package com.ktis.msp.m2m.usimreturngmt.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktis.msp.m2m.usimreturngmt.vo.MsgQueueVO;

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

@Mapper("usimReturnMapper")
public interface UsimReturnMapper {
	
	List<?> noticeList(Map<String, Object> pReqParamMap);
	
	List<?> getNoticeFile(Map<String, Object> pReqParamMap);
	
	int noticeInsert(Map<String, Object> pReqParamMap);
	
	int noticeUpdate(Map<String, Object> pReqParamMap);
	
	int noticeDelete(Map<String, Object> pReqParamMap);
	
	String getNoticeId();
	
	/**
     * @Description : 파일 목록 조회(파일유형에 따라서 조회)
     * @Param  : String
     * @Return : List<?>
     * @Author : 김용문
     * @Create Date : 2017. 11. 20.
     */
    int getFileExtList(HashMap<String, String> map);
    
    /**
     * @Description : 공지사항 게시글 수정/삭제권한 체크
     * @Param  : String
     * @Return : List<?>
     * @Author : 권성광
     * @Create Date : 2018. 11. 29.
     */
    String checkNoticeAuth(String noticeId);
    

    List<?> getM2mMngHTel(Map<String, Object> pReqParamMap);

}

