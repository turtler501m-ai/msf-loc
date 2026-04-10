<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="nextDay" value="<%=new java.util.Date(new java.util.Date().getTime() + 60*60*24*1000*1L)%>" />
<c:set var="nextDate"><fmt:formatDate value="${nextDay}" pattern="yyyy.MM.dd" /></c:set>

<script type="text/javascript">
var nextDate = '<c:out value="${nextDate}"></c:out>';
var threeMonthLaterDate = '<c:out value="${threeMonthLaterDate}"></c:out>';
</script>
<script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
<script type="text/javascript" src="/resources/js/common/validator.js?version=22.08.18"></script>
<script type="text/javascript" src="/resources/js/portal/retention/retentionNoticeView.js?version=23.11.24"></script>

    <div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="find-cvs-modal__title">약정만료 알림받기</h2>
          <button id="retentionCloseBtn" class="c-button" data-dialog-close="">
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="etc-guide">
            <i class="c-icon c-icon--end-notice" aria-hidden="true"></i>
            <strong class="etc-guide__title">약정만료 시 알림 받기 신청</strong>
            <p class="etc-guide__text u-co-gray">고객님께서 요청하신 전월/익월에 카카오톡 또는 SMS로 안내드립니다.</p>
          </div>
          <div class="c-form-wrap u-mt--32">
            <div class="c-form-group" role="group" aira-labelledby="inpJoinInfoHead">
              <h3 class="c-group--head c-hidden" id="inpJoinInfoHead">가입정보 입력</h3>
              <div class="c-form-field">
                <div class="c-form__input">
                  <input class="c-input" id="customerNm" name="customerNm" type="text" maxlength="30" placeholder="고객명 입력" style="ime-mode:active;" >
                  <label class="c-form__label" for="customerNm">고객명</label>
                </div>
              </div>

              <div class="c-form-field">
                <div class="c-form__input-group">
                  <div class="c-form__input c-form__input-division">
                    <input class="c-input--div3" name="inpPhoneNum1" id="inpPhoneNum1" type="text" placeholder="숫자입력" title="휴대폰 앞자리 입력" maxlength="3" >
                    <span>-</span>
                    <input class="c-input--div3" name="inpPhoneNum2" id="inpPhoneNum2" type="text" placeholder="숫자입력" title="휴대폰 중간자리 입력" maxlength="4" >
                    <span>-</span>
                    <input class="c-input--div3" name="inpPhoneNum3" id="inpPhoneNum3" type="text" placeholder="숫자입력" title="휴대폰 뒷자리 입력" maxlength="4" >
                    <label class="c-form__label" for="inpPhoneNum">휴대폰</label>
                  </div>
                </div>
              </div>

              <div class="c-form-field">
                <div class="c-form__input">
                  <input class="c-input" id="birthDate" name="birthDate" type="text" maxlength="8" placeholder="8자리 숫자만 입력 (19701225)" style="ime-mode:active;" >
                  <label class="c-form__label" for="birthDate">생년월일</label>
                </div>
              </div>

              <div class="c-form-field">
                <div class="c-form__select">
                   <select id="mobileCarrierCd" class="c-select">
                       <option value="">현재 이용중인 통신사</option>
                       <c:forEach items="${nmcp:getCodeList('MB0015')}" var="carrierObj">
                           <option value="${carrierObj.dtlCd}"  >${carrierObj.dtlCdNm}</option>
                       </c:forEach>
                   </select>
                   <label class="c-form__label" for="mobileCarrierCd">현재 이용중인 통신사</label>
                </div>
              </div>


              <div class="c-form-field">
                <div class="c-form__select">
                    <select id="mobileCompanyCd" class="c-select">
                        <option value="">현재 이용중인 휴대폰 브랜드</option>
                        <c:forEach items="${nmcp:getCodeList('MB0016')}" var="companyObj">
                            <option value="${companyObj.dtlCd}"  >${companyObj.dtlCdNm}</option>
                        </c:forEach>
                    </select>
                    <label class="c-form__label" for="mobileCompanyCd">현재 이용중인 휴대폰 브랜드</label>
                </div>
              </div>

              <div class="c-form-field">
                <div class="c-form__input">
                  <input class="c-input" id="mobileModelNm" name="mobileModelNm" type="text" maxlength="30" placeholder="예) 아이폰 13프로" style="ime-mode:active;"  >
                  <label class="c-form__label" for="mobileModelNm">사용중인 휴대폰기종</label>
                </div>
              </div>

              <div class="c-form-field c-form-field--datepicker">
                <div class="c-form__input has-value">
                  <input class="c-input flatpickr-input" id="expiryDate" name="expiryDate" type="text" title="날짜를" placeholder="YYYY.MM.DD" readonly="readonly"  >
                  <label class="c-form__label" for="expiryDate">약정만료예정일</label>
                  <span class="c-button">
                    <span class="c-hidden">날짜선택</span>
                    <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                  </span>
                </div>
              </div>

            </div>
            <p class="c-bullet c-bullet--dot u-co-gray u-mt--16">신청 당월로부터 3개월 이내까지만 알림 신청이 가능합니다.</p>

          </div>
          <div class="c-form-wrap u-mt--40">
            <div class="c-agree c-agree--type2">
              <div class="c-agree__item">
                <input class="c-checkbox" id="chkDS" type="checkbox" name="chkDS" >
                <label class="c-label" for="chkDS"> 개인정보 수집이용 동의<span class="u-co-gray">(필수)</span>
                </label>
              </div>
              <div class="c-agree__inside">
                <ul class="c-text-list c-bullet c-bullet--number">
                  <li class="c-text-list__item u-mt--0">수집 이용의 목적<p class="c-bullet c-bullet--dot u-co-gray">서비스 상품 및 가입안내, 법인 신용평가 등급 등의 가입 상담을 위한 정보 제공</p>
                  </li>
                  <li class="c-text-list__item u-mt--16">수집항목<p class="c-bullet c-bullet--dot u-co-gray">고객명, 휴대폰, 생년월일, 현재 이용중인 통신사, 현재 이용중인 휴대폰브랜드, 사용중인 휴대폰 기종, 약정만료예정일</p>
                  </li>
                  <li class="c-text-list__item u-mt--16">이용 및 보유기간<p class="c-bullet c-bullet--dot u-co-gray">약정만료 알림일 3개월 후 자동 파기</p>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="c-modal__footer">
          <div class="c-button-wrap">
            <button id="retentionConfirm" class="c-button c-button--lg c-button--primary c-button--w460" disabled=""  >확인</button>
          </div>
        </div>
      </div>
