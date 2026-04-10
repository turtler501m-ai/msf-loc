<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">1:1 상담문의</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
         <script type="text/javascript" src="/resources/js/portal/cs/inquiry/inquiryIntHist.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">1:1 상담문의</h2>
            </div>
            <div class="c-tabs c-tabs--type1" >
                <div class="c-tabs__list" role="tablist">
                    <a class="c-tabs__button" id="tab1" href="/cs/csInquiryInt.do" aria-selected="false">1:1 문의 작성</a>
                    <a class="c-tabs__button is-active" id="tab2" href="/cs/csInquiryIntHist.do" aria-selected="true" title="내 문의 확인하기 선택됨">내 문의 확인하기</a>
                </div>
            </div>
            <div class="c-tabs__panel u-block" id="tabB2panel" >
                <c:choose>
                    <c:when test="${userSession eq null and authSmsDto eq null}">
                        <div class="c-row c-row--lg">
                            <p class="c-text c-text--fs17 u-mb--8">kt M모바일 고객센터에서는 별도의 회원가입 없이 휴대폰 본인인증 후 1:1 상담 문의 및 확인이 가능합니다.</p>
                            <span class="c-bullet c-bullet--fyr u-co-sub-4 fs-13">회원가입 후 kt M모바일만의 다양한 맞춤 서비스를 경험해보세요.</span>
                            <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>
                            <div class="c-form-wrap u-mt--48">
                                <span class="c-form__title">약관동의</span>
                                <div class="c-agree">
                                    <div class="c-agree__item">
                                        <button class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMSINF&cdGroupId2=TERMSINF03');">
                                            <span class="c-hidden">개인정보 수집이용 동의 상세팝업 보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <div class="c-agree__inner">
                                            <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">
                                            <label class="c-label" for="chkAgree">개인정보 수집이용 동의</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="c-button-wrap u-mt--56">
                                <button class="c-button c-button--lg c-button--primary c-button--w460" id="applyBtn" disabled="disabled">확인</button>
                            </div>

                            <div class="c-button-wrap u-mt--40">
                                <a class="c-button c-button--underline u-mr--12 u-co-black" href="/loginForm.do">kt M모바일계정 로그인</a>
                                <a class="c-button c-button--underline u-ml--12 u-co-black" href="/join/fstPage.do">회원가입</a>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="c-row c-row--lg">
                            <p class="c-text c-text--fs17">답변상태가 처리중인 문의사항은 상담원이 문의를 처리 중입니다.</p>
                            <p class="c-text c-text--fs17 u-mt--8">주말 및 휴일에는 답변 처리가 다소 지연될 수 있습니다.</p>
                            <div class="counselling-head" aria-hidden="true">
                                <span class="counselling-head__text">문의유형</span>
                                <strong class="counselling-head__title">제목</strong>
                                <span class="counselling-head__num">회선번호</span>
                                <span class="counselling-head__date">문의일</span>
                                <span class="counselling-head__state">답변상태</span>
                            </div>
                            <c:choose>
                                <c:when test="${inquiryList ne null and !empty inquiryList}">
                                    <c:if test="${empty maskingSession}">
	                                    <div class="masking-badge-wrap">
		                                    <div class="masking-badge">
		                                        <a class="masking-badge__button" href="/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기" style="top: -9.875rem;">
		                                            <i class="masking-badge__icon" aria-hidden="true"></i>
		                                             <p>가려진(*)<br />정보보기</p>
		                                           </a>
		                                    </div>
		                                </div>
		                            </c:if>
                                    <div class="c-accordion c-accordion--type2 counselling" data-ui-accordion>
                                        <ul class="c-accordion__box">
                                            <c:forEach var="inquiryList" items="${inquiryList}" varStatus="status">
                                                <li class="c-accordion__item">
                                                    <div class="c-accordion__head">
                                                        <span class="c-accordion__text">
                                                            <span class="c-hidden">문의유형</span>${inquiryList.qnaSubCtgNm}
                                                        </span>
                                                        <strong class="c-accordion__title">
                                                            <span class="c-hidden">제목</span>${inquiryList.qnaSubject}
                                                        </strong>
                                                        <span class="c-accordion__num">
                                                            <span class="c-hidden">회선번호</span>${inquiryList.mobileNo}
                                                        </span>
                                                        <span class="c-accordion__date">
                                                            <span class="c-hidden">문의일</span>${inquiryList.cretDT}
                                                        </span>
                                                        <span class="c-accordion__state">
                                                            <span class="c-hidden">답변상태</span>
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
                                                            <span class="box-prefix">Q</span>
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
                            <c:if test="${totalCount > 10}">
                                <nav class="c-paging" aria-label="검색결과">
                                    <nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="goPaging" />
                                </nav>
                            </c:if>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
