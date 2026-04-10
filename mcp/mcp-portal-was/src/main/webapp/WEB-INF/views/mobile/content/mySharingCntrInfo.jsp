<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>

    <script type="text/javascript">
        $("#menuType").val("sharing");

        function btn_ok(){
            if($("#certifyYn").val() != 'Y'){
                MCP.alert("인증을 받아주세요.");
                return;
            }else{
                ajaxCommon.createForm({
                    method:"post"
                    ,action:"/m/content/mySharingView.do"
                });
                ajaxCommon.attachHiddenElement("userDivisionYn",$("#userDivisionYn").val());
                ajaxCommon.attachHiddenElement("phoneNum",$("#phoneNum").val());
                ajaxCommon.attachHiddenElement("menuType",$("#menuType").val());
                ajaxCommon.formSubmit();
            }
        }

        function btnRegDtl(param){
            openPage('termsInfoPop','/termsPop.do',param);
        }

    </script>

</t:putAttribute>

 <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">데이터쉐어링<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <c:set var="sharingInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />

      <div class="upper-banner upper-banner--bg c-expand">
        <div class="upper-banner__content">
            <strong class="upper-banner__title">
                데이터쉐어링이란?
            </strong>
            <p>대표회선의 데이터를 여러기기에서<br/> 함께 사용할 수 있는 서비스</p>
            <c:forEach var="item" items="${sharingInfo}" varStatus="status">
              <c:if test = "${Constants.SHAR_INFO_CD eq item.dtlCd}">
                <button class="c-button c-button--sm upper-banner__anchor" onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">
                    자세히 보기<i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                </button>
              </c:if>
            </c:forEach>
        </div>
        <div class="upper-banner__image">
            <img src="/resources/images/mobile/rate/bg_sharing.png" alt="">
        </div>
      </div>

      <form name="frm" id="frm" method="post" action="/m/loginForm.do">
        <input type="hidden" name="uri" id="uri" value="/m/content/mySharingView.do"/>
        <input id="certifyYn" type="hidden" value="N" />
        <input type="hidden" id="userDivisionYn" name="userDivisionYn" value=""/>
      </form>
      <c:if test="${!nmcp:hasLoginUserSessionBean()}">
          <span class="Number-label--type1 u-mt--44">
              <em>1</em>다이렉트 몰 아이디가 있는 경우 (로그인 후 진행)
          </span>
	      <div class="c-button-wrap u-mt--18">
	          <a class="c-button c-button--full c-button--mint" href="javascript:void(0);" onclick="$('#frm').submit();">로그인하고 시작하기</a>
	      </div>
   		  <hr class="c-hr c-hr--type3 u-mt--40">
   		  <span class="Number-label--type1 u-mt--42 u-mb--m40">
          	  <em>2</em>다이렉트몰 아이디가 없는 경우 (가입정보 인증 후 진행)
          </span>
      	  <div class="c-form">
          	  <span class="c-form__title c-hidden" id="inpJoinInfo">가입정보 확인하기</span>
      	      <!-- sms 인증 -->
              <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
          </div>
          <div class="c-button-wrap">
              <button class="c-button c-button--full c-button--primary" disabled="disabled" id="btnOk"  onclick="btn_ok();">가입정보 확인</button>
          </div>
      </c:if>

      <h5 class="sharing__subtit">데이터쉐어링 가입 퀵 가이드</h5>
      <div class="embed-youtube u-mt--16">
	      <iframe src="https://www.youtube.com/embed/7-rB3pCL0Ow" title="알뜰폰 데이터쉐어링, 쉽고 간편하게 5분 완료!ㅣkt M모바일ㅣ서비스 퀵 가이드" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"></iframe>
	  </div>

      <hr class="c-hr c-hr--type2">
      <p class="c-text--type3 c-bullet">
        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
        알려드립니다
      </p>
      <ul class="c-text-list c-bullet c-bullet--hyphen u-mt--16">
        <li class="c-text-list__item u-co-gray">데이터쉐어링은 대표회선의 데이터를 여러 기기에서 함께 사용할 수 있는 서비스로 가입을 위해서는 로그인 또는 대표회선의 가입 여부 확인이 필요합니다.</li>
        <li class="c-text-list__item u-co-gray">데이터쉐어링은 대표 회선 당 1개의 회선만 연결하여 사용 가능하며, 여러 회선을 개통하실 경우 사용이 불가하므로 고객센터를 통하여 개별 해지를 하셔야 합니다.</li>
      </ul>
    </div>
</t:putAttribute>
</t:insertDefinition>