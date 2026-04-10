/**
 * 
 */

 // facebook 공유하기
function facebookShare(){
	
	/*var url = window.document.location.href;
	var endPoint = url.indexOf("do");
	url = url.substring(0,(endPoint+2));*/
	var url = window.location.pathname + window.location.search;
/*	var param = $("#param").val();
	if(param){
		url = url + param;
	}
    */
    facebook_share(url);
}
// Line 공유하기
function lineShare(){
   /*var url = location.origin + location.pathname;
   var endPoint = url.indexOf("do");
	url = url.substring(0,(endPoint+2));*/
	var url = window.location.pathname + window.location.search;
/*    var param = $("#param").val();
	if(param){
		url = url + param;
	}*/
    line_share(url);
}
//
function kakaoShare() {
	var txt = $("title").text();
    /*var url = location.origin + location.pathname;
*/
	var url = window.location.pathname + window.location.search;
	var mobileUrl = "m" + url;
/*    var param = $("#param").val();
	if(param){
		url = url + param;
	}*/
	kakao_share('/resources/images/portal/common/share_logo.png', mobileUrl, url);
}

// twitter 공유하기
function twitterShare(){  
    var txt = $("title").text();
    var url = encodeURIComponent(window.document.location.href);
    
    twitter_share(url, txt);
}
// kakaostory 공유하기
function kakaostoryShare(){    
    var url = encodeURIComponent(window.document.location.href);
    
    kakaostory_share(url);
}