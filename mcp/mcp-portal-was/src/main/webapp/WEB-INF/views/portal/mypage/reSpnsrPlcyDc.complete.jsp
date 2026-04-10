<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">요금할인 재약정 신청</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript">
    history.pushState(null, null,"/mypage/reSpnsrPlcyDc.do");
    window.onpopstate = function (event){
        history.go("/mypage/reSpnsrPlcyDc.do");
    }
    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">요금할인 재약정 신청</h2>
      </div>
      <div class="c-row c-row--lg">
        <div class="complete u-ta-center">
          <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
          <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
            <b>요금할인 재약정</b>이
            <br>
            <b>완료</b>되었습니다
          </h3>
        </div>
        <div class="c-table u-mt--48">
          <table>
            <caption>고객명, 휴대폰번호, 요금제, 약정개월, 약정기간, 월 할인 금액으로 구성된 표</caption>
            <colgroup>
              <col style="width: 153px">
              <col>
            </colgroup>
            <tbody>
              <tr>
                <th class="u-ta-left" scope="row">고객명</th>
                <td class="u-ta-left">${searchVO.userName}</td>
              </tr>
              <tr>
                <th class="u-ta-left" scope="row">휴대폰번호</th>
                <td class="u-ta-left">${ searchVO.ctn }</td>
              </tr>
              <tr>
                <th class="u-ta-left" scope="row">요금제</th>
                <td class="u-ta-left">${mcpUserCntrMngDto.rateNm }</td>
              </tr>
              <tr>
                <th class="u-ta-left" scope="row">약정개월</th>
                <td class="u-ta-left">${moscSdsInfo.engtPerdMonsNum}개월</td>
              </tr>
              <fmt:parseDate value="${moscSdsInfo.engtAplyStDate}" pattern="yyyyMMdd" var="engtAplyStDateTmp"/>
              <fmt:formatDate value="${engtAplyStDateTmp}" pattern="yyyy.MM.dd" var="engtAplyStDate"/>
              <fmt:parseDate value="${moscSdsInfo.engtExpirPamDate}" pattern="yyyyMMdd" var="engtExpirPamDateTmp"/>
              <fmt:formatDate value="${engtExpirPamDateTmp}" pattern="yyyy.MM.dd" var="engtExpirPamDate"/>
              <tr>
                <th class="u-ta-left" scope="row">약정기간</th>
                <td class="u-ta-left">${engtAplyStDate }~${engtExpirPamDate }</td>
              </tr>
              <tr>
                <th class="u-ta-left" scope="row">월 할인 금액</th>
                <td class="u-ta-left"><fmt:formatNumber value="${dcSuprtAmtVat}" type="number"/>원</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="c-button-wrap u-mt--56">
          <a class="c-button c-button--lg c-button--primary c-button--w460" href="/mypage/reSpnsrPlcyDc.do">확인</a>
        </div>
        <b class="c-flex c-text c-text--fs15 u-mt--48">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </b>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <li class="c-text-list__item u-co-gray">정지기간은 약정기간에 포함되며, 정지기간 중에는 요금할인이 제공되지 않습니다.</li>
          <li class="c-text-list__item u-co-gray">약정 기간 내 해지 시 위약금이 발생됩니다.</li>
          <li class="c-text-list__item u-co-gray">재약정은 당월 말일까지 약정 철회가 가능합니다. 상세내용은 고객센터(1899-5000)로 문의 바랍니다.</li>
        </ul>
      </div>
    </div>
</t:putAttribute>
</t:insertDefinition>
