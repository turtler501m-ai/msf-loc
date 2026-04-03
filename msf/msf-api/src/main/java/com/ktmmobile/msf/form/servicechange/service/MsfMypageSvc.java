package com.ktmmobile.msf.form.servicechange.service;

import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.dto.db.McpMrktHistDto;
import com.ktmmobile.msf.system.common.dto.db.McpRequestAgrmDto;
import com.ktmmobile.msf.form.servicechange.dto.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MsfMypageSvc {

    /** MCP 휴대폰 회선관리 리스트 조회 */
    List<McpUserCntrMngDto> selectCntrList(String userid);

    /** 로그인한 사용자 고객구분 조회 */
    String selectCustomerType(String custId);

    /** 휴대폰 회선에 따른 요금제 정보 조회 */
    McpUserCntrMngDto selectSocDesc(String svcCntrNo);

    /** 휴대폰 회선에 따른 단말기할인 정보 조회 */
    MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo);

    /** 휴대폰 모델 ID에 따른 이미지 경로 조회 */
    NmcpProdImgDtlDto selectHpImgPath(String modelId);

    /** 접속한 횟수 조회 */
    McpRetvRststnDto retvRstrtn(HashMap<String, String> map);

    /** 접속한 횟수 설정 */
    void retvRstrtnInsert(HashMap<String, String> map);

    /** 접속제한 횟수 감소 */
    void retvRstrtnUpCnt(HashMap<String, String> map);

    /** 접속한 횟수, 접속일자 현재일자로 초기화 */
    void retvRstrtnUpSysDate(HashMap<String, String> map);

    /** 부가서비스 조회 */
    List<McpRegServiceDto> selectRegService(String ncn);

    McpFarPriceDto selectFarPricePlan(String ncn);

    List<McpFarPriceDto> selectFarPricePlanList(String rateCd);

    int countFarPricePlanList(String rateCd);

    /** 제주항공 회원 아이디 조회 */
    String selectJejuid(String contractNum);

    /** 제주항공 회원 아이디 기간 종료 업데이트 */
    void updatePointMgmt(Map<String, String> map);

    /** 제주항공 회원 아이디 신규 인서트 */
    void insertPointMgmt(Map<String, String> map);

    /** 제주항공 회원 아이디 설정 */
    void setJejuPointMgmt(Map<String, String> map);

    /** 원부증명서 조회 */
    Map<String, Object> selectCertHist(String userId);

    /** 원부증명서 신규 인서트 */
    void insertCertHist(Map<String, String> map);

    /** 단말 할부개월, 단말 할부원금 조회 */
    Map<String, BigDecimal> selectModelSaleInfo(String ncn);

    /** 요금제 변경 정보 조회 */
    String selectFarPriceAddInfo(Map<String, String> map);

    /** 제주항공 포인트 리스트 (페이징) */
    List<JehuDto> selectJehuList(String contractNum, int skipResult, int maxResult);

    /** 제주항공 포인트 리스트 전체 */
    List<JehuDto> selectJehuListAll(String contractNum);

    /** 제주항공 포인트 리스트 건수 */
    int selectJehuListCnt(String contractNum);

    /** 금융 제휴 변경 이력 저장 */
    McpRateChgDto saveNmcpRateChgHist(McpRateChgDto mcpRateChgDto);

    /** 금융 제휴 변경 이력 조회 */
    McpRateChgDto selectNmcpRateChg(McpRateChgDto mcpRateChgDto);

    /** 금융 제휴 변경 서식지 재생성 */
    McpRateChgDto reMakeFinanceTemplate(McpRateChgDto mcpRateChgDto);

    /** 사용량조회 서비스 2시간동안 조회 횟수 */
    int selectCallSvcLimitCount(HashMap<String, String> hm);

    /** 사용량 조회 로그 저장 */
    void insertMcpSelfcareStatistic(HashMap<String, String> hm);

    /** 재약정 요청 정보 저장 */
    boolean insertMcpRequestArm(McpRequestAgrmDto mcpRequestAgrmDto);

    /** 추천 아이디 조회 */
    String getCommendId(String contractNum);

    /** 친구 추천 현황 조회 */
    List<CommendStateDto> getCommendStateList(String commendId);

    /** 친구 추천 현황 조회 (검색 조건 포함) */
    List<CommendStateDto> getCommendStateList(String commendId, MyPageSearchDto searchVO);

    /** 정회원 아닌 사용자 요금제 조회 */
    McpUserCntrMngDto selectSocDescNoLogin(String svcCntrNo);

    /** 휴대폰 회선정보 조회 (비로그인) */
    McpUserCntrMngDto selectCntrListNoLogin(String contractNum);

    McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto);

    List<SuspenChgTmlDto> selectSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto);

    List<SuspenChgTmlDto> selectSuspenChgTmpList();

    int suspenChgUpdate(SuspenChgTmlDto reqDto);

    /** 부가서비스 정보 조회 */
    JuoFeatureDto getJuoFeature(JuoFeatureDto juoFeatureDto);

    /** 약정정보 조회 */
    MspJuoAddInfoDto getEnggInfo(String contractNum);

    /** 개통 채널정보 조회 */
    MspJuoAddInfoDto getChannelInfo(String contractNum);

    /** 해지 해야 할 부가 서비스 조회 */
    List<McpUserCntrMngDto> getCloseSubList(String contractNum);

    /** 서비스 변경 이력 저장 처리 */
    boolean insertServiceAlterTrace(McpServiceAlterTraceDto serviceAlterTrace);

    /** 요금제셀프변경실패 INSERT */
    boolean insertSocfailProcMst(McpServiceAlterTraceDto serviceAlterTrace);

    /** 가입 해야 할 부가 서비스 조회 (프로모션 DC) */
    List<McpUserCntrMngDto> getromotionDcList(String toSocCode);

    /** 쉐어링 가입시 청구계정 조회 */
    String selectBanSel(String contractNum);

    BillWayChgDto getMspData(String contractNum);

    void insertMcpBillwayResend(BillWayChgDto billWayChgDto);

    boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession);

    /** 다중회선 중 후불건 조회 */
    List<McpUserCntrMngDto> selectPoList(List<McpUserCntrMngDto> cntrList);

    /** 최근 60분 내 요금제 변경 성공 이력 확인 */
    int checkAllreadPlanchgCount(McpServiceAlterTraceDto serviceAlterTrace);

    List<McpMrktHistDto> selectExistingConsent(String userId);

    /** 요금제변경 후 MSP_DIS_APD(평생할인 부가서비스 기적용 대상) INSERT */
    int insertDisApd(McpUserCntrMngDto apdDto);

    /** 요금제변경 후 평생할인 프로모션(오프라인) 아이디 조회 */
    String getChrgPrmtIdSocChg(String rateCd);

    /** 계약번호로 정회원 정보 조회 */
    McpUserCntrMngDto getCntrInfoByContNum(String contractNum);

    /** 인가된 사용자 체크 */
    Map<String, String> checkAuthUser(String name, String userSsn);

    /** 평생할인 정상화 진행 요청 건 */
    List<SuspenChgTmlDto> selectChrgPrmtCheckTmp();

    int updateChrgPrmtCheckTmp(SuspenChgTmlDto suspenChgTmlDto);

    int insertSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto);

    /** 서식지 여부 체크 */
    String getChangeOfNameData(long custReqSeq, String reqType);

    /** QoS 제공 여부 확인 */
    Map<String, String> selectRateMst(String rateCd);
}
