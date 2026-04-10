<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/cs/serviceQuickGuide.js""></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">

        <div class="ly-content selfservice" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    서비스 퀵 가이드
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="quickguide-content">
                <div class="quickguide-head">
                    <h3 class="quickguide-head__title">이용방법을 동영상으로 쉽게 확인해보세요.</h3>
                </div>
                <ul class="quickguide-list">

                    <c:forEach var="boardObj" items="${snsBoardList}">
                        <li class="quickguide-list__item">
                            <a class="quickguide-list__anchor _btnView" data-iframe-src="${boardObj.mobileLinkUrl}" href="javascript:void(0);" title="${boardObj.ntcartTitle}">
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
        <div class="c-modal quickguide" id="modalContent" role="dialog" tabindex="-1" aria-describedby="modalContentTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="quickguide-box">
                        	<i class="c-icon c-icon--reset" aria-hidden="true" data-dialog-close></i>
                            <iframe id="youTubeIframe" src="about:blank" frameborder="0" allowfullscreen="1" style="width: 100%; height: 250px;" allow="autoplay; encrypted-media"></iframe>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>