<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<div class="c-modal__content card-benefit">
	<div class="c-modal__header">
		<h1 class="c-modal__title" id="search-info-title">혜택 비교하기</h1>
		<button class="c-button" data-dialog-close>
			<i class="c-icon c-icon--close" aria-hidden="true"></i> <span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<ul class="card-benefit__list">
			<c:if test="${!empty prodList}">
			  <c:forEach var="prodInfo" items="${prodList}" varStatus="status">
				<c:set var="bnfitLists1" value="${prodInfo.bnfitLists1}"/>
				<c:choose>
					<c:when test="${fn:length(bnfitLists1) > 1}">
						<c:set var="rowNum" value="${fn:length(bnfitLists1)}"/>
					</c:when>
					<c:otherwise>
						<c:set var="rowNum" value="1"/>
					</c:otherwise>
				</c:choose>
				<li class="card-benefit__item">
					<div class="card-benefit__img">
						<img src="${prodInfo.cprtCardThumbImgNm}" alt="${prodInfo.cprtCardGdncNm} 실물 사진">
						<a href="#" onclick="initCprtCard('${prodInfo.cprtCardCtgCd}'); return false;" title="${prodInfo.cprtCardGdncNm}으로 이동">자세히 보기</a>
					</div>
					<div class="card-benefit__info">
						<strong>${prodInfo.cprtCardGdncNm}</strong>
						<dl>
	                        <dt>
	                        	<span>
	                        		<c:if test="${!empty prodInfo.bnfitLists1}">
										${prodInfo.bnfitLists1.get(0).cprtCardItemNm}
									</c:if>
	                        	</span>
	                       	</dt>
	                        <dd>
	                        	<c:if test="${!empty prodInfo.bnfitLists1}">
									${prodInfo.bnfitLists1.get(0).cprtCardItemDesc}
								</c:if>
	                        </dd>
	                    </dl>
	                    <c:if test="${rowNum > 1}">
							<c:forEach var="bnfit1" items="${prodInfo.bnfitLists1}" begin="1" end="${fn:length(bnfitLists1)}" step="1" varStatus="status">
							<dl>
								<dt>
	                        		<span>
										${bnfit1.cprtCardItemNm}
									</span>
	                       		</dt>
								 <dd>
									${bnfit1.cprtCardItemDesc}
								</dd>
							</dl>
							</c:forEach>
						</c:if>
						<div class="card-benefit__desc">
		                    <p>
		                    	<c:if test="${!empty prodInfo.bnfitLists2}">
									${prodInfo.bnfitLists2.get(0).cprtCardItemDesc}
								</c:if>
		                    </p>
		                </div>
					</div>
				</li>
		      </c:forEach>
		    </c:if>
		</ul>
		<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
	        <li class="c-text-list__item u-co-gray">카드 클릭 시 해당 상세 페이지로 이동합니다.</li>
	        <li class="c-text-list__item u-co-gray u-mt--0">카드사별 자세한 혜택은 제휴카드 상세 페이지에서 확인이 가능합니다.</li>
	    </ul>
	</div>
</div>
