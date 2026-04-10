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
<c:set var="pageTitle" value="홍보채널 | kt M mobile" />
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
        <p data-aos="fade-right" data-aos-duration="1800" data-aos-delay="300">홍보채널</p>
        <img class="img-pc" src="/resources/company/images/sub_marketing_bg.png" alt="">
        <img class="img-mo" src="/resources/company/images/sub_marketing_bg_mo.png" alt="">
      </div>
    </div>
    <div class="sub-gnb sub-gnb--center">
      <a class="sub-gnb__item" href="/company/mediaList.do" title="보도자료 페이지 이동">보도자료</a>
      <a class="sub-gnb__item is-active" href="javascript:void(0);">이벤트</a>
    </div>
    <div class="ly-content" id="main-content">
        <div class="ly-content-wrap">
          <section class="content-section">
            <h4 class="content-section__title">이벤트</h4>
            <ul class="event-board-list">
                <c:forEach var="event" items="${eventList}">
                    <li class="event-board-item">
                        <a id="goDetail" href="javascript:void(0);" ntcartSeq="${event.ntcartSeq}" linkTarget="${event.linkTarget}" linkUrlAdr="${event.linkUrlAdr}">
                            <div class="event-board-img">
                                <img src="${event.listImg}" alt="${event.imgDesc}">
                            </div>
                            <p class="event-board-title">${event.ntcartSubject}</p>
                            <p class="board-post event">
                                <span>${event.comEventStartDt} ~ ${event.comEventEndDt}</span>
                                <em>${event.ntcartHitCnt}</em>
                            </p>
                            <p class="board-post event mo">
                                <span>${event.comEventStartDt} ~ ${event.comEventEndDt}</span>
                                <em>${event.ntcartHitCnt}</em>
                            </p>
                        </a>
                    </li>
                </c:forEach>
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
  <script type="text/javascript" src="/resources/js/portal/ktm.ui.js?version=250513"></script>
  <script type="text/javascript" src="/resources/js/jqueryCommon.js?version=250513"></script>
  <script type="text/javascript" src="/resources/company/js/companyEvent.js?version=250513"></script>

</body>
</html>