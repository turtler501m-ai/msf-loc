package com.ktis.msp.pps.rcgautomgmt.service;



import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsCustomerNationListVo;
import com.ktis.msp.pps.rcgautomgmt.mapper.PpsRcgAutoMgmtMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @param <PpsRcgAutoMgmtMapper>
 * @Class Name : PpsRcgAutoMgmtService
 * @Description : 선불
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.16  최초생성
 * @
 * @author : 
 * @Create Date : 2016. 3. 16.
 */

@Service
public class PpsRcgAutoMgmtService extends ExcelAwareService {

	
	
	@Autowired
	protected EgovPropertyService propertyService;

	@Autowired
	private PpsRcgAutoMgmtMapper rcgAutoMgmtMapper;
	
//	@Autowired
//	private CryptoFactory factory;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;
	
	/**
	 * 자동충전관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getRcgAutoInfoMgmtList(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>)rcgAutoMgmtMapper.getRcgAutoInfoMgmtList(pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 자동충전관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getRcgAutoInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("대리점","요금제명","개통신분증","충전구분","충전금액","등록일","변경일","처리자","설명");
		param.setStrValue("AGENT_NM","SOC_NM","CUST_IDNT_NO_IND_CD_NM","REQ_TYPE_NM","AMOUNT_NM","REG_DT","MOD_DT","ADMIN_NM","REMARK");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("자동충전관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgautomgmt.mapper.PpsRcgAutoMgmtMapper.getRcgAutoInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "자동충전관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 자동충전변경이력 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getRcgAutoHistInfoMgmtList(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>)rcgAutoMgmtMapper.getRcgAutoHistInfoMgmtList(pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 자동충전변경이력 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getRcgAutoHistInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("대리점","요금제명","개통신분증","충전구분","충전금액","등록일","변경일","변경구분","처리자","설명");
		param.setStrValue("AGENT_NM","SOC_NM","CUST_IDNT_NO_IND_CD_NM","REQ_TYPE_NM","AMOUNT_NM","REG_DT","HIS_DT","HIS_OP_CODE_NM","HIS_ADMIN_NM","REMARK");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("자동충전변경이력");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgautomgmt.mapper.PpsRcgAutoMgmtMapper.getRcgAutoHistInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "자동충전변경이력_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 자동충전관리 등록 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsRcgAutoReg(Map<String, Object> pRequestParamMap){
		rcgAutoMgmtMapper.ppsRcgAutoReg(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 대리점명 조회해서 대리점콤보박스 옵션목록 가져오기 
	 * @param pRequestParamMap
	 * @return
	 */
	public List<?> getSelectPpsRcgAutoAgentList(Map<String, Object> pRequestParamMap){
		
		List<?> resultList = new ArrayList<Map<String, Object>>();
		   
		   resultList = rcgAutoMgmtMapper.getSelectPpsRcgAutoAgentList(pRequestParamMap);
		
		
		return resultList;
	}
	
	/**
	 * 자동충전관리 요금제코드리스트 
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getPpsRcgAutoSocListData(Map<String, Object> pRequestParamMap) {
		
		List<?> resultList = new ArrayList<PpsCustomerNationListVo>();
		
		resultList = rcgAutoMgmtMapper.getPpsRcgAutoSocListData(pRequestParamMap);
		
		
		return resultList;
	}
	
	/**
	 * 자동충전관리 RS요금제 여부 확인
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getRcgAutoPps35Chk(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>)rcgAutoMgmtMapper.getRcgAutoPps35Chk(pRequestParamMap);

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
