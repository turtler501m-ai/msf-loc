package com.ktmmobile.msf.domains.form.form.servicechange.repository;


import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.commons.mybatis.config.MspMyBatisConfig;
import com.ktmmobile.msf.commons.mybatis.config.SmartFormMyBatisConfig;
import com.ktmmobile.msf.domains.form.common.dto.AppformReqDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeMpPC0Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;

@Slf4j
@Repository
public class SvcAppformRepositoryImpl {

    private static final String SMART_MAPPER_NAMESPACE =
        "com.ktmmobile.msf.domains.form.form.servicechange.repository.smartform.SvcAppformMapper.";

    private final SqlSessionTemplate mspSqlSession;
    private final SqlSessionTemplate smartFormSqlSession;

    public SvcAppformRepositoryImpl(
        @Qualifier(MspMyBatisConfig.SQL_SESSION_TEMPLATE) SqlSessionTemplate mspSqlSession,
        @Qualifier(SmartFormMyBatisConfig.SQL_SESSION_TEMPLATE) SqlSessionTemplate smartFormSqlSession
    ) {
        this.mspSqlSession = mspSqlSession;
        this.smartFormSqlSession = smartFormSqlSession;
    }

    public AppformReqDto getCopyMcpRequest(AppformReqDto appformReq) {
        log.debug("[SvcAppformDao][getCopyMcpRequest] query: contractNum={}",
            appformReq == null ? null : appformReq.getContractNum());
        AppformReqDto result = mspSqlSession.selectOne("MspAppformMapper.getCopyMcpRequest", appformReq);
        log.debug("[SvcAppformDao][getCopyMcpRequest] result: contractNum={}, found={}, requestKey={}, resNo={}, cstmrName={}, cstmrMobile={}-{}-{}",
            appformReq == null ? null : appformReq.getContractNum(),
            result != null,
            result == null ? null : result.getRequestKey(),
            result == null ? null : result.getResNo(),
            result == null ? null : result.getCstmrName(),
            result == null ? null : result.getCstmrMobileFn(),
            result == null ? null : result.getCstmrMobileMn(),
            result == null ? null : result.getCstmrMobileRn());
        return result;
    }

    public AppformReqDto selectDataSharingOp0Appform(Long requestKey) {
        log.debug("[SvcAppformDao][selectDataSharingOp0Appform] query: requestKey={}", requestKey);
        AppformReqDto result = smartFormSqlSession.selectOne(
            SMART_MAPPER_NAMESPACE + "selectDataSharingOp0Appform",
            requestKey);
        log.debug(
            "[SvcAppformDao][selectDataSharingOp0Appform] result: requestKey={}, found={}, resNo={}, agentCode={}, cntpntShopId={}, reqUsimSn={}, socCode={}",
            requestKey,
            result != null,
            result == null ? null : result.getResNo(),
            result == null ? null : result.getAgentCode(),
            result == null ? null : result.getCntpntShopId(),
            result == null ? null : result.getReqUsimSn(),
            result == null ? null : result.getSocCode());
        return result;
    }

    public boolean updateMsfRequestWantNumber(McpRequestDto mcpRequestDto) {
        log.debug("[SvcAppformDao][updateMsfRequestWantNumber] query: requestKey={}, resNo={}, reqWantNumber={}",
            mcpRequestDto == null ? null : mcpRequestDto.getRequestKey(),
            mcpRequestDto == null ? null : mcpRequestDto.getResNo(),
            mcpRequestDto == null ? null : mcpRequestDto.getReqWantNumber());
        int affected = smartFormSqlSession.update(
            SMART_MAPPER_NAMESPACE + "updateMsfRequestWantNumber",
            mcpRequestDto);
        log.debug("[SvcAppformDao][updateMsfRequestWantNumber] result: requestKey={}, resNo={}, affected={}",
            mcpRequestDto == null ? null : mcpRequestDto.getRequestKey(),
            mcpRequestDto == null ? null : mcpRequestDto.getResNo(),
            affected);
        return 0 < affected;
    }

    public String getAgentCode(String cntpntShopId) {
        log.debug("[SvcAppformDao][getAgentCode] query: cntpntShopId={}", cntpntShopId);
        Map<String, Object> agentInfo = mspSqlSession.selectOne("McpAppformMapper.selectAgentCode", cntpntShopId);
        Object ktOrgId = agentInfo == null ? null : agentInfo.get("KT_ORG_ID");
        String agentCode = ktOrgId == null ? "" : String.valueOf(ktOrgId);
        log.debug("[SvcAppformDao][getAgentCode] result: cntpntShopId={}, agentCode={}, agentInfo={}",
            cntpntShopId, agentCode, agentInfo);
        return agentCode;
    }

    /* 미사용
    public boolean insertMcpRequestCstmr(AppformReqDto appformReq) {
        log.debug(
            "[SvcAppformDao][insertMcpRequestCstmr] query: requestKey={}, resNo={}, cstmrName={}, cstmrNativeRrn={}, selfCstmrCi={}, cstmrMobile={}-{}-{}, prntsCtn={}",
            appformReq == null ? null : appformReq.getRequestKey(),
            appformReq == null ? null : appformReq.getResNo(),
            appformReq == null ? null : appformReq.getCstmrName(),
            appformReq == null ? null : appformReq.getCstmrNativeRrn(),
            appformReq == null ? null : appformReq.getSelfCstmrCi(),
            appformReq == null ? null : appformReq.getCstmrMobileFn(),
            appformReq == null ? null : appformReq.getCstmrMobileMn(),
            appformReq == null ? null : appformReq.getCstmrMobileRn(),
            appformReq == null ? null : appformReq.getPrntsCtn());
        int affected = mspSqlSession.insert(
            "com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestCstmrWriteMapper.insertMcpRequestCstmr",
            appformReq);
        log.debug("[SvcAppformDao][insertMcpRequestCstmr] result: requestKey={}, affected={}",
            appformReq == null ? null : appformReq.getRequestKey(), affected);
        return 0 < affected;
    }
    */

    /* 미사용
    public boolean insertMcpRequestAgent(AppformReqDto appformReq) {
        log.debug("[SvcAppformDao][insertMcpRequestAgent] query: requestKey={}, cstmrType={}, minorAgentName={}, minorAgentTel={}-{}-{}",
            appformReq == null ? null : appformReq.getRequestKey(),
            appformReq == null ? null : appformReq.getCstmrType(),
            appformReq == null ? null : appformReq.getMinorAgentName(),
            appformReq == null ? null : appformReq.getMinorAgentTelFn(),
            appformReq == null ? null : appformReq.getMinorAgentTelMn(),
            appformReq == null ? null : appformReq.getMinorAgentTelRn());
        int affected = mspSqlSession.insert(
            "com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestAgentWriteMapper.insertMcpRequestAgent",
            appformReq);
        log.debug("[SvcAppformDao][insertMcpRequestAgent] result: requestKey={}, affected={}",
            appformReq == null ? null : appformReq.getRequestKey(), affected);
        return 0 < affected;
    }
    */

    /* 미사용
    public boolean insertMcpRequestMove(AppformReqDto appformReq) {
        log.debug("[SvcAppformDao][insertMcpRequestMove] query: requestKey={}, moveCompany={}, moveMobile={}-{}-{}",
            appformReq == null ? null : appformReq.getRequestKey(),
            appformReq == null ? null : appformReq.getMoveCompany(),
            appformReq == null ? null : appformReq.getMoveMobileFn(),
            appformReq == null ? null : appformReq.getMoveMobileMn(),
            appformReq == null ? null : appformReq.getMoveMobileRn());
        int affected = mspSqlSession.insert(
            "com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestMoveWriteMapper.insertMcpRequestMove",
            appformReq);
        log.debug("[SvcAppformDao][insertMcpRequestMove] result: requestKey={}, affected={}",
            appformReq == null ? null : appformReq.getRequestKey(), affected);
        return 0 < affected;
    }
    */

    public boolean insertMsfRequest(AppformReqDto appformReq) {
        return insertSmartForm("insertMsfRequest", appformReq,
            value -> smartFormSqlSession.insert(SMART_MAPPER_NAMESPACE + "insertMsfRequest", value));
    }

    public boolean insertMsfRequestCstmr(AppformReqDto appformReq) {
        return insertSmartForm("insertMsfRequestCstmr", appformReq,
            value -> smartFormSqlSession.insert(SMART_MAPPER_NAMESPACE + "insertMsfRequestCstmr", value));
    }

    public boolean insertMsfRequestAgent(AppformReqDto appformReq) {
        return insertSmartForm("insertMsfRequestAgent", appformReq,
            value -> smartFormSqlSession.insert(SMART_MAPPER_NAMESPACE + "insertMsfRequestAgent", value));
    }

    public boolean insertMsfRequestMove(AppformReqDto appformReq) {
        return insertSmartForm("insertMsfRequestMove", appformReq,
            value -> smartFormSqlSession.insert(SMART_MAPPER_NAMESPACE + "insertMsfRequestMove", value));
    }

    public boolean insertMsfRequestSale(AppformReqDto appformReq) {
        return insertSmartForm("insertMsfRequestSale", appformReq,
            value -> smartFormSqlSession.insert(SMART_MAPPER_NAMESPACE + "insertMsfRequestSale", value));
    }

    public boolean insertMsfRequestBillReq(AppformReqDto appformReq) {
        return insertSmartForm("insertMsfRequestBillReq", appformReq,
            value -> smartFormSqlSession.insert(SMART_MAPPER_NAMESPACE + "insertMsfRequestBillReq", value));
    }

    public boolean insertMsfRequestDvcChg(AppformReqDto appformReq) {
        return insertSmartForm("insertMsfRequestDvcChg", appformReq,
            value -> smartFormSqlSession.insert(SMART_MAPPER_NAMESPACE + "insertMsfRequestDvcChg", value));
    }

    /* 미사용
    public boolean insertMsfRequestAddition(AppformReqDto appformReq) {
        return insertSmartForm("insertMsfRequestAddition", appformReq,
            value -> smartFormSqlSession.insert(SMART_MAPPER_NAMESPACE + "insertMsfRequestAddition", value));
    }
    */

    public List<Map<String, Object>> getMcpAdditionList(AppformReqDto appformReq) {
        log.debug("[SvcAppformDao][getMcpAdditionList] query: additionKeyList={}",
            appformReq == null ? null : java.util.Arrays.toString(appformReq.getAdditionKeyList()));
        List<Map<String, Object>> result = mspSqlSession.selectList("MspAppformMapper.getMcpAdditionList", appformReq);
        log.debug("[SvcAppformDao][getMcpAdditionList] result: count={}", result == null ? 0 : result.size());
        return result;
    }

    public boolean insertMsfRequestAdditionItem(Map<String, Object> additionItem) {
        log.debug("[SvcAppformDao][insertMsfRequestAdditionItem] query: requestKey={}, additionKey={}",
            additionItem == null ? null : additionItem.get("requestKey"),
            additionItem == null ? null : additionItem.get("additionKey"));
        int affected = smartFormSqlSession.insert(
            SMART_MAPPER_NAMESPACE + "insertMsfRequestAdditionItem",
            additionItem);
        log.debug("[SvcAppformDao][insertMsfRequestAdditionItem] result: additionKey={}, affected={}",
            additionItem == null ? null : additionItem.get("additionKey"), affected);
        return 0 < affected;
    }

    public boolean insertMsfRequestState(AppformReqDto appformReq) {
        return insertSmartForm("insertMsfRequestState", appformReq,
            value -> smartFormSqlSession.insert(SMART_MAPPER_NAMESPACE + "insertMsfRequestState", value));
    }

    public NewChangeMpPC0Response selectMsfRequestMpRequest(NewChangeRequest request) {
        log.debug("[SvcAppformDao][selectMsfRequestMpRequest] query: requestKey={}",
            request == null ? null : request.getRequestKey());
        NewChangeMpPC0Response result = smartFormSqlSession.selectOne(
            SMART_MAPPER_NAMESPACE + "selectMsfRequestMpRequest",
            request);
        log.debug("[SvcAppformDao][selectMsfRequestMpRequest] result: requestKey={}, found={}, mvnoOrdNo={}, operTypeCd={}",
            request == null ? null : request.getRequestKey(),
            result != null,
            result == null ? null : result.getMvnoOrdNo(),
            result == null ? null : result.getOperTypeCd());
        return result;
    }

    private boolean insertSmartForm(String action, AppformReqDto appformReq, ToIntFunction<AppformReqDto> insertFn) {
        log.debug("[SvcAppformDao][{}] query: requestKey={}, resNo={}",
            action,
            appformReq == null ? null : appformReq.getRequestKey(),
            appformReq == null ? null : appformReq.getResNo());
        int affected = insertFn.applyAsInt(appformReq);
        log.debug("[SvcAppformDao][{}] result: requestKey={}, affected={}",
            action,
            appformReq == null ? null : appformReq.getRequestKey(),
            affected);
        return 0 < affected;
    }

    /* 미사용
    public int requestOsstCount(McpRequestOsstDto mcpRequestOsstDto) {
        log.debug("[SvcAppformDao][requestOsstCount] query: mvnoOrdNo={}, prgrStatCd={}, rsltCd={}",
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getMvnoOrdNo(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getPrgrStatCd(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getRsltCd());
        Object resultObj = mspSqlSession.selectOne("MspAppformMapper.requestOsstCount", mcpRequestOsstDto);
        if (resultObj instanceof Number number) {
            int count = number.intValue();
            log.debug("[SvcAppformDao][requestOsstCount] result: mvnoOrdNo={}, prgrStatCd={}, count={}",
                mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getMvnoOrdNo(),
                mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getPrgrStatCd(),
                count);
            return count;
        }
        log.debug("[SvcAppformDao][requestOsstCount] result: mvnoOrdNo={}, prgrStatCd={}, count=0, rawResult={}",
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getMvnoOrdNo(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getPrgrStatCd(),
            resultObj);
        return 0;
    }
    */

    public McpRequestOsstDto getRequestOsst(McpRequestOsstDto mcpRequestOsstDto) {
        log.debug("[SvcAppformDao][getRequestOsst] query: mvnoOrdNo={}, prgrStatCd={}",
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getMvnoOrdNo(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getPrgrStatCd());
        McpRequestOsstDto result = mspSqlSession.selectOne("MspAppformMapper.getRequestOsst", mcpRequestOsstDto);
        log.debug("[SvcAppformDao][getRequestOsst] result: mvnoOrdNo={}, prgrStatCd={}, found={}, osstOrdNo={}, rsltCd={}, tlphNo={}",
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getMvnoOrdNo(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getPrgrStatCd(),
            result != null,
            result == null ? null : result.getOsstOrdNo(),
            result == null ? null : result.getRsltCd(),
            result == null ? null : result.getTlphNo());
        return result;
    }

    public boolean insertMcpRequestOsst(McpRequestOsstDto mcpRequestOsstDto) {
        log.debug("[SvcAppformDao][insertMcpRequestOsst] query: mvnoOrdNo={}, osstOrdNo={}, prgrStatCd={}, rsltCd={}, tlphNo={}, nstepGlobalId={}",
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getMvnoOrdNo(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getOsstOrdNo(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getPrgrStatCd(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getRsltCd(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getTlphNo(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getNstepGlobalId());
        int affected = mspSqlSession.insert("MspAppformMapper.insertMcpRequestOsst", mcpRequestOsstDto);
        log.debug("[SvcAppformDao][insertMcpRequestOsst] result: mvnoOrdNo={}, prgrStatCd={}, affected={}",
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getMvnoOrdNo(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getPrgrStatCd(),
            affected);
        return 0 < affected;
    }

    public NmcpCdDtlDto getCodeNmDto(String cdGroupId, String dtlCd) {
        log.debug("[SvcAppformDao][getCodeNmDto] query: cdGroupId={}, dtlCd={}", cdGroupId, dtlCd);
        NmcpCdDtlDto param = new NmcpCdDtlDto();
        param.setCdGroupId(cdGroupId);
        param.setDtlCd(dtlCd);
        NmcpCdDtlDto result = mspSqlSession.selectOne("MspAppformMapper.getCodeNmDto", param);
        log.debug("[SvcAppformDao][getCodeNmDto] result: cdGroupId={}, dtlCd={}, found={}, dtlCdNm={}, expnsnStrVal1={}",
            cdGroupId, dtlCd, result != null,
            result == null ? null : result.getDtlCdNm(),
            result == null ? null : result.getExpnsnStrVal1());
        return result;
    }

    public String getMspCommonCodeEtc1(String grpId, String cdVal) {
        log.debug("[SvcAppformDao][getMspCommonCodeEtc1] query: grpId={}, cdVal={}", grpId, cdVal);
        String result = mspSqlSession.selectOne(
            "MspAppformMapper.getMspCommonCodeEtc1",
            Map.of("grpId", grpId, "cdVal", cdVal));
        log.debug("[SvcAppformDao][getMspCommonCodeEtc1] result: grpId={}, cdVal={}, etc1={}",
            grpId, cdVal, result);
        return result;
    }

}
