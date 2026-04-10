<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />
<c:set var="jsver" value="${nmcp:getJsver()}" />
<t:insertDefinition name="mlayoutOutFormFotter">

    <t:putAttribute name="titleAttr">
        <c:choose>
            <c:when test="${addInfoTxt eq 'Y'}">셀프개통 가입신청서</c:when>
            <c:otherwise>대리점 셀프개통 가입신청서</c:otherwise>
        </c:choose>
    </t:putAttribute>

    <t:putAttribute name="googleTagScript">
        <!-- Event snippet for 가입신청시작_MO conversion page -->
        <script>
            gtag('event', 'conversion', {'send_to': 'AW-862149999/qD77CK_jxLYZEO-6jZsD'});
        </script>
    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/appForm/appAgentFormDesignView.js?ver=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?ver=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js?ver=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/mobile/sns.js?ver=${jsver}"></script>
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <input type="hidden" id="serverName"          value="${serverName}"/>
            <input type="hidden" id="prdtId"              name="prdtId"              value="${AppformReq.prdtId}" />
            <input type="hidden" id="rprsPrdtId"          name="rprsPrdtId"          value="${AppformReq.rprsPrdtId}" />
            <input type="hidden" id="prdtNm"              name="prdtNm"              value="${AppformReq.prdtNm}" />
            <input type="hidden" id="prdtSctnCd"          name="prdtSctnCd"          value="${AppformReq.prdtSctnCd}" />
            <input type="hidden" id="enggMnthCnt"         name="enggMnthCnt"         value="${AppformReq.enggMnthCnt}" />
            <input type="hidden" id="operType"            name="operType"            value="${AppformReq.operType}" />
            <input type="hidden" id="operTypeNm"          name="operTypeNm"          value="${AppformReq.operTypeNm}" />
            <input type="hidden" id="onOffType"           name="onOffType"           value="${AppformReq.onOffType}" />
            <input type="hidden" id="socCode"             name="socCode"             value="${AppformReq.socCode}" />
            <input type="hidden" id="cntpntShopId"        name="cntpntShopId"        value="${AppformReq.cntpntShopId}" />
            <input type="hidden" id="modelSalePolicyCode" name="modelSalePolicyCode" value="${AppformReq.modelSalePolicyCode}" />
            <input type="hidden" id="modelMonthly"        name="modelMonthly"        value="${AppformReq.modelMonthly}" />
            <input type="hidden" id="socLimitYn"          name="socLimitYn"          value="${socLimitYn}" />
            <input type="hidden" id="openMarketId"        name="openMarketId"        value="${AppformReq.openMarketId}" />
            <input type="hidden" id="joinPrice"           name="joinPrice"           value="-">
            <input type="hidden" id="joinPriceIsPay"      name="joinPriceIsPay"      value="N">
            <input type="hidden" id="reqModelName"        name="reqModelName"        value="${AppformReq.reqModelName}" >
            <input type="hidden" id="sprtTp"              name="sprtTp"              value="${AppformReq.sprtTp}" >

            <input type="hidden" name="isEvntCdPrmtAuth" id="isEvntCdPrmtAuth" value="" />
            <input type="hidden" name="evntCdPrmtSoc" id="evntCdPrmtSoc" value="" />
             <input type="hidden" name="evntCdSeq" id="evntCdSeq" value="" />

            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    <c:choose>
                        <c:when test="${addInfoTxt eq 'Y'}">셀프개통 가입신청서</c:when>
                        <c:otherwise>대리점 셀프개통 가입신청서</c:otherwise>
                    </c:choose>
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

            <div class="payment-planning">

                <!--개통유형-->
                <div class="c-form">

                    <c:choose>
                        <c:when test="${addInfoTxt eq 'Y'}">
                            <a data-tpbox="#openTypeTooltip">
                                <span class="c-form__title--type3 u-mt--20" id="radOpenType">개통유형</span>
                                <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                            </a>
                            <div class="c-tooltip-box u-mt--0" id="openTypeTooltip">
                                <h3 class="c-tooltip-box__title c-hidden">개통유형 설명</h3>
                                <div class="c-tooltip-box__content">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item">유심을 보유하고 있으시다면 셀프개통이 가능합니다.</li>
                                        <li class="c-text-list__item">유심이 없으신 고객님은 다이렉트몰 구매하기를 이용하시거나, 편의점 또는 오픈마켓에서 유심 구매 후 셀프개통을 진행해주세요.</li>
                                        <li class="c-text-list__item">개통이 잘 안되시거나 어려운 부분이 있으시면 평일 09:00 ~ 18:00 고객센터(1899-5000/유료)로 연락 부탁드립니다.</li>
                                    </ul>
                                    <h4 class="c-heading c-heading--type6">
                                        <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                                        <br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
                                    </h4>
                                </div>
                                <button class="c-tooltip-box__close" data-tpbox-close>
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <span class="c-form__title--type3 u-mt--20" id="radOpenType">개통유형</span>
                        </c:otherwise>
                    </c:choose>
                    <div class="c-check-wrap" role="group" aria-labelledby="radOpenType">
                        <div class="c-chk-wrap">
                            <input class="c-radio c-radio--button" id="radOpenType1" type="radio" name="radOpenType" value="7" checked>
                            <label class="c-label" for="radOpenType1">셀프개통<span class="c-text c-text--type5">(유심있음)</span></label>
                        </div>
                    </div>
                </div>

                <!--가입유형-->
                <div class="c-form">
                    <span class="c-form__title--type3" id="radRegistType">가입유형</span>
                    <div class="c-check-wrap" role="group" aria-labelledby="radRegistType">
                        <c:if test="${mspSalePlcyMstDto.mnpYn == 'Y'}">
                            <input class="c-radio c-radio--button" id="operTypeRadio1" type="radio" name="operTypeRadio" value="${Constants.OPER_TYPE_MOVE_NUM}" operName="번호이동" <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM }" > checked </c:if> disabled >
                            <label class="c-label" for="operTypeRadio1">번호이동</label>
                        </c:if>
                        <c:if test="${mspSalePlcyMstDto.newYn == 'Y'}">
                            <input class="c-radio c-radio--button" id="operTypeRadio2" type="radio" name="operTypeRadio" value="${Constants.OPER_TYPE_NEW}" operName="신규가입" <c:if test="${AppformReq.operType eq Constants.OPER_TYPE_NEW }" > checked </c:if> disabled >
                            <label class="c-label" for="operTypeRadio2">신규가입</label>
                        </c:if>
                    </div>
                </div>
                <!--가입유형 설명문구-->
                <ul class="c-text-list c-bullet c-bullet--dot">
                    <!--번호이동 설명문구-->
                    <li class="c-text-list__item u-co-gray" id="movTxt" style="display: none;">
                        현재 사용하고 있는 번호 유지하고 kt M 에 가입합니다.
                    </li>
                    <!--신규가입 설명문구-->
                    <li class="c-text-list__item u-co-gray" id="newTxt" style="display: none;">
                        새로운 번호로 개통합니다.
                    </li>
                    <!--공통 설명문구-->
                    <c:if test="${addInfoTxt ne 'Y'}">
                    <li class="c-text-list__item u-co-gray">
                        <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                        <br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
                    </li>
                    </c:if>
                </ul>

                <!--서비스유형-->
                <div class="c-form">
                    <span class="c-form__title--type2" id="radServType">서비스 유형</span>
                    <div class="c-check-wrap c-check-wrap--type3" role="group" aria-labelledby="radServType">
                        <c:forEach items="${nmcp:getCodeList('usimProductDataType')}" var="usimProductDataType" varStatus="status">
                            <c:if test="${usimProductDataType.dtlCd eq AppformReq.prdtSctnCd}">
                                <input class="c-radio c-radio--button" id="dataType${status.index}" type="radio" name="dataType" value="${usimProductDataType.dtlCd}" checked/>
                                <label class="c-label" for="dataType${status.index}">${usimProductDataType.dtlCdNm}</label>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>

                <!--요금제-->
                <span class="c-form__title--type2">요금제 선택</span>
                <div class="c-form__select">
                    <select class="c-select" id="selSocCode">
                    </select>
                    <label class="c-form__label" for="selSocCode">요금제 선택</label>
                </div>

                <!--이벤트 코드-->
                <div class="c-form" id ="evntCdPrmtWrap" style="display: none">
                    <span class="c-form__title--type2">이벤트 코드</span>
                     <p class="u-mb--16">해당 요금제는 이벤트 코드 적용이 가능합니다.<br />이벤트에서 확인한 코드가 있다면 입력 해 주세요.<br />※ 지급 조건은 프로모션별로 상이합니다.</p>
                     <div class="c-form" style="display: flex">
                         <input class="c-input" name="evntCdPrmt" id="evntCdPrmt" type="text" placeholder="이벤트 코드 입력" maxlength="50" onkeyup="nextStepBtnChk();">
                         <label class="c-form__label sr-only" for="evntCdPrmt">이벤트코드</label>
                         <button id="evntCdPrmtBtn" class="c-button c-button--mint u-width--140 u-ml--8" onclick="checkEvntCdPrmt();">확인</button>
                         <button id="evntCdTryBtn" class="c-button c-button--mint u-width--140 u-ml--8 is-disabled" onclick="tryEvntCdPrmt();">재입력</button>
                     </div>
                </div>

                <!-- 사은품-->
                <hr class="c-hr c-hr--type1 c-expand u-mt--19 giftInfoListHr" style="display:none;">
                <section id="divGift" class="gift-section" style="display:none;"></section>

                <div class="giftInfoList" style="margin-top:-40px; display:none;"></div>
                <!--
                <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2 u-mt--m6 u-mb--48 giftInfoTxt" style="display:none;">
                    <li class="c-text-list__item u-co-gray">가입조건 선택에 따른 선택 사은품을 확인하세요.</li>
                    <li class="c-text-list__item u-co-gray">실제 선택 사은품은 구매처에 따라 달라질 수 있습니다.</li>
                    <li class="c-text-list__item u-co-gray">가입 서식지 작성 시 사은품을 선택할 수 있습니다.</li>
                </ul>
                -->
                <div class="c-button-wrap">
                    <button class="c-button c-button--full c-button--red nextStepBtn" onclick="fnSelfClick();">가입하기</button>
                </div>
            </div>

            <!--하단 플로팅바 close 영역-->
            <div class="c-button-wrap c-button-wrap--b-floating is-active" id="bs_floating_button" data-floating-bs-trigger="#bsSample2">
                <button class="c-button c-button--bs-openner">
                    <p class="c-text c-text--type3 u-fw--regular">
                        월 예상 납부금액
                        <span class="fs-26 u-ml--8" id="totalPriceBottom">0 <span class="fs-14 fw-normal">원</span></span>
                        <input type="hidden" name="settlAmt" id="settlAmt"/>
                    </p>
                    <p class="c-text c-text--type5 u-co-red-sub1">가입비 및 유심비 등 기타요금은 별도 청구됩니다.</p>
                    <i class="c-icon c-icon--arrow-up" aria-hidden="true"></i>
                </button>
            </div>
        </div>

        <!--가입전 확인 popup-->
        <button data-dialog-trigger="#signup-dialog" id="chkModalPop" style="display:none;">가입 전 확인해주세요 popup 호출</button>
        <div class="c-modal" id="signup-dialog" role="dialog" tabindex="-1" aria-describedby="#signup-title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="signup-title">가입 전 확인해주세요</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body overflow-x-hidden">
                        <h3 class="c-heading c-heading--type3">신분증과 <br>계좌/신용카드를 준비해주세요!</h3>
                        <p class="c-text c-text--type2 u-co-gray"> 비대면 가입신청서 작성 시 본인인증과 <br>납부정보 확인이 필요합니다. </p>
                        <div class="c-box u-mt--32">
                            <img class="center-img" src="/resources/images/mobile/content/img_id_card.png" alt="ars 수신번호">
                        </div>
                        <hr class="c-hr c-hr--type2">
                        <b class="c-text--type3 c-bullet">
                            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>선택 가능 본인인증 방법
                        </b>
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-co-gray" id="authListDesc">신용카드, 네이버인증서, toss 인증서, PASS 인증서, 카카오 본인인증</li>
                        </ul>
                        <div class="c-box c-box--type1 d-box--type1 u-ta-center">
                            <div class="c-button-wrap c-button-wrap--column u-mt--0">
                                <button class="c-button c-button--full c-button--primary" onclick="confirmNextStep();">가입하기</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--하단 플로팅바 open 영역-->
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
                            <dl class="c-addition c-addition--type1 bottomTop">
                                <dt>월 통신요금</dt>
                                <dd class="u-ta-right"><b id="totalPrice2">0</b> 원</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2 bottomTop">
                                <dt>기본요금</dt>
                                <dd class="u-ta-right" id="baseAmt">0 원</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2 bottomTop" id="bottomDefDc">
                                <dt>
                                    <a data-tpbox="#tp1">기본할인
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </a>
                                    <div class="c-tooltip-box" id="tp1">
                                        <div class="c-tooltip-box__title">기본할인</div>
                                        <div class="c-tooltip-box__content">약정 가입시 기본적으로 제공되는 요금제 할인금액입니다.</div>
                                    </div>
                                </dt>
                                <dd class="u-ta-right">
                                    <b> <em id="dcAmt">0 원</em></b>
                                </dd>
                            </dl>
                            <dl class="c-addition c-addition--type2 bottomTop" id="bottomPriceDc">
                                <dt>
                                    <a data-tpbox="#tp2">요금할인
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </a>
                                </dt>
                                <dd class="u-ta-right">
                                    <b> <em id="addDcAmt">0 원</em></b>
                                </dd>
                            </dl>
                            <div class="c-tooltip-box" id="tp2">
                                <div class="c-tooltip-box__title">요금할인</div>
                                <div class="c-tooltip-box__content">약정 - 할인 선택시 제공되는 요금할인 혜택입니다.</div>
                                <button class="c-tooltip-box__close" data-tpbox-close>
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>
                            <dl class="c-addition c-addition--type2 bottomTop">
                                <dt>프로모션 할인</dt>
                                <dd class="u-ta-right">
                                    <b> <em id="promotionDcAmtTxt">0 원</em></b>
                                </dd>
                            </dl>

                            <hr class="c-hr c-hr--type2 bottomMiddle">

                            <dl class="c-addition c-addition--type1 bottomMiddle">
                                <dt>가입비 및 유심비 별도</dt>
                            </dl>
                            <dl class="c-addition c-addition--type2 bottomMiddle">
                                <dt>가입비(3개월 분납)</dt>
                                <dd class="u-ta-right">
                                    <em id="joinPriceTxt"><span class="c-text c-text--strike">0 원</span></em>
                                </dd>
                            </dl>

                            <dl class="c-addition c-addition--type2 bottomMiddle">
                                <dt id="move_01" style="display:none;">번호이동 수수료</dt>
                                <dd id="move_02" style="display:none;" class="u-ta-right">${nmcp:getFtranPrice()} 원</dd>
                                <input type="hidden" id="moveYn"/>
                            </dl>

                            <hr class="c-hr c-hr--type2">

                            <dl class="c-addition c-addition--type1 c-addition--sum">
                                <dt>월 납부금액(부가세 포함)</dt>
                                <dd class="u-ta-right"><b id="totalPrice">0</b> 원</dd>
                            </dl>

                            <dl class="c-addition c-addition--type2">
                                <dt id="bottomTitle"></dt>
                            </dl>
                        </div>

                        <hr class="c-hr c-hr--type2">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-co-gray">kt M mobile에서 제공한 유심으로 개통을 진행하지 않으실 경우, 유심비용이 청구될 수 있습니다.
                            <li class="c-text-list__item u-co-gray">월 납부금액은 부가서비스 등의 사용에 따라 추가금액이 합산되어 청구 될 수 있습니다.</li>
                            <li class="c-text-list__item u-co-gray">월 납부금액은 부가세 포함 금액입니다.</li>
                            <li class="c-text-list__item u-co-gray">타사향(SK, LG U+ 등) 단말은 일부 서비스(MMS,영상통화, 교통카드 기능 등) 이용이 제한될 수 있습니다.</li>
                        </ul>
                    </div>

                    <div class="c-modal__footer fixed-area">
                        <div class="c-button-wrap u-mt--0">
                            <button class="c-button c-button--full c-button--red nextStepBtn" onclick="fnSelfClick();">가입하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>