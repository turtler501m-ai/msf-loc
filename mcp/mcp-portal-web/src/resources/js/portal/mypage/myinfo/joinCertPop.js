
$(document).ready(function (){

});
//window.onbeforeprint = function() {
//	$("#issueNum").show();
//};
//
//window.onafterprint = function() {
//	$("#issueNum").hide();
//};


//function printBtn(){
//
//	var issueNum = $("#issueNum > span").text();
//
//	if(issueNum ==""){
//		alert(issueNum);
//		var productionDate = $("#productionDate option:selected").val();
//		var contractNum = $("#ctn option:selected").val();
//		var varData = ajaxCommon.getSerializedData({
//			contractNum : contractNum,
//			productionDate : productionDate
//	    });
//
//		ajaxCommon.getItem({
//            id:'joinCertAjax'
//            ,cache:false
//            ,url:'/mypage/joinCertAjax.do'
//            ,data: varData
//            ,dataType:"json"
//         }
//         ,function(jsonObj){
//        	 var resultCode = jsonObj.resultCode;
//        	 var message = jsonObj.message;
//        	 var seqSr = jsonObj.seqSr;
//        	 alert(seqSr);
//        	 if(resultCode !="00"){
//
//        		 MCP.alert(message,function(){
//        			 window.close();
//        		 });
//        	 } else {
//        		 $("#issueNum > span").text(seqSr);
//        		 $("#issueNum").show();
//        		 window.print();
//        	 }
//         });
//	} else {
//		$("#issueNum").show();
//		window.print();
//	}
//
//}