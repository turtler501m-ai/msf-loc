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
<c:set var="GNBlistdepth1" value="${nmcp:getMenuDepthList('GNBPC001', 1)}"/>
<c:set var="GNBlistdepth2" value="${nmcp:getMenuDepthList('GNBPC001', 2)}"/>
<c:set var="GNBlistdepth3" value="${nmcp:getMenuDepthList('GNBPC001', 3)}"/>
<c:set var="menulistdepth1" value="${nmcp:getMenuDepthList('MENUPC001', 1)}"/>
<c:set var="menulistdepth2" value="${nmcp:getMenuDepthList('MENUPC001', 2)}"/>
<c:set var="menulistdepth3" value="${nmcp:getMenuDepthList('MENUPC001', 3)}"/>

<c:set var="isFqcView" value="${nmcp:getCodeNmDto('Constant','isFqcView')}" />
<c:set var="fqcEventSeq" value="${nmcp:getCodeNmDto('Constant','fqcEventSeq')}" />
<c:set var="isFreeUsimView" value="${nmcp:getCodeNm('Constant','isFreeUsimView')}" />

<!-- ai 추천 메뉴 관련 값 -->
<c:set var="AIMenuCode" value="${nmcp:getCodeNmDto('AISetting', 'pcMenu')}" />
<input type="hidden" id="AIGnbMenuCode" value="${AIMenuCode.expnsnStrVal1}">
<input type="hidden" id="AIMenuCode" value="${AIMenuCode.expnsnStrVal2}">

    <!--레이아웃 헤더-->
    <header class="ly-header">
      <div class="ly-header__wrap">
        <a class="ly-header__logo" href="/main.do">
<c:set var="g001List" value="${nmcp:getBannerList('G001')}" />
<c:forEach items="${g001List}" var="item">
          <img src="${item.bannImg }" alt="KT M Mobile 로고">
</c:forEach>
        </a>
        <!--헤더 메뉴-->
        <div class="ly-header__nav">
          <ul class="nav nav--depth1">

<c:set var="menuCode1" value=""/>
<c:set var="nextDepthCnt1" value="0"/>
<c:set var="menuCode2" value=""/>
<c:set var="nextDepthCnt2" value="0"/>
<c:set var="menuCode3" value=""/>
<c:set var="nextDepthCnt3" value="0"/>
<c:forEach items="${GNBlistdepth1}" var="item1">
    <c:set var="menuCode1" value="${item1.menuCode}"/>
    <c:set var="nextDepthCnt1" value="${item1.nextDepthCnt}"/>
    <c:if test = "${nextDepthCnt1 > 0}">
        <li class="nav__item">
        <c:choose>
            <c:when test="${appFormYn eq 'Y'}">
                    <a href="javascript:void(0);" onclick="leaveChkLayer('${item1.urlAdr}');">${item1.menuNm}</a>
            </c:when>
            <c:otherwise>
                    <a href="${item1.urlAdr}">${item1.menuNm}</a>
            </c:otherwise>
        </c:choose>
    </c:if>
        <c:forEach items="${GNBlistdepth2}" var="item2" varStatus="vsdepth2">
            <c:set var="menuCode2" value="${item2.menuCode}"/>
            <c:set var="nextDepthCnt2" value="${item2.nextDepthCnt}"/>
            <c:if test = "${nextDepthCnt1 > 0}">
                <c:if test = "${vsdepth2.count eq 1}">
                    <ul class="nav nav--depth2">
                </c:if>
            </c:if>
            <c:if test = "${menuCode1 eq item2.prntsKey}">
                        <li class="nav__item">
                <c:if test = "${nextDepthCnt2 > 0}">
                            <strong>${item2.menuNm}</strong>
                    <c:forEach items="${GNBlistdepth3}" var="item3" varStatus="vsdepth3">
                        <c:set var="menuCode3" value="${item3.menuCode}"/>
                        <c:set var="nextDepthCnt3" value="${item3.nextDepthCnt}"/>
                        <c:if test = "${vsdepth3.count eq 1}">
                            <ul class="nav nav--depth3">
                        </c:if>
                        <c:if test = "${menuCode2 eq item3.prntsKey}">
                                <li class="nav__item">
                            <c:if test = "${item3.menuLinkCd eq 'MENUTP03'}">
                                  <a href="javascript:openPage('pullPopup','${item3.urlAdr}');">${item3.menuNm}</a>
                            </c:if>
                            <c:if test = "${item3.menuLinkCd ne 'MENUTP03'}">
                                <c:if test = "${item3.menuLinkCd eq 'MENUTP01'}">
                                  <a href="javascript:void(0);" onclick="handleEmptyMenuClick('${item3.menuCode}')">${item3.menuNm}</a>
                                </c:if>
                                <c:if test = "${item3.menuLinkCd ne 'MENUTP01'}">
                                    <c:choose>
                                        <c:when test="${appFormYn eq 'Y'}">
                                          <a href="javascript:void(0);" onclick="leaveChkLayer('${item3.urlAdr}');">${item3.menuNm}</a>
                                        </c:when>
                                        <c:otherwise>
                                          <a href="${item3.urlAdr}" data-code="${fn:replace(item3.menuCode, 'PCGNB', '')}" >${item3.menuNm}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:if>
                                </li>
                        </c:if>
                    </c:forEach>
                            </ul>
                </c:if>
                <c:if test = "${nextDepthCnt2 eq 0}">
                    <c:if test = "${item2.menuLinkCd eq 'MENUTP03'}">
                            <a href="javascript:openPage('pullPopup','${item2.urlAdr}');">${item2.menuNm}</a>
                    </c:if>
                    <c:if test = "${item2.menuLinkCd ne 'MENUTP03'}">
                        <c:if test = "${item2.menuLinkCd eq 'MENUTP01'}">
                            <a href="javascript:void(0);" onclick="handleEmptyMenuClick('${item2.menuCode}')">${item2.menuNm}</a>
                        </c:if>
                        <c:if test = "${item2.menuLinkCd ne 'MENUTP01'}">
                            <c:choose>
                                <c:when test="${appFormYn eq 'Y'}">
                                    <a href="javascript:void(0);" onclick="leaveChkLayer('${item2.urlAdr}');">${item2.menuNm}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${item2.urlAdr}" data-code="${fn:replace(item2.menuCode, 'PCGNB', '')}" >${item2.menuNm}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:if>
                </c:if>
                        </li>
            </c:if>
        </c:forEach>
                    </ul>
        </li>
</c:forEach>
          </ul>
        </div>

        <!--헤더 유틸 메뉴-->
        <div class="ly-header__util">
          <div class="util-wrap">
              <c:choose>
                <c:when test="${appFormYn eq 'Y'}">

                    <c:choose>
                      <c:when test="${'Y' eq isFreeUsimView}">
                        <a class="util-wrap__link" href="javascript:void(0);" onclick="leaveChkLayer('/apply/replaceUsimView.do');">교체 유심 신청</a>
                      </c:when>
                      <c:otherwise>
                        <a class="util-wrap__link" href="javascript:void(0);" onclick="openPage('pullPopup', '/retention/retentionInfoView.do');">재약정/기변신청</a>
                      </c:otherwise>
                    </c:choose>
                    <a class="util-wrap__link" href="javascript:void(0);" onclick="leaveChkLayer('/order/orderList.do');">신청조회</a>
                    <a class="util-wrap__link __loader__" href="javascript:void(0);" onclick="leaveChkLayer('/mypage/myinfoView.do');">마이페이지</a>

                    <c:if test="${'Y' eq isFqcView.dtlCdNm}" >
                        <c:choose>
                            <c:when test="${not empty sessionScope.USER_SESSION}">
                                <button type="button" class="util-wrap__link u-co-black u-fw--medium" id="freqToggleBtn"  aria-expanded="false" aria-controls="freqSummary" onclick="freqSummary()">M프리퀀시</button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="util-wrap__link u-co-black u-fw--medium" id="freqToggleBtn"  aria-expanded="false" aria-controls="freqSummary" onclick="leaveChkLayer('/loginForm.do?uri=/fqc/fqcEventView.do');" >M프리퀀시</button>
                            </c:otherwise>
                        </c:choose>
                    </c:if>

                </c:when>
                <c:otherwise>

                    <c:choose>
                      <c:when test="${'Y' eq isFreeUsimView}">
                        <a class="util-wrap__link" href="/apply/replaceUsimView.do">교체 유심 신청</a>
                      </c:when>
                      <c:otherwise>
                        <a class="util-wrap__link" href="javascript:void(0);" onclick="openPage('pullPopup', '/retention/retentionInfoView.do');">재약정/기변신청</a>
                      </c:otherwise>
                    </c:choose>
                    <a class="util-wrap__link" href="/order/orderList.do">신청조회</a>
                    <a class="util-wrap__link __loader__" href="/mypage/myinfoView.do">마이페이지</a>
                    <c:if test="${'Y' eq isFqcView.dtlCdNm}" >
                        <c:choose>
                            <c:when test="${not empty sessionScope.USER_SESSION}">
                                <button type="button" class="util-wrap__link u-co-black u-fw--medium" id="freqToggleBtn"  aria-expanded="false" aria-controls="freqSummary" onclick="freqSummary()">M프리퀀시</button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="util-wrap__link u-co-black u-fw--medium" id="freqToggleBtn"  aria-expanded="false" aria-controls="freqSummary" onclick="location.href='/loginForm.do?uri=/fqc/fqcEventView.do'" >M프리퀀시</button>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:otherwise>
            </c:choose>
          </div>



<c:choose>
   <c:when test="${not empty sessionScope.USER_SESSION}">
      <c:choose>
        <c:when test="${appFormYn eq 'Y'}">
          <button class="ly-header__state-btn" type="button" onclick="leaveChkLayer('/logOutProcess.do');">로그아웃</button>
        </c:when>
        <c:otherwise>
          <button class="ly-header__state-btn" type="button" onclick="location.href='/logOutProcess.do'">로그아웃</button>
        </c:otherwise>
      </c:choose>
   </c:when>
   <c:otherwise>
      <c:choose>
        <c:when test="${appFormYn eq 'Y'}">
          <button class="ly-header__state-btn" type="button" onclick="leaveChkLayer('/loginForm.do');">로그인</button>
        </c:when>
        <c:otherwise>
          <button class="ly-header__state-btn" type="button" onclick="location.href='/loginForm.do'">로그인</button>
        </c:otherwise>
      </c:choose>
   </c:otherwise>
</c:choose>
          <button class="ly-header__search-btn" type="button">
            <div class="c-hidden">통합검색 열기</div>
            <div class="c-icon c-icon--search-red"></div>
          </button>
          <button class="ly-header__allmenu">
              <div class="c-hidden">전체메뉴 열기</div>
          </button>
        </div>
        <!--헤더 스와이프 배너-->
        <div class="ly-header__banner">
          <div class="swiper-container">
            <div class="swiper-wrapper">
<c:set var="t002List" value="${nmcp:getBannerList('T002')}" />
<c:if test = "${t002List.size() > 0}">
<c:forEach items="${t002List}" var="item">
                <c:choose>
                    <c:when test="${appFormYn eq 'Y'}">
                        <c:choose>
                            <c:when test="${item.linkTarget eq 'Y'}">
                                <a class="swiper-slide swiper-banner__anchor" href="${item.linkUrlAdr}" target="_blank" onclick="insertBannAccess('${item.bannSeq}','${item.bannCtg}');">
                            </c:when>
                            <c:otherwise>
                                <a class="swiper-slide swiper-banner__anchor" href="javascript:void(0);" onclick="insertBannAccess('${item.bannSeq}','${item.bannCtg}');leaveChkLayer('${item.linkUrlAdr}');">
                            </c:otherwise>
                        </c:choose>
                        <img src="${item.bannImg}" alt="${item.imgDesc}">
                      </a>
                    </c:when>
                    <c:otherwise>
                    <c:choose>
                        <c:when test="${item.linkTarget eq 'Y'}">
                            <a class="swiper-slide swiper-banner__anchor" href="${item.linkUrlAdr}" target="_blank" onclick="insertBannAccess('${item.bannSeq}','${item.bannCtg}');" >
                        </c:when>
                        <c:otherwise>
                            <a class="swiper-slide swiper-banner__anchor" href="${item.linkUrlAdr}" onclick="insertBannAccess('${item.bannSeq}','${item.bannCtg}');" >
                        </c:otherwise>
                    </c:choose>
                        <img src="${item.bannImg }" alt="${item.imgDesc}">
                      </a>
                    </c:otherwise>
                </c:choose>
                </c:forEach>
                </c:if>
                <c:set var="g001List" value="${nmcp:getBannerList('G001')}" />
                <c:if test = "${t002List.size() == 0}">
                <c:forEach items="${g001List}" var="item">
                                <c:choose>
                                    <c:when test="${appFormYn eq 'Y'}">
                                      <a class="swiper-slide swiper-banner__anchor" href="javascript:void(0);" onclick="insertBannAccess('${item.bannSeq}','${item.bannCtg}');leaveChkLayer('${item.linkUrlAdr}');">
                                        <img src="${item.bannImg }" alt="${item.bannNm }">
                                      </a>
                                    </c:when>
                                    <c:otherwise>
                                      <a class="swiper-slide swiper-banner__anchor" href="${item.linkUrlAdr}" onclick="insertBannAccess('${item.bannSeq}','${item.bannCtg}');" >
                                        <img src="${item.bannImg }" alt="${item.bannNm }">
                                      </a>
                                    </c:otherwise>
                                </c:choose>
                </c:forEach>
                </c:if>
            </div>

            <div class="swiper-controls-main">
              <div class="swiper-controls">
                <button class="swiper-button-prev" type="button"> </button>
                <div class="swiper-pagination"></div>
                <button class="swiper-button-next" type="button"></button>
                <button class="swiper-button-pause" type="button">
                  <span class="c-hidden">자동재생 정지</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--헤더 BG-->
      <div class="ly-header__bg"></div>
      <!--스크롤 시 햄버거(전체메뉴)메뉴 플로팅-->
      <div class="ly-header__fixed">
        <button type="button">
          <div class="c-hidden">전체메뉴 열기</div>
        </button>
      </div>
    </header>

    <!-- PC 현재위치 인식기능 도입 시작-->
    <c:set var="menuInfo" value="${nmcp:getCurrentMenuInfo()}" />
    <c:set var="currentUrl" value="${menuInfo.url}" />
    <c:set var="depth1" value="" />
    <c:set var="depth2" value="" />
    <c:set var="depth3" value="" />
    <c:set var="depth1Url" value="" />
    <c:set var="depth2Url" value="" />

    <!-- 3뎁스까지 탐색 -->
    <c:forEach var="d1" items="${menulistdepth1}">
      <c:forEach var="d2" items="${menulistdepth2}">
        <c:if test="${d2.prntsKey eq d1.menuCode}">
          <c:forEach var="d3" items="${menulistdepth3}">
             <c:if test="${d3.prntsKey eq d2.menuCode && currentUrl eq d3.urlAdr}">
              <c:set var="depth1" value="${d1.menuNm}" />
              <c:set var="depth1Url" value="${d1.urlAdr}" />
              <c:set var="depth2" value="${d2.menuNm}" />
              <c:set var="depth2Url" value="${d2.urlAdr}" />
              <c:set var="depth3" value="${d3.menuNm}" />
            </c:if>
          </c:forEach>
        </c:if>
      </c:forEach>
    </c:forEach>

    <!-- 2뎁스까지 일치할 경우 -->
    <c:if test="${empty depth3}">
      <c:forEach var="d1" items="${menulistdepth1}">
        <c:forEach var="d2" items="${menulistdepth2}">
          <c:if test="${d2.prntsKey eq d1.menuCode && currentUrl eq d2.urlAdr}">
            <c:set var="depth1" value="${d1.menuNm}" />
            <c:set var="depth1Url" value="${d1.urlAdr}" />
            <c:set var="depth2" value="${d2.menuNm}" />
          </c:if>
        </c:forEach>
      </c:forEach>
    </c:if>

    <!-- 1뎁스만 일치하는 경우 -->
    <c:if test="${empty depth2 && empty depth3}">
      <c:forEach var="d1" items="${menulistdepth1}">
        <c:if test="${currentUrl eq d1.urlAdr}">
          <c:set var="depth1" value="${d1.menuNm}" />
        </c:if>
      </c:forEach>
    </c:if>

    <div class="ly-bread-wrap">
        <ul class="ly-bread-list">
            <c:if test="${not empty depth1}">
              <li><a href="/main.do" title="홈 화면 바로가기">홈</a></li>
            </c:if>

            <c:if test="${not empty depth1}">
              <li><a href="${depth1Url}" title="${depth1} 바로가기">${depth1}</a></li>
            </c:if>

            <c:if test="${not empty depth2}">
              <c:choose>
                <c:when test="${not empty depth3}">
                  <li><a href="${depth2Url}" title="${depth2} 바로가기">${depth2}</a></li>
                </c:when>
                <c:otherwise>
                  <li>${depth2}</li>
                </c:otherwise>
              </c:choose>
            </c:if>

            <c:if test="${not empty depth3}">
              <li>${depth3}</li>
            </c:if>
        </ul>
    </div>
    <!-- PC 현재위치 인식기능 도입 종료-->

    <!-- 프리퀀시 팝업 -->
    <div class="freq-summary-container">
      <div class="freq-summary" id="freqSummary" role="region" aria-labelledby="freqToggleBtn" tabindex="-1">
        <div class="freq-summary-wrap" >
          <div class="freq-summary__title">
            <p class="c-modal__title"><span>${nmcp:getMaskedName(USER_SESSION.name)}</span>님의 M프리퀀시</p>
            <button type="button" class="c-button" id="freqCloseButton" onclick="closeFreqSummary()" aria-label="M프리퀀시 닫기">
              <i class="c-icon c-icon--close2" aria-hidden="true"></i>
              <span class="c-hidden">팝업닫기</span>
            </button>
          </div>
          <div class="freq-summary__content">
            <img src="/resources/images/portal/frequency/freq_main_banner.png" alt="스템프 완성하고 최대 20만원 받기">
            <div class="freq-summary-stamp">
              <div class="freq-summary-stamp__status-wrap">
                <p>현재  <span>0</span> M탬프 달성</p>
                <div class="freq-summary-stamp__status">
                  <span>0</span>/<span>7</span>
                </div>
              </div>
              <ul class="freq-summary-stamp__progress">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
              </ul>
            </div>
            <div class="freq-summary-button">
              <a href="/event/eventDetail.do?ntcartSeq=${fqcEventSeq.dtlCdNm}" title="M프리퀀시 이벤트 페이지 이동">
                <p>M프리퀀시<br/>이벤트</p>
              </a>
              <a href="/fqc/fqcEventView.do" rel="nosublink" title="내 스탬프 모으기 페이지 이동"><p>내 스탬프<br/>모으기</p></a>
            </div>
          </div>
        </div>
      </div>
    </div>

<!--전체메뉴 영역-->
<div class="ly-allmenu">
  <div class="ly-allmenu__wrap">
    <button class="ly-allmenu__close" type="button">
      <div class="c-hidden">전체메뉴 닫기</div>
    </button>

<!-- navy start -->
    <div class="nav">
      <ul class="nav nav--depth1">

<c:set var="menuCode1" value=""/>
<c:set var="nextDepthCnt1" value="0"/>
<c:set var="menuCode2" value=""/>
<c:set var="nextDepthCnt2" value="0"/>
<c:set var="menuCode3" value=""/>
<c:set var="nextDepthCnt3" value="0"/>
<c:forEach items="${menulistdepth1}" var="item1" varStatus="vsdepth1">
    <c:set var="menuCode1" value="${item1.menuCode}"/>
    <c:set var="nextDepthCnt1" value="${item1.nextDepthCnt}"/>
    <c:if test = "${nextDepthCnt1 > 0}">
        <li class="nav__item">
            <a href="#n">${item1.menuNm}</a>
    </c:if>
        <c:forEach items="${menulistdepth2}" var="item2" varStatus="vsdepth2">
            <c:set var="menuCode2" value="${item2.menuCode}"/>
            <c:set var="nextDepthCnt2" value="${item2.nextDepthCnt}"/>
            <c:if test = "${nextDepthCnt1 > 0}">
                <c:if test = "${vsdepth2.count eq 1}">
                    <c:if test = "${vsdepth1.count eq 6}"><%-- 6단계 나누기 --%>
                    <div class="mypage-wrap">
                        <c:set var="myPageMenuKey" value="${menuCode1}"/>
                    </c:if>
                    <ul class="nav nav--depth2"   >
                </c:if>
                <c:if test = "${vsdepth1.count eq 6 and item2.prntsKey eq myPageMenuKey }"><%-- 6단계 나누기 --%>
                    <c:set var="myPageCnt" value="${myPageCnt+1}"/>
                </c:if>
                <c:if test = "${vsdepth1.count eq 6 and myPageCnt eq 4}"><%-- 6단계 나누기 --%>
                    <!-- <div class="mypage-wrap"> -->
                    <ul class="nav nav--depth2">
                </c:if>
            </c:if>
            <c:if test = "${menuCode1 eq item2.prntsKey}">
                        <li class="nav__item" myPageCnt="${myPageCnt}"  >
                <c:if test = "${nextDepthCnt2 > 0}">
                            <strong>${item2.menuNm}</strong>
                    <c:forEach items="${menulistdepth3}" var="item3" varStatus="vsdepth3">
                        <c:set var="menuCode3" value="${item3.menuCode}"/>
                        <c:set var="nextDepthCnt3" value="${item3.nextDepthCnt}"/>
                        <c:if test = "${vsdepth3.count eq 1}">
                            <ul class="nav nav--depth3">
                        </c:if>
                        <c:if test = "${menuCode2 eq item3.prntsKey}">
                                <li class="nav__item">
                            <c:if test = "${item3.menuLinkCd eq 'MENUTP03'}">
                                  <a href="javascript:openPage('pullPopup','${item3.urlAdr}');">${item3.menuNm}</a>
                            </c:if>
                            <c:if test = "${item3.menuLinkCd ne 'MENUTP03'}">
                                <c:if test = "${item3.menuLinkCd eq 'MENUTP01'}">
                                  <a href="javascript:void(0);" onclick="handleEmptyMenuClick('${item3.menuCode}')">${item3.menuNm}</a>
                                </c:if>
                                <c:if test = "${item3.menuLinkCd ne 'MENUTP01'}">
                                    <c:choose>
                                        <c:when test="${appFormYn eq 'Y'}">
                                          <a href="javascript:void(0);" onclick="leaveChkLayer('${item3.urlAdr}');">${item3.menuNm}</a>
                                        </c:when>
                                        <c:otherwise>
                                          <a href="${item3.urlAdr}"  data-code="${fn:replace(item3.menuCode, 'PCMENU', '')}"  >${item3.menuNm}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:if>
                                </li>
                        </c:if>
                    </c:forEach>
                            </ul>
                </c:if>
                <c:if test = "${nextDepthCnt2 eq 0}">
                    <c:if test = "${item2.menuLinkCd eq 'MENUTP03'}">
                            <a href="javascript:openPage('pullPopup','${item2.urlAdr}');">${item2.menuNm}</a>
                    </c:if>
                    <c:if test = "${item2.menuLinkCd ne 'MENUTP03'}">
                        <c:if test = "${item2.menuLinkCd eq 'MENUTP01'}">
                            <a href="javascript:void(0);" onclick="handleEmptyMenuClick('${item2.menuCode}')">${item2.menuNm}</a>
                        </c:if>
                        <c:if test = "${item2.menuLinkCd ne 'MENUTP01'}">
                            <c:choose>
                                <c:when test="${appFormYn eq 'Y'}">
                                    <a href="javascript:void(0);" onclick="leaveChkLayer('${item2.urlAdr}');">${item2.menuNm}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${item2.urlAdr}" data-code="${fn:replace(item2.menuCode, 'PCMENU', '')}" >${item2.menuNm}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:if>
                </c:if>
                        </li>
            </c:if>

            <c:if test = "${vsdepth1.count eq 6 and myPageCnt eq 3 }"><%-- 6단계 나누기 --%><%-- 6단계 나누기 --%>
                    </ul>
                </c:if>
        </c:forEach>
                    </ul>
        <c:if test = "${vsdepth1.count eq 6}"><%-- 6단계 나누기 --%>
            </div>
        </c:if>
        </li>
</c:forEach>

      </ul>
    </div>
<!-- navy end -->

  </div>
</div>

<script type="text/javascript">
  //챗봇 메뉴를 누르면 새창열기
    setTimeout(function(){
        var chatbot = $(".nav__item > a").filter(":contains('고객센터 챗봇')");
        $(chatbot).attr('title',"고객센터 챗봇 새창열림");
        $(chatbot).click(function(){
            var chatbotUrl = "https://chatbot.kt-aicc.com/client/20231226131140095/chat.html";
            window.open(chatbotUrl, '고객센터챗봇', 'width=420px,height=642px,scrollbars=yes')
            return false;
        });
    },100);

    // 프리퀀시 팝업
    const focusableSelectors = 'a[href], button, textarea, input[type="text"], input[type="radio"], input[type="checkbox"], select, [tabindex]:not([tabindex="-1"])';
    const popup = document.getElementById("freqSummary");
    const toggleBtn = document.getElementById("freqToggleBtn");

    function freqSummary() {
        popup.classList.add("show");
        toggleBtn.setAttribute("aria-expanded", "true");


        ajaxCommon.getItem({
            id: 'getFqcInfo'
            , cache: false
            , url: '/fqc/getFqcProAjax.do'
            , data: ''
            , dataType: "json"
            , async: false
        }, function (jsonObj) {
            if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                let msnCnt = jsonObj.MSN_CNT;
                $("#freqSummary .freq-summary-stamp__status-wrap").html( "<p>현재<span>"+msnCnt+"</span> M탬프 달성</p><div class=freq-summary-stamp__status ><span>"+msnCnt+"</span>/<span>7</span></div>") ;

                $("#freqSummary .freq-summary-stamp__progress li").each(function(index){
                    if (index < msnCnt) {
                        $(this).addClass("is-stamped");
                    }  else {
                        $(this).removeClass("is-stamped");
                    }
                });
            }

        });

        // 포커스 첫 요소로 이동
        const focusable = popup.querySelectorAll(focusableSelectors);
        if (focusable.length) focusable[0].focus();
    }

    function closeFreqSummary() {
        popup.classList.remove("show");
        toggleBtn.setAttribute("aria-expanded", "false");
        toggleBtn.focus();
    }



    document.addEventListener("keydown", function (e) {
        if (!popup.classList.contains("show")) return;

        if (e.key === "Escape") {
          e.preventDefault();
          closeFreqSummary();
        }
        // 웹접근성 키보드
        if (e.key === "Tab") {
          const focusable = popup.querySelectorAll(focusableSelectors);
          if (focusable.length === 0) return;

          const first = focusable[0];
          const last = focusable[focusable.length - 1];

          if (e.shiftKey) {
            // Shift + Tab (역방향)
            if (document.activeElement === first) {
              e.preventDefault();
              last.focus();
            }
          } else {
            // Tab (정방향)
            if (document.activeElement === last) {
              e.preventDefault();
              first.focus();
            }
          }
        }
    });

    function handleEmptyMenuClick(menuCode){

      const gnbMenuCode = $("#AIGnbMenuCode").val();
      const allMenuCode = $("#AIMenuCode").val();

      // AI 추천 메뉴 여부 확인 후 팝업 오픈
      if(menuCode === gnbMenuCode || menuCode === allMenuCode){
        aiRecommendPopOpen();
      }
    }

</script>

    <%@ include file="/WEB-INF/views/portal/search/searchView.jsp" %>
