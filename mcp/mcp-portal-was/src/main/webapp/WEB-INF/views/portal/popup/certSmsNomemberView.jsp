<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">신청조회</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/portal/popup/certSmsNomemberView.js?version=22-08-22"></script>
    <script type="text/javascript" src="/resources/js/portal/order/orderList.js"></script>
    <script type="text/javascript" src="/resources/js/portal/order/reqSelfDlvry.js"></script>
    <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
    <script type="text/javascript">

    var certifySucess = 'N';
    var SessionYn = '<c:out value="${SessionYn}" />';
    var loginYn = '<c:out value="${loginYn}" />';
    var pageAuthYn = '<c:out value="${pageAuthYn}" />';
    var orderType = '<c:out value="${orderType}" />';

    <c:set var="today" value="<%=new java.util.Date()%>" />
    <c:set var="nowDate"><fmt:formatDate value="${today}" pattern="yyyy.MM.dd" /></c:set>

    <c:set var="weekAgo" value="<%=new java.util.Date(new java.util.Date().getTime() - 60*60*24*1000*7L)%>"/>
    <c:set var="weekAgoDate"><fmt:formatDate value="${weekAgo}" pattern="yyyy.MM.dd" /></c:set>

    var nowDate = '<c:out value="${nowDate}"></c:out>';
    var weekAgoDate = '<c:out value="${weekAgoDate}"></c:out>';
    var monthAgoDate = '<c:out value="${monthAgoDate}"></c:out>';
    var twoMonthAgoDate = '<c:out value="${twoMonthAgoDate}"></c:out>';
    var oneYearAgoDate = '<c:out value="${oneYearAgoDate}"></c:out>';

    $(document).ready(function (){
        initUsimData();

        $('#subbody_loading').hide();

        $("#regularCertBtn").removeAttr("onClick");
        $("#regularCertBtn").attr("onClick","newRegularCertBtn();");
        $("#main-content > div.c-tabs.c-tabs--type1 > div > button").click(function(){

            if($(this).prop("classList") == 'c-tabs__button is-active'){
                return false;
            }

            if(SessionYn == 'N' && $("#cfrmYn").val() != 'Y'){
                MCP.alert("본인인증이 필요합니다.");
                return false;
            }

            orderSessionChk();

            var tabDataId = $(this).data("value");

            $("#main-content > div.c-tabs.c-tabs--type1 > div > button").removeClass("is-active");
            $(this).addClass("is-active");

            $("#tabB1panel").hide();
            $("#tabB2panel").hide();

            $("#"+tabDataId).show();

            if(tabDataId == 'tabB1panel'){
                if($("#divJoinArea").is(":visible")){
                    searchOrderTempList();
                }
            }

            if(tabDataId == 'tabB2panel'){
                if($("#orderSearch").val() != 'Y'){
                    $("#orderSearch").val('Y');
                    searchOrder(1);
                    searchSelfOrder(1);
                    $("#tab11").trigger('click');
                }

            }

        });

        if(SessionYn == 'Y'){
            searchOrderTempList();
        }

        $("input[name='radAOne']").change(function(){
            if($(this).is(':checked')){

                var selRangDate;
                if($(this).attr("id") == 'radA1'){
                    selRangDate = [weekAgoDate,nowDate];
                }else if($(this).attr("id") == 'radA2'){
                    selRangDate = [monthAgoDate,nowDate];
                }else if($(this).attr("id") == 'radA3'){
                    selRangDate = [twoMonthAgoDate,nowDate];
                }

                KTM.datePicker("#range.flatpickr-input", {
                     mode: "range", defaultDate: selRangDate , minDate: oneYearAgoDate ,maxDate: nowDate ,dateFormat: 'Y.m.d'
                         ,onClose: function(selectedDates, dateStr, instance){
                               if(dateStr.indexOf('~') < 0){
                                   dateStr = dateStr + ' ~ ' + dateStr;
                               }
                               $("#range").val(dateStr);
                         }
                });

            }
        });

        $("input[name='radBTwo']").change(function(){
            if($(this).is(':checked')){

                var selRangDate;
                if($(this).attr("id") == 'radB1'){
                    selRangDate = [weekAgoDate,nowDate];
                }else if($(this).attr("id") == 'radB2'){
                    selRangDate = [monthAgoDate,nowDate];
                }else if($(this).attr("id") == 'radB3'){
                    selRangDate = [twoMonthAgoDate,nowDate];
                }

                KTM.datePicker("#range_2.flatpickr-input", {
                     mode: "range", defaultDate: selRangDate , minDate: oneYearAgoDate  ,maxDate: nowDate ,dateFormat: 'Y.m.d'
                         ,onClose: function(selectedDates, dateStr, instance){
                               if(dateStr.indexOf('~') < 0){
                                   dateStr = dateStr + ' ~ ' + dateStr;
                               }
                               $("#range_2").val(dateStr);
                         }
                });

            }
        });

        $("#orderStdt").val(monthAgoDate.trim().replace(/\./gi, ""));
        $("#orderEddt").val(nowDate.trim().replace(/\./gi, ""));
        $("#selfStdt").val(monthAgoDate.trim().replace(/\./gi, ""));
        $("#selfEddt").val(nowDate.trim().replace(/\./gi, ""));




    });

    document.addEventListener("UILoaded", function() {

        KTM.datePicker("#range.flatpickr-input", {
             mode: "range" , defaultDate: [monthAgoDate,nowDate] , minDate: oneYearAgoDate  ,maxDate: nowDate ,dateFormat: 'Y.m.d'
                 ,onClose: function(selectedDates, dateStr, instance){
                       if(dateStr.indexOf('~') < 0){
                           dateStr = dateStr + ' ~ ' + dateStr;
                       }
                       $("#range").val(dateStr);
                 }
        });

       KTM.datePicker("#range_2.flatpickr-input", {
         mode: "range" , defaultDate: [monthAgoDate,nowDate] ,   minDate: oneYearAgoDate  ,maxDate: nowDate ,dateFormat: 'Y.m.d'
             ,onClose: function(selectedDates, dateStr, instance){
                 if(dateStr.indexOf('~') < 0){
                     dateStr = dateStr + ' ~ ' + dateStr;
                   }
                   $("#range_2").val(dateStr);
           }
       });

       setOrderType(orderType);
    });

    function newRegularCertBtn(){
        var certify = $("#certify").val();
        if(certify!="Y"){
            MCP.alert("인증번호를 받으세요.",function(){
                $("#certifyCallBtn").focus();
            });
            return false;
        }

        var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
        var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
        if(certifySms==""){
            MCP.alert("인증번호를 입력해 주세요.",function(){
                $("#certifySms").focus()
            });
            return false;
        }else if(certifySms.length != 6){
            MCP.alert("인증번호를 확인해 주세요.",function(){
                $("#certifySms").focus()
            });
            return false;
        }

        if($("#timer").text() == '' || $("#timeover").is(":visible")){
            MCP.alert("유효시간이 지났습니다. \n 인증번호를 재전송 후 다시 인증해주세요.");
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


        ajaxCommon.getItemNoLoading({
            id:'checkCertSmsAjax'
            ,cache:false
            ,url:'/sms/checkCertSmsAjax.do'
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
                    $("#btnOk").removeClass("is-disabled");
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
        $("#id_cfrm_btn").prop("disabled",false);
    }

    function agreeChk(obj){

        if(certifySucess != 'Y'){
            MCP.alert("본인인증이 필요합니다.");
            $("#chkAgree").prop("checked",false);
            return false;
        }

        if($(obj).is(":checked")){
            $("#id_cfrm_btn").prop("disabled",false);

        }else{
            $("#id_cfrm_btn").prop("disabled",true);

        }

    }


    function setOrderType(orderType){
        if(orderType == 'order'){
            $("#tab2").trigger('click');
        }else if(orderType == 'temp'){
            searchOrderTempList();
        }else if(orderType == 'self'){
            $("#tab2").trigger('click');
            $("#tab22").trigger('click');
        }

    }

    function orderSessionChk(){

        $.ajax({
            url : '/order/sessionCheckAajx.do',
            type : 'post',
            dataType : 'json',
            async : false,
            success : function(data) {
                var resultCode = data.RESULT_CODE;

                if(resultCode == "00001") {
                    location.href = '/certSmsNomemberView.do';
                }
            }
        });

    }

    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
       <form id="appForm" name="appForm" method="post" action="/appForm/appFormDesignView.do">
        <input type="hidden" id="requestKey" name="requestKey" />
    </form>
    <input type="hidden" name="orderTempPageNo" id="orderTempPageNo" value="1" />
    <input type="hidden" name="orderTempLastPage" id="orderTempLastPage" />
    <input type="hidden" id="cfrmYn" />
    <input type="hidden" id="orderSearch" />
    <div class="ly-content" id="main-content">
      <div id="subbody_loading" style="display:flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
            <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
      </div>
      <div class="ly-page--title">
        <h2 class="c-page--title">신청조회</h2>
      </div>
      <div class="c-tabs c-tabs--type1" data-ui-tab>
        <div class="c-tabs__list" role="tablist">
            <button class="c-tabs__button" id="tab1" data-value="tabB1panel" role="tab" aria-controls="tabB1panel" aria-selected="false" tabindex="-1">가입 진행 중</button>
            <button class="c-tabs__button" id="tab2" data-value="tabB2panel" role="tab" aria-controls="tabB2panel" aria-selected="false" tabindex="-1">배송조회</button>
        </div>
      </div>
      <!-- 백업용 다시 말바꾸질몰라서.. -->
      <!-- <div class="c-tabs__panel" id="tabB1panel"> -->
      <div class="c-tabs__panel" id="tabB1panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
        <div id="divJoinArea" <c:if test="${SessionYn eq 'Y'}"> style="display: none;" </c:if>>
          <div class="c-row c-row--lg">
            <p class="c-text c-text--fs17">kt M모바일 신청조회에서는 별도의 회원가입 없이 휴대폰 본인인증 후 가입 진행 중인 내역 확인이 가능합니다.</p>
            <p class="c-bullet c-bullet--fyr u-co-sub-4">회원가입 후 kt M모바일만의 다양한 맞춤 서비스를 경험해보세요.</p>
            <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>
            <h3 class="c-form__title u-mt--48">약관동의</h3>
            <div class="c-agree">
              <div class="c-agree__item">
                <div class="c-agree__inner">
                  <button type="button" class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMSINF&cdGroupId2=TERMSINF02');">
                      <span class="c-hidden">개인정보 수집이용 동의 상세팝업 보기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                  </button>
                  <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree" href="javascript:void(0);" onclick="agreeChk(this);">
                  <label class="c-label" for="chkAgree">개인정보 수집이용 동의</label>
                </div>
              </div>
            </div>
            <div class="c-button-wrap u-mt--56">
              <button id="id_cfrm_btn" type="button" class="c-button c-button--lg c-button--primary u-width--460"  onclick="authCfrm();" disabled>확인</button>
            </div>
            <div class="c-button-wrap u-mt--40">
              <a class="c-button c-button--underline" href="/loginForm.do">kt M모바일계정 로그인</a>
              <a class="c-button c-button--underline" href="/join/fstPage.do">회원가입</a>
            </div>
          </div>
        </div>

        <div id="divOrderTempArea" style="display: none;">
          <div class="c-row c-row--lg">
            <p class="c-text c-text--fs17">최근 7일 까지의 내역을 보실 수 있습니다.</p>
            <p class="c-bullet c-bullet--fyr u-co-gray">단, 당사 사정에 의해 할인, 정책 등이 변경되어 일부 저장 내용이 달라질 수 있습니다.</p><!-- 데이터 있는 경우-->
            <ul id="orderTempUlArea" class="c-list u-mt--8">
              <li class="c-list__item">
                <div class="c-list__head">
                  <div class="c-list__title">
                    <span class="c-hidden">분류</span>
                    <b>유심</b>
                    <div class="c-list__sub">셀프개통</div>
                  </div>
                  <div class="c-list__sub">0000.00.00</div>
                </div>
                <div class="c-list__body">
                  <div class="detail">
                    <ul class="product-info">
                      <li class="product-info__item">
                        <!-- [2022-02-08] 아이콘 변경-->
                        <i class="c-icon c-icon--payment-24" aria-hidden="true"></i>
                        <span class="detail__sub-title c-hidden">요금제명</span>데이터 전용 10GB+
                      </li>
                    </ul>
                    <div class="detail__item">
                      <span class="detail__sub-title">월 납부금액</span>
                      <b>000,000원</b>
                    </div>
                    <div class="detail__item">
                      <span class="detail__sub-title">진행단계<span class="u-co-black">(1/5)</span>
                      </span>
                      <b class="u-co-point-4">본인 확인 및 약관동의</b>
                    </div>
                    <div class="c-button-wrap">
                      <a class="c-button c-button-round--sm c-button--white" href="">이어하기</a>
                    </div>
                  </div>
                </div>
              </li>
              <li class="c-list__item">
                <div class="c-list__head">
                  <div class="c-list__title">
                    <span class="c-hidden">분류</span>
                    <b>휴대폰</b>
                    <div class="c-list__sub">중고폰</div>
                  </div>
                  <div class="c-list__sub">0000.00.00</div>
                </div>
                <div class="c-list__body">
                  <div class="detail">
                    <ul class="product-info">
                      <li class="product-info__item">
                        <!-- [2022-02-08] 아이콘 변경-->
                        <i class="c-icon c-icon--phone-24" aria-hidden="true"></i>
                        <span class="detail__sub-title c-hidden">휴대폰명</span>갤럭시 A32 / 64GB / 화이트
                      </li>
                      <li class="product-info__item">
                        <!-- [2022-02-08] 아이콘 변경-->
                        <i class="c-icon c-icon--payment-24" aria-hidden="true"></i>
                        <span class="detail__sub-title c-hidden">요금제명</span>LTE 데이터 알뜰 0GB/30분
                      </li>
                    </ul>
                    <div class="detail__item">
                      <span class="detail__sub-title">월 납부금액</span>
                      <b>000,000원</b>
                    </div>
                    <div class="detail__item">
                      <span class="detail__sub-title">진행단계<span class="u-co-black">(2/5)</span>
                      </span>
                      <b class="u-co-point-4">고객정보 및 신분증 확인</b>
                    </div>
                    <div class="c-button-wrap">
                      <!-- 불가능한 경우 is-disabled 클래스 추가-->
                      <a class="c-button c-button-round--sm c-button--white is-disabled" href="">이어하기</a><!-- //-->
                    </div>
                  </div>
                </div><!-- 불가능한 경우 문구 추가-->
                <div class="c-list__bottom">
                  <p class="c-bullet c-bullet--error">현재 해당 조건으로 가입이 불가능합니다.</p>
                </div><!-- //-->
              </li>
            </ul>
            <nav id="orderTempPaging" class="c-paging" aria-label="조회결과" style="display: none;">
            </nav>
          </div>
        </div>

      </div>
      <div class="c-tabs__panel" id="tabB2panel" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
      <!-- 백업용 다시 말바꾸질몰라서.. -->
      <!-- <div class="c-tabs__panel" id="tabB2panel"> -->
        <%@ include file="/WEB-INF/views/portal/order/orderList.jsp"%>
      </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>
