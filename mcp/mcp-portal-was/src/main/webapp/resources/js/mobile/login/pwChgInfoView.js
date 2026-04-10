var updatePwChgInfoNoShowAjax = function (){
	var varData = {};
    $.ajax({
		type:'post',
	    url : '/m/updatePwChgInfoNoShowAjax.do',
	    data : varData,
	    dataType : 'xml',
		error: function(jqXHR, textStatus, errorThrown){
			return;
		},
		success:	function(data){
			if(data.resultCd == '0000'){
				location.href='/m/main.do';
			}else if(data.resultCd == '-1'){
				location.href = '/m/loginForm.do';
			}else{
				MCP.alert(data.msg);
			}
		}
	});
}
