<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<script type="text/javascript" src="/resources/js/mobile/popup/niceAuth.js?version=24.05.21"></script>
<script type="text/javascript" src="/resources/js/mobile/mcash/mcashJoinPop.js"></script>

<input type="hidden" id="isAuth" name="isAuth">
<div class="c-modal__content">
  <div class="c-modal__header">
    <h1 class="c-modal__title" id="mcashServiceJoinTitle">M쇼핑할인 가입하기</h1>
    <button class="c-button" data-dialog-close>
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>

  <div class="c-modal__body">
    <c:if test="${RESULT_CODE eq '0000'}">
      <div id="termAgree">
        <h3 class="c-heading c-heading--type1">약관동의</h3>
        <h3 class="c-heading c-heading--type2">M쇼핑할인 서비스 가입 안내 및 이용 동의 절차</h3>
        <div class="c-agree u-mt--0">
          <input class="c-checkbox" id="chkAgreeAll" type="checkbox" name="chkAgreeAll" onclick="chkAgreeAll(this);">
          <label class="c-label" for="chkAgreeAll">이용약관 및 개인정보 수집/이용에 모두 동의합니다.</label>
          <div class="c-agree__item">
            <input class="c-checkbox c-checkbox--type2 chkAgree" id="chkAgree1" type="checkbox" name="chkAgree1">
            <label class="c-label" for="chkAgree1">서비스 이용약관 동의<span class="u-co-gray">(필수)</span>
            </label>
            <button class="c-button c-button--xsm" data-dialog-trigger="#termsOfServiceAgree" onclick="mcashSetEventId('chkAgree1');">
              <span class="c-hidden">상세보기</span>
              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
            </button>
          </div>
          <div class="c-agree__item">
            <input class="c-checkbox c-checkbox--type2 chkAgree" id="chkAgree2" type="checkbox" name="chkAgree2">
            <label class="c-label" for="chkAgree2">개인정보 수집 &middot; 이용동의<span class="u-co-gray">(필수)</span>
            </label>
            <button class="c-button c-button--xsm" data-dialog-trigger="#personalInfoCollectAgree" onclick="mcashSetEventId('chkAgree2');">
              <span class="c-hidden">상세보기</span>
              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
            </button>
          </div>
          <div class="c-agree__item">
            <input class="c-checkbox c-checkbox--type2 chkAgree" id="chkAgree3" type="checkbox" name="chkAgree3">
            <label class="c-label" for="chkAgree3">개인정보 제3자 제공동의<span class="u-co-gray">(필수)</span>
            </label>
            <button class="c-button c-button--xsm" data-dialog-trigger="#othersTrnsAgree" onclick="mcashSetEventId('chkAgree3');">
              <span class="c-hidden">상세보기</span>
              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
            </button>
          </div>
        </div>
        <div class="c-notice-wrap">
          <hr>
          <h5 class="c-notice__title">
            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
            <span>서비스 이용 불가 고객 안내</span>
          </h5>
          <ul class="c-notice__list">
            <li>만 19세 미만 고객님은 M쇼핑할인 서비스 가입 및 이용이 불가합니다.</li>
            <li>법인 고객님은 M쇼핑할인 서비스 가입 및 이용이 불가합니다.</li>
          </ul>
        </div>
      </div>

    <!-- 회선 선택 영역 -->
      <div id="selLine">
        <h3 class="c-heading c-heading--type2">M쇼핑할인 서비스 적용할 회선을 선택하세요</h3>
        <hr class="c-hr c-hr--type3">
        <h3 class="c-heading c-heading--type3 u-mb--12">서비스 계약번호</h3>
        <div class="c-accordion c-accordion--type3 mcash-service-acc">
          <ul class="c-accordion__box acco-border" id="mcashServiceAcc">
            <c:forEach items="${mcashCntrList}" var="item" varStatus="itemIndex">
              <li class="c-accordion__item">
                <div class="c-accordion__head">
                  <div class="c-accordion__check">
                    <c:choose>
                      <c:when test="${!item.isAdult}">
                        <input class="c-radio" id="serviceNum${itemIndex.index+1}" type="radio" name="serviceNum" value="${item.svcCntrNo}" onclick="return false;">
                        <label class="c-label u-co-gray-6" for="serviceNum${itemIndex.index+1}">${item.mkSvcCntrNo} &nbsp; <span class="u-co-red">(만 19세 미만 회선)</span></label>
                      </c:when>
                      <c:when test="${item.denyRateYn ne 'N'}">
                        <input class="c-radio" id="serviceNum${itemIndex.index+1}" type="radio" name="serviceNum" value="${item.svcCntrNo}" onclick="return false;">
                        <label class="c-label u-co-gray-5" for="serviceNum${itemIndex.index+1}">${item.mkSvcCntrNo} &nbsp; <span class="u-co-red">(이용불가 요금제)</span></label>
                      </c:when>
                      <c:otherwise>
                        <input class="c-radio" id="serviceNum${itemIndex.index+1}" type="radio" name="serviceNum" value="${item.svcCntrNo}" onchange="changeSendButtonStatus();">
                        <label class="c-label" for="serviceNum${itemIndex.index+1}">${item.mkSvcCntrNo}</label>
                      </c:otherwise>
                    </c:choose>
                  </div>
                  <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#mcashServiceList${itemIndex.index+1}">
                    <div class="c-accordion__trigger"> </div>
                  </button>
                </div>
                <div class="c-accordion__panel expand c-expand" id="mcashServiceList${itemIndex.index+1}">
                  <div class="c-accordion__inside">
                    <div class="info-box">
                      <dl>
                        <dt>휴대폰번호</dt>
                        <dd>${item.mkMobileNo}</dd>
                      </dl>
                      <dl>
                        <dt>요금제</dt>
                        <dd>${item.rateNm}</dd>
                      </dl>
                    </div>
                  </div>
                </div>
              </li>
            </c:forEach>
          </ul>
        </div>
        <div class="c-notice-wrap">
          <h5 class="c-notice__title">
            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
            <span>회선 선택 유의사항</span>
          </h5>
          <ul class="c-notice__list">
            <li>선불요금제, 데이터쉐어링 요금제, 2nd 디바이스 요금제(PAD전용, 스마트워치전용 등) 이용 회선은 본 서비스 이용이 불가합니다.</li>
          </ul>
        </div>
        <div class="c-notice-wrap">
          <h5 class="c-notice__title">
            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
            <span>이용 방법 안내</span>
          </h5>
          <ul class="c-notice__list">
            <li>적립한 캐시로 매월 최대 2만원까지 통신비를 자동 할인 받으실 수 있습니다.</li>
            <li>통신요금 할인은 3천 원/5천 원/1만 원/2만 원 중 매월 말일 기준으로 보유 중인 캐시에서 통신비가 자동으로 차감되어 할인됩니다.</li>
            <li>캐시적립 메뉴를 통해 브랜드 쇼핑 결제 후 캐시를 적립받을 수 있습니다.</li>
            <li>추가적으로 이벤트 또는 미션 참여 보상으로도 캐시가 지급될 수 있습니다.</li>
            <li>캐시적립 유형은 즉시적립/예정적립 두 가지로, 예정적립의 경우 구매 확정 후 캐시가 지급되기까지 일정 기간이 소요될 수 있습니다. </li>
            <li>제휴사 브랜드별 캐시적립 유형은 각 브랜드 캐시적립 페이지 하단의 유의사항을 참고 부탁드립니다.</li>
            <li>제휴사 브랜드 및 브랜드별 적립률/적립금액은 정책에 따라 변경될 수 있으며, 제휴사 사정에 따라 캐시 적립 혜택이 종료될 수 있습니다.</li>
            <li>광고 차단 어플리케이션 또는 서버 우회 등의 방법을 이용하여 접속 시 캐시 적립이 정상적으로 처리되지 않을 수 있습니다.</li>
            <li>타 제휴 서비스를 통해 장바구니 추가 후 M쇼핑할인에서 쇼핑 결제 시 캐시 적립이 정상적으로 처리되지 않을 수 있습니다.</li>
            <li>캐시 적립 후 결제를 취소한 경우 캐시 적립이 취소됩니다.</li>
            <li>정당하지 못한 방법을 통해 적립한 캐시는 모두 회수 처리되며, 향후 캐시 지급 거절 또는 서비스 제공 불가 사유가 될 수 있습니다.</li>
            <li>통신비 할인에 사용된 캐시는 익월 1일에 자동 차감되며, 할인 내역은 통신비 명세서에서 확인 가능합니다.</li>
            <li>할인 내역은 통신비 명세서에서 "제휴사 납부"로 표기되고, 모바일 명세서에서는 "과납요금"으로 표기됩니다.</li>
          </ul>
        </div>
        <div class="c-agree">
          <div class="c-agree__item">
            <input class="c-checkbox" id="benefitAgreeChk" type="checkbox" name="benefitAgreeChk" onchange="changeSendButtonStatus();">
            <label class="c-label" for="benefitAgreeChk">선택하신 회선에 혜택 받기를 동의합니다.</label>
          </div>
        </div>
      </div>

      <div class="c-chk-wrap" style="display: none;">
        <input class="c-radio c-radio--button c-radio--button--icon" id="onlineAuthType3" value="M" type="radio" name="onlineAuthType" checked>
        <label class="c-label u-ta-left" for="onlineAuthType3">
          <i class="c-icon c-icon--card" aria-hidden="true"></i>SMS 본인인증
        </label>
      </div>
      <div class="c-form _onlineAuthType" data-value="M">
        <div class="c-button-wrap u-mt--40">
          <button id="btnNiceAuthM" class="c-button c-button--full c-button--h48 c-button--mint _btnNiceAuth">휴대폰 인증</button>
        </div>
      </div>

      <div class="c-button-wrap u-mt--48">
        <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
        <button class="c-button c-button--full c-button--primary" id="mcashServiceJoinSend" disabled="">확인</button>
      </div>
    </c:if>

    <!-- 가입불가 영역 -->
    <c:if test="${RESULT_CODE ne '0000'}">
      <div class="complete u-pt--40">
        <i class="c-icon c-icon--notice" aria-hidden="true"></i>
        <p class="complete__heading">
          <b>${custNm}</b><span> 님은</span>
        </p>
        <p class="complete__text u-mt--12">
          <c:if test="${RESULT_CODE eq '2001'}">만 19세 미만 가입고객으로</c:if>
          <c:if test="${RESULT_CODE eq '2002'}">일시적인 오류로 인해</c:if>
          <c:if test="${RESULT_CODE eq '2003'}">법인 가입고객으로</c:if>
          <br/>M쇼핑할인 서비스 가입이 불가능 합니다.<br/>이용에 불편을 드려 대단히 죄송합니다.
        </p>
      </div>

      <div class="c-button-wrap u-mt--48">
        <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
      </div>
    </c:if>

  </div>
</div>
