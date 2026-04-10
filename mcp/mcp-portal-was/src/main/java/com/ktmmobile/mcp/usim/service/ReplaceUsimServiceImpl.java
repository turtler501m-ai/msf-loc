package com.ktmmobile.mcp.usim.service;

import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.MplatFormOsstWebService;
import com.ktmmobile.mcp.common.mplatform.vo.MpUsimPukVO;
import com.ktmmobile.mcp.common.mplatform.vo.RetvUsimChgAcceptPsblVO;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.service.CustRequestService;
import com.ktmmobile.mcp.usim.dao.ReplaceUsimDao;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimDto;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimSubDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReplaceUsimServiceImpl implements ReplaceUsimService {

	@Autowired
	private ReplaceUsimDao replaceUsimDao;

	@Autowired
	private MplatFormOsstWebService mplatFormOsstWebService;

	@Autowired
	private CustRequestService custRequestService;

	@Value("${api.interface.server}")
	private String apiInterfaceServer;

	@Override
	public String selectCustomerIdByConnInfo(ReplaceUsimDto replaceUsimDto) {

		RestTemplate restTemplate = new RestTemplate();
		String[] customerIds = restTemplate.postForObject(apiInterfaceServer + "/usim/selectCustomerIdByConnInfo", replaceUsimDto, String[].class);

		if(customerIds == null || customerIds.length != 1){
			return null;
		}

		return customerIds[0];
	}

	@Override
	public List<ReplaceUsimSubDto> selectReplaceUsimSubInfo(String customerId) {

		List<ReplaceUsimSubDto> replaceUsimSubDtos = new ArrayList<>();

		// 계약정보 조회
		RestTemplate restTemplate = new RestTemplate();
		ReplaceUsimDto[] replaceUsimDtos = restTemplate.postForObject(apiInterfaceServer + "/usim/selectReplaceUsimSubInfo", customerId, ReplaceUsimDto[].class);

		if(replaceUsimDtos == null || replaceUsimDtos.length == 0){
			return replaceUsimSubDtos;
		}

		// 신청 가능여부 확인
		long osstNo = replaceUsimDao.generateReplaceUsimOsstNo();

		for(ReplaceUsimDto dto : replaceUsimDtos){

			ReplaceUsimSubDto replaceUsimSubDto = new ReplaceUsimSubDto();
			replaceUsimSubDto.setMskMobileNo(dto.getSubscriberNo());
			replaceUsimSubDto.setOsstNo(osstNo);

			// 기접수건
			if(0 < dto.getSeq()){
				String preAppliedCd = "00".equals(dto.getPreChkRsltCd()) ? "PRE_APPLY_DONE" : "PRE_APPLY_ING";
				replaceUsimSubDto.setResultCd(preAppliedCd);
				replaceUsimSubDto.setResultMsg("기접수건");
				replaceUsimSubDtos.add(replaceUsimSubDto);
				continue;
			}

			// 정지
			if("S".equals(dto.getSubStatus())){
				replaceUsimSubDto.setResultCd("CONTRACT_STOP");
				replaceUsimSubDto.setResultMsg("정지회선");
				replaceUsimSubDtos.add(replaceUsimSubDto);
				continue;
			}

			// eSIM고객 : eSIM고객 T01 호출 안하는 것으로 변경
			if("E".equals(dto.getUsimType())){
				replaceUsimSubDto.setResultCd("ESIM_APPLY_LIMIT");
				replaceUsimSubDto.setResultMsg("이심 신청 불가");
				replaceUsimSubDtos.add(replaceUsimSubDto);
				continue;
			}

			// T01 유심무상교체 접수 가능 여부조회
			RetvUsimChgAcceptPsblVO retvUsimChgAcceptPsblVO = new RetvUsimChgAcceptPsblVO();
			try{
				retvUsimChgAcceptPsblVO = mplatFormOsstWebService.retvUsimChgAcceptPsbl(dto.getSvcCntrNo(), dto.getSubscriberNo(), dto.getCustomerId());
				replaceUsimSubDto.setResultCd(retvUsimChgAcceptPsblVO.getRsltCd());
				replaceUsimSubDto.setResultMsg(retvUsimChgAcceptPsblVO.getRsltMsg());

				// 요청사항: eSIM고객 (T01 호출 시 변경 가능으로 응답받을 경우) 신청불가로 바꿔치기
				if("00".equals(retvUsimChgAcceptPsblVO.getRsltCd()) && "E".equals(retvUsimChgAcceptPsblVO.getUsimTypeCd())){
					replaceUsimSubDto.setResultCd("ESIM_APPLY_LIMIT");
					replaceUsimSubDto.setResultMsg("이심 신청 불가");
				}

			}catch(SelfServiceException e){
				replaceUsimSubDto.setResultCd("PRE_CHECK_FAIL");
				replaceUsimSubDto.setResultMsg("연동 오류");

				retvUsimChgAcceptPsblVO.setGlobalNo(e.getGlobalNo());
				retvUsimChgAcceptPsblVO.setResultCode(e.getResultCode());
				retvUsimChgAcceptPsblVO.setSvcMsg(e.getMessageNe());

			}catch(SocketTimeoutException | McpMplatFormException e){
				replaceUsimSubDto.setResultCd("PRE_CHECK_FAIL");
				replaceUsimSubDto.setResultMsg("연동 오류");
			}catch(Exception e){
				replaceUsimSubDto.setResultCd("PRE_CHECK_FAIL");
				replaceUsimSubDto.setResultMsg("연동 오류");
			}

			// 연동 결과 INSERT
			retvUsimChgAcceptPsblVO.setOsstNo(osstNo);
			retvUsimChgAcceptPsblVO.setContractNum(dto.getContractNum());
			retvUsimChgAcceptPsblVO.setSvcCntrNo(dto.getSvcCntrNo());
			replaceUsimDao.saveFreeUsimOsst(retvUsimChgAcceptPsblVO);
			replaceUsimSubDtos.add(replaceUsimSubDto);
		}

		return replaceUsimSubDtos;
	}

	@Override
	public List<RetvUsimChgAcceptPsblVO> selectReplaceUsimReqInfo(ReplaceUsimDto replaceUsimDto) {

		// 계약정보 조회
		RestTemplate restTemplate = new RestTemplate();
		ReplaceUsimDto[] replaceUsimDtos = restTemplate.postForObject(apiInterfaceServer + "/usim/selectReplaceUsimSubInfo", replaceUsimDto.getCustomerId(), ReplaceUsimDto[].class);

		if(replaceUsimDtos == null || replaceUsimDtos.length == 0){
			return null;
		}

		List<String> contractNums = Arrays.stream(replaceUsimDtos)
			.map(ReplaceUsimDto::getContractNum)
			.collect(Collectors.toList());

		Map<String, String> ctns = Arrays.stream(replaceUsimDtos)
				.collect(Collectors.toMap(ReplaceUsimDto::getContractNum, ReplaceUsimDto::getSubscriberNo));

		// 유심교체 신청가능 여부 결과 조회 (사전체크 결과 조회)
		List<RetvUsimChgAcceptPsblVO> retvUsimChgAcceptPsblVOs = replaceUsimDao.selectFreeUsimOsstResult(replaceUsimDto.getOsstNo());
		if(retvUsimChgAcceptPsblVOs == null || retvUsimChgAcceptPsblVOs.isEmpty()){
			return null;
		}

		// 접수 가능한 계약건만 추출
		return retvUsimChgAcceptPsblVOs.stream()
			.filter(item ->  contractNums.contains(item.getContractNum()))
			.filter(item -> "00".equals(item.getRsltCd()))
			.filter(item -> !"E".equals(item.getUsimTypeCd()))
			.map(item -> {
				item.setCtn(ctns.get(item.getContractNum()));
				return item;
			})
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void saveReplaceUsimRequest(ReplaceUsimDto replaceUsimDto, List<RetvUsimChgAcceptPsblVO> retvUsimChgAcceptPsblVOs) {

		UserSessionDto userSession = SessionUtils.getUserCookieBean();
		String userId = userSession == null ? null : userSession.getUserId();
		replaceUsimDto.setCretId(userId);

		// 신청정보 마스터 저장
		replaceUsimDao.saveFreeUsimReqMst(replaceUsimDto);

		// 신청정보 상세 저장
		for(RetvUsimChgAcceptPsblVO vo : retvUsimChgAcceptPsblVOs){
			vo.setSeq(replaceUsimDto.getSeq());
			vo.setCretId(replaceUsimDto.getCretId());

			// Y07 USIM PUK 번호 조회
			MpUsimPukVO mpUsimPukVO = custRequestService.getUsimPukByMP(vo.getSvcCntrNo(), vo.getCtn(), replaceUsimDto.getCustomerId());
			if(!mpUsimPukVO.isSuccess()){
				mpUsimPukVO.setIntmMdlId(null);
				vo.setUsimModelId("0000");
			}

			// NFC 유심여부
			if (mpUsimPukVO.getIntmMdlId() != null) {
				ReplaceUsimDto usimInfo = this.selectNfcUsimYn(mpUsimPukVO.getIntmMdlId());
				vo.setNfcUsimYn(usimInfo.getNfcUsimYn());
				vo.setUsimModelCd(usimInfo.getUsimModelCd());
				vo.setUsimModelId(usimInfo.getUsimModelId());
			}

			replaceUsimDao.saveFreeUsimReqDtl(vo);
		}
	}

	@Override
	public McpUserCntrMngDto selectContractInfoForReplaceUsim(ReplaceUsimDto replaceUsimDto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/usim/selectContractInfoForReplaceUsim", replaceUsimDto, McpUserCntrMngDto.class);
	}

	@Override
	public ReplaceUsimDto selectNfcUsimYn(String intmMdlId) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/usim/selectNfcUsimYn", intmMdlId, ReplaceUsimDto.class);
	}

}
