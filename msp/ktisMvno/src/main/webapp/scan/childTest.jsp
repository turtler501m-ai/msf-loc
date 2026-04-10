<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>postMessage</title>
</head>
<body> 
<script type="text/javascript">
//<![CDATA[
           
 	<%
 		request.setCharacterEncoding("UTF-8");
 		String scanId = request.getParameter("scanId");
 		if(scanId != null && !"".equals(scanId)){
 	%>
 	<%
 		}
 	%>
 
    /* bind(window, 'message', function (e) {
        if (e.origin === 'http://msplocal.ktism.com:8080') {
        	alert('test');
            document.getElementById('msg_list').innerHTML += '<li style="margin-top:4px;">' + e.data + '(' + e.origin + ' 도메인에서 메시지를 전송함)</li>';            
        }
    }); */
 
    function sendMsg() {
    	alert('tseee');
    	location.href = "http://msplocal.ktism.com:8080/scan/childTest.jsp?scanId="+document.getElementById('scanId').value;
        //window.opener.postMessage(document.getElementById('responseMsg').value, 'http://msplocal.ktism.com:8080');
    }
 
    // 이벤트 할당
    function bind(elem, type, handler, capture) {
        type = typeof type === 'string' && type || '';
        handler = handler || function () { ; };
 
        if (elem.addEventListener) {
            elem.addEventListener(type, handler, capture);
        }
        else if (elem.attachEvent) {
            elem.attachEvent('on' + type, handler);
        }
 
        return elem;
    };
 
 
//]]>
</script>
<input type="text" id="responseMsg" />
<input type="button" value="serMessage" onclick="sendMsg()" />
 <input type="text" id="scanId" />
 <input type="text" id="shopCd" />
 
 
 
<div id="text_container" style="width:100%;height:100%"><ul id="msg_list"></ul></div>
</body>
</html>