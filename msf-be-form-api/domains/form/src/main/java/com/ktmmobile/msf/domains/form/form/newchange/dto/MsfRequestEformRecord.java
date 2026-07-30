package com.ktmmobile.msf.domains.form.form.newchange.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.NonNull;
import org.apache.commons.lang3.math.NumberUtils;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeEformFieldMapper;

public record MsfRequestEformRecord(
    MsfRequestVo msfRequestVo,
    MsfRequestCstmrVo msfRequestCstmrVo,
    MsfRequestAgentVo msfRequestAgentVo,
    MsfRequestSaleVo msfRequestSaleVo,
    MsfRequestBillReqVo msfRequestBillReqVo,
    MsfRequestMoveVo msfRequestMoveVo,
    MsfRequestDvcChgVo msfRequestDvcChgVo
    // List<MsfRequestAdditionVo> msfRequestAdditionVo
) {

    public static MsfRequestEformRecord requestToRecord(NewChangeInfoRequest request) {
        return new MsfRequestEformRecord(
            NewChangeEformFieldMapper.INSTANCE.toMsfRequestVo(request),
            NewChangeEformFieldMapper.INSTANCE.toMsfRequestCstmrVo(request),
            NewChangeEformFieldMapper.INSTANCE.toMsfRequestAgentVo(request),
            NewChangeEformFieldMapper.INSTANCE.toMsfRequestSaleVo(request),
            NewChangeEformFieldMapper.INSTANCE.toMsfRequestBillReqVo(request),
            NewChangeEformFieldMapper.INSTANCE.toMsfRequestMoveVo(request),
            NewChangeEformFieldMapper.INSTANCE.toMsfRequestDvcChgVo(request)
            //NewChangeEformFieldMapper.INSTANCE.toMsfRequestAdditionVo(request.getAdditionList())
        );
    }

    // 로그인 사용자명 셋팅
    public String getCustomSaleManagerNm() {
        try {
            return AuthenticationUtils.getUser().getUserName();
        } catch (Exception _) {
            return null;
        }
    }

    // 대리점명/코드 셋팅
    public String getCustomAgentCd() {
        if (msfRequestVo == null) {
            return null;
        }
        String shopCd = StringUtil.NVL(msfRequestVo.getShopCd(), "").trim();
        String shopNm = StringUtil.NVL(msfRequestVo.getShopNm(), "").trim();

        if (shopCd.isEmpty() && shopNm.isEmpty()) {
            return null;
        }
        if (!shopCd.isEmpty() && !shopNm.isEmpty()) {
            return  shopNm + " / " + shopCd; // ex) 엠통신 / V000010054
        }
        return shopCd.isEmpty() ? shopNm : shopCd;
    }

    // 핸드폰 대금 0: 일시불, 1:할부
    public String getCustomModelMonthlyPricdCd() {

        if (msfRequestSaleVo == null || msfRequestSaleVo.getModelMonthly() == null) {
            return null;
        }
        String monthlyStr = msfRequestSaleVo.getModelMonthly().replaceAll("[^0-9]", "");
        if (monthlyStr.isEmpty()) {
            return null;
        }

        try {
            int monthly = Integer.parseInt(monthlyStr);
            return monthly <= 1 ? "0" : "1";
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // 고객구분 셋팅
    public String getCustomCstmrTypeCd() {

        if (msfRequestVo == null || msfRequestCstmrVo == null) {
            return null;
        }

        String cstmrTypeCd = "";
        if ("NA".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
            || "FN".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
        ) {
            cstmrTypeCd = "I"; // 개인
            if (!"".equals(StringUtil.NVL(msfRequestCstmrVo.getCstmrPrivateBizNo(), ""))) {
                cstmrTypeCd = "O"; // 개인사업자
            }
        } else if ("NM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
            || "FM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
        ) {
            cstmrTypeCd = "I"; // 개인
        } else if ("JP".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))) {
            cstmrTypeCd = "B"; // 법인
        } else {
            cstmrTypeCd = "E"; // 공공
        }
        return cstmrTypeCd;
    }

    // 추가 지원금 셋팅
    public Long getCustomAddDcAmt() {
        if (msfRequestSaleVo == null || msfRequestSaleVo.getModelDiscount3() == null) {
            return 0L; // 또는 기획에 따라 null
        }
        //return msfRequestSaleVo.getModelDiscount3();
        return 0L; // 고정값 처리 MCP_REQUEST 에 대리점 지원금이 안들어오므로 우리쪽도 0으로 보여주기 위해
    }

    // 할인요금 셋팅 = 약정할인요금 + 평생할인요금
    public Long getCustomDcAmt() {
        if(msfRequestSaleVo == null
            || (msfRequestSaleVo.getDcAmt() == null
            && msfRequestSaleVo.getDisPrmtAmt() == null)
        ){
            return 0L;
        }
        long dcAmt = msfRequestSaleVo.getDcAmt() == null ? 0L : msfRequestSaleVo.getDcAmt(); // 약정할인금액
        long disPrmtAmt = msfRequestSaleVo.getDisPrmtAmt() == null ? 0L : msfRequestSaleVo.getDisPrmtAmt(); // 평생금액

        return dcAmt + disPrmtAmt;
    }

    // 월 할부금 셋팅
    public Long getCustomRealMdlInstamt() {
        // 1. Vo 자체가 없거나, 총 할부금(ModelInstamt)이 null이면 안전하게 0L 반환
        if (msfRequestSaleVo == null || msfRequestSaleVo.getModelInstamt() == null) {
            return 0L;
        }

        // 2. 숫자가 아닌 문자를 제거한 순수 숫자 문자열 추출
        String monthlyStr = StringUtil.NVL(msfRequestSaleVo.getModelMonthly(), "").replaceAll("[^0-9]", "");

        // 3. 추출한 문자열이 빈 값인지 체크
        if ("".equals(monthlyStr)) {
            return 0L;
        }

        // 4. 안전하게 할부 개월수 파싱
        long modelMonthly = Long.parseLong(monthlyStr);
        long totalInstamt = msfRequestSaleVo.getModelInstamt();

        // 5. 할부 개월수가 1개월 초과일 때 반올림 연산 수행
        if (modelMonthly > 1) {
            // double 캐스팅 후 반올림을 적용하여 13,291.666... -> 13,292원으로 변환
            return Math.round((double) totalInstamt / modelMonthly);
        }

        // 6. 일시불이거나 1개월 이하인 경우 총 금액 그대로 반환
        return totalInstamt;
    }

    // 고객수납금 셋팅
    public Long getCustomCustPayAmt() {
        if (msfRequestSaleVo == null || msfRequestSaleVo.getModelMonthly() == null) {
            return 0L;
        }

        // 숫자가 아닌 문자 제거
        String monthlyStr = msfRequestSaleVo.getModelMonthly().replaceAll("[^0-9]", "");
        // 할부개월수 문자열이 너무 길면 부적절한 데이터로 판단하고 안전하게 0L 반환 (오버플로우 원천 차단)
        if (monthlyStr.isEmpty() || monthlyStr.length() > 3) {
            return 0L;
        }

        try {
            // 할부개월수 파싱 (Long으로 안전하게 처리)
            long monthly = Long.parseLong(monthlyStr);

            // 💡 [개선] 명확하게 일시불(0) 일 때만 출고가 기반 계산식 수행
            if (monthly == 0L) {
                long modelPrice = msfRequestSaleVo.getModelPrice() == null ? 0L : msfRequestSaleVo.getModelPrice();
                long modelSprt = msfRequestSaleVo.getModelSprt() == null ? 0L : msfRequestSaleVo.getModelSprt();
                long modelDiscount3 = 0L; // TODO: 추후 필요시 VO에서 매핑
                long etcDcAmt = 0L;       // TODO: 추후 필요시 VO에서 매핑
                long resultAmt = modelPrice - modelSprt - modelDiscount3 - etcDcAmt;
                // 💡 [개선] 계산 결과가 음수(-)가 나오는 경우를 대비해 최솟값을 0원으로 방어
                return Math.max(0L, resultAmt);
            }
            // monthly > 0 (할부 있음) 일 경우 0원 반환
            return 0L;
        } catch (NumberFormatException e) {
            // 혹시 모를 파싱 예외 발생 시 안전하게 0 처리
            return 0L;
        }
    }

    // 할부원금 셋팅
    public Long getCustomModelInstallment() {
        if (msfRequestSaleVo == null || msfRequestSaleVo.getModelInstamt() == null) {
            return 0L; // 또는 기획에 따라 null
        }
        return msfRequestSaleVo.getModelInstamt();
    }

    // 월 평균할부수수료 셋팅
    public Long getCustomAvgInstFee() {
        // 1. 객체 및 할부금 유효성 체크
        if (msfRequestSaleVo == null || msfRequestSaleVo.getModelInstamt() == null) {
            return 0L;
        }

        long modelInstamt = msfRequestSaleVo.getModelInstamt(); // 입력 할부원금 (예: 319,000원)
        long modelMonthly = 0L;
        String monthlyStr = StringUtil.NVL(msfRequestSaleVo.getModelMonthly(), "").replaceAll("[^0-9]", "");
        if (!monthlyStr.isEmpty()) {
            modelMonthly = Long.parseLong(monthlyStr);
        }

        // 2. 일시불이거나 1개월 이하인 경우 수수료 0원 반환
        if (modelMonthly <= 1) {
            return 0L;
        }

        // 3. 연 5.9% 원리금 균등상환 수식 정밀 연산
        BigDecimal principal = BigDecimal.valueOf(modelInstamt);

        // 월 이율 = 0.059 / 12 (소수점 10자리)
        BigDecimal monthlyRate = new BigDecimal("0.059").divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);

        // (1 + r)^n 연산
        BigDecimal onePlusRPowN = BigDecimal.ONE.add(monthlyRate).pow((int) modelMonthly);

        // 원리금 균등상환 분자/분모
        BigDecimal numerator = monthlyRate.multiply(onePlusRPowN);
        BigDecimal denominator = onePlusRPowN.subtract(BigDecimal.ONE);

        // rawPayment 연산 (14,123.9050646758... 원)
        BigDecimal rawPayment = principal.multiply(numerator).divide(denominator, 10, RoundingMode.HALF_UP);

        // 💡 [핵심] 통신사 청구 방식: 소수점 이하 절사/버림 (FLOOR) -> 14,123원 확정 (하드코딩 제거!)
        long monthlyPayment = rawPayment.setScale(0, RoundingMode.FLOOR).longValue();

        // 4. 총 납부금액, 총 수수료, 월평균 수수료 산출
        long totalPayment = monthlyPayment * modelMonthly;                  // 14,123 * 24 = 338,952원
        long totalInterest = totalPayment - modelInstamt;                   // 338,952 - 319,000 = 19,952원
        long avgFee = Math.round((double) totalInterest / modelMonthly);     // 19,952 / 24 = 831원

        return avgFee;
    }

    // 선택한 요금상품 이동통신요금
    public String getCustomSocCode() {
        if (msfRequestSaleVo == null) {
            return null;
        }
        return msfRequestSaleVo.getSocNm();
    }

    // 선택한 요금상품 약정할인_핸드폰할인
    public String getCustomSocCodeNm() {
        if (msfRequestSaleVo == null || !"KD".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
        ) {
            return null;
        }
        return msfRequestSaleVo.getSocNm();
    }

    // 선택한 요금상품 약정할인_요금할인
    public String getCustomEnggSocCodeNm() {
        if (msfRequestSaleVo == null
            || (!"PM".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
                && !"SM".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), "")))
        ) {
            return null;
        }
        return msfRequestSaleVo.getSocNm();
    }

    // A. 핸드폰 월 납부액 셋팅
    public Long getCustomPhoneMonthPayAmt() {
        if (msfRequestSaleVo == null) {
            return 0L;
        }

        return getCustomRealMdlInstamt() + getCustomAvgInstFee();
    }

    // 총 지원금 셋팅
    public Long getCustomPhoneTotSubsidyAmt() {
        if (msfRequestSaleVo == null) {
            return 0L;
        }

        long modelSprt = 0L;
        long modelDiscoutn3 = 0L;
        if (msfRequestSaleVo.getModelSprt() != null) {
            modelSprt = msfRequestSaleVo.getModelSprt();
        }
        if (msfRequestSaleVo.getModelDiscount3() != null) {
            //modelDiscoutn3 = msfRequestSaleVo.getModelDiscount3();
            modelDiscoutn3 = 0L;
        }
        return modelSprt + modelDiscoutn3;
    }

    // 단말할인요금 셋팅
    public Long getCustomDeviceDiscountAmt() {
        if (msfRequestSaleVo == null) {
            return 0L;
        }
        if ("KD".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))) {
            long modelSprt = 0L;
            long modelDiscoutn3 = 0L;
            if (msfRequestSaleVo.getModelSprt() != null) {
                modelSprt = msfRequestSaleVo.getModelSprt();
            }
            if (msfRequestSaleVo.getModelDiscount3() != null) {
                //modelDiscoutn3 = msfRequestSaleVo.getModelDiscount3();
                modelDiscoutn3 = 0L;
            }
            return modelSprt + modelDiscoutn3;
        }
        return 0L;
    }

    // 요금할인(지원금) 셋팅
    public Long getCustomPlanDiscountAmt() {
        if (msfRequestSaleVo == null || !"PM".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))) {
            return 0L;
        }

        long dcAmt = getCustomDcAmt();
        long enggMnthCnt = msfRequestSaleVo.getEnggMnthCnt() == null ? 0L : msfRequestSaleVo.getEnggMnthCnt();

        if (enggMnthCnt == 0) {
            return dcAmt;
        } else {
            return dcAmt * enggMnthCnt;
        }
    }

    // 무선서비스 요금할인(지원금) 셋팅
    public Long getCustomPlanDiscountAmt3() {
        if (msfRequestSaleVo == null || !"PM".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))) {
            return 0L;
        }

        return msfRequestSaleVo.getDcAmt() == null ? 0L : msfRequestSaleVo.getDcAmt();
    }

    // 무선서비스 B. 통신요금 월 납부액 셋팅
    public Long getCustomTelecomMonthPay() {
        if (msfRequestSaleVo == null) {
            return 0L;
        }
        // 월정액요금 - 월요금할인(평생할인) - 월요금할인(약정할인)
        long socBaseChrgAmt = msfRequestSaleVo.getSocBaseChrgAmt() == null ? 0L : msfRequestSaleVo.getSocBaseChrgAmt();
        long dcAmt = msfRequestSaleVo.getDcAmt() == null ? 0L : msfRequestSaleVo.getDcAmt();
        long disPrmtAmt = msfRequestSaleVo.getDisPrmtAmt() == null ? 0L : msfRequestSaleVo.getDisPrmtAmt();
        return socBaseChrgAmt - dcAmt - disPrmtAmt;
    }

    // C. 월 기본납부액 셋팅
    public Long getCustomBaseMonthPay() {
        if (msfRequestSaleVo == null) {
            return 0L;
        }

        return getCustomPhoneMonthPayAmt() + getCustomTelecomMonthPay();
    }

    // 핸드폰일련번호
    public String getCustomReqPhoneSn() {
        if(msfRequestVo == null
            || ("JP".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
            && msfRequestVo.getVolumeMobileNoQnty() != null
            && Constants.OPER_TYPE_NEW.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), "")))

        ){
            // 대량법인의 경우 리턴 처리
            return null;
        }
        return msfRequestVo.getReqPhoneSn();
    }

    // 고객 타입이 법인(JP) 이면 법인명 그 외 고객명 (공공기관(GO) 체크 필요)
    //고객명 (법인명 셋팅)
    public String getCustomCstmrName() {
        if (msfRequestVo == null || msfRequestCstmrVo == null) {
            return null;
        }
        //return ("JP".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")) || "GO".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")))
        //    ? StringUtil.NVL(msfRequestCstmrVo.getCstmrJuridicalCname(), "")
        //    : StringUtil.NVL(msfRequestCstmrVo.getCstmrNm(), "");
        return StringUtil.NVL(msfRequestCstmrVo.getCstmrNm(), "");
    }

    // 무선표준계약서 가입자서명여부
    public String getCustomEnggSignYn() {
        return "Y";
    }

    //개인(생년월일) / 법인(등록번호) 셋팅
    public String getCustomCstmrNativeRrn() {
        if (msfRequestVo == null || msfRequestCstmrVo == null) {
            return null;
        }

        String cstmrTypeCd = StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "");

        // 1. 법인(JP) 및 공공기관(GO)
        if ("JP".equals(cstmrTypeCd) || "GO".equals(cstmrTypeCd)) {
            String juridicalRrn = StringUtil.NVL(msfRequestCstmrVo.getCstmrJuridicalRrn(), "");
            String juridicalBizNo = StringUtil.NVL(msfRequestCstmrVo.getCstmrJuridicalBizNo(), "");

            // 법인번호가 있으면 법인번호, 없으면 사업자번호, 둘 다 없으면 null
            if (!"".equals(juridicalRrn)) {
                return juridicalRrn;
            } else if (!"".equals(juridicalBizNo)) {
                return juridicalBizNo;
            } else {
                return null;
            }
        }
        // 2. 개인 (외국인: FN, FM / 내국인: NA, NM 및 기타)
        else {
            if ("FN".equals(cstmrTypeCd) || "FM".equals(cstmrTypeCd)) {
                if(!"".equals(StringUtil.NVL(msfRequestCstmrVo.getCstmrForeignerBirth(), ""))){
                    return msfRequestCstmrVo.getCstmrForeignerBirth();
                }else{
                    return msfRequestCstmrVo.getCstmrForeignerRrn();
                }
            } else {
                if(!"".equals(StringUtil.NVL(msfRequestCstmrVo.getCstmrNativeBirth(), ""))){
                    return msfRequestCstmrVo.getCstmrNativeBirth();
                }else{
                    return msfRequestCstmrVo.getCstmrNativeRrn();
                }
            }
        }
    }

    // 외국인 등록번호(외국인의 경우)
    public String getCustomCstmrForeignerRrn() {
        if (msfRequestVo == null
            || msfRequestCstmrVo == null
            || (!"FN".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")) && !"FM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")))
        ) {
            return null;
        }
        return msfRequestCstmrVo.getCstmrForeignerRrn();
    }

    // 국적(외국인의 경우)
    public String getCustomCstmrForeignerNation() {
        if (msfRequestVo == null
            || msfRequestCstmrVo == null
            || (!"FN".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")) && !"FM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")))
        ) {
            return null;
        }
        return msfRequestCstmrVo.getCstmrForeignerNation();
    }

    // 여권번호(외국인의 경우)
    public String getCustomCstmrForeignerPn() {
        if (msfRequestVo == null
            || msfRequestCstmrVo == null
            || (!"FN".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")) && !"FM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")))
        ) {
            return null;
        }
        return msfRequestCstmrVo.getCstmrForeignerPn();
    }

    // 체류기간(외국인의 경우) 시작일자
    public String getCustomCstmrForeignerSdate() {
        if (msfRequestVo == null
            || msfRequestCstmrVo == null
            || StringUtil.isBlank(msfRequestCstmrVo.getCstmrForeignerVdateStartDate())
            || (!"FN".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")) && !"FM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")))
        ) {
            return null;
        }
        return msfRequestCstmrVo.getCstmrForeignerVdateStartDate().replace("-", "");
    }

    // 체류기간(외국인의 경우) 종료일자
    public String getCustomCstmrForeignerEdate() {
        if (msfRequestVo == null
            || msfRequestCstmrVo == null
            || StringUtil.isBlank(msfRequestCstmrVo.getCstmrForeignerVdateEndDate())
            || (!"FN".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")) && !"FM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")))
        ) {
            return null;
        }
        return msfRequestCstmrVo.getCstmrForeignerVdateEndDate().replace("-", "");
    }

    // 성별 셋팅
    public String getCustomGender() {
        if (msfRequestVo == null
            || msfRequestCstmrVo == null
            || "GO".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")) // 공공기관
        ) {
            return null;
        }
        return ("JP".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))) // 법인
            ? extractGender(msfRequestCstmrVo.getCstmrJuridicalBirth())
            : ("FN".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")) || "FM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")))
                //? extractGender(msfRequestCstmrVo.getCstmrForeignerRrn())
                ? msfRequestCstmrVo.getCstmrForeignerGenderCd()
                : msfRequestCstmrVo.getCstmrNativeGenderCd();
    }

    // 연락받을 전화번호 셋팅
    public String getCustomCstmrReceiveTelNo() {
        if (msfRequestCstmrVo == null) {
            return null;
        }
        String cleanCombinedTel = getCleanCombinedNo(msfRequestCstmrVo.getCstmrMobileFnNo(),
            msfRequestCstmrVo.getCstmrMobileMnNo(),
            msfRequestCstmrVo.getCstmrMobileRnNo());

        if (cleanCombinedTel.isEmpty()) {
            return null;
        } else {
            return cleanCombinedTel;
        }
    }

    // 주소 셋팅 cstmrAddr = cstmrAdr + cstmrAdrDtl
    public String getCustomCstmrAddr() {
        if (msfRequestCstmrVo == null) {
            return null;
        }
        String info = msfRequestCstmrVo.getCstmrAdr() != null ? msfRequestCstmrVo.getCstmrAdr() : "";
        String dtl = msfRequestCstmrVo.getCstmrAdrDtl() != null ? msfRequestCstmrVo.getCstmrAdrDtl() : "";
        return (info + " " + dtl).trim();
    }

    // 명세서 종류 코드
    public String getCustomCstmrBillSendCode() {
        if (msfRequestBillReqVo == null
            || StringUtil.isBlank(msfRequestBillReqVo.getCstmrBillSendTypeCd())
            || msfRequestVo == null
            || Constants.OPER_TYPE_CHANGE.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), ""))
            || Constants.OPER_TYPE_EXCHANGE.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), ""))
        ) {
            return null;
        }

        return msfRequestBillReqVo.getCstmrBillSendTypeCd();
    }

    // (요금)자동납부_구분 셋팅
    public String getCustomReqPayType() {
        if (msfRequestBillReqVo == null
            || msfRequestVo == null
            || Constants.OPER_TYPE_CHANGE.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), ""))
            || Constants.OPER_TYPE_EXCHANGE.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), ""))
        ) {
            return null;
        }
        return msfRequestBillReqVo.getReqPayTypeCd();
    }

    public String getCustomReqPayTypeCd() {

        if (msfRequestBillReqVo == null
            || msfRequestVo == null
            || Constants.OPER_TYPE_CHANGE.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), ""))
            || Constants.OPER_TYPE_EXCHANGE.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), ""))
        ) {
            return null;
        }
        String reqPayTypeCd = "";

        if ("D".equals(StringUtil.NVL(msfRequestBillReqVo.getReqPayTypeCd(), ""))
            || "C".equals(StringUtil.NVL(msfRequestBillReqVo.getReqPayTypeCd(), ""))
            || "R".equals(StringUtil.NVL(msfRequestBillReqVo.getReqPayTypeCd(), ""))
        ) {
            reqPayTypeCd = "D"; // 자동이체
        }

        if ("Y".equals(StringUtil.NVL(msfRequestBillReqVo.getOthersPaymentYn(), ""))) {
            reqPayTypeCd = "O"; // 타인납부동의
        }

        if ("0".equals(StringUtil.NVL(msfRequestBillReqVo.getOthersPaymentYn(), ""))) {
            reqPayTypeCd = "0"; // 통합청구
        }
        return reqPayTypeCd;
    }

    // 타인납부동의 / 통합청구 예금주 명 셋팅
    public String getCustomOthersPaymentNm(){
        if(msfRequestBillReqVo == null){
            return null;
        }

        if(!"".equals(StringUtil.NVL(msfRequestBillReqVo.getPrntsBillNo(), ""))){
            return StringUtil.NVL(msfRequestCstmrVo.getCstmrNm(), "") + "(통합청구)";
        }else if ("Y".equals(StringUtil.NVL(msfRequestBillReqVo.getOthersPaymentYn(), ""))) {
            return msfRequestBillReqVo.getOthersPaymentNm();
        }
        return null;
    }

    // 타인납부동의 생년월일 셋팅
    public String getCustomOthersPaymentRrn() {
        if (msfRequestBillReqVo == null) {
            return null;
        }
        String othersPaymentRrn = "";
        if ("Y".equals(StringUtil.NVL(msfRequestBillReqVo.getOthersPaymentYn(), ""))) {
            othersPaymentRrn = msfRequestBillReqVo.getOthersPaymentRrn(); // 타인납부동의 생년월일
        }
        return othersPaymentRrn;
    }

    // 계좌/카드번호 셋팅
    public String getCustomAutoPayAcctCardNo() {
        if (msfRequestBillReqVo == null) {
            return null;
        }
        String autoPayAcctCardNo = "";
        if ("D".equals(StringUtil.NVL(msfRequestBillReqVo.getReqPayTypeCd(), ""))) {
            //은행
            autoPayAcctCardNo = msfRequestBillReqVo.getReqAccountNo();
        } else if ("C".equals(StringUtil.NVL(msfRequestBillReqVo.getReqPayTypeCd(), ""))
            && !"".equals(StringUtil.NVL(msfRequestBillReqVo.getReqCardCompanyCd(), ""))
        ) {
            //카드
            autoPayAcctCardNo = msfRequestBillReqVo.getReqCardNo();
        }

        return autoPayAcctCardNo;
    }

    // 카드 유효기간 셋팅
    public String getCustomAutoPayCardExp() {
        if (msfRequestBillReqVo == null) {
            return null;
        }
        String autoPayCardExp = "";
        if ("C".equals(StringUtil.NVL(msfRequestBillReqVo.getReqPayTypeCd(), ""))) {
            autoPayCardExp = "20" + StringUtil.NVL(msfRequestBillReqVo.getReqCardYy(), "") + StringUtil.NVL(msfRequestBillReqVo.getReqCardMm(), "");
        }

        return autoPayCardExp;
    }

    // 부가서비스 가격 셋팅
    public Long getCustomReqAdditionPrice() {
        if(msfRequestVo == null || "".equals(StringUtil.NVL(msfRequestVo.getReqAdditionListNm(), ""))){
            return null;
        }
        return msfRequestVo.getReqAdditionPrice();
    }

    // 가입희망번호/번호연결서비스 셋팅
    public String getCustomWishNoLinkSvc() {
        if (msfRequestVo == null
            || ("JP".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
            && msfRequestVo.getVolumeMobileNoQnty() != null
            && Constants.OPER_TYPE_NEW.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), "")))
        ) {
            // 대량법인의 경우 리턴 처리
            return null;
        }

        if(Constants.OPER_TYPE_NEW.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), ""))
            || Constants.OPER_TYPE_CHANGE.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), ""))
            || Constants.OPER_TYPE_EXCHANGE.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), ""))
        ){
            return msfRequestVo.getOpenNo();
        }else{
            // 1. 가입희망번호 구하기
            String wishNo = getCleanCombinedNo(msfRequestVo.getReqWantFnNo(), msfRequestVo.getReqWantMnNo(), msfRequestVo.getReqWantRnNo());

            // 2. 번호연결서비스 구하기 (msfRequestMoveVo가 null이 아닐 때만)
            String linkNo = "";
            if (msfRequestMoveVo != null) {
                linkNo = getCleanCombinedNo(msfRequestMoveVo.getReqGuideFnNo(), msfRequestMoveVo.getReqGuideMnNo(), msfRequestMoveVo.getReqGuideRnNo());
            }

            // 3. 존재 여부에 따른 리턴 분기 처리
            boolean hasWishNo = !wishNo.isEmpty();
            boolean hasLinkNo = !linkNo.isEmpty();

            // Case 1: 둘 다 있을 때 -> "가입희망번호 / 번호연결서비스"
            if (hasWishNo && hasLinkNo) {
                return wishNo + " / " + linkNo;
            }
            // Case 2: 가입희망번호만 있을 때
            if (hasWishNo) {
                return wishNo;
            }
            // Case 3: 번호연결서비스만 있을 때
            if (hasLinkNo) {
                return linkNo;
            }
        }

        // Case 4: 둘 다 없을 때 -> null 리턴 (기존 로직 유지)
        return null;
    }

    // SIM모델명_USIM/eSIM_구분 셋팅
    public String getCustomUsimKindsCd() {
        if (msfRequestVo == null || msfRequestVo.getUsimKindsCd() == null) {
            return null;
        }

        if ("09".equals(StringUtil.NVL(msfRequestVo.getUsimKindsCd(), ""))) {
            return "09"; // eSIM
        } else{
            return "UU"; // 유심
        }
    }

    public Long getCustomUsimPrice() {
        if(msfRequestVo == null
            || msfRequestSaleVo == null
            || "06".equals(StringUtil.NVL(msfRequestVo.getUsimKindsCd(), ""))
        ){
            return null;
        }
        return msfRequestSaleVo.getUsimPrice();
    }

    // IMEI(일련번호) 셋팅(둘다 셋팅하는 것인지 하나만 셋팅하는지 확인필요)
    public String getCustomImei() {
        if (msfRequestVo == null
            || ("JP".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
            && msfRequestVo.getVolumeMobileNoQnty() != null
            && Constants.OPER_TYPE_NEW.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), "")))
        ) {
            // 대량법인의 경우 리턴 처리
            return null;
        }

        if(!"09".equals(StringUtil.NVL(msfRequestVo.getUsimKindsCd(), ""))){
            return StringUtil.NVL(msfRequestVo.getReqPhoneSn(), "");

        }
        return null;
    }

    // SIM 일련번호
    public String getCustomReqUsimSn() {
        if (msfRequestVo == null
            || ("JP".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
            && msfRequestVo.getVolumeMobileNoQnty() != null
            && Constants.OPER_TYPE_NEW.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), "")))
        ) {
            // 대량법인의 경우 리턴 처리
            return null;
        }
        return msfRequestVo.getReqUsimSn();
    }

    public String getCustomUsimPriceTypeCd() {
        if(msfRequestSaleVo == null || msfRequestVo == null){
            return null;
        }

        if("06".equals(StringUtil.NVL(msfRequestVo.getUsimKindsCd(), ""))
            || "".equals(StringUtil.NVL(msfRequestVo.getUsimKindsCd(), ""))
        ){
            return "N";
        }

        return StringUtil.NVL(msfRequestSaleVo.getUsimPriceTypeCd(), "");
    }

    // 번호이동할 전화번호
    public String getCustomMoveMobileNo() {
        if (msfRequestMoveVo == null
            || msfRequestVo == null
            || !Constants.OPER_TYPE_MOVE_NUM.equals(StringUtil.NVL(msfRequestVo.getOperTypeCd(), ""))
        ) {
            return null;
        }
        String cleanCombinedTel = getCleanCombinedNo(msfRequestMoveVo.getMoveMobileFnNo(),
            msfRequestMoveVo.getMoveMobileMnNo(),
            msfRequestMoveVo.getMoveMobileRnNo());

        if (cleanCombinedTel.isEmpty()) {
            return null;
        } else {
            return cleanCombinedTel;
        }
    }

    // 이번달 사용요금 셋팅
    public String getCustomMoveThismonthPayType() {
        if (msfRequestMoveVo == null) {
            return null;
        }
        String moveThismonthPayTypeCd = StringUtil.NVL(msfRequestMoveVo.getMoveThismonthPayTypeCd(), "");
        return "Y".equals(moveThismonthPayTypeCd) ? "NM" : "SP";
    }

    // 신청일자 셋팅
    public String getCustomReqInDt() {
        if (msfRequestVo == null) {
            return null;
        }
        return this.getReqInDt(msfRequestVo);
    }

    // 법정대리인동의서/위임장_신청일자 셋팅
    public String getCustomGdnFormReqDt() {
        if (msfRequestVo == null || msfRequestCstmrVo == null) {
            return null;
        }

        // gdnFormReqDt 셋팅
        // 방문자가 본인이 아니로 && 법인 / 공공기관 / 내국인(미성년자) / 외국인(미성년자)
        if (!"VMY".equals(msfRequestCstmrVo().getCstmrVisitTypeCd())
            && ("GO".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
            || "NM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
            || "FM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), "")))
        ) {
            return this.getReqInDt(msfRequestVo);
        }
        return null;
    }

    // 약정할인 가입신청서_신청일자 셋팅
    public String getCustomEnggReqDt() {

        // enggReqDt 셋팅
        // KD(단말할인) or PM(요금할인)
        if (msfRequestVo == null
            || msfRequestSaleVo == null
            || (!"KD".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
                && !"PM".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), "")))
        ) {
            return null;
        }
        return this.getReqInDt(msfRequestVo);
    }

    // 무선서비스 요금할인(지원금)할인율 셋팅
    public String getCustomPlanDiscountMnthRate3(){

        if (msfRequestSaleVo == null) {
            return "0";
        }

        // 1. VO에서 데이터를 Long 타입으로 가져옴
        Long monthFeeVat3 = msfRequestSaleVo.getSocBaseChrgAmt(); // 월 정액요금
        Long planDiscountMnthAmt3 = msfRequestSaleVo.getDcAmt(); // 월 요금할인

        // 최종적으로 구해야 하는 변수 선언
        String planDiscountMnthRate3;

        // 2. 예외 처리: 월 정액요금이 없거나 0인 경우, 또는 할인금액이 없는 경우
        if (monthFeeVat3 == null || monthFeeVat3 == 0L || planDiscountMnthAmt3 == null || planDiscountMnthAmt3 == 0L) {
            planDiscountMnthRate3 = "0";
        } else {
            double monthFee = (double) monthFeeVat3;
            double monthDiscount = (double) planDiscountMnthAmt3;

            // 4. 할인율 공식 적용: (월 할인액 / 월 정액요금) * 100
            double rate = (monthDiscount / monthFee) * 100;

            // 5. 소수점 둘째 자리 포맷 설정 (뒤가 .00이면 생략, 있으면 둘째 자리까지 반올림)
            DecimalFormat df = new DecimalFormat("0.##");

            // 6. 포맷팅된 결과를 다시 String 변수에 할당
            planDiscountMnthRate3 = df.format(rate);
        }

        return planDiscountMnthRate3;
    }

    // 휴대폰 안심 보험(안드로이드)_신청일자 셋팅
    public String getCustomAndroidReqDt(){
        /*
        PL245L228 휴대폰안심보험 폴드 180
        PL245L229 휴대폰안심보험 분실파손 150
        PL245L230 휴대폰안심보험 분실파손 100
        PL245L231 휴대폰안심보험 분실파손 70
        PL245L232 휴대폰안심보험 파손 50
        */
        if(msfRequestVo != null
            && ("PL245L228".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
                || "PL245L229".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
                || "PL245L230".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
                || "PL245L231".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
                || "PL245L232".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            )
        ){
            return this.getReqInDt(msfRequestVo);
        }
        return null;
    }

    // 휴대폰 안심 보험(아이폰)_신청일자 셋팅
    public String getCustomIosRegDt(){
        /*
        PL245L235 휴대폰안심보험 I-분실파손 150
        PL245L236 휴대폰안심보험 I-분실파손 90
        PL245L237 휴대폰안심보험 I-파손 50
        PL245L233 휴대폰안심보험 중고 파손 100
        PL245L234 휴대폰안심보험 중고 파손 40
        */
        if(msfRequestVo != null
            && ("PL245L235".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L236".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L237".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L233".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L234".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
        )
        ){
            return this.getReqInDt(msfRequestVo);
        }
        return null;
    }

    // 휴대폰 안심 보험(안드로이드)_신청인 생년월일 셋팅
    public String getCustomAndroidCstmrNativeBirth(){
        if(msfRequestVo != null
            && msfRequestCstmrVo != null
            && ("PL245L228".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L229".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L230".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L231".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L232".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            )
        ) {
            if("JP".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
                || "GO".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))){
                // 법인 / 공공기관
                return msfRequestCstmrVo.getCstmrJuridicalBirth();
            } else if("FN".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
                || "FM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))) {
                // 외국인
                return msfRequestCstmrVo.getCstmrForeignerBirth();
            } else {
                // 내국인
                return msfRequestCstmrVo.getCstmrNativeBirth();
            }
        }
        return null;
    }

    // 휴대폰 안심 보험(아이폰)_신청인 생년월일 셋팅
    public String getCustomIosCstmrNativeBirth(){
        if(msfRequestVo != null
            && msfRequestCstmrVo != null
            && ("PL245L235".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L236".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L237".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L233".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
            || "PL245L234".equals(StringUtil.NVL(msfRequestVo.getInsrProdCd(), ""))
        )
        ) {
            if("JP".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
                || "GO".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))){
                // 법인 / 공공기관
                return msfRequestCstmrVo.getCstmrJuridicalBirth();
            } else if("FN".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))
                || "FM".equals(StringUtil.NVL(msfRequestVo.getCstmrTypeCd(), ""))) {
                // 외국인
                return msfRequestCstmrVo.getCstmrForeignerBirth();
            } else {
                // 내국인
                return msfRequestCstmrVo.getCstmrNativeBirth();
            }
        }
        return null;
    }

    // 약관_개인정보 제3자 제공 동의 지니뮤직 Jini
    public String getCustomJehuPartnerJiniYn() {
        return clausePriOfferYn(msfRequestVo, "jini");
    }

    // 약관_개인정보 제3자 제공 동의 스토리위즈(블라이스) Story
    public String getCustomJehuPartnerStoryYn() {
        return clausePriOfferYn(msfRequestVo, "story");
    }

    // 약관_개인정보 제3자 제공 동의 브이피(후후), 메리츠화재 Who
    public String getCustomJehuPartnerWhoYn() {
        return clausePriOfferYn(msfRequestVo, "who");
    }

    // 약관_개인정보 제3자 제공 동의 카카오엔터테인먼트(멜론) Ml
    public String getCustomJehuPartnerMlYn() {
        return clausePriOfferYn(msfRequestVo, "ml");
    }

    // 약관_개인정보 제3자 제공 동의 왓차 Wc
    public String getCustomJehuPartnerWcYn() {
        return clausePriOfferYn(msfRequestVo, "wc");
    }

    // 약관_개인정보 제3자 제공 동의 밀리의 서재 Mi
    public String getCustomJehuPartnerMiYn() {
        return clausePriOfferYn(msfRequestVo, "mi");
    }

    // 약관_개인정보 제3자 제공 동의 콘텐츠웨이브주식회사 Wv
    public String getCustomJehuPartnerWvYn() {
        return clausePriOfferYn(msfRequestVo, "wv");
    }

    // 약관_개인정보 제3자 제공 동의 비즈에프리테일 Cu
    public String getCustomJehuPartnerCuYn() {
        return clausePriOfferYn(msfRequestVo, "cu");
    }

    // 약관_개인정보 제3자 제공 동의 플렌티넷, 플래티엠 Mz
    public String getCustomJehuPartnerMzYn() {
        return clausePriOfferYn(msfRequestVo, "mz");
    }

    // 약관_개인정보 제3자 제공 동의 케이티알파 Alpha
    public String getCustomJehuPartnerAlphaYn() {
        return clausePriOfferYn(msfRequestVo, "alpha");
    }

    // 약관_개인정보 제3자 제공 동의 롯데멤버스 Lotte
    public String getCustomJehuPartnerLotteYn() {
        return clausePriOfferYn(msfRequestVo, "lotte");
    }

    // 약관_개인정보 제3자 제공 동의 케이뱅크 Kbank
    public String getCustomJehuPartnerKbankYn() {
        return clausePriOfferYn(msfRequestVo, "kbank");
    }

    // 약관_개인정보 제3자 제공 동의 스토리위즈 Story2
    public String getCustomJehuPartnerStory2Yn() {
        return clausePriOfferYn(msfRequestVo, "story2");
    }

    // 약관_㈜밀리의서재(밀리의서재), ㈜브이피(후후), ㈜메리츠화재
    public String getCustomJehuPartnerMiWhoYn() {
        if("Y".equals(clausePriOfferYn(msfRequestVo, "who"))
            || "Y".equals(clausePriOfferYn(msfRequestVo, "mi"))){
            return "Y";
        }else{
            return "N";
        }
    }

    // 법정대리인동의서/위임장_동의서 생년월일 셋팅
    public String getCustomMinorAgentRrn() {
        if (msfRequestAgentVo == null) {
            return null;
        }
        return msfRequestAgentVo.getMinorAgentBirth();
    }

    // 법정대리인동의서/위임장_동의서 성별 셋팅
    public String getCustomMinorAgentGender() {
        if (msfRequestAgentVo == null) {
            return null;
        }
        return extractGender(msfRequestAgentVo.getMinorAgentRrn());
    }

    // 법정대리인동의서/위임장_동의서 연락받을 전화번호 셋팅
    public String getCustomMinorAgentTelNo() {
        if (msfRequestAgentVo == null) {
            return null;
        }
        String cleanCombinedTel = getCleanCombinedNo(msfRequestAgentVo.getMinorAgentTelFnNo(),
            msfRequestAgentVo.getMinorAgentTelMnNo(),
            msfRequestAgentVo.getMinorAgentTelRnNo());

        if (cleanCombinedTel.isEmpty()) {
            return null;
        } else {
            return cleanCombinedTel;
        }
    }

    // 법정대리인동의서/위임장_위임장 위임하시는 분
    public String getCustomMinorDelegator() {
        if (msfRequestAgentVo == null || msfRequestCstmrVo == null) {
            return null;
        }
        return msfRequestCstmrVo.getCstmrJuridicalRepNm();
    }

    // 법정대리인동의서/위임장_위임장 위임받는 분
    public String getCustomMinorAgent() {
        if (msfRequestAgentVo == null) {
            return null;
        }
        return msfRequestAgentVo.getJrdclAgentNm();
    }

    // 법정대리인동의서/위임장_위임장 위임받는 분 생년월일
    public String getCustomMinorAgentRrn2() {
        if (msfRequestAgentVo == null) {
            return null;
        }
        String jrdclAgentRrn = StringUtil.NVL(msfRequestAgentVo.getJrdclAgentRrn(), "").trim();
        if (jrdclAgentRrn.length() < 8) {
            return jrdclAgentRrn;
        }
        return jrdclAgentRrn.substring(0, 8);
    }

    // 법정대리인동의서/위임장_위임장 위임받는 분 성별
    public String getCustomMinorAgentGender2() {
        if (msfRequestAgentVo == null) {
            return null;
        }
        // 화면에서 생년월일(YYYYMMDD) + 1 or 2 로 들어옴.
        String jrdclAgentRrn = StringUtil.NVL(msfRequestAgentVo.getJrdclAgentRrn(), "").trim().replace("-", "");
        if (jrdclAgentRrn.length() >= 9) {
            char genderChar = jrdclAgentRrn.charAt(8); // 변수명 jrdclAgentRrn으로 통일
            int genderNum = Character.getNumericValue(genderChar);

            return (genderNum % 2 != 0) ? "M" : "F";
        }
        return null;

    }

    // 법정대리인동의서/위임장_위임장 연락받을 전화번호
    public String getCustomMinorAgentTelNo2() {
        if (msfRequestAgentVo == null) {
            return null;
        }
        String cleanCombinedTel = getCleanCombinedNo(msfRequestAgentVo.getJrdclAgentTelFnNo(),
            msfRequestAgentVo.getJrdclAgentTelMnNo(),
            msfRequestAgentVo.getJrdclAgentTelRnNo());

        if (cleanCombinedTel.isEmpty()) {
            return null;
        } else {
            return cleanCombinedTel;
        }
    }


    // 약정할인가입신청서_핸드폰할인_약정기간 셋팅
    public Long getCustomModelEnggMnthCnt() {
        if (msfRequestSaleVo == null || !"KD".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
        ) {
            return null;
        }
        return msfRequestSaleVo.getEnggMnthCnt();
    }

    // 약정할인가입신청서_핸드폰할인_단말지원금(공통지원금) 셋팅
    public Long getCustomModelSprt() {
        if (msfRequestSaleVo == null
            || !"KD".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
            || msfRequestVo == null
            || "UU".equals(StringUtil.NVL(msfRequestVo.getReqBuyTypeCd(), ""))
        ) {
            return null;
        }
        return msfRequestSaleVo.getModelSprt();
    }

    // 무선표준계약서 단말지원금
    public Long getCustomModelSprt3() {
        if(msfRequestVo != null && "UU".equals(StringUtil.NVL(msfRequestVo.getReqBuyTypeCd(), ""))){
          return 0L;
        }
        if (msfRequestSaleVo == null
            || !"KD".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
        ) {
            return null;
        }
        return msfRequestSaleVo.getModelSprt();
    }

    // 약정할인가입신청서_요금할인_약정기간 셋팅
    public Long getCustomEnggMnthCnt() {
        if (msfRequestSaleVo == null
            || (!"PM".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
            && !"SM".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), "")))

        ) {
            return null;
        }
        return msfRequestSaleVo.getEnggMnthCnt();
    }

    public Long getCustomEnggMnthCnt3() {
        if (msfRequestSaleVo == null
            || msfRequestSaleVo.getEnggMnthCnt() == null
            || msfRequestVo == null
            || "UU".equals(StringUtil.NVL(msfRequestVo.getReqBuyTypeCd(), ""))
        ){
            return 0L;
        }
        return msfRequestSaleVo.getEnggMnthCnt();
    }

    // 요금 할인_할인프로그램 셋팅
    public String getCustomDiscountProg() {
        if (msfRequestSaleVo != null && !"".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))) {
            return msfRequestSaleVo.getSprtTypeCd();
        }
        return null;
    }

    // 월 요금할인(VAT 포함) 셋팅
    public Long getCustomMonthFeeDcVatAmt() {
        if (msfRequestSaleVo == null
            || (!"PM".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
            && !"SM".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), "")))

        ) {

            return null;
        }

        long dcAmt = getCustomDcAmt();
        long enggMnthCnt = msfRequestSaleVo.getEnggMnthCnt() == null ? 0L : msfRequestSaleVo.getEnggMnthCnt();

        if (enggMnthCnt == 0) {
            return dcAmt;
        }
        // 한쪽을 (double)로 캐스팅하여 소수점 계산을 수행한 뒤, 반올림(Math.round) 처리
        return Math.round((double) dcAmt / enggMnthCnt);
    }

    // 이동통신 단말기보험 상품설명서 보험 코드 셋팅
    public String getCustomInsrProdCd() {
        if (msfRequestVo == null) {
            return null;
        }
        return msfRequestVo.getInsrProdCd();
    }

    // 서식지ID, 안면트랜잭션ID
    public String getCustomAuthInfo() {
        if (msfRequestVo == null) {
            return null;
        }
        // null인 경우 빈 문자열("")로 안전하게 변환
        String scanId = StringUtil.NVL(msfRequestVo.getScanId(), "").trim();
        String fathTransacId = StringUtil.NVL(msfRequestVo.getFathTransacId(), "").trim();

        boolean hasScanId = !scanId.isEmpty();
        boolean hasFathTransacId = !fathTransacId.isEmpty();

        // 1. 둘 다 있는 경우 -> 콤마로 연결
        if (hasScanId && hasFathTransacId) {
            return scanId + "," + fathTransacId;
        }
        // 2. scanId만 있는 경우
        if (hasScanId) {
            return scanId;
        }
        // 3. fathTransacId만 있는 경우 (또는 둘 다 없는 경우 "" 반환)
        return fathTransacId;
    }

    // 총 분할상환수수료 셋팅
    public String getCustomModelInstFee3() {

        if (msfRequestSaleVo == null
            || msfRequestSaleVo.getModelInstamt() == null
            || msfRequestSaleVo.getModelMonthly() == null
        ) {
            return "0";
        }

        String monthlyStr = StringUtil.NVL(msfRequestSaleVo.getModelMonthly(), "").replaceAll("[^0-9]", "");

        if (monthlyStr.isEmpty()) {
            return "0";
        }

        // 입력 조건
        BigDecimal principal = new BigDecimal(msfRequestSaleVo.getModelInstamt()); // 할부원금 (P)
        int months = Integer.parseInt(monthlyStr);                                 // 할부기간 (n)

        if (months <= 1) {
            return "0"; // 일시불/1개월은 수수료 0원
        }

        BigDecimal annualRate = new BigDecimal("0.059"); // 연 수수료율 (5.9%)

        // 1. 월 이자율 계산 (연 이자율 / 12)
        BigDecimal monthlyRate = annualRate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);

        // 2. 원리금 균등상환공식: M = P * [r(1+r)^n] / [(1+r)^n - 1]
        BigDecimal onePlusRPowN = BigDecimal.ONE.add(monthlyRate).pow(months);

        BigDecimal numerator = monthlyRate.multiply(onePlusRPowN);       // 분자: r(1+r)^n
        BigDecimal denominator = onePlusRPowN.subtract(BigDecimal.ONE);  // 분모: (1+r)^n - 1

        // 원리금 균등 연산 (14,123.905...원)
        BigDecimal rawMonthlyPayment = principal.multiply(numerator).divide(denominator, 10, RoundingMode.HALF_UP);

        // 3. 월 단말요금 (★ 통신사 전산 표준: 소수점 이하 버림 FLOOR)
        // 14,123.905...원 -> 14,123원 확정
        BigDecimal monthlyPayment = rawMonthlyPayment.setScale(0, RoundingMode.FLOOR);

        // 4. 총 납부금액 = 월 단말요금 * 할부기간 (14,123 * 24 = 338,952원)
        BigDecimal totalPayment = monthlyPayment.multiply(new BigDecimal(months));

        // 5. 총할부수수료 = 총 납부금액 - 할부원금 (338,952 - 319,000 = 19,952원)
        BigDecimal totalFee = totalPayment.subtract(principal);

        return totalFee.toString(); // "19952" 반환
    }

    // 해지위약금% 셋팅
    public String getCustomPenaltyCanRate3() {
        if(msfRequestSaleVo == null
            || !"KD".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
        ){
            return null;
        }
        return "0";
    }

    // 기기변경위약금% 셋팅
    public String getCustomPenaltyChgRate3() {
        if(msfRequestSaleVo == null
            || !"KD".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
        ){
            return null;
        }
        return "0";
    }

    // 위약금(12개월째) 셋팅
    public String getCustomPenalty12AmtMnth3() {
        return calculateCustomPenalty(12);
    }

    // 위약금(18개월째) 셋팅
    public String getCustomPenalty18AmtMnth3() {
        return calculateCustomPenalty(18);
    }

    /**
     * [공통] 위약금 계산 헬퍼 메소드
     * @param targetMonths 계산 기준 개월 수 (예: 12, 18)
     */
    private String calculateCustomPenalty(int targetMonths) {
        if (msfRequestSaleVo == null
            || msfRequestSaleVo.getSocBaseChrgAmt() == null
            || msfRequestSaleVo.getEnggMnthCnt() == null
            || !"KD".equals(StringUtil.NVL(msfRequestSaleVo.getSprtTypeCd(), ""))
        ) {
            return null;
        }

        Long modelSprt = getCustomModelSprt(); // 공통지원금
        if(modelSprt == null){
            modelSprt = 0L;
        }
        String enggMnthCnt = String.valueOf(msfRequestSaleVo.getEnggMnthCnt());

        // [개선 1] 안전한 유효성 검증
        if (!NumberUtils.isCreatable(enggMnthCnt.trim())) {
            return null;
        }

        int enggMnthCnt3 = Integer.parseInt(enggMnthCnt.trim());

        // [개선 2] 분모가 0이거나 음수인 경우 방어
        if (enggMnthCnt3 <= 0) {
            return "0";
        }

        // [변경] 매개변수 targetMonths를 재할당하지 않고, 새로운 지역 변수를 선언하여 계산
        int remainingMonths = enggMnthCnt3 - targetMonths; // 남은 약정기간

        // [개선 3] 정산 오차 방지를 위해 (지원금 * 대상개월수) / 약정개월수 순으로 계산
        long penaltyAmt = (modelSprt * remainingMonths) / enggMnthCnt3;

        return String.valueOf(penaltyAmt);
    }

    private String getReqInDt(MsfRequestVo msfRequestVo){
        // 💡 1. reqInDt가 null이면 안전하게 빈 문자열이나 null 반환
        if (msfRequestVo.getReqInDt() == null) {
            //return "";
            LocalDateTime now = LocalDateTime.now();
            msfRequestVo.setReqInDt(now);
        }

        // 💡 2. yyyyMMdd 포맷터 정의 (대소문자 주의: MM은 월, dd는 일)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 💡 3. 포맷에 맞춰 문자열로 변환하여 리턴
        return msfRequestVo.getReqInDt().format(formatter);
    }

    private String clausePriOfferYn(MsfRequestVo msfRequestVo, String targetType) {
        if (msfRequestVo == null) {
            return "N";
        }

        String currentType = StringUtil.NVL(msfRequestVo.getJehuPartnerTypeCd(), "").toLowerCase();
        if (targetType.equals(currentType)) {
            return StringUtil.NVL(msfRequestVo.getClausePriOfferYn(), "N");
        }
        return "N";
    }

    private String extractGender(String jumin) {
        if (jumin == null) {
            return null;
        }

        String cleanJumin = jumin.replace("-", "");
        if (cleanJumin.length() == 13) {
            char genderChar = cleanJumin.charAt(6);
            int genderNum = Character.getNumericValue(genderChar);
            return (genderNum % 2 != 0) ? "M" : "F";
        }

        return null; // 13자리가 아니면 null 반환 (기존 성별 유지 혹은 무시)
    }

    private static @NonNull String getCleanCombinedNo(String no1, String no2, String no3) {
        // 1. Null 체크를 하면서 동시에 '숫자'를 제외한 모든 문자(하이픈, 공백 등)를 강제로 제거
        String first = (no1 != null) ? no1.replaceAll("[^0-9]", "") : "";
        String middle = (no2 != null) ? no2.replaceAll("[^0-9]", "") : "";
        String last = (no3 != null) ? no3.replaceAll("[^0-9]", "") : "";

        // 2. 단순히 결합 (이미 특수문자와 공백이 제거되었으므로 안전함)
        return first + middle + last;

    }
}
