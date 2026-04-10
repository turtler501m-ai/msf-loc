package com.ktmmobile.mcp.telCounsel.dao;

import java.util.List;

import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.telCounsel.dto.TelCounselDtlDto;
import com.ktmmobile.mcp.telCounsel.dto.TelCounselDto;

public interface TelCounselDao {

	/**
	* @Description :
	* @param counselSeq 전화상담 정보를 조회한다.
	* 		 전화상담순차번호로 전화테이블(NMCP_TEL_COUNDEL) 을 조회하고
	*		 해당 전화상담기본테이블의 하위인 전화상담내역테이블(NMCP_TEL_COUNSEL_DTL) 정보 리스트를 같이 조회한다
	*
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 4.
	*/
	TelCounselDto selectTelCounsel(int counselSeq);

	/**
	* @Description :
	* @param telCounselDtlDto
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 4.
	*/
	int updateTelCounselDtl(TelCounselDtlDto telCounselDtlDto);

	/**
	* @Description : 전화상담문의 리스트 카운트
	* @param telCounselDto
	* @param pageInfoBean
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 6.
	*/
	List<TelCounselDto> selectTelCounselBasList(TelCounselDto telCounselDto,
			PageInfoBean pageInfoBean);

	/**
	* @Description : 상담일련번호로 해당하는 상담내역상담 리스트(NMCP_TEL_COUNSEL_DTL)모두를 삭제 처리한다.
	* @param counselSeq
	* @Author : ant
	* @Create Date : 2016. 3. 7.
	*/
	int deleteTelCounselDtlBySeq(int counselSeq);

	/**
	* @Description : 상담일련번호로 해당하는 상담내역상담기본정보(NMCP_TEL_COUNSEL_BAS)를 삭제한다.
	* @param counselSeq
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 7.
	*/
	int deleteTelCounselBasBySeq(int counselSeq);

	/**
	* @Description : 상담 상세내용을 저장처리한다.
	* @param telCounselDtlDto
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 7.
	*/
	int insertTelCounselDtl(TelCounselDtlDto telCounselDtlDto);

	/**
	* @Description :
	* 입력받은 전화상담 일련번호에 해당하는 최근 상세 상담내용을 변경한다
	* (최근 상세 상담내용은 등록일 기준으로 삭제가 안되 1개의 값을 NMCP_TEL_COUNSEL_BAS 테이블 LAST_COUNSEL_DTL_SEQ 필드에 업데이트해준다)
	* @param counselSeq 전화상담문의 기본 일련번호
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 7.
	*/
	int updateLastCounselDtlSeq(int counselSeq);


	/**
	* @Description : 전화상담 신청정보 저장
	* @param telCounselDto
	* @return
	* @Author
	* @Create Date : 2019. 3. 20.
	*/
	public int insertCtiTelCounsel(TelCounselDto telCounselDto);

	/**
	* @Description : 전화상담 30일이내 신청정보 확인
	* @param telCounselDto
	* @return
	* @Author
	* @Create Date : 2019. 3. 20.
	*/
	public int selectCtiTelCounselCnt(TelCounselDto telCounselDto);

	/**
	* @Description : 직영온라인 30일 이내 신청정보 확인
	* @param telCounselDto
	* @return
	* @Author
	* @Create Date : 2019. 3. 20.
	*/
	public int selectCtiReqCnt(TelCounselDto telCounselDto);
}
