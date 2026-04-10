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
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/mobile/cs/guide/serviceGuide.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					고객센터 안내
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-box c-box--type3">
				<strong class="c-box c-box--type3__title">고객님! 무엇을 도와드릴까요?</strong>
				<p class="c-box c-box--type3__text">
					kt M모바일 가입 및 서비스 이용에 대해 <br>궁금한 사항을 도와드리는 kt M모바일 <br>고객센터 입니다.
				</p>
				<div class="c-box c-box--type3__image">
					<img src="/resources/images/mobile/content/guide_info_cs.png" alt="">
				</div>
			</div>

			<h3 class="c-heading c-heading--type3">유용한 서비스</h3>
	        <div class="c-button-wrap c-button-wrap--column service">
	          <a class="c-button c-button--md c-button--white loding" href="/m/rate/rateList.do">
	            <img src="/resources/images/mobile/common/ico_coin.svg" alt="">
	            <span>요금제</span>
	            <span class="c-button__sub">
	              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	            </span>
	          </a>
	          <a class="c-button c-button--md c-button--white loding" href="/m/rate/adsvcGdncList.do">
	            <img src="/resources/images/mobile/common/ico_plus_phone.svg" alt="">
	            <span>부가서비스</span>
	            <span class="c-button__sub">
	              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	            </span>
	          </a>
	          <a class="c-button c-button--md c-button--white loding" href="/m/mmobile/ktmMobileUsimGuidView.do">
	            <img src="/resources/images/mobile/common/ico_usim_chip.svg" alt="">
	            <span>개통 가이드</span>
	            <span class="c-button__sub">
	              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	            </span>
	          </a>
	        </div>

			<h3 class="c-heading c-heading--type3">온라인 셀프 서비스</h3>
	        <p class="c-text c-text--type3 u-mt--40">요금조회/납부</p>
	        <div class="c-button-wrap u-mt--20">
			  <a class="c-button c-button--icon loding" href="/m/mypage/myinfoView.do">
			    <i class="c-icon c-icon--cs-joininfo" aria-hidden="true"></i>
				<span>가입정보</span>
			  </a>
			  <a class="c-button c-button--icon loding" href="/m/mypage/billWayReSend.do">
				<i class="c-icon c-icon--cs-bill" aria-hidden="true"></i>
				<span>요금명세서</span>
			  </a>
		    </div>
		    <div class="c-button-wrap u-mt--8">
			  <a class="c-button c-button--icon loding" href="/m/mypage/chargeView05.do">
				<i class="c-icon c-icon--cs-chgpay" aria-hidden="true"></i>
				<span>납부방법변경</span>
			  </a>
			  <a class="c-button c-button--icon loding" href="/m/mypage/unpaidChargeList.do">
				<i class="c-icon c-icon--cs-immpay" aria-hidden="true"></i>
				<span>즉시납부</span>
			  </a>
		    </div>
		    <p class="c-text c-text--type3 u-mt--40">서비스 신청/변경</p>
	        <div class="c-button-wrap u-mt--20">
		      <a class="c-button c-button--icon loding" href="/m/mypage/farPricePlanView.do">
		      <i class="c-icon c-icon--cs-chgrate" aria-hidden="true"></i>
		      <span>요금제 변경</span>
		      </a>
		      <a class="c-button c-button--icon loding" href="/m/mypage/regServiceView.do">
		      <i class="c-icon c-icon--cs-extraservice" aria-hidden="true"></i>
			  <span>부가서비스</span>
		      </a>
		    </div>
		    <div class="c-button-wrap u-mt--8">
			  <a class="c-button c-button--icon loding" href="/m/mypage/suspendView01.do">
			    <i class="c-icon c-icon--cs-pause" aria-hidden="true"></i>
			    <span>일시정지</span>
			  </a>
			  <a class="c-button c-button--icon loding" href="/m/mypage/lostView.do">
			    <i class="c-icon c-icon--cs-lost" aria-hidden="true"></i>
			    <span>분실신고</span>
			  </a>
		    </div>
		    <p class="c-bullet c-bullet--fyr u-co-gray u-mt--17">
			  마이페이지에서 더 많은 서비스를 직접 조회/신청/변경할 수 있습니다.
			  <a class="c-text-link--bluegreen loding" href="/m/mypage/myinfoView.do">마이페이지 바로가기</a>
		    </p>

			<h3 class="c-heading c-heading--type3">자주묻는 질문</h3>
			<div class="c-button-wrap c-button-wrap--right">
				<a class="c-button c-button--xsm" href="/m/cs/faqList.do">
					<span class="c-hidden">바로가기</span>
					<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
				</a>
			</div>
			<c:if test="${faqTopList !=null and !empty faqTopList}">
				<div class="c-accordion c-accordion--type1 faq-accordion">
					<ul class="c-accordion__box auto-numbering">
						<c:forEach items="${faqTopList}" var="faqTopList">
							<li class="c-accordion__item faqTop" boardSeq="${faqTopList.boardSeq}">
								<div class="c-accordion__head" data-acc-header>
									<button class="c-accordion__button">
										<div class="c-accordion__title">${faqTopList.boardSubject}</div>
									</button>
								</div>
								<div class="c-accordion__panel c-expand expand">
									<div class="c-accordion__inside box-answer">
										<div class="box-prefix">A</div>
										<div class="box-content">${faqTopList.boardContents}</div>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
			<h3 class="c-heading c-heading--type3">kt M모바일 고객센터</h3>
			<div class="c-button-wrap c-button-wrap--right">
				<button class="c-button c-button--xsm c-button--white" onclick="openPage('pullPopup','/m/cs/serviceGuideArsHtml.do','');">ARS 메뉴 안내</button>
			</div>
			<div class="c-button-wrap c-button-wrap--column">
				<a class="c-button c-button--md c-button--white" href="tel:114">
					<i class="c-icon c-icon--tel" aria-hidden="true"></i>
					<span>kt M모바일 휴대폰</span>
					<span class="c-button__sub">114
						<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
					</span>
				</a>
				<a class="c-button c-button--md c-button--white" href="tel:1899-5000">
					<i class="c-icon c-icon--tel" aria-hidden="true"></i>
					<span>타사 휴대폰 및 유선전화</span>
					<span class="c-button__sub">1899-5000
						<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
					</span>
				</a>
			</div>

			<p class="c-bullet c-bullet--fyr u-co-gray">고객센터 운영시간 : 09 ~ 12시 / 13 ~ 18시 토요일, 일요일, 공휴일은 분실, 정지 신고 및 통화품질 관련 상담만 가능</p>
			<p class="c-bullet c-bullet--fyr u-co-gray">주소 : 경기 안양시 만안구 안양로 331 (교보생명빌딩, 6층)</p>
			<h3 class="c-heading c-heading--type3">선불 충전서비스</h3>
			<div class="c-button-wrap c-button-wrap--column">
				<a class="c-button c-button--md c-button--white" href="tel:080-013-0114">
					<i class="c-icon c-icon--tel" aria-hidden="true"></i>
					<span>선불충전</span>
					<span class="c-button__sub">080-013-0114
						<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
					</span>
				</a>
				<a class="c-button c-button--md c-button--white" href="tel:080-012-0114">
					<i class="c-icon c-icon--tel" aria-hidden="true"></i>
					<span>선불잔액조회</span>
					<span class="c-button__sub">080-012-0114
						<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
					</span>
				</a>
			</div>
			<p class="c-bullet c-bullet--fyr u-co-gray">1588, 1599 등 유사 대표번호로 발신하지 않도록 주의 부탁드립니다.</p>
		</div>
	</t:putAttribute>
</t:insertDefinition>