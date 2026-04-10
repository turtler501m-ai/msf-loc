package com.ktmmobile.mcp.msp.mapper;

import com.ktmmobile.mcp.msp.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface MspMapper {

    /**
     * 현재 단품 ID 로 해당 대표모델ID를 조회한다.
     * @param prdtId
     * @return
     * @throws Exception
     */
    PhoneMspDto findMspPhoneInfo(String contractNum);

    /**
     * 판매정책정보를 조회한다.상품과 상관없이 기관별 조회를 한다.
     * @param MspSalePlcyMstDto mspSalePlcyMstDto
     * @return
     * @throws Exception
     */
    List<MspSalePlcyMstDto> findMspSalePlcyInfoByOnlyOrgn(MspSalePlcyMstDto mspSalePlcyMstDto);

    /**
     * 판매정책정보를 조회한다.
     * @param MspSalePlcyMstDto mspSalePlcyMstDto
     * @return
     * @throws Exception
     */
    List<MspSalePlcyMstDto> findMspSaleOrgnMst(MspSalePlcyMstDto mspSalePlcyMstDto);

    /**
     * MSP 판매 상품및 수수료 정보 조회.
     * @param MspSalePrdtMstDto mspSalePrdtMstDto
     * @return
     * @throws Exception
     */
    MspSalePrdtMstDto findMspSalePrdMst (MspSalePrdtMstDto mspSalePrdtMstDto);

    /**
     * MSP 에서 제조사/공급사 정보를 조회한다.
     * @param MspOrgDto mspOrgDto
     * @return
     * @throws Exception
     */
    List<MspOrgDto> listOrgnMnfctMst (MspOrgDto mspOrgDto);

    /**
     * MSP 에서 제조사/공급사 정보를 조회한다.
     * @param
     * @return
     * @throws Exception
     */
    List<MspOrgDto> listOrgnMnfctMstRe (String prodType);

    /**
     * 해당정책코드에 해당하는 판매요금제 정보 리스트 조회.
     * @param MspRateMstDto mspRateMstDto
     * @return
     * @throws Exception
     */
    List<MspRateMstDto> listMspRateMst(MspRateMstDto mspRateMstDto);



    List<MspRateMstDto> listMspRateAgrm(MspRateMstDto mspRateMstDto);

    /**
     * 해당정책코드에 해당하는 판매요금제 정보 조회.
     * @param MspRateMstDto mspRateMstDto
     * @return
     * @throws Exception
     */
    MspRateMstDto findMspRateMst(MspRateMstDto mspRateMstDto);

    /**
     * 해당정책코드에 해당하는 약정개월정보 리스트 조회.
     * @param salePlcyCd
     * @return
     * @throws Exception
     */

    List<MspSaleAgrmMst> listMspSaleAgrmMst(String salePlcyCd);

    List<MspSaleAgrmMst> listMspSaleAgrmMst2(MspSaleAgrmMst mspSaleAgrmMst);


    /**
     * @param salePlcyCd
     * @return
     * @throws Exception
     */
    MspSalePlcyMstDto getMspSalePlcyMst(String salePlcyCd);

    /**
     * @param grpCd, cdVal
     * @return
     * @throws Exception
     */
    CmnGrpCdMst findCmnGrpCdMst(CmnGrpCdMst cmnGrpCdMst);

    /**
     * @param orgnId, sprtTp, plcySctnCd, prdtSctnCd, plcyTypeCd
     * @return
     * @throws Exception
     */
    List<MspRateMstDto> listRateByOrgnInfos(MspSalePlcyMstDto mspSalePlcyMstDto);

    /**
     * 정책코드로 해당정책에 속해있는 요금제 리스트를 가져온다.
     * @param MspRateMstDto mspRateMstDto
     * @return
     * @throws Exception
     */
    List<MspRateMstDto> listMspRateMspBySalePlcyCd(MspRateMstDto mspRateMstDto);

    /**
     * @param salePlcyCd
     * @return
     * @throws Exception
     */
    MspSalePlcyMstDto findMspSalePlcyInfo(String salePlcyCd);

    /**
     * 정책에 해당하는 상품의 상세정보를 가져온다.
     * @param salePlcyCd
     * @return
     * @throws Exception
     */
    List<MspSalePrdtMstDto> listMspSalePrdtMstBySalePlcyCd(String salePlcyCd);

    /**
     * 요금제 목록을 가져온다.
     * @param
     * @return
     * @throws Exception
     */
    List<MspNoticSupportMstDto> listMspOfficialSupportRateNm();

    /**
     * 공시지원금 목록을 가져온다.
     * @param
     * @return
     * @throws Exception
     */
    List<MspNoticSupportMstDto> listMspOfficialNoticeSupport(MspNoticSupportMstDto mspNoticSupportMstDto, RowBounds rowBounds);

    /**
     * 공시지원금 목록 카운트를 가져온다.
     * @param MspNoticSupportMstDto mspNoticSupportMstDto
     * @return
     * @throws Exception
     */
    int listMspOfficialNoticeSupportCount(MspNoticSupportMstDto mspNoticSupportMstDto);

    /**
     * 계약번호의 주민번호.
     * @param contractNum
     * @return
     * @throws Exception
     */
    String getCustomerSsn(String contractNum);

    /**
     * @param searchUsimNo
     * @return
     * @throws Exception
     */
    int sellUsimMgmtCount(String searchUsimNo);

    /**
     * @param searchUsimNo
     * @return
     * @throws Exception
     */
    String sellUsimMgmtOrgnId(String searchUsimNo);

    /**
     * 요금제 정보 리스트 조회 .
     * @param rateCd
     * @return
     * @throws Exception
     */
    MspRateMstDto getMspRateMst(String rateCd);

    /**
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    int juoSubIngoCount(String subscriberNo);

    /**
     * 할인금액을 조회.
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    List<MspSaleSubsdMstDto> listMspSaleMst(MspSaleSubsdMstDto mspSaleSubsdMstDto);

    /**
     * 상품의 판매금액관련 상세내역을 조회 한다.
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    MspSaleSubsdMstDto findMspSaleSubsdMst(
            MspSaleSubsdMstDto mspSaleSubsdMstDto);

    /**
     * 최저가를 구하기 위한 해당 상품의 요금제 정보 1건 조회 한다.
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    MspSaleSubsdMstDto getLowPriceChargeInfoByProd(
            MspSaleSubsdMstDto mspSaleSubsdMstDto);

    /**
     * 최저가를 구하기 위한 해당 상품의 요금제 정보 리스트를 조회 한다.
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    List<MspSaleSubsdMstDto> selectMspSaleSubsdMstListWithRateInfo(
            MspSaleSubsdMstDto mspSaleSubsdMstDto);

    /**
     * 최저가를 구하기 위한 해당 상품의 요금제 정보 리스트를 조회 한다.
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    List<MspSaleSubsdMstDto> selectMspSaleSubsdMstListForLowPrice(
            MspSaleSubsdMstDto mspSaleSubsdMstDto);


    /**
     * 정책정보를 조회
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    List<MspSaleSubsdMstDto> salePlcyMstList(
            MspSaleSubsdMstDto mspSaleSubsdMstDto);

    /**
     * 프로모션 할인 금액 조회
     * @param mspSaleSubsdMstDto
     * @return
     */
    Integer getromotionDcAmt(MspSaleSubsdMstDto mspSaleSubsdMstDto);


    /**
     * 스캔 이미지 보정 처리
     * MSP_REQUEST_DTL@DL_MSP  T1.SCAN_ID = '999999' 업데이트 처리
     */
    int updateScanIdForDtl();

    /**
    * 스캔 이미지 보정 처리
    * MSP_JUO_SUB_INFO@DL_MSP  T1.SCAN_ID = '999999' 업데이트 처리
    */
    int updateScanIdForSubInfo();

    /**
     * 결합 가능 요금제코드 및 부가서비스 코드
     * MSP_COMB_RATE_MAPP@DL_MSP
     */
    MyCombinationResDto selectMspCombRateMapp(String pRateCd);

    /**
     * 약정유형 조회 (무약정 포함)
     * MSP_SALE_AGRM_MST@DL_MSP RA
     */
    List<MspSaleAgrmMst> selectMspSaleAgrmMst(String salePlcyCd);

    List<CouponOutsideDto> selectAllCoupons(List<String> arrTest);

    int selectMspMemeberCheck(String userVO);

    Integer getKtTripleDcAmt(@RequestBody String rateCd);

    int getMspJuoSubInfoCount(String customerId);

    List<Map<String, String>> mspDisAddList(String prmtId);

    int insertApiTrace(ApiTraceDto apiTraceDto);

    void updateApiTrace(ApiTraceDto apiTraceDto);

    String getMspJuoSubStatus(String contractNum);



    String getInetInfo(HashMap<String, String> map);

    List<FqcDto> selectFqcPlcyPlaList(String fqcPlcyCd);

    List<MspRateMstDto> selectAllMspRateList();

    int selectFqcBasListCnt(FqcDto fqcDto);

    List<FqcDto> selectFqcBasList(FqcDto fqcDto, RowBounds rowBounds);

    List<FqcDto> selectFqcBasExcelList(FqcDto fqcDto);

    List<String> getAcenVocAgntListByOrgnId(String vocAgntCd);

    List<CmnGrpCdMst> getCmnGrpCdList(CmnGrpCdMst cmnGrpCdMst);

    int insertNmcpUseRateInfo(Map<String, String> paramMap);

    List<MspCombineDto> getSyncCombineSoloListWithMspInfo(int baseDate);
}
