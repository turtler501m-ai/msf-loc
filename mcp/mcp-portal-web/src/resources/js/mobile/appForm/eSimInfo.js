
var pageInfo = {
    initFlag:true
    , simpleApplyObj :{}
}

$(document).ready(function(){

	/* aos 애니메이션 */
	AOS.init();

    $("#step3").click(function(){
        //셀프개통 가능 여부 확인
        ajaxCommon.getItemNoLoading({
            id:'isSimpleApply'
            ,cache:false
            ,url:'/appForm/isSimpleApplyAjax.do'
            ,data: ""
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            pageInfo.simpleApplyObj = jsonObj ;
        });

        if (!pageInfo.simpleApplyObj.IsMacTime && !pageInfo.simpleApplyObj.IsMnpTime) {
             KTM.Confirm('셀프개통 가능한 시간은 신규(08:00~21:50)<br/>번호이동(10:00~19:50) 입니다.<br/>확인 시  [상담사 개통 신청]으로 진행됩니다.'
                ,function () {
                    location.href='/m/appForm/eSimSelfView.do';
                })
        } else {
            location.href='/m/appForm/eSimSelfView.do';
        }
    });


    $("#btnWatchStart").click(function(){
        //셀프개통 가능 여부 확인
        ajaxCommon.getItemNoLoading({
            id:'isSimpleApply'
            ,cache:false
            ,url:'/appForm/isSimpleApplyAjax.do'
            ,data: ""
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            pageInfo.simpleApplyObj = jsonObj ;
        });

        if (!pageInfo.simpleApplyObj.IsMacTime ) {
            KTM.Confirm('셀프개통 가능한 시간은 신규(08:00~21:50)<br/>입니다.<br/>확인 시  [상담사 개통 신청]으로 진행됩니다.'
                ,function () {
                    location.href='/m/appForm/eSimWatch.do';
                })
        } else {
            location.href='/m/appForm/eSimWatch.do';
        }
    });


    /* esim 후기 슬라이드 */
    var swiper = new Swiper('.esim-now-review-swiper', {
		observer: true,
		observeParents: true,
		loop: true,
		centeredSlides: true,
		slidesPerView: "auto",
		spaceBetween: 0,
		speed: 500,
		autoplay: {
		    delay: 5000,
		    disableOnInteraction: false
		},
  	});


  	/* 아코디언 싱글 */
  	function openActiveInAccordion($accordion, animate = false) {
  	  const $btn = $accordion.find('.c-accordion__button.is-active').first();
  	  if (!$btn.length) return false;

  	  const $item = $btn.closest('.c-accordion__item');
  	  const $panel = $item.find('.c-accordion__panel');

  	  // 해당 accordion 안에서만 닫기
  	  $accordion.find('.c-accordion__button')
  	    .removeClass('is-active')
  	    .attr('aria-expanded', 'false');

  	  if (animate) {
  	    $accordion.find('.c-accordion__panel').stop(true, true).slideUp();
  	  } else {
  	    $accordion.find('.c-accordion__panel').hide();
  	  }

  	  // 대상만 열기
  	  $btn.addClass('is-active').attr('aria-expanded', 'true');
  	  if (animate) $panel.stop(true, true).slideDown();
  	  else $panel.show();

  	  return true;
  	}

  	$(function () {
  	  $('.c-accordion--single').each(function () {
  	    openActiveInAccordion($(this), false);
  	  });
  	});

  	(function () {
  	  $('.c-accordion--single').each(function () {
  	    const accordion = this;

  	    const observer = new MutationObserver(() => {
  	      if (openActiveInAccordion($(accordion), false)) {
  	        observer.disconnect();
  	      }
  	    });

  	    observer.observe(accordion, { childList: true, subtree: true });
  	  });
  	})();

  	// ✅ 클릭 이벤트 (자기 아코디언 안에서만 동작)
  	$(document).on('click', '.c-accordion--single .c-accordion__button', function () {
  	  const $btn = $(this);
  	  const $accordion = $btn.closest('.c-accordion--single');

  	  const $item = $btn.closest('.c-accordion__item');
  	  const $panel = $item.find('.c-accordion__panel');

  	  const isOpen = $btn.hasClass('is-active');

  	  $accordion.find('.c-accordion__button')
  	    .removeClass('is-active')
  	    .attr('aria-expanded', 'false');

  	  $accordion.find('.c-accordion__panel')
  	    .stop(true, true)
  	    .slideUp();

  	  if (!isOpen) {
  	    $btn.addClass('is-active').attr('aria-expanded', 'true');
  	    $panel.stop(true, true).slideDown();
  	  }
  	});






});
