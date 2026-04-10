<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<span id="tab1Label" class="c-hidden">${gdncBas.cprtCardGdncNm} 상세</span>
<div>
	<div class="card-detail">
		<div class="card-detail__head">
			<strong id="cprtCardTitle" class="card-detail__title">${gdncBas.cprtCardGdncNm}</strong>
			<button class="c-button c-button--xsm" type="button" data-dialog-trigger="#modalShareAlert">
				<span class="c-hidden">공유하기</span>
				<i class="c-icon c-icon--share" aria-hidden="true"></i>
			</button>
		</div>
		<div class="card-detail__body">
			<div class="card-detail__image">
				<c:if test="${!empty gdncBas.cprtCardLargeImgNm}">
					<img src="${gdncBas.cprtCardLargeImgNm}" alt="${gdncBas.cprtCardGdncNm} 실물 사진" id="cardImg">
				</c:if>
			</div>
			<div class="card-detail__content">
				<%-- 제휴카드 혜택안내 : 제휴카드 대표 혜택명 (PCARD102) --%>
				<c:forEach var="bnfit" items="${bnfitList}">
					<c:if test="${bnfit.cprtCardBnfitItemCd eq 'PCARD102'}">
						<p class="card-detail__text">
							${bnfit.cprtCardItemNm}
						</p>
					</c:if>
				</c:forEach>

				<%-- 제휴카드 혜택안내 : 제휴카드 대표 혜택 안내 (PCARD101) --%>
				<c:forEach var="bnfit" items="${bnfitList}">
					<c:if test="${bnfit.cprtCardBnfitItemCd eq 'PCARD101'}">
						<p class="card-detail__sub">${bnfit.cprtCardItemNm}</p>
					</c:if>
				</c:forEach>

				<%-- 제휴카드 혜택안내 : 제휴카드 혜택 (PCARD103) --%>
				<ul class="card-detail__list">
					<c:forEach var="bnfit" items="${bnfitList}">
						<c:if test="${bnfit.cprtCardBnfitItemCd eq 'PCARD103'}">
							<li class="card-detail__item">
								<div class="card-detail__image">
			                    	<img src="${bnfit.cprtCardItemImgNm}" alt="">
			                    </div>
								<span>${bnfit.cprtCardItemNm}</span>
								<b>${bnfit.cprtCardItemDesc}</b>
							</li>
						</c:if>
					</c:forEach>
				</ul>

				<%-- 제휴카드안내링크상세 --%>
				<c:forEach var="link" items="${linkList}">
					<c:if test="${!empty link.linkUrl}">
						<div class="c-button-wrap">
							<c:choose>
								<c:when test="${link.linkUrlTarget eq 'Y'}">
									<a class="c-button c-button--full c-button--primary" target="_blank" href="${link.linkUrl}">${link.linkNm}</a>
								</c:when>
								<c:otherwise>
									<a class="c-button c-button--full c-button--primary" href="${link.linkUrl}">${link.linkNm}</a>
								</c:otherwise>
							</c:choose>


						</div>
					</c:if>
				</c:forEach>

				<%-- 가입가이드, 혜택비교 버튼 --%>
				<div class="c-button-wrap">
					<button class="c-button c-button--underline" type="button" onclick="javascript:openPage('pullPopup', '/event/cprtCardBnfitLayer.do?cprtCardCtgCd=${cprtCardCtgCd}', '', 0);">혜택 비교하기</button>
					<button class="c-button c-button--underline" type="button" onclick="javascript:openPage('pullPopup', '/event/cprtCardRegLayer.do?cprtCardGdncSeq=${gdncBas.cprtCardGdncSeq}', '', 1);">이용 가이드</button>
				</div>
			</div>
		</div>
	</div>

	<%-- 이벤트 배너 (CO0007 : PCARD206(PC) / (CO0007 : PCARD207(모바일)) --%>
    <c:forEach var="dtl" items="${dtlList}">
		<c:if test="${dtl.cprtCardItemCd eq 'PCARD206'}">${dtl.cprtCardItemSbst}</c:if>
	</c:forEach>

	<%-- 제휴카드혜택안내 --%>
	<div class="c-row c-row--lg">
		<div class="card-benefit--box one-source">
			<h3 class="c-heading c-title--type1">혜택 안내</h3>
			<hr class="c-hr c-hr--title">

			<c:if test="${!empty dtlList}">
				<c:forEach var="dtl" items="${dtlList}">
					<c:if test="${dtl.cprtCardItemCd ne 'PCARD205' && dtl.cprtCardItemCd ne 'PCARD206' && dtl.cprtCardItemCd ne 'PCARD207'}">
						<h4 class="c-heading c-title--type3">
							<c:choose>
								<c:when test="${dtl.cprtCardItemCd eq 'PCARD299'}">${dtl.cprtCardItemNm}</c:when>
								<c:otherwise>${dtl.cprtCardItemCdNm}</c:otherwise>
							</c:choose>
						</h4>
						${dtl.cprtCardItemSbst}
					</c:if>
				</c:forEach>
			</c:if>
		</div>
	</div>
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
