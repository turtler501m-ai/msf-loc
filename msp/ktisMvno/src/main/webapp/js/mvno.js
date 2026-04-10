/**
 * @namespace
 */
var mvno = mvno || {};

var ROOT = ROOT ||  "";		// ROOT 는 header.jsp 에서 설정(?)
if(ROOT == "/") ROOT = "";

//-----------------------------------------------------------------
// 일단 IE console.log 보완
//-----------------------------------------------------------------
if (window['console'] === undefined || console.log === undefined ) {
 	console = { log: function() {} };
}

//-----------------------------------------------------------------
// 개발모드/운용모드 구분 (hostname 기준)
//-----------------------------------------------------------------
var _DEVMODE_ = false;
if(location.hostname.indexOf("localhost") >= 0 || location.hostname.indexOf("dev") >= 0) {
	_DEVMODE_ = true;
}

//운영모드인 경우 console.log(..) 찍히지 않도록.. 풀어 둔다.
if (! _DEVMODE_) {
	console.log = function(msg) {};	 // 운영모드인 경우 console.log(...) 찍히지 않도록!
}

var _TIMESTAMP_ = "20141017_01";	// 동적 JS 파일 Cache 기준적용 TimeStamp

//-----------------------------------------------------------------
// 동적 Javascript Loading 용 
//-----------------------------------------------------------------
var _JSURL_ = {
	MSG 		: ROOT + '/js/mvno_msg.js?'+ _TIMESTAMP_,
	LOV 		: ROOT + '/js/mvno_lov.js?' + _TIMESTAMP_,
	CODEFINDER 	: ROOT + '/js/mvno_codefinder.js?' + _TIMESTAMP_,
	VALIDATOR 	: ROOT + '/js/mvno_validator.js?' + _TIMESTAMP_,
	SPINNER 	: ROOT + '/lib/etc/spin.js',
	SWFOBJECT 	: ROOT + '/lib/dhtmlx/codebase/ext/swfobject.js',
	dummy      	: ''
};

//-----------------------------------------------------------------
// 기본버튼 권한관련 Mapping 정보 
// - creatAbableYn 또는 Able 과 Auth 혼용등은 서버 Naming 에 준함
//   (Naming 이 좀 이상해 보이지만..)
//-----------------------------------------------------------------
var _BUTTON_AUTH_MAPPING_ = {
	btnSearch 		: "srchAuth",			// 조회
	btnReg 			: "creatAbableYn",		// 등록
	btnMod 			: "rvisnAuth",			// 수정
	btnDel 			: "delAuth",			// 삭제
	btnDnExcel		: "exelAbableYn",		// 엑셀다운로드
	btnPrint		: "prntAbableYn",		// 출력(인쇄)
	btnAppendRow 	: "creatAbableYn",		// (다중처리용) 추가
	btnMDelete 		: "delAuth",			// (다중처리용) 선택 삭제등록
	btnMSave 		: "creatAbableYn",		// (다중처링용) (추가/수정분) 일괄저장
};


//==================================================================
// IE 함수 보완 및 String, Number, Date Prototpe 추가 
//==================================================================

// IE8 에서는 Array 의 indexOf 함수 지원안함.. 보완용
if (!Array.indexOf) {
  Array.prototype.indexOf = function (obj, start) {
    for (var i = (start || 0); i < this.length; i++) {
      if (this[i] == obj) return i;
    }
    return -1;
  };
}

//IE8 에서 String trim 함수없어 지원.
if(typeof String.prototype.trim != 'function') {	
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, ''); 
	};
};


//------------------------------------------------------------------
Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
    var weekName = ["일", "월", "화", "수", "목", "금", "토"];
    var d = this, h;
    return f.replace(/(yyyy|yy|mm|dd|D|hh12|hh|mi|ss|ap)/gi, function($1) {
        switch ($1) {
            case "yyyy"	: return d.getFullYear();
            case "yy"	: return (d.getFullYear() % 1000).zf(2);
            case "mm"	: return (d.getMonth() + 1).zf(2);
            case "dd"	: return d.getDate().zf(2);
            case "D"	: return weekName[d.getDay()];
            case "hh12"	: return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "hh"	: return d.getHours().zf(2);
            case "mi"	: return d.getMinutes().zf(2);
            case "ss"	: return d.getSeconds().zf(2);
            case "ap"	: return d.getHours() < 12 ? "오전" : "오후";
            default 	: return $1;
        }
    });
};

String.prototype.lpad = function(length, ch) {
	var str = this, ch = ch || ' ';
	while(str.length < length) { str = ch + str ; }
	return str;
};
String.prototype.rpad = function(length, ch) {
	var str = this, ch = ch || ' ';
	while(str.length < length) { str = str + ch ; }
	return str;
};
String.prototype.zf = function(len)	{return this.lpad(len,'0');};
Number.prototype.zf = function(len)	{return this.toString().lpad(len,'0');};

Number.prototype.cf = function() {
	var parts = this.toString().split(".");
	parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	return parts.join(".");
};
String.prototype.cf = function() {
	if(isNaN(this))	return this;
	return Number(this).cf();
};
String.prototype.nvl = function(val) {
	if(this == undefined || this == null || this == '')		return(val);
	return this;
};
String.prototype.nindexOf = function(str, n) {			// nth indexOf
	var index = -1;
	for(var i = 0 ; i < n ; i++) {
		index = this.indexOf(str, index + 1);
		//console.log("index", index, i);
		if (index < 0)	return(index);
	}
	return(index);
};

String.prototype.code2text = function() {
	for(var i = 0, len = arguments.length; i < len - 1; i+= 2) {
		if (arguments[i+1]) {
			if (this == arguments[i])	return (arguments[i+1]);
		}
	}
	if (arguments[i])	return(arguments[i]);
	return this;
};




//==================================================================
// MVNO.CMN
//==================================================================
/**
 * @namespace
 */
mvno.cmn = {

	//---------------------------------------------------------------
	/**
	 * 내용이 빈것인지 확인 (데이터 유형별로 포괄적인 판단)
	 * @return {boolean}  null, undefined, '', {}, [] 이면 true
	 */	
	isEmpty : function(obj) {
		if (obj == null || obj == undefined)	return true;
		// number, boolean 은 언제나 값이 있다고 판단!! (아니면, 숫자 0 도 empty 로 판단!)
		if (typeof(obj) == "number" || typeof(obj) == "boolean")	return false; 
		if (obj == "")	return true;
		if (obj instanceof Array && obj.length == 0) 	return true;  
		return $.isEmptyObject(obj);
	},


	/**
	 * IE Browser 여부 확인 
	 * @return {boolean} IE 이면 true, 아니면 false
	 */
	isIE: function()  {
		var ua = window.navigator.userAgent.toLowerCase();
		if (ua.indexOf("trident") >= 0 || ua.indexOf("msie") >= 0)  return true;	// IE (IE7 ~ IE11)
		else return false;
	},

	/**
	 * 특정 문자열을 클립보드에 복사
	 * 	<ul>
	 * 		<li>IE 는 Clipboard 에 바로 복사</li>
	 * 		<li>나머지 Browser 는 보안상 이유로 Clipboard 바로 복사 금지 - 복사할 수 있는 popup 을 띄움</li>
	 * 	</ul>
	 * @param  {string} val 복사대상 문자열
	 */
	copy2Clipboard: function(val)  {
		if (mvno.cmn.isIE()) {
			window.clipboardData.setData('Text',val);
		}
		else {
			mvno.ui.prompt("Ctrl + C 로 복사해서 사용하세요", null, { value : val, readonly: true });
		}
	},


	/**
	 * Object 가 동일한지 비교 (Object.prototype.equals 형태는 다른 library와 충돌(?)가능성)
	 * @param {object} objA 
	 * @param {object} objB
	 * @return {boolean} 동일한 내용이면 true
	 */
 	compareObject: function (objA, objB) {

 		if (! objA || ! objB)	return false;

		for(var p in objA) {

			if(typeof(objB[p])=='undefined') return false; 
			if (objA[p]) {
				switch(typeof(objA[p])) {
					case 'object':
						if (! mvno.cmn.compareObject(objA[p],objB[p])) return false; 
						break;
					case 'function':
						if (objA[p].toString() != objB[p].toString()) return false;
						break;
					default:
						if (objA[p] != objB[p]) return false;
				}
			} else {
				if (objB[p]) return false;
			}
		}

		for(var p in objB) {
			if(typeof(objA[p])=='undefined') return false;
		}

		return true;
	},

	/**
	 * Object 에서 특정 항목만 발췌해서 새로운 Object 만듬 
	 * @param {object} fullObject - 대상 Object Array
	 * @param {string} properties - 발췌할 속성을 ","로 연결된 문자열로 지정
	 * @return {object} 발췌된 Object 
	 */	
	getPartialObject: function(fullObject, properties) {

		if (! properties)	return fullObject;

		var partObject = {};
		var arrProp = properties.split(",");

		for(var i = 0, len = arrProp.length; i < len; i++) {
			var prop = arrProp[i].trim();
			partObject[prop] = fullObject[prop];
		}		

		return partObject;
	},


	/**
	 * Object Array 에서 특정 항목만 발췌해서 새로운 Object Array 만듬 
	 * @param {object} arrFullObject - 대상 Object Array
	 * @param {string} properties - 발췌할 속성을 ","로 연결된 문자열로 지정
	 * @return {array} 발췌된 Object Array
	 */	
	getPartialObjectArray: function(arrFullObject, properties) {

		var arrPartialObject = [];

		for (var i = 0, len = arrFullObject.length; i < len; i++) {
			var data = mvno.cmn.getPartialObject(arrFullObject[i], properties);
			arrPartialObject.push(data);
		}

		return arrPartialObject;
	},
	
	/**
	 * 공백 없이 리턴
	 * @param {String} str 검사할 입력 문자열
	 */
	trim : function (str) {
		var tmp = mvno.cmn.nvl(str);
		return tmp.replace(/^\s\s*|\s\s*$/g, '');
	},
	
	/**
	 * 입력된 string 이 Null 일 경우 빈 문자열 반환
	 * @param {String} input Null인지 검사할 입력 문자열
	 * @return {boolean} Null 체크 결과.
	 */
	nvl : function (input, defaultValue) {
		var retValue = input;
		if (input == null || input == '' || input == "" || typeof(input) == "undefined") {
			if (typeof(defaultValue) == "undefined") {
				retValue = "";
			} else {
				retValue = defaultValue;
			}
		}
		return retValue;
	},
	
	/**
	 * 일 계산 날짜 구해오기 
	 * @param {String} 날자 문자열 "20150326"
	 * @parma {String} 일 계산 (하루전 -1)
	 */
	getAddDay: function(date,interval) {
		if(interval == null || interval == "") {
			interval = 0;
		}
		
		var year = Number(date.substring(0,4));
		var month = Number(date.substring(4,6));
		var day = Number(date.substring(6,8));
		var changeDate = new Date();
		
		changeDate.setFullYear(year, month - 1, day + interval);
		
		var y = changeDate.getFullYear();
		var m = changeDate.getMonth() + 1;
		var d = changeDate.getDate();
		if(m < 10)    { m = "0" + m; }
		if(d < 10)    { d = "0" + d; }
		
		var resultDate = y + "" + m + "" + d;
		return resultDate;
	},
	
	/**
	 * 허용기간 확인
	 * @param {String} 시작일자
	 * @parma {String} 종료일자
	 * @param {int} 허용기간
	 * @return true / false
	 */
	checkAllowDate : function(sdate, edate, allowday) {
		var checkDate = mvno.cmn.getAddDay(sdate, allowday);
		
		var checkFlag = true;
		if(checkDate < edate) {
			checkFlag = false;
		}
		
		return checkFlag;
	},
	
	
	/**
	 * Query String 을 JSON 으로 변경
	 * @param {string} qs - Query String (default:window.location.search)
	 * @return {object} 변경된 JSON Object
	 */	
	qs2json: function(qs) {
		var obj = {};

		qs = qs || window.location.search;
		var arr = qs.replace("?","").split('&');
		
		// Array.prototype.map() 은 IE8 지원안됨 -> $.map() 사용
		$.map(arr, function(param) {	
			var p = param.split('=');
			obj[p[0]] = p[1];			
		});
		
		return obj;
	},

	/**
	 * 하위 Object 의 속성값을 dot(".") 로 연결된 Property 를 기준으로 구함
	 * @param {object} obj - 대상 Object
	 * @param {string} dotPath - dot(".") 로 연결된 Proprty 문자열 (예: aaa.bbb.ccc)
	 * @return {object} 하위 속성 값
	 */	
	getChildProp: function(obj, dotPath) {
		var arr = dotPath.split(".");
		while(arr.length && (obj = obj[arr.shift()]));
		return obj;
	},


	/**
	 * 기본(범용) AJAX 처리 (기본은 x-www-form-urlencoded 형태의 POST 방식, Async mode, jQuery.ajax 활용)
	 * @param {string} url - 호출 서버 URL
	 * @param {object|dhtmlXForm} data - 전송할 데이터 
	 * 	<ul>
	 * 		<li>dhxForm 을 넘기면 자동으로 Validation Check 후 성공하면 dhxForm 의 Data 를 전송</li>
	 * 	</ul>
	 * @param {function|dhtmlXGridObject|dhtmlXForm} callback - 서버 처리가 성공한 경우 호출한 콜백함수 
	 * 	<ul>
	 * 		<li>dhtmlXGridObject 을 넘기면 결과를 자동으로 표시 (다중 데이터)</li>
	 * 		<li>dhtmlXForm 을 넘기면 결과를 자동으로 표시 (단일 데이터)</li>
	 * 	</ul>
	 * @param {object} options - 추가 옵션 (가급적 사용하지 않도록)
	 * 	<ul>
	 * 		<li>async - true(default)/false</li>
	 * 		<li>dimmed - true/false(default)</li>
	 * 		<li>type - "post"(default)/"get"</li>
	 * 		<li>contentType - "application/x-www-form-urlencoded; charset=UTF-8"(default) / ... </li>
	 * 		<li>resultTypeCheck : 지정된 데이터 형식 확인여부 - true(default)/false</li>
	 * 		<li>spinner : 대기중 이미지 표시여부 - true(default)/false</li>
	 * 		<li>errorCallback :  서버쪽 응용 에러(NOK) 인경우 처리할 콜백함수 - null(default)</li>
	 * 		<li>timeout :  서버 응답 대기 시간 (단위:millisecond) - 30000(default)</li>
	 * 	</ul>
	 */
	ajax : function(url, data, callback, options) {		

		console.log("[AJAX][Start]", url, data, options);

		var spinner;

    	options = $.extend({}, {
    		async : true,
    		type  : "post",
    		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
    		resultTypeCheck : true,
    		spinner : true,
    		dimmed : false,
			errorCallback : null,
    		timeout : 30000			// 기본 30초
    	}, options);


    	/*--
		if (options.cross_domain) {

			// Cross-Domain 통과용...(일단 jsonp 로!!)
		    $.ajaxPrefilter('json', function(options, orig, jqXHR) {
		        return 'jsonp';
		    });
		}
		----*/

		// data 대신 dhxForm 을 넘기면, Validation Check 후 Ajax 처리하도록!
		var dhxForm4Send;
		if (data instanceof dhtmlXForm) {

		 	console.log("[AJAX][Form 2 Data]");
		 
			dhxForm4Send = data;
			if(! dhxForm4Send.validate()) {
				console.log("[AJAX][Form Validation Error]");
				return;
			}
			
			data = dhxForm4Send.getFormData(true);
			console.log("[AJAX][Form getFormData]", data);
		}

		//------------------------------------------------------------------
		$.ajax({
			url		: url, 
			async   : options.async,
			type  	: options.type,
			contentType : options.contentType,
			data	: data,
			dataType: options.dataType,  // default : Intelligent Guess
			timeout : options.timeout,
			beforeSend : function(xhr) {
				if(options.spinner)	spinner = mvno.cmn.spin(options.spinner_target);
				if (options.dimmed)  {	
					$("body").append('<div class="ajax-dimmed" style="z-index:999998"></div>');
				}
			},
			success : function (result, status,xhr) { 

				console.log("[AJAX][Success]", result);
				
				if (spinner) 	{
					spinner.stop();
				}
				if (options.dimmed)  {	
					$("body").find(".ajax-dimmed").last().remove();
				}				
				
				// 잔여시간 reset
				if(window.top.JSP){
					window.top.timeout = window.top.JSP.timeout;
				}
				
				if (options.resultTypeCheck)  {
					if (typeof result != "object") {	// 항상 object 로 돌려받자!!
						console.log("[AJAX][ERROR - Not JSON Object]", result);	// 개발지원용
						//mvno.ui.alert("수신 데이터가 JSON 형식이 아닙니다.");
						mvno.ui.alert(result);
						return;
					}
					if(mvno.cmn.isEmpty(result) || ! result.result || ! result.result.code) {
						console.log("[AJAX][ERROR - Invalid Result Format]", result); // 개발지원용
						mvno.ui.alert("수신 데이터 형식이 잘못되었습니다.");
						return;
					}
					if (result.result.code != 'OK') { 	// 에러는 data.msg 에 담자!!
						console.log("[AJAX][ERROR - Server Error]", result); // 개발지원용
						
						// Session 이 끊어진 경우에는 특별한 처리 (로그인 페이지로 이동?)
						if (result.result.code == 'SESSION_FINISH') {
							mvno.ui.alert(result.result.msg, '', function() {
								top.location.href = "/";
							});
							return;
						}
						
						//mvno.ui.alert("에러!!<br/><br/>" + result.result.msg); 
						mvno.ui.alert(result.result.msg); 	// 가이드성격의 메시지도 있어서, "에러" 표현 뺌
						
						if (options.errorCallback)	options.errorCallback.call(this, result);	// 응용에러 콜백지원
						return;
					} 
				}

				//-----------------------------------------------------------
				// Form 전송한 경우, form 의 isChanged() 함수의 변경 기준정보를 변경!!
				if (dhxForm4Send)  {
					dhxForm4Send['_LAST_DATA_'] = dhxForm4Send.getFormData(true);
				}
				
				//-----------------------------------------------------------
				// dhxForm, dhxGrid 전용처리는 내부에 포함
				// dhxGrid 의 경우, Paging 처리없이 전부다 가져오는 경우에 한함.
				if (callback instanceof dhtmlXGridObject) {
					console.log("[AJAX][Data 2 Grid]", result.result.data.rows);
					var dhxGrid = callback;
					dhxGrid.clearAll();
					dhxGrid.parse(result.result.data.rows, "js"); 
				}
				else if (callback instanceof dhtmlXForm) {
					console.log("[AJAX][Data 2 Form]", result.result.data);
					var dhxForm = callback;
					dhxForm.clear();
					dhxForm.setFormData(result.result.data);
				}
				else if (typeof callback == 'function') {
					console.log("[AJAX][Data 2 Callback]");
					callback.call(this, result);
				}
				
			},
			error : function(xhr,e) { 
				
				console.log("[AJAX][Error]", xhr, e);
			
				if (spinner) {
					spinner.stop();
				}
				if (options.dimmed)  {	
					$("body").find(".ajax-dimmed").last().remove();
				}	

				if(e == 'timeout'){
					mvno.ui.alert('서버 응답이 없습니다.(Timeout)');
				} else if(xhr.status == 404){
					mvno.ui.alert('서버 경로가 잘못되었습니다.');
				} else if(xhr.status == 500){
					mvno.ui.alert('서버 내부 처리에 문제가 발생했습니다.');
				} else {
					mvno.ui.alert('서버 요청과정에 문제가 발생했습니다.\n' + xhr.responseText);
				}						

			}
		});

	},

	ajax2 : function(url, data, callback, options) {		

		console.log("[AJAX][Start]", url, data, options);

		var spinner;

    	options = $.extend({}, {
    		async : false,
    		type  : "post",
    		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
    		resultTypeCheck : true,
    		spinner : true,
    		dimmed : false,
			errorCallback : null,
    		timeout : 30000			// 기본 30초
    	}, options);


    	/*--
		if (options.cross_domain) {

			// Cross-Domain 통과용...(일단 jsonp 로!!)
		    $.ajaxPrefilter('json', function(options, orig, jqXHR) {
		        return 'jsonp';
		    });
		}
		----*/

		// data 대신 dhxForm 을 넘기면, Validation Check 후 Ajax 처리하도록!
		var dhxForm4Send;
		if (data instanceof dhtmlXForm) {

		 	console.log("[AJAX][Form 2 Data]");
		 
			dhxForm4Send = data;
			if(! dhxForm4Send.validate()) {
				console.log("[AJAX][Form Validation Error]");
				return;
			}
			
			data = dhxForm4Send.getFormData(true);
			console.log("[AJAX][Form getFormData]", data);
		}

		//------------------------------------------------------------------
		$.ajax({
			url		: url, 
			async   : options.async,
			type  	: options.type,
			contentType : options.contentType,
			data	: data,
			dataType: options.dataType,  // default : Intelligent Guess
			timeout : options.timeout,
			beforeSend : function(xhr) {
				if(options.spinner)	spinner = mvno.cmn.spin(options.spinner_target);
				if (options.dimmed)  {	
					$("body").append('<div class="ajax-dimmed" style="z-index:999998"></div>');
				}
			},
			success : function (result, status,xhr) { 

				console.log("[AJAX][Success]", result);
				
				if (spinner) 	{
					spinner.stop();
				}
				if (options.dimmed)  {	
					$("body").find(".ajax-dimmed").last().remove();
				}				
				
				// 잔여시간 reset
				if(window.top.JSP){
					window.top.timeout = window.top.JSP.timeout;
				}
				
				if (options.resultTypeCheck)  {
					if (typeof result != "object") {	// 항상 object 로 돌려받자!!
						console.log("[AJAX][ERROR - Not JSON Object]", result);	// 개발지원용
						//mvno.ui.alert("수신 데이터가 JSON 형식이 아닙니다.");
						mvno.ui.alert(result);
						return;
					}
					if(mvno.cmn.isEmpty(result) || ! result.result || ! result.result.code) {
						console.log("[AJAX][ERROR - Invalid Result Format]", result); // 개발지원용
						mvno.ui.alert("수신 데이터 형식이 잘못되었습니다.");
						return;
					}
					if (result.result.code != 'OK') { 	// 에러는 data.msg 에 담자!!
						console.log("[AJAX][ERROR - Server Error]", result); // 개발지원용
						
						// Session 이 끊어진 경우에는 특별한 처리 (로그인 페이지로 이동?)
						if (result.result.code == 'SESSION_FINISH') {
							mvno.ui.alert(result.result.msg, '', function() {
								top.location.href = "/";
							});
							return;
						}
						
						//mvno.ui.alert("에러!!<br/><br/>" + result.result.msg); 
						mvno.ui.alert(result.result.msg); 	// 가이드성격의 메시지도 있어서, "에러" 표현 뺌
						
						if (options.errorCallback)	options.errorCallback.call(this, result);	// 응용에러 콜백지원
						return;
					} 
				}

				//-----------------------------------------------------------
				// Form 전송한 경우, form 의 isChanged() 함수의 변경 기준정보를 변경!!
				if (dhxForm4Send)  {
					dhxForm4Send['_LAST_DATA_'] = dhxForm4Send.getFormData(true);
				}
				
				//-----------------------------------------------------------
				// dhxForm, dhxGrid 전용처리는 내부에 포함
				// dhxGrid 의 경우, Paging 처리없이 전부다 가져오는 경우에 한함.
				if (callback instanceof dhtmlXGridObject) {
					console.log("[AJAX][Data 2 Grid]", result.result.data.rows);
					var dhxGrid = callback;
					dhxGrid.clearAll();
					dhxGrid.parse(result.result.data.rows, "js"); 
				}
				else if (callback instanceof dhtmlXForm) {
					console.log("[AJAX][Data 2 Form]", result.result.data);
					var dhxForm = callback;
					dhxForm.clear();
					dhxForm.setFormData(result.result.data);
				}
				else if (typeof callback == 'function') {
					console.log("[AJAX][Data 2 Callback]");
					callback.call(this, result);
				}
				
			},
			error : function(xhr,e) { 
				
				console.log("[AJAX][Error]", xhr, e);
			
				if (spinner) {
					spinner.stop();
				}
				if (options.dimmed)  {	
					$("body").find(".ajax-dimmed").last().remove();
				}	

				if(e == 'timeout'){
					mvno.ui.alert('서버 응답이 없습니다.(Timeout)');
				} else if(xhr.status == 404){
					mvno.ui.alert('서버 경로가 잘못되었습니다.');
				} else if(xhr.status == 500){
					mvno.ui.alert('서버 내부 처리에 문제가 발생했습니다.');
				} else {
					mvno.ui.alert('서버 요청과정에 문제가 발생했습니다.\n' + xhr.responseText);
				}						

			}
		});

	},
	/**
	 * Confirm 확인후 AJAX 처리 (주로 삭제처리시) 
	 * 	<ul>
	 * 		<li>mvno.cmn.ajax 함수 활용</li>
	 * 		<li>msg 를 제외한 나머지 parameter 는 mvno.cmn.ajax 와 동일</li>
	 * 	</ul>	 
	 * @param {string} msg - Confirm 용 메시지
	 * @param {string} url - 호출 서버 URL
	 * @param {object} data - 전송할 데이터
	 * @param {function} callback - 서버 처리가 성공한 경우 호출한 콜백함수 
	 * @param {object} options - 추가 옵션 
	 */
	ajax4confirm : function(msg, url, data, callback, options) {	
		mvno.ui.confirm(msg, function() {
			mvno.cmn.ajax(url, data, callback, options);
		});
	},


	/**
	 * 개별 LOV 처리 지원용 AJAX 처리 
	 * 	<ul>
	 * 		<li>mvno.cmn.ajax 함수 활용</li>
	 * 		<li>서버쪽에서는 {text:xxx, value:xxx} 형태의 Array 로 데이터 돌려줘야함</li>
	 * 		<li>LOV 처리는 mvno_lov.js 에서 공통으로 괸리하는 것이 원칙으로, ajax4lov 는 제한적으로 사용</li>
	 * 	</ul>	 
	 * @param {string} url - 호출 서버 URL
	 * @param {object} data - 전송할 데이터
	 * @param {dhtmlXForm} dhxForm - 대상 dhtmlXForm
	 * @param {string} itemName - Form Item 이름 
	 * @param {object} options - 추가 옵션 	 
	 * 	<ul>
	 * 		<li>calllback : 콜백함수 - null(default)</li>
	 * 	</ul> 	 
	 */	
	ajax4lov : function(url, data, dhxForm, itemName, options) {

		mvno.cmn.ajax(url, data, function(result) {

            var lov = result.result.data.rows;

            var itemPulls = dhxForm.getItemPulls();

            //------------------------------------------------------------
            // LOV 에 "- 선택 -", "- 전체 -" 를 자동으로 추가 여부 결정
            var required = itemPulls[itemName]._required;
            if (! required) {
                if ($(dhxForm.cont).closest(".section").hasClass("section-search")) {  // 조회조건 영역
                    lov.unshift({text: "- 전체 -", value:""});
                }
                else {
                	if(options && options.topOption) {
                		lov.unshift({text: options.topOption, value:""});
                	} else {
                		lov.unshift({text: "- 선택 -", value:""});
                	}
                }
            } else {
            	if(options && options.topOption) {
            		lov.unshift({text: options.topOption, value:""});
            	}
            }
            
			dhxForm.reloadOptions(itemName, lov);

			// 거의 사용하지는 않지만...제한적으로 필요한 경우 사용할 수 있도록 지원
			if(options && options.callback) {
				options.callback(result);
			}

		}, {
			async: false				// LOV 호출은 반드시 Sync Mode 로!!
		});		
	},	


	/**
	 * JSON 데이터를 String 으로 만들어 AJAX 처리 (다중처리 데이터용)
	 * 	<ul>
	 * 		<li>mvno.cmn.ajax 함수 활용</li>
	 * 		<li>parameter 는 mvno.cmn.ajax 와 동일하나, options 의 contentType 이 "application/json" 으로 고정</li>
	 * 	</ul>		 
	 * @param {string} url - 호출 서버 URL
	 * @param {object} data - 전송할 데이터
	 * @param {function} callback - 서버 처리가 성공한 경우 호출한 콜백함수 
	 * @param {object} options - 추가 옵션 
	 */	
	ajax4json : function(url, data, callback, options) {	
		options = options || {};
		options.contentType  = "application/json";

		mvno.cmn.ajax(url, JSON.stringify(data), callback, options);	
	},


	/**
	 * 동적으로 Form 생성 + Data 채우고 + Submit 처리
	 * @param {string} url - 호출 서버 URL
	 * @param {object} data - 전송할 데이터
	 * @param {object} options - 추가 옵션 
	 * 	<ul>
	 * 		<li>method : method - post(default)/get</li>
	 * 		<li>enctype : enctype - application/x-www-form-urlencoded(default)/...</li>
	 * 		<li>target : target - _self(default)/...</li>
	 * 	</ul>		 
	 */		
	submitDynamicForm : function(url, data, options) {	

		options = $.extend({}, {
			method  : "post",
			enctype : "application/x-www-form-urlencoded",
			target  : "_self"
		}, options);

		$form = $('<form></form>').attr({
			action: url, 
			method: options.method,
			enctype: options.enctype,
			target: options.target
		});

		for(var prop in data) {
			$form.append(
				$('<input></input>').attr({
					type: 'hidden',
					name: prop
				}).val(data[prop])
			);
		}

		// (주의!!) IE Submit() 이 동작할려면.. 반드시 DOM 에 붙은 상태여야!!
		$("body").append($form);
		$form.submit();
		$form.remove();

	},

	/**
	 * 다운로드 처리 (Form 활용)
	 * @param {string} url : 다운로드 URL
	 * @param {boolean} logging : 기록남길지 여부  - true(default)/false
	 * @param {object} options - 추가옵션 (추후 확장용)
	 * 	<ul>
	 * 		<li>postData - POST 용 Data</li>
	 * 	</ul>		 
	 */		
	download : function(url, logging, options) {	

		options = $.extend({}, {
			postData : {}
		}, options);	

		if (logging == 'undefined')	logging = true;

		if (! logging) {
			mvno.cmn.submitDynamicForm(url, options.postData, options);
			return;
		}

		mvno.cmn.downloadCallback(function(result) {
			options.postData['DWNLD_RSN'] = result;
			mvno.cmn.submitDynamicForm(url, options.postData, options)
		}, options);
	},

	downloadCallback : function(callback, options) {
		options = $.extend({}, {
			lines : 5,
			maxLength : 80,
			minLength : 10
		}, options);

		mvno.ui.prompt("다운로드 사유", callback, {
			lines : options.lines,
			maxLength : options.maxLength,
			minLength : options.minLength}
		);
	},
	/**
	 * 동적으로 CSS Loading (중복방지)
	 * @param {string} url - CSS URL
	 * @private
	 */
	loadCSS : function(url) {

		// CSS 중복 Check !!
		var $el  = $("link[href='" + url + "']");
		if ($el.length > 0)  return;

		$("head").append("<link href='" + url + "' rel='stylesheet' type='text/css'>");

	},


	/**
	 * 동적으로 Javascript Loading (중복방지)
	 * @param {string} url - Javascript URL
	 * @param {window} win - WebBrowser Window (window, window.self, window.top 등)
	 * @private
	 */
	loadJS : function(url, win) {		// win : window.top 확장 지원
		
		win = win || window.self ;			
		
		// Script 중복 Check !! (? 뒷부분은 빼고 비교)
		var $el  = $("script[src^='" + url.split("?")[0] + "']", win.document);
		if ($el.length > 0)  return;
		
		$("head", win.document).append("<script src='" + url + "' type='text/javascript'></script>");

	},


	/**
	 * Loading Image (Spinner) 동적 생성 (주로 AJAX 처리시 대기안내용)
	 * @param {DOMObject} target - Spinner 가 붙을 DOM Object (지정하지 않으면 body)
	 * @private
	 */
	spin : function(target) {

		mvno.cmn.loadJS(_JSURL_.SPINNER);	// 최초 한번 Load!!

		if (typeof Spinner == 'undefined')	return;

		var opts = {
			lines: 13, // The number of lines to draw
			length: 8, // The length of each line
			width: 5, // The line thickness
			radius: 12, // The radius of the inner circle
			corners: 1, // Corner roundness (0..1)
			rotate: 0, // The rotation offset
			direction: 1, // 1: clockwise, -1: counterclockwise
			color: '#444', // #rgb or #rrggbb or array of colors
			speed: 1, // Rounds per second
			trail: 60, // Afterglow percentage
			shadow: false, // Whether to render a shadow
			hwaccel: false, // Whether to use hardware acceleration
			className: 'spinner', // The CSS class to assign to the spinner
			//zIndex: 2e9, // The z-index (defaults to 2000000000)
			zIndex: 999999, 
			top: 'auto', // Top position relative to parent in px
			left: 'auto' // Left position relative to parent in px	
		};
		
		//var target = document.getElementById('progress_image');
		if (!target) {
			//target = $("body")[0];
			if (! $("#_SPINNER_CENTER_")[0]) {
				$("body").append('<div id ="_SPINNER_CENTER_" style="z-index:999999;position:fixed;top:50%;left:50%"></div>');
			}
			target = $("#_SPINNER_CENTER_")[0];
		}
		var spinner = new Spinner(opts).spin(target);
		return  spinner;
	},


	/**
	 * 공통메시지 얻어오기
	 * @param {string|array} msg - 메시지 Id (또는 메시지)
	 * 	<ul>
	 * 		<li>메시지 내부에 치환될 문자열 ({1},{2}...) 있는 경우 array 로 넘김 [msgId, replace1, replace2 ...]</li>
	 * 	</ul>			 
	 * @return {string} 메시지  (_MSG_ 에 등록된 메시지 Id 가 아니면, 메시지로 판단)
	 */
	getMessage: function(msg) {		

		mvno.cmn.loadJS(_JSURL_.MSG, window.top);

		if (! window.top._MSG_)	return (msg);

		if (msg instanceof Array) {
			var replaceMsg = window.top._MSG_[msg[0]] ? window.top._MSG_[msg[0]] : msg[0];
			for(var i = 1, len = msg.length; i < len; i++) {
				var re = new RegExp("\\{" + i + "\\}", "g");
				replaceMsg = replaceMsg.replace(re, msg[i]);
			}
			return replaceMsg;
		}

		return window.top._MSG_[msg] ? window.top._MSG_[msg] : msg;
	},


	/**
	 * 공통코드목록(LOV - List of Values) 얻어오기
	 * @param {string} lovId - LOV Id 
	 * @return {array} _LOV_ 에 지정된 공통코드목록 
	 */
	getLOV: function(lovId) {

		var lov = [];

		// 특수 예약 lovId 처리 (연도 등...) 
		if (lovId == '_Y10_') {			// 현재년도부터 10년간.

			var y = Number(new Date().format('yyyy'));
			for (var i = 0 ; i < 10; i++) {
				lov.push({ text : (y - i), value: (y - i) });
			}
			return (lov);
		}

		mvno.cmn.loadJS(_JSURL_.LOV, window.top);

		if (! window.top._LOV_  || ! window.top._LOV_[lovId])	return (lov);

		// 일단은 간단하게...
		var  arr = window.top._LOV_[lovId].split(",");
		for(var i = 0, len = arr.length; i < len; i++) {
			var arr2 = arr[i].split(':');
			var item = { text : arr2[1].trim(), value: arr2[0].trim() };
			lov.push(item);
		}

		return (lov);

	},	
	

};



//==================================================================
// MVNO.UI
//==================================================================
var _WINDOWS_;


/**
 * @namespace
 */
mvno.ui = {

	/**
	 * 경고창(alert) 띄움.
	 * @param {string|array} msg - 메시지 Id (또는 메시지)
	 * 	<ul>
	 * 		<li>메시지 내부에 치환될 문자열 ({1},{2}...) 있는 경우 array 로 넘김 [msgId, replace1, replace2 ...]</li>
	 * 	</ul>			 
	 * @param {string} type - 유형 (default - warning)
	 * @param {function} callback - 콜백함수
	 * @param {object} options - 추가옵션 (추후 확장용)
	 */
	alert : function(msg, type, callback, options) {

		//mvno.cmn.loadJS(_JSURL_.MSG, window.top);
		dhtmlx.alert({
			text: mvno.cmn.getMessage(msg),
			type: type,         // default : alert-waring?
			ok: "확인",
			callback: callback
		});
	},

	

	/**
	 * 확인창(confirm) 띄움.
	 * @param {string|array} msg - 메시지 Id (또는 메시지)
	 * 	<ul>
	 * 		<li>메시지 내부에 치환될 문자열 ({1},{2}...) 있는 경우 array 로 넘김 [msgId, replace1, replace2 ...]</li>
	 * 	</ul>			 
	 * @param {function} callback - 콜백함수 (동의한 경우에만 호출됨)
	 * @param {object} options - 추가옵션 (추후 확장용)
	 * 	<ul>
	 *      <li>cancelCalllback : 콜백함수 - null(default)</li>	 
	 * 	</ul>		 
	 */
	confirm : function(msg, callback, options) {

		//mvno.cmn.loadJS(_JSURL_.MSG, window.top);

		var okCallback = callback;

		dhtmlx.confirm({
			text: mvno.cmn.getMessage(msg),
			ok: "예",
			cancel: "아니오",
			callback: function(answer) {
				if(answer)  okCallback();
				else {
					if (options && options.cancelCallback)	options.cancelCallback();
				}
			}
		});
	},		


	/**
	 * 조건에 따라 확인창(confirm) 띄움.
	 * 	<ul>
	 * 		<li>condition 을 제외한 parameter 는 mvno.cmn.confirm 과 동일</li>	 
	 * 	</ul>		 
	 * @param {boolean} condition - 조건
	 * @param {string} msg - 경고 메시지
	 * @param {function} callback - 콜백함수 (동의한 경우에만 호출됨)
	 * @param {object} options - 추가옵션 (추후 확장용)
	 */
	confirmIf : function(condition, msg, callback, options) {
		if (condition)	mvno.ui.confirm(msg, callback, options);
		else 			callback();
	},		


	/**
	 * 간단한 사용자 입력값을 받음.
	 * @param {string|array} msg - 메시지 Id (또는 메시지)
	 * 	<ul>
	 * 		<li>메시지 내부에 치환될 문자열 ({1},{2}...) 있는 경우 array 로 넘김 [msgId, replace1, replace2 ...]</li>
	 * 	</ul>			 
	 * @param {function} callback - 콜백함수 (동의한 경우에만 호출됨)
	 * @param {object} options - 추가옵션 (추후 확장용)
	 * 	<ul>
	 * 		<li>value - default 문자열</li>
	 * 		<li>required - true(default)/false</li>
	 * 		<li>maxLength - 최대 입력 길이</li>
	 * 		<li>lines : 입력화면 line 수 (2이상 이면 textarea) - 1(default)</li>
	 * 		<li>readonly - 읽기 전용 (Copy & Paste 용도 등)</li>
	 * 	</ul>		 
	 */
	prompt : function(msg, callback, options) {

		options = $.extend({}, {
			required : true,
			readonly : false,
			lines : 1,
			minLength : 0
		}, options);

		var height = 175;
		
//		var strMaxLength = (options.maxLength) ? ' maxLength="' + options.maxLength + '"' : '';
		var strMaxLength = ' maxLength="80"';
			
		var html = '';
		html += '<div id="_PROMPT_" class="display:none">';
		html += '<b>' + msg + '</b>';
		if (options.lines > 1) {
			html += '<div style="padding:10px 0 10px 0; "><textarea class="prompt-input" style="width:100%" rows="' + options.lines + '"' + strMaxLength + '></textarea></div>';
			height += (options.lines - 1) * 18;
		}
		else {
			html += '<div><input class="prompt-input" style="width:100%"' + strMaxLength + '></input></div>';
		}

		if (options.readonly) {
			html += '<div style="text-align:center;"><button class="btn btn-default">확인</button></div>';
		}
		else {
			html += '<div style="text-align:center;"><button class="btn btn-default">확인</button><button class="btn btn-default">닫기</button></div>';
		}
		html += '</div>';

		// 기존에 만들어진 것이 있으면 제거
		if ($("#_PROMPT_")[0]) {
			$("#_PROMPT_").off("click", ".btn");
			$("#_PROMPT_").remove();
		}

		// 새로 추가
		$("body").append(html);

		mvno.ui.popupWindowById("_PROMPT_", "확인", 500, height, {
			xclose: false,
			destroyOnClose: true
		});

		if(options.value) {
			$("#_PROMPT_").find(".prompt-input").val(options.value);
			$("#_PROMPT_").find(".prompt-input").select();
		}

		$("#_PROMPT_").find(".prompt-input").focus();

		$("#_PROMPT_").on("click", ".btn", function() {

			if (! options.readonly)  {

				switch($(this).index()) {

					case 0 :  	// 확인 
						var val = $("#_PROMPT_").find(".prompt-input").val().replace(/\n/g, "");
						if (val) {
							if(val.length > 80){ 
								mvno.ui.alert("입력 제한 글자수를 초과하였습니다.", "", function() {
									$("#_PROMPT_").find(".prompt-input").focus();
								});
								return;
							}

							if ( val.trim().length < options.minLength ) {
								var minLengthMsg = "최소 " + options.minLength + "글자 이상 입력바랍니다.";
								mvno.ui.alert(minLengthMsg, "", function() {
									$("#_PROMPT_").find(".prompt-input").focus();
								});
								return;
							}
							
							var check = /^(?=.*['"!@#$%^*<>]).*$/;
							if(check.test(val)){
								val = val.replace(/['"!@#$%^*<>]/g, '');
							}

							if (callback)	callback(val);
						}
						else {
							if (options.required) {
								mvno.ui.alert("내용을 입력하세요.", "", function() {
									$("#_PROMPT_").find(".prompt-input").focus();
								});
								return;
							}
						}
						break;

					case 1 : 	// 취소
						break;
				}

			}

			$("#_PROMPT_").off("click", ".btn");
			mvno.ui.closeWindowById('_PROMPT_');
		});

	},	


	/**
	 * 알림용 메시지 (주로 처리가 성공한 경우, 살짝 나타났다가 사라지는 용도)
	 * @param {string|array} msg - 메시지 Id (또는 메시지)
	 * 	<ul>
	 * 		<li>메시지 내부에 치환될 문자열 ({1},{2}...) 있는 경우 array 로 넘김 [msgId, replace1, replace2 ...]</li>
	 * 	</ul>			 
	 * @param {object} options - 추가옵션
	 * 	<ul>
	 * 		<li>expire : 나타나있는 시간(단위:millisecond) - 1500(default)</li>
	 * 	</ul>		 
	 */	
	notify : function(msg, options) {
		//mvno.cmn.loadJS(_JSURL_.MSG, window.top);

		options = $.extend({}, {
			expire : 1500
		}, options);

		dhtmlx.message({
			text: mvno.cmn.getMessage(msg),
			expire: options.expire
		});
	},
	


	/**
	 * 외부 URL 을 DHX Window 로 Popup 처리 (iframe 내장) (Modal 기본)
	 * @param {string} url - 호출 URL
	 * @param {string} title - Popup Window 제목 
	 * @param {number} width - 폭
	 * @param {number} height - 높이
	 * @param {object} options - 추가옵션
	 * 	<ul>
	 * 		<li>param - iframe 에 전달할 paramter (callback 등)</li>
	 * 		<li>modal - true(default)/false</li>
	 * 		<li>top : 최상위 window 에서 띄울지 여부 - true/false(default)</li>
	 * 		<li>movable : 이동 가능 여부 - true(default)/false</li>
	 * 		<li>resizable : 크기 조절 여부 - true/false(default)</li>
	 * 		<li>xclose : 닫기용 X 버튼 지원여부 - true(true)/false</li>
	 * 		<li>maximized : 초기에 최대크기로 상태로 띄울지 여부 - true/false(default)</li>
	 * 	</ul>		 
	 */
	popupWindow : function(url, title, width, height, options) {

		options = $.extend({}, {
			modal : true,
			top : false,
			movable: true,
			resizable: false,
			xclose: true,
			maximized: false
		}, options);


		if (! _WINDOWS_)  _WINDOWS_ = new dhtmlXWindows();
		var id = 'win' + new Date().getTime();

		_WINDOWS_.createWindow(id, 100, 100, width, height);
		var win = _WINDOWS_.window(id);

		win.attachURL(url);
		$(win.cell).addClass("wins_iframe");

		//-----------------------------------------------------------------------------
		// DHX 는 modal window 를 1개만 관리 - 보완!!
		//-----------------------------------------------------------------------------
		if (options.modal) {
			var zIndex = $(win.cell).closest(".dhxwin_active, .dhxwin_inactive").css("z-index");
			$(win.cell).closest(".dhxwin_active, .dhxwin_inactive").before('<div class="dhxwins_mcover" style="z-index:' + zIndex +'"></div>');
		}

		if (options.param)  win.initParam2Iframe(options.param);	// iframe 내부로 parameter 전달

		if (options.maximized)  	win.maximize();		// 처음부터 Maxmized 상태로.
		if (! options.resizable)  	win.denyResize();
		if (! options.movable) 		win.denyMove();
		//if (options.modal)  win.setModal(true);

		win.setText(title);		
		win.button("park").hide();		
		win.button("minmax").hide();		

		if (! options.xclose)		win.button("close").hide();	

		win.centerOnScreen();

		for(var prop in options) {
			if (prop.indexOf("on") == 0 && (typeof options[prop] == 'function')) {
				if(prop != 'onClose') {		// onClose 처리를 별도 처리
					win.attachEvent(prop, options[prop]);
				}
			}
		}

		// onClose 는 별도처리
		win.attachEvent("onClose", function(win) {

			if (win['_CLOSE_FORCE_'])  {
				if (options.modal) {	// Modal 처리 보강 
					$(win.cell).closest(".dhxwin_active, .dhxwin_inactive").prev(".dhxwins_mcover").remove();
				}
				return true;
			}

			//-----------------------------------------------------
			// PAGE.onClose() 와 연동방안
			// - 바로 win.close 하지 않고... win.detachURL?
			// iframe 내부의 PAGE.onClose() 를 먼저 확인하는 방향으로..
			//-----------------------------------------------------
			var iframe = win.getFrame();
			if (iframe.contentWindow && 
				iframe.contentWindow.PAGE && iframe.contentWindow.PAGE.onClose) {

				var ret = iframe.contentWindow.PAGE.onClose();
				if (ret) {
					mvno.ui.confirm(ret + "... 계속 진행하시겠습니까?", function() {
						win.closeForce();
					});
				}
				else {
					if (iframe.contentWindow.PAGE.changed) {
						mvno.ui.confirm("CCMN0005", function() {
							win.closeForce();
						});
					}
					else {
						win.closeForce();
					}
				}
			}
			else {
				if (options.modal) {	// Modal 처리 보강 
					$(win.cell).closest(".dhxwin_active, .dhxwin_inactive").prev(".dhxwins_mcover").remove();
				}
				return true;
			}
		});
	},
	
	
	/**
	 * 외부 URL 을 DHX Window 로 Popup 처리 (iframe 내장) (Modal 기본)
	 * @param {string} url - 호출 URL
	 * @param {string} title - Popup Window 제목 
	 * @param {number} width - 폭
	 * @param {number} height - 높이
	 * @param {object} options - 추가옵션
	 * 	<ul>
	 * 		<li>param - iframe 에 전달할 paramter (callback 등)</li>
	 * 		<li>modal - true(default)/false</li>
	 * 		<li>top : 최상위 window 에서 띄울지 여부 - true/false(default)</li>
	 * 		<li>movable : 이동 가능 여부 - true(default)/false</li>
	 * 		<li>resizable : 크기 조절 여부 - true/false(default)</li>
	 * 		<li>xclose : 닫기용 X 버튼 지원여부 - true(true)/false</li>
	 * 		<li>maximized : 초기에 최대크기로 상태로 띄울지 여부 - true/false(default)</li>
	 * 	</ul>		 
	 */
	popupWindowIbsheet : function(url, title, width, height, options) {

		options = $.extend({}, {
			modal : true,
			top : false,
			movable: true,
			resizable: false,
			xclose: true,
			maximized: false
		}, options);


		if (! _WINDOWS_)  _WINDOWS_ = new dhtmlXWindows();
		//var id = 'win' + new Date().getTime();
		var id = 'winIBSHEET';

		_WINDOWS_.createWindow(id, 100, 100, width, height);
		var win = _WINDOWS_.window(id);

		win.attachURL(url);
		$(win.cell).addClass("wins_iframe");

		//-----------------------------------------------------------------------------
		// DHX 는 modal window 를 1개만 관리 - 보완!!
		//-----------------------------------------------------------------------------
		if (options.modal) {
			var zIndex = $(win.cell).closest(".dhxwin_active, .dhxwin_inactive").css("z-index");
			$(win.cell).closest(".dhxwin_active, .dhxwin_inactive").before('<div class="dhxwins_mcover" style="z-index:' + zIndex +'"></div>');
		}

		if (options.param)  win.initParam2Iframe(options.param);	// iframe 내부로 parameter 전달

		if (options.maximized)  	win.maximize();		// 처음부터 Maxmized 상태로.
		if (! options.resizable)  	win.denyResize();
		if (! options.movable) 		win.denyMove();
		//if (options.modal)  win.setModal(true);

		win.setText(title);		
		win.button("park").hide();		
		win.button("minmax").hide();		

		if (! options.xclose)		win.button("close").hide();	

		win.centerOnScreen();

		for(var prop in options) {
			if (prop.indexOf("on") == 0 && (typeof options[prop] == 'function')) {
				if(prop != 'onClose') {		// onClose 처리를 별도 처리
					win.attachEvent(prop, options[prop]);
				}
			}
		}

		// onClose 는 별도처리
		win.attachEvent("onClose", function(win) {

			if (win['_CLOSE_FORCE_'])  {
				if (options.modal) {	// Modal 처리 보강 
					$(win.cell).closest(".dhxwin_active, .dhxwin_inactive").prev(".dhxwins_mcover").remove();
				}
				return true;
			}
			
			if (options.modal) {	// Modal 처리 보강 
				$(win.cell).closest(".dhxwin_active, .dhxwin_inactive").prev(".dhxwins_mcover").remove();
			}
			
			win.closeForce();
			
			
		});
	},
	


	/**
	 * TOP(window.top) 레벨에서 popupWindow 처리 (iframe 내장) (Modal 기본)
	 * 	<ul>
	 * 		<li>parameter 는 mvno.cmn.popupWindow 와 동일</li>	 
	 * 		<li>options 의 top 을 true 로 설정해서 popupWindow 호출</li>
	 * 	</ul>		 
	 * @param {string} url - 호출 URL
	 * @param {string} title - Popup Window 제목 
	 * @param {number} width - 폭
	 * @param {number} height - 높이
	 * @param {object} options - 추가옵션
	 */
	popupWindowTop : function(url, title, width, height, options) {
		top.mvno.ui.popupWindow(url, title, width, height, options);
	},

	/**
	 * popupWindow 를 닫음 
	 * @param {object} result - popupWindow 를 호출한 쪽으로 전달할 데이터 
	 * 	<ul>
	 * 		<li>popupWindow 생성시 넘겨준 callback 함수를 이용해서 전달</li>
	 * 	</ul>	
	 * @param {boolean} isForce - onClose 이벤트 처리를 무시하고 강제 닫기 여부 
	 */	
	closeWindow: function(result, isForce) {

		var iframe = mvno.ui.getIframe();

		if (! iframe)	return;

		var dcw = $(iframe).closest(".dhx_cell_wins");
		var winId = dcw[0]._winId;

		console.log("_WINDOWS_", parent._WINDOWS_);				/// 일단은 parent .. 나중에 top? 고려

		var win = parent._WINDOWS_.w[winId].cell;

		//console.log("PAGE.PARAM", PAGE.param);

		// result 가 있는 경우에만 Callback 부르자. (받는쪽 Coding 간결하도록..)
		if (PAGE.param && PAGE.param.callback && result)  {		
			PAGE.param.callback.call(win, result);			// Close 후에 불려 지도록? 할수 있을까?
		}

		if (isForce)	win.closeForce();
		else 			win.close();

	},


	/**
	 * 내부 영역을 DHX Window 로 Popup 처리 (Modal 이 기본)
	 * @param {string} divId - 내부 영역 Id
	 * @param {string} title - Popup Window 제목 
	 * @param {number} width - 폭
	 * @param {number} height - 높이
	 * @param {object} options - 추가옵션
	 * 	<ul>
	 * 		<li>onClose - 닫기 직전 확인후 처리할 내용 (변경사항 확인 등)</li>	 
	 * 		<li>onClose2 - 실제 닫는 시점의 공통 처리 사항 </li>	 
	 * 		<li>modal - true(default)/false</li>
	 * 		<li>movable : 이동 가능 여부 - true(default)/false</li>
	 * 		<li>resizable : 크기 조절 여부 - true/false(default)</li>
	 * 		<li>xclose : 닫기용 X 버튼 지원여부 - true(true)/false</li>
	 * 		<li>maximized : 초기에 최대크기로 상태로 띄울지 여부 - true/false(default)</li>
	 * 		<li>destroyOnClose : 닫을때 내부영역(divId) 완전제거 여부 - true/false(default)</li>
	 * 	</ul>		 
	 */
	popupWindowById : function(divId, title, width, height, options) {

		options = $.extend({}, {
			modal : true,
			movable: true,
			resizable: false,
			xclose: true,
			maximized: false,
			destroyOnClose : false
		}, options);

		if (! _WINDOWS_)  _WINDOWS_ = new dhtmlXWindows();
		var id = 'win' + new Date().getTime();

		_WINDOWS_.createWindow(id, 100, 100, width, height);

		var win = _WINDOWS_.window(id);

		win.attachObject(divId);
		$(win.cell).addClass("wins_layer");

		//-----------------------------------------------------------------------------
		// DHX 는 modal window 를 1개만 관리 - 보완!!
		//-----------------------------------------------------------------------------
		if (options.modal) {
			var zIndex = $(win.cell).closest(".dhxwin_active, .dhxwin_inactive").css("z-index");
			$(win.cell).closest(".dhxwin_active, .dhxwin_inactive").before('<div class="dhxwins_mcover" style="z-index:' + zIndex +'"></div>');
		}

		if (options.maximized)  	win.maximize();		// 처음부터 Maxmized 상태로.
		if (! options.resizable)  	win.denyResize();
		if (! options.movable) 		win.denyMove();

		//if (options.modal)  win.setModal(true);

		win.setText(title);
		win.button("park").hide();		
		win.button("minmax").hide();

		if (! options.xclose)		win.button("close").hide();

		win.centerOnScreen();
		$(win.cell).find('input, textarea').first().focus().blur(); // IE11 에서 Focus 문제때문에 추가!

		for(var prop in options) {
			if (prop.indexOf("on") == 0 && (typeof options[prop] == 'function')) {
				if(prop != 'onClose') {		// onClose 처리를 별도 처리
					win.attachEvent(prop, options[prop]);
				}
			}
		}

		// onClose 는 별도처리
		win.attachEvent("onClose", function(win) {
			var ret = true;

			if (! win['_CLOSE_FORCE_']) {
				if (options.onClose) {		// onClose 는 닫기 여부에 대한 최종 판단 (닫지 않을 수도 있다!)	
					ret = options.onClose.call(this, win);
				}
			}

			if (ret) {
				if (options.onClose2) {		// onClose2 는 닫기 직전에 무조건 실행해야할 공통 처리	
					options.onClose2.call(this, win);
				}

				if (options.modal) {	// Modal 처리 보강 
					$(win.cell).closest(".dhxwin_active, .dhxwin_inactive").prev(".dhxwins_mcover").remove();
				}

				if (! options.destroyOnClose) {
					win.detachObject();					// 재활용!!
				}
			}

			return ret;
		});

	},


	/**
	 * 내부영역 popupWindow 를 닫음 
	 * @param {string} divId - 내부 영역 Id
	 * @param {boolean} isForce - onClose 이벤트 처리를 무시하고 강제 닫기 여부 
	 */
	closeWindowById: function(divId, isForce) {

		// ID 대신에 해당 Component 를 넘겨도 지원 
		var  $el;
		if (divId instanceof dhtmlXForm) {
			$el = $(divId.cont);
		}
		else {
			$el = $("#" + divId); 
		}

		//------------------------------------------------
		var dcw = $el.closest(".dhx_cell_wins"); 
		if(! dcw[0])  return;

		var winId = dcw[0]._winId;
		
		var win = _WINDOWS_.w[winId].cell;

		if(isForce)		win.closeForce();
		else 			win.close();

	},	

	

	/**
	 * CodeFinder (코드찾기용 Popup 처리) (항상 Top 레벨 Popup)
	 * @param {string} cfId - CodeFinder 구분용 Id
	 * @param {function} callback - CodeFinder Popup 에서 데이터를 선택한 경우 호출될 콜백함수
	 */	
	codefinder: function(cfId, callback) {

		mvno.cmn.loadJS(_JSURL_.CODEFINDER, window.top);

		if (! window.top._CODEFINDER_  || ! window.top._CODEFINDER_[cfId]) {
			mvno.ui.alert("[개발용] CodeFinder 가 지정되지 않았습니다.");
			return;
		}

		var cf = window.top._CODEFINDER_[cfId];
		var cfCallback = callback;

		mvno.ui.popupWindowTop(cf.url, cf.title, cf.width, cf.height, {
			param : {
				callback : function(result) {
					cfCallback(result);
				}
			}
		});		

	},


	/**
	 * 우편번호 찾기 전용 CodeFinder (항상 Top 레벨 Popup)
	 * @param {string} formName - 대상 form 이름 
	 * @param {string} itemName4ZipCd - 우편번호 항목명
	 * @param {string} itemName4Addr1 - 기본주소 항목명
	 * @param {string} itemName4Addr2 - 상세주소 항목명
	 */	
	codefinder4ZipCd: function(formName, itemName4ZipCd, itemName4Addr1 ,itemName4Addr2) {
		window.open(ROOT + "/org/orgmgmt/findZipCd.do?formName="+formName+"&itemName4ZipCd="+itemName4ZipCd+"&itemName4Addr1="+itemName4Addr1+"&itemName4Addr2="+itemName4Addr2,"pop","width=570,height=420,scrollbars=yes");
	},
	
	/**
	 * 우편번호 찾기 전용 CodeFinder (항상 Top 레벨 Popup)
	 * @param {string} formName - 대상 form 이름 
	 * @param {string} itemName4ZipCd - 우편번호 항목명
	 * @param {string} itemName4Addr1 - 기본주소 항목명
	 * @param {string} itemName4Addr2 - 상세주소 항목명
	 */	
	codeExcFileUpload: function(formName) {
		window.open(ROOT + "/cmn/fileup/fileuploadPop.do?formName=" + formName  ,"pop","width=570,height=420,scrollbars=yes");
	},

	/**
	 * 녹취 듣기 CodeFinder (항상 Top 레벨 Popup)
	 * @param {string} requestKey 
	 */	
	codefinderRec: function(fileNm,filePath,fileNm2) {
		window.open(ROOT + "/org/common/downFile3.do?fileNm="+fileNm+"&filePath="+filePath+"&fileNm2="+fileNm2,"pop","width=400,height=110,scrollbars=no");
	},

	/**
	 * 녹취 듣기 CodeFinder (항상 Top 레벨 Popup)
	 * @param {string} requestKey 
	 */	
	codefinderRec2: function(fileNm,filePath,fileNm2) {
		window.open(ROOT + "/org/common/play.do?fileNm="+fileNm+"&filePath="+filePath+"&fileNm2="+fileNm2,"pop","width=400,height=110,scrollbars=no");
	},
	
	/**
	 * 우편번호 찾기용 공통 Callback 함수 
	 * @param {string} formName - 대상 form 이름 
	 * @param {object} result - 선택한 우편번호 데이터
	 * @private 
	 */	
	callback4ZipCd: function(formName, result){
    	var dhxForm = PAGE[formName];
    	
    	for(var key in result){
    		dhxForm.setItemValue(key, result[key]);
    	}
    },
    


	/**
	 * 현재 document 를 둘러싼 Parent 의 iframe 을 구함
	 * @private
	 */		
	getIframe : function() {

		var iframe = null;

		if(! parent)	return iframe;

		var iframes = $("iframe", parent.document);

		for(var i = 0, len = iframes.length; i < len; i++) {
			try {
				var frameDocument = iframes[i].contentDocument || iframes[i].contentWindow.document;
				if (frameDocument == document)  {
					iframe = iframes[i];
					break;
				}
			}
			catch (e) { continue; }
		}
		return iframe;	
	},


	//-----------------------------------------------------------------
	/**
	 * Form 항목에 LOV 값을 채움 (동적 변경)
	 * @param {dhtmlXForm} dhxForm - 대상 dhtmlXForm
	 * @param {string} itemName - 대상 항목명
	 * @param {string} lovId - LOV Id 
	 */		 
	reloadLOV : function(dhxForm, itemName, lovId) {

		var lov = mvno.cmn.getLOV(lovId);

		var itemPulls = dhxForm.getItemPulls();  // (임시!) Form 생성정보 구해옴

        // LOV 에 "- 선택 -", "- 전체 -" 를 자동으로 추가 여부 결정
        var required = itemPulls[itemName]._required;
        if (! required) {
            if ($(dhxForm.cont).closest(".section").hasClass("section-search")) {  // 조회조건 영역
                lov.unshift({ text: "- 전체 -", value:"", selected:true });
            }
            else {
                lov.unshift({ text: "- 선택 -", value:"", selected:true });
            }
        }
		dhxForm.reloadOptions(itemName, lov);

	},

	
	/**
	 * Section 하단의 버튼용 HTML 생성 (LCR: Left-Center-Right)
	 * @param {string} sectionId - 버튼이 속할 Section Id
	 * @param {object} buttons - 생성할 버튼 정보 
	 * @private
	 */		
	makeFooterButtonHTML : function(sectionId, buttons) {
		var html = "";

		html += mvno.ui.makeButtonHTML(sectionId, buttons, "left");
		html += mvno.ui.makeButtonHTML(sectionId, buttons, "center");
		html += mvno.ui.makeButtonHTML(sectionId, buttons, "right");

		return html;
	},

	/**
	 * Section 하단의 버튼용 HTML 생성
	 * @param {string} sectionId - 버튼이 속할 Section Id
	 * @param {object} buttons - 생성할 버튼 정보 
	 * @param {string} strLCR - left/center/right 구분
	 * 	<ul>
	 * 		<li>버튼에 대한 권한처리도 포함</li>
	 * 		<li>기본버튼 (PAGE.buttonAuth.crud 속성 참조)</li>
	 * 		<li>확장버튼 (PAGE.buttonAuth.extra 속성 참조)</li>
	 * 	</ul>		 
	 * @private
	 */		
	makeButtonHTML : function(sectionId, buttons, strLCR) {
		var html = "";
		var className;
		var button;
		var enabled;
		var strDisabled;

		var defaultBtnClassNname = $("#" + sectionId).hasClass("section-pagescope") ? "btn-strong" : "btn-normal";

		if(buttons[strLCR]) {
			html += "<div class='" + strLCR + "'>";
			for(var key in buttons[strLCR]) {
				button = buttons[strLCR][key];
				className = button.className || defaultBtnClassNname;

				enabled = true;

				if (PAGE && PAGE.buttonAuth) {

					if (button.auth) {					// 버튼 확장권한 확인
						enabled = false;
						if (PAGE.buttonAuth.extra) {
							var arr = button.auth.split(",");	// 복수권한 설정지원
							for(var i = 0, len = arr.length; i < len; i++) {
								if (PAGE.buttonAuth.extra.indexOf(arr[i]) >= 0)   {
									enabled = true;
									break;
								}
							}
						}
					}
					else {
						if (PAGE.buttonAuth.crud) {		// 버튼 기본권한 확인 (button.auth 가 없더라도 무조건 확인)
							var prop = _BUTTON_AUTH_MAPPING_[key];
							if ( PAGE.buttonAuth.crud[prop] == "N")	enabled = false;
						}
					}
				}

				strDisabled = enabled ? '' : ' disabled="disabled"';

				html += "<button id='" + sectionId +"_" + key + "' type='button' class='btn " + className + "'" + strDisabled +">" + button.label + "</button>";
			}
			html += "</div>";
		}

		return html;
	},


	/**
	 * Section 버튼 보여주기 (Show)
	 * @param {string} sectionId - 버튼이 속할 Section Id
	 * @param {string} btnId - Button Id
	 */		
	showButton : function(sectionId, btnId) {
		$("#" + sectionId + "_" + btnId).show();
	},

	/**
	 * Section 버튼 감추기 (Hide)
	 * @param {string} sectionId - 버튼이 속할 Section Id
	 * @param {string} btnId - Button Id
	 */		
	hideButton : function(sectionId, btnId) {
		$("#" + sectionId + "_" + btnId).hide();
	},

	/**
	 * Section 버튼 활성화 (Enable)
	 * @param {string} sectionId - 버튼이 속할 Section Id
	 * @param {string} btnId - Button Id
	 */		
	enableButton : function(sectionId, btnId) {
		$("#" + sectionId + "_" + btnId).attr("disabled", false);
	},

	/**
	 * Section 버튼 활성화 (Enable)
	 * @param {string} sectionId - 버튼이 속할 Section Id
	 * @param {string} btnId - Button Id
	 */		
	disableButton : function(sectionId, btnId) {
		$("#" + sectionId + "_" + btnId).attr("disabled", true);
	},


	/**
	 * DHX Form(dhtmlXForm) 을 생성 (중복생성 자동방지)
	 * 	<ul>
	 * 		<li>(DHX Form 이 생성되면 자동으로 PAGE[dhxId] 변수가 생성되고 할당됨) (예: PAGE.FORM1)</li>
	 * 		<li>Global 변수 DHX, PAGE 간의 관계는 별도자료 참조</li>
	 * 	</ul>	
	 * @param {string} dhxId - DHX 변수중 Form 생성에 활용할 DHX Id
	 */	
	createForm: function(dhxId) {

		if (typeof DHX == 'undefined' || ! DHX[dhxId]) {
			alert("[개발] DHX 또는 " + dhxId + " 을 확인해 주십시오.");
			return;
		}

		// 이미 만들어진 경우, 실행하지 않음 (2중 호출시 존재여부 Check 하지 않아도 됨!!)
		if (PAGE[dhxId])	return;			


		var defaults = {
			height: 300,
			items: []
		};

		var options = DHX[dhxId] || {};
		options = $.extend({}, defaults, options);

		var sectionId = dhxId;

		var sectionHeaderId = sectionId + "_header";
		var sectionBodyId 	= sectionId + "_body";
		var sectionFooterId = sectionId + "_footer";
		var sectionFormId 	= sectionId + "_form";

		var html = 
			"<div id='" + sectionHeaderId + "' class='section-header'/>" +
		    "<div id='" + sectionBodyId   + "' class='section-body'>" +
		    "  <div id='" + sectionFormId   + "' style='width:100%'/>" +
		    "</div>" +
		    "<div id='" + sectionFooterId + "' class='section-footer'/>";

		//------------------------------------------------------------
		// 화면에 지정된 영역이 없으면 자동으로 추가 
		if(! $("#" + sectionId)[0]) {
			$("body").append("<div id='" + sectionId + "'></div>");
		}

		$("#" + sectionId).html(html).addClass("section");
		if(options.css) {
			$('#' + sectionBodyId).css(options.css);
		}		

		//----------------------------------------------------------------
		var headerHTML = "";

		if(options.title) {
			headerHTML += "<h2>" + options.title + "</h2>";
		}
		if(options.buttons && options.buttons.hright) {	// Header 오른쪽에 들어갈 버튼 
			headerHTML += mvno.ui.makeButtonHTML(sectionId, options.buttons, "hright");
		}		

		if(headerHTML) {
			$("#" + sectionHeaderId).html(headerHTML);
		}
		else {		// 내용없으면 제거
			$("#" + sectionHeaderId).remove();
		}

		//----------------------------------------------------------------
		var footerHTML = "";
		if(options.buttons) {
			footerHTML = mvno.ui.makeFooterButtonHTML(sectionId, options.buttons);
		}

		if(footerHTML) {
			$("#" + sectionFooterId).html(footerHTML);
		}
		else {		// 내용없으면 제거
			$("#" + sectionFooterId).remove();
		}

		//----------------------------------------------------------------
		// 파일업로드가 있는 경우에만 swfobject.js include
		// (검토?) swfobject.js 가 크지 않은데..무조건 include 시켜도 되지 않을까?
		// upload 항목은 따로 관리되어야 한다 (이벤트처리를 위해.)
		
		var uploadNames = [];
		(function() {
			function _fnArrangeUpload(list, names) {
				for(var i = 0, len = list.length; i < len; i++) {
					var listItem = list[i];
					
					if (! listItem) continue;
					if (listItem.type == "upload") {
						names.push(listItem.name);
						// upload 의 기본 option 설정
						if (mvno.cmn.isEmpty(listItem.autoStart))  listItem.autoStart = true;

						listItem.titleText = listItem.titleText || "선택된 파일 없음";
						
						listItem.mode = listItem.mode; //|| "flash";
						//listItem.swfPath = listItem.url// listItem.swfPath || "/lib/dhtmlx/codebase/ext/uploader.swf";
						//listItem.swfUrl = listItem.swfUrl || listItem.url;
						listItem.url = listItem.url;
					}					
					if (listItem.list) {
						_fnArrangeUpload(listItem.list, names);	//recursive Call
					}
				}
			}
			_fnArrangeUpload(options.items, uploadNames);
		})();
		
		if (uploadNames.length > 0) {
			//mvno.cmn.loadJS(_JSURL_.SWFOBJECT);		//swfobject.js (파일업로드 관련)
		}

		//----------------------------------------------------------------
		// 실제 dhtmlxForm 생성 
		var dhxForm = new dhtmlXForm(sectionFormId, options.items);


		//----------------------------------------------------------------
		// 기본적으로는 조회버튼(btnSearch)이 해당되겠지만...
		// 다른 버튼도 가능하게 확장
		if (PAGE && PAGE.buttonAuth && PAGE.buttonAuth.crud) {
			//if (PAGE.buttonAuth.crud['srchAuth'] == "N")   dhxForm.disableItem("btnSearch");
			for(var prop in _BUTTON_AUTH_MAPPING_) {
				var bamAuth = _BUTTON_AUTH_MAPPING_[prop];
				if (PAGE.buttonAuth.crud[bamAuth] == "N")   dhxForm.disableItem(prop);
			}
		}

		//var itemPulls = dhxForm.getItemPulls();    // (임시!) Form 생성정보 구해옴

		// Form 요소 보정 (LOV Population  등)
		var popoverIds = [];
		var dhxPopup = null;
		dhxForm.forEachItem(function(name) {

			var item = dhxForm._getItemByName(name);
			var itemType = dhxForm.getItemType(name);

			if (! item)	 return;		// radio 등 개별항목인 경우, item 은 null

			//------------------------------------------------------------
			// LOV
			//------------------------------------------------------------
			var lovId = dhxForm.getUserData(name, "lov");
			if(lovId) {
				mvno.ui.reloadLOV(dhxForm, name, lovId);
			}

			//------------------------------------------------------------
			// Popover
			//------------------------------------------------------------
			var popoverText = dhxForm.getUserData(name, "popover");
			if(popoverText) {
				popoverIds.push(name);
			}

			//------------------------------------------------------------
			// Validation Rule 이 ValidNumeric, ValidAplhaNumeric 인 경우 
			//    입력시점에서 자동 제한 ('-' 및 소수점('.')은 허용)
            //    또한 IE 에서 한글입력도 자동제한 (mvno.vxRule.excludeKorean 포함)
			//------------------------------------------------------------
			if (item._validate) {
			
				if(item._validate.indexOf("ValidNumeric") >= 0 || item._validate.indexOf("ValidInteger") >= 0) {
					// 숫자와 '-' 로 제한 
					$("#" + sectionId).find("input[name=" + name + ']').on("keypress", function(e) {
						var ch = e.keyCode ? e.keyCode : e.which;
                        if (ch == 45 || ch == 46) 	return true;	// 45: '-', 46: '.'			
						if (ch >= 48 && ch <= 57)	return true;
						return false;
					});

					// 숫자는 기본적으로 우측 정렬, 한글입력방지(IE전용)
					$("#" + sectionId).find("input[name=" + name + ']').css({ 
						"text-align": "right",
						"ime-mode": "disabled",
						"letter-spacing" : "0.001em"
					}); 
				}

				// ValidaAlphaNumeric 이 아니라 ValidAplhaNumeric 임에 유의!!
				if(item._validate.indexOf("ValidAplhaNumeric") >= 0 || item._validate.indexOf("mvno.vxRule.AplhaRealNumber") >= 0) {
					// 숫자와 '-' 그리고 Alphabet 으로 제한 
					$("#" + sectionId).find("input[name=" + name + ']').on("keypress", function(e) {
						var ch = e.keyCode ? e.keyCode : e.which;
						if (ch == 45 || ch == 46) 	return true;	// 45: '-', 46: '.'			
						if (ch >= 48 && ch <= 57)	return true;    // 숫자 (0:48, ... 9:57)
						if (ch >= 65 && ch <= 90)	return true;    // 대문자 (A:65, ... Z:90)
						if (ch >= 97 && ch <= 122)	return true;    // 소문자 (A:97, ... Z:122)
						return false;
					});

					// 한글입력방지(IE전용)
					$("#" + sectionId).find("input[name=" + name + ']').css({ "ime-mode": "disabled" }); 
				}

				if(item._validate.indexOf("mvno.vxRule.excludeKorean") >= 0) {
					// 한글입력방지(IE전용)
					$("#" + sectionId).find("input[name=" + name + ']').css({ "ime-mode": "disabled" }); 
				}
			}

			//------------------------------------------------------------
			// Input Text-Align 지원
			//------------------------------------------------------------
			var align = dhxForm.getUserData(name, "align");
			if(align) {
				$("#" + sectionId).find("input[name=" + name + ']').css('text-align', align); 
			}
			//------------------------------------------------------------
			// Upload 관련 보완 처리
			//------------------------------------------------------------
			if (itemType == "upload") {
				var uploader = dhxForm.getUploader(name);

				var limitCount = dhxForm.getUserData(name, "limitCount");
				limitCount = limitCount || 1;

				if (limitCount == 1) {	// 파일 1개인 경우가 대부분이어서 CSS 에서 다르게 처리 지원
					$(uploader.p).addClass("only-one-file");
				}

			}
			//------------------------------------------------------------
			// 달력 : 년월 Format (%Y-%m ) 인경우, 일자 선택부분 안나오도록
			// - DateFormat 설정에 따라 일자형, 년월형이 동적 변경 지원 
			//------------------------------------------------------------
			else if (itemType == "calendar") {

				var calendar = dhxForm.getCalendar(name);

				calendar.attachEvent("onChange", function(date, state) {
					if (calendar._dateFormat == "%Y-%m") {
						item._tempValue = null;		// 달력 editing 시 사용되는 임시값 Clear 
						var val = calendar._dateToStr(date, "%Y%m");
						dhxForm.setItemValue(name, val.replace(/-/g,""));
					}
				});
				calendar.attachEvent("onArrowClick", function(date, nextdate){
					if (calendar._dateFormat == "%Y-%m") {
						item._tempValue = null;		// 달력 editing 시 사용되는 임시값 Clear
						var val = calendar._dateToStr(nextdate, "%Y%m");
						dhxForm.setItemValue(name, val.replace(/-/g,""));
					}
				});
				calendar.attachEvent("onShow", function(){
					if (calendar._dateFormat == "%Y-%m") {
						$(this.contDays).hide();
						$(this.contDates).hide();
					}
					else {
						$(this.contDays).show();
						$(this.contDates).show();
					}
				});

			}

		});
		
		if(popoverIds.length > 0) {
			dhxPopup = new dhtmlXPopup({ form: dhxForm,  id: popoverIds });

            dhxForm.attachEvent("onFocus", function(id,value) {
            	var itemType = dhxForm.getItemType(id);
            	if(itemType == 'button')	return;			// Button 은 onButtonClick 에서 처리

                 var popoverText = dhxForm.getUserData(id, "popover");
                if(! popoverText) return;

                if (typeof(value) != "undefined") id = [id,value]; // for radiobutton
                dhxPopup.attachHTML(popoverText);
                dhxPopup.show(id);
            });

			dhxForm.attachEvent("onButtonClick", function(btnId) {
				console.log("onButtonClick!!", btnId);
                var popoverText = dhxForm.getUserData(btnId, "popover");
                if(! popoverText) return;

                dhxPopup.attachHTML(popoverText);
                dhxPopup.show(btnId);				
			});            
            dhxForm.attachEvent("onBlur", function(id,value) {
                dhxPopup.hide();
            }); 

		}
	
		//----------------------------------------------------------------------------
		if(options.liveValidation) 	dhxForm.enableLiveValidation(true);


		//----------------------------------------------------------------------
		if(options.onButtonClick) {
			dhxForm.attachEvent("onButtonClick", function(btnId) {
				options.onButtonClick.call(dhxForm, btnId);
			});

			$("#" + sectionId).on("click", "button.btn", function() {
				dhxForm.callEvent("onButtonClick", [this.id.replace(sectionId + '_', '')]);
			});
		}

		//----------------------------------------------------------------------
		// Validation Error Message 준비
		//----------------------------------------------------------------------
		dhxForm.attachEvent("onBeforeValidate", function (id) {
			mvno.cmn.loadJS(_JSURL_.VALIDATOR);		// Validation 메시지 및 추가 Rules 관련

			// dhxForm['_ERRORS_'] = [] 는 새로운 메모리 사용..
			// (낭비 막기 위해, 기존 Array 가 있으면 length  = 0 로 Clear!
			if (! dhxForm['_ERRORS_'])	dhxForm['_ERRORS_'] = [];
			else 						dhxForm['_ERRORS_'].length = 0;

			return true;
		});	

		dhxForm.attachEvent("onValidateError2", function (name, value, result, validationRule) {
			//console.log("onValidateError2", name, value, result, validationRule);
			var label = dhxForm.getItemLabel(name);
			var errMsg = "입력 값에 오류가 있습니다.";	// Default Error Message
			if (validationRule.indexOf("mvno.vxRule") >= 0) {
				validationRule = validationRule.replace(/\./g, "_").toUpperCase().trim();
			}
			if(_VALIDATION_MSG_[validationRule]) errMsg = _VALIDATION_MSG_[validationRule];
			if( label == '~' ) label = '입력날짜';
			this.pushError(name, label, errMsg, false);	// 에러메시지 Push
		});	
			
		dhxForm.attachEvent("onAfterValidate", function (status) {

			if (mvno.cmn.isEmpty(dhxForm['_ERRORS_'])) return;

			var html = "<ul class='validation-error-msg'>";
			for(var i = 0, len = dhxForm['_ERRORS_'].length; i <len ; i++) {
				if (i >= 10) {
					// 에러 갯수가 10개 넘으면... 10개까지만 표시
					html += "<li>(... 이하 에러 표시 생략 ...)</li>";
					break;
				}				
				html += "<li>[" + dhxForm['_ERRORS_'][i].label + "] " + mvno.cmn.getMessage(dhxForm['_ERRORS_'][i].msg) + "</li>";
			}
			html += "</ul>";
			
			mvno.ui.alert(html, "", function() {	
				// 에러가 발생한 첫번째 항목으로 Focus
				// 살짝 time delay 를 주도록!! (Enter 키가 item 에서도 바로 먹는 현상 방지)
				setTimeout(function() {
					if(dhxForm['_ERRORS_'][0].name instanceof Array) {
						dhxForm.setItemFocus(dhxForm['_ERRORS_'][0].name[0]);
					}
					else {
						dhxForm.setItemFocus(dhxForm['_ERRORS_'][0].name);
					}
				}, 200);
			});
				
		});		

		//----------------------------------------------------------------------
		// upload 관련 이벤트 처리 
		// - upload 관련 이벤트는 이벤트가 발생할 항목을 넘겨주지 않는다. (DHX 문제)
		// - 그래서 upload 항목 전체에 대한 검증을 함께 처리해야 한다.
		//   (대개의 경우는 upload 는 없거나 1개일 것이다.)
		//----------------------------------------------------------------------
		if (uploadNames.length > 0) {

			//------------------------------------------------------------------
			dhxForm.attachEvent("onBeforeFileAdd", function(realName, size) {
				// 파일 갯수 및 크기 제한 Check!!
				for(var i = 0, len = uploadNames.length; i < len; i++)  {

					var uploader = dhxForm.getUploader(uploadNames[i]);

					var fcnt = 0;
					var fsize = 0;
					for (var prop in uploader._files) {
						fcnt ++;
						fsize += uploader._files[prop].size;
					}

					var limitCount = dhxForm.getUserData(uploadNames[i], "limitCount");
					var limitSize = dhxForm.getUserData(uploadNames[i], "limitSize");
					limitCount = limitCount || 1;
					limitSize = limitSize || (50 * 1024);	// 단위 KB (default : 50MB)

					if (fcnt >= limitCount) {
						mvno.ui.alert("파일 갯수는 최대 " + limitCount + " 개까지 입니다.");
						return false;
					}

					if ((fsize + size) > (limitSize * 1024) ) {
						mvno.ui.alert("전체 파일 크기는 최대 " + limitSize + " KBytes 까지 입니다.");
						return false;
					}
				}
				return true;
			});


			//------------------------------------------------------------------
			dhxForm.attachEvent("onBeforeFileRemove", function(realName, serverName) {

				// serverName 이 없는 경우는 업로드가 안된 경우이므로 기본처리로 충분!
				if (! serverName)	return true;

				// 서버에 upload 된 파일은 서버에 삭제 요청
				// 문제는 어느 항목에서 발생한 것인지 확인이 안되므로 
				// uploader 의 data 를 통해서 찾은 다음 해당 delUrl 로 요청
				var delUrl;
				var uploader;
				for(var i = 0, len = uploadNames.length; i < len; i++)  {
					uploader = dhxForm.getUploader(uploadNames[i]);
					for(var prop in uploader._data) {
						if (uploader._data[prop].realName == realName && 
							uploader._data[prop].serverName == serverName) {
							delUrl = dhxForm.getUserData(uploadNames[i], "delUrl");
							break;
						}
					}
				}

				var ret = false;
				if (delUrl) {
					
					// Popup 닫는 경우 reset() 호출하고, 이때는 서버로 삭제요청하지 않음 
					if (uploader["_RESET_"])	return true;	
					
					mvno.cmn.ajax(delUrl, { fileKey: serverName }, function(result) {
						ret = true; 
					}, { 
						async: false, 		// 반드시 sync mode 로!!
						errorCallback: function() {	ret = false; }
					});
				}
				else {
					ret = true;
				}
				return ret;

			});


			//------------------------------------------------------------------
			dhxForm.attachEvent("onUploadFail", function(realName) {
				mvno.ui.alert("파일업로드중 장애가 발생했습니다. [" + realName + "]");
			});

		}

		//----------------------------------------------------------------------
		// 조회버튼 위치 통일을 위해 조회버튼 상위 class 추가 
		// 조회영역에서 Enter키 지원 (조회버튼을 Default 버튼으로)
		if($("#" + sectionId).hasClass("section-search")) { 

			$("#" + sectionId).find("[class^='btn-search'],div[class*=' btn-search']").parent().addClass('btn-search-wrap');

			dhxForm.attachEvent("onKeyUp",function(inp, ev, name, value){
				if (ev.keyCode == 13) {		// Enter 키
					if (dhxForm.getItemType(name) == 'input') {  // 일단, input 타입일때만 지원
						dhxForm.setItemFocus("btnSearch");	// input focus 가 남아있으면.. Enter 키가 계속먹음.
						dhxForm.callEvent("onButtonClick", ["btnSearch"]);		// 조회버튼 이름은 'btnSearch' 로!
					}
				}
			});
		}

		//----------------------------------------------------------------------------
		// onXXX 시리즈는 자동으로 attachEvent 되도록 보강해보자!! (제일마지막에 위치)
		for(var prop in options) {
			if (prop.indexOf("on") == 0 && (typeof options[prop] == 'function')) {
				if(prop != 'onButtonClick') {		// onButtonClick 처리를 별도 처리
					dhxForm.attachEvent(prop, options[prop]);
				}
			}
		}
		
		
		//----------------------------------------------------------------------
		PAGE[dhxId] = dhxForm; 			// PAGE 변수에 자동추가

	},

	/**
	 * DHX Grid(dhtmlXGridObject) 를 생성 (중복생성 자동방지) 
	 * 	<ul>
	 * 		<li>(DHX Grid 가 생성되면 자동으로 PAGE[dhxId] 변수가 생성되고 할당됨) (예: PAGE.GRID1)</li>
	 * 		<li>Global 변수 DHX, PAGE 간의 관계는 별도자료 참조</li>
	 * 	</ul>	
	 * @param {string} dhxId - DHX 변수중 Grid 생성에 활용할 DHX Id 
	 */
	createGrid: function(dhxId) {

		if (typeof DHX == 'undefined' || ! DHX[dhxId]) {
			alert("[개발] DHX 또는 " + dhxId + " 을 확인해 주십시오.");
			return;
		}

		// 이미 만들어진 경우, 실행하지 않음 (2중 호출시 존재여부 Check 하지 않아도 됨!!)
		if (PAGE[dhxId])	return;			

		var defaults = {
			imagePath: ROOT + '/lib/dhtmlx/codebase/imgs/',
			multiSelect: false,
			editable: true,
			paging: false,
			pagingStyle: 3,   	// 페이징영역 Default (기본영역 + 페이지목록영역 + 페이지당Row갯수영역)
			pageSize: 10,
			pageSizeList: [10,20,30,50,100],		// 페이지당 Row갯수 선택목록
			showPagingAreaOnInit: false,
			autoHeight: false,
			autoWidth: false
		};

		var options = DHX[dhxId] || {};
		options = $.extend({}, defaults, options);

		var sectionId = dhxId;

		var sectionHeaderId = sectionId + "_header";
		var sectionBodyId 	= sectionId + "_body";
		var sectionFooterId = sectionId + "_footer";
		var sectionGridId 	= sectionId + "_grid";
		var sectionPagingId = sectionId + "_paging";

		var html = 
			"<div id='" + sectionHeaderId + "' class='section-header'/>" +
		    "<div id='" + sectionBodyId   + "' class='section-body'>" +
		    "  <div id='" + sectionGridId   + "'/>" +
		    "  <div id='" + sectionPagingId   + "' class='paging'/>" +
		    "</div>" +
		    "<div id='" + sectionFooterId + "' class='section-footer'/>";

		//------------------------------------------------------------
		// 화면에 지정된 영역이 없으면 자동으로 추가 
		if(! $("#" + sectionId)[0]) {
			$("body").append("<div id='" + sectionId + "'></div>");
		}
		
		$("#" + sectionId).html(html).addClass("section");
		if(options.css) {
			$('#' + sectionGridId).css(options.css);
		}		

		//----------------------------------------------------------------
		var headerHTML = "";

		if(options.title) {
			headerHTML += "<h2>" + options.title + "</h2>";
		}
		if(options.buttons && options.buttons.hright) {	// Header 오른쪽에 들어갈 버튼 
			headerHTML += mvno.ui.makeButtonHTML(sectionId, options.buttons, "hright");
		}		

		if(headerHTML) {
			$("#" + sectionHeaderId).html(headerHTML);
		}
		else {		// 내용없으면 제거
			$("#" + sectionHeaderId).remove();
		}

		//----------------------------------------------------------------
		var footerHTML = "";
		if(options.buttons) {
			footerHTML = mvno.ui.makeFooterButtonHTML(sectionId, options.buttons);
		}

		if(footerHTML) {
			$("#" + sectionFooterId).html(footerHTML);
		}
		else {		// 내용없으면 제거
			$("#" + sectionFooterId).remove();
		}
		

		//----------------------------------------------------------------
		var dhxGrid = new dhtmlXGridObject(sectionGridId);

		dhxGrid.setEditable(options.editable);
		
		if(options.header) {
			if(! options.headerStyle) {
				var headerStyle = options.header.split(","); // 따로 변수 안만들고 overwrite 용 변수로
				for(var i = 0, len = headerStyle.length; i < len ; i++) {
					headerStyle[i] = "text-align:center";
				}
				options.headerStyle = headerStyle;
			}
			dhxGrid.setHeader(options.header, null, options.headerStyle);
		}		

		if(options.header2) 			{
			if(! options.header2Style) {
				var header2Style = options.header2.split(",");	// 따로 변수 안만들고 overwrite 용 변수로
				for(var i = 0, len = header2Style.length; i < len ; i++) {
					header2Style[i] = "text-align:center";
				}
				options.header2Style = headerStyle;
			}
			dhxGrid.attachHeader(options.header2, options.header2Style);
		}

		if(options.header3) 			{
			if(! options.header3Style) {
				var header3Style = options.header3.split(",");	// 따로 변수 안만들고 overwrite 용 변수로
				for(var i = 0, len = header3Style.length; i < len ; i++) {
					header3Style[i] = "text-align:center";
				}
				options.header3Style = headerStyle;
			}
			dhxGrid.attachHeader(options.header3, options.header3Style);
		}
		
		if(options.imagePath) 		dhxGrid.setImagePath(options.imagePath);
		if(options.columnIds) 		dhxGrid.setColumnIds(options.columnIds);
		if(options.autoHeight) 		dhxGrid.enableAutoHeight(true);
		if(options.autoWidth) 		dhxGrid.enableAutoWidth(true);

		if(options.resizing) 		dhxGrid.enableResizing(options.resizing);		
		if(options.widths) 			dhxGrid.setInitWidths(options.widths);
		if(options.widthsP) 		dhxGrid.setInitWidthsP(options.widthsP);

		if(options.colSorting) 		dhxGrid.setColSorting(options.colSorting);
		if(options.colTypes) 		dhxGrid.setColTypes(options.colTypes);		
		if(options.colAlign) 		dhxGrid.setColAlign(options.colAlign);

		if(options.skin)			dhxGrid.setSkin(options.skin);
		if(options.multiSelect)		dhxGrid.enableMultiselect(options.multiSelect);

		if(options.colValidators) 	dhxGrid.setColValidators(options.colValidators);
		

		//----------------------------------------------------------------
		if(options.hiddenColumns)	{	// String 및 Array 양쪽 방식 모두 지원
			if (typeof options.hiddenColumns == "string") {
				options.hiddenColumns = options.hiddenColumns.split(",");
			}
			for(var i = 0, len = options.hiddenColumns.length; i < len; i++) {
	  			dhxGrid.setColumnHidden(options.hiddenColumns[i],true); 
			}
		}	

		if(options.paging)			{	// 페이징 처리는 추후 보강!!

			if(options.showPagingAreaOnInit) {
				$("#" + sectionPagingId).addClass("dhx_toolbar_dhx_skyblue");
			}
			switch(options.pagingStyle)  {
				case  1 :
					dhxGrid.setPagingWTMode(true, true, false, false);
					break;
				case  2 :
					dhxGrid.setPagingWTMode(true, true, false, options.pageSizeList);
					break;
				default :
					dhxGrid.setPagingWTMode(true, true, true, options.pageSizeList);
					break;
			}

			dhxGrid.enablePaging(true, options.pageSize, 5, sectionPagingId, true); // 한페이지에 10개씩, 페이지묶음은 5개로 보여줌
			dhxGrid.setPagingSkin("toolbar", "dhx_skyblue");

			// onPageChanged 이벤트 처리
			dhxGrid.attachEvent("onPageChanged", function(ind, fInd, lInd){
				if (fInd == 0 && lInd == 0)	return;	
				dhxGrid.refresh({ pageIndex: ind, pageSize: this.rowsBufferOutSize });
			});

			dhxGrid.attachEvent("onClearAll", function(){
				$(dhxGrid._pgn_parentObj).addClass("paging");
				$(dhxGrid._pgn_parentObj).children().hide();
			});

		}
		else {		// Paging 내용 없으면 제거
			$("#" + sectionPagingId).remove();
		}


		//----------------------------------------------------------------------
		// Validation Error Message 준비
		//----------------------------------------------------------------------
		dhxGrid.attachEvent("onValidationError", function (rowId, colInd, value, validationRule) {
			console.log("onValidationError",rowId, colInd, value, validationRule);
			var label = dhxGrid.getColLabel(colInd);
			var errMsg = "입력 값에 오류가 있습니다.";	// Default Error Message
			if (validationRule.indexOf("mvno.vxRule") >= 0) {
				validationRule = validationRule.replace(/\./g, "_").toUpperCase();
			}
			if(_VALIDATION_MSG_[validationRule]) errMsg = _VALIDATION_MSG_[validationRule];
			this.pushError(rowId, colInd, label, errMsg, false);	// 에러메시지 Push
			return true;
		});		

		//----------------------------------------------------------------------
		if(options.onButtonClick) {

			// Grid 에는 기본적으로 onButtonClick 는 없다. (아닌가? 자동으로 동작 하는같은데..?)
			dhxGrid.attachEvent("onButtonClick", function(btnId) {	

				var selectedData;

				// Grid 에 선택된 데이터 (1건 or Multi) 가 없는 경우 걸러내야 하는 버튼 처리
				// 여기서 걸려내어서.. 개별 Coding 에서 더욱 간결하게~~
				if (options.checkSelectedButtons) {
					if (options.checkSelectedButtons.indexOf(btnId) >= 0)  {
						// 일단 1건 선택된 데이터를 넘기는데..
						// 추후 multi selected 된 데이터를 넘기도록...보강 
						selectedData = dhxGrid.getSelectedRowData();   

						//if(mvno.cmn.isEmpty(selectedData)) {  // selectedData 는 Object or Array
						// 새로 추가한 Row 를 선택한 경우, 빈 {} 일수 있다. (진짜 선택안한 경우는 null)
						if(selectedData == null) {  
							mvno.ui.alert("ECMN0002");	// 선택한 데이터가 없습니다.
							return;
						}						
					}
				}

				options.onButtonClick.call(dhxGrid, btnId, selectedData);
			});


			$("#" + sectionId).on("click", "button.btn", function() {
				dhxGrid.callEvent("onButtonClick", [this.id.replace(sectionId + '_', '')]);
			});

		}

		dhxGrid.init();
		
		//---------------------------------------------------------------------
		// Number 는 기본 Format 을 지정 (천단위 , 로)
		for(var i = 0, len = dhxGrid.getColumnsNum(); i < len; i++) {
			var colType = dhxGrid.getColType(i);
			if (colType == "ron" || colType == "edn") {
				dhxGrid.setNumberFormat("0,000", i, ".", ",");
			}
		}
				
		//---------------------------------------------------------------------
		// Multi 선택 처리용 
		//---------------------------------------------------------------------
		if(options.multiCheckbox) {
			dhxGrid.insertColumn(dhxGrid.getColumnsNum(),'#master_checkbox','ch',50,'na','center','center',null, null);
			dhxGrid.moveColumn(dhxGrid.getColumnsNum()-1,0);
			$(dhxGrid.hdr.rows[1]).find("td").eq(0).css("text-align", "center");
			
			// Multi 선택용 CheckBox 는 wasChanged 대상에서 제외!!
			dhxGrid.attachEvent("onCheck", function(rId, cInd, state){
				if (cInd == 0)  dhxGrid.cellById(rId, cInd).cell.wasChanged = false;
			});			
		}


		//---------------------------------------------------------------------
		// TreeGrid 인 경우 처리
		//---------------------------------------------------------------------
		if (options.treeId)	{
			dhxGrid["_TREE_ID_"] = options.treeId;
			dhxGrid["_TREE_SUB_ID_"] = options.treeSubId;
		}
			
		if (dhxGrid.isTreeGrid()) {
			dhxGrid.kidsXmlFile = "dynamic";	// 실제 URL 은 list4tree 에서 설정

			dhxGrid.attachEvent("onRowSelect", function(rId,cInd){
				// tree 컬럼에서만 expand/collapse
				if (dhxGrid.getColType(cInd) != "tree")		return;

				if (dhxGrid.getOpenState(rId)) 	dhxGrid.closeItem(rId);
				else 							dhxGrid.openItem(rId);

			});

			dhxGrid.attachEvent("onEditCell", function(stage,rId,cInd,nValue,oValue){
				// tree 컬럼은 기본적으로 Edit 할 수 없도록..
				if (dhxGrid.getColType(cInd) == "tree")  return false;
			});

			// 하위 TreeNode 가져오기..
			dhxGrid.attachEvent("onDynXLS", function(start,count){
	
				var lastReq4Tree = dhxGrid['_LAST_REQ4TREE_'];
				if (!lastReq4Tree)	return false;

				var turl 		= lastReq4Tree.url;
				//var tdata 		= lastReq4Tree.data;
				var tdata		= {};	// 하위 Tree 의 경우 검색조건등은 무의미, id (parent) 만 의미 있음!!
				var toptions 	= lastReq4Tree.options;

				var treeId = dhxGrid["_TREE_ID_"];
				var treeSubId = dhxGrid["_TREE_SUB_ID_"];
				var rowData = dhxGrid.getRowData(start);
				
				tdata["id"] 	= rowData["id"];	// Tree UI 상의 id				
				tdata[treeId]  	= rowData[treeId];	// Tree 내부의 데이터상의 id
				tdata["incIds"]  = rowData["incIds"];	// Tree 내부에서 포함되어야 할 id(다른 용도로 사용 가능. 필수 아님)
				
				toptions = toptions || {};
				toptions.async = false;

				mvno.cmn.ajax(turl, tdata, function(result){

					var prefix = new Date().getTime();	// 검색등으로 tree id 중복방지!!
					if (treeId) {
						if(treeSubId) {
							for(var i = 0, len = result.result.data.rows.length ; i <len; i++) {
								var row = result.result.data.rows[i];
								row.id = prefix + "_" + row[treeId] + "_" + row[treeSubId];
							}
						} else {
							for(var i = 0, len = result.result.data.rows.length ; i <len; i++) {
								var row = result.result.data.rows[i];
								row.id = prefix + "_" + row[treeId];
							}
						}
					}					
					
					dhxGrid.parse(result.result.data, "js");	// 기존 TreeGrid 에 Append 하게 됨.
					if (toptions.callback)	{
						toptions.callback(result);
					}
				}, toptions );	// 하위 Tree 는 sync 모드로 (onSelect 이벤트등 고려해서)
				
				return false;
			});

		}

		//----------------------------------------------------------------
		// editable false 인 Grid 에서도 Ctrl+C 로 Cell 복사 기능 제공 
		dhxGrid.attachEvent("onKeyPress", function(code,cFlag,sFlag) {
			if(cFlag && code == 67) {		// Ctrl + C
				var rId =  dhxGrid.selectedRows[0].idd;
				var cInd = dhxGrid.cell._cellIndex;
				var val = dhxGrid.cells(rId, cInd).getValue();

				//dhxGrid.cellToClipboard();	// 이건 IE 제외한 Chrome 등에서 문제
				mvno.cmn.copy2Clipboard(val);
			}
		});

		//----------------------------------------------------------------
		// onXXX 시리즈는 자동으로 attachEvent 되도록 보강해보자!! (제일마지막에 위치)
		for(var prop in options) {
			if (prop.indexOf("on") == 0 && (typeof options[prop] == 'function')) {
				if(prop != 'onButtonClick') {		// onButtonClick 처리를 별도 처리
					dhxGrid.attachEvent(prop, options[prop]);
				}
			}
		}
		
		//---------------------------------------------------------------------
		PAGE[dhxId] = dhxGrid; 			// PAGE 변수에 자동추가		

	},


	/**
	 * DHX Tabbar(dhtmlXTabBar API) 를 생성 (중복생성 자동방지) 
	 *  <ul>
	 * 		<li>(DHX Tabbar 가 생성되면 자동으로 PAGE[dhxId] 변수가 생성되고 할당됨) (예: PAGE.TABBAR1)</li>
	 * 		<li>Global 변수 DHX, PAGE 간의 관계는 별도자료 참조</li>
	 * 	</ul>	
	 * @param {string} dhxId - DHX 변수중 Grid 생성에 활용할 DHX Id 
	 */
	createTabbar: function(dhxId) {

		if (typeof DHX == 'undefined' || ! DHX[dhxId]) {
			alert("[개발] DHX 또는 " + dhxId + " 을 확인해 주십시오.");
			return;
		}

		// 이미 만들어진 경우, 실행하지 않음 (2중 호출시 존재여부 Check 하지 않아도 됨!!)
		if (PAGE[dhxId])	return;			


		var defaults = {
			close_button: false,
			sideArrow: true
		};

		var options = DHX[dhxId] || {};
		options = $.extend({}, defaults, options);

		//------------------------------------------------------------
		// 화면에 지정된 영역이 없으면 자동으로 추가 
		if(! $("#" + dhxId)[0]) {
			$("body").append("<div id='" + dhxId + "'></div>");
		}

		options.parent = dhxId;

		if(options.css) {
			$('#' + dhxId).css(options.css);
		}		

		//----------------------------------------------------------------
		var dhxTabbar = new dhtmlXTabBar(options);

		dhxTabbar.enableAutoReSize(true);
		
		if (! options.sideArrow) {		// 고정 Tabbar 인 경우에는 양쪽 옆의 화살표 영역을 감춤
			$(dhxTabbar.tabsArea).find(".dhxtabbar_tabs_ar_left, .dhxtabbar_tabs_ar_right").hide();
			dhxTabbar.setSizes();
		}		

		// Tabbar tab(cell) 영역 붙이고.. 보정!
		dhxTabbar.forEachTab(function(tab){

		    tab.showInnerScroll();

		    // 동적으로 일단 영역을 붙이고..
		    var objId = dhxId + "_" + tab.getId();
		    if ($("#" + objId)[0]) {	// Id 가 있은 경우에만 생성 
		    	tab.attachObject(objId);
		    }
		});		

		dhxTabbar.attachEvent("onSelect", function(id, lastId) {	

			if(options.onSelect) {
				var tab = dhxTabbar.tabs(id);
				var ret = options.onSelect.call(dhxTabbar, id, lastId, (! tab['_VISITED_']));
				if (ret == false)	return false;	// true, false 와 undefind 를 true 처리하기 위해

				if (! tab['_VISITED_'])  tab['_VISITED_'] = true;
				return true;
			}

			return true;
		});


		//---------------------------------------------------------------------
		// ActiveTab 이 정해져 있지 않으면  첫번째 TabCell 을 Active 
		var activeId = dhxTabbar.getActiveTab();
		if (activeId) {
			dhxTabbar.callEvent('onSelect', [activeId, null, true]);
		}
		else {
			activeId= dhxTabbar.getAllTabs()[0];
			dhxTabbar.tabs(activeId).setActive();
		}

		//------------------------------------------------------------------------
		// onXXX 시리즈는 자동으로 attachEvent 되도록 보강해보자!! (제일마지막에 위치)
		// onTabClick, onTabClose ...
		for(var prop in options) {
			if (prop.indexOf("on") == 0 && (typeof options[prop] == 'function')) {
				if(prop != 'onSelect') {		// onSelect 처리를 별도 처리
					dhxTabbar.attachEvent(prop, options[prop]);
				}
			}
		}
		
		//---------------------------------------------------------------------
		PAGE[dhxId] = dhxTabbar; 			// PAGE 변수에 자동추가

	},


	/**
	 * DHX Grid/Form 을 둘러싼 Section 영역의 내용을 동적으로 변경 
	 * @param {string} sectionId - Section Id
	 * @param {string} property - 변경할 속성 (현재는 title 만.. 필요시 추가.)
	 * 	<ul>
	 * 		<li>title : 섹션 제목</li>
	 * 	</ul>	
	 * @param {string} value - 변경할 내용
	 */
	updateSection: function(sectionId, property, value) {

		var sectionHeaderId = sectionId + "_header";

		switch(property) {

			case "title" : 
				$("#" + sectionHeaderId).html("<h2>" + value + "</h2>");
				break;

		}

	},
	

	setTitle : function (newTitle) {
		$(".page-header h1").text(newTitle);
	},
	
	//--------------------------------------------------------------------------
	dummy : function() {}

};


//==================================================================
// DHX prototype 추가 / 수정
//==================================================================
/**
 * @namespace dhtmlXGridObject
 */

/**
 * 선택한 행의 데이터를 구함 
 * @return {object|array} Object 또는 Array
 * 	<ul>
 * 		<li>multiSelect 설정되어 있으면 Array, 그렇지 않으면 Object</li>
 * 	</ul>	
 */
dhtmlXGridObject.prototype.getSelectedRowData = function() {

	var rowIds = this.getSelectedRowId();

	if (this.selMultiRows) 	return this.getRowData(rowIds, true);
	else 					return this.getRowData(rowIds);
};


/**
 * Checkbox 로 선택한 행의 데이터를 구함 
 * @param {number} chkInd - Checkbox Column Index (default:0)
 * @return {array} 1개 선택해도 Array 로 return
*/
dhtmlXGridObject.prototype.getCheckedRowData = function(chkInd) {

	chkInd = chkInd || 0;

	var rowIds = this.getCheckedRows(chkInd);

	return this.getRowData(rowIds, true);
};


/**
 * 지정된 RowId(s) 에 해당하는 행의 데이터를 구함 
 * @param {string} rowIds - Row Ids (한개의 Row Id 또는 "," 로 연결된 여러개의 Row Id)
 * @param {boolean} returnToArray - return type 지정 (true 이면 한 건이라도 Array 로 return)
 * @return {object|array} Object 또는 Array 
 */
dhtmlXGridObject.prototype.getRowData = function(rowIds, returnToArray) {

	var arrData = [];

	if (!rowIds) {
		if (returnToArray)	return arrData;
		else                return null;
	}

	var arrIds = rowIds.toString().split(",");	 // rowIds 처리보강 (number 대비)
	for(var i = 0, len = arrIds.length; i < len ; i++) {

    	var self = this;

		//-----------------------------------------------------------------
		// (주의) RowData 는 Grid 화면에 보이지 않는 것도 가져오기 위해
		//    row 의 _attrs 를 이용한다.
		//    그러나 row._attrs 는 실제 편집등으로 수정된 내용은 반영하지 못하므로
		//    실제 cells 값을 다시한번 확인해서 통합한다.
		//    그래야 실제 퍈집된 값 + Grid 에 연동되지 않는 값을 온전하게 가져온다.
		var data = self.getRowById(arrIds[i])._attrs;  

		self.forEachCell(arrIds[i], function(cellObj, ind) {
			var colId = self.getColumnId(ind);
			var value = self.cells(arrIds[i],ind).getValue();
			data[colId] = value;
		});        
		//-----------------------------------------------------------------

		arrData.push(data);
	}

	if (returnToArray)	return arrData;

	if (arrData.length > 0)		return arrData[0];
	else                        return null;
	
};




/**
 * 지정된 RowId 에 해당하는 행에 데이터를 설정(Setting)함
 * @param {string} rowId - Row Id (한개의 Row Id)
 * @param {object} data - Row Data
 */
dhtmlXGridObject.prototype.setRowData = function(rowId, data) {

	if (!rowId)	return;

	if (data instanceof dhtmlXForm) {
		var dhxForm = data;
		if(! dhxForm.validate()) return;	// 일단 넣은 걸로.. 필요없으면 빼고?

		data = dhxForm.getFormData(true);
	}

	var dhxGrid = this;
	dhxGrid.forEachCell(rowId, function(cellObj, ind) {
		var colId = dhxGrid.getColumnId(ind);
		dhxGrid.cells(rowId,ind).setValue(data[colId]);
	});

};

/**
 * 현재 선택한 행에 데이터를 설정(Setting)함 (multiSelect 지정시 첫번째 선택행에 적용)
 * @param {object} data - Row Data 
 */
dhtmlXGridObject.prototype.setSelectedRowData = function(data) {
	var rowIds = this.getSelectedRowId();
	if (! rowIds)	return;

	var arrIds = rowIds.toString().split(",");		// rowIds 처리보강 (number 대비)
	this.setRowData(arrIds[0], data);		// 다중선택시 무조건 첫번째 선택행에 적용
};

/**
 * 지정된 RowId(s) 에 해당하는 행의 데이터를 기존데이터와 신규데이터로 구분 (다중처리용 Grid 전용)
 * @param {string} rowIds - Row Ids (한개의 Row Id 또는 "," 로 연결된 여러개의 Row Id)
 * @param {string} properties - 분류시 포함시킬 데이터의 속성값을 "," 로 연결된 문자열로 지정  
 * @return {object} Object  
 * 	<ul>
 * 		<li>Object 구조 - { ALL: [...], OLD: [...], NEW: [...] }</li>
 * 		<li>ALL - 전체, OLD - 기존데이터, NEW - 신규추가데이터</li>
 * 	</ul>	 
 */
 dhtmlXGridObject.prototype.classifyRowsFromIds = function(rowIds, properties) {

	var allData = [];
	var oldData = [];
	var newData = [];

   	var self = this;

	rowIds = rowIds || this.getChangedRows();		// rowIds 를 지정하지 않으면 변경된 Rows 정보기준 

  	var arrIds = rowIds.toString().split(",");  // rowIds 처리보강 (number 대비)

    for(var i = 0, len = arrIds.length; i < len; i++) {

    	var data = self.getRowData(arrIds[i]);
		data = mvno.cmn.getPartialObject(data, properties);
		
        if (self._added_rows.indexOf(arrIds[i]) < 0) {
            oldData.push(data);
        }
        else {
            newData.push(data);
        }

    	allData.push(data);
    }

    return {
    	'ALL' : allData,
    	'OLD' : oldData,
    	'NEW' : newData
    };

};


/**
 * 지정된 RowId(s) 에 해당하는 행을 Grid 상에서 제거함
 * 	<ul>
 * 		<li>DB 데이터 삭제처리와 상관없이 화면상에서만 제거</li>
 * 	</ul>	 
 * @param {string} rowIds - Row Ids (한개의 Row Id 또는 "," 로 연결된 여러개의 Row Id)
 */
dhtmlXGridObject.prototype.deleteRowByIds = function(rowIds) {
	var arr = rowIds.toString().split(",");	/// rowIds 처리보강 (number 대비)
	for (var i = 0, len = arr.length; i < len; i++) {
	    this.deleteRow(arr[i]);
	}
}; 

/**
 * Grid 의 지정된 위치에 빈 행을 추가하여 지정된 데이터를 설정함 
 * @param {object} data - 빈 행에 설정할 초기 데이터 (optional)
 * @param {number} rowInd - 추가할 위치 (지정된 Row Index 뒤에 추가, 지정하지 않으면 맨 마지막에 추가, -1 이면 맨 앞에 추가)
 */
dhtmlXGridObject.prototype.appendRow = function(data, rowInd) {
	var newId = (new Date()).valueOf() + Math.floor(Math.random() * 99999);

	if (rowInd) this.addRow(newId, "", rowInd + 1);
	else 		this.addRow(newId, "");	

	if (! mvno.cmn.isEmpty(data))	this.setRowData(newId, data);

	this.selectRowById(newId, true, true, true);
};


/**
 * Grid 와 From 의 양방향 Binding 설정 (중복 Binding 자동방지)
 * 	<ul>
 * 		<li>DHX 기본 bind 는 Grid -> Form 단방향으로만 Binding</li>
 * 	</ul>	 
 * @param {dhtmlXForm} dhxForm - Grid 와 연동할 dhtmlXForm
 */
dhtmlXGridObject.prototype.dualBind = function(dhxForm) {

	var self = this;
	
	if (self["_DUAL_BIND_"])  return;	// 2중 실행 방지용!
	self["_DUAL_BIND_"] = dhxForm;	

	dhxForm.bind(self);		// 기본 Bind.

    dhxForm.attachEvent("onChange", function(name, value) {

        var itemValue = dhxForm.getItemValue(name, true);

        var rId = self.getSelectedRowId();
        var cInd = self.getColIndexById(name);

        self.cellById(rId, cInd).setValue(itemValue);
        self.cellById(rId, cInd).cell.wasChanged = true;   // 수동으로 추가해줘야!!

    });	

};


/**
 * 다중처리 Grid 전용 Validate 처리 
 * 	<ul>
 * 		<li>Validate 를 실행하면, 먼저 Grid 의 colValidator 설정에 따른 Column Validate 가 진행되고 </li>
 * 		<li>두개 이상의 컬럼에 대한 Validation 을 위한 onValidateMore 이벤트 발생시킴</li>
 * 	</ul>	 
 */
dhtmlXGridObject.prototype.validate = function() {

	var self = this;

	var rowIds = self.getChangedRows();
	if (! rowIds) return true;


	// self['_ERRORS_'] = [] 는 새로운 메모리 사용..
	// (낭비 막기 위해, 기존 Array 가 있으면 length  = 0 로 Clear!
	if (! self['_ERRORS_'])		self['_ERRORS_'] = [];
	else 						self['_ERRORS_'].length = 0;

	//--------------------------------------------------------------------------
	mvno.cmn.loadJS(_JSURL_.VALIDATOR);		// Validation 메시지 및 추가 Rules 관련

	var arrIds = rowIds.toString().split(","); 
	for(var i = 0, len = arrIds.length; i < len; i++) {

		//--------------------------------------------------------------------
		// _validators 없는 상태로 validateCell(...) 호출하면 에러 발생!!
		//--------------------------------------------------------------------
		if (self._validators) {
			self.forEachCell(arrIds[i], function(cellObj,ind) {
				var cell = self.cells(arrIds[i], ind).cell;
				cell.className = cell.className.replace(/[ ]*dhtmlx_validation_error/g,"");
				self.validateCell(arrIds[i], ind);
			});
		}
		var rowData = this.getRowData(arrIds[i]);
		// ROW 단위 추가  Validation 처리
		self.callEvent("onValidateMore", [arrIds[i], rowData]);
	}

	//--------------------------------------------------------------------------
	if (self['_ERRORS_'].length > 0) {

		var html = "<ul class='validation-error-msg'>";
		for(var i = 0, len = self['_ERRORS_'].length; i <len ; i++) {
			if (i >= 10) {
				// 에러 갯수가 10개 넘으면... 10개까지만 표시
				html += "<li>(... 이하 에러 표시 생략 ...)</li>";
				break;
			}
			var rowInd = self.getRowIndex(self['_ERRORS_'][i].rowId);
			html += "<li>" + (rowInd+1) + "행 :: [" + self['_ERRORS_'][i].label + "] " + mvno.cmn.getMessage(self['_ERRORS_'][i].msg) + "</li>";
		}
		html += "</ul>";
		
		mvno.ui.alert(html, "", function() {	
			// 에러가 발생한 첫번째 Row 에 위치하도록 
			// 살짝 time delay 를 주도록!! 
			setTimeout(function() {
				self.selectRowById(self['_ERRORS_'][0].rowId, true, true, true);
			}, 200);
		});

		return false;
	} 

	return true;
};

/**
 * 다중처리 Grid 전용 일괄저장용 AJAX 처리 
 * 	<ul>
 * 		<li>추가/수정분의 데이터는 { oldData: [...], newData: [...] } 형식으로 전송</li>
 * 	</ul>	 
 * @param {string} url - 일괄저장용 URL
 * @param {string} properties - 전송데이터의 속성값을 "," 로 연결된 문자열로 지정 
 * @param {function} callback - 일괄저장후 수행할 콜백함수 (optional) 
 * @param {object} options - 추가 옵션 
 */
dhtmlXGridObject.prototype.ajax4msave = function(url, properties, callback, options) {

	var self = this;

	var rowIds = self.getChangedRows();
	if (! rowIds) {
	    mvno.ui.alert("ECMN0004");
	    return;
	}

	//-----------------------------------------------------------------
	if (! self.validate())	return;
	//-----------------------------------------------------------------
	var changedData = self.classifyRowsFromIds(rowIds, properties);

	mvno.ui.confirm("변경데이터 - 추가: [" + changedData.NEW.length + "]건, 수정: [" + changedData.OLD.length + "]건 <br><br/>데이터를 일괄 저장하시겠습니까?", function() {
	    mvno.cmn.ajax4json(url, { newData: changedData.NEW, oldData: changedData.OLD }, function() {
	    	self.clearChangedState(); 		// Grid 변경상태를 Clear
	        mvno.ui.notify("NCMN0006");
	        if (callback)	callback();
	    }, options);
	});

};



/**
 * 다중처리 Grid 전용 선택삭제용 AJAX 처리 
 * 	<ul>
 * 		<li>신규추가데이터를 제외한 기존데이터만 { data: [...] } 형식으로 전송</li>
 * 		<li>서버처리가 성공하면 기존데이터 및 신규추가데이터를 Grid 상에서 일괄 제거</li>
 * 	</ul>	 
 * @param {string} url - 선택삭제용 URL
 * @param {string} properties - 전송데이터의 속성값을 "," 로 연결된 문자열로 지정 (삭제시 주로 Primary Key) (예: "id,subId")
 * 	<ul>
 * 		<li>properties 를 지정하지 않으면 Grid 행의 전체 컬럼값이 데이터로 전송</li>
 * 	</ul> 
 * @param {function} callback - 선택삭제처리후 수행할 콜백함수 (optional)
 * @param {object} options - 추가 옵션 
 */
dhtmlXGridObject.prototype.ajax4mdelete = function(url, properties, callback, options) {

	var self = this;

	// 첫번째 컬럼에 multi 선택용 checkbox 있어야!! 
	var rowIds = this.getCheckedRows(0);
	if (! rowIds) {
	    mvno.ui.alert("ECMN0003");
	    return;
	}

	var checkedData = self.classifyRowsFromIds(rowIds, properties);

	mvno.ui.confirm("선택한(체크된) [" + checkedData.ALL.length + "]건 데이터가 삭제됩니다. <br/>삭제한 데이터는 복구(취소)할 수 없습니다. <br/><br/>삭제하시겠습니까?", function() {

	    if(checkedData.OLD.length > 0) {    // 기존 데이터만 서버에 보내고...성공하면 신규분까지 화면에서 제거

	        mvno.cmn.ajax4json(url, { data: checkedData.OLD }, function() {
	            self.deleteRowByIds(rowIds);
	            mvno.ui.notify("NCMN0003");
	            if (callback)	callback();
	        }, options);

	    }
	    else {  // 전부 신규 추가한 데이터면.. 바로 화면에서 제거!!
	        self.deleteRowByIds(rowIds);
	        mvno.ui.notify("NCMN0003");
	        if (callback)	callback();
	    }

	});

};



/**
 * AJAX 로 데이터를 요청해서 Grid 에 연결시키는 처리까지 진행 (추후 refresh 의 기준점이 됨. refresh 가 필요하면 반드시 list 를 사용해야 함)
 * @param {string} url - 데이터 요청 URL
 * @param {object} data - 전송 데이터
 * @param {object} pageOptions - 추가 옵션 (주로 페이징 처리 옵션 - 전체조회시 서버에서 무시하면 됨)
 * 	<ul>
 * 		<li>pageIndex : 페이지 - 1(default)</li>
 * 		<li>pageSize : 페이지당 데이터갯수 - 10(default)</li>
 * 		<li>calllback : 콜백함수 (페이징과 무관하나 예외적으로 지원) - null(default)</li>
 * 	</ul> 
 */
dhtmlXGridObject.prototype.list = function(url, data, pageOptions) {

	var self = this;

	if (data instanceof dhtmlXForm) {
		var dhxForm = data;
		if(! dhxForm.validate()) {
            console.log("[AJAX][GRID.LIST][Form Validation Error]");
            return;			
		}
		data = dhxForm.getFormData(true);
	}

	pageOptions = pageOptions || {};
	if (! pageOptions.pageIndex)	pageOptions.pageIndex = 1;
	if (! pageOptions.pageSize)	 {
		if (self['_LAST_REQ_'] && self['_LAST_REQ_'].pageOptions && self['_LAST_REQ_'].pageOptions.pageSize) {
			pageOptions.pageSize = self['_LAST_REQ_'].pageOptions.pageSize;
		}
		else {
			pageOptions.pageSize = self.rowsBufferOutSize ? self.rowsBufferOutSize : 10;
		}
	}

	//----------------------------------------------------------------------
	var data2 = $.extend(data, pageOptions);    // pageOption 까지 data 로 통합!
	if (data2 && data2.callback) {
		data2.callback = null;			// callback 함수는 data 에 포함시키면 안된다. (그러면 ajax 데이터 serialize 과정에서 한번 수행된다!!)
	}

	mvno.cmn.ajax(url, data2, function(result) {
		
		// 마지막 조회실행 조건 저장
		self['_LAST_REQ_'] = { url : url, data : data, pageOptions: pageOptions };	
		
		//-----------------------------------------------------------------
		if(self.pagingOn) {
			
			//-------------------------------------------------------------
			// clearAll 하면 Paging Skin 관련 정보도 날라간다. (따로 Save & Restore 해야!)
			// 따라서 clearAll 전에 pagingOn = false 처리해서 paging 설정 제거하고,
			// clearAll 후에 다시 pagingOn = true 로 복구처리
			// (더 좋은 방법은 찾게되면 추후 개선)
			//
			// 그렇다고 clearAll 처리하지 않으면 selectedRow, checkedRow 등과 같이
			// 이전 페이지 정보가 내부적으로 공유(엉키는?) 현상 발생
			//
			// 단, 1페이지 조회시는 바로 clearAll() 처리해야!! 
			// (새로운 조회로 판단, paging 정보를 남겨두면.. 1페이지 데이터 나오지 않음)
			//-------------------------------------------------------------
			if(pageOptions.pageIndex == 1) {
				self.clearAll();
			}
			else {
				self.pagingOn = false;
				self.clearAll();
				self.pagingOn = true;
			}			
			
			//-----------------------------------------------------------------
			// Paging 관련 정보 보정 (total_count 및 pos 설정) (DHX 예약속성)
			//-----------------------------------------------------------------
			result.result.data.rows.total_count = result.result.data.total;
			result.result.data.rows.pos = (result.result.data.pageIndex - 1) * result.result.data.pageSize;
			
			self.parse(result.result.data.rows, "js");
			
			$(self._pgn_parentObj).children().show();	// 페이징영역 보여주기 (clearAll() 시점에서 hide 된 상태)

		}
		else {
			self.clearAll();
			self.parse(result.result.data.rows, "js");
		}
		
		
		//-----------------------------------------------------------------
		// Refresh 등으로 이전에 선택한 RowId 로 이동 지원
		var selectedRowId = self['_LAST_SELECTED_ROWID_'];
		if(selectedRowId) {
			var rowId = selectedRowId.toString().split(",")[0];	// 다중선택인 경우 대비 (첫번째것 기준)
			if (self.doesRowExist(rowId)) {
				self.selectRowById(rowId, true, true, true);
			}
		}
		else {
			if (self._bind_hash) {	// Form 등과 Binding 된 Grid 의 경우, 자동으로 첫번째 Row Select!!
				self.selectRow(0, true, true, true);
			}
		}

		//-----------------------------------------------------------------
		if(pageOptions.callback)  pageOptions.callback.call(this, result);
		
	}, pageOptions);

};


/**
 * Grid 데이터 Refresh (list 실행시 내부적으로 저장된 조건으로 다시 조회처리)
 * @param {object} options - 기존 조건에서 변경할 내용 (주로 pageIndex 나 pageSize 변경) (예: { pageIndex:1, pageSize: 30 })
 */
dhtmlXGridObject.prototype.refresh = function(options) {

	//------------------------------------------------------
	var req = this['_LAST_REQ_'];
	if(! req)		{
		console.log("실행한 조회가 없습니다.?");
		return;				// 이전에 조회실행한적 없으면 무시
	}

	this['_LAST_SELECTED_ROWID_'] = null;	// refresh 후 유지할 RowId

	if (options) {
		req.data = $.extend(req.data, options);

		if(options.pageIndex)	req.pageOptions.pageIndex = options.pageIndex;
		if(options.pageSize)	req.pageOptions.pageSize = options.pageSize;
		
		//-----------------------------------------------------------------
		// Refresh 채운후, 현재 선택한 RowId 로 이동 지원 ( { selectRow : true })
		if (options.selectRow) this['_LAST_SELECTED_ROWID_'] = this.getSelectedRowId();		
	}

	this.list(req.url, req.data, req.pageOptions); 

};


/**
 * AJAX 로 데이터를 요청해서 Grid 에 연결시키는 처리까지 진행 (Tree 형) 
 * @param {string} url - 데이터 요청 URL
 * @param {object} data - 전송 데이터
 * @param {object} options - 추가 옵션 
 * 	<ul>
 * 		<li>calllback : 콜백함수 - null(default)</li>
 * 	</ul>  
 */
dhtmlXGridObject.prototype.list4tree = function(url, data, options) {

	var self = this;

	if (data instanceof dhtmlXForm) {
		var dhxForm = data;
		if(! dhxForm.validate()) {
            console.log("[AJAX][GRID.LIST4TREE][Form Validation Error]");
            return;			
		}
		data = dhxForm.getFormData(true);
	}

	//----------------------------------------------------------------------
	var data2 = $.extend(data, options);    
	if (data2 && data2.callback) {
		data2.callback = null;			// callback 함수는 data 에 포함시키면 안된다. (그러면 ajax 데이터 serialize 과정에서 한번 수행된다!!)
	}

	mvno.cmn.ajax(url, data2, function(result) {

		// 마지막 조회실행 조건 저장
		self['_LAST_REQ4TREE_'] = { url : url, data : data, options: options };	
		
		self.clearAll();

		var treeId = self["_TREE_ID_"];
		var treeSubId = self["_TREE_SUB_ID_"];

		var prefix = new Date().getTime();	// 검색등으로 tree id 중복방지!!
		if (treeId) {
			if(treeSubId) {
				for(var i = 0, len = result.result.data.rows.length ; i <len; i++) {
					var row = result.result.data.rows[i];
					row.id = prefix + "_" + row[treeId] + "_" + row[treeSubId];
				}
			} else {
				for(var i = 0, len = result.result.data.rows.length ; i <len; i++) {
					var row = result.result.data.rows[i];
					row.id = prefix + "_" + row[treeId];
				}
			}
		}
		self.parse(result.result.data, "js");

		//-----------------------------------------------------------------
		if(options.callback)  options.callback.call(this, result);

	}, options);

};


/**
 * Grid 데이터 Refresh (Tree 형) - 특정 Node 이하 데이터를 삭제하고, 새로 서버에서 데이터를 가져옴
 * @param {string} rowId - rowId 
 */
dhtmlXGridObject.prototype.refresh4tree = function(rowId) {

	var self = this;

	//------------------------------------------------------
	var req = self['_LAST_REQ4TREE_'];
	if(! req)		{
		console.log("실행한 조회가 없습니다.?");
		return;				// 이전에 조회실행한적 없으면 무시
	}

	var data = req.data;

	rowId = rowId || 0;		// rowId = 0 는 Tree Root 의미
	if (rowId == 0) {		
		if (data)   data.id = null;
		self.list4tree(req.url, data, req.options);
		return;
	}

	//------------------------------------------------------
	var treeId = self["_TREE_ID_"];
	var treeSubId = self["_TREE_SUB_ID_"];
	var rowData = self.getRowData(rowId);

	data = {};  // 하위 Tree 의 경우 검색조건등은 무의미, id (parent) 만 의미 있음!!
	data["id"] 	= rowData["id"];	 	// Tree UI 상의 id			
	data[treeId] = rowData[treeId];	 	// Tree 내부의 데이터상의 id		
	
	var options = req.options;
	options = options || {};
	options.async = false;

	self.deleteChildItems(rowId);		// 기존것은 지우고...
	mvno.cmn.ajax(req.url, data, function(result){
		
		var prefix = new Date().getTime();	// 검색등으로 tree id 중복방지!!
		if (treeId) {
			if(treeSubId) {
				for(var i = 0, len = result.result.data.rows.length ; i <len; i++) {
					var row = result.result.data.rows[i];
					row.id = prefix + "_" + row[treeId] + "_" + row[treeSubId];
				}
			} else {
				for(var i = 0, len = result.result.data.rows.length ; i <len; i++) {
					var row = result.result.data.rows[i];
					row.id = prefix + "_" + row[treeId];
				}
			}
		}
		
		self.parse(result.result.data, "js");	// 기존 TreeGrid 에 Append 하게 됨.	
		if (options.callback)	{
			options.callback(result);
		}
	}, options);	// 하위 Tree 는 sync 모드로 (onSelect 이벤트등 고려해서)

};


/**
 * Grid 의 데이터 변경여부 확인
 * @return {boolean} true 이면 변경된 데이터 있음
 */
dhtmlXGridObject.prototype.isChanged = function() {

	var rowIds = this.getChangedRows();
	
	if (rowIds) 	return true;
	return false;

};

/**
 * Grid Validation 에러메시지 추가 
 * @param {string} rowId - Row Id
 * @param {string|array} colId - Column Id (컬럼명)
 * 	<ul>
 * 		<li>두개 이상의 항목에 걸친 Validation 인 경우 Array 지원</li>
 * 	</ul>  
 * @param {string} label - 에러 메시지에 보여줄 항목의 제목 (복합 제목등)
 * @param {string|array} errMsg - 메시지 Id (또는 메시지)
 * 	<ul>
 * 		<li>메시지 내부에 치환될 문자열 ({1},{2}...) 있는 경우 array 로 넘김 [msgId, replace1, replace2 ...]</li>
 * 	</ul>	
 * @param {boolean} withCSS - 에러 CSS 처리 포함 (default:true) 
 * 	<ul>
 * 		<li>Validation Error CSS 는 자동으로 적용되므로 기본적으로 따로 처리하지 않아도 되나, 
 *          onValidateMore 이벤트에서 처리에서는 CSS 처리가 되지 않으므로 
 *          기본적으로 true 로 강제 CSS 적용을 요청하고, 
 *          내부적으로 자동으로 CSS 적용되는 경우에는 false 를 넘겨 중복 적용을 방지하는 방향으로 처리</li>
 * 	</ul>  
 */
dhtmlXGridObject.prototype.pushError = function(rowId, colId, label, errMsg, withCSS) {    

	var colInd;

	if (! this['_ERRORS_'])  this['_ERRORS_'] = [];
	this['_ERRORS_'].push({ rowId: rowId, colId: colId, label: label, msg: errMsg });	

	if (withCSS != false) {		// (주의) undefined, true

		var cell;

		if (colId instanceof Array) {
			for(var i = 0, len = colId.length; i < len; i++) {
				colInd = this.getColIndexById(colId[i]);
				cell = this.cells(rowId,colInd).cell;
				cell.className += " dhtmlx_validation_error";
			}
		}
		else {
			colInd = this.getColIndexById(colId);
			cell = this.cells(rowId,colInd).cell;
			cell.className += " dhtmlx_validation_error";
		}

	}

};


/**
 * Grid 의 Validation Error (정확히는 Error 메시지) 가 있는지 확인 
 * @return {boolean} true 이면 Validation Error 있음
 */
dhtmlXGridObject.prototype.hasError = function() {    
	return (! mvno.cmn.isEmpty(this['_ERRORS_']));
};


/**
 * Grid Combo 데이터를 LovId 를 기준으로 채움
 * @param {string} colId - Column Id 
 * @param {string} lovId - LOV Id 
 */
dhtmlXGridObject.prototype.fillComboByLovId = function(colId, lovId) {    

	var combo = this.getCombo(this.getColIndexById(colId));
	combo.clear();
	var lov = mvno.cmn.getLOV(lovId);
	for(var i = 0, len = lov.length; i < len; i++) {
	    combo.put(lov[i].value, lov[i].text);
	}

};

/**
 * Grid Combo 데이터를 LovId 를 기준으로 채움
 * @param {string} colId - Column Id 
 * @param {string} url - 호출 서버 URL
 * @param {object} data - 전송할 데이터  
 */
dhtmlXGridObject.prototype.fillComboByAjax = function(colId, url, data) {    
	
	var combo = this.getCombo(this.getColIndexById(colId));
	combo.clear();
	mvno.cmn.ajax(url, data, function(result) {
		var lov = result.result.data.rows;
		for(var i = 0, len = lov.length; i < len; i++) {
		    combo.put(lov[i].value, lov[i].text);
		}		
	}, { async: false });

};

/**
 * Grid Paging 영역 한글화 
 */
dhtmlXGridObject.prototype.i18n.paging = {
    results: "결과",
    records: "[범위] ",
    to: " ~ ",
    page: "페이지 ",
    perpage: "개씩 보기",
    first: "처음",
    previous: "이전",
    found: "데이터가 있습니다.",
    next: "다음",
    last: "마지막",
    of: " of ",
    notfound: "데이터가 없습니다."
};


/**
 * @namespace dhtmlXForm
 */

//-------------------------------------------------------------------------
dhtmlXForm.prototype.__setFormData__ = dhtmlXForm.prototype.setFormData; // 원본저장 

/**
 * Form 에 데이터 설정(Setting) 함
 * 	<ul>
 * 		<li>dhtmlxForm 의 기본 setFormData 를 확장</li>
 * 		<li>신규데이터 여부나 추후 데이터 변경여부 등을 지원용</li>
 * 	</ul>	
 * @param {object} data - 설정 데이터
 * @param {boolean} isNew - 신규 데이터 여부
 */
dhtmlXForm.prototype.setFormData = function(data, isNew) {

	this.clear();  // 일단 Clear 처리 (등록/수정 데이터 반복 설정시에도 문제없도록...)

	if (mvno.cmn.isEmpty(data))	{
		if (isNew == undefined) isNew = true;
	}
	else {
		this.__setFormData__(data);
	}						

	this['_IS_NEW_'] = isNew;
	this['_LAST_DATA_'] = this.getFormData(true);
};

/**
 * Form 의 데이터가 신규 데이터 여부 확인 
 * @return {boolean} true 이면 신규 데이터
 */
dhtmlXForm.prototype.isNew = function() {
	return this['_IS_NEW_'];
};


/**
 * Form 의 데이터 변경여부 확인
 * @return {boolean} true 이면 변경된 데이터 있음
 */
dhtmlXForm.prototype.isChanged = function() {
	var curData = this.getFormData(true);
	var orgData = this['_LAST_DATA_'];

	if (! orgData || ! curData)		return false;
	return (! mvno.cmn.compareObject(curData, orgData));
}; 

/**
 * Form 의 데이터 변경여부를 Clear 하고 새로운 변경기준점을 저장
 */
dhtmlXForm.prototype.clearChanged = function() {
	this['_LAST_DATA_'] = this.getFormData(true);
};

/**
 * Form Item 생성정보 확인용 (Form 생성시 사용한 설정정보 확인을 위한 Recursive 함수) (제한적으로 사용!)
 * @return {object} Form Item 설정정보 
 * @private
 */
dhtmlXForm.prototype.getItemPulls = function() {        // (임시?) Form Item 생성정보 확인용 (Recursive!!)
    var itemPulls = {};

    for (var key in this.itemPull) {
        itemPulls[this.itemPull[key]._idd] = this.itemPull[key];

        if (this.itemPull[key]._list) {
            for(var i = 0, len = this.itemPull[key]._list.length; i < len; i++) {
                var childItemPulls = (this.itemPull[key]._list[i]).getItemPulls();
                $.extend(itemPulls, childItemPulls);
            }
        }
    }
    return itemPulls;
};



/**
 * Form Validation 에러메시지 추가 
 * @param {string|array} itemName - 에러 발생 항목명 
 * 	<ul>
 * 		<li>두개 이상의 항목에 걸친 Validation 인 경우 Array 지원</li>
 * 	</ul> 
 * @param {string} label - 에러 메시지에 보여줄 항목의 제목 (복합 제목등)
 * @param {string|array} errMsg - 메시지 Id (또는 메시지)
 * 	<ul>
 * 		<li>메시지 내부에 치환될 문자열 ({1},{2}...) 있는 경우 array 로 넘김 [msgId, replace1, replace2 ...]</li>
 * 	</ul>	 
 * @param {boolean} withCSS - 에러 CSS 처리 포함 (default:true) 
 * 	<ul>
 * 		<li>Validation Error CSS 는 자동으로 적용되므로 기본적으로 따로 처리하지 않아도 되나, 
 *          onValidateMore 이벤트에서 처리에서는 CSS 처리가 되지 않으므로 
 *          기본적으로 true 로 강제 CSS 적용을 요청하고, 
 *          내부적으로 자동으로 CSS 적용되는 경우에는 false 를 넘겨 중복 적용을 방지하는 방향으로 처리</li>
 * 	</ul> 
 */
dhtmlXForm.prototype.pushError = function(itemName, label, errMsg, withCSS) {    

	if (! this['_ERRORS_'])  this['_ERRORS_'] = [];
	this['_ERRORS_'].push({name: itemName, label: label, msg: errMsg });

	if (withCSS != false) {		// (주의) undefined, true ..
		if (itemName instanceof Array) {
			for(var i = 0, len = itemName.length; i < len; i++) {
				this.setValidateCss(itemName[i], false);
			}
		}
		else {
			this.setValidateCss(itemName, false);
		}
	}
};

/**
 * Grid 의 Validation Error (정확히는 Error 메시지) 가 있는지 확인 
 * @return {boolean} true 이면 Validation Error 있음
 */
dhtmlXForm.prototype.hasError = function() {    
	return (! mvno.cmn.isEmpty(this['_ERRORS_']));
};


/**
 * @namespace dhtmlXFileUploader
 */

/**
 * uploader 컴포넌트 재생(재활용) 
 * - Popup 내의 uploader 는 close 시 clear 처리해야 하고
 *   다시 popup 띄우기 전에 revive 처리해야!!
 *   (IE 에서 flash 재실행시 문제 보완책)
 */
dhtmlXFileUploader.prototype.revive = function() {
	//this._initToolbar();
	this._initEngine();	
	//this._checkTitleScreen();
	this.buttons["info"].innerHTML = this._defaultTitleText;
};

/**
 * uploader 컴포넌트 Reset
 * - Popup Window 닫을때 clear 를 직접호출하면 onBeforeFileRemove 이벤트 발생
 *   내부적으로 onBeforeRemove 등에서 RESET 모드인 경우 무시하도록 처리
 */
dhtmlXFileUploader.prototype.reset = function() {
	this["_RESET_"] = true;
	this.clear();
	this["_RESET_"] = false;
};


/**
 * @namespace dhtmlXWindowsCell
 */

/**
 * Popup Window 를 강제로 닫음 (onClose 이벤트 처리등 무시)
 */
dhtmlXWindowsCell.prototype.closeForce = function() {

	//--------------------------------------------------------------
	// detachObject 등의 처리는 onClose 이벤트 처리에서 !!
	//--------------------------------------------------------------
	this['_CLOSE_FORCE_'] = true;

	this.close();
};


/**
 * @namespace dhtmlXCellObject
 */


/**
 * Cell 이 내장한 하위 iframe body 에 메시지(이벤트)를 보냄
 * 	<ul>
 * 		<li>parent 에서 iframe 으로 범용 처리요청 방법</li>
 * 		<li>iframe 과 parent 간 직접 함수호출은 원칙적으로 금지</li>
 * 	</ul>	 
 * @param {string} type - 메시지 종류 (사용자 정의 - RFRESH/.../...)
 * @param {object} data - 메시지 부가 정보 
 */
dhtmlXCellObject.prototype.sendMessage2Iframe = function(type, data) {

	var iframe = this.getFrame();
	if (! iframe || ! iframe.contentWindow)	return;

	//var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;

	// 아직 iframe 이 load 가 완료된 상태가 아님!(jQuery 인식안됨)
	// 이떄는 _IFRAME_PARAM_ 을 활용?
	if (! iframe.contentWindow.$) { 
		// REFRESH 일때만.. 초기처리로 자동연결시켜 둠.
		if (type == 'REFRESH') {
			//$(iframe).data("_IFRAME_PARAM_", { data: data });
			this.initParam2Iframe({ data: data });
		}
		return;
	}

	iframe.contentWindow.$("body").trigger({
		type: "_MESSAGE_",
		message: { type : type, data: data }
	});
};

/**
 * Cell 이 내장한 하위 iframe body 에 Refresh 요청 (메시지 전송)
 * 	<ul>
 * 		<li>sendMessage2Iframe 함수 활용</li>
 * 	</ul>
 * @param {object} data - Refresh 용 데이터
 */
dhtmlXCellObject.prototype.refreshIframe = function(data) {
	this.sendMessage2Iframe("REFRESH", data);
};

/**
 * Cell 이 내장한 하위 iframe 에 초기처리 데이터(callback 함수 포함) 전달 
 * 	<ul>
 * 		<li>주로 Tab Cell 에서 사용</li>
 * 		<li>PopupWindow 내의 iframe 에 대한 초기처리 데이터 전달은 popupWindow 활용</li>
 * 	</ul>	  
 * @param {object} param - 초기 처리용 Parameter (사용자 정의)
 */
dhtmlXCellObject.prototype.initParam2Iframe = function(param) {
	var iframe = this.getFrame();
	if (! iframe)	return;

	var data = $(iframe).data("_IFRAME_PARAM_");
	if(! data)	data = {};

	$.extend(data, param);

	$(iframe).data("_IFRAME_PARAM_", data); 
};


/**
 * 달력(Calendar) 한글화 
 */
dhtmlXCalendarObject.prototype.lang = "ko";
dhtmlXCalendarObject.prototype.langData = {
	"ko": {
		dateformat: "%Y-%m-%d",
		hdrformat: "%Y년 %F",
		monthesFNames: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		monthesSNames: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		daysFNames: ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"],
		daysSNames: ["일","월","화","수","목","금","토"],
		weekstart: 0,
		weekname: "주"
	}
};


/**
 * Grid Custom Column - 날짜형 데이터 처리
 * 	<ul>
 * 		<li>화면표시는 yyyy-mm-dd 형식으로, 실제데이터는 yyyymmdd 로 유지</li>
 * 		<li>Grid colTypes 에 "roXd" 으로 지정</li>
 * 		<li>DB 내의 varchar 형식의 날짜 데이터는 그대로, Date 형식은 to_char(date,'yyyymmdd') 로 내려주도록</li>
 * 		<li>Grid 가 Form 과 Bining 된 경우, Calender Component 와의 연동시 Format 이 중요</li>
 * 	</ul>	  
 * @param {dhtmlXCellObject} cell - dhtmlXCellObject
 */
function eXcell_roXd(cell) { 
    if (cell){
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function() {}; 
    this.isDisabled = function() { return true; }; 
    this.setValue = function(val) {
    	val = val || '' ;			// undefined, null 은 '' 로 표시
    	if (val.length == 8) 	{ this.setCValue(val.substr(0,4) + '-' + val.substr(4,2) + '-' + val.substr(6,2)); }
    	else 					{ this.setCValue(val); }
    };
	this.getValue = function() {
		return this.cell.innerHTML.replace(/-/g, "");
    };
}
eXcell_roXd.prototype = new eXcell;

/**
 * Grid Custom Column - 시간형 데이터 처리
 * 	<ul>
 * 		<li>화면표시는 hh24:mi:ss 형식으로, 실제데이터는 hh24miss 로 유지</li>
 * 		<li>Grid colTypes 에 "roXt" 으로 지정</li>
 * 	</ul>	  
 * @param {dhtmlXCellObject} cell - dhtmlXCellObject
 */
function eXcell_roXt(cell) { 
    if (cell){
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function() {}; 
    this.isDisabled = function() { return true; }; 
    this.setValue = function(val) {
    	val = val || '' ;			// undefined, null 은 '' 로 표시
    	if (val.length == 6) 	{ this.setCValue(val.substr(0,2) + ':' + val.substr(2,2) + ':' + val.substr(4,2)); }
    	else 					{ this.setCValue(val);	}
    };
	this.getValue = function() {
		return this.cell.innerHTML.replace(/:/g, "");
    };
}
eXcell_roXt.prototype = new eXcell;


/**
 * Grid Custom Column - 날짜 + 시간형 데이터 처리
 * 	<ul>
 * 		<li>화면표시는 yyyy-mm-dd hh24:mi:ss 형식으로, 실제데이터는 yyyymmddhh24miss 로 유지</li>
 * 		<li>Grid colTypes 에 "roXdt" 으로 지정</li>
 * 	</ul>	  
 * @param {dhtmlXCellObject} cell - dhtmlXCellObject
 */
function eXcell_roXdt(cell) { 
    if (cell){
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function() {}; 
    this.isDisabled = function() { return true; }; 
    this.setValue = function(val) {
    	val = val || '' ;			// undefined, null 은 '' 로 표시
    	if (val.length == 14) {
        	this.setCValue(
        		val.substr(0,4) + '-' + val.substr(4,2) + '-' + val.substr(6,2) + ' ' +
        		val.substr(8,2) + ':' + val.substr(10,2) + ':' + val.substr(12,2)
        	); 
    	}
    	else { this.setCValue(val); }
    };
	this.getValue = function() {
		return this.cell.innerHTML.replace(/[-: ]/g, "");
    };
}
eXcell_roXdt.prototype = new eXcell;

/**
 * Grid Custom Column - 년월 데이터 처리
 * 	<ul>
 * 		<li>화면표시는 yyyy-mm 형식으로, 실제데이터는 yyyymm 로 유지</li>
 * 		<li>Grid colTypes 에 "roXdm" 으로 지정</li>
 * 	</ul>	  
 * @param {dhtmlXCellObject} cell - dhtmlXCellObject
 */
function eXcell_roXdm(cell) { 
    if (cell){
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function() {}; 
    this.isDisabled = function() { return true; }; 
    this.setValue = function(val) {
    	val = val || '' ;			// undefined, null 은 '' 로 표시
    	if (val.length == 6) 	{ this.setCValue(val.substr(0,4) + '-' + val.substr(4,2)); }
    	else 					{ this.setCValue(val);	}
    };
	this.getValue = function() {
		return this.cell.innerHTML.replace(/[-: ]/g, "");
    };
}
eXcell_roXdm.prototype = new eXcell;


/**
 * Grid Custom Column - 년월 또는 년월일 데이터 처리
 * 	<ul>
 * 		<li>데이터 길이가 6자리인 경우 화면표시는 yyyy-mm 형식으로, 실제데이터는 yyyymm 로 유지</li>
 * 		<li>데이터 길이가 8자리인 경우 화면표시는 yyyy-mm-dd 형식으로, 실제데이터는 yyyymmdd 로 유지</li>
 * 	</ul>	 
 * @param {dhtmlXCelObject} cel - dhtmlXCellObject
 */
function eXcell_roXdom(cell) { 
    if (cell){
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function() {}; 
    this.isDisabled = function() { return true; }; 
    this.setValue = function(val) {
    	val = val || '' ;			// undefined, null 은 '' 로 표시
    	if (val.length == 6) 		{ this.setCValue(val.substr(0,4) + '-' + val.substr(4,2)); }
		else if (val.length == 8) 	{ this.setCValue(val.substr(0,4) + '-' + val.substr(4,2) + '-' + val.substr(6,2)); }
    	else 						{ this.setCValue(val); }
    };
	this.getValue = function() {
		return this.cell.innerHTML.replace(/[-: ]/g, "");
    };
}
eXcell_roXdom.prototype = new eXcell;


/**
 * Grid Custom Column - 주민등록번호/사업자등록번호/법인번호
 * 	<ul>
 * 		<li>13자리 : 주민등록번호 or 법인번호</li>
 * 		<li>10자리 : 사업자등록번호</li>
 * 	</ul>	 
 * @param {dhtmlXCelObject} cel - dhtmlXCellObject
 */
function eXcell_roXr(cell) { 
    if (cell){
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function() {}; 
    this.isDisabled = function() { return true; }; 
    this.setValue = function(val) {
    	val = val || '' ;			// undefined, null 은 '' 로 표시
        if (val.length == 13) 		{ this.setCValue(val.substr(0,6) + '-' + val.substr(6,7)); }
		else if (val.length == 10) 	{ this.setCValue(val.substr(0,3) + '-' + val.substr(3,2) + '-' + val.substr(5,5)); }
    	else 						{ this.setCValue(val); }
    };
	this.getValue = function() {
		return this.cell.innerHTML.replace(/[-]/g, "");
    };
}
eXcell_roXr.prototype = new eXcell;


/**
 * Grid Custom Column - 각종 전화번호(일반,휴대폰)
 * 	<ul>
 * 		<li>02-999(9)-9999</li>
 * 		<li>099-999(9)-9999</li>
 * 		<li>999(9)-9999</li>
 * 	</ul>	 
 * @param {dhtmlXCelObject} cel - dhtmlXCellObject
 */
function eXcell_roXp(cell) { 
    if (cell){
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function() {}; 
    this.isDisabled = function() { return true; }; 
    this.setValue = function(val) {
    	val = val || '' ;			// undefined, null 은 '' 로 표시
    	if(val.substr(0,1) == '0') {
    		if (val.substr(0,2) == '02') {
				if (val.length == 9) 		{ this.setCValue(val.substr(0,2) + '-' + val.substr(2,3) + '-' + val.substr(5,4)); }
				else if (val.length == 10) 	{ this.setCValue(val.substr(0,2) + '-' + val.substr(2,4) + '-' + val.substr(6,4)); }
    			else 						{ this.setCValue(val); }
    		}
    		else {
				if (val.length == 10) 		{ this.setCValue(val.substr(0,3) + '-' + val.substr(3,3) + '-' + val.substr(6,4)); }
				else if (val.length == 11) 	{ this.setCValue(val.substr(0,3) + '-' + val.substr(3,4) + '-' + val.substr(7,4)); }
    			else 						{ this.setCValue(val); }
    		}
    	}
    	else {
    		if (val.length == 7) 			{ this.setCValue(val.substr(0,3) + '-' + val.substr(3,4)); }
    		else if (val.length == 8) 		{ this.setCValue(val.substr(0,4) + '-' + val.substr(4,4)); }
    		else 							{ this.setCValue(val); }
    	}
    };
	this.getValue = function() {
		return this.cell.innerHTML.replace(/[-]/g, "");
    };
}
eXcell_roXp.prototype = new eXcell;

/**
 * Grid Custom Column - 우편번호
 * @param {dhtmlXCelObject} cel - dhtmlXCellObject
 */
function eXcell_roXz(cell) { 
    if (cell){
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function() {}; 
    this.isDisabled = function() { return true; }; 
    this.setValue = function(val) {
    	val = val || '' ;			// undefined, null 은 '' 로 표시
    	if (val.length == 6) 	{ this.setCValue(val.substr(0,3) + '-' + val.substr(3,3)); }
    	else 					{ this.setCValue(val);	}
    };
	this.getValue = function() {
		return this.cell.innerHTML.replace(/[-]/g, "");
    };
}
eXcell_roXz.prototype = new eXcell;

/**
 * Grid Custom Column - 문자/숫자 공용 
 * @param {dhtmlXCelObject} cel - dhtmlXCellObject
 *  <ul>
 *  	<li>문자인 경우 그대로, 숫자인 경우 comma 포맷 적용</li>
 *  </ul>
 */

function eXcell_roXns(cell) { 
    if (cell){
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function() {}; 
    this.isDisabled = function() { return true; }; 
    this.setValue = function(val) {
    	val = val || '' ;			// undefined, null 은 '' 로 표시
    	if (! val) {
    		this.setCValue(val);
    		return;
    	}	

    	var num = Number(val);
    	if (isNaN(num)) { this.setCValue(val); }
    	else 			{ this.setCValue(num.cf()); }
    };
	this.getValue = function() {
		var str = this.cell.innerHTML.replace(/[,]/g, "");
		if(! str)	return("");

		var num = Number(str);
		if (isNaN(num))	return str;
		return num.toString();
    };
}

eXcell_roXns.prototype = new eXcell;

/**
 * Grid Custom Column - 버튼
 * @param {dhtmlXCelObject} cel - dhtmlXCellObject
 *  <ul>
 *  	<li>버튼 속성 추가</li>
 *  </ul>
 */

function eXcell_bt(cell){
	if (cell){
		this.cell = cell;
		this.grid = this.cell.parentNode.grid;
	}
	
	this.edit = function(){}
	
	this.isDisabled = function(){ return true; }
	
	this.setValue=function(val){
		var btnNm;
		
		if (val != '') {
			btnNm = val;
		}else{
			var cellIdx = this.cell.cellIndex;
			var colNm = this.cell.parentNode.grid.getColumnId(cellIdx);
			btnNm = colNm;
		}
		
		this.setCValue("<input type='button' style='font-size:8pt;' value='"+btnNm+"'>",btnNm);
	}
}

eXcell_bt.prototype = new eXcell;

function ib_close() {
	alert('ddddd');
	//$.winIBSHEET.hide();
	$("#div:winIBSHEET").hide();
}

