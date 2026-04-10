package com.ktis.msp.pps.orgmgmt.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.annotation.Crypto;
import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;
import com.ktis.msp.pps.orgmgmt.mapper.PpsAgencyOrgMgmtMapper;
import com.ktis.msp.pps.orgmgmt.vo.FaxHstVO;
import com.ktis.msp.pps.orgmgmt.vo.PpsOrgMgmtVO;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;



/**
 * @param <PpsHdofcRcgMgmtMapper>
 * @Class Name : PpsAgencyOrgMgmtService
 * @Description : 선불 대리점 판매점관리  service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */
@Service
public class PpsAgencyOrgMgmtService extends ExcelAwareService {
	
	@Autowired
	private PpsAgencyOrgMgmtMapper ppsAgencyOrgMgmtMapper;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
    private FileDownService  fileDownService;
	
	/**
	 * 선불 대리점 판매점  목록 조회 
	 * @param pRequestParamMap
	 * @return
	 */
	@Crypto(decryptName="DBMSDec", fields = {"rprsenRrnum"})
	public List<?> getAgencyOrgMgmtList(Map<String, Object> pRequestParamMap) {
		List<?> listOrgMgmts = new ArrayList<PpsOrgMgmtVO>();
		
		listOrgMgmts = ppsAgencyOrgMgmtMapper.getAgencyOrgMgmtList(pRequestParamMap);
		
		
		return  listOrgMgmts;
	}
	
	/**
	 * 선불 대리점 판매점  목록엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Crypto(decryptName="DBMSDec", fields = {"userSsn", "adSsn","cmsUserSsn","customerSsn"})
	public void getAgencyOrgMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("판매점ID","판매점명","대표자명","대표자전화","팩스","조직상태","적용시작일","적용종료일","등록일");
		param.setStrValue("ORGN_ID","ORGN_NM","RPRSEN_NM","TELNUM","FAX","ORGN_STAT_NM","APPL_STRT_DT","APPL_CMPLT_DT","REG_DTTM");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000,5000, 5000, 5000, 5000);
		param.setIntLen(0, 0,0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("판매점내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
		File f = makeBigDataExcel("com.ktis.msp.pps.orgmgmt.mapper.PpsAgencyOrgMgmtMapper.getAgencyOrgMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "판매점내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
		//=======파일다운로드사유 로그 START==========================================================
        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");
           
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();
            
            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
            
            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
        }
        //=======파일다운로드사유 로그 END==========================================================
        
		try {
			doDownload(response, f, URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}

	}
	
	/**
	 *  대리점 조직아이디생성
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsSearchOrgnId(Map<String, Object> pRequestParamMap) throws InterruptedException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String orgnId = ppsAgencyOrgMgmtMapper.getPpsSearchOrgnId();
		resultMap.put("orgnId", orgnId);
		
		return resultMap;
	}
	
	/**
     * @Description : 조직상세 조회한다.
     * @Param  : 
     * @Return : OrgMgmtVO
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    public OrgMgmtVO ppsDetailOrgMgmt(OrgMgmtVO orgMgmtVO){
        return ppsAgencyOrgMgmtMapper.ppsDetailOrgMgmt(orgMgmtVO);
    }  
    
    /**
     * @Description : 신규 조직을 생성한다.
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="DBMSEnc", fields = {"rprsenRrnum"})
    public int ppsInsertOrgMgmt(OrgMgmtVO orgMgmtVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("insertOrgMgmt START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Date toDay = new Date();
		
		int resultCnt = ppsAgencyOrgMgmtMapper.ppsInsertOrgMgmt(orgMgmtVO);
		
		//이력 생성
		orgMgmtVO.setOrgnSeq("1");
		
		int resultCntHst = ppsAgencyOrgMgmtMapper.ppsInsertOrgMgmtHst(orgMgmtVO);
		logger.debug("resultCntHst = " + resultCntHst);
		logger.debug("orgMgmtVO.getFax() = " + orgMgmtVO.getFax());
		
		if(!StringUtils.isEmpty(orgMgmtVO.getFax()))
		{
			//FAX 정보 생성
			FaxHstVO faxMgmtVo = new FaxHstVO();
			
			faxMgmtVo.setFax(orgMgmtVO.getFax());
			faxMgmtVo.setOrgnId(orgMgmtVO.getOrgnId());
			faxMgmtVo.setRegId(orgMgmtVO.getRegId());
			faxMgmtVo.setRvisnId(orgMgmtVO.getRvisnId());
			faxMgmtVo.setApplCmpltDttm("99991231");
			faxMgmtVo.setApplStrtDttm(KtisUtil.toStr(toDay, KtisUtil.DATE_SHORT));
			
			int resultFaxCnt = this.ppsInsertFaxMgmt(faxMgmtVo);
			logger.debug("resultFaxCnt = " + resultFaxCnt);
		}
		
		// 2014-12-19 추가
		// 대리점유형이력생성
		this.setAgncyTypeHst(orgMgmtVO);
				
        return resultCnt;
    }
    
    
    /**
     * @Description : Fax 추가
     * @Param  : faxHstVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 158.
     */
    @Transactional(rollbackFor=Exception.class)
    public int ppsInsertFaxMgmt(FaxHstVO faxHstVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("FAX 생성 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        String maxSeq = null;
        
        try{
            int faxMgmtCnt = ppsAgencyOrgMgmtMapper.ppsIntCntFaxMgmt(faxHstVO);
            
            if(faxMgmtCnt == 0){
                faxHstVO.setFaxSeqNum("1");
            } else {
                maxSeq = ppsAgencyOrgMgmtMapper.ppsMaxSeqFaxMgmt(faxHstVO);
                int addSeq = Integer.parseInt(maxSeq)+1;
                faxHstVO.setFaxSeqNum(Integer.toString(addSeq));
            }
            
            resultCnt = ppsAgencyOrgMgmtMapper.ppsInsertFaxMgmt(faxHstVO);
                
            if(maxSeq != null && !"".equals(maxSeq)){
                //최종 이력의 종료일을 새로 입력하는 이력정보의 시작일로 UPDATE한다
                faxHstVO.setFaxSeqNum(maxSeq);
                faxHstVO.setApplCmpltDttm(faxHstVO.getApplStrtDttm());
                resultCnt = ppsAgencyOrgMgmtMapper.ppsUpdateFaxMgmt(faxHstVO);
            }
            
            //조직 테이블의 FAX전화번호를 변경한다.
            OrgMgmtVO orgMgmtVO  = new OrgMgmtVO();
            
            orgMgmtVO.setOrgnId(faxHstVO.getOrgnId());
            orgMgmtVO.setFax(faxHstVO.getFax());
           
            int orgCnt = ppsAgencyOrgMgmtMapper.ppsUpdateOrgMgmtFax(orgMgmtVO);
            logger.info(generateLogMsg("조직 업데이트 건수 = " + orgCnt));
            
        }catch (Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
    /**
     * 대리점유형이력관리
     * @param pRequestParamMap
     */
    @Transactional(rollbackFor=Exception.class)
    public void setAgncyTypeHst(OrgMgmtVO vo){
    	logger.debug("대리점유형이력관리............." + vo);
    	
    	if(vo.getOrgnId() == null || "".equals(vo.getOrgnId())) return;
    	
    	// 대리점여부 체크
    	Map<String, Object> orgMap = ppsAgencyOrgMgmtMapper.ppsGetAgncyInfo(vo);
    	if(orgMap == null) return;
    	
    	// 대리점이력 생성
    	int i = ppsAgencyOrgMgmtMapper.ppsUpdateAgncyTypeHst(orgMap);
    	if(i ==0){
    		// 기존 이력이 없으므로 변경일 기준으로 이력을 생성한다.
    		ppsAgencyOrgMgmtMapper.ppsInsertAgncyTypeHstFst(orgMap);
    	}
    	
    	// 9991231 이력 생성
    	ppsAgencyOrgMgmtMapper.ppsInsertAgncyTypeHst(orgMap);
    }
    
    /**
     * @Description : 조직 정보를 업데이트한다.
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Crypto(encryptName="DBMSEnc", fields = {"rprsenRrnum"}, throwable=true)
	@Transactional(rollbackFor=Exception.class)
	public int updateOrgMgmtMasking(OrgMgmtVO orgMgmtVO){

		int resultCnt = ppsAgencyOrgMgmtMapper.ppsUpdateOrgMgmtMasking(orgMgmtVO);
        return resultCnt;
    }
    
    /**
     * @Description : 조직 정보를 업데이트한다.
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	@Crypto(encryptName="DBMSEnc", fields = {"rprsenRrnum"}, throwable=true)
	@Transactional(rollbackFor=Exception.class)
	public int updateOrgMgmt(OrgMgmtVO orgMgmtVO) throws MvnoServiceException {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateOrgMgmt START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Date toDay = new Date();
		
		//조직 가동점 체크로직
		if(orgMgmtVO.getOrgnLvlCd().equals("10") && orgMgmtVO.getOrgnStatCd().equals("4"))
		{
			int valdCnt = 0;
			valdCnt = ppsAgencyOrgMgmtMapper.ppsSelectValCnt(orgMgmtVO);
			
			if(valdCnt == 0)
			{
				throw new MvnoServiceException("가상계좌를 등록하세요.");
			}
			
			int valdCnt1 = 0;
			valdCnt1 = ppsAgencyOrgMgmtMapper.ppsSelectValCnt1(orgMgmtVO);
			
			if(valdCnt1 == 0)
			{
				throw new MvnoServiceException("담보를 등록하세요.");
			}
		}
		
		int resultCnt = ppsAgencyOrgMgmtMapper.ppsUpdateOrgMgmt(orgMgmtVO);
		
		//조직 정보가 정상적으로 변경이 되면 이력을 남긴다.
		if(resultCnt == 1)
		{
			int orgrMgmtHstcnt = ppsAgencyOrgMgmtMapper.ppsListCntOrgMgmtHst(orgMgmtVO);
			
			if(orgrMgmtHstcnt == 0)
			{
				orgMgmtVO.setOrgnSeq("1");
			}
			else
			{
				String hstSeq = ppsAgencyOrgMgmtMapper.ppsListSeqOrgMgmtHst(orgMgmtVO);
				orgMgmtVO.setOrgnSeq(hstSeq);
			}
			
			//이력 생성
			int resultCntHst = ppsAgencyOrgMgmtMapper.ppsInsertOrgMgmtHst(orgMgmtVO);
			
			logger.debug("resultCntHst = " + resultCntHst);
			
//			OrgMgmtVO returnOrgMgmtVO = orgMgmtMapper.detailOrgMgmt(orgMgmtVO);
			
			logger.debug("FAX 신: " + orgMgmtVO.getFax());
			logger.debug("FAX 구: " + orgMgmtVO.getOldFax());
			String newFax = (String) KtisUtil.changeNull(orgMgmtVO.getFax(), "");
			String oldFax = (String) KtisUtil.changeNull(orgMgmtVO.getOldFax(), "");
			
			if(KtisUtil.notEquals(newFax, oldFax))
			{
				logger.debug("orgMgmtVO.getFax( = " + orgMgmtVO.getFax());
				//FAX 정보 생성
				FaxHstVO faxMgmtVo = new FaxHstVO();
				
				faxMgmtVo.setFax(orgMgmtVO.getFax());
				faxMgmtVo.setOrgnId(orgMgmtVO.getOrgnId());
				faxMgmtVo.setRegId(orgMgmtVO.getRegId());
				faxMgmtVo.setRvisnId(orgMgmtVO.getRvisnId());
				faxMgmtVo.setApplCmpltDttm("99991231");
				faxMgmtVo.setApplStrtDttm(KtisUtil.toStr(toDay, KtisUtil.DATE_SHORT));
				
				int resultFaxCnt = this.ppsInsertFaxMgmt(faxMgmtVo);
				logger.debug("resultFaxCnt = " + resultFaxCnt);
			}
			
		}
		
		// 2014-12-19 추가
		// 대리점유형이력생성
		this.setAgncyTypeHst(orgMgmtVO);
		
        return resultCnt;
    }
	
	/**
    * @Description :  조직유형을 조회한다
    * @Param  : 
    * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    public String selectOrgnTypeDtlCd(String orgnType){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("조직유형을 조회 START."));
        logger.info(generateLogMsg("================================================================="));
        
        String result = null;

        result = ppsAgencyOrgMgmtMapper.ppsSelectOrgnTypeDtlCd(orgnType);
        
        return result;
    }
    
    /**
     * @Description :  조직유형을 조회한다
     * @Param  : 
     * @Return : List<?>
      * @Author : 장익준
      * @Create Date : 2015. 6. 25.
      */
     public List<?> selectOrgnTypeDtl(String orgnType){
         logger.info(generateLogMsg("================================================================="));
         logger.info(generateLogMsg("조직유형을 조회 START."));
         logger.info(generateLogMsg("================================================================="));
         
         List<?> resultList = new ArrayList<OrgMgmtVO>();

         resultList = ppsAgencyOrgMgmtMapper.ppsSelectOrgnTypeDtl(orgnType);
         
         return resultList;
     }
     
     
     public String getCurrentTimes()
 	{
 		String currDateTime = "";
 		
 		Date currtentDate = new Date(System.currentTimeMillis());
 		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
 		currDateTime = "_"+sdfFileName.format(currtentDate);
 		
 		
 		return currDateTime;
 	}
	

}
