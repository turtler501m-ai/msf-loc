<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>


<t:insertDefinition name="mlayoutDefaultTitle">
    <t:putAttribute name="titleAttr">유심칩 및 단말 개통 가이드 </t:putAttribute>
<t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript">
            $(window).load(function(){
                var accordionEl = $("#accodrionContent1")[0];
                var accordionIns = KTM.Accordion.getInstance(accordionEl);
                accordionIns.openAll();
                var tab = $("#tab").val();
                if(tab=="1"){
                    $(".c-tabs__button").eq(0).trigger("click");
                } else {
                    $(".c-tabs__button").eq(1).trigger("click");
                }
            });
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    유심 및 단말 개통 가이드
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="c-tabs c-tabs--type2" data-changed-scroll>
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button" data-tab-header="#tabB1-panel">유심 개통 가이드</button>
                    <button class="c-tabs__button" data-tab-header="#tabB2-panel">단말 개통 가이드</button>
                </div>
                <div class="c-tabs__panel" id="tabB1-panel">
                    <h3 class="c-heading c-heading--type5 u-ta-center u-mb--0">
                        <b>유심 개통 안내</b>
                    </h3>
                    <hr class="c-hr c-hr--title u-mb--0">
                    <div class="opening-guide">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">1</span>
                            <p class="opening-guide__text">데이터 무제한에서 초저가 요금제까지 다양한 <br>요금제에서 원하시는 요금제를 찾아보세요.</p>
                            <div class="opening-guide__image u-mt--24">
                                <img src="/resources/images/mobile/content/img_usim_opening_guide_01.png" alt="">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">현재 사용하고 계시는 또는 자급제/중고폰 등 사용할 휴대폰이 있다면 유심만 개통하여 약정 없이 사용할 수 있습니다.</li>
                                <li class="c-text-list__item u-co-gray">이벤트 페이지에서 현재 진행되고 있는 가입 혜택을 확인할 수 있습니다.
                                    <a class="c-button c-button--underline c-button--sm fs-13 u-co-sub-4" href="/m/event/eventList.do">이벤트 바로가기</a>
                                </li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--type3">
                        <div class="opening-guide__item u-pb--0">
                            <span class="opening-guide__step">2</span>
                            <p class="opening-guide__text">유심 보유 여부를 확인하시어 <br>개통유형을 선택해 주세요.</p>
                            <ul class="c-text-list c-bullet c-bullet--dot u-mt--24">
                                <li class="c-text-list__item u-co-gray">셀프개통 : 보유하고 있는 유심으로 바로 개통하여 사용하실 수 있습니다.</li>
                                <li class="c-text-list__item u-co-gray">상담사 개통 신청 : 가입신청서를 작성하시면 순차적으로 AI상담사 또는 개통상담사가 전화를 드려 본인확인을 진행합니다.</li>
                            </ul>
                            <div class="c-box c-box--type1 u-mt--24">
                                <strong class="c-text c-text--type3 u-fw--medium">유심 구매 방법</strong>
                                <p class="c-text c-text--type4 u-mt--16">
                                    <b>1. 다이렉트몰, 편의점, 오픈마켓에서 구매</b>
                                </p>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">다이렉트몰에서 구매 시 바로배송(당일 퀵)으로 평균 2시간이내 유심을 받아보시거나 택배로 유심을 받아보실 수 있습니다.(배송비 무료)</li>
                                    <li class="c-text-list__item u-co-gray">편의점/오픈마켓 : 유심을 판매 중인 편의점, 오픈마켓에서도 유심 구매가 가능합니다. 구입 가능한 판매처를 확인해보세요.</li>
                                    <li class="link-item">
                                        <a class="c-button c-button--underline c-button--sm u-co-sub-4" href="/m/appForm/reqSelfDlvry.do">다이렉트몰 구매하기</a>
                                    </li>
                                    <li class="link-item">
                                        <a class="c-button c-button--underline c-button--sm u-co-sub-4" href="/m/direct/openMarketInfo.do">오픈마켓 구매하기</a>
                                    </li>
                                    <li class="link-item">
                                        <a class="c-button c-button--underline c-button--sm u-co-sub-4" href="/m/direct/storeInfo.do">편의점/마트 구매하기</a>
                                    </li>
                                </ul>
                                <p class="c-text c-text--type4 u-mt--24">
                                    <b>2. 온라인 신청서 작성 후 유심 받기</b>
                                </p>
                                <p class="c-bullet c-bullet--dot u-co-gray">가입신청서 작성 시 배송방법을 선택 후 신청서 작성을 완료하면 작성하신 주소로 유심이 배송됩니다.</p>
                            </div>
                        </div>
                        <div class="c-accordion c-accordion--type1" id="accodrionContent1">
                            <ul class="c-accordion__box" id="accordionB">
                                <li class="c-accordion__item">
                                    <div class="c-accordion__head" data-acc-header>
                                        <button class="c-accordion__button" type="button" aria-expanded="false">셀프개통 가입절차</button>
                                    </div>
                                    <div class="c-accordion__panel expand c-expand">
                                        <div class="c-accordion__inside">
                                            <img src="/resources/images/mobile/content/img_usim_opening_guide_02.png" alt="">
                                            <strong class="c-text c-text--type4 u-mt--24">
                                                <b>셀프개통 가능시간</b>
                                            </strong>
                                            <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                                <li class="c-text-list__item u-co-gray">번호이동 10:00 ~ 19:50 <br> (일요일, 신정/설/추석 당일 제외)</li>
                                                <li class="c-text-list__item u-co-gray">신규가입 08:00 ~ 21:50 <br> (미성년/외국인 셀프개통 불가)</li>
                                            </ul>
                                            <strong class="c-text c-text--type4 u-mt--24 u-mb--16">
                                                <b>셀프개통 퀵 가이드</b>
                                            </strong>
                                            <iframe src="https://www.youtube.com/embed/ISyg89YFZZU" frameborder="0" allowfullscreen="1" style="width: 100%; height: 250px;" allow="autoplay; encrypted-media"></iframe>
                                            <!-- <img src="/resources/images/mobile/content/temp_video.png" alt=""> -->
                                            <div class="c-button-wrap u-mt--48">
                                                <a class="c-button c-button--full c-button--primary" href="/m/appForm/appSimpleInfo.do">셀프개통 바로가기</a>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li class="c-accordion__item">
                                    <div class="c-accordion__head" data-acc-header>
                                        <button class="c-accordion__button" type="button" aria-expanded="false">온라인신청(상담가입) 가입절차</button>
                                    </div>
                                    <div class="c-accordion__panel expand c-expand">
                                        <div class="c-accordion__inside">
                                            <img src="/resources/images/mobile/content/img_usim_opening_guide_03.png" alt="">
                                            <ul class="c-text-list c-bullet c-bullet--fyr">
                                                <li class="c-text-list__item u-co-gray">신규가입 및 전화상담 부재 시(2번) 자동으로 접수 취소 됩니다.</li>
                                                <li class="c-text-list__item u-co-gray">바로배송(퀵) 배송 시 유심비의 선결제가 필요하며 유심번호를 입력하셔야 개통이 가능합니다.</li>
                                            </ul>
                                            <div class="c-button-wrap u-mt--48">
                                                <a class="c-button c-button--full c-button--primary" href="/m/appForm/appCounselorInfo.do">온라인신청 바로가기</a>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--type3 u-mt--40">
                        <div class="opening-guide__item u-pb--0">
                            <span class="opening-guide__step">3</span>
                            <p class="opening-guide__text">신청서를 작성해 주세요.</p>
                            <div class="c-box c-box--type1 u-mt--40">
                                <strong class="c-text c-text--type4"> <b>가입신청서 작성 시 준비사항</b></strong>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">본인인증 용 : 신용카드, 범용인증서, 간편인증(네이버인증서, PASS인증서, toss인증서, 카카오인증서)중 택 1, 신분증</li>
                                    <li class="c-text-list__item u-co-gray">요금납부 용 : 본인 명의의 자동이체 계좌번호 또는 신용카드</li>
                                </ul>
                                <p class="c-bullet c-bullet--fyr u-co-gray">미성년자는 가입자 본인 외 법정대리인의 정보가 필요합니다.</p>
                                <b class="c-text c-text--type3 u-block u-mt--16">
                                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>Tip
                                </b>
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray">신청 중간에 중단되어도 7일 이내 “신청조회” 에서 이어서 작성할 수 있습니다.</li>
                                </ul>
                            </div>
                        </div>
                        <hr class="c-hr c-hr--type3 u-mt--40">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">4</span>
                            <p class="opening-guide__text">
                                셀프개통은 바로 개통이 완료되며, <br>온라인 신청은 선택하신 절차에 따라 <br>개통/배송이 진행됩니다.
                            </p>
                            <div class="opening-guide__image u-mt--24">
                                <img src="/resources/images/mobile/content/img_usim_opening_guide_04.png" 	alt="">
                            </div>
                        </div>
                        <hr class="c-hr c-hr--type3">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">5</span>
                            <p class="opening-guide__text">유심 장착 및 전화번호 등록</p>
                            <div class="c-box c-box--type1 u-mt--24">
                                <p class="c-text c-text--type4">
                                    <b>1. 배송된 USIM칩을 USIM장착 부분에 끼워 줍니다.</b>
                                </p>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">배터리 분리형 : 휴대폰 뒷면에서 배터리 커버 분리 후 장착</li>
                                    <li class="c-text-list__item u-co-gray">배터리 일체형 : 유심 분리용 핀으로 휴대폰 옆면의 구멍을 눌러, 유심 슬롯에 장착</li>
                                </ul>
                                <p class="c-text c-text--type4 u-mt--24">
                                    <b>2. USIM칩 장착 후 배터리와 후면 커버를 부착한 후, 핸드폰 전원 껐다 켰다를 2-3회 반복합니다.</b>
                                </p>
                                <p class="c-bullet c-bullet--fyr u-co-gray u-mt--16">'USIM카드 장착을 확인해주세요' 또는 'USIM카드를 인식 할 수 없습니다. 고객센터 문의 해주세요' 등의 메시지가 보여질 경우, kt M모바일 고객센터 1899-5000 로 전화해 주시기 바랍니다.</p>
                            </div>
                            <div class="opening-guide__image u-mt--24">
                                <img src="/resources/images/mobile/content/img_usim_opening_guide_05.png" alt="">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="c-tabs__panel" id="tabB2-panel">
                    <h3 class="c-heading c-heading--type5 u-ta-center u-mb--0">
                        <b>휴대폰 구매 안내</b>
                    </h3>
                    <hr class="c-hr c-hr--title u-mb--0">
                    <div class="opening-guide">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">1</span>
                            <p class="opening-guide__text">원하시는 조건에 맞는 휴대폰을 찾아 골라보세요. <br>약정기간이 부담되시는 분은 약정 없이 구매할 <br>수도 있습니다.</p>
                            <div class="opening-guide__image u-mt--24">
                                <img src="/resources/images/mobile/content/img_opening_guide_01.png" alt="">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">비교하고 싶은 핸드폰을 2개까지 선택하여 비교할 수 있습니다.</li>
                            </ul>
                            <div class="opening-guide__image">
                                <img src="/resources/images/mobile/content/img_opening_guide_02.png" alt="">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">비교함에서도 요금제별 월 납부금액과 스펙을 비교하고 바로 주문이 가능합니다.</li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--type3">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">2</span>
                            <p class="opening-guide__text">휴대폰의 색상, 용량을 선택 후 가입조건을 <br>선택해 주세요.</p>
                            <div class="opening-guide__image">
                                <img src="/resources/images/mobile/content/img_opening_guide_03.png" alt="">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">용량, 색상을 선택하고 가입할 수 있습니다.</li>
                            </ul>
                            <div class="opening-guide__image">
                                <img src="/resources/images/mobile/content/img_opening_guide_04.png" alt="">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">무약정 선택 시 공통지원금은 없으나 유심 요금제로 가입이 가능합니다.</li>
                                <li class="c-text-list__item u-co-gray">가입유형, 약정, 요금제 등 가입조건을 선택하면 월 납부금액을 확인할 수 있습니다.</li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--type3">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">3</span>
                            <p class="opening-guide__text">온라인신청서를 작성해 주세요.</p>
                            <div class="c-box c-box--type1 u-mt--40">
                                <p class="c-text c-text--type4">
                                    <b>가입신청서 작성 시 준비사항</b>
                                </p>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">본인인증 용 : 신용카드, 범용인증서, 간편인증(네이버인증서, PASS인증서, toss인증서, 카카오인증서)중 택 1, 신분증</li>
                                    <li class="c-text-list__item u-co-gray">요금납부 용 : 본인 명의의 자동이체 계좌번호 또는 신용카드</li>
                                </ul>
                            </div>
                            <div class="opening-guide__image u-mt--24">
                                <img src="/resources/images/mobile/content/img_opening_guide_05.png" alt="">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">미성년자는 가입자 본인 외 법정대리인의 본인인증이 필요합니다.</li>
                                <li class="c-text-list__item u-co-gray">외국인 가입시에는 외국인등록번호와 여권번호, 국적, 체류기간이 필요합니다.</li>
                            </ul>
                            <b class="c-text c-text--type3 u-block u-mt--16">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>Tip
                            </b>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">신청 중간에 중단되어도 7일 이내 “신청조회” 에서 이어서 작성할 수 있습니다.</li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--type3">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">4</span>
                            <p class="opening-guide__text">배송 및 개통</p>
                            <div class="opening-guide__image u-mt--24">
                                <img src="/resources/images/mobile/content/img_opening_guide_07.png" alt="">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">주문내역은 배송조회에서 확인이 가능합니다.</li>
                                <li class="c-text-list__item u-co-gray">구매하신 휴대폰은 본인 확인을 위한 전화 상담 후 개통되어 택배로 발송됩니다.</li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--type3">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">5</span>
                            <p class="opening-guide__text">유심 장착 및 전화번호 등록</p>
                            <div class="c-box c-box--type1 u-mt--24">
                                <p class="c-text c-text--type4">
                                    <b>1. 배송된 USIM칩을 USIM장착 부분에 끼워 줍니다.</b>
                                </p>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">배터리 분리형 : 휴대폰 뒷면에서 배터리 커버 분리 후 장착</li>
                                    <li class="c-text-list__item u-co-gray">배터리 일체형 : 유심 분리용 핀으로 휴대폰 옆면의 구멍을 눌러, 유심 슬롯에 장착</li>
                                </ul>
                                <p class="c-text c-text--type4 u-mt--24">
                                    <b>2. USIM칩 장착 후 배터리와 후면 커버를 부착한 후, 핸드폰 전원 껐다 켰다를 2-3회 반복합니다.</b>
                                </p>
                                <p class="c-bullet c-bullet--fyr u-co-gray u-mt--16">'USIM카드 장착을 확인해주세요' 또는 'USIM카드를 인식 할 수 없습니다. 고객센터 문의 해주세요' 등의 메시지가 보여질 경우, kt M모바일 고객센터 1899-5000 로 전화해 주시기 바랍니다.</p>
                            </div>
                            <div class="opening-guide__image u-mt--24">
                                <img src="/resources/images/mobile/content/img_opening_guide_08.png" alt="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<input type="hidden" id="tab" name="tab" value="${tab}">
    </t:putAttribute>
</t:insertDefinition>