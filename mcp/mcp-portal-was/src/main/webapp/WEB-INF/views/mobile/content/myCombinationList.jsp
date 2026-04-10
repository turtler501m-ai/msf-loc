<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script type="text/javascript">




</script>
	<div class="c-modal__content">
		<div class="c-modal__header">
			<h1 class="c-modal__title" id="search-info-title">결합내역 조회</h1>
			<button class="c-button" data-dialog-close>
				<i class="c-icon c-icon--close" aria-hidden="true"></i> <span
					class="c-hidden">팝업닫기</span>
			</button>
		</div>
		 <div class="c-modal__body">
          <h3 class="c-heading c-heading--type2 u-mb--12">결합 중인 회선</h3>
          <c:choose>
          	<c:when test="${empty moscCombDtlResDTO.list}">
	          	 <div class="c-nodata">
		            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
		            <p class="c-noadat__text">조회 결과가 없습니다.</p>
		          </div><!-- [2021-12-02] 간격 유틸클래스 삭제 -->
          	</c:when>
          	<c:otherwise>
          		<div class="c-table">
	          	<table>
	              <caption>결합 현황</caption>
	              <colgroup>
	                <col style="width: 30%">
	                <col style="width: 70%">
	              </colgroup>
	              <tbody>
			         <c:forEach items="${moscCombDtlResDTO.list}" var="combSvcListArr">
						<tr>
		                  <th scope="row">${moscCombDtlResDTO.combProdNm}</th>
		                  <td class="u-ta-left">${combSvcListArr.prodNm}<br/>${combSvcListArr.svcNo}</td>
		                </tr>
			         </c:forEach> 
	              </tbody>
	            </table>
          	</div>
          	</c:otherwise>
          </c:choose>
         
          
          <hr class="c-hr c-hr--type2 u-mt--32">
          <b class="c-text c-text--type3">
            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
          </b>
          <ul class="c-text-list c-bullet c-bullet--dot u-mt--8">
            <li class="c-text-list__item u-co-gray">Kt상품과 kt M모바일 상품 간 결합현황 조회는 kt 고객센터를 통해 MVNO 고객정보제공에 동의한 고객에 한해 제공됩니다.</li>
            <li class="c-text-list__item u-co-gray">Kt상품과 결합 이용 고객 중 결합현황 조회가 되지 않는 고객께서는 kt 고객센터(100번)를 통해 정보제공 동의 후 조회를 부탁드립니다.</li>
        </ul>
       </div>
	</div>

