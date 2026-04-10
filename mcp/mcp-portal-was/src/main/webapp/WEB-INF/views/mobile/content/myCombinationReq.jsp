<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
	<script src="/resources/js/mobile/content/myCombinationReq.js"></script>
  		<script type="text/javascript">
 			history.pushState(null, null,location.href);
				window.onpopstate = function (event){   	   				
				location.href = "/m/myCombinationInfo.do";
    		 }
 		</script>
	</t:putAttribute>

 <t:putAttribute name="contentAttr">
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
      	 	 <button class="c-button c-button--sm c-button--white" onclick="btnConRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">자세히 보기</button>
     		</c:if>
     	</c:forEach>
      </div>
      <hr class="c-hr c-hr--type2">
      <p class="c-text c-text--type2 u-co-gray">kt M모바일 상품을 이용 중인 고객이 동일 명의로 KT 유/무선 상품을 사용중일 경우 결합하여 할인 혜택을 제공하는 서비스입니다.</p>
      <div class="c-form u-mt--40">
        <div class="c-form__title flex-type u-mb--12">내 정보<a class="c-button c-button--sm u-co-mint c-expand" href="javascript:void(0);" onclick="btn_combiPop();">결합내역 조회<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
          </a>
        </div>
        <form name="frm" id="frm">
        <input type="hidden"  name="contractNum" id="contractNum" value="${contractNum}"/>
        <input type="hidden"  name="combiChkYn" id="combiChkYn" value="Y"/>
        <input type="hidden"  id="reqSvc" value=""/>
		<input type="hidden" id="targetTerms"/>

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

      <h3 class="c-heading c-heading--type2 u-mb--12">결합 가능 상품 선택</h3>
      <div class="c-check-wrap c-check-wrap--column u-mr--0">
      <c:choose>
      	<c:when test="${empty moscCombInfoResDTO and empty svcDivList}">
      		<div class="c-chk-wrap">
	          <input class="c-radio c-radio--button" id="radUsed1" type="radio" name="radUsed">
	          <label class="c-label u-ta-left" for="radUsed1">
	            <dl class="c-servlist">
	              <dt></dt>
	              <dd>
	                <span>결합 가능한 상품이 없습니다.</span>
	              </dd>
	            </dl>
	          </label>
	        </div>
      	</c:when>
      	<c:otherwise>
      	 <c:if test="${!empty svcDivList}">
	      	 <c:forEach items="${svcDivList}" var="svcDivListDto" varStatus="status">
	      	  <c:choose>
				<c:when test="${'Y' eq statYn}">
				<div class="c-chk-wrap">
		          <input class="c-radio c-radio--button" id="radUsed1" type="radio" name="radUsed">
		          <label class="c-label u-ta-left" for="radUsed1">
		            <dl class="c-servlist">
		              <dt></dt>
		              <dd>
		                <span>결합 가능한 상품이 없습니다.</span>
		              </dd>
		            </dl>
		          </label>
		        </div>
				</c:when>
				<c:otherwise>
					<div class="c-chk-wrap">
			          <input class="c-radio c-radio--button" id="radUsed${status.count}"  type="radio" 
			          name="radUsed"  <c:if test="${status.count eq 1 }">checked</c:if> no="${status.count}" value="M">
			          <label class="c-label u-ta-left" for="radUsed${status.count}">
			            <dl class="c-servlist">
			              <dt>${svcDivListDto.socNm}(${svcDivListDto.mskSvcNo})</dt>
			              <dd>
			                <span>kt M모바일</span>
			              </dd>
			            </dl>
						<input type="hidden" name="reqSvc" id="reqSvc${status.count}" value="${svcDivListDto.svcNo}"/>
		                <input type="hidden" name="svcNoTypeCdMb" id="svcNoTypeCdMb${status.count}" value="M"/>
				         <input type="hidden" name="combYnMb" id="combYnMb${status.count}" value="${combiChkYn}"/>
		                
			          </label>
			        </div>
				</c:otherwise>
			</c:choose>
	       </c:forEach>
	     </c:if>
	    <c:if test="${!empty moscSrchCombInfoList}">  
		<c:forEach items="${moscSrchCombInfoList}" var="rtnListDto">
			<c:if test="${'N' eq rtnListDto.combYn }">
		        <div class="c-chk-wrap">
		          <input class="c-radio c-radio--button" id="radktUsed${status.count}" type="radio" name="radUsed" no="${status.count}" value="K">
		          <label class="c-label u-ta-left" for="radktUsed${status.count}">
		            <dl class="c-servlist">
		              <dt>${rtnListDto.mskSvcNo}</dt>
		              <dd>
		                <span>${rtnListDto.svcDivCd}</span>
		              </dd>
		            </dl>
		             <c:if test="${'인터넷' eq rtnListDto.svcDivCd}">
		              	<input type="hidden" name="cmbStndSvcNoEv" id="cmbStndSvcNoEv${status.count}" value="IT"/>
		              </c:if>
		               <input type="hidden" name="svcNoTypeCdKt" id="svcNoTypeCdKt${status.count}" value="K"/>
		    		   	<input type="hidden" name="reqSvc" id="reqSvcKt${status.count}" value="${rtnListDto.svcNo}"/>		      		   
		    		   	<input type="hidden" name="combYn" id="combYnKt${status.count}" value="${rtnListDto.combYn}"/>
		          </label>
		        </div>
	        </c:if>
	       </c:forEach>
	      </c:if>
      	</c:otherwise>
      </c:choose>
     <c:if test="${!empty svcDivList and 'Y' ne statYn}">  
      <hr class="c-hr c-hr--type2">
      <c:set var="combiList" value="${nmcp:getCodeList(Constants.COMBI_CD)}" />
      <c:forEach var="item" items="${combiList}" varStatus="status">
	      <div class="c-agree__item">
	        <input class="c-checkbox is-disabled" id="chkAgree" type="checkbox" name="chkAgree" onclick="chkEvent()" >
	        <label class="c-label" for="chkAgree">${item.dtlCdNm}<span class="u-co-gray">(${item.expnsnStrVal1})</span></label>
	        <button class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=${Constants.COMBI_CD}&cdGroupId2=${item.dtlCd}');">
	          <span class="c-hidden">상세보기</span>
	          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	        </button>
	      </div>
	   </c:forEach>
      <hr class="c-hr c-hr--type2 u-mt--24"><!-- 버튼 활성화 시 .is-disabled 클래스 삭제-->
      <div class="c-button-wrap">
        <button class="c-button c-button--full c-button--primary is-disabled" id="btnReg" onclick="btn_Combi();" >결합 신청</button>
      </div>
      </c:if>
      <hr class="c-hr c-hr--type2 u-mt--40">
      <b class="c-text c-text--type3">
        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
      </b>
      <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
        <li class="c-text-list__item u-co-gray">해당 서비스는 KT 와 kt M모바일의 결합 시 할인 혜택을 제공하는 서비스 입니다.</li>
        <li class="c-text-list__item u-co-gray">해당 서비스는 동일명의 KT 유선(신규), 무선(신규/우수기변/재약정) 고객과 kt M모바일의 직영온라인 기존/신규 가입고객만 결합이 가능합니다.</li>
        <li class="c-text-list__item u-co-gray">KT 상품 당 kt M모바일의 가입 가능 회선은 1회선입니다.</li>
        <li class="c-text-list__item u-co-gray">결합 신청 전, KT 고객은 KT 고객센터(100번)를 통해, kt M모바일로의 ‘정보제공동의’가 선행되야 결합 가능한 서비스입니다.</li>
        <li class="c-text-list__item u-co-gray">결합 신청 기한은 KT 상품 가입기준으로 익월 말 이내에 가능합니다.</li>
        <li class="c-text-list__item u-co-gray">KT의 결합 가능 대상 요금제는 이벤트 페이지 내의 상품을 참고 부탁 드리며, 할인 혜택 적용 여부는 KT 명세서에서 확인가능합니다.</li>
        <li class="c-text-list__item u-co-gray">kt M모바일의 결합 가능 대상 요금제는 데이터쉐어링, 데이터 전용 요금제를 제외한 모든 요금제이며, 단 할인 혜택은 없습니다.</li>
        <li class="c-text-list__item u-co-gray">서비스의 해지 및 이용 정지 시 할인 혜택이 중단 혹은 변경됩니다.</li>
        <li class="c-text-list__item u-co-gray">KT(유선,무선)상품의 이용 관련문의(가입 및 이용 정지)는 KT 고객센터(100번)를 통해 확인 바랍니다.</li>
        <li class="c-text-list__item u-co-gray">KT상품 결합(유무/무무결합)으로 혜택을 이미 받고 계신분은 해당 결합 시 혜택은 미 제공 됩니다.</li>
        <li class="c-text-list__item u-co-gray">결합 중에 결합취소를 원하시면, 당사 고객센터(1899-5000)를 통해 결합 취소가 가능합니다.</li>
        <li class="c-text-list__item u-co-gray">kt와 kt M모바일 간 결합신청은 kt 고객센터를 통해 MVNO고객정보제공에 동의한 고객에 한해 제공됩니다.</li>
        <li class="c-text-list__item u-co-gray">동일명의의 kt상품이 있으나 결합 가능 상품이 조회 되지 않는 고객께서는 kt 고객센터(100번)을 통해 정보제공동의 후 조회를 부탁드립니다.</li>
        <li class="c-text-list__item u-co-gray">이미 다른 상품과 결합 중인 상품의 경우 결합이 불가합니다.</li>
        <li class="c-text-list__item u-co-gray">마스킹 정보 조회는 kt.com에서 이용해주세요.</li>
      </ul>
    </div>
  </div>
  </div>
</t:putAttribute>
</t:insertDefinition>