<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>postMessage</title>
</head>
<body>
<script type="text/javascript">
//<![CDATA[
 
   //var openUrl = 'http://prx.ktism.com:8180';
   var openUrl = 'http://msplocal.ktism.com:8080';
           
    bind(window, 'message', function (e) {
    	
        if (e.origin === openUrl) {
        	alert(e.data );
            document.getElementById('msg_list').innerHTML += '<li style="margin-top:4px;">' + e.data + '(' + e.origin + ' 도메인에서 메시지를 전송함)</li>';
        }
    });
 
    function sendMsg() {
        document.getElementById('sof').contentWindow.postMessage({}, openUrl);
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
    
    function test(scanId){
    	alert(scanId);
    }
 
    
    function aa() {
    	
	 	var width = 1200;
       	var height = 700;
       	var top  = 10;
       	var left = 10;
       	


       	//var url = "${scanSearchUrl}" + "?DBMSType="+DBMSType+"&CID="+CID+"&RGST_PRSN_ID="+userId+"&RGST_PRSN_NM="+userName+"&ORG_TYPE="+orgType+"&ORG_NM="+orgNm+"&ORG_ID="+orgId+parameters;
       	var url = openUrl + "/scan/childTest.jsp";
       //	var url = openUrl + "/test/childTest.jsp";
       	window.open(url, "_popup1", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);	

    	
    }
 
//]]>
</script>
<input type="text" id="requestMsg" />
<input type="button" value="postMessage" onclick="sendMsg()" />
<input type="button" value="button" onclick="aa()" />
 
<div id="text_container" style="width:100%;height:100%"><ul id="msg_list"></ul></div>
 
 <div><iframe id="sof" name="sof" src="http://prx.ktism.com:8180/test/childTest.jsp" width="1000" height="1000"></iframe></div> 
</body>
</html>