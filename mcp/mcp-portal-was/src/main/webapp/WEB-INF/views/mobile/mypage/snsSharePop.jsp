 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/sns.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/mypage/snsSharePop.js"></script>
<%-- </t:putAttribute> --%>

<div class="c-modal c-modal--sm" id="sns-share_dialog" role="dialog" tabindex="-1" aria-labelledby="#modalShareAlertTitle">
   <div class="c-modal__dialog" role="document">
     <div class="c-modal__content">
       <div class="c-modal__body u-ta-center">
         <h3 class="c-heading c-heading--fs22 u-fw--bold" id="modalShareAlertTitle">공유하기</h3>
         <div class="c-button-wrap c-flex u-mt--32">
           <a class="c-button" href="#">
             <span class="c-hidden">카카오</span>
             <i class="c-icon c-icon--kakao" aria-hidden="true" onclick="kakaoShare(); return false;"></i>
           </a>
           <a class="c-button" href="#">
             <span class="c-hidden">line</span>
             <i class="c-icon c-icon--line" aria-hidden="true" onclick="lineShare(); return false;"></i>
           </a>
           <a class="c-button" href="#">
             <span class="c-hidden">URL 복사하기</span>
             <i class="c-icon c-icon--link" aria-hidden="true" onclick="clip(); return false;"></i>
           </a>
         </div>
         <div class="c-button-wrap u-mt--48">
           <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
         </div>
       </div>
     </div>
   </div>
 </div>
