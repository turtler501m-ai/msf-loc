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

          function btn_ok(){
            if($("#certifyYn").val() != 'Y'){
                MCP.alert("인증을 받아주세요.");
                return;
            }else{

                ajaxCommon.createForm({
                    method:"post"
                    ,action:"/content/mySharingView.do"
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
      <div class="ly-page--title">
        <h2 class="c-page--title">데이터쉐어링</h2>
      </div>
      <form name="frm" id="frm" method="post" action="/loginForm.do">
        <input type="hidden"	 name="uri" 			id="uri" value="/content/mySharingView.do"/>
        <input type="hidden"	 name="userDivisionYn"  id="userDivisionYn"   value="02"/>
        </form>
      <div class="ly-page--deco u-bk--blue">
        <div class="ly-avail--wrap">
          <h3 class="c-headline">데이터쉐어링이란?</h3>
          <p class="c-headline--sub">데이터쉐어링은 대표회선의 데이터를 여러 기기에서 함께 사용할 수 있는
            <br>서비스로 대표 회선당 1개의 회선만 가입/결합하여 사용 가능합니다.
          </p>
          <div class="c-button-wrap c-button-wrap--left">
            <c:set var="sharDataInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />
            <c:forEach var="item" items="${sharDataInfo}" varStatus="status">
                  <c:if test = "${Constants.SHAR_INFO_CD eq item.dtlCd}">
                <button class="c-button c-button--underline c-button--sm u-co-white" type="button"  onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">자세히보기</button>
                 </c:if>
             </c:forEach>
          </div>
          <img class="c-headline--deco" src="/resources/images/portal/content/img_sharing.png" alt="" aria-hidden="true">
        </div>
      </div>
      <div class="c-row c-row--lg">
          <c:if test="${!nmcp:hasLoginUserSessionBean()}">
              <span class="Number-label--type1 u-mt--68">
                <em>1</em>다이렉트 몰 아이디가 있는 경우 (로그인 후 진행)
            </span>
            <div class="c-button-wrap u-mt--42">
                <a class="c-button c-button-round--md c-button--mint u-width--460" href="javascript:void(0);" onclick="$('#frm').submit();">로그인하고 시작하기</a>
            </div>
            <hr class="c-hr c-hr--title u-mt--58">
            <span class="Number-label--type1 u-mt--50">
                <em>2</em>다이렉트몰 아이디가 없는 경우 (가입정보 인증 후 진행)
            </span>
        </c:if>
        <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>
        <div class="c-button-wrap u-mt--56">
          <a class="c-button c-button--lg c-button--primary u-width--460 is-disabled" href="javascript:void(0);" disabled="disabled" id="btnOk" onclick="btn_ok();">가입정보 확인</a>
        </div>
        <h5 class="sharing__subtit">데이터쉐어링 가입 퀵 가이드</h5>
        <iframe class="u-mt--24" src="https://www.youtube.com/embed/7-rB3pCL0Ow" width="940" height="529" title="알뜰폰 데이터쉐어링, 쉽고 간편하게 5분 완료!ㅣkt M모바일ㅣ서비스 퀵 가이드" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen=""></iframe>
        <h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </h3>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <li class="c-text-list__item u-co-gray">데이터쉐어링은 대표 회선의 데이터를 여러 기기에서 함께 사용할 수 있는 서비스로 가입을 위해서는 로그인 또는 대표회선의 가입 여부 확인이 필요합니다.</li>
          <li class="c-text-list__item u-co-gray">데이터쉐어링은 대표 회선 당 1개의 회선만 연결하여 사용 가능하며, 여러 회선을 개통하실 경우 사용이 불가하므로 고객센터를 통하여 개별 해지를 하셔야 합니다.</li>
        </ul>
      </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>

