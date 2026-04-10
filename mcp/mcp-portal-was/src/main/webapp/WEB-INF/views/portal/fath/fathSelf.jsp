<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutNoGnbDefault">

  <t:putAttribute name="titleAttr">셀프안면인증 URL</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.12.19"></script>
    <script type="text/javascript" src="/resources/js/portal/fath/fathSelf.js?version=26.03.03"></script>
    <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=25.12.19"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js?version=25.12.19"></script>
  </t:putAttribute>
  
  <t:putAttribute name="contentAttr">

    <input type="hidden" id="resNo" name="resNo" value="${resNo}"/>
    <input type="hidden" id="operType" name="operType" value="${operType}"/>
    <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv"/>

    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">셀프안면인증 URL</h2>
      </div>
      <div class="c-row c-row--lg">
        <div class="c-form-wrap _isFathCert" >
          <div class="c-button-wrap u-mt--40">
            <button id="btnFathUrlRqt" class="c-button c-button--md c-button--mint c-button--w460">안면인증 URL 받기</button>
          </div>
  
          <div class="c-button-wrap u-mt--40">
            <button id="btnFathTxnRetv" class="c-button c-button--md c-button--mint c-button--w220" disabled>안면인증 결과 확인</button>
            <button id="btnFathSkip" class="c-button c-button--md c-button--gray c-button--w220" onclick="requestFathSkip();" disabled>안면인증 SKIP</button>
          </div>
        </div>
      </div>
    </div>
    
  </t:putAttribute>
</t:insertDefinition>
