<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="layoutGnbDefaultTitle">
    <t:putAttribute name="titleAttr">요금제 비교</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/rate/rateComp.js?version=25-12-30"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <input type="hidden" id="soloTxt" value="${nmcp:getCodeNm(Constants.GROUP_CODE_RATE_PROPERTY_LIST,Constants.GROUP_CODE_RATE_PROPERTY_SOLO)}">
        <input type="hidden" id="dataShareTxt" value="${nmcp:getCodeNm(Constants.GROUP_CODE_RATE_PROPERTY_LIST,Constants.GROUP_CODE_RATE_PROPERTY_SHARE)}">
        <input type="hidden" id="tripleSaleTxt" value="${nmcp:getCodeNm(Constants.GROUP_CODE_RATE_PROPERTY_LIST,Constants.GROUP_CODE_RATE_PROPERTY_TRIPLE)}">
        <input type="hidden" id="dataCouponTxt" value="${nmcp:getCodeNm(Constants.GROUP_CODE_RATE_PROPERTY_LIST,Constants.GROUP_CODE_RATE_PROPERTY_COUPON)}">
        <input type="hidden" id="ktWifiTxt" value="${nmcp:getCodeNm(Constants.GROUP_CODE_RATE_PROPERTY_LIST,Constants.GROUP_CODE_RATE_PROPERTY_WIFI)}">
        <input type="hidden" id="rateCompDefault01" value="${nmcp:getCodeNm('Constant','rateCompDefault01')}">
        <input type="hidden" id="rateCompDefault02" value="${nmcp:getCodeNm('Constant','rateCompDefault02')}">
        <input type="hidden" id="rateCompDefault03" value="${nmcp:getCodeNm('Constant','rateCompDefault03')}">

      <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">요금제 비교</h2>
      </div>


      <div class="rate-comp-wrap">
          <div class="rate-comp-content">
              <div class="rate-comp-total-wrap">
                  <h4 class="rate-comp-total">총 <span id="spCnt">-</span>개 요금제</h4>
                  <div class="rate-comp-total__button">
                      <button id="btnFilter"><i class="c-icon c-icon--sort" aria-hidden="true"></i>필터</button>
                      <button id="btnReset"><i class="c-icon c-icon--refresh-type2" aria-hidden="true"></i>재설정</button>
                  </div>
              </div>
              <ul class="rate-comp-select">
                  <li class="rate-comp-select__item">
                      <div class="c-form">
                          <label class="c-label c-hidden" for="rateComparison1">요금제 선택</label>
                          <select class="c-select" id="rateComparison1" name="rateComparison1">

                          </select>
                      </div>
                      </li>
                  <li class="rate-comp-select__item">
                      <div class="c-form">
                          <label class="c-label c-hidden" for="rateComparison2">요금제 선택</label>
                          <select class="c-select" id="rateComparison2" name="rateComparison2">

                          </select>
                      </div>
                  </li>
                  <li class="rate-comp-select__item">
                      <div class="c-form">
                          <label class="c-label c-hidden" for="rateComparison3">요금제 선택</label>
                          <select class="c-select" id="rateComparison3" name="rateComparison3">

                          </select>
                      </div>
                  </li>
              </ul>
              <ul class="rate-comp-list">
                  <li class="rate-comp-item">
                      <div class="rate-comp-info" id="rate-comp-info_01">
                             <div class="rate-comp-info no-data">
                                <div>
                                    <i class="c-icon c-icon--rate-comparison" aria-hidden="true"></i>
                                    <p>비교하실 요금제를<br>선택해 주세요.</p>
                                </div>
                            </div>
                      </div>
                  </li>
                  <li class="rate-comp-item">
                      <div class="rate-comp-info" id="rate-comp-info_02">
                             <div class="rate-comp-info no-data">
                                <div>
                                    <i class="c-icon c-icon--rate-comparison" aria-hidden="true"></i>
                                    <p>비교하실 요금제를<br>선택해 주세요.</p>
                                </div>
                            </div>
                      </div>
                  </li>
                  <li class="rate-comp-item">
                    <div class="rate-comp-info" id="rate-comp-info_03">
                             <div class="rate-comp-info no-data">
                                <div>
                                    <i class="c-icon c-icon--rate-comparison" aria-hidden="true"></i>
                                    <p>비교하실 요금제를<br>선택해 주세요.</p>
                                </div>
                            </div>
                      </div>
                  </li>
              </ul>



              <div class="c-accordion rate-comp c-accordion--type6" data-ui-accordion>
                  <ul class="c-accordion__box">
                      <li class="c-accordion__item">
                          <div class="c-accordion__head">
                              <strong class="c-accordion__title">기본 제공</strong>
                              <button type="button" class="c-accordion__button is-active" id="rateCompDefault" aria-controls="rateCompDefaultContent" aria-expanded="false"></button>
                          </div>
                          <div class="c-accordion__panel expand expanded" id="rateCompDefaultContent" aria-labelledby="rateCompDefault" aria-hidden="true">
                              <ul class="rate-comp__list">
                                <li class= "rate-comp__list-item" id="rate-comp__list-item1"></li>
                                <li class= "rate-comp__list-item" id="rate-comp__list-item2"></li>
                                <li class= "rate-comp__list-item" id="rate-comp__list-item3"></li>
                              </ul>

                          </div>
                      </li>
                      <li class="c-accordion__item">
                          <div class="c-accordion__head">
                              <strong class="c-accordion__title">추가 혜택</strong>
                              <button type="button" class="c-accordion__button is-active" id="rateCompAdd" aria-controls="rateCompAddContent" aria-expanded="false"></button>
                          </div>
                          <div class="c-accordion__panel expand expanded" id="rateCompAddContent" aria-labelledby="rateCompAdd" aria-hidden="true">
                              <ul class="rate-comp__list">
                                  <li class="rate-comp__list-item" id="rate-comp__list-item01"></li>
                                  <li class="rate-comp__list-item" id="rate-comp__list-item02"></li>
                                  <li class="rate-comp__list-item" id="rate-comp__list-item03"></li>
                              </ul>
                          </div>
                      </li>
                      <li class="c-accordion__item">
                          <div class="c-accordion__head">
                              <strong class="c-accordion__title">모바일 혜택</strong>
                              <button type="button" class="c-accordion__button is-active" id="btnRateGift" aria-controls="rateGiftContent" aria-expanded="false"></button>
                          </div>
                          <div class="c-accordion__panel expand expanded" id="rateGiftContent" aria-labelledby="btnRateGift" aria-hidden="true">
                              <ul class="rate-comp__list">
                                  <li class="rate-comp__list-item" id="rate-comp__list-item11"></li>
                                  <li class="rate-comp__list-item" id="rate-comp__list-item12"></li>
                                  <li class="rate-comp__list-item" id="rate-comp__list-item13"></li>
                              </ul>
                          </div>
                      </li>
                  </ul>
              </div>
              <ul class="c-text-list c-bullet c-bullet--fyr">
                  <li class="c-text-list__item u-co-gray">트리플 할인의 요금 할인과 데이터 제공 정보는 <a class="c-text-link--bluegreen u-ml--0" href="/content/ktDcInfo.do" title="트리플 할인 페이지 이동하기">트리플 할인 페이지</a>에서 확인 부탁드립니다.</li>
                  <li class="c-text-list__item u-co-gray">데이터 쿠폰은 신규 가입만 해당되며, 기존 고객의 요금제 변경은 해당 되지 않습니다.</li>
              </ul>
          </div>
      </div>
    </div>
     </t:putAttribute>

    <t:putAttribute name="popLayerAttr">
   <!-- 필터 팝업 -->
        <div class="c-modal c-modal--lg c-modal--rate-comparison" id="rateComparisonModal" role="dialog" tabindex="-1" aria-labelledby="rateComparisonModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="rateComparisonModalTitle">필터 <i class="c-icon c-icon--sort" aria-hidden="true"></i></h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="review-filter">
                        <div class="c-filter">
                            <div class="c-filter__inner">
                                <button class="c-button" data-target="section1">요금제</button>
                                <button class="c-button" data-target="section2">데이터</button>
                                <button class="c-button" data-target="section3">통화</button>
                                <button class="c-button" data-target="section4">문자</button>
                                <button class="c-button" data-target="section5">가격</button>
                                <button class="c-button" data-target="section6">추가혜택</button>
                            </div>
                        </div>
                    </div>
                    <div class="c-modal__body">
                        <section  class="section1" id="section1">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonRateGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonRateGroupTitle">요금제</h3>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonRate1" value="A" type="checkbox" name="rateComparisonRateGroup" checked>
                                        <label class="c-label" for="rateComparisonRate1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonRate2" value="4"  type="checkbox" name="rateComparisonRateGroup">
                                        <label class="c-label" for="rateComparisonRate2">LTE</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonRate3" value="5"  type="checkbox" name="rateComparisonRateGroup">
                                        <label class="c-label" for="rateComparisonRate3">5G</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonRate4" value="2"  type="checkbox" name="rateComparisonRateGroup">
                                        <label class="c-label" for="rateComparisonRate4">제휴요금제</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonRate5" value="99" data-rate-ctg="05"   type="checkbox" name="rateComparisonRateGroup">
                                        <label class="c-label" for="rateComparisonRate5">연령특화</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="section2" id="section2">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonDataGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonDataGroupTitle">데이터</h3>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonData1" value="" type="radio" name="rateComparisonDataGroup" checked>
                                        <label class="c-label" for="rateComparisonData1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonData2" value="2" data-min-val="0" data-max-val="3" type="radio" name="rateComparisonDataGroup">
                                        <label class="c-label" for="rateComparisonData2">~3GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonData3" value="3" data-min-val="3" data-max-val="7" type="radio" name="rateComparisonDataGroup">
                                        <label class="c-label" for="rateComparisonData3">3GB~7GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonData4" value="4" data-min-val="7" data-max-val="999" type="radio" name="rateComparisonDataGroup">
                                        <label class="c-label" for="rateComparisonData4">7GB~</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonData5" value="1" data-min-val="999" data-max-val="999" type="radio" name="rateComparisonDataGroup">
                                        <label class="c-label" for="rateComparisonData5">무제한</label>
                                    </div>
                                </div>
                                <!-- 무제한 선택 시 나오는 영역 -->
                                <div id="divDataSlider" style="display:none;">
                                    <div class="c-range c-range--type2 multi c-range--step6 u-mt--16">
                                        <div class="c-hidden">데이터 선택(0GB,7GB,10GB,15GB,100GB,제한없음)</div>
                                        <div class="c-range__slider" data-range-slider data-rsliders-block-step="20" data-rsliders-a11y="0GB|7GB|10GB|15GB|100GB|제한없음">
                                            <input type="range" min="0" max="100" value="0" step="20" title="데이터 최소값">
                                            <input type="range" min="0" max="100" value="100" step="20" title="데이터 최대값">
                                        </div>
                                        <div class="c-range__ruler">
                                            <span class="ruler-mark">0GB</span>
                                            <span class="ruler-mark">7GB</span>
                                            <span class="ruler-mark">10GB</span>
                                            <span class="ruler-mark">15GB</span>
                                            <span class="ruler-mark">100GB</span>
                                            <span class="ruler-mark">제한없음</span>
                                        </div>
                                    </div>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData1" value="0" data-min-val="0" data-max-val="100"  type="radio" name="rateComparisonInfinityDataGroup" checked>
                                        <label class="c-label" for="rateComparisonInfinityData1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData2" value="1" data-min-val="0" data-max-val="20" type="radio" name="rateComparisonInfinityDataGroup">
                                        <label class="c-label" for="rateComparisonInfinityData2">~7GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData3" value="2" data-min-val="20" data-max-val="40"  type="radio" name="rateComparisonInfinityDataGroup">
                                        <label class="c-label" for="rateComparisonInfinityData3">7GB~10GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData4" value="3" data-min-val="40" data-max-val="60" type="radio" name="rateComparisonInfinityDataGroup">
                                        <label class="c-label" for="rateComparisonInfinityData4">10GB~15GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData5" value="4" data-min-val="60" data-max-val="80" type="radio" name="rateComparisonInfinityDataGroup">
                                        <label class="c-label" for="rateComparisonInfinityData5">15GB~100GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData6" value="5" data-min-val="80" data-max-val="100" type="radio" name="rateComparisonInfinityDataGroup">
                                        <label class="c-label" for="rateComparisonInfinityData6">100GB~</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="section3" id="section3">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonCallGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonCallGroupTitle">통화</h3>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonCall1" value=""  type="radio" name="rateComparisonCallGroup" checked>
                                        <label class="c-label" for="rateComparisonCall1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonCall2" value="1" data-min-val="0" data-max-val="100" type="radio" name="rateComparisonCallGroup">
                                        <label class="c-label" for="rateComparisonCall2">~100분</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonCall3" value="2" data-min-val="100" data-max-val="200" type="radio" name="rateComparisonCallGroup">
                                        <label class="c-label" for="rateComparisonCall3">100분~200분</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonCall4" value="3" data-min-val="200" data-max-val="300" type="radio" name="rateComparisonCallGroup">
                                        <label class="c-label" for="rateComparisonCall4">200분~300분</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonCall5" value="4" data-min-val="999" data-max-val="999" type="radio" name="rateComparisonCallGroup">
                                        <label class="c-label" for="rateComparisonCall5">무제한</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="section4" id="section4">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonSmsGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonSmsGroupTitle">문자</h3>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonSms1" value=""  type="radio" name="rateComparisonSmsGroup" checked>
                                        <label class="c-label" for="rateComparisonSms1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonSms2" value="1" data-min-val="0" data-max-val="50" type="radio" name="rateComparisonSmsGroup">
                                        <label class="c-label" for="rateComparisonSms2">~50건</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonSms3" value="2" data-min-val="50" data-max-val="100" type="radio" name="rateComparisonSmsGroup">
                                        <label class="c-label" for="rateComparisonSms3">50건~100건</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonSms4" value="3" data-min-val="100" data-max-val="300" type="radio" name="rateComparisonSmsGroup">
                                        <label class="c-label" for="rateComparisonSms4">100건~300건</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonSms5" value="4" data-min-val="999" data-max-val="999" type="radio" name="rateComparisonSmsGroup">
                                        <label class="c-label" for="rateComparisonSms5">무제한</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="section5" id="section5">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonPriceGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonPriceGroupTitle">가격</h3>
                                    <div class="c-range c-range--type2 multi c-range--step6 u-mt--16 rate-comparison-Price">
                                        <div class="c-hidden">가격 선택(0원, 5,000원, 10,000원, 20,000원, 30,000원,제한없음)</div>
                                        <div class="c-range__slider" data-range-slider data-rsliders-block-step="20" data-rsliders-a11y="0원|5,000원|10,000원|20,000원|30,000원|제한없음">
                                            <input type="range" min="0" max="100" value="0" step="20" title="가격 최소값">
                                            <input type="range" min="0" max="100" value="100" step="20" title="가격 최대값">
                                        </div>
                                        <div class="c-range__ruler">
                                            <span class="ruler-mark">0원</span>
                                            <span class="ruler-mark">5,000원</span>
                                            <span class="ruler-mark">10,000원</span>
                                            <span class="ruler-mark">20,000원</span>
                                            <span class="ruler-mark">30,000원</span>
                                            <span class="ruler-mark">제한없음</span>
                                        </div>
                                    </div>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice1" type="radio" value="0" data-min-val="0" data-max-val="100" name="rateComparisonPriceGroup" checked>
                                        <label class="c-label" for="rateComparisonPrice1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice2" type="radio" value="1" data-min-val="0" data-max-val="20" name="rateComparisonPriceGroup">
                                        <label class="c-label" for="rateComparisonPrice2">~5,000원</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice3" type="radio" value="2" data-min-val="20" data-max-val="40" name="rateComparisonPriceGroup">
                                        <label class="c-label" for="rateComparisonPrice3">5,000원~10,000원</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice4" type="radio" value="3" data-min-val="40" data-max-val="60" name="rateComparisonPriceGroup">
                                        <label class="c-label" for="rateComparisonPrice4">10,000원~20,000원</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice5" type="radio" value="4" data-min-val="60" data-max-val="80" name="rateComparisonPriceGroup">
                                        <label class="c-label" for="rateComparisonPrice5">20,000원~30,000원</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice6" type="radio" value="5" data-min-val="80" data-max-val="100" name="rateComparisonPriceGroup">
                                        <label class="c-label" for="rateComparisonPrice6">30,000원~</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="section6" id="section6">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonGiftGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonGiftGroupTitle">추가혜택</h3>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift1" value="A" type="checkbox" name="rateComparisonGiftGroup" checked>
                                        <label class="c-label" for="rateComparisonGift1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift2" value="01"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift2">아무나 SOLO 결합</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift3" value="06"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift3">데이터 쉐어링</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift4" value="07"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift4">데이터 함께쓰기</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift5" value="05"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift5">트리플 할인</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift6" value="02"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift6">데이터 쿠폰</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift7" value="09"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift7">KT WiFi</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                 <div class="c-modal__footer u-flex-center">
                        <div class="u-box--w460 c-button-wrap">
                            <%--<button class="c-button c-button--full c-button--gray"><i class="c-icon c-icon--refresh-type2" aria-hidden="true"></i>재설정</button>--%>
                            <button id="btnSetList" class="c-button c-button--full c-button--mint u-co-white">총 <span>-</span>건 적용</button>
                        </div>
                    </div>
                </div>
        </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>