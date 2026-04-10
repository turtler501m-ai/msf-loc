
$(document).ready(function () {

	$("#chatbotOpen").click(function() {
	    //alert("�˼��մϴ�.\n����?������?ê��?����?��������?����?�̿���?��ƽ��ϴ�.\n������?>?1:1��㹮��?�Ǵ�?���ֹ�������?�̿�?��Ź?�帳�ϴ�.\n\n��?���˱Ⱓ:?9��?24��(��)?17:00?~?27��(��)?����?�����?���� ");
	    chatbotTalkOpen();
	});

});

function chatbotTalkOpen(){

    //var chatbotUrl = "https://hub.ktis.co.kr:8028/ui/chat/m_mobile.html?botCode=M6CJR7OKSC&chType=MO";
    var chatbotUrl = "https://chatbot.kt-aicc.com/client/20231226131140095/chat.html";
    var popup= window.open(chatbotUrl, "_blank");
    popup.focus();
}
