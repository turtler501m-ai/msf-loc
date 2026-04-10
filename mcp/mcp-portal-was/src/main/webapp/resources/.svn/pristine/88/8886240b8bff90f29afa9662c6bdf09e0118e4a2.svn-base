;

var naver_id_login;
var state;
$(document).ready(function(){

	var clientId = $("#clientId").val();
	var serviceUrl = $("#serviceUrl").val();
	var pcCallbackUrl = $("#pcCallbackUrl").val();
	naver_id_login = new naver_id_login(clientId, pcCallbackUrl);
  	state = naver_id_login.getUniqState();
  	naver_id_login.setButton("white", 3, 50);
  	naver_id_login.setDomain(serviceUrl);
  	naver_id_login.setState(state);
  	naver_id_login.setPopup();
  	naver_id_login.init_naver_id_login();

});
