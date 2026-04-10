<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script type="text/javascript" charset="UTF-8" src="/resources/js/portal/telCounsel/telCounselForm.js"></script>
	<div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="find-cvs-modal__title">법인 가입 상담</h2>
          <button id="telCounselCloseBtn" type="button" class="c-button" data-dialog-close="">
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="etc-guide">
            <!-- [2022-02-08] 아이콘 변경-->
            <i class="c-icon c-icon--corp-counsel" aria-hidden="true"></i>
            <strong class="etc-guide__title">법인 고객님들의
              <br>가입을 편리하게 도와드립니다!
            </strong>
            <p class="etc-guide__text u-co-gray">아래에 회사 법인명, 연락처, 사업자 등록번호 등 귀하 법인의 기본 정보를 남겨주시면 저희 상담원이 전화를 드려 가입 진행을 도와드리겠습니다. (수집 정보는 상담 외 가입 진행 시 법인 신용등급 평가 등에 사용 될 수 있습니다.)</p>
            <p class="c-bullet c-bullet--fyr u-mt--16 u-co-sub-4 u-ta-left">가입을 안하실 경우 수집된 정보는 1년 후 자동 파기됩니다.</p>
          </div>
          <div class="c-form-wrap u-mt--32">
            <div class="c-form-group" role="group" aira-labelledby="inpJoinInfoHead">
              <h3 class="c-group--head c-hidden" id="inpJoinInfoHead">법인 정보 입력</h3>
              <div class="c-form-field">
                <div class="c-form__input">
                  <input class="c-input" name="counselNm" id="counselNm" type="text" maxlength="30" placeholder="법인명 입력" title="법인명을" style="ime-mode:active;" onkeyup="nextChk(this);">
                  <label class="c-form__label" for="counselNm">법인명</label>
                </div>
              </div>
              <div class="c-form-field">
                <div class="c-form__input">
                  <input class="c-input" id="juridicalNumber" name="juridicalNumber" pattern="d*" type="number" maxlength="10" placeholder="숫자만 입력가능" title="사업자 등록번호를" onkeyup="nextChk(this);">
                  <label class="c-form__label" for="juridicalNumber">사업자등록번호</label>
                </div>
              </div>
              <div class="c-form-field">
                <div class="c-form__input">
                  <input class="c-input" id="agentNm" name="agentNm" maxlength="30" type="text" placeholder="대표자(대리인) 이름 입력" style="ime-mode:active; title="대표자(대리인) 이름을" onkeyup="nextChk(this);">
                  <label class="c-form__label" for="agentNm">대표자(대리인) 이름</label>
                </div>
              </div>
              <div class="c-form-field">
                <div class="c-form__input-group">
                  <div class="c-form__input c-form__input-division">
                    <input class="c-input--div3" id="inpTelNum1" pattern="[0-9]*" type="text" placeholder="숫자입력" title="연락처 앞자리 입력" maxlength="3" onkeyup="nextChk(this);">
                    <span>-</span>
                    <input class="c-input--div3" id="inpTelNum2" pattern="[0-9]*" type="text" placeholder="숫자입력" title="연락처 중간자리 입력" maxlength="4" onkeyup="nextChk(this);">
                    <span>-</span>
                    <input class="c-input--div3" id="inpTelNum3" pattern="[0-9]*" type="text" placeholder="숫자입력" title="연락처 뒷자리 입력" maxlength="4" onkeyup="nextChk(this);">
                    <label class="c-form__label" for="inpTelNum">연락처</label>
                  </div>
                </div>
              </div>
              <div class="c-form-field">
                <div class="c-form__select has-value">
                  <select class="c-select"  id="operType" name="operType" disabled="">
                    <option label="유심요금제" value="유심요금제" selected="">유심요금제</option>
                  </select>
                  <label class="c-form__label" for="operType">가입유형</label>
                </div>
              </div>
            </div>
          </div>
          <h3 class="c-heading c-heading--fs16 u-mt--48" id="inpSecurity">보안문자 입력</h3>
          <p class="c-text c-text--fs15 u-co-gray u-mt--8">아래 보안문자 이미지의 숫자를 보이는 대로 입력해주세요.</p>
          <div class="security-box">
            <div class="security-box__panel">
              <div class="security-box__panel-image" id="catpcha" style="text-align: center;">
                <!-- <img src="../../resources/images/portal/content/img_temp_security.svg" alt="보안문자"> -->
              </div>
              <div class="security-box__panel-button">
                <button class="c-button c-button-round--sm c-button--white" id="soundOnKor">음성듣기</button>
                <button class="c-button" id="reLoad">
                  <span class="c-hidden">새로고침</span>
                  <i class="c-icon c-icon--refresh" aria-hidden="true"></i>
                </button>
              </div>
            </div>
            <div class="c-form">
              <div class="c-input-wrap">
                <label class="c-label c-hidden" for="inpSecurityText">보안문자 입력</label>
                <input class="c-input" type="text" placeholder="보안문자 입력" id="answer" name="answer" maxlength="5" title="보안문자를" onkeyup="nextChk(this);">
              </div>
            </div>
          </div>
          <div class="c-form-wrap u-mt--40">
            <h3 class="c-form__title">약관동의</h3>
            <div class="c-agree c-agree--type2">
              <div class="c-agree__item">
                <input class="c-checkbox" id="chkAgreeAll" type="checkbox" name="chkDS" href="javascript:void(0);" onclick="nextChk(this);">
                <label class="c-label" for="chkAgreeAll"> 개인정보 수집 및 이용에 대한 동의<span class="u-co-gray">(필수)</span>
                </label>
              </div>
              <div class="c-agree__inside">
                <ul class="c-text-list c-bullet c-bullet--number">
                  <li class="c-text-list__item u-mt--0">수집 이용의 목적<p class="c-bullet c-bullet--dot u-co-gray">서비스 상품 및 가입안내, 법인 신용평가 등급 등의 가입 상담을 위한 정보 제공</p>
                  </li>
                  <li class="c-text-list__item u-mt--16">수집항목<p class="c-bullet c-bullet--dot u-co-gray">법인명, 사업자 등록번호, 대표자(대리인) 이름, 연락처, 가입 유형</p>
                  </li>
                  <li class="c-text-list__item u-mt--16">이용 및 보유기간<p class="c-bullet c-bullet--dot u-co-gray">동의를 통한 수집 일시 기준 1년 후 자동 파기</p>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <hr class="c-hr c-hr--basic u-mt--48">
          <b class="c-flex c-text c-text--fs15 u-mt--24">
            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
            <span class="u-ml--4">알려드립니다</span>
          </b>
          <p class="c-text c-text--fs17 u-mt--16">아래 서류가 구비되어 있을 경우 더욱 원활한 상담과 빠른 가입이 진행됩니다.</p>
          <p class="c-bullet c-bullet--dot u-co-gray u-mt--16">법인인감증명, 사업자등록증, 대표자 혹은 대리인 신분증 사본(대리인일 경우 대리인 신분증 및 재직증명서), 표준 재무재표(최근 3개년), 납부내역증명(최근 3개월)</p>
        </div>
        <div class="c-modal__footer">
          <div class="c-button-wrap">
            <button class="c-button c-button--lg c-button--primary c-button--w460" disabled="disabled" id="telCounselConfirm" onclick="telCounselAjax();">법인 가입 상담하기</button>
          </div>
        </div>
      </div>