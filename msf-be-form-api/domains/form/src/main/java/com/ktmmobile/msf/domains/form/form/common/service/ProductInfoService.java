package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.common.code.CategoryType;
import com.ktmmobile.msf.domains.form.common.dto.IntmInsrRelDTO;
import com.ktmmobile.msf.domains.form.common.dto.McpRegServiceDto;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.form.common.dto.*;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.ProductInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.ProductSmartInfoReadMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductInfoService.class);
    private final McpApiClient mcpApiClient;
    private final ProductInfoReadMapper productInfoReadMapper;
    private final ProductSmartInfoReadMapper productSmartInfoReadMapper;
    private final AuthInfoService authInfoService;

    //판매정책조회
    /*public List<MspSalePlcyMstInfoDto> getMspSalePlcyMstList(CommonSearchCondition condition) {
        List<MspSalePlcyMstInfoDto> data = productInfoMapper.selectMspSalePlcyMstList(condition);
        return data;
    }*/
    //판매정책조회 (PRDT_ID 값 k코드가 있으면 판매정책 하나가 조회됨)
    public List<MspSalePlcyMstInfoDto> getMspSalePlcyMstList(ProductInfoRequest condition) {
        List<MspSalePlcyMstInfoDto> mspSalePlcyInfo = productInfoReadMapper.selectMspSalePlcyMstList(condition);
        List<MspSalePlcyMstInfoDto> distinctList = new ArrayList<>(
                mspSalePlcyInfo.stream()
                        .collect(Collectors.toMap(
                                MspSalePlcyMstInfoDto::getSalePlcyCd,
                                Function.identity(),
                                (existing, replacement) -> existing
                        ))
                        .values()
        );
        return distinctList;
    }

    //할인유형조회
    public List<MspSalePlcyMstInfoDto> getSaleTypeList(ProductInfoRequest condition) {
        List<MspSalePlcyMstInfoDto> salePlcyInfo = productInfoReadMapper.selectSaleTypeList(condition);
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
    public List<MspSaleAgrmMstInfoDto> getMspSaleAgrmMstList(ProductInfoRequest condition) {
        List<MspSaleAgrmMstInfoDto> data = productInfoReadMapper.selectMspSaleAgrmMstList(condition);
        return data;
    }

    //휴대폰 할부기간 조회
    public List<PhoneInfoDto> getModelMonthlyList(ProductInfoRequest condition) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectModelMonthlyList(condition);
        return data;
    }

    //휴대폰 색상 목록 조회
    public List<PhoneInfoDto> getPrdtColorList(ProductInfoRequest condition) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectPrdtColorList(condition);
        return data;
    }

    //휴대폰 용량 목록 조회
    public List<PhoneInfoDto> getPrdtCapacityList(ProductInfoRequest condition) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectPrdtCapacityList(condition);
        return data;
    }

    //휴대폰 목록조회
    public List<PhoneInfoDto> getPhoneList(ProductInfoRequest request) {
        //@@ 삭제해야함.
        /*PhoneSerialRequest phoneSerialRequest = new PhoneSerialRequest();
        phoneSerialRequest.setOrgnId(condition.getOrgnId());
        phoneSerialRequest.setProdId(condition.getProdId());
        phoneSerialRequest.setProdSn(condition.getProdSn());
        List<CategoryInfoDto> categoryInfoDto = this.getPhoneInventoryList(phoneSerialRequest);
        condition.setListPhoneDto(categoryInfoDto);*/

        //@@ 판매정책조회를 위한 데이타셋 임시 - 휴대폰 목록조회는 판매정책조회하여 조건절에 추가필요.
        //condition.setPlcyTypeCd("N"); //CMN0050 정책유형코드 : W >> condition.setPlcyTypeCd(OFFLINE_FOR_MSP);
        //condition.setPlcySctnCd(PHONE_FOR_MSP); //상품 : 핸드폰
        //condition.setOrgnId("1100014062"); //로그인한 사용자의 조직코드는 대리점으로 할듯    대리점? 판매점?
        //condition.setPrdtSctnCd("");
        //condition.setSprtTp("");//할인유형

        //1. 조직코드로 단말재고 확인
        PhoneSerialRequest phoneSerialRequest = new PhoneSerialRequest();
        phoneSerialRequest.setOrgnId(request.getOrgnId());
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
    }

    //요금제 목록조회
    public List<RateInfoDto> getRateList(ProductInfoRequest condition) {
        CategoryRelRequest productCategoryProdRequest = new CategoryRelRequest();
        productCategoryProdRequest.setProdCtgTypeCd(condition.getProdCtgTypeCd());
        List<CategoryInfoDto> listRateDto = this.getCategoryDetailList(productCategoryProdRequest); //선택된 카테고리로 조회된 요금제 목록
        condition.setListRateDto(listRateDto);

        //임시 조건데이타 설정
        condition.setPayClCd("PO"); //PAY_CL_CD : 후불
        condition.setServiceType("P"); //SERVICE_TYPE : 구분(요금제:P)
        condition.setOrgnId("1100011741"); //조직코드
        condition.setSalePlcyCd("D2026030508145"); //판매정책코드
        condition.setSprtTp(""); //할인유형
        condition.setSprtTp(""); //할인유형
        //condition.setRateType(reqBuyTypeCd); //CMN0047 >> 01 : 단말요금제 / 02 : USIM요금제
        List<RateInfoDto> data = productInfoReadMapper.selectRateList(condition);
        return data;
    }

    //공시지원금 조회
    public List<PhoneInfoDto> getMspOfficialNoticeSupport(ProductInfoRequest condition) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectMspOfficialNoticeSupport(condition);
        return data;
    }

    //휴대폰 매장 재고 조회 (postgre)
    public List<CategoryInfoDto> getPhoneInventoryList(@Valid PhoneSerialRequest condition) {
        List<CategoryInfoDto> data = productSmartInfoReadMapper.selectPhoneInventoryList(condition);
        return data;
    }

    //휴대폰 매장 재고 조회 (postgre) >> 휴대폰 일련번호 유효성체크에서 일단 사용예정
    public String getPhoneInventory(PhoneSerialRequest condition) {
        String imei = productSmartInfoReadMapper.selectPhoneInventory(condition);
        return imei;
    }

    //요금제,부가서비스,안심보험 카테고리 목록 조회 (postgre)
    public List<CategoryInfoDto> getCategoryList(CategoryMstRequest condition) {
        List<CategoryInfoDto> data = null;
        CategoryType prodCtgTypeCd = condition.getProdCtgTypeCd(); //카테고리 조회를 위한 타입 (P:요금제, R:부가서비스, I:안심보험)
        condition.setProdCtgTypeCd(prodCtgTypeCd);
        data = productSmartInfoReadMapper.selectProductCategoryList(condition);
        return data;

        //String prodCtgTypeCd = condition.getProdCtgTypeCd(); //카테고리 조회를 위한 타입 (P:요금제, R:부가서비스, ?:안심보험)
        /*if (!StringUtils.hasText(prodCtgTypeCd)) {
            condition.setProdCtgTypeCd("P"); //요금제로 카테고리로 기본 설정
        }*/
    }

    //요금제,부가서비스,안심보험 카테고리 상세 조회 (postgre)
    public List<CategoryInfoDto> getCategoryDetailList(CategoryRelRequest condition) {
        List<CategoryInfoDto> data = null;
        //prodCtgTypeCd : 카테고리 구분코드 (P,R,I)
        //prodCtgId : 카테고리 상세코드 (prodCtgTypeCd 에 따른 상세코드)
        data = productSmartInfoReadMapper.selectProductCategoryDetailList(condition);
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
        prodCtgIdList.forEach(id -> {
            categoryRelRequest.setProdCtgTypeCd("R"); //R:부가서비스
            categoryRelRequest.setProdCtgId(id);
            List<CategoryInfoDto> categoryInfoDtoList = this.getCategoryDetailList(categoryRelRequest);
            categoryInfoDtoListAll.addAll(categoryInfoDtoList);
        });

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
        //고객구분, 고객식별번호, 핸드폰번호로 고객조회해서 고식별번호와 핸드폰번호로
        //고객명과 핸드폰번호로 기기변경 조회서비스 호출하여 고객아이디 조회
        MspJuoSubInfoResponse customerInfoResponse = authInfoService.getJuoSubInfo(request);
        String ncn = customerInfoResponse.getContractNum();

        //기기변경인 경우 가입중 부가서비스 목록 조회 ( mcp-api : /mypage/regService ) //계약번호 필요
        List<McpRegServiceDto> regServiceList = mcpApiClient.post(
                "/mypage/regService",
                ncn,
                List.class
        );

        List<MsfRequestAdditionResponse> msfRequestAdditionResponseList = new ArrayList<>();
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
        /*{
            "intmInsrRelDTO": {
            "reqBuyType": "MM", -- MM : 단말, UU : 유심
            "rprsPrdtId":"K7001165" -- 선택한 단말코드 (유심인 경우 해당없음)
        },
            "prodCtgId": "I000001" -- 스마트에 등록된 안심보험 카테고리 목록 중 선택한 하나의 값을 보냄
        }*/
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

        //3. 스마트에 등록된 안심보험 목록 기준으로 추출
        Set<String> validIds = categoryInfoDtoList.stream()
                .map(CategoryInfoDto::getProdId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        //4. M전산에서 조회한 안심보험 목록이 스마트에 등록된 안심보험 목록에 포함된 것만 INSR_PROD_CD 기준으로 필터링 처리
        List<IntmInsrRelDTO> filteredList = insrProdList.stream()
                .filter(insr -> validIds.contains(insr.getInsrProdCd()))
                .collect(Collectors.toList());

        return filteredList;
    }

}
