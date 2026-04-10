<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="/resources/js/mobile/mypage/addSvcViewPop.js"></script>

 <div class="c-modal__content">
	
       <div class="c-modal__header">
         <h1 class="c-modal__title" id="serv-apply5-title">부가서비스 신청</h1>
         <button class="c-button" data-dialog-close>
           <i class="c-icon c-icon--close" aria-hidden="true"></i>
           <span class="c-hidden">팝업닫기</span>
         </button>
       </div>
       <div class="c-modal__body">
         <h3 class="c-heading c-heading--type2 u-mt--24 u-mb--12">선택 부가서비스 내역</h3>
         <div class="c-table">
           <table>
             <caption>선택 부가서비스 영역</caption>
             <colgroup>
               <col style="width: 50%">
               <col style="width: 50%">
             </colgroup>
             <thead>
               <tr>
                 <th scope="col">신청 부가서비스 내역</th>
                 <th scope="col">이용요금(VAT)포함</th>
               </tr>
             </thead>
             <tbody>
               <tr>
                 <td class="u-ta-center">요금할인</td>
                 <td class="u-ta-center">1,000~20,000원</td>
               </tr>
             </tbody>
           </table>
         </div>
         <div class="c-item u-mt--40">
           <div class="c-item__title flex-type--between">포인트 현황<span class="c-text c-text--type4 u-ml--auto u-co-gray-7">
            <fmt:parseDate value="${nowData}" var="startDt" pattern="yyyyMMdd"/>
        	<fmt:formatDate value="${startDt}" pattern="yyyy.MM.dd"/> 기준</span>
           </div>
           <div class="banner-balloon u-mt--12 u-mb--56">
             <p>
               <span class="c-text c-text--type5">My 포인트</span>
               <br>
               <b class="c-text c-text--type7" >
               <c:choose>
               	<c:when test="${!empty custPoint.totRemainPoint}">
               		<fmt:formatNumber value="${custPoint.totRemainPoint}" type="number"/>
               	</c:when>
               	<c:otherwise>
               		0
               	</c:otherwise>
               </c:choose>
               P</b>
               <img class="deco-img" src="/resources/images/mobile/content/img_bolloon_banner_05.png" alt="">
             </p>
           </div>
         </div>
         <h3 class="c-heading c-heading--type2 u-mt--40 u-mb--12">할인금액</h3>
         <div class="c-from">
           <input class="c-input numOnly" type="tel" placeholder="최소 1,000P (500P단위 입력)" title="포인트 입력" id="pointAmt" name="pointAmt" maxlength="10"> 
         </div>
         <hr class="c-hr c-hr--type2 u-mt--24">
         <ul class="c-text-list c-bullet c-bullet--dot">
           <li class="c-text-list__item u-co-gray">요금할인 서비스는 보유한 포인트를 사용하여 익월 요금을 할인 받는 서비스입니다.</li>
           <li class="c-text-list__item u-co-gray">후불 요금제에 한정하여 신청 가능합니다.</li>
           <li class="c-text-list__item u-co-gray">할인 금액은 부가세 별도 금액으로 입력 해주세요.</li>
         </ul>
         <form name="frm" id="frm">
	      	<input type="hidden" name="rateAdsvcCd" id="rateAdsvcCd" value="${rateAdsvcCd}"/>
	      	<input type="hidden" name="contractNum" id="contractNum" value="${contractNum}"/>
	      	<input type="hidden" name="ftrNewParam" id="ftrNewParam" value=""/>
	      	<input type="hidden" name="baseVatAmt" id="baseVatAmt" value="${baseVatAmt}"/>
      		<c:set var="today" value="<%=new java.util.Date()%>" />
			<c:set var="nowDate"><fmt:formatDate value="${today}" pattern="yyyy.MM.dd" /></c:set> 
			<input type="hidden" name="now" id="now"   value="<c:out value="${nowDate}"/>"/> 
			<input type="hidden" name="totalPoint"     id="totalPoint" value="${custPoint.totRemainPoint}"/> 
			<input type="hidden" name="couoponPrice"   id="couoponPrice" value=""/> 
      	  </form>
         <div class="c-button-wrap u-mt--48">
           <button class="c-button c-button--full c-button--gray" onclick="btn_cancel();">취소</button>
           <button class="c-button c-button--full c-button--primary" onclick="btn_pointReg();">확인</button>
         </div>
         <hr class="c-hr c-hr--type2 u-mt--40">
         <b class="c-text c-text--type3">
           <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
         </b>
         <ul class="c-text-list c-bullet c-bullet--dot">
           <li class="c-text-list__item u-co-gray">변경하신 부가서비스는 즉시 적용 됩니다.</li>
           <li class="c-text-list__item u-co-gray">변경되는 내용을 모두 확인하시고 확인 혹은 취소를 선택해주세요.</li>
           <li class="c-text-list__item u-co-gray">부가서비스 월 중 신청/해지 시 해당월 월정액 및 무료제공 혜택은 각각 일할 적용 됩니다.</li>
           <li class="c-text-list__item u-co-gray">해지 시 재가입 또는 즉시 재가입이 불가능한 부가서비스가 있으므로 주의 바랍니다.</li>
           <li class="c-text-list__item u-co-gray">부가서비스 별로 실제 적용시점이 다른 경우가 있으니 신청/해지 전 확인하시기 바랍니다.</li>
         </ul>
       </div>
     </div>
   </div>
 </div><!-- [START]-->
  