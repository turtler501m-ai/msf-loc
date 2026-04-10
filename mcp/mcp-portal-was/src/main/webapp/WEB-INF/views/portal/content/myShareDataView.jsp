<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">함께쓰기</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script src="/resources/js/portal/content/myShareDataView.js"></script>

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
      <div class="ly-page--deco u-bk--blue">
        <div class="ly-avail--wrap">
          <h3 class="c-headline">함께쓰기란?</h3>
         <!--[2022-03-31] 문구 수정 요청 반영-->
          <p class="c-headline--sub">kt M모바일을 이용중인 가족/지인과 결합하여 매월 2GB 데이터를
            <br />자동으로 주고 받을 수 있는 서비스 입니다.
          </p>
          <div class="c-button-wrap c-button-wrap--left">
          <c:set var="dataInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />
            <c:forEach var="item" items="${dataInfo}" varStatus="status">
                <c:if test = "${Constants.DATA_INFO_CD eq item.dtlCd}">
                    <button class="c-button c-button--underline c-button--sm u-co-white" type="button"  onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">자세히보기</button>
                 </c:if>
              </c:forEach>
          </div>
          <img class="c-headline--deco" src="/resources/images/portal/content/img_together.png" alt="" aria-hidden="true">
        </div>
      </div>
      <div class="c-row c-row--lg">
      	<c:if test="${empty maskingSession}">
      	  	<div class="masking-badge-wrap">
		        <div class="masking-badge">
		        	<a class="masking-badge__button" href="/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
		        		<i class="masking-badge__icon" aria-hidden="true"></i>
		 				<p>가려진(*)<br />정보보기</p>
		       		</a>
		        </div>
	        </div>
        </c:if>
        <div class="c-form-wrap u-mt--48">
          <div class="c-form-group" role="group" aria-labelledby="formGroupA">
            <div class="c-group--head">
              <h3 id="formGroupA">내 정보</h3>
              <button class="c-button c-button--underline" type="button"  onclick="btn_combiPop();">결합내역 조회</button>
            </div>
            <div class="c-box c-box--type1 c-box--round">
            <div class="c-form-row c-col2">
              <div class="c-form-field">
                <div class="c-form__select u-mt--0">
                  <select class="c-select" name="ncn" id="ncn" >
                       <c:choose>
                       <c:when test="${'02' eq  searchVO.userDivisionYn}">
                           <option value="${searchVO.ncn}" >${searchVO.ctn}</option>
                       </c:when>
                       <c:otherwise>
                            <c:forEach items="${cntrList}" var="item">
                                  <c:choose>
                                      <c:when test="${maskingSession eq 'Y'}">
                                          <option value="${item.svcCntrNo }" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.formatUnSvcNo }</option>
                                      </c:when>
                                      <c:otherwise>
                                          <option value="${item.svcCntrNo }" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.cntrMobileNo }</option>
                                      </c:otherwise>
                                 </c:choose>
                            </c:forEach>
                       </c:otherwise>
                   </c:choose>
                  </select>
                  <label class="c-form__label" for="ncn">휴대폰 번호</label>
                </div>
              </div>
              <div class="c-form-field">
                <!-- [2022-01-17] has-value 클래스추가-->
                <div class="c-form__input u-mt--0 has-value">
                  <!-- [2022-01-17] disabled &gt; readonly 속성 수정-->
                  <input class="c-input" id="inpHP" type="text" readonly value="${mcpUserCntrMngDto.rateNm}">
                  <label class="c-form__label" for="inpHP">요금제</label>
                </div>
              </div>
              <button class="c-button c-button-round--md c-button--primary u-width--105 u-ml--24"  onclick="btn_search();">조회</button>
            </div>
          </div>
        </div><!-- case1-1 : 결합불가인 경우-->
        <c:choose>
           <c:when test="${'S' eq subStatus}">
        <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64" id="step06">
          <p class="c-text-box__text">
            <b class="u-co-red">함께쓰기 결합은 현재 사용중인 고객만 가능합니다.</b>
          </p>
          <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
        </div><!-- //-->
        </c:when>
        <c:otherwise>
         <c:choose>
          <c:when test="${resultCode eq '00'}">
              <input type="hidden" name="retvGubunCd" id="retvGubunCd" value="${retvGubunCd}"/>

             <!-- case1-2: 결합 가능한 경우-->
            <div class="c-text-box c-text-box--blue c-text-box--lg u-mt--64 reg_combiYn">
              <p class="c-text-box__text">함께쓰기
                <br />
                <b class="u-co-blue">결합이 가능한 회선입니다.</b>
              </p>
              <img class="c-text-box__image" src="/resources/images/portal/content/img_possible.png" alt="" aria-hidden="true">
            </div>
            <div class="c-form-wrap u-mt--48 reg_combiYn">
              <div class="c-form-group" role="group" aria-labelledby="formGroupB">
                <!-- [2022-01-14] u-mb--0 클래스삭제-->
                <h3 class="c-group--head" id="formGroupB">함께쓰기 회선 등록</h3>
                <div class="c-form-row c-col2">
                  <div class="c-form-field">
                    <div class="c-form__input">
                      <input class="c-input" id="custName" type="text" placeholder="고객명 입력" maxlength="30">
                      <label class="c-form__label" for="custName">고객명</label>
                    </div>
                  </div>
                  <div class="c-form-field">
                    <!-- [2022-01-14] u-mt--0 클래스추가-->
                    <div class="c-form__input-group u-mt--0">
                      <div class="c-form__input c-form__input-division">
                        <input class="c-input--div3 custSvcNo numOnly" id="custSvcNo1" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리">
                        <span>-</span>
                        <input class="c-input--div3 custSvcNo numOnly" id="custSvcNo2" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 중간자리">
                        <span>-</span>
                        <input class="c-input--div3 custSvcNo numOnly" id="custSvcNo3" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 뒷자리">
                        <label class="c-form__label" for="custSvcNo3">휴대폰 번호</label>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
           <div class="c-button-wrap u-mt--40 reg_combiYn ">
              <a class="c-button c-button-round--md c-button--mint u-width--460 is-disabled" href="javascript:void(0);" id="btnOk" onclick="btn_ok();" disabled="disabled">결합 가능 여부 조회</a>
            </div><!-- //-->
          </c:when>
          <c:otherwise>
          <c:choose>
            <c:when test="${'01' eq resultCode}">
             <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64" id="step01">
              <p class="c-text-box__text">
                   <b class="u-co-red">함께쓰기 결합 대상 요금제가 아닙니다.</b><!-- 하단 문구 필요할떄 아래와 같이 사용하세요.-->
              </p>
              <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
            </div><!-- //-->
            </c:when>
            <c:when test="${'02' eq resultCode}">
            <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64" id="step02">
              <p class="c-text-box__text">
                <b class="u-co-red">함께쓰기 결합 가능한 회선 수를 초과하였습니다.</b>
              </p>
              <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
            </div><!-- //-->
            </c:when>
            <c:when test="${'03' eq resultCode}">
            <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64" id="step05">
              <p class="c-text-box__text">
                <b class="u-co-red">함께쓰기 결합 대상 회선에 3개월 이내 결합이력이 존재하여 결합이 불가합니다.</b>
              </p>
              <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
            </div><!-- //-->
            </c:when>

            <c:otherwise>
                <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64" id="step05">
                  <p class="c-text-box__text">
                    <b class="u-co-red">함께쓰기 결합 서비스 계약조건 처리 불가능 합니다.</b>
                  </p>
                  <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
                </div><!-- //-->
            </c:otherwise>
            </c:choose>
          </c:otherwise>
         </c:choose>
         </c:otherwise>
     </c:choose>
     <input type="hidden" name="menuType" id="menuType" value="${menuType}"/>

        <!-- [2022-01-14] 페이지 하단 유의사항 마크업 수정-->
        <h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </h3>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <!-- [$2022-01-14] 페이지 하단 유의사항 마크업 수정-->
          <li class="c-text-list__item">서비스 개요<ul class="c-text-list c-bullet c-bullet--dot">
              <li class="c-text-list__item u-co-gray">함께쓰기 결합 시 매월 1일자에 결합 상대 회선(결합 상대 회선)으로부터 데이터 2GB 제공됩니다. (데이터 주기 회선은 기본 제공량에서 2GB 차감)</li>
              <li class="c-text-list__item u-co-gray">결합 당월에 한정하여 데이터 주기 회선의 데이터 차감 없이 프로모션 데이터 2GB 무료 제공됩니다.</li>
            </ul>
          </li>
          <li class="c-text-list__item">주의사항<ul class="c-text-list c-bullet c-bullet--dot">
              <li class="c-text-list__item u-co-gray">함께쓰기 결합은 전용 부가서비스 가입을 통해 이루어집니다.(LTE결합서비스_데이터받기2GB/데이터주기2GB)</li>
              <li class="c-text-list__item u-co-gray">함께쓰기 결합은 특정 요금제에서만 가입 가능합니다.(결합가능 요금제 리스트는 상단 자세히 보기 참조)</li>
              <li class="c-text-list__item u-co-gray">함께쓰기 결합은 kt M모바일을 사용중인 고객간 가능합니다.(동일명의 결합 가능)</li>
              <li class="c-text-list__item u-co-gray">힘께쓰기 결합 해지는 고객센터 (114)를 통해 신청 가능합니다.</li><!-- 결합 가능한 회선일 경우 노출-->
              <li class="c-text-list__item u-co-gray">함께쓰기 결합 후 월 중 모회선의 요금제 변경, 정지, 해지 시 데이터주기 2GB의 초과요금이 발생할 수 있습니다.</li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
      </div>
    </t:putAttribute>
</t:insertDefinition>

