;

var naver_id_login;

$(document).ready(function(){
	var clientId = $("#clientId").val();
	var nearoCallBackUrl = $("#nearoCallBackUrl").val();
	naver_id_login = new naver_id_login(clientId, nearoCallBackUrl);
	naver_id_login.get_naver_userprofile("naverSignInCallback()");
});


function naverSignInCallback() {

	var access_token = naver_id_login.oauthParams.access_token;
    const id = naver_id_login.getProfileData('id');
    const name = naver_id_login.getProfileData('name');
	const email = naver_id_login.getProfileData('email');
    const gender = naver_id_login.getProfileData('gender');
	naverSignInLogin(access_token,id,name,email,gender);
}


function naverSignInLogin(access_token, id, name, email, gender){
	var newForm = $('<form></form>');
	newForm.attr('name','newForm');
	newForm.attr('method','post');
	newForm.attr('action','/m/cert/nearoCallBackLoginProcese.do');
	newForm.append($('<input/>', {type: 'hidden', name: 'accessToken', access_token }));
	newForm.append($('<input/>', {type: 'hidden', name: 'platform', value:'PC'}));
	newForm.append($('<input/>', {type: 'hidden', name: 'id', value:id}));
	newForm.append($('<input/>', {type: 'hidden', name: 'name', value:name}));
	newForm.append($('<input/>', {type: 'hidden', name: 'email', value:email}));
	newForm.append($('<input/>', {type: 'hidden', name: 'gender', value:gender}));
	newForm.appendTo('body');
	newForm.submit();
}




