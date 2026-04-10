package com.ktmmobile.mcp.telCounsel.service;

import java.util.List;

import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.telCounsel.dto.TelCounselDtlDto;
import com.ktmmobile.mcp.telCounsel.dto.TelCounselDto;


/**
 * @Class Name : TelCounselService
 * @Description : 전화상담 서비스 I/F
 *
 * @author : ant
 * @Create Date : 2016. 3. 4.
 */
public interface TelCounselService {

	/**
	* @Description : 전화상담 정보 조회 처리
	* @param counselSeq 전화상담일련번호
	* @return 전화상담정보
	* @Author : ant
	* @Create Date : 2016. 3. 4.
	*/
	TelCounselDto findTelCounselByCounselSeq(int counselSeq);

	/**
	* @Description : 전화상담 리스트 정보
	* @param telCounselDto 검색정보
	* @return 전화상담 리스트
	* @Author : ant
	* @Create Date : 2016. 3. 4.
	*/
	List<TelCounselDto> listTelCounsel(TelCounselDto telCounselDto);

	/**
	* @Description : 전화상담 상세내역 수정처리
	* @param telCounselDtlDto
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 4.
	*/
	int modifyTelCounselDto(TelCounselDtlDto telCounselDtlDto);

	/**
	* @Description : 페이징 처리되는 전화상담문의 게시판 리시트
	* 호출
	* @param telCounselDto
	* @param pageInfoBean
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 6.
	*/
	List<TelCounselDto> listTelCounselBas(TelCounselDto telCounselDto,
			PageInfoBean pageInfoBean);

	/**
	* @Description :
	* 상담 기본 일련번호로 해당 상담 기본정보와 상세 정보를 모두 삭제처리한다.
	* @param counselSeq 상담기본일련번호
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 7.
	*/
	int removeTelCounselBasWithDtlList(int counselSeq);

	/**
	* @Description :
	* 상담내역정보를 히스토리가 저장 처리한다.
	* @param telCounselDtlDto
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 7.
	*/
	int insertTelCounselDtl(TelCounselDtlDto telCounselDtlDto);

	/**
	* @Description :
	* 상남내역상세정보 한건을 수정 처리한다.
	* @param telCounselDtlDto
	* @return
	* @Author : ant
	* @Create Date : 2016. 3. 7.
	*/
	int modifyTelCounselDtlDto(TelCounselDtlDto telCounselDtlDto);


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
