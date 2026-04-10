<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<t:insertDefinition name="mlayoutDefault">

  <t:putAttribute name="titleAttr">M쇼핑할인</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/mobile/mcash/mcashMain.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">

    <div class="ly-content u-pb--0" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">M쇼핑할인
          <button class="header-clone__gnb"></button>
        </h2>
      </div>
    </div>

    <div class="swiper-container swiper-event-banner c-expand" id="swiperCardBanner" >
      <div class="swiper-wrapper">

      </div>
    </div>

    <iframe id="mcashMain" src="" width="100%" height="800px" scrolling="no"></iframe>
    <input type="hidden" id="mcashMainUrl" value="${mcashMainUrl}">
  </t:putAttribute>
</t:insertDefinition>