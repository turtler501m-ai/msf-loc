<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="no-js" lang="ko">

<head>
<title>kt M mobile</title>
<meta http-equiv="content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, user-scalable=no">
<link rel="stylesheet" type="text/css" href="/resources/company/css/ktmmobile.css">
<!--[if lt IE 9]>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js"></script>
<![endif]-->
<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/resources/js/swipe.js"></script>

<script type="text/javascript" src="/resources/company/common.js"></script>
<script type="text/javascript" src="/resources/js/jqueryCommon.js"></script>
<script>
    $(document).ready(function() {

        $('.real_menu').mouseover(function() {

            $('.sub_menu').slideDown();

        });
        $('.sub_menu').mouseleave(function() {

            $('.sub_menu').slideUp();
        });
        $('.gnb_mobile .menu').click(function() {

            $('.sub_menu_mobile').slideToggle();
        });

        var tab = $('#slider2').css("display");

        if( tab == "none"){
        window.myMainSwipe = new Swipe(document.getElementById('slider'), {
            startSlide: 0,
            speed: 800,
            auto: 5000,
            continuous: true,
            disableScroll: false,
            stopPropagation: false,
            callback: function (index, elem) {
                var pos = index + 1;
                $("#slider").find(".banner_index button").removeClass("active");
                $("#slider").find(".banner_index button:nth-child(" + pos + ")").addClass("active");
            },
            transitionEnd: function (index, elem) { }
        });

        }else {

        window.myMainSwipe2 = new Swipe(document.getElementById('slider2'), {
            startSlide: 0,
            speed: 800,
            auto: 5000,
            continuous: true,
            disableScroll: false,
            stopPropagation: false,
            callback: function (index, elem) {
                var pos = index + 1;
                $("#slider2").find(".banner_index2 button").removeClass("active");
                $("#slider2").find(".banner_index2 button:nth-child(" + pos + ")").addClass("active");
            },
            transitionEnd: function (index, elem) { }
        });
        }


        $('.btn_index').click(function () {
            myMainSwipe.slide($(this).index(), 400);
            myMainSwipe2.slide($(this).index(), 400);

              $('.btn_stop').css("display","none");
             $('.btn_start').css("display","");
        });

        $('.btn_stop').click(function () {
            $('.btn_stop').css("display","none");
            $('.btn_start').css("display","");

                myMainSwipe.stop();


        });

        $('.btn_start').click(function () {
            $('.btn_start').css("display","none");
            $('.btn_stop').css("display","");

                myMainSwipe.begin();


        });



    });

    function pop_getCookie(name) {
         var nameOfCookie = name + "=";
         var x = 0

         while ( x <= document.cookie.length ) {
           var y = (x+nameOfCookie.length);
           if ( document.cookie.substring( x, y ) == nameOfCookie ) {
            if ( (endOfCookie=document.cookie.indexOf( ";",y )) == -1 )
             endOfCookie = document.cookie.length;
            return unescape( document.cookie.substring(y, endOfCookie ) );
           }
           x = document.cookie.indexOf( " ", x ) + 1;
           if ( x == 0 )
            break;
         }
         return "";
        }
</script>





<link rel="shortcut icon" href="/favicon.ico" />
<script type="text/javascript">
    function visit(select) {

        var url = select.options[select.selectedIndex].getAttribute('value');

        if (url) {

        }
    }
</script>

</head>

<body>




    <div class="background_wide_top">
        <div class="header">
            <div class="logo">
                <img src="/resources/company/img/logo.jpg" alt="kt Mmobile" onclick='window.location="/company/main.do";' style="cursor: pointer;">
            </div>
            <div class="gnb">
                <ul>
                    <li class="real_menu" onclick="window.location='/company/company.do'">kt M모바일 소개</li>
                    <!-- <li class="real_menu" onclick=' window.open("/mmobile/newsDataList.do", "_blank");'>홍보채널</li> -->
                    <li class="real_menu" onclick="window.location='/company/recruit.do'">인재채용</li>
                    <li><button onclick='window.open("https://www.ktmmobile.com", "_blank");'>다이렉트몰 바로가기</button></li>
                </ul>

            </div>
            <div class="gnb_mobile">
                <img src="/resources/images/company/mobile_logo.png" alt="모바일 실용주의 KT M mobile" onclick='window.location="/company/main.do";'>
                <div class="menu">
                    <img src="/resources/images/company/menu.png" alt="menu">
                </div>
            </div>
            <div class="sub_menu_mobile">
                <ul>
                    <li onclick="window.location='/company/company.do'">kt M모바일 소개</li>
                    <!-- <li onclick=' window.open("/mmobile/newsDataList.do", "_blank");'>홍보채널</li> -->
                    <li onclick="window.location='/company/recruit.do'">인재채용</li>
                    <li onclick='window.open("https://www.ktmmobile.com", "_blank");'>다이렉트몰 바로가기</li>
                </ul>
            </div>
            <div class="sub_menu">
                <div class="content2">
                    <ul class="menu_item">
                        <li onclick="window.location='/company/company.do#content1'">-기업소개</li>
                        <li onclick="window.location='/company/company.do#content2'">-CEO Message</li>
                        <li onclick="window.location='/company/company.do#content3'">-비전</li>
                        <li onclick="window.location='/company/company.do#content4'">-윤리경영</li>
                        <li onclick="window.location='/company/company.do#content6'">-고객만족경영</li>
                        <li onclick="window.location='/company/company.do#content5'">-오시는길</li>
                    </ul>
                    <!-- <ul class="menu_item">
                        <li onclick='window.open("/mmobile/newsDataList.do", "_blank");'>- 보도자료</li>
                        <li onclick='window.open("/event/eventBoardList.do?sbstCtg=E", "_blank");'>- 이벤트</li>
                    </ul> -->
                    <ul class="menu_item">
                        <li onclick="window.location='/company/recruit.do#content1'">- 인재상</li>
                        <li onclick="window.location='/company/recruit.do#content2'">- 인사제도</li>
                        <li onclick="window.location='/company/recruit.do#content3'">- 복리후생</li>
                        <li onclick="window.open('https://recruit.kt.com')">- 지원하러가기</li>
                    </ul>


                </div>
            </div>
        </div>
    </div>
    <div class="background_wide2" style="box-shadow: 0 0 10px rgba(0, 0, 0, 0.3); padding-bottom: 20px; z-index: 2;">
        <div class="banner">
            <div id='slider' class='swipe'>
              <div class='swipe-wrap'>
                <div class="banner1"></div>
                <div class="banner2"></div>
              </div>
                  <span class="banner_index" style="display:none">
                    <button type="button" class="btn_index active" ></button>
                    <button type="button" class="btn_index" ></button>
                </span>
                <button type="button" class="btn_stop" style="display:none"></button>
                <button type="button" class="btn_start" style="display:none"></button>
            </div>
            <div id='slider2' class='swipe'>
              <div class='swipe-wrap'>
                <div><img src="/resources/company/img/banner_mobile1.png" style="width:100%;" alt="더 가치 있는 고객의 삶을 위한 대한민국 No.1 통신파트너"></div>
                <div><img src="/resources/company/img/banner_mobile2.png" style="width:100%;" alt="고객의 삶을 더 가치 있게! Beyond No.1 kt M mobile"></div>

              </div>
                <span class="banner_index2" style="display:none">
                    <button type="button" class="btn_index active" ></button>
                    <button type="button" class="btn_index" ></button>
                </span>

            </div>
        </div>


        <div class="link_area">
            <div class="content">
                <div class="item link1" onclick='window.open("/product/phone/phoneList.do", "_blank");'>
                    <div class="img"></div>
                    <div class="text">휴대폰 가입</div>
                </div>
                <div class="item link2" onclick='window.open("/rate/rateList.do", "_blank");'>
                    <div class="img"></div>
                    <div class="text">
                        요금제/<br>부가서비스
                    </div>
                </div>
                <div class="item link3" onclick='window.open("/cs/serviceGuide.do", "_blank");'>
                    <div class="img"></div>
                    <div class="text">고객센터</div>
                </div>
                <div class="item link4" onclick='window.open("/event/eventBoardList.do?sbstCtg=E", "_blank");'>
                    <div class="img"></div>
                    <div class="text">이벤트</div>
                </div>
                <div class="item link5" onclick='window.open("https://www.ktmmobile.com", "_blank");'>
                    <div class="img"></div>
                    <div class="text">kt M mobile</div>
                </div>

            </div>
        </div>
        <div class="content">
            <div class="item2 intro" onclick='window.location="/company/company.do#content1";'>
                <div class="img">주식회사 케이티엠모바일</div>
                <div class="text">
                    <div class="text-wrap">MVNO 사업과<br />통신 모듈 유통 사업을 운영하고 있는<br />대한민국 No.1 MVNO Company</div>
                    <button>자세히 보기</button>
                </div>
            </div>
            <div class="item2 business" onclick='window.location="/company/company.do#content3";'>
                <div class="img">비전</div>
                <div class="text">
                    <div class="text-wrap">더 가치있는<br />고객의 삶을 위한<br />대한민국 No.1 통신 파트너</div>
                    <button>자세히 보기</button>
                </div>
            </div>
            <div class="item2 recruit" onclick='window.location="/company/recruit.do";'>
                <div class="img">인재상</div>
                <div class="text">
                    <div class="text-wrap">kt M mobile과 함께<br />가치를 높여나갈<br />인재를 기다립니다.</div>
                    <button>자세히 보기</button>
                </div>
            </div>
        </div>
    </div>

    <div class="background_wide_gray" style="margin-top: 30px; padding-top: 0; z-index: 1;">
       <div class="content footer">
            <span>|</span>
            <a href="/company/company.do#content4" target="_blank" title="윤리상담센터 새창열림">윤리상담센터</a>
            <span>|</span>
        </div>
        <div class="content footer" style="margin-top: -20px; padding-top: 30px;">
            회사명 : 주식회사 케이티엠모바일&nbsp;|&nbsp;대표이사 : 구강본&nbsp;|&nbsp;사업자등록번호 : 133-81-43410<br>
            주소 : 서울특별시 강남구 테헤란로 422. kt선릉타워 12층(대치동)<br>
            Copyright(c) 2015 kt M mobile. All rights reserved.
            <div class="logo2">
                <img src="/resources/images/company/logo_footer.png" alt="kt M mobile">
            </div>
        </div>

    </div>


    <div class="dim-layer">
       <div class="dimBg"></div>
       <!--  공지사항 팝업 -->
       <div id="noticeInfo" class="pop-layer" style="display:none;">
            <div class="pop-container">
                <div class="pop-conts">
                    <div class="popup_top">
                       <h4 class="popup_title_h4">자본금 감소에 따른 채권자이의제출 및 구주권제출 공고</h4>
                   </div>
                   <div class="popup_content">
                       <article class="c-editor">
                            <p style="text-align: center; font-size: 16px; font-weight: bold;"><span>자본금 감소에 따른 채권자이의제출 및 구주권제출 공고</span></p>
														<br>
														본 회사는 2025년 3월 25일 정기주주총회에서 다음과 같이 주식병합의 방법으로 자본금 감소(무상감자)를 특별결의 하였는바,
														본 자본금 감소에 이의가 있는 채권자는 다음의 채권자보호절차 기간 내에 서면으로 이의를 제출하여 주시고,
														주주 및 질권자께서는 다음의 구주권 제출기간 내에 회사로 주권을 제출하여 주시기 바랍니다.
														<br>
														<p style="text-align: center; font-size: 16px; font-weight: bold;">- 다 음 -</p><p>
															<br>
															1. 감자목적: 결손금 보전, 주주가치 제고 등 재무구조 개선<br>
															2. 감자주식의 종류와 수: 보통주식 20,000,000주<br>
															3. 감자방법: 액면금액 5,000원의 보통주식 2주 당 동일 액면금액의 보통주식 1주의 비율로 병합하는 무상감자<br>
															4. 감자 전후 변동내역<br>
														</p>
														<div class="content2" style="width: 100%; margin-top: 0;">
									            <div class="introduction story">
									              <div class="detail ">
									                <table cellpadding="0" cellspacing="0" summary="kt M mobile 개요">
									                    <caption class="hidden">kt M모바일 개요</caption>
									                    <colgroup>
									                        <col>
									                    </colgroup>
									                    <thead>
	                                      <tr>
	                                          <th scope="col"></th>
	                                          <th scope="col">감자전</th>
	                                           <th scope="col" style="border-right: 0;">감자후</th>
	                                      </tr>
                                      </thead>
									                    <tbody>
									                     <tr>
                                           <th scope="row">자본금</th>
                                           <td style="border-right: 0;background-color: #fff;">200,000,000,000원</td>
                                           <td style="text-align: center;">100,000,000,000원</td>
                                       </tr>
                                        <tr>
                                            <th scope="row">발행주식총수</th>
                                            <td style="border-right: 0;background-color: #fff;">40,000,000주</td>
                                            <td style="text-align: center;">20,000,000주</td>
                                        </tr>
									                    </tbody>
									                 </table>
									            </div>
									          </div>
									        </div>
													<br>
														5. 감자기준일(효력발생일): 2025년 4월 25일 (금)<br>
														6. 채권자 보호절차기간 및 구주권제출기간: 2025년 3월 26일 (수) ~ 2025년 4월 24일 (목)<br>
														7. 이의 제출장소: 서울특별시 강남구 테헤란로 422, KT 선릉타워 12층 케이티엠모바일<br><br>
														<p style="text-align: center;">
														2025년 3월 25일<br>
														주식회사 케이티엠모바일<br>
														서울특별시 강남구 테헤란로 422, KT 선릉타워 12층<br>
														대표이사 구 강 본<br>
														</p>
                       </article>
                   </div>
                   <div style="display: flex;text-align: center;background:#fff;justify-content: space-between;padding: 20px;border: 1px solid #eaeaea;">
                       <button id="todayStop" class="today-stop-button">오늘 하루 그만보기</button>
                       <button class="notice_cancel" style="cursor: pointer;height: 35px;border-radius: 20px;border: 1px solid #ed1c24;width: 100px;background: #ed1c24;font-weight: bold;color: white;">닫기</button>
                    </div>
               </div>
           </div>
           <a class="popup_cancel" href="javascript:void(0)">
               <img src="/resources/images/company/layer_cancel.png" alt="닫기"/>
           </a>
        </div>
   </div>
   <script>

   $(document).ready(function(){

	   // 오늘 하루 그만 보기
	    let now = new Date().getTime();
	    let hidePopupTime = localStorage.getItem("hideNoticePopup");

	    // 저장된 값이 없거나, 저장된 시간이 이미 지났으면 팝업 표시
	    if (!hidePopupTime || now > parseInt(hidePopupTime)) {
	        fn_layerPop('noticeInfo', 1040, 1800); // 팝업 열기
	    }

	   //제보하기 안내
	     $("#noticeInfoBtn").click(function() {
	       fn_layerPop('noticeInfo', 1040, 1800);

	       $('#reportFrm')[0].reset();
	       $('#certCheck').prop("checked", false);

	       var refFocusEl = null;
         $('.dim-layer').find('.popup_title_h4').attr('tabindex','0');
         $('.popup_title_h4').show().focus();
             refFocusEl = this;

         $('.popup_cancel').click(function(){
             if(refFocusEl) refFocusEl.focus();
             refFocusEl = null;
             return false;
         });
     });

	    // 닫기 버튼
      $('.notice_cancel').click(function(){
        $(".popup_cancel").trigger("click");
      });


       // "오늘 하루 안 보기" 버튼 클릭 시
       $("#todayStop").click(function() {
         let tomorrow = new Date();
         tomorrow.setHours(24, 0, 0, 0); // 자정까지 남은 시간 설정

         localStorage.setItem("hideNoticePopup", tomorrow.getTime()); // localStorage에 저장
         $(".popup_cancel").trigger("click"); // 팝업 닫기
       });
     });
   </script>





    <script src="/resources/company/js/jquery.bxslider.min.js"></script>
    <%@ include file="/WEB-INF/views/layouts/GDNScript.jsp" %>
</body>
</html>

