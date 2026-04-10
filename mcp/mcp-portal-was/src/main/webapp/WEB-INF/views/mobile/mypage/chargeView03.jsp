<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/mypage/chargeView03.js?version=${jsver}"></script>
</t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    요금조회
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="c-tabs c-tabs--type2" data-tab-active="1">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button" id="tab1" data-tab-header="#tabB1-panel" >월별금액조회</button>
                    <button class="c-tabs__button is-active" id="tab2" data-tab-header="#tabB2-panel" >실시간 요금조회</button>
                </div>
                <hr class="c-hr c-hr--type3 c-expand">
                <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
                <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
                <div class="c-tabs__panel" id="tabB1-panel"></div>
                <div class="c-tabs__panel border-none" id="tabB2-panel">
                    <div class="banner-balloon u-mt--40 u-mb--24">
                        <p>
                            <span class="c-text c-text--type5">이용중인 요금제</span> <br>
                            <b class="c-text c-text--type7">${rateNm}</b>
                        </p>
                        <img class="deco-img" src="/resources/images/mobile/content/img_bolloon_banner_03.png" alt="">
                    </div>

                    <div class="c-box u-mt--56 u-mb--40">
                        <div class="c-item">
                            <div class="c-item__title flex-type--between">
                                <span> <i class="c-icon c-icon--data" aria-hidden="true"></i>
                                    <span class="u-ml--8">${sumAmt} 원</span>
                                </span>
                                <span class="u-ml--auto c-text c-text--type4">
                                    <span class="u-co-mint">${useDay}일</span>/${useTotalDay}일
                                </span>
                            </div>
                            <div class="c-indicator c-indicator--type1 u-mt--16">
                                <span class="mark-mint" style="width:${grapWidth}%"></span>
                            </div>
                        </div>
                        <hr class="c-hr c-hr--type3 c-expand u-mt--40">
                    </div>

                    <div class="c-item u-mt--40">
                        <div class="c-item__title flex-type--between">요금상세내역
                            <span class="c-text c-text--type4 u-co-gray-7 u-ml--auto" id="searchTime">${searchTime}</span>
                        </div>

                        <c:choose>
                            <c:when test="${realFareVOList ne null and !empty realFareVOList}">
                                <div class="info-box u-mt--12">
                                    <c:forEach var="realFareVOList" items="${realFareVOList}">
                                        <dl class="pay-detail__item">
                                            <dt>${realFareVOList.gubun}</dt>
                                            <dd>${realFareVOList.payment} 원</dd>
                                        </dl>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="c-nodata">
                                    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                    <p class="c-nodata__text">실시간 요금이 조회되지 않습니다.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <hr class="c-hr c-hr--type3 u-mt--40 u-mb--24">
                    <b class="c-text c-text--type3">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
                    </b>
                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                        <li class="c-text-list__item u-co-gray">실시간 요금조회는 정확한 금액이 아니며, 청구서에 표기되는 금액과 다를 수 있으므로 참고용으로 확인 부탁드립니다.</li>
                        <li class="c-text-list__item u-co-gray">국제 로밍 이용료 실시간 요금 조회는 해외 통신 사업자의 사정상 1일 ~ 2일 이상 차이가 날 수 있습니다.</li>
                        <li class="c-text-list__item u-co-gray">현재 사용요금은 조회 반영 시점에 따라 최종요금과 다소 차이가 있을 수 있습니다.</li>
                        <li class="c-text-list__item u-co-gray">월 중 번호변경 시 잔여가입비와 핸드폰 분납금은 변경된 번호로 조회 됩니다.</li>
                        <li class="c-text-list__item u-co-gray">요금제 및 부가서비스에 포함되는 각종 무료 제공 혜택(음성/데이터/문자 등)은 청구항목으로 표기되나, 실제 청구시에는 요금할인액으로(-) 처리되어 부가되지 않을 수 있습니다.</li>
                        <li class="c-text-list__item u-co-gray">요금제/부가서비스/데이터 상품 월중 신청 시 해당 월 요금제 월정액 및 제공 혜택은 각각 일할 적용됩니다.(무선 인터넷 1MB=1,024Kb)</li>
                        <li class="c-text-list__item u-co-gray">당월 명의변경 고객님은 가입일(변경일)부터의 요금으로 산정됩니다.</li>
                        <li class="c-text-list__item u-co-gray">실시간 요금은 익월 10일 이후부터 납부가 가능합니다.</li>
                    </ul>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>