<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
    uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">회원가입 이벤트</h2>
            </div>
            <div class="c-row c-row--lg">
                <h3 class="c-heading c-heading--fs20">이벤트 참여 결과</h3>
                <div class="c-table u-mt--16">
                    <table>
                        <caption>이벤트 참여 결과 내역 - 번호, 이벤트명, 참여일, 당첨여부 상세, 결과발표 정보표</caption>
                        <colgroup>
                            <col style="width: 8%">
                            <col style="width: 35%">
                            <col style="width: 15%">
                            <col>
                            <col style="width: 15%">
                        </colgroup>
                        <thead>
                            <tr>
                                <th scope="col">번호</th>
                                <th scope="col">이벤트명</th>
                                <th scope="col">참여일</th>
                                <th scope="col">당첨여부 상세</th>
                                <th scope="col">결과발표</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                        <c:when test="${!empty myPromotionList}">
                            <c:forEach var="userPromotionItem" items="${myPromotionList}" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td>${userPromotionItem.dtlCdNm}</td>
                                    <td><fmt:formatDate value="${userPromotionItem.cretDt}" pattern="yyyy.MM.dd" /></td>
                                    <c:choose>
                                        <c:when test="${empty userPromotionItem.rsltAndt}">
                                            <td>-</td>
                                            <td>-</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${userPromotionItem.prizenm}</td>
                                            <td><fmt:formatDate value="${userPromotionItem.rsltAndt}" pattern="yyyy.MM.dd" /></td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5"><p class="nodata">조회결과가 없습니다.</p></td>
                            </tr>
                        </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </div>
                <ul class="c-text-list c-bullet c-bullet--fyr u-fs-14 u-mt--16">
                    <li class="c-text-list__item u-co-gray">이벤트에 따라 지급일정이 상이할 수 있습니다.</li>
                    <li class="c-text-list__item u-co-gray">이벤트 지급일정은 참여하신 이벤트의 유의사항 안내에 따릅니다.</li>
                </ul>
            </div>
        </div>
</t:putAttribute>
</t:insertDefinition>