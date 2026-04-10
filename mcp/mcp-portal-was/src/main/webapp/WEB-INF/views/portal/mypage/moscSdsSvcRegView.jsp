<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">요금할인 재약정 신청</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
    <script type="text/javascript" src="/resources/js/portal/mypage/moscSdsSvcRegView.js"></script>
    <script type="text/javascript">
    history.pushState(null, null,"/mypage/reSpnsrPlcyDc.do");
    window.onpopstate = function (event){
        history.go("/mypage/reSpnsrPlcyDc.do");
    }
    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <form id="frm" name="frm" method="post">
        <input type="hidden" name="hPhoneNum" id="hPhoneNum" value="${param.phoneNum}">
           <input type="hidden" name="engtPerd" id="engtPerd" value="${param.engtPerd}">
           <input type="hidden" name="ctn" id="ctn" value="${param.ctn}">
           <input type="hidden" name="ncn" id="ncn" value="${param.svcCntrNo}">
    </form>
    <div class="ly-content" id="main-content">

      <div class="ly-page--title">
        <h2 class="c-page--title">요금할인 재약정 신청</h2>
      </div>
      <div class="c-row c-row--xlg">
        <div class="c-row c-row--lg presents-wrap">
          <h3 class="c-row c-row--lg c-text c-text-fs-16 u-fw-meidum u-mt--48">선택 가능 사은품<span class="u-co-gray u-fs-14 u-ml--4"></span>
          </h3>
          <button class="c-button presents-wrap__button presents-wrap__button--prev" type="button" tabindex="-1" aria-hidden="true" disabled="true">
            <div class="c-hidden">이전</div>
          </button>
          <div class="presents-row">
            <div class="c-checktype-row">
              <div class="c-chk-wrap">
                <input class="c-checkbox c-checkbox--button c-checkbox--button--type4" id="chkBS1" type="checkbox" name="chkSelectBasicService" href="javascript:void(0);" onclick="chgChk(this);">
                <label class="c-label" for="chkBS1">
                  <!-- 선택 안 함일 경우, 클래스 .no-select 추가-->
                  <span class="no-select"></span>선택 안 함
                </label>
              </div>
              <c:forEach items="${presentList}" var="item" varStatus="eachIndex">
                <div class="c-chk-wrap">
                  <input class="c-checkbox c-checkbox--button c-checkbox--button--type4" data-isdlvry="${item.expnsnStrVal2}" id="chkBS<c:out value="${eachIndex.count +1}"></c:out>"  value="${item.dtlCd}" type="checkbox" name="chkSelectBasicService" onclick="chgChk(this);">
                  <label class="c-label" for="chkBS<c:out value="${eachIndex.count +1}"></c:out>">
                    <span class="img-prefix">
                      <img src='<c:out value="${item.expnsnStrVal1}"></c:out>' alt="">
                    </span><c:out value="${item.dtlCdNm}"></c:out>
                  </label>
                </div>
              </c:forEach>
            </div>
          </div>
          <button class="c-button presents-wrap__button presents-wrap__button--next" type="button" tabindex="-1" aria-hidden="true">
            <div class="c-hidden">다음</div>
          </button>
        </div>
        <div class="c-row c-row--lg">
          <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item u-co-gray">사은품 목록은 가입 조건에 따라 변동될 수 있습니다.</li>
          </ul>
        </div>
      </div>
      <div class="c-row c-row--lg">

        <div class="c-form-wrap u-mt--48" id="divInpCsArea" style="display: none;">
          <div class="c-form-group" role="group" aira-labelledby="inpCsInfoHead">
            <h3 class="c-group--head" id="inpCsInfoHead">주문자 정보</h3>
            <div class="c-form-row c-col2">
              <div class="c-form-field">
                <div class="c-form__input u-mt--0">
                  <input class="c-input" id="inpOrdererName" maxlength="20" type="text" placeholder="이름 입력" aria-invalid="true">
                  <label class="c-form__label" for="inpOrdererName">이름</label>
                </div>
              </div>
              <div class="c-form-field">
                <div class="c-form__input-group u-mt--0">
                  <div class="c-form__input c-form__input-division">
                    <input class="c-input--div3" id="inpOrdererTel1" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 첫자리" onkeyup="phoneNumChk();">
                    <span>-</span>
                    <input class="c-input--div3" id="inpOrdererTel2" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰 중간자리" onkeyup="phoneNumChk();">
                    <span>-</span>
                    <input class="c-input--div3" id="inpOrdererTel3" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰 뒷자리" onkeyup="phoneNumChk();">
                    <label class="c-form__label" for="inpRecieverTel">휴대폰</label>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="c-form-group u-mt--48" role="group" aira-labelledby="inpDelAddrHead">
            <h3 class="c-group--head" id="inpDelAddrHead">배송주소</h3>
            <div class="c-form-row c-col2">
              <div class="c-form">
                <div class="c-input-wrap u-mt--0">
                  <label class="c-form__label c-hidden" for="inpAdressNum">우편번호</label>
                  <input class="c-input" id="inpAdressNum" type="text" placeholder="우편번호" readonly="">
                  <button type="button" class="c-button c-button--sm c-button--underline"  onclick="openPage('pullPopup', '/addPopup.do','','1');">우편번호찾기</button>
                </div>
              </div>
              <div class="c-form">
                <div class="c-input-wrap u-mt--0">
                  <label class="c-form__label c-hidden" for="inpAdress1">주소</label>
                  <input class="c-input" id="inpAdress1" type="text" placeholder="주소" readonly="">
                </div>
              </div>
            </div>
            <div class="c-form u-mt--16">
              <label class="c-label c-hidden" for="inpAddress2">상세주소</label>
              <input class="c-input" id="inpAddress2" type="text" placeholder="상세주소" name="inpAddress2" readonly="">
            </div>
          </div>
          <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
            <li class="c-text-list__item u-co-gray">직접 배송이 필요한 사은품 선택시에만 배송주소 입력이 가능합니다.</li>
            <li class="c-text-list__item u-co-gray">잘못된 배송 주소 입력 시 사은품의 재발송 및 반품이 불가하오니 반드시 배송주소를 확인/변경 해 주시기 바랍니다.</li>
            <li class="c-text-list__item u-co-gray">사은품은 익월 말까지 회선 사용 고객에 한해 제공됩니다.</li>
          </ul>
        </div>

        <div class="c-form-wrap u-mt--40">
          <h3 class="c-form__title">요금할인 재약정 동의 및 본인확인 절차 동의</h3>
          <div class="c-agree c-agree--type2">
            <div class="c-agree__item">
              <input class="c-checkbox" id="chkStepAgreeAll" type="checkbox" name="chkStepAgree" href="javascript:void(0);" onclick="agreeChk(this);">
              <label class="c-label" for="chkStepAgreeAll">재약정 및 본인확인 절차 동의</label>
            </div>
            <div class="c-agree__inside">
              <div class="c-chk-wrap">
                <input class="c-checkbox c-checkbox--type2" id="chkStepAgree1" type="checkbox" name="chkStepAgree2" href="javascript:void(0);" onclick="agreeChk(this);">
                <label class="c-label" for="chkStepAgree1">요금할인 재약정을 위한 필수 확인사항</label>
                <button type="button" class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMPLCY&cdGroupId2=TERMPLCY01');">
                  <span class="c-hidden">상세보기(새창)</span>
                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
        <div class="c-button-wrap u-mt--56">
          <a class="c-button c-button--lg c-button--gray u-width--220" href="/mypage/reSpnsrPlcyDc.do">취소</a>
          <button type="button" class="c-button c-button--lg c-button--primary u-width--220" data-dialog-trigger="#certi-sms-modal2" disabled="disabled" onclick="authSms();">SMS 본인인증</button>
        </div>
      </div>
    </div>
</t:putAttribute>
</t:insertDefinition>
