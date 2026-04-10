<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<c:set var="pageTitle" value="경영철학 | kt M mobile" />
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
        <p data-aos="fade-right" data-aos-duration="1800" data-aos-delay="300">경영철학</p>
        <img class="img-pc" src="/resources/company/images/sub_mgtphilosophy_bg.png" alt="">
        <img class="img-mo" src="/resources/company/images/sub_mgtphilosophy_bg_mo.png" alt="">
      </div>
    </div>
    <div class="sub-gnb sub-gnb--center">
      <button class="sub-gnb__item active" data-target="subGnb1">윤리경영</button>
      <button class="sub-gnb__item" data-target="subGnb2">고객만족경영</button>
      <button class="sub-gnb__item" data-target="subGnb3">CI</button>
    </div>
    <div class="ly-content" id="main-content">
        <div class="ly-content-wrap">
          <section class="content-section ethos" id="subGnb1" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="1000">
            <h4 class="content-section__title">윤리경영</h4>
            <p class="content-section__desc">우리는 디지털 혁신 파트너 kt M mobile을 만들기 위해 올바른 의사결정과 윤리적 판단으로 회사의 미래를 도모한다.<br />이를 위해 ‘고객중심, 준법경영, 기본충실, 주인정신, 사회적 책임’을 모든 kt M mobile인이 공유하고,<br />지켜야 할 윤리경영 5대 행동원칙으로 삼고, 이를 적극 실천할 것을 다짐한다.</p>
            <div class="tabs" role="tablist" aria-label="tabs">
                <button class="tab-button" role="tab" aria-selected="true" aria-controls="tab1" id="tab1-button">윤리경영원칙</button>
                <button class="tab-button" role="tab" aria-selected="false" aria-controls="tab2" id="tab2-button">윤리상담센터</button>
            </div>
            <div class="tab-content-wrap">
              <div class="tab-content" id="tab1" role="tabpanel" aria-labelledby="tab1-button">
                <ul class="company-ethos__list">
                  <li class="company-ethos__item">
                    <div class="company-ethos__item-box"><span>1</span></div>
                    <div class="company-ethos__item-info">
                      <p class="company-ethos__item-title"><span class="mo">[원칙1] </span>고객 중심으로 사고하고 행동한다.</p>
                      <ul class="text-list">
                        <li>1-1. 고객의 가치를 존중하고 정보를 철저히 보호한다.</li>
                        <li>1-2. 고객의 삶의 변화를 이끌 수 있는 차별화된 가치를 끊임없이 창출한다.</li>
                      </ul>
                    </div>
                  </li>
                  <li class="company-ethos__item">
                    <div class="company-ethos__item-box"><span>2</span></div>
                    <div class="company-ethos__item-info">
                      <p class="company-ethos__item-title"><span class="mo">[원칙2] </span>각종 법령과 규정을 엄격히 준수한다.</p>
                      <ul class="text-list">
                        <li>2-1. 법과 윤리에 따라 행동하며 컴플라이언스 의무를 다한다.</li>
                        <li>2-2. 부패방지 행동강령을 숙지하고 준수한다.</li>
                        <li>2-3. 경영의 투명성을 확보ㆍ유지하며 회사의 비밀은 철저히 보호한다.</li>
                      </ul>
                    </div>
                  </li>
                  <li class="company-ethos__item">
                    <div class="company-ethos__item-box"><span>3</span></div>
                    <div class="company-ethos__item-info">
                      <p class="company-ethos__item-title"><span class="mo">[원칙3] </span>기본과 원칙에 충실한다.</p>
                      <ul class="text-list">
                        <li>3-1. 회사 전체 이익 관점에서 합리적, 객관적으로 판단하고 책임 있게 행동한다.</li>
                        <li>3-2. 공과 사를 엄격히 구분하며 건전한 조직문화 조성에 앞장선다.</li>
                        <li>3-3. 회사의 올바른 성장을 위해 지속 가능한 성과를 추구하고 공정한 보고 문화를 확립한다.</li>
                      </ul>
                    </div>
                  </li>
                  <li class="company-ethos__item">
                    <div class="company-ethos__item-box"><span>4</span></div>
                    <div class="company-ethos__item-info">
                      <p class="company-ethos__item-title"><span class="mo">[원칙4] </span>스스로 회사와 내가 하나라는 주인정신을 갖는다.</p>
                      <ul class="text-list">
                        <li>4-1. 실패를 두려워하지 않고 항상 최고에 도전한다.</li>
                        <li>4-2. 고객의 문제를 가장 잘 해결할 수 있도록 역량을 키우고 전문성을 함양한다.</li>
                        <li>4-3. 모든 구성원이 서로 존중하고 하나로 화합하여 함께 목표 달성에 앞장선다.</li>
                      </ul>
                    </div>
                  </li>
                  <li class="company-ethos__item">
                    <div class="company-ethos__item-box"><span>5</span></div>
                    <div class="company-ethos__item-info">
                      <p class="company-ethos__item-title"><span class="mo">[원칙5] </span>AICT 선도 기업으로서 사회적 책임과 의무를 다한다.</p>
                      <ul class="text-list">
                        <li>5-1. 주주의 권리와 이익을 보호하고 임직원의 「삶의 질」 향상을 위해 노력한다.</li>
                        <li>5-2. 환경ㆍ안전ㆍ인권을 중시하고 회사가 가진 역량을 활용하여 ESG 경영을 적극 추진한다.</li>
                        <li>5-3. 사회공헌 활동에 앞장서고 사업 파트너와 동반성장의 관계를 구축한다.</li>
                      </ul>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="tab-content" id="tab2" role="tabpanel" aria-labelledby="tab2-button" hidden>
               <ul class="company-center__list">
                  <li class="company-center__item">
                    <p class="company-center__title">신고대상</p>
                    <ul class="text-list dot">
                      <li>업무와 관련 금품수수, 향응 등을 요구하거나 제공받는 행위</li>
                      <li>직위를 이용하여 부당한 이득을 얻거나 손실을 끼친 행위</li>
                      <li>본인 또는 타인의 공정한 직무수행을 저해하는 행위</li>
                      <li>기타 임직원 행동강령 및 행동규범을 위반하여 부당한 이득을 취하는 행위</li>
                      <li>임직원이 본의 아니게 금지된 금품 등을 수수하였으나 돌려줄 방법이 없는 경우</li>
                    </ul>
                  </li>
                  <li class="company-center__item">
                    <p class="company-center__title">신고처리</p>
                    <ul class="text-list dot">
                      <li>부패행위 신고자, 협조자 및 자진신고자에 대한 비밀 및 신분보장</li>
                      <li>신고자의 동의 없는 신분공개 및 암시 행위 금지</li>
                    </ul>
                  </li>
                  <li class="company-center__item">
                    <p class="company-center__title">처리절차</p>
                    <ul class="text-list dot">
                      <li>접수 후 사실 확인과 현장조사를 거쳐 전화 또는 이메일로 결과 통보</li>
                    </ul>
                  </li>
                  <li class="company-center__item">
                    <p class="company-center__title">윤리상담센터 접수 채널</p>
                    <div class="ly-table">
                      <table>
                        <caption class="c-hidden">웹(WEB), 전화, 팩스, 서신, 이메일의 정보로 구성된 윤리상담센터 접수 채널 정보</caption>
                        <colgroup>
                          <col>
                          <col>
                        </colgroup>
                        <tbody>
                          <tr>
                            <th scope="row">웹(WEB)</th>
                            <td>홈페이지 하단 윤리경영 > 윤리상담센터 > 상담하기</td>
                          </tr>
                          <tr>
                            <th scope="row">전화</th>
                            <td>010-3315-8755</td>
                          </tr>
                          <tr>
                            <th scope="row">팩스</th>
                            <td>02-559-8792</td>
                          </tr>
                          <tr>
                            <th scope="row">서신</th>
                            <td>서울 강남구 테헤란로 420. kt선릉타워 West 13층(대치동) 주식회사 케이티엠모바일 감사실</td>
                          </tr>
                          <tr>
                            <th scope="row">이메일</th>
                            <td>ktm-ethics@kt.com</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <div class="company-center__button">
                      <button class="btn--red" id="reportBtn">상담하기</button>
                      <button id="reportSearchBtn">접수결과 확인</button>
                      <button class="btn--red mo" id="reportMoBtn">상담하기</button>
                      <button class="mo" id="reportSearchMoBtn">접수결과 확인</button>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </section>
          <section class="content-section" id="subGnb2" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="0">
            <h4 class="content-section__title">고객만족경영</h4>
            <p class="content-section__desc">kt M mobile은 핵심 가치인 고객 최우선 경영을 위한 고객 경험 혁신 및 고객 권익 보호를 위해 최선을 다 하고 있습니다.</p>
            <ul class="company-cs">
              <li class="company-cs__item">
                <p class="company-cs__title">CS 비전</p>
                <ul class="text-list">
                  <li>No.1 통신 파트너로서 고객의 통신 생활에 필요한 다양한 고객 맞춤형 고품질 서비스 제공</li>
                </ul>
              </li>
              <li class="company-cs__item">
                <p class="company-cs__title">고객 최우선 경영 추진 체계 </p>
                <ul class="text-list">
                  <li>1. 고객 우선 : 고객 니즈 중심 고객을 향한 진심을 담아 더 편리하고, 더 쉽고, 빠르게 고객의 불편함을 해소</li>
                  <li>2. 품질 우선 : 고객이 원하는 그 이상의 고품질 서비스 제공</li>
                  <li>3. 경험 혁신 : AI기반 다양한 고객 경험 혁신을 통한 새로운 고객 가치 창출</li>
                </ul>
              </li>
              <li class="company-cs__item">
                <p class="company-cs__title">고객만족 실천헌장 </p>
                <p class="company-cs__desc">우리의 핵심 가치인 고객 우선 정신을 바탕으로 고객의 입장에서 생각하고 행동하며, 모든 고객이 만족 할 수 있도록 다음과 같이 5대 실천헌장을 지켜나가겠습니다. </p>
                <div class="company-cs__text-wrap">
                 <ul class="text-list">
                   <li><span>하나</span>, 우리는 서비스 실명제를 통하여 고객과의 약속을 반드시 지키겠습니다.</li>
                   <li><span>하나</span>, 우리는 고객이 원하는 것을 신속하게 파악하고 완벽하게 처리하겠습니다.</li>
                   <li><span>하나</span>, 우리는 고객의 참여와 평가를 통하여 고객이 원하는 상품을 만들겠습니다.</li>
                   <li><span>하나</span>, 우리는 다양한 방식으로 365일 24시간 쉼 없이 고객의 소리를 듣겠습니다.</li>
                   <li><span>하나</span>, 우리는 한결같은 마음으로 소외되고 외진 곳 까지 달려가겠습니다.</li>
                 </ul>
                </div>
              </li>
            </ul>
          </section>
        </div>
        <div class="ly-content-wide">
          <div class="ly-content-wrap">
            <section class="content-section" id="subGnb3">
              <h4 class="content-section__title">CI</h4>
              <ul class="company-ci">
                <li class="company-ci__item" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="0">
                  <div class="company-ci__title">
                    <p>기본형</p>
                  </div>
                  <ul class="company-ci__default" >
                    <li class="company-ci__default-item">
                      <img class="img-pc" src="/resources/company/images/company_ci_01.png" alt="kt M mobile 로고">
                      <img class="img-pc" src="/resources/company/images/company_ci_02.png" alt="kt M mobile 로고">
                      <img class="img-mo" src="/resources/company/images/company_ci_mo_01.png" alt="kt M mobile 로고">
                      <img class="img-mo" src="/resources/company/images/company_ci_mo_02.png" alt="kt M mobile 로고">
                    </li>
                    <li class="company-ci__default-item">
                      <div class="company-ci__default-info">
                        <p class="company-ci__default-title"><span>소문자,</span> 그리고 휘날림</p>
                        <p class="company-ci__default-desc">고객 눈높이로 친근하게 다가가기 위해 대문자(KT)에서 소문자(kt)로 변화했습니다. 이는 고객중심의 기업이 되겠다는 다짐의 표현입니다. 휘날리는 깃발의 모습은 세계로 뻗어 나가는 글로벌 브랜드의 위상을 상징합니다.</p>
                      </div>
                      <div class="company-ci__default-info">
                        <p class="company-ci__default-title"><span>감성과 신뢰</span>를 담은 컬러</p>
                        <p class="company-ci__default-desc">레드(Red)는 열정과 혁신, 그리고 고객을 향한 따뜻한 감성을 의미하며, 블랙(Black) 은 신뢰를 상징합니다. 이 두 컬러 간 조화를 통해 kt가 지향하는 가치와 변화의 스토리 를 만들어 나가고자 합니다</p>
                      </div>
                    </li>
                  </ul>
                </li>
                <li class="company-ci__item" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="0">
                  <div class="company-ci__title">
                    <p>단색형</p>
                  </div>
                  <ul class="company-ci__solid">
                    <li class="company-ci__solid-item">
                      <img class="img-pc" src="/resources/company/images/company_ci_03.png" alt="kt M mobile 로고">
                      <img class="img-mo" src="/resources/company/images/company_ci_mo_03.png" alt="kt M mobile 로고">
                    </li>
                    <li class="company-ci__solid-item">
                      <img class="img-pc" src="/resources/company/images/company_ci_04.png" alt="kt M mobile 로고">
                      <img class="img-mo" src="/resources/company/images/company_ci_mo_04.png" alt="kt M mobile 로고">
                    </li>
                    <li class="company-ci__solid-item">
                      <img class="img-pc" src="/resources/company/images/company_ci_05.png" alt="kt M mobile 로고">
                      <img class="img-mo" src="/resources/company/images/company_ci_mo_05.png" alt="kt M mobile 로고">
                    </li>
                    <li class="company-ci__solid-item mo">
                      <img class="img-mo" src="/resources/company/images/company_ci_mo_06.png" alt="kt M mobile 로고">
                    </li>
                  </ul>
                </li>
                <li class="company-ci__item" data-aos="fade-up" data-aos-duration="1800" data-aos-delay="0">
                  <div class="company-ci__title">
                    <p>색상</p>
                  </div>
                  <ul class="company-ci__color">
                    <li class="company-ci__color-item">
                      <div class="company-ci__color-box"></div>
                      <div class="company-ci__color-info">
                        <p class="company-ci__color-title">KT CI Red</p>
                        <p class="company-ci__color-value">C0  M100  Y100  K0<br/>R255  G0  B0</p>
                        <p class="company-ci__color-pantone">#FF0000, Pantone 1795C</p>
                      </div>
                    </li>
                    <li class="company-ci__color-item">
                      <div class="company-ci__color-box"></div>
                      <div class="company-ci__color-info">
                        <p class="company-ci__color-title">KT Black</p>
                        <p class="company-ci__color-value">C0  M0  Y0  K100<br/>R0  G0  B0</p>
                        <p class="company-ci__color-pantone">#000000, Pantone Black C</p>
                      </div>
                    </li>
                    <li class="company-ci__color-item">
                      <div class="company-ci__color-box"></div>
                      <div class="company-ci__color-info">
                        <p class="company-ci__color-title">D.Gray</p>
                        <p class="company-ci__color-value">C0  M0  Y0  K85<br/>R76  G76  B78</p>
                        <p class="company-ci__color-pantone">#4C4C4E, Pantone 425C</p>
                      </div>
                    </li>
                    <li class="company-ci__color-item">
                      <div class="company-ci__color-box"></div>
                      <div class="company-ci__color-info">
                        <p class="company-ci__color-title">L.Gray</p>
                        <p class="company-ci__color-value">C0  M0  Y0  K40<br/>R162  G164  B163</p>
                        <p class="company-ci__color-pantone">#A2A4A3, Pantone 421C</p>
                      </div>
                    </li>
                  </ul>
                </li>
              </ul>
            </section>
          </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/company/layouts/companyFloatButton.jsp" %>
    <%@ include file="/WEB-INF/views/company/layouts/companyFooter.jsp" %>

    <!-- 윤리경영 팝업  -->
    <div class="dim-layer center">
      <div class="dimBg"></div>

      <!--  제보하기 안내 S-->
      <div id="reportFormInfo" class="pop-layer" style="display:none;" role="dialog" aria-modal="true" aria-labelledby="reportFormInfomodalTitle" aria-describedby="reportFormInfomodalDesc" tabindex="-1">
        <div class="pop-container">
          <div class="pop-conts">
            <div class="popup_top">
              <h4 class="popup_title_h4" id="reportFormInfomodalTitle">윤리상담 유의사항</h4>
            </div>
            <div class="popup_content" id="reportFormInfomodalDesc">
              <ul class="ethos-notice">
                <li class="ethos-notice__item">
                  <p class="ethos-notice__title">제보자(성명, 연락처) 및 제보 대상자(소속, 성명 등)를 기재하여 주십시오.</p>
                  <ul class="text-list dot">
                    <li>실명 제보가 원칙이나 익명제보도 구체성과 근거가 명확한 경우에 한하여 조사가 가능하며, 제보자의 신분과 제보내용 및 증거는 대외비로 철저히 보안을 유지하고 있습니다.</li>
                  </ul>
                </li>
                <li class="ethos-notice__item">
                  <p class="ethos-notice__title">구체적인 사실을 입력하여 주십시오.</p>
                  <ul class="text-list dot">
                    <li>금품/향응수수, 횡령, 부당 압력 행사, 정보 유출, 특혜제공 등 직무관련 비위행위에 대하여 <span class="u-co-red">사실에 근거한 내용을 가급적 구체적으로(관련된 직원이 누구인지, 언제 발생한 일인지, 어느 부서 또는 어느 지역 인지 등) 작성</span>하여 주실것을 부탁드리며, 관련 근거(사진, 문서, 녹취, 메시지 및 증빙자료)를 첨부하여  주시면 조사에 많은 도움이 됩니다. (사생활, 근거없는 소문, 허위 추정 및 타인 비방 목적의 음해성 제보는 조사를 진행하지 않을 수 있음)</li>
                  </ul>
                </li>
              </ul>
              <div class="company-center__button">
                <button class="btn--red" id="reportFormBtn">확인</button>
              </div>
            </div>
            <div class="popup_content__after"></div>
          </div>
        </div>
        <button class="popup_cancel" id="closeModal" aria-label="닫기">
          <i class="c-icon c-icon--gnb-all-close" aria-hidden="true"></i>
        </button>
      </div>

      <!-- 제보하기 입력 폼 S -->
      <div id="reportForm" class="pop-layer" style="display:none;" role="dialog" aria-modal="true" aria-labelledby="reportFormmodalTitle" aria-describedby="reportFormmodalDesc" tabindex="-1">
        <div class="pop-container">
          <div class="pop-conts">
            <div class="popup_top">
              <h4 class="popup_title_h4" id="reportFormmodalTitle">윤리상담센터</h4>
            </div>
            <div class="popup_content" id="reportFormmodalDesc">
              <div class="ethos-form__text-wrap">
                <ul class="text-list dot">
                  <li>실명 제보가 원칙이나 익명제보도 구체성과 근거가 명확한 경우에 한하여 조사가 가능하며, <b>제보자의 신분과 제보내용 및 증거는 대외비로 철저히 보안</b>을 유지하고 있습니다.</li>
                  <li><b>사실에 근거한 내용을 가급적 구체적으로</b>(관련된 직원명, 발생일자 등) 작성하여 주실 것을 부탁드리며, 사생활, 타인 비방 목적의 음해성 제보는 민원으로 처리되지 않습니다.</li>
                  <li>신고 내용은 최대한 빠른 시일 내에 처리되며 진행여부는 신고한 사이트에서 확인할 수 있습니다.</li>
                </ul>
              </div>
              <form class="ethos-form" id="reportFrm" method="POST" enctype="multipart/form-data">
                <div class="ly-table">
                  <table>
                    <caption class="c-hidden">제보자 성명, 제보자 휴대폰, 제보자 이메일, 제목, 내용, 첨부, 결과통보 정보로 구성된 윤리상담센터 정보</caption>
                    <colgroup>
                      <col>
                      <col>
                    </colgroup>
                    <tbody>
                      <tr>
                        <th scope="row"><p>제보자 성명</p></th>
                        <td>
                          <label class="hidden" for="reportName">제보자 성명</label>
                          <input type="text" name="reportName" id="reportName" maxlength="30">
                          <span class="report-pc">※ 익명제보를 원하실 경우 '익명'으로 기재</span>
                          <span class="report-mo">※ 실명 또는 ‘익명’</span>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row"><p>제보자 휴대폰</p></th>
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
                      <tr>
                        <th scope="row"><p>제보자 이메일</p></th>
                        <td class="ethos-form__email">
                          <input name="reportMail01" id="reportMail01" maxlength="25" type="text" title="이메일 앞자리"> @ <input name="reportMail02" id="reportMail02" maxlength="25" type="text" title="이메일 도메인">
                          <select name="selEmail" id="selEmail" class="select_button u-ml--4" title="이메일 도메인 선택">
                            <option value="">직접입력</option>
                            <option value="naver.com">naver.com</option>
                            <option value="nate.com">nate.com</option>
                            <option value="dreamwiz.com">dreamwiz.com</option>
                            <option value="gmail.com">gmail.com</option>
                            <option value="hanmail.net">hanmail.net</option>
                          </select>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row"><p>제목</p></th>
                        <td>
                          <label class="hidden" for="reportTitle">제목</label>
                          <input type="text" name="reportTitle" id="reportTitle" style="width: 100%;" maxlength="100" value="">
                        </td>
                      </tr>
                      <tr>
                        <th scope="row"><p>내용</p></th>
                        <td>
                          <label class="hidden" for="reportContent">내용</label>
                          <textarea name="reportContent" id="reportContent" maxlength="2000"></textarea>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row"><p class="point-none">첨부</p></th>
                        <td style="position: relative;overflow: hidden;">
                          <input type="file" title="첨부파일" id="file" name="file" class="insert_file" onchange="Handlechange('file','filename');">
                          <input type="text" title="첨부파일 선택" id="filename" readonly="readonly" class="float_left margin_right_10">
                          <input type="button" value="파일선택" id="fakeBrowse" class="float_left">
                        </td>
                      </tr>
                      <tr>
                        <th scope="row"><p>결과통보</p></th>
                        <td>
                          <div class="payment_option">
                              <input type="radio" name="reportResultNotic" id="resPhone" value="P" title="휴대폰" checked=""><label for="resPhone"> 휴대폰 </label>
                              <input type="radio" name="reportResultNotic" id="resEmail" value="E" title="이메일" class="u-ml--64"><label for="resEmail"> 이메일 </label>
                              <input type="radio" name="reportResultNotic" id="resNone" value="N" title="미신청" class="u-ml--64"><label for="resNone"> 미신청 </label>
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
               </form>
               <div class="accordion">
                 <div class="accordion__item">
                   <div class="accordion__head">
                     <button aria-expanded="false" aria-controls="accContent1">
                       <span class="arrow"><i class="c-icon c-icon--arrow-right" aria-hidden="true"></i></span>
                     </button>
                     <div class="c-checkbox-wrap">
                       <input type="checkbox" class="c-checkbox" id="certCheck">
                       <label class="c-label" for="certCheck">개인정보 수집/이용 동의</label>
                     </div>
                   </div>
                   <div id="accContent1" class="accordion__content">
                     <div class="ethos-agree">
                       <ul class="text-list">
                         <li>kt M모바일은 서비스 제공을 위하여 필요한 최소한의 개인정보만을 수집합니다. (선택동의 없음)</li>
                         <li>고객님은 개인정보 수집/이용에 대한 동의를 거부할 권리가 있습니다. 단, 필수동의사항에 거부할 경우 서비스 이용이 불가하거나 제한이 있을 수 있습니다.</li>
                       </ul>
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
                               </tr>
                               <tr>
                                 <td>고객님께서 접수한 내용 처리</td>
                                 <td>성명, 휴대폰, 이메일 등 고객이 제시하는 개인정보</td>
                                 <td>고객의 신고사항 처리 후 3년(신고내용 재확인 및 통계용)</td>
                               </tr>
                             </tbody>
                           </table>
                         </div>
                       </div>
                     </div>
                   </div>
                 </div>
               </div>
               <div class="company-center__button">
                 <button class="btn--dark" id="submitBtn">등록</button>
               </div>
             </div>
             <div class="popup_content__after"></div>
           </div>
         </div>
         <button class="popup_cancel" id="closeModal2" aria-label="닫기">
             <i class="c-icon c-icon--gnb-all-close" aria-hidden="true"></i>
         </button>
     </div>
     <!-- 제보하기 입력 폼 E -->

     <!-- 제보완료 S -->
     <div id="reportCmpl" class="pop-layer" style="display:none;" role="dialog" aria-modal="true" aria-labelledby="reportCmplmodalTitle" aria-describedby="reportCmplmodalDesc" tabindex="-1">
       <div class="pop-container">
         <div class="pop-conts">
           <div class="popup_top">
             <h4 class="popup_title_h4" id="reportCmplmodalTitle">윤리상담센터</h4>
           </div>
           <div class="popup_content" id="reportCmplmodalDesc">
             <ul class="ethos-notice">
               <li class="ethos-notice__item">
                 <p class="ethos-notice__title" style="line-height: inherit;">감사합니다.<br/>제보해주신 내용이 성공적으로 접수되었습니다.</p>
                 <ul class="text-list">
                   <li>제보결과는 제보자 성명과 인증문자를 통해 확인이 가능합니다.<br/>아래의 인증문자를 기억 부탁 드립니다.</li>
                 </ul>
               </li>
               <li class="ethos-notice__item">
                 <p class="ethos-notice__title">인증문자 : <span style="color:red" id="viewCertNumber">000000</span></p>
                 <ul class="text-list">
                   <li>귀하께서 제보해주신 내용은 담당부서에서 빠르게 검토하여 신속하게 시정, 반영될 수 있도록 조치하겠습니다.</li>
                   <li>제보해주신 내용에 대한 처리결과는 제보 작성 시 결과통보 신청여부에 따라 처리 결과를 통보 해 드립니다.</li>
                   <li>kt M모바일을 사랑해주시는 마음에 깊은 감사를 드립니다.</li>
                 </ul>
               </li>
             </ul>
             <div class="company-center__button">
               <button class="btn--red layerConfirmBtn">확인</button>
             </div>
           </div>
           <div class="popup_content__after"></div>
         </div>
       </div>
       <button class="popup_cancel" id="closeModal3" aria-label="닫기">
           <i class="c-icon c-icon--gnb-all-close" aria-hidden="true"></i>
       </button>
     </div>
     <!-- 제보완료 E -->

     <!-- 접수결과 확인 S -->
     <div id="reportResultsSch" class="pop-layer" style="display:none;" role="dialog" aria-modal="true" aria-labelledby="reportResultsSchmodalTitle" aria-describedby="reportResultsSchmodalDesc" tabindex="-1">
       <div class="pop-container">
         <div class="pop-conts">
           <div class="popup_top">
             <h4 class="popup_title_h4" id="reportResultsSchmodalTitle">윤리상담 접수결과 확인</h4>
           </div>
           <div class="popup_content">
             <div id="resultDiv_01" id="reportResultsSchmodalDesc">
               <div class="ethos-form__text-wrap">
                 <ul class="text-list dot">
                   <li>접수하신 온라인 신고 및 의견에 대한 접수 현황 및 처리상태를 확인하실 수 있습니다.</li>
                 </ul>
               </div>
               <div class="ethos-form">
                 <div class="ly-table">
                    <table>
                      <caption class="c-hidden">인증문자, 성명 정보로 구성된 윤리상담 접수결과 정보</caption>
                      <colgroup>
                        <col>
                        <col>
                      </colgroup>
                      <tbody>
                        <tr>
                          <th scope="row"><p class="point-none">인증문자</p></th>
                          <td>
                          <label class="hidden" for="searchCertNum">인증문자</label>
                          <input type="text" id="searchCertNum" maxlength="30">
                        </td>
                        </tr>
                        <tr>
                          <th scope="row"><p class="point-none">성명</p></th>
                          <td>
                          <label class="hidden" for="searchName">성명</label>
                          <input type="text" id="searchName" maxlength="30">
                        </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <ul class="text-list">
                  <li>※ 익명으로 접수하신 경우 성명란에 '익명'으로 기입 요청드립니다.</li>
                </ul>
                <div class="company-center__button">
                  <button class="btn--red" id="reportResultsSchBtn">확인</button>
                </div>
              </div>

              <!-- 결과조회 완료 S-->
              <div id="resultDiv_02" style="display:none;">
                <form class="ethos-form u-mt--0">
	                <div class="ly-table u-mt--0">
	                  <table>
	                    <caption class="hidden">윤리상담 접수결과 확인</caption>
	                    <colgroup>
	                      <col>
	                      <col>
	                    </colgroup>
	                    <tbody>
	                      <tr>
	                        <th scope="row">제보자 성명</th>
	                        <td id="viewReportName"></td>
	                      </tr>
	                      <tr>
	                        <th scope="row">제보일자</th>
	                        <td id="viewReportDt"></td>
	                      </tr>
	                      <tr>
	                        <th scope="row">제목</th>
	                        <td id="viewReportTitle"></td>
	                      </tr>
	                      <tr>
	                        <th scope="row">처리상태</th>
	                        <td id="viewReportStatus"></td>
	                      </tr>
	                      <tr>
	                        <th scope="row">접수일자</th>
	                        <td id="viewReportRcvCpltDt"></td>
	                      </tr>
	                      <tr>
	                        <th scope="row">완료일자</th>
	                        <td id="viewReportAnswerDt"></td>
	                      </tr>
	                      <tr>
	                        <th scope="row">비고</th>
	                        <td id="viewReportAnswer"></td>
	                      </tr>
	                    </tbody>
	                  </table>
	                </div>
                </form>
                <div class="company-center__button">
                  <button class="btn--red layerConfirmBtn">확인</button>
                </div>
              </div>
            </div>
            <div class="popup_content__after"></div>
          </div>
        </div>
        <button class="popup_cancel" id="closeModal4" aria-label="닫기">
          <i class="c-icon c-icon--gnb-all-close" aria-hidden="true"></i>
        </button>
      </div>
      <!-- 접수결과 확인 E -->

   </div>
   <!-- 윤리경영 팝업 끝  -->

    <!-- 딤처리 오버레이 -->
    <div class="overlay" id="overlayPc" onclick="toggleMenuAll()"></div>
    <div class="overlay" id="overlayMo" onclick="toggleMenu()"></div>
  </div>

  <%@ include file="/WEB-INF/views/company/layouts/commonJs.jsp" %>
  <script type="text/javascript" src="/resources/js/jqueryCommon.js?version=250513"></script>
  <script type="text/javascript" src="/resources/js/common/validator.js?version=250513"></script>
  <script type="text/javascript" src="/resources/company/js/company.js?version=260310"></script>
  <script type="text/javascript" src="/resources/js/portal/ktm.ui.js?version=250513"></script>

</body>
</html>