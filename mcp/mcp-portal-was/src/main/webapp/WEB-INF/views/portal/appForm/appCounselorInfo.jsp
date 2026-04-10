<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">가입신청(상담사 연결)</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/login/addBirthGenderView.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">상담사 개통 신청</h2>
            </div>
            <div class="ly-page--deco self-open-bk--blue">
                <div class="ly-avail--wrap">
                    <h3 class="c-headline">신청서를 작성하시면 AI 상담사 또는 개통 상담사가<br />순차적으로 개통을 도와드립니다.</h3>
                    <p class="c-headline--sub">야간에 상담사 개통 신청을 하신 경우 오전 9시 이후 순차적으로 전화드립니다.</p>
                    <div class="c-button-wrap c-button-wrap--left u-mt--20">
                        <a class="c-button c-button--sm c-button--transparent c-button-round--sm c-flex" href="/appForm/appFormDesignView.do">
                            <span class="u-mr--16">가입신청서 작성</span>
                            <span class="c-hidden">바로가기</span>
                            <i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                        </a>
                    </div>
                    <img class="c-headline--deco" src="/resources/images/portal/content/img_connect_counselor.png" alt="">
                </div>
            </div>
            <div class="c-row c-row--lg">
                <div class="usim-guide-wrap">
                    <p class="c-text u-co-gray">
                           개통안내전화를 기다릴 필요 없이<span class="u-co-black c-heading--medium"> 직접 개통하는 셀프개통</span> 방법도 확인해보세요.
                        <a class="c-text-link--bluegreen-type2" href="/appForm/appSimpleInfo.do" title="유심 셀프개통 페이지 바로가기">유심 셀프개통 바로가기</a>
                    </p>
                    <p class="c-text u-co-gray u-mt--6">
                        eSIM으로 셀프개통이 가능합니다. eSIM 개통 가능 여부를 확인해 보세요.
                        <a class="c-text-link--bluegreen-type2" href="/appForm/eSimInfom.do" title="eSIM 알아보기 페이지 바로가기">eSIM 알아보기</a>
                    </p>
                </div>
                <p class="c-group--head c-heading--medium u-mt--33">셀프개통 가능시간</p>
                <div class="c-box c-box--type1 u-mt--20">
                    <p class="c-bullet c-bullet--dot u-co-gray c-text--fs14 u-mt--2"><span class="c-text c-text--fs14 u-co-black u-mr--4">번호이동 10:00 ~ 19:50</span>(일요일, 신정/설/추석 당일 제외)</p>
                    <p class="c-bullet c-bullet--dot u-co-gray c-text--fs14 u-mt--8 u-mb--4"><span class="c-text c-text--fs14 u-co-black u-mr--4">신규가입 08:00 ~ 21:50</span></p>
                    <p class="c-bullet c-bullet--fyr u-co-gray u-fs-14 u-mt--11">
                 외국인,미성년자는 가입신청서 작성완료후, 순차적으로 연락하여 개통안내를 드립니다.
                        <a class="c-text-link--bluegreen" href="/appForm/appFormDesignView.do?onOffType=0&operType=MNP3&indcOdrg=1&prdtSctnCd=LTE"  title="eSIM 알아보기 페이지 이동하기">가입신청서 작성하기</a>
                    </p>
                </div>
                <p class="c-group--head c-heading--medium u-mt--40">* 통신요금 미납정보 조회 방법 안내</p>
                <p class="c-bullet c-bullet--fyr u-fs-14 u-ml--10">개통을 더욱 빠르고 원활하게 진행하시려면, 아래 사이트에서 통신요금 미납 여부와 단기간 다회선 가입 이력을 사전에 확인 해보시는 것을 권장 드립니다.<br />(통신요금 미납이 있거나, 최근 180일 이내에 3회선 이상을 가입한 이력이 있을 경우, 개통이 어려울 수 있습니다.)</p>
                <hr class="c-hr c-hr--title u-mt--24 u-mb--0">
                <div class="c-accordion c-accordion--type3" data-ui-accordion>
				  <ul class="c-accordion__box">
				    <li class="c-accordion__item">
				      <div class="c-accordion__head u-pl--0">
				        <strong class="c-accordion__title">통신요금 미납정보 조회 사이트 바로가기</strong>
				        <button class="runtime-data-insert c-accordion__button" id="appCounselorAcc_header" type="button" aria-expanded="false" aria-controls="appCounselorAcc_content">
				          <span class="c-hidden">통신요금 미납정보 조회 사이트 바로가기 상세열기</span>
				        </button>
				      </div>
				      <div class="c-accordion__panel expand" id="appCounselorAcc_content" aria-labelledby="appCounselorAcc_header">
				        <div class="c-accordion__inside">
				            <ul class="c-text-list c-bullet c-bullet--hyphen">
					            <li class="c-text-list__item">링크 : 방송통신 신용정보 공동관리 (<a href="https://www.credit.or.kr/" title="방송통신 신용정보 공동관리 페이지 새창 열기" target="_blank">credit.or.kr</a>)</li>
					            <li class="c-text-list__item">
					                   접속경로 :
					               <ul class="c-text-list u-mt--8">
							           <li class="c-text-list__item">1) 사이트 접속 후 ‘본인신용정보 조회하기’ 클릭</li>
							           <li class="c-text-list__item">2) 공동인증서 또는 휴대폰 인증으로 간편한 본인확인 진행</li>
							           <li class="c-text-list__item">3) 현재 통신사 미납정보와 단기간 다회선 가입여부 확인 가능</li>
							       </ul>
					            </li>
					        </ul>
				        </div>
				      </div>
				    </li>
				  </ul>
				</div>
                <a class="usim-banner u-mt--64" href="/mmobile/ktmMobileUsimGuid.do" style="display: block;">
                    <strong class="usim-banner__title">유심 및 단말 개통가이드</strong>
                    <p class="usim-banner__text">셀프개통 누구나 쉽게 5분이면 끝!</p>
                    <i class="usim-banner__image" aria-hidden="true">
                        <img src="/resources/images/portal/content/img_usim_banner.png" alt="">
                    </i>
                </a>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
