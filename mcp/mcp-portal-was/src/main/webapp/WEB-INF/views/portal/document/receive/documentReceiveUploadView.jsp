<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

	<t:putAttribute name="titleAttr">서류 등록</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.09.30"></script>
		<script type="module" src="/resources/js/portal/document/receive/documentReceiveUploadView.js?version=25.11.11"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
        <input type="hidden" id="docRcvId" value="<c:out value='${docRcvId}'/>" />
        <input type="hidden" id="scanInterfaceUrl" value="<c:out value='${scanInterfaceUrl}'/>" />

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">서류등록</h2>
            </div>
            <div class="c-row c-row--lg">
                <div class="file-upload-title-wrap">
                    <h3 class="file-upload-title">서류등록</h3>
                    <button id="addFileBtn" class="file-upload-title-btn">
                        <span>서류첨부 추가</span>
                        <i class="c-icon c-icon--plus-type2" aria-hidden="true"></i>
                    </button>
                </div>
                <ul class="file-upload-list" id="fileUploadList">
                    <li class="file-upload-list__item">
                        <div class="file-upload-list__select">
                            <span class="file-upload-list__number">01</span>
                            <label class="c-label" for="select001">서류선택</label>
                            <select class="c-select" id="select001">
                                <option label="명의자신분증" selected="">E0001</option>
                                <option label="명의자신분증2">E0002</option>
                                <option label="명의자신분증3">E0003</option>
                                <option label="명의자신분증4">E0004</option>
                                <option label="명의자신분증5">E0005</option>
                            </select>
                        </div>
                        <div class="file-upload-list__attachment">
                            <div class="file-upload-wrap">
                                <label class="c-label" for="fileName1">첨부</label>
                                <input type="text" class="file-name" id="fileName1" readonly="">
                                <input type="file" class="file-input" id="fileUpload1">
                                <label for="fileUpload1" class="file-label">파일선택</label>
                            </div>
                        </div>
                    </li>
                </ul>
                <ul class="c-text-list c-bullet c-bullet--hyphen u-fs-16 u-mt--48">
                    <li class="c-text-list__item u-co-gray">
                        서비스 이용을 위해 필요한 최소한의 정보만 제출해주시고, 그 외 불필요한 개인정보는 반드시 마스킹(가림) 처리 후 업로드해 주시기 바랍니다.<br>
                        또한, 불필요한 파일이 포함되지 않았는지 업로드 전에 한 번 더 확인해 주시기 바랍니다.
                    </li>
                    <li class="c-text-list__item u-co-gray">
                        서명이 필요한 문서의 경우 반드시 자필로 작성해주시고 제출된 서류가 미비하거나 확인이 어려운 경우 재접수 요청드릴 수 있습니다.
                    </li>
                    <li class="c-text-list__item u-co-gray">
                        신분증이나 가족관계증명서처럼 본인 확인이 필요한 서류는 관련 법령*에 따라 확인이 필요하니, 이 점 양해 부탁드립니다.<br><br>
                        [관련 근거]<br>
                        전기통신사업법 제32조의5(부정가입방지시스템 구축)<br>
                        전기통신사업법 시행령 제37조의7(계약 체결 시 본인확인)
                    </li>
                </ul>
                <div class="c-button-wrap u-mt--56">
                    <button class="c-button c-button--lg c-button--primary c-button--w460" id="sendBtn">확인</button>
                </div>
            </div>
        </div>
	</t:putAttribute>
</t:insertDefinition>