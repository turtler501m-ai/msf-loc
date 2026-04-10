//-------------------------------------------------------------------
// 맨 하단부에서... 공통으로 처리할 내용들
//-------------------------------------------------------------------
$(function() {

	//-----------------------------------------------------
	// 공용변수 PAGE 처리 (onOpen/onClose/onButtonClick)
	//-----------------------------------------------------
	if (typeof PAGE == 'undefined') {
		window.PAGE = {};
	}

	//-----------------------------------------------------
	// Page Header 영역 동적으로 생성
	//-----------------------------------------------------
	if (PAGE.title) {
		var html = "<div class='page-header'>";
		html += "<h1>" + PAGE.title + "</h1>";

		if (! PAGE.menuId) {
			var qs = mvno.cmn.qs2json();
			PAGE.menuId = qs['menuId'];	  // menuId 는 URL 호출시 넘겨준다.
		}
		if (PAGE.menuId) {
			// 2016-12-29, 헬프추가
			html += "<a href='#none' class='help'>HELP</a>";
			html += "<a href='#none' class='favor'>즐겨찾기 추가</a>";
		}
		if (PAGE.breadcrumb) {
			html += "<ul>";
			var  arr = PAGE.breadcrumb.split(">");
			for(var i = 0, len = arr.length; i < len; i++) {
				html += "<li>" + arr[i].trim() + "</li>";
			}
			html += "</ul>";
		}
		html += "</div>";

		if (! $("body .page-header")[0]) {
			$("body").prepend(html);
			$("body .favor").on("click", function() {
				//mvno.ui.notify("[임시] 즐겨찾기에 추가되었습니다.");
				mvno.cmn.ajax(ROOT + "/cmn/favrmenu/favrMenuInsert.json", { menuId: PAGE.menuId }, function() {
					mvno.ui.notify("NCMN0009");
					if(top.fnRefreshFavorite) {
						top.fnRefreshFavorite();	// 메인화면 즐겨찾기 Refresh
					}
				});
				return false;
			});
			
			// Help 화면
			$("body .help").on("click", function() {
				var menuId = PAGE.menuId;
				
				mvno.cmn.ajax(ROOT + "/cmn/help/selectHelpMgmtList.json", {menuId:menuId}, function(resultData) {
					var totCnt = resultData.result.data.total;
					
					if( totCnt > 0){
						window.open(ROOT + "/cmn/help/help.do?menuId="+menuId,"_help","width=700,height=600,scrollbars=yes, resizable=yes");
					}else{
						mvno.ui.alert("등록된 도움말이 없습니다.<br/>관리자에게 문의 하세요.");
					}
				});
				
				return false;
			});
		}
	}


	//-----------------------------------------------------
	// Parent 등에서 보낸 Message 수신 처리용
	//-----------------------------------------------------
	$("body").on("_MESSAGE_", function(e) {
		// 가장 많이 사용되는 REFRESH 는 따로 분리!!
		if (e.message.type == 'REFRESH') {
			if (PAGE.onRefresh) PAGE.onRefresh.call(PAGE, e.message.data);
		}
		else {
			if (PAGE.onMessage) {
				PAGE.onMessage.call(PAGE, e.message.type, e.message.data);
			}
		}
	});

	//-------------------------------------------------
	// iframe 으로 열린 경우
	// parent 에서 넘긴 paramter 확인해서 (_IFRAME_PARAM_)
	// PAGE.param 속성에 설정!!
	//-------------------------------------------------
	var iframe = mvno.ui.getIframe();
	if (iframe) {
		PAGE.param = parent.$(iframe).data("_IFRAME_PARAM_");
	}
	PAGE.param = PAGE.param || {};


	//-------------------------------------------------
	// onOpen 처리용
	//-------------------------------------------------
	if (PAGE.onOpen) {
		PAGE.onOpen.call(PAGE);
	} else { // onOpen 없으면... fnInit() 호출 (?)
		if (typeof window["fnInit"] == 'function') {
			window["fnInit"].call(this);
		}
	}

	//-------------------------------------------------
	// onClose 처리용
	//-------------------------------------------------
	$(window).on('beforeunload', function() {

		// 사용자가 정의한 onClose 부분을 먼저 확인하고..
		if (PAGE.onClose) {
			var ret = PAGE.onClose.call(PAGE);
			if(ret) return(ret);
		}

		// PAGE.changed 변수 확인!!
		if(PAGE.changed) {
			return "변경사항이 있습니다.";
		}

	});

	//-------------------------------------------------
	// IE 메모리 누수 방지위해...
	// PAGE 의 FORM, GRID 등 DHX 변수에 대한 detachAllEvents 처리
	//-------------------------------------------------
	$(window).on('unload', function() {
		for(var prop in PAGE) {
			var dhx = PAGE[prop];
			if (dhx instanceof dhtmlXGridObject  || dhx instanceof dhtmlXForm) {
				try { dhx.detachAllEvents(); }		// IE 경우 unload 시점에서 에러나는 경우도 있어 무시!
				catch(e) {}
			}
		}
	});

});
