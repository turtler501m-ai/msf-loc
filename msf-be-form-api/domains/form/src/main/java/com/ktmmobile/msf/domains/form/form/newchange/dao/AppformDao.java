package com.ktmmobile.msf.domains.form.form.newchange.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.domains.form.form.common.dto.UsimBasDto;
import com.ktmmobile.msf.domains.form.common.dto.AppformReqDto;
import com.ktmmobile.msf.domains.form.common.dto.IntmInsrRelDTO;
import com.ktmmobile.msf.domains.form.common.dto.JuoSubInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.McpRequestPayInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.McpUploadPhoneInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.OsstUc0ReqDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestAdditionDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestCstmrDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestDlvryDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestMoveDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestSaleinfoDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestSelfDlvryDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpAppFormMstDto;

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

    AppformReqDto getAppFormTemp(String userId);

    /**
     *
     * @param requestKey
     * @return
     */
    Map<String, String> getAppFormData(long requestKey);

    /**
     *
     * @param requestKey
     * @return
     */
    Map<String, String> getAppFormUserData(long requestKey);

    /**
     * <pre>
     * 설명     : 서식지 전체 좌표 조회
     * @return
     * </pre>
     */
    List<HashMap<String, String>> getAppFormPointList();

    /**
     * <pre>
     * 설명     : 서식지 그룹별 좌표 조회
     * @param groupCode : 서식지 그룹코드
     * @return
     * </pre>
     */
    List<HashMap<String, String>> getAppFormPointList(String groupCode);

    /**
     * <pre>
     * 설명     : 페이지 별 좌표 조회
     * @param pageCode : 페이지 코드
     * @return
     * </pre>
     */
    List<HashMap<String, String>> getAppFormPageList(String pageCode);

    /**
     * <pre>
     * 설명     : 스캔 서버 연동 UPDATE  처리
     * @param requestKey : 서식지 키값
     * @return
     * </pre>
     */
    boolean updateAppFormXmlYn(long requestKey);

    /**
     * <pre>
     * 설명     : 결제 가승인 승인 처리
     * @param requestKey : 서식지 키값
     * @return
     * </pre>
     */
    boolean updateAppForPstate(long requestKey);

    /**
     * <pre>
     * 설명     : updateMcpRequestSaleinfo
     * @param mcpRequestSaleinfoDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean updateMcpRequestSaleinfo(McpRequestSaleinfoDto mcpRequestSaleinfoDto);

    /**
     * <pre>
     * 설명     : 가입신청 테이블(MCP_REQUEST)
     * @param mcpRequestDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequest(McpRequestDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_고객정보 테이블(MCP_REQUEST_CSTMR)
     * @param mcpRequestCstmrDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestCstmr(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_대리인 테이블(MCP_REQUEST_AGENT)
     * @param
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestAgent(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_번호이동정보 테이블(MCP_REQUEST_MOVE)
     * @param
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestMove(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_선불충전 테이블(MCP_REQUEST_PAYMENT)
     * @param
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestPayment(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_선불충전 테이블(MCP_REQUEST_PAYMENT)
     * @param
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestSaleinfo(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_배송정보 테이블(MCP_REQUSET_DLVRY)
     * @param
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestDlvry(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_청구정보 테이블(MCP_REQUEST_REQ)
     * @param mcpRequestReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestReq(AppformReqDto appformReq);

    /**
     *
     * @param mcpRequestChangeDto
     * @return
     */
    boolean insertMcpRequestChange(AppformReqDto appformReq);


    /**
     * <pre>
     * 설명     : 가입신청_기변사유 테이블(MCP_REQUEST_DVC_CHG)
     * @param mcpRequestReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestDvcChg(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청_부가서비스 테이블(MCP_REQUEST_ADDITION)
     * @param mcpRequestAdditionDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestAddition(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 프로모션 관련 부가 서비스 등록 처리(MCP_REQUEST_ADDITION)
     * @param mcpRequestAdditionDto
     * @return
     * @return: boolean
     * </pre>
     */
    int insertMcpRequestAdditionPromotion(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 진행상태 테이블(MCP_REQUEST_STATE)
     * @param mcpRequestAdditionDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestState(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 서식지 일련번호 생성
     * @return
     * </pre>
     */
    Long generateRequestKey();

    /**
     * <pre>
     * 설명     : 예약번호 추출
     * @return
     * @return: String
     * </pre>
     */
    String generateResNo();

    /**
     * <pre>
     * 설명     : 개통자 확인
     * @return
     * @return: String
     * </pre>
     */
    JuoSubInfoDto selRMemberAjax(JuoSubInfoDto juoSubInfoDto);

    /**
     * <pre>
     * 설명     : 가입신청 테이블(MCP_REQUEST) 조회
     * @param requestKey
     * @return
     * @return: McpRequestDto
     * </pre>
     */
    McpRequestDto getMcpRequest(long requestKey);

    McpRequestDto getMcpRequest(McpRequestDto requestDto);


    /**
     * <pre>
     * 설명     : 가입신청 테이블 여러 테이블  (MCP_REQUEST,MCP_REQUEST_CSTMR,MCP_REQUEST_REQ) 조회
     * @param requestKey
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    AppformReqDto getCopyMcpRequest(AppformReqDto appformReq);

    /**
     * <pre>
     * 설명     : 가입신청_고객정보 테이블(MCP_REQUEST_CSTMR)조회
     * @param AppformReqDto
     * @return
     * @return: McpRequestCstmrDto
     * </pre>
     */
    McpRequestCstmrDto getMcpRequestCstmr(long requestKey);

    /**
     * <pre>
     * 설명     : 가입신청_배송정보 테이블(MCP_REQUSET_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestDlvryDto
     * </pre>
     */
    McpRequestDlvryDto getMcpRequestDlvry(long requestKey);

    /**
     * <pre>
     * 설명     : 가입신청_판매정보 테이블(MCP_REQUEST_SALEINFO)
     * @param requestKey
     * @return
     * @return: McpRequestSaleinfoDto
     * </pre>
     */
    McpRequestSaleinfoDto getMcpRequestSaleinfo(long requestKey);

    /**
     * <pre>
     * 설명     : 부가서비스 목록 조회
     * @param mcpRequestAdditionDto
     * @return
     * @return: List<McpRequestAdditionDto>
     * </pre>
     */
    List<McpRequestAdditionDto> getMcpAdditionList(
        McpRequestAdditionDto mcpRequestAdditionDto
    );

    /**
     * <pre>
     * 설명     : 약관 동의 테이블 저장
     * @param hm
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestClause(HashMap<String, Object> hm);

    /**
     * <pre>
     * 설명     :주문정보 사용자 검증
     * @param appformReqDto
     * @return
     * @return: int
     * </pre>
     */
    int isOwnerCount(AppformReqDto appformReqDto);

    /**
     *
     * @param appformReqDto
     * @return
     */
    int getMcpRequestCount(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 제주항공 요금제 여부
     * @param rateCd
     * @return
     * @return: int
     * </pre>
     */
    int checkJejuCodeCount(String rateCd);

    /**
     * <pre>
     * 설명     : 제휴 서비스를 위한 동의 필수 인 요금제 정보
     * @param rateCd
     * @return
     * @return: int
     * </pre>
     */
    int checkClauseJehuRatecd(String rateCd);

    /**
     * <pre>
     * 설명     : 서식지 관련 MSP 코드 조회
     * </pre>
     */
    McpRequestDto getMspPrdtCode(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 단말색상 가져오기
     * </pre>
     */
    String selPrdtcolCd(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     :단말기 속성 조회
     * @return
     * @return: String
     * </pre>
     */
    String getAtribVal(HashMap<String, Object> hm);

    /**
     * <pre>
     * 설명     : 정책관련 상품 조회
     * </pre>
     */
    AppformReqDto getMarketRequest(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 할부개월
     * @param AppformReqDto : 정책번호
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    List<AppformReqDto> selectModelMonthlyList(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 약정개월
     * @param AppformReqDto : 정책번호
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    List<AppformReqDto> selectMonthlyListMarket(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 생상정보
     * @param AppformReqDto : modelId
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    List<AppformReqDto> selectPrdtColorList(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 서식지 페이지 코드 조회
     * @param pageCode : 페이지코드
     * @return
     * </pre>
     */
    NmcpAppFormMstDto selectNmcpAppFormMst(String pageCode);


    /**
     * <pre>
     * 설명     : 대리점 코드 패치
     * @param cntpntShopId 접점코드
     * @return
     * @return: String
     * </pre>
     */
    String getAgentCode(String cntpntShopId);


    /**
     * <pre>
     * 설명     : 대리점 정보 패치
     * @param cntpntShopId 접점코드
     * @return
     * @return: String
     * </pre>
     */
    Map<String, String> getAgentInfoOjb(String cntpntShopId);

    /**
     *
     * @param mcpRequestCstmrDto
     * @return
     */
    boolean updateMcpRequestCstmr(McpRequestCstmrDto mcpRequestCstmrDto);

    /**
     *
     * @param mcpRequestDto
     * @return
     */
    boolean updateMcpRequest(McpRequestDto mcpRequestDto);

    /**
     *
     * @param mcpRequestMoveDto
     * @return
     */
    boolean updateMcpRequestMove(McpRequestMoveDto mcpRequestMoveDto);

    /**
     * <pre>
     * 설명     : RequestOsst 정보
     * @return
     * @return: McpRequestOsstDto
     * </pre>
     */
    McpRequestOsstDto getRequestOsst(McpRequestOsstDto mcpRequestOsstDto);


    /**
     * <pre>
     * 설명     : RequestOsst count
     * @return
     * @return: McpRequestOsstDto
     * </pre>
     */
    int requestOsstCount(McpRequestOsstDto mcpRequestOsstDto);


    /**
     * <pre>
     * 설명     : 간편가입 테이블(MCP_REQUEST_OSST)
     * @param mcpRequestDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestOsst(McpRequestOsstDto mcpRequestOsstDto);


    /**
     * <pre>
     * 설명     : NP1 ORDER NO UPDATE_MCP_REQUEST_OSST
     * @param mcpRequestOsstDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean updateMcpRequestOsstOrdNo(McpRequestOsstDto mcpRequestOsstDto);

    /**
     *
     * @param appformReqDto
     * @return
     */
    int deleteMcpRequestReq(AppformReqDto appformReqDto);

    /**
     *
     * @param appformReqDto
     * @return
     */
    int deleteMcpRequestAddition(AppformReqDto appformReqDto);

    /**
     *
     * @param appformReqDto
     * @return
     */
    int deleteMcpRequestAgent(AppformReqDto appformReqDto);

    /**
     *
     * @param appformReqDto
     * @return
     */
    int deleteRequestPayment(AppformReqDto appformReqDto);

    /**
     *
     * @param appformReqDto
     * @return
     */
    int deleteMcpRequestChange(AppformReqDto appformReqDto);

    /**
     *
     * @param appformReqDto
     * @return
     */
    int deleteMcpRequestDlvry(AppformReqDto appformReqDto);

    /**
     *
     * @param appformReqDto
     * @return
     */
    int deleteMcpRequestSaleinfo(AppformReqDto appformReqDto);

    /**
     *
     * @param appformReqDto
     * @return
     */
    int deleteMcpRequestCommend(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : DB선택보험 목록 가져오기
     * @param
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    List<AppformReqDto> getInsrCode();

    /**
     * <pre>
     * 설명     : 휴대폰 안심 서비스 상품  목록 가져오기
     * @param
     * @return
     * @return: List<IntmInsrRelDTO>
     * </pre>
     */
    List<IntmInsrRelDTO> getInsrProdList(IntmInsrRelDTO intmInsrRelDTO);

    /**
     * <pre>
     * 설명     : MCP_REQUEST_SELF_DLVRY  PK 값 조회
     * @return
     * </pre>
     */
    Long getRequestSelfDlvrKey();

    /**
     * <pre>
     * 설명     : MCP_REQUEST_SELF_DLVRY 등록
     * @return
     * </pre>
     */
    boolean insertMcpRequestSelfDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry);


    /**
     * <pre>
     * 설명     : MCP_REQUEST_SELF_DLVRY 등록
     * @return
     * </pre>
     */
    boolean insertRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry);

    /**
     * <pre>
     * 설명     : 셀프개통 배송 (MCP_REQUEST_SELF_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    List<McpRequestSelfDlvryDto> getMcpRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry);

    /**
     * <pre>
     * 설명     : 셀프개통 배송 (MCP_REQUEST_SELF_DLVRY_HIST) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    McpRequestSelfDlvryDto getMcpSelfDlvryDataHist(Long selfDlvryIdx);

    /**
     * <pre>
     * 설명     : 셀프개통 배송 (MCP_REQUEST_SELF_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    McpRequestSelfDlvryDto getMcpSelfDlvryData(Long selfDlvryIdx);

    /**
     *
     * @param reqSelfDlvry
     * @return
     */
    boolean insertMcpRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry);

    /**
     *
     * @param selfDlvryIdx
     * @return
     */
    int deleteMcpRequestSelfDlvry(Long selfDlvryIdx);

    /**
     * <pre>
     * 설명     : MCP_REQUEST_COMMEND 등록
     * @return
     * </pre>
     */
    boolean insertMcpRequestCommend(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 고객CI정보에 대한 개통 정보 확인 위한 정보 추출
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    AppformReqDto getLimitForm(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 개통신청시(3단계)에서 최근 10분 내 샐프개통 신청(요청) 이력이 있을 경우 진행을 제한
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    int checkLimitFormCount(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 셀프개통 바로 배송 (MCP_REQUEST_NOW_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    McpRequestSelfDlvryDto getMcpNowDlvryData(Long selfDlvryIdx);

    /**
     *
     * @param reqSelfDlvry
     * @return
     */
    boolean insertMcpRequestNowDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry);

    /**
     * <pre>
     * 설명     : 온라인 신청서 등록후 바로 배송 키값 업데이트
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    boolean updateMcpRequestNowDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry);

    /**
     *
     * @param selfDlvryIdx
     * @return
     */
    int deleteMcpRequestNowDlvry(Long selfDlvryIdx);

    /**
     *
     * @param reqSelfDlvry
     * @return
     */
    boolean insertMcpRequestNowDlvry(McpRequestSelfDlvryDto reqSelfDlvry);

    /**
     *
     * @param selfDlvryIdx
     * @return
     */
    McpRequestSelfDlvryDto getMcpNowDlvryDataHist(Long selfDlvryIdx);

    /**
     *
     * @param mcpRequestSelfDlvryDto
     * @return
     */
    boolean updateNowDlvry(McpRequestSelfDlvryDto mcpRequestSelfDlvryDto);

    /**
     *
     * @param mcpRequestSelfDlvryDto
     * @return
     */
    boolean updatePayCdNowDlvry(McpRequestSelfDlvryDto mcpRequestSelfDlvryDto);

    /**
     *
     * @param selfDlvryIdx
     * @return
     */
    int updateSelfViewYn(Long selfDlvryIdx);

    /**
     *
     * @param selfDlvryIdx
     * @return
     */
    int updateNowViewYn(Long selfDlvryIdx);


    /**
     * <pre>
     * 설명     : 바로배송 요청 신청서 정보 패치
     * @param requestKey
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    List<AppformReqDto> getFormDlveyList(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 바로배송 요청 신청서 USIM 번호 , PSTATE = '13'
     * @param requestKey
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    boolean updateFormDlveyUsim(AppformReqDto appformReqDto);

    /**
     * 설명 : 요금제 설계 임시저장 시퀀스
     * @param appFormDesignDto
     * @return
     */
    long getTempRequestKey();

    /**
     * 설명 : 요금제 설계 임시저장
     * @param appFormDesignDto
     * @return
     */
    int insertAppFormTempSave(AppformReqDto appformReqDto);

    /**
     * 설명 : 요금제 설계 판매정보 임시저장
     * @param appFormDesignDto
     * @return
     */
    int insertSaleinfoTempSave(AppformReqDto appformReqDto);

    /**
     * 설명 : 요금제 설계 자급제 임시저장
     * @param appFormDesignDto
     * @return
     */
    int insertAppFormApdTempSave(AppformReqDto appformReqDto);

    /**
     * 설명 : 요금제 설계 판매정보 자급제 임시저장
     * @param appFormDesignDto
     * @return
     */
    int insertSaleinfoApdTempSave(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신서 정보 테이블(NMCP_REQUEST) 조회
     * @param requestKey
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    AppformReqDto getAppForm(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_TEMP) 조회
     * @param requestKey
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    AppformReqDto getAppFormTemp(long requestKey);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_TEMP) UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean updateRequestTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_CSTMR_TEMP) UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean updateRequestCstmrTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_AGENT_TEMP) UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean updateRequestAgentTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_DLVRY_TEMP) UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean updateRequestDlvryTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_MOVE_TEMP) UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean updateRequestMoveTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_ADDITION_TEMP) INSERT
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestAdditionTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_ADDITION_TEMP) DELETE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean deleteMcpRequestAdditionTemp(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_ADDITION_TEMP) DELETE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    List<String> getAdditionTempList(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(NMCP_REQUEST_REQ_TEMP) 업데이트
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean updateRequestReqTemp(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 유심상품 정보 조회
     * @param UsimBasDto
     * @return
     * @return: UsimBasDto
     * </pre>
     */
    UsimBasDto getUsimBasInfo(UsimBasDto usimBasObj);

    //    /**
    //     * <pre>
    //     * 설명     : 사은품 신청정보 (NMCP_GIFT_REQ_TXN) INSERT
    //     * @param GiftPromotionDtl
    //     * @return
    //     * @return: boolean
    //     * </pre>
    //     */
    //    public boolean insertGiftReqTxn(GiftPromotionDtl giftPromotionDtl);
    //
    //    /**
    //     * <pre>
    //     * 설명     : 사은품 신청정보 (NMCP_GIFT_REQ_TXN) 중복 방지을 위해 count
    //     * @param GiftPromotionDtl
    //     * @return
    //     * @return: boolean
    //     * </pre>
    //     */
    //    public int checkGiftReqCount(GiftPromotionDtl giftPromotionDtl) ;


    boolean insertNmcpRequestApd(McpRequestDto appformReq);

    boolean insertNmcpRequestApdDlvry(AppformReqDto appformReq);

    boolean insertNmcpRequestApdSaleinfo(AppformReqDto appformReq);

    boolean insertNmcpRequestApdState(AppformReqDto appformReq);

    boolean updateNmcpRequestApd(AppformReqDto appformReq);

    boolean updateNmcpRequestApdState(AppformReqDto appformReq);

    boolean updateMcpRequestState(AppformReqDto appformReq);

    AppformReqDto getNmcpRequestApdSaleinfo(Long requestKey);

    int insertNmcpUsimBuyTxn(AppformReqDto appformReq);

    boolean updateMcpRequestCallBack(AppformReqDto appformReq);


    /**
     * 설명     : 유심셀프변경 insert
     */
    int insertMcpSelfUsimChg(OsstUc0ReqDto osstUc0ReqDto);

    /**
     * 설명     : 휴대폰 EID 등록여부
     */
    int checkUploadPhoneInfoCount(long uploadPhoneSrlNo);

    /**
     * <pre>
     * 설명     : eSIM UP LOAD 정보 확인
     * @param getUploadPhoneInfo
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    McpUploadPhoneInfoDto getUploadPhoneInfo(long uploadPhoneSrlNo);


    /**
     * <pre>
     * 설명     : 가입신청 테이블(MCP_REQUEST_KT_INTER)
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestKtInter(AppformReqDto appformReq);




    /**
     * <pre>
     * 설명     : 가입신청_결제정보 테이블(MCP_REQUEST_PAY_INFO)
     * @param mcpRequestReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    boolean insertMcpRequestPayInfo(AppformReqDto appformReq);


    /**
     * <pre>
     * 설명     : 가입신청_결제정보 테이블(MCP_REQUEST_PAY_INFO)
     * @param mcpRequestPayInfo
     * @return
     * @return: boolean
     * </pre>
     */
    boolean updateMcpRequestPayInfo(McpRequestPayInfoDto mcpRequestPayInfo);

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
