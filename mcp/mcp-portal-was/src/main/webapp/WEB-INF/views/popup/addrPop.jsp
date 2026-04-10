<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<script>



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


function goSubmit(param) {
	$('#zipcode').val('');
	$('#addr1').val('');
	$('#addr2').val('');
	$('#tmpAddr2').val('');
	$('#zipText').html('');
	$('#addr1Text').html('');
	$('.info_list_table:eq(0)').css('display','none');
	$('.info_list_table:eq(1)').css('display','none');
	$('.board_detail_table').css('display','none');
	$('#cButton').css('display','none');

	if(param) {
		$('#currentPage').val(param);
	} else {
		$('#currentPage').val(1)
	}

	if($('#tmpKey').val() == "") {
		alert("검색어를 입력해 주세요");
		$('#tmpKey').focus();
		return;
	}

	if (!checkSearchedWord($('#tmpKey').val())) {
		return ;
	}

	$('#keyword').val($('#tmpKey').val());

	try {

	    var varData = ajaxCommon.getSerializedData({
	        currentPage:$("#currentPage").val()
            ,countPerPage:$('#countPerPage').val()
            ,keyword:$('#keyword').val()
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
				//alert(data);
				//var xmlStr = data.returnXml;
				var xmlStr = data;
				//alert(xmlStr);
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
					$(xmlStr).find('juso').each(function() {
						//alert($(this).find('zipNo').text());
						htmlStr += '<tr>';
						htmlStr += '<td>'+index+'</td>';
						htmlStr += '<td><a href="javascript:setAddr1(\''+$(this).find('roadAddrPart1').text()+'\',\''+$(this).find('zipNo').text()+'\');">'+$(this).find('roadAddr').text() + '<br/>[지번]' + $(this).find('jibunAddr').text()+' '+$(this).find('bdNm').text()+'</a></td>';
						//htmlStr += '<td>'+$(this).find('roadAddrPart1').text() + '</td>';
						//htmlStr += '<td>'+$(this).find('roadAddrPart2').text() + '</td>';
						htmlStr += '<td>'+$(this).find('zipNo').text() + '</td>';
						htmlStr += '</tr>';
						index ++;
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
				}

				$('#searchList').html(htmlStr);
				//alert(totalCount + ";" + errorCode);
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
			                pageFirst = '<button type="button" class="btn_paging" onclick="goSubmit(1)">&lt;&lt;</button>';
						    pageLeft = '<button type="button" class="btn_paging" onclick="goSubmit('+ (firstPageNoOnPageList-1) +')">&lt;</button>';
			            } else {
			                pageFirst = '<button type="button" class="btn_paging" onclick="goSubmit(1)">&lt;&lt;</button>';
						    pageLeft = '<button type="button" class="btn_paging" onclick="goSubmit('+(currentPage-1)+')">&lt;</button>';
			            }
			        } else {
			           	pageFirst = '<button type="button" class="btn_paging">&lt;&lt;</button>';
					    pageLeft = '<button type="button" class="btn_paging">&lt;</button>';
			        }

				    var pageStr = ""
					for (var i = firstPageNoOnPageList; i <= lastPageNoOnPageList; ++i) {
			        	if (i == currentPage) {
			        		pageStr +="<button type='button' class='btn_paging active'>"+i+"</button>\n";
			            } else {
			            	pageStr +="<button type='button' class='btn_paging' onclick='goSubmit("+i+")'>"+i+"</button>\n";
			            }
				    }

				    var pageLast = '<button type="button" class="btn_paging" onclick="goSubmit(1)">&gt;&gt;</button>';
					var pageRight = '<button type="button" class="btn_paging" onclick="goSubmit(1)">&gt;</button>';

					if(totalPage > currentPage ){
			            if (lastPageNoOnPageList < totalPage) {
			                pageRight = '<button type="button" class="btn_paging" onclick="goSubmit('+(firstPageNoOnPageList + pageSize)+')">&gt;</button>';
							pageLast = '<button type="button" class="btn_paging" onclick="goSubmit('+totalPage+')">&gt;&gt;</button>';
			            } else {
							pageRight = '<button type="button" class="btn_paging" onclick="goSubmit('+(currentPage+1)+')">&gt;</button>';
							pageLast = '<button type="button" class="btn_paging" onclick="goSubmit('+totalPage+')">&gt;&gt;</button>';
			            }
			        } else {
			        	pageLast = '<button type="button" class="btn_paging">&gt;&gt;</button>';
						pageRight = '<button type="button" class="btn_paging">&gt;</button>';
			        }
					$('.paging div:eq(0)').html(pageFirst + pageLeft);
					$('.paging div:eq(1)').html(pageStr);
					$('.paging div:eq(2)').html(pageRight + pageLast);
				} else {
					$('.paging div:eq(0)').text('');
					$('.paging div:eq(1)').text('');
					$('.paging div:eq(2)').text('');
				}

				$('.info_list_table:eq(1)').css('display','block');
				$('.info_list_table:eq(0)').css('display','none');
			}
		});


	}
	catch(e){
	    alert(e.message);
	}
	finally {
	}

}


function setAddr1(addr, zip) {
	$('#zipcode').val(zip);
	$('#addr1').val(addr);
	$('#tmpAddr2').val('');
	$('#zipText').html($('#zipcode').val());
	$('#addr1Text').html($('#addr1').val());

	$('.info_list_table:eq(0)').css('display','none');
	$('.info_list_table:eq(1)').css('display','none');
	$('.board_detail_table').css('display','block');
	$('#cButton').css('display','block');
}

function setAddr2() {
	if($('#tmpAddr2').val().trim() == "") {
		alert("상세주소를 입력해 주세요");
		$('#tmpAddr2').focus();
		return;
	}

	$('#addr2').val($('#tmpAddr2').val());

	var roadFullAddr = '';
	var roadAddrPart1 = $('#addr1').val();
	var addrDetail = $('#addr2').val();
	var roadAddrPart2 = '';
	var engAddr = '';
	var jibunAddr = '';
	var zipNo = $('#zipcode').val();
	var admCd = '';
	var rnMgtSn = '';
	var bdMgtSn = '';
	
	
	if(opener != null) {
		opener.jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn);
		self.close();
	} else {
	    parent.jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn);
	}
}




$(document).ready(function() {
    $(document).on("keydown", "#tmpKey", function(e) {
        if (e.keyCode == 13) {
            goSubmit();
        }
    });
    
    $('#tmpKey').focus();
})



</script>

</head>
<body style="margin:0;padding:0;border:0;">
<form name="form" id="form" method="post">
	<input type="hidden" name="currentPage" id="currentPage" value="1" />
	<input type="hidden" name="countPerPage" id="countPerPage" value="10" />
 	<input type="hidden" name="confmKey" id="confmKey" value="${jusoKey }" />
	<input type="hidden" name="keyword" id="keyword" value="" />
</form>
<form name="rtnForm" id="rtnForm">
	<input type="hidden" name="zipcode" id="zipcode" value=""/>
	<input type="hidden" name="addr1" id="addr1" value=""/>
	<input type="hidden" name="addr2" id="addr2" value=""/>
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
								<input type="text" id="tmpAddr2" class="input_intable width_100" placeholder="입력해주세요." title="상세주소를 입력해주세요.">
								<br/>예) 1202동 101호 or 종로빌딩 1층
							</td>
						</tr>
					</table>
				</div>
				<div class="text_align_center" id="cButton" style="display:none">
					<button type="button" class="btn_small_red" onclick="setAddr2();">주소입력</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>