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

       <script type="text/javascript">
        function btnRegDtl(param){
            openPage('termsInfoPop','/termsPop.do',param);
        }


    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
         <div class="ly-content" id="main-content">
          <div class="ly-page--title">
            <h2 class="c-page--title">유무선결합</h2>
          </div>
          <form name="frm" id="frm" method="post" action="/loginForm.do">
            <input type="hidden" name="uri" id="uri" value="/content/myCombinationView.do"/>
          </form>
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
                       <button class="c-button c-button--underline c-button--sm u-co-white" type="button" onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">자세히보기</button>
                   </c:if>
                   </c:forEach>
              </div>
              <img class="c-headline--deco" src="/resources/images/portal/content/img_combination.png" alt="" aria-hidden="true">
            </div>
          </div>
          <c:if test="${!nmcp:hasLoginUserSessionBean()}">
          <div class="c-row c-row--sm u-mt--64 u-ta-center">
            <p class="c-text c-text--fs24">로그인 후 사용하실 수 있습니다.</p>
            <div class="c-button-wrap u-mt--56">
              <a class="c-button c-button--full c-button--primary" href="javascript:void(0);" onclick="$('#frm').submit();">로그인</a>
            </div>
          </div>
          </c:if>
        </div>
       <!--  <footer class="ly-footer">Footer<a href="#n">링크</a>
          <div>
            <a href="#n">링크</a>
          </div>
          <div>
            <a href="#n">링크</a>
          </div>
        </footer> -->
      </div>

    </t:putAttribute>
</t:insertDefinition>

