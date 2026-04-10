<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script type="text/javascript">



</script>
	<div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="modalTogetherbLineTitle">결합내역 조회</h2>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <h3 class="c-heading c-heading--fs20 u-mb--40">결합 중인 회선</h3><!-- 데이터 있는 경우-->
          	 <c:choose>
	            <c:when test="${!empty moscCombStatMgmtInfoOutVO.outGiveDtoList or !empty moscCombStatMgmtInfoOutVO.outRcvDtoList}"> 
		          <div class="c-table">
		            <table>
		              <caption>서비스 번호, 결합 일시를 포함한 결합 중인 회선 정보</caption>
		              <colgroup>
		                <col style="width: 50%">
		                <col>
		              </colgroup>
		              <thead>
		                <tr>
		                  <th scope="col">서비스 번호</th>
		                  <th scope="col">결합 일시</th>
		                </tr>
		              </thead>
		              <tbody>
		               <c:if test ="${!empty  moscCombStatMgmtInfoOutVO.outGiveDtoList}">
		             		<c:forEach items="${moscCombStatMgmtInfoOutVO.outGiveDtoList}" var="outGiveDtoList">
			             			<c:if test ="${'결합' eq outGiveDtoList.bindStatus}">
				             			<tr>
						                  <td>${outGiveDtoList.rcvSvcNo}</td>
						                  <td>${outGiveDtoList.efctStDt}</td>
						                </tr>
			             			</c:if>
			             	</c:forEach>
	             		</c:if>
	             		<c:if test ="${!empty  moscCombStatMgmtInfoOutVO.outRcvDtoList}">
			             	<c:forEach items="${moscCombStatMgmtInfoOutVO.outRcvDtoList}" var="outRcvDtoList">
			             		<c:if test ="${'결합' eq outRcvDtoList.bindStatus}">
			             			<tr>
					                  <td>${outRcvDtoList.giveSvcNo}</td>
					                  <td>${outRcvDtoList.efctStDt}</td>
					                </tr>
			             		</c:if>
		             		</c:forEach>
		             	</c:if>
		              </tbody>
		            </table>
		          </div><!-- //-->
		       </c:when>
		       <c:otherwise>
			       <!-- 데이터 없는 경우-->
		          <div class="c-nodata">
		            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
		            <p class="c-nodata__text">현재 결합 중인 회선이 없습니다.</p>
		          </div><!-- //-->
		       </c:otherwise>
	          </c:choose>
	
	          <h3 class="c-heading c-heading--fs20 u-mt--40 u-mb--40">결합 해지 내역</h3>
	          <p class="c-bullet c-bullet--fyr u-co-sub-4 u-mb--16"> 최근 3개월 이내 데이터 받기 서비스가 해지된 이력만 조회됩니다.</p><!-- 데이터 있는 경우-->
	       	  <p class="c-bullet c-bullet--fyr u-co-sub-4 u-mb--16"> 최근 3개월 이내 데이터 받기 서비스가 해지된 이력이 있을 경우 데이터 받기 결합신청 불가합니다.</p><!-- 데이터 있는 경우-->
	       
	          <c:choose>
	           	<c:when test="${!empty moscCombStatMgmtInfoOutVO.outGiveDtoList or !empty moscCombStatMgmtInfoOutVO.outRcvDtoList}">
		          <div class="c-table">
		            <table>
		              <caption>서비스 번호, 결합 일시, 해지 일시를 포함한 결합 해지 내역 정보</caption>
		              <colgroup>
		                <col>
		                <col style="width: 33%">
		                <col style="width: 33%">
		              </colgroup>
		              <thead>
		                <tr>
		                  <th scope="col">서비스 번호</th>
		                  <th scope="col">결합 일시</th>
		                  <th scope="col">해지 일시</th>
		                </tr>
		              </thead>
		              <tbody>
		                <c:if test ="${!empty  moscCombStatMgmtInfoOutVO.outGiveDtoList}">
		             		<c:forEach items="${moscCombStatMgmtInfoOutVO.outGiveDtoList}" var="outGiveDtoList">
			             			<c:if test ="${'결합' ne outGiveDtoList.bindStatus}">
				             			<tr>
						                  <td>${outGiveDtoList.rcvSvcNo}</td>
						                  <td>${outGiveDtoList.efctStDt}</td>
						                  <td>${outGiveDtoList.efctFnsDt}</td>
						                </tr>
			             			</c:if>
			             	</c:forEach>
	             		</c:if>
	             		<c:if test ="${!empty  moscCombStatMgmtInfoOutVO.outRcvDtoList}">
			             	<c:forEach items="${moscCombStatMgmtInfoOutVO.outRcvDtoList}" var="outRcvDtoList">
			             		<c:if test ="${'결합' ne outRcvDtoList.bindStatus}">
			             			<tr>
					                  <td>${outRcvDtoList.giveSvcNo}</td>
					                  <td>${outRcvDtoList.efctStDt}</td>
					                  <td>${outRcvDtoList.efctFnsDt}</td>
					                </tr>
			             		</c:if>
		             		</c:forEach>
		             	</c:if>
		              </tbody>
		            </table>
		          </div><!-- //-->
		       </c:when>
		       <c:otherwise>
		        <!-- 데이터 없는 경우-->
		          <div class="c-nodata">
		            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
		            <p class="c-nodata__text">결합 해지 내역이 없습니다.</p>
		          </div><!-- //-->
		       </c:otherwise>
		      </c:choose>
        </div>
      </div>
      
