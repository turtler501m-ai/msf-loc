<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<div class="c-modal" id="find-store-map-dialog" role="dialog" tabindex="-1" aria-describedby="#find-store-map-title">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
	<div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="find-store-map-title">지도보기</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="store-result store-result--next c-expand u-mt--0 u-mb--m32">
            <ul class="store-list">
              <li class="store-list__item">
                <dl class="store-list__info">
                  <dt id="mapStoreName"></dt>
                  <dd>
                    <p class="store-list__text" id="mapAddr"></p>
                    <p class="store-list__text" id="mapPhone"></p>
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
  </div>
</div>     