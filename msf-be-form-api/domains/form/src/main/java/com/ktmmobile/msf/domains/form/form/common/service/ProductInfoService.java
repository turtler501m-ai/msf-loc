package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.form.common.dto.*;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.ProductInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.ProductSmartInfoReadMapper;
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
    private final ProductSmartInfoReadMapper productCategoryMapper;

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

    //휴대폰 매장 재고 조회 (postgre)
    public List<ProductCategoryDto> getPhoneInventoryList(ProductSearchCondition condition) {
        List<ProductCategoryDto> data = productCategoryMapper.selectPhoneInventoryList(condition);
        return data;
    }

    //휴대폰 목록조회
    public List<PhoneInfoDto> getPhoneList(ProductSearchCondition condition) {
        condition.setStorCd("V000000004"); //매장재고조회를 위한 것 (임시)
        List<ProductCategoryDto> productCategoryDto = this.getPhoneInventoryList(condition);
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

    //요금제,부가서비스,안심보험 카테고리 목록 조회
    public List<ProductCategoryDto> getProductCategoryList(ProductSearchCondition condition) {
        List<ProductCategoryDto> data = null;
        String searchProductCategoryTypeCd = condition.getSearchProductCategoryTypeCd(); //카테고리 조회를 위한 타입 (P:요금제, R:부가서비스, ?:안심보험)
        if (!StringUtils.hasText(searchProductCategoryTypeCd)) {
            condition.setSearchProductCategoryTypeCd("P"); //요금제로 카테고리로 기본 설정
        }
        data = productCategoryMapper.selectProductCategoryList(condition);
        return data;
    }

    //요금제,부가서비스,안심보험 카테고리 상세 조회
    public List<ProductCategoryDto> getProductCategoryDetailList(ProductSearchCondition condition) {
        List<ProductCategoryDto> data = null;
        List<ProductCategoryDto> productCategoryList = this.getProductCategoryList(condition);
        if (productCategoryList.size() > 0) {
            ProductCategoryDto productCategoryDto = productCategoryList.stream()
                    .findFirst()
                    .orElse(null);

            condition.setSearchProductCategoryId(productCategoryDto.getCtgCd());
            data = productCategoryMapper.selectProductCategoryDetailList(condition);
        }

        return data;
    }

    //공시지원금 조회
    public List<PhoneInfoDto> getMspOfficialNoticeSupport(ProductSearchCondition condition) {
        List<PhoneInfoDto> data = productInfoReadMapper.selectMspOfficialNoticeSupport(condition);
        return data;
    }

    //제휴요금제 정보 조회
    public List<RateInfoDto> getJehuInfoList(ProductSearchCondition condition) {
        List<RateInfoDto> data = productInfoReadMapper.selectJehuInfoList(condition);
        return data;
    }


}
