/*
function pop_setCookie(name,value,expiredays) {
	var todayDate = new Date();
	todayDate.setDate(todayDate.getDate() + expiredays);
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

function pop_getCookie(name) {
	var nameOfCookie = name + "=";
	var x = 0

	while ( x <= document.cookie.length ) {
			var y = (x+nameOfCookie.length);
			if ( document.cookie.substring( x, y ) == nameOfCookie ) {
				if ( (endOfCookie=document.cookie.indexOf( ";",y )) == -1 )
					endOfCookie = document.cookie.length;
				return unescape( document.cookie.substring(y, endOfCookie ) );
			}
			x = document.cookie.indexOf( " ", x ) + 1;
			if ( x == 0 )
				break;
	}
	return "";
}

function pop_openLayerPop(popupSeq,layerId,frameId){
	if (pop_getCookie(popupSeq) != "done") {
		$('#'+frameId).css('width', $(window).width());
		$('#'+frameId).css('height', $(window).height()-200);
		$('#'+frameId).attr('src', '/popupDetail.do?popupSeq='+popupSeq);
		fn_mobile_layerOpen(layerId);
	}
}

function closeLayer(){
	fn_mobile_layerClose('mPopLayer0');
}

$(document).ready(function($) {
	if(typeof menuCode != 'undefined') {
//		if(menuCode=="SMMainWeb"){
//		    $.ajax({
//		        type : "POST",
//		        url : "/m/getPopupListAjax.do",
//		        data : { menuCode : menuCode, currentUrl : $(location).attr('pathname') },
//		        dataType : "json",
//		        timeout : 30000 ,
//		        success : function(message){
//		        	if(message.length > 0) {
//		        		var innerHtml = '';
//		        		innerHtml += '<div id="layerContent">';
//		        		innerHtml += '</div>';
//			        	$("body").append(innerHtml);
//		        		var layerId = 'mPopLayer';
//		        		var frameId = 'mPopLayerFrame';
//		        		for(var i = 0; i < message.length; i++) {
//		        			layerId = layerId+i;
//		        			frameId = frameId+i;
//		        			var innerFrameHtml = '';
//		        			innerFrameHtml += '	<div class="layer" id="'+layerId+'">';
//		        			innerFrameHtml += '		<div class="content_title">';
//		        			innerFrameHtml += '			공지사항';
//		        			innerFrameHtml += '			<img src="/resources/images/front/layer_cancel.png" class="btn_cancel" />';
//		        			innerFrameHtml += '		</div>';
//		        			innerFrameHtml += '		<div class="contnet_main sample_layer">';
//		        			innerFrameHtml += '			<iframe id="'+frameId+'" ></iframe>';
//		        			innerFrameHtml += '		</div>';
//		        			innerFrameHtml += '	</div>';
//			        		$("#layerContent").append(innerFrameHtml);
//			        		pop_openLayerPop(message[i].popupSeq,layerId,frameId);
//		        		}
//		        	}
//		        },
//		        error : function( obj ){
//		        }
//		    });
//		}
	}

});*/