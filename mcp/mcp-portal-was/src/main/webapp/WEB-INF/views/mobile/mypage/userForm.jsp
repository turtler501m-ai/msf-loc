<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un"
    uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<un:useConstants var="Constants"
    className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript"
            src="/resources/js/mobile/mypage/userForm.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">

        <%-- <c:if test="${pwChg != '1'}">	<!--  비밀번호변경후 3개월 경과 팝업에서 왔을경우 비밀번호확인 팝업 없음  -->
            fn_tabMode('edit_depth',0);
        </c:if>
        <c:if test="${pwChg == '1'}">	<!--  비밀번호변경후 3개월 경과 팝업에서 왔을경우 비밀번호 변경 팝업 바로 띄움  -->
            //fn_layerPop('layer1',300,300);
            fn_mobile_layerOpen('newlayer1');
        </c:if>
        <c:if test="${pwChg == '3'}">	 비밀번호 다시 한번 입력 페이지 인증후 들어왔을때
            fn_tabMode('edit_depth',1);
            <c:set scope="session" var="PW_CHECK_YN" value="" />
        </c:if> --%>

    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">정보수정<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <input type="hidden" id="status" value="${status}" />
      <div class="c-form">
        <div class="c-form__group" role="group" aria-labelledby="inpG">
          <span class="c-form__title">
          <c:choose>
                 <c:when test="${maskingSession eq 'Y'}">
                     <td>${userMasking.name}</td>
                 </c:when>
                 <c:otherwise>
                     <td>${userVO.mkNm}</td>
                 </c:otherwise>
             </c:choose>님의 정보
          </span>
          <div class="info-box">
            <dl>
              <dt>이름</dt>
              <c:choose>
                  <c:when test="${maskingSession eq 'Y'}">
                      <dd>${userMasking.name}</dd>
                  </c:when>
                  <c:otherwise>
                     <dd>${userVO.mkNm}</dd>
                  </c:otherwise>
              </c:choose>
            </dl>
            <dl>
              <dt class="c-flex">아이디</dt>
               <c:choose>
                   <c:when test="${maskingSession eq 'Y'}">
                       <dd>${userMasking.userId}</dd>
                   </c:when>
                   <c:otherwise>
                      <dd>${userVO.mkId}</dd>
                   </c:otherwise>
               </c:choose>
            </dl>
            <dl>
              <dt class="c-flex">
                  <span class="u-mr--8">휴대폰</span>
                <a class="c-button c-button--sm u-co-mint c-button--underline certifyUser">${userVO.userDivision == '01' ? '다회선 인증' : '정회원 인증'  }</a>
              </dt>
              <dd>
                 <c:choose>
                        <c:when test="${maskingSession eq 'Y'}">
                            <c:if test="${fn:length(userVO.mobileNo) > 10}">
                                ${fn:substring(userVO.mobileNo,0,3)} - ${fn:substring(userVO.mobileNo,3,7)} - ${fn:substring(userVO.mobileNo,7,11)}
                            </c:if>
                            <c:if test="${fn:length(userVO.mobileNo) == 10}">
                                ${fn:substring(userVO.mobileNo,0,3)} - ${fn:substring(userVO.mobileNo,3,6)} - ${fn:substring(userVO.mobileNo,6,10)}
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${fn:length(userVO.mkMobileNo) > 10}">
                                ${fn:substring(userVO.mkMobileNo,0,3)} - ${fn:substring(userVO.mkMobileNo,3,7)} - ${fn:substring(userVO.mkMobileNo,7,11)}
                            </c:if>
                            <c:if test="${fn:length(userVO.mkMobileNo) == 10}">
                                ${fn:substring(userVO.mkMobileNo,0,3)} - ${fn:substring(userVO.mkMobileNo,3,6)} - ${fn:substring(userVO.mkMobileNo,6,10)}
                            </c:if>
                        </c:otherwise>
                   </c:choose>
              </dd>
            </dl>
            <dl>
              <dt>이메일</dt>
               <c:choose>
                   <c:when test="${maskingSession eq 'Y'}">
                       <dd>${userMasking.email}</dd>
                   </c:when>
                   <c:otherwise>
                       <dd>${userVO.email}</dd>
                   </c:otherwise>
               </c:choose>
            </dl>
            <dl>
              <dt>
                  고객 혜택 이용 동의(선택)
              </dt>
              <dd></dd>
            </dl>
            <dl>
              <dt>
                  <input class="c-checkbox c-checkbox--type2" id="personalInfoCollectAgree" name="personalInfoCollectAgree" value="${userVO.personalInfoCollectAgree}" <c:if test="${'Y' eq userVO.personalInfoCollectAgree}">checked="checked"</c:if> type="checkbox" data-mand-yn="" onclick="return false" />
                  <label class="c-label" for="personalInfoCollectAgree">고객 혜택 제공을 위한 개인정보 수집 및 이용 동의<br/>
                      <span class="u-co-gray">${userVO.personalInfoCollectAgree == 'Y' ? '수신/동의일자' : '수신거부/거부일자'}(${empty userVO.personAgreeTime ? '-' : userVO.personAgreeTime})</span>
                  </label><br/>
                  <input class="c-checkbox c-checkbox--type2" id="clausePriAdFlag" name="clausePriAdFlag" value="${userVO.clausePriAdFlag}" type="checkbox" <c:if test="${'Y' eq userVO.clausePriAdFlag}">checked="checked"</c:if> data-mand-yn="" onclick="return false" />
                  <label class="c-label" for="clausePriAdFlag">정보/광고 수신 동의 <br/>
                      <span class="u-co-gray">${userVO.clausePriAdFlag == 'Y' ? '수신/동의일자' : '수신거부/거부일자'}(${empty userVO.clauerPriTiem ? '-' : userVO.clauerPriTiem})</span>
                  </label><br/>
                  <input class="c-checkbox c-checkbox--type2" id="othersTrnsAgree" name="othersTrnsAgree" value="${userVO.othersTrnsAgree}" <c:if test="${'Y' eq userVO.othersTrnsAgree}">checked="checked"</c:if> type="checkbox" data-mand-yn="" onclick="return false" />
                  <label class="c-label" for="othersTrnsAgree">제 3자 제공 동의<br/>
                      <span class="u-co-gray">${userVO.othersTrnsAgree == 'Y' ? '수신/동의일자' : '수신거부/거부일자'}(${empty userVO.othersTime ? '-' : userVO.othersTime})</span>
                  </label>
              </dt>
              <dd></dd>
            </dl>
          </div>
          <div class="c-button-wrap c-button-wrap--column">
            <button class="c-button c-button--lg c-button--mint--type2" data-dialog-trigger="#pw_confirm-dialog" onclick="btnCheck('sns');">소셜로그인 관리</button>
            <button class="c-button c-button--lg c-button--mint" data-dialog-trigger="#pw_confirm-dialog" onclick="btnCheck('modify');">정보 수정</button>
          </div>
          <div class="c-button-wrap u-mt--28">
              <a class="c-button c-button--underline" href="/m/mypage/recForm.do">회원탈퇴</a>
          </div>
        </div>
      </div>
    </div>
  <div class="c-modal" id="pw_confirm-dialog" role="dialog" tabindex="-1" aria-describedby="#pw_confirm-title">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="pw_confirm-title">비밀번호 확인</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
          <input type="hidden" name="btnCheck" id="btnCheck" />
        </div>
        <div class="c-modal__body">
          <p class="c-text c-text--type2 u-co-gray u-mt--24">정보를 안전하게 보호하기 위해 비밀번호를 다시한번 입력해주세요.</p>
          <div class="c-form u-mt--32">
            <div class="c-form__input">
              <c:choose>
                   <c:when test="${maskingSession eq 'Y'}">
                      <input class="c-input" id="inpA" type="text" value="${userMasking.userId}" readonly>
                   </c:when>
                   <c:otherwise>
                      <input class="c-input" id="inpA" type="text" value="${userVO.mkId }" readonly>
                   </c:otherwise>
               </c:choose>
              <input type="hidden" id="userId" value="${userVO.mkId }" />
              <label class="c-form__label" for="inpA">아이디</label>
            </div>
            <div class="c-form__input">
              <input class="c-input" id="inpB" type="password" name="" placeholder="10~15자리 영문/숫자/특수기호 조합" maxlength="20">
              <label class="c-form__label" for="inpB">비밀번호</label>
            </div>
          </div>
          <div class="c-button-wrap u-mt--48">
            <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
            <button class="c-button c-button--full c-button--primary"  onclick="pwCheck();">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div><!-- [START]-->





    </t:putAttribute>
</t:insertDefinition>