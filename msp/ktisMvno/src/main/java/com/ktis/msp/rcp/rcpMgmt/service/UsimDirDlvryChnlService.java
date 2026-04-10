/**
 * 
 */
package com.ktis.msp.rcp.rcpMgmt.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.mapper.UsimDirDlvryChnlMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDirDlvryChnlVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @author 
 *
 */
@Service
public class UsimDirDlvryChnlService extends BaseService {
	private static final Log LOGGER = LogFactory.getLog(NstepCallService.class);
	
	protected final static String HEADER = "commHeader";
	protected final static String GLOBAL_NO = "globalNo";
	protected final static String RESPONSE_TYPE = "responseType";
	protected final static String RESPONSE_CODE = "responseCode";
	protected final static String RESPONSE_BASIC = "responseBasic";
	
	public static final String MPLATFORM_RESPONEXML_EMPTY_EXCEPTION = "서버(M-Platform) 점검중 입니다. 잠시후 다시 시도 하기기 바랍니다.(XML EMPTY)";
	
	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private UsimDirDlvryChnlMapper usimDirDlvryChnlMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	/** Constructor */
	public UsimDirDlvryChnlService() {
		setLogPrefix("[UsimDirDlvryChnlService] ");
	}
	
	
	/**
	 * 신청정보(바로배송채널) 리스트
	 * @param paramMap
	 * @return
	 */
	public List<?> getUsimDirDlvryChnlList(Map<String, Object> paramMap){
		
		// 필수조건 체크
		if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
			if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
				throw new MvnoRunException(-1, "검색값 누락");
			}
		} else {
			if(paramMap.get("strtDt") == null || "".equals(paramMap.get("strtDt"))){
				throw new MvnoRunException(-1, "시작일자 누락");
			}
			if(paramMap.get("endDt") == null || "".equals(paramMap.get("endDt"))){
				throw new MvnoRunException(-1, "종료일자 누락");
			}
		}
		
		List<EgovMap> list = (List<EgovMap>) usimDirDlvryChnlMapper.getUsimDirDlvryChnlList(paramMap);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);

		/**
		 * 데이터 파싱
		 * */
		for( EgovMap map : result ) {
			try {
				if(map.get("dlvryTel")!=null && !map.get("dlvryTel").equals("")){
					String[] dlvryTel = Util.getPhoneNum( (String)map.get("dlvryTel") );
					map.put("dlvryTelFn", dlvryTel[0]);
					map.put("dlvryTelMn", dlvryTel[1]);
					map.put("dlvryTelRn", dlvryTel[2]);
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		
		return result;
	}

	/**
	 * 신청정보(바로배송채널) 엑셀다운로드
	 * @param paramMap
	 * @return
	 */
	public List<?> getUsimDirDlvryChnlListByExcel(Map<String, Object> paramMap){
		
		// 필수조건 체크
		if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
			if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
				throw new MvnoRunException(-1, "검색값 누락");
			}
		} else {
			if(paramMap.get("strtDt") == null || "".equals(paramMap.get("strtDt"))){
				throw new MvnoRunException(-1, "시작일자 누락");
			}
			if(paramMap.get("endDt") == null || "".equals(paramMap.get("endDt"))){
				throw new MvnoRunException(-1, "종료일자 누락");
			}
		}
		
		List<EgovMap> list = (List<EgovMap>) usimDirDlvryChnlMapper.getUsimDirDlvryChnlListByExcel(paramMap);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}


	/**
	 * 신청정보(바로배송채널) 수정
	 * @param paramMap
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setUsimDirDlvryChnlInfo(UsimDirDlvryChnlVO usimDirDlvryChnlVO) {
		usimDirDlvryChnlMapper.updateUsimDirDlvryChnlInfo(usimDirDlvryChnlVO);	
	}
}