<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script src="/resources/js/portal/mypage/regServicePop.js?version=25-03-18"></script>

 <div class="c-modal__content">

    <div class="c-modal__header">
      <h2 class="c-modal__title" id="ChangeProductTitle">요금제 변경</h2>
      <button class="c-button" data-dialog-close>
        <i class="c-icon c-icon--close" aria-hidden="true"></i>
        <span class="c-hidden">팝업닫기</span>
      </button>
    </div>

    <div class="c-modal__body">
      <h3 class="c-heading c-heading--fs20 c-heading--regular">변경 정보</h3>
      <div class="c-table u-mt--32">
        <!-- 변경정보 레이아웃 변경, 혜택 안내 추가 20230403 -->
        <table>
          <caption>구분, 이용중인 요금제, 변경 요금제로 구성된 변경 요금제 정보</caption>
          <colgroup>
            <col style="width: 10rem">
            <col>
            <col>
          </colgroup>
          <thead>
            <tr>
              <th scope="col">구분</th>
              <th scope="col">이용중인 요금제</th>
              <th scope="col">변경 요금제</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>요금제</td>
              <td>${prvRateGrpNm}</td>
              <td>${rateNm}</td>
            </tr>
            <tr>
              <td>이용요금(VAT포함)</td>
              <td>${instAmtDisp}</td>
              <td>${baseAmtDisp} 원</td>
            </tr>
            <tr>
              <td>혜택 변경 안내</td>
              <td class="u-va-top">
                <c:choose>
                  <c:when test="${!empty prvFarPriceDto}">
                    <ul class="ratechg-info c-text-list c-bullet c-bullet--dot">
                      <c:if test="${!empty prvFarPriceDto.bnfitData}">
                        <li class="c-text-list__item u-mt--8 u-fs-15"><span>${prvFarPriceDto.bnfitData}</span></li>
                      </c:if>
                      <c:if test="${!empty prvFarPriceDto.bnfitVoice}">
                        <li class="c-text-list__item u-mt--8 u-fs-15"><span>${prvFarPriceDto.bnfitVoice}</span></li>
                      </c:if>
                      <c:if test="${!empty prvFarPriceDto.bnfitSms}">
                        <li class="c-text-list__item u-mt--8 u-fs-15"><span>${prvFarPriceDto.bnfitSms}</span></li>
                      </c:if>
                      <c:if test="${!empty prvFarPriceDto.bnfitWifi}">
                        <li class="c-text-list__item u-mt--8 u-fs-15"><span>WiFi ${prvFarPriceDto.bnfitWifi}</span></li>
                      </c:if>
                      <c:if test="${!empty prvFarPriceDto.bnfitList}">
                        <c:forEach var="bnfit" items="${prvFarPriceDto.bnfitList}">
                          <li class="c-text-list__item u-mt--8 u-fs-15">
                            <c:if test="${!empty bnfit.rateAdsvcItemNm}">
                              ${bnfit.rateAdsvcItemNm}&nbsp;:&nbsp;
                            </c:if>
                            <span>${bnfit.rateAdsvcItemDesc}</span>
                          </li>
                        </c:forEach>
                      </c:if>
                    </ul>
                  </c:when>
                  <c:otherwise></c:otherwise>
                </c:choose>
              </td>
              <td class="u-va-top">
                <c:choose>
                  <c:when test="${!empty nxtFarPriceDto}">
                    <ul class="ratechg-info c-text-list c-bullet c-bullet--dot">
                      <c:if test="${!empty nxtFarPriceDto.bnfitData}">
                        <li class="c-text-list__item u-mt--8 u-fs-15"><span>${nxtFarPriceDto.bnfitData}</span></li>
                      </c:if>
                      <c:if test="${!empty nxtFarPriceDto.bnfitVoice}">
                        <li class="c-text-list__item u-mt--8 u-fs-15"><span>${nxtFarPriceDto.bnfitVoice}</span></li>
                      </c:if>
                      <c:if test="${!empty nxtFarPriceDto.bnfitSms}">
                        <li class="c-text-list__item u-mt--8 u-fs-15"><span>${nxtFarPriceDto.bnfitSms}</span></li>
                      </c:if>
                      <c:if test="${!empty nxtFarPriceDto.bnfitWifi}">
                        <li class="c-text-list__item u-mt--8 u-fs-15"><span>WiFi ${nxtFarPriceDto.bnfitWifi}</span></li>
                      </c:if>
                      <c:if test="${!empty nxtFarPriceDto.bnfitList}">
                        <c:forEach var="bnfit" items="${nxtFarPriceDto.bnfitList}">
                          <li class="c-text-list__item u-mt--8 u-fs-15">
                            <c:if test="${!empty bnfit.rateAdsvcItemNm}">
                              ${bnfit.rateAdsvcItemNm}&nbsp;:&nbsp;
                            </c:if>
                            <span>${bnfit.rateAdsvcItemDesc}</span>
                          </li>
                        </c:forEach>
                      </c:if>
                    </ul>
                  </c:when>
                  <c:otherwise></c:otherwise>
                </c:choose>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <form name="frm" id="frm">
        <input type="hidden" name="targetTerms" id="targetTerms1" value=""/>
        <input type="hidden" name="targetTerms" id="targetTerms2" value=""/>
        <input type="hidden" name="contractNum" id="contractNum" value="${contractNum}"/>
        <input type="hidden" name="rateAdsvcCd" id="rateAdsvcCd" value="${rateAdsvcCd}"/>
        <input type="hidden" name="aSocAmnt" id="aSocAmnt1" value="${instAmt}"/>
        <input type="hidden" name="tSocAmnt" id="tSocAmnt" value="${baseAmt}"/>
        <input type="hidden" id="jehuProdType" name="jehuProdType" value="${jehuProdType}" ><!--제휴처 요금제 타입 -->
        <input type="hidden" id="jehuProdName" name="jehuProdName" value="${jehuProdName}" ><!--제휴처 요금제 이름 -->
        <input type="hidden" id="isActivatedThisMonth" name="isActivatedThisMonth" value="${isActivatedThisMonth}" ><!--이번달 개통여부 -->
        <input type="hidden" id="isFarPriceThisMonth" name="isFarPriceThisMonth" value="${isFarPriceThisMonth}" ><!--이번달 요금제 변경여부 -->
      </form>

      <div class="c-form-group u-mt--48" role="group" aira-labelledby="radDateTypeHead">
        <div class="c-checktype-row c-col">
          <input class="c-radio c-radio--button" id="radDateType1" type="radio" name="chkRadion" value="P" <c:if test="${chkRadion eq 'P'}">checked</c:if>>
          <label class="c-label" for="radDateType1">예약(익월1일)</label>
          <input class="c-radio c-radio--button" id="radDateType2" type="radio" name="chkRadion" value="S" <c:if test="${chkRadion eq 'S'}">checked</c:if>>
          <label class="c-label" for="radDateType2">즉시변경</label>
        </div>
      </div>

      <!-- <p class="c-bullet c-bullet--fyr u-co-sub-4 u-mt--12">무선+무선 결합에 의한 데이터 추가제공은 '24년 3월 31일 이후 종료 되었습니다. '24년 4월 1일 이후 비 대상 요금제로 변경시 다시 대상 요금제로 변경 하시더라도 데이터 추가제공 혜택을 받으실 수 없으니 주의하시기 바랍니다.<br />(대상 요금제는 공지사항에서 확인 가능합니다.)</p> -->
      <p class="c-bullet c-bullet--fyr u-co-sub-4 u-mt--12">요금제 변경 시 할인받으셨던 금액(공통지원금, 요금할인, 단말할인)이 위약금으로 발생할 수 있습니다.</p>
      <p class="c-bullet c-bullet--fyr u-co-red u-mt--6">요금제 변경으로 결합혜택(아무나결합, 트리플할인)이 제공되지 않을 수 있습니다.</p>
      <p class="c-bullet c-bullet--fyr u-co-red u-mt--6">개통 후 사은품 지급 전 요금제 변경 시 사은품 지급 대상에서 제외됩니다.</p>

      <!-- 요금제 변경 시 추가요금 안내 추가 20230403 -->
      <div class="u-mt--64">
        <h3 class="c-heading c-heading--fs20 c-heading--regular">요금제 변경 시 추가 요금 안내</h3>
        <hr class="c-hr c-hr--title u-mt--11">

        <ul class="c-text-list c-bullet c-bullet--dot">
          <li class="c-text-list__item">
            기본 요금제를 사용 중이시거나, 예약(익월1일) 변경을 신청하신 경우에는 해당되지 않습니다.
            <ul class="c-text-list c-bullet c-bullet--hyphen u-mt--8">
              <li class="u-pl--10 u-co-gray">※ 기본 요금제에 해당하는 경우</li>
              <li class="c-text-list__item u-co-gray">음성 : 음성 기본 요금제(영상/부가통화 사용량은 제공량에 따른 추가 요금 발생 대상에 해당)</li>
              <!-- <li class="c-text-list__item u-co-gray u-mt--4">데이터 : 데이터 기본 요금제를 사용하거나 QoS 부가서비스를 이용중인 고객(부가서비스 가입 여부는 부가서비스 신청/해지 메뉴에서 확인 가능)</li> -->
            </ul>
          </li>
          <li class="c-text-list__item">
            추가 요금 발생 확인 방법
            <ul class="c-text-list c-bullet">
              <li class="c-text-list__item u-co-gray">
                1) 사용중인 요금제의 한달 제공량과 현재까지 사용량 확인
                <br/><span class="u-pl--10">※ 데이득, 데이터 쿠폰 등 기본제공량 외 추가제공 서비스는 제외(부가서비스 신청/해지 메뉴에서 확인가능)</span>
              </li>
              <li class="c-text-list__item u-co-gray">2) 일할 제공량 계산</li>
              <li class="c-text-list__item u-co-gray">
                3) (현재까지 사용량이 일할계산 된 제공량을 초과했을 경우) 추가 발생 예상 금액 확인(하기 계산 예시 참조)
                <br/><span class="u-pl--10">※ 초과요율은 현재 사용하는 요금제의 상품소개 참조</span>
              </li>
            </ul>

            <!-- 추가요금 예시 영역 -->
            <div class="c-box c-box--type4 c-box--round u-pb--40 u-mt--16">
              <ul class="c-text-list c-bullet c-bullet--dot u-pl--10">
                <li class="c-text-list__item u-co-gray">사용한 일자까지의 제공량 계산 예시
                  <ul class="c-text-list c-bullet c-bullet--hyphen u-mt--8">
                    <li class="c-text-list__item u-co-gray">기본 제공량이 300MB인 요금제를 200MB만큼 사용한 상태에서 16일에 요금제 변경 시</li>
                  </ul>
                </li>
              </ul>
              <div class="c-table u-mt--20 u-mb--20">
                <table>
                  <caption>추가요금 산정방식 예시</caption>
                  <colgroup>
                    <col style="width: 25%;" />
                    <col style="width: 25%;" />
                    <col style="width: 25%;"/>
                    <col/>
                  </colgroup>
                  <thead>
                    <tr class="darker">
                      <th>이용기간</th>
                      <th>제공량(일할계산)</th>
                      <th>과금 대상</th>
                      <th>초과 요금</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td class="u-fs-14">4월 1일~4월15일<br/>(해당월이 30일로 가정)</td>
                      <td class="u-fs-14">300MB/30일x15일(사용일수)= 150MB</td>
                      <td class="u-fs-14">200MB(사용량)-150MB(제공량)= 50MB</td>
                      <td class="u-fs-14">50MBx22.53원(1MB당 초과요금)= 약 1,126원</td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <img src="/resources/images/portal/rate/img_rate_chg.jpg" alt="요금제 변경 일할 계산 안내 도표" aria-hidden="true">
            </div>

          </li>
          <li class="c-text-list__item">
            일부 저렴한 종량제 대상 요금제는 초과 사용량에 따른 요율이 상이하므로 요금제 별 상세보기에서 초과 요율을 확인 바랍니다.
            <ul class="c-text-list c-bullet c-bullet--hyphen u-mt--8">
              <li class="c-text-list__item u-co-gray">저렴한 종량제 대상 요금제는 기본제공량 소진 후 1, 3구간 0.011원/0.5KB(부가세포함)의 요율로 과금됩니다.</li>
            </ul>
            <div class="c-box c-box--type1 c-box--round u-ta-center u-p--0">
              <img src="/resources/images/portal/rate/img_rate_chg_chart.jpg" alt="구간 별 데이터 과금 안내 이미지" aria-hidden="true">
            </div>
          </li>
        </ul>
      </div>

      <!-- 사용량 영역 -->
      <div class="c-accordion c-accordion--type3 u-mt--48" id="accordionproductA" data-ui-accordion>
        <ul class="c-accordion__box u-bt-gray-3">
          <li class="c-accordion__item">
            <div class="c-accordion__head u-pl--0">
              <div class="product__title-wrap">
                <strong class="product__title">내 사용량 펼쳐보기</strong>
              </div>
              <button class="runtime-data-insert c-accordion__button" id="realTimeDataHeader" type="button" aria-expanded="false" aria-controls="acc_content_a1"></button>
            </div>
            <div class="c-accordion__panel expand" id="acc_content_a1" aria-labelledby="realTimeDataHeader">

              <div class="c-accordion__inside u-ptb--24 u-bg--white u-bt-gray-3">
                <p class="c-bullet c-bullet--dot u-fs-14 u-mb--28 u-co-gray _realTimeDataView" id="realTimeDataViewAddDesc" style="display: none;">용량집계에 소요되는 시간 때문에 실제 사용시점과 다소 차이가 있을 수 있습니다.</p>
                <div>
                  <!-- 실시간 이용량 표출_실시간 이용량_조회횟수 초과 -->
                  <div class="u-ta-center _realTimeDataView" id="realTimeDataView01" style="display:none;">
                    <i class="c-icon c-icon--notice-sm" aria-hidden="true"></i>
                    <p class="c-text c-text--fs16 u-mt--16">
                      안정적인 서비스를 위해 이용량 조회는 <b>120분 내 20회</b>로 제한하고 있습니다. <br>잠시 후 이용해 주시기 바랍니다.
                    </p>
                  </div>
                  <!-- 실시간 이용량 표출_실시간 이용량_조회결과 없음 -->
                  <div class="c-nodata u-pb--0 u-pt--0 _realTimeDataView" id="realTimeDataView02" style="display:none;">
                    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                    <p class="c-nodata__text">이용량 조회 결과가 없습니다.</p>
                  </div>
                  <!-- 실시간 이용량 표출_실시간 이용량_조회오류 -->
                  <div class="u-ta-center _realTimeDataView" id="realTimeDataView03" style="display:none;">
                    <i class="c-icon c-icon--notice-sm" aria-hidden="true"></i>
                    <p class="c-text c-text--fs16 u-mt--16">
                      서비스 처리 중 오류가 발생하였습니다.<br>잠시 후 다시 이용해 주시기 바랍니다.
                    </p>
                  </div>
                  <!-- 실시간 이용량 표출_실시간 이용량_조회결과 있음 -->
                  <div class="_realTimeDataView" id="realTimeDataView04" style="display:none;">
                    <div class="c-table">
                      <table>
                        <caption>당월 내 사용량</caption>
                        <colgroup>
                          <col style="width: 40%;"/>
                          <col style="width: 30%;"/>
                          <col/>
                        </colgroup>
                        <thead>
                          <tr>
                            <th class="u-pt--12 u-pb--12">구분</th>
                            <th class="u-pt--12 u-pb--12">이용량</th>
                            <th class="u-pt--12 u-pb--12">잔여량</th>
                          </tr>
                        </thead>
                        <tbody id="realTimeDataViewContent">
                         <!-- 내 사용량 데이터 영역 -->
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>

      <!-- 유의사항 -->
      <div class="u-mt--64">
        <h3 class="c-heading c-heading--fs20 c-heading--regular">유의사항</h3>
        <hr class="c-hr c-hr--title u-mt--11">
        <ul class="c-text-list c-bullet c-bullet--dot">
          <li class="c-text-list__item">데이터 추가제공(데이득 프로모션 등)을 받는 고객님께서 요금제를 변경하실 경우 추가제공 되는 데이터는 중단될 수 있으며 중단 시 다시 제공되지 않습니다.</li>
          <li class="c-text-list__item">3G에서 LTE요금제로 또는 LTE, 5G 요금제간 변경을 희망하시는 고객님께서는 요금제(망)에 맞는 단말기를 사용하셔야 요금제 변경 가능합니다.</li>
          <li class="c-text-list__item">요금제 예약변경 신청 시 실제 변경시점(매월 1일)의 프로모션 할인가로 적용되며, 프로모션 요금 할인가는 당사 정책에 의해 상시 변동될 수 있습니다.</li>
          <li class="c-text-list__item">1일 요금제 변경 시 0시부터 변경 시점까지의 사용량은 초과사용료로 과금됩니다.</li>
        </ul>
      </div>

      <!-- 동의 -->
      <c:set var="termsMargin" value="${socAlliacYn eq 'Y' ? 'u-mt--15' : 'u-mt--48'}"></c:set>

      <c:if test="${'Y' eq socAlliacYn}">
      <div class="c-form-wrap u-mt--48">
        <div class="c-agree c-agree--type2">
          <div class="c-agree__item">
            <input class="c-checkbox" id="chkAgree1" type="checkbox" name="chkAgree">
            <label class="c-label" for="chkAgree1">개인정보 제3자 제공 동의[${jehuProdName} 가입고객 필수 동의]<span class="u-co-gray">(필수)</span></label>
          </div>
        </div>
      </div>
      </c:if>

      <div class="c-form-wrap ${termsMargin}">
        <div class="c-agree c-agree--type2">
            <div class="c-agree__item">
              <input class="c-checkbox" id="chkAgreeA2" type="checkbox" name="chkAgree">
              <label class="c-label" for="chkAgreeA2">초과 사용료 과금 우려 및 기타 안내사항을 모두 확인 하였으며, 변경 진행에 동의합니다.</label>
            </div>
        </div>
      </div>

      <div class="c-button-wrap u-mt--48">
        <button class="c-button c-button--lg c-button--gray c-button--w220" data-dialog-close>취소</button>
        <button class="c-button c-button--lg c-button--primary c-button--w220" onclick="btn_farReg();">요금제 변경</button>
      </div>

    </div> <!-- end of modal body -->
  </div>