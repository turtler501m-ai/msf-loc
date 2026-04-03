package com.ktmmobile.msf.form.newchange.dao;

import com.ktmmobile.msf.form.newchange.dto.*;
import com.ktmmobile.msf.system.common.dto.db.*;
import com.ktmmobile.msf.system.common.legacy.etc.dto.GiftPromotionDtl;
import com.ktmmobile.msf.usim.dto.UsimBasDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : AppformDao.java
 * 날짜     :
 * 작성자   :
 * 설명     : 서식지 관련 처리 서비스
 * </pre>
 */
public interface AppformDao {

    public AppformReqDto getAppFormTemp(String userId);

    /**
     *
     * @param requestKey
     * @return
     */
    public Map<String, String> getAppFormData(long requestKey);

    /**
     *
     * @param requestKey
     * @return
     */
    public Map<String, String> getAppFormUserData(long requestKey);

    /**
     * <pre>
     * 설명     : 서식지 전체 좌표 조회
     * @return
     * </pre>
     */
    public List<HashMap<String, String>> getAppFormPointList();

    /**
     * <pre>
     * 설명     : 서식지 그룹별 좌표 조회
     * @param groupCode : 서식지 그룹코드
     * @return
     * </pre>
     */
    public List<HashMap<String, String>> getAppFormPointList(String groupCode);

    /**
     * <pre>
     * 설명     : 페이지 별 좌표 조회
     * @param pageCode : 페이지 코드
     * @return
     * </pre>
     */
    public List<HashMap<String, String>> getAppFormPageList(String pageCode);

    /**
     * <pre>
     * 설명     : 스캔 서버 연동 UPDATE  처리
     * @param requestKey : 서식지 키값
     * @return
     * </pre>
     */
    public boolean updateAppFormXmlYn(long requestKey);

    /**
     * <pre>
     * 설명     : 결제 가승인 승인 처리
     * @param requestKey : 서식지 키값
     * @return
     * </pre>
     */
    public boolean updateAppForPstate(long requestKey);

    /**
     * <pre>
     * 설명     : updateMcpRequestSaleinfo
     * @param mcpRequestSaleinfoDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateMcpRequestSaleinfo(McpRequestSaleinfoDto mcpRequestSaleinfoDto);

    /**
     * <pre>
     * 설명     : 가입신청 테이블(MCP_REQUEST)
     * @param mcpRequestDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequest(McpRequestDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_고객정보 테이블(MCP_REQUEST_CSTMR)
     * @param mcpRequestCstmrDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestCstmr(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_대리인 테이블(MCP_REQUEST_AGENT)
     * @param
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestAgent(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_번호이동정보 테이블(MCP_REQUEST_MOVE)
     * @param
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestMove(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_선불충전 테이블(MCP_REQUEST_PAYMENT)
     * @param
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestPayment(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_선불충전 테이블(MCP_REQUEST_PAYMENT)
     * @param
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestSaleinfo(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_배송정보 테이블(MCP_REQUSET_DLVRY)
     * @param
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestDlvry(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_청구정보 테이블(MCP_REQUEST_REQ)
     * @param mcpRequestReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestReq(AppformReqDto appformReq);

    /**
     *
     * @param mcpRequestChangeDto
     * @return
     */
    public boolean insertMcpRequestChange(AppformReqDto appformReq);


    /**
     * <pre>
     * 설명     : 가입신청_기변사유 테이블(MCP_REQUEST_DVC_CHG)
     * @param mcpRequestReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestDvcChg(AppformReqDto appformReqDto) ;

    /**
     * <pre>
     * 설명     : 가입신청_부가서비스 테이블(MCP_REQUEST_ADDITION)
     * @param mcpRequestAdditionDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestAddition(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 프로모션 관련 부가 서비스 등록 처리(MCP_REQUEST_ADDITION)
     * @param mcpRequestAdditionDto
     * @return
     * @return: boolean
     * </pre>
     */
    public int insertMcpRequestAdditionPromotion(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 진행상태 테이블(MCP_REQUEST_STATE)
     * @param mcpRequestAdditionDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestState(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 서식지 일련번호 생성
     * @return
     * </pre>
     */
    public Long generateRequestKey();

    /**
     * <pre>
     * 설명     : 예약번호 추출
     * @return
     * @return: String
     * </pre>
     */
    public String generateResNo();

    /**
     * <pre>
     * 설명     : 개통자 확인
     * @return
     * @return: String
     * </pre>
     */
    public JuoSubInfoDto selRMemberAjax(JuoSubInfoDto juoSubInfoDto);

    /**
     * <pre>
     * 설명     : 가입신청 테이블(MCP_REQUEST) 조회
     * @param requestKey
     * @return
     * @return: McpRequestDto
     * </pre>
     */
    public McpRequestDto getMcpRequest(long requestKey);

    public McpRequestDto getMcpRequest(McpRequestDto requestDto);


    /**
     * <pre>
     * 설명     : 가입신청 테이블 여러 테이블  (MCP_REQUEST,MCP_REQUEST_CSTMR,MCP_REQUEST_REQ) 조회
     * @param requestKey
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public AppformReqDto getCopyMcpRequest(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_고객정보 테이블(MCP_REQUEST_CSTMR)조회
     * @param AppformReqDto
     * @return
     * @return: McpRequestCstmrDto
     * </pre>
     */
    public McpRequestCstmrDto getMcpRequestCstmr(long requestKey);

    /**
     * <pre>
     * 설명     : 가입신청_배송정보 테이블(MCP_REQUSET_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestDlvryDto
     * </pre>
     */
    public McpRequestDlvryDto getMcpRequestDlvry(long requestKey);

    /**
     * <pre>
     * 설명     : 가입신청_판매정보 테이블(MCP_REQUEST_SALEINFO)
     * @param requestKey
     * @return
     * @return: McpRequestSaleinfoDto
     * </pre>
     */
    public McpRequestSaleinfoDto getMcpRequestSaleinfo(long requestKey);

    /**
     * <pre>
     * 설명     : 부가서비스 목록 조회
     * @param mcpRequestAdditionDto
     * @return
     * @return: List<McpRequestAdditionDto>
     * </pre>
     */
    public List<McpRequestAdditionDto> getMcpAdditionList(
            McpRequestAdditionDto mcpRequestAdditionDto);

    /**
     * <pre>
     * 설명     : 약관 동의 테이블 저장
     * @param hm
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestClause(HashMap<String, Object> hm);

    /**
     * <pre>
     * 설명     :주문정보 사용자 검증
     * @param appformReqDto
     * @return
     * @return: int
     * </pre>
     */
    public int isOwnerCount(AppformReqDto appformReqDto);

    /**
     *
     * @param appformReqDto
     * @return
     */
    public int getMcpRequestCount(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 제주항공 요금제 여부
     * @param rateCd
     * @return
     * @return: int
     * </pre>
     */
    public int checkJejuCodeCount(String rateCd);

    /**
     * <pre>
     * 설명     : 제휴 서비스를 위한 동의 필수 인 요금제 정보
     * @param rateCd
     * @return
     * @return: int
     * </pre>
     */
    public int checkClauseJehuRatecd(String rateCd);

    /**
     * <pre>
     * 설명     : 서식지 관련 MSP 코드 조회
     * </pre>
     */
    public McpRequestDto getMspPrdtCode(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 단말색상 가져오기
     * </pre>
     */
    public String selPrdtcolCd(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     :단말기 속성 조회
     * @return
     * @return: String
     * </pre>
     */
    public String getAtribVal(HashMap<String, Object> hm) ;

    /**
     * <pre>
     * 설명     : 정책관련 상품 조회
     * </pre>
     */
    public AppformReqDto getMarketRequest(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 할부개월
     * @param AppformReqDto : 정책번호
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    public List<AppformReqDto> selectModelMonthlyList(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 약정개월
     * @param AppformReqDto : 정책번호
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    public List<AppformReqDto> selectMonthlyListMarket(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 생상정보
     * @param AppformReqDto : modelId
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    public List<AppformReqDto> selectPrdtColorList(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 서식지 페이지 코드 조회
     * @param pageCode : 페이지코드
     * @return
     * </pre>
     */
    public NmcpAppFormMstDto selectNmcpAppFormMst(String pageCode);



    /**
     * <pre>
     * 설명     : 대리점 코드 패치
     * @param cntpntShopId 접점코드
     * @return
     * @return: String
     * </pre>
     */
    public String getAgentCode(String cntpntShopId);


    /**
     * <pre>
     * 설명     : 대리점 정보 패치
     * @param cntpntShopId 접점코드
     * @return
     * @return: String
     * </pre>
     */
    public Map<String,String> getAgentInfoOjb(String cntpntShopId);

    /**
     *
     * @param mcpRequestCstmrDto
     * @return
     */
    public boolean updateMcpRequestCstmr(McpRequestCstmrDto mcpRequestCstmrDto );

    /**
     *
     * @param mcpRequestDto
     * @return
     */
    public boolean updateMcpRequest(McpRequestDto mcpRequestDto);

    /**
     *
     * @param mcpRequestMoveDto
     * @return
     */
    public boolean updateMcpRequestMove(McpRequestMoveDto mcpRequestMoveDto);

    /**
     * <pre>
     * 설명     : RequestOsst 정보
     * @return
     * @return: McpRequestOsstDto
     * </pre>
     */
    public McpRequestOsstDto getRequestOsst(McpRequestOsstDto mcpRequestOsstDto);


    /**
     * <pre>
     * 설명     : RequestOsst count
     * @return
     * @return: McpRequestOsstDto
     * </pre>
     */
    public int requestOsstCount(McpRequestOsstDto mcpRequestOsstDto);


    /**
     * <pre>
     * 설명     : 간편가입 테이블(MCP_REQUEST_OSST)
     * @param mcpRequestDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestOsst(McpRequestOsstDto mcpRequestOsstDto);


    /**
     * <pre>
     * 설명     : NP1 ORDER NO UPDATE_MCP_REQUEST_OSST
     * @param mcpRequestOsstDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateMcpRequestOsstOrdNo(McpRequestOsstDto mcpRequestOsstDto) ;

    /**
     *
     * @param appformReqDto
     * @return
     */
    public int deleteMcpRequestReq(AppformReqDto appformReqDto) ;

    /**
     *
     * @param appformReqDto
     * @return
     */
    public int deleteMcpRequestAddition(AppformReqDto appformReqDto) ;

    /**
     *
     * @param appformReqDto
     * @return
     */
    public int deleteMcpRequestAgent(AppformReqDto appformReqDto) ;

    /**
     *
     * @param appformReqDto
     * @return
     */
    public int deleteRequestPayment(AppformReqDto appformReqDto) ;

    /**
     *
     * @param appformReqDto
     * @return
     */
    public int deleteMcpRequestChange(AppformReqDto appformReqDto) ;

    /**
     *
     * @param appformReqDto
     * @return
     */
    public int deleteMcpRequestDlvry(AppformReqDto appformReqDto) ;

    /**
     *
     * @param appformReqDto
     * @return
     */
    public int deleteMcpRequestSaleinfo(AppformReqDto appformReqDto) ;

    /**
     *
     * @param appformReqDto
     * @return
     */
    public int deleteMcpRequestCommend(AppformReqDto appformReqDto) ;

    /**
     * <pre>
     * 설명     : DB선택보험 목록 가져오기
     * @param
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    public List<AppformReqDto> getInsrCode();

    /**
     * <pre>
     * 설명     : 휴대폰 안심 서비스 상품  목록 가져오기
     * @param
     * @return
     * @return: List<IntmInsrRelDTO>
     * </pre>
     */
    public List<IntmInsrRelDTO> getInsrProdList(IntmInsrRelDTO intmInsrRelDTO) ;

    /**
     * <pre>
     * 설명     : MCP_REQUEST_SELF_DLVRY  PK 값 조회
     * @return
     * </pre>
     */
    public Long getRequestSelfDlvrKey() ;

    /**
     * <pre>
     * 설명     : MCP_REQUEST_SELF_DLVRY 등록
     * @return
     * </pre>
     */
    public boolean insertMcpRequestSelfDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry) ;


    /**
     * <pre>
     * 설명     : MCP_REQUEST_SELF_DLVRY 등록
     * @return
     * </pre>
     */
    public boolean insertRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) ;

    /**
     * <pre>
     * 설명     : 셀프개통 배송 (MCP_REQUEST_SELF_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    public List<McpRequestSelfDlvryDto> getMcpRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry);

    /**
     * <pre>
     * 설명     : 셀프개통 배송 (MCP_REQUEST_SELF_DLVRY_HIST) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    public McpRequestSelfDlvryDto getMcpSelfDlvryDataHist(Long selfDlvryIdx);

    /**
     * <pre>
     * 설명     : 셀프개통 배송 (MCP_REQUEST_SELF_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    public McpRequestSelfDlvryDto getMcpSelfDlvryData(Long selfDlvryIdx);

    /**
     *
     * @param reqSelfDlvry
     * @return
     */
    public boolean insertMcpRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) ;

    /**
     *
     * @param selfDlvryIdx
     * @return
     */
    public int deleteMcpRequestSelfDlvry(Long selfDlvryIdx);

    /**
     * <pre>
     * 설명     : MCP_REQUEST_COMMEND 등록
     * @return
     * </pre>
     */
    public boolean insertMcpRequestCommend(AppformReqDto appformReqDto) ;

    /**
     * <pre>
     * 설명     : 고객CI정보에 대한 개통 정보 확인 위한 정보 추출
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public AppformReqDto getLimitForm(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 개통신청시(3단계)에서 최근 10분 내 샐프개통 신청(요청) 이력이 있을 경우 진행을 제한
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public int checkLimitFormCount(AppformReqDto appformReqDto) ;

    /**
     * <pre>
     * 설명     : 셀프개통 바로 배송 (MCP_REQUEST_NOW_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    public McpRequestSelfDlvryDto getMcpNowDlvryData(Long selfDlvryIdx);

    /**
     *
     * @param reqSelfDlvry
     * @return
     */
    public boolean insertMcpRequestNowDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry) ;

    /**
     * <pre>
     * 설명     : 온라인 신청서 등록후 바로 배송 키값 업데이트
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    public boolean updateMcpRequestNowDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry) ;

    /**
     *
     * @param selfDlvryIdx
     * @return
     */
    public int deleteMcpRequestNowDlvry(Long selfDlvryIdx);

    /**
     *
     * @param reqSelfDlvry
     * @return
     */
    public boolean insertMcpRequestNowDlvry(McpRequestSelfDlvryDto reqSelfDlvry) ;

    /**
     *
     * @param selfDlvryIdx
     * @return
     */
    public McpRequestSelfDlvryDto getMcpNowDlvryDataHist(Long selfDlvryIdx);

    /**
     *
     * @param mcpRequestSelfDlvryDto
     * @return
     */
    public boolean updateNowDlvry(McpRequestSelfDlvryDto mcpRequestSelfDlvryDto);

    /**
     *
     * @param mcpRequestSelfDlvryDto
     * @return
     */
    public boolean updatePayCdNowDlvry(McpRequestSelfDlvryDto mcpRequestSelfDlvryDto);

    /**
     *
     * @param selfDlvryIdx
     * @return
     */
    public int updateSelfViewYn(Long selfDlvryIdx);

    /**
     *
     * @param selfDlvryIdx
     * @return
     */
    public int updateNowViewYn(Long selfDlvryIdx);


    /**
     * <pre>
     * 설명     : 바로배송 요청 신청서 정보 패치
     * @param requestKey
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    public List<AppformReqDto> getFormDlveyList(AppformReqDto appformReqDto) ;


    /**
     * <pre>
     * 설명     : 바로배송 요청 신청서 USIM 번호 , PSTATE = '13'
     * @param requestKey
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    public boolean updateFormDlveyUsim(AppformReqDto appformReqDto);

    /**
     * 설명 : 요금제 설계 임시저장 시퀀스
     * @param appFormDesignDto
     * @return
     */
    public long getTempRequestKey();

    /**
     * 설명 : 요금제 설계 임시저장
     * @param appFormDesignDto
     * @return
     */
    public int insertAppFormTempSave(AppformReqDto appformReqDto);

    /**
     * 설명 : 요금제 설계 판매정보 임시저장
     * @param appFormDesignDto
     * @return
     */
    public int insertSaleinfoTempSave(AppformReqDto appformReqDto);

    /**
     * 설명 : 요금제 설계 자급제 임시저장
     * @param appFormDesignDto
     * @return
     */
    public int insertAppFormApdTempSave(AppformReqDto appformReqDto);

    /**
     * 설명 : 요금제 설계 판매정보 자급제 임시저장
     * @param appFormDesignDto
     * @return
     */
    public int insertSaleinfoApdTempSave(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신서 정보 테이블(NMCP_REQUEST) 조회
     * @param requestKey
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public AppformReqDto getAppForm(AppformReqDto appformReqDto);




    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_TEMP) 조회
     * @param requestKey
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public AppformReqDto getAppFormTemp(long requestKey);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_TEMP) UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateRequestTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_CSTMR_TEMP) UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateRequestCstmrTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_AGENT_TEMP) UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateRequestAgentTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_DLVRY_TEMP) UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateRequestDlvryTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_MOVE_TEMP) UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateRequestMoveTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_ADDITION_TEMP) INSERT
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestAdditionTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_ADDITION_TEMP) DELETE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean deleteMcpRequestAdditionTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_ADDITION_TEMP) DELETE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public List<String> getAdditionTempList(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_REQ_TEMP) 업데이트
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateRequestReqTemp(AppformReqDto appformReqDto);



    /**
     * <pre>
     * 설명     : 유심상품 정보 조회
     * @param UsimBasDto
     * @return
     * @return: UsimBasDto
     * </pre>
     */
    public UsimBasDto getUsimBasInfo(UsimBasDto usimBasObj);

    /**
     * <pre>
     * 설명     : 사은품 신청정보 (NMCP_GIFT_REQ_TXN) INSERT
     * @param GiftPromotionDtl
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertGiftReqTxn(GiftPromotionDtl giftPromotionDtl);

    /**
     * <pre>
     * 설명     : 사은품 신청정보 (NMCP_GIFT_REQ_TXN) 중복 방지을 위해 count
     * @param GiftPromotionDtl
     * @return
     * @return: boolean
     * </pre>
     */
    public int checkGiftReqCount(GiftPromotionDtl giftPromotionDtl) ;


    public boolean insertNmcpRequestApd(McpRequestDto appformReq);

    public boolean insertNmcpRequestApdDlvry(AppformReqDto appformReq);

    public boolean insertNmcpRequestApdSaleinfo(AppformReqDto appformReq);

    public boolean insertNmcpRequestApdState(AppformReqDto appformReq);

    public boolean updateNmcpRequestApd(AppformReqDto appformReq);

    public boolean updateNmcpRequestApdState(AppformReqDto appformReq);

    public boolean updateMcpRequestState(AppformReqDto appformReq);

    public AppformReqDto getNmcpRequestApdSaleinfo(Long requestKey);

    public int insertNmcpUsimBuyTxn(AppformReqDto appformReq);

    public boolean updateMcpRequestCallBack(AppformReqDto appformReq) ;



    /**
     * 설명     : 유심셀프변경 insert
     */
    public int insertMcpSelfUsimChg(OsstUc0ReqDto osstUc0ReqDto);

    /**
     * 설명     : 휴대폰 EID 등록여부
     */
    public int checkUploadPhoneInfoCount(long uploadPhoneSrlNo) ;

    /**
     * <pre>
     * 설명     : eSIM UP LOAD 정보 확인
     * @param getUploadPhoneInfo
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public McpUploadPhoneInfoDto getUploadPhoneInfo(long uploadPhoneSrlNo);


    /**
     * <pre>
     * 설명     : 가입신청 테이블(MCP_REQUEST_KT_INTER)
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestKtInter(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명 : ACEN 연동 대상 INSERT
     * @param acenDto
     * @return: int
     * </pre>
     */
    int insertAcenReqTrg(AcenDto acenDto);


    /**
     * <pre>
     * 설명     : 가입신청_결제정보 테이블(MCP_REQUEST_PAY_INFO)
     * @param mcpRequestReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean insertMcpRequestPayInfo(AppformReqDto appformReq);


    /**
     * <pre>
     * 설명     : 가입신청_결제정보 테이블(MCP_REQUEST_PAY_INFO)
     * @param mcpRequestPayInfo
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateMcpRequestPayInfo(McpRequestPayInfoDto mcpRequestPayInfo) ;

    /** 특정 기간 이내의 010 신규 셀프개통 건 수 */
    int getNacSelfCount(Map<String, String> param);

    /** 특정 기간 이내의 동일 번호이동전화번호 신청서 resNo 조회 */
    List<String> getResNoByMoveMobileNum(Map<String, Object> paramMap);

    /** 특정 기간 이내의 사전체크 시도 이력 조회 */
    int getPreCheckTryCnt(Map<String, Object> paramMap);

    /** 유심번호로 신청서 중복 체크 */
    int chkDupByReqUsimSn(Map<String, String> param);

    /** 번호이동전화번호로 신청서 중복 체크 */
    int chkDupByMovePhoneNum(Map<String, String> param);

    /** 신청서 상세 정보 조회 */
    int chkMcpReqDtl(long requestKey);

    /** 신청서 상세 정보 조회 */
    AppformReqDto getMcpReqDtl(long requestKey);

    /** 신청서 상세 정보 현행화 */
    int updateMcpReqDtl(AppformReqDto appformReq);

    /** 신청서 상세 정보 등록 */
    int insertMcpReqDtl(AppformReqDto appformReq);

    /** 유심등록 URL 제휴유심미보유 신청서정보 조회 */
    AppformReqDto getJehuUsimlessByResNo(String resNo);

    String selectUsimChgResult(String mvnoOrdNo);

    int updateMcpSelfUsimChgUC0(OsstUc0ReqDto osstUc0ReqDto);

    int insertKtCounsel(AppformReqDto appformReqDto);
}
