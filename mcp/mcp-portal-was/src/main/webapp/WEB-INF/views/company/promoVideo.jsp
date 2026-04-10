<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
      <a class="sub-gnb__item" href="/company/mediaList.do" title="언론보도 페이지 이동">언론보도</a>
      <a class="sub-gnb__item is-active" href="javascript:void(0);" title="홍보영상 페이지 이동">홍보영상</a>
      <a class="sub-gnb__item" href="/company/awardDetails.do" title="수상내역 페이지 이동">수상내역</a>
    </div>
    <div class="ly-content" id="main-content">
        <div class="ly-content-wrap">
          <section class="content-section">
            <h4 class="content-section__title">홍보영상</h4>
            <ul class="board-list">
                <li class="board-item">
                    <a href="javascript:void(0);" class="news-link" data-seq="89" data-target="" onclick="goDetail(89, 1);">
                        <div class="board-title-wrap">
                            <p class="board-title">홍보영상 텍스트홍보영상 텍스트홍보영상 텍스트홍보영상 텍스트</p>
                        </div>
                        <p class="board-text">홍보영상 텍스트홍보영상 텍스트홍보영상 텍스트</p>
                        <p class="board-post"><span>2025.04.11</span></p>
                        <p class="board-post mo"><span>2025.04.11</span></p>
                    </a>
                </li>
                <li class="board-item">
                    <a href="javascript:void(0);" class="news-link" data-seq="89" data-target="" onclick="goDetail(89, 1);">
                        <div class="board-title-wrap">
                            <p class="board-title">홍보영상 텍스트홍보영상 텍스트홍보영상 텍스트</p>
                        </div>
                        <p class="board-text">홍보영상 텍스트홍보영상 텍스트</p>
                        <p class="board-post"><span>2025.04.11</span></p>
                        <p class="board-post mo"><span>2025.04.11</span></p>
                    </a>
                </li>
                <li class="board-item">
                    <a href="javascript:void(0);" class="news-link" data-seq="89" data-target="" onclick="goDetail(89, 1);">
                        <div class="board-title-wrap">
                            <p class="board-title">홍보영상 텍스트홍보영상 텍스트홍보영상 텍스트</p>
                        </div>
                        <p class="board-text">홍보영상 텍스트홍보영상 텍스트</p>
                        <p class="board-post"><span>2025.04.11</span></p>
                        <p class="board-post mo"><span>2025.04.11</span></p>
                    </a>
                </li>
                <li class="board-item">
                    <a href="javascript:void(0);" class="news-link" data-seq="89" data-target="" onclick="goDetail(89, 1);">
                        <div class="board-title-wrap">
                            <p class="board-title">홍보영상 텍스트홍보영상 텍스트홍보영상 텍스트</p>
                        </div>
                        <p class="board-text">홍보영상 텍스트홍보영상 텍스트</p>
                        <p class="board-post"><span>2025.04.11</span></p>
                        <p class="board-post mo"><span>2025.04.11</span></p>
                    </a>
                </li>
            </ul>
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

</body>
</html>