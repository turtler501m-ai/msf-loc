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
 *  == M전산_등록계정(Modification Information) ==
 *
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2019.01.17          권오승        최초 생성
 *
 * </pre>
 */

@Mapper("securityUsrMgmtMapper")
public interface SecurityUsrMgmtMapper {
	
	/** M전산_등록계정 */
	List<?> mspUsrMgmtList(Map<String, Object> pReqParamMap);

	/** M전산_등록계정 엑셀다운로드 */
	List<SecurityMgmtVo> mspUsrMgmtListExcel(Map<String, Object> pReqParamMap);
	
	/** M전산_마스킹권한자 */
	List<?> mspUsrMaskList(Map<String, Object> pReqParamMap);
	
	/** M전산_마스킹권한자 엑셀다운로드 */
	List<SecurityMgmtVo> mspUsrMaskListExcel(Map<String, Object> pReqParamMap);
	
	/** M전산_삭제계정 */
	List<?> mspUsrDelList(Map<String, Object> pReqParamMap);

	/** M전산_삭제계정 엑셀다운로드 */
	List<SecurityMgmtVo> mspUsrDelListExcel(Map<String, Object> pReqParamMap);
	
	
	/** M전산_전체계정 */
	List<?> mspAllUsrMgmtList(Map<String, Object> pReqParamMap);

	/** M전산_전체계정 엑셀다운로드 */
	List<SecurityMgmtVo> mspAllUsrMgmtListExcel(Map<String, Object> pReqParamMap);
		
}


 


