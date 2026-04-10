package com.ktis.msp.pps.smsmgmt.mapper;
import java.util.List;
import java.util.Map;

import com.ktis.msp.org.common.vo.FileInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsSmsDumpMgmtMapper
 * @Description :   선불카드관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsSmsDumpMgmtMapper")
public interface PpsSmsDumpMgmtMapper {
	/**
	 * 문자관리 문자전송내역
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getSmsDumpMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 문자관리 문자전송내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getSmsDumpMgmtListExcel(Map<String, Object> pRequestParamMap);

	/**
	 * 문자관리 단문문자전송
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsSmsDumpShortProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * dump_seq를 받는다.
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	int getDumpSeq();
	
	/**
	 * 회신번호 목록을 가져옴
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getSelectPpsCallingNumList();

	/**
	 * msgQueue 시퀀스를 가져옴
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String getMsgQueueSeq();
	
	/**
	 * 파일의 정보를 변경한다.
	 */    
    int updateFile(FileInfoVO fileInfoVO);

	/**
	 * 파일의 정보를 변경한다.
	 */    
    int updateFile1(FileInfoVO fileInfoVO);

	/**
	 * 파일의 정보를 변경한다.
	 */    
    int updateFile2(FileInfoVO fileInfoVO);

	/**
	 * 파일의 정보를 변경한다.
	 */    
    int updateFile3(FileInfoVO fileInfoVO);

	/**
	 * 파일의 정보를 변경한다.
	 */    
    int updateFile4(FileInfoVO fileInfoVO);

	/**
	 * MMS 파일 갯수를 가져온다.
	 */    
    int getMmsFileCnt(FileInfoVO fileInfoVO);
    
	/**
	 * MMS 파일 리스트를 가져온다.
	 */  
    List<Map<String, String>> getMmsFileList(FileInfoVO fileInfoVO);
}
