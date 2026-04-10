<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>postMessage</title>
</head>
<script type="text/javascript">
	
	function aa() {
		
	 	var width = 1200;
	   	var height = 700;
	   	var top  = 10;
	   	var left = 10;
	   	var url = "http://scndev.ktism.com:8081/test/child.jsp?scanId=aaaa";
	   	window.open(url, "_popup1", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);	
	   	
	   	
	}
	
</script>
<body>

<tr>
	<td>
		전달값:<input  type="text" id="result" name="result" />
		<input type="button" value="send" onclick="aa()"/>
	</td>
</tr>




</body>
</html>