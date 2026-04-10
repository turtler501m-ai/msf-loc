var menuVer = new Date().getTime();

document.write('<script type="text/javascript" src="/resources/upload/js/dataMenuConfig.js?version=' + menuVer + '" charset="utf-8"></script>');
var subTitleText = " - kt M모바일";
$(document).ready(function() {
    
    
    //주메뉴
    var $lnbMain = $("#gnbMainMenu") ;
    var $gnTotalMenu  = $("#gnTotalMenu") ;
    var $gnbMenuBanner = $("#gnbMenuBanner");
    var $lnbTitle = $('#lnbTitle') ;
    var gnbMenuCode = "" ;
    var subMenuCode = "";
    var titleTxt = "";
    
    
    var evnetObj = "";
    $(document).on("click",".lnb_2depth",function(){
    	if (evnetObj == ""  ) {
    		$(this).find("div > a").trigger('click');
    		$(this).find("div > a").focus();
    	}
    	evnetObj = "";

    });

    $(document).on("click",".lnb_2depth > div > a",function(){

    	evnetObj = "lnb_2depthA";
    	$3depth = $(this).parent().siblings('.lnb_3depth');
        $2depth_title = $(this).parent();

        if ($3depth.css('display') == 'none') {
            $('.lnb_3depth').slideUp();
        }

        $3depth.slideToggle();
        if ($2depth_title.hasClass('open')){
            $2depth_title.removeClass('open');
            $2depth_title.addClass('close');
        } else if ($2depth_title.hasClass('close')) {
            $open_menu = $('.lnb_2depth > div.open');
            $open_menu.removeClass('open');
            $open_menu.addClass('close');
            $2depth_title.removeClass('close');
            $2depth_title.addClass('open');
        }
    });
    
    
    if ( typeof menuCode != "undefined" && menuCode.length >= 4)  {
        gnbMenuCode = menuCode.substring(0, 4) ;
    }
    
    if ( typeof menuCode != "undefined" && menuCode.length >= 6)  {
        subMenuCode = menuCode.substring(0, 6) ;
    }

    //전체 메뉴
    var totalMenuHtml = [];
    
    //GNB메뉴
    var htmlMainMenu = [];
    
    //menuBanner
    var htmlMunuBanner = [];
    
    for (var i = 0; i < NMCP_GNB_MENU_LIST.length; i++) {        
        var gnbOnClass = "";
        if (NMCP_GNB_MENU_LIST[i].menuCode == gnbMenuCode) {
            if ($lnbTitle.length != 0 ) {
                 $lnbTitle.html(NMCP_GNB_MENU_LIST[i].menuNm);
            }
            gnbOnClass = "active";
            titleTxt =  NMCP_GNB_MENU_LIST[i].menuNm ;
        }
        
        if (NMCP_GNB_MENU_LIST[i].menuHierTypeCd == "00" ) {
            htmlMainMenu.push("<li class='n_menu"+(i+1)+"'>");
            htmlMainMenu.push("   <a href='"+NMCP_GNB_MENU_LIST[i].urlAdr+"' class='"+ gnbOnClass +"' >"+NMCP_GNB_MENU_LIST[i].menuNm+"</a>");
            htmlMainMenu.push("   <ul class='n_sub'>");
            
            totalMenuHtml.push("<li class='n_menu"+(i+1)+"'>");
            totalMenuHtml.push("    <span>"+NMCP_GNB_MENU_LIST[i].menuNm+"</span>");
            totalMenuHtml.push("    <ul class='n_sub'>");
            
            var tempMenuList = NMCP_SECOND_MENU_OJB[NMCP_GNB_MENU_LIST[i].menuCode] ;
            
            for(var j=0; j< tempMenuList.length; j++) {
                var subOnClass = "";
                if (tempMenuList[j].menuCode == subMenuCode) {
                    subOnClass = "active";
                }
                htmlMainMenu.push("<li><a href='"+tempMenuList[j].urlAdr+"' class='"+ subOnClass +"'>"+tempMenuList[j].menuNm+"</a></li>");
                totalMenuHtml.push("<li><a href='"+tempMenuList[j].urlAdr+"' class='"+ subOnClass +"'>"+tempMenuList[j].menuNm+"</a></li>");
            }
            
            htmlMainMenu.push("   </ul>");
            htmlMainMenu.push("</li>");
            
            totalMenuHtml.push("    </ul>");
            totalMenuHtml.push("</li>");
            
            //배너
            var target = "";
            var target2 = "";
            if (NMCP_GNB_MENU_LIST[i].attYn == 'Y') {
                target = "target='_blank' title='새창열림'";
            }
            if (NMCP_GNB_MENU_LIST[i].attYn2 == 'Y') {
                target2 = "target='_blank' title='새창열림'";
            }
            
            if (NMCP_GNB_MENU_LIST[i].filePathNm !="") {
                htmlMunuBanner.push("<li><a href='"+NMCP_GNB_MENU_LIST[i].linkUrlAdr+"' "+ target +"><img src='"+NMCP_GNB_MENU_LIST[i].filePathNm+"' alt='"+NMCP_GNB_MENU_LIST[i].imgDesc+"'></a></li>");
            }
            
            if (NMCP_GNB_MENU_LIST[i].filePathNm2 !="") {
                htmlMunuBanner.push("<li><a href='"+NMCP_GNB_MENU_LIST[i].linkUrlAdr2+"' "+ target2 +"><img src='"+NMCP_GNB_MENU_LIST[i].filePathNm2+"' alt='"+NMCP_GNB_MENU_LIST[i].menuDesc2+"'></a></li>");
            }
            
            
            
        } else {
            totalMenuHtml.push("<li class='n_menu1'>");
            totalMenuHtml.push("    <span>"+NMCP_GNB_MENU_LIST[i].menuNm+"</span>");
            totalMenuHtml.push("    <ul class='n_sub'>");
            
            var tempMenuList = NMCP_SECOND_MENU_OJB[NMCP_GNB_MENU_LIST[i].menuCode] ;
            
            for(var j=0; j< tempMenuList.length; j++) {
                var subOnClass = "";
                if (tempMenuList[j].menuCode == subMenuCode) {
                    subOnClass = "active";
                }
                totalMenuHtml.push("<li><a href='"+tempMenuList[j].urlAdr+"' class='"+ subOnClass +"'>"+tempMenuList[j].menuNm+"</a></li>");
            }
            
            totalMenuHtml.push("    </ul>");
            totalMenuHtml.push("</li>");
        }
    }
    
    $lnbMain.html(htmlMainMenu.join(""));
    $gnTotalMenu.html(totalMenuHtml.join(""));
    $gnbMenuBanner.html(htmlMunuBanner.join(""));
    
    
    var $lnbList = $('#lnbList .lnb_menu') ;
    //LNB 설정

    var arrStrLnb = [];
    var pageMenuNm = ""; // 페이지에 메뉴에 맞는 이름을 넣어줄 변수

   if ( typeof menuCode != "undefined" && menuCode.length >= 6 && typeof NMCP_SECOND_MENU_OJB != "undefined" )  {
       
       var tempMenuList = NMCP_SECOND_MENU_OJB[gnbMenuCode] ;

       if (tempMenuList != null) {
           for(var i=0; i< tempMenuList.length; i++) {
               var txtActive = "";
               var txtActive2 = "";
               if (subMenuCode ==  tempMenuList[i].menuCode) {
                   txtActive = "no_3depth active";
                   txtActive2 = "active close";
                   titleTxt = titleTxt +">"+ tempMenuList[i].menuNm ; //- 모바일 실용주의 케이티 엠모바일";
                   //페이지 네임을 메뉴코드에 등록된 이름으로 바꿔준다
                   pageMenuNm = tempMenuList[i].menuNm;
               } else {
                   txtActive = "no_3depth";
                   txtActive2 = "close";
               }

               if (tempMenuList[i].nextMenuCount == 0) {
                   arrStrLnb.push("<li class='lnb_2depth'><div class='"+txtActive+"'><a href='"+tempMenuList[i].urlAdr+"'>"+tempMenuList[i].menuNm+"</a></div></li>");
               } else {

                   var thirdMenuList = NMCP_THIRD_MENU_OJB[tempMenuList[i].menuCode] ;
                   arrStrLnb.push("<li class='lnb_2depth' menuCode='"+tempMenuList[i].menuCode+"' >");
                   arrStrLnb.push("    <div class='"+txtActive2+"'><a href='javascript:void(0);'>"+tempMenuList[i].menuNm+"</a></div>");
                   arrStrLnb.push("    <ul class='lnb_3depth'>");
                   for(var k=0; k< thirdMenuList.length; k++) {
                       var txtActive3 = "";
                       if (menuCode ==  thirdMenuList[k].menuCode) {
                           txtActive3 = "class='active'";
                           //페이지 네임을 메뉴코드에 등록된 이름으로 바꿔준다
                           pageMenuNm = thirdMenuList[k].menuNm;
                       }
                       arrStrLnb.push("        <li "+txtActive3+" ><a href='"+thirdMenuList[k].urlAdr+"'>"+thirdMenuList[k].menuNm+"</a></li>");
                   }
                   arrStrLnb.push("    </ul>");
                   arrStrLnb.push("</li>");
               }
           }

           if ($lnbList.length != 0 ) {
               $lnbList.html(arrStrLnb.join(''));
           }
           //페이지 네임을 메뉴코드에 등록된 이름으로 바꿔준다. 실제 html로 적용
           $('#topMenuTitleSpan').html(pageMenuNm);
       }

       $('.lnb_3depth').hide();
       $(".lnb_2depth[menuCode="+subMenuCode+"] > div > a").trigger('click');
   }

   // 페이지에서 설정한. title를 우선시 한다.
   // document.title.length < 20
   // 기본값 "- 모바일 실용주의 케이티 엠모바일" 이고 titleTxt 가 존재하면 변경 처리
    if (titleTxt != "" ) {
        if (subMenuCode.indexOf("SM09") > -1) {//마이페이지 이하 서브페이지
            titleTxt = titleTxt + ">"+pageMenuNm + subTitleText;
        } else {
            titleTxt = titleTxt + subTitleText;
        }
        document.title = titleTxt ;
     } else {
         //document.title = "KT가 만든 대한민국 1등 알뜰폰 – kt M모바일 공식 다이렉트몰";
     }
    
    //메뉴 title속성 추가
    $(".mm_gnb a:contains('라이프굿케어')").attr("title","라이프굿케어 이벤트 페이지로 이동");
    $(".mm_gnb a:contains('무료렌탈')").attr("title","무료렌탈 이벤트 페이지로 이동");
    $(".mm_gnb a:contains('해외 직구대행')").attr("title","해외 직구대행 이벤트 페이지로 이동");
    $(".mm_gnb a:contains('라이프케어 요금제')").attr("title","라이프케어 요금제 이벤트 페이지로 이동");
    $(".mm_gnb a:contains('생활안심 요금제')").attr("title","생활안심 요금제 이벤트 페이지로 이동");

    $(".total_menu a:contains('라이프굿케어')").attr("title","라이프굿케어 이벤트 페이지로 이동");
    $(".total_menu a:contains('무료렌탈')").attr("title","무료렌탈 이벤트 페이지로 이동");
    $(".total_menu a:contains('해외 직구대행')").attr("title","해외 직구대행 이벤트 페이지로 이동");
    $(".total_menu a:contains('라이프케어 요금제')").attr("title","라이프케어 요금제 이벤트 페이지로 이동");
    $(".total_menu a:contains('생활안심 요금제')").attr("title","생활안심 요금제 이벤트 페이지로 이동");
    
    
    // 전체메뉴
    $('.btn_all_menu').on('click',function(){
        $('.n_gnb_bg').hide();
        $('.n_menu_list .n_sub').hide();
        $('.n_menu_list .n_gnb_banner').hide();
        gnbSlider.stopAuto();
        if($(this).hasClass('on')){
            $(this).removeClass('on').text('전체메뉴 열기').next('.n_total_menu').hide();
        }else{
            $(this).addClass('on').text('전체메뉴 닫기').next('.n_total_menu').show();
        }
        var max_th=0;
        $('.n_total_menu .n_sub').each(function(){
            var th = parseInt($(this).css("height"));
            if(max_th<th){ max_th = th; }
        });
        $('.n_total_menu .n_sub').each(function(){
            $(this).css({height:max_th});
        });
    });
    $('.n_total_menu .btn_close').on('click', function(){
        $('.btn_all_menu').removeClass('on').text('전체메뉴 열기');
        $('.n_total_menu').hide();
    });
    
    
    // gnb-focus
    $('.n_menu_list').focusin(function(){
        $('.n_gnb_bg').show();
        var subH = $('.n_menu_list .n_sub').height();
        $('.n_gnb_bg').height(subH);
         $('.n_menu_list .n_sub').show();
         $('.n_gnb_banner').show();
         gnbSlider.startAuto();
    });
    $('.n_etc_menu').focusin(function(){
        $('.n_sub').hide();
        $('.n_gnb_bg').hide();
        $('.n_gnb_banner').hide();
        gnbSlider.stopAuto();
    });

    // gnb-스크롤시 고정
    $(window).scroll(function(){
        var hdTop = $('.n_header_top').outerHeight();
        var scroll = $(window).scrollTop();
        if (scroll >= hdTop) {
            $('.n_gnb').addClass('fixed');
            $('.n_etc_menu>ul').css('display','none');
            $('.n_etc_menu .n_mypage').css('display','block');
        } else {
            $('.n_gnb').removeClass('fixed');
            $('.n_etc_menu .n_mypage').css('display','none');
            $('.n_etc_menu>ul').css('display','block');
        }
    });
    
    
    // - 배너 슬라이드
    var gnbBnList = $('.gnb_banner_slider li').length;
    
    //Return a random number between 0 and length:
    var startIndex = Math.floor((Math.random() * gnbBnList) );
    
    // - list가 1개이상부터 슬라이드 실행
    if(gnbBnList>1){
        gnbSlider =$('.gnb_banner_slider').bxSlider({
            mode: 'fade',
            auto:true,
            moveSlides:1,
            slideWidth:315,
            slideHeight:200,
            autoControls:true,
            speed:500,
            pause:3000,
            startSlide:startIndex,
            pager:false
        });
        $('.n_gnb_banner .bx-stop').on('click',function(){
            $(this).css('display','none');
            $('.n_gnb_banner').find('.bx-start').css('display','block');
            $('.n_gnb_banner').find('.bx-start').focus();
            gnbSlider.stopAuto();
        });
        $('.n_gnb_banner .bx-start').on('click',function(){
            $(this).css('display','none');
            $('.n_gnb_banner').find('.bx-stop').css('display','block');
            $('.n_gnb_banner').find('.bx-stop').focus();
            gnbSlider.startAuto();
        });    
    }
    
    
    // gnb
    var max_h=0;
    $('.n_menu_list .n_sub').each(function(){
        var h = parseInt($(this).css("height"));
        if(max_h<h){ max_h = h; }
    });
    $('.n_menu_list .n_sub').each(function(){
        $(this).css({height:max_h});
    });

    
    $('.n_menu_list>ul>li>a').on('mouseenter',function(){
        $('.n_gnb_bg').show();
        var subH = $('.n_menu_list .n_sub').height();
        $('.n_gnb_bg').height(subH);
         $('.n_menu_list .n_sub').show();
         $('.n_menu_list .n_gnb_banner').show();
         gnbSlider.startAuto();
         //$('.btn_all_menu').removeClass('on').text('전체메뉴 열기').next('.n_total_menu').hide();
    });
    
    
    $('.n_gnb').on('mouseleave',function(){
        $('.n_gnb_bg').hide();
        $('.n_menu_list .n_sub').hide();
        $('.n_menu_list .n_gnb_banner').hide();
        gnbSlider.stopAuto();
    });
    
    
});


function detailTitleMakerFn(detailTitle) {
    var titleTxt = document.title;
    titleTxt = titleTxt.substring(0, titleTxt.indexOf("-"));
    if (titleTxt != "") {
        document.title = titleTxt + ">" +  detailTitle + subTitleText;
    } else {
        document.title = detailTitle + subTitleText;
    }
}








