//package com.ktmmobile.msf.system.common.service;
//
//import com.ktmmobile.msf.system.common.cache.DbCacheHandler;
//import com.ktmmobile.msf.system.common.commCode.dao.CommCodeDAO;
//import com.ktmmobile.msf.system.common.dao.FCommonDao;
//import com.ktmmobile.msf.system.common.dto.*;
//import com.ktmmobile.msf.system.common.dto.db.MspCommDatPrvTxnDto;
//import com.ktmmobile.msf.system.common.dto.db.MspSmsTemplateMstDto;
//import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
//import com.ktmmobile.msf.system.common.exception.McpCommonException;
//import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
//import com.ktmmobile.msf.system.common.exception.McpErropPageException;
//import com.ktmmobile.msf.system.common.mplatform.MsfMplatFormService;
//import com.ktmmobile.msf.system.common.mplatform.dto.CdInfoDto;
//import com.ktmmobile.msf.system.common.mplatform.dto.CommCdInfoRes;
//import com.ktmmobile.msf.system.common.mspservice.MspService;
//import com.ktmmobile.msf.system.common.mspservice.dto.MspRateMstDto;
//import com.ktmmobile.msf.system.common.util.DateTimeUtil;
//import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
//import com.ktmmobile.msf.system.common.util.SessionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.text.ParseException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static com.ktmmobile.msf.system.common.constants.CacheConstants.*;
//import static com.ktmmobile.msf.system.common.constants.Constants.SERVICE_DOWNTIME;
//import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
//
//@Service
//public class FCommonSvcImpl implements FCommonSvc {
//
//    private  static final Logger logger = LoggerFactory.getLogger(FCommonSvcImpl.class);
//
//    @Value("${SERVER_NAME}")
//    private String serverName;
//
//    @Autowired
//    private FCommonDao fCommonDao;
//
//    @Autowired
//    CommCodeDAO commCodeDAO;
//
//    @Autowired
//    private SmsSvc smsSvc;
//
//    @Autowired
//    private DbCacheHandler dbCacheHandler ;
//
//
//    @Autowired
//    private MsfMplatFormService mplatFormService;
//
//    @Autowired
//    MspService mspService;
//
//    @Value("${api.interface.server}")
//    private String apiServer;
//
//
//    @Autowired
//    private IpStatisticService ipstatisticService;
//
//
//    //캐시-공통코드
//    @Override
//    public void getCodeCahe() {
//        List<CdGroupBean> list = fCommonDao.getCodeAllList();
//        Map<String, List<NmcpCdDtlDto>> codeMap = new HashMap<String, List<NmcpCdDtlDto>>();
//        //Map<String, Map<String, String>> codeValueMap = new HashMap<String, Map<String, String>>();
//
//        for(CdGroupBean cdGroupBean : list){
//            //Map<String, String> code = new HashMap<String, String>();
//
//            //for(NmcpCdDtlDto cdBean : cdGroupBean.getListCdBean()){
//            //    code.put(cdBean.getDtlCd(), cdBean.getDtlCdNm());
//            //}
//            //codeValueMap.put(cdGroupBean.getCdGroupId(), code);
//            codeMap.put(cdGroupBean.getCdGroupId(), cdGroupBean.getListCdBean());
//        }
//
//        if(dbCacheHandler.getElement(CACHE_CODE) == null) {
//            dbCacheHandler.put(CACHE_CODE, codeMap);
//        } else {
//            dbCacheHandler.replace(CACHE_CODE, codeMap);
//        }
////        if(dbCacheHandler.getElement(CACHE_CODE_VALUE) == null) {
////            dbCacheHandler.put(CACHE_CODE_VALUE, codeValueMap);
////        } else {
////            dbCacheHandler.replace(CACHE_CODE_VALUE, codeValueMap);
////        }
//    }
//
//    //캐시-메뉴
//    @Override
//    public void getMenuCahe() {
//        List<SiteMenuDto> list = fCommonDao.getMenuAllList();
//
//        if(dbCacheHandler.getElement(CACHE_MENU) == null) {
//            dbCacheHandler.put(CACHE_MENU, list);
//        } else {
//            dbCacheHandler.replace(CACHE_MENU, list);
//        }
//    }
//
//    //캐시-메뉴권한
//    @Override
//    public void getMenuAuthCahe() {
//        List<SiteMenuDto> list = fCommonDao.getMenuAuthList();
//
//        if(dbCacheHandler.getElement(CACHE_MENUAUTH) == null) {
//            dbCacheHandler.put(CACHE_MENUAUTH, list);
//        } else {
//            dbCacheHandler.replace(CACHE_MENUAUTH, list);
//        }
//    }
//
//    //캐시-배너
//    @Override
//    public void getBannerCahe() {
//        List<BannerDto> list = fCommonDao.getBannerAllList();
//
//        if(dbCacheHandler.getElement(CACHE_BANNER) == null) {
//            dbCacheHandler.put(CACHE_BANNER, list);
//        } else {
//            dbCacheHandler.replace(CACHE_BANNER, list);
//        }
//    }
//
//    //캐시-배너
//    @Override
//    public void getBannerApdCahe() {
//        List<BannerDto> list = fCommonDao.getBannerApdList();
//
//        if(dbCacheHandler.getElement(CACHE_BANNERAPD) == null) {
//            dbCacheHandler.put(CACHE_BANNERAPD, list);
//        } else {
//            dbCacheHandler.replace(CACHE_BANNERAPD, list);
//        }
//    }
//
//    //캐시-팝업
//    @Override
//    public void getPopupCahe() {
//        List<PopupDto> list = fCommonDao.getPopupAllList();
//
//        if(dbCacheHandler.getElement(CACHE_POPUP) == null) {
//            dbCacheHandler.put(CACHE_POPUP, list);
//        } else {
//            dbCacheHandler.replace(CACHE_POPUP, list);
//        }
//    }
//
//    //캐시-작업공지
//    @Override
//    public void getMenuUrlNotiCahe() {
//        List<WorkNotiDto> list = fCommonDao.getMenuUrlAllList();
//
//        if(dbCacheHandler.getElement(CACHE_WORKNOTI) == null) {
//            dbCacheHandler.put(CACHE_WORKNOTI, list);
//        } else {
//            dbCacheHandler.replace(CACHE_WORKNOTI, list);
//        }
//    }
//
//    //캐시-작업공지우회
//    @Override
//    public void getAcesAlwdCahe() {
//        List<AcesAlwdDto> list = fCommonDao.getAcesAlwdList();
//
//        if(dbCacheHandler.getElement(CACHE_ACESALWD) == null) {
//            dbCacheHandler.put(CACHE_ACESALWD, list);
//        } else {
//            dbCacheHandler.replace(CACHE_ACESALWD, list);
//        }
//    }
//
//    //캐시-텍스트 배너
//    @Override
//    public void getBannerTextCahe() {
//        List<BannerTextDto> list = fCommonDao.getBannerTextList();
//
//        if(dbCacheHandler.getElement(CACHE_BANNERTEXT) == null) {
//            dbCacheHandler.put(CACHE_BANNERTEXT, list);
//        } else {
//            dbCacheHandler.replace(CACHE_BANNERTEXT, list);
//        }
//    }
//
//    //캐시-플로팅 배너
//    @Override
//    public void getBannerFloatCahe() {
//        List<BannerFloatDto> list = fCommonDao.getBannerFloatList();
//
//        if(dbCacheHandler.getElement(CACHE_BANNERFLOAT) == null) {
//            dbCacheHandler.put(CACHE_BANNERFLOAT, list);
//        } else {
//            dbCacheHandler.replace(CACHE_BANNERFLOAT, list);
//        }
//    }
//
//
//    @Override
//    public List<NmcpCdDtlDto> getCodeList(NmcpCdDtlDto nmcpCdDtlDto) {
//        return fCommonDao.getCodeList(nmcpCdDtlDto);
//    }
//
//    @Override
//    public NmcpCdDtlDto getCodeNm(NmcpCdDtlDto nmcpCdDtlDto) {
//        return fCommonDao.getCodeNm(nmcpCdDtlDto);
//    }
//
//
//    @Override
//    public MspRateMstDto getMspRateMst(String rateCd) {
//        return fCommonDao.getMspRateMst(rateCd);
//    }
//
//    @Override
//    public boolean sendAuthSms(AuthSmsDto authSmsDto) {
//        boolean result = true;
//
//        //6자리 인증번호 생성
//        StringBuffer authSmsNo = new StringBuffer();
//
//        Random objRandom;
//        try {
//            objRandom = SecureRandom.getInstance("SHA1PRNG");
//        } catch (NoSuchAlgorithmException e) {
//            throw new McpErropPageException(COMMON_EXCEPTION);
//        }
//
//        for(int i=0 ; i<6 ; i++) {
//            authSmsNo.append(objRandom.nextInt(10)) ;
//        }
//
//        //SMS 인증번호 발송
//        StringBuffer message = new StringBuffer();
//        message.append("인증번호는 ").append(authSmsNo).append(" 입니다.");
//        try {
//           smsSvc.sendSmsForAuth(authSmsDto.getPhoneNum(),false,message.toString());
//        } catch(Exception e) {
//            return false;
//        }
//
//        authSmsDto.setAuthNum(authSmsNo.toString());
//        String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
//        authSmsDto.setStartDate(today);
//        //관리자 정보 session 저장
//        SessionUtils.setAuthSmsSession(authSmsDto);
//
//        return result;
//    }
//
//    /**
//     * <pre>
//     * 설명     : 팝업 리스트 조회
//     * @param
//     * @return
//     * @return: List<PopupDto>
//     * </pre>
//     */
//    @Override
//    public List<PopupDto> getPopupList(PopupDto popupDto) {
//        return fCommonDao.getPopupList(popupDto);
//    }
//
//    /**
//     * <pre>
//     * 설명     : 팝업 상세 조회
//     * @param popupSeq
//     * @return
//     * @return: PopupDto
//     * </pre>
//     */
//    @Override
//    public PopupDto getPopupDetail(String popupSeq) {
//        String device = "PC";
//        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
//            device = "MO";
//        }
//        PopupDto popupDto = new PopupDto();
//        popupDto.setPopupSeq(popupSeq);
//        popupDto.setDevice(device);
//
//        return fCommonDao.getPopupDetail(popupDto);
//    }
//
//    /**
//     * <pre>
//     * 설명     : 팝업 리스트 조회 메인PC
//     * @param
//     * @return
//     * @return: List<PopupDto>
//     * </pre>
//     */
//    @Override
//    public List<PopupDto> getPopupMainList(String menuCode) {
//        // TODO Auto-generated method stub
//        return fCommonDao.getPopupMainList(menuCode);
//    }
//
//
//    @Override
//    public boolean insertmspCommDatPrvTxn(MspCommDatPrvTxnDto mspCommDatPrvTxnDto) {
//        return fCommonDao.insertmspCommDatPrvTxn(mspCommDatPrvTxnDto);
//    }
//
//    /**
//    * @Description : NMCP Login 정보 저장 테이블에 저장한다.
//    * @param loginHistoryDto
//    * @return
//    * @Author : ant
//    * @Create Date : 2016. 3. 28.
//    */
//    @Override
//    public int insertLoginHistory(LoginHistoryDto loginHistoryDto) {
//        return fCommonDao.insertLoginHistory(loginHistoryDto);
//    }
//
//    @Override
//    public MspSmsTemplateMstDto getMspSmsTemplateMst(int templateId){
//        return fCommonDao.getMspSmsTemplateMst(templateId);
//    }
//
//    @Override
//    public int checkCrawlingCount(McpIpStatisticDto mcpIpStatisticDto) {
//        return fCommonDao.checkCrawlingCount(mcpIpStatisticDto);
//    }
//
//    @Override
//    public List<McpIpStatisticDto> getRateResChgList(McpIpStatisticDto ipStatistic) {
//        return fCommonDao.getRateResChgList(ipStatistic);
//    }
//
//
//    @Override
//    public boolean updateNmcpRateResChgBas(McpIpStatisticDto ipStatistic) {
//        return fCommonDao.updateNmcpRateResChgBas(ipStatistic);
//    }
//
//    @Override
//    public void prdMnpCmpnList() {
//
//
//        if ("LOCAL".equals(serverName)) {
//            logger.info("[WOO][WOO][WOO][LOCAL]===>BREAK!!");
//            return;
//        }
//
//        CommCdInfoRes commCdInfoRes = mplatFormService.moscCommCdInfo("NP_COMM_CMPN");
//
//        //CD[KIS],CD_DSCR[케이티엠모바일]
//        if (commCdInfoRes.isSuccess() ) {
//            List<CdInfoDto> cdList = commCdInfoRes.getCdList();
//
//            // 초기화
//            //commCodeDAO.updateMnpCmpnListInit();
//            if (cdList!=null && cdList.size() > 0) {
//                AtomicInteger atomIndex = new AtomicInteger(0);
//
//                cdList.forEach(cdInfoDto -> {
//
//                    //DTL_CD   ===> 번호이동 사업자 코드 (유일한 KEY값)    ==> <rfrnVal1>SMT</rfrnVal1
//                    //EXPNSN_STR_VAL1  => 번호이동 셀프 개통 코드  ==>  <cd>K99</cd>/
//
//                           /*
//                           <cd>K99</cd> ==>  EXPNSN_STR_VAL1
//                            <cdDscr>(주)포인트파크</cdDscr>    <rfrnVal1>PNT</rfrnVal1>===> CD
//                            <rfrnVal2>2022-06-20</rfrnVal2></cdList>
//                            */
//
//                    logger.info("CD[" + cdInfoDto.getCd() + "],CD_DSCR["+ cdInfoDto.getCdDscr() +"],RFRN_VAL1["+ cdInfoDto.getRfrnVal1() +"]");
//                    NmcpCdDtlDto cdDtlDto = new NmcpCdDtlDto();
//                    cdDtlDto.setDtlCd(cdInfoDto.getRfrnVal1());
//                    cdDtlDto.setDtlCdNm(cdInfoDto.getCdDscr());
//                    cdDtlDto.setExpnsnStrVal1(cdInfoDto.getCd());  //cdInfoDto.getCd()
//                    cdDtlDto.setIndcOdrg(atomIndex.getAndIncrement()+ 100);
//
//                    //정보 UPDATE OR INSERT
//                    commCodeDAO.updateMnpCmpn(cdDtlDto);
//
//                });
//            }
//
//        }
//    }
//
//    @Override
//    public Map<String, Object> sendAuthSms2(AuthSmsDto authSmsDto) throws ParseException {
//
//        Map<String, Object> reMap = new HashMap<>();
//        RestTemplate restTemplate = new RestTemplate();
//
//        List<NmcpCdDtlDto> nmcpCdDtlDtos  = NmcpServiceUtils.getCodeList("SMSRESTRICT");
//        int adMin = Integer.parseInt(nmcpCdDtlDtos.stream().filter(dtlCd -> dtlCd.getDtlCd().equals("smsMin")).findFirst().get().getExpnsnStrVal1());
//        int adCount = Integer.parseInt(nmcpCdDtlDtos.stream().filter(dtlCd -> dtlCd.getDtlCd().equals("smsCnt")).findFirst().get().getExpnsnStrVal1());
//
//        AuthSmsDto valueAuthDto = new AuthSmsDto();
//        valueAuthDto.setPhoneNum(authSmsDto.getPhoneNum());
//        valueAuthDto.setLimitMin(String.valueOf(adMin));
//        logger.info("전화번호 : " + authSmsDto.getPhoneNum());
//        logger.info("제한 분 : " + String.valueOf(adMin));
//
//        //문자 신규모듈 운영서버 체크
//        if("DEV".equals(serverName) || "LOCAL".equals(serverName) || "STG".equals(serverName)){
//            valueAuthDto.setIsReal("N");
//        }else{
//            valueAuthDto.setIsReal("Y");
//        }
//
//        //int stackCount = restTemplate.postForObject(apiServer + "/sms/qStackCnt", valueAuthDto, int.class);
//        int stackCount = restTemplate.postForObject(apiServer + "/sms/qStackNewCnt", valueAuthDto, int.class);
//        logger.info("제한 수 : " + stackCount);
//        if( adCount <= stackCount) {
//            reMap.put("result", false);
//            reMap.put("message", "문자 발송이 이미 완료 되었습니다.<br/>수신된 문자를 확인 부탁 드립니다."); // msg 변경 필요
//            return reMap;
//        }
//
//        reMap.put("result", true);
//
//        //6자리 인증번호 생성
//        StringBuffer authSmsNo = new StringBuffer();
//
//        Random objRandom;
//        try {
//            objRandom = SecureRandom.getInstance("SHA1PRNG");
//        } catch (NoSuchAlgorithmException e) {
//            throw new McpErropPageException(COMMON_EXCEPTION);
//        }
//
//        for(int i=0 ; i<6 ; i++) {
//            authSmsNo.append(objRandom.nextInt(10)) ;
//        }
//
//        //SMS 인증번호 발송
//        StringBuffer message = new StringBuffer();
//        message.append("인증번호는 ").append(authSmsNo).append(" 입니다.");
//        try {
//            //smsSvc.sendSmsForAuth(authSmsDto.getPhoneNum(),false,message.toString());
//            smsSvc.sendSmsForAuth(authSmsDto.getPhoneNum(),false,message.toString(),authSmsDto.getReserved02(),authSmsDto.getReserved03());
//        } catch(Exception e) {
//            reMap.put("result", false);
////            return false;
//        }
//
//        authSmsDto.setAuthNum(authSmsNo.toString());
//        String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
//        authSmsDto.setStartDate(today);
//        //관리자 정보 session 저장
//        SessionUtils.setAuthSmsSession(authSmsDto);
//
//        return reMap;
//    }
//
//    /**
//     * <pre>
//     * 설명 : 상세코드명으로 공통코드 조회
//     * @param cdGroupId
//     * @param dtlCdNm
//     * @return NmcpCdDtlDto
//     * </pre>
//     */
//    @Override
//    public NmcpCdDtlDto getDtlCodeWithNm(String cdGroupId, String dtlCdNm) {
//        NmcpCdDtlDto paramDto= new NmcpCdDtlDto();
//        paramDto.setCdGroupId(cdGroupId);
//        paramDto.setDtlCdNm(dtlCdNm);
//
//        return fCommonDao.getDtlCodeWithNm(paramDto);
//    }
//
//    /**
//     * <pre>
//     * 설명 : 상세코드 조회 (사용여부/기간 조건없이 조회)
//     * @param cdGroupId
//     * @return List<NmcpCdDtlDto>
//     * </pre>
//     */
//    @Override
//    public List<NmcpCdDtlDto> getAllDtlCdList(String cdGroupId) {
//        return fCommonDao.getAllDtlCdList(cdGroupId);
//    }
//
//    /**
//     * <pre>
//     * 설명 : 서비스 중단 시간 공통코드 사용하여 서비스 이용가능 여부 확인
//     * @param serviceName
//     * @return Map<String, String>
//     * </pre>
//     */
//    @Override
//    public Map<String, String> checkServiceAvailable(String serviceName) throws McpCommonException {
//        Map<String, String> checkMap = new HashMap<>();
//        checkMap.put("code", "0000");
//        checkMap.put("message", "이용가능");
//
//        // 1. 서비스 중단 시간 조회
//        try {
//            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(SERVICE_DOWNTIME, serviceName);
//            if (nmcpCdDtlDto == null) {
//                return checkMap;
//            }
//            String strtDttm = nmcpCdDtlDto.getExpnsnStrVal1();
//            String endDttm = nmcpCdDtlDto.getExpnsnStrVal2();
//            String message = nmcpCdDtlDto.getExpnsnStrVal3();
//
//            // 2. 현재 서비스 중단 시간이면 alertMessage 구성 후 throw
//            if (isServiceDowntime(strtDttm, endDttm)) {
//                try {
//                    throw new McpCommonJsonException("9999", getServiceDownTimeMessage(strtDttm, endDttm, message));
//                } catch (ParseException e) {
//                    logger.info(e.getMessage());
//                }
//            }
//        } catch (McpCommonJsonException e) {
//            checkMap.put("code", e.getRtnCode());
//            checkMap.put("message", e.getErrorMsg());
//        }
//
//        return checkMap;
//    }
//
//    private static boolean isServiceDowntime(String strtDttm, String endDttm){
//        boolean isDownTime = false;
//
//        if (!DateTimeUtil.isValid(strtDttm, "yyyyMMddHHmmss") || !DateTimeUtil.isValid(endDttm, "yyyyMMddHHmmss")) {
//            return false;
//        }
//
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//            LocalDateTime strtDate = LocalDateTime.parse(strtDttm, formatter);
//            LocalDateTime endDate = LocalDateTime.parse(endDttm, formatter);
//            isDownTime = strtDate.isBefore(endDate) && DateTimeUtil.isMiddleDateTime2(strtDttm, endDttm);
//        } catch (ParseException e){
//            logger.info(e.getMessage());
//        }
//
//        return isDownTime;
//    }
//
//    private static String getServiceDownTimeMessage(String strtDttm, String endDttm, String message) throws ParseException {
//        String alertStrtDttm = DateTimeUtil.changeFormat(strtDttm, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm");
//        String alertEndDttm = DateTimeUtil.changeFormat(endDttm, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm");
//        return message.replaceAll("#\\{start\\}", alertStrtDttm).replaceAll("#\\{end\\}", alertEndDttm);
//    }
//
//    @Override
//    public NmcpCdDtlDto getJehuProdInfo(String rateCd) {
//        MspRateMstDto mspRateMst = mspService.getMspRateMst(rateCd);
//        if (mspRateMst == null || mspRateMst.getJehuProdType() == null) {
//            return new NmcpCdDtlDto();
//        }
//
//        List<NmcpCdDtlDto> jehuProdDtoList = NmcpServiceUtils.getCodeList("JehuProdType");
//        if (jehuProdDtoList == null){
//            return new NmcpCdDtlDto();
//        }
//
//        return jehuProdDtoList.stream()
//                .filter(jehuProdDto -> mspRateMst.getJehuProdType().equals(jehuProdDto.getDtlCd()))
//                .findAny()
//                .orElseGet(NmcpCdDtlDto::new);
//    }
//
//    @Override
//    public NmcpCdDtlDto getJehuPartnerInfo(String pCntpntShopId) {
//        List<NmcpCdDtlDto> jehuPartnerDtoList = NmcpServiceUtils.getCodeList("PartnerTerm");
//        if (jehuPartnerDtoList == null || StringUtils.isEmpty(pCntpntShopId)) {
//            return new NmcpCdDtlDto();
//        }
//
//        for (NmcpCdDtlDto nmcpCdDtlDto : jehuPartnerDtoList){
//            if (pCntpntShopId.equals(nmcpCdDtlDto.getDtlCd())) {
//                return nmcpCdDtlDto;
//            }
//        }
//
//        return new NmcpCdDtlDto();
//    }
//
//    @Override
//    public NmcpCdDtlDto getJehuPartnerInfo() {
//        List<NmcpCdDtlDto> jehuPartnerDtoList = NmcpServiceUtils.getCodeList("PartnerTerm");
//        if (jehuPartnerDtoList == null || StringUtils.isEmpty(SessionUtils.getCoalitionInflow())) {
//            return new NmcpCdDtlDto();
//        }
//
//        for (NmcpCdDtlDto nmcpCdDtlDto : jehuPartnerDtoList){
//            if (SessionUtils.getCoalitionInflow().equals(nmcpCdDtlDto.getExpnsnStrVal1())) {
//                return nmcpCdDtlDto;
//            }
//        }
//
//        return new NmcpCdDtlDto();
//    }
//
//    @Override
//    public boolean insertUserEventTrace(UserEventTraceDto userEventTraceDto) {
//        userEventTraceDto.setRip(ipstatisticService.getClientIp());
//        return fCommonDao.insertUserEventTrace(userEventTraceDto);
//    }
//
//    @Override
//    public boolean updateUserEventTrace(UserEventTraceDto userEventTraceDto) {
//        return fCommonDao.updateUserEventTrace(userEventTraceDto);
//    }
//
//    @Override
//    public PopupEditorDto getPopupEditor(String popupSeq) {
//        String device = "PC";
//        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
//            device = "MO";
//        }
//        PopupEditorDto popupEditorDto = new PopupEditorDto();
//        popupEditorDto.setPopupSeq(popupSeq);
//        popupEditorDto.setDevice(device);
//
//        return fCommonDao.getPopupEditor(popupEditorDto);
//    }
//
//    @Override
//    public int selectPageViewsCount(String url) {
//        return fCommonDao.selectPageViewsCount(url);
//    }
//
//    @Override
//    public void getRateAdsvcGdncVersionCache() {
//        String version = fCommonDao.getLastedRateAdsvcGdncVersion();
//        if(dbCacheHandler.getElement(CACHE_RATE_ADSVC_GDNC_VERSION) == null) {
//            dbCacheHandler.put(CACHE_RATE_ADSVC_GDNC_VERSION, version);
//        } else {
//            dbCacheHandler.replace(CACHE_RATE_ADSVC_GDNC_VERSION, version);
//        }
//    }
//}
//
//
