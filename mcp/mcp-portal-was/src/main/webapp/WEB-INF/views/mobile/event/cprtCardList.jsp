<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="metaTagAttr">
         <!-- 페이스북 Open Graph Tag -->
        <meta property="fb:app_id" content="1694564987490429">
        <meta property="og:type" content="website" />
        <meta property="og:title" content="제휴카드 | kt M모바일 공식 다이렉트몰" id="meta_og_title" />
        <meta property="og:url" content="https://www.ktmmobile.com/event/cprtCardList.do" id="meta_og_url" />
        <meta property="og:description" content="" id="meta_og_description" />
        <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" id="meta_og_image"  />
         <meta name="keywords" content="${nmcp:getCodeDesc('MetaKeywords','01')}">
        <!--// 페이스북 Open Graph Tag -->
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    제휴 카드
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

        <!--  배너시작 -->
            <c:if test="${!empty bannerInfo}">
                <div class="mbership-banner__swiper upper-banner__box">
                    <div class="c-swiper swiper-container">
                        <div class="swiper-wrapper">
                            <c:forEach var="bannerInfo" items="${bannerInfo}">
                                <c:set var="target" value="_self " />
                                <c:if test="${bannerInfo.linkTarget eq 'Y' }">
                                    <c:set var="target" value=""/>
                                </c:if>
                                <c:choose>
                                    <c:when test="${bannerInfo.mobileLinkUrl eq null || bannerInfo.mobileLinkUrl eq ''}">
                                        <div class="swiper-slide">
                                            <div class="c-hidden">
                                                <p>
                                                    <c:out value="${bannerInfo.bannDesc}" />
                                                </p>
                                            </div>
                                            <img src="<c:out value="${bannerInfo.mobileBannImgNm}"/>" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>">
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="swiper-slide">
                                            <div class="c-hidden">
                                                <p>
                                                    <c:out value="${bannerInfo.bannDesc}" />
                                                </p>
                                            </div>
                                            <a href="#" onclick="fn_go_banner('<c:out value="${bannerInfo.mobileLinkUrl}"/>','<c:out value="${bannerInfo.bannSeq}"/>','<c:out value="${bannerInfo.bannCtg}"/>','${target}')">
                                                <img src="${bannerInfo.mobileBannImgNm}" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>">
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>

                        <c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
                            <button class="swiper-play-button stop" type="button"></button>
                            <div class="swiper-pagination"></div>
                        </c:if>
                    </div>
                </div>
            </c:if>
            <!--  배너끝 -->

            <div class="c-tabs c-tabs--type3">
                <%-- 카드 목록 --%>
                <div id="mainTabList" class="c-tabs__list c-expand" data-tab-list="">
                </div>
                <%-- Panel 정보 --%>
                <div id="mainTabPanel" class="c-tabs__panel n-content">
                </div>
            </div>
        </div>

        <%-- SNS 공유하기 버튼 --%>
        <div class="c-modal c-modal--bs" id="bsSample1" role="dialog" tabindex="-1" aria-describedby="#bsSampleDesc1" aria-labelledby="#bsSampletitle1">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="bsSampletitle1">공유하기</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">바텀시트 닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body" id="bsSampleDesc1">
                        <div class="c-button-wrap c-flex u-mt--0">
                            <a class="c-button" href="javascript:void(0);" onclick="kakaoShare(); return false;">
                                <span class="c-hidden">카카오톡</span>
                                <i class="c-icon c-icon--kakao" aria-hidden="true"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);" onclick="lineShare(); return false;">
                                <span class="c-hidden">line</span>
                                <i class="c-icon c-icon--line" aria-hidden="true"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);" onclick="clip();">
                                <span class="c-hidden">URL 복사하기</span>
                                <i class="c-icon c-icon--link" aria-hidden="true"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bluebird.core.js"></script>
        <script src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/validationCheck.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/sns.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/event/cprtCardList.js"></script>
        <script>
            var v_paramCprtCardCtgCd = '<c:out value="${alliCardContDto.cprtCardCtgCd}"/>';
        </script>


    </t:putAttribute>
</t:insertDefinition>
