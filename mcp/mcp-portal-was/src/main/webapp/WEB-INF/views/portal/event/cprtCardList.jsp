<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="metaTagAttr">
        <!-- 페이스북 Open Graph Tag -->
        <meta property="fb:app_id" content="1694564987490429">
        <meta property="og:type" content="website" />
        <meta property="og:title" content="제휴카드 | kt M모바일 공식 다이렉트몰" id="meta_og_title" />
        <meta property="og:url" content="https://www.ktmmobile.com/event/cprtCardList.do" id="meta_og_url" />
        <meta property="og:description" content="" id="meta_og_description" />
        <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
        <meta name="keywords" content="${nmcp:getCodeDesc('MetaKeywords','01')}">
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div id="subbody_loading" style="display:flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
            <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
        </div>

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">제휴카드</h2>
            </div>

         <!--  배너시작 -->
            <div class="cprtCard-banner">
                <c:if test="${!empty bannerInfo}">
                    <div class="swiper-container" id="swiperCprtCard">
                        <ul class="swiper-wrapper">
                            <c:forEach var="bannerInfo" items="${bannerInfo}">
                                <c:set var="target" value="_self " />
                                <c:if test="${bannerInfo.linkTarget eq 'Y' }">
                                    <c:set var="target" value="_blank" />
                                    <c:set var="title" value="새창열림" />
                                </c:if>
                                <c:choose>
                                    <c:when test="${bannerInfo.linkTarget eq null || bannerInfo.linkTarget eq ''}">
                                        <li class="swiper-slide" tabindex="0" aria-label="" <c:if test="${!empty bannerInfo.bgColor}">style="background:<c:out value="${bannerInfo.bgColor}"/>"</c:if>>
                                            <div class="u-box--w1100 u-margin-auto">
                                                <img src="<c:out value="${bannerInfo.bannImg}"/>" alt="<c:out value="${bannerInfo.imgDesc}"/>">
                                            </div>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="swiper-slide" tabindex="0" aria-label="" <c:if test="${!empty bannerInfo.bgColor}">style="background:<c:out value="${bannerInfo.bgColor}"/>"</c:if>>
                                            <div class="u-box--w1100 u-margin-auto">
                                                <a <c:if test="${bannerInfo.linkTarget eq 'Y' }"> title ='새창열림' </c:if> href="javascript:void(0);"
                                                    onclick="fn_go_banner('<c:out value="${bannerInfo.linkUrlAdr}"/>','<c:out value="${bannerInfo.bannSeq}"/>','<c:out value="${bannerInfo.bannCtg}"/>','<c:out value="${target}"/>')">
                                                    <img src="${bannerInfo.bannImg}" alt="<c:out value="${bannerInfo.imgDesc}"/>">
                                                </a>
                                            </div>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
            </div>
            <!--  배너끝 -->

            <div class="c-row c-row--xlg cprtCard-wrap">
                <div class="c-tabs c-tabs--type2">
                    <div  class="c-tabs__list u-mb--0" role="tablist">
                        <div class="swiper-banner" id="swiperCprtCard">
                            <div class="swiper-container">
                                  <div class="swiper-wrapper" id="mainTabList">

                                  </div>
                            </div>
                            <button class="swiper-button-next" type="button"><span class='c-hidden'>다음</span></button>
                            <button class="swiper-button-prev" type="button"><span class='c-hidden'>이전</span></button>
                         </div>
                    </div>
                </div>

                <%-- Panel 정보 --%>
                <div id="mainTabPanel" class="is-active">
                </div>
            </div>
        </div>

        <%-- SNS 공유하기 버튼 --%>
        <div class="c-modal c-modal--sm" id="modalShareAlert" role="dialog" tabindex="-1" aria-labelledby="#modalShareAlertTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body u-ta-center">
                        <h3 class="c-heading c-heading--fs22 u-fw--bold">공유하기</h3>
                        <div class="c-button-wrap c-flex u-mt--32">
                            <a class="c-button" href="javascript:void(0);" onclick="kakaoShare(); return false;">
                                <span class="c-hidden">카카오</span>
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
                        <div class="c-button-wrap u-mt--48">
                            <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bluebird.core.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/portal/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/validationCheck.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/sns.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/portal/event/cprtCardList.js"></script>
        <script>
            var v_paramCprtCardCtgCd = '<c:out value="${alliCardContDto.cprtCardCtgCd}"/>';
        </script>

    </t:putAttribute>
</t:insertDefinition>