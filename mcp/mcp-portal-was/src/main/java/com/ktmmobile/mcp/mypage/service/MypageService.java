/**
 *
 */
package com.ktmmobile.mcp.mypage.service;

import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.McpMrktHistDto;
import com.ktmmobile.mcp.common.dto.db.McpRequestAgrmDto;
import com.ktmmobile.mcp.mypage.dto.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ANT_FX700_02
 *
 */
public interface MypageService {
    /**
    * @Description : MCP 휴대폰 회선관리 리스트를 가지고 온다.
    * @param mcpUserCntrMngDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 12.
    */
    public List<McpUserCntrMngDto> selectCntrList(String userid);

    /**
     * 로그인한 사용자 고객구분 조회
     * @param custId
     * @return
     */
    public String selectCustomerType(String custId);

    /**
    * @Description : 휴대폰 회선에 따른 요금제 정보를 가지고 온다.
    * @param svcCntrNo
    * @return McpUserCntrMngDto
    * @Author : ant
    * @Create Date : 2016. 1. 12.
    */
    public McpUserCntrMngDto selectSocDesc(String svcCntrNo);

    /**
    * @Description : 휴대폰 회선에 따른 단말기할인 정보를 가지고 온다.
    * @param svcCntrNo
    * @return MspJuoAddInfoDto
    * @Author : ant
    * @Create Date : 2016. 1. 12.
    */
    public MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo);

    /**
    * @Description : 휴대폰 모델 ID 에 따른 이미지 경로를 가지고 온다.
    * @param modelId
    * @return NmcpProdImgDtlDto
    * @Author : ant
    * @Create Date : 2016. 1. 22.
    */
    public NmcpProdImgDtlDto selectHpImgPath(String modelId);

    /**
     * @Description : 접속한 횟수를 가져온다
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    public McpRetvRststnDto retvRstrtn(HashMap<String, String> map);

    /**
     * @Description : 접속한 횟수를 설정한다
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    public void retvRstrtnInsert(HashMap<String, String> map);

    /**
     * @Description : 접속제한 횟수감소
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    public void retvRstrtnUpCnt(HashMap<String, String> map);

    /**
     * @Description : 접속한 횟수, 접속일자 현재일자로 초기화
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    public void retvRstrtnUpSysDate(HashMap<String, String> map);

    /**
     * 부가서비스 조회
     * @param ncn
     * @return
     */
    public List<McpRegServiceDto> selectRegService(String ncn);

    public McpFarPriceDto selectFarPricePlan(String ncn);

    public List<McpFarPriceDto> selectFarPricePlanList(String rateCd);

    public int countFarPricePlanList(String rateCd);

    /**
     * @Description : 제주항공 회원 아이디 가지고 오기
     * @param String contractNum
     * @return int
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    public String selectJejuid(String contractNum);

    /**
     * @Description : 제주항공 회원 아이디 기간 종료 업데이트
     * @param Map<String, Stirng> map
     * @return void
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    public void updatePointMgmt(Map<String,String> map);

    /**
     * @Description : 제주항공 회원 아이디 신규 인서트
     * @param Map<String, Stirng> map
     * @return void
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    public void insertPointMgmt(Map<String,String> map);

    /**
     * @Description : 제주항공 회원 아이디 설정
     * @param Map<String, Stirng> map
     * @return void
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    public void setJejuPointMgmt(Map<String, String> map);

    /**
     * @Description : 원부증명서 가지고 오기
     * @param String userId
     * @return Map<String, String>
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    public Map<String, Object> selectCertHist(String userId);

    /**
     * @Description : 원부증명서 신규 인서트
     * @param HashMap<String, Stirng> map
     * @return void
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    public void insertCertHist(Map<String,String> map);

    /**
     * @Description : 단말 할부개월, 단말 할부원금 가지고 오기
     * @param String ncn
     * @return Map<String, String>
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    public Map<String, BigDecimal> selectModelSaleInfo(String ncn);

    /**
     * @Description : 요금제 변경 정보 select
     * @param String ncn
     * @return  Map<String,String>
     * @Author : ant
     * @Create Date : 2016. 3.21
     */
    public String selectFarPriceAddInfo(Map<String,String> map);


    /**
     * @Description : 제주항공 포인트 리스트
     * @param String contractNum,int skipResult,int maxResult
     * @return  List<JehuDto>
     * @Author : ntki1741
     * @Create Date : 2016. 4.22
     */
    public List<JehuDto> selectJehuList(String contractNum,int skipResult,int maxResult);


    /**
     * @Description : 제주항공 포인트 리스트
     * @param String contractNum
     * @return  List<JehuDto>
     * @Author : ntki1741
     * @Create Date : 2016. 4.22
     */
    public List<JehuDto> selectJehuListAll(String contractNum);


    /**
     * @Description : 제주항공 포인트 리스트갯수
     * @param String contractNum
     * @return int
     * @Author : ntki1741
     * @Create Date : 2016. 4.22
     */
    public int selectJehuListCnt(String contractNum);


    /**
     * @Description : 금융 제휴 변경 이력 저장
     * @param mcpRateChgDto
     * @return
     */
    public McpRateChgDto saveNmcpRateChgHist(McpRateChgDto mcpRateChgDto);


    /**
     * @Description : 금융 제휴 변경 이력 조회
     * @param mcpRateChgDto
     * @return
     */
    public McpRateChgDto selectNmcpRateChg(McpRateChgDto mcpRateChgDto);

    /**
     * @Description : 금융 제휴 변경 서식지 재생성
     * @param mcpRateChgDto
     * @return
     */
    public McpRateChgDto reMakeFinanceTemplate(McpRateChgDto mcpRateChgDto);

    /**
     * @Description : 사용량조회 서비스 2시간동안 조회 횟수
     * @param HashMap<String, String>
     * @return
     */
    public int selectCallSvcLimitCount(HashMap<String, String> hm);

    /**
     * @Description : 사용량 조회 로그 저장
     * @param mcpRateChgDto
     * @return int
     */
    public void insertMcpSelfcareStatistic(HashMap<String, String> hm);

    /**
     * @Description : 재약정 요청 정보 저장
     * @param McpRequestAgrmDto
     * @return void
     * @Author : papier
     * @Create Date : 2019. 10. 02.
     */
    public boolean insertMcpRequestArm(McpRequestAgrmDto mcpRequestAgrmDto);

    /**
     * @Description : 추천 아이디 조회
     * @param
     * @return  COMMEND_ID_
     * @Author : papier
     * @Create Date : 2020. 07. 17.
     */
    public String getCommendId(String contractNum);

    /**
     * @Description : 친구 추천 현황 조회
     * @param
     * @return
     * @Author : papier
     * @Create Date : 2020. 07. 17.
     */
    public List<CommendStateDto> getCommendStateList(String commendId);



    /**
     * @Description : 친구 추천 현황 조회
     * @param
     * @return
     * @Author : papier
     * @Create Date : 2020. 07. 17.
     */
    public List<CommendStateDto> getCommendStateList(String commendId, MyPageSearchDto searchVO);

    /**
     * @Description : 정회원 아닌 사용자 요금제 조회
     * @param
     * @return
     * @Author : papier
     * @Create Date : 2020. 07. 17.
     */
    public McpUserCntrMngDto selectSocDescNoLogin(String svcCntrNo);

    /**
     * @Description : 휴대폰 회선정보 가지고 온다.
     * @param mcpUserCntrMngDto
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 12.
     */
     public McpUserCntrMngDto selectCntrListNoLogin(String contractNum);

     public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto);

     public List<SuspenChgTmlDto> selectSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto);
     public List<SuspenChgTmlDto> selectSuspenChgTmpList();
     public int suspenChgUpdate(SuspenChgTmlDto reqDto);


     /**
      * @Description : 부가서비스 정보 조회
      * @param contractNum , soc ,intAddMonths
      * @return JuoFeatureDto
      * @Author : papier
      * @Create Date : 2021. 3. 11.
      */
     public JuoFeatureDto getJuoFeature(JuoFeatureDto juoFeatureDto);

    /**
     * @Description : 약정정보
     * @param
     * @return MspJuoAddInfoDto
     * @Author : papier
     * @Create Date : 2021. 11. 19.
     */
    public MspJuoAddInfoDto getEnggInfo(String contractNum) ;

    /**
     * @Description : 개통 채널정보
     * @param
     * @return MspJuoAddInfoDto
     * @Author : papier
     * @Create Date : 2021. 11. 19.
     */
    public MspJuoAddInfoDto getChannelInfo(String contractNum) ;


    /**
     * @Description : 해지 해야 할 부가 서비스 조회
     * @param  ctn : 전화번호
     * @return List<McpRequestSaleinfoDto>
     * @Author : papier
     * @Create Date : 2021. 11. 05.
     */
    public List<McpUserCntrMngDto> getCloseSubList(String contractNum);

    /**
     * @Description : 서비스 변경 이력 저장 처리
     * @param
     * @return boolean
     * @Author : papier
     * @Create Date : 2021. 11. 05.
     */

    public boolean insertServiceAlterTrace(McpServiceAlterTraceDto serviceAlterTrace);

    /**
     * @Description : 요금제셀프변경실패 INSERT
     * @param
     * @return boolean
     * @Author : papier
     * @Create Date : 2021. 11. 05.
     */
    public boolean insertSocfailProcMst(McpServiceAlterTraceDto serviceAlterTrace) ;

    /**
     * @Description : 가입 해야 할 부가 서비스 조회
     * @param
     * @return List<McpRequestSaleinfoDto>
     * @Author : papier
     * @Create Date : 2021. 11. 05.
     */
    public List<McpUserCntrMngDto> getromotionDcList(String toSocCode);

    /**
     * 쉐어링 가입시 청구계정조회
     * @param contractNum
     * @return
     */
    public String selectBanSel(String contractNum);

    public BillWayChgDto getMspData(String contractNum);

    public void insertMcpBillwayResend(BillWayChgDto billWayChgDto);

    public boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession);

    /**
     * 다중회선 중 후불건 조회
     * @param cntrList
     * @return
     */
    public List<McpUserCntrMngDto> selectPoList(List<McpUserCntrMngDto> cntrList);


    /**
     * @Description : 최근 60분 내 요금제 변경 성공 이력 확인
     * @param
     * @return int
     * @Author : papier
     * @Create Date : 2023. 05. 08.
     */
    public int checkAllreadPlanchgCount(McpServiceAlterTraceDto serviceAlterTrace) ;

    List<McpMrktHistDto> selectExistingConsent(String userId); //수정

    /**
     * @Description : 요금제변경 후 MSP_DIS_APD(평생할인 부가서비스 기적용 대상)에 insert
     * @param : McpUserCntrMngDto
     * @return : int
     * @Author : wooki
     * @Create : 2023.12
     */
    public int insertDisApd(McpUserCntrMngDto apdDto);

    /**
     * @Description : 요금제변경 후 평생할인 프로모션(오프라인) 아이디 가져오기
     * @param : String
     * @return : String
     * @Author : wooki
     * @Create : 2024.01
     */
    public String getChrgPrmtIdSocChg(String rateCd);

    /**
     * @Description : 계약번호로 정회원 정보 가져오기
     * @param : String
     * @return : String
     * @Author : 82243643
     * @Create : 2024.12
     */
    public McpUserCntrMngDto getCntrInfoByContNum(String contractNum);

    /**
     * @Description : 인가된 사용자 체크
     * @param : String
     * @return : String
     * @Author : 82243643
     * @Create : 2024.12
     */
    public Map<String, String> checkAuthUser(String name, String userSsn);

    /*
     * 평생할인 정상화 진행 요청 건
     */
    public List<SuspenChgTmlDto> selectChrgPrmtCheckTmp();
    public int updateChrgPrmtCheckTmp(SuspenChgTmlDto suspenChgTmlDto);
    public int insertSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto);


    /**
    * @Description : 서식지 여부 체크
    */
    public String getChangeOfNameData(long custReqSeq,String reqType);

    /**
     * @Description : QoS 제공 여부 확인
     * @param
     * @return String
     * @Author : papier
     * @Create Date : 2025. 12. 10.
     */
    public Map<String, String> selectRateMst(String rateCd);
}
