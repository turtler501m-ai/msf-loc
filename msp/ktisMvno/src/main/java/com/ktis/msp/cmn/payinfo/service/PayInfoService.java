package com.ktis.msp.cmn.payinfo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.payinfo.mapper.PayInfoMapper;
import com.ktis.msp.cmn.payinfo.vo.PayInfoDtlVO;
import com.ktis.msp.cmn.payinfo.vo.PayInfoMstVO;
import com.ktis.msp.cmn.payinfo.vo.PayInfoNonBindVO;
import com.ktis.msp.cmn.payinfo.vo.PayInfoVO;
import com.ktis.msp.org.common.vo.FileInfoVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;
/**
 * @Class Name : PayInfoService
 * @Description : PayInfo service
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.07.11  장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2016. 7. 11.
 */
@Service
public class PayInfoService extends BaseService {
    
	/** PayInfo mapper */
    @Autowired
    private PayInfoMapper payInfoMapper;
    
    /** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
    
    /** prefix log */
    public PayInfoService() {
        setLogPrefix("[PayInfoService] ");
    }
    
    /**
     * @Description : 대리점 등록 리스트를 보여준다.
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    public List<?> orgPayInfoList(PayInfoVO payInfoVO, Map<String, Object> paramMap){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("대리점 등록  리스트 조회 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        //return payInfoMapper.orgPayInfoList(payInfoVO);
        
        List<?> payInfoVoList = new ArrayList<PayInfoVO>();
		
        payInfoVoList = payInfoMapper.orgPayInfoList(payInfoVO);

        logger.info(generateLogMsg("payInfoDtlVO==") + payInfoVO.toString());
        
		paramMap.put("SESSION_USER_ID", payInfoVO.getSessionUserId());
		
		maskingService.setMask(payInfoVoList, maskingService.getMaskFields(), paramMap);
		
		return payInfoVoList;
    }
    
    /**
     * @Description : 대리점 등록 리스트 엑셀 다운로드.
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    public List<?> orgPayInfoListExcel(PayInfoVO payInfoVO, Map<String, Object> paramMap){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("대리점 등록 리스트 엑셀 다운로드 START."));
        logger.info(generateLogMsg("================================================================="));
        //return payInfoMapper.orgPayInfoList(payInfoVO);
        
        List<?> payInfoVoList = new ArrayList<PayInfoVO>();
		
        payInfoVoList = payInfoMapper.orgPayInfoListExcel(payInfoVO);

        logger.info(generateLogMsg("payInfoDtlVO==") + payInfoVO.toString());
        
		paramMap.put("SESSION_USER_ID", payInfoVO.getSessionUserId());
		
		maskingService.setMask(payInfoVoList, maskingService.getMaskFields(), paramMap);
		
		return payInfoVoList;
    }
    
    /**
     * @Description : 개통 고객 리스트 찾기
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    public List<?> custmgmtLists(PayInfoVO payInfoVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("대리점 등록  리스트 조회 서비스 START."));
        logger.info(generateLogMsg("================================================================="));

        List<?> list = new ArrayList<PayInfoVO>();

        list = payInfoMapper.custmgmtLists(payInfoVO);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("SESSION_USER_ID", payInfoVO.getSessionUserId());
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
        return list;
    }

    /**
     * @Description : 고객 찾기
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 김동혁
     * @Create Date : 2024. 09. 21.
     */
    public List<?> getCustomerInfo(PayInfoVO payInfoVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("고객 찾기 START."));
        logger.info(generateLogMsg("================================================================="));

        List<?> list = new ArrayList<PayInfoVO>();

        list = payInfoMapper.getCustomerInfo(payInfoVO);

        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("SESSION_USER_ID", payInfoVO.getSessionUserId());

        maskingService.setMask(list, maskingService.getMaskFields(), paramMap);

        return list;
    }

    /**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public String getFileNm(String fileId){
		
		
		String returnFileId = payInfoMapper.getFileNm(fileId);
		
		return returnFileId;
	}  
	
	/**
     * @Description : 자동이체동의서상세 - 첨부 파일 명을 찾아온다.
     * @Param  : FileInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    public String getFileNmDtl(String fileId){


        String returnFileId = payInfoMapper.getFileNmDtl(fileId);

        return returnFileId;
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
        String strSeq = payInfoMapper.getFileNmSeq();
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
    public int insertOffline(PayInfoVO payinfoVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("고객 정보 생성 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        
        try{
        	resultCnt = payInfoMapper.insertOffline(payinfoVO);
            
            logger.info(generateLogMsg("등록건수 = " + resultCnt));
            
        }catch (Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
    /**
     * @Description : 합본 미대상 이미지 정보 이력 INSERT
     * @Param  : PayInfoVO
     * @Return : int
     * @Author : 김용문
     * @Create Date : 2016. 8. 01.
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertCmnKftcNonBindFileHst(PayInfoNonBindVO payinfononbindvo){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("합본 미대상 이미지 정보 이력 INSERT START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        
        try{
        	resultCnt = payInfoMapper.insertCmnKftcNonBindFileHst(payinfononbindvo);
            
            logger.info(generateLogMsg("등록건수 = " + resultCnt));
            
        }catch (Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
    /**
     * @Description : 선택한 고객 정보 수정
     * @Param  : PayInfoVO
     * @Return : int
     * @Author : 김용문
     * @Create Date : 2016. 7. 18.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateOffline(PayInfoVO payinfoVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("고객 정보 수정 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        
        try{
        	resultCnt = payInfoMapper.updateOffline(payinfoVO);
            
            logger.info(generateLogMsg("수정건수 = " + resultCnt));
            
        }catch (Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
    /**
     * @Description : 자동이체동의서상세 - 선택한 고객 정보 등록
     * @Param  : PayInfoDtlVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateDtl(PayInfoDtlVO payinfoDtlVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("자동이체동의서상세 - 고객 정보 생성 서비스 START."));
        logger.info(generateLogMsg("================================================================="));

        int resultCnt = 0;

        try{
            resultCnt = payInfoMapper.updateDtl(payinfoDtlVO);

            logger.info(generateLogMsg("등록건수 = " + resultCnt));

        }catch (Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
    /**
     * @Description : 자동이체동의서상세 - 선택한 고객 정보 등록
     * @Param  : PayInfoDtlVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    @Transactional(rollbackFor=Exception.class)
    public void mspPayinfoImg(HashMap<String, String> param){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("서식지 합본"));
        logger.info(generateLogMsg("================================================================="));

        try{
            payInfoMapper.mspPayinfoImg(param);
//            logger.info("resultCd  : " + param.get("o_code"));
//    		logger.info("resultMsg  : " + param.get("o_msg"));
        }catch (Exception e){
            logger.error(e);
        }
    }
    
    /**
     * @Description : 파일 정보를 등록한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertFile(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("insertFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = payInfoMapper.insertFile(fileInfoVO);
		
        return resultCnt;
    }
    
    public List<?> getFile1(PayInfoVO payInfoVO){
		
		List<?> payinfoMgmtVoList = new ArrayList();
		
		payinfoMgmtVoList = payInfoMapper.getFile1(payInfoVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("SESSION_USER_ID", payInfoVO.getSessionUserId());
		
		maskingService.setMask(payinfoMgmtVoList, maskingService.getMaskFields(), paramMap);
		
		return payinfoMgmtVoList;
	}	
    
    /**
     * @Description : 자동이체동의서상세 - 등록된 파일정보를 가져온다
     * @Param  : PayInfoDtlVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    public List<?> getFileDtl1(PayInfoDtlVO payInfoDtlVO){

        List<?> payinfoMgmtVoList = new ArrayList();

        payinfoMgmtVoList = payInfoMapper.getFileDtl1(payInfoDtlVO);
        
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("SESSION_USER_ID", payInfoDtlVO.getSessionUserId());
		
		maskingService.setMask(payinfoMgmtVoList, maskingService.getMaskFields(), paramMap);

        return payinfoMgmtVoList;
    }   

    /**
     * @Description : 배치 파일 마스터 정보를 가져온다.
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    public List<?> payInfoMstList(PayInfoMstVO payInfoMstVO, Map<String, Object> paramMap){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("배치 파일 마스터 정보를 가져온다. START."));
        logger.info(generateLogMsg("================================================================="));
        
		@SuppressWarnings("unchecked")
		List<EgovMap> resultList = (List<EgovMap>)payInfoMapper.payInfoMstList(payInfoMstVO);
		for( EgovMap map : resultList){

			String kftcMstSeqStr = map.get("kftcMstSeqStr").toString();
			if(!StringUtils.isBlank(map.get("kftcMstSeqStr").toString())&& !kftcMstSeqStr.equals("0") )
			{
				StringBuffer sb = new StringBuffer();
		    	sb.append(kftcMstSeqStr).append("^javaScript:goFileInfoData("+kftcMstSeqStr+");^_self");
		    	map.put("kftcMstSeqStr", sb.toString());
			}
		}
		
        logger.info(generateLogMsg("payInfoDtlVO==") + payInfoMstVO.toString());
        
		paramMap.put("SESSION_USER_ID", payInfoMstVO.getSessionUserId());
		
		maskingService.setMask(resultList, maskingService.getMaskFields(), paramMap);
		
        return resultList;
    }        
    
    /**
     * @Description : 배치 파일 상세 정보를 가져온다.
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    public List<?> payInfoDtlList(PayInfoDtlVO payInfoDtlVO, Map<String, Object> paramMap){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("자동이체동의서 상세 결과 조회 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        //return payInfoMapper.payInfoDtlList(payInfoDtlVO);

        List<?> payInfoDtVoList = new ArrayList<PayInfoDtlVO>();

        payInfoDtVoList = payInfoMapper.payInfoDtlList(payInfoDtlVO);

        logger.info(generateLogMsg("payInfoDtlVO==") + payInfoDtlVO.toString());
        
		paramMap.put("SESSION_USER_ID", payInfoDtlVO.getSessionUserId());
				
        maskingService.setMask(payInfoDtVoList, maskingService.getMaskFields(), paramMap);

        return payInfoDtVoList;
    }
    
    /**
	 * @Description : 첨부 파일 삭제
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public int deleteFile(String fileId){
		
		int returnFileId = payInfoMapper.deleteFile(fileId);
		
		return returnFileId;
	}  
	
	/**
     * @Description : 자동이체동의서상세 - 첨부 파일 삭제
     * @Param  : FileInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFileDtl(String fileId, String strUserNM){
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("kftcDtlSeq", fileId);
    	map.put("sessionUserId", strUserNM);
    	
    	
        int returnFileId = payInfoMapper.updateFileDtl(map);

        return returnFileId;
    } 
	
    /**
     * @Description : 파일 정보를 등록한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateApprovalYn(PayInfoMstVO payInfoMstVO) throws MvnoServiceException {
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateApprovalYn START."));
		logger.info(generateLogMsg("================================================================="));
		
		//주석요청
//		if("0000".equals(payInfoMstVO.getApprovalCd())) {
//			int failCnt = payInfoMapper.failCnt(payInfoMstVO);
//	
//			logger.info(generateLogMsg("failCnt===" + failCnt));
//			
//			if(failCnt > 0)
//			{
//				throw new MvnoServiceException("파일 미등록 또는 승인처리가 되지 않았습니다. <br/>상세 화면에서 확인하세요.");
//			}
//		}
		
		int resultCnt = payInfoMapper.updateApprovalYn(payInfoMstVO);
		
		
        return resultCnt;
    }
    
    /**
     * @Description : KT 배치 파일 상세 정보를 가져온다.
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    public List<?> payInfoKtList(PayInfoDtlVO payInfoDtlVO, Map<String, Object> paramMap){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("KT 연동 자료 상세 조회(대리점/고객센터용) 서비스 START."));
        logger.info(generateLogMsg("================================================================="));

        List<?> payInfoDtVoList = new ArrayList<PayInfoDtlVO>();

        payInfoDtVoList = payInfoMapper.payInfoKtList(payInfoDtlVO);

        logger.info(generateLogMsg("payInfoDtlVO==") + payInfoDtlVO.toString());
        
		paramMap.put("SESSION_USER_ID", payInfoDtlVO.getSessionUserId());
		
        maskingService.setMask(payInfoDtVoList, maskingService.getMaskFields(), paramMap);

        return payInfoDtVoList;
    }
    
    /**
     * @Description : 개통/변경 등록서 검증 처리
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateCheYn(PayInfoVO payInfoVO){  
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateCheYn START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = payInfoMapper.updateCheYn(payInfoVO);
		
        return resultCnt;
    }

    
    /**
     * @Description : 개통/변경 등록서 검증 처리
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateAppYn(PayInfoDtlVO payInfoDtlVO){  
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateAppYn START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = payInfoMapper.updateAppYn(payInfoDtlVO);
		
        return resultCnt;
    }

    /**
     * @Description : 
     * @Param  : HashMap<String, String> param
     * @Return : void
     * @Author : 김용문
     * @Create Date : 2016. 7. 29.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<String> getImgBindTarget(String strGrpId){
        logger.info("=================================================================");
        logger.info("서식지 합본 대상 확장자 조회");
        logger.info("=================================================================");
        List<String> list = new ArrayList<String>();
        try{
        	list = payInfoMapper.getImgBindTarget(strGrpId);
        }catch (Exception e){
            logger.error(e);
        }
        return list;
    }
    
    /**
     * @Description : 
     * @Param  : PayInfoDtlVO payinfodtlvo
     * @Return : int
     * @Author : 김용문
     * @Create Date : 2016. 7. 29.
     */
    @Transactional(rollbackFor=Exception.class)
    public int getDtlReadyChk(PayInfoDtlVO payinfodtlvo){
        logger.info("=================================================================");
        logger.info("DTL 현행화 체크 : " + payinfodtlvo.toString());
        logger.info("=================================================================");
        int iRtn = 0;
        try{
        	iRtn = payInfoMapper.getDtlReadyChk(payinfodtlvo);
        }catch (Exception e){
            logger.error(e);
        }
        return iRtn;
    }
    
    /**
     * @Description : 은행코드값을 가져온다.
     * @Param  : void
     * @Return : void
     * @Author : 김용문
     * @Create Date : 2016. 8. 01.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<?> getBankCdComboList() {
    	
    	List<?> comboList = new ArrayList<String>();
    	
    	comboList = payInfoMapper.getBankCdComboList();
    	
    	return comboList;
    }
    
    /**
     * @Description : 증거파일 목록 조회(파일유형에 따라서 조회)
     * @Param  : String
     * @Return : List<?>
     * @Author : 김용문
     * @Create Date : 2016. 8. 19.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<String> getEvidenceFileExtList(String strEvidenceTypeCd) {
    	
    	List<String> comboList = new ArrayList<String>();
    	
    	comboList = payInfoMapper.getEvidenceFileExtList(strEvidenceTypeCd);
    	
    	return comboList;
    }
    
    /**
     * @Description : 증거파일 리사이징 확장자 목록 조회
     * @Param  : String
     * @Return : List<?>
     * @Author : 김용문
     * @Create Date : 2016. 8. 19.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<String> getEvidenceFileResizeExtList(String strEtc3) {
    	
    	List<String> comboList = new ArrayList<String>();
    	
    	comboList = payInfoMapper.getEvidenceFileResizeExtList(strEtc3);
    	
    	return comboList;
    }
    
    /**
     * @Description : 증거파일 용량 조회
     * @Param  : String
     * @Return : long
     * @Author : 김용문
     * @Create Date : 2016. 8. 19.
     */
    @Transactional(rollbackFor=Exception.class)
    public long getEvidenceFileSize(String strEvidenceTypeCd) {
    	
    	long lEvidenceFileSize = 0;
    	
    	lEvidenceFileSize = payInfoMapper.getEvidenceFileSize(strEvidenceTypeCd);
    	
    	return lEvidenceFileSize;
    }
    
    /**
     * @Description : 증거파일 용량 체크 대상 체크
     * @Param  : String
     * @Return : String
     * @Author : 김용문
     * @Create Date : 2016. 8. 19.
     */
    @Transactional(rollbackFor=Exception.class)
    public String getEvidenceFileSizeTargetChk(String strExt) {
    	
    	String strEvidenceFileSizeTargetChk = "";
    	
    	strEvidenceFileSizeTargetChk = payInfoMapper.getEvidenceFileSizeTargetChk(strExt);
    	
    	return strEvidenceFileSizeTargetChk;
    }
    
    /**
     * @Description : 증거파일 용량 메시지 조회
     * @Param  : String
     * @Return : long
     * @Author : 김용문
     * @Create Date : 2016. 8. 19.
     */
    @Transactional(rollbackFor=Exception.class)
    public String getEvidenceFileSizeMsg(String strEvidenceTypeCd) {
    	
    	String strEvidenceFileSizeMsg = "";
    	
    	strEvidenceFileSizeMsg = payInfoMapper.getEvidenceFileSizeMsg(strEvidenceTypeCd);
    	
    	return strEvidenceFileSizeMsg;
    }
    
    /**
     * @Description : 변경동의서관리 데이터와 현행화 MST 목록 조회
     * @Param  : void
     * @Return : List<Integer>
     * @Author : 김용문
     * @Create Date : 2016. 9. 02.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<Integer> getPayinfoDataSynMST() {
    	logger.info("=========================================================");
		logger.info("변경동의서관리 데이터와 현행화 : MST 조회.");
		logger.info("=========================================================");
    	List<Integer> arrSeqs = new ArrayList<Integer>();
    	
    	arrSeqs = payInfoMapper.getPayinfoDataSynMST();
    	
    	return arrSeqs;
    }
    
    /**
     * @Description : 변경동의서관리 데이터와 현행화 DTL 목록 조회
     * @Param  : void
     * @Return : List<Integer>
     * @Author : 김용문
     * @Create Date : 2016. 9. 02.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<PayInfoDtlVO> getPayinfoDataSynDTL(int iMstSeq) {
    	logger.info("=========================================================");
		logger.info("변경동의서관리 데이터와 현행화 : DTL 조회.");
		logger.info("=========================================================");
    	List<PayInfoDtlVO> arrDtls = new ArrayList<PayInfoDtlVO>();
    	
    	arrDtls = payInfoMapper.getPayinfoDataSynDTL(iMstSeq);
    	
    	return arrDtls;
    }
    
    /**
     * @Description : 변경동의서관리 데이터와 현행화 DTL UPDATE
     * @Param  : PayInfoDtlVO
     * @Return : int
     * @Author : 김용문
     * @Create Date : 2016. 9. 02.
     */
    @Transactional(rollbackFor=Exception.class)
    public int setPayinfoDataSynDTL(PayInfoDtlVO payinfodtlvo) {
    	logger.info("=========================================================");
		logger.info("변경동의서관리 데이터와 현행화 : DTL UPDATE.");
		logger.info("=========================================================");
    	
    	int iUpdateDtl = payInfoMapper.setPayinfoDataSynDTL(payinfodtlvo);
    	
    	return iUpdateDtl;
    }
    
    /**
     * @Description : 파일이력정보를 가져온다.
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    public List<?> fileList(PayInfoVO payInfoVO, Map<String, Object> paramMap){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일이력 조회 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> payInfoVoList = new ArrayList<PayInfoVO>();
		
        payInfoVoList = payInfoMapper.fileList(payInfoVO);
		
        logger.info(generateLogMsg("payInfoDtlVO==") + payInfoVO.toString());
        
		paramMap.put("SESSION_USER_ID", payInfoVO.getSessionUserId());
		
		maskingService.setMask(payInfoVoList, maskingService.getMaskFields(), paramMap);
		
		return payInfoVoList;
    }    
    
    /**
     * @Description : 관리화면 엑셀 다운로드
     * @Param  : payInfoMstVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.09.01
     */
    public List<?> selectPayinfoMstEx(PayInfoMstVO payInfoMstVO){

		List<?> payInfoList = new ArrayList<PayInfoMstVO>();
		
		payInfoList = payInfoMapper.selectPayinfoMstEx(payInfoMstVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
        
		paramMap.put("SESSION_USER_ID", payInfoMstVO.getSessionUserId());
		
		maskingService.setMask(payInfoList, maskingService.getMaskFields(), paramMap);
		
		return payInfoList;
    	
    }
    
    

    /**
     * @Description : 관리화면상세 엑셀 다운로드
     * @Param  : payInfoDtlVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.09.01
     */
    public List<?> selectPayinfoDtlEx(PayInfoDtlVO payInfoDtlVO, Map<String, Object> pRequestParamMap){

		List<?> payInfoDtlList = new ArrayList<PayInfoDtlVO>();
		
		payInfoDtlList = payInfoMapper.selectPayinfoDtlEx(payInfoDtlVO);
		
        logger.info(generateLogMsg("payInfoDtlVO==") + payInfoDtlVO.toString());
        
        pRequestParamMap.put("SESSION_USER_ID", payInfoDtlVO.getSessionUserId());
				
        maskingService.setMask(payInfoDtlList, maskingService.getMaskFields(), pRequestParamMap);
		
		
		return payInfoDtlList;
    	
    }

    
    /**
     * @Description : KT연동자료관리(대리점/고객센터용) 엑셀 다운로드
     * @Param  : payInfoDtlVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.09.01
     */
    public List<?> selectPayinfoKtEx(PayInfoDtlVO payInfoDtlVO, Map<String, Object> pRequestParamMap){

		List<?> payInfoDtlList = new ArrayList<PayInfoDtlVO>();
		
		payInfoDtlList = payInfoMapper.selectPayinfoKtEx(payInfoDtlVO);

        logger.info(generateLogMsg("payInfoDtlVO==") + payInfoDtlVO.toString());
        
        pRequestParamMap.put("SESSION_USER_ID", payInfoDtlVO.getSessionUserId());
		
        maskingService.setMask(payInfoDtlList, maskingService.getMaskFields(), pRequestParamMap);
		
		
		return payInfoDtlList;
    	
    }
    

    
    /**
     * @Description : 동의서 파일 이력 엑셀 다운로드
     * @Param  : payInfoVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.09.01
     */
    public List<?> searchFileInfoEx(PayInfoVO payInfoVO, Map<String, Object> pRequestParamMap){

		List<?> payInfoList = new ArrayList<PayInfoVO>();
		
		payInfoList = payInfoMapper.searchFileInfoEx(payInfoVO);
		
        logger.info(generateLogMsg("payInfoDtlVO==") + payInfoVO.toString());
        
        pRequestParamMap.put("SESSION_USER_ID", payInfoVO.getSessionUserId());
		
		maskingService.setMask(payInfoList, maskingService.getMaskFields(), pRequestParamMap);
		
		
		return payInfoList;
    	
    }
    
    /**
     * @Description : 은행코드값을 가져온다.
     * @Param  : void
     * @Return : void
     * @Author : 박준성
     * @Create Date : 2017. 08. 10.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<?> getReqBankComboList() {
    	
    	List<?> comboList = new ArrayList<String>();
    	
    	comboList = payInfoMapper.getReqBankComboList();
    	
    	return comboList;
    }
    
    
    /**
     * @Description : 카드정보를 가져온다.
     * @Param  : void
     * @Return : void
     * @Author : 박준성
     * @Create Date : 2017. 09. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<?> getReqCardComboList(PayInfoVO vo) {
    	
    	List<?> comboList = new ArrayList<String>();
    	
    	comboList = payInfoMapper.getReqCardComboList(vo);
    	
    	return comboList;
    }    
    
    
    /**
	 * @Description : 동의서파일이력조회 상세 파일 명을 찾아온다.
	 * @Param  : String
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2021. 6. 14.
	 */
	public String getFileNmSec(String fileId){
		
		
		String returnFileNm = payInfoMapper.getFileNmSec(fileId);
		
		return returnFileNm;
	}
}
