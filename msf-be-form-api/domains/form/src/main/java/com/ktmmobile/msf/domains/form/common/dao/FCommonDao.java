/**
 *
 */
package com.ktmmobile.msf.domains.form.common.dao;

import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.domains.form.common.dto.AcesAlwdDto;
import com.ktmmobile.msf.domains.form.common.dto.BannerDto;
import com.ktmmobile.msf.domains.form.common.dto.BannerFloatDto;
import com.ktmmobile.msf.domains.form.common.dto.BannerTextDto;
import com.ktmmobile.msf.domains.form.common.dto.CdGroupBean;
import com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.domains.form.common.dto.MspCommDatPrvTxnDto;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.dto.SiteMenuDto;
import com.ktmmobile.msf.domains.form.common.dto.WorkNotiDto;


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
    int insertIpStat(McpIpStatisticDto mcpIpStatisticDto);

    List<CdGroupBean> getCodeAllList() ;

    /**
     * <pre>
     * 설명     : 공통코드 조회 한다.
     * @param nmcpCdDtlDto
     * @return
     * @return: List<NmcpCdDtlDto>
     * </pre>
     */
    List<NmcpCdDtlDto> getCodeList(NmcpCdDtlDto nmcpCdDtlDto) ;


    /**
     * <pre>
     * 설명     : 공통명 조회
     * @param nmcpCdDtlDto
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    NmcpCdDtlDto getCodeNm(NmcpCdDtlDto nmcpCdDtlDto) ;


    /**
     * <pre>
     * 설명     : 요금제 정보 조회
     * @param rateCd
     * @return
     * @return: MspRateMstDto
     * </pre>
     */
    MspRateMstDto getMspRateMst(String rateCd) ;

    /**
     * <pre>
     * 설명     : 통신자료제공내역신청 등록
     * @param
     * @return
     * @return: List<PopupDto>
     * </pre>
     */
    boolean insertmspCommDatPrvTxn(MspCommDatPrvTxnDto mspCommDatPrvTxnDto);

    int insertIpStatAdmin(McpIpStatisticDto mcpIpStatisticDto) ;

    List<BannerDto> getBannerAllList();

    List<BannerDto> getBannerApdList();

    List<SiteMenuDto> getMenuAllList();

    List<WorkNotiDto> getMenuUrlAllList();

    List<SiteMenuDto> getMenuAuthList();

    List<AcesAlwdDto> getAcesAlwdList();

    List<BannerTextDto> getBannerTextList();

    List<BannerFloatDto> getBannerFloatList();

    int deleteRateResChgAccessTrace(String rateResChgSeq);

    String selectRateResChgAccessTrace(McpIpStatisticDto mcpIpStatisticDto);

    /**
     * recaptcha 로그 기록
     * @param recaptchaLogMap
     */
    int insertRecaptchaLog(Map<String, String> recaptchaLogMap);

    /**
     * <pre>
     * 설명     : 상세코드명으로 공통코드 조회
     * @param  nmcpCdDtlDto
     * @return NmcpCdDtlDto
     * </pre>
     */
    NmcpCdDtlDto getDtlCodeWithNm(NmcpCdDtlDto nmcpCdDtlDto);

    /**
     * <pre>
     * 설명 : 상세코드 조회 (사용여부/기간 조건없이 조회)
     * @param cdGroupId
     * @return List<NmcpCdDtlDto>
     * </pre>
     */
    List<NmcpCdDtlDto> getAllDtlCdList(String cdGroupId);

}
