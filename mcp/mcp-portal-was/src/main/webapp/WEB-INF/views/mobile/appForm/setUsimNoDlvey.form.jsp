<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<t:insertDefinition name="mlayoutDefault">
<t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/mobile/popup/niceAuth.js?version=24.05.21"></script>
    <script type="text/javascript" src="/resources/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
    <script type="text/javascript" src="/resources/js/naver.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?version=21.09.30"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js?version=20.07.07"></script>
    <script type="text/javascript" src="/resources/js/mobile/appForm/setUsimNoDlvey.form.js?version=23.11.17"></script>
</t:putAttribute>

<t:putAttribute name="contentAttr">

      <input type="hidden" id="isAuth" name="isAuth"  >
      <input type="hidden" id="clientId" name="clientId" value="<spring:eval expression="@propertiesForJsp['NAVER_CLIENT_ID_NEW']" />"  >
      <input type="hidden" id="serviceUrl" name="serviceUrl" value="${nmcp:getServerInfo()}"  >
      <input type="hidden" id="pcCallbackUrl" name="pcCallbackUrl" value="${nmcp:getServerInfo()}<spring:eval expression="@propertiesForJsp['NEARO_MOBILE_CALLBACK_URL']" />"  >
      <input type="hidden" id="resNo" value="${appformReqDto.resNo}" />
      <input type="hidden" id="cntpntShopId" value="${appformReqDto.cntpntShopId}" />
      <input type="hidden" id="socCode" value="${appformReqDto.socCode}" />

      <div id="naver_id_login" class="hidden" style="display:none;"></div>

      <div class="ly-content" id="main-content">


          <div class="ly-page-sticky">

               <h2 class="ly-page__head">유심번호 등록<button class="header-clone__gnb"></button></h2>
              <div class="c-indicator">
                  <span id="spIndicator" style="width: 33%"></span>
              </div>
          </div>

          <!-- STEP1 START -->
          <div id="step1" class="_divStep">

            <h3 class="c-heading c-heading--type5">실명 및 본인인증</h3>
            <hr class="c-hr c-hr--type2">
            <div class="c-form">
              <div class="c-form__input ">
                <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력"   maxlength="20">
                <label class="c-form__label" for="cstmrName">이름</label>
              </div>
            </div>

            <div class="c-form-field _isDefault" >
              <div class="c-form__input-group">
                <div class="c-form__input c-form__input-division">
                    <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6"  placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');">
                    <span>-</span>
                    <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="off" type="password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                    <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                </div>
              </div>
            </div>

            <!-- START 본인인증 방법 : 모바일이 있는 특이케이스(신용카드, 네이버, 모바일) -->
            <div class="c-form">
              <span class="c-form__title--type2" id="spOnlineAuthType">본인인증 방법 선택</span>
              <div class="c-check-wrap c-check-wrap--column">
                  <div class="c-chk-wrap">
                      <input class="c-radio c-radio--button c-radio--button--icon" id="onlineAuthType1" value="C" type="radio" name="onlineAuthType">
                      <label class="c-label u-ta-left" for="onlineAuthType1">
                          <i class="c-icon c-icon--card" aria-hidden="true"></i>신용카드
                      </label>
                  </div>
                  <div class="c-chk-wrap">
                      <input class="c-radio c-radio--button c-radio--button--icon" id="onlineAuthType2" value="N" type="radio" name="onlineAuthType">
                      <label class="c-label u-ta-left" for="onlineAuthType2">
                          <i class="c-icon c-icon--card" aria-hidden="true"></i>네이버 인증
                      </label>
                  </div>
                  <div class="c-chk-wrap">
                      <input class="c-radio c-radio--button c-radio--button--icon" id="onlineAuthType3" value="M" type="radio" name="onlineAuthType">
                      <label class="c-label u-ta-left" for="onlineAuthType3">
                          <i class="c-icon c-icon--card" aria-hidden="true"></i>SMS 본인인증
                      </label>
                  </div>
              </div>
            </div>

            <div class="c-form _onlineAuthType" data-value="C" style="display:none">
              <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                  <li class='c-text-list__item'>고객님의 본인명의(미성년자의 경우 법정 대리인)의 국내발행 신용카드 정보를 입력해주세요.(체크카드 인증불가)</li>
                  <li class='c-text-list__item'>신용카드 비밀번호 3회 이상 오류 시 해당카드로 인증을 받으실 수 없으니 유의하시기 바랍니다.</li>
              </ul>
              <div class="c-button-wrap u-mt--40">
                  <button id="btnNiceAuthC" class="c-button c-button--full c-button--h48 c-button--mint _btnNiceAuth">신용카드 인증</button>
              </div>
            </div>
            <div class="c-form _onlineAuthType" data-value="N" style="display:none">
              <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                  <li class='c-text-list__item'>네이버 앱에 인증을 진행하는 사용자의 ID가 로그인되어 있어야 인증 진행이 가능합니다.</li>
                  <li class='c-text-list__item'>네이버인증서는 발급 과정에서 본인명의의 휴대폰인증이 필요하며, 아이폰5S/갤럭시S5, Note4 이상에서 지원됩니다.(iOS10, 안드로이드6 이상)</li>
                  <li class='c-text-list__item'>네이버 인증이 정상적으로 진행되지 않으실 경우 네이버인증서를 재발급 받으신 후 이용이 가능합니다.</li>
              </ul>
              <div class="c-button-wrap u-mt--40">
                  <button id="btnNiceAuthN" class="c-button c-button--full c-button--h48 c-button--mint _btnNiceAuth">네이버 인증</button>
              </div>
            </div>
            <div class="c-form _onlineAuthType" data-value="M" style="display:none">
              <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                  <li class='c-text-list__item'>본인인증 발급 과정에서 본인명의의 휴대폰인증이 필요합니다.</li>
                  <li class='c-text-list__item'>전기통신사업법 제 84조 2항/제 32조 3항에 의거하여 번호변작 또는 스팸발송 시 핸드폰 정지 또는 해지될 수 있습니다.</li>
              </ul>
              <div class="c-button-wrap u-mt--40">
                  <button id="btnNiceAuthM" class="c-button c-button--full c-button--h48 c-button--mint _btnNiceAuth">SMS 인증</button>
              </div>
            </div>
            <!-- END 본인인증 방법 : 모바일이 있는 특이케이스(신용카드, 네이버, 모바일) -->
          </div>
          <!-- STEP1 END -->

          <!-- STEP2 START -->
          <div id="step2" style="display:none" class="_divStep" >
            <h3 class="c-heading c-heading--type5">유심정보 입력</h3>
            <hr class="c-hr c-hr--type2">

             <div class="c-form">
                <span class="c-form__title u-mt--0">주문번호</span>
                <select id="selResNo" class="c-select">
                  <option value="">신청서 주문번호</option>
                </select>

            </div>
            <div class="c-form">
              <div class="c-form__input ">
                <input class="c-input onlyNum" id="reqUsimSn" type="tel" maxlength="19" pattern="[0-9]*" placeholder="직접입력('-'없이 입력)" title="유심번호 입력" >
                <label class="c-form__label" for="reqUsimSn">유심번호 19자리</label>
              </div>
              <ul class="c-text-list c-bullet c-bullet--dot" id="ulOnOffLineUsimDesc">
                <li class="c-text-list__item u-co-gray">고객님께서는 유심번호 19자리를 입력해 주세요</li>
              </ul>

            </div>


          </div>
          <!-- STEP2 END -->

          <!-- STEP3 START -->
          <div id="step3" style="display:none" class="_divStep" >
            <h3 class="c-heading c-heading--type5">개통신청 완료</h3>
            <hr class="c-hr c-hr--type2">

             <div class="complete">
                 <div class="c-icon c-icon--complete" aria-hidden="true">
                     <span></span>
                 </div>
                 <p class="complete__heading">
                                                 개통신청이 완료 되었습니다!
                 </p>
                 <p class="complete__text">kt M모바일과 함께해 주셔서 감사합니다.
                  </p>
             </div>



          </div>
          <!-- STEP3 END -->

          <!-- STEP4 START -->
          <div id="step4" style="display:none" class="_divStep" >
            <h3 class="c-heading c-heading--type5">확인</h3>
            <hr class="c-hr c-hr--type2">

             <div class="complete">
                 <div class="c-icon c-icon--complete" aria-hidden="true">
                     <span></span>
                 </div>
                 <p class="complete__heading">
                   <c:choose>
                     <c:when test="${appformReqDto.cntpntShopId ne null and !empty appformReqDto.cntpntShopId }">
                       <span id="endMsg"></span>
                     </c:when>
                     <c:otherwise>
                       소지하신 유심을 셀프개통이나 가입신청 통해 개통 신청 부탁드립니다.
                     </c:otherwise>
                   </c:choose>
                 </p>
                 <p class="complete__text">
                   접수가 어려우신 분은 개통실로 연락부탁드립니다.
                 </p>
                 <p class="complete__text">
                                                 개통실 : 1899-0034
                 </p>
                 <p class="complete__text">kt M모바일과 함께해 주셔서 감사합니다.
                  </p>
             </div>
             <div class="c-button-wrap">
               <c:choose>
                 <c:when test="${appformReqDto.cntpntShopId ne null and !empty appformReqDto.cntpntShopId }">
                   <a class="c-button c-button--full c-button--gray" href="/m/appForm/appJehuFormDesignView.do?a=${appformReqDto.cntpntShopId}<c:if test="${appformReqDto.socCode ne null and !empty appformReqDto.socCode}">&r=${appformReqDto.socCode}</c:if>">가입하기</a>
                 </c:when>
                 <c:otherwise>
                   <a class="c-button c-button--full c-button--gray" href="/m/appForm/appSimpleInfo.do">셀프개통하기</a>
                   <a class="c-button c-button--full c-button--primary" href="/m/appForm/appCounselorInfo.do">가입신청</a>
                 </c:otherwise>
               </c:choose>
             </div>


          </div>
          <!-- STEP4 END -->

          <div id="divButtonStep" class="c-button-wrap" style="display:none" >
            <a id="btnNext" class="c-button c-button--full c-button--primary" >다음</a>
          </div>



    </div>



</t:putAttribute>

<t:putAttribute name="popLayerAttr">






</t:putAttribute>



</t:insertDefinition>