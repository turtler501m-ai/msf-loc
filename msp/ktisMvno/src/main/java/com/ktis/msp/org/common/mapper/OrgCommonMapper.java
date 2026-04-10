package com.ktis.msp.org.common.mapper;

import java.util.List;

import com.ktis.msp.org.common.vo.FileInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : OrgCommonMapper
 * @Description : 기준 공통 mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */

@Mapper("orgCommonMapper")
public interface OrgCommonMapper {
	
	/**
	 * 해당월의 첫날짜를 가져옴
	 * @Return : String
	 */
	String getFirstDay(int day);

	/**
	 * 해당월의 마지막날짜를 가져옴
	 * @Return : String
	 */
	String getLastDay(int day);
	
	/**
	 * 해당월을 가지고 온다
	 * @Return : String
	 */
	String getToMonth(int day);	
	
	/**
	 * 해당일을 가지고 온다
	 * @Return : String
	 */
	String getToDay(int day);	
	
	/**
	 * 해당시간을 가지고 온다
	 * @Return : String
	 */
	String getToTime(int day);	
	
	/**
	 * 해당시간을 가지고 온다
	 * @Return : String
	 */
	String getLastMonthDay(int day);
	
	/**
	 * 해당시간을 가지고 온다
	 * @Return : String
	 */
	String getLastMonthMaxDay(int day);
	
	/**
	 * 전월을 가지고 온다
	 * @Return : String
	 */
	String getLastMonth(int day);		
	
	/**
	 * 해당일에 +된 일자를 가져옴
	 * @Return : String
	 */
	String getWantDay(int addition);			
	
	/**
	 * 해당일에 +된 일자를 가져옴
	 * @Return : String
	 */
	String getWantDayTime(int addition);	
	
	/**
	 * 해당일을 가지고 온다
	 * @Return : String
	 */
	String getToDayTime(int day);

	/**
	 * @Description : 특정월 마지막일을 가지고 온다
	 * @Param  : wantDate
	 * @Return : String
	 * @Author : hsy
	 * @Create Date : 2023. 4. 24.
	 */
	String getCertainMonthMaxDay(String wantDate);

	/**
	 * @Description : 파일의 기본정보를 등록한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int insertFile(FileInfoVO fileInfoVO);
    
	/**
	 * @Description : 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile(FileInfoVO fileInfoVO);    
    
	/**
	 * @Description : 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile1(FileInfoVO fileInfoVO);    
    
	/**
	 * @Description : 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile2(FileInfoVO fileInfoVO);  
    
	/**
	 * @Description : 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile3(FileInfoVO fileInfoVO);  
    
	/**
	 * @Description : 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile4(FileInfoVO fileInfoVO);  
    
	/**
	 * @Description : 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile5(FileInfoVO fileInfoVO);  
    
	/**
	 * @Description : 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile6(FileInfoVO fileInfoVO);  
    
	/**
	 * @Description : 녹취 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile7(FileInfoVO fileInfoVO);  

	/**
	 * @Description : 녹취 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile8(FileInfoVO fileInfoVO);  
    
	/**
	 * @Description : 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> getFile1(FileInfoVO fileInfoVO);
	
	/**
	 * @Description : 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> getFile2(FileInfoVO fileInfoVO);
	
	/**
	 * @Description : 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> getFile3(FileInfoVO fileInfoVO);
	
	/**
	 * @Description : 파일 명을 찾아온다.
	 * @Param  : String
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	String getFileNm(String fileId);
	
	/**
	 * @Description : 파일 명을 찾아온다.
	 * @Param  : String
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	String getFileNm2(String orgnId);
	
	/**
	 * @Description : 파일 삭제
	 * @Param  : String
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	int deleteFile(String fileId);	
	
	/**
	* @Description : 파일 갯수 
	* @Param  : FileInfoVO
	* @Return : int
	* @Author : 고은정
	* @Create Date : 2014. 10. 17.
	 */
	int getFileCnt(FileInfoVO fileInfoVO); 
	
    
	/**
	 * @Description : 녹취 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> getFile4(FileInfoVO fileInfoVO);    

	/**
	 * @Description : 녹취 파일 건수를 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	int getFile5(FileInfoVO fileInfoVO);    	

	/**
	 * @Description : 녹취 파일 건수를 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	int getFile6(FileInfoVO fileInfoVO);   

	/**
	 * @Description : 녹취 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> getFile7(FileInfoVO fileInfoVO);    

	/**
	 * @Description : 파일 명을 찾아온다.
	 * @Param  : String
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	String getFileNm8(String orgnId);
	
	/**
	 * @Description : 녹취 파일 seq 찾아온다.
	 * @Param  : String
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	String getRecSeq(String orgnId);
	
	/**
	 * @Description : 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> getRequestKey(FileInfoVO fileInfoVO);
	
	/**
	 * @Description : 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile23(FileInfoVO fileInfoVO);    
    
	/**
	 * @Description : 파일의 정보를 변경한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int updateFile24(FileInfoVO fileInfoVO);    
    
	/**
	 * @Description : 녹취여부 Y인값 찾기
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int getRequestKeyCnt(String orgnId);  
    
	/**
	 * @Description : 녹취여부 N 값으로 update
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int getRequestKeyUpdate(String orgnId);

}
