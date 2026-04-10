<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/mypage/myCombineList.js?version=24-12-09"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    결합 내역 조회
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

            <div class="c-tabs c-tabs--type2">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <c:if test="${isMaster}">
                	    <button class="c-tabs__button" data-tab-header="#tabB4-panel" id="tab4" style="line-height: initial; padding: 0.75rem 0.375rem;">아무나SOLO결합</button>
                    </c:if>
                    <button class="c-tabs__button" data-tab-header="#tabB3-panel" id="tab3" style="line-height: initial; padding: 0.75rem 0.375rem;">아무나결합</button>
                    <button class="c-tabs__button" data-tab-header="#tabB1-panel" id="tab1" style="line-height: initial; padding: 0.75rem 0.375rem;">아무나가족결합+</button>
                    <button class="c-tabs__button" data-tab-header="#tabB2-panel" id="tab2" style="line-height: initial; padding: 0.75rem 0.375rem;">함께쓰기</button>
                </div>
                <hr class="c-hr c-hr--type3 c-expand">
                <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
                <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp"%>

                <div class="c-tabs__panel border-none" id="tabB1-panel">
                    <div class="">
                        <h3 class="combin-history__tit">결합 중인 회선</h3>
                        <div class="combin-history__myinfo c-table">
                            <table>
                                <caption>조회회선, 무료결합데이터를 포함한 결합 중인 회선 정보</caption>
                                <colgroup>
                                    <col style="width: 32%">
                                    <col>
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th scope="row">조회 회선</th>
                                    <td>
                                        <p class="_ctn"><!--010-12**-*123--></p>
                                        <p class="rate-info _rate"><!--데이터 맘껏 15GB+/300분(기프티쇼 5000P)--></p>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">무료결합데이터</th>
                                    <td class="progress-info _serviceNm" id="pServiceNm">-</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div id="divCombinHistory"  class="combin-history__list c-table" style="display: none">
                            <table>
                                <caption>kt M모바일, KT 인터넷, KT 모바일을 포함한 결합 중인 회선 정보</caption>
                                <colgroup>
                                    <col style="width: 32%">
                                    <col>
                                </colgroup>
                                <tbody>
                                    <%-- <tr>
                                        <th scope="row">kt M모바일</th>
                                        <td>
                                            <p>010-12**-*123</p>
                                            <p class="rate-info">데이터 맘껏 15GB+/300분</p>
                                            <p class="date-info">(결합일 : 2022.12.31)</p>
                                        </td>
                                    </tr> --%>
                                </tbody>
                            </table>
                        </div>
                        <div id="divCombinHistory2"  class="combin-history__progress" style="display: none">
                            <h3>결합 진행 현황</h3>
                            <p class="combin-history__subtit">심사가 완료되면 상단의 결합중인 회선에 결합 내역이 노출됩니다.</p>
                            <div class="combin-history__proginfo c-table">
                                <table>
                                    <caption>결합 대상 회선, 진행현황을 포함한 결합 진행 현황 정보</caption>
                                    <colgroup>
                                        <col style="width: 32%">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                        <%-- <tr>
                                            <th scope="row">kt 인터넷</th>
                                            <td>
                                                <p>z!1234****</p>
                                                <span class="progress-info">승인대기</span>
                                            </td>
                                        </tr>
                                        --%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div id="divCombinHistoryNodata" class="combin-nodata c-nodata" style="display: none">
                            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                            <p class="c-noadat__text">현재 결합 중인 회선이 없습니다.</p>
                        </div>

                        <div class="c-notice-wrap">
                            <hr>
                            <h5 class="c-notice__title">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                <span>알려드립니다</span>
                            </h5>
                            <ul class="c-notice__list">
                                <li>결합된 회선의 해지는 고객센터(114, 1899-5000)를 통해 가능합니다.</li>
                            </ul>
                        </div>
                    </div>

                </div>
                <div class="c-tabs__panel border-none" id="tabB2-panel">
                    <div id="tabB2Body">

                    </div>
                </div>

                <div class="c-tabs__panel border-none" id="tabB3-panel">
                    <div class="">
                        <h3 class="combin-history__tit">결합 중인 회선</h3>
                        <div class="combin-history__myinfo c-table">
                            <table>
                                <caption>조회회선, 무료결합데이터를 포함한 결합 중인 회선 정보</caption>
                                <colgroup>
                                    <col style="width: 32%">
                                    <col>
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th scope="row">조회 회선</th>
                                    <td>
                                        <p class="_ctn"></p>
                                        <p class="rate-info _rate"></p>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">무료결합데이터</th>
                                    <td class="progress-info _serviceNm" id="pServiceNm">-</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div id="divCombinHistoryData" class="combin-nodata c-nodata u-co-blue" style="display: none">
                            <p class="c-noadat__text">해당 회선은 현재 ‘아무나 결합 중’입니다.</p>
                        </div>
                        <div id="divCombinHistoryNodata2" class="combin-nodata c-nodata" style="display: none">
                            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                            <p class="c-noadat__text">현재 결합 중인 회선이 없습니다.</p>
                        </div>

                        <div class="c-notice-wrap">
                            <hr>
                            <h5 class="c-notice__title">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                <span>알려드립니다</span>
                            </h5>
                            <ul class="c-notice__list">
                                <li>결합된 회선의 해지는 고객센터(114, 1899-5000)를 통해 가능합니다.</li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- 아무나 Solo -->
                <div class="c-tabs__panel border-none" id="tabB4-panel">
                    <div class="">
                        <h3 class="combin-history__tit _soloInfo" style="display: none">결합 중인 회선</h3>
                        <div class="combin-history__myinfo c-table _soloInfo" style="display: none">
                            <table>
                                <caption>조회회선, 무료결합데이터를 포함한 결합 중인 회선 정보</caption>
                                <colgroup>
                                    <col style="width: 32%">
                                    <col>
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th scope="row">조회 회선</th>
                                    <td>
                                        <p class="_ctn"></p>
                                        <p class="rate-info _rate"></p>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">무료결합데이터</th>
                                    <td class="progress-info _serviceNm" id="tdServiceNm">-</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="divSoloNone" class="combin-nodata c-nodata" style="display: none">
                            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                            <p class="c-noadat__text">현재 결합 중인 회선이 없습니다.</p>
                            <div class="c-button-wrap">
				                <a class="c-button c-button--full c-button--primary" href="/m/content/combineSelf.do" title="결합 신청 안내 페이지 바로가기">결합 신청 안내</a>
				            </div>
                        </div>
                        <div class="c-notice-wrap">
                            <hr>
                            <h5 class="c-notice__title">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                <span>알려드립니다</span>
                            </h5>
                            <ul class="c-notice__list">
                                <li>결합(아무나SOLO결합, 아무나결합, 아무나가족결합) 시 제공되는 혜택은 중복으로 적용되지 않습니다.</li>
                                <li>결합된 회선의 해지는 고객센터(114, 1899-5000)를 통해 가능합니다.</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- [START]-->
    </t:putAttribute>
</t:insertDefinition>