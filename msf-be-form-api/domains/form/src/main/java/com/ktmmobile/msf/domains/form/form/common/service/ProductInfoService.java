package com.ktmmobile.msf.domains.form.form.common.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.code.CategoryType;
import com.ktmmobile.msf.domains.form.common.code.ReqBuyType;
import com.ktmmobile.msf.domains.form.common.dto.IntmInsrRelDTO;
import com.ktmmobile.msf.domains.form.common.dto.McpRegServiceDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryRelRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.InsrProdRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestAdditionRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestAdditionResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspAdditionDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleAgrmMstInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSalePlcyMstInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneSerialRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.ProductInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.RateInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.ProductInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.ProductSmartInfoReadMapper;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductInfoService.class);
    private final McpApiClient mcpApiClient;
    private final ProductInfoReadMapper productInfoReadMapper;
    private final ProductSmartInfoReadMapper productSmartInfoReadMapper;
    private final AuthInfoService authInfoService;

    //판매정책조회 (PRDT_ID 값 k코드가 있으면 판매정책 하나가 조회됨)
    public List<MspSalePlcyMstInfoDto> getMspSalePlcyMstList(ProductInfoRequest request) {
        List<MspSalePlcyMstInfoDto> mspSalePlcyInfo = null;
        List<MspSalePlcyMstInfoDto> distinctList = null;
        request.setPlcyTypeCd("N"); //위탁온라인(N) >> 고정 ~ 상수처리필요함.
        request.setOrgnId(AuthenticationUtils.getAgentCode());
        if (!StringUtils.hasText(request.getSprtTp())) {
            request.setSprtTp("KD"); //할인판매유형 : 단말할인 KD 로 고정
        }
        if (ReqBuyType.UNDEFINED.equals(request.getReqBuyTypeCd())) {
            return null;
        }

        mspSalePlcyInfo = productInfoReadMapper.selectMspSalePlcyMstList(request);
        distinctList = new ArrayList<>(
            mspSalePlcyInfo.stream()
                .collect(Collectors.toMap(
                    MspSalePlcyMstInfoDto::getSalePlcyCd,
                    Function.identity(),
                    (existing, replacement) -> existing
                ))
                .values()
        );

        return distinctList;

        /*if (mspSalePlcyInfo.size() == 0 || distinctList.size() == 0) {
            return FormResponse.of(ResponseMessage.NO_DATA);
        }*/
        // return FormResponse.of(ResponseMessage.SUCCESS, distinctList);
    }

    //할인유형조회
    public List<MspSalePlcyMstInfoDto> getSaleTypeList(ProductInfoRequest request) {
        request.setPlcyTypeCd("N"); //정책유형코드 : N @@확인필요사항@@
        request.setOrgnId(AuthenticationUtils.getAgentCode()); //조직코드는 세션의 대리점코드로 매핑

        List<MspSalePlcyMstInfoDto> salePlcyInfo = productInfoReadMapper.selectSaleTypeList(request);
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
    public List<MspSaleAgrmMstInfoDto> getMspSaleAgrmMstList(ProductInfoRequest request) {
        List<MspSaleAgrmMstInfoDto> data = null;
        //data = productInfoReadMapper.selectMspSaleAgrmMstList(request);
        //약정기간에 무약정도 추가되도록 판매정책코드 조건절 주석 (쿼리는 유지)
        if (StringUtils.hasText(request.getSalePlcyCd())) {
            data = productInfoReadMapper.selectMspSaleAgrmMstList(request);
        }
        return data;
    }

    //휴대폰 할부기간 조회
    public List<PhoneInfoDto> getModelMonthlyList(ProductInfoRequest request) {
        List<PhoneInfoDto> data = null;
        data = productInfoReadMapper.selectModelMonthlyList(request);

        //할부기간에 0개월도 추가되도록 판매정책코드 조건절 주석 (쿼리는 유지)
        /*if (StringUtils.hasText(request.getSalePlcyCd())) {
            data = productInfoReadMapper.selectModelMonthlyList(request);
        }*/
        return data;
    }

    //휴대폰 색상 목록 조회
    public List<PhoneInfoDto> getPrdtColorList(ProductInfoRequest condition) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectPrdtColorList(condition);
        return data;
    }

    //휴대폰 용량 목록 조회
    public List<PhoneInfoDto> getPrdtCapacityList(ProductInfoRequest request) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectPrdtCapacityList(request);
        return data;
    }

    //휴대폰 목록조회
    public List<PhoneInfoResponse> getPhoneList(ProductInfoRequest request) {

        //1. 조직코드로 단말재고 확인
        PhoneSerialRequest phoneSerialRequest = new PhoneSerialRequest();
        phoneSerialRequest.setOrgnId(AuthenticationUtils.getAgentCode()); //로그인 세션의 대리점코드로 조회
        //phoneSerialRequest.setOrgnId(AuthenticationUtils.getShopCode()); //로그인 세션의 판매점코드로 조회
        List<CategoryInfoDto> categoryInfoDto = this.getPhoneInventoryList(phoneSerialRequest);
        request.setListPhoneDto(categoryInfoDto);
        //K7038209

        //2. 판매정책 조회
        request.setPlcyTypeCd("N"); //[필수][스마트 고정] >> N:위탁온라인, W:도매, M:오프라인, D:직영
        List<MspSalePlcyMstInfoDto> listMspSaleDto = this.getMspSalePlcyMstList(request);
        request.setListMspSaleDto(listMspSaleDto);

        //3. M전산에서 조회된 판매정책으로 단말목록조회
        List<PhoneInfoResponse> mspPhoneList = null;
        if (!ObjectUtils.isEmpty(listMspSaleDto)) {
            mspPhoneList = productInfoReadMapper.selectPhoneList(request);
        }

        //4. M전산 조회 단말목록에서 매장재고 있는 단말목록만 추출
        List<PhoneInfoResponse> data = null;
        if (!ObjectUtils.isEmpty(mspPhoneList)) {
            data = mspPhoneList.stream()
                .filter(phone -> categoryInfoDto.stream()
                    .filter(y -> y.getProdId() != null)
                    .map(x -> x.getProdId())
                    .toList().contains(phone.getModelId()))
                //.toList().contains(phone.getRprsPrdtId()))
                .collect(Collectors.toList());
        }

        return data;
    }
    /*public List<PhoneInfoDto> getPhoneList(ProductInfoRequest request) {
        //1. 조직코드로 단말재고 확인
        PhoneSerialRequest phoneSerialRequest = new PhoneSerialRequest();
        //phoneSerialRequest.setOrgnId(request.getOrgnId()); //@@변경필수@@ - 세션정보로 매장코드 변경
        phoneSerialRequest.setOrgnId("1100014062"); //@@변경필수@@ - 세션정보로 매장코드 변경
        List<CategoryInfoDto> categoryInfoDto = this.getPhoneInventoryList(phoneSerialRequest);
        request.setListPhoneDto(categoryInfoDto);

        //2. 판매정책 조회
        request.setPlcyTypeCd("N"); //[필수][스마트 고정] >> N:위탁온라인, W:도매, M:오프라인, D:직영
        List<MspSalePlcyMstInfoDto> listMspSaleDto = this.getMspSalePlcyMstList(request);
        request.setListMspSaleDto(listMspSaleDto);

        //3. M전산에서 조회된 판매정책으로 단말목록조회
        List<PhoneInfoDto> mspPhoneList = null;
        if (!ObjectUtils.isEmpty(listMspSaleDto)) {
            mspPhoneList = productInfoReadMapper.selectPhoneList(request);
        }

        //4. M전산 조회 단말목록에서 매장재고 있는 단말목록만 추출
        List<PhoneInfoDto> data = null;
        if (!ObjectUtils.isEmpty(mspPhoneList)) {
            data = mspPhoneList.stream()
                .filter(phone -> categoryInfoDto.stream()
                    .map(x -> x.getProdId())
                    .toList().contains(phone.getRprsPrdtId()))
                .collect(Collectors.toList());
        }

        return data;
    }*/

    //요금제 목록조회
    public List<RateInfoResponse> getRateList(ProductInfoRequest request) {
        CategoryRelRequest productCategoryProdRequest = new CategoryRelRequest();
        if (!StringUtils.hasText(request.getProdCtgTypeCd())) {
            //
        }
        productCategoryProdRequest.setProdCtgTypeCd(request.getProdCtgTypeCd());
        productCategoryProdRequest.setProdCtgId(request.getProdCtgId());

        //1. 선택된 카테고리로 요금제 목록 조회
        List<CategoryInfoDto> listRateDto = this.getCategoryDetailList(productCategoryProdRequest);

        //1. 결과로 선택된 카테고리로 조회된 요금제 목록 세팅
        if (listRateDto != null && !listRateDto.isEmpty()) {
            request.setListRateDto(listRateDto);
        }

        //2. 선택된 카테고리에 맞는 요금제를 조건절로 추가하여 M전산에서 요금제 목록 조회
        List<RateInfoResponse> data = null;
        request.setOrgnId(AuthenticationUtils.getAgentCode()); //대리점코드
        request.setPayClCd("PO"); //후불-고정 >> 상수처리@@ 필요!!!
        request.setPlcyTypeCd("N"); //위탁온라인
        request.setServiceType("P"); //요금제구분 (P:요금제, R:부가서비스)

        //판매정책이 필수가 아닌 경우가 있을 것임. 확인필요함~
        //if (StringUtils.hasText(request.getSalePlcyCd())) {
        if ("MM".equals(request.getReqBuyTypeCd().getCode())) { //단말구매
            data = productInfoReadMapper.selectRateList(request);
        } else { //그외
            if (!StringUtils.hasText(request.getPrdtSctnCd())) {
                request.setPrdtSctnCd("LTE"); // 선택할 값이 없어서 초기값 처리?  LTE / 5G 어떻게 알지????
            }
            data = productInfoReadMapper.selectUsimRateList(request);
        }
        //data = productInfoReadMapper.selectRateList(request);
        //data = productInfoReadMapper.selectUsimRateList(request);

        return data;
    }

    //가격정보조회
    public MspSaleSubsdMstResponse getMspSalePriceInfo(MspSaleSubsdMstRequest request) {
        //최종결과
        MspSaleSubsdMstResponse response = new MspSaleSubsdMstResponse();

        //CategoryType reqBuyTypeCd = ""; //상품유형
        //parameter
        String oldYn = "N"; //중고여부
        String orgnId = AuthenticationUtils.getAgentCode(); //대리점코드
        String salePlcyCd = request.getSalePlcyCd(); //판매정책코드
        String agrmTrm = request.getAgrmTrm(); //요금약정기간
        String socCd = request.getRateCd(); //요금제 코드

        if (!StringUtils.hasText(agrmTrm)) {
            agrmTrm = "0";
        }
        /*if ("0".equals(agrmTrm)) {
            agrmTrm = "0";
        }*/

        //DATA SET
        request.setOrgnId(orgnId); //조직코드
        request.setOldYn(oldYn); //중고여부

        if (!StringUtils.hasText(request.getReqBuyTypeCd())) { //상품 : 휴대폰, USIM
            request.setReqBuyTypeCd("MM"); //휴대폰 선택으로 고정
        }
        if (!StringUtils.hasText(request.getOperTypeCd())) { //가입유형 : 신규(NAC3) , 번이(MNP3), 기변(HDN3)
            return null;
        }
        if (!StringUtils.hasText(request.getPrdtSctnCd())) {
            request.setPrdtSctnCd("LTE"); //데이타유형 : 3G / LTE / 5G / LTE5G >> 기본값 LTE 처리
        }

        //1. 약정이 있는 경우 단말/요금 관련 요금조회
        MspSaleSubsdMstResponse saleSubsdMstResponse = new MspSaleSubsdMstResponse();
        if ("MM".equals(request.getReqBuyTypeCd())) {
            if (StringUtils.hasText(salePlcyCd)) { //판매정책코드
                saleSubsdMstResponse = this.getMspSaleSubsdMst(request);
            }
            if (saleSubsdMstResponse != null) {
                response = saleSubsdMstResponse; //단말/요금 관련 결과정보
            }
        }
        //if (Integer.parseInt(agrmTrm) > 0) { //무약정이어도 가능해야함.
        //}

        //2. 약정이 없는 경우 요금할인 조회?? (무약정 또는 USIM 선택일때도 무약정이겠지?)
        if ("UU".equals(request.getReqBuyTypeCd())) {
            MspSaleSubsdMstResponse usimDcamtResponse = new MspSaleSubsdMstResponse();
            if (StringUtils.hasText(socCd)) { //요금제 코드가 있는 경우에만
                usimDcamtResponse = this.getUsimDcamt(request);
            }
            if (usimDcamtResponse != null) {
                response.setDcAmt(usimDcamtResponse.getDcAmt()); //요금할인
            }
        }
        //if (Integer.parseInt(agrmTrm) == 0) { //무약정일때 요금할인금액 조회
        //}

        //3. 가입비, 유심비 등 조회
        PriceJoinUsimRequest priceJoinUsimRequest = new PriceJoinUsimRequest();
        PriceJoinUsimResponse priceJoinUsimResponse = new PriceJoinUsimResponse();
        String priceGubun = request.getOperTypeCd() + request.getPrdtSctnCd();
        priceJoinUsimRequest.setPriceGubun(priceGubun);
        priceJoinUsimResponse = this.getUsimBasJoinPrice(priceJoinUsimRequest);
        if (priceJoinUsimResponse != null) {
            response.setJoinPrice(priceJoinUsimResponse.getJoinPrice()); //가입비
            response.setUsimPrice(priceJoinUsimResponse.getUsimPrice()); //유심비
        }

        //총할부수수료 산출 ( vat 포함 할부수수료인 instCmsn 과 비교해서 계산을 해봐도 동일한 값이 안나와서 산출식 추가함. ㅠㅠ;)
        String totalInstCmsn = "0"; //총할부수수료
        String instAmt = ""; //할부원금
        String modelMonthly = request.getModelMonthly(); //단말할부기간
        if (saleSubsdMstResponse != null) {
            instAmt = saleSubsdMstResponse.getInstAmt();
            if (instAmt != null && instAmt.length() > 0 && modelMonthly != null && modelMonthly.length() > 0) {
                Integer instAmtInt = Integer.parseInt(instAmt);
                Integer modelMonthlyInt = Integer.parseInt(modelMonthly); //프론트에서 넘기는 값
                if (instAmtInt > 0 && modelMonthlyInt > 0) { //할부원금과 할부개월이 있는 경우
                    request.setInstAmt(instAmt); //할부원금
                    //request.setInstAmt(modelMonthly); //할부원금
                    totalInstCmsn = getTotalInstCmsn(request);
                }
            }

        }
        response.setTotalInstCmsn(totalInstCmsn);

        return response;
    }

    //총할부수수료 산출
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
            BigDecimal bgTemp2 = bgYearRate.divide(bgMonth, 38, BigDecimal.ROUND_HALF_UP).add(bgTemp); //(1+0.059/12)
            bgPow = bgTemp2.pow(modelMonthly).setScale(38, BigDecimal.ROUND_HALF_UP);  ////POWER((1+0.059/12), 24)
            BigDecimal bgPow2 = bgPow.subtract(bgTemp); //( P-1 )

            //round(    1094500 * 0.059 / 12 * P / ( P-1 ),0)
            BigDecimal bgRound1 = bgInstAmt.multiply(bgYearRate).divide(bgMonth, 38, BigDecimal.ROUND_HALF_UP).multiply(bgPow)
                .divide(bgPow2, 0, BigDecimal.ROUND_DOWN);

            //x 할부개월수 – 할부원금
            totalInstCmsn = bgRound1.multiply(bgModelMonthly).subtract(bgInstAmt).intValue();
        } else {
            totalInstCmsn = 0;
        }
        totalInstCmsnStr = totalInstCmsn.toString();

        return totalInstCmsnStr;
    }


    //가격정보조회 (단말,요금,지원금) - 출고가, 기본요금, 공시지원금 등
    public MspSaleSubsdMstResponse getMspSaleSubsdMst(MspSaleSubsdMstRequest request) {
        //request.setOrgnId(AuthenticationUtils.getAgentCode()); //대리점코드
        //request.setOldYn("N"); //스마트는 새폰만 됨.
        return productInfoReadMapper.selectMspSaleSubsdMst(request);
    }

    //가격정보 (무약정)
    public MspSaleSubsdMstResponse getUsimDcamt(MspSaleSubsdMstRequest request) {
        return productInfoReadMapper.selectUsimDcamt(request);
    }

    //가격정보조회 (가입비, 유심비)
    public PriceJoinUsimResponse getUsimBasJoinPrice(PriceJoinUsimRequest request) {
        //String priceGubun = request.getOperTypeCd() + request.getPrdtSctnCd();
        //request.setPriceGubun(priceGubun);
        return productInfoReadMapper.selectJoinUsimPrice(request);
    }


    //휴대폰 매장 재고 조회 (postgre)
    public List<CategoryInfoDto> getPhoneInventoryList(@Valid PhoneSerialRequest request) {
        //request.setOrgnId(AuthenticationUtils.getAgentCode()); //대리점코드
        request.setOrgnId(AuthenticationUtils.getShopCode()); //판매점코드
        List<CategoryInfoDto> data = productSmartInfoReadMapper.selectPhoneInventoryList(request);
        return data;
    }

    //휴대폰 매장 재고 조회 (postgre) >> 휴대폰 일련번호 유효성체크에서 일단 사용예정
    public String getPhoneInventory(PhoneSerialRequest condition) {
        String imei = productSmartInfoReadMapper.selectPhoneInventory(condition);
        return imei;
    }

    //요금제,부가서비스,안심보험 카테고리 목록 조회 (postgre)
    public List<CategoryInfoDto> getCategoryList(CategoryMstRequest request) {
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
    public List<MsfRequestAdditionResponse> getAdditionList(MsfRequestAdditionRequest condition) {
        List<MsfRequestAdditionResponse> msfRequestAdditionResponseList = new ArrayList<>();
        List<MspAdditionDto> mspAdditionDtoList = new ArrayList<>();

        MsfRequestAdditionResponse msfRequestAdditionResponse = new MsfRequestAdditionResponse();
        msfRequestAdditionResponse.setFreeAddition(new ArrayList<>());
        msfRequestAdditionResponse.setPaidAddition(new ArrayList<>());

        List<String> prodCtgIdList = condition.getCategoryMstRequest().getProdCtgId();
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
            condition.setProductCategoryInfoDtoList(categoryInfoDtoListAll);
            mspAdditionDtoList = this.getMsfAdditionList(condition);
            msfRequestAdditionResponse.setFreeAndPaid(mspAdditionDtoList);
            msfRequestAdditionResponseList.add(msfRequestAdditionResponse);
        }
        return msfRequestAdditionResponseList;
    }

    //부가서비스 목록 조회 (기기변경의 가입중 부가서비스 목록조회)
    public List<MsfRequestAdditionResponse> getActiveAdditionList(MspJuoSubInfoRequest request) {
        List<MsfRequestAdditionResponse> msfRequestAdditionResponseList = new ArrayList<>();

        //고객구분, 고객식별번호, 핸드폰번호로 고객조회해서 고식별번호와 핸드폰번호로
        //고객명과 핸드폰번호로 기기변경 조회서비스 호출하여 고객아이디 조회
        String ncn = "";
        FormResponse<MspJuoSubInfoResponse> customerInfoResponse = authInfoService.getJuoSubInfo(request);
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
        List<McpRegServiceDto> regServiceList = mcpApiClient.post(
            "/mypage/regService",
            ncn,
            List.class
        );

        MsfRequestAdditionRequest condition = new MsfRequestAdditionRequest(); //
        List<MspAdditionDto> mspAdditionDtoList = new ArrayList<>();

        MsfRequestAdditionResponse msfRequestAdditionResponse = new MsfRequestAdditionResponse();
        msfRequestAdditionResponse.setFreeAddition(new ArrayList<>()); //무료부가서비스 return
        msfRequestAdditionResponse.setPaidAddition(new ArrayList<>()); //유료부가서비스 return

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
            mspAdditionDtoList = this.getMsfAdditionList(condition);
            msfRequestAdditionResponse.setFreeAndPaid(mspAdditionDtoList);
        }

        msfRequestAdditionResponseList.add(msfRequestAdditionResponse);

        return msfRequestAdditionResponseList;
    }

    //M전산 테이블에서 부가서비스 상세 정보 조회하기
    public List<MspAdditionDto> getMsfAdditionList(MsfRequestAdditionRequest condition) {
        List<MspAdditionDto> data = productInfoReadMapper.selectMsfAdditionList(condition);
        return data;
    }

    //안심보험 목록 조회
    public List<IntmInsrRelDTO> getInsrProdList(InsrProdRequest request) {
        //1. M전산에서 안심보험 목록 조회
        List<IntmInsrRelDTO> insrProdList = mcpApiClient.post(
            "/appform/selectInsrProdList",
            request.getIntmInsrRelDTO(),
            List.class
        );

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

            //4. M전산에서 조회한 안심보험 목록이 스마트에 등록된 안심보험 목록에 포함된 것만 INSR_PROD_CD 기준으로 필터링 처리
            filteredList = insrProdList.stream()
                .filter(insr -> validIds.contains(insr.getInsrProdCd()))
                .collect(Collectors.toList());
        }

        return filteredList;
    }

}
