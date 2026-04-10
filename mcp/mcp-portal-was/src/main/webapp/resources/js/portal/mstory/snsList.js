/**
 * 
 */
 $(".c-tabs__button").on("click", function(){
	if($(this).attr("id") == 'monthlyM'){
		location.href = "/mstory/monthlyUsimList.do";
	}else{
		location.href = "/mstory/snsList.do";
	}
});


$(document).ready(function(){
	// if mstory
	if($("#status").val() == 'F'){
		$(".c-tabs--type1").hide();	
	}
	
});