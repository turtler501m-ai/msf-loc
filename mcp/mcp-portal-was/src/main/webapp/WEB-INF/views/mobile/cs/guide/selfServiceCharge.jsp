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

		<div class="ly-content selfservice" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					요금조회 및 납부
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="selfservice-content">
				<div class="c-filter c-filter--accordion c-expand">
		            <button class="c-filter--accordion__button">
		            	<div class="c-hidden">필터 펼치기</div>
		          	</button>
		          	<div class="c-filter__inner">
		            	<a class="c-button" href="#charge" data-scroll-top="#charge|62">요금조회</a>
	                    <a class="c-button" href="#payment" data-scroll-top="#payment|62">납부방법 변경</a>
	                    <a class="c-button" href="#unpaidCharge" data-scroll-top="#unpaidCharge|62">즉시납부</a>
	                    <a class="c-button" href="#billWayChg" data-scroll-top="#billWayChg|62">명세서 정보변경</a>
	                    <a class="c-button" href="#mPay" data-scroll-top="#mPay|62">소액결제 조회/변경</a>
		          	</div>
		        </div>
                <div class="selfservice-head chg">
				    <h3 class="selfservice-head__title">온라인 셀프 서비스</h3>
					<p class="selfservice-head__text">
						고객센터로 연락할 필요 없이<br />
						 마이페이지/App에서 서비스를 직접<br />
						 간편하고 빠르게 확인/변경 해 보세요.
					</p>
				</div>
				<div class="selfservice-list-wrap chg">
					<div class="selfservice-list__item" id="charge">
						<div class="selfservice-list__img">
							<img src="/resources/images/mobile/cs/selfservice_chg_01.png" alt="App 설치 후 위젯을 설정하시면 사용량도 바로 확인이 가능합니다.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>요금조회</h4>
								<ul>
						            <li>
						            	이번달 청구요금을 확인 해 보세요.<br/>
										App 설치 후 위젯을 설정하시면 사용량도 바로 확인이 가능합니다.
									</li>
						        </ul>
					        	<div class="selfservice-list__btn-wrap">
							        <button data-dialog-trigger="#widget-install-dialog">위젯 설정 안내</button>
							        <a href="/m/mypage/chargeView01.do" title="요금조회 바로가기">요금조회 바로가기</a>
						        </div>
					        </div>
						</div>
					</div>
					<div class="selfservice-list__item" id="payment">
						<div class="selfservice-list__img">
							<img src="/resources/images/mobile/cs/selfservice_chg_02.png" alt="이용 요금 납부방법을 계좌 자동이체 또는 신용카드 자동 결제로 변경할 수 있습니다.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>납부방법 변경</h4>
								<ul>
						            <li>
						            	이용 요금 납부방법을 계좌 자동이체 또는 신용카드 자동 결제로 변경할 수 있습니다.
									</li>
						        </ul>
						        <div class="selfservice-list__btn-wrap">
							        <a href="/m/mypage/chargeView05.do" title="납부방법 변경 바로가기">납부방법 변경 바로가기</a>
						        </div>
					        </div>
						</div>
					</div>
					<div class="selfservice-list__item" id="unpaidCharge">
						<div class="selfservice-list__img">
							<img src="/resources/images/mobile/cs/selfservice_chg_03.png" alt="신용카드, 카카오페이, 페이코, 네이버페이로 청구된 요금을 바로 납부 가능합니다.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>즉시납부</h4>
								<ul>
						            <li>
						            	신용카드, 카카오페이, 페이코, 네이버페이로 청구된 요금을 바로 납부 가능합니다.
									</li>
						        </ul>
						        <div class="selfservice-list__btn-wrap">
							        <a href="/m/mypage/unpaidChargeList.do" title="즉시납부 바로가기">즉시납부 바로가기</a>
						        </div>
					        </div>
						</div>
					</div>
					<div class="selfservice-list__item" id="billWayChg">
						<div class="selfservice-list__img">
							<img src="/resources/images/mobile/cs/selfservice_chg_04.png" alt="현재 받고 있는 명세서 정보를 확인 또는 명세서 수령 방법을 이메일, MMS로 변경할 수 있어요.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>명세서 정보변경</h4>
								<ul>
						            <li>
						            	현재 받고 있는 명세서 정보를 확인 또는 명세서 수령 방법을 이메일, MMS로 변경할 수 있어요.
									</li>
						        </ul>
						        <div class="selfservice-list__btn-wrap">
							        <a href="/m/mypage/billWayChgView.do" title="명세서 정보변경 바로가기">명세서 정보변경 바로가기</a>
						        </div>
					        </div>
						</div>
					</div>
					<div class="selfservice-list__item" id="mPay">
						<div class="selfservice-list__img">
							<img src="/resources/images/mobile/cs/selfservice_chg_05.png" alt="휴대폰으로 결제하신 내역을 확인할 수 있는 서비스로 한도 조정도 가능합니다.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>소액결제 조회/변경</h4>
								<ul>
						            <li>
						            	휴대폰으로 결제하신 내역을 확인할 수 있는 서비스로 한도 조정도 가능합니다.
									</li>
						        </ul>
						        <div class="selfservice-list__btn-wrap">
							        <a href="/m/mypage/mPayView.do" title="소액결제 조회 바로가기">소액결제 조회 바로가기</a>
						        </div>
					        </div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="c-modal" id="widget-install-dialog" role="dialog" tabindex="-1" aria-describedby="#widget-install-title">
		    <div class="c-modal__dialog c-modal__dialog--full" role="document">
		      <div class="c-modal__content">
		        <div class="c-modal__header">
		          <h1 class="c-modal__title" id="widget-install-title">위젯 설정 안내</h1>
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