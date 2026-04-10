<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">다이렉트몰 구매하기</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr"></t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <form name="frm" id="frm" method="post" action="/m/loginForm.do">
                <input type="hidden" name="uri" id="uri" value="/m/appForm/reqSelfDlvry.do"/>
            </form>

            <div class="ly-page--title">
                <h2 class="c-page--title">다이렉트몰 구매하기</h2>
            </div>
            <div class="ly-page--deco directmall u-bk--red">
                <div class="ly-avail--wrap">
                    <h3 class="c-headline">
                        다이렉트몰에서 <br>유심(USIM) 구매하세요
                    </h3>
                    <div class="c-button-wrap c-button-wrap--left u-mt--24">
                        <a class="c-button c-button--sm c-button--transparent c-button-round--sm c-flex" data-dialog-trigger="#pre-prepare-modal">
                            <span class="u-mr--16">유심구매</span>
                            <span class="c-hidden">바로가기</span>
                            <i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                        </a>
                    </div>
                    <img class="c-headline--deco" src="/resources/images/portal/content/img_directmall.png" alt="">
                </div>
            </div>
            <div class="c-row c-row--lg">
                <div class="directmall-wrap">
                    <p class="c-text c-text--fs16 u-mt--64">스마트한 통신생활의 시작</p>
                    <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--8">
                        사용하던 폰 그대로<b>유심</b>(USIM)만 교체하세요.
                    </h3>
                    <p class="c-text c-text--fs17 u-mt--24 u-co-gray">
                        휴대폰은 그대로, 요금제는 저렴하게! <br>현재 사용하는 휴대폰의 유심(USIM)만 교체하시면 됩니다.
                    </p>
                    <div class="c-button-wrap">
                        <a class="c-button c-button--underline u-co-black u-fw--medium" href="/requestReView/requestReView.do">사용후기 확인하기</a>
                    </div>
                </div>
                <div class="u-mt--64">
                    <img src="/resources/images/portal/content/img_usim_banner.png" alt="찾으시는 결과가 없다면? - HELP 버튼을 선택해 챗봇과 1:1 상담문의 서비스를 이용해보세요!" href="javascript:void(0);" onclick="javascript:location.href='/mmobile/ktmMobileGuidView.do';" style="cursor:pointer;">
                </div>
            </div>
        </div>

        <div class="c-modal c-modal--md" id="pre-prepare-modal" role="dialog" aria-labelledby="#pre-prepare-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="pre-prepare-modal__title">구매 전 준비해주세요</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <h3 class="c-heading c-heading--fs20">신분증과 계좌/신용카드를 준비해주세요!</h3>
                        <p class="c-text c-text--fs17 u-mt--12 u-co-gray">유심 구매 시 본인인증이 진행됩니다.</p>
                        <!--[2022-02-07] 이미지 변경 및 aria-hidden 속성 추가-->
                        <div class="certification-wrap u-mt--32 u-mb--32" aria-hidden="true">
                            <img src="/resources/images/portal/content/img_id_card.png" alt="">
                        </div>
                        <hr class="c-hr c-hr--title">
                        <b class="c-flex c-text c-text--fs15">
                            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                            <span class="u-ml--4">선택 가능 본인인증 방법</span>
                        </b>
                        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                            <li class="c-text-list__item u-fw--medium u-co-gray">신용카드, 네이버인증서, PASS 인증서, toss 인증서, 범용 인증서</li>
                        </ul>
                    </div>
                    <!--[2021-01-14] 로그인 유도 영역 삭제(기획요청)-->
                    <div class="c-modal__footer u-ta-center">
                        <!-- case : 로그인인 경우-->
                        <button class="c-button c-button--lg c-button--primary c-button--w460" onclick="location.href='/appForm/reqSelfDlvry.do';">구매하기</button>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
