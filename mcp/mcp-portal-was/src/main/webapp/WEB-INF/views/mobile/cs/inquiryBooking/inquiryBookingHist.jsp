<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/cs/inquiryBooking/inquiryBookingHist.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    상담 예약
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

            <div class="c-tabs c-tabs--type2" data-ignore="true">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button" id="prvTab">상담 예약 신청</button>
                    <button class="c-tabs__button is-active" >상담 예약 결과보기</button>
                </div>

                <div class="c-tabs__panel">
                	<c:if test="${empty maskingSession}">
                      <div class="masking-badge-wrap">
                          <div class="masking-badge" style="top: -2rem;">
                                 <a class="masking-badge__button" href="/m/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
                                <i class="masking-badge__icon" aria-hidden="true"></i>
                                   <p>가려진(*) 정보보기</p>
                              </a>
                          </div>
                      </div>
                    </c:if>
                    <div class="csInq-info-wrap u-mt--40">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-mt--8 u-co-mint">요금제 변경, 납부방법 변경 등 단순한 업무는 아래 링크를 누르시면 바로 이용 가능하십니다.
                                <div class="u-mt--8">
                                    <a class="c-button c-button--underline u-mr--16 u-co-sub-4 u-fw--bold" href="/m/mypage/farPricePlanView.do" title="요금제 변경 페이지 바로가기">요금제 변경</a>
                                    <a class="c-button c-button--underline u-mr--16 u-co-sub-4 u-fw--bold" href="/m/mypage/regServiceView.do" title="부가서비스 신청 페이지 바로가기">부가서비스 신청</a>
                                    <a class="c-button c-button--underline u-mr--16 u-co-sub-4 u-fw--bold" href="/m/mypage/chargeView05.do" title="납부방법 변경 페이지 바로가기">납부방법 변경</a>
                                    <a class="c-button c-button--underline u-mr--16 u-co-sub-4 u-fw--bold" href="/m/mypage/unpaidChargeList.do" title="청구요금 납부 페이지 바로가기">청구요금 납부</a>
                                    <a class="c-button c-button--underline u-mr--16 u-co-sub-4 u-fw--bold" href="/m/mypage/usimSelfChg.do" title="유심 셀프변경 페이지 바로가기">유심 셀프변경</a>
                                </div>
                            </li>
                            <li class="c-text-list__item u-mt--8">결과 내용은 최근 3개월만 조회 가능합니다.</li>
                            <li class="c-text-list__item u-mt--8">처리 결과가 '진행중'으로 보여지는 경우 상담원이 접수내용을 확인하고 있는 상태입니다. 최대한 빠르게 연락 드리겠습니다.</li>
                        </ul>
                    </div>
                    <div class="c-accordion c-accordion--type1 faq-accordion" id="myBookingHist">
                        <ol class="c-accordion__box" id="liDataArea">

                        </ol>
                    </div>
                    <div class="c-nodata" style="display:none;">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-nodata__text">조회 결과가 없습니다.</p>
                    </div>
                    <div class="c-button-wrap" id="addBtnArea">
                        <button class="c-button c-button--full" id="moreBtn">더보기
                            <span class="c-button__sub">
                                        <span id="listCount"></span>/<span id="totalCount"></span>
                                        <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
                                    </span>
                        </button>
                    </div>
                </div>
            </div>
            <input type="hidden" id="pageNo" name="pageNo" value="1"/>
        </div>

    </t:putAttribute>
</t:insertDefinition>