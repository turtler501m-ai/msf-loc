<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

  <t:putAttribute name="titleAttr">납부방법 변경</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/personal/portal/pPaywayInfo.js?version=${jsver}"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">개인화 URL (납부방법 변경)</h2>
      </div>

      <div class="c-row c-row--lg">
        <%@ include file="/WEB-INF/views/personal/portal/pCommCtn.jsp" %>
        <hr class="c-hr c-hr--title">
        <div class="c-flex--between">
          <h4 class="c-heading c-heading--fs20">이용중인 납부방법</h4>
          <button type="button" id="virtualAccountButton" class="c-button--underline u-co-sub-4" onclick="openVirtualAccountListPop();">납부 가상계좌 조회</button>
        </div>
        <div class="c-table pay-method u-mt--16">
          <table>
            <caption>납부방법, 납부계정정보, 명세서유형, 납부기준일로 구성된 납부방법 정보</caption>
            <colgroup>
              <col style="width: 10rem">
              <col>
              <col style="width: 10rem">
              <col>
            </colgroup>
            <tbody>
            <c:choose>
              <c:when test="${payMethod eq '자동이체'}">
                <tr>
                  <th class="u-ta-left" scope="row">납부방법</th>
                  <td class="u-ta-left">${payMethod}</td>
                  <th class="u-ta-left" scope="row">납부일자</th>
                  <td class="u-ta-left">${billCycleDueDay}</td>
                </tr>
                <tr>
                  <th class="u-ta-left" scope="row">납부계정정보</th>
                  <td class="u-ta-left">${blBankAcctNo}</td>
                  <th class="u-ta-left" scope="row">명세서유형</th>
                  <td class="u-ta-left">${reqType}</td>
                </tr>
                <c:if test="${billTypeCd ne 'MB'}">
                  <tr>
                    <th class="u-ta-left" scope="row">${reqTypeNm}</th>
                    <td class="u-ta-left" colspan="3">${blaAddr}</td>
                  </tr>
                </c:if>
              </c:when>

              <c:when test="${payMethod eq '지로'}">
                <tr>
                  <th class="u-ta-left" scope="row">납부방법</th>
                  <td class="u-ta-left">${payMethod}</td>
                  <th class="u-ta-left" scope="row">납부일자</th>
                  <td class="u-ta-left">${billCycleDueDay}</td>
                </tr>
                <tr>
                  <th class="u-ta-left" scope="row">명세서유형</th>
                  <td class="u-ta-left" colspan="3">${reqType}</td>
                </tr>
                <tr>
                  <th class="u-ta-left" scope="row">${reqTypeNm}</th>
                  <td class="u-ta-left" colspan="3">${blaAddr}</td>
                </tr>
              </c:when>

              <c:when test="${payMethod eq '신용카드'}">
                <tr>
                  <th class="u-ta-left" scope="row">납부방법</th>
                  <td class="u-ta-left">${payMethod}</td>
                  <th class="u-ta-left" scope="row">납부기준일</th>
                  <td class="u-ta-left">${payTmsCd}</td>
                </tr>
                <tr>
                  <th class="u-ta-left" scope="row">카드번호</th>
                  <td class="u-ta-left">${prevCardNo}</td>
                  <th class="u-ta-left" scope="row">명세서유형</th>
                  <td class="u-ta-left">${reqType}</td>
                </tr>
                <tr>
                  <c:if test="${billTypeCd ne 'MB'}">
                    <th class="u-ta-left" scope="row">${reqTypeNm}</th>
                    <td class="u-ta-left" colspan="3">${blaAddr}</td>
                  </c:if>
                </tr>
              </c:when>

              <c:when test="${payMethod eq '간편결제'}">
                <tr>
                  <th class="u-ta-left" scope="row">납부방법</th>
                  <td class="u-ta-left">${payMethod}</td>
                  <th class="u-ta-left" scope="row">납부기준일</th>
                  <td class="u-ta-left">지정일</td>
                </tr>
                <tr>
                  <th class="u-ta-left" scope="row">납부계정정보</th>
                  <td class="u-ta-left">${billCycleDueDay}</td>
                  <th class="u-ta-left" scope="row">명세서유형</th>
                  <td class="u-ta-left">${reqType}</td>
                </tr>
                <tr>
                  <th class="u-ta-left" scope="row">${reqTypeNm}</th>
                  <td class="u-ta-left" colspan="3">${blaAddr}</td>
                </tr>
              </c:when>

              <c:otherwise>
                <tr>
                  <th class="u-ta-left" scope="row">납부방법</th>
                  <td class="u-ta-left">-</td>
                  <th class="u-ta-left" scope="row">납부기준일</th>
                  <td class="u-ta-left">-</td>
                </tr>
                <tr>
                  <th class="u-ta-left" scope="row">명세서유형</th>
                  <td class="u-ta-left" colspan="3">-</td>
                </tr>
              </c:otherwise>
            </c:choose>
            </tbody>
          </table>
        </div>
        <div class="c-button-wrap u-mt--56">
          <a class="c-button c-button--lg c-button--mint c-button--w460" href="javascript:btn_reg()" title="납부방법 변경 페이지 이동">납부방법 변경</a>
        </div>
        <h4 class="c-heading c-heading--fs20 u-mt--64">변경가능한 납부방법 </h4>
        <div class="c-table u-mt--16">
          <table>
            <caption>가능 납부방법, 선택 가능 납부일, 입금 확인일로 구성된 변경가능한 납부방법</caption>
            <colgroup>
              <col>
              <col>
              <col>
            </colgroup>
            <thead>
            <tr>
              <th scope="col">가능 납부방법</th>
              <th scope="col">선택 가능 납부일</th>
              <th scope="col">입금 확인일</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>은행계좌</td>
              <td>매월18일, 21일, 25일</td>
              <td>1~3일 후</td>
            </tr>
            <tr>
              <td>간편결제(카카오/페이코)</td>
              <td>1차(21일), 2차(25일)</td>
              <td>1일 후</td>
            </tr>
            <tr>
              <td>
                신용/체크카드<br/>
                <button class="c-text-link--bluegreen-type2 u-ml--0" onclick="javascript:openPage('pullPopup', '/event/cprtCardBnfitLayer.do?cprtCardCtgCd=PCARD00011', '', 0);">제휴카드 혜택 안내</button>
              </td>
              <td>1차(11일경), 2차(20일경)</td>
              <td>2~4일 후</td>
            </tr>
            </tbody>
          </table>
        </div>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--18">
          <li class="c-text-list__item u-co-gray">상품 가입 명의자의 계좌, 간편결제(카카오페이/페이코) 또는 신용/체크카드로만 변경 가능합니다.</li>
          <li class="c-text-list__item u-co-gray">타인명의의 계좌 또는 신용/체크카드로 변경 하시려면, 고객센터 전화상담을 통해 가능하며, 이 경우에는 납부자(예금주) 신분증, 명의자 신분증, 납부자(예금자)가 서명한 동의서 원본 등 구비서류가 필요합니다.</li>
        </ul>
        <h4 class="c-heading c-heading--fs20 u-mt--48">납부방법 변경 적용 시점</h4>
        <div class="c-table u-mt--16">
          <table>
            <caption>구분, 이번달 적용, 다음달 적용으로 구성된 납부방법 변경 적용 시점 정보</caption>
            <colgroup>
              <col>
              <col>
              <col>
            </colgroup>
            <thead>
            <tr>
              <th scope="col">구분</th>
              <th scope="col">이번달 적용</th>
              <th scope="col">다음달 적용</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>납부 기준일만 변경하는 경우</td>
              <td>매월 3일까지 신청</td>
              <td>매월 3일 이후 신청</td>
            </tr>
            <tr>
              <td>동일한 수단으로 변경하는 경우 <br>(예) 카드&lt;-&gt;카드</td>
              <td>변경하는 납부 기준일로 부터 2일 이전까지<br>신청 시 (영업일 기준)</td>
              <td>변경하는 납부 기준일로 부터 1일 이하로<br>남아있을 경우 (영업일 기준)</td>
            </tr>
            <tr>
              <td>다른 수단으로 변경하는 경우 <br>(예) 카드&lt;-&gt;은행</td>
              <td>이번달 미적용 <br>(기존 납부 방법으로 출금)</td>
              <td>다음달부터 변경된 납부방법으로 적용</td>
            </tr>
            </tbody>
          </table>
        </div>
        <b class="c-flex c-text c-text--fs15 u-mt--60">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </b>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <li class="c-text-list__item u-co-gray">자동납부(계좌/카드)를 이용하시는 경우, 당월 청구금액 납부 시 이중 납부가 될 수 있으니 납부일 및 계좌/카드 거래내역 확인 후 이용하시기 바랍니다.</li>
          <li class="c-text-list__item u-co-gray">입금확인 일자는 은행,신용카드 등 에서 케이티 엠모바일에 납부사실을 통보한 일자로, 납부하신 날짜와 입금확인 일자가 다소 차이가 날 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">납부사실 통보에 소요되는 시간이 각 은행과 신용카드 기관별로 차이가 있기 때문에, 입금확인 일자가 서로 다를 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">미납 요금계에 마이너스(-)로 표기된 경우는 지난달 과납 등으로 발생된 금액입니다.</li>
          <li class="c-text-list__item u-co-gray">상세내역은 최근 6개월까지 요금내역만 제공됩니다.</li>
          <li class="c-text-list__item u-co-gray">미납요금을 납부한 고객일지라도, 명세서 상에 미납요금이 표기되어 있을 수 있습니다. (명세서 상 미납요금은 청구시점의 미납요금이 표기된 것으로 현 시점의 미납요금과 상이할 수 있음)</li>
          <li class="c-text-list__item u-co-gray">납부변경 적용일 동일한 납부방법으로 변경 시 : 최소 3일 전까지 변경시 당월적용(변경 요청 시점이 은행, 카드사로 당월 납부요청이 지난 후 에는 변경한 납부방법이 다음달로 예약)</li>
          <li class="c-text-list__item u-co-gray">다른 납부방법으로 변경 시 : 익월적용</li>
        </ul>
      </div>
    </div>
  </t:putAttribute>
</t:insertDefinition>