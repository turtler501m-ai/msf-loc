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

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/validator.js?version=22.08.18"></script>
        <script type="text/javascript" src="/resources/js/portal/content/combineWireless.js?version=23.12.12"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">



        <input type="hidden" id="prcsMdlInd" name="prcsMdlInd" value="<fmt:formatDate value="${nmcp:getDateToCurrent(0)}" pattern="yyMMddHHmmss" />" >

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">아무나 결합</h2>
            </div>
            <div class="ktmm-combine-banner _regForm">
                <div class="ktmm-combine-banner__wrap">
                    <h3>아무나 결합이란?</h3>
                    <p>kt M모바일 <b>고객이라면 누구나</b> 결합하여 무료 결합 데이터 혜택을 제공받는 서비스입니다.</p>
                    <c:set var="dataInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />
                    <c:forEach var="item" items="${dataInfo}" varStatus="status">
                        <c:if test = "${Constants.COMB_LINK_CD eq item.dtlCd}">
                            <button type="button" id="btnBanner" onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">자세히보기</button>
                            <img src="/resources/images/portal/product/ktmm_combine_banner.png" alt="" aria-hidden="true">
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="ktmm-combine-content _regForm">

                <!-- 로그인 체크 영역 -->
                <div id="divStep1" class="user-confirm "  <c:if test="${nmcp:hasLoginUserSessionBean()}">style="display: none"</c:if>>
                    <span class="user-confirm__tit"><em>1</em>다이렉트 몰 아이디가 있으신 경우</span>
                    <div class="btn-login">
                        <a id="bntLogin" href="javascript:void(0);" >로그인하고 시작하기</a>
                    </div>
                    <span class="user-confirm__tit"><em>2</em>다이렉트 몰 아이디가 없으신 경우</span>

                    <input type="hidden" name="ncType" id="ncType" value="0"/>
                    <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>

                    <div class="btn-usercheck">
                        <button type="button" class="is-disabled" href="javascript:void(0);"  id="btnOk" >가입정보 확인</button>
                    </div>
                </div>

                <!-- 결합 정보 영역 -->
                <div id="divStep2" class="combine-info" <c:if test="${!nmcp:hasLoginUserSessionBean()}">style="display: none"</c:if>>
                    <c:if test="${empty maskingSession}">
			      	  	<div class="masking-badge-wrap">
					        <div class="masking-badge">
					        	<a class="masking-badge__button" href="/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
					        		<i class="masking-badge__icon" aria-hidden="true"></i>
					 				<p>가려진(*)<br />정보보기</p>
					       		</a>
					        </div>
				        </div>
			        </c:if>
                    <div class="c-form-wrap">
                        <div class="c-form-group" role="group" aria-labelledby="combineMyinfo">
                            <div class="c-group--head">
                                <h4 id="combineMyinfo">내 정보</h4>
                                <button id="btnCombiSvcList" class="c-button c-button--underline" type="button" >결합내역 조회</button>
                            </div>
                            <div class="combine-myinfo">
                                <div class="c-form-row c-col2">
                                    <div class="c-form-field">
                                        <div class="c-form__select has-value">
                                            <select class="c-select" id="ncn" name="ncn">
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
                                            </select>
                                            <label class="c-form__label" for="ncn">휴대폰 번호</label>
                                        </div>
                                    </div>
                                    <div class="c-form-field">
                                        <div class="c-form__input has-value">
                                            <input type="text" class="c-input" id="rateNm" name="rateNm"  readonly>
                                            <label class="c-form__label" for="rateNm">요금제</label>
                                        </div>
                                    </div>
                                    <button type="button" id="btnCheckCombine" class="combine-check">조회</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 결합 여부 출력 영역 -->
                    <div class="combine-result" style="display: none">

                    </div>

                    <!-- 결합 가능할때 출력 영역 -->
                    <div id="divChild" style="display: none">
                        <div class="c-form-wrap">
                            <div class="c-form-group" role="group" aria-labelledby="formGroupB">
                                <h4 class="c-group--head" id="formGroupB">결합 대상 회선 정보</h4>
                                <div class="c-form-row c-col2">
                                    <div class="c-form-field">
                                        <div class="c-form__input">
                                            <input type="text" class="c-input numOnly" id="childCtn"  maxlength="11" placeholder="휴대폰 번호" >
                                            <label class="c-form__label" for="childCtn">휴대폰 번호</label>
                                            <button class="c-button c-button--sm c-button--underline" id="btnChildCertify">인증번호 받기</button>
                                        </div>
                                        <p class="c-form__text" id="childCtnChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
                                    </div>

                                    <div class="c-form-field">
                                        <div class="c-form__input">
                                            <input type="number" class="c-input" id="authNum" name="authNum"  maxlength="6" placeholder="인증번호" >
                                            <label class="c-form__label" for="authNum">인증번호 입력</label>
                                            <button id="bthAuthSms" class="c-button c-button--sm c-button--underline">인증번호 확인</button>
                                        </div>
                                        <!-- case1 : 인증번호 확인 전-->
                                        <p class="c-form__text" id="countdown2"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후 -->
                                        <p class="c-form__text" id="countdownTime2" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer2"></span>
                                            <button class="c-button c-button--xsm c-button--underline u-ml--8 u-co-mint" id="btnMoreTime">시간연장</button>
                                        </p>
                                        <!-- case3 : 인증시간 초과-->
                                        <p class="c-form__text" id="timeover2" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
                                        <input type="hidden"    id="timeOverYn2"  name="timeOverYn2" value="N"/>
                                        <input id="isSendSms"   type="hidden"     value="" />
                                        <input id="isVerify"    type="hidden"     value="" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <h3 class="c-form__title u-mt--48">약관동의</h3>
                        <div class="c-agree">
                            <div class="c-agree__item">
                                <button type="button" class="c-button c-button--xsm" data-dialog-trigger="#combinAgree">
                                    <span class="c-hidden">결합을 위한 필수 확인 사항 동의 상세보기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                </button>
                                <div class="c-agree__inner">
                                    <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">
                                    <label class="c-label" for="chkAgree">결합을 위한 필수 확인 사항 동의 (필수)</label>
                                </div>
                            </div>
                        </div>
                        <div class="c-agree u-mt--8">
                            <div class="c-agree__item">
                                <button type="button" class="c-button c-button--xsm" data-dialog-trigger="#combinAgree2">
                                    <span class="c-hidden">결합을 위한 필수 개인정보 수집 상세보기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                </button>
                                <div class="c-agree__inner">
                                    <input class="c-checkbox" id="chkAgree2" type="checkbox" name="chkAgree2">
                                    <label class="c-label" for="chkAgree2">개인정보 수집 및 이용에 관한 동의 (필수)</label>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 결합 신청 버튼 영역 -->
                    <div class="combine-sign" style="display: none">
                        <button id="btnRegCombin" type="button" class="is-disabled">결합 신청</button>
                    </div>
                </div>

                <!-- 알려드립니다 영역 -->
                <div class="combine-notice">

                </div>
            </div>

            <div class="combine-complete _completeForm" style="display: none">
                <div class="combine-complete__wrap">
                    <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                    <p class="combine-complete__txt">
                        결합이 완료 되었습니다.<br/>kt M모바일 회선에 무료결합데이터(<span class="_tdServiceNm">-</span>)이 제공되었습니다.
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
                                <td class="_ctn"></td>
                                <th scope="row">무료결합데이터</th>
                                <td class="_tdServiceNm" >-</td>
                            </tr>
                            <tr>
                                <th scope="row">요금제</th>
                                <td class="_rate" colspan="3"></td>
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
                                <td id="tdComChildCtn"><!-- 010-27**-*341 --></td>
                                <th scope="row">무료결합데이터</th>
                                <td id="tdChildServiceNm"><!-- 매월 50GB 제공 --></td>
                            </tr>
                            <tr>
                                <th scope="row">요금제</th>
                                <td colspan="3" id="tdCoChildmRate"><!-- DATA 함께쓰기 120분 --></td>
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

    <t:putAttribute name="popLayerAttr">

        <!-- 약관동의 팝업 -->
        <div class="c-modal c-modal--xlg" id="combinAgree" role="dialog" aria-labelledby="combinAgreeTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="combinAgreeTitle">결합을 위한 필수 확인 사항 동의 (필수)</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="one-source">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item">
                                    아래 결합 서비스의 주의사항을 확인하였습니다.
                                    <ul class="c-text-list c-bullet c-bullet--hyphen">
                                        <li class="c-text-list__item">아무나 결합은 최소 2회선 이상부터 여러 회선과 회선 수 제한 없이 결합이 가능합니다.</li>
                                        <li class="c-text-list__item">아무나 결합은 kt M모바일 회선(동일명의, 타인, 가족)간 결합이 가능합니다.</li>
                                        <li class="c-text-list__item">아무나 결합으로 결합한 후 본인 또는 결합 구성원 누구나 다른 회선과 추가 결합을 할 수 있으며, 추가된 회선도 결합 구성원에 포함됩니다.<br />(단, 미결합 상태의 회선만 추가 가능)<br />(예시) A회선과 B회선 결합 후 A회선이 C회선과 결합, B회선이 D회선과 결합 할 경우 A, B, C, D 회선이 결합 구성원이 됩니다.</li>
                                        <li class="c-text-list__item">여러 회선 결합 상태일 경우 일부 결합 회선이 해지하더라도 유지 중인 결합 구성원이 있다면 결합이 유지되며 결합 혜택이 제공됩니다.<br />(예시) A, B, C, D 결합 상태 중 A회선이 해지 하더라도 나머지 회선 B, C, D 결합은 유지되어 결합 혜택이 제공됩니다.</li>
                                        <li class="c-text-list__item">아무나 결합 중 일부 회선의 결합 해지 및 회선 해지 등의 사유로 결합 구성(최소 2회선)이 불가능 할 경우 직권으로 결합 서비스가 해지 되며, 혜택도 즉시 중단됩니다.<br />(예시) A회선과 B회선 결합 후 A회선 해지 했을 경우 B와 결합 서비스는 해지 되고 결합 혜택 제공이 중단됩니다.</li>
                                        <li class="c-text-list__item">결합한 회선의 요금제 변경 시, 별도의 결합 해지 없이도 마이페이지에서 변경이 가능합니다.</li>
                                    </ul>
                                </li>
                                <li class="c-text-list__item">kt M모바일 회선간 결합시 약정기간은 없으며, 해지 시 위약금도 없습니다.</li>
                                <li class="c-text-list__item">결합 해지는 고객센터(114)를 통해 가능합니다.</li>
                                <li class="c-text-list__item">결합신청 동의 내용을 바탕으로 결합이용 확인서가 생성 됩니다.</li>
                                <li class="c-text-list__item">신규 회선의 추가 결합 시, 결합 구성원 중 일부의 결합 동의 후에 결합이 이뤄질 수 있습니다.</li>
                            </ul>
                            <p class="u-fw--bold u-mt--12">※고객님은 해당 사항에 동의 거부하실 수 있으며, 동의 거부 시 서비스 이용이 불가합니다.</p>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" id="btnAgreeClose">동의 후 닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="c-modal c-modal--xlg" id="combinAgree2" role="dialog" aria-labelledby="combinAgreeTitle2">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="combinAgreeTitle2">개인정보 수집 및 이용에 관한동의 (필수)</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="one-source">
                            <p>결합 서비스 가입을 위해 고객님께서 개인정보 수집 및 이용약관에 동의하셔야 서비스 이용이 가능합니다.</p>
                            <ul class="c-text-list c-bullet c-bullet--number u-mt--16">
                                <li class="c-text-list__item">수집 및 이용 목적: 결합 서비스 가입 및 혜택 제공</li>
                                <li class="c-text-list__item">수집 항목: 이름, 전화번호, 요금제명, 생년월일, 성별, 부가서비스명, 부가서비스코드</li>
                                <li class="c-text-list__item">이용 및 보유기간: 결합서비스 가입 기간</li>
                            </ul>
                            <p class="u-fw--bold u-mt--12">※고객님은 해당 사항에 동의 거부하실 수 있으며, 동의 거부 시 서비스 이용이 불가합니다.</p>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" id="btnAgreeClose2">동의 후 닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 결합내역 조회 팝업 -->
        <div class="c-modal c-modal--lg850" id="combinHistory" role="dialog" aria-labelledby="combinHistoryTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <div class="c-modal__title" id="combinHistoryTitle">결합내역 조회</div>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
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
                        <%-- <div id="divCombinHistory" class="combin-history__list c-table" style="display: none">
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
                        </div> --%>
                        <div id="divCombinHistoryData" class="combin-nodata u-co-blue" style="display: none">
                            <p>해당 회선은 현재 ‘아무나 결합 중’입니다.</p>
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

        <!--결합상품이용계약서 인쇄 영역-->
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
<%--                                 <table>
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
                                </table> --%>
                                <div id="divCombinHistoryData2" class="combin-nodata u-co-blue" style="display: none">
                                    <p>해당 회선은 현재 ‘아무나 결합 중’입니다.</p>
                                </div>
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

    </t:putAttribute>

</t:insertDefinition>

