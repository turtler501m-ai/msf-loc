<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

  <t:putAttribute name="titleAttr">소액결제 조회 및 한도변경</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.06.10"></script>
    <script type="text/javascript" src="/resources/js/personal/portal/pMpayView.js?version=25.06.10"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">개인화 URL (소액결제 조회 및 한도변경)</h2>
      </div>

      <div class="c-row c-row--lg">
        <%@ include file="/WEB-INF/views/personal/portal/pCommCtn.jsp" %>

        <hr class="c-hr c-hr--title">
        <h4 class="c-heading c-heading--fs16">소액결제 이용한도</h4>
        <div class="c-table u-mt--16">
          <table>
            <caption>이번달 소액결제 한도, 이번 달 결제금액, 이번달 잔여한도 으로 구성된 소액결제 이용한도 정보</caption>
            <colgroup>
              <col style="width: 33.33%">
              <col style="width: 33.33%">
              <col>
            </colgroup>
            <thead>
            <tr>
              <th scope="col">이번달 소액결제 한도</th>
              <th scope="col">이번 달 결제금액</th>
              <th scope="col">이번달 잔여한도</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td id="tmonLmtAmt">0원</td>
              <td id="nowMonthAmt">0원</td>
              <td id="rmndLmtAmt">0원</td>
            </tr>
            </tbody>
          </table>
          <ul class="c-text-list c-bullet c-bullet--fyr">
            <li class="c-text-list__item u-co-mint">소액결제 사용 및 한도 변경을 위해서는 최초 1회 이용동의가 필요합니다.</li>
            <li class="c-text-list__item u-co-mint"><span class="u-co-red c-text--fs17">이용동의 및 한도 변경은 PASS앱에서 가능합니다.<br/>
                [PASS 앱에서 설정 방법] PASS 앱 로그인 > 전체 > 결제 > 휴대폰결제 > 한도변경하기 > 결제한도에서 ‘차단(0원)’ 또는 원하는 금액 선택</span></li>
          </ul>
        </div>

        <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--70">이용내역 조회</h3>
        <hr class="c-hr c-hr--title">
        <div class="u-flex-between u-mt--40">
          <div class="c-select-search u-mt--m12">
            <div class="c-form--line">
              <label class="c-label c-hidden" for="monthTitle">이용량 월 선택</label>
              <input type="hidden" name="useMonth" value="">
              <select class="c-select" id="monthTitle" name="datalist">
                <c:forEach items="${dateList}" var="date" varStatus="status">
                  <option value="${date.monthTitle}" <c:if test="${status.index eq 0}">selected</c:if>>
                    ${fn:substring(date.monthTitle,0,4)}년 ${fn:substring(date.monthTitle,4,6)}월
                  </option>
                </c:forEach>
              </select>
            </div>
            <div class="c-form--line u-ml--4">
              <label class="c-label c-hidden" for="mPayStatus">소액결제 상태 선택</label>
              <select class="c-select" id="mPayStatus" name="datalist" style="text-align:center;">
                <option value= "ALL" selected>전체</option>
                <c:forEach items="${mPayStatusCd}" var="item">
                  <option value="${item.dtlCd}">
                    ${item.dtlCdNm}
                  </option>
                </c:forEach>
              </select>
            </div>
            <button class="c-button c-button--search2 c-button--white u-mt--6" type="button" onclick="searchMpayHistory();">조회</button>
          </div>
          <span id="datePeriod">
            <c:forEach items="${dateList}" var="date" varStatus="status">
              <span class="u-co-gray" name="firstLastDay" data-month="${date.monthTitle}" <c:if test="${status.index ne 0}">style="display:none"</c:if>>
                ${date.monthFirstDay} ~ ${date.monthLastDay}
              </span>
            </c:forEach>
          </span>
        </div>

        <div class="total-charge u-mt--20">
          <dl class="total-charge__price">
            <dt class="total-charge__title">총 이용금액</dt>
            <dd class="total-charge__text">
              <b id="selMonthAmt">0</b> 원
            </dd>
          </dl>
        </div>

        <ul class="c-text-list c-bullet c-bullet--fyr u-mt--8">
          <li class="c-text-list__item u-co-sub-4">소액결제 이용내역은 최근 13개월 이내까지 가능합니다. (2022년 7월 이전 내역은 조회 불가)</li>
        </ul>

        <div class="c-table u-mt--48">
          <table class="micropay">
            <caption>이용업체명, 이용서비스, 구매상품, 결제대행사, 결제일시, 금액, 상태로 구성된 이용내역 정보</caption>
            <colgroup>
              <col style="width: 165px">
              <col style="width: 165px">
              <col style="width: 165px">
              <col>
              <col>
              <col>
              <col style="width: 9%">
            </colgroup>
            <thead>
            <tr>
              <th>이용업체명</th>
              <th>이용서비스</th>
              <th>구매상품</th>
              <th>결제대행사</th>
              <th>결제일시</th>
              <th>금액</th>
              <th>상태</th>
            </tr>
            </thead>
            <tbody id="mPayListBody">
              <tr><td colspan="7">내역이 존재하지 않습니다.</td></tr>
            </tbody>
          </table>
        </div>

        <b class="c-flex c-text c-text--fs15 u-mt--48">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </b>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <li class="c-text-list__item u-co-gray">
            소액결제 대행사
            <button class="c-text c-text--underline u-ml--6 u-co-sub-4" data-dialog-trigger="#agency-contact-modal">
              연락처 보기
              <span class="c-hidden">소액결제 대행사 연락처 상세 팝업 열기</span>
            </button>
          </li>
          <li class="c-text-list__item u-co-gray">이용내역조회는 당월포함 13개월까지 가능하며 1개월 단위로 조회 가능합니다. (2022년 7월 이전 내역은 조회 불가)</li>
          <li class="c-text-list__item u-co-gray">소액결제 월 최대 한도는 100만원이며, 고객님의 통신요금 미납 및 연체이력 등에 따라 월 최대 한도가 30만원으로 제한될 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">신규개통시 일정기간 부정사용 피해방지와 고객보호를 위하여 이용한도가 4만원으로 제한 될 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">월 이용한도는 편의에 따라 4만원, 30만원, 50만원, 60만원, 80만원, 100만원 또는 만원단위 금액으로 직접 조정할 수 있지만 통신요금 미납과 연체이력 등에 따라 한도 상향이 제한될 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">안전한 결제를 위해 이용한도 상향시에는 추가적인 동의 절차가 필요합니다.</li>
          <li class="c-text-list__item u-co-gray">
            부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.<br/>예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
          </li>
        </ul>
      </div>
    </div>

    <!-- 소액결제 대행사 연락처 상세 팝업 -->
    <div class="c-modal c-modal--xlg" id="agency-contact-modal" role="dialog" aria-labelledby="agency-contact-modal__title">
      <div class="c-modal__dialog" role="document">
        <div class="c-modal__content">
          <div class="c-modal__header">
            <h2 class="c-modal__title" id="agency-contact-modal__title">소액결제대행사 연락처</h2>
            <button class="c-button" data-dialog-close>
              <i class="c-icon c-icon--close" aria-hidden="true"></i>
              <span class="c-hidden">팝업닫기</span>
            </button>
          </div>
          <div class="c-modal__body">
            <div class="c-table">
              <table>
                <caption>결제대행사, 고객센터 전화번호, 홈페이지 주소로 구성된 소액결제대행사 정보</caption>
                <colgroup>
                  <col style="width: 33.33%">
                  <col style="width: 33.33%">
                  <col>
                </colgroup>
                <thead>
                <tr>
                  <th scope="col">결제대행사</th>
                  <th scope="col">고객센터 전화번호</th>
                  <th scope="col">홈페이지</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                  <td>페이레터</td>
                  <td>1599-2583</td>
                  <td>
                    <a class="c-text c-text--underline u-co-sub-4" href="https://www.payletter.com/ko/" title="페이레터 바로가기(새창)" target="_blank">www.payletter.com</a>
                  </td>
                </tr>
                <tr>
                  <td>모빌리언스</td>
                  <td>1800-0678</td>
                  <td>
                    <a class="c-text c-text--underline u-co-sub-4" href="https://www.mobilians.co.kr/" title="모빌리언스 바로가기(새창)" target="_blank">www.mobilians.co.kr</a>
                  </td>
                </tr>
                <tr>
                  <td>다날</td>
                  <td>1566-3355</td>
                  <td>
                    <a class="c-text c-text--underline u-co-sub-4" href="https://www.danal.co.kr" title="다날 바로가기(새창)" target="_blank">www.danal.co.kr</a>
                  </td>
                </tr>
                <tr>
                  <td>갤럭시아머니트리(주)</td>
                  <td>1566-0123</td>
                  <td>
                    <a class="c-text c-text--underline u-co-sub-4" href="https://www.galaxiamoneytree.co.kr" title="갤럭시아머니트리(주) 바로가기(새창)" target="_blank">www.galaxiamoneytree.co.kr</a>
                  </td>
                </tr>
                <tr>
                  <td>NHN한국사이버결제</td>
                  <td>1688-6410</td>
                  <td>
                    <a class="c-text c-text--underline u-co-sub-4" href="https://www.kcp.co.kr" title="NHN한국사이버결제 바로가기(새창)" target="_blank">www.kcp.co.kr</a>
                  </td>
                </tr>
                <tr>
                  <td>효성FMS</td>
                  <td>1544-5162</td>
                  <td>
                    <a class="c-text c-text--underline u-co-sub-4" href="https://www.hyosungfms.co.kr" title="효성FMS 바로가기(새창)" target="_blank">www.hyosungfms.co.kr</a>
                  </td>
                </tr>
                <tr>
                  <td>세틀뱅크</td>
                  <td>1600-5220</td>
                  <td>
                    <a class="c-text c-text--underline u-co-sub-4" href="https://www.settlebank.co.kr" title="세틀뱅크 바로가기(새창)" target="_blank">www.settlebank.co.kr</a>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="c-modal__footer">
            <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
          </div>
        </div>
      </div>
    </div>
  </t:putAttribute>
</t:insertDefinition>