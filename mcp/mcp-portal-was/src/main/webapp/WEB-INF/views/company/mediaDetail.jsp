<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<c:set var="pageTitle" value="홍보관 | kt M mobile" />
<%@ include file="/WEB-INF/views/company/layouts/companyHead.jsp" %>
<body>
  <div class="ly-wrap">
    <!--skip navigation(웹접근성)-->
    <div id="skipNav">
        <a class="skip-link" href="#main-content">본문 바로가기</a>
    </div>

    <%@ include file="/WEB-INF/views/company/layouts/companyHeader.jsp" %>

    <div class="sub-head-banner">
      <div class="sub-head-banner-wrap">
        <p data-aos="fade-right" data-aos-duration="1800" data-aos-delay="300">홍보관</p>
        <img class="img-pc" src="/resources/company/images/sub_marketing_bg.png" alt="">
        <img class="img-mo" src="/resources/company/images/sub_marketing_bg_mo.png" alt="">
      </div>
    </div>
    <div class="sub-gnb sub-gnb--center">
      <a class="sub-gnb__item is-active" href="/company/mediaList.do#subGnb1" title="언론보도 페이지 이동">언론보도</a>
      <a class="sub-gnb__item" href="/company/awardDetails.do#subGnb1" title="수상내역 페이지 이동">수상내역</a>
      <a class="sub-gnb__item" href="/company/noticeList.do#subGnb1" title="공지사항 페이지 이동">공지사항</a>
    </div>
    <div class="ly-content" id="main-content">
        <div class="ly-content-wrap">
          <section class="content-section">
            <div class="board-detail-title-wrap">
               <strong class="board-detail-title">${newsDetail.newsDatSubject}</strong>
               <c:choose>
               <c:when test="${empty newsDetail.upDt }">
               <p class="board-post">
                  <span><fmt:formatDate value="${newsDetail.cretDt}" pattern="yyyy.MM.dd"/></span>
                </p>
            </div>
            <div class="board-detail-content">
               <!-- 에이터 영역 -->
               <article class="board-editor">
                 <p>
                    ${newsDetail.newsDatSbst}                                 ​
                 </p>
               </article>
               <p class="board-post mo">
                  <span><fmt:formatDate value="${newsDetail.cretDt}" pattern="yyyy.MM.dd"/></span>
                </p>
               </c:when>
               <c:otherwise>
                  <p class="board-post">
                  <span><fmt:formatDate value="${newsDetail.upDt}" pattern="yyyy.MM.dd"/></span>
                </p>
            </div>
            <div class="board-detail-content">
               <!-- 에이터 영역 -->
               <article class="board-editor">
                 <p>
                    ${newsDetail.newsDatSbst}                                 ​
                 </p>
               </article>
               <p class="board-post mo">
                  <span><fmt:formatDate value="${newsDetail.upDt}" pattern="yyyy.MM.dd"/></span>
                </p>
                </c:otherwise>
                </c:choose>
            </div>

            <input type="hidden" id="prevSeq" value="${newsDetail.preSeq}">
            <input type="hidden" id="nextSeq" value="${newsDetail.nextSeq}">
            <input type="hidden" id="curPage" value="${pageNo}">

             <div class="board-detail-paging">
                                        <!-- 이전 -->
           <c:choose>
            <c:when test="${newsDetail.preSeq == 0}">
            <a class="board-detail-anchor" href="javascript:void(0);" onclick="goPage('prev');">
                <span class="c-hidden">이전 페이지</span>
                <i class="c-icon c-icon--arrow-board-detail-left" aria-hidden="true"></i>
                     이전
            </a>
            </c:when>
            <c:otherwise>
                <a class="board-detail-anchor active" href="javascript:void(0);" onclick="goPage('prev');">
                  <span class="c-hidden">이전 페이지</span>
                  <i class="c-icon c-icon--arrow-board-detail-left" aria-hidden="true"></i>
                       이전
                </a>
            </c:otherwise>
            </c:choose>
              <a class="board-detail-btn-list" href="javascript:void(0);" onclick="goList(${pageInfoBean.pageNo});">
                <span class="c-hidden">목록 페이지</span>
                                     목록
              </a>
             <!-- 다음 -->
            <c:choose>
            <c:when test="${newsDetail.nextSeq == 0}">
            <a class="board-detail-anchor" href="javascript:void(0);" onclick="goPage('next');">
                <span class="c-hidden">다음 페이지</span>
                    다음
                <i class="c-icon c-icon--arrow-board-detail-right" aria-hidden="true"></i>
            </a>
            </c:when>
            <c:otherwise>
                <a class="board-detail-anchor active" href="javascript:void(0);" onclick="goPage('next');">
                  <span class="c-hidden">다음 페이지</span>
                      다음
                  <i class="c-icon c-icon--arrow-board-detail-right" aria-hidden="true"></i>
                </a>
            </c:otherwise>
            </c:choose>
            </div>
          </section>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/company/layouts/companyFloatButton.jsp" %>
    <%@ include file="/WEB-INF/views/company/layouts/companyFooter.jsp" %>

    <!-- 딤처리 오버레이 -->
    <div class="overlay" id="overlayPc" onclick="toggleMenuAll()"></div>
    <div class="overlay" id="overlayMo" onclick="toggleMenu()"></div>
  </div>

  <%@ include file="/WEB-INF/views/company/layouts/commonJs.jsp" %>
  <script type="text/javascript" src="/resources/js/portal/ktm.ui.js?version=250513"></script>
  <script type="text/javascript" src="/resources/js/jqueryCommon.js?version=250513"></script>
  <script type="text/javascript" src="/resources/company/js/mediaDetail.js?version=250513"></script>

</body>
</html>