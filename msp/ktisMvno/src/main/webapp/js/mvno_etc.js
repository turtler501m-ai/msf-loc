/**
 * @namespace
 */
var mvno = mvno || {};


//==================================================================
//MVNO.ETC
//==================================================================
/**
* @namespace
*/
mvno.etc = {

	/**
	 * (SAMPLE) 두 수의 합계를 구함
	 * @param {number} a  - 첫번째 숫자 
	 * @param {number} b  - 두번째 숫자
	 * @return {number} 두수의 합
	 */
	sum: function(a, b) {
		return a + b ;
	},
	
	/**
	 * 전화번호 등 세자리 모두 입력되었는지 체크
	 * @param {string} itemNameTelNum1 - 첫째자리 item name
	 * @param {string} itemNameTelNum2 - 둘째자리 item name
	 * @param {string} itemNameTelNum3 - 셋째자리 item name
	 * @return {boolean} true - 모두 입력됨.
	 */
	checkPackNum : function (itemNameTelNum1, itemNameTelNum2, itemNameTelNum3){
		if(itemNameTelNum1 || itemNameTelNum2 || itemNameTelNum3){			//하나라도 입력되었는데
			if(!itemNameTelNum1 || !itemNameTelNum2 || !itemNameTelNum3){	//하나라도 입력되지 않았다면
				return false; 
			}
		} 
		
		return true;
	},
	
	/**
	 * 전화번호, 휴대폰번호, fax번호 '-' 추가 
	 * @param {string} telNum - '-'없는 숫자만있는 전화번호
	 * @returns {String} retValue - '-' 추가된 전화번호 형식
	 */
	setTelNumHyphen : function (telNum){
		if(!telNum) return "";
		
		var str = telNum;
        var retValue = telNum;
        var len = str.length;
        
        if(len == 9){
        	if(str.substring(0,2) == 02){
        		//retValue = telNum.replace(/([0-9]{2})([0-9]+)([0-9]{4})/,"$1-$2-$3");
        		retValue = telNum.replace(/([0-9]{2})([0-9|\*]+)([0-9|\*]{4})/,"$1-$2-$3");
        	}
        }
        else if(len == 10){
        	if(str.substring(0,2) == 02){
        		//retValue = telNum.replace(/([0-9]{2})([0-9]+)([0-9]{4})/,"$1-$2-$3");
        		retValue = telNum.replace(/([0-9]{2})([0-9|\*]+)([0-9|\*]{4})/,"$1-$2-$3");
        	} else {
//        		retValue = telNum.replace(/(^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
        		retValue = telNum.replace(/(^01.{1}|[0-9]{3})([0-9|\*]+)([0-9|\*]{4})/,"$1-$2-$3");
        	}
        }
        else if(len == 11){
//        	retValue = telNum.replace(/(^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
        	retValue = telNum.replace(/(^01.{1}|[0-9]{3})([0-9|\*]+)([0-9|\*]{4})/,"$1-$2-$3");
        }
        else if(len == 12){
//        	retValue = telNum.replace(/(^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
        	retValue = telNum.replace(/(^01.{1}|[0-9]{3,4})([0-9|\*]+)([0-9|\*]{4})/,"$1-$2-$3");
        }
        
        return retValue;
	},
	
	/**
	 * 사업자번호 '-' 추가 
	 * @param {string} bizRegNum - '-'없는 숫자만있는 사업자번호
	 * @returns {String} retValue - '-' 추가된 사업자번호 형식
	 */
	setBizRegNumHyphen : function(bizRegNum){
		if(!bizRegNum) return "";
		
		var str = bizRegNum;
        var retValue = "";
        var len = str.length;
        
        if(len == 10){
        	retValue = bizRegNum.replace(/([0-9]{3})([0-9]+)([0-9]{5})/,"$1-$2-$3");
        }
        return retValue;
	},
	
	/**
	 * 주민등록번호, 법인등록번호 '-' 추가 
	 * @param {string} rrnum - '-'없는 숫자만있는 주민번호
	 * @returns {String} retValue - '-' 추가된 주민번호 형식
	 */
	setRrnumHyphen : function(rrnum){
		if(!rrnum) return "";
		
		var str = rrnum;
        var retValue = "";
        var len = str.length;
        
        if(len == 13){
//        	retValue = rrnum.replace(/([0-9]{6})([0-9]{7})/,"$1-$2");
        	retValue = rrnum.replace(/([0-9|\*]{6})([0-9|\*]{7})/,"$1-$2");
        }
        return retValue;
	},
	
	/**
	 * 전화번호 형식의 유효성 체크
	 * @param num
	 * @returns {Boolean}
	 */
	checkPhoneNumber : function (num) {
//		var regExp = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9|\*]{3,4}-?[0-9|\*]{4}$/;
		var regExp = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1}[0-9]{0,1})-?[0-9|\*]{3,4}-?[0-9|\*]{4}$/;
		
		if(!regExp.test(num)){
			return false;
		}
		return true;
	},
	
	/**
	 * 주민번호 유효성 체크
	 * @param num
	 * @returns {Boolean}
	 */
	checkRrNum : function (num) {
//		var regExp =  /^\d{6}[1234][0-9|\*]{6}$/;
		var regExp =  /^\d{6}[0-9|\*]{7}$/;
		
		if(!regExp.test(num)){
			return false;
		}
		return true;
	},
	
	/**
	 * 법인번호 유효성 체크
	 * @param num
	 * @returns {Boolean}
	 */
	checkCorpRegNum : function (num) {
		var regExp =  /^\d{6}[0-9|\*]{7}$/;
		
		if(!regExp.test(num)){
			return false;
		}
		return true;
	},
	
	/**
	 * MAC 유효성 체크
	 * @param num
	 * @returns {Boolean}
	 */
	checkMacNum : function (num) {
		var regExp =  /^([A-Fa-f0-9]{2}-){5}[A-Fa-f0-9]{2}$/;
		
		if(!regExp.test(num)){
			return false;
		}
		return true;
	},
	
	convertDateFormat : function (val) {
		if (!val) return null;
		var retVal = "";
			
		if(val.length > 8){
			retVal = val.substr(0,4) + '-' + val.substr(4,2) + '-' + val.substr(6,2) + ' ' +
    		val.substr(8,2) + ':' + val.substr(10,2) + ':' + val.substr(12,2);
		} 
		else if (val.length > 6){
			retVal = val.substr(0,4) + '-' + val.substr(4,2) + '-' + val.substr(6,2);
		}
		
		return retVal;
	},
	
	/**
	 * 개인정보수정 비밀번호 유효성체크
	 * @param pw
	 * @returns {Boolean}
	 */
	validPassword : function (pw) {
		 //8~20자리, 영문 대 소문자, 특수문자, 숫자 3가지 혼용
		 var check = /^.*(?=^.{8,20}$)(?=.*[a-zA-Z])(?=.*[!@#$%^*])(?=.*[0-9]).*$/;
		 
		 if(!check.test(pw)){
			 return false;
		 }
		 
		 return true;
	},
	
	validEng : function (val) {
		 var check =  /^[A-Za-z]/g;
		 
		 if(!check.test(val)){
			 return false;
		 }
		 
		 return true;
	},
	
	ValidAplhaNumeric : function (val) {
		 var check =  /[^A-Z|^a-z|^0-9]/gi;
		 
		 if(check.test(val)){
			 return false;
		 }
		 
		 return true;
	},
	
	/**
	 * 금액->문자,문자->금액
	 * @param obj
	 */
	isNumObj : function (obj) {
		for (var i = 0; i < obj.value.length ; i++){
			chr = obj.value.substr(i,1);		
			chr = escape(chr);
			key_eg = chr.charAt(1);
			if (key_eg == 'u'){
				key_num = chr.substr(i,(chr.length-1));			
				if((key_num < "AC00") || (key_num > "D7A3")) { 
					event.returnValue = false;
				} 			
			}
		}
		if ((event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode == 8) {
			
		} else {
			event.returnValue = false;
		}
	},
	
	comma : function(num){
		re = /(\d+)/;
		if(re.test(num)){ 
			re.exec(num); num = RegExp.$1; 
			re = /(\d+)(\d{3})/;
			while(re.test(num)){ num = num.replace(re,"$1,$2"); }
		}
	    return (num);
	},
	
	unComma : function (str){
		return str.replace(/,/g,"");
	},
	
	moneyToHan : function (str, formName, itemName){
		arrayNum=new Array("","일","이","삼","사","오","육","칠","팔","구");
		arrayUnit=new Array("","십","백","천","만","십만","백만","천만",
		                    "억","십억","백억","천억","조","십조","백조");
		arrayStr= new Array();
		len = str.length;
		hanStr = "";
		for(var i=0;i<len;i++) { arrayStr[i] = str.substr(i,1); }
		code = len;
		for(var i=0;i<len;i++) {
			code--;
			tmpUnit = "";
			if(arrayNum[arrayStr[i]] != ""){
				tmpUnit = arrayUnit[code];
				if(code>4) {
					if(( Math.floor(code/4) == Math.floor((code-1)/4)
					     && arrayNum[arrayStr[i+1]] != "") || 
					   ( Math.floor(code/4) == Math.floor((code-2)/4) 
					     && arrayNum[arrayStr[i+2]] != "")) {
						tmpUnit=arrayUnit[code].substr(0,1);
					} 
				}
			}
			hanStr +=  arrayNum[arrayStr[i]]+tmpUnit;
	    }
		
		var dhxForm = PAGE[formName];
		var cssText = "[ <span style='color:red;'>"+hanStr+"</span> 원]"; 
		dhxForm.setItemLabel(itemName, cssText);
	},
	
	/**
	 * 오늘 이후 일자 유효성 체크
	 * @param value
	 * @returns {Boolean}
	 */
	afterToday : function(value) {
		var today = new Date().format('yyyymmdd');
		if (Number(value) > Number(today)) 	return true;
		return false;
	},
	
	/**
	 * 바코드리딩시 자릿수 체크
	 * @param value
	 * @returns {String}
	 */
	isBacode : function(value,digit,mnfct,prdt) {
		if(value == '' || digit == '') return "";
		var str = '';
		
		if(prdt != 'SM-G720N0-ZW' && mnfct == '0141') { //삼성전자일경우
			str = value.substring(value.length-digit-1,value.length-1);
		}
		else {
			str = value.substring(value.length-digit,value.length);
		}
		return str; 
	},

	/**
	 * 바코드리딩시 자릿수 체크 NEW
	 * @param value
	 * @returns {String}
	 */
	isBacodeMake : function(value,prdtSrlCd) {
		var arrStr = prdtSrlCd.split("_");
	    var totalLength = value.length;								// 바코드전체길이
	    var prdtSrlLength = Number(arrStr[0]);						// 바코드자릿수
	    var prdtSrlTrimLength = Number(arrStr[2]);					// 바코드트림수
	    var stanLength = prdtSrlLength + prdtSrlTrimLength;		// 바코드설정길이
	    var baseLine = 0;
	    var str = '';
	    
	    if( stanLength > totalLength ){ // 설정된 길이보다 바코드 입력값이 부족
	    	return str;
	    }
	    if( arrStr[1] == 'B' ){ // arrStr[1]=코드생성기준( F : 앞, B : 뒤 )
	    	baseLine = totalLength - stanLength; 
	    }
	    if( arrStr[3] == 'F' ){ // arrStr[3]=트림적용기준( F : 앞, B : 뒤 ) 
	    	baseLine = baseLine + prdtSrlTrimLength; 
	    }
	    
	    str = value.substr(baseLine,prdtSrlLength);
	    
		return str; 
	},
	
	/**
	 * 바코드 생성기준 코드 조합
	 * 바코드생성기준  = 바코드자릿수_코드생성기준_바코드트림수_트림적용기준
	 * @param value
	 * @returns {String}
	 */
	fnMakePrdtSrlCode : function(form) {
		var prdtSrlLength = form.getItemValue("prdtSrlLength");					// 바코드자릿수
		var prdtSrlStandard = form.getItemValue("prdtSrlStandard");			// 코드생성기준
		var prdtSrlTrimLength = form.getItemValue("prdtSrlTrimLength");		// 바코드트림수
		var prdtSrlTrimStandard = form.getItemValue("prdtSrlTrimStandard");	// 트림적용기준
		var prdtSrlCode = "";															// 바코드생성기준
		
		if( prdtSrlLength == "" ){ prdtSrlLength = 0; }
		if( prdtSrlTrimLength == "" ){ prdtSrlTrimLength = 0; }
		if( Number(prdtSrlTrimLength) > Number(prdtSrlLength) ){
			return false;
		}
		
		prdtSrlCode = prdtSrlLength + '_' + prdtSrlStandard + '_' + prdtSrlTrimLength + '_' + prdtSrlTrimStandard;
		
		return prdtSrlCode;
	},	
	
	/**
	 * 바코드기준 코드에 따른 기준 생성
	 * @param value
	 * @returns {String}
	 */
	fnSetPrdtSrlCode : function(form,val) {
		var arrStr = val.split("_");
		var ableItems = ["prdtSrlLength","prdtSrlStandard","prdtSrlTrimLength","prdtSrlTrimStandard"];
		
		form.setItemValue("prdtSrlLength", arrStr[0]);
		form.setItemValue("prdtSrlStandard", arrStr[1]);
		form.setItemValue("prdtSrlTrimLength", arrStr[2]);
		form.setItemValue("prdtSrlTrimStandard", arrStr[3]);
		form.setItemValue("prdtSrlNum", "");
		
		if( val == "0_B_0_B" ){ // 기타 인경우
			for(var inx=0; inx < ableItems.length; inx++){
				form.enableItem(ableItems[inx]);
			}				
		} else {
			for(var inx=0; inx < ableItems.length; inx++){
				form.disableItem(ableItems[inx]);
			}
		}
	},
	
	/**
	 * 바코드기준 코드에 따른 코드 설명
	 * @param value
	 * @returns {String}
	 */
	fnSetPrdtSrlCodeDescripton : function(val, code) {
		var lov_msg = mvno.cmn.getLOV(code);
		var codeValue = "";
		for( var attr in lov_msg ) {
			var code_msg = lov_msg[attr];
			for( var attr2 in code_msg ) {
				if( attr2 == "text" ){
					text = code_msg[attr2];
				} else {
					codeValue = code_msg[attr2];
				}
			}
			
			if( val == codeValue ){
				if( text != "" ){
					mvno.ui.alert(text);
				}
				break;
			}
		}
	},
	
	/**
	 * 현재시간보다 작은지 체크(판매정책에서 사용)
	 */
	afterTodayTime : function(value){
		if(!value || value.lenght < 10) return null;
		
		var fromDate = new Date(value.substring(0,4), Number(value.substring(4,6))-1, value.substring(6,8),value.substring(9,11));
		var now = new Date();
		
		var chkDate = (fromDate.getTime() - now.getTime());
		
		if(chkDate < 0){
			//현재 시간보다 과거
			return -1;
		}
		
		return 0;
	},
	
	dummy: "dummy"		

};

 
