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
<div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="find-cvs-title">지도보기</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <!-- [2021-11-30] 상단 탭 영역 삭제 및 버튼 삭제  -->
          <div class="store-result store-result--next c-expand u-mt--0 u-mb--m32">
            <ul class="store-list">
              <li class="store-list__item">
                <i class="c-icon stor-icon" aria-hidden="true"></i>
                <dl class="store-list__info">
                  <dt id="mapStoreName">대치삼성점</dt>
                  <dd>
                    <p class="store-list__text" id="mapAddr">서울특별시 강남구 테헤란로 98길 8, 1층</p>
                    <p class="store-list__text" id="mapPhone">02-6916-1500</p>
                  </dd>
                </dl>
                <dl class="store-list__sub">
                  <dt>남은 수량</dt>
                  <dd>
                    <span id="mapNormal">일반 3</span>
                    <span id="mapNfc">NFC 1</span>
                  </dd>
                </dl>
              </li>
            </ul><!-- 지도 영역-->
            <div class="map" style="display:none;">
              <div id="map" style="width:100%;height:100%;"></div>
            </div>
          </div>
        </div>
      </div>
