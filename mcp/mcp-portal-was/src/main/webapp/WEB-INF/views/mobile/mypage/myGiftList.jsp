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
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="contentAttr">
    <script>
    $(document).ready(function(){
        $("#moreBtn").click(function(){
            var listCount = ${listCount};
            var totalCount = ${totalCount};
            if(listCount >= totalCount){
                alert("마지막 페이지 입니다.");
                return false;
            } else {
                var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),"1"));
                myGiftListAjax(pageNo+1);
                $("#pageNo").val(pageNo+1);
            }
        });
    });
    var myGiftListAjax = function(pageNo) {

        $("#pageNo").val(pageNo);
        var pageNo = pageNo;
        var contractNum = $("#cntrList").val();
        var varData = ajaxCommon.getSerializedData({
            pageNo : pageNo,
            contractNum : contractNum,
        });

        ajaxCommon.getItem({
            id:'myGiftListAjax'
            ,cache:false
            ,url:'/m/mypage/myGiftListAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        },function(jsonObj){

            var totalCount = jsonObj.totalCount; // 총갯수
            var giftCustList = jsonObj.giftCustList; // 조회데이터
            var listCount = jsonObj.listCount; // 페이징포함 곱한 갯수

            viewDesc(totalCount,giftCustList,listCount);
        });
    }
    var viewDesc = function(totalCount,giftCustList,listCount){
        var htmlArea = "";

        if(giftCustList !=null && giftCustList.length>0 ){

            var prmtNm =  "";
            var prdtNm = "";
            var regstDttm = "";

            for(var i=0; i<giftCustList.length; i++){

                prmtNm =  giftCustList[i].prmtNm;
                prdtNm = giftCustList[i].prdtNm;
                regstDttm =  giftCustList[i].regstDttm;

                htmlArea += '	<tr>';
                htmlArea += '		<td class="u-ta-center">'+ prmtNm +'</td>';
                htmlArea += '		<td class="u-ta-center">'+ prdtNm +'</td>';
                htmlArea += '		<td class="u-ta-center">'+ regstDttm +'</td>';
                htmlArea += '	</tr>';
            }
            $("#liDataArea").append(htmlArea);
            $("#dataArea").show();
            $(".c-nodata").hide();
            $("#listCount").text(numberWithCommas(listCount)); // 현재 노출
            $("#totalCount").text(numberWithCommas(totalCount));
            if(listCount < totalCount){
                $("#addBtnArea").show();
            } else {
                $("#addBtnArea").hide();
            }
        } else {
            //initView();
        }
        var accEl = $("#dataArea")[0];
        //var accIns = KTM.Accordion.getInstance(accEl).update();
    }
    function initView(){

        $("#dataArea").hide();
        $("#liDataArea").html("");
        $(".c-nodata").show();
        $("#listCount").text(0); // 현재 노출
        $("#totalCount").text(0);
        $("#addBtnArea").hide();
    }
    function selectCntr(){
        ajaxCommon.createForm({
            method:"post"
            ,action:"/m/mypage/myGiftList.do"
        });
        var contractNum = $("#cntrList").val();

        ajaxCommon.attachHiddenElement("contractNum", contractNum);
        ajaxCommon.formSubmit();
    }
    </script>
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    My 사은품
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <h3 class="c-heading c-heading--type7 u-mt--40">
                조회회선
                <div class="c-form u-width--40p">
                    <select class="c-select c-select--type3" id="cntrList" onchange="selectCntr();">
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
            </h3>
            <hr class="c-hr c-hr--type3 u-mt--20">
            <div class="c-table c-table--plr-16 u-mt--40">
                <p class="c-table-title c-flex c-flex--jfy-between">
                    <span class="u-co-black fs-16 u-fw--medium">사은품 신청 내역 변경</span> <span
                        class="u-co-gray-7 fs-13 u-fw--medium">${formatedNow} 기준</span>
                </p>
                <table id="dataArea">
                    <caption>사은품 신청 내역 변경</caption>
                    <colgroup>
                        <col style="width: 34%">
                        <col style="width: 35%">
                        <col style="width: 31%">
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col">지급사유</th>
                            <th scope="col">사은품 명</th>
                            <th scope="col">신청일</th>
                        </tr>
                    </thead>
                    <tbody id="liDataArea">
                    <c:choose>
                    <c:when test="${!empty giftCustList}">
                        <c:forEach var="giftCustListVar" items="${giftCustList}">
                            <tr>
                                <td class="u-ta-center">${giftCustListVar.prmtNm }</td>
                                <td class="u-ta-center">${giftCustListVar.prdtNm }</td>
                                <td class="u-ta-center">${giftCustListVar.regstDttm }</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="3">
                            <div class="c-nodata">
                                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                <p class="c-noadat__text">조회 결과가 없습니다.</p>
                            </div>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
                </table>
                <input type="hidden" name="pageNo" id="pageNo" value="${giftCustListVar.pageNo}" />
                <c:if test="${totalCount gt '10'}">
                <div class="c-button-wrap u-mt--8" id="addBtnArea">
                    <button class="c-button c-button--full fs-14" id="moreBtn">더보기<span class="c-button__sub u-co-gray-7 u-mr--8" id="pageNav"><span id="listCount"><fmt:formatNumber value="${listCount}" pattern="#,###" /></span>/<span id="totalCount"><fmt:formatNumber value="${totalCount}" pattern="#,###" /></span></span>
                        <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
                        <span id="maxPage" style="display: none;"></span>
                    </button>
                </div>
                </c:if>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>