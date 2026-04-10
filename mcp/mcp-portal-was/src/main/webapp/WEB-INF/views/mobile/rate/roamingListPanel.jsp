<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<%-- 부가서비스 목록 --%>
<c:if test="${!empty prodXmlList}">
    <div class="c-accordion c-accordion--type1">
        <ul class="c-accordion__box" id="listPanel2">
            <c:if test="${!empty prodXmlList}">
                <c:forEach var="prod" items="${prodXmlList}" varStatus="status">
                       <li class="c-accordion__item" data-amt="${prod.mmBasAmtVatDesc}">
                        <div class="c-accordion__head" data-acc-header>
                            <div class="c-accordion__image">
                                <c:if test="${!empty prod.rateAdsvcCtgImgNm}">
                                    <img src="${pageContext.request.contextPath}${prod.rateAdsvcCtgImgNm}" alt="${prod.rateAdsvcNm}" onerror="this.src='/resources/images/mobile/content/img_noimage.png'">
                                </c:if>
                            </div>
                            <button class="c-accordion__button c-accordion__button__type2" type="button" aria-expanded="false" data-seq="${prod.rateAdsvcGdncSeq}" onclick="showUsimAccordion('${prod.rateAdsvcGdncSeq}', '${status.count}', this)">
                                <div class="product__title-wrap">
                                    <div class="product__title">
                                        <p class="c-text c-text--type4 u-mb--8 u-co-sub-1"><c:forEach var="ctgNm" items="${prod.ctgNmList}" varStatus="stat"><c:if test="${!stat.first}">, </c:if>${ctgNm}</c:forEach></p>
                                        ${prod.rateAdsvcNm}
                                      </div>
                                      <div class="product__sub" style="text-align: right">
                                          <c:choose>
                                              <c:when test="${'Y' eq prod.freeYn}">
                                                  <span class="product__text u-co-point-4" style="white-space: nowrap;">무료제공</span>
                                              </c:when>
                                              <c:when test="${'Y' ne prod.freeYn and '1' eq prod.dateType and prod.usePrd ne '0' and !empty prod.usePrd}">
                                                  <span class="product__text u-co-mint" style="white-space: nowrap;">${prod.mmBasAmtVatDesc}원</span><br><span class="product__text u-co-mint" style="white-space: nowrap;">/ ${prod.usePrd}일</span>
                                              </c:when>
                                            <c:when test="${'Y' ne prod.freeYn and '3' eq prod.dateType and '중국/일본 알뜰 로밍' eq prod.rateAdsvcNm and prod.usePrd ne '0' and !empty prod.usePrd}">
                                                <span class="product__text u-co-mint" style="white-space: nowrap;">${prod.mmBasAmtVatDesc}원</span><br><span class="product__text u-co-mint" style="white-space: nowrap;">/ ${prod.usePrd}일</span>
                                            </c:when>
                                              <c:otherwise>
                                                  <span class="product__text u-co-mint" style="white-space: nowrap;">${prod.mmBasAmtVatDesc}원</span>
                                              </c:otherwise>
                                          </c:choose>
                                      </div>
                                </div>
                            </button>
                        </div>
                        <%-- 부가서비스 상품 리스트 아코디언 --%>
                        <div class="c-accordion__panel expand c-expand">
                            <div class="c-hidden rateAdsvcGdncSeq">${prod.rateAdsvcGdncSeq}</div>
                            <div class="c-hidden rateAdsvcCtgCd">${prod.rateAdsvcCtgCd}</div>

                            <div class="c-accordion__inside">
                                <!--  -->
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
</c:if>
