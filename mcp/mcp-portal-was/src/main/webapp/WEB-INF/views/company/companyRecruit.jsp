<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<c:set var="pageTitle" value="인재채용 | kt M mobile" />
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
        <p data-aos="fade-right" data-aos-duration="1800" data-aos-delay="300">인재채용</p>
        <img class="img-pc" src="/resources/company/images/sub_recruit_bg.png" alt="">
        <img class="img-mo" src="/resources/company/images/sub_recruit_bg_mo.png" alt="">
      </div>
    </div>
    <div class="sub-gnb">
      <button class="sub-gnb__item active" data-target="subGnb1">인재상</button>
      <button class="sub-gnb__item" data-target="subGnb2">인사제도</button>
      <button class="sub-gnb__item" data-target="subGnb3">복리후생</button>
      <a class="sub-gnb__item" href="https://recruit.kt.com" title="지원하러 가기 페이지 새창 이동" target="_blank">지원하러 가기</a>
    </div>
    <div class="ly-content" id="main-content">
        <div class="ly-content-wrap">
          <section class="content-section" id="subGnb1" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="1000">
            <h4 class="content-section__title">인재상</h4>
            <div class="recruit-elite-wrap">
              <img class="recruit-elite-logo" src="/resources/company/images/recruit_circle_logo.png" alt="">
              <ul class="recruit-elite-list">
                  <li class="recruit-elite-item">
                      <div class="recruit-elite-img">
                        <i class="c-icon c-icon--elite-1" aria-hidden="true"></i>
                      </div>
                      <div class="recruit-elite-text-wrap">
                        <p class="recruit-elite-title">끊임없이 도전하는 인재</p>
                        <ul class="recruit-elite-text">
                            <li>시련과 역경에 굴하지 않고 목표를 향해 끊임없이<br/> 도전하여 최고의 수준을 달성한다.</li>
                            <li>변화와 혁신을 선도하여 차별화된 서비스를<br/> 구현한다.</li>
                        </ul>
                      </div>
                  </li>
                  <li class="recruit-elite-item">
                      <div class="recruit-elite-img">
                        <i class="c-icon c-icon--elite-2" aria-hidden="true"></i>
                      </div>
                      <div class="recruit-elite-text-wrap">
                        <p class="recruit-elite-title">벽 없이 소통하는 인재</p>
                        <ul class="recruit-elite-text">
                            <li>동료 간 적극적으로 소통하여 서로의 성장과 발전을<br/> 위해 끊임없이 노력한다.</li>
                            <li>kt M모바일의 성공을 위해 상호 협력하여 시너지를<br/> 창출한다.</li>
                        </ul>
                      </div>
                  </li>
                  <li class="recruit-elite-item">
                      <div class="recruit-elite-img">
                        <i class="c-icon c-icon--elite-3" aria-hidden="true"></i>
                      </div>
                      <div class="recruit-elite-text-wrap">
                        <p class="recruit-elite-title">고객을 존중하는 인재</p>
                        <ul class="recruit-elite-text">
                            <li>모든 업무 수행에 있어 고객의 이익과 만족을 먼저<br/> 생각한다.</li>
                            <li>고객을 존중하고, 고객과의 약속을 반드시 지킨다.</li>
                        </ul>
                      </div>
                  </li>
                  <li class="recruit-elite-item">
                      <div class="recruit-elite-img">
                        <i class="c-icon c-icon--elite-4" aria-hidden="true"></i>
                      </div>
                      <div class="recruit-elite-text-wrap">
                        <p class="recruit-elite-title">기본과 원칙을 지키는 인재</p>
                        <ul class="recruit-elite-text">
                            <li>회사의 주인은 나라는 생각으로 자부심을 갖고<br/> 업무를 수행한다.</li>
                            <li>윤리적 판단에 따라 행동하며 결과에 대해 책임을<br/> 진다.</li>
                        </ul>
                      </div>
                  </li>
              </ul>
            </div>
          </section>
        </div>
        <div class="ly-content-wide">
          <div class="ly-content-wrap">
            <section class="content-section hr" id="subGnb2">
              <h4 class="content-section__title">인사제도</h4>
              <ul class="recruit-hr-list">
                <li class="recruit-hr-item">
                  <img class="img-pc" src="/resources/company/images/recruit_hr_01.png" alt="">
                  <p class="recruit-hr-title">승진제도</p>
                  <img class="img-mo" src="/resources/company/images/recruit_hr_mo_01.png" alt="">
                  <p class="recruit-hr-text">개인성과/역량 및 대내외 활동들을 매년 마일리지로 환산 부여하며, 직급별 일정 기간의 누적 마일리지를 기반으로 승진자를 선정, 예측 가능하고 투명한 승진을 시행하고 있습니다. 또한, 성과가 뛰어난 직원에게는 발탁 승진의 기회를 부여하여 우수한 인재가 회사 내에서 빠르게 성장할 수 있는 승진제도를 운영하고 있습니다.</p>
                </li>
                <li class="recruit-hr-item">
                  <img class="img-pc" src="/resources/company/images/recruit_hr_02.png" alt="">
                  <p class="recruit-hr-title">역량강화 교육</p>
                  <img class="img-mo" src="/resources/company/images/recruit_hr_mo_02.png" alt="">
                  <p class="recruit-hr-text">직원들이 업무를 수행하는 데 공통적으로 필요한 역량은 물론 전문가로 성장할 수 있도록 온라인 및 오프라인을 통한 역량강화 교육 및 향후 회사를 이끌어갈 리더로 성장하기 위한 체계적인 리더십 강화 교육 과정을 운영하고 있습니다.</p>
                </li>
              </ul>
            </section>
          </div>
        </div>
        <div class="ly-content-wrap">
          <section class="content-section" id="subGnb3" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="0">
            <h4 class="content-section__title pc">복리후생</h4>
            <ul class="recruit-benefit">
              <li class="recruit-benefit__item">
                <div class="recruit-benefit__title">
                    <p>자유로운 휴일·휴가</p>
                </div>
                <div class="recruit-benefit__list-wrap">
                  <ul class="recruit-benefit__list">
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_01.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">M-Day</p>
                        <p class="recruit-benefit__list-desc">월 1회 조기 퇴근</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_02.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">기념일 Day</p>
                        <p class="recruit-benefit__list-desc">생일 연차<br/>결혼(기혼) / 입사(미혼) 기념일 조기퇴근</p>
                      </div>
                    </li>
                  </ul>
                </div>
              </li>
              <li class="recruit-benefit__item">
                <div class="recruit-benefit__title">
                    <p>업무 몰입 지원</p>
                </div>
                <div class="recruit-benefit__list-wrap">
                  <ul class="recruit-benefit__list">
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_03.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">PC OFF제</p>
                        <p class="recruit-benefit__list-desc">정시퇴근 보장을 위한 자동 PC 차단</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_21.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">자격증 취득 지원</p>
                        <p class="recruit-benefit__list-desc">직무 역량 강화 및 직원 성장 지원</p>
                      </div>
                    </li>
                  </ul>
                </div>
              </li>
              <li class="recruit-benefit__item">
                <div class="recruit-benefit__title">
                    <p>리프레시 지원</p>
                </div>
                <div class="recruit-benefit__list-wrap">
                  <ul class="recruit-benefit__list">
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_06.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">여행포인트</p>
                        <p class="recruit-benefit__list-desc">매년 여행 포인트 지급</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_07.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">사내 동호회</p>
                        <p class="recruit-benefit__list-desc">사내 동호회 활동비용 지원  </p>
                      </div>
                    </li>
                  </ul>
                </div>
              </li>
              <li class="recruit-benefit__item">
                <div class="recruit-benefit__title">
                    <p>일상 속 지원</p>
                </div>
                <div class="recruit-benefit__list-wrap">
                  <ul class="recruit-benefit__list">
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_08.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">통신비</p>
                        <p class="recruit-benefit__list-desc">KT / kt M모바일 통신비 지원</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_09.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">복지포인트</p>
                        <p class="recruit-benefit__list-desc">여가 생활을 위한 복지 포인트 지급</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_10.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">경조금</p>
                        <p class="recruit-benefit__list-desc">기쁨과 슬픔을 함께 지원</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_11.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">대출</p>
                        <p class="recruit-benefit__list-desc">주택마련/임차/생활안정/신생아출산/<br/>입사 전 본인 학자금을 낮은 이자율로 지원</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_12.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">보육수당</p>
                        <p class="recruit-benefit__list-desc">만 0세~만 18세 미만까지 매월 지원</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_19.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">자기계발</p>
                        <p class="recruit-benefit__list-desc">자기계발 비용 지원</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_20.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">육아지원</p>
                        <p class="recruit-benefit__list-desc">출산 축하, 산후조리 비용 지원</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_22.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">자녀 학자금 지원</p>
                        <p class="recruit-benefit__list-desc">자녀 초~고등학교 입학축하금 지원<br/>자녀 대학교 학자금 지원</p>
                        </div>
                    </li>
					<li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_23.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">해외 eSIM 로밍 지원</p>
                        <p class="recruit-benefit__list-desc">임직원 해외여행 시 eSIM 로밍 지원</p>
                      </div>
                    </li>
                  </ul>
                </div>
              </li>
              <li class="recruit-benefit__item">
                <div class="recruit-benefit__title">
                    <p>건강 증진 지원</p>
                </div>
                <div class="recruit-benefit__list-wrap">
                  <ul class="recruit-benefit__list">
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_14.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">의료비</p>
                        <p class="recruit-benefit__list-desc">본인과 가족의 의료비 지원</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_15.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">건강검진</p>
                        <p class="recruit-benefit__list-desc">본인과 가족의 건강검진 지원</p>
                      </div>
                    </li>
                  </ul>
                </div>
              </li>
              <li class="recruit-benefit__item">
                <div class="recruit-benefit__title">
                    <p>축하금 지급</p>
                </div>
                <div class="recruit-benefit__list-wrap">
                  <ul class="recruit-benefit__list">
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_17.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">안식년 지원</p>
                        <p class="recruit-benefit__list-desc">장기 근속 시 보로금 및 휴가 지원</p>
                      </div>
                    </li>
                    <li class="recruit-benefit__list-item">
                      <img src="/resources/company/img/recruit_welfare_18.png" alt="">
                      <div>
                        <p class="recruit-benefit__list-title">Welcome Bonus</p>
                        <p class="recruit-benefit__list-desc">신입사원 수습 종료 후 축하금 지급</p>
                      </div>
                    </li>
                  </ul>
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