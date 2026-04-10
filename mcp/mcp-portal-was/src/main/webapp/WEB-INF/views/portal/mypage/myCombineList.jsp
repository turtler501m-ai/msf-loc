<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="titleAttr">결합 내역 조회</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/mypage/myCombineList.js?version=24-12-09"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">결합 내역 조회</h2>
            </div>
            <div class="c-tabs c-tabs--type1" data-ui-tab>
                <div class="c-tabs__list" role="tablist">
                    <c:if test="${isMaster}">
                        <button class="c-tabs__button" id="tab4" role="tab" aria-controls="tabB4panel" aria-selected="false" >아무나SOLO결합</button>
                    </c:if>
                    <button class="c-tabs__button" id="tab3" role="tab" aria-controls="tabB3panel" aria-selected="false" >아무나결합</button>
                    <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabB1panel" aria-selected="false" >아무나가족결합+</button>
                    <button class="c-tabs__button" id="tab2" role="tab" aria-controls="tabB2panel" aria-selected="false" >함께쓰기</button>
                </div>
            </div>
            <input type="hidden" name="contractNum" id="contractNum" value=${searchVO.contractNum}>

            <div class="c-row c-row--lg">
                <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">조회할 회선을 선택해 주세요.</h3>
                <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp"%>
                <hr class="c-hr c-hr--title">
            </div>
            <div class="c-tabs__panel" id="tabB1panel">
                <div class="c-row c-row--lg">
                    <!-- 결합내역 조회 팝업 -->
                    <div id="combinHistory">
                        <div class="">
                            <div class="combin-history__tit">
                                <h3>결합 중인 회선</h3>
                                <button id="btnPrint" type="button" style="display: none">결합상품이용확인서</button>
                            </div>
                            <div class="combin-history__myinfo c-table">
                                <table>
                                    <caption>조회회선, 무료결합데이터를 포함한 결합 중인 회선 정보</caption>
                                    <colgroup>
                                        <col style="width: 8.3125rem">
                                        <col>
                                        <col style="width: 8.3125rem">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th scope="row">조회 회선</th>
                                        <td>
                                            <p class="_ctn"><!--010-12**-*123--></p>
                                            <p class="rate-info _rate"><!--데이터 맘껏 15GB+/300분(기프티쇼 5000P)--></p>
                                        </td>
                                        <th scope="row">무료결합데이터</th>
                                        <td class="progress-info _serviceNm" >-</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div id="divCombinHistory" class="combin-history__list c-table" style="display: none">
                                <table>
                                    <caption>kt M모바일, KT 인터넷, KT 모바일을 포함한 결합 중인 회선 정보</caption>
                                    <colgroup>
                                        <col style="width: 8.3125rem">
                                        <col>
                                        <col style="width: 8.3125rem">
                                        <col>
                                    </colgroup>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                            <div id="divCombinHistory2" class="combin-history__progress" style="display: none">
                                <h3 class="">결합 진행 현황<span class="c-form__sub">심사가 완료되면 상단의 결합중인 회선에 결합 내역이 노출됩니다.</span></h3>
                                <div class="combin-history__proginfo c-table">
                                    <table>
                                        <caption>결합 대상 회선, 진행현황을 포함한 결합 진행 현황 정보</caption>
                                        <colgroup>
                                            <col style="width: 8.3125rem">
                                            <col>
                                            <col style="width: 8.3125rem">
                                            <col>
                                        </colgroup>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div id="divCombinHistoryNodata" class="combin-nodata " style="display: none">
                                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                <p>현재 결합 중인 회선이 없습니다.</p>
                            </div>
                            <div class="c-notice-wrap">
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
                </div>
            </div>

            <div class="c-tabs__panel" id="tabB2panel">
                <div class="c-row c-row--lg" id="tabB2Body">

                </div>
            </div>

            <div class="c-tabs__panel" id="tabB3panel">
                <div class="c-row c-row--lg">
                    <!-- 결합내역 조회 팝업 -->
                    <div id="combinHistory2">
                        <div class="">
                            <div class="combin-history__tit">
                                <h3>결합 중인 회선</h3>
                                <button id="btnPrint2" type="button" style="display: none">결합상품이용확인서</button>
                            </div>
                            <div class="combin-history__myinfo c-table">
                                <table>
                                    <caption>조회회선, 무료결합데이터를 포함한 결합 중인 회선 정보</caption>
                                    <colgroup>
                                        <col style="width: 8.3125rem">
                                        <col>
                                        <col style="width: 8.3125rem">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th scope="row">조회 회선</th>
                                        <td>
                                            <p class="_ctn"><!--010-12**-*123--></p>
                                            <p class="rate-info _rate"><!--데이터 맘껏 15GB+/300분(기프티쇼 5000P)--></p>
                                        </td>
                                        <th scope="row">무료결합데이터</th>
                                        <td class="progress-info _serviceNm" >-</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div id="divCombinHistoryData" class="combin-nodata u-co-blue" style="display: none">
                                <p>해당 회선은 현재 ‘아무나 결합 중’입니다.</p>
                            </div>
                            <div id="divCombinHistoryNodata2" class="combin-nodata " style="display: none">
                                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                <p>현재 결합 중인 회선이 없습니다.</p>
                            </div>
                            <div class="c-notice-wrap">
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
                </div>
            </div>

			<!-- 아무나 SOLO -->
            <div class="c-tabs__panel" id="tabB4panel">
                <div class="c-row c-row--lg">
                    <div >
                        <div >
                            <div class="combin-history__tit _soloInfo" style="display: none">
                                <h3>결합 중인 회선</h3>
                            </div>
                            <div  class="combin-history__myinfo c-table _soloInfo" style="display: none">
                                <table>
                                    <caption>조회회선, 무료결합데이터를 포함한 결합 중인 회선 정보</caption>
                                    <colgroup>
                                        <col style="width: 8.3125rem">
                                        <col>
                                        <col style="width: 8.3125rem">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th scope="row">조회 회선</th>
                                        <td>
                                            <p class="_ctn"></p>
                                            <p class="rate-info _rate"></p>
                                        </td>
                                        <th scope="row">무료결합데이터</th>
                                        <td id="tdServiceNm" class="progress-info" >-</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                            <div id="divSoloNone" class="combin-nodata " style="display: none">
                                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                <p>현재 결합 중인 회선이 없습니다.</p>
                                <div class="c-button-wrap u-mt--48">
					                <a class="c-button c-button--lg c-button--primary c-button--w460" href="/content/combineSelf.do" title="결합 신청 안내 페이지 바로가기">결합 신청 안내</a>
					            </div>
                            </div>
                            <div class="c-notice-wrap">
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
        </div>

        <!--결합상품이용계약서 인쇄 영역(아무나 가족결합+)-->
        <div class="c-modal c-modal--combine-print" id="combinePrint" role="dialog" aria-labelledby="combinePrintTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="combinePrintTitle">결합 상품 이용 확인서</h2>
                        <button class="c-button no-print" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body combine-print">
                        <div class="certi-box">
                            <div class="c-button-wrap c-button-wrap--right print-btn">
                                <button class="c-button c-button--sm c-button--white c-button-round--sm">
                                    <i class="c-icon c-icon--print" aria-hidden="true"></i>
                                    <span>인쇄</span>
                                </button>
                            </div>
                            <div class="c-table u-mt--16">
                                <table>
                                    <caption>고객명, 휴대폰번호/휴대폰모델명을 포함한 고객정보</caption>
                                    <colgroup>
                                        <col style="width: 8.5rem">
                                        <col>
                                        <col style="width: 8.5rem">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th scope="row">고객명</th>
                                        <td class="u-ta-left _userNm"><!-- 홍길동 --></td>
                                        <th scope="row">연락처</th>
                                        <td class="u-ta-left _ctn"><!-- 010-222-3333 --></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">요금제명</th>
                                        <td class="u-ta-left _rate" ><!-- 데이터 맘껏 안심 1GB+/100분--></td>
                                        <th scope="row">무료결합데이터</th>
                                        <td class="u-ta-left _serviceNm" >-</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h3>계약내역</h3>
                            <div class="combine-info-list c-table">
                                <table>
                                    <caption>구분, 상품명, 안내사항, 요금제, 유료부가서비스, 기본제공 부가서비스, 납부방법, 요금명세서를 포함한 모바일 가입정보</caption>
                                    <colgroup>
                                        <col>
                                        <col >
                                        <col style="width: 7.625rem">
                                        <col>
                                        <col style="width: 6.625rem">
                                    </colgroup>
                                    <thead>
                                    <th scope="col">통신사</th>
                                    <th scope="col">상품유형</th>
                                    <th scope="col">ID/전화번호</th>
                                    <th scope="col">약정기간</th>
                                    <th scope="col">만료예정일</th>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                                <ul class="combine-notice__bullet u-mt--30">
                                    <li>이용 확인서의 계약 내용이 본인이 신청한 내용과 불일치 시 고객센터(114)통해 연락 주시기 바랍니다.</li>
                                    <li>개인정보보호를 위해 고객의 정보는 일부 마스킹처리 됩니다.</li>
                                    <li>본 결합 상품 이용 확인서는 현재 고객님의 결합 현황을 토대로 작성된 것으로 최초 결합 신청 당시의 정보와 상이할 수 있습니다.</li>
                                </ul>
                            </div>
                        </div>
                        <div class="certi-box--bottom u-ta-center">
                            <p><fmt:formatDate value="${nmcp:getDateToCurrent(0)}"  pattern="yyyy년  MM월  dd일" /><span>신청인 <em class="_userNm"><!-- 김엠모 --></em></span></p>
                            <b>주식회사 케이티엠모바일</b>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--결합상품이용계약서 인쇄 영역(아무나결합)-->
        <div class="c-modal c-modal--combine-print" id="combinePrint2" role="dialog" aria-labelledby="combinePrintTitle2">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="combinePrintTitle2">결합 상품 이용 확인서</h2>
                        <button class="c-button no-print" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body combine-print">
                        <div class="certi-box">
                            <div class="c-button-wrap c-button-wrap--right print-btn2">
                                <button class="c-button c-button--sm c-button--white c-button-round--sm">
                                    <i class="c-icon c-icon--print" aria-hidden="true"></i>
                                    <span>인쇄</span>
                                </button>
                            </div>
                            <div class="c-table u-mt--16">
                                <table>
                                    <caption>고객명, 휴대폰번호/휴대폰모델명을 포함한 고객정보</caption>
                                    <colgroup>
                                        <col style="width: 8.5rem">
                                        <col>
                                        <col style="width: 8.5rem">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th scope="row">고객명</th>
                                        <td class="u-ta-left _userNm"><!-- 홍길동 --></td>
                                        <th scope="row">연락처</th>
                                        <td class="u-ta-left _ctn"><!-- 010-222-3333 --></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">요금제명</th>
                                        <td class="u-ta-left _rate" ><!-- 데이터 맘껏 안심 1GB+/100분--></td>
                                        <th scope="row">무료결합데이터</th>
                                        <td class="u-ta-left _serviceNm" >-</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h3>계약내역</h3>
                            <div id="divCombinHistoryData2" class="combin-nodata u-co-blue">
                                <p>해당 회선은 현재 ‘아무나 결합 중’입니다.</p>
                            </div>
                            <ul class="combine-notice__bullet u-mt--30">
                                <li>이용 확인서의 계약 내용이 본인이 신청한 내용과 불일치 시 고객센터(114)통해 연락 주시기 바랍니다.</li>
                                <li>개인정보보호를 위해 고객의 정보는 일부 마스킹처리 됩니다.</li>
                                <li>본 결합 상품 이용 확인서는 현재 고객님의 결합 현황을 토대로 작성된 것으로 최초 결합 신청 당시의 정보와 상이할 수 있습니다.</li>
                            </ul>
                        </div>
                        <div class="certi-box--bottom u-ta-center">
                            <p><fmt:formatDate value="${nmcp:getDateToCurrent(0)}"  pattern="yyyy년  MM월  dd일" /><span>신청인 <em class="_userNm"><!-- 김엠모 --></em></span></p>
                            <b>주식회사 케이티엠모바일</b>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
