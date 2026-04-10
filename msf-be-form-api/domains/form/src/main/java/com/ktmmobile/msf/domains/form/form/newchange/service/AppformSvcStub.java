package com.ktmmobile.msf.domains.form.form.newchange.service;

// [ASIS] stub — AppformSvcImpl 전체 주석처리로 인한 기동용 stub 제공

import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestAdditionDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestCstmrDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestDlvryDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestMoveDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestSaleinfoDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.legacy.etc.dto.GiftPromotionBas;
import com.ktmmobile.msf.domains.form.common.legacy.point.dto.CustPointDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoListXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlSt1VO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlUc0VO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlVO;
import com.ktmmobile.msf.domains.form.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.domains.form.form.common.dto.UsimBasDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AppformReqDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.FormDtlDTO;
import com.ktmmobile.msf.domains.form.form.newchange.dto.IntmInsrRelDTO;
import com.ktmmobile.msf.domains.form.form.newchange.dto.JuoSubInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.McpUploadPhoneInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.OsstFathReqDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.OsstReqDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.OsstUc0ReqDto;

@Service("appformSvc")
public class AppformSvcStub implements AppformSvc {

    @Override public FormDtlDTO getFormDesc(FormDtlDTO formDtlDTO) { return null; }
    @Override public AppformReqDto getAppFormTempById(String userId) { return null; }
    @Override public AppformReqDto saveAppform(AppformReqDto appformReqDto, CustPointDto custPoint, List<GiftPromotionBas> giftPromotionBasList) { return null; }
    @Override public AppformReqDto saveSimpleAppform(AppformReqDto appformReqDto) { return null; }
    @Override public String saveSimpleAppformUpdate(AppformReqDto appformReqDto, List<GiftPromotionBas> giftPromotionBasList) { return null; }
    @Override public McpRequestDto getMcpRequest(long requestKey) { return null; }
    @Override public McpRequestCstmrDto getMcpRequestCstmr(long requestKey) { return null; }
    @Override public McpRequestDlvryDto getMcpRequestDlvry(long requestKey) { return null; }
    @Override public McpRequestSaleinfoDto getMcpRequestSaleinfo(long requestKey) { return null; }
    @Override public JuoSubInfoDto selRMemberAjax(JuoSubInfoDto juoSubInfoDto) { return null; }
    @Override public List<McpRequestAdditionDto> getMcpAdditionList(McpRequestAdditionDto mcpRequestAdditionDto) { return Collections.emptyList(); }
    @Override public int isOwnerCount(AppformReqDto appformReqDto) { return 0; }
    @Override public long generateRequestKey() { return 0L; }
    @Override public int checkJejuCodeCount(String rateCd) { return 0; }
    @Override public int checkClauseJehuRatecd(String rateCd) { return 0; }
    @Override public String selPrdtcolCd(AppformReqDto appformReqDto) { return null; }
    @Override public String getAtribVal(HashMap<String, Object> hm) { return null; }
    @Override public AppformReqDto getMarketRequest(AppformReqDto appformReqDto) { return null; }
    @Override public List<AppformReqDto> selectModelMonthlyList(AppformReqDto appformReqDto) { return Collections.emptyList(); }
    @Override public List<AppformReqDto> selectMonthlyListMarket(AppformReqDto appformReqDto) { return Collections.emptyList(); }
    @Override public List<AppformReqDto> selectPrdtColorList(AppformReqDto appformReqDto) { return Collections.emptyList(); }
    @Override public int getMcpRequestCount(AppformReqDto appformReqDto) { return 0; }
    @Override public String getAgentCode(String cntpntShopId) { return null; }
    @Override public Map<String, String> getAgentInfoOjb(String cntpntShopId) { return Collections.emptyMap(); }
    @Override public McpRequestOsstDto getRequestOsst(McpRequestOsstDto mcpRequestOsstDto) { return null; }
    @Override public int requestOsstCount(McpRequestOsstDto mcpRequestOsstDto) { return 0; }
    @Override public boolean updateMcpRequest(McpRequestDto mcpRequestDto) { return false; }
    @Override public boolean insertMcpRequestOsst(McpRequestOsstDto mcpRequestOsstDto) { return false; }
    @Override public boolean updateMcpRequestOsstOrdNo(McpRequestOsstDto mcpRequestOsstDto) { return false; }
    @Override public boolean updateMcpRequestMove(McpRequestMoveDto mcpRequestMoveDto) { return false; }
    @Override public List<AppformReqDto> getInsrCode() { return Collections.emptyList(); }
    @Override public List<IntmInsrRelDTO> getInsrProdList(IntmInsrRelDTO intmInsrRelDTO) { return Collections.emptyList(); }
    @Override public Long saveSelfDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry) { return null; }
    @Override public Long insertRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) { return null; }
    @Override public List<McpRequestSelfDlvryDto> getMcpRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) { return Collections.emptyList(); }
    @Override public McpRequestSelfDlvryDto getMcpSelfDlvryDataHist(Long selfDlvryIdx) { return null; }
    @Override public Long saveSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) { return null; }
    @Override public McpRequestSelfDlvryDto getMcpSelfDlvryData(Long selfDlvryIdx) { return null; }
    @Override public int deleteMcpRequestSelfDlvry(Long selfDlvryIdx) { return 0; }
    @Override public McpRequestSelfDlvryDto getMcpNowDlvryData(Long selfDlvryIdx) { return null; }
    @Override public Long saveNowDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry) { return null; }
    @Override public int deleteMcpRequestNowDlvry(Long selfDlvryIdx) { return 0; }
    @Override public Long saveNowDlvry(McpRequestSelfDlvryDto reqSelfDlvry) { return null; }
    @Override public boolean updateNowDlvry(McpRequestSelfDlvryDto mcpRequestSelfDlvryDto) { return false; }
    @Override public Map<String, Object> nowDlvryComplete(Long selfDlvryIdx) { return Collections.emptyMap(); }
    @Override public boolean updatePayCdNowDlvry(McpRequestSelfDlvryDto reqSelfDlvry) { return false; }
    @Override public McpRequestSelfDlvryDto getMcpNowDlvryDataHist(Long selfDlvryIdx) { return null; }
    @Override public int updateSelfViewYn(Long selfDlvryIdx) { return 0; }
    @Override public int updateNowViewYn(Long selfDlvryIdx) { return 0; }
    @Override public int checkLimitFormCount(AppformReqDto appformReqDto) { return 0; }
    @Override public MSimpleOsstXmlVO sendOsstService(String resNo, String eventCd) throws SelfServiceException, SocketTimeoutException, McpMplatFormException { return null; }
    @Override public MSimpleOsstXmlVO sendOsstService(Map<String, String> osstParam, String eventCd) throws SelfServiceException, SocketTimeoutException { return null; }
    @Override public MSimpleOsstXmlVO sendOsstAddBillService(String resNo, String eventCd, String billAcntNo) throws SelfServiceException, SocketTimeoutException, McpMplatFormException { return null; }
    @Override public MSimpleOsstXmlVO sendOsstService(String resNo, String eventCd, String gubun) throws SelfServiceException, SocketTimeoutException { return null; }
    @Override public MSimpleOsstXmlVO sendOsstService(OsstReqDto osstReqDto, String eventCd) throws SelfServiceException, SocketTimeoutException { return null; }
    @Override public MPhoneNoListXmlVO getPhoneNoList(String resNo, String eventCd) throws SelfServiceException, SocketTimeoutException { return null; }
    @Override public MPhoneNoListXmlVO getPhoneNoList(Map<String, String> osstParam, String eventCd) throws SelfServiceException, SocketTimeoutException { return null; }
    @Override public AppformReqDto getLimitForm(AppformReqDto appformReqDto) { return null; }
    @Override public boolean updateAppForPstate(long requestKey) { return false; }
    @Override public List<AppformReqDto> getFormDlveyList(AppformReqDto appformReqDto) { return Collections.emptyList(); }
    @Override public boolean updateFormDlveyUsim(AppformReqDto appformReqDto) { return false; }
    @Override public long getTempRequestKey() { return 0L; }
    @Override public int insertAppFormTempSave(AppformReqDto appformReqDto) { return 0; }
    @Override public int insertSaleinfoTempSave(AppformReqDto appformReqDto) { return 0; }
    @Override public int insertAppFormApdTempSave(AppformReqDto appformReqDto) { return 0; }
    @Override public int insertSaleinfoApdTempSave(AppformReqDto appformReqDto) { return 0; }
    @Override public McpRequestDto getMcpRequestByContractNum(String contractNum) { return null; }
    @Override public AppformReqDto getAppFormTemp(long requestKey) { return null; }
    @Override public AppformReqDto getAppForm(AppformReqDto appformReqDto) { return null; }
    @Override public boolean updateRequestTempStep1(AppformReqDto appformReqDto) { return false; }
    @Override public boolean updateRequestTempStep3(AppformReqDto appformReqDto) { return false; }
    @Override public boolean updateRequestTempStep4(AppformReqDto appformReqDto) { return false; }
    @Override public boolean updateRequestTempStep5(AppformReqDto appformReqDto) { return false; }
    @Override public List<String> getAdditionTempList(AppformReqDto appformReqDto) { return Collections.emptyList(); }
    @Override public UsimBasDto getUsimBasInfo(UsimBasDto usimBasObj) { return null; }
    @Override public AppformReqDto getNmcpRequestApdSaleinfo(Long selfDlvryIdx) { return null; }
    @Override public boolean useSelfFormSufPay(Long requestKey) { return false; }
    @Override public AppformReqDto getCopyMcpRequest(AppformReqDto appformReq) { return null; }
    @Override public int insertNmcpUsimBuyTxn(AppformReqDto appformReq) { return 0; }
    @Override public String getUsimOrgnId(String usimNo) { return null; }
    @Override public MSimpleOsstXmlUc0VO sendOsstUc0Service(OsstUc0ReqDto osstUc0ReqDto, String eventCd) throws SelfServiceException, SocketTimeoutException, McpMplatFormException { return null; }
    @Override public String usimChgChk(OsstUc0ReqDto osstUc0ReqDto) { return null; }
    @Override public int checkUploadPhoneInfoCount(String uploadPhoneSrlNo) { return 0; }
    @Override public McpUploadPhoneInfoDto getUploadPhoneInfo(long uploadPhoneSrlNo) { return null; }
    @Override public int selectStolenIp(String customer) { return 0; }
    @Override public boolean crtSaveAppFormStep(AppformReqDto appformReqDto) { return false; }
    @Override public Map<String, String> crtSaveAppFormInfo(AppformReqDto appformReqDto) { return Collections.emptyMap(); }
    @Override public Map<String, String> crtSaveSimpleAppFormInfo(AppformReqDto appformReqDto) { return Collections.emptyMap(); }
    @Override public boolean crtUpdateSimpleAppFormStep(AppformReqDto appformReqDto) { return false; }
    @Override public Map<String, String> crtUpdateSimpleAppFormInfo(AppformReqDto appformReqDto) { return Collections.emptyMap(); }
    @Override public String getChrgPrmtId(AppformReqDto appformReqDto) { return null; }
    @Override public int insertDisPrmtApd(AppformReqDto appformReqDto, String evntCd) { return 0; }
    @Override public String getDisPrmtId(AppformReqDto appformReqDto) { return null; }
    @Override public MSimpleOsstXmlSt1VO sendOsstSt1Service(String resNo, String eventCd) throws SelfServiceException, SocketTimeoutException, McpMplatFormException { return null; }
    @Override public MSimpleOsstXmlSt1VO sendOsstSt1Service(Map<String, String> osstParam, String eventCd) throws SelfServiceException, SocketTimeoutException { return null; }
    @Override public Map<String, String> chkRealPc2Result(String resNo, String contractNum) { return Collections.emptyMap(); }
    @Override public Map<String, String> isSimpleApplyPlatform(String operType) { return Collections.emptyMap(); }
    @Override public boolean updateDirectPhone(AppformReqDto appformReqDto) { return false; }
    @Override public int getNacSelfCount() { return 0; }
    @Override public Map<String, Object> mnpPreCheckLimit(String moveMobileNo) { return Collections.emptyMap(); }
    @Override public Map<String, String> checkDupReq(AppformReqDto appformReqDto) { return Collections.emptyMap(); }
    @Override public List<MspSalePlcyMstDto> getSalePlcyInfo(AppformReqDto appformReqDto) { return Collections.emptyList(); }
    @Override public boolean chkAcenReqCondition(McpRequestDto mcpRequestDto, Map<String, String> etcParam) { return false; }
    @Override public void insertAcenReqTrg(McpRequestDto mcpRequestDto) {}
    @Override public void containsGoldNumbers(List<String> reqWantNumbers) {}
    @Override public Map<String, Object> isSimpleApplyObj() { return Collections.emptyMap(); }
    @Override public AppformReqDto getJehuUsimlessByResNo(String resNo) { return null; }
    @Override public HashMap<String, Object> processOsstFt0Service(OsstFathReqDto osstFathReqDto) { return new HashMap<>(); }
    @Override public HashMap<String, Object> processOsstFs8Service(OsstFathReqDto osstFathReqDto) { return new HashMap<>(); }
    @Override public HashMap<String, Object> processOsstFs9Service(OsstFathReqDto osstFathReqDto, String resltCd) { return new HashMap<>(); }
    @Override public HashMap<String, Object> processOsstFt1Service(OsstFathReqDto osstFathReqDto) { return new HashMap<>(); }
    @Override public boolean isEnabledFT1() { return false; }
}
