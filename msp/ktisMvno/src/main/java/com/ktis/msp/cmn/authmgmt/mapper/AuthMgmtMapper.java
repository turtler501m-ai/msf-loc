package com.ktis.msp.cmn.authmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.authmgmt.vo.AuthMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 통계에 관한 데이터처리 매퍼 클래스
 *
 * @author  표준프레임워크센터
 * @since 2014.08.05
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2014.08.13        임지혜          최초 생성
 *
 * </pre>
 */

@Mapper("authMgmtMapper")
public interface AuthMgmtMapper {
	
	List<?> prgmList(Map<String, Object> pReqParamMap);
	List<?> prgmList2(Map<String, Object> pReqParamMap); /*프로그램 화면추가*/
	List<?> prgmDetail(Map<String, Object> pReqParamMap);
	List<?> prgmInsertDupMainCheck(Map<String, Object> pReqParamMap);
	List<?> prgmUpdateDupMainCheck(Map<String, Object> pReqParamMap);
	List<?> menuList(Map<String, Object> pReqParamMap);
	List<?> menuHghrList(Map<String, Object> pReqParamMap);
	List<?> menuDetail(Map<String, Object> pReqParamMap);
	List<?> authList(Map<String, Object> pReqParamMap);
	List<?> authListPaging(Map<String, Object> pReqParamMap);
	List<?> authDetail(Map<String, Object> pReqParamMap);
	List<?> groupSearchList(Map<String, Object> pReqParamMap);
	List<?> authMenuList(Map<String, Object> pReqParamMap);
	List<?> authMenuDetail(Map<String, Object> pReqParamMap);
	List<?> usrMenuAsgnList(Map<String, Object> pReqParamMap);
	List<?> usrMenuAsgnDetail(Map<String, Object> pReqParamMap);
	List<?> grpAuthAsgnList(Map<String, Object> pReqParamMap);
	List<?> usrGrpList(Map<String, Object> pReqParamMap);
	List<?> usrGrpDetail(Map<String, Object> pReqParamMap);
	List<?> usrGrpAsgnList(Map<String, Object> pReqParamMap);
	List<?> grpIdList();
	List<?> usrAuthReportList(Map<String, Object> pReqParamMap);
	
	List<?> usrGrpHstList(Map<String, Object> pRequestParamMap);
	
	
	int prgmInsert(Map<String, Object> pReqParamMap);
	int prgmInsert2(Map<String, Object> pReqParamMap); /*프로그램 화면추가*/
	int prgmUpdate(Map<String, Object> pReqParamMap);
	int prgmDelete(Map<String, Object> pReqParamMap);
	int menuInsert(Map<String, Object> pReqParamMap);
	int menuUpdate(Map<String, Object> pReqParamMap);
	int menuDelete(Map<String, Object> pReqParamMap);
	
	int authInsert(Map<String, Object> pReqParamMap);
	int authUpdate(Map<String, Object> pReqParamMap);
	int authDelete(Map<String, Object> pReqParamMap);
	int authMenuInsert(Map<String, Object> pReqParamMap);
	int authMenuUpdate(Map<String, Object> pReqParamMap);
	int authMenuDelete(Map<String, Object> pReqParamMap);
	int usrMenuAsgnInsert(Map<String, Object> pReqParamMap);
	int usrMenuAsgnUpdate(Map<String, Object> pReqParamMap);
	int usrMenuAsgnDelete(Map<String, Object> pReqParamMap);

	int grpAuthAsgnInsert(Map<String, Object> pReqParamMap);
	int grpAuthAsgnDelete(Map<String, Object> pReqParamMap);
	
	int usrGrpInsert(Map<String, Object> pReqParamMap);
	int usrGrpUpdate(Map<String, Object> pReqParamMap);
	int usrGrpDelete(Map<String, Object> pReqParamMap);

	int usrGrpAsgnInsert(Map<String, Object> pReqParamMap);
	int usrGrpAsgnHstInsert(Map<String, Object> pReqParamMap);
	int usrGrpAsgnDelete(Map<String, Object> pReqParamMap);


	List<?>  menuHghrListXml(Map<String, Object> pReqParamMap);
	List<?>  mnpList(Map<String, Object> pReqParamMap);
	List<?>  userInfoMgmtList(Map<String, Object> pReqParamMap);
	
	/*메뉴 트리구조*/
	List<?>  treeMenuList(AuthMgmtVO authMgmtVO);	
	
	List<?>  treeMenuAllList();
    
}


 


