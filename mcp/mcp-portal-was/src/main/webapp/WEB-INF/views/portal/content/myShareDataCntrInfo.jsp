<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">함께쓰기</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
       <script type="text/javascript">

         $("#menuType").val("shareData");

         function btn_ok(){
            if($("#certifyYn").val() != 'Y'){
                MCP.alert("인증을 받아주세요.");
                return;
            }else{

                ajaxCommon.createForm({
                    method:"post"
                    ,action:"/content/myShareDataView.do"
                });
                ajaxCommon.attachHiddenElement("menuType",$("#menuType").val());
                ajaxCommon.attachHiddenElement("userDivisionYn",$("#userDivisionYn").val());
                ajaxCommon.attachHiddenElement("phoneNum",$("#phoneNum").val());
                ajaxCommon.formSubmit();
            }
        }
        function btnRegDtl(param){
            openPage('termsInfoPop','/termsPop.do',param);
        }



    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <input type="hidden" name="ncType" id="ncType" value="0"/>

        <div class="ly-content" id="main-content">
          <div class="ly-page--title">
            <h2 class="c-page--title">함께쓰기</h2>
          </div>
          <form name="frm" id="frm" method="post" action="/loginForm.do">
            <input type="hidden" name="uri" id="uri" value="/content/myShareDataView.do"/>
            <input type="hidden" id="userDivisionYn"  name="userDivisionYn" value=""/>

          </form>
          <div class="ly-page--deco u-bk--blue">
            <div class="ly-avail--wrap">
              <h3 class="c-headline">함께쓰기란?</h3>
                  <p class="c-headline--sub">kt M모바일을 이용중인 가족/지인과 결합하여 매월 2GB 데이터를
                    <br>자동으로 주고 받을 수 있는 서비스 입니다.
                  </p>
              <div class="c-button-wrap c-button-wrap--left">
               <c:set var="dataInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />
                  <c:forEach var="item" items="${dataInfo}" varStatus="status">
                   <c:if test = "${Constants.DATA_INFO_CD eq item.dtlCd}">
                    <button class="c-button c-button--underline c-button--sm u-co-white" type="button"  onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">자세히보기</button>
                   </c:if>
                   </c:forEach>
               </div>
              <img class="c-headline--deco" src="/resources/images/portal/content/img_together.png" alt="" aria-hidden="true">
            </div>
          </div>
          <div class="c-row c-row--lg">
              <c:if test="${!nmcp:hasLoginUserSessionBean()}">
                <span class="Number-label--type1 u-mt--68">
                    <em>1</em>다이렉트 몰 아이디가 있는 경우 (로그인 후 진행)
                </span>
                <div class="c-button-wrap u-mt--42">
                    <a class="c-button c-button-round--md c-button--mint u-width--460"  href="javascript:void(0);" onclick="$('#frm').submit();">로그인하고 시작하기</a>
                </div>
                <hr class="c-hr c-hr--title u-mt--58">
                <span class="Number-label--type1 u-mt--50">
                    <em>2</em>다이렉트몰 아이디가 없는 경우 (가입정보 인증 후 진행)
                </span>
             </c:if>
            <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>

            <div class="c-button-wrap u-mt--56">
              <a class="c-button c-button--lg c-button--primary u-width--460 is-disabled" href="javascript:void(0);"  id="btnOk" onclick="btn_ok();">가입정보 확인</a>
            </div><!-- [2022-01-14] 페이지 하단 유의사항 마크업 수정-->
            <h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
              <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
              <span class="u-ml--4">알려드립니다</span>
            </h3>
            <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
              <!-- [$2022-01-14] 페이지 하단 유의사항 마크업 수정-->
              <li class="c-text-list__item">서비스 개요<ul class="c-text-list c-bullet c-bullet--dot">
                  <li class="c-text-list__item u-co-gray">함께쓰기 결합 시 매월 1일자에 결합 상대 회선(결합 상대 회선)으로부터 데이터 2GB 제공됩니다. (데이터 주기 회선은 기본 제공량에서 2GB 차감)</li>
                  <li class="c-text-list__item u-co-gray">결합 당월에 한정하여 데이터 주기 회선의 데이터 차감 없이 프로모션 데이터 2GB 무료 제공됩니다.</li>
                </ul>
              </li>
              <li class="c-text-list__item">주의사항<ul class="c-text-list c-bullet c-bullet--dot">
                  <li class="c-text-list__item u-co-gray">함께쓰기는 kt M모바일을 이용중인 가족/지인과 결합하여 매월 2GB 데이터를 자동으로 주고 받을 수 있는 서비스로 가입을 위해서는 로그인 또는 대표회선의 가입 여부 확인이 필요합니다.</li>
                  <li class="c-text-list__item u-co-gray">함께쓰기 결합은 전용 부가서비스 가입을 통해 이루어집니다.(LTE결합서비스_데이터받기2GB/데이터주기2GB)</li>
                  <li class="c-text-list__item u-co-gray">함께쓰기 결합은 특정 요금제에서만 가입 가능합니다.(결합가능 요금제 리스트는 상단 자세히 보기 참조)</li>
                  <li class="c-text-list__item u-co-gray">함께쓰기 결합은 kt M모바일을 사용중인 고객간 가능합니다.(동일명의 결합 가능)</li>
                  <li class="c-text-list__item u-co-gray">함께쓰기 결합 해지는 고객센터 (114)를 통해 신청 가능합니다.</li>
                  <li class="c-text-list__item u-co-gray">함께쓰기 결합 후 월 중 모회선의 요금제 변경, 정지, 해지 시 데이터주기 2GB의 초과요금이 발생할 수 있습니다.</li>
                </ul>
              </li>
            </ul>
          </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>

