
// facebook 공유하기
function facebook_share(link){
	//var v_link = $(location).attr('protocol') +"//"+$(location).attr('host');
	var v_link = "https://www.ktmmobile.com";  
	var v_shareLink = v_link + link;
	
    var snsUrl = "https://www.facebook.com/sharer/sharer.php?u="+encodeURIComponent(v_shareLink);
    
    var popup= window.open(snsUrl, "_snsPopupWindow", "width=500, height=500, menubar=yes, status=yes, toolbar=yes, resizable=yes");
    popup.focus();
}

// Line 공유하기
function line_share(txt, link){    
	//var v_link = $(location).attr('protocol') +"//"+$(location).attr('host');
	var v_link = "https://www.ktmmobile.com";  
	var v_shareLink = v_link + link;
	
	console.log("v_link : ", v_link);
	
    var snsUrl = "https://social-plugins.line.me/lineit/share?url="+encodeURIComponent(v_shareLink)+"&text="+encodeURIComponent(txt);
    
    var popup= window.open(snsUrl, "_snsPopupWindow", "width=500, height=500, menubar=yes, status=yes, toolbar=yes, resizable=yes");
    popup.focus();
}

// Kakao 공유하기
//function kakao_share(shareText, url) {
function kakao_share(img, mobileLink, webLink) {
	//var v_link = $(location).attr('protocol') +"//"+$(location).attr('host');
	var v_link = "https://www.ktmmobile.com";
	var v_img = v_link + img;
	var v_mobileLink = v_link + mobileLink;
	var v_webLink = v_link + webLink;
	
	// 사용할 앱의 JavaScript 키 설정 ,본인 자바스크립트 API KEY 입력
	if(!Kakao.isInitialized()){
		Kakao.init('e302056d1b213cb21f683504be594f25'); // 운영
	}

	Kakao.Link.sendDefault({ 
		objectType : 'feed', 
		content : { 
			title : '', 
			description : '', 
			imageUrl : v_img, 
			link : { 
				mobileWebUrl : v_mobileLink, 
				webUrl : v_webLink, 
			}, 
		},
		buttons : [ 
			{ 
				title : '자세히 보기', 
				link : { 
					mobileWebUrl : v_mobileLink, 
					webUrl : v_webLink, 
				}, 
			}, 
		],
	});
}


function kakaoShareV2(obj) {
	// 사용할 앱의 JavaScript 키 설정 ,본인 자바스크립트 API KEY 입력
	if(!Kakao.isInitialized()){
		Kakao.init('e302056d1b213cb21f683504be594f25'); // 운영
	}
	//Kakao.Share.createDefaultButton(obj);
	Kakao.Share.sendDefault(obj);
}

// 현재 url 복사하기
function clip() {
	var url = '';
	var textarea = document.createElement("textarea");
	document.body.appendChild(textarea);
	url = window.document.location.href;
	var endPoint = url.indexOf("do");
	url = url.substring(0,(endPoint+2));
	var param = $("#param").val();
	if(param){
		url = url + param;
	}
	textarea.value = url;
	textarea.select();
	document.execCommand("copy");
	document.body.removeChild(textarea);
	MCP.alert("URL이 복사되었습니다.");
}

// twitter 공유하기
function twitter_share(url, txt){
    var snsUrl = "https://twitter.com/intent/tweet?text="+encodeURIComponent(txt)+"&url="+encodeURIComponent(url)+"&hashtags=ktmmobile,ktM모바일";
    
    var popup = window.open(snsUrl, "_snsPopupWindow", "width=500, height=500, menubar=yes, status=yes, toolbar=yes, resizable=yes");
    popup.focus();
}

// kakaostory 공유하기
function kakaostory_share(url){    
    var snsUrl = "https://story.kakao.com/share?url="+encodeURIComponent(url);

    var popup= window.open(snsUrl, "_snsPopupWindow", "width=500, height=500, menubar=yes, status=yes, toolbar=yes, resizable=yes");
    popup.focus();
}
