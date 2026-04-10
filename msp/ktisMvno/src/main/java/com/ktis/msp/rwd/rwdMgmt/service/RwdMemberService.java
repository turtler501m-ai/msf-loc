package com.ktis.msp.rwd.rwdMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.cdmgmt.mapper.CmnCdMgmtMapper;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendVO;
import com.ktis.msp.rwd.rwdMgmt.mapper.RwdMemberMapper;
import com.ktis.msp.rwd.rwdMgmt.vo.RwdMemberVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class RwdMemberService extends BaseService {
    @Autowired
    private CmnCdMgmtMapper cmnCdMgmtMapper;
	
	@Autowired
	private RwdMemberMapper rwdMemberMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	protected Logger logger = LogManager.getLogger(getClass());
	
	public RwdMemberService() {
		setLogPrefix("[RwdMemberService] ");
	}
	
	/**
	* @Description : 보상서비스 접수목록
	* @Param  : RwdMemberVO rwdMemberVO, Map<String, Object> paramMap
	* @Author : 장익준
	* @Create Date : 2023. 2. 22.
	* @Return : List<RwdMemberVO>
	*/
	public List<RwdMemberVO> getMemberJoinList(RwdMemberVO rwdMemberVO, Map<String, Object> paramMap){
		
		if ( (rwdMemberVO.getSearchCd() == null || "".equals(rwdMemberVO.getSearchCd()))
			&& ((rwdMemberVO.getSearchFromDt() == null || "".equals(rwdMemberVO.getSearchFromDt()))
			|| (rwdMemberVO.getSearchToDt() == null || "".equals(rwdMemberVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "가입일자 정보가 존재 하지 않습니다.");
		}
		
		List<RwdMemberVO> rsltAryListVo = rwdMemberMapper.getMemberJoinList(rwdMemberVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	/**
	* @Description : 보상서비스 가입자목록
	* @Param  : RwdMemberVO rwdMemberVO, Map<String, Object> paramMap
	* @Author : 장익준
	* @Create Date : 2023. 2. 22.
	* @Return : List<RwdMemberVO>
	*/
	public List<RwdMemberVO> getMemberList(RwdMemberVO rwdMemberVO, Map<String, Object> paramMap){
		
		if ( (rwdMemberVO.getSearchCd() == null || "".equals(rwdMemberVO.getSearchCd()))
			&& ((rwdMemberVO.getSearchFromDt() == null || "".equals(rwdMemberVO.getSearchFromDt()))
			|| (rwdMemberVO.getSearchToDt() == null || "".equals(rwdMemberVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "가입일자 정보가 존재 하지 않습니다.");
		}
		
		List<RwdMemberVO> rsltAryListVo = rwdMemberMapper.getMemberList(rwdMemberVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	/**
	* @Description : 보상서비스 접수목록 엑셀 다운로드
	* @Param  : RwdMemberVO rwdMemberVO, Map<String, Object> paramMap
	* @Author : 장익준
	* @Create Date : 2023. 2. 22.
	* @Return : List<RwdMemberVO>
	*/
	public List<RwdMemberVO> getRwdMemberJoinListByExcel(RwdMemberVO rwdMemberVO, Map<String, Object> paramMap){
		
		if ( (rwdMemberVO.getSearchCd() == null || "".equals(rwdMemberVO.getSearchCd()))
			&& ((rwdMemberVO.getSearchFromDt() == null || "".equals(rwdMemberVO.getSearchFromDt()))
			|| (rwdMemberVO.getSearchToDt() == null || "".equals(rwdMemberVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "가입일자 정보가 존재 하지 않습니다.");
		}
		
		List<RwdMemberVO> rsltAryListVo = rwdMemberMapper.getRwdMemberJoinListByExcel(rwdMemberVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	/**
	* @Description : 보상서비스 접수목록 엑셀 다운로드
	* @Param  : RwdMemberVO rwdMemberVO, Map<String, Object> paramMap
	* @Author : 장익준
	* @Create Date : 2023. 2. 22.
	* @Return : List<RwdMemberVO>
	*/
	public List<RwdMemberVO> getRwdMemberListByExcel(RwdMemberVO rwdMemberVO, Map<String, Object> paramMap){
		
		if ( (rwdMemberVO.getSearchCd() == null || "".equals(rwdMemberVO.getSearchCd()))
			&& ((rwdMemberVO.getSearchFromDt() == null || "".equals(rwdMemberVO.getSearchFromDt()))
			|| (rwdMemberVO.getSearchToDt() == null || "".equals(rwdMemberVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "가입일자 정보가 존재 하지 않습니다.");
		}
		
		List<RwdMemberVO> rsltAryListVo = rwdMemberMapper.getRwdMemberListByExcel(rwdMemberVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	/**
	* @Description : 마스킹
	* @Param  : 
	* @Return : HashMap<String, String>
	* @Author : 장익준
	* @Create Date : 2023. 2. 22.
	*/
	private HashMap<String, String> getMaskFields() {
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		
		maskFields.put("subLinkName", "CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("custNm", "CUST_NAME");
		maskFields.put("intmSrlNo", "DEV_NO");
		maskFields.put("imei", "DEV_NO");
		maskFields.put("imeiTwo", "DEV_NO");
		
		return maskFields;
	}
	
	/**
	 * @Description : 고객정보 조회
	 * @Param  : RwdMemberVO
	 * @Return : RwdRegVO
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	public RwdMemberVO getRwdCntrInfo(RwdMemberVO rwdMemberVO, Map<String, Object> paramMap) {
		
		if (rwdMemberVO.getSearchGbVal() == null || "".equals(rwdMemberVO.getSearchGbVal()) ){
			throw new MvnoRunException(-1, "해당 정보가 존재 하지 않습니다.");
		}
		
		RwdMemberVO rsltVO = rwdMemberMapper.getRwdCntrInfo(rwdMemberVO);
		
		if(rsltVO != null && rsltVO.getContractNum() != null && !"".equals(rsltVO.getContractNum())) {
			HashMap<String, String> maskFields = getMaskFields();
		
			maskingService.setMask(rsltVO, maskFields, paramMap);
		}
		
		return rsltVO;
	}
	
	/**
	 * @Description : 보상서비스 신규 등록
	 * @Param  : RwdMemberVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	public void regMspRwdOrder(RwdMemberVO rwdMemberVO) {
		
		int resultCnt = 0; //건수체크
		int insertCnt = 0; //등록건수
		
		resultCnt = rwdMemberMapper.getRwdOrderCntrCnt(rwdMemberVO);
		if(resultCnt >= 1) {
			throw new MvnoRunException(-1, "보상서비스 가입 신청 고객입니다.<br>신청 정보를 확인 하세요.");
		}
		
		resultCnt = rwdMemberMapper.getRwdOrderImeiCnt(rwdMemberVO);
		if(resultCnt >= 1) {
			throw new MvnoRunException(-1, "보상서비스 가입 신청 중인 고객의 IMEI 정보입니다.<br>신청 정보를 확인 하세요.");
		}
		
		resultCnt = rwdMemberMapper.getRwdMemCntrCnt(rwdMemberVO);
		if(resultCnt >= 1) {
			throw new MvnoRunException(-1, "보상서비스 정상가입 또는 가입 처리중인 고객입니다.<br>신청 정보를 확인 하세요.");
		}
		
		resultCnt = rwdMemberMapper.getRwdMemImeiCnt(rwdMemberVO);
		if(resultCnt >= 1) {
			throw new MvnoRunException(-1, "보상서비스 정상가입 또는 가입 처리중인 고객의 IMEI 정보입니다.<br>신청 정보를 확인 하세요.");
		}
		
		try {
			insertCnt = rwdMemberMapper.regMspRwdOrder(rwdMemberVO);
//			if(insertCnt > 0) {
//				// SMS 보상서비스 접수
//				this.getSendSms(rwdMemberVO, "254");
//			}
		} catch (Exception e) {
			if (e instanceof DataAccessException) {
				throw new MvnoRunException(-1, "보상서비스 정상가입 또는 가입 처리중인 고객입니다.<br>신청 정보를 확인 하세요.");
			}
			throw new MvnoRunException(-1, e.getMessage());
		}
	}

	/**
	 * @Description : 보상서비스 수정
	 * @Param  : RwdMemberVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	public void updateRwdOrder(RwdMemberVO rwdMemberVO) {
		String ifTrgtCd = "";
		int resultCnt = 0; //건수체크
		
		try {
			ifTrgtCd = rwdMemberMapper.getOrderIfTrgtCd(rwdMemberVO);
			
			if("C".equals(ifTrgtCd) || "S".equals(ifTrgtCd)) {
				if("CAN".equals(rwdMemberVO.getUpdType())) {
					throw new MvnoRunException(-1, "연동상태가 전송대상인 신청 건만 취소할 수 있습니다.");
				} else {
					throw new MvnoRunException(-1, "연동상태가 전송대상인 신청 건만 수정할 수 있습니다.");
				}
			}
			
			if("CAN".equals(rwdMemberVO.getUpdType())) {
				rwdMemberMapper.updateRwdOrder(rwdMemberVO);
			} else {
				resultCnt = rwdMemberMapper.getRwdOrderImeiCnt(rwdMemberVO);
				if(resultCnt >= 1) {
					throw new MvnoRunException(-1, "보상서비스 가입 신청 중인 고객의 IMEI 정보입니다.<br>신청 정보를 확인 하세요.");
				}
				resultCnt = rwdMemberMapper.getRwdMemImeiCnt(rwdMemberVO);
				if(resultCnt >= 1) {
					throw new MvnoRunException(-1, "보상서비스 정상가입 또는 가입 처리중인 고객의 IMEI 정보입니다.<br>신청 정보를 확인 하세요.");
				}

				rwdMemberMapper.updateRwdOrder(rwdMemberVO);
			}
		} catch (Exception e) {
			if (e instanceof DataAccessException) {
				throw new MvnoRunException(-1, "수정중 오류가 발생하였습니다.");
			}
			throw new MvnoRunException(-1, e.getMessage());
		}
	}
	
	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : RwdMemberVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	public List<?> getFile1(RwdMemberVO rwdMemberVO){
		
		List<?> fileInfoVOs = new ArrayList<RwdMemberVO>();
		
		fileInfoVOs = rwdMemberMapper.getFile1(rwdMemberVO);
		
		return fileInfoVOs;
	}
	
	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : RwdMemberVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	public String getFileNm(String fileId){
		
		
		String returnFileId = rwdMemberMapper.getFileNm(fileId);
		
		return returnFileId;
	}  
	
	/**
	 * @Description : 첨부 파일 삭제
	 * @Param  : fileId
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	public int deleteFile(String rwdSeq){
		
		int returnFileId = rwdMemberMapper.deleteFile(rwdSeq);
		
		return returnFileId;
	}
	
    /**
     * @Description : 파일 정보를 등록한다.
     * @Param  : RwdMemberVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2023. 2. 22.
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertFile(RwdMemberVO rwdMemberVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("insertFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = rwdMemberMapper.insertFile(rwdMemberVO);
		
        return resultCnt;
    }
    
    /**
     * @Description : 파일명을 가져온다.
     * @Param  : RwdMemberVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2023. 2. 22.
     */
    @Transactional(rollbackFor=Exception.class)
    public String getFileName(){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("getFileName START."));
		logger.info(generateLogMsg("================================================================="));
		
		String fileNm = rwdMemberMapper.getFileName();
		
        return fileNm;
    }
    

    /**
     * @Description : 파일 이름 시퀀스 조회
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    public String getFileNmSeq(){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일 이름 시퀀스 조회 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        String strSeq = rwdMemberMapper.getFileNmSeq();
        logger.info(generateLogMsg("파일 시퀀스 : " + strSeq));
        logger.info(generateLogMsg("================================================================="));
        return strSeq;
    }
    
    /**
     * @Description : 선택한 고객 정보 등록
     * @Param  : PayInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertImageFile(RwdMemberVO rwdMemberVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("고객 정보 생성 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        
        try{
        	resultCnt = rwdMemberMapper.insertImageFile(rwdMemberVO);
            
            logger.info(generateLogMsg("등록건수 = " + resultCnt));
            
        }catch (Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
	private void getSendSms(RwdMemberVO rwdMemberVO, String templateId) {
		//자급제 보상서비스 코드로 보상서비스명 찾아오기
		CmnCdMgmtVo paramVo = new CmnCdMgmtVo();
		paramVo.setGrpId("CMN0258");
		List<EgovMap> resultList = (List<EgovMap>) cmnCdMgmtMapper.getCommCombo(paramVo);
		
		for(int i = 0; i < resultList.size(); i++) {
			if(rwdMemberVO.getSbscRwdProdNm().equals(resultList.get(i).get("value"))) {
				rwdMemberVO.setRwdProdNm(String.valueOf(resultList.get(i).get("text")));
			}
		}
		
		// SMS 템플릿 제목,내용 가져오기
		MsgQueueReqVO msgVO = smsMgmtMapper.getTemplateText(templateId);
		msgVO.setText((msgVO.getTemplateText())
				.replaceAll(Pattern.quote("#{rwdProdNm}"), rwdMemberVO.getRwdProdNm()));
		msgVO.setMobileNo(rwdMemberVO.getSubscriberNo());
		msgVO.setTemplateId(templateId);
		
		// SMS 저장
		smsMgmtMapper.insertMsgTmpQueue(msgVO);
		
		SmsSendVO sendVO = new SmsSendVO();
		sendVO.setSendDivision("MSP");
		sendVO.setTemplateId(templateId);
		sendVO.setMseq(msgVO.getMseq());
		sendVO.setRequestTime(msgVO.getRequestTime());
		sendVO.setReqId(rwdMemberVO.getSessionUserId());
		
		// 발송내역 저장
		smsMgmtMapper.insertSmsSendMst(sendVO);
	}
    
    /**
     * @Description : 보상서비스 청구수납 목록 조회
     * @Param  : paramMap
     * @Return : List
     * @Author : 김동혁
     * @Create Date : 2023. 5. 12.
     */
    public List<?> getRwdBillPayList(Map<String, Object> paramMap){
        // 필수조건 체크
        if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
            if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
                throw new MvnoRunException(-1, "검색값 누락");
            }
        } else {
            if(paramMap.get("searchBillYm") == null || "".equals(paramMap.get("searchBillYm"))){
                throw new MvnoRunException(-1, "청구월 누락");
            }
        }
        
        List<EgovMap> list = (List<EgovMap>) rwdMemberMapper.getRwdBillPayList(paramMap);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }
    
    /**
     * @Description : 보상서비스 청구수납 목록 엑셀 다운로드
     * @Param  : paramMap
     * @Return : List
     * @Author : 김동혁
     * @Create Date : 2023. 5. 12.
     */
    public List<?> getRwdBillPayListByExcel(Map<String, Object> paramMap){
        // 필수조건 체크
        if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
            if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
                throw new MvnoRunException(-1, "검색값 누락");
            }
        } else {
            if(paramMap.get("searchBillYm") == null || "".equals(paramMap.get("searchBillYm"))){
                throw new MvnoRunException(-1, "청구월 누락");
            }
        }
        
        List<EgovMap> list = (List<EgovMap>) rwdMemberMapper.getRwdBillPayListByExcel(paramMap);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }

    /**
     * @Description : 보상서비스 권리실행 접수현황 조회
     * @Param  : paramMap
     * @Return : List
     * @Author : 김동혁
     * @Create Date : 2023. 5. 12.
     */
    public List<?> getRwdMemCmpnList(Map<String, Object> paramMap){
        // 필수조건 체크
        if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
            if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
                throw new MvnoRunException(-1, "검색값 누락");
            }
        } else {
            if(paramMap.get("searchFromDt") == null || "".equals(paramMap.get("searchFromDt"))){
                throw new MvnoRunException(-1, "시작일자 누락");
            }
            if(paramMap.get("searchToDt") == null || "".equals(paramMap.get("searchToDt"))){
                throw new MvnoRunException(-1, "종료일자 누락");
            }
        }
        
        List<EgovMap> list = (List<EgovMap>) rwdMemberMapper.getRwdMemCmpnList(paramMap);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }
    
    /**
     * @Description : 보상서비스 권리실행 접수현황 엑셀 다운로드
     * @Param  : paramMap
     * @Return : List
     * @Author : 김동혁
     * @Create Date : 2023. 5. 12.
     */
    public List<?> getRwdMemCmpnListByExcel(Map<String, Object> paramMap){
        // 필수조건 체크
        if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
            if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
                throw new MvnoRunException(-1, "검색값 누락");
            }
        } else {
            if(paramMap.get("searchFromDt") == null || "".equals(paramMap.get("searchFromDt"))){
                throw new MvnoRunException(-1, "시작일자 누락");
            }
            if(paramMap.get("searchToDt") == null || "".equals(paramMap.get("searchToDt"))){
                throw new MvnoRunException(-1, "종료일자 누락");
            }
        }
        
        List<EgovMap> list = (List<EgovMap>) rwdMemberMapper.getRwdMemCmpnListByExcel(paramMap);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }
    
    /**
     * @Description : 보상서비스 보상지급현황 조회
     * @Param  : paramMap
     * @Return : List
     * @Author : 김동혁
     * @Create Date : 2023. 5. 12.
     */
    public List<?> getRwdMemPayList(Map<String, Object> paramMap){
        // 필수조건 체크
        if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
            if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
                throw new MvnoRunException(-1, "검색값 누락");
            }
        } else {
            if(paramMap.get("searchFromDt") == null || "".equals(paramMap.get("searchFromDt"))){
                throw new MvnoRunException(-1, "시작일자 누락");
            }
            if(paramMap.get("searchToDt") == null || "".equals(paramMap.get("searchToDt"))){
                throw new MvnoRunException(-1, "종료일자 누락");
            }
        }
        
        List<EgovMap> list = (List<EgovMap>) rwdMemberMapper.getRwdMemPayList(paramMap);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }
    
    /**
     * @Description : 보상서비스 보상지급현황 엑셀 다운로드
     * @Param  : paramMap
     * @Return : List
     * @Author : 김동혁
     * @Create Date : 2023. 5. 12.
     */
    public List<?> getRwdMemPayListByExcel(Map<String, Object> paramMap){
        // 필수조건 체크
        if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
            if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
                throw new MvnoRunException(-1, "검색값 누락");
            }
        } else {
            if(paramMap.get("searchFromDt") == null || "".equals(paramMap.get("searchFromDt"))){
                throw new MvnoRunException(-1, "시작일자 누락");
            }
            if(paramMap.get("searchToDt") == null || "".equals(paramMap.get("searchToDt"))){
                throw new MvnoRunException(-1, "종료일자 누락");
            }
        }
        
        List<EgovMap> list = (List<EgovMap>) rwdMemberMapper.getRwdMemPayListByExcel(paramMap);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }
    
    /**
	* @Description : 보상서비스 도래대상 목록
	* @Param  : RwdMemberVO rwdMemberVO, Map<String, Object> paramMap
	* @Author : wooki
	* @CreateDate : 2023.05.11
	* @Return : List<RwdMemberVO>
	*/
	public List<RwdMemberVO> getCmpnMemberList(RwdMemberVO rwdMemberVO, Map<String, Object> paramMap){
		
		if ( (rwdMemberVO.getSearchCd() == null || "".equals(rwdMemberVO.getSearchCd()))
			&& (rwdMemberVO.getSearchTermCd() == null || "".equals(rwdMemberVO.getSearchTermCd()))
			&& ((rwdMemberVO.getSearchFromDt() == null || "".equals(rwdMemberVO.getSearchFromDt()))
			|| (rwdMemberVO.getSearchToDt() == null || "".equals(rwdMemberVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "도래일자 정보가 존재 하지 않습니다.");
		}
		
		List<RwdMemberVO> rsltAryListVo = rwdMemberMapper.getCmpnMemberList(rwdMemberVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	/**
	* @Description : 보상서비스 도래대상 목록 엑셀 다운로드
	* @Param  : RwdMemberVO rwdMemberVO, Map<String, Object> paramMap
	* @Author : wooki
	* @CreateDate : 2023.05.12
	* @Return : List<RwdMemberVO>
	*/
	public List<RwdMemberVO> getCmpnMemberListByExcel(RwdMemberVO rwdMemberVO, Map<String, Object> paramMap){
		
		if ( (rwdMemberVO.getSearchCd() == null || "".equals(rwdMemberVO.getSearchCd()))
			&& (rwdMemberVO.getSearchTermCd() == null || "".equals(rwdMemberVO.getSearchTermCd()))
			&& ((rwdMemberVO.getSearchFromDt() == null || "".equals(rwdMemberVO.getSearchFromDt()))
			|| (rwdMemberVO.getSearchToDt() == null || "".equals(rwdMemberVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "도래일자 정보가 존재 하지 않습니다.");
		}
		
		List<RwdMemberVO> rsltAryListVo = rwdMemberMapper.getCmpnMemberListByExcel(rwdMemberVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
}
