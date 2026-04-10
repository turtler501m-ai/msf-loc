<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<div class="c-modal c-modal--md" id="find-cvs-modal" role="dialog" aria-labelledby="#find-cvs-modal__title">
    <div class="c-modal__dialog" role="document">
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
                <dt id="mapStoreName"></dt>
                <dd>
                  <p id="mapAddr"></p>
                  <p id="mapPhone"></p>
                </dd>
              </dl>
              <dl class="store-list__sub">
                <dt id="mapProdNm"></dt>
                <dd>
                  <span id="mapProdDesc"></span>
                </dd>
              </dl>
            </li>
          </ul><!-- 지도 영역-->
          <div class="map">
            <div id="map" style="width:100%;height:100%;"></div>
          </div>
        </div>
      </div>
    </div>
 </div>   