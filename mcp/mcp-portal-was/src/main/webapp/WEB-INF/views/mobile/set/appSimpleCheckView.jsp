<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<c:set var="loginType" value="${loginType}" />

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="contentAttr">

    <form id="checkForm" name="checkForm" method="POST">
    <div class="ly-content" id="main-content">
<c:if test = "${loginType eq 'bio' }">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">생체인증 설정<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <p class="c-text c-text--type2 u-co-gray">생체인증 설정을 위해
        <br>로그인 아이디와 비밀번호를 입력해주세요.
      </p>
</c:if>
<c:if test = "${loginType eq 'simp' }">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">간편비밀번호 설정<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <p class="c-text c-text--type2 u-co-gray">간편비밀번호 설정을 위해
        <br>로그인 아이디와 비밀번호를 입력해주세요.
      </p>
</c:if>
      <div class="c-form">
        <div class="c-form__input has-value">
          <input class="c-input" id="userMaskedId" name="userMaskedId" type="text" value="${nmcp:getMaskedId(USER_SESSION.userId)}" readonly>
          <input id="userId" name="userId" type="hidden" value="${USER_SESSION.userId}" >
          <label class="c-form__label" for="userId">아이디</label>
        </div>
      </div>
      <div class="c-form">
        <div class="c-form__input has-value">
          <input class="c-input" id="passWord" name="passWord" type="password" placeholder="10~15자리 영문/숫자/특수기호 조합" aria-invalid="true" aria-describedby="" onkeyup="nextCheck();" maxlength="20">
          <label class="c-form__label" for="passWord">비밀번호</label>
        </div>
      </div>
      <div class="c-button-wrap">
        <a class="c-button c-button--full c-button--primary is-disabled" id="loginBtn" href="javascript:goSimpleCheck();">확인</a>
      </div>
    </div>
    </form>

    </t:putAttribute>

      <t:putAttribute name="scriptHeaderAttr">
          <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/login/loginForm.js"></script>
          <script>

          var nextCheck = function (){
              if($.trim($('#passWord').val()) != ''){
                  $('#loginBtn').removeClass('is-disabled');
              }else{
                  $('#loginBtn').addClass('is-disabled');
              }
          }

        // 인증 체크
        function goSimpleCheck() {

            if($('#passWord').val().length == 0) {
                MCP.alert('비밀번호를 입력해 주세요.', function (){
                    $('#passWord').focus();
                });
                return;
            }

            ajaxCommon.getItem({
                id:'appSimpleCheckAjax'
                ,cache:false
                ,url:'/m/set/appSimpleCheckAjax.do'
                ,data: $('#checkForm').serialize()
                ,dataType:"json"
            }
            ,function(data){
                if( data.CODE == "0000" ){
                    //MCP.alert("인증 성공");
<c:if test = "${loginType eq 'bio' }">
                    reSetBioLogin();
                    //bioSettingResult("Y");
</c:if>
<c:if test = "${loginType eq 'simp' }">
                    resetSimpleLogin();
                    //simpleSettingResult("Y");
</c:if>
                } else {
                    MCP.alert(data.ERRORDESC);
                    $("#passWord").val("");
                    return false;
                }
            });

        }

          $(document).ready(function(){
              $(document).on("keydown", "#passWord", function(e) {
                  if ($.trim($('#passWord').val()).length > 0 && e.keyCode == 13) {
                      goSimpleCheck();
                  }
              });
          });

        </script>
      </t:putAttribute>

</t:insertDefinition>
