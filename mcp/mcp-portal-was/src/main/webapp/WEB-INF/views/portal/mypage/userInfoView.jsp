<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutMainDefault">
    <t:putAttribute name="scriptHeaderAttr">
         <script type="text/javascript" src="/resources/js/portal/mypage/userInfoView.js"></script>
         <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
         <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
         <script type="text/javascript" src="/resources/js/portal/popup/diAuth.js"></script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
         <div class="ly-content" id="main-content">
          <div class="ly-page--title">
            <h2 class="c-page--title">정보수정</h2>
          </div>
          <div class="c-row c-row--lg">
            <div class="c-form-wrap">
              <div class="c-form-field">
                <div class="c-form__input u-width--460 u-mt--0 has-value">
                  <input class="c-input" id="inpName" type="text" placeholder="고객명 입력" value="${userVO.mkNm }" readonly>
                  <input type="text" id="name" name="name" value="${userVO.mkNm}" hidden/>
                  <input type="text" id="userId" value="${userVO.mkId }" hidden/>
                  <label class="c-form__label" for="inpName">고객명</label>
                </div>
              </div>
              <div class="c-form-row c-col2">
                <div class="c-form-field">
                  <div class="c-form__input has-value">
                    <input class="c-input" id="inpA" type="text" value="${userVO.mkId }" readonly>
                    <label class="c-form__label" for="inpA">아이디</label>
                  </div>
                </div>
                <div class="c-form-field">
                  <div class="c-form__input has-value">
                    <input class="c-input" id="pw" type="password" placeholder="10~15자리 영문/숫자/특수기호 조합" value="password" readonly>
                    <label class="c-form__label" for="pw">비밀번호</label>
                    <button class="c-button c-button--sm c-button--underline" type="button" data-dialog-trigger="#pw_change-dialog" onclick="initPwChg()">비밀번호 변경</button>
                  </div>
                </div>
              </div>
              <div class="c-form-row c-col2">
                <div class="c-form-field">
                  <div class="c-form__input-group" id="mobileNo">
                    <div class="c-form__input c-form__input-division has-value">
                      <input class="c-input--div3" id="mobileNo1" name="mobileNo1" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리" value="${fn:substring(userVO.mobileNo, 0, 3) }">
                      <span>-</span>
                      <input class="c-input--div3" id="mobileNo2" name="mobileNo2" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 중간자리" value="${fn:substring(userVO.mobileNo, 3, 7) }">
                      <span>-</span>
                      <input class="c-input--div3" id="mobileNo3" name="mobileNo3" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 뒷자리" value="${fn:substring(userVO.mobileNo, 7, 11) }">
                      <label class="c-form__label" for="mobileNo">휴대폰</label>
                      <input type="hidden" id="isAuth"  value="-1" />
                    </div>
                  </div>
                </div>
                <div class="c-form-field">
                  <div class="c-form__input has-value">
                    <input class="c-input" id="email" type="text" name="email" placeholder="이메일을 입력해주세요." value="${userVO.email1}@${userVO.email2}">
                    <label class="c-form__label" for="email">이메일</label>
                  </div>
                </div>
              </div>

              <div id="isAuthComplete" style="display:none" aria-hidden="true">
                <c:if test="${checkKid eq 'Y' }">
                  <input type="hidden" id="isAuthAgent"  value="-1" />
                  <div class="c-form-wrap u-mt--32">
                    <div class="c-form-group" role="group" aria-labelledby="inputAddressHead">
                      <h3 class="c-group--head c-hidden" id="inputAddressHead">법정 대리인 정보</h3>
                      <!-- <div class="c-form-row c-col2"> -->
                      <div class="c-form u-mt--16">
                        <div class="c-form-field">
                          <div class="c-form__input">
                            <input class="c-input" name="minorAgentName" id="minorAgentName" type="text" placeholder="법정대리인 성명" aria-invalid="true" maxlength="20">
                            <label class="c-form__label" for="minorAgentName">법정대리인 성명</label>
                            <button class="c-button c-button--sm c-button--underline" id="btnAgentAut">휴대폰 인증</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <ul class="c-text-list c-bullet c-bullet--dot u-ta-left u-mt--16">
                    <li class="c-text-list__item u-co-gray">만 14세 미만 어린이는 법정대리인(부모님)의 동의절차를 거쳐 회원 정보 수정이 진행됩니다.</li>
                    <li class="c-text-list__item u-co-gray">부 혹은 모와 동일한 법정대리인의 휴대폰인증을 해야 합니다.</li>
                  </ul>
                </c:if>
              </div>
                <%--    테스트     --%>
              <form id="inputForm" name="inputForm" method="post">
                <input type="hidden" name="agree" id="TERMSMEM04" value2="personalInfoCollectAgree" value="${userVO.personalInfoCollectAgree}" />
                <input type="hidden" name="agree" id="TERMSMEM03" value2="clausePriAdFlag" value="${userVO.clausePriAdFlag}" />
                <input type="hidden" name="agree" id="TERMSMEM05" value2="othersTrnsAgree" value="${userVO.othersTrnsAgree}" />
                <input type="hidden" name="agree" id="emailRcvYn" value="${userVO.emailRcvYn}" />
                <input type="hidden" name="agree" id="smsRcvYn" value="${userVO.smsRcvYn}" />
                <input type="hidden" name="agree" id="targetTerms" value=""/>
              </form>
              <div class="c-accordion c-accordion--type1 u-mt--48" data-ui-accordion>
                <div class="c-accordion__box">
                  <div class="c-accordion__item">
                    <div class="c-accordion__head">
                      <!-- [2021-12-23] 마크업 순서 변경 및 접근성 텍스트 추가 시작 =====-->
                        <%--                                <button class="runtime-data-insert c-accordion__button" id="acc_headerA1" type="button" aria-expanded="false" aria-controls="acc_contentA1">--%>
                        <%--                                    <span class="c-hidden">이용약관 및 개인정보 수집/이용에 모두 동의합니다. 상세열기</span>--%>
                        <%--                                </button>--%>
                      <div class="c-accordion__check">
                        <input class="c-checkbox c-checkbox--type5" id="checkAll" type="checkbox">
                        <label class="c-label" for="checkAll">전체 동의</label>
                      </div>
                      <!-- [$2021-12-23] 마크업 순서 변경 및 접근성 텍스트 추가 끝 =====-->
                    </div>
                    <div class="c-accordion__panel expand expanded" id="acc_contentA1" aria-labelledby="acc_headerA1">
                      <div class="c-accordion__inside">
<%--                        <c:set var="termsMemList" value="${nmcp:getCodeList(Constants.TERMS_MEM_CD)}" />--%>
                        <c:set var="termsMemList" value="${nmcpList}" />
                        <c:forEach var="item" items="${termsMemList}" varStatus="status">
                          <c:if test="${'선택' eq item.expnsnStrVal1}">
                            <div class="c-accordion__sub-check">
                              <button class="c-button c-button--xsm" onclick="viewTerms('${item.dtlCd}', 'cdGroupId1=${Constants.TERMS_MEM_CD}&cdGroupId2=${item.dtlCd}');">
                                <span class="c-hidden">${item.dtlCdNm} 상세 팝업보기</span>
                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                              </button>
                              <input class="c-checkbox c-checkbox--type2" id="terms${status.index}" inheritance="${item.expnsnStrVal2}" name="terms" data-dtl-cd="${item.dtlCd}" type="checkbox"  href="javascript:void(0);" onclick="setChkbox(this)">
                              <label class="c-label" id="terms${status.index}label" for="terms${status.index}">${item.dtlCdNm}<span class="u-co-gray">(${item.expnsnStrVal1})</span></label>
                            </div>
                          </c:if>
                        </c:forEach>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="c-button-wrap u-mt--56">
              <a class="c-button c-button--lg c-button--gray u-width--220" href="#"  onclick="history.go(-1)">취소</a>
              <button type="button" class="c-button c-button--lg c-button--primary u-width--220" onclick="modifySubmit();">변경</button>
            </div>
          </div>
        </div>

        <div class="c-modal c-modal--md" id="pw_change-dialog" role="dialog" aria-labelledby="#modalPwChangeTitle">
    <div class="c-modal__dialog" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="modalPwChangeTitle">비밀번호 변경</h2>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <p class="c-text c-text--fs20 u-mb--32">사용하실 비밀번호를 입력해 주세요.</p>
          <div class="c-form-wrap">
            <div class="c-form-field">
              <div class="c-form__input">
                <input class="c-input" id="nowPw" type="password" placeholder="10~15자리 영문/숫자/특수기호 조합" maxlength="20">
                <label class="c-form__label" for="nowPw">현재 비밀번호</label>
              </div>
            </div>
            <div class="c-form-field">
              <!-- 에러 발생 시 .has-error(aria-invalid="true") validation 통과 시 .has-safe(aria-invalid="false") 클래스를 사용-->
              <div class="c-form__input">
                <input class="c-input" id="newPw" type="password" placeholder="10~15자리 영문/숫자/특수기호 조합" maxlength="15" aria-invalid="false" aria-describedby="inpMsgB" >
                <label class="c-form__label" for="newPw">새 비밀번호</label>
              </div>
              <p class="c-form__text" id="inpMsgB" style="display:none;">사용 가능한 비밀번호입니다.</p>
            </div>
            <div class="c-form-field">
              <div class="c-form__input">
                <input class="c-input" id="checkPw" type="password" placeholder="10~15자리 영문/숫자/특수기호 혼용" maxlength="15" aria-invalid="true" aria-describedby="inpMsgC">
                <label class="c-form__label" for="checkPw">새 비밀번호 확인</label>
              </div>
              <p class="c-form__text" id="inpMsgC" style="display:none;">비밀번호가 일치하지 않습니다.</p>
            </div>
          </div>
        </div>
        <div class="c-modal__footer">
          <div class="c-button-wrap">
            <button type="button" class="c-button c-button--lg c-button--gray u-width--220" id="pwChgBut" data-dialog-close>취소</button>
            <button type="button" class="c-button c-button--lg c-button--primary u-width--220" onclick="pwChange()">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div>
    </t:putAttribute>

</t:insertDefinition>