<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<%-- 요금제 목록 --%>
<span id="tab2Label" class="c-hidden"></span>
<div>
	<div class="c-row c-row--lg">
		<div class="c-accordion product" id="accordionproductA" data-ui-accordion>
			<ul class="c-accordion__box">
				<c:forEach var="ctg" items="${ctgXmlList}" varStatus="status">
					<li class="c-accordion__item">
						<div class="c-accordion__head">
							<c:if test="${!empty ctg.rateAdsvcCtgImgNm}">
								<img src="${ctg.rateAdsvcCtgImgNm}" alt="${ctg.rateAdsvcCtgImgNm}">
							</c:if>
							<div class="product__title-wrap">
								<strong class="product__title">${ctg.rateAdsvcCtgNm}</strong>
								<span class="product__sub">${ctg.rateAdsvcCtgBasDesc}</span>
							</div>
							<button type="button" id="acc_header_a${status.count}" data-rateAdsvcCtgCd="${ctg.rateAdsvcCtgCd}" class="runtime-data-insert c-accordion__button" aria-expanded="false" aria-controls="acc_content_a${status.count}" href="javascript:void(0);" onclick="showUsimAccordion('${ctg.rateAdsvcCtgCd}', '${status.count}', this)">
								<span class="c-hidden">${ctg.rateAdsvcCtgNm} 펼쳐보기</span>
							</button>
						</div>
						<div id="acc_content_a${status.count}" class="c-accordion__panel expand" aria-labelledby="acc_header_a${status.count}">
							<%-- accordion 목록 --%>
							<div class="c-accordion__inside">
							</div>
						</div>
					</li>
				</c:forEach>

			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(".runtime-data-insert").click(function(){
		var hasClass = $(this).hasClass("is-active");
		var title = $(this).siblings().find(".product__title").text();

		if(hasClass){
			$(this).children().remove();
			$(this).append("<span class='c-hidden'>" + title + " 펼쳐보기</span>");
		} else {
			$(this).children().remove();
			$(this).append("<span class='c-hidden'>" + title + " 접기</span>");
		}
	});
</script>
