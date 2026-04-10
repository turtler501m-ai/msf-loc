<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%-- <%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %> --%>
<%-- <%@ taglib prefix="nmcp" uri="nmcp.tag" %> --%>
<%-- <un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" /> --%>
<t:insertDefinition name="layoutPopupDefault">
<t:putAttribute name="titleAttr">주소검색 - kt 엠모바일</t:putAttribute>
<t:putAttribute name="scriptHeaderAttr">
<script type="text/javascript">

	var searchTxtTmp = "";
	var pageNoTmp = "";

	//특수문자, 특정문자열(sql예약어의 앞뒤공백포함) 제거
	function checkSearchedWord(objVal){

		if(objVal.length >0){
			//특수문자 제거
			var expText = /[%=><]/ ;
			if(expText.test(objVal) == true){
				alert("특수문자를 입력 할수 없습니다.") ;
				objVal = objVal.split(expText).join("");
				return false;
			}

			//특정문자열(sql예약어의 앞뒤공백포함) 제거
			var sqlArray = new Array(
				//sql 예약어
				"OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC",
	             		 "UNION",  "FETCH", "DECLARE", "TRUNCATE"
			);

			var objValUpper = objVal.toUpperCase();

			var regex ;
			var regex_plus ;
			for(var i=0; i<sqlArray.length; i++){
			    if (objValUpper.indexOf(sqlArray[i]) >-1) {
			        alert("\"" + sqlArray[i]+"\"와(과) 같은 특정문자로 검색할 수 없습니다.");
					return false;
			    }
			}
		}
		return true ;
	}

	function stepFn(step) {
		$(".stepDiv").hide();
		$("#step" + step).show();
	}

	function searchList() {

		if(searchTxtTmp == "") {
			alert("검색어를 입력해 주세요");
			$('#keyword').focus();
			return;
		}

		if (!checkSearchedWord(searchTxtTmp)) {
			return ;
		}

		var varData = ajaxCommon.getSerializedData({
			currentPage : pageNoTmp
	        ,countPerPage : 5
	        ,keyword : searchTxtTmp
	    });

		$.ajax({
			type:"post",
		    url : "/addrlink/addrLinkApi.do",
		    data : varData,
		    dataType : "xml",
			error: function(jqXHR, textStatus, errorThrown){
				return;
			},
			complete: function(){
			},
			success:	function(data){

				var xmlStr = data;

				var errorCode = $(xmlStr).find('errorCode').text();
				var errorMsg = $(xmlStr).find('errorMessage').text();
				var totalCount = $(xmlStr).find('totalCount').text();
				var currentPage = $(xmlStr).find('currentPage').text();
				var countPerPage = $(xmlStr).find('countPerPage').text();

				var index = (currentPage-1) * countPerPage + 1;
				var totalPage = parseInt(totalCount / countPerPage);
				if((totalCount % countPerPage) > 0) {
					totalPage = totalPage + 1;
				}

				$('.table_title_font12_type1').text('도로명주소 검색 결과('+totalCount+'건)');

				var htmlStr = "";
				if(totalCount > 0 && errorCode == "0") {

					$('#searchList').empty();
					$(xmlStr).find('juso').each(function() {
						htmlStr = "";
						htmlStr += '<tr>';
						htmlStr += '<td>'+index+'</td>';
						htmlStr += '<td><a href="javascript:void(0)" class="setAddr1">'+$(this).find('roadAddr').text() + '<br/>[지번]' + $(this).find('jibunAddr').text()+' '+$(this).find('bdNm').text()+'</a></td>';
						htmlStr += '<td>'+$(this).find('zipNo').text() + '</td>';
						htmlStr += '</tr>';
						index ++;
						$('#searchList').append(htmlStr);
						$('#searchList tr:last a').data($(this));
					});

				} else {

					if(errorCode != "0") {
						if(errorCode == "P0001") {
							htmlStr = "<tr><td colspan='3'>"+errorMsg+"<br/>예) 역삼동 635</td></tr>";
						} else {
							htmlStr = "<tr><td colspan='3'>"+errorMsg+"</td></tr>";
						}
					} else {
						htmlStr = "<tr><td colspan='3'>검색된 결과가 없습니다.</td></tr>";
					}
					$('#searchList').html(htmlStr);

				}

				if(totalCount > 0 && errorCode == "0") {

					var pageSize = 5; //페이지 사이즈
					var firstPageNoOnPageList = parseInt((currentPage - 1) / pageSize ) * pageSize + 1;
					var lastPageNoOnPageList = (firstPageNoOnPageList + pageSize - 1);
				    if (lastPageNoOnPageList > totalPage) {
				    	lastPageNoOnPageList = totalPage;
				    }

				    var pageFirst = '';
				    var pageLeft = '';

				    if(currentPage > 1 ) {
			            if (firstPageNoOnPageList > pageSize) {
			                pageFirst = '<button type="button" title="처음 페이지" class="pageButton btn_first" onclick="goSubmit(1)">처음 페이지</button>';
						    pageLeft = '<button type="button" title="이전 페이지" class="pageButton btn_prev" onclick="goSubmit('+ (firstPageNoOnPageList-1) +')">이전 페이지</button>';
			            } else {
			                pageFirst = '<button type="button" title="처음 페이지" class="pageButton btn_first" onclick="goSubmit(1)">처음 페이지</button>';
						    pageLeft = '<button type="button" title="이전 페이지" class="pageButton btn_prev" onclick="goSubmit('+(currentPage-1)+')">&lt;</button>';
			            }
			        } else {
			           	pageFirst = '<button type="button" title="처음 페이지" class="pageButton btn_first">처음 페이지</button>';
					    pageLeft = '<button type="button" title="이전 페이지" class="pageButton btn_prev">이전 페이지</button>';
			        }

				    var pageStr = ""
					for (var i = firstPageNoOnPageList; i <= lastPageNoOnPageList; ++i) {
			        	if (i == currentPage) {
			        		pageStr +="<button type='button' title='현재페이지' class='pageButton active'>"+i+"</button>\n";
			            } else {
			            	pageStr +="<button type='button' title='"+i+"페이지' class='pageButton _pageWrap' onclick='goSubmit("+i+")'>"+i+"</button>\n";
			            }
				    }

				    var pageLast = '';
					var pageRight = '';

					if(totalPage > currentPage ){
			            if (lastPageNoOnPageList < totalPage) {
							pageRight = '<button type="button" class="pageButton btn_next _pageWrap" title="다음 페이지" onclick="goSubmit('+(firstPageNoOnPageList + pageSize)+')">다음 페이지</button>';
							pageLast = '<button type="button" class="pageButton btn_last _pageWrap" title="마지막 페이지" onclick="goSubmit('+totalPage+')">마지막 페이지</button>';
			            } else {
							pageRight = '<button type="button" class="pageButton btn_next _pageWrap" title="다음 페이지" onclick="goSubmit('+(currentPage+1)+')">다음 페이지</button>';
							pageLast = '<button type="button" class="pageButton btn_last _pageWrap" title="마지막 페이지" onclick="goSubmit('+totalPage+')">마지막 페이지</button>';
			            }
			        } else {
			        	pageLast = '<button type="button" class="pageButton btn_next _pageWrap" title="다음 페이지">다음 페이지</button>';
						pageRight = '<button type="button" class="pageButton btn_last _pageWrap" title="마지막 페이지">마지막 페이지</button>';
			        }


					var pagingHtml = "";
					pagingHtml += pageFirst;
					pagingHtml += pageLeft;
					pagingHtml += pageStr;
					pagingHtml += pageRight;
					pagingHtml += pageLast;

					$("#tablePage").html(pagingHtml);

				}

				stepFn(2);

	       	}

		 });

	}

	function goSubmit(pageNo) {
		pageNoTmp = pageNo;
		searchList();
	}

	$(document).ready(function() {

		//주소선택
		$(document).on("click", ".setAddr1", function() {
			var roadAddrPart1 = $(this).data().find('roadAddrPart1').text();
			var zipNo = $(this).data().find('zipNo').text();
			$("#setAddr2").data($(this).data());

			$("#addr1Text").text(roadAddrPart1);
			$("#zipText").text(zipNo);
			$("#addrDetail").val("");
			stepFn(3);
		});

		//상세주소 제한길이
		$('#addrDetail').keyup(function(){
			if($('#addrDetail').val().length > $('#addrDetail').attr('maxlength')){
				$('#addrDetail').val($('#addrDetail').val().substr(0, $('#addrDetail').attr('maxlength')));
			}
		});
		
		//주소입력
		$("#setAddr2").click(function() {

			if($('#addrDetail').val().trim() == "") {
				alert("상세주소를 입력해 주세요");
				$('#addrDetail').focus();
				return;
			}

			var roadAddrPart1 = $(this).data().find('roadAddrPart1').text();
			var roadAddrPart2 = $(this).data().find('roadAddrPart2').text();
			var engAddr = $(this).data().find('engAddr').text();
			var jibunAddr = $(this).data().find('jibunAddr').text();
			var admCd = $(this).data().find('admCd').text();
			var rnMgtSn = $(this).data().find('rnMgtSn').text();
			var bdMgtSn = $(this).data().find('bdMgtSn').text();
			var zipNo = $(this).data().find('zipNo').text();
			var addrDetail = $("#addrDetail").val();

			var roadFullAddr = roadAddrPart1;
			if(addrDetail != "" && addrDetail != null){
				roadFullAddr += ", " + addrDetail;
			}
			if(roadAddrPart2 != "" && roadAddrPart2 != null){
				roadFullAddr += " " + roadAddrPart2;
			}

			opener.jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr,jibunAddr,zipNo,admCd, rnMgtSn, bdMgtSn);
			
			window.close();

		});

		$("#keyword").keydown(function (key) {
			if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
				$("#searchBtn").trigger("click");
		    }
		});

		//검색
		$("#searchBtn").click(function() {
			searchTxtTmp = $('#keyword').val();
			pageNoTmp = 1;
			searchList();
		});

		//초기 화면 세팅
		stepFn(1);

		//닫기
		$(".btn_cancel").click(function() {
			window.close();
		});

	});

	$(window).load(function() {
	  window.resizeTo( 700, 550 );
	});

</script>
</t:putAttribute>

<t:putAttribute name="contentAttr">
		<div style="margin-top: 10px;"></div>
		<div id="addrPopup">

            <!-- 검색 -->
            <div class="addr_title">
                주소검색
                <a href="javascript:void(0)" class="btn_cancel"><img src="/images/mcp/layer_cancel.png" alt="닫기"></a>
            </div>

            <div class="addr_search">
            	<input type="text" id="keyword" class="addr_search_input" placeholder="도로명주소, 건물명 또는 지번입력" title="도로명주소, 건물명 또는 지번입력">
				<button type="button" id="searchBtn" class="addr_search_btn">검색</button>
            	<div style="margin-top : 20px"></div>
            	<p class="addr_search_ex">
            		검색어 예시 : 도로명(반포대로 58), 건물명(독립기념관), 지번(삼성동 25)
            	</p>
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
									<input type="text" id="addrDetail" class="input_intable width_400px" placeholder="입력해주세요." title="상세주소를 입력해주세요." maxlength="120">
									<br/>예) 1202동 101호 or 종로빌딩 1층
								</td>
							</tr>
						</table>
					</div>

					<div class="text_align_center">
						<button type="button" id="setAddr2" class="btn_small_red">주소입력</button>
					</div>
				</div>
				<!-- step3-->


        </div>
</t:putAttribute>

</t:insertDefinition>