<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		
	</t:putAttribute>
	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
	      <div class="sample-box" style="display: flex; flex-direction: column; justify-content: center; align-items: center; width: 100%; height: 70vh">
	        <button class="c-button c-button--full c-button--white" data-dialog-trigger="#join-counseling-dialog" onclick="openPage('pullPopup','/m/retention/retentionNoticeView.do');" >약정만료 알림 popup 호출</button>
	        
	      </div>
	    </div>
	</t:putAttribute>
</t:insertDefinition>