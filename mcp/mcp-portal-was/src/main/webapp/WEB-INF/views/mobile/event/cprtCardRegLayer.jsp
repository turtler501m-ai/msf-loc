<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<div class="c-modal__content">
	<div class="c-modal__header">
    	<h1 class="c-modal__title" id="join-guide-title">이용 가이드</h1>
        <button class="c-button" data-dialog-close>
        	<i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>
    <div class="c-modal__body">
    	<h3 class="c-heading c-heading--type1">제휴카드 이용 및 등록 안내</h3>
        <div class="join-info u-ta-center">
        	<div class="c-box c-box--type3">
            	<span class="c-box c-box--type3__label">01</span>
              	<strong class="c-box c-box--type3__title">kt M모바일 제휴카드 발급</strong>
              	<div class="c-box c-box--type3__image">
                	<img src="/resources/images/mobile/content/img_join_info1.png" alt="">
              	</div>
            </div>
            <div class="c-box c-box--type4 u-ta-left u-mt--24">
            	<i class="c-icon c-icon--label-tip u-mt--m4" aria-hidden="true"></i>
              	<b class="c-text c-text--type4 u-ml--8">제휴카드 발급 꿀팁!</b>
              	<p class="c-text c-text--type4 u-fw--medium u-mt--16">롯데 제휴카드는 즉시발급 가능</p>
              	<ul class="c-text-list u-mt--12">
                	<li class="c-text-list__item u-co-gray">① kt M모바일 제휴카드 페이지에서 카드신청<span class="u-block u-ml--16 u-mt--4">※카드 신청 시 앱카드를 함께 신청 필수</span></li>
                	<li class="c-text-list__item u-co-gray">② 카드 수령 전 카드사 앱에서 앱카드 등록</li>
                	<li class="c-text-list__item u-co-gray">③ 카드사 앱에서 카드번호 조회</li>
                	<li class="c-text-list__item u-co-gray">④ 카드번호 확인 후 통신료 자동납부 신청</li>
              	</ul>
              	<p class="c-bullet c-bullet--fyr u-co-sub-4 u-mt--16">상세한 카드발급 안내는 해당 카드 홈페이지 참조</p>
            </div>
            <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
            <div class="c-box c-box--type3">
            	<span class="c-box c-box--type3__label">02</span>
              	<strong class="c-box c-box--type3__title"> 발급받은 카드로 통신료 자동이체수단 변경</strong>
              	<p class="c-text c-text--type3">
                	<span class="c-text c-text--type3">마이페이지 &gt;</span>
                	<a class="c-button c-button--underline c-button--sm" href="/m/mypage/chargeView05.do">납부방법 변경</a>
              	</p>
              	<div class="c-box c-box--type3__image">
              		<img src="/resources/images/mobile/content/img_join_info2.png" alt="">
              	</div>
            </div>
            <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
            <div class="c-box c-box--type3">
              	<span class="c-box c-box--type3__label">03</span>
              	<strong class="c-box c-box--type3__title">제휴카드 전월 실적 충족 시 통신료 할인</strong>
              	<div class="c-box c-box--type3__image">
                	<img src="/resources/images/mobile/content/img_join_info3.png" alt="">
              	</div>
            </div>
        </div>
        <%-- 링크 버튼 --%>
        <c:if test="${!empty linkList}">
        	<c:forEach var="linkInfo" items="${linkList}">
        		<div class="c-button-wrap">
	            	<a class="c-button c-button--lg c-button--primary card-issuance-btn" href="#" onclick="pageLink('${linkInfo.linkUrl}');">${linkInfo.linkNm}</a>
	          	</div>
        	</c:forEach>
        </c:if>
        <%-- 가입가이드 (CO0007 : PCARD205) --%>
        <c:forEach var="dtl" items="${dtlList}">
			<c:if test="${dtl.cprtCardItemCd eq 'PCARD205'}">
				<h4 class="c-heading c-heading--fs16 u-mt--48">카드문의</h4>
          		<p class="c-bullet c-bullet--dot u-co-gray">${dtl.cprtCardItemSbst}</span></p>
			</c:if>
		</c:forEach>
	</div>
</div>
