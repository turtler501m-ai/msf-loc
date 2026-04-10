package com.ktis.msp.pps.sucmgmt.service;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCommonService;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsKtInResVo;
import com.ktis.msp.pps.sucmgmt.mapper.SucMgmtMapper;
import com.ktis.msp.pps.sucmgmt.vo.SucVo;
import com.ktis.msp.rcp.rcpMgmt.Util;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class SucMgmtService extends BaseService{
	
	@Autowired
	private SucMgmtMapper sucMgmtMapper;
	
	@Autowired
	PpsHdofcCommonService hdofcCommonService;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	@SuppressWarnings("unchecked")
	public List<EgovMap> getSucList(SucVo sucVo, Map<String, Object> paramMap){

		List<EgovMap> resultList = (List<EgovMap>) sucMgmtMapper.getSucList(sucVo);

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("frgnrNameMsk","CUST_NAME");
		maskFields.put("mobileNoMsk","MOBILE_PHO");
		maskFields.put("mailMsk","EMAIL");
		maskFields.put("endUsrNm","CUST_NAME");

		maskingService.setMask(resultList, maskFields, paramMap);

		return resultList;
	}

	@SuppressWarnings("unchecked")
	public SucVo getSucInfo(SucVo sucVo, Map<String, Object> paramMap){

		SucVo resultVo = sucMgmtMapper.getSucInfo(sucVo);

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("frgnrNameMsk","CUST_NAME");
		maskFields.put("mobileNoMsk","MOBILE_PHO");
		maskFields.put("mailMsk","EMAIL");
		maskFields.put("endUsrNm","CUST_NAME");

		maskingService.setMask(resultVo, maskFields, paramMap);

		return resultVo;
	}

	@Transactional(rollbackFor=Exception.class)
	public void updateSsate(HttpServletRequest pRequest, Map<String, Object> paramMap, SucVo sucVo){
		
		if(paramMap != null){
    		sucVo.setAdminId(String.valueOf(paramMap.get("SESSION_USER_ID")));
	    	sucVo.setAdminNm(String.valueOf(paramMap.get("SESSION_USER_NAME")));
    	}

		if("20".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
			sucVo.setCntpntShopId(String.valueOf(paramMap.get("SESSION_USER_ORGN_ID")));
		}

		SucVo resultVo = this.getSucInfo(sucVo, paramMap);

		String fileMask = resultVo.getFileMask();
		String etcFileMask = resultVo.getEtcFileMask();

		//String filePathStr = "";
		//String etcFilePathStr = "";
		StringBuffer filePathStr = new StringBuffer();
		StringBuffer etcFilePathStr = new StringBuffer();
		
		if(pRequest != null ){
			filePathStr.append(getFilePath(fileMask, pRequest.getServerName()));
			etcFilePathStr.append(getFilePath(etcFileMask, pRequest.getServerName()));
			
			if(filePathStr.toString().equals("")){
				filePathStr.setLength(0);
				//filePathStr = "";
			}else{
				filePathStr.append(fileMask);
				//filePathStr = filePathStr + pRequest.getParameter("fileMask");
			}
			
			if(etcFilePathStr.toString().equals("")){
				etcFilePathStr.setLength(0);
				//etcFilePathStr = "";
			}else{
				etcFilePathStr.append(etcFileMask);
				//etcFilePathStr = etcFilePathStr + pRequest.getParameter("etcFileMask");
			}
		}
		
		sucVo.setCheckImgPath(filePathStr.toString());
		sucVo.setCheckEtcImgPath(etcFilePathStr.toString());
		
		sucMgmtMapper.updateSsate(sucVo);
		
		//Map<String, Object> resultMap = new HashMap<String, Object>();
		paramMap.put("sellUsimKey", sucVo.getSellUsimKey());
		paramMap.put("usimNo", sucVo.getUsimNo());
		paramMap.put("phone", sucVo.getMobileNo());
		paramMap.put("adminId", paramMap.get("SESSION_USER_ID"));
		paramMap.put("adminNm", paramMap.get("SESSION_USER_NAME"));
		paramMap.put("checkImgPath", filePathStr.toString());
		paramMap.put("checkEtcImgPath", etcFilePathStr.toString());
		paramMap.put("rcgAutoAmt2", sucVo.getRcgAutoAmt2());
		
		paramMap.put("oRetCd", "");
		paramMap.put("oRetMsg", "");
		paramMap.put("seq", "");
		
		paramMap.put("oResCode", "0000");
		paramMap.put("oResCodeNm", "개통 유심 수정  완료");
		
		
		//상태가 처리완료일시 프로시져 저장
		if(sucVo.getSstate().equals("20")){
			//resultMap.put("O_SEQ", "");
			sucMgmtMapper.procPpsOpenUsimDone(paramMap);
			
			paramMap.put("oResCode", "");
			if(paramMap != null)
				paramMap.put("oResCode", String.valueOf(paramMap.get("oRetCd")));
			paramMap.put("oResCodeNm", "개통 유심 수정 완료");
			
			if(paramMap != null 
					&& (paramMap.get("oRetCd") != null && "0000".equals(paramMap.get("oRetCd")))
					&& (paramMap.get("seq") != null && !"".equals(paramMap.get("seq"))) ){
				
				PpsKtInResVo ppsKtInResVo = new PpsKtInResVo();
				ppsKtInResVo = hdofcCommonService.ppsHdofcSleep(paramMap);
				
				paramMap.put("oResCode", ppsKtInResVo.getoResCode());
				logger.debug(ppsKtInResVo.getoResCode());
				if(ppsKtInResVo.getoResCode().equals("0000")){
					paramMap.put("oResCodeNm", "개통 유심 수정 완료, 자동 충전 성공");
				}else{
					paramMap.put("oResCodeNm", "개통 유심 수정 완료, 자동 충전 실패 : "+ppsKtInResVo.getoResCodeNm());
				}
			}
			
			
			
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int deleteSellUsim(SucVo sucVo){
		return sucMgmtMapper.deleteSellUsim(sucVo);
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getExcelList(SucVo sucVo){
		return (List<EgovMap>) sucMgmtMapper.getExcelList(sucVo);
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getSucStatisticsListAjax(SucVo sucVo, Map<String, Object> paramMap){
		return (List<EgovMap>) sucMgmtMapper.getSucStatisticsListAjax(sucVo);
	}
	
	public String getFilePath(String fileMask, String serverName){
		//이전 파일명일때
		//String pathStr = "";
		StringBuffer pathStr = new StringBuffer();
		File tmpF = null;
		
		if(serverName.toLowerCase().indexOf("mspdev.ktmmobile.com") != -1 
				|| serverName.toLowerCase().indexOf("mspoper.ktmmobile.com") != -1){
	    	/*path="/jboss/nfs/mcpdev/default/upload/sellusim/";*/
	    	//2016-01-05 이미지 경로 수정 
			pathStr.append("/jboss/data/mcp_dev/upload/sellusim");
			//pathStr="/jboss/data/mcp_dev/upload/sellusim";
	    }else{
	    	/*path="/jboss/nfs/mcp/default/upload/sellusim/";*/
	    	//2016-01-05 이미지 경로 수정
	    	pathStr.append("/jboss/data/mcp/upload/sellusim");
	    	//pathStr="/jboss/data/mcp/upload/sellusim";
	    }
		
		if(fileMask == null || fileMask.equals("")){
			pathStr.setLength(0);
			//pathStr = "";
		}else if(fileMask.toLowerCase().indexOf("_") ==-1 ){
	    	//파일 이전 확인
	    	tmpF=new File(pathStr.toString() + fileMask);
	    	if(!tmpF.isFile()){
	    		String imsiPath2[] = fileMask.split("\\.");
		    	Date now = new Date( Long.parseLong(imsiPath2[0]));
		    	SimpleDateFormat sdf1 =new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
				String imsi= sdf1.format(now);
				pathStr.append("/"+imsi.substring(0,4)+"/"+imsi.substring(4,6)+"/");
				//pathStr = pathStr+"/"+imsi.substring(0,4)+"/"+imsi.substring(4,6)+"/";
	    	}
	    }else{//파일명에 날짜가있을때
	    	//파일 이전 확인
	    	tmpF=new File( pathStr+fileMask);
	    	if(!tmpF.isFile()){
	    		String imsiPath[] = fileMask.split("_");
	    		pathStr.append("/"+imsiPath[0].substring(0,4)+"/"+imsiPath[0].substring(4,6)+"/");
	    		//pathStr = pathStr+"/"+imsiPath[0].substring(0,4)+"/"+imsiPath[0].substring(4,6)+"/";
	    	}
	    }
	    
	    return pathStr.toString();
	}
	
}
