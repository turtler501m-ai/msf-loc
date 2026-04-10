<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">서비스 조회/변경</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content selfservice" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">서비스 조회/변경</h2>
            </div>
            <div class="selfservice-content">
                <div class="c-filter">
	                <div class="c-filter__inner">
	                    <a class="c-button" href="#farPricePlan" data-scroll-top="#farPricePlan|100">요금제 변경</a>
	                    <a class="c-button" href="#regService" data-scroll-top="#regService|100">부가서비스 신청/변경</a>
	                    <a class="c-button" href="#usimSelfChg" data-scroll-top="#usimSelfChg|100">유심변경</a>
	                    <a class="c-button" href="#lost" data-scroll-top="#lost|100">분실신고/해제</a>
	                    <a class="c-button" href="#suspend" data-scroll-top="#suspend|100">일시정지/해제</a>
	                    <a class="c-button" href="#myinfo" data-scroll-top="#myinfo|100">가입정보 조회</a>
	               </div>
	            </div>
				<div class="selfservice-head">
				    <h3 class="selfservice-head__title">온라인 셀프 서비스</h3>
					<p class="selfservice-head__text">
						고객센터로 연락할 필요 없이 마이페이지/App에서<br />
						서비스를 직접 간편하고 빠르게 확인/변경 해 보세요.
					</p>
				</div>
				<div class="selfservice-list-wrap inq">
					<div class="selfservice-list__item" id="farPricePlan">
						<div class="selfservice-list__img">
							<img src="/resources/images/portal/cs/selfservice_inq_01.png" alt="이용중인 요금제를 온라인으로 확인 및 즉시/예약 (다음달 1일) 변경이 가능합니다.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>요금제 변경</h4>
								<ul>
						            <li>
						            	이용중인 요금제를 온라인으로 확인 및 즉시/예약 (다음달 1일) 변경이 가능합니다.
									</li>
						            <li>
						            	내게 맞는 요금제를 찾아 요금제를 변경 해 보세요.
					            	</li>
						        </ul>
						        <div class="selfservice-list__btn-wrap">
						        	<a href="/mypage/farPricePlanView.do" title="요금제 변경 바로가기">요금제 변경 바로가기</a>
						        </div>
					        </div>
						</div>
					</div>
					<div class="selfservice-list__item" id="regService">
						<div class="selfservice-list__img">
							<img src="/resources/images/portal/cs/selfservice_inq_02.png" alt="부가서비스 소개 페이지에서 현재 사용중인 부가서비스를 확인하고 신청 및 변경할 수 있습니다.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>부가서비스 신청/변경</h4>
								<ul>
						            <li>
						            	현재 사용중인 부가서비스를 확인하고 신청 및 변경할 수 있습니다.
									</li>
						            <li>
						            	부가서비스 소개 페이지에서 유용한 부가서비스를 확인해보세요.
					            	</li>
						        </ul>
						        <div class="selfservice-list__btn-wrap">
							        <a href="/rate/adsvcGdncList.do" title="부가서비스 소개 바로가기">부가서비스 소개 바로가기</a>
							        <a href="/mypage/regServiceView.do" title="부가서비스 신청/변경 바로가기">부가서비스 신청/변경 바로가기</a>
						        </div>
					        </div>
						</div>
					</div>
					<div class="selfservice-list__item" id="usimSelfChg">
						<div class="selfservice-list__img">
							<img src="/resources/images/portal/cs/selfservice_inq_03.png" alt="휴대폰 변경 등으로 유심 변경이 필요한 경우 사용중인 휴대폰의 유심을 새 유심으로 교체할 수 있습니다.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>유심 셀프 변경</h4>
								<ul>
						            <li>
						            	사용중인 휴대폰의 유심을 새 유심으로 교체할 수 있습니다.
						            	휴대폰 변경 등으로 유심 변경이 필요한 경우 이용하세요.
									</li>
						            <li>
						            	유심변경을 위해서는 먼저 변경할 유심이 필요합니다.
					            	</li>
						        </ul>
						        <div class="selfservice-list__btn-wrap">
							        <a href="/appForm/reqSelfDlvry.do" title="다이렉트몰 유심구매 바로가기">다이렉트몰 유심구매 바로가기</a>
							        <a href="/mypage/usimSelfChg.do" title="유심 변경 바로가기">유심 변경 바로가기</a>
						        </div>
					        </div>
						</div>
					</div>
					<div class="selfservice-list__item" id="lost">
						<div class="selfservice-list__img">
							<img src="/resources/images/portal/cs/selfservice_inq_04.png" alt="온라인을 통해 분실 신고를 접수 또는 해제하세요.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>분실신고/해제</h4>
								<ul>
						            <li>
						            	온라인을 통해 분실 신고를 접수 또는 해제하세요.
									</li>
						            <li>
						            	분실 신고는 별도 서류 없이 바로 접수가 가능합니다.
					            	</li>
						        </ul>
						        <div class="selfservice-list__btn-wrap">
						        	<a href="/mypage/lostView.do" title="분실신고/해제 바로가기">분실신고/해제 바로가기</a>
					        	</div>
					        </div>
						</div>
					</div>
					<div class="selfservice-list__item" id="suspend">
						<div class="selfservice-list__img">
							<img src="/resources/images/portal/cs/selfservice_inq_05.png" alt="휴대폰 번호를 일정기간 사용하지 않는 경우 원하는 기간만큼 일시정지를 신청하고 다시 해제할 수 있습니다.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>일시정지/해제</h4>
								<ul>
						            <li>
						            	휴대폰 번호를 일정기간 사용하지 않는 경우 원하는 기간만큼 일시정지를 신청하고 다시 해제할 수 있습니다.
									</li>
						        </ul>
						        <div class="selfservice-list__btn-wrap">
						        	<a href="/mypage/suspendView01.do" title="일시정지/해제 바로가기">일시정지/해제 바로가기</a>
					        	</div>
					        </div>
						</div>
					</div>
					<div class="selfservice-list__item" id="myinfo">
						<div class="selfservice-list__img">
							<img src="/resources/images/portal/cs/selfservice_inq_06.png" alt="마이페이지에서 가입 내역을 확인하고 기타 조회/변경 가능한 서비스를 확인 해 보세요.">
						</div>
						<div class="selfservice-list__info-wrap">
							<div class="selfservice-list__info">
								<h4>기타 조회/변경</h4>
								<ul>
						            <li>
						            	마이페이지에서 가입 내역을 확인하고 기타 조회/변경<br/> 가능한 서비스를 확인 해 보세요.
									</li>
						        </ul>
						        <div class="selfservice-list__btn-wrap">
						        	<a href="/mypage/myinfoView.do" title="가입정보 조회 바로가기">가입정보 조회 바로가기</a>
					        	</div>
					        </div>
						</div>
					</div>
				</div>
            </div>
        </div>



    </t:putAttribute>
</t:insertDefinition>
