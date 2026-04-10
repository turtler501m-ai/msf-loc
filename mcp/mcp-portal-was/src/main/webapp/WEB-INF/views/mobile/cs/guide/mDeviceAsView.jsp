<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>


<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					단말기 A/S 안내
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<h3 class="c-heading c-heading--type3">단말 불량(A/S 안내)</h3>
			<p class="c-text c-text--type3">단말 불량 시 각 제조사(중고폰/리퍼폰의 경우 공급사)의 A/S 센터를 통해 수리가 가능합니다.</p>
			<ul class="c-text-list c-bullet c-bullet--dot">
				<li class="c-text-list__item">각 제조사의 AS센터(바로가기)를 누르신 후 지역 별 센터의 위치/전화번호를 확인하시기 바랍니다.</li>
				<li class="c-text-list__item">각 제조사(공급사)의 AS센터(바로가기 있는 경우 해당 사이트 연결)를 확인하시기 바랍니다.</li>
				<li class="c-text-list__item">중고폰/리퍼폰 공급사 확인이 필요하실 경우에는 고객센터(1899-5000)에서 확인 가능합니다.</li>
			</ul>
			<div class="c-table">
				<table>
					<caption>단말기 A/S 안내</caption>
					<colgroup>
						<col style="width: 42%">
						<col style="width: 58%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">구분</th>
							<th scope="col">품질보증 기간 내</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="row">사용자 부주의에 의한 파손 및 침수</th>
							<td>유상</td>
						</tr>
						<tr>
							<th class="u-ta-center" scope="row">단말기 자체 불량</th>
							<td class="u-ta-center">무상</td>
						</tr>
					</tbody>
				</table>
			</div>
			<h3 class="c-heading c-heading--type3">제조사</h3>
			<ul class="c-list c-list--type1">
				<li class="c-list__item">
					<div class="c-list__image">
						<img src="/resources/images/mobile/logo/ico_samsung.png" alt="삼성">
					</div>
					<a class="c-list__anchor c-list__anchor--icon" href="http://www.samsungsvc.co.kr" target="_blank" title=“새창”>
						<strong class="c-list__title u-mb--20">삼성</strong>
						<a class="c-list__sub c-list__sub--tel" href="tel:1899-3666">
						<i class="c-icon c-icon--call" aria-hidden="true"></i>A/S센터 1588-3366</a>
					</a>
				</li>
				<li class="c-list__item">
					<div class="c-list__image">
						<img src="/resources/images/mobile/logo/ico_lg.png" alt="LG">
					</div>
					<a class="c-list__anchor c-list__anchor--icon" href="https://www.lgservice.co.kr" target="_blank" title=“새창”>
						<strong class="c-list__title u-mb--20">LG</strong>
						<a class="c-list__sub c-list__sub--tel" href="tel:1544-7777">
							<i class="c-icon c-icon--call" aria-hidden="true"></i>A/S센터 1544-7777
						</a>
					</a>
				</li>
				<li class="c-list__item">
					<div class="c-list__image">
						<img src="/resources/images/public/logo/logo_xiaomi.svg" alt="샤오미 로고">
					</div>
					<a class="c-list__anchor c-list__anchor--icon" href="https://m.arootmall.com/board/as%EC%95%88%EB%82%B4/7/" target="_blank" title=“새창”>
						<strong class="c-list__title u-mb--20">샤오미</strong>
						<a class="c-list__sub c-list__sub--tel" href="tel:1566-8106">
							<i class="c-icon c-icon--call" aria-hidden="true"></i>A/S센터 1566-8106
						</a>
					</a>
				</li>
				<li class="c-list__item">
					<div class="c-list__image">
						<img src="/resources/images/mobile/logo/ico_aroot.png" alt="에이루트 로고">
					</div>
					<a class="c-list__anchor c-list__anchor--icon" href="https://m.arootmall.com/board/as%EC%95%88%EB%82%B4/7/" target="_blank" title=“새창”>
						<strong class="c-list__title u-mb--20">에이루트</strong>
						<a class="c-list__sub c-list__sub--tel" href="tel:1644-5380">
							<i class="c-icon c-icon--call" aria-hidden="true"></i>A/S센터 1644-5380
						</a>
					</a>
				</li>
				<li class="c-list__item">
					<div class="c-list__image">
						<img src="/resources/images/mobile/logo/ico_twonumber.png" alt="투넘버">
					</div>
					<a class="c-list__anchor c-list__anchor--icon" href="https://twonumber.com" target="_blank" title=“새창”>
						<strong class="c-list__title u-mb--20">투넘버(팬택)</strong>
						<a class="c-list__sub c-list__sub--tel" href="tel:1688-9256">
							<i class="c-icon c-icon--call" aria-hidden="true"></i>A/S센터 1688-9256
						</a>
					</a>
				</li>
				<li class="c-list__item">
                    <div class="c-list__image">
                        <img src="/resources/images/mobile/logo/img_logo_zte.png" alt="zte 로고">
                    </div>
                    <a class="c-list__anchor c-list__anchor--icon" href="http://ztedevice.co.kr/bbs/board.php?bo_table=21_4" target="_blank" title=“새창”>
                        <strong class="c-list__title u-mb--20">ZTE</strong>
                        <a class="c-list__sub c-list__sub--tel" href="tel:1688-9256">
                            <i class="c-icon c-icon--call" aria-hidden="true"></i>A/S센터 1666-6016
                        </a>
                    </a>
                </li>
			</ul>
			<h3 class="c-heading c-heading--type3">중고폰</h3>
			<ul class="c-list c-list--type1">
				<li class="c-list__item">
					<div class="c-list__image">
						<img src="/resources/images/mobile/logo/ico_acl.png" alt="ACL">
					</div>
					<a class="c-list__anchor c-list__anchor--icon" href="http://www.aclife.co.kr" target="_blank">
						<strong class="c-list__title u-mb--20">(주)ACL</strong>
						<a class="c-list__sub c-list__sub--tel" href="tel:032-503-7903">
							<i class="c-icon c-icon--call" aria-hidden="true"></i>032-503-7903
						</a>
					</a>
				</li>
				<li class="c-list__item">
					<div class="c-list__image">
						<img src="/resources/images/mobile/logo/ico_ecotrade.png" alt="에코트레이드">
					</div>
					<a class="c-list__anchor c-list__anchor--icon" href="https://eco-trade.co.kr" target="_blank">
						<strong class="c-list__title u-mb--20">(주)에코트레이드</strong>
						<a class="c-list__sub c-list__sub--tel" href="tel:1551-2343">
							<i class="c-icon c-icon--call" aria-hidden="true"></i>1551-2343
						</a>
					</a>
				</li>
			</ul>
			<h3 class="c-heading c-heading--type3">리퍼폰</h3>
			<ul class="c-list c-list--type1">
				<li class="c-list__item">
					<div class="c-list__image">
						<img src="/resources/images/mobile/logo/ico_mobillion.png" alt="모빌라이언">
					</div>
					<a class="c-list__anchor c-list__anchor--icon">
						<strong class="c-list__title u-mb--20">모빌라이언</strong>
						<a class="c-list__sub c-list__sub--tel" href="tel:010-7646-8871">
							<i class="c-icon c-icon--call" aria-hidden="true"></i>010-7646-8871
						</a>
					</a>
				</li>
				<li class="c-list__item">
					<div class="c-list__image">
						<img src="/resources/images/mobile/logo/ico_upstairs.png" alt="업스테어스">
					</div>
					<a class="c-list__anchor c-list__anchor--icon" href="https://fongabi.com" target="_blank">
						<strong class="c-list__title u-mb--20">(주)업스테어스</strong>
						<a class="c-list__sub c-list__sub--tel" href="tel:1544-0278">
							<i class="c-icon c-icon--call" aria-hidden="true"></i>1544-0278
						</a>
					</a>
				</li>
			</ul>
			<h3 class="c-heading c-heading--type3">단말 불량(교환/취소 안내)</h3>
			<div class="c-tabs c-tabs--type2">
				<div class="c-tabs__list" data-tab-list="">
					<button class="c-tabs__button" data-tab-header="#tabB1-panel">새폰</button>
					<button class="c-tabs__button" data-tab-header="#tabB2-panel">중고폰/리퍼폰</button>
				</div>
				<div class="c-tabs__panel" id="tabB1-panel">
					<!-- tab1 : 새폰-->
					<h4 class="c-heading c-heading--type2">교환/취소 순서</h4>
					<div class="order-list-wrap">
						<dl class="order-list">
							<dt class="order-list__item">
								<span class="order-list__number">1</span>
								<strong class="order-list__title">불량확인</strong>
							</dt>
							<dd class="order-list__text">
								제조사 AS센터를 방문 및 문의하여 불량 확인서 (애플의 경우 작업인가 확인서) 수령
							</dd>
							<dt class="order-list__item">
								<span class="order-list__number">2</span>
								<strong class="order-list__title">불량접수</strong>
							</dt>
							<dd class="order-list__text">
								고객센터(1899-5000)로 불량 접수(교환 or 취소 신청)
							</dd>
							<dt class="order-list__item">
								<span class="order-list__number">3</span>
								<strong class="order-list__title">휴대폰 반품</strong>
							</dt>
							<dd class="order-list__text">불량 확인서 or 작업인가 확인서를 함께 동봉하여 휴대폰 반품</dd>
							<p class="c-bullet c-bullet--fyr u-co-gray">구성품 누락 시 교환/반품불가</p>
							<dt class="order-list__item">
								<span class="order-list__number">4</span>
								<strong class="order-list__title">교환 또는 취소</strong>
							</dt>
							<dd class="order-list__text">단말 교환 또는 개통취소 진행</dd>
						</dl>
					</div>
					<div class="c-box c-box--type1 c-expand">
						<h4 class="c-heading c-heading--type2">꼭 알아두세요</h4>
						<ul class="c-text-list c-bullet c-bullet--dot">
							<li class="c-text-list__item">단말기 불량 시 14일 이내 교환/취소가 가능하며, 단순변심 및 가격변동으로 인한 교환/취소는 불가합니다.</li>
							<li class="c-text-list__item">개통 후 14일 이내에 제조사 AS센터를 방문하셔서 제품 불량확인서를 받으시고 개통센터에 교환/취소 접수를 하신 후 휴대폰 반납까지 완료, 반납 확인이 가능한 경우에만 교환/취소가 가능합니다.</li>
							<li class="c-text-list__item">불량 단말기를 반납하실 경우 불량확인서(필수)와 최초 구매 시의 모든 구성품을 누락 없이 반납하셔야 합니다.(불량확인서, 휴대폰 박스 내 구성품 누락 시 교환/취소가 불가합니다.)</li>
							<li class="c-text-list__item">개통을 취소 하실 경우 개통 후 부터 취소 시점까지 발생된 요금은 일할 계산 되어 청구됩니다.</li>
						</ul>
					</div>
				</div>
				<div class="c-tabs__panel" id="tabB2-panel">
					<!-- tab2 : 중고폰-->
					<h4 class="c-heading c-heading--type2">교환/취소 순서</h4>
					<div class="order-list-wrap">
						<dl class="order-list">
							<dt class="order-list__item">
								<span class="order-list__number">1</span>
								<strong class="order-list__title">불량확인</strong>
							</dt>
							<dd class="order-list__text">
								제조사 AS센터를 방문 및 문의하여 불량 확인서 (애플의 경우 작업인가 확인서) 수령
							</dd>
							<dt class="order-list__item">
								<span class="order-list__number">2</span>
								<strong class="order-list__title">불량접수</strong>
							</dt>
							<dd class="order-list__text">
								고객센터(1899-5000)로 불량 접수(교환 or 취소 신청)
							</dd>
							<dt class="order-list__item">
								<span class="order-list__number">3</span>
								<strong class="order-list__title">휴대폰 반품</strong>
							</dt>
							<dd class="order-list__text">불량 확인서 or 작업인가 확인서를 함께 동봉하여 휴대폰 반품</dd>
							<p class="c-bullet c-bullet--fyr u-co-gray">구성품 누락 시 교환/반품불가</p>
							<dt class="order-list__item">
								<span class="order-list__number">4</span>
								<strong class="order-list__title">교환 또는 취소</strong>
							</dt>
							<dd class="order-list__text">단말 교환 또는 개통취소 진행</dd>
						</dl>
					</div>
					<div class="c-box c-box--type1 c-expand">
						<!--[2021-11-26] 폰트 관련 유틸클래스 추가=====-->
						<h4 class="c-heading c-heading--type2 u-fw--medium">꼭 알아두세요</h4>
						<ul class="c-text-list c-bullet c-bullet--dot">
							<li class="c-text-list__item">단말기 불량 시 14일 이내 교환/취소가 가능하며, 단순변심 및 가격변동으로 인한 교환/취소는 불가합니다.</li>
							<li class="c-text-list__item">개통 후 14일 이내에 개통센터 및 공급사로 연락하셔서 제품 불량확인서를 받으시고 개통센터에 교환/취소 접수를 하신 후 휴대폰 반납까지 완료, 반납 확인이 가능한 경우에만 교환/취소가 가능합니다.
								<span class="c-bullet c-bullet--fyr u-co-sub-4 fs-13">고객센터(1899-5000)를 통해 중고폰/리퍼폰 제조사(공급사) 확인이 가능합니다.</span>
							</li>
							<li class="c-text-list__item">제품에 결함이 있는 경우 제조사(공급사) 별 품질 보증기간 내 무상 A/S가 가능합니다.(고객 부주의/과실에 의한 파손 및 불량 발생건 제외)
                        		<span class="c-bullet c-bullet--fyr u-co-sub-4 fs-13">각 공급사로 A/S 문의</span>
                       		</li>
							<li class="c-text-list__item">불량 단말기를 반납하실 경우 불량확인서(필수)와 최초 구매 시의 모든 구성품을 누락 없이 반납하셔야 합니다.(불량확인서, 휴대폰 박스 내 구성품 누락 시 교환/취소가 불가합니다.)</li>
							<li class="c-text-list__item">개통을 취소 하실 경우 개통 후 부터 취소 시점까지 발생된 요금은 일할 계산 되어 청구됩니다.</li>
						</ul>
					</div>
				</div>
			</div>
			<h3 class="c-heading c-heading--type3">통화품질 불량(취소 안내)</h3>
			<p class="c-text c-text--type3">통화품질 불량의 경우 교환은 불가하며 개통 취소만 가능합니다.</p>
			<ul class="c-text-list c-bullet c-bullet--dot">
				<li class="c-text-list__item">통화품질 불량의 경우 고객센터 (1899-5000)를 통하여 통화품질 불량을 접수하시면 현장기사가 방문하여 불량여부를 확인합니다.</li>
				<li class="c-text-list__item">불량임이 확인되면 개통 후 14일 이내 휴대폰 반납까지 완료, 반납 확인이 가능한 경우에만 개통취소가 가능합니다.</li>
				<li class="c-text-list__item">단말기를 반납하실 경우 모든 구성품을 누락 없이 반납하셔야 합니다. (누락 시 취소가 불가합니다.)</li>
			</ul>
			<p class="c-bullet c-bullet--fyr u-co-gray">그 밖에 궁금하신 점은 kt M모바일 고객센터(1899-5000)로 문의 해주세요.</p>
		</div>

	</t:putAttribute>
</t:insertDefinition>