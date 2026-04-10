<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">


	<script src="/resources/js/mobile/content/myCombinationView.js"></script>
		<script type="text/javascript">
 			history.pushState(null, null,location.href);
				window.onpopstate = function (event){   	   				
				location.href = "/m/myCombinationInfo.do";
    		 }
 		</script>
	</t:putAttribute>

 <t:putAttribute name="contentAttr">

	<div id="content">
	<div class="ly-wrap">
    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">유무선결합<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="c-title-wrap c-title-wrap--flex">
        <h3 class="c-heading c-heading--type1"> KT-알뜰폰
          <br>유무선결합이란?
        </h3>
        <c:set var="combiInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />
        <c:forEach var="item" items="${combiInfo}" varStatus="status">
        	<c:if test = "${Constants.COMBI_INFO_CD eq item.dtlCd}">
        		 <button class="c-button c-button--sm c-button--white"   onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">자세히 보기</button>
        	</c:if>
     	</c:forEach>
     </div>
      <hr class="c-hr c-hr--type2">
      <p class="c-text c-text--type2 u-co-gray">kt M모바일 상품을 이용 중인 고객이 동일 명의로 KT 유/무선 상품을 사용중일 경우 결합하여 할인 혜택을 제공하는 서비스입니다.</p>
      <div class="c-form u-mt--40">
       <form name="frm" id="frm">
        <div class="c-form__title flex-type u-mb--12">내 정보<a class="c-button c-button--sm u-co-mint c-expand" href="javascript:void(0);" onclick="btn_combiPop();">결합내역 조회<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
          </a>
        </div>
        <div class="c-form__group" role="group" aria-labeledby="inpHP">
          <div class="c-form__select has-value">
             <select class="c-select" name="ncn" id="ncn"   title="서비스번호" >
				<c:forEach items="${cntrList}" var="item">
					<option value="${item.svcCntrNo }" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.cntrMobileNo }</option>
				</c:forEach>
			</select>
            <label class="c-form__label">휴대폰 번호</label>
          </div>
          <div class="c-form__input has-value">
            <input class="c-input" id="inpHP" type="text" placeholder="사용중인 요금제" value="${mcpUserCntrMngDto.rateNm}" readonly>
            <label class="c-form__label" for="inpHP">요금제</label>
          </div>
        </div>
        </form>
      </div>
      <div class="c-button-wrap u-mt--48 combichkReg" style="display: none;" >
        <button class="c-button c-button--full c-button--primary" onclick="btn_combiYn();">결합 가능 상품 조회</button>
      </div>
      <input type="hidden" name="combiYn" id="combiYn" value=""/>
       <div class="c-text-box c-text-box--red deco-combine u-mt--32" id="combiChkYn" style="display: none;">
       </div>
          <!--[2022-01-19] 배너 꾸밈 이미지/클래스 추가(.deco-combine)-->
      <div class="c-text-box c-text-box--red deco-combine u-mt--32 combichkYn1" style="display: none;">
        <p class="c-text c-text--type5 u-co-black">현재 회선을 사용중인</p>
        <b class="c-text c-text--type2">고객만 결합이 가능합니다.</b>
        <img src="/resources/images/mobile/content/img_combine.svg" alt="">
      </div>
       <div class="c-text-box c-text-box--red deco-combine u-mt--32 combichkYn2" style="display: none;">
        <p class="c-text c-text--type5 u-co-black">개인고객만</p>
        <b class="c-text c-text--type2">가입이 가능합니다.</b>
        <img src="/resources/images/mobile/content/img_combine.svg" alt="">
      </div>
      <div class="c-text-box c-text-box--red deco-combine u-mt--32 combichkYn3" style="display: none;">
        <p class="c-text c-text--type5 u-co-black">해당 요금제는</p>
        <b class="c-text c-text--type2">결합이 불가합니다.</b>
        <img src="/resources/images/mobile/content/img_combine.svg" alt="">
      </div>
      <hr class="c-hr c-hr--type2 u-mt--40">
      <b class="c-text c-text--type3">
        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
      </b>
      <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
        <li class="c-text-list__item u-co-gray">해당 서비스는 kt 와 kt M모바일의 결합 시 할인 혜택을 제공하는 서비스 입니다.</li>
        <li class="c-text-list__item u-co-gray">해당 서비스는 동일명의 kt 유선(신규), 무선(신규/우수기변/재약정) 고객과 kt M모바일의 직영온라인 기존/신규 가입고객만 결합이 가능합니다.</li>
        <li class="c-text-list__item u-co-gray">kt 상품 당 kt M모바일의 가입 가능 회선은 1회선입니다.</li>
        <li class="c-text-list__item u-co-gray">결합 신청 전, kt 고객은 kt 고객센터(100번)를 통해, kt M모바일로의 ‘정보제공동의’가 선행되야 결합 가능한 서비스입니다.</li>
        <li class="c-text-list__item u-co-gray">결합 신청 기한은 kt 상품 가입기준으로 익월 말 이내에 가능합니다.</li>
        <li class="c-text-list__item u-co-gray">kt의 결합 가능 대상 요금제는 이벤트 페이지 내의 상품을 참고 부탁 드리며, 할인 혜택 적용 여부는 kt 명세서에서 확인 가능합니다.</li>
        <li class="c-text-list__item u-co-gray">kt M모바일의 결합 가능 대상 요금제는 데이터쉐어링, 데이터 전용 요금제를 제외한 모든 요금제이며, 단 할인 혜택은 없습니다.</li>
        <li class="c-text-list__item u-co-gray">서비스의 해지 및 이용 정지 시 할인 혜택이 중단 혹은 변경됩니다.</li>
        <li class="c-text-list__item u-co-gray">kt(유선,무선)상품의 이용 관련문의(가입 및 이용 정지)는 kt 고객센터(100번)를 통해 확인 바랍니다.</li>
        <li class="c-text-list__item u-co-gray">kt상품 결합(유무/무무결합)으로 혜택을 이미 받고 계신 분은 해당 결합 시 혜택은 미 제공 됩니다.</li>
        <li class="c-text-list__item u-co-gray">결합 중에 결합취소를 원하시면, 당사 고객센터(1899-5000)를 통해 결합 취소가 가능합니다.</li>
      </ul>
    </div>
  </div>
	</div>
</t:putAttribute>
</t:insertDefinition>