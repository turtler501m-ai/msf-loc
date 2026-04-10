<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					kt M모바일 앱 안내
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-box c-box--type3">
				<strong class="c-box c-box--type3__title"> 언제 어디서나 <br />바로 보는 편리함!</strong>
				<p class="c-box c-box--type3__text">kt M모바일 앱으로 확인하세요</p>

				<c:set var="agent" value="${header['User-Agent']}" />
				<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
				<c:choose>
					<c:when test="${platFormCd eq 'A'}">
						<c:if test='${fn:indexOf(agent, "Android") > -1 }'>
							<a class="c-box c-box--type3__button u-mr--4" href="javascript:appOutSideOpen('https://play.google.com/store/apps/details?id=kt.co.ktmmobile');" title="새창열림">앱 다운로드</a>
							<button class="c-box c-box--type3__button--mint" data-dialog-trigger="#widget-install-dialog">
								<span class="c-hidden">위젯설정안내 상세팝업 보기</span>
								위젯설정안내
							</button>
						</c:if>
						<c:if test='${fn:indexOf(agent, "iPhone") > -1 or fn:indexOf(agent, "iPad") > -1}'>
							<a class="c-box c-box--type3__button u-mr--4" href="javascript:appOutSideOpen('https://itunes.apple.com/us/app/kt-mmobail-gogaegsenteo/id1094611503?mt=8');" title="새창열림">앱 다운로드</a>
							<button class="c-box c-box--type3__button--mint" data-dialog-trigger="#widget-install-dialog">
								<span class="c-hidden">위젯설정안내 상세팝업 보기</span>
								위젯설정안내
							</button>
						</c:if>
					</c:when>
					<c:otherwise>
						<c:if test='${fn:indexOf(agent, "Android") > -1 }'>
							<a class="c-box c-box--type3__button u-mr--4" href="https://play.google.com/store/apps/details?id=kt.co.ktmmobile" target="_blank">앱 다운로드</a>
							<button class="c-box c-box--type3__button--mint" data-dialog-trigger="#widget-install-dialog">
								<span class="c-hidden">위젯설정안내 상세팝업 보기</span>
								위젯설정안내
							</button>
						</c:if>
						<c:if test='${fn:indexOf(agent, "iPhone") > -1 or fn:indexOf(agent, "iPad") > -1}'>
							<a class="c-box c-box--type3__button u-mr--4" href="https://itunes.apple.com/us/app/kt-mmobail-gogaegsenteo/id1094611503?mt=8" target="_blank">앱 다운로드</a>
							<button class="c-box c-box--type3__button--mint" data-dialog-trigger="#widget-install-dialog">
								<span class="c-hidden">위젯설정안내 상세팝업 보기</span>
								위젯설정안내
							</button>
						</c:if>
					</c:otherwise>
				</c:choose>

				<div class="c-box c-box--type3__image">
					<img class="guide-app-img" src="/resources/images/mobile/content/guide_info_app.png" alt="">
				</div>
			</div>

			<h3 class="c-heading c-heading--type3">주요 기능</h3>
			<ul class="function">
				<li class="function__item">
					<p class="function__text">바로 확인하는<strong class="function__highlight">위젯</strong></p>
					<i class="c-icon c-icon--function1" aria-hidden="true"></i>
				</li>
				<li class="function__item">
					<p class="function__text">실시간으로 확인하는<strong class="function__highlight">사용량 조회</strong></p>
					<i class="c-icon c-icon--function2" aria-hidden="true"></i>
				</li>
				<li class="function__item">
					<p class="function__text">한번에 확인하는<strong class="function__highlight">가입정보 조회</strong></p>
					<i class="c-icon c-icon--function3" aria-hidden="true"></i>
				</li>
				<li class="function__item">
					<p class="function__text">실시간으로 보는<strong class="function__highlight">요금/월별 금액</strong></p>
					<i class="c-icon c-icon--function4" aria-hidden="true"></i>
				</li>
				<li class="function__item">
					<p class="function__text">간편한 요금제<strong class="function__highlight">조회/변경</strong></p>
					<i class="c-icon c-icon--function5" aria-hidden="true"></i>
				</li>
			</ul>
		</div>

		<div class="c-modal" id="widget-install-dialog" role="dialog" tabindex="-1" aria-describedby="#widget-install-title">
		    <div class="c-modal__dialog c-modal__dialog--full" role="document">
		      <div class="c-modal__content">
		        <div class="c-modal__header">
		          <h1 class="c-modal__title" id="widget-install-title">위젯설정안내</h1>
		          <button class="c-button" data-dialog-close>
		            <i class="c-icon c-icon--close" aria-hidden="true"></i>
		            <span class="c-hidden">팝업닫기</span>
		          </button>
		        </div>
		        <div class="c-modal__body">
		          <div class="c-tabs c-tabs--type2" data-tab-active="0">
		            <div class="c-tabs__list c-expand sticky-header--modal u-border-bottom" data-tab-list="">
		              <button class="c-tabs__button" data-tab-header="#tabwidget1-panel">안드로이드폰</button>
		              <button class="c-tabs__button" data-tab-header="#tabwidget2-panel">아이폰</button>
		            </div>
		            <div class="c-tabs__panel border-none" id="tabwidget1-panel">
			          <div class="widget-guide">
			            <div class="widget-guide__item u-pt--20">
			              <span class="widget-guide__step">1</span>
			              <p class="widget-guide__text">설치된 앱 아이콘을 "꾹" 눌러,
			                <br />위젯을 선택해주세요.
			              </p>
			              <div class="widget-guide__image u-mt--24">
			                <img src="/resources/images/mobile/content/img_and_01.png" alt="">
			              </div>
			            </div>
			            <hr class="c-hr c-hr--type3">
			            <div class="widget-guide__item">
			              <span class="widget-guide__step">2</span>
			              <p class="widget-guide__text"> 원하시는 사이즈의 위젯을 추가해주세요.</p>
			              <div class="widget-guide__image u-mt--24">
			                <img src="/resources/images/mobile/content/img_and_02.png" alt="">
			              </div>
			            </div>
			            <hr class="c-hr c-hr--type3">
			            <div class="widget-guide__item u-pb--0">
			              <span class="widget-guide__step">3</span>
			              <p class="widget-guide__text">홈화면에 추가된 위젯을
			                <br />확인하실 수 있습니다.
			              </p>
			              <div class="widget-guide__image u-mt--24">
			                <img src="/resources/images/mobile/content/img_and_03.png" alt="">
			              </div>
			            </div>
			            <div class="widget-guide__item u-pb--0">
			              <div class="c-box c-box--type1">
			                <b class="c-text c-text--type3 u-block u-mt--8">
			                  <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>Tip
			                </b>
			                <ul class="c-text-list c-bullet c-bullet--dot">
			                  <li class="c-text-list__item u-co-gray">
			                  	앱에서 로그인 상태 유지에 체크 후 로그인 하시면 로그아웃을 선택하기 전 까지는 계속 로그인 상태가 유지되어 별도 로그인 없이 위젯을 이용하실 수 있습니다.
			                  	<br />(단, 2주동안 앱을 사용하지 않을 경우 로그인 상태 유지는 해제될 수 있습니다.)
			                  </li>
			                </ul>
			                <div class="widget-guide__image u-mt--30 u-mb--12">
			                  <img class="u-width--73p" src="/resources/images/mobile/content/login.png" alt="">
			                </div>
			              </div>
			            </div>
			            <div class="widget-guide__item u-pb--0">
			              <img src="/resources/images/mobile/content/img_setnumber.png" alt="">
			            </div>
			          </div>
		            </div>
		            <div class="c-tabs__panel border-none" id="tabwidget2-panel">
			          <div class="widget-guide">
			            <div class="widget-guide__item u-pt--20">
			              <span class="widget-guide__step">1</span>
			              <p class="widget-guide__text">바탕화면의 빈영역을 "꾹" 누른 뒤,
			                <br />플러스(+)버튼을 선택하세요.
			              </p>
			              <div class="widget-guide__image u-mt--24">
			                <img src="/resources/images/mobile/content/img_ios_01.png" alt="">
			              </div>
			            </div>
			            <hr class="c-hr c-hr--type3">
			            <div class="widget-guide__item">
			              <span class="widget-guide__step">2</span>
			              <p class="widget-guide__text">위젯 검색화면에서
			              	<br />"kt M모바일"을 선택하세요.
			              </p>
			              <div class="widget-guide__image u-mt--24">
			                <img src="/resources/images/mobile/content/img_ios_02.png" alt="">
			              </div>
			            </div>
			            <hr class="c-hr c-hr--type3">
			            <div class="widget-guide__item">
			              <span class="widget-guide__step">3</span>
			              <p class="widget-guide__text">원하시는 사이즈의 위젯을 추가해주세요.</p>
			              <div class="widget-guide__image u-mt--24">
			                <img src="/resources/images/mobile/content/img_ios_03.png" alt="">
			              </div>
			            </div>
			            <hr class="c-hr c-hr--type3">
			            <div class="widget-guide__item u-pb--0">
			              <span class="widget-guide__step">4</span>
			              <p class="widget-guide__text">완료버튼을 선택하시면,
			              	<br />위젯설정이 완료됩니다.
			              </p>
			              <div class="widget-guide__image u-mt--24">
			                <img src="/resources/images/mobile/content/img_ios_04.png" alt="">
			              </div>
			            </div>
			            <div class="widget-guide__item u-pb--0">
			              <div class="c-box c-box--type1">
			                <b class="c-text c-text--type3 u-block u-mt--8">
			                  <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>Tip
			                </b>
			                <ul class="c-text-list c-bullet c-bullet--dot">
			                  <li class="c-text-list__item u-co-gray">
			                  	앱에서 로그인 상태 유지에 체크 후 로그인 하시면 로그아웃을 선택하기 전 까지는 계속 로그인 상태가 유지되어 별도 로그인 없이 위젯을 이용하실 수 있습니다.
			                  	<br />(단, 2주동안 앱을 사용하지 않을 경우 로그인 상태 유지는 해제될 수 있습니다.)
			                  </li>
			                </ul>
			                <div class="widget-guide__image u-mt--30 u-mb--12">
			                  <img class="u-width--73p" src="/resources/images/mobile/content/login.png" alt="">
			                </div>
			              </div>
			            </div>
			            <div class="widget-guide__item u-pb--0">
			              <img src="/resources/images/mobile/content/img_setnumber.png" alt="">
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