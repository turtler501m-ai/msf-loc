package com.ktmmobile.msf.domains.form.form.newchange.service
;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.domains.form.common.dto.McpRequestAdditionDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestCstmrDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestDlvryDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestMoveDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestSaleinfoDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestSelfDlvryDto;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.legacy.etc.dto.GiftPromotionBas;
import com.ktmmobile.msf.domains.form.common.legacy.point.dto.CustPointDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoListXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlSt1VO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlUc0VO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlVO;
import com.ktmmobile.msf.domains.form.common.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.domains.form.form.common.dto.UsimBasDto;
import com.ktmmobile.msf.domains.form.common.dto.AppformReqDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.FormDtlDTO;
import com.ktmmobile.msf.domains.form.common.dto.IntmInsrRelDTO;
import com.ktmmobile.msf.domains.form.common.dto.JuoSubInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.McpUploadPhoneInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.OsstFathReqDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.OsstReqDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.OsstUc0ReqDto;


/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : AppformSvc.java
 * 날짜     : 2016. 1. 15. 오전 11:42:28
 * 작성자   : papier
 * 설명     : 서식지 처리 service
 * </pre>
 */
public interface AppformSvc {

    /**
     * <pre>
     * 설명     : 약관정보 상세 패치
     * @return
     * @return: Map<String, FormDtlDTO>
     * </pre>
     */
    public FormDtlDTO getFormDesc(FormDtlDTO formDtlDTO);

    /**
     * <pre>
     * 설명     : 로그인 아이디로 임시저장 데이터 찾기
     * @return
     * @return: Boolean
     * </pre>
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public AppformReqDto getAppFormTempById(String userId);

    /**
     * <pre>
     * 설명     : 서식지 저장 처리 프로세스
     * @return
     * @return: Boolean
     * </pre>
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public AppformReqDto saveAppform(AppformReqDto appformReqDto, CustPointDto custPoint, List<GiftPromotionBas> giftPromotionBasList);


    /**
     * <pre>
     * 설명     : 개통 간소화 서식지 저장 처리 프로세스
     * @return
     * @return: Boolean
     * </pre>
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public AppformReqDto saveSimpleAppform(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 개통 간소화 서식지 업데이트 처리 프로세스
     * @return
     * @return: Boolean
     * </pre>
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String saveSimpleAppformUpdate(AppformReqDto appformReqDto, List<GiftPromotionBas> giftPromotionBasList);


    /**
     * <pre>
     * 설명     : 가입신청 테이블(MCP_REQUEST) 조회
     * @param requestKey
     * @return
     * @return: McpRequestDto
     * </pre>
     */
    public McpRequestDto getMcpRequest(long requestKey);

    /**
     * <pre>
     * 설명     : 가입신청_고객정보 테이블(MCP_REQUEST_CSTMR)조회
     * @param requestKey
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
     * 설명     : 가입신청_판매정보 테이블(MCP_REQUEST_SALEINFO) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSaleinfoDto
     * </pre>
     */
    public McpRequestSaleinfoDto getMcpRequestSaleinfo(long requestKey);

    /**
     * <pre>
     * 설명     : 기변시 msp_juo_sub_info 개통 데이터 확인
     * @return
     * @return: String
     * </pre>
     */
    public JuoSubInfoDto selRMemberAjax(JuoSubInfoDto juoSubInfoDto);


    /**
     * <pre>
     * 설명     : 부가서비스 목록 조회
     * @param mcpRequestAdditionDto
     * @return
     * @return: List<McpRequestAdditionDto>
     * </pre>
     */
    public List<McpRequestAdditionDto> getMcpAdditionList(McpRequestAdditionDto mcpRequestAdditionDto);


    /**
     * <pre>
     * 설명     : 약관정보 패치
     * @return
     * @return: Map<String, FormDtlDTO>
     * </pre>
     */
    //    public List<Map<String, FormDtlDTO>> setClauseMap();


    /**
     * <pre>
     * 설명     : 약관정보 상세 패치
     * @return
     * @return: Map<String, FormDtlDTO>
     * </pre>
     */
    //    public FormDtlDTO getFormDesc(FormDtlDTO formDtlDTO);

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
     * <pre>
     * 설명     : 서식지 기본키 요청
     * @return
     * @return: long
     * </pre>
     */
    public long generateRequestKey();

    /**
     * <pre>
     * 설명     : 제주항공 요금제 여부
     * @param rateCd
     * @return
     * @return: boolean
     * </pre>
     */
    public int checkJejuCodeCount(String rateCd);


    /**
     * <pre>
     * 설명     : 제휴 서비스를 위한 동의 필수 인 요금제 정보 확인
     * @param rateCd
     * @return
     * @return: boolean
     * </pre>
     */
    public int checkClauseJehuRatecd(String rateCd);


    /**
     * <pre>
     * 설명     : 단말기생상 가죠오기
     * @param appformReqDto
     * @return
     * @return: String
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
    public String getAtribVal(HashMap<String, Object> hm);

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


    //    public boolean inicisMobilePay(AppformReqDto appformReqDto ,InipayDto inipayDto);

    //    public boolean inicisMobileIsoPay(InipayDto inipayDto) ;

    public int getMcpRequestCount(AppformReqDto appformReqDto);


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
    public Map<String, String> getAgentInfoOjb(String cntpntShopId);

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
     * 설명     : UPDATE MCP_REQUEST TABLE
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateMcpRequest(McpRequestDto mcpRequestDto);


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
    public boolean updateMcpRequestOsstOrdNo(McpRequestOsstDto mcpRequestOsstDto);

    public boolean updateMcpRequestMove(McpRequestMoveDto mcpRequestMoveDto);

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
    public List<IntmInsrRelDTO> getInsrProdList(IntmInsrRelDTO intmInsrRelDTO);


    /**
     * <pre>
     * 설명     : 셀프개통 배송 요청 정보 등록
     * @param
     * @return
     * @return: Long MCP_REQUEST_SELF_DLVRY_HIST  INSERT 후 키값 리턴..
     * </pre>
     */
    public Long saveSelfDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry);


    /**
     * <pre>
     * 설명     : MCP_REQUEST_SELF_DLVRY 등록
     * @return
     * </pre>
     */
    public Long insertRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry);


    /**
     * <pre>
     * 설명     : 셀프개통 배송 (MCP_REQUEST_SELF_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    public List<McpRequestSelfDlvryDto> getMcpRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry);

    public McpRequestSelfDlvryDto getMcpSelfDlvryDataHist(Long selfDlvryIdx);


    public Long saveSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry);

    public McpRequestSelfDlvryDto getMcpSelfDlvryData(Long selfDlvryIdx); // 택배

    public int deleteMcpRequestSelfDlvry(Long selfDlvryIdx);

    public McpRequestSelfDlvryDto getMcpNowDlvryData(Long selfDlvryIdx); // 바로배송

    public Long saveNowDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry);

    public int deleteMcpRequestNowDlvry(Long selfDlvryIdx);

    public Long saveNowDlvry(McpRequestSelfDlvryDto reqSelfDlvry);

    public boolean updateNowDlvry(McpRequestSelfDlvryDto mcpRequestSelfDlvryDto);

    public Map<String, Object> nowDlvryComplete(Long selfDlvryIdx);

    public boolean updatePayCdNowDlvry(McpRequestSelfDlvryDto reqSelfDlvry);

    McpRequestSelfDlvryDto getMcpNowDlvryDataHist(Long selfDlvryIdx);

    public int updateSelfViewYn(Long selfDlvryIdx);

    public int updateNowViewYn(Long selfDlvryIdx);


    /**
     * <pre>
     * 설명     : 개통신청시(3단계)에서 최근 10분 내 샐프개통 신청(요청) 이력이 있을 경우 진행을 제한
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public int checkLimitFormCount(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : Mplatform OSST 연동 처리
     * @return
     * @return: MSimpleOsstXmlVO
     * </pre>
     */
    public MSimpleOsstXmlVO sendOsstService(String resNo, String eventCd) throws SelfServiceException, SocketTimeoutException, McpMplatFormException;

    /**
     * <pre>
     * 설명     : Mplatform OSST 연동 처리
     * @param osstParam
     * @param eventCd
     * @return: MSimpleOsstXmlVO
     * </pre>
     */
    public MSimpleOsstXmlVO sendOsstService(Map<String, String> osstParam, String eventCd)
        throws SelfServiceException, SocketTimeoutException, McpMplatFormException;

    /**
     * <pre>
     * 설명     : Mplatform OSST 연동 처리
     *     납부 BAN 정보
     * @return
     * @return: MSimpleOsstXmlVO
     * </pre>
     */
    public MSimpleOsstXmlVO sendOsstAddBillService(String resNo, String eventCd, String billAcntNo)
        throws SelfServiceException, SocketTimeoutException, McpMplatFormException;

    /**
     * <pre>
     * 설명     : Mplatform OSST 연동 처리
     * @return
     * @return: MSimpleOsstXmlVO
     * </pre>
     */
    public MSimpleOsstXmlVO sendOsstService(String resNo, String eventCd, String gubun) throws SelfServiceException, SocketTimeoutException;


    /**
     * <pre>
     * 설명     : Mplatform OSST 연동 처리
     * @return
     * @return: MSimpleOsstXmlVO
     * </pre>
     */
    public MSimpleOsstXmlVO sendOsstService(OsstReqDto osstReqDto, String eventCd) throws SelfServiceException, SocketTimeoutException;

    /**
     * <pre>
     * 설명     : Mplatform OSST 번호조회 요청 처리
     * @return
     * @return: MPhoneNoListXmlVO
     * </pre>
     */
    public MPhoneNoListXmlVO getPhoneNoList(String resNo, String eventCd) throws SelfServiceException, SocketTimeoutException;

    /**
     * <pre>
     * 설명     : Mplatform OSST 번호조회 요청 처리
     * @param osstParam
     * @param eventCd
     * @return: MPhoneNoListXmlVO
     * </pre>
     */
    public MPhoneNoListXmlVO getPhoneNoList(Map<String, String> osstParam, String eventCd) throws SelfServiceException, SocketTimeoutException;


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
     * 설명     : PSTATE 값 업데이트
     * @param requestKey : 서식지 키값
     * @return
     * </pre>
     */
    public boolean updateAppForPstate(long requestKey);

    /**
     * <pre>
     * 설명     : 바로배송 요청 신청서 정보 패치
     * @param requestKey
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    public List<AppformReqDto> getFormDlveyList(AppformReqDto appformReqDto);

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
     * * 설명     : 요금제 설계 시퀀스
     * @param appFormDesignDto
     * @return
     */
    public long getTempRequestKey();

    /**
     * * 설명     : 요금제 설계 임시저장
     * @param appFormDesignDto
     * @return
     */
    public int insertAppFormTempSave(AppformReqDto appformReqDto);


    /**
     * * 설명     : 요금제 설계 판매정보 임시저장
     * @param appFormDesignDto
     * @return
     */
    public int insertSaleinfoTempSave(AppformReqDto appformReqDto);

    /**
     * * 설명     : 요금제 설계 자급제 임시저장
     * @param appFormDesignDto
     * @return
     */
    public int insertAppFormApdTempSave(AppformReqDto appformReqDto);

    /**
     * * 설명     : 요금제 설계 판매정보 자급제 임시저장
     * @param appFormDesignDto
     * @return
     */
    public int insertSaleinfoApdTempSave(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 테이블(MCP_REQUEST) 조회
     * @param contractNum
     * @return
     * @return: McpRequestDto
     * </pre>
     */
    public McpRequestDto getMcpRequestByContractNum(String contractNum);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 테이블(MCP_REQUEST) 조회
     * @param requestKey
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public AppformReqDto getAppFormTemp(long requestKey);


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
     * 설명     : 가입신청 임시 정보 n개 테이블  UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateRequestTempStep1(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 n개 테이블  UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateRequestTempStep3(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 n개 테이블  UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateRequestTempStep4(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 n개 테이블  UPDATE
     * @param AppformReqDto
     * @return
     * @return: boolean
     * </pre>
     */
    public boolean updateRequestTempStep5(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 가입신청 임시 정보 부가서비스 목록 조회
     * @param
     * @return
     * @return: List<String>
     * </pre>
     */
    public List<String> getAdditionTempList(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 유심상품 정보 조회
     * @param UsimBasDto
     * @return
     * @return: UsimBasDto
     * </pre>
     */
    public UsimBasDto getUsimBasInfo(UsimBasDto usimBasObj);

    public AppformReqDto getNmcpRequestApdSaleinfo(Long selfDlvryIdx);

    /**
     * <pre>
     * 설명     : 자급제 신청서 결제 성공 후 성공 처리 업데이트
     *           point 사용처리
     * @param Long requestKey 신청서 키값..
     * @return  SELF_SUF_PAY
     * @return: boolean
     * </pre>
     */
    public boolean useSelfFormSufPay(Long requestKey);


    /**
     * <pre>
     * 설명     : 가입신청 테이블 여러 테이블  (MCP_REQUEST,MCP_REQUEST_CSTMR,MCP_REQUEST_REQ) 조회
     * @param requestKey
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public AppformReqDto getCopyMcpRequest(AppformReqDto appformReq);


    public int insertNmcpUsimBuyTxn(AppformReqDto appformReq);


    /**
     * <pre>
     * 설명     : 유심번호로 조직 코드 조회
     * @param requestKey
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public String getUsimOrgnId(String usimNo);


    /**
     * <pre>
     * 설명     : Mplatform OSST 연동 처리 (UC0)
     * @return
     * @return: MSimpleOsstXmlUc0VO
     * </pre>
     */
    public MSimpleOsstXmlUc0VO sendOsstUc0Service(OsstUc0ReqDto osstUc0ReqDto, String eventCd)
        throws SelfServiceException, SocketTimeoutException, McpMplatFormException;


    /**
     * <pre>
     * 설명     : 유심변경 결과 확인 (UC2)
     * @return
     * @return: String
     * </pre>
     */
    public String usimChgChk(OsstUc0ReqDto osstUc0ReqDto);

    /**
     * 설명     : 휴대폰 EID 등록여부
     */
    public int checkUploadPhoneInfoCount(String uploadPhoneSrlNo);

    /**
     * <pre>
     * 설명     : eSIM UP LOAD 정보 확인
     * @param getUploadPhoneInfo
     * @return
     * @return: AppformReqDto
     * </pre>
     */
    public McpUploadPhoneInfoDto getUploadPhoneInfo(long uploadPhoneSrlNo);


    int selectStolenIp(String customer);

    // ====================== START: STEP 검증 관련 메소드 ======================

    /** [step별 검증] 상담사 개통 최종 step 확인 */
    boolean crtSaveAppFormStep(AppformReqDto appformReqDto);

    /** [step별 검증] 상담사 개통 최종 정보 확인 */
    Map<String, String> crtSaveAppFormInfo(AppformReqDto appformReqDto);

    /** [step별 검증] 셀프개통 사전체크 최종 정보 확인 */
    Map<String, String> crtSaveSimpleAppFormInfo(AppformReqDto appformReqDto);

    /** [step별 검증] 셀프개통 최종 step 확인 */
    boolean crtUpdateSimpleAppFormStep(AppformReqDto appformReqDto);

    /** [step별 검증] 셀프개통 최종 정보 확인 */
    Map<String, String> crtUpdateSimpleAppFormInfo(AppformReqDto appformReqDto);

    // ====================== END: STEP 검증 관련 메소드 ======================

    /**
     * <pre>
     * 설명     :(셀프개통) 직영 평생할인 프로모션 id 찾아오기
     * @param appformReqDto
     * </pre>
     */
    String getChrgPrmtId(AppformReqDto appformReqDto);


    /**
     * <pre>
     * 설명     : 평생할인 프로모션 기적용 테이블 insert
     * @param appformReqDto
     * </pre>
     */
    int insertDisPrmtApd(AppformReqDto appformReqDto, String evntCd);

    /**
     * <pre>
     * 설명     :(오프라인) 평생할인 프로모션 id찾아오기
     * @param appformReqDto
     * </pre>
     */
    String getDisPrmtId(AppformReqDto appformReqDto);

    /**
     * <pre>
     * 설명     : Mplatform OSST 연동 처리 (ST1)
     * @param: resNo
     * @param: eventCd
     * @return: MSimpleOsstXmlSt1VO
     * </pre>
     */
    MSimpleOsstXmlSt1VO sendOsstSt1Service(String resNo, String eventCd) throws SelfServiceException, SocketTimeoutException, McpMplatFormException;

    MSimpleOsstXmlSt1VO sendOsstSt1Service(Map<String, String> osstParam, String eventCd)
        throws SelfServiceException, SocketTimeoutException, McpMplatFormException;

    /**
     * <pre>
     * 설명     : 사전체크 완료상태 조회 (사전체크 작업 완료 후 MP측 DB작업 반영 상태 조회)
     * @param: resNo
     * @return: Map<String, String>
     * </pre>
     */
    Map<String, String> chkRealPc2Result(String resNo, String contractNum);


    /**
     * <pre>
     * 설명     : 셀프개통 가능 플랫폼 체크
     * @param : operType
     * @return: Map<String, String>
     * </pre>
     */
    Map<String, String> isSimpleApplyPlatform(String operType);


    /**
     * <pre>
     * 설명     : 즉시결제  결과 UPDATE CJFL
     * @param Long requestKey 신청서 키값..
     * @return  SELF_SUF_PAY
     * @return: boolean
     * </pre>
     */
    public boolean updateDirectPhone(AppformReqDto appformReqDto);

    /** 특정 기간 이내의 010 신규 셀프개통 건 수 */
    int getNacSelfCount();

    /** 번호이동 사전체크 일 건수 제한 */
    Map<String, Object> mnpPreCheckLimit(String moveMobileNo);

    /** 중복 신청 체크 */
    Map<String, String> checkDupReq(AppformReqDto appformReqDto);

    public List<MspSalePlcyMstDto> getSalePlcyInfo(AppformReqDto appformReqDto);

    /** Acen 대상 Check */
    public boolean chkAcenReqCondition(McpRequestDto mcpRequestDto, Map<String, String> etcParam);

    /** Acen 대상으로 INSERT */
    public void insertAcenReqTrg(McpRequestDto mcpRequestDto);

    void containsGoldNumbers(List<String> reqWantNumbers);


    /**
     * <pre>
     * 설명     : 셀프개통 가능 여부 체크
     * @param : operType
     * @return: Map<String, String>
     * </pre>
     */
    public Map<String, Object> isSimpleApplyObj();

    public AppformReqDto getJehuUsimlessByResNo(String resNo);

    HashMap<String, Object> processOsstFt0Service(OsstFathReqDto osstFathReqDto);

    HashMap<String, Object> processOsstFs8Service(OsstFathReqDto osstFathReqDto);

    HashMap<String, Object> processOsstFs9Service(OsstFathReqDto osstFathReqDto, String resltCd);

    HashMap<String, Object> processOsstFt1Service(OsstFathReqDto osstFathReqDto);

    boolean isEnabledFT1();
}