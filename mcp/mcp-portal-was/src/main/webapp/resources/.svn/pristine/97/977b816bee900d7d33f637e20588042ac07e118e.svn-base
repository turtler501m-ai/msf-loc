
//input byte 체크(UTF-8)
var maxByteCheck = function($obj)
{

	if($obj.attr('maxlength') && $obj.attr('maxlength')>0){
	var lsStr=$obj.val();               //이벤트가 일어난 컨트롤의 value값
      var lsStrLen=lsStr.length;          //전체길이
      var maxLen= $obj.attr('maxlength');                    //제한할 글자수 크기
      var liByte=0;                       //한글일 경우는 2 그밖에는 1을 더함
      var liLen=0;                        //substring하기위해사용
      var lsOneChar="";                   //한글자씩 검사한다.
      var lsStr2="";                      //글자수를 초과하면 제한할 수 글자전까지만 보여준다.

      for(var i=0;i<lsStrLen;i++)
      {
          //한글자추출
          lsOneChar=lsStr.charAt(i);
          //한글이면 2를 더한다.
          if(escape(lsOneChar).length>4)
          {
              liByte=liByte+3;
          }
          else if((lsOneChar =='\r' && lsStr.charAt(i+1) =='\n') || lsOneChar=='\n')
          {
              liByte=liByte+2;
          }
          else
          {
              liByte++;
          }

          //전체 크기가 maxLen을 넘지않으면
          if(liByte<=maxLen)
          {
          	lsStr2+=lsOneChar;
              liLen=i+1;
          }

      }

      //전체길이를 초과하면
      if(liByte>maxLen)
      {
      	$obj.val(lsStr2);
      	alert('한글 '+parseInt(maxLen/3)+'자 영문 '+maxLen+'자 까지만 입력해 주세요.');
      	$obj.focus();
          return false;
      }

      return true;
	}

  return true;
};

//시작 시간이 끝나는시간보다 뒤인지 체크
var dateStartEndCheck = function(sId,eId){
	var sDate=exportNumber($('#'+sId).val());
	var eDate=exportNumber($('#'+eId).val());
	if(sDate!='' && eDate!=''){
		if(parseInt(sDate)>parseInt(eDate)){
			alert('시작 일자는 종료 일자보다 뒤일 수 없습니다.');
			$('#'+sId).val('');
			$('#'+sId).focus();
			return false;
		}
	}
	return true;
};


//시작 시간이 끝나는시간보다 뒤인지 체크
var dateStartEndCheckTime = function(sVal,eVal){
	var sDate=exportNumber(sVal);
	var eDate=exportNumber(eVal);
	if(sDate!='' && eDate!=''){
		if(parseInt(sDate)>parseInt(eDate)){
			alert('시작 시간은 종료 시간보다 뒤일 수 없습니다.');
			return false;
		}
	}
	return true;
};


//숫자 추출
var exportNumber = function(a){
	if (!a || a=="") return '';

    a = new String(a);
    var regex = /[^0-9]/g;
    a = a.replace(regex, '');
    return a
};


//정규식 checkValType(타입('numberOnly','noKorean','engOnly','engNumberOnly'),input type id)
var checkValType = function(type,$obj){
	var checkType ='';
	var msg='';

	//벨류 없을시 걍 리턴
	if (!$obj.val() || $obj.val()=='') return true;

	if(type=='numberOnly'){ //숫자만
		checkType =/^[0-9]*$/;
		msg="숫자만 입력해 주세요";
	}else if(type=='noKorean'){ //한글 정규식만 이상해서 따로처리
		 checkType =/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
		 msg="한글을 입력하지 말아주세요";
		if($obj.attr(type) && $obj.attr(type)=='Y'){
			if(checkType.test($obj.val())){
				alert(msg);
				$obj.val('');
				$obj.focus();
				return false;
			}
		}
		return true;
	}else if(type=='engOnly'){ //영어만
		 checkType = /^[A-za-z]/g;
		 msg="영어만 입력해주세요";
	}else if(type=='engNumberOnly'){ //영어 또는 숫자만
		checkType = /^[A-Za-z0-9+]*$/;
		msg="영어문 또는 숫자만 입력해주세요";
	}else if(type=='email'){ //이메일 주소
		checkType = /([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
		msg="Email 타입이 잘못되었습니다.";
	}else if(type=='phone'){ //전화번호
		checkType = /^((01[1|6|7|8|9])[1-9]+[0-9]{6,7})|(010[1-9][0-9]{7})$/;
		msg="전화번호 타입이 잘못되었습니다.";
	}else if(type='korName'){
		checkType = /^[가-힣]/g;
		msg="올바른 이름을 입력해 주세요.";
	}else{
		//타입없음
		return true;
	}

	if($obj.attr(type) && $obj.attr(type)=='Y'){

		if(!checkType.test($obj.val())){
			alert(msg);
			$obj.val('');
			$obj.focus();
			return false;
		}
	}
	return true;
};


//유효성 체크
var validationCheck = function (){

	for(var i=0; i<$('input').length; i++){
		//input type text check
		if($('input').eq(i).attr('textCheck')=='check'){
			if(!$('input').eq(i).val()){
				var t = (!$('input').eq(i).attr('title') || $('input').eq(i).attr('title') =='') ? $('input').eq(i).attr('alt') : $('input').eq(i).attr('title');

				alert(t +'를 입력해 주세요.');
				$('input').eq(i).focus();
				return false;
			}
		}
		//input type text end
		var $obj = (!$('[name='+$('input').eq(i).attr('name')+']')) ?  $('#'+$('input').eq(i).attr('id')) : $('[name='+$('input').eq(i).attr('name')+']');
		//length 체크
		if(!maxByteCheck($obj)) return false;
		//넘버체크
		if(!checkValType('numberOnly',$obj)) return false;
		//한글입력x
		if(!checkValType('noKorean',$obj)) return false;
		//영어만입력
		if(!checkValType('engOnly',$obj)) return false;
		//이메일 유형체크
		if(!checkValType('email',$obj)) return false;
		//전화번호 유형체크
		if(!checkValType('phone',$obj)) return false;

	}

	//textarea check editor 전용!!!!
	for(var i=0; i<$('textarea').length; i++){
		if($('textarea').eq(i).attr('textCheck')=='check'){
			if(!$('textarea').eq(i).val() || $('textarea').eq(i).val() == '<p>&nbsp;</p>'){
				var t = (!$('textarea').eq(i).attr('title') || $('textarea').eq(i).attr('title') =='') ? $('textarea').eq(i).attr('alt') : $('textarea').eq(i).attr('title');

				alert(t +'를 입력해 주세요.');
				$('textarea').eq(i).focus();
				return false;
			}
		}
	}
	//textarea check end

	return true;
};
