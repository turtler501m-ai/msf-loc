<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">


	<script type="text/javascript" src="/resources/js/common.js"></script>
	<script type="text/javascript" src="/resources/js/swipe.js"></script>
	<script type="text/javascript">
   			history.pushState(null, null,location.href);
  				window.onpopstate = function (event){   	   				
					location.href = "/m/myShareDataCntrInfo.do";
      		 }
   		</script>
	</t:putAttribute>

 <t:putAttribute name="contentAttr">

	<div id="content">
	<div class="ly-wrap">
    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">함께쓰기<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="complete">
        <div class="c-icon c-icon--complete" aria-hidden="true">
          <span></span>
        </div>
        <p class="complete__heading">
          <b>함께쓰기</b>신청이
          <br>
          <b>완료</b>되었습니다.
        </p>
      </div>
	   <form name="frm" id="frm" method="post" action="/m/content/myShareDataView.do">
	  </form>
      <h3 class="c-heading c-heading--type2 u-mb--12">내 정보</h3><!-- [2021-12-02] 간격 유틸클래스 삭제-->
      <div class="c-table">
        <table>
          <caption>내 정보</caption>
          <colgroup>
            <col style="width: 30%">
            <col style="width: 70%">
          </colgroup>
          <tbody>
            <tr>
              <th scope="row">휴대폰 번호</th>
              <td class="u-ta-left">${reShareDataResDto.mySvcNo}</td>
            </tr>
            <tr>
              <th scope="row">요금제</th>
              <td class="u-ta-left">${reShareDataResDto.mySocNm}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <h3 class="c-heading c-heading--type2 u-mb--12">함께쓰기 등록 회선</h3><!-- [2021-12-02] 간격 유틸클래스 삭제-->
      <div class="c-table">
        <table>
          <caption>함께쓰기 등록 회선</caption>
          <colgroup>
            <col style="width: 30%">
            <col style="width: 70%">
          </colgroup>
          <tbody>
            <tr>
              <th scope="row">고객명</th>
              <td class="u-ta-left">${reShareDataResDto.reqCustName}</td>
            </tr>
            <tr>
              <th scope="row">휴대폰 번호</th>
              <td class="u-ta-left">${reShareDataResDto.reqSvcNo}</td>
            </tr>
            <tr>
              <th scope="row">요금제</th>
              <td class="u-ta-left">${reShareDataResDto.reqSocNm}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <hr class="c-hr c-hr--type2">
      <p class="c-bullet c-bullet--fyr u-co-gray">함께쓰기 신청 완료 시 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.</p>
      <div class="c-button-wrap">
        <button class="c-button c-button--full c-button--primary"  onclick="$('#frm').submit();">확인</button>
      </div>
    </div>
    </div>
    </div>
</t:putAttribute>
</t:insertDefinition>