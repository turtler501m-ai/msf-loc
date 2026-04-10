package com.ktis.msp.pps.agencycustmgmt.service;


import com.ktds.crypto.annotation.Crypto;
import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.pps.agencycustmgmt.mapper.PpsAgencyCustMgmtMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @Class Name : PpsAgencyOpenMgmtService
 * @Description : 대리점 개통조회
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */

@Service
public class PpsAgencyCustMgmtService  extends ExcelAwareService {

	@Autowired
	private PpsAgencyCustMgmtMapper  agencyCustMgmtMapper;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;


	/**
	 * 선불대리점  개통관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@Crypto(decryptName="DBMSDec", fields = {"userSsn", "adSsn","cmsUserSsn","customerSsn"})
	public List<?> getAgencyOpenInfoMngList(Map<String, Object> pRequestParamMap) {


		//List<?> resultList = new ArrayList<PpsCustomerVo>();
		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		resultList = (List<EgovMap>)agencyCustMgmtMapper.getAgencyOpenInfoMngList(pRequestParamMap);
				
		for( EgovMap map : resultList  ){
			  
			if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
				String contractNum =  map.get("contractNum").toString().trim();
				if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
				{
					StringBuffer sb = new StringBuffer();
					String tmp = "^javaScript:goAgentCustInfoData("+contractNum+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
					sb.append(contractNum).append(tmp);
					map.put("contractNumLink",sb.toString());
				}
			}
			
		}

		return resultList;
	}
	
	

	/**
	 * 선불 대리점 개통관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Crypto(decryptName="DBMSDec", fields = {"userSsn", "adSsn","cmsUserSsn","customerSsn"})
	public void getAgencyOpenInfoMngListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","이름","CTN","모델명","개통일자","상태","선/후불","잔액","판매점코드","판매점명","국적","요금제","식별구분");
		param.setStrValue("CONTRACT_NUM","SUB_LINK_NAME","SUBSCRIBER_NO","MODEL_NAME","ENTER_DATE","SUB_STATUS_NM","SERVICE_TYPE_NM","BASIC_REMAINS","AGENT_SALE_ID","AGENT_SALE_NM","AD_NATION_NM","SERVICE_NM","CUST_IDNT_NO_IND_CD_NM");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000,5000, 5000, 5000, 5000,5000,5000,5000,5000);
		param.setIntLen(0, 0,0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
		param.setSheetName("개통내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agencycustmgmt.mapper.PpsAgencyCustMgmtMapper.getAgencyOpenInfoMngListExcel", pRequestParamMap, param, handler);
		String fileName = "대리점개통정보_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 선불대리점  개통관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@Crypto(decryptName="DBMSDec", fields = {"userSsn", "adSsn","cmsUserSsn","customerSsn"})
	public List<?> getAgencyOpenInfoMngDtlList(Map<String, Object> pRequestParamMap) {


		//List<?> resultList = new ArrayList<PpsCustomerVo>();
		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		resultList = (List<EgovMap>)agencyCustMgmtMapper.getAgencyOpenInfoMngDtlList(pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 선불대리점  서식지 등록여부 검색
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getAgencyOpenInfoAgentList(Map<String, Object> pRequestParamMap) {


		//List<?> resultList = new ArrayList<PpsCustomerVo>();
		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		resultList = (List<EgovMap>)agencyCustMgmtMapper.getAgencyOpenInfoAgentList(pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 대리점 고객 cms정보조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Crypto(decryptName="DBMSDec", fields = {"cmsAccount"})
	public List<?> getPpsAgencyUserCmsInfo(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		resultList = (List<EgovMap>)agencyCustMgmtMapper.getPpsAgencyUserCmsInfo(pRequestParamMap);

		return resultList;
		
		
	}
	
	/**
	 * 선불 대리점 CMS설정 요청
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getAgencyPpsAgentCmsReq(Map<String, Object> pRequestParamMap){
		
		agencyCustMgmtMapper.getAgencyPpsAgentCmsReq(pRequestParamMap);
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
