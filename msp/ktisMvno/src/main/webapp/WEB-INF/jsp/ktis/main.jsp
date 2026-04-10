<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.ktis.msp.base.login.LoginInfo" %>
<%@ page import="egovframework.rte.fdl.property.EgovPropertyService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	LoginInfo loginInfo = new LoginInfo(request, response);
	String passChange = StringUtils.defaultString((String)request.getParameter("passChange"),"");
	
%>
<style type="text/css">
body{max-width:100%;height:100%;padding:0 !important;}
</style>
<!-- 화면 배치 -->
<div id="wrap">
	<div id="container">
		<!-- 로고, 사용자 메뉴, 메뉴 -->
		<div class="left_wrap" id="GNB">
			<h1><img src="../images/logo_2025.png" width="198px" height="78px" alt="kt M mobile" /></h1>
			<!-- 사용자메뉴 -->
			<h2 class="blind">
				사용자메뉴
			</h2>
			<div class="lnb_user">

				<%
					if ( loginInfo.isLogin() )
					{
				%>
					<dl>
						<dt> 이름 </dt>
						<dd><%=loginInfo.getUserName()%></dd>
						<dt> 조직 </dt>
						<dd title="<%=loginInfo.getUserOrgnNm()%>"> <%=loginInfo.getUserOrgnNm()%> </dd>
						<dt> 시간 </dt>
						<dd> <div id="ViewTimer"></div> </dd>
					</dl>
					<div class="ex">
						<a href="#none" id="logout" class="btn_logout">로그아웃</a>
						<a href="#none" id="usrName" class="btn_my">개인정보</a>
						<a href="#none" id="timeInit" class="btn_clock">연장</a>
					</div>
				<%
					}else
					{
				%>
					<dl>
						<dt> 이름 </dt>
						<dd> &nbsp;</dd>
						<dt> 조직 </dt>
						<dd> &nbsp; </dd>
						<dt> &nbsp; </dt>
						<dd> &nbsp; </dd>
					</dl>
					<div class="ex">
						<a href="#none" id="logout" class="btn_logout">로그아웃</a>
						<a href="#none" id="usrName" class="btn_my">개인정보</a>
						<a href="#none" id="timeInit" class="btn_clock">연장</a>
					</div>
				<%
					}
				%>


			</div>
			<!-- //사용자메뉴 -->
			<!-- 메뉴 -->
			<h2 class="blind">
				주메뉴
			</h2>
			<div id="mainAcc" class="gnb_mn_wrap">
			</div>
		</div>
		<!-- //메뉴 -->
		<div class="mn_close" id="gnbClose">
			<a href="#none"><img src="../images/btn_close.gif" alt="메뉴닫기" /></a>
		</div>
		<div class="mn_open" id="gnbOpen" style="display:none">
			<a href="#none"><img src="../images/btn_open.gif" alt="메뉴열기" /></a>
		</div>
		<!-- //로고, 사용자 메뉴, 메뉴 -->
		<!-- 콘텐츠 -->
		<div id="contents" class="contents">
			<!-- 공통 탭 -->
			<div id="mainTabbar" class="ui_tabbar" ></div>
			<!-- 탭 전체닫기 -->
			<a href="#none" id="btnCloseAllTab" title="탭 전체닫기">탭 전체닫기</a>
		</div>
		<!-- //콘텐츠 -->
	</div>
	<!-- footer -->
	<div id="footer_wrap">
		<b>
			Copyright(c) 2015
			<span>
				kt M mobile INC.
			</span>
			All rights reserved.
		</b>
	</div>
	<!-- //footer -->
</div>

<!-- Script -->
<script type="text/javascript">

	var mainAcc, mainTabbar;
	var tree1, tree2;

	// JSP 에서 Javascript 로 넘겨줄 변수는 여기에 한꺼번에 설정!
	var JSP = {
		passChange : "<%=passChange%>"
		, timeout : "<%=loginInfo.getTimeout()%>"
	};
	
	var timeout = JSP.timeout;
	
	// PAGE 설정
	var PAGE = {

		onOpen: function() {

			mainAcc = new dhtmlXAccordion({
				parent: "mainAcc",
				items: [
				  { id: "a1", text: "메뉴" },
					  { id: "a2", text: "즐겨찾기" }
				]
			});

			//---------------------------------------------------
			// TreeMenu 생성
			//---------------------------------------------------
			tree1 = mainAcc.cells("a1").attachTree();
			tree1.setImagePath(ROOT + "/lib/dhtmlx/codebase/imgs/dhxtree_skyblue/");

			tree1.loadJSON(ROOT + "/cmn/menu/menuPrgmMstList.json");

			tree1.attachEvent("onClick", function(id) { // onSelect 대신 onClick 으로!
				fnClickTreeNode(this, id);
			});


			//---------------------------------------------------
			// 즐겨찾기 목록
			//---------------------------------------------------
			mainAcc.cells("a2").attachHTMLString('<div class="delFavorite"><button id="_btnDelFavorite" type="button" class="btn btn-default">삭제</button></div><div id="_favorite_" class="dhxtree_dhx_skyblue"></div>');

			tree2 = new dhtmlXTreeObject("_favorite_","100%","100%",0);
			tree2.setImagePath(ROOT + "/lib/dhtmlx/codebase/imgs/dhxtree_skyblue/");

			fnRefreshFavorite();

			tree2.attachEvent("onClick", function(id) { // onSelect 대신 onClick 으로!
				fnClickTreeNode(this, id);
			});

			$("#_btnDelFavorite").on("click", function() {

				var tid = tree2.getSelectedItemId();
				if (! tid) {
					mvno.ui.alert("선택한 데이터가 없습니다.");
					return;
				}
				var url = tree2.getUserData(tid, "url");
				var menuId = tree2.getUserData(tid, "menuId");

				mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/cmn/favrmenu/favrMenuDelete.json", { menuId: menuId }, function() {
					mvno.ui.notify("NCMN0003");
					tree2.deleteItem(tid);          // 굳이 Refresh 하지 않아도 됨 (Node 만 삭제)
				})

			});


			//---------------------------------------------------
			// Tabbar 생성
			//---------------------------------------------------
			mainTabbar = new dhtmlXTabBar({
				parent: "mainTabbar",
				close_button: true,
				tabs: []
			});

			mainTabbar.attachEvent("onTabClick", function(id, lastId) {     // onSelect 에서 하면 안됨
				tree1.clearSelection(); // 현재 선택한 TreeNode Clear (메뉴-탭 Sync 혼란 방지용)
				tree2.clearSelection(); // 현재 선택한 TreeNode Clear (즐겨찾기-탭 Sync 혼란 방지용)
			});

			mainTabbar.attachEvent("onTabClose", function(id) {     // onSelect 에서 하면 안됨
				tree1.clearSelection(); // 현재 선택한 TreeNode Clear (메뉴-탭 Sync 혼란 방지용)
				tree2.clearSelection(); // 현재 선택한 TreeNode Clear (즐겨찾기-탭 Sync 혼란 방지용)

				//--------------------------------------------
				// IE 에서 iframe 제거시 메모리 누수를 보완책
				// - mvno_footer 에서 unload 시 detachAllEvents
				var ifr = mainTabbar.tabs(id).getFrame();
				if (ifr)  ifr.src = "about:blank";
				//--------------------------------------------

				return true;
			});


			//------------------------------------------------------
			// gnbClose, gnbOpen 처리  - 좌측메뉴 영역 닫기/열기
			//------------------------------------------------------
			$("#gnbClose").on("click", function() {
				$("#GNB, #gnbClose").hide();
				$("#gnbOpen").show();
				return false;
			});
			$("#gnbOpen").on("click", function() {
				$("#gnbOpen").hide();
				$("#GNB, #gnbClose").show();
				return false;
			});

			//------------------------------------------------------
			// 탭 전체닫기
			//------------------------------------------------------
			$("#btnCloseAllTab").on("click", function() {

				if (mainTabbar.getNumberOfTabs() == 0)  return false;   // #none 안나오도록

				// 실제 탭을 닫는 방식
				mvno.ui.confirm("전체 탭을 닫으시겠습니까?", function() {

					// mainTabbar.clearAll(); // clearAll() 하면 onTabClose 이벤트를 타지 못한다..
					mainTabbar.forEachTab(function(tab) {
						mainTabbar.callEvent("onTabClose", [tab._idd]);     // onTabClose 처리 활용하도록!
						tab.close();
					});
				});

				// 전체를 Refresh 하는 방식  - 향후, 메모리,성능 문제 발생시 대안으로 Refresh 처리
				//mvno.ui.confirm("전체 탭을 닫으면 화면이 초기화됩니다. <br/><br/>전체 탭을 닫으시겠습니까?", function() {
					//top.window.location.href = "/";
				//});
				return false;   // #none 안나오도록
			});


			//------------------------------------------------------
			// 로그아웃 처리
			//------------------------------------------------------
			$("#logout").on("click", function() {
				mvno.ui.confirm("로그아웃하시겠습니까?", function(){

					mvno.cmn.ajax(ROOT + "/cmn/login/logout.do", {}, function(result) {
						if(mvno.cmn.isEmpty(result) || ! result.result || ! result.result.code) {
							mvno.ui.alert("에러!! 수신 데이터 형식에 문제가 있습니다.");
							return;
						}
						if (result.result.code == "SESSION_FINISH") {
							mvno.ui.notify("로그아웃되었습니다");
							top.location.href = ROOT + "/main.do";

							//mvno.ui.alert("로그아웃되었습니다.", null, function() {
								//top.location.href = ROOT + "/main.do";
							//});
						}
						else {
							mvno.ui.alert("로그아웃처리에 문제가 있습니다.");
						}
					}, { resultTypeCheck: false })


				});


				return false;   // #none 안나오도록
			});


			//------------------------------------------------------
			// 사용자 정보 수정
			//------------------------------------------------------
			$("#usrName").on("click", function() {
				mvno.ui.popupWindow(ROOT + "/org/userinfomgmt/usrModifyPopup.do", "사용자정보수정", '700', '370');
				return false;   // #none 안나오도록
			});

			//------------------------------------------------------
			// 사용자정보수정 바로 띄우기
			//------------------------------------------------------
			if (JSP.passChange == "Y")  {
				$("#usrName").trigger("click");
			}

			//------------------------------------------------------
			// 첫번째 탭 자동 생성 (향후, 공지사항용 등)
			//------------------------------------------------------
			// Sample 은 조직관리로..
			fnGo("/cmn/board/notice.do", "공지사항", "MSP1000010");
			
			//------------------------------------------------------
			// 타임아웃 연장
			//------------------------------------------------------
			$("#timeInit").on("click", function() {
				mvno.cmn.ajax(ROOT + "/cmn/loginTime/sessTime.json", {}, function(result) {
					if(mvno.cmn.isEmpty(result) || ! result.result || ! result.result.code) {
						mvno.ui.alert("에러!! 수신 데이터 형식에 문제가 있습니다.");
						return;
					}
					if(result.result.code == "OK") {
						timeout = JSP.timeout;
					}
					else if(result.result.code == "SESSION_FINISH") {
						top.location.href = ROOT + "/main.do";
					}
				}, { resultTypeCheck: false })
				return false;   // #none 안나오도록
			});
		}

	};


	//-----------------------------------------------------------
	// Tree Node Click 이벤트 Handler (메뉴Tree 및 즐겨찾기 Tree 공용)
	//-----------------------------------------------------------
	function fnClickTreeNode(tree, id) {

		var url = tree.getUserData(id, "url");
		var menuId = tree.getUserData(id, "menuId");

		var ibsheetYn = tree.getUserData(id, "ibsheetYn");

		var title = tree.getItemText(id);
		if (url) {
			if(ibsheetYn == "Y") {
				//fn_openIbsheetUrl(url);
				fnCsAppOpen();
			} else {
				fnGo(url, title, menuId);
			}

		}
		else {  // Click 으로 펼침/닫힘 지원
			if (tree.getOpenState(id) == 1) {       // OPEN 상태 (1)
				tree.closeItem(id);
			}
			else {                                  // CLOSE 상태 (-1)
				tree.openItem(id);
			}
		}
	}


	// ibsheet 창 열기 (script 중복오류 피하기 위해 신규생성)
	function fn_openIbsheetUrl(url) {

		//top.mvno.ui.popupWindowIbsheet("${ibsheetUrl}/mspMain.do?url=" + url, "M mobile", 1250, 890);

		var x,y, popx, popy, l;

		//팝업창 가로, 세로 크기
		popx=1230;
		popy=860;

		//정중앙에 위치하도록하기
		if (self.innerHeight) { // IE 외 모든 브라우저
			x = (self.innerWidth) / 2;
			y = (self.innerHeight) / 2;
		} else if (document.documentElement && document.documentElement.clientHeight) { // IE >= 6
			x = (document.documentElement.clientWidth) / 2;
			y = (document.documentElement.clientHeight) / 2;
		} else if (document.body) { // IE < 6
			x = (document.body.clientWidth) / 2;
			y = (document.body.clientHeight) / 2;
		}

		// 창의 크기만큼 위치 재조정.
		x=parseInt(x-(popx/2));
		y=parseInt(y-(popy/2));

		var style = ",toolbar=no,status=no,directories=no,scrollbars=yes,location=no,resizable=no,border=0,menubar=no";

		window.open("${ibsheetUrl}/mspMain.do?url="+url,"Mmobile","width="+popx+",height="+popy+",left="+x+",top="+y+style);

		timeout = JSP.timeout;
	}

	//-----------------------------------------------------------
	// 메뉴 선택시 해당화면의 이동 (Tab 생성 or 이동)
	//-----------------------------------------------------------
	function fnGo(url, title, menuId) {

		if (mainTabbar.tabs(url)) {
			mainTabbar.tabs(url).setActive(); // 기존에 있는 탭을 active 로!
			return;
		}

		mainTabbar.addTab(url, title, null, null, true);
		mainTabbar.tabs(url).attachURL(url + "?menuId=" + menuId);
		
		timeout = JSP.timeout;
	}

	//-----------------------------------------------------------
	// 즐겨찾기 Refresh
	//-----------------------------------------------------------
	function fnRefreshFavorite() {
		tree2.deleteChildItems(0);
		tree2.loadJSON(ROOT + "/cmn/favrmenu/favrMenuList.json");
	}
	
	//-----------------------------------------------------------
	// 잔여시간
	//-----------------------------------------------------------
	function logoutTimer() {	// 1초씩 카운트
		m = Math.floor(timeout / 60) + "분 " + (timeout % 60) + "초";	// 남은 시간 계산
		var msg = "<font color='red'>" + m + "</font>";
		document.all.ViewTimer.innerHTML = msg;		// div 영역에 보여줌 
		timeout--;					// 1초씩 감소
		
		if(timeout < 0) {			// 시간이 종료 되었으면..
			clearInterval(tid);		// 타이머 해제
		}
	}
	
	//-----------------------------------------------------------
	// CTI 상담화면 OPEN
	//-----------------------------------------------------------
	function fnCsAppOpen(){
		var action ="https://cs.ktmmobile.com/KTMM/conCtiap/serviceConctiap.do?magtid=" + encodeURIComponent("<%=loginInfo.getEncUserId()%>");
		var pop = window.open(action, "dtl","width=1600, height=800");
		pop.focus();
	}
	
	window.onload = function TimerStart(){ tid=setInterval('logoutTimer()',1000) };

	
</script>
