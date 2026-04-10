<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script type="text/javascript">

	var searchTxtTmp = "";
	var pageNoTmp = "";

	//특수문자, 특정문자열(sql예약어의 앞뒤공백포함) 제거
	function checkSearchedWord(objVal){

		if(objVal.length >0){
			//특수문자 제거
			var expText = /[%=><]/ ;
			if(expText.test(objVal) == true){
				MCP.alert("특수문자를 입력 할수 없습니다.") ;
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
			    	MCP.alert("\"" + sqlArray[i]+"\"와(과) 같은 특정문자로 검색할 수 없습니다.");
					return false;
			    }
			}
		}
		return true ;
	}

	/**
	 * 주소 입력
	 * 한글, 숫자, 영문, 공백, 기본 특수문자( . - , ( ) / ) 총 6종
	 * 허용
	 */
	function cleanAddress(value) {
		if (!value) return "";

		// 허용 문자:
		// - 한글 자모 확장: \u1100-\u11FF
		// - 한글 자모:      \u3131-\u318E
		// - 완성형 한글:    \uAC00-\uD7A3
		// - 숫자/영문/공백/.,()/\\-#&+
		const allowed = /[\u1100-\u11FF\u3131-\u318E\uAC00-\uD7A30-9A-Za-z\s.,()#+\/-]/g;  //.,()/\\-#&+

		const matched = value.match(allowed);
		return matched ? matched.join("") : "";
	}

	function stepFn(step, gubun) {
		if(gubun == 0){
			$('.step' + step).show();
		}else{
			$('.step' + step).hide();
		}
	}

	function searchList() {

		if(searchTxtTmp == "") {
			MCP.alert("검색어를 입력해 주세요", function (){
				$('#keyword').focus();
			});
			return;
		}

		if (!checkSearchedWord(searchTxtTmp)) {
			return ;
		}

		var varData = ajaxCommon.getSerializedData({
			currentPage : pageNoTmp
	        ,countPerPage : 10
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

				$('#addrCnt').html('&nbsp;('+numberWithCommas(totalCount)+'건)');

				var htmlStr = "";
				if(totalCount > 0 && errorCode == "0") {

					$('#searchList').empty();
					$(xmlStr).find('juso').each(function() {
						htmlStr = "";
						htmlStr += '<li class="addr-result__item">';
						htmlStr += '    <a href="javascript:void(0);" class="setAddr1"> ';
						htmlStr += '        <strong class="addr-result__number">'+ $(this).find('zipNo').text() + '</strong>';
						htmlStr += '        <p class="addr-result__text">';
						htmlStr += '            <span class="c-text-label c-text-label--mint">도로명</span>';
						htmlStr += 				$(this).find('roadAddr').text();
						htmlStr += '        </p>';
						htmlStr += '        <p class="addr-result__text">';
						htmlStr += '            <span class="c-text-label c-text-label--gray">지번</span>';
						htmlStr += 				$(this).find('jibunAddr').text()+' '+$(this).find('bdNm').text();
						htmlStr += '        </p>';
						htmlStr += '    </a>';
						htmlStr += '</li>';
						index ++;
						
						$('#searchList').append(htmlStr);
						$('#searchList li:last a').data($(this));
					});
					
					stepFn(1, 1);
				} else {

					htmlStr += '<div class="c-nodata">';
					htmlStr += '    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
					htmlStr += '    <p class="c-nodata__text">검색 결과가 없습니다.</p>';
					htmlStr += '</div>';
					
					$('#searchList').html(htmlStr);
					$(".c-paging").html('');
					stepFn(1, 0);
					stepFn(1, 1);
				}
				
				stepFn(3, 1);

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
			            	pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
			            	pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+ (firstPageNoOnPageList-1) +');"><span>이전</span></a>';
			            } else {
			            	pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
			            	pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(currentPage-1)+');"><span>이전</span></a>';
			            }
			        } else {
		            	pageFirst = '<a class="c-button is-disabled" href="javascript:void(0);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
		            	pageLeft = '<a class="c-button is-disabled" href="javascript:void(0);"><span>이전</span></a>';
			        }

				    var pageStr = ""
					for (var i = firstPageNoOnPageList; i <= lastPageNoOnPageList; ++i) {
			        	if (i == currentPage) {
			        		pageStr += '<a class="c-paging__anchor c-paging__anchor--current c-paging__number" href="javascript:void(0);"><span class="c-hidden">현재 페이지</span>' + i + '</a>';
			            } else {
			        		pageStr += '<a class="c-paging__anchor c-paging__number" href="javascript:void(0);" onclick="goSubmit(' + i + ')"><span class="c-hidden">현재 페이지</span>' + i + '</a>';
			            }
				    }

				    var pageLast = '';
					var pageRight = '';

					if(totalPage > currentPage ){
			            if (lastPageNoOnPageList < totalPage) {
			            	pageRight = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(firstPageNoOnPageList + pageSize)+');"><span>다음</span></a>';
			            	pageLast = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
			            } else {
			            	pageRight = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(currentPage+1)+');"><span>다음</span></a>';
			            	pageLast = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
			            }
			        } else {
			        	pageRight = '<a class="c-button is-disabled" href="javascript:void(0);"><span>다음</span></a>';
		            	pageLast = '<a class="c-button is-disabled" href="javascript:void(0);"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
			        }

					var pagingHtml = "";
					pagingHtml += pageFirst;
					pagingHtml += pageLeft;
					pagingHtml += pageStr;
					pagingHtml += pageRight;
					pagingHtml += pageLast;
					
					if(totalPage > 1){
						$(".c-paging").html(pagingHtml);
					}else{
						$(".c-paging").html('');
						
					}
				}

				stepFn(2, 0);

	       	}

		 });

	}

	function goSubmit(pageNo) {
		pageNoTmp = pageNo;
		searchList();
	}
	
	function addrInChk(key){
		
		if($.trim($('#addrDetail').val()) != ''){
			$('#setAddr2').removeClass('is-disabled');
		}else{
			$('#setAddr2').addClass('is-disabled');
		}
	}

	$(document).ready(function() {
		
		//주소선택
		$(document).on("click", ".setAddr1", function() {
			var data = $($(this).data());
			var roadAddrPart1 = data.find('roadAddrPart1').text();
			var zipNo = data.find('zipNo').text();
			$("#setAddr2").data(data);

			$("#addr1Text").text(roadAddrPart1);
			$("#zipText").text(zipNo);
			$("#addrDetail").val("");
			stepFn(2, 1);
			stepFn(3, 0);
		});

		//주소입력
		$("#setAddr2").click(function() {

			if($.trim($('#addrDetail').val()) == "") {
				MCP.alert("상세주소를 입력해 주세요.", function (){
					$('#addrDetail').focus();
				});
				return;
			}

			var data = $(this).data();
			var roadAddrPart1 = data.find('roadAddrPart1').text();
			var roadAddrPart2 = data.find('roadAddrPart2').text();
			var engAddr = data.find('engAddr').text();
			var jibunAddr = data.find('jibunAddr').text();
			var admCd = data.find('admCd').text();
			var rnMgtSn = data.find('rnMgtSn').text();
			var bdMgtSn = data.find('bdMgtSn').text();
			var zipNo = data.find('zipNo').text();
			var addrDetail = cleanAddress($("#addrDetail").val());

			var roadFullAddr = roadAddrPart1;
			if(addrDetail != "" && addrDetail != null){
				roadFullAddr += ", " + addrDetail;
			}
			if(roadAddrPart2 != "" && roadAddrPart2 != null){
				roadFullAddr += " " + roadAddrPart2;
			}

			jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr,jibunAddr,zipNo,admCd, rnMgtSn, bdMgtSn , data);
			$('.c-icon--close').trigger('click');

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
		stepFn(1, 0);

		//닫기
		$(".btn_cancel").click(function() {
			window.close();
		});
		
		$("#addrDetail").keydown(function (key) {
			if($('#addrDetail').val() != '' && key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
				$("#setAddr2").trigger("click");
		    }
		});

	});

	window.addEventListener("load", function(e) {
	  window.resizeTo( 700, 550 );
	});
	
	


</script>
	<div class="c-modal__content">
		<div class="c-modal__header">
			<h1 class="c-modal__title" id="search-info-title">주소검색</h1>
			<button class="c-button" data-dialog-close>
				<i class="c-icon c-icon--close" aria-hidden="true"></i> <span
					class="c-hidden">팝업닫기</span>
			</button>
		</div>
		<div class="c-modal__body">
			<div class="content-wrap">
				<div class="c-form c-form--search">
					<div class="c-form__input">
						<input class="c-input" type="text" id="keyword" placeholder="도로명주소, 건물명 또는 지번입력" title="도로명주소, 건물명 또는 지번입력" maxlength="20" autocomplete="off">
						<!--[2022-03-07] 검색버튼 디자인 변경-->
						<button class="c-button c-button--search-bk" id="searchBtn">
							<span class="c-hidden">검색어</span> <i class="c-icon c-icon--search-bk" aria-hidden="true"></i>
						</button>
						<button class="c-button c-button--reset">
							<span class="c-hidden">초기화</span> <i class="c-icon c-icon--reset" aria-hidden="true"></i>
						</button>
					</div>
				</div>
				<!-- case1 - 주소 검색 초기-->
				<h3 class="c-heading c-heading--type4 step1">검색어 예시</h3>
				<ul class="search-ex step1">
					<li class="search-ex__item">
						<strong class="search-ex__title">도로명 + 건물번호 입력</strong>
						<p class="search-ex__desc">예) 반포대로 58</p>
					</li>
					<li class="search-ex__item">
						<strong class="search-ex__title">건물명 입력</strong>
						<p class="search-ex__desc">예) 독립기념관</p>
					</li>
					<li class="search-ex__item">
						<strong class="search-ex__title">지번</strong>
						<p class="search-ex__desc">예) 삼성동 25</p>
					</li>
				</ul>
				<hr class="c-hr c-hr--type2 step1">
				<b class="c-text c-text--type3 step1"> 
					<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
				</b>
				<ul class="c-text-list c-bullet c-bullet--dot step1">
					<li class="c-text-list__item u-co-gray">건물명은 각 자치단체에서 등록한 경우 조회가 가능합니다.</li>
					<li class="c-text-list__item u-co-gray">화면 상단의 상세검색을 이용하시면 보다 정확한 결과를 얻으실 수 있습니다.</li>
				</ul>
				<!-- 
				<div class="c-button-wrap">
					<a class="c-button c-button--full c-button--primary" href="javascript:void(0);" id="searchBtn">검색</a>
				</div>
				-->
				<!-- //-->
				<!-- case2 - 주소 검색 후-->
				<h3 class="c-heading c-heading--type4 step2" style="display:none;">
					도로명주소 검색 결과<span class="u-co-mint" id="addrCnt">&nbsp;</span>
				</h3>
				<ul class="addr-result step2" id="searchList" style="display:none;"></ul>
				<nav class="c-paging step2" aria-label="검색결과" style="display:none;"></nav>
				<!-- case3 - 상세 주소 입력-->
				<ul class="addr-result step3" style="display:none;">
					<li class="addr-result__item bd-none">
						<a href="#"> 
							<strong class="addr-result__number" id="zipText"></strong>
							<p class="addr-result__text">
								<span class="c-text-label c-text-label--mint">도로명</span>
								<span id="addr1Text"></span>
							</p>
						</a>
					</li>
				</ul>
				<div class="c-form step3" style="display:none;">
					<div class="c-form__group" role="group" aria-labeledby="inpG">
						<input class="c-input" type="text" id="addrDetail" placeholder="상세주소 (예 1202동 101호 / 종로빌딩 1층)" title="상세주소 (예 1202동 101호 / 종로빌딩 1층)" onkeyup="addrInChk();" maxlength="100" autocomplete="off">
					</div>
				</div>
				<div class="c-button-wrap step3" style="display:none;">
					<a class="c-button c-button--full c-button--primary is-disabled" href="javascript:void(0);" id="setAddr2">주소입력</a>
				</div>
			</div>
			<!-- //-->
		</div>
	</div>
