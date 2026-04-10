<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.placeholder.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					다이렉트몰 구매하기
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="upper-banner c-expand">
				<div class="upper-banner__title">
					다이렉트몰에서 <br>유심(USIM) 구매하세요
				</div>
				<a class="c-button c-button--sm upper-banner__anchor" data-dialog-trigger="#purchase-info-dialog">
					유심구매<i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
				</a>
				
				<div class="upper-banner__image">
					<img src="/resources/images/mobile/content/img_upper_banner_card.png" alt="">
				</div>
			</div>
			<div class="banner-guide__box--type2 c-expand">
				<h4 class="ad-headline--small u-co-mint">스마트한 통신생활의 시작</h4>
				<h3 class="ad-headline">
					사용하던 폰 그대로 <br>유심(USIM)만 교체하세요.
				</h3>
				<p class="ad-text">
					휴대폰은 그대로, 요금제는 저렴하게! <br>현재 사용하는 휴대폰의 유심(USIM)만 <br>교체하시면 됩니다.
				</p>
				<a class="c-button c-button--underline c-button--sm u-mt--24" href="/m/requestReView/requestReView.do">사용후기 확인하기</a>
			</div>
			<!-- 배너영역((추후 이미지 추가교체 예정)-->
			<div class="bottom-link__box c-expand">
				<a class="link__anchor" href="/m/mmobile/ktmMobileGuidView.do"> <!--[2022-01-27] 간격조정 유틸 클래스 추가-->
					<b class="c-text c-text--type7 u-mr--8">유심 및 개통 가이드</b> 
					<i class="c-icon c-icon--anchor-white" aria-hidden="true"></i> 
					<img src="/resources/images/mobile/content/img_deco_man.svg" alt="">
				</a>
			</div>
		</div>
		
		<div class="c-modal" id="purchase-info-dialog" role="dialog" tabindex="-1" aria-describedby="#purchase-info-title">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="personal-info-title">구매 전 준비해주세요</h1>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i> 
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body u-pb--00">
						<!--[2021-11-26] 컨텐츠 wrap 추가, 문구 수정 및 간격조정 유틸클래스 추가,  hr 태그 추가, 마크업 수정, 로그인 되었을 경우 버튼 추가(기획/디자인 반영) 시작======-->
						<div class="content-wrap">
							<h3 class="c-heading c-heading--type3 u-mb--12">
								신분증과 <br>계좌/신용카드를 준비해주세요!
							</h3>
							<p class="c-text c-text--type2 u-co-gray">유심 구매 시 본인인증이 진행됩니다.</p>
							<!--[2022-01-26] 클래스 및 이미지 파일명 변경-->
							<div class="c-box u-mt--32">
								<img class="center-img" src="/resources/images/mobile/content/img_id_card.png" alt="">
							</div>
							<hr class="c-hr c-hr--type2 u-mt--20">
							<b class="c-text--type3 c-bullet"> <i
								class="c-icon c-icon--bang--black" aria-hidden="true"></i> 선택 가능 본인인증 방법
							</b>
							<ul class="c-text-list c-bullet c-bullet--dot">
								<!-- [2021-11-29] 폰트 컬러 유틸클래스 추가(디자인 변경 적용)=====-->
								<li class="c-text-list__item u-fw--medium u-co-gray">신용카드, 네이버 인증서, PASS 인증서, toss 인증서</li>
							</ul>
						</div>
						<!--[2021-01-14] 태그 삭제 및 디자인 수정 적용-->
						<!-- case: 로그인 되었을 경우-->
						<div class="c-button-wrap c-button-wrap--column u-mt--48">
							<button class="c-button c-button--full c-button--primary" onclick="location.href='/m/appForm/reqSelfDlvry.do';">구매하기</button>
						</div>
						<!--[$2021-11-26] 컨텐츠 wrap 추가, 문구 수정 및 간격조정 유틸클래스 추가,  hr 태그 추가, 마크업 수정(기획/디자인 반영) 끝======-->
					</div>
				</div>
			</div>
		</div>
	</t:putAttribute>
</t:insertDefinition>