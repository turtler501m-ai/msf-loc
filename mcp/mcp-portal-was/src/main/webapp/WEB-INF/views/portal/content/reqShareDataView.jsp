<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">함께쓰기 </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
          <script src="/resources/js/portal/content/reqShareDataView.js"></script>
          <script type="text/javascript">
               history.pushState(null, null,location.href);
                  window.onpopstate = function (event){
                    location.href = "/myShareDataCntrInfo.do";
               }


           </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
         <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">함께쓰기</h2>
      </div>
      <div class="c-row c-row--lg">
        <h3 class="c-heading c-heading--fs16 u-mb--16">내 정보</h3>
        <div class="c-table">
          <table>
            <caption>전화번호, 요금제를 포함한 내 정보</caption>
            <colgroup>
              <col style="width: 18%">
              <col>
            </colgroup>
            <tbody class="c-table__left">
              <tr>
                <th scope="row">휴대폰 번호</th>
                <td>${reShareDataResDto.mySvcNo}</td>
              </tr>
              <tr>
                <th scope="row">요금제</th>
                <td>${reShareDataResDto.mySocNm}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <h3 class="c-heading c-heading--fs16 u-mt--48">함께쓰기 회선 등록</h3>
        <div class="c-form-row c-col2">
          <div class="c-form-field">
            <!-- [2022-01-17] has-value클래스추가-->
            <div class="c-form__input has-value">
              <input class="c-input" id="reqCustName" type="text" placeholder="고객명 입력" value="${reShareDataResDto.reqCustName}"  readonly>
              <label class="c-form__label" for="reqCustName">고객명</label>
            </div>
          </div>
          <div class="c-form-field">
            <!-- [2022-01-17] is-readonly 클래스 추가-->
            <div class="c-form__input-group is-readonly">
              <!-- [2022-01-17] has-value 클래스추가-->
              <div class="c-form__input c-form__input-division has-value">
                <input class="c-input--div3 numOnly" id="reqSvcNo1" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리" value="${fn:substring(reShareDataResDto.mskReqSvcNo, 0, 3)}" readonly>
                <span>-</span>
                <input class="c-input--div3 numOnly" id="reqSvcNo2" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 중간자리" value="${fn:substring(reShareDataResDto.mskReqSvcNo, 4, 8)}" readonly>
                <span>-</span>
                <input class="c-input--div3 numOnly" id="reqSvcNo3" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 뒷자리" value="${fn:substring(reShareDataResDto.mskReqSvcNo, 9, 13)}" readonly>
                <label class="c-form__label" for="reqSvcNo3">휴대폰 번호</label>
              </div>
            </div>
          </div>
          <input type="hidden" name="targetTerms" id="targetTerms" value=""/>
            <input type="hidden" name="certifyYn" id="certifyYn" value=""/>
            <input type="hidden" id="isAuth" name="isAuth">
            <input type="hidden" id="contractNum" name="contractNum" value="${reShareDataResDto.contractNum}"/>
            <input type="hidden" id="retvGubunCd" name="retvGubunCd" value="${retvGubunCd}" />
            <input type="hidden" id="reqSvcNo" 	name="reqSvcNo" value="${reShareDataResDto.reqSvcNo}" />
            <input type="hidden" id="mskReqSvcNo" 	name="mskReqSvcNo" value="${reShareDataResDto.mskReqSvcNo}" />
            <input type="hidden" id="ncn" name="ncn" value="${reShareDataResDto.ncn}" />


        </div>
        <h3 class="c-form__title u-mt--48">약관동의</h3>
        <div class="c-agree">
         <c:set var="sdatList" value="${nmcp:getCodeList(Constants.SDAT_CD)}" />
          <c:forEach var="item" items="${sdatList}" varStatus="status">

          <div class="c-agree__item">
            <div class="c-agree__inner">
             <button class="c-button c-button--xsm"  onclick="btnRegDtl('cdGroupId1=${Constants.SDAT_CD}&cdGroupId2=${item.dtlCd}');">
                  <span class="c-hidden">${item.dtlCdNm} 상세팝업 보기</span>
                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
             </button>
              <input class="c-checkbox"id="chkAgree"${status.index} type="checkbox" name="chkAgree" href="javascript:void(0);" onclick="chkEvent()">
              <label class="c-label" for="chkAgree"${status.index}>${item.dtlCdNm}</label>
            </div>

          </div>
           </c:forEach>
        </div>
        <div class="c-button-wrap u-mt--56">
          <a class="c-button c-button--lg c-button--gray u-width--220"  href="javascript:void(0);" onclick="btn_cancel();">취소</a>
          <a class="c-button c-button--lg c-button--primary u-width--220 is-disabled" href="javascript:void(0);" id="btnReg" onclick="btn_reg();">함께쓰기 신청</a>
        </div>
      </div>
    </div>


    </t:putAttribute>
</t:insertDefinition>

