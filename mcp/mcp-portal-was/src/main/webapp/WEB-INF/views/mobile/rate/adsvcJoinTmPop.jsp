<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
<script type="text/javascript" src="/resources/js/common/validator.js"></script>
<script type="text/javascript" src="/resources/js/mobile/rate/adsvcJoinPop.js"></script>

 <div class="c-modal__content">
	 <div class="c-modal__header">
	 	<c:choose>
	 		<c:when test="${'Y' eq flag}">
	 		       <h1 class="c-modal__title" id="serv-apply-title">부가서비스 변경</h1>
	 		</c:when>
	 		<c:otherwise>
	 			 <h1 class="c-modal__title" id="serv-apply-title">부가서비스 신청</h1>
	 		</c:otherwise>
	 	</c:choose>
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
                  <td class="u-ta-center">불법TM수신차단</td>
                  <td class="u-ta-center">무료</td>
                </tr>
              </tbody>
            </table>
          </div>
          
		  <div class="c-form">
	        <span class="c-form__title">가입할 회선 입력</span>
	        <div class="c-form__input-group">
	            <div class="c-form__input c-form__input-division">
	                <input type="tel" class="c-input--div3 onlyNum" id="txtPhoneF" name="txtPhoneF" value="" maxlength="3" placeholder="숫자입력" onkeyup="nextFocus(this, 3, 'txtPhoneM');">
	                <span>-</span>
	                <input type="tel" class="c-input--div3 onlyNum" id="txtPhoneM" name="txtPhoneM" value="" maxlength="4" placeholder="숫자입력" onkeyup="nextFocus(this, 4, 'txtPhoneL');">
	                <span>-</span>
	                <input type="tel" class="c-input--div3 onlyNum" id="txtPhoneL" name="txtPhoneL" value="" maxlength="4" placeholder="숫자입력">
	                <label class="c-form__label" for="txtPhoneF">휴대폰 번호</label>
	            </div>
	        </div>
	      </div>
          
          <div class="c-box u-mt--24">
            <div class="c-item">
              <div class="c-item__title flex-type--between">
                <span>수신차단 번호 입력</span>
                <span class="u-ml--auto c-text c-text--type4 u-fw--regular u-co-gray" >
                  <span class="u-co-mint" id="numCount" >1</span> / 50
                </span>
              </div>
            </div>
            <ul class="c-list c-list--inp-number addNumber">
              <li class="c-list__item numberAddView">
                <div class="c-input-wrap c-input-wrap--flex u-mt--0">
                  <input class="c-input c-input--h34 u-ta-center onlyNum" id="phone1_1" type="tel" maxlength="3" placeholder="앞자리" title="앞자리 입력">
                  <input class="c-input c-input--h34 u-ta-center onlyNum" id="phone2_1" type="tel" maxlength="4" placeholder="중간자리" title="중간자리 입력">
                  <input class="c-input c-input--h34 u-ta-center onlyNum" id="phone3_1" type="tel" maxlength="4" placeholder="뒷자리" title="뒷자리 입력">
                </div>
<!--                 <button class="c-button" type="button" onclick="btn_del('0')">
                <span class="c-hidden">삭제</span>
                  <i class="c-icon c-icon--delete2" aria-hidden="true"></i>
                </button> -->
              </li>
             
            </ul>
            <div class="c-button-wrap u-mt--24">
              <button class="c-button c-button--sm c-button--white fs-12" onclick="btn_numAdd();">번호추가</button>
            </div>
          </div>
          <hr class="c-hr c-hr--type2">
          <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item u-co-gray">수신차단 번호는 최대 50개까지 설정 가능하며 등록한 번호로 수신되는 음성통화, 문자메시지, 음성사서함 모두 차단합니다.</li>
            <li class="c-text-list__item u-co-gray">각 자리별 입력된 번호를 포함하는 번호는 모두 차단합니다. (자리별 부분 차단 가능)</li>
          </ul>
           <form name="frm" id="frm">
		      	<input type="hidden" name="rateAdsvcCd" id="rateAdsvcCd" value="${rateAdsvcCd}"/>
		      	<input type="hidden" name="contractNum" id="contractNum" value="${contractNum}"/>
		      	<input type="hidden" name="ftrNewParam" id="ftrNewParam" value=""/>
       	  </form>
          <div class="c-button-wrap u-mt--48">
            <button class="c-button c-button--full c-button--gray" onclick="btn_cancel();">취소</button>
            <button class="c-button c-button--full c-button--primary" onclick="btn_tmReg();">확인</button>
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
