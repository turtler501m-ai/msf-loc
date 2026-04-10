<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<div>
	<c:if test="${!empty prodXmlList}">
		<c:forEach var="prod" items="${prodXmlList}" varStatus="status">
			<c:if test="${status.count % 2 != 0}">
				<div class="product__content">
					<div class="c-card-col c-card-col--2">
			</c:if>
					<a class="c-card c-card--type1" href="#" title="${prod.rateAdsvcNm} 상세 바로가기" href="javascript:void(0);"  onclick="javascript:openPage('pullPopup', '/rate/rateLayer.do?rateAdsvcCtgCd=${prod.rateAdsvcCtgCd}&rateAdsvcGdncSeq=${prod.rateAdsvcGdncSeq}&rateAdsvcCd=${prod.rateAdsvcCd}', '');">
						<i class="c-icon c-icon--arrow-gray-2" aria-hidden="true"></i>
						<div class="c-card__title">
							<strong>${prod.rateAdsvcNm}</strong>

                           <%-- 혜택 안내 (RATEBE12) --%>
				            <c:if test="${!empty prod.bnfitList2}">
				            	<c:forEach var="bnfit2" items="${prod.bnfitList2}">
				            		<p class="c-card__sub">
				            			<c:if test="${!empty bnfit2.rateAdsvcItemNm}">
				            				${bnfit2.rateAdsvcItemNm}
				            			</c:if>
				            			<c:if test="${!empty bnfit2.rateAdsvcItemDesc}">
				            				<br>
				            				${bnfit2.rateAdsvcItemDesc}
	                                	</c:if>
	                                </p>
           	    				</c:forEach>
				            </c:if>
						</div>
						<div class="c-card__content">
							<ul class="product-info">
								<c:if test="${!empty prod.bnfitDataItemImgNm}">
									<li class="product-info__item">
										<img class="item-icon" src="${prod.bnfitDataItemImgNm}" alt="" aria-hidden="true">
										<span class="c-hidden">데이터</span>
										<c:choose>
					           	    		<c:when test="${!empty prod.bnfitData}">
					           	    			${prod.bnfitData}
					           	    			<c:if test="${!empty prod.promotionBnfitData}"><div class="product-info__sub">${prod.promotionBnfitData}</div></c:if>
					           	    		</c:when>
					           	    		<c:otherwise>-</c:otherwise>
					           	    	</c:choose>
									</li>
								</c:if>
								<c:if test="${!empty prod.bnfitVoiceItemImgNm}">
									<li class="product-info__item">
										<img class="item-icon" src="${prod.bnfitVoiceItemImgNm}" alt="" aria-hidden="true">
										<span class="c-hidden">음성</span>
										<c:choose>
					           	    		<c:when test="${!empty prod.bnfitVoice}">
					           	    			${prod.bnfitVoice}
					           	    			<c:if test="${!empty prod.promotionBnfitVoice}"><div class="product-info__sub">${prod.promotionBnfitVoice}</div></c:if>
					           	    		</c:when>
					           	    		<c:otherwise>-</c:otherwise>
					           	    	</c:choose>
									</li>
								</c:if>
								<c:if test="${!empty prod.bnfitSmsItemImgNm}">
									<li class="product-info__item">
										<img class="item-icon" src="${prod.bnfitSmsItemImgNm}" alt="" aria-hidden="true">
										<span class="c-hidden">문자</span>
										<c:choose>
					           	    		<c:when test="${!empty prod.bnfitSms}">
					           	    			${prod.bnfitSms}
					           	    			<c:if test="${!empty prod.promotionBnfitSms}"><div class="product-info__sub">${prod.promotionBnfitSms}</div></c:if>
					           	    		</c:when>
					           	    		<c:otherwise>-</c:otherwise>
					           	    	</c:choose>
									</li>
								</c:if>
								<%-- 제휴혜택 노출문구 (RATEBE11) --%>
			           			<c:if test="${!empty prod.bnfitList}">
			           				<c:forEach var="bnfit" items="${prod.bnfitList}">
	                    				<li class="product-info__item">
		                                	<c:if test="${!empty bnfit.rateAdsvcItemImgNm}">
		                                		<img class="item-icon" src="${bnfit.rateAdsvcItemImgNm}" alt="" aria-hidden="true">
		                                	</c:if>
		                                    <span class="c-hidden">혜택</span>${bnfit.rateAdsvcItemDesc}
		                                </li>
	                    			</c:forEach>
			           			</c:if>
							</ul>
						</div>
						<div class="c-card__price-wrap">
							<c:if test="${!empty prod.mmBasAmtVatDesc && !empty prod.promotionAmtVatDesc}">
				        		<span class="u-td-line-through">
				        			<span class="c-hidden">월 기본료(VAT 포함)</span>${prod.mmBasAmtVatDesc} 원
								</span>
				        	</c:if>

							<div class="c-card__price">
								<div class="c-card__price-title">
									<c:if test="${!empty prod.mmBasAmtVatDesc && !empty prod.promotionAmtVatDesc}">
						        		<span class="c-hidden">프로모션 요금</span>
						        	</c:if>
									월 기본료(VAT 포함)
								</div>
								<span>
									<b><c:choose><c:when test="${!empty prod.mmBasAmtVatDesc && !empty prod.promotionAmtVatDesc}">${prod.promotionAmtVatDesc}</c:when><c:otherwise>${prod.mmBasAmtVatDesc}</c:otherwise></c:choose></b>원
								</span>
							</div>
							<c:if test="${!empty prod.contractAmtVatDesc}">
								<div class="c-card__price">
				        			<div class="c-card__price-title">
										<span class="c-hidden">약정시 기본료</span>
										약정시 기본료(VAT 포함)
									</div>
				        			<span><b>${prod.contractAmtVatDesc}</b>원</span>
								</div>
							</c:if>
							<c:if test="${!empty prod.rateDiscntAmtVatDesc}">
								<div class="c-card__price">
				        			<div class="c-card__price-title">
										<span class="c-hidden">요금할인 시 기본료</span>
										요금할인 시 기본료(VAT 포함)
									</div>
				        			<span><b>${prod.rateDiscntAmtVatDesc}</b>원</span>
								</div>
							</c:if>
							<c:if test="${!empty prod.seniorDiscntAmtVatDesc}">
								<div class="c-card__price">
					        		<div class="c-card__price-title">
										<span class="c-hidden">시니어 할인 시 기본료</span>
										시니어 할인 시 기본료(VAT 포함)
									</div>
					        		<span><b>${prod.seniorDiscntAmtVatDesc}</b>원</span>
								</div>
							</c:if>
						</div>
					</a>

			<c:if test="${status.count % 2 == 0}">
					</div>
				</div>
			</c:if>
		</c:forEach>
	</c:if>
</div>
