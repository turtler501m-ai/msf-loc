<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%
response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
if (request.getProtocol().equals("HTTP/1.1"))
    response.setHeader("Cache-Control", "no-cache");
%>



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
      <button class="sub-gnb__item" data-target="subGnb1">언론보도</button>
      <a class="sub-gnb__item" href="/company/awardDetails.do#subGnb1" title="수상내역 페이지 이동">수상내역</a>
      <a class="sub-gnb__item" href="/company/noticeList.do#subGnb1" title="공지사항 페이지 이동">공지사항</a>
    </div>
    <div class="ly-content" id="main-content">
        <div class="ly-content-wrap">
          <section class="content-section" id="subGnb1">
            <h4 class="content-section__title">언론보도</h4>
            <ul class="board-list">
                <c:forEach var="news" items="${newsDataList}">
                    <li class="board-item">
                        <a href="javascript:void(0);" class="news-link" data-seq="${news.newsDatSeq}" data-target="${news.linkTarget}" onclick="goDetail(${news.newsDatSeq}, ${pageInfoBean.pageNo});">
                           <c:choose>
                                <c:when test="${empty news.upDt }">
                                <div class="board-title-wrap">
                                <p class="board-title">${news.newsDatSubject}</p>
                                </div>
                                <p class="board-text">${news.newsDatSbst}</p>
                                <p class="board-post"><span><fmt:formatDate value="${news.cretDt}" pattern="yyyy.MM.dd"/></span></p>
                                <p class="board-post mo"><span><fmt:formatDate value="${news.cretDt}" pattern="yyyy.MM.dd"/></span></p>
                                </c:when>
                                <c:otherwise>
                                <div class="board-title-wrap">
                                <p class="board-title">${news.newsDatSubject}</p>
                                </div>
                                <p class="board-text">${news.newsDatSbst}</p>
                                <p class="board-post"><span><fmt:formatDate value="${news.upDt}" pattern="yyyy.MM.dd"/></span></p>
                                <p class="board-post mo"><span><fmt:formatDate value="${news.upDt}" pattern="yyyy.MM.dd"/></span></p>
                                </c:otherwise>
                                </c:choose>
                        </a>
                    </li>
                </c:forEach>
            </ul>

            <input type="hidden" id="pageNo" name="pageNo" value="${pageInfoBean.pageNo}">
            <!-- 페이징 버튼들 -->
            <nav class="paging" aria-label="페이지 네비게이션">
                <!-- 맨 처음 페이지 버튼 -->
                <a class="paging-anchor ${pageInfoBean.pageNo == 1 ? '' : 'active'}"
                   href="javascript:void(0);" onclick="goPaging(1);">
                    <span class="c-hidden">맨 처음 페이지</span>
                    <i class="c-icon c-icon--arrow-board-double-left" aria-hidden="true"></i>
                </a>

                <!-- 이전 페이지 버튼 -->
                <a class="paging-anchor ${pageInfoBean.pageNo > 1 ? 'active' : ''}"
                   href="javascript:void(0);" onclick="goPaging(${pageInfoBean.pageNo - 1});">
                    <span class="c-hidden">이전 페이지</span>
                    <i class="c-icon c-icon--arrow-board-left" aria-hidden="true"></i>
                </a>


                <!-- 페이지 번호들 -->
                <div class="paging-wrap">
                    <c:forEach var="i" begin="${startPage}" end="${endPage}">
                        <a class="paging-number ${pageInfoBean.pageNo == i ? 'active' : ''}"
                           href="javascript:void(0);" onclick="goPaging(${i});">
                    <span class="c-hidden">페이지</span>${i}
                        </a>
                    </c:forEach>
                </div>

                <!-- 다음 페이지 버튼 -->
               <a class="paging-anchor ${pageInfoBean.pageNo < totalPage ? 'active' : ''}"
                   href="javascript:void(0);" onclick="goPaging(${pageInfoBean.pageNo + 1});">
                    <span class="c-hidden">다음 페이지</span>
                    <i class="c-icon c-icon--arrow-board-right" aria-hidden="true"></i>
                </a>

                <!-- 맨 끝 페이지 버튼 -->
		             <a class="paging-anchor ${pageInfoBean.pageNo < totalPage ? 'active' : ''}"
		               href="javascript:void(0);" onclick="goPaging(${totalPage});">
		                <span class="c-hidden">마지막 페이지</span>
		                <i class="c-icon c-icon--arrow-board-double-right" aria-hidden="true"></i>
		            </a>
		          </nav>

		          <!-- 모바일에서는 더보기 버튼으로 대체 -->
		          <div class="paging-more-wrap">
                <button class="paging-more" id="paging-more-btn">
                                            더보기(<span class="   paging-more-count" id="pageNav"><span id="listCount">1</span>/<span id="totalCount">${pageInfoBean.totalPageCount}</span></span>)
                    <i class="c-icon c-icon--arrow-board-down" aria-hidden="true"></i>
                </button>
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
  <script type="text/javascript" src="/resources/company/js/mediaList.js?version=250513"></script>

</body>
</html>