$(document).ready(function() {

    stepFn(1, 0);

    $(document).on("keydown", "#tmpKey", function(e) {
        if (e.keyCode == 13) {
            goSubmit();
        }
    });
    $('#tmpKey').focus();


    $(document).on("click", ".setAddr1", function() {
        $('.setAddrDiv').show();
        $(".setAddr2").show();
        $(".setAddr3").hide();

        var data = $($(this).data());
        var roadAddrPart1 = data.find('roadAddrPart1').text();
        var zipNo = data.find('zipNo').text();
        var jibunAddr = data.find('jibunAddr').text();
        //좌표
        var admCd = data.find('admCd').text();
        var rnMgtSn = ajaxCommon.isNullNvl(data.find('rnMgtSn').text(),data.find('rdMgtSn').text());
        var udrtYn = data.find('udrtYn').text();
        var buldMnnm = data.find('buldMnnm').text();
        var buldSlno = data.find('buldSlno').text();
        // 좌표

        $("#setAddr2").data(data);
        $("#addr1Text").text(roadAddrPart1);
        $("#zipText").text(zipNo);
        $("#addrDetail").val("");

        $('.setAddr2').css('display','block');

        $("#entY").val("");
        $("#entX").val("");
        $("#psblYn").val("N");
        var entY ="";
        var entX ="";
        var dataObj = getJusoAddrCoordApi(admCd,rnMgtSn,udrtYn,buldMnnm,buldSlno);
        if(dataObj !=null && dataObj.length > 1){
            entY = ajaxCommon.isNullNvl(dataObj[1],"")+"";
            entX = ajaxCommon.isNullNvl(dataObj[0],"")+"";
            if(entY !="" && (entY.indexOf(".") > -1) ){
                entY = Number(entY).toFixed(4);
            }
            if(entX !="" && (entX.indexOf(".") > -1) ){
                entX = Number(entX).toFixed(4);
            }
        }
        $("#entY").val(entY);
        $("#entX").val(entX);

        stepFn(2, 1);
        stepFn(3, 0);
    });


    $("#setAddr2").click(function() {

        var psblYn = $("#psblYn").val();
        var bizOrgCd = $("#bizOrgCd").val();
        if(bizOrgCd ==""){
            psblYn = "N";
        }

        if($.trim($('#addrDetail').val()) == "") {
            MCP.alert("상세주소를 입력해 주세요.");
            $('#addrDetail').focus();
            return;
        }

        var data = $($(this).data());

        var roadAddrPart1 = data.find('roadAddrPart1').text();
        var roadAddrPart2 = data.find('roadAddrPart2').text();
        var engAddr = data.find('engAddr').text();
        var jibunAddr = data.find('jibunAddr').text();
        var admCd = data.find('admCd').text();
        var rnMgtSn = data.find('rnMgtSn').text();
        var bdMgtSn = data.find('bdMgtSn').text();
        var zipNo = data.find('zipNo').text();
        var addrDetail = $("#addrDetail").val();
        var udrtYn = data.find('udrtYn').text();
        var buldMnnm = data.find('buldMnnm').text();
        var buldSlno = data.find('buldSlno').text();

        var roadFullAddr = roadAddrPart1;
        if(addrDetail != "" && addrDetail != null){
            roadFullAddr += ", " + addrDetail;
        }
        if(roadAddrPart2 != "" && roadAddrPart2 != null){
            roadFullAddr += " " + roadAddrPart2;
        }
        var acceptTime = $("#acceptTime").val();
        var entY = $("#entY").val();
        var entX = $("#entX").val();
        var nfcYn = $("#nfcYn").val();

        $("#roadFullAddr").val(roadFullAddr);
        $("#roadAddrPart1").val(roadAddrPart1);
        $("#roadAddrPart2").val(roadAddrPart2);
        $("#jibunAddr").val(jibunAddr);

        goDlvryChk(zipNo,roadAddrPart1,jibunAddr,admCd,rnMgtSn,udrtYn,buldMnnm,buldSlno,entY,entX,addrDetail,nfcYn);
    });


    /**
     * 주소 입력
     * 한글, 숫자, 영문, 공백, 기본 특수문자( . - , ( ) / ) 총 6종
     * 허용
     */
    function cleanAddress(value) {
        if (!value) return "";

        // 허용 문자:
        // - 한글 자모 확장: \u1100-\u11FF
        // - 한글 자모:      \u3131-\u318E
        // - 완성형 한글:    \uAC00-\uD7A3
        // - 숫자/영문/공백/.,()/\\-#&+
        const allowed = /[\u1100-\u11FF\u3131-\u318E\uAC00-\uD7A30-9A-Za-z\s.,()#+\/-]/g;  //.,()/\\-#&+

        const matched = value.match(allowed);
        return matched ? matched.join("") : "";
    }

 // 주소입력
    $("#setAddr3").click(function() {
        if($.trim($('#addrDetail').val()) == ''){
            MCP.alert('상세주소를 입력해 주세요.', function (){
                $('#addrDetail').focus();
            });
            return false;
        }
        var roadFullAddr = $("#roadFullAddr").val();
        var roadAddrPart1 = $("#roadAddrPart1").val();
        var addrDetail = $("#addrDetail").val();
        var roadAddrPart2 = $("#roadAddrPart2").val();
        var jibunAddr = $("#jibunAddr").val();
        var zipNo = $("#zipNo").val();
        var psblYn = $("#psblYn").val();
        var bizOrgCd = $("#bizOrgCd").val();
        var acceptTime = $("#acceptTime").val();
        var entY = $("#entY").val();
        var entX = $("#entX").val();
        roadFullAddr = roadFullAddr.replace(/[?&=]/gi,' ');
        roadAddrPart1 = roadAddrPart1.replace(/[?&=]/gi,' ');
        addrDetail = cleanAddress(addrDetail);
        roadAddrPart2 = roadAddrPart2.replace(/[?&=]/gi,' ');
        jibunAddr = jibunAddr.replace(/[?&=]/gi,' ');



        dlvryJusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,jibunAddr,zipNo,psblYn,bizOrgCd,acceptTime,entY,entX);
        $('.c-icon--close').trigger('click');
    });


});


//특수문자, 특정문자열(sql예약어의 앞뒤공백포함) 제거
function checkSearchedWord(objVal){

    if(objVal.length >0){
        //특수문자 제거
        var expText = /[%=><]/ ;
        if(expText.test(objVal) == true){
            MCP.alert("특수문자를 입력 할수 없습니다.") ;
            objVal = objVal.split(expText).join("");
            return false;
        }

        //특정문자열(sql예약어의 앞뒤공백포함) 제거
        var sqlArray = new Array(
            //sql 예약어
            "OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC",
                      "UNION",  "FETCH", "DECLARE", "TRUNCATE"
        );

        var objValUpper = objVal.toUpperCase();

        var regex ;
        var regex_plus ;
        for(var i=0; i<sqlArray.length; i++){

            if (objValUpper.indexOf(sqlArray[i]) >-1) {
                MCP.alert("\"" + sqlArray[i]+"\"와(과) 같은 특정문자로 검색할 수 없습니다.");
                return false;
            }
        }
    }
    return true ;
}


function goSubmit(param) {
    $('.setAddrDiv').hide();
    $(".setAddr2").show();
    $(".setAddr3").hide();
    $('#addrDetail').val('').prop('readonly', false);
    $('#zipText').html('');
    $('#addr1Text').html('');
    $('.setAddr2').css('display','none');
    if(param) {
        $('#currentPage').val(param);
    } else {
        $('#currentPage').val(1)
    }

    if($('#tmpKey').val() == "") {
        MCP.alert("검색어를 입력해 주세요");
        $('#tmpKey').focus();
        return;
    }

    if (!checkSearchedWord($('#tmpKey').val())) {
        return ;
    }

    $('#keyword').val($('#tmpKey').val());

    try {

        var varData = ajaxCommon.getSerializedData({
            currentPage:$("#currentPage").val()
            ,countPerPage:$('#countPerPage').val()
            ,keyword:$('#keyword').val()
        });

        $.ajax({
            type:"post",
            url : "/addrlink/addrLinkApi.do",
            data : varData,
            dataType : "xml",
            error: function(jqXHR, textStatus, errorThrown){
                return;
            },
            complete: function(){

            },
            success:	function(data){
                var xmlStr = data;

                var errorCode = $(xmlStr).find('errorCode').text();
                var errorMsg = $(xmlStr).find('errorMessage').text();
                var totalCount = $(xmlStr).find('totalCount').text();
                var currentPage = $(xmlStr).find('currentPage').text();
                var countPerPage = $(xmlStr).find('countPerPage').text();

                var index = (currentPage-1) * countPerPage + 1;
                var totalPage = parseInt(totalCount / countPerPage);
                if((totalCount % countPerPage) > 0) {
                    totalPage = totalPage + 1;
                }

                $('#dlvryInfoCnt').html('&nbsp;('+totalCount+'건)');

                var htmlStr = "";
                if(totalCount > 0 && errorCode == "0") {
                    $('#searchList').empty();
                    $(xmlStr).find('juso').each(function() {
                        htmlStr = "";
                        htmlStr += '<li class="addr-result__item">';
                        htmlStr += '    <a href="javascript:void(0);" class="addr-result__anchor setAddr1"> ';
                        htmlStr += '        <strong class="addr-result__number"><span class="c-hidden">우편번호</span>'+ $(this).find('zipNo').text() + '</strong>';
                        htmlStr += '        <p class="addr-result__text">';
                        htmlStr += '            <span class="c-text-label c-text-label--mint">도로명</span>';
                        htmlStr += 				$(this).find('roadAddr').text();
                        htmlStr += '        </p>';
                        htmlStr += '        <p class="addr-result__text">';
                        htmlStr += '            <span class="c-text-label c-text-label--gray">지번</span>';
                        htmlStr += 				$(this).find('jibunAddr').text()+' '+$(this).find('bdNm').text();
                        htmlStr += '        </p>';
                        htmlStr += '    </a>';
                        htmlStr += '</li>';
                        index ++;

                        $('#searchList').append(htmlStr);
                        $('#searchList li:last a').data($(this));
                    });
                    stepFn(1, 1);

                } else {

					htmlStr += '<div class="c-nodata">';
                    htmlStr += '    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
                    htmlStr += '    <p class="c-nodata__text">검색 결과가 없습니다.</p>';
                    htmlStr += '</div>';

                    $('#searchList').html(htmlStr);
                    stepFn(1, 0);
                    stepFn(1, 1);
                }

                stepFn(3, 1);

                if(totalCount > 0 && errorCode == "0") {

                    var pageSize = 5; //페이지 사이즈
                    var firstPageNoOnPageList = parseInt((currentPage - 1) / pageSize ) * pageSize + 1;
                    var lastPageNoOnPageList = (firstPageNoOnPageList + pageSize - 1);
                    if (lastPageNoOnPageList > totalPage) {
                        lastPageNoOnPageList = totalPage;
                    }

                    var pageFirst = '';
                    var pageLeft = '';

                    if(currentPage > 1 ) {
                        if (firstPageNoOnPageList > pageSize) {
                            pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
                            pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+ (firstPageNoOnPageList-1) +');"><span>이전</span></a>';
                        } else {
                            pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
                            pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(currentPage-1)+');"><span>이전</span></a>';
                        }
                    } else {
                        pageFirst = '<a class="c-button is-disabled" href="javascript:void(0);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
                        pageLeft = '<a class="c-button is-disabled" href="javascript:void(0);"><span>이전</span></a>';
                    }

                    var pageStr = ""
                    for (var i = firstPageNoOnPageList; i <= lastPageNoOnPageList; ++i) {
                        if (i == currentPage) {
                            pageStr += '<a class="c-paging__anchor c-paging__anchor--current c-paging__number" href="javascript:void(0);"><span class="c-hidden">현재 페이지</span>' + i + '</a>';
                        } else {
                            pageStr += '<a class="c-paging__anchor c-paging__number" href="javascript:void(0);" onclick="goSubmit(' + i + ')"><span class="c-hidden">현재 페이지</span>' + i + '</a>';
                        }
                    }

                    var pageLast = '';
                    var pageRight = '';

                    if(totalPage > currentPage ){
                        if (lastPageNoOnPageList < totalPage) {
                            pageRight = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(firstPageNoOnPageList + pageSize)+');"><span>다음</span></a>';
                            pageLast = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
                        } else {
                            pageRight = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(currentPage+1)+');"><span>다음</span></a>';
                            pageLast = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
                        }
                    } else {
                        pageRight = '<a class="c-button is-disabled" href="javascript:void(0);"><span>다음</span></a>';
                        pageLast = '<a class="c-button is-disabled" href="javascript:void(0);"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
                    }

                    var pagingHtml = "";
                    pagingHtml += pageFirst;
                    pagingHtml += pageLeft;
                    pagingHtml += pageStr;
                    pagingHtml += pageRight;
                    pagingHtml += pageLast;

                    if(totalPage > 1){
                        $(".c-paging").html(pagingHtml);

                    }else{
                        $(".c-paging").html('');

                    }
                }else{
                    $(".c-paging").html('');

                }

                stepFn(2, 0);
                $('#directTxt1').hide();
                $('#directTxt2').hide();
            }

        });

    }
    catch(e){
        MCP.alert(e.message);
    }
    finally {
    }
}

function stepFn(step, gubun) {
    if(gubun == 0){
        $('.step' + step).show();
    }else{
        $('.step' + step).hide();
    }
}

function goDlvryChk(zipNo,roadAddr,jibunAddr,admCd,rnMgtSn,udrtYn,buldMnnm,buldSlno,entY,entX,addrDetail,nfcYn){

    roadAddr = roadAddr.replace(/[?&=]/gi,' ');
    jibunAddr = jibunAddr.replace(/[?&=]/gi,' ');
    addrDetail = addrDetail.replace(/[?&=]/gi,' ');
    var varData = ajaxCommon.getSerializedData({
        zipNo : zipNo
        ,targetAddr1 : roadAddr // 기본주소
        ,targetAddr2 : "" // 상세주소
        ,bizOrgCd : ""
        ,entY : entY
        ,entX : entX
        ,jibunAddr : jibunAddr
        ,targetAddr2 : addrDetail
        ,nfcYn : nfcYn
    });
    $("#chkArea").html("").hide();
    $.ajax({
        type:"post",
        url : "/dlvryAddrChkAjax.do",
        data : varData,
        dataType : "json",
        error: function(jqXHR, textStatus, errorThrown){
            return;
        },
        success:	function(data){

            var rsltCd = ajaxCommon.isNullNvl(data.rsltCd,"");
            var psblYn = ajaxCommon.isNullNvl(data.psblYn,"");
            var rsltMsg = ajaxCommon.isNullNvl(data.rsltMsg,"");
            var chkHtml = "";
            var bizOrgCd = ajaxCommon.isNullNvl(data.bizOrgCd,"");
            var acceptTime = ajaxCommon.isNullNvl(data.acceptTime,"");

            var isable = false;
            if(psblYn=="Y" && bizOrgCd !=""){ // 배달가능
                isable = true;
            }

            if(isable){
                $('#directTxt1').show();
                $('#directTxt2').hide();
                $("#psblYn").val("Y");
                $("#bizOrgCd").val(bizOrgCd);
                $("#addrDetail").prop('readonly', true);
                $(".setAddr2").hide();
                $(".setAddr3").show();
                $("#entY").val(entY);
                $("#entX").val(entX);
                $("#acceptTime").val(acceptTime);
                $('#setAddr3').removeClass('is-disabled');
                $('#setAddr3').addClass('is-active');
            } else {
                $('#directTxt1').hide();
                $('#directTxt2').show();
                $("#psblYn").val("N");
                $("#bizOrgCd").val("");
                $(".setAddr2").hide();
                $(".setAddr3").show();
            }
            $("#zipNo").val(zipNo);
        }

    });

}


function getJusoAddrCoordApi(admCd,rnMgtSn,udrtYn,buldMnnm,buldSlno){

    var returnObj = null;
    var varData = ajaxCommon.getSerializedData({
        confmKey : $("#confmKey").val()
        ,resultType : 'json'
        ,admCd : admCd
        ,rnMgtSn : rnMgtSn
        ,udrtYn : udrtYn
        ,buldMnnm : buldMnnm
        ,buldSlno : buldSlno
    });

    $.ajax({
        url:"https://www.juso.go.kr/addrlink/addrCoordApi.do"									// 고객사 API 호출할 Controller URL
        ,type:"post"
        ,data:varData								// 요청 변수 설정
        ,dataType:"json"											// 데이터 결과 : JSON
        ,async : false
        ,success:function(jsonStr){									// jsonStr : 주소 검색 결과 JSON 데이터

            var errCode = jsonStr.results.common.errorCode; 		// 응답코드
            var errDesc = jsonStr.results.common.errorMessage;		// 응답메시지
            if(errCode != "0"){ 									// 응답에러시 처리
                returnObj = null;
            }else{
                if(jsonStr!= null){
                    $(jsonStr.results.juso).each(function(){

                        var entX = this.entX;
                        var entY = this.entY;
                        var firstProjection = "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs"; //from
                        var secondProjection = "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs"; //to
                        var data = proj4(firstProjection, secondProjection, [Number(entX), Number(entY)]);
                        returnObj = data;
                    });
                }
            }
        }
        ,error: function(xhr,status, error){
            returnObj = null;
        }
    });

    return returnObj;

}

function addrInChk() {
    if($.trim($('#addrDetail').val()) != ''){
        $('#setAddr2').addClass('is-active');
        $('#setAddr2').removeClass('is-disabled');
        $('#setAddr3').addClass('is-active');
        $('#setAddr3').removeClass('is-disabled');
    }else{
        $('#setAddr2').addClass('is-disabled');
        $('#setAddr2').removeClass('is-active');
        $('#setAddr3').addClass('is-disabled');
        $('#setAddr3').removeClass('is-active');

    }
}
