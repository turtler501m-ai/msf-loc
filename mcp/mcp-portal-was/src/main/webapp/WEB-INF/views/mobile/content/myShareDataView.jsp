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
    <script src="/resources/js/mobile/content/myShareDataView.js"></script>
        <script type="text/javascript">
               history.pushState(null, null,location.href);
                  window.onpopstate = function (event){
                    location.href = "/m/myShareDataCntrInfo.do";
               }
           </script>
</t:putAttribute>

 <t:putAttribute name="contentAttr">

    <div id="content">
         <div class="ly-content" id="main-content">
          <div class="ly-page-sticky">
            <h2 class="ly-page__head">함께쓰기<button class="header-clone__gnb"></button>
            </h2>
          </div>

          <div class="upper-banner upper-banner--bg c-expand">
            <div class="upper-banner__content">
                <strong class="upper-banner__title">
                    함께쓰기란?
                </strong>
                <p>가족/지인과 결합하여 매월 2GB<br/> 데이터를 자동으로 주고 받는 서비스</p>
                <c:set var="dataInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />
                <c:forEach var="item" items="${dataInfo}" varStatus="status">
                    <c:if test = "${Constants.DATA_INFO_CD eq item.dtlCd}">
                        <button class="c-button c-button--sm upper-banner__anchor" onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">
                            자세히 보기<i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                        </button>
                    </c:if>
                </c:forEach>
            </div>
            <div class="upper-banner__image">
                <img src="/resources/images/mobile/rate/bg_sharedata.png" alt="">
            </div>
          </div>
          <c:if test="${empty maskingSession}">
			  <div class="masking-badge-wrap">
		          <div class="masking-badge">
				   	  <a class="masking-badge__button" href="/m/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
				    	<i class="masking-badge__icon" aria-hidden="true"></i>
					   	<p>가려진(*) 정보보기</p>
					  </a>
			      </div>
		      </div>
		  </c:if>
          <div class="c-form u-mt--40">
            <div class="c-form__title flex-type u-mb--12">내 정보<button class="c-button c-button--sm u-co-mint c-expand" onclick="btn_combiPop();">결합내역 조회<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
              </button>
            </div>
            <div class="c-form__group" role="group" aria-labeledby="inpHP">
              <div class="c-form__select has-value">
               <select class="c-select" name="ncn" id="ncn"   title="서비스번호" >
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
                <label class="c-form__label">휴대폰 번호</label>
              </div>
              <div class="c-form__input has-value">
                <input class="c-input" id="inpHP" type="text" placeholder="사용중인 요금제" value="${mcpUserCntrMngDto.rateNm}" readonly>
                <label class="c-form__label" for="inpHP">요금제</label>
              </div>
            </div>
          </div><!-- case1 : 결합 가능 할 때 -->
          <c:choose>
             <c:when test="${'S' eq subStatus}">
                 <div class="c-text-box c-text-box--red deco-combine u-mt--32" id="step01">
                    <p class="c-text c-text--type5 u-co-black">함께쓰기 결합</p>
                    <b class="c-text c-text--type2">현재 사용중인 고객만 가능합니다.</b>
                    <img src="/resources/images/mobile/content/img_combine.svg" alt="">
                  </div>
              </c:when>
          <c:otherwise>
              <c:choose>
              <c:when test="${resultCode eq '00'}">
                   <input type="hidden" name="retvGubunCd" id="retvGubunCd" value="${retvGubunCd}"/>
                 <div class="c-text-box c-text-box--blue u-mt--32 deco-combine-possible reg_combiYn">
                    <p class="c-text c-text--type5 u-co-black">함께쓰기 결합이</p>
                    <b class="c-text c-text--type2">가능한 회선입니다.</b>
                    <img src="/resources/images/mobile/content/img_combine_possible.svg" alt="">
                  </div>
                  <div class="c-form u-mt--40 reg_combiYn">
                    <div class="c-form__title flex-type u-mb--12">함께쓰기 회선 등록</div>
                    <div class="c-form__group" role="group" aria-labeledby="inpHP">
                      <div class="c-form__input">
                        <input class="c-input" id=custName type="text" placeholder="고객명" title="고객명 입력" maxlength="30">
                        <label class="c-form__label" for="custName">고객명</label>
                      </div>
                      <div class="c-form__input">
                        <input class="c-input numOnly" id="custSvcNo" type="tel" placeholder="'-'없이 입력"  maxlength="13" title="휴대폰 번호 입력">
                        <label class="c-form__label" for="custSvcNo">휴대폰</label>
                      </div>
                    </div>
                  </div>
                   <div class="c-button-wrap u-mt--48 reg_combiYn">
                    <button class="c-button c-button--full c-button--primary is-disabled" id="btnOk" onclick="btn_ok();" >결합 가능 여부 조회</button>
                  </div>
              </c:when>
             <c:otherwise>
                 <c:choose>
                <c:when test="${'01' eq resultCode}">
                   <div class="c-text-box c-text-box--red deco-combine u-mt--32" id="step01">
                    <p class="c-text c-text--type5 u-co-black">함께쓰기 결합</p>
                    <b class="c-text c-text--type2">대상 요금제가 아닙니다.</b>
                    <img src="/resources/images/mobile/content/img_combine.svg" alt="">
                  </div>
                </c:when>
                   <c:when test="${'02' eq resultCode}">
                        <div class="c-text-box c-text-box--red deco-combine u-mt--32" id="step02" >
                        <p class="c-text c-text--type5 u-co-black">함께쓰기 결합</p>
                        <b class="c-text c-text--type2">결합 가능한 회선 수를 초과하였습니다.</b>
                        <img src="/resources/images/mobile/content/img_combine.svg" alt="">
                      </div>
                   </c:when>
                 <c:when test="${'03' eq resultCode}">
                      <div class="c-text-box c-text-box--red deco-combine u-mt--32" id="step05">
                        <p class="c-text c-text--type5 u-co-black">함께쓰기 결합</p>
                        <b class="c-text c-text--type2">결합 대상 회선에 3개월 이내 결합이력이 존재하여 결합이 불가합니다.</b>
                        <img src="/resources/images/mobile/content/img_combine.svg" alt="">
                      </div>
                 </c:when>
                  <c:otherwise>
                      <div class="c-text-box c-text-box--red deco-combine u-mt--32" id="step05" >
                        <p class="c-text c-text--type5 u-co-black">함께쓰기 결합</p>
                        <b class="c-text c-text--type2">서비스 계약조건 처리 불가능 합니다.</b>
                        <img src="/resources/images/mobile/content/img_combine.svg" alt="">
                      </div>
                  </c:otherwise>
                  </c:choose>
             </c:otherwise>
             </c:choose>
         </c:otherwise>
          </c:choose>
          <input type="hidden" name="menuType" id="menuType" value="${menuType}"/>
          <hr class="c-hr c-hr--type2 u-mt--40">
          <b class="c-text c-text--type3">
            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
          </b><!-- //-->
          <strong class="c-heading c-heading--type4 u-mb--0 u-mt--16">서비스 개요</strong>
          <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item u-co-gray">함께쓰기 결합 시 매월 1일자에 결합 상대 회선(결합 상대 회선)으로부터 데이터 2GB 제공됩니다. (데이터 주기 회선은 기본 제공량에서 2GB 차감).</li>
            <li class="c-text-list__item u-co-gray">결합 당월에 한정하여 데이터 주기 회선의 데이터 차감 없이 프로모션 데이터 2GB 무료 제공됩니다.</li>
          </ul>
          <strong class="c-heading c-heading--type4 u-mb--0 u-mt--16">주의사항</strong>
          <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item u-co-gray">함께쓰기 결합은 전용 부가서비스 가입을 통해 이루어집니다. (LTE결합서비스_데이터받기 2GB/데이터주기 2GB)</li>
            <li class="c-text-list__item u-co-gray">함께쓰기 결합은 특정 요금제에서만 가입 가능합니다. (결합가능 요금제 리스트는 상단 자세히 보기 참조)</li>
            <li class="c-text-list__item u-co-gray">함께쓰기 결합은 kt M모바일을 사용중인 고객간 가능합니다. (동일명의 결합 가능)</li>
            <li class="c-text-list__item u-co-gray">함께쓰기 결합 해지는 고객센터 (114)를 통해 신청 가능합니다.</li>
            <li class="c-text-list__item u-co-gray">함께쓰기 결합 후 월 중 모회선의 요금제 변경, 정지, 해지 시 데이터주기 2GB의 초과요금이 발생할 수 있습니다.</li>
          </ul>
        </div>
      </div><!-- [START]-->
</t:putAttribute>
</t:insertDefinition>