<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<c:set var="pageTitle" value="사업소개 | kt M mobile" />
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
        <p data-aos="fade-right" data-aos-duration="1800" data-aos-delay="300">사업소개</p>
        <img class="img-pc" src="/resources/company/images/sub_prodsvchistory_bg.png" alt="">
        <img class="img-mo" src="/resources/company/images/sub_prodsvchistory_bg_mo.png" alt="">
      </div>
    </div>
    <div class="sub-gnb sub-gnb--center">
      <button class="sub-gnb__item active" data-target="subGnb1">사업소개</button>
      <button class="sub-gnb__item" data-target="subGnb2">상품/서비스</button>
    </div>
    <div class="ly-content" id="main-content">
        <div class="ly-content-wrap">
          <section class="content-section" id="subGnb1" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="1000">
            <h4 class="content-section__title">사업소개</h4>
            <p class="content-section__desc">kt M mobile은 MVNO 사업과 eSIM 데이터 로밍 사업 및 통신 모듈 유통 사업을 운영하고 있는 대한민국 No.1 MVNO 사업자 입니다.<br/>언제나 고객을 생각하며, 고객을 가장 잘 아는 통신 파트너가 되기 위해 최선을 다하겠습니다.</p>
            <ul class="company-business">
              <li class="company-business__item">
                <p class="company-business__title" id="mvnoBusiness">MVNO 사업</p>
                <p class="company-business__desc">MVNO 업계 최다 가입자를 보유한 kt M mobile!<br/>KT의 통화·데이터 품질과 서비스를 그대로, 고객의 통신 생활에 가치를 더한 서비스를 제공하고 있습니다.<br/>합리적인 가격의 요금제, 멤버십, 결합/부가 서비스 등 더 특별한 가치를 제공하기 위해 노력하고 있습니다.</p>
                <ul class="company-business__list">
                  <li class="company-business__list-item">
                    <div class="company-business__list-img-wrap pc">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-1" aria-hidden="true"></i>
                      </div>
                    </div>
                    <div class="company-business__list-img-wrap mo">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-1" aria-hidden="true"></i>
                      </div>
                      <p class="company-business__list-title">합리적 요금제</p>
                    </div>
                    <div class="company-business__list-info">
                      <p class="company-business__list-title">합리적 요금제</p>
                      <p class="company-business__list-desc">고객님의 생활 패턴에 맞춘 다양한 통화·데이터 요금제가 준비되어 있습니다.<br/>통신비 절감은 물론, 다양한 제휴 혜택까지 누릴 수 있어 통신 생활이 더욱 즐거워집니다.</p>
                    </div>
                  </li>
                  <li class="company-business__list-item">
                    <div class="company-business__list-img-wrap pc">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-2" aria-hidden="true"></i>
                      </div>
                    </div>
                    <div class="company-business__list-img-wrap mo">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-2" aria-hidden="true"></i>
                      </div>
                      <p class="company-business__list-title">차별화 서비스</p>
                    </div>
                    <div class="company-business__list-info">
                      <p class="company-business__list-title">차별화 서비스</p>
                      <p class="company-business__list-desc">다양한 멤버십 서비스(쇼핑할인, 쿠폰, M스토어)와 kt 유선 인터넷, 무선 결합 서비스를 제공하고 있습니다.<br/>No.1 MVNO 사업자로서, 통신 분야를 넘어 새롭고 참신한 가치를 제공하기 위해 노력하고 있습니다.</p>
                    </div>
                  </li>
                  <li class="company-business__list-item">
                    <div class="company-business__list-img-wrap pc">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-3" aria-hidden="true"></i>
                      </div>
                    </div>
                    <div class="company-business__list-img-wrap mo">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-3" aria-hidden="true"></i>
                      </div>
                      <p class="company-business__list-title">+ 편의성 제공</p>
                    </div>
                    <div class="company-business__list-info">
                      <p class="company-business__list-title">+ 편의성 제공</p>
                      <p class="company-business__list-desc">온라인(ktmmobile.com, 오픈마켓)과 오프라인(편의점, 마트)에서 언제든 유심을 구매할 수 있고, 셀프개통으로 간편하게 통신서비스를 이용하실 수 있습니다.<br/>상담사 연결까지 기다림 없이 PC/모바일을 통해 요금제 변경, 부가서비스 신청, 실시간 사용량 조회가 가능합니다.<br/>고객님의 소중한 시간을 아끼기 위한 다양한 편의 서비스가 준비되어 있습니다.</p>
                    </div>
                  </li>
                </ul>
              </li>
              <li class="company-business__item">
                <p class="company-business__title" id="esimBusiness">eSIM 데이터 로밍 사업</p>
                <p class="company-business__desc">kt M mobile은 통신사가 직접 제공하는 eSIM 데이터 로밍 서비스 mobi를 통해 고객이 해외에서도 쉽고 안정적으로 모바일 데이터를 이용할 수 있는 환경을 제공합니다.</p>
                <ul class="company-business__list">
                  <li class="company-business__list-item">
                    <div class="company-business__list-img-wrap pc">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-6" aria-hidden="true"></i>
                      </div>
                    </div>
                    <div class="company-business__list-img-wrap mo">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-6" aria-hidden="true"></i>
                      </div>
                      <p class="company-business__list-title">즉시 개통 가능</p>
                    </div>
                    <div class="company-business__list-info">
                      <p class="company-business__list-title">즉시 개통 가능</p>
                      <p class="company-business__list-desc">앱 및 스마트스토어를 통해 eSIM을 바로 구매하고 설치할 수 있어 별도의 유심 교체가 필요 없습니다.<br/>로그인 없이 간단한 인증만으로 이용이 가능해, 출국 전 준비 시간을 크게 줄였습니다.<br/>여행 전·중 언제든 빠르게 데이터 이용을 시작할 수 있는 환경을 제공합니다.</p>
                    </div>
                  </li>
                  <li class="company-business__list-item">
                    <div class="company-business__list-img-wrap pc">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-7" aria-hidden="true"></i>
                      </div>
                    </div>
                    <div class="company-business__list-img-wrap mo">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-7" aria-hidden="true"></i>
                      </div>
                      <p class="company-business__list-title">출국 전 안심 개통 체크</p>
                    </div>
                    <div class="company-business__list-info">
                      <p class="company-business__list-title">출국 전 안심 개통 체크</p>
                      <p class="company-business__list-desc">출국 전 국내에서 현지와 동일한 환경으로 개통 상태를 사전에 확인할 수 있습니다.<br/>이를 통해 해외 도착 후 발생할 수 있는 연결 지연이나 설정 오류를 최소화했습니다.<br/>비행기에서 내리는 즉시 안정적인 데이터 사용 경험을 제공합니다.</p>
                    </div>
                  </li>
                  <li class="company-business__list-item">
                    <div class="company-business__list-img-wrap pc">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-8" aria-hidden="true"></i>
                      </div>
                    </div>
                    <div class="company-business__list-img-wrap mo">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-8" aria-hidden="true"></i>
                      </div>
                      <p class="company-business__list-title">365 연중무휴 고객지원 서비스</p>
                    </div>
                    <div class="company-business__list-info">
                      <p class="company-business__list-title">365 연중무휴 고객지원 서비스</p>
                      <p class="company-business__list-desc">데이터 사용 중 부족할 경우 앱에서 즉시 추가 충전이 가능해 사용 흐름이 끊기지 않습니다.<br/>선물하기 기능을 통해 동반 여행자와 데이터 준비를 간편하게 공유할 수 있습니다.<br/>또한 365일 고객 지원 서비스를 운영해 해외에서도 안정적인 서비스 이용을 돕습니다.</p>
                      <div class="company-business__mobi">
                   		<i class="c-icon c-icon--mobi_default" aria-hidden="true"></i>
                   		<span class="sr-only">mobi</span>
                      	<div class="company-business__mobi-link">
                   		  <a href="https://mobiesim.co.kr/" title="mobi 홈페이지 새창 이동" target="_blank">
			                <i class="c-icon c-icon--mobi" aria-hidden="true"></i>
			                <span>공식 홈페이지 바로가기</span>
   			              </a>
   			              <a href="https://apps.apple.com/kr/app/%EB%AA%A8%EB%B9%84-%ED%86%B5%EC%8B%A0%EC%82%AC%EA%B0%80-%EC%A0%9C%EA%B3%B5%ED%95%98%EB%8A%94-esim-%EB%A1%9C%EB%B0%8D/id6756096473" title="애플스토어 페이지 새창 이동" target="_blank">
			                <i class="c-icon c-icon--apple" aria-hidden="true"></i>
			                <span>Apple Store</span>
   			              </a>
   			              <a href="https://play.google.com/store/apps/details?id=com.kt.mobi&airbridge_deeplink=https://play.google.com/store/apps/details?id%3Dcom.kt.mobi&airbridge_referrer=airbridge%3Dtrue%26event_uuid%3Da6866432-e48c-4417-aab7-79525ab076b8%26client_id%3D7b0d9cd4-48d0-4f2e-8d4e-12b05ad7199f%26channel%3Dairbridge.websdk%26referrer_timestamp%3D1768524871996&pli=1" title="구글스토어 페이지 새창 이동" target="_blank">
			                <i class="c-icon c-icon--google" aria-hidden="true"></i>
			                <span>Google play</span>
   			              </a>
   			              <a href="https://smartstore.naver.com/mobi_store?nt_source=mobiweb&nt_medium=mainpage&nt_detail=260109" title="네이버스마트스토어 페이지 새창 이동" target="_blank">
			                <i class="c-icon c-icon--naver" aria-hidden="true"></i>
			                <span>네이버 스마트스토어</span>
   			              </a>
                      	</div>
                      </div>
                    </div>
                  </li>
                </ul>
              </li>
              <li class="company-business__item">
                <p class="company-business__title" id="moduleBusiness">통신 모듈 유통 사업</p>
                <p class="company-business__desc">kt M mobile은 KT 통신 모듈(USIM, eSIM, 셀룰러 모듈)을 고객 및 고객사에게 안정적으로 공급하고 있습니다.<br/>지속적인 혁신과 서비스 강화를 통해 고객과 함께 성장하는 최적의 통신 모듈 파트너로 성장해 나가겠습니다.</p>
                <ul class="company-business__list">
                  <li class="company-business__list-item">
                    <div class="company-business__list-img-wrap pc">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-4" aria-hidden="true"></i>
                      </div>
                    </div>
                    <div class="company-business__list-img-wrap mo">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-4" aria-hidden="true"></i>
                      </div>
                      <p class="company-business__list-title">SIM 유통<span>(Subscriber Identity Module)</span></p>
                    </div>
                    <div class="company-business__list-info">
                      <p class="company-business__list-title">SIM 유통<span>(Subscriber Identity Module)</span></p>
                      <p class="company-business__list-desc--type2">kt M mobile은 KT 통신망 서비스 제공에 필수적인 가입자 인증 모듈을 공급하고 있습니다.</p>
                      <ul class="text-list dot">
                        <li>USIM(Universal Subscriber Identity Module) : 이동통신 가입자를 인증하는 표준 모듈</li>
                        <li>eSIM(Embedded SIM) : 디바이스 내장형 가입자 인증 모듈로, 원격 프로파일 설정 지원</li>
                        <li>M2M USIM : IoT 및 산업용 기기 간 통신(Machine-to-Machine, M2M)을 위한 전용 USIM</li>
                      </ul>
                      <p class="company-business__list-desc--type2">고객의 일반 USIM, eSIM 뿐만 아니라, M2M 및 IoT 디바이스에 최적화된 USIM 솔루션을 제공하여 기업의 원활한 연결성과 운영 효율성을 지원합니다.</p>
                    </div>
                  </li>
                  <li class="company-business__list-item">
                    <div class="company-business__list-img-wrap pc">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-5" aria-hidden="true"></i>
                      </div>
                    </div>
                    <div class="company-business__list-img-wrap mo">
                      <div class="company-business__list-img">
                        <i class="c-icon c-icon--business-5" aria-hidden="true"></i>
                      </div>
                      <p class="company-business__list-title">IoT 모듈 유통<span>(IoT Module)</span></p>
                    </div>
                    <div class="company-business__list-info">
                      <p class="company-business__list-title">IoT 모듈 유통<span>(IoT Module)</span></p>
                      <p class="company-business__list-desc--type2">kt M mobile은 네트워크 환경에 맞는 통신 모듈을 공급하여, 다양한 산업에서 활용할 수 있도록 지원하고 있습니다.</p>
                      <ul class="text-list dot">
                        <li>AMI(Advanced Metering Infrastructure) : 스마트 미터 및 에너지 관리 시스템</li>
                        <li>스마트 시티 : 도시 인프라 모니터링 및 제어 시스템</li>
                        <li>원격 모니터링 : 산업용 장비 및 공정 데이터 실시간 모니터링</li>
                        <li>차량 관제 및 추적 : 물류, 운송, 공유 차량 관리 시스템</li>
                        <li>스마트 팩토리 : IoT 기반 공장 자동화 및 생산 공정 최적화</li>
                      </ul>
                      <p class="company-business__list-desc--type2">고객 맞춤형 솔루션을 제공하여 IoT 및 M2M 통신모듈 시장에서 차별화된 경쟁력을 확보해 나가겠습니다.</p>
                    </div>
                  </li>
                </ul>
              </li>
            </ul>
          </section>
          <section class="content-section" id="subGnb2" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="0">
            <h4 class="content-section__title">상품/서비스</h4>
            <ul class="prodsvc-history">
                <div class="vertical-line"></div>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2025</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2025_03.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2025_01.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>2</span></b>eSIM 데이터 로밍 mobi 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>잡지 구독 서비스 혜택 모아진 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>생활 제휴 요금제 5종 오대장 라인업 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>AI 음성 상담 서비스 엠봇 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>올리브영·다이소 제휴 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>3</span></b>eSIM 가입 고객 AI자동개통 서비스 지원</li>
                  </ul>
                </li>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2024</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2024_01.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2024_02.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>쇼핑 캐시적립 서비스 M쇼핑 할인 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>아무나 결합 서비스 5G 요금제 확대</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>MVNO 최초 폴더블폰 안심보험 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>MVNO 최초 편의점 제휴 CU 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>MVNO 최초 AI자동개통 서비스 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>MVNO 최초 온라인 직거래 보상 후후 안심 요금제 출시</li>
                  </ul>
                </li>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2023</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2023_01.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2023_02.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>MVNO 최초 멤버십 쇼핑몰 M스토어 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>웨이브 OTT 무제한 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>해외유심 브랜드 심크루 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>5G 중간요금제 확대</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>밀리의 서재 정기 구독 혜택 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 상담 예약 서비스 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 알뜰폰 멤버십 할인 M쿠폰 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 영화 관람 혜택 제공 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>4</span></b>10만원대 스마트 폴더폰 에이루트 폴더A1 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>4</span></b>MVNO 최초 아무나 결합 서비스 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>1</span></b>LTE 스마트폰 팬택폴더2 단독 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>1</span></b>스마트워치 전용 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>1</span></b>MVNO 최초 5G 중간요금제 론칭</li>
                  </ul>
                </li>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2022</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2022_01.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2022_02.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>0</span></b>애플 리셀러 에이샵 유심 판매 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>0</span></b>eSIM 브랜드 양심 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>시니어 대상 보이스피싱 예방 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>무약정 유심 이마트 판매 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>바로유심 서비스 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>롯데하이마트·윌리스 유심 판매 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>배달앱 제휴 요금제 2종 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>모두다 맘껏 안심 요금제 라인업 확대</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>3</span></b>왓챠 OTT 무제한 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>2</span></b>키즈 전용 무제한 요금제 키즈 안심 4종 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>1</span></b>만 65세 이상 전용 요금제 시니어 모두다 맘껏 출시</li>
                  </ul>
                </li>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2021</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2021_01.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2021_02.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>LTE 유심 이마트24 편의점 판매 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>LTE 유심 CU 편의점 판매 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>비대면 개통 서비스에 네이버 인증서 도입</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>월 1만원대 무제한 요금제 모두다 맘껏 7GB+ 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>네이버페이 제휴 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>지니뮤직 제휴 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>시니어 맞춤형 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>바로배송유심 서비스 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>5</span></b>기프티콘 제공 요금제 M 기프티 37 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>4</span></b>MVNO 최초 데이터 공유 결합 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>2</span></b>업계 최초 월 3만원대 200GB 제공</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>2</span></b>월 8천원대 어르신 전용 무제한 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>1</span></b>월 9천원대 무제한 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>1</span></b>구글플레이 제휴 요금제 출시</li>
                  </ul>
                </li>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2020</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2020_01.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2020_02.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>1</span></b>보험료 지원 M케어 서비스 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>0</span></b>저가형 무제한 요금제 모두다 맘껏 안심 4종 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>0</span></b>어르신 전용 통신 서비스 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>월 2만원대 무제한 요금제 2종 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>LTE 후불 유심 홈플러스 판매 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>청소년·실버 세대 특화폰 LG폴더2S 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>OTT 서비스 구독 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>5</span></b>LTE 후불 유심 롯데하이마트 쇼핑몰 판매 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>4</span></b>MVNO 최초 데이터 공유 요금제 데이터 함께 쓰기 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>4</span></b>월 1만원대 무제한 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>4</span></b>LTE 후불 유심 다이소 매장 판매 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>2</span></b>업계 최초 5G 무약정 유심 편의점 판매 개시</li>
                  </ul>
                </li>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2019</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2019_01.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2019_02.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>2</span></b>5G 전용 요금제 2종 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>1</span></b>SKY 3G 폴더폰 단독 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>데이터 쉐어링 서비스 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>지표 관리 로봇 M-DNA 도입</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>통신업계 최초 생활가전 렌탈 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>3</span></b>세븐일레븐 편의점 후불 유심 판매 개시</li>
                  </ul>
                </li>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2018</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2018_01.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2018_02.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>2</span></b>온라인 직영 다이렉트몰 개편</li>
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>1</span></b>LTE 폴더폰 라디오 청춘2 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>1</span></b>육군 장병 전용 더캠프 무약정 유심 요금제 3종 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>1</span><span>0</span></b>온라인 보험 가입 할인 프로모션</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>유무선 동시가입 할인 프로모션</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>상해보험 무료 제공 프로모션</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>3G 자급제 효도폰 라디오 청춘 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>고객 맞춤형 FIT 12 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>MVNO 최초 ATM 즉시 개통 서비스 도입</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>MVNO 최초 셀프개통 서비스 도입·운영</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>평생할인 제공 국민통신요금제 7종 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>미니스톱 편의점 LTE 유심 판매 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>5</span></b>데이터 무제한 LTE 요금제 4종 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 M쇼핑 오픈</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>2</span></b>씨스페이스 편의점 후불 유심 판매 개시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>1</span></b>엘포인트 적립형 L.POINT 요금제 3종 출시</li>
                  </ul>
                </li>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2017</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2017_01.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2017_02.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>4</span></b>라이프케어 연계 M 라이프 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>4</span></b>상해보험 지원 생활안심 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>2</span></b>롯데카드 제휴 통신비 12만원 캐시백 프로모션</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>1</span></b>우리카드 제휴 통신비 1만원 할인 프로모션</li>
                  </ul>
                </li>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2016</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2016_01.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2016_02.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>시니어 대상 안심 차단 요금제 및 부모님 전담 고객센터 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>6</span></b>교통비 적립형 M 티머니 요금제 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>5</span></b>MVNO 최초 항공사 마일리지 적립형 요금제 출시</li>
                  </ul>
                </li>
                <li class="prodsvc-history__item" data-aos="fade" data-aos-duration="2400" data-aos-delay="500">
                  <div class="prodsvc-history__date">
                    <p>2015</p>
                  </div>
                  <div class="prodsvc-history__img">
                    <img src="/resources/company/images/prodsvchistory_2015_01.png" alt="">
                    <img src="/resources/company/images/prodsvchistory_2015_02.png" alt="">
                  </div>
                  <ul class="prodsvc-history__list">
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>LTE 선불요금제 M LTE 베이직 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>착한텔레콤 제휴 중고폰 쇼핑몰 론칭</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>9</span></b>국내 최저가 M 음성무제한 요금제 3종 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>8</span></b>청소년 전용 LTE 요금제 M 청소년 LTE 19/24 출시</li>
                    <li class="prodsvc-history__list-item"><b><span>0</span><span>7</span></b>국내 최초 1만원대 LTE 1GB 제공 요금제 출시</li>
                  </ul>
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