<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/resources/js/jqueryCommon.js"></script>
<script type="text/javascript" src="/resources/js/nmcpCommon.js"></script>
<script type="text/javascript" src="/resources/js/mobile/ktm.ui.js"></script>
<script type="text/javascript" src="/resources/js/portal/nmcpCommonComponent.js"></script>
<script type="text/javascript" src="/resources/js/mobile/popup/niceAuth.js?version=24.05.21"></script>
<script type="text/javascript" src="/resources/js/mobile/popup/kakaoAuthPop.js?version=24.03.26"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/mobile/styles.css" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no,maximum-scale=1.0, minimum-scale=1.0">

<head>
    <title>카카오 인증</title>
</head>
<script>
    let MCP= new NmcpCommonComponent();
</script>
<body style="margin:0;padding:0;border:0;">

    <input type="hidden" name="authId" id="txId"/>
    <div class="ly-wrap"></div>

    <div class="kakao-auth-wrap" id="passLoginWrap">
        <div class="ly-wrap">
            <header class="ly-header">
                <div class="header-main">
                    <div class="ly-header__logo">
                          <img class="c-logo" src="/resources/images/mobile/common/img_logo_ktm.png" alt="KT M Mobile 로고">
                    </div>
                    <div class="ly-header__logo">
                          <img class="c-logo" src="/resources/images/mobile/common/img_logo_kakao.png" alt="KaKao 로고">
                    </div>
                  </div>
            </header>
            <div class="ly-content">

                <div class="c-form u-mt--24" id="kakaoInfoDev">
                    <div class="c-form__group" role="group" aria-labelledby="inpInfo">
                        <div class="c-form__input">
                              <input class="c-input" id="custNm" type="text" placeholder="예) 홍길동" name="" value="">
                            <label class="c-form__label" for="custNm">이름</label>
                           </div>
                        <div class="c-form__input">
                              <input class="c-input onlyNum" id="birthday" type="number" placeholder="예) 19001010" name="" value="" maxlength="8">
                            <label class="c-form__label" for="birthday">생년월일</label>
                        </div>
                        <div class="c-form__input">
                              <input class="c-input onlyNum" id="inpPhone" type="number" placeholder="예) 01012345678" name="" value="" maxlength="11">
                            <label class="c-form__label" for="inpPhone">휴대폰</label>
                        </div>
                      </div>
                      <div class="c-form">
                          <span class="c-form__title">이용약관</span>
                          <div class="c-agree u-mt--0">
                            <input class="c-checkbox" id="kakaoAuthAllCheck" type="checkbox">
                            <label class="c-label" for="kakaoAuthAllCheck">전체 약관에 동의합니다.</label>
                            <div class="c-agree__item">
                                  <input type="checkbox" class="c-checkbox c-checkbox--type2 _agree" id="clausePriCollectFlag"  value="">
                                  <label class="c-label" for="clausePriCollectFlag">개인정보 수집 및 이용동의</label>
                                  <button class="c-button c-button--xsm" data-dialog-trigger="#clausePriCollectFlagModal">
                                    <span class="c-hidden">상세보기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                  </button>
                            </div>
                            <div class="c-agree__item">
                                  <input type="checkbox" class="c-checkbox c-checkbox--type2 _agree" id="clauseEssCollectFlag"  value="">
                                  <label class="c-label" for="clauseEssCollectFlag">개인정보 제3자 제공동의</label>
                                  <button class="c-button c-button--xsm" data-dialog-trigger="#clauseEssCollectFlagModal">
                                    <span class="c-hidden">상세보기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                  </button>
                            </div>
                          </div>
                    </div>
                    <div class="c-notice-wrap">
                          <hr>
                          <h5 class="c-notice__title">
                              <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                              <span>유의사항</span>
                          </h5>
                          <ul class="c-notice__list">
                              <li>인증이 정상적으로 진행되지 않을 경우, 카카오인증서를 재발급 받으신 후 이용해주세요.</li>
                          </ul>
                      </div>
                      <div class="c-button-wrap u-mt--16" id="KaAuthBtn">
                        <button class="c-button c-button--full c-button--gray" id="kakaoCancle">닫기</button>
                        <button class="c-button c-button--full c-button--primary" id="kakaoAuth">인증 하기</button>
                      </div>
                      <div class="u-ta-center u-mt--16">
                          <button class="fs-12" data-dialog-trigger="#kakaoFlagModal">카카오 인증서 개인정보처리방침</button>
                    </div>
                </div>

                <div class="c-form u-mt--24" id="comfirmDiv" style="display: none">
                    <ul class="c-step-list">
                          <li class="c-step-list__item">
                            <span class="c-step-list__label">STEP 1</span>
                            <i class="c-step-list__image" aria-hidden="true">
                                  <img src="/resources/images/public/icon/ico_auth_step_01.svg" alt="">
                            </i>
                            <strong class="c-step-list__title">인증 요청</strong>
                            <p class="c-step-list__text">앱 인증 요청 메세지 확인 (APP PUSH)</p>
                          </li>
                          <li class="c-step-list__item">
                            <span class="c-step-list__label">STEP 2</span>
                            <i class="c-step-list__image" aria-hidden="true">
                                  <img src="/resources/images/public/icon/ico_auth_step_02.svg" alt="">
                            </i>
                            <strong class="c-step-list__title">앱 인증</strong>
                            <p class="c-step-list__text">앱 내 인증 진행</p>
                          </li>
                          <li class="c-step-list__item">
                            <span class="c-step-list__label">STEP 3</span>
                            <i class="c-step-list__image" aria-hidden="true">
                                  <img src="/resources/images/public/icon/ico_auth_step_03.svg" alt="">
                            </i>
                            <strong class="c-step-list__title">인증 확인</strong>
                            <p class="c-step-list__text">인증 완료 후, 하단의 인증 확인 클릭</p>
                          </li>
                    </ul>
                    <div class="u-ta-center u-mt--28">
                          <p class=" fs-14 u-fw--medium">휴대폰 APP PUSH 메시지가 오지 않았다면?</p>
                          <div class="u-co-gray fs-13 u-mt--16">
                              <p>1. 앱이 설치되어 있는지 확인해 주세요.</p>
                              <p>2. 해당 앱에 접속하시어 알림 내역을 확인해 주세요.</p>
                          </div>
                    </div>
                    <div class="c-button-wrap" id="KaAuthBtn">
                        <button class="c-button c-button--full c-button--gray" id="kakaoClose">닫기</button>
                        <button class="c-button c-button--full c-button--primary" id="kakaoComfirm">인증 확인</button>
                      </div>
                      <div class="u-ta-center u-mt--16">
                          <button class="fs-12" data-dialog-trigger="#kakaoFlagModal">카카오 인증서 개인정보처리방침</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</body>


<!-- 개인정보 수집 및 이용동의 팝업 -->
<div class="c-modal" id="clausePriCollectFlagModal" role="dialog" tabindex="-1" aria-describedby="#clausePriCollectFlagModalTitle">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
          <div class="c-modal__content">
            <div class="c-modal__header">
                  <h1 class="c-modal__title" id="clausePriCollectFlagModalTitle">개인정보 수집 및 이용동의</h1>
                   <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
            </div>
            <div class="c-modal__body">
                <div class="one-source">
                    <p class="c-text c-text--type3 u-mt--16">
                        이용자는㈜ 더즌(이하 “더즌”) 및 주식회사 카카오(이하 “카카오”)의 카카오 인증서 서비스를 제공받기 위하여 다음의 내용을 확인하고 “더즌”과 “카카오”가 본인의 개인정보를 다음과 같이 수집∙이용하는 것에 동의합니다.
                    </p>
                    <p class="c-text c-text--type3 u-mt--16">
                        “더즌”과 “카카오”는 “서비스”와 관련하여 인증서 발급/재발급/삭제 또는 서비스 이용 시 이용자로부터 아래와 같은 개인정보를 수집하고 있습니다
                    </p>
                    <h4 class="c-heading c-title--type3">1. 수집항목</h4>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">이동전화번호, 성명, 생년월일, 성별, 내외국인, 통신사정보, 본인확인값(CI, TI)</li>
                        <li class="c-text-list__item">단말기 정보(Android IMEI, iOS UUID, OS정보 등)</li>
                        <li class="c-text-list__item">이용기관/대행사, 인증요청내용(제목/내용), 이동전화번호,성명, 생년월일,성별, 인증요청일시/인증일시 등</li>
                        <li class="c-text-list__item">로그기록, 접속지 정보 등</li>
                      </ul>
                      <h4 class="c-heading c-title--type3">2. 수집 및 이용 목적</h4>
                      <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">“서비스” 이용을 위한 이용자 식별(본인 및 연령 확인 포함), 서비스 관리, 인증서 발급/삭제 및 검증관리, 회원관리, 상담관리 목적</li>
                        <li class="c-text-list__item">부정 이용방지를 위한 확인 및 인증서 검증관리 목적</li>
                        <li class="c-text-list__item">“서비스” 이용 이력 관리, 상담관리 목적</li>
                        <li class="c-text-list__item">서비스 이용 및 부정거래 기록 확인</li>
                      </ul>
                      <h4 class="c-heading c-title--type3">3. 보유 및 이용기간</h4>
                      <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">개인정보는 본 “서비스” 를 제공하는 기간 동안 보유 및 이용되고, 이용자의 “서비스” 해지 시 해당 이용자의 개인정보가 열람 또는 이용될 수 없도록 파기 처리함. 단 관계 법령의 규정에 의하여 보존할 필요가 있는 경우 (3)개인정보 보유 및 이용기관 조항에서와 같이 관계법령에서 정한 일정한 기간 동안 이용자 정보를 보관</li>
                      </ul>
                    <p class="c-text c-text--type3 u-mt--16">
                        이용자의 개인정보는 원칙적으로 개인정보의 수집 및 이용목적이 달성되면 지체 없이 파기합니다. 다만, “더즌”과 “카카오”는 관련법령의 규정에 의하여 개인정보를 보유할 필요가 있는 경우, 해당 법령에서 정한 바에 의하여 아래와 같이 개인정보를 보유할 수 있습니다. 법령에 의하여 수집, 이용되는 이용자의 정보는 아래와 같은 수집, 이용목적으로 보관합니다.
                    </p>
                    <h4 class="c-heading c-title--type3">1. 전자서명법</h4>
                      <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">인증서 발급/삭제 및 인증업무 이용에 관한 기록 (인증서 발급/삭제 및 인증업무 이용에 관한 기록) : 5년</li>
                      </ul>
                      <h4 class="c-heading c-title--type3">2. 통신비밀보호법</h4>
                      <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">통신사실확인자료 제공 (로그기록, 접속지 정보 등) : 3개월</li>
                        <li class="c-text-list__item">통신사실확인자료 제공 (그 외 통신사실 확인 자료) : 12개월</li>
                      </ul>
                      <h4 class="c-heading c-title--type3">3. 전상거래 등에서의 소비자 보호에 관한 법률</h4>
                      <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">표시, 광고에 관한 기록 (표시, 광고 기록) : 6개월</li>
                        <li class="c-text-list__item">대금결제 및 재화 등의 공급에 관한 기록 (대금결제/재화 등의 공급 기록) : 5년</li>
                        <li class="c-text-list__item">계약 또는 청약철회 등에 관한 기록 (소비자 식별정보 계약/청약철회 기록) : 5년</li>
                        <li class="c-text-list__item">소비자 불만 또는 분쟁처리에 관한 기록 (소비자 식별정보 분쟁처리 기록) : 3년</li>
                      </ul>
                      <p class="c-text c-text--type3 u-mt--16">이용자는 동의를 거부할 권리가 있으나, 동의 거부 시 카카오 인증서 서비스 이용이 불가능합니다.</p>
                </div>
            </div>
            <div class="c-modal__footer">
                <button class="c-button c-button--full c-button--primary" onclick="kakaoAgreeCheck('clausePriCollectFlag')" data-dialog-close>동의 후 닫기</button>
            </div>
          </div>
       </div>
</div>

<!-- 개인정보 제3자 제공동의 팝업 -->
<div class="c-modal" id="clauseEssCollectFlagModal" role="dialog" tabindex="-1" aria-describedby="#clauseEssCollectFlagModalTitle">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
          <div class="c-modal__content">
            <div class="c-modal__header">
                  <h1 class="c-modal__title" id="clauseEssCollectFlagModalTitle">개인정보 제3자 제공동의</h1>
                   <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
            </div>
            <div class="c-modal__body">
                <div class="one-source">
                    <p class="c-text c-text--type3 u-mt--16">
                        주식회사 케이티엠모바일(이하 “회사”라 한다)은 카카오 인증서의 원활한 진행을 위해 다음과 같은 정보를 ㈜더즌, 주식회사 카카오에 제공합니다. 취득한 개인정보는 “통신비밀보호법”, “전자통신사업법”, "전자서명법" 및 “개인정보 보호법” 등 정보통신서비스 제공자가 준수하여야 할 관련 법령 상의 개인정보 보호 규정을 준수합니다.
                    </p>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">제공 받는 자 : (주)더즌, 주식회사 카카오</li>
                        <li class="c-text-list__item">제공 목적 : 본인 확인을 통한 부정이용 방지(인증서 발급/재발급/삭제, 서비스 제공) 및 민원확인용</li>
                        <li class="c-text-list__item">제공하는 개인정보 : 전화번호, 이름, 생년월일</li>
                        <li class="c-text-list__item">보유기간 : 본 서비스 해지 시 까지 또는 관계 법령에 따른 보관의무 기간 동안 보관</li>
                      </ul>
                      <p class="c-text c-text--type3 u-mt--16">
                        “서비스” 제공을 위해 필요한 최소한의 개인정보이므로 동의를 해주셔야 “서비스” 이용이 가능합니다.
                    </p>
                </div>
            </div>
            <div class="c-modal__footer">
                <button class="c-button c-button--full c-button--primary" onclick="kakaoAgreeCheck('clauseEssCollectFlag')" data-dialog-close>동의 후 닫기</button>
            </div>
          </div>
       </div>
</div>

<!-- 카카오 인증서 개인정보처리방침 팝업 -->
<div class="c-modal" id="kakaoFlagModal" role="dialog" tabindex="-1" aria-describedby="#kakaoFlagModalTitle">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
          <div class="c-modal__content">
            <div class="c-modal__header">
                  <h1 class="c-modal__title" id="kakaoFlagModalTitle">카카오 인증서 개인정보처리방침</h1>
                   <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
            </div>
            <div class="c-modal__body">
                <div class="one-source">
                    <h4 class="c-heading c-title--type3 u-mt--16">1. 개인정보처리방침</h4>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">주식회사 카카오(이하"회사" 또는 “카카오”)는 이용자의 ‘동의를 기반으로 개인정보를 수집·이용 및 제공’하고 있으며, ‘이용자의 권리 (개인정보 자기결정권)를 적극적으로 보장’합니다.</li>
                        <li class="c-text-list__item">회사는 정보통신서비스제공자가 준수하여야 하는 대한민국의 관계 법령 및 개인정보보호 규정, 가이드라인을 준수하고 있습니다.</li>
                        <li class="c-text-list__item">“개인정보처리방침”이란 이용자의 소중한 개인정보를 보호함으로써 이용자가 안심하고 서비스를 이용할 수 있도록 회사가 준수해야 할 지침을 의미합니다.</li>
                        <li class="c-text-list__item">본 개인정보처리방침은 회사가 제공하는 카카오계정 기반의 서비스(이하 ‘서비스'라 함)에 적용됩니다.</li>
                      </ul>
                      <h4 class="c-heading c-title--type3">2. 개인정보 수집</h4>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">서비스 제공을 위한 필요 최소한의 개인정보를 수집하고 있습니다.</li>
                        <li class="c-text-list__item">회원 가입 시 또는 서비스 이용 과정에서 홈페이지 또는 개별 어플리케이션이나 프로그램 등을 통해 아래와 같은 서비스 제공을 위해 필요한 최소한의 개인정보를 수집하고 있습니다.</li>
                        <li class="c-text-list__item">
                            [카카오계정]
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">필수 : 이메일, 비밀번호, 이름(닉네임), 프로필사진, 친구목록, 카카오톡 전화번호(카카오톡 이용자의 경우에 한함), 연락처, 서비스 이용내역, 서비스 내 구매 및 결제 내역</li>
                                <li class="c-text-list__item">선택 : 생년월일, 성별, 배송지정보(수령인명, 배송지 주소, 전화번호)</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            [본인인증 시]
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">이름, 성별, 생년월일, 휴대폰번호, 통신사업자, 내/외국인 여부, 암호화된 이용자 확인값(CI), 중복가입확인정보(DI)</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            [법정대리인 동의 시]
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">법정대리인 정보(이름, 성별, 생년월일, 휴대폰번호, 통신사업자, 내/외국인 여부, 암호화된 이용자 확인값(CI), 중복가입확인정보(DI))</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            [유료서비스 이용 시]
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">신용카드 결제 시: 카드번호(일부), 카드사명 등</li>
                                <li class="c-text-list__item">휴대전화번호 결제 시: 휴대전화번호, 결제승인번호 등</li>
                                <li class="c-text-list__item">계좌이체 시: 예금주명, 계좌번호, 계좌은행 등</li>
                                <li class="c-text-list__item">상품권 이용 시: 상품권 번호, 해당 사이트 아이디</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            [환불처리 시]
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">계좌은행, 계좌번호, 예금주명, 이메일</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            [현금영수증 발행 시]
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">휴대폰번호, 현금영수증 카드번호</li>
                                <li class="c-text-list__item">일부 서비스에서는 특화된 여러 기능들을 제공하기 위해 ‘카카오계정’에서 공통으로 수집하는 정보 이외에 이용자에게 동의를 받고 추가적인 개인정보를 수집할 수 있습니다.</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            필수정보란?
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">해당 서비스의 본질적 기능을 수행하기 위한 정보</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            선택정보란?
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">보다 특화된 서비스를 제공하기 위해 추가 수집하는 정보 (선택 정보를 입력하지 않은 경우에도 서비스 이용 제한은 없습니다.)</li>
                              </ul>
                        </li>
                      </ul>
                      <p class="c-text c-text--type3 u-mt--16">개인정보를 수집하는 방법은 다음과 같습니다.</p>
                      <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">개인정보를 수집하는 경우에는 반드시 사전에 이용자에게 해당 사실을 알리고 동의를 구하고 있으며, 아래와 같은 방법을 통해 개인정보를 수집합니다.</li>
                        <li class="c-text-list__item">회원가입 및 서비스 이용 과정에서 이용자가 개인정보 수집에 대해 동의를 하고 직접 정보를 입력하는 경우</li>
                        <li class="c-text-list__item">제휴 서비스 또는 단체 등으로부터 개인정보를 제공받은 경우</li>
                        <li class="c-text-list__item">고객센터를 통한 상담 과정에서 웹페이지, 메일, 팩스, 전화 등</li>
                        <li class="c-text-list__item">온·오프라인에서 진행되는 이벤트/행사 등 참여</li>
                        <li class="c-text-list__item">만 14세 미만 아동의 개인정보를 수집할 때 법정 대리인의 동의를 얻어 최소한의 개인정보를 수집하고 있습니다.</li>
                        <li class="c-text-list__item">아동에게 법정대리인의 성명, 연락처와 같이 최소한의 정보를 요구할 수 있으며, 아래 방법 으로 법정대리인의 동의를 확인합니다.</li>
                        <li class="c-text-list__item">법정대리인의 휴대전화 본인인증을 통해 본인여부를 확인하는 방법</li>
                        <li class="c-text-list__item">법정대리인에게 동의내용이 적힌 서면을 제공하여 서명날인 후 제출하게 하는 방법</li>
                        <li class="c-text-list__item">그 밖에 위와 준하는 방법으로 법정대리인에게 동의내용을 알리고 동의의 의사표시를 확인하는 방법</li>
                        <li class="c-text-list__item">
                            서비스 이용 과정에서 이용자로부터 수집하는 개인정보는 아래와 같습니다.
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">PC웹, 모바일 웹/앱 이용 과정에서 단말기정보(OS, 화면사이즈, 디바이스 아이디, 폰기종, 단말기 모델명), IP주소, 쿠키, 방문일시, 부정이용기록, 서비스 이용 기록 등의 정보가 자동으로 생성되어 수집될 수 있습니다.</li>
                                <li class="c-text-list__item">서비스 간 제휴, 연계 등으로 제3자로부터 개인정보를 제공받고 있습니다.</li>
                              </ul>
                        </li>
                      </ul>
                      <h4 class="c-heading c-title--type3">3. 개인정보 이용</h4>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">회원관리, 서비스 제공·개선, 신규 서비스 개발 등을 위해 이용합니다.</li>
                        <li class="c-text-list__item">회원 가입 시 또는 서비스 이용 과정에서 홈페이지 또는 개별 어플리케이션이나 프로그램 등을 통해 아래와 같이 서비스 제공을 위해 필요한 최소한의 개인정보를 수집하고 있습니다.</li>
                        <li class="c-text-list__item">회원 식별/가입의사 확인, 본인/연령 확인, 부정이용 방지</li>
                        <li class="c-text-list__item">만 14세 미만 아동 개인정보 수집 시 법정 대리인 동의여부 확인, 법정 대리인 권리행사 시 본인 확인</li>
                        <li class="c-text-list__item">이용자간 메시지 전송, 친구등록 및 친구추천 기능의 제공</li>
                        <li class="c-text-list__item">친구에게 활동내역을 알리거나 이용자 검색, 등록 등의 기능 제공</li>
                        <li class="c-text-list__item">신규 서비스 개발, 다양한 서비스 제공, 문의사항 또는 불만처리, 공지사항 전달</li>
                        <li class="c-text-list__item">유료서비스 이용 시 콘텐츠 등의 전송이나 배송·요금 정산</li>
                        <li class="c-text-list__item">서비스의 원활한 운영에 지장을 주는 행위(계정 도용 및 부정 이용 행위 등 포함)에 대한 방지 및 제재</li>
                        <li class="c-text-list__item">인구통계학적 특성과 이용자의 관심, 기호, 성향의 추정을 통한 맞춤형 콘텐츠 추천 및 마케팅에 활용</li>
                        <li class="c-text-list__item">음성명령 처리 및 음성인식 향상, 개인화 서비스 제공</li>
                        <li class="c-text-list__item">서비스 이용 기록, 접속 빈도 및 서비스 이용에 대한 통계, 프라이버시 보호 측면의 서비스 환경 구축, 서비스 개선에 활용</li>
                        <li class="c-text-list__item">개인정보의 추가적인 이용・제공을 하는 경우가 있습니다.</li>
                        <li class="c-text-list__item">수집 목적과 합리적으로 관련된 범위에서는 법령에 따라 이용자의 동의 없이 개인정보를 이용하거나 제3자에게 제공할 수 있으며, 이때 ‘당초 수집 목적과 관련성이 있는지, 수집한 정황 또는 처리 관행에 비추어 볼 때 개인정보의 추가적인 이용 또는 제공에 대한 예측 가능성이 있는지, 이용자의 이익을 부당하게 침해하는지, 가명처리 또는 암호화 등 안전성 확보에 필요한 조치를 하였는지’를 종합적으로 고려합니다.</li>
                        <li class="c-text-list__item">카카오는 수집한 개인정보를 특정 개인을 알아볼 수 없도록 가명처리하여 통계작성, 과학적 연구, 공익적 기록보존 등을 위하여 처리할 수 있습니다. 이 때 가명정보는 재식별되지 않도록 추가정보와 분리하여 별도 저장・관리하고 필요한 기술적・관리적 보호조치를 취합니다.</li>
                      </ul>
                      <h4 class="c-heading c-title--type3">4. 개인정보 제공</h4>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">카카오는 이용자의 별도 동의가 있는 경우나 법령에 규정된 경우를 제외하고는 이용자의 개인정보를 제3자에게 제공하지 않습니다.</li>
                        <li class="c-text-list__item">제3자 서비스와의 연결을 위해 아래와 같이 개인정보를 제공하고 있습니다.</li>
                        <li class="c-text-list__item">카카오는 이용자의 사전 동의 없이 개인정보를 제3자에게 제공하지 않습니다. 다만, 이용자가 외부 제휴사 등의 서비스를 이용하기 위하여 필요한 범위 내에서 이용자의 동의를 얻은 후에 개인정보를 제3자에게 제공하고 있습니다.</li>
                        <li class="c-text-list__item">
                            개인정보 제3자 제공 중 국외로 제공되는 경우는 아래와 같습니다.
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">카카오는 재난, 감염병, 급박한 생명・신체 위험을 초래하는 사건사고, 급박한 재산 손실 등의 긴급상황이 발생하는 경우 정보주체의 동의 없이 관계기관에 개인정보를 제공할 수 있습니다.</li>
                                <li class="c-text-list__item">서비스 제공을 위해 아래와 같은 업무를 위탁하고 있습니다.</li>
                                <li class="c-text-list__item">서비스 제공에 있어 필요한 업무 중 일부를 외부 업체로 하여금 수행하도록 개인정보를 위탁하고 있습니다. 그리고 위탁업무 수행목적 외 개인정보 처리금지, 기술적・관리적 보호조치 적용, 재위탁 제한 등 위탁 받은 업체가 관계 법령을 준수하도록 관리·감독하고 있습니다.</li>
                              </ul>
                        </li>
                      </ul>
                      <h4 class="c-heading c-title--type3">5. 개인정보 파기</h4>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">개인정보는 수집 및 이용목적이 달성되면 지체없이 파기하며, 절차 및 방법은 아래와 같습니다.</li>
                        <li class="c-text-list__item">수집 및 이용 목적의 달성 또는 회원 탈퇴 등 파기 사유가 발생한 개인정보는 개인정보의 형태를 고려하여 파기방법을 정합니다.</li>
                        <li class="c-text-list__item">전자적 파일 형태인 경우 복구 및 재생되지 않도록 안전하게 삭제하고, 그 밖에 기록물, 인쇄물, 서면 등의 경우 분쇄하거나 소각하여 파기합니다.</li>
                        <li class="c-text-list__item">
                            다만, 내부 방침에 따라 일정 기간 보관 후 파기하는 정보는 아래와 같습니다.
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">아래 정보는 탈퇴일부터 최대 1년간 보관 후 파기합니다.</li>
                                <li class="c-text-list__item">안내메일 발송 및 CS문의 대응을 위해 카카오계정과 탈퇴안내 이메일 주소를 암호화하여 보관</li>
                                <li class="c-text-list__item">서비스 부정이용 기록</li>
                                <li class="c-text-list__item">카카오같이가치: 프로젝트 모금함 최종 종료일부터 3년 경과 후 파기합니다.(단, 미선정의 경우, 미선일로부터 90일 경과 후 파기)</li>
                                <li class="c-text-list__item">지원대상 선정 시 수집한 증빙서류</li>
                                <li class="c-text-list__item">카카오 인증서 : 인증서 이용내역은 인증서 폐지 시점으로부터 10년 보관 후 파기합니다.</li>
                                <li class="c-text-list__item">이름, 생년월일, 성별, 휴대폰번호, 암호화된 이용자 확인값(CI), 인증서 데이터</li>
                                <li class="c-text-list__item">코로나19 잔여백신 당일예약 신청: 정보를 수집한 해당 연도의 마지막 날까지 보관 후 파기합니다.</li>
                                <li class="c-text-list__item">코로나19 백신 접종 예약 의료기관 및 예약 시간, 예약결과</li>
                                <li class="c-text-list__item">음(mm) : 부정 이용으로 신고된 음성정보는 접수 시점으로부터 3년 보관 후 파기합니다.</li>
                                <li class="c-text-list__item">닉네임, 음성정보</li>
                                <li class="c-text-list__item">오픈채팅 > 보이스룸 : 부정 이용으로 신고된 음성정보는 접수 시점으로부터 3년 보관 후 파기합니다.</li>
                                <li class="c-text-list__item">또한, 카카오는 ‘개인정보 유효기간제’에 따라 1년간 서비스를 이용하지 않은 회원의 개인정보를 별도로 분리 보관 또는 삭제하고 있으며, 분리 보관된 개인정보는 4년간 보관 후 지체없이 파기합니다.</li>
                                <li class="c-text-list__item">이 외에 법령에 따라 일정기간 보관해야 하는 개인정보 및 해당 법령은 아래 표와 같습니다.</li>
                            </ul>
                        </li>
                      </ul>
                      <h4 class="c-heading c-title--type3">6. 개인위치정보의 처리</h4>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">카카오는 위치정보의 보호 및 이용 등에 관한 법률(이하 ‘위치정보법’)에 따라 다음과 같이 개인위치정보를 처리합니다.</li>
                        <li class="c-text-list__item">카카오 위치기반서비스 이용약관 제4조에 따른 서비스의 제공을 위해 개인위치정보를 보유할 수 있습니다.</li>
                        <li class="c-text-list__item">위치기반서비스 이용 및 제공 목적 달성한 때에는 지체없이 개인위치정보를 파기합니다.</li>
                        <li class="c-text-list__item">이용자가 작성한 게시물 또는 콘텐츠와 함께 위치정보가 저장되는 서비스의 경우 해당 게시물 또는 콘텐츠의 보관기간 동안 개인위치정보가 보관됩니다.</li>
                        <li class="c-text-list__item">그 외 위치기반서비스 제공을 위해 필요한 경우 이용목적 달성을 위해 필요한 최소한의 기간 동안 개인위치정보를 보유할 수 있습니다.</li>
                        <li class="c-text-list__item">개인위치정보의 수집 및 이용목적이 달성되면 지체없이 파기합니다.</li>
                        <li class="c-text-list__item">수집 및 이용 목적의 달성 또는 회원 탈퇴 등 개인위치정보 처리목적이 달성된 경우, 개인위치정보를 복구 및 재생되지 않도록 안전하게 삭제합니다.</li>
                        <li class="c-text-list__item">다만, 다른 법령에 따라 보관해야 하는 등 정당한 사유가 있는 경우에는 그에 따릅니다.</li>
                        <li class="c-text-list__item">또한, 위치정보법 제16조2항에 따라 이용자의 위치정보의 이용ㆍ제공사실 확인자료를 위치정보시스템에 6개월간 보관합니다.</li>
                        <li class="c-text-list__item">이용자의 사전 동의 없이 개인위치정보를 제3자에게 제공하지 않습니다.</li>
                        <li class="c-text-list__item">카카오는 이용자의 동의 없이 개인위치정보를 제3자에게 제공하지 않으며, 제3자에게 제공하는 경우에는 제공받는 자 및 제공목적을 사전에 이용자에게 고지하고 동의를 받습니다.</li>
                        <li class="c-text-list__item">이용자의 동의를 거쳐 개인위치정보를 제3자에게 제공하는 경우, 이용자에게 매회 이용자에게 제공받는 자, 제공일시 및 제공목적을 즉시 통지합니다.</li>
                        <li class="c-text-list__item">
                            8세 이하의 아동 등의 보호를 위해 필요한 경우 보호의무자의 권리는 다음과 같습니다.
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">위치정보법 제26조제1항에 따라 아래 이용자의 생명 또는 신체보호를 위하여 보호의무자가 개인위치정보의 이용 또는 제공에 동의하는 경우에는 본인의 동의가 있는 것으로 봅니다.</li>
                                <li class="c-text-list__item">8세 이하의 아동</li>
                                <li class="c-text-list__item">피성년후견인</li>
                                <li class="c-text-list__item">장애인복지법 제2조제2항제2호에 따른 정신적 장애를 가진 사람으로서 장애인고용촉진 및 직업재활법 제2조제2호에 따른 중증장애인에 해당하는 사람(장애인복지법 제32조에 따라 장애인 등록을 한 사람만 해당한다)</li>
                                <li class="c-text-list__item">위 권리를 행사하고자 하는 보호의무자는 서면동의서에 보호의무자임을 증명하는 서면을 첨부하여 회사에 제출하여야 하며, 이 경우 보호의무자는 위치기반서비스 이용약관에 따른 이용자의 권리를 모두 가집니다.</li>
                                <li class="c-text-list__item">그 외 개인위치정보 처리와 관련된 자세한 내용은 카카오 위치기반서비스 이용약관을 참고하시기 바랍니다.</li>
                              </ul>
                        </li>
                      </ul>
                      <h4 class="c-heading c-title--type3">7. 기타</h4>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item">카카오는 여러분의 권리를 보호합니다.</li>
                        <li class="c-text-list__item">이용자는 언제든지 자신의 개인정보를 조회하거나 수정할 수 있으며, 수집・이용, 제공에 대한 동의 철회 또는 가입 해지를 요청할 수 있습니다.</li>
                        <li class="c-text-list__item">만 14세 미만인 아동의 경우, 법정대리인이 아동의 개인정보를 조회하거나 수정 및 삭제, 처리정지, 수집 및 이용, 제공 동의를 철회할 권리를 보장합니다.</li>
                        <li class="c-text-list__item">보다 구체적으로는 서비스 내 설정을 통한 회원정보 수정 기능이나 회원탈퇴 기능을 이용할 수 있고, 고객센터를 통해 서면, 전화 또는 이메일로 요청하시면 지체 없이 조치하겠습니다.</li>
                        <li class="c-text-list__item">개인정보의 오류에 대한 정정을 요청하신 경우 정정을 완료하기 전까지 해당 개인정보를 이용 또는 제공하지 않습니다.</li>
                        <li class="c-text-list__item">또한, '온라인 맞춤형 광고 개인정보보호 가이드 라인'에 따른 이용자 권리보호를 위한 페이지도 제공하고 있습니다.</li>
                        <li class="c-text-list__item">웹기반 서비스의 제공을 위하여 쿠키를 이용하는 경우가 있습니다.</li>
                        <li class="c-text-list__item">쿠키는 보다 빠르고 편리한 웹사이트 사용을 지원하고 맞춤형 서비스를 제공하기 위해 사용됩니다.</li>
                        <li class="c-text-list__item">
                            쿠키란?
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">웹사이트를 운영하는데 이용되는 서버가 이용자의 브라우저에 보내는 아주 작은 텍스트 파일로서 이용자 컴퓨터에 저장됩니다.</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            사용목적
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">개인화되고 맞춤화된 서비스를 제공하기 위해서 이용자의 정보를 저장하고 수시로 불러오는 쿠키를 사용합니다.</li>
                                <li class="c-text-list__item">이용자가 웹사이트에 방문할 경우 웹 사이트 서버는 이용자의 디바이스에 저장되어 있는 쿠키의 내용을 읽어 이용자의 환경설정을 유지하고 맞춤화된 서비스를 제공하게 됩니다.</li>
                                <li class="c-text-list__item">쿠키는 이용자가 웹 사이트를 방문할 때, 웹 사이트 사용을 설정한대로 접속하고 편리하게 사용할 수 있도록 돕습니다.</li>
                                <li class="c-text-list__item">또한, 이용자의 웹사이트 방문 기록, 이용 형태를 통해서 최적화된 광고 등 맞춤형 정보를 제공하기 위해 활용됩니다.</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            쿠키 수집 거부
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">이용자는 쿠키 설치에 대한 선택권을 가지고 있습니다.</li>
                                <li class="c-text-list__item">따라서, 이용자는 웹 브라우저에서 옵션을 설정함으로써 모든 쿠키를 허용하거나, 쿠키가 저장될 때마다 확인을 거치거나, 모든 쿠키의 저장을 거부할 수도 있습니다.</li>
                                <li class="c-text-list__item">다만, 쿠키 설치를 거부할 경우 웹 사용이 불편해지며, 로그인이 필요한 일부 서비스 이용에 어려움이 있을 수 있습니다.</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            설정 방법의 예
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">1) Internet Explorer의 경우 : 웹 브라우저 상단의 도구 메뉴 > 인터넷 옵션 > 개인정보 > 설정</li>
                                <li class="c-text-list__item">2) Chrome의 경우 : 웹 브라우저 우측의 설정 메뉴 > 화면 하단의 고급 설정 표시 > 개인정보의 콘텐츠 설정 버튼 > 쿠키</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">카카오는 이용자의 소중한 개인정보 보호를 위해 다음의 노력을 하고 있습니다.</li>
                        <li class="c-text-list__item">이용자의 개인정보를 가장 소중한 가치로 여기고 개인정보를 처리함에 있어서 다음과 같은 노력을 다하고 있습니다.</li>
                        <li class="c-text-list__item">이용자의 개인정보를 암호화하고 있습니다.</li>
                        <li class="c-text-list__item">이용자의 개인정보를 암호화된 통신구간을 이용하여 전송하고, 비밀번호 등 중요정보는 암호화하여 보관하고 있습니다.</li>
                        <li class="c-text-list__item">해킹이나 컴퓨터 바이러스로부터 보호하기 위하여 노력하고 있습니다.</li>
                        <li class="c-text-list__item">해킹이나 컴퓨터 바이러스 등에 의해 이용자의 개인정보가 유출되거나 훼손되는 것을 막기 위해 외부로부터 접근이 통제된 구역에 시스템을 설치하고 있습니다. 해커 등의 침입을 탐지하고 차단할 수 있는 시스템을 설치하여 24시간 감시하고 있으며, 백신 프로그램을 설치하여 시스템이 최신 악성코드나 바이러스에 감염되지 않도록 노력하고 있습니다. 또한 새로운 해킹/보안 기술에 대해 지속적으로 연구하여 서비스에 적용하고 있습니다.</li>
                        <li class="c-text-list__item">소중한 개인정보에 접근할 수 있는 사람을 최소화하고 있습니다.</li>
                        <li class="c-text-list__item">개인정보를 처리하는 직원을 최소화 하며, 업무용 PC에서는 외부 인터넷 서비스를 사용할 수 없도록 차단 하여 개인정보 유출에 대한 위험을 줄이고 있습니다. 또한 개인정보를 보관하는 데이터베이스 시스템과 개인정보를 처리하는 시스템에 대한 비밀번호의 생성과 변경, 그리고 접근할 수 있는 권한에 대한 체계적인 기준을 마련하고 지속적인 감사를 실시하고 있습니다.</li>
                        <li class="c-text-list__item">임직원에게 이용자의 개인정보 보호에 대해 정기적인 교육을 실시하고 있습니다.</li>
                        <li class="c-text-list__item">개인정보를 처리하는 모든 임직원들을 대상으로 개인정보보호 의무와 보안에 대한 정기적인 교육과 캠페인을 실시하고 있습니다.</li>
                        <li class="c-text-list__item">이용자 정보의 보호 활동 및 체계에 대해 국내 및 국제 인증 규격을 충족하고 있습니다.</li>
                        <li class="c-text-list__item">정보보호 및 개인정보보호 관리 체계에 대해 국내 및 국제 인증 심사 규격 대비 충족 여부를 매년 독립된 심사 기관으로부터 검증을 받고 개선의 기회를 마련하고 있습니다.</li>
                        <li class="c-text-list__item">카카오는 유럽연합 일반 개인정보보호법(General Data Protection Regulation) 및 각 회원국의 법률을 준수합니다.</li>
                        <li class="c-text-list__item">유럽연합 내 이용자를 대상으로 서비스를 제공하는 경우, 아래 내용이 적용될 수 있습니다.</li>
                        <li class="c-text-list__item">
                            [개인정보 처리의 목적 및 처리근거]
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">카카오는 수집한 개인정보를 "3. 개인정보의 이용"에 기재된 목적으로만 이용하며, 사전에 이용자에게 해당 사실을 알리고 동의를 구하고 있습니다.</li>
                                <li class="c-text-list__item">그리고 GDPR 등 적용되는 법률에 따라, 카카오는 아래 하나에 해당하는 경우에 이용자의 개인정보를 처리할 수 있습니다.</li>
                                <li class="c-text-list__item">정보주체와의 계약의 체결 및 이행을 위한 경우</li>
                                <li class="c-text-list__item">법적 의무사항 준수를 위한 경우</li>
                                <li class="c-text-list__item">정보주체의 중대한 이익을 위해 처리가 필요한 경우</li>
                                <li class="c-text-list__item">회사의 적법한 이익 추구를 위한 경우(정보주체의 이익과 권리 또는 자유가 그 이익보다 중요한 경우는 제외)</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">
                            [유럽연합 내 이용자의 권리 보장]
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">카오는 "여러분의 권리를 보호합니다"에서 알린 바와 같이, 이용자의 개인정보를 소중하게 보호합니다. GDPR 등 적용되는 법률에 따라, 이용자는 자신의 개인정보를 다른 관리자에게 이전해 달라고 요청할 수 있고, 자신의 정보 처리 거부를 요청 할 수 있습니다. 그리고 이용자는 개인정보보호 권한 당국에 불만을 제기할 권리가 있습니다.</li>
                              </ul>
                        </li>
                        <li class="c-text-list__item">또한, 카카오는 이벤트 및 광고 등 마케팅을 제공하기 위해 개인정보를 활용할 수 있으며, 사전에 이용자의 동의를 구하고 있습니다. 이용자는 원하지 않은 경우 언제든지 동의를 철회할 수 있습니다.</li>
                        <li class="c-text-list__item">위와 관련한 요청사항은 고객센터를 통해 서면, 전화 또는 이메일로 연락하시면 지체 없이 조치하겠습니다.</li>
                        <li class="c-text-list__item">개인정보의 오류에 대한 정정을 요청하신 경우 정정을 완료하기 전까지 해당 개인정보를 이용 또는 제공하지 않습니다.</li>
                        <li class="c-text-list__item">서비스를 이용하면서 발생하는 모든 개인정보보호 관련 문의, 불만, 조언이나 기타 사항은 개인정보 보호책임자 및 담당부서로 연락해 주시기 바랍니다.</li>
                        <li class="c-text-list__item">카카오는 여러분의 목소리에 귀 기울이고 신속하고 충분한 답변을 드릴 수 있도록 최선을 다하겠습니다.</li>
                        <li class="c-text-list__item">
                            개인정보 보호책임자 및 담당부서
                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                <li class="c-text-list__item">책임자 : 김연지 (개인정보 보호책임자/DPO/위치정보관리책임자)</li>
                                <li class="c-text-list__item">담당부서 : 개인정보보호파트</li>
                                <li class="c-text-list__item">문의 : 카카오 고객센터 : 1577-3754 (유료)</li>
                                <li class="c-text-list__item">또한 개인정보가 침해되어 이에 대한 신고나 상담이 필요하신 경우에는 아래 기관에 문의하셔서 도움을 받으실 수 있습니다.</li>
                                <li class="c-text-list__item">개인정보침해 신고센터 (국번없이)118 https://privacy.kisa.or.kr</li>
                                <li class="c-text-list__item">대검찰청 사이버수사과 (국번없이)1301 cid@spo.go.kr</li>
                                <li class="c-text-list__item">경찰청 사이버안전국 (국번없이)182 https://ecrm.cyber.go.kr</li>
                                <li class="c-text-list__item">개인정보처리방침이 변경되는 경우 법률이나 서비스의 변경사항을 반영하기 위한 목적 등으로 개인정보처리방침을 수정할 수 있습니다.</li>
                                <li class="c-text-list__item">개인정보처리방침이 변경되는 경우 카카오는 변경 사항을 게시하며, 변경된 개인정보처리방침은 게시한 날로부터 7일 후부터 효력이 발생합니다.</li>
                                <li class="c-text-list__item">다만, 수집하는 개인정보의 항목, 이용목적의 변경 등과 같이 이용자 권리의 중대한 변경이 발생할 때에는 최소 30일 전에 미리 알려드리겠습니다.</li>
                                <li class="c-text-list__item">공고일자: 2022년 07월 25일</li>
                                <li class="c-text-list__item">시행일자: 2022년 08월 01일</li>
                              </ul>
                        </li>
                      </ul>
                </div>
            </div>
            <div class="c-modal__footer">
                <button class="c-button c-button--full c-button--primary" data-dialog-close>닫기</button>
            </div>
          </div>
       </div>
</div>
