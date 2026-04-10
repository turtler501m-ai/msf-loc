<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	function viewPrint(){
 		window.print();

	}
</script>

 <div class="c-modal__content">
   <div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="modalCombLineTitle">청구 요금 인쇄</h2>
          <button class="c-button no-print" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--0">고객사항</h3>
          <div class="c-button-wrap c-button-wrap--right print-btn">
            <button class="c-button c-button--sm c-button--white c-button-round--sm print-btn" onclick="viewPrint();">
              <i class="c-icon c-icon--print" aria-hidden="true"></i>
              <span>인쇄</span>
            </button>
          </div>
          <div class="c-table u-mt--32">
            <table>
              <caption>고객명, 휴대폰 번호를 포함한 고객사항 정보</caption>
              <colgroup>
                <col style="width: 50%">
                <col style="width: 50%">
              </colgroup>
              <thead>
                <tr>
                  <th scope="col">고객명</th>
                  <th scope="col">휴대폰번호</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>${ searchVO.userName }</td>
                  <td>${ searchVO.ctn}</td>
                </tr>
              </tbody>
            </table>
          </div>
         
         <c:set value="${monthPay.billMonth}" var="oDate"/>
		 <c:set value="${fn:substring(oDate,0,6)}" var="parseDate" />
		 <fmt:parseDate value="${parseDate}" pattern="yyyyMM" var="parseDate1"/>
		 <fmt:formatDate value="${parseDate1}" pattern="yyyy년 MM월" var="resultDate"/>
		 <c:set value="${detailInfo.useDate}" var="useDate"/>
          
          <div class="u-flex-between u-mt--48">
            <h3 class="c-heading c-heading--fs20 c-heading--regular">${resultDate} 요금 상세내역</h3>
            <span class="c-text c-text--fs13 u-co-gray">${fn:substring(useDate,0,2)}월${fn:substring(useDate,2,4)}일  ~ ${fn:substring(useDate,5,7)}월${fn:substring(useDate,7,9)}일 기준</span>
          </div>
          <div class="c-table u-mt--32">
            <table>
              <caption>초알뜰500MB/60분, 10원미만할인요금, 부가가치세, 당월요금계, 납부하실 금액, 할인요금, 총 청구금액으로 구성된 요금 상세내역 정보</caption>
              <colgroup>
                <col style="width: 198px">
                <col>
              </colgroup>
              <tbody>
              	<c:forEach items="${ detailInfo.itemList }" var="item" varStatus="staus">
	              <c:if test="${item.splitDescription eq '당월 요금' or item.splitDescription eq '미납요금'}">
	                <tr>
	                  <th class="u-ta-left" scope="row">${ item.splitDescription }</th>
	                  <td class="u-ta-right"><fmt:formatNumber value="${item.actvAmt}" type="number"/> 원</td>
	                </tr>
	              </c:if>
	              <c:choose>
	              	<c:when test="${item.messageLine ne '' and 'DISCBYSVC' ne item.messageLine}">
	              	 <tr>
	                  <th class="u-ta-left" scope="row">${ item.splitDescription }</th>
	                  <td class="u-ta-right"><fmt:formatNumber value="${item.actvAmt}" type="number"/> 원</td>
	                </tr>
	              	</c:when>	
	              	<c:when test="${'할인요금' eq item.splitDescription}">
	              	 <tr>
	                  <th class="u-ta-left" scope="row">${ item.splitDescription }</th>
	                  <td class="u-ta-right"><fmt:formatNumber value="${item.actvAmt}" type="number"/> 원</td>
	                </tr>
	              	</c:when>
	              </c:choose>  
                </c:forEach>
                <c:forEach items="${ detailInfo.itemList }" var="item2" varStatus="staus">
              	  <c:choose>
	              	<c:when test="${'당월요금계' eq item2.splitDescription}">
		              	<tr>
		                  <th class="u-ta-left" scope="row">${ item2.splitDescription }</th>
		                  <td class="u-ta-right"><fmt:formatNumber value="${item2.actvAmt}" type="number"/> 원</td>
		                </tr>
	          		</c:when>
	              	<c:when test="${item2.messageLine eq '' and 'DISCBYSVC' ne item2.messageLine}">
	          			<tr>
		                  <th class="u-ta-left" scope="row">${ item2.splitDescription }</th>
		                  <td class="u-ta-right"><fmt:formatNumber value="${item2.actvAmt}" type="number"/> 원</td>
		                </tr>
	          		</c:when>
	          	 </c:choose>
                </c:forEach>
                <tr>
                  <th class="u-ta-left" scope="row">총 청구금액</th>
                  <td class="u-ta-right"><fmt:formatNumber value="${monthPay.totalDueAmt}" type="number"/>원</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
   </div>
   