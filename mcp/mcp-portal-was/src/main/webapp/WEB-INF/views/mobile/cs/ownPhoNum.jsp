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
	   	<script type="text/javascript" src="/resources/js/mobile/cs/ownPhoNum.js"></script>
    	<script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js?version=24.01.11"></script>
        <script type="text/javascript">
		history.pushState(null, null,"/m/cs/ownPhoNum.do");
		window.onpopstate = function (event){   	   				
			history.go("/m/cs/ownPhoNum.do");
		}
	    </script>
	</t:putAttribute>
	
	<t:putAttribute name="contentAttr">
        <input type="hidden" id="isAuth" name="isAuth">

		<div class="ly-content" id="main-content">
	      <!--include ../_includes/nav.pug-->
	      <div class="ly-page-sticky">
	        <h2 class="ly-page__head">
	        	가입번호 조회
	        	<button class="header-clone__gnb"></button>
	        </h2>
	      </div>
		  <div class="callhistory-info">
		      <div class="callhistory-info__list">
		        <h3>가입번호 조회 서비스란?</h3>
				<ul>
		          <li>신용카드 등 본인인증을 통해 전체 핸드폰 번호를 조회하는 서비스 입니다.</li>
		          <li>개인고객님만 이용 가능하시며, 법인 및 공공기관 명의 가입자는 이용 불가능합니다.</li>
		          <li>현재 사용중 또는 정지되어 있는 핸드폰 번호만 조회 가능하며, 해지 된 번호는 확인 할 수 없습니다.</li>
		          <li>어떤 고객님이 사용하시면 좋은가요?
		            <p class="c-bullet c-bullet--hyphen u-co-gray">KT 엠모바일 가입 후 해외출국 등 장기미사용으로 핸드폰 번호가 기억나지 않는 고객님</p>
		            <p class="c-bullet c-bullet--hyphen u-co-gray">셀프개통으로 신규가입하여 번호를 모르는 고객님 등 편히 이용하실 수 있습니다.</p>
		          </li>
				</ul>
		      </div>
	      </div>
		  
          <div class="c-form">
	          <span class="c-form__title" id="radC">가입번호 조회요청 본인인증 동의</span>
	          <div class="c-agree u-mt--0">
	            <input class="c-checkbox" id="clauseSimpleOpen" type="checkbox" >
	            <label class="c-label" for="clauseSimpleOpen">본인인증 절차 동의</label>
	          </div>
	      </div>
 		  <div class="c-form">
	        <span class="c-form__title" id="spOnlineAuthType">실명 및 본인인증</span>
	        <div class="c-form__input">
	          <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" value="" maxlength="60">
	          <label class="c-form__label" for="cstmrName">이름</label>
	        </div>
	      </div>
	      <div class="c-form-field _isDefault">
	        <div class="c-form__input-group">
	          <div class="c-form__input c-form__input-division" >
	            <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                <span>-</span>
                <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" >
                <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
              </div>
	        </div>
	      </div>

		  <!-- 본인인증 방법 선택 -->
		  <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
		  	  <jsp:param name="controlYn" value="N"/>
			  <jsp:param name="reqAuth" value="CNATK"/>
		  </jsp:include>
	 	  
		  <div class="c-button-wrap u-mt--40" style="display: none;" id="divBtnReq">
            <button class="c-button c-button--lg c-button--primary c-button--full" id="btnReq">조회요청</button>
          </div>
		</div>
	</t:putAttribute>
</t:insertDefinition>