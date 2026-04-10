<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">서비스 퀵 가이드</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/cs/serviceQuickGuide.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content selfservice" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">서비스 퀵 가이드</h2>
            </div>
            <div class="quickguide-content">
                <div class="quickguide-head">
                    <h3 class="quickguide-head__title">이용방법을 동영상으로 쉽게 확인해보세요.</h3>
                    <hr>
                </div>
                <ul class="quickguide-list">
                    <c:forEach var="boardObj" items="${snsBoardList}">
                        <li class="quickguide-list__item">
                            <a class="quickguide-list__anchor _btnView" data-iframe-src="${boardObj.pcLinkUrl}"  data-title="${boardObj.ntcartTitle}" href="javascript:void(0);" title="${boardObj.ntcartTitle}">
                                <div class="quickguide-list__image">
                                    <img src="${boardObj.thumbImgNm}" alt="${boardObj.thumbImgDesc}" onerror="this.src='/resources/images/portal/content/img_review_noimage.png'" >
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

    </t:putAttribute>
    <t:putAttribute name="popLayerAttr">

        <div class="c-modal c-modal--xlg" id="modalContent" role="dialog" tabindex="-1" aria-labelledby="#modalContentTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="modalContentTitle">서비스 퀵 가이드</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="opening-guide__image u-mt--0">
                            <iframe id="youTubeIframe" src="about:blank" width="940" height="529" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen=""> </iframe>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
