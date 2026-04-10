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
<div class="c-accordion c-accordion--type1 blue-inside u-mb--24">
	<ul class="c-accordion__box" id="accordionB">                               
		<c:forEach var="ctg" items="${ctgXmlList}" varStatus="status">
       		<li class="c-accordion__item">
        		<div class="c-accordion__head" data-acc-header>
                  	<c:if test="${!empty ctg.rateAdsvcCtgImgNm}">
	                  	<div class="c-accordion__image">
	                    	<img src="${ctg.rateAdsvcCtgImgNm}" alt="${ctg.rateAdsvcCtgNm}">
	                  	</div>
                  	</c:if>
            		<button class="c-accordion__button" type="button" data-rateAdsvcCtgCd="${ctg.rateAdsvcCtgCd}" aria-expanded="false" onclick="showUsimAccordion('${ctg.rateAdsvcCtgCd}', '${status.count}', this)"> 
            			${ctg.rateAdsvcCtgNm}
            			<p class="c-text c-text--type4 u-mt--8">${ctg.rateAdsvcCtgBasDesc}</p>
              		</button>
            	</div><!-- 유심 요금제 상품 리스트 아코디언-->
            	<div class="c-accordion__panel expand c-expand">
              		<div class="c-accordion__inside">
              		</div>
            	</div>
        	</li>
       	</c:forEach>
	</ul>
</div>

<div class="c-button-wrap c-button-wrap--column">
	<button class="c-button c-button--md c-button--white" onclick="javascript:openPage('pullPopup', '/m/rate/rateAgreeView.do', '');">
    	<i class="c-icon c-icon--guide" aria-hidden="true"></i>
    	<span>약정할인 프로그램 및 할인반환금</span>
  	</button>
</div>

<div class="payment-guide-box">
	<i class="c-icon c-icon--payment-guide" aria-hidden="true"></i>
    <p class="c-text c-text--type3 u-mt--16">
    	kt M모바일만의
        <br>실속있는 다양한 상품들을 만나보세요
    </p>
    <div class="c-button-wrap">
    	<a class="c-button c-button--full c-button--sm" href="/m/appForm/appFormDesignView.do">유심 상품 가입</a>
        <a class="c-button c-button--full c-button--sm" href="/m/product/phone/phoneList.do">휴대폰 결합상품 가입</a>
    </div>
</div>
