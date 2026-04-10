


$(document).ready(function(){

	$(document).on("click",".viewMore",function(){
		var foldArea = $(this).parents(".foldArea");
		var orgnArea = foldArea.next(".orgnArea");
		foldArea.hide();
		orgnArea.show();
	});

	$(document).on("click",".viewFold",function(){
		var orgnArea = $(this).parents(".orgnArea");
		var foldArea = orgnArea.prev(".foldArea");
		foldArea.show();
		orgnArea.hide();
	});

});


function goReviewPaging(pageNo){
	getRequestReviewDataList(pageNo)
}

