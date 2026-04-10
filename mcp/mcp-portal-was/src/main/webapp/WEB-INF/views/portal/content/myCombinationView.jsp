<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">유무선결합</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script src="/resources/js/portal/content/myCombinationView.js"></script>
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
        <h2 class="c-page--title">유무선결합</h2>
      </div>
      <div class="ly-page--deco u-bk--blue">
        <div class="ly-avail--wrap">
          <h3 class="c-headline">KT–알뜰폰 유무선결합이란?</h3>
          <p class="c-headline--sub">kt M모바일 상품을 이용 중인 고객이 동일 명의로 KT 유/무선 상품을
            <br>사용중일 경우 결합하여 할인 혜택을 제공하는 서비스입니다.
          </p>
          <div class="c-button-wrap c-button-wrap--left">
          <c:set var="combiInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />
                  <c:forEach var="item" items="${combiInfo}" varStatus="status">
                   <c:if test = "${Constants.COMBI_INFO_CD eq item.dtlCd}">
                       <button class="c-button c-button--underline c-button--sm u-co-white" type="button"  onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">자세히보기</button>
                   </c:if>
                   </c:forEach>
          </div>
          <img class="c-headline--deco" src="/resources/images/portal/content/img_combination.png" alt="" aria-hidden="true">
        </div>
      </div>
      <div class="c-row c-row--lg">
        <div class="c-form-wrap u-mt--48">
          <div class="c-form-group" role="group" aria-labelledby="formGroupA">
            <div class="c-group--head">
              <h3 id="formGroupA">내 정보</h3>
              <button class="c-button c-button--underline" type="button" onclick="btn_combiPop();">결합내역 조회</button>
            </div>
            <div class="c-box c-box--type1 c-box--round">
             <div class="c-form-row c-col2">
              <div class="c-form-field">
                <div class="c-form__select u-mt--0">
                  <select class="c-select chgNcn" name="ncn" id="ncn">
                      <c:forEach items="${cntrList}" var="item">
                        <option value="${item.svcCntrNo }" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.cntrMobileNo }</option>
                    </c:forEach>
                  </select>
                  <label class="c-form__label" for="ncn">휴대폰 번호</label>
                </div>
              </div>
              <div class="c-form-field">
                <div class="c-form__input u-mt--0 has-value">
                  <input class="c-input" id="rateNm" type="text" disabled value="${mcpUserCntrMngDto.rateNm}">
                  <label class="c-form__label" for="rateNm">요금제</label>
                </div>
              </div>
              <button class="c-button c-button-round--md c-button--primary u-width--105 u-ml--24" onclick="btn_search();">조회</button>
              </div>
            </div>
          </div>
        </div>
        <input type="hidden" name="combiYn" id="combiYn" value=""/>

        <!-- case1 : 결합불가인 경우-->
        <!-- case1-1: 회선이 일시정지 상태일 경우-->
        <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64 combichkYn1" style="display: none;">
          <p class="c-text-box__text">현재 회선을
            <br>
            <b class="u-co-red">사용 중인 고객만 결합이 가능합니다.</b>
          </p>
          <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
        </div><!-- //-->
        <!-- case1-2: 회선이 법인, 외국인, 개인사업자의 경우-->
        <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64 combichkYn2" style="display: none;">
          <p class="c-text-box__text">개인고객만
            <br>
            <b class="u-co-red">가입이 가능합니다.</b>
          </p>
          <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
        </div><!-- //-->
        <!-- case1-3: 대상 요금제가 선불, 데이터전용, 데이터 쉐어링 요금제일 경우-->
        <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64 combichkYn3" style="display: none;">
          <p class="c-text-box__text">해당 요금제는
            <br>
            <b class="u-co-red">결합이 불가합니다.</b>
          </p>
          <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
        </div><!-- //-->
        <!-- //-->
        <!-- case2 : 결합가능한 경우-->
        <div class="c-button-wrap u-mt--40 combichkReg" style="display: none;">
          <button class="c-button c-button-round--md c-button--mint u-width--460"  onclick="btn_combiYn();">결합 가능 상품 조회</button>
        </div><!-- //-->
        <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">알려드립니다.</h3>
        <hr class="c-hr c-hr--title">
        <ul class="c-text-list c-bullet c-bullet--dot">
          <li class="c-text-list__item u-co-gray">해당 서비스는 kt 와 kt M모바일의 결합 시 할인 혜택을 제공하는 서비스 입니다.</li>
          <li class="c-text-list__item u-co-gray">해당 서비스는 동일명의 kt 유선(신규), 무선(신규/우수기변/재약정) 고객과 kt M모바일의 직영온라인 기존/신규가입고객만 결합이 가능합니다.</li>
          <li class="c-text-list__item u-co-gray">kt 상품 당 kt M모바일의 가입 가능 회선은 1회선입니다.</li>
          <li class="c-text-list__item u-co-gray">결합 신청 전, kt 고객은 kt 고객센터(100번)를 통해, kt M모바일로의 ‘정보제공동의’가 선행되야 결합 가능한 서비스입니다.</li>
          <li class="c-text-list__item u-co-gray">결합 신청 기한은 kt 상품 가입기준으로 익월 말 이내에 가능합니다.</li>
          <li class="c-text-list__item u-co-gray">kt의 결합 가능 대상 요금제는 이벤트 페이지 내의 상품을 참고 부탁 드리며, 할인 혜택 적용 여부는 kt 명세서에서 확인가능합니다.</li>
          <li class="c-text-list__item u-co-gray">kt M모바일의 결합 가능 대상 요금제는 데이터쉐어링, 데이터 전용 요금제를 제외한 모든 요금제이며, 단 할인 혜택은 없습니다.</li>
          <li class="c-text-list__item u-co-gray">서비스의 해지 및 이용 정지 시 할인 혜택이 중단 혹은 변경됩니다.</li>
          <li class="c-text-list__item u-co-gray">kt(유선,무선)상품의 이용 관련문의(가입 및 이용 정지)는 kt 고객센터(100번)를 통해 확인 바랍니다.</li>
          <li class="c-text-list__item u-co-gray">kt상품 결합(유무/무무결합)으로 혜택을 이미 받고 계신 분은 해당 결합 시 혜택은 미 제공 됩니다.</li>
          <li class="c-text-list__item u-co-gray">결합 중에 결합취소를 원하시면, 당사 고객센터(1899-5000)를 통해 결합 취소가 가능합니다.</li><!-- 결합 가능한 경우만 노출-->
          <li class="c-text-list__item u-co-gray">kt/kt M모바일 간 결합신청은 kt 고객센터를 통해 MVNO고객정보제공에 동의한 고객에 한해 제공됩니다.</li>
          <li class="c-text-list__item u-co-gray">동일명의의 kt상품이 있으나 결합 가능 상품이 조회 되지 않는 고객께서는 kt 고객센터(100번)을 통해 정보제공 동의 후 조회를 부탁드립니다.</li>
          <li class="c-text-list__item u-co-gray">이미 다른 상품과 결합 중인 상품의 경우 결합이 불가합니다.</li>
          <li class="c-text-list__item u-co-gray">마스킹 정보 조회는 kt.com에서 이용해주세요.</li>
        </ul>
      </div>
    </div>

  </div>

    </t:putAttribute>
</t:insertDefinition>
