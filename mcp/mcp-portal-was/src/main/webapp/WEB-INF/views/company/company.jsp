<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- saved from url=(0048)company.html#_company -->
<html class=" js svg" lang="ko">

<head>
  <title>kt M mobile </title>
        <meta http-equiv="content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="/resources/company/css/ktmmobile.css">
        <script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="/resources/company/common.js"></script>
        <script type="text/javascript" src="/resources/js/iscroll.js"></script>
        <script type="text/javascript" src="/resources/js/jqueryCommon.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/company/js/company.js"></script>
        <!--[if lt IE 9]>
        <script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js"></script>
        <![endif]-->
        <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=de6f55cc92785c6c337ebfcf3aba1470"></script>



<!-- <style type="text/css">
select {-ms-box-sizing:border-box;-moz-box-sizing:border-box;-webkit-box-sizing:border-box;box-sizing:border-box;font-size:14px;cursor:pointer;line-height:20px;padding:5px 10px 5px 10px;color:#333;min-width:70px}
select:disabled {-ms-box-sizing:border-box;-moz-box-sizing:border-box;-webkit-box-sizing:border-box;box-sizing:border-box;font-size:14px;line-height:20px;padding:5px 10px 5px 10px;color:#777;min-width:70px;}
</style> -->


<link rel="shortcut icon" href="/favicon.ico" />
<script type="text/javascript">
function visit(select){

    var url = select.options[select.selectedIndex].getAttribute('value');

    if(url) {

    }
}
</script>
<script>



$(document).ready(function(){	// 페이지 처음 들어올때 지도 셋팅
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

    var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
    var mapTypeControl = new daum.maps.MapTypeControl();

    // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
    // daum.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
    map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);

    // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
    var zoomControl = new daum.maps.ZoomControl();

    map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);


    var coords = new daum.maps.LatLng(37.5053530,127.0528256);

    // 결과값으로 받은 위치를 마커로 표시합니다
    var marker = new daum.maps.Marker({
        map: map,
        position: coords
    });

    // 인포윈도우로 장소에 대한 설명을 표시합니다
    var infowindow = new daum.maps.InfoWindow({
        content: '<div align="center" style="padding:5px;">주식회사 케이티엠모바일</div>'
    });
    infowindow.open(map, marker);
    map.setCenter(coords);

});


</script>

</head>

<body>




<div class="background_wide_top">

        <div class="header">
            <div class="logo">
                <img src="/resources/company/img/logo.jpg" alt="kt Mmobile" onclick='window.location="/company/main.do";' style="cursor:pointer;">
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
                <img src="/resources/images/company/mobile_logo.png" onclick='window.location="/company/main.do";' alt="모바일 실용주의 KT M mobile">
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
                        <li onclick="window.location='/company/company.do#content1'">- 기업소개</li>
                        <li onclick="window.location='/company/company.do#content2'">- CEO Message</li>
                        <li onclick="window.location='/company/company.do#content3'">- 비전</li>
                        <li onclick="window.location='/company/company.do#content4'">- 윤리경영</li>
                        <li onclick="window.location='/company/company.do#content6'">- 고객만족경영</li>
                        <li onclick="window.location='/company/company.do#content5'">- 오시는길</li>
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

    <div class="background_wide" style="padding-top:20px;" id="content1">
        <div class="content2">
            <div class="title">
                <img src="/resources/company/img//bull2.jpg" alt=""><span>기업소개</span>
            </div>
            <div class="introduction">
                <div class="title">
                    kt M모바일 개요
                </div>
                <div class="detail">
                    <table cellpadding="0" cellspacing="0" summary="kt M mobile 개요">
                        <caption class="hidden">kt M모바일 개요</caption>
                        <colgroup>
                               <col />
                           </colgroup>
                           <tr>
                            <th>회사명</th>
                            <td>주식회사 케이티엠모바일</td>
                        </tr>
                        <tr>
                            <th>대표이사</th>
                            <td>구강본</td>
                        </tr>
                        <tr>
                            <th>설립일</th>
                            <td>2015년 04월 02일</td>
                        </tr>
                        <tr>
                            <th>자본금</th>
                            <td>2,000억원</td>
                        </tr>
                        <tr>
                            <th>본사위치</th>
                            <td>서울특별시 강남구 테헤란로 422. kt선릉타워 East 12층(대치동)</td>
                        </tr>
                        <tr>
                            <th>주요사업</th>
                            <td>MVNO 사업, 통신 모듈 유통 사업(USIM, eSIM, IoT Module)</td>
                        </tr>
                        <tr>
                            <th>고객센터 번호</th>
                            <td>1899-5000 (무료 kt M모바일 114)</td>
                        </tr>
                    </table>
                    <img src="/resources/company/img/intro_img1.png" alt="">
                </div>
            </div>
            <div class="business_area">
                <div class="title">
                    사업영역
                </div>
                <div class="detail">
                    <div class="summary">
                        <p>kt M mobile은 MVNO 사업과 통신 모듈 유통 사업을 운영하고 있는 대한민국 No.1 MVNO 사업자 입니다.<br>
						언제나 고객을 생각하며, 고객을 가장 잘 아는 통신 파트너가 되기 위해 최선을 다하겠습니다.</p>
                    </div>
                    <div class="detail1">
                        <div class="title1">
                            <img src="/resources/company/img/bull1.jpg" class="bull" alt=""> <span class="subtitle">MVNO 사업</span>
                        </div>
                        <div class="text">
                        	<span>
                                        MVNO 업계 최다 가입자를 보유한 kt M mobile!<br>
KT의 통화·데이터 품질과 서비스를 그대로, 고객의 통신 생활에 가치를 더한 서비스를 제공하고 있습니다.<br>
합리적인 가격의 요금제, 멤버십, 결합/부가 서비스 등 더 특별한 가치를 제공하기 위해 노력하고 있습니다.
                        	</span>
                         	<div class="mvno_img" alt="아래내용 참조">
                         	<div class="blind">
<strong>합리적 요금제</strong>
<p>고객님의 생활 패턴에 맞춘 다양한 통화·데이터 요금제가 준비되어 있습니다.<br>
통신비 절감은 물론 다양한 제휴 혜택까지 누릴 수 있어 통신 생활이 더욱 즐거워집니다.</p>

<strong>차별화 서비스</strong>
<p>다양한 멤버십 서비스(쇼핑할인, 쿠폰, M마켓)와 KT 유선 인터넷, 무선 결합 서비스를 제공하고 있습니다.<br>
No.1 MVNO 사업자로서, 통신 분야를 넘어 새롭고 참신한 가치를 제공하기 위해 노력하고 있습니다.</p>

<strong>+ 편의성 제공</strong>
<p>온라인(ktmmobile.com, 오픈마켓)과 오프라인(편의점, 마트)에서 언제든 유심을 구매할 수 있고, 셀프개통으로 간편하게 통신서비스를 이용하실 수 있습니다. <br>
상담사 연결까지 기다림 없이 PC/모바일을 통해 요금제 변경, 부가서비스 신청, 실시간 사용량 조회가 가능합니다. <br>
고객님의 소중한 시간을 아끼기 위한 다양한 편의 서비스가 준비되어 있습니다.</p>
</div>
                         	</div>


                        </div>
                   	</div>
                                       <div class="detail1">
                        <div class="title1">
                            <img src="/resources/company/img/bull1.jpg" class="bull" alt=""> <span class="subtitle">통신 모듈 유통 사업</span>
                        </div>
                        <div class="text">
                        	<span>
kt M mobile은 KT 통신 모듈(USIM, eSIM, 셀룰러 모듈)을 고객 및 고객사에게 안정적으로 공급하고 있습니다.<br> 지속적인 혁신과 서비스 강화를 통해 고객과 함께 성장하는 최적의 통신 모듈 파트너로 성장해 나가겠습니다.

                        	</span>
                         	<div class="sim_img" alt="아래내용 참조">
                         	<div class="blind">
<strong>SIM 유통 *Subscriber Identity Module
</strong>
<p>kt M mobile은 KT 통신망 서비스 제공에 필수적인 가입자 인증 모듈을 공급하고 있습니다.</p>
<ul>
<li>USIM(Universal Subscriber Identity Module) : 이동통신 가입자를 인증하는 표준 모듈</li>
 <li>eSIM(Embedded SIM) : 디바이스 내장형 가입자 인증 모듈로, 원격 프로파일 설정 지원</li>
 <li>M2M USIM : IoT 및 산업용 기기 간 통신(Machine-to-Machine, M2M)을 위한 전용 USIM</li>
</ul>
<p>고객의 일반 USIM, eSIM 뿐만 아니라, M2M 및 IoT 디바이스에 최적화된 USIM 솔루션을 제공하여 기업의 원활한 연결성과 운영 효율성을 지원합니다.</p>

<strong>통신모듈 유통 * Cellular Module</strong>
<p>KT M mobile은 네트워크 환경에 맞는 통신 모듈을 공급하여, 다양한 산업에서 활용할 수 있도록 지원하고 있습니다.</p>

<strong>+ 편의성 제공</strong>
<p>온라인(ktmmobile.com, 오픈마켓)과 오프라인(편의점, 마트)에서 언제든 유심을 구매할 수 있고, 셀프개통으로 간편하게 통신서비스를 이용하실 수 있습니다. <br>
상담사 연결까지 기다림 없이 PC/모바일을 통해 요금제 변경, 부가서비스 신청, 실시간 사용량 조회가 가능합니다. <br>
고객님의 소중한 시간을 아끼기 위한 다양한 편의 서비스가 준비되어 있습니다.</p>
<ul>
<li>AMI(Advanced Metering Infrastructure) : 스마트 미터 및 에너지 관리 시스템</li>
 <li>스마트 시티 : 도시 인프라 모니터링 및 제어 시스템</li>
 <li>원격 모니터링 : 산업용 장비 및 공정 데이터 실시간 모니터링</li>
 <li>차량 관제 및 추적 : 물류, 운송, 공유 차량 관리 시스템</li>
 <li>스마트 팩토리 : IoT 기반 공장 자동화 및 생산 공정 최적화</li>
</ul>
<p>고객 맞춤형 셀룰러 모듈 솔루션을 제공하여 IoT 및 M2M 통신모듈 시장에서 차별화된 경쟁력을 확보해 나가겠습니다.</p>
</div>
                         	</div>

                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div class="content2 margin_top_70" id="content2">
            <div class="title">
                <img src="/resources/company/img/bull2.jpg" alt=""><span>CEO Message</span>
            </div>
            <div class="ceo_message">

                <div>
                    <div class="photo">
                        <img src="/resources/company/img/ceo.png" alt="kt M mobile 대표이사 구강본">
                    </div>
                    <div class="text">
                    	<div class="focus">
		                    <span class="ceo_top1"><b>더 가치 있는 고객의 삶을 위한 대한민국 No.1 통신 파트너</b></span>
		                </div>
                        <div class="text1">
	                               안녕하십니까, kt M mobile 대표이사 구강본 입니다.<br /><br />
	                         kt M mobile에 보내주신 고객 여러분의 신뢰와 성원에 깊이 감사드립니다.<br />
							우리는 고객 최우선을 핵심 가치로 삼고,<br />
							고객을 가장 잘 아는 통신 파트너가 되기 위해 항상 최선을 다하고 있습니다.<br /><br />
							앞으로 kt M mobile은 더 가치 있는 고객의 삶을 위하여,<br />
							여러분께 꼭 필요한 차별화된 상품과 기대를 뛰어넘는 혁신적인 서비스로<br />
							생활 모든 순간을 함께하는 통신 파트너로 자리매김할 것을 약속드립니다.<br /><br />
							kt M mobile 임직원은 고객 여러분의 의견을 귀담아 듣고, 고객의 입장에서 생각합니다.<br />
							여러분께서 들려준 소중한 경험을 참신하고 의미 있는 가치로 발굴하여,<br />
							미래 변화를 선도하는 대한민국 No.1 통신사로 성장해 나가겠습니다.<br /><br />
							kt M mobile과 함께하는 모든 순간이 더욱 특별하고 가치 있는 경험이 되기를 진심으로 바랍니다.<br /><br />
							감사합니다.
                        </div>
                        <div class="ceo_sign">
                           	kt M mobile 대표이사 구강본
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="background_wide" id="content3">
        <div class="content2" >
            <div class="title">
                <img src="/resources/company/img/bull2.jpg" alt=""><span>비전</span>
            </div>
            <div class="vision">
                <div class="graph">
                    <img src="/resources/company/img/vision.png" alt="더 가치 있는 고객의 삶을 위한 대한민국 No.1 통신 파트너 고객의 삶을 더 가치 있게! Beyond No.1 kt M mobile">
                </div>
                <div class="graph2">
                    <div class="item3">
                        <img src="/resources/company/img/vision1.jpg" alt="고객(Customer Value) 고객의 니즈 충족과 문제 해결을 위해 치열하게 고민하고새로운 고객경험을 제시합니다">
                    </div>
                    <div class="item3">
                        <img src="/resources/company/img/vision2.jpg" alt="역량(Excellence) 고객의 문제를 해결하고 고객이 원하는 혁신을 가장 잘 할 수 있도록 전문성을 높입니다">
                    </div>
                    <div class="item3">
                        <img src="/resources/company/img/vision3.jpg" alt="실질(Practical Outcome) 우리 본업인 통신과 ICT를 단단히 하고 화려한 겉모습보다 실질적인 성과를 추구합니다">
                    </div>
                    <div class="item3">
                        <img src="/resources/company/img/vision4.jpg" alt="화합(Togetherness) 다름을 인정하되 서로 존중하고 합심해 함께 목표를 이뤄갑니다">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="background_wide">
        <div class="content2" >
            <div class="title">
                <img src="/resources/company/img/bull2.jpg" alt=""><span>연혁</span>
            </div>
            <div class="introduction story">
	            <div class="detail pc">
	                <table cellpadding="0" cellspacing="0" summary="kt M mobile 개요">
	                    <caption class="hidden">kt M모바일 개요</caption>
	                    <colgroup>
	                        <col>
	                    </colgroup>
	                    <tbody>
	                     <tr>
	                          <th>구분</th>
	                          <th>주요 연혁</th>
	                          <th>구분</th>
                            <th>주요 연혁</th>
	                      </tr>
	                      <tr>
	                          <td>2015</td>
	                          <td>kt M mobile 창립(’15.04.02)</td>
	                          <td>2020</td>
                            <td>
                              MVNO 최초 데이터 차별화 상품 출시<br/>
                              MVNO 최다 제휴채널 구축
                            </td>
	                      </tr>
	                      <tr>
                           <td>2016</td>
                            <td>
                              i-AWARD KOREA ‘통신서비스 분야‘ 대상 수상<br/>
                              MVNO 최다 유통망 구축<br/>
                                     가입자 50만 달성
                            </td>
                            <td>2021</td>
                            <td>
                              MVNO 최초 가입자 100만 달성<br/>
                              KCPI 알뜰폰 부문 우수 선정<br/>
                                     통신 모듈 유통 사업 개시
                            </td>
	                      </tr>
	                      <tr>
	                          <td>2017</td>
                            <td>KS-CQI 콜센터 품질 우수 선정</td>
                            <td>2022</td>
                            <td>
                                      국가 브랜드 경쟁지수 알뜰폰 부분 1위 선정<br/>
                                    온라인 채널(다이렉트몰) 리뉴얼<br/>
                                    가족친화우수기업 여성가족부 장관상 수상<br/>
                              2022 USIM+eSIM 브랜드 ‘양심’ 론칭
                            </td>
	                      </tr>
	                      <tr>
	                         <td>2018</td>
                            <td>
                                    일자리창출 유공 대통령 표창 수상<br/>
                              KS-CQI 콜센터 품질 우수 선정<br/>
                              MVNO 최초 셀프개통 서비스 도입<br/>
                              MVNO 최초 M쇼핑 서비스 런칭
                            </td>
                            <td>2023</td>
                            <td>
                              MVNO 최초 가입자 150만 달성, 매출 3,000억원 달성<br/>
                              MVNO 최초 M쿠폰&M마켓 서비스 런칭<br/>
                                    한국생산성본부 국가브랜드경쟁력지수 알뜰폰 부문 1위 수상<br/>
                                    문화체육관광부 여가친화기업 인증
                            </td>
	                      </tr>
	                      <tr>
	                           <td>2019</td>
                            <td>
                              MVNO 점유율 1위 달성<br/>
                              KS-CQI MVNO 부문 1위 수상<br/>
                              MVNO 최초 편의점 제휴채널 구축
                            </td>
	                          <td>2024</td>
                            <td>
                                    통신업계 최초 AI자동개통 서비스 론칭<br/>
                              MVNO 최초 온라인 직거래 보상 요금제 출시(후후 안심 요금제)<br/>
                              MVNO 최초 편의점 제휴 요금제 출시(CU 요금제)<br/>
                              <span>MVNO 최초 가입자 170만 달성</span>
                            </td>
	                      </tr>
	                    </tbody>
	                 </table>
	            </div>
	            <div class="detail mo">
	              <table cellpadding="0" cellspacing="0" summary="kt M mobile 개요">
	                  <caption class="hidden">kt M모바일 개요</caption>
	                  <colgroup>
                        <col>
                    </colgroup>
                    <tbody>
                     <tr>
		                      <th>구분</th>
		                      <th>주요 연혁</th>
		                  </tr>
		                  <tr>
		                      <td>2015</td>
		                      <td>kt M mobile 창립(’15.04.02)</td>
		                  </tr>
		                  <tr>
                          <td>2016</td>
                          <td>
                            i-AWARD KOREA ‘통신서비스 분야‘ 대상 수상<br/>
														MVNO 최다 유통망 구축<br/>
														가입자 50만 달성
                          </td>
                      </tr>
                      <tr>
                          <td>2017</td>
                          <td>KS-CQI 콜센터 품질 우수 선정</td>
                      </tr>
                      <tr>
                          <td>2018</td>
                          <td>
			                            일자리창출 유공 대통령 표창 수상<br/>
														KS-CQI 콜센터 품질 우수 선정<br/>
														MVNO 최초 셀프개통 서비스 도입<br/>
														MVNO 최초 M쇼핑 서비스 런칭
                          </td>
                      </tr>
                      <tr>
                          <td>2019</td>
                          <td>
                            MVNO 점유율 1위 달성<br/>
														KS-CQI MVNO 부문 1위 수상<br/>
														MVNO 최초 편의점 제휴채널 구축
                          </td>
                      </tr>
                      <tr>
                          <td>2020</td>
                          <td>
                            MVNO 최초 데이터 차별화 상품 출시<br/>
														MVNO 최다 제휴채널 구축
                          </td>
                      </tr>
                      <tr>
                          <td>2021</td>
                          <td>
                            MVNO 최초 가입자 100만 달성<br/>
														KCPI 알뜰폰 부문 우수 선정<br/>
														통신 모듈 유통 사업 개시
                          </td>
                      </tr>
                      <tr>
                          <td>2022</td>
                          <td>
			                            국가 브랜드 경쟁지수 알뜰폰 부분 1위 선정<br/>
														온라인 채널(다이렉트몰) 리뉴얼<br/>
														가족친화우수기업 여성가족부 장관상 수상<br/>
														2022 USIM+eSIM 브랜드  ‘양심’ 론칭
                          </td>
                      </tr>
                      <tr>
                          <td>2023</td>
                          <td>
	                          MVNO 최초 가입자 150만 달성, 매출 3,000억원 달성<br/>
														MVNO 최초 M쿠폰&M마켓 서비스 런칭<br/>
														한국생산성본부 국가브랜드경쟁력지수 알뜰폰 부문 1위 수상<br/>
														문화체육관광부 여가친화기업 인증
                          </td>
                      </tr>
                      <tr>
                          <td>2024</td>
                          <td>
			                            통신업계 최초 AI자동개통 서비스 론칭<br/>
														MVNO 최초 온라인 직거래 보상 요금제 출시(후후 안심 요금제)<br/>
														MVNO 최초 편의점 제휴 요금제 출시(CU 요금제)<br/>
														<span>MVNO 최초 가입자 170만 달성</span>
                          </td>
                      </tr>
                    </tbody>
	               </table>
	          </div>
          </div>
        </div>
    </div>

    <div class="background_wide_gray">
        <div class="content2">
            <div class="title">
                <img src="/resources/company/img/bull2.jpg" alt=""><span>C.I</span>
            </div>
            <div class="ci">
                <img class="ci_main" src="/resources/company/img/ci.png" alt="KT M Mobile CI 로고">
                <div class="download">
                     <!-- <img src="/resources/company/img/ci_download.png"> -->
                    <!--<div class="text" >
                        CI 다운로드
                    </div> -->
                    <div class="ci_list">
                        <div class="ci_item"><img src="/resources/company/img/ci1.jpg" alt="KT M Mobile CI 로고-흰배경에 검은색 빨간색 글씨"></div>
                        <div class="ci_item"><img src="/resources/company/img/ci2.jpg" alt="KT M Mobile CI 로고-흰배경에 검은색 글씨"></div>
                        <div class="ci_item"><img src="/resources/company/img/ci3.jpg" alt="KT M Mobile CI 로고-검은개 배경에 흰색 빨간색 글씨"></div>
                        <div class="ci_item"><img src="/resources/company/img/ci4.jpg" alt="KT M Mobile CI 로고-빨간색배경에 흰색 글씨"></div>
                    </div>
                </div>

                <div class="ci_script">
                    <div class="script1">
                        <span class="title"><span class="text_color_red margin_left_0">소문자</span>, 그리고 휘날림</span><br>
                        고객 눈높이로 친근하게 다가가기 위해
    대문자(KT)에서 소문자(kt)로 변화했습니다.
    이는 고객중심의 기업이 되겠다는 다짐의 표현입니다.
    휘날리는 깃발의 모습은 세계로 뻗어 나가는 글로벌 브랜드의 위상을 상징합니다.
                    </div>
                    <div class="script2">
                        <span class="title"><span class="text_color_red margin_left_0">감성과 신뢰</span>를 담은 컬러</span><br>
                        레드(Red)는 열정과 혁신, 그리고 고객을 향한 따뜻한 감성을 의미하며,
     블랙(Black)은 신뢰를 상징합니다.
     이 두 컬러 간 조화를 통해 kt가 지향하는 가치와 변화의 스토리를
     만들어 나가고자 합니다
                    </div>

                </div>
            </div>
        </div>
    </div>


    <div class="background_wide" id="content4">
        <div class="content2">
            <div class="title">
                <img src="/resources/company/img/bull2.jpg" alt=""><span>윤리경영</span>
            </div>
            <div class="ethic">
                <div class="slo1">
                    <span class="text_color_red">1등 kt M모바일 ”윤리경영원칙”</span>
                </div>
                <div class="slo1_script">
                    우리는 디지털 혁신 파트너 kt M mobile을 만들기 위해 올바른 의사결정과 윤리적 판단으로 회사의 미래를 도모한다. <br>
이를 위해 ‘고객중심, 준법경영, 기본충실, 주인정신, 사회적 책임’을 모든 kt M mobile인이 공유하고,
지켜야 할 윤리경영 5대 행동원칙으로 삼고, 이를 적극 실천할 것을 다짐한다.
                </div>
                <div class="tabbar">
                    <div id="tab1" class="selected">윤리경영원칙</div>
                    <div id="tab2" class="">윤리상담센터</div>
                </div>
                <div class="tab2">
                    <div class="ul1">

                        <div class="title">
                            <span>신고대상</span>
                        </div>
                        <ul>
                            <li>- 업무와 관련 금품수수, 향응 등을 요구하거나 제공받는 행위</li>
                            <li>- 직위를 이용하여 부당한 이득을 얻거나 손실을 끼친 행위</li>
                            <li>- 본인 또는 타인의 공정한 직무수행을 저해하는 행위</li>
                            <li>- 기타 임직원 행동강령 및 행동규범을 위반하여 부당한 이득을 취하는 행위</li>
                            <li>- 임직원이 본의 아니게 금지된 금품 등을 수수하였으나 돌려줄 방법이 없는 경우</li>
                        </ul>
                    </div>
                    <div class="ul1">

                        <div class="title">
                            <span>신고처리</span>
                        </div>
                        <ul>
                            <li>- 부패행위 신고자, 협조자 및 자진신고자에 대한 비밀 및 신분보장</li>
                            <li>- 신고자의 동의 없는 신분공개 및 암시 행위 금지</li>

                        </ul>
                    </div>
                    <div class="ul1">

                        <div class="title">
                            <span>처리절차</span>
                        </div>
                        <ul>
                            <li>- 접수 후 사실 확인과 현장조사를 거쳐 전화 또는 이메일로 결과 통보</li>


                        </ul>
                    </div>
                    <div class="ul1">

                        <div class="title">
                            <span>윤리상담센터 접수 채널</span>
                        </div>
                        <table cellpadding="0" cellspacing="0" summary="윤리상담센터 접수 채널">
                            <caption class="hidden">윤리상담센터 접수 채널</caption>
                            <colgroup>
                                <col />
                            </colgroup>

                            <tr>
                                <th>웹(WEB)</th>
                                <td> 홈페이지 하단 윤리상담센터 > 윤리상담센터 > 상담하기</td>
                            </tr>
                            <tr>
                                <th>전화</th>
                                <td>010-3315-8755</td>
                            </tr>
                            <tr>
                                <th>팩스</th>
                                <td>02-559-8792</td>
                            </tr>
                            <tr>
                                <th>서신</th>
                                <td>서울 강남구 테헤란로 420. kt선릉타워 West 13층(대치동) 주식회사 케이티엠모바일 감사실</td>
                            </tr>
                            <tr>
                                <th>이메일</th>
                                <td>ktm-ethics@kt.com</td>
                            </tr>
                        </table>
                        <br>
                        <c:choose>
                            <c:when test="${fn:contains(header['User-Agent'],'Android') || fn:contains(header['User-Agent'],'iPhone') || fn:contains(header['User-Agent'],'iPad')}">
                                <a href="javascript:alert('PC에서 사용가능합니다.');" ><button>상담하기</button></a>
                            </c:when>
                            <c:otherwise>
                                <a href="javascript:void(0)" id="reportBtn"><button>상담하기</button></a>
                                <a href="javascript:void(0)" id="reportSearchBtn"><button style="margin-left: 15px;border: 1px solid #b5b5b7;background: #b5b5b7;">접수결과 확인</button></a>
                            </c:otherwise>
                        </c:choose>
                    </div>

                </div>
                <div class="tab1">
                <div class="ul1">

                        <div class="title">
                            <img src="/resources/company/img/ul1.jpg" alt=""><span>고객 중심으로 사고하고 행동한다.</span>
                        </div>
                        <ul>
                            <li>1-1. 고객의 가치를 존중하고 정보를 철저히 보호한다.</li>
                            <li>1-2. 고객의 삶의 변화를 이끌 수 있는 차별화된 가치를 끊임없이 창출한다.</li>
                        </ul>
                    </div>
                    <div class="ul1">
                        <div class="title">
                            <img src="/resources/company/img/ul2.jpg" alt=""><span>각종 법령과 규정을 엄격히 준수한다.</span>
                        </div>
                        <ul>
                            <li>2-1. 법과 윤리에 따라 행동하며 컴플라이언스 의무를 다한다.</li>
                            <li>2-2. 부패방지 행동강령을 숙지하고 준수한다.</li>
                            <li>2-3. 경영의 투명성을 확보ㆍ유지하며 회사의 비밀은 철저히 보호한다.</li>
                        </ul>
                    </div>
                    <div class="ul1">
                        <div class="title">
                        <img src="/resources/company/img/ul3.jpg" alt=""><span>기본과 원칙에 충실한다.</span>
                        </div>
                        <ul>
                            <li>3-1. 회사 전체 이익 관점에서 합리적, 객관적으로 판단하고 책임 있게 행동한다.</li>
                            <li>3-2. 공과 사를 엄격히 구분하며 건전한 조직문화 조성에 앞장선다.</li>
                            <li>3-3. 회사의 올바른 성장을 위해 지속 가능한 성과를 추구하고 공정한 보고 문화를 확립한다.</li>
                        </ul>
                    </div>
                    <div class="ul1">
                        <div class="title">
                        <img src="/resources/company/img/ul4.jpg" alt=""><span>스스로 회사와 내가 하나라는 주인정신을 갖는다.</span>
                        </div>
                        <ul>
                            <li>4-1. 실패를 두려워하지 않고 항상 최고에 도전한다.</li>
                            <li>4-2. 고객의 문제를 가장 잘 해결할 수 있도록 역량을 키우고 전문성을 함양한다.</li>
                            <li>4-3. 모든 구성원이 서로 존중하고 하나로 화합하여 함께 목표 달성에 앞장선다.</li>
                        </ul>
                    </div>
                    <div class="ul1">
                        <div class="title">
                        <img src="/resources/company/img/ul5.jpg" alt=""><span>AICT 선도 기업으로서 사회적 책임과 의무를 다한다.</span>
                        </div>
                        <ul>
                            <li>5-1. 주주의 권리와 이익을 보호하고 임직원의 「삶의 질」 향상을 위해 노력한다.</li>
                            <li>5-2. 환경ㆍ안전ㆍ인권을 중시하고 회사가 가진 역량을 활용하여 ESG 경영을 적극 추진한다.</li>
                            <li>5-3. 사회공헌 활동에 앞장서고 사업 파트너와 동반성장의 관계를 구축한다.</li>
                        </ul>
                    </div>
                    </div>
            </div>
        </div>
    </div>


    <div class="background_wide" id="content6">
        <div class="content2">
        <div class="ethic">
            <div class="title">
                <img src="/resources/company/img/bull2.jpg" alt=""><span>고객 최우선 경영</span>
            </div>
            <div class="slo1">
            </div>
            <div class="summary font_size_16">
            kt M mobile은 핵심 가치인 고객 최우선 경영을 위한 고객 경험 혁신 및 고객 권익 보호를 위해 최선을 다 하고 있습니다.
            </div>
            <div class="ul1">
            <div class="title">
            <span>CS 비전</span>
            </div>
                <ul>
                    <li>No.1 통신 파트너로서 고객의 통신 생활에 필요한 다양한 고객 맞춤형 고품질 서비스 제공</li>
                </ul>
            </div>
            <div class="ul1">
            <div class="title">
            <span>고객 최우선 경영 추진 체계</span>
            </div>
                <ul>
                    <li>1. 고객 우선 : 고객 니즈 중심 고객을 향한 진심을 담아 더 편리하고, 더 쉽고, 빠르게 고객의 불편함을 해소</li>
                    <li>2. 품질 우선 : 고객이 원하는 그 이상의 고품질 서비스 제공</li>
                    <li>3. 경험 혁신 : AI기반 다양한 고객 경험 혁신을 통한 새로운 고객 가치 창출</li>
                </ul>
            </div>
            <div class="ul1">
            <div class="title">
            <span>고객만족 실천헌장</span>
            </div>
                <ul>
                    <li>우리의 핵심 가치인 고객 우선 정신을 바탕으로 고객의 입장에서 생각하고 행동하며, 모든 고객이 만족 할 수 있도록 다음과 같이 5대 실천헌장을 지켜나가겠습니다.</li>
                </ul>
            </div>
            <div class="slo1_script">

            <span class="font_weight_bold">하나</span>, 우리는 서비스 실명제를 통하여 고객과의 약속을 반드시 지키겠습니다.<br>
            <span class="font_weight_bold">하나</span>, 우리는 고객이 원하는 것을 신속하게 파악하고 완벽하게 처리하겠습니다.<br>
            <span class="font_weight_bold">하나</span>, 우리는 고객의 참여와 평가를 통하여 고객이 원하는 상품을 만들겠습니다.<br>
            <span class="font_weight_bold">하나</span>, 우리는 다양한 방식으로 365일 24시간 쉼 없이 고객의 소리를 듣겠습니다.<br>
            <span class="font_weight_bold">하나</span>, 우리는 한결같은 마음으로 소외되고 외진 곳 까지 달려가겠습니다.<br>
            </div>
         </div>
         </div>
    </div>


    <div class="background_wide_gray" style="border-bottom:1px solid #d3d3d3;" id="content5">
        <div class="content2">
            <div class="title">
                <img src="/resources/company/img/bull2.jpg" alt=""><span>오시는 길</span>
            </div>

            <div class="location">
                <div class="title1">
                    <img src="/resources/company/img/bull1.jpg" class="bull" alt=""> <span class="subtitle">위치안내</span>
                </div>
                <div id="map" style="background-color:#ddd; height: 400px; margin: 1em 0;"></div>
                <div class="location_text">
                    도로명주소 : 서울특별시 강남구 테헤란로 422. kt선릉타워 East 12층(대치동)
                </div>
                <div class="location_text2">
                    <img src="/resources/company/img/location.png" alt="버스노선"><span>노선버스 : 146, 333, 341, 360, 740, N13, N61, 1100, 1700, 2000-1, 2000, 7007, 8001, 9414, 8146, N31, 9303, G3202, P9601, P9602, M6450</span>
                </div>
                <div class="location_text2">
                    <img src="/resources/company/img/location2.png" alt="지하철"><span>지하철 : 2호선/분당선 선릉역 1번출구, 포스코사거리 방향 450m(도보 6분)
                    </span>
                </div>
             </div>
        </div>
    </div>



<div class="background_wide_gray" style="margin-top:30px;padding-top: 0;z-index:1;">
       <div class="content footer">
            <span>|</span>
            <a href="/company/company.do#content4" target="_blank" title="윤리상담센터 새창열림">윤리상담센터</a>
            <span>|</span>
        </div>
        <div class="content footer" style="margin-top:-20px;padding-top:30px;">
            회사명 : 주식회사 케이티엠모바일&nbsp;|&nbsp;대표이사 : 구강본&nbsp;|&nbsp;사업자등록번호 : 133-81-43410<br>
            주소 : 서울특별시 강남구 테헤란로 422. kt선릉타워 12층(대치동)<br>
            Copyright(c) 2015 kt M mobile. All rights reserved.
            <div class="logo2">
                <img src="/resources/images/company/logo_footer.png" alt="kt M mobile">
            </div>
        </div>

    </div>



<!--


 <div id="map" style="background-color:#ddd; height: 400px; margin: 1em 0;">
                </div>
<div class="row family">
            <div class="col-xs-3 text-right"><h6><img src="/resources/company/images/footer-kt.png" alt="kt family"></h6></div>
            <div class="col-xs-9 select-wrap"><select name="" id="" class="form-control" >
                <optgroup label="대표사이트">
                    <option value="http://www.kt.com/ ">kt</option>
                </optgroup>
                <optgroup label="계열사">
                    <option value="http://www.ktpowertel.co.kr">kt powertel</option>
                    <option value="http://www.ktlinkus.co.kr">kt linkus</option>
                    <option value="http://www.ktsubmarine.co.kr">kt submarine</option>
                    <option value="http://www.ktsat.co.kr">kt sat</option>
                    <option value="http://www.ktcs.co.kr">kt cs</option>
                    <option value="http://www.ktis.co.kr">kt is</option>
                    <option value="http://www.ktmns.com">kt m&amp;s</option>
                    <option value="http://www.kttech.co.kr">kt tech</option>
                    <option value="http://www.ktn.co.kr">kt ens</option>
                    <option value="http://www.ktnexr.com">NexR</option>
                    <option value="http://www.sofnics.com">sofnics</option>
                    <option value="http://www.initech.com/www/html/index.html">initech</option>
                    <option value="http://www.ktcloudware.com">kt cloudware</option>
                    <option value="http://www.skylife.co.kr/index.html">kt skylife</option>
                </optgroup>
                <optgroup label="패밀리사이트">
                    <option value="https://www.ktmmobile.com/">M 모바일</option>
                </optgroup>
            </select></div>
        </div>

         -->


<script src="/resources/company/js/jquery.js"></script>
<!-- <script src="scripts/vendor/jquery-migrate-1.2.1.min.js"></script> -->
<script src="/resources/company/js/bootstrap.js"></script>
<script src="/resources/company/js/main.js"></script>
<script src="/resources/company/js/jquery.bxslider.min.js"></script>
<script type="text/javascript" src="/resources/js/portal/ktm.ui.js"></script>



<script type="text/javascript" src="/resources/company/js/tab.js"></script>
<script type="text/javascript">
$(function(){
    initTabMenu('tabmenu');
});
</script>

            <!-- PC버전 레이어 팝업 S-->
             <div class="dim-layer">
                <div class="dimBg"></div>

                <!--  제보하기 안내 S-->
                <div id="reportFormInfo" class="pop-layer" style="display:none;">
                     <div class="pop-container">
                         <div class="pop-conts">
                             <div class="popup_top">
                                <h4 class="popup_title_h4">윤리상담 유의사항</h4>
                            </div>
                            <div class="popup_content">
                                <div style="font-weight: bold;font-size: 18px;">제보자(성명, 연락처) 및 제보 대상자(소속, 성명 등)를 기재하여 주십시오.</div>
                                <div style="padding-bottom: 15px;">
                                    실명 제보가 원칙이나 익명제보도 구체성과 근거가 명확한 경우에 한하여 조사가 가능하며, 제보자의 신분과 제보내용 및 증거는 대외비로 철저히 보안을 유지하고 있습니다.
                                </div>

                                <div style="font-weight: bold;font-size: 18px;">구체적인 사실을 입력하여 주십시오.</div>
                                <div style="padding-bottom: 15px;">
                                    금품/향응수수, 횡령, 부당 압력 행사, 정보 유출, 특혜제공 등 직무관련 비위행위에 대하여 <span style="color: red;">
                                    사실에 근거한 내용을 가급적 구체적으로(관련된 직원이 누구인지, 언제 발생한 일인지, 어느 부서 또는 어느 지역
                                    인지 등) 작성</span>하여 주실것을 부탁드리며, 관련 근거(사진, 문서, 녹취, 메시지 및 증빙자료)를 첨부하여
                                    주시면 조사에 많은 도움이 됩니다. (사생활, 근거없는 소문, 허위 추정 및 타인 비방 목적의 음해성
                                    제보는 조사를 진행하지 않을 수 있음)
                                </div>

                                <div class="clear_both"></div>

                                <!-- 안내 S  2025.02.21 김지영 숨김처리
                                <div class="mypage_box" style="background: #eaeaea;">
                                    <div class="margin_bottom_5 text_color_666" style="padding: 10px;">
                                        아래 <span style="color: red;">제보 예시 사례</span>를 참고하시기 바랍니다.<br/>
                                        제보자 : 김억울(전화번호 : 010-1234-5678)<br/>
                                        대상자 : KT ㅇㅇ부서 이비위<br/>
                                        내용 : 귀사의 직원 이비위와 본인(김억울)은 2015년 2월부터 함께 KT 관련 사업을 추진하는 관계입니다.
                                        그러던 중 이비위의 요구에 의해 2016년 12월 1일 00도 00시 00카페에서 현금 5백만원을 제공한 사실이
                                        있어 이를 제보하고자 합니다. 해당 금액은 만난 자리에서 스마트폰을 통해 이비위의 계좌로 송금하였고,
                                        영수증은 스캔 파일로 첨부하였습니다. 사업 추진에 영향을 받을 것이 두려워 금품을 제공하였으나 심각
                                        한 비위로 판단되어 제보하게 되었으니 조사하여 주시기 바랍니다. 추가 문의사항이 있을 경우 연락 주십시오.
                                    </div>
                               </div --!>
                               <!-- 안내 E-->
                               <div style="text-align: center; padding-bottom:30px; background:#fff;">
                                       <a href="javascript:void(0)" id="reportFormBtn"><button style="cursor: pointer;height: 35px;border-radius: 20px;border: 1px solid #ed1c24;width: 200px;background: #ed1c24;font-weight: bold;color: white;">확인</button></a>
                               </div>

                            </div>
                             </div>
                    </div>

                    <a class="popup_cancel" href="javascript:void(0)">
                        <img src="/resources/images/company/layer_cancel.png" alt="닫기"/>
                    </a>
                 </div>
                <!--제보하기 안내 E-->


                <!-- 제보하기 입력 폼 S -->
                <div id="reportForm" class="pop-layer" style="display:none;">
                     <div class="pop-container">
                         <div class="pop-conts">
                             <div class="popup_top">
                                <h4 class="popup_title_h4">윤리상담센터</h4>
                            </div>
                            <div class="popup_content">
                                <!-- 안내 S-->
                                <div class="mypage_box" style="background: #eaeaea;">
                                    <ul style="line-height:1.5em;padding: 10px 15px 10px 35px;;margin:0">
                                        <li>실명 제보가 원칙이나 익명제보도 구체성과 근거가 명확한 경우에 한하여 조사가 가능하며, <span style="font-weight: bold;">제보자의 신분과 제보내용 및 증거는 철저히 보안</span>을 유지하고 있습니다.</li>
                                        <li><span style="font-weight: bold;">사실에 근거한 내용을 가급적 구체적으로</span>(관련된 직원명, 발생일자 등) 작성하여 주실 것을 부탁드리며, 사생활, 타인 비방 목적의 음해성 제보는 민원으로 처리되지 않습니다.</li>
                                        <li>신고 내용은 최대한 빠른 시일 내에 처리되며 진행여부는 신고한 사이트에서 확인할 수 있습니다.</li>
                                    </ul>
                               </div>


                               <form id="reportFrm" method="POST" enctype="multipart/form-data">
                                   <div class="board_detail_table" style="padding-top: 10px;">
                                       <table summary="">
                                            <caption class="hidden">윤리상담센터</caption>
                                            <colgroup>
                                                <col width="140px">
                                                <col>
                                            </colgroup>
                                            <tbody>

                                                <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">제보자 성명</th>
                                                    <td>
                                                        <label class="hidden" for="reportName">제보자 성명</label>
                                                        <input type="text" name="reportName" id="reportName" maxlength="30"><span style="padding-left: 15px;">※ 익명제보를 원하실 경우 '익명'으로 기재</span>
                                                    </td>
                                                   </tr>

                                                <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">제보자 휴대폰</th>
                                                    <td>
                                                        <select class="phone" name="reportMobileFn" id="reportMobileFn" title="전화번호 지역번호">
                                                            <option value="010">010</option>
                                                        </select>
                                                                -
                                                            <input type="text" name="reportMobileMn" id="reportMobileMn" maxlength="4" class="phone" title="휴대폰 번호 중간자리 입력">
                                                             -
                                                            <input type="text" name="reportMobileRn" id="reportMobileRn" maxlength="4" class="phone" title="휴대폰 번호 중간자리 입력">
                                                       </td>
                                                   </tr>

                                                <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">제보자 이메일</th>
                                                    <td>
                                                        <input name="reportMail01" id="reportMail01" maxlength="25" type="text" title="이메일 앞자리"> @ <input name="reportMail02" id="reportMail02" maxlength="25" type="text" title="이메일 도메인">
                                                        <select name="selEmail" id="selEmail" class="select_button margin_left_10 margin_right_10 width_100px" title="이메일 도메인 선택">
                                                            <option value="">직접입력</option>
                                                            <option value="naver.com">naver.com</option>
                                                            <option value="nate.com">nate.com</option>
                                                            <option value="dreamwiz.com">dreamwiz.com</option>
                                                            <option value="gmail.com">gmail.com</option>
                                                            <option value="hanmail.net">hanmail.net</option>
                                                        </select>
                                                    </td>
                                                   </tr>

                                                <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">제목</th>
                                                    <td>
                                                        <label class="hidden" for="reportTitle">제목</label>
                                                        <input type="text" name="reportTitle" id="reportTitle" style="width: 80%;" maxlength="100" value="">
                                                    </td>
                                                   </tr>
                                                <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">내용</th>
                                                    <td>
                                                        <label class="hidden" for="reportContent">내용</label>
                                                        <textarea name="reportContent" id="reportContent" maxlength="2000"></textarea>
                                                    </td>
                                                   </tr>

                                                <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row" style="padding-left: 42px;">첨부</th>
                                                    <td style="position: relative;overflow: hidden;">
                                                        <input type="file" title="첨부파일" id="file" name="file" class="insert_file" onchange="Handlechange('file','filename');">
                                                        <input type="text" title="첨부파일 선택" id="filename" readonly="readonly" class="float_left margin_right_10">
                                                        <input type="button" value="파일선택" id="fakeBrowse" class="float_left">
                                                       </td>
                                                   </tr>

                                                <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">결과통보</th>
                                                    <td>
                                                        <div class="payment_option">
                                                            <input type="radio" name="reportResultNotic" id="resPhone" value="P" title="휴대폰" checked><label for="resPhone"> 휴대폰 </label>
                                                            <input type="radio" name="reportResultNotic" id="resEmail" value="E" title="이메일"><label for="resEmail"> 이메일 </label>
                                                            <input type="radio" name="reportResultNotic" id="resNone" value="N" title="미신청"><label for="resNone"> 미신청 </label>
                                                        </div>
                                                    </td>
                                                   </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </form>

                                   <div class="mypagebox margin_bottom_20">
                                       <div class="mypage_title font_size_16 margin_bottom_10">(필수) 개인정보 수집/이용 동의</div>
                                    <div style="padding-bottom: 10px;">
                                        kt M모바일은 서비스 제공을 위하여 필요한 최소한의 개인정보만을 수집합니다. (선택동의 없음)<br/>
                                        고객님은 개인정보 수집/이용에 대한 동의를 거부할 권리가 있습니다. 단, 필수동의사항에 거부할 경우 서비스 이용이 불가하거나 제한이 있을 수 있습니다.

                                    </div>
                                    <div class="box_boder">
                                        <div class="board_line_table">
                                               <table summary="">
                                                   <caption class="hidden">윤리상담센터</caption>
                                                    <colgroup>
                                                        <col width="33%"/>
                                                        <col width="33%"/>
                                                        <col/>
                                                    </colgroup>
                                                    <tbody>
                                                        <tr>
                                                            <th>목적</th>
                                                            <th>항목</th>
                                                            <th>보유기간</th>
                                                        <tr>
                                                        <tr>
                                                            <td>고객님께서 접수한 내용 처리</td>
                                                            <td>성명, 휴대폰, 이메일 등 고객이 제시하는 개인정보</td>
                                                            <td>고객의 신고사항 처리 후 3년(신고내용 재확인 및 통계용)</td>
                                                        <tr>

                                                    </tbody>
                                            </table>
                                        </div>


                                        <div class="notice margin_top_10 padding_top_10">
                                            <div class="font_weight_bold">
                                                <label class="hidden" for="certCheck">동의</label>
                                                <input type="checkbox" id="certCheck">
                                                <span class="margin_right_20">본인은 상기 내용에 대하여 내용을 읽어보았으며 이에 동의합니다.</span>
                                            </div>
                                        </div>

                                    </div>
                                </div>


                               <!-- 안내 E-->
                               <div style="text-align: center;margin-bottom:30px">
                                       <a href="javascript:void(0)" id="submitBtn"><button style="cursor: pointer;height: 35px;border-radius: 20px;border: 1px solid #ed1c24;width: 200px;background: #ed1c24;font-weight: bold;color: white;margin-top: 10px;">등록</button></a>
                               </div>

                            </div>
                             </div>
                    </div>

                    <a class="popup_cancel" href="javascript:void(0)">
                        <img src="/resources/images/company/layer_cancel.png" alt="닫기"/>
                    </a>
                </div>
                <!-- 제보하기 입력 폼 E -->

                <!-- 제보완료 S -->
                <div id="reportCmpl" class="pop-layer" style="display:none;">
                     <div class="pop-container">
                         <div class="pop-conts">
                             <div class="popup_top">
                                <h4 class="popup_title_h4">윤리상담센터</h4>
                            </div>
                            <div class="popup_content">
                                  <div style="font-weight: bold;font-size: 18px;padding-bottom: 10px;">
                                       감사합니다. <br/>
                                       제보해주신 내용이 성공적으로 접수되었습니다.
                               </div>

                               <div style="padding-bottom: 10px;">
                                       제보결과는 제보자 성명과 인증문자를 통해 확인이 가능합니다.<br/>
                                    아래의 <span>인증문자를 기억</span> 부탁 드립니다.
                               </div>

                               <div style="font-weight: bold;font-size: 18px;padding-bottom: 10px;">
                                       인증문자 : <span style="color:red" id="viewCertNumber">000000</span>
                               </div>

                               <div style="padding-bottom: 10px;">
                                       귀하께서 제보해주신 내용은 담당부서에서 빠르게 검토하여 신속하게 시정, 반영될 수 있도록 조치하겠습니다. <br/>
                                    제보해주신 내용에 대한 처리결과는 제보 작성 시 결과통보 신청여부에 따라 처리 결과를 통보 해 드립니다.<br/>
                                    <br/>
                                    kt M모바일을 사랑해주시는 마음에 깊은 감사를 드립니다.
                               </div>

                               <div style="text-align: center;">
                                       <a href="javascript:void(0)" class="layerConfirmBtn"><button style="cursor: pointer;height: 35px;border-radius: 20px;border: 1px solid #ed1c24;width: 200px;background: #ed1c24;font-weight: bold;color: white;margin-top: 10px;">확인</button></a>
                               </div>

                            </div>
                             </div>
                    </div>

                    <a class="popup_cancel" href="javascript:void(0)">
                        <img src="/resources/images/company/layer_cancel.png" alt="닫기"/>
                    </a>
                 </div>
                <!-- 제보완료 E -->

                <!-- 접수결과 확인 S -->
                <div id="reportResultsSch" class="pop-layer" style="display:none;">
                     <div class="pop-container">
                         <div class="pop-conts">
                             <div class="popup_top">
                                <h4 class="popup_title_h4">윤리상담 접수결과 확인</h4>
                            </div>
                            <div class="popup_content">
                                      <!-- 결과조회 입력 S-->
                                      <div id="resultDiv_01">
                                      <!-- 안내 S-->
                                    <div class="mypage_box" style="background: #eaeaea;">
                                        <ul style="line-height:1.5em;padding: 10px 15px 10px 35px;;margin:0">
                                            <li>접수하신 온라인 신고 및 의견에 대한 접수 현황 및 처리상태를 확인하실 수 있습니다.</li>
                                           </ul>
                                   </div>
                                   <!-- 안내 E-->

                                   <div class="board_detail_table" style="padding-top: 10px;">
                                       <table summary="" style="margin-bottom: 0px;">
                                            <caption class="hidden">윤리상담 접수결과 확인</caption>
                                            <colgroup>
                                                <col width="140px">
                                                <col>
                                            </colgroup>
                                            <tbody>
                                                <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row">인증문자</th>
                                                    <td>
                                                        <label class="hidden" for="searchCertNum">인증문자</label>
                                                        <input type="text" id="searchCertNum" maxlength="30">
                                                    </td>
                                                   </tr>
                                                   <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row">성명</th>
                                                    <td>
                                                        <label class="hidden" for="searchName">성명</label>
                                                        <input type="text" id="searchName" maxlength="30">
                                                    </td>
                                                   </tr>
                                               </tbody>
                                           </table>
                                   </div>
                                   <div style="float: right;">※ 익명으로 접수하신 경우 성명란에 '익명'으로 기입 요청드립니다.</div>

                                   <div style="text-align: center;margin-top: 30px;">
                                           <a href="javascript:void(0)" id="reportResultsSchBtn"><button style="cursor: pointer;height: 35px;border-radius: 20px;border: 1px solid #ed1c24;width: 200px;background: #ed1c24;font-weight: bold;color: white;margin-top: 10px;">확인</button></a>
                                   </div>
                               </div>
                               <!-- 결과조회 입력 E-->

                               <!-- 결과조회 완료 S-->
                               <div id="resultDiv_02" style="display:none;">
                                   <div class="board_detail_table" style="padding-top: 10px;">
                                       <table summary="" style="margin-bottom: 0px;">
                                            <caption class="hidden">윤리상담 접수결과 확인</caption>
                                            <colgroup>
                                                <col width="140px">
                                                <col>
                                            </colgroup>
                                            <tbody>
                                                <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row">제보자 성명</th>
                                                    <td id="viewReportName">홍길동</td>
                                                   </tr>
                                                   <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row">제보일자</th>
                                                    <td id="viewReportDt">2018.03.15  13:43:58</td>
                                                   </tr>
                                                   <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row">제목</th>
                                                    <td id="viewReportTitle">윤리상담센터 테스트</td>
                                                   </tr>
                                                   <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row">처리상태</th>
                                                    <td id="viewReportStatus">접수증</td>
                                                   </tr>
                                                   <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row">접수일자</th>
                                                    <td id="viewReportRcvCpltDt">2018.03.15  18:15:28</td>
                                                   </tr>
                                                   <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row">완료일자</th>
                                                    <td id="viewReportAnswerDt"></td>
                                                   </tr>
                                                   <tr style="border-top: 1px solid #d3d3d3;">
                                                    <th scope="row">비고</th>
                                                    <td id="viewReportAnswer"></td>
                                                   </tr>
                                               </tbody>
                                           </table>
                                    </div>

                                    <div style="text-align: center;">
                                           <a href="javascript:void(0)" class="layerConfirmBtn"><button style="cursor: pointer;height: 35px;border-radius: 20px;border: 1px solid #ed1c24;width: 200px;background: #ed1c24;font-weight: bold;color: white;margin-top: 10px;">확인</button></a>
                                       </div>

                               </div>

                            </div>
                             </div>
                    </div>

                    <a class="popup_cancel" href="javascript:void(0)">
                        <img src="/resources/images/company/layer_cancel.png" alt="닫기"/>
                    </a>
                 </div>
                <!-- 접수결과 확인 E -->

            </div>
            <!-- PC버전 레이어 팝업 E-->
    <%@ include file="/WEB-INF/views/layouts/GDNScript.jsp" %>

</body></html>