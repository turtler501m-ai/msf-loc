<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="titleAttr">자급제 보상 서비스</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <style>
            .u-lh--24 {line-height: 1.5rem !important;}
            .rwdInfoLayout .c-sticker{border-radius: 0.5rem;}
            .rwdInfoLayout .c-table th, .rwdInfoLayout .c-table td{
                padding: 0.25rem !important;
                font-size: 0.6875rem;
            }
            .rwdInfoLayout .fs-11{font-size: 0.6875rem !important;}
            .u-width--55p {
                width: 55% !important;
                -webkit-box-flex: 1;
                -ms-flex: 1;
                flex: 1;
            }
            .rwdInfoLayout .u-table-bt-gray{border-bottom: 0.0625rem solid #e8e8e8 !important;}
        </style>
        <script src="/resources/js/mobile/content/rwdView.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <!-- rwd service content -->
        </div>

        <!-- 로그인 여부 확인 -->
        <input type="hidden" name="loginYn" value="${loginYn}"/>
    </t:putAttribute>

</t:insertDefinition>