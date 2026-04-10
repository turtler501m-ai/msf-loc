<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
    	<script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
    	<script type="text/javascript" src="/resources/js/mobile/phone/exhibitList.js?version=23-06-20"></script>

    </t:putAttribute>
    <t:putAttribute name="contentAttr">
    <form id="phonelistfrm" name="phonelistfrm" method="post" action="./phoneList.do">
    	<input type="hidden" id="listOrderEnum" name="listOrderEnum" />
    	<input type="hidden" id="selProdCtgId" name="selProdCtgId" />
    	<input type="hidden" id="shandYn" name="shandYn" value="N"/>
		<input type="hidden" id="ctgCd" name="ctgCd" value="0001" />
    	<input type="hidden" id="sesplsYn" name="sesplsYn" value="N" />
    	<input type="hidden" name="prodId" value="${param.prodId}"/>
		<div class="ly-content" id="main-content">
	      <div class="ly-page-sticky">
	        <h2 class="ly-page__head">기획전<button class="header-clone__gnb"></button>
	        </h2>
	      </div>

	      <!--  배너시작 -->
	      <c:if test="${!empty bannerInfo}">
      		<div class="banner-wrap upper-banner__box upper-banner__box--type3">
		        <div class="c-swiper swiper-container">
		          <div class="swiper-wrapper">
		            <c:forEach var="bannerInfo" items="${bannerInfo}">
						<c:set var="target" value="_self "/>
          				<c:if test="${bannerInfo.linkTarget eq 'Y' }"><c:set var="target" value="_blank "/></c:if>
						<c:choose>
                      			<c:when test="${bannerInfo.mobileLinkUrl eq null || bannerInfo.mobileLinkUrl eq ''}">
                      				<div class="swiper-slide">
						              <div class="c-hidden">
						                <p><c:out value="${bannerInfo.bannDesc}"/></p>
						              </div>
						              <img src="<c:out value="${bannerInfo.mobileBannImgNm}"/>" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>">
						            </div>
                      			</c:when>
                      			<c:otherwise>
                      				<div class="swiper-slide">
						              <div class="c-hidden">
						                <p><c:out value="${bannerInfo.bannDesc}"/></p>
						              </div>
						              <a href="#" onclick="fn_go_banner('<c:out value="${bannerInfo.mobileLinkUrl}"/>','<c:out value="${bannerInfo.bannSeq}"/>','<c:out value="${bannerInfo.bannCtg}"/>','${target}')"><img src="${bannerInfo.mobileBannImgNm}" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>"></a>
						            </div>
                      			</c:otherwise>
						</c:choose>
					</c:forEach>
		          </div>
		          <!--[2021-12-17] 재생/일시정지 버튼 추가(재생 시 .play, 일시정지 시 .stop)-->
		          <c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
		          <button class="swiper-play-button stop" type="button"></button>
		          <div class="swiper-pagination"> </div>
		          </c:if>
		        </div>
		      </div>
	      </c:if>
	      <!--  배너끝 -->
	      <div class="phone">

	        <div class="phone__item-wrap c-expand phone-list-wrap ">
	          <div class="phone__sort" style="display:none;">
	          	<button type="button" class="c-button c-button--full c-flex c-flex--jfy-center" data-dialog-trigger="#bsSortList" id="orderButton">
					<span class="u-mr--4 fs-14">추천 순</span><i class="c-icon c-icon--sort" aria-hidden="true"></i>
				</button>
	          </div>
			  <ul id="product_list">

			  </ul>
	          <div class="phone__more" >
	            <button type="button" class="c-button c-button--full">
	              <div class="c-block">
	                <i class="c-icon c-icon--more" aria-hidden="true"></i>
	              </div>더보기<span class="c-button__sub"><span id="currentViewCount">1</span>/<span id="totalCount"></span></span>
	            </button>
	          </div>
	        </div>
	      </div>
	  </div>

	</form>

    </t:putAttribute>

    <t:putAttribute name="popLayerAttr">
    	<div class="c-modal c-modal--bs" id="bsSortList" role="dialog" tabindex="-1" aria-describedby="#bsListDesc" aria-labelledby="#bsListtitle">
		    <div class="c-modal__dialog" role="document">
		      <div class="c-modal__content">
		        <div class="c-modal__header">
		          <h1 class="c-modal__title" id="bsSampletitle">정렬</h1>
		          <button class="c-button" id="bsSortList_close_button" data-dialog-close>
		            <i class="c-icon c-icon--close" aria-hidden="true"></i>
		            <span class="c-hidden">정렬 바텀시트 닫기</span>
		          </button>
		        </div>
		        <div class="c-modal__body" id="bsSampleDesc">
		          <div class="payment-list">
		            <input class="c-radio c-radio--list" id="radList1" type="radio" name="radList" data-value="RECOMMEND"  checked>
		            <label class="c-label" for="radList1">추천 순</label>
		            <input class="c-radio c-radio--list" id="radList2" type="radio" name="radList" data-value="NEW_ITEM" >
		            <label class="c-label" for="radList2">신상품 순</label>
		            <input class="c-radio c-radio--list" id="radList3" type="radio" name="radList" data-value="CHARGE_HIGH">
		            <label class="c-label" for="radList3">할부금 높은 순</label>
		            <input class="c-radio c-radio--list" id="radList4" type="radio" name="radList" data-value="CHARGE_ROW">
		            <label class="c-label" for="radList4">할부금 낮은 순</label>
		          </div>
		        </div>
		      </div>
		    </div>
	    </div>
    </t:putAttribute>




</t:insertDefinition>