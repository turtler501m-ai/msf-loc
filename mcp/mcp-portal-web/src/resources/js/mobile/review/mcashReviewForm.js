$(document).ready(function() {

    $("#certifyPopBtn").click(function() {

        if (!$("textarea[name=reviewContent]").val() || $("textarea[name=reviewContent]") == "") {
            MCP.alert("사용후기를 작성해 주세요.");
            return false;
        }

        if ($(".c-textarea").val().trim().length < 40) {
            MCP.alert("사용후기는 40자 이상부터 등록이 가능합니다.");
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

        openPage("smsCertifyPop", "/m/mcash/review/mcashReviewCertifyPop.do", parameterData);
        if ($("input[name=phone]").val()) {
            $("#phoneNum").val($("input[name=phone]").val());
            $("#phoneNum").prop("readonly", true);
            $("#contractNum").val($("#cntrNum").val());

            // 고객에게는 마스킹 처리된 연락처 노출
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
});

$(document).on("click", "#mcashReviewRegBtn", function() {

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

    regstReview();
});

// 후기 작성하기
function regstReview() {
    var formData = new FormData();

    var imgFiles = $("input[name=file]");
    imgFiles.each(function(index, item) {
        if ($(item)[0].files[0]) {
            var descIndex = index + 1;
            var alt = $("#imageDesc" + descIndex).val() ? $("#imageDesc" + descIndex).val() : ' ';
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

function validFileSize(file) {
    var result = true;
    if (file.size/1024/1024 > 2) {
        result = false;
    }
    return result;
}

// 이미지 첨부 시 미리보기, 첨부란 추가
$(document).on("change", ".upload-image__hidden", function() {
    var reader = new FileReader();
    var labelEl = $(this).parent();
    var currentLabelIndex = parseInt(labelEl.attr('index'));
    var maxImage = $("#maxImage").val();
    var currentImageLength = $(".complete-label").length;
    var target = $(this);
    var file = $(this)[0].files[0];

    // 이미지 파일 형식, 크기 확인
    if (!validateFile(file)) {
        return false;
    }

    target.siblings(".c-icon").hide();
    target.siblings(".u-co-gray").hide();
    target.siblings(".upload-image__delete").show();
    target.prop('disabled', true);
    labelEl.css("padding", "0px");

    // 현재 추가한 이미지에 대한 설명란 생성
    $("#altDiv").append(appendUploadDescHtml(currentLabelIndex));

    // 다음 이미지 추가 버튼 생성
    if (currentImageLength < maxImage - 1) {
        $("#imageDiv").append(getUploadLabelHtml(currentLabelIndex + 1));
    }

    // 업로드 완료 시
    var completeDiv = target.siblings(".upload-complete");
    reader.onload = function(e) {
        completeDiv.width(95);
        completeDiv.height(72);
        completeDiv.attr("style", "position: relative; top: 25px;");
        completeDiv.html("등록완료");
        completeDiv.show();

        labelEl.prop("disabled", true);
        labelEl.addClass('complete-label');
    }

    reader.readAsDataURL(this.files[0]);
});

function validateFile(file) {
    var fileType = file.type;
    if (fileType.indexOf("jpg") == -1 && fileType.indexOf("gif") == -1 && fileType.indexOf("png") == -1 && fileType.indexOf("jpeg") == -1 ) {
        MCP.alert("첨부파일 확장자를 확인해주세요. 가능한 파일 확장자는 jpg, gif, png 입니다.");
        return false;
    }

    if (!validFileSize(file)) {
        MCP.alert("업로드 가능한 파일용량은 2MB입니다.");
        return false;
    }

    return true;
}

function getUploadLabelHtml(index) {
    var appendHtml ="";

    appendHtml += `<label class='upload-image__label' index='${index}'>`;
    appendHtml += `    <span class='c-hidden'>파일선택</span>`;
    appendHtml += `    <input class='upload-image__hidden' key='file${index}' name='file' type='file' id='fileTarget${index}' accept='.jpg, .gif, .png'>`;
    appendHtml += `    <i class='c-icon c-icon--photo' aria-hidden='true'></i>`;
    appendHtml += `    <span class='u-co-gray' id='imgCnt'>사진 ${index - 1} / 4</span>`;
    appendHtml += `    <button class='upload-image__delete' style='display:none;'>`;
    appendHtml += `        <i class='c-icon c-icon--delete' aria-hidden='true'></i>`;
    appendHtml += `        <span class='c-hidden'>삭제</span>`;
    appendHtml += `    </button>`;
    appendHtml += `    <div class="upload-complete" id='file${index}'></div>`;
    appendHtml += `</label>`;

    return appendHtml;
}

function appendUploadDescHtml(uploadImageIndex) {
    var appendHtmlDesc = "";
    appendHtmlDesc += `<input class='c-input image-desc' type='text' id='imageDesc${uploadImageIndex}' name='fileAlt' placeholder='${uploadImageIndex}. 사용후기 이미지에 관한 설명(선택)' title='사용후기 이미지 설명 입력' maxlength="60">`;
    return appendHtmlDesc;
}

function resetLabelIndex() {
    $.each($(".upload-image__label"), function (index, item) {
        var lableEl = $(item);
        var labelIndex = index + 1;
        var inputEl = lableEl.find(".upload-image__hidden");
        var completeDiv = lableEl.find(".upload-complete");

        lableEl.attr('index', labelIndex);
        inputEl.attr('key', 'file' + labelIndex);
        inputEl.attr('id', 'fileTarget' + labelIndex);
        completeDiv.attr('id', 'file' + labelIndex);
    });

    $.each($(".image-desc"), function (index, item) {
        var descEl = $(item);
        var descIndex = index + 1;

        descEl.attr('id', 'imageDesc' + descIndex);
        descEl.attr('placeholder', descIndex + ". 사용후기 이미지에 관한 설명(선택)");
    });
}

// 삭제 아이콘 클릭 이벤트
$(document).on("click", ".upload-image__delete", function(){
    var labelEl = $(this).parent();
    var deleteLabelIndex = parseInt(labelEl.attr('index'));
    var maxImage = $("#maxImage").val();
    var currentImageLength = $('.complete-label').length;
    var descEl = $(".image-desc").eq(deleteLabelIndex - 1);

    labelEl.remove();
    descEl.remove();

    resetLabelIndex();

    if (currentImageLength == maxImage) {
        $("#imageDiv").append(getUploadLabelHtml(maxImage));
    }

    $(".upload-image__label:last-child").find('span').text(`사진 ${currentImageLength - 1} / ${maxImage}`);
});

$(document).on("click", "#eventLink", function(){
    var parameterData = ajaxCommon.getSerializedData({
        eventPopTitle : 'M쇼핑할인 사용후기 이벤트'
    });
    openPage('eventPop', '/m/event/eventDetailViewAjax.do?ntcartSeq=1636', parameterData);
})
