<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<script type="text/javascript">




</script>
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="inquiry-title">결합내역 조회</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <h3 class="c-heading c-heading--type2 u-mb--12">결합 중인 회선</h3><!-- [2021-12-02] 간격 유틸클래스 삭제 -->
          <div class="c-table">
            <table>
              <caption>결합 중인 회선</caption>
              <colgroup>
                <col style="width: 33%">
                <col style="width: 33%">
                <col style="width: 33%">
              </colgroup>
              <thead>
                <tr>
                  <th scope="col">주기 가능 총 회선 수</th>
                  <th scope="col">현재 결합 회선 수</th>
                  <th scope="col">주기 가능 회선 수</th>
                </tr>
              </thead>
              <tbody>
               <c:choose>
	              	<c:when test="${!empty  moscCombStatMgmtInfoOutVO.outWireDtoVo}">
	              		<tr>
		              		<td>${moscCombStatMgmtInfoOutVO.outWireDtoVo.sbscPsblTotCnt}</td>
		  	              	<td>${moscCombStatMgmtInfoOutVO.outWireDtoVo.sbscBindNowCnt}</td>
		  	  	            <td>${moscCombStatMgmtInfoOutVO.outWireDtoVo.sbscBindRemdCnt}</td>
		              	</tr>
	              	</c:when>
              		<c:otherwise>
	              		<tr>
	              			<td colspan="3">회선 정보가 없습니다.</td>
	              		</tr>
              		</c:otherwise>
             	</c:choose>
              </tbody>
            </table>
          </div>
          <h3 class="c-heading c-heading--type2 u-mb--12">결합 해지 내역</h3>
          <p class="c-bullet c-bullet--fyr u-co-gray">최근 3개월 이내 데이터 받기 서비스가 해지된 이력이 있을 경우 데이터 받기 대상요금제로 변경하더라도 데이터 받기 결합신청 불가합니다.</p>
          <div class="c-table u-mt--16">
            <table>
              <caption>결합 중인 회선</caption>
              <colgroup>
                <col style="width: 50%">
                <col style="width: 50%">
              </colgroup>
              <thead>
                <tr>
                  <th scope="col">서비스 번호</th>
                  <th scope="col">결합 일시</th>
                </tr>
              </thead>
              <tbody>
                <c:choose>
               	<c:when test="${!empty moscCombStatMgmtInfoOutVO.outGiveDtoList}">
               		<c:forEach items="${moscCombStatMgmtInfoOutVO.outGiveDtoList}" var="outGiveDtoList">
               			<c:choose>
               			 	<c:when test="${'결합' eq  outGiveDtoList.bindStatus}">
               			 		<tr>
               			 			<td>${outGiveDtoList.rcvSvcNo}</td>
               			 			<td>${outGiveDtoList.efctStDt}</td>
               			 		</tr>
               			 	</c:when>
               			 	<c:otherwise>
               			 		<tr>
	                				<td colspan="2">회선 정보가 없습니다.</td>
	                			</tr>
               			 	</c:otherwise>
               			</c:choose>
               		</c:forEach>
               	</c:when>
               	<c:otherwise>
              			<tr>
           				<td colspan="2">회선 정보가 없습니다.</td>
           			</tr>
               	</c:otherwise>
               </c:choose>
              </tbody>
            </table>
          </div>
        </div>
      </div>

