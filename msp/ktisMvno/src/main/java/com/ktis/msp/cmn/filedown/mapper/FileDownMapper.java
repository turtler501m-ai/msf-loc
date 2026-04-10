package com.ktis.msp.cmn.filedown.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FileDownMapper.java
 * 3. Package	: com.ktis.msp.cmn.filedown.mapper
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:54:23
 * </PRE>
 */
@Mapper("fileDownMapper")
public interface FileDownMapper {
	
	/**
	 * <PRE>
	 * 1. MethodName: insertCmnFileDnldMgmtMst
	 * 2. ClassName	: FileDownMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:54:24
	 * </PRE>
	 * 		@return int
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	int  insertCmnFileDnldMgmtMst(Map<String, Object> pReqParamMap) ;
	
	int  insertCmnExclDnldHst(Map<String, Object> pReqParamMap) ;
	
	List<?> getFileMgmtById(Map<String, Object> pReqParamMap);
	
	public void insertExcelDown(Map<String, Object> param);

	public List<?> selectExcelList(Map<String, Object> param);

	public List<?> selectExcelFilePath(Map<String, Object> param);
	
	public List<?> selectExcelFilePathByExclDnldId(Map<String, Object> param);
	
	public int getSqCmnExclDnldHst();
	
	int  insertCmnExclDnldHstVO(BatchJobVO vo) ;
}
