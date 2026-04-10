<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">KT인터넷 트리플할인</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">KT인터넷 트리플할인</h2>
            </div>
               <div class="ktdc-banner">
                <div class="ktdc-banner__wrap">
                    <h3>KT인터넷 이용하고 할인은 더 크게</h3>
                    <p>KT상품을 더하면 커지는 할인혜택</p>
                    <div class="ktdc-banner__button">
                        <a href="https://shop.kt.com/display/olhsPlan.do?plnDispNo=2181" title="KT인터넷 상담 신청 바로가기 새창열림" target="_blank">
                            <span>KT인터넷 상담 신청</span>
                            <i class="c-icon" aria-hidden="true"></i>
                        </a>
                        <a href="/content/ktDcView.do" title="트리플할인 신청 바로가기">
                            <span>트리플할인 신청</span>
                            <i class="c-icon" aria-hidden="true"></i>
                        </a>
                    </div>
                    <img src="/resources/images/portal/product/ktdc_banner.png" alt="">
                </div>
            </div>
            <div class="ktdc-content">
                <div class="ktdc-title">
                    <em>What</em>
                    <h4>KT인터넷 트리플할인이란?</h4>
                    <p>kt M모바일을 사용하는 고객이 KT인터넷을 신규로 가입 할 경우 추가로 할인 받을 수 있는 서비스 입니다.</p>
                </div>
                <div class="ktdc-benefit">
                    <div class="ktdc-benefit__head">
                        <p class="ktdc-benefit__title">한번 더 저렴해지는 요금 혜택!</p>
                        <p>KT 내 상품 결합 여부와 상관없이 요금 할인 혜택을 받으실 수 있습니다.</p>
                    </div>
                    <ul class="ktdc-benefit__list">
                        <li class="ktdc-benefit__item">
                            <div class="ktdc-benefit__item-head">
                                <p class="ktdc-benefit__item-title">최대 <span>113</span>만원</p>
                                <p>사은품/할인</p>
                            </div>
                            <img src="/resources/images/portal/product/ktdc_Benefit_01.png" alt="TV, 청소기, 노트북, 전기밥솥, 공기청정기 등 가전 사은품">
                            <p>인터넷 신규+TV 가입시</p>
                            <div class="ktdc-benefit__item-sticker">
                                <p>KT샵<br />단독할인<br />포함</p>
                            </div>
                        </li>
                        <li class="ktdc-benefit__item">
                            <div class="ktdc-benefit__item-head rate">
                                <p class="ktdc-benefit__item-title">최대 <span>13<em>.</em>2</span>만원</p>
                                <p>요금할인</p>
                            </div>
                            <img src="/resources/images/portal/product/ktdc_Benefit_02.png" alt="매달 최대 5,500원 요금할인">
                            <p>kt M모바일 1회선 24개월</p>
                        </li>
                    </ul>
                </div>
                <div class="ktdc-title">
                    <em>Who</em>
                    <h4>할인 대상자는 누구인가요?</h4>
                    <p>“KT인터넷 상담신청”을 통해 KT인터넷을 신규로 가입하고 가입일로부터 익월 말까지 “마이페이지”를 통해 트리플할인을 신청한 고객</p>
                </div>
                <div class="ktdc-taget">
                    <ul class="ktdc-taget__list">
                        <li class="ktdc-taget__item">
                            <span class="step-list">STEP 1</span>
                            <img src="/resources/images/portal/product/ktdc_icon_01.png" alt="">
                            <p>“KT인터넷 상담신청”을 통해<br />KT인터넷 가입</p>
                            <a href="https://shop.kt.com/display/olhsPlan.do?plnDispNo=2181" title="KT인터넷 상담 신청 바로가기 새창열림" target="_blank">인터넷 상담신청 바로가기</a>
                        </li>
                        <li class="ktdc-taget__item">
                            <span class="step-list">STEP 2</span>
                            <img src="/resources/images/portal/product/ktdc_icon_02.png" alt="">
                            <p>마이페이지에서<br />트리플할인 신청</p>
                            <a href="/content/ktDcView.do" title="트리플할인 신청 바로가기">트리플할인 신청 바로가기</a>
                        </li>
                    </ul>
                </div>
                <div class="ktdc-title">
                    <em>Why</em>
                    <h4>왜 kt M모바일을 통해 KT인터넷을 가입해야 하나요?</h4>
                    <p>우리 홈페이지를 통해 KT인터넷을 가입한 고객에게만 kt M모바일에 추가할인 혜택을 제공해 드려요.</p>
                </div>
            </div>
            <div class="ktdc-example">
                <div class="ktdc-example-wrap">
                    <div class="ktdc-example__head-wrap">
                        <div class="ktdc-example__head">
                            <p class="ktdc-example__title">여기서 잠깐! kt M모바일의 할인금액을 알아볼게요.</p>
                            <p>KT인터넷 개통 시 추가로 할인받아 이용할 수 있는 요금제와 금액을 확인해 보세요.</p>
                        </div>
                    </div>
                    <div class="ktdc-example__detail">
                        <p class="ktdc-example__detail-title">KT 혜택</p>
                        <div class="ktdc-example__ktdc">
                            <div class="ktdc-example__ktdc-info">
                                <div class="ktdc-example__ktdc-info-wrap">
                                    <p class="ktdc-example__ktdc-info-num">1</p>
                                    <dl>
                                        <dt>상품권(사은품)</dt>
                                        <dd>최대 450,000원</dd>
                                    </dl>
                                    <dl>
                                        <dt>인터넷 + TV결합할인</dt>
                                        <dd>최대 530,640원</dd>
                                    </dl>
                                </div>
                                <div class="ktdc-example__ktdc-info-wrap">
                                    <div class="ktdc-example__ktdc-info-wrap">
                                        <p class="ktdc-example__ktdc-info-num">2</p>
                                        <p class="ktdc-example__ktdc-info-titlesub">KT샵 단독할인</p>
                                        <dl>
                                            <dt>KT샵 추가 요금 할인</dt>
                                            <dd>118,800원</dd>
                                        </dl>
                                        <dl>
                                            <dt>TV쿠폰</dt>
                                            <dd>30,000원</dd>
                                        </dl>
                                    </div>
                                </div>
                            </div>
                            <div class="ktdc-example__ktdc-total">
                                <p>최대 혜택 금액</p>
                                <p class="ktdc-example__ktdc-price">113<span>만원</span></p>
                            </div>
                        </div>
                        <p class="ktdc-example__detail-fyr">※ 인터넷 에센스 이상+지니 TV에센스 이상 요금제 기준</p>
                        <p class="ktdc-example__detail-fyr">※ 3년 약정 가입 시</p>
                    </div>
                    <div class="ktdc-example__detail total">
                        <p class="ktdc-example__detail-title"><span class="ktdc-example__detail-title--num">3</span>M모바일 혜택</p>
                        <ul class="ktdc-example__benefit">
                            <li class="ktdc-example__benefit-item">
                                <p class="ktdc-example__benefit-title">5G 모두다 맘껏 안심 6GB+</p>
                                <p class="ktdc-example__benefit-titlesub">6GB+최대 400Kbps</p>
                                <div class="ktdc-example__benefit-disc">
                                    <p class="ktdc-example__benefit-disc--rate">월 11,400원</p>
                                    <p class="ktdc-example__benefit-disc--through">36,000</p>
                                </div>
                                <p class="ktdc-example__benefit-text">프로모션 할인, 트리플할인 적용 시</p>
                                <div class="ktdc-example__benefit-price-wrap">
                                    <p class="ktdc-example__benefit-dctitle">총 트리플할인 금액</p>
                                    <p class="ktdc-example__benefit-price">132,000원</p>
                                    <p class="ktdc-example__benefit-subtext">(월 5,500원×24개월)</p>
                                </div>
                            </li>
                            <li class="ktdc-example__benefit-item">
                                <p class="ktdc-example__benefit-title">5G 통화 맘껏 3GB</p>
                                <p class="ktdc-example__benefit-titlesub">3GB</p>
                                <div class="ktdc-example__benefit-disc">
                                    <p class="ktdc-example__benefit-disc--rate">월 5,500원</p>
                                    <p class="ktdc-example__benefit-disc--through">28,000</p>
                                </div>
                                <p class="ktdc-example__benefit-text">프로모션 할인, 트리플할인 적용 시</p>
                                <div class="ktdc-example__benefit-price-wrap">
                                    <p class="ktdc-example__benefit-dctitle">총 트리플할인 금액</p>
                                    <p class="ktdc-example__benefit-price">105,600원</p>
                                    <p class="ktdc-example__benefit-subtext">(월 4,400원×24개월)</p>
                                </div>
                            </li>
                        </ul>
                        <div class="ktdc-example-button-wrap">
                            <button data-dialog-trigger="#ktDcRateModal"><span>대상 요금제 전체 보기</span><span class="c-hidden">대상 요금제 전체 보기 바로가기</span></button>
                        </div>
                    </div>
                    <div class="ktdc-example-total">
                        <p>
                            총 혜택 금액
                            <span class="ktdc-example-total-text">최대</span>
                            <span class="ktdc-example-total-rate">1,261,440<em>원</em></span>
                        </p>
                    </div>
                </div>
            </div>
            <div class="ktdc-content">
                <div class="ktdc-title u-mt--0">
                    <em>When</em>
                    <h4>할인은 언제부터 적용되나요?</h4>
                    <p>마이페이지에서 트리플할인을 신청하여 성공한 경우 당월 사용분부터 kt M모바일 통신 요금에서 자동할인을 해 드려요.</p>
                    <p class="ktdc-title__red">※ 할인 여부는 익월 명세서를 통해 확인하실 수 있습니다.</p>
                </div>
                <div class="ktdc-title">
                    <em>How</em>
                    <h4>추가 할인을 받으려면 어떻게 해야하나요?</h4>
                </div>
                <div class="ktdc-title u-mt--32">
                    <h4 class="ktdc-title--type2 u-mt--0">사용하시는 인터넷과 M모바일 결합 시 인터넷에서 추가할인을 받으실 수 있습니다.</h4>
                    <div class="c-table">
                        <table>
                            <caption>KT인터넷, M모바일 결합 추가할인 안내 - KT 인터넷, 결합할인(3년 약정 기준) 금액 정보표</caption>
                              <colgroup>
                                <col>
                                <col>
                              </colgroup>
                              <thead>
                                <tr>
                                      <th scope="col">KT 인터넷</th>
                                      <th scope="col">결합할인(3년 약정 기준)</th>
                                </tr>
                              </thead>
                              <tbody>
                                  <tr>
                                    <td>인터넷 슬림</td>
                                      <td>월 3,300원</td>
                                </tr>
                                  <tr>
                                    <td>인터넷슬림 플러스</td>
                                      <td>월 9,350원</td>
                                </tr>
                                <tr>
                                    <td>인터넷 베이직</td>
                                      <td rowspan="4">월 11,000원</td>
                                </tr>
                                <tr>
                                    <td>인터넷 에센스</td>
                                </tr>
                                <tr>
                                    <td>인터넷 프리미엄</td>
                                </tr>
                                <tr>
                                    <td>인터넷 프리미엄플러스</td>
                                </tr>
                                <tr>
                                    <td class="u-bb-gray-2">인터넷 슈퍼프리미엄</td>
                                      <td class="u-bb-gray-2">월 16,500원</td>
                                </tr>
                              </tbody>
                        </table>
                      </div>
                      <ul class="ktdc-text-list--fyr">
                        <li>8월 1일부터 결합 처리 완료 시 변경 된 인터넷 요금할인 금액 반영됩니다.</li>
                    </ul>
                      <h4 class="ktdc-title--type2">만약 하기 <span>요금제에 가입하고 인터넷과 결합 시</span>에는 매월 최대 20GB 데이터가 추가로 제공!</h4>
                    <div class="c-table">
                        <table>
                            <caption>KT인터넷, M모바일 결합 시 추가 데이터 안내 - M모바일 LTE, 5G  요금제별 혜택제공 정보표</caption>
                              <colgroup>
                                <col>
                                <col width="50%">
                                <col>
                              </colgroup>
                              <thead>
                                <tr>
                                    <th scope="col"></th>
                                      <th scope="col">대상 요금제명</th>
                                      <th scope="col">혜택제공</th>
                                </tr>
                              </thead>
                              <tbody>
                                  <tr>
                                      <td class="u-bg--blue-light u-bb-gray-2" rowspan="22">LTE</td>
                                    <td>모두다 맘껏 7GB+(밀리의 서재 FREE)</td>
                                      <td rowspan="7">5GB(매월)</td>
                                </tr>
                                <tr>
                                    <td>모두다 맘껏 7GB+(PAY쿠폰_5000P)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 7GB+(지니뮤직 FREE)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 7GB+ (메가박스 FREE)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 7GB+(후후안심)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 7GB+(PAY쿠폰_3000P)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 7GB++</td>
                                </tr>
                                <tr>
                                    <td>모두다 맘껏 11GB+(Pay쿠폰_5000P)</td>
                                      <td class="u-bb-gray-2" rowspan="15">20GB(매월)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 11GB+(배달의민족 5000P)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 11GB+(요기요 5000P)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 11GB+(기프티쇼_5000P)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 11GB+(지니뮤직_FREE)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 11GB+(왓챠 베이직)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 11GB+(왓챠 프리미엄)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 11GB++</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 11GB+(CU 20%할인)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 100GB+(밀리의서재 FREE)</td>
                                </tr>
                                <tr>
                                      <td>구글플레이기프트(100GB+/통화맘껏)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 100GB+(PAY쿠폰_5000P)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 100GB+(왓챠 베이직)</td>
                                </tr>
                                <tr>
                                      <td>모두다 맘껏 100GB+(왓챠 프리미엄)</td>
                                </tr>
                                <tr>
                                      <td class="u-bb-gray-2">모두다 맘껏 100GB+(CU 20%할인)</td>
                                </tr>
                                <tr>
                                      <td class="u-bg--red-light u-bb-gray-2" rowspan="11">5G</td>
                                    <td>5G 모두다 맘껏 14GB+</td>
                                      <td>5GB(매월)</td>
                                </tr>
                                <tr>
                                    <td>5G 모두다 맘껏 30GB+</td>
                                      <td rowspan="4">10GB(매월)</td>
                                </tr>
                                <tr>
                                    <td>5G 모두다 맘껏 50GB+</td>
                                </tr>
                                <tr>
                                    <td>5G 모두다 맘껏 70GB+(밀리의 서재 FREE)</td>
                                </tr>
                                <tr>
                                    <td>5G 모두다 맘껏 90GB+(밀리의 서재 FREE)</td>
                                </tr>
                                <tr>
                                    <td>5G 모두다 맘껏 110GB+(밀리의 서재 FREE)</td>
                                      <td class="u-bb-gray-2" rowspan="5">20GB(매월)</td>
                                </tr>
                                <tr>
                                    <td>5G 모두다 맘껏 110GB+(지니뮤직 FREE)</td>
                                </tr>
                                <tr>
                                    <td>5G 모두다 맘껏 110GB+(메가박스 FREE)</td>
                                </tr>
                                <tr>
                                    <td>5G 모두다 맘껏 200GB+(밀리의 서재 FREE)</td>
                                </tr>
                                <tr>
                                    <td class="u-bb-gray-2">5G 모두다 맘껏 200GB+(지니뮤직 FREE)</td>
                                </tr>
                              </tbody>
                        </table>
                      </div>
                      <ul class="ktdc-text-list--fyr">
                        <li>결합 할인 및 추가 데이터 제공은 M모바일 회선 간 결합이나 KT 내 결합할인을 이용하시지 않을 경우에만 적용 가능합니다.</li>
                        <li>결합 신청은 KT고객센터에서 MVNO정보제공 동의 후에 kt M모바일 고객센터(114) 또는 홈페이지 내 “아무나가족결합+” 페이지를 통해 가능합니다.</li>
                        <li>KT인터넷은 신규 가입고객만 결합이 가능하여, 가입일 기준 익월 말 이내에 결합 신청을 하셔야 KT요금할인 및 데이터 추가 제공이 적용됩니다.<br />(ex. 5월 1일에 KT상품을 가입한 고객은 6월 30일까지 결합 신청을 해야 함)</li>
                        <li>결합 시 첫 데이터는 별도의 신청 없이 요금제에 따라 즉시 제공되며, 이후에는 매월 1일에 데이터가 제공됩니다.<br />(미사용 데이터는 이월 불가하며 M모바일 회선에만 제공됩니다.)</li>
                        <li>결합 가능 KT인터넷 : 인터넷 슬림, 인터넷 슬림 플러스, 인터넷 베이직, 인터넷 에센스, 인터넷 프리미엄, 인터넷 프리미엄플러스, 인터넷 슈퍼프리미엄</li>
                        <li>결합 시 제공되는 데이터는 테더링으로 사용 가능하며, 함께쓰기, 데이터쉐어링, 로밍으로는 사용 불가합니다.</li>
                        <li>요금제 변경 이후의 요금제에 따라 결합 데이터 제공량이 달라지거나, 제공되지 않으니 “데이터 제공 요금제”표를  참고해주세요.</li>
                        <li>결합 할인 금액과 추가 데이터 제공은 KT정책 및 약정기간에 따라 변동될 수 있습니다.</li>
                    </ul>
                </div>
                <div class="ktdc-title">
                    <em>Where</em>
                    <h4>상담신청은 어디에서 하나요?</h4>
                    <p>상담신청은 하기 버튼을 눌러 이동한 페이지에서 진행 해 주세요.</p>
                    <p class="ktdc-title__fyr">※ 해당 프로모션은 KT Shop과의 제휴에 의해 진행되는 건으로 하기 링크를 클릭 시 KT Shop 페이지로 이동합니다.</p>
                    <p class="ktdc-title__fyr">※ 할인 혜택은 하기 이동 페이지를 통해 상담 신청을 접수하여 인터넷을 개통한 건에 대해서만 제공됩니다.</p>
                    <div class="c-button-wrap u-mt--54">
                        <a class="c-button c-button--lg c-button--primary c-button--w460" href="https://shop.kt.com/display/olhsPlan.do?plnDispNo=2181" title="KT인터넷 상담 신청 바로가기 새창열림" target="_blank">KT인터넷 상담 신청</a>
                        <a class="c-button c-button--lg c-button--gray c-button--w460" href="/content/ktDcView.do" title="트리플할인 신청 바로가기">트리플할인 신청</a>
                    </div>
                </div>
                <div class="c-notice-wrap u-mt--64">
                    <h5 class="c-notice__title">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                        <span>유의사항</span>
                    </h5>
                    <ul class="c-notice__list">
                        <li>KT인터넷 가입일부터 익월말까지 트리플할인을 신청한 고객 대상 신청월부터 kt M모바일 요금할인이 적용됩니다.</li>
                        <li>KT인터넷 트리플할인의 kt M모바일 요금할인 혜택은 다이렉트몰을 통해서 가입한 고객에게만 적용됩니다.</li>
                        <li>KT인터넷 신규 가입자와 kt M모바일 가입자의 명의가 동일할 경우에만 혜택이 제공되며 다회선 가입 시 요금할인은 개통 순으로 1회에 한해 적용됩니다.</li>
                        <li>KT인터넷 트리플할인 대상 요금제 및 할인금액은 월 진행되는 프로모션에 따라 변동 될 수 있습니다.</li>
                        <li>혜택 제공 후 M모바일 가입 요금제의 변경, 해지, 일시정지 및 KT인터넷의 해지 시 요금할인의 혜택은 종료됩니다.</li>
                        <li>요금할인 혜택은 제공 후 익월 명세서에서 확인이 가능합니다.</li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- 대상 요금제 전체보기 팝업 -->
        <div class="c-modal c-modal--lg" id="ktDcRateModal" role="dialog" aria-labelledby="ktDcRateModalTitle">
            <div class="c-modal__dialog" role="document">
                  <div class="c-modal__content">
                    <div class="c-modal__header">
                          <h2 class="c-modal__title" id="ktDcRateModalTitle">${formDtl.dtlCdNm}</h2>
                              <button class="c-button" data-dialog-close>
                                <i class="c-icon c-icon--close" aria-hidden="true"></i>
                                <span class="c-hidden">팝업닫기</span>
                              </button>
                    </div>
                    <div class="c-modal__body u-pt--0">
                        ${formDtl.docContent}
                    </div>
                </div>
           </div>
           </div>


    </t:putAttribute>
</t:insertDefinition>