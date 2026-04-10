<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
	<script src="/resources/js/mobile/content/reqShareDataView.js"></script>
	<script type="text/javascript">
   			history.pushState(null, null,location.href);
  				window.onpopstate = function (event){   	   				
					location.href = "/m/myShareDataCntrInfo.do";
      		 }
	

   		</script>

</t:putAttribute>

 <t:putAttribute name="contentAttr">
	<div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">함께쓰기<button class="header-clone__gnb"></button>
        </h2>
      </div>
       <input type="hidden" name="" id="" value=""/>
      <div class="c-form u-mt--40">
        <div class="c-form__title flex-type u-mb--12">내 정보</div><!-- [2021-12-02] 간격 유틸클래스 삭제-->
        <div class="c-table">
          <table>
            <caption>결합 회선</caption>
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
      </div>

      <div class="c-form u-mt--40">
        <div class="c-form__title flex-type u-mb--12">함께쓰기 회선 등록</div>
        <div class="c-form__group" role="group" aria-labeledby="inpHP">
          <div class="c-form__input">
            <input class="c-input" id="reqCustName" name="reqCustName" type="text" placeholder="" title="고객명 입력" value="${reShareDataResDto.reqCustName}" readonly>
            <label class="c-form__label" for="reqCustName">고객명</label>
          </div>
          <div class="c-form__input">
            <input class="c-input" id="mskReqSvcNo" type="tel" placeholder="'-'없이 입력" title="휴대폰 번호 입력" value="${reShareDataResDto.mskReqSvcNo}" readonly>
            <label class="c-form__label" for="mskReqSvcNo">휴대폰</label>
          </div>
        </div>
      </div>
      <div class="c-agree u-mt--32">
      <input type="hidden" name="targetTerms" id="targetTerms" value=""/>
      <input type="hidden" name="certifyYn" id="certifyYn" value=""/>
	  <input type="hidden" id="isAuth" name="isAuth">
	  <input type="hidden" id="contractNum" name="contractNum" value="${reShareDataResDto.contractNum}"/>
	  <input type="hidden" id="retvGubunCd" name="retvGubunCd" value="${retvGubunCd}" />
	  <input type="hidden" id="reqSvcNo" 	name="reqSvcNo" value="${reShareDataResDto.reqSvcNo}" />
	  <input type="hidden" id="mskReqSvcNo" 	name="mskReqSvcNo" value="${reShareDataResDto.mskReqSvcNo}" />
      <input type="hidden" id="ncn" name="ncn" value="${reShareDataResDto.ncn}" />

      <c:set var="sdatList" value="${nmcp:getCodeList(Constants.SDAT_CD)}" />
      <c:forEach var="item" items="${sdatList}" varStatus="status">
        <div class="c-agree__item">
          <input class="c-checkbox" id="chkAgree"${status.index} type="checkbox" name="chkAgree" onclick="chkEvent()">
          <label class="c-label" for="chkAgree"${status.index}>${item.dtlCdNm}</label>
          <button class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=${Constants.SDAT_CD}&cdGroupId2=${item.dtlCd}');">
            <span class="c-hidden">상세보기</span>
            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
          </button>
        </div>
       </c:forEach>
      </div>
      <div class="c-button-wrap">
        <a class="c-button c-button--full c-button--gray" href="javascript:void(0);" onclick="btn_cancel();">취소</a>
        <button class="c-button c-button--full c-button--primary is-disabled" id="btnReg" onclick="btn_reg();">함께쓰기 신청</button>
      </div>
    </div>
</t:putAttribute>
</t:insertDefinition>