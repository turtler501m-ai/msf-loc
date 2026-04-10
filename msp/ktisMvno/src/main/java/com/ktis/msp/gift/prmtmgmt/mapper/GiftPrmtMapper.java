package com.ktis.msp.gift.prmtmgmt.mapper;

import java.util.List;

import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("giftPrmtMapper")
public interface GiftPrmtMapper {
	
	// 사은품 프로모션 목록 조회
	List<GiftPrmtMgmtVO> getGiftPrmtList(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;
	
	// 사은품 프로모션 조직 목록 조회
	List<GiftPrmtMgmtSubVO> getGiftPrmtOrgnList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException;
	
	// 사은품 프로모션 요금제 목록 조회
	List<GiftPrmtMgmtSubVO> getGiftPrmtSocList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException;
	
	// 사은품 프로모션 사은품 목록 조회
	List<GiftPrmtMgmtSubVO> getGiftPrmtPrdtList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException;
	
	// 사은품 프로모션 중복 체크
	List<GiftPrmtMgmtVO> getGiftPrmtDupByInfo(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;

	// 사은품 프로모션 수정을 위한 중복 체크
	List<GiftPrmtMgmtVO> getGiftPrmtDupByInfoToMod(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;
	
	// 사은품 프로모션 마스터 추가
	int insertMspGiftPrmt(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;
	
	//사은품 프로모션 마스터 수정
	int updateMspGiftPrmt(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;
	
	// 사은품 프로모션 조직 추가
	int insertMspGiftPrmtOrg(GiftPrmtMgmtSubVO giftPrmtMgmtVO) throws EgovBizException;
	
	//사은품 프로모션 조직 삭제
	int deleteMspGiftPrmtOrg(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;
	
	// 사은품 프로모션 요금제 추가
	int insertMspGiftPrmtSoc(GiftPrmtMgmtSubVO giftPrmtMgmtVO) throws EgovBizException;
	
	//사은품 프로모션 요금제 삭제
	int deleteMspGiftPrmtSoc(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;
	
	// 사은품 프로모션 사은품 추가
	int insertMspGiftPrmtPrdt(GiftPrmtMgmtSubVO giftPrmtMgmtVO) throws EgovBizException;
	
	//사은품 프로모션 사은품 삭제
	int deleteMspGiftPrmtPrdt(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;
	
	// 사은품 프로모션 정보 변경
	int updGiftPrmtByInfo(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;

	// 모집경로 목록 조회
	List<GiftPrmtMgmtSubVO> getGiftPrmtOnoffList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException;
	
	// 모집경로 추가
	int insertMspGiftPrmtOnOff(GiftPrmtMgmtSubVO giftPrmtMgmtVO) throws EgovBizException;
	
	//모집경로 삭제
	int deleteMspGiftPrmtOnOff(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;

	// 대상제품 목록 조회
	List<GiftPrmtMgmtSubVO> getGiftPrmtModelList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException;
	
	// 대상제품 추가
	int insertMspGiftPrmtModel(GiftPrmtMgmtSubVO giftPrmtMgmtVO) throws EgovBizException;
	
	// 대상제품 삭제
	int deleteMspGiftPrmtModel(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException;
	
	//요금제선택 엑셀다운로드
	List<GiftPrmtMgmtSubVO> getExcelUpSocList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException;
	
	List<GiftPrmtMgmtSubVO> getExcelUpModelList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException;

	//사은품 프로모션 관리 엑셀다운로드
    List<?> getGiftPrmtListEx(GiftPrmtMgmtVO searchVO) throws EgovBizException;
    
    //사은품 프로모션 권한여부 조회
    int groupUserAuth(String usrId);
	
}
