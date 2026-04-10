<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<div class="theft__wrap">
	<h4 class="theft__title">명의도용이란?</h4>
	<hr class="c-hr">
	<p>본인의 동의 없이 <span class="mint">제3자가 본인의 명의</span>로 통신서비스 이용 <span class="mint">계약 등을 체결</span>해 발생하는 피해를 말합니다.</p>
	<h5 class="theft__subtitle">예방 및 대처법</h5>
	<div class="theft-Adv-box">
		<div class="theft-Adv__item">
			<img src="/resources/images/portal/cs/icon_identitytheft_01.png" alt="">
			<div class="theft-Adv__panel">
				<strong>개인정보 제공 금지</strong>
				<p>개인정보(신분증, 휴대폰번호)를 다른사람에게 함부로 제공 금지</p>
			</div>
		</div>
		<div class="theft-Adv__item">
			<img src="/resources/images/portal/cs/icon_identitytheft_02.png" alt="">
			<div class="theft-Adv__panel">
				<strong>신분증 분실 시 신고</strong>
				<p>신분증을 분실한 경우 가까운 주민센터에 즉시 신고</p>
			</div>
		</div>
		<div class="theft-Adv__item">
			<img src="/resources/images/portal/cs/icon_identitytheft_03.png" alt="">
			<div class="theft-Adv__panel">
				<strong>불법 대출 이용 금지</strong>
				<p>이동전화 개통을 조건으로 하는 대출 이용 금지</p>
			</div>
		</div>
		<div class="theft-Adv__item">
			<img src="/resources/images/portal/cs/icon_identitytheft_04.png" alt="">
			<div class="theft-Adv__panel">
				<strong>본인이 가입하지 않은 가입문자를 받았을 경우</strong>
				<ul class="theft-Adv__texlist">
					<li>경찰서를 방문하여 명의도용 신고</li>
					<li>통신민원조정센터(080-3472-119)에 피해 구제 상담</li>
				</ul>
			</div>
		</div>
		<div class="theft-Adv__item">
			<img src="/resources/images/portal/cs/icon_identitytheft_05.png" alt="">
			<div class="theft-Adv__panel">
				<strong>명의도용방지서비스 이용</strong>
				<p>한국정보통신진흥협회 명의도용방지서비스 Msafer(<a class="c-text-link" href="https://www.msafer.or.kr" title="명의도용방지서비스 페이지 이동하기" target="_blank">www.msafer.or.kr</a>) 이용</p>
			</div>
		</div>
	</div>
	<h4 class="theft__title">명의도용방지서비스(Msafer)란?</h4>
	<hr class="c-hr">
	<p>신규로 각종 통신서비스(이동전화, 무선인터넷[WiBro], 유선전화, 초고속인터넷, 인터넷전화[VoIP], 유료방송 등)에 가입하거나 명의변경을 통해 양도 받을 경우 그 사실을 <span class="mint">본인 명의로 사용하고 있는 이동전화 회선을 통해 SMS로 알려주는 무료서비스</span> 입니다.	</p>
	<div class="theft-safer__wrap">
		<img class="theft-safer__img" src="/resources/images/portal/cs/icon_identitytheft_safer.png" alt="명의도용방지 서비스 : 1. 사용자가 유선전화, 이동전화, 무선인터넷, 초고속인터넷, 인터넷전화, 방송서비스를 신규 가입시 개통사실통보. 2. 이동통신사업자에게 개통사실안내 SMS발송을 요청하면 사용자에게 개통사실을 안내. 3. E-mail로 사용자에게 개통사실 안내">
	</div>
	<p class="theft-safer__title">이용방법</p>
	<p>명의도용방지서비스 홈페이지(<a class="c-text-link" href="https://www.msafer.or.kr" title="명의도용방지서비스 페이지 이동하기" target="_blank">www.msafer.or.kr</a>)에서 서비스 신청</p>
	<p class="theft-safer__title">이용 가능한 주요 서비스</p>
	<p>각 서비스는 공동 인증서로 로그인 후 서비스 신청/이용이 가능합니다.</p>
	<p class="theft-safer__subtxt">※ 가입현황조회는 네이버인증, 카카오페이 로그인을 통해서도 이용이 가능합니다.</p>
	<div class="theft-service">
		<p class="theft-service__title">가입사실현황조회 서비스</p>
		<p class="theft-service__txt">본인 명의로 가입된 이동전화, 무선인터넷[WiBro], 유선전화, 초고속인터넷, 인터넷전화[VoIP], 유료방송 등의 통신서비스 현황을 조회일자 기준으로 확인할 수 있는 서비스입니다.&nbsp;
			<a class="c-text-link--bluegreen" href="https://www.msafer.or.kr" title="가입사실현황조회 서비스 페이지 이동하기" target="_blank">신청하러가기</a>
		</p>
		<p class="theft-service__title">가입제한 서비스</p>
		<p class="theft-service__txt">본인 아닌 타인으로 부터 이동전화 신규가입 또는 명의변경 등을 제한하는 서비스로 사전에 가입을 제한할 수 있습니다.&nbsp;
			<a class="c-text-link--bluegreen" href="https://www.msafer.or.kr" title="가입제한 서비스 페이지 이동하기" target="_blank">신청하러가기</a>
		</p>
		<p class="theft-service__title">이메일안내 서비스</p>
		<p class="theft-service__txt">본인 명의로 각종 통신서비스(이동전화, 무선인터넷[WiBro], 유선전화, 초고속인터넷, 인터넷전화[VoIP], 유료방송 등)에 신규로 가입하거나 명의변경을 통해 양도받을 경우 그 사실을 e-mail로 알려주는 서비스입니다. (SMS는 별도 신청없이 자동 발송)&nbsp;
			<a class="c-text-link--bluegreen" href="https://www.msafer.or.kr" title="이메일안내 서비스 페이지 이동하기" target="_blank">신청하러가기</a>
		</p>
	</div>
</div>