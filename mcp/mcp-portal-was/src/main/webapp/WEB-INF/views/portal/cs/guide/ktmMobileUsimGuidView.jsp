<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefaultTitle">

    <t:putAttribute name="titleAttr">유심칩 및 단말 개통 가이드</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">

    <script>
    $(document).ready(function(){
        setTimeout(function(){
            $('#subbody_loading').hide();
        }, 200);
    });
    </script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-loading" id="subbody_loading">
            <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
        </div>

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">유심 및 단말 개통 가이드</h2>
            </div>

            <div class="c-tabs c-tabs--type1" data-ui-tab data-changed-scroll>
                <div class="c-tabs__list" role="tablist">
                    <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabB1panel" aria-selected="false" tabindex="-1">유심개통 가이드</button>
                    <button class="c-tabs__button" id="tab2" role="tab" aria-controls="tabB2panel" aria-selected="false" tabindex="-1">단말개통 가이드</button>
                </div>
            </div>
            <div class="c-tabs__panel" id="tabB1panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
                <div class="c-row c-row--lg">
                    <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">유심 개통 안내</h3>
                    <hr class="c-hr c-hr--title u-mb--0">
                    <div class="opening-guide">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">1</span>
                            <p class="opening-guide__text">데이터 무제한에서 초저가 요금제까지 <br>다양한 요금제에서 원하시는 요금제를 찾아보세요.</p>
                            <div class="opening-guide__image">
                                <img src="/resources/images/portal/content/img_usim_opening_guide_01.png" alt="상품소개 페이지에서 다양한 요금제를 확인하실 수 있습니다.">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">현재 사용하고 계시는 또는 자급제/중고폰 등 사용할 휴대폰이 있다면 유심만 개통하여 약정 없이 사용할 수 있습니다.</li>
                                <li class="c-text-list__item u-co-gray">이벤트 페이지에서 현재 진행되고 있는 가입 혜택을 확인할 수 있습니다.
                                    <a class="c-text c-text--underline u-ml--6 u-co-sub-4" href="/event/eventBoardList.do">이벤트 바로가기</a>
                                </li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--basic">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">2</span>
                            <p class="opening-guide__text">유심 보유 여부를 확인하시어 개통유형을 선택해 주세요.</p>
                            <ul class="c-text-list c-bullet c-bullet--dot u-mt--40">
                                <li class="c-text-list__item u-co-gray">셀프개통 : 보유하고 있는 유심으로 바로 개통하여 사용하실 수 있습니다.</li>
                                <li class="c-text-list__item u-co-gray">상담사 개통 신청 : 가입신청서를 작성하시면 순차적으로 AI상담사 또는 개통상담사가 전화를 드려 본인확인을 진행합니다.</li>
                            </ul>
                            <div class="c-box c-box--type1 u-mt--40">
                                <strong class="c-text c-text--fs15 u-fw--bold">유심 구매 방법</strong>
                                <p class="c-text c-text--fs15 u-fw--medium u-mt--20">1.다이렉트몰, 편의점, 오픈마켓에서 구매</p>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">다이렉트몰에서 구매 시 바로배송(당일 퀵)으로 평균 2시간이내 유심을 받아보시거나 택배로 유심을 받아보실 수 있습니다.(배송비 무료)</li>
                                    <li class="c-text-list__item u-co-gray">편의점/오픈마켓 : 유심을 판매 중인 편의점, 오픈마켓에서도 유심 구매가 가능합니다. 구입 가능한 판매처를 확인해보세요.</li>
                                    <li class="u-mt--16">
                                        <a class="c-text c-text--underline u-co-sub-4" href="/appForm/reqSelfDlvry.do">다이렉트몰 구매하기</a>
                                        <a class="c-text c-text--underline u-ml--16 u-co-sub-4" href="/direct/openMarketInfo.do">오픈마켓 구매하기</a>
                                        <a class="c-text c-text--underline u-ml--16 u-co-sub-4" href="/direct/storeInfo.do">편의점/마트 구매하기</a>
                                    </li>
                                </ul>
                                <p class="c-text c-text--fs15 u-fw--medium u-mt--24">2. 온라인 신청서 작성 후 유심 받기</p>
                                <p class="c-bullet c-bullet--dot u-co-gray u-mt--16">가입신청서 작성 시 배송방법을 선택 후 신청서 작성을 완료하면 작성하신 주소로 유심이 배송됩니다.</p>
                            </div>
                            <div class="c-accordion c-accordion--type3 u-mt--24" data-ui-accordion>
                                <ul class="c-accordion__box">
                                    <li class="c-accordion__item">
                                        <div class="c-accordion__head">
                                            <strong class="c-accordion__title">셀프개통 가입절차</strong>
                                            <button class="runtime-data-insert c-accordion__button is-active" id="acc_header_c1" type="button" aria-expanded="false" aria-controls="acc_content_c1">
                                            </button>
                                        </div>
                                        <div class="c-accordion__panel expand" id="acc_content_c1" aria-labelledby="acc_header_c1">
                                            <div class="c-accordion__inside">
                                                <img src="/resources/images/portal/content/img_usim_opening_guide_02.png" alt="1. 유심구매 2. 셀프개통 3. 유심장착">
                                                <strong class="c-text c-text--fs15 u-fw--medium u-mt--20">셀프개통 가능시간</strong>
                                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                                    <li class="c-text-list__item u-co-gray">번호이동 10:00 ~ 19:50 (일요일, 신정/설/추석 당일 제외)</li>
                                                    <li class="c-text-list__item u-co-gray">신규가입 08:00 ~ 21:50 (미성년/외국인 셀프개통 불가)</li>
                                                </ul>
                                                <strong class="c-text c-text--fs15 u-fw--medium u-mt--24">셀프개통	퀵 가이드</strong>
                                                <div class="opening-guide__image u-mt--16">
                                                    <iframe src="https://www.youtube.com/embed/ISyg89YFZZU" width="876" height="492" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen> </iframe>
                                                </div>
                                                <div class="c-button-wrap u-mt--40">
                                                    <a class="c-button c-button--lg c-button--primary c-button--w460" href="/appForm/appSimpleInfo.do" target="_blank" title="새창열림">셀프개통 바로가기</a>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="c-accordion__item">
                                        <div class="c-accordion__head">
                                            <strong class="c-accordion__title">온라인신청(상담가입) 가입절차</strong>
                                            <button class="runtime-data-insert c-accordion__button is-active" id="acc_header_c2" type="button" aria-expanded="false" aria-controls="acc_content_c2">
                                            </button>
                                        </div>
                                        <div class="c-accordion__panel expand" id="acc_content_c2" aria-labelledby="acc_header_c2">
                                            <div class="c-accordion__inside">
                                                <img src="/resources/images/portal/content/img_usim_opening_guide_03.png" alt="" aria-hidden="true">
                                                <div class="c-hidden">
                                                    <ul>
                                                        <li>1. 신청서 작성<strong>당일배송/택배배송 선택 시</strong>
                                                            <ul>
                                                                <li>1. 전화상담(본인확인)</li>
                                                                <li>2. (유심미보유 시) 배송/유심수령</li>
                                                            </ul> <strong>바로배송(퀵) 배송 선택 시</strong>
                                                            <ul>
                                                                <li>1. 배송/유심 및 SMS 수령</li>
                                                                <li>2. 유심번호입력</li>
                                                                <li>3. 전화상담(본인확인)</li>
                                                            </ul>
                                                        </li>
                                                        <li>2. 상담사 개통진행</li>
                                                        <li>3. 유심장착</li>
                                                    </ul>
                                                </div>
                                                <ul class="c-text-list c-bullet c-bullet--fyr u-mt--16">
                                                    <li class="c-text-list__item u-co-gray">신규가입 및 전화상담 부재 시(2번) 자동으로 접수 취소 됩니다.</li>
                                                    <li class="c-text-list__item u-co-gray">바로배송(퀵) 배송 시 유심비의 선결제가 필요하며 유심번호를 입력하셔야 개통이 가능합니다.</li>
                                                </ul>
                                                <div class="c-button-wrap u-mt--48">
                                                    <a class="c-button c-button--lg c-button--primary c-button--w460" href="/appForm/appCounselorInfo.do">온라인신청 바로가기</a>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <hr class="c-hr c-hr--basic">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">3</span>
                            <p class="opening-guide__text">신청서를 작성해 주세요.</p>
                            <div class="c-box c-box--type1 u-mt--40">
                                <strong class="c-text c-text--fs15 u-fw--medium">가입신청서 작성 시 준비사항</strong>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">본인인증 용 : 신용카드, 범용인증서, 간편인증(네이버인증서, PASS인증서, toss인증서, 카카오인증서)중 택 1, 신분증</li>
                                    <li class="c-text-list__item u-co-gray">요금납부 용 : 본인 명의의 자동이체 계좌번호 또는 신용카드</li>
                                </ul>
                                <p class="c-bullet c-bullet--fyr u-co-gray">미성년자는 가입자 본인 외 법정대리인의 정보가 필요합니다.</p>
                                <b class="c-text c-text--type3 u-block u-mt--24">
                                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>Tip
                                </b>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">신청 중간에 중단되어도 7일 이내 “신청조회” 에서 이어서 작성할 수 있습니다.</li>
                                </ul>
                            </div>
                        </div>
                        <hr class="c-hr c-hr--basic">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">4</span>
                            <p class="opening-guide__text">
                                셀프개통은 바로 개통이 완료되며, <br>온라인 신청은 선택하신 절차에 따라 개통/배송이 진행됩니다.
                            </p>
                            <div class="opening-guide__image">
                                <img src="/resources/images/portal/content/img_usim_opening_guide_04.png" alt="가입조건 선택 단계에서 개통유형을 셀프개통으로 선택">
                            </div>
                        </div>
                        <hr class="c-hr c-hr--basic">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">5</span>
                            <p class="opening-guide__text">유심 장착 및 전화번호 등록</p>
                            <div class="c-box c-box--type1 u-mt--40">
                                <p class="c-text c-text--fs15 u-fw--medium">1. 배송된 USIM칩을 USIM장착 부분에 끼워 줍니다.</p>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">배터리 분리형 : 휴대폰 뒷면에서 배터리 커버 분리 후 장착</li>
                                    <li class="c-text-list__item u-co-gray">배터리 일체형 : 유심 분리용 핀으로 휴대폰 옆면의 구멍을 눌러, 유심 슬롯에 장착</li>
                                </ul>
                                <p class="c-text c-text--fs15 u-fw--medium u-mt--24">2.
                                    USIM칩 장착 후 배터리와 후면 커버를 부착한 후, 핸드폰 전원 껐다 켰다를 2-3회 반복합니다.</p>
                                <p class="c-bullet c-bullet--fyr u-co-gray">'USIM카드 장착을 확인해주세요' 또는 'USIM카드를 인식 할 수 없습니다. 고객센터 문의 해주세요' 등의 메시지가 보여질 경우, kt M모바일 고객센터 1899-5000 로 전화해주시기 바랍니다.</p>
                            </div>
                            <div class="opening-guide__image u-mt--24">
                                <img src="/resources/images/portal/content/img_usim_opening_guide_05.png" alt="" aria-hidden="true">
                                <div class="c-hidden">
                                    <strong>전화번호 등록방법</strong>
                                    <p>일반적으로 핸드폰에 유심(USIM)을 장착하면 자동으로 번호가 등록되지만, 등록되지 않을 경우 아래와 같은 방법으로 등록이 가능합니다.</p>
                                    <ul>
                                        <li>1. 휴대폰에 USIM을 장착하고 전원을 켜세요.</li>
                                        <li>2. [번호등록]을 선택해주세요.</li>
                                        <li>3. 비밀번호 *147359*682* 를 입력하세요.</li>
                                        <li>4. “번호등록 중입니다” 메시지 후 번호 수신이 완료되면 [확인] 버튼을 눌러주세요.</li>
                                        <li>5. 자동으로 꺼졌다 켜지면 사용 가능합니다.</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="c-tabs__panel" id="tabB2panel" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
                <div class="c-row c-row--lg">
                    <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">휴대폰 구매 안내</h3>
                    <hr class="c-hr c-hr--title u-mb--0">
                    <div class="opening-guide">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">1</span>
                            <p class="opening-guide__text">
                                원하시는 조건에 맞는 휴대폰을 찾아 골라보세요. <br>약정기간이 부담되시는 분은 약정 없이 구매할 수도 있습니다.
                            </p>
                            <div class="opening-guide__image">
                                <img src="/resources/images/portal/content/img_opening_guide_01.png" alt="휴대폰 목록에서 원하는 휴대폰을 선택합니다.">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">비교하고 싶은 핸드폰을 3개까지 선택하여 비교할 수 있습니다.</li>
                            </ul>
                            <div class="opening-guide__image">
                                <img src="/resources/images/portal/content/img_opening_guide_02.png" alt="휴대폰 목록에서 비교하기 아이콘이 선택된 휴대폰들은 휴대폰 비교함에서 확인하실 수 있습니다.">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">비교함에서도 요금제별 월 납부금액과 스펙을 비교하고 바로 주문이 가능합니다.</li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--basic">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">2</span>
                            <p class="opening-guide__text">휴대폰의 색상, 용량을 선택 후 가입조건을 선택해 주세요.</p>
                            <div class="opening-guide__image">
                                <img src="/resources/images/portal/content/img_opening_guide_03.png" alt="휴대폰 상세 페이지에서 상품정보와 스펙을 상세하게 확인하실 수 있습니다.">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">용량, 색상을 선택하고 가입할 수 있습니다.</li>
                            </ul>
                            <div class="opening-guide__image">
                                <img src="/resources/images/portal/content/img_opening_guide_04.png" alt="설계하기에서 원하는 가입유형, 요금제 약정기간, 단말기 할부기간을 선택합니다.">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">무약정 선택 시 공통지원금은 없으나 유심 요금제로 가입이 가능합니다.</li>
                                <li class="c-text-list__item u-co-gray">가입유형, 약정, 요금제 등 가입조건을 선택하면 월 납부금액을 확인할 수 있습니다.</li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--basic">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">3</span>
                            <p class="opening-guide__text">온라인신청서를 작성해 주세요.</p>
                            <div class="c-box c-box--type1 u-mt--40">
                                <strong class="c-text c-text--fs15 u-fw--medium">가입신청서 작성 시 준비사항</strong>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">본인인증 용 : 신용카드, 범용인증서, 간편인증(네이버인증서, PASS인증서, toss인증서, 카카오인증서)중 택 1, 신분증</li>
                                    <li class="c-text-list__item u-co-gray">요금납부 용 : 본인 명의의 자동이체 계좌번호 또는 신용카드</li>
                                </ul>
                            </div>
                            <div class="opening-guide__image u-mt--24">
                                <img src="/resources/images/portal/content/img_opening_guide_05.png" alt="설계하기 후 가입하기 단계로 이동하며 고객님의 유형에 맞는 온라인신청서를 작성합니다.">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">미성년자는 가입자 본인 외 법정대리인의 본인인증이 필요합니다.</li>
                                <li class="c-text-list__item u-co-gray">외국인 가입시에는 외국인등록번호와 여권번호, 국적, 체류기간이 필요합니다.</li>
                            </ul>
                            <b class="c-text c-text--type3 u-block u-mt--24">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>Tip
                            </b>
                            <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                <li class="c-text-list__item u-co-gray">신청 중간에 중단되어도 7일 이내 “신청조회” 에서 이어서 작성할 수 있습니다.</li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--basic">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">4</span>
                            <p class="opening-guide__text">배송 및 개통</p>
                            <div class="opening-guide__image">
                                <img src="/resources/images/portal/content/img_opening_guide_07.png" alt="가입신청이 정상적으로 완료되었습니다.">
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">주문내역은 배송조회에서 확인이 가능합니다.</li>
                                <li class="c-text-list__item u-co-gray">구매하신 휴대폰은 본인 확인을 위한 전화 상담 후 개통되어 택배로 발송됩니다.</li>
                            </ul>
                        </div>
                        <hr class="c-hr c-hr--basic">
                        <div class="opening-guide__item">
                            <span class="opening-guide__step">5</span>
                            <p class="opening-guide__text">유심 장착 및 전화번호 등록</p>
                            <div class="c-box c-box--type1 u-mt--40">
                                <p class="c-text c-text--fs15 u-fw--medium">1. 배송된 USIM칩을 USIM장착 부분에 끼워 줍니다.</p>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-co-gray">배터리 분리형 : 휴대폰 뒷면에서 배터리 커버 분리 후 장착</li>
                                    <li class="c-text-list__item u-co-gray">배터리 일체형 : 유심 분리용 핀으로 휴대폰 옆면의 구멍을 눌러, 유심 슬롯에 장착</li>
                                </ul>
                                <p class="c-text c-text--fs15 u-fw--medium u-mt--24">2.
                                    USIM칩 장착 후 배터리와 후면 커버를 부착한 후, 핸드폰 전원 껐다 켰다를 2-3회 반복합니다.</p>
                                <p class="c-bullet c-bullet--fyr u-co-gray">
                                    'USIM카드 장착을 확인해주세요' 또는 'USIM카드를 인식 할 수 없습니다. 고객센터 문의 해주세요' 등의 메시지가 보여질 경우, kt M모바일 고객센터 1899-5000 로 전화해 주시기 바랍니다.
                                </p>
                            </div>
                            <div class="opening-guide__image u-mt--24">
                                <img src="/resources/images/portal/content/img_opening_guide_08.png" alt="" aria-hidden="true">
                                <div class="c-hidden">
                                    <strong>전화번호 등록방법</strong>
                                    <p>일반적으로 핸드폰에 유심(USIM)을 장착하면 자동으로 번호가 등록되지만, 등록되지 않을 경우 아래와 같은 방법으로 등록이 가능합니다.</p>
                                    <ul>
                                        <li>1. 휴대폰에 USIM을 장착하고 전원을 켜세요.</li>
                                        <li>2. [번호등록]을 선택해주세요.</li>
                                        <li>3. 비밀번호 *147359*682* 를 입력하세요.</li>
                                        <li>4. “번호등록 중입니다” 메시지 후 번호 수신이 완료되면 [확인] 버튼을 눌러주세요.</li>
                                        <li>5. 자동으로 꺼졌다 켜지면 사용 가능합니다.</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
