function searchOrderView(key) {
		
	var varData = ajaxCommon.getSerializedData({		
		requestKey : key
	});
	$.ajax({
			url : '/m/order/orderView.do',
			type : 'post',
			dataType : 'json',
			data : varData,
			async : false,
			success : function(data) {
				var resultCode = data.RESULT_CODE;
				if(resultCode == "00000") {
					modalBodyHtml(data.view);	
				}
			}
	});

}
