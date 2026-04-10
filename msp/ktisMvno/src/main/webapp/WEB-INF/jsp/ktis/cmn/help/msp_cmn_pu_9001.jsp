<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_cmn_pu_9001.jsp
	 * @Description : 도움말조회(팝업)
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 
	 * @author : 
	 * @Create Date : 
	 */
%>

<!-- Script -->
<script type="text/javascript">
	
	var menuId = getParams("menuId");
	
	mvno.cmn.ajax(ROOT + "/cmn/help/selectHelpMgmtList.json", {menuId:menuId}, function(resultData) {
		var totCnt = resultData.result.data.total;
		var menuNm = "";
		var html = "";
		
		if( totCnt > 0){
			menuNm = resultData.result.data.rows[0].menuNm;
			
			html += "<div class='page-header'><h1>" + menuNm + "</h1></div>";
			
			html += "<center>";
			
			for(var idx = 0; idx < totCnt; idx++){
				var fileId = encodeURI(encodeURIComponent(resultData.result.data.rows[idx].fileId));
				
				html += "<img src=/cmn/help/viewFile.json?fileId=" + fileId + " style='width:600px' >";
				html += "<br/>";
			}
			
			html += "<a hef='#' onClick='self.close();'><button class='btn btn-default'>닫기</button></a>"
			html += "</center>";
			
			$("body").prepend(html);
		}
	});
	
	function getParams(key){
		var _parammap = {};
		document.location.search.replace(/\??(?:([^=]+)=([^&]*)&?)/g, function () {
			function decode(s) {
				return decodeURIComponent(s.split("+").join(" "));
			}
			
			_parammap[decode(arguments[1])] = decode(arguments[2]);
		});
		
		return _parammap[key];
	}
</script>
