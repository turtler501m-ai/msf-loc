package com.ktmmobile.mcp.mypage.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.mypage.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestBody;

import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.common.dto.MspCommDatPrvTxnDto;
import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
import com.ktmmobile.mcp.msp.dto.MspSaleSubsdMstDto;

@Mapper
public interface MypageMapper {

    /**
     * MCP 휴대폰 회선관리 리스트를 가지고 온다.
     * @param userId
     * @return List<McpUserCntrMngDto>
     * @throws Exception
     */
    List<McpUserCntrMngDto> selectCntrList(HashMap<String, String> map);


    List<McpUserCntrMngDto> selectCntrMngList(HashMap<String, String> map);


    /**
     * 로그인한 사용자 구분
     * 개인구분, 법인
     * @param custId
     * @return
     */
    String  selectCustomerType(String custId);


    /**
     * 휴대폰 회선에 따른 요금제 정보를 가지고 온다.
     * @param svcCntrNo
     * @return McpUserCntrMngDto
     * @throws Exception
     */
    McpUserCntrMngDto selectSocDesc(String svcCntrNo);

    /**
     * 휴대폰 회선에 따른 단말기할인 정보를 가지고 온다.
     * @param svcCntrNo
     * @return MspJuoAddInfoDto
     * @throws Exception
     */
    MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo);

    /**
     *
     * @param map
     * @return List<UserVO>
     * @throws Exception
     */
    List<UserVO> selectUserMultiLine(HashMap<String, String> map);

    /**
     *
     * @param map
     * @return String
     * @throws Exception
     */
    String selectContractNum(HashMap<String, String> map);



    public Map<String, String> selectContractObj(@RequestBody HashMap<String, String> paraMap) ;

    /**
     *
     * @param contractNum
     * @return List<MspPpsRcgTesDto>
     * @throws Exception
     */
    List<MspPpsRcgTesDto> selectPPSList(String contractNum);

    /**
     *
     * @param ncn
     * @return List<McpRegServiceDto>
     * @throws Exception
     */
    List<McpRegServiceDto> selectRegService(String ncn);

    /**
     *
     * @param ncn
     * @return McpFarPriceDto
     * @throws Exception
     */
    McpFarPriceDto selectFarPricePlan(String ncn);


    /**
     *
     * @param ncn
     * @return McpFarPriceDto
     * @throws Exception
     */
    int getPromotionDcSumAmt(String ncn);


    public MspSaleSubsdMstDto getRateInfo(String rateCd);

    List<MspSaleSubsdMstDto> getRateInfoListByPromotionDate(String promotionDate);

    /**
     *
     * @param rateCd
     * @return List<McpFarPriceDto>
     * @throws Exception
     */
//	List<McpFarPriceDto> selectFarPricePlanList(String rateCd, new RowBounds(skipResult, maxResult));
    List<McpFarPriceDto> selectFarPricePlanList(String rateCd);

    /**
     *
     * @param rateCd
     * @return int
     * @throws Exception
     */
    int selectFarPricePlanListCount(String rateCd);

    /**
     * 요금제 변경 정보 select
     * @param map
     * @return String
     * @throws Exception
     */
    String selectFarPriceAddInfo(HashMap<String, String> map);

    /**
     * 제주항공 포인트 리스트
     * @param map
     * @return List<JehuDto>
     * @throws Exception
     */
    List<JehuDto> selectJehuList(HashMap<String, String> map);

    /**
     * 제주항공 포인트 리스트갯수
     * @param contractNum
     * @return int
     * @throws Exception
     */
    int selectJehuListCount(String contractNum);

    /**
     * 제주항공 포인트 리스트갯수
     * @param map
     * @return List<JehuDto>
     * @throws Exception
     */
    List<JehuDto> selectJehuListAll(String contractNum);

    /**
     * 회선별 마케팅 동의 여부 조회
     * @param contractNum
     * @return Map<String, String>
     * @throws Exception
     */
    public Map<String, String> selectMspMrktAgrYn(String contractNum);

    /**
     * 회선별 마케팅 동의 여부 저장
     * @param ctn
     * @param mrktAgr
     */
    public void callMspMrktAgr(HashMap<String, String> map);

    /**
     * 선불 요금제 사용 여부 조회
     * @param contractNum
     * @return int
     * @throws Exception
     */
    int selectPrePayment(String contractNum);

    /**
     * 친구 추천 현황 조회
     * @param commendId
     * @return List<CommendStateDto>
     */
    public List<CommendStateDto> selectCommendStateList(String commendId);


    /**
     * 친구 추천 현황 조회 && KT인터넷 가입 현황
     * @return List<CommendStateDto>
     */
    public List<CommendStateDto> selectCommendAllStateList(HashMap<String, String> map);



    /**
     * 친구 추천 현황 조회
     * @param  map commendId : 친구 조대 아이디  , lstComActvDate : 개통일 기준
     *
     * @return List<CommendStateDto>
     */
    public List<CommendStateDto> getFriendInvitationList(HashMap<String, String> map);

    /**
     *
     * @param map
     * @return String
     */
    public String selectSvcCntrNo(HashMap<String, String> map);

    /**
     * 정회원 아닌 사용자 요금제 조회
     * @param svcCntrNo
     * @return McpUserCntrMngDto
     */
    public McpUserCntrMngDto selectSocDescNoLogin(String svcCntrNo);

    /**
     * 휴대폰 회선정보 가지고 온다.
     * @param McpUserCntrMngDto
     * @return McpUserCntrMngDto
     */
    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto mcpUserCntrMngDto);

    /**
     * 부가서비스 정보 조회
     * @param JuoFeatureDto
     * @return JuoFeatureDto
     */
    public JuoFeatureDto selectJuoFeature(JuoFeatureDto juoFeatureDto);

    void callMcpMrktAgrNew(HashMap<String, String> hm);

    void callMcpMrktAgr(HashMap<String, String> hm);

    Map<String, String> callMcpRateChgImg(McpRateChgDto mcpRateChgDto);

    /**
     * 약정정보 조회
     * @param contractNum
     *@Create Date : 2021. 11. 19.
     * @return
     */
    public MspJuoAddInfoDto getEnggInfo(String contractNum);

    /**
     * 개통 채널정보
     * @param contractNum
     * @return
     */
    MspJuoAddInfoDto getChannelInfo(String contractNum);

    /**
     * 해지 해야 할 부가 서비스 조회
     * @param contractNum
     * @return
     */
    List<McpUserCntrMngDto> getCloseSubList(String contractNum);

    /**
     * @Description : 요금제셀프변경실패 INSERT
     * @param
     * @return boolean
     * @Author : papier
     * @Create Date : 2021. 11. 05.
     */
    boolean insertSocfailProcMst(McpServiceAlterTraceDto serviceAlterTrace);
    /**
     * @Description : 가입 해야 할 부가 서비스 조회
     * @param
     * @return List<McpRequestSaleinfoDto>
     * @Author : papier
     * @Create Date : 2021. 11. 05.
     */

    List<McpUserCntrMngDto> getromotionDcList(String rateCd);

    int countFarPricePlanList(String rateCd);

    // 부가서비스 정보 조회
    JuoFeatureDto getJuoFeature(JuoFeatureDto juoFeatureDto);

    // My 사은품 목록 및 수
    //List<MyGiftDto> getCustList(String contractNum);
    List<MyGiftDto> getCustList(MyGiftDto myGiftDto, RowBounds rowBounds);
    int getCustListCount(MyGiftDto myGiftDto);

    JuoSubInfoDto selectJuoSubInfo(HashMap<String, String> map);

    /**
     * 쉐어링 개통시 청구계정 번호 조회
     * @param contractNum
     * @return
     */
    String selectBanSel(String contractNum);

    /**
     * 명세서 재발행을 위한 전산 정보 조히
     * */
    BillWayChgDto billWayReSend(String contractNum);


    /**
     * 유심변경 결과 확인
     * @param map
     * @return String
     * @throws Exception
     */
    int usimChgChk(HashMap<String, String> map);


    /**
     * MCP 휴대폰 회선관리 리스트를 가지고 온다. (명의변경)
     */
    List<McpUserCntrMngDto> selectCntrListNmChg(HashMap<String, String> map);


    /**
     * 통신자료 제공내역 요청리스트 가져오기
     */
    List<MspCommDatPrvTxnDto> pvcCommDataList(HashMap<String, String> map);

    /**
     * 통신자료 제공내역 신청 insert
     */
    int insertPrvCommData(HashMap<String, String> map);

    /**
     * 통신자료 제공내역상세 출력정보 가져오기
     */
    List<MspCommDatPrvTxnDto> pvcCommDataDtlList(HashMap<String, String> map);

    /**
     * 고객이 가입한 모든 회선정보를 가져온다.
     * @param userId
     * @return List<McpUserCntrMngDto>
     * @throws Exception
     */
    List<McpUserCntrMngDto> selectOwnPhoNumList(HashMap<String, String> map);

    /**
     * 가입번호 조회이력을 저장한다.
     */
    int insertOwnPhoNumChkHst(HashMap<String, String> map);


    /**
     * @Description : 안심보험 가입 여부 조회
     * @param contractNum
     * @return Map<String, String>
     * @Author : wooki
     * @CreateDate : 2022.10.04
     */
    Map<String, String> getInsrInfo(String contractNum);

    /**
     * @Description : 안심보험정보 조회(by insrCd)
     * @param insrCd
     * @return Map<String, String>
     * @Author : wooki
     * @CreateDate : 2022.10.20
     */
    Map<String, String> getInsrInfoByCd(String insrCd);

    /**
     * @Description : 안심보험 가입신청 내역 조회
     * @param contractNum
     * @return Map<String, String>
     * @Author : wooki
     * @CreateDate : 2022.10.20
     */
    Map<String, String> getReqInsrData(long custReqKey);

    /**
     * @Description : org 스캔아이디 조회
     * @param contractNum
     * @return String
     * @Author : wooki
     * @CreateDate : 2022.10.20
     */
    public String getOrgScanId(String contractNum);

    /**
     * @Description : 자급제 보상서비스 가입 상태 조회
     * @param contractNum
     * @return RwdOrderDto
     * @Author : hsy
     * @CreateDate : 2023.03.02
     */
    RwdOrderDto selectRwdInfoByCntrNum(String contractNum);

    /**
     * @Description : 자급제 보상서비스 리스트 조회
     * @return List<RwdDto>
     * @Author : hsy
     * @CreateDate : 2023.03.02
     */
    List<RwdDto> selectRwdProdList(String rwdProdCd);

    /**
     * 2023.03.10 hsy
     * 자급제 보상서비스 가입 시 해당 imei 사용가능 여부 체크
     * @param  rwdOrderDto
     * @return String
     */
    int checkImeiForRwd(RwdOrderDto rwdOrderDto);

    /**
     * @Description : 계약번호로 신청정보 조회
     * @param contractNum
     * @return List<RwdOrderDto>
     * @Author : hsy
     * @CreateDate : 2023.02.28
     */
    List<RwdOrderDto> selectMcpRequestInfoByCntrNum(String contractNum);

    /**
     * @Description : 자급제 보상서비스 order insert
     * @param rwdOrderDto
     * @return int
     * @Author : hsy
     * @CreateDate : 2023.02.28
     */
    int insertRwdOrder(RwdOrderDto rwdOrderDto);


    /**
     * 2023.03.13 hsy
     * 자급제 보상서비스 물리 파일명 추출
     * @return String
     */
    String selectRwdNewFileNm();

    /**
     * 2023.05.19
     * 자급제 보상서비스 서식지 정보 조회
     * @return Map<String, String>
     */
    Map<String, String> getRwdOrderData(String rwdSeq);

    /**
     * @Description : resNo으로 신청정보 조회
     * @param rwdOrderDto
     * @return int
     */
    int selectRwdInfoByResNo(RwdOrderDto rwdOrderDto);

    /**
     * @Description : 자급제 보상서비스 order update
     * @return int
     */
    int updateRwdOrder(RwdOrderDto rwdOrderDto);

    /**
     * @Description : 자급제 보상서비스 order seq 조회 By resNo
     * @return int
     */
    long getRwdOrderSeq(RwdOrderDto rwdOrderDto);

    AgreeDto selectMspMrktAgrTime(String contractNum);

    /**
     * cusInfo 이름, 생년월일 조회
     */
    Map<String, String> getUserCusInfo(String customerId);

    /**
     * @Description : 평생할인 부가서비스 기적용 대상(MSP_DIS_APD) insert
     * @param McpUserCntrMngDto
     * @return int
     * @Author : wooki
     * @CreateDate : 2023.11.10
     */
    public int insertDisApd(McpUserCntrMngDto mngDto);

    /**
     * @Description : 요금제 변경 온라인 프로모션ID 조회
     * @param String
     * @return String
     * @Author : 개발팀 김동혁
     * @CreateDate : 2024.01.19
     */
    String getChrgPrmtIdSocChg(String rateCd);

    //회선 계약정보 조회(이름,주민번호,전화번호)
    Map<String, String> selectConSsnObj(@RequestBody HashMap<String, String> paraMap);

    /**
     * 가족관계 법정대리인 조회
     * @param minorAgentSvcCntrNo
     * @return ChildInfoDto
     * @throws Exception
     */
    ChildInfoDto selectMinorAgentInfo(String minorAgentSvcCntrNo);

    /**
     * 가족관계 자녀 회선 목록 조회
     * @param famSeq
     * @return List<ChildInfoDto>
     * @throws Exception
     */
    List<ChildInfoDto> selectChildCntrList(String famSeq);

    /**
     * 가족관계 자녀 회선 조회
     * @param childInfoDto
     * @return ChildInfoDto
     * @throws Exception
     */
    ChildInfoDto selectChildCntr(ChildInfoDto childInfoDto);

}
