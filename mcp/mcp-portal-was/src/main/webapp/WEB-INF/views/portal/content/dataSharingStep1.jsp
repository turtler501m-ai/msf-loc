<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">데이터쉐어링</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">


       <script type="text/javascript">
           $("#menuType").val("sharing");

           document.getElementById("btnOk").addEventListener("click", function () {
               if($("#certifyYn").val() != 'Y'){
                   MCP.alert("인증을 받아주세요.");
                   return;
               } else {
                   ajaxCommon.createForm({
                       method:"post"
                       ,action:"/content/dataSharingStep2.do"
                   });
                   ajaxCommon.attachHiddenElement("userDivisionYn",$("#userDivisionYn").val());
                   ajaxCommon.attachHiddenElement("phoneNum",$("#phoneNum").val());
                   ajaxCommon.attachHiddenElement("menuType",$("#menuType").val());
                   ajaxCommon.formSubmit();
               }
           });

           function btnRegDtl(param){
               openPage('termsInfoPop','/termsPop.do',param);
           }

           $(document).ready(function (){
               // 자주 묻는 질문 및 유의사항
               ajaxCommon.getItemNoLoading({
                   id:'getNotice'
                   ,cache:false
                   ,url:'/termsPop.do'
                   ,data: "cdGroupId1=TERMSSHA&cdGroupId2=faqImportantInfo"
                   ,dataType:"json"
               } ,function(jsonObj){
                   var inHtml = unescapeHtml(jsonObj.docContent);
                   $('.data-sharing-notice').html(inHtml);
                   KTM.initialize();
               });
           })



    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
        <form name="frm" id="frm" method="post" action="/loginForm.do">
            <input type="hidden"	 name="uri" 			id="uri" value="/content/dataSharingStep1.do"/>
            <input type="hidden"	 name="userDivisionYn"  id="userDivisionYn"   value="02"/>
        </form>

        <%@ include file="/WEB-INF/views/portal/content/dataSharingInfo.jsp"%>

        <div class="c-row c-row--lg">
          <div class="data-sharing-form u-mt--96">
              <p class="u-fs-20 u-fw--bold u-ta-center">정보 확인을 위해 인증 방법을 선택 후 데이터쉐어링 가입을 신청하세요.</p>
              <div class="c-button-wrap u-mt--34">
                  <!-- 로그인 및 휴대폰 인증 스크립트 필요 / 가족결합+ combineKt.js 참고 -->
                  <a id="btnLogin" class="c-button c-button--lg c-button--primary c-button--w460" href="javascript:void(0);" onclick="$('#frm').submit();" title="로그인 하기">로그인 하기</a>
                  <button type="button" class="c-button c-button--lg c-button--white c-button--w460" title="휴대폰인증하기" data-dialog-trigger="#divCertifySms">휴대폰 인증하기</button>
              </div>
          </div>


          <div class="data-sharing-notice">

          </div>




      </div>
    </div>

    <!-- 휴대폰 인증하기  팝업 -->
    <div class="c-modal c-modal--xlg" id="divCertifySms" role="dialog" aria-labelledby="divCertifySmsTitle">
        <div class="c-modal__dialog" role="document">
            <div class="c-modal__content">
                <div class="c-modal__header">
                    <h2 class="c-modal__title" id="divCertifySmsTitle">휴대폰 인증</h2>
                    <button class="c-button" data-dialog-close>
                        <i class="c-icon c-icon--close" aria-hidden="true"></i>
                        <span class="c-hidden">팝업닫기</span>
                    </button>
                </div>
                <div class="c-modal__body">
                    <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>
                    <div class="c-notice-wrap">
                        <h5 class="c-notice__title">
                            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                            <span>알려드립니다</span>
                        </h5>
                        <ul class="c-notice__list">
                            <li>SMS(단문메시지)는 시스템 사정에 따라 다소 지연될 수 있습니다.</li>
                            <li>인증번호는 1899-5000로 발송되오니 해당 번호를 단말기 스팸 설정 여부 및 스팸편지함을 확인 해 주세요.</li>
                        </ul>
                    </div>
                    <div class="c-button-wrap u-mt--48">
                        <button type="button" class="c-button c-button--lg c-button--primary c-button--w460 is-disabled" href="javascript:void(0);" id="btnOk">가입정보 확인</button>
                    </div>
                </div>
            </div>
        </div>
    </div>



    </t:putAttribute>
</t:insertDefinition>

