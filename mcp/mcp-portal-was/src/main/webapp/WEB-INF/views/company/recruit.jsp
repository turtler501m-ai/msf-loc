<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- saved from url=(0052)http://www.ktmmobile.co.kr/recruit.aspx#_rightpeople -->
<html class=" js svg" lang="ko"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>kt M mobile </title>
    <meta http-equiv="content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/resources/company/css/ktmmobile_layout.css">

    <!--[if lt IE 9]>
    <script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js"></script>
    <![endif]-->
    <script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
    <script>
        $(document).ready(function(){

            $('.real_menu').mouseover(function(){
                $('.sub_menu').slideDown();
            });
            $('.sub_menu').mouseleave(function(){
                $('.sub_menu').slideUp();
            });
            $('.gnb_mobile .menu').click(function(){
                $('.sub_menu_mobile').slideToggle();
            });

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

    <!-- 컨텐츠 시작 -->
    <div class="ly-content">
        <div class="content-section" id="content1">
            <div class="content-wrap">
                <h3 class="content__tit">인재상</h3>
                <div class="content__box">
                    <div class="recruit-ideal-wrap">
                        <div class="recruit-ideal__item bg-point">
                            <img class="recruit-ideal-img" src="/resources/company/img/recruit_ideal_01.png" alt="">
                            <img class="recruit-ideal-m-img" src="/resources/company/img/recruit_ideal_m_01.png" alt="">
                            <p class="recruit-ideal__txt">끊임없이 도전하는 인재</p>
                            <ul class="recruit-ideal__txtlist">
                                <li>시련과 역경을 극복하고 목표를 향해 끊임없이 도전하여 최고의 수준을 달성한다.</li>
                                <li>변화와 혁신을 선도하여 차별화된 서비스를 구현한다.</li>
                            </ul>
                            <img class="bg-img" src="/resources/company/img/recruit_ideal_logo.png" alt="">
                        </div>
                        <div class="recruit-ideal__item">
                            <img class="recruit-ideal-img" src="/resources/company/img/recruit_ideal_02.png" alt="">
                            <img class="recruit-ideal-m-img" src="/resources/company/img/recruit_ideal_m_02.png" alt="">
                            <p class="recruit-ideal__txt">벽 없이 소통하는 인재</p>
                            <ul class="recruit-ideal__txtlist">
                                <li>동료 간 적극적으로 소통하여 서로의 성장과 발전을 위해 끊임없이 노력한다.</li>
                                <li>kt M mobile의 성공을 위해 상호 협력하여 시너지를 창출한다.</li>
                            </ul>
                        </div>
                        <div class="recruit-ideal__item">
                            <img class="recruit-ideal-img" src="/resources/company/img/recruit_ideal_03.png" alt="">
                            <img class="recruit-ideal-m-img" src="/resources/company/img/recruit_ideal_m_03.png" alt="">
                            <p class="recruit-ideal__txt">고객을 존중하는 인재</p>
                            <ul class="recruit-ideal__txtlist">
                                <li>모든 업무 수행에 있어 고객의 이익과 만족을 먼저 생각한다.</li>
                                <li>고객을 존중하고, 고객과의 약속을 반드시 지킨다.</li>
                            </ul>
                        </div>
                        <div class="recruit-ideal__item bg-point">
                            <img class="recruit-ideal-img" src="/resources/company/img/recruit_ideal_04.png" alt="">
                            <img class="recruit-ideal-m-img" src="/resources/company/img/recruit_ideal_m_04.png" alt="">
                            <p class="recruit-ideal__txt">기본과 원칙을 지키는 인재</p>
                            <ul class="recruit-ideal__txtlist">
                                <li>회사의 주인은 나라는 생각으로 자부심을 갖고 업무를 수행한다.</li>
                                <li>윤리적 판단에 따라 행동하며 결과에 대해 책임을 진다.</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="content-wide" id="content2">
            <div class="content-section">
                <div class="content-wrap">
                    <h3 class="content__tit">인사제도</h3>
                    <div class="content__box">
                        <div class="recruit-person-wrap">
                            <div class="recruit-person__item">
                                <img class="recruit-person-img "src="/resources/company/img/recruit_person_01.png" alt="">
                                <div class="recruit-person-m-img">
                                    <img src="/resources/company/img/recruit_person_m_01.png" alt="">
                                    <p>승진제도 </p>
                                </div>
                            </div>
                            <div class="recruit-person__item">
                                <p class="recruit-person__tit">승진제도</p>
                                <p class="recruit-person__txt">개인성과/역량 및 대내외 활동들을 매년 마일리지로 환산 부여하며, 직급별 일정 기간의 누적 마일리지를 기반으로 승진자를 선정, 예측 가능하고 투명한 승진을 시행하고 있습니다. 또한, 성과가 뛰어난 직원에게는 발탁 승진의 기회를 부여하여 우수한 인재가 회사 내에서 빠르게 성장할 수 있는 승진제도를 운영하고 있습니다.</p>
                            </div>
                            <div class="recruit-person__item">
                                <p class="recruit-person__tit">역량강화 교육</p>
                                <p class="recruit-person__txt">직원들이 업무를 수행하는 데 공통적으로 필요한 역량은 물론 전문가로 성장할 수 있도록 온라인 및 오프라인을 통한 역량강화 교육 및 향후 회사를 이끌어갈 리더로 성장하기 위한 체계적인 리더십 강화 교육 과정을 운영하고 있습니다.</p>
                            </div>
                            <div class="recruit-person__item">
                                <img class="recruit-person-img" src="/resources/company/img/recruit_person_02.png" alt="">
                                <div class="recruit-person-m-img">
                                    <img src="/resources/company/img/recruit_person_m_02.png" alt="">
                                    <p>역량강화 교육  </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="content-section" id="content3">
            <div class="content-wrap">
                <h3 class="content__tit">복리후생</h3>
                <div class="content__box">
                    <div class="recruit-welfarel-wrap">
                        <div class="recruit-welfarel-box">
                            <p class="recruit-welfarel-tit">자유로운 휴일·휴가</p>
                            <div class="recruit-welfarel-list">
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_01.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">M-DAY</p>
                                        <p class="recruit-welfarel-list__txt">월 1회 조기 퇴근</p>
                                    </div>
                                </div>
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_02.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">기념일 Day</p>
                                        <p class="recruit-welfarel-list__txt">생일/결혼(기혼)/입사(미혼) 기념일 조기 퇴근</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="recruit-welfarel-box">
                            <p class="recruit-welfarel-tit">업무 몰입 지원</p>
                            <div class="recruit-welfarel-list">
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_03.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">PC OFF제</p>
                                        <p class="recruit-welfarel-list__txt">정시퇴근 보장을 위한 자동 PC 차단</p>
                                    </div>
                                </div>
                                <!--  div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_04.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">캐주얼 day</p>
                                        <p class="recruit-welfarel-list__txt">매주 금요일 캐주얼한 복장으로 출근</p>
                                    </div>
                                </div -->
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_05.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">기업문화 TF</p>
                                        <p class="recruit-welfarel-list__txt">기업문화 개선을 위한 노력</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="recruit-welfarel-box">
                            <p class="recruit-welfarel-tit">리프레시 지원</p>
                            <div class="recruit-welfarel-list">
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_06.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">여행포인트</p>
                                        <p class="recruit-welfarel-list__txt">매년 여행 포인트 지급</p>
                                    </div>
                                </div>
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_07.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">사내 동호회</p>
                                        <p class="recruit-welfarel-list__txt">사내 동호회 활동비용 지원</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="recruit-welfarel-box">
                            <p class="recruit-welfarel-tit">일상 속 지원</p>
                            <div class="recruit-welfarel-list">
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_08.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">통신비</p>
                                        <p class="recruit-welfarel-list__txt">KT / kt M모바일 통신비 지원</p>
                                    </div>
                                </div>
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_09.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">복지포인트</p>
                                        <p class="recruit-welfarel-list__txt">여가 생활을 위한 복지 포인트 지급</p>
                                    </div>
                                </div>
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_10.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">경조금</p>
                                        <p class="recruit-welfarel-list__txt">기쁨과 슬픔을 함께 지원</p>
                                    </div>
                                </div>
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_11.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">대출</p>
                                        <p class="recruit-welfarel-list__txt">주택마련/임차/생활안정/신생아출산/<br>입사 전 본인 학자금을 낮은 이자율로 지원
</p>
                                    </div>
                                </div>
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_12.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">보육수당</p>
                                        <p class="recruit-welfarel-list__txt">만0세~만18세 이하까지 매달 지원</p>
                                    </div>
                                </div>
                                <!-- div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_13.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">일체복 지원</p>
                                        <p class="recruit-welfarel-list__txt">세련된 디자인의 일체복 무료 지원</p>
                                    </div>
                                </div -->
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_19.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">자기계발</p>
                                        <p class="recruit-welfarel-list__txt">자기계발 비용 지원</p>
                                    </div>
                                </div>
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_20.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">육아지원</p>
                                        <p class="recruit-welfarel-list__txt">출산 축하, 산후조리 비용 지원</p>
                                    </div>
                                </div>


                            </div>
                        </div>
                        <div class="recruit-welfarel-box">
                            <p class="recruit-welfarel-tit">건강 증진 지원</p>
                            <div class="recruit-welfarel-list">
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_14.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">의료비</p>
                                        <p class="recruit-welfarel-list__txt">본인과 가족의 의료비 지원</p>
                                    </div>
                                </div>
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_15.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">건강검진</p>
                                        <p class="recruit-welfarel-list__txt">본인과 가족의 건강검진 지원</p>
                                    </div>
                                </div>
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_16.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">워크숍</p>
                                        <p class="recruit-welfarel-list__txt">협동심과 체력 증진</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="recruit-welfarel-box">
                            <p class="recruit-welfarel-tit">축하금 지급</p>
                            <div class="recruit-welfarel-list">
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_17.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">안식년 지원</p>
                                        <p class="recruit-welfarel-list__txt">장기 근속 시 보로금 및 휴가 지원</p>
                                    </div>
                                </div>
                                <div class="recruit-welfarel__item">
                                    <img src="/resources/company/img/recruit_welfare_18.png" alt="">
                                    <div>
                                        <p class="recruit-welfarel-list__tit">Welcome Bonus</p>
                                        <p class="recruit-welfarel-list__txt">신입사원 수습 종료 후 축하금 지급</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- footer -->
    <div class="background_wide_gray" style="margin-top:30px;padding-top: 0;z-index:100;">
        <div class="content footer">
            <span>|</span>
            <a href="/company/company.do#content4" target="_blank" title="윤리상담센터 새창열림">윤리상담센터</a>
            <span>|</span>
        </div>
        <div class="content footer" style="margin-top: -20px; padding-top:30px;">
            회사명 : 주식회사 케이티엠모바일&nbsp;|&nbsp;대표이사 : 구강본&nbsp;|&nbsp;사업자등록번호 : 133-81-43410<br/>
            주소 : 서울특별시 강남구 테헤란로 422. kt선릉타워 12층(대치동)<br/>
            Copyright(c) 2015 kt M mobile. All rights reserved.
            <div class="logo2">
                <img src="/resources/images/company/logo_footer.png" alt="kt M mobile">
            </div>
        </div>
    </div>


    <script src="/resources/company/js/jquery.js"></script>
    <!-- <script src="scripts/vendor/jquery-migrate-1.2.1.min.js"></script> -->
    <script src="/resources/company/js/bootstrap.js"></script>
    <script src="/resources/company/js/main.js"></script>
    <script src="/resources/company/js/jquery.bxslider.min.js"></script>
    <%@ include file="/WEB-INF/views/layouts/GDNScript.jsp" %>
</body></html>