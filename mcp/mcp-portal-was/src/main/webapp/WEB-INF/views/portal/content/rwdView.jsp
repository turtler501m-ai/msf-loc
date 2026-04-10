<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">자급제 보상 서비스</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <style>
            .u-lh--28 {line-height: 1.75rem !important;}
            .u-width--18p {
                width: 18% !important;
                -webkit-box-flex: 0;
                -ms-flex: 0 0 auto;
                flex: 0 0 auto;
            }
            .rwdInfoLayout .c-sticker{border-radius: 0.5rem;}
            .rwdInfoLayout .bt-gray{border-top:  0.15rem solid #ccc;}
            .rwdInfoLayout .c-table th, .rwdInfoLayout .c-table td {
                padding: 0.625rem 1rem;
                font-size: 0.875rem;
            }
            .rwdInfoLayout .process-list{
                font-size: 0.875rem;
                margin-right: 2rem;
            }
            .rwdInfoLayout .process-list:last-child{margin-right: 0;}
            .rwdInfoLayout .process-list:nth-child(n+2)::before{left: -1.25rem;}
            .rwdInfoLayout .process-wrap{
                background: #f2eef7;
                padding: 1rem 0.5rem;
            }
            .rwdInfoLayout .u-ta-top{vertical-align: top;}
            .u-br--0 {border-right: 0 !important;}
        </style>
        <script src="/resources/js/portal/content/rwdView.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <!-- rwd service content -->
        </div>

        <!-- 로그인 여부 확인 -->
        <input type="hidden" name="loginYn" value="${loginYn}"/>

    </t:putAttribute>

</t:insertDefinition>