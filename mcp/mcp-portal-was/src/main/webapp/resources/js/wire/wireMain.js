﻿;

$(document).ready(function () {
	/* 슬라이드 */
	$(function(){
		window.directMainSwipe = new Swipe(document.getElementById('slider'), {
            startSlide: 0,
            speed: 400,
            auto: 5000,
            continuous: true,
            disableScroll: false,
            stopPropagation: false,
            callback: function (index, elem) {	            	
            	var pos = directMainSwipe.getPos() + 1;
                // 슬라이드가 2개일 경우
                var maxIdx = $('.banner_index .btn_index').length;
                if (maxIdx == 2) {
                    if (pos == 3) {
                        pos = 1;
                    } else if (pos == 4) {
                        pos = 2;
                    }
                }
                // //슬라이드가 2개일 경우
                $("#slider").find(".banner_index button").removeClass("active");
		        $("#slider").find(".banner_index button:nth-child(" + pos + ")").addClass("active");
		    },
		    transitionEnd: function (index, elem) {
		    	 // 현재 슬라이드만 탭이동이 되도록 함
	            $('.swipe-wrap .clri a').attr('tabindex', '-1');
	            $('.swipe-wrap .clri').eq(index).find('a').removeAttr('tabindex');
	            // //현재 슬라이더만 탭이동이 되도록 함
	        }
        });
		
		 // 슬라이드 탭이동 기능 초기화
	    $('.swipe-wrap .clri a').attr('tabindex', '-1');
	    $('.swipe-wrap .clri').eq(0).find('a').removeAttr('tabindex');
	    // //슬라이드 탭이동 기능 초기화
		
		$('.btn_index').click(function () {
	    	$('.btn_stop').css("display","none");
	    	$('.btn_start').css("display","");
	        var id = $(this).attr("id");
	        var index = id.split("_")[1];
	        index = parseInt(index) - 1;		      
	        directMainSwipe.slide(index, 400);
	    });

		$('.btn_stop').click(function () {
			$('.btn_stop').css("display","none");
			$('.btn_start').css("display","");			
			directMainSwipe.stop();		    
		});			

		$('.btn_start').click(function () {
			$('.btn_start').css("display","none");
			$('.btn_stop').css("display","");				
			directMainSwipe.begin();	    			    
		});	
	});
	
 	$('.arrow_right').click(function () {
    	$('.btn_stop').css("display","none");
    	$('.btn_start').css("display","");
    	directMainSwipe.next();
    });

    $('.arrow_left').click(function () {
    	$('.btn_stop').css("display","none");
    	$('.btn_start').css("display","");
    	directMainSwipe.prev();
    });
	/* 슬라이드 */
 	
    $(document).on("click", ".joinBtn", function() {
		
		window.open("","wireProdJoinForm","width=720,height=820");

        ajaxCommon.createForm({
            method:"post"
           ,action:"/wire/wireProdJoinForm.do" 
           ,target :"wireProdJoinForm" 
         });
        
        ajaxCommon.attachHiddenElement("joinProdCorp","");
        ajaxCommon.attachHiddenElement("joinProdCd","");
        ajaxCommon.attachHiddenElement("joinProdDtlSeq","");
        ajaxCommon.attachHiddenElement("wireProdCd","");
        ajaxCommon.formSubmit();
	});
});

