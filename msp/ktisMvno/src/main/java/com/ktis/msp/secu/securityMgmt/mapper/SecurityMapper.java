package com.ktis.msp.secu.securityMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.secu.securityMgmt.vo.SecurityMgmtVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 월간보안점검
 *
 * @author  
 * @since 2019.01.17
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2019.01.17                     최초 생성
 *
 * </pre>
 */

@Mapper("securityMapper")
public interface SecurityMapper {
	
	List<?> accessFailLogList(Map<String, Object> pReqParamMap);
	List<SecurityMgmtVo> accessFailLogListExcel(Map<String, Object> pReqParamMap);
	List<?> accessFailFileDownList(Map<String, Object> pReqParamMap);
	List<SecurityMgmtVo> accessFailFileDownListExcel(Map<String, Object> pReqParamMap);
	List<?> stopUserList(Map<String, Object> pReqParamMap);
	List<SecurityMgmtVo> stopUserListExcel(Map<String, Object> pReqParamMap);
	List<?> fileDownLogList(Map<String, Object> pReqParamMap);
	List<SecurityMgmtVo> fileDownLogListExcel(Map<String, Object> pReqParamMap);

}


 


