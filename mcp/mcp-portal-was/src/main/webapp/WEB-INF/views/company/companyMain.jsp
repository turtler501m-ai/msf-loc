<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<c:set var="pageTitle" value="kt M mobile" />
<%@ include file="/WEB-INF/views/company/layouts/companyHead.jsp" %>
<body>
  <div class="ly-wrap">
    <!--skip navigation(웹접근성)-->
    <div id="skipNav">
        <a class="skip-link" href="#main-content">본문 바로가기</a>
    </div>

    <%@ include file="/WEB-INF/views/company/layouts/companyHeader.jsp" %>

    <section class="main-banner">
      <div class="swiper main-swiper">
        <div class="swiper-wrapper">
          <div class="swiper-slide">
            <div class="txt-box">
              <h3 class="txt">더 가치있는 고객의 삶을 위한<br /><b>대한민국 No.1 통신 파트너</b></h3>
            </div>
            <picture class="img-box">
              <img class="img-pc" src="/resources/company/images/main_banner_pc_01.png" alt="1번째 메인배너 이미지">
              <img class="img-mo" src="/resources/company/images/main_banner_mo_01_edit.png" alt="1번째 메인배너 이미지">
            </picture>
          </div>
          <div class="swiper-slide white">
            <div class="txt-box">
              <h3 class="txt">kt M mobile은 MVNO사업과<br />eSIM 데이터 로밍 사업 및 통신 모듈 유통 사업을 운영하고 있는<br /><b>대한민국 No.1 MVNO Company</b></h3>
            </div>
            <picture class="img-box">
              <img class="img-pc" src="/resources/company/images/main_banner_02.png" alt="2번째 메인배너 이미지">
            <img class="img-mo" src="/resources/company/images/main_banner_mo_02.png" alt="2번째 메인배너 이미지">
            </picture>
          </div>
          <div class="swiper-slide">
            <div class="txt-box">
              <h3 class="txt partner">언제나 고객을 생각하며<br /><b>고객을 가장 잘 아는 통신 파트너</b>가 되기 위해 <br />최선을 다하겠습니다.</h3>
            </div>
            <picture class="img-box">
              <img class="img-pc" src="/resources/company/images/main_banner_03.png" alt="3번째 메인배너 이미지">
              <img class="img-mo" src="/resources/company/images/main_banner_mo_03.png" alt="3번째 메인배너 이미지">
            </picture>
          </div>
        </div>
        <div class="all-box">
          <div class="progress-box">
            <div class="swiper-pagination"></div>
          </div>
          <div class="arrow-box">
            <div class="swiper-button-prev"></div>
            <div class="swiper-button-next"></div>
            <button id="playPauseBtn" class="pause"><i class="c-icon c-icon--pause" aria-hidden="true"></i></button>
          </div>
        </div>
        </div>
    </section>
    <div class="ly-content" id="main-content">
        <div class="ly-content-wrap">
          <section class="content-section main benefit">
            <div data-aos="fade-up" data-aos-duration="1300">
              <a href="https://www.ktmmobile.com" title="다이렉트몰 페이지 새창 이동" target="_blank">
                  <h4 class="content-section__title">kt M모바일만의 다양한 혜택</h4>
                  <p class="content-section__text">지금 바로 kt M모바일에 접속하여<br />다양한 유심요금제와 이벤트를 경험해 보세요.</p>
              </a>
            </div>
            <!-- PC benefit-->
            <ul class="main-benefit pc">
              <li class="main-benefit__item" data-aos="fade-right" data-aos-duration="800" data-aos-delay="200">
                <a href="https://www.ktmmobile.com/appForm/appSimpleInfo.do" title="M모바일 가입 페이지 새창 이동" target="_blank">
                  <div class="main-benefit__info">
                    <i class="c-icon c-icon--benefit-mmobile" aria-hidden="true"></i>
                    <p>M모바일 가입</p>
                  </div>
                </a>
              </li>
              <li class="main-benefit__item" data-aos="fade-down" data-aos-duration="800" data-aos-delay="200">
                <a href="https://www.ktmmobile.com/rate/rateList.do" title="요금제 소개 페이지 새창 이동" target="_blank">
                  <div class="main-benefit__info">
                    <i class="c-icon c-icon--benefit-rate" aria-hidden="true"></i>
                    <p>요금제 소개</p>
                  </div>
                </a>
              </li>
              <li class="main-benefit__item" data-aos="fade-up" data-aos-duration="800" data-aos-delay="200">
                <a href="https://www.ktmmobile.com/event/eventBoardList.do" title="이벤트 페이지 새창 이동" target="_blank">
                  <div class="main-benefit__info">
                    <i class="c-icon c-icon--benefit-event" aria-hidden="true"></i>
                    <p>이벤트</p>
                  </div>
                </a>
              </li>
              <li class="main-benefit__item" data-aos="fade-left" data-aos-duration="800" data-aos-delay="200">
                <a href="https://www.ktmmobile.com/requestReView/requestReView.do" title="사용후기 페이지 새창 이동" target="_blank">
                  <div class="main-benefit__info">
                    <i class="c-icon c-icon--benefit-review" aria-hidden="true"></i>
                    <p>사용후기</p>
                  </div>
                </a>
              </li>
            </ul>
            <!-- mo benefit-->
            <ul class="main-benefit mo">
              <li class="main-benefit__item" data-aos="fade-right" data-aos-duration="800" data-aos-delay="200">
                <a href="https://www.ktmmobile.com/appForm/appSimpleInfo.do" title="M모바일 가입 페이지 새창 이동" target="_blank">
                  <div class="main-benefit__info">
                    <i class="c-icon c-icon--benefit-mmobile" aria-hidden="true"></i>
                  </div>
                  <p>M모바일 가입</p>
                </a>
              </li>
              <li class="main-benefit__item" data-aos="fade-down" data-aos-duration="800" data-aos-delay="200">
                <a href="https://www.ktmmobile.com/rate/rateList.do" title="요금제 소개 페이지 새창 이동" target="_blank">
                  <div class="main-benefit__info">
                    <i class="c-icon c-icon--benefit-rate" aria-hidden="true"></i>
                  </div>
                  <p>요금제 소개</p>
                </a>
              </li>
              <li class="main-benefit__item" data-aos="fade-up" data-aos-duration="800" data-aos-delay="200">
                <a href="https://www.ktmmobile.com/event/eventBoardList.do" title="이벤트 페이지 새창 이동" target="_blank">
                  <div class="main-benefit__info">
                    <i class="c-icon c-icon--benefit-event" aria-hidden="true"></i>
                  </div>
                  <p>이벤트</p>
                </a>
              </li>
              <li class="main-benefit__item" data-aos="fade-left" data-aos-duration="800" data-aos-delay="200">
                <a href="https://www.ktmmobile.com/requestReView/requestReView.do" title="사용후기 페이지 새창 이동" target="_blank">
                  <div class="main-benefit__info">
                    <i class="c-icon c-icon--benefit-review" aria-hidden="true"></i>
                  </div>
                  <p>사용후기</p>
                </a>
              </li>
            </ul>
          </section>

          <!-- <section class="content-section main about">
            <h4 class="content-section__title">대한민국 No.1 MVNO</h4>
            <ul class="main-about">
              <li class="main-about__item" data-aos="fade" data-aos-duration="500" data-aos-delay="0">
                 <p class="main-about__title">가입자 수</p>
                 <p class="main-about__text" ><span class="counter" data-target="170">170</span>만명</p>
              </li>
              <li class="main-about__item" data-aos="fade" data-aos-duration="500" data-aos-delay="1000">
                 <p class="main-about__title">연 매출</p>
                 <p class="main-about__text"><span class="counter" data-target="3">3</span>천억 원</p>
              </li>
              <li class="main-about__item" data-aos="fade" data-aos-duration="500" data-aos-delay="2000">
                 <p class="main-about__title">제휴 유통망</p>
                 <p class="main-about__text"><span class="counter" data-target="4.7">4.7</span>만 점포</p>
              </li>
              <li class="main-about__item" data-aos="fade" data-aos-duration="500" data-aos-delay="3000">
                 <p class="main-about__title">대리점 수</p>
                 <p class="main-about__text"><span class="counter" data-target="50">50</span>개 </p>
              </li>
            </ul>
          </section> -->
        </div>

        <div class="ly-content-wide main-business-content c-parallax">
          <div class="ly-content-wrap">
            <section class="content-section main">
              <ul class="main-business">
                <li>
                  <a class="main-business__item" href="/company/prodSvcHistory.do#mvnoBusiness" title="MVNO 사업 페이지 이동">
                      <div class="main-business__info">
                        <p class="main-business__info-title">MVNO 사업</p>
                        <p class="main-business__info-desc">MVNO업계 최대 가입자를 보유한 kt M mobile</p>
                        <div class="main-business__info-link">
                          <span>MVNO 사업 바로가기</span>
                              <i class="c-icon c-icon--arrow-default" aria-hidden="true"></i>
                            </div>
                      </div>
                      <ul class="main-business__list">
                        <li class="main-business__list-item">
                          <i class="c-icon c-icon--business-white-1" aria-hidden="true"></i>
                          <p class="main-business__list-text">합리적 요금제</p>
                        </li>
                        <li class="main-business__list-item">
                           <i class="c-icon c-icon--business-white-2" aria-hidden="true"></i>
                           <p class="main-business__list-text">차별화 서비스</p>
                        </li>
                        <li class="main-business__list-item">
                           <i class="c-icon c-icon--business-white-3" aria-hidden="true"></i>
                           <p class="main-business__list-text">+ 편의성 제공</p>
                        </li>
                      </ul>
                      <div class="main-business__info-link-mo">
                       <span>MVNO 사업 바로가기</span>
                     </div>
                  </a>
                </li>
                <li>
                  <a class="main-business__item black" href="/company/prodSvcHistory.do#moduleBusiness" title="통신 모듈 유통 사업 페이지 이동">
                        <ul class="main-business__list">
                          <li class="main-business__list-item">
                             <i class="c-icon c-icon--business-white-4" aria-hidden="true"></i>
                             <p class="main-business__list-text">SIM 유통</p>
                          </li>
                          <li class="main-business__list-item">
                             <i class="c-icon c-icon--business-white-5" aria-hidden="true"></i>
                             <p class="main-business__list-text">IoT 모듈 유통</p>
                          </li>
                        </ul>
                        <div class="main-business__info-link-mo">
                      <span>통신 모듈 유통 사업 바로가기</span>
                    </div>
                      <div class="main-business__info">
                            <p class="main-business__info-title">통신 모듈 유통 사업</p>
                            <p class="main-business__info-desc">kt M mobile은 KT통신모듈(USIM, eSIM, 셀롤러 모듈)을 고객과 고객사에게 안정적으로 공급합니다</p>
                            <div class="main-business__info-link">
                          <span>통신 모듈 유통 사업  바로가기</span>
                            <i class="c-icon c-icon--arrow-default" aria-hidden="true"></i>
                          </div>
                      </div>
                  </a>
                </li>
              </ul>
            </section>
          </div>
        </div>
        <div class="ly-content-wrap">
          <section class="content-section main introduce">
            <ul class="main-introduce">
              <li class="main-introduce__item" data-aos="fade-left" data-aos-duration="1300" data-aos-delay="200">
                <a href="/company/company.do" title="회사소개 페이지 이동">
                  <div class="main-introduce__info">
                    <p class="main-introduce__title">MVNO No.1 Company<i class="c-icon c-icon--arrow-bold" aria-hidden="true"></i></p>
                    <p class="main-introduce__text">고객의 삶을 더 가치있게!<br/> Beyond No.1 kt M mobile</p>
                  </div>
                  <img class="img-pc" src="/resources/company/images/introduce_01.png" alt="1번째 소개 이미지">
                  <img class="img-mo" src="/resources/company/images/introduce_mo_01.png" alt="1번째 소개 이미지">
                </a>
              </li>
              <li class="main-introduce__item" data-aos="fade-left" data-aos-duration="1300" data-aos-delay="400">
                <a href="/company/mediaList.do" title="비전 페이지 이동">
                  <div class="main-introduce__info">
                    <p class="main-introduce__title">홍보관<i class="c-icon c-icon--arrow-bold" aria-hidden="true"></i></p>
                    <p class="main-introduce__text">kt M mobile의 새로운 소식을<br/> 확인해보세요!</p>
                  </div>
                  <img class="img-pc" src="/resources/company/images/introduce_02.png" alt="2번째 소개 이미지">
                  <img class="img-mo" src="/resources/company/images/introduce_mo_02.png" alt="2번째 소개 이미지">
                </a>
              </li>
              <li class="main-introduce__item" data-aos="fade-left" data-aos-duration="1300" data-aos-delay="600">
                <a href="/company/recruit.do" title="인재상 페이지 이동">
                  <div class="main-introduce__info">
                    <p class="main-introduce__title">인재채용<i class="c-icon c-icon--arrow-bold" aria-hidden="true"></i></p>
                    <p class="main-introduce__text">kt M mobile과 함께 가치를 높여나갈<br/> 인재를 기다립니다.</p>
                  </div>
                  <img class="img-pc" src="/resources/company/images/introduce_03.png" alt="3번째 소개 이미지">
                  <img class="img-mo" src="/resources/company/images/introduce_mo_03.png" alt="3번째 소개 이미지">
                </a>
              </li>
            </ul>
          </section>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/company/layouts/companyFloatButton.jsp" %>
    <%@ include file="/WEB-INF/views/company/layouts/companyFooter.jsp" %>

<c:if test="${not empty popup}">
    <input type="hidden" id="isPopup" value="Y">
    <!-- 공지 팝업 -->
    <div class="dim-layer">
       <div class="dimBg"></div>
       <div id="noticeInfo" class="pop-layer" style="display:none;">
            <div class="pop-container">
                <div class="pop-conts">
                    <div class="popup_top">
                       <h4 class="popup_title_h4">${popup.popupSubject}</h4>
                   </div>
                   <div class="popup_content">
                        <c:out value="${popup.popupSbst}" escapeXml="false" />
                   </div>
                   <div class="popup_footer">
                       <button id="todayStop" class="today-stop-button">오늘 하루 그만보기</button>
                       <button class="notice_cancel">닫기</button>
                    </div>
               </div>
           </div>
           <button class="popup_cancel c-hidden">
               <i class="c-icon c-icon--gnb-all-close" aria-hidden="true"></i>
           </button>
        </div>
    </div>
</c:if>

    <!-- 딤처리 오버레이 -->
    <div class="overlay" id="overlayPc" onclick="toggleMenuAll()"></div>
    <div class="overlay" id="overlayMo" onclick="toggleMenu()"></div>
  </div>

  <%@ include file="/WEB-INF/views/company/layouts/commonJs.jsp" %>
  <script src="/resources/company/js/companyMain.js?version=250929"></script>

</body>
</html>