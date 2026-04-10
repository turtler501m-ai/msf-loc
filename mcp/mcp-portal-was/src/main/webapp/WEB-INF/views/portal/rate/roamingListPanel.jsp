<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<%-- 로밍 목록 --%>
<div id="accordionproductR" class="c-accordion product" data-ui-accordion>
    <ul id="listPanel2" class="c-accordion__box">
        <c:if test="${!empty prodXmlList}">
            <c:forEach var="prod" items="${prodXmlList}" varStatus="status">
                <li class="c-accordion__item" data-amt="${prod.mmBasAmtVatDesc}">
                    <div class="c-accordion__head">
                        <c:if test="${!empty prod.rateAdsvcCtgImgNm}">
                            <div class="product__image">
                                  <img src="${pageContext.request.contextPath}${prod.rateAdsvcCtgImgNm}" alt="${prod.rateAdsvcNm}" aria-hidden="true" onerror="this.src='/resources/images/portal/content/img_phone_noimage.png'">
                            </div>
                        </c:if>
                        <div class="product__title-wrap">
                            <span class="product__sub"><c:forEach var="ctgNm" items="${prod.ctgNmList}" varStatus="stat"><c:if test="${!stat.first}">, </c:if>${ctgNm}</c:forEach></span>
                            <strong class="product__title">${prod.rateAdsvcNm}</strong>
                        </div>
                        <div class="product__text">
                            <div class="product__sub">VAT 포함</div>
                            <span class="u-co-sub-4">
                                <c:choose>
                                    <c:when test="${'Y' eq prod.freeYn}">
                                        <b>무료제공</b>
                                    </c:when>
                                      <c:when test="${'Y' ne prod.freeYn and '1' eq prod.dateType and prod.usePrd ne '0' and !empty prod.usePrd}">
                                          <b>${prod.mmBasAmtVatDesc}</b>원<b> / ${prod.usePrd}</b>일
                                      </c:when>
                                        <c:when test="${'Y' ne prod.freeYn and '3' eq prod.dateType and '중국/일본 알뜰 로밍' eq prod.rateAdsvcNm and prod.usePrd ne '0' and !empty prod.usePrd}">
                                        <b>${prod.mmBasAmtVatDesc}</b>원<b> / ${prod.usePrd}</b>일
                                    </c:when>
                                      <c:otherwise>
                                          <b>${prod.mmBasAmtVatDesc}</b>원
                                    </c:otherwise>
                                  </c:choose>
                            </span>
                        </div>
                        <button class="runtime-data-insert c-accordion__button" id="acc_header_b${status.count}" type="button" aria-expanded="false" aria-controls="acc_content_b${status.count}" href="javascript:void(0);" data-seq="${prod.rateAdsvcGdncSeq}" onclick="showUsimAccordion('${prod.rateAdsvcGdncSeq}', '${status.count}', this)">
                            <span class="c-hidden">데이터 당겨쓰기 상세열기</span>
                        </button>
                    </div>
<%--					 로밍 상품 리스트 아코디언--%>

                    <div class="c-accordion__panel expand" id="acc_content_b${status.count}" aria-labelledby="acc_header_b${status.count}">
                        <div class="c-hidden rateAdsvcGdncSeq">${prod.rateAdsvcGdncSeq}</div>
                        <div class="c-hidden rateAdsvcCtgCd">${prod.rateAdsvcCtgCd}</div>

                        <div class="c-accordion__inside">

                        </div>
                    </div>
                </li>
            </c:forEach>
        </c:if>

        <div class="c-nodata" <c:if test="${!empty prodXmlList}"> style="display: none;" </c:if>>
               <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
               <p class="c-nodata__text">조회 결과가 없습니다.</p>
           </div>
    </ul>
</div>
