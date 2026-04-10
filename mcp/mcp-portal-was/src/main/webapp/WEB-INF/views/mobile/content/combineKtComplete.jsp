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

    <t:putAttribute name="titleAttr">아무나 가족 결합+</t:putAttribute>


    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">



            <div class="ly-page-sticky">
                <h2 class="ly-page__head">아무나 가족 결합+<button class="header-clone__gnb"></button>
                </h2>
            </div>


            <c:choose>
                <c:when test="${reqCombine.combTgtTypeCd eq '01'}">
                    <!-- KT유무선 결합 완료(가족결합 제외) 노출 영역 -->
                    <div class="combine-complete" >
                        <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                        <p class="combine-complete__txt">
                            <span class="u-co-mint">결합이 완료 되었습니다.</span><br />kt M모바일 회선에 무료결합데이터(${reqCombine.mRateAdsvcNm})이 제공되었습니다.
                        </p>
                        <p class="combine-complete__subtxt">※ 결합 완료 시 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.</p>
                        <p class="combine-complete__subtxt">※ 요금제 변경 및 해지 시 고객센터를 통해 신청바랍니다.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- KT유무선 결합(가족결합) 신청완료 노출 영역 -->
                    <div  >
                        <div class="combine-complete">
                            <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                            <p class="combine-complete__txt">
                                <span class="u-co-mint">결합 신청이 완료 되었습니다.</span><br/>가족관계검증을 위해 관련 서류를 보내주시기 바랍니다.<br/>서류검증 후 결합처리가 완료됩니다.
                            </p>
                            <p class="combine-complete__subtxt">※결합 신청 후 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.</p>
                        </div>
                        <div class="combine-checklist u-mt--48">
                            <p class="combine-checklist__tit u-co-red">
                                <i class="c-icon c-icon--bang--red" aria-hidden="true"></i>
                                <span>주의사항</span>
                            </p>
                            <ul class="combine-checklist__item">
                                <li>해당 페이지에서 결합 신청완료 한 후, 아래 서류를 신청 후 3일 이내에 이메일 (ktm.vit@kt.com) 이나 팩스(070-4275-2483)로 전달 주셔야 합니다.</li>
                                <li>결합 신청일 기준 5일이내로 서류가 도착하지 않는 경우 해당 결합 신청이 취소되며, 취소 후에 결합을 원하실 경우  재신청 바랍니다.</li>
                            </ul>
                            <p class="combine-checklist__subtit u-co-red">
                                <span>※가족명의 결합 요청 시</span>
                            </p>
                            <ul class="combine-checklist__item no-bullet">
                                <li><b>① 주민등록등본, 건강보험증, 가족관계증명서, 혼인관계증명서, 입양관계증명서, 친양자입양관계증명서 중 택 1</b></li>
                                <li><b>② 결합신청인, 결합대상인의 신분증(주민등록증, 여권, 운전면허증, 공무원증 중 택 1)</b></li>
                                <li>
                                    ※ 주민번호 뒷 번호가 보이지 않게 마스킹 처리 바랍니다. 미처리 시 해당 자료는 폐기됩니다.<br />
                                    ※ 미성년자의 경우 법정대리인 신분증으로 제출 바랍니다.
                                </li>
                            </ul>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>






            <div class="combine-complete__list" >
                <h4 class="c-heading c-heading--type5">신청 내역</h4>
                <hr class="c-hr c-hr--type2">
                <h3 class="u-mt--0">내 정보</h3>
                <div class="c-table">
                    <table>
                        <caption>전화번호, 무료결합데이터, 요금제 정보를 포함한 결합내역 정보</caption>
                        <colgroup>
                            <col style="width: 33%">
                            <col>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row">휴대폰 번호</th>
                            <td class="_ctn" >${reqCombine.mCtnMaker}</td>
                        </tr>
                        <tr>
                            <th scope="row">요금제</th>
                            <td class="_rate">${reqCombine.mRateNm}</td>
                        </tr>
                        <tr>
                            <th scope="row">무료결합데이터</th>
                            <td class="u-ta-left progress-info _serviceNm" >${reqCombine.mRateAdsvcNm}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <h3>결합 회선 정보</h3>
                <div class="c-table">
                    <table>
                        <caption>요금제 정보를 포함한 결합내역 정보</caption>
                        <colgroup>
                            <col style="width: 33%">
                            <col>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row">KT</th>
                            <td id="tdKtInfo">${reqCombine.combSvcNoMaker}</td>
                        </tr>
                        </tbody>
                    </table>
                    <p class="combine-complete__listsubtxt">※ 해당 KT 상품의 상세 내용 확인은 KT 고객센터로 문의 해주시기 바랍니다.</p>
                </div>
            </div>
            <div class="combine-complete__confirm" >
                <button type="button" class="c-button" onclick="location.href='/content/combineKt.do' ">확인</button>
            </div>







        </div>



    </t:putAttribute>


    <t:putAttribute name="popLayerAttr">


        <!-- 결합내역 조회 팝업 -->
        <div class="c-modal" id="combinHistory" role="dialog" tabindex="-1" aria-describedby="combinHistoryTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="combinHistoryTitle">결합내역 조회</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
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
                                    <td class="progress-info" id="pServiceNm">-</td>
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
                                <li>결합된 회선의 요금제 변경 및 해지 시 kt M모바일 고객은 kt M모바일 고객센터(114, 1899-5000), KT 고객은 KT 고객센터를 통해 가능합니다.</li>
                            </ul>
                        </div>


                    </div>
                </div>
            </div>
        </div>



        <!-- 정보제공 동의 팝업 -->
        <div class="c-modal" id="combinInfoAgree" role="dialog" tabindex="-1" aria-describedby="combinInfoAgreeTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="combinInfoAgreeTitle">정보제공동의</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">

                        <div class="combin-Info-Agree">
                            <h3>정보제공 동의란?</h3>
                            <p>KT 상품을 가입한 고객이 KT 고객센터를 통해 자신의 정보가 kt M모바일에 제공되도록 <span class="u-co-mint">정보제공 동의</span>를 하는 절차입니다.</p>
                            <div class="combin-Info-Agree__wrap">
                                <ul class="process-wrap">
                                    <li class="process-list">
                                        <span class="process-list__step">1</span>
                                        <strong>KT 고객센터 전화하기</strong>
                                        <p>KT 모바일 사용자 : 114번</p>
                                        <p>KT 인터넷 가입자 : 100번</p>
                                    </li>
                                    <li class="process-list">
                                        <span class="process-list__step">2</span>
                                        <strong>상담원 연결</strong>
                                    </li>
                                    <li class="process-list">
                                        <span class="process-list__step">3</span>
                                        <strong>kt M모바일과 결합 위해<br/>「MVNO정보제공동의」요청</strong>
                                    </li>
                                    <li class="process-list">
                                        <span class="process-list__step">4</span>
                                        <strong>본인인증</strong>
                                    </li>
                                    <li class="process-list">
                                        <span class="process-list__step">5</span>
                                        <strong>완료</strong>
                                    </li>
                                </ul>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>





        <!-- 서비스 ID 팝업 -->
        <div class="c-modal" id="serviceInfo" role="dialog" tabindex="-1" aria-describedby="serviceInfoTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="serviceInfo">인터넷 서비스 ID</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <p class="c-text c-text--type3">
                            인터넷 서비스 ID란  KT 유선 서비스에 가입된 고객에게 부여되며 마이케이티APP 및 KT 고객센터에서 문의 후 입력해 주시기 바랍니다.
                        </p>
                    </div>
                    <div class="c-modal__footer">
                        <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>


</t:insertDefinition>