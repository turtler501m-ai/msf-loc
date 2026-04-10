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

<t:insertDefinition name="layoutOutFormGnbDefault">

    <t:putAttribute name="titleAttr">
        <c:choose>
            <c:when test="${ AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM}">
                대리점 유심 가입신청서
            </c:when>
            <c:otherwise>
                대리점 단말 가입신청서
            </c:otherwise>
        </c:choose>
    </t:putAttribute>


    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=21.09.30"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=24.04.01"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?version=21.11.28"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js?version=24.04.01"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js?version=21.12.22"></script>
        <script type="text/javascript" src="/resources/js/portal/appForm/appOutForm.js?version=26.03.03"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="isAuth" name="isAuth"  >
        <input type="hidden" id="isAuthInsr" name="isAuthInsr"  >
        <input type="hidden" id="isAuthRwd" name="isAuthRwd">
        <input type="hidden" id="isPassAuth" name="isPassAuth"  >
        <input type="hidden" id="isCheckAccount" name="isCheckAccount" >
        <input type="hidden" id="isCheckCard" name="isCheckCard" >
        <input type="hidden" id="isNpPreCheck" name="isNpPreCheck" >
        <input type="hidden" id="isCheckmember" name="isCheckmember" >
        <input type="hidden" id="isUsimNumberCheck" name="isUsimNumberCheck" >
        <input type="hidden" id="operType" name="operType" value="${AppformReq.operType}" />
        <input type="hidden" id="onOffTypeInit" name="onOffTypeInit" value="${AppformReq.onOffType}" />
        <input type="hidden" id="prdtNm" name="prdtNm" value="${AppformReq.prdtNm}" />
        <input type="hidden" id="operTypeNm" name="operTypeNm" value="${AppformReq.operTypeNm}" />
        <input type="hidden" id="onOffType" name="onOffType" value="${AppformReq.onOffType}" />
        <input type="hidden" id="prdtSctnCd" name="prdtSctnCd" value="${AppformReq.prdtSctnCd}">
        <input type="hidden" id="reqBuyType" name="reqBuyType" value="${AppformReq.reqBuyType}">
        <input type="hidden" id="rprsPrdtId" name="rprsPrdtId" value="${AppformReq.rprsPrdtId}">
        <input type="hidden" id="prdtId" name="prdtId" value="${AppformReq.prdtId}">
        <input type="hidden" id="serviceType" name="serviceType" value="${AppformReq.serviceType}" >
        <input type="hidden" id="cntpntShopId" name="cntpntShopId" value="${AppformReq.cntpntShopId}" >
        <input type="hidden" id="socCode" name="socCode" value="${AppformReq.socCode}" >
        <input type="hidden" id="prodId" name="prodId" value="${AppformReq.prodId}" >
        <input type="hidden" id="siteReferer" name="siteReferer" value="${AppformReq.siteReferer}" >
        <input type="hidden" id="reqCnt" name="reqCnt" value="" />
        <input type="hidden" id="requestKeyTemp" name="requestKeyTemp" value="${AppformReq.requestKey}">
        <input type="hidden" id="insrProdCdTemp" name="insrProdCdTemp" value="${AppformReq.insrProdCd}">
        <input type="hidden" id="rwdProdCdTemp" name="rwdProdCdTemp" value="${AppformReq.rwdProdCd}">
        <input type="hidden" id="bannerCd" name="bannerCd" value="${AppformReq.bannerCd}" >
        <input type="hidden" id="modelSalePolicyCode" name="modelSalePolicyCode" value="${AppformReq.modelSalePolicyCode}" >
        <input type="hidden" id="sprtTp" name="sprtTp" value="${AppformReq.sprtTp}" >
        <input type="hidden" id="reqModelName" name="reqModelName" value="${AppformReq.reqModelName}" >
        <input type="hidden" id="sntyColorCd" name="sntyColorCd" value="${AppformReq.sntyColorCd}" ><!--단말기모델아이디_색상검색용  -->
        <input type="hidden" id="sntyCapacCd" name="sntyCapacCd" value="${AppformReq.sntyCapacCd}" ><!--단품용량코드  -->
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
        <input type="hidden" id="dataType" name="dataType" value="${AppformReq.dataType}" ><!--요금제구분 LTE,5G-->

        <input type="hidden" id="jehuProdType" name="jehuProdType" value="${jehuProdType}" ><!--제휴처 요금제 타입 -->
        <input type="hidden" id="jehuProdName" name="jehuProdName" value="${jehuProdName}" ><!--제휴처 요금제 이름 -->
        <input type="hidden" id="jehuPartnerType" name="jehuPartnerType" value="${jehuPartnerType}" ><!--제휴사타입 -->
        <input type="hidden" id="jehuPartnerName" name="jehuPartnerName" value="${jehuPartnerName}" ><!--제휴사이름 -->
        <input type="hidden" id="openMarketReferer" name="openMarketReferer" value="${AppformReq.openMarketReferer}" ><!--유입경로 -->

        
        <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv" > <!--안면인증 결과 -->
        <input type="hidden" id="fathTrgYn" name="fathTrgYn" > <!--안면인증 대상여부 -->

        <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />
        <div class="ly-content" id="main-content">

            <div class="ly-page--title">
                <h2 class="c-page--title">
                    <c:choose>
                        <c:when test="${ AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM}">
                            대리점 유심 가입신청서
                        </c:when>
                        <c:otherwise>
                            대리점 단말 가입신청서
                        </c:otherwise>
                    </c:choose>
                </h2>

                <div class="c-stepper-wrap">
                    <div class="c-hidden">휴대폰 요금제 가입하기 총 5단계 중 현재 1단계(본인확인·약관동의)</div>
                    <ul id="ulStepper" class="c-stepper">
                        <li class="c-stepper__item c-stepper__item--active">
                            <span class="c-stepper__num">1</span>
                            <span class="c-stepper__title">본인확인·약관동의</span>
                        </li>
                        <li class="c-stepper__item">
                            <span class="c-stepper__num">2</span>
                        </li>
                        <li class="c-stepper__item">
                            <span class="c-stepper__num">3</span>
                        </li>
                        <li class="c-stepper__item">
                            <span class="c-stepper__num">4</span>
                        </li>
                        <li class="c-stepper__item">
                            <span class="c-stepper__num">5</span>
                        </li>
                    </ul>
                </div>
            </div>

            <!-- STEP1 START -->
            <div id="step1" class="c-row c-row--lg _divStep">
                <div class="c-form-step">
                    <span class="c-form-step__number">1</span>
                    <h3 class="c-form-step__title">본인확인·약관동의</h3>
                </div>

                <div class="c-form-wrap u-mt--24">
                    <div class="c-form-group _fromMove _fromNew" role="group" aira-labelledby="radJoinTypeTitle">
                        <h3 class="c-form__title--type2" id="radJoinTypeTitle">가입유형</h3>
                        <div class="c-checktype-row c-col2 u-mt--0">
                            <input class="c-radio c-radio--button" id="operTypeRadio01" type="radio" name="operTypeRadio" value="${Constants.OPER_TYPE_MOVE_NUM}" txtName="번호이동" <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM }" > checked </c:if> >
                            <label class="c-label" for="operTypeRadio01">번호이동</label>
                            <input class="c-radio c-radio--button" id="operTypeRadio02" type="radio" name="operTypeRadio" value="${Constants.OPER_TYPE_NEW}" txtName="신규가입" <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_NEW }" > checked </c:if>  >
                            <label class="c-label" for="operTypeRadio02">신규가입</label>
                        </div>
                        <div class="c-checktype-row c-col2" style="display:none">
                            <input class="c-radio c-radio--button" id="operTypeRadio03" type="radio" name="operTypeRadio" value="${Constants.OPER_TYPE_CHANGE}" txtName="기기변경" <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_CHANGE }" > checked </c:if> >
                            <label class="c-label" for="operTypeRadio03">기기변경</label>
                            <input class="c-radio c-radio--button" id="operTypeRadio04" type="radio" name="operTypeRadio" value="${Constants.OPER_TYPE_EXCHANGE}" txtName="우수기변" <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_EXCHANGE }" > checked </c:if>  >
                            <label class="c-label" for="operTypeRadio04">우수기변</label>
                        </div>
                    </div>
                    <div class="c-box c-box--type1 u-mt--20 _fromMove _fromNew">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-co-gray u-mt--0" id="pOperTypeDesc">타 통신사에서 사용하던 번호 그대로 엠모바일로 가입하시는 경우 선택해 주세요.</li>
                            <li class="c-text-list__item u-co-gray">
                                <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                                <br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
                            </li>
                        </ul>
                    </div>
                    <c:choose>
                        <c:when test="${AppformReq.reqBuyType ne Constants.REQ_BUY_TYPE_USIM}">
                            <c:if test="${AppformReq.prdtSctnCd eq 'LTE5G'}">
                                <div class="c-form-row c-col2 u-mt--48">
                                    <div class="c-form-field">
                                        <div class="c-checktype-row c-col2">
                                            <input class="c-radio c-radio--button" id="dataTypeRadio01" type="radio" name="dataTypeRadio" value="LTE" txtName="LTE" <c:if test="${AppformReq.dataType eq 'LTE'}"> checked </c:if> >
                                            <label class="c-label" for="dataTypeRadio01">LTE</label>
                                            <input class="c-radio c-radio--button" id="dataTypeRadio02" type="radio" name="dataTypeRadio" value="5G" txtName="5G" <c:if test="${AppformReq.dataType eq '5G'}" > checked </c:if>  >
                                            <label class="c-label" for="dataTypeRadio02">5G</label>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="c-form-row c-col2 u-mt--48">
                                <div class="c-form-field">
                                    <div class="c-form__select u-mt--0">
                                        <select class="c-select" id="modelId">
                                            <c:forEach var="colorInfo" items="${listColorInfo}">
                                                <option value="${colorInfo.modelId}"
                                                        atribValCd1="${colorInfo.reqModelColor}"
                                                        atribValNm1="${colorInfo.reqModelColorNm}"
                                                        atribValNm2="${colorInfo.prdtNm}">${colorInfo.reqModelColorNm}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <label class="c-form__label" for="modelId">색상</label>
                                    </div>
                                </div>
                                <div class="c-form-field">
                                    <div class="c-form__select u-mt--0">
                                        <select class="c-select" id="selSocCode">

                                        </select>
                                        <label class="c-form__label" for="selSocCode">요금제 선택</label>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <h3 class="c-form__title--type2">요금제 선택</h3>
                            <div class="c-form-row c-col2">
                                <div class="c-form-field">
                                    <div class="c-form__select u-mt--0">
                                        <select class="c-select" id="selSocCode">

                                        </select>
                                        <label class="c-form__label" for="selSocCode">요금제 선택</label>
                                    </div>
                                </div>
                                <div class="c-form-field">
                                    <input type="hidden" id="modelId" name="modelId" value="${AppformReq.prdtId}" >
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="c-form-wrap u-mt--48" id="div01">
                    <div class="c-form-group" role="group" aira-labelledby="radServTypeTitle">
                        <h5 class="c-form__title--type2" id="radServTypeTitle">약정/할부</h5>
                        <div class="c-form-row c-col2 u-mt--16">
                            <div class="c-form-field">
                                <div class="c-form__select u-mt--0">
                                    <select class="c-select" id="enggMnthCnt">
                                        <c:forEach var="item" items="${listEnggMnthCnt}">
                                            <option value="${item.enggMnthCnt }" <c:if test="${AppformReq.enggMnthCnt eq item.enggMnthCnt }" > selected </c:if> >${item.enggMnthCnt }개월</option>
                                        </c:forEach>
                                    </select>
                                    <label class="c-form__label" for="enggMnthCnt">약정기간</label>
                                </div>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__select u-mt--0">
                                    <select class="c-select" id="modelMonthly">
                                        <c:forEach var="item" items="${listModelMonthly}">
                                            <option value="${item.modelMonthly }" <c:if test="${AppformReq.modelMonthly eq item.modelMonthly }" > selected </c:if>  >${item.modelMonthly }개월</option>
                                        </c:forEach>
                                    </select>
                                    <label class="c-form__label" for="modelMonthly">할부기간</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <h4 class="c-form__title--type2" id="inpNameCertiTitle">가입자 정보</h4>
                <div class="c-form-wrap">
                    <div class="c-form-group" role="group" aira-labelledby="radCsTypeTitle">
                        <div class="c-checktype-row c-col3 u-mt--0">
                            <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType1" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NA}" checked  >
                            <label class="c-label" for="cstmrType1">
                                <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                                <span>19세 이상 내국인</span>
                            </label>
                            <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType2" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NM}" >
                            <label class="c-label" for="cstmrType2">
                                <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                                <span>19세 미만 미성년자</span>
                            </label>
                            <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}" >
                            <label class="c-label" for="cstmrType3">
                                <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                                <span>외국인</span>
                            </label>
                        </div>
                    </div>
                </div>

                <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--48" id="inpNameCertiTitle">실명 및 본인인증</h4>
                <hr class="c-hr c-hr--title">
                <div class="c-form-wrap u-mt--32">
                    <div class="c-form-group" role="group" aira-labelledby="inpNameCertiTitle">
                        <div class="c-form-row c-col2 u-mt--0">
                            <div class="c-form-field">
                                <div class="c-form__input ">
                                    <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" value="${AppformReq.cstmrName}"  maxlength="60" <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">disabled="disabled"</c:if>>
                                    <label class="c-form__label" for="cstmrName">이름</label>
                                </div>
                            </div>
                            <c:choose>
                                <c:when test="${AppformReq.operType eq Constants.OPER_TYPE_CHANGE ||  AppformReq.operType eq Constants.OPER_TYPE_EXCHANGE  }">
                                    <div class="c-form-field _isForeigner device-chg_wrap" style="display:none;">
                                        <div class="device-chg-container">
                                            <div>
                                                <div class="c-form__input">
                                                    <input type="tel" class="c-input onlyNum mskDvcChg" style="width: 15.25rem" id="cstmrForeignerRrn01" type="text" autocomplete="off" maxlength="6" placeholder="외국인등록번호 앞자리" title="외국인등록번호 앞자리" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrForeignerRrn02');">
                                                    <label class="c-form__label" for="cstmrForeignerRrn01">생년월일</label>
                                                </div>
                                            </div>
                                            <span class="u-fs-22 u-fw--bold u-ta-center device-chg-num-span">-</span>
                                            <div class="device-chg-back-num">
                                                <div class="c-form__input">
                                                    <input type="tel" class="c-input onlyNum device-chg-back-num-input mskDvcChg" id="cstmrForeignerRrn02" maxlength="1" placeholder="" aria-invalid="true" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,6,7)}" onkeyup="nextFocus(this, 1, 'changeCstmrMobileFn');">
                                                    <label class="c-form__label" for="cstmrForeignerRrn01"></label>
                                                </div>
                                                <span class="u-fs-15 device-chg-msk-span">●●●●●●</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="c-form-field _isDefault device-chg_wrap">
                                        <div class="device-chg-container">
                                            <div>
                                                <div class="c-form__input">
                                                    <input type="tel" class="c-input onlyNum mskDvcChg" style="width: 15.25rem" id="cstmrNativeRrn01" maxlength="6" placeholder="생년월일(6자리)" aria-invalid="true" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                                                    <label class="c-form__label" for="cstmrNativeRrn01">생년월일</label>
                                                </div>
                                            </div>
                                            <span class="u-fs-22 u-fw--bold u-ta-center device-chg-num-span">-</span>
                                            <div class="device-chg-back-num">
                                                <div class="c-form__input">
                                                    <input type="tel" class="c-input onlyNum device-chg-back-num-input mskDvcChg" id="cstmrNativeRrn02" maxlength="1" placeholder="" aria-invalid="true" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,6,7)}" onkeyup="nextFocus(this, 1, 'changeCstmrMobileFn');">
                                                    <label class="c-form__label" for="cstmrNativeRrn02"></label>
                                                </div>
                                                <span class="u-fs-15 device-chg-msk-span">●●●●●●</span>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="c-form-field _isForeigner" style="display:none;">
                                        <div class="c-form__input-group">
                                            <div class="c-form__input c-form__input-division">
                                                <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn01" autocomplete="off" type="text" maxlength="6" placeholder="외국인등록번호 앞자리" title="외국인등록번호 앞자리" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrForeignerRrn02');" >
                                                <span>-</span>
                                                <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn02" autocomplete="off" type="password" maxlength="7" placeholder="외국인등록번호 뒷자리" title="외국인등록번호 뒷자리" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,6,13)}" >
                                                <label class="c-form__label" for="cstmrForeignerRrn01">외국인 등록번호</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="c-form-field _isDefault" >
                                        <div class="c-form__input-group">
                                            <div class="c-form__input c-form__input-division">
                                                <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" type="text" autocomplete="false" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                                                <span>-</span>
                                                <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,6,13)}" >
                                                <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                                            </div>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <c:choose>
                            <c:when test="${AppformReq.operType eq Constants.OPER_TYPE_CHANGE ||  AppformReq.operType eq Constants.OPER_TYPE_EXCHANGE  }">
                                <div class="c-form-row c-col2 u-width--460 u-mt--16">
                                    <div class="c-form-field">
                                        <div class="c-form__input-group" id="changeCstmrMobile">
                                            <div class="c-form__input c-form__input-division">
                                                <input class="c-input--div3 onlyNum" id="changeCstmrMobileFn" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리" onkeyup="nextFocus(this, 3, 'changeCstmrMobileMn');" >
                                                <span>-</span>
                                                <input class="c-input--div3 onlyNum" id="changeCstmrMobileMn" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 중간자리" onkeyup="nextFocus(this, 4, 'changeCstmrMobileRn');"  >
                                                <span>-</span>
                                                <input class="c-input--div3 onlyNum" id="changeCstmrMobileRn" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 뒷자리" >
                                                <label class="c-form__label" for="changeCstmrMobile">기기변경 휴대폰 번호</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </div>


                    <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                        <div class="c-box c-box--type1 u-mt--20 _isDefaultText " style="display:none;">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                            </ul>
                        </div>
                    </c:if>

                    <div class="c-box c-box--type1 u-mt--20 _isTeen" style="display:none;">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item">영/유아(0세~4세 이하)의 경우 가입신청이 제한되므로 타 명의로 진행 바랍니다.</li>
                            <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                                <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                            </c:if>
                        </ul>
                    </div>

                    <div class="c-box c-box--type1 u-mt--20 _isForeigner" style="display:none;">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item">외국인 등록증과 동일하게 작성해주세요.</li>
                            <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                                <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                            </c:if>
                        </ul>
                    </div>

                </div>

                <div class="c-form-wrap u-mt--32 _isTeen " style="display:none;">
                    <div class="c-form-group" role="group" aira-labelledby="inpDepInfoTitle">
                        <h3 class="c-group--head" id="inpDepInfoTitle">법정대리인 정보</h3>
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input">
                                    <input class="c-input" id="minorAgentName" type="text" placeholder="법정대리인 성명" aria-invalid="true" value="${AppformReq.minorAgentName}" maxlength=60 >
                                    <label class="c-form__label" for="minorAgentName">법정대리인 성명</label>
                                </div>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__select">
                                    <select id="minorAgentRelation" class="c-select">
                                        <option value="">선택</option>
                                        <option value="01" <c:if test="${AppformReq.minorAgentRelation eq '01' }" > selected </c:if> >부</option>
                                        <option value="02" <c:if test="${AppformReq.minorAgentRelation eq '02' }" > selected </c:if> >모</option>
                                        <option value="10" <c:if test="${AppformReq.minorAgentRelation eq '10' }" > selected </c:if> >그외</option>
                                    </select>
                                    <label class="c-form__label" for="minorAgentRelation">신청인과의 관계</label>
                                </div>
                            </div>
                        </div>
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input-group">
                                    <div class="c-form__input c-form__input-division">
                                        <input class="c-input--div2 onlyNum" id="minorAgentRrn01" autocomplete="off" type="text" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="${fn:substring(AppformReq.minorAgentRrnDesc,0,6)}" >
                                        <span>-</span>
                                        <input class="c-input--div2 onlyNum" id="minorAgentRrn02" autocomplete="off" type="password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="${fn:substring(AppformReq.minorAgentRrnDesc,6,13)}" >
                                        <label class="c-form__label" for="minorAgentRrn01">법정대리인 주민등록번호</label>
                                    </div>
                                </div>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__input-group">
                                    <div class="c-form__input c-form__input-division">
                                        <input class="c-input--div3 onlyNum" id="minorAgentTelFn" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="전화번호 첫자리" value="${AppformReq.minorAgentTelFn}">
                                        <span>-</span>
                                        <input class="c-input--div3 onlyNum" id="minorAgentTelMn" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 중간자리" value="${AppformReq.minorAgentTelMn}">
                                        <span>-</span>
                                        <input class="c-input--div3 onlyNum" id="minorAgentTelRn" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 뒷자리" value="${AppformReq.minorAgentTelRn}">
                                        <label class="c-form__label" for="minorAgentTelFn">법정대리인 전화번호 <em class="c-form__sublabel">(휴대폰)</em></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <h5 class="c-form__title u-mt--48">법정대리인 안내사항 확인 및 동의</h5>
                    <div class="c-agree c-agree--type1">
                        <div class="c-agree__item">
                            <button class="c-button c-button--xsm" onclick="fnSetEventId('minorAgentAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MINOR_AGENT}',0);" >
                                <span class="c-hidden">법정대리인 안내사항 확인 및 동의(필수) 상세 팝업 열기</span>
                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                            <div class="c-agree__inner">
                                <input class="c-checkbox c-checkbox--type2 " id="minorAgentAgrmYn" type="checkbox" >
                                <label class="c-label" for="minorAgentAgrmYn">본인은 안내사항을 확인하였습니다.<span class="u-co-gray">(필수)</span>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 본인인증 방법 선택 -->
                <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
                    <jsp:param name="controlYn" value="N"/>
                    <jsp:param name="reqAuth" value="CNKATX"/>
                </jsp:include>

                <div class="c-accordion c-accordion--type1 acc-agree c-accordion--nested">
                    <span class="c-form__title--type2">약관동의</span>
                    <div class="c-accordion__box">
                        <div class="c-accordion__item">
                            <div class="c-accordion__head">
                                <button class="runtime-data-insert c-accordion__button" id="acc_agree_headerA1" type="button" aria-expanded="false" aria-controls="acc_agreeA1">
                                    <span class="c-hidden">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다. ※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span>
                                </button>
                                <div class="c-accordion__check">
                                    <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                                    <label class="c-label" for="btnStplAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span></label>
                                </div>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_agreeA1" aria-labelledby="acc_agree_headerA1" aria-hidden="true">
                                <div class="c-accordion__inside">

                                    <div class="c-accordion__sub-check _fromMove">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseMoveCode');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MOVE_CODE}',0);">
                                            <span class="c-hidden">번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseMoveCode" type="checkbox"  code="${Constants.CLAUSE_MOVE_CODE}" cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                                        <label class="c-label" for="clauseMoveCode">번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법 동의<span class="u-co-gray">(필수)</span></label>
                                    </div>

                                    <div class="c-accordion__sub-check">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}',0);">
                                            <span class="c-hidden">고유식별정보 수집&middot;이용 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriOfferFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" >
                                        <label class="c-label" for="clausePriOfferFlag">고유식별정보 수집&middot;이용 동의<span class="u-co-gray">(필수)</span></label>
                                    </div>

                                    <div class="c-accordion__sub-check">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}',0);" >
                                            <span class="c-hidden">개인정보/신용정보 수집·이용 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriCollectFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                                        <label class="c-label" for="clausePriCollectFlag">개인정보/신용정보 수집·이용 동의<span class="u-co-gray">(필수)</span>
                                        </label>
                                    </div>

                                    <div class="c-accordion__sub-check">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseEssCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}',0);">
                                            <span class="c-hidden">개인정보 제3자 제공 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseEssCollectFlag" type="checkbox"  cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y"  >
                                        <label class="c-label" for="clauseEssCollectFlag">개인정보 제3자 제공 동의<span class="u-co-gray">(필수)</span></label>
                                    </div>

                                    <div class="c-accordion__sub-check">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFathFlag01');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag01',0);" >
                                            <span class="c-hidden">민감정보(생체인식정보)수집 및 이용동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseFathFlag01" type="checkbox"  code="clauseFathFlag01"  cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                                        <label class="c-label" for="clauseFathFlag01">민감정보(생체인식정보)수집 및 이용동의<span class="u-co-gray">(필수)</span></label>
                                    </div>
                                    <div class="c-accordion__sub-check">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFathFlag02');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag02',0);" >
                                            <span class="c-hidden">민감정보(생체인식정보)조희 및 이용 / 3자 제공에 대한 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseFathFlag02" type="checkbox"  code="clauseFathFlag02"  cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                                        <label class="c-label" for="clauseFathFlag02">민감정보(생체인식정보)조희 및 이용 / 3자 제공에 대한 동의<span class="u-co-gray">(필수)</span></label>
                                    </div>
                                    
                                    <div class="c-accordion__sub-check _isTeen" style="display:none;" >
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('nwBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_23}',0);"  >
                                            <span class="c-hidden">청소년 유해정보 네트워크차단 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="nwBlckAgrmYn" type="checkbox"  code="${Constants.NOTICE_CODE_23}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y">
                                        <label class="c-label" for="nwBlckAgrmYn">청소년 유해정보 네트워크차단 동의<span class="u-co-gray">(필수)</span></label>
                                    </div>

                                    <div class="c-accordion__sub-check _isTeen" style="display:none;" >
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('appBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_24}',0);"  >
                                            <span class="c-hidden">청소년 유해정보차단 APP 설치 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="appBlckAgrmYn" type="checkbox"  code="${Constants.NOTICE_CODE_24}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y">
                                        <label class="c-label" for="appBlckAgrmYn">청소년 유해정보차단 APP 설치 동의<span class="u-co-gray">(필수)</span></label>
                                    </div>

                                    <c:if test="${AppformReq.prdtSctnCd eq PhoneConstant.FIVE_G_FOR_MSP}">
                                        <div class="c-accordion__sub-check">
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clause5gCoverage');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_5G_COVERAGE}',0);"  >
                                                <span class="c-hidden">5g 커버리지 확인 및 가입 동의(필수) 상세 팝업 열기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                            <input class="c-checkbox c-checkbox--type2 _agree" id="clause5gCoverage" type="checkbox"  code="${Constants.CLAUSE_5G_COVERAGE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y"  validityyn="Y">
                                            <label class="c-label" for="clause5gCoverage">5g 커버리지 확인 및 가입 동의<span class="u-co-gray">(필수)</span></label>
                                        </div>
                                    </c:if>

                                    <div class="c-accordion__sub-check _clauseJehuFlag" style="display:none;" >
                                        <button class="c-button c-button--xsm" id="clauseJehuButton" >
                                            <span class="c-hidden">제휴 서비스를 위한 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseJehuFlag" type="checkbox"  code="clauseJehuFlag" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="N" validityyn="Y" >
                                    </div>
                                    <div class="c-accordion__sub-check _clausePartnerOfferFlag" style="display:none;" >
                                        <button class="c-button c-button--xsm" id="clausePartnerOfferButton" >
                                            <span class="c-hidden">제휴사 정보 제공 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clausePartnerOfferFlag" type="checkbox"  code="clausePartnerOfferFlag" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="N" validityyn="Y" >
                                    </div>

                                    <div class="c-accordion c-accordion--nested__sub c-accordion__sub-check">
                                        <div class="c-accordion__box">
                                            <div class="c-accordion__item">
                                                <div class="c-accordion__head">
                                                    <button class="c-accordion__button" id="head5" type="button" aria-expanded="false" aria-controls="agree5"></button>
                                                    <div class="c-accordion__check">
                                                        <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="agreeWrap5" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
                                                        <label class="c-label" for="agreeWrap5">고객 혜택 제공을 위한 정보수집 이용동의 및 혜택 광고의 수신 위탁 동의<span class="u-co-gray">(선택)</span>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="c-accordion__panel expand" id="agree5" aria-labelledby="head5" aria-hidden="true">
                                                    <div class="c-accordion__inside">
                                                        <div class="c-accordion__sub-check">
                                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('personalInfoCollectAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.PERSONAL_INFO_COLLECT_AGREE}',0);">
                                                                <span class="c-hidden">고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의(선택) 상세 팝업 열기</span>
                                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                            </button>
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="personalInfoCollectAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.PERSONAL_INFO_COLLECT_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N">
                                                            <label class="c-label" for="personalInfoCollectAgree">고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의<span class="u-co-gray">(선택)</span>
                                                            </label>
                                                        </div>
                                                        <div class="c-accordion__sub-check">
                                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriAdFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_PRI_AD_CODE}',0);">
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

                                    <div class="c-accordion c-accordion--nested__sub c-accordion__sub-check">
                                        <div class="c-accordion__box">
                                            <div class="c-accordion__item">
                                                <div class="c-accordion__head">
                                                    <button class="c-accordion__button" id="head6" type="button" aria-expanded="false" aria-controls="agree6"></button>
                                                    <div class="c-accordion__check">
                                                        <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="agreeWrap6" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
                                                        <label class="c-label" for="agreeWrap6">혜택 제공을 위한 제 3자 제공 및 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="c-accordion__panel expand" id="agree6" aria-labelledby="head6" aria-hidden="true">
                                                    <div class="c-accordion__inside">
                                                        <div class="c-accordion__sub-check">
                                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('othersTrnsAllAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.OTHERS_TRNS_ALL_AGREE}',0);initOthersTrnsAgrees()">
                                                                <span class="c-hidden">혜택 제공을 위한 제 3자 제공 및 광고 수신 동의 상세 팝업 열기</span>
                                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                            </button>
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="othersTrnsAllAgree" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)" code="S${Constants.OTHERS_TRNS_ALL_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N">
                                                            <label class="c-label" for="othersTrnsAllAgree">혜택 제공을 위한 제3자 제공 동의<span class="u-co-gray">(선택)</span>
                                                            </label>
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="formOthersTrnsAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_TRNS_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5" style="display: none;">
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="formOthersTrnsKtAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_TRNS_KT_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5" style="display: none;">
                                                        </div>
                                                        <div class="c-accordion__sub-check">
                                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('othersAdReceiveAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.OTHERS_AD_RECEIVE_AGREE}',0);">
                                                                <span class="c-hidden">제3자 제공관련 광고 수신 동의(선택) 상세 팝업 열기</span>
                                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                            </button>
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="othersAdReceiveAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_AD_RECEIVE_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5">
                                                            <label class="c-label" for="othersAdReceiveAgree">제3자 제공관련 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                        <%--                            <div class="c-chk-wrap">--%>
                                        <%--                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriTrustFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_TRUST_CODE}',0);" >--%>
                                        <%--                                    <span class="c-hidden">개인정보의 처리 업무 위탁 동의(필수) 상세 팝업 열기</span>--%>
                                        <%--                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>--%>
                                        <%--                                </button>--%>
                                        <%--                                <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriTrustFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_TRUST_CODE}" cdGroupId1=FORMREQUIRED  docVer="0"  type="checkbox"  value="Y" validityyn="Y" >--%>
                                        <%--                                <label class="c-label" for="clausePriTrustFlag">개인정보의 처리 업무 위탁 동의<span class="u-co-gray">(필수)</span>--%>
                                        <%--                                </label>--%>
                                        <%--                            </div>--%>

                                        <%--                            <div class="c-chk-wrap">--%>
                                        <%--                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseConfidenceFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_CONFIDENCE_CODE}',0);">--%>
                                        <%--                                    <span class="c-hidden">신용정보 조회 ·이용 ·제공에 대한 동의서(필수) 상세 팝업 열기</span>--%>
                                        <%--                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>--%>
                                        <%--                                </button>--%>
                                        <%--                                <input class="c-checkbox c-checkbox--type2 _agree" id="clauseConfidenceFlag" type="checkbox"  code="${Constants.CLAUSE_CONFIDENCE_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" >--%>
                                        <%--                                <label class="c-label" for="clauseConfidenceFlag">신용정보 조회 &middot; 이용 &middot; 제공에 대한 동의서<span class="u-co-gray">(필수)</span>--%>
                                        <%--                                </label>--%>
                                        <%--                            </div>--%>

                                        <%--                            <div class="c-chk-wrap">--%>
                                        <%--                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFinanceFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_FINANCE_FLAG}',0);">--%>
                                        <%--                                    <span class="c-hidden">개인(신용)정보 처리 및 보험가입 동의(선택) 상세 팝업 열기</span>--%>
                                        <%--                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>--%>
                                        <%--                                </button>--%>
                                        <%--                                <input class="c-checkbox c-checkbox--type2 _agree" id="clauseFinanceFlag" type="checkbox"  code="S${Constants.CLAUSE_FINANCE_FLAG}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" >--%>
                                        <%--                                <label class="c-label" for="clauseFinanceFlag">개인(신용)정보 처리 및 보험가입 동의<span class="u-co-gray">(선택)</span>--%>
                                        <%--                                </label>--%>
                                        <%--                            </div>--%>
                                    <div class="c-accordion__sub-check">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('notice');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}',0);" >
                                            <span class="c-hidden">서비스 이용약관(고지) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <p class="u-ml--36 u-fs-15 u-co-black">서비스 이용약관<span class="u-co-gray u-ml--4">(고지)</span>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <!-- STEP2 START -->
            <div id="step2" class="c-row c-row--lg _divStep" style="display:none" >
                <div class="c-form-step">
                    <span class="c-form-step__number">2</span>
                    <h3 class="c-form-step__title">유심정보·신분증 확인</h3>
                </div>

                <h4 class="c-form__title--type2" id="radinfoTypeTitle">유심정보</h4>
                <div class="c-form-wrap _noSelf">
                    <div class="c-form-group" role="group" aira-labelledby="radinfoTypeTitle">
                        <div class="c-checktype-row c-col2">
                            <input class="c-radio c-radio--button" id="onOffLineUsim01" type="radio" name="onOffLineUsim" value="offLine" checked  >
                            <label class="c-label" for="onOffLineUsim01">유심 보유</label>
                            <input class="c-radio c-radio--button" id="onOffLineUsim02" type="radio" name="onOffLineUsim" value="onLine"  >
                            <label class="c-label" for="onOffLineUsim02">유심 미보유</label>
                        </div>
                    </div>
                </div>

                <div class="c-box c-box--type1 c-box--bullet _onLineUsim">
                    <ul class="c-text-list c-bullet c-bullet--dot u-fs-16" id="ulOnOffLineUsimDesc">
                        <li class="c-text-list__item u-co-gray">유심 카드를 보유한 고객님께서는 유심번호 19자리를 입력해 주세요.</li>
                        <li class="c-text-list__item u-co-gray">유심 보유를 선택하실 경우 유심비용이 미청구 됩니다.</li>
                    </ul>
                </div>

                <div class="c-form-wrap u-mt--32 _offLineUsim">
                    <div class="c-form-group" role="group" aira-labelledby="chkValUsimTitle1">
                        <h3 class="c-group--head c-hidden u-mb--0" id="chkValUsimTitle1">유심 유효성 체크</h3>
                        <div class="u-box--w460 u-margin-auto">
                            <div class="c-form">
                                <div class="c-img-wrap">
                                    <img src="/resources/images/portal/content/img_usim_example.png" alt="유심카드 예시(유심번호는 4자리+4자리+4자리+4자리+3자리 숫자)">
                                </div>

                                <!-- 에러 발생 시 .has-error(aria-invalid="true") validation 통과 시 .has-safe(aria-invalid="false") 클래스를 사용-->
                                <div class="c-input-wrap ">
                                    <label class="c-form__label c-hidden" for="reqUsimSn">유심번호 입력</label>
                                    <input class="c-input onlyNum" id="reqUsimSn" type="number" maxlength="19" placeholder="직접입력('-'없이 입력)" aria-invalid="false" aria-describedby="msgA1" value="${AppformReq.reqUsimSn}" >
                                </div>
                            </div>

                            <div class="c-box u-mt--24">
                                <ul class="c-text-list c-bullet c-bullet--dot u-fs-16" id="ulOnOffLineUsimDesc">
                                    <li class="c-text-list__item u-co-gray">유심 카드를 보유한 고객님께서는 유심번호 19자리를 입력해 주세요.</li>
                                    <li class="c-text-list__item u-co-gray">유심 보유를 선택하실 경우 유심비용이 미청구 됩니다.</li>
                                </ul>
                            </div>

                            <div class="c-button-wrap u-mt--32">
                                <button id="btnUsimNumberCheck" class="c-button c-button--md c-button--mint c-button--w460">유심 유효성 체크</button>
                            </div>
                        </div>
                    </div>
                </div>
                <h4 class="c-form__title--type2 _fromMove _fromNew" id="inpCsInfoTitle">연락처</h4>
                <div class="c-form-wrap _fromMove _fromNew">
                    <div class="c-form-group" role="group" aira-labelledby="inpCsInfoTitle">
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input-group _isDefaultMt">
                                    <div class="c-form__input c-form__input-division">
                                        <input class="c-input--div3 onlyNum" id="cstmrMobileFn" value="${AppformReq.cstmrMobileFn}" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리" onkeyup="nextFocus(this, 3, 'cstmrMobileMn');" >
                                        <span>-</span>
                                        <input class="c-input--div3 onlyNum" id="cstmrMobileMn" value="${AppformReq.cstmrMobileMn}" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 중간자리" onkeyup="nextFocus(this, 4, 'cstmrMobileRn');" >
                                        <span>-</span>
                                        <input class="c-input--div3 onlyNum" id="cstmrMobileRn" value="${AppformReq.cstmrMobileRn}" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 뒷자리" onkeyup="nextFocus(this, 4, 'cstmrTelFn');" >
                                        <label class="c-form__label" for="inpPhoneNum">휴대폰</label>
                                    </div>
                                </div>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__input-group _isDefaultMt">
                                    <div class="c-form__input c-form__input-division">
                                        <input class="c-input--div3 onlyNum" id="cstmrTelFn" value="${AppformReq.cstmrTelFn}" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="전화번호 첫자리" onkeyup="nextFocus(this, 3, 'cstmrTelMn');" >
                                        <span>-</span>
                                        <input class="c-input--div3 onlyNum" id="cstmrTelMn" value="${AppformReq.cstmrTelMn}" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 중간자리" onkeyup="nextFocus(this, 4, 'cstmrTelRn');" >
                                        <span>-</span>
                                        <input class="c-input--div3 onlyNum" id="cstmrTelRn" value="${AppformReq.cstmrTelRn}" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 뒷자리" onkeyup="nextFocus(this, 4, 'btnAddr');" >
                                        <label class="c-form__label" for="inpTelNum">전화번호(선택)</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="c-form-row c-col2 u-width--460 u-mt--16">
                            <div class="c-form-field">
                                <div class="c-form__input u-mt--0 ">
                                    <input class="c-input" id="cstmrMail" type="text" placeholder="이메일 입력" aria-invalid="true" value="${AppformReq.cstmrMail}" maxlength="50" >
                                    <label class="c-form__label" for="cstmrMail">이메일</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="c-form-wrap u-mt--48 _fromMove _fromNew">
                    <div class="c-form-group" role="group" aira-labelledby="inputAddressHead">
                        <h4 class="c-form__title--type2" id="inputAddressHead">주소</h4>
                        <div class="c-form-row c-col2">
                            <div class="c-form">
                                <div class="c-input-wrap u-mt--0">
                                    <label class="c-form__label c-hidden" for="inpAdressNum">우편번호</label>
                                    <input class="c-input" id="cstmrPost" type="text" placeholder="우편번호" value="${AppformReq.cstmrPost}" readonly>
                                    <button id="btnAddr" class="c-button c-button--sm c-button--underline _btnAddr" addrFlag="1">우편번호찾기</button>
                                </div>
                            </div>
                            <div class="c-form">
                                <div class="c-input-wrap u-mt--0">
                                    <label class="c-form__label c-hidden" for="cstmrAddr">주소</label>
                                    <input class="c-input" id="cstmrAddr" type="text" placeholder="주소" value="${AppformReq.cstmrAddr}" readonly >
                                </div>
                            </div>
                        </div>
                        <div class="c-form u-mt--16">
                            <label class="c-label c-hidden" for="cstmrAddrDtl">상세주소</label>
                            <input class="c-input" id="cstmrAddrDtl" type="text" placeholder="상세주소" value="${AppformReq.cstmrAddrDtl}" name="cstmrAddrDtl" readonly>
                        </div>
                    </div>
                </div>

                <div class="c-form-wrap u-mt--48 _fromMove _fromNew">
                    <div class="c-form-group" role="group" aira-labelledby="radIdCardHead">
                        <h5 class="c-form__title--type2" id="radIdCardHead">신분증 확인</h5>
                        <div class="c-checktype-row c-col3">
                            <input class="c-radio c-radio--button" id="selfCertType1" value="01" type="radio" name="selfCertType"  >
                            <label class="c-label" for="selfCertType1">주민등록증</label>
                            <input class="c-radio c-radio--button" id="selfCertType2" value="02" type="radio" name="selfCertType"  >
                            <label class="c-label" for="selfCertType2">운전면허증</label>
                            <input class="c-radio c-radio--button" id="selfCertType3" value="03" type="radio" name="selfCertType"  >
                            <label class="c-label" for="selfCertType3">장애인 등록증</label>
                        </div>
                        <div class="c-checktype-row c-col3">
                            <input class="c-radio c-radio--button" id="selfCertType4" value="04" type="radio" name="selfCertType"  >
                            <label class="c-label" for="selfCertType4">국가 유공자증</label>
                            <input class="c-radio c-radio--button" id="selfCertType6" value="06" type="radio" name="selfCertType"   >
                            <label class="c-label" for="selfCertType6">외국인 등록증</label>
                            <input class="c-radio c-radio--button c-hidden" id="selfCertType5" value="05" type="radio" name="selfCertType"  >
                            <label class="c-label c-hidden" for="selfCertType5">여권</label>
                        </div>
                    </div>
                </div>

                <div class="c-form-wrap _isFathCert" style="display:none;">
                    <div class="c-button-wrap u-mt--40">
                        <button id="btnFathUrlRqt" class="c-button c-button--md c-button--mint c-button--w460">안면인증 URL 받기</button>
                    </div>

                    <div class="c-button-wrap u-mt--40">
                        <button id="btnFathTxnRetv" class="c-button c-button--md c-button--mint c-button--w220" disabled>안면인증 결과 확인</button>
                        <button id="btnFathSkip" class="c-button c-button--md c-button--gray c-button--w220" onclick="requestFathSkip();" disabled>안면인증 SKIP</button>
                    </div>
                </div>
                
                
                
                <div class="c-form-wrap u-mt--32 _chkIdCardTitle" style="display:none;">
                    <div class="c-form-group" role="group" aira-labelledby="chkIdCardTitle">
                        <h3 class="c-group--head c-hidden" id="chkIdCardTitle">신분증 확인 검증</h3>
                        <div class="u-box--w460 u-margin-auto">
                            <!--[2022-02-07] aria-hidden 속성 및 케이스별 신분증 이미지 추가-->
                            <div class="c-img-wrap" aria-hidden="true">
                                <!--주민등록증 선택 시-->
                                <img id="imgRadIdCard" src="/resources/images/portal/content/img_jumin_card.png" alt="">
                            </div>

                            <div class="c-form__select _cstmrForeignerNation" style="display:none;">
                                <select class="c-select" id="cstmrForeignerNation" name="cstmrForeignerNation">

                                </select>
                                <label class="c-form__label" for="cstmrForeignerNation">국가</label>
                            </div>

                          <div class="c-form-row c-col2 u-mt--0 _selfIssuNumF" style="display:none;">
                                <div class="c-form-field">
                                    <div class="c-form__input">
                                        <input class="c-input" id="selfIssuNum" type="text" placeholder="보훈번호 입력">
                                        <label class="c-form__label" for="selfIssuNum">보훈번호</label>
                                    </div>
                                </div>
                            </div>

                             <!-- 운전면허증 선택 시 노출-->
                          <div class="c-form-row c-col2 _driverSelfIssuNumF" style="display:none;">
                              <div class="c-form-field">
                                 <div class="c-form__input">
                                    <input class="c-input" id="driverSelfIssuNum" type="text" placeholder="면허번호 입력" value="${AppformReq.selfIssuNumDesc}">
                                    <label class="c-form__label" for="driverSelfIssuNum">면허번호</label>
                                  </div>
                              </div>
                                    <input  type="hidden" id="driverSelfIssuNum1">
                                    <input type="hidden"  id="driverSelfIssuNum2">
                          </div>

                            <div class="c-form-field c-form-field--datepicker ">
                                <div class="c-form__input has-value">
                                    <input class="c-input flatpickr" id="selfIssuExprDt" type="text" placeholder="YYYY-MM-DD" value="<fmt:formatDate value="${AppformReq.selfIssuExprDate}" pattern="yyyy-MM-dd"   />" maxlength="10"  >
                                    <label class="c-form__label" for="selfIssuExprDt">발급일자</label>
                                    <span class="c-button c-button--calendar">
                                        <span class="c-hidden">날짜선택</span>
                                        <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                                    </span>
                                </div>
                            </div>

                            <div class="c-form-wrap u-mt--20">
                                <div class="c-checktype-row">
                                    <input class="c-checkbox" id="selfInqryAgrmYnFlag" type="checkbox" >
                                    <label class="c-label u-mb--20" for="selfInqryAgrmYnFlag">본인 조회에 동의합니다.</label>
                                </div>
                            </div>

                            <div class="c-box">
                                <p class="c-bullet c-bullet--dot u-co-gray">입력하신 정보는 한국정보통신진흥협회(KAIT)에서 제공하는 부정가입방지 시스템을 통해 신분증 진위여부 판단에 이용됩니다.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="divDlvry"  style="display:none" >

                    <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--64" id="inputAddressHead">배송정보</h4>
                    <div class="c-checktype-row _fromMove _fromNew">
                        <input class="c-checkbox c-checkbox--type3" id="inEqualCheck" type="checkbox" name="inEqualCheck">
                        <label class="c-label" for="inEqualCheck">가입고객 정보와 동일</label>
                    </div>
                    <hr class="c-hr c-hr--title">
                    <div class="c-form-wrap">
                        <div class="c-form-group" role="group" aira-labelledby="inputAddressHead">
                            <div class="c-form-row c-col2">
                                <div class="c-form-field">
                                    <div class="c-form__input u-mt--0">
                                        <input class="c-input" id="dlvryName" maxlength='25' type="text" placeholder="이름 입력" aria-invalid="true" value="${AppformReq.dlvryName}" >
                                        <label class="c-form__label" for="dlvryName">받으시는분</label>
                                    </div>
                                </div>
                                <div class="c-form-field">
                                    <div class="c-form__input-group u-mt--0">
                                        <div class="c-form__input c-form__input-division">
                                            <input class="c-input--div3" id="dlvryTelFn" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="전화번호 첫자리" value="${AppformReq.dlvryTelFn}">
                                            <span>-</span>
                                            <input class="c-input--div3" id="dlvryTelMn" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 중간자리" value="${AppformReq.dlvryTelMn}">
                                            <span>-</span>
                                            <input class="c-input--div3" id="dlvryTelRn" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 뒷자리" value="${AppformReq.dlvryTelRn}">
                                            <label class="c-form__label" for="dlvryTelFn">전화번호(선택)</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="c-form-row c-col2 u-width--460">
                                <div class="c-form-field">
                                    <div class="c-form__input-group">
                                        <div class="c-form__input c-form__input-division">
                                            <input class="c-input--div3" id="dlvryMobileFn" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 첫자리" value="${AppformReq.dlvryMobileFn}">
                                            <span>-</span>
                                            <input class="c-input--div3" id="dlvryMobileMn" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰 중간자리" value="${AppformReq.dlvryMobileMn}" >
                                            <span>-</span>
                                            <input class="c-input--div3" id="dlvryMobileRn" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰 뒷자리" value="${AppformReq.dlvryMobileRn}" >
                                            <label class="c-form__label" for="dlvryMobileFn">휴대폰</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-form-wrap u-mt--48">
                        <div class="c-form-group" role="group" aira-labelledby="inputAddressHead">
                            <h5 class="c-group--head" id="inputAddressHead">주소</h5>
                            <div class="c-form-row c-col2">
                                <div class="c-form">
                                    <div class="c-input-wrap u-mt--0">
                                        <label class="c-form__label c-hidden" for=dlvryPost>우편번호</label>
                                        <input class="c-input" id="dlvryPost" type="text" placeholder="우편번호" value="${AppformReq.dlvryPost}" readonly>
                                        <button class="c-button c-button--sm c-button--underline _btnAddr" addrFlag="2">우편번호 찾기</button>
                                    </div>
                                </div>
                                <div class="c-form">
                                    <div class="c-input-wrap u-mt--0">
                                        <label class="c-form__label c-hidden" for="dlvryAddr">주소</label>
                                        <input class="c-input" id="dlvryAddr" type="text" placeholder="주소" value="${AppformReq.dlvryAddr}" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="c-form u-mt--16">
                                <label class="c-label c-hidden" for="dlvryAddrDtl">상세주소</label>
                                <input class="c-input" id="dlvryAddrDtl" type="text" placeholder="상세주소"  value="${AppformReq.dlvryAddrDtl}" readonly>
                            </div>

                            <!-- 직접입력 항목 선택 시 활성화-->
                            <div class="c-form _isNotNowDlvry">
                                <div class="c-input-wrap c-input-wrap--textinp">
                                    <label class="c-label c-hidden">배송 메시지 입력</label>
                                    <input id="dlvryMemo" class="c-input" type="text" placeholder="배송 메시지 입력" value="${AppformReq.dlvryMemo}">
                                    <div class="c-input-wrap__sub">
                                        <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                                        <span>0/50</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr class="c-hr c-hr--title u-mt--48">
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item u-co-gray">단말 및 유심 신규신청 건은 선 개통 되어 발송되며, 기기변경/번호이동 건은 본인 수령 확인 및 개통 안내문자 발송 후 개통이 진행됩니다.</li>
                        <li class="c-text-list__item u-co-gray">단말 및 유심 번호이동 건은 선개통 진행 시 기존에 사용하시던 휴대폰을 일시적으로 사용 못하실 수 있으니, 선 개통을 원치 않으실 경우에는 해피콜 상담 시 말씀해 주시기 바랍니다.</li>
                    </ul>
                </div>

            </div>

            <!-- STEP3 START -->
            <div id="step3" class="c-row c-row--lg _divStep" style="display:none">
                <div class="c-form-step">
                    <span class="c-form-step__number">3</span>
                    <h3 class="c-form-step__title">가입신청 정보</h3>
                </div>

                <c:set var="codeListMoveC" value="${nmcp:getCodeList(Constants.WIRE_SERVICE_CODE)}" />
                <div class="c-form-wrap _fromMove">
                    <div class="c-form-group" role="group" aira-labelledby="useCompanyTitle">
                        <h3 class="c-form__title--type2" id="useCompanyTitle">사용중인 통신사</h3>
                        <div class="c-checktype-row c-col4 u-mt--0">
                            <c:forEach items="${codeListMoveC}" var="moveObj"  varStatus="status" >
                                <c:if test="${moveObj.dtlCdDesc eq '01'  }" >
                                    <input type="radio" class="c-radio c-radio--button" id="moveCompanyGroup1_${status.index}" name="moveCompanyGroup1" value="${moveObj.dtlCd}" mpCode="${moveObj.expnsnStrVal1}" companyNm="${moveObj.dtlCdNm}" companyTel="${moveObj.expnsnStrVal2}" >
                                    <label class="c-label" for="moveCompanyGroup1_${status.index}">${moveObj.dtlCdNm}</label>
                                </c:if>
                            </c:forEach>
                            <input type="radio" class="c-radio c-radio--button" id="moveCompanyGroup1_99" name="moveCompanyGroup1" value="-1">
                            <label class="c-label" for="moveCompanyGroup1_99">알뜰폰</label>
                        </div>
                        <!-- 알뜰폰 선택 시 나오는 항목 -->
                        <div id="divMoveCompanyGroup2"  class="c-form-row c-col2 u-mt--20" style="display:none" >
                            <div class="c-form-field">
                                <div class="c-form__select u-mt--0">
                                    <select class="c-select" id="moveCompanyGroup2">
                                        <option value="">알뜰폰 통신사</option>
                                        <option value="02">SKT알뜰폰</option>
                                        <option value="03">LGU+알뜰폰</option>
                                        <option value="04">KT알뜰폰</option>
                                    </select>
                                    <label class="c-form__label" for="moveCompanyGroup2">알뜰폰 통신사</label>
                                </div>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__select u-mt--0">
                                    <select class="c-select" id="moveCompany">

                                    </select>
                                    <label class="c-form__label" for="moveCompany">알뜰폰 사업자</label>
                                </div>
                            </div>
                        </div>
                        <!-- 알뜰폰 선택 시 나오는 항목 끝 -->
                    </div>
                    <div class="c-box c-box--type1 u-mt--20">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-co-gray">통신사를 모르실 경우 현재 사용중인 휴대폰에서 114로 확인 바랍니다.</li>
                        </ul>
                    </div>
                </div>

                <div class="c-form-wrap u-mt--48 _fromMove">
                    <div class="c-form-group" role="group" aira-labelledby="inpMoveNumbHead">
                        <h5 class="c-form__title--type2" id="inpMoveNumbHead">번호이동할 전화번호</h5>
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input-group u-width--460 u-mt--0">
                                    <div class="c-form__input c-form__input-division">
                                        <input class="c-input--div3 onlyNum" id="moveMobileFn" type="text" maxlength="3" value="${AppformReq.moveMobileFn}" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리" onkeyup="nextFocus(this, 3, 'moveMobileMn');" >
                                        <span>-</span>
                                        <input class="c-input--div3 onlyNum" id="moveMobileMn" type="text" maxlength="4" value="${AppformReq.moveMobileMn}" placeholder="숫자입력" title="휴대폰번호 중간자리" onkeyup="nextFocus(this, 4, 'moveMobileRn');" >
                                        <span>-</span>
                                        <input class="c-input--div3 onlyNum" id="moveMobileRn" type="text" maxlength="4" value="${AppformReq.moveMobileRn}" placeholder="숫자입력" title="휴대폰번호 뒷자리"  >
                                        <label class="c-form__label" for="inpPhoneNum">휴대폰</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>



                <div class="c-form-wrap reservation _fromNew ">
                    <div class="c-form-group" role="group" aira-labelledby="inpReservNumHead">
                        <div class="c-flex c-flex--jfy-between">
                            <h5 class="c-form__title--type2 u-mb--0" id="inpReservNumHead">가입희망번호 뒷 4자리</h5>
                        </div>
                        <div class="c-box c-box--type1">
                            1순위 <span class="reservation__text">010 - **** -</span>
                            <input class="c-input onlyNum" id="reqWantNumber" type="number"  maxlength="4" pattern="[0-9]" placeholder="" value="${AppformReq.reqWantNumber}">
                        </div>
                        <div class="c-box c-box--type1">
                            2순위 <span class="reservation__text">010 - **** -</span>
                            <input class="c-input onlyNum" id="reqWantNumber2" type="number"  maxlength="4" pattern="[0-9]" placeholder="" value="${AppformReq.reqWantNumber2}">
                        </div>
                        <div class="c-box c-box--type1">
                            3순위 <span class="reservation__text">010 - **** -</span>
                            <input class="c-input onlyNum" id="reqWantNumber3" type="number"  maxlength="4" pattern="[0-9]" placeholder="" value="${AppformReq.reqWantNumber3}">
                        </div>
                    </div>
                    <ul class="c-text-list c-bullet c-bullet--dot u-fs-16 u-mt--16">
                        <li class="c-text-list__item u-co-gray">희망하시는 전화번호 뒷자리 번호를 입력해 주시면 해당 번호로 개통이 진행 됩니다.</li>
                    </ul>
                    <ul class="c-text-list c-bullet c-bullet--fyr u-fs-16">
                        <li class="c-text-list__item u-co-red">단, 1111, 1234와 같이 동일하거나 연속적인 번호(골드번호)는 번호부여가 불가하오니 참고 부탁 드립니다.</li>
                        <li class="c-text-list__item u-co-red">골드번호 예시 안내
                            <div class="cols-wrap" style="gap: 0.25rem 0; margin-top: 0.25rem;">
                                <div class="col-3">① 단일숫자반복 (예: 1111, 2222)</div>
                                <div class="col-3">② 끝자리반복 (예: 0001, 0002)</div>
                                <div class="col-3">③ 짝수쌍패턴 (예: 0011, 1100)</div>
                                <div class="col-3">④ 반복쌍패턴 (예: 0101, 0202)</div>
                                <div class="col-3">⑤ 연속쌍패턴 (예: 1000, 2000)</div>
                                <div class="col-3">⑥ 연속숫자형 (예: 0123, 4567)</div>
                                <div class="col-3">⑦ 전체반복패턴 (예: 1234-1234)</div>
                                <div class="col-2">⑧ 특정의미 번호 (예: 1004(천사), 7942(친구사이))</div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="c-form u-width--460 _fromChange">
                    <label class="c-label" for="inputPhoneNum">기기변경 휴대폰 번호</label>
                    <input class="c-input" id="inputPhoneNum" type="text" placeholder="기기변경 휴대폰 번호" value="" readonly>
                </div>
            </div>
            <!-- STEP3 END -->

            <!-- STEP4 START -->
            <div id="step4" class="_divStep" style="display:none">

                <div class="c-row c-row--lg">
                    <div class="c-form-step">
                        <span class="c-form-step__number">4</span>
                        <h3 class="c-form-step__title">부가서비스 정보</h3>
                    </div>
                    <div class="c-form-wrap">
                        <div class="c-form-group" role="group" aria-labelledby="inpAddInfoHead">
                            <!-- [2022-01-14] h3 -> h5 접근성 관련 태그 수정 -->
                            <h4 class="c-form__title--type2" id="inpAddInfoHead">
                                친구초대 추천인ID<span class="u-co-gray u-fs-16 u-ml--4">(선택)</span>
                            </h4>
                            <div class="c-form-row c-col2">
                                <div class="c-form">
                                    <c:set var="recommendFlagList" value="${nmcp:getCodeList(Constants.RECOMMEND_FLAG_GRUP_CODE)}" />
                                    <label class="c-label c-hidden" for="recommendFlagCd">추가정보 선택</label>
                                    <select class="c-select" id="recommendFlagCd" >
                                        <c:forEach items="${recommendFlagList}" var="recommendFlag" >
                                            <option value="${recommendFlag.dtlCd}" <c:if test="${AppformReq.recommendFlagCd eq recommendFlag.dtlCd }" > selected </c:if> >${recommendFlag.dtlCdNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="c-form">
                                    <div class="c-input-wrap u-mt--0">
                                        <input class="c-input" id="recommendInfo" type="text" placeholder="직접입력" value="${AppformReq.recommendInfo}" maxlength="20"  >
                                        <label class="c-form__label c-hidden" for="recommendInfo">직접입력</label>
                                        <input type="hidden" name="commendId" id="commendId"  value=""  title="추천 아이디"  >
                                    </div>
                                </div>
                            </div>
                            <p class="c-bullet c-bullet--dot u-co-gray">추천인ID 오/미입력 시 사은품 지급이 불가하니 정확히 입력 해주세요.</p>
                        </div>
                    </div>
                </div>




                <div class="c-row c-row--lg">

                    <div class="_fromMove _fromNew">


                        <h4 class="c-heading c-heading--fs20 c-heading--medium u-mt--56">부가서비스 신청<span class="u-co-gray u-fs-16 u-ml--4">(선택)</span></h4>
                        <div class="c-accordion c-accordion--type1 add-service" data-ui-accordion>
                            <div class="c-accordion__box">
                                <div class="c-accordion__item">
                                    <div class="c-accordion__head">
                                        <button class="c-accordion__button" id="accAddService" type="button" aria-expanded="false" aria-controls="accAddServiceContent">
                                            <span>자세히보기<i class="c-icon c-icon--triangle-bottom" aria-hidden="true"></i></span>
                                        </button>
                                        <div class="c-accordion__check">
                                            <input class="c-checkbox c-checkbox--type3" id="cbAddServiceCatchCall" type="checkbox">
                                            <label class="c-label" for="cbAddServiceCatchCall">
                                                <span class="text-label">추천</span>
                                                <b>캐치콜 플러스(월 880원)</b>
                                                놓친 전화도 문자로 딱! 중요한 순간을 지켜드립니다.
                                            </label>
                                        </div>
                                    </div>
                                    <div class="c-accordion__panel expand" id="accAddServiceContent" aria-labelledby="accAddService">
                                        <div class="c-accordion__inside">-</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="c-accordion c-accordion--type1 add-service" data-ui-accordion>
                            <div class="c-accordion__box u-mt--8">
                                <div class="c-accordion__item">
                                      <div class="c-accordion__head">
                                        <button class="c-accordion__button" id="callWaitingService" type="button" aria-expanded="false" aria-controls="callWaitingServiceContent">
                                            <span>자세히보기<i class="c-icon c-icon--triangle-bottom" aria-hidden="true"></i></span>
                                        </button>
                                        <div class="c-accordion__check">
                                            <input class="c-checkbox c-checkbox--type3" id="callWaiting" type="checkbox">
                                            <label class="c-label" for="callWaiting" >
                                                <b>통화중대기 설정/해제 (무료)</b>
                                                현재 통화는 잠시 대기, 새 전화는 바로 연결
                                            </label>
                                        </div>
                                    </div>
                                <div class="c-accordion__panel expand" id="callWaitingServiceContent" aria-labelledby="callWaitingService">
                                        <div class="c-accordion__inside">-</div>
                                 </div>
                                </div>
                            </div>
                        </div>
                        <div class="add-service-wrap u-mt--16">
                            <h5 class="c-heading c-heading--fs16 u-mb--15">선택한 부가서비스(<span id="spAdditionCnt">0</span>개)</h5>
                            <div class="c-charge add-service">
                                <div class="c-charge__panel" id="divAdditionListFree" style="display:none">
                                    <!-- <dl class="c-charge__item">
                                      <dt class="u-co-gray-9">스팸차단서비스</dt>
                                      <dd class="u-fw--medium u-co-sub-4">무료</dd>
                                    </dl>-->
                                </div>
                                <div class="c-charge__panel"  id="divAdditionListPrice" style="display:none">
                                    <h5 class="c-heading c-heading--fs16 u-mb--20">유료 부가서비스</h5>
                                    <!-- <dl class="c-charge__item">
                                      <dt class="u-co-gray-9">스팸차단서비스</dt>
                                      <dd class="u-fw--medium u-co-sub-4">무료</dd>
                                    </dl> -->
                                </div>
                                <div class="c-charge__panel" id="divAdditionListEmpty" >
                                    <p>선택된 부가서비스가 없습니다.</p>
                                </div>
                            </div>
                            <div class="c-button-wrap u-mt--20">
                                <button id="btnAddition" class="c-button c-button--sm c-button--primary c-button--w215 u-fs-15" data-dialog-trigger="#addServiceModal">기타 부가서비스 선택하기</button>
                            </div>
                        </div>

                        <div class="add-service-result c-box c-box--type1 u-flex-between u-mt--16">
                            <p class="c-text c-text--fs16">
                                <b>부가서비스 합계</b>
                                <span class="c-text c-text--fs14 u-co-gray u-ml--4">(VAT 포함)</span>
                            </p>
                            <div class="u-co-red">
                                <span class="u-fs-26 u-fw--bold" id="totalRantalTxt">0</span>
                                <span class="u-fs-15 u-fw--medium">원</span>
                            </div>
                        </div>
                        <p class="c-bullet c-bullet--dot u-co-gray u-fs-14 u-mt--15">개통 시 기본제공 되는 부가서비스는 상품소개 페이지를 참고 부탁드리며, 부가서비스는 홈페이지, App, 고객센터를 통해 추가로 가입/해지가 가능합니다.</p>

                    </div>




                    <h4 class="c-heading c-heading--fs20 c-heading--medium u-mt--56 u-mb--24 _insrProdForm">휴대폰안심보험 신청<span class="u-co-gray u-fs-16 u-ml--4">(선택)</span>
                    </h4>
                    <div class="c-button-wrap c-button-wrap--right _insrProdForm">
                        <button id="btnInsrProd" class="c-button c-button--sm c-button--white c-button-round--sm">휴대폰안심보험 신청/변경</button>
                    </div>
                    <!-- case - 안심서비스 미가입 시(데이터 없음)-->
                    <div class="c-nodata _insrProdForm" id="divInsrProdInfo"  >
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-nodata__text">선택한 휴대폰안심보험 서비스가 없습니다.</p>
                    </div>

                    <div class="product _insrProdList" style="display:none">
                        <ul class="c-card-col c-card-col--2">
                            <li class="c-card c-card--type2" id="insrProdView">
                                <!-- <div class="c-card__title">
                                  <strong>안드로이드 플래티넘</strong>
                                  <ul class="c-card__sub">
                                    <li>자급제 신품 전용 보험</li>
                                  </ul>
                                </div>
                                <div class="c-card__price-wrap">
                                  <i class="c-icon c-icon--android" aria-hidden="true"></i>
                                  <div class="c-card__price">
                                    <span class="c-card__price-title">월 이용료</span>
                                    <b>5,300 원</b>
                                  </div>
                                </div> -->
                            </li>
                        </ul>
                    </div>



                    <div class="c-form-wrap u-mt--32 _insrProdList" style="display:none">
                        <div class="c-accordion c-accordion--type1 acc-agree" data-ui-accordion="">
                            <h5 class="c-form__title">휴대폰안심보험 신청 동의</h5>
                            <div class="c-accordion__box">
                                <div class="c-accordion__item">
                                    <div class="c-accordion__head">
                                        <button class="runtime-data-insert c-accordion__button is-active" id="insr_agree_headerA1" type="button" aria-expanded="true" aria-controls="insr_agreeA1">
                                            <span class="c-hidden">휴대폰안심보험 가입 동의에 대하여 내용을 읽어보았으며, 보험가입에 동의합니다. (일괄 동의)</span>
                                        </button>
                                        <div class="c-accordion__check">
                                            <input class="c-checkbox" id="btnInsrAllCheck" type="checkbox">
                                            <label class="c-label" for="btnInsrAllCheck">휴대폰안심보험 가입 동의에 대하여 내용을 읽어보았으며, 보험가입에 동의합니다. (일괄 동의)</label>
                                        </div>
                                    </div>

                                    <div class="c-accordion__panel expand expanded" id="insr_agreeA1" aria-labelledby="insr_agree_headerA1" aria-hidden="true">
                                        <div class="c-accordion__inside">
                                            <div class="c-accordion__sub-check">
                                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseInsrProdFlag');openPage('termsPop','/termsPop.do','cdGroupId1=ClauseInsur&cdGroupId2=ClauseInsur01',0);">
                                                    <span class="c-hidden">이동통신 단말기 보험 상품 설명서 교부 확인(필수) 상세 팝업 열기</span>
                                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                </button>
                                                <input class="c-checkbox c-checkbox--type2 _agreeInsr" id="clauseInsrProdFlag" type="checkbox"  code="ClauseInsur01" cdGroupId1=ClauseInsur docVer="0"  type="checkbox"  value="Y" validityyn="Y" >
                                                <label class="c-label" for="clauseInsrProdFlag">이동통신 단말기 보험 상품 설명서 교부 확인 <span class="u-co-gray">(필수)</span>
                                                </label>
                                            </div>
                                            <div class="c-accordion__sub-check">
                                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseInsrProdFlag02');openPage('termsPop','/termsPop.do','cdGroupId1=ClauseInsur&cdGroupId2=ClauseInsur02',0);" >
                                                    <span class="c-hidden">개인정보 수집 및 이용동의 (필수) 상세 팝업 열기</span>
                                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                </button>
                                                <input class="c-checkbox c-checkbox--type2 _agreeInsr" id="clauseInsrProdFlag02" type="checkbox"  code="ClauseInsur02"  cdGroupId1=ClauseInsur docVer="0" value="Y" validityyn="Y" >
                                                <label class="c-label" for="clauseInsrProdFlag02">개인정보 수집 및 이용동의 <span class="u-co-gray">(필수)</span>
                                                </label>
                                            </div>

                                            <div class="c-accordion__sub-check" >
                                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseInsrProdFlag03');openPage('termsPop','/termsPop.do','cdGroupId1=ClauseInsur&cdGroupId2=ClauseInsur03',0);">
                                                    <span class="c-hidden">개인정보 제 3자 제공동의(필수) 상세 팝업 열기</span>
                                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                </button>
                                                <input class="c-checkbox c-checkbox--type2 _agreeInsr" id="clauseInsrProdFlag03" type="checkbox"  code="ClauseInsur03"   cdGroupId1=ClauseInsur docVer="0" type="checkbox"  value="Y" validityyn="Y"  >
                                                <label class="c-label" for="clauseInsrProdFlag03">개인정보 제 3자 제공동의<span class="u-co-gray">(필수)</span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="c-box c-box--type1 c-box--bullet u-mt--16 _insrProdList" style="display:none">
                            <strong class="c-heading c-heading--fs15">
                                <button class="c-button c-button--underline" data-dialog-trigger="#productManualModal">이동통신 단말기 보험 상품 설명서</button>
                            </strong>
                            <ul class="c-text-list c-bullet c-bullet--dot u-mt--20">
                                <li class="c-text-list__item u-co-gray">휴대폰 안심 서비스의 가입을 위해서는 명의자 본인 확인을 위해 휴대폰 SMS인증이 필요합니다.</li>
                                <li class="c-text-list__item u-co-gray">본인 명의의 휴대폰을 보유하고 계시지 않으실 경우에는 개통 후 고객센터를 통해 신청 부탁 드립니다.</li>
                                <li class="c-text-list__item u-co-gray">안드로이드/아이폰 전용 상품은 자급제 단말 구매일로부터 29일 이내 신청이 가능하며, 이외 상품은 개통일로부터 29일 이내 가입이 가능합니다.</li>
                            </ul>
                        </div>
                        <div class="c-button-wrap u-mt--40">
                            <button id="btnAuthInsr" class="c-button c-button--md c-button--mint c-button--w460" >휴대폰 인증</button>
                        </div>
                    </div>
                    <!-- 자급제 보상 서비스 신청 -->
                    <!-- AppformReq.reqBuyType  eq Constants.REQ_BUY_TYPE_USIM -->
                    <c:if test="${false}">
                        <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--70 _rwdProdForm">
                            자급제 보상 서비스 신청<span class="u-co-gray u-fs-14 u-ml--4">(선택)</span>
                        </h4>
                        <div class="c-button-wrap c-button-wrap--right _rwdProdForm">
                            <button id="btnRwdProd" class="c-button c-button--sm c-button--white c-button-round--sm">자급제 보상 서비스 신청/변경</button>
                        </div>
                        <hr class="c-hr c-hr--title _rwdProdForm">

                        <!-- case - 자급제 보상 서비스 미가입 시 (데이터 없음) -->
                        <div class="c-nodata _rwdProdForm" id="divRwdProdInfo">
                            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                            <p class="c-nodata__text">가입된 자급제 보상 서비스가 없습니다</p>
                        </div>

                        <!-- case - 자급제 보상 서비스 가입 시 (데이터 있음) -->
                        <div class="product _rwdProdList" style="display:none">
                            <ul class="c-card-col c-card-col--2">
                                <li class="c-card c-card--type2" id="rwdProdView">
                                    <!-- 선택한 자급제 보상 서비스 표시 -->
                                </li>
                            </ul>
                        </div>

                        <!-- 자급제 보상 서비스 가입 시 필수입력값, 필수약관, 휴대폰 인증 -->
                        <div class="c-form-wrap u-mt--48 _rwdProdList" style="display:none">

                            <!-- rwd 구입가 -->
                            <div class="c-form-wrap u-mt--48">
                                <div class="c-form-group" role="group" aira-labelledby="rwdInpBuyPricTitle">
                                    <h4 class="c-group--head" id="rwdInpBuyPricTitle">
                                        <span>자급제 단말기 구입가<span class="c-form__sub">(단위: 원)</span></span>
                                    </h4>
                                    <div class="c-form-row c-col2 u-width--460">
                                        <div class="c-form-field">
                                            <div class="c-form__input">
                                                <input class="c-input" id="rwdBuyPric" name="rwdBuyPric" type="text" placeholder="구입가격 입력" maxlength="15" >
                                                <label class="c-form__label" for="rwdBuyPric">구입가격</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- rwd imei 등록 (esim개통인 경우 imei값 이전에 입력받은 값으로 사용) -->
                            <div class="c-form-wrap u-mt--48">
                                <div class="c-form-group" role="group" aira-labelledby="rwdInpImeiTitle">
                                    <h4 class="c-group--head" id="rwdInpImeiTitle">
                                        <span>단말기 정보값<span class="c-form__sub">(IMEI)</span></span>
                                    </h4>

                                    <div class="c-form-row c-col2">
                                        <div class="c-form-field">
                                            <div class="c-form__input is-readonly">
                                                <input class="c-input" id="rwdImei1" name="rwdImei1" type="text" placeholder="IMEI1" value="${AppformReq.imei1}" readOnly>
                                                <label class="c-form__label" for="rwdImei1">IMEI1</label>
                                            </div>
                                        </div>
                                        <div class="c-form-field">
                                            <div class="c-form__input is-readonly">
                                                <input class="c-input" id="rwdImei2" name="rwdImei2" type="text" placeholder="IMEI2" value="${AppformReq.imei2}" readOnly>
                                                <label class="c-form__label" for="rwdImei2">IMEI2</label>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="rwdImeiForm">
                                        <div class="c-box c-box--type1 u-mt--16">
                                            <ul class="c-text-list c-bullet c-bullet--dot">
                                                <li class="c-text-list__item u-co-gray">자급제 보상 서비스 가입을 위해서는 IMEI 등록이 선행되어야 하며, IMEI 캡쳐화면 이미지 업로드가 필요합니다.</li>
                                                <li class="c-text-list__item u-co-gray">이미지 업로드 시 자동으로 입력됩니다.<a class="c-text-link--bluegreen" data-dialog-trigger="#uploadguide-modal">이미지 가이드 보기</a></li>
                                            </ul>
                                        </div>

                                        <div class="c-button-wrap u-mt--40">
                                            <label for="rwdImeiImage" class="c-button c-button-round--md c-button--mint u-width--460">이미지 등록</label>
                                            <input type="file" class="c-hidden" id="rwdImeiImage" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- rwd 구매영수증 등록 -->
                            <div class="c-form-wrap u-mt--48">
                                <div class="c-form-group" role="group" aria-labelledby="rwdInpFileTitle">
                                    <h4 class="c-group--head" id="rwdInpFileTitle">
                                        <span>구매영수증 첨부하기<span class="c-form__sub">(필수)</span></span>
                                    </h4>
                                    <div class="c-file-image">
                                        <input class="c-file upload-image__hidden" id="rwdFile1" name="rwdFile1" type="file" title="사진 등록" accept='.jpg, .gif, .png' onchange="setThumb();">
                                        <label class="c-label file-label"><span class="sr-only">업로드한</span> <span class="c-label__sub">첨부파일 0/1</span></label>
                                        <div class="c-form-row c-col2 staged_img-list">
                                            <!-- 등록된 파일 영역 -->
                                        </div>
                                        <p class="c-bullet c-bullet--fyr u-co-gray u-mt--0">2MB 이내의 jpg, gif, png 형식으로 등록 가능</p>
                                    </div>
                                </div>
                            </div>

                            <!-- rwd 약관동의 -->
                            <h5 class="c-form__title u-mt--48">자급제 보상 서비스 신청 동의</h5>
                            <div class="c-agree c-agree--type2">
                                <div class="c-agree__item">
                                    <input class="c-checkbox" id="clauseRwdFlag" type="checkbox">
                                    <label class="c-label" for="clauseRwdFlag">이용약관 및 개인정보 수집/이용에 모두 동의합니다.</label>
                                </div>
                                <div class="c-agree__inside">
                                    <!-- 약관 1 -->
                                    <div class="c-chk-wrap">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdRegFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_REG_FLAG}',0);" >
                                            <span class="c-hidden">상품설명(이용료) 및 서비스 가입 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _rwdAgree" id="clauseRwdRegFlag" type="checkbox"  code="${Constants.CLAUSE_RWD_REG_FLAG}" cdGroupId1="FORMREQUIRED"  docVer="0"  type="checkbox"  value="Y" validityyn="Y" >
                                        <label class="c-label" for="clauseRwdRegFlag">상품설명(이용료) 및 서비스 가입 동의<span class="u-co-gray">(필수)</span>
                                        </label>
                                    </div>
                                    <!-- 약관 2 -->
                                    <div class="c-chk-wrap">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdPaymentFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_PAYMENT_FLAG}',0);" >
                                            <span class="c-hidden">만기 시 보장율/보장금액 지급기준 이행 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _rwdAgree" id="clauseRwdPaymentFlag" type="checkbox"  code="${Constants.CLAUSE_RWD_PAYMENT_FLAG}"  cdGroupId1="FORMREQUIRED" docVer="0" value="Y" validityyn="Y" >
                                        <label class="c-label" for="clauseRwdPaymentFlag">만기 시 보장율/보장금액 지급기준 이행 동의<span class="u-co-gray">(필수)</span>
                                        </label>
                                    </div>
                                    <!-- 약관 3 -->
                                    <div class="c-chk-wrap">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdRatingFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_RATING_FLAG}',0);" >
                                            <span class="c-hidden">서비스 단말 반납 조건, 등급산정 및 평가 기준 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _rwdAgree" id="clauseRwdRatingFlag" type="checkbox"  code="${Constants.CLAUSE_RWD_RATING_FLAG}"  cdGroupId1="FORMREQUIRED" docVer="0" value="Y" validityyn="Y" >
                                        <label class="c-label" for="clauseRwdRatingFlag">서비스 단말 반납 조건, 등급산정 및 평가 기준 동의<span class="u-co-gray">(필수)</span>
                                        </label>
                                    </div>
                                    <!-- 약관 4 -->
                                    <div class="c-chk-wrap">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdPrivacyInfoFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_PRIVACY_INFO_FLAG}',0);" >
                                            <span class="c-hidden">개인정보 수집ㆍ이용에 대한 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _rwdAgree" id="clauseRwdPrivacyInfoFlag" type="checkbox"  code="${Constants.CLAUSE_RWD_PRIVACY_INFO_FLAG}"  cdGroupId1="FORMREQUIRED" docVer="0" value="Y" validityyn="Y" >
                                        <label class="c-label" for="clauseRwdPrivacyInfoFlag">개인정보 수집ㆍ이용에 대한 동의<span class="u-co-gray">(필수)</span>
                                        </label>
                                    </div>
                                    <!-- 약관 5 -->
                                    <div class="c-chk-wrap">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdTrustFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_TRUST_FLAG}',0);" >
                                            <span class="c-hidden">서비스 위탁사 개인정보 수집ㆍ이용ㆍ제공ㆍ위탁 동의서(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _rwdAgree" id="clauseRwdTrustFlag" type="checkbox"  code="${Constants.CLAUSE_RWD_TRUST_FLAG}"  cdGroupId1="FORMREQUIRED" docVer="0" value="Y" validityyn="Y" >
                                        <label class="c-label" for="clauseRwdTrustFlag">서비스 위탁사 개인정보 수집ㆍ이용ㆍ제공ㆍ위탁 동의서<span class="u-co-gray">(필수)</span>
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <!-- rwd 휴대폰 인증 -->
                            <div class="c-button-wrap u-mt--40">
                                <button id="btnAuthRwd" class="c-button c-button-round--md c-button--mint c-button--w460" >휴대폰 인증</button>
                            </div>
                        </div>

                        <div class="c-box c-box--type1 u-mt--48 _rwdProdList" style="display:none">
                            <strong class="c-heading c-heading--fs15">
                                <button class="c-button c-button--underline" data-dialog-trigger="#rwdManualModal">자급제 보상 서비스 상품 설명서</button>
                            </strong>
                            <ul class="c-text-list c-bullet c-bullet--dot u-mt--20">
                                <li class="c-text-list__item u-co-gray">서비스 보상 신청은 가입 후 19개월 차 ~ 21개월 차에 새로운 단말기로 구매한 구매영수증 필히 제출하고 kt M mobile 이동통신서비스를 유지하는 경우 가능합니다.</li>
                                <li class="c-text-list__item u-co-gray">보상 받을 단말은 서비스 가입 당시 구매한 단말기 (IMEI 일치)여야하며 (단, 분실보험에 의한 기변, 제조사 공식 서비스센터로부터 A/S 기변한 고객은 변경된 동종 단말에 한해 반납 가능) 반납 단말의 상태가 완파, 계정잠김, 단말 분실 혹은 매입 할 수 없는 등급일 경우 서비스 신청이 불가 할 수 있습니다.</li>
                            </ul>
                        </div>
                    </c:if>
                    <!-- // 자급제 보상 서비스 신청 -->
                </div>

            </div>
            <!-- STEP4 END -->

            <!-- STEP5 START -->
            <div id="step5" class="c-row c-row--lg _divStep" style="display:none" >
                <div class="c-form-step">
                    <span class="c-form-step__number">5</span>
                    <h3 class="c-form-step__title">납부정보·가입정보 확인</h3>
                </div>
                <div class="c-form-wrap">
                    <div class="c-form-group" role="group" aira-labelledby="radReceiveTypeTitle">
                        <h5 class="c-form__title--type2" id="radReceiveTypeTitle">명세서 수신 유형</h5>
                        <div class="c-checktype-row c-col3 u-mt--0">
                            <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrBillSendCode02" value="CB" type="radio" name="cstmrBillSendCode" checked>
                            <label class="c-label" for="cstmrBillSendCode02">
                                <i class="c-icon c-icon--email-bill" aria-hidden="true"></i>
                                <span>이메일 명세서</span>
                            </label>

                            <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrBillSendCode03" value="MB" type="radio" name="cstmrBillSendCode" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NM}">disabled</c:if>>
                            <label class="c-label" for="cstmrBillSendCode03">
                                <i class="c-icon c-icon--mobile-bill" aria-hidden="true"></i>
                                <span>모바일 명세서(MMS)</span>
                            </label>

                            <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrBillSendCode01" value="LX" type="radio" name="cstmrBillSendCode"  >
                            <label class="c-label" for="cstmrBillSendCode01">
                                <i class="c-icon c-icon--post-bill" aria-hidden="true"></i>
                                <span>우편 명세서</span>
                            </label>
                        </div>

                                <p class="c-bullet c-bullet--dot u-co-gray u-fs-16 u-mt--16 _isTeen">
                                    미성년의 경우 MMS명세서 선택이 불가합니다. 희망하시는 경우 개통 후 고객센터로 문의 부탁드립니다.</p>

                                <p class="c-bullet c-bullet--dot u-co-gray u-fs-16 u-mt--16 _isCommon">
                                    이메일, 모바일(MMS) 명세서를 선택 하시면 청구내역을 빠르고 정확하게 받아보실 수 있습니다.</p>

                    </div>
                </div>
                <div class="c-form-wrap u-mt--48">
                    <div class="c-form-group" role="group" aira-labelledby="radPayMethodTitle">
                        <h5 class="c-form__title--type2" id="radPayMethodTitle">요금 납부 방법</h5>
                        <div class="c-checktype-row c-col2 u-mt--0">
                            <input class="c-radio c-radio--button" id="reqPayType01" value="D" type="radio" name="reqPayType" checked  >
                            <label class="c-label" for="reqPayType01">자동이체</label>
                            <input class="c-radio c-radio--button" id="reqPayType02" value="C"  type="radio" name="reqPayType"  >
                            <label class="c-label" for="reqPayType02">신용카드</label>
                        </div>
                    </div>
                </div>
                <!-- case : 자동이체 선택 시-->
                <div class="c-form-wrap _bank">
                    <div class="c-form-group" role="group" aria-labelledby="inpAccountInfoHead">
                        <h5 class="c-form__title--type2" id="inpAccountInfoHead">계좌정보</h5>
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__select u-mt--0">
                                    <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />
                                    <select id="reqBank" class="c-select">
                                        <option value="">은행명 선택</option>
                                        <c:forEach items="${bankList }" var="itemBank" >
                                            <option value="${itemBank.dtlCd }"  >${itemBank.dtlCdNm }</option>
                                        </c:forEach>
                                    </select>
                                    <label class="c-form__label" for="reqBank">은행명</label>
                                </div>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__input u-mt--0">
                                    <input class="c-input onlyNum" id="reqAccountNumber" type="number" placeholder="'-'없이 입력" value="${AppformReq.reqAccountNumber }" >
                                    <label class="c-form__label" for="reqAccountNumber">계좌번호</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-box c-box--type1 c-box--bullet">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item">납부방법은 본인명의의 카드 또는 계좌로만 등록 가능합니다.(미성년자의 경우 법정대리인 명의 포함)</li>
                            <li class="c-text-list__item">매월 21일 납부</li>
                            <li class="c-text-list__item">평생계좌(개인 핸드폰번호를 계좌번호로 사용하는 계좌)는 이용 불가능합니다.</li>
                        </ul>
                    </div>
                </div>
                <div class="c-button-wrap u-mt--40 _bank">
                    <button id="btnCheckAccount" class="c-button c-button--md c-button--mint c-button--w460">계좌번호 유효성 체크</button>
                </div>


                <!-- case : 신용카드 선택 시-->
                <div class="c-form-wrap u-mt--48 _card" style="display:none">
                    <div class="c-form-group" role="group" aria-labelledby="inpcardInfoHead">
                        <h5 class="c-form__title--type2" id="inpcardInfoHead">신용카드 정보</h5>
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input u-width--460 u-mt--0">
                                    <input class="c-input onlyNum" id="reqCardNo" type="number" maxlength="16" placeholder="'-'없이 입력" value="${AppformReq.reqCardNo }">
                                    <label class="c-form__label" for="reqCardNo">카드번호</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="c-form-wrap u-mt--48 _card" style="display:none">
                    <div class="c-form-group" role="group" aria-labelledby="inpcardTermHead">
                        <h5 class="c-form__title--type2" id="inpcardTermHead">신용카드 유효기간</h5>
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__select u-mt--0">
                                    <select class="c-select" id="reqCardMm">
                                        <option value="" ></option>
                                        <option value="01"  <c:if test="${AppformReq.reqCardMm eq '01' }" > selected </c:if> >1월</option>
                                        <option value="02"  <c:if test="${AppformReq.reqCardMm eq '02' }" > selected </c:if> >2월</option>
                                        <option value="03"  <c:if test="${AppformReq.reqCardMm eq '03' }" > selected </c:if> >3월</option>
                                        <option value="04"  <c:if test="${AppformReq.reqCardMm eq '04' }" > selected </c:if> >4월</option>
                                        <option value="05"  <c:if test="${AppformReq.reqCardMm eq '05' }" > selected </c:if> >5월</option>
                                        <option value="06"  <c:if test="${AppformReq.reqCardMm eq '06' }" > selected </c:if> >6월</option>
                                        <option value="07"  <c:if test="${AppformReq.reqCardMm eq '07' }" > selected </c:if> >7월</option>
                                        <option value="08"  <c:if test="${AppformReq.reqCardMm eq '08' }" > selected </c:if> >8월</option>
                                        <option value="09"  <c:if test="${AppformReq.reqCardMm eq '09' }" > selected </c:if> >9월</option>
                                        <option value="10"  <c:if test="${AppformReq.reqCardMm eq '10' }" > selected </c:if> >10월</option>
                                        <option value="11"  <c:if test="${AppformReq.reqCardMm eq '11' }" > selected </c:if> >11월</option>
                                        <option value="12"  <c:if test="${AppformReq.reqCardMm eq '12' }" > selected </c:if> >12월</option>
                                    </select>
                                    <label class="c-form__label" for="reqCardMm">MM</label>
                                </div>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__select u-mt--0">
                                    <fmt:formatDate value="${nmcp:getDateToCurrent(0)}" pattern="yyyy" var="nowYear" />
                                    <select class="c-select" id="reqCardYy">
                                        <option value=""> </option>
                                        <c:forEach begin="0" end="10" var="item">
                                            <option value="${nowYear+item}" <c:if test="${AppformReq.reqCardYy eq (nowYear+item) }" > selected </c:if> >${nowYear+item}년</option>
                                        </c:forEach>
                                    </select>
                                    <label class="c-form__label" for="reqCardYy">YY</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-box c-box--type1 u-mt--20">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-co-gray">납부방법은 본인명의의 카드 또는 계좌로만 등록 가능합니다.(미성년자의 경우 법정대리인 명의 포함)</li>
                            <li class="c-text-list__item u-co-gray">1회차 매월 10~11일경</li>
                        </ul>
                    </div>
                </div>
                <div class="c-button-wrap u-mt--40 _card" style="display:none">
                    <!-- 유효성 체크 완료 시 클래스 .is-complete 추가-->
                    <button id="btnCheckCardNo" class="c-button c-button--md c-button--mint c-button--w460">신용카드 유효성 체크</button>
                </div>
            </div>
            <!-- STEP5 END -->
            <div id="divBtnGroup1"  class="c-button-wrap u-mt--56">
                <button id="btnNext" class="c-button c-button--lg c-button--primary c-button--w460 is-disabled _btnNext">다음</button>
            </div>

            <div id="divBtnGroup2" class="c-button-wrap u-mt--56" style="display:none">
                <!-- 비활성화 시 .is-disabled 클래스 추가-->
                <a id="btnBefore" class="c-button c-button--lg c-button--gray u-width--220 " href="javascript:void(0)">이전</a>
                <a id="btnNext2" class="c-button c-button--lg c-button--primary u-width--220 is-disabled _btnNext" href="javascript:void(0)">다음</a>
            </div>
        </div>

    </t:putAttribute>
    <t:putAttribute name="popLayerAttr">

        <div class="c-bs-compare" id="bottom_sheet">
            <button class="c-bs-compare__openner" id="bs_acc_btn" type="button">
                <span class="sr-only">비교함 상세 열기|비교함 상세 닫기</span>
            </button>
            <div class="c-bs-compare__container">
                <div class="c-bs-compare__inner">
                    <div class="c-bs-compare__preview" aria-hidden="false">
                        <div class="u-h--100 c-flex c-flex--jfy-between">
                            <div class="c-flex u-ta-left">

                                <div class="inner-box">
                                    <p class="c-text c-text--fs18 u-fw--medium" id="bottomTitle" ><!-- 갤럭시 A32 / 64GB / 블랙 --></p>
                                    <p class="c-text c-text--fs16 u-co-gray" id="bottomTitle2"><!-- 모두다 맘껏 15GB+ --></p>
                                </div>
                            </div>
                            <div class="fee-compare-wrap">
                                <div class="fee-compare-wrap__item">
                                    <div class="u-co-red">
                                        <span class="c-text c-text--fs14">월 납부금액</span>
                                        <span class="c-text c-text--fs32 c-text--bold u-ml--12" id="paySumMnthTxt2">0</span>
                                        <span class="c-text c-text--fs18 c-text--bold">원</span>
                                    </div>
                                    <p class="c-text c-text--fs13 u-co-gray">가입비 및 유심비 등 기타요금은 별도 청구됩니다</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-bs-compare__detail" id="acc_content_1" aria-hidden="true">
                        <!-- <div class="top-sticky-banner">
                          <div class="swiper-banner" id="swiperBanner">
                            <div class="swiper-container">
                              <div class="swiper-wrapper">
                                <div class="swiper-slide">
                                  <img src="/resources/images/portal/content/img_sticky_banner.png" alt="">
                                </div>
                                <div class="swiper-slide">
                                  <img src="/resources/images/portal/content/img_sticky_banner.png" alt="">
                                </div>
                                <div class="swiper-slide">
                                  <img src="/resources/images/portal/content/img_sticky_banner.png" alt="">
                                </div>
                              </div>
                            </div>
                            <button class="swiper-button-next" type="button"></button>
                            <button class="swiper-button-prev" type="button"></button>
                          </div>
                        </div> -->
                        <div class="c-row c-col2-row c-scrolly-auto">
                            <div class="c-col2-left">
                                <div class="c-addition-wrap">
                                    <!-- [2022-01-20] 마크업 수정-->
                                    <strong class="c-addition c-addition--type1">가입정보</strong>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>가입유형</dt>
                                        <dd class="u-ta-right" id="ddOperTypeNm"><!-- 번호이동 --></dd>
                                    </dl>

                                    <c:if test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_PHONE  }">

                                        <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                                        <dl class="c-addition c-addition--type1">
                                            <dt>월 단말요금(A)</dt>
                                            <dd class="u-ta-right">
                                                <b id="payMnthAmtTxt">-</b>원
                                            </dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2">
                                            <dt>단말기 출고가</dt>
                                            <dd class="u-ta-right" id="hndstAmtTxt">- 원</dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2">
                                            <dt>공통지원금</dt>
                                            <dd class="u-ta-right">
                                                <b>
                                                    <em id="subsdAmtTxt">- 원</em>
                                                </b>
                                            </dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2">
                                            <dt>추가지원금</dt>
                                            <dd class="u-ta-right">
                                                <b>
                                                    <em id="agncySubsdAmtTxt">- 원</em>
                                                </b>
                                            </dd>
                                        </dl>

                                        <dl class="c-addition c-addition--type2">
                                            <dt>할부원금</dt>
                                            <dd class="u-ta-right" id="instAmtTxt">- 원</dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2">
                                            <dt>총할부수수료</dt>
                                            <dd class="u-ta-right" id="totalInstCmsnTxt">- 원</dd>
                                        </dl>
                                    </c:if>


                                    <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                                    <dl class="c-addition c-addition--type1">
                                        <dt>월 통신요금(B)</dt>
                                        <dd class="u-ta-right">
                                            <b id="payMnthChargeAmtTxt">-</b>원
                                        </dd>
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>기본요금</dt>
                                        <dd class="u-ta-right" id="baseAmt">- 원</dd>
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>기본할인</dt>
                                        <dd class="u-ta-right">
                                            <b>
                                                <em id="dcAmt">-원</em>
                                            </b>
                                        </dd>
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>요금할인</dt>
                                        <dd class="u-ta-right">
                                            <b>
                                                <em id="addDcAmt">-원</em>
                                            </b>
                                        </dd>
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>프로모션 할인</dt>
                                        <dd class="u-ta-right">
                                            <b>
                                                <em id="promotionDcAmtTxt">- 원</em>
                                            </b>
                                        </dd>
                                    </dl>
                                    <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                                    <dl class="c-addition c-addition--type1">
                                        <dt>기타요금</dt>
                                        <!-- <dd class="u-ta-right">
                                          <b>7,400</b>원
                                        </dd> -->
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>가입비(3개월 분납)</dt>
                                        <dd class="u-ta-right" id="joinPriceTxt">
                                            <!-- <span class="c-text u-td-line-through">7,200</span>(무료) -->
                                        </dd>
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>USIM(최초 1회)</dt>
                                        <dd class="u-ta-right" id="usimPriceTxt"><!-- 6,600 원 --></dd>
                                    </dl>
                                    <dl class="c-addition c-addition--type2 _fromMove">
                                        <dt>번호이동 수수료</dt>
                                        <dd class="u-ta-right">${nmcp:getFtranPrice()} 원</dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="c-col2-right">
                                <div class="c-box c-box--type1">
                                    <div class="c-addition-wrap">
                                        <dl class="c-addition c-addition--type1 c-addition--sum">
                                            <dt>
                                                <b>월 납부금액</b>
                                                <span class="c-text c-text--fs14 u-co-gray">(부가세 포함)</span>
                                            </dt>
                                            <dd class="u-ml--auto u-ta-right">
                                                <b id="paySumMnthTxt">-</b>원
                                            </dd>
                                        </dl><!-- [2022-01-20] 마크업 수정-->
                                        <p class="c-text c-text--fs15 u-co-gray" id="pRateNm">-</p>
                                        <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                            <li class="c-text-list__item u-co-gray">유심비용은 첫달에만 부과 됩니다.</li>
                                            <li class="c-text-list__item u-co-gray">월 납부금액은 부가서비스 등의 사용에 따라 추가금액이 합산되어 청구될 수 있습니다.</li>
                                            <li class="c-text-list__item u-co-gray">월 납부금액은 부가세 포함 금액입니다.</li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="c-button-wrap u-mt--48">
                                    <button id="btnNext3" class="c-button c-button--full c-button--primary _btnNext">다음</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="c-modal c-modal--xlg" id="bankAutDialog" role="dialog" aria-labelledby="#bankAutDialog__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="#bankAutDialog__title">계좌인증</h2>
                        <div class="c-stepper-wrap">
                            <div class="c-hidden">유심구매 총 2단계 중 현재 2단계(계좌정보 입력)</div>
                            <ul class="c-stepper">
                                <li class="c-stepper__item c-stepper__item--active">
                                    <span class="c-stepper__num">1</span>
                                    <span class="c-stepper__title">계좌정보 입력</span>
                                </li>
                                <li class="c-stepper__item">
                                    <span class="c-stepper__num">2</span>
                                </li>
                            </ul>
                        </div>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <h3 class="c-heading c-heading--fs20" id="AccountInfoHead">계좌정보 입력</h3>
                        <p class="c-text c-text--fs17 u-co-gray u-mt--12">본인인증에 필요한 정보를 입력해 주세요.
                            <br>PASS 인증을 받은 실명과 계좌 명의자가 동일해야 인증이 가능합니다.
                        </p>
                        <div class="c-form-wrap u-mt--48 u-mb--56">
                            <div class="c-form-group" role="group" aria-labelledby="AccountInfoHead">
                                <div class="c-form-row c-col2">
                                    <div class="c-form-field">
                                        <div class="c-form__select u-mt--0">
                                            <select id="reqBankAut" class="c-select">
                                                <option value="">선택</option>
                                                <c:forEach items="${bankList}" var="itemBank" >
                                                    <option value="${itemBank.dtlCd }">${itemBank.dtlCdNm }</option>
                                                </c:forEach>
                                            </select>
                                            <label class="c-form__label" for="reqBankAut">은행/증권사</label>
                                        </div>
                                    </div>
                                    <div class="c-form-field">
                                        <div class="c-form__input u-mt--0">
                                            <input class="c-input onlyNum" id="reqAccountAut" type="number" placeholder="'-'없이 입력">
                                            <label class="c-form__label" for="reqAccountAut">계좌번호</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="c-form-row c-col2 u-width--460">
                                    <div class="c-form-field">
                                        <div class="c-form__input">
                                            <input class="c-input" id="cstmrNameTemp" type="text" placeholder="이름 입력" value="" readonly>
                                            <label class="c-form__label" for="cstmrNameTemp">이름</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-modal__footer u-flex-center">
                        <div class="u-box--w460 c-button-wrap">
                            <button class="c-button c-button--full c-button--gray"  data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary" id="btnAutAccount">인증요청</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="c-modal c-modal--xlg" id="bankAutDialog2" role="dialog" aria-labelledby="#bankAutDialog2__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="bankAutDialog2__title">계좌인증</h2>
                        <div class="c-stepper-wrap">
                            <div class="c-hidden">유심구매 총 2단계 중 현재 2단계(인증번호 입력)</div>
                            <ul class="c-stepper">
                                <li class="c-stepper__item">
                                    <span class="c-stepper__num">1</span>
                                </li>
                                <li class="c-stepper__item c-stepper__item--active">
                                    <span class="c-stepper__num">2</span>
                                    <span class="c-stepper__title">인증번호 입력</span>
                                </li>
                            </ul>
                        </div>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <h3 class="c-heading c-heading--fs20">인증번호 입력</h3>
                        <p class="c-text c-text--fs17 u-co-gray u-mt--12">입력하신 계좌로 1원을 보내드렸습니다.
                            <br>계좌 거래내역에서 1원의 입금자로 표시된 숫자 6자리를 입력해 주시기 바랍니다.
                        </p>
                        <div class="c-form-wrap u-mt--48">
                            <div class="c-form-group" role="group" aira-labelledby="chkValUsimTitle">
                                <h3 class="c-group--head c-hidden" id="chkValUsimTitle">유심 유효성 체크</h3>
                                <div class="u-box--w460 u-margin-auto">
                                    <div class="c-form">
                                        <!--[2022-02-07] aria-hidden 속성 추가 및 이미지 변경-->
                                        <div class="c-img-wrap" aria-hidden="true">
                                            <img src="/resources/images/portal/content/img_account_certification.png" alt="">
                                        </div><!-- 인증 성공 시 .has-safe, 인증 실패 시 .has-error 추가-->
                                        <div class="c-form-field">
                                            <div class="c-form__input">
                                                <input class="c-input onlyNum" id="textOpt" type="text" placeholder="숫자 6자리" maxlength="6">
                                                <label class="c-form__label" for="textOpt">인증번호</label>
                                            </div><!-- 에러 설명 필요할때 -->
                                            <!-- p.c-bullet.c-bullet--dot.u-co-gray 설명                 -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-modal__footer u-flex-center">
                        <div class="u-box--w460 c-button-wrap">
                            <button class="c-button c-button--full c-button--gray" id="confirm_and_close" data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary" id="btnCheckAccountOtpConfirm">인증요청</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="c-modal c-modal--xlg" id="bankAutDialog3" role="dialog" aria-labelledby="#bankAutDialog3__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="bankAutDialog3__title">계좌인증</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <!-- [2022-01-20] 간격조정 유틸 클래스 추가-->
                        <div class="complete u-mt--80 u-mb--64 u-ta-center">
                            <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
                            <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                                <b>인증 완료</b>
                            </h3>
                            <p class="c-text c-text--fs20 u-co-gray u-mt--16"> 계좌 인증이 완료되었습니다.
                                <br>확인 버튼을 누른 뒤 신청서를 계속 작성해주시기 바랍니다.
                            </p>
                        </div><!-- [2022-01-20] .u-flex-center 클래스 삭제-->
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>



        <!--부가서비스 -->
        <div class="c-modal c-modal--md" id="addServiceModal" role="dialog" tabindex="-1" aria-labelledby="#addServiceModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="addServiceModalTitle">부가서비스 선택</h2>
                        <button class="c-button _btnClose" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <h3 class="c-heading c-heading--fs20 c-heading--medium u-ta-left">무료 부가서비스</h3>
                        <div class="c-accordion c-accordion--type4 u-mt--24" data-ui-accordion>
                            <ul class="c-accordion__box" id="ulAdditionListFree">
                                <!-- <li class="c-accordion__item">
                                  <div class="c-accordion__head">
                                    <button class="runtime-data-insert c-accordion__button" id="acc_headerA1" type="button" aria-expanded="false" aria-controls="acc_contentA1">
                                    </button>
                                    <div class="c-accordion__check">
                                      <input class="c-checkbox c-checkbox--type3" id="chkServA1" type="checkbox" name="chkServ">
                                      <label class="c-label" for="chkServA1">무선인터넷 차단</label>
                                    </div>
                                  </div>
                                  <div class="c-accordion__panel expand c-expand--pop" id="acc_contentA1" aria-labelledby="acc_headerA1">
                                    <div class="c-accordion__inside">무선인터넷 차단 내용</div>
                                  </div>
                                </li> -->

                            </ul>
                        </div>
                        <h3 class="c-heading c-heading--fs20 c-heading--medium u-ta-left u-mt--36">유료 부가서비스</h3>
                        <div class="c-accordion c-accordion--type4 u-mt--24" data-ui-accordion>
                            <ul class="c-accordion__box" id="ulAdditionListPrice">
                                <!-- <li class="c-accordion__item">
                                  <div class="c-accordion__head">
                                    <button class="runtime-data-insert c-accordion__button" id="acc_headerB1" type="button" aria-expanded="false" aria-controls="acc_contentB1">
                                    </button>
                                    <div class="c-accordion__check">
                                      <input class="c-checkbox c-checkbox--type3" id="chkServB1" type="checkbox" name="chkServ">
                                      <label class="c-label" for="chkServB1">링투유</label>
                                    </div>
                                    <span class="c-accordion__amount">990원</span>
                                  </div>
                                  <div class="c-accordion__panel expand c-expand--pop" id="acc_contentB1" aria-labelledby="acc_headerB1">
                                    <div class="c-accordion__inside">링투유 내용</div>
                                  </div>
                                </li> -->
                            </ul>
                        </div>
                    </div>

                    <div class="c-accordion c-accordion--type3" style="display:none">
                        <ul class="c-accordion__box" id="ulAdditionListBase">
                            <!-- <li class="c-accordion__item">
                                <div class="c-accordion__head">
                                  <div class="c-accordion__check">
                                    <input class="c-checkbox c-checkbox--type3" id="chkAS1" type="checkbox" name="chkAS">
                                    <label class="c-label" for="chkAS1">무선인터넷 차단</label>
                                  </div>
                                  <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#chkASP1">
                                    <div class="c-accordion__trigger"> </div>
                                  </button>
                                </div>
                                <div class="c-accordion__panel expand c-expand" id="chkASP1">
                                  <div class="c-accordion__inside">
                                    <div class="c-text c-text--type3">무선인터넷 차단 내용</div>
                                  </div>
                                </div>
                            </li> -->
                        </ul>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--gray u-width--220 _btnClose"  data-dialog-close>취소</button>
                            <button id="btnSetAddition" class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!--안심 보험  -->
        <div class="c-modal c-modal--xlg" id="insrProdDialog" role="dialog" aria-labelledby="#insrProdDialogtitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="insrProdDialogtitle">휴대폰안심보험 신청/변경</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body c-modal__body--full u-pt--0">
                        <div class="c-form u-width--300 u-mt--32 u-ml--80">
                            <input class="c-radio c-radio--button mark-check" id="insrProdCD_0" type="radio" name="insrProdCD" checked>
                            <label class="c-label" for="insrProdCD_0">미가입</label>
                        </div>
                        <div class="product">
                            <div class="c-card-col c-card-col--3" id="divInsrProdList">


                                <!-- <div class="c-card c-card--type2">
                                  <input class="c-radio c-radio--type4" id="radioT1" type="radio" name="radioT">
                                  <label class="c-card__label" for="radioT1">
                                    <span class="c-hidden">선택</span>
                                  </label>
                                  <div class="c-card__title">
                                    <strong>스마트 고가형</strong>
                                    <ul class="c-card__sub">
                                      <li>단말결합 안드로이드형</li>
                                    </ul>
                                  </div>
                                  <div class="c-card__price-wrap">
                                    <i class="c-icon c-icon--android" aria-hidden="true"></i>
                                    <div class="c-card__price">
                                      <span class="c-card__price-title">월 이용료</span>
                                      <b>2,800 원</b>
                                    </div>
                                  </div>
                                  <div class="c-card__content">
                                    <dl class="product-desc">
                                      <dt>설명</dt>
                                      <dd>안드로이드(삼성 등) 단말기와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                      <dt>보상범위<span class="c-text-label c-text-label--orange">중고단말 최고가 보장</span>
                                      </dt>
                                      <dd>최대 80만 원 보장</dd>
                                    </dl>
                                    <div class="c-text-label-wrap">
                                      <span class="c-text-label c-text-label--gray">분실</span>
                                      <span class="c-text-label c-text-label--gray">도난</span>
                                      <span class="c-text-label c-text-label--gray">화재</span>
                                      <span class="c-text-label c-text-label--gray">파손</span>
                                      <span class="c-text-label c-text-label--gray">침수</span>
                                    </div>
                                  </div>
                                </div> -->



                            </div>
                        </div>
                    </div>
                    <div class="c-modal__footer u-flex-center">
                        <div class="u-box--w460 c-button-wrap">
                            <button class="c-button c-button--full c-button--gray"  data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary" id="btnInsrProdConfirm" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--안심보험 서비스 메뉴얼  -->
        <div class="c-modal c-modal--md" id="productManualModal" role="dialog" tabindex="-1" aria-describedby="#productManualModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="productManualModalTitle">이동통신 단말기 보험 상품 설명서</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body u-ta-left">
                        <p class="c-text c-text--fs17 u-co-gray">본 상품은 주식회사 케이티엠모바일의 휴대폰 안심 서비스에 가입하신 고객을 위해, 주식회사 케이티엠모바일(보험계약자)이 휴대폰 안심 서비스 고객들을 피보험자로 하여 DB손해보험 (주)에 가입한 이동통신단말기보험에 대한 상품설명서입니다.</p>
                        <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">USIM형 자급제 전용(안드로이드/아이폰)</h3>
                        <p class="c-text c-text--fs15 u-mt--32">1. 가입 가능 기간 : 신규 단말기 구매일 기준 45일 이내(구매영수증 발송 필수)</p>
                        <p class="c-text c-text--fs15 u-mt--24">2. 가입대상 단말기 종류 및 가입상품 명칭</p>
                        <div class="c-table u-mt--16">
                            <table>
                                <caption>가입대상 단말기 종류 및 가입상품 명칭 - 가입대상 단말기 유형, 가입플랜 명칭으로 구성됨</caption>
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
                        <p class="c-text c-text--fs15 u-mt--24">3. 보상범위</p>
                        <div class="c-table u-mt--16">
                            <table>
                                <caption>보상범위 - 가입플랜 명칭, 보상범위로 구성됨</caption>
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
                        <p class="c-text c-text--fs15 u-mt--24">4. 최대 가입금액(보험 가입 금액) 및 월 서비스 이용료</p>
                        <div class="c-table u-mt--16">
                            <table>
                                <caption>최대 가입금액(보험 가입 금액) 및 월 서비스 이용료 - 가입플랜 명칭, 최대 가입금액, 월 서비스 이용료(부가세 없음)으로 구성됨</caption>
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
                        <p class="c-text c-text--fs15 u-mt--24">5. 자기부담금</p>
                        <div class="c-table u-mt--16">
                            <table>
                                <caption>자기부담금 - 가입플랜 명칭, 자기부담금으로 구성됨</caption>
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
                        <p class="c-text c-text--fs15 u-mt--24">6. 보상한도 액</p>
                        <p class="c-text c-text--fs14 u-co-gray u-mt--16">가입시점의 휴대폰 출고가와 최대 가입금액 중 낮은 금액에서 자기 부담금을 차감한 금액</p>
                        <p class="c-text c-text--fs15 u-mt--24">7. 보험기간</p>
                        <p class="c-text c-text--fs14 u-co-gray u-mt--16">최대 가입기간 : 36개월
                        <ul class="c-text-list c-bullet c-bullet--fyr">
                            <li class="c-text-list__item u-fs-13 u-co-gray">보험개시일로부터 36개월 경과시점 또는 이동통신사 포괄계약기간의 만료일 중 먼저 도래하는 일시까지(포괄계약기간이 갱신될 경우 해당되지 않음)</li>
                            <li class="c-text-list__item u-fs-13 u-co-gray">보험개시일은 서비스 가입일로부터 익일 00시 이후로 합니다.
                                <br/><span class="u-co-red">(단, 본 상품 가입 익일 0시 이후 통화내역 또는 모바일인증이 있어야 보험사의 책임(보상효력)이 개시됩니다.)</span>
                            </li>
                        </ul>
                        </p>
                        <h3 class="c-heading c-heading--fs20 c-heading--medium u-mt--48">단말결합 및 USIM형(단말 전 기종, 중고단말 포함)</h3>
                        <p class="c-text c-text--fs15 u-mt--32">1. 가입 가능 기간 : 신규/기변 개통 후 45일 이내</p>
                        <p class="c-text c-text--fs15 u-mt--24">2. 가입대상 단말기 종류 및 가입상품 명칭</p>
                        <div class="c-table u-mt--16">
                            <table>
                                <caption>가입대상 단말기 종류 및 가입상품 명칭 - 가입대상 단말기 유형, 가입플랜 명칭</caption>
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
                        <p class="c-text c-text--fs15 u-mt--24">3. 보상범위</p>
                        <div class="c-table u-mt--12">
                            <table>
                                <caption>보상범위 - 가입플랜 명칭, 보상범위로 구성됨</caption>
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
                        <p class="c-text c-text--fs15 u-mt--24">4. 최대 가입금액(보험 가입 금액) 및 월 서비스 이용료</p>
                        <p class="c-text c-text--fs14 u-co-gray u-mt--16">가입시점의 가입 단말기 출고가와 아래 상품별 금액 중 낮은 금액
                        <div class="c-table u-mt--12">
                            <table>
                                <caption>최대 가입금액(보험 가입 금액) 및 월 서비스 이용료 - 가입플랜 명칭, 최대 가입금액, 월 서비스 이용료(부가세 없음)으로 구성됨</caption>
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
                        </p>
                        <p class="c-text c-text--fs15 u-mt--24">5. 자기부담금</p>
                        <div class="c-table u-mt--12">
                            <table>
                                <caption>자기부담금 - 가입플랜 명칭, 자기부담금으로 구성됨</caption>
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
                        <p class="c-text c-text--fs15 u-mt--24">6. 보상한도 액</p>
                        <p class="c-text c-text--fs14 u-co-gray u-mt--16">가입시점의 휴대폰 출고가와 최대 가입금액 중 낮은 금액에서 자기 부담금을 차감한 금액</p>
                        <p class="c-text c-text--fs15 u-mt--24">7. 보험기간</p>
                        <p class="c-text c-text--fs14 u-co-gray u-mt--16">최대 가입기간 : 36개월
                        <ul class="c-text-list c-bullet c-bullet--fyr">
                            <li class="c-text-list__item u-fs-13 u-co-gray">보험개시일로부터 36개월 경과시점 또는 이동통신사 포괄계약기간의 만료일 중 먼저 도래하는 일시까지(포괄계약기간이 갱신될 경우 해당되지 않음)</li>
                            <li class="c-text-list__item u-fs-13 u-co-gray">
                                <p>보험개시일은 서비스 가입일로부터 익일 00시 이후로 합니다.</p>
                                <p class="u-co-red">(단, 본 상품 가입 익일 0시 이후 통화내역 또는 모바일인증이 있어야 보험사의 책임(보상효력)이 개시됩니다.)</p>
                            </li>
                        </ul>
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <!-- 자급제 보상 서비스 상품 설명서 (모달) -->
        <div class="c-modal c-modal--xlg" id="rwdManualModal" role="dialog" tabindex="-1" aria-describedby="#rwdManualModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="rwdManualModalTitle">자급제 보상 서비스 상품 설명서</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body u-ta-left">
                        <p class="c-text c-text--fs17 u-co-gray">
                            kt M mobile “자급제 보상 서비스” 는 위니아에이드가 제공하는 서비스입니다.
                            최신 자급제 스마트폰을 구매한 고객이 kt M mobile 이동전화서비스 가입 및 본 서비스 가입 18개월 후 새 단말기를 구매하여 kt M mobile 이동전화를 유지하고
                            보상기간 중 사용하던 기존 단말[kt M mobile 자급제보상서비스 가입시 등록한 스마트폰]을 반납하는 경우, 위니아에이드가 개월차별 책정된 보상률(최대50%)에 따라 중고폰 매입가격을
                            현금으로 보장해 드립니다.
                        </p>
                        <p class="c-text c-text--fs15 u-mt--32">1. 가입 가능 기간 : 스마트폰 구매 후 90일 이내 가입 가능 (단말 구매 영수증 기준)</p>
                        <p class="c-text c-text--fs15 u-mt--24">2. 가입상품 명칭 및 월 서비스 이용료</p>
                        <div class="c-table u-mt--8 u-mb--24">
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
                        <p class="c-text c-text--fs15 u-mt--24">3. 중고폰 매입가격 보장금액: ‘구매가’ x ‘개월 차’ 에 따라 책정된 보장율 (* 보장율은 개월차별로 경감 됨)</p>
                        <div class="c-table u-mt--8 u-mb--24">
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
                        <p class="c-text c-text--fs15 u-mt--24">4. 보장기간: 18개월 약정</p>
                        <p class="c-bullet c-bullet--dot">서비스 가입 18개월 후 19개월 차 ~ 21개월차(3개월 간)</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- 자급제 보상 서비스 상품 팝업 -->
        <div class="c-modal c-modal--xlg" id="rwdProdDialog" role="dialog" aria-labelledby="#rwdProdDialogtitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="rwdProdDialogtitle">자급제 보상 서비스 신청/변경</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body c-modal__body--full u-pt--0">
                        <div class="c-form u-width--300 u-mt--32 u-ml--80">
                            <input class="c-radio c-radio--button mark-check" id="rwdProdCD_0" type="radio" name="rwdProdCD" checked>
                            <label class="c-label" for="rwdProdCD_0">미가입</label>
                        </div>
                        <div class="product">
                            <div class="c-card-col c-card-col--3" id="divRwdProdList">
                                <!-- 자급제 보상 서비스 상품 리스트 -->
                            </div>
                        </div>
                    </div>
                    <div class="c-modal__footer u-flex-center">
                        <div class="u-box--w460 c-button-wrap">
                            <button class="c-button c-button--full c-button--gray"  data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary" id="btnRwdProdConfirm" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- imei image upload guide -->
        <div class="c-modal c-modal--lg" id="uploadguide-modal" role="dialog" aria-labelledby="uploadguide-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="uploadguide-modal__title">이미지 업로드 가이드</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="esim-guide-box__pop">
                            <ul class="esim-guide__list">
                                <li class="esim-guide__item">
                                    <span class="Number-label u-ml--m32">1</span>
                                    통화창에 *#06#입력
                                </li>
                                <li class="esim-guide__item">
                                    <span class="Number-label u-ml--32">2</span>
                                    이미지 캡쳐본 업로드
                                </li>
                            </ul>
                            <img src="/resources/images/portal/esim/eSIM_guide_pop.png" alt="1.통화창에 *#06#입력, 2.화면 캡쳐 정보의 가이드 이미지">
                        </div>
                        <div class="c-box c-box--type1 u-mt--16">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">이미지 업로드 시 각 항목이 자동으로 입력됩니다.</li>
                            </ul>
                        </div>
                        <div class="c-button-wrap u-mt--48">
                            <button class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- //imei image upload guide -->

        <!-- imei image register popup -->
        <div class="c-modal c-modal--lg" id="imei-check-modal" role="dialog" aria-labelledby="imei-check-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">

                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="imei-check-modal__title">IMEI 사용 슬롯 확인</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>

                    <div class="c-modal__body">
                        <div class="esim-slot-box">
                            <ul class="esim-slot-box__list">
                                <li class="u-co-red">휴대폰 정보가 올바른지 반드시 확인하시기 바랍니다.</li>
                                <li>이미지에서 추출된 숫자가 정확하지 않으면 <span>다시 촬영해서 업로드</span> 시도 해 주시거나 고객님께서 <span>직접 해당 번호를 입력</span> 해 주십시오.</li>
                            </ul>
                        </div>
                        <div class="c-table">
                            <table>
                                <caption>구분, IMEI 정보</caption>
                                <colgroup>
                                    <col style="width:30%;">
                                    <col>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th scope="col">구분</th>
                                    <th scope="col">IMEI</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>IMEI1</td>
                                    <td>
                                        <div class="c-form">
                                            <input type="text" class="c-input numOnly" id="imei1Txt" name="imei1Txt" value="" maxlength="15">
                                            <label class="c-form__label c-hidden" for="imei1Txt">imei1Txt</label>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>IMEI2</td>
                                    <td>
                                        <div class="c-form">
                                            <input type="text" class="c-input numOnly" id="imei2Txt" name="imei2Txt" value="" maxlength="15">
                                            <label class="c-form__label c-hidden" for="imei2Txt">imei2Txt</label>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <!-- imei 이미지 에러 메세지 효출 영역 -->
                        <div class="esim-slot-box__err" id="imeiErrTxt" style="display:none;"></div>
                    </div>

                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--gray u-width--220" id="imeiReSetBtn" data-dialog-close>다시 등록하기</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220" id="imeiSetBtn">확인</button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <!-- // imei image register popup -->

        <div class="c-modal c-modal--xlg" id="simpleDialog" role="dialog" aria-labelledby="#simpleTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title _title" id="simpleTitle">오류 TITLE</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body _detail">

                    </div>
                    <div class="c-modal__footer">
                        <div id="divDefaultButton"  class="c-button-wrap _simpleDialogButton">
                            <button class="c-button c-button--lg c-button--primary u-width--220 _btnCheck" data-dialog-close>확인</button>
                        </div>
                        <div id="divChangeButton" class="c-button-wrap _simpleDialogButton">
                            <button id="btnOnline" class="c-button c-button--lg c-button--gray u-width--220" >해피콜 신청서 작성</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 _btnCheck" data-dialog-close>확인</button>
                        </div>
                        <div id="divCheckCaution" class="c-button-wrap _simpleDialogButton" style="flex-direction: column;">
                            <div class="c-agree__item" style="padding-left:4rem;">
                                <input class="c-checkbox" id="cautionFlag" type="checkbox">
                                <label class="c-label" for="cautionFlag">위 사항을 읽고, 이해하였습니다.</label>
                            </div>
                            <button class="c-button c-button--lg c-button--primary u-width--220 _btnCheck" id="cautionCheck">확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
     <div class="c-modal c-modal--md" id="regServiceModal" role="dialog" aria-labelledby="regServiceModalTitle">
        <div class="c-modal__dialog" role="document">
          <div class="c-modal__content">
            <div class="c-modal__header">
              <h2 class="c-modal__title" id="regServiceModalTitle" >통화중대기 설정/해제 (무료)</h2>
              <button class="c-button" data-dialog-close>
                <i class="c-icon c-icon--close" aria-hidden="true"></i>
                <span class="c-hidden">팝업닫기</span>
              </button>
            </div>
            <div class="c-modal__body">
                <ul class="c-text-list c-bullet c-bullet--dot">
                    <li class="c-text-list__item u-fs-16 u-mt--0">통화중대기 서비스를 정상적으로 이용 및 해지하려면 아래의 절차를 진행해주세요.<br /><span class="u-co-red">(절차가 완료되지 않으면 서비스가 정상적으로 반영되지 않습니다)</span></li>
                    <li class="c-text-list__item u-fs-16 u-mt--24">
                            서비스 신청방법
                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                            <li class="c-text-list__item u-co-gray u-fs-16">설정: *+40+통화→ “서비스가 정상적으로 설정되었습니다” → 종료</li>
                            <li class="c-text-list__item u-co-gray u-fs-16">해제: *+400+통화→ “서비스가 정상적으로 해제되었습니다” → 종료</li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="c-modal__footer">
              <div class="c-button-wrap">
                <button class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>확인</button>
              </div>
            </div>
          </div>
        </div>
    </div>

        

    </t:putAttribute>
</t:insertDefinition>
