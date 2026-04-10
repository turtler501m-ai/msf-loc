package com.ktis.msp.pps.orgmgmt.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.orgmgmt.mapper.PpsHdofcOrgMgmtMapper;
import com.ktis.msp.pps.orgmgmt.vo.PpsOnlineOrderVo;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;

import egovframework.rte.psl.dataaccess.util.EgovMap;



/**
 * @param <PpsHdofcRcgMgmtMapper>
 * @Class Name : PpsHdofcOrgMgmtService
 * @Description : 선불 조직관리  service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */

@Service
public class PpsHdofcOrgMgmtService  extends ExcelAwareService {
	
	
	@Autowired
	private PpsHdofcOrgMgmtMapper orgMgmtMapper;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;
	
	@Autowired
	private MaskingService maskingService;
	
	
	/**
	 * 선불 예치금출금내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getAgentDepositMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsAgentDepositVo>();
		
		List<EgovMap> resultList = (List<EgovMap>)orgMgmtMapper.getAgentDepositMgmtList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("adminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		for(EgovMap map : resultList){
			
			
			if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
				String contractNum =  map.get("contractNum").toString().trim();
				if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
				{
					StringBuffer sb = new StringBuffer();
					String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
					sb.append(contractNum).append(tmp);
					map.put("contractNum",sb.toString());
				}
			}
		}
	
		return resultList;
	}
	
	/**
	 * 선불 예치금출금내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getAgentDepositMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("대리점명","예치금구분","입출금일자","입금액","출금액","처리금액","충전계약번호","잔액","관리자","메모");
		param.setStrValue("AGENTNAME","DEPOSITTYPENAME","DEPOSITDATE","PLUSDEPOSIT","MINUSDEPOSIT","RECHARGE","CONTRACTNUM","REMAINS","ADMINNM","REMARK");
		param.setIntWidth(5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 10000);
		param.setIntLen(0, 0, 0, 1, 1, 1, 0, 1, 0,0);
		param.setSheetName("예치금사용내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.orgmgmt.mapper.PpsHdofcOrgMgmtMapper.getAgentDepositMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "예치금사용내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 온라인 주문내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getOnlineOrderMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsOnlineOrderVo>();
		
		List<EgovMap> resultList = (List<EgovMap>)orgMgmtMapper.getOnlineOrderMgmtList(pRequestParamMap);
		
		for(EgovMap map : resultList){
			String status = map.get("status").toString();
			String statusNm = map.get("statusNm").toString();
			//산태가 접수나 접수중일 경우 링크 설치 
			if(status.equals("0")|| status.equals("1") && map.get("orderSeq")!=null && !map.get("orderSeq").equals(""))
			{
				StringBuffer sb = new StringBuffer();
				String orderSeq = map.get("orderSeq").toString();
				String tmp = "^javaScript:goOnlineUpdate("+orderSeq+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
				sb.append(statusNm).append(tmp);
				map.put("statusNm",sb.toString());
			}
			
			
		}
	
		return resultList;
	}
	
	/**
	 * 온라인 주문내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getOnlineOrderMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("대리점명","요청일자","품목","모델명","수량","희망배송일자","배송주소","담당자","메모","처리상태","본사관리자","관리자메모","완료일자","주문번호");
		param.setStrValue("AGENTNAME","REQDATE","ORDERITEMNM","MODELNM","AMOUNT","SHIPDATE","SHIPADDRESS","OPNM","AGENTMEMO","STATUSNM","BONSAADMINID","ADMINMEMO","FINISHDATE","ORDERSEQ");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0,0);
		param.setSheetName("온라인주문내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
				        
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.orgmgmt.mapper.PpsHdofcOrgMgmtMapper.getOnlineOrderMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "온라인주문내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 온라인주문 내역 처리 
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsOnlineOrderAdminProc(Map<String, Object> pRequestParamMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("orderSeq", pRequestParamMap.get("orderSeq"));
		resultMap.put("status", pRequestParamMap.get("status"));
		resultMap.put("adminId", pRequestParamMap.get("adminId"));
		resultMap.put("adminMemo", pRequestParamMap.get("adminMemo"));
		
		resultMap.put("oRetCode","" );
		resultMap.put("oRetMsg","" );
		
		orgMgmtMapper.getPpsOnlineOrderAdminProc(resultMap);
		
		
		return resultMap;
	}
	
	/**
	 * 온라인주문 대리점 
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsOnlineOrderAgentProc(Map<String, Object> pRequestParamMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("opCode", pRequestParamMap.get("opCode"));
		resultMap.put("orderSeq", pRequestParamMap.get("orderSeq"));
		resultMap.put("orderItem", pRequestParamMap.get("orderItem")); 
		resultMap.put("modelNm", pRequestParamMap.get("modelNm")); 
		resultMap.put("amount", pRequestParamMap.get("amount"));
		resultMap.put("shipDate", pRequestParamMap.get("shipDate"));
		resultMap.put("shipAddress", pRequestParamMap.get("shipAddress")); 
		resultMap.put("opNm", pRequestParamMap.get("opNm")); 
		resultMap.put("opPhone", pRequestParamMap.get("opPhone"));
		resultMap.put("agentMemo", pRequestParamMap.get("agentMemo")); 
		resultMap.put("agentId", pRequestParamMap.get("agentId")); 
		resultMap.put("adminId", pRequestParamMap.get("adminId"));
		
		resultMap.put("oRetCode","" );
		resultMap.put("oRetMsg","" );
		
		orgMgmtMapper.getPpsOnlineOrderAgentProc(resultMap);
		
		
		return resultMap;
	}
	
	/**
	 * 온라인 주문내역 상세조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public PpsOnlineOrderVo getOnlineOrderInfo(Map<String, Object> pRequestParamMap) {
		PpsOnlineOrderVo ppsOnlineOrderVo = new PpsOnlineOrderVo();
		
		ppsOnlineOrderVo = orgMgmtMapper.getOnlineOrderInfo(pRequestParamMap);
		
		
		return ppsOnlineOrderVo;
	}
	
	
	/**
	 * 개통대리점 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getOpenAgentInfoList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<EgovMap>();
		
		 List<EgovMap> resultList = (List<EgovMap>)orgMgmtMapper.getOpenAgentInfoList(pRequestParamMap);
		 
		      logger.debug("service   resultList["+resultList.toString()+"]");
				
				HashMap<String, String> maskFields = new HashMap<String, String>();
				maskFields.put("rprsenNm",	"CUST_NAME");
				maskFields.put("respnPrsnNum",	"MOBILE_PHO");
				maskFields.put("telnum",	"MOBILE_PHO");
				maskFields.put("fax",		"MOBILE_PHO");
				
				maskingService.setMask(resultList, maskFields, pRequestParamMap);
		 
		 for(EgovMap map : resultList){
				
				
				if(map.get("agentId")!=null && !map.get("agentId").equals("") ){
					
					
					String agentId =  map.get("agentId").toString().trim();
					if(!StringUtils.isBlank(agentId))
					{
						StringBuffer sb = new StringBuffer();
						String tmp = "^javaScript:goAgentInfoData("+map.get("linenum")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
						sb.append(agentId).append(tmp);
						map.put("agentIdStr",sb.toString());
					}
					if(map.get("deposit")!=null && !map.get("deposit").equals("") )
					{
						String deposit =  map.get("deposit").toString().trim();
						if(!StringUtils.isBlank(deposit))
						{
							
							StringBuffer sb = new StringBuffer();
							String tmp = "^javaScript:goDepositInfoData("+map.get("linenum")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
							sb.append(deposit).append(tmp);
							map.put("depositStr",sb.toString());
							
						}
						
					}
					
					if(map.get("virAccountId")!=null && !map.get("virAccountId").equals("") )
					{
						String virAccountId = map.get("virAccountId").toString().trim();
						if(!StringUtils.isBlank(virAccountId))
						{
							
							StringBuffer sb = new StringBuffer();
							String tmp = "^javaScript:goVirAccountData("+map.get("linenum")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
							sb.append(virAccountId).append(tmp);
							map.put("virAccountIdStr",sb.toString());
							
						}
					
					}
					
					
					
				}
			}
		
		return resultList;
	}
	
	/**
	 * 개통대리점 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getOpenAgentInfoListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("대리점코드","대리점명","대표자명","담당자연락처","유선연락처","팩스","예치금","가상계좌","은행명","등록일자");
		param.setStrValue("AGENTID","AGENTNM","RPRSENNM","RESPNPRSNNUM","TELNUM","FAX","DEPOSIT","VIRACCOUNTID","VIRBANKNM","REGDTTM");
		param.setIntWidth(5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 1, 0, 0,0);
		param.setSheetName("개통대리점목록");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
						        
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.orgmgmt.mapper.PpsHdofcOrgMgmtMapper.getOpenAgentInfoListExcel", pRequestParamMap, param, handler);
		String fileName = "개통대리점목록_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	
	public String getCurrentTimes()
	{
		String currDateTime = "";
		
		Date currtentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
		currDateTime = "_"+sdfFileName.format(currtentDate);
		
		
		return currDateTime;
	}
	 

	
}
