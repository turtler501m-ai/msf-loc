/**
 * 
 */


$(document).on("click", "#snsShareBut", function(){
		openPage('snsSharePop', '/m/snsSharePop.do', '')
	});

$(".c-tabs__button").on("click", function(){
	if($(this).attr("id") == 'monthlyM'){
		location.href = "/m/mstory/monthlyUsimList.do";
	}else{
		location.href = "/m/mstory/snsList.do";
	}
});


$(document).ready(function(){
	// if mstory
	if($("#status").val() == 'F'){
		$(".c-tabs--type1").hide();	
		$(".c-tabs__panel").addClass("u-mt--30");
		$(".c-tabs__panel").removeClass("c-tabs__panel");
	}
	
});