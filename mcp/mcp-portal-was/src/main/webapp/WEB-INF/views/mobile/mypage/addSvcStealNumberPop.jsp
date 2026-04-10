<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script type="text/javascript" src="/resources/js/mobile/mypage/addSvcViewPop.js"></script>

	
<div class="c-modal__content">
     <div class="c-modal__header">
       <h1 class="c-modal__title" id="serv-apply3-title">부가서비스 신청</h1>
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
               <td class="u-ta-center">번호도용 차단 서비스</td>
               <td class="u-ta-center">무료</td>
             </tr>
           </tbody>
         </table>
       </div>
       <h3 class="c-heading c-heading--type2 u-mt--40 u-mb--12">번호도용 차단 서비스</h3>
       <div class="c-table c-table--row">
         <table>
           <caption>번호도용 차단 서비스</caption>
           <colgroup>
             <col style="width: 30%">
             <col style="width: 70%">
           </colgroup>
           <tbody>
             <tr>
               <th class="u-ta-left" scope="row">제공받는 자</th>
               <td class="u-ta-left">KISA</td>
             </tr>
             <tr>
               <th class="u-ta-left" scope="row">이용 목적</th>
               <td class="u-ta-left">차단</td>
             </tr>
             <tr>
               <th class="u-ta-left" scope="row">제공 항목</th>
               <td class="u-ta-left">전화번호</td>
             </tr>
             <tr>
               <th class="u-ta-left" scope="row">이용/보유기간</th>
               <td class="u-ta-left">서비스 가입기간(가입일~해지일)동안 이용하고, 요금정산, 요금과오납 등 분쟁 대비를 위해 해지 후 6개월까지 보유할 수 있으며, 요금 미/과납이 있을 경우 해결 시까지 보유(단, 법령에 특별한 규정이 있을 경우 관련 법령에 따라 보관합니다.)</td>
             </tr>
           </tbody>
         </table>
       </div>
       <form name="frm" id="frm">
	      	<input type="hidden" name="rateAdsvcCd" id="rateAdsvcCd" value="${rateAdsvcCd}"/>
	      	<input type="hidden" name="contractNum" id="contractNum" value="${contractNum}"/>
	      	<input type="hidden" name="ftrNewParam" id="ftrNewParam" value=""/>
       </form>
       <div class="c-from u-mt--24">
         <input class="c-checkbox c-checkbox--type2" id="chkAgree" type="checkbox" name="chkAgree" >
         <label class="c-label" for="chkAgree">고객정보를 KISA에 제공하는 것을 동의합니다.</label>
       </div>
       <hr class="c-hr c-hr--type2 u-mt--24">
       <ul class="c-text-list c-bullet c-bullet--dot">
         <li class="c-text-list__item u-co-gray">“번호도용 차단 서비스”는 타 기관으로 고객정보를 제공하여 차단하는 서비스입니다.</li>
         <li class="c-text-list__item u-co-gray">타 기관에 고객정보를 제공하는데 동의하셔야 서비스 이용하실 수 있습니다.</li>
       </ul>
       <div class="c-button-wrap u-mt--48">
         <button class="c-button c-button--full c-button--gray" onclick="btn_cancel();">취소</button>
         <button class="c-button c-button--full c-button--primary" onclick="btn_numberReg();">확인</button>
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
 
