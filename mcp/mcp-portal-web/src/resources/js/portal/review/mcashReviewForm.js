$(document).ready(function() {

    // 후기 작성 버튼 클릭
    $("#certifyPopBtn").click(function(){

        if (!$("textarea[name=reviewContent]").val() || $("textarea[name=reviewContent]").val() == "") {
            MCP.alert("사용후기를 작성해 주세요.");
            return false;
        }

        if ($("input[key=file1]")[0].files[0]) {
            if(validFileType($("input[key=file1]")[0].files[0])) {
                if(!validFileSize($("input[key=file1]")[0].files[0])) {
                    alert("업로드 가능한 파일용량을 초과했습니다.");
                    return false;
                }
            } else {
                alert("업로드 가능한 파일 형식이 아닙니다.");
                return false;
            }
        }

        if ($("input[key=file2]")[0].files[0]) {
            if(validFileType($("input[key=file2]")[0].files[0])) {
                if(!validFileSize($("input[key=file2]")[0].files[0])) {
                    alert("업로드 가능한 파일용량을 초과했습니다.");
                    return false;
                }
            } else {
                alert("업로드 가능한 파일 형식이 아닙니다.");
                return false;
            }
        }

        if ($("input[key=file3]")[0].files[0]) {
            if(validFileType($("input[key=file3]")[0].files[0])) {
                if(!validFileSize($("input[key=file3]")[0].files[0])) {
                    alert("업로드 가능한 파일용량을 초과했습니다.");
                    return false;
                }
            } else {
                alert("업로드 가능한 파일 형식이 아닙니다.");
                return false;
            }
        }

        if ($("input[key=file4]")[0].files[0]) {
            if(validFileType($("input[key=file4]")[0].files[0])) {
                if(!validFileSize($("input[key=file4]")[0].files[0])) {
                    alert("업로드 가능한 파일용량을 초과했습니다.");
                    return false;
                }
            } else {
                alert("업로드 가능한 파일 형식이 아닙니다.");
                return false;
            }
        }

        if ($("textarea[name=reviewContent]").val().trim().length < 40) {
            MCP.alert("사용후기는 40자 이상부터 등록 가능합니다.");
            return false;
        }

        if ($("input[name=snsInfo]").val()) {
            var regExp = /[ㄱ-ㅎㅏ-ㅣ가-힣]/gi;
            if (regExp.test($("input[name=snsInfo]").val())) {
                MCP.alert("SNS공유 URL에 한글이 포함될 수 없습니다.");
                return false;
            }
        }

        var parameterData = ajaxCommon.getSerializedData({
            menuType: 'mcashReviewReg',
            popType: 'nmIncPop',
            buttonType: 'mcashReviewRegBtn'
        });

        // sms 인증 팝업
        openPage("smsCertifyPop", "/mcash/review/mcashReviewCertifyPop.do", parameterData);
        if ($("input[name=phone]").val()) {
            $("#phoneNum").val($("input[name=phone]").val());
            $("#phoneNum").prop("readonly", true);
            $("#contractNum").val($("input[name=cntrNum]").val());

            // 고객에게 마스킹 처리된 연락처 노출
            $("#phoneNum").hide();
            var maskedTelNo = $("#phone").val();
            $("#maskedPhoneNum").show();
            $("#maskedPhoneNum").val(maskedTelNo.replace(/-/gi, ''));
            $("#maskedPhoneNum").prop("readonly", true);
        }
    });

    // 사용후기 작성 텍스트 count
    $("textarea[name=reviewContent]").on("input paste", function() {
        if ($(this).val().trim().length > 800) {
            $("textarea[name=reviewContent]").val(
                $("textarea[name=reviewContent]").val().substring(0, 800)
            )
        }
        $("#txtCnt").text($(this).val().trim().length);
    });

    $("input[name=snsInfo]").on("change paste", function() {
        var regExp = /[ㄱ-ㅎㅏ-ㅣ가-힣]/gi;
        var value = $(this).val();

        var replaceStr = value.replaceAll(regExp, '');
        $("input[name=snsInfo]").val(replaceStr);
    });

    $("#subbody_loading").hide();
});
$(document).on("click", "#mcashReviewRegBtn", function() {

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

    regstReview();
});

// 후기 작성하기
function regstReview() {
    var formData = new FormData();

    var imgFiles = $("input[name=file]");
    imgFiles.each(function(index, item) {
        if ($(item)[0].files[0]) {
            var descIndex = index + 1;
            var alt = $("#imgDesc" + descIndex).val() ? $("#imgDesc" + descIndex).val() : ' ';
            formData.append("file", $(item)[0].files[0]);
            formData.append("fileAlt", alt);
        }
    });

    KTM.LoadingSpinner.show();

    formData.append("contractNum", $("#cntrNum").val());
    formData.append("name", $("#custNm").val());
    formData.append("reviewType", "MCASH");
    formData.append("reviewTypeDtl", $("select[name=eventCd]").val());
    formData.append("reviewContent", $("textarea[name=reviewContent]").val().trim());
    formData.append("snsInfo", $("input[name=snsInfo]").val().trim());

    ajaxCommon.getItemFileUp({
        id: 'regstReviewAjax',
        url: '/mcash/review/regstReviewAjax.do',
        data: formData,
        dataType: "json",
        cache: false,
        async: false
    }, function(jsonObj) {
        KTM.LoadingSpinner.hide();

        if (jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS) {
            MCP.alert("M쇼핑할인 사용후기 등록이 완료되었습니다.", function() {
                location.href = '/mcash/review/mcashReview.do';
            });
        } else {
            MCP.alert(jsonObj.RESULT_MSG);
        }
    });
}

function validFileType(file) {
    var validFile = ['jpg', 'jpeg', 'png', 'gif'];
    var result = false;
    for (param in validFile) {
        if (file.type.indexOf(validFile[param]) != -1) {
            result = true;
            break;
        }
    }
    return result;
}

function validFileSize(file) {
    var result = true;
    if (file.size/1024/1024 > 2) {
        result = false;
    }

    return result;
}

// 이미지 첨부 시 미리보기, 첨부란 추가
function setThumb(event, fileOrder) {
    var reader = new FileReader();
    var id;
    var target;
    var condAppend = '';
    var fileBox = '';
    var order;

    if(fileOrder == "file4") {
        condAppend += '<input class="c-input" id="imgDesc4" name="fileAlt" type="text" placeholder="사용후기 이미지에 관한 설명(선택)" title="사용후기 이미지에 관한 설명(선택) 입력" style="width:220px;" maxlength="60">';
        fileBox = '<div class="c-file-image__item" style="width: 220px;height: 167px;border: 1px solid #ddd;border-radius: 0.5rem;background: rgba(0,0,0,0);">';
        target = document.getElementById("file4");
        id = target.getAttribute("key");
        order = "4";
    } else if (fileOrder == "file3") {
        condAppend += '<input class="c-input" id="imgDesc3" name="fileAlt" type="text" placeholder="사용후기 이미지에 관한 설명(선택)" title="사용후기 이미지에 관한 설명(선택) 입력" style="width:220px;" maxlength="60">';
        fileBox = '<div class="c-file-image__item" style="width: 220px;height: 167px;border: 1px solid #ddd;border-radius: 0.5rem;background: rgba(0,0,0,0);">';
        target = document.getElementById("file3");
        id = target.getAttribute("key");
        order = "3";
    } else if (fileOrder == "file2") {
        condAppend += '<input class="c-input" id="imgDesc2" name="fileAlt" type="text" placeholder="사용후기 이미지에 관한 설명(선택)" title="사용후기 이미지에 관한 설명(선택) 입력" style="width:220px;" maxlength="60">';
        fileBox = '<div class="c-file-image__item" style="width: 220px;height: 167px;border: 1px solid #ddd;border-radius: 0.5rem;background: rgba(0,0,0,0);">';
        target = document.getElementById("file2");
        id = target.getAttribute("key");
        order = "2";
    } else {
        condAppend += '<input class="c-input" id="imgDesc1" name="fileAlt"  type="text" placeholder="사용후기 이미지에 관한 설명(선택)" title="사용후기 이미지에 관한 설명(선택) 입력" style="width:220px; position:relative;" maxlength="60">';
        fileBox = '<div class="c-file-image__item" style="width: 220px;height: 167px;border: 1px solid #ddd;border-radius: 0.5rem;background: rgba(0,0,0,0);">';
        target = document.getElementById("file1");
        id = target.getAttribute("key");
        order = "1";
    }

    var type = target.files[0].type;
    if (type.indexOf("jpg") == -1 && type.indexOf("gif") == -1 && type.indexOf("png") == -1 && type.indexOf("jpeg") == -1) {
        MCP.alert("첨부파일 확장자를 확인해주세요. 가능한 파일 확장자는 jpg,gif,png 입니다.", function() {
            $("#file" + order + "Image").next().find("i").trigger("click");
        });
        return false;
    }
    if (!validFileSize(target.files[0])) {
        MCP.alert("업로드 가능한 파일용량은 2MB입니다.", function(){
            $(".image-label" + order).find("button").trigger("click");
        });
        return false;
    }

    if ($(".staged-image").length != 4) {
        var appendHtml = "";
        appendHtml += '<div class="c-form staged-image" style="display: flex; flex-direction: column; gap: 10px; margin-top: 1rem;">';
        appendHtml += fileBox;
        appendHtml += '<div id="' + id + 'Image"></div>';
        appendHtml += '<button class="c-button">';
        appendHtml += '<span class="sr-only">삭제</span>';
        appendHtml += '<i class="c-icon c-icon--delete" aria-hidden="true"></i>';
        appendHtml += '</button>';
        appendHtml += '</div>';
        appendHtml += condAppend;
        appendHtml += '</div>';

        $(".staged_img-list").append(appendHtml);

        if ($(".staged-image").length == 1) {
            $(".c-label__sub").text("사진 1/4");
            $(".file-label").attr("for", "file2");
        } else if ($(".staged-image").length == 2) {
            $(".c-label__sub").text("사진 2/4");
            $(".file-label").attr("for", "file3");
        } else if ($(".staged-image").length == 3) {
            $(".c-label__sub").text("사진 3/4");
            $(".file-label").attr("for", "file4");
        } else {
            $(".c-label__sub").text("사진 4/4");
        }
    }

    reader.onload = function(event) {
        $("#" + id + "Image").html("등록완료");
    }

    reader.readAsDataURL(event.target.files[0]);
}

// 이미지 첨부 삭제 아이콘 클릭 이벤트
$(document).on("click", ".c-icon--delete", function() {
    var clickTarget = $(this);

    var fileId = $(clickTarget).parent().parent().find("div:first").attr("id");
    var id = fileId.substring(0,5);  // file1

    $(clickTarget).parent().parent().parent().remove();

    $(".file-label").attr("for", id);
    $("#"+ id).val('');

    var remainLength = $(".staged-image").length;
    $(".c-label__sub").text("사진 "+remainLength+"/4");
});

$(document).on("click", "#eventLink", function(){
    var parameterData = ajaxCommon.getSerializedData({
        eventPopTitle : 'M쇼핑할인 사용후기 이벤트'
    });

    openPage('eventPop', '/event/eventDetailViewAjax.do?ntcartSeq=1636', parameterData);
})