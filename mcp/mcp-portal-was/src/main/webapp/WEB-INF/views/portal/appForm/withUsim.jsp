<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">USIM 있다면</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/appForm/withUsim.js?version=26-02-20"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="tab" value="${tab}">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">USIM 있다면</h2>
            </div>
            <div class="c-tabs c-tabs--type1 c-tabs--usim c-tabs--with-usim no-sticky" id="withUsim-tab" data-ui-tab>
              <div class="c-tabs__list" role="tablist">
                <button class="c-tabs__button " id="tab1" role="tab" aria-controls="tabpanel1" aria-selected="true" tabindex="0" data-name="appSimpleinfo"><p>셀프개통</p><span>5분 즉시 개통</span></button>
                <button class="c-tabs__button " id="tab2" role="tab" aria-controls="tabpanel2" aria-selected="false" tabindex="0" data-name="appCounselorInfo"><p>상담사 개통</p><span>&nbsp</span></button>
              </div>
            </div>
            <div class="c-tabs__panel" id="tabpanel1" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
                <div class="ly-page--deco self-open-bk--purple">
                    <div class="ly-avail--wrap">
                        <h3 class="c-headline">유심이 있다면<br>누구나 쉽게 5분이면 끝!</h3>
                        <div class="c-button-wrap c-button-wrap--left u-mt--24">
                            <a class="c-button c-button--sm c-button--transparent c-button-round--sm c-flex" href="javascript:void(0);" onclick="selfTimeChk();">
                                <span class="u-mr--16">셀프개통 시작</span>
                                <span class="c-hidden">바로가기</span>
                                <i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                            </a>
                            <button class="c-button c-button--sm c-button--transparent c-button-round--sm c-flex" data-scroll-top="#swiperUsimGuide|100">
                                <span class="u-mr--16">가이드 보기</span>
                                <span class="c-hidden">셀프개통 가이드 보기 바로가기</span>
                                <i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                            </button>
                            <button class="c-button c-button c-button--white" data-dialog-trigger="#join-info-modal" id="selfBtn" style="display:none;">셀프개통 가능 시간 안내 popup 호출</button>
                        </div>
                        <img class="c-headline--deco" src="/resources/images/portal/content/img_self_ofening.png" alt="">
                    </div>
                </div>
                <div class="c-row c-row--lg">
                    <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--64 u-mb--26">유심이 필요하신가요?</h4>
                    <hr class="c-hr c-hr--title">
                    <div class="c-button-wrap u-mt--30">
                        <a class="c-button--link" href="/appForm/reqSelfDlvry.do" title="다이렉트몰 유심 구매 이동하기">
                            <i class="c-icon c-icon--ktmm" aria-hidden="true"></i>
                            <span>다이렉트몰 유심 구매</span>
                        </a>
                        <a class="c-button--link" href="/direct/storeInfo.do" title="편의점/마트 유심 구매 이동하기">
                            <i class="c-icon c-icon--mart" aria-hidden="true"></i>
                            <span>편의점/마트 유심 구매</span>
                        </a>
                        <a class="c-button--link" href="/direct/openMarketInfo.do" title="오픈마켓 유심 구매 이동하기">
                            <i class="c-icon c-icon--openmarket" aria-hidden="true"></i>
                            <span>오픈마켓 유심 구매</span>
                        </a>
                    </div>
                    <p class="c-heading--medium c-heading--medium u-mt--37">셀프개통 가능시간</p>
                    <div class="c-box c-box--type1 u-mt--20">
                        <p class="c-bullet c-bullet--dot u-co-gray c-text--fs14 u-mt--2"><span class="c-text c-text--fs14 u-co-black u-mr--4">번호이동 10:00 ~ 19:50</span>(일요일, 신정/설/추석 당일 제외)</p>
                        <p class="c-bullet c-bullet--dot u-co-gray c-text--fs14 u-mt--8"><span class="c-text c-text--fs14 u-co-black u-mr--4">신규가입 08:00 ~ 21:50</span></p>
                        <p class="c-bullet c-bullet--fyr u-co-gray u-fs-14 u-mt--11">
                            eSIM으로도 셀프개통이 가능합니다. eSIM 개통 가능 여부를 확인해보세요.
                            <a class="c-text-link--bluegreen" href="/appForm/eSimInfom.do" title="eSIM 알아보기 페이지 이동하기">eSIM 알아보기</a>
                        </p>
                         <p class="c-bullet c-bullet--fyr u-co-gray u-fs-14 u-mt--11">
                     외국인,미성년자는 가입신청서 작성완료후, 순차적으로 연락하여 개통안내를 드립니다.
                            <a class="c-text-link--bluegreen" href="/appForm/appFormDesignView.do?onOffType=0&operType=MNP3&indcOdrg=1&prdtSctnCd=LTE"  title="eSIM 알아보기 페이지 이동하기">가입신청서 작성하기</a>
                        </p>
                    </div>
                </div>

                <!-- 배너 -->
                <div class="swiper-banner u-mt--64" id="swiperFriendInvite">
                    <div class="swiper-container">
                        <div class="swiper-wrapper" id="swiperArea">
                        </div>
                    </div>
                    <button class="swiper-banner-button--next swiper-button-next" type="button"></button>
                    <button class="swiper-banner-button--prev swiper-button-prev" type="button"></button>
                </div>

                <div class="ly-content--wrap self-open-bk--light-purple">
                    <div class="swiper-banner u-mt--64" id="swiperUsimGuide">
                        <div class="swiper-container">
                            <div class="swiper-wrapper">
                                <div class="swiper-slide">
                                    <img src="/resources/images/portal/content/img_usim_guide_swiper_01.png" alt="셀프개통 가이드 이것만 확인하세요! *PC와 모바일에서 모두 개통 가능합니다.">
                                </div>
                                <div class="swiper-slide">
                                    <img src="/resources/images/portal/content/img_usim_guide_swiper_02.png" alt="1: 가입유형(신규/번호이동) 및 요금제를 선택해 주세요.">
                                </div>
                                <div class="swiper-slide">
                                    <img src="/resources/images/portal/content/img_usim_guide_swiper_03.png" alt="2: 본인 정보 입력 및 신용카드/공인인증서로 본인 인증 후 사전체크를 진행해 주세요.">
                                </div>
                                <div class="swiper-slide">
                                    <img src="/resources/images/portal/content/img_usim_guide_swiper_04.png" alt="주의: 번호이동 신청 시 사전동의 요청 후 반드시 통신사 인증(ARS/문자)을 진행해 주셔야 합니다. 인증확인 후 개통사전체크 확인을 클릭하여 다음 단계로 진행해 주세요.">
                                </div>
                                <div class="swiper-slide">
                                    <img src="/resources/images/portal/content/img_usim_guide_swiper_05.png" alt="3: 구매한 유심카드 일련번호와 요금납부 정보 등을 입력 후, 개통요청을 클릭하면 잠시 후 개통이 완료됩니다.">
                                </div>
                                <div class="swiper-slide">
                                    <img src="/resources/images/portal/content/img_usim_guide_swiper_06.png" alt="4: 휴대폰의 유심 슬롯크기를 확인하시고, 크기에 맞게 분리해 주세요. 5: 휴대폰에 삽입하고, 전원을 껐다 켜세요. 연결이 확인되면 셀프개통 끝!">
                                </div>
                            </div>
                        </div>
                        <button class="swiper-banner-button--next swiper-button-next" type="button"></button>
                        <button class="swiper-banner-button--prev swiper-button-prev" type="button"></button>
                        <div class="swiper-controls-wrap u-box--w1100">
                            <div class="swiper-controls">
                                <div class="swiper-pagination" aria-hidden="true"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="ly-content--wrap self-open-bk--gray">
                    <div class="c-row c-row--lg">
                        <h3 class="c-group--head">셀프개통 퀵 가이드</h3>
                        <div class="opening-guide__image u-mt--24">
                            <iframe src="https://www.youtube.com/embed/ISyg89YFZZU" width="876" height="492" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen=""> </iframe>
                        </div>
                        <div class="c-button-wrap u-mt--50">
                            <a class="c-button c-button--lg c-button--primary c-button--w460" href="javascript:void(0);" onclick="selfTimeChk();">셀프개통 바로가기</a>
                        </div>
                    </div>
                </div>
                <div class="c-row c-row--lg">
                    <h5 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">유의사항</h5>
                    <hr class="c-hr c-hr--title u-mb--0">
                    <div class="c-accordion c-accordion--type3" data-ui-accordion>
                        <ul class="c-accordion__box">
                            <li class="c-accordion__item">
                                <div class="c-accordion__head">
                                    <strong class="c-accordion__title">셀프개통 유의사항</strong>
                                    <button class="runtime-data-insert c-accordion__button" id="acc_header_a1" type="button" aria-expanded="false" aria-controls="acc_content_a1"></button>
                                </div>
                                <div class="c-accordion__panel expand" id="acc_content_a1" aria-labelledby="acc_header_a1">
                                    <div class="c-accordion__inside">
                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                            <li class="c-text-list__item u-co-gray">
                                                          셀프개통 중, 전 통신사 해지로 모바일 데이터가 끊길 수 있으므로 Wi-Fi 환경에서 개통 진행 바랍니다.
                                            </li>
                                            <li class="c-text-list__item u-co-gray">
                                                          당일 작성 완료되지 않은 신청 정보는 창을 닫거나 개통이 완료되지 않을 경우 자동 삭제됩니다. 이 경우, 익일 재신청이 필요합니다.
                                            </li>
                                            <li class="c-text-list__item u-co-gray">
                                                  유심 배송 요청으로 유심을 수령하신 경우, 유심 비용은 후청구 됩니다.
                                            </li>
                                            <li class="c-text-list__item u-co-gray">
                                                          유심은 꼭 개통 완료 후 휴대폰에 장착해야 합니다.
                                            </li>
                                            <li class="c-text-list__item u-co-gray">
                                                   미성년자는 셀프개통 대상에서 제외됩니다.
                                               </li>
                                            <li class="c-text-list__item u-co-gray">
                                                   셀프개통을 통해 신규 가입한 고객은 ‘로밍차단 서비스’에 자동 가입(번호이동 가입 고객 제외) 됩니다.
                                            </li>
                                            <li class="c-text-list__item u-co-gray">
                                                          셀프개통 신규가입은 명의당 1회선만 가능합니다.(단, 번호이동 가입은 제한에서 제외 됩니다.)
                                            </li>
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
            </div>
            <div class="c-tabs__panel" id="tabpanel2" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
                <div class="ly-page--deco self-open-bk--blue">
                    <div class="ly-avail--wrap">
                        <h3 class="c-headline">신청서를 작성하시면 AI 상담사 또는 개통 상담사가<br />순차적으로 개통을 도와드립니다.</h3>
                        <p class="c-headline--sub">야간에 상담사 개통 신청을 하신 경우 오전 9시 이후 순차적으로 전화드립니다.</p>
                        <div class="c-button-wrap c-button-wrap--left u-mt--20">
                            <a class="c-button c-button--sm c-button--transparent c-button-round--sm c-flex" href="/appForm/appFormDesignView.do">
                                <span class="u-mr--16">가입신청서 작성</span>
                                <span class="c-hidden">바로가기</span>
                                <i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                            </a>
                        </div>
                        <img class="c-headline--deco" src="/resources/images/portal/content/img_connect_counselor.png" alt="">
                    </div>
                </div>
                <div class="c-row c-row--lg">
                    <div class="usim-guide-wrap">
                        <p class="c-text u-co-gray">
                               개통안내전화를 기다릴 필요 없이<span class="u-co-black c-heading--medium"> 직접 개통하는 셀프개통</span> 방법도 확인해보세요.
                            <a class="c-text-link--bluegreen-type2" href="/appForm/appSimpleInfo.do" title="유심 셀프개통 페이지 바로가기">유심 셀프개통 바로가기</a>
                        </p>
                        <p class="c-text u-co-gray u-mt--6">
                            eSIM으로 셀프개통이 가능합니다. eSIM 개통 가능 여부를 확인해 보세요.
                            <a class="c-text-link--bluegreen-type2" href="/appForm/eSimInfom.do" title="eSIM 알아보기 페이지 바로가기">eSIM 알아보기</a>
                        </p>
                    </div>
                    <p class="c-group--head c-heading--medium u-mt--33">셀프개통 가능시간</p>
                    <div class="c-box c-box--type1 u-mt--20">
                        <p class="c-bullet c-bullet--dot u-co-gray c-text--fs14 u-mt--2"><span class="c-text c-text--fs14 u-co-black u-mr--4">번호이동 10:00 ~ 19:50</span>(일요일, 신정/설/추석 당일 제외)</p>
                        <p class="c-bullet c-bullet--dot u-co-gray c-text--fs14 u-mt--8 u-mb--4"><span class="c-text c-text--fs14 u-co-black u-mr--4">신규가입 08:00 ~ 21:50</span></p>
                        <p class="c-bullet c-bullet--fyr u-co-gray u-fs-14 u-mt--11">
                     외국인,미성년자는 가입신청서 작성완료후, 순차적으로 연락하여 개통안내를 드립니다.
                            <a class="c-text-link--bluegreen" href="/appForm/appFormDesignView.do?onOffType=0&operType=MNP3&indcOdrg=1&prdtSctnCd=LTE"  title="eSIM 알아보기 페이지 이동하기">가입신청서 작성하기</a>
                        </p>
                    </div>
                    <p class="c-group--head c-heading--medium u-mt--40">* 통신요금 미납정보 조회 방법 안내</p>
                    <p class="c-bullet c-bullet--fyr u-fs-14 u-ml--10">개통을 더욱 빠르고 원활하게 진행하시려면, 아래 사이트에서 통신요금 미납 여부와 단기간 다회선 가입 이력을 사전에 확인 해보시는 것을 권장 드립니다.<br />(통신요금 미납이 있거나, 최근 180일 이내에 3회선 이상을 가입한 이력이 있을 경우, 개통이 어려울 수 있습니다.)</p>
                    <hr class="c-hr c-hr--title u-mt--24 u-mb--0">
                    <div class="c-accordion c-accordion--type3" data-ui-accordion>
                      <ul class="c-accordion__box">
                        <li class="c-accordion__item">
                          <div class="c-accordion__head u-pl--0">
                            <strong class="c-accordion__title">통신요금 미납정보 조회 사이트 바로가기</strong>
                            <button class="runtime-data-insert c-accordion__button" id="appCounselorAcc_header" type="button" aria-expanded="false" aria-controls="appCounselorAcc_content">
                              <span class="c-hidden">통신요금 미납정보 조회 사이트 바로가기 상세열기</span>
                            </button>
                          </div>
                          <div class="c-accordion__panel expand" id="appCounselorAcc_content" aria-labelledby="appCounselorAcc_header">
                            <div class="c-accordion__inside">
                                <ul class="c-text-list c-bullet c-bullet--hyphen">
                                    <li class="c-text-list__item">링크 : 방송통신 신용정보 공동관리 (<a href="https://www.credit.or.kr/" title="방송통신 신용정보 공동관리 페이지 새창 열기" target="_blank">credit.or.kr</a>)</li>
                                    <li class="c-text-list__item">
                                           접속경로 :
                                       <ul class="c-text-list u-mt--8">
                                           <li class="c-text-list__item">1) 사이트 접속 후 ‘본인신용정보 조회하기’ 클릭</li>
                                           <li class="c-text-list__item">2) 공동인증서 또는 휴대폰 인증으로 간편한 본인확인 진행</li>
                                           <li class="c-text-list__item">3) 현재 통신사 미납정보와 단기간 다회선 가입여부 확인 가능</li>
                                       </ul>
                                    </li>
                                </ul>
                            </div>
                          </div>
                        </li>
                      </ul>
                    </div>
                    <a class="usim-banner u-mt--64" href="/mmobile/ktmMobileUsimGuid.do" style="display: block;">
                        <strong class="usim-banner__title">유심 및 단말 개통가이드</strong>
                        <p class="usim-banner__text">셀프개통 누구나 쉽게 5분이면 끝!</p>
                        <i class="usim-banner__image" aria-hidden="true">
                            <img src="/resources/images/portal/content/img_usim_banner.png" alt="">
                        </i>
                    </a>
                </div>
            </div>
        </div>

        <div class="c-modal c-modal--sm" id="join-info-modal" role="dialog" tabindex="-1" aria-labelledby="#join-info-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="self-opening">
                            <strong class="self-opening__title">신규 가입만 가능한 시간입니다.</strong>
                            <p class="self-opening__text u-mb--0">
                                   번호 이동 셀프 개통은 <br> <a class="u-td-underline u-fw--medium" href="/appForm/appCounselorInfo.do">온라인 가입신청</a>을 이용해 주세요.
                            </p>
                            <span class="c-text-label c-text-label--type2 c-text-label--red u-mt--20 rectangle">셀프개통 가능시간</span>
                            <div class="c-table">
                                <table>
                                    <caption>번호이동, 신규가입을 포함한 셀프개통 가능 시간 정보</caption>
                                    <colgroup>
                                        <col style="width: 50%">
                                        <col style="width: 50%">
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th scope="col">번호이동</th>
                                            <th scope="col">신규가입</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <strong class="self-opening__highlight">10:00 ~ 19:50</strong>
                                                <p class="c-bullet c-bullet--fyr u-co-gray">일요일, 신정/설/추석 당일 제외</p>
                                            </td>
                                            <td>
                                                <strong class="self-opening__highlight">08:00 ~ 21:50</strong>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="c-button-wrap u-mt--48">
                            <a class="c-button c-button--full c-button--primary" href="/appForm/appFormDesignView.do" id="movBtn" style="display:none;">확인</a>
                            <a class="c-button c-button--full c-button--primary" data-dialog-close id="closeBtn">확인</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener('UILoaded', function() {
                KTM.swiperA11y('#swiperUsimGuide .swiper-container', {
                    navigation : {
                        nextEl : '#swiperUsimGuide .swiper-button-next',
                        prevEl : '#swiperUsimGuide .swiper-button-prev',
                    },
          	        pagination: {
        	          el: '#swiperUsimGuide .swiper-pagination',
        	          type: 'fraction',
        	        },
                });
            });
        </script>
    </t:putAttribute>
</t:insertDefinition>
