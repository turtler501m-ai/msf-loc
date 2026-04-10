<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
<h3 class="c-heading c-heading--type3">피해 사례</h3>
<div class="c-button-wrap c-button-wrap--right">
    <c:choose>
        <c:when test="${platFormCd eq 'A'}">
            <a class="c-button c-button--xsm" href="javascript:appOutSideOpen('https://wiseuser.go.kr/edu_list.do?boardtypecode=5250');">
        </c:when>
        <c:otherwise>
            <a class="c-button c-button--xsm" href="javascript:openPage('outLink','https://wiseuser.go.kr/edu_list.do?boardtypecode=5250','');">
        </c:otherwise>
    </c:choose>
        <span class="c-hidden">바로가기</span>
        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
    </a>
</div>
<div class="swiper-guide-wrap c-expand swiper-container" id="swipeGuideWrap">
    <div class="swiper-wrapper">
        <c:choose>
            <c:when test="${platFormCd eq 'A'}">
                <div class="swiper-slide" onclick="javascript:appOutSideOpen('https://wiseuser.go.kr/edu_list.do?boardtypecode=5250');">
            </c:when>
            <c:otherwise>
                <div class="swiper-slide" onclick="openPage('outLink','https://wiseuser.go.kr/edu_list.do?boardtypecode=5250','');">
            </c:otherwise>
            </c:choose>
            <div class="c-box c-box--type3">
                <strong class="c-box c-box--type3__title">보이스 피싱</strong>
                <p class="c-box c-box--type3__text">음성(voice)+개인정보(Private data) +낚시(Fishing)의 합성어로 전화를 통해 개인 금융정보를 알아낸 뒤 이를 범죄에 이용하는 수법</p>
                <div class="c-box c-box--type3__image">
                    <img src="/resources/images/mobile/content/guide_info_swiper_01.png" alt="">
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${platFormCd eq 'A'}">
                <div class="swiper-slide" onclick="javascript:appOutSideOpen('https://wiseuser.go.kr/edu_list.do?boardtypecode=5250');">
            </c:when>
            <c:otherwise>
                <div class="swiper-slide" onclick="openPage('outLink','https://wiseuser.go.kr/edu_list.do?boardtypecode=5250','');">
            </c:otherwise>
        </c:choose>
            <div class="c-box c-box--type3">
                <strong class="c-box c-box--type3__title">스미싱</strong>
                <p class="c-box c-box--type3__text">문자메시지의 인터넷주소를 클릭하면 악성코드가 스마트폰에 설치되어, 피해자가 모르는 사이에 소액결제 및 공인 인증서, 연락처 등 개인·금융정보 등을 유출해 가는 사기 수법</p>
                <div class="c-box c-box--type3__image">
                    <img src="/resources/images/mobile/content/guide_info_swiper_02.png"	alt="">
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${platFormCd eq 'A'}">
                <div class="swiper-slide" onclick="javascript:appOutSideOpen('https://wiseuser.go.kr/edu_list.do?boardtypecode=5250');">
            </c:when>
            <c:otherwise>
                <div class="swiper-slide" onclick="openPage('outLink','https://wiseuser.go.kr/edu_list.do?boardtypecode=5250','');">
            </c:otherwise>
        </c:choose>
            <div class="c-box c-box--type3">
                <strong class="c-box c-box--type3__title">파밍</strong>
                <p class="c-box c-box--type3__text">사용자의 PC를 악성코드로 감염시켜 금융정보를 빼내는 수법</p>
                <div class="c-box c-box--type3__image">
                    <img src="/resources/images/mobile/content/guide_info_swiper_03.png"	alt="">
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${platFormCd eq 'A'}">
                <div class="swiper-slide" onclick="javascript:appOutSideOpen('https://wiseuser.go.kr/edu_list.do?boardtypecode=5250');">
            </c:when>
            <c:otherwise>
                <div class="swiper-slide" onclick="openPage('outLink','https://wiseuser.go.kr/edu_list.do?boardtypecode=5250','');">
            </c:otherwise>
        </c:choose>
            <div class="c-box c-box--type3">
                <strong class="c-box c-box--type3__title">큐싱</strong>
                <p class="c-box c-box--type3__text">출처가 불분명한 ‘QR코드’를 스마트폰으로 찍을 경우, 악성 앱을 내려 받도록 유도하거나 악성프로그램을 설치하게 하는 금융사기 수법</p>
                <div class="c-box c-box--type3__image">
                    <img src="/resources/images/mobile/content/guide_info_swiper_04.png"	alt="">
                </div>
            </div>
        </div>
        <c:choose>
            <c:when test="${platFormCd eq 'A'}">
                <div class="swiper-slide" onclick="javascript:appOutSideOpen('https://wiseuser.go.kr/edu_list.do?boardtypecode=5250');">
            </c:when>
            <c:otherwise>
                <div class="swiper-slide" onclick="openPage('outLink','https://wiseuser.go.kr/edu_list.do?boardtypecode=5250','');">
            </c:otherwise>
        </c:choose>
            <div class="c-box c-box--type3">
                <strong class="c-box c-box--type3__title">메신저피싱</strong>
                <p class="c-box c-box--type3__text">메신저 ID를 도용하여 지인을 사칭하며 카카오톡, 네이트온 등 대화창을 통해 돈을 요구하여 편취하는 사기수법</p>
                <div class="c-box c-box--type3__image">
                    <img src="/resources/images/mobile/content/guide_info_swiper_05.png" alt="">
                </div>
            </div>
        </div>
    </div>
</div>

<h3 class="c-heading c-heading--type3">피해방지 서비스</h3>
<div class="c-button-wrap c-button-wrap--column">
    <c:choose>
        <c:when test="${platFormCd eq 'A'}">
            <a class="c-button c-button--md c-button--white" href="/m/cs/privacyBoardList.do?tabIndex=4">
        </c:when>
        <c:otherwise>
            <a class="c-button c-button--md c-button--white" href="/m/cs/privacyBoardList.do?tabIndex=4">
        </c:otherwise>
    </c:choose>
        <i class="c-icon c-icon--secure-person" aria-hidden="true"></i>
        <span>명의도용 방지 서비스</span>
        <span class="c-button__sub">
            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
        </span>
    </a>

    <a class="c-button c-button--md c-button--white" href="javascript:openPage('outLink','/m/rate/roamingList.do','');">
        <i class="c-icon c-icon--secure-prevent" aria-hidden="true"></i>
        <span>로밍차단 서비스</span>
        <span class="c-button__sub">
            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
        </span>
    </a>
</div>

<h3 class="c-heading c-heading--type3">상담 · 신고기관</h3>
<c:choose>
    <c:when test="${privacyEtcBoardList ne null and !empty privacyEtcBoardList}">
        <ul class="c-list c-list--type1">
            <c:forEach var="privacyEtcBoardList" items="${privacyEtcBoardList}">
                <li class="c-list__item">
                <c:choose>
                    <c:when test="${platFormCd eq 'A'}">
                        <a class="c-list__anchor c-list__anchor--icon" href="javascript:appOutSideOpen('${privacyEtcBoardList.boardContents}');">
                    </c:when>
                    <c:otherwise>
                        <a class="c-list__anchor c-list__anchor--icon" href="${privacyEtcBoardList.boardContents}" target="_blank">
                    </c:otherwise>
                </c:choose>
                        <strong class="c-list__title">${privacyEtcBoardList.boardSubject}</strong>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <div class="c-nodata">
            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
            <p class="c-noadat__text">일치하는 검색 결과가 없습니다.</p>
        </div>
    </c:otherwise>
</c:choose>

