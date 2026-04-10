package com.ktis.msp.ptnr.ptnrmgmt.mapper;

import java.util.HashMap;
import java.util.List;

import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrLPointInterfaceVO;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrLinkInfoVO;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrRetryFileVO;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrRetryPointVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ptnrLinkMapper")
public interface PtnrLinkMapper {
	
	List<PtnrLinkInfoVO> getPtnrLinkLstInfo(PtnrLinkInfoVO vo) throws EgovBizException;
	
	/* 정산파일 업로드 STEP1 ~ 2*/
	/* 제주항공 */
	List<PtnrRetryPointVO> getJejuPointList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int deleteJejuPointList() throws EgovBizException;
	
	int insertJejuPointList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	/* 엠하우스 */
	List<PtnrRetryPointVO> getGiftiPointList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int deleteGiftiPointList() throws EgovBizException;
	
	int insertGiftiPointList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	/* 티머니 */
	List<PtnrRetryPointVO> getTmoneyPointList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int deleteTmoneyPointList() throws EgovBizException;
	
	int insertTmoneyPointList(PtnrRetryPointVO paramVO) throws EgovBizException;

	/* 구글Play */
	List<PtnrRetryPointVO> getGooglePlayPointList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int deleteGooglePlayPointList() throws EgovBizException;
	
	int insertGooglePlayPointList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	/* 롯데멤버스 */
	List<PtnrRetryPointVO> getLPointList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int deleteLPointList() throws EgovBizException;
	
	int insertLPointList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int deleteLPointInterface() throws EgovBizException;
	
	int insertLpointInterface(String partnerId) throws EgovBizException;
	
	String getLPointReqHeader() throws EgovBizException;
	
	List<HashMap<String, Object>> getLPointReqBody() throws EgovBizException;
	
	String getLPointReqTrailer() throws EgovBizException;
	
	/* 정산파일 업로드 공통 */
	List<PtnrRetryFileVO> getPartnerSubList(String ifNo) throws EgovBizException;
	
	int insertPointFile(PtnrRetryFileVO paramVO) throws EgovBizException;
	
	int updatePointFile(PtnrRetryFileVO paramVO) throws EgovBizException;
	
	/* 정산파일 업로드 STEP3 */
	List<PtnrRetryFileVO> getPointFileUpList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int updatePointFileUp(PtnrRetryFileVO paramVO) throws EgovBizException;
	
	
	/* 정산결과 파일 다운로드 STEP1 */
	List<PtnrRetryFileVO> getPointFileDownList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int updatePointFileDown(PtnrRetryFileVO paramVO) throws EgovBizException;
	
	/* 정산결과 파일 읽기 STEP2 */
	List<PtnrRetryFileVO> getPointFileReadList(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	/* 제휴사별 정산 파일 읽기 STEP3 */
	/* 제주항공 */
	int updateJejuPointMst(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int updatePartnerJejuair(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	/* 엠하우스 */
	int updateGiftiPointMst(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int updatePartnerGifti(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	/* 티머니 */
	int updateTmoneyPointMst(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int updatePartnerTmoney(PtnrRetryPointVO paramVO) throws EgovBizException;

	/* 구글Play */
	int updateGooglePlayPointMst(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	int updatePartnerGooglePlay(PtnrRetryPointVO paramVO) throws EgovBizException;
	
	/* 롯데멤버스 */
	int updateLPointInterface(PtnrLPointInterfaceVO vo) throws EgovBizException;

	int updateLPointPointMst(PtnrLPointInterfaceVO vo) throws EgovBizException;
	
	int updatePartnerLPoint(PtnrLPointInterfaceVO vo) throws EgovBizException;
}
