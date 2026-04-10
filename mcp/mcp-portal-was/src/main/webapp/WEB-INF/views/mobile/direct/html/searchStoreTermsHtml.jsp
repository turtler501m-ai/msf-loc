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
          <h1 class="c-modal__title" id="find-cvs-title">편의점 찾기</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="c-tabs c-tabs--type2" data-tab-active="1">
            <!-- [2021-11-19] 탭활성화 파라미터 업데이트-->
            <div class="c-tabs__list c-expand sticky-header--modal" data-tab-list="">
              <button class="c-tabs__button" data-tab-header="#tabCVS1-panel">매장별/지역별</button>
              <button class="c-tabs__button" data-tab-header="#tabCVS2-panel">현재위치</button>
            </div>
            <div class="c-tabs__panel" id="tabCVS1-panel"> </div>
            <div class="c-tabs__panel" id="tabCVS2-panel">
              <div class="term-localinfo">
                <h3 class="c-heading c-heading--type1">위치정보 이용약관</h3>
                <p class="c-text c-text--type2"> kt M모바일 편의점 매장찾기는 위치기반 서비스를 제공함에 있어
                  개인 위치정보주체자(이하: 이용자)에게 다음과 같은 내용을 고지하고
                  개인위치정보 이용에 대한 사전 동의를 요청합니다.</p><!-- [2021-11-29] 이용약관 box 수정(디자인 변경 적용) 시작=====-->
                <div class="terms-txt-box">위치기반 서비스 이용약관 txt</div><!-- [$2021-11-29] 이용약관 box 수정(디자인 변경 적용) 끝=====-->
                <div class="c-form">
                  <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">
                  <label class="c-label" for="chkAgree">가까운 매장 찾기를 위한 위치정보 이용약관에 동의합니다.</label>
                </div><!-- [2021-11-29] 버튼 클래스 추가(디자인 변경 적용) 시작=====-->
                <div class="c-button-wrap">
                  <button class="c-button c-button--gray c-button--lg u-width-120">동의안함</button>
                  <button class="c-button c-button--full c-button--primary">동의</button>
                </div><!-- [$2021-11-29] 버튼 클래스 추가(디자인 변경 적용) 끝=====-->
              </div>
            </div>
          </div>
        </div>
      </div>