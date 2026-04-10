<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

  <t:putAttribute name="titleAttr">요금명세서</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/personal/portal/pBillWayChgView.js?version=25.06.10"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">개인화 URL (요금명세서)</h2>
      </div>

      <div class="c-tabs c-tabs--type1">
        <div class="c-tabs__list">
          <a class="c-tabs__button" id="Fbill01" href="javascript:;">명세서 재발송</a>
          <a class="c-tabs__button is-active" id="Fbill02" href="javascript:;" aria-current="page">명세서 정보변경</a>
        </div>
      </div>

      <div class="c-tabs__panel u-block">
        <div class="c-row c-row--lg">
          <%@ include file="/WEB-INF/views/personal/portal/pCommCtn.jsp" %>
          <hr class="c-hr c-hr--title">
          <h4 class="c-heading c-heading--fs16 u-mt--64 u-mb--16">이용중인 명세서 정보</h4>
          <div class="c-table">
            <table>
              <caption>납부방법, 납부계정정보, 명세서유형, 납부기준일로 구성된 이용중인 명세서 정보</caption>
              <colgroup>
                <col style="width: 143px">
                <col>
              </colgroup>
              <tbody class="info-box">
              </tbody>
            </table>
          </div>
          <div class="c-form-wrap u-mt--48">
            <div class="c-form-row c-col2">
              <div class="c-form u-width--460">
                <label class="c-label" for="selCategory">변경 유형 선택</label>
                <select class="c-select" id="selCategory" name="selCategory">
                  <option value="MB">모바일(MMS)</option>
                  <option value="CB">이메일</option>
                  <option value="LX">우편</option>
                </select>
              </div>
              <div class="c-form u-hide" id="cateEmail">
                <div class="c-input-wrap u-mt--0">
                  <label class="c-label" for="emailAdr">이메일 주소</label>
                  <input class="c-input" id="emailAdr" type="text" placeholder="예 : ktm@ktm.com" maxlength="50">
                </div>
              </div>
            </div>
            <p class="c-bullet c-bullet--fyr u-co-sub-4 u-hide" id="cateMobile">모바일(MMS) 선택 시 각 회선으로 명세서가 발송됩니다.</p>
            <div class="c-form-group u-mt--48 u-hide" role="group" aira-labelledby="inpStateAddrHead" id="catePost">
              <h4 class="c-group--head" id="inpStateAddrHead">명세서 주소</h4>
              <div class="c-form-row c-col2">
                <div class="c-form">
                  <div class="c-input-wrap">
                    <label class="c-form__label c-hidden" for="postNo">우편번호</label>
                    <input class="c-input" id="postNo" type="text" placeholder="우편번호" readonly>
                    <button class="c-button c-button--sm c-button--underline" onclick="openPage('pullPopup', '/addPopup.do','','1');">우편번호찾기</button>
                  </div>
                </div>
                <div class="c-form">
                  <div class="c-input-wrap">
                    <label class="c-form__label c-hidden" for="address1">주소</label>
                    <input class="c-input" id="address1" type="text" placeholder="주소" readonly>
                  </div>
                </div>
              </div>
              <div class="c-form u-mt--16">
                <label class="c-label c-hidden" for="address2">상세주소</label>
                <input class="c-input" id="address2" type="text" placeholder="상세주소" name="inpAddress2" readonly>
              </div>
            </div>
          </div>
          <div class="c-button-wrap u-mt--56">
            <button class="c-button c-button--lg c-button--primary u-width--460" onclick="changeSubmit()" id="change-submit" disabled>변경</button>
          </div>
        </div>
      </div>
    </div>
  </t:putAttribute>
</t:insertDefinition>
