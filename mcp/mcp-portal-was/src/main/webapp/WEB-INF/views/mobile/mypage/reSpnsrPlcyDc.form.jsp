<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/mypage/reSpnsrPlcyDc.form.js"></script>
        <script type="text/javascript">
		history.pushState(null, null,"/m/mypage/reSpnsrPlcyDc.do");
		window.onpopstate = function (event){
			history.go("/m/mypage/reSpnsrPlcyDc.do");
		}
	    </script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<form name="frm" id="frm" action="/m/mypage/moscSdsSvcRegView.do" method="post">
	    <input type="hidden" name="phoneNum" id="phoneNum" value="${phoneNum}">
	    <input type="hidden" name="svcCntrNo" id="svcCntrNo" value="" >
	    <input type="hidden" name="rateSoc" value="${mcpUserCntrMngDto.soc}"  >
	    <input type="hidden" id="mCode" name="mCode" value="rate"  >
	    <input type="hidden" id="ncn" name="ncn" value=""  >
	    <input type="hidden" id="engtPerd" name="engtPerd" value=""  >
	    </form>
		<div class="ly-content" id="main-content">
			<input type="hidden" id="rateSoc" name="rateSoc" value="${mcpUserCntrMngDto.soc}"  >
            <input type="hidden" id="certifyYn" name="certifyYn"  value=""/>
            <input type="hidden" id="isAuth" name="isAuth"  >
            <input type="hidden" id="isDlvrySele" name="isDlvrySele"  >

			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					요금할인 재약정 신청
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
			<%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
			<div class="c-form">
				<span class="c-form__title u-mt--40">가입정보</span>
				<div class="info-box">
					<dl>
						<dt>고객명</dt>
						<dd>${searchVO.userName}</dd>
					</dl>
					<dl>
						<dt>휴대폰번호</dt>
						<dd>${ searchVO.ctn }</dd>
					</dl>
					<dl>
						<dt>요금제</dt>
						<dd>${mcpUserCntrMngDto.rateNm }</dd>
					</dl>
				</div>
				<span class="c-form__title">약정기간 선택</span>
				<div class="c-check-wrap">
					<div class="c-chk-wrap">
						<input class="c-radio c-radio--button" id="instNom01" name="instNom" value="12" type="radio" checked ="checked"/>
						<label class="c-label" for="instNom01">12개월</label>
					</div>
					<div class="c-chk-wrap">
						<input class="c-radio c-radio--button" id="radTerm2"  name="instNom" value="24" type="radio" />
						<label class="c-label" for="radTerm2">24개월</label>
					</div>
				</div>
			</div>
			<div class="c-button-wrap u-mt--48">
				<button class="c-button c-button--full c-button--primary" data-dialog-trigger="#chk-inquiry-dialog" id="svcBtn" style="display:none;"></button>
				<button class="c-button c-button--full c-button--primary" id="btnSvcPreChk">약정 가능 여부 조회</button>
			</div>
		</div>

		<div class="c-modal" id="chk-inquiry-dialog" role="dialog" tabindex="-1" aria-describedby="#chk-inquiry-title">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="chk-inquiry-title">약정 가능 여부 결과</h1>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i> <span
								class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body">
						<div class="c-form">
							<span class="c-form__title" id="srchRstHArea">조회 결과</span>
							<div class="info-box" id="divResult">
								<dl>
									<dt>할인전 월 기본료</dt>
									<dd></dd>
								</dl>
								<dl>
									<dt>재약정 할인금액</dt>
									<dd></dd>
								</dl>
								<dl>
									<dt>재약정 시 월 통신료</dt>
									<dd></dd>
								</dl>
								<dl>
									<dt>
										총 추가 할인금액 <br>(약정기간 기준)
									</dt>
									<dd></dd>
								</dl>
							</div>
							<div class="c-button-wrap u-mt--48">
								<button class="c-button c-button--full c-button--primary" data-dialog-close onclick="goMosc();">약정 조건 재약정</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</t:putAttribute>
</t:insertDefinition>