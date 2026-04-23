package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.form.common.dto.*;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.ProductInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.ProductSmartInfoReadMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ktmmobile.msf.domains.form.form.common.constant.PhoneConstant.PHONE_FOR_MSP;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final ProductInfoReadMapper productInfoReadMapper;
    private final ProductSmartInfoReadMapper productSmartInfoReadMapper;

    //판매정책조회
    /*public List<MspSalePlcyMstInfoDto> getMspSalePlcyMstList(CommonSearchCondition condition) {
        List<MspSalePlcyMstInfoDto> data = productInfoMapper.selectMspSalePlcyMstList(condition);
        return data;
    }*/
    //판매정책조회 (PRDT_ID 값 k코드가 있으면 판매정책 하나가 조회됨)
    public List<MspSalePlcyMstInfoDto> getMspSalePlcyMstList(ProductSearchCondition condition) {
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
    public List<MspSalePlcyMstInfoDto> getSaleTypeList(ProductSearchCondition condition) {
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
    public List<MspSaleAgrmMstInfoDto> getMspSaleAgrmMstList(ProductSearchCondition condition) {
        List<MspSaleAgrmMstInfoDto> data = productInfoReadMapper.selectMspSaleAgrmMstList(condition);
        return data;
    }

    //휴대폰 할부기간 조회
    public List<PhoneInfoDto> getModelMonthlyList(ProductSearchCondition condition) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectModelMonthlyList(condition);
        return data;
    }

    //휴대폰 색상 목록 조회
    public List<PhoneInfoDto> getPrdtColorList(ProductSearchCondition condition) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectPrdtColorList(condition);
        return data;
    }

    //휴대폰 용량 목록 조회
    public List<PhoneInfoDto> getPrdtCapacityList(ProductSearchCondition condition) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectPrdtCapacityList(condition);
        return data;
    }

    //휴대폰 목록조회
    public List<PhoneInfoDto> getPhoneList(ProductSearchCondition condition) {
        condition.setOrgnId("V000000004"); //@삭제필요@ >> 매장재고조회를 위한 임시값 설정
        PhoneSerialCondition phoneSerialCondition = new PhoneSerialCondition();
        phoneSerialCondition.setOrgnId(condition.getOrgnId());
        phoneSerialCondition.setProdId(condition.getProdId());
        phoneSerialCondition.setProdSn(condition.getProdSn());
        List<ProductCategoryDto> productCategoryDto = this.getPhoneInventoryList(phoneSerialCondition);
        condition.setListPhoneDto(productCategoryDto);

        //휴대폰 목록조회는 판매정책조회하여 조건절에 추가필요.
        condition.setPlcyTypeCd("N"); //CMN0050 정책유형코드 : W >> condition.setPlcyTypeCd(OFFLINE_FOR_MSP);
        condition.setPlcySctnCd(PHONE_FOR_MSP); //상품 : 핸드폰
        condition.setOrgnId("1100014062"); //로그인한 사용자의 조직코드는 대리점으로 할듯    대리점? 판매점?
        //condition.setPrdtSctnCd("");
        //condition.setSprtTp("");//할인유형

        //판매정책 조회
        List<MspSalePlcyMstInfoDto> listMspSaleDto = this.getMspSalePlcyMstList(condition);
        condition.setListMspSaleDto(listMspSaleDto);

        List<PhoneInfoDto> data = null;
        if (!listMspSaleDto.isEmpty() && !productCategoryDto.isEmpty()) {
            data = productInfoReadMapper.selectPhoneList(condition);
        }
        return data;
    }

    //요금제 목록조회
    public List<RateInfoDto> getRateList(ProductSearchCondition condition) {
        List<ProductCategoryDto> listRateDto = getProductCategoryDetailList(condition); //선택된 카테고리로 조회된 요금제 목록
        condition.setListRateDto(listRateDto);
        String reqBuyTypeCd = condition.getReqBuyTypeCd();
        if ("UU".equals(reqBuyTypeCd)) { //유심상품을 선택
            reqBuyTypeCd = "02";
        } else { //그외 전체
            reqBuyTypeCd = "01";
        }

        //임시 조건데이타 설정
        condition.setPayClCd("PO"); //PAY_CL_CD : 후불
        condition.setServiceType("P"); //SERVICE_TYPE : 구분(요금제:P)
        condition.setOrgnId("1100011741"); //조직코드
        condition.setPlcySctnCd("02"); //정책구분코드 01:단말, 02:USIM
        condition.setRateType("02"); //요금제유형 : 01(휴대폰), 02(유심) */ -- 이게 왜 PL241F504 가  02 인것인가?
        condition.setSalePlcyCd("D2026030508145"); //판매정책코드
        condition.setSprtTp(""); //할인유형
        condition.setSprtTp(""); //할인유형
        //condition.setRateType(reqBuyTypeCd); //CMN0047 >> 01 : 단말요금제 / 02 : USIM요금제
        List<RateInfoDto> data = productInfoReadMapper.selectRateList(condition);
        return data;
    }

    //공시지원금 조회
    public List<PhoneInfoDto> getMspOfficialNoticeSupport(ProductSearchCondition condition) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectMspOfficialNoticeSupport(condition);
        return data;
    }

    //휴대폰 매장 재고 조회 (postgre)
    public List<ProductCategoryDto> getPhoneInventoryList(@Valid PhoneSerialCondition condition) {
        List<ProductCategoryDto> data = productSmartInfoReadMapper.selectPhoneInventoryList(condition);
        return data;
    }

    //휴대폰 매장 재고 조회 (postgre) >> 휴대폰 일련번호 유효성체크에서 일단 사용예정
    public String getPhoneInventory(PhoneSerialCondition condition) {
        String imei = productSmartInfoReadMapper.selectPhoneInventory(condition);
        return imei;
    }

    //요금제,부가서비스,안심보험 카테고리 목록 조회 (postgre)
    public List<ProductCategoryDto> getProductCategoryList(ProductSearchCondition condition) {
        List<ProductCategoryDto> data = null;
        String prodCtgTypeCd = condition.getProdCtgTypeCd(); //카테고리 조회를 위한 타입 (P:요금제, R:부가서비스, ?:안심보험)
        if (!StringUtils.hasText(prodCtgTypeCd)) {
            condition.setProdCtgTypeCd("P"); //요금제로 카테고리로 기본 설정
        }
        data = productSmartInfoReadMapper.selectProductCategoryList(condition);
        return data;
    }

    //요금제,부가서비스,안심보험 카테고리 상세 조회 (postgre)
    public List<ProductCategoryDto> getProductCategoryDetailList(ProductSearchCondition condition) {
        List<ProductCategoryDto> data = null;
        //조회하려는 카테고리의 ProdCtgTypeCd(요금,부가서비스,안심보험) 및 ProdCtgId 가 넘어오지 않은 경우
        //카테고리 목록을 조회하여 맨 처음꺼로 카테고리를 상세조회하도록 함
        if (!StringUtils.hasText(condition.getProdCtgTypeCd()) && !StringUtils.hasText(condition.getProdCtgId())) {
            List<ProductCategoryDto> productCategoryList = this.getProductCategoryList(condition);
            if (!productCategoryList.isEmpty()) {
                ProductCategoryDto productCategoryDto = productCategoryList.stream()
                        .findFirst()
                        .orElse(null);
                condition.setProdCtgId(productCategoryDto.getCtgCd());
            }
        }
        data = productSmartInfoReadMapper.selectProductCategoryDetailList(condition);
        return data;
    }

    //부가서비스 목록 조회
    //서비스변경에서도 선택한 카테고리 값을 받아서 조회하는 걸로 처리
    //신규/변경의 경우 카테고리 코드를 프론트에서 받아오는 형태로 변경예정
    //기기변경의 경우 가입중 부가서비스 조회해서 합치는 것 처리필요
    public List<MsfRequestAdditionResponse> getAdditionList(MsfRequestAdditionRequest condition) {
        String operTypeCd = condition.getOperTypeCd(); //업무구분코드 NAC3(신규가입), MNP3(번호이동), HDN3(기기변경)
        String prodCtgId = condition.getProdCtgId(); //프론트에서 전달되는 부가서비스 카테고리의 아이디
        List<MsfRequestAdditionResponse> msfRequestAdditionResponseList = new ArrayList<>();
        List<MspAdditionDto> mspAdditionDtoList = new ArrayList<>();

        //스마트 관리자에 설정된 부가서비스 목록 조회
        ProductSearchCondition productSearchCondition = new ProductSearchCondition();
        productSearchCondition.setProdCtgTypeCd("R"); //R:부가서비스

        if (StringUtils.hasText(prodCtgId)) {
            MsfRequestAdditionResponse msfRequestAdditionResponse = new MsfRequestAdditionResponse();
            msfRequestAdditionResponse.setFreeAddition(new ArrayList<>());
            msfRequestAdditionResponse.setPaidAddition(new ArrayList<>());
            //스마트 관리자에서 카테고리로 상품조회
            productSearchCondition.setProdCtgId(prodCtgId);
            List<ProductCategoryDto> productCategoryProdList = this.getProductCategoryDetailList(productSearchCondition);

            //조회된 상품목록을 List 에 담기
            if (!productCategoryProdList.isEmpty()) {
                //M전산에서 조회하기
                condition.setListProductCategoryDto(productCategoryProdList); //스마트에서 조회한 상품목록을 파라미터로 전달
                mspAdditionDtoList = this.getMsfAdditionList(condition);
                msfRequestAdditionResponse.setAdditionCtgCd(prodCtgId); //리턴할 부가서비스 목록의 카테고리 항목 세팅
                mspAdditionDtoList.forEach(mspAdditionDto -> {
                    if (Integer.parseInt(mspAdditionDto.getBaseAmt()) == 0) {
                        msfRequestAdditionResponse.getFreeAddition().add(mspAdditionDto);
                    } else {
                        msfRequestAdditionResponse.getPaidAddition().add(mspAdditionDto);
                    }
                });
                msfRequestAdditionResponseList.add(msfRequestAdditionResponse);

                //기기변경인 경우 가입중 부가서비스 목록 조회 ( mcp-api : /mypage/regService )
                /*List<MspAdditionDto> mspAdditionDtoRegList = new ArrayList<>();
                if ("HDN3".equals(operTypeCd)) {
                    mspAdditionDtoRegList.get(0).setRateCd("SMARTBREK");
                    mspAdditionDtoRegList.get(0).setRateNm("휴대폰안심보험 스마트파손형");
                    mspAdditionDtoRegList.get(0).setBaseAmt("1300");
                    mspAdditionDtoRegList.get(1).setRateCd("KISSTDSAL");
                    mspAdditionDtoRegList.get(1).setRateNm("M 기본료할인 6개월");
                    mspAdditionDtoRegList.get(1).setBaseAmt("-2000");
                }*/
            }
        }
        return msfRequestAdditionResponseList;
    }

    //신규/변경에서 부가서비스 목록 조회 시 스마트 상품카테고리의 부가서비스 유료/무료 항목을 가져오도록 조회함. (2026.04.22)
    /*public List<MsfRequestAdditionResponse> getAdditionList(MsfRequestAdditionRequest condition) {
        //String formTypeCd = condition.getFormTypeCd(); //신규변경 : 1, 서비스변경 : 2?
        List<MsfRequestAdditionResponse> msfRequestAdditionResponseList = new ArrayList<>();

        //스마트 관리자에 설정된 부가서비스 목록 조회
        ProductSearchCondition productSearchCondition = new ProductSearchCondition();
        productSearchCondition.setProdCtgTypeCd("R"); //R:부가서비스
        List<ProductCategoryDto> productCategoryList = this.getProductCategoryList(productSearchCondition);
        if (!productCategoryList.isEmpty()) {
            productCategoryList.forEach(productCategoryDto -> {
                List<MspAdditionDto> mspAdditionDtoList = new ArrayList<>();
                MsfRequestAdditionResponse msfRequestAdditionResponse = new MsfRequestAdditionResponse();
                msfRequestAdditionResponse.setFreeAddition(new ArrayList<>());
                msfRequestAdditionResponse.setPaidAddition(new ArrayList<>());
                //스마트 관리자에서 카테고리로 상품조회
                productSearchCondition.setProdCtgId(productCategoryDto.getCtgCd());
                List<ProductCategoryDto> productCategoryProdList = this.getProductCategoryDetailList(productSearchCondition);

                //조회된 상품목록을 List 에 담기
                if (!productCategoryProdList.isEmpty()) {
                    //M전산에서 조회하기
                    condition.setListProductCategoryDto(productCategoryProdList); //스마트에서 조회한 상품목록을 파라미터로 전달
                    mspAdditionDtoList = this.getMsfAdditionList(condition);
                    msfRequestAdditionResponse.setAdditionCtgCd(productCategoryDto.getCtgCd()); //리턴할 부가서비스 목록의 카테고리 항목 세팅
                    mspAdditionDtoList.forEach(mspAdditionDto -> {
                        if (Integer.parseInt(mspAdditionDto.getBaseAmt()) == 0) {
                            msfRequestAdditionResponse.getFreeAddition().add(mspAdditionDto);
                        } else {
                            msfRequestAdditionResponse.getPaidAddition().add(mspAdditionDto);
                        }
                    });
                    msfRequestAdditionResponseList.add(msfRequestAdditionResponse);

                }
            });
        }
        return msfRequestAdditionResponseList;
    }*/

    //M전산 테이블에서 부가서비스 상세 정보 조회하기
    public List<MspAdditionDto> getMsfAdditionList(MsfRequestAdditionRequest condition) {
        List<MspAdditionDto> data = productInfoReadMapper.selectMsfAdditionList(condition);
        return data;
    }

}
