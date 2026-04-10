<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="mlayoutNoGnbFotter">

  <t:putAttribute name="titleAttr">유심 셀프변경</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/usim/replaceUsimSelf.js?version=25-10-31"></script>
    <script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js?version==25-10-31"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">

    <input type="hidden" id="isUsimNumberCheck" name="isUsimNumberCheck" />
    <input type="hidden" id="isAuth" name="isAuth" />

    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">
          유심 셀프변경
          <button class="header-clone__gnb"></button>
        </h2>
      </div>

      <hr class="c-hr c-hr--type3">
      <p class="u-fs-13 u-mt--32">
        유심 셀프변경 시 음성, 문자 등 휴대폰 사용이 중단됩니다. 신속히 새로운 유심을 단말기에 장착하여 1~3회 전원을 껐다 켜면 사용 가능합니다.
      </p>

      <h4 class="c-heading u-fw-normal u-fs-20 u-mt--38 u-mb--12">유심(USIM) 교체 전 확인 필수 안내</h4>
      <ul class="c-text-list c-bullet c-bullet--dot">
        <li class="c-text-list__item u-co-gray">
          <span class="u-fw--bold u-co-black">SIM에 <span class="u-co-red">연락처</span>가 저장되어 있는 경우 단말기에 연락처를 복사하거나 백업 앱을 통해 저장하신 후 USIM 교체를 진행해 주세요.</span>
          <ul class="c-text-list c-bullet c-bullet--hyphen">
            <li class="c-text-list__item u-co-gray u-mt--2">삼성(갤럭시) : 연락처 앱 열기 → 메뉴/설정 → 연락처 내보내기 → 내장 저장공간 저장</li>
            <li class="c-text-list__item u-co-gray u-mt--2">아이폰 : 설정 → 하단 “앱” → “연락처” 선택 → 연락처 가져오기</li>
          </ul>
          <p class="c-bullet c-bullet--fyr u-mt--2">단말기에 따라 경로가 다를 수 있으며, 위 경로로 확인되지 않을 경우 제조사에 문의해 주시기 바랍니다.</p>
        </li>
        <li class="c-text-list__item u-co-gray u-mt--12">
          <span class="u-fw--bold u-co-black"><span class="u-co-red">선불 모바일 교통카드</span>의 잔액은 USIM에 저장되므로 USIM 변경 전 아래 내용을 확인해 주세요.</span><br>
          자세한 사항은 티머니/이즐 고객센터로 문의해 주시기 바랍니다.
          <ul class="c-text-list c-bullet c-bullet--hyphen">
            <li class="c-text-list__item u-co-gray u-mt--2">티머니 : USIM 변경 전에 잔액을 환불하고, 변경 완료 후 재가입</li>
            <li class="c-text-list__item u-co-gray u-mt--2">이즐 : USIM 교체 전 앱에서 잔액 환급을 신청하거나 잔액 보관 신청을 한 뒤 변경 후 잔액을 이전</li>
          </ul>
        </li>
        <li class="c-text-list__item u-co-gray u-mt--12">
          현재 사용 중인 단말이 아닌 다른 단말에서 이용하실 예정인 경우, ‘유심보호서비스(부가서비스)’를 먼저 해지해 주세요.
        </li>
      </ul>

      <h3 class="c-heading c-heading--type1 u-mt--50">유심 변경 신청 정보</h3>
      <div class="c-form">
        <div class="c-form__input ">
          <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" maxlength="90">
          <label class="c-form__label" for="cstmrName">이름</label>
        </div>
      </div>
      <div class="c-form">
        <div class="c-form__input-group has-value">
          <div class="c-form__input c-form__input-division has-value">
            <input class="c-input--div3 onlyNum" name="cstmrTelFn" id="cstmrTelFn" type="tel" maxlength="3" placeholder="숫자입력" value="" onkeyup="nextFocus(this, 3, 'cstmrTelMn');" autocomplete="off"> <span>-</span>
            <input class="c-input--div3 onlyNum" name="cstmrTelMn" id="cstmrTelMn" type="tel" maxlength="4" placeholder="숫자입력" value="" onkeyup="nextFocus(this, 4, 'cstmrTelRn');" autocomplete="off"> <span>-</span>
            <input class="c-input--div3 onlyNum" name="cstmrTelRn" id="cstmrTelRn" type="tel" maxlength="4" placeholder="숫자입력" value="" autocomplete="off">
            <label class="c-form__label" for="cstmrTelFn">변경할 휴대폰 번호 입력</label>
          </div>
        </div>
      </div>

      <h3 class="c-heading c-heading--type1">신규 유심 정보 등록</h3>
      <div class="c-form c-scan-wrap">
        <div class="c-box c-box--type1 c-box--dotted u-mt--36">
          <img class="center-img" src="/resources/images/mobile/content/img_usim_card.png" alt="유심 카드 샘플 이미지">
        </div>
        <div class="c-button-wrap c-button-wrap--right" id="divUsimScan">
          <button class="c-button _ocrRecord" data-type="usim">
            <span class="c-text c-text--type3 u-co-sub-4">스캔하기</span>
            <i class="c-icon c-icon--scan" aria-hidden="true"></i>
          </button>
        </div>
      </div>
      <div class="c-form">
        <div class="c-form__input">
          <input class="c-input onlyNum" id="reqUsimSn" type="tel" maxlength="19" pattern="[0-9]*" placeholder="직접입력('-'없이 입력)" title="유심번호 입력" value="">
          <label class="c-form__label" for="reqUsimSn">유심번호 19자리</label>
        </div>
        <div class="c-button-wrap">
          <button id="btnUsimNumberCheck" class="c-button c-button--full c-button--mint">유심번호 유효성 체크</button>
        </div>
      </div>

      <h3 class="c-heading c-heading--type1">유심변경 본인인증 동의</h3>
      <div class="c-agree u-mt--0">
        <input class="c-checkbox" id="chkAgree" type="checkbox">
        <label class="c-label" for="chkAgree">본인인증 절차 동의</label>
      </div>
      <ul class="c-text-list c-bullet c-bullet--dot">
        <li class="c-text-list__item u-co-gray">
          명의자 본인 확인을 위해서 본인인증이 필요합니다. 안전한 서비스 이용 및 고객님의 개인정보 보호를 위해 본인인증을 받으신 고객분만 이용이 가능합니다. 유심변경 이용을 위해서 본인확인 절차를 진행해주세요.
        </li>
      </ul>

      <!-- 본인인증 방법 선택 -->
      <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
        <jsp:param name="controlYn" value="N"/>
        <jsp:param name="reqAuth" value="CNAT"/>
      </jsp:include>

      <div class="c-button-wrap u-mb--20">
        <button class="c-button c-button--full c-button--primary" id="btnUsimChg" style="display: none;">유심변경</button>
      </div>

      <b class="c-text c-text--type3 u-mb--16">
        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
      </b>
      <ul class="c-text-list c-bullet c-bullet--dot">
        <li class="c-text-list__item u-co-gray">유심 셀프변경 시 음성, 문자 등 휴대폰 사용이 중단됩니다. 신속히 새로운 유심을 단말기에 장착하여 1~3회 전원을 껐다 켜면 사용 가능합니다.</li>
        <li class="c-text-list__item u-co-gray">홈페이지 또는 편의점 등에서 kt M모바일 유심을 구매하신 고객님께서 이용가능하며, 유심변경 취소는 불가능합니다.</li>
        <li class="c-text-list__item u-co-gray">유심변경 시 기존 유심에 저장된 개인정보(연락처, 사진 등)는 자동으로 전송되지 않습니다. 미리 휴대폰으로 저장하셔야 합니다.</li>
      </ul>
    </div>
  </t:putAttribute>

</t:insertDefinition>