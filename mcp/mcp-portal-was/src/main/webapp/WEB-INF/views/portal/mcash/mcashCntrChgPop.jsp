<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<script type="text/javascript" src="/resources/js/portal/mcash/mcashCntrChgPop.js"></script>

<div class="c-modal__content">
  <div class="c-modal__header">
    <h2 class="c-modal__title" id="selLine">M쇼핑할인 회선 변경하기</h2>
    <button class="c-button" data-dialog-close="">
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>

  <div class="c-modal__body">
    <div class="c-row c-row--lg">
      <h3 class="c-heading c-heading--fs20 c-heading--regular">M쇼핑할인 서비스 적용할 회선을 선택하세요</h3>
      <hr class="c-hr c-hr--title">
      <h4 class="c-heading c-heading--fs20 u-mt--64">
        <b>서비스 계약번호</b>
      </h4>
      <div class="c-accordion c-accordion--type4 extra-service mcash-service-acc u-mt--24" data-ui-accordion>
        <ul class="c-accordion__box">
          <c:forEach items="${mcashCntrList}" var="item" varStatus="itemIndex">
            <li class="c-accordion__item">
              <div class="c-accordion__head">
                <button class="runtime-data-insert c-accordion__button" id="ServiceListHeader${itemIndex.index+1}" type="button" aria-expanded="false" aria-controls="ServiceList${itemIndex.index+1}"> </button>
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
              </div>
              <div class="c-accordion__panel expand" id="ServiceList${itemIndex.index+1}" aria-labelledby="ServiceListHeader${itemIndex.index+1}">
                <div class="c-accordion__inside">
                  <div class="c-table">
                    <table>
                      <caption>휴대폰번호, 요금제 정보를 포함한 서비스 계약 정보</caption>
                      <colgroup>
                        <col style="width: 20%">
                        <col>
                      </colgroup>
                      <tbody class="c-table__left">
                      <tr>
                        <th scope="row" class="u-ta-center">휴대폰번호</th>
                          <c:choose>
                             <c:when test="${maskingSession eq 'Y'}">
                                    <td>${item.formatUnSvcNo}</td>
                              </c:when>
                              <c:otherwise>
                                     <td>${item.mkMobileNo}</td>
                              </c:otherwise>
                         </c:choose>
                      </tr>
                      <tr>
                        <th scope="row" class="u-ta-center">요금제</th>
                        <td>${item.rateNm}</td>
                      </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </li>
          </c:forEach>
        </ul>
      </div>
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
    <div class="c-agree u-mt--48">
      <div class="c-agree__item">
        <div class="c-agree__inner">
          <input class="c-checkbox" id="chkCertiAgree" type="checkbox" name="chkCertiAgree" onchange="changeBtnDisable();">
          <label class="c-label" for="chkCertiAgree">선택하신 회선에 혜택 받기를 동의합니다.</label>
        </div>
      </div>
    </div>
  </div>

  <div class="c-modal__footer">
    <div class="c-button-wrap">
      <button class="c-button c-button--lg c-button--gray u-width--220" data-dialog-close>취소</button>
      <button class="c-button c-button--lg c-button--primary u-width--220" id="mcashCntrChg" onclick="mcashCntrChg();" disabled="">변경</button>
    </div>
  </div>
</div>