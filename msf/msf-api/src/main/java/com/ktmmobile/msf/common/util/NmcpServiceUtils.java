package com.ktmmobile.msf.common.util;

import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_ACESALWD;
import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_BANNER;
import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_BANNERAPD;
import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_BANNERFLOAT;
import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_BANNERTEXT;
import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_CODE;
import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_MENU;
import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_MENUAUTH;
import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_POPUP;
import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_RATE_ADSVC_GDNC_VERSION;
import static com.ktmmobile.msf.common.constants.CacheConstants.CACHE_WORKNOTI;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;
import com.ktmmobile.msf.common.cache.DbCacheHandler;
import com.ktmmobile.msf.common.dto.AcesAlwdDto;
import com.ktmmobile.msf.common.dto.BannerDto;
import com.ktmmobile.msf.common.dto.BannerFloatDto;
import com.ktmmobile.msf.common.dto.BannerTextDto;
import com.ktmmobile.msf.common.dto.PopupDto;
import com.ktmmobile.msf.common.dto.SiteMenuDto;
import com.ktmmobile.msf.common.dto.UserSessionDto;
import com.ktmmobile.msf.common.dto.WorkNotiDto;
import com.ktmmobile.msf.common.dto.db.BannAccessTxnDto;
import com.ktmmobile.msf.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.common.exception.McpCommonException;
import com.ktmmobile.msf.common.service.BannerStatService;
import com.ktmmobile.msf.common.service.FCommonSvc;

public class NmcpServiceUtils {

    private static final Logger logger = LoggerFactory.getLogger(NmcpServiceUtils.class);

    public final static String MNP800_GROUP_ID  		= "MNP800"; // MNP800 그룹 ID
    public final static String MNP800_DTL_CD      		= "01"; // MNP800 상세 CD

     /**
     * <pre>
     * 설명     : 그룹 코드에 대한 상세코드 정보를 가져온다.
     * @param param
     * @return
     * </pre>
     */
    public static List<NmcpCdDtlDto> getCodeList(String grpCd) {
        List<NmcpCdDtlDto> codeList = null;
        List<NmcpCdDtlDto> codeRstList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        Map<String, List<NmcpCdDtlDto>> codeMap = (Map<String, List<NmcpCdDtlDto>>) dbCacheHandler.getValue(CACHE_CODE);
        if (codeMap != null) {
            codeList = codeMap.get(grpCd);
            codeRstList = new ArrayList<NmcpCdDtlDto>();

            if(codeList !=null && !codeList.isEmpty()) {
                for(NmcpCdDtlDto bean : codeList) {
                    if(DateTimeUtil.checkValidDate(bean.getPstngStartDate(), bean.getPstngEndDate())) {
                        codeRstList.add(bean);
                    }
                }
            }
        }
        return codeRstList;
    }

    public static List<SiteMenuDto> getMenuList(String menuOutputCd) {
        List<SiteMenuDto> menuList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        List<SiteMenuDto> codeMap = (List<SiteMenuDto>) dbCacheHandler.getValue(CACHE_MENU);
        if (codeMap != null) {
            menuList = new ArrayList<SiteMenuDto>();
            for(SiteMenuDto menu : codeMap) {
                if(DateTimeUtil.checkValidDate(menu.getPstngStartDate(), menu.getPstngEndDate())) {
                    if("".equals(menuOutputCd)) {
                        menuList.add(menu);
                    } else {
                        if(menu.getMenuOutputCd().trim().equals(menuOutputCd)) {
                            if("Y".equals(menu.getStatVal())) {
                                menuList.add(menu);
                            }
                        }
                    }
                }
            }
        }
        return menuList;
    }

    /**
    * <pre>
    * 설명     : 모바일, 앱 메뉴리스트 가져온다.
    * @param menuOutputCd 메뉴코드
    * @param mobileMenuUseYn 모바일 메뉴 사용여부
    * @param appMenuUseYn 앱 메뉴 사용여부
    * @return
    * </pre>
    */
    public static List<SiteMenuDto> getMobileMenuList(String menuOutputCd, String mobileMenuUseYn , String appMenuUseYn) {
        List<SiteMenuDto> menuList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);
        String platFormCd = "";

        @SuppressWarnings("unchecked")
        List<SiteMenuDto> codeMap = (List<SiteMenuDto>) dbCacheHandler.getValue(CACHE_MENU);
        if (codeMap != null) {
            menuList = new ArrayList<SiteMenuDto>();
            for(SiteMenuDto menu : codeMap) {
                if(DateTimeUtil.checkValidDate(menu.getPstngStartDate(), menu.getPstngEndDate())) {
                    if("".equals(menuOutputCd)) {
                        menuList.add(menu);
                    } else {
                        if(menu.getMenuOutputCd().trim().equals(menuOutputCd)) {
                            platFormCd = getPlatFormCd();
                            if("Y".equals(menu.getStatVal())) {
                                if("M".equals(platFormCd) && menu.getMobileMenuUseYn().equals(mobileMenuUseYn)) {
                                    menuList.add(menu);
                                }else if("A".equals(platFormCd) && menu.getAppMenuUseYn().equals(appMenuUseYn)) {
                                    menuList.add(menu);
                                }
                            }
                        }
                    }
                }
            }
        }
        return menuList;
    }

    public static List<SiteMenuDto> getMenuDepthList(String menuOutputCd, int depth) {
        List<SiteMenuDto> menuList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        if("".equals(menuOutputCd)) {
            return menuList;
        }

        @SuppressWarnings("unchecked")
        List<SiteMenuDto> codeMap = (List<SiteMenuDto>) dbCacheHandler.getValue(CACHE_MENU);
        if (codeMap != null) {
            menuList = new ArrayList<SiteMenuDto>();
            for(SiteMenuDto menu : codeMap) {
                if(DateTimeUtil.checkValidDate(menu.getPstngStartDate(), menu.getPstngEndDate())) {
                    if(menu.getMenuOutputCd().trim().equals(menuOutputCd) && menu.getDepthKey() == depth  ) {
                        if("Y".equals(menu.getStatVal())) {
                            menuList.add(menu);
                        }
                    }
                }
            }
        }
        return menuList;
    }

    public static boolean getMenuAuthList(String chkUrl, String authGrade) {

        boolean retVal = false;

        List<SiteMenuDto> menuList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        List<SiteMenuDto> codeMap = (List<SiteMenuDto>) dbCacheHandler.getValue(CACHE_MENUAUTH);
        if (codeMap != null) {
            menuList = new ArrayList<SiteMenuDto>();
            for(SiteMenuDto menu : codeMap) {
                if(DateTimeUtil.checkValidDate(menu.getPstngStartDate(), menu.getPstngEndDate())) {
                    // admin에서 메뉴링크코드를 빈메뉴로 등록하는 경우 urlAdr는 null
                    if(menu.getUrlAdr() != null && menu.getUrlAdr().trim().equals(chkUrl)) {
                        menuList.add(menu);
                        if(menu.getAutGradeCd().equals(authGrade)) {
                            retVal = true;
                        }
                    }
                }
            }
        }

        if(menuList.size() == 0) {
            retVal = true;
        }

        return retVal;
    }

    public static List<BannerDto> getBannerList(String bannCtg) {
        List<BannerDto> bannerList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        List<BannerDto> codeMap = (List<BannerDto>) dbCacheHandler.getValue(CACHE_BANNER);
        if (codeMap != null) {
            bannerList = new ArrayList<BannerDto>();
            for(BannerDto banner : codeMap) {
                if (DateTimeUtil.checkValidDate(banner.getPstngStartDateSec(), banner.getPstngEndDateSec())) {
                    banner.setBannImg(banner.getBannImgSec());
                    banner.setImgDesc(banner.getImgDescSec());
                    banner.setBgColor(banner.getBgColorSec());
                    banner.setMobileBannImgNm(banner.getMobileBannImgNmSec());
                    banner.setPstngStartDate(banner.getPstngStartDateSec());
                    banner.setPstngEndDate(banner.getPstngEndDateSec());
                }

                if(DateTimeUtil.checkValidDate(banner.getPstngStartDate(), banner.getPstngEndDate())) {
                    if("".equals(bannCtg)) {
                        bannerList.add(banner);
                    } else {
                        if(banner.getBannCtg().trim().equals(bannCtg)) {
                            bannerList.add(banner);
                        }
                    }
                }
            }
        }

        try {

            BannerStatService bannerStatService = getBean(BannerStatService.class);
            BannAccessTxnDto bannAccessTxnDto = new BannAccessTxnDto();
            SiteMenuDto curmenu = SessionUtils.getCurrentMenuDto();

            if(curmenu != null) {

                bannAccessTxnDto.setBannSeq(0);
                bannAccessTxnDto.setPlatformCd(curmenu.getPlatformCd());
                bannAccessTxnDto.setBannCtg(bannCtg);
                bannAccessTxnDto.setMenuSeq(0);
                bannAccessTxnDto.setAccessIp("");
                bannAccessTxnDto.setUrlSeq(0);

                UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
                String userId = "";
                if (userSessionDto != null) {
                    userId = userSessionDto.getUserId();
                }
                bannAccessTxnDto.setUserId(userId);
                bannAccessTxnDto.setReqTrtCd("LOAD");
                bannerStatService.insertBannAccessTxn(bannAccessTxnDto);
            }

        } catch(DataAccessException e) {
            logger.error("Exception e : {}", e.getMessage());
        } catch(Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }

        return bannerList;
   }

    public static List<BannerDto> getBannerApdList(String bannSeq) {
        List<BannerDto> bannerApdList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        List<BannerDto> codeMap = (List<BannerDto>) dbCacheHandler.getValue(CACHE_BANNERAPD);
        if (codeMap != null) {
            bannerApdList = new ArrayList<BannerDto>();
            for(BannerDto banner : codeMap) {
                if(DateTimeUtil.checkValidDate(banner.getPstngStartDate(), banner.getPstngEndDate())) {
                    if("".equals(bannSeq)) {
                        bannerApdList.add(banner);
                    } else {
                        int iBannSeq = Integer.parseInt(bannSeq);
                        if(banner.getBannSeq() == iBannSeq) {
                            bannerApdList.add(banner);
                        }
                    }
                }
            }
        }
        return bannerApdList;
   }

    public static List<PopupDto> getPopupList(String popupShowUrl) {
        List<PopupDto> popupList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);
        boolean isOnlyGet = false;
        String device = "PC";
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            device = "MO";
        }

        @SuppressWarnings("unchecked")
        List<PopupDto> codeMap = (List<PopupDto>) dbCacheHandler.getValue(CACHE_POPUP);
        if (codeMap != null) {
            popupList = new ArrayList<PopupDto>();

            for(PopupDto popup : codeMap) {
                if (!device.equals(popup.getDevice())) {
                    continue;
                }

                if(DateTimeUtil.checkValidDate(popup.getPstngStartDate(), popup.getPstngEndDate())) {
                    // 일회성 팝업인 경우
                    if("O".equals(popup.getUsageType()) && !isOnlyGet ) {
                        String oneTimePopupGrp = popup.getOneTimePopupGrp();
                        // 공통코드 조회
                        List<NmcpCdDtlDto> urlList = NmcpServiceUtils.getCodeList(oneTimePopupGrp);
                        if(urlList.size() > 0 ) {
                            // 일회성그룹 공통코드의 상세코드설명에 존재 시 popupList에 추가
                            for(NmcpCdDtlDto nmcpCdDtlDto : urlList) {
                                if( -1 < popupShowUrl.indexOf(nmcpCdDtlDto.getExpnsnStrVal1().trim())) {
                                    //일회성 팝업이 존재하는 경우의 URL이면 팝업이 일회성팝업만 노출되어야 하기때문에 popupList를 클리어
                                    //popupList.clear();

                                    // 세션이 없는경우 (일회성 팝업 첫 노출을 위해 popupList에 넣고 세션에 값 넣어 다음엔 노출되지 않도록)
                                    if(!oneTimePopupGrp.equals(SessionUtils.getOneTimePopup())) {
                                        popupList.add(popup);
                                        SessionUtils.saveOneTimePopup(oneTimePopupGrp);
                                    }
                                    isOnlyGet = true;  // 죄초 한개만.   저장 ...  by papier
                                    break ;
                                }
                            }
                        }
                    } else {
                        if("".equals(popupShowUrl)) {
                            popupList.add(popup);
                        } else {
                            if(popup.getPopupShowUrl().trim().equals(popupShowUrl)) {
                                popupList.add(popup);
                            }
                        }
                    }
                }
            }
        }
        return popupList;
    }

    public static List<WorkNotiDto> getWorkNotiList(String cdChkY) {
        List<WorkNotiDto> workNotiList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        List<WorkNotiDto> codeMap = (List<WorkNotiDto>) dbCacheHandler.getValue(CACHE_WORKNOTI);
        if (codeMap != null) {
            //workNoti (작업공지) / mustLogin(로그인필수여부)
            if("workNoti".equals(cdChkY)) {
                workNotiList = new ArrayList<WorkNotiDto>();
                for(WorkNotiDto noti : codeMap) {
                    if("Y".equals(StringUtil.NVL(noti.getSysWorkNotiRegYn(),""))) {
                        workNotiList.add(noti);
                    }
                }
            } else if("mustLogin".equals(cdChkY)) {
                workNotiList = new ArrayList<WorkNotiDto>();
                for(WorkNotiDto noti : codeMap) {
                    if("Y".equals(StringUtil.NVL(noti.getLoginMustYn(),""))) {
                        workNotiList.add(noti);
                    }
                }
            } else {
                workNotiList = codeMap;
            }
        }
        return workNotiList;
    }

    public static List<WorkNotiDto> getMenuInfoList() {
        List<WorkNotiDto> workNotiList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        String chkUrl = "";

        SiteMenuDto curmenu = SessionUtils.getCurrentMenuDto();
        if(curmenu != null) {
            chkUrl = curmenu.getUrlAdr();
        }

        if(!"".equals(StringUtil.NVL(chkUrl, ""))) {
            workNotiList = new ArrayList<WorkNotiDto>();

            @SuppressWarnings("unchecked")
            List<WorkNotiDto> codeMap = (List<WorkNotiDto>) dbCacheHandler.getValue(CACHE_WORKNOTI);
            if (codeMap != null) {
                for(WorkNotiDto noti : codeMap) {
                    if(chkUrl.equals(StringUtil.NVL(noti.getUrl(),""))) {
                        workNotiList.add(noti);
                    }
                }
            }
        }
        return workNotiList;
    }

    public static boolean getAcesAlwdList(String accessIp) {

        boolean retVal = false;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        if(!"".equals(StringUtil.NVL(accessIp, ""))) {

            @SuppressWarnings("unchecked")
            List<AcesAlwdDto> codeMap = (List<AcesAlwdDto>) dbCacheHandler.getValue(CACHE_ACESALWD);
            if (codeMap != null) {
                for(AcesAlwdDto noti : codeMap) {
                    if(accessIp.equals(StringUtil.NVL(noti.getAcesAlwdIp(),""))) {
                        retVal = true;
                        break;
                    }
                }
            }

        }
        return retVal;
    }

    public static List<BannerTextDto> getBannerTextList(String bannTxtType) {
        List<BannerTextDto> bannerTextList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        List<BannerTextDto> codeMap = (List<BannerTextDto>) dbCacheHandler.getValue(CACHE_BANNERTEXT);
        if (codeMap != null) {
            bannerTextList = new ArrayList<BannerTextDto>();
            for(BannerTextDto bannerText : codeMap) {
                if(DateTimeUtil.checkValidDate(bannerText.getBannPstngStartDate(), bannerText.getBannPstngEndDate())
                    && DateTimeUtil.checkValidDate(bannerText.getTxtPstngStartDate(), bannerText.getTxtPstngEndDate())) {
                    if("".equals(bannTxtType)) {
                        bannerTextList.add(bannerText);
                    } else {
                        if(bannTxtType.equals(bannerText.getBannTxtType())) {
                            bannerTextList.add(bannerText);
                        }
                    }
                }
            }
        }
        return bannerTextList;
    }

    public static List<BannerFloatDto> getBannerFloatList(int ntcartSeq) {
        List<BannerFloatDto> bannerFloatList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        List<BannerFloatDto> codeMap = (List<BannerFloatDto>) dbCacheHandler.getValue(CACHE_BANNERFLOAT);
        if (codeMap != null) {
            bannerFloatList = new ArrayList<BannerFloatDto>();
            for(BannerFloatDto bannerFloat : codeMap) {
                if(DateTimeUtil.checkValidDate(bannerFloat.getPstngStartDate(), bannerFloat.getPstngEndDate())) {
                    if(bannerFloat.getNtcartSeq() == ntcartSeq) {
                        bannerFloatList.add(bannerFloat);
                    }
                }
            }
        }
        return bannerFloatList;
    }

    /**
     * <pre>
     * 설명     :  코드명을 조회
     * @param grpCd 그룹코드
     * @param dtlCd 상세코드
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    public static String getCodeNm(String grpCd, String dtlCd) {
        List<NmcpCdDtlDto> codeList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        Map<String, List<NmcpCdDtlDto>> codeMap = (Map<String, List<NmcpCdDtlDto>>) dbCacheHandler.getValue(CACHE_CODE);
        if (codeMap != null) {
            codeList = codeMap.get(grpCd);
            if (codeList != null) {
                for (NmcpCdDtlDto bean : codeList) {
                    if (bean.getDtlCd().equals(dtlCd)) {
                        return bean.getDtlCdNm();
                    }
                }
            }
        }
        return "";
    }

    /**
     * <pre>
     * 설명     :  코드명을 조회
     * @param grpCd 그룹코드
     * @param dtlCd 상세코드
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    public static NmcpCdDtlDto getCodeNmDto(String grpCd, String dtlCd) {

        NmcpCdDtlDto result = null;

        List<NmcpCdDtlDto> codeList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        Map<String, List<NmcpCdDtlDto>> codeMap = (Map<String, List<NmcpCdDtlDto>>) dbCacheHandler.getValue(CACHE_CODE);
        if (codeMap != null) {
            codeList = codeMap.get(grpCd);
            if (codeList != null) {
                for (NmcpCdDtlDto bean : codeList) {
                    if (bean.getDtlCd().equals(dtlCd)) {
                        return bean;
                    }
                }
            }
        }
        return null;
    }

    /**
     * <pre>
     * 설명     :  코드명을 조회
     * @param grpCd 그룹코드
     * @param dtlCd 상세코드
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    public static NmcpCdDtlDto getCodeNmObj(NmcpCdDtlDto nmcpCdDtlDto) {
        List<NmcpCdDtlDto> codeList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        Map<String, List<NmcpCdDtlDto>> codeMap = (Map<String, List<NmcpCdDtlDto>>) dbCacheHandler.getValue(CACHE_CODE);
        if (codeMap != null) {
            codeList = codeMap.get(nmcpCdDtlDto.getCdGroupId());
            if (codeList != null) {
                for (NmcpCdDtlDto bean : codeList) {
                    if (bean.getDtlCd().equals(nmcpCdDtlDto.getDtlCd())) {
                        return bean;
                    }
                }
            }
        }
        return null;
    }

    /** 상세 코드명으로 공통코드 상세 조회 (중복 시 정렬순서가 가장 빠른 값 리턴) */
    public static NmcpCdDtlDto getCodeByDtlCdNm(String grpCd, String dtlCdNm) {

        if(StringUtil.isEmpty(dtlCdNm)){
            return null;
        }

        List<NmcpCdDtlDto> codeList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        Map<String, List<NmcpCdDtlDto>> codeMap = (Map<String, List<NmcpCdDtlDto>>) dbCacheHandler.getValue(CACHE_CODE);
        if (codeMap != null) {
            codeList = codeMap.get(grpCd);
            if (codeList != null) {
                for (NmcpCdDtlDto bean : codeList) {
                    if (DateTimeUtil.checkValidDate(bean.getPstngStartDate(), bean.getPstngEndDate()) &&
                        dtlCdNm.equals(bean.getDtlCdNm())) {
                        return bean;
                    }
                }
            }
        }
        return null;
    }

    /**
     * <pre>
     * 설명     :  번호변경 수수료 DB
     * @param grpCd 그룹코드
     * @param dtlCd 상세코드
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    public static String getFtranPrice() {
        String rtnVal = "800"  ;
        String tmp = getCodeNm(NmcpServiceUtils.MNP800_GROUP_ID, NmcpServiceUtils.MNP800_DTL_CD);
        if( tmp != null) {
            rtnVal = tmp;
        }
        return rtnVal;
    }

    public static <T> T getBean( Class<T> calssType) {
        WebApplicationContext applicationContext = ContextLoaderListener.getCurrentWebApplicationContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(applicationContext.getServletContext(),FrameworkServlet.SERVLET_CONTEXT_PREFIX +"appServlet");
        return context.getBean(calssType);
    }


    /**
     * <pre>
     * 설명     :  가입비 , USIM 밑줄 처리 하는 접점 코드 확인
     * </pre>
     */
    public static Boolean isContainsTrick(String strCntpnt) {
        return com.ktmmobile.msf.common.constants.Constants.MNG_CNTPNT_STRIKE.contains(strCntpnt);
    }

    /**
     * <pre>
     * 설명     : 주민등록번호로 고객의 나이 알아내기
     * @param custNo
     * @param toDay
     * @return
     * </pre>
     */
    public static int getAge(String custNo, String toDay) {

        int age = 0;

        if (custNo.equals("") || custNo.length() < 7) {
            return age;
        } else {
            // *********** ctn 사용자 나이 계산
            int toYear = Integer.parseInt(toDay.substring(0, 4)); // 현재 년도
            int toMonth = Integer.parseInt(toDay.substring(4, 6)); // 현재 월
            int toDate = Integer.parseInt(toDay.substring(6, 8)); // 현재 날짜

            int birYear = Integer.parseInt(custNo.substring(0, 2)); // 생일 년
            int birMonth = Integer.parseInt(custNo.substring(2, 4)); // 생일 월
            int birDate = Integer.parseInt(custNo.substring(4, 6)); // 생일 날짜

            @SuppressWarnings("unused")
            int toAgeMonth = 0; // 나이 월
            @SuppressWarnings("unused")
            int toAgeDate = 0; // 나이 날짜

            // 월의 마지막 날짜 계산
            String yyyy = toDay.substring(0, 4);
            String mm = toDay.substring(4, 6);
            String dd = "";
            if (mm.equals("01") || mm.equals("03") || mm.equals("05") || mm.equals("07") || mm.equals("08") || mm.equals("10") || mm.equals("12")) {
                dd = "31";
            } else if (mm.equals("02")) {
                if ((Float.parseFloat(yyyy) / 4) == (Integer.parseInt(yyyy) / 4)) {
                    if ((Float.parseFloat(yyyy) / 100) == (Integer.parseInt(yyyy) / 100)) {
                        if ((Float.parseFloat(yyyy) / 400) == (Integer.parseInt(yyyy) / 400)) {
                            dd = "29";
                        } else {
                            dd = "28";
                        }
                    } else {
                        dd = "29";
                    }
                } else {
                    dd = "28";
                }
            } else {
                dd = "30";
            }
            int intDd = Integer.parseInt(dd);

            // 년도 붙이기
            if (Integer.parseInt(custNo.substring(6, 7)) == 1 || Integer.parseInt(custNo.substring(6, 7)) == 2 || Integer.parseInt(custNo.substring(6, 7)) == 5 || Integer.parseInt(custNo.substring(6, 7)) == 6) {
                birYear = 1900 + birYear;
            } else if (Integer.parseInt(custNo.substring(6, 7)) == 3 || Integer.parseInt(custNo.substring(6, 7)) == 4 || Integer.parseInt(custNo.substring(6, 7)) == 7 || Integer.parseInt(custNo.substring(6, 7)) == 8) {
                birYear = 2000 + birYear;
            }

            // 나이 날짜 계산
            if (toDate < birDate) {
                toAgeDate = (toDate + intDd) - birDate;
                toMonth = toMonth - 1;
            } else {
                toAgeDate = toDate - birDate;
            }
            // 나이 월 계산
            if (toMonth < birMonth) {
                toAgeMonth = (toMonth + 12) - birMonth;
                toYear = toYear - 1;
            } else {
                toAgeMonth = toMonth - birMonth;
            }
            // 나이 년도 계산
            age = toYear - birYear;
        }

        return age;
    }

    /**
     * <pre>
     * 설명     : 생년월일 고객의 나이 알아내기
     * @param custNo
     * @param toDay
     * @return
     * </pre>
     */
    public static int getBirthDateToAge(String birthDate, String toDay) {

        int age = 0;

        if (birthDate.equals("") || birthDate.length() != 8) {
            return age;
        } else {
            // *********** ctn 사용자 나이 계산
            int toYear = Integer.parseInt(toDay.substring(0, 4)); // 현재 년도
            int toMonth = Integer.parseInt(toDay.substring(4, 6)); // 현재 월
            int toDate = Integer.parseInt(toDay.substring(6, 8)); // 현재 날짜

            int birYear = Integer.parseInt(birthDate.substring(0, 4)); // 생일 년
            int birMonth = Integer.parseInt(birthDate.substring(4, 6)); // 생일 월
            int birDate = Integer.parseInt(birthDate.substring(6, 8)); // 생일 날짜


            // 나이 날짜 계산
            if (toDate < birDate) {
                toMonth = toMonth - 1;
            }
            // 나이 월 계산
            if (toMonth < birMonth) {
                toYear = toYear - 1;
            //} else {

            }
            // 나이 년도 계산
            age = toYear - birYear;
        }

        return age;
    }

    /**
     * <pre>
     * 설명     : 년도가 있는 전체 주민번호
     * @param custNo
     * @return
     * </pre>
     */
    public static String getSsnDate(String custNo) {

        String rtnStr = custNo;

        if (custNo.equals("") || custNo.length() < 7) {
            return rtnStr;
        } else {
            // 년도 붙이기
            /*
             * 5 : 1900년대6: 1900 년대
                7, 8 :2000년대
             */
            if ( Integer.parseInt(custNo.substring(6, 7)) == 1 || Integer.parseInt(custNo.substring(6, 7)) == 2 || Integer.parseInt(custNo.substring(6, 7)) == 5 || Integer.parseInt(custNo.substring(6, 7)) == 6 ) {
                rtnStr = "19" + custNo.substring(0, 6);  //
            } else if (Integer.parseInt(custNo.substring(6, 7)) == 3 || Integer.parseInt(custNo.substring(6, 7)) == 4 || Integer.parseInt(custNo.substring(6, 7)) == 7 || Integer.parseInt(custNo.substring(6, 7)) == 8 ) {
                rtnStr = "20" + custNo.substring(0, 6);
            } else {
                rtnStr = "19" + custNo.substring(0, 6);  //
            }
        }

        return rtnStr;
    }

    /**
     * Device 구분 : 모바일 or PC or APP구분
     * @param
     */
    public static String getDeviceType() {
        //[모바일 or PC 구별하기]
        String filter = "iphone|ipod|android|windows ce|blackberry|symbian|windows phone|webos|opera mini|opera mobi|polaris|iemobile|lgtelecom|nokia|sonyericsson|lg|samsung";
        String filters[] = filter.split("\\|");
        String rtnDeviceType = "WEB";

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            if(request.getHeader("User-Agent") != null){
                if( request.getHeader("User-Agent").indexOf("M-Mobile-App") != -1) {
                    rtnDeviceType = "APP";
                }else{
                    for(String tmp : filters){
                        if ( request.getHeader("User-Agent").toLowerCase().indexOf(tmp) != -1) {
                            rtnDeviceType = "MOB";
                            break;
                        } else {
                            rtnDeviceType = "WEB";
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }
        return rtnDeviceType;
    }

    /**
     * Device 구분 : APP or WEB
     * 기능 추가: P:PC, M:Mobile, A:APP
     * @param
     */
    public static String getPlatFormCd() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String curUri = request.getRequestURI().toString();
        String chkParam = StringUtil.NVL(request.getParameter("PCTURN"),"");

        String rtnPlatFormCd = "P";

        try {
            if("APP".equals(NmcpServiceUtils.getDeviceType())){
                rtnPlatFormCd = "A";
            } else if("MOB".equals(NmcpServiceUtils.getDeviceType())){
                rtnPlatFormCd = "M";
            } else {
                rtnPlatFormCd = "P";
            }
        } catch(McpCommonException e) {
            logger.error("McpCommonException e : {}", e.getMessage());
        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        //pc/mobile 보기
        if(!"".equals(chkParam)) {
            if("M".equals(chkParam)) {
                rtnPlatFormCd = "M";
            } else if("A".equals(chkParam)) {
                rtnPlatFormCd = "A";
            } else {
                rtnPlatFormCd = "P";
            }
        }
        return rtnPlatFormCd;
    }

    public static String getPhoneOs() {

        String rtnPhoneOs = "O"; // Android, IOS 이외의 진입.

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            if(request.getHeader("User-Agent") != null){
                if ( request.getHeader("User-Agent").toLowerCase().indexOf("android") != -1) {
                    rtnPhoneOs = "A";
                } else if ( request.getHeader("User-Agent").toLowerCase().indexOf("iphone") != -1
                            || request.getHeader("User-Agent").toLowerCase().indexOf("ipad") != -1) {
                    rtnPhoneOs = "I";
                }
            }
        } catch(IllegalStateException e ) {
            throw new McpCommonException(COMMON_EXCEPTION);
        } catch(Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }
        return rtnPhoneOs;
    }


    public static String getPhoneOsVerForOcr() {

        String rtnOCRchk = "N";

        String rtnPhoneOs = "O";

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            if(request.getHeader("User-Agent") != null){
                if ( request.getHeader("User-Agent").toLowerCase().indexOf("android") != -1) {
                    rtnPhoneOs = "A";
                } else if ( request.getHeader("User-Agent").toLowerCase().indexOf("iphone") != -1
                            || request.getHeader("User-Agent").toLowerCase().indexOf("ipad") != -1) {
                    rtnPhoneOs = "I";
                }
            }

            if(!"O".equals(rtnPhoneOs)) {
                int iv1 = 0;
                int iv2 = 0;

                if("A".equals(rtnPhoneOs)) {
                    // 안드로이드는 OCR 가능
                    rtnOCRchk = "Y";
                } else { // I
                    String uagent = request.getHeader("User-Agent");
                    String[] appOsVer = uagent.split(" ");
                    String[] iOsVer = {"0","0"};

                    for(String ss : appOsVer) {
                        if(ss.contains("appOsVer")) {
                            String[] ver = ss.split("=");
                            if(ver.length > 1) {
                                iOsVer = ver[1].split("[.]");
                                if(iOsVer.length > 1) {
                                    iv1 = Integer.parseInt(StringUtil.NVL(iOsVer[0],"0"));
                                    iv2 = Integer.parseInt(StringUtil.NVL(iOsVer[1],"0"));
                                } else if(iOsVer.length == 1) {
                                    iv1 = Integer.parseInt(StringUtil.NVL(iOsVer[0],"0"));
                                }
                                break;
                            }
                        }
                    }
                    // IOS 는 os version 13.5 이상 OCR 가능
                    if(iv1 > 13) {
                        rtnOCRchk = "Y";
                    } else if(iv1 == 13) {
                        if(iv2 >= 5) {
                            rtnOCRchk = "Y";
                        }
                    }
                }
            }

        } catch(NumberFormatException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }
        return rtnOCRchk;
    }

    /**
     * URL access 구분 : PC WEB or Mobile WEB, 모바일 URL 인 경우 'Y'
     * @param
     */
    public static String isMobile() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String chkParam = StringUtil.NVL(request.getParameter("PCTURN"),"");

        String curUri = request.getRequestURI().toString();

        String retStr = "N";
        if(curUri != null && curUri.toLowerCase().contains("/m/")) {
            retStr = "Y";
        }

        //pc/mobile 보기
        if(!"".equals(chkParam)) {
            if("M".equals(chkParam)) {
                retStr = "Y";
            } else if("A".equals(chkParam)) {
                retStr = "Y";
            } else {
                retStr = "N";
            }
        }

        return retStr;
       }


    public static String getPropertiesVal(String id) {
        String resource = "/configuration/properties/common.properties";
        Properties properties = new Properties();
        Reader reader = null;
        String retStr = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            properties.load(reader);
            retStr = properties.getProperty(id);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return retStr;
    }

    /**
     * 설명     : 생년월일 고객의 만 나이 알아내기
     * @param custNo
     * @param toDay
     * @return
     * </pre>
     */
    public static int getBirthDateToAmericanAge(String birthDate, String toDay) {

        int age = 0;

        if (birthDate.equals("") || birthDate.length() != 8) {
            return age;
        } else {
            // *********** ctn 사용자 나이 계산
            int toYear = Integer.parseInt(toDay.substring(0, 4)); // 현재 년도
            int toMonth = Integer.parseInt(toDay.substring(4, 6)); // 현재 월
            int toDate = Integer.parseInt(toDay.substring(6, 8)); // 현재 날짜

            int birYear = Integer.parseInt(birthDate.substring(0, 4)); // 생일 년
            int birMonth = Integer.parseInt(birthDate.substring(4, 6)); // 생일 월
            int birDate = Integer.parseInt(birthDate.substring(6, 8)); // 생일 날짜


            // 나이 날짜 계산
            if (toDate < birDate) {
                toMonth = toMonth - 1;
            }
            // 나이 월 계산
            if (toMonth < birMonth) {
                toYear = toYear - 1;
            //} else {

            }
            // 나이 년도 계산
            age = toYear - birYear;
        }

        return age;
    }

    public static List<NmcpCdDtlDto> getSimpleAuthList(String controlYn, String reqAuth) {

        String grpCd= null;
        if("P".equals(NmcpServiceUtils.getPlatFormCd())) grpCd= "pSimpleAuth";
        else grpCd= "mSimpleAuth";

        FCommonSvc fCommonSvc= getBean(FCommonSvc.class);
        List<NmcpCdDtlDto> simpleAuthList= fCommonSvc.getAllDtlCdList(grpCd);

        if(simpleAuthList == null || simpleAuthList.size() == 0){
            return simpleAuthList;
        }

        // controlYn : 공통코드에 의해 제어(Y), reqAuth에 해당하는 본인인증 수단 전체표출(N)
        List<NmcpCdDtlDto> rtnAuthList= new ArrayList<>();
        if("Y".equalsIgnoreCase(controlYn)){

            for(NmcpCdDtlDto cdDtlDto : simpleAuthList){
                if("N".equalsIgnoreCase(cdDtlDto.getUseYn())) continue;
                if(!DateTimeUtil.checkValidDate(cdDtlDto.getPstngStartDate(), cdDtlDto.getPstngEndDate())) continue;
                rtnAuthList.add(cdDtlDto);
            }

        }else{
            if(!StringUtil.isEmpty(reqAuth)){
                for(NmcpCdDtlDto cdDtlDto : simpleAuthList){
                    if(reqAuth.indexOf(cdDtlDto.getDtlCd()) > -1) rtnAuthList.add(cdDtlDto);
                }
            }
        }

        return rtnAuthList;
    }


    /**
     * <pre>
     * 설명     :  상세코드설명 조회
     * @param grpCd 그룹코드
     * @param dtlCd 상세코드
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    public static String getCodeDesc(String grpCd, String dtlCd) {
        List<NmcpCdDtlDto> codeList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        Map<String, List<NmcpCdDtlDto>> codeMap = (Map<String, List<NmcpCdDtlDto>>) dbCacheHandler.getValue(CACHE_CODE);
        if (codeMap != null) {
            codeList = codeMap.get(grpCd);
            if (codeList != null) {
                for (NmcpCdDtlDto bean : codeList) {
                    if (bean.getDtlCd().equals(dtlCd)) {
                        return bean.getCdGroupDesc();
                    }
                }
            }
        }
        return "";
    }

    public static String getLatestRateAdsvcGdncVersion() {
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        String version = (String) dbCacheHandler.getValue(CACHE_RATE_ADSVC_GDNC_VERSION);
        if (version == null) {
            return "";
        }
        return version;
    }

}
