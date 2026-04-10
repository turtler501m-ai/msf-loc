package com.ktmmobile.mcp.common.service;

import com.ktmmobile.mcp.common.dto.*;
import com.ktmmobile.mcp.common.dto.db.MspCommDatPrvTxnDto;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspRateMstDto;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : FCommonSvc.java
 * 날짜     : 2016. 1. 25. 오후 5:11:45
 * 작성자   : papier
 * 설명     : 공용 @Service
 * </pre>
 */
public interface FCommonSvc {

    /**
     * <pre>
     * 설명     : 공통코드 Cahe 처리
     * @param nmcpCdDtlDto
     * @return
     * @return: List<NmcpCdDtlDto>
     * </pre>
     */
    void getCodeCahe();

    void getPopupCahe();

    void getMenuCahe();

    void getMenuAuthCahe();

    void getBannerCahe();

    void getBannerApdCahe();

    void getMenuUrlNotiCahe();

    void getAcesAlwdCahe();

    void getBannerTextCahe();

    void getBannerFloatCahe();

    /**
     * <pre>
     * 설명     : 공통코드 조회 한다.
     * @param nmcpCdDtlDto
     * @return
     * @return: List<NmcpCdDtlDto>
     * </pre>
     */
    public List<NmcpCdDtlDto> getCodeList(NmcpCdDtlDto nmcpCdDtlDto) ;



    /**
     * <pre>
     * 설명     : 코드명 조회
     * @param nmcpCdDtlDto
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    public NmcpCdDtlDto getCodeNm(NmcpCdDtlDto nmcpCdDtlDto) ;


    /**
     * <pre>
     * 설명 : 공통 인증 SMS 발송
     * @param phone
     */
    public boolean sendAuthSms(AuthSmsDto authSmsDto);

    /**
     * <pre>
     * 설명     : 요금제 정보 조회
     * @param rateCd
     * @return
     * @return: MspRateMstDto
     * </pre>
     */
    public MspRateMstDto getMspRateMst(String rateCd) ;

    /**
     * <pre>
     * 설명     : 팝업 리스트 조회
     * @param menuCode
     * @return
     * @return: List<PopupDto>
     * </pre>
     */
    public List<PopupDto> getPopupList(PopupDto popupDto) ;

    /**
     * <pre>
     * 설명     : 팝업 상세 조회
     * @param popupSeq
     * @return
     * @return: PopupDto
     * </pre>
     */
    public PopupDto getPopupDetail(String popupSeq) ;


    /**
     * <pre>
     * 설명     : 팝업 리스트 조회 메인PC
     * @param string
     * @param
     * @return
     * @return: PopupDto
     * </pre>
     */
    public List<PopupDto> getPopupMainList(String menuCode);


    /**
     * <pre>
     * 설명     : 통신자료제공내역신청 등록
     * @param
     * @return
     * @return: List<PopupDto>
     * </pre>
     */
    public boolean insertmspCommDatPrvTxn(MspCommDatPrvTxnDto mspCommDatPrvTxnDto);

    /**
    * @Description : NMCP Login 정보 저장 테이블에 저장한다.
    * @param loginHistoryDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 3. 28.
    */
    public int insertLoginHistory(LoginHistoryDto loginHistoryDto);


    /**
     * <pre>
     * 설명     : SMS TEMPLATE 정보 조회
     * @param int
     * @param
     * @return
     * @return: MspSmsTemplateMstDto
     * </pre>
     */
    public MspSmsTemplateMstDto getMspSmsTemplateMst(int templateId);


    /**
     * <pre>
     * 설명     : 동일한 아이피로 페이지 호출 건수 조회
     * @param int
     * @param
     * @return
     * @return: McpIpStatisticDto
     * </pre>
     */
    public int checkCrawlingCount(McpIpStatisticDto mcpIpStatisticDto) ;



    /**
     * <pre>
     * 설명     : 요금제 예약번호 리스트 , 배치로 부가서비스 해지 등록 처리
     * @param McpIpStatisticDto
     * @return
     * @return: List<McpIpStatisticDto>
     * </pre>
     */
    public List<McpIpStatisticDto> getRateResChgList(McpIpStatisticDto ipStatistic) ;


    /**
     * <pre>
     * 설명     : 배치로 부가서비스 해지 등록 결과 업데이트
     * @param McpIpStatisticDto
     * @return
     * </pre>
     */
    public boolean updateNmcpRateResChgBas(McpIpStatisticDto ipStatistic);

    /**
     * <pre>
     * 설명     : 번호이동 사업자 조회 후 공통코드 업데이트 처리
     * @return
     * </pre>
     */
    public void prdMnpCmpnList() ;

    Map<String, Object> sendAuthSms2(AuthSmsDto authSmsDto) throws ParseException;

    /**
     * <pre>
     * 설명 : 상세코드명으로 공통코드 조회
     * @param cdGroupId
     * @param dtlCdNm
     * @return NmcpCdDtlDto
     * </pre>
     */
    NmcpCdDtlDto getDtlCodeWithNm(String cdGroupId, String dtlCdNm);

    /**
     * <pre>
     * 설명 : 상세코드 조회 (사용여부/기간 조건없이 조회)
     * @param cdGroupId
     * @return List<NmcpCdDtlDto>
     * </pre>
     */
    List<NmcpCdDtlDto> getAllDtlCdList(String cdGroupId);

    /**
     * <pre>
     * 설명 : 서비스 중단 시간 공통코드 사용하여 서비스 이용가능 여부 확인
     * @param serviceName
     * @return Map<String, String>
     * </pre>
     */
    Map<String, String> checkServiceAvailable(String serviceName);

    NmcpCdDtlDto getJehuProdInfo(String rateCd);

    NmcpCdDtlDto getJehuPartnerInfo(String pCntpntShopId);

    NmcpCdDtlDto getJehuPartnerInfo();

    /**
     * <pre>
     * 설명 : 고객이 특정  이벤트 정보 저장
     * </pre>
     */
    public boolean insertUserEventTrace(UserEventTraceDto userEventTraceDto) ;


    /**
     * <pre>
     * 설명     :고객이 특정  이벤트 정보  업데이트
     * </pre>
     */
    public boolean updateUserEventTrace(UserEventTraceDto userEventTraceDto);


    PopupEditorDto getPopupEditor(String popupSeq);

    /*특정 페이지 조회수 조회*/
    public int selectPageViewsCount(String url);

    void getRateAdsvcGdncVersionCache();
}
