<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<t:insertDefinition name="mlayoutOutFormFotter">

    <t:putAttribute name="titleAttr">
        <c:choose>
            <c:when test="${AppformReq.onOffType eq '7'}">
                셀프개통
            </c:when>
            <c:when test="${AppformReq.onOffType ne '7' && AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM}">
                유심 요금제 가입하기
            </c:when>
        </c:choose>
    </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?ver=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?ver=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js?ver=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js?ver=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/appJehuForm.js?ver=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js?ver=${jsver}"></script>
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
        <input type="hidden" id="reqBuyType" name="reqBuyType" value="${AppformReq.reqBuyType}">
        <input type="hidden" id="rprsPrdtId" name="rprsPrdtId" value="${AppformReq.rprsPrdtId}">
        <input type="hidden" id="prdtId" name="prdtId" value="${AppformReq.prdtId}">
        <input type="hidden" id="prdtNm" name="prdtNm" value="${AppformReq.prdtNm}">
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
        <input type="hidden" name="openMarketId" id="openMarketId" value="${AppformReq.openMarketId}" />
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
        <input type="hidden" id="jehuProdType" name="jehuProdType" value="${jehuProdType}" ><!--제휴처 요금제 타입 -->
        <input type="hidden" id="jehuProdName" name="jehuProdName" value="${jehuProdName}" ><!--제휴처 요금제 이름 -->
        <input type="hidden" id="jehuPartnerType" name="jehuPartnerType" value="${jehuPartnerType}" ><!--제휴사타입 -->
        <input type="hidden" id="jehuPartnerName" name="jehuPartnerName" value="${jehuPartnerName}" ><!--제휴사이름 -->
        <input type="hidden" id="openMarketReferer" name="openMarketReferer" value="${AppformReq.openMarketReferer}" ><!--유입경로 -->
        <input type="hidden" id="jehuRefererYn" name="jehuRefererYn" value="${jehuRefererYn}" ><!-- 제휴위탁온라인 유입여부 -->
        <input type="hidden" id="evntCdPrmt" name="evntCdPrmt" value="${AppformReq.evntCdPrmt}" ><!--이벤트코드 프로모션 -->
        <input type="hidden" id="recoUseYn" name="recoUseYn" value="${AppformReq.recoUseYn}" ><!--이벤트코드에 따른 추천인ID 노출여부 -->


        <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv" > <!--안면인증 결과 -->
        <input type="hidden" id="fathTrgYn" name="fathTrgYn" > <!--안면인증 대상여부 -->


        <div class="ly-content" id="main-content">

            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    <c:choose>
                        <c:when test="${AppformReq.onOffType eq '7'}">
                            셀프개통
                        </c:when>
                        <c:when test="${AppformReq.onOffType ne '7' && AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM}">
                            유심 요금제 가입하기
                        </c:when>
                    </c:choose>
                    <button class="header-clone__gnb"></button>
                </h2>
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

                <div class="c-form u-mt--24">
                    <div class="c-check-wrap">
                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType1" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NA}" desc="19세 이상 내국인" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NA }" > checked </c:if> >
                            <label class="c-label" for="cstmrType1">
                                <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                                <span>19세 이상 내국인</span>
                            </label>
                        </div>
                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType2" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NM}" desc="19세 미만 미성년자" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NM }" > checked </c:if> <c:if test="${AppformReq.onOffType eq '7' }" > disabled </c:if> >
                            <label class="c-label" for="cstmrType2">
                                <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                                <span>19세 미만 미성년자</span>
                            </label>
                        </div>
                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}" desc="외국인" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_FN }" > checked </c:if> <c:if test="${AppformReq.onOffType eq '7' }" > disabled </c:if>>
                            <label class="c-label" for="cstmrType3">
                                <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                                <span>외국인</span>
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

                <div class="c-form-field _isForeigner" style="display:none;">
                    <div class="c-form__input-group">
                        <div class="c-form__input c-form__input-division">
                            <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn01" name="cstmrForeignerRrn01" autocomplete="off" type="tel" maxlength="6" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,0,6)}" placeholder="외국인 등록번호 앞자리" title="외국인 등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrForeignerRrn02');" >
                            <span>-</span>
                            <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn02" name="cstmrForeignerRrn02" autocomplete="off" type="password" maxlength="7" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,6,13)}" placeholder="외국인 등록번호 뒷자리" title="외국인 등록번호 뒷자리">
                            <label class="c-form__label" for="cstmrForeignerRrn01">외국인 등록번호</label>
                        </div>
                    </div>
                </div>

                <div class="c-form-field _isDefault" >
                    <div class="c-form__input-group">
                        <div class="c-form__input c-form__input-division">
                            <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,0,6)}" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');">
                            <span>-</span>
                            <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="off" type="password" maxlength="7" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,6,13)}" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                            <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                        </div>
                    </div>
                </div>

                <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                    <div class="c-form _isDefaultText " style="display:none;">
                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                            <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                        </ul>
                    </div>
                </c:if>

                <div class="c-form _isTeen" style="display:none;">
                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                        <li class="c-text-list__item">영/유아(0세~4세 이하)의 경우 가입신청이 제한되므로 타 명의로 진행 바랍니다.</li>
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

                <div class="c-accordion c-accordion--type5 acc-agree">
                    <span class="c-form__title--type2">약관동의</span>
                    <div class="c-accordion__box">
                        <div class="c-accordion__item">
                            <div class="c-accordion__head">
                                <div class="c-accordion__check">
                                    <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                                    <label class="c-label" for="btnStplAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span></label>
                                </div>
                                <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeA1">
                                    <div class="c-accordion__trigger"></div>
                                </button>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_agreeA1">
                                <div class="c-accordion__inside">

                                    <c:if test="${ AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM }" >
                                        <div class="c-agree__item">
                                            <input  id="clauseMoveCode" code="${Constants.CLAUSE_MOVE_CODE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                            <label class="c-label" for="clauseMoveCode">번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법 동의<span class="u-co-gray">(필수)</span></label>
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseMoveCode');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MOVE_CODE}');" >
                                                <span class="c-hidden">상세보기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                    </c:if>

                                    <div class="c-agree__item">
                                        <input id="clausePriOfferFlag" code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                        <label class="c-label" for="clausePriOfferFlag">고유식별정보 수집&middot;이용 동의<span class="u-co-gray">(필수)</span></label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}');">
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>

                                    <div class="c-agree__item">
                                        <input id="clausePriCollectFlag" code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                                        <label class="c-label" for="clausePriCollectFlag">개인정보/신용정보 수집·이용 동의<span class="u-co-gray">(필수)</span></label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}');" >
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>

                                    <div class="c-agree__item">
                                        <input id="clauseEssCollectFlag" code="${Constants.CLAUSE_ESS_COLLECT_CODE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                                        <label class="c-label" for="clauseEssCollectFlag">개인정보 제3자 제공 동의<span class="u-co-gray">(필수)</span></label>
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
                                        <label class="c-label" for="notice">서비스 이용약관<span class="u-co-gray">(필수)</span></label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('notice');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}');" >
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>

                                    <c:if test="${AppformReq.prdtSctnCd eq PhoneConstant.FIVE_G_FOR_MSP}">
                                        <div class="c-agree__item">
                                            <input id="clause5gCoverage" code="${Constants.CLAUSE_5G_COVERAGE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                            <label class="c-label" for="clause5gCoverage">5g 커버리지 확인 및 가입 동의<span class="u-co-gray">(필수)</span></label>
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
                                        <label class="c-label" for="clauseJehuFlag">개인정보 제3자 제공 동의[${jehuProdName} 가입고객 필수 동의]<span class="u-co-gray">(필수)</span></label>
                                    </div>

                                    <div class="c-agree__item _clausePartnerOfferFlag" style="display:none;" >
                                        <button class="c-button c-button--xsm" id="clausePartnerOfferButton" >
                                            <span class="c-hidden">제휴사 정보 제공 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clausePartnerOfferFlag" type="checkbox"  code="clausePartnerOfferFlag" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="N" validityyn="Y" >
                                        <label class="c-label" for="clausePartnerOfferFlag">개인정보 제3자 제공 동의[${jehuPartnerName} 가입고객 필수 동의]<span class="u-co-gray">(필수)</span></label>
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
                                                            <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="othersAdReceiveAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_AD_RECEIVE_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" required-agree-id="agreeWrap5" validityyn="N">
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
            </div>
            <!-- // STEP1 END -->

            <!-- STEP2 START -->
            <div id="step2" style="display:none" class="_divStep" >
                <div class="c-form-step">
                    <span class="c-form-step__number">2</span>
                    <h3 class="c-form-step__title">유심정보·신분증 확인</h3>
                </div>

                <h4 class="c-form__title--type2 u-mt--50">유심정보</h4>
                <div class="c-check-wrap _noSelf">
                    <div class="c-chk-wrap">
                        <input class="c-radio c-radio--button" id="onOffLineUsim01" type="radio" name="onOffLineUsim" value="offLine" <c:if test="${AppformReq.clauseMpps35Flag eq 'N' }" > checked </c:if> >
                        <label class="c-label" for="onOffLineUsim01">유심 보유</label>
                    </div>
                    <div class="c-chk-wrap">
                        <input class="c-radio c-radio--button" id="onOffLineUsim02" type="radio" name="onOffLineUsim" value="onLine" <c:if test="${AppformReq.clauseMpps35Flag eq 'Y' }" > checked </c:if> >
                        <label class="c-label" for="onOffLineUsim02">유심 미보유</label>
                    </div>
                </div>

                <div id="divUsim" class="c-form">
                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2 _onLineUsim" id="ulOnOffLineUsimDesc">
                        <li class="c-text-list__item u-co-gray">
                            유심 미보유 고객님은 가입 신청 완료 후 아래 유심 구매처에서 유심을 구매하시기 바랍니다.
                            <br>전국 편의점 및 마트(CU, GS25, 세븐일레븐, 이마트24, 스토리웨이, 이마트, 홈플러스)
                            <br><a class="c-text c-text--underline u-co-sub-3" href="https://smartstore.naver.com/ktmmobile/products/4824197008" target="_blank">오픈마켓(네이버스마트스토어)</a>
                        </li>
                        <li class="c-text-list__item u-co-gray">가입 신청 완료하신 고객님께 개별적으로 안내 문자발송 예정입니다.</li>
                    </ul>
                </div>

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
                    <div class="c-form__input _offLineUsim">
                        <input class="c-input onlyNum" id="reqUsimSn" type="tel" maxlength="19" pattern="[0-9]*" placeholder="직접입력('-'없이 입력)" title="유심번호 입력" value="${AppformReq.reqUsimSn}">
                        <label class="c-form__label" for="reqUsimSn">유심번호 19자리</label>
                    </div>

                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2" id="ulOnOffLineUsimDesc">
                        <li class="c-text-list__item">유심번호 19자리를 입력해 주세요.</li>
                    </ul>
                    <div class="c-button-wrap u-mt--40 _offLineUsim">
                        <button id="btnUsimNumberCheck" class="c-button c-button--full c-button--h48 c-button--mint">유심번호 유효성 체크</button>
                    </div>
                </div>



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
                            <input class="c-radio c-radio--button" id="selfCertType2" value="02" type="radio" name="selfCertType"  >
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
                                    <input class="c-radio c-radio--button c-hidden" id="selfCertType5" value="05" type="radio" name="selfCertType" >
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
            </div>
            <!-- // STEP2 END -->

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
                                <c:if test="${moveObj.dtlCdDesc eq '01'}" >
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
                            <select class="c-select u-mt--8" id="moveCompanyGroup2">
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
                                    <li class="c-text-list__item">타사 신규개통/명의변경/번호이동 후 3개월 이내 고객은 셀프개통으로 번호이동이 불가하므로 온라인 가입신청을 부탁드립니다.</li>
                                </c:when>
                                <c:otherwise>
                                    <li class="c-text-list__item u-co-red">통신사를 모르실 경우 현재 사용중인 휴대폰에서 114로 확인 바랍니다.</li>
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
            <!-- // STEP3 END -->

            <!--  STEP4 START -->
            <div id="step4"  style="display:none" class="_divStep" >

                <div class="c-form-step">
                    <span class="c-form-step__number">4</span>
                    <h3 class="c-form-step__title">부가서비스 정보</h3>
                </div>
                <c:if test="${recommendFlagYn eq 'Y'}">
                    <h4 class="c-form__title--type2">친구초대 추천인ID<small>(선택)</small> </h4>
                <div class="c-form__group" role="group" aria-labeledby="inpG">
                    <div class="c-form__select">
                        <c:set var="recommendFlagList" value="${nmcp:getCodeList(Constants.RECOMMEND_FLAG_GRUP_CODE)}" />
                        <select id="recommendFlagCd" class="c-select">
                            <c:forEach items="${recommendFlagList}" var="recommendFlag" >
                                <option value="${recommendFlag.dtlCd}" <c:if test="${AppformReq.recommendFlagCd eq recommendFlag.dtlCd }" > selected </c:if> >${recommendFlag.dtlCdNm}</option>
                            </c:forEach>
                        </select>
                        <label class="c-form__label">추천인 정보</label>
                    </div>
                    <input id="recommendInfo" class="c-input" type="text" placeholder="직접입력" title="직접입력" value="${AppformReq.commendId}"  maxlength="20"  >
                    <input type="hidden" name="commendId" id="commendId"  value=""  title="추천 아이디"  >
                </div>

                    <p class="c-bullet c-bullet--dot u-co-gray">추천인ID 오/미입력 시 사은품 지급이 불가하니 정확히 입력 해주세요.</p>

                    <hr class="c-hr c-hr--type1 c-expand">
                </c:if>

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





                <hr class="c-hr c-hr--type1 c-expand _insrProdForm">
                <div class="c-title-wrap c-title-wrap--flex _insrProdForm">
                    <h4 class="c-heading c-heading--type5 u-fw--medium"> 휴대폰안심보험 신청<small>(선택)</small></h4>
                    <button id="btnInsrProd" class="c-button c-button--sm c-button--white" >신청/변경</button>
                </div>

                <div id="divInsrProdInfo" class="u-mt--32 _insrProdForm"  >
                    <button id="btnInsrProd2" class="c-button c-button--md c-button--gray c-button--full u-flex-center" >
                        <i class="c-icon c-icon--bang--yellow" aria-hidden="true" ></i>선택한 휴대폰안심보험 서비스가 없습니다.
                    </button>
                </div>

                <!--서비스 표시 박스 -->
                <ul class="c-card-list _insrProdList" style="display:none" >
                    <li class="c-card c-card--type3 var-type1" id="insrProdView" >
                    </li>
                </ul>

                <div class="c-card-list _insrProdList" style="display:none">
                    <div class="c-accordion c-accordion--type5 acc-agree">
                        <h4 class="c-heading c-heading--type2">휴대폰안심보험 신청 동의</h4>
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

                <!-- 사은품 리스트-->
                <section id="divGift" class="gift-section" style="display:none;"></section>

            </div>
            <!-- // STEP4 END -->

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
                                <span>이메일<br>명세서</span>
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
                                <span>우편<br>명세서</span>
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
            </div>
            <!-- // STEP5 END -->

            <div id="divButtonStep1" class="c-button-wrap">
                <button id="btnBefore" class="c-button c-button--full c-button--gray" style="display: none;">이전</button>
                <a id="btnNext" class="c-button c-button--full c-button--primary is-disabled _btnNext" href="javascript:void(0)">다음</a>
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

                    <div class="c-modal__body u-pt--0" id="bsDetailDesc2">
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

                            <hr class="c-hr c-hr--type2">
                            <dl class="c-addition c-addition--type1">
                                <dt>월 통신요금</dt>
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
                            </dl>
                            <dl class="c-addition c-addition--type2">
                                <dt>가입비(3개월 분납)</dt>
                                <dd class="u-ta-right">
                                    <em id="joinPriceTxt">- 원</em>
                                </dd>
                            </dl>

                            <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM}">
                                <dl class="c-addition c-addition--type2">
                                    <dt id="move_01" >번호이동 수수료</dt>
                                    <dd id="move_02" class="u-ta-right">${nmcp:getFtranPrice()} 원</dd>
                                </dl>
                            </c:if>

                            <hr class="c-hr c-hr--type2">

                            <dl class="c-addition c-addition--type1 c-addition--sum">
                                <dt>
                                    <b>월 납부금액</b>
                                    (부가세 포함)
                                </dt>
                                <dd class="u-ta-right"><b id="paySumMnthTxt2">0</b> 원</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2">
                                <dt id="bottomTitle"></dt>
                            </dl>

                            <hr class="c-hr c-hr--type2">

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
                            <button class="c-button c-button--full c-button--red is-disabled _btnNext" id="btnNext2">다음</button>
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
                            <button id="btnInsrProdConfirm" class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 셀프 개통 Layer popup-->
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
                        <div id="divDefaultButton" class="c-button-wrap _simpleDialogButton">
                            <button class="c-button c-button--full c-button--primary _btnCheck" data-dialog-close >확인</button>
                        </div>
                        <div id="divChangeButton" class="c-button-wrap _simpleDialogButton">
                            <button class="c-button c-button--gray c-button--lg u-width--120 _btnCheck" data-dialog-close>확인</button>
                            <button id="btnOnline" class="c-button c-button--full c-button--primary">해피콜 신청서 작성</button>
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

        <!-- 번호예약 Layer layerSearchNum -->
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

        <!-- 안심보험 서비스 메뉴얼 -->
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

    </t:putAttribute>

</t:insertDefinition>