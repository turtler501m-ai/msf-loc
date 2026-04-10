<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">고객센터 > 상담예약 > 상담예약결과보기</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/cs/inquiryBooking/inquiryBookingHist.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">상담 예약</h2>
            </div>
            <div class="c-tabs c-tabs--type1" >
                <div class="c-tabs__list" role="tablist">
                    <a class="c-tabs__button" id="tab1">상담 예약 신청</a>
                    <span class="c-tabs__button is-active" id="tab2">상담 예약 결과보기</span>
                </div>
            </div>
            <div class="c-tabs__panel u-block" id="tabB2panel" >
                <div class="c-row c-row--lg">
                	<c:if test="${empty maskingSession}">
			      	  	<div class="masking-badge-wrap">
					        <div class="masking-badge">
					        	<a class="masking-badge__button" href="/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
					        		<i class="masking-badge__icon" aria-hidden="true"></i>
					 				<p>가려진(*)<br />정보보기</p>
					       		</a>
					        </div>
				        </div>
			        </c:if>
                    <div class="csInq-info-wrap">

                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-mt--8 u-co-mint">요금제 변경, 납부방법 변경 등 단순한 업무는 아래 링크를 누르시면 바로 이용 가능하십니다.<br/>
                                <div class="u-mt--8">
                                    <a class="c-text--underline u-co-sub-4 c-text--fs17 c-text--fs15 u-fw--bold" href="/mypage/farPricePlanView.do" title="요금제 변경 페이지 바로가기">요금제 변경</a>
                                    <a class="c-text--underline u-ml--16 u-co-sub-4 c-text--fs17 c-text--fs15 u-fw--bold" href="/mypage/regServiceView.do" title="부가서비스 신청 페이지 바로가기">부가서비스 신청</a>
                                    <a class="c-text--underline u-ml--16 u-co-sub-4 c-text--fs17 c-text--fs15 u-fw--bold" href="/mypage/chargeView05.do" title="납부방법 변경 페이지 바로가기">납부방법 변경</a>
                                    <a class="c-text--underline u-ml--16 u-co-sub-4 c-text--fs17 c-text--fs15 u-fw--bold" href="/mypage/unpaidChargeList.do" title="청구요금 납부 페이지 바로가기">청구요금 납부</a>
                                    <a class="c-text--underline u-ml--16 u-co-sub-4 c-text--fs17 c-text--fs15 u-fw--bold" href="/mypage/usimSelfChg.do" title="유심 셀프변경 페이지 바로가기">유심 셀프변경</a>
                                </div>
                            </li>
                            <li class="c-text-list__item u-mt--8">결과 내용은 최근 3개월만 조회 가능합니다.</li>
                            <li class="c-text-list__item u-mt--8">처리 결과가 '진행중'으로 보여지는 경우 상담원이 접수내용을 확인하고 있는 상태입니다. 최대한 빠르게 연락 드리겠습니다.</li>
                        </ul>
                    </div>

                    <div class="counselling-head" aria-hidden="true">
                        <span class="counselling-head__text" style="width: 11rem;">문의유형</span>
                        <strong class="counselling-head__title" style="width: 17rem; !important;">제목</strong>
                        <span class="counselling-head__num">회선번호</span>
                        <span class="counselling-head__date">접수일</span>
                        <span class="counselling-head__date">상담 예약일</span>
                        <span class="counselling-head__state">처리 결과</span>
                    </div>

                        <%--데이터 있는 경우--%>
                    <div class="c-accordion c-accordion--type2 counselling" data-ui-accordion>
                        <ul class="c-accordion__box" id="myBookingHist">

                        </ul>
                    </div>


                    <div class="c-paging"id="paging">
                        <nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="goPaging" />
                    </div>

                </div>
            </div>
            <input type="hidden" name="pageNo" id="pageNo">
        </div>
    </t:putAttribute>
</t:insertDefinition>
