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
    	<script type="text/javascript" src="/resources/js/mobile/phone/phoneList.js?version=23-10-30"></script>
    	<script>
    		var reqRateCd = '<c:out value="${rateCd}"/>';
        </script>
        <script>
	    var swiper = new Swiper('.c-swiper', {
	      pagination: {
	        el: '.swiper-pagination',
	        type: 'fraction',
	      }
          <c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
	      ,
	      loop: true,
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


	    //- var accIns = KTM.Accordion.getInstance(acc);
  	</script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
    <form id="phonelistfrm" name="phonelistfrm" method="post" action="./phoneList.do">
    	<input type="hidden" id="listOrderEnum" name="listOrderEnum" />
    	<input type="hidden" id="selProdCtgId" name="selProdCtgId" />
    	<input type="hidden" id="shandYn" name="shandYn" value="N"/>
    	<input type="hidden" id="prodType" name="prodType" value="04" />
    	<input type="hidden" id="sesplsYn" name="sesplsYn" value="N" />
    	<input type="hidden" name="prodId" value="${param.prodId}"/>

			<div class="ly-content" id="main-content">
				<div class="ly-page-sticky">
					<h2 class="ly-page__head">
						중고폰
						<button class="header-clone__gnb"></button>
					</h2>
				</div>
				<div class="c-side-badge">
					<b class="c-text c-text--type3">휴대폰 비교함</b> <span class="in-box">
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
													<img	src="${bannerInfo.mobileBannImgNm}" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>">
												</a>
											</div>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>

							<c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
								<button class="swiper-play-button stop" type="button"></button>
								<div class="swiper-pagination"></div>
							</c:if>
						</div>
					</div>
				</c:if>
				<!--  배너끝 -->
				<div class="phone">
					<a class="c-button c-button--sm c-button--white u-mt--48" href="javascript:saveScroll();openPage('spaLink', '/m/product/phone/officialNoticeSupportList.do', '');">단말 공통지원금 안내</a>
					<a class="c-button c-button--sm c-button--white u-mt--48 u-ml--2" data-dialog-trigger="#phones-rank-dialog">등급안내</a>



					<div class="c-check-wrap u-mt--24 u-mb--16">
						<span class="u-fs-14 u-fw--medium u-mt--2 u-mr--8">제조사</span>
						<input type="checkbox" class="c-checkbox c-checkbox--type3" id="chkProduct_0" value="0000" name="chkProduct" checked >
						<label class="c-label u-pl--28 u-mr--8" for="chkProduct_0">전체</label>
						<input type="checkbox" class="c-checkbox c-checkbox--type3" id="chkProduct_1" value="0141" name="chkProduct" checked >
						<label class="c-label u-pl--28 u-mr--8" for="chkProduct_1">삼성</label>
						<input type="checkbox" class="c-checkbox c-checkbox--type3" id="chkProduct_2" value="0222" name="chkProduct" checked >
						<label class="c-label u-pl--28 u-mr--8" for="chkProduct_2">애플</label>
						<input type="checkbox" class="c-checkbox c-checkbox--type3" id="chkProduct_3" value="9999" name="chkProduct" checked >
						<label class="c-label u-pl--28 u-mr--8" for="chkProduct_3">기타</label>
					</div>

					<div class="phone__item-wrap c-expand phone-list-wrap">
						<div class="phone__sort" style="display: none;">
							<button type="button" class="c-button c-button--full c-flex c-flex--jfy-center" data-dialog-trigger="#bsSortList" id="orderButton">
								<span class="u-mr--4 fs-14">추천 순</span><i class="c-icon c-icon--sort" aria-hidden="true"></i>
							</button>
						</div>

						<ul id="product_list">

						</ul>


					</div>
				</div>
			</div>
		</form>
    </t:putAttribute>
    <t:putAttribute name="popLayerAttr">
    	<c:import url="/m/product/phone/phoneComparePop.do"></c:import>

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

		<div class="c-modal" id="phones-rank-dialog" role="dialog"
			tabindex="-1" aria-describedby="#phones-rank-title">
			<div class="c-modal__dialog" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="find-store-title">중고폰 등급안내</h1>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body">
						<div class="c-table c-table--row">
							<table>
								<caption>kt M 모바일 중고폰 등급안내</caption>
								<colgroup>
									<col style="width: 25%">
									<col style="width: 75%">
								</colgroup>
								<tbody>
									<tr>
										<th class="" scope="row">S+급</th>
										<td class="u-ta-left">단순 개봉이력으로 새폰과 다름없는 퀄리티</td>
									</tr>
									<tr>
										<th class="" scope="row">S급</th>
										<td class="u-ta-left">미세한 생활 스크래치 수준의 새폰같은 깨끗함</td>
									</tr>
									<tr>
										<th class="" scope="row">리퍼폰</th>
										<td class="u-ta-left">새로운 정품 부품으로 외관 교체가 완료된 폰</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="c-modal__footer">
						<button class="c-button c-button--full c-button--primary"
							data-dialog-close>확인</button>
					</div>
				</div>
			</div>
		</div>

	</t:putAttribute>

</t:insertDefinition>