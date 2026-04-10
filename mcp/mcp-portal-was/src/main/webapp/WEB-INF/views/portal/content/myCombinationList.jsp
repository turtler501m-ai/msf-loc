<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script type="text/javascript">



</script>
<div class="c-modal__content">
   <div class="c-modal__header">
     <h2 class="c-modal__title" id="modalCombLineTitle">결합내역 조회</h2>
     <button class="c-button"  type="button" data-dialog-close>
       <i class="c-icon c-icon--close" aria-hidden="true"></i>
       <span class="c-hidden">팝업닫기</span>
     </button>
   </div>
   <div class="c-modal__body">
    <c:choose>
    	<c:when test="${!empty moscCombDtlResDTO.list}">
    	 <!-- 데이터 있는 경우-->
      <div class="c-table">
        <table>
          <caption>조회 회선, kt 인터넷, kt 모바일, kt M모바일을 포함한 결합 중인 회선 표</caption>
          <colgroup>
            <col style="width: 30%">
            <col>
          </colgroup>
          <tbody class="c-table__left">
      
            <c:forEach items="${moscCombDtlResDTO.list}" var="combSvcListArr">
	 		  <tr>
               <th scope="row">${moscCombDtlResDTO.combProdNm}</th>
               <td>${combSvcListArr.prodNm}<br/>${combSvcListArr.svcNo}</td>
             </tr>
			</c:forEach>
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
    

     <!-- [2022-01-14] 페이지 하단 유의사항 마크업 수정-->
     <h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
       <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
       <span class="u-ml--4">알려드립니다</span>
     </h3>
     <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
       <!-- [$2022-01-14] 페이지 하단 유의사항 마크업 수정-->
       <li class="c-text-list__item u-co-gray">Kt상품과 kt M모바일 상품 간 결합현황 조회는 kt 고객센터를 통해 MVNO 고객정보제공에 동의한 고객에 한해 제공됩니다.</li>
       <li class="c-text-list__item u-co-gray">Kt상품과 결합 이용 고객 중 결합현황 조회가 되지 않는 고객께서는 kt 고객센터(100번)을 통해 정보제공동의 후 조회를 부탁드립니다.</li>
     </ul>
   </div>
 </div>


