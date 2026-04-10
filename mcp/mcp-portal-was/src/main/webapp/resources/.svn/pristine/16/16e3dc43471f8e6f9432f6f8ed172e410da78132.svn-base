/**
 *
 */
$(document).ready(function(){


/*
        var varData = ajaxCommon.getSerializedData({
            name:$.trim(userName)
            ,phone:$.trim(phoneNum)
            ,send:send
        });*/
        ajaxCommon.getItem({
            id:'MultiPhoneLineListAjax'
            ,cache:false
            ,url:'/m/mypage/MultiPhoneLineListAjax.do'
            ,data: null
            ,dataType:"json"
        }
        ,function(data){
            $("#mid-title").text(data.title);
            $("#mainBut").text(data.but);

            if(data.userVO1 && data.userVO1.length > 0){
                for(var i = 0; i < data.userVO1.length; i ++){
                    var mobileNo = "";
                    if($("#maskingSession").val() == 'Y'){
                        mobileNo = data.userVO1[i].mobileNo;
                    }else{
                        mobileNo = data.userVO1[i].mkMobileNo;
                    }

                    if(data.userVO1[i].repNo != 'R'){
                    	$(".ex-multiLine").show();
                        $("tbody").append("<tr>" +
                                        "<td class='u-ta-center'>" + mobileNo + "</td>" +
                                        "<td class='u-ta-center'>" + data.userVO1[i].modelName  + "</td>" +
                                        "<td class='u-ta-center'>" +
                                            "<button class='c-button c-button--underline c-button--sm u-co-mint c-flex__shrink-0 rep-button' id='" + data.userVO1[i].contractNum + "'  href='javascript:void(0);'    onclick='changeRep(this)'>선택</button>" +
                                        "</td>" +
                                    "</tr>");
                    }else{
                    	$(".ex-multiLine").show();
                        $("tbody").append("<tr>" +
                                            "<td class='u-ta-center'>" + mobileNo + "</td>" +
                                            "<td class='u-ta-center'>" + data.userVO1[i].modelName  + "</td>" +
                                            "<td class='u-ta-center'>대표번호</td>" +
                                        "</tr>");
                    }
                }
            }else{
            	$(".ex-multiLine").hide();
                $("tbody").append("<tr>" +
                                    "<td colspan='3' class='u-ta-center'>kt M모바일의 모든 서비스를 이용하시기 위해<br/>정회원 인증을 해주세요.</td>" +
                                        "</tr>");
            }
        });



});
function changeRep(key){
    KTM.Confirm("이 번호를 대표번호로 변경하시겠습니까?", function(){
        this.close();
    var varData = ajaxCommon.getSerializedData({
            contractNum : $(key).attr("id")
        });
        ajaxCommon.getItem({
            id:'userRepChange'
            ,cache:false
            ,url:'/m/mypage/userRepChange.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(data){
            MCP.alert(data.successMsg, function(){
                location.href = data.redirectUrl;
            });
        });
    });
}

$("#mainBut").on("click", function(){
    $("#pw_confirm-dialog").remove();
    var param = ajaxCommon.getSerializedData({
                            menuType : 'multiPhone'
                        });
    openPage('multiPhonePop', '/m/mypage/multiPhoneLineAddView.do', param);
});

