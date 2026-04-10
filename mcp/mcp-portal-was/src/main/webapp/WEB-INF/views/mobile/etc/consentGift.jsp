<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>


<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/etc/consentGift.js?version=24-02-06"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/popup/niceAuth.js?version=24.05.21"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="diffInDays" value="${diffInDays}" />
        <input type="hidden" id="addressYn" value="${consentDto.addressYn}">
        <input type="hidden" id="taxNo" value="${consentDto.taxNo}">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">사은품 지급 안내<button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="c-item c-item--type1 product-info-box u-pa--16">
                <p class="c-bullet c-bullet--dot u-fs-14 u-mt--0">사은품 수령을 위해서는 제세공과금 신고를 위한 개인정보 수집 동의 및 본인확인이 필요합니다. <b class="u-co-black">내용 확인 후, <span class="u-co-red">5일 안에 입력해주시기 바랍니다.</span></b></p>
                <p class="c-bullet c-bullet--fyr u-fs-14 u-fw--bold">개인정보 입력 및 본인인증 시 <span class="u-co-red">문자 수신 고객 본인의 정보</span>가 반드시 입력되어야 합니다.<br/>법정대리인의 정보로 입력 시 인증이 불가하니 참고 부탁드립니다.</p>
            </div>
            <p class="c-bullet c-bullet--dot u-co-gray">제세공과금 신고절차와 비용은 kt M모바일이 진행 및 부담합니다.</p>
            <div class="c-agree" id="divAgreeArea">
                <div class="c-agree__item">
                    <input class="c-checkbox" id="chkA1" type="checkbox" name="chkB">
                    <label class="c-label" for="chkA1">개인정보 수집/이용동의<span class="u-co-gray">(필수)</span></label>
                    <button type="button" class="c-button c-button--xsm" data-dialog-trigger="#personal-info-dialog">
                        <span class="c-hidden">상세보기</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                </div>
            </div>
            <div class="c-form">
                <span class="c-form__title">가입정보 확인</span>
                <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
            <c:if test="${consentDto.addressYn == 'Y'}">
                <div class="c-form">
                    <span class="c-form__title">주소</span>
                    <div class="c-form__group" role="group" aria-labelledby="inpG">
                        <div class="c-input-wrap">
                            <input class="c-input" type="text" placeholder="우편번호" title="우편번호 입력" readonly id="cstmrPost">
                            <button class="c-button c-button--sm c-button--underline" onclick="openPage('pullPopup', '/m/addPopup.do');">우편번호 찾기</button>
                        </div>
                        <input class="c-input" type="text" placeholder="주소 입력" title="주소 입력" value="" readonly id="cstmrAddr">
                        <input class="c-input" type="text" placeholder="상세 주소 입력" title="상세 주소 입력" value="" readonly id="cstmrAddrDtl">
                    </div>
                    <p class="c-bullet c-bullet--dot u-co-gray">현물 사은품의 경우 입력하신 주소로 발송됩니다.</p>
                    <p class="c-bullet c-bullet--dot u-co-gray">제출된 주소는 수정이 불가함으로 신중히 작성 부탁드립니다.</p>
                </div>
            </c:if>
                <div class="c-button-wrap">
                    <button class="c-button c-button--full c-button--primary" id="certBtn" href="javascript:;">작성완료 및 제출</button>
                </div>
            </div>
            <div class="c-notice-wrap">
        <hr>
        <h5 class="c-notice__title">
            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
            <span>알려드립니다</span>
        </h5>
        <ul class="c-notice__list">
            <li class="u-co-red u-fw--bold">문자 수신 후 5일 초과 시, 경품 당첨이 취소되니 반드시 기한 내에 작성 후 제출해 주시기 바랍니다.</li>
            <li>해당 개인정보는 동의자에 한해 수집되며, 문자발송일 기준 익월 말일 폐기됩니다.</li>
            <li>제세공과금 처리 내역은 국세청에서 기타 소득세 항목으로 추후 확인이 가능합니다.</li>
        </ul>
    </div>
   </div>
    <div class="c-modal" id="personal-info-dialog" role="dialog" tabindex="-1" aria-describedby="#personal-info-title">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
        <div class="c-modal__content">
            <div class="c-modal__header">
                <h1 class="c-modal__title" id="personal-info-title">개인정보 수집/이용동의</h1>
                <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                </button>
            </div>
            <div class="c-modal__body">
                <div class="one-source">
                    <div class="c-table c-table--x-scroll">
                        <table>
                            <caption>수집,이용 목적, 개인정보 항목, 보유기간 항목이 포함된 표</caption>
                            <colgroup>
                                <col style="width: 150px">
                                <col style="width: 200px">
                                <col style="width: 250px">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">수집,이용 목적</th>
                                    <th scope="col">개인정보 항목</th>
                                    <th scope="col">보유기간</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>경품 발송</td>
                                    <td>성명, 연락처, 주소(현물지급 시)</td>
                                    <td>문자발송일 기준 익월 말일</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <p class="c-bullet c-bullet--fyr">수집된 개인정보는 경품 발송에만 이용되며, 제공에 거부하실 수 있으나 이 경우 사은품 제공이 불가합니다.</p>
                    <h4 class="c-heading c-title--type3">(5만원 초과 경품인 경우) 기타 고지사항</h4>
                    <div class="c-table c-table--x-scroll u-mt--16">
                        <table>
                            <caption>수집,이용 목적, 개인정보 항목, 수집 근거 항목이 포함된 표</caption>
                            <colgroup>
                                <col style="width: 150px">
                                <col style="width: 200px">
                                <col style="width: 250px">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">수집,이용 목적</th>
                                    <th scope="col">개인정보 항목</th>
                                    <th scope="col">수집 근거</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>제세공과금 납부</td>
                                    <td>주민등록번호</td>
                                    <td class="u-ta-left">「소득세법」 제21조(기타소득), 제127조(원천징수의무), 제164조(지급명세서의 제출)</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="c-modal__footer">
                <button class="c-button c-button--full c-button--primary" onclick="agreeClose()" data-dialog-close>동의 후 닫기</button>

            </div>
        </div>
    </div>
</div>

    </t:putAttribute>
</t:insertDefinition>