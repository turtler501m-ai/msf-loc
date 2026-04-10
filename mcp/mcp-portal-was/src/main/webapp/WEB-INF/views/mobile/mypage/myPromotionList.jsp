<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                         회원가입 이벤트
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <h3 class="c-heading c-heading--type3 u-mt--20 u-mb--12">이벤트 참여 결과</h3>
            <ul class="c-text-list c-bullet c-bullet--fyr">
                <li class="c-text-list__item u-co-gray u-mt--0">이벤트에 따라 지급일정이 상이할 수 있습니다.</li>
                <li class="c-text-list__item u-co-gray">이벤트 지급일정은 참여하신 이벤트의 유의사항 안내에 따릅니다.</li>
            </ul>
            <c:choose>
                <c:when test="${!empty myPromotionList}">
                    <c:forEach var="userPromotionItem" items="${myPromotionList}">
                        <hr class="c-hr c-hr--type2 c-expand">
                        <h4 class="u-fs-18 u-fw--bold u-mb--6">${userPromotionItem.dtlCdNm}</h4>
                           <p class="u-fs-14 u-co-gray">참여일 : <fmt:formatDate value="${userPromotionItem.cretDt}" pattern="yyyy.MM.dd" /></p>
                        <div class="c-table">
                            <table class="u-mt--16" id="dataArea">
                                <caption>이벤트 참여 결과 - 당첨여부 상세, 결과발표 정보표</caption>
                                <colgroup>
                                    <col style="width: 60%">
                                    <col style="width: 40%">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th scope="col">당첨여부 상세</th>
                                        <th scope="col">결과발표</th>
                                    </tr>
                                </thead>
                                <tbody id="liDataArea">
                                    <tr>
                                        <c:choose>
                                            <c:when test="${empty userPromotionItem.rsltAndt}">
                                                <td>-</td>
                                                <td>-</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td class="u-ta-center">${userPromotionItem.prizenm}</td>
                                                <td class="u-ta-center"><fmt:formatDate value="${userPromotionItem.rsltAndt}" pattern="yyyy.MM.dd" /></td>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                 </tbody>
                               </table>
                           </div>
                      </c:forEach>
                 </c:when>
                 <c:otherwise>
                     <div class="c-table">
                           <table id="dataArea">
                             <caption>이벤트 참여 결과 - 당첨여부 상세, 결과발표 정보표</caption>
                             <colgroup>
                                 <col style="width: 70%">
                                 <col style="width: 30%">
                             </colgroup>
                             <thead>
                                 <tr>
                                     <th scope="col">당첨여부 상세</th>
                                     <th scope="col">결과발표</th>
                                 </tr>
                             </thead>
                             <tbody id="liDataArea">
                                 <tr>
                                     <td colspan="3">
                                         <div class="c-nodata">
                                             <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                             <p class="c-noadat__text">조회 결과가 없습니다.</p>
                                         </div>
                                     </td>
                                 </tr>
                              </tbody>
                          </table>
                      </div>
                  </c:otherwise>
             </c:choose>
        </div>
    </t:putAttribute>
</t:insertDefinition>