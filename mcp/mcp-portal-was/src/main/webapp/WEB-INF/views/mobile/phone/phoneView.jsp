<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />
<c:set var="cardDcInfoList" value="${nmcp:getCodeList('BE0020')}" />
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e302056d1b213cb21f683504be594f25&libraries=services,clusterer,drawing"></script>
        <script type="text/javascript" src="/resources/js/mobile/phone/phoneView.js?version=22-10-13"></script>
        <script type="text/javascript" src="/resources/js/mobile/phone/phoneInventoryStoreList.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script type="text/javascript">
        var platFormCd = '<c:out value="${platFormCd}" />';

        <c:choose>
        <c:when test="${phoneProdBas.sesplsYn eq 'Y'}">
             var prodCtgType = '03';
        </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${phoneProdBas.prodType eq '04'}">
            var prodCtgType = '02';
            </c:when>
            <c:otherwise>
            var prodCtgType = '01';
            </c:otherwise>
       </c:choose>
    </c:otherwise>
    </c:choose>
    </script>
    <script>
       <c:if test="${phoneProdBas.sesplsYn eq 'Y' && !empty cardDcInfoList}">
               window.onload = function() {
              var instance = KTM.Accordion.getInstance(document.querySelector('.c-accordion--type1'));
              instance.openAll();
            };
       </c:if>
      </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <form id="phoneViewfrm" name="phoneViewfrm" method="post">
            <input type="hidden" id="pageNo" name="pageNo" value="1" />
            <input type="hidden" id="listOrderEnum" name="listOrderEnum" />
            <input type="hidden" id="fprodNm" value='<c:out value="${phoneProdBas.prodNm }"/>' />
            <input type="hidden" id="fprodId" value='<c:out value="${phoneProdBas.prodId }"/>' />
            <input type="hidden" id="hndsetModelId" value="${hndsetModelId}" />
            <div class="ly-content" id="main-content">
                <div class="ly-page-sticky">
                    <h2 class="ly-page__head">${phoneProdBas.prodNm }<button class="header-clone__gnb"></button></h2>
                </div>
                <div class="phone-detail c-expand">
                    <div id="phoneSwiper" class="swiper-container">
                        <div class="swiper-wrapper"></div>
                        <button class="swiper-prev" type="button">
                            <i class="c-icon c-icon--arrow-left" aria-hidden="true"></i>
                        </button>
                        <button class="swiper-next" type="button">
                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                        <div class="swiper-pagination"></div>
                    </div>
                </div>
                <div class="u-mt--40">
                    <span class="c-label--Network">${phoneProdBas.prodCtgIdLabel}</span>
                </div>
                <div class="phone-price">
                    <c:choose>
                        <c:when test="${phoneProdBas.sesplsYn eq 'Y'}">
                            <dl class="phone-price__release">
                                <dt>출고가</dt>
                                <dd>
                                    <fmt:formatNumber value="${phoneProdBas.inUnitPric}" pattern="#,###" /> 원
                                </dd>
                            </dl>
                            <dl class="phone-price__installments">
                                <dt>판매가</dt>
                                <dd>
                                    <b><fmt:formatNumber value="${phoneProdBas.outUnitPric}" pattern="#,###" /></b> <span>원</span>
                                </dd>
                            </dl>
                        </c:when>
                        <c:otherwise>
                            <dl class="phone-price__release">
                                <dt>출고가</dt>
                                <dd>
                                    <fmt:formatNumber value="${chargeList[0].hndstAmt}" pattern="#,###" /> 원
                                </dd>
                            </dl>
                            <dl class="phone-price__installments">
                                <dt>할부원금</dt>
                                <dd>
                                    <b><fmt:formatNumber value="${minValue}" pattern="#,###" /></b>
                                    <span class="u-ml--2">원 ~</span> <b><fmt:formatNumber value="${maxValue}" pattern="#,###" /></b> <span>원</span>
                                </dd>
                            </dl>
                        </c:otherwise>
                    </c:choose>
                </div>

                <c:if test="${phoneProdBas.sesplsYn eq 'Y' && !empty cardDcInfoList}">
                    <div class="c-accordion c-accordion--type1 card u-mt--24">
                        <ul class="c-accordion__box" id="accordionB">
                            <li class="c-accordion__item">
                                <div class="c-accordion__head" data-acc-header="#chkCard">
                                    <button class="c-accordion__button" type="button" aria-expanded="false">카드 즉시 할인</button>
                                </div>
                                <div class="c-accordion__panel expand c-expand" id="chkCard">
                                    <div class="c-accordion__inside">
                                        <div class="card-sale">
                                            <c:forEach items="${cardDcInfoList}" var="cardDcInfo">
                                                <dl class="card-sale__panel">
                                                    <dt class="card-sale__title">
                                                        <c:out value="${cardDcInfo.dtlCdNm}"></c:out>
                                                    </dt>
                                                    <dd class="card-sale__text">
                                                        <p>
                                                            <c:choose>
                                                                <c:when test="${cardDcInfo.expnsnStrVal1 eq 'PCT'}">
                                                                    <em class="u-co-point-4 u-fw--medium"><c:out value="${cardDcInfo.expnsnStrVal2}"></c:out>%</em>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <em class="u-co-point-4 u-fw--medium"><fmt:formatNumber value="${cardDcInfo.expnsnStrVal2}" pattern="#,###" />원</em>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            카드 즉시 할인(최대<fmt:formatNumber value="${cardDcInfo.expnsnStrVal3}" pattern="#,###" />원 할인)
                                                        </p>
                                                        <p class="u-co-gray">
                                                            <fmt:parseDate value="${fn:substring(cardDcInfo.pstngStartDate, 0,8)}" var="pstngStartDate" pattern="yyyyMMdd" />
                                                            <fmt:formatDate value="${pstngStartDate}" pattern="yyyy-MM-dd" />
                                                            ~
                                                            <fmt:parseDate value="${fn:substring(cardDcInfo.pstngEndDate, 0,8)}" var="pstngEndDate" pattern="yyyyMMdd" />
                                                            <fmt:formatDate value="${pstngEndDate}" pattern="yyyy-MM-dd" />
                                                        </p>
                                                    </dd>
                                                </dl>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </c:if>

                <div class="c-form">
                    <span class="c-form__title u-mt--8">용량</span>
                    <select class="c-select" id="atribValCd2" name="atribValCd2">

                    </select>
                </div>
                <div class="c-form phone-color-wrap">
                    <dl class="u-mt--17 u-mb--12">
                        <dt>
                            <span class="u-fw--medium">색상</span>
                        </dt>
                        <dd>
                            <span id="colorNm"></span>
                        </dd>
                    </dl>
                    <div class="color-group" id="colorGroupDiv"></div>
                </div>
                <button id="phone_join_button" type="button" class="c-button c-button--full c-button--red c-button--h48 u-mt--32" onclick="go_join_page();">가입하기</button>
                <!-- 개선/신규 사항 #24331 휴대폰 페이지내 구매가능한 매장 미노출 처리 start -->
                <!--
                  <a class="offline-store-banner" href="#n" onclick="goStore();">
                    <p class="c-text c-text--type2"> 구매 가능한 오프라인 매장을
                      <br>확인하세요!
                    </p>
                    <span class="c-text c-text--type3 c-flex u-mt--16">구매가능 매장<i class="c-icon c-icon--arrow-right-yellow" aria-hidden="true"></i>
                    </span>
                    <i class="c-icon c-icon--store" aria-hidden="true"></i>
                  </a>
                  -->
                <!-- 개선/신규 사항 #24331 휴대폰 페이지내 구매가능한 매장 미노출 처리 end -->
                <div class="c-expand">
                    <hr class="c-hr c-hr--type1 u-mt--40 u-mb--0">
                    <div class="c-tabs c-tabs--type3">
                        <div class="c-tabs__list" data-tab-list="">
                            <button class="c-tabs__button" data-tab-header="#tab-panel">상품정보</button>
                            <button class="c-tabs__button" data-tab-header="#tab-pane2">상품스펙</button>
                            <button class="c-tabs__button" data-tab-header="#tab-pane3">사용후기</button>
                        </div>
                        <div class="c-tabs__panel c-un-expand" id="tab-panel">
                            <div class="phone-info__detail-box">${phoneProdBas.sntyDetailSpec}</div>
                        </div>
                        <div class="c-tabs__panel c-un-expand" id="tab-pane2">
                            <div class="c-table">
                                <table>
                                    <caption>테이블 정보</caption>
                                    <colgroup>
                                        <col>
                                        <col>
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th class="u-ta-left">휴대폰 명</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyProdNm}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">휴대폰 설명</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyProdDesc}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">모델번호</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyModelId}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">네트워크</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyNet}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">브랜드/제조사</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyMaker}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">출시월</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyRelMonth}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">색상</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyColor}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">디스플레이</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyDisp}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">크기</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntySize}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">무게</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyWeight}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">메모리</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyMemr}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">배터리</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyBtry}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">OS(운영체제)</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyOs}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">대기시간</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyWaitTime}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">카메라</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyCam}" default="-" /></td>
                                        </tr>
                                        <tr>
                                            <th class="u-ta-left">영상통화</th>
                                            <td class="u-ta-left"><c:out value="${phoneProdBas.sntyVideTlk}" default="-" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="c-tabs__panel c-un-expand" id="tab-pane3">
                            <div class="u-mt--36" id="reviewBody">
                                <ul class="review" id="reviewBoard">
                                </ul>
                            </div>
                            <div class="c-button-wrap">
                                <button class="c-button c-button--full" type="button" id="moreBtn">
                                    더보기
                                    <span class="c-button__sub">
                                    <span id="reViewCurrent"></span>/
                                    <span id="reViewTotal"></span></span>
                                    <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </form>
        <form id="appForm" name="appForm" method="post" action="/m/appForm/appFormDesignView.do">
        <input type="hidden" name="hndsetModelId" />
        <input type="hidden" name="rateCd" value='<c:out value="${rateCd}"/>'/>
        <input type="hidden" name="prodId" value='<c:out value="${phoneProdBas.prodId }"/>'/>
        <input type="hidden" name="outUnitPric" value="<fmt:formatNumber value="${phoneProdBas.outUnitPric + phoneProdBas.outUnitPric * 0.1}" pattern="####" />"/>
        <input type="hidden" name="selPric" value="<fmt:formatNumber value="${phoneProdBas.outUnitPric + phoneProdBas.outUnitPric * 0.1}" pattern="####" />"/>
        <input type="hidden" name="prodCtgType" value=""/>
    </form>
    </t:putAttribute>
    <t:putAttribute name="popLayerAttr">
          <c:import url="/m/product/phone/phoneInventoryStoreMapInfo.do"></c:import>
    </t:putAttribute>

</t:insertDefinition>