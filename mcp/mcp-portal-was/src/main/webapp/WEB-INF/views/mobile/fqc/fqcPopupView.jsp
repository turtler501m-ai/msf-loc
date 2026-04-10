<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="fqcEventSeq" value="${nmcp:getCodeNmDto('Constant','fqcEventSeq')}" />
<div class="c-modal__content">
    <div class="c-modal__header">
        <h1 class="c-modal__title" id="freqSummaryModal__title"><span>${userName}</span>님의 M프리퀀시</h1>
        <button class="c-button" data-dialog-close="">
            <i class="c-icon c-icon--close u-mr--0" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>
    <div class="c-modal__body">
        <img class="u-mt--14" src="/resources/images/mobile/frequency/freq_main_banner.png" alt="스템프 완성하고 최대 20만원 받기">
        <div class="freq-summary-stamp">
            <div class="freq-summary-stamp__status-wrap">
                <p>현재  <span>${msnCnt}</span> M탬프 달성</p>
                <div class="freq-summary-stamp__status">
                    <span>${msnCnt}</span>/<span>7</span>
                </div>
            </div>
            <ul class="freq-summary-stamp__progress">
                <c:forEach var="index" begin="0" end="6" step="1">
                    <c:choose>
                        <c:when test="${index lt msnCnt}">
                            <li class="is-stamped"></li>
                        </c:when>
                        <c:otherwise>
                            <li></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>


            </ul>
        </div>
        <div class="freq-summary-button">
            <a class="c-button c-button--full c-button--primary" href="/m/event/eventDetail.do?ntcartSeq=${fqcEventSeq.dtlCdNm}" title="M프리퀀시 이벤트 페이지 이동">
                <p>M프리퀀시<br>이벤트</p>
            </a>
            <a class="c-button c-button--full c-button--primary" href="/m/fqc/fqcEventView.do" rel="nosublink" title="내 스탬프 모으기 페이지 이동">
                <p>내 스탬프<br>모으기</p>
            </a>
        </div>
    </div>
</div>