<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
    uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<un:useConstants var="Constants"
    className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
<t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/mobile/event/frndRecommendView.js"></script>
</t:putAttribute>
<t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
        <!--include ../_includes/nav.pug-->
        <div class="ly-page-sticky">
            <h2 class="ly-page__head">친구초대 올인원<button class="header-clone__gnb"></button>
            </h2>
        </div>
        <div class="friend-all-banner-container">
            <div class="friend-all-banner__wrap">
                <h3><span class="friend-name-wrap">${custNM}</span>님이<br />직접 써보고 추천하는<br /><b>kt M모바일</b></h3>
                <p>[ 초대 ID ]입력 후 가입 시 <span>친구도 나도 <b>’포인트 증정’</b></span></p>
            </div>
            <div class="friend-all-banner__image">
                <img src="/resources/images/mobile/event/friend_all_banner.png" alt="">
            </div>
            <div class="friend-all-banner__rate">
                <p class="friend-all-banner__rate-title">친구가 추천해준 요금제</p>
                <ul id="ulRecommendRateList" class="rate-banner-list">


                </ul>
            </div>
        </div>
        <div class="friend-all-content">
            <div class="friend-all-wrap">
                <div class="friend-all-title">
                    <span class="friend-all-title__badge">kt M모바일 요금제</span>
                    <h4>어떻게 가입하는게 <b>가장 빠를까요?</b></h4>
                </div>
                <ul class="friend-all-join">
                    <li class="friend-all-join__item">
                        <span class="friend-all-circle">1</span>
                        <div class="friend-all-join__img">
                            <img src="/resources/images/mobile/event/friend_all_join1.png" alt="">
                        </div>
                        <div class="friend-all-join__desc">
                            <p>가까운 편의점 또는<br />퀵배송으로 유심 구매하기!</p>
                        </div>
                    </li>
                    <li class="friend-all-join__item">
                        <span class="friend-all-circle">2</span>
                        <div class="friend-all-join__img">
                            <img src="/resources/images/mobile/event/friend_all_join2.png" alt="">
                        </div>
                        <div class="friend-all-join__desc">
                            <p>친구가 추천해준 요금제로<br /><b>5분만에 [셀프]로 개통하기!</b></p>
                            <p>가입 시 <span>친구의 [초대 ID]입력은 필수!</span><br /><em>(이 페이지에서 가입하면 ID 자동입력)</em></p>
                        </div>
                    </li>
                    <li class="friend-all-join__item">
                        <span class="friend-all-circle">3</span>
                        <div class="friend-all-join__img">
                            <img src="/resources/images/mobile/event/friend_all_join3.png" alt="">
                        </div>
                        <div class="friend-all-join__desc">
                            <p class="u-ml--7">휴대폰에 유심만 바꿔끼면<br />친구도 나도 포인트 혜택</p>
                        </div>
                    </li>
                    <li class="friend-all-join__item">
                        <span class="friend-all-circle">4</span>
                        <div class="friend-all-join__img">
                            <img src="/resources/images/mobile/event/friend_all_join4.png" alt="">
                        </div>
                        <div class="friend-all-join__desc">
                            <p class="u-ml--7">KT인터넷까지 가입하면<br />M모바일 요금제+KT인터넷+TV<br />할인 금액에 이용 가능!</p>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="friend-all-wrap c-expand">
                <div class="friend-all-title">
                    <span class="friend-all-title__badge">추천해준 가입 방법</span>
                    <p><span><b>${custNM}님</b></span>이 추천한 개통 방법은</p>
                    <c:choose>
                        <c:when test="${recommend.openMthdCd eq '01'}">
                            <h4><span><b>바로배송유심</b><em>(당일 퀵)</em></span> 이에요!</h4>
                        </c:when>
                        <c:when test="${recommend.openMthdCd eq '02'}">
                            <h4><span><b>편의점</b><em>(CU/GS25 등)</em></span> 이에요!</h4>
                        </c:when>
                        <c:when test="${recommend.openMthdCd eq '03'}">
                            <h4><span><b>오픈마켓</b><em>(쿠팡/네이버 등)</em></span> 이에요!</h4>
                        </c:when>
                        <c:when test="${recommend.openMthdCd eq '04'}">
                            <h4><span><b>eSIM</b><em>(실물유심 배송없이 즉시 개통)</em></span> 이에요!</h4>
                        </c:when>
                    </c:choose>

                </div>
                <ul class="friend-all-usim">
                    <!-- 유심구매방법 선택한 것만 강조 / is-active 클래스 추가 -->
                    <li class="friend-all-usim__item ${recommend.openMthdCd eq '01' ? 'is-active' : ''}">
                        <div class="friend-all-usim__tooltip">
                            <button role="button" data-tpbox="#friendAllTp1" aria-describedby="#friendAllTp1__title">
                                <i class="c-icon c-icon--tooltip-type3" aria-hidden="true"></i>
                                <span class="sr-only">바로배송유심 툴팁 열기</span>
                            </button>
                            <div class="c-tooltip-box u-ta-left" id="friendAllTp1" role="tooltip">
                                <div class="c-tooltip-box__title" id="friendAllTp1__title">바로배송유심</div>
                                <div class="c-tooltip-box__content">
                                    바로배송유심은 구매한 유심을 2시간 내에 퀵으로 배송해드리는 무료배송 서비스에요!
                                </div>
                                <button class="c-tooltip-box__close" data-tpbox-close>
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>
                        </div>
                        <a class="friend-all-usim__info" href="/appForm/reqSelfDlvry.do" title="바로배송 유심 주문(당일 퀵) 페이지 이동">
                            <div class="friend-all-usim__img">
                                <img src="/resources/images/portal/event/friend_all_usim1.png" alt="">
                            </div>
                            <div class="friend-all-usim__desc">
                                <p>바로배송 유심주문</p>
                                <span>(당일 퀵)</span>
                            </div>
                        </a>
                    </li>
                    <li class="friend-all-usim__item ${recommend.openMthdCd eq '02' ? 'is-active' : ''} ">
                        <div class="friend-all-usim__tooltip">
                            <button role="button" data-tpbox="#friendAllTp2" aria-describedby="#friendAllTp2__title">
                                <i class="c-icon c-icon--tooltip-type3" aria-hidden="true"></i>
                                <span class="sr-only">편의점 툴팁 열기</span>
                            </button>
                            <div class="c-tooltip-box u-ta-left" id="friendAllTp2" role="tooltip">
                                <div class="c-tooltip-box__title" id="friendAllTp2__title">편의점</div>
                                <div class="c-tooltip-box__content">
                                    내 주변에 가까운 편의점에서 유심을 바로 구매할 수 있어요!
                                </div>
                                <button class="c-tooltip-box__close" data-tpbox-close>
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>
                        </div>
                        <a class="friend-all-usim__info" href="/direct/storeInfo.do" title="편의점 구매처 보러가기(CU/GS25 등) 페이지 이동">
                            <div class="friend-all-usim__img">
                                <img src="/resources/images/portal/event/friend_all_usim2.png" alt="">
                            </div>
                            <div class="friend-all-usim__desc">
                                <p>편의점 구매처 보러가기</p>
                                <span>(CU/GS25 등)</span>
                            </div>
                        </a>
                    </li>
                    <li class="friend-all-usim__item ${recommend.openMthdCd eq '04' ? 'is-active' : ''}  ">
                        <div class="friend-all-usim__tooltip">
                            <button role="button" data-tpbox="#friendAllTp3" aria-describedby="#friendAllTp3__title">
                                <i class="c-icon c-icon--tooltip-type3" aria-hidden="true"></i>
                                <span class="sr-only">eSIM 툴팁 열기</span>
                            </button>
                            <div class="c-tooltip-box u-ta-left" id="friendAllTp3" role="tooltip">
                                <div class="c-tooltip-box__title" id="friendAllTp3__title">eSIM</div>
                                <div class="c-tooltip-box__content">
                                    실물 유심 없이 QR코드로 휴대폰에 바로 개통하는 방식이에요. 배송 기다릴 필요 없이 즉시 사용할 수 있어요!
                                </div>
                                <button class="c-tooltip-box__close" data-tpbox-close>
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>
                        </div>
                        <a class="friend-all-usim__info" href="/appForm/eSimSelfView.do" title="eSIM(실물유심 배송 없이 즉시 개통) 페이지 이동">
                            <div class="friend-all-usim__img">
                                <img src="/resources/images/portal/event/friend_all_usim4.png" alt="">
                            </div>
                            <div class="friend-all-usim__desc">
                                <p>eSIM</p>
                                <span>(실물유심 배송 없이 즉시 개통)</span>
                            </div>
                        </a>
                    </li>
                    <li class="friend-all-usim__item ${recommend.openMthdCd eq '03' ? 'is-active' : ''}  ">
                        <div class="friend-all-usim__tooltip">
                            <button role="button" data-tpbox="#friendAllTp4" aria-describedby="#friendAllTp4__title">
                                <i class="c-icon c-icon--tooltip-type3" aria-hidden="true"></i>
                                <span class="sr-only">오픈마켓 툴팁 열기</span>
                            </button>
                            <div class="c-tooltip-box u-ta-left" id="friendAllTp4" role="tooltip">
                                <div class="c-tooltip-box__title" id="friendAllTp4__title">오픈마켓</div>
                                <div class="c-tooltip-box__content">
                                    네이버스토어·쿠팡 등에서 유심을 구매하는 방법이에요. 보유한 해당 플랫폼 포인트로 할인 결제도 가능해요.
                                </div>
                                <button class="c-tooltip-box__close" data-tpbox-close>
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>
                        </div>
                        <a class="friend-all-usim__info" href="/direct/openMarketInfo.do" title="오픈마켓 구매하기(쿠팡/네이버 등) 페이지 이동">
                            <div class="friend-all-usim__img">
                                <img src="/resources/images/portal/event/friend_all_usim3.png" alt="">
                            </div>
                            <div class="friend-all-usim__desc">
                                <p>오픈마켓 구매하기</p>
                                <span>(쿠팡/네이버 등)</span>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="friend-all-wrap friend-all--gray">
                <div class="friend-all-title">
                    <span class="friend-all-title__badge">추천 요금제</span>
                    <h4><span><b>${custNM}님</b></span>은 이 요금제를 추천했어요!</h4>
                </div>

                <div class="rate-banner event-summary">
                    <ul class="rate-banner-list">
                        <li class="rate-banner__item" data-rate-adsvc-cd="${recommend.commendSocCode01}"></li>
                        <li class="rate-banner__item" data-rate-adsvc-cd="${recommend.commendSocCode02}"></li>
                        <li class="rate-banner__item" data-rate-adsvc-cd="${recommend.commendSocCode03}"></li>
                    </ul>
                </div>
                <div class="c-button-wrap friend-all-rate">
                    <a class="c-button" href="/m/rate/rateList.do" title="요금제 페이지 이동">더 많은 요금제 보러가기<i class="c-icon c-icon--arrow-light" aria-hidden="true"></i></a>
                </div>
            </div>

            <!-- 관리자 관리 HTML START-->
             ${allInOneContent}
            <!-- 관리자 관리 HTML END-->


        </div>
    </div>



</t:putAttribute>
</t:insertDefinition>