<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">고객센터 안내</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/cs/guide/serviceGuide.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">고객센터 안내</h2>
            </div>
            <div class="ly-page--deco u-bk--light-mint">
                <div class="ly-avail--wrap">
                    <img src="/resources/images/portal/content/img_customer_center1.png" alt="고객님! 무엇을 도와드릴까요? kt M모바일 가입 및 서비스 이용에 대해 궁금한 사항을 도와드리는 kt M모바일 고객센터입니다.">
                </div>
            </div>
            <div class="c-row c-row--lg">
                <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">유용한	서비스</h3>
                <hr class="c-hr c-hr--title">
                <div class="c-button-wrap u-mt--50">
                  <a class="c-button c-button--service loding" href="/rate/rateList.do" title="요금제 페이지 이동하기">
                    <img src="/resources/images/portal/content/img_mobile_charge.png" alt="">
                    <div class="service_txt_wrap">
                      <p class="service_tit">요금제</p>
                      <p class="service_txt">다양한 유심, 제휴 및<br/>휴대폰 요금제</p>
                    </div>
                  </a>
                  <a class="c-button c-button--service loding" href="/rate/adsvcGdncList.do" title="부가서비스 페이지 이동하기">
                    <img src="/resources/images/portal/content/img_mobile_extra.png" alt="">
                    <div class="service_txt_wrap">
                      <p class="service_tit">부가서비스</p>
                      <p class="service_txt">통화편의, 데이터추가, 로밍<br/>외 다양한 부가서비스</p>
                    </div>
                  </a>
                  <a class="c-button c-button--service loding" href="/mmobile/ktmMobileUsimGuid.do" title="개통 가이드 페이지 이동하기">
                    <img src="/resources/images/portal/content/img_mobile_usim.png" alt="">
                    <div class="service_txt_wrap">
                      <p class="service_tit">개통 가이드</p>
                      <p class="service_txt">셀프개통 포함 유심 및<br/>단말 개통 가이드</p>
                    </div>
                  </a>
                </div>
                <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--70">온라인 셀프 서비스</h3>
                <hr class="c-hr c-hr--title">
                <h4 class="c-heading c-heading--fs16 c-heading--medium u-mt--50">요금조회/납부</h4>
                <div class="c-button-wrap u-mt--20">
                  <a class="c-button c-button--icon loding" href="/mypage/myinfoView.do" title="가입정보 페이지 이동하기">
                    <i class="c-icon c-icon--cs-joininfo" aria-hidden="true"></i>
                       <span>가입정보</span>
                  </a>
                  <a class="c-button c-button--icon loding" href="/mypage/billWayReSend.do" title="요금명세서 페이지 이동하기">
                    <i class="c-icon c-icon--cs-bill" aria-hidden="true"></i>
                       <span>요금명세서</span>
                  </a>
                  <a class="c-button c-button--icon loding" href="/mypage/chargeView05.do" title="납부방법변경 페이지 이동하기">
                    <i class="c-icon c-icon--cs-chgpay" aria-hidden="true"></i>
                       <span>납부방법변경</span>
                  </a>
                  <a class="c-button c-button--icon loding" href="/mypage/unpaidChargeList.do" title="즉시납부 페이지 이동하기">
                    <i class="c-icon c-icon--cs-immpay" aria-hidden="true"></i>
                       <span>즉시납부</span>
                  </a>
                </div>
                <h4 class="c-heading c-heading--fs16 c-heading--medium u-mt--50">서비스 신청/변경</h4>
                <div class="c-button-wrap u-mt--20">
                  <a class="c-button c-button--icon loding" href="/mypage/farPricePlanView.do" title="요금제변경 페이지 이동하기">
                    <i class="c-icon c-icon--cs-chgrate" aria-hidden="true"></i>
                       <span>요금제 변경</span>
                  </a>
                  <a class="c-button c-button--icon loding" href="/mypage/regServiceView.do" title="부가서비스 페이지 이동하기">
                    <i class="c-icon c-icon--cs-extraservice" aria-hidden="true"></i>
                       <span>부가서비스</span>
                  </a>
                  <a class="c-button c-button--icon loding" href="/mypage/suspendView01.do" title="일시정지 페이지 이동하기">
                    <i class="c-icon c-icon--cs-pause" aria-hidden="true"></i>
                       <span>일시정지</span>
                  </a>
                  <a class="c-button c-button--icon loding" href="/mypage/lostView.do" title="분실신고 페이지 이동하기">
                    <i class="c-icon c-icon--cs-lost" aria-hidden="true"></i>
                       <span>분실신고</span>
                  </a>
                </div>
                <p class="c-bullet c-bullet--fyr u-co-gray u-fs-14 u-mt--25">
                    마이페이지에서 더 많은 서비스를 직접 조회/신청/변경할 수 있습니다.
                    <a class="c-text-link--bluegreen loding" href="/mypage/myinfoView.do" title="마이페이지 이동하기">마이페이지 바로가기</a>
                </p>

                <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--70">무엇이 궁금하신가요?</h3>
                <div class="c-button-wrap c-button-wrap--right">
                    <a class="c-button c-button--sm u-co-sub-4" href="/cs/faqList.do">자주묻는 질문
                        <span class="c-hidden">바로가기</span>
                        <i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
                    </a>
                </div>
                <hr class="c-hr c-hr--title u-mb--0">
                <c:if test="${faqTopList !=null and !empty faqTopList}">
                    <div class="c-accordion c-accordion--type2" data-ui-accordion>
                        <ul class="c-accordion__box">
                            <c:forEach items="${faqTopList}" var="faqTopList" varStatus="status">
                                <li class="c-accordion__item" >
                                    <div class="c-accordion__head">
                                        <span class="question-label">${status.count}<span class="c-hidden">질문</span>	</span>
                                        <strong class="c-accordion__title">${faqTopList.boardSubject}</strong>
                                        <div class="c-accordion__info">
                                            <span>조회</span>
                                            <span class="faqTopCtn">${faqTopList.boardHitCnt}</span>
                                        </div>
                                        <button class="acc_headerA${status.count} runtime-data-insert c-accordion__button faqTop" type="button" aria-expanded="false" aria-controls="acc_contentA${status.count}" boardSeq="${faqTopList.boardSeq}">
                                            <span class="c-hidden"></span>
                                        </button>
                                    </div>
                                    <div class="c-accordion__panel expand" id="acc_contentA${status.count}" aria-labelledby="acc_headerA${status.count}">
                                        <div class="c-accordion__inside">
                                            <span class="box-prefix">A</span>
                                            <div class="box-content">${faqTopList.boardContents}</div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">ARS 상담 문의</h3>
                <hr class="c-hr c-hr--title">
                <h4 class="c-heading c-heading--fs16 c-heading--medium">ARS 상담 문의
                    <span class="c-text c-text--fs14 u-ml--8 u-co-gray">kt M모바일에 대한 모든것을 알려드립니다.</span>
                </h4>
                <div class="c-button-wrap c-button-wrap--right">
                    <button class="c-button c-button-round--xsm c-button--white" onclick="openPage('pullPopup','/cs/serviceGuideArsHtml.do','');">ARS 메뉴 안내</button>
                </div>
                <div class="customer-service customer-service--type2">
                    <div class="customer-service__item">
                        <i class="c-icon c-icon--ars-phone" aria-hidden="true"></i>
                        <strong class="customer-service__title">kt M모바일 휴대폰</strong>
                        <div class="customer-service__info">
                            <b class="c-text c-text--fs22 u-co-point-4">114</b>
                            <sapn class="c-text c-text--fs14 u-co-gray-9 u-ml--8">무료</sapn>
                        </div>
                    </div>
                    <div class="customer-service__item">
                        <i class="c-icon c-icon--ars-tel" aria-hidden="true"></i>
                        <strong class="customer-service__title">타사 휴대폰 및 유선전화</strong>
                        <div class="customer-service__info">
                            <b class="c-text c-text--fs22 u-co-point-3">1899-5000</b>
                            <sapn class="c-text c-text--fs14 u-co-gray-9 u-ml--8">유료</sapn>
                        </div>
                    </div>
                    <div class="customer-service__item">
                        <strong class="customer-service__title">고객센터 운영시간</strong>
                        <p class="customer-service__text">09~12시/13~18시</p>
                        <sapn class="c-text c-text--fs14 u-co-gray-9">토요일, 일요일, 공휴일은 분실, 정지 <br>	신고 및 통화품질 관련 상담만 가능 </sapn>
                        <p class="c-text c-text--fs14 u-co-gray-9 u-mt--8">※ 주소 : 경기 안양시 만안구 안양로 331 (교보생명빌딩, 6층)</p>
                    </div>
                </div>
                <h4 class="c-heading c-heading--fs16 c-heading--medium u-mt--48">
                    선불 충전서비스<span class="c-text c-text--fs14 u-ml--8 u-co-gray">필요한 만큼 충전해서 사용하세요.</span>
                </h4>
                <div class="customer-service customer-service--type2">
                    <div class="customer-service__item">
                        <i class="c-icon c-icon--ars-tel" aria-hidden="true"></i>
                        <strong class="customer-service__title">타사 휴대폰 및 유선전화</strong>
                        <div class="customer-service__info">
                            <b class="c-text c-text--fs22 u-co-point-4">1899-5000</b>
                            <sapn class="c-text c-text--fs14 u-co-gray-9 u-ml--8">유료</sapn>
                        </div>
                    </div>
                    <div class="customer-service__item">
                        <i class="c-icon c-icon--recharge" aria-hidden="true"></i>
                        <strong class="customer-service__title">선불충전</strong>
                        <div class="customer-service__info">
                            <b class="c-text c-text--fs22 u-co-point-3">080-013-0114</b>
                            <sapn class="c-text c-text--fs14 u-co-gray-9 u-ml--8">무료</sapn>
                        </div>
                    </div>
                    <div class="customer-service__item">
                        <i class="c-icon c-icon--balance" aria-hidden="true"></i>
                        <strong class="customer-service__title">잔액조회</strong>
                        <div class="customer-service__info">
                            <b class="c-text c-text--fs22 u-co-point-3">080-012-0114</b>
                            <sapn class="c-text c-text--fs14 u-co-gray-9 u-ml--8">무료</sapn>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
