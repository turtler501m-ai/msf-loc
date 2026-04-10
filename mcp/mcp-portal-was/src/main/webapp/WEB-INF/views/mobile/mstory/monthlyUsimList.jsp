<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="metaTagAttr">
        <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
        <meta property="og:title" content="M Story I kt M모바일 공식 다이렉트몰"/>
    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/mstory/monthlyUsimList.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.placeholder.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <input type="hidden" name="yy" value=""/>
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">:
                <h2 class="ly-page__head c-flex">
                    M Story<a class="header-share-button" href="#"> <i
                        class="c-icon c-icon--share" id="snsShareBut" aria-hidden="true"></i>
                    </a>
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <input type="text" id="param" value="" hidden/>
            <input type="text" id="year" value="${year}" hidden/>
            <div class="c-tabs c-tabs--type2">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button is-active" id="monthlyM">월간M</button>
                    <button class="c-tabs__button" id="cust">고객소통</button>
                </div>
                <div class="c-tabs__panel" id="t1">
                    <div class="m-storage__select" id="yearSelect">
                        <select>

                        </select>
                    </div>
                    <!-- thumbnail image swiper-->
                    <div class="m-storage__swiper c-expand">
                        <div class="swiper-wrapper" id="mainList">

                        </div>
                    </div>
                    <!-- //thumbnail image swiper-->
                </div>
        </div>


        <script src="../../resources/js/mobile/vendor/swiper.min.js"></script>

    </t:putAttribute>
</t:insertDefinition>