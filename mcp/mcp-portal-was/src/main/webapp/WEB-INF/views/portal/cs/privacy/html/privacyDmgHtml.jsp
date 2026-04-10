<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<div class="c-row c-row--lg">
    <h3 class="c-heading c-heading--fs20 c-heading--regular">피해 사례</h3>
    <div class="c-button-wrap c-button-wrap--right">
        <a class="c-button c-button--xsm u-co-gray" href="https://wiseuser.go.kr/edu_list.do?boardtypecode=5250" target="_blank" title="새창열림">바로가기
            <i class="c-icon c-icon--anchor" aria-hidden="true"></i>
        </a>
    </div>
    <hr class="c-hr c-hr--title">
    <ul class="damage-case">
        <li class="damage-case__item">
            <div class="damage-case__panel">
                <a href="https://wiseuser.go.kr/edu_list.do?boardtypecode=5250" target="_blank" title="보이스 피싱 새창열림">
                    <strong class="damage-case__title">보이스 피싱</strong>
                    <i class="damage-case__image" aria-hidden="true">
                        <img src="/resources/images/portal/content/img_damage_case_bg1.png" alt="">
                    </i>
                </a>
            </div>
            <p class="c-text c-text--fs14 u-co-gray">음성(voice)+개인정보(Private data)+낚시(Fishing)의 합성어로 전화를 통해 개인 금융정보를 알아낸 뒤 이를 범죄에 이용하는 수법</p>
        </li>
        <li class="damage-case__item">
            <div class="damage-case__panel">
                <a href="https://wiseuser.go.kr/edu_list.do?boardtypecode=5250" target="_blank" title="스미싱 새창열림">
                    <strong class="damage-case__title">스미싱</strong>
                    <i class="damage-case__image" aria-hidden="true">
                        <img src="/resources/images/portal/content/img_damage_case_bg2.png" alt="">
                    </i>
                </a>
            </div>
            <p class="c-text c-text--fs14 u-co-gray">문자메시지의 인터넷주소를 클릭하면 악성코드가 스마트폰에 설치되어, 피해자가 모르는 사이에 소액결제 및 공인 인증서, 연락처 등 개인·금융정보 등을 유출해 가는 사기 수법</p>
        </li>
        <li class="damage-case__item">
            <div class="damage-case__panel">
                <a href="https://wiseuser.go.kr/edu_list.do?boardtypecode=5250" target="_blank" title="파밍 새창열림">
                    <strong class="damage-case__title">파밍</strong>
                    <i class="damage-case__image" aria-hidden="true">
                        <img src="/resources/images/portal/content/img_damage_case_bg3.png" alt="">
                    </i>
                </a>
            </div>
            <p class="c-text c-text--fs14 u-co-gray">사용자의 PC를 악성코드로 감염시켜 금융정보를 빼내는 수법</p>
        </li>
        <li class="damage-case__item">
            <div class="damage-case__panel">
                <a href="https://wiseuser.go.kr/edu_list.do?boardtypecode=5250" target="_blank" title="큐싱 새창열림">
                    <strong class="damage-case__title">큐싱</strong>
                    <i class="damage-case__image" aria-hidden="true">
                        <img src="/resources/images/portal/content/img_damage_case_bg4.png" alt="">
                    </i>
                </a>
            </div>
            <p class="c-text c-text--fs14 u-co-gray">출처가 불분명한 ‘QR코드’를 스마트폰으로 찍을 경우, 악성 앱을 내려 받도록 유도하거나 악성프로그램을 설치하게 하는 금융사기 수법</p>
        </li>
        <li class="damage-case__item">
            <div class="damage-case__panel">
                <a href="https://wiseuser.go.kr/edu_list.do?boardtypecode=5250" target="_blank" title="메신저피싱 새창열림">
                    <strong class="damage-case__title">메신저피싱</strong>
                    <i class="damage-case__image" aria-hidden="true">
                        <img src="/resources/images/portal/content/img_damage_case_bg5.png" alt="">
                    </i>
                </a>
            </div>
            <p class="c-text c-text--fs14 u-co-gray">메신저 ID를 도용하여 지인을 사칭하며 카카오톡, 네이트온 등 대화창을 통해 돈을 요구하여 편취하는 사기수법</p>
        </li>
    </ul>

    <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">피해방지 서비스</h3>
    <hr class="c-hr c-hr--title">
    <div class="prevent-service">
        <div class="prevent-service__item">
            <i class="prevent-service__image" aria-hidden="true">
                <img src="/resources/images/portal/common/ico_prevent_service_01.svg" alt="">
            </i>
            <strong class="prevent-service__title"> 명의도용 방지 서비스</strong>
            <a class="prevent-service__anchor" id="impersonation" href="/cs/privacyBoardList.do?tabIndex=4" title="명의도용 방지 페이지 이동하기">바로가기
                <i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
            </a>
        </div>
        <div class="prevent-service__item">
            <i class="prevent-service__image" aria-hidden="true">
                <img src="/resources/images/portal/common/ico_prevent_service_02.png" alt="">
            </i>
            <strong class="prevent-service__title"> 로밍차단 서비스</strong>
            <a class="prevent-service__anchor" href="/rate/roamingList.do" target="_blank" title="로밍차단 서비스 새창열림"> 바로가기
                <i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
            </a>
        </div>
    </div>

    <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">상담 · 신고기관</h3>
    <hr class="c-hr c-hr--title u-mb--0">
    <c:choose>
        <c:when test="${privacyEtcBoardList ne null and !empty privacyEtcBoardList}">
            <ul class="c-list c-list--type1">
                <c:forEach var="privacyEtcBoardList" items="${privacyEtcBoardList}">
                    <li class="c-list__item">
                        <strong class="c-list__title">${privacyEtcBoardList.boardSubject}</strong>
                        <a class="c-list__anchor" href="${privacyEtcBoardList.boardContents}" target="_blank" title="${privacyEtcBoardList.boardSubject} 새창열림">바로가기
                            <i class="c-icon c-icon--anchor" aria-hidden="true"></i>
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
</div>