package com.ktis.msp.cmn.authmgmt.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.authmgmt.mapper.AuthMgmtMapper;
import com.ktis.msp.cmn.authmgmt.vo.AuthMgmtVO;
import com.ktis.msp.cmn.masking.service.MaskingService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : AuthMgmtService.java
 * @Description : AuthMgmtService Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.09.01    임지혜       최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Service
public class AuthMgmtService extends BaseService {
	
	@Autowired
	private AuthMgmtMapper authMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	
	public AuthMgmtService() {
		setLogPrefix("[AuthMgmtService] ");
	}
	
	/*트리구조 메뉴*/
	public List<?> treeMenuList(AuthMgmtVO authMgmtVO){
		logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("서비스 : 메뉴 트리 리스트 조회 ." +authMgmtVO.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        List menuList = authMgmtMapper.treeMenuList(authMgmtVO);
        
        
        for(int i = 0; i < menuList.size(); i++){
        	logger.info(generateLogMsg("서비스 for 안에 들어 옴"));
            EgovMap tempMap = (EgovMap)menuList.get(i);
            String xmlkidsBo = (String) tempMap.get("xmlkidsBo");
            if("true".equals(xmlkidsBo)){
                tempMap.put("xmlkids", true);
            } else {
                tempMap.put("xmlkids", false);
            }
            
            menuList.set(i, tempMap);
        }
       
        return menuList;

    }
	
	public List<?> treeMenuAllList(){
	    logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("서비스 : 메뉴 전체 트리 리스트 조회 ." ));
        logger.info(generateLogMsg("================================================================="));
        List<Map<String, Object>> treeList = new ArrayList<Map<String,Object>>();   // 최종 트리
        
        List menuList = authMgmtMapper.treeMenuAllList();
        
        final List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); // 원본데이터(Bean일경우 Map으로 변환)
        Iterator iter;
        
        for( iter=menuList.iterator(); iter.hasNext(); ) {
            try{
                Object obj = iter.next();
                if( obj instanceof Map ) {
                    list.add((Map<String, Object>) obj);
                }
                // PMD : empty if statment
//                else{
//                    list.add((Map<String, Object>) BeanUtils.describe(obj));
//                }
            }catch (Exception e) {
            	// PMD
//                throw new RuntimeException("Collection -> List<Map> 으로 변환 중 실패: " + e); 
            	throw new MvnoErrorException(e);
            }
        }
        
        int listLength = list.size();
        int loopLength = 0;
        final int[] treeLength = new int[] { 0 };
        
        while ( treeLength[0] != listLength && listLength != loopLength++ ) {
            for ( int i=0; i < list.size(); i++ ) {
                Map<String, Object> item = list.get(i);
                
                if ( "0".equals((String)item.get("hghrMenuId")) ) { //root node
                    Map<String, Object> view = new HashMap<String, Object>(item);
                    view.put("rows", new ArrayList<Map<String,Object>>());
                     
                    treeList.add(view);
                    list.remove(i);
                     
                    treeLength[0]++;
                     
                    Collections.sort(treeList, new Comparator<Map<String, Object>>(){
                        public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                            // TODO Auto-generated method stub
                            return ((String)arg0.get("algnSeq")).compareTo((String)arg1.get("algnSeq"));
                        }
                    });
                    view.put("xmlkids", true);
                     
                    break;
                } else {
                    new InnerClass(){
                        public void getParentNode(List<Map<String, Object>> children, Map<String,Object> item){
                            for ( int innerIndex=0; innerIndex < children.size(); innerIndex++ ) { // PMD : i -> innerIndex로 변수 수정
                                Map<String, Object> child = children.get(innerIndex);
                                
                                if ( child.get("menuId").equals(item.get("hghrMenuId")) ) {
                                    Map<String, Object> view = new HashMap<String, Object>(item);
                                    
                                    if("true".equals(item.get("xmlkidsBo"))){
                                        view.put("rows", new ArrayList<Map<String,Object>>());
                                        view.put("xmlkids", true);
                                    } else {
                                        view.put("xmlkids", false);
                                    }
                                    
                                    ((List<Map<String,Object>>) child.get("rows")).add(view);
                                    
                                    treeLength[0]++;
                                     
                                    list.remove(list.indexOf(item));
                                    
                                    Collections.sort(((List<Map<String,Object>>) child.get("rows")), new Comparator<Map<String, Object>>(){
                                        public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                                            // TODO Auto-generated method stub
                                            int returnCom = 0;
                                            if(arg0.get("algnSeq") != null && arg1.get("algnSeq") != null){
                                                returnCom = ((BigDecimal)arg0.get("algnSeq")).compareTo((BigDecimal)arg1.get("algnSeq"));
                                            }
                                            return returnCom;
                                        }
                                    });
                                    
                                    break;
                                } else {
                                    
                                    if( child.get("rows") != null &&  ((List<Map<String,Object>>) child.get("rows")).size() > 0 ){
                                        getParentNode((List<Map<String,Object>>) child.get("rows"), item);
                                    }
                                }
                            }
                        }
                    }.getParentNode(treeList, item);
                }
            }
        }
        
        return treeList;
	}

	public interface InnerClass{
	    public void getParentNode(List<Map<String, Object>> list, Map<String,Object> item);
	}
	
	/**
	 * @Description : 사용자 그룹 이력 로그 리스트 조회
	 * @Param  : PowerTmntMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> usrGrpHstList(Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 그룹 이력 로그 서비스 START."));
		logger.info(generateLogMsg("================================================================="));

		List<?> result = authMgmtMapper.usrGrpHstList(pRequestParamMap);
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}
	
	public List<?> grpIdList(){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("메뉴 트리구조 xml"));
		logger.info(generateLogMsg("================================================================="));
		
		return authMgmtMapper.grpIdList();
	}
	
	public List<?> prgmList(Map<String, Object> param){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("프로그램 리스트"));
		logger.info(generateLogMsg("================================================================="));
		
		return authMgmtMapper.prgmList(param);
	}
	
	public List<?> prgmList2(Map<String, Object> param){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("프로그램 리스트"));
		logger.info(generateLogMsg("================================================================="));
		
		return authMgmtMapper.prgmList2(param);
	}
	
	public List<?> prgmDetail(Map<String, Object> param){
		
		return authMgmtMapper.prgmDetail(param);
	}
	
	public List<?> prgmInsertDupMainCheck(Map<String, Object> param){
		
		return authMgmtMapper.prgmInsertDupMainCheck(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void prgmInsert(Map<String, Object> param){
		
		authMgmtMapper.prgmInsert(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void prgmInsert2(Map<String, Object> param){
		
		authMgmtMapper.prgmInsert2(param);
	}
	
	public List<?> prgmUpdateDupMainCheck(Map<String, Object> param){
		
		return authMgmtMapper.prgmUpdateDupMainCheck(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int prgmUpdate(Map<String, Object> param){
		
		return authMgmtMapper.prgmUpdate(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int prgmDelete(Map<String, Object> param){
		
		return authMgmtMapper.prgmDelete(param);
		
	}
	
	public List<?> menuHghrList(Map<String, Object> param){
		
		return authMgmtMapper.menuHghrList(param);
	}
	
	public List<?> menuDetail(Map<String, Object> param){
		
		return authMgmtMapper.menuDetail(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int menuInsert(Map<String, Object> param){
		
		return authMgmtMapper.menuInsert(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int menuUpdate(Map<String, Object> param){
		
		return authMgmtMapper.menuUpdate(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int menuDelete(Map<String, Object> param){
		
		return authMgmtMapper.menuDelete(param);
		
	}
	
	public List<?> authList(Map<String, Object> param){
		
		return authMgmtMapper.authList(param);
	}
	
	public List<?> authListPaging(Map<String, Object> param){
		
		return authMgmtMapper.authListPaging(param);
	}
	
	public List<?> authDetail(Map<String, Object> param){
		
		return authMgmtMapper.authDetail(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int authInsert(Map<String, Object> param){
		
		return authMgmtMapper.authInsert(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int authUpdate(Map<String, Object> param){
		
		return authMgmtMapper.authUpdate(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int authDelete(Map<String, Object> param){
		
		return authMgmtMapper.authDelete(param);
		
	}
	
	public List<?> groupSearchList(Map<String, Object> param){
		
		return authMgmtMapper.groupSearchList(param);
	}
	
	
	public List<?> authMenuList(Map<String, Object> param){
		
		return authMgmtMapper.authMenuList(param);
	}
	
	public List<?> authMenuDetail(Map<String, Object> param){
		
		return authMgmtMapper.authMenuDetail(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int authMenuInsert(Map<String, Object> param){
		
		return authMgmtMapper.authMenuInsert(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int authMenuUpdate(Map<String, Object> param){
		
		return authMgmtMapper.authMenuUpdate(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int authMenuDelete(Map<String, Object> param){
		
		return authMgmtMapper.authMenuDelete(param);
		
	}
	
	public List<?> usrMenuAsgnList(Map<String, Object> param){
		
		List<EgovMap> result = (List<EgovMap>) authMgmtMapper.usrMenuAsgnList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID_MSK",		"SYSTEM_ID");
		maskFields.put("USR_NM",			"CUST_NAME");
		maskFields.put("TEL_NUM",			"MOBILE_PHO");
		maskFields.put("MBLPHN_NUM",		"MOBILE_PHO");
		maskFields.put("EMAIL",				"EMAIL");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	
	public List<?> usrMenuAsgnDetail(Map<String, Object> param){
		
		List<EgovMap> result = (List<EgovMap>) authMgmtMapper.usrMenuAsgnDetail(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID_MSK",		"SYSTEM_ID");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int usrMenuAsgnInsert(Map<String, Object> param){
		
		return authMgmtMapper.usrMenuAsgnInsert(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int usrMenuAsgnUpdate(Map<String, Object> param){
		
		return authMgmtMapper.usrMenuAsgnUpdate(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int usrMenuAsgnDelete(Map<String, Object> param){
		
		return authMgmtMapper.usrMenuAsgnDelete(param);
		
	}
	
	public List<?> grpAuthAsgnList(Map<String, Object> param){
		
		return authMgmtMapper.grpAuthAsgnList(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int grpAuthAsgnInsert(Map<String, Object> param){
		
		return authMgmtMapper.grpAuthAsgnInsert(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int grpAuthAsgnDelete(Map<String, Object> param){
		
		return authMgmtMapper.grpAuthAsgnDelete(param);
		
	}
	
	public List<?> usrGrpList(Map<String, Object> param){
		
		return authMgmtMapper.usrGrpList(param);
	}
	
	public List<?> usrGrpDetail(Map<String, Object> param){
		
		return authMgmtMapper.usrGrpDetail(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int usrGrpInsert(Map<String, Object> param){
		
		return authMgmtMapper.usrGrpInsert(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int usrGrpUpdate(Map<String, Object> param){
		
		return authMgmtMapper.usrGrpUpdate(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int usrGrpDelete(Map<String, Object> param){
		
		return authMgmtMapper.usrGrpDelete(param);
		
	}
	
	public List<?> usrGrpAsgnList(Map<String, Object> param){
		
		List<EgovMap> result = (List<EgovMap>) authMgmtMapper.usrGrpAsgnList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID_MSK",	"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int usrGrpAsgnInsert(Map<String, Object> param){
		
		return authMgmtMapper.usrGrpAsgnInsert(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int usrGrpAsgnHstInsert(Map<String, Object> param){
		
		return authMgmtMapper.usrGrpAsgnHstInsert(param);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int usrGrpAsgnDelete(Map<String, Object> param){
		
		return authMgmtMapper.usrGrpAsgnDelete(param);
		
	}
	
	public List<?> usrAuthReportList(Map<String, Object> param){
		
		return authMgmtMapper.usrAuthReportList(param);
	}
	
	
	public List<?> menuHghrListXml(Map<String, Object> param){
		
		return authMgmtMapper.menuHghrListXml(param);
	}
	
	public List<?> userInfoMgmtList(Map<String, Object> param){
		
		List<EgovMap> result = (List<EgovMap>) authMgmtMapper.userInfoMgmtList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID_MSK",		"SYSTEM_ID");
		maskFields.put("USR_NM",			"CUST_NAME");
		maskFields.put("TEL_NUM",			"MOBILE_PHO");
		maskFields.put("MBLPHN_NUM",		"MOBILE_PHO");
		maskFields.put("EMAIL",				"EMAIL");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	
}
