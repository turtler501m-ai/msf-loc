<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<c:set var="jsver" value="${nmcp:getJsver()}" />

<t:insertDefinition name="layoutMainDefault">

	<t:putAttribute name="mainTopBannerAttr">
	    <%-- 메인 상단 띠배너 --%>
		<div id="sec_M008" class="ly-event is-active" style="display: none;">
			<div class="top-event" id="mainTopEvent">
				<div class="swiper-container">
					<div id="M008" class="swiper-wrapper">
					</div>
				</div>
				<div class="top-event__control">
					<button class="top-event__button top-event__button--prev" type="button"></button>
					<button class="top-event__button top-event__button--next" type="button"></button>
				</div>
			</div>
			<button class="c-button">
				<i class="c-icon c-icon--close-white" aria-hidden="true"></i>
				<span class="c-hidden">팝업닫기</span>
			</button>
		</div>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content u-pt--0 main" id="main-content">
			<%--div class="ly-page--title">
				<h2 class="c-page--title">kt M모바일</h2>
			</div --%>
			<div class="main-panel" id="main_panel">
				<%-- [START] 섹션1 : 메인 상단 빅Banner 조회 (M001) --%>

				<div id="main_loading" style="display:flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
					<img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
				</div>

				<section id="sec_M001" class="main-full-banner-box" style="display: none;">
					<div class="full-box-banner" id="main-full-banner">
						<div class="swiper-container">
							<div id="M001" class="swiper-wrapper">
							</div>
							<div class="swiper-controls-full-box">
								<div class="swiper-controls">
									<button class="full-swiper-button-prev" type="button"></button>
									<div class="full-swiper swiper-pagination"></div>
									<button class="full-swiper-button-next" type="button"></button>
									<button class="full-swiper-button-pause swiper-button-pause" type="button">
										<span class="c-hidden">자동재생 정지</span>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="thumb-box-banner">
						<div class="c-main-row">
							<div class="swiper-container">
								<a class="btn-call-event" data-dialog-trigger="#eventBannerPop" href="#all">
									<div class="c-hidden">이벤트 모아보기 팝업 열기</div>
									<i class="c-icon c-icon--plus-black" aria-hidden="true"></i>
								</a>
								<div id="M001_thumb" class="swiper-wrapper">
								</div>
							</div>
						</div>
					</div>
				</section>
				<%-- [END] 섹션1 : 메인 상단 빅Banner 조회 (M001) --%>
				<%-- [START] 섹션2 : 메인 주요메뉴 Banner (M002) --%>
				<section id="sec_M002" class="section-service-icon" style="display: none;">
					<div class="c-main-row">
						<div class="service-icon-swiper swiper-container">
							<div id="M002" class="swiper-wrapper">
							</div>
							<div class="swiper-scrollbar"></div>
						</div>
						<button class="service-icon-pager circle swiper-button-next" type="button" role="button" aria-label="다음 슬라이드"></button>
            <button class="service-icon-pager circle swiper-button-prev" type="button" role="button" aria-label="이전 슬라이드"></button>
					</div>
				</section>
				<%-- [END] 섹션2 : 메인 주요메뉴 Banner (M002) --%>
				<%-- [START] 섹션3 : 메인 시선집중(1) (M003) --%>
				<section id="sec_M003" class="main-suggest-box" style="display: none;">
					<div class="c-main-row">
						<div class="section-title">
							<div class="left-box">
								<h3 class="title-txt">
									<img src="/resources/images/portal/content/tit_banner_01.png" alt="HOT뜨거운">
									<span class="title-sub">이달의 이벤트</span>
									<span class="title-deco">
										<img src="/resources/images/portal/content/img_deco_hot.png" alt="hot">
									</span>
								</h3>
							</div>
						</div>
						<div class="suggest-swiper-box">
							<div class="suggest-swiper swiper">
								<div class="swiper-container">
									<div class="suggest-swiper-control">
										<button class="suggest-swiper-button-prev"></button>
										<button class="suggest-swiper-button-next"></button>
									</div>
									<div id="M003" class="swiper-wrapper">
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
				<%-- [END] 섹션3 : 메인 시선집중(1) (M003) --%>
				<%--[START] 섹션5,6 : 유심요금제, 내게 맞는 상품 찾기 --%>
				<section class="c-row u-bg-main--mint">
					<section id="sec_rate" class="section-rate" style="display: none;">
						<div class="c-main-row" id="rate_1">
							<div class="rate-box">
								<div class="c-tabs c-tabs--type1">
									<div class="c-tabs__list">
										<button class="c-tabs__button is-active" data-click-to-scroll="#rate_1" data-click-to-tab="first">
											<div class="c-hidden">유심 요금제 화면으로 이동</div>
											유심 요금제
										</button>
										<button class="c-tabs__button" data-click-to-scroll="#rate_2" data-click-to-tab="first">
											<div class="c-hidden">휴대폰 요금제 화면으로 이동</div>
											휴대폰 추천
										</button>
									</div>
								</div>
								<a class="link-all link-all--gray" href="/rate/rateList.do" title="유심요금제">
									전체보기
									<i class="c-icon c-icon--arrow-link" aria-hidden="true"></i>
								</a>
								<div class="c-tabs__panel u-block">

									<div class="c-accordion product main-usim" id="mainUsimAcc">
										<ul class="c-accordion__box">


										</ul>
										<!-- 더보기 버튼, 5개이상일 경우 노출, 클릭 시 5개씩 추가 -->
										<div class="c-button-wrap u-mt--28" >
											<button id="btnMoreUsimList" class="c-button c-button-round--sm c-button--gray" style="display:none" >+ 더보기</button>
										</div>
									</div>



								</div>
							</div>
						</div>
					</section>
					<%--[END] 섹션5 : 유심요금제 --%>
					<%-- [START] 섹션6 : 내게 맞는 상품 찾기 --%>
					<section id="sec_survey" class="section-survey" style="display: none;">
						<div class="c-main-row">
							<div class="survey">
								<div class="survey__box">
									<div class="survey-ani" aria-hidden="true" data-ani-url="/resources/animation/portal/01.webjson"></div>
									<div class="survey-ani" aria-hidden="true" data-ani-url="/resources/animation/portal/02.webjson"></div>
									<div class="survey-ani" aria-hidden="true" data-ani-url="/resources/animation/portal/04.webjson"></div>
									<div id="inqrBasList" class="survey__content sug__rate is-active">
										<a class="u-block" href="#n" role="button">
											<div class="u-fs-12 u-co-gray-9">
												내게 맞는 상품찾기
												<span class="u-fs-18 u-co-mint u-fw--bold u-block">
													유심요금제&nbsp;<span class="u-co-black u-mr--8">고민이라면?</span>
													<span class="u-fs-14 u-fw--regular u-co-black">고객님성향에 맞춘 추천 요금제를 확인해보세요</span>
												</span>
											</div>
											<div class="c-hidden">클릭하여 설문하기</div>
										</a>
									</div>
								</div>
							</div>
						</div>
					</section>
					<%--[END] 섹션6 : 내게 맞는 상품 찾기 --%>
				</section>
				<%--[END] 섹션5,6 : 유심요금제, 내게 맞는 상품 찾기 --%>
				<%-- [START] 섹션7 : 메인 시선집중(2) (M004) --%>
				<section id="sec_M004" class="main-event-guide" style="display: none;">
					<div class="c-main-row">
						<div class="section-title">
							<div class="left-box">
								<h3 class="title-txt">
									<img src="/resources/images/portal/content/tit_banner_02.png" alt="시선집중">
									<span class="title-sub">오늘의 상품제안</span>
									<span class="title-deco">
										<img src="/resources/images/portal/content/img_deco_hot.png" alt="hot">
									</span>
								</h3>
							</div>
							<div class="right-box"></div>
						</div>
						<div class="event-swiper-box">
							<div class="event-swiper swiper">
								<div class="swiper-container">
									<div class="event-swiper-control">
										<button class="event-swiper-button-prev"></button>
										<button class="event-swiper-button-next"></button>
									</div>
									<div id="M004" class="swiper-wrapper">
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
				<%-- [END] 섹션7 : 메인 시선집중(2) (M004) --%>
				<%--[START] 섹션8 : 휴대폰 요금제 --%>
				<section class="main-phone-box" id="rate_2" style="display: none;">
					<div class="c-main-row">
						<div class="phone-box-outer">
							<div class="c-tabs c-tabs--type1">
								<div class="c-tabs__list">
									<button class="c-tabs__button" data-click-to-scroll="#rate_1" data-click-to-tab="first">
										<div class="c-hidden">유심 요금제 화면으로 이동</div>
										유심 요금제
									</button>
									<button class="c-tabs__button is-active" data-click-to-scroll="#rate_2" data-click-to-tab="first">
										<div class="c-hidden">휴대폰 요금제 화면으로 이동</div>
										휴대폰 추천
									</button>
								</div>
							</div>
							<a class="link-all link-all--gray" href="/product/phone/phoneList.do" title="새 휴대폰 페이지 이동하기">
								전체보기
								<i class="c-icon c-icon--arrow-link" aria-hidden="true"></i>
							</a>
							<div class="c-tabs__panel u-block">
								<div class="c-tabs c-tabs--type2" id="phone_tab">
									<%-- 탭목록 --%>
									<div id="rateAdsvcScndCtgList" class="c-tabs__list" role="tablist">
									</div>
								</div>
								<%-- 상품목록 --%>
							</div>
						</div>
					</div>
				</section>
				<%--[END] 섹션8 : 휴대폰 요금제 --%>
				<%--[START] 섹션9 : 메인 혜택안내 (M005) --%>
				<section id="sec_M005" class="main-benefit-guide" style="display: none;">
					<div class="c-main-row">
						<div class="section-title">
							<div class="left-box">
								<h3 class="title-txt">
									<img src="/resources/images/portal/content/tit_benefit.svg" alt="혜택안내">
									<span class="title-sub">이달의 혜택</span>
									<span class="title-deco">
										<img src="/resources/images/portal/content/img_deco_now.png" alt="now">
									</span>
								</h3>
							</div>
							<div class="right-box">
								<a class="link-all" href="/event/eventBoardList.do" title="혜택안내">전체보기
									<i class="c-icon c-icon--arrow-link" aria-hidden="true"></i>
								</a>
							</div>
						</div>
						<div class="benefit-swiper-box">
							<div class="benefit-swiper swiper">
								<div class="swiper-container">
									<div id="M005" class="swiper-wrapper">
									</div>
								</div>
								<button class="swiper-button-next"></button>
								<button class="swiper-button-prev"></button>
							</div>
						</div>
					</div>
				</section>
				<%--[END] 섹션9 : 메인 혜택안내 (M005) --%>
				<%--[START] 섹션10 : 사용후기 --%>
				<section id="sec_requestReview" class="main-customer-review" style="display: none;">
					<div class="c-main-row">
						<div class="section-title">
							<div class="left-box">
								<h3 class="title-txt">
									<img src="/resources/images/portal/content/tit_review.svg" alt="사용후기">
									<span class="title-sub">고객님들의 생생한 후기</span>
									<span class="title-deco">
										<img src="/resources/images/portal/content/img_title_review_deco.png" alt="">
									</span>
								</h3>
							</div>
							<div class="right-box">
								<a class="link-all" href="/requestReView/requestReView.do" title="사용후기">전체보기
									<i class="c-icon c-icon--arrow-link" aria-hidden="true"></i>
								</a>
							</div>
						</div>
						<ul id="requestReviewList" class="review-list">
						</ul>
					</div>
				</section>
				<%--[END] 섹션10 : 사용후기 --%>
				<%--[START] 섹션11 : 메인 시선집중(3) (M006) --%>
				<section id="sec_M006" class="main-line-banner" style="display: none;">
					<div class="line-banner" id="line-banner">
						<div class="swiper-container">
							<div id="M006" class="swiper-wrapper">
							</div>
							<button class="line-swiper-button-prev" type="button"></button>
							<button class="line-swiper-button-next" type="button"></button>
						</div>
					</div>
				</section>
				<%--[END] 섹션11 : 메인 시선집중(3) (M006) --%>
				<%--[START] 섹션12 : 공지, 메인 퀵메뉴 (M007) --%>
				<section>
					<div class="c-main-row">
						<%-- 공지 --%>
						<div id="div_notice" class="notice-box" style="display: none;">
						</div>
						<%-- 메인 퀵메뉴 --%>
						<ul id="M007" class="board-link" style="display: none;">
						</ul>
					</div>
				</section>

			</div>

		</div>
		<%-- 팝업 : 메인(배너) (MB01) --%>
		<div class="c-modal c-modal--event-banner" id="eventBannerPop" role="dialog" aria-labelledby="#eventBannerPopTitle" aria-describedby="#eventBannerPopDesc">
			<div class="c-modal__dialog" role="document">
				<div class="main-bs__footer u-bg--transparent">
					<button class="u-block" data-dialog-close>
						<i class="c-icon c-icon--close-ty2" aria-hidden="true"></i>
						<span class="c-hidden">팝업닫기</span>
					</button>
				</div>
				<div class="c-modal__content u-bg--transparent">
					<div class="c-modal__header c-hidden">
						<h2 class="c-modal__title" id="eventBannerPopTitle">이벤트배너 모아보기</h2>
					</div>
					<div class="c-modal__body" id="eventBannerPopDesc">
						<ul id="MB01" class="all-event-list">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</t:putAttribute>
	<t:putAttribute name="scriptHeaderAttr">
		<script src="/resources/js/portal/vendor/swiper.min.js"></script>
  		<script src="/resources/js/portal/vendor/lottie.min.js"></script>
		<script type="text/javascript" src="/resources/js/portal/main/main.js?version=25-12-30"></script>
		<script>
			document.addEventListener('UILoaded', function() {
				// 메인 풀 배너
				function mainFullBanner() {
					// 메인 스와이퍼 썸네일(스와이퍼 활성화)
					function activeMainBannerThumSlider(idx) {
						mainThumbSwiper.slideTo(idx);
						var thumbSwiperSlides = mainThumbSwiper.slides;
						[].forEach.call(thumbSwiperSlides, function(el, i) {
							if (i === idx) {
								el.classList.add('swiper-slide-thumb-active');
							} else {
								el.classList.remove('swiper-slide-thumb-active');
							}
						});
					}

					function setTextAnimation(el, isOut) {
						var children = el.children;
						[].forEach.call(children, function(el, i) {
							if (isOut === true) {
								el.classList.remove('txt-ani' + (i + 1));
							} else {
								el.classList.add('txt-ani' + (i + 1));
							}
						});
					}

					function pickCurrent(activeIndex, slides) {
						[].forEach.call(slides, function(slide, i) {
							var infoEl = slide.querySelector('.event-headline');
							if (i === activeIndex) {
								if (infoEl) {
									//console.log('add info el - >',infoEl);
									setTextAnimation(infoEl);
								}
							} else {
								if (infoEl) {
									setTextAnimation(infoEl, true);
								}
							}
						});
					}

					var mainSwiper = bannerSwiper(
					'.full-box-banner .swiper-container', {
						effect : 'fade',
						a11yTarget : 'a',
						speed : 500,
						autoplay : {
							delay : 5000,
							disableOnInteraction : false,
						},
						navigation : {
							nextEl : '.full-swiper-button-next',
							prevEl : '.full-swiper-button-prev',
						},
						pagination : {
							el : '.full-swiper.swiper-pagination',
							type : 'fraction',
						},
						afterInit : function() {
							pickCurrent(this.activeIndex, this.slides); // pickCurrent는 activeIndex 그대로 적용
						},
						on : {
							slideChange : function() {
								//- var current = this.slides[this.activeIndex];
								//- var activeIndex = this.activeIndex;
								pickCurrent(this.activeIndex, this.slides);
								// 하단 썸네일 스와이프 슬라이드
								//activeMainBannerThumSlider(this.activeIndex);
								activeMainBannerThumSlider(this.realIndex); // activeMainBannerThumSlider는 activeIndex 대신 realIndex 적용
							},
						},
						loop: $(".full-box-banner .swiper-container .swiper-wrapper .swiper-slide").length > 1,  // 메인배너 루프 처리
					});

					// 웹 접근성 next, prev 버튼 포커스 처리
					setTimeout(function(){
						$(".full-swiper-button-next").attr("tabindex", "0");
						$(".full-swiper-button-prev").attr("tabindex", "0");
					},400);

					var mainThumbSwiper = new Swiper('.thumb-box-banner .swiper-container', {
								slidesPerView : 'auto',
								on : {
									init : function() {
										[].forEach.call(this.slides, function(slide, i) {
											slide.setAttribute('tabindex', 0);
											slide.addEventListener('click', function(e) {
												e.preventDefault();
												mainSwiper.slideTo(i+1, 300); // main bannder swiper에 loop적용으로 +1 추가
											});
											// 포커스 처리
											slide.addEventListener('focus', function(e) {
												e.preventDefault();
												mainSwiper.slideTo(i+1, 300); // main bannder swiper에 loop적용으로 +1 추가
											});
										});
										setTimeout(
										function() {
											activeMainBannerThumSlider(0);
										}, 400);
									},
								},
							});
				}


				// 아이콘 컨테이너 swiper
		        function iconScrollContainer() {
		          var slides = document.querySelectorAll('.service-icon-swiper .swiper-slide');
		          var scrollBar = document.querySelector('.section-service-icon .swiper-scrollbar');

		          if (slides.length > 7) {
		            scrollBar.style.opacity = 1;
		            KTM.swiperA11y('.service-icon-swiper', {
		              scrollbar: {
		                el: '.service-icon-swiper .swiper-scrollbar',
		                hide: false,
		              },
		              navigation : {
		            	  nextEl : '.service-icon-pager.swiper-button-next',
		            	  prevEl : '.service-icon-pager.swiper-button-prev',
	            	  },
		              pagination: {},
		              slidesPerView: 'auto',
		              a11yTarget: 'a',
		            });
		          } else {
		            // 아이콘이 8개 이하면 스크롤바 사라짐
		            scrollBar.style.display = 'none';
		          }
		        }

				// 접근성 swiper 초기화 함수
				function bannerSwiper(wrapper, options) {
					var _options = options;
					if (!_options.pagination) {
						_options.pagination = {};
					}
					_options.init = function() {
						if (options.navigation) {
							var next = this.el.querySelector(options.navigation.nextEl);
							var prev = this.el.querySelector(options.navigation.prevEl);

							// 웹 접근성 next, prev 버튼 포커스 안가도록 처리
							setTimeout(function() {
								if (next) next.setAttribute('tabindex', -1);
								if (prev) prev.setAttribute('tabindex', -1);
							}, 300);
						}
						if (_options.afterInit) {
							_options.afterInit.apply(this, []);
						}
					};
					return KTM.swiperA11y(wrapper, options);
				}

				// 메인 설문 컨텐츠
				function initSurveyContent() {
					var surveyContainer = document.querySelector('.survey__box');
					var surveyContents = document.querySelectorAll('.survey__content');
					var aniIns = [];
					lottieAnimation();
					// 각 설문 show
					function showSurveyContent(idx) {
						[].forEach.call(surveyContents, function(content, i) {
							if (i === idx) {
								content.style.display = 'block';
								var cache = content.offsetHeight;
								content.classList.add('is-active');
								if (content.querySelector('.survey-swiper')) {
									// 마지막 (결과) 애니메이션
									playAnimation(2);

								} else {
									if (idx === 0) {
										playAnimation(0);
									} else {
										playAnimation(1);
									}
								}
								var firstFocus = content.querySelector('input');
								// 첫 번째 인풋에 포커스 가도록..
								// if (firstFocus) {
								// 	setTimeout(function() {
								// 				content.querySelector('input').focus();
								// 			}, 100);
								// }
							} else {
								content.classList.remove('is-active');
								content.style.display = 'none';
							}
						});
					}


					function lottieAnimation() {
						var animations = document.querySelectorAll('.survey-ani');
						[].forEach.call(animations, function(content) {
							var url = content.getAttribute('data-ani-url');
							var ani = lottie.loadAnimation({
										container : content,
										renderer : 'svg',
										//loop : true,
										autoplay : true,
										path : url,
									});

							ani.pause();
							aniIns.push(ani);
							content.style.display = 'none';
						});
					}

					// 설문 애니메이션 play(해당 설문지에 해당하는 애니메이션만 show)
					function playAnimation(index) {
						for (var i = 0; i < aniIns.length; i++) {
							if (i === index) {
								aniIns[index].wrapper.style.display = 'block';
								setTimeout(function() {
									aniIns[index].play();
								}, 0);
							} else {
								aniIns[i].stop();
								aniIns[i].wrapper.style.display = 'none';
							}
						}
					}

					[].forEach.call(surveyContents, function(content, i) {
						// 항상 가장 첫 번째는 설문시작 ( 클릭하여 설문 시작 )
						if (i === 0) {
							content.addEventListener('click', function click(e) {
								e.preventDefault();
								e.currentTarget.removeEventListener('click', click);
								showSurveyContent(1);
							});
						}

						var nextbutton = content.querySelector('.c-button-wrap button');
						if (nextbutton) {
							nextbutton.idx = i;
							nextbutton.addEventListener('click', function(e) {
								e.preventDefault();
								if (e.currentTarget.classList.contains('restart')) {
									// 다시하기, 첫 번째 설문으로..
									// 요금제추천 Param 초기화
									ansNo = "";
									showSurveyContent(1);
								} else {
									// 다음 버튼
									let v_ansNo = ansNoValidate(this);
									if (ajaxCommon.isNullNvl(v_ansNo, "") == "") {
										MCP.alert("답변을 선택해주세요.");
										return false;
									}
									ansNo = ansNo + "" + v_ansNo;

									// 결과조회
									if (e.currentTarget.classList.contains('result')) {
										getResultInqr(this);
									}

									showSurveyContent(e.currentTarget.idx + 1);
								}
							});
						}
					});

					// 애니메이션 초기화
					//lottieAnimation();
					// 설문 시작 박스 오픈
					showSurveyContent(0);
				}


				var surveySlide = null;
				// 결과페이지 swiper
				function initSurveySlide() {
					if(surveySlide) {
						surveySlide.destroy();
					}

					surveySlide = KTM.swiperA11y('.survey-swiper.swiper-container', {
						init : function() {
							setTimeout(function() {
								// 웹 접근성 관련, 다음, 이전버튼 포커스 안가도록..
								var n = document.querySelector('.survey-pager.swiper-button-next');
								var p = document.querySelector('.survey-pager.swiper-button-prev');
								n.setAttribute('tabindex', -1);
								p.setAttribute('tabindex', -1);
								if(surveySlide)
									surveySlide.slides[0].focus();
							}, 500);
						},
						pagination : {},
						a11yTarget : '.c-main-card__anchor',
						observer : true,
						observeParents : true,
						slidesPerView : 3,
						spaceBetween : 30,
						threshold: 10,
						navigation : {
							nextEl : '.survey-pager.swiper-button-next',
							prevEl : '.survey-pager.swiper-button-prev',
						},
					});
					surveySlide.slideTo(0, 100);
				}


				// 휴대폰 탭 컨텐츠 (swiper 포함)
				function phoneContent() {
					var tabEl = document.querySelector('#phone_tab');
					// 요금제 탭 변경될 때
					tabEl.addEventListener(
							KTM.Tab.EVENT.CHANGE, function(event) {
								var currentTab = event.current;
								var beforeTab = event.before;
								initSlide(currentTab.content);
							});
					initSlide(document.querySelector('#phone_panel1'));

					function initSlide(wrapper) {
						var swiperContainer = wrapper.querySelector('.phone-swiper.swiper-container');
						if (!swiperContainer) return;
						if (swiperContainer.swiperIns) {
							// [2022-02-18] 탭 변경될 때, 첫번째 카드로 이동 추가
				            swiperContainer.swiperIns.slideTo(0);
						} else {
							swiperContainer.swiperIns = KTM.swiperA11y(swiperContainer, {
								init : function() {
									setTimeout(
											function() {
												// 웹 접근성 관련, 다음, 이전버튼 포커스 안가도록..
												var n = wrapper.querySelector('.phone-pager.circle.swiper-button-next');
												var p = wrapper.querySelector('.phone-pager.circle.swiper-button-prev');
												n.setAttribute('tabindex', -1);
												p.setAttribute('tabindex', -1);
											}, 500);
								},
								pagination : {},
								a11yTarget : '.c-main-phone__anchor',
								observer : true,
								observeParents : true,
								slidesPerView : 3,
								spaceBetween : 20,
								threshold: 10,
								navigation : {
									nextEl : '.phone-pager.circle.swiper-button-next',
									prevEl : '.phone-pager.circle.swiper-button-prev',
								},
							});
						}
					}
				}



				// 이벤트 배너, lottie 애니메이션
				function aniBannerSwiper(target) {
					var targetEl = document.querySelector(target);
					if (!targetEl) return;
					if (targetEl.getAttribute('data-state') !== 'init') {
						targetEl.setAttribute('data-state', 'init');
						var animations = [];
						var aniContents = document.querySelectorAll(target + ' .ani-banner');
						[].forEach.call(aniContents, function(el) {
							var aniContainer = el.querySelector('.ani-banner__content');
							var url = aniContainer.getAttribute('data-ani-url');
							var ani = lottie.loadAnimation({
										container : aniContainer,
										renderer : 'svg',
										//loop : true,
										autoplay : false,
										path : url,
									});
							animations.push(ani);
						});
						animations[0].play();

						KTM.swiperA11y(target, {
							init : function() {
								setTimeout(function() {
											var nextBtn = targetEl.querySelector(' .swiper-button-next');
											var prevBtn = targetEl.querySelector(' .swiper-button-prev');
											nextBtn.setAttribute('tabindex', -1);
											prevBtn.setAttribute('tabindex', -1);
										}, 500);
							},
							effect : 'fade',
							a11yTarget : 'a',
							observer : true,
							observeParents : true,
							pagination : {},
							navigation : {
								nextEl : target + ' .swiper-button-next',
								prevEl : target + ' .swiper-button-prev',
							},
							on : {
								// 활성화된 배너만 애니메이션 플레이
								slideChange : function() {
									var currentIndex = this.activeIndex;
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
					}
				}

				// 상단 이벤트
				function topEvent() {
					KTM.swiperA11y('#mainTopEvent .swiper-container', {
						pagination : {},
						navigation : {
							nextEl : '#mainTopEvent .top-event__button--next',
							prevEl : '#mainTopEvent .top-event__button--prev',
						},
						loop: $("#mainTopEvent .swiper-container .swiper-wrapper .swiper-slide").length > 1,  // 상단 띠배너 루프 처리
					});

					var wrapper = document.querySelector('.ly-event');
					var swiperWrapper = wrapper.querySelector('.top-event');
					var closeBtn = wrapper.querySelector('.c-button');

					closeBtn.addEventListener('click', function() {
						swiperWrapper.classList.add('is-hide');
						swiperWrapper.addEventListener('transitionend', function() {
							wrapper.classList.remove('is-active');
							window.__headerFixedCheckerRemove();
						});
					});

					if(window.__mainEventActive) {
						window.__mainEventActive();
					}
				}

				window.mainFunctions = {
			         mainFullBanner: mainFullBanner, // 빅베너
			         initSurveyContent: initSurveyContent, // 설문
			 		 initSurveySlide: initSurveySlide, //설문 결과페이지
			         phoneContent: phoneContent, // 휴대폰 요금제
			 		 topEvent: topEvent, // 헤더 띠배너
			         iconScrollContainer: iconScrollContainer, // 퀵메뉴,
			 		 aniBannerSwiper1: function(){
			 			aniBannerSwiper('#ani_swiper1');
			 		 },
					 aniBannerSwiper2: function(){
						 aniBannerSwiper('#ani_swiper2');
					 },
			 		 bannerSwiper1: function() {
			 			//오늘의 상품제안 배너 (시선집중1)
						bannerSwiper('.suggest-swiper .swiper-container', {
							effect : 'fade',
							a11yTarget : 'a',
							navigation : {
								nextEl : '.suggest-swiper-button-next',
								prevEl : '.suggest-swiper-button-prev',
							},
						});
					 },
			 		 bannerSwiper2: function() {
						//이달의 이벤트 배너 (시선집중2)
						bannerSwiper('.event-swiper .swiper-container', {
							slidesPerView : 2,
							spaceBetween : 20,
							a11yTarget : 'a',
							navigation : {
								nextEl : '.event-swiper-button-next',
								prevEl : '.event-swiper-button-prev',
							},
						});
					 },
			 		 bannerSwiper3: function() {
						//혜택안내 배너
						bannerSwiper('.benefit-swiper .swiper-container', {
							slidesPerView : 2,
							spaceBetween : 20,
							a11yTarget : 'a',
							navigation : {
								nextEl : '.benefit-swiper .swiper-button-next',
								prevEl : '.benefit-swiper .swiper-button-prev',
							},
						});
				 	 },
				 	bannerSwiper4: function() {
						// 시선집중3
				 		bannerSwiper('.line-banner .swiper-container', {
							effect : 'fade',
							a11yTarget : 'a',
							navigation : {
								nextEl : '.line-swiper-button-next',
								prevEl : '.line-swiper-button-prev',
							},
						});
				 	 },
			    };

			});

			$( document ).ready(function() {
				$(".ly-wrap").addClass("main");

				// 페이지 로드시 설문 시작 버튼 클릭
				setTimeout(function(){
					$('.survey__content').trigger("click");
				},500);
			});
		</script>
	</t:putAttribute>
</t:insertDefinition>
