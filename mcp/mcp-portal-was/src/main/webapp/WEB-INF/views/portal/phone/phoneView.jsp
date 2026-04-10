<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<c:set var="cardDcInfoList" value="${nmcp:getCodeList('BE0020')}" />
<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">
    <c:choose>
        <c:when test="${phoneProdBas.sesplsYn eq 'Y'}">
             자급제
        </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${phoneProdBas.prodType eq '04'}">
                    중고폰
            </c:when>
            <c:otherwise>
                       새 휴대폰 구매하기
            </c:otherwise>
       </c:choose>
   </c:otherwise>
   </c:choose>
    kt M모바일 공식 다이렉트몰
    </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e302056d1b213cb21f683504be594f25&libraries=services,clusterer,drawing"></script>
    <script type="text/javascript" src="/resources/js/portal/phone/phoneView.js?version=22-10-13"></script>
    <script type="text/javascript" src="/resources/js/portal/phone/phoneInventoryStoreList.js"></script>
    <script type="text/javascript">
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
        <c:if test="${phoneProdBas.sesplsYn eq 'Y' && !empty cardDcInfoList}">
        document.addEventListener('UILoaded', function() {

          // [2022-02-15] 카드 즉시 할인 아코디언 기본값 펼침
          var instance = KTM.Accordion.getInstance(document.querySelector('.c-accordion--type3'));
          instance.openAll();

        });
        </c:if>
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
       <form name="form" id="form" method="post">
    </form>
    <form id="phoneViewfrm" name="phoneViewfrm" method="post" >
        <input type="hidden" id="pageNo" name="pageNo" value="1"/>
        <input type="hidden" id="listOrderEnum" name="listOrderEnum" />
        <input type="hidden" id="fprodNm" value='<c:out value="${phoneProdBas.prodNm }"/>'/>
        <input type="hidden" id="fprodId" value='<c:out value="${phoneProdBas.prodId }"/>'/>
        <input type="hidden" id="hndsetModelId" value="${hndsetModelId}"/>
    </form>
    <div class="ly-content" id="main-content">
        <div class="ly-page--title">
            <h2 class="c-page--title">${phoneProdBas.prodNm}</h2>
        </div>
        <div class="c-row c-row--lg">
            <div class="phone-detail--type2">
                <div class="phone-detail--left-col">
                    <div class="swiper-thumbs" id="swiperThumbs">
                        <div class="swiper-container">
                            <div class="swiper-wrapper"></div>
                        </div>
                        <button class="swiper-button-next c-hidden" type="button" tabindex="-1" aria-hidden="true"></button>
                        <button class="swiper-button-prev c-hidden" type="button" tabindex="-1" aria-hidden="true"></button>
                    </div>
                    <div class="swiper-detail" id="swiperDetail">
                        <div class="swiper-container">
                            <div class="swiper-wrapper"></div>
                        </div>
                    </div>
                </div>

                <div class="phone-detail--right-col">
                    <div class="phone-detail__title">
                        <span class="c-label--Network__detail">${phoneProdBas.prodCtgIdLabel}</span>
                    </div>
                    <div class="phone-detail__price">
                        <c:choose>
                            <c:when test="${phoneProdBas.sesplsYn eq 'Y'}">
                                <dl>
                                    <dt>출고가</dt>
                                    <dd class="u-co-gray"><fmt:formatNumber value="${phoneProdBas.inUnitPric}" pattern="#,###" />원</dd>
                                </dl>
                                <dl>
                                    <dt>판매가</dt>
                                    <dd>
                                        <span class="phone-detail__sum"><fmt:formatNumber value="${phoneProdBas.outUnitPric}" pattern="#,###" /></span> 원
                                    </dd>
                                </dl>
                            </c:when>
                            <c:otherwise>
                                <dl>
                                    <dt>출고가</dt>
                                    <dd class="u-co-gray"><fmt:formatNumber value="${chargeList[0].hndstAmt}" pattern="#,###" />원</dd>
                                </dl>
                                <dl>
                                    <dt>할부원금</dt>
                                    <dd>
                                        <span class="phone-detail__sum"><fmt:formatNumber value="${minValue}" pattern="#,###" /></span> 원 ~
                                        <span class="phone-detail__sum"><fmt:formatNumber value="${maxValue}" pattern="#,###" /></span> 원
                                    </dd>
                                </dl>
                            </c:otherwise>
                        </c:choose>
                        <dl class="u-mt--22 u-mb--20">
                            <dt>용량</dt>
                            <dd>
								<label class="c-label c-hidden" for="atribValCd2">용량 선택</label>
                                <select class="c-select" id="atribValCd2" name="atribValCd2">
                                    <option label="용량 선택" disabled value="">용량 선택</option>
                                </select>
                            </dd>
                        </dl>
                        <dl>
                            <dt>
                                <span class="phone-color-tit">색상</span>
                            </dt>
                            <dd class="phone-color-wrap">
                                <ul class="c-checktype-row color-group clearfix" id="colorGroupDiv">
                                </ul>
                            </dd>
                        </dl>
                    </div>
                    <div class="c-button-wrap u-mt--30">
                        <button type="button" class="c-button c-button--full c-button--red" onclick="go_join_page();">가입하기</button>
                    </div>
                </div>
            </div>
            <div class="c-tabs c-tabs--type2 col-3">
                <div class="c-tabs__list" role="tablist">
                    <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabC1panel" aria-selected="false" tabindex="-1">상품정보</button>
                    <button class="c-tabs__button" id="tab2" role="tab" aria-controls="tabC2panel" aria-selected="false" tabindex="-1">상품스펙</button>
                    <button class="c-tabs__button" id="tab3" role="tab" aria-controls="tabC3panel" aria-selected="false" tabindex="-1">사용후기</button>
                </div>
            </div>

            <div class="c-tabs__panel" id="tabC1panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
              <div>
                ${phoneProdBas.sntyDetailSpec}
              </div>
            </div>
            <div class="c-tabs__panel" id="tabC2panel" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
              <div class="c-table u-mt--48">
                <table>
                  <caption>휴대폰 명, 휴대폰 설명, 모델번호, 네트워크, 브랜드/제조사, 출시일, 색상, 디스플레이, 크기, 무게, 메모리, 배터리, OS(운영체제), 대기시간, 카메라, 영상통화를 포함하는 상품스펙 정보</caption>
                  <colgroup>
                    <col style="width: 166px">
                    <col>
                  </colgroup>
                  <tbody>
                    <tr>
                      <th scope="row">휴대폰 명</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyProdNm}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">휴대폰 설명</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyProdDesc}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">모델번호</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyModelId}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">네트워크</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyNet}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">브랜드/제조사</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyMaker}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">출시일</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyRelMonth}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">색상</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyColor}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">디스플레이</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyDisp}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">크기</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntySize}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">무게</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyWeight}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">메모리</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyMemr}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">배터리</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyBtry}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">OS(운영체제)</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyOs}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">대기시간</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyWaitTime}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">카메라</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyCam}" default="-" /></td>
                    </tr>
                    <tr>
                      <th scope="row">영상통화</th>
                      <td class="u-ta-left"><c:out value="${phoneProdBas.sntyVideTlk}" default="-" /></td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <div class="c-tabs__panel" id="tabC3panel" role="tabpanel" aria-labelledby="tab3" tabindex="-1">
              <div id="phoneViewReviewNodataDiv" class="c-nodata u-bt-gray-3 u-mt--48"><i class="c-icon c-icon--nodata" aria-hidden="true"></i><p class="c-noadat__text">조회 결과가 없습니다.</p></div>
              <div class="c-accordion review u-mt--40 u-bt--0" id="accordionReview" data-ui-accordion>
                <ul id="reviewBoard" class="c-accordion__box">
                </ul>
                <nav class="c-paging" aria-label="검색결과">
                </nav>
              </div>
            </div>
          </div>
    </div>
    <form id="appForm" name="appForm" method="post" action="/appForm/appFormDesignView.do">
       <input type="hidden" name="hndsetModelId" />
       <input type="hidden" name="rateCd" value='<c:out value="${rateCd}"/>'/>
       <input type="hidden" name="prodId" value='<c:out value="${phoneProdBas.prodId}"/>'/>
       <input type="hidden" name="outUnitPric" value="<fmt:formatNumber value="${phoneProdBas.outUnitPric + phoneProdBas.outUnitPric * 0.1}" pattern="####" />"/>
       <input type="hidden" name="selPric" value="<fmt:formatNumber value="${phoneProdBas.outUnitPric + phoneProdBas.outUnitPric * 0.1}" pattern="####" />"/>
       <input type="hidden" name="prodCtgType" value=""/>
   </form>
    <c:import url="/product/phone/phoneInventoryStoreMapInfo.do"></c:import>
    </t:putAttribute>
</t:insertDefinition>
