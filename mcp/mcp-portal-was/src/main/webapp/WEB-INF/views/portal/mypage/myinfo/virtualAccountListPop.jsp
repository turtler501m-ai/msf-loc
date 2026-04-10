<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<div class="c-modal__content">
    <div class="c-modal__header">
        <h2 class="c-modal__title" id="modalJoinInfoTitle">가상계좌 조회</h2>
        <button class="c-button no-print" data-dialog-close="">
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>
    <div class="c-modal__body">
        <div class="certi-box">
            <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--0">가상계좌</h3>
            <div class="c-table u-mt--32 u-mb--16">
                <table>
                    <colgroup>
                        <col style="width: 30%">
                        <col style="width: 30%">
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <th>납부자<br/>(예금주)</th>
                            <th>금융기관</th>
                            <th>계좌번호</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${!isSuccess}">
                                <tr>
                                    <td colspan="3">
                                        가상계좌 조회에 실패했습니다.
                                    </td>
                                </tr>
                            </c:when>
                            <c:when test="${empty virtualAccountList}">
                                <tr>
                                    <td colspan="3">
                                        가상계좌가 존재하지 않습니다.
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="virtualAccount" items="${virtualAccountList}">
                                    <tr>
                                        <td>${userName}</td>
                                        <td>${virtualAccount.bankNm}</td>
                                        <td>${virtualAccount.virtlBnkacnNo}</td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
            <ul class="c-text-list c-bullet c-bullet--dot">
                <li class="c-text-list__item">실제 은행 계좌와 동일한 역할을 하는 가상계좌로 지로납부방법으로 등록되어 있으신 고객님께서는 가상계좌가 자동 부여되어 지로명세서에 표기됩니다.</li>
                <li class="c-text-list__item">해당 계좌로 통신비를 납부할 수 있으며, 납부방법이 자동납부(은행 또는 신용카드)로 등록되어 있으신 경우 납부 기준일에 따라 이중납부가 될 수 있으니 유의하시기 바랍니다.</li>
                <li class="c-text-list__item">당월 청구금(미납 포함) 이하의 금액만 입금 가능합니다.</li>
                <li class="c-text-list__item">가상계좌를 신규로 생성하고 싶으실 경우 고객센터로 문의를 부탁드립니다.</li>
                <li class="c-text-list__item">가상계좌 개설 가능 은행
                    <ul class="c-text-list c-bullet c-bullet--hyphen">
                        <li class="c-text-list__item">케이뱅크, 농협은행, 신한은행, 국민은행, 카카오뱅크, 토스뱅크, 우리은행, KEB 하나은행, 기업은행, 우체국, 부산은행, 경남은행, 광주은행, 전북은행, 아이엠뱅크(구. 대구), SC제일은행, 한국씨티은행</li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>