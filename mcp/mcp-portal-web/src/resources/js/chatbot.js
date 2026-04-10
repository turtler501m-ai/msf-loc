
var chatbotOpen;

$(document).ready(function () {

	$("#chatbotOpen").click(function() {
	    //alert("죄송합니다.\n현재 고객센터 챗봇 서비스 점검으로 서비스 이용이 어렵습니다.\n고객센터 > 1:1상담문의 또는 자주묻는질문 이용 부탁 드립니다.\n\n■ 점검기간: 9월 24일(금) 17:00 ~ 27일(월) 점검 종료시 까지 ");
	    chatbotTalkOpen();
	});

});

function chatbotTalkOpen(){

	var popWidth = 420;
	var popHeight = 642;

	var winHeight = document.body.clientHeight;				// 현재창의 높이
	var winWidth = document.body.clientWidth;					// 현재창의 너비

	var winX = window.screenX || window.screenLeft || 0;	// 현재창의 x좌표
	var winY = window.screenY || window.screenTop || 0; // 현재창의 y좌표

	var popX = winX + (winWidth - popWidth) / 2;
	//var popY = winY + (winHeight - popHeight) / 2;
	var popY = 81;

	chatbotTalkAction(popWidth, popHeight, popY, popX );
}


function chatbotTalkAction(popWidth, popHeight, popY, popX ){

	//var link = "https://hub.ktis.co.kr:8028/ui/chat/m_mobile-pc.html?botCode=THHCW92FDZ&chType=PC";
    var link = "https://chatbot.kt-aicc.com/client/20231226131140095/chat.html";

	//  팝업이 열려있는지 체크한다.
	if(chatbotOpen == null){
		chatbotOpen = window.open("", "chatbotTalkPopup", "width=" + popWidth + "px,height=" + popHeight + "px,top=" + popY + ",left=" + popX + ",resizable=no, status=yes");
		try {
			if(chatbotOpen.location.host == ""){
				chatbotOpen.location.href = link;
				//chatbotOpen.focus();
			} else {}
		} catch(e) {
			//IE10 이하 새창 띄우기
			chatbotOpen = window.open(link, "chatbotTalkPopup", "width=" + popWidth + "px,height=" + popHeight + "px,top=" + popY + ",left=" + popX + ",resizable=no, status=yes");
		}

	} else {
		if(chatbotOpen.closed == false){
			//chatbotOpen.focus();
		}else{
			chatbotOpen = window.open("", "chatbotTalkPopup", "width=" + popWidth + "px,height=" + popHeight + "px,top=" + popY + ",left=" + popX + ",resizable=no, status=yes");
			try {
				if(chatbotOpen.location.host == ""){
					chatbotOpen.location.href = link;
					//chatbotOpen.focus();
				} else {}
			} catch(e) {
				chatbotOpen = window.open(link, "chatbotTalkPopup", "width=" + popWidth + "px,height=" + popHeight + "px,top=" + popY + ",left=" + popX + ",resizable=no, status=yes");
			}
		}
   }
	chatbotOpen.focus();
}





