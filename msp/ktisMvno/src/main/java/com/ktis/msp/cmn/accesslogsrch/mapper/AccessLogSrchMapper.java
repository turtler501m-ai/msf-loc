package com.ktis.msp.cmn.accesslogsrch.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.accesslogsrch.vo.AccessLogSrchVo;

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

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: AccessLogSrchMapper.java
 * 3. Package	: com.ktis.msp.cmn.accesslogsrch.mapper
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:50:26
 * </PRE>
 */
@Mapper("accessLogSrchMapper")
public interface AccessLogSrchMapper {
	
	List<?> selectList(Map<String, Object> pReqParamMap);

	List<AccessLogSrchVo> selecExcelList(AccessLogSrchVo userInfoMgmtVo);
}


 


