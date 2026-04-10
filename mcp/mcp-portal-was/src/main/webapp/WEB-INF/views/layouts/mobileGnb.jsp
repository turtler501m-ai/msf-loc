<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<script type="text/javascript">
    var leaveChkLayer = function (url){
        $('#leaveUrl').val(url);
        $('#chkLayerPopBtn').trigger('click');
    }

    var leaveUrlGo = function (){
        KTM.LoadingSpinner.show();
        location.href=$('#leaveUrl').val();
    }
</script>
<input type="hidden" name="leaveUrl" id="leaveUrl"/>
<input type="hidden" name="mobileAppChk" id="mobileAppChk" value="${nmcp:getPlatFormCd()}"/>
<input type="hidden" name="appOcrChk" id="appOcrChk" value="${nmcp:getPhoneOsVerForOcr()}"/>
<c:set var="agent" value="${header['User-Agent']}"/>
<c:set var="req" value="${pageContext.request}"/>
<c:set var="GNBmenulist" value="${nmcp:getMobileMenuList('GNBMO001','Y','Y')}"/>
<c:set var="menulist" value="${nmcp:getMobileMenuList('MENUMO001','Y','Y')}"/>
<c:set var="serverNm" value="${nmcp:getPropertiesVal('SERVER_NAME')}" />
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />

<!-- ai 추천 메뉴 관련 값 -->
<c:set var="AIMenuCode" value="${nmcp:getCodeNmDto('AISetting', 'moMenu')}" />
<input type="hidden" id="AIGnbMenuCode" value="${AIMenuCode.expnsnStrVal1}">
<input type="hidden" id="AIMenuCode" value="${AIMenuCode.expnsnStrVal2}">

    <header class="ly-header">
      <h1 class="header-main">
        <a class="ly-header__logo" href="/m/main.do">
<c:set var="g001List" value="${nmcp:getBannerList('G001')}" />
<c:forEach items="${g001List}" var="item">
          <img class="c-logo" src="${item.mobileBannImgNm }" alt="KT M Mobile">
</c:forEach>
        </a>
      </h1>
      <ul class="ly-header__util">
        <li>
          <a class="ly-header__search" title="검색" href="javascript:void(0);" onclick="javascript:openPage('pullPopup','/m/search/searchView.do',''); $('#searchPopupText').focus();" >
            <span class="c-hidden">검색</span>
          </a>
        </li>
        <li>
            <c:choose>
                <c:when test="${appFormYn eq 'Y'}">
                    <a class="ly-header__login __loader__" title="마이페이지" href="javascript:void(0);" onclick="leaveChkLayer('/m/mypage/myinfoView.do');"><span class="c-hidden">마이페이지</span></a>
                </c:when>
                <c:otherwise>
                    <a class="ly-header__login __loader__" title="마이페이지" href="/m/mypage/myinfoView.do"><span class="c-hidden">마이페이지</span></a>
                </c:otherwise>
            </c:choose>
        </li>
        <li>
          <button class="ly-header__gnb" title="전체 메뉴 보기"></button>
          <span class="c-hidden">전체 메뉴보기</span>
        </li>
      </ul>
    </header><!-- [START]-->

<c:if test = "${isMain eq 'Y' }">
    <!-- 메인 GNB-->
    <nav class="main-gnb">
      <div class="nav-container-one">
        <ul class="nav-depth--one">
<c:forEach items="${GNBmenulist}" var="item" varStatus="vs">
    <c:if test = "${item.depthKey eq 1}">
        <c:if test = "${item.nextDepthCnt > 0}">
          <li class="nav-depth1"><a href="#gnb_${item.menuCode}">${item.menuNm}</a></li>
        </c:if>
        <c:if test = "${item.nextDepthCnt eq 0}">
            <c:if test = "${item.menuLinkCd eq 'MENUTP03'}">
          <li class="nav-depth1"><a href="javascript:openPage('pullPopup','${item.urlAdr}');">${item.menuNm}</a></li>
            </c:if>
            <c:if test = "${item.menuLinkCd ne 'MENUTP03'}">
                <c:if test = "${item.menuLinkCd eq 'MENUTP01'}">
          <li class="nav-depth1"><a href="javascript:void(0);" onclick="handleEmptyMenuClick('${item.menuCode}')">${item.menuNm}</a></li>
                </c:if>
                <c:if test = "${item.menuLinkCd ne 'MENUTP01'}">
          <li class="nav-depth1">
              <c:choose>
                  <c:when test="${appFormYn eq 'Y'}">
                      <a href="javascript:void(0);" onclick="leaveChkLayer('${item.urlAdr}');">${item.menuNm}</a>
                  </c:when>
                  <c:otherwise>
                      <a href="${item.urlAdr}" data-code="${fn:replace(item.menuCode, 'MOGNB', '')}" >${item.menuNm}</a>
                  </c:otherwise>
              </c:choose>
          </li>
                </c:if>
            </c:if>
        </c:if>
    </c:if>
</c:forEach>
        </ul>
      </div>
              <div class="nav-container-two">
<c:set var="deptchChg" value=""/>
<c:forEach items="${GNBmenulist}" var="item" varStatus="vs">
    <c:if test = "${item.depthKey eq 2}">
        <c:if test = "${deptchChg ne item.prntsKey}">
            <c:if test = "${deptchChg ne ''}">
                </ul>
            </c:if>
            <c:set var="deptchChg" value="${item.prntsKey}"/>
                <ul class="nav-depth--two" id="gnb_${item.prntsKey}">
        </c:if>
        <c:if test = "${item.nextDepthCnt > 0}">
                    <li class="nav-depth2"><a class="has-child" href="#d_${item.menuCode}">${item.menuNm}</a></li>
        </c:if>
        <c:if test = "${item.nextDepthCnt eq 0}">
            <c:if test = "${item.menuLinkCd eq 'MENUTP03'}">
                    <li class="nav-depth2"><a href="javascript:openPage('pullPopup','${item.urlAdr}');">${item.menuNm}</a></li>
            </c:if>
            <c:if test = "${item.menuLinkCd ne 'MENUTP03'}">
                <c:if test = "${item.menuLinkCd eq 'MENUTP01'}">
                    <li class="nav-depth2"><a href="javascript:void(0);" onclick="handleEmptyMenuClick('${item.menuCode}')">${item.menuNm}</a></li>
                </c:if>
                <c:if test = "${item.menuLinkCd ne 'MENUTP01'}">
                    <c:choose>
                          <c:when test="${appFormYn eq 'Y'}">
                            <li class="nav-depth2"><a href="javascript:void(0);" onclick="leaveChkLayer('${item.urlAdr}');">${item.menuNm}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-depth2"><a href="${item.urlAdr}" data-code="${fn:replace(item.menuCode, 'MOGNB', '')}" >${item.menuNm}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:if>
        </c:if>
    </c:if>
</c:forEach>
                </ul>
            <div class="nav-container-three">
<c:forEach items="${GNBmenulist}" var="item" varStatus="vs">
    <c:if test = "${item.depthKey eq 3}">
        <c:if test = "${deptchChg ne item.prntsKey}">
            <c:if test = "${deptchChg ne ''}">
                </ul>
            </c:if>
            <c:set var="deptchChg" value="${item.prntsKey}"/>
                <ul class="nav-depth--three" id="d_${item.prntsKey}">
        </c:if>
        <c:if test = "${item.menuLinkCd eq 'MENUTP03'}">
                <li><a href="javascript:openPage('pullPopup','${item.urlAdr}');">${item.menuNm}</a></li>
        </c:if>
        <c:if test = "${item.menuLinkCd ne 'MENUTP03'}">
            <c:if test = "${item.menuLinkCd eq 'MENUTP01'}">
                <li><a href="javascript:void(0);" onclick="handleEmptyMenuClick('${item.menuCode}')">${item.menuNm}</a></li>
            </c:if>
            <c:if test = "${item.menuLinkCd ne 'MENUTP01'}">
                <c:choose>
                     <c:when test="${appFormYn eq 'Y'}">
                        <li><a href="javascript:void(0);" onclick="leaveChkLayer('${item.urlAdr}');">${item.menuNm}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${item.urlAdr}" data-code="${fn:replace(item.menuCode, 'MOGNB', '')}" >${item.menuNm}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:if>
    </c:if>
</c:forEach>
                </ul>
            </div>

    </nav><!-- end-->
    <!-- //메인 GNB-->
    <!-- [END]-->

</c:if>

    <!-- 전체메뉴( 공통영역 )-->
    <div class="nav-wrap">
      <div class="nav-wrap__header">
        <div class="column">
          <a class="nav-wrap__link nav-wrap__link--home" href="/m/main.do">
            <i class="c-icon c-icon--menu-home" aria-hidden="true"></i>
          </a>
        </div>
        <div class="column">
<c:choose>
    <c:when test="${not empty sessionScope.USER_SESSION}">
          <!--로그인 후 나오는 로그아웃 버튼-->
        <c:choose>
               <c:when test="${appFormYn eq 'Y'}">
                <a class="nav-wrap__link nav-wrap__link--logout" href="javascript:void(0);" onclick="leaveChkLayer('/m/logOutProcess.do');">
                    <i class="c-icon c-icon--menu-logout" aria-hidden="true"></i>
                </a>
            </c:when>
            <c:otherwise>
                <a class="nav-wrap__link nav-wrap__link--logout" href="/m/logOutProcess.do">
                    <i class="c-icon c-icon--menu-logout" aria-hidden="true"></i>
                </a>
            </c:otherwise>
        </c:choose>
    </c:when>
</c:choose>
<c:if test = "${platFormCd eq 'A'}">
          <a class="nav-wrap__link nav-wrap__link--setting" href="javascript:appSettingView();">
            <i class="c-icon c-icon--menu-setting" aria-hidden="true"></i>
          </a>
</c:if>
          <a class="nav-wrap__link nav-wrap__link--close">
            <i class="c-icon c-icon--menu-close" aria-hidden="true"></i>
          </a>
        </div>
      </div>
      <div class="nav-wrap__user">
<c:choose>
   <c:when test="${not empty sessionScope.USER_SESSION}">
        <!--로그인 후-->
        <c:choose>
               <c:when test="${appFormYn eq 'Y'}">
                <a class="c-link u-fw--bold u-mt--8 __loader__" href="javascript:void(0);" onclick="leaveChkLayer('/m/mypage/myinfoView.do');">
                  <span class="u-fw--regular u-mr--4">안녕하세요.</span>
                  <span>${nmcp:getMaskedName(USER_SESSION.name)}님</span>
                  <i class="c-icon c-icon--arrow-black" aria-hidden="true"></i>
                </a>
            </c:when>
            <c:otherwise>
                <a class="c-link u-fw--bold u-mt--8 __loader__" href="/m/mypage/myinfoView.do">
                  <span class="u-fw--regular u-mr--4">안녕하세요.</span>
                  <span>${nmcp:getMaskedName(USER_SESSION.name)}님
                  <i class="c-icon c-icon--arrow-black" aria-hidden="true"></i>
                </a>
            </c:otherwise>
        </c:choose>
   </c:when>
   <c:otherwise>
        <!-- 로그인 전-->
        <c:choose>
               <c:when test="${appFormYn eq 'Y'}">
                <a class="c-link u-fw--bold u-mt--8" href="javascript:void(0);" onclick="leaveChkLayer('/m/loginForm.do');">로그인 해주세요<i class="c-icon c-icon--arrow-black" aria-hidden="true"></i></a>
            </c:when>
            <c:otherwise>
                <a class="c-link u-fw--bold u-mt--8" href="/m/loginForm.do">로그인 해주세요<i class="c-icon c-icon--arrow-black" aria-hidden="true"></i></a>
            </c:otherwise>
        </c:choose>
   </c:otherwise>
</c:choose>
      </div>
      <div class="nav-wrap__icon">
<c:set var="t001List" value="${nmcp:getBannerList('T001')}" />
<c:forEach items="${t001List}" var="item">
        <div class="icon-box">
    <c:if test = "${item.linkTarget eq 'P'}">
          <img src="${item.mobileBannImgNm }" href="javascript:void(0);" onclick="insertBannAccess('${item.bannSeq}','${item.bannCtg}');openPage('pullPopup','${item.mobileLinkUrl}');" alt="${item.bannNm }">
    </c:if>
    <c:if test = "${item.linkTarget ne 'P' and item.linkTarget ne 'L'}">
        <c:choose>
            <c:when test="${appFormYn eq 'Y'}">
              <img src="${item.mobileBannImgNm }" href="javascript:void(0);" onclick="insertBannAccess('${item.bannSeq}','${item.bannCtg}');leaveChkLayer('${item.mobileLinkUrl}');" alt="${item.bannNm }">
            </c:when>
            <c:otherwise>
              <img src="${item.mobileBannImgNm }" href="javascript:void(0);" onclick="insertBannAccess('${item.bannSeq}','${item.bannCtg}');location.href='${item.mobileLinkUrl}';" alt="${item.bannNm }">
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:if test = "${item.linkTarget eq 'L'}">
        <c:choose>
            <c:when test="${not empty sessionScope.USER_SESSION}">
                <img src="${item.mobileBannImgNm }" href="javascript:void(0);" onclick="insertBannAccess('${item.bannSeq}','${item.bannCtg}');openPage('pullPopup','${item.mobileLinkUrl}');" alt="${item.bannNm }">
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${appFormYn eq 'Y'}">
                        <img src="${item.mobileBannImgNm }" href="javascript:void(0);" onclick="leaveChkLayer('/m/loginForm.do?uri=/fqc/fqcEventView.do');" alt="${item.bannNm }">
                    </c:when>
                    <c:otherwise>
                        <img src="${item.mobileBannImgNm }" href="javascript:void(0);" onclick="location.href='/m/loginForm.do?uri=/fqc/fqcEventView.do';" alt="${item.bannNm }">
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:if>

          <strong class="u-block">${item.bannNm }</strong>
        </div>
</c:forEach>


      </div>
      <hr class="c-hr c-hr--type3 u-mt--16 u-mb--0">

      <div class="nav-wrap__menu">
        <ui class="nav-wrap__menu-main">
<c:set var="deptch1Cnt" value=""/>
<c:forEach items="${menulist}" var="item" varStatus="vsdepth1">
    <c:if test = "${item.depthKey eq 1}">
        <c:set var="deptch1Cnt" value="${vsdepth1.count}"/>
          <li class="nav-wrap__menu-item depth1">
              <a <c:if test = "${vsdepth1.first}">class="is-active"</c:if> href="#menu_${item.menuCode}">${item.menuNm}</a>
          </li>
    </c:if>
</c:forEach>
        </ui>
                                            <ul class="nav-wrap__menu-sub c-accordion" data-acc-only-one="true">

<c:set var="deptchChg" value=""/>
<c:set var="deptchMenu3" value=""/>
<c:forEach items="${menulist}" var="item" varStatus="vsdepth2">
    <c:if test = "${item.depthKey eq 2}">
        <c:if test = "${deptchChg ne item.prntsKey}">
            <c:if test = "${deptchChg ne ''}">
                                                </ul>
                                                </li>
            </c:if>
            <c:set var="deptchChg" value="${item.prntsKey}"/>
                                              <li class="nav-wrap__menu-item depth2" id="menu_${item.prntsKey}" <c:if test = "${deptch1Cnt eq vsdepth2.index}">style="display: block"</c:if>>
                                                  <ul class="nav-wrap__menu-sub" >
        </c:if>
        <c:if test = "${item.nextDepthCnt > 0}">
            <c:set var="deptchMenu3" value="${item.menuCode}"/>
                                                  <li class="nav-wrap__menu-item--ty1">
                                                      <a href="javascript:void(0);" data-acc-header>${item.menuNm}</a>
                                                    <ul class="expand">
             <c:forEach items="${menulist}" var="depthitem3" varStatus="vsdepth3">
                <c:if test = "${deptchMenu3 eq depthitem3.prntsKey}">
                    <c:if test = "${depthitem3.menuLinkCd eq 'MENUTP03'}">
                                                      <li class="nav-wrap__menu-item--ty2"><a class="" href="javascript:openPage('pullPopup','${depthitem3.urlAdr}');">${depthitem3.menuNm}</a></li>
                    </c:if>
                    <c:if test = "${depthitem3.menuLinkCd ne 'MENUTP03'}">
                        <c:if test = "${depthitem3.menuLinkCd eq 'MENUTP01'}">
                                                      <li class="nav-wrap__menu-item--ty2"><a class="" href="javascript:void(0);" onclick="handleEmptyMenuClick('${depthitem3.menuCode}')">${depthitem3.menuNm}</a></li>
                        </c:if>
                        <c:if test = "${depthitem3.menuLinkCd ne 'MENUTP01'}">
                            <c:choose>
                                <c:when test="${appFormYn eq 'Y'}">
                                                      <li class="nav-wrap__menu-item--ty2"><a class="" href="javascript:void(0);" onclick="leaveChkLayer('${depthitem3.urlAdr}');">${depthitem3.menuNm}</a></li>
                                </c:when>
                                <c:otherwise>
                                                      <li class="nav-wrap__menu-item--ty2"><a class="" href="${depthitem3.urlAdr}" data-code="${fn:replace(depthitem3.menuCode, 'MOMENU', '')}" >${depthitem3.menuNm}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:if>
                </c:if>
            </c:forEach>
                                                    </ul>
                                                  </li>
        </c:if>
        <c:if test = "${item.nextDepthCnt eq 0}">
                                                  <li class="nav-wrap__menu-item--ty1">
            <c:if test = "${item.menuLinkCd eq 'MENUTP03'}">
                                                      <a class="" href="javascript:openPage('pullPopup','${item.urlAdr}');">${item.menuNm}</a>
            </c:if>
            <c:if test = "${item.menuLinkCd ne 'MENUTP03'}">
                <c:if test = "${item.menuLinkCd eq 'MENUTP01'}">
                                                      <a class="" href="javascript:void(0);" onclick="handleEmptyMenuClick('${item.menuCode}')">${item.menuNm}</a>
                </c:if>
                <c:if test = "${item.menuLinkCd ne 'MENUTP01'}">
                    <c:choose>
                        <c:when test="${appFormYn eq 'Y'}">
                                                      <a class="" href="javascript:void(0);" onclick="leaveChkLayer('${item.urlAdr}');">${item.menuNm}</a>
                        </c:when>
                        <c:otherwise>
                                                      <a class="" href="${item.urlAdr}" data-code="${fn:replace(item.menuCode, 'MOMENU', '')}" >${item.menuNm}</a>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:if>
                                                  </li>
        </c:if>
    </c:if>
 </c:forEach>
                                            </ul>
          </div>
<c:if test = "${platFormCd ne 'A'}">
    <c:if test='${fn:indexOf(agent, "Android") > -1 }'>
        <div class="nav-wrap__menu-download">
            <a class="c-lick" href="https://play.google.com/store/apps/details?id=kt.co.ktmmobile" target="_blank">
                <img src="/resources/images/mobile/common/i_App Icon_R.png" alt="앱 다운로드 페이지로 이동">앱다운로드
            </a>
        </div>
    </c:if>
    <c:if test='${fn:indexOf(agent, "iPhone") > -1 or fn:indexOf(agent, "iPad") > -1}'>
        <div class="nav-wrap__menu-download">
            <a class="c-lick" href="https://itunes.apple.com/us/app/kt-mmobail-gogaegsenteo/id1094611503?mt=8" target="_blank">
                <img src="/resources/images/mobile/common/i_App Icon_R.png" alt="앱 다운로드 페이지로 이동">앱다운로드
            </a>
        </div>
    </c:if>
</c:if>
      </div>
    <div class="nav-dim"></div><!-- [END]-->

<script type="text/javascript">
    //챗봇 메뉴를 누르면 새창열기
    setTimeout(function(){
        var chatbot = $(".main-gnb a, .nav-wrap a").filter(":contains('고객센터 챗봇')");
        $(chatbot).attr('title',"고객센터 챗봇 새창열림");
        $(chatbot).click(function(){
            var chatbotUrl = "https://chatbot.kt-aicc.com/client/20231226131140095/chat.html";
            window.open(chatbotUrl, "_blank")
            return false;
        });
    },100);

    function handleEmptyMenuClick(menuCode){

      const gnbMenuCode = $("#AIGnbMenuCode").val();
      const allMenuCode = $("#AIMenuCode").val();

      // AI 추천 메뉴 여부 확인 후 팝업 오픈
      if(menuCode === gnbMenuCode || menuCode === allMenuCode){
        aiRecommendPopOpen();
      }
    }
</script>
