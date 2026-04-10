<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<c:if test="${!empty prodXmlList}">
	<c:forEach var="prod" items="${prodXmlList}" varStatus="status">
		<ul class="c-card-list accd-card-list">
			<li class="c-card c-card--type2 u-mb--0">
				<a class="c-card__box u-mt--0" onclick="javascript:openPage('pullPopup', '/m/rate/rateLayer.do?rateAdsvcCtgCd=${prod.rateAdsvcCtgCd}&rateAdsvcGdncSeq=${prod.rateAdsvcGdncSeq}&rateAdsvcCd=${prod.rateAdsvcCd}', '');">
			    	<div class="c-card__top c-expand__right"></div>
			        <div class="c-card__title u-mt--0">
			        	<b>${prod.rateAdsvcNm}</b>
			        	<%--c:set var="rateAdsvcCd" value="KISUSEV14"></c:set --%>
			        	<span class="c-button c-button--sm u-ml--auto">
			             	<span class="c-hidden">요금제 안내 팝업</span>
			             	<i class="c-icon c-icon--arrow-gray" aria-hidden="true"></i>
			            </span>
			           	<p class="c-card__sub">
			           	    <%-- 요금제혜택(데이터, 음성, 문자) --%>
			           	    <span>
			           	    	데이터 
			           	    	<c:choose>
			           	    		<c:when test="${!empty prod.bnfitData}">
			           	    			${prod.bnfitData}
			           	    			<c:if test="${!empty prod.promotionBnfitData}">(${prod.promotionBnfitData})</c:if>
			           	    		</c:when>
			           	    		<c:otherwise>-</c:otherwise>
			           	    	</c:choose>
			           	    </span>
				            <span>
				            	음성
				            	<c:choose>
			           	    		<c:when test="${!empty prod.bnfitVoice}">
			           	    			${prod.bnfitVoice}
			           	    			<c:if test="${!empty prod.promotionBnfitVoice}">(${prod.promotionBnfitVoice})</c:if>	
			           	    		</c:when>
			           	    		<c:otherwise>-</c:otherwise>
			           	    	</c:choose>
				            </span>
				            <span>
				            	문자
				            	<c:choose>
			           	    		<c:when test="${!empty prod.bnfitSms}">
			           	    			${prod.bnfitSms}
			           	    			<c:if test="${!empty prod.promotionBnfitSms}">(${prod.promotionBnfitSms})</c:if>
			           	    		</c:when>
			           	    		<c:otherwise>-</c:otherwise>
			           	    	</c:choose>
				            </span>
				            <%-- 제휴혜택 노출문구 (RATEBE11) --%>
				            <c:if test="${!empty prod.bnfitList}">
				            	<c:forEach var="bnfit" items="${prod.bnfitList}">
				            		<span>${bnfit.rateAdsvcItemDesc}</span>
           	    				</c:forEach>
				            </c:if>
			           	</p>
			        </div>
			        <%-- 혜택 안내 (RATEBE12) --%>
			        <c:if test="${!empty prod.bnfitList2}">				    	
				        <div class="c-card__detail-info">
	                    	<div class="c-hr c-hr--type3"></div>
	                      	<ul class="c-text-list c-bullet c-bullet--hyphen">
	                      		<c:forEach var="bnfit2" items="${prod.bnfitList2}">
	                        		<li class="c-text-list__item u-co-gray">${bnfit2.rateAdsvcItemDesc}</li>
                   				</c:forEach>
	                      	</ul>
	                    </div>
				    </c:if>
			        <%-- 요금 --%>
			        <div class="c-card__price-box multi-row">
			        	<span class="c-text c-text--type4">월 기본료(VAT 포함)</span>	
		           		<c:choose>
		           			<c:when test="${!empty prod.mmBasAmtVatDesc && !empty prod.promotionAmtVatDesc}">
		           				<span class="c-text u-td-line-through u-ml--auto">${prod.mmBasAmtVatDesc}</span>
		           				<span><b>${prod.promotionAmtVatDesc}</b>원</span>
		           			</c:when>			           		
		           			<c:otherwise>
		           				<span><b>${prod.mmBasAmtVatDesc}</b>원</span>
		           			</c:otherwise>
		           		</c:choose>
					</div>
			         			         
			        <c:if test="${!empty prod.contractAmtVatDesc}">
						<div class="c-card__price-box multi-row">
							<span class="c-text c-text--type4">약정시 기본료(VAT 포함)</span>	
			        		<span><b>${prod.contractAmtVatDesc}</b>원</span>
						</div>
					</c:if>
					<c:if test="${!empty prod.rateDiscntAmtVatDesc}">
						<div class="c-card__price-box multi-row">
							<span class="c-text c-text--type4">약정시 기본료(VAT 포함)</span>	
			        		<span><b>${prod.rateDiscntAmtVatDesc}</b>원</span>
						</div>
					</c:if>
					<c:if test="${!empty prod.seniorDiscntAmtVatDesc}">
						<div class="c-card__price-box multi-row">
							<span class="c-text c-text--type4">약정시 기본료(VAT 포함)</span>	
			        		<span><b>${prod.seniorDiscntAmtVatDesc}</b>원</span>
						</div>
					</c:if>
				</a>
			</li>
		</ul>
	</c:forEach>
</c:if>
