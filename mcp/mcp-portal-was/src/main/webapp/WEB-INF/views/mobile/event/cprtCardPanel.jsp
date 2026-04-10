<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>



<div class="card-info c-expand">
    <strong class="card-info__heading">${gdncBas.cprtCardGdncNm}</strong>
</div>
<div class="card-promotion">
    <div class="card-promotion__image">
        <c:if test="${!empty gdncBas.cprtCardThumbImgNm}">
            <img src="${gdncBas.cprtCardLargeImgNm}" alt="${gdncBas.cprtCardGdncNm}" id="cardImg">
        </c:if>
    </div>

    <%-- 제휴카드 혜택안내 : 제휴카드 대표 혜택명 --%>
    <c:forEach var="bnfit" items="${bnfitList}">
        <c:if test="${bnfit.cprtCardBnfitItemCd eq 'PCARD102'}">
        <div class="card-promotion__title">
            ${bnfit.cprtCardItemNm}
        </div>
        </c:if>
    </c:forEach>

    <%-- 제휴카드 혜택안내 : 제휴카드 대표 혜택 안내 --%>
    <c:forEach var="bnfit" items="${bnfitList}">
        <c:if test="${bnfit.cprtCardBnfitItemCd eq 'PCARD101'}">
            <div class="card-promotion__text">
                ${bnfit.cprtCardItemNm}
            </div>
        </c:if>
    </c:forEach>


    <%-- 제휴카드 혜택안내 : 제휴카드 혜택 --%>
    <c:if test="${!empty bnfitList}">

        <c:set var="isDisplay" value="false" />
        <c:forEach var="bnfit" items="${bnfitList}">
            <c:if test="${bnfit.cprtCardBnfitItemCd eq 'PCARD103'}">
                <c:set var="isDisplay" value="true" />
            </c:if>
        </c:forEach>

        <div class="card-promotion__info" <c:if test="${'false' eq isDisplay}"> style="display:none;" </c:if>>
            <c:forEach var="bnfit" items="${bnfitList}">
                <c:if test="${bnfit.cprtCardBnfitItemCd eq 'PCARD103'}">
                    <dl>
                        <dt>${bnfit.cprtCardItemNm}</dt>
                        <dd>${bnfit.cprtCardItemDesc}</dd>
                    </dl>
                </c:if>
            </c:forEach>
        </div>
    </c:if>

    <%-- 가입가이드, 혜택비교 버튼 --%>
    <div class="c-button-wrap">
        <button class="c-button c-button--xsm" onclick="javascript:openPage('pullPopup', '/m/event/cprtCardBnfitLayer.do?cprtCardCtgCd=${cprtCardCtgCd}', '');">혜택 비교</button>
        <button class="c-button c-button--xsm" onclick="javascript:openPage('pullPopup', '/m/event/cprtCardRegLayer.do?cprtCardGdncSeq=${gdncBas.cprtCardGdncSeq}', '');">이용 가이드</button>
    </div>

    <a class="c-button share" data-dialog-trigger="#bsSample1">
        <span class="c-hidden">공유하기</span>
        <i class="c-icon c-icon--share" aria-hidden="true"></i>
    </a>

</div>

<%-- 제휴카드안내링크상세 --%>
<c:forEach var="link" items="${linkList}">
    <c:if test="${!empty link.linkUrlMo}">
        <div class="c-button-wrap">
        	<c:choose>
        		<c:when test="${link.linkUrlTargetMo eq 'Y'}">
        			<a class="c-button c-button--full c-button--mint" target="_blank" href="${link.linkUrlMo}">${link.linkNm}</a>
        		</c:when>
        		<c:otherwise>
        			<a class="c-button c-button--full c-button--mint" href="${link.linkUrlMo}">${link.linkNm}</a>
        		</c:otherwise>
        	</c:choose>


        </div>
    </c:if>
</c:forEach>

<%-- 이벤트 배너 (CO0007 : PCARD206(PC) / (CO0007 : PCARD207(모바일)) --%>
   <c:forEach var="dtl" items="${dtlList}">
    <c:if test="${dtl.cprtCardItemCd eq 'PCARD207'}">${dtl.cprtCardItemSbst}</c:if>
</c:forEach>

<%-- 제휴카드혜택안내 --%>
<div class="card-benefit--box one-source">
    <h3 class="c-heading c-title--type1">혜택 안내</h3>
    <hr class="c-hr c-hr--title">

    <%-- 코드가 PCARD205 (가입가이드) 인경우 제외시킴  --%>
    <c:if test="${!empty dtlList}">
        <c:forEach var="dtl" items="${dtlList}">
            <c:if test="${dtl.cprtCardItemCd ne 'PCARD205' && dtl.cprtCardItemCd ne 'PCARD206' && dtl.cprtCardItemCd ne 'PCARD207'}">
                <h4 class="c-heading c-title--type3">
                    <c:choose>
                        <c:when test="${dtl.cprtCardItemCd eq 'PCARD299'}">${dtl.cprtCardItemNm}</c:when>
                        <c:otherwise>${dtl.cprtCardItemCdNm}</c:otherwise>
                    </c:choose>
                </h4>
                <ul class="c-text-list c-bullet c-bullet--dot">
                    ${dtl.cprtCardItemSbst}
                </ul>
            </c:if>
        </c:forEach>
    </c:if>
</div>

<script>
$(document).ready(function(){
    // OG 태그 초기화
    var v_host = $(location).attr('protocol') +"//"+$(location).attr('host');
    var v_src = ajaxCommon.isNullNvl($("#cardImg").attr("src"), "");
    var v_image = v_host + v_src;
    if(v_src == "") {
        v_image = "";
    }
    $("#meta_og_image").attr("content", v_image);

    $(document).one("click", "#mainTabList > button", function() {
        var v_cprtCardCtgCd = $(this).attr("data-cprtcardctgcd");
        var v_cprtCardTxt = $(this).text();

        var v_title = v_cprtCardTxt + " | 제휴카드 | kt M모바일 공식 다이렉트몰";
        var v_link = v_host + "/event/cprtCardList.do?cprtCardCtgCd="+v_cprtCardCtgCd;

        $("#meta_og_title").attr("content", v_title);
        $("#meta_og_url").attr("content", v_link);
    });
});
</script>
