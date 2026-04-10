<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
<script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
<script type="text/javascript" src="/resources/js/common/validator.js"></script>
<script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
<script type="text/javascript" src="/resources/js/mobile/popup/maskingPop.js"></script>

<input type="hidden" id="isAuth" name="isAuth">
<div class="c-modal__content masking-pop">
    <div class="c-modal__header">
      <h1 class="c-modal__title" id="total_search_title">가려진(*) 정보보기</h1>
      <button class="c-button" data-dialog-close>
        <i class="c-icon c-icon--close" id="closePopup" aria-hidden="true"></i>
        <span class="c-hidden">팝업닫기</span>
      </button>
    </div>
    <div class="c-modal__body overflow-x-hidden">
        <div class="c-box c-box--type6 u-mt--0">
            <p>(*)으로 보안 처리되어 있는 고객 정보를 본인 인증 후 확인하실 수 있습니다.</p>
        </div>
        <h4 class="c-form__title--type2">보안해제 정보</h4>
        <p>(*)로 표시된 이름, 전화번호, 아이디, 이메일, 주소, 카드 만료 기간</p>
        <h4 class="c-form__title--type2">유지시간</h4>
        <p>본인 인증 1회시, <b class="u-co-red">10분 동안 유지</b></p>
        <!-- 본인인증 방법 선택 -->
        <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
            <jsp:param name="controlYn" value="N"/>
            <jsp:param name="reqAuth" value="CNKATX"/>
        </jsp:include>

        <div class="c-notice-wrap">
           <hr>
           <h5 class="c-notice__title">
               <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
               <span>알려드립니다</span>
           </h5>
           <ul class="c-notice__list">
              <li>본인인증 시 제공되는 정보는 해당 인증기관에서 직접 수집하므로 인증 이외의 용도로 이용 또는 저장되지 않습니다.</li>
           </ul>
         </div>
    </div>
</div>
