$(document).ready(function(){

    // 예약가능 시간대가 존재하지 않은 경우, 문구 표시
    if($("#timeListBody tr").length <= 0){
        var emptyHtml= "";
        emptyHtml+= "<tr>";
        emptyHtml+=   "<td colspan='2'>선택하신 날짜에 예약이 모두 완료되었습니다.<br>다른 날짜로 선택 부탁드립니다.</td>";
        emptyHtml+= "</tr>";
        $("#timeListBody").html(emptyHtml);
    }

    // 시간대 선택
    $("input[name=timeList]").change(function(){
        if($(this).is(":checked")){
            $("#checkBtn").prop("disabled",false);
        } else {
            $("#checkBtn").prop("disabled",true);
        }
    });

});

function btnCheck(){

    var value= ajaxCommon.isNullNvl($('input[name=timeList]:checked').val(),"");
    if(value == ""){
        MCP.alert("상담 예약 시간대를 선택해 주세요");
        return false;
    }

    // 부모 화면에 선택 시간대 표시
    $('#timeChk').html($('input[name=timeList]:checked').attr("id"));
    $('#hiddenTimeChk').val(value);

    // 예약상담 등록하기 버튼 상태 체크
    btnChk();
};


