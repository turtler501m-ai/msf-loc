<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/appForm/appSimpleInfo.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">셀프개통<button class="header-clone__gnb"></button></h2>
            </div>
            <div class="upper-banner upper-banner--self-opening c-expand">
                <div class="upper-banner__content">
                    <strong class="upper-banner__title">유심이 있다면<br>누구나 쉽게 5분이면 끝!</strong>
                    <a class="c-button c-button--sm upper-banner__anchor u-mr--8" href="javascript:void(0);" onclick="selfTimeChk();">
                             셀프개통 시작<i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                    </a>
                    <button class="c-button c-button--sm upper-banner__anchor" data-scroll-top="#swiperUsimGuide|100">가이드 보기<i class="c-icon c-icon--anchor-white" aria-hidden="true"></i></button>
                </div>
                <div class="upper-banner__image">
                    <img src="/resources/images/mobile/content/bg_deco_man.png" alt="">
                </div>
            </div>
            <h3 class="c-heading c-heading--type3 u-mb--20">유심이 필요하신가요?</h3>
            <div class="c-button-wrap c-button-wrap--column u-mt--0">
                <a class="c-button--link" href="/m/appForm/reqSelfDlvry.do">
                    <i class="c-icon c-icon--ktmm" aria-hidden="true"></i>
                    <span>다이렉트몰 유심 구매</span>
                </a>
                <a class="c-button--link" href="/m/direct/storeInfo.do">
                    <i class="c-icon c-icon--mart" aria-hidden="true"></i>
                    <span>편의점/마트 유심 구매</span>
                </a>
                <a class="c-button--link" href="/m/direct/openMarketInfo.do">
                    <i class="c-icon c-icon--openmarket" aria-hidden="true"></i>
                    <span>오픈마켓 유심 구매</span>
                </a>
            </div>
            <p class="c-heading c-heading--type8">셀프개통 가능시간</p>
            <div class="self-opening-box c-expand">
                <p class="c-bullet c-bullet--dot u-co-gray c-text--fs13 u-mt--2"><span class="c-text c-text--fs13 u-co-black u-mr--4">번호이동 10:00 ~ 19:50</span>(일요일, 신정/설/추석 당일 제외)</p>
                <p class="c-bullet c-bullet--dot u-co-gray c-text--fs13 u-mt--8"><span class="c-text c-text--fs13 u-co-black u-mr--4">신규가입 08:00 ~ 21:50</span></p>
                <p class="c-bullet c-bullet--fyr u-co-gray u-mt--13 u-mb--6">
                    eSIM으로 셀프개통이 가능합니다. eSIM 개통 가능 여부를 확인해 보세요.
                    <a class="c-text-link--bluegreen" href="/m/appForm/eSimInfom.do">eSIM 알아보기</a>
                </p>
                <p class="c-bullet c-bullet--fyr u-co-gray u-mt--13 u-mb--6">
              외국인,미성년자는 가입신청서 작성완료후, 순차적으로 연락하여 개통안내를 드립니다.
                    <a class="c-text-link--bluegreen" href="/m/appForm/appFormDesignView.do?onOffType=3&operType=MNP3&indcOdrg=1&prdtSctnCd=LTE">가입신청서 작성하기</a>
                </p>
            </div>

            <!-- 배너 -->
            <div class="swiper-container swiper-event-banner friend-banner c-expand" id="swiperInvitBanner">
                <div class="swiper-wrapper">

                </div>
            </div>

            <div class="self-guide-wrap--type2 c-expand" id="swiperUsimGuide">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/content/img_self_opening_banner_01.png" alt="">
                        </div>
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/content/img_self_opening_banner_02.png" alt="">
                        </div>
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/content/img_self_opening_banner_03.png" alt="">
                        </div>
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/content/img_self_opening_banner_04.png" alt="">
                        </div>
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/content/img_self_opening_banner_05.png" alt="">
                        </div>
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/content/img_self_opening_banner_06.png" alt="">
                        </div>
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/content/img_self_opening_banner_07.png" alt="">
                        </div>
                    </div>
                    <div class="swiper-pagination u-mb--4"></div>
                </div>
            </div>
            <div class="self-opening-box c-expand u-mt--0">
                  <h3 class="c-heading c-heading--type2 u-mt--20">셀프개통 퀵 가이드</h3>
                  <iframe src="https://www.youtube.com/embed/ISyg89YFZZU" frameborder="0" allowfullscreen="1" style="width: 100%; height: 250px;" allow="autoplay; encrypted-media"></iframe>
                <div class="c-button-wrap u-mt--36 u-mb--16">
                    <a class="c-button c-button--full c-button--primary" href="javascript:void(0);" onclick="selfTimeChk();">셀프개통 바로가기</a>
                </div>
            </div>
            <h4 class="c-heading c-heading--type5">유의사항</h4>
            <hr class="c-hr c-hr--type3">
            <div class="c-accordion c-accordion--type1">
                <ul class="c-accordion__box" id="accordionB">
                    <li class="c-accordion__item">
                        <div class="c-accordion__head" data-acc-header>
                            <button class="c-accordion__button" type="button" aria-expanded="false">셀프개통 유의사항</button>
                        </div>
                        <div class="c-accordion__panel expand c-expand">
                            <div class="c-accordion__inside">
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray">셀프개통 중, 전 통신사 해지로 모바일 데이터가 끊길 수 있으므로 Wi-Fi 환경에서 개통 진행 바랍니다.</li>
                                    <li class="c-text-list__item u-co-gray">당일 작성 완료되지 않은 신청 정보는 창을 닫거나 개통이 완료되지 않을 경우 자동 삭제됩니다. 이 경우, 익일 재신청이 필요합니다.</li>
                                    <li class="c-text-list__item u-co-gray">유심 배송 요청으로 유심을 수령하신 경우, 유심 비용은 후청구 됩니다.</li>
                                    <li class="c-text-list__item u-co-gray">유심은 꼭 개통 완료 후 휴대폰에 장착해야 합니다.</li>
                                    <li class="c-text-list__item u-co-gray">미성년자는 셀프개통 대상에서 제외됩니다.</li>
                                    <li class="c-text-list__item u-co-gray">셀프개통을 통해 신규 가입한 고객은 ‘로밍차단 서비스’에 자동 가입(번호이동 가입 고객 제외) 됩니다.</li>
                                    <li class="c-text-list__item u-co-gray">셀프개통 신규가입은 명의당 1회선만 가능합니다.(단, 번호이동 가입은 제한에서 제외 됩니다.)</li>
                                    <li class="c-text-list__item u-co-gray">
                                        <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                                        <br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <script src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script>
            var selfOpeningSwiper = new Swiper('.swiper-container', {
                pagination : {
                    el : '.swiper-pagination',
                    type: 'fraction',
                },
            });
        </script>
    </t:putAttribute>
</t:insertDefinition>