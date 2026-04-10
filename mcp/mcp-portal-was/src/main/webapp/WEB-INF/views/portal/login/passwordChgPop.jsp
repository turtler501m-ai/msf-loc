<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<t:insertDefinition name="layoutGnbDefault">

<t:putAttribute name="scriptHeaderAttr">
<script type="text/javascript">

// 쿠키 생성
function setCookie(cName, cValue, cDay){
    var expire = new Date();
    expire.setDate(expire.getDate() + cDay);
    cookies = cName + '=' + escape(cValue) + '; path=/ '; // 한글 깨짐을 막기위해 escape(cValue)를 합니다.
    if(typeof cDay != 'undefined') cookies += ';expires=' + expire.toGMTString() + ';';
    document.cookie = cookies;

    var uri = '${uri}';
    if(uri == '' || uri == null){
    	location.href= '/main.do';
    }else{
		location.href= uri;
    }
}

function goBack() {
    var uri = '${uri}';
    if(uri == '' || uri == null){
    	location.href= '/main.do';
    }else{
		location.href= uri;
    }
}

// 쿠키 가져오기
/* function getCookie(cName) {
    cName = cName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cName);
    var cValue = '';
    if(start != -1){
        start += cName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cValue = cookieData.substring(start, end);
    }
    return unescape(cValue);
} */


</script>
</t:putAttribute>

<t:putAttribute name="contentAttr">

<div id="member_container">
<a id= "container"></a>
	<div class="join_viewport">
		<h2 class="join_title">비밀번호 변경안내</h2>
		
			<div class="pop_3month">
				<img src="/resources/images/front/icon_pwchange.png"  alt="비밀번호변경안내"/>
				<div class="title">
					고객님께서는 <span class="font_weight_bold text_color_red">3개월 동안</span><br>
					<span class="font_weight_bold">비밀번호</span>를 <span class="font_weight_bold">변경</span>하지 않으셨습니다!					
				</div>
				<div class="subtitle">
					개인정보 보호를 위해 3개월마다 비밀번호를 변경해 주세요.
				</div>
				<div class="buttonlist">
					<button type="button" class="change" onclick="javascript:location.href='/mypage/updateForm.do?pwChg=1'">지금 변경하기</button>
					<button type="button" class="later" onclick="goBack();">나중에 변경하기</button>
					<button type="button" class="hide" onclick="setCookie('pwChgCookie', 'true', 14);">2주동안 안보이기</button>
					<!-- <input type="button" value="쿠키 보기" onclick="alert(getCookie('pwChgCookie'))"> -->
					<!-- <input type="button" value="쿠키 삭제" onclick="setCookie('test', '', -1)"> -->
				</div>
			</div>	
		     
	</div>
</div>
</t:putAttribute>
</t:insertDefinition>