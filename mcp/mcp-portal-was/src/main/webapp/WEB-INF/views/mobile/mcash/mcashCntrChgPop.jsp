<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<script type="text/javascript" src="/resources/js/mobile/mcash/mcashCntrChgPop.js"></script>

<div class="c-modal__content">
  <div class="c-modal__header">
    <h2 class="c-modal__title" id="selLine">M쇼핑할인 회선 변경</h2>
    <button class="c-button" data-dialog-close="">
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>

  <div class="c-modal__body">
    <h3 class="c-heading c-heading--type2">M쇼핑할인 서비스 적용할 회선을 선택하세요</h3>
    <hr class="c-hr c-hr--type3">
    <h3 class="c-heading c-heading--type3 u-mt--32 u-mb--12">서비스 계약번호</h3>
    <div class="c-accordion c-accordion--type3 mcash-service-acc">
      <ul class="c-accordion__box acco-border" id="mcashServiceAcc">
        <c:forEach items="${mcashCntrList}" var="item" varStatus="itemIndex">
          <li class="c-accordion__item">
            <div class="c-accordion__head">
              <div class="c-accordion__check">
                <c:choose>
                    <c:when test="${maskingSession eq 'Y'}">
                            <c:choose>
                                  <c:when test="${!item.isAdult}">
                                    <input class="c-radio" id="serviceNum${itemIndex.index+1}" type="radio" name="serviceNum" value="${item.svcCntrNo}" onclick="return false;">
                                    <label class="c-label u-co-gray-6" for="serviceNum${itemIndex.index+1}">${item.svcCntrNo} &nbsp; <span class="u-co-red">(만 19세 미만 회선)</span></label>
                                  </c:when>
                                  <c:when test="${item.denyRateYn ne 'N'}">
                                    <input class="c-radio" id="serviceNum${itemIndex.index+1}" type="radio" name="serviceNum" value="${item.svcCntrNo}" onclick="return false;">
                                    <label class="c-label u-co-gray-5" for="serviceNum${itemIndex.index+1}">${item.svcCntrNo} &nbsp; <span class="u-co-red">(이용불가 요금제)</span></label>
                                  </c:when>
                                  <c:otherwise>
                                    <input class="c-radio" id="serviceNum${itemIndex.index+1}" type="radio" name="serviceNum" value="${item.svcCntrNo}" onchange="changeBtnDisable();">
                                    <label class="c-label" for="serviceNum${itemIndex.index+1}">${item.svcCntrNo}</label>
                                  </c:otherwise>
                            </c:choose>
                     </c:when>
                     <c:otherwise>
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
                                    <input class="c-radio" id="serviceNum${itemIndex.index+1}" type="radio" name="serviceNum" value="${item.svcCntrNo}" onchange="changeBtnDisable();">
                                    <label class="c-label" for="serviceNum${itemIndex.index+1}">${item.mkSvcCntrNo}</label>
                                  </c:otherwise>
                            </c:choose>
                     </c:otherwise>
                </c:choose>


              </div>
              <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#mcashServiceList${itemIndex.index+1}">
                <div class="c-accordion__trigger"></div>
              </button>
            </div>
            <div class="c-accordion__panel expand c-expand" id="mcashServiceList${itemIndex.index+1}">
              <div class="c-accordion__inside">
                <div class="info-box">
                  <dl>
                    <dt>휴대폰번호</dt>
                    <c:choose>
                        <c:when test="${maskingSession eq 'Y'}">
                               <dd>${item.formatUnSvcNo}</dd>
                         </c:when>
                         <c:otherwise>
                                <dd>${item.mkMobileNo}</dd>
                         </c:otherwise>
                    </c:choose>
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
        <span>회선 변경 유의사항</span>
      </h5>
      <ul class="c-notice__list">
        <li>다회선을 보유하신 고객님은 통신 요금 할인받을 회선을 변경할 수 있습니다.</li>
        <li>선불 요금제, 데이터쉐어링 요금제, 2nd 디바이스 요금제(PAD 전용, 스마트워치 전용 등) 이용 회선은 본 서비스 이용이 불가합니다.</li>
      </ul>
    </div>
    <div class="c-agree">
      <div class="c-agree__item">
        <input class="c-checkbox" id="chkCertiAgree" type="checkbox" name="chkCertiAgree" onchange="changeBtnDisable();">
        <label class="c-label" for="chkCertiAgree">선택하신 회선에 혜택 받기를 동의합니다.</label>
      </div>
    </div>

    <div class="c-modal__footer">
      <div class="c-button-wrap">
        <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
        <button class="c-button c-button--full c-button--primary" id="mcashCntrChg" onclick="mcashCntrChg();" disabled="">변경</button>
      </div>
    </div>
  </div>
</div>
