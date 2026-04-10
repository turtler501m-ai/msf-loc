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
   
     <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="saving-point-title">포인트 이용/적립 내역 조회</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body overflow-x-hidden">
          <h3 class="c-heading c-heading--type7 u-mt--32">조회회선</h3>
          <div class="c-select-wrap u-select-adj_size">
            <select class="c-select c-select--type3" id="popCntrNo" onchange="$('#pageNo').val('1');selectDetailListByDate();">
	            <c:forEach items="${cntrList}" var="cntrList">
	            	<option value="${cntrList.svcCntrNo}" moCntr="${cntrList.cntrMobileNo}" ${cntrNo eq cntrList.svcCntrNo?'selected':'' }>${cntrList.cntrMobileNo}</option>
				</c:forEach>
            </select>
            <select class="c-select c-select--type3" id="pointYyyy" onchange="$('#pageNo').val('1');selectDetailListByDate();">
              <option value="${formatedNow-2}">${formatedNow-2}년</option>
              <option value="${formatedNow-1}">${formatedNow-1}년</option>
              <option value = "${formatedNow}" selected>${formatedNow}년</option>
            </select>
            <select class="c-select c-select--type3" id="pointMm" onchange="$('#pageNo').val('1');selectDetailListByDate();">
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
          <hr class="c-hr c-hr--type3">
          <div class="c-filter c-expand u-mt--24">
            <div class="c-filter__inner">
              <div class="c-chk-wrap">
                <input class="c-checkbox c-checkbox--button c-checkbox--button--type3 pointCheck" id="allPoint" type="checkbox" name="11" value = "ALL" checked onclick="$('#pageNo').val('1');selectDetailList(this)">
                <label class="c-label u-plr--20" for="allPoint">전체</label>
              </div>
              <div class="c-chk-wrap">
                <input class="c-checkbox c-checkbox--button c-checkbox--button--type3 pointCheck" id="sPoint" type="checkbox" name="11" value="S" onclick="$('#pageNo').val('1');selectDetailList(this)">
                <label class="c-label u-plr--20" for="sPoint">적립</label>
              </div>
              <div class="c-chk-wrap">
                <input class="c-checkbox c-checkbox--button c-checkbox--button--type3 pointCheck" id="uPoint" type="checkbox" name="11" value="U" onclick="$('#pageNo').val('1');selectDetailList(this)">
                <label class="c-label u-plr--20" for="uPoint">사용</label>
              </div>
              <div class="c-chk-wrap">
                <input class="c-checkbox c-checkbox--button c-checkbox--button--type3 pointCheck" id="ePoint" type="checkbox" name="11" value="E" onclick="$('#pageNo').val('1');selectDetailList(this)">
                <label class="c-label u-plr--20" for="ePoint">소멸</label>
              </div>
            </div>
          </div>
          <div >
	          <ul class="c-list c-list--type1 u-mt--16" id="contents">
	          	 <div class="c-nodata u">
		            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
		            <p class="c-nodata__text">조회 결과가 없습니다.</p>
	       		 </div>
	          </ul>
	          <div class="c-button-wrap u-mt--8"  onclick="selectDetailListByDate();" id="moreList">
	            <button class="c-button c-button--full">더보기<span class="c-button__sub" id="pageCnt">${listCount}/${pageInfoBean.totalCount }</span>
	              <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
	            </button>
	          </div>
	      </div>
        </div>
      </div>
      
      <script>
      	$(document).ready(function(){
      		$.ajax({
    			id: 'myPointSaveHistListHtml',
    			url: '/m/mypage/myPointSaveHistListHtml.do?cntrNo='+$('#cntrNo').val()+'&pointYyyy='+$('#pointYyyy').val()+'&pointMm='+$('#pointMm').val(),
    			type: 'GET',
    			data: '',
    			dataType: "text",
    			async: false,
    			success: function(resp) {
    				listContentsCallback(resp);
    			}
    		});
      	});
      </script>

