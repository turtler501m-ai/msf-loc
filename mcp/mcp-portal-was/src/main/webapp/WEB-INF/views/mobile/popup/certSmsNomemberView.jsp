<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/mobile/popup/certSmsNomemberView.js?version=22-08-22"></script>
    <script type="text/javascript" src="/resources/js/mobile/order/orderList.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/order/reqSelfDlvry.js"></script>
    <script type="text/javascript">
    var platFormCd = '<c:out value="${platFormCd}" />';
    var certifySucess = 'N';
    var SessionYn = '<c:out value="${SessionYn}" />';
    var loginYn = '<c:out value="${loginYn}" />';
    var pageAuthYn = '<c:out value="${pageAuthYn}" />';
    var orderType = '<c:out value="${orderType}" />';
    $(document).ready(function (){
        $("#regularCertBtn").removeAttr("onClick");
        $("#regularCertBtn").attr("onClick","newRegularCertBtn();");
        initUsimData();
        $("#main-content > div.c-tabs.c-tabs--type2 > div.c-tabs__list.c-expand.sticky-header > button").click(function(){

            if($(this).prop("classList") == 'c-tabs__button is-active'){
                return false;
            }


            if(SessionYn == 'N' && $("#cfrmYn").val() != 'Y'){
                MCP.alert("본인인증이 필요합니다.");
                return false;
            }

            orderSessionChk();

            var tabId = $(this).data("tab");
            $("#main-content > div.c-tabs.c-tabs--type2 > div.c-tabs__list.c-expand.sticky-header > button").each(function(){
                $(this).prop("classList",'c-tabs__button');
            });

            $(this).prop("classList",'c-tabs__button is-active');
            $("#tabA1-panel").hide();
            $("#tabA2-panel").hide();
            $("#"+tabId+"-panel").show();

            if(tabId == 'tabA1'){
                if($("#divJoinArea").is(":visible") && $("#divAgreeArea").is(":visible")){
                    searchOrderTempList();
                }
            }

            if(tabId == 'tabA2'){
                if($("#orderSearch").val() != 'Y'){
                    $("#orderSearch").val('Y');
                    searchOrder(1);
                    searchSelfOrder(1);
                }

            }

        });
        //SessionYn = 'N';

        if(SessionYn == 'Y'){
            searchOrderTempList();
        }
        /*
        if(loginYn == 'Y'){
            searchOrderTempList();
        }
        */


    });

    document.addEventListener("UILoaded", function() {

       setOrderType(orderType);

    });

    function newRegularCertBtn(){
        var certify = $("#certify").val();
        if(certify!="Y"){
            MCP.alert('인증번호를 받으세요.', function (){
                $("#phoneNum" ).focus();
            });


            return false;
        }

        var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
        var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
        if(certifySms==""){
            MCP.alert('인증번호를 입력해 주세요.', function (){
                $("#phoneNum" ).focus();
            });

            return false;
        }

        if($("#timeOverYn").val() == "Y"){
            MCP.alert("유효시간이 지났습니다. \n 인증번호를 재전송 후 다시 인증해주세요.",function(){
                $("#phoneNum").focus();
            });

            return false;
        }

        var varData = ajaxCommon.getSerializedData({
            certifySms:$.trim(certifySms)
            ,phone:$.trim(phoneNum)
            ,menuType :$("#menuType").val()
            ,svcCntrNo : $("#svcCntrNo").val()
            ,name:$("#custNm").val()
            ,birthday :ajaxCommon.isNullNvl($("#birthday").val(),"")
            ,mobileNo:$.trim(phoneNum)

        });


        ajaxCommon.getItem({
            id:'checkCertSmsAjax'
            ,cache:false
            ,url:'/m/sms/checkCertSmsAjax.do'
            ,data: varData
            ,dataType:"json"
        },function(data){

            if(data.returnCode == "00"){
                    MCP.alert("인증이 완료되었습니다.");
                    certifySucess = 'Y';
                    $("#certifyYn").val("Y");
                    $("#combiChkYn").val("N");
                    $("#timeover").hide();
                    $("#countdown").hide();
                    $("#countdownTime").hide();
                    $("#countdownTime").hide();
                    $("#userDivisionYn").val("02");
                    $("#btnOk").prop("disabled", false);
                    $("#phoneNum").prop("disabled", true);
                    $("#certifySms").prop("disabled", true);
                    $("#regularCertBtn").prop("disabled", true);
                    $("#certifyCallBtn").prop("disabled", true);
                    $("#custNm").prop("disabled", true);
                    clearInterval(interval);
                } else{
                    MCP.alert(data.message);
                }
        }
      );

    }

    function targetTermsAgree(){
        $("#chkAgree").prop("checked",true);
    }

    function setOrderType(orderType){

        if(orderType == 'order'){
            $("#tab2").trigger('click');
        }else if(orderType == 'temp'){
            searchOrderTempList();
        }else if(orderType == 'self'){
            $("#tab2").trigger('click');
            $("#id_sgltr8y42").trigger('click');
        }

    }

    function orderSessionChk(){

        $.ajax({
            url : '/m/order/sessionCheckAajx.do',
            type : 'post',
            dataType : 'json',
            async : false,
            success : function(data) {
                var resultCode = data.RESULT_CODE;

                if(resultCode == "00001") {
                    location.href = '/m/certSmsNomemberView.do';
                }
            }
        });

    }

    </script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
    <form id="appForm" name="appForm" method="post" action="/m/appForm/appFormDesignView.do">
        <input type="hidden" id="requestKey" name="requestKey" />
    </form>
    <input type="hidden" name="orderTempPageNo" id="orderTempPageNo" value="1"/>
    <input type="hidden" name="orderTempLastPage" id="orderTempLastPage" />
    <input type="hidden" id="cfrmYn" />
    <input type="hidden" id="orderSearch" />

    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">신청조회<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="c-tabs c-tabs--type2">
        <div class="c-tabs__list c-expand sticky-header" >
          <button id="tab1" type="button" class="c-tabs__button is-active" data-tab="tabA1">가입 진행 중</button>
          <button id="tab2" type="button" class="c-tabs__button" data-tab="tabA2">배송조회</button>
        </div>


        <div class="c-tabs__panel" id="tabA1-panel">
          <p class="c-text c-text--type2">kt M모바일 신청조회에서는 별도의 회원가입 없이 휴대폰 본인인증 후 가입 진행 중인 내역 확인이 가능합니다.</p>
          <p class="c-bullet c-bullet--dot u-co-gray">회원가입 후 kt M모바일만의 다양한 맞춤 서비스를 경험해보세요.</p>



          <div id="divJoinArea" class="c-form u-mt--40" <c:if test="${SessionYn eq 'Y'}"> style="display: none;" </c:if>>
            <span class="c-form__title" id="inpG">가입정보 확인</span>
          <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
          </div>

          <div id="divAgreeArea" class="c-agree u-mt--32" <c:if test="${SessionYn eq 'Y'}"> style="display: none;" </c:if>>
            <div class="c-agree__item">
              <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkB" />
              <label class="c-label" for="chkAgree">개인정보 수집이용 동의<span class="u-co-gray">(필수)</span>
              </label>
              <button type="button" class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMSINF&cdGroupId2=TERMSINF02');">
                <span class="c-hidden">상세보기</span>
                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
              </button>
            </div>
          </div>

          <div class="c-button-wrap">
            <a class="c-button c-button--xsm" href="/m/loginForm.do">kt M모바일계정 로그인</a>
            <a class="c-button c-button--xsm" href="/m/join/fstPage.do">
              <b>회원가입</b>
            </a>
          </div>
          <div class="c-button-wrap">
            <a id="id_cfrm_btn" class="c-button c-button--full c-button--primary" href="#" onclick="authCfrm();" >확인</a>
          </div>




        </div>
        <div class="c-tabs__panel" id="tabA2-panel" style="display: none;">
            <%@ include file="/WEB-INF/views/mobile/order/orderList.jsp"%>
        </div>
      </div>
    </div>
  </div>
  </t:putAttribute>

  <t:putAttribute name="popLayerAttr">
  </t:putAttribute>


</t:insertDefinition>