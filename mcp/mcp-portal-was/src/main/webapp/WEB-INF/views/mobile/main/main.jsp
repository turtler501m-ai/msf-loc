<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<c:set var="jsver" value="${nmcp:getJsver()}" />

<t:insertDefinition name="mlayoutMainDefault">
	<t:putAttribute name="mainTopBannerAttr">
		<%-- 메인 상단 띠배너 --%>
		<section id="sec_M008" style="display: none;">
			<div class="top-banner ly-top-banner is-active" id="top_banner">
				<div class="swiper top-banner-swiper">
					<div id="M008" class="swiper-wrapper">
					</div>
					<a class="top-banner--close" href="#n">
						<i class="c-icon c-icon--close-ty4" aria-hidden="true"></i>
					</a>
				</div>
			</div>
		</section>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">
		<div id="main-content" class="ly-content u-pt--0 main">

			<!-- 메인 이벤트 배너 뱃지-->
			<a href="#n" id="events_open" class="main-banner-badge">
				<i class="c-icon c-icon--plus-2" aria-hidden="true"></i>
			</a>
			<!--이벤트 닫기-->
	        <a class="main-close-badge" id="events_close" href="#n">
	            <i class="c-icon c-icon--close-ty3" aria-hidden="true"></i>
	        </a>

	      	<%--이벤트판 전환을 위해 메인 전체를 main-panel div 밑으로 이동 --%>
	      	<div class="main-panel" id="main_panel">
	      		<%-- [START] 섹션1 : 메인 상단 빅Banner 조회 (M001) --%>
				<section id="sec_M001" class="c-expand" style="display: none;">
					<div id="mainBanner" class="main-swiper">
						<div class="swiper-container">
							<div id="M001" class="swiper-wrapper">
							</div>
							<!-- bg 이펙트를 위한 effect-bg div -->
							<div class="effect-bg"></div>
						</div>
						<div class="swiper-controls-wrap">
							<div class="swiper-controls">
								<div class="swiper-button-prev"></div>
								<div class="swiper-pagination"></div>
								<div class="swiper-button-next"></div>
								<button class="swiper-button-pause is-show" type="button"></button>
								<button class="swiper-button-play" type="button"></button>
							</div>
						</div>
					</div>
				</section>
				<%-- [END] 섹션1 : 메인 상단 빅Banner 조회 (M001) --%>
				<%-- [START] 섹션2 : 메인 주요메뉴 Banner (M002) --%>
				<section id="sec_M002" style="display: none;">
					<div id="M002" class="main-service c-expand u-pl--16">
					</div>
				</section>
				<%-- [END] 섹션2 : 메인 주요메뉴 Banner (M002) --%>

				<%--이벤트 뱃지 보이기 시작 트리거 위치--%>
				<span id="event_badge_show"></span>
				<%-- [START] 섹션3 : 메인 시선집중(1) (M003) --%>
				<section id="sec_M003" style="display: none;">
					<div class="banner-box u-mb--24 u-flex-start-center">
						<span class="banner-title">
							<img src="/resources/images/mobile/content/img_banner_title_01.png" alt="시선집중">
						</span>
						<span class="c-text c-text--type4 u-fw--medium u-ml--6">이달의 이벤트</span>
						<i class="u-ml--6 c-icon--hot"></i>
					</div>
					<div class="swiper-container swiper-main-sw-event-banner" id="swiperCardBannerA">
						<div id="M003" class="swiper-wrapper">
						</div>
						<div class="swiper-pagination u-mb--40"></div>
					</div>
				</section>
				<%-- [END] 섹션3 : 메인 시선집중(1) (M003) --%>
				<%--[START] 섹션4 : 유심요금제 --%>
				<section id="sec_rate" class="u-mb--40" style="display: none;">
					<div class="c-tabs c-tabs--type2 u-mb--40" id="sctop1" data-ignore="true">
						<div class="c-tabs__list c-expand">
							<button class="c-tabs__button is-active" data-scroll-top="#sctop1">유심 요금제</button>
							<button class="c-tabs__button" data-scroll-top="#sctop2">휴대폰 추천</button>
						</div>
						<div class="c-tabs__panel" >



							<div class="main-usim">
								<ul id="ulRateCtgList" class="main-usim__list" >

								</ul>
								<!-- 더보기 버튼, 5개이상일 경우 노출, 클릭 시 5개씩 추가 -->
								<div class="c-button-wrap u-mt--20">
									<button id="btnMoreUsimList" class="c-button c-button--sm c-button--gray">+ 더보기</button>
								</div>
							</div>


						</div>
					</div>
				</section>
				<%--[END] 섹션4 : 유심요금제 --%>
				<%-- [START] 섹션5 : 설문영역 --%>
				<section id="sec_survey" class="survey" style="display: none;">
					<!--설문 애니메이션(고정 애니메이션)-->
					<div class="survey__actor">
						<!-- 디폴트는 01.json play-->
						<div class="survey__actor-ani" data-ani-url="/resources/animation/mobile/lottie/01.webjson"></div>
						<!--설문중에는 02.json play-->
						<div class="survey__actor-ani" data-ani-url="/resources/animation/mobile/lottie/02.webjson"></div>
						<!--설문중에는 02.json play-->
						<div class="survey__actor-ani" data-ani-url="/resources/animation/mobile/lottie/04.webjson"></div>
					</div>
					<!--설문 타이틀-->
					<div class="survey__head">
						<!--디폴트로 나와있는 타이틀-->
						<div class="survey__head-title maintitle">
							<p class="c-text c-text--type5">내게 맞는 상품찾기</p>
							<p class="c-text c-text--type7 u-fw--bold">
								<span class="u-co-mint">유심요금제&nbsp;</span>고민이라면?
							</p>
						</div>
						<!--설문 갯수에 따라.. Q1, Q2, Q3 ...-->
						<div class="survey__head-title question">Q1.</div>
						<div class="survey__head-title question">Q2.</div>
						<!--설문완료시에 나오는 타이틀-->
						<div class="survey__head-title success">
							<p class="c-text c-text--type5">고객님께 딱 맞는</p>
							<p class="c-text c-text--type7 u-fw--bold">
								<span class="u-co-mint">유심요금제&nbsp;</span>추천합니다!
							</p>
						</div>
					</div>
					<!--설문 컨텐츠 ( Swiper로 구성되어있음 )-->
					<div class="survey__content swiper-container">
						<div id="inqrBasList" class="swiper-wrapper">
						</div>
					</div>
				</section>
				<%-- [END] 섹션5 : 설문영역 --%>
				<%-- [START] 섹션6 : 메인 시선집중(2) (M004) --%>
				<section id="sec_M004" style="display: none;">
					<div class="banner-box u-mb--24 u-flex-start-center">
						<span class="banner-title">
							<img src="/resources/images/mobile/content/img_banner_title_02.png" alt="시선집중">
						</span>
						<span class="c-text c-text--type4 u-fw--medium u-ml--6">오늘의 상품제안</span>
						<i class="u-ml--6 c-icon--hot"></i>
					</div>
					<div class="swiper-container swiper-main-sw-event-banner"
						id="swiperCardBannerB">
						<div id="M004" class="swiper-wrapper">
						</div>
						<div class="swiper-pagination u-mb--40"></div>
					</div>
				</section>
				<%-- [END] 섹션6 : 메인 시선집중(2) (M004) --%>
				<%--[START] 섹션7 : 휴대폰 요금제 --%>
				<section id="sec_phone" style="display: none;">
					<div class="c-tabs c-tabs--type2" id="sctop2" data-ignore="true">
						<div class="c-tabs__list c-expand">
							<button class="c-tabs__button" data-scroll-top="#sctop1">유심요금제</button>
							<button class="c-tabs__button is-active" data-scroll-top="#sctop2">휴대폰 추천</button>
						</div>
						<div class="c-tabs__panel" data-swiper-target="#product_phone_charge">
							<div class="c-tabs c-tabs--type3" data-ignore="true">
								<div id="rateAdsvcScndCtgList" class="c-tabs__list u-bb--0 c-expand u-ta-center">
								</div>
							</div>
							<div class="swiper-container u-pt--24 u-pb--5" id="product_phone_charge">
								<div id="rateAdsvcList" class="swiper-wrapper">
								</div>
							</div>
						</div>
					</div>
				</section>
				<%--[END] 섹션7 : 휴대폰 요금제 --%>
				<%--이벤트 뱃지 숨기기 시작 트리거 위치--%>
				<span id="event_badge_hide"></span>
				<%--[START] 섹션8 : 메인 혜택안내 (M005) --%>
				<section id="sec_M005" class="benefit-info-box u-mt--64" style="display: none;">
					<h3>
						<a class="u-flex-start-center" href="/m/event/eventBoardList.do">
							<img src="/resources/images/mobile/content/img_benefit_title.svg" alt="혜택안내">
							<i class="c-icon c-icon--arrow-gray" aria-hidden="true"></i>
						</a>
					</h3>
					<ul id="M005" class="benefit-list">
					</ul>
				</section>
				<%--[END] 섹션8 : 메인 혜택안내 (M005) --%>
				<%--[START] 섹션9 : 사용후기 --%>
				<section id="sec_requestReview" class="review-info-box c-expand u-mt--64" style="display: none;">
					<h3 class="review-info-box__title">
						<a class="u-flex-start-center" href="/m/requestReView/requestReView.do">
							<img src="../resources/images/mobile/content/img_review_title.svg" alt="사용후기">
							<i class="c-icon c-icon--arrow-gray" aria-hidden="true"></i>
						</a>
					</h3>
					<ul id="requestReviewList" class="main-review-list u-mt--32">
					</ul>
				</section>
				<%--[END] 섹션9 : 사용후기 --%>
				<%--[START] 섹션10 : 메인 시선집중(3) (M006) --%>
				<section id="sec_M006" class="u-mt--56" style="display: none;">
					<div class="banner-box u-mb--24 u-flex-start-center">
						<span class="banner-title">
							<img src="/resources/images/mobile/content/img_banner_title_03.png" alt="시선집중">
						</span>
						<span class="c-text c-text--type4 u-fw--medium u-ml--6">특별한 혜택</span>
						<i class="u-ml--6 c-icon--hot"></i>
					</div>
					<div class="swiper-container swiper-main-sw-event-banner" id="swiperCardBannerC">
						<div id="M006" class="swiper-wrapper">
						</div>
						<div class="swiper-pagination"></div>
					</div>
				</section>
				<%--[END] 섹션10 : 메인 시선집중(3) (M006) --%>
				<%--[START] 섹션11 : 공지, 메인 퀵메뉴 (M007) --%>
				<section id="sec_M007" class="u-mt--40" style="display: none;">
					<ul id="M007" class="link-menu-box u-mt--49">
					</ul>
					<a href="#" id="workNoti" class="c-button c-button--full c-button--white with-right-arrow u-mt--60">
						<strong class="c-text c-text--type3 u-mr--16">공지</strong>
						<span id="workNotiSpan" class="c-text c-text--type3 c-text-ellipsis u-pr--32">-</span>
						<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
					</a>
					<a class="c-button c-button--full c-button--red u-mt--20" href="tel:1899-5000">
						<i class="c-icon c-icon--call-white" aria-hidden="true"></i>
						<span class="c-text c-text--type3">1899-5000</span>
					</a>
				</section>
				<%--[END] 섹션11 : 공지, 메인 퀵메뉴 (M007) --%>
	      	</div>
	      	<%-- 이벤트판 전환을 위한 fake cover 추가--%>
			<div class="events-cover" id="events_cover"></div>


		</div>
		<%-- 팝업 : 메인(배너) (MB01) --%>
		<div class="c-modal" id="event_banner_list" role="dialog" tabindex="-1" aria-describedby="#main_event_bannerlist_desc" aria-labelledby="#main_event_bannerlist_title">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content u-bg--transparent">

					<div class="c-modal__header sr-only">
						<h1 class="c-modal__title" id="main_event_bannerlist_title">이벤트배너 모아보기</h1>
					</div>
					<div class="main-bs__footer u-bg--transparent c-flex--jfy-center">
						<a class="u-block" data-dialog-close>
							<i class="c-icon c-icon--close-ty2" aria-hidden="true"></i>
						</a>
					</div>
					<div id="MB01" class="c-modal__body" id="main_event_bannerlist_desc">
					</div>
				</div>
			</div>
		</div>


		<%-- 팝업 : 유심 추천 상세 --%>
		<div class="c-modal main-usim" id="mainUsimModal1" role="dialog" tabindex="-1" aria-describedby="#mainUsimModalTitle">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="mainUsimModalTitle"></h1>
						<button class="c-button" data-dialog-close="">
							<i class="c-icon c-icon--reset" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body">
						<div class="rate-content">
							<ul class="rate-content__list">
							</ul>
						</div>

					</div>
				</div>
			</div>
		</div>


	</t:putAttribute>
  	<t:putAttribute name="scriptHeaderAttr">
  		<script type="text/javascript" src="/resources/js/mobile/main/main.js?version=25-12-30"></script>
	  	<script src="/resources/js/mobile/vendor/swiper.min.js"></script>
	  	<script src="/resources/js/mobile/vendor/lottie.min.js"></script>
	  	<script>




	    // 설문박스
	    function surveyBox() {
	      //설문지 스와이프
	      var surveySwiper = new Swiper(".survey__content.swiper-container", {
	        spaceBetween: 25,
	        autoHeight: true,
	        initialSlide: 1,
	        allowTouchMove: false,
	        on: {
	          slideChange: function() {
	            var currentIndex = surveySwiper.activeIndex;
	            changeTitle(currentIndex + 1);
	            if (currentIndex === 0) {
	              playAnimation(1);
	            } else if (currentIndex == surveySwiper.slides.length - 1) {
	              playAnimation(2);
	              changeTitle("success");
	            }
	          },
	        },
	      });

	      //설문 종료 시 추천상품 스와이퍼
	      var surveyProductsSiper = new Swiper(".survey__success.swiper-container", {
	        spaceBetween: 10,
	        autoHeight: true,
	        observeParents: true,
	        observer: true,
	        on: {
	        	setTransition: function () {
		        	if(surveySwiper)
    		            surveySwiper.updateAutoHeight(200);
    		    },
		    },
	        pagination: {
	          el: ".survey .swiper-pagination",
	        },
	      });

	      // 설문 헤드
	      var openSurveyButton = document.querySelector(".survey__head");
	      // 설문지의 다음버튼( 클릭시 다음 설문으로 이동 )
	      var nextStepButtons = document.querySelectorAll("[data-next-btn]");
	      // 설문 컨텐츠
	      var surveyContentWrap = document.querySelector(".survey__content");
	      // 설문 타이틀( 설문 컨텐츠 변경 시 함께 변경 됨/ show, hide)
	      var surveyTitles = document.querySelectorAll(".survey__head-title");
	      // 설문 애니메이션( 우측상단 )
	      var animations = document.querySelectorAll(".survey__actor-ani");
	      // 설문 애니메이션 인스턴스 리스트
	      var aniIns = [];

	      // 설문 애니메이션 play(해당 설문지에 해당하는 애니메이션만 show)
	      function playAnimation(index) {
	        for (var i = 0; i < aniIns.length; i++) {
	          if (i === index) {
	            aniIns[index].wrapper.style.display = "block";
	            setTimeout(function() {
	              aniIns[index].play();
	            }, 0);
	          } else {
	            aniIns[i].stop();
	            aniIns[i].wrapper.style.display = "none";
	          }
	        }
	      }

	      // 설문 변경 시 설문 타이틀도 함께 변경
	      function changeTitle(index) {
	        if (index === "success") {
	          index = surveyTitles.length - 1;
	          $(".survey__head-title.maintitle").removeClass('u-block');
	        }else {
       		  $(".survey__head-title.maintitle").addClass('u-block');
	        }
	        for (var i = 0; i < surveyTitles.length; i++) {
	          var show = i === index ? "block" : "none";
	          surveyTitles[i].style.display = show;
	        }
	      }

	      // 설문 박스 오픈
	      openSurveyButton.addEventListener("click", function(e) {
	        e.preventDefault();
	        surveyContentWrap.style.height = surveyContentWrap.scrollHeight + 1 + "px";
	        setTimeout(function() {
	          surveyContentWrap.style.height = "auto";
	        }, 510);
	        surveySwiper.slideTo(0, 500);

	        var surveyTop = document.querySelector(".survey").getBoundingClientRect().top - 20 + window.pageYOffset;
	        // 설문 박스 오픈 시 설문박스 위치로 스크롤
	        /* scrollTo({
	          top: surveyTop,
	          behavior: "smooth",
	        }); */
	      });

	      // 애니메이션 초기화
	      [].forEach.call(animations, function(content) {
	        var url = content.getAttribute("data-ani-url");
	        var ani = lottie.loadAnimation({
	          container: content,
	          renderer: "svg",
	          loop: true,
	          autoplay: true,
	          path: url,
	        });

	        ani.pause();
	        aniIns.push(ani);
	        content.style.display = "none";
	      });

	      // 설문컨텐츠의 다음버튼 클릭 이벤트
	      [].forEach.call(nextStepButtons, function(button) {
	        button.addEventListener("click", function(el) {
	          el.preventDefault();
	          if (el.currentTarget.getAttribute("data-next-btn") === "reset") {
	        	// 요금제추천 Param 초기화
				ansNo = "";

	            surveySwiper.slideTo(0);
	          } else {
	        	// 다음 버튼
	            let v_ansNo = ansNoValidate(this);
				if(ajaxCommon.isNullNvl(v_ansNo, "") == "") {
					MCP.alert("답변을 선택해 주세요.");
					return false;
				}
				ansNo = ansNo +""+ v_ansNo;

				// 결과조회
				if (el.currentTarget.getAttribute("data-next-btn") === "result") {
				  getResultInqr(this);
	            }

	            surveySwiper.slideNext();
	          }
	        });
	      });

	      playAnimation(0);
	      changeTitle(0);
	    }

	    // 메인배너
	    function mainBanner() {
	      function textMotion(swiper) {
	        var slides = swiper.slides;
	        var currentSlide = swiper.slides[swiper.activeIndex];
	        for (var i = 0; i < slides.length; i++) {
	          if (i === swiper.activeIndex) {
	            slides[swiper.activeIndex].classList.add("is-active");
	          } else {
	            slides[i].classList.remove("is-active");
	          }
	        }
	      }

	      var bg = document.querySelector(".main-swiper .effect-bg");

	      function initSlideBg(swiper) {
			var currentSlide = swiper.slides[swiper.activeIndex];
			if(!currentSlide) return;
			var currentBgColor = currentSlide.getAttribute("data-background") || "#fff";
            var slideContainer = swiper.el;
  	      	var bg = document.querySelector(".main-swiper .effect-bg");

			bg.classList.remove("is-active");
            bg.style.backgroundColor = currentBgColor;
            bg.classList.add("is-active");

            slideContainer.style.backgroundColor = currentBgColor;
	      }

	      //main-swiper 작동
	      var swiperMainBanner = new Swiper(".main-swiper .swiper-container", {
	    	loop: true,
	    	loopedSlides: 1,
	        observer: true,
	        observeParents: true,
	        on: {
	          //배경칼러 등록 시 컬러 변환함수
	          slideChange: function() {
	            var currentSlide = swiperMainBanner.slides[swiperMainBanner.activeIndex];
	            if(!currentSlide) return; // 2022.04.09추가
	            var currentBgColor = currentSlide.getAttribute("data-background") || "#fff";
	            bg.classList.remove("is-active");
	            bg.style.backgroundColor = currentBgColor;
	            bg.offsetHeight;
	            bg.classList.add("is-active");
	          },
	          slideChangeTransitionEnd: function() {
	            var currentSlide = swiperMainBanner.slides[swiperMainBanner.activeIndex];
	            if(!currentSlide) return; // 2022.04.09추가
	            var currentBgColor = currentSlide.getAttribute("data-background") || "#fff";
	            var slideContainer = swiperMainBanner.el;
	            slideContainer.style.backgroundColor = currentBgColor;
	            textMotion(swiperMainBanner);
	          },
	          update: function() {
	        	  var swiper = this;
	        	  swiper.loopDestroy();
	        	  if(swiper.slides.length > 1) {
	        		  swiper.loopCreate();
	        		  swiper.slideToLoop(0);
	        	  	var initTimer = setTimeout(function() {
	        	  		initSlideBg(swiper);
	        	  	}, 100);
	                textMotion(swiper);
	        	  }
	          }
	        },
	        pagination: {
	          el: ".main-swiper .swiper-pagination",
	          type: "fraction",
	        },
	        navigation: {
	          nextEl: ".main-swiper .swiper-button-next",
	          prevEl: ".main-swiper .swiper-button-prev",
	        },
	        autoplay: {
	          delay: 5000,
	          disableOnInteraction: false,
	        },
	      });
	      textMotion(swiperMainBanner);
 	      var pauseBtn = document.querySelector(".main-swiper .swiper-button-pause");
	      var playBtn = document.querySelector(".main-swiper .swiper-button-play");
	      pauseBtn.addEventListener("click", function() {
	        swiperMainBanner.autoplay.stop();
	        playBtn.classList.add("is-show");
	        pauseBtn.classList.remove("is-show");
	      });
	      playBtn.addEventListener("click", function() {
	        swiperMainBanner.autoplay.start();
	        pauseBtn.classList.add("is-show");
	        playBtn.classList.remove("is-show");
	      });

	      return swiperMainBanner;
	    }

	    // 이벤트 모아보기 팝업
	    function eventsPopupBanner() {
	      // 이벤트배너 오픈 버튼
	      var openner = document.querySelector("#events_open");
	      // 이벤트배너 팝업
	      var eventDialog = document.querySelector("#event_banner_list");

	      // 등장애니메이션 추가를 위해 인터렉션옵저버 등록
	      var io = new IntersectionObserver(
	        function(entries, observer) {
	          [].forEach.call(entries, function(entry) {
	            if (entry.isIntersecting) {
		          //console.log('entry.target: ',entry.target );
	              entry.target.classList.add("active");
	              observer.unobserve(entry.target);
	            }
	          });
	        }, {
	          root: null,
	          threshold: 0.3,
	        }
	      );

	      // 이벤트 팝업 오픈되면 실행될 이벤트
	      eventDialog.addEventListener(KTM.Dialog.EVENT.OPEN, function() {
	        eventDialog.querySelector(".c-modal__body").scrollTo(0, 0);
		    // 이벤트배너에 포함된 배너들의 애니메이션 트리거 식별자
		    var banners = document.querySelectorAll("[data-move-fadein]");
	        [].forEach.call(banners, function(banner) {
	          io.observe(banner);
	        });
	      });

	      // 이벤트 팝업 닫히면 필요없는 클래스 삭제 및 옵저버 remove
	      eventDialog.addEventListener(KTM.Dialog.EVENT.CLOSED, function() {
		    // 이벤트배너에 포함된 배너들의 애니메이션 트리거 식별자
		    var banners = document.querySelectorAll("[data-move-fadein]");
	        [].forEach.call(banners, function(el) {
	          el.classList.remove("active");
	          io.unobserve(el);
	        });
	      });

	      // 이벤트 뱃지 클릭
	      openner.addEventListener("click", function(e) {
	        e.preventDefault();
	        popupOpen();
	      });

	      // 이벤트 팝업 오픈
	      function popupOpen() {
	        var popInstance = KTM.Dialog.getInstance(eventDialog) ? KTM.Dialog.getInstance(eventDialog) : new KTM.Dialog(eventDialog);
	        popInstance.open();
	      }
	    }

	    // 시선집중 배너
	    function bannerSwiper(target) {
	      var pagination = document.querySelector(target + " .swiper-pagination");
	      new Swiper(target, {
	        slidesPerView: 1,
	        spaceBetween: 20,
	        //loop: true,
	        pagination: {
	          el: pagination,
	        },
	      });
	    }

	    //유심요금제, 휴대폰요금제 제품별, 가격대별 컨텐츠 Swiper
	    function productSwiper(target) {
	      var wrap = document.querySelector(target);
	      var swiperTargetName = wrap.getAttribute("data-swiper-target");
	      var swiperTarget = document.querySelector(swiperTargetName);
	      var tabButtons = wrap.querySelectorAll("[data-swiper-active]");
	      [].forEach.call(tabButtons, function(el) {
	        el.addEventListener("click", function(e) {
	          e.preventDefault();
	          var index = e.currentTarget.getAttribute("data-swiper-active");
	          if (productSwiper) {
	            productSwiper.slideTo(index);
	          }
	        });
	      });
	      var productSwiper = new Swiper(swiperTarget, {
	        observeParents: true,
	        observer: true,
	        autoHeight: true,
	        spaceBetween: 20,
	        on: {
	          slideChange: function() {
	            // 버튼 활성화 처리
	            var currentIndex = productSwiper.activeIndex;
	            for (var i = 0; i < tabButtons.length; i++) {
	              if (i === currentIndex) {
	                tabButtons[currentIndex].classList.add("is-active");
	              } else {
	                tabButtons[i].classList.remove("is-active");
	              }
	            }
	          },
	        },
	      });
	    }

	    // 이벤트 판 전환( 메인-> 이벤트판, 이벤트판-> 메인 )
	    function eventsPanel(swiperObjects) {
	      var wrap = document.querySelector(".ly-content.main");
	      var eventClose = document.querySelector("#events_close");
	      var mainPanel = document.querySelector("#main_panel");
	      var cover = document.querySelector("#events_cover");

	      function gotoEventPanel() {
	        cover.style.display = "block";
	        cover.offsetHeight;
	        // 이벤트전환 커버 등장
	        cover.classList.add("is-active");
	        // 이벤트전환 커버 등장 후
	        cover.addEventListener("transitionend", function transitionend(event) {
	          cover.removeEventListener("transitionend", transitionend);
	          // 화면 스크롤 처리
	          window.scrollTo(0, 0);
	          // 커버 페이드아웃 처리
	          cover.classList.add("fade-out");
	          // 이벤트판 화면에 노출 준비
	          eventPanel.style.display = "block";
	          const cache = eventPanel.offsetHeight;
	          // 이벤트판 슬라이드 인
	          eventPanel.classList.add("slide-in");
	          eventPanel.addEventListener("transitionend", function transitionendEventPanel(event) {
	            cover.style.display = "";
	            wrap.classList.add("active-event");
	            eventPanel.removeEventListener("transitionend", transitionendEventPanel);
	            mainPanel.style.display = "none";
	            cover.classList.remove("fade-out", "is-active");
	            eventPanel.classList.remove("slide-in");
	            eventPanel.classList.add("is-active");
	          });
	        });
	      }

	      function gotoMainPanel() {
	        // 이벤트전환 커버 등장
	        cover.style.display = "block";
	        cover.offsetHeight;
	        cover.classList.add("is-active");
	        cover.addEventListener("transitionend", function transitionend(event) {
	          cover.removeEventListener("transitionend", transitionend);
	          // 화면 스크롤 처리
	          window.scrollTo(0, 0);
	          eventPanel.style.display = "";
	          eventPanel.classList.remove("is-active");
	          mainPanel.style.display = "";
	          cover.classList.remove("is-active");
	          wrap.classList.remove("active-event");
	          setTimeout(function() {
	            cover.style.display = "";

	            if(mainBannerObject.slide.length > 1) {
	            	mainBannerObject.slideToLoop(0);
	            }
	          }, 500);
	        });
	      }

	      window.__goEventPanel = function() {
	        gotoEventPanel();
	      };
	      window.__goMainPanel = function() {
	        gotoMainPanel();
	      };

	      eventClose.addEventListener("click", function(e) {
	        e.preventDefault();
	        gotoMainPanel();
	      });

	    }

	    // 이벤트 판 ()
	    function aniBannerSwiper(target) {
	      var pagination = document.querySelector(target + " .swiper-pagination");
	      var animations = [];
	      var swiper = new Swiper(target, {
	        spaceBetween: 50,
	        observeParents: true,
	        observer: true,
	        pagination: {
	          el: pagination,
	        },
	        on: {
	          // 활성화된 배너만 애니메이션 플레이
	          slideChange: function() {
	            var currentIndex = swiper.activeIndex;
	            for (var i = 0; i < animations.length; i++) {
	              if (i === currentIndex) {
	                animations[currentIndex].play();
	              } else {
	                animations[i].stop();
	              }
	            }
	          },
	        },
	      });
	      var aniContents = document.querySelectorAll(target + " .ani-banner");
	      [].forEach.call(aniContents, function(el) {
	        var aniContainer = el.querySelector(".ani-banner__content");
	        var url = aniContainer.getAttribute("data-ani-url");
	        var ani = lottie.loadAnimation({
	          container: aniContainer,
	          renderer: "svg",
	          loop: true,
	          autoplay: false,
	          path: url,
	        });
	        animations.push(ani);
	      });
	      animations[0].play();

	      return swiper;
	    }

	 	// [2022-02-22] 메인 카드 더보기
	    function mainCardToggle() {
	      var cardTargetName = document.querySelectorAll('[data-more]');

	      [].forEach.call(cardTargetName, function(el) {
		      if(!el.isClickEvent) {
		    	  el.isClickEvent = true;
		        el.addEventListener('click', function() {
			          var mainCardList = document.querySelector(el.getAttribute('data-more'));

			          if (!mainCardList.classList.contains('is-active')) {
			            mainCardList.classList.add('is-active');
			            el.style.display = 'none';
			          } else {
			            mainCardList.classList.remove('is-active');
			            el.style.display = 'block';
			          }
			          KTM.resizeDispatcher();
			        });
			   }
	      });
	    }

	    var mainBannerObject = null;
	    document.addEventListener("UILoaded", function() {

	      // 메인배너
	      mainBannerObject = mainBanner();

			window.mainFunctions = {
			  	aniBannerSwiper1: function() {
			  		// 메인 시선집중(1)
			  		bannerSwiper("#swiperCardBannerA");
				},
				aniBannerSwiper2: function() {
					// 메인 시선집중(2)
					bannerSwiper("#swiperCardBannerB");
				},
				aniBannerSwiper3: function() {
					// 메인 시선집중(3)
					bannerSwiper("#swiperCardBannerC");
				},
				aniBannerSwiper4: function() {
					// 메인(이벤트) 이번달 이벤트 당첨자
					aniBannerSwiper("#ani_banner1");
				},
				aniBannerSwiper5: function() {
					// 메인(이벤트) 제휴 이벤트 당첨자
					aniBannerSwiper("#ani_banner2");
				},
				productSwiper2: function() {
					// 휴대폰요금제
					productSwiper('[data-swiper-target="#product_phone_charge"]');
					mainCardToggle();
				},
				surveyBox: surveyBox, // 설문박스
				eventsPopupBanner: eventsPopupBanner, // 이벤트배너 모아보기
			};

	      eventsPopupBanner();
	      eventsPanel();
	    });

		$( document ).ready(function() {
			$(".ly-header").addClass("ly-header-main");

			// 페이지 로드시 설문 시작 버튼 클릭
			setTimeout(function(){
				$('.survey__head').trigger("click");
			},300);
		});

		</script>
		<!-- 카카오 광고 분석  -->
	    <script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
		<script type="text/javascript">
		      kakaoPixel('5981604067138488988').pageView();
		</script>
        <!-- 카카오 광고 분석 end-->
  	</t:putAttribute>

</t:insertDefinition>
