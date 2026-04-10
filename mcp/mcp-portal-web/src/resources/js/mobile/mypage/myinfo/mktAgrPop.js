
var checkInheritance = [];

$(document).ready(function (){
	initCheck();

	$("#setMktArgBtn").click(function(){

		var contractNum = $("#contractNum").val();
		var checkNoChange = true;

		document.getElementsByName('volutionAgree').forEach((data) => {
			var checkVal = $('#'+$(data).attr('dtl-cd')).is(':checked') ? 'Y': 'N';
			if (checkVal != data.value) {
				checkNoChange = false;
				return false;
			}
		})
		if (checkNoChange) {
			MCP.alert("현재 상태값과 동일합니다.");
			return false;
		}

		KTM.Confirm("변경하시겠습니까?", function () {
			this.close();
			var varData = ajaxCommon.getSerializedData({
				contractNum : contractNum,
				personalInfoCollectAgree : $('#personalInfoCollectAgree').is(':checked') ? 'Y' : 'N',
				mktAgrYn : $('#mrkAgrYn').is(':checked') ? 'Y' : 'N',
				othersTrnsAgree : $('#othersTrnsAgree').is(':checked') ? 'Y' : 'N',
				othersTrnsKtAgree : $('#othersTrnsKtAgree').is(':checked') ? 'Y' : 'N',
				othersAdReceiveAgree : $('#othersAdReceiveAgree').is(':checked') ? 'Y' : 'N'
			});

			ajaxCommon.getItem({
				id:'setMktAgrYnAjax'
				,cache:false
				,url:'/mypage/setMktAgrYnAjax.do'
				,data: varData
				,dataType:"json"
			},function(jsonObj){
				 if (jsonObj.RESULT_CODE == "S") {
					 MCP.alert("수정을 완료하였습니다.",function(){
						$('.c-icon--close').trigger('click');
					});
				 } else {
					 MCP.alert(jsonObj.RESULT_MSG);
				 }
			});
		})

	});
});

var initCheck = function() {
	document.getElementsByName('volutionAgree').forEach((volution) => {
		$('#' + $(volution).attr('dtl-cd')).prop('checked', $(volution).val() === 'Y');
	});

	document.getElementsByName('volutionTm').forEach((volutionTime) => {
		var dtlCd = $(volutionTime).attr('dtl-cd');

		if (dtlCd === "othersTrnsAgree" || dtlCd === "othersTrnsKtAgree") {
			return true;
		}

		var checkText = $('#' + dtlCd).is(':checked') ? '수신/동의일자' : '수신거부/거부일자';
		var agreeTime = $(volutionTime).val() ? $(volutionTime).val() : "-";
		var addHtml = `<span> ${checkText}(${agreeTime}) </span>`;

		$('label[for=' + dtlCd + ']').append(addHtml);
	});

	applyCheckedWrapList();

	initAgreeWrapTime();
	initOthersTrnsAgreesTime();
}

var initAgreeWrapTime = function() {
	var agreeWrapList = $(".agreeWrap");
	agreeWrapList.each(function(index, agreeWrap){
		var agreeWrapTime = getAgreeWrapTime(agreeWrap);
		var checkText = agreeWrap.checked ? '수신/동의일자' : '수신거부/거부일자';
		var addHtml = `<span> ${checkText}(${agreeWrapTime}) </span>`;

		$('label[for=' + agreeWrap.id + ']').append(addHtml);
	});
}

var initOthersTrnsAgreesTime = function() {
	var othersTrnsAgreeTime = $("#othersTrnsAgreeTime").val() ? $("#othersTrnsAgreeTime").val() : "-";
	var othersTrnsKtAgreeTime = $("#othersTrnsKtAgreeTime").val() ? $("#othersTrnsKtAgreeTime").val() : "-";

	var othersTrnsAgreeMessage = $("#othersTrnsAgree").is(":checked") ? '수신/동의일자' : '수신거부/거부일자';
	othersTrnsAgreeMessage += "(" + othersTrnsAgreeTime + ")";
	var othersTrnsAgreeKtMessage = $("#othersTrnsKtAgree").is(":checked") ? '수신/동의일자' : '수신거부/거부일자';
	othersTrnsAgreeKtMessage += "(" + othersTrnsKtAgreeTime + ")";

	var agreeTimeHtml = '';
	agreeTimeHtml += `<ul class="c-text-list c-bullet c-bullet--dot">`;
	agreeTimeHtml += `	<li class="c-text-list__item">kt M mobile &rarr; KT 그룹사 ${othersTrnsAgreeMessage}</li>`;
	agreeTimeHtml += `	<li class="c-text-list__item">KT &rarr; kt M mobile ${othersTrnsAgreeKtMessage}</li>`;
	agreeTimeHtml += `</ul>`;
	$("#othersTrnsAgreesBox").prepend(agreeTimeHtml);
}

var handleOptionalAgreeClick = function(agree) {
    if (agree.checked && !validateRequiredAgree(getRequiredAgreeChecks(agree), true)) {
        agree.checked = false;
        return false;
    }

    uncheckSecondaryIfRequiredUnchecked();
    applyCheckedWrapList();

}

var validateRequiredAgree = function(requiredAgreeChecks, isAlert) {
    var isCheckedRequiredAgrees = true;
    if (requiredAgreeChecks.length > 0) {
        requiredAgreeChecks.each(function(index, requiredAgree) {
            if (!requiredAgree.checked) {
                isAlert && MCP.alert(`${getAgreeName(requiredAgree.id)}가 필요합니다.`);

                isCheckedRequiredAgrees = false;
                return false;
            }
        });
    }

    return isCheckedRequiredAgrees;
}

var handleOptionalAgreeWrapClick = function(agreeWrap) {
    if (agreeWrap.checked && !validateRequiredAgreeInWrap(agreeWrap)) {
        agreeWrap.checked = false;
        return true;
    }

    checkChildAgrees(agreeWrap);
    uncheckSecondaryIfRequiredUnchecked();
    applyCheckedWrapList();
}

var validateRequiredAgreeInWrap = function(agreeWrap) {
    var agreeChecks = getAgreeChecks(agreeWrap);
    var secondaryAgreeChecks = agreeChecks.filter('[required-agree-id]');
    for (var i = 0; i < secondaryAgreeChecks.length; i++) {
        var requiredAgreeChecks = getRequiredAgreeChecks(secondaryAgreeChecks[i]);
        if (!validateRequiredAgree(requiredAgreeChecks.not(agreeChecks), false)) {
            MCP.alert(`${getAgreeName(secondaryAgreeChecks[i].getAttribute("required-agree-id"))}가 필요합니다.`);
            return false;
        }
    }
    return true;
}

var getRequiredAgreeChecks = function(agree) {
    var requiredAgreeId = agree.getAttribute('required-agree-id');
    if (requiredAgreeId) {
        return getAgreeChecks($("#" + requiredAgreeId)[0]);
    }
    return [];
}

var getAgreeName = function(agreeId) {
    return $("label[for='" + agreeId + "']")
        .contents()
        .filter(function() {
            return this.nodeType === Node.TEXT_NODE;
        })
        .text().trim();
}

var uncheckSecondaryIfRequiredUnchecked = function() {
    $("[required-agree-id]").each(function(index, agree) {
        if (!validateRequiredAgree(getRequiredAgreeChecks(agree), false)) {
            agree.checked = false;
        }
    })
}

var applyCheckedWrapList = function() {
    $(".agreeWrap").each(function(index, agreeWrap) {
        applyCheckedWrap(agreeWrap);
    });
}

var applyCheckedWrap = function (agreeWrap) {
    var isCheckedWrap = true;
    var agreeChecks = getAgreeChecks(agreeWrap);
    agreeChecks.each(function(index, agreeCheck) {
        if (!agreeCheck.checked) {
            isCheckedWrap = false;
            return false;
        }
    });

    agreeWrap.checked = isCheckedWrap;
}

var checkChildAgrees = function (agreeWrap) {
    var agreeChecks = getAgreeChecks(agreeWrap);
    agreeChecks.each(function(index, agreeCheck) {
        agreeCheck.checked = agreeWrap.checked;
    });
}

var getAgreeChecks = function (agreeWrap) {
    return $(agreeWrap).closest('.c-accordion__item').find('.agreeCheck');
}

var getAgreeWrapTime = function(agreeWrap) {
    var wrapTime = "";
    var childAgrees = getAgreeChecks(agreeWrap);
    if (agreeWrap.checked) {
        childAgrees.each(function (index, agree) {
            var agreeTime = $("input[name=volutionTm][dtl-cd=" + agree.id + "]").val();
            if (wrapTime < agreeTime) {
                wrapTime = agreeTime;
            }
        });
    } else {
        childAgrees.each(function (index, agree) {
            if (agree.checked) {
                return true;
            }

            var agreeTime = $("input[name=volutionTm][dtl-cd=" + agree.id + "]").val();
            if (wrapTime < agreeTime) {
                wrapTime = agreeTime;
            }
        });
    }

    return wrapTime;
}