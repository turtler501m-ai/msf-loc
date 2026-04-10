var mvno = mvno || {};  

//  Validation Message
var _VALIDATION_MSG_ = {
	// DHX Stanard Rules
	Empty 						: "내용이 비어 있어야 합니다.",
	NotEmpty 					: "필수 입력 항목입니다.",
	ValidBoolean				: "true 또는 false 이어야 합니다.",
	ValidEmail 					: "이메일 형식이 아닙니다.",
	ValidInteger 				: "정수 값이 아닙니다.",
	ValidNumeric 				: "숫자 값이 아닙니다.",
	ValidAplhaNumeric 			: "영문자와 숫자값만 허용됩니다.",
	ValidDatetime 				: "날짜시간 정보가 잘못되었습니다.",
	ValidDate 					: "날짜 정보가 잘못되었습니다.",
	ValidTime 					: "시간 정보가 잘못되었습니다.",
	ValidIPv4 					: "IP(v4) 주소 형식이 아닙니다.",

	// Custome Rules
	MVNO_VXRULE_EXCLUDEKOREAN	: "한글은 허용되지 않습니다.",	
	MVNO_VXRULE_BEFORETODAY		: "오늘 이전 날짜만 허용됩니다.",
	MVNO_VXRULE_APLHAREALNUMBER : "영문자와 숫자(소수점 가능)값만 허용됩니다."
};

//==================================================================
// MVNO.VXRULE - 특정 항목의 값 하나에 대한 Custom Rule
//==================================================================
/**
 * @namespace
 */
mvno.vxRule = {

	// 한글 포함되지 않도록 !!
	excludeKorean : function(value) {
		var re = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
		return (! re.test(value));
	},

	// 오늘 이전 날짜만 허용
	beforeToday : function(value) {
		var today = new Date().format('yyyymmdd');
		console.log("Before Today", value, today);
		if (Number(value) <= Number(today)) 	return true;
		return false;
	},

	// 영문 , 숫자('.' 허용)만 허용
	AplhaRealNumber : function(value) {
		return !!value.toString().match(/^[_\.a-z0-9]+$/gi);
	},
	
	dummy: "dummy"		

};

//==================================================================
// MVNO.VALIDATOR - Validate 전용 함수들 
//==================================================================
/**
 * @namespace
 */
mvno.validator = {

	// 샘플입니다.. 최초 작성하는 분이.. 지우세요~~
	isGreater : function(a, b) {
		console.log("isGreator", a, b, a > b);
		return a > b;
	},

	// 시작날짜, 종료날짜 비교
	dateCompare : function(date1, date2) { 
		
		var getDate1 = new Date(date1.substr(0,4), date1.substr(4,2)-1, date1.substr(6,2));
		var getDate2 = new Date(date2.substr(0,4), date2.substr(4,2)-1, date2.substr(6,2));
		
		var getDiffTime = getDate2.getTime() - getDate1.getTime();
		
		return Math.floor(getDiffTime / (1000 * 60 * 60 * 24));
	},
	
	
	dummy: "dummy"

};


