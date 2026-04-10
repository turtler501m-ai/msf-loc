<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

    <t:putAttribute name="titleAttr">요금제 변경</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.06.10"></script>
        <script type="text/javascript" src="/resources/js/personal/portal/pFarPricePlanView.js?version=25.12.11"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <input type="hidden" id="soc" name="soc">
        <input type="hidden" id="prvRateCd"         name="prvRateCd"         value="${mcpFarPriceDto.prvRateCd}" >
        <input type="hidden" id="prvRateGdncSeq"    name="prvRateGdncSeq"    value="" >
        <input type="hidden" id="instMnthAmt"       name="instMnthAmt"       value="${mcpFarPriceDto.instMnthAmt}" >
        <input type="hidden" id="isPriceChange"     name="isPriceChange"     value="${isPriceChange}" >
        <input type="hidden" id="strRateAdsvcCtgCd" name="strRateAdsvcCtgCd" value=""/>
        <input type="hidden" id="rsrvPrdcChk"       name="rsrvPrdcChk"       value="">
        <input type="hidden" id="prvRateNm"         name="prvRateNm"         value="">

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">개인화 URL (요금제 변경)</h2>
            </div>

            <div class="c-row c-row--lg">
                <%@ include file="/WEB-INF/views/personal/portal/pCommCtn.jsp" %>
                <hr class="c-hr c-hr--title">
                <strong class="u-block c-heading c-heading--fs24 c-heading--regular u-mt--32 u-ta-center" id="prvRateGrpNm"></strong>
                <hr class="c-hr c-hr--title">
                <div class="total-charge u-mt--16">
                    <div class="total-charge__panel">
                        <span class="c-text c-text--fs14 u-co-gray" id="appStartDdDate"></span>
                        <ul class="product-summary">
                            <li class="product-summary__item">
                                <i class="c-icon c-icon--payment-data" aria-hidden="true"></i>
                                <span class="product-summary__text" id="rateAdsvcLteDesc"></span>
                            </li>
                            <li class="product-summary__item">
                                <i class="c-icon c-icon--payment-call" aria-hidden="true"></i>
                                <span class="product-summary__text" id="rateAdsvcCallDesc"></span>
                            </li>
                            <li class="product-summary__item">
                                <i class="c-icon c-icon--payment-sms" aria-hidden="true"></i>
                                <span class="product-summary__text" id="rateAdsvcSmsDesc"></span>
                            </li>
                        </ul>
                    </div>
                    <dl class="total-charge__price" border=1 >
                        <dt class="total-charge__title">월 요금(VAT포함)</dt>
                        <dd>
                            <c:choose>
                                <c:when test="${mcpFarPriceDto.promotionDcAmt > 0 }">
                                    <span class="u-td-line-through"><fmt:formatNumber value="${mcpFarPriceDto.baseVatAmt}" type="number"/></span>
                                    <span class="total-charge__text u-ml--8" id="instAmt"><fmt:formatNumber value="${mcpFarPriceDto.instMnthAmt}" type="number"/>원</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="total-charge__text" id="instAmt"><fmt:formatNumber value="${mcpFarPriceDto.baseVatAmt}" type="number"/>원</span>
                                </c:otherwise>
                            </c:choose>
                        </dd>
                    </dl>
                </div>
                <p class="c-bullet c-bullet--fyr u-co-sub-4">월 요금은 부가서비스 사용, 프로모션 할인 등에 따라 실 청구 금액과 다를 수 있습니다.</p>

                <h4 class="c-heading c-heading--fs16 u-mt--48 resYn" style="display: none;">변경 예약 중 요금제</h4>
                <div class="c-table u-mt--16 resYn" style="display: none;">
                    <table>
                        <caption>요금제, 기본료, 예약취소로 구성된 변경 예약 중 요금제 정보</caption>
                        <colgroup>
                            <col style="width: 426px">
                            <col style="width: 426px">
                            <col>
                        </colgroup>
                        <thead>
                        <th scope="col">요금제</th>
                        <th scope="col">기본료</th>
                        <th scope="col">예약취소</th>
                        </thead>
                        <tbody id="rsrvPrdcView">
                        </tbody>
                    </table>
                </div>

                <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64 u-mb--24">변경 가능한 요금제</h3>
                <ul class="c-text-list c-bullet c-bullet--dot u-mb--40">
                    <li class="c-text-list__item u-co-gray">
                        요금제 <span class="u-co-red">예약변경 신청 시 실제 변경시점(매월 1일)의 프로모션 할인가로 적용</span>되며, 프로모션 요금 할인가는 당사 정책에 의해 상시 변동될 수 있습니다.
                    </li>
                    <li class="c-text-list__item u-co-gray">
                        요금제는 월 1회에 한하여 변경이 가능하며 예약변경 시에는 1일에 반영되어 그 달 내 추가 변경이 불가합니다.
                    </li>
                    <li class="c-text-list__item u-co-gray">
                        월 중 요금제를 변경하시면 변경 전 이용기간 만큼 기본료 및 기본 제공량이 일할 계산되어 <span class="u-co-red">이용기간에 따른 제공량을 초과한 사용 분에 대해서는 추가 요금이 발생할 수 있습니다.</span>
                    </li>
                </ul>

                <div class="tab_view">
                    <div class="c-tabs c-tabs--type1">
                        <div class="c-tabs__list u-width--100p" role="tablist" id="1depthList"></div>
                    </div>
                </div>

                <form name="far">
                    <input type="hidden" name="rateAdsvcDivCd"   id="rateAdsvcDivCd"    value="RATE"/>
                    <input type="hidden" name="ctgOutputCd"      id="ctgOutputCd"       value="CO00130001"/>
                    <input type="hidden" name="upRateAdsvcCtgCd" id="upRateAdsvcCtgCd"  value=""/>
                    <input type="hidden" name="tabChk"           id="tabChk"/>
                </form>

                <b class="c-flex c-text c-text--fs15 u-mt--48">
                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                    <span class="u-ml--4">알려드립니다</span>
                </b>
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                    <li class="c-text-list__item u-co-gray">현재 이용중인 요금제가 신규가입이 중단된 요금제일 경우 다른 요금제로 변경 시 기존 사용하던 요금제로의 재 변경(복구)가 불가합니다.</li>
                    <li class="c-text-list__item u-co-gray">요금제 변경을 예약(익월 1일)하신 고객님께서는 예약 신청을 먼저 취소하여야 요금제 즉시 변경이 가능합니다.</li>
                    <li class="c-text-list__item u-co-gray">요금제 변경 시 공통지원금 변동에 따른 차액정산금(위약금)이 발생될 수 있으며, 다음달 요금 안내서에 합산되어 청구됩니다.</li>
                    <li class="c-text-list__item u-co-gray">결합중인 kt M모바일 회선의 요금제 변경 시, 별도의 결합 해지 없이도 마이페이지에서 요금제 변경이 가능합니다.</li>
                    <li class="c-text-list__item u-co-gray">결합중인 kt M모바일 회선의 요금제 변경 시, 변경 후의 요금제에 따라 결합 데이터 제공량이 달라지거나, 제공되지 않을수 있으니 결합 이벤트 페이지의 ‘결합 데이터 제공 요금제표’를 참고해 주시기 바랍니다.</li>
                </ul>
            </div>
        </div>

        <!-- 요금제 변경 첫 알럿 팝업 -->
        <div class="c-modal c-modal--md" id="farChgStartModal" role="dialog" tabindex="-1" aria-modal="true">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">

                    <div class="c-modal__body u-ta-center">
                        <i class="c-icon c-icon--notice" aria-hidden="true"></i>
                        <p class="u-fs-22 u-mt--24" ><b>현재 사용 중인 요금제의<br/> 데이터, 통화, 문자 제공량이 부족하신가요?</b></p>

                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button id="btnFarChgY" class="c-button c-button--full c-button--primary u-pt--11">네, 제공량이 부족해요</button>
                            <button id="btnFarChgN" class="c-button c-button--full c-button--gray">아니요</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 요금제 변경 첫 알럿 네 선택 시 -->
        <div class="c-modal c-modal--lg" id="farChgWarningModal" role="dialog" tabindex="-1" aria-modal="true">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">

                    <div class="c-modal__body u-ta-center">
                        <p class="u-fs-16">월 중 요금제 변경 시, 기본 제공량 재산정(일할계산)으로 인해<br/>
                            <b><span class="u-co-sub-4">더 많은 초과 요금이 추가로 발생합니다.</span></b><br/><br/>
                            예약(익월1일) 변경을 권유드립니다.</p>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button id="btnFarChgReserve" class="c-button c-button--full c-button--primary u-pt--6">예약으로 진행<br/><span class="u-fs-14">(익월1일로 변경)</span></button>
                            <button id="btnFarChgImmediate" class="c-button c-button--full c-button--gray u-pt--6">즉시변경으로 진행<br/><span class="u-fs-14">(초과요금 추가 과금될 수 있어요)</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="c-modal c-modal--md" id="farChgOvertModal" role="dialog" tabindex="-1" aria-modal="true" labelledby="#farChgOvertModalTitle" >
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body u-ta-center">
                        <i class="c-icon c-icon--notice" aria-hidden="true"></i>
                        <h3 class="u-fs-24 u-mt--16">
                            <b>초과 요금 발생 안내</b>
                        </h3>
                        <p class="u-fs-16 u-mt--24" >현재 <span class="u-co-sub-4"><b id="chgInfoTxt">[-]</b></span> 제공량을 모두 사용하셔서,<br/> 초과 요금이 반드시 발생합니다.</p><br/>
                        <p class="u-fs-16">
                            월 중 요금제 변경 시, 기본 제공량 재산정(일할계산)으로<br/>  인해 <span class="u-co-sub-4"><b>더 많은 초과 요금이 추가로 발생합니다.</b></span>
                            <br/>예약(익월1일) 변경을 권유드립니다.
                        </p>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button id="btnFarOverChgY" class="c-button c-button--full c-button--primary u-pt--6" >예약<br/><span class="u-fs-14">(익월1일로 변경)</span></button>
                            <button id="btnFarOverChgN" class="c-button c-button--full c-button--gray u-pt--6" >즉시변경<br/><span class="u-fs-14">(초과 요금 추가 과금)</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="c-modal c-modal--md" id="farChgOvertModal2" role="dialog" tabindex="-1" aria-modal="true" labelledby="#farChgOvertModal2Title" >
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body u-ta-center">
                        <i class="c-icon c-icon--notice" aria-hidden="true"></i>
                        <h3 class="u-fs-24 u-mt--16">
                            <b>초과 요금 발생 동의</b>
                        </h3>
                        <p class="u-fs-16 u-mt--24">
                            즉시 요금제 변경 시, 기본 제공량 재산정되어<br/> 현재 사용량 기준으로
                            <span class="u-co-sub-4"><b>더 많은 초과 요금이 발생합니다.</b></span>
                        </p>
                        <p class="u-fs-16">이에 동의하며 변경을 진행합니다.</p>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--primary u-pt--11" data-dialog-close >취소</button>
                            <button id="btnAgree" class="c-button c-button--full c-button--gray u-pt--6"  >동의 후 즉시변경<br/><span class="u-fs-14">(초과 요금 추가 과금)</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>