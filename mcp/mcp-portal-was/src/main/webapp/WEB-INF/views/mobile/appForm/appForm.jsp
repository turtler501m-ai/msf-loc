<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=24.10.30"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?version=24.11.28"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js?version=24.04.01"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js?version=24.04.08"></script>
        <script src="${smartroScriptUrl}?version=${today}"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/appForm.js?version=26.03.06"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js?version=24.01.11"></script>
        <!-- 카카오 광고 분석  -->
        <script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
        <script type="text/javascript">
            kakaoPixel('5981604067138488988')
        </script>
        <!-- 카카오 광고 분석 end-->
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="mode" name="mode"/>

        <input type="hidden" id="isAuth" name="isAuth"  >
        <input type="hidden" id="isSmsAuth" name="isSmsAuth"  >
        <input type="hidden" id="isAuthInsr" name="isAuthInsr"  >
        <input type="hidden" id="isAuthRwd" name="isAuthRwd">
        <input type="hidden" id="isPassAuth" name="isPassAuth"  >
        <input type="hidden" id="isCheckAccount" name="isCheckAccount" >
        <input type="hidden" id="isCheckCard" name="isCheckCard" >
        <input type="hidden" id="isNpPreCheck" name="isNpPreCheck" >
        <input type="hidden" id="isCheckmember" name="isCheckmember" >
        <input type="hidden" id="isUsimNumberCheck" name="isUsimNumberCheck" >
        <input type="hidden" id="userDivision" name="userDivision" value="${userDivision}" />
        <input type="hidden" id="operType" name="operType" value="${AppformReq.operType}" />
        <input type="hidden" id="operTypeNm" name="operTypeNm" value="${AppformReq.operTypeNm}" />
        <input type="hidden" id="onOffType" name="onOffType" value="${AppformReq.onOffType}" />
        <input type="hidden" id="onOffTypeInit" name="onOffTypeInit" value="${AppformReq.onOffType}" />
        <input type="hidden" id="prdtSctnCd" name="prdtSctnCd" value="${AppformReq.prdtSctnCd}">
        <input type="hidden" id="prdtSctnCdTemp" name="prdtSctnCdTemp" value="${AppformReq.prdtSctnCd}">
        <input type="hidden" id="reqBuyType" name="reqBuyType" value="${AppformReq.reqBuyType}">
        <input type="hidden" id="rprsPrdtId" name="rprsPrdtId" value="${AppformReq.rprsPrdtId}">
        <input type="hidden" id="prdtId" name="prdtId" value="${AppformReq.prdtId}">
        <input type="hidden" id="modelId" name="modelId" value="${AppformReq.prdtId}" >
        <input type="hidden" id="sesplsProdId" name=sesplsProdId value="${AppformReq.sesplsProdId}" >
        <input type="hidden" id="serviceType" name="serviceType" value="${AppformReq.serviceType}" >
        <input type="hidden" id="cntpntShopId" name="cntpntShopId" value="${AppformReq.cntpntShopId}" >
        <input type="hidden" id="socCode" name="socCode" value="${AppformReq.socCode}" >
        <input type="hidden" id="prodId" name="prodId" value="${AppformReq.prodId}" >
        <input type="hidden" id="siteReferer" name="siteReferer" value="${AppformReq.siteReferer}" >
        <input type="hidden" id="reqCnt" name="reqCnt" value="" />
        <input type="hidden" id="requestKeyTemp" name="requestKeyTemp" value="${AppformReq.requestKey}">
        <input type="hidden" id="insrProdCdTemp" name="insrProdCdTemp" value="${AppformReq.insrProdCd}">
        <input type="hidden" id="rwdProdCdTemp" name="rwdProdCdTemp" value="${AppformReq.rwdProdCd}">
        <!-- 확인 필요 -->
        <input type="hidden" id="bannerCd" name="bannerCd" value="${AppformReq.bannerCd}" >
        <input type="hidden" id="modelSalePolicyCode" name="modelSalePolicyCode" value="${AppformReq.modelSalePolicyCode}" >
        <input type="hidden" id="sprtTp" name="sprtTp" value="${AppformReq.sprtTp}" >
        <input type="hidden" id="modelMonthly" name="modelMonthly" value="${AppformReq.modelMonthly}" ><!--단말할부개월수  -->
        <input type="hidden" id="reqModelName" name="reqModelName" value="${AppformReq.reqModelName}" >
        <input type="hidden" id="sntyColorCd" name="sntyColorCd" value="${AppformReq.sntyColorCd}" ><!--단말기모델아이디_색상검색용  -->
        <input type="hidden" id="sntyCapacCd" name="sntyCapacCd" value="${AppformReq.sntyCapacCd}" ><!--단품용량코드  -->
        <input type="hidden" id="enggMnthCnt" name="enggMnthCnt" value="${AppformReq.enggMnthCnt}" ><!--유심에서 약정????  -->
        <input type="hidden" name="usimKindsCd" id="usimKindsCd" value="${AppformReq.usimKindsCd}" />
        <input type="hidden" name="uploadPhoneSrlNo" id="uploadPhoneSrlNo" value="${AppformReq.uploadPhoneSrlNo}" />
        <input type="hidden" name="settlWayCd" id="settlWayCd" value="${AppformReq.settlWayCd}" />
        <input type="hidden" id="requestNo" name="requestNo" >
        <input type="hidden" id="resUniqId" name="resUniqId" >
        <input type="hidden" id="joinPriceIsPay" name="joinPriceIsPay" >
        <input type="hidden" id="usimPriceIsPay" name="usimPriceIsPay" >
        <input type="hidden" id="usimPriceNfcIsPay" name="usimPriceNfcIsPay" >
        <input type="hidden" id="joinPrice" name="joinPrice" >
        <input type="hidden" id="usimPrice" name="usimPrice" >
        <input type="hidden" id="usimPriceBase" name="usimPriceBase" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_OBJ_BASE)}">
        <input type="hidden" id="usimPrice5G" name="usimPrice5G" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_OBJ_5G)}">
        <input type="hidden" id="usimPriceNfc" name="usimPriceNfc" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_USIM_NFC)}">
        <input type="hidden" id="usimPrice3G" name="usimPrice3G" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_OBJ_3G)}">
        <input type="hidden" id="bizOrgCd" name="bizOrgCd" value="" ><!--바로배송 업체코드-->

        <input type="hidden" id="jehuProdType" name="jehuProdType" value="${jehuProdType}" ><!--제휴처 요금제 타입 -->
        <input type="hidden" id="jehuProdName" name="jehuProdName" value="${jehuProdName}" ><!--제휴처 요금제 이름 -->
        <input type="hidden" id="jehuPartnerType" name="jehuPartnerType" value="${jehuPartnerType}" ><!--제휴사타입 -->
        <input type="hidden" id="jehuPartnerName" name="jehuPartnerName" value="${jehuPartnerName}" ><!--제휴사이름 -->

        <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv" > <!--안면인증 결과 -->
        <input type="hidden" id="fathTrgYn" name="fathTrgYn" > <!--안면인증 대상여부 -->


        <form id="tranMgr" name="tranMgr" method="post">
            <!-- 각 값들을 가맹점에 맞게 설정해 주세요. -->
            <input type="hidden" name="PayMethod"   id="PayMethod"  value="CARD"/>
            <input type="hidden" name="GoodsCnt"    id="GoodsCnt"   value=""/>
            <input type="hidden" name="GoodsName"   id="GoodsName"  value=""/> <!-- 거래 상품명 -->
            <input type="hidden" name="Amt"         id="Amt"        value=""/> <!-- 설정한 Amt -->
            <input type="hidden" name="Moid"        id="Moid"       value=""/> <!-- 주문번호 특수문자 포함 불가 -->
            <input type="hidden" name="Mid"         id="Mid"        value=""/> <!-- 설정한 Mid -->
            <input type="hidden" name="ReturnUrl"   id="ReturnUrl"  value=""> <!-- 가맹점 ReturnUrl 설정 -->
            <input type="hidden" name="StopUrl"     id="StopUrl"    value=""/> <!-- 결제중단 URL -->
            <input type="hidden" name="BuyerName"   id="BuyerName"  value=""/> <!-- 구매자명 -->
            <input type="hidden" name="BuyerTel"    id="BuyerTel"   value=""/> <!-- 전화번호 -->
            <input type="hidden" name="BuyerEmail"  id="BuyerEmail" value=""/> <!-- 이메일 -->
            <input type="hidden" name="MallIp"      id="MallIp"     value=""/>
            <input type="hidden" name="VbankExpDate"id="VbankExpDate" value=""/> <!-- 가상계좌 이용 시 필수 -->
            <input type="hidden" name="EncryptData" id="EncryptData" value=""/> <!-- 위/변조방지 HASH 데이터 -->
            <input type="hidden" name="GoodsCl"     id="GoodsCl"    value="1"/> <!-- 0: 컨텐츠, 1: 실물(가맹점 설정에 따라 셋팅, 휴대폰 결제 시 필수)-->
            <input type="hidden" name="EdiDate"     id="EdiDate"    value=""/> <!-- 설정한 EdiDate -->
            <input type="hidden" name="TaxAmt"      id="TaxAmt"     value=""/> <!-- 과세 : 부가세 직접계산 가맹점 필수, 숫자만 가능, 문장부호 제외  -->
            <input type="hidden" name="TaxFreeAmt"  id="TaxFreeAmt" value=""/> <!-- 비과세 : 부가세 직접계산 가맹점 필수,숫자만 가능, 문장부호 제외 -->
            <input type="hidden" name="VatAmt"      id="VatAmt"     value=""/> <!-- 부가세 : 부가세 직접계산 가맹점 필수,숫자만 가능, 문장부호 제외 -->
            <input type="hidden" name="MallReserved"id="MallReserved"   value=""/> <!-- 상점예비필드 -->
            <input type="hidden" name="PayType"id="PayType" value=""/>
            <input type="hidden" name="NonUi"id="NonUi" value="N"/>
            <input type="hidden" name="FnCd"id="FnCd"   value=""/>
        </form>

        <input type="hidden" id="evntCdPrmt" name="evntCdPrmt" value="${AppformReq.evntCdPrmt}" ><!--이벤트코드 프로모션 -->
        <input type="hidden" id="recoUseYn" name="recoUseYn" value="${AppformReq.recoUseYn}" ><!--이벤트코드에 따른 추천인ID 노출여부 -->



        <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />


        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <c:choose>
                    <c:when test="${AppformReq.usimKindsCd eq '09'}">
                        <h2 class="ly-page__head">eSIM 가입하기<button class="header-clone__gnb"></button></h2>
                    </c:when>
                    <c:when test="${AppformReq.onOffType eq '7'}">
                        <h2 class="ly-page__head">셀프개통<button class="header-clone__gnb"></button></h2>
                    </c:when>
                    <c:when test="${AppformReq.onOffType ne '7' && AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM && 'Y' eq AppformReq.sesplsYn}">
                        <h2 class="ly-page__head">자급제 요금제 가입하기<button class="header-clone__gnb"></button></h2>
                    </c:when>
                    <c:when test="${AppformReq.onOffType ne '7' && AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM}">
                        <h2 class="ly-page__head">유심 요금제 가입하기<button class="header-clone__gnb"></button></h2>
                    </c:when>
                    <c:otherwise>
                        <h2 class="ly-page__head">휴대폰 요금제 가입하기<button class="header-clone__gnb"></button></h2>
                    </c:otherwise>
                </c:choose>
                <div class="c-indicator">
                    <span id="spIndicator" style="width: 20%"></span>
                </div>
            </div>

            <!-- STEP1 START -->
            <div id="step1" class="_divStep">
                <div class="c-form-step">
                    <span class="c-form-step__number">1</span>
                    <h3 class="c-form-step__title">본인확인·약관동의</h3>
                </div>

                <div class="c-form app-form-view-content u-mt--24">
                    <div class="c-check-wrap">
                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType1" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NA}" desc="19세 이상 내국인" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NA }" > checked </c:if> >
                            <label class="c-label" for="cstmrType1">
                                <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                                <span>내국인</span>
                            </label>
                        </div>
                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType2" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NM}" desc="19세 미만 미성년자" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NM }" > checked </c:if> <c:if test="${AppformReq.onOffType eq '7' }" > disabled </c:if> >
                            <label class="c-label" for="cstmrType2">
                                <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                                <span class="app-form-view__lable">미성년자<br /><span class="u-fs-13">(19세 미만)</span></span>
                            </label>
                        </div>
                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}" desc="외국인" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_FN }" > checked </c:if> <c:if test="${AppformReq.onOffType eq '7' }" > disabled </c:if>>
                            <label class="c-label" for="cstmrType3">
                                <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                                <span class="app-form-view__lable">외국인<br /><span class="u-fs-13">(Foreigner)</span></span>
                            </label>
                        </div>
                    </div>
                </div>


                <h4 id="inpNameCertiTitle" class="c-form__title--type2">가입자 정보</h4>
                <div class="c-form">
                    <div class="c-form__input ">
                        <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" value="${AppformReq.cstmrName}"  maxlength="60" <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">disabled="disabled"</c:if>>
                        <label class="c-form__label" for="cstmrName">이름</label>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${AppformReq.operType eq Constants.OPER_TYPE_CHANGE ||  AppformReq.operType eq Constants.OPER_TYPE_EXCHANGE  }">
                        <div class="c-form-field device-chg-container _isForeigner" style="display:none;">
                            <div>
                                <div class="c-form__input">
                                    <input type="tel" class="c-input onlyNum mskDvcChg" id="cstmrForeignerRrn01" type="text" autocomplete="off" maxlength="6" placeholder="외국인등록번호 앞자리" title="외국인등록번호 앞자리" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrForeignerRrn02');">
                                    <label class="c-form__label" for="cstmrForeignerRrn01">생년월일</label>
                                </div>
                            </div>
                            <span class="u-fs-22 u-fw--bold u-ta-center device-chg-num-span">-</span>
                            <div class="device-chg-back-num">
                                <div class="c-form__input">
                                    <input type="tel" class="c-input onlyNum u-width--50 mskDvcChg" id="cstmrForeignerRrn02" maxlength="1" placeholder="" aria-invalid="true" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,6,7)}" onkeyup="nextFocus(this, 1, 'changeCstmrMobileFn');">
                                    <label class="c-form__label" for="cstmrForeignerRrn01"></label>
                                </div>
                                <span class="u-fs-13 u-width--105 device-chg-msk-span">●●●●●●</span>
                            </div>
                        </div>

                        <div class="c-form-field device-chg-container _isDefault">
                            <div>
                                <div class="c-form__input">
                                    <input type="tel" class="c-input onlyNum mskDvcChg" id="cstmrNativeRrn01" maxlength="6" placeholder="생년월일(6자리)" aria-invalid="true" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                                    <label class="c-form__label" for="cstmrNativeRrn01">생년월일</label>
                                </div>
                            </div>
                            <span class="u-fs-22 u-fw--bold u-ta-center device-chg-num-span">-</span>
                            <div class="device-chg-back-num">
                                <div class="c-form__input">
                                    <input type="tel" class="c-input onlyNum u-width--50 mskDvcChg" id="cstmrNativeRrn02" maxlength="1" placeholder="" aria-invalid="true" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,6,7)}" onkeyup="nextFocus(this, 1, 'changeCstmrMobile');">
                                    <label class="c-form__label" for="cstmrNativeRrn02"></label>
                                </div>
                                <span class="u-fs-13 u-width--105 device-chg-msk-span">●●●●●●</span>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="c-form-field _isForeigner" style="display:none;">
                            <div class="c-form__input-group" >
                                <div class="c-form__input c-form__input-division">
                                    <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn01" name="cstmrForeignerRrn01" autocomplete="off" type="tel" maxlength="6" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,0,6)}" placeholder="외국인 등록번호 앞자리" title="외국인 등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrForeignerRrn02');" >
                                    <span>-</span>
                                    <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn02" name="cstmrForeignerRrn02" autocomplete="off" type="password" maxlength="7" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,6,13)}" placeholder="외국인 등록번호 뒷자리" title="외국인 등록번호 뒷자리">
                                    <label class="c-form__label" for="cstmrForeignerRrn01">외국인 등록번호</label>
                                </div>
                            </div>
                        </div>




                        <div class="c-form-field _isDefault" >
                            <div class="c-form__input-group" >
                                <div class="c-form__input c-form__input-division">
                                    <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,0,6)}" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');">
                                    <span>-</span>
                                    <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="off" type="password" maxlength="7" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,6,13)}" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                                    <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>


                <c:choose>
                    <c:when test="${AppformReq.operType eq Constants.OPER_TYPE_CHANGE ||  AppformReq.operType eq Constants.OPER_TYPE_EXCHANGE  }">
                        <div class="c-form u-mt--8">
                            <div class="c-form__input">
                                <input class="c-input onlyNum" id="changeCstmrMobile" type="tel" name="" maxlength="14" pattern="[0-9]*" placeholder="&quot;-&quot; 없이 입력">
                                <label class="c-form__label" for="changeCstmrMobile">기기변경 휴대폰 번호</label>
                            </div>
                        </div>
                    </c:when>
                </c:choose>

                <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                    <div class="c-form _isDefaultText " style="display:none;">
                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                            <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                        </ul>
                    </div>
                </c:if>

                <div class="c-form _isTeen" style="display:none;">
                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                        <li class="c-text-list__item">영·유아(만 0세~3세)는 가입이 제한되며, 만 4세부터 가입 신청이 가능합니다.</li>
                        <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                            <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                        </c:if>
                    </ul>
                </div>

                <div class="c-form _isForeigner" style="display:none;">
                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                        <li class="c-text-list__item">외국인 등록증과 동일하게 작성해주세요.</li>
                        <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                            <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                        </c:if>
                    </ul>
                </div>



                <c:if test="${AppformReq.onOffType eq '7' and AppformReq.operType eq Constants.OPER_TYPE_NEW }" >
                    <div class="c-form _self">
                        <span class="c-form__title--type2" id="spSmsAuth">휴대폰 인증</span>
                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2 u-mt--0">
                            <li class="c-text-list__item">셀프개통 신규 가입 서비스는 본인 명의의 휴대폰을 통한 SMS인증 후 이용이 가능합니다.</li>
                            <li class="c-text-list__item u-co-red">전기통신사업법 제 84조 2항/제 32조 3항에 의거하여 번호변작 또는 스팸발송 시 핸드폰 정지 또는 해지될 수 있습니다.</li>
                        </ul>
                        <div class="c-button-wrap">
                            <button id="btnSmsAuth" class="c-button c-button--full c-button--h48 c-button--mint">휴대폰 인증</button>
                        </div>
                    </div>
                </c:if>

                <div class="c-form _isTeen" style="display:none;">
                    <span class="c-form__title--type2" id="spMinor">법정대리인 정보</span>
                </div>
                <div class="c-form _isTeen" style="display:none;">
                    <div class="c-form__input">
                        <input class="c-input" id="minorAgentName" type="text" placeholder="법정대리인 성명" value="${AppformReq.minorAgentName}" maxlength=60 >
                        <label class="c-form__label" for="minorAgentName">법정대리인 성명</label>
                    </div>
                </div>
                <div class="c-form _isTeen" style="display:none;">
                    <div class="c-form__select">
                        <select id="minorAgentRelation" class="c-select">
                            <option value="">선택</option>
                            <option value="01" <c:if test="${AppformReq.minorAgentRelation eq '01' }" > selected </c:if> >부</option>
                            <option value="02" <c:if test="${AppformReq.minorAgentRelation eq '02' }" > selected </c:if> >모</option>
                            <option value="10" <c:if test="${AppformReq.minorAgentRelation eq '10' }" > selected </c:if> >그외</option>
                        </select>
                        <label class="c-form__label" for="minorAgentRelation" >신청인과의 관계</label>
                    </div>
                </div>


                <div class="c-form-field _isTeen" style="display:none;">
                    <div class="c-form__input-group">
                        <div class="c-form__input c-form__input-division">
                            <input class="c-input--div2 onlyNum" id="minorAgentRrn01" name="minorAgentRrn01" autocomplete="off" type="tel" maxlength="6" value="${fn:substring(AppformReq.minorAgentRrnDesc,0,6)}" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'minorAgentRrn02');">
                            <span>-</span>
                            <input class="c-input--div2 onlyNum" id="minorAgentRrn02" name="minorAgentRrn02" autocomplete="off" type="password" maxlength="7" value="${fn:substring(AppformReq.minorAgentRrnDesc,6,13)}" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                            <label class="c-form__label" for="minorAgentRrn01">법정대리인 주민등록번호</label>
                        </div>
                    </div>
                </div>


                <div class="c-form u-mt--8 _isTeen" style="display:none;">
                    <div class="c-form__input">
                        <input class="c-input onlyNum" id="minorAgentTel" type="tel" name="minorAgentTel" value="${AppformReq.minorAgentTelFn}${AppformReq.minorAgentTelMn}${AppformReq.minorAgentTelRn}" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력">
                        <label class="c-form__label" for="minorAgentTel">법정대리인 연락처 <em class="c-form__sublabel">(휴대폰)</em></label>
                    </div>
                </div>

                <div class="c-form _isTeen" style="display:none;">
                    <span class="c-form__title--type2">법정대리인 안내사항 확인 및 동의</span>
                    <div class="c-agree__item">
                        <input class="c-checkbox c-checkbox--type2" id="minorAgentAgrmYn" type="checkbox" name="minorAgentAgrmYn"  >
                        <label class="c-label" for="minorAgentAgrmYn">본인은 안내사항을 확인하였습니다.<span class="u-co-gray">(필수)</span>
                        </label>
                        <button id="minorAgentAgree" class="c-button c-button--xsm" onclick="fnSetEventId('minorAgentAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MINOR_AGENT}',0);"  >
                            <span class="c-hidden">상세보기</span>
                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                    </div>
                </div>

                <!--[$2021-11-29] 미성년자 선택 시 입력 항목 변경추가-->

                <!-- 본인인증 방법 선택 -->
                <c:choose>
                    <c:when test="${ AppformReq.operType ne Constants.OPER_TYPE_CHANGE && AppformReq.operType ne Constants.OPER_TYPE_EXCHANGE && 'Y' ne AppformReq.sesplsYn && '09' ne AppformReq.usimKindsCd}">
                        <c:if test="${AppformReq.onOffType eq '7'}">
                            <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                                <jsp:param name="controlYn" value="Y"/>
                                <jsp:param name="helpDesc" value="Y"/>
                            </jsp:include>
                        </c:if>
                        <c:if test="${AppformReq.onOffType ne '7'}">
                            <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                                <jsp:param name="controlYn" value="N"/>
                                <jsp:param name="reqAuth" value="CNKAT"/>
                                <jsp:param name="helpDesc" value="Y"/>
                            </jsp:include>
                        </c:if>
                    </c:when>

                    <c:otherwise>
                        <c:if test="${AppformReq.onOffType eq '7'}">
                            <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                                <jsp:param name="controlYn" value="Y"/>
                            </jsp:include>
                        </c:if>
                        <c:if test="${AppformReq.onOffType ne '7'}">
                            <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                                <jsp:param name="controlYn" value="N"/>
                                <jsp:param name="reqAuth" value="CNKAT"/>
                            </jsp:include>
                        </c:if>
                    </c:otherwise>
                </c:choose>


                <div class="c-accordion c-accordion--type5 acc-agree c-accordion--nested">
                    <span class="c-form__title--type2">약관동의</span>
                    <div class="c-accordion__box">
                        <div class="c-accordion__item">
                            <div class="c-accordion__head">
                                <div class="c-accordion__check">
                                    <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                                    <label class="c-label" for="btnStplAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span></label>
                                </div>
                                <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeA1">
                                    <div class="c-accordion__trigger"> </div>
                                </button>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_agreeA1">
                                <div class="c-accordion__inside">

                                    <c:if test="${ AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM }" >
                                        <div class="c-agree__item">
                                            <input  id="clauseMoveCode" code="${Constants.CLAUSE_MOVE_CODE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                            <label class="c-label" for="clauseMoveCode">번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법 동의<span class="u-co-gray">(필수)</span>
                                            </label>
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseMoveCode');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MOVE_CODE}');" >
                                                <span class="c-hidden">상세보기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                    </c:if>

                                    <div class="c-agree__item">
                                        <input id="clausePriOfferFlag" code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                        <label class="c-label" for="clausePriOfferFlag">고유식별정보 수집&middot;이용 동의<span class="u-co-gray">(필수)</span>
                                        </label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}');">
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>

                                    <div class="c-agree__item">
                                        <input id="clausePriCollectFlag" code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                                        <label class="c-label" for="clausePriCollectFlag">개인정보/신용정보 수집·이용 동의<span class="u-co-gray">(필수)</span>
                                        </label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}');" >
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>

                                    <div class="c-agree__item">
                                        <input id="clauseEssCollectFlag" code="${Constants.CLAUSE_ESS_COLLECT_CODE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                                        <label class="c-label" for="clauseEssCollectFlag">개인정보 제3자 제공 동의<span class="u-co-gray">(필수)</span>
                                        </label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseEssCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}');">
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>

                                    <div class="c-agree__item">
                                        <input id="clauseFathFlag01" code="clauseFathFlag01" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                                        <label class="c-label" for="clauseFathFlag01">민감정보(생체인식정보)수집 및 이용동의<span class="u-co-gray">(필수)</span></label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFathFlag01');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag01');">
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                    <div class="c-agree__item">
                                        <input id="clauseFathFlag02" code="clauseFathFlag02" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                                        <label class="c-label" for="clauseFathFlag02">민감정보(생체인식정보)조희 및 이용 / 3자 제공에 대한 동의<span class="u-co-gray">(필수)</span></label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFathFlag02');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag02');">
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>

                                    <div class="c-agree__item">
                                        <input id="notice" code="F${Constants.NOTICE_CODE_16}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 hidden _agree">
                                        <label class="c-label" for="notice">서비스 이용약관<span class="u-co-gray">(필수)</span>
                                        </label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('notice');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}');" >
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>

                                    <c:if test="${AppformReq.prdtSctnCd eq PhoneConstant.FIVE_G_FOR_MSP}">
                                        <div class="c-agree__item">
                                            <input id="clause5gCoverage" code="${Constants.CLAUSE_5G_COVERAGE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                            <label class="c-label" for="clause5gCoverage">5g 커버리지 확인 및 가입 동의<span class="u-co-gray">(필수)</span>
                                            </label>
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clause5gCoverage');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_5G_COVERAGE}');" >
                                                <span class="c-hidden">상세보기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                    </c:if>

                                    <div class="c-agree__item _isTeen" style="display:none;">
                                        <input id="nwBlckAgrmYn" code="${Constants.NOTICE_CODE_23}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                        <label class="c-label" for="nwBlckAgrmYn">청소년 유해정보 네트워크차단 동의<span class="u-co-gray">(필수)</span>
                                        </label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('nwBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_23}');"  >
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>

                                    <div class="c-agree__item _isTeen" style="display:none;">
                                        <input id="appBlckAgrmYn" code="${Constants.NOTICE_CODE_24}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                        <label class="c-label" for="appBlckAgrmYn">청소년 유해정보차단 APP 설치 동의<span class="u-co-gray">(필수)</span>
                                        </label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('appBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_24}');"  >
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>

                                    <div class="c-agree__item _clauseJehuFlag" style="display:none;">
                                        <button class="c-button c-button--xsm" id="clauseJehuButton" >
                                            <span class="c-hidden">제휴 서비스를 위한 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseJehuFlag" type="checkbox"  code="clauseJehuFlag" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="N" validityyn="Y" >
                                        <label class="c-label" for="clauseJehuFlag">개인정보 제3자 제공 동의[${jehuProdName} 가입고객 필수 동의]<span class="u-co-gray">(필수)</span>
                                        </label>
                                    </div>
                                    <div class="c-agree__item _clausePartnerOfferFlag" style="display:none;" >
                                        <button class="c-button c-button--xsm" id="clausePartnerOfferButton" >
                                            <span class="c-hidden">제휴사 정보 제공 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clausePartnerOfferFlag" type="checkbox"  code="clausePartnerOfferFlag" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="N" validityyn="Y" >
                                        <label class="c-label" for="clausePartnerOfferFlag">개인정보 제3자 제공 동의[${jehuPartnerName} 가입고객 필수 동의]<span class="u-co-gray">(필수)</span>
                                        </label>
                                    </div>

                                    <div class="c-accordion c-accordion--nested__sub c-agree__item">
                                        <div class="c-accordion__box">
                                            <div class="c-accordion__item">
                                                <div class="c-accordion__head">
                                                    <div class="c-accordion__check">
                                                        <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="agreeWrap5" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
                                                        <label class="c-label" for="agreeWrap5">고객 혜택 제공을 위한 정보수집 이용 동의 및 혜택 광고의 수신 위탁 동의<span class="u-co-gray">(선택)</span>
                                                        </label>
                                                    </div>
                                                    <button class="c-accordion__button" type="button" aria-expanded="false">
                                                        <div class="c-accordion__trigger"></div>
                                                    </button>
                                                </div>
                                                <div class="c-accordion__panel expand" id="agree5">
                                                    <div class="c-accordion__inside">
                                                        <div class="c-agree__item">
                                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('personalInfoCollectAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.PERSONAL_INFO_COLLECT_AGREE}',0);">
                                                                <span class="c-hidden">고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의(선택) 상세 팝업 열기</span>
                                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                            </button>
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="personalInfoCollectAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.PERSONAL_INFO_COLLECT_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N">
                                                            <label class="c-label" for="personalInfoCollectAgree">고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의<span class="u-co-gray">(선택)</span>
                                                            </label>
                                                        </div>
                                                        <div class="c-agree__item">
                                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriAdFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_PRI_AD_CODE}');" >
                                                                <span class="c-hidden">개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의(선택) 상세 팝업 열기</span>
                                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                            </button>
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="clausePriAdFlag" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.CLAUSE_PRI_AD_CODE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="personalInfoCollectAgree">
                                                            <label class="c-label" for="clausePriAdFlag">개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="c-accordion c-accordion--nested__sub c-agree__item">
                                        <div class="c-accordion__box">
                                            <div class="c-accordion__item">
                                                <div class="c-accordion__head">
                                                    <div class="c-accordion__check">
                                                        <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="agreeWrap6" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
                                                        <label class="c-label" for="agreeWrap6">혜택 제공을 위한 제3자 제공 및 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                                        </label>
                                                    </div>
                                                    <button class="c-accordion__button" type="button" aria-expanded="false">
                                                        <div class="c-accordion__trigger"></div>
                                                    </button>
                                                </div>
                                                <div class="c-accordion__panel expand" id="agree6">
                                                    <div class="c-accordion__inside">
                                                        <div class="c-agree__item">
                                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('othersTrnsAllAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.OTHERS_TRNS_ALL_AGREE}',0);initOthersTrnsAgrees()">
                                                                <span class="c-hidden">혜택 제공을 위한 제3자 제공 동의 상세 팝업 열기</span>
                                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                            </button>
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="othersTrnsAllAgree" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)" code="S${Constants.OTHERS_TRNS_ALL_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N">
                                                            <label class="c-label" for="othersTrnsAllAgree">혜택 제공을 위한 제3자 제공 동의<span class="u-co-gray">(선택)</span>
                                                            </label>
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="formOthersTrnsAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_TRNS_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5" style="display: none;">
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="formOthersTrnsKtAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_TRNS_KT_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5" style="display: none;">
                                                        </div>
                                                        <div class="c-agree__item">
                                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('othersAdReceiveAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.OTHERS_AD_RECEIVE_AGREE}');" >
                                                                <span class="c-hidden">개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의(선택) 상세 팝업 열기</span>
                                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                            </button>
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="othersAdReceiveAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_AD_RECEIVE_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5">
                                                            <label class="c-label" for="othersAdReceiveAgree">개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <c:if test="${AppformReq.onOffType eq '7'}" >
                    <div class="c-form _self">
                        <span class="c-form__title--type2" >셀프개통 안내사항</span>
                        <div class="c-agree__item">
                            <input class="c-checkbox" id="clauseSimpleOpen" type="checkbox" name="clauseSimpleOpen" validityyn="Y" >
                            <label class="c-label" for="clauseSimpleOpen">본인은 안내사항을 확인하였습니다. <span class="u-co-gray">(필수)</span></label>
                        </div>
                        <div class="c-agree__inside">
                            <div class="self-agree__inside">
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray">
                                        최근 대출업자, SNS 등을 통해 이동전화 개통 시 자금을 제공해 주겠다고 권유한 후, 개통된 휴대폰/유심을 대출 사기, 보이스 피싱 조직에 유통하는 등의 악용하는 사례가 다수 적발되고 있습니다.<br />이러한 제 3자에게 본인명의의 휴대폰/유심을 개통해주거나, 개통에 필요한 신청서류를 제공하는 행위는 전기통신사업법 제30조(타인사용의 제한) 및 97조(벌칙)의 규정에 따라 1년 이하의 징역 및 5천만원 이하의 형사처벌을 받을 수 있으니 각별히 주의하시기 바랍니다.
                                    </li>
                                    <li class="c-text-list__item u-co-gray">
                                        부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.<br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${ '01' eq AppformReq.settlWayCd}">
                    <div class="c-form ">
                        <span class="c-form__title--type2" >이동전화 사기판매 주의 및 주문 시 동의사항</span>
                        <div class="c-agree__item">
                            <input class="c-checkbox" id="clauseSettlWayCd" type="checkbox" name="clauseSettlWayCd" validityyn="Y" >
                            <label class="c-label" for="clauseSettlWayCd">본인은 안내사항을 확인하였습니다. <span class="u-co-gray">(필수)</span></label>
                        </div>
                        <div class="c-agree__inside">
                            <div class="inspay-agree__inside">
                                <div class="one-source">
                                    <h3 class="c-heading c-title--type2 u-fw--bold u-co-mint u-mt--16">이동전화 사기판매 주의 안내</h3>
                                    <p class="c-heading c-heading--fs16 u-mt--20">
                                        이동전화 가입 시 일부 불편법영업 행위에 의한 사기판매 피해가 우려되어, 이용자 여러분의 각별한 주의가 요구됩니다.
                                    </p>
                                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--20">
                                        <li class="c-text-list__item">
                                            소액대출(내구제 대출 등) 용도로 개통
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">현금성 대출을 받기 위해 카드/인증서/계좌번호 등의 개인정보를 요구하는 경우</li>
                                            </ul>
                                        </li>
                                        <li class="c-text-list__item">
                                            선입금 요구, 잔여할부금 면제
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">추후 잔여할부금을 면제하는 조건으로 선입금을 요구하는 경우</li>
                                            </ul>
                                        </li>
                                        <li class="c-text-list__item">
                                            신분증 전달 요구
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">온라인가입 시 가입자의 신분증을 택배 등으로 전달할 것을 요구하는 경우</li>
                                            </ul>
                                        </li>
                                        <li class="c-text-list__item">
                                            비공식 계약서 작성
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">불법지원금 제공(현금지급, 할부금 면제 등) 조건을 별도의 계약서로 작성하는 경우</li>
                                            </ul>
                                        </li>
                                    </ul>
                                    <h3 class="c-heading c-heading--fs16 u-mt--24 u-co-red">※ 불법지원금 제공과 불법 대출인 것을 알고도 이에 동조하는 경우 발생하는 피해에 대해서는 법적으로 보호받을 수 없습니다.</h3>
                                    <h3 class="c-heading c-title--type2 u-fw--bold u-co-mint u-mt--24">주문 시 필수 안내</h3>
                                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--20">
                                        <li class="c-text-list__item">
                                            개통안내 공통사항
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">kt M모바일 다이렉트몰 휴대폰 가입 신청 시(신규가입, 번호이동, 기기변경 등) 상담원 해피콜 후 선개통 되어 배송이 진행 됩니다.</li>
                                                <li class="c-text-list__item">선개통이란? 배송 전 개통되어 단말이 발송되며, 수령 후 즉시 사용이 가능하도록 미리 개통하는 방식</li>
                                            </ul>
                                        </li>
                                        <li class="c-text-list__item">
                                            주문취소/교환/반품안내
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">주문취소는 배송 준비 전 까지만 가능합니다.</li>
                                                <li class="c-text-list__item">개통 취소 시 재 가입이 불가한 요금제로 복구처리가 불가능합니다.</li>
                                                <li class="c-text-list__item">개통 취소는 기기불량, 통화품질 불량 시 개통일로부터 14일 이내(배송기간 포함) 가능합니다.<br>(단, 개통 후 취소시까지 발생된 요금은 일할계산 처리되어 청구됩니다.)</li>
                                                <li class="c-text-list__item">교환/반품은 구매하신 대리점에 연락하여 사전 확인 후 발송해주시기 바랍니다.</li>
                                                <li class="c-text-list__item">교환/반품 시 단말기 본체(박스포함), 구성품이 누락되지 않아야 합니다.</li>
                                                <li class="c-text-list__item">단말기 교환 시 개인정보보호를 위하여 단말기 비밀번호, 패턴, 지문락 등을 설정 초기화하여 반송해주셔야 합니다.</li>
                                                <li class="c-text-list__item">개인구매 후 부착하신 액세서리(보호필름, 튜닝제품 등)은 보상처리되지 않는 점 양해 부탁 드립니다.</li>
                                                <li class="c-text-list__item">제조사 정책에 의거하여, 단말기 불량으로 인한 교환/취소를 원하실 경우, 제조사 A/S센터 내방하여 14일 이내(애플은 애플 A/S확인) 불량 확인서를 받으시고 개통대리점의 안내에 따라 교환/취소가 가능함을 양지하여 주시기 바랍니다.</li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>






            </div>
            <!-- STEP1 END -->

            <!-- STEP2 START -->
            <div id="step2" style="display:none;" class="_divStep" >
                <div class="c-form-step">
                    <span class="c-form-step__number">2</span>
                    <h3 class="c-form-step__title">유심정보·신분증 확인</h3>
                </div>

                <c:choose>
                    <c:when test="${AppformReq.usimKindsCd eq '09'}">
                        <!-- eSIM -->

                        <h4 class="c-heading c-heading--type5 u-fw--medium">휴대폰정보</h4>
                        <hr class="c-hr c-hr--type2">
                        <div class="c-form">
                            <ul class="c-text-list c-bullet c-bullet--dot" >
                                <li class="c-text-list__item u-co-gray">휴대폰의 IMEI2로 개통이 진행됩니다.</li>
                                <li class="c-text-list__item u-co-gray">개통 진행 시 변경이 불가하므로 다시 한번 확인 후 진행하시기 바랍니다.</li>
                            </ul>

                        </div>


                        <div class="c-form">

                            <div class="c-form__input">
                                <input class="c-input" id="imei2"  name="imei2" value="${AppformReq.imei2}"  readonly>
                                <label class="c-form__label" for="imei2">IMEI2</label>
                            </div>

                            <div class="c-form__input">
                                <input class="c-input" id="eid"  name="eid"  value="${AppformReq.eid}" readonly >
                                <label class="c-form__label" for="eid">EID</label>
                            </div>

                        </div>


                        <!-- eSIM END -->
                    </c:when>
                    <c:otherwise>


                        <!-- eSIM other -->
                        <h4 class="c-form__title--type2 u-mt--50">유심정보</h4>

                        <div class="c-check-wrap _noSelf">

                            <c:choose>
                                <c:when test="${AppformReq.operType eq Constants.OPER_TYPE_CHANGE ||  AppformReq.operType eq Constants.OPER_TYPE_EXCHANGE  }">
                                    <div class="c-chk-wrap">
                                        <input class="c-radio c-radio--button" id="onOffLineUsim01" type="radio" name="onOffLineUsim" value="offLine" <c:if test="${AppformReq.clauseMpps35Flag eq 'N' }" > checked </c:if> >
                                        <label class="c-label" for="onOffLineUsim01">기존 유심 사용</label>
                                    </div>
                                    <div class="c-chk-wrap">
                                        <input class="c-radio c-radio--button" id="onOffLineUsim02" type="radio" name="onOffLineUsim" value="onLine" <c:if test="${AppformReq.clauseMpps35Flag eq 'Y' }" > checked </c:if> >
                                        <label class="c-label" for="onOffLineUsim02">유심 미보유</label>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="c-chk-wrap">
                                        <input class="c-radio c-radio--button" id="onOffLineUsim01" type="radio" name="onOffLineUsim" value="offLine" <c:if test="${AppformReq.clauseMpps35Flag eq 'N' }" > checked </c:if> >
                                        <label class="c-label" for="onOffLineUsim01">유심 보유</label>
                                    </div>
                                    <div class="c-chk-wrap">
                                        <input class="c-radio c-radio--button" id="onOffLineUsim02" type="radio" name="onOffLineUsim" value="onLine" <c:if test="${AppformReq.clauseMpps35Flag eq 'Y' }" > checked </c:if> >
                                        <label class="c-label" for="onOffLineUsim02">유심 미보유</label>
                                    </div>
                                </c:otherwise>
                            </c:choose>


                        </div>

                        <c:if test="${ AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM  }">
                            <div class="c-check-wrap _onLineUsim _noSelf u-mt--8" style="display:none;" >
                                <div class="c-chk-wrap">
                                    <input class="c-radio c-radio--button" id="radUsimComm" type="radio" name="radUsimProd" value="05" data-usim-prod-code="01" usimNm="일반 USIM" checked  usimPrice="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_OBJ_BASE)}"   >
                                    <label class="c-label" for="radUsimComm">일반 <span id="spCommUsimPrice">-</span>원</label>
                                </div>
                               <div class="c-chk-wrap">
                                    <input class="c-radio c-radio--button" id="radUsimNfc" type="radio" name="radUsimProd" value="06" data-usim-prod-code="02" usimNm="NFC USIM" usimPrice="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_USIM_NFC)}"  >
                                    <label class="c-label" for="radUsimNfc">NFC <span id="spCommUsimNfcPrice">-</span>원</label>
                                </div>
                            </div>
                        </c:if>



                        <c:choose>
                            <c:when test="${AppformReq.operType eq Constants.OPER_TYPE_CHANGE ||  AppformReq.operType eq Constants.OPER_TYPE_EXCHANGE  }">
                                <div id="divUsim" class="c-form ">
                                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2 _offLineUsim" >
                                        <li class="c-text-list__item">현재 사용하시고 계신 유심을 사용할 수 있습니다.</li>
                                        <li class="c-text-list__item">기존 유심 사용을 선택하실 경우 유심비용이 미청구 됩니다.</li>
                                        <li class="c-text-list__item">보유하신 신규 유심으로 개통을 원하시는 고객님은 상담 시 말씀부탁드립니다.</li>
                                    </ul>

                                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2 _onLineUsim" id="ulOnOffLineUsimDesc">
                                        <li class="c-text-list__item">유심 카드를 보유한 고객님께서는 유심번호 19자리를 입력해 주세요.</li>
                                        <li class="c-text-list__item">유심 보유를 선택하실 경우 유심비용이 미청구 됩니다.</li>
                                    </ul>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="c-form c-scan-wrap _offLineUsim">
                                    <div class="c-box">
                                        <img class="center-img" src="/resources/images/mobile/content/img_usim_card.png" alt="유심 카드 샘플 이미지">
                                    </div>
                                    <div class="c-button-wrap c-button-wrap--right" id="divUsimScan">
                                        <button class="c-button _ocrRecord" data-type="usim" >
                                            <span class="c-text c-text--type3 u-co-sub-4">스캔하기</span>
                                            <i class="c-icon c-icon--scan" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                </div>


                                <div id="divUsim" class="c-form">
                                    <div class="c-form__input _offLineUsim">
                                        <input class="c-input onlyNum" id="reqUsimSn" type="tel" maxlength="19" pattern="[0-9]*" placeholder="직접입력('-'없이 입력)" title="유심번호 입력" value="${AppformReq.reqUsimSn}">
                                        <label class="c-form__label" for="reqUsimSn">유심번호 19자리</label>
                                    </div>

                                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2" id="ulOnOffLineUsimDesc">
                                        <li class="c-text-list__item">유심 카드를 보유한 고객님께서는 유심번호 19자리를 입력해 주세요.</li>
                                        <li class="c-text-list__item">유심 보유를 선택하실 경우 유심비용이 미청구 됩니다.</li>
                                    </ul>
                                    <div class="c-button-wrap u-mt--40 _offLineUsim">
                                        <button id="btnUsimNumberCheck" class="c-button c-button--full c-button--h48 c-button--mint">유심번호 유효성 체크</button>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>


                        <!-- eSIM other END -->

                    </c:otherwise>
                </c:choose>




                <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_NEW  or AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM }" >

                    <h4 class="c-form__title--type2">연락처</h4>
                    <div class="c-form">
                        <div class="c-form__input">
                            <input class="c-input onlyNum" id="cstmrMobile" type="tel" name="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="${AppformReq.cstmrMobileFn}${AppformReq.cstmrMobileMn}${AppformReq.cstmrMobileRn}"  >
                            <label class="c-form__label" for="cstmrMobile">휴대폰</label>
                        </div>
                        <div class="c-form__input">
                            <input class="c-input onlyNum" id="cstmrTel" type="tel" name="cstmrTel" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="${AppformReq.cstmrTelFn}${AppformReq.cstmrTelMn}${AppformReq.cstmrTelRn}" >
                            <label class="c-form__label" for="cstmrTel">전화번호(선택)</label>
                        </div>
                        <div class="c-form__input ">
                            <input class="c-input" id="cstmrMail" type="text" placeholder="예: ktm@ktm.com" title="이메일주소 입력" value="${AppformReq.cstmrMail}" maxlength="50" >
                            <label class="c-form__label" for="cstmrMail">이메일</label>
                        </div>
                    </div>

                    <div class="c-form">
                        <span class="c-form__title--type2">주소</span>
                        <div class="c-form__group" role="group" aria-labeledby="inpG">
                            <div class="c-input-wrap">
                                <input id="cstmrPost"class="c-input" type="text" placeholder="우편번호" title="우편번호 입력"  value="${AppformReq.cstmrPost}" readonly >
                                <button class="c-button c-button--sm c-button--underline _btnAddr" addrFlag="1" >우편번호 찾기</button>
                            </div>
                            <input id="cstmrAddr" class="c-input" type="text" placeholder="주소 입력" title="주소 입력" value="${AppformReq.cstmrAddr}" readonly>
                            <input id="cstmrAddrDtl" class="c-input" type="text" placeholder="상세 주소 입력" title="상세 주소 입력"  value="${AppformReq.cstmrAddrDtl}" readonly>
                        </div>
                    </div>

                    <div class="c-form">
                        <span id="radIdCardHead" class="c-form__title--type2">신분증 확인</span>
                        <div class="c-check-wrap c-check-wrap--type2" >
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button" id="selfCertType1" value="01" type="radio" name="selfCertType" >
                                <label class="c-label" for="selfCertType1">주민등록증</label>
                            </div>
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button" id="selfCertType2" value="02" type="radio" name="selfCertType" >
                                <label class="c-label" for="selfCertType2">운전면허증</label>
                            </div>
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button" id="selfCertType3" value="03" type="radio" name="selfCertType" >
                                <label class="c-label" for="selfCertType3">장애인 등록증</label>
                            </div>
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button" id="selfCertType4" value="04" type="radio" name="selfCertType" >
                                <label class="c-label" for="selfCertType4">국가 유공자증</label>
                            </div>
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button" id="selfCertType6" value="06" type="radio" name="selfCertType" >
                                <label class="c-label" for="selfCertType6">외국인 등록증</label>
                            </div>
                            <c:choose>
                                <c:when test="${AppformReq.onOffType eq '7'}">
                                    <div class="c-chk-wrap">
                                        <input class="c-radio c-radio--button c-hidden" id="selfCertType5" value="05" type="radio" name="selfCertType" disabled >
                                        <label class="c-label c-hidden" for="selfCertType5">여권</label>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="c-chk-wrap">
                                        <input class="c-radio c-radio--button c-hidden" id="selfCertType5" value="05" type="radio" name="selfCertType"  >
                                        <label class="c-label c-hidden" for="selfCertType5">여권</label>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="c-form _isFathCert" style="display:none;">
                        <button id="btnFathUrlRqt" class="c-button c-button--h48 c-button--mint c-button--full u-mt--32">안면인증 URL 받기</button>
                        <div class="c-button-wrap u-mt--32">
                            <button id="btnFathTxnRetv" class="c-button c-button--h48 c-button--mint c-button--full" disabled>안면인증 결과 확인</button>
                            <button id="btnFathSkip" class="c-button c-button--h48 c-button--gray c-button--full u-mt--8" onclick="requestFathSkip();" disabled>안면인증 SKIP</button>
                        </div>
                    </div>

                    <div class="_chkIdCardTitle" style="display:none;">
                        <div class="c-form c-scan-wrap">
                            <div class="c-box u-mt--32">
                                <img id="imgRadIdCard" class="center-img" src="/resources/images/mobile/content/img_jumin_card.png" alt="">
                            </div>
                            <div class="c-button-wrap c-button-wrap--right _divScan" style="display:none;">
                                <button class="c-button _ocrRecord" data-type="social" >
                                    <span class="c-text c-text--type3 u-co-sub-4">스캔하기</span>
                                    <i class="c-icon c-icon--scan" aria-hidden="true"></i>
                                </button>
                            </div>
                        </div>

                        <div class="c-form__select _cstmrForeignerNation" style="display:none;">
                            <select class="c-select" id="cstmrForeignerNation" name="cstmrForeignerNation">

                            </select>
                            <label class="c-form__label" for="cstmrForeignerNation">국가</label>
                            <input type="hidden" id="cstmrForeignerNationTmp" name="cstmrForeignerNationTmp" value="${AppformReq.cstmrForeignerNation}">
                        </div>

                        <!-- 운전면허증 선택 시 노출-->
                        <div class="c-form _driverSelfIssuNumF" style="display:none;">
                            <div class="c-form__input">
                                     <input id="driverSelfIssuNum" class="c-input" type="tel" name="driverSelfIssuNum" maxlength="15" placeholder="면허번호 입력"  value="${AppformReq.selfIssuNumDesc}">
                                     <label class="c-form__label" for="selfIssuNum">면허번호</label>
                            </div>
                             <input  type="hidden" id="driverSelfIssuNum1">
                             <input type="hidden"  id="driverSelfIssuNum2">
                        </div>


                        <div class="c-form _selfIssuNumF"  style="display:none;">
                            <div class="c-form__input">
                                <input class="c-input" id="selfIssuNum" type="tel"  pattern="[0-9]*" placeholder="보훈번호 입력" title="보훈번호"  value="${AppformReq.selfIssuNumDesc}" >
                                <!--[2021-11-22] 인풋타입 변경-->
                                <label class="c-form__label" for="selfIssuNum">보훈번호</label>
                            </div>
                        </div>


                        <div class="c-form c-form--datepicker">
                            <div class="c-form__input " >
                                <input class="c-input date-input" id="selfIssuExprDt" type="text" placeholder="YYYY-MM-DD" title="발급일자"  maxlength="10"   value="<fmt:formatDate value="${AppformReq.selfIssuExprDate}" pattern="yyyy-MM-dd"   />" >
                                <label class="c-form__label" for="selfIssuExprDt">발급일자</label>
                                <button class="c-button">
                                    <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                                </button>
                            </div>
                        </div>

                        <input class="c-checkbox" id="selfInqryAgrmYnFlag" type="checkbox" >
                        <label class="c-label u-mt--20" for="selfInqryAgrmYnFlag">본인 조회에 동의합니다</label>
                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                            <li class="c-text-list__item">입력하신 정보는 한국정보통신진흥협회(KAIT)에서 제공하는 부정가입방지 시스템을 통해 신분증 진위여부 판단에 이용됩니다.</li>
                        </ul>
                    </div>


                </c:if>




                <div id="divDlvry" style="display:none" class="_noSelf">

                    <div class="c-form__title flex-type u-mt--40 u-mb--24">
                        <h4 class="c-form__title--type2 u-mt--0 u-mb--0">배송정보</h4>
                        <div class="c-form u-ml--auto">
                            <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_NEW  or AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM }" >
                                <input class="c-checkbox c-checkbox--type3" id="inEqualCheck" type="checkbox" name="inEqualCheck">
                                <label class="c-label" for="inEqualCheck">가입고객 정보와 동일</label>
                            </c:if>
                        </div>
                    </div>

                    <div class="c-form__group" role="group" aria-labelledby="inpDelivInfo">
                        <div class="c-form__input">
                            <input class="c-input" id="dlvryName" maxlength='60'  type="text" placeholder="이름 입력" value="${AppformReq.dlvryName}" >
                            <label class="c-form__label" for="dlvryName">받으시는 분</label>
                        </div>
                        <div class="c-form__input">
                            <input class="c-input onlyNum" id="dlvryTel" type="tel" name="dlvryTel" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="${AppformReq.dlvryTelFn}${AppformReq.dlvryTelMn}${AppformReq.dlvryTelRn}" >
                            <label class="c-form__label" for="dlvryTel">전화번호(선택)</label>
                        </div>
                        <div class="c-form__input">
                            <input class="c-input onlyNum" id="dlvryMobile" type="tel" name="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="${AppformReq.dlvryMobileFn}${AppformReq.dlvryMobileMn}${AppformReq.dlvryMobileRn}" >
                            <label class="c-form__label" for="dlvryMobile">휴대폰</label>
                        </div>
                    </div>


                    <c:if test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM  }">
                        <div class="c-form " id="divDlvryTop">
                <span class="c-form__title--type2">
                  <a data-tpbox="#tip2">배송유형 선택
                    <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                  </a>
                  <div class="c-tooltip-box" id="tip2">
                    <div class="c-tooltip-box__title">택배</div>
                    <div class="c-tooltip-box__content">택배로 신청하실 경우
                      <br>
                      <span class="u-co-red">평일 13시까지 주문시 당일 출고</span>됩니다. (주말/공휴일 제외)
                    </div>







                    <div class="c-tooltip-box__title _isNowDlvryTip" >바로배송</div>
                    <div class="c-tooltip-box__content _isNowDlvryTip">일부 지역은 바로 배송 서비스가 제외되오니 하단에 바로 배송 가능 지역 조회가 되지 않을 경우 택배를 이용해주세요.
                      <br>
                        <!-- <a class="c-button c-button--underline c-button--sm u-mt--12 u-co-mint" href="#">바로배송이란</a> -->
                    </div>

                      <!-- <div class="c-tooltip-box__title _isQuickDlvryTip" style="display:none;">
                          <a class="c-button c-button--underline c-button--sm u-mt--12 u-co-mint" href="javascript:openPage('outLink','/m/event/eventDetail.do?ntcartSeq=640');">당일배송 자세히보기</a>
                      </div>
                      <div class="c-tooltip-box__content _isQuickDlvryTip" style="display:none;">
                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                          <li class="c-text-list__item">당일배송은 서울 전지역에 한해 서비스 가능합니다.</li>
                          <li class="c-text-list__item">당일배송은 당일 12시(정오)까지 가입신청 완료 시에만 당일 수령이 가능합니다.</li>
                          <li class="c-text-list__item">당일배송 신청 이후 유심 종류 및 요금제, 배송지 변경은 불가합니다. </li>
                        </ul>
                      </div> -->

                    <button class="c-tooltip-box__close" data-tpbox-close>
                      <span class="c-hidden">툴팁닫기</span>
                    </button>
                  </div>
                </span>
                            <div class="c-check-wrap" id="divDlvryType">
                                <!-- <input class="c-radio" id="dlvryType02" type="radio" name="dlvryType" value="02" checked>
                                <label class="c-label" for="dlvryType02">바로배송</label>
                                <input class="c-radio" id="dlvryType01" type="radio" name="dlvryType" >
                                <label class="c-label" for="dlvryType01">택배</label>
                                <input class="c-radio" id="dlvryType03" type="radio" name="dlvryType">
                                <label class="c-label" for="dlvryType03">당일배송</label> -->
                            </div>
                        </div>
                    </c:if>

                    <div class="c-form">
                        <span class="c-form__title--type2" id="inpAddress">주소</span>
                        <div class="c-form__group" role="group" aria-labelledby="inpAddress">
                            <div class="c-input-wrap">
                                <input id="dlvryPost" class="c-input" type="text" placeholder="우편번호" title="우편번호 입력" value="${AppformReq.dlvryPost}" readonly>
                                <button class="c-button c-button--sm c-button--underline _btnAddr" addrFlag="2">우편번호 찾기</button>
                            </div>
                            <input id="dlvryAddr"  class="c-input" type="text" placeholder="주소" title="주소 입력" value="${AppformReq.dlvryAddr}" readonly  >
                            <input id="dlvryAddrDtl" class="c-input" type="text" placeholder="상세 주소" title="상세 주소 입력" value="${AppformReq.dlvryAddrDtl}" readonly  ><!-- case02 - 당일배송 지역 조회-->

                        </div>
                    </div>
                    <div class="c-form">
                        <div class="c-form__group" role="group" aria-labelledby="inpDelivInfo">

                            <div class="c-textarea-wrap _isNotNowDlvry">
                                <textarea id="dlvryMemo" class="c-textarea" placeholder="배송 시 요청사항 입력">${AppformReq.dlvryMemo}</textarea>
                                <div class="c-textarea-wrap__sub">
                                    <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                                    <span>0/50 </span>
                                </div>
                            </div>

                            <div class="c-form__select _isNowDlvry" style="display:none;">
                                <select class="c-select" id="orderReqMsg" >
                                    <option value="0" selected="selected">배송메시지를 선택해 주세요.</option>
                                    <option value="1">문 앞에 부착 해주세요.</option>
                                    <option value="2">우편함에 넣어주세요.</option>
                                    <option value="3">직접 받을께요(부재 시 문앞 부착)</option>
                                    <option value="4">직접입력</option>
                                </select>
                                <label class="c-form__label">배송 시 요구사항(선택)</label>
                            </div>

                            <div class="c-textarea-wrap _isNowDlvry2" style="display:none;">
                                <textarea id="dlvryMemoSub" class="c-textarea" placeholder="배송 시 요청사항 입력"></textarea>
                                <div class="c-textarea-wrap__sub">
                                    <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                                    <span>0/50 </span>
                                </div>
                            </div>


                            <div class="c-form__select _isNowDlvry" style="display:none;">
                                <select id="exitPw" class="c-select">
                                    <option value="0">선택 안함</option>
                                    <option value="1">비밀번호</option>
                                    <option value="2">경비실 호출</option>
                                    <option value="3">자유출입가능</option>
                                </select>
                                <label class="c-form__label">공동현관 출입방법</label>
                            </div>
                            <input class="c-input " style="display:none;" id="homePw" name="homePw" type="text"  placeholder="공동현관 비밀번호(선택)" title="공동현관 비밀번호(선택)" maxlength=10  >
                        </div>
                    </div>
                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2" id="ulOnOffLineUsimDesc">
                        <li class="c-text-list__item u-co-gray">단말 및 유심 신규신청 건은 선 개통 되어 발송되며, 기기변경/번호이동 건은 본인 수령 확인 및 개통 안내문자 발송 후 개통이 진행됩니다.</li>
                        <li class="c-text-list__item u-co-gray">단말 및 유심 번호이동 건은 선개통 진행 시 기존에 사용하시던 휴대폰을 일시적으로 사용못하실 수 있습니다.<br/>단말 개통 건은 선개통만 가능하며, 유심 번호이동 건은 선개통을 원치 않으실 경우에 해피콜 상담 시 말씀해 주시기 바랍니다.</li>
                    </ul>

                </div>
            </div>
            <!-- STEP2 END -->

            <!-- STEP3 START -->
            <div id="step3" style="display:none" class="_divStep" >
                <div class="c-form-step">
                    <span class="c-form-step__number">3</span>
                    <h3 class="c-form-step__title">가입신청 정보</h3>
                </div>

                <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM}">
                    <c:set var="codeListMoveC" value="${nmcp:getCodeList(Constants.WIRE_SERVICE_CODE)}" />

                    <div class="c-form">
                        <span class="c-form__title--type2 u-mt--50">사용중인 통신사</span>
                        <div class="c-check-wrap c-check-wrap--type2">
                            <c:forEach items="${codeListMoveC}" var="moveObj"  varStatus="status" >
                                <c:if test="${moveObj.dtlCdDesc eq '01'  }" >
                                    <div class="c-chk-wrap">
                                        <input type="radio" class="c-radio c-radio--button" id="moveCompanyGroup1_${status.index}" name="moveCompanyGroup1" value="${moveObj.dtlCd}" mpCode="${moveObj.expnsnStrVal1}" companyNm="${moveObj.dtlCdNm}" companyTel="${moveObj.expnsnStrVal2}" >
                                        <label class="c-label" for="moveCompanyGroup1_${status.index}">${moveObj.dtlCdNm}</label>
                                    </div>
                                </c:if>
                            </c:forEach>
                            <div class="c-chk-wrap">
                                <input type="radio" class="c-radio c-radio--button" id="moveCompanyGroup1_99" name="moveCompanyGroup1" value="-1">
                                <label class="c-label" for="moveCompanyGroup1_99">알뜰폰</label>
                            </div>
                        </div>
                        <!-- 알뜰폰 선택 시 나오는 항목 -->
                        <div id="divMoveCompanyGroup2" style="display:none">
                            <select class="c-select u-mt--8"" id="moveCompanyGroup2">
                            <option value="">알뜰폰 통신사</option>
                            <option value="02">SKT알뜰폰</option>
                            <option value="03">LGU+알뜰폰</option>
                            <option value="04">KT알뜰폰</option>
                            </select>

                            <select class="c-select" id="moveCompany">
                                <option value="">알뜰폰 사업자</option>
                            </select>
                        </div>
                        <!-- 알뜰폰 선택 시 나오는 항목 끝 -->
                        <div class="c-box c-box--type6">
                            <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                                <c:choose>
                                    <c:when test="${AppformReq.onOffType eq '7' }">
                                        <li class="c-text-list__item u-co-red">통신사를 모르실 경우 현재 사용중인 휴대폰에서 114로 확인 바랍니다.</li>
                                        <li class="c-text-list__item u-co-red">현재 사용중인 통신사가 목록에 없을 경우 챗봇에서 '통신사명 확인'으로 검색하시면 대체 선택 통신사 확인이 가능합니다.</li>
                                        <li class="c-text-list__item">타사 신규개통/명의변경/번호이동 후 3개월 이내 고객은 셀프개통으로 번호이동이 불가하므로 온라인 가입신청을 부탁드립니다.</li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="c-text-list__item u-co-red">통신사를 모르실 경우 현재 사용중인 휴대폰에서 114로 확인 바랍니다.</li>
                                        <li class="c-text-list__item u-co-red">현재 사용중인 통신사가 목록에 없을 경우 챗봇에서 '통신사명 확인'으로 검색하시면 대체 선택 통신사 확인이 가능합니다.</li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${AppformReq.onOffType eq '7' }">
                            <div class="c-form">
                                <span class="c-form__title--type2">번호이동할 전화번호</span>
                                <input id="moveMobile" class="c-input onlyNum" type="tel"  maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" title="전화번호 입력" value="${AppformReq.moveMobileFn}${AppformReq.moveMobileMn}${AppformReq.moveMobileRn}">
                                <div class="c-box c-box--type6" id="npDesc">
                                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                                        <li class="c-text-list__item">번호이동을 위해서는 현재 사용중인 통신사에서의 인증 절차가 필요합니다.<button class="c-text-link--bluegreen u-fs-14" data-dialog-trigger="#move_number_pop">사전동의 방법</button></li>
                                        <li class="c-text-list__item">번호이동 사전동의는 일 7회로 제한됩니다.</li>
                                    </ul>
                                </div>
                            </div>

                            <div id="divArs" style="display:none">
                                <hr class="c-hr c-hr--type1 c-expand">
                                <div class="ars-company">
                                    <p class="ars-company__text">문자 내 URL로 또는 전화를 걸어<br/>ARS 안내에 따라 동의 <span>(1~2분 소요)</span></p>
                                    <div class="ars-company__wrap">
                                        <p class="ars-company__name">-</p>
                                        <p class="ars-company__number">-</p>
                                        <a id="btnArsConn" class="c-button c-button--full c-button--h48 c-button--mint u-mt--16"  >
                                            <i class="c-icon c-icon--call-white-type2" aria-hidden="true"></i>
                                            <span>전화걸기</span>
                                        </a>
                                    </div>
                                    <p class="ars-company__info">동의 후 아래 완료 버튼을 눌러주세요.</p>
                                </div>
                            </div>

                        </c:when>
                        <c:otherwise>
                            <div class="c-form">
                                <span class="c-form__title--type2">번호이동할 전화번호</span>
                                <input id="moveMobile" class="c-input onlyNum" type="tel"  maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" title="전화번호 입력" value="${AppformReq.moveMobileFn}${AppformReq.moveMobileMn}${AppformReq.moveMobileRn}">
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_NEW }">

                    <c:if test="${AppformReq.onOffType eq '7' }">
                        <div class="c-form c-reserv-num _self">
                            <div class="title-wrap">
                                <span class="c-form__title">번호예약</span>
                                <span id="searchCnt"> 조회 가능 횟수<b>20회</b></span>
                            </div>
                            <div class="c-box c-box--type1">
                                <span id="spReqWantNumber" >010 - **** -</span>
                                <input class="c-input onlyNum" id="reqWantNumberN" type="tel" maxlength="4" pattern="[0-9]*" placeholder="" value="${AppformReq.reqWantNumber}" >

                            </div>
                            <p class="c-bullet c-bullet--dot u-co-gray">조회 가능 횟수를 초과할 경우 신청서를 재작성 해야 합니다.</p>
                            <div class="c-button-wrap">
                                <button id="btnSearchNumber" action="RSV" class="c-button c-button--full c-button--h48 c-button--mint" >번호조회</button>
                            </div>
                        </div>
                    </c:if>

                    <div class="c-form c-reserv-num _noSelf" >
                        <div class="c-box c-box--type1">
                            <span>1순위 010 - **** -</span>
                            <input class="c-input onlyNum" id="reqWantNumber" type="tel" maxlength="4" pattern="[0-9]*" placeholder="" value="${AppformReq.reqWantNumber}" >
                        </div>
                        <div class="c-box c-box--type1">
                            <span>2순위 010 - **** -</span>
                            <input class="c-input onlyNum" id="reqWantNumber2" type="tel" maxlength="4" pattern="[0-9]*" placeholder="" value="${AppformReq.reqWantNumber2}" >
                        </div>
                        <div class="c-box c-box--type1">
                            <span>3순위 010 - **** -</span>
                            <input class="c-input onlyNum" id="reqWantNumber3" type="tel" maxlength="4" pattern="[0-9]*" placeholder="" value="${AppformReq.reqWantNumber3}">
                        </div>
                        <ul class="c-text-list c-bullet c-bullet--dot u-fs-14 u-mt--12">
                            <li class="c-text-list__item u-co-gray">희망하시는 전화번호 뒷자리 번호를 입력해 주시면 해당 번호로 개통이 진행 됩니다.</li>
                        </ul>
                        <ul class="c-text-list c-bullet c-bullet--fyr u-fs-14">
                            <li class="c-text-list__item u-co-red">단, 1111, 1234와 같이 동일하거나 연속적인 번호(골드번호)는 번호부여가 불가하오니 참고 부탁 드립니다.</li>
                            <li class="c-text-list__item u-co-red">골드번호 예시 안내
                                <div class="cols-wrap" style="gap: 0.25rem 0; margin-top: 0.25rem;">
                                    <div class="col-md-1">① 단일숫자반복 (예: 1111, 2222)</div>
                                    <div class="col-md-1">② 끝자리반복 (예: 0001, 0002)</div>
                                    <div class="col-md-1">③ 짝수쌍패턴 (예: 0011, 1100)</div>
                                    <div class="col-md-1">④ 반복쌍패턴 (예: 0101, 0202)</div>
                                    <div class="col-md-1">⑤ 연속쌍패턴 (예: 1000, 2000)</div>
                                    <div class="col-md-1">⑥ 연속숫자형 (예: 0123, 4567)</div>
                                    <div class="col-md-1">⑦ 전체반복패턴 (예: 1234-1234)</div>
                                    <div class="col-md-1">⑧ 특정의미 번호 (예: 1004(천사), 7942(친구사이))</div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </c:if>




            </div>
            <!-- STEP3 END -->



            <!--  STEP4 START -->
            <div id="step4"  style="display:none" class="_divStep" >


                <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_NEW  or AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM }" >
                    <div class="c-form-step">
                        <span class="c-form-step__number">4</span>
                        <h3 class="c-form-step__title">부가서비스 정보</h3>
                    </div>

                    <h4 class="c-form__title--type2 recoIdArea">친구초대 추천인ID<small>(선택)</small> </h4>
                    <div class="c-form__group recoIdArea" role="group" aria-labeledby="inpG">
                        <div class="c-form__select recoIdArea">
                            <c:set var="recommendFlagList" value="${nmcp:getCodeList(Constants.RECOMMEND_FLAG_GRUP_CODE)}" />
                            <select id="recommendFlagCd" class="c-select">
                                <c:forEach items="${recommendFlagList}" var="recommendFlag" >
                                    <option value="${recommendFlag.dtlCd}" <c:if test="${AppformReq.recommendFlagCd eq recommendFlag.dtlCd }" > selected </c:if> >${recommendFlag.dtlCdNm}</option>
                                </c:forEach>
                            </select>
                            <label class="c-form__label">추천인 정보</label>
                        </div>
                        <input id="recommendInfo" class="c-input recoIdArea" type="text" placeholder="직접입력" title="직접입력" value="${AppformReq.commendId}"  maxlength="20"  >
                        <input type="hidden" name="commendId" id="commendId"  value=""  title="추천 아이디" class="recoIdArea" >
                    </div>
                    <p class="c-bullet c-bullet--dot u-co-gray recoIdArea">추천인ID 오/미입력 시 사은품 지급이 불가하니 정확히 입력 해주세요.</p>

                   <!-- 아무나 SOLO 결합 가입 영역 -->
                    <div id="combineSoloSection" style="display:none;">
                        <hr class="c-hr c-hr--type1 c-expand">
                        <div class="c-title-wrap c-title-wrap--flex">
                          <h4 class="c-form__title--type2 combine u-mt--0 u-mb--0">
                               아무나 SOLO 결합 가입
                            <button data-tpbox="#combine_tp1"><span class="sr-only">아무나 SOLO 결합 가입 상세 보기</span><i class="c-icon c-icon--tooltip-type2" aria-hidden="true"></i></button>
                            <div class="c-tooltip-box" id="combine_tp1">
                              <div class="c-tooltip-box__title">아무나 SOLO 결합</div>
                              <div class="c-tooltip-box__content">
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray">대상 요금제 가입 시 매월 추가 데이터를 제공하는 1인 결합 서비스입니다.</li>
                                    <li class="c-text-list__item u-co-gray">신청 시 개통과 함께 자동 적용되며 즉시 데이터가 제공됩니다.</li>
                                    <li class="c-text-list__item u-co-gray">개통 후에도 별도 신청이 가능하며, 약정이나 위약금은 없습니다.</li>
                                    <li class="c-text-list__item u-co-gray">요금제 변경 시 요금제에 따라 데이터의 제공량이 달라지거나, 결합이 해지 될 수 있습니다.</li>
                                </ul>
                              </div>
                              <button class="c-tooltip-box__close" data-tpbox-close>
                                <span class="c-hidden">툴팁닫기</span>
                              </button>
                            </div>
                            <small>(선택)</small>
                          </h4>
                        </div>
                        <div id="divResultInfo">
                          <div class="combine-self-default u-mt--0">
                            <p>아무나 SOLO 결합을 신청하실 경우<br/><b><em id="combineSoloDataText"></em>이 제공됩니다.</b></p>
                          </div>
                        </div>
                        <div class="c-check-wrap c-check-wrap--col2 u-mt--12">
                            <input class="c-radio c-checkbox--button" id="combineSolo1" type="radio" name="combineSoloType" value="N" checked>
                            <label class="c-label" for="combineSolo1">나중에 신청</label>
                            <input class="c-radio c-checkbox--button" id="combineSolo2" type="radio" name="combineSoloType" value="Y">
                            <label class="c-label" for="combineSolo2">결합 신청</label>
                        </div>
                        <div id="divReg" style="display: none">
                          <div class="combine-agree c-agree u-mt--16">
                            <div class="c-agree__item">
                              <input type="checkbox" class="c-checkbox" id="combineSoloFlag" name="combineSoloFlag">
                              <label class="c-label" for="combineSoloFlag">결합을 위한 필수 확인사항 동의 (필수)</label>
                              <button type="button" class="c-button c-button--xsm" onclick="fnSetEventId('combineSoloFlag');openPage('termsPop','/termsPop.do','cdGroupId1=TERMSCOM&cdGroupId2=TERMSCOM06',0);" >
                                <span class="c-hidden">결합을 위한 필수 확인사항 동의  (필수) 상세보기</span>
                                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                              </button>
                            </div>
                          </div>
                        </div>
                    </div>

                    <hr class="c-hr c-hr--type1 c-expand">
                    <div class="c-title-wrap c-title-wrap--flex">
                        <h4 class="c-heading c-heading--type5 u-fw--medium">부가서비스 신청<small class="u-c-gray u-ml--2">(선택)</small> </h4>
                    </div>
                    <div class="c-accordion c-accordion--type3 add-service">
                        <ul class="c-accordion__box">
                            <li class="c-accordion__item">
                                <div class="c-accordion__head">
                                    <div class="c-accordion__check">
                                        <input class="c-checkbox c-checkbox--type3" id="cbAddServiceCatchCall" type="checkbox">
                                        <label class="c-label" for="cbAddServiceCatchCall">
                                            <span class="text-label">추천</span>
                                            <b>캐치콜 플러스(월 880원)</b><br />
                                            놓친 전화도 문자로 딱! 중요한 순간을 지켜드립니다.
                                        </label>
                                    </div>
                                </div>
                                <div class="c-accordion__panel expand" id="accAddServiceContent">
                                    <div class="c-accordion__inside">-</div>
                                </div>
                                <button class="c-accordion__button" type="button" aria-expanded="false" data-acc-header="#accAddServiceContent" id="accAddService">
                                    <span>서비스 자세히보기<i class="c-icon c-icon--triangle-bottom" aria-hidden="true"></i></span>
                                </button>
                            </li>
                        </ul>
                    </div>

                     <div class="c-accordion c-accordion--type3 add-service u-mt--32">
                        <ul class="c-accordion__box">
                            <li class="c-accordion__item">
                                <div class="c-accordion__head">
                                    <div class="c-accordion__check">
                                        <input class="c-checkbox c-checkbox--type3" id="callWaiting" type="checkbox">
                                        <label class="c-label" for="callWaiting">
                                            <b>통화중대기 설정/해제 (무료)</b><br />
                                            현재 통화는 잠시 대기, 새 전화는 바로 연결
                                        </label>
                                    </div>
                                </div>
                                <div class="c-accordion__panel expand" id="callWaitingServiceContent">
                                    <div class="c-accordion__inside">-</div>
                                </div>
                                <button class="c-accordion__button" type="button" aria-expanded="false" data-acc-header="#callWaitingServiceContent" id="callWaitingService">
                                    <span>서비스 자세히보기<i class="c-icon c-icon--triangle-bottom" aria-hidden="true"></i></span>
                                </button>
                            </li>
                        </ul>
                    </div>

                    <div id="divAdditionTab1" >
                        <div class="add-service-wrap c-addition-wrap">
                            <h5 class="add-service-title">
                                <span>선택한 부가서비스</span>
                                <span><span id="spAdditionCnt">0</span><em>개</em></span>
                            </h5>
                            <div class="addition-list">
                                <div class="c-charge__panel" id="divAdditionListEmpty" >
                                    <p>선택된 부가서비스가 없습니다.</p>
                                </div>
                                <div id="divAdditionListFree" style="display:none">

                                </div>
                                <dl class="c-addition c-addition--type1 c-hidden">
                                    <dt>유료 부가서비스</dt>
                                    <dd class="u-ta-right" id="AdditionListPriceCnt"><b>0</b>건</dd>
                                </dl>
                                <div id="divAdditionListPrice" style="display:none">

                                </div>
                            </div>
                            <div class="c-button-wrap u-mt--16">
                                <button id="btnAddition" class="c-button c-button--sm c-button--primary" data-dialog-trigger="#additionDialog">기타 부가서비스 선택하기</button>
                            </div>
                        </div>
                        <dl class="add-service-result">
                            <dt>부가서비스 합계<span>(VAT 포함)</span></dt>
                            <dd class="u-ta-right" id="totalRantalTxt" ><b>0</b>원</dd>
                        </dl>
                        <p class="c-bullet c-bullet--dot u-co-gray u-mt--20">개통 시 기본제공되는 부가서비스는 상품소개 페이지를 참고 부탁드리며, 부가서비스는 홈페이지, App, 고객센터를 통해 추가로 가입/해지가 가능합니다.</p>
                    </div>


                </c:if>

                <hr class="c-hr c-hr--type1 c-expand _insrProdForm">
                <div class="c-title-wrap c-title-wrap--flex _insrProdForm">
                    <h4 class="c-heading c-heading--type5 u-fw--medium"> 휴대폰안심보험 신청<small>(선택)</small></h4>
                    <button id="btnInsrProd" class="c-button c-button--sm c-button--white" >신청/변경</button>
                </div>






                <div id="divInsrProdInfo" class="u-mt--32 _insrProdForm"  >
                    <button id="btnInsrProd2" class="c-button c-button--md c-button--gray c-button--full u-flex-center" >
                        <i class="c-icon c-icon--bang--yellow" aria-hidden="true" ></i>선택한 휴대폰안심보험 서비스가 없습니다.
                        <!-- <i class="c-icon c-icon--arrow" aria-hidden="true"></i> -->
                    </button>
                </div>

                <!--서비스 표시 박스 -->
                <ul class="c-card-list _insrProdList" style="display:none" >
                    <li class="c-card c-card--type3 var-type1" id="insrProdView" >
                        <!-- <div class="c-card__box box-gray">
                            <div class="c-card__title u-mt--0">
                                <b>안드로이드 플래티넘</b>
                                <p class="c-card__sub u-pb--24">
                                    <span>자급제 신품</span>
                                    <span>중고 단말기 보험</span>
                                </p>
                            </div>
                            <div class="c-card__price-box">
                                <span class="ico-type">
                                  <img src="/resources/images/mobile/common/ico_android.png" alt="">
                                </span>
                                <span class="c-text c-text--type3 u-ml--auto">월 기본료</span>
                                <span class="u-ml--4">
                                  <b>4,900 원</b>
                                </span>
                            </div>
                        </div> -->
                    </li>
                </ul>

                <div class="c-card-list _insrProdList" style="display:none">
                    <div class="c-accordion c-accordion--type5 acc-agree">
                        <h4 class="c-heading c-heading--type2">휴대폰안심보험 신청 동의</h4><!-- 관련 약관 URL 을 연결해주세요.-->
                        <div class="c-accordion__box">
                            <div class="c-accordion__item">
                                <div class="c-accordion__head">
                                    <div class="c-accordion__check">
                                        <input class="c-checkbox" id="btnInsrAllCheck" type="checkbox">
                                        <label class="c-label" for="btnInsrAllCheck">휴대폰안심보험 가입 동의에 대하여 내용을 읽어보았으며, 보험가입에 동의합니다. (일괄 동의)</label>
                                    </div>
                                    <button class="c-accordion__button u-ta-right is-active" type="button" aria-expanded="false" data-acc-header="#insr_agreeA1" >
                                        <div class="c-accordion__trigger"> </div>
                                    </button>
                                </div>
                                <div class="c-accordion__panel expand open" id="insr_agreeA1">
                                    <div class="c-accordion__inside">
                                        <div class="c-agree__item">
                                            <input id="clauseInsrProdFlag" code="ClauseInsur01M" cdgroupid1="ClauseInsur" docver="0" type="checkbox" class="c-checkbox c-checkbox--type2 _agreeInsr">
                                            <label class="c-label" for="clauseInsrProdFlag">이동통신 단말기 보험 상품 설명서 교부 확인<span class="u-co-gray">(필수)</span>
                                            </label>
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseInsrProdFlag');openPage('termsPop','/termsPop.do','cdGroupId1=ClauseInsur&cdGroupId2=ClauseInsur01M');">
                                                <span class="c-hidden">상세보기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </div>

                                        <div class="c-agree__item">
                                            <input id="clauseInsrProdFlag02" code="ClauseInsur02" cdgroupid1="ClauseInsur" docver="0" type="checkbox"  class="c-checkbox c-checkbox--type2 _agreeInsr">
                                            <label class="c-label" for="clauseInsrProdFlag02">개인정보 수집 및 이용동의 <span class="u-co-gray">(필수)</span>
                                            </label>
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseInsrProdFlag02');openPage('termsPop','/termsPop.do','cdGroupId1=ClauseInsur&cdGroupId2=ClauseInsur02');">
                                                <span class="c-hidden">상세보기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </div>

                                        <div class="c-agree__item">
                                            <input id="clauseInsrProdFlag03" code="ClauseInsur03" cdgroupid1="ClauseInsur" docver="0" type="checkbox"  class="c-checkbox c-checkbox--type2 _agreeInsr">
                                            <label class="c-label" for="clauseInsrProdFlag03">개인정보 제 3자 제공동의<span class="u-co-gray">(필수)</span>
                                            </label>
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseInsrProdFlag03');openPage('termsPop','/termsPop.do','cdGroupId1=ClauseInsur&cdGroupId2=ClauseInsur03');">
                                                <span class="c-hidden">상세보기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="c-box c-box--type6 u-mt--40">
                        <h3 class="c-heading c-heading--type6 u-mt--0">
                            <span class="c-text--underline" data-dialog-trigger="#productManualModal" >이동통신 단말기 보험 상품 설명서</span>
                        </h3>
                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                            <li class="c-text-list__item">휴대폰 안심 서비스의 가입을 위해서는 명의자 본인 확인을 위해 휴대폰 SMS인증이 필요합니다.</li>
                            <li class="c-text-list__item">본인 명의의 휴대폰을 보유하고 계시지 않으실 경우에는 개통 후 고객센터를 통해 신청 부탁 드립니다.</li>
                            <li class="c-text-list__item">안드로이드/아이폰 전용 상품은 자급제 단말 구매일로부터 44일 이내 신청이 가능하며, 이외 상품은 개통일로부터 44일 이내 가입이 가능합니다.</li>
                        </ul>
                    </div>
                    <button id="btnAuthInsr" class="c-button c-button--h48 c-button--mint c-button--full u-mt--32">휴대폰 인증</button>
                </div>

                <!-- 자급제 보상 서비스 신청 -->
                <!-- (AppformReq.usimKindsCd eq '09') || (AppformReq.onOffType eq '7')  || (AppformReq.onOffType ne '7' && AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM && 'Y' ne AppformReq.sesplsYn) -->
                <c:if test="${false}">
                    <hr class="c-hr c-hr--type1 c-expand _rwdProdForm">

                    <div class="c-title-wrap c-title-wrap--flex _rwdProdForm">
                        <h4 class="c-heading c-heading--type5 u-fw--medium"> 자급제 보상 서비스 신청<small>(선택)</small></h4>
                        <button id="btnRwdProd" class="c-button c-button--sm c-button--white" >신청/변경</button>
                    </div>

                    <div id="divRwdProdInfo" class="_rwdProdForm">
                        <hr class="c-hr c-hr--type2">
                        <button id="btnRwdProd2" class="c-button c-button--md c-button--gray c-button--full u-flex-center">
                            <!-- case - 자급제 보상 서비스 미가입 시 (데이터 없음) -->
                            <i class="c-icon c-icon--bang--yellow" aria-hidden="true" ></i>가입된 자급제 보상 서비스가 없습니다
                        </button>
                    </div>

                    <!-- case - 자급제 보상 서비스 가입 시 (데이터 있음) -->
                    <ul class="c-card-list _rwdProdList" style="display:none" >
                        <li class="c-card c-card--type3 var-type1" id="rwdProdView" >
                            <!-- 선택한 자급제 보상 서비스 표시 -->
                        </li>
                    </ul>

                    <!-- 자급제 보상 서비스 가입 시 필수입력값, 필수약관, 휴대폰 인증 -->
                    <div class="c-card-list _rwdProdList" style="display:none">

                        <!-- rwd 구입가 -->
                        <div class="c-form">
                            <h4 class="c-form__title">자급제 단말기 구입가<span class="c-form__sub">(단위: 원)</span></h4>
                            <div class="c-form__input" style="margin-top: 0;">
                                <input class="c-input numOnly" id="rwdBuyPric" name="rwdBuyPric" type="number" placeholder="구입가격 입력" maxlength="15">
                                <label class="c-form__label" for="rwdBuyPric">구입가격</label>
                            </div>
                        </div>

                        <!-- rwd imei 등록 (esim개통인 경우 imei값 이전에 입력받은 값으로 사용) -->
                        <div class="c-form">
                            <h4 class="c-form__title">단말기 정보값<span class="c-form__sub">(IMEI)</span></h4>
                            <div class="c-form__input" style="margin-top: 0;">
                                <input class="c-input" id="rwdImei1" name="rwdImei1" type="text" placeholder="IMEI1" value="${AppformReq.imei1}" readOnly>
                                <label class="c-form__label" for="rwdImei1">IMEI1</label>
                            </div>
                            <div class="c-form__input">
                                <input class="c-input" id="rwdImei2" name="rwdImei2" type="text" placeholder="IMEI2" value="${AppformReq.imei2}" readOnly>
                                <label class="c-form__label" for="rwdImei2">IMEI2</label>
                            </div>
                            <div id="rwdImeiForm">
                                <div class="c-box c-box--type6">
                                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                                        <li class="c-text-list__item u-co-gray">자급제 보상 서비스 가입을 위해서는 IMEI 등록이 선행되어야 하며, IMEI 캡쳐화면 이미지 업로드가 필요합니다.</li>
                                        <li class="c-text-list__item u-co-gray u-pb--8">이미지 업로드 시 자동으로 입력됩니다.<a class="c-text-link--bluegreen" data-dialog-trigger="#uploadguide-dialog">이미지 가이드 보기</a></li>
                                    </ul>
                                </div>
                                <div class="c-button-wrap u-mt--8">
                                    <label for="rwdImeiImage" class="c-button c-button--full c-button--h48 c-button--mint">이미지 등록</label>
                                    <input type="file" class="c-hidden" id="rwdImeiImage" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp" />
                                </div>
                            </div>
                        </div>
                        <!--//required sub info > imei-->


                        <!-- rwd 구매영수증 등록 -->
                        <div class="c-form" id="fileContainer">
                            <span class="c-form__title" id="inpFileTitle">구매영수증 첨부하기<span class="u-co-gray">(필수)</span></span>
                            <div class="c-form__group" role="group" aria-labelledby="inpFileTitle">
                                <div class="upload-image">
                                    <label class="upload-image__label">
                                        <input class="upload-image__hidden" name="rwdFile1" type="file" id="rwdFile1" accept=".jpg, .gif, .png" onchange="setThumb();">
                                        <i class="c-icon c-icon--photo" aria-hidden="true"></i>
                                        <span class="u-co-gray c-label__sub">첨부파일 0/1</span>
                                    </label>
                                    <div class="staged_img-list">
                                        <!-- 등록된 파일 영역 -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <p class="c-bullet c-bullet--fyr u-co-gray">2MB 이내의 jpg, gif, png 형식으로 등록가능</p>
                        <!-- // rwd 구매영수증 등록 -->

                        <!-- rwd 약관동의 -->
                        <div class="c-form">
                            <span class="c-form__title">자급제 보상 서비스 신청 동의</span>
                            <div class="c-agree u-mt--0">
                                <input class="c-checkbox" id="clauseRwdFlag" type="checkbox">
                                <label class="c-label" for="clauseRwdFlag">이용약관 및 개인정보 수집/이용에 모두 동의합니다.</label>

                                <!-- 약관 1 -->
                                <div class="c-agree__item">
                                    <input id="clauseRwdRegFlag" code="${Constants.CLAUSE_RWD_REG_FLAG}"  docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _rwdAgree">
                                    <label class="c-label" for="clauseRwdRegFlag">상품설명(이용료) 및 서비스 가입 동의<span class="u-co-gray">(필수)</span>
                                    </label>
                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdRegFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_REG_FLAG}',0);" >
                                        <span class="c-hidden">상세보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                </div>

                                <!--약관 2-->
                                <div class="c-agree__item">
                                    <input id="clauseRwdPaymentFlag" code="${Constants.CLAUSE_RWD_PAYMENT_FLAG}" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _rwdAgree"  >
                                    <label class="c-label" for="clauseRwdPaymentFlag">만기 시 보장율/보장금액 지급기준 이행 동의<span class="u-co-gray">(필수)</span>
                                    </label>
                                    <button class="c-button c-button--xsm"  onclick="fnSetEventId('clauseRwdPaymentFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_PAYMENT_FLAG}',0);" >
                                        <span class="c-hidden">만기 시 보장율/보장금액 지급기준 이행 동의 상세 팝업 열기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                </div>

                                <!--약관 3-->
                                <div class="c-agree__item">
                                    <input id="clauseRwdRatingFlag" code="S${Constants.CLAUSE_RWD_RATING_FLAG}" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _rwdAgree"  >
                                    <label class="c-label" for="clauseRwdRatingFlag">서비스 단말 반납 조건, 등급산정 및 평가 기준 동의<span class="u-co-gray">(필수)</span>
                                    </label>
                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdRatingFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_RATING_FLAG}');" >
                                        <span class="c-hidden">상세보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                </div>

                                <div class="c-agree__item">
                                    <input id="clauseRwdPrivacyInfoFlag" code="S${Constants.CLAUSE_RWD_PRIVACY_INFO_FLAG}" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _rwdAgree"  >
                                    <label class="c-label" for="clauseRwdPrivacyInfoFlag">개인정보 수집ㆍ이용에 대한 동의 상세<span class="u-co-gray">(필수)</span>
                                    </label>
                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdPrivacyInfoFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_PRIVACY_INFO_FLAG}');" >
                                        <span class="c-hidden">상세보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                </div>

                                <div class="c-agree__item">
                                    <input id="clauseRwdTrustFlag" code="${Constants.CLAUSE_RWD_TRUST_FLAG}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 hidden _rwdAgree">
                                    <label class="c-label" for="clauseRwdTrustFlag">서비스 위탁사 개인정보 수집ㆍ이용ㆍ제공ㆍ위탁 동의서 <span class="u-co-gray">(필수)</span>
                                    </label>
                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdTrustFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_TRUST_FLAG}',0);" >
                                        <span class="c-hidden">상세보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <!-- // rwd 약관동의 -->

                        <!-- rwd 휴대폰 인증 -->
                        <div class="c-button-wrap u-mt--40">
                            <button class="c-button c-button--full c-button--h48 c-button--mint" id="btnAuthRwd">휴대폰 인증</button>
                        </div>

                        <div class="c-box c-box--type6">
                            <strong class="c-heading u-fs-15">
                                <button class="c-button c-text c-text--line" data-dialog-trigger="#rwdManualModal">자급제 보상 서비스 상품 설명서</button>
                            </strong>
                            <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2 u-mt--20">
                                <li class="c-text-list__item u-co-gray">서비스 보상 신청은 가입 후 19개월 차 ~ 21개월 차에 새로운 단말기로 구매한 구매영수증 필히 제출하고 kt M mobile 이동통신서비스를 유지하는 경우 가능합니다.</li>
                                <li class="c-text-list__item u-co-gray">보상 받을 단말은 서비스 가입 당시 구매한 단말기 (IMEI 일치)여야하며 (단, 분실보험에 의한 기변, 제조사 공식 서비스센터로부터 A/S 기변한 고객은 변경된 동종 단말에 한해 반납 가능) 반납 단말의 상태가 완파, 계정잠김, 단말 분실 혹은 매입 할 수 없는 등급일 경우 서비스 신청이 불가 할 수 있습니다.</li>
                            </ul>
                        </div>
                    </div>
                </c:if>
                <!-- // 자급제 보상 서비스 신청 -->

                <!-- 사은품 리스트-->
                <section id="divGift" class="gift-section" style="display:none;"></section>


            </div>
            <!-- STEP4 END -->


            <!-- STEP5 START -->
            <div id="step5" style="display:none" class="_divStep" >

                <div class="c-form-step">
                    <span class="c-form-step__number">5</span>
                    <h3 class="c-form-step__title">납부정보·가입정보 확인</h3>
                </div>
                <div class="c-form">
                    <span class="c-form__title--type2 u-mt--50">명세서 수신 유형</span>
                    <div class="c-check-wrap">
                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button c-radio--button--type2" id="cstmrBillSendCode02" value="CB" type="radio" name="cstmrBillSendCode" <c:if test="${AppformReq.cstmrBillSendCode eq 'CB' }" > checked </c:if> >
                            <label class="c-label" for="cstmrBillSendCode02">
                                <i class="c-icon c-icon--email-bill" aria-hidden="true"></i>
                                <span> 이메일
                    <br>명세서
                  </span>
                            </label>
                        </div>

                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button c-radio--button--type2" id="cstmrBillSendCode03" value="MB" type="radio" name="cstmrBillSendCode" <c:if test="${AppformReq.cstmrBillSendCode eq 'MB' }" > checked </c:if>
                            <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NM}">disabled</c:if>>
                            <label class="c-label" for="cstmrBillSendCode03">
                                <i class="c-icon c-icon--mobile-bill" aria-hidden="true"></i>
                                <span> 모바일<br>명세서(MMS)</span>
                            </label>
                        </div>

                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button c-radio--button--type2" id="cstmrBillSendCode01" value="LX" type="radio" name="cstmrBillSendCode" <c:if test="${AppformReq.cstmrBillSendCode eq 'LX' }" > checked </c:if> >
                            <label class="c-label" for="cstmrBillSendCode01">
                                <i class="c-icon c-icon--post-bill" aria-hidden="true"></i>
                                <span>우편
                    <br>명세서
                  </span>
                            </label>
                        </div>
                    </div>
                      <c:choose>
                            <c:when test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NM}">
                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                        <li class="c-text-list__item">미성년의 경우 MMS명세서 선택이 불가합니다. 희망하시는 경우 개통 후 고객센터로 문의 부탁드립니다.</li>
                    </ul>
                    </c:when>
                    <c:otherwise>
                     <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                        <li class="c-text-list__item">이메일, 모바일(MMS) 명세서를 선택 하시면 청구내역을 빠르고 정확하게 받아보실 수 있습니다.</li>
                    </ul>
                    </c:otherwise>
                    </c:choose>
                </div>

                <div class="c-form">
                    <span class="c-form__title--type2">요금 납부 방법</span>
                    <div class="c-check-wrap">
                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button" id="reqPayType01" value="D" type="radio" name="reqPayType" <c:if test="${AppformReq.reqPayType eq 'D' }" > checked </c:if> >
                            <label class="c-label" for="reqPayType01">자동이체</label>
                        </div>
                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button" id="reqPayType02" value="C"  type="radio" name="reqPayType" <c:if test="${AppformReq.reqPayType eq 'C' }" > checked </c:if> >
                            <label class="c-label" for="reqPayType02">신용카드</label>
                        </div>
                    </div>
                    <div class="c-button-wrap c-button-wrap--right _card" style="display:none" id="divScanCard" >
                        <button class="c-button _ocrRecord" data-type="creditcard" >
                            <span class="c-text c-text--type3 u-co-sub-4">스캔하기</span>
                            <i class="c-icon c-icon--scan" aria-hidden="true"></i>
                        </button>
                    </div>

                    <!-- 자동이체일 경우-->
                    <div class="c-form__group _bank" role="group" aria-labeledby="inpBankNum">
                        <div class="c-form__select">
                            <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />
                            <select id="reqBank" class="c-select">
                                <option value="">은행명 선택</option>
                                <c:forEach items="${bankList }" var="itemBank" >
                                    <option value="${itemBank.dtlCd }" <c:if test="${AppformReq.reqBank eq itemBank.dtlCd }" > selected </c:if> >${itemBank.dtlCdNm }</option>
                                </c:forEach>
                            </select>
                            <label class="c-form__label">은행명</label>
                        </div>
                        <div class="c-form__input">
                            <input class="c-input onlyNum" id="reqAccountNumber" maxlength='20' type="tel" name="" pattern="[0-9]*" placeholder="'-'없이 입력" value="${AppformReq.reqAccountNumber }" >
                            <!--[2021-11-22] 인풋타입 변경-->
                            <label class="c-form__label onlyNum" for="reqAccountNumber">계좌번호</label>
                        </div>
                    </div>
                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2 _bank">
                        <li class="c-text-list__item">납부방법은 본인명의의 카드 또는 계좌로만 등록 가능합니다.(미성년자의 경우 법정대리인 명의 포함)</li>
                        <li class="c-text-list__item">매월 21일 납부</li>
                        <li class="c-text-list__item">평생계좌(개인 핸드폰번호를 계좌번호로 사용하는 계좌)는 이용 불가능합니다.</li>
                    </ul>

                    <div class="c-button-wrap _bank">
                        <button id="btnCheckAccount" class="c-button c-button--full c-button--h48 c-button--mint">계좌번호 유효성 체크</button>
                    </div>

                    <!-- 신용카드 일 경우-->
                    <div class="c-form__group _card" role="group" aria-labeledby="inpNum" style="display:none">

                        <div class="c-form__input">
                            <input class="c-input onlyNum" id="reqCardNo" maxlength="16" type="tel" name="" pattern="[0-9]*" placeholder="'-'없이 입력" value="${AppformReq.reqCardNo }" >
                            <!--[2021-11-22] 인풋타입 변경-->
                            <label class="c-form__label" for="reqCardNo">카드번호</label>
                        </div>
                        <span class="c-form__title--type2">유효기간</span>
                        <div class="c-select-wrap">
                            <select id="reqCardMm" class="c-select">
                                <option value="" >MM</option>
                                <option value="01"  <c:if test="${AppformReq.reqCardMm eq '01' }" > selected </c:if> >01</option>
                                <option value="02"  <c:if test="${AppformReq.reqCardMm eq '02' }" > selected </c:if> >02</option>
                                <option value="03"  <c:if test="${AppformReq.reqCardMm eq '03' }" > selected </c:if> >03</option>
                                <option value="04"  <c:if test="${AppformReq.reqCardMm eq '04' }" > selected </c:if> >04</option>
                                <option value="05"  <c:if test="${AppformReq.reqCardMm eq '05' }" > selected </c:if> >05</option>
                                <option value="06"  <c:if test="${AppformReq.reqCardMm eq '06' }" > selected </c:if> >06</option>
                                <option value="07"  <c:if test="${AppformReq.reqCardMm eq '07' }" > selected </c:if> >07</option>
                                <option value="08"  <c:if test="${AppformReq.reqCardMm eq '08' }" > selected </c:if> >08</option>
                                <option value="09"  <c:if test="${AppformReq.reqCardMm eq '09' }" > selected </c:if> >09</option>
                                <option value="10"  <c:if test="${AppformReq.reqCardMm eq '10' }" > selected </c:if> >10</option>
                                <option value="11"  <c:if test="${AppformReq.reqCardMm eq '11' }" > selected </c:if> >11</option>
                                <option value="12"  <c:if test="${AppformReq.reqCardMm eq '12' }" > selected </c:if> >12</option>
                            </select>
                            <label class="c-form__label c-hidden" for="reqCardMm">월</label>
                            <fmt:formatDate value="${nmcp:getDateToCurrent(0)}" pattern="yyyy" var="nowYear" />
                            <select id="reqCardYy" class="c-select">
                                <option value="">YYYY</option>
                                <c:forEach begin="0" end="10" var="item">
                                    <option value="${nowYear+item}" <c:if test="${AppformReq.reqCardYy eq (nowYear+item) }" > selected </c:if> >${nowYear+item}</option>
                                </c:forEach>
                            </select>
                            <label class="c-form__label c-hidden" for="reqCardYy" >년</label>
                        </div>
                    </div>
                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2 _card" style="display:none" >
                        <li class="c-text-list__item">납부방법은 본인명의의 카드 또는 계좌로만 등록 가능합니다.(미성년자의 경우 법정대리인 명의 포함)</li>
                        <li class="c-text-list__item">1회차 매월 10~11일경.</li>
                    </ul>
                    <div class="c-button-wrap _card" style="display:none">
                        <button id="btnCheckCardNo" class="c-button c-button--h48 c-button--full c-button--mint">신용카드 유효성 체크</button>
                    </div>


                </div>





                <c:if test="${(AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_PHONE) || (AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM && 'Y' eq AppformReq.sesplsYn)}">

                    <c:set var="cardDcInfoList" value="${nmcp:getCodeList('BE0020')}" />
                    <hr class="c-hr c-hr--type1 c-expand">

                    <div class="c-form">
                        <h4 class="c-form__title--type2">단말기 결제 정보
                            <!-- <a data-tpbox="#tpA1">
                                <i class="c-icon c-icon--tooltip-large" aria-hidden="true"></i>
                            </a> -->
                        </h4>
                        <div class="c-tooltip-box" id="tpA1">
                            <!--[2022-02-24] 툴팁박스 내용 수정-->
                            <div class="c-tooltip-box__content">
                                <!--[2022-02-24] 유틸클래스 추가(u-mt--48)-->
                                <ul class="c-text-list c-bullet c-bullet--hyphen u-mt--48">
                                    <li class="c-text-list__item">kt M모바일 로그인한 회선 보유 회원만 적립 및 사용이 가능합니다.</li>
                                    <li class="c-text-list__item">kt M모바일 포인트는 최소 1,000 포인트 이상, 500포인트 단위로 기입하여 사용 가능합니다.(1포인트=1원).</li>
                                    <li class="c-text-list__item">단말 결제금액이 1,000포인트 미만일 경우 사용 불가합니다.</li>
                                    <li class="c-text-list__item">주문/결제 시 다른 결제수단과 함께 이용할 수 있습니다.</li>
                                    <li class="c-text-list__item">적립된 포인트의 유효기간은 적립일로부터 1년이며, 유효기간이 경과된 포인트는 월 단위로 자동 소멸됩니다.</li>
                                </ul>
                            </div>
                            <button class="c-tooltip-box__close" data-tpbox-close>
                                <span class="c-hidden">툴팁닫기</span>
                            </button>
                        </div>
                        <hr class="c-hr c-hr--type2">




                        <c:if test="${userDivision eq Constants.DIVISION_CODE_LEGALLY_MEMBER}">
                            <%-- <span class="c-form__title u-mt--0">회선선택</span>
                            <select class="c-select" name="usePointSvcCntrNo" id="usePointSvcCntrNo"   title="서비스번호" >
                              <c:forEach items="${cntrList}" var="item">
                                <option value="${item.svcCntrNo }" >${item.cntrMobileNoMasking }</option>
                              </c:forEach>
                            </select>
                            <div class="c-title-wrap c-title-wrap--flex">
                              <span class="c-form__title u-mt--0 u-mb--0" >포인트</span>
                              <span class="c-text c-text--type3 u-co-gray u-ml--auto">보유포인트</span>
                              <span class="c-text c-text--type3 u-ml--4">
                                <b id="totRemainPointTxt">0 P</b>
                              </span>
                            </div>
                            <div class="c-input-wrap">
                              <input class="c-input onlyNum" id="usePoint" type="number" placeholder="0" title="포인트">
                              <button id="btnPointApply" class="c-button c-button--sm c-button--underline">적용</button>
                            </div> --%>
                        </c:if>




                        <c:if test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM && 'Y' eq AppformReq.sesplsYn}">
                            <c:choose>
                                <c:when test="${userDivision eq Constants.DIVISION_CODE_LEGALLY_MEMBER}">
                                    <span class="c-form__title u-mt--0">결제 방법</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="c-form__title u-mt--0">결제 방법</span>
                                </c:otherwise>
                            </c:choose>

                            <div class="c-check-wrap">
                                <input class="c-radio" id="radPayType1" type="radio" value="1" name="radPayType" >
                                <label class="c-label" for="radPayType1">간편결제</label>
                                <input class="c-radio" id="radPayType2" type="radio" value="2" name="radPayType" checked>
                                <label class="c-label" for="radPayType2">일반결제</label>
                            </div>
                            <p class="c-bullet c-bullet--dot u-co-gray">간편결제 시 즉시할인 카드를 선택할 수 없습니다.</p>

                            <span class="c-form__title u-mt--20 _cardDcDiv">즉시할인 카드</span>

                            <select id="cardDcCd" class="c-select _cardDcDiv">
                                <option value="">선택</option>
                                <c:forEach items="${cardDcInfoList}" var="cardDcInfo">
                                    <fmt:parseDate value="${fn:substring(cardDcInfo.pstngStartDate, 0,8)}" var="pstngStartDate" pattern="yyyyMMdd"/>
                                    <fmt:parseDate value="${fn:substring(cardDcInfo.pstngEndDate, 0,8)}" var="pstngEndDate" pattern="yyyyMMdd"/>
                                    <c:choose>
                                        <c:when test="${cardDcInfo.expnsnStrVal1 eq 'PCT'}">
                                            <c:set var="priceDcTxt" value="${cardDcInfo.expnsnStrVal2}%" />
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber value="${cardDcInfo.expnsnStrVal2}" var="priceDcTxt" pattern="#,###" />
                                            <c:set var="priceDcTxt" value="${priceDcTxt}원" />
                                        </c:otherwise>
                                    </c:choose>

                                    <option value="${cardDcInfo.dtlCd}" cardNm="${cardDcInfo.dtlCdNm}"
                                            cardDcAmt="${cardDcInfo.expnsnStrVal2}"  cardDcDivCd="${cardDcInfo.expnsnStrVal1}"  maxDcAmt="${cardDcInfo.expnsnStrVal3}"
                                            pstngStartDate="<fmt:formatDate value="${pstngStartDate}" pattern="yyyy-MM-dd"/>"
                                            pstngEndDate="<fmt:formatDate value="${pstngEndDate}" pattern="yyyy-MM-dd"/>"
                                            label="${cardDcInfo.dtlCdNm}카드(${priceDcTxt} 즉시할인)">
                                            ${cardDcInfo.dtlCdNm}카드(${priceDcTxt}원 즉시할인)
                                    </option>
                                </c:forEach>
                                <option value="etc" label="그외 카드"> 그외 카드</option>
                            </select>
                            <p class="c-bullet c-bullet--dot u-co-gray _cardDcDiv" id="cardDescLi" >즉시할인 카드 선택하세요</p>
                            <hr class="c-hr c-hr--type2">
                        </c:if>

                        <div class="c-addition-wrap" id="divChargeInfo">

                        </div>

                        <c:if test="${ '01' eq AppformReq.settlWayCd}">
                            <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2"  >
                                <li class="c-text-list__item u-co-gray">주문/결제 취소는 배송 준비 전까지만 가능합니다.</li>
                                <li class="c-text-list__item u-co-gray">개통 취소 시 재 가입이 불가한 요금제로 복구처리 불가 합니다.</li>
                                <li class="c-text-list__item u-co-gray">개통 취소는 기기불량, 기기불량, 통화품질 불량 시 개통일로부터 14일 이내(배송기간 포함) 가능합니다.<br/>
                                    (단, 개통 후 취소시까지 발생된 요금은 일할계산 처리되어 청구 됩니다.)</li>
                                <li class="c-text-list__item u-co-gray">교환/반품은 고객센터(1899-5000)를 통해 사전 확인 후 발송해 주시기 바랍니다.</li>
                                <li class="c-text-list__item u-co-gray">교환 반품 시 단말기 본체(박스포함), 구성품이 누락되지 않아야 합니다.</li>
                                <li class="c-text-list__item u-co-gray">단말기 교환/반품 시 개인정보보호를 위하여 단말기 비밀번호, 패턴, 지문락 등을 설정 초기화하여 반송해 주셔야 합니다.</li>
                                <li class="c-text-list__item u-co-gray">개인구매 후 부착하신 액세서리(보호필름, 튜닝제품 등)은 보상처리되지 않는 점 양해 부탁 드립니다.</li>
                            </ul>

                        </c:if>






                    </div>




                </c:if>

            </div>
            <!-- STEP5 END -->





            <div id="divButtonStep1" class="c-button-wrap">
                <button id="btnBefore" class="c-button c-button--full c-button--gray" style="display: none;">이전</button>
                <c:choose>
                    <c:when test="${AppformReq.onOffType eq '7'}">
                        <!--  GTM 추적을 위한 data-step 추가 -->
                        <button id="btnNext" class="c-button c-button--full c-button--primary is-disabled" data-step="11" onclick="gtag_report_conversion(this.getAttribute('data-step'))">다음</button>
                    </c:when>
                    <c:otherwise>
                        <button id="btnNext" class="c-button c-button--full c-button--primary is-disabled"  data-step="31" onclick="gtag_report_conversion(this.getAttribute('data-step'))">다음</button>
                    </c:otherwise>
                </c:choose>
            </div>



            <div id="divButtonStep6" class="c-button-wrap" style="display:none" >
                <a class="c-button c-button--full c-button--gray" href="/">메인으로</a>
            </div>




        </div>



    </t:putAttribute>

    <t:putAttribute name="popLayerAttr">

        <!-- 하단 bottom-sheet trigger  영역 시작 (bottom-trigger 작동 시 is-active class 부여해주세요.)-->
        <div class="c-button-wrap c-button-wrap--b-floating is-active" id="bs_floating_button" data-floating-bs-trigger="#bsSample2">
            <button class="c-button c-button--bs-openner">
                <p class="c-text c-text--type3 u-fw--regular">
                    월 예상 납부금액
                    <span class="fs-26 u-ml--8" id="paySumMnthTxt">0</span> <span class="fs-14 fw-normal">원</span>
                </p>
                <p class="c-text c-text--type5 u-co-red-sub1">가입비 및 유심비 등 기타요금은 별도 청구됩니다.</p>
                <i class="c-icon c-icon--arrow-up" aria-hidden="true"></i>
            </button>
        </div>
        <!-- 하단 bottom-sheet trigger  영역 끝-->

        <div class="c-modal c-modal--bs" id="bsSample2" role="dialog" tabindex="-1" aria-describedby="#bsDetailDesc2" aria-labelledby="#bsDetailtitle2">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="bsDetailtitle2"></h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--arrow-down" aria-hidden="true"></i>
                            <span class="c-hidden">바텀시트 닫기</span>
                        </button>
                    </div>

                    <!--[2021-11-23] 모달내 배너 스와이핑 처리 추가변경 시작 (하단 스와이퍼 js 업데이트 확인요망)===================-->
                    <div class="c-modal__body u-pt--0" id="bsDetailDesc2">
                        <div class="top-sticky-banner">
                            <div class="swiper-event-banner swiper-container c-expand" id="swiperCardBanner">
                                <div class="swiper-wrapper u-mb--24" id="O001"></div>
                                <!--[$2021-11-23] 모달내 배너 스와이핑 처리 추가변경 끝 ===================-->
                            </div>
                        </div>
                        <div class="c-addition-wrap">
                            <dl class="c-addition c-addition--type1">
                                <dt>가입정보</dt>
                            </dl>
                            <dl class="c-addition c-addition--type2">
                                <dt>고객구분</dt>
                                <dd class="u-ta-right" id="ddCstmrType">-</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2">
                                <dt>가입유형</dt>
                                <dd class="u-ta-right" id="ddOperTypeNm"><!-- 번호이동 --></dd>
                            </dl>
                            <c:if test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_PHONE  || (AppformReq.onOffType ne '5' && AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM && 'Y' eq AppformReq.sesplsYn) }">
                                <dl class="c-addition c-addition--type2">
                                    <dt>단말기</dt>
                                    <dd class="u-ta-right" id="ddProdNm">-</dd>
                                </dl>
                            </c:if>



                            <c:if test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_PHONE  }">
                                <hr class="c-hr c-hr--type2">
                                <dl class="c-addition c-addition--type1">
                                    <dt>월 단말요금(A)</dt>
                                    <dd class="u-ta-right"><b id="payMnthAmtTxt">-</b> 원</dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt>단말기 출고가</dt>
                                    <dd class="u-ta-right" ><b id="hndstAmtTxt">-</b> 원</dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt>공통지원금</dt>
                                    <dd class="u-ta-right" ><em id="subsdAmtTxt">-</em> 원</dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt>추가지원금</dt>
                                    <dd class="u-ta-right" ><em id="agncySubsdAmtTxt">-</em> 원</dd>
                                </dl>
                                <!--  <dl class="c-addition c-addition--type2">
                                <dt>사용 포인트</dt>
                                <dd class="u-ta-right" ><em id="usePointTxt">0</em> P</dd>
                                </dl> -->
                                <c:choose>
                                    <c:when test="${ '01' eq AppformReq.settlWayCd}">
                                        <dl class="c-addition c-addition--type2">
                                            <dt>즉시결제 금액</dt>
                                            <dd class="u-ta-right" ><b id="instAmtTxt">-</b> 원</dd>
                                        </dl>
                                    </c:when>
                                    <c:otherwise>
                                        <dl class="c-addition c-addition--type2">
                                            <dt>할부원금</dt>
                                            <dd class="u-ta-right" ><b id="instAmtTxt">-</b> 원</dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2">
                                            <dt>총할부수수료</dt>
                                            <dd class="u-ta-right" ><b id="totalInstCmsnTxt">-</b> 원</dd>
                                        </dl>
                                    </c:otherwise>
                                </c:choose>

                            </c:if>




                            <hr class="c-hr c-hr--type2">
                            <dl class="c-addition c-addition--type1">
                                <dt>
                                    월 통신요금 <c:if test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_PHONE}">(B)</c:if>
                                </dt>
                                <dd class="u-ta-right"><b id="payMnthChargeAmtTxt">-</b> 원</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2">
                                <dt>기본요금</dt>
                                <dd class="u-ta-right" ><b id="baseAmt">-</b> 원</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2">
                                <dt>
                                    <a data-tpbox="#tp1">기본할인 <!-- [2021-11-24] 'tooltop' > 'tooltip' -->
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </a>
                                </dt>
                                <dd class="u-ta-right" ><b id="dcAmtTxt">-</b> 원</dd>
                            </dl>
                            <div class="c-tooltip-box" id="tp1">
                                <div class="c-tooltip-box__title">기본할인</div>
                                <div class="c-tooltip-box__content">약정 가입시 기본적으로 제공되는 요금제 할인금액입니다.</div>
                                <button class="c-tooltip-box__close" data-tpbox-close>
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>
                            <dl class="c-addition c-addition--type2">
                                <dt>
                                    <a data-tpbox="#tp2">요금할인 <!-- [2021-11-24] 'tooltop' > 'tooltip' -->
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </a>
                                </dt>
                                <dd class="u-ta-right" ><b id="addDcAmt">-</b> 원</dd>
                            </dl>
                            <div class="c-tooltip-box" id="tp2">
                                <div class="c-tooltip-box__title">요금할인</div>
                                <div class="c-tooltip-box__content">약정 - 할인 선택시 제공되는 요금할인 혜택입니다.</div>
                                <button class="c-tooltip-box__close" data-tpbox-close>
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>
                            <dl class="c-addition c-addition--type2">
                                <dt>프로모션 할인</dt>
                                <dd class="u-ta-right">
                                    <b> <em id="promotionDcAmtTxt">-</em></b> 원
                                </dd>
                            </dl>

                            <hr class="c-hr c-hr--type2">

                            <dl class="c-addition c-addition--type1">
                                <dt>가입비 및 유심비 별도</dt>
                                <!-- <dd class="u-ta-right">
                                    <b >0</b> 원
                                </dd> -->
                            </dl>
                            <dl class="c-addition c-addition--type2">
                                <dt>가입비(3개월 분납)</dt>
                                <dd class="u-ta-right">
                                    <em id="joinPriceTxt">- 원</em>
                                </dd>
                            </dl>


                            <c:choose>
                                <c:when test="${AppformReq.usimKindsCd eq '09'}">
                                    <dl class="c-addition c-addition--type2">
                                        <dt>eSIM(최초 1회)</dt>
                                        <dd class="u-ta-right" id="usimPriceTxt"><!-- 6,600 원 --></dd>
                                    </dl>
                                </c:when>
                                <c:when test="${AppformReq.onOffType ne '7'}">
                                    <dl class="c-addition c-addition--type2">
                                        <dt>USIM(최초 1회)</dt>
                                        <dd class="u-ta-right" id="usimPriceTxt"><!-- 6,600 원 --></dd>
                                    </dl>
                                </c:when>
                            </c:choose>



                            <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM}">
                                <dl class="c-addition c-addition--type2">
                                    <dt id="move_01" >번호이동 수수료</dt>
                                    <dd id="move_02" class="u-ta-right">${nmcp:getFtranPrice()} 원</dd>
                                </dl>
                            </c:if>

                            <hr class="c-hr c-hr--type2">

                            <dl class="c-addition c-addition--type1 c-addition--sum">
                                <dt>
                                    <c:choose>
                                        <c:when test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_PHONE}">
                                            <b>월 납부금액(A+B)</b>
                                        </c:when>
                                        <c:otherwise>
                                            <b>월 납부금액</b>
                                        </c:otherwise>
                                    </c:choose>
                                    (부가세 포함)
                                </dt>
                                <dd class="u-ta-right"><b id="paySumMnthTxt2">0</b> 원</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2">
                                <dt id="bottomTitle"></dt>
                            </dl>
                            <hr class="c-hr c-hr--type2">

                            <c:if test="${AppformReq.onOffType ne '7' && AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM && 'Y' eq AppformReq.sesplsYn}" >
                                <dl class="c-addition c-addition--type1 c-addition--sum">
                                    <dt>단말기 결제금액 (부가세 포함)</dt>
                                    <dd class="u-ta-right">
                                        <b class="_paymentHndsetPrice">-</b>원
                                    </dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt class="_bottomTitle"><!-- 갤럭시 A32 / 64GB / 화이트 --></dt>
                                </dl>
                                <hr class="c-hr c-hr--type2">
                            </c:if>

                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <c:if test="${AppformReq.onOffType ne '7'}">
                                    <li class="c-text-list__item u-co-gray">
                                        <c:choose>
                                            <c:when test="${AppformReq.usimKindsCd eq '09'}">eSIM비는 다운로드에 상관없이 최초 개통 시 1회 발생되며, 프로파일 삭제 등으로 재 다운로드 시 추가 발생합니다.</c:when>
                                            <c:otherwise>kt M mobile에서 제공한 유심으로 개통을 진행하지 않으실 경우, 유심비용이 청구될 수 있습니다.</c:otherwise>
                                        </c:choose>
                                    </li>
                                </c:if>
                                <li class="c-text-list__item u-co-gray">월 납부금액은 부가서비스 등의 사용에 따라 추가금액이 합산되어 청구될 수 있습니다.</li>
                                <li class="c-text-list__item u-co-gray">월 납부금액은 부가세가 포함된 금액입니다.</li>
                                <li class="c-text-list__item u-co-gray">타사향(SK, LG U+ 등) 단말은 일부 서비스(MMS,영상통화, 교통카드 기능 등) 이용이 제한될 수 있습니다.</li>
                            </ul>



                        </div>


                    </div>

                    <div class="c-modal__footer fixed-area">
                        <div class="c-button-wrap u-mt--0">
                            <button class="c-button c-button--full c-button--red" id="btnNext2">다음</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>




        <!-- 부가서비스 선택 -->
        <div class="c-modal" id="additionDialog" role="dialog" tabindex="-1" aria-describedby="#additionTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="additionTitle">부가서비스 선택</h1>
                        <button class="c-button _btnClose " data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body overflow-x-hidden">
                        <h3 class="c-heading c-heading--type3">무료 부가서비스</h3>
                        <div class="c-accordion c-accordion--type3">
                            <ul class="c-accordion__box" id="ulAdditionListFree">

                            </ul>
                        </div>

                        <h3 class="c-heading c-heading--type3">유료 부가서비스</h3>
                        <div class="c-accordion c-accordion--type3">
                            <ul class="c-accordion__box" id="ulAdditionListPrice">

                            </ul>
                        </div>

                        <div class="c-accordion c-accordion--type3" style="display:none">
                            <ul class="c-accordion__box" id="ulAdditionListBase">

                            </ul>
                        </div>

                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button  class="c-button c-button--full c-button--primary"  id="btnSetAddition"   data-dialog-close >확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!--  안심보험 -->
        <div class="c-modal" id="insrProdDialog" role="dialog" tabindex="-1" aria-describedby="#insrProdTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="insrProdTitle">휴대폰안심보험 서비스</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <h2 class="c-heading c-heading--type2">휴대폰안심보험 서비스 신청</h2>
                        <div class="c-box c-box--type2 u-flex-center">나에게 맞는 안심보험을 선택하세요</div>
                        <ul class="c-card-list" id="ulInsrProdList">
                            <!-- <li class="c-card c-card--type3">
                              <div class="c-card__box">
                                <div class="c-chk-wrap">
                                  <input class="c-checkbox c-checkbox--type4" id="insrProdCD_1" type="radio" name="insrProdCD">
                                  <label class="c-card__label" for="insrProdCD_1">
                                    <span class="c-hidden">선택</span>
                                  </label>
                                </div>
                                <div class="c-card__title">
                                  <b>안드로이드 플래티넘</b>
                                  <p class="c-card__sub">
                                    <span>자급제 신품</span>
                                    <span>중고 단말기 보험</span>
                                  </p>
                                </div>
                                <div class="c-card__price-box">
                                  <span class="ico-type">
                                    <img src="/resources/images/mobile/common/ico_android.png" alt="">
                                  </span>
                                  <span class="c-text c-text--type3 u-ml--auto">월 기본료</span>
                                  <span class="u-ml--4">
                                    <b>4,900 원</b>
                                  </span>
                                </div>
                                <div class="c-card__info c-box c-box--type1">
                                  <dl class="info-dl">
                                    <dt>설명</dt>
                                    <dd>안드로이드(삼성 등) 단말기 구매고객 전용</dd>
                                    <dt>가입 가능 기준</dt>
                                    <dd>구매가 100 만 원 초과 시</dd>
                                    <dt>보상범위<span class="c-text--type6 c-text-label c-text-label--orange u-ml--4">중고단말 최고가 보장</span>
                                    </dt>
                                    <dd>최대 120 만 원 보장</dd>
                                  </dl>
                                  <div class="u-row-box u-mt--20">
                                    <span class="c-text-label c-text-label--gray">분실</span>
                                    <span class="c-text-label c-text-label--gray">도난</span>
                                    <span class="c-text-label c-text-label--gray">화재</span>
                                    <span class="c-text-label c-text-label--gray">파손</span>
                                    <span class="c-text-label c-text-label--gray">침수</span>
                                  </div>
                                </div>
                              </div>
                            </li> -->
                        </ul>
                        <div class="c-form">
                            <div class="c-check-wrap c-check-wrap--column">
                                <div class="c-chk-wrap">
                                    <input class="c-checkbox c-checkbox--button" id="insrProdCD_0" type="radio" name="insrProdCD" checked="checked">
                                    <label class="c-label" for="insrProdCD_0">미가입</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button id=btnInsrProdConfirm class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>







        <!--  계좌점유  Layer-->
        <div class="c-modal" id="bankAutDialog" role="dialog" tabindex="-1" aria-describedby="#bankAutTitle">

            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="bankAutTitle">계좌인증</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-indicator">
                        <span style="width: 33.33%"></span>
                    </div>

                    <div id="divBankAut1" class="c-modal__body">
                        <h3 class="c-heading c-heading--type1">계좌정보 입력</h3>
                        <p class="c-text c-text--type2 u-co-gray"> 본인인증에 필요한 정보를 입력해 주세요.
                            PASS 인증을 받은 실명과 계좌 명의자가 동일해야
                            인증이 가능합니다.</p>
                        <div class="c-form u-mt--0">
                            <span class="c-form__title">은행/증권사</span>
                            <div class="c-form__group" role="group" aria-labelledby="inpBankNum">
                                <div class="c-form__select">
                                    <select id="reqBankAut" class="c-select">
                                        <option value="">선택</option>
                                        <c:forEach items="${bankList}" var="itemBank" >
                                            <option value="${itemBank.dtlCd }">${itemBank.dtlCdNm }</option>
                                        </c:forEach>
                                    </select>
                                    <label class="c-form__label">은행명</label>
                                </div>
                                <div class="c-form__input">
                                    <input class="c-input onlyNum" id="reqAccountAut" type="tel" name="" pattern="[0-9]*" placeholder="'-'없이 입력">
                                    <label class="c-form__label" for="reqAccountAut">계좌번호</label>
                                </div>
                                <div class="c-form__input has-value">
                                    <input class="c-input" id="cstmrNameTemp" type="text" placeholder=""  readonly >
                                    <label class="c-form__label" for="cstmrNameTemp">이름</label>
                                </div>
                            </div>
                        </div><!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
                        <div class="c-button-wrap">
                            <!--[2021-12-02] 유틸클래스명 변경 -->
                            <button class="c-button c-button--gray c-button--lg u-width--120" data-dialog-close>취소</button>
                            <button id="btnAutAccount" class="c-button c-button--full c-button--primary">인증요청</button>
                        </div>
                    </div>


                    <div id="divBankAut2" style="display:none;" class="c-modal__body">
                        <h3 class="c-heading c-heading--type1">인증번호 입력</h3>
                        <p class="c-text c-text--type2 u-co-gray"> 입력하신 계좌로 1원을 보내드렸습니다.
                            <br>계좌 거래내역에서 1원의 입금자로 표시된
                            <br>숫자 6자리를 입력해 주시기 바랍니다.
                        </p>
                        <div class="c-form u-mt--32">
                            <div class="c-box u-mb--24">
                                <img class="center-img" src="/resources/images/mobile/content/img_account_certification.png" alt="">
                            </div>
                            <div class="c-form__input">
                                <input class="c-input onlyNum" id="textOpt"  maxlength="6" type="tel" name="" pattern="[0-9]*" placeholder="입금자 명에 표시된 숫자 6자리 입력">
                                <label class="c-form__label" for="textOpt">인증번호</label>
                            </div>
                        </div><!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
                        <div class="c-button-wrap">
                            <!--[2021-12-02] 유틸클래스명 변경 -->
                            <button class="c-button c-button--gray c-button--lg u-width--120" data-dialog-close>취소</button>
                            <button id="btnCheckAccountOtpConfirm"  class="c-button c-button--full c-button--primary">인증하기</button>
                        </div>
                    </div>

                    <div id="divBankAut3" style="display:none;" class="c-modal__body">
                        <h3 class="c-heading c-heading--type1">인증완료</h3>
                        <p class="c-text c-text--type2 u-co-gray"> 계좌 인증이 완료되었습니다.
                            <br>확인 버튼을 누른 뒤 신청서를
                            <br>계속 작성해주시기 바랍니다.
                        </p><!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--primary" data-dialog-close >확인</button>
                        </div>
                    </div>


                </div>
            </div>


        </div>

        <!-- 셀프 개통 Layer-->
        <div class="c-modal" id="simpleDialog" role="dialog" tabindex="-1" aria-describedby="#simpleTitle">

            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title _title" >오류 TITLE</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-indicator">
                        <span style="width: 33.33%"></span>
                    </div>

                    <div class="c-modal__body">
                        <h3 class="c-heading c-heading--type1"></h3>
                        <p class="c-text c-text--type2 u-co-gray _detail">
                            ※ 신규가입은 명의당 30일이내 1회선만 가입 가능합니다.<br/>
                            추가 가입은 최근 가입하신 KT M모바일 회선 가입일을 기준으로 30일 경과된 시점에 신청 부탁드립니다.
                        </p>

                        <div id="divChangeButton" class="c-button-wrap _simpleDialogButton">
                            <button class="c-button c-button--gray c-button--lg u-width--120 _btnCheck" data-dialog-close>확인</button>


                            <button id="btnOnline" class="c-button c-button--full c-button--primary">해피콜 신청서 작성</button>
                        </div>

                        <div id="divChangeUsimButton" class="c-button-wrap _simpleDialogButton">
                            <button class="c-button c-button--gray c-button--lg u-width--120 _btnCheck" data-dialog-close>확인</button>
                            <button onclick="location.href='/m/appForm/reqSelfDlvry.do'"  class="c-button c-button--full c-button--primary">유심구매</button>
                        </div>

                        <div id="divDefaultButton" class="c-button-wrap _simpleDialogButton">
                            <button class="c-button c-button--full c-button--primary _btnCheck" data-dialog-close >확인</button>
                        </div>

                        <div id="divReqPreButton" class="c-button-wrap _simpleDialogButton">
                            <button id="reqPreButton" class="c-button c-button--full c-button--primary"  >개통사전체크확인</button>
                        </div>
                        <div id="divCheckCaution" class="c-button-wrap _simpleDialogButton" style="flex-direction: column;">
                            <div class="c-agree__item">
                                <input class="c-checkbox" id="cautionFlag" type="checkbox">
                                <label class="c-label" for="cautionFlag">위 사항을 읽고, 이해하였습니다.</label>
                            </div>
                            <button class="c-button c-button--full c-button--primary _btnCheck" id="cautionCheck">확인</button>
                        </div>
                    </div>


                </div>
            </div>


        </div>




        <!--사전동의 안내 Layer-->
        <div class="c-modal" id="move_number_pop" role="dialog" tabindex="-1" aria-describedby="#move_number_pop_title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="move_number_pop_title">번호이동 사전동의 안내</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <p class="c-text c-text--type2 u-co-gray-10 u-mt--12">번호이동을 위해서는 현재 사용 중인 통신사의 인증 절차가 필요합니다.</p>
                        <p class="c-bullet c-bullet--dot u-co-gray u-fs-14">사전 동의 요청 시 문자가 발송되며, 문자가 오지 않을 경우 통신사 ARS로 전화하셔서 동의를 진행해 주시기 바랍니다.</p>
                        <div class="c-tabs c-tabs--type3">
                            <div class="c-tabs__list c-expand" data-tab-list>
                                <button class="c-tabs__button" data-tab-header="#tabD1-panel">문자인증</button>
                                <button class="c-tabs__button" data-tab-header="#tabD2-panel">ARS인증</button>
                            </div>
                            <div class="c-tabs__panel" id="tabD1-panel">
                                <div class="req-agree">
                                    <div class="req-agree__item">
                                        <div class="req-agree__icon">
                                            <i class="c-icon c-icon--click" aria-hidden="true"></i>
                                        </div>
                                        <span class="req-agree__text">1. ‘사전동의 요청’ 버튼 클릭</span>
                                    </div>
                                    <div class="req-agree__item">
                                        <div class="req-agree__icon">
                                            <i class="c-icon c-icon--url" aria-hidden="true"></i>
                                        </div>
                                        <span class="req-agree__text">2. 수신한 문자 내 URL에 접속하여 동의</span>
                                    </div>
                                    <div class="req-agree__item">
                                        <div class="req-agree__icon">
                                            <i class="c-icon c-icon--complete-type2" aria-hidden="true"></i>
                                        </div>
                                        <span class="req-agree__text">3. ‘동의완료’ 버튼을 눌러 다음 단계 이동</span>
                                    </div>
                                </div>
                            </div>
                            <div class="c-tabs__panel" id="tabD2-panel">
                                <div class="req-agree">
                                    <div class="req-agree__item">
                                        <div class="req-agree__icon">
                                            <i class="c-icon c-icon--click" aria-hidden="true"></i>
                                        </div>
                                        <span class="req-agree__text">1. ‘사전동의 요청’ 버튼 클릭</span>
                                    </div>
                                    <div class="req-agree__item">
                                        <div class="req-agree__icon">
                                            <i class="c-icon c-icon--ars-type2" aria-hidden="true"></i>
                                        </div>
                                        <span class="req-agree__text">2. 이전 통신사 ARS에 전화걸어 동의 진행</span>
                                    </div>
                                    <div class="req-agree__item">
                                        <div class="req-agree__icon">
                                            <i class="c-icon c-icon--complete-type2" aria-hidden="true"></i>
                                        </div>
                                        <span class="req-agree__text">3. ‘동의완료’ 버튼을 눌러 다음 단계 이동</span>
                                    </div>
                                </div>
                                <h3 class="c-heading c-heading--type2 u-fs-14 u-mb--16 u-mt--32">통신사별 ARS번호</h3>
                                <div class="c-table">
                                    <table>
                                        <caption>통신사, 사전동의 ARS 번호 표</caption>
                                        <colgroup>
                                            <col style="width: 50">
                                            <col style="width: 50">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th scope="col">통신사</th>
                                            <th scope="col">ARS번호</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td scope="row">KT/KT 알뜰폰(MVNO)</td>
                                            <td>1588-2935</td>
                                        </tr>
                                        <tr>
                                            <td scope="row">SKT</td>
                                            <td>1566-1509</td>
                                        </tr>
                                        <tr>
                                            <td scope="row">SKT 알뜰폰(MVNO)</td>
                                            <td>1599-0133</td>
                                        </tr>
                                        <tr>
                                            <td scope="row">LGU+/LGU 알뜰폰(MVNO)</td>
                                            <td>1544-3553</td>
                                        </tr>
                                        <tr>
                                            <td scope="row">CJ헬로모바일</td>
                                            <td>070-7336-7766</td>
                                        </tr>
                                        <tr>
                                            <td scope="row">세종텔레콤(스노우맨)</td>
                                            <td>1688-9345</td>
                                        </tr>
                                        <tr>
                                            <td scope="row">한국케이블텔레콤_KCT</td>
                                            <td>1877-9120</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- 번호예약 Layer  layerSearchNum -->
        <div class="c-modal" id="searchNumDialog" role="dialog" tabindex="-1" aria-describedby="#searchNumTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="searchNumTitle">예약가능 번호 리스트</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="c-form">
                            <div class="c-form__title">전화번호 선택</div>
                            <div class="c-check-wrap c-check-wrap--column _list">
                                <!-- <div class="c-chk-wrap">
                                  <input class="c-radio" id="radNumber1" type="radio" name="radNumber" >
                                  <label class="c-label" for="radNumber1">010-1111-1234</label>
                                </div>    -->
                            </div>
                        </div>
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
                            <button id="btnConfirmNum" class="c-button c-button--full c-button--primary">확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>




        <!-- 이탈방지 -->
        <button class="c-button c-button--full c-button--white c-hidden"  data-dialog-trigger="#waitingConnectDialog" id="chkLayerPopBtn">이탈방지 레이어 호출</button>
        <div class="c-modal" id="waitingConnectDialog" role="dialog" tabindex="-1" aria-describedby="#waitingConnectTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body u-ta-center">
                        <h3 class="c-heading c-heading--type3">
                            앗 ! 신청서 작성이 아직 남아있어요. <br>화면에서 나가시겠습니까?
                        </h3>
                        <p class="c-text c-text--type3 u-co-gray">
                            화면 이탈 시 입력 내용은 '신청조회' 메뉴에서 <br>확인 후 이어할 수 있습니다. <br>(입력 정보는 7일간 보관됩니다.)
                        </p>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--gray" onclick="leaveUrlGo();" data-dialog-close>나가기</button>
                            <button class="c-button c-button--full c-button--primary" data-dialog-close >계속하기</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="swiper-outside-banner" id="swiperOutsideBanner">
                <!--[2022-02-10] 배너 통 이미지 적용-->
                <div class="swiper-wrapper u-mb--24 c-box--sticky">
                    <c:set var="O101List" value="${nmcp:getBannerList('O101')}" />
                    <c:forEach var="item" items="${O101List}">
                        <c:choose>
                            <c:when test="${item.linkTarget eq 'Y' and item.linkUrlAdr ne ''}">
                                <div class="swiper-slide" href="javascript:void(0);" onclick="javascript:insertBannAccess('${item.bannSeq}','${item.bannCtg}');location.href='${item.linkUrlAdr}';">
                                    <button class="swiper-event-banner__button">
                                        <div class="swiper-event-banner__item">
                                            <img src="${item.mobileBannImgNm}" alt="${item.bannNm}">
                                        </div>
                                    </button>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="swiper-slide">
                                    <button class="swiper-event-banner__button">
                                        <div class="swiper-event-banner__item">
                                            <img src="${item.mobileBannImgNm}" alt="${item.bannNm}">
                                        </div>
                                    </button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
                <c:if test="${fn:length(O101List) gt 0}">
                    <div class="swiper-pagination"></div>
                </c:if>
            </div>


        </div>

        <!--이동통신 단말기 보험 상품 설명서-->
        <div class="c-modal" id="productManualModal" role="dialog" tabindex="-1" aria-describedby="#productManualModalTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="productManualModalTitle">이동통신 단말기 보험 상품 설명서</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="content-wrap">
                            <p class="c-text c-text--type3 u-mt--20"> 본 상품은 주식회사 케이티엠모바일의 휴대폰 안심 서비스에 가입하신 고객을 위해, 주식회사 케이티엠모바일(보험계약자)이 휴대폰 안심 서비스 고객들을 피보험자로 하여 DB손해보험(주)에 가입한 이동통신단말기보험에 대한 상품설명서 입니다.</p>
                            <hr class="c-hr c-hr--type2">
                            <h3 class="c-heading c-heading--type2 u-mt--0">USIM형 자급제 전용(안드로이드/아이폰)</h3>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">1. 가입 가능 기간 : 신규 단말기 구매일 기준 45일 이내(구매영수증 발송 필수)</h4>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">2. 가입대상 단말기 종류 및 가입상품 명칭</h4>
                            <div class="c-table u-mt--12">
                                <table>
                                    <caption>안심서비스 가입 정보</caption>
                                    <colgroup>
                                        <col style="width: 50%">
                                        <col style="width: 50%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th scope="col">가입대상 단말기 유형</th>
                                        <th scope="col">가입플랜 명칭</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td id="androidPhone" rowspan="5" scope="row">안드로이드 스마트폰</td>
                                        <td headers="androidPhone">폴드 180</td>
                                    </tr>
                                    <tr>
                                        <td headers="androidPhone">분실파손 150</td>
                                    </tr>
                                    <tr>
                                        <td headers="androidPhone">분실파손 100</td>
                                    </tr>
                                    <tr>
                                        <td headers="androidPhone">분실파손 70</td>
                                    </tr>
                                    <tr>
                                        <td headers="androidPhone">파손 50</td>
                                    </tr>
                                    <tr>
                                        <td id="iPhone" rowspan="3" scope="row">아이폰(전기종)</td>
                                        <td headers="iPhone">i-분실파손 150</td>
                                    </tr>
                                    <tr>
                                        <td headers="iPhone">i-분실파손 90</td>
                                    </tr>
                                    <tr>
                                        <td headers="iPhone">i-파손 50</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">3. 보상범위</h4>
                            <div class="c-table u-mt--12">
                                <table>
                                    <caption>보상범위</caption>
                                    <colgroup>
                                        <col style="width: 50%">
                                        <col style="width: 50%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th scope="col">가입플랜 명칭</th>
                                        <th scope="col">보상범위</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td headers="rewardRangeA">폴드 180</td>
                                        <td rowspan="2" id="rewardRangeA">분실, 도난, 파손(화재, 침수, 완파) 및 피싱해킹</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeA">분실파손 150</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeB">분실파손 100</td>
                                        <td rowspan="2" id="rewardRangeB">분실, 도난, 파손(화재, 침수, 완파)</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeB">분실파손 70</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeC">분실파손 50</td>
                                        <td id="rewardRangeC">파손(화재, 침수, 완파)</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeD">i-분실파손 150</td>
                                        <td id="rewardRangeD">분실, 도난, 파손(화재, 침수, 완파) 및 피싱해킹</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeE">i-분실파손 90</td>
                                        <td id="rewardRangeE">분실, 도난, 파손(화재, 침수, 완파)</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeF">i-파손 50</td>
                                        <td id="rewardRangeF">파손(화재, 침수, 완파)</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">4. 최대 가입금액(보험 가입 금액) 및 월 서비스 이용료</h4>
                            <div class="c-table u-mt--12">
                                <table>
                                    <caption>최대 가입금액(보험 가입 금액) 및 월 서비스 이용료</caption>
                                    <colgroup>
                                        <col style="width: 33.33%">
                                        <col style="width: 33.33%">
                                        <col style="width: 33.33%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th scope="col">가입플랜 명칭</th>
                                        <th scope="col">최대 가입금액</th>
                                        <th scope="col"> 월 서비스 이용료
                                            <br>(부가세 없음)
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>폴드 180</td>
                                        <td>180만원</td>
                                        <td>7,700원</td>
                                    </tr>
                                    <tr>
                                        <td>분실파손 150</td>
                                        <td>150만원</td>
                                        <td>4,000원</td>
                                    </tr>
                                    <tr>
                                        <td>분실파손 100</td>
                                        <td>100만원</td>
                                        <td>3,600원</td>
                                    </tr>
                                    <tr>
                                        <td>분실파손 70</td>
                                        <td>70만원</td>
                                        <td>3,300원</td>
                                    </tr>
                                    <tr>
                                        <td>파손 50</td>
                                        <td>50만원</td>
                                        <td>2,800원</td>
                                    </tr>
                                    <tr>
                                        <td>i-분실파손 150</td>
                                        <td>150만원</td>
                                        <td>4,000원</td>
                                    </tr>
                                    <tr>
                                        <td>i-분실파손 90</td>
                                        <td>90만원</td>
                                        <td>3,300원</td>
                                    </tr>
                                    <tr>
                                        <td>i-파손 50</td>
                                        <td>50만원</td>
                                        <td>2,800원</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">5. 자기부담금</h4>
                            <div class="c-table u-mt--12">
                                <table>
                                    <caption>보상범위</caption>
                                    <colgroup>
                                        <col style="width: 50%">
                                        <col style="width: 50%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th scope="col">가입플랜 명칭</th>
                                        <th scope="col">자기부담금</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td hearders="selfBurdenA">폴드 180</td>
                                        <td rowspan="8" id="selfBurdenA"> 손해액의 30%
                                            <br>(최소 3만원)
                                        </td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA">분실파손 150</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA">분실파손 100</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA">분실파손 70</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA">파손 50</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA">i-분실파손 150</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA">i-분실파손 90</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA">i-파손 50</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">6. 보상한도 액</h4>
                            <p class="c-bullet c-bullet--hyphen">가입시점의 휴대폰 출고가와 최대 가입금액 중 낮은 금액에서 자기 부담금을 차감한 금액</p>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">7. 보험기간</h4>
                            <p class="c-bullet c-bullet--hyphen">최대 가입기간 : 36개월</p>
                            <ul class="c-text-list c-bullet c-bullet--fyr">
                                <li class="c-text-list__item">보험개시일로부터 36개월 경과시점 또는 이동통신사 포괄계약기간의 만료일 중 먼저 도래하는 일시까지(포괄계약기간이 갱신될 경우 해당되지 않음)</li>
                                <li class="c-text-list__item">보험개시일은 서비스 가입일로부터 익일 00시 이후로 합니다.
                                    <br/><span class="u-co-red">(단, 본 상품 가입 익일 0시 이후 통화내역 또는 모바일인증이 있어야 보험사의 책임(보상효력)이 개시됩니다.)</span>
                                </li>
                            </ul>
                            <h3 class="c-heading c-heading--type2">단말결합 및 USIM형(단말 전 기종, 중고단말 포함)</h3>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">1. 가입 가능 기간 : 신규/기변 개통 후 45일 이내</h4>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">2. 가입대상 단말기 종류 및 가입상품 명칭</h4>
                            <div class="c-table u-mt--12">
                                <table>
                                    <caption>안심서비스 가입 정보</caption>
                                    <colgroup>
                                        <col style="width: 50%">
                                        <col style="width: 50%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th scope="col">가입대상 단말기 유형</th>
                                        <th scope="col">가입플랜 명칭</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td id="smartPhone" rowspan="5"> 스마트폰
                                            <br>(아이폰 제외)
                                        </td>
                                        <td headers="smartPhone">폴드 180</td>
                                    </tr>
                                    <tr>
                                        <td headers="smartPhone">분실파손 150</td>
                                    </tr>
                                    <tr>
                                        <td headers="smartPhone">분실파손 100</td>
                                    </tr>
                                    <tr>
                                        <td headers="smartPhone">분실파손 70</td>
                                    </tr>
                                    <tr>
                                        <td headers="smartPhone">파손 50</td>
                                    </tr>

                                    <tr>
                                        <td id="allPhone" rowspan="2">단말기 전 기종</td>
                                        <td headers="allPhone">중고 파손 100</td>
                                    </tr>
                                    <tr>
                                        <td headers="allPhone">중고 파손 40</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">3. 보상범위</h4>
                            <div class="c-table u-mt--12">
                                <table>
                                    <caption>보상범위</caption>
                                    <colgroup>
                                        <col style="width: 50%">
                                        <col style="width: 50%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th scope="col">가입플랜 명칭</th>
                                        <th scope="col">보상범위</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td headers="rewardRangeA2">폴드 180</td>
                                        <td rowspan="2" id="rewardRangeA2">분실, 도난, 파손(화재, 침수, 완파) 및 피싱해킹</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeA2">분실파손 150</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeB2">분실파손 100</td>
                                        <td rowspan="2" id="rewardRangeB2">분실, 도난, 파손(화재, 침수, 완파)</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeB2">분실파손 70</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeC2">분실파손 50</td>
                                        <td id="rewardRangeC2">파손(화재, 침수, 완파)</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeD2">중고 파손 100</td>
                                        <td rowspan="2" id="rewardRangeD2">파손(화재, 침수, 완파)</td>
                                    </tr>
                                    <tr>
                                        <td headers="rewardRangeD2">중고 파손 40</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">4. 최대 가입금액(보험 가입 금액) 및 월 서비스 이용료</h4>
                            <p class="c-bullet c-bullet--hyphen">가입시점의 가입 단말기 출고가와 아래 상품별 금액 중 낮은 금액</p>
                            <div class="c-table u-mt--12">
                                <table>
                                    <caption>최대 가입금액(보험 가입 금액) 및 월 서비스 이용료</caption>
                                    <colgroup>
                                        <col style="width: 33.33%">
                                        <col style="width: 33.33%">
                                        <col style="width: 33.33%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th scope="col">가입플랜 명칭</th>
                                        <th scope="col">최대 가입금액</th>
                                        <th scope="col"> 월 서비스 이용료
                                            <br>(부가세 없음)
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>폴드 180</td>
                                        <td>180만원</td>
                                        <td>7,700원</td>
                                    </tr>
                                    <tr>
                                        <td>분실파손 150</td>
                                        <td>150만원</td>
                                        <td>4,000원</td>
                                    </tr>
                                    <tr>
                                        <td>분실파손 100</td>
                                        <td>100만원</td>
                                        <td>3,600원</td>
                                    </tr>
                                    <tr>
                                        <td>분실파손 70</td>
                                        <td>70만원</td>
                                        <td>3,300원</td>
                                    </tr>
                                    <tr>
                                        <td>파손 50</td>
                                        <td>50만원</td>
                                        <td>2,800원</td>
                                    </tr>
                                    <tr>
                                        <td>중고 파손 100</td>
                                        <td>100만원</td>
                                        <td>6,000원</td>
                                    </tr>
                                    <tr>
                                        <td>중고 파손 40</td>
                                        <td>40만원</td>
                                        <td>3,700원</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">5. 자기부담금</h4>
                            <div class="c-table u-mt--12">
                                <table>
                                    <caption>보상범위</caption>
                                    <colgroup>
                                        <col style="width: 50%">
                                        <col style="width: 50%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th scope="col">가입플랜 명칭</th>
                                        <th scope="col">자기부담금</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td hearders="selfBurdenA2">폴드 180</td>
                                        <td rowspan="7" id="selfBurdenA2"> 손해액의 30%
                                            <br>(최소 3만원)
                                        </td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA2">분실파손 150</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA2">분실파손 100</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA2">분실파손 70</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA2">파손 50</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA2">중고 파손 100</td>
                                    </tr>
                                    <tr>
                                        <td hearders="selfBurdenA2">중고 파손 40</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">6. 보상한도 액</h4>
                            <p class="c-bullet c-bullet--hyphen">가입시점의 휴대폰 출고가와 최대 가입금액 중 낮은 금액에서 자기 부담금을 차감한 금액</p>
                            <h4 class="c-heading c-heading--type6 u-fw--regular">7. 보험기간</h4>
                            <p class="c-bullet c-bullet--hyphen">최대 가입기간 : 36개월</p>
                            <ul class="c-text-list c-bullet c-bullet--fyr">
                                <li class="c-text-list__item">보험개시일로부터 36개월 경과시점 또는 이동통신사 포괄계약기간의 만료일 중 먼저 도래하는 일시까지(포괄계약기간이 갱신될 경우 해당되지 않음)</li>
                                <li class="c-text-list__item">보험개시일은 서비스 가입일로부터 익일 00시 이후로 합니다.
                                    <br/><span class="u-co-red">(단, 본 상품 가입 익일 0시 이후 통화내역 또는 모바일인증이 있어야 보험사의 책임(보상효력)이 개시됩니다.)</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 자급제 보상 서비스 상품 설명서 (모달) -->
        <div class="c-modal" id="rwdManualModal" role="dialog" tabindex="-1" aria-describedby="#rwdManualModalTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="rwdManualModalTitle">자급제 보상 서비스 상품 설명서</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <p class="c-text c-text u-fs-17 u-co-gray u-mt--15">
                            kt M mobile “자급제 보상 서비스” 는 위니아에이드가 제공하는 서비스입니다.
                            최신 자급제 스마트폰을 구매한 고객이 kt M mobile 이동전화서비스 가입 및 본 서비스 가입 18개월 후 새 단말기를 구매하여 kt M mobile 이동전화를 유지하고
                            보상기간 중 사용하던 기존 단말[kt M mobile 자급제보상서비스 가입시 등록한 스마트폰]을 반납하는 경우, 위니아에이드가 개월차별 책정된 보상률(최대50%)에 따라 중고폰 매입가격을
                            현금으로 보장해 드립니다.
                        </p>
                        <p class="c-text c-text u-fs-15 u-mt--32">1. 가입 가능 기간 : 스마트폰 구매 후 90일 이내 가입 가능 (단말 구매 영수증 기준)</p>
                        <p class="c-text c-text u-fs-15 u-mt--24">2. 가입상품 명칭 및 월 서비스 이용료</p>
                        <div class="c-table u-mt--16">
                            <table>
                                <caption>자급제 보상 서비스 이용료 정보</caption>
                                <colgroup>
                                    <col style="width: 20%">
                                    <col style="width: 20%">
                                    <col style="width: 20%">
                                    <col style="width: 20%">
                                    <col style="width: 20%">
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>가입단말</th>
                                    <th>약정기간</th>
                                    <th>최대보장율</th>
                                    <th>월이용료(vat포함)</th>
                                    <th>총 납입액</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>아이폰형</td>
                                    <td>18개월</td>
                                    <td>50%</td>
                                    <td>월 6,600원</td>
                                    <td>118,800원</td>
                                </tr>
                                <tr>
                                    <td>안드로이드형</td>
                                    <td>18개월</td>
                                    <td>45%</td>
                                    <td>월 8,800원</td>
                                    <td>158,400원</td>
                                </tr>
                                <tr>
                                    <td>폴더블폰형</td>
                                    <td>18개월</td>
                                    <td>45%</td>
                                    <td>월 12,650원</td>
                                    <td>227,700원</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <p class="c-text c-text u-fs-15 u-mt--32">3. 중고폰 매입가격 보장금액: ‘구매가’ x ‘개월 차’ 에 따라 책정된 보장율 (* 보장율은 개월차별로 경감 됨)</p>
                        <div class="c-table u-mt--16">
                            <table>
                                <caption>자급제 보상 서비스 보장기간</caption>
                                <colgroup>
                                    <col style="width: 15%">
                                    <col style="width: 15%">
                                    <col style="width: 15%">
                                    <col style="width: 15%">
                                    <col style="width: 15%">
                                    <col style="width: 25%">
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="2">개월차</th>
                                    <th>19개월차</th>
                                    <th>20개월차</th>
                                    <th>21개월차</th>
                                    <th>22개월차</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td rowspan="3">보장율</td>
                                    <td>아이폰형</td>
                                    <td>50%</td>
                                    <td>47%</td>
                                    <td>44%</td>
                                    <td rowspan="3">권리실행불가</td>
                                </tr>
                                <tr>
                                    <td>안드로이드형</td>
                                    <td>45%</td>
                                    <td>42%</td>
                                    <td>39%</td>
                                </tr>
                                <tr>
                                    <td>폴더블폰형</td>
                                    <td>45%</td>
                                    <td>42%</td>
                                    <td>39%</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <p class="c-text c-text u-fs-15 u-mt--32">4. 보장기간: 18개월 약정</p>
                        <p class="c-bullet c-bullet--dot">서비스 가입 18개월 후 19개월 차 ~ 21개월차(3개월 간)</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- 자급제 보상 서비스 상품 팝업 -->
        <div class="c-modal" id="rwdProdDialog" role="dialog" aria-labelledby="#rwdProdDialogtitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="rwdProdDialogtitle">자급제 보상 서비스 신청/변경</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <h2 class="c-heading c-heading--type2">자급제 보상 서비스 신청</h2>
                        <div class="c-box c-box--type2 u-flex-center">나에게 맞는 보상 서비스를 선택하세요</div>
                        <div class="product">
                            <div class="c-card-col c-card-col--3" id="divRwdProdList">
                                <!-- 자급제 보상 서비스 상품 리스트 -->
                            </div>
                        </div>
                        <div class="c-form">
                            <div class="c-check-wrap c-check-wrap--column">
                                <div class="c-chk-wrap">
                                    <input class="c-checkbox c-checkbox--button" id="rwdProdCD_0" type="radio" name="rwdProdCD" checked="checked">
                                    <label class="c-label" for="rwdProdCD_0">미가입</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button id="btnRwdProdConfirm" class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- imei image upload guide -->
        <div class="c-modal" id="uploadguide-dialog" role="dialog" tabindex="-1" aria-describedby="#uploadguide-title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="uploadguide-title">이미지 업로드 가이드</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="esim-guide-box__pop">
                            <div class="esim-guide__list">
                                <span class="Number-label">1</span> 통화창에 *#06# 입력
                            </div>
                            <img src="/resources/images/mobile/esim/eSIM_guide_01.png" alt="1.통화창에 *#06#입력 가이드 이미지">
                        </div>
                        <div class="esim-guide-space__pop">
                            <i class="c-icon c-icon--triangle_bluegreen-bottom" aria-hidden="true"></i>
                        </div>
                        <div class="esim-guide-box__pop u-mt--0">
                            <div class="esim-guide__list">
                                <span class="Number-label">2</span> 이미지 캡쳐본 업로드
                            </div>
                            <img src="/resources/images/mobile/esim/eSIM_guide_02.png" alt="2.화면 캡쳐 정보의 가이드 이미지">
                        </div>
                        <div class="c-box c-box--type1 u-pa--16 u-mt--24">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray u-mt--0">이미지 업로드 시 각 항목이 자동으로 입력됩니다.</li>
                            </ul>
                        </div>
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 아코디언 서브 스크립트 -->
        <script>
            $(document).on('click', '.c-accordion.c-accordion--nested__sub > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button', function() {
                var $btn = $(this);
                var $panel = $btn.closest('.c-accordion__item').children('.c-accordion__panel');
                var isActive = $btn.hasClass('is-active');

                $btn.toggleClass('is-active', !isActive);
                $btn.attr('aria-expanded', !isActive);

                $panel.stop()[isActive ? 'slideUp' : 'slideDown']();
                $panel.attr('aria-hidden', isActive);
            });
        </script>

        <!-- // imei image upload guide -->

        <!-- imei image register popup -->
        <!-- // imei image register popup -->


     <div class="c-modal" id="regServiceModal" role="dialog" tabindex="-1" aria-describedby="#regServiceModalTitle">
        <div class="c-modal__dialog c-modal__dialog--full" role="document">
          <div class="c-modal__content">
            <div class="c-modal__header">
              <h1 class="c-modal__title" id="regServiceModalTitle">통화중대기 설정/해제 (무료)</h1>
              <button class="c-button" data-dialog-close>
                <i class="c-icon c-icon--close" aria-hidden="true"></i>
                <span class="c-hidden">팝업닫기</span>
              </button>
            </div>
            <div class="c-modal__body">
                <ul class="c-text-list c-bullet c-bullet--dot">
                    <li class="c-text-list__item u-fs-14">통화중대기 서비스를 정상적으로 이용 및 해지하려면 아래의 절차를 진행해주세요.<br /><span class="u-co-red">(절차가 완료되지 않으면 서비스가 정상적으로 반영되지 않습니다)</span></li>
                    <li class="c-text-list__item u-mt--16 u-fs-14">
                            서비스 신청방법
                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                            <li class="c-text-list__item u-co-gray u-fs-14">설정: *+40+통화→ “서비스가 정상적으로 설정되었습니다” → 종료</li>
                            <li class="c-text-list__item u-co-gray u-fs-14">해제: *+400+통화→ “서비스가 정상적으로 해제되었습니다” → 종료</li>
                        </ul>
                    </li>
                </ul>
            </div>
             <div class="c-modal__footer">
                <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
              </div>
          </div>
        </div>
    </div>

    </t:putAttribute>



</t:insertDefinition>