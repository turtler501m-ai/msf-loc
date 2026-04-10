
var key = false;
var first = true;

function review(){
    if(!key){
        return false;
    }else{
        if(first){
            $("input[key='file1']").trigger("click");
            first = false;
        }else{
            event.returnValue = true;
        }
    }
}


$(document).ready(function(){
    $("input[key='file1']").on("click", function(){
        var mobileAppChk = $("#mobileAppChk").val();
        var inTime = this;
        var keyNm = $(this).attr("key");

        //앱/어플리케이션으로 실행시에만 실행
        if (mobileAppChk == 'A') {
            if(!key){
                event.returnValue = false;
            }

            var result = requestPermission('PIC', 'review');


        }
    });


    $("#certifyPopBut").on("click", function(){
        $('#InfoYn').val('N');
        $('#collectionItem').html("");

        var reviewNums = $(".review-cnt").val();
        for(var i=1; i <= reviewNums; i++){
            if ($(`#reviewNum${i} input[type="checkbox"]:checked`).length == 0) {
                MCP.alert("사용후기 상세 질문을 모두 선택해주세요.");
                return false;
            }
        }

        if(!$("textarea[name='reviewSbst']").val() || $("textarea[name='reviewSbst']").val() == ''){
            MCP.alert("사용후기를 작성해 주세요.");
            return false;
        }

        if($("input[key='file1']")[0].files[0]){
            if(validFileType($("input[key='file1']")[0].files[0])){
                if(!validFileSize($("input[key='file1']")[0].files[0])){
                    MCP.alert("업로드 가능한 파일용량을 초과했습니다. 업로드 가능한 파일 용량은 최대 2MB입니다.");
                    return false;
                }
            }else{
                MCP.alert("업로드 가능한 파일 형식이 아닙니다. 업로드 가능한 파일 형식은 'jpg, gif, png' 입니다.");
                return false;
            }
        }
        if($("input[key='file2']").length == 1 && $("input[key='file2']")[0].files[0]){
            if(validFileType($("input[key='file2']")[0].files[0])){
                if(!validFileSize($("input[key='file2']")[0].files[0])){
                    MCP.alert("업로드 가능한 파일용량을 초과했습니다. 업로드 가능한 파일 용량은 최대 2MB입니다.");
                    return false;
                }
            }else{
                MCP.alert("업로드 가능한 파일 형식이 아닙니다. 업로드 가능한 파일 형식은 'jpg, gif, png' 입니다.");
                return false;
            }
        }

        if($(".c-textarea").val().length < 40){
            MCP.alert("사용후기는 40자 이상부터 등록가능합니다.");
            return false;
        }

        var appendExecuted = false;
        $('input:checkbox[name=detailQuestion]').each(function (index) {
            if($(this).context.dataset.sub2 == 'consentInfo'){
                if($(this).context.dataset.sub == 'detailQuestions'){
                    if($(this).prop('checked') && !appendExecuted){
                        $('#InfoYn').val('Y');
                        $('#collectionItem').append(', '+$(this).context.dataset.sub3);
                        appendExecuted = true;
                    }
                }else{
                    appendExecuted = false;
                    if($(this).prop('checked') && !appendExecuted){
                        $('#InfoYn').val('Y');
                        $('#collectionItem').append(', '+$(this).context.dataset.sub3);
                        appendExecuted = false;
                    }
                }
            }
        });

        var collecItem = $('#collectionItem').html();
        $('#collectionItem').html(collecItem.substr(2));


        if($('#InfoYn').val() == 'Y'){
            $("#consentInfos").click();
        }else{
            var parameterData = ajaxCommon.getSerializedData({
                menuType : 'reviewReg',
                buttonType : 'registReviewBut'
            });

            openPage("smsCertifyPop", "/m/requestReview/requestReviewCertifyPop.do", parameterData);
            $("#certifyDelBut").attr("id", "registReviewBut");
            $("#phoneNum").val($("select[name='phone']").val());
            if($("#isLogin").val() == "Y"){
                $("#phoneNum").prop("readonly", true);
                $("#contractNum").val($("select[name='phone'] option:selected").attr("data-contractNum"));

                // 고객에게는 마스킹처리된 연락처로 노출
                $("#phoneNum").hide();

                var maskedTelNo= $("select[name='phone'] option:selected").attr("data-maskedTelNo");
                $("#maskedPhoneNum").show();
                $("#maskedPhoneNum").val(maskedTelNo.replace(/-/gi,''));
                $("#maskedPhoneNum").prop("readonly", true);

            }else{
                $("#phoneNum").prop("readonly", false);
            }
        }
    });

    $("#agreeBtn").on("click", function(){

        var parameterData = ajaxCommon.getSerializedData({
            menuType : 'reviewReg',
            buttonType : 'registReviewBut'
        });

        openPage("smsCertifyPop", "/m/requestReview/requestReviewCertifyPop.do", parameterData);
        $("#certifyDelBut").attr("id", "registReviewBut");
        $("#phoneNum").val($("select[name='phone']").val());
        if($("#isLogin").val() == "Y"){
            $("#phoneNum").prop("readonly", true);
            $("#contractNum").val($("select[name='phone'] option:selected").attr("data-contractNum"));

            // 고객에게는 마스킹처리된 연락처로 노출
            $("#phoneNum").hide();

            var maskedTelNo= $("select[name='phone'] option:selected").attr("data-maskedTelNo");
            $("#maskedPhoneNum").show();
            $("#maskedPhoneNum").val(maskedTelNo.replace(/-/gi,''));
            $("#maskedPhoneNum").prop("readonly", true);

        }else{
            $("#phoneNum").prop("readonly", false);
        }

    });

    $("textarea[name='reviewSbst']").on("keyup", function(){

        $("#txtCnt").text($(this).val().length);
        if($(this).val().length > 800){
            $("textarea[name='reviewSbst']").val(
                $("textarea[name='reviewSbst']").val().substring(0,800)
            )
        }
    });

});


function validFileType(file){
    var validFile = ['jpg', 'jpeg', 'png', 'gif'];
    var result = false;
    for(param in validFile){
        if(file.type.indexOf(validFile[param]) != -1){
            result = true;
            break;
        }
    }
    return result;
}

function validFileSize(file){
    var result = true;
    if(file.size/1024/1024 > 2){
        result = false;
    }
    return result;
}

$(document).on("click", "#registReviewBut", function(){
    var formData = new FormData();

    // if(!$("#chkAgree").is(":checked")){
    // 	MCP.alert("유의사항에 동의 후 등록가능합니다.");
    // 	return false;
    // }

    if(!$("#chkAgree").is(":checked")){
        // MCP.alert("유의사항에 동의 후 등록가능합니다.");
        MCP.alert("개인정보 수집 및 이용에 동의해 주세요.");
        return false;
    }

    if ($("#custNm").val() == '' || $("#custNm").val() == null) {
        MCP.alert("고객명을 입력해 주세요.");
        return false;
    }

    if ($('#phoneNum').val() == '' || $('#phoneNum').val() == null) {
        MCP.alert("핸드폰 번호를 입력해 주세요.");
        return false;
    }

    if ($('#certifySms').val() == '' || $('#certifySms').val() == null) {
        MCP.alert("인증번호를 입력해 주세요.");
        return false;
    }

    if($("#certifyYn").val() == 'N'){
        MCP.alert("SMS 인증 후 등록가능합니다.");
        return false;
    }

    if($("input[key='file1']")[0].files[0]){
        var file1 = $("input[key='file1']")[0].files[0];
        formData.append("file", $("input[key='file1']")[0].files[0]);
        formData.append("fileAlt", $("#imageDesc1").val());
    }
    if($("input[key='file2']").length == 1 && $("input[key='file2']")[0].files[0]){
        var file2 = $("input[key='file2']")[0].files[0];
        formData.append("file", $("input[key='file2']")[0].files[0]);
        formData.append("fileAlt", $("#imageDesc2").val());
    }

    var deQuestions = [];
    $('input:checkbox[name=detailQuestion]').each(function (index) {

        if($(this).is(":checked")==true){
            deQuestions.push($(this).val());
        }
    });

    var varData = ajaxCommon.getSerializedData({
        commendYn : $("input[name='commendYn']:checked").val()
        ,phone : $("#phoneNum").val()
        ,eventCd : $("select[name='eventCd']").val()
        ,reviewSbst : $("textarea[name='reviewSbst']").val()
        ,snsInfo : $("input[name='snsInfo']").val()
        ,file : [file1, file2]
        ,menuType : 'reviewReg'
    });
    KTM.LoadingSpinner.show();
    formData.append("commendYn", $("input[name='commendYn']:checked").val());
    formData.append("phone",$("#phoneNum").val());
    formData.append("eventCd", $("select[name='eventCd']").val());
    formData.append("reviewSbst", $("textarea[name='reviewSbst']").val());
    formData.append("snsInfo", $("input[name='snsInfo']").val());
    formData.append("name", $("#custNm").val());
    formData.append("menuType", 'reviewReg');
    formData.append("isLogin", $("#isLogin").val());
    formData.append("deQuestions", deQuestions);
    formData.append("contractNum", $("select[name='phone'] option:selected").attr("data-contractNum") ? $("select[name='phone'] option:selected").attr("data-contractNum") : '');

    /*if($("input[key='file1']").val() != ""){
        formData.append("file", $("input[key='file1']")[0].files[0]);
    }
    if($("input[key='file2']").val() != ""){
        formData.append("file", $("input[key='file2']")[0].files[0]);
    }*/

    ajaxCommon.getItemFileUp({
        id : 'reviewRegAjax'
        , cache : false
        , async : false
        , url : '/m/requestReView/reviewRegAjax.do'
        , data : formData
        , dataType : "json"
    }
    ,function(result){
        KTM.LoadingSpinner.hide();
        var resultCode = result.RESULT_CODE;
        if(resultCode=="00000"){
            MCP.alert("등록되었습니다.", function(){
                //location.reload();
                location.href = '/m/requestReView/requestReView.do';
            });
         } else if(resultCode=="0001"){
            MCP.alert("SMS인증 후 등록가능합니다.");
            return false;
         } else if(resultCode=="0003"){
             MCP.alert("현재 kt M모바일을 사용중인 고객만 사용후기 작성이 가능합니다.");
             return false;
         } else if(resultCode=="0002"){

             MCP.alert("파일 사이즈 및 확장자명을 확인해 주세요.");
             return false;
         } else if(resultCode=="0004"){

             MCP.alert("등록에 실패했습니다.");
             return false;
         }else if(resultCode=="0005"){
            MCP.alert("로그인 정보가 다릅니다.");
            return false;
        }else if(resultCode=="0006"){
            MCP.alert("정지된 회선은 후기등록이 불가합니다.");
            return false;
        }else if(resultCode=="0007"){
            MCP.alert("이미 후기를 등록한 회선입니다.");
            return false;
        }else if(resultCode=="STEP01" || resultCode=="STEP02"){ // STEP 오류
            MCP.alert(result.RESULT_MSG);
            return false;
        }else if(resultCode=="9999"){
            MCP.alert(result.RESULT_MSG);
            return false;
        }else if(resultCode=="0008"){
            MCP.alert("사용후기 상세 질문을 모두 선택해주세요.");
            return false;
        }else if(resultCode=="0009"){
            MCP.alert("정회원 인증이 완료 된 회원만<br> 사용후기 등록이 가능합니다.");
            return false;
        }
    });



});


$(".commend-check").on("click", function(){
    $(".commend-check").prop("checked",false);
    $(this).prop("checked", true);
});



// 이미지 첨부시 미리보기, 첨부란 추가
$(document).on("change", ".upload-image__hidden", function(){
    var reader = new FileReader();
    var target;
    var order;
    var id=$(this).attr("key");
    if(id == 'file1' && $(".upload-image__label").length != 2){
        target = document.getElementById("fileTarget1");
        order = 1;
    }else{
        target = document.getElementById("fileTarget2");
        order = 2;
    }
    console.log(target.files[0]);
    var type = target.files[0].type;
    if(type.indexOf("jpg") == -1 && type.indexOf("gif") == -1 && type.indexOf("png") == -1 && type.indexOf("jpeg") == -1 ){
        MCP.alert("첨부파일 확장자를 확인해주세요. 가능한 파일 확장자는 jpg,gif,png입니다.", function(){
            $(".image-label" + order).find("button").trigger("click");
            return false;
        })
    }

    if(!validFileSize(target.files[0])){
        MCP.alert("업로드 가능한 파일용량은 2MB입니다.", function(){
            $(".image-label" + order).find("button").trigger("click");
            return false;
        });
    }
    $(this).siblings(".c-icon").hide();
    $(this).siblings(".u-co-gray").hide();
    $(this).siblings(".upload-image__delete").show();
    $(this).parent().css("padding", "0px");
    var htmlAppend = "";
    var htmlDescAppend = "";
    if(id == 'file1' && $(".upload-image__label").length != 2){

        htmlAppend += "<label class='upload-image__label image-label2'>";
        htmlAppend += "<span class='c-hidden'>파일선택</span>";
        htmlAppend += "<input class='upload-image__hidden' key='file2' name='file' type='file' id='fileTarget2' accept='.jpg, .gif, .png'>";
        htmlAppend += "<i class='c-icon c-icon--photo' aria-hidden='true'></i>";
        htmlAppend += "<span class='u-co-gray'>사진 2 / 2</span>";
        htmlAppend += "<button class='upload-image__delete' style='display:none;'>";
        htmlAppend += "<i class='c-icon c-icon--delete' aria-hidden='true'></i>";
        htmlAppend += "<span class='c-hidden'>삭제</span>";
        htmlAppend += "</button>";
        htmlAppend += "<div id='file2' src=''></div>";
        htmlAppend += "</label>";


        htmlDescAppend += "<input class='c-input' type='text' id='imageDesc1' name='fileAlt' placeholder='1. 사용후기 이미지에 관한 설명(선택)' title='사용후기 이미지 설명 입력'>";
        $(".upload-image").append(htmlAppend);
        $(".upload-image").after(htmlDescAppend);

    }

    if(!htmlAppend){
        htmlDescAppend += "<input class='c-input' type='text' id='imageDesc2' name='fileAlt' placeholder='2. 사용후기 이미지에 관한 설명(선택)' title='사용후기 이미지 설명 입력'>";
        $("#imageDesc1").after(htmlDescAppend);
    }

    reader.onload = function(e){
        /*$("#" + id).attr("src", e.target.result);*/
        $("#" + id).width(95);
        $("#" + id).height(72);
        $("#" + id).attr("style", "position:relative; top:25px;")
        $("#" + id).html("등록완료");
        $("#" + id).show();

        $("input[key='" + id + "']").prop("disabled", true);

    }
    reader.readAsDataURL(this.files[0]);
});

// 삭제 아이콘 클릭 이벤트
$(document).on("click",".upload-image__delete",function(){
    var clickTarget = $(this);
    //이미지가 2개일때
    if($(".upload-image__label").length == 2 && $(".image-label2").find("input[type='file']").val()){
        //첫번째 이미지 아이콘 클릭시
        if($(clickTarget).parent().hasClass("image-label1")){
            $(clickTarget).parent().remove();
            $(".image-label2").addClass("image-label1");
            $(".image-label1").removeClass("image-label2");
            $(".image-label1 .upload-image__hidden").attr("key", "file1");
            $(".image-label1 .u-co-gray").text("사진 1 / 2");
            $(".image-label1 img").attr("id", "file1");
            $("input[key='file1']").prop("disabled", false);
            if($("#file1").attr("src")){
                var htmlAppend = "";
                htmlAppend += "<label class='upload-image__label image-label2'>";
                htmlAppend += "<span class='c-hidden'>파일선택</span>";
                htmlAppend += "<input class='upload-image__hidden' key='file2' type='file' accept='.jpg, .gif, .png'>";
                htmlAppend += "<i class='c-icon c-icon--photo' aria-hidden='true'></i>";
                htmlAppend += "<span class='u-co-gray'>사진 2 / 2</span>";
                htmlAppend += "<button class='upload-image__delete' style='display:none;'>";
                htmlAppend += "<i class='c-icon c-icon--delete' aria-hidden='true'></i>";
                htmlAppend += "<span class='c-hidden'>삭제</span>";
                htmlAppend += "</button>";
                htmlAppend += "<div id='file2'></div>";
                htmlAppend += "</label>";
                $(".upload-image").append(htmlAppend);
                $("#imageDesc2").remove();

            }else{
                $("#imageDesc2").remove();
            }
        // 두번째 이미지 아이콘 클릭시
        }else{
            if($("#file1").html()){
                $(clickTarget).siblings("input").attr("type","text");
                $(clickTarget).siblings("img").attr("src", "");
                $(clickTarget).siblings('img').hide();
                $(clickTarget).parent().css("padding", "12");
                $(clickTarget).siblings(".c-icon--photo").show();
                $(clickTarget).siblings(".u-co-gray").show();
                $(clickTarget).siblings("input").attr("type","file");
                $(clickTarget).hide();
                $("#imageDesc2").remove();
                $("input[key='file2']").prop("disabled", false);
            }else{
                $(clickTarget).parent().remove();
                $("#imageDesc2").remove();
            }
        }
        window.KTM.initialize();
    // 이미지 하나일때
    }else{
        $(clickTarget).siblings("input").attr("type","text");
        $(clickTarget).siblings("img").attr("src", "");
        $(clickTarget).siblings('img').hide();
        $(clickTarget).parent().css("padding", "12");
        $(clickTarget).siblings(".c-icon--photo").show();
        $(clickTarget).siblings(".u-co-gray").show();
        $(clickTarget).siblings("input").attr("type","file");
        $(clickTarget).hide();
        $("input[key='file1']").prop("disabled", false);
        $(".image-label2").remove();
        $("#imageDesc1").remove();
    }
});



$(document).on("click","#eventLink",function(){
    var parameterData = ajaxCommon.getSerializedData({
        eventPopTitle : '사용후기 이벤트'
    });
    openPage('eventPop', '/m/event/eventDetailViewAjax.do?ntcartSeq=908&sbstCtg=E&eventBranch=E', parameterData);
})



//체크박스 라디오버튼처럼 1개만 선택되도록 + 체크해제도 가능
$(document).on('click', 'input[type="checkbox"]', function() {

    var group = $(this).closest('.c-check-wrap--column');

    if($(this).context.dataset.sub != 'detailQuestions'){
        if($(this).prop('checked')){
            group.find('input[type="checkbox"]').prop('checked', false);
            $(this).prop('checked', true);
        }
    }

});

