<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<c:set var="pageTitle" value="회사소개 | kt M mobile" />
<%@ include file="/WEB-INF/views/company/layouts/companyHead.jsp" %>
<body>
  <div class="ly-wrap">
    <!--skip navigation(웹접근성)-->
    <div id="skipNav">
        <a class="skip-link" href="#main-content">본문 바로가기</a>
    </div>

    <%@ include file="/WEB-INF/views/company/layouts/companyHeader.jsp" %>

    <div class="sub-head-banner">
      <div class="sub-head-banner-wrap">
        <p data-aos="fade-right" data-aos-duration="1800" data-aos-delay="300">회사소개</p>
        <img class="img-pc" src="/resources/company/images/sub_company_bg.png" alt="">
        <img class="img-mo" src="/resources/company/images/sub_company_bg_mo.png" alt="">
      </div>
    </div>
    <div class="sub-gnb">
      <button class="sub-gnb__item active" data-target="subGnb1">기업개요</button>
      <button class="sub-gnb__item" data-target="subGnb2">CEO Message</button>
      <button class="sub-gnb__item" data-target="subGnb3">비전/핵심가치</button>
      <button class="sub-gnb__item" data-target="subGnb4">연혁</button>
      <button class="sub-gnb__item" data-target="subGnb5">오시는 길</button>
    </div>
    <div class="ly-content" id="main-content">
        <div class="ly-content-wrap">
          <section class="content-section introduction" id="subGnb1" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="1000">
            <h4 class="content-section__title">기업개요</h4>
            <div class="ly-table">
              <table>
                <caption class="c-hidden">회사명, 대표이사, 설립일, 자본금, 본사위치, 주요사업의 정보로 구성된 기업개요 정보</caption>
                <colgroup>
                  <col>
                  <col>
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">회사명</th>
                    <td>주식회사 케이티엠모바일</td>
                  </tr>
                  <tr>
                    <th scope="row">대표이사</th>
                    <td>구강본</td>
                  </tr>
                  <tr>
                    <th scope="row">설립일</th>
                    <td>2015년 04월 02일</td>
                  </tr>
                  <tr>
                    <th scope="row">자본금</th>
                    <td>1,000억원</td>
                  </tr>
                  <tr>
                    <th scope="row">본사위치</th>
                    <td>서울특별시 강남구 테헤란로 422. kt선릉타워 East 12층(대치동)</td>
                  </tr>
                  <tr>
                    <th scope="row">주요사업</th>
                    <td>MVNO 사업, eSIM 데이터 로밍 사업, 통신 모듈 유통 사업(USIM, eSIM, IoT Module)</td>
                  </tr>
                  <tr>
                    <th scope="row">고객센터 번호</th>
                    <td>1899-5000 (무료 kt M모바일 114)</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </section>
          <section class="content-section ceo" id="subGnb2" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="0">
            <h4 class="content-section__title">CEO Message</h4>
            <div class="ceo-message">
              <div class="ceo-img">
                <img class="img-pc" src="/resources/company/images/company_ceo.png" alt="ceo 이미지">
              </div>
              <div class="ceo-comment">
                <p class="ceo-title">더 가치 있는 고객의 삶을 위한<br/> 대한민국 No.1 통신 파트너</p>
                <img class="img-mo" src="/resources/company/images/company_ceo_mo.png" alt="ceo 이미지">
                <p class="ceo-text">
                  <span>
                        안녕하십니까, kt M mobile 대표이사 구강본 입니다.
                  </span>
                  <span>
                    kt M mobile에 보내주신 고객 여러분의 신뢰와 성원에 깊이 감사 드립니다.<br />
                    우리는 고객 최우선을 핵심 가치로 삼고, 고객을 가장 잘 아는 통신 파트너가 되기 위해 항상 최선을 다하고 있습니다.
                  </span>
                  <span>
                    앞으로 kt M mobile은 더 가치있는 고객의 삶을 위하여,<br />
                    여러분께 꼭 필요한 차별화된 상품과 기대를 뛰어넘는 혁신적인 서비스로 생활 모든 순간을 함께하는<br />
                    통신 파트너로 자리매김할 것을 약속 드립니다.
                  </span>
                  <span>
                    kt M mobile 임직원은 고객 여러분의 의견을 귀담아 듣고, 고객의 입장에서 생각합니다.<br />
                    여러분께서 들려준 소중한 경험을 참신하고 의미 있는 가치로 발굴하여,<br />
                    미래 변화를 선도하는 대한민국 No.1 통신사로 성장 해 나가겠습니다.
                  </span>
                  <span>
                    kt M mobile과 함께하는 모든 순간이 더욱 특별하고 가치 있는 경험이 되기를 진심으로 바랍니다.
                  </span>
                  감사합니다.
                </p>
                <p class="ceo-sign">kt M mobile 대표이사 구강본</p>
              </div>
            </div>
          </section>
          <section class="content-section vision" id="subGnb3" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="0">
            <h4 class="content-section__title">비전/핵심가치</h4>
            <div class="vision-wrap" data-aos="fade-up" data-aos-duration="2400" data-aos-delay="200">
              <div class="vision-list-wrap" >
                <ul class="vision-list">
                  <li class="vision-list__item" data-aos="fade-up" data-aos-duration="2400" data-aos-delay="400">
                    <img class="img-pc" src="/resources/company/images/vision_logo.png" alt="kt M mobile 로고">
                    <img class="img-mo" src="/resources/company/images/vision_logo_mo.png" alt="kt M mobile 로고">
                  </li>
                  <li class="vision-list__item" data-aos="fade-up" data-aos-duration="2400" data-aos-delay="600">
                    <p class="vision-list__title">비전</p>
                    <p class="vision-list__text">더 가치 있는 고객의 삶을 위한 대한민국 No.1 통신 파트너</p>
                  </li>
                  <li class="vision-list__item" data-aos="fade-up" data-aos-duration="2400" data-aos-delay="800">
                    <p class="vision-list__title">슬로건</p>
                    <p class="vision-list__text">고객의 삶을 더 가치 있게! Beyond No.1 kt M mobile</p>
                  </li>
                  <li class="vision-list__item" data-aos="fade-up" data-aos-duration="2400" data-aos-delay="1000">
                    <p class="vision-list__title">핵심가치</p>
                    <ul class="value-list">
                      <li class="value-list__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="1500">
                        <div class="value-list__detail">
                          <p class="value-list__title">고객</p>
                          <p class="value-list__title-sub">(Customer Value)</p>
                          <hr />
                          <p class="value-list__text">고객의 니즈 충족과 문제 해결을 위해 치열하게 고민하고 새로운 고객경험을 제시합니다.</p>
                          <p class="value-list__text mo">Customer Value</p>
                        </div>
                      </li>
                      <li class="value-list__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="2000">
                        <div class="value-list__detail">
                          <p class="value-list__title">역량</p>
                          <p class="value-list__title-sub">(Excellence)</p>
                          <hr />
                          <p class="value-list__text">고객의 문제를 해결하고,고객이 원하는 혁신을 가장  잘할 수 있도록 전문성을 높입니다.</p>
                          <p class="value-list__text mo">Excellence</p>
                        </div>
                      </li>
                      <li class="value-list__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="2500">
                        <div class="value-list__detail">
                          <p class="value-list__title">실질</p>
                          <p class="value-list__title-sub">(Practical Outcome)</p>
                          <hr />
                          <p class="value-list__text">우리 본업인 통신과 ICT를 단단히 하고, 화려한 겉모습보다 실질적인 성과를 추구합니다.</p>
                          <p class="value-list__text mo">Practical Outcome</p>
                        </div>
                      </li>
                      <li class="value-list__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="3000">
                        <div class="value-list__detail">
                          <p class="value-list__title">화합</p>
                          <p class="value-list__title-sub">(Togetherness)</p>
                          <hr />
                          <p class="value-list__text">다름을 인정하되 서로 존중하고 합심해 함께 목표를 이뤄갑니다.</p>
                          <p class="value-list__text mo">Togetherness</p>
                        </div>
                      </li>
                    </ul>
                  </li>
                </ul>
              </div>
            </div>
          </section>
			<section class="content-section" id="subGnb4">
			  <h4 class="content-section__title">연혁</h4>
			  <ul class="company-history">
			    <div class="vertical-line"></div>
			    <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
			      <div class="company-history__date">
			        <p>2026</p>
			      </div>
			      <ul class="company-history__list">
			        <li class="company-history__list-item"><b><span>0</span><span>1</span></b>MVNO 최초 가입자 190만 달성</li>
			      </ul>
			    </li>
			    <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
			      <div class="company-history__date">
			        <p>2025</p>
			      </div>
			      <ul class="company-history__list">
			      	<li class="company-history__list-item"><b><span>1</span><span>2</span></b>eSIM 데이터 로밍 mobi 론칭</li>
			      	<li class="company-history__list-item"><b><span>1</span><span>1</span></b>한국인터넷진흥원(KISA) 정보보호관리체계(ISMS) 인증</li>
			        <li class="company-history__list-item"><b><span>0</span><span>9</span></b>한국표준협회 KS-CQI 7년 연속 「우수」 선정</li>
			        <li class="company-history__list-item"><b><span>0</span><span>8</span></b>한국생산성본부 NBCI 알뜰폰 부문 4년 연속 1위</li>
			        <li class="company-history__list-item"><b><span>0</span><span>6</span></b>음성 기반 AI 상담 「엠봇」 서비스 론칭</li>
			        <li class="company-history__list-item"><b><span>0</span><span>5</span></b>조선일보 BCR 알뜰폰 부문 3년 연속 「대상」 선정</li>
			        <li class="company-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 가입자 180만 달성</li>
			        <li class="company-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 웹 접근성 품질인증 10년 연속 획득</li>
			        <li class="company-history__list-item"><b><span>0</span><span>4</span></b>kt M mobile 창립 10주년</li>
			        <li class="company-history__list-item"><b><span>0</span><span>3</span></b>eSIM 가입 고객 AI자동개통 서비스 지원</li>
			        <li class="company-history__list-item"><b><span>0</span><span>3</span></b>방통위 이용자 보호업무 평가 4년 연속 「우수」 사업자 획득</li>
			      </ul>
			    </li>
			    <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
			      <div class="company-history__date">
			        <p>2024</p>
			      </div>
			      <ul class="company-history__list">
			        <li class="company-history__list-item"><b><span>1</span><span>2</span></b>MVNO 최초 가입자 170만 달성</li>
			        <li class="company-history__list-item"><b><span>1</span><span>2</span></b>고객 보호 서비스 강화를 위한 부정사용방지 TF 신설</li>
			        <li class="company-history__list-item"><b><span>1</span><span>2</span></b>한국능률협회컨설팅 KCPI 알뜰폰 부문 「우수」 선정</li>
			        <li class="company-history__list-item"><b><span>1</span><span>2</span></b>여성가족부 가족친화기업 인증</li>
			        <li class="company-history__list-item"><b><span>0</span><span>9</span></b>한국표준협회 KS-CQI 6년 연속 「우수」 선정</li>
			        <li class="company-history__list-item"><b><span>0</span><span>8</span></b>한국생산성본부 NBCI 알뜰폰 부문 3년 연속 1위</li>
			        <li class="company-history__list-item"><b><span>0</span><span>6</span></b>MVNO 최초 AI자동개통 서비스 론칭</li>
			        <li class="company-history__list-item"><b><span>0</span><span>5</span></b>조선일보 BCR 알뜰폰 부문 2년 연속 「대상」 선정</li>
			        <li class="company-history__list-item"><b><span>0</span><span>5</span></b>웹 접근성 품질인증 9년 연속 획득</li>
			        <li class="company-history__list-item"><b><span>0</span><span>4</span></b>MVNO 최초 가입자 160만 달성</li>
			        <li class="company-history__list-item"><b><span>0</span><span>3</span></b>방통위 이용자 보호업무 평가 3년 연속 「우수」 사업자 획득</li>
			      </ul>
			    </li>
			    <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
			      <div class="company-history__date">
			        <p>2023</p>
			      </div>
			      <ul class="company-history__list">
			        <li class="company-history__list-item"><b><span>1</span><span>1</span></b>MVNO 최초 가입자 150만, 매출 3,000억원 달성</li>
			        <li class="company-history__list-item"><b><span>1</span><span>1</span></b>문화체육관광부 여가친화기업 인증</li>
			        <li class="company-history__list-item"><b><span>0</span><span>9</span></b>한국표준협회 KS-CQI 5년 연속 「우수」 선정</li>
			        <li class="company-history__list-item"><b><span>0</span><span>9</span></b>MVNO 최초 멤버십 쇼핑몰 「M스토어」 론칭</li>
			        <li class="company-history__list-item"><b><span>0</span><span>8</span></b>한국생산성본부 NBCI 알뜰폰 부문 2년 연속 1위</li>
			        <li class="company-history__list-item"><b><span>0</span><span>7</span></b>MVNO 최초 가입자 140만 달성</li>
			        <li class="company-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 상담 예약 서비스 론칭</li>
			        <li class="company-history__list-item"><b><span>0</span><span>5</span></b>웹 접근성 품질인증 8년 연속 획득</li>
			        <li class="company-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 알뜰폰 멤버십 할인 「M쿠폰」 출시</li>
			        <li class="company-history__list-item"><b><span>0</span><span>4</span></b>조선일보 BCR 알뜰폰 부문 「대상」 선정</li>
			        <li class="company-history__list-item"><b><span>0</span><span>3</span></b>방통위 이용자 보호업무 평가 2년 연속 「우수」 사업자 획득</li>
			        <li class="company-history__list-item"><b><span>0</span><span>3</span></b>이용자 피해 방지 위한 보이스피싱 예방 활동 전개</li>
			      </ul>
			    </li>
			    <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
			      <div class="company-history__date">
			        <p>2022</p>
			      </div>
			      <ul class="company-history__list">
			        <li class="company-history__list-item"><b><span>1</span><span>2</span></b>여성가족부 가족친화우수기업 장관상 수상</li>
			        <li class="company-history__list-item"><b><span>1</span><span>2</span></b>MVNO 최초 가입자 130만 달성</li>
			        <li class="company-history__list-item"><b><span>1</span><span>0</span></b>한국표준협회 KS-CQI 4년 연속 「우수」 선정</li>
			        <li class="company-history__list-item"><b><span>1</span><span>0</span></b>eSIM 브랜드 「양심」 론칭</li>
			        <li class="company-history__list-item"><b><span>0</span><span>8</span></b>한국생산성본부 NBCI 알뜰폰 부문 1위</li>
			        <li class="company-history__list-item"><b><span>0</span><span>7</span></b>바로유심 서비스 론칭</li>
			        <li class="company-history__list-item"><b><span>0</span><span>7</span></b>MVNO 최초 가입자 120만 달성</li>
			        <li class="company-history__list-item"><b><span>0</span><span>5</span></b>웹 접근성 품질인증 7년 연속 획득</li>
			        <li class="company-history__list-item"><b><span>0</span><span>5</span></b>한국인터넷진흥원(KISA) 정보보호관리체계(ISMS) 인증</li>
			        <li class="company-history__list-item"><b><span>0</span><span>4</span></b>통신 모듈 유통 사업 론칭</li>
			        <li class="company-history__list-item"><b><span>0</span><span>3</span></b>방통위 이용자 보호업무 평가 「우수」 사업자 획득</li>
			        <li class="company-history__list-item"><b><span>0</span><span>2</span></b>MVNO 최초 가입자 110만 달성</li>
			      </ul>
			    </li>
			    <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="company-history__date">
                    <p>2021</p>
                  </div>
                  <ul class="company-history__list">
                    <li class="company-history__list-item"><b><span>1</span><span>2</span></b>한국능률협회컨설팅 KCPI 알뜰폰 부문 「우수」 선정</li>
                    <li class="company-history__list-item"><b><span>1</span><span>1</span></b>MVNO 최초 가입자 100만 달성</li>
                    <li class="company-history__list-item"><b><span>1</span><span>1</span></b>한국표준협회 KS-CQI 3년 연속 「우수」 선정</li>
                    <li class="company-history__list-item"><b><span>0</span><span>8</span></b>친환경 유심 패키지 도입으로 ESG 실천</li>
                    <li class="company-history__list-item"><b><span>0</span><span>6</span></b>누적 가입자 90만 달성</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>웹 접근성 품질인증 6년 연속 획득</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>한국능률협회컨설팅 KS-QI 4년 연속 「우수 콜센터」 선정</li>
                    <li class="company-history__list-item"><b><span>0</span><span>1</span></b>한국소비자원 알뜰폰 서비스 이용자 만족도 조사 1위</li>
                  </ul>
                </li>
                <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="company-history__date">
                    <p>2020</p>
                  </div>
                  <ul class="company-history__list">
                    <li class="company-history__list-item"><b><span>1</span><span>2</span></b>누적 가입자 80만 달성</li>
                    <li class="company-history__list-item"><b><span>0</span><span>9</span></b>한국표준협회 KS-CQI 2년 연속 「우수」 선정</li>
                    <li class="company-history__list-item"><b><span>0</span><span>9</span></b>산업정책연구원 국가서비스대상 알뜰폰 부문 「대상」 선정</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>한국능률협회컨설팅 KS-QI 3년 연속 「우수 콜센터」 선정</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>웹 접근성 품질인증 5년 연속 획득</li>
                  </ul>
                </li>
                <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="company-history__date">
                    <p>2019</p>
                  </div>
                  <ul class="company-history__list">
                    <li class="company-history__list-item"><b><span>1</span><span>2</span></b>여성가족부 가족친화기업 인증 </li>
                    <li class="company-history__list-item"><b><span>0</span><span>9</span></b>한국표준협회 KS-CQI 「우수」 선정</li>
                    <li class="company-history__list-item"><b><span>0</span><span>9</span></b>MVNO 시장 점유율 1위 달성</li>
                    <li class="company-history__list-item"><b><span>0</span><span>8</span></b>한국인터넷진흥원(KISA) 정보보호관리체계(ISMS) 인증</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>한국능률협회컨설팅 KS-QI 2년 연속 「우수 콜센터」 선정</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>웹 접근성 품질인증 4년 연속 획득</li>
                  </ul>
                </li>
                <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="company-history__date">
                    <p>2018</p>
                  </div>
                  <ul class="company-history__list">
                    <li class="company-history__list-item"><b><span>1</span><span>2</span></b>고용노동부 일자리 창출 유공 산업포장 수훈</li>
                    <li class="company-history__list-item"><b><span>0</span><span>8</span></b>누적 가입자 70만 달성</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>한국능률협회컨설팅 KS-QI 「우수 콜센터」 선정</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 셀프개통 서비스 도입·운영</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>웹 접근성 품질인증 3년 연속 획득</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 「M쇼핑」 오픈</li>
                  </ul>
                </li>
                <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="company-history__date">
                    <p>2017</p>
                  </div>
                  <ul class="company-history__list">
                    <li class="company-history__list-item"><b><span>0</span><span>8</span></b>누적 가입자 60만 달성</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>웹 접근성 품질인증 2년 연속 획득</li>
                  </ul>
                </li>
                <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="company-history__date">
                    <p>2016</p>
                  </div>
                  <ul class="company-history__list">
                    <li class="company-history__list-item"><b><span>1</span><span>2</span></b>i-AWARD KOREA 통신서비스 분야 「대상」 수상</li>
                    <li class="company-history__list-item"><b><span>1</span><span>2</span></b>누적 가입자 50만 달성</li>
                    <li class="company-history__list-item"><b><span>0</span><span>8</span></b>한국인터넷진흥원(KISA) 정보보호관리체계(ISMS) 인증</li>
                    <li class="company-history__list-item"><b><span>0</span><span>5</span></b>웹 접근성 품질인증 획득</li>
                  </ul>
                </li>
			    <li class="company-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
			      <div class="company-history__date">
			        <p>2015</p>
			      </div>
			      <ul class="company-history__list">
			        <li class="company-history__list-item"><b><span>0</span><span>4</span></b>kt M mobile 창립(2015.04.02)</li>
			      </ul>
			    </li>
			  </ul>
			</section>
			<section class="content-section location" id="subGnb5">
			  <h4 class="content-section__title pc">오시는 길</h4>
			  <div id="map"></div>
			  <div class="ly-table">
			    <table>
			      <caption class="c-hidden">회사명, 대표이사, 설립일, 자본금, 본사위치, 주요사업의 정보로 구성된 기업개요 정보</caption>
			      <colgroup>
			        <col>
			        <col>
			      </colgroup>
			      <tbody>
			        <tr>
			          <th scope="row"><p class="bullet">도로명주소</p></th>
			          <td>서울특별시 강남구 테헤란로 422 kt선릉타워 12층 (대치동)</td>
			        </tr>
			        <tr>
			          <th scope="row"><p class="bullet">노선버스</p></th>
			          <td>146, 333, 341, 360, 740, 1100, 1700, 2000, 2000-1, 7007, 8001, 8146, 9303, 9414, G3202, M6450, N13, N31, N61, P9601, P9602</td>
			        </tr>
			        <tr>
			          <th scope="row"><p class="bullet">지하철</p></th>
			          <td>2호선/분당선 선릉역 1번출구, 포스코사거리 방향 450m(도보 6분)</td>
			        </tr>
			      </tbody>
			    </table>
			  </div>
			</section>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/company/layouts/companyFloatButton.jsp" %>
    <%@ include file="/WEB-INF/views/company/layouts/companyFooter.jsp" %>

    <!-- 딤처리 오버레이 -->
    <div class="overlay" id="overlayPc" onclick="toggleMenuAll()"></div>
    <div class="overlay" id="overlayMo" onclick="toggleMenu()"></div>
  </div>

  <%@ include file="/WEB-INF/views/company/layouts/commonJs.jsp" %>
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=de6f55cc92785c6c337ebfcf3aba1470"></script>

  <script>

    /* 지도 */
    $(document).ready(function(){ // 페이지 처음 들어올때 지도 셋팅
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
            content: '<div align="center" style="padding:5px;width: 163px;">케이티엠모바일</div>'
        });
        infowindow.open(map, marker);
        map.setCenter(coords);
    });
  </script>
</body>
</html>