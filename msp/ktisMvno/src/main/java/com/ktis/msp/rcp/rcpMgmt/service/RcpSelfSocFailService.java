package com.ktis.msp.rcp.rcpMgmt.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ktis.msp.rcp.statsMgmt.mapper.StatsMgmtMapper;
import com.ktis.msp.rcp.statsMgmt.service.StatsMgmtService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpSelfSocFailMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpSelfSocFailVO;

/**
 * @Class Name : RcpSelfSocFailService
 * @Description : RcpSelfSocFailVO Service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2021.11.16 권오승 최초생성
 * @
 * @author : 권오승
 * @Create Date : 2021. 11. 16.
 */
@Service
public class RcpSelfSocFailService extends BaseService {

	@Autowired
	private RcpSelfSocFailMapper rcpSelfSocFailMapper;

	@Autowired
	private StatsMgmtService statsMgmtService;

	@Autowired
	private StatsMgmtMapper statsMgmtMapper;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
		
	public RcpSelfSocFailService() {
		setLogPrefix("[RcpSelfSocFailService] ");
	}
	
	/**
	 * @Description : 요금제 셀프변경 실패 고객 리스트 조회
	 * @Param  : rcpSelfSocFailVo
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 16.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<RcpSelfSocFailVO> getRcpSelfSocFailList(RcpSelfSocFailVO rcpSelfSocFailVo , Map<String, Object> paramMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("요금제 셀프변경 실패 고객 리스트 조회 START."));
        logger.info(generateLogMsg("Return Vo [rcpSelfSocFailVo] = " + rcpSelfSocFailVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<RcpSelfSocFailVO> rcpSelfSocFailVoList = new ArrayList<RcpSelfSocFailVO>();
		
		
		if(Integer.parseInt(rcpSelfSocFailVo.getStrDate()) > Integer.parseInt(rcpSelfSocFailVo.getEndDate())){
			throw new MvnoRunException(-1, "시작일자가 종료일자보다 클 수 없습니다.");
		}
		

		if(rcpSelfSocFailVo.getStrDate()== null || "".equals(rcpSelfSocFailVo.getStrDate()) || rcpSelfSocFailVo.getEndDate() == null || "".equals(rcpSelfSocFailVo.getEndDate())){
			throw new MvnoRunException(-1, "사용기간이 누락되었습니다.");
		}
		
		rcpSelfSocFailVoList = rcpSelfSocFailMapper.getRcpSelfSocFailList(rcpSelfSocFailVo);
		
		
		for(RcpSelfSocFailVO item : rcpSelfSocFailVoList) {
			maskingService.setMask(item, maskingService.getMaskFields(), paramMap);
		}
		
		return rcpSelfSocFailVoList;
		
	}
	
	/**
	 * @Description : 요금제변경 실패고객 처리상세 결과 조회
	 * @Param  : rcpSelfSocFailVo
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 17.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<?> getRcpSelfSocFailDetail(RcpSelfSocFailVO rcpSelfSocFailVo,Map<String, Object> paramMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("요금제 셀프변경 실패 고객 리스트 조회 START."));
        logger.info(generateLogMsg("Return Vo [rcpSelfSocFailVo] = " + rcpSelfSocFailVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> rcpSelfSocFailVoList = new ArrayList<RcpSelfSocFailVO>();
				
		rcpSelfSocFailVoList = rcpSelfSocFailMapper.getRcpSelfSocFailDetail(rcpSelfSocFailVo);

		maskingService.setMask(rcpSelfSocFailVoList, maskingService.getMaskFields(), paramMap);

		return rcpSelfSocFailVoList;
		
	}
	
	/**
	 * 사용자상태변경
	 * @param vo
	 * <li>taxbillSrlNum=계산서일련번호</li>
	 * <li>userId=사용자id</li>
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateSelfSocFailProc(RcpSelfSocFailVO vo) {
		
		rcpSelfSocFailMapper.updateSelfSocFailProc(vo);	
		
	}
	
	/**
	 * @Description : 요금제 셀프변경 실패 고객 리스트 엑셀다운로드
	 * @Param  : rcpSelfSocFailVo
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 17.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<RcpSelfSocFailVO> getRcpSelfSocFailListExcel(RcpSelfSocFailVO rcpSelfSocFailVo ,  Map<String, Object> paramMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("요금제 셀프변경 실패 고객 리스트 엑셀다운로드 START."));
        logger.info(generateLogMsg("Return Vo [rcpSelfSocFailVo] = " + rcpSelfSocFailVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<RcpSelfSocFailVO> rcpSelfSocFailVoList = new ArrayList<RcpSelfSocFailVO>();
		
		if(Integer.parseInt(rcpSelfSocFailVo.getStrDate()) > Integer.parseInt(rcpSelfSocFailVo.getEndDate())){
			throw new MvnoRunException(-1, "시작일자가 종료일자보다 클 수 없습니다.");
		}
		

		if(rcpSelfSocFailVo.getStrDate()== null || "".equals(rcpSelfSocFailVo.getStrDate()) || rcpSelfSocFailVo.getEndDate() == null || "".equals(rcpSelfSocFailVo.getEndDate())){
			throw new MvnoRunException(-1, "사용기간이 누락되었습니다.");
		}
		
		rcpSelfSocFailVoList = rcpSelfSocFailMapper.getRcpSelfSocFailListExcel(rcpSelfSocFailVo);
		
		for(RcpSelfSocFailVO item : rcpSelfSocFailVoList) {
			maskingService.setMask(item, maskingService.getMaskFields(), paramMap);
		}
		
		return rcpSelfSocFailVoList;
		
	}

	/**
	 * @Description : 부가서비스 재처리 금지 조건 확인
	 * @Param  : rcpSelfSocFailVo
	 * @Author : hsy
	 * @Create Date : 2023. 11. 06.
	 */
    public void retryPrmtAutoChk(RcpSelfSocFailVO searchVO) throws ParseException {

		if(KtisUtil.isEmpty(searchVO.getPrcsMdlInd()) || KtisUtil.isEmpty(searchVO.getSeq()) || KtisUtil.isEmpty(searchVO.getSysDt())) {
			throw new MvnoRunException(-1, "선택된 데이터가 없습니다.<br>데이터를 선택하고 처리해주시기 바랍니다.");
		}
		
		// 1. 본사 소속 (고객센터 포함)만 재처리 가능
		if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())) throw new MvnoRunException(-1, "재처리 불가한 고객입니다.");

		// 2. 고객 계약번호 / 이벤트 코드 / 부가서비스 확인
		Map<String, String> cstmrChkMap = rcpSelfSocFailMapper.getCstmrChk(searchVO);
		if(cstmrChkMap == null) throw new MvnoRunException(-1, "고객 정보가 존재하지 않습니다.");

		// 2-1. 고객 이벤트 코드가 x21 이면 안됨.
		if(!"X21".equals(cstmrChkMap.get("EVENT_CODE"))) throw new MvnoRunException(-1, "부가서비스만 재처리 가능합니다.");

		// 2-2. 성공여부 / 처리여부가 Y가 아니어야함.
		if("Y".equals(cstmrChkMap.get("PROC_YN")) || "Y".equals(cstmrChkMap.get("SUCC_YN")))
			throw new MvnoRunException(-1, "부가서비스 가입 성공인 경우 재처리를 할 수 없습니다.");

		// 3. 고객 SUB_INFO 정보 확인
		Map<String,String> cstmrInfoMap = statsMgmtMapper.getCstmrSubInfo(cstmrChkMap.get("CONTRACT_NUM"));
		if(cstmrInfoMap == null) throw new MvnoRunException(-1, "현재 회선을 사용하는 고객이 아닙니다.");

		// 4. 현재 가입된 프로모션 확인
		Map<String,String> cstmrPrmtId = rcpSelfSocFailMapper.getCstmrPrmtId(cstmrChkMap.get("CONTRACT_NUM"));
		if(cstmrPrmtId == null) throw new MvnoRunException(-1, "고객 정보가 존재하지 않습니다.");
		if(!cstmrPrmtId.get("PRMT_ID").equals(cstmrPrmtId.get("DIS_PRMT_ID"))) throw new MvnoRunException(-1, "다른 프로모션 가입자 고객입니다.");

		// 5. 프로모션 적용대상 max값 확인
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("contractNum", cstmrChkMap.get("CONTRACT_NUM"));
		param.put("effectiveDate", cstmrChkMap.get("SYS_RDATE"));

		String maxEffDtRslt = statsMgmtMapper.getMaxTrgEffDt(param);
		if(!"Y".equals(maxEffDtRslt)) throw new MvnoRunException(-1, "다른 프로모션 가입자 고객입니다.");

		// 6. 프로모션 재처리 가능일 확인
		// 6-1. 공통코드 재처리 가능일 가져오기
		String rtyCanDt = statsMgmtMapper.getRtyCanDt();
		int rtyMaxDt = 0;
		if(!KtisUtil.isEmpty(rtyCanDt)) rtyMaxDt = Integer.parseInt(rtyCanDt);
		else rtyMaxDt = 30;

		// 6-2. 날짜 형식 세팅
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date todt = sdf.parse(searchVO.getStrDate()); //오늘

		// 6-3. 재처리 가능일 세팅
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(todt);
		calendar.add(Calendar.DATE, -rtyMaxDt);
		Date rtyDate = calendar.getTime(); //재처리 가능일

		// 6-4. 재처리 할 프로모션 가입일 체크
		String regstDt = cstmrChkMap.get("SYS_RDATE");
		Date regstDate = sdf.parse(regstDt);
		if(regstDate.before(rtyDate)) throw new MvnoRunException(-1, "재처리 가능 기간이 지났습니다.");


		// 7. 부가서비스 성공여부 / 재처리 횟수
		searchVO.settSocCd(cstmrChkMap.get("T_SOC_CODE"));
		searchVO.setContractNum(cstmrChkMap.get("CONTRACT_NUM"));
		List<EgovMap> chkList = rcpSelfSocFailMapper.getSocRtyChkList(searchVO);
		if(chkList.size() == 0) throw new MvnoRunException(-1, "잘못된 접근입니다.");


		int rtyCnt = 0;
		for(int i=0; i<chkList.size(); i++){
			// 7-1. 부가서비스 가입 성공인 경우
			if("0000".equals(chkList.get(i).get("succYn")))
				throw new MvnoRunException(-1, "부가서비스 가입 성공인 경우 재처리를 할 수 없습니다.");

			// 7-2. 재처리 횟수 체크
			if("RTY".equals(chkList.get(i).get("tbNm")) && "1".equals(chkList.get(i).get("rtySeq")))
				rtyCnt++;
			if(rtyCnt >= 3) throw new MvnoRunException(-1, "재처리는 3번까지 가능합니다.");
		}

		searchVO.setNcn(cstmrInfoMap.get("SVC_CNTR_NO"));
		searchVO.setCtn(cstmrInfoMap.get("SUBSCRIBER_NO"));
		searchVO.setCustomerId(cstmrInfoMap.get("CUSTOMER_ID"));
    }

	/**
	 * @Description : 요금제 셀프변경 실패 고객 부가서비스 재처리 연동
	 * @Param  : rcpSelfSocFailVo
	 * @Return : Map<String, Object>
	 * @Author : 한수연
	 * @Create Date : 2021. 11. 07.
	 */
	public String retrySocAutoAdd(RcpSelfSocFailVO searchVO) throws InterruptedException {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "X21");
		param.put("ncn", searchVO.getNcn());
		param.put("ctn",searchVO.getCtn());
		param.put("custId",searchVO.getCustomerId());
		param.put("soc",searchVO.gettSocCd());
		param.put("mdlInd", "WEB");
		param.put("userid",searchVO.getUserId());
		param.put("ip", searchVO.getAccessIp());

		// 1. 부가서비스 (x21) 연동
		for(int reTryCount = 0; reTryCount < 2; reTryCount++){
			if(result == null){
				result = statsMgmtService.mpPrmtSocAddRty(param);
			} else if ("ITL_SYS_E0001".equals(result.get("code"))){
				// 1-1. MP 연동 오류인 경우만 연동 한번 더 진행
				Thread.sleep(3000);
				// 1-2. 이력 저장
				searchVO.setGlobalNo((String)result.get("globalNo"));
				searchVO.setRsltCd((String)result.get("code"));
				searchVO.setPrcsSbst((String)result.get("msg"));
				this.insertRtySocFail(searchVO);

				// 1-3. 부가서비스 (x21) 재처리 시작
				result = statsMgmtService.mpPrmtSocAddRty(param);
			}
		}

		// 2. 이력 저장
		searchVO.setGlobalNo((String)result.get("globalNo"));
		searchVO.setRsltCd((String)result.get("code"));
		searchVO.setPrcsSbst((String)result.get("msg"));

		this.insertRtySocFail(searchVO);

		String resultCd="N";

		// 3. 재처리 성공인 경우
		if("0000".equals(result.get("code"))){
			resultCd = "Y";
		}

		return resultCd;
	}

	/**
	 * @Description 요금제 셀프변경 실패 고객 부가서비스 재처리 연동 결과  insert
	 * @Param : searchVO
	 * @Return : int
	 * @Author : hsy
	 * @CreateDate : 2023.11.07
	 */
	private int insertRtySocFail(RcpSelfSocFailVO searchVO) {
		if(KtisUtil.isEmpty(searchVO.getRtySeq())) {
			String rtySeq = rcpSelfSocFailMapper.getRtySeq();
			searchVO.setRtySeq(rtySeq);
		}
		return rcpSelfSocFailMapper.insertRtySocFail(searchVO);
	}

}
