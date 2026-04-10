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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="c-modal__content">
  <div class="c-modal__header">
    <h2 class="c-modal__title" id="find-cvs-modal__title">지도보기</h2>
    <button class="c-button" data-dialog-close>
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>
  <div class="c-modal__body">
    <ul class="store-list">
      <li class="store-list__item u-bg--gray-1">
        <dl class="store-list__info">
          <dt>
            <i class="c-icon stor-icon" aria-hidden="true"></i>
            <span class="c-hidden"></span><span id="mapStoreName"></span>
          </dt>
          <dd>
            <p id="mapAddr"></p>
            <p id="mapPhone"></p>
          </dd>
        </dl>
        <dl class="store-list__sub">
          <dt>남은 수량</dt>
          <dd>
            <span id="mapNormal"></span>
            <span id="mapNfc"></span>
          </dd>
        </dl>
      </li>
    </ul><!-- 지도 영역-->
    <div class="map">
      <!-- <div class="marker marker--red"></div>
      <div class="marker marker--blue"></div> -->
      <div id="map" style="width:100%;height:100%;"></div>
    </div>
  </div>
</div>