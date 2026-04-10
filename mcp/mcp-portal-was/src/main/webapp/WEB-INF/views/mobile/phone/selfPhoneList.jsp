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
    	<script type="text/javascript" src="/resources/js/mobile/phone/phoneList.js"></script>
    	<script>
    		var reqRateCd = '<c:out value="${rateCd}"/>';
        </script>
        <script>
	    var swiper = new Swiper('.c-swiper', {
	      loop: true,
	      pagination: {
	        el: '.swiper-pagination',
	        type: 'fraction',
	      }
	      <c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
	      ,
	      autoplay: {
	        delay: 3000,
	        disableOnInteraction: false,
	      },
	      on: {
	        init: function() {
	          var swiper = this;
	          var autoPlayButton = this.el.querySelector('.swiper-play-button');
	          if(typeof autoPlayButton != "undefined"){
		          autoPlayButton.addEventListener('click', function() {
		            if (autoPlayButton.classList.contains('play')) {
		              autoPlayButton.classList.remove('play');
		              autoPlayButton.classList.add('stop');
		              swiper.autoplay.start();
		            } else {
		              autoPlayButton.classList.remove('stop');
		              autoPlayButton.classList.add('play');
		              swiper.autoplay.stop();
		            }
		          });
	          }
	        },
	      },
	     </c:if>
	    });
	    //[2022-01-19] 하위 accordion 작동과 아웃라인 연동기능을 위한 함수 추가
	    function accordState() {
	      var accEl = document.querySelector('#find_phone_acc');
	      var expandFlag = document.querySelector('.expand-flag');
	      // 열릴 때
	      accEl.addEventListener(KTM.Accordion.EVENT.OPEN, function() {
	        expandFlag.classList.add('is-expanded');
	      });
	      // 닫힐 때
	      accEl.addEventListener(KTM.Accordion.EVENT.CLOSE, function() {
	        expandFlag.classList.remove('is-expanded');
	      });
	    }
	    document.addEventListener('UILoaded', function() {
	      accordState();
	    });

	    //- var accIns = KTM.Accordion.getInstance(acc);
  	</script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
	<form id="phonelistfrm" name="phonelistfrm" method="post" action="./phoneList.do">
		<input type="hidden" id="listOrderEnum" name="listOrderEnum" />
		<input type="hidden" id="selProdCtgId" name="selProdCtgId" />
		<input type="hidden" id="sesplsYn" name="sesplsYn" value="Y" />
		<input type="hidden" name="prodId" value="${param.prodId}" />
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					자급제<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-side-badge">
				<b class="c-text c-text--type3">휴대폰 비교함</b>
				<span class="in-box">
					<span class="c-hidden">0</span> <i class="c-icon c-icon--num0" aria-hidden="true"></i>
				</span>
			</div>
			<!--  배너시작 -->
			<c:if test="${!empty bannerInfo}">
				<div class="banner-wrap upper-banner__box upper-banner__box--type3">
					<div class="c-swiper swiper-container">
						<div class="swiper-wrapper">
							<c:forEach var="bannerInfo" items="${bannerInfo}">
								<c:set var="target" value="_self " />
								<c:if test="${bannerInfo.linkTarget eq 'Y' }">
									<c:set var="target" value="_blank " />
								</c:if>
								<c:choose>
									<c:when test="${bannerInfo.mobileLinkUrl eq null || bannerInfo.mobileLinkUrl eq ''}">
										<div class="swiper-slide">
											<div class="c-hidden">
												<p>
													<c:out value="${bannerInfo.bannDesc}" />
												</p>
											</div>
											<img src="<c:out value="${bannerInfo.mobileBannImgNm}"/>" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>">
										</div>
									</c:when>
									<c:otherwise>
										<div class="swiper-slide">
											<div class="c-hidden">
												<p>
													<c:out value="${bannerInfo.bannDesc}" />
												</p>
											</div>
											<a href="#" onclick="fn_go_banner('<c:out value="${bannerInfo.mobileLinkUrl}"/>','<c:out value="${bannerInfo.bannSeq}"/>','<c:out value="${bannerInfo.bannCtg}"/>','${target}')">
												<img src="${bannerInfo.mobileBannImgNm}" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>">
											</a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
						<!--[2021-12-17] 재생/일시정지 버튼 추가(재생 시 .play, 일시정지 시 .stop)-->
						<c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
							<button class="swiper-play-button stop" type="button"></button>
							<div class="swiper-pagination"></div>
						</c:if>
					</div>
				</div>
			</c:if>
			<!--  배너끝 -->
			<div class="phone">
				<div class="banner-balloon expand-flag u-mt--48 u-mb--24">
					<b class="c-text c-text--type7 u-co-mint"> 어떤 휴대폰을 <br>원하세요?
						<img class="deco-img-type2" src="/resources/images/mobile/content/img_bolloon_banner_01.png" alt="">
					</b>
				</div>
				<div class="c-accordion c-accordion--type4 with-expand-flag" id="find_phone_acc">
					<div class="phone-find__box">
						<div class="c-accordion__head" data-acc-header="#chkCSP1">
							<button class="c-accordion__button c-accordion__button_type1 find_phone_btn" type="button" aria-expanded="false">
								<i class="c-icon c-icon--arrow-down-mint" aria-hidden="true"></i>
							</button>
						</div>
					</div>
					<ul class="c-accordion__box" id="accordion1">
						<li class="c-accordion__item no-line">
							<div class="c-accordion__panel expand" id="chkCSP1">
								<div class="c-accordion__inside box-white">
									<div class="c-form">
										<span class="c-form__title u-mt--0">네트워크</span>
										<div class="c-check-wrap c-check-wrap--type2">
											<div class="c-chk-wrap">
												<input value="" class="c-radio c-radio--button" id="radNetwork1" type="radio" name="prodCtgId" checked>
												<label class="c-label" for="radNetwork1">전체</label>
											</div>
											<div class="c-chk-wrap">
												<input value="04" class="c-radio c-radio--button" id="radNetwork2" type="radio" name="prodCtgId">
												<label class="c-label" for="radNetwork2">LTE</label>
											</div>
											<div class="c-chk-wrap">
												<input value="08" class="c-radio c-radio--button" id="radNetwork3" type="radio" name="prodCtgId">
												<label class="c-label" for="radNetwork3">5G</label>
											</div>
										</div>
									</div>
									<div class="c-form">
										<span class="c-form__title">제조사</span>
										<div class="c-check-wrap c-check-wrap--type2">
											<div class="c-chk-wrap">
												<input class="c-radio c-radio--button" id="radManufactAll" type="radio" name="makrCd" value="" checked>
												<label class="c-label" for="radManufactAll">전체</label>
											</div>
											<c:forEach items="${mspOrgList}" var="orgItem" varStatus="status">
												<div class="c-chk-wrap">
													<input class="c-radio c-radio--button" id="radManufact<c:out value="${status.count}"/>"type="radio" name="makrCd" value="${orgItem.mnfctId }"<c:if test ="${commonSearchDto.makrCd eq orgItem.mnfctId }">checked</c:if>>
													<label class="c-label" for="radManufact<c:out value="${status.count}"/>"><c:out value="${orgItem.mnfctNm}" /></label>
												</div>
											</c:forEach>
										</div>
									</div>
									<button id="search_button" type="button" class="c-button c-button--full c-button--mint c-button--h48 u-mt--40 u-mb--24" data-scroll-top="#product_list|75">검색</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="phone__item-wrap c-expand phone-list-wrap">
					<div class="phone__sort" style="display: none;">
						<button type="button" class="c-button c-button--full c-flex c-flex--jfy-center" data-dialog-trigger="#bsSortList" id="orderButton">
							<span class="u-mr--4 fs-14">추천 순</span><i class="c-icon c-icon--sort" aria-hidden="true"></i>
						</button>
					</div>
					<ul id="product_list">

					</ul>
					<div class="phone__more" style="display: none;" id="moreBtn">
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
	            <input class="c-radio c-radio--list" id="radList3" type="radio" name="radList" data-value="USED_PRICE_HIGH">
	            <label class="c-label" for="radList3">출고가 높은 순</label>
	            <input class="c-radio c-radio--list" id="radList4" type="radio" name="radList" data-value="USED_PRICE_ROW">
	            <label class="c-label" for="radList4">출고가 낮은 순</label>
	          </div>
	        </div>
	      </div>
	    </div>
	  </div>
    	<c:import url="/m/product/phone/phoneComparePop.do"></c:import>
    </t:putAttribute>

</t:insertDefinition>