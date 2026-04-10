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
    <input type="hidden" id="pageNo" name="pageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}">
    <input type="hidden" id="listCount" name="listCount" value="${listCount}">
    <input type="hidden" id="maxPage" name="maxPage" value="${maxPage}">
		<div class="c-modal__content" style="height: 70vh;">
			<div class="c-modal__header">
				<h2 class="c-modal__title" id="modalPointTitle">포인트 이용/적립 내역 조회</h2>
				<button class="c-button" data-dialog-close
					href="javascript:void(0);">
					<i class="c-icon c-icon--close" aria-hidden="true"></i> <span
						class="c-hidden">팝업닫기</span>
				</button>
			</div>
			<div class="c-modal__body" style="overflow-y: scroll;">
				<div class="c-form-wrap">
					<div class="c-form-group" role="group" aira-labelledby="formGroupA">
						<h3 class="c-hidden" id="formGroupA">조회조건</h3>
						<div class="c-heading-wrap">
							<label class="c-heading c-heading--fs20 c-heading--regular" for="selLine">조회회선 선택</label>
							<div class="c-heading-wrap__sub">
								<div class="c-select-search u-mt--0">
									<div class="c-form--line">
										<select class="c-select u-fs-16" id="popCntrNo">
											<c:forEach items="${cntrList}" var="cntrList">
												<option value="${cntrList.svcCntrNo}" moCntr="${cntrList.cntrMobileNo}" ${cntrNo eq cntrList.svcCntrNo?'selected':'' }>${cntrList.cntrMobileNo}</option>
											</c:forEach>
										</select>
									</div>
									<div class="c-form--line">
										<label class="c-label c-hidden" for="selYear">년도 선택</label>
										<select
											class="c-select u-fs-16" id="pointYyyy">
											<option value="${formatedNow-2}">${formatedNow-2}년</option>
											<option value="${formatedNow-1}">${formatedNow-1}년</option>
											<option value="${formatedNow}" selected>${formatedNow}년</option>
										</select>
									</div>
									<div class="c-form--line">
										<label class="c-label c-hidden" for="selMonth">월 선택</label> <select
											class="c-select u-fs-16" id="pointMm">
											<option value="ALL">전체</option>
											<option value="01" ${formatedNow2 eq '01'?'selected':'' }>01월</option>
											<option value="02" ${formatedNow2 eq '02'?'selected':'' }>02월</option>
											<option value="03" ${formatedNow2 eq '03'?'selected':'' }>03월</option>
											<option value="04" ${formatedNow2 eq '04'?'selected':'' }>04월</option>
											<option value="05" ${formatedNow2 eq '05'?'selected':'' }>05월</option>
											<option value="06" ${formatedNow2 eq '06'?'selected':'' }>06월</option>
											<option value="07" ${formatedNow2 eq '07'?'selected':'' }>07월</option>
											<option value="08" ${formatedNow2 eq '08'?'selected':'' }>08월</option>
											<option value="09" ${formatedNow2 eq '09'?'selected':'' }>09월</option>
											<option value="10" ${formatedNow2 eq '10'?'selected':'' }>10월</option>
											<option value="11" ${formatedNow2 eq '11'?'selected':'' }>11월</option>
											<option value="12" ${formatedNow2 eq '12'?'selected':'' }>12월</option>
										</select>
									</div>
									<input type="hidden" name="pageNo" id="pageNo" value="${pageInfoBean.pageNo}" />
									<button class="c-button c-button-round--xsm c-button--primary u-ml--10" onclick="$('#pageNo').val('1');selectDetailListByDate();">조회</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="c-filter u-mt--32" role="group" aira-labelledby="formGroupB">
					<h3 class="c-hidden" id="formGroupB">포인트 유형 선택</h3>
					<div class="c-filter__inner">
						<input class="c-radio c-radio--button c-radio--button--type3 pointCheck" id="allPoint" type="radio" name="pointCheck" value="ALL" onclick="$('#pageNo').val('1');selectDetailList(this);" checked>
						<label class="c-label" for="allPoint">전체</label> 
						<input class="c-radio c-radio--button c-radio--button--type3 pointCheck" id="sPoint" type="radio" name="pointCheck" value="S" onclick="$('#pageNo').val('1');selectDetailList(this);">
						<label class="c-label" for="sPoint">적립</label> 
						<input class="c-radio c-radio--button c-radio--button--type3 pointCheck" id="uPoint" type="radio" name="pointCheck" value="U" onclick="$('#pageNo').val('1');selectDetailList(this);">
						<label class="c-label" for="uPoint">사용</label> 
						<input class="c-radio c-radio--button c-radio--button--type3 pointCheck" id="ePoint" type="radio" name="pointCheck" value="E" onclick="$('#pageNo').val('1');selectDetailList(this);">
						<label class="c-label" for="ePoint">소멸</label>
					</div>
				</div>
				<ul class="c-list c-list--type1 u-mt--48" id="contents">
					<div class="c-nodata u">
						<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
						<p class="c-noadat__text">조회 결과가 없습니다.</p>
					</div>
				</ul>
				<div class="c-button-wrap"
					href="javascript:void(0);" onclick="selectDetailListByDate();" id="moreList">
					<button class="c-button c-button--full">
						더보기<span class="c-button__sub" id="pageCnt">${listCount}/${pageInfoBean.totalCount }</span>
						<i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
					</button>
				</div>

		<script>
      	$(document).ready(function(){
      		$.ajax({
    			id: 'myPointSaveHistListHtml',
    			url: '/mypage/myPointSaveHistListHtml.do?cntrNo='+$('#cntrNo').val()+'&pointYyyy='+$('#pointYyyy').val()+'&pointMm='+$('#pointMm').val(),
    			type: 'get',
    			data: '',
    			dataType: "text",
    			async: false,
    			success: function(resp) {
    				listContentsCallback(resp);
    			}
    		});
      	});
      </script>
      