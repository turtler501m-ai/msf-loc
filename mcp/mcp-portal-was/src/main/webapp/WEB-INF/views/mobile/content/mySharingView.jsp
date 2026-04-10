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
    <script src="/resources/js/mobile/content/mySharingView.js"></script>
    <script type="text/javascript">
            history.pushState(null, null,location.href);
                window.onpopstate = function (event){
                location.href = "/m/mySharingCntrInfo.do";
             }
        </script>

</t:putAttribute>

 <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">데이터쉐어링<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <c:set var="sharingInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />

      <div class="upper-banner upper-banner--bg c-expand">
        <div class="upper-banner__content">
            <strong class="upper-banner__title">
                데이터쉐어링이란?
            </strong>
            <p>대표회선의 데이터를 여러기기에서<br/> 함께 사용할 수 있는 서비스</p>
            <c:forEach var="item" items="${sharingInfo}" varStatus="status">
                <c:if test = "${Constants.SHAR_INFO_CD eq item.dtlCd}">
                    <button class="c-button c-button--sm upper-banner__anchor"  onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">
                        자세히 보기<i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                    </button>
                </c:if>
            </c:forEach>
        </div>
        <div class="upper-banner__image">
            <img src="/resources/images/mobile/rate/bg_sharing.png" alt="">
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
        <div class="c-form__title flex-type u-mb--12">내 정보</div>
        <div class="c-form__group" role="group" aria-labeledby="inpHP">
          <div class="c-form__select has-value">
              <select class="c-select" name="ncn" id="ncn"   title="서비스번호" >
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
            <label class="c-form__label">휴대폰 번호</label>
          </div>
          <div class="c-form__input has-value">
            <input class="c-input" id="inpHP" type="text" placeholder="사용중인 요금제"  value="${mcpUserCntrMngDto.rateNm}" readonly>
            <label class="c-form__label" for="inpHP">요금제</label>
          </div>
        </div>
      </div>
        <c:choose>
         <c:when test="${!empty moscDataSharingResDto.sharingList}">
            <h3 class="c-heading c-heading--type2 u-mb--12">결합 회선</h3>
              <div class="c-table">
                <table>
                  <caption>결합 회선</caption>
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
                  <tbody>
                    <c:forEach items="${moscDataSharingResDto.sharingList}" var="sharingListDto">
                    <tr>
                        <td class="u-ta-left">${sharingListDto.svcNo}</td>
                        <td class="u-ta-left">${fn:substring(sharingListDto.efctStDt,0,4)}.${fn:substring(sharingListDto.efctStDt,4,6)}.${fn:substring(sharingListDto.efctStDt,6,8)}</td>
                    </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
              <div class="c-text-box c-text-box--red u-mt--32">
                <p class="c-text c-text--type5 u-co-black">이미 결합되어 있는</p>
                <b class="c-text c-text--type2">회선이 있습니다.</b>
              </div><!-- //-->
          </c:when>
         <c:when test="${'Y' eq subStatusYn}">
          <div class="c-text-box c-text-box--red u-mt--32">
            <p class="c-text c-text--type5 u-co-black">사용중인 회선만</p>
            <b class="c-text c-text--type2">데이터쉐어링 결합 가능합니다.</b>
          </div>
         </c:when>
         <c:when test="${'Y' eq customerType}">
          <div class="c-text-box c-text-box--red u-mt--32">
            <p class="c-text c-text--type5 u-co-black">개인고객만</p>
            <b class="c-text c-text--type2">가입이 가능합니다.</b>
          </div>
         </c:when>
         <c:when test="${'Y' eq userType}">
           <div class="c-text-box c-text-box--red u-mt--32">
            <p class="c-text c-text--type5 u-co-black">외국인 고객은</p>
            <b class="c-text c-text--type2">가입이 불가능합니다.</b>
          </div>
         </c:when>
         <c:when test="${'Y' eq changeYn}">
         <div class="c-text-box c-text-box--red u-mt--32">
            <p class="c-text c-text--type5 u-co-black">납부방법을 신용카드/은행계좌</p>
            <b class="c-text c-text--type2">자동이체로 변경 후 <br>쉐어링 결합 신청이 가능합니다.</b>
          </div>
         </c:when>
         <c:when test="${'Y' ne socChkYn}">
             <div class="c-text-box c-text-box--red deco-combine u-mt--32">
                <p class="c-text c-text--type5 u-co-black">데이터쉐어링 결합 사용이</p>
                <b class="c-text c-text--type2">불가능한 요금제입니다.</b>
                <img src="/resources/images/mobile/content/img_combine.svg" alt="">
              </div><!-- //-->
         </c:when>
         <c:otherwise>
           <div class="c-text-box c-text-box--blue u-mt--32 sharingYn">
            <p class="c-text c-text--type5 u-co-black">데이터쉐어링 결합이</p>
            <b class="c-text c-text--type2">가능한 회선입니다.</b>
          </div>
          <div class="c-button-wrap sharingYn">
            <button class="c-button c-button--full c-button--primary" onclick="btn_sharingReg();">데이터쉐어링 결합하기</button>
          </div>
         </c:otherwise>
        </c:choose>
        <input type="hidden" name="menuType" id="menuType" value="${menuType}"/>

      <h5 class="sharing__subtit">데이터쉐어링 가입 퀵 가이드</h5>
      <div class="embed-youtube u-mt--16">
          <iframe src="https://www.youtube.com/embed/7-rB3pCL0Ow" title="알뜰폰 데이터쉐어링, 쉽고 간편하게 5분 완료!ㅣkt M모바일ㅣ서비스 퀵 가이드" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"></iframe>
      </div>

      <hr class="c-hr c-hr--type2 u-mt--40">
      <p class="c-text--type3 c-bullet">
        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
        알려드립니다
      </p>
      <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
        <li class="c-text-list__item u-co-gray">데이터쉐어링은 대표 회선 당 1개의 회선만 연결하여 사용 가능하며, 여러 회선을 개통하실 경우 사용이 불가하므로 고객센터를 통하여 개별 해지를 하셔야 합니다.</li>
      </ul>
    </div>
</t:putAttribute>
</t:insertDefinition>