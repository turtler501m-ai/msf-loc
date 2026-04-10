<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
 <div class="c-modal c-modal--xlg" id="phone-compare-modal" role="dialog" aria-labelledby="#phone-compare-modal__title">
    <div class="c-modal__dialog" role="document">
       <form id="appForm" name="appForm" method="post" action="/appForm/appFormDesignView.do">
        <input type="hidden" name="hndsetModelId" />
        <input type="hidden" name="rateCd" />
        <input type="hidden" name="prodId" />
        <input type="hidden" name="outUnitPric" value=""/>
        <input type="hidden" name="selPric" value=""/>
        <input type="hidden" name="prodCtgType" value=""/>
    </form>
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="phone-compare-modal__title">휴대폰 비교하기</h2>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <p class="c-text c-text--fs20 u-ta-center">
            <b>요금부터 성능까지</b>한눈에 비교하세요!
          </p>
          <ul class="phone-select">
            <!-- case1 : 휴대폰 선택 후-->
            <li class="phone-select__item">
              <div class="c-form">
                <label class="c-label c-hidden" for="phoneSelect_0">휴대폰 선택</label>
                <select class="c-select" id="phoneSelect_0" name="phoneSelect_0">
                  <option label="휴대폰 선택" disabled>휴대폰 선택</option>
                </select>
              </div>
              <div class="c-form">
                <label class="c-label c-hidden" for="colorSelect_0">용량/색상 선택</label>
                <select class="c-select" id="colorSelect_0" name="colorSelect_0">
                  <option label="용량/색상" disabled>용량/색상</option>
                </select>
              </div>
              <span id="phone_img_div_0">
              </span>
              <div class="c-form u-mt--48">
                <label class="c-label c-hidden" for="rateSelect_0">요금제 선택</label>
                <select class="c-select" id="rateSelect_0" name="rateSelect_0">
                    <option label="요금제 선택" disabled="" selected="">요금제 선택</option>
                </select>
              </div>
              <!--[2022-01-19] 마크업 수정-->
              <span id="phone_dl_0">
              </span>
              <div class="c-button-wrap u-mt--40">
                <button type="button" id="phone_button_0" class="c-button c-button--full c-button--red">가입하기</button>
              </div>
            </li><!-- case1 : 휴대폰 선택 후-->
            <li class="phone-select__item">
              <div class="c-form">
                <label class="c-label c-hidden" for="phoneSelect_1">휴대폰 선택</label>
                <select class="c-select" id="phoneSelect_1" name="phoneSelect_1">
                  <option label="휴대폰 선택" disabled>휴대폰 선택</option>
                </select>
              </div>
              <div class="c-form">
                <label class="c-label c-hidden" for="colorSelect_1">용량/색상 선택</label>
                <select class="c-select" id="colorSelect_1" name="colorSelect_1">
                  <option label="용량/색상" disabled>용량/색상</option>
                </select>
              </div>
              <span id="phone_img_div_1">
              </span>
              <div class="c-form u-mt--48">
                <label class="c-label c-hidden" for="rateSelect_1">요금제 선택</label>
                <select class="c-select" id="rateSelect_1" name="rateSelect_1">
                    <option label="요금제 선택" disabled="" selected="">요금제 선택</option>
                </select>
              </div>
              <!--[2022-01-19] 마크업 수정-->
              <span id="phone_dl_1">
              </span>
              <div class="c-button-wrap u-mt--40">
                <button type="button" id="phone_button_1" class="c-button c-button--full c-button--red">가입하기</button>
              </div>
            </li><!-- case2 : 휴대폰 선택 전-->
            <li class="phone-select__item">
              <div class="c-form">
                <label class="c-label c-hidden" for="phoneSelect_2">휴대폰 선택</label>
                <select class="c-select" id="phoneSelect_2" name="phoneSelect_2">
                  <option label="휴대폰 선택" disabled>휴대폰 선택</option>
                </select>
              </div>
              <div class="c-form">
                <label class="c-label c-hidden" for="colorSelect_2">용량/색상 선택</label>
                <select class="c-select" id="colorSelect_2" name="colorSelect_2">
                  <option label="용량/색상" disabled>용량/색상</option>
                </select>
              </div>
              <span id="phone_img_div_2">
              </span>
              <div class="c-form u-mt--48">
                <label class="c-label c-hidden" for="rateSelect_2">요금제 선택</label>
                <select class="c-select" id="rateSelect_2" name="rateSelect_2">
                    <option label="요금제 선택" disabled="" selected="">요금제 선택</option>
                </select>
              </div>
              <span id="phone_dl_2">
              </span>
              <div class="c-button-wrap u-mt--40">
                <button type="button" id="phone_button_2" class="c-button c-button--full c-button--red is-disabled">가입하기</button>
              </div>
            </li>
          </ul>
          <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">월 납부금액</h3>
          <hr class="c-hr c-hr--title">
          <ul class="phone-comparison">
            <li id="phone_li_0" class="phone-comparison__item">
            </li>
            <li id="phone_li_1" class="phone-comparison__item">
            </li>
            <li id="phone_li_2" class="phone-comparison__item">
            </li>
          </ul>
          <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
            <!-- <li class="c-text-list__item u-co-gray">월 납부금액은 24개월 약정(할부) 기준입니다.</li> -->
            <li class="c-text-list__item u-co-gray">실제 납부금액은 가입조건에 따라 달라질 수 있습니다.</li>
          </ul>
          <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">기기사양</h3>
          <hr class="c-hr c-hr--title">
          <ul class="phone-comparison">
            <li id="phone_comp_li_0" class="phone-comparison__item">
            </li>
            <li id="phone_comp_li_1" class="phone-comparison__item">
            </li>
            <li id="phone_comp_li_2" class="phone-comparison__item">
            </li>
          </ul>
          <hr class="c-hr c-hr--basic u-mt--48 u-mb--32">
          <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item u-co-gray">위 정보는 휴대폰과 요금제에 대한 가격만 비교하는 정보로 당해 프로모션에 따라 월 납부금액이 변경될 수 있습니다.</li>
            <li class="c-text-list__item u-co-gray">각 휴대폰별로 적용된 약정기간이 다를 수 있으므로, 자세한 가격정보는 휴대폰 별 상세 페이지에서 확인하시기 바랍니다.</li>
          </ul>
        </div>
      </div>

     </div>
    </div>

