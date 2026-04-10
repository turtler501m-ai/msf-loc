<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
    uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<t:insertDefinition name="layoutGnbDefault">
   <t:putAttribute name="titleAttr">1:1 문의 내역</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
         <script type="text/javascript" src="/resources/js/cs/inquiry/inquiryIntHist.js"></script>
         <script>
         function myCsInquiryList(pageNo){

             ajaxCommon.createForm({
                 method:"post"
                 ,action:"/mypage/myCsInquiryList.do"
             });
             ajaxCommon.attachHiddenElement("pageNo",pageNo);
             ajaxCommon.formSubmit();
         }
         </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">1:1 문의 내역</h2>
            </div>
            <div class="c-row c-row--lg">
                <p class="c-text c-text--fs20 u-fw--medium">
                    답변상태가<span class="u-co-point-4">&nbsp;처리중</span>인 문의사항은 상담원이 문의를 처리 중입니다.
                </p>
                <div class="u-flex-between">
                    <p class="c-bullet c-bullet--dot u-co-gray">주말 및 휴일에는 답변 처리가 다소 지연될 수 있습니다.</p>
                    <div class="c-button-wrap c-button-wrap--right">
                        <a class="c-button c-button-round--sm c-button--white"
                            href="/cs/csInquiryInt.do">1:1 문의하기</a>
                    </div>
                </div>
                <!-- 리스트 최대 10개씩 노출 -->
                <div class="counselling-head u-mt--20" aria-hidden="true">
                    <span class="counselling-head__text">문의유형</span>
                    <strong class="counselling-head__title">제목</strong>
                    <span class="counselling-head__num">회선번호</span>
                    <span class="counselling-head__date">문의일</span>
                    <span class="counselling-head__state">답변상태</span>
                </div>
                <c:choose>
	                <c:when test="${inquiryList ne null and !empty inquiryList}">
		                <div class="c-accordion c-accordion--type2 counselling" data-ui-accordion>
		                    <ul class="c-accordion__box">
		                        <c:forEach var="inquiryList" items="${inquiryList}" varStatus="status">
		                        <li class="c-accordion__item">
		                            <div class="c-accordion__head">
		                                <span class="c-accordion__text">
		                                    <span class="c-hidden">문의유형</span>${inquiryList.qnaSubCtgNm}</span>
		                                    <strong class="c-accordion__title"><span class="c-hidden">제목</span>${inquiryList.qnaSubject}</strong>
		                                    <span class="c-accordion__num"> <span class="c-hidden">회선번호</span>${inquiryList.mobileNo}</span>
		                                    <span class="c-accordion__date"> <span class="c-hidden">문의일</span>${inquiryList.cretDT}</span>
		                                    <span class="c-accordion__state"> <span class="c-hidden">답변상태</span>
		                                        <c:choose>
		                                        <c:when test="${inquiryList.ansStatVal eq '2'}"><span class="state-label complete">답변완료</span></c:when>
		                                        <c:otherwise><span class="state-label registering">접수중</span></c:otherwise>
		                                        </c:choose>
		                                    </span>
		                                    <button class="acc_headerA${status.count} runtime-data-insert c-accordion__button" type="button" aria-expanded="false" aria-controls="acc_contentA${status.count}">
		                                        <span class="c-hidden">상세열기</span>
		                                    </button>
		                                </div>
		                                <div class="c-accordion__panel expand" id="acc_contentA${status.count}" aria-labelledby="acc_headerA${status.count}">
		                                <div class="c-accordion__inside question">
		                                    <span class="box-prefix">질문</span>
		                                    <div class="box-content">${inquiryList.qnaContent}</div>
		                                </div>
		                                <c:if test="${inquiryList.ansContent ne null and inquiryList.ansContent ne ''}">
		                                <div class="c-accordion__inside answer">
		                                    <span class="box-prefix">A</span>
		                                    <div class="box-content">${inquiryList.ansContent}</div>
		                                </div>
		                                </c:if>
		                            </div>
		                        </li>
		                        </c:forEach>
		                    </ul>
		                </div>
	                </c:when>
	                <c:otherwise>
	                    <p class="nodata--type2">조회 결과가 없습니다.</p>
	                </c:otherwise>
                </c:choose>
                <c:if test="${pageInfoBean.totalCount > 10}">
                <nav class="c-paging" aria-label="검색결과">
                    <nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="myCsInquiryList" />
                </nav>
                </c:if>
</div>
</div>
    </t:putAttribute>
</t:insertDefinition>