<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">데이터쉐어링</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script src="/resources/js/portal/content/mySharingView.js"></script>
        <script type="text/javascript">
                history.pushState(null, null,location.href);
                window.onpopstate = function (event){
                location.href = "/mySharingCntrInfo.do";
             }
        </script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">데이터쉐어링</h2>
      </div>
      <div class="ly-page--deco u-bk--blue">
        <div class="ly-avail--wrap">
          <h3 class="c-headline">데이터쉐어링이란?</h3>
          <p class="c-headline--sub">데이터쉐어링은 대표회선의 데이터를 여러 기기에서 함께 사용할 수 있는
            <br />서비스로 대표 회선당 1개의 회선만 가입/결합하여 사용 가능합니다.
          </p>
          <div class="c-button-wrap c-button-wrap--left">
          <c:set var="sharDataInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />
            <c:forEach var="item" items="${sharDataInfo}" varStatus="status">
                <c:if test = "${Constants.SHAR_INFO_CD eq item.dtlCd}">
                <button class="c-button c-button--underline c-button--sm u-co-white" type="button"  onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">자세히보기</button>
                </c:if>
            </c:forEach>
          </div>
          <img class="c-headline--deco" src="/resources/images/portal/content/img_sharing.png" alt="" aria-hidden="true">
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
            <h3 class="c-group--head" id="formGroupA">내 정보</h3>
            <div class="c-box c-box--type1 c-box--round">
              <div class="c-form-row c-col2">
                <div class="c-form-field">
                  <div class="c-form__select u-mt--0">
                    <select class="c-select" name="ncn" id="ncn">
                     <c:choose>
                        <c:when test="${'02' eq  searchVO.userDivisionYn}">
                            <option value="${searchVO.ncn}">${searchVO.ctn}</option>
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
                  <div class="c-form__input u-mt--0 has-value">
                    <input class="c-input" id="inpHP" type="text" readonly value="${mcpUserCntrMngDto.rateNm}">
                    <label class="c-form__label" for="inpHP">요금제</label>
                  </div>
                </div>
                <button class="c-button c-button-round--md c-button--primary u-width--105 u-ml--24"  onclick="btn_search();">조회</button>
              </div>
            </div>
          </div>
        </div><!-- case1-1 : 결합불가인 경우-->
       <c:choose>
         <c:when test="${!empty moscDataSharingResDto.sharingList}">
           <h3 class="c-heading c-heading--fs16 u-mt--48 u-mb--16 sharingYn">결합 회선</h3>
            <div class="c-table sharingYn">
              <table>
                <caption>휴대폰 번호, 요금제를 포함한 결합 회선 정보</caption>
                <colgroup>
                  <col style="width: 50%">
                  <col style="width: 50%">
                </colgroup>
                <thead>
                <tr>
                    <th scope="row">휴대폰 번호</th>
                    <th scope="row">결합일</th>
                </tr>
                </thead>
                <tbody class="c-table__left sharingView">
                  <c:forEach items="${moscDataSharingResDto.sharingList}" var="sharingListDto">
                       <tr>
                        <td>${sharingListDto.svcNo}</td>
                        <td>${fn:substring(sharingListDto.efctStDt,0,4)}.${fn:substring(sharingListDto.efctStDt,4,6)}.${fn:substring(sharingListDto.efctStDt,6,8)}</td>
                      </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
            <div class="c-text-box c-text-box--blue c-text-box--lg u-mt--64 sharingYn">
              <p class="c-text-box__text">이미 결합되어 있는
                <br />
                <b class="u-co-blue">회선이 있습니다.</b>
              </p>
              <img class="c-text-box__image" src="/resources/images/portal/content/img_possible.png" alt="" aria-hidden="true">
            </div><!-- //-->
         </c:when>
         <c:when test="${'Y' eq changeYn}">
         <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64 sharingStatusYn">
          <p class="c-text-box__text">납부방법을 신용카드/은행계좌
            <br />
            <b class="u-co-red"> 자동이체로 변경 후 <br />쉐어링 결합 신청이 가능합니다.</b>
          </p>
          <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
         </div><!-- //-->
         </c:when>
         <c:when test="${'Y' eq subStatusYn}">
            <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64 sharingStatusYn">
              <p class="c-text-box__text">사용중인 회선만
                <br />
                <b class="u-co-red">데이터쉐어링 결합 가능합니다.</b>
              </p>
              <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
            </div><!-- //-->
         </c:when>
         <c:when test="${'Y' eq customerType}">
            <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64 sharingStatusYn">
              <p class="c-text-box__text">개인고객만
                <br />
                <b class="u-co-red">가입이 가능합니다.</b>
              </p>
              <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
            </div><!-- //-->
         </c:when>
          <c:when test="${'Y' ne socChkYn}">
           <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64">
              <p class="c-text-box__text">데이터쉐어링 결합 사용이
                <br />
                <b class="u-co-red">불가능한 요금제입니다.</b>
              </p>
              <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
            </div><!-- //-->
         </c:when>
          <c:when test="${'Y' eq userType}">
            <div class="c-text-box c-text-box--red c-text-box--lg u-mt--64 sharingStatusYn">
              <p class="c-text-box__text">외국인 고객은
                <br />
                <b class="u-co-red">가입이 불가능합니다.</b>
              </p>
              <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
            </div><!-- //-->
         </c:when>
        <c:otherwise>
            <div class="c-text-box c-text-box--blue c-text-box--lg u-mt--64 sharingChkYn">
              <p class="c-text-box__text">데이터쉐어링
                <br />
                <b class="u-co-blue">결합이 가능한 회선입니다.</b>
              </p>
              <img class="c-text-box__image" src="/resources/images/portal/content/img_possible.png" alt="" aria-hidden="true">
            </div>
            <div class="c-button-wrap u-mt--56 sharingChkYn">
              <a class="c-button c-button--lg c-button--primary u-width--460" href="javascript:void(0);" onclick="btn_sharingReg();">데이터쉐어링 결합하기</a>
            </div>
        </c:otherwise>
        </c:choose>
        <input type="hidden" name="menuType" id="menuType" value="${menuType}"/>

        <h5 class="sharing__subtit">데이터쉐어링 가입 퀵 가이드</h5>
        <iframe class="u-mt--24" src="https://www.youtube.com/embed/7-rB3pCL0Ow" width="940" height="529" title="알뜰폰 데이터쉐어링, 쉽고 간편하게 5분 완료!ㅣkt M모바일ㅣ서비스 퀵 가이드" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen=""></iframe>

        <!-- [2022-01-14] 페이지 하단 유의사항 마크업 수정-->
        <h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </h3>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <!-- [$2022-01-14] 페이지 하단 유의사항 마크업 수정-->
          <li class="c-text-list__item u-co-gray">데이터쉐어링은 대표 회선 당 1개의 회선만 연결하여 사용 가능하며, 여러 회선을 개통하실 경우 사용이 불가하므로 고객센터를 통하여 개별 해지를 하셔야 합니다.</li>
        </ul>
      </div>
    </div>

    </t:putAttribute>
</t:insertDefinition>
