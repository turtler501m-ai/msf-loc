<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
	    <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
	    <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
	    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
	    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
	    <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
	   	<script type="text/javascript" src="/resources/js/mobile/mypage/prvCommData.js"></script>
    	<script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js"></script>
        <script type="text/javascript">
		history.pushState(null, null,"/m/mypage/prvCommData.do");
		window.onpopstate = function (event){
			history.go("/m/mypage/prvCommData.do");
		}
	    </script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
        <input type="hidden" id="isAuth" name="isAuth">
	  	<input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

		<div class="ly-content" id="main-content">
	      <!--include ../_includes/nav.pug-->
	      <div class="ly-page-sticky">
	        <h2 class="ly-page__head">
	        	통신이용자정보 제공내역 조회요청
	        	<button class="header-clone__gnb"></button>
	        </h2>
	      </div>
		  <div class="callhistory-info">
		      <div class="callhistory-info__list">
		          <h3>통신이용자정보 제공내역 조회요청이란?</h3>
					<ul>
					  <li>최근 1년이내 수사기관에서 고객님의 정보(성함+주소+핸드폰번호 등)을 제공했는지 여부를 확인할 수 있는 서비스입니다.</li>
			          <li>정보통신망 이용촉진 및 정보보호에 관한법률에 의거 통신이용자정보 제공사실은 본인인증 후 열람신청 가능합니다.</li>
					</ul>
		      </div>
			  <div class="callhistory-guide">
				<h3>신청절차</h3>
				<ul class="callhistory-guide__list">
				  <li class="callhistory-guide__item">
					<strong><span class="callhistory-guide__step">1</span>고객님</strong>
					<p>통신이용자정보 제공내역 조회요청</p>
				  </li>
				  <li class="callhistory-guide__item">
					<strong><span class="callhistory-guide__step">2</span>KT엠모바일</strong>
					<p>수사기관 제공내역 확인</p>
				  </li>
				  <li class="callhistory-guide__item">
					<strong><span class="callhistory-guide__step">3</span>KT엠모바일→고객님</strong>
					<p>홈페이지 내 결과 등록<br/> 및 SMS를 통해 안내</p>
				  </li>
				  <li class="callhistory-guide__item">
					<strong><span class="callhistory-guide__step">4</span>고객님</strong>
					<p>결과확인 및 출력</p>
				  </li>
				</ul>
			  </div>
	      </div>
		  <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
		  <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>

		  <div class="chgName-info__list">
		    <h3>주의사항</h3>
		    <ul>
		      <li>WEB/APP을 통한 통신이용자정보 제공사실 열람신청은 사용중인 고객만 제공되며 본인회선만 신청 가능합니다.</li>
	          <li>통신이용자정보 제공사실 열람은 수사기관에 가입자 정보를 제공했는지 여부를 확인하기 위한 서비스입니다.</li>
	          <li>통신이용자정보 제공사실 열람은 정회원 고객님이 본인인증 후 이용가능합니다.</li>
		    </ul>
		  </div>

          <div class="c-form">
	          <span class="c-form__title" id="radC">통신이용자정보 제공내용 조회요청 본인인증 동의</span>
	          <div class="c-agree u-mt--0">
	            <input class="c-checkbox" id="clauseSimpleOpen" type="checkbox" >
	            <label class="c-label" for="clauseSimpleOpen">본인인증 절차 동의</label>
	          </div>
	      </div>

		  <!-- 본인인증 방법 선택 -->
		  <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
		  	  <jsp:param name="controlYn" value="N"/>
			  <jsp:param name="reqAuth" value="CNAT"/>
		  </jsp:include>

	 	  <div class="c-form" id="divDataList" style="display: none;">
	          <span class="c-form__title">통신이용자정보 제공내역 조회결과</span>
			  <div class="chgName-info__list" style="border-top: 0.0625rem solid #eaeaea;">
			    <h3>주의사항</h3>
			    <ul>
			      <li>WEB/APP을 통한 통신이용자정보 제공사실 열람신청은 사용중인 고객만 제공되며 본인회선만 신청 가능합니다.</li>
		          <li>통신이용자정보 제공사실 열람은 수사기관에 가입자 정보를 제공했는지 여부를 확인하기 위한 서비스입니다.</li>
		          <li>통신이용자정보 제공사실 열람은 정회원 고객님이 본인인증 후 이용가능합니다.</li>
			    </ul>
			  </div>
	          <div class="c-table" style="margin: 2rem 0 0.75rem;">
		        <table>
		          <caption>구분, 신청일자, 처리결과, 제공여부</caption>
		          <colgroup>
		            <col style="width: 10%">
		            <col style="width: 20%">
		            <col style="width: 20%">
		            <col style="width: 20%">
		          </colgroup>
		          <thead>
		            <tr>
		              <th scope="col">구분</th>
		              <th scope="col">신청일자</th>
		              <th scope="col">처리결과</th>
		              <th scope="col">제공여부</th>
		            </tr>
		          </thead>
		          <tbody id="dataList">
		          	<tr>
		            	<td colspan="4">내역이 존재하지 않습니다.</td>
		            </tr>
		          </tbody>
		        </table>
	    	  </div>
    	  </div>
		  <div class="c-button-wrap u-mt--40" style="display: none;" id="divBtnReq">
            <button class="c-button c-button--lg c-button--primary c-button--full" id="btnRequest">조회요청</button>
          </div>
		</div>
	</t:putAttribute>
</t:insertDefinition>