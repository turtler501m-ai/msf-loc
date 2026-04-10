<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
<script type="text/javascript" src="/resources/js/common/validator.js"></script>
<script type="text/javascript" src="/resources/js/portal/coupon/couponDownPop.js"></script>

<div class="c-modal__content">
    <div class="c-modal__header">
        <h2 class="c-modal__title" id="modalCouponAddTitle">쿠폰 받기</h2>
        <button class="c-button" data-dialog-close id="dtlPopClose">
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>

    <!-- 쿠폰 다운 전 start -->
    <div class="c-modal__body" id="divMainOne" style="padding: 2rem 2rem 0;">
        <c:choose>
            <c:when test="${CouponCategoryList ne null and !empty CouponCategoryList}">

                <h3 class="c-heading u-co-mint" style="padding:0.5rem; font-size:0.9375rem;">쿠폰을 받기 전 자세히 보기를 통해 유의사항을 반드시 확인하세요.</h3>
                <c:forEach var="item" items="${CouponCategoryList}" varStatus="status">
                    <div class="c-table c-table--type2" style="padding:1rem;border-radius: 1rem;">
                        <input type="hidden" data-coupon-ctg-cd="${item.coupnCtgCd}" data-sms-snd-posbl-yn="${item.smsSndPosblYn}" data-coupon-ctg-nm="${item.coupnCtgNm}" name="counponInfo">
                        <table>
                            <colgroup>
                                <col style="width: 33%">
                                <col>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th scope="row" style="padding:0.5rem;">쿠폰명</th>
                                    <td style="padding:0.5rem;">${item.coupnCtgNm}</td>
                                </tr>
                                <tr>
                                    <th scope="row" style="padding:0.5rem;">금액</th>
                                    <td style="padding:0.5rem;">무료</td>
                                </tr>
                                <tr>
                                    <th scope="row" style="padding:0.5rem;">등록 유효기간</th>
                                    <td style="padding:0.5rem;">${item.useStartDate} ~ ${item.useEndDate}</td>
                                </tr>
                                <tr>
                                    <th scope="row" style="padding:0.5rem;">사용 가능기간</th>
                                    <td style="padding:0.5rem;">${item.avPrd}</td>
                                </tr>
                                <tr>
                                    <th scope="row" style="padding:0.5rem;">사용처</th>
                                    <td style="padding:0.5rem;">${item.usePlc} <c:if test="${item.linkPc ne null and !empty item.linkPc}"><a href="${item.linkPc}" target="_blank">(${item.linkPc})</a></c:if></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <br/>
                </c:forEach>
                <h3 class="c-heading" style="padding:1rem;">쿠폰 발급 신청 정보</h3>
                <div class="c-form-field">
                    <div class="c-form__input-group u-mt--0">
                        <div class="c-form__input c-form__input-division">
                            <input class="c-input--div3 onlyNum" id="txtPhoneF" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 첫자리" onkeyup="nextFocus(this, 3, 'txtPhoneM');">
                            <span>-</span>
                            <input class="c-input--div3 onlyNum" id="txtPhoneM" type="text" maxlength="4" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 중간자리" onkeyup="nextFocus(this, 4, 'txtPhoneL');">
                            <span>-</span>
                            <input class="c-input--div3 onlyNum" id="txtPhoneL" type="text" maxlength="4" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 뒷자리">
                            <label class="c-form__label" for="inpRecieverPhone">휴대폰 번호</label>
                        </div>
                    </div>
                </div>
                </br>
                <div class="c-box c-box--type1" style="padding:0.7rem;">
                    <ul class="c-text-list">
                        <li class="c-text-list__item u-co-gray">ㆍ현재 사용중인 본인명의의 회선으로만 발행이 가능합니다.</li>
                        <li class="c-text-list__item u-co-gray">ㆍ발급받은 쿠폰은 취소가 불가합니다.</li>
                    </ul>
                </div>
                <%-- 				<br/>
                                <h3 class="c-heading" style="padding:1rem;">약관동의</h3>
                                <div class="c-agree c-agree--type2">
                                    <div class="c-accordion c-accordion--type3" data-ui-accordion="">
                                        <ul class="c-accordion__box">
                                            <li class="c-accordion__item" style="border-bottom: 0.0625rem">
                                                  <div class="c-accordion__head u-pl--0">
                                                      <input class="c-checkbox c-checkbox--type2 _grAgree" id="notice1" type="checkbox">
                                                    <label class="c-label" for="notice1">
                                                        <c:choose>
                                                            <c:when test="${rtnFormDto ne null and !empty rtnFormDto}">
                                                                ${rtnFormDto.dtlCdNm}
                                                            </c:when>
                                                        </c:choose>
                                                    </label>
                                                    <button class="runtime-data-insert c-accordion__button" style="position:unset;" id="insrAccHeader01" type="button" aria-expanded="false" aria-controls="insrAccContent01"></button>
                                                  </div>
                                                  <div class="c-accordion__panel expand" id="insrAccContent01" aria-labelledby="insrAccHeader01">
                                                    <div class="c-accordion__inside" style="padding:1rem;">
                                                        <c:choose>
                                                            <c:when test="${rtnFormDto ne null and !empty rtnFormDto}">
                                                                ${rtnFormDto.docContent}
                                                            </c:when>
                                                        </c:choose>
                                                    </div>
                                                  </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div> --%>
            </c:when>

            <c:otherwise>
                <div class="c-nodata">
                    <i class="c-icon c-icon--nodata-coupon" aria-hidden="true"></i>
                    <p class="c-nodata__text">잘못된 접근입니다.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <!-- 쿠폰 다운 전 end -->

    <!-- 쿠폰 다운 후 start -->
    <div class="c-modal__body" id="divMainTwo" style="display:none;">
        <div class="c-text-box c-text-box--blue">
            <div class="c-icon c-icon--complete" id="iconBox" aria-hidden="true"></div><br><br>
            <h3 class="c-heading c-heading--fs20" id="endMsg"></h3><br>
        </div>
    </div>
    <!-- 쿠폰 다운 후 end -->

    <c:choose>
        <c:when test="${CouponCategoryList ne null and !empty CouponCategoryList}">
            <div class="c-modal__footer">
                <!-- 쿠폰 다운 전 버튼 -->
                <div class="c-button-wrap u-mt--0" id="divFooterOne">
                    <button class="c-button c-button--lg c-button--white c-button--w220" id="btnCan" onclick="popClose();">취소</button>
                    <button class="c-button c-button--lg c-button--primary c-button--w220" id="btnDown">쿠폰받기</button>
                </div>
                <!-- 쿠폰 다운 후 버튼 -->
                <div class="c-button-wrap u-mt--0" id="divFooterTwo" style="display:none;">
                    <button class="c-button c-button--lg c-button--white c-button--w220" id="btnClose" onclick="popClose('close');">닫기</button>
                    <button class="c-button c-button--lg c-button--primary c-button--w220" id="btnMyCoupon">쿠폰보러가기</button>
                </div>
            </div>
        </c:when>
    </c:choose>

</div>

