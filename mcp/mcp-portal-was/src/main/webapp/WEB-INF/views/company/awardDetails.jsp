<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<c:set var="pageTitle" value="홍보관 | kt M mobile" />
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
        <p data-aos="fade-right" data-aos-duration="1800" data-aos-delay="300">홍보관</p>
        <img class="img-pc" src="/resources/company/images/sub_marketing_bg.png" alt="">
        <img class="img-mo" src="/resources/company/images/sub_marketing_bg_mo.png" alt="">
      </div>
    </div>
    <div class="sub-gnb sub-gnb--center">
      <a class="sub-gnb__item" href="/company/mediaList.do#subGnb1" title="언론보도 페이지 이동">언론보도</a>
      <button class="sub-gnb__item" data-target="subGnb1">수상내역</button>
      <a class="sub-gnb__item" href="/company/noticeList.do#subGnb1" title="공지사항 페이지 이동">공지사항</a>
    </div>
    <div class="ly-content" id="main-content">
        <div class="ly-content-wrap">
          <section class="content-section" id="subGnb1">
            <h4 class="content-section__title">수상내역</h4>
            <ul class="award-board-list">
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/award_list_01.png" alt="KS-CQI(콜센터품질지수) 우수기업 인증 마크">
                  <img class="img-mo" src="/resources/company/images/award_list_01_mo.png" alt="KS-CQI(콜센터품질지수) 우수기업 인증 마크">
                </div>
                <div class="award-board-text">
	              <p class="award-board-agency">[한국표준협회]</p>
	              <p class="award-board-title">KS-CQI(콜센터품질지수) 우수기업</p>
	              <p class="award-board-desc">7년 연속 수상(2019~2025년)</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/award_list_02.png" alt="NBCI(국가브랜드경쟁력지수) 알뜰폰 부문 1위 인증 마크">
                  <img class="img-mo" src="/resources/company/images/award_list_02_mo.png" alt="NBCI(국가브랜드경쟁력지수) 알뜰폰 부문 1위 인증 마크">
                </div>
                <div class="award-board-text">
	              <p class="award-board-agency">[한국생산성본부]</p>
	              <p class="award-board-title">NBCI(국가브랜드경쟁력지수) 알뜰폰 부문 1위</p>
	              <p class="award-board-desc">4년 연속 수상(2022~2025년)</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/award_list_03.png" alt="BCR(소비자추천 1위 브랜드) 알뜰폰 부문 대상 인증 마크">
                  <img class="img-mo" src="/resources/company/images/award_list_03_mo.png" alt="BCR(소비자추천 1위 브랜드) 알뜰폰 부문 대상 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[조선일보]</p>
                  <p class="award-board-title">BCR(소비자추천 1위 브랜드) 알뜰폰 부문 대상</p>
                  <p class="award-board-desc">3년 연속 수상(2023~2025년)</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/award_list_04.png" alt="KCPI(한국의 소비자보호지수) 알뜰폰 부문 우수기업 인증 마크">
                  <img class="img-mo" src="/resources/company/images/award_list_04_mo.png" alt="KCPI(한국의 소비자보호지수) 알뜰폰 부문 우수기업 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[한국능률협회컨설팅]</p>
                  <p class="award-board-title">KCPI(한국의 소비자보호지수) 알뜰폰 부문 우수기업</p>
                  <p class="award-board-desc">2021년, 2024년</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/award_list_05.png" alt="국가서비스대상 알뜰폰 부문 대상 인증 마크">
                  <img class="img-mo" src="/resources/company/images/award_list_05_mo.png" alt="국가서비스대상 알뜰폰 부문 대상 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[산업정책연구원]</p>
                  <p class="award-board-title">국가서비스대상 알뜰폰 부문 대상</p>
                  <p class="award-board-desc">2020년</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/award_list_06.png" alt="KS-QI(한국산업의 서비스품질지수) 우수 콜센터 인증 마크">
                  <img class="img-mo" src="/resources/company/images/award_list_06_mo.png" alt="KS-QI(한국산업의 서비스품질지수) 우수 콜센터 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[한국능률협회컨설팅]</p>
                  <p class="award-board-title">KS-QI(한국산업의 서비스품질지수) 우수 콜센터</p>
                  <p class="award-board-desc">2018~2021년</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/award_list_07.png" alt="i-AWARD KOREA 통신서비스 분야 대상 인증 마크">
                  <img class="img-mo" src="/resources/company/images/award_list_07_mo.png" alt="i-AWARD KOREA 통신서비스 분야 대상 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[한국인터넷전문가협회]</p>
                  <p class="award-board-title">i-AWARD KOREA 통신서비스 분야 대상</p>
                  <p class="award-board-desc">2016년</p>
                </div>
              </li>
            </ul>
          </section>
          <section class="content-section award">
            <h4 class="content-section__title">인증/표창</h4>
            <ul class="award-board-list">
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/certification_list_01.png" alt="여가친화기업 인증 마크">
                  <img class="img-mo" src="/resources/company/images/certification_list_01_mo.png" alt="여가친화기업 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[문화체육관광부]</p>
                  <p class="award-board-title">여가친화기업 인증</p>
                  <p class="award-board-desc">2023~현재</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/certification_list_02.png" alt="이용자 보호업무 평가 우수 사업자 인증 마크">
                  <img class="img-mo" src="/resources/company/images/certification_list_02_mo.png" alt="이용자 보호업무 평가 우수 사업자 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[방송미디어통신위원회]</p>
                  <p class="award-board-title">이용자 보호업무 평가 우수 사업자</p>
                  <p class="award-board-desc">2022~현재</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/certification_list_03.png" alt="가족친화우수기업 장관상 수상 인증 마크">
                  <img class="img-mo" src="/resources/company/images/certification_list_03_mo.png" alt="가족친화우수기업 장관상 수상 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[여성가족부]</p>
                  <p class="award-board-title">가족친화우수기업 장관상 수상</p>
                  <p class="award-board-desc">2022년</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/certification_list_04.png" alt="가족친화기업 인증 마크">
                  <img class="img-mo" src="/resources/company/images/certification_list_04_mo.png" alt="가족친화기업 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[여성가족부]</p>
                  <p class="award-board-title">가족친화기업 인증</p>
                  <p class="award-board-desc">2019~현재</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/certification_list_05.png" alt="일자리 창출 유공 산업포장 수훈 인증 마크">
                  <img class="img-mo" src="/resources/company/images/certification_list_05_mo.png" alt="일자리 창출 유공 산업포장 수훈 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[고용노동부]</p>
                  <p class="award-board-title">일자리 창출 유공 산업포장 수훈</p>
                  <p class="award-board-desc">2018년</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/certification_list_06.png" alt="ISMS 정보보호관리체계 인증 마크">
                  <img class="img-mo" src="/resources/company/images/certification_list_06_mo.png" alt="ISMS 정보보호관리체계 인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[한국인터넷진흥원]</p>
                  <p class="award-board-title">ISMS 정보보호관리체계 인증</p>
                  <p class="award-board-desc">2016~현재</p>
                </div>
              </li>
              <li class="award-board-item">
                <div class="award-board-img">
                  <img class="img-pc" src="/resources/company/images/certification_list_07.png" alt="웹 접근성 품질인증 마크">
                  <img class="img-mo" src="/resources/company/images/certification_list_07_mo.png" alt="웹 접근성 품질인증 마크">
                </div>
                <div class="award-board-text">
                  <p class="award-board-agency">[한국웹접근성인증평가원]</p>
                  <p class="award-board-title">웹 접근성 품질인증</p>
                  <p class="award-board-desc">10년 연속 인증(2016~현재)</p>
                </div>
              </li>
            </ul>
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

</body>
</html>