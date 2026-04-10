   <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
<script type="text/javascript" src="/resources/js/common/validator.js?version=22.08.18"></script>
<script type="text/javascript" src="/resources/js/mobile/retention/retentionNoticeView.js?version=23.11.24"></script>
<c:set var="nextDay" value="<%=new java.util.Date(new java.util.Date().getTime() + 60*60*24*1000*1L)%>" />
<c:set var="nextDay"><fmt:formatDate value="${nextDay}" pattern="yyyy.MM.dd" /></c:set>

<script type="text/javascript">
var nextDay = '<c:out value="${nextDay}"></c:out>';
var threeMonthLaterDate = '<c:out value="${threeMonthLaterDate}"></c:out>';
</script>
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="term_notice-title">약정만료 알림받기</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true" id="retentionCloseBtn"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="u-mt--24">
            <i class="c-icon c-icon--add-service-gray" aria-hidden="true"></i>
            <h3 class="c-heading c-heading--type1 u-mt--24 u-fw--medium">약정만료 시 알림 받기 신청</h3>
            <hr class="c-hr c-hr--type2 u-mt--0">
            <p class="c-text c-text--type2 u-co-gray">고객님께서 요청하신 전월/익월에 카카오톡 또는 SMS로 안내드립니다.</p>
            <div class="c-form u-mt--48">
              <span class="c-form__title sr-only" id="inpInfo">정보 입력</span>
              <div class="c-form__group" role="group" aria-labelledby="inpInfo">
                <div class="c-form__input input has-value">
                  <input class="c-input" id="customerNm" type="text" name="customerNm" maxlength="30" placeholder="고객명 입력" title="고객명을" >
                  <label class="c-form__label" for="customerNm">고객명</label>
                </div>
                <div class="c-form__input input has-value">
                  <input class="c-input" id="mobileNo" type="number" name="mobileNo" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" title="휴대폰 번호를" >

                  <label class="c-form__label" for="mobileNo">휴대폰</label>
                </div>




                <div class="c-form__input">
                  <input class="c-input" id="birthDate" name="birthDate" type="text" maxlength="8" placeholder="8자리 숫자만 입력 (19701225)" style="ime-mode:active;" >
                  <label class="c-form__label" for="birthDate">생년월일</label>
                </div>

                <div class="c-form__select">
                   <select id="mobileCarrierCd" class="c-select" >
                       <option value="">현재 이용중인 통신사</option>
                       <c:forEach items="${nmcp:getCodeList('MB0015')}" var="carrierObj">
                           <option value="${carrierObj.dtlCd}"  >${carrierObj.dtlCdNm}</option>
                       </c:forEach>
                   </select>
                   <label class="c-form__label" for="mobileCarrierCd">현재 이용중인 통신사</label>
                </div>

                <div class="c-form__select">
                    <select id="mobileCompanyCd" class="c-select" style="height: 4.5rem;">
                        <option value="">현재 이용중인 휴대폰 브랜드</option>
                        <c:forEach items="${nmcp:getCodeList('MB0016')}" var="companyObj">
                            <option value="${companyObj.dtlCd}"  >${companyObj.dtlCdNm}</option>
                        </c:forEach>
                    </select>
                    <label class="c-form__label" for="mobileCompanyCd">현재 이용중인 휴대폰 브랜드</label>
                </div>


                <div class="c-form__input">
                  <input class="c-input" id="mobileModelNm" name="mobileModelNm" type="text" maxlength="30" placeholder="예) 아이폰 13프로" style="ime-mode:active;"  >
                  <label class="c-form__label" for="mobileModelNm">사용중인 휴대폰기종</label>
                </div>








              </div>
            </div>


            <div class="c-form u-mt--8">
              <span class="c-form__title" id="inpG">약정만료 예정일</span>
              <!---->
              <!--[2022-02-18] form ui 변경 -->
              <div class="c-form c-form--datepicker">
                <!---->
                <!-- [2022-02-16] input type 변경 (date -> text)-->
                <div class="c-form__input">
                  <input class="c-input date-input" id="expiryDate" name="expiryDate" type="text" placeholder="YYYY-MM-DD" title="날짜를" readonly>
                  <!--[2022-03-22] hidden type의 input type="date" 추가-->
                  <input class="c-hidden" type="date">
                  <label class="c-form__label" for="expiryDate">약정만료 예정일</label>
                  <button class="c-button">
                    <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                  </button>
                </div>
              </div>
            </div>

            <p class="c-bullet c-bullet--dot u-co-gray">신청 당월로부터 3개월 이내까지만 알림 신청이 가능합니다.</p>
            <div class="c-accordion c-accordion--type5">
              <div class="c-accordion__box" id="accordion5">
                <div class="c-accordion__item">
                  <div class="c-accordion__head">
                    <div class="c-accordion__check">
                      <input class="c-checkbox c-checkbox--type5" id="chkDS" type="checkbox" name="chkDS"  >
                      <label class="c-label" for="chkDS">개인정보 수집이용 동의<span class="u-co-gray">(필수)</span>
                      </label>
                    </div>
                    <button class="c-accordion__button u-ta-right is-active" type="button" aria-expanded="true" data-acc-header="#chkDSP1">
                      <div class="c-accordion__trigger"> </div>
                    </button>
                  </div>
                  <div class="c-accordion__panel expand open" id="chkDSP1">
                    <div class="c-accordion__inside">
                      <ul class="c-text-list c-bullet c-bullet--number">
                        <li class="c-text-list__item u-co-gray">수집 이용의 목적<p class="c-bullet c-bullet--dot u-co-gray">서비스 상품 및 가입안내 등 가입 상담을 위한 정보 제공</p>
                        </li>
                        <li class="c-text-list__item u-co-gray">수집항목<p class="c-bullet c-bullet--dot u-co-gray">고객명, 휴대폰, 생년월일, 현재 이용중인 통신사, 현재 이용중인 휴대폰브랜드, 사용중인 휴대폰 기종, 약정만료예정일</p>
                        </li>
                        <li class="c-text-list__item u-co-gray">이용 및 보유기간<p class="c-bullet c-bullet--dot u-co-gray">약정만료 알림일 3개월 후 자동 파기</p>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="c-button-wrap u-mt--48">
              <button type="button" class="c-button c-button--full c-button--primary" id="retentionConfirm"  disabled="" >확인</button>
            </div>
          </div>
        </div>
      </div>
        <!--
        <script src="/resources/js/mobile/vendor/datepicker.min.js"></script>
        <script>
          document.addEventListener("DOMContentLoaded", function() {
            new Datepicker(document.querySelector("[data-datepicker]"), {
              autohide: true,
              format: "yyyymmdd",
              language: "ko",
            });
          });
        </script>
        -->