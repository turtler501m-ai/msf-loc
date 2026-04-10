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
	<link href="/resources/css/mobile/styles.css" rel="stylesheet" />
	<script type="text/javascript">

			$("#menuType").val("shareData");

			function btn_ok(){
				if($("#certifyYn").val() != 'Y'){
		    		MCP.alert("인증을 받아주세요.");
		    		return;
				}else{
					ajaxCommon.createForm({
					    method:"post"
					    ,action:"/m/content/myShareDataView.do"
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

	<div id="content">
	  <div class="ly-wrap">
	    <div class="ly-content" id="main-content">
	      <div class="ly-page-sticky">
	        <h2 class="ly-page__head">함께쓰기<button class="header-clone__gnb"></button>
	        </h2>
	      </div>
	     <form name="frm" id="frm" method="post" action="/m/loginForm.do">
			<input type="hidden" name="uri" id="uri" value="/m/content/myShareDataView.do"/>
			<input type="hidden" name="certifyYn" id="certifyYn" value=""/>
			<input type="hidden" id="userDivisionYn"  name="userDivisionYn" value=""/>
		  </form>

		  <div class="upper-banner upper-banner--bg c-expand">
			<div class="upper-banner__content">
				<strong class="upper-banner__title">
					함께쓰기란?
				</strong>
				<p>가족/지인과 결합하여 매월 2GB<br/> 데이터를 자동으로 주고 받는 서비스</p>
				<c:set var="dataInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />
		        <c:forEach var="item" items="${dataInfo}" varStatus="status">
		        	<c:if test = "${Constants.DATA_INFO_CD eq item.dtlCd}">
		        		<button class="c-button c-button--sm upper-banner__anchor" onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">
							자세히 보기<i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
						</button>
		        	</c:if>
		        </c:forEach>
			</div>
			<div class="upper-banner__image">
				<img src="/resources/images/mobile/rate/bg_sharedata.png" alt="">
			</div>
		  </div>

	      <c:if test="${!nmcp:hasLoginUserSessionBean()}">
	          <span class="Number-label--type1 u-mt--44">
	              <em>1</em>다이렉트 몰 아이디가 있는 경우 (로그인 후 진행)
	          </span>
		      <div class="c-button-wrap u-mt--18">
		        <a class="c-button c-button--full c-button--mint"  href="javascript:void(0);" onclick="$('#frm').submit();">로그인하고 시작하기</a>
		      </div>
		      <hr class="c-hr c-hr--type3 u-mt--40">
	   		  <span class="Number-label--type1 u-mt--42 u-mb--m40">
	          	  <em>2</em>다이렉트몰 아이디가 없는 경우 (가입정보 인증 후 진행)
	          </span>
		      <span class="c-form__title c-hidden" id="inpJoinInfo">가입정보 확인하기</span>
		      <!-- sms 인증 -->
	          <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
		      <div class="c-button-wrap">
		          <button class="c-button c-button--full c-button--primary"  disabled="disabled" id="btnOk"  onclick="btn_ok();">가입정보 확인</button>
		      </div>
	      </c:if>
	      <hr class="c-hr c-hr--type2">
	      <b class="c-text c-text--type3">
	        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
	      </b>
	      <strong class="c-heading c-heading--type4 u-mb--0 u-mt--16">서비스 개요</strong>
	      <ul class="c-text-list c-bullet c-bullet--dot">
	        <li class="c-text-list__item u-co-gray">함께쓰기 결합 시 매월 1일자에 결합 상대 회선(결합 상대 회선)으로부터 데이터 2GB 제공됩니다. (데이터 주기 회선은 기본 제공량에서 2GB 차감).</li>
	        <li class="c-text-list__item u-co-gray">결합 당월에 한정하여 데이터 주기 회선의 데이터 차감 없이 프로모션 데이터 2GB 무료 제공됩니다.</li>
	      </ul>
	      <strong class="c-heading c-heading--type4 u-mb--0 u-mt--16">주의사항</strong>
	      <ul class="c-text-list c-bullet c-bullet--dot">
	        <li class="c-text-list__item u-co-gray">함께쓰기는 kt M모바일을 이용중인 가족/지인과 결합하여 매월 2GB 데이터를 자동으로 주고 받을 수 있는 서비스로 가입을 위해서는 로그인 또는 대표회선의 가입 여부 확인이 필요합니다.</li>
	        <li class="c-text-list__item u-co-gray">함께쓰기 결합은 전용 부가서비스 가입을 통해 이루어집니다. (LTE결합서비스_데이터받기 2GB/데이터주기 2GB)</li>
	        <li class="c-text-list__item u-co-gray">함께쓰기 결합은 kt M모바일을 사용중인 고객간 가능합니다. (동일명의 결합 가능)</li>
	        <li class="c-text-list__item u-co-gray">함께쓰기 결합 해지는 고객센터 (114)를 통해 신청 가능합니다.</li>
	        <li class="c-text-list__item u-co-gray">함께쓰기 결합 후 월 중 모회선의 요금제 변경, 정지, 해지 시 데이터주기 2GB의 초과요금이 발생할 수 있습니다.</li>
	      </ul>
	    </div>
	  </div><!-- [START]-->
	</div>
</t:putAttribute>
</t:insertDefinition>