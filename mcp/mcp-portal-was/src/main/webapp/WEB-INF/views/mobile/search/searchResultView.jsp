<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="nmcp.tag" prefix="nmcp" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<c:set var="searchKey" value="${searchKey}"/>

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="contentAttr">
	<%-- content 영역 , class="ly-content" 인 부분추출 --%>
	
	<div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">통합검색<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="c-form c-form--search">
        <div class="c-form__input">
          <input class="c-input" type="text" id="searchViewInput"  placeholder="검색어를 입력해주세요" title="검색어 입력" value="" autocomplete="off" maxlength="20">
          <!--[2022-03-07] 검색버튼 디자인 변경-->
          <button class="c-button c-button--search-bk">
            <span class="c-hidden">검색어</span>
            <i class="c-icon c-icon--search-bk" aria-hidden="true"></i>
          </button>
          <button class="c-button c-button--reset">
            <span class="c-hidden">초기화</span>
            <i class="c-icon c-icon--reset" aria-hidden="true"></i>
          </button>
        </div>
      </div>
 
 	  <c:set var="changeKey" value="<span class='u-co-mint'>${searchKey}</span>"/>
      <c:choose>
        <c:when test="${not empty searchMenu.hits.hits}">
            <!-- 출력된 검색어 컬러 적용 시 클래스 .u-co-mint 추가-->
            <h3 class="c-heading c-heading--type3">메뉴 바로가기
      	        <ul class="c-list c-list--type1" id="menuShortCut">
      	            <c:forEach items="${searchMenu.hits.hits}" var="menu">
                    <c:set var="menuNm" value="${menu._source.menuNm}"/>
                    <c:set var="menuCode" value="${menu._source.menuCode}"/>
                    <c:url var="menuUrl" value="${menu._source.urlAdr}"></c:url>
                    <c:url var="prntsKey" value="${menu._source.prntsKey}"></c:url>
                    
                    <c:if test="${not empty menu.highlight}">
                        <c:set var="highlightMn" value="${menu.highlight.menuNm[0]}" />
                    </c:if>

                    <c:if test="${fn:startsWith(menuCode, 'MOMENU')}">
			          <li class="c-list__item">
			            <a class="c-list__anchor" href="${menuUrl}">
			              <strong class="c-list__title c-text-ellipsis">
			              ${fn:replace(menuNm,searchKey,changeKey)}
			              </strong>
			              <span class="c-list__sub u-fw--regular">${prntsKey}</span>
			            </a>
			          </li>
			        </c:if>
                    </c:forEach>
                </ul>
      		</h3>
          </c:when>
        </c:choose>
 
      <c:choose>
          <c:when test="${not empty searchPhone.hits.hits}">
          <h3 class="c-heading c-heading--type3">휴대폰</h3>
          <div class="c-button-wrap c-button-wrap--right">
            <a class="c-button c-button--xsm" href="javascript:void(0);" onclick="listView('/m/product/phone/phoneList.do');">
              <span class="c-hidden">바로가기</span>
              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
            </a>
          </div>
              <ul class="phone-box-wrap u-mt--32" id="phoneResult">
                  <c:forEach items="${searchPhone.hits.hits}" var="phone">
                  <c:url var="phoneUrl" value="/m/product/phone/phoneView.do">
                      <c:param name="prodId" value="${phone._source.prodId}"></c:param>
                      <c:param name="hndsetModelId" value="${phone._source.hndsetModelId}"></c:param>
                  </c:url>
                  <li class="phone-box-wrap__item">
                      <a href="${phoneUrl}">
                          <div class="phone-box">
                              <img class="phone-img" src="${phone._source.imgPath}">
                              <strong class="c-text c-text--type2 u-mt--24">
                                  <b><c:out value="${phone._source.prodNm}"></c:out></b>
                              </strong>
                          </div>
                      </a>
                  </li>
                  </c:forEach>
              </ul>
              <hr class="c-hr c-hr--type3 u-mt--24">
              <c:if test="${searchPhone.hits.total.value gt 2}">
	              <div class="c-button-wrap u-mt--8" id="phoneMoreBtn">
	                <button class="c-button c-button--full">더보기<span class="c-button__sub" id="phoneMoreBtnTxt">${fn:length(searchPhone.hits.hits)}/${searchPhone.hits.total.value}</span>
	                  <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
	                </button>
	              </div>
	          </c:if>
          </c:when>
      </c:choose>
      <c:choose>
          <c:when test="${not empty searchRate.hits.hits}">
          <h3 class="c-heading c-heading--type3">요금제</h3>
          <div class="c-button-wrap c-button-wrap--right">
            <a class="c-button c-button--xsm" href="javascript:void(0);" onclick="listView('/m/rate/rateList.do');">
              <span class="c-hidden">바로가기</span>
              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
            </a>
          </div>
              <ul class="c-list c-list--type1 u-mt--12" id="rateResult">
                  <c:forEach items="${searchRate.hits.hits}" var="rate">
                  <li class="c-list__item">
                      <a class="c-list__anchor" id="ratePopup" href="javascript:void(0);" onclick="rateView('${rate._source.rateAdsvcGdncSeq}','${rate._source.rateAdsvcCd}');">
                          <strong class="c-list__title c-text-ellipsis">
                              <c:out value="${rate._source.rateAdsvcNm}"></c:out>
                          </strong>
                          <div class="c-flex c-flex--jfy-between">
                            <span class="c-list__sub u-fw--regular">월 기본료(VAT 포함)</span>
                            <span class="u-ml--auto c-text c-text--type4">
                            <c:choose>
                              <c:when test="${rate._source.promotionAmtVatDesc eq null}">
                                <span class="u-ml--8 u-co-mint fs-16">
                                    <b><c:out value="${rate._source.mmBasAmtVatDesc}"></c:out></b>원
                                </span>
                              </c:when>
                              <c:otherwise>
                                <span class="c-text c-text--strike u-co-gray-7 u-fw--regular">
                                    <c:out value="${rate._source.mmBasAmtVatDesc}"></c:out>
                                </span>
                                <span class="u-ml--8 u-co-mint fs-16">
                                    <b><c:out value="${rate._source.promotionAmtVatDesc}"></c:out></b>원
                                </span>
                              </c:otherwise>
                            </c:choose>
                            </span>
                          </div>
                      </a>
                  </li>
                  </c:forEach>
              </ul>
              <c:if test="${searchRate.hits.total.value gt 5}">
                  <div class="c-button-wrap u-mt--8" id="rateMoreBtn">
                    <button class="c-button c-button--full">더보기<span class="c-button__sub" id="rateMoreBtnTxt">${fn:length(searchRate.hits.hits)}/${searchRate.hits.total.value}</span>
                      <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
                    </button>
                  </div>
              </c:if>
          </c:when>
      </c:choose>
      <c:choose>
          <c:when test="${not empty searchAdsvc.hits.hits}">
          <h3 class="c-heading c-heading--type3">부가서비스</h3>
          <div class="c-button-wrap c-button-wrap--right">
            <a class="c-button c-button--xsm" href="javascript:void(0);" onclick="listView('/m/rate/adsvcGdncList.do');">
              <span class="c-hidden">바로가기</span>
              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
            </a>
          </div>
              <div class="c-accordion c-accordion--type1 u-mt--12">
                  <ul class="c-accordion__box" id="adsvcResult">
                      <c:forEach items="${searchAdsvc.hits.hits}" var="adsvc" varStatus="status">
                      	<c:set var="adsvcNm" value="${adsvc._source.rateAdsvcNm}"/>
                          <li class="c-accordion__item adsvcAccordion" id="adsvc${status.index}">
                            <div class="c-accordion__head" data-acc-header id="acc_header_b${status.index}" >
                              <button class="c-accordion__button" type="button" aria-expanded="false">
                                <p class="c-text c-text--type4 u-mb--8 u-co-gray-7">
                                    <c:out value="${adsvc._source.rateAdsvcCtgNm}"></c:out>
                                </p>
                                <c:out value="${adsvc._source.rateAdsvcNm}"></c:out>
                              </button>
                            </div>
                            <div class="c-accordion__panel expand c-expand" id="acc_content_b${status.index}">
                              <div class="c-accordion__inside">
                              <div class="c-extraservice--box">
                              	<ul>
                              	<c:forEach items="${searchAdsvcXml}" var="adsvcXml">
                              		<c:if test="${adsvcXml._source.rateAdsvcGdncSeq eq adsvc._source.rateAdsvcGdncSeq}">
                              			<c:if test="${not empty adsvcXml.ADDSV101}">
			                                <li>서비스 정의
			                                    <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV101, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
			                                </li>
                              			</c:if>
                              			<c:if test="${not empty adsvcXml.ADDSV102}">
			                                <li>서비스 이용요금(VAT포함)
			                                    <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV102, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
			                                </li>
                              			</c:if>
                              			<c:if test="${not empty adsvcXml.ADDSV103}">
			                                <li>서비스 신청방법
			                                    <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV103, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
			                                </li>
                              			</c:if>
                              			<c:if test="${not empty adsvcXml.ADDSV104}">
			                                <li>서비스 이용방법
			                                    <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV104, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
			                                </li>
                              			</c:if>
                              			<c:if test="${not empty adsvcXml.ADDSV105}">
			                                <li>서비스 유의사항
			                                    <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV105, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
			                                </li>
                              			</c:if>
                              			<c:if test="${not empty adsvcXml.ADDSV106}">
			                                <li>서비스 선택유형
			                                    <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV106, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
			                                </li>
                              			</c:if>
                              			<c:if test="${not empty adsvcXml.ADDSV107}">
			                                <li>서비스 제공내용
			                                    <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV107, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
			                                </li>
                              			</c:if>
                              			<c:if test="${not empty adsvcXml.ADDSV108}">
			                                <li>서비스 신청(가입 및 해지)방법
			                                    <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV108, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
			                                </li>
                              			</c:if>
                              			<c:if test="${not empty adsvcXml.ADDSV199}">
			                                <li>직접입력
			                                    <c:out value="${fn:replace(fn:replace(adsvcXml.ADDSV199, '&lt;', '<'), '&gt;', '>')}" escapeXml="false"/>
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
              <c:if test="${searchAdsvc.hits.total.value gt 5}">
                  <div class="c-button-wrap u-mt--8" id="adsvcMoreBtn">
                    <button class="c-button c-button--full">더보기<span class="c-button__sub" id="adsvcMoreBtnTxt">${fn:length(searchAdsvc.hits.hits)}/${searchAdsvc.hits.total.value}</span>
                      <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
                    </button>
                  </div>
              </c:if>
          </c:when>
      </c:choose>
      <c:choose>
          <c:when test="${not empty searchEvent.hits.hits}">
            <h3 class="c-heading c-heading--type3">진행 중인 이벤트</h3>
            <div class="c-button-wrap c-button-wrap--right">
              <a class="c-button c-button--xsm" href="javascript:void(0);" onclick="listView('/m/event/eventList.do');">
                <span class="c-hidden">바로가기</span>
                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
              </a>
            </div>
              <div class="swiper-container swiper-event-banner c-expand u-mt--24" id="swiperCardBanner">
                  <div class="swiper-wrapper">
                      <c:forEach items="${searchEvent.hits.hits}" var="event">
                      <c:url var="eventUrl" value="${fn:substringAfter(event._source.eventUrlAdr, '.com')}"></c:url>
                      <input type="hidden" value="/m${eventUrl}"/>
                        <div class="swiper-slide">
                          <button class="swiper-event-banner__button">
                            <div class="swiper-event-banner__item">
                              <a href="/m${eventUrl}">
                                <img src="${event._source.listImg}" alt="">
                              </a>
                            </div>
                          </button>
                        </div>
                      </c:forEach>
                  </div>
              </div>
          </c:when>
      </c:choose>
      <c:choose>
          <c:when test="${not empty searchBoard.hits.hits}">
      <h3 class="c-heading c-heading--type3">자주묻는 질문</h3>
      <div class="c-button-wrap c-button-wrap--right">
        <a class="c-button c-button--xsm" href="javascript:void(0);" onclick="listView('/m/cs/faqList.do');">
          <span class="c-hidden">바로가기</span>
          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
        </a>
      </div>
              <div class="c-accordion c-accordion--type1 faq-accordion">
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
                              <div class="c-accordion__head is-qtype" data-acc-header>
                                  <span class="p-prefix">Q</span>
                                  <button class="c-accordion__button" type="button" aria-expanded="false">
                                  <div class="c-accordion__title">
<%-- 
                                      <c:choose>
                                          <c:when test="${not empty highLightSubject}">
                                              <c:choose>
                                                  <c:when test="${fn:startsWith(highLightSubject, '<')}">
                                                      <!-- 검색어와 메뉴가 같을경우 -->
                                                      <c:if test="${highLightSubject eq boardSubject}">
                                                          <span class="u-co-mint">
                                                              <c:out value="${highlightSub2}"></c:out>
                                                          </span>
                                                      </c:if>
                                                      <span class="u-co-mint">
                                                          <c:out value="${highlightSub2}"></c:out>
                                                      </span>
                                                      <c:out value="${subjectSplit2}"></c:out>
                                                  </c:when>
                                                  <c:otherwise>
                                                      <c:out value="${subjectSplit}"></c:out>
                                                      <span class="u-co-mint">
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
                                  </div>
                                  </button>
                              </div>
                              <div class="c-accordion__panel c-expand expand">
                                  <div class="c-accordion__inside box-answer">
                                      <div class="box-prefix">A</div>
                                      <div class="box-content">
	                                      <c:catch var="익셉션 변수">
	                                		<c:out value="${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(board._source.boardContents, '&lt;', '<'), '&gt;', '>'), '\"', ''), '&quot;', ''), '&amp;', '&')}" escapeXml="false"/>
		                                    <c:if test="${e != null}">
									            에러메시지 : ${e.message}
									        </c:if>
									    </c:catch>
                                      </div>
                                  </div>
                              </div>
                          </li>
                      </c:forEach>
                  </ul>
              </div>
              <c:if test="${searchBoard.hits.total.value gt 5}">
                  <div class="c-button-wrap u-mt--8" id="boardMoreBtn">
                      <button class="c-button c-button--full">더보기<span class="c-button__sub" id="boardMoreBtnTxt">${fn:length(searchBoard.hits.hits)}/${searchBoard.hits.total.value}</span>
                          <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
                      </button>
                  </div>
              </c:if>
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
      
    <input type="hidden" id="searchKeyword" value="${searchKey}"/>
    <input type="hidden" id="searchMenu" value="${empty searchMenu.hits.total.value ? 0 : searchMenu.hits.total.value}"/>
    <input type="hidden" id="searchPhone" value="${empty searchPhone.hits.total.value ? 0 : searchPhone.hits.total.value}"/>
    <input type="hidden" id="searchRate" value="${empty searchRate.hits.total.value ? 0 : searchRate.hits.total.value}"/>
    <input type="hidden" id="searchAdsvc" value="${empty searchAdsvc.hits.total.value ? 0 : searchAdsvc.hits.total.value}"/>
    <input type="hidden" id="searchEvent" value="${empty searchEvent.hits.total.value ? 0 : searchEvent.hits.total.value}"/>
    <input type="hidden" id="searchBoard" value="${empty searchBoard.hits.total.value ? 0 : searchBoard.hits.total.value}"/>
      
      <div class="add-info-box c-expand u-mb--m32" onclick="listView('/m/cs/csInquiryInt.do');">
        <img src="/resources/images/mobile/content/img_chat_balloon.png">
        <p class="c-text c-text--type3">HELP 버튼을 클릭해 챗봇과</p>
        <p class="c-text c-text--type2 u-mt--4 u-fw--medium">1:1 상담문의 서비스를 이용해 보세요!</p>
      </div>
      <div class="c-form" id="submitResult"></div>
      <script src="/resources/js/mobile/vendor/swiper.min.js"></script>
      <script>
        // 진행중 이벤트 스와이퍼
        var swiperCardBanner = new Swiper("#swiperCardBanner", {
          spaceBetween: 10,
          threshold: 10
        });
      </script>
    </div>

	</t:putAttribute>
	<t:putAttribute name="scriptHeaderAttr">

		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/search/searchResultView.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$('#searchViewInput').focusin(function(){
					$(this).val("");
					openPage('pullPopup', '/m/search/searchView.do');     
					$("#searchPopupText").focus(); 
					fnRecommendList();
			        fnPopularWordList();                  
				});
			});

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

		</script>
	</t:putAttribute>
</t:insertDefinition> 
