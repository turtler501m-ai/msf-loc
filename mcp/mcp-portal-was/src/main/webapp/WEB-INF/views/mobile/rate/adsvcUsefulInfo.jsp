<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					알면 유용한 부가서비스
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="usefuladdsvc-banner">
				<div class="usefuladdsvc-banner--wrap">
					<strong>
						엠모바일에서 추천하는<br/>알면 유용한 서비스!
					</strong>
				</div>
				<div class="usefuladdsvc-banner--img">
					<img src="/resources/images/mobile/product/usefuladdsvc-banner_bg_mo.png" alt="">
				</div>
			</div>
			<div class="usefuladdsvc-connect">
				<div class="usefuladdsvc-list">
					<h4 class="usefuladdsvc-list__title">따로 신청할 필요 없이 알아서 제공하는 서비스</h4>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_01.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>긴급 구조 위치 제공 서비스</strong>
								<p>고객님이 위급할 땐 언제나!<br/>긴급상황 발생 시 소방방재청으로 위치 공유</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_02.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>스팸차단 서비스</strong>
								<p>귀찮고 쓸데없는 문자는 알아서 걸러주는<br/>불필요한 스팸 자동 차단</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_03.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>정보제공사업자 번호 차단 서비스</strong>
								<p>대출권유나 보험 안내 전화는 이제 그만!<br/>16XX, 15XX 등의 사업자 번호를 일괄 차단</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-more">
						<a href="/m/rate/adsvcGdncList.do?rateAdsvcCtgCd=100" title="부가서비스 안내 페이지 이동하기">더 많은 서비스를 알고 싶으시다면</a>
					</div>
				</div>
				<div class="usefuladdsvc-list">
					<h4 class="usefuladdsvc-list__title">부담없이 무료로 사용하실 수 있는 필수 서비스</h4>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_04.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>불법 TM 수신차단 서비스</strong>
								<p>받기싫은 광고 전화는 이제 그만!<br/>TM 전화를 최대 50개까지 등록하고 차단</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_05.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>번호도용 문자 차단 서비스</strong>
								<p>누군가 내 번호를 몰래 도용해서 쓴다면?<br/>타인이 임의로 내 번호를 사용해 문자 발송을 차단</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_06.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>정보보호 알리미</strong>
								<p>내 폰이 해킹을 당하거나 보이스 피싱에 당한다면? 한국인터넷진흥원과 함께 해킹 등 사고발생 시 대처방법을 안내 발송</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-more">
						<a href="/m/rate/adsvcGdncList.do?rateAdsvcCtgCd=101" title="부가서비스 안내 페이지 이동하기">더 많은 서비스를 알고 싶으시다면</a>
					</div>
				</div>
				<div class="usefuladdsvc-list">
					<h4 class="usefuladdsvc-list__title">내 마음대로 필요한 기능만 골라쓰는 유료 서비스</h4>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_07.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>캐치콜</strong>
								<p>중요한 전화는 놓치지 않고 캐치.<br/>전화를 받지 못할때 문자로 안내해주는 서비스</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_08.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>착신 전환</strong>
								<p>핸드폰이 2개여도 하나로 바로 받자!<br/>2개의 핸드폰을 사용할 경우 전화를 자동으로 착신 전환</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_09.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>특정번호 수신차단 서비스</strong>
								<p>받기싫은 전화, 문자메세지가 있다면??<br/>음성 및 SMS를 최대 100개까지 번호를 지정 하여 차단</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-more">
						<a href="/m/rate/adsvcGdncList.do?rateAdsvcCtgCd=102" title="부가서비스 안내 페이지 이동하기">더 많은 서비스를 알고 싶으시다면</a>
					</div>
				</div>
				<div class="usefuladdsvc-list">
					<h4 class="usefuladdsvc-list__title">한국에서 쓰는 것 처럼 전원만 켜서 바로 사용하는 로밍 서비스</h4>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_10.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>로밍 데이터 함께ON</strong>
								<p>국가별로 지정된 데이터만큼 사용하는<br/>합리적인 로밍 상품</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_11.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>데이터로밍 하루종일ON</strong>
								<p>전세계에서 하루종일 데이터를<br/>무제한으로!</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-list__item">
						<div class="usefuladdsvc-list__box">
							<img src="/resources/images/common/product_usefuladdsvc_icon_12.svg" alt="">
							<div class="usefuladdsvc-list__panel">
								<strong>데이터로밍 하루종일ON 플러스</strong>
								<p>전세계에서 하루종일 데이터를 더욱 빠른<br/>속도로 무제한 사용</p>
							</div>
						</div>
					</div>
					<div class="usefuladdsvc-more">
						<a href="/m/rate/adsvcGdncList.do?rateAdsvcCtgCd=129" title="부가서비스 안내 페이지 이동하기">더 많은 서비스를 알고 싶으시다면</a>
					</div>
				</div>
			</div>

	    </div>
	</t:putAttribute>

</t:insertDefinition>
