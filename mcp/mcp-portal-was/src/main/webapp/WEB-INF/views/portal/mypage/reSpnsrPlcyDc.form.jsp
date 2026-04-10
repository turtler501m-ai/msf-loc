<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">요금할인 재약정 신청</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
    <script type="text/javascript" src="/resources/js/portal/mypage/reSpnsrPlcyDc.form.js"></script>
    <script type="text/javascript">
    /* history.pushState(null, null,"/mypage/reSpnsrPlcyDc.do");
    window.onpopstate = function (event){
        history.go("/mypage/reSpnsrPlcyDc.do");
    } */
    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <form name="selfFrm" id="selfFrm" action="/mypage/reSpnsrPlcyDc.do" method="post">
        <input type="hidden" name="ncn" value="" >
    </form>
    <form name="frm" id="frm" action="/mypage/moscSdsSvcRegView.do" method="post">
    <input type="hidden" name="phoneNum" id="phoneNum" value="${phoneNum}">
    <input type="hidden" name="svcCntrNo" id="mocSvcCntrNo" value="${searchVO.ncn}" >
    <input type="hidden" id="rateSoc" name="rateSoc" value="${mcpUserCntrMngDto.soc}"  >
    <input type="hidden" id="mCode" name="mCode" value="rate"  >
    <input type="hidden" id="ncn" name="ncn" value=""  >
    <input type="hidden" id="engtPerd" name="engtPerd" value=""  >
    <input type="hidden" id="ctn" name="ctn" value=""  >
    <input type="hidden" id="certifyYn" name="certifyYn"  value=""/>
    <input type="hidden" id="isAuth" name="isAuth"  >
    <input type="hidden" id="isDlvrySele" name="isDlvrySele"  >
    </form>
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">요금할인 재약정 신청</h2>
      </div>
      <div class="c-row c-row--lg">
        <h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
        <div class="c-select-search">
        <div class="phone-line">
            <span class="phone-line__cnt">${searchVO.moCtn}</span>
        </div>
          <div class="c-form--line">
            <label class="c-label c-hidden" for="selSvcCntrNo">회선 선택</label>
            <select class="c-select" name="selSvcCntrNo" id="selSvcCntrNo">
                <c:forEach items="${cntrList}" var="item">
                     <c:choose>
                        <c:when test="${maskingSession eq 'Y'}">
                            <option value="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''} >${item.formatUnSvcNo}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''} >${item.cntrMobileNo}</option>
                        </c:otherwise>
                   </c:choose>
                </c:forEach>
            </select>
          </div>
          <button type="button" class="c-button c-button-round--xsm c-button--primary u-ml--10" href="javascript:void(0);" onclick="searchSvcCntrNo();">조회</button>
          <c:if test="${empty maskingSession}">
      	  	<div class="masking-badge-wrap">
		        <div class="masking-badge">
		        	<a class="masking-badge__button" href="/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기" >
		        		<i class="masking-badge__icon" aria-hidden="true"></i>
		 				<p>가려진(*)<br />정보보기</p>
		       		</a>
		        </div>
	        </div>
        </c:if>
        </div>
        <hr class="c-hr c-hr--title">
        <h4 class="c-heading c-heading--fs16">가입정보</h4>
        <div class="c-table u-mt--16">
          <table>
            <caption>고객명, 휴대폰번호, 요금제로 구성된 가입정보</caption>
            <colgroup>
              <col style="width: 147px">
              <col>
            </colgroup>
            <tbody>
              <tr>
                <th scope="row">고객명</th>
                <td class="u-ta-left">${searchVO.userName}</td>
              </tr>
              <tr>
                <th scope="row">휴대폰번호</th>
                <td class="u-ta-left">${ searchVO.ctn }</td>
              </tr>
              <tr>
                <th scope="row">요금제</th>
                <td class="u-ta-left">${mcpUserCntrMngDto.rateNm }</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="c-form-wrap u-mt--48">
          <div class="c-form-group" role="group" aira-labelledby="radContractHead">
            <h4 class="c-group--head" id="radContractHead">약정기간 선택</h4>
            <div class="c-checktype-row c-col4">
              <input class="c-radio c-radio--button" id="radContract1" type="radio" name="instNom" value="12" checked="">
              <label class="c-label" for="radContract1">12개월</label>
              <input class="c-radio c-radio--button" id="radContract2" type="radio" name="instNom" value="24">
              <label class="c-label" for="radContract2">24개월</label>
            </div>
          </div>
        </div>
        <div class="c-button-wrap u-mt--56">
          <button class="c-button c-button--lg c-button--primary c-button--w460" id="btnSvcPreChk">약정 가능 여부 조회</button>
        </div>
      </div>
    </div>

    <div class="c-modal c-modal--md" id="contract-result-modal" role="dialog" aria-labelledby="#contract-result-modal__title">
    <div class="c-modal__dialog" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="modalSMSTitle">약정 가능 여부 결과</h2>
          <button class="c-button" data-dialog-close="">
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <span id="modalResultArea" style="display: none;">
          <h3 class="c-heading c-heading--fs20 c-heading--regular" id="srchRstHArea">조회 결과</h3>
          <!--조회 결과 있는 경우-->
          <div class="c-table u-mt--32" id="divResult">
            <table>
              <caption>할인전 월 기본료, 재약정 할인금액, 재약정 시 월 통신료, 총 추가 할인금액 (약정기간 기준)으로 구성된 약정 가능 여부 조회 결과 정보</caption>
              <colgroup>
                <col style="width: 274px">
                <col>
              </colgroup>
              <tbody>
                <tr>
                  <th class="u-ta-left" scope="row">할인전 월 기본료</th>
                  <td class="u-ta-right" id="divResult_td_one"></td>
                </tr>
                <tr>
                  <th class="u-ta-left" scope="row">재약정 할인금액</th>
                  <td class="u-ta-right" id="divResult_td_two"></td>
                </tr>
                <tr>
                  <th class="u-ta-left" scope="row">재약정 시 월 통신료</th>
                  <td class="u-ta-right" id="divResult_td_three"></td>
                </tr>
                <tr>
                  <th class="u-ta-left" scope="row">총 추가 할인금액 (약정기간 기준)</th>
                  <td class="u-ta-right" id="divResult_td_four"></td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="c-button-wrap u-mt--40" id="divResultBtnArea">
            <button type="button" class="c-button c-button--lg c-button--primary c-button--w460" data-dialog-close="" href="javascript:void(0);" onclick="goMosc();">약정 조건 재약정</button>
          </div>
          </span>
          <!--조회 결과 없는 경우-->
          <span id="modalResultNoArea" style="display: none;">
          <div class="u-mt--40 u-ta-center" id="divNoResult">
            <p class="c-text c-text--fs17 u-co-gray-9 u-mb--16">요금할인 재약정이 불가능합니다.
              <br>요금할인 재약정 대상 요금제가 아닙니다.
            </p>
            <span class="c-bullet c-bullet--fyr u-co-sub-4">고객센터 (가입휴대폰에서 114)를 통하여 재약정 문의가 가능합니다.</span>
          </div>
          <div class="c-button-wrap u-mt--40" id="divNoResultBtnArea">
            <button class="c-button c-button--lg c-button--primary c-button--w460" data-dialog-close="">확인</button>
          </div>
          </span>

        </div>
      </div>
    </div>
  </div>
</t:putAttribute>
</t:insertDefinition>
