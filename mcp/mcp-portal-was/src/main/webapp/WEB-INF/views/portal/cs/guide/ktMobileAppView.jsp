<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">kt M모바일 앱 안내</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">kt M모바일 앱 안내</h2>
            </div>

            <div class="ly-page--deco u-bk--beige">
                <div class="ly-avail--wrap">
                    <img src="/resources/images/portal/content/img_customer_center2.png" alt="언제 어디서나 바로 보는 편리함! kt M모바일 앱으로 확인하세요. #실시간 요금 조회 #실시간 사용량 조회 #요금제 조회/변경 #명세서 관리">
                </div>
            </div>
            <div class="c-row c-row--lg">
                <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">주요 기능</h3>
                <hr class="c-hr c-hr--title">
                <div class="customer-service customer-service--icon">
                    <div class="customer-service__item">
                        <i class="c-icon c-icon--function-01" aria-hidden="true"></i>
                        <strong class="customer-service__title"> 바로 확인하는 위젯</strong>
                            <button class="c-button c-button--xxsm c-button--mint--type3" data-dialog-trigger="#widget-install-dialog">위젯설정안내</button>
                    </div>
                    <div class="customer-service__item">
                        <i class="c-icon c-icon--function-02" aria-hidden="true"></i>
                        <strong class="customer-service__title">실시간으로 확인하는 <br>사용량 조회</strong>
                    </div>
                    <div class="customer-service__item">
                        <i class="c-icon c-icon--function-03" aria-hidden="true"></i>
                        <strong class="customer-service__title"> 한번에 확인하는 <br>가입 정보 조회</strong>
                    </div>
                </div>
                <div class="customer-service customer-service--icon u-mt--40">
                    <div class="customer-service__item">
                        <i class="c-icon c-icon--function-04" aria-hidden="true"></i>
                        <strong class="customer-service__title">실시간 요금조회 <br>월별금액 조회</strong>
                    </div>
                    <div class="customer-service__item">
                        <i class="c-icon c-icon--function-05" aria-hidden="true"></i>
                        <strong class="customer-service__title">간편한 요금제 <br>조회/변경</strong>
                    </div>
                </div>
                <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">kt M모바일 APP 다운받기</h3>
                <hr class="c-hr c-hr--title">
                <div class="app-download">
                    <div class="app-download__item">
                        <div class="app-download__image">
                            <img src="/resources/images/portal/content/img_qr_aos.png" alt="구글 플레이 스토어 다운받기 QR코드(https://play.google.com/store/apps/details?id=kt.co.ktmmobile)">
                        </div>
                        <strong class="app-download__title">안드로이드</strong>
                        <a class="app-download__anchor" href="https://play.google.com/store/apps/details?id=kt.co.ktmmobile" target="_blank" title="새창열림">구글 플레이스토어 다운받기
                            <i class="c-icon c-icon--arrow-white" aria-hidden="true"></i>
                        </a>
                    </div>
                    <div class="app-download__item">
                        <div class="app-download__image">
                            <img src="/resources/images/portal/content/img_qr_ios.png" alt="애플 앱스토어 다운받기 QR코드(https://itunes.apple.com/us/app/kt-mmobail-gogaegsenteo/id1094611503?mt=8)">
                        </div>
                        <strong class="app-download__title">iOS</strong>
                        <a class="app-download__anchor" href="https://itunes.apple.com/us/app/kt-mmobail-gogaegsenteo/id1094611503?mt=8" target="_blank" title="새창열림">애플 앱스토어 다운받기
                            <i class="c-icon c-icon--arrow-white" aria-hidden="true"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="c-modal c-modal--lg" id="widget-install-dialog" role="dialog" aria-labelledby="ars-menu-_title">
            <div class="c-modal__dialog" role="document">
              <div class="c-modal__content">
                <div class="c-modal__header">
                  <h2 class="c-modal__title" id="ars-menu-_title">위젯설정안내</h2>
                  <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
                </div>
                <div class="c-tabs c-tabs--type1 u-pt--32 u-border-bottom" data-ui-tab="">
                  <div class="c-tabs__list no-b-line c-tabs-sticky" role="tablist">
                    <button class="c-tabs__button u-width--260 u-ml--m16 is-active" id="widget1" role="tab" aria-controls="tabwidget1-panel" aria-selected="true" tabindex="0" onclick="location.href='#tabwidget1-panel' ">안드로이드폰</button>
                    <button class="c-tabs__button u-width--260 " id="widget2" role="tab" aria-controls="tabwidget2-panel" aria-selected="false" tabindex="-1" onclick="location.href='#tabwidget2-panel' ">아이폰</button>
                  </div>
                </div>
                <div class="c-modal__body u-pt--0">
                    <div class="c-tabs__panel" id="tabwidget1-panel" role="tabpanel" aria-labelledby="widget1" tabindex="-1">
                        <div class="c-row">
                          <div class="widget-guide">
                            <div class="widget-guide__item">
                              <span class="widget-guide__step">1</span>
                              <p class="widget-guide__text">설치된 앱 아이콘을 "꾹" 눌러,
                                <br />위젯을 선택해주세요.
                              </p>
                              <div class="widget-guide__image">
                                <img src="/resources/images/mobile/content/img_and_01.png" alt="설치된 앱 아이콘을꾹 눌러 위젯을 선택해주세요.">
                              </div>
                            </div>
                            <hr class="c-hr c-hr--basic">
                            <div class="widget-guide__item">
                              <span class="widget-guide__step">2</span>
                              <p class="widget-guide__text">원하시는 사이즈의 위젯을 추가해주세요.</p>
                              <div class="widget-guide__image">
                                <img src="/resources/images/mobile/content/img_and_02.png" alt="원하시는 사이즈의 위젯을 추가해주세요.">
                              </div>
                            </div>
                            <hr class="c-hr c-hr--basic">
                            <div class="widget-guide__item">
                              <span class="widget-guide__step">3</span>
                              <p class="widget-guide__text">홈화면에 추가된 위젯을
                                <br />확인하실 수 있습니다.
                              </p>
                              <div class="widget-guide__image">
                                <img src="/resources/images/mobile/content/img_and_03.png" alt="홈화면에 추가된 위젯을 확인하실 수 있습니다.">
                              </div>
                            </div>
                            <div class="widget-guide__item u-pt--0">
                              <div class="c-box c-box--type1">
                                <b class="c-text c-text--type3 u-block">
                                  <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>Tip
                                </b>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                  <li class="c-text-list__item u-co-gray">
                                      앱에서 로그인 상태 유지에 체크 후 로그인 하시면 로그아웃을 선택하기 전 까지는 계속
                                      <br />로그인 상태가 유지되어 별도 로그인 없이 위젯을 이용하실 수 있습니다.
                                      <br />(단, 2주동안 앱을 사용하지 않을 경우 로그인 상태 유지는 해제될 수 있습니다.)
                                  </li>
                                </ul>
                                <div class="widget-guide__image u-mt--30">
                                  <img class="u-width--73p" src="/resources/images/mobile/content/login.png" alt="앱에서 로그인 상태 유지에 체크 후 로그인 하시면 로그아웃을 선택하기 전 까지는 계속 로그인 상태가 유지되어 별도 로그인 없이 위젯을 이용하실 수 있습니다. (단, 개인정보 보호를 위해 로그인은 2주 동안 유지됩니다.)">
                                </div>
                              </div>
                            </div>
                            <div class="widget-guide__item u-pa--0">
                              <img src="/resources/images/mobile/content/img_setnumber.png" alt="대표번호 설정방법 - 복수의 단말기(회선)을 사용하시고 계시면 설정메뉴에서 원하시는 대표번호를 설정해주세요 설정된 회선기준으로 위젯의 사용량 정보를 확인하실 수 있습니다. 1. 전체메뉴 선택해주세요 → 2. 설정메뉴 선택해주세요. → 3. 원하는 대표번호를 설정하면 해당 번호기준으로 위젯의 사용량 정보가 출력됩니다.">
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="c-tabs__panel" id="tabwidget2-panel" role="tabpanel" aria-labelledby="widget2" tabindex="-1">
                        <div class="c-row">
                          <div class="widget-guide">
                            <div class="widget-guide__item">
                              <span class="widget-guide__step">1</span>
                              <p class="widget-guide__text">바탕화면의 빈영역을 "꾹" 누른 뒤,
                            <br />플러스(+)버튼을 선택하세요.
                              </p>
                              <div class="widget-guide__image">
                                <img src="/resources/images/mobile/content/img_ios_01.png" alt="바탕화면의 빈영역을 꾹 누른 뒤 플러스(+)버튼을 선택하세요.">
                              </div>
                            </div>
                            <hr class="c-hr c-hr--basic">
                            <div class="widget-guide__item">
                              <span class="widget-guide__step">2</span>
                              <p class="widget-guide__text">위젯 검색화면에서
                                  <br />"kt M모바일"을 선택하세요.
                                </p>
                              <div class="widget-guide__image">
                                <img src="/resources/images/mobile/content/img_ios_02.png" alt="위젯 검색화면에서 kt M모바일 선택하세요.">
                              </div>
                            </div>
                            <hr class="c-hr c-hr--basic">
                            <div class="widget-guide__item">
                              <span class="widget-guide__step">3</span>
                              <p class="widget-guide__text">원하시는 사이즈의 위젯을 추가해주세요.</p>
                              <div class="widget-guide__image">
                                <img src="/resources/images/mobile/content/img_ios_03.png" alt="원하시는 사이즈의 위젯을 추가해주세요.">
                              </div>
                            </div>
                            <hr class="c-hr c-hr--basic">
                            <div class="widget-guide__item">
                              <span class="widget-guide__step">4</span>
                              <p class="widget-guide__text">완료버튼을 선택하시면,
                                  <br />위젯설정이 완료됩니다.
                                </p>
                              <div class="widget-guide__image">
                                <img src="/resources/images/mobile/content/img_ios_04.png" alt="완료버튼을 선택하시면 위젯설정이 완료됩니다.">
                              </div>
                            </div>
                            <div class="widget-guide__item u-pt--0">
                              <div class="c-box c-box--type1">
                                <b class="c-text c-text--type3 u-block">
                                  <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>Tip
                                </b>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                  <li class="c-text-list__item u-co-gray">
                                      앱에서 로그인 상태 유지에 체크 후 로그인 하시면 로그아웃을 선택하기 전 까지는 계속
                                      <br />로그인 상태가 유지되어 별도 로그인 없이 위젯을 이용하실 수 있습니다.
                                      <br />(단, 2주동안 앱을 사용하지 않을 경우 로그인 상태 유지는 해제될 수 있습니다.)
                                  </li>
                                </ul>
                                <div class="widget-guide__image u-mt--30">
                                  <img class="u-width--73p" src="/resources/images/mobile/content/login.png" alt="앱에서 로그인 상태 유지에 체크 후 로그인 하시면 로그아웃을 선택하기 전 까지는 계속 로그인 상태가 유지되어 별도 로그인 없이 위젯을 이용하실 수 있습니다. (단, 개인정보 보호를 위해 로그인은 2주 동안 유지됩니다.)">
                                </div>
                              </div>
                            </div>
                            <div class="widget-guide__item u-pa--0">
                              <img src="/resources/images/mobile/content/img_setnumber.png" alt="대표번호 설정방법 - 복수의 단말기(회선)을 사용하시고 계시면 설정메뉴에서 원하시는 대표번호를 설정해주세요 설정된 회선기준으로 위젯의 사용량 정보를 확인하실 수 있습니다. 1. 전체메뉴 선택해주세요 → 2. 설정메뉴 선택해주세요. → 3. 원하는 대표번호를 설정하면 해당 번호기준으로 위젯의 사용량 정보가 출력됩니다.">
                            </div>
                          </div>
                        </div>
                      </div>
                </div>
              </div>
            </div>
          </div>

    </t:putAttribute>
</t:insertDefinition>
