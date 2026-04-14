package com.ktmmobile.msf.domains.form.form.servicechange.service;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ktmmobile.msf.domains.form.form.servicechange.dao.CustRequestDao;
import com.ktmmobile.msf.domains.form.form.servicechange.dao.MypageDao;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.BillWayChgDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CommendStateDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.JehuDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.JuoFeatureDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpFarPriceDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpRateChgDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpRegServiceDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpRetvRststnDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NmcpProdImgDtlDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.SuspenChgTmlDto;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.MoscCombReqDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceLogDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceResDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpMrktHistDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestAgrmDto;
import com.ktmmobile.msf.domains.form.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombInfoResDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscMvnoComInfo;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarMonBillingInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarMonDetailInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscSpnsrItgInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMonthPayMentDto;
import com.ktmmobile.msf.domains.form.common.mspservice.dao.MspDao;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.TerminationSettlementDto;
import com.ktds.crypto.exception.CryptoException;

@Service
public class MsfMypageSvcImpl implements MsfMypageSvc {
  
    private static final Logger logger = LoggerFactory.getLogger(MsfMypageSvcImpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    IpStatisticService ipstatisticService;

    @Autowired
    MypageDao mypageDao;

    @Autowired
    MspDao mspDao;

    @Autowired
    MsfNicePinService nicePinService;

    @Autowired
    CustRequestDao custRequestDao;

    @Autowired
    MsfMyCombinationSvc myCombinationSvc;

    @Autowired
    MsfMypageUserService mypageUserService;

    @Lazy
    @Autowired
    MsfMplatFormService mPlatFormService;

    /*
    @Autowired
    FormImageMakeSvc formImageMakeSvc;
    */

    @Override
    public List<McpUserCntrMngDto> selectCntrList(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto != null) {
            params.put("customerId", userSessionDto.getCustomerId());
        }

        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrList", params, McpUserCntrMngDto[].class);

        List<McpUserCntrMngDto> list = null;
        if (Optional.ofNullable(resultList).isPresent() && resultList.length != 0) {
            list = Arrays.asList(resultList);
            for (McpUserCntrMngDto dto : list) {
                String strUnUserSSn = dto.getUnUserSSn();
                dto.setAge(Integer.toString(getAge(strUnUserSSn)));
                if (strUnUserSSn != null && strUnUserSSn.length() > 5) {
                    dto.setBirth(strUnUserSSn.substring(0, 6));
                } else if (strUnUserSSn != null) {
                    dto.setBirth(strUnUserSSn);
                }
            }
        }
        return list;
    }

    public static int getAge(String idNum) {
        String today = "";
        String birthday = "";
        int myAge = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        today = formatter.format(new Date());
        if (idNum != null && idNum.trim().length() == 13) {
            if (idNum.charAt(6) == '1' || idNum.charAt(6) == '2' || idNum.charAt(6) == '5' || idNum.charAt(6) == '6') {
                birthday = "19" + idNum.substring(0, 6);
            } else if (idNum.charAt(6) == '*') {
                return -1;
            } else {
                birthday = "20" + idNum.substring(0, 6);
            }
        } else {
            return 0;
        }
        myAge = Integer.parseInt(today.substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4));
        if (Integer.parseInt(today.substring(4)) < Integer.parseInt(birthday.substring(4))) myAge = myAge - 1;
        return myAge;
    }

    @Override
    public String selectCustomerType(String custId) {
        RestTemplate restTemplate = new RestTemplate();
        String customerType = restTemplate.postForObject(apiInterfaceServer + "/mypage/customerType", custId, String.class);
        return customerType == null ? "" : customerType;
    }

    @Override
    public McpUserCntrMngDto selectSocDesc(String svcCntrNo) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/socDesc", svcCntrNo, McpUserCntrMngDto.class);
    }

    @Override
    public MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/mspAddInfo", svcCntrNo, MspJuoAddInfoDto.class);
    }

    @Override
    public NmcpProdImgDtlDto selectHpImgPath(String modelId) {
        return mypageDao.selectHpImgPath(modelId);
    }

    @Override
    public McpRetvRststnDto retvRstrtn(HashMap<String, String> map) {
        return mypageDao.retvRstrtn(map);
    }

    @Override
    public void retvRstrtnInsert(HashMap<String, String> map) {
        mypageDao.retvRstrtnInsert(map);
    }

    @Override
    public void retvRstrtnUpCnt(HashMap<String, String> map) {
        mypageDao.retvRstrtnUpCnt(map);
    }

    @Override
    public void retvRstrtnUpSysDate(HashMap<String, String> map) {
        mypageDao.retvRstrtnUpSysDate(map);
    }

    @Override
    public List<McpRegServiceDto> selectRegService(String ncn) {
        if (StringUtils.isBlank(ncn)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        RestTemplate restTemplate = new RestTemplate();
        McpRegServiceDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/mypage/regService", ncn, McpRegServiceDto[].class);
        return Arrays.asList(rtnList);
    }

    @Override
    public McpFarPriceDto selectFarPricePlan(String ncn) {
        if (StringUtils.isBlank(ncn)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/farPricePlan", ncn, McpFarPriceDto.class);
    }

    @Override
    public List<McpFarPriceDto> selectFarPricePlanList(String rateCd) {
        RestTemplate restTemplate = new RestTemplate();
        McpFarPriceDto[] list = restTemplate.postForObject(apiInterfaceServer + "/mypage/farPricePlanList", rateCd, McpFarPriceDto[].class);
        return Arrays.asList(list);
    }

    @Override
    public int countFarPricePlanList(String rateCd) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/countFarPricePlanList", rateCd, int.class);
    }

    @Override
    public String selectJejuid(String contractNum) {
        return mypageDao.selectJejuid(contractNum);
    }

    @Override
    public void updatePointMgmt(Map<String, String> map) {
        mypageDao.updatePointMgmt(map);
    }

    @Override
    public void insertPointMgmt(Map<String, String> map) {
        mypageDao.insertPointMgmt(map);
    }

    @Override
    @Transactional
    public void setJejuPointMgmt(Map<String, String> map) {
        String pointId = StringUtil.NVL(map.get("pointId"), "").trim();
        String contractNum = map.get("contractNum");
        String oldPointId = StringUtil.NVL(mypageDao.selectJejuid(contractNum), "");

        if (!pointId.equals(oldPointId)) {
            map.put("memo", "홈페이지 회원정보수정");
            mypageDao.updatePointMgmt(map);
            if (!pointId.equals("")) {
                mypageDao.insertPointMgmt(map);
            }
        }
    }

    @Override
    public Map<String, Object> selectCertHist(String userId) {
        return mypageDao.selectCertHist(userId);
    }

    @Override
    public void insertCertHist(Map<String, String> map) {
        String outputDivCd = map.get("outputDivCd");
        if (outputDivCd == null || outputDivCd.equals("")) {
            map.put("outputDivCd", "01");
        }
        mypageDao.insertCertHist(map);
    }

    @Override
    public Map<String, BigDecimal> selectModelSaleInfo(String ncn) {
        return mypageDao.selectModelSaleInfo(ncn);
    }

    @Override
    public String selectFarPriceAddInfo(Map<String, String> map) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/farPriceAddInfo", map, String.class);
    }

    @Override
    public List<JehuDto> selectJehuList(String contractNum, int skipResult, int maxResult) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("contractNum", contractNum);
        map.put("skipResult", skipResult);
        map.put("maxResult", maxResult);
        RestTemplate restTemplate = new RestTemplate();
        JehuDto[] list = restTemplate.postForObject(apiInterfaceServer + "/mypage/selectJehuList", map, JehuDto[].class);
        return Arrays.asList(list);
    }

    @Override
    public List<JehuDto> selectJehuListAll(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        JehuDto[] list = restTemplate.postForObject(apiInterfaceServer + "/mypage/jehuListAll", contractNum, JehuDto[].class);
        return Arrays.asList(list);
    }

    @Override
    public int selectJehuListCnt(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/jehuListCount", contractNum, int.class);
    }

    @Override
    public McpRateChgDto saveNmcpRateChgHist(McpRateChgDto mcpRateChgDto) {
        String soc = StringUtil.NVL(mcpRateChgDto.getRateCd(), "");
        boolean isFinaceRate = false;

        List<NmcpCdDtlDto> clauseFinanceRatesCDList = NmcpServiceUtils.getCodeList("ClauseFinanceRatesCD");
        if (clauseFinanceRatesCDList != null) {
            for (NmcpCdDtlDto dto : clauseFinanceRatesCDList) {
                if (soc.equals(dto.getDtlCd())) {
                    isFinaceRate = true;
                    break;
                }
            }
        }

        if (isFinaceRate) {
            String createYn = mcpRateChgDto.getCreateYn();
            if (createYn.equals(Constants.MCPRATE_CHG_HIST_CREATEYN_REQ)) {
                NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
                String cStmrName = StringUtil.NVL(mcpRateChgDto.getCstmrName(), "");
                String birthDate = "";
                try {
                    RestTemplate restTemplate = new RestTemplate();
                    birthDate = restTemplate.postForObject(apiInterfaceServer + "/msp/customerSsn", mcpRateChgDto.getContractNum(), String.class);
                    birthDate = EncryptUtil.ace256Dec(birthDate);
                    birthDate = birthDate.substring(0, 6);
                } catch (CryptoException e) {
                    mcpRateChgDto.setResultFlag(false);
                    mcpRateChgDto.setResultMsg(ExceptionMsgConstant.ACE_256_DECRYPT_EXCEPTION);
                    return mcpRateChgDto;
                }

                if (sessNiceRes != null) {
                    if (cStmrName.equalsIgnoreCase(sessNiceRes.getName()) && birthDate.equals(sessNiceRes.getBirthDate().substring(2, 8))) {
                        String resNo = "ReqNo:".concat(sessNiceRes.getReqSeq()).concat(", ResNo:").concat(sessNiceRes.getResSeq());
                        mcpRateChgDto.setResNo(resNo);
                        mypageDao.inertNmcpRateChg(mcpRateChgDto);
                    } else {
                        mcpRateChgDto.setResultFlag(false);
                        mcpRateChgDto.setResultMsg(ExceptionMsgConstant.NICE_CERT_EXCEPTION);
                    }
                } else {
                    mcpRateChgDto.setResultFlag(false);
                    mcpRateChgDto.setResultMsg(ExceptionMsgConstant.F_BIND_EXCEPTION);
                }

            } else if (createYn.equals(Constants.MCPRATE_CHG_HIST_CREATEYN_SUCESS)) {
                mypageDao.updateNmcpRateChg(mcpRateChgDto);
            } else if (createYn.equals(Constants.MCPRATE_CHG_HIST_CREATEYN_FAIL)
                    || createYn.equals(Constants.MCPRATE_CHG_HIST_CREATEYN_TIMEOUT)) {
                mypageDao.updateNmcpRateChg(mcpRateChgDto);
            }
        } else {
            mcpRateChgDto.setResultFlag(true);
        }
        return mcpRateChgDto;
    }

    @Override
    public McpRateChgDto selectNmcpRateChg(McpRateChgDto mcpRateChgDto) {
        return mypageDao.selectNmcpRateChg(mcpRateChgDto);
    }

    @Override
    public McpRateChgDto reMakeFinanceTemplate(McpRateChgDto mcpRateChgDto) {
        mypageDao.selectNmcpRateChg(mcpRateChgDto);
        return mcpRateChgDto;
    }

    @Override
    public int selectCallSvcLimitCount(HashMap<String, String> hm) {
        return mypageDao.selectCallSvcLimitCount(hm);
    }

    @Override
    public void insertMcpSelfcareStatistic(HashMap<String, String> hm) {
        mypageDao.insertMcpSelfcareStatistic(hm);
    }

    @Override
    public boolean insertMcpRequestArm(McpRequestAgrmDto mcpRequestAgrmDto) {
        return mypageDao.insertMcpRequestArm(mcpRequestAgrmDto);
    }

    @Override
    public String getCommendId(String contractNum) {
        return mypageDao.getCommendId(contractNum);
    }

    @Override
    public List<CommendStateDto> getCommendStateList(String commendId) {
        RestTemplate restTemplate = new RestTemplate();
        CommendStateDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/commendStateList", commendId, CommendStateDto[].class);
        return Arrays.asList(resultList);
    }

    @Override
    public List<CommendStateDto> getCommendStateList(String commendId, MyPageSearchDto searchVO) {
        String prcsMdlInd = DateTimeUtil.getFormatString("yyyyMMddHHmmss");

        MoscCombReqDto moscCombReqDto = new MoscCombReqDto();
        moscCombReqDto.setCustId(searchVO.getCustId());
        moscCombReqDto.setNcn(searchVO.getNcn());
        moscCombReqDto.setCtn(searchVO.getCtn());
        moscCombReqDto.setCombSvcNoCd("M");
        moscCombReqDto.setCombSrchId(searchVO.getCtn());
        moscCombReqDto.setSameCustKtRetvYn("Y");

        MoscCombInfoResDTO moscCombInfoResDTO = myCombinationSvc.selectCombiMgmtListLog(moscCombReqDto, prcsMdlInd, searchVO.getContractNum());
        String internetId = "";

        if (moscCombInfoResDTO != null && moscCombInfoResDTO.isSuccess()
                && moscCombInfoResDTO.getMoscSrchCombInfoList() != null
                && !moscCombInfoResDTO.getMoscSrchCombInfoList().isEmpty()) {
            for (MoscMvnoComInfo item : moscCombInfoResDTO.getMoscSrchCombInfoList()) {
                if ("인터넷".equals(item.getSvcDivCd()) && "MVNOKIS".equals(item.getCorrNm())) {
                    internetId = item.getSvcNo() == null ? "" : item.getSvcNo();
                    break;
                }
            }
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("commendId", commendId);
        params.put("internetId", internetId);
        RestTemplate restTemplate = new RestTemplate();
        CommendStateDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/selectCommendAllStateList", params, CommendStateDto[].class);
        return Optional.ofNullable(resultList).map(Arrays::asList).orElseGet(Collections::emptyList);
    }

    @Override
    public McpUserCntrMngDto selectSocDescNoLogin(String svcCntrNo) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/socDescNoLogin", svcCntrNo, McpUserCntrMngDto.class);
    }

    @Override
    public McpUserCntrMngDto selectCntrListNoLogin(String contractNum) {
        if (contractNum == null || "".equals(contractNum)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setSvcCntrNo(contractNum);
        return selectCntrListNoLogin(userCntrMngDto);
    }

    @Override
    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        if (userCntrMngDto.getSvcCntrNo() == null && userCntrMngDto.getCntrMobileNo() == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);
    }

    @Override
    public List<SuspenChgTmlDto> selectSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto) {
        return mypageDao.selectSuspenChgTmp(suspenChgTmlDto);
    }

    @Override
    public List<SuspenChgTmlDto> selectSuspenChgTmpList() {
        return mypageDao.selectSuspenChgTmpList();
    }

    @Override
    public int suspenChgUpdate(SuspenChgTmlDto reqDto) {
        return mypageDao.suspenChgUpdate(reqDto);
    }

    @Override
    public JuoFeatureDto getJuoFeature(JuoFeatureDto juoFeatureDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/getJuoFeature", juoFeatureDto, JuoFeatureDto.class);
    }

    @Override
    public MspJuoAddInfoDto getEnggInfo(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/enggInfo1", contractNum, MspJuoAddInfoDto.class);
    }

    @Override
    public MspJuoAddInfoDto getChannelInfo(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/channelInfo", contractNum, MspJuoAddInfoDto.class);
    }

    @Override
    public List<McpUserCntrMngDto> getCloseSubList(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/mypage/closeSubList", contractNum, McpUserCntrMngDto[].class);
        return Arrays.asList(rtnList);
    }

    @Override
    public boolean insertServiceAlterTrace(McpServiceAlterTraceDto serviceAlterTrace) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        
        //PNB_확인필요 serviceAlterTrace.setAccessIp(ipStatisticService.getClientIp());
        
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if (userSession != null) {
            serviceAlterTrace.setUserId(userSession.getUserId());
        }
        serviceAlterTrace.setAccessUrl(request.getRequestURI());
        return mypageDao.insertServiceAlterTrace(serviceAlterTrace);
    }

    @Override
    public boolean insertSocfailProcMst(McpServiceAlterTraceDto serviceAlterTrace) {
        if (StringUtils.isBlank(serviceAlterTrace.getChgType())) {
            serviceAlterTrace.setChgType("I");
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/insertSocfailProcMst", serviceAlterTrace, boolean.class);
    }

    @Override
    public List<McpUserCntrMngDto> getromotionDcList(String toSocCode) {
        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto[] list = restTemplate.postForObject(apiInterfaceServer + "/mypage/romotionDcList", toSocCode, McpUserCntrMngDto[].class);
        return Arrays.asList(list);
    }

    @Override
    public String selectBanSel(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/selectBanSel", contractNum, String.class);
    }

    @Override
    public BillWayChgDto getMspData(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/billWayReSend", contractNum, BillWayChgDto.class);
    }

    @Override
    public void insertMcpBillwayResend(BillWayChgDto billWayChgDto) {
        mypageDao.insertMcpBillwayResend(billWayChgDto);
    }

    @Override
    public boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession) {
        String userDivision = userSession.getUserDivision();
        if (!StringUtil.equals(userDivision, "01")) {
            return false;
        }
        if (cntrList == null || cntrList.isEmpty()) {
            return false;
        }

        boolean isOwn = false;
        String paramNcn = StringUtil.NVL(searchVO.getNcn(), "");
        String sesNcn = StringUtil.NVL(SessionUtils.getCurrPhoneNcn(), "");
        String selNcn = StringUtil.NVL(paramNcn, sesNcn);
        searchVO.setMoCtn(cntrList.size());

        if (StringUtil.isEmpty(selNcn)) {
            McpUserCntrMngDto first = cntrList.get(0);
            searchVO.setNcn(first.getSvcCntrNo());
            searchVO.setCtn(first.getCntrMobileNo());
            searchVO.setCustId(first.getCustId());
            searchVO.setModelName(first.getModelName());
            searchVO.setContractNum(first.getContractNum());
            searchVO.setSoc(first.getSoc());
            SessionUtils.setCurrPhoneNcn(first.getSvcCntrNo());
            isOwn = true;
        }

        for (McpUserCntrMngDto dto : cntrList) {
            String ctn = dto.getCntrMobileNo();
            String ncn = dto.getSvcCntrNo();
            dto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
            if (StringUtil.equals(selNcn, String.valueOf(ncn))) {
                searchVO.setNcn(ncn);
                searchVO.setCtn(ctn);
                searchVO.setCustId(dto.getCustId());
                searchVO.setModelName(dto.getModelName());
                searchVO.setContractNum(dto.getContractNum());
                searchVO.setSoc(dto.getSoc());
                SessionUtils.setCurrPhoneNcn(ncn);
                isOwn = true;
            }
        }
        return isOwn;
    }

    @Override
    public List<McpUserCntrMngDto> selectPoList(List<McpUserCntrMngDto> cntrList) {
        List<McpUserCntrMngDto> poList = new ArrayList<>();
        if (cntrList == null || cntrList.isEmpty()) {
            return cntrList;
        }
        for (McpUserCntrMngDto dto : cntrList) {
            if ("PO".equals(dto.getPppo())) {
                poList.add(dto);
            }
        }
        return poList;
    }

    @Override
    public int checkAllreadPlanchgCount(McpServiceAlterTraceDto serviceAlterTrace) {
        return mypageDao.checkAllreadPlanchgCount(serviceAlterTrace);
    }

    @Override
    public List<McpMrktHistDto> selectExistingConsent(String userId) {
        List<McpMrktHistDto> mrktList = mypageDao.selectExistingConsent(userId);
        return !CollectionUtils.isEmpty(mrktList) ? mrktList : null;
    }

    @Override
    public int insertDisApd(McpUserCntrMngDto apdDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/insertDisApd", apdDto, int.class);
    }

    @Override
    public String getChrgPrmtIdSocChg(String rateCd) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/getChrgPrmtIdSocChg", rateCd, String.class);
    }

    @Override
    public McpUserCntrMngDto getCntrInfoByContNum(String contractNum) {
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = this.selectCntrList(userSession.getUserId());
        if (cntrList != null && !cntrList.isEmpty()) {
            for (McpUserCntrMngDto dto : cntrList) {
                if (contractNum.equals(dto.getContractNum()) || contractNum.equals(dto.getSvcCntrNo())) {
                    return dto;
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, String> checkAuthUser(String name, String userSsn) {
        Map<String, String> rtnMap = new HashMap<>();
        rtnMap.put("returnCode", "0000");

        UserSessionDto session = SessionUtils.getUserCookieBean();
        if (session != null) {
            String paramName = StringUtil.NVL(name, "").replaceAll(" ", "");
            String sessionName = session.getName().replaceAll(" ", "");

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String referer = StringUtil.NVL(request.getHeader("REFERER"), "");

            if (referer.indexOf("/reqSelfDlvry.do") > -1 || referer.indexOf("/replaceUsimView.do") > -1) {
                if (StringUtils.isBlank(paramName)) {
                    rtnMap.put("returnCode", "0010");
                    rtnMap.put("returnMsg", "이름이 입력되지 않았습니다.<br/>새로고침 후 다시 시도해주세요.");
                } else {
                    if (!paramName.equalsIgnoreCase(sessionName)) {
                        rtnMap.put("returnCode", "0010");
                        rtnMap.put("returnMsg", "회원정보와 본인인증 정보가 일치하지 않습니다.<br/>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.");
                    }
                }
                return rtnMap;
            }

            if (StringUtils.isBlank(paramName) || StringUtils.isBlank(userSsn)) {
                rtnMap.put("returnCode", "0010");
                rtnMap.put("returnMsg", "이름, 주민번호가 입력되지 않았습니다.<br/>새로고침 후 다시 시도해주세요.");
            } else {
                if ("00".equals(session.getUserDivision())) {
                    if (!paramName.equalsIgnoreCase(sessionName)) {
                        rtnMap.put("returnCode", "0010");
                        rtnMap.put("returnMsg", "회원정보와 본인인증 정보가 일치하지 않습니다.<br/>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.");
                        return rtnMap;
                    }
                    NiceLogDto niceLogDto = new NiceLogDto();
                    niceLogDto.setName(name);
                    niceLogDto.setBirthDate(userSsn);
                    Map<String, String> nicePinRtn = nicePinService.getNicePinDi(niceLogDto);
                    if ("0000".equals(nicePinRtn.get("returnCode"))) {
                        if (!session.getPin().equals(nicePinRtn.get("dupInfo"))) {
                            rtnMap.put("returnCode", "0010");
                            rtnMap.put("returnMsg", "회원정보와 본인인증 정보가 일치하지 않습니다.<br/>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.");
                        }
                    } else {
                        rtnMap.put("returnCode", "9999");
                        rtnMap.put("returnMsg", nicePinRtn.get("returnMsg"));
                    }
                } else if ("01".equals(session.getUserDivision())) {
                    List<McpUserCntrMngDto> cntrList = this.selectCntrList(session.getUserId());
                    if (cntrList != null && !cntrList.isEmpty()) {
                        McpUserCntrMngDto cntrMngDto = cntrList.get(0);
                        String cntrName = cntrMngDto.getUserName().replaceAll(" ", "");
                        if (!paramName.equalsIgnoreCase(cntrName) || !userSsn.equals(cntrMngDto.getUnUserSSn())) {
                            rtnMap.put("returnCode", "0010");
                            rtnMap.put("returnMsg", "회원정보와 본인인증 정보가 일치하지 않습니다.<br/>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.");
                        }
                    } else {
                        rtnMap.put("returnCode", "0010");
                        rtnMap.put("returnMsg", "회원정보가 확인되지 않습니다.<br/>로그아웃 후 이용 해 주시기 바랍니다.");
                    }
                } else {
                    rtnMap.put("returnCode", "0010");
                    rtnMap.put("returnMsg", "회원정보가 확인되지 않습니다.<br/>로그아웃 후 이용 해 주시기 바랍니다.");
                }
            }
        }
        return rtnMap;
    }

    @Override
    public List<SuspenChgTmlDto> selectChrgPrmtCheckTmp() {
        return mypageDao.selectChrgPrmtCheckTmp();
    }

    @Override
    public int updateChrgPrmtCheckTmp(SuspenChgTmlDto suspenChgTmlDto) {
        return mypageDao.updateChrgPrmtCheckTmp(suspenChgTmlDto);
    }

    @Override
    public int insertSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto) {
        return mypageDao.insertSuspenChgTmp(suspenChgTmlDto);
    }

    @Override
    public Map<String, String> selectRateMst(String rateCd) {
        return mypageDao.selectRateMst(rateCd);
    }

    @Override
    public String getChangeOfNameData(long custReqSeq, String reqType) {
        Map<String, String> requestData = null;
        if ("CL".equals(reqType)) {
            requestData = custRequestDao.getReqCallListData(custReqSeq);
        } else if ("NC".equals(reqType)) {
            requestData = custRequestDao.getReqNmChgData(custReqSeq);
        } else if ("IS".equals(reqType)) {
            requestData = custRequestDao.getReqInsrData(custReqSeq);
        } else if ("CC".equals(reqType)) {
            requestData = custRequestDao.getCancelConsultData(custReqSeq);
        }
        return requestData == null ? "FAIL" : "SUCCESS";
    }

    /**
     * requestView 위약금 블록(Try 블록 2)과 동일한 로직.
     * ① 선불 요금제 여부 확인 → 선불이면 조기 반환
     * ② X54(스폰서/위약금) 조회
     * ③ saleEngtNm 이 존재하는 경우에만 X16(잔여 할부금) + mspAddInfo(할부원금) 조회
     *
     * @param ncn    서비스 계약번호
     * @param ctn    휴대폰번호
     * @param custId 고객번호
     * @return TerminationSettlementDto
     */
    @Override
    public TerminationSettlementDto getTerminationSettlement(String ncn, String ctn, String custId) {
        TerminationSettlementDto settlement = new TerminationSettlementDto();
        try {
            // ① 선불 요금제 여부
            boolean prePayment = mypageUserService.selectPrePayment(ncn);
            settlement.setPrePayment(prePayment);
            if (prePayment) {
                // 선불 요금제이면 위약금 조회 불필요
                return settlement;
            }

            // ② X54 스폰서/위약금 조회
            MpMoscSpnsrItgInfoInVO moscSpnsrItgInfo = mPlatFormService.kosMoscSpnsrItgInfo(ncn, ctn, custId);

            // null-safe 처리 (requestView 동일 패턴)
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getChageDcAmt()))              moscSpnsrItgInfo.setChageDcAmt("0");
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getTrmnForecBprmsAmt()))       moscSpnsrItgInfo.setTrmnForecBprmsAmt("0");
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getRtrnAmtAndChageDcAmt()))    moscSpnsrItgInfo.setRtrnAmtAndChageDcAmt("0");
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getChageDcAmtSuprtRtrnAmt())) moscSpnsrItgInfo.setChageDcAmtSuprtRtrnAmt("0");

            // X54 결과 → DTO 매핑
            settlement.setSaleEngtNm(moscSpnsrItgInfo.getSaleEngtNm());
            settlement.setSaleEngtOptnCd(moscSpnsrItgInfo.getSaleEngtOptnCd());
            settlement.setTrmnForecBprmsAmt(moscSpnsrItgInfo.getTrmnForecBprmsAmt());
            settlement.setChageDcAmt(moscSpnsrItgInfo.getChageDcAmt());
            settlement.setRtrnAmtAndChageDcAmt(moscSpnsrItgInfo.getRtrnAmtAndChageDcAmt());
            settlement.setChageDcAmtSuprtRtrnAmt(moscSpnsrItgInfo.getChageDcAmtSuprtRtrnAmt());
            settlement.setKtSuprtPenltAmt(moscSpnsrItgInfo.getKtSuprtPenltAmt());
            settlement.setStorSuprtPenltAmt(moscSpnsrItgInfo.getStorSuprtPenltAmt());
            settlement.setEngtAplyStDate(moscSpnsrItgInfo.getEngtAplyStDate());
            settlement.setEngtExpirPamDate(moscSpnsrItgInfo.getEngtExpirPamDate());
            settlement.setEngtRmndDate(moscSpnsrItgInfo.getEngtRmndDate());

            // ③ saleEngtNm 이 있는 경우에만 잔여 할부금 / 할부원금 조회 (requestView 동일 조건)
            if (StringUtil.isNotBlank(moscSpnsrItgInfo.getSaleEngtNm())) {

                // X16 잔여 할부금 조회 — 최신 청구월 기준으로 조회하기 위해 X15(월이용요금) 먼저 호출
                try {
                    MpFarMonBillingInfoDto billInfo = mPlatFormService.farMonBillingInfoDto(
                        ncn, ctn, custId, DateTimeUtil.getFormatString("yyyyMM"));

                    if (billInfo != null && billInfo.getMonthList() != null && !billInfo.getMonthList().isEmpty()) {
                        MpMonthPayMentDto monthPay = billInfo.getMonthList().get(0); // 최신 청구월
                        MpFarMonDetailInfoDto farMonDetailInfoDto = mPlatFormService.farMonDetailInfoDto(
                            ncn, ctn, custId,
                            monthPay.getBillSeqNo(),
                            monthPay.getBillDueDateList(),
                            monthPay.getBillMonth(),
                            monthPay.getBillStartDate(),
                            monthPay.getBillEndDate());

                        if (StringUtil.isEmpty(farMonDetailInfoDto.getInstallmentAmt())) {
                            farMonDetailInfoDto.setInstallmentAmt("0");
                        }
                        settlement.setInstallmentAmt(farMonDetailInfoDto.getInstallmentAmt());
                        settlement.setTotalNoOfInstall(farMonDetailInfoDto.getTotalNoOfInstall());
                        settlement.setInstallmentYN(farMonDetailInfoDto.getInstallmentYN());
                    }
                } catch (Exception e) {
                    logger.warn("[getTerminationSettlement] X16 잔여 할부금 조회 오류: ncn={}, {}", ncn, e.getMessage());
                }

                // 할부원금 조회 — MsfMypageSvc.selectMspAddInfo() 재사용
                try {
                    MspJuoAddInfoDto mspJuoAddInfoDto = this.selectMspAddInfo(ncn);
                    if (mspJuoAddInfoDto != null) {
                        settlement.setInstOrginAmnt(mspJuoAddInfoDto.getInstOrginAmnt());
                        settlement.setInstMnthCnt(mspJuoAddInfoDto.getInstMnthCnt());
                        settlement.setRemainPay(mspJuoAddInfoDto.getRemainPay());
                        settlement.setRemainMonth(mspJuoAddInfoDto.getRemainMonth());
                        settlement.setModelName(mspJuoAddInfoDto.getModelName());
                    }
                } catch (Exception e) {
                    logger.warn("[getTerminationSettlement] mspAddInfo 조회 오류: ncn={}, {}", ncn, e.getMessage());
                }
            }

        } catch (Exception e) {
            logger.error("[getTerminationSettlement] 위약금 정산 조회 오류: ncn={}, ctn={}, {}", ncn, ctn, e.getMessage());
        }
        return settlement;
    }
}
