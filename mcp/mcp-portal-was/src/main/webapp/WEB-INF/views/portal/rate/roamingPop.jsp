<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script src="/resources/js/portal/mypage/regServicePop.js?version=22-05-30"></script>
<script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
<script type="text/javascript" src="/resources/js/portal/rate/roamingPop.js"></script>

<div class="c-modal__content">

    <div class="c-modal__header">
        <h2 class="c-modal__title" id="ChangeProductTitle">로밍상품 신청</h2>
        <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>

    <div class="c-modal__body">
        <p class="u-fw--medium u-fs-16">선택 로밍상품 내역</p>
        <div class="u-mt--16 c-table c-table--product">
            <table>
                <caption>테이블 정보</caption>
                <thead>
                <tr>
                    <th>상품명</th>
                    <th>이용요금(VAT)포함</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${joinPop.rateAdsvcNm}</td>
                        <td>
                            <c:choose>
                                <c:when test="${'Y' eq joinPop.freeYn}">
                                      무료제공
                                  </c:when>
                                  <c:when test="${'Y' ne joinPop.freeYn and '1' eq joinPop.dateType and joinPop.usePrd ne '0' and !empty joinPop.usePrd}">
                                      ${joinPop.mmBasAmtVatDesc}원 / ${joinPop.usePrd}일
                                  </c:when>
                                <c:when test="${'Y' ne joinPop.freeYn and '3' eq joinPop.dateType and '중국/일본 알뜰 로밍' eq joinPop.rateAdsvcNm and joinPop.usePrd ne '0' and !empty joinPop.usePrd}">
                                    ${joinPop.mmBasAmtVatDesc}원 / ${joinPop.usePrd}일
                                </c:when>
                                  <c:otherwise>
                                      ${joinPop.mmBasAmtVatDesc}원
                                  </c:otherwise>
                              </c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>

            <c:choose>
                <c:when test="${joinPop.lineType eq 'G'}">
                    <p class="u-mt--24 u-fw--medium u-fs-16">신청 번호</p>
                </c:when>
                <c:otherwise>
                    <p class="u-mt--24 u-fw--medium u-fs-16">대표 번호</p>
                </c:otherwise>
            </c:choose>
            <div class="c-form__input-group grpRoamNum">
                <div class="c-form__input c-form__input-division" name="roamPhone">
                    <input class="c-input--div3 roamPhone onlyNum" id="roamPhone1" type="text" pattern="[0-9]" placeholder="숫자입력" title="연락처 앞자리 입력" maxlength="3" onkeyup="nextFocus(this, 3, 'roamPhone2');">
                    <span>-</span>
                    <input class="c-input--div3 roamPhone onlyNum" id="roamPhone2" type="text" pattern="[0-9]" placeholder="숫자입력" title="연락처 중간자리 입력" maxlength="4" onkeyup="nextFocus(this, 4, 'roamPhone3');">
                    <span>-</span>
                    <input class="c-input--div3 roamPhone onlyNum" id="roamPhone3" type="text" pattern="[0-9]" placeholder="숫자입력" title="연락처 뒷자리 입력" maxlength="4">
                        <label class="c-form__label" for="roamPhone1">휴대폰 번호</label>
                </div>
            </div>

            <c:if test="${joinPop.dateType eq '1' or joinPop.dateType eq '2' or joinPop.dateType eq '3' or joinPop.dateType eq '4'}">
                <p class="u-mt--24 u-fw--medium u-fs-16 date">이용기간 설정(한국시간 기준)</p>
                 <div class="c-form-row c-form-wrap--time u-mt--16 c-col2 date">
                    <div class="c-form-field c-form-field--datepicker">
                        <div class="c-form__input u-mt--0">
                            <input class="c-input flatpickr flatpickr-input" id="useStartDate" type="text" placeholder="YYYY-MM-DD" readonly="readonly">
                            <label class="c-form__label" for="useStartDate">시작일</label>
                            <span class="c-button c-button--calendar">
                         <span class="c-hidden">날짜선택</span>
                         <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                       </span>
                        </div>
                    </div>

                    <div class="c-form">
                      <label class="c-label c-hidden" for="startTime">시작시간 선택</label>
                      <select class="c-select c-select--time" id="startTime" name="startTime">
                        <option label="시작시간선택" disabled selected>시작시간 선택</option>
                           <c:forEach var="i"  begin="0" end="23">
                            <option value="${i>9?i:'0'}${i>9?'':i}">${i>9?i:'0'}${i>9?'':i}시</option>
                        </c:forEach>
                      </select>
                    </div>
                 </div>

                 <div class="c-form-row c-form-wrap--time u-mt--12 c-col2 date">
                    <div class="c-form-field c-form-field--datepicker date">
                        <div class="c-form__input u-mt--0 useEndDate">
                            <input class="c-input flatpickr flatpickr-input" id="useEndDate" type="text" placeholder="YYYY-MM-DD" readonly="readonly">
                            <label class="c-form__label" for="useEndDate">종료일</label>
                            <span class="c-button c-button--calendar">
                             <span class="c-hidden">날짜선택</span>
                             <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                           </span>
                        </div>
                    </div>
                    <div class="c-form" style="width: 9.545rem;">
                    </div>
                </div>
            </c:if>

            <c:choose>
                <c:when test="${joinPop.lineType eq 'M'}">
                    <p class="u-mt--24 u-fw--medium u-fs-16 addPhoneTitle">서브 번호<span class="u-fw--medium u-fs-14 addPhoneDesc"> (데이터를 함께 이용하실 추가 고객 등록)</span></p>
                    <ul class="u-mt--0 c-text-list c-bullet c-bullet--dot">
                        <li class="u-mt--4 c-text-list__item">추가 고객은 kt M모바일 사용 휴대폰 번호에 한해 최대 ${joinPop.lineCnt}개까지 등록 가능합니다.</li>
                        <li class="u-mt--4 c-text-list__item" id="subRoamName">추가 등록된 고객님도 '로밍 데이터 함께ON(서브)'를 별도 가입하셔야합니다.</li>
                      </ul>
                </c:when>
                <c:when test="${joinPop.lineType eq 'S'}">
                    <p class="u-mt--24 u-fw--medium u-fs-16 addPhoneTitle">서브 번호<span class="u-fw--medium u-fs-14 addPhoneDesc"> (신청자 본인 회선 입력)</span></p>
                </c:when>
            </c:choose>

            <div id="addPhone">

            </div>

        </div>
        <b class="c-flex c-text c-text--fs15 u-mt--48">
            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
            <span class="u-ml--4">알려드립니다</span>
        </b>
        <c:choose>
            <c:when test="${joinPop.lineType eq 'M'}">
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                    <li class="c-text-list__item u-co-gray">데이터/음성 로밍 차단 상품에 가입되어 있으면 해당 상품을 먼저 해지하셔야 합니다.</li>
                    <li class="c-text-list__item u-co-gray">신청 후 데이터 미 사용 시, 요금은 청구되지 않습니다.</li>
                    <li class="c-text-list__item u-co-gray">서브회선의 신청/가입 여부는 대표회선에서 확인이 불가합니다. 서브회선 이용 고객에게 확인 바랍니다.</li>
                    <li class="c-text-list__item u-co-gray">서브회선 고객님이 ‘데이터 함께ON’ 서브 상품에 별도로 가입해야 무료 적용이 가능합니다.</li>
                    <li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
                </ul>
            </c:when>
            <c:when test="${joinPop.lineType eq 'S'}">
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                    <li class="c-text-list__item u-co-gray">데이터/음성 로밍 차단 상품에 가입되어 있으면 해당 상품을 먼저 해지하셔야 합니다.</li>
                    <li class="c-text-list__item u-co-gray">데이터 함께ON 대표회선 고객님이 지정하신 고객님만 신청 가능합니다.</li>
                    <li class="c-text-list__item u-co-gray">대표 번호 고객님이 서브 번호 고객님을 사전 등록했더라도 서브 회선 고객님이 ‘데이터 함께ON’ 서브 상품에 별도로 가입해야 무료 적용이 가능합니다.</li>
                    <li class="c-text-list__item u-co-gray">신청 후 데이터 미 사용 시, 요금은 청구되지 않습니다.</li>
                    <li class="c-text-list__item u-co-gray">대표회선 고객님께서 설정한 기간 내에서 이용이 가능합니다.</li>
                    <li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
                </ul>
            </c:when>
            <c:when test="${joinPop.lineType eq 'G'}">
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                    <li class="c-text-list__item u-co-gray">신청 후 데이터 또는 음성 이용 시 요금 전액이 청구 됩니다.</li>
                    <li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
                </ul>
            </c:when>
        </c:choose>
    </div>
<form name="frm" id="frm">
    <input type="hidden" id="dateType" value="${joinPop.dateType}">
    <input type="hidden" id="lineType" value="${joinPop.lineType}">
    <input type="hidden" id="usePrd" value="${joinPop.usePrd}">
    <input type="hidden" id="lineCnt" value="${joinPop.lineCnt}">
    <input type="hidden" id="rateAdsvcGdncSeq" value="${joinPop.rateAdsvcGdncSeq}">
    <input type="hidden" id="rateAdsvcNm" value="${joinPop.rateAdsvcNm}"/>
    <input type="hidden" id="rateAdsvcCd" value="${prodRel.rateAdsvcCd}"/>
    <input type="hidden" id="reqLineCnt" value="0"/>
</form>
    <div class="c-modal__footer c-button-wrap u-mt--24">
        <button class="c-button c-button--lg c-button--gray u-width--220"  type="button" onclick="btn_cancel();" data-dialog-close >취소</button>
        <button class="c-button c-button--lg c-button--primary u-width--220" id="checkBtn"  type="button" disabled >확인</button>
    </div>

</div>
