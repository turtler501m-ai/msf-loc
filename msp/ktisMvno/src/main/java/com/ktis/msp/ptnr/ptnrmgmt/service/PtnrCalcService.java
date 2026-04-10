package com.ktis.msp.ptnr.ptnrmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.ptnr.ptnrmgmt.mapper.PtnrCalcMapper;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrCalcVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class PtnrCalcService extends BaseService {

	@Autowired
	private PtnrCalcMapper ptnrCalcMapper;
	
	@Autowired
	private RcpEncService encSvc;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

    public List<?> getPtnrCalcJejuList(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
		List<EgovMap> list = (List<EgovMap>) ptnrCalcMapper.getPtnrCalcJejuList(searchVO);
			
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), pRequestParamMap);
    	
    	return list;
    }
    
    public List<?> getPtnrCalcJejuListSum(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
    	List<?> jejuListSum = new ArrayList<PtnrCalcVO>();
    	jejuListSum = ptnrCalcMapper.getPtnrCalcJejuListSum(searchVO);
    	
    	return jejuListSum;
    }    
    
    public List<?> getPtnrCalcGiftiList(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
		List<EgovMap> list = (List<EgovMap>) ptnrCalcMapper.getPtnrCalcGiftiList(searchVO);
			
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), pRequestParamMap);
    	
    	return list;
    }  
    
    public List<?> getPtnrCalcGiftiListSum(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
    	List<?> giftiListSum = new ArrayList<PtnrCalcVO>();
    	giftiListSum = ptnrCalcMapper.getPtnrCalcGiftiListSum(searchVO);
    	
    	return giftiListSum;
    }  
    
    public List<?> getPtnrCalcTmoneyList(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){

		List<EgovMap> list = (List<EgovMap>) ptnrCalcMapper.getPtnrCalcTmoneyList(searchVO);
			
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), pRequestParamMap);
    	
    	return list;
    }  
    
    public List<?> getPtnrCalcTmoneyListSum(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
    	List<?> tmoneyListSum = new ArrayList<PtnrCalcVO>();
    	tmoneyListSum = ptnrCalcMapper.getPtnrCalcTmoneyListSum(searchVO);
    	
    	return tmoneyListSum;
    } 


    public List<?> getPtnrCalcGooglePlayList(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
    	List<?> googlePlayList = new ArrayList<PtnrCalcVO>();
    	googlePlayList = ptnrCalcMapper.getPtnrCalcGooglePlayList(searchVO);
        
        maskingService.setMask(googlePlayList, maskingService.getMaskFields(), pRequestParamMap);
        
    	return googlePlayList;
    }  
    
    public List<?> getPtnrCalcGooglePlayListSum(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
    	List<?> googlePlayListSum = new ArrayList<PtnrCalcVO>();
    	googlePlayListSum = ptnrCalcMapper.getPtnrCalcGooglePlayListSum(searchVO);
    	
    	return googlePlayListSum;
    } 
    
    public List<?> getPtnrCalcGooglePlayListEx(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
    	List<?> googlePlayList = new ArrayList<PtnrCalcVO>();
    	googlePlayList = ptnrCalcMapper.getPtnrCalcGooglePlayListEx(searchVO);
        
        maskingService.setMask(googlePlayList, maskingService.getMaskFields(), pRequestParamMap);
        
    	return googlePlayList;
    }  
    

    public List<?> getPtnrCalcLpointList(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){

		List<EgovMap> list = (List<EgovMap>) ptnrCalcMapper.getPtnrCalcLpointList(searchVO);
			
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), pRequestParamMap);
    	
    	return list;
    }  
    
    public List<?> getPtnrCalcLpointListSum(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
    	List<?> lpointListSum = new ArrayList<PtnrCalcVO>();
    	lpointListSum = ptnrCalcMapper.getPtnrCalcLpointListSum(searchVO);
    	
    	return lpointListSum;
    } 
    
    public List<?> getPtnrCalcLpointSttlList(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
    	List<?> lpointSttlList = new ArrayList<PtnrCalcVO>();
    	lpointSttlList = ptnrCalcMapper.getPtnrCalcLpointSttlList(searchVO);
    	
    	return lpointSttlList;
    } 

    
    public List<?> getPtnrCalcLpointListEx(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
    	
		List<EgovMap> list = (List<EgovMap>) ptnrCalcMapper.getPtnrCalcLpointListEx(searchVO);
			
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), pRequestParamMap);
    	
    	return list;
    } 
    
    public List<?> getPtnrCalcMrAsstList(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
       
       List<?> aryRslt = ptnrCalcMapper.getPtnrCalcMrAsstList(searchVO);
       
       maskingService.setMask(aryRslt, maskingService.getMaskFields(), pRequestParamMap);
       
       return aryRslt;
       
    }
    
    public List<?> getPtnrCalcMrAsstListByExcel(PtnrCalcVO searchVO, Map<String, Object> pRequestParamMap){
       
       List<?> aryRslt = ptnrCalcMapper.getPtnrCalcMrAsstListByExcel(searchVO);
       
       maskingService.setMask(aryRslt, maskingService.getMaskFields(), pRequestParamMap);
       
       return aryRslt;
       
    }
}
