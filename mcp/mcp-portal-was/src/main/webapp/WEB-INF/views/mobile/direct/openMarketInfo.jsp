<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>


<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="contentAttr">
	<div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">오픈마켓 구매하기<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="upper-banner__box upper-banner__box--type3">
        <div class="c-swiper swiper-container" ${empty topBannerList ? 'hidden' : ''}>
          <div class="swiper-wrapper">
          <c:forEach var="topList" items="${topBannerList}">
          	<div class="swiper-slide" onclick="insertBannAccess('${topList.bannSeq}', '${topList.bannCtg}')" style="${empty topList.mobileLinkUrl ? 'pointer-events:none;' : ''}">
              <div class="c-hidden">
              	<p>${topList.bannNm}</p>
              </div>
              <a href="${empty topList.mobileLinkUrl ? '' : topList.mobileLinkUrl}" target="${topList.linkTarget eq 'Y' ? '_blank' : '_self'}">
              <img src="${topList.mobileBannImgNm}" alt="${topList.imgDesc}">
              </a>
            </div>
          </c:forEach>
          </div>
          <input type="text" value="${fn:length(topBannerList)}" id="topBanLength" hidden/>
			<c:if test="${fn:length(topBannerList) gt 1}">
          		<div class="swiper-play-button stop"></div>
          		<div class="swiper-pagination"> </div>
          	</c:if>
        </div>
      </div>
      <div class="ad-store__box ad-store__box--type2" ${empty midBannerList ? 'hidden' : '' }>
        <h3 class="c-heading c-heading--type3 u-mt--0">오픈마켓 유심 판매처</h3>
        <ul class="c-store__list">
          <c:forEach var="midList" items="${midBannerList}">
          	<li onclick="insertBannAccess('${midList.bannSeq}', '${midList.bannCtg}')">
            <a href="${midList.mobileLinkUrl }"  target="${midList.linkTarget eq 'Y' ? '_blank' : '_self'}">
              <dl class="c-hidden">
                <dt>중간배너</dt>
                <dd>
                  <p>${midList.bannNm}</p>
                </dd>
              </dl>
              <img src="${midList.mobileBannImgNm}" alt="${midList.imgDesc}">
            </a>
          </li>
          </c:forEach>
        </ul>
      </div>
      <div class="banner-guide__box">
        <h3 class="ad-headline ad-headline--type2">오픈마켓에서
          <br>kt M모바일 유심 구매 가능!
        </h3>
        <p class="ad-text ad-text--type2"> 오픈마켓 유심 구매했다면? 바로 개통해보세요.</p>
        <ul class="c-step__list">
          <li>
            <dl>
              <dt>오픈마켓 찾기</dt>
              <dd>
                <img src="../../resources/images/mobile/common/ico_market.svg" alt="">
                <p>선호하는 오픈마켓을 찾아주세요.</p>
              </dd>
            </dl>
          </li>
          <li>
            <dl>
              <dt>유심구매</dt>
              <dd>
                <img src="../../resources/images/mobile/common/ico_usim_buy.svg" alt="">
                <p>원하는 유심을 구매하세요.</p>
              </dd>
            </dl>
          </li>
          <li>
            <dl>
              <dt>셀프개통</dt>
              <dd>
                <img src="../../resources/images/mobile/common/ico_phone_usim.svg" alt="">
                <p>kt M모바일 홈페이지에서 셀프개통을 진행하시면 특별한 혜택을 드립니다.</p>
              </dd>
            </dl>
          </li>
        </ul>
      </div>
      <div class="c-button-wrap c-button-wrap--column u-mt--48">
        <button class="c-button c-button--full c-button--mint" onclick="selfTimeChk()">셀프개통 하기</button>
        <a class="c-button c-button--full c-button--mint" href="/m/cs/faqList.do">자주 묻는 질문 바로가기</a>
      </div>

      <!-- <hr class="c-hr c-hr--type1 c-expand">
      <div class="banner-guide__box">
        <h3 class="ad-headline ad-headline--type2">오픈마켓 유심 구매 혜택</h3>
        <ul class="c-benefit__list">
          <li>
            <dl>
              <dt>KT WI-FI 무료이용</dt>
              <dd>
                <p> KT Wi-Fi Zone 어디서든
                  <br>인터넷을 자유롭게
                </p>
                <p class="c-sub--txt u-co-sub-2">※ 일부 요금제에 한함</p>
              </dd>
            </dl>
            <i class="c-icon c-icon--benefit-wifi" aria-hidden="true"></i>
          </li>
          <li>
            <dl>
              <dt>휴대폰 안심보험 가입 가능</dt>
              <dd>
                <p>월 3,600원으로 내 휴대폰을 안전하게!</p>
                <p class="c-sub--txt u-co-sub-2">※ 위 금액은 USIM형 기준(USIM 고가형은 월 5,500원)</p>
              </dd>
            </dl>
            <i class="c-icon c-icon--benefit-secure" aria-hidden="true"></i>
          </li>
          <li>
            <dl>
              <dt>제휴카드 가입 시 추가 할인</dt>
              <dd>
                <p>
                	KB국민/하나/롯데/현대/IBK/우리
                </p>
              </dd>
            </dl>
            <i class="c-icon c-icon--benefit-card" aria-hidden="true"></i>
          </li>
        </ul>
      </div> -->


      <hr class="c-hr c-hr--type1 c-expand">
      <div class="c-notice__box">
        <h3 class="c-heading c-heading--type3">유의사항</h3>
        <hr class="c-hr c-hr--type3">
        <div class="c-accordion c-accordion--type1">
          <ul class="c-accordion__box" id="accordionB">
            <li class="c-accordion__item">
              <div class="c-accordion__head" data-acc-header>
                <button class="c-accordion__button" type="button" aria-expanded="false">셀프개통</button>
              </div><!--  [2021-11-29] 영역 확장 클래스(c-expand) 추가=====-->
              <div class="c-accordion__panel expand c-expand">
                <div class="c-accordion__inside">
                  <div>
                    ${storeInfoFormDt.docContent }
                  </div>
                </div>
              </div>
            </li>
            <li class="c-accordion__item">
              <div class="c-accordion__head" data-acc-header>
                <button class="c-accordion__button" type="button" aria-expanded="false">공통</button>
              </div><!--  [2021-11-29] 영역 확장 클래스(c-expand) 추가=====-->
              <div class="c-accordion__panel expand c-expand">
                <div class="c-accordion__inside">
                  <div>
                    ${storeEtcFormDt.docContent }
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>

      <%-- <hr class="c-hr c-hr--type1 c-expand">
      <div class="banner-guide__box">
        <input type="hidden" id="totalBoardCnt" value="${total}" />
        <input type="hidden" name="ctgVal" id="ctgVal" value="${boardDto.ctgVal}" />
        <input type="hidden" name="noticePageNo" id="noticePageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}" />
            <input type="hidden" id="sbstCtg" value="" />
        <h3 class="ad-headline ad-headline--type2">자주묻는 질문</h3>
        <div class="c-form c-form--search u-mt--40">
          <div class="c-form__input">
            <input class="search-input c-input" id="searchNm" name="searchNm" type="text"
                            placeholder="검색어를 입력해주세요" title="검색어 입력"
                            value="${boardDto.searchNm }"
                            onkeypress="if(event.keyCode==13) {faqSearch(); return false;}" maxlength="20">
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
        <div class="c-filter c-filter--accordion c-expand">
          <button class="c-filter--accordion__button">
            <div class="c-hidden">필터 펼치기</div>
          </button>
          <div class="c-filter__inner">
              <c:set var="sbstCtgList"
                value="${nmcp:getCodeList(directUsimFaqCtg)}" />
            <fmt:parseNumber var="widthSize"
                value="${100/(fn:length(sbstCtgList)+1)}" integerOnly="true" />
            <button class="c-button c-button--sm c-button--white is-active"
                sbstCtg="" onclick="selectNoticeCtg('',this)">전체<span class="c-hidden">선택됨</span></button>
            <c:forEach items="${sbstCtgList}" var="sbstCtg">
                <button class="c-button c-button--sm c-button--white"
                    type="button"
                    onclick="selectNoticeCtg('${sbstCtg.dtlCd}',this);"
                    sbstCtg="${sbstCtg.dtlCd}">${sbstCtg.dtlCdNm}</button>
            </c:forEach>
          </div>
        </div>
        <div class="c-nodata" style="display: none;">
            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
            <p class="c-nodata__text">검색 결과가 없습니다.</p>
        </div>
        <div class="c-accordion c-accordion--type1 faq-accordion"
            id="accordion_runtime_update">
            <ul class="c-accordion__box" id="noticeListTBody">

            </ul>
        </div>

        <div class="c-button-wrap" id="c-button-wrap"
            onclick="goNextNoticePage();">
            <button class="c-button c-button--full">
                더보기 <span class="c-button__sub" id="noticePageNav"><fmt:formatNumber value="${listCount}" pattern="#,###"/>/<fmt:formatNumber value="${pageInfoBean.totalCount}" pattern="#,###"/></span>
                <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
            </button>
            <span
                id="maxPage" style="display: none;"></span>
        </div>
      </div> --%>


    </div>
 <script type="text/javascript" src="/resources/js/mobile/usim/openMarketInfo.js"></script>
 <script src="../../resources/js/mobile/vendor/swiper.min.js"></script>
  <!--[2021-12-17] script 수정-->
  <script>
  $(document).ready(function(){
	if($("#topBanLength").val() > 1){
	    var swiper = new Swiper(".c-swiper", {
	      loop: true,
	      pagination: {
	        el: ".swiper-pagination",
	        type: "fraction",
	      },
	      autoplay: {
	        delay: 3000,
	        disableOnInteraction: false,
	      },
	      on: {
	        init: function() {
	          var swiper = this;

		          var autoPlayButton = this.el.querySelector(".swiper-play-button");
		          autoPlayButton.addEventListener("click", function() {
		            if (autoPlayButton.classList.contains("play")) {
		              autoPlayButton.classList.remove("play");
		              autoPlayButton.classList.add("stop");

		              swiper.autoplay.start();
		            } else {
		              autoPlayButton.classList.remove("stop");
		              autoPlayButton.classList.add("play");

		              swiper.autoplay.stop();
		            }
		          });

	        },
	      },
	    });
	}
  });


	 var selfTimeChk = function(){

         ajaxCommon.getItem({
             id:'isSimpleApply'
             ,cache:false
             ,url:'/appForm/isSimpleApplyAjax.do'
             ,data: ""
             ,dataType:"json"
             ,async:false
         },function(jsonObj){

             if (!jsonObj.IsMacTime && !jsonObj.IsMnpTime) {
                 MCP.alert('셀프개통이 불가능한 시간입니다.<br/><br/>셀프개통 가능한 시간은<br/>신규(08:00~21:50)<br/>번호이동(10:00~19:50) 입니다.');
             } else if (!jsonObj.IsMnpTime) {
                 MCP.alert('신규 가입만 가능한 시간입니다.<br/><br/>셀프개통 가능한 시간은<br/>신규(08:00~21:50)<br/>번호이동(10:00~19:50) 입니다.', function (){
                     location.href='/m/appForm/appFormDesignView.do';
                 });
             } else {
                 location.href='/m/appForm/appFormDesignView.do';
             }
         });
 	}
  </script>

	</t:putAttribute>
</t:insertDefinition>