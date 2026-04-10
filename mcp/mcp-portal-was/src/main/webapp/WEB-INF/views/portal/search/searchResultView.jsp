<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="nmcp.tag" prefix="nmcp" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<c:set var="searchKey" value="${searchKey}"/>

<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="contentAttr">

    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">통합검색</h2>
      </div>
      <div class="c-row c-row--lg">
        <div class="c-form c-form--search">
          <div class="c-form__input has-value">
            <input class="c-input" id="searchInputText" type="text" placeholder="검색어를 입력해주세요" value="" maxlength="20">
            <label class="c-form__label c-hidden" for="searchInputText">검색어 입력</label>
            <button class="c-button c-button--reset">
              <span class="c-hidden">초기화</span>
              <i class="c-icon c-icon--reset" aria-hidden="true"></i>
            </button>
            <button class="c-button c-button-round--sm c-button--primary">검색</button>
          </div>
        </div>
        
        <c:set var="changeKey" value="<span class='u-co-red'>${searchKey}</span>"/>
        <c:choose>
            <c:when test="${not empty searchMenu.hits.hits}">
              <h3 class="c-heading c-heading--fs20 c-heading--regular u-mb--24">메뉴 바로가기</h3>
                <ul class="c-list c-list--type1" id="menuShortCut">
                    <c:forEach items="${searchMenu.hits.hits}" var="menu">
                    <c:set var="menuNm" value="${menu._source.menuNm}"/>
                    <c:set var="menuCode" value="${menu._source.menuCode}"/>
                    <c:url var="menuUrl" value="${menu._source.urlAdr}"></c:url>
                    <c:url var="prntsKey" value="${menu._source.prntsKey}"></c:url>
                    
                    <c:if test="${not empty menu.highlight}">
                        <c:set var="highlightMn" value="${menu.highlight.menuNm[0]}" />
                    </c:if>

                    <c:if test="${fn:startsWith(menuCode, 'PCMENU')}">
			          <li class="c-list__item">
			            <a class="c-list__inner c-list__anchor" href="${menuUrl}">
			              <span class="c-list__title">
			                ${fn:replace(menuNm,searchKey,changeKey)}
			              </span>
			              <span class="c-list__text">
			                <span class="c-hidden">메뉴경로</span>${prntsKey}
			              </span>
			            </a>
			          </li>
                    </c:if>
                    </c:forEach>
                </ul>
            </c:when>
        </c:choose>
        
        <c:choose>
            <c:when test="${not empty searchPhone.hits.hits}">
              <div class="c-heading-wrap u-mt--64 u-mb--24">
                <h3 class="c-heading c-heading--fs20 c-heading--regular">휴대폰</h3>
                <div class="c-heading-wrap__sub">
                  <a class="c-button u-co-sub-4" href="javascript:void(0);" onclick="listView('/product/phone/phoneList.do');">
                  	<span class="c-hidden">휴대폰</span>바로가기<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
                  </a>
                </div>
              </div>
                <ul class="phone-list phone-list--type2 u-mt--28" id="phoneResult">
                    <c:forEach items="${searchPhone.hits.hits}" var="phone">
                    <c:url var="phoneUrl" value="/product/phone/phoneView.do">
                        <c:param name="prodId" value="${phone._source.prodId}"></c:param>
                        <c:param name="hndsetModelId" value="${phone._source.hndsetModelId}"></c:param>
                    </c:url>
                        <li class="phone-list__item">
                          <a class="phone-list__anchor" href="${phoneUrl}">
                            <div class="phone-list__image" aria-hidden="true">
                              <img src="${phone._source.imgPath}" alt="${phone._source.prodNm} 실물 사진">
                            </div>
                            <span class="phone-list__title"><c:out value="${phone._source.prodNm}"></c:out></span>
                          </a>
                        </li>
                    </c:forEach>
                </ul>
                <div class="c-button-wrap">
                    <c:if test="${searchPhone.hits.total.value gt 3}">
                        <button class="c-button c-button--more" id="phoneMoreBtn">
                          <i class="c-icon c-icon--more" aria-hidden="true"></i>
                          <span>더보기</span>
                          <span class="c-button__sub" id="phoneMoreCurrent">
                            <span class="c-hidden">현재 노출된 항목</span>${fn:length(searchPhone.hits.hits)}
                          </span>
                          <span class="c-button__sub" id="phoneMoreTotal">
                            <span class="c-hidden">전체 항목</span>${searchPhone.hits.total.value}
                          </span>
                        </button>
                    </c:if>
                </div>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${not empty searchRate.hits.hits}">
            <div class="c-heading-wrap u-mt--64 u-mb--24">
              <h3 class="c-heading c-heading--fs20 c-heading--regular">요금제</h3>
              <div class="c-heading-wrap__sub">
                <a class="c-button u-co-sub-4" href="javascript:void(0);" onclick="listView('/rate/rateList.do');">
                	<span class="c-hidden">요금제</span>바로가기<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
                </a>
              </div>
            </div>
                <ul class="c-list c-list--type1" id="rateResult">
                  <c:forEach items="${searchRate.hits.hits}" var="rate">
                  <li class="c-list__item">
                    <a class="c-list__anchor" href="javascript:void(0);" onclick="rateView('${rate._source.rateAdsvcGdncSeq}','${rate._source.rateAdsvcCd}');">
                      <span class="c-list__title">
                          <c:out value="${rate._source.rateAdsvcNm}"></c:out>
                      </span>
                      <div class="price">
                        <c:choose>
                          <c:when test="${rate._source.promotionAmtVatDesc eq null}">
                              <span class="price__item">
                                <span class="c-hidden">월 기본료(VAT 포함)</span>
                                <b><c:out value="${rate._source.mmBasAmtVatDesc}"></c:out></b>원
                              </span>
                          </c:when>
                          <c:otherwise>
                              <span class="price__item u-td-line-through">
                                <span class="c-hidden">월 기본료(VAT 포함)</span><c:out value="${rate._source.mmBasAmtVatDesc}"></c:out> 원
                              </span>
                              <span class="price__item">
                                <span class="c-hidden">프로모션 요금 월 기본료(VAT 포함)</span>
                                <b><c:out value="${rate._source.promotionAmtVatDesc}"></c:out></b>원
                              </span>
                          </c:otherwise>
                        </c:choose>
                      </div>
                    </a>
                  </li>
                  </c:forEach>
                </ul>
                <div class="c-button-wrap">
                  <c:if test="${searchRate.hits.total.value gt 5}">
                      <button class="c-button c-button--more" id="rateMoreBtn">
                        <i class="c-icon c-icon--more" aria-hidden="true"></i>
                        <span>더보기</span>
                        <span class="c-button__sub" id="rateMoreCurrent">
                          <span class="c-hidden">현재 노출된 항목</span>${fn:length(searchRate.hits.hits)}
                        </span>
                        <span class="c-button__sub" id="rateMoreTotal">
                          <span class="c-hidden">전체 항목</span>${searchRate.hits.total.value}
                        </span>
                      </button>
                  </c:if>
                </div>
            </c:when>
        </c:choose>
        
        <c:choose>
            <c:when test="${not empty searchAdsvc.hits.hits}">
            <div class="c-heading-wrap u-mt--64 u-mb--24">
              <h3 class="c-heading c-heading--fs20 c-heading--regular">부가서비스</h3>
              <div class="c-heading-wrap__sub">
                <a class="c-button u-co-sub-4" href="javascript:void(0);" onclick="listView('/rate/adsvcGdncList.do');">
                	<span class="c-hidden">부가서비스</span>바로가기<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
                </a>
              </div>
            </div>
                <div class="c-accordion product u-bt-gray-3" id="accordionproductB" data-ui-accordion>
                  <ul class="c-accordion__box" id="adsvcResult">
                      <c:forEach items="${searchAdsvc.hits.hits}" var="adsvc" varStatus="status">
                      <c:set var="adsvcNm" value="${adsvc._source.rateAdsvcNm}"/>
                          <li class="c-accordion__item adsvcAccordion" id="adsvc${status.index}">
                            <div class="c-accordion__head">
                              <div class="product__title-wrap">
                                <span class="product__sub">
                                    <c:out value="${adsvc._source.rateAdsvcCtgNm}"></c:out>
                                </span>
                                <span class="product__title">
                                    <c:out value="${adsvc._source.rateAdsvcNm}"></c:out>
                                </span>
                              </div>

                              <div class="product__text">
                                  <c:choose>
                                    <c:when test="${not empty adsvc._source.mmBasAmtVatDesc}">
                                      <c:choose>
                                        <c:when test="${fn:contains(adsvc._source.mmBasAmtVatDesc, '무료')}">
                                          <span class="u-co-point-4">
                                            <b><c:out value="${adsvc._source.mmBasAmtVatDesc}"></c:out></b>
                                          </span>
                                        </c:when>
                                        <c:otherwise>
                                          <div class="product__sub">VAT 포함</div>
                                          <span class="u-co-sub-4">
                                            <b><c:out value="${adsvc._source.mmBasAmtVatDesc}"></c:out></b>원
                                          </span>
                                        </c:otherwise>
                                      </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                      <span class="u-co-point-4">
                                          <b><c:out value="${adsvc._source.mmBasAmtVatDesc}"></c:out></b>
                                      </span>
                                    </c:otherwise>
                                  </c:choose>
                              </div>

                              <button class="runtime-data-insert c-accordion__button" id="acc_header_b${status.index}" type="button" aria-expanded="false" aria-controls="acc_content_b${status.index}">
                                <span class="c-hidden">${adsvc._source.rateAdsvcNm} 상세열기</span>
                              </button>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_content_b${status.index}" aria-labelledby="acc_header_b${status.index}">
                              <div class="c-accordion__inside">
                                  <div class="product__content">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <c:forEach items="${searchAdsvcXml}" var="adsvcXml">
                                            <c:if test="${adsvcXml._source.rateAdsvcGdncSeq eq adsvc._source.rateAdsvcGdncSeq}">
                                                <c:if test="${not empty adsvcXml.ADDSV101}">
                                                    <li class="c-text-list__item">서비스 정의
                                                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                            <li class="c-text-list__item u-co-gray">
                                                                <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV101, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                                <c:if test="${not empty adsvcXml.ADDSV102}">
                                                    <li class="c-text-list__item">서비스 이용요금(VAT포함)
                                                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                            <li class="c-text-list__item u-co-gray">
                                                                <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV102, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                                <c:if test="${not empty adsvcXml.ADDSV103}">
                                                    <li class="c-text-list__item">서비스 신청방법
                                                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                            <li class="c-text-list__item u-co-gray">
                                                                <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV103, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                                <c:if test="${not empty adsvcXml.ADDSV104}">
                                                    <li class="c-text-list__item">서비스 이용방법
                                                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                            <li class="c-text-list__item u-co-gray">
                                                                <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV104, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                                <c:if test="${not empty adsvcXml.ADDSV105}">
                                                    <li class="c-text-list__item">서비스 유의사항
                                                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                            <li class="c-text-list__item u-co-gray">
                                                                <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV105, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                                <c:if test="${not empty adsvcXml.ADDSV106}">
                                                    <li class="c-text-list__item">서비스 선택유형
                                                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                            <li class="c-text-list__item u-co-gray">
                                                                <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV106, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                                <c:if test="${not empty adsvcXml.ADDSV107}">
                                                    <li class="c-text-list__item">서비스 제공내용
                                                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                            <li class="c-text-list__item u-co-gray">
                                                                <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV107, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                                <c:if test="${not empty adsvcXml.ADDSV108}">
                                                    <li class="c-text-list__item">서비스 신청(가입 및 해지)방법
                                                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                            <li class="c-text-list__item u-co-gray">
                                                                <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV108, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                                <c:if test="${not empty adsvcXml.ADDSV199}">
                                                    <li class="c-text-list__item">직접입력
                                                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                            <li class="c-text-list__item u-co-gray">
                                                                <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV199, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                  </div>
                                </div>
                              </div>
                          </li>
                      </c:forEach>
                  </ul>
                </div>
                <div class="c-button-wrap">
                    <c:if test="${searchAdsvc.hits.total.value gt 5}">
                        <button class="c-button c-button--more" id="adsvcMoreBtn">
                          <i class="c-icon c-icon--more" aria-hidden="true"></i>
                          <span>더보기</span>
                          <span class="c-button__sub" id="adsvcMoreCurrent">
                            <span class="c-hidden">현재 노출된 항목</span>${fn:length(searchAdsvc.hits.hits)}
                          </span>
                          <span class="c-button__sub" id="adsvcMoreTotal">
                            <span class="c-hidden">전체 항목</span>${searchAdsvc.hits.total.value}
                          </span>
                        </button>
                    </c:if>
                </div>
            </c:when>
        </c:choose>
        
        <c:choose>
            <c:when test="${not empty searchEvent.hits.hits}">
        <div class="c-heading-wrap u-mt--64 u-mb--24">
          <h3 class="c-heading c-heading--fs20 c-heading--regular">진행 중인 이벤트</h3>
          <div class="c-heading-wrap__sub">
            <a class="c-button u-co-sub-4" href="javascript:void(0);" onclick="listView('/event/eventBoardList.do');">
            	<span class="c-hidden">이벤트</span>바로가기<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
            </a>
          </div>
        </div>
                <div class="swiper-banner" id="swiperEvent">
                  <div class="swiper-container">
                    <div class="swiper-wrapper">
                      <c:forEach items="${searchEvent.hits.hits}" var="event">
                      <c:url var="eventUrl" value="${fn:substringAfter(event._source.eventUrlAdr, '.com')}"></c:url>
                      <input type="hidden" value="${eventUrl}"/>
                        <div class="swiper-slide">
                          <a class="swiper-banner__anchor" href="${eventUrl}">
                            <img src="${event._source.listImg}" alt="${event._source.imgDesc}">
                          </a>
                        </div>
                      </c:forEach>
                    </div>
                  </div>
                  <button class="swiper-banner-button--next swiper-button-next" type="button"></button>
                  <button class="swiper-banner-button--prev swiper-button-prev" type="button"></button>
                </div>
            </c:when>
        </c:choose>    
        
        <c:choose>
            <c:when test="${not empty searchBoard.hits.hits}">    
            <div class="c-heading-wrap u-mt--64 u-mb--24">
              <h3 class="c-heading c-heading--fs20 c-heading--regular">자주 묻는 질문</h3>
              <div class="c-heading-wrap__sub">
                <a class="c-button u-co-sub-4" href="javascript:void(0);" onclick="listView('/cs/faqList.do');">
                	<span class="c-hidden">자주 묻는 질문</span>바로가기<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
                </a>
              </div>
            </div>
                <div class="c-accordion c-accordion--type2 u-bt-gray-3" data-ui-accordion>
                  <ul class="c-accordion__box" id="boardResult">
                      <c:forEach items="${searchBoard.hits.hits}" var="board" varStatus="status">
                          <c:set var="highLightSubject" value="${board.highlight.boardSubject[0]}"/>
                          <c:set var="highlightSub1" value="${fn:split(highLightSubject, '>')[1]}" />
                          <c:set var="highlightSub2" value="${fn:split(highlightSub1, '<')[0]}" />
                          <c:set var="boardSubject" value="${board._source.boardSubject}"/>
                          <!-- case1. em태그가 맨처음인 경우 --> 
                          <c:set var="subjectSplit" value="${fn:substringBefore(boardSubject, highlightSub2)}"/>
                          <!-- case2. em태그가 중간인 경우 --> 
                          <c:set var="subjectSplit2" value="${fn:substringAfter(boardSubject, highlightSub2)}"/>
                          
                          <li class="c-accordion__item boardAccordion" id="board${status.index}">
                            <div class="c-accordion__head">
                              <span class="question-label">Q<span class="c-hidden">질문</span>
                              </span>
                              <span class="c-accordion__title">
<%-- 
                                  <c:choose>
                                      <c:when test="${not empty highLightSubject}">
                                          <c:choose>
                                              <c:when test="${fn:startsWith(highLightSubject, '<')}">
                                                  <!-- 검색어와 메뉴가 같을경우 -->
                                                  <c:if test="${highLightSubject eq boardSubject}">
                                                      <span class="u-co-red">
                                                          <c:out value="${highlightSub2}"></c:out>
                                                      </span>
                                                  </c:if>
                                                  <span class="u-co-red">
                                                      <c:out value="${highlightSub2}"></c:out>
                                                  </span>
                                                  <c:out value="${subjectSplit2}"></c:out>
                                              </c:when>
                                              <c:otherwise>
                                                  <c:out value="${subjectSplit}"></c:out>
                                                  <span class="u-co-red">
                                                      <c:out value="${highlightSub2}"></c:out>
                                                  </span>
                                                  <c:out value="${subjectSplit2}"></c:out>
                                              </c:otherwise>
                                          </c:choose>    
                                      </c:when>
                                      <c:otherwise>
                                          ${boardSubject}
                                      </c:otherwise>
                                  </c:choose>
 --%>
 								${boardSubject}
                              </span>
                              <button class="acc_headerA1 runtime-data-insert c-accordion__button" type="button" aria-expanded="false" aria-controls="acc_contentA${status.index}">
                                <span class="c-hidden">${boardSubject} 상세열기</span>
                              </button>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_contentA${status.index}" aria-labelledby="acc_headerA${status.index}">
                              <div class="c-accordion__inside">
                                <span class="box-prefix">A</span>
                                <div class="box-content">
                                    
                                    <c:catch var="익셉션 변수">
                                        <c:out value="${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(board._source.boardContents, '&lt;', '<'), '&gt;', '>'), '\"', ''), '&quot;', ''), '&amp;', '&')}" escapeXml="false"/>
                                        <c:if test="${e != null}">
                                            에러메시지 : ${e.message}
                                        </c:if>
                                    </c:catch>
                              </div>
                            </div>
                          </li>
                      </c:forEach>
                  </ul>
                </div>
                <div class="c-button-wrap">
                    <c:if test="${searchBoard.hits.total.value gt 5}">
                        <button class="c-button c-button--more" id="boardMoreBtn">
                          <i class="c-icon c-icon--more" aria-hidden="true"></i>
                          <span>더보기</span>
                          <span class="c-button__sub" id="boardMoreCurrent">
                            <span class="c-hidden">현재 노출된 항목</span>${fn:length(searchBoard.hits.hits)}
                          </span>
                          <span class="c-button__sub" id="boardMoreTotal">
                            <span class="c-hidden">전체 항목</span>${searchBoard.hits.total.value}
                          </span>
                        </button>
                    </c:if>
                </div>
            </c:when>
        </c:choose>
        <c:if test="${empty searchMenu.hits.hits && empty searchPhone.hits.hits
                    && empty searchRate.hits.hits && empty searchAdsvc.hits.hits
                    && empty searchEvent.hits.hits && empty searchBoard.hits.hits}">
          <div class="c-nodata">
              <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
              <p class="c-noadat__text">검색 결과가 없습니다.</p>
          </div>
        </c:if>
      </div>
      <div class="u-bk--sky-blue u-mt--64">
        <div class="c-row c-row--lg">
          <img src="/resources/images/portal/content/img_customer_banner.png" alt="찾으시는 결과가 없다면? - HELP 버튼을 선택해 챗봇과 1:1 상담문의 서비스를 이용해보세요!" onclick="listView('/cs/csInquiryInt.do');">
        </div>
      </div>
    </div>
    
    <input type="hidden" id="searchKeyword" value="${searchKey}"/>
    <input type="hidden" id="searchMenu" value="${empty searchMenu.hits.total.value ? 0 : searchMenu.hits.total.value}"/>
    <input type="hidden" id="searchPhone" value="${empty searchPhone.hits.total.value ? 0 : searchPhone.hits.total.value}"/>
    <input type="hidden" id="searchRate" value="${empty searchRate.hits.total.value ? 0 : searchRate.hits.total.value}"/>
    <input type="hidden" id="searchAdsvc" value="${empty searchAdsvc.hits.total.value ? 0 : searchAdsvc.hits.total.value}"/>
    <input type="hidden" id="searchEvent" value="${empty searchEvent.hits.total.value ? 0 : searchEvent.hits.total.value}"/>
    <input type="hidden" id="searchBoard" value="${empty searchBoard.hits.total.value ? 0 : searchBoard.hits.total.value}"/>
    
    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/portal/search/searchResultView.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/portal/vendor/swiper.min.js"></script>
        <script type="text/javascript">
            //휴대폰 -> 공시지원금 단말 바로가기
            //요금제 -> 유심 요금제(목록) 바로가기
            //부가서비스 -> 부가서비스 페이지 바로가기
            //진행중인 이벤트 -> 진행중 이벤트(목록) 바로가기
            //자주묻는 질문 -> 자주묻는 질문 페이지 바로가기
            //통합검색 전 필요.
            function listView(url){
                ajaxCommon.createForm({
                        method:"post"
                        ,action: url
                });
                ajaxCommon.formSubmit();
            }

            document.addEventListener("UILoaded", function() {
                KTM.swiperA11y("#swiperEvent .swiper-container", {
                  slidesPerView: 2,
                  spaceBetween: 20,
                  threshold: 10,
                  navigation: {
                    nextEl: "#swiperEvent .swiper-button-next",
                    prevEl: "#swiperEvent .swiper-button-prev",
                  },
                });
              });
        </script>
    </t:putAttribute>
</t:insertDefinition>