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
        <script>

    function selectCntr(){
        ajaxCommon.createForm({
            method:"post"
            ,action:"/mypage/myGiftList.do"
        });
        var contractNum = $("#cntrList").val();

        ajaxCommon.attachHiddenElement("contractNum", contractNum);
        ajaxCommon.formSubmit();
    }
    function goPaging(pageNo){

        ajaxCommon.createForm({
            method:"post"
            ,action:"/mypage/myGiftList.do"
        });
        //var pageNo = $("#pageNo").val();
        ajaxCommon.attachHiddenElement("pageNo",pageNo);
        var contractNum = $("#cntrList").val();
        ajaxCommon.attachHiddenElement("contractNum", contractNum);

        ajaxCommon.formSubmit();

    }
    </script>
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">My 사은품</h2>
            </div>
            <div class="c-row c-row--lg">
                <div class="c-heading-wrap">
                    <h3 class="c-heading c-heading--fs20">조회할 회선을 선택해 주세요.</h3>
                    <div class="c-heading-wrap__sub">
                        <div class="c-form c-form--line">
                            <label class="c-label c-hidden" for="selLine">회선 선택</label>
                            <select class="c-select" id="cntrList">
                                <c:forEach items="${cntrList}" var="cntrListVar">
                                    <c:choose>
                                        <c:when test="${maskingSession eq 'Y'}">
                                            <option value="${cntrListVar.svcCntrNo}" ${contractNum eq cntrListVar.svcCntrNo?'selected':'' }>${cntrListVar.formatUnSvcNo}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${cntrListVar.svcCntrNo}" ${contractNum eq cntrListVar.svcCntrNo?'selected':'' }>${cntrListVar.cntrMobileNo}</option>
                                        </c:otherwise>
                                   </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <button class="c-button c-button-round--xsm c-button--primary u-ml--10" onclick="selectCntr();">조회</button>
                    </div>
                </div>
                <hr class="c-hr c-hr--title">
                <div class="c-heading-wrap">
                    <h3 class="c-heading c-heading--fs16">사은품 신청 내역</h3>
                    <div class="c-heading-wrap__sub">
                        <span class="c-text c-text--fs14 u-co-gray">${formatedNow} 기준</span>
                    </div>
                </div>
                <div class="c-table u-mt--16">
                    <table>
                        <caption>번호, 지급사유, 사은품명, 신청일을 포함한 사은품 신청 내역</caption>
                        <colgroup>
                            <col style="width: 8%">
                            <col style="width: 35%">
                            <col>
                            <col style="width: 15%">
                        </colgroup>
                        <thead>
                            <tr>
                                <th scope="col">번호</th>
                                <th scope="col">지급사유</th>
                                <th scope="col">사은품명</th>
                                <th scope="col">신청일</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                        <c:when test="${!empty giftCustList}">
                            <c:forEach var="giftCustListVar" items="${giftCustList}" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td>${giftCustListVar.prmtNm }</td>
                                    <td>${giftCustListVar.prdtNm }</td>
                                    <td>${giftCustListVar.regstDttm }</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="4"><p class="nodata">조회결과가 없습니다.</p></td>
                            </tr>
                        </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </div>
                <c:if test="${pageInfoBean.totalCount > 10}">
                <c:if test="${!empty giftCustList}">
                <nav class="c-paging" aria-label="검색결과">
                    <nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="goPaging" />
                </nav>
                </c:if>
                </c:if>
            </div>
        </div>
        <input type="hidden" id="pageNo" name="pageNo" value="${pageInfoBean.pageNo}">
</t:putAttribute>
</t:insertDefinition>