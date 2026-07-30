package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.common.code.CategoryType;
import com.ktmmobile.msf.domains.form.common.code.ReqBuyType;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.IntmInsrRelDTO;
import com.ktmmobile.msf.domains.form.common.dto.McpRegServiceDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryRelRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.InsrProdRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestAdditionRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestAdditionResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspAdditionDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSalePlcyMstInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.ProductInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.RateInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.ProductInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.ProductSmartInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.service.AuthInfoService;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.IntmInsrResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PhoneModelCapacityResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PhoneModelColorResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PhoneModelMonthlyResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PhoneSaleAgrmResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PhoneSaleTypeResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.ProductInventoryRequest;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.NewChangeMspReadMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final McpApiClient mcpApiClient;
    private final ProductInfoReadMapper productInfoReadMapper;
    private final ProductSmartInfoReadMapper productSmartInfoReadMapper;
    private final AuthInfoService authInfoService;
    private final FormCommService formCommService;
    private final CommonCodeReader commonCodeReader;  //❗️공통코드 조회 서비스 인터페이스 주입
    private final AgencyCacheReader agencyCacheReader;
    private final NewChangeMspReadMapper newChangeMspReadMapper;

    @Value("${LOCAL_TEST:false}")
    private boolean localTest;

    /**
     * 판매정책목록
     */
    public List<MspSalePlcyMstInfoDto> getMspSalePlcyMst(ProductInfoRequest request) {
        List<MspSalePlcyMstInfoDto> mspSalePlcyInfo = null;

        String agentCd = request.getAgentCd(); //대리점코드
        if (agentCd == null || agentCd.isEmpty()) { //넘어온 데이타 없을 경우 세션으로도 캐쉬로도 하지 말고 대리점 목록조회의 첫번째 항목으로 처리
            AgentInfoRequest agentInfoRequest = new AgentInfoRequest();
            List<AgentInfoResponse> agentInfoResponseList = formCommService.getAgentList(agentInfoRequest);
            agentCd = agentInfoResponseList.getFirst().getOrgnId();
        }

        //상품유형 (MM / UU)
        //if (!request.getReqBuyTypeCd().isValid()) {
        //    return new ArrayList<>();
        //}

        //가입유형 추가 >> 2026.07.11
        String operTypeCd = request.getOperTypeCd();
        if (StringUtils.hasText(operTypeCd)) {
            request.setOperTypeCd("MNP3");
        }

        request.setAgentCd(agentCd); //대리점코드
        if (!StringUtils.hasText(request.getSprtTp())) {
            request.setSprtTp("KD"); //할인판매유형 : 단말할인 KD 로 고정
        }

        //단말할부기간 값이 없을 경우 24개월로 조건 추가
        if (!StringUtils.hasText(request.getModelMonthly())) {
            request.setModelMonthly("24");
            //request.setModelMonthly("0");
        }
        log.debug(
            "productInfoRequest >> setRateCd: {}, getOperTypeCd: {}, setAgentCd: {}, setSprtTp: {}, setPrdtSctnCd: {}, setPrdtId: {}, setModelMonthly: {}",
            request.getRateCd(),
            request.getOperTypeCd(),
            agentCd,
            request.getSprtTp(),
            request.getPrdtSctnCd(),
            request.getPrdtId(),
            request.getModelMonthly());

        //판매정책조회
        try {
            mspSalePlcyInfo = productInfoReadMapper.selectMspSalePlcyMst(request);
        } catch (Exception e) {
            throw new SimpleDomainException("조회되는 판매정책이 없습니다.");
        }
        return mspSalePlcyInfo;
    }


    //판매정책조회 (PRDT_ID 값 k코드가 있으면 판매정책 하나가 조회됨)
    public List<MspSalePlcyMstInfoDto> getMspSalePlcyMstList(ProductInfoRequest request) {
        //단말코드가 없는 경우 조회불가? //최초진입시에는 단말보다 판매정책을 먼저 조회하고 단말을 조회하므로 주석처리.
        //판매정책은 하나를 조회하는 것이 아니라 판매정책 목록을 조회해서 조회된 판매정책목록으로 단말 목록을 조회함.
        //if (!StringUtils.hasText(request.getProdId())) {
        //    return null;
        //}
        String agentCd = request.getAgentCd(); //대리점코드
        if (agentCd == null || agentCd.isEmpty()) { //넘어온 데이타 없을 경우 세션으로도 캐쉬로도 하지 말고 대리점 목록조회의 첫번째 항목으로 처리
            AgentInfoRequest agentInfoRequest = new AgentInfoRequest();
            List<AgentInfoResponse> agentInfoResponseList = formCommService.getAgentList(agentInfoRequest);
            agentCd = agentInfoResponseList.getFirst().getOrgnId();
            //agentCd = AuthenticationUtils.getAgentCode(); //대리점코드
        }

        List<MspSalePlcyMstInfoDto> mspSalePlcyInfo = null;
        List<MspSalePlcyMstInfoDto> distinctList = null;
        request.setPlcyTypeCd("N"); //위탁온라인(N) >> 고정 ~ 상수처리필요함.
        //request.setOrgnId(AuthenticationUtils.getAgentCode());
        request.setAgentCd(agentCd); //대리점코드 매핑
        if (!StringUtils.hasText(request.getSprtTp())) {
            request.setSprtTp("KD"); //할인판매유형 : 단말할인 KD 로 고정
        }
        if (!request.getReqBuyTypeCd().isValid()) {
            return new ArrayList<>();
        }

        //단말할부기간 값이 없을 경우 24개월로 조건 추가
        if (!StringUtils.hasText(request.getModelMonthly())) {
            request.setModelMonthly("24");
            //request.setModelMonthly("0");
        }

        //판매정책조회
        //mspSalePlcyInfo = productInfoReadMapper.selectMspSalePlcyMstList(request);
        mspSalePlcyInfo = productInfoReadMapper.selectMspSalePlcyMst(request);
        if (!mspSalePlcyInfo.isEmpty()) {
            distinctList = new ArrayList<>(
                mspSalePlcyInfo.stream()
                    .collect(Collectors.toMap(
                        MspSalePlcyMstInfoDto::getSalePlcyCd,
                        Function.identity(),
                        (existing, replacement) -> existing
                    ))
                    .values()
            );
        }

        return distinctList;

        /*if (mspSalePlcyInfo.size() == 0 || distinctList.size() == 0) {
            return FormResponse.of(ResponseMessage.NO_DATA);
        }*/
        // return FormResponse.of(ResponseMessage.SUCCESS, distinctList);
    }

    //할인유형조회
    public List<PhoneSaleTypeResponse> getSaleTypeList(ProductInfoRequest request) {

        request.setPlcyTypeCd("N"); //정책유형코드 : N
        String agentCd = request.getAgentCd();
        if (agentCd == null || agentCd.equals("")) {
            AgentInfoRequest agentInfoRequest = new AgentInfoRequest();
            List<AgentInfoResponse> agentInfoResponseList = formCommService.getAgentList(agentInfoRequest);
            agentCd = agentInfoResponseList.get(0).getOrgnId();
        }
        request.setAgentCd(agentCd); //대리점코드

        log.debug("할인유형조회 : plcyTypeCd: {}, salePlcyCd: {}, prdtSctnCd: {}, prdtId: {}, agentCd: {}",
            request.getPlcyTypeCd(),
            request.getSalePlcyCd(),
            request.getPrdtSctnCd(),
            request.getPrdtId(),
            request.getAgentCd());

        List<PhoneSaleTypeResponse> salePlcyInfo = productInfoReadMapper.selectSaleTypeList(request);
        return salePlcyInfo;
        //        List<MspSalePlcyMstInfoDto> mspSalePlcyInfo = productInfoMapper.selectMspSalePlcyMstList(condition);
        //        List<MspSalePlcyMstInfoDto> distinctList = new ArrayList<>(
        //                mspSalePlcyInfo.stream()
        //                        .collect(Collectors.toMap(
        //                                MspSalePlcyMstInfoDto::getSprtTp,
        //                                Function.identity(),
        //                                (existing, replacement) -> existing
        //                        ))
        //                        .values()
        //        );
        //        return distinctList;
    }

    //요금 약정기간 조회
    public List<PhoneSaleAgrmResponse> getMspSaleAgrmMstList(ProductInfoRequest request) {
        List<PhoneSaleAgrmResponse> data = null;
        //data = productInfoReadMapper.selectMspSaleAgrmMstList(request);
        //약정기간에 무약정도 추가되도록 판매정책코드 조건절 주석 (쿼리는 유지)
        if (StringUtils.hasText(request.getSalePlcyCd())) {
            data = productInfoReadMapper.selectMspSaleAgrmMstList(request);
        }
        return data;
    }

    //휴대폰 할부기간 조회
    public List<PhoneModelMonthlyResponse> getModelMonthlyList(ProductInfoRequest request) {
        List<PhoneModelMonthlyResponse> data = null;
        data = productInfoReadMapper.selectModelMonthlyList(request);

        //할부기간에 0개월도 추가되도록 판매정책코드 조건절 주석 (쿼리는 유지)
        /*if (StringUtils.hasText(request.getSalePlcyCd())) {
            data = productInfoReadMapper.selectModelMonthlyList(request);
        }*/
        return data;
    }

    //휴대폰 색상 목록 조회
    public List<PhoneModelColorResponse> getPrdtColorList(ProductInfoRequest request) {
        List<PhoneModelColorResponse> data = null;
        if (StringUtils.hasText(request.getRprsPrdtId())) { //대표단말코드로 요청
            data = productInfoReadMapper.selectPrdtColorList(request);
        }
        return data;
    }

    //휴대폰 용량 목록 조회
    public List<PhoneModelCapacityResponse> getPrdtCapacityList(ProductInfoRequest request) {
        List<PhoneModelCapacityResponse> data = null;
        if (StringUtils.hasText(request.getProdId())) { //숫자 4자리 코드로 조회
            data = productInfoReadMapper.selectPrdtCapacityList(request);
        }
        return data;
    }

    //휴대폰 목록조회
    public List<PhoneInfoResponse> getPhoneList(ProductInfoRequest request) {
        String agentCd = request.getAgentCd(); //선택한 대리점코드 :: ex.V000084398

        //가입유형 - 2026.07.11
        String operTypeCd = request.getOperTypeCd();
        if (!StringUtils.hasText(operTypeCd)) {
            request.setOperTypeCd("MNP3");
        }

        //1. 조직코드로 매장재고 단말목록 추출
        ProductInventoryRequest productInventoryRequest = new ProductInventoryRequest();
        productInventoryRequest.setProdId(request.getProdId());
        productInventoryRequest.setAgentCd(agentCd);
        List<CategoryInfoDto> categoryInfoDto = this.getPhoneInventoryList(productInventoryRequest);
        request.setListPhoneDto(categoryInfoDto);

        //2. 판매정책 조회
        request.setPlcyTypeCd("N"); //[필수][스마트 고정] >> N:위탁, W:도매, M:오프라인, D:직영
        List<MspSalePlcyMstInfoDto> listMspSaleDto = this.getMspSalePlcyMstList(request);
        request.setListMspSaleDto(listMspSaleDto);

        //3. M전산에서 조회된 판매정책으로 단말목록조회
        List<PhoneInfoResponse> mspPhoneList = null;
        if (!ObjectUtils.isEmpty(listMspSaleDto)) {
            //mspPhoneList = productInfoReadMapper.selectPhoneList(request);

            List<PhoneInfoResponse> mspPhoneListTmp = productInfoReadMapper.selectPhoneList(request);
            //mspPhoneList = mspPhoneListTmp.stream()
            //    // PROD_ID 없는 데이터 제거
            //    .filter(p -> p.getProdId() != null && !p.getProdId().isEmpty())
            //
            //    // modelId 기준 중복 제거
            //    .collect(Collectors.toMap(
            //        PhoneInfoResponse::getModelId,
            //        Function.identity(),
            //        (existing, replacement) -> existing
            //    ))
            //    .values()
            //    .stream()
            //    .collect(Collectors.toList());

            mspPhoneList = mspPhoneListTmp.stream()
                .collect(Collectors.toMap(
                    PhoneInfoResponse::getModelId,
                    Function.identity(),
                    (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
        }

        //4. M전산 조회 단말목록에서 매장재고 있는 단말목록만 추출
        List<PhoneInfoResponse> data = null;
        if (!ObjectUtils.isEmpty(mspPhoneList)) {
            data = mspPhoneList.stream()
                .filter(phone -> categoryInfoDto.stream()
                    .filter(y -> y.getProdId() != null)
                    .map(x -> x.getProdId())
                    .toList().contains(phone.getModelId()))
                .collect(Collectors.toList());
        }

        return data;
    }

    /**
     * 예상납부금액 조회를 위한 가격정보 조회
     */
    public MspSaleSubsdMstResponse getMspSalePriceInfo(MspSaleSubsdMstRequest request) {
        MspSaleSubsdMstResponse response = new MspSaleSubsdMstResponse();

        String oldYn = "N"; //중고여부
        String salePlcyCd = ""; //판매정책 재조회함.
        String agentCd = StringUtil.NVL(request.getAgentCd(), ""); //조직코드[필수]
        String reqBuyTypeCd = StringUtil.NVL(request.getReqBuyTypeCd(), "MM"); //상품[필수] : 휴대폰, USIM
        String operTypeCd = StringUtil.NVL(request.getOperTypeCd(), ""); //가입유형[필수] : 신규(NAC3) , 번이(MNP3), 기변(HDN3)
        String prdtSctnCd = StringUtil.NVL(request.getPrdtSctnCd(), "LTE"); //데이타유형 : 3G / LTE / 5G / LTE5G >> 기본값 LTE 처리
        String modelId = StringUtil.NVL(request.getModelId(), ""); //단말코드
        String modelMonthly = StringUtil.NVL(request.getModelMonthly(), ""); //단말할부기간
        String sprtTp = StringUtil.NVL(request.getSprtTp(), ""); //할인유형
        String agrmTrm = StringUtil.NVL(request.getAgrmTrm(), "0"); //요금약정기간
        String socCd = StringUtil.NVL(request.getRateCd(), ""); //요금제 코드[필수]
        String joinPayMthdCd = StringUtil.NVL(request.getJoinPayMthdCd(), ""); //화면에서 선택값 추가
        String joinPriceTypeCd = ""; //joinPayMthdCd 값에 의해 결정됨

        log.debug(
            "getMspSalePriceInfo >> agentCd: {}, reqBuyTypeCd: {}, operTypeCd: {}, prdtSctnCd: {}, modelId: {}, modelMonthly: {}, sprtTp: {}, agrmTrm: {}, socCd: {}, joinPayMthdCd: {}",
            agentCd,
            reqBuyTypeCd,
            operTypeCd,
            prdtSctnCd,
            modelId,
            modelMonthly,
            sprtTp,
            agrmTrm,
            socCd,
            joinPayMthdCd);

        //화면로딩이 느려 데이타가 넘어오지 않을시 agentCd 값 없으면 리턴처리
        if ("".equals(agentCd) || "".equals(socCd) || "".equals(reqBuyTypeCd) || "".equals(operTypeCd)) {
            return null;
        }

        //약정이 있는데 sprt_tp 값이 null 로 들어간 것도 있음.
        //약정이 있는데 할인유형이 없는 경우에는 임의로 KD 세팅
        if ("MM".equals(reqBuyTypeCd) && !"0".equals(agrmTrm) && "".equals(sprtTp)) {
            request.setSprtTp("KD"); //할인유형
        }

        //DATA SET
        request.setOldYn(oldYn); //중고여부
        //request.setAgentCd(agentCd); //조직코드

        //1. 단말 및 요금 조회
        MspSaleSubsdMstResponse saleSubsdMstResponse = new MspSaleSubsdMstResponse();
        if (StringUtils.hasText(modelId) && StringUtils.hasText(socCd)) {
            saleSubsdMstResponse = this.getMspModelRateInfo(request);
            if (saleSubsdMstResponse != null) {
                response.setHndstAmt(saleSubsdMstResponse.getHndstAmt()); //단말금액(vat포함)
                response.setBaseAmt(saleSubsdMstResponse.getBaseAmt()); //요금기본료
            }
        }

        //2. 판매정책코드 재조회 : 2026.07.10
        //판매정책이 단말의 할부와 현금(일시납) 구분이 있어서 프론트에서 판매정책코드 호출 시점이 애매하므로 재조회하여 처리하도록 함.
        //USIM 에도 약정기간이 추가되어도 문제는 없어보임.
        //단말할부 parameter 는 modelMonthly
        String rateCd = "01";
        if ("UU".equals(request.getReqBuyTypeCd())) {
            rateCd = "02";
        }
        ProductInfoRequest productInfoRequest = new ProductInfoRequest();
        productInfoRequest.setPlcyTypeCd("N"); //판매유형코드 : N (KTM 모바일)
        productInfoRequest.setRateCd(rateCd); //01:단말,02:유심 - 요금제코드 아님. 주의!
        productInfoRequest.setAgentCd(agentCd);
        productInfoRequest.setSprtTp(sprtTp);
        productInfoRequest.setPrdtSctnCd(prdtSctnCd);
        productInfoRequest.setPrdtId(modelId);
        productInfoRequest.setOperTypeCd(operTypeCd);
        productInfoRequest.setModelMonthly(modelMonthly);
        log.debug("productInfoRequest >> setRateCd: {}, setAgentCd: {}, setSprtTp: {}, setPrdtSctnCd: {}, setPrdtId: {}, setModelMonthly: {}",
            rateCd,
            agentCd,
            sprtTp,
            prdtSctnCd,
            modelId,
            modelMonthly);
        List<MspSalePlcyMstInfoDto> salePlcyList = new ArrayList<>();
        if (!ReqBuyType.USIM.getCode().equals(reqBuyTypeCd)) {
            salePlcyList = this.getMspSalePlcyMst(productInfoRequest);
        }

        //판매정책코드 조회가 되어야하는데 없는 경우 (예외처리)
        if (salePlcyList != null && !salePlcyList.isEmpty()) {
            salePlcyCd = salePlcyList.getFirst().getSalePlcyCd();
            log.debug("판매정책코드 재조회 >> salePlcyCd[재조회]: {}, salePlcyCd[프론트전달값]: {}" + salePlcyCd, salePlcyList.getFirst().getSalePlcyCd());
            request.setSalePlcyCd(salePlcyCd);
        }

        //3. 지원금 조회
        MspSaleSubsdMstResponse saleSubsdMst = new MspSaleSubsdMstResponse();
        if (StringUtils.hasText(salePlcyCd)) { //판매정책코드가 있는 경우로만 제한함.
            saleSubsdMst = this.getMspSaleSubsdMst(request);
            if (saleSubsdMst != null) {
                salePlcyCd = StringUtil.NVL(saleSubsdMst.getSalePlcyCd(), "");
            }

        }

        if (StringUtils.hasText(salePlcyCd)) {
            //무약정일 경우 단말금액과 요금기본료만 전달하도록 처리 (2026.07.01)
            if ("0".equals(agrmTrm) && !StringUtils.hasText(agrmTrm)) { //무약정
                if ("MM".equals(reqBuyTypeCd)) { //핸드폰
                    if (saleSubsdMst != null && !ObjectUtils.isEmpty(saleSubsdMst)) {
                        response.setHndstAmt(saleSubsdMst.getHndstAmt()); //단말금액(vat포함)
                        response.setSubsdAmt("0"); //공시지원금(vat포함)
                        response.setInstAmt(saleSubsdMst.getHndstAmt()); // 할부원금(vat포함)
                        response.setInstCmsn(saleSubsdMst.getInstCmsn()); //할부수수료(vat포함)
                        response.setAgncySubsdAmt("0"); //대리점보조금max(vat포함)
                        response.setAgncySubsdMax("0"); //대리점보조금(vat포함)
                        response.setSprtTp(""); //지원금유형
                        response.setBaseAmt(saleSubsdMstResponse.getBaseAmt()); //기본료 (vat 포함)
                        response.setDcAmt("0"); //기본할인금액
                        response.setAddDcAmt("0"); //추가할인금액
                    }

                } else { //유심
                    //response.setHndstAmt("0"); //단말금액(vat포함)
                    response.setSubsdAmt("0"); //공시지원금(vat포함)
                    response.setInstAmt("0"); // 할부원금(vat포함)
                    response.setInstCmsn("0"); //할부수수료(vat포함)
                    response.setAgncySubsdAmt("0"); //대리점보조금max(vat포함)
                    response.setAgncySubsdMax("0"); //대리점보조금(vat포함)
                    response.setSprtTp(""); //지원금유형
                    response.setBaseAmt(saleSubsdMst.getBaseAmt()); //기본료 (VAT포함)
                    response.setDcAmt("0"); //기본할인금액
                    response.setAddDcAmt("0"); //추가할인금액
                }
            } else { //약정
                if (saleSubsdMst != null && !ObjectUtils.isEmpty(saleSubsdMst)) {
                    response.setHndstAmt(saleSubsdMst.getHndstAmt()); //단말금액(vat포함)
                    response.setSubsdAmt(saleSubsdMst.getSubsdAmt()); //공시지원금(vat포함)
                    response.setInstAmt(saleSubsdMst.getInstAmt()); // 할부원금(vat포함)
                    response.setInstCmsn(saleSubsdMst.getInstCmsn()); //할부수수료(vat포함)
                    response.setAgncySubsdAmt(saleSubsdMst.getAgncySubsdAmt()); //대리점보조금max(vat포함)
                    response.setAgncySubsdMax(saleSubsdMst.getAgncySubsdMax()); //대리점보조금(vat포함)
                    response.setSprtTp(saleSubsdMst.getSprtTp()); //지원금유형
                    response.setBaseAmt(saleSubsdMst.getBaseAmt()); //기본료 (vat 포함처리)
                    response.setDcAmt(saleSubsdMst.getDcAmt()); //기본할인금액 (vat 포함처리)
                    response.setAddDcAmt(saleSubsdMst.getAddDcAmt()); //추가할인금액 (vat 포함처리)
                } else { //
                    if (StringUtils.hasText(modelMonthly) && !"0".equals(modelMonthly)) { //약정개월은 있는데 판매정책은 조회가 안되고 할부원금 세팅이 안되서 처리. 개선필요.
                        response.setInstAmt(response.getHndstAmt()); // 할부원금(vat포함)
                    }
                }
            }
        } else {
            if (StringUtils.hasText(modelId)) {
                response.setInstAmt(response.getHndstAmt()); // 할부원금(vat포함)
            }
        }
        log.debug("HndstAmt: {}, InstAmt: {}, BaseAmt: {}", response.getHndstAmt(), response.getInstAmt(), response.getBaseAmt());

        //단말할부기간이 없는 경우 (할부원금 초기화 처리)
        if ("0".equals(modelMonthly)) {
            response.setInstAmt("0"); // 할부원금(vat포함) - 이 값이 맞을까????? 고객수납금으로 대체되어야하는데.. 흠..
            response.setInstCmsn("0"); //할부수수료(vat포함)
        }

        //3. USIM 구매의 요금조회 (할인요금이 해당하는 경우)
        if ("UU".equals(reqBuyTypeCd)) {
            MspSaleSubsdMstResponse usimDcamtResponse = new MspSaleSubsdMstResponse();
            if (StringUtils.hasText(socCd)) { //요금제 코드가 있는 경우에만
                usimDcamtResponse = this.getUsimDcamt(request);
            }
            if (usimDcamtResponse != null) {
                response.setDcAmt(usimDcamtResponse.getDcAmt()); //요금 할인 (vat포함)
                response.setBaseAmt(usimDcamtResponse.getBaseAmt()); //요금 기본요금 (vat포함)
            }
        }

        //4. 총할부수수료 산출 ( vat 포함 할부수수료인 instCmsn 과 비교해서 계산을 해봐도 동일한 값이 안나와서 산출식 추가함. ㅠㅠ;)
        String totalInstCmsn = "0"; //총할부수수료
        String instAmt = ""; //할부원금
        //if (saleSubsdMst != null) {
        if (StringUtils.hasText(modelId)) {
            //instAmt = saleSubsdMst.getInstAmt();
            instAmt = response.getInstAmt();
            if (instAmt != null && instAmt.length() > 0 && modelMonthly != null && modelMonthly.length() > 0) {
                Integer instAmtInt = Integer.parseInt(instAmt);
                Integer modelMonthlyInt = Integer.parseInt(modelMonthly); //단말할부기간
                if (instAmtInt > 0 && modelMonthlyInt > 0) { //할부원금과 할부개월이 있는 경우
                    request.setInstAmt(instAmt); //할부원금
                    totalInstCmsn = this.getTotalInstCmsn(request);
                }
            }
        }
        //}
        response.setTotalInstCmsn(totalInstCmsn); //총할부수수료

        //5. 가입비, 유심비 등 조회
        String usimKindsCd = request.getUsimKindsCd();
        PriceJoinUsimRequest priceJoinUsimRequest = new PriceJoinUsimRequest();
        PriceJoinUsimResponse priceJoinUsimResponse = new PriceJoinUsimResponse();
        priceJoinUsimRequest.setOrgnId(agentCd); //조직코드
        priceJoinUsimRequest.setReqBuyTypeCd(reqBuyTypeCd); //상품유형
        priceJoinUsimRequest.setOperTypeCd(operTypeCd); //가입유형 : NAC3 / MNP3 / HCN3
        priceJoinUsimRequest.setDataType(request.getDataType()); //요금제 조회에서 리턴된 데이터유형 LTE / 5G 등
        priceJoinUsimRequest.setUsimKindsCd(usimKindsCd); //유심종류 : 유심 해당없음(06) , LTE일반유심(02) , 5G일반유심(07) , NFC유심(08) , eSIM(09)

        //유심종류 스마트 화면값에서 공통코드 값으로 변경처리 - 저장할때도 처리해야해서 중복은 되네 (front 에서 이 값으로 처리하다가 문제있을 수 있어 return 은 하지않는 걸로)
        //if ("".equals(usimKindsCd)) {
        //    usimKindsCd = "06";
        //} else if ("01".equals(usimKindsCd) && "LTE".equals(prdtSctnCd)) { //일반유심 - 스마트 화면에서 값
        //    usimKindsCd = "02"; // RCP2035 기준코드
        //} else if ("01".equals(usimKindsCd) && !"LTE".equals(prdtSctnCd)) { //5G유심 - 스마트 화면에서 값
        //    usimKindsCd = "07"; // RCP2035 기준코드
        //} else if ("02".equals(usimKindsCd)) { //NFC유심 - 스마트 화면에서 값
        //    usimKindsCd = "08"; // RCP2035 기준코드
        //} else if ("09".equals(request.getUsimKindsCd())) { //eSIM - 스마트 화면에서 값
        //    usimKindsCd = "09"; //
        //}
        //2026.07.20 주석 - 고객은 일반유심인지 NFC유심인지 알수 없을 수 있어서 제거요청 처리
        //if ("".equals(usimKindsCd)) {
        //    usimKindsCd = "06"; // RCP2035 기준코드
        //} else if ("01".equals(request.getUsimKindsCd())) { //일반유심 - 스마트 화면에서 값
        //    if ("LTE".equals(request.getPrdtSctnCd())) {
        //        usimKindsCd = "02"; // RCP2035 기준코드
        //    } else {
        //        usimKindsCd = "07"; // RCP2035 기준코드
        //    }
        //} else if ("02".equals(request.getUsimKindsCd())) { //NFC유심 - 스마트 화면에서 값
        //    usimKindsCd = "08"; // RCP2035 기준코드
        //} else if ("09".equals(request.getUsimKindsCd())) { //eSIM - 스마트 화면에서 값
        //    usimKindsCd = "09"; // RCP2035 기준코드
        //}

        //2026.07.21 변경
        if ("".equals(usimKindsCd) || "06".equals(usimKindsCd) || "N".equals(usimKindsCd)) {
            usimKindsCd = "06"; // RCP2035 기준코드
        } else if ("09".equals(request.getUsimKindsCd())) { //eSIM - 스마트 화면에서 값
            usimKindsCd = "09"; // RCP2035 기준코드
        } else { //유심 - 스마트 화면에서 유심구매
            //if ("LTE".equals(request.getPrdtSctnCd()) || "LTE".equals(request.getDataType())) {
            if ("LTE".equals(request.getDataType())) {
                usimKindsCd = "02"; // RCP2035 기준코드
            } else {
                usimKindsCd = "07"; // RCP2035 기준코드 - 개발기준 고객포탈에서는 데이타 없음. (5G유심)
            }
        }
        priceJoinUsimRequest.setUsimKindsCd(usimKindsCd);

        //유심 및 가입비 조회
        priceJoinUsimResponse = this.getUsimBasJoinPrice(priceJoinUsimRequest);
        if (priceJoinUsimResponse != null) { //기기변경 eSIM 없음 등등으로 조건 추가
            log.debug("priceJoinUsimResponse >> getJoinPrice: {}, getJoinIsPay: {}, getSimPrice: {}, getSimIsPay: {}, getNfcSimIsPay: {}",
                priceJoinUsimResponse.getJoinPrice(),
                priceJoinUsimResponse.getJoinIsPay(),
                priceJoinUsimResponse.getSimPrice(),
                priceJoinUsimResponse.getSimIsPay(),
                priceJoinUsimResponse.getNfcSimIsPay());

            //가입비 결과 처리
            String joinIsPay = priceJoinUsimResponse.getJoinIsPay(); //가입비 납부여부
            String joinPrice = priceJoinUsimResponse.getJoinPrice(); //가입비
            //String joinPriceTypeCd = "I"; //가입비납부유형코드 R:완납, I:분납 , P:면제
            //String joinPayMthdCd = "1"; //가입비납부방법코드 : 가입비 기본값은 면제, 그외에는 분납 :: 1(면제) 2(일시납) 3(3개월분납)
            log.debug("joinIsPay: {}, joinPrice: {}, joinPriceTypeCd: {}, joinPayMthdCd: {}", joinIsPay, joinPrice, joinPriceTypeCd, joinPayMthdCd);

            //reqBuyTypeCd :: 휴대폰 - AppformController.java
            //if ("HDN3".equals(request.getOperTypeCd()) || "HCN3".equals(request.getOperTypeCd())) {
            //    joinPrice = "0"; //가입비
            //    joinPriceTypeCd = "P"; //가입비 납부유형코드 : 면제
            //    joinPayMthdCd = "1"; //가입비 납부방법 : 면제
            //} else {
            //    if ("N".equals(joinIsPay)) { //가입비 납부대상 아님.
            //        joinPrice = "0"; //가입비
            //        joinPriceTypeCd = "R"; //가입비 납부유형코드 : 완납
            //        joinPayMthdCd = "2"; //가입비 납부방법 : 일시납
            //    } else {
            //        if (this.getStringToLong(joinPrice) > 0L) { //가입비 있음
            //            joinPayMthdCd = "3"; //가입비 납부방법 : 분납
            //        } else { //가입비 없음
            //            joinPrice = "0"; //가입비
            //            joinPayMthdCd = "2"; //가입비 납부방법 : 일시납
            //        }
            //    }
            //}

            //가입비에 따른 처리
            if ("HDN3".equals(request.getOperTypeCd()) || "HCN3".equals(request.getOperTypeCd())) {
                joinPrice = "0"; //가입비
                joinPriceTypeCd = "P"; //가입비 납부유형코드 : 면제
                joinPayMthdCd = "1"; //가입비 납부방법 : 면제
            } else {
                if ("N".equals(joinIsPay)) { //가입비 납부대상 아님.
                    joinPrice = "0"; //가입비
                } else {
                    if (this.getStringToLong(joinPrice) == 0L) { //가입비 없음
                        joinPrice = "0"; //가입비
                    }
                }
            }

            //가입비납부방법을 화면에서 처리 추가 - 2026.07.14
            if (StringUtils.hasText(joinPayMthdCd)) {
                if ("2".equals(joinPayMthdCd)) { //일시납
                    joinPriceTypeCd = "R"; //완납
                } else if ("3".equals(joinPayMthdCd)) { //분납
                    joinPriceTypeCd = "I"; //분납
                }
            }
            log.debug("가입비 결과 >> joinPriceTypeCd: {}, joinPayMthdCd: {}, joinPrice: {}", joinPriceTypeCd, joinPayMthdCd, joinPrice);

            //유심 가격조회 결과 처리
            String simIsPay = "N"; //일반유심비용 납부여부 ( Y:납부, N:면제 )
            String nfcSimIsPay = "N"; //NFC유심비용 납부여부 ( Y:납부, N:면제 )
            String simPrice = "0";
            String usimPrice = ""; //유심비
            String usimPayMthdCd = ""; //유심납부방법 : R (즉납)  B (후청구)  N (비구매)
            String usimPriceTypeCd = ""; //유심납부유형 : B (정기)  I (즉납)

            if (priceJoinUsimResponse != null && StringUtils.hasText(usimKindsCd) && !"06".equals(usimKindsCd)) {
                simIsPay = priceJoinUsimResponse.getSimIsPay();
                nfcSimIsPay = priceJoinUsimResponse.getNfcSimIsPay();
                simPrice = priceJoinUsimResponse.getSimPrice();
            }

            //if (priceJoinUsimResponse != null) {
            //    if (StringUtils.hasText(usimKindsCd) && !"06".equals(usimKindsCd)) {
            //        simIsPay = priceJoinUsimResponse.getSimIsPay();
            //        nfcSimIsPay = priceJoinUsimResponse.getNfcSimIsPay();
            //        simPrice = priceJoinUsimResponse.getSimPrice();
            //    }
            //
            //    조회된 유심비, 납부방법코드, 납부유형코드 분기
            //    if ("06".equals(usimKindsCd)) {
            //        usimPrice = "0";
            //    } else {
            //        if ("N".equals(simIsPay) || "N".equals(nfcSimIsPay)) { //면제
            //            usimPrice = "0";
            //        } else {
            //            if ("09".equals(request.getUsimKindsCd())) { //eSIM - 스마트 화면에서 값
            //                usimPayMthdCd = request.getUsimPayMthdCd(); //납부인 경우 유심가격이 있을 경우 화면에서 선택한 값
            //                usimPriceTypeCd = request.getUsimPriceTypeCd(); //납부인 경우 유심가격이 있을 경우 화면에서 선택한 값
            //            } else {
            //                usimPrice = simPrice; //유심가격
            //                usimPayMthdCd = request.getUsimPayMthdCd(); //납부인 경우 유심가격이 있을 경우 화면에서 선택한 값
            //                usimPriceTypeCd = request.getUsimPriceTypeCd(); //납부인 경우 유심가격이 있을 경우 화면에서 선택한 값
            //            }
            //        }
            //    }
            //}

            //USIM 관련항목 세팅 (2026.07.27)
            usimPrice = simPrice; //유심가격
            usimPayMthdCd = request.getUsimPayMthdCd(); //납부인 경우 유심가격이 있을 경우 화면에서 선택한 값
            usimPriceTypeCd = request.getUsimPriceTypeCd(); //납부인 경우 유심가격이 있을 경우 화면에서 선택한 값

            response.setJoinPrice(joinPrice); //가입비
            response.setJoinIsPay(joinIsPay); //가입비 납부여부 ( Y:납부, N:면제 )
            response.setJoinPriceTypeCd(joinPriceTypeCd); //가입비납부유형코드	o 코드관리(M포탈) (R:완납, I:분납 , P:면제)
            response.setJoinPayMthdCd(joinPayMthdCd); //가입비납부방법코드 o 코드관리(M포탈) (1 : 면제 , 2 : 일시납, 3 : 3개월분납))

            response.setSimIsPay(simIsPay); //일반유심비용 납부여부 ( Y:납부, N:면제 ) - front
            response.setNfcSimIsPay(nfcSimIsPay); //NFC유심비용 납부여부 ( Y:납부, N:면제 ) - front
            response.setSimPrice(simPrice); //유심비 - front

            response.setUsimPrice(usimPrice); //유심가격
            response.setUsimPayMthdCd(usimPayMthdCd); //유심비 납부방법코드 : R (즉납)  B (후청구)  N (비구매)
            response.setUsimPriceTypeCd(usimPriceTypeCd); //유심비 납부유형코드 : B (정기)  I (즉납)
        }

        log.debug(
            "@JOIN >> joinIsPay:{}, joinPrice:{}, joinPriceTypeCd:{}, joinPayMthdCd:{}",
            response.getJoinIsPay(),
            response.getJoinPrice(),
            response.getJoinPriceTypeCd(),
            response.getJoinPayMthdCd());

        log.debug(
            "@SIM >> getSimPrice:{}, getSimIsPay:{}, getNfcSimIsPay:{}, usimPrice: {}, usimPayMthdCd: {}, usimPriceTypeCd: {}",
            response.getSimPrice(),
            response.getSimIsPay(),
            response.getNfcSimIsPay(),
            response.getUsimPrice(),
            response.getUsimPayMthdCd(),
            response.getUsimPriceTypeCd());

        log.debug(
            "@단말 및 요금 >> HndstAmt(): {}, SubsdAmt(): {}, InstAmt(): {}, InstCmsn(): {}, AgncySubsdAmt(): {}, AgncySubsdMax(): {}, SprtTp(): {}, BaseAmt(): {}, DcAmt(): {}, AddDcAmt(): {}",
            response.getHndstAmt(),
            response.getSubsdAmt(),
            response.getInstAmt(),
            response.getInstCmsn(),
            response.getAgncySubsdAmt(),
            response.getAgncySubsdMax(),
            response.getSprtTp(),
            response.getBaseAmt(),
            response.getDcAmt(),
            response.getAddDcAmt()
        );

        // 평생할인 프로모션 요금 대체 처리
        try {
            List<String> prmtIds = this.getDisPrmtId(request);
            if (prmtIds != null && prmtIds.size() > 0) {
                Long promoBaseAmt = newChangeMspReadMapper.selectPromoBaseAmt(prmtIds);
                if (promoBaseAmt != null) {
                    response.setPrmtAmt(String.valueOf(promoBaseAmt));
                    log.info("평생할인 프로모션 가격 세팅 완료: prmtIds={}, prmtAmt={}", prmtIds, promoBaseAmt);
                }
            }
        } catch (Exception e) {
            log.error("평생할인 프로모션 기본료 대체 중 오류 발생", e);
        }

        return response;
    }

    /**
     * 총할부수수료 산출
     */
    public String getTotalInstCmsn(MspSaleSubsdMstRequest request) {
        String totalInstCmsnStr = "0";
        Integer instAmt = Integer.parseInt(request.getInstAmt());
        Integer modelMonthly = Integer.parseInt(request.getModelMonthly());
        Integer totalInstCmsn;
        if (1 != modelMonthly) {

            BigDecimal bgYearRate = new BigDecimal("0.059"); //고정...
            BigDecimal bgInstAmt = new BigDecimal(instAmt + "");    //단말기 할부원금
            BigDecimal bgModelMonthly = new BigDecimal(modelMonthly + "");  //단말기 할부 기간
            BigDecimal bgMonth = BigDecimal.valueOf(12);
            BigDecimal bgTemp = BigDecimal.ONE;
            BigDecimal bgPow;
            BigDecimal bgTemp2 = bgYearRate.divide(bgMonth, 38, RoundingMode.HALF_UP).add(bgTemp); //(1+0.059/12)
            bgPow = bgTemp2.pow(modelMonthly).setScale(38, RoundingMode.HALF_UP);  //POWER((1+0.059/12), 24)
            BigDecimal bgPow2 = bgPow.subtract(bgTemp); //( P-1 )

            //round(    1094500 * 0.059 / 12 * P / ( P-1 ),0)
            BigDecimal bgRound1 = bgInstAmt.multiply(bgYearRate).divide(bgMonth, 38, RoundingMode.HALF_UP).multiply(bgPow)
                .divide(bgPow2, 0, RoundingMode.DOWN);

            //x 할부개월수 – 할부원금
            totalInstCmsn = bgRound1.multiply(bgModelMonthly).subtract(bgInstAmt).intValue();
        } else {
            totalInstCmsn = 0;
        }
        totalInstCmsnStr = totalInstCmsn.toString();

        return totalInstCmsnStr;
    }


    //가격정보조회 (단말,요금,지원금) - 출고가, 기본요금, 공시지원금 등
    public MspSaleSubsdMstResponse getMspModelRateInfo(MspSaleSubsdMstRequest request) {
        return productInfoReadMapper.selectMspModelRateInfo(request);
    }

    //가격정보조회 (단말,요금,지원금) - 출고가, 기본요금, 공시지원금 등
    public MspSaleSubsdMstResponse getMspSaleSubsdMst(MspSaleSubsdMstRequest request) {
        return productInfoReadMapper.selectMspSaleSubsdMst(request);
    }

    //가격정보 (무약정)
    public MspSaleSubsdMstResponse getUsimDcamt(MspSaleSubsdMstRequest request) {
        return productInfoReadMapper.selectUsimDcamt(request);
    }

    //가격정보조회 (가입비, 유심비)
    @SuppressWarnings("PMD.EmptyControlStatement")
    public PriceJoinUsimResponse getUsimBasJoinPrice(PriceJoinUsimRequest request) {
        PriceJoinUsimResponse responseJoinUsim = new PriceJoinUsimResponse();
        //Request
        String orgnId = request.getOrgnId(); //조직코드
        String reqBuyTypeCd = request.getReqBuyTypeCd(); //상품유형
        String operTypeCd = request.getOperTypeCd(); //가입유형 : NAC3 / MNP3 / HCN3
        String dataType = StringUtil.NVL(request.getDataType(), "LTE"); //요금제 조회에서 리턴된 데이터유형 LTE / 5G 등
        String usimKindsCd = request.getUsimKindsCd(); //유심종류 : 유심 해당없음(06) , LTE일반유심(02) , 5G일반유심(08) , NFC유심(08) , eSIM(09)
        //String priceGubun = ""; //가입유형+데이타유형 >> 실제 쿼리에서 사용하는 parameter
        //if (usimKindsCd.equals("06")) { //유심 구매하지 않는 경우
        //    usimKindsCd = "";
        //}
        //if (dataType.equals("")) {
        //    dataType = "LTE"; //혹시 값이 넘어오지 않는다면
        //}

        //Response
        String joinPrice = "0"; //가입비
        String joinIsPay = "Y"; //가입비 납부여부 ( Y:납부, N:면제 )
        String simPrice = "0"; //유심가격
        String simIsPay = "Y"; //일반유심비용 납부여부 ( Y:납부, N:면제 )
        String nfcSimIsPay = "Y"; //NFC유심비용 납부여부 ( Y:납부, N:면제 )

        if ("09".equals(usimKindsCd)) { //selectJoinUsimPrice 의 parameter 처리를 위해 변환
            dataType = "ESIM";
        }
        request.setPriceGubun(operTypeCd + dataType);

        //1. 가입비 및 유심비 조회
        PriceJoinUsimResponse response = productInfoReadMapper.selectJoinUsimPrice(request);
        if (response == null) { //기기변경 eSIM 예외상황
            responseJoinUsim.setJoinPrice(joinPrice); //가입비
            responseJoinUsim.setJoinIsPay(joinIsPay); //가입비 납부여부 ( Y:납부, N:면제 )
            responseJoinUsim.setSimPrice(simPrice); //유심가격
            responseJoinUsim.setSimIsPay(simIsPay); //일반유심비용 납부여부 ( Y:납부, N:면제 )
            responseJoinUsim.setNfcSimIsPay(nfcSimIsPay); //NFC유심비용 납부여부 ( Y:납부, N:면제 )

            return responseJoinUsim;
        } else {
            joinPrice = response.getJoinPrice();
            simPrice = response.getSimPrice();
        }
        log.debug("@가입비 및 유심비 조회 >> joinPrice: {}, simPrice: {}", joinPrice, simPrice);

        //공통코드 조회
        CommonCodesRequest crdRequest = CommonCodesRequest.of(List.of("DirectUsimPrice",
            "Constant",
            "MarketJoinUsimPriceInfo"), true, true);
        CommonCodeGroups commonCodeCrdGroups = commonCodeReader.getCommonCodes(crdRequest); //M전산,고객포탈,스마트에서 CRD 그룹코드를 모두 조회

        //2. USIM 구매인 경우
        if ("UU".equals(reqBuyTypeCd)) { //reqBuyTypeCd : USIM
            //고객포탈 공통코드에서 USIM 가격 가져오기
            if ("02".equals(usimKindsCd)) { //LTE일반유심 선택
                simPrice = commonCodeCrdGroups.getSimple("DirectUsimPrice", "BASE").title(); //dtl_cd_nm 값이 가격
                //simPrice = NmcpServiceUtils.getCodeNm(GROUP_CODE_DIRECT_USIM_PRICE, DTL_CD_OBJ_BASE);
            } else if ("07".equals(usimKindsCd)) { //5G일반유심 선택 - 5G 요금?단말?일때 07로 처리하는 부분 추가필요함.
                simPrice = commonCodeCrdGroups.getSimple("DirectUsimPrice", "5G").title(); //dtl_cd_nm 값이 가격
                //simPrice = NmcpServiceUtils.getCodeNm(GROUP_CODE_DIRECT_USIM_PRICE, DTL_CD_OBJ_BASE);
            } else if ("08".equals(usimKindsCd)) { //NFC유심 선택
                simPrice = commonCodeCrdGroups.getSimple("DirectUsimPrice", "NFC").title(); //dtl_cd_nm 값이 가격
                //simPrice = NmcpServiceUtils.getCodeNm(GROUP_CODE_DIRECT_USIM_PRICE, DTL_CD_USIM_NFC);
            }

            //if (!"09".equals(usimKindsCd) && "02".equals(usimKindsCd)) { //LTE일반유심 선택
            //    simPrice = commonCodeCrdGroups.getSimple("DirectUsimPrice", "BASE").title(); //dtl_cd_nm 값이 가격
            //    //simPrice = NmcpServiceUtils.getCodeNm(GROUP_CODE_DIRECT_USIM_PRICE, DTL_CD_OBJ_BASE);
            //} else if (!"09".equals(usimKindsCd) && "07".equals(usimKindsCd)) { //5G일반유심 선택 - 5G 요금?단말?일때 07로 처리하는 부분 추가필요함.
            //    simPrice = commonCodeCrdGroups.getSimple("DirectUsimPrice", "BASE").title(); //dtl_cd_nm 값이 가격
            //    //simPrice = NmcpServiceUtils.getCodeNm(GROUP_CODE_DIRECT_USIM_PRICE, DTL_CD_OBJ_BASE);
            //} else if ("08".equals(usimKindsCd)) { //NFC유심 선택
            //    simPrice = commonCodeCrdGroups.getSimple("DirectUsimPrice", "NFC").title(); //dtl_cd_nm 값이 가격
            //    //simPrice = NmcpServiceUtils.getCodeNm(GROUP_CODE_DIRECT_USIM_PRICE, DTL_CD_USIM_NFC);
            //}

            log.debug("simPrice: {}", simPrice);
        }

        //3. eSIM
        if ("09".equals(usimKindsCd)) {
            //joinIsPay = NmcpServiceUtils.getCodeNm("Constant", "eSimJoinIsPay");
            //2026.07.20 주석처리
            //joinIsPay = commonCodeCrdGroups.getSimple("Constant", "eSimJoinIsPay").code();
            //if ("Y".equals(joinIsPay)) { //가입비 납부인 경우
            //    joinIsPay = "Y";
            //} else {
            //    joinIsPay = "N";
            //}

            simIsPay = commonCodeCrdGroups.getSimple("Constant", "eSimIsPay").code();
            //simIsPay = NmcpServiceUtils.getCodeNm("Constant", "eSimIsPay");
            if ("Y".equals(simIsPay)) {
                simIsPay = "Y";
            } else {
                simIsPay = "N";
            }
        } else {
            /** 위탁온라인 가입비/유심비 면제 여부 */
            //public static final String GROUP_CODE_MARKET_JOIN_USIM_INFO = "MarketJoinUsimPriceInfo";

            //납부로 기본값 설정
            joinIsPay = "Y"; //가입비 납부인 경우
            simIsPay = "Y";
            nfcSimIsPay = "Y";

            Optional<CommonCodeData> simPriceObj = commonCodeCrdGroups.get("MarketJoinUsimPriceInfo", orgnId);
            //NmcpCdDtlDto simPriceObj = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_MARKET_JOIN_USIM_INFO, orgnId);
            if (simPriceObj.isPresent()) {
                //고객단계에서 가입비를 화면으로 일시불(현금완납), 3개월분납 선택할 수 있도록 변경하여 주석처리함.
                //if (simPriceObjCode != null) {
                //if ("N".equals(simPriceObj.get().detail().etcValue1())) {
                //    joinIsPay = "N";
                //} else {
                //    joinIsPay = "N";
                //}

                //2026.07.20 - 주석 >> 화면값으로 처리
                //if ("N".equals(simPriceObj.get().detail().etcValue2())) {
                //    simIsPay = "N";
                //} else {
                //    simIsPay = "N";
                //}

                //2026.07.20 - 주석 >> 화면값으로 처리
                //if ("N".equals(simPriceObj.get().detail().etcValue3())) {
                //    nfcSimIsPay = "N";
                //} else {
                //    nfcSimIsPay = "N";
                //}
            }
            log.debug("위탁온라인 가입비/유심비 면제 여부  >> joinIsPay:{}, joinPrice: {}, simIsPay: {}, simPrice: {}", joinIsPay, joinPrice, simIsPay, simPrice);
            log.debug("가입비/유심비 구분  >> joinIsPay:Y:납부, N:면제, simIsPay: Y:납부, N:면제, nfcSimIsPay: Y:납부, N:면제");
        }

        //유심 비구매 처리 (예상납부는 이 부분에서 처리?)
        if ("".equals(usimKindsCd) || "06".equals(usimKindsCd)) {
            simPrice = "0";
            simIsPay = "";
            nfcSimIsPay = "";
        }

        response.setJoinPrice(joinPrice); //가입비
        response.setJoinIsPay(joinIsPay); //가입비 납부여부 ( Y:납부, N:면제 )
        response.setSimPrice(simPrice); //유심가격
        response.setSimIsPay(simIsPay); //일반유심비용 납부여부 ( Y:납부, N:면제 )
        response.setNfcSimIsPay(nfcSimIsPay); //NFC유심비용 납부여부 ( Y:납부, N:면제 )

        return response;
    }

    //매장재고 조회 - 휴대폰 목록 추출 (postgre)
    public List<CategoryInfoDto> getPhoneInventoryList(@Valid ProductInventoryRequest request) {
        String agentCd = request.getAgentCd(); //화면에서 선택한 대리점코드
        //최초진입할 때는 넘어와서 휴대폰 목록조회를 하지만, 작성 시 여전히 선택하지 않을 때는 조회해야하기 때문에 추가
        if (!StringUtils.hasText(agentCd)) { //최초진입 시 선택한 대리점이 없을 경우에 대한 처리
            AgentInfoRequest agentInfoRequest = new AgentInfoRequest();
            agentInfoRequest.setShopOrgnId(AuthenticationUtils.getShopCode());
            List<AgentInfoResponse> agentInfoResponseList = formCommService.getAgentList(agentInfoRequest);
            if (agentInfoResponseList != null && agentInfoResponseList.size() > 0) {
                agentCd = agentInfoResponseList.get(0).getOrgnId(); //판매점조직코드로 가져온 대리점목록에서 첫번째꺼로 세팅
            }
        }

        //사용자가 선택한 대리점 조직이 아니라, 선택한 대리점의 KT조직으로 재고관리 (2026-06-17)
        String ktOrgId = ""; //화면에서 선택한 대리점코드에 매칭된 KT조직코드
        Optional<AgencyCache> agentInfo = agencyCacheReader.getAgency(agentCd);
        if (agentInfo.isPresent()) {
            ktOrgId = agentInfo.get().ktOrganizationId();
            request.setAgentCd(ktOrgId);//대리점코드
        }
        //parameter : KT조직코드, 단말코드, (선택)단말일련번호
        List<CategoryInfoDto> data = productSmartInfoReadMapper.selectPhoneInventoryList(request);
        return data;
    }

    //매장재고 존재여부 조회 - USIM일련번호 조회 , 휴대폰일련번호 조회 // postgre
    public boolean getPhoneInventoryCount(ProductInventoryRequest request) {
        boolean rtnValue = false;
        String agentCd = request.getAgentCd(); //화면에서 선택한 대리점코드

        //사용자가 선택한 대리점 조직이 아니라, 선택한 대리점의 KT조직으로 재고관리 (2026-06-17)
        String ktOrgId = ""; //화면에서 선택한 대리점코드에 매칭된 KT조직코드
        Optional<AgencyCache> agentInfo = agencyCacheReader.getAgency(agentCd);
        if (agentInfo.isPresent()) {
            ktOrgId = agentInfo.get().ktOrganizationId();
            request.setAgentCd(ktOrgId);//대리점코드
        }

        int rtnCnt = productSmartInfoReadMapper.selectPhoneInventoryCount(request);
        if (rtnCnt > 0) {
            rtnValue = true;
        }
        return rtnValue;
    }

    //신청서 작성 시 입력한 유심 일련번호로 Prod_id 를 가져와서 M전산에서 맞는 이름을 찾아서 저장하도록 함. (2026.07.03)
    public String getUsimModelNm(String reqUsimSn) {
        //1. 유심일련번호로 매장재고를 확인해서 prod_id 를 가져와
        //~> prod_id
        //2. prod_id 로 CMN_INTM_MDL 에서 K 코드 추출
        //~> req_model_nm
        String rtnVal = "";
        log.debug("getUsimModelNm >> reqUsimSn: {} ", reqUsimSn);
        String prodId = productSmartInfoReadMapper.selectProdId(reqUsimSn);
        log.debug("getUsimModelNm >> prodId: {} ", prodId);
        if (StringUtils.hasText(prodId)) {
            String reqModelNm = productInfoReadMapper.selectModelNm(prodId);
            log.debug("getUsimModelNm >> reqModelNm: {} ", reqModelNm);
            if (StringUtils.hasText(reqModelNm)) {
                rtnVal = reqModelNm;
            }
        }
        return rtnVal;
    }

    //요금제,부가서비스,안심보험 카테고리 목록 조회 (postgre)
    public List<CategoryInfoDto> getCategoryList2(CategoryMstRequest request) {
        List<CategoryInfoDto> data = null;
        CategoryType prodCtgTypeCd; //카테고리 조회를 위한 타입 (P:요금제, R:부가서비스, I:안심보험)
        if (StringUtils.hasText(request.getProdCtgTypeCd().getCode())) {
            prodCtgTypeCd = request.getProdCtgTypeCd();
        } else {
            return data;
            //prodCtgTypeCd = prodCtgTypeCd.PRICE;
        }

        request.setProdCtgTypeCd(prodCtgTypeCd);
        data = productSmartInfoReadMapper.selectProductCategoryList(request);
        return data;

    }

    //요금제,부가서비스,안심보험 카테고리 상세 조회 (postgre)
    public List<CategoryInfoDto> getCategoryDetailList(CategoryRelRequest request) {
        List<CategoryInfoDto> data = null;

        //prodCtgTypeCd : 카테고리 구분코드 (P,R,I)
        //prodCtgId : 카테고리 상세코드 (prodCtgTypeCd 에 따른 상세코드)

        //if (!StringUtils.hasText(request.getProdCtgTypeCd()) && !StringUtils.hasText(request.getProdCtgId())) {
        if (!StringUtils.hasText(request.getProdCtgId())) {
            return data;
            //request.setProdCtgTypeCd("P"); //요금제로 조회로 고정
        }

        data = productSmartInfoReadMapper.selectProductCategoryDetailList(request);
        return data;
    }

    //부가서비스 목록 조회 (신규/변경의 신규가입 및 번호이동 그리고 서비스변경)
    public List<MsfRequestAdditionResponse> getAdditionList(MsfRequestAdditionRequest request) {
        List<MsfRequestAdditionResponse> msfRequestAdditionResponseList = new ArrayList<>();
        List<MspAdditionDto> mspAdditionDtoList = new ArrayList<>();
        List<MspAdditionDto> mcpAdditionDtoList = new ArrayList<>();

        MsfRequestAdditionResponse msfRequestAdditionResponse = new MsfRequestAdditionResponse();
        msfRequestAdditionResponse.setFreeAddition(new ArrayList<>());
        msfRequestAdditionResponse.setPaidAddition(new ArrayList<>());
        msfRequestAdditionResponse.setDailyAddition(new ArrayList<>());

        List<String> prodCtgIdList = request.getCategoryMstRequest().getProdCtgId();
        //List<CategoryType> prodCtgIdList = condition.getCategoryMstRequest().getProdCtgId();
        List<CategoryInfoDto> categoryInfoDtoListAll = new ArrayList<CategoryInfoDto>();
        CategoryRelRequest categoryRelRequest = new CategoryRelRequest();

        //요청한 부가서비스 그룹코드로
        //스마트 관리자에 설정된 부가서비스 상품 목록 조회해오기
        if (!ObjectUtils.isEmpty(prodCtgIdList)) {
            prodCtgIdList.forEach(id -> {
                categoryRelRequest.setProdCtgTypeCd("R"); //R:부가서비스
                categoryRelRequest.setProdCtgId(id);
                List<CategoryInfoDto> categoryInfoDtoList = this.getCategoryDetailList(categoryRelRequest);
                categoryInfoDtoListAll.addAll(categoryInfoDtoList);
            });
        }

        //조회된 상품목록을 List 에 담기
        if (!categoryInfoDtoListAll.isEmpty()) {
            //M전산에서 조회하기
            request.setProductCategoryInfoDtoList(categoryInfoDtoListAll);
            mspAdditionDtoList = this.getMspAdditionList(request);
            msfRequestAdditionResponse.setFreeAndPaidSorted(mspAdditionDtoList, categoryInfoDtoListAll);

            msfRequestAdditionResponseList.add(msfRequestAdditionResponse);

            if (localTest) {
                mcpAdditionDtoList = getLocalDailyAdditionList();
            } else {
                request.setProductCategoryInfoDtoList(categoryInfoDtoListAll);
                mcpAdditionDtoList = this.getMcpAdditionList(request);
            }
            msfRequestAdditionResponse.setDailyAddition(mcpAdditionDtoList);
            msfRequestAdditionResponse.mergeDailyAdditionIntoDisplayList(categoryInfoDtoListAll);
            msfRequestAdditionResponse.getFreeAddition().sort(Comparator.comparing(MspAdditionDto::getSortOrdr));
            msfRequestAdditionResponse.getPaidAddition().sort(Comparator.comparing(MspAdditionDto::getSortOrdr));
        }

        return msfRequestAdditionResponseList;
    }

    private List<MspAdditionDto> getLocalDailyAdditionList() {
        return List.of(
            createLocalDailyAddition("PL199N109", "함께 쓰는 로밍 4GB(대표)", "30000", "15"),
            createLocalDailyAddition("PL199N120", "함께 쓰는 로밍 8GB(대표)", "40000", "30"),
            createLocalDailyAddition("PL199N122", "함께 쓰는 로밍 12GB(대표)", "60000", "30"),
            createLocalDailyAddition("PL199N126", "Y 함께 쓰는 로밍 5GB(대표)", "18000", "15"),
            createLocalDailyAddition("PL199N129", "Y 함께 쓰는 로밍 9GB(대표)", "24000", "30"),
            createLocalDailyAddition("PL199N132", "Y 함께 쓰는 로밍 13GB(대표)", "36000", "30"),
            createLocalDailyAddition("DATAROM01", "데이터로밍 20MB", "10000", "15"),
            createLocalDailyAddition("DATAROM03", "데이터로밍 100MB", "30000", "15"),
            createLocalDailyAddition("LTEDTROM5", "데이터로밍 300MB", "50000", "15"),
            createLocalDailyAddition("ITGSAFE3G", "중국/일본 알뜰 로밍", "22727", "5")
        );
    }

    private MspAdditionDto createLocalDailyAddition(
        String rateCd,
        String rateNm,
        String baseAmt,
        String usePrd
    ) {
        MspAdditionDto addition = new MspAdditionDto();
        addition.setRateCd(rateCd);
        addition.setRateNm(rateNm);
        addition.setBaseAmt(baseAmt);
        addition.setUsePrd(usePrd);
        addition.setSortOrdr(9999);
        return addition;
    }

    //부가서비스 목록 조회 (기기변경의 가입중 부가서비스 목록조회)
    public List<MsfRequestAdditionResponse> getActiveAdditionList(MspJuoSubInfoRequest request) {
        List<MsfRequestAdditionResponse> msfRequestAdditionResponseList = new ArrayList<>();

        //고객구분, 고객식별번호, 핸드폰번호로 고객조회해서 고식별번호와 핸드폰번호로
        //고객명과 핸드폰번호로 기기변경 조회서비스 호출하여 고객아이디 조회
        String ncn = ""; //계약번호
        //String ctn = ""; //전화번호
        //String custId = ""; //고객아이디
        FormResponse<MspJuoSubInfoResponse> customerInfoResponse = authInfoService.getKtmMemberInfo(request);
        if (customerInfoResponse == null) {
            return msfRequestAdditionResponseList;
        }
        if (customerInfoResponse.resCode().equals("0000")) {
            ncn = customerInfoResponse.resData().getContractNum();
        }

        if (!StringUtils.hasText(ncn)) {
            return msfRequestAdditionResponseList;
        }

        //기기변경인 경우 가입중 부가서비스 목록 조회 ( mcp-api : /mypage/regService ) //계약번호 필요
        @SuppressWarnings("unchecked") //
        List<McpRegServiceDto> regServiceList = mcpApiClient.post(
            "/mypage/regService",
            ncn,
            List.class
        );
        //
        /*@SuppressWarnings("unchecked")
        List<McpRegServiceDto> regServiceList = mspApiDirectRepository.query("/mypage/regService",
            ncn,
            List.class);*/

        MsfRequestAdditionRequest condition = new MsfRequestAdditionRequest(); //
        List<MspAdditionDto> mspAdditionDtoList = new ArrayList<>();
        List<MspAdditionDto> mcpAdditionDtoList = new ArrayList<>();

        MsfRequestAdditionResponse msfRequestAdditionResponse = new MsfRequestAdditionResponse();
        msfRequestAdditionResponse.setFreeAddition(new ArrayList<>()); //무료부가서비스 return
        msfRequestAdditionResponse.setPaidAddition(new ArrayList<>()); //유료부가서비스 return
        msfRequestAdditionResponse.setDailyAddition(new ArrayList<>()); //

        List<CategoryInfoDto> categoryInfoDtoList = regServiceList.stream()
            .map(mcpDto -> {
                CategoryInfoDto categoryDto = new CategoryInfoDto();
                categoryDto.setProdId(mcpDto.getRateCd());
                return categoryDto;
            })
            .collect(Collectors.toList());

        if (!categoryInfoDtoList.isEmpty()) {
            //M전산에서 조회하기
            condition.setProductCategoryInfoDtoList(categoryInfoDtoList);
            mspAdditionDtoList = this.getMspAdditionList(condition);
            msfRequestAdditionResponse.setFreeAndPaid(mspAdditionDtoList);

            //고객포탈에서 부가서비스 조회
            condition.setProductCategoryInfoDtoList(categoryInfoDtoList);
            mcpAdditionDtoList = this.getMcpAdditionList(condition);
            msfRequestAdditionResponse.setDailyAddition(mcpAdditionDtoList);
            msfRequestAdditionResponse.mergeDailyAdditionIntoDisplayList(categoryInfoDtoList);
        }

        msfRequestAdditionResponseList.add(msfRequestAdditionResponse);
        return msfRequestAdditionResponseList;
    }

    //M전산 테이블에서 부가서비스 상세 정보 조회하기
    public List<MspAdditionDto> getMspAdditionList(MsfRequestAdditionRequest condition) {
        List<MspAdditionDto> data = productInfoReadMapper.selectMspAdditionList(condition);
        return data;
    }

    //MCP 테이블에서 부가서비스 상세 정보 조회하기
    public List<MspAdditionDto> getMcpAdditionList(MsfRequestAdditionRequest condition) {
        List<MspAdditionDto> data = productInfoReadMapper.selectMcpAdditionList(condition);
        return data;
    }


    //안심보험 목록 조회
    public List<IntmInsrRelDTO> getInsrProdList(InsrProdRequest request) {
        //IntmInsrRelDTO intmInsrRelDTO = new IntmInsrRelDTO();
        //intmInsrRelDTO.setReqBuyType(request.getReqBuyType());
        //intmInsrRelDTO.setRprsPrdtId(request.getRprsPrdtId());
        //1. M전산에서 안심보험 목록 조회
        //mcp-api 조회
        @SuppressWarnings("unchecked")
        List<IntmInsrRelDTO> insrProdList = mcpApiClient.post(
            "/appform/selectInsrProdList",
            request.getIntmInsrRelDTO(),
            List.class
        );
        //
        /*@SuppressWarnings("unchecked")
        List<IntmInsrRelDTO> insrProdList = (mspApiDirectRepository.query("/appform/selectInsrProdList",
            request.getIntmInsrRelDTO(),
            List.class));*/

        //2. 스마트에서 요금제/부가서비스/안심보험 목록 관리 테이블에서 조회
        CategoryRelRequest categoryRelRequest = new CategoryRelRequest();
        categoryRelRequest.setProdCtgTypeCd("I"); //I:안심보험 카테고리
        categoryRelRequest.setProdCtgId(request.getProdCtgId());
        List<CategoryInfoDto> categoryInfoDtoList = this.getCategoryDetailList(categoryRelRequest);

        List<IntmInsrRelDTO> filteredList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(categoryInfoDtoList)) {
            //3. 스마트에 등록된 안심보험 목록 기준으로 추출
            Set<String> validIds = categoryInfoDtoList.stream()
                .map(CategoryInfoDto::getProdId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

            Map<String, Integer> sortMap = categoryInfoDtoList.stream()
                .collect(Collectors.toMap(
                    CategoryInfoDto::getProdId,
                    CategoryInfoDto::getSortOdrg
                ));

            //4. M전산에서 조회한 안심보험 목록이 스마트에 등록된 안심보험 목록에 포함된 것만 INSR_PROD_CD 기준으로 필터링 처리
            filteredList = insrProdList.stream()
                .filter(insr -> validIds.contains(insr.getInsrProdCd()))
                .sorted(Comparator.comparing(
                    insr -> sortMap.get(insr.getInsrProdCd())
                ))
                .collect(Collectors.toList());
        }

        return filteredList;
    }


    /**
     * M전산에서 조회한 상품(요금.부가서비스.안심보험)을 스마트 카테고리 관리 테이블에서 조회하여 코드추출
     * MSF_RATE_ADSVC_CTG_BAS
     * MSF_RATE_ADSVC_CTG_PROD_REL
     */
    public List<CategoryInfoDto> getCategoryCdList(CategoryInfoRequest categoryInfoRequest) {
        List<CategoryInfoDto> priceCategoryCdList = new ArrayList<>();
        priceCategoryCdList = productSmartInfoReadMapper.selectCategoryCdList(categoryInfoRequest);
        return priceCategoryCdList;
    }

    /**
     * 추출된 카테고리 코드목록으로 코드명을 포함하여 카테고리 목록 구성 ( getCategoryCdList 에 합쳐야할지? )
     * MSF_RATE_ADSVC_CTG_BAS
     */
    public List<CategoryInfoDto> getCategoryInfoList(CategoryInfoRequest categoryInfoRequest) {
        List<CategoryInfoDto> priceCategoryCdList = new ArrayList<>();
        priceCategoryCdList = productSmartInfoReadMapper.selectCategoryList(categoryInfoRequest);
        return priceCategoryCdList;
    }

    /**
     * 카테고리 목록 조회 - 요금제
     */
    public List<CategoryInfoDto> getPriceCategoryList(CategoryMstRequest request) {
        CategoryInfoRequest categoryInfoRequest = new CategoryInfoRequest();
        categoryInfoRequest.setRateAdsvcDivCd("P"); //요금제

        //정책조회
        ProductInfoRequest productInfoRequest = new ProductInfoRequest();
        List<MspSalePlcyMstInfoDto> salePlcyList = this.getMspSalePlcyMst(productInfoRequest);

        //1. 요금제 목록 조회
        productInfoRequest = new ProductInfoRequest();
        productInfoRequest.setReqBuyTypeCd(ReqBuyType.MOBILE);
        //productInfoRequest.setReqBuyTypeCd(request.getReqBuyTypeCd());
        productInfoRequest.setListMspSaleDto(salePlcyList); //판매정책
        List<RateInfoResponse> rateInfoList = this.getPriceList(productInfoRequest);
        List<String> productList = rateInfoList.stream()
            .map(rateInfo -> rateInfo.getRateCd())
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        //2. 등록된 요금제의 카테고리 코드 추출 - 요금제부가서비스카테고리상품관계
        List<CategoryInfoDto> ctgCdList = null;
        List<String> ctgCdList2 = null;
        if (productList != null && productList.size() > 0) {
            categoryInfoRequest.setProductList(productList);
            ctgCdList = this.getCategoryCdList(categoryInfoRequest); //MSF_RATE_ADSVC_CTG_PROD_REL 테이블에서 조회한 카테고리 목록
            ctgCdList2 = ctgCdList.stream()
                .map(categoryInfoDto -> categoryInfoDto.getCtgCd())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
            log.debug("ctgCdList2 : {}", ctgCdList2.size());
        }

        //3. 추출한 요금제 카테고리 코드명 추출 - 요금제부가서비스카테고리기본
        List<CategoryInfoDto> categoryInfoList = null;
        if (ctgCdList2 != null && !ctgCdList2.isEmpty()) {
            categoryInfoRequest.setCtgList(ctgCdList2);
            categoryInfoList = this.getCategoryInfoList(categoryInfoRequest);
            log.debug("categoryInfoList : {}", categoryInfoList.size());
        }

        return categoryInfoList;
    }

    /**
     * 카테고리 목록 조회 - 부가서비스
     */
    public List<CategoryInfoDto> getAdditionCategoryList(CategoryMstRequest request) {
        CategoryInfoRequest categoryInfoRequest = new CategoryInfoRequest();
        categoryInfoRequest.setRateAdsvcDivCd("R"); //부가서비스

        //1. 부가서비스 목록 목록 조회
        MsfRequestAdditionRequest requestAdditionRequest = new MsfRequestAdditionRequest();
        List<MspAdditionDto> mspAdditionList = this.getMspAdditionList(requestAdditionRequest);
        List<String> productList = mspAdditionList.stream()
            .map(additionInfo -> additionInfo.getRateCd())
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        log.debug("productList: {}", productList.size());

        //2. 등록된 요금제의 카테고리 코드 추출 - 요금제부가서비스카테고리상품관계
        List<String> ctgCdStringList = new ArrayList<>();
        if (productList != null && !productList.isEmpty()) {
            categoryInfoRequest.setProductList(productList);
            List<CategoryInfoDto> ctgCdList = this.getCategoryCdList(categoryInfoRequest);
            if (ctgCdList != null) {
                ctgCdStringList = ctgCdList.stream()
                    .map(categoryInfoDto -> categoryInfoDto.getCtgCd())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            }
        }
        log.debug("ctgCdStringList: {}", ctgCdStringList.size());

        //3. 추출한 요금제 카테고리 코드명 추출 - 요금제부가서비스카테고리기본
        List<CategoryInfoDto> categoryInfoList = new ArrayList<>();
        if (!ctgCdStringList.isEmpty()) {
            categoryInfoRequest.setCtgList(ctgCdStringList);
            List<CategoryInfoDto> result = this.getCategoryInfoList(categoryInfoRequest);
            if (result != null) {
                categoryInfoList = result;
            }
        }
        log.debug("categoryInfoList : {}", categoryInfoList.size());

        return categoryInfoList;
    }

    /**
     * 카테고리 목록 조회 - 안심보험
     */
    public List<CategoryInfoDto> getInsrCategoryList(CategoryMstRequest request) {
        CategoryInfoRequest categoryInfoRequest = new CategoryInfoRequest();
        categoryInfoRequest.setRateAdsvcDivCd("I"); //안심보험

        //1. 가입가능한 안심보험 목록 조회
        InsrProdRequest insrProdRequest = new InsrProdRequest();
        insrProdRequest.setReqBuyTypeCd(request.getReqBuyTypeCd().getCode()); //단말:MM , 유심:UU
        insrProdRequest.setRprsPrdtId(request.getRprsPrdtId()); //단말코드
        insrProdRequest.setServiceChangeYn(request.getServiceChangeYn()); //서비스변경은 ASIS 후보 기준으로 추천카테고리 산출
        List<IntmInsrResponse> insrProdList = this.getMspInsrProdList(insrProdRequest);
        // INSR_PROD_CD

        //2. 등록된 안심보험의 카테고리 코드 추출 - 요금제부가서비스카테고리상품관계
        List<String> productList = insrProdList.stream()
            .map(IntmInsrResponse::getInsrProdCd)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());

        List<CategoryInfoDto> ctgCdList = null;
        List<String> ctgCdStringList = null;
        if (!CollectionUtils.isEmpty(productList)) {
            categoryInfoRequest.setProductList(productList);
            ctgCdList = this.getCategoryCdList(categoryInfoRequest);
            ctgCdStringList = ctgCdList.stream()
                .map(categoryInfoDto -> categoryInfoDto.getCtgCd())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }
        //log.debug("ctgCdStringList: {}", ctgCdStringList.isEmpty());

        //3. 추출한 요금제 카테고리 코드명 추출 - 요금제부가서비스카테고리기본
        List<CategoryInfoDto> categoryInfoList = null;
        if (!CollectionUtils.isEmpty(ctgCdStringList)) {
            categoryInfoRequest.setCtgList(ctgCdStringList);
            categoryInfoList = this.getCategoryInfoList(categoryInfoRequest);
        }
        //log.debug("categoryInfoList : {}", categoryInfoList.size());

        return categoryInfoList;
    }


    /**
     * 안심보험 목록 조회
     */
    @SuppressWarnings("PMD.EmptyControlStatement")
    public List<RateInfoResponse> getInsrListByCategory(ProductInfoRequest request) {
        //List<RateInfoResponse> data = new ArrayList<>();
        if (!StringUtils.hasText(request.getProdCtgId())) {
            //return data; //선택된 요금제 카테고리가 없습니다. >> 최초진입시에는... 어쩌지
        }

        //1. 선택된 카테고리로 요금제 목록 조회
        CategoryRelRequest productCategoryProdRequest = new CategoryRelRequest();
        productCategoryProdRequest.setProdCtgTypeCd("P"); //prodCtgTypeCd : 카테고리 구분코드 (P,R,I)
        productCategoryProdRequest.setProdCtgId(request.getProdCtgId()); //prodCtgId : 카테고리 상세코드 (prodCtgTypeCd 에 따른 상세코드)
        List<CategoryInfoDto> listRateDto = this.getCategoryDetailList(productCategoryProdRequest);
        if (listRateDto != null && !listRateDto.isEmpty()) {
            request.setListRateDto(listRateDto); //결과로 선택된 카테고리로 조회된 요금제 목록 세팅
        }

        //2. 선택된 카테고리에 맞는 요금제를 조건절로 추가하여 M전산에서 요금제 목록 조회
        List<RateInfoResponse> rateInfoList = this.getPriceList(request);

        //3. 카테고리 관리에 등록된 정렬 순서로 처리 : SORT_ODRG
        List<RateInfoResponse> rateInfoListSorted = new ArrayList<>();
        if (listRateDto != null && !listRateDto.isEmpty()) {
            Map<String, Integer> sortMap = listRateDto.stream()
                .collect(Collectors.toMap(
                    CategoryInfoDto::getProdId,
                    CategoryInfoDto::getSortOdrg
                ));

            //.reversed() desc , 기본은 asc
            rateInfoListSorted = rateInfoList.stream()
                .sorted(Comparator
                    .comparing((RateInfoResponse rateInfo) ->
                        sortMap.getOrDefault(rateInfo.getRateCd(), Integer.MAX_VALUE)
                    )
                    .thenComparing(RateInfoResponse::getRateCd)
                )
                .collect(Collectors.toList());
        }

        if (listRateDto != null && !listRateDto.isEmpty()) {
            return rateInfoListSorted;
        } else {
            return rateInfoList;
        }

    }

    /**
     * 요금제 조회 (M전산)
     */
    @SuppressWarnings("PMD.EmptyControlStatement")
    public List<RateInfoResponse> getPriceList(ProductInfoRequest request) {
        List<RateInfoResponse> data = null;
        String agentCd = request.getAgentCd();
        if (agentCd == null || agentCd.equals("")) {
            AgentInfoRequest agentInfoRequest = new AgentInfoRequest();
            List<AgentInfoResponse> agentInfoResponseList = formCommService.getAgentList(agentInfoRequest);
            agentCd = agentInfoResponseList.get(0).getOrgnId();
        }
        request.setAgentCd(agentCd); //대리점코드
        request.setOrgnId(agentCd); //대리점코드
        request.setPayClCd(Constants.SERVICE_TYPE_LATER_PAY); //후불(PO) : 고정값
        request.setPlcyTypeCd("N"); //위탁온라인
        request.setServiceType("P"); //요금제구분 (P:요금제, R:부가서비스)

        if (ReqBuyType.MOBILE.getCode().equals(request.getReqBuyTypeCd().getCode())) { //휴대폰
            data = productInfoReadMapper.selectRateList(request);
        } else { //유심
            if (!StringUtils.hasText(request.getPrdtSctnCd())) {
                request.setPrdtSctnCd("LTE"); // 선택할 값이 없어서 초기값 처리?  LTE / 5G 어떻게 알지????
            }
            data = productInfoReadMapper.selectUsimRateList(request);
        }

        return data;
    }

    /**
     * 안심보험 목록 조회 (M전산)
     */
    @SuppressWarnings("PMD.EmptyControlStatement")
    public List<IntmInsrResponse> getMspInsrProdList(InsrProdRequest request) {
        //M전산에서 안심보험 목록 조회
        List<IntmInsrResponse> insrList = productInfoReadMapper.selectInsrList(request);
        log.debug("안심보험 목록 조회 결과 갯수 : {}", insrList.size());
        return insrList;
    }

    public List<IntmInsrRelDTO> getMspInsrProdList2(InsrProdRequest request) {
        //M전산에서 안심보험 목록 조회
        //mcp-api 조회
        @SuppressWarnings("unchecked")
        List<IntmInsrRelDTO> insrProdList = mcpApiClient.post(
            "/appform/selectInsrProdList",
            request.getIntmInsrRelDTO(),
            List.class
        );

        return insrProdList;
    }

    /**
     * 요금제 목록 조회
     */
    @SuppressWarnings("PMD.EmptyControlStatement")
    public List<RateInfoResponse> getRateListByCategory(ProductInfoRequest request) {
        //List<RateInfoResponse> data = new ArrayList<>();
        if (!StringUtils.hasText(request.getProdCtgId())) {
            //return data; //선택된 요금제 카테고리가 없습니다. >> 최초진입시에는... 어쩌지
        }

        //1. 선택된 카테고리로 요금제 목록 조회
        CategoryRelRequest productCategoryProdRequest = new CategoryRelRequest();
        productCategoryProdRequest.setProdCtgTypeCd("P"); //prodCtgTypeCd : 카테고리 구분코드 (P,R,I)
        productCategoryProdRequest.setProdCtgId(request.getProdCtgId()); //prodCtgId : 카테고리 상세코드 (prodCtgTypeCd 에 따른 상세코드)
        List<CategoryInfoDto> listRateDto = this.getCategoryDetailList(productCategoryProdRequest);
        if (listRateDto != null && !listRateDto.isEmpty()) {
            request.setListRateDto(listRateDto); //결과로 선택된 카테고리로 조회된 요금제 목록 세팅
        }

        //2. 선택된 카테고리에 맞는 요금제를 조건절로 추가하여 M전산에서 요금제 목록 조회
        List<RateInfoResponse> rateInfoList = this.getPriceList(request);

        //3. 카테고리 관리에 등록된 정렬 순서로 처리 : SORT_ODRG
        List<RateInfoResponse> rateInfoListSorted = new ArrayList<>();
        List<RateInfoResponse> rateInfoListSorted2 = new ArrayList<>();
        List<RateInfoResponse> rateInfoListDistinct = null;
        if (listRateDto != null && !listRateDto.isEmpty()) { //카테고리에서 가져온 상품목록
            Map<String, Integer> sortMap = listRateDto.stream()
                .collect(Collectors.toMap(
                    CategoryInfoDto::getProdId,
                    CategoryInfoDto::getSortOdrg
                ));

            //.reversed() desc , 기본은 asc
            rateInfoListSorted = rateInfoList.stream()
                .sorted(Comparator
                    .comparing((RateInfoResponse rateInfo) ->
                        sortMap.getOrDefault(rateInfo.getRateCd(), Integer.MAX_VALUE)
                    )
                    .thenComparing(RateInfoResponse::getRateCd)
                )
                .collect(Collectors.toList());
        } else { //카테고리 목록으로 조회한게 아닌 전체 조회한 경우 distinct 처리할 것
            rateInfoListDistinct = new ArrayList<>(
                rateInfoList.stream()
                    .collect(Collectors.toMap(
                        RateInfoResponse::getRateCd,
                        Function.identity(),
                        (existing, replacement) -> existing
                    ))
                    .values()
            );

            Map<String, String> sortMap2 = rateInfoListDistinct.stream()
                .collect(Collectors.toMap(
                    RateInfoResponse::getRateCd,
                    RateInfoResponse::getRateNm
                ));
            rateInfoListSorted2 = rateInfoListDistinct.stream()
                .sorted(Comparator
                    .comparing((RateInfoResponse rateInfo) ->
                        sortMap2.getOrDefault(rateInfo.getRateCd(), rateInfo.getRateNm())
                    )
                    .thenComparing(RateInfoResponse::getRateNm)
                )
                .collect(Collectors.toList());
        }

        if (listRateDto != null && !listRateDto.isEmpty()) {
            return rateInfoListSorted;
        } else if (rateInfoList != null && !rateInfoList.isEmpty()) {
            return rateInfoListSorted2;
        } else {
            return rateInfoList;
        }
    }

    /**
     * 안심보험 목록 조회
     */
    @SuppressWarnings("PMD.EmptyControlStatement")
    public List<IntmInsrResponse> getInsrProdListByCategory(InsrProdRequest request) {
        List<IntmInsrResponse> mspInsrInfoList = null;
        if (!StringUtils.hasText(request.getProdCtgId())) {
            //return data; //선택된 카테고리가 없는 경우에 대한 처리가 필요한가
        }
        log.debug("안심보험 목록 조회 >> ProdCtgId: {}, ReqBuyTypeCd: {}, RprsPrdtId: {}", request.getProdCtgId(),
            request.getReqBuyTypeCd(),
            request.getRprsPrdtId());

        //추후 삭제해야함.
        String prodCtgId = request.getProdCtgId();
        String reqBuyTypeCd = request.getReqBuyTypeCd();
        //String rprsPrdtId = request.getRprsPrdtId();
        if (!StringUtils.hasText(reqBuyTypeCd)) {
            request.setReqBuyTypeCd("MM");
        }
        //if (reqBuyTypeCd.equals("MM") && !StringUtils.hasText(rprsPrdtId)) {
        //    return mspInsrInfoList;
        //}

        log.debug("안심보험 목록 조회 >> ProdCtgId: {}, ReqBuyTypeCd: {}, RprsPrdtId: {}", request.getProdCtgId(),
            request.getReqBuyTypeCd(),
            request.getRprsPrdtId());

        //1. 선택된 카테고리로 안심보험 목록 조회
        CategoryRelRequest categoryRelRequest = new CategoryRelRequest();
        categoryRelRequest.setProdCtgTypeCd("I"); //prodCtgTypeCd : 카테고리 구분코드 (P,R,I)
        categoryRelRequest.setProdCtgId(prodCtgId); //prodCtgId : 카테고리 상세코드 (prodCtgTypeCd 에 따른 상세코드)
        List<CategoryInfoDto> listInsrDto = this.getCategoryDetailList(categoryRelRequest);
        if (listInsrDto != null && !listInsrDto.isEmpty()) {
            request.setListInsrDto(listInsrDto); //결과로 선택된 카테고리로 조회된 안심보험 목록 세팅
        }

        //2. 안심보험 목록 조회
        log.debug("getMspInsrProdList >> reqBuyTypeCd: {}, rprsPrdtId: {}, ListInsrDto: {}",
            request.getReqBuyTypeCd(),
            request.getRprsPrdtId(),
            request.getListInsrDto() == null ? 0 : request.getListInsrDto().size());
        mspInsrInfoList = this.getMspInsrProdList(request);

        //3. 카테고리 관리에 등록된 정렬 순서로 처리 : SORT_ODRG
        List<IntmInsrResponse> mspInsrInfoListSorted = new ArrayList<>();
        if (listInsrDto != null && !listInsrDto.isEmpty()) {
            Map<String, Integer> sortMap = listInsrDto.stream()
                .collect(Collectors.toMap(
                    CategoryInfoDto::getProdId,
                    CategoryInfoDto::getSortOdrg
                ));

            //.reversed() desc , 기본은 asc
            mspInsrInfoListSorted = mspInsrInfoList.stream()
                .sorted(Comparator
                    .comparing((IntmInsrResponse insrInfo) ->
                        sortMap.getOrDefault(insrInfo.getInsrProdCd(), Integer.MAX_VALUE)
                    )
                    .thenComparing(IntmInsrResponse::getInsrProdCd)
                )
                .collect(Collectors.toList());
        }

        if (listInsrDto != null && !listInsrDto.isEmpty()) {
            return mspInsrInfoListSorted;
        } else {
            log.debug("조회된 안심보험 목록이 없습니다!");
            return mspInsrInfoList;
        }

    }

    /**
     * String TO Long
     */
    private static long getStringToLong(String str) {
        try {
            return (str == null || str.isBlank()) ? 0L : Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * 평생할인 프로모션 아이디 가져오기
     */
    public List<String> getDisPrmtId(MspSaleSubsdMstRequest request) {
        String slsTp = newChangeMspReadMapper.selectDisPrmtSlsTp(request);
        request.setSlsTp(slsTp);

        if ("0".equals(request.getModelMonthly())) {
            request.setModelMonthly("00");
        }
        List<String> prmtIdList = newChangeMspReadMapper.selectDisPrmtId(request);

        return prmtIdList != null ? prmtIdList : new ArrayList<>();
    }

}
