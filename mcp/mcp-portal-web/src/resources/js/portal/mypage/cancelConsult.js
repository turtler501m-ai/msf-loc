var pageObj = {
    niceType: "",
    authObj: {},
    niceHistSeq: 0,
    startTime: 0,
    eventId: ""
};

const modalMap = {
    '#modalTerms1': '#benefitAgreeFlag',
    '#modalTerms2': '#clauseCntrDelFlag',
    '#modalTerms3': '#etcAgreeFlag'
};

$(window).on('load', function () {
    //일시정지 팝업
    openModal('#startModal');
});

$(document).ready(function () {
    //미성년자 팝업
    if ($("#underAge").val() === "true") openModal('#modalMinorNotAllowed');

    //팝업 내 스크롤 완료 시 동의 버튼 활성화
    initModalScrollHandlers();

    //동의 관련(체크, 스크롤)
    initAgreementHandlers();

    //본인인증 버튼
    initAuthHandlers();

    //설문
    initSurveyHandlers();

    //입력, 체크 확인, 해지신청 버튼 활성화
    initValidationBinding();

    //해지신청 직접입력
    $('#cancelMobileNo').on('change', function () {
        const isManual = $(this).val() === 'manual';
        $('#cancelMobileNoInputArea').toggle(isManual);
        checkRequiredConditions();
    });

    //해지신청
    $("#btnCancelConsult").on("click", function () {
        //필수항목 체크
        const formData = getCancelFormData();

        if (!formData) return;

        const varData = ajaxCommon.getSerializedData({
            ...formData,
            onlineAuthType: pageObj.niceResLogObj.authType,
            reqSeq: pageObj.niceResLogObj.reqSeq,
            resSeq: pageObj.niceResLogObj.resSeq
        });

        ajaxCommon.getItem({
            id: 'cancelConsultAjax',
            cache: false,
            url: '/mypage/cancelConsultAjax.do',
            data: varData,
            dataType: "json",
            async: false
        }, function (jsonObj) {
            if (jsonObj.RESULT_CODE === "00000") { //정상 신청
                openModal('#modalCancelRequestComplete');
            } else if (jsonObj.RESULT_CODE === "00002") { //미성년자
                openModal('#modalMinorNotAllowed');
            } else if(jsonObj.RESULT_CODE === "00005"){
                MCP.alert("<b>입력하신 해지 신청 번호가</b><br/>확인되지 않습니다.<br/><br/>확인 후 다시 입력 해 주세요.");
            } else if (jsonObj.RESULT_CODE === "00006") { //이미 접수
                openModal('#modalAlreadyRequested');
            } else {
                MCP.alert(jsonObj.RESULT_MSG || "처리 중 오류가 발생했습니다.");
            }
        });
    });

});

//포커스 자동옮기기
function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

//팝업 공통 오픈(신청완료, 미성년자, 중복신청 등)
function openModal(selector) {
    const el = $(selector)[0];
    const modal = KTM.Dialog.getInstance(el) || new KTM.Dialog(el);
    modal.open();
}

//팝업 내 스크롤 완료 시 동의 버튼 활성화
function initModalScrollHandlers() {
    $('[data-scroll-required]').each(function () {
        const $body = $(this);
        const $btn = $body.closest('.c-modal__content').find('[data-agree-btn]');
        $body.on('scroll', function () {
            const scrollBottom = $body[0].scrollHeight - $body.scrollTop() - $body.outerHeight();
            if (scrollBottom <= 1) {
                $btn.prop('disabled', false);
                $body.data('scrolled', true);
            }
        });
    });
}

//동의 관련(체크, 스크롤)
function initAgreementHandlers() {
    //개별 동의 체크 시 스크롤 확인
    $('._agree').on('change', function () {
        const checkbox = $(this);
        const modalId = checkbox.closest('.c-accordion__sub-check').find('[data-dialog-trigger]').data('dialog-trigger');
        const $body = $(modalId).find('[data-scroll-required]');
        if (!$body.data('scrolled')) {
            MCP.alert('상세 내용을 끝까지 확인 후 동의 해 주세요.');
            checkbox.prop('checked', false);
        }
    });

    //전체 동의
    $('#btnStplAllCheck').on('change', function () {
        const isChecked = $(this).prop('checked');
        if (isChecked) {
            const allScrolled = Object.keys(modalMap).every(modalId => $(modalId).find('[data-scroll-required]').data('scrolled'));
            if (!allScrolled) {
                MCP.alert('아직 읽지 않은 항목이 있습니다. 내용을 확인해 주세요.');
                $(this).prop('checked', false);
                return;
            }
            $.each(modalMap, (_, checkboxId) => {
                $(checkboxId).prop('checked', true).trigger('change');
            });
        } else {
            $('._agree').prop('checked', false).trigger('change');
        }
    });

    //개별 다 체크항목 다 체크 시
    $('._agree').on('change', function () {
        const allChecked = $('._agree').length === $('._agree:checked').length;
        $('#btnStplAllCheck').prop('checked', allChecked);
    });

    //팝업 열릴 때 동의 버튼 초기화
    $('[data-dialog-trigger]').on('click', function () {
        const target = $(this).data('dialog-trigger');
        const $modal = $(target);
        const $body = $modal.find('[data-scroll-required]');
        const $btn = $modal.find('[data-agree-btn]');

        $body.scrollTop(0).data('scrolled', false);
        $btn.prop('disabled', true);
        $body.off('scroll.scrollCheck');

        setTimeout(() => {
            const el = $body[0];
            if (el.scrollHeight <= el.clientHeight + 1) {
                $body.data('scrolled', true);
                $btn.prop('disabled', false);
            } else {
                $body.on('scroll.scrollCheck', function () {
                    const isAtBottom = el.scrollTop + el.clientHeight >= el.scrollHeight - 5;
                    if (isAtBottom && !$body.data('scrolled')) {
                        $btn.prop('disabled', false);
                        $body.data('scrolled', true);
                    }
                });
            }
        }, 30);
    });

    //동의 후 닫기 시 초기화
    $('[data-dialog-close]').on('click', function () {
        const $modal = $(this).closest('.c-modal');
        $modal.find('[data-scroll-required]').scrollTop(0).data('scrolled', false);
    });

    //동의 후 닫기 체크처리
    $.each(modalMap, (modalId, checkboxId) => {
        $(`${modalId} .c-modal__footer button`).on('click', function () {
            $(modalId).find('[data-scroll-required]').data('scrolled', true);
            $(checkboxId).prop('checked', true).trigger('change');
        });
    });
}

//본인인증 버튼
function initAuthHandlers() {
    $("._btnNiceAuthBut").click(function () {
        if (!$("#niceChkAgree").is(":checked")) {
            MCP.alert("본인인증 절차에 동의해 주세요.");
            return false;
        }
        if ($("#isAuth").val() === "1") {
            MCP.alert("본인 인증을 완료 하였습니다.");
            return false;
        }

        const authType = $(this).data("onlineAuthType");
        (authType === "M") ? pop_nice() : pop_credit();
    });
}

/** 휴대폰 인증창 요청 */
function pop_nice() {
    pageObj.niceType = NICE_TYPE.CUST_AUTH;
    openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', { width: '500', height: '700' });
}

/** 신용카드 인증창 요청 */
function pop_credit() {
    pageObj.niceType = NICE_TYPE.CUST_AUTH;
    openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=C', { width: '500', height: '700' });
}

//본인인증 리턴
function fnNiceCert(obj) {
    pageObj.niceResLogObj = obj;

    const authInfoJson = { ncType: "0" };
    const isAuthDone = diAuthCallback(authInfoJson);

    if (isAuthDone) {
        const isCard = obj.authType === "C";
        const btns = $("._btnNiceAuthBut");

        btns.eq(isCard ? 1 : 0).addClass("is-active")
            .html(`<i class="c-icon ${isCard ? 'c-icon--card' : 'c-icon--phone'}" aria-hidden="true"></i>${isCard ? '신용카드' : '휴대폰'} 인증완료`);
        $("._btnNiceAuth").eq(isCard ? 0 : 1).prop('disabled', true);

        $("#niceChkAgree").prop("disabled", true);
        MCP.alert("본인인증에 성공 하였습니다.");
        $("#isAuth").val("1");
        $(".cancel-reason-form").show();
    } else {
        const msg = diAuthObj?.resAuthOjb?.RESULT_MSG || "본인인증 한 정보가 다릅니다. 다시 본인인증을 하여 주시기 바랍니다.";
        MCP.alert(msg);
        $("#isAuth").val("");
    }
}

//설문
function initSurveyHandlers() {
    // 상세 항목 숨기기
    $(".cancel-reason-message").hide();

    // 설문1 라디오 클릭 시 상세 항목 보이기
    $("input[name='survey1Cd']").on("change", function () {
        const val = $(this).val();
        $(".cancel-reason-message").hide();
        $("#survey1Text").closest(".c-form").hide();
        $("input[name='survey2Cd']").prop("checked", false);
        $("#survey1Text").val("");

        if (["01", "02", "03"].includes(val)) {
            $(this).nextAll(".cancel-reason-message:first").show();
        } else if (val === "05") {
            $("#survey1Text").closest(".c-form").show();
        }
    });
}

//필수항목 체크 및 form 데이터
function getCancelFormData() {
    const benefit = $("#benefitAgreeFlag").is(":checked");
    const clause = $("#clauseCntrDelFlag").is(":checked");
    const etc = $("#etcAgreeFlag").is(":checked");
    const isAllAgreementChecked = benefit && clause && etc && $("#chkAgree").is(":checked");

    const r1 = $("#receiveMobileNo1").val().trim();
    const r2 = $("#receiveMobileNo2").val().trim();
    const r3 = $("#receiveMobileNo3").val().trim();
    const isMobileValid = r1.length === 3 && r2.length === 4 && r3.length === 4;
    const mobile = [r1, r2, r3].join("");

    const contractNum = $("#cancelMobileNo").val().trim();
    const isDirectInput = contractNum === "manual";

    let c1 = "", c2 = "", c3 = "", cancelMobileNo = "", isCancelMobileValid = false;
    if (isDirectInput) {
        c1 = $("#cancelMobileNo1").val().trim();
        c2 = $("#cancelMobileNo2").val().trim();
        c3 = $("#cancelMobileNo3").val().trim();
        isCancelMobileValid = c1.length === 3 && c2.length === 4 && c3.length === 4;
        cancelMobileNo = [c1, c2, c3].join("");
    } else {
        isCancelMobileValid = contractNum.length > 0;
    }

    const s1 = $("input[name='survey1Cd']:checked").val();
    const s1Text = $("#survey1Text").val().trim();
    const s2 = $("input[name='survey2Cd']:checked").val();
    const score = $("input[name='surveyScore']:checked").val();
    const suggestion = $("#surveySuggestion").val().trim();

    //Validation
    if (!isAllAgreementChecked) return MCP.alert("모든 필수 동의 항목에 체크해 주세요.");
    if (!isMobileValid) return MCP.alert("연락 가능한 연락처를 정확히 입력해주세요.");
    if (!isCancelMobileValid) return MCP.alert("고객님께서 사용 중이신 번호를 올바르게 기입해주세요.");
    if (!s1) return MCP.alert("해지 사유를 선택해주세요.");
    if (s1 === "03" && !s2) return MCP.alert("서비스 품질 문제의 상세 항목을 선택해주세요.");
    if (s1 === "05" && !s1Text) return MCP.alert("기타 사유를 입력해주세요.");
    if (!score) return MCP.alert("만족도 체크를 해주세요.");

    //설문
    const reasonMap = {
        "01": "요금 부담이 커서",
        "02": "데이터/통화 혜택이 부족해서",
        "03": "서비스 품질 문제(통화끊김, 속도 등)",
        "04": "단순 사용 종료",
        "05": s1Text
    };

    const s2TextMap = {
        "01": "통화 품질이 불만족스러웠다",
        "02": "고객센터 응대가 미흡했다",
        "03": "멤버십/부가 혜택이 부족했다"
    };

    const data = {
        receiveMobileNo: mobile,
        contractNum: contractNum,
        cancelMobileNo: cancelMobileNo,
        survey1Cd: s1,
        survey1Text: reasonMap[s1],
        surveyScore: score,
        surveySuggestion: suggestion
    };

    if (s2) data.survey2Cd = s2;
    if (s1 === "03") data.survey2Text = s2TextMap[s2];

    return data;
}


//입력, 체크 확인
function initValidationBinding() {
    const inputs = "#receiveMobileNo1, #receiveMobileNo2, #receiveMobileNo3, #cancelMobileNo, #cancelMobileNo1, #cancelMobileNo2, #cancelMobileNo3";
    const checkboxes = "._agree, #chkAgree";
    const radios = "input[name='survey1Cd'], input[name='survey2Cd'], input[name='surveyScore']";

    $(inputs).on("input", checkRequiredConditions);
    $(checkboxes).on("change", checkRequiredConditions);
    $(radios).on("change", checkRequiredConditions);
    $("#survey1Text").on("input", checkRequiredConditions);
}

//해지신청 버튼 활성화
function checkRequiredConditions() {
    const isAllChecked = ['#benefitAgreeFlag', '#clauseCntrDelFlag', '#etcAgreeFlag', '#chkAgree'].every(id => $(id).is(":checked"));

    //연락가능 연락처 체크
    const r1 = $("#receiveMobileNo1").val().trim();
    const r2 = $("#receiveMobileNo2").val().trim();
    const r3 = $("#receiveMobileNo3").val().trim();
    const isMobileValid = r1.length === 3 && r2.length === 4 && r3.length === 4;

    //해지 신청 번호 체크
    const contractNum = $("#cancelMobileNo").val().trim();
    const isDirectInput = contractNum === "manual";

    let isContractNumValid = false;
    if (isDirectInput) {
        const c1 = $("#cancelMobileNo1").val().trim();
        const c2 = $("#cancelMobileNo2").val().trim();
        const c3 = $("#cancelMobileNo3").val().trim();
        isContractNumValid = c1.length === 3 && c2.length === 4 && c3.length === 4;
    } else {
        isContractNumValid = contractNum.length > 0;
    }

    //설문 체크
    const survey1 = $("input[name='survey1Cd']:checked").val();
    const surveyScore = $("input[name='surveyScore']:checked").val();

    let isSurveyValid = !!survey1;
    if (survey1 === "03") {
        isSurveyValid = !!$("input[name='survey2Cd']:checked").val();
    } else if (survey1 === "05") {
        isSurveyValid = $("#survey1Text").val().trim().length > 0;
    }

    //본인 인증 완료 여부
    const isAuthDone = $("#isAuth").val() === "1";

    const isValid = isAllChecked && isMobileValid && isContractNumValid && !!survey1 && !!surveyScore && isSurveyValid && isAuthDone;

    $("#btnCancelConsult").toggleClass("is-disabled", !isValid);
}
