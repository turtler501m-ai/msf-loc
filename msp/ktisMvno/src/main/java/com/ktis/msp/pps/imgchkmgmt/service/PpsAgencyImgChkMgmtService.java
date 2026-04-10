package com.ktis.msp.pps.imgchkmgmt.service;


import com.ktds.crypto.annotation.Crypto;
import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.pps.imgchkmgmt.mapper.PpsAgencyImgChkMgmtMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @param <PpsAgencyCustMgmtMapper>
 * @Class Name : PpsAgencyImgChkMgmtService
 * @Description : 대리점 검수관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */

@Service
public class PpsAgencyImgChkMgmtService  extends ExcelAwareService {

	@Autowired
	private PpsAgencyImgChkMgmtMapper  agencyImgChkMgmtMapper;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;


	/**
	 * 선불대리점  검수관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@Crypto(decryptName="DBMSDec", fields = {"userSsn", "adSsn","cmsUserSsn","customerSsn"})
	public List<?> getAgencyCustImgChkMngList(Map<String, Object> pRequestParamMap) {


		//List<?> resultList = new ArrayList<PpsCustomerVo>();
		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		resultList = (List<EgovMap>)agencyImgChkMgmtMapper.getAgencyCustImgChkMngList(pRequestParamMap);

		return resultList;
	}
	
	

	/**
	 * 선불 대리점 검수관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Crypto(decryptName="DBMSDec", fields = {"userSsn", "adSsn","cmsUserSsn","customerSsn"})
	public void getAgencyCustImgChkMngExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("검수등록번호","계약번호","개통번호","업무구분","개통일","검수등록일","검수현황","개통신청서","신분증","기타","개통서류","신분증","서명","기타","메모");
		param.setStrValue("EVENT_SEQ","CONTRACT_NUM","SUBSCRIBER_NO","MNP_IND_CD_NM","LST_COM_ACTV_DATE","REG_DT","CHK_STATUS_NM","IMG_OPEN_CNT","IMG_ID_CNT","IMG_ETC_CNT","D_STR","I_STR","S_STR","E_STR","AGENT_MEMO");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0,0,0,0,0,0,0,1,1,1,0,0,0,0,0);
		param.setSheetName("검수관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.imgchkmgmt.mapper.PpsAgencyImgChkMgmtMapper.getAgencyCustImgChkMngExcel", pRequestParamMap, param, handler);
		String fileName = "대리점검수관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 검수관리 검수수정
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsAgencyCustImgChkChgProc(Map<String, Object> pRequestParamMap) {

		pRequestParamMap.put("opCode", "AGENT");
		agencyImgChkMgmtMapper.getPpsAgencyCustImgChkChgProc(pRequestParamMap);

		return pRequestParamMap;
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
