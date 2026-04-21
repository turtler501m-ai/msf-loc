package com.ktmmobile.msf.domains.form.common.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.common.dto.ApiMapDto;
import com.ktmmobile.msf.domains.form.common.dto.AppformReqDto;
import com.ktmmobile.msf.domains.form.common.dto.AuthSmsDto;
import com.ktmmobile.msf.domains.form.common.dto.CommonSearchDto;
import com.ktmmobile.msf.domains.form.common.dto.IntmInsrRelDTO;
import com.ktmmobile.msf.domains.form.common.dto.JuoSubInfoDto;
import com.ktmmobile.msf.domains.form.common.dto.McpFarPriceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRegServiceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.domains.form.common.dto.MspSalePrdtMstDto;
import com.ktmmobile.msf.domains.form.common.dto.MspSaleSubsdMstDto;
import com.ktmmobile.msf.domains.form.common.dto.OrderDto;
import com.ktmmobile.msf.domains.form.common.dto.PhoneProdBasDto;
import com.ktmmobile.msf.domains.form.common.dto.RwdOrderDto;
import com.ktmmobile.msf.domains.form.common.dto.UsimMspRateDto;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.AJAX_SUCCESS;


/**
 * MCP DB 직접 조회 DAO.
 *
 * MspApiClient 내부에서 경로(path) 기반으로 자동 호출된다.
 * (mcp-api 연결 실패 또는 use-mcp=false 정책 전환 시)
 *
 */

@Repository
public class MspApiDirectRepository {

    private static final Logger logger = LoggerFactory.getLogger(MspApiDirectRepository.class);

    @Autowired
    @Qualifier("mspSqlSession")
    private SqlSessionTemplate mspSession;

    /**
     * 경로(path) 기반으로 MSP 직접 조회를 실행한다.
     *
     * @param path         mcp-api 엔드포인트 경로
     * @param request      원래 요청 파라미터
     * @param responseType 응답 타입
     * @return MSP 조회 결과 (매핑된 직접 조회 없으면 null)
     */

    @SuppressWarnings("unchecked")
    public <T> T query(String path, Object request, Class<T> responseType) {

        logger.debug("[MspApiDirectRepository] query: path={}, request={}", path, request);

        switch (path) {
            // [MSP/MCP] appformMapper.selectMarketRequest -- @RequestMapping("/appform/marketRequest") -- SELECT(MSP) -- ITO API 사용
            case "/appform/marketRequest":
                return (T) selectMarketRequest((AppformReqDto) request);
            // [MSP/MCP] appformMapper.selectModelMonthlyList -- @RequestMapping("/appform/modelMonthlyList") -- SELECT(MSP) -- ITO API 사용
            case "/appform/modelMonthlyList":
                return (T) selectModelMonthlyList((AppformReqDto) request);
            // [MSP/MCP] appformMapper.selectMonthlyListMarket -- @RequestMapping("/appform/monthlyListMarket") -- SELECT(MSP) -- ITO API 사용
            case "/appform/monthlyListMarket":
                return (T) selectMonthlyListMarket((AppformReqDto) request);
            // [MSP/MCP] appformMapper.selectPrdtColorList -- @RequestMapping("/appform/prdtColorList") -- SELECT(MSP) -- ITO API 사용
            case "/appform/prdtColorList":
                return (T) selectPrdtColorList((AppformReqDto) request);
            // [MSP/MCP] appformMapper.selectAgentCode -- @RequestMapping("/appform/agentInfoOjb") -- SELECT(MSP) -- ITO API 사용
            case "/appform/agentInfoOjb":
                return (T) selectAgentCode((String) request);
            // [MSP/MCP] appformMapper.selectSelRMemberAjax -- @RequestMapping("/appform/selRMemberAjax") -- SELECT(MSP) -- ITO API 사용
            case "/appform/selRMemberAjax":
                return (T) selectSelRMemberAjax((JuoSubInfoDto) request);
            // [MSP/MCP] appformMapper.checkValidUsimNo -- @RequestMapping("/appform/checkValidUsimNo") -- SELECT(MSP) -- ITO API 사용
            case "/appform/checkValidUsimNo":
                return (T) Integer.valueOf(checkValidUsimNo((String) request));
            // [MSP/MCP] appformMapper.selectInsrProdList -- @RequestMapping("/appform/selectInsrProdList") -- SELECT(MSP/MCP) -- ITO API 사용
            case "/appform/selectInsrProdList":
                return (T) selectInsrProdList((IntmInsrRelDTO) request);
            // [MSP/MCP] appformMapper.selectLimitForm -- @RequestMapping("/appform/limitForm") -- SELECT(MSP/MCP) -- ITO API 사용
            case "/appform/limitForm":
                return (T) selectLimitForm((AppformReqDto) request);
            // [MSP/MCP] appformMapper.checkLimitOpenFormCount -- @RequestMapping("/appform/checkLimitOpenFormCount") -- SELECT(MSP) -- ITO API 사용
            case "/appform/checkLimitOpenFormCount":
                return (T) Integer.valueOf(checkLimitOpenFormCount((AppformReqDto) request));
            // [MSP/MCP] appformMapper.getDisPrmtId -- @RequestMapping("/appform/getDisPrmtId") -- SELECT(MSP) -- ITO API 사용
            case "/appform/getDisPrmtId":
                return (T) selectDisPrmtId((AppformReqDto) request);
            // [MSP/MCP] appformMapper.selectMspPrdtCode -- @RequestMapping("/appform/mspPrdtCode") -- SELECT(MSP) -- ITO API 사용
            case "/appform/mspPrdtCode":
                return (T) selectMspPrdtCode((AppformReqDto) request);
            // [MSP/MCP] appformMapper.insertMcpRequestAdditionPromotion -- @RequestMapping("/appform/mcpRequestAdditionPromotion") -- SELECT(MSP/MCP)/INSERT(MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
            case "/appform/mcpRequestAdditionPromotion":
                return (T) Integer.valueOf(insertMcpRequestAdditionPromotion((AppformReqDto) request));
            // [MSP/MCP] appformMapper.existsAbuseImei -- @RequestMapping("/appform/existsAbuseImei") -- SELECT(MSP) -- ITO API 사용
            case "/appform/existsAbuseImei":
                return (T) Boolean.valueOf(existsAbuseImei((String) request));
            // [MSP/MCP] appformMapper.getCpntId -- @RequestMapping("/appform/getCpntId") -- SELECT(MSP) -- ITO API 사용
            case "/appform/getCpntId":
                return (T) selectCpntId((String) request);
            // [MSP/MCP] appformMapper.selectRequestJoinDataByRjoinKeyNew -- @RequestMapping("/appform/requestJoinDataByRjoinKeyNew") -- SELECT(MSP/MCP) -- ITO API 사용
            case "/appform/requestJoinDataByRjoinKeyNew":
                return (T) selectRequestJoinDataByRjoinKeyNew((Long) request);
            // [MSP/MCP] appformMapper.getChrgPrmtId -- @RequestMapping("/appform/getChrgPrmtId") -- SELECT(MSP/MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
            case "/appform/getChrgPrmtId":
                return (T) selectChrgPrmtId((AppformReqDto) request);
            // [MSP/MCP] appformMapper.insertDisPrmtApd -- @RequestMapping("/appform/insertDisPrmtApd") -- SELECT(MSP)/INSERT(MSP) -- ITO API 사용
            case "/appform/insertDisPrmtApd":
                return (T) Integer.valueOf(insertDisPrmtApd((AppformReqDto) request));
            // [MSP/MCP] commonMapper.selectMspRateMst -- @RequestMapping("/common/mspRateMst") -- SELECT(MSP) -- ITO API 사용
            case "/common/mspRateMst":
                return (T) selectMspRateMst((String) request);
            // [MSP/MCP] commonMapper.selectMspSmsTemplateMst -- @RequestMapping("/common/mspSmsTemplateMst") -- SELECT(MSP) -- ITO API 사용
            case "/common/mspSmsTemplateMst":
                return (T) selectMspSmsTemplateMst((Integer) request);
            // [MSP/MCP] mPlatformMapper.selectCheckMpCallCount -- @RequestMapping("/mPlatform/checkMpCallCount") -- SELECT(MCP) -- ITO API 사용
            case "/mPlatform/checkMpCallCount":
                return (T) selectCheckMpCallCount((Map<String, String>) request);
            // [MSP/MCP] mspMapper.listRateByOrgnInfos -- @RequestMapping("/msp/rateByOrgnInfos") -- SELECT(MSP) -- ITO API 사용
            case "/msp/rateByOrgnInfos":
                return (T) selectRateByOrgnInfos((MspSalePlcyMstDto) request);
            // [MSP/MCP] mspMapper.findMspSalePlcyInfoByOnlyOrgn -- @RequestMapping("/msp/mspSalePlcyInfoByOnlyOrgn") -- SELECT(MSP) -- ITO API 사용
            case "/msp/mspSalePlcyInfoByOnlyOrgn":
                return (T) selectMspSalePlcyInfoByOnlyOrgn((MspSalePlcyMstDto) request);
            // [MSP/MCP] mspMapper.getLowPriceChargeInfoByProd -- @RequestMapping("/msp/lowPriceChargeInfoByProd") -- SELECT(MSP) -- ITO API 사용
            case "/msp/lowPriceChargeInfoByProd":
                return (T) selectLowPriceChargeInfoByProd((MspSaleSubsdMstDto) request);
            // [MSP/MCP] mspMapper.findMspPhoneInfo -- @RequestMapping("/msp/mspPhoneInfo") -- SELECT(MSP) -- ITO API 사용
            case "/msp/mspPhoneInfo":
                return (T) selectMspPhoneInfo((String) request);
            // [MSP/MCP] mspMapper.findMspSaleOrgnMst -- @RequestMapping("/msp/mspSaleOrgnMst") -- SELECT(MSP) -- ITO API 사용
            case "/msp/mspSaleOrgnMst":
                return (T) selectMspSaleOrgnMst((MspSalePlcyMstDto) request);
            // [MSP/MCP] mspMapper.findMspSalePrdMst -- @RequestMapping("/msp/mspSalePrdMst") -- SELECT(MSP) -- ITO API 사용
            case "/msp/mspSalePrdMst":
                return (T) selectMspSalePrdMst((MspSalePrdtMstDto) request);
            // [MSP/MCP] mspMapper.listMspSaleAgrmMst -- @RequestMapping("/msp/mspSaleAgrmMst") -- SELECT(MSP) -- ITO API 사용
            case "/msp/mspSaleAgrmMst":
                return (T) selectMspSaleAgrmMst((String) request);
            // [MSP/MCP] mspMapper.getMspRateMst -- @RequestMapping("/msp/mspRateMst") -- SELECT(MSP) -- ITO API 사용
            case "/msp/mspRateMst":
                return (T) selectMspRateMst((String) request);
            // [MSP/MCP] mspMapper.sellUsimMgmtOrgnId -- @RequestMapping("/msp/sellUsimMgmtOrgnId") -- SELECT(MSP) -- ITO API 사용
            case "/msp/sellUsimMgmtOrgnId":
                return (T) selectSellUsimMgmtOrgnId((String) request);
            // [MSP/MCP] mspMapper.getMspSalePlcyMst -- @RequestMapping("/msp/mspSalePlcyMst") -- SELECT(MSP) -- ITO API 사용
            case "/msp/mspSalePlcyMst":
                return (T) selectMspSalePlcyMst((String) request);
            // [MSP/MCP] mspMapper.selectMspSaleSubsdMstListForLowPrice / mspMapper.selectMspSaleSubsdMstListWithRateInfo -- @RequestMapping("/msp/mspSaleSubsdMstList") -- SELECT(MSP) -- ITO API 사용
            case "/msp/mspSaleSubsdMstList":
                return (T) selectSaleSubsdMstList((MspSaleSubsdMstDto) request);
            // [MSP/MCP] mspMapper.juoSubIngoCount -- @RequestMapping("/msp/juoSubIngoCount") -- SELECT(MSP) -- ITO API 사용
            case "/msp/juoSubIngoCount":
                return (T) Integer.valueOf(selectJuoSubIngoCount((String) request));
            // [MSP/MCP] mspMapper.selectMspCombRateMapp -- @RequestMapping("/msp/mspCombRateMapp") -- SELECT(MSP) -- ITO API 사용
            case "/msp/mspCombRateMapp":
                return (T) selectMspCombRateMapp((String) request);
            // [MSP/MCP] mspMapper.getCustomerSsn -- @RequestMapping("/msp/customerSsn") -- SELECT(MSP) -- ITO API 사용
            case "/msp/customerSsn":
                return (T) selectCustomerSsn((String) request);
            // [MSP/MCP] mspMapper.getromotionDcAmt -- @RequestMapping("/msp/getromotionDcAmt") -- SELECT(MSP/MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
            case "/msp/getromotionDcAmt":
                return (T) selectPromotionDcAmt((MspSaleSubsdMstDto) request);
            // [MSP/MCP] MypageController.mspAddInfo() ? @RequestMapping("/mypage/mspAddInfo")-- SELECT(MSP) -- ITO API 사용
            case "/mypage/mspAddInfo":
                return (T) selectMspAddInfo((String) request);
            // [MSP/MCP] mypageMapper.insertRwdOrder -- @RequestMapping("/mypage/insertRwdOrder") -- INSERT(MSP) -- ITO API 사용
            case "/mypage/insertRwdOrder":
                return (T) insertRwdOrder((RwdOrderDto) request);
            // [MSP/MCP] mypageMapper.getOrgScanId -- @RequestMapping("/mypage/getOrgScanId") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/getOrgScanId":
                return (T) selectOrgScanId((String) request);
            // [MSP/MCP] mypageMapper.selectCntrList -- @RequestMapping("/mypage/cntrList") -- SELECT -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
            case "/mypage/cntrList":
                return (T) selectCntrList((HashMap<String, String>) request);
            // [MSP/MCP] mypageMapper.selectContractObj -- @RequestMapping("/mypage/selectContractObj") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/selectContractObj":
                return (T) selectContractObj((HashMap<String, String>) request);
            // [MSP/MCP] mypageMapper.selectJuoSubInfo -- @RequestMapping("/mypage/juoSubInfo") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/juoSubInfo":
                return (T) selectJuoSubInfo((HashMap<String, String>) request);
            // [MSP/MCP] mypageMapper.getChannelInfo -- @RequestMapping("/mypage/channelInfo") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/channelInfo":
                return (T) selectChannelInfo((String) request);
            // [MSP/MCP] mypageMapper.selectCntrListNmChg -- @RequestMapping("/mypage/cntrListNmChg") -- SELECT -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
            case "/mypage/cntrListNmChg":
                return (T) selectCntrListNmChg((HashMap<String, String>) request);
            // [MSP/MCP] mypageMapper.selectCntrListNoLogin -- @RequestMapping("/mypage/cntrListNoLogin") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/cntrListNoLogin":
                return (T) selectCntrListNoLogin((McpUserCntrMngDto) request);
            // [MSP/MCP] mypageMapper.selectSocDesc -- @RequestMapping("/mypage/socDesc") -- SELECT -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
            case "/mypage/socDesc":
                return (T) selectSocDesc((String) request);
            // [MSP/MCP] mypageMapper.selectCustomerType -- @RequestMapping("/mypage/customerType") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/customerType":
                return (T) selectCustomerType((String) request);
            // [MSP/MCP] mypageMapper.selectBanSel -- @RequestMapping("/mypage/selectBanSel") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/selectBanSel":
                return (T) selectBanSel((String) request);
            // [MSP/MCP] mypageMapper.selectConSsnObj -- @RequestMapping("/mypage/selectConSsnObj") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/selectConSsnObj":
                return (T) selectConSsnObj((HashMap<String, String>) request);
            // [MSP/MCP] mypageMapper.selectPrePayment -- @RequestMapping("/mypage/prePayment") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/prePayment":
                return (T) selectPrePayment((String) request);
            // [MSP/MCP] mypageMapper.getInsrInfo -- @RequestMapping("/mypage/getInsrInfo") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/getInsrInfo":
                return (T) selectInsrInfo((String) request);
            // [MSP/MCP] mypageMapper.getInsrInfoByCd -- @RequestMapping("/mypage/getInsrInfoByCd") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/getInsrInfoByCd":
                return (T) selectInsrInfoByCd((String) request);
            // [MSP/MCP] mypageMapper.selectFarPricePlan -- @RequestMapping("/mypage/farPricePlan") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/farPricePlan":
                return (T) selectFarPricePlan((String) request);
            // [MSP/MCP] mypageMapper.countFarPricePlanList -- @RequestMapping("/mypage/countFarPricePlanList") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/countFarPricePlanList":
                return (T) selectFarPricePlanListCount((String) request);
            // [MSP/MCP] mypageMapper.selectFarPricePlanList -- @RequestMapping("/mypage/farPricePlanList") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/farPricePlanList":
                return (T) selectFarPricePlanList((String) request);
            // [MSP/MCP] mypageMapper.getEnggInfo -- @RequestMapping("/mypage/enggInfo1") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/enggInfo1":
                return (T) selectEnggInfo((String) request);
            // [MSP/MCP] mypageMapper.selectFarPriceAddInfo -- @RequestMapping("/mypage/farPriceAddInfo") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/farPriceAddInfo":
                return (T) selectFarPriceAddInfo((Map<String, String>) request);
            // [MSP/MCP] mypageMapper.getCloseSubList -- @RequestMapping("/mypage/closeSubList") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/closeSubList":
                return (T) selectCloseSubList((String) request);
            // [MSP/MCP] mypageMapper.insertSocfailProcMst -- @RequestMapping("/mypage/insertSocfailProcMst") -- INSERT(MSP) -- ITO API 사용
            case "/mypage/insertSocfailProcMst":
                return (T) Boolean.valueOf(insertSocfailProcMst((McpServiceAlterTraceDto) request));
            // [MSP/MCP] mypageMapper.getChrgPrmtIdSocChg -- @RequestMapping("/mypage/getChrgPrmtIdSocChg") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/getChrgPrmtIdSocChg":
                return (T) selectChrgPrmtIdSocChg((String) request);
            // [MSP/MCP] mypageMapper.insertDisApd -- @RequestMapping("/mypage/insertDisApd") -- SELECT(MSP)/INSERT(MSP) -- ITO API 사용
            case "/mypage/insertDisApd":
                return (T) insertDisApd((McpUserCntrMngDto) request);
            // [MSP/MCP] mypageMapper.getromotionDcList -- @RequestMapping("/mypage/romotionDcList") -- SELECT(MSP/MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
            case "/mypage/romotionDcList":
                return (T) selectPromotionDcList((String) request);
            // [MSP/MCP] mypageMapper.getRateInfo -- @RequestMapping("/mypage/getRateInfo") -- SELECT(MSP/MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
            case "/mypage/getRateInfo":
                return (T) selectRateInfo((String) request);
            // [MSP/MCP] mypageMapper.selectRegService -- @RequestMapping("/mypage/regService") -- SELECT(MSP) -- ITO API 사용
            case "/mypage/regService":
                return (T) selectRegService((String) request);
            // [MSP/MCP] orderMapper.selectOrderGroupListCount -- @RequestMapping("/order/selectOrderGroupListCount") -- SELECT(MCP) -- ITO API 사용
            case "/order/selectOrderGroupListCount":
                return (T) selectOrderGroupListCount((OrderDto) request);
            // [MSP/MCP] orderMapper.selectOrderGroupList -- @RequestMapping("/order/selectOrderGroupList") -- SELECT(MCP) -- ITO API 사용
            case "/order/selectOrderGroupList":
                return (T) selectOrderGroupList((OrderDto) request);
            // [MSP/MCP] orderMapper.selectOrderTempListCount -- @RequestMapping("/order/selectOrderTempListCount") -- SELECT(MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
            case "/order/selectOrderTempListCount":
                return (T) selectOrderTempListCount((OrderDto) request);
            // [MSP/MCP] orderMapper.selectOrderTempList -- @RequestMapping("/order/selectOrderTempPageList") -- SELECT(MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
            case "/order/selectOrderTempPageList":
                return (T) selectOrderTempList((OrderDto) request);
            // [MSP/MCP] phoneMapper.listPhoneProdBasForFrontOneQuery -- @RequestMapping("/phone/phoneProdBasForFrontOneQuery") -- SELECT(MSP/MCP) -- ITO API 사용
            case "/phone/phoneProdBasForFrontOneQuery":
                return (T) selectPhoneProdBas((CommonSearchDto) request);
            // [MSP/MCP] phoneMapper.findNmcpProdBas -- @RequestMapping("/phone/nmcpProdBas") -- SELECT(MSP/MCP) -- ITO API 사용
            case "/phone/nmcpProdBas":
                return (T) selectNmcpProdBas((CommonSearchDto) request);
            // [MSP/MCP] prepiaMapper.getRateList -- @RequestMapping("/prepia/rateList") -- SELECT(MSP/MCP) -- ITO API 사용
            case "/prepia/rateList":
                return (T) selectRateList((Map<String, String>) request);
            // [MSP/MCP] smsMapper.insertKakaoNoti -- @RequestMapping("/sms/addKakaoNoti") -- INSERT(MSP) -- ITO API 사용
            case "/sms/addKakaoNoti":
                return (T) insertKakaoNoti((ApiMapDto) request);
            // [MSP/MCP] smsMapper.insertNewSms -- @RequestMapping("/sms/addNewSms") -- INSERT(MSP) -- ITO API 사용
            case "/sms/addNewSms":
                return (T) insertNewSms((ApiMapDto) request);
            // [MSP/MCP] smsMapper.selectQstackNewCount -- @RequestMapping("/sms/qStackNewCnt") -- INSERT(MSP) -- ITO API 사용
            case "/sms/qStackNewCnt":
                return (T) selectQstackNewCount((AuthSmsDto) request);
            // [MSP/MCP] storeUsimMapper.selectJoinUsimPriceNew -- @RequestMapping("/storeUsim/joinUsimPriceNew") -- SELECT(MSP) -- ITO API 사용
            case "/storeUsim/joinUsimPriceNew":
                return (T) selectJoinUsimPriceNew((String) request);
            // [MSP/MCP] storeUsimMapper.selectFailUsims -- @RequestMapping("/storeUsim/failUsim") -- SELECT(MCP) -- ITO API 사용
            case "/storeUsim/failUsim":
                return (T) selectFailUsims((String) request);
            // [MSP/MCP] storeUsimMapper.updateFailUsim -- @RequestMapping("/storeUsim/updateFailUsim") -- UPDATE(MCP) -- ITO API 사용
            case "/storeUsim/updateFailUsim":
                return (T) updateFailUsim((JuoSubInfoDto) request);
            // [MSP/MCP] storeUsimMapper.selectUsimDcamt -- @RequestMapping("/storeUsim/usimDcamt") -- SELECT(MSP) -- ITO API 사용
            case "/storeUsim/usimDcamt":
                return (T) selectUsimDcamt((String) request);
            default:
                logger.warn("[MspApiDirectRepository] 직접 조회 미구현 경로: {}", path);
                return null;
        }

    }

    // ──────────────────────────────────────────────
    //  직접 조회 메서드
    // ──────────────────────────────────────────────


    // [MSP/MCP] appformMapper.selectMarketRequest -- @RequestMapping("/appform/marketRequest") -- SELECT(MSP) -- ITO API 사용

    /** 오픈마켓 (외부서식지) -/appform/marketRequest */
    private AppformReqDto selectMarketRequest(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] selectMarketRequest: appformReqDto={}", appformReqDto);
        AppformReqDto result = mspSession.selectOne("McpAppformMapper.selectMarketRequest", appformReqDto);
        logger.debug("[MspApiDirectRepository] selectMarketRequest: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.selectModelMonthlyList -- @RequestMapping("/appform/modelMonthlyList") -- SELECT(MSP) -- ITO API 사용

    /** 할부개월 -/appform/modelMonthlyList */
    private List<AppformReqDto> selectModelMonthlyList(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] selectModelMonthlyList: appformReqDto={}", appformReqDto);
        List<AppformReqDto> result = mspSession.selectList("McpAppformMapper.selectModelMonthlyList", appformReqDto);
        logger.debug("[MspApiDirectRepository] selectModelMonthlyList: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.selectMonthlyListMarket -- @RequestMapping("/appform/monthlyListMarket") -- SELECT(MSP) -- ITO API 사용

    /** 약정 -/appform/monthlyListMarket */
    private List<AppformReqDto> selectMonthlyListMarket(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] selectMonthlyListMarket: appformReqDto={}", appformReqDto);
        List<AppformReqDto> result = mspSession.selectList("McpAppformMapper.selectMonthlyListMarket", appformReqDto);
        logger.debug("[MspApiDirectRepository] selectMonthlyListMarket: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.selectPrdtColorList -- @RequestMapping("/appform/prdtColorList") -- SELECT(MSP) -- ITO API 사용

    /** 약정 -/appform/prdtColorList */
    private List<AppformReqDto> selectPrdtColorList(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] selectPrdtColorList: appformReqDto={}", appformReqDto);
        List<AppformReqDto> result = mspSession.selectList("McpAppformMapper.selectPrdtColorList", appformReqDto);
        logger.debug("[MspApiDirectRepository] selectPrdtColorList: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.selectAgentCode -- @RequestMapping("/appform/agentInfoOjb") -- SELECT(MSP) -- ITO API 사용

    /** 대리점 코드 -/appform/agentInfoOjb */
    private Map<String, String> selectAgentCode(String cntpntShopId) {
        logger.debug("[MspApiDirectRepository] selectAgentCode: cntpntShopId={}", cntpntShopId);
        Map<String, String> result = mspSession.selectMap("McpAppformMapper.selectAgentCode", cntpntShopId);
        logger.debug("[MspApiDirectRepository] selectAgentCode: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.selectSelRMemberAjax -- @RequestMapping("/appform/selRMemberAjax") -- SELECT(MSP) -- ITO API 사용

    /** 기기변경 고객정보 확인 일반기변 , 우수기변 -/appform/selRMemberAjax */
    private JuoSubInfoDto selectSelRMemberAjax(JuoSubInfoDto juoSubInfoDto) {
        logger.debug("[MspApiDirectRepository] selectSelRMemberAjax: juoSubInfoDto={}", juoSubInfoDto);
        JuoSubInfoDto result = mspSession.selectOne("McpAppformMapper.selectSelRMemberAjax", juoSubInfoDto);
        logger.debug("[MspApiDirectRepository] selectSelRMemberAjax: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.checkValidUsimNo -- @RequestMapping("/appform/checkValidUsimNo") -- SELECT(MSP) -- ITO API 사용

    /** API설명 -/appform/checkValidUsimNo */
    private Integer checkValidUsimNo(String usimNo) {
        logger.debug("[MspApiDirectRepository] checkValidUsimNo: usimNo={}", usimNo);
        Integer result = mspSession.selectOne("McpAppformMapper.checkValidUsimNo", usimNo);
        logger.debug("[MspApiDirectRepository] checkValidUsimNo: result={}", result);
        return result != null ? result : 0;
    }

    // [MSP/MCP] appformMapper.selectInsrProdList -- @RequestMapping("/appform/selectInsrProdList") -- SELECT(MSP/MCP) -- ITO API 사용

    /** 분실파손 보험 조회 -/appform/selectInsrProdList */
    private List<IntmInsrRelDTO> selectInsrProdList(IntmInsrRelDTO intmInsrRelDTO) {
        logger.debug("[MspApiDirectRepository] selectInsrProdList: intmInsrRelDTO={}", intmInsrRelDTO);
        List<IntmInsrRelDTO> result = mspSession.selectList("McpAppformMapper.selectInsrProdList", intmInsrRelDTO);
        logger.debug("[MspApiDirectRepository] selectInsrProdList: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.selectLimitForm -- @RequestMapping("/appform/limitForm") -- SELECT(MSP/MCP) -- ITO API 사용

    /** 고객CI정보에 대한 개통  정보 추출 [다회선 제한 기능] -/appform/limitForm */
    private AppformReqDto selectLimitForm(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] selectLimitForm: appformReqDto={}", appformReqDto);
        AppformReqDto result = mspSession.selectOne("McpAppformMapper.selectLimitForm", appformReqDto);
        logger.debug("[MspApiDirectRepository] selectLimitForm: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.checkLimitOpenFormCount -- @RequestMapping("/appform/checkLimitOpenFormCount") -- SELECT(MSP) -- ITO API 사용

    /** 동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회 -/appform/checkLimitOpenFormCount */
    private Integer checkLimitOpenFormCount(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] checkLimitOpenFormCount: appformReqDto={}", appformReqDto);
        Integer result = mspSession.selectOne("McpAppformMapper.checkLimitOpenFormCount", appformReqDto);
        logger.debug("[MspApiDirectRepository] checkLimitOpenFormCount: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] appformMapper.getDisPrmtId -- @RequestMapping("/appform/getDisPrmtId") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getDisPrmtSlsTp -> selectDisPrmtSlsTp, getDisPrmtId -> selectDisPrmtId

    /** 가입유형 찾아오기 -/appform/getDisPrmtId */
    private String selectDisPrmtId(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] selectDisPrmtId: appformReqDto={}", appformReqDto);
        String slsTp = mspSession.selectOne("McpAppformMapper.selectDisPrmtSlsTp", appformReqDto);
        logger.debug("[MspApiDirectRepository] selectDisPrmtId: slsTp={}", slsTp);
        appformReqDto.setSlsTp(slsTp);

        // 오프라인 평생할인 프로모션 ID 조회
        List<String> prmtIdList = mspSession.selectList("McpAppformMapper.selectDisPrmtId", appformReqDto);
        if (prmtIdList == null || prmtIdList.isEmpty()) {
            //MM일때 프로모션 ID가 없다면, 프로모션 ID 한번 더 조회
            if ((!"".equals(appformReqDto.getModelId()) || appformReqDto.getModelId() != null) && "MM".equals(appformReqDto.getReqBuyType())) {
                String modelId = appformReqDto.getModelId();
                appformReqDto.setModelId(null);
                prmtIdList = mspSession.selectList("McpAppformMapper.selectDisPrmtId", appformReqDto);
                appformReqDto.setModelId(modelId);
            }
        }
        String result = "";
        if (prmtIdList != null && prmtIdList.size() != 0) {
            result = prmtIdList.getFirst();
        }
        logger.debug("[MspApiDirectRepository] selectDisPrmtId: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.selectMspPrdtCode -- @RequestMapping("/appform/mspPrdtCode") -- SELECT(MSP) -- ITO API 사용

    /** 서식지 관련 MSP 코드 조회 -/appform/mspPrdtCode */
    private com.ktmmobile.msf.domains.form.common.dto.McpRequestDto selectMspPrdtCode(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] selectMspPrdtCode: appformReqDto={}", appformReqDto);
        com.ktmmobile.msf.domains.form.common.dto.McpRequestDto result = mspSession.selectOne("McpAppformMapper.selectMspPrdtCode", appformReqDto);
        logger.debug("[MspApiDirectRepository] selectMspPrdtCode: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.insertMcpRequestAdditionPromotion -- @RequestMapping("/appform/mcpRequestAdditionPromotion") -- SELECT(MSP/MCP)/INSERT(MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용

    /** 부가서비스 프로모션 등록 -/appform/mcpRequestAdditionPromotion */
    private Integer insertMcpRequestAdditionPromotion(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] insertMcpRequestAdditionPromotion: appformReqDto={}", appformReqDto);
        Integer result = mspSession.insert("McpAppformMapper.insertMcpRequestAdditionPromotion", appformReqDto);
        logger.debug("[MspApiDirectRepository] insertMcpRequestAdditionPromotion: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] appformMapper.existsAbuseImei -- @RequestMapping("/appform/existsAbuseImei") -- SELECT(MSP) -- ITO API 사용

    /** API설명 -/appform/existsAbuseImei */
    private boolean existsAbuseImei(String imei) {
        logger.debug("[MspApiDirectRepository] existsAbuseImei: imei={}", imei);
        boolean result = mspSession.selectOne("McpAppformMapper.existsAbuseImei", imei);
        logger.debug("[MspApiDirectRepository] existsAbuseImei: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.getCpntId -- @RequestMapping("/appform/getCpntId") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getCpntId -> selectCpntId

    /** (대리점)안면인증 가능 접점ID조회 -/appform/getCpntId */
    private String selectCpntId(String cntpntShopId) {
        logger.debug("[MspApiDirectRepository] selectCpntId: cntpntShopId={}", cntpntShopId);
        String result = mspSession.selectOne("McpAppformMapper.selectCpntId", cntpntShopId);
        logger.debug("[MspApiDirectRepository] selectCpntId: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.selectRequestJoinDataByRjoinKeyNew -- @RequestMapping("/appform/requestJoinDataByRjoinKeyNew") -- SELECT(MSP/MCP) -- ITO API 사용

    /** 요금조회 -/appform/requestJoinDataByRjoinKeyNew */
    private Map<String, String> selectRequestJoinDataByRjoinKeyNew(long requestKey) {
        logger.debug("[MspApiDirectRepository] selectRequestJoinDataByRjoinKeyNew: requestKey={}", requestKey);
        Map<String, Object> result = mspSession.selectOne("McpAppformMapper.selectRequestJoinDataByRjoinKeyNew", requestKey);
        logger.debug("[MspApiDirectRepository] selectRequestJoinDataByRjoinKeyNew: result={}", result);

        if (result == null) {
            return null;
        }
        Map<String, String> newMap = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry: result.entrySet()) {
            newMap.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return newMap;
    }


    // [MSP/MCP] appformMapper.getChrgPrmtId -- @RequestMapping("/appform/getChrgPrmtId") -- SELECT(MSP/MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
    // SQL 메소드 변경 : getChrgPrmtId -> selectChrgPrmtId

    /** (셀프개통) 직영 프로모션 아이디 가져오기 -/appform/getChrgPrmtId */
    private String selectChrgPrmtId(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] selectChrgPrmtId: appformReqDto={}", appformReqDto);
        List<String> prmtIdList = mspSession.selectList("McpAppformMapper.selectChrgPrmtId", appformReqDto);
        String result = "";
        if (prmtIdList != null && prmtIdList.size() != 0) {
            result = prmtIdList.get(0);
        }
        logger.debug("[MspApiDirectRepository] selectChrgPrmtId: result={}", result);
        return result;
    }


    // [MSP/MCP] appformMapper.insertDisPrmtApd -- @RequestMapping("/appform/insertDisPrmtApd") -- SELECT(MSP)/INSERT(MSP) -- ITO API 사용

    /** 평생할인 프로모션 기적용 테이블 INSERT -/appform/insertDisPrmtApd */
    private Integer insertDisPrmtApd(AppformReqDto appformReqDto) {
        logger.debug("[MspApiDirectRepository] insertDisPrmtApd: appformReqDto={}", appformReqDto);
        Integer result = mspSession.insert("McpAppformMapper.insertDisPrmtApd", appformReqDto);
        logger.debug("[MspApiDirectRepository] insertDisPrmtApd: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] commonMapper.selectMspSmsTemplateMst -- @RequestMapping("/common/mspSmsTemplateMst") -- SELECT(MSP) -- ITO API 사용

    /** API설명 -/common/mspSmsTemplateMst */
    private com.ktmmobile.msf.domains.form.common.dto.MspSmsTemplateMstDto selectMspSmsTemplateMst(int templateId) {
        logger.debug("[MspApiDirectRepository] selectMspSmsTemplateMst: templateId={}", templateId);
        com.ktmmobile.msf.domains.form.common.dto.MspSmsTemplateMstDto result = mspSession.selectOne("McpCommonMapper.selectMspSmsTemplateMst",
            templateId);
        logger.debug("[MspApiDirectRepository] selectMspSmsTemplateMst: result={}", result);
        return result;
    }


    // [MSP/MCP] mPlatformMapper.selectCheckMpCallCount -- @RequestMapping("/mPlatform/checkMpCallCount") -- SELECT(MCP) -- ITO API 사용

    /** 당일 MP호출 수 -/mPlatform/checkMpCallCount */
    private Integer selectCheckMpCallCount(Map<String, String> paramMap) {
        logger.debug("[MspApiDirectRepository] selectCheckMpCallCount: paramMap={}", paramMap);
        Integer result = mspSession.selectOne("McpMplatformMapper.selectCheckMpCallCount", paramMap);
        logger.debug("[MspApiDirectRepository] selectCheckMpCallCount: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] mspMapper.listRateByOrgnInfos -- @RequestMapping("/msp/rateByOrgnInfos") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : listRateByOrgnInfos -> selectRateByOrgnInfos

    /** API설명 -/msp/rateByOrgnInfos */
    private List<MspRateMstDto> selectRateByOrgnInfos(MspSalePlcyMstDto mspSalePlcyMstDto) {
        logger.debug("[MspApiDirectRepository] selectRateByOrgnInfos: mspSalePlcyMstDto={}", mspSalePlcyMstDto);
        List<MspRateMstDto> result = mspSession.selectList("McpMspMapper.selectRateByOrgnInfos", mspSalePlcyMstDto);
        logger.debug("[MspApiDirectRepository] selectRateByOrgnInfos: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.findMspSalePlcyInfoByOnlyOrgn -- @RequestMapping("/msp/mspSalePlcyInfoByOnlyOrgn") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : findMspSalePlcyInfoByOnlyOrgn -> selectMspSalePlcyInfoByOnlyOrgn

    /** 판매정책정보를 조회 : 상품과 상관없이 기관별 조회 -/msp/mspSalePlcyInfoByOnlyOrgn */
    private List<MspSalePlcyMstDto> selectMspSalePlcyInfoByOnlyOrgn(MspSalePlcyMstDto mspSalePlcyMstDto) {
        logger.debug("[MspApiDirectRepository] selectMspSalePlcyInfoByOnlyOrgn: mspSalePlcyMstDto={}", mspSalePlcyMstDto);
        List<MspSalePlcyMstDto> result = mspSession.selectList("McpMspMapper.selectMspSalePlcyInfoByOnlyOrgn", mspSalePlcyMstDto);
        logger.debug("[MspApiDirectRepository] selectMspSalePlcyInfoByOnlyOrgn: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.getLowPriceChargeInfoByProd -- @RequestMapping("/msp/lowPriceChargeInfoByProd") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getLowPriceChargeInfoByProd -> selectLowPriceChargeInfoByProd

    /** 최저가를 구하기 위한 해당 상품의 요금제 정보 1건 조회 -/msp/lowPriceChargeInfoByProd */
    private MspSaleSubsdMstDto selectLowPriceChargeInfoByProd(MspSaleSubsdMstDto mspSaleSubsdMstDto) {
        logger.debug("[MspApiDirectRepository] selectLowPriceChargeInfoByProd: mspSaleSubsdMstDto={}", mspSaleSubsdMstDto);
        MspSaleSubsdMstDto result = mspSession.selectOne("McpMspMapper.selectLowPriceChargeInfoByProd", mspSaleSubsdMstDto);
        logger.debug("[MspApiDirectRepository] selectLowPriceChargeInfoByProd: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.findMspPhoneInfo -- @RequestMapping("/msp/mspPhoneInfo") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : findMspPhoneInfo -> selectMspPhoneInfo

    /** 단품모델ID로 핸드폰정보를 조회 -/msp/mspPhoneInfo */
    private com.ktmmobile.msf.domains.form.common.dto.PhoneMspDto selectMspPhoneInfo(String prdtId) {
        logger.debug("[MspApiDirectRepository] selectMspPhoneInfo: prdtId={}", prdtId);
        com.ktmmobile.msf.domains.form.common.dto.PhoneMspDto result = mspSession.selectOne("McpMspMapper.selectMspPhoneInfo", prdtId);
        logger.debug("[MspApiDirectRepository] selectMspPhoneInfo: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.findMspSaleOrgnMst -- @RequestMapping("/msp/mspSaleOrgnMst") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : findMspSaleOrgnMst -> selectMspSaleOrgnMst

    /** 판매정책정보를 조회 -/msp/mspSaleOrgnMst */
    private List<MspSalePlcyMstDto> selectMspSaleOrgnMst(MspSalePlcyMstDto mspSalePlcyMstDto) {
        logger.debug("[MspApiDirectRepository] selectMspSaleOrgnMst: mspSalePlcyMstDto={}", mspSalePlcyMstDto);
        List<MspSalePlcyMstDto> result = mspSession.selectList("McpMspMapper.selectMspSaleOrgnMst", mspSalePlcyMstDto);
        logger.debug("[MspApiDirectRepository] selectMspSaleOrgnMst: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.findMspSalePrdMst -- @RequestMapping("/msp/mspSalePrdMst") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : findMspSalePrdMst -> selectMspSalePrdMst

    /** 판매중인 상품정보를 조회 -/msp/mspSalePrdMst */
    private MspSalePrdtMstDto selectMspSalePrdMst(MspSalePrdtMstDto mspSalePrdtMstDto) {
        logger.debug("[MspApiDirectRepository] selectMspSalePrdMst: mspSalePrdtMstDto={}", mspSalePrdtMstDto);
        MspSalePrdtMstDto result = mspSession.selectOne("McpMspMapper.selectMspSalePrdMst", mspSalePrdtMstDto);
        logger.debug("[MspApiDirectRepository] selectMspSalePrdMst: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.listMspSaleAgrmMst -- @RequestMapping("/msp/mspSaleAgrmMst") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : listMspSaleAgrmMst -> selectMspSaleAgrmMst

    /** 해당정책코드에 해당하는 약정개월정보 리스트 조회 -/msp/mspSaleAgrmMst */
    private List<com.ktmmobile.msf.domains.form.common.dto.MspSaleAgrmMst> selectMspSaleAgrmMst(String salePlcyCd) {
        logger.debug("[MspApiDirectRepository] selectMspSaleAgrmMst: salePlcyCd={}", salePlcyCd);
        List<com.ktmmobile.msf.domains.form.common.dto.MspSaleAgrmMst> result = mspSession.selectList("McpMspMapper.selectMspSaleAgrmMst",
            salePlcyCd);
        logger.debug("[MspApiDirectRepository] selectMspSaleAgrmMst: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.getMspRateMst -- @RequestMapping("/msp/mspRateMst") -- SELECT(MSP) -- ITO API 사용
    // [MSP/MCP] commonMapper.selectMspRateMst -- @RequestMapping("/common/mspRateMst") -- SELECT(MSP) -- ITO API 사용 -> /msp/mspRateMst 으로 사용
    // SQL 메소드 변경 : getMspRateMst -> selectMspRateMst

    /** 요금제 정보 리스트 조회 -/msp/mspRateMst */
    private MspRateMstDto selectMspRateMst(String rateCd) {
        logger.debug("[MspApiDirectRepository] selectMspRateMst: rateCd={}", rateCd);
        MspRateMstDto result = mspSession.selectOne("McpMspMapper.selectMspRateMst", rateCd);
        logger.debug("[MspApiDirectRepository] selectMspRateMst: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.sellUsimMgmtOrgnId -- @RequestMapping("/msp/sellUsimMgmtOrgnId") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : sellUsimMgmtOrgnId -> selectSellUsimMgmtOrgnId

    /** API설명 -/msp/sellUsimMgmtOrgnId */
    private String selectSellUsimMgmtOrgnId(String searchUsimNo) {
        logger.debug("[MspApiDirectRepository] selectSellUsimMgmtOrgnId: searchUsimNo={}", searchUsimNo);
        String result = mspSession.selectOne("McpMspMapper.selectSellUsimMgmtOrgnId", searchUsimNo);
        logger.debug("[MspApiDirectRepository] selectSellUsimMgmtOrgnId: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.getMspSalePlcyMst -- @RequestMapping("/msp/mspSalePlcyMst") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getMspSalePlcyMst -> selectMspSalePlcyMst

    /** API설명 -/msp/mspSalePlcyMst */
    private MspSalePlcyMstDto selectMspSalePlcyMst(String salePlcyCd) {
        logger.debug("[MspApiDirectRepository] selectMspSalePlcyMst: salePlcyCd={}", salePlcyCd);
        MspSalePlcyMstDto result = mspSession.selectOne("McpMspMapper.selectMspSalePlcyMst", salePlcyCd);
        logger.debug("[MspApiDirectRepository] selectMspSalePlcyMst: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.selectMspSaleSubsdMstListForLowPrice / mspMapper.selectMspSaleSubsdMstListWithRateInfo -- @RequestMapping("/msp/mspSaleSubsdMstList") -- SELECT(MSP) -- ITO API 사용

    /** 최저가를 구하기 위한 해당 상품의 요금제 정보 리스트를 조회 -/msp/mspSaleSubsdMstList */
    private List<MspSaleSubsdMstDto> selectSaleSubsdMstList(MspSaleSubsdMstDto mspSaleSubsdMstDto) {
        logger.debug("[MspApiDirectRepository] selectSaleSubsdMstList: mspSaleSubsdMstDto={}", mspSaleSubsdMstDto);
        List<MspSaleSubsdMstDto> result = null;
        if ("Y".equals(mspSaleSubsdMstDto.getForFrontFastYn())) {
            result = mspSession.selectList("McpMspMapper.selectMspSaleSubsdMstListForLowPrice", mspSaleSubsdMstDto);
        } else {
            result = mspSession.selectList("McpMspMapper.selectMspSaleSubsdMstListWithRateInfo", mspSaleSubsdMstDto);
        }
        logger.debug("[MspApiDirectRepository] selectSaleSubsdMstList: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.juoSubIngoCount -- @RequestMapping("/msp/juoSubIngoCount") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : juoSubIngoCount -> selectJuoSubIngoCount

    /** API설명 -/msp/juoSubIngoCount */
    private Integer selectJuoSubIngoCount(String subscriberNo) {
        logger.debug("[MspApiDirectRepository] selectJuoSubIngoCount: subscriberNo={}", subscriberNo);
        Integer result = mspSession.selectOne("McpMspMapper.selectJuoSubIngoCount", subscriberNo);
        logger.debug("[MspApiDirectRepository] selectJuoSubIngoCount: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] mspMapper.selectMspCombRateMapp -- @RequestMapping("/msp/mspCombRateMapp") -- SELECT(MSP) -- ITO API 사용

    /** API설명 -/msp/mspCombRateMapp */
    private com.ktmmobile.msf.domains.form.common.dto.MyCombinationResDto selectMspCombRateMapp(String pRateCd) {
        logger.debug("[MspApiDirectRepository] selectMspCombRateMapp: pRateCd={}", pRateCd);
        com.ktmmobile.msf.domains.form.common.dto.MyCombinationResDto result = mspSession.selectOne("McpMspMapper.selectMspCombRateMapp", pRateCd);
        logger.debug("[MspApiDirectRepository] selectMspCombRateMapp: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.getCustomerSsn -- @RequestMapping("/msp/customerSsn") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getCustomerSsn -> selectCustomerSsn

    /** 계약번호의 주민번호 조회 -/msp/customerSsn */
    private String selectCustomerSsn(String contractNum) {
        logger.debug("[MspApiDirectRepository] selectCustomerSsn: contractNum={}", contractNum);
        String result = mspSession.selectOne("McpMspMapper.selectCustomerSsn", contractNum);
        logger.debug("[MspApiDirectRepository] selectCustomerSsn: result={}", result);
        return result;
    }


    // [MSP/MCP] mspMapper.getromotionDcAmt -- @RequestMapping("/msp/getromotionDcAmt") -- SELECT(MSP/MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
    // SQL 메소드 변경 : getromotionDcAmt -> selectPromotionDcAmt

    /** API설명 -/msp/getromotionDcAmt */
    private Integer selectPromotionDcAmt(MspSaleSubsdMstDto mspSaleSubsdMstDto) {
        logger.debug("[MspApiDirectRepository] selectPromotionDcAmt: mspSaleSubsdMstDto={}", mspSaleSubsdMstDto);
        Integer result = mspSession.selectOne("McpMspMapper.selectPromotionDcAmt", mspSaleSubsdMstDto);
        logger.debug("[MspApiDirectRepository] selectPromotionDcAmt: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] mypageMapper.selectMspAddInfo -- @RequestMapping("/mypage/mspAddInfo")-- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : mspAddInfo -> selectMspAddInfo

    /** 할부원금 조회 -/mypage/mspAddInfo */
    private MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        logger.debug("[MspApiDirectRepository] selectMspAddInfo: svcCntrNo={}", svcCntrNo);
        MspJuoAddInfoDto result = mspSession.selectOne("McpMyPageMapper.selectMspAddInfo", svcCntrNo);
        logger.debug("[MspApiDirectRepository] selectMspAddInfo: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.insertRwdOrder -- @RequestMapping("/mypage/insertRwdOrder") -- INSERT(MSP) -- ITO API 사용

    /** 자급제 보상서비스 주문 -/mypage/insertRwdOrder */
    private HashMap<String, String> insertRwdOrder(RwdOrderDto rwdOrderDto) {
        logger.debug("[MspApiDirectRepository] insertRwdOrder: rwdOrderDto={}", rwdOrderDto);
        HashMap<String, String> rtnMap = new HashMap<>();

        try {
            int result = mspSession.insert("McpMyPageMapper.insertRwdOrder", rwdOrderDto);
            logger.debug("[MspApiDirectRepository] insertRwdOrder: result={}", result);
            if (result == 1) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청이 완료되었습니다.");
                rtnMap.put("RWD_SEQ", String.valueOf(rwdOrderDto.getRwdSeq()));
            } else {
                rtnMap.put("RESULT_CODE", "FAIL5");
                rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청에 실패했습니다.");
            }
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "FAIL6");
            rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청에 실패했습니다.");
        }

        return rtnMap;
    }


    // [MSP/MCP] mypageMapper.getOrgScanId -- @RequestMapping("/mypage/getOrgScanId") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getOrgScanId -> selectOrgScanId

    /** MSP_JUO_SUB_INFO에서 orgSacnId 조회 -/mypage/getOrgScanId */
    private String selectOrgScanId(String contractNum) {
        logger.debug("[MspApiDirectRepository] selectOrgScanId: contractNum={}", contractNum);
        String result = mspSession.selectOne("McpMyPageMapper.selectOrgScanId", contractNum);
        logger.debug("[MspApiDirectRepository] selectOrgScanId: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectCntrList -- @RequestMapping("/mypage/cntrList") -- SELECT -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용

    /** API설명 -/mypage/cntrList */
    private List<McpUserCntrMngDto> selectCntrList(HashMap<String, String> paramMap) {
        logger.debug("[MspApiDirectRepository] selectCntrList: paramMap={}", paramMap);
        List<McpUserCntrMngDto> result = mspSession.selectList("McpMyPageMapper.selectCntrList", paramMap);
        logger.debug("[MspApiDirectRepository] selectCntrList: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectContractObj -- @RequestMapping("/mypage/selectContractObj") -- SELECT(MSP) -- ITO API 사용

    /** API설명 -/mypage/selectContractObj */
    private Map<String, String> selectContractObj(HashMap<String, String> paramMap) {
        logger.debug("[MspApiDirectRepository] selectContractObj: paramMap={}", paramMap);
        Map<String, String> result = mspSession.selectMap("McpMyPageMapper.selectContractObj", String.valueOf(paramMap));
        logger.debug("[MspApiDirectRepository] selectContractObj: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectJuoSubInfo -- @RequestMapping("/mypage/juoSubInfo") -- SELECT(MSP) -- ITO API 사용

    /** 회원가입시 회선 정보 조회 -/mypage/juoSubInfo */
    private JuoSubInfoDto selectJuoSubInfo(HashMap<String, String> paramMap) {
        logger.debug("[MspApiDirectRepository] selectJuoSubInfo: paramMap={}", paramMap);
        JuoSubInfoDto result = mspSession.selectOne("McpMyPageMapper.selectJuoSubInfo", paramMap);
        logger.debug("[MspApiDirectRepository] selectJuoSubInfo: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.getChannelInfo -- @RequestMapping("/mypage/channelInfo") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getChannelInfo -> selectChannelInfo

    /** 개통 채널정보 -/mypage/channelInfo */
    private MspJuoAddInfoDto selectChannelInfo(String contractNum) {
        logger.debug("[MspApiDirectRepository] selectChannelInfo: contractNum={}", contractNum);
        MspJuoAddInfoDto result = mspSession.selectOne("McpMyPageMapper.selectChannelInfo", contractNum);
        logger.debug("[MspApiDirectRepository] selectChannelInfo: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectCntrListNmChg -- @RequestMapping("/mypage/cntrListNmChg") -- SELECT -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용

    /** 현재요금제정보 가져오기 (명의변경) -/mypage/cntrListNmChg */
    private List<McpUserCntrMngDto> selectCntrListNmChg(HashMap<String, String> paramMap) {
        logger.debug("[MspApiDirectRepository] selectCntrListNmChg: paramMap={}", paramMap);
        List<McpUserCntrMngDto> result = mspSession.selectList("McpMyPageMapper.selectCntrListNmChg", paramMap);
        logger.debug("[MspApiDirectRepository] selectCntrListNmChg: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectCntrListNoLogin -- @RequestMapping("/mypage/cntrListNoLogin") -- SELECT(MSP) -- ITO API 사용

    /** 계약 현행화 정보 -/mypage/cntrListNoLogin */
    private McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto mcpUserCntrMngDto) {
        logger.debug("[MspApiDirectRepository] selectCntrListNoLogin: mcpUserCntrMngDto={}", mcpUserCntrMngDto);
        McpUserCntrMngDto result = mspSession.selectOne("McpMyPageMapper.selectCntrListNoLogin", mcpUserCntrMngDto);
        logger.debug("[MspApiDirectRepository] selectCntrListNoLogin: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectSocDesc -- @RequestMapping("/mypage/socDesc") -- SELECT -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용

    /** API설명 -/mypage/socDesc */
    private McpUserCntrMngDto selectSocDesc(String svcCntrNo) {
        logger.debug("[MspApiDirectRepository] selectSocDesc: svcCntrNo={}", svcCntrNo);
        McpUserCntrMngDto result = mspSession.selectOne("McpMyPageMapper.selectSocDesc", svcCntrNo);
        logger.debug("[MspApiDirectRepository] selectSocDesc: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectCustomerType -- @RequestMapping("/mypage/customerType") -- SELECT(MSP) -- ITO API 사용

    /** API설명 -/mypage/customerType */
    private String selectCustomerType(String custId) {
        logger.debug("[MspApiDirectRepository] selectCustomerType: custId={}", custId);
        String result = mspSession.selectOne("McpMyPageMapper.selectCustomerType", custId);
        logger.debug("[MspApiDirectRepository] selectCustomerType: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectBanSel -- @RequestMapping("/mypage/selectBanSel") -- SELECT(MSP) -- ITO API 사용

    /** 청구계정 번호 조회 -/mypage/selectBanSel */
    private String selectBanSel(String contractNum) {
        logger.debug("[MspApiDirectRepository] selectBanSel: contractNum={}", contractNum);
        String result = mspSession.selectOne("McpMyPageMapper.selectBanSel", contractNum);
        logger.debug("[MspApiDirectRepository] selectBanSel: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectConSsnObj -- @RequestMapping("/mypage/selectConSsnObj") -- SELECT(MSP) -- ITO API 사용

    /** 주민번호,이름,연락처로 계약번호 조회 -/mypage/selectConSsnObj */
    private Map<String, String> selectConSsnObj(HashMap<String, String> paramMap) {
        logger.debug("[MspApiDirectRepository] selectConSsnObj: paramMap={}", paramMap);
        Map<String, Object> result = mspSession.selectOne("McpMyPageMapper.selectConSsnObj", paramMap);
        logger.debug("[MspApiDirectRepository] selectConSsnObj: result={}", result);
        if (result == null) return null;
        Map<String, String> strResult = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            strResult.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return strResult;
    }


    // [MSP/MCP] mypageMapper.selectPrePayment -- @RequestMapping("/mypage/prePayment") -- SELECT(MSP) -- ITO API 사용

    /** 선불 요금제 사용 여부 조회 -/mypage/prePayment */
    private Integer selectPrePayment(String contractNum) {
        logger.debug("[MspApiDirectRepository] selectPrePayment: contractNum={}", contractNum);
        Integer result = mspSession.selectOne("McpMyPageMapper.selectPrePayment", contractNum);
        logger.debug("[MspApiDirectRepository] selectPrePayment: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] mypageMapper.getInsrInfo -- @RequestMapping("/mypage/getInsrInfo") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getInsrInfo -> selectInsrInfo

    /** 안심보험가입여부조회 -/mypage/getInsrInfo */
    private Map<String, String> selectInsrInfo(String contractNum) {
        logger.debug("[MspApiDirectRepository] selectInsrInfo: contractNum={}", contractNum);
        Map<String, String> result = mspSession.selectMap("McpMyPageMapper.selectInsrInfo", contractNum);
        logger.debug("[MspApiDirectRepository] selectInsrInfo: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.getInsrInfoByCd -- @RequestMapping("/mypage/getInsrInfoByCd") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getInsrInfoByCd -> selectInsrInfoByCd

    /** 안심보험정보조회(by insrCd) -/mypage/getInsrInfoByCd */
    private Map<String, String> selectInsrInfoByCd(String insrCd) {
        logger.debug("[MspApiDirectRepository] selectInsrInfoByCd: insrCd={}", insrCd);
        Map<String, String> result = mspSession.selectMap("McpMyPageMapper.selectInsrInfoByCd", insrCd);
        logger.debug("[MspApiDirectRepository] selectInsrInfoByCd: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectFarPricePlan -- @RequestMapping("/mypage/farPricePlan") -- SELECT(MSP) -- ITO API 사용

    /** API설명 -/mypage/farPricePlan */
    private McpFarPriceDto selectFarPricePlan(String ncn) {
        logger.debug("[MspApiDirectRepository] selectFarPricePlan: ncn={}", ncn);
        McpFarPriceDto result = mspSession.selectOne("McpMyPageMapper.selectFarPricePlan", ncn);
        logger.debug("[MspApiDirectRepository] selectFarPricePlan: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.countFarPricePlanList -- @RequestMapping("/mypage/countFarPricePlanList") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : countFarPricePlanList -> selectFarPricePlanListCount

    /** API설명 -/mypage/countFarPricePlanList */
    private Integer selectFarPricePlanListCount(String rateCd) {
        logger.debug("[MspApiDirectRepository] selectFarPricePlanListCount: rateCd={}", rateCd);
        Integer result = mspSession.selectOne("McpMyPageMapper.selectFarPricePlanListCount", rateCd);
        logger.debug("[MspApiDirectRepository] selectFarPricePlanListCount: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] mypageMapper.selectFarPricePlanList -- @RequestMapping("/mypage/farPricePlanList") -- SELECT(MSP) -- ITO API 사용

    /** API설명 -/mypage/farPricePlanList */
    private List<McpFarPriceDto> selectFarPricePlanList(String rateCd) {
        logger.debug("[MspApiDirectRepository] selectFarPricePlanList: rateCd={}", rateCd);
        List<McpFarPriceDto> result = mspSession.selectList("McpMyPageMapper.selectFarPricePlanList", rateCd);
        logger.debug("[MspApiDirectRepository] selectFarPricePlanList: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.getEnggInfo -- @RequestMapping("/mypage/enggInfo1") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getEnggInfo -> selectEnggInfo

    /** 약정정보 -/mypage/enggInfo1 */
    private MspJuoAddInfoDto selectEnggInfo(String contractNum) {
        logger.debug("[MspApiDirectRepository] selectEnggInfo: contractNum={}", contractNum);
        MspJuoAddInfoDto result = mspSession.selectOne("McpMyPageMapper.selectEnggInfo", contractNum);
        logger.debug("[MspApiDirectRepository] selectEnggInfo: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectFarPriceAddInfo -- @RequestMapping("/mypage/farPriceAddInfo") -- SELECT(MSP) -- ITO API 사용

    /** API설명 -/mypage/farPriceAddInfo */
    private String selectFarPriceAddInfo(Map<String, String> paramMap) {
        logger.debug("[MspApiDirectRepository] selectFarPriceAddInfo: paramMap={}", paramMap);
        String result = mspSession.selectOne("McpMyPageMapper.selectFarPriceAddInfo", paramMap);
        logger.debug("[MspApiDirectRepository] selectFarPriceAddInfo: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.getCloseSubList -- @RequestMapping("/mypage/closeSubList") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getCloseSubList -> selectCloseSubList

    /** 해지 해야할 부가 서비스 리스트 -/mypage/closeSubList */
    private List<McpUserCntrMngDto> selectCloseSubList(String contractNum) {
        logger.debug("[MspApiDirectRepository] selectCloseSubList: contractNum={}", contractNum);
        List<McpUserCntrMngDto> result = mspSession.selectList("McpMyPageMapper.selectCloseSubList", contractNum);
        logger.debug("[MspApiDirectRepository] selectCloseSubList: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.insertSocfailProcMst -- @RequestMapping("/mypage/insertSocfailProcMst") -- INSERT(MSP) -- ITO API 사용

    /** API설명 -/mypage/insertSocfailProcMst */
    private boolean insertSocfailProcMst(McpServiceAlterTraceDto mcpServiceAlterTraceDto) {
        logger.debug("[MspApiDirectRepository] insertSocfailProcMst: mcpServiceAlterTraceDto={}", mcpServiceAlterTraceDto);
        Integer result = mspSession.insert("McpMyPageMapper.insertSocfailProcMst", mcpServiceAlterTraceDto);
        logger.debug("[MspApiDirectRepository] insertSocfailProcMst: result={}", result);
        return result != null && result > 0;
    }


    // [MSP/MCP] mypageMapper.getChrgPrmtIdSocChg -- @RequestMapping("/mypage/getChrgPrmtIdSocChg") -- SELECT(MSP) -- ITO API 사용
    // SQL 메소드 변경 : getChrgPrmtIdSocChg -> selectChrgPrmtIdSocChg

    /** API설명 -/mypage/getChrgPrmtIdSocChg */
    private String selectChrgPrmtIdSocChg(String rateCd) {
        logger.debug("[MspApiDirectRepository] selectChrgPrmtIdSocChg: rateCd={}", rateCd);
        String result = mspSession.selectOne("McpMyPageMapper.selectChrgPrmtIdSocChg", rateCd);
        logger.debug("[MspApiDirectRepository] selectChrgPrmtIdSocChg: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.insertDisApd -- @RequestMapping("/mypage/insertDisApd") -- SELECT(MSP)/INSERT(MSP) -- ITO API 사용

    /** API설명 -/mypage/insertDisApd */
    private Integer insertDisApd(McpUserCntrMngDto mcpUserCntrMngDto) {
        logger.debug("[MspApiDirectRepository] insertDisApd: mcpUserCntrMngDto={}", mcpUserCntrMngDto);
        Integer result = mspSession.insert("McpMyPageMapper.insertDisApd", mcpUserCntrMngDto);
        logger.debug("[MspApiDirectRepository] insertDisApd: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] mypageMapper.getromotionDcList -- @RequestMapping("/mypage/romotionDcList") -- SELECT(MSP/MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
    // SQL 메소드 변경 : getromotionDcList -> selectPromotionDcList

    /** 가입 해야할 부가 서비스 리스트 -/mypage/romotionDcList */
    private List<McpUserCntrMngDto> selectPromotionDcList(String rateCd) {
        logger.debug("[MspApiDirectRepository] selectPromotionDcList: rateCd={}", rateCd);
        List<McpUserCntrMngDto> result = mspSession.selectList("McpMyPageMapper.selectPromotionDcList", rateCd);
        logger.debug("[MspApiDirectRepository] selectPromotionDcList: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.getRateInfo -- @RequestMapping("/mypage/getRateInfo") -- SELECT(MSP/MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용
    // SQL 메소드 변경 : getRateInfo -> selectRateInfo

    /** 월납부 통신요금  = 기본요금 - 할인요금 -/mypage/getRateInfo */
    private MspSaleSubsdMstDto selectRateInfo(String rateCd) {
        logger.debug("[MspApiDirectRepository] selectRateInfo: rateCd={}", rateCd);
        MspSaleSubsdMstDto result = mspSession.selectOne("McpMyPageMapper.selectRateInfo", rateCd);
        logger.debug("[MspApiDirectRepository] selectRateInfo: result={}", result);
        return result;
    }


    // [MSP/MCP] mypageMapper.selectRegService -- @RequestMapping("/mypage/regService") -- SELECT(MSP) -- ITO API 사용

    /** API설명 -/mypage/regService */
    private List<McpRegServiceDto> selectRegService(String ncn) {
        logger.debug("[MspApiDirectRepository] selectRegService:  ncn={}", ncn);
        List<McpRegServiceDto> result = mspSession.selectList("McpMyPageMapper.selectRegService", ncn);
        logger.debug("[MspApiDirectRepository] selectRegService: result={}", result);
        return result;
    }


    // [MSP/MCP] orderMapper.selectOrderGroupListCount -- @RequestMapping("/order/selectOrderGroupListCount") -- SELECT(MCP) -- ITO API 사용

    /** API설명 -/order/selectOrderGroupListCount */
    private Integer selectOrderGroupListCount(OrderDto orderDto) {
        logger.debug("[MspApiDirectRepository] selectOrderGroupListCount: orderDto={}", orderDto);
        Integer result = mspSession.selectOne("McpOrderMapper.selectOrderGroupListCount", orderDto);
        logger.debug("[MspApiDirectRepository] selectOrderGroupListCount: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] orderMapper.selectOrderGroupList -- @RequestMapping("/order/selectOrderGroupList") -- SELECT(MCP) -- ITO API 사용

    /** API설명 -/order/selectOrderGroupList */
    private List<OrderDto> selectOrderGroupList(OrderDto orderDto) {
        logger.debug("[MspApiDirectRepository] selectOrderGroupList: orderDto={}", orderDto);
        List<OrderDto> result = mspSession.selectList("McpOrderMapper.selectOrderGroupList", orderDto);
        logger.debug("[MspApiDirectRepository] selectOrderGroupList: result={}", result);
        return result;
    }


    // [MSP/MCP] orderMapper.selectOrderTempListCount -- @RequestMapping("/order/selectOrderTempListCount") -- SELECT(MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용

    /** API설명 -/order/selectOrderTempListCount */
    private Integer selectOrderTempListCount(OrderDto orderDto) {
        logger.debug("[MspApiDirectRepository] selectOrderTempListCount: orderDto={}", orderDto);
        Integer result = mspSession.selectOne("McpOrderMapper.selectOrderTempListCount", orderDto);
        logger.debug("[MspApiDirectRepository] selectOrderTempListCount: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] orderMapper.selectOrderTempList -- @RequestMapping("/order/selectOrderTempPageList") -- SELECT(MCP) -- 스마신청서App으로 SQL 이관, ITO API SQL 미사용

    /** API설명 -/order/selectOrderTempPageList */
    private List<OrderDto> selectOrderTempList(OrderDto orderDto) {
        logger.debug("[MspApiDirectRepository] selectOrderTempList: orderDto={}", orderDto);
        List<OrderDto> result = mspSession.selectList("McpOrderMapper.selectOrderTempList", orderDto);
        logger.debug("[MspApiDirectRepository] selectOrderTempList: result={}", result);
        return result;
    }


    // [MSP/MCP] phoneMapper.listPhoneProdBasForFrontOneQuery2 -- @RequestMapping("/phone/phoneProdBasForFrontOneQuery") -- SELECT(MSP/MCP) -- ITO API 사용
    // SQL 메소드 변경 : listPhoneProdBasForFrontOneQuery2 -> selectPhoneProdBas2
    // SQL 메소드 변경 : listPhoneProdBasForFrontOneQuery -> selectPhoneProdBas

    /** 상품리스트 조회 -/phone/phoneProdBasForFrontOneQuery */
    private List<PhoneProdBasDto> selectPhoneProdBas(CommonSearchDto commonSearchDto) {
        logger.debug("[MspApiDirectRepository] selectPhoneProdBas: commonSearchDto={}", commonSearchDto);
        List<PhoneProdBasDto> result = null;
        String sesplsYn = commonSearchDto.getSesplsYn();
        if ("Y".equals(sesplsYn)) {
            // 자급제인 경우
            result = mspSession.selectList("McpPhoneMapper.selectPhoneProdBas2", commonSearchDto);
        } else {
            // 휴대폰, 중고폰인 경우
            result = mspSession.selectList("McpPhoneMapper.selectPhoneProdBas", commonSearchDto);
        }
        logger.debug("[MspApiDirectRepository] selectPhoneProdBas: result={}", result);
        return result;
    }

    // [MSP/MCP] phoneMapper.findNmcpProdBas -- @RequestMapping("/phone/nmcpProdBas") -- SELECT(MSP/MCP) -- ITO API 사용
    // SQL 메소드 변경 : findNmcpProdBas -> selectNmcpProdBas

    /** 핸드폰 상품관리 상세 조회 -/phone/nmcpProdBas */
    private PhoneProdBasDto selectNmcpProdBas(CommonSearchDto commonSearchDto) {
        logger.debug("[MspApiDirectRepository] selectNmcpProdBas: commonSearchDto={}", commonSearchDto);
        PhoneProdBasDto result = mspSession.selectOne("McpPhoneMapper.selectNmcpProdBas", commonSearchDto);
        logger.debug("[MspApiDirectRepository] selectNmcpProdBas: result={}", result);
        return result;
    }


    // [MSP/MCP] prepiaMapper.getRateList -- @RequestMapping("/prepia/rateList") -- SELECT(MSP/MCP) -- ITO API 사용
    // SQL 메소드 변경 : getRateList -> selectRateList

    /** API설명 -/prepia/rateList */
    private List<MspSaleSubsdMstDto> selectRateList(Map<String, String> paramMap) {
        logger.debug("[MspApiDirectRepository] selectRateList: paramMap={}", paramMap);
        List<MspSaleSubsdMstDto> result = mspSession.selectList("McpPrepiaMapper.selectRateList", paramMap);
        logger.debug("[MspApiDirectRepository] selectRateList: result={}", result);
        return result;
    }


    // [MSP/MCP] smsMapper.insertKakaoNoti -- @RequestMapping("/sms/addKakaoNoti") -- INSERT(MSP) -- ITO API 사용

    /** API설명 -/sms/addKakaoNoti */
    private Integer insertKakaoNoti(ApiMapDto apiMapDto) {
        logger.debug("[MspApiDirectRepository] insertKakaoNoti: apiMapDto={}", apiMapDto);
        Integer result = 0;
        if ("LOCAL".equals(NmcpServiceUtils.getPropertiesVal("SERVER_NAME"))) {
            try {
                apiMapDto.setI_SUBJECT(new String(apiMapDto.getI_SUBJECT().getBytes("ISO-8859-1"), "UTF-8"));
                apiMapDto.setI_MSG(new String(apiMapDto.getI_MSG().getBytes("ISO-8859-1"), "UTF-8"));
                //} catch (UnsupportedEncodingException e1) {
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        result = mspSession.insert("McpSmsMapper.insertKakaoNoti", apiMapDto);
        logger.debug("[MspApiDirectRepository] insertKakaoNoti: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] smsMapper.insertNewSms -- @RequestMapping("/sms/addNewSms") -- INSERT(MSP) -- ITO API 사용

    /** API설명 -/sms/addNewSms */
    private Integer insertNewSms(ApiMapDto apiMapDto) {
        logger.debug("[MspApiDirectRepository] insertNewSms: apiMapDto={}", apiMapDto);
        Integer result = 0;
        if ("LOCAL".equals(NmcpServiceUtils.getPropertiesVal("SERVER_NAME"))) {
            try {
                apiMapDto.setI_SUBJECT(new String(apiMapDto.getI_SUBJECT().getBytes("ISO-8859-1"), "UTF-8"));
                apiMapDto.setI_MSG(new String(apiMapDto.getI_MSG().getBytes("ISO-8859-1"), "UTF-8"));
                //} catch (UnsupportedEncodingException e1) {
            } catch (Exception e1) {
                logger.error("addNewSms error : {}", e1.getMessage());
            }
        }
        result = mspSession.insert("McpSmsMapper.insertNewSms", apiMapDto);
        logger.debug("[MspApiDirectRepository] insertNewSms: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] smsMapper.selectQstackNewCount -- @RequestMapping("/sms/qStackNewCnt") -- INSERT(MSP) -- ITO API 사용

    /** API설명 -/sms/qStackNewCnt */
    private Integer selectQstackNewCount(AuthSmsDto authSmsDto) {
        logger.debug("[MspApiDirectRepository] selectQstackNewCount: authSmsDto={}", authSmsDto);
        Integer result = mspSession.selectOne("McpSmsMapper.selectQstackNewCount", authSmsDto);
        logger.debug("[MspApiDirectRepository] selectQstackNewCount: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] storeUsimMapper.selectJoinUsimPriceNew -- @RequestMapping("/storeUsim/joinUsimPriceNew") -- SELECT(MSP) -- ITO API 사용

    /** USIM 가입비조회 -/storeUsim/joinUsimPriceNew */
    private List<UsimMspRateDto> selectJoinUsimPriceNew(String gubun) {
        logger.debug("[MspApiDirectRepository] selectJoinUsimPriceNew: gubun={}", gubun);
        List<UsimMspRateDto> result = mspSession.selectList("McpStoreUsimMapper.selectJoinUsimPriceNew", gubun);
        logger.debug("[MspApiDirectRepository] selectJoinUsimPriceNew: result={}", result);
        return result;
    }


    // [MSP/MCP] storeUsimMapper.selectFailUsims -- @RequestMapping("/storeUsim/failUsim") -- SELECT(MCP) -- ITO API 사용

    /** 불량 유심 조회 -/storeUsim/failUsim */
    private Integer selectFailUsims(String iccId) {
        logger.debug("[MspApiDirectRepository] selectFailUsims: iccId={}", iccId);
        Integer result = mspSession.selectOne("McpStoreUsimMapper.selectFailUsims", iccId);
        logger.debug("[MspApiDirectRepository] selectFailUsims: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] storeUsimMapper.updateFailUsim -- @RequestMapping("/storeUsim/updateFailUsim") -- UPDATE(MCP) -- ITO API 사용

    /** API설명 -/storeUsim/updateFailUsim */
    private Integer updateFailUsim(JuoSubInfoDto juoSubInfoDto) {
        logger.debug("[MspApiDirectRepository] updateFailUsim: juoSubInfoDto={}", juoSubInfoDto);
        Integer result = mspSession.update("McpStoreUsimMapper.updateFailUsim", juoSubInfoDto);
        logger.debug("[MspApiDirectRepository] updateFailUsim: result={}", result);
        return result != null ? result : 0;
    }


    // [MSP/MCP] storeUsimMapper.selectUsimDcamt -- @RequestMapping("/storeUsim/usimDcamt") -- SELECT(MSP) -- ITO API 사용

    /** usim 상품 약정기간 없는 할인율 조회 -/storeUsim/usimDcamt */
    private UsimMspRateDto selectUsimDcamt(String rateCd) {
        logger.debug("[MspApiDirectRepository] selectUsimDcamt: rateCd={}", rateCd);
        UsimMspRateDto result = mspSession.selectOne("McpStoreUsimMapper.selectUsimDcamt", rateCd);
        logger.debug("[MspApiDirectRepository] selectUsimDcamt: result={}", result);
        return result;
    }


}
