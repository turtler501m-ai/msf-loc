<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">쉐어링 </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    <script src="/resources/js/portal/content/reqSharingView.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript">
                 history.pushState(null, null,location.href);
                window.onpopstate = function (event){
                location.href = "/mySharingCntrInfo.do";
             }
         </script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
      <form name="frm" id="frm" method="post" action="/mySharingCntrInfo.do">

      </form>
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">쉐어링</h2>
      </div>
      <div class="c-row c-row--lg">
        <div class="complete u-ta-center">
          <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
          <p class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
            <b>데이터 쉐어링 신청</b>이
            <br>
            <b>완료</b>되었습니다
          </p>
        </div>
        <h3 class="c-heading c-heading--fs16 u-mt--64 u-mb--16">대표회선(모회선)</h3>
        <div class="c-table">
          <table>
            <caption>전화번호, 요금제를 포함한 대표회선(모회선) 정보</caption>
            <colgroup>
              <col style="width: 15%">
              <col>
            </colgroup>
            <tbody class="c-table__left">
              <tr>
                <th scope="row">전화번호</th>
                <td>${myShareDataResDto.svcNo}</td>
              </tr>
              <tr>
                <th scope="row">요금제</th>
                <td>${myShareDataResDto.socNm}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <h3 class="c-heading c-heading--fs16 u-mt--48 u-mb--16">데이터쉐어링 등록 회선</h3>
        <div class="c-table">
          <table>
            <caption>전화번호, 요금제를 포함한 데이터쉐어링 등록 회선 정보</caption>
            <colgroup>
              <col style="width: 15%">
              <col>
            </colgroup>
            <tbody class="c-table__left">
              <tr>
                <th scope="row">전화번호</th>
                <td>${myShareDataResDto.opmdSvcNo}</td>
              </tr>
              <tr>
                <th scope="row">요금제</th>
                <td>데이터쉐어링 전용요금제</td>
              </tr>
            </tbody>
          </table>
        </div>
        <hr class="c-hr c-hr--basic u-mt--48 u-mb--32">
        <p class="c-bullet c-bullet--dot u-co-gray">데이터쉐어링 신청 완료 시 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.</p>
        <div class="c-button-wrap u-mt--56">
          <a class="c-button c-button--lg c-button--primary u-width--460" href="javascript:void(0);" onclick="$('#frm').submit();">확인</a>
        </div>
      </div>
    </div>

    </t:putAttribute>
</t:insertDefinition>
