package com.ktmmobile.mcp.usim.service;

import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.dto.UsimChgAcceptVO;
import com.ktmmobile.mcp.common.mplatform.service.MplatFormOsstWebService;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.usim.dao.ReplaceUsimDao;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimDto;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimSendDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReplaceUsimService {

	@Autowired
	private ReplaceUsimDao replaceUsimDao;

	@Autowired
	private MplatFormOsstWebService mplatFormOsstWebService;

	@Value("${api.interface.server}")
	private String apiInterfaceServer;

	public List<ReplaceUsimSendDto> selectReplaceUsimSendList() {

		List<ReplaceUsimSendDto> sendList = null;

		// 날짜 및 처리건수 제어
		Map<String, String> paraMap = new HashMap<>();
		String offsetDays = replaceUsimDao.selectFreeUsimSettings("offsetDays");
		String procCnt = replaceUsimDao.selectFreeUsimSettings("procCnt");

		paraMap.put("offsetDays", StringUtil.NVL(offsetDays, "7"));
		paraMap.put("procCnt", StringUtil.NVL(procCnt, "300"));
		
		// 대상 리스트 조회
		RestTemplate restTemplate = new RestTemplate();
		ReplaceUsimSendDto[] replaceUsimSendDtos = restTemplate.postForObject(apiInterfaceServer + "/usim/selectReplaceUsimSendList", paraMap, ReplaceUsimSendDto[].class);

		if(replaceUsimSendDtos != null){
			sendList = Arrays.asList(replaceUsimSendDtos);
		}

		return sendList;
	}

	@Transactional
	public boolean sendReplaceUsimRcpInfo(ReplaceUsimSendDto replaceUsimSendDto) {

		boolean result = true;

		ReplaceUsimDto updateDto = new ReplaceUsimDto();
		updateDto.setSeq(replaceUsimSendDto.getSeq());
		updateDto.setSubSeq(replaceUsimSendDto.getSubSeq());

		// 해지고객
		if("C".equals(replaceUsimSendDto.getSubStatus())){
			updateDto.setApplyRsltCd("CONTRACT_CANCEL");
			updateDto.setApplyRsltMsg("해지 고객");
			replaceUsimDao.updateFreeUsimReqDtl(updateDto);
			return true;
		}

		// T02 유심무상교체 신청내역 전송
		UsimChgAcceptVO usimChgAcceptVO = new UsimChgAcceptVO();
		try{

			String ncn = replaceUsimSendDto.getSvcCntrNo();
			String ctn = replaceUsimSendDto.getSubscriberNo();
			String custId = replaceUsimSendDto.getCustomerId();
			String acceptDt = replaceUsimSendDto.getReqInDay();
			String acceptChCd = replaceUsimSendDto.getChnlCd();
			String deliveryDivCd = replaceUsimSendDto.getDeliveryType();

			usimChgAcceptVO = mplatFormOsstWebService.usimChgAccept(ncn, ctn, custId, acceptDt, acceptChCd, deliveryDivCd);

			updateDto.setApplyRsltCd(usimChgAcceptVO.getRsltCd());
			updateDto.setApplyRsltMsg(usimChgAcceptVO.getRsltMsg());

		}catch(SelfServiceException e){
			updateDto.setApplyRsltCd(e.getResultCode());
			updateDto.setApplyRsltMsg(e.getMessageNe());

			usimChgAcceptVO.setGlobalNo(e.getGlobalNo());
			usimChgAcceptVO.setResultCode(e.getResultCode());
			usimChgAcceptVO.setSvcMsg(e.getMessageNe());

		}catch(SocketTimeoutException e){
			updateDto.setApplyRsltCd("MP_TIMEOUT");
			updateDto.setApplyRsltMsg("Socket Timeout");
			result = false;
		}catch(McpMplatFormException e){
			updateDto.setApplyRsltCd("MP_RES_NULL");
			updateDto.setApplyRsltMsg("Response Message Is Null");
			result = false;
		}catch(Exception e){
			updateDto.setApplyRsltCd("MP_EXCEPTION");
			updateDto.setApplyRsltMsg("시스템 내부 처리 오류");
			result = false;
		}

		// 연동 결과 INSERT
		long osstNo = replaceUsimSendDto.getOsstNo();
		if(osstNo < 1){
			osstNo = replaceUsimDao.generateReplaceUsimOsstNo(replaceUsimSendDto.getSeq());
		}

		usimChgAcceptVO.setOsstNo(osstNo);
		usimChgAcceptVO.setContractNum(replaceUsimSendDto.getContractNum());
		usimChgAcceptVO.setSvcCntrNo(replaceUsimSendDto.getSvcCntrNo());
		replaceUsimDao.saveFreeUsimOsst(usimChgAcceptVO);

		// 신청정보 UPDATE
		updateDto.setOsstNo(osstNo);
		int uCnt = replaceUsimDao.updateFreeUsimReqDtl(updateDto);
		if(0 < uCnt){
			replaceUsimDao.updateFreeUsimReqMst(updateDto);
		}

		return result;
	}

}
