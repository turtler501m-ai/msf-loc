;

$(document).ready(function(){

    var rtnObj = {
         authType: ""
        ,reqSeq: ""
        ,resSeq: ""
    }

    $("#ifrHidden").attr("src","http://nid.naver.com/nidlogin.logout");
    
	var authResult = $("#authResult").val();
	var errMsg = $("#errMsg").val();
	if(authResult=="success"){
        rtnObj.authType = $("#authType").val();
        rtnObj.reqSeq = $("#reqSeq").val();
        rtnObj.resSeq = $("#resSeq").val();

        if(opener != null) {

            var resultMsg = opener.fnNiceCert(rtnObj) ;
            if (resultMsg == "1") {
                alert("본인인증에 성공 하였습니다.");
                self.close();
            } else {
                if (resultMsg != null) alert(resultMsg);
                self.close();
            }
        } else {
            parent.fnNiceCert(rtnObj);
        }
        
	} else {
		alert(errMsg);
		self.close();
	}
	
	
	
});




