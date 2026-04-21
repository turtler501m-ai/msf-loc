/**
 *
 */
package com.ktmmobile.msf.domains.form.common.dao;

import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.domains.form.common.dto.*;
import com.ktmmobile.msf.domains.form.common.dto.MspCommDatPrvTxnDto;
import com.ktmmobile.msf.domains.form.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;


/**
 * @author ANT_FX700_02
 *
 */
public interface FCommonDao {
    /**
    * @Description : MCP IP 저장 테이블에 저장한다.
    * @param mcpIpStatisticDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 12.
    */
    public int insertIpStat(McpIpStatisticDto mcpIpStatisticDto);

    public List<CdGroupBean> getCodeAllList() ;

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
     * 설명     : 공통명 조회
     * @param nmcpCdDtlDto
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    public NmcpCdDtlDto getCodeNm(NmcpCdDtlDto nmcpCdDtlDto) ;


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
     * @param PopupDto
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
    public PopupDto getPopupDetail(PopupDto popupDto) ;

    /**
     * <pre>
     * 설명     : 팝업 리스트 조회 메인PC
     * @param
     * @return
     * @return: List<PopupDto>
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
     * 설명     : 이력정보 조회
     * @param McpIpStatisticDto
     * @param
     * @return
     * @return: McpIpStatisticDto
     * </pre>
     */
    public List<McpIpStatisticDto> getAdminAccessTrace(McpIpStatisticDto mcpIpStatisticDto) ;

    public int insertIpStatAdmin(McpIpStatisticDto mcpIpStatisticDto) ;

    public List<BannerDto> getBannerAllList();

    public List<BannerDto> getBannerApdList();

    public List<PopupDto> getPopupAllList();

    public List<SiteMenuDto> getMenuAllList();

    public List<WorkNotiDto> getMenuUrlAllList();

    public List<SiteMenuDto> getMenuAuthList();

    public List<AcesAlwdDto> getAcesAlwdList();

    public List<BannerTextDto> getBannerTextList();

    public List<BannerFloatDto> getBannerFloatList();

    public int insertRateResChgAccessTrace(McpIpStatisticDto mcpIpStatisticDto);

    public int deleteRateResChgAccessTrace(String rateResChgSeq);

    public String selectRateResChgAccessTrace(McpIpStatisticDto mcpIpStatisticDto);



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
     * recaptcha 로그 기록
     * @param recaptchaLogMap
     */
    public int insertRecaptchaLog(Map<String, String> recaptchaLogMap);

    /**
     * <pre>
     * 설명     : 상세코드명으로 공통코드 조회
     * @param  nmcpCdDtlDto
     * @return NmcpCdDtlDto
     * </pre>
     */
    public NmcpCdDtlDto getDtlCodeWithNm(NmcpCdDtlDto nmcpCdDtlDto);

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

    /**
     * <pre>
     * 설명     :고객이 특정  이벤트 정보  조회
     * </pre>
     */
    public List<UserEventTraceDto> getUserEventTraceList(UserEventTraceDto userEventTraceDto);


    PopupEditorDto getPopupEditor(PopupEditorDto popupEditorDto);

    /*조회 수 증가*/
    public void updatePageViewCount(McpIpStatisticDto mcpIpStatisticDto);

    public int selectPageViewsCount(String url);

    String getLastedRateAdsvcGdncVersion();
}
