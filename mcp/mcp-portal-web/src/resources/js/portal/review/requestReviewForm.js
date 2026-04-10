

$(document).ready(function(){

    //탭버튼 클릭이벤트
    $(document).on("click",".c-tabs__button", function(){
        if($(this).attr("id") == 'tab1'){
            $(".ly-footer").hide();
            /*ajaxCommon.createForm({
                    method:"post"
                    ,action:"/requestReView/requestReView.do"
            });

            ajaxCommon.formSubmit(); */
            /*location.href = '/requestReView/requestReView.do';*/
        }else{
            $(".ly-footer").hide();
            /*ajaxCommon.createForm({
                    method:"post"
                    ,action:"/requestReView/requestReviewForm.do"
            });

            ajaxCommon.formSubmit();*/
            /*location.href = '/requestReView/requestReviewForm.do';*/
        }
    });

    // 페이지 하단 등록하기 버튼 클릭 이벤트
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
                    alert("업로드 가능한 파일용량을 초과했습니다.");
                    return false;
                }
            }else{
                alert("업로드 가능한 파일 형식이 아닙니다.");
                return false;
            }
        }
        if($("input[key='file2']").length == 1 && $("input[key='file2']")[0].files[0]){
            if(validFileType($("input[key='file2']")[0].files[0])){
                if(!validFileSize($("input[key='file2']")[0].files[0])){
                    alert("업로드 가능한 파일용량을 초과했습니다.");
                    return false;
                }
            }else{
                alert("업로드 가능한 파일 형식이 아닙니다.");
                return false;
            }
        }

        if($(".c-textarea").val().length < 40){
            alert("사용후기는 40자 이상부터 등록가능합니다.");
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
                popType : 'nmIncPop',
                buttonType : 'registReviewBut'
            });
            // sms 인증 팝업
            openPage("smsCertifyPop", "/requestReview/requestReviewCertifyPop.do", parameterData);
            $("#certifyDelBut").attr("id", "registReviewBut");
            if($("select[name='phone']").val()){
                $("#phoneNum").val($("select[name='phone']").val());
                $("#phoneNum").prop("readonly", true);
                $("#contractNum").val($("select[name='phone'] option:selected").attr("data-contractNum"));

                // 고객에게는 마스킹처리된 연락처로 노출
                $("#phoneNum").hide();
                var maskedTelNo= $("select[name='phone'] option:selected").attr("data-maskedTelNo");
                $("#maskedPhoneNum").show();
                $("#maskedPhoneNum").val(maskedTelNo.replace(/-/gi,''));
                $("#maskedPhoneNum").prop("readonly", true);
            }
        }

    });

    $("#agreeBtn").on("click", function(){
        var parameterData = ajaxCommon.getSerializedData({
            menuType : 'reviewReg',
            popType : 'nmIncPop',
            buttonType : 'registReviewBut'
        });
        // sms 인증 팝업
        openPage("smsCertifyPop", "/requestReview/requestReviewCertifyPop.do", parameterData);
        $("#certifyDelBut").attr("id", "registReviewBut");
        if($("select[name='phone']").val()){
            $("#phoneNum").val($("select[name='phone']").val());
            $("#phoneNum").prop("readonly", true);
            $("#contractNum").val($("select[name='phone'] option:selected").attr("data-contractNum"));

            // 고객에게는 마스킹처리된 연락처로 노출
            $("#phoneNum").hide();
            var maskedTelNo= $("select[name='phone'] option:selected").attr("data-maskedTelNo");
            $("#maskedPhoneNum").show();
            $("#maskedPhoneNum").val(maskedTelNo.replace(/-/gi,''));
            $("#maskedPhoneNum").prop("readonly", true);
        }
    });


    // 사용 후기 작성 텍스트 count
    $("textarea[name='reviewSbst']").on("keyup", function(){

        $("#txtCnt").text($(this).val().length);
        if($(this).val().length > 800){
            $("textarea[name='reviewSbst']").val(
                $("textarea[name='reviewSbst']").val().substring(0,800)
            )
        }
    });

    $("#subbody_loading").hide();

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


    if(!$("#chkAgreeA").is(":checked")){
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
        formData.append("fileAlt", $("#imgDesc1").val());
    }
    if($("input[key='file2']").length == 1 && $("input[key='file2']")[0].files[0]){
        var file2 = $("input[key='file2']")[0].files[0];
        formData.append("file", $("input[key='file2']")[0].files[0]);
        formData.append("fileAlt", $("#imgDesc2").val());
    }

    var deQuestions = [];
    $('input:checkbox[name=detailQuestion]').each(function (index) {


        if($(this).is(":checked")==true){
            deQuestions.push($(this).val());
        }
    });

    var varData = ajaxCommon.getSerializedData({
        commendYn : $("input[name='commendYn']:checked").val()
        ,phone : $("select[name='phone']").val()
        ,eventCd : $("select[name='eventCd']").val()
        ,reviewSbst : $("textarea[name='reviewSbst']").val()
        ,snsInfo : $("input[name='snsInfo']").val()
        ,file : [file1, file2]
        ,menuType : 'reviewReg'
    });
    /*	if($("input[name='commendYn']:checked")){
            formData.append("commendYn", '1');
        }else{
            formData.append("commendYn", '0');
        }*/
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
    formData.append("contractNum", $("select[name='phone'] option:selected").attr("data-contractNum") ? $("select[name='phone'] option:selected").attr("data-contractNum") : "");
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
        , url : '/requestReView/reviewRegAjax.do'
        , data : formData
        , dataType : "json"
    }
    ,function(result){
        KTM.LoadingSpinner.hide();
        var resultCode = result.RESULT_CODE;
        if(resultCode=="00000"){
            MCP.alert("등록되었습니다.", function(){
                //location.reload();
                location.href = '/requestReView/requestReView.do';
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
/*$(document).on("change", ".upload-image__hidden", function(){*/
function setThumb(event){
    var reader = new FileReader();
    var id;
    var target;
    var condAppend = '';
    var fileBox = '';
    var order;
    if($(".staged-image").length == 1){
        condAppend += '<input class="c-input" id="imgDesc2" name="fileAlt" type="text" placeholder="사용후기 이미지에 관한 설명(선택)" title="사용후기 이미지에 관한 설명(선택) 입력" style="width:220px; position:relative; left:30px;">';
        fileBox = '<div class="c-file-image__item" style="width: 220px;height: 167px;border: 1px solid #ddd;border-radius: 0.5rem;background: rgba(0,0,0,0);">';
        target=document.getElementById("file2");
        id=target.getAttribute("key");
        order = "2";
    }else{
        condAppend += '<input class="c-input" id="imgDesc1" name="fileAlt"  type="text" placeholder="사용후기 이미지에 관한 설명(선택)" title="사용후기 이미지에 관한 설명(선택) 입력" style="width:220px; position:relative; left:256px;">';
        fileBox = '<div class="c-file-image__item" style="width: 220px;height: 167px;border: 1px solid #ddd;border-radius: 0.5rem;background: rgba(0,0,0,0);left:16rem;">';
        target=document.getElementById("file1");
        id=target.getAttribute("key");
        order = "1";
    }
    var type = target.files[0].type;
    if(type.indexOf("jpg") == -1 && type.indexOf("gif") == -1 && type.indexOf("png") == -1 && type.indexOf("jpeg") == -1 ){
        MCP.alert("첨부파일 확장자를 확인해주세요. 가능한 파일 확장자는 jpg,gif,png입니다.", function(){
            $("#file" + order + "Image").next().find("i").trigger("click");
        });
        return false;
    }
    if(!validFileSize(target.files[0])){
        MCP.alert("업로드 가능한 파일용량은 2MB입니다.", function(){
            $(".image-label" + order).find("button").trigger("click");
        });
        return false;
    }
    if($(".staged-image").length != 2){
        var htmlAppend = "";
        var htmlDescAppend = "";
        htmlAppend += '<div class="c-form staged-image">';
        htmlAppend += fileBox;
        htmlAppend += '<div id="' + id + 'Image"></div>';
        htmlAppend += '<button class="c-button">';
        htmlAppend += '<span class="sr-only">삭제</span>';
        htmlAppend += '<i class="c-icon c-icon--delete" aria-hidden="true"></i>';
        htmlAppend += '</button>';
        htmlAppend += '</div>';
        htmlAppend += condAppend;
        htmlAppend += '</div>';

        $(".staged_img-list").append(htmlAppend);

        if($(".staged-image").length == 1){
            $(".c-label__sub").text("사진 1/2");
            $(".file-label").attr("for", "file2");
        }else{
            $(".c-label__sub").text("사진 2/2");
        }
        $(this).hide();

    }

    reader.onload = function(event){
        console.log(target.files[0].name);

        console.log(id);
        /*var img = document.createElement("img");*/
        /*$("#" + id + "Image").html(target.files[0].name);*/
        $("#" + id + "Image").html("등록완료");
        /*document.querySelector("div.c-file-image__item").appendChild(img);*/

        //$("#" + id).show();
    }

    reader.readAsDataURL(event.target.files[0]);
}
/*});*/

//이미지 첨부 삭제 아이콘 클릭 이벤트
$(document).on("click",".c-icon--delete",function(){
    var clickTarget = $(this);
    //이미지가 2개일때
    if($(".staged-image").length == 2){
        //첫번째 이미지 아이콘 클릭시
        if($(clickTarget).parent().siblings('img').attr("id") == 'file1Image'){
            $(clickTarget).parent().parent().parent().remove();
            $(".staged-image").find('img').attr('id', 'file1Image');
            $(".staged-image").find('input[name="imgDesc"]').attr('id', 'imgDesc1');
            $(".c-label__sub").text("사진 1/2");
            $("#file1").attr("key", 'file3');
            $("#file1").attr("id", 'file3');

            $("#file2").attr("key", 'file1');
            $("#file2").attr("id", 'file1');

            $("#file3").attr("key", 'file2');
            $("#file3").attr("id", 'file2');

            $(".file-label").attr("for", 'file1');
            $("#file2").val('');


            /*if($("#file1").attr("src")){
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
                htmlAppend += "<img id='file2' src=''>";
                htmlAppend += "</label>";
                $(".upload-image").append(htmlAppend);

            }else{
                $("#imageDesc2").remove();
            }*/
        // 두번째 이미지 아이콘 클릭시
        }else{
            $(clickTarget).parent().parent().parent().remove();
            $(".c-label__sub").text("사진 1/2");

            $("#file2").val('');
            /*		if($("#file1").attr("src")){
                        $(clickTarget).siblings("input").attr("type","text");
                        $(clickTarget).siblings("img").attr("src", "");
                        $(clickTarget).siblings('img').hide();
                        $(clickTarget).parent().css("padding", "12");
                        $(clickTarget).siblings(".c-icon--photo").show();
                        $(clickTarget).siblings(".u-co-gray").show();
                        $(clickTarget).siblings("input").attr("type","file");
                        $(clickTarget).hide();
                    }else{
                        $(clickTarget).parent().remove();
                        $("#imageDesc2").remove();
                    }*/
        }
        window.KTM.initialize();
    // 이미지 하나일때
    }else{
        $(clickTarget).parent().parent().parent().remove();
        $(".c-label__sub").text("사진 0/2");
        $(".file-label").attr("for", 'file1');
        $("#file1").val('');

    }
});

$(document).on("click","#eventLink",function(){
    var parameterData = ajaxCommon.getSerializedData({
        eventPopTitle : '사용후기 이벤트'
    });
    // 공통코드 작업필요
    openPage('eventPop', '/event/eventDetailViewAjax.do?ntcartSeq=908&sbstCtg=E&eventBranch=E', parameterData);

    // var data = {width:'1200', height:'800'};
    // openPage('outLink3', '/event/eventDetail.do?ntcartSeq=908&sbstCtg=E&eventBranch=E', data);
    // window.open('/event/eventDetail.do?ntcartSeq=908&sbstCtg=E&eventBranch=E');
})


//체크박스 라디오버튼처럼 1개만 선택되도록 + 체크해제도 가능
$(document).on('click', 'input[type="checkbox"]', function() {

    var group = $(this).closest('.c-checktype-row');

    if($(this).context.dataset.sub != 'detailQuestions'){
        if($(this).prop('checked')){
            group.find('input[type="checkbox"]').prop('checked', false);
            $(this).prop('checked', true);
        }
    }

});

