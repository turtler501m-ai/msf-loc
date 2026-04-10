<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/mobile/mypage/recForm.js"></script>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					회원탈퇴 신청
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="recForm-info">
				<h3>회원탈퇴 안내사항</h3>
				<ul>
					<li>탈퇴 즉시 회원님의 온라인 정보는 삭제 됩니다.</li>
					<li>사용하셨던 해당 아이디는 회원 탈퇴 후 사용이 불가능 합니다.</li>
					<li>회원탈퇴 이후 재가입은 바로 가능 합니다.</li>
					<li>탈퇴처리 이후에는 어떠한 방법으로도 회원님의 개인정보를 복원할 수 없습니다.</li>
				</ul>
			</div>
			<div class="c-form u-mt--32">
				<div class="c-form__input">
					<input class="c-input" id="inpName" type="text" placeholder="아이디 입력" value="${searchVO.userName}" readonly>
					<label class="c-form__label" for="inpName">아이디</label>
					<input type="text" id="userId" value="${searchVO.userName}" hidden />
				</div>
				<div class="c-form__input">
					<input class="c-input" id="pw" name="pw" type="password" placeholder="비밀번호 입력" maxlength="20">
					<label class="c-form__label" for="inpName">비밀번호</label>
				</div>
			</div>
			<div class="c-form">
				<span class="c-form__title">탈퇴사유<span class="c-form__sub">(필수)</span></span>
				<div class="c-check-wrap c-check-wrap--column">
					<input class="c-radio" id="secedeReason01" type="radio" name="secedeReason" value="01">
					<label class="c-label" for="secedeReason01">다른 ID 변경</label>
					<input class="c-radio" id="secedeReason02" type="radio" name="secedeReason" value="02">
					<label class="c-label" for="secedeReason02">시스템 장애(속도 저조, 잦은 에러 등)</label>
					<input class="c-radio" id="secedeReason03" type="radio" name="secedeReason" value="03">
					<label class="c-label" for="secedeReason03">개인정보(통신 및 신용정보)의 유출 우려</label>
					<input class="c-radio" id="secedeReason04" type="radio" name="secedeReason" value="04">
					<label class="c-label" for="secedeReason04">이용빈도 저조</label>
					<input class="c-radio" id="secedeReason05" type="radio" name="secedeReason" value="05">
					<label class="c-label" for="secedeReason05">타사로 전환하기 위해서</label>
					<input class="c-radio" id="secedeReason06" type="radio" name="secedeReason" value="06">
					<label class="c-label" for="secedeReason06">알뜰 서비스 중단</label>
					<input class="c-radio" id="secedeReason07" type="radio" name="secedeReason" value="07">
					<label class="c-label" for="secedeReason07">서비스 불만족</label>
					<input class="c-radio" id="secedeReason08" type="radio" name="secedeReason" value="08">
					<label class="c-label" for="secedeReason08">기타</label>
				</div>
				<div class="c-form" id="reasonDesc" style="display: none;">
					<div class="c-input-wrap c-input-wrap--textinp u-mt--16">
						<label class="c-label c-hidden">기타 사유</label>
						<input class="c-input" type="text" id="secedeReasonDesc" name="secedeReasonDesc" placeholder="기타 사유를 입력해주세요">
					</div>
				</div>
			</div>
			<div class="c-button-wrap u-mt--48">
				<button class="c-button c-button--lg c-button--primary c-button--full" id="certBtn">확인</button>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>