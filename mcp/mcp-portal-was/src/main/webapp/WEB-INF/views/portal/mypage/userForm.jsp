<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="layoutMainDefault">
    <t:putAttribute name="scriptHeaderAttr">
         <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/portal/mypage/userForm.js"></script>



    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
          <div class="ly-page--title">
            <h2 class="c-page--title">정보수정</h2>
          </div>
          <input type="hidden" id="status" value="${status}" />
          <div class="c-row c-row--lg">
            <h3 class="c-heading c-heading--fs20 u-mb--24">
             <c:choose>
                 <c:when test="${maskingSession eq 'Y'}">
                     <td>${userMasking.name}</td>
                 </c:when>
                 <c:otherwise>
                     <td>${userVO.mkNm}</td>
                 </c:otherwise>
             </c:choose>님의 정보</h3>
            <div class="c-table c-table--type2">
              <table>
                <caption>이름, 아이디, 휴대폰, 이메일, 주소, 마케팅정보 수신동의 항목이 있는 내 정보</caption>
                <colgroup>
                  <col style="width: 24%">
                  <col>
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">이름${pwChg}</th>
                    <c:choose>
                        <c:when test="${maskingSession eq 'Y'}">
                            <td>${userMasking.name}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${userVO.mkNm}</td>
                        </c:otherwise>
                    </c:choose>
                  </tr>
                  <tr>
                    <th scope="row">아이디</th>
                     <c:choose>
                        <c:when test="${maskingSession eq 'Y'}">
                            <td>${userMasking.userId}</td>
                        </c:when>
                        <c:otherwise>
                           <td>${userVO.mkId }</td>
                        </c:otherwise>
                    </c:choose>
                  </tr>
                  <tr>
                    <th scope="row">휴대폰</th>
                    <td>

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

                    <button class="c-button c-button--underline u-co-sub-4 u-ml--24" onclick="location.href='/mypage/multiPhoneLine.do'" type="button">${userVO.userDivision == '01' ? '다회선 인증' : '정회원 인증' }</button>

                    </td>
                  </tr>
                  <tr>
                    <th scope="row">이메일</th>
                    <c:choose>
                        <c:when test="${maskingSession eq 'Y'}">
                            <td>${userMasking.email}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${userVO.email }</td>
                        </c:otherwise>
                    </c:choose>
                  </tr>
                  <tr>
                    <td colspan="2">
                      <div>
                        <label class="c-label">고객 혜택 이용 동의(선택)</label>
                        <input class="c-checkbox c-checkbox--type2" id="personalInfoCollectAgree" name="personalInfoCollectAgree" value="${userVO.personalInfoCollectAgree}"
                               <c:if test="${'Y' eq userVO.personalInfoCollectAgree}">checked="checked"</c:if> type="checkbox" data-mand-yn="" onclick="return false" />
                        <label class="c-label" for="personalInfoCollectAgree">고객 혜택 제공을 위한 개인정보 수집 및 이용 동의 <span class="u-co-gray">${userVO.personalInfoCollectAgree == 'Y' ? '수신/동의일자' : '수신거부/거부일자'}(${empty userVO.personAgreeTime ? '-' : userVO.personAgreeTime})
                          </span>
                        </label><br>

                        <input class="c-checkbox c-checkbox--type2" id="clausePriAdFlag" name="clausePriAdFlag" value="${userVO.clausePriAdFlag}" type="checkbox"
                               <c:if test="${'Y' eq userVO.clausePriAdFlag}">checked="checked"</c:if> data-mand-yn="" onclick="return false" />
                        <label class="c-label" for="clausePriAdFlag">정보/광고 수신 동의  <span class="u-co-gray">${userVO.clausePriAdFlag == 'Y' ? '수신/동의일자' : '수신거부/거부일자'}(${empty userVO.clauerPriTiem ? '-' : userVO.clauerPriTiem})
                          </span>
                        </label><br>

                        <input class="c-checkbox c-checkbox--type2" id="othersTrnsAgree" name="othersTrnsAgree" value="${userVO.othersTrnsAgree}"
                               <c:if test="${'Y' eq userVO.othersTrnsAgree}">checked="checked"</c:if> type="checkbox" data-mand-yn="" onclick="return false" />
                        <label class="c-label" for="othersTrnsAgree">제 3자 제공 동의  <span class="u-co-gray">${userVO.othersTrnsAgree == 'Y' ? '수신/동의일자' : '수신거부/거부일자'}(${empty userVO.othersTime ? '-' : userVO.othersTime})
                          </span>
                        </label><br>
                      </div>
                    </td>
                  </tr>


<%--                  <tr>--%>
<%--                    <th scope="row">수신동의</th>--%>

<%--                    <td>--%>
<%--                      <div>--%>
<%--                        <label >이용약관 및 개인정보 수집/이용에 모두 동의합니다.</label>--%>
<%--                        <label class="c-label" for="terms${status.index}">--%>
<%--                      </div>--%>
<%--                      <ul class="c-text-list c-text-list --row">--%>

<%--&lt;%&ndash;                        <li class="c-text-list__item">이메일<span class="c-text-list__sub"> ${userVO.emailRcvYn == 'Y' ? '수신/동의일자' : '수신거부/거부일자'} (${empty userVO.emailRcvHist ? '-' : userVO.emailRcvHist})</span>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        </li>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <li class="c-text-list__item">SMS<span class="c-text-list__sub"> ${userVO.smsRcvYn == 'Y' ? '수신/동의일자' : '수신거부/거부일자'}(${empty userVO.smsRcvHist ? '-' : userVO.smsRcvHist})</span>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        </li>&ndash;%&gt;--%>
<%--                        &lt;%&ndash;  여기 수정 시작&ndash;%&gt;--%>
<%--                        <li class="c-text-list__item">고객 혜택 제공을 위한 개인정보 수집 및 이동 동의 (선택)<span class="c-text-list__sub"> ${userVO.personalInfoCollectAgree == 'Y' ? '수신/동의일자' : '수신거부/거부일자'} (${empty userVO.personAgreeTime ? '-' : userVO.personAgreeTime})</span>--%>
<%--                        </li>--%>
<%--                        <li class="c-text-list__item">제 3자 제공 동의 (선택)<span class="c-text-list__sub"> ${userVO.othersTrnsAgree == 'Y' ? '수신/동의일자' : '수신거부/거부일자'}(${empty userVO.othersTime ? '-' : userVO.othersTime})</span>--%>
<%--                        </li>--%>
<%--                        <li class="c-text-list__item">정보/광고 수신동의 (선택)<span class="c-text-list__sub"> ${userVO.clausePriAdFlag == 'Y' ? '수신/동의일자' : '수신거부/거부일자'}(${empty userVO.clauerPriTiem ? '-' : userVO.clauerPriTiem})</span>--%>
<%--                        </li>--%>
<%--                      </ul>--%>
<%--                    </td>--%>
<%--                  </tr>--%>
                </tbody>
              </table>
            </div>
            <div class="c-button-wrap u-mt--56">
              <a class="c-button c-button--lg c-button--gray u-width--220" href="#" id="snsBut" data-dialog-trigger="#pw_confirm-dialog" href="javascript:void(0);" onclick="btnCheck('sns');">소셜로그인 관리</a>
              <a class="c-button c-button--lg c-button--primary u-width--220" href="#" id="modifyBut" data-dialog-trigger="#pw_confirm-dialog" href="javascript:void(0);" onclick="btnCheck('modify');">정보수정</a>
            </div>
            <div class="c-button-wrap u-mt--40">
              <a class="c-button c-button--underline" href="/mypage/recForm.do">회원탈퇴</a>
            </div>
          </div>
        </div>
        <div class="c-modal c-modal--md" id="pw_confirm-dialog" role="dialog" aria-labelledby="#modalPassword">
            <div class="c-modal__dialog" role="document">
              <div class="c-modal__content">
                <div class="c-modal__header">
                  <h2 class="c-modal__title" id="pw_confirm-title">비밀번호 확인</h2>
                  <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
                  <input type="hidden" name="btnCheck" id="btnCheck" />
                </div>
                <div class="c-modal__body">
                  <p class="c-text c-text--fs20 u-mb--32">정보를 안전하게 보호하기 위해
                    <br>비밀번호를 다시 한 번 입력해주세요.
                  </p>
                  <div class="c-form-wrap">
                    <div class="c-form-field">
                      <div class="c-form__input has-value">
                        <c:choose>
                            <c:when test="${maskingSession eq 'Y'}">
                               <input class="c-input" id="inpA" type="text" value="${userMasking.userId}" readonly>
                            </c:when>
                            <c:otherwise>
                               <input class="c-input" id="inpA" type="text" value="${userVO.mkId}" readonly>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" id="userId" value="${userVO.mkId }" />
                        <label class="c-form__label" for="inpA">아이디</label>
                      </div>
                    </div>
                    <div class="c-form-field">
                      <div class="c-form__input">
                        <input class="c-input" id="inpB" type="password" placeholder="영문,숫자,특수문자 3가지 종류를 모두 조합하여 10~15자리" maxlength="20">
                        <label class="c-form__label" for="inpB">비밀번호</label>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="c-modal__footer">
                  <div class="c-button-wrap">
                    <button class="c-button c-button--lg c-button--gray u-width--220" id="pw_modal-cancelBut" data-dialog-close>취소</button>
                    <button class="c-button c-button--lg c-button--primary u-width--220" id="pw_modal-okBut" onclick="pwCheck();">확인</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
    </t:putAttribute>

</t:insertDefinition>