<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>kt M모바일</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
	<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/resources/js/jqueryCommon.js"></script>

	<link rel="stylesheet" type="text/css" href="/resources/css/mobile/common.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/mobile/style.css">

	<script type="text/javascript" charset="UTF-8" src="/resources/js/mobile/popup/dlvryChkAddPop.js"></script>
	<script type="text/javascript" src="/resources/js/proj4.js" ></script>

</head>
<body style="margin:0;padding:0;border:0;">
<form name="form" id="form" method="post">
	<input type="hidden" name="currentPage" id="currentPage" value="1" />
	<input type="hidden" name="countPerPage" id="countPerPage" value="10" />
	<input type="hidden" name="keyword" id="keyword" value="" />
</form>

	<div id="content" style="margin:0;padding:0">
		<div class="content_main" style="margin:0;padding:0">
			<div class="mypage padding_15">
				<div class="table_display">
					<div class="table_cell_display width_60">
						<input type="text" id="tmpKey" class="input_intable width_100" placeholder="도로명주소, 건물명 또는 지번입력" title="도로명주소, 건물명 또는 지번입력">
					</div>
					<div class="table_cell_display width_20 text_align_right">
						<button type="button" class="btn_intable_lightgrey margin_left_10" onclick="goSubmit();">검색</button>
					</div>
				</div>
				<div class="text_666 margin_topdown_10">
					검색어 예: 도로명(반포대로 58) 건물명(독립기념관), 지번(삼성동 25)
				</div>

				<div class="addr_search border_top_d3 padding_top_20 padding_bottom_10 padding_left_5"  style="display:none;" id="chkArea">

            	</div>

				<div class="info_list_table">
					<table summary="주소검색 예시" >
						<caption class="table_title_font12_type1"></caption>
						<colgroup>
							<col width="30%" />
							<col width="30%" />
							<col />
						</colgroup>
						<thead>
							<tr>
								<th scope="col">구분</th>
								<th scope="col">권장 검색방법</th>
								<th scope="col">검색 예시</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>도로명으로 조회</td>
								<td>시 도/시 군 구 +도로명주소</td>
								<td>예)종로구 사직로 161</td>
							</tr>
							<tr>
								<td>지번으로 조회</td>
								<td>시 도/시 군 구/읍 면 동 +지번</td>
								<td>예)종로구 광훈동 198-1</td>
							</tr>
							<tr>
								<td>건물명으로 조회</td>
								<td>시 도/시 군 구 +건물명</td>
								<td>예)종로구 홍익빌딩</td>
							</tr>
						</tbody>
					</table>
					<div class="addservice">
						<ul>
							<li>건물명은 각 자치단체에서 등록한 경우 조회가 가능합니다.</li>
							<li>화면 상단의 상세검색을 이용하시면 보다 정확한 결과를 얻으실 수 있습니다.</li>
						</ul>
					</div>
				</div>

				<div class="info_list_table" style="display:none">
					<table summary="주소검색 결과" >
						<caption class="table_title_font12_type1"></caption>
						<colgroup>
							<col width="10%" />
							<col />
							<col width="20%" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col">NO.</th>
								<th scope="col">도로명주소</th>
								<th scope="col">우편번호</th>
							</tr>
						</thead>
						<tbody id="searchList">
						</tbody>
					</table>
					<div class="paging table_display text_align_center">
						<div class="table_cell_display width_25">
						</div>
						<div class="table_cell_display width_50">
						</div>
						<div class="table_cell_display width_25">
						</div>
					</div>
				</div>

				<div class="board_detail_table" style="display:none">
					<table summary="상세주소입력">
						<caption class="table_title_font12_type1">상세주소입력</caption>
						<colgroup>
							<col width="20%" />
							<col />
						</colgroup>
						<tr>
							<th scope="row">우편번호</th>
							<td id="zipText"></td>
						</tr>
						<tr>
							<th scope="row">도로명</th>
							<td id="addr1Text"></td>
						</tr>
						<tr>
							<th scope="row">상세주소</th>
							<td>
								<input type="text" id="addrDetail" class="input_intable width_100" placeholder="입력해주세요." title="상세주소를 입력해주세요.">
								<br/>예) 1202동 101호 or 종로빌딩 1층
							</td>
						</tr>
					</table>
				</div>
				<div class="text_align_center" id="cButton" style="display:none">
					<button type="button" class="btn_small_red" id="setAddr2">주소입력</button>
				</div>
			</div>
		</div>
	</div>
<input type="hidden" name="psblYn" id="psblYn" value="" />
<input type="hidden" name="bizOrgCd" id="bizOrgCd" value="" />
<input type="hidden" name="acceptTime" id="acceptTime" value="" />
<input type="hidden" name="confmKey" id="confmKey" value="<spring:eval expression="@propertiesForJsp['juso.coord.key']" />" />
<input type="hidden" name="entY" id="entY" value="" />
<input type="hidden" name="entX" id="entX" value="" />

</body>
</html>