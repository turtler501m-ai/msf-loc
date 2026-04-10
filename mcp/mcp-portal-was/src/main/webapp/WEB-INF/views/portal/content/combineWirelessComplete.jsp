<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">아무나 결합</t:putAttribute>



    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">아무나 결합</h2>
            </div>

            <div class="combine-complete " style="">
                <div class="combine-complete__wrap">
                    <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                    <p class="combine-complete__txt">
                        결합이 완료 되었습니다.<br/>kt M모바일 회선에 무료결합데이터(<span >${reqCombine.mRateAdsvcNm}</span>)이 제공되었습니다.
                    </p>
                    <p class="combine-complete__subtxt">
                        ※ 결합 완료 시 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.<br/>
                        결합 해지 시 고객센터(114)를 통해 신청바랍니다.
                    </p>
                </div>

                <div class="combine-complete__list">
                    <h3>결합 내역</h3>
                    <hr>
                    <h4>내 정보</h4>
                    <div class="c-table">
                        <table>
                            <caption>전화번호, 무료결합데이터, 요금제 정보를 포함한 결합내역 정보</caption>
                            <colgroup>
                                <col style="width: 10rem">
                                <col>
                                <col style="width: 10rem">
                                <col>
                            </colgroup>
                            <tbody>
                            <tr>
                                <th scope="row">전화번호</th>
                                <td class="_ctn">${reqCombine.mCtnMaker}</td>
                                <th scope="row">무료결합데이터</th>
                                <td class="_tdServiceNm" >${reqCombine.mRateAdsvcNm}</td>
                            </tr>
                            <tr>
                                <th scope="row">요금제</th>
                                <td class="_rate" colspan="3">${reqCombine.mRateNm}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <h4>결합 회선 정보</h4>
                    <div class="c-table">
                        <table>
                            <caption>전화번호, 무료결합데이터, 요금제 정보를 포함한 결합내역 정보</caption>
                            <colgroup>
                                <col style="width: 10rem">
                                <col>
                                <col style="width: 10rem">
                                <col>
                            </colgroup>
                            <tbody>
                            <tr>
                                <th scope="row">전화번호</th>
                                <td id="tdComChildCtn">${reqCombine.combSvcNoMaker}</td>
                                <th scope="row">무료결합데이터</th>
                                <td id="tdChildServiceNm">${reqCombine.combRateAdsvcNm}</td>
                            </tr>
                            <tr>
                                <th scope="row">요금제</th>
                                <td colspan="3" id="tdCoChildmRate">${reqCombine.combSocNm}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="combine-complete__confirm">
                    <button type="button" onclick="location.href='/content/combineWireless.do' " >확인</button>
                </div>

            </div>



        </div>



    </t:putAttribute>








</t:insertDefinition>

