;


$(document).ready(function(){

	var authResult = $("#authResult").val();
	var errMsg = $("#errMsg").val();
	if(authResult=="success"){
		alert("인증성공");
	} else {
		alert(errMsg);
	}
});




