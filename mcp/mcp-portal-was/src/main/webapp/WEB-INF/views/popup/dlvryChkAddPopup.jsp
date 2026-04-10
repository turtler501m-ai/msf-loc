<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="layoutPopupDefault">
<t:putAttribute name="titleAttr">주소검색 - kt M모바일</t:putAttribute>
<t:putAttribute name="scriptHeaderAttr">
	<script type="text/javascript" charset="UTF-8" src="/resources/js/popup/dlvryChkAddPopup.js"></script>
	<script type="text/javascript" src="/resources/js/proj4.js" ></script>
</t:putAttribute>

<t:putAttribute name="contentAttr">
		<div id="addrPopup">

            <!-- 검색 -->
            <div class="addr_title">
                배송 가능 지역 확인
            </div>

            <div class="addr_search">
            	<input type="text" id="keyword" class="addr_search_input" placeholder="도로명주소, 건물명 또는 지번입력" title="도로명주소, 건물명 또는 지번입력">
				<button type="button" id="searchBtn" class="addr_search_btn">검색</button>
            	<p class="addr_search_ex">
            		검색어 예: 도로명(반포대로 58) 건물명(독립기념관), 지번(삼성동 25)
            	</p>
            </div>
			<div class="addr_search border_top_grey padding_top_20 padding_left_160" style="display:none;" id="chkArea">

            </div>
            <!-- 검색 -->

			<!-- step1 -->
            <div class="info_list_table margin_20 stepDiv" id="step1">
					<table summary="주소검색 예시" >
						<caption></caption>
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
				<!-- step1 -->

				<!-- step2 -->
				<div class="info_list_table margin_20 stepDiv" id="step2">
					<table summary="주소검색 결과">
						<caption class="table_title_font12_type1 schResultCntTxt"></caption>
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

						<!-- 주소검색 결과 -->
						<tbody id="searchList">
						</tbody>

					</table>


					<div class="board_list_table_paging" id="divPaging">
						<div id="tablePage">
							<button type="button" title="처음 페이지" class="pageButton btn_first">처음 페이지</button>
							<button type="button" title="이전 페이지" class="pageButton btn_prev">이전 페이지</button>
							<button type="button" title="현재페이지" class="pageButton active">1</button>
							<button type="button" title="2 페이지" pageno="2" class="pageButton _pageWrap">2</button>
							<button type="button" title="3 페이지" pageno="3" class="pageButton _pageWrap">3</button>
							<button type="button" title="4 페이지" pageno="4" class="pageButton _pageWrap">4</button>
							<button type="button" title="5 페이지" pageno="5" class="pageButton _pageWrap">5</button>
							<button type="button" onclick="javascript:void(0)" pageno="11" class="pageButton btn_next _pageWrap" title="다음 페이지">다음 페이지</button>
							<button type="button" onclick="javascript:void(0)" pageno="68" class="pageButton btn_last _pageWrap" title="마지막 페이지">마지막 페이지</button>
						</div>
					</div>

				</div>
				<!-- step2 -->

				<!-- step3-->
				<div id="step3" class="stepDiv">
					<div class="board_detail_table margin_20">
						<table summary="상세주소입력">
							<caption class="table_title_font12_type1" style="display:none">상세주소입력</caption>
							<colgroup>
								<col width="20%" />
								<col />
							</colgroup>
							<tr class="border_top_d3">
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
									<input type="text" id="addrDetail" class="input_intable width_400px" placeholder="입력해주세요." title="상세주소를 입력해주세요.">
									<br/>예) 1202동 101호 or 종로빌딩 1층
								</td>
							</tr>
						</table>
					</div>
					<div class="margin_20">
						<label class="font_size_14" style="font-weight: normal;"> ※ 배송 특성 상 배송 신청 이후 배송지 변경은 불가합니다.</label>
					</div>
					<div class="text_align_center" style="padding-bottom:10px;">
						<button type="button" id="setAddr2" class="btn_small_red">주소입력</button>
					</div>
				</div>
				<!-- step3-->
<input type="hidden" name="psblYn" id="psblYn" value="" />
<input type="hidden" name="bizOrgCd" id="bizOrgCd" value="" />
<input type="hidden" name="acceptTime" id="acceptTime" value="" />
<input type="hidden" name="confmKey" id="confmKey" value="<spring:eval expression="@propertiesForJsp['juso.coord.key']" />" />
<input type="hidden" name="entY" id="entY" value="" />
<input type="hidden" name="entX" id="entX" value="" />
        </div>
</t:putAttribute>

</t:insertDefinition>