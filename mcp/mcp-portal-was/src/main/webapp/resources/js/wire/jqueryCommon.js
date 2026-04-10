
var contextUrl = '';// Url 받아오기
var ajaxCommon = {
    cache 					: {},
    listeners 				: {},
    signInfo 				: {},
    currentActiveProduct	: '',

    // 브라우져 체킹 변수
    isIE  	 				: false,
    isIE6 	 				: false,
    isIE7 	 				: false,
    isIE8 	 				: false,
    isIE9 	 				: false,
    isIE10 					: false,
    isIE11 					: false,
    isOpera 				: false,
    isSafari 				: false,
    isGecko					: false,
    isChrome  				: false,
    isIPad    				: false,
    isIpod 					: false,
    isIphone 				: false,
    isAndroid  				: false,

    $form : null ,
    $body : null ,
    createForm : function(cfg) {
        if(!ajaxCommon.$form){
            ajaxCommon.$form = $('<form></form>');
        }
        ajaxCommon.$form.empty();

        if(cfg.id != undefined) {
            ajaxCommon.$form.attr('id',cfg.id)
        }
        if(cfg.name != undefined) {
            ajaxCommon.$form.attr('name',cfg.name);
        }
        ajaxCommon.$form.attr('method', cfg.method || 'post');
        ajaxCommon.$form.attr('action', cfg.action || '');
        ajaxCommon.$form.attr('target', cfg.target || '_self');
        ajaxCommon.$body.append(ajaxCommon.$form);

        return ajaxCommon.$form;
    },
    attachHiddenElement : function(name, value) {
        if(!ajaxCommon.$form) {
            alert('createForm() must be called');
            return;
        }

        var $hdnEl = $('<input type="hidden"></input>');
        $hdnEl.attr('name',name);
        $hdnEl.attr('value',value);
        ajaxCommon.$form.append($hdnEl);
    },
    formSubmit : function() {
        if(ajaxCommon.$form) {
            ajaxCommon.$form.submit();
        }
    },


    layerFactory : 	function()
    {
        var str  = '<div id="viewLoading" style="display:none;" class="loading"><img src="/kt12/images/common/anibox.gif" alt="로딩." /></div>	';
            return str;
    },

    getBrowser : function()
    {
        var  ua = navigator.userAgent.toLowerCase()
                    ,check = function(r){
                            return r.test(ua);
                    }
            ,DOC 			= document
            ,docMode 		= DOC.documentMode
            ,isOpera 		= check(/opera/)
            ,isChrome 		= check(/\bchrome\b/)
            ,isWebKit 		= check(/webkit/)
            ,isSafari 		= !isChrome && check(/safari/)
            ,isSafari2 		= isSafari && check(/applewebkit\/4/)
            ,isSafari3 		= isSafari && check(/version\/3/)
            ,isSafari4 		= isSafari && check(/version\/4/)
            ,isIE 			= !isOpera && (check(/msie/) || check(/rv:11\.0/))
            ,isIE7 			= isIE && (check(/msie 7/) || docMode == 7)
            ,isIE8 			= isIE && (check(/msie 8/) && docMode != 7)
            ,isIE9 			= isIE && check(/msie 9/)
            ,isIE10 		= isIE && check(/msie 10/)
            ,isIE11 		= isIE && check(/rv:11\.0/)
            ,isIE6 			= isIE && !isIE7 && !isIE8 && !isIE9 && !isIE10 && !isIE11
            ,isGecko 		= !isWebKit && check(/gecko/)
            ,isGecko2 		= isGecko && check(/rv:1\.8/)
            ,isGecko3 		= isGecko && check(/rv:1\.9/)
            ,isWindows 		= check(/windows|win32/)
            ,isMac 			= check(/macintosh|mac os x/)
            ,isAir 			= check(/adobeair/)
            ,isIPad 		= check(/ipad/)
            ,isIpod 		= check(/ipod/)
            ,isIphone 		= check(/iphone/)
            ,isLinux 		= check(/linux/)
            ,isAndroid		= check(/android/);

        this.isIE  	 	= isIE;
        this.isIE6 	 	= isIE6;
        this.isIE7 	 	= isIE7;
        this.isIE8 	 	= isIE8;
        this.isIE9 	 	= isIE9;
        this.isIE10 	= isIE10;
        this.isIE11 	= isIE11;
        this.isOpera 	= isOpera;
        this.isSafari 	= isSafari;
        this.isGecko	= isGecko;
        this.isChrome   = isChrome;
        this.isIPad     = isIPad;
        this.isIpod 	= isIpod;
        this.isIphone 	= isIphone;
        this.isAndroid  = isAndroid;
    },

    setCache : function(id,data) {
        this.cache[id] = data;
    },

    getMlisteners : function() {
        return listeners;
    },

    hasCache : function(id) {
        return this.cache[id]!=undefined;
    },


    getItem : function(cfg,func) {
        if(!cfg){
            alert('value is null!!!!');
            return;
        }

        cfg.cache = false;
        if(cfg.cache) {
            if(this.cache[cfg.id]!=undefined) {
                this.notifyItemLoaded(cfg.id,cache[cfg.id],cfg.isTabActive,cfg.isDivShow);
                return;
            }
        }

        var rand = "rand=" + Math.floor(Math.random() * 999999) + 100000; //100000 遺?꽣 999999踰붿쐞 以?radom ??諛쒖깮
        if (cfg.data && cfg.data != "") {
            cfg.data += "&" + rand ;
        } else {
            cfg.data =  rand ;
        }

        $.ajax({
            url       		: cfg.url
            ,data      		: cfg.data
            ,type      		: (cfg.method == undefined)?'post':cfg.method
            ,contentType  	: (cfg.contentType == undefined)?"application/x-www-form-urlencoded;charset=UTF-8":cfg.contentType
            ,dataType  		: cfg.dataType
            ,context   		: document.body
            ,async     		: (cfg.async == undefined)?true:cfg.async
            ,timeout   		: (cfg.timeout == undefined)? 60000:cfg.timeout
            ,beforeSend : function(){

            }
            ,error: function(e){
                if (cfg.errorCall == undefined) {
                    ajaxCommon.notifyLoadFail(cfg.id,e);
                }

            }
            ,success: function(data){
                ajaxCommon.loadResponse(cfg.id,data,cfg.isTabActive,cfg.isDivShow);
                ajaxCommon.notifyLoadFinish(cfg.id,data,cfg.loading,func);
            }
        });
    },
    
    getItemFileUp : function(cfg,func) {
        if(!cfg){
            alert('value is null!!!!');
            return;
        }

        cfg.cache = false;
        if(cfg.cache) {
            if(this.cache[cfg.id]!=undefined) {
                this.notifyItemLoaded(cfg.id,cache[cfg.id],cfg.isTabActive,cfg.isDivShow);
                return;
            }
        }

        var rand = Math.floor(Math.random() * 999999) + 100000; //100000 遺?꽣 999999踰붿쐞 以?radom ??諛쒖깮
        cfg.data.append("rand", rand);
        
        $.ajax({
        	type      		: "POST"        	
        	,url       			: cfg.url	
        	,data      		: cfg.data
        	,cache : false
            ,processData : false
            ,contentType : false
        	,timeout   		: 80000   
            ,beforeSend : function(){

            }
            ,error: function(e){
                if (cfg.errorCall == undefined) {
                    ajaxCommon.notifyLoadFail(cfg.id,e);
                }

            }
            ,success: function(data){
                ajaxCommon.loadResponse(cfg.id,data,cfg.isTabActive,cfg.isDivShow);
                ajaxCommon.notifyLoadFinish(cfg.id,data,cfg.loading,func);
            }
        });
    },


    loadResponse : function(id,data,isTabActive,isDivShow){
        this.cache[id] = data
        this.notifyItemLoaded(id,data,isTabActive,isDivShow);
    },

    isObject : function(obj)
    {
        return Object.prototype.toString.call(obj) === '[object Object]';
    },

    clearAll : function(){
        this.cache = new Array();
    },


    notifyLoadFinish : function(id,data,loading,func){
            var code = undefined;


            if(typeof data == 'string')	{
                try {
                    data = JSON.parse(data);
                    code = data;
                } catch(e){}
            } else {
                try	{
                    code = data;
                } catch(x){}
            }

            if (loading == undefined || loading == true) {
                $('#ajaxLoading').css('display','none');
            }

            //null to ""
            var jsonStr = JSON.stringify(data, function(key,value) {
                if(value === null) {
                    return "";
                }
                return value;
            });

            data = JSON.parse(jsonStr);
            func(data);

    },

    notifyLoadFail : function(id,e){
        $('#ajaxLoading').css('display','none');
        //error code : 0   ==> timeout
        // AJAX 호출을 보낸 브라우저 의 새로 고침 이 AJAX 응답을 받기 전에 발생 된 경우 는 0 의 상태 코드를 얻을 수 있을 수 있습니다. AJAX 호출 이 취소되고 이 상태를 얻을 것이다 .
        //error code : 500 ==> internal server error

        var errorMsg = '';

        if(e.status != '0')	{
            errorMsg = '처리 중 오류가 발생하였습니다.';
            alert(errorMsg);
        }


    },

    notifyItemLoaded : function(id,data,isTabActive,isDivShow){
        var code = undefined;
        if(typeof data == 'string')	{
            try
            {
                data = JSON.parse(data);
                code = data['RESULT_TYPE'];
                if(code != undefined && code == '1') return;
            }
            catch(e){}
        } else {
            try	{
                if (data.RESULT_TYPE == 'L') {
                    window.location.href = '/needLogin.jsp?goPage='+data.RETURN_URL;
                    return;
                } else if (data.RESULT_TYPE == 'E') {
                    window.location.href = '/etc/common_message_err.jsp';
                    return;
                } else if (data.RESULT_TYPE == 'U') {
                    window.location.href = '/etc/common_message_auth.jsp?reason='+data.reason;
                    return;
                }
                //if(code != undefined && code == '1') return;
            } catch(x){}
        }

    },

    getSerializedData : function(param){
        var resultStr = '';
        if(Object.prototype.toString.call(param) === '[object Object]') {
            var arr = [];
            var encodedParam = '';
            for(var p in param) {
                if(param.hasOwnProperty(p)) {
                    encodedParam = param[p];
                    if(typeof encodedParam == 'string') {
                        encodedParam = encodedParam.replace(/\%/gm,'%25').replace(/\&/gm,'%26').replace(/\+/gm,'%2B').replace(/\?/gm,'%3F');
                    }
                    arr.push(p+'='+encodedParam);
                }
            }

            resultStr = arr.join('&');
        } else if($.isArray(param)) {
            resultStr = param.join('&');
        }
        return resultStr;
    },


    setPaging : function($pageObj,pageInfoBean)
    {
        //페이징 처리
        var firstPageNo = pageInfoBean.firstPageNo;
        var firstPageNoOnPageList = pageInfoBean.firstPageNoOnPageList;
        var totalPageCount = pageInfoBean.totalPageCount;
        var pageSize = pageInfoBean.pageSize;
        var lastPageNoOnPageList = pageInfoBean.lastPageNoOnPageList;
        var currentPageNo = pageInfoBean.pageNo;
        var lastPageNo = pageInfoBean.lastPageNo;
        var arrHtml         = [];

        arrHtml.push("<div id='tablePage'> \n");

        if(currentPageNo > 1 ){
            if (firstPageNoOnPageList > pageSize) {
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+firstPageNo+"'  class='pageButton btn_first _pageWrap' title='처음 페이지'>처음 페이지</button>\n");
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+ (firstPageNoOnPageList-1)+"' class='pageButton btn_prev _pageWrap' title='이전 페이지'>이전 페이지</button>\n");
            } else {
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+firstPageNo+"'     class='pageButton btn_first _pageWrap' title='처음 페이지'>처음 페이지</button>\n");
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+ (currentPageNo-1) +"' class='pageButton btn_prev _pageWrap' title='이전 페이지'>이전 페이지</button>\n");
            }
        } else {
            arrHtml.push("<button type='button' title='처음 페이지' class='pageButton btn_first'>처음 페이지</button>\n");
            arrHtml.push("<button type='button' title='이전 페이지' class='pageButton btn_prev'>이전 페이지</button>\n");
        }

        for (var i = firstPageNoOnPageList; i <= lastPageNoOnPageList; ++i) {
            if (i == currentPageNo) {
                arrHtml.push("<button type='button' title='현재페이지' class='pageButton active'>"+ i + "</button>\n");
            } else {
                arrHtml.push("<button type='button' title='"+i+" 페이지' pageNo='"+i+"' class='pageButton _pageWrap'>"+ i + "</button>\n");
            }
        }

        if(lastPageNo > currentPageNo ){
            if (lastPageNoOnPageList < totalPageCount) {
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+(firstPageNoOnPageList + pageSize)+"' class='pageButton btn_next _pageWrap' title='다음 페이지'>다음 페이지</button>\n");
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+lastPageNo+"'  class='pageButton btn_last _pageWrap' title='마지막 페이지'>마지막 페이지</button>\n");
            } else {
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+(currentPageNo+1)+"'  class='pageButton btn_next _pageWrap' title='다음 페이지'>다음 페이지</button>\n");
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+lastPageNo+"'   class='pageButton btn_last _pageWrap' title='마지막 페이지'>마지막 페이지</button>\n");
            }
        } else {
            arrHtml.push("<button type='button'  class='pageButton btn_next' title='다음 페이지'>다음 페이지</button>\n");
            arrHtml.push("<button type='button'  class='pageButton btn_last' title='마지막 페이지'>마지막 페이지</button>\n");
        }

        arrHtml.push("</div>\n");

        $pageObj.html(arrHtml.join(''));
    },


    setAdminPaging : function($pageObj,pageInfoBean)
    {
        //페이징 처리
        var firstPageNo = pageInfoBean.firstPageNo;
        var firstPageNoOnPageList = pageInfoBean.firstPageNoOnPageList;
        var totalPageCount = pageInfoBean.totalPageCount;
        var pageSize = pageInfoBean.pageSize;
        var lastPageNoOnPageList = pageInfoBean.lastPageNoOnPageList;
        var currentPageNo = pageInfoBean.pageNo;
        var lastPageNo = pageInfoBean.lastPageNo;
        var arrHtml         = [];

        arrHtml.push("<div id='tablePage'> \n");


        if(currentPageNo > 1 ){
            if (firstPageNoOnPageList > pageSize) {
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+firstPageNo+"'  class='pageButton btn_first _pageWrap' title='처음 페이지'>First</button>\n");
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+ (firstPageNoOnPageList-1)+"' class='pageButton btn_prev _pageWrap' title='이전 페이지'>Prev</button>\n");
            } else {
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+firstPageNo+"'     class='pageButton btn_first _pageWrap' title='처음 페이지'>First</button>\n");
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+ (currentPageNo-1) +"' class='pageButton btn_prev _pageWrap' title='이전 페이지'>Prev</button>\n");
            }
        } else {
            arrHtml.push("<button type='button' title='처음 페이지' class='pageButton btn_first'>First</button>\n");
            arrHtml.push("<button type='button' title='이전 페이지' class='pageButton btn_prev'>Prev</button>\n");
        }

        for (var i = firstPageNoOnPageList; i <= lastPageNoOnPageList; ++i) {
            if (i == currentPageNo) {
                arrHtml.push("<button type='button' title='현재페이지' class='pageButton pageButtonActive'>"+ i + "</button>\n");
            } else {
                arrHtml.push("<button type='button' title='"+i+" 페이지' pageNo='"+i+"' class='pageButton _pageWrap'>"+ i + "</button>\n");
            }
        }

        if(lastPageNo > currentPageNo ){
            if (lastPageNoOnPageList < totalPageCount) {
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+(firstPageNoOnPageList + pageSize)+"' class='pageButton btn_next _pageWrap' title='다음 페이지'>Next</button>\n");
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+lastPageNo+"'  class='pageButton btn_last _pageWrap' title='마지막 페이지'>Last</button>\n");
            } else {
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+(currentPageNo+1)+"'  class='pageButton btn_next _pageWrap' title='다음 페이지'>Next</button>\n");
                arrHtml.push("<button type='button' onclick='javascript:void(0)' pageNo='"+lastPageNo+"'   class='pageButton btn_last _pageWrap' title='마지막 페이지'>Last</button>\n");
            }
        } else {
            arrHtml.push("<button type='button'  class='pageButton btn_next' title='다음 페이지'>Next</button>\n");
            arrHtml.push("<button type='button'  class='pageButton btn_last' title='마지막 페이지'>Last</button>\n");
        }

        arrHtml.push("</div>\n");

        $pageObj.html(arrHtml.join(''));
    },
    
    
    setSelectBoxReadonly: function(obj){
        var objVal=   obj.val();
        var objTxt=   obj.find("option:selected").text();
        obj.find("option").remove();
        obj.append("<option value='"+objVal+"'>"+objTxt+"</option>");
    },
    
    isNullNvl : function (varCk,reVal){
		if( varCk != undefined && varCk != "undefined" && varCk != null && varCk != "" ){
			return varCk;
		} else {
			return reVal;
		}
    }
};

//data 출력샘플
var printConsoleLog = function(data){
    console.log(data);
};

//샘플
var sampleAjax= function(){
    // 세팅 json 데이터 , ajax후 실행할 function
    ajaxCommon.getItem({
        id:'getAgreList'
        ,cache:false
        ,url:'/usage/TelTotalUseTimeAjax.action?c='+Math.random()
        ,data: $("#form").serialize()
        ,dataType:"json"
    }, printConsoleLog);

};


//백키 막기
var backKey = function(){
    $(document).keydown(function(e){
        var actEl=document.activeElement;
        if(e.which==8)
        {
            if(actEl.getAttribute('readonly') || actEl.nodeName=='BODY' || actEl.nodeName=='DIV') return false;
        }
    });
};


var setContextUrl = function(url){
    contextUrl=url;
};

//파일 다운로드 flag 일반보드 1 policy 2   ....
var fileDownLoad = function(fileSeq,flag,callback,callbackParam){
    var $iFrm = $('<iframe id="donwloadIFrm" name="donwloadIFrm" frameBorder="0" style="display:none;" scrolling="no"></iframe>');
    $('body').append($iFrm);
     var $form = $('<form></form>');
     $form.attr('action', '/fileDownload.do');
     $form.attr('method', 'get');
     $form.attr('target', 'donwloadIFrm');
     $('body').append($form);

     fileSeq = (fileSeq=='') ? 0 : fileSeq;
     var fileNm = $('<input type="hidden" value="'+flag+'" name="flag">');
     var fileSeq = $('<input type="hidden" value="'+fileSeq+'" name="fileSeq">');

     $form.append(fileNm).append(fileSeq);
     $form.submit();

     if(typeof callback == 'function' && callback != null) {
         callback(callbackParam);
     }
};





var numberWithCommas = function (x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};

var numberMinusWithCommas = function (x) {
    if (x == null || x == "" || x == "0" || x == 0 ) {
        return "0";
    }
    return "-" + x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};



var fnIsNumeric = function (param) {
    if( $.isNumeric(param) ) {
        return param;
    } else {
        return 0;
    }
}


//팝업열기 (가로,세로,url)
var openPopupUtil =function(h,w,url){
    var centeredY,centeredX;

    if ($.browser.msie) {//hacked together for IE browsers
        //centeredY = (window.screenTop - 120) + ((((document.documentElement.clientHeight + 120)/2) - (settings.height/2)));
        //centeredX = window.screenLeft + ((((document.body.offsetWidth + 20)/2) - (settings.width/2)));
    }else{
        centeredY = window.screenY + (((window.outerHeight/2) - (h/2)));
        centeredX = window.screenX + (((window.outerWidth/2) - (w/2)));
    }
    var newPopup =window.open(url, 'usimMcp',  'height='+h+',width='+w+',left=' + centeredX +',top=' + centeredY);
    if($.browser.chrome){

    }else{
     if (window.focus) {newPopup.focus();}

   if (!newPopup.closed) {newPopup.focus();}

    }
};


//팝업열기 옵션추가 (가로,세로,url,옵션)
var openPopupOptUtil =function(h,w,url,opt){
    var centeredY,centeredX;

    if ($.browser.msie) {//hacked together for IE browsers
        //centeredY = (window.screenTop - 120) + ((((document.documentElement.clientHeight + 120)/2) - (settings.height/2)));
        //centeredX = window.screenLeft + ((((document.body.offsetWidth + 20)/2) - (settings.width/2)));
    }else{
        centeredY = window.screenY + (((window.outerHeight/2) - (h/2)));
        centeredX = window.screenX + (((window.outerWidth/2) - (w/2)));
    }
    var newPopup =window.open(url, 'usimMcp',  'height='+h+',width='+w+',left=' + centeredX +',top=' + centeredY +','+ opt);
    if($.browser.chrome){

    }else{
     if (window.focus) {newPopup.focus();}

   if (!newPopup.closed) {newPopup.focus();}

    }
};


//팝업열기 (가로,세로,url,스크롤)
var openPopupOptUtil =function(h,w,url,scroll){
    var centeredY,centeredX;

    if ($.browser.msie) {//hacked together for IE browsers
        //centeredY = (window.screenTop - 120) + ((((document.documentElement.clientHeight + 120)/2) - (settings.height/2)));
        //centeredX = window.screenLeft + ((((document.body.offsetWidth + 20)/2) - (settings.width/2)));
    }else{
        centeredY = window.screenY + (((window.outerHeight/2) - (h/2)));
        centeredX = window.screenX + (((window.outerWidth/2) - (w/2)));
    }
    var newPopup =window.open(url, 'usimMcp',  'height='+h+',width='+w+',left=' + centeredX +',top=' + centeredY + ', scrollbars='+scroll);
    if($.browser.chrome){

    }else{
     if (window.focus) {newPopup.focus();}

   if (!newPopup.closed) {newPopup.focus();}

    }
};


var loadingInnerHTML = "<div id='ajaxLoading' class='uil-default-css' style='-webkit-transform:scale(0.72)'>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(0deg) translate(0,-60px);transform:rotate(0deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(30deg) translate(0,-60px);transform:rotate(30deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(60deg) translate(0,-60px);transform:rotate(60deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(90deg) translate(0,-60px);transform:rotate(90deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(120deg) translate(0,-60px);transform:rotate(120deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(150deg) translate(0,-60px);transform:rotate(150deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(180deg) translate(0,-60px);transform:rotate(180deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(210deg) translate(0,-60px);transform:rotate(210deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(240deg) translate(0,-60px);transform:rotate(240deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(270deg) translate(0,-60px);transform:rotate(270deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(300deg) translate(0,-60px);transform:rotate(300deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "<div style='top:80px;left:93px;width:14px;height:40px;background:#00b2ff;-webkit-transform:rotate(330deg) translate(0,-60px);transform:rotate(330deg) translate(0,-60px);border-radius:10px;position:absolute;'></div>";
    loadingInnerHTML += "</div>";
    loadingInnerHTML += "<div id='ajaxLoadingBackgrd' class='wrap-loading'></div>";



  //숫자만 입력가능 (ex: <input type="text"  class="onlyNum" />)
    $(document).on("blur keyup", ".onlyNum ,.phone", function() {
        $(this).val($(this).val().replace(/[^0-9]/gi,""));
    });

   //한글 제외 처리
    $(document).on("blur keyup", ".notKor", function() {
        $(this).val($(this).val().replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/gi,""));
    });




    $(document).ready(function(){




        ajaxCommon.$body = $('body');


        //로그인 페이지 이동
        $("#btnFrontLogin").click(function(){
            var redirectUrl = $("#redirectUrl").val();
            if (redirectUrl != null) {
                location.href='/loginForm.do?redirectUrl='+encodeURIComponent(redirectUrl);
            } else {
                location.href='/loginForm.do';
            }
        }) ;


        //로그인 페이지 이동
        $("#login").click(function(){
            var redirectUrl = $("#redirectUrl").val();
            if (redirectUrl != null) {
                location.href='/m/loginForm.do?redirectUrl='+encodeURIComponent(redirectUrl);
            } else {
                location.href='/m/loginForm.do';
            }
        }) ;
        
      //로그인 페이지 이동
        $("._login").click(function(){
            var redirectUrl = $("#redirectUrl").val();
            if (redirectUrl != null) {
                location.href='/m/loginForm.do?redirectUrl='+encodeURIComponent(redirectUrl);
            } else {
                location.href='/m/loginForm.do';
            }
        }) ;






        $("#btnShortCut").click(function(){

        	if ($("#lnb").length > 0) {
        		//좌측 메뉴가 있다..

        		if ($(".customerservice").length > 0) {
        			$(".customerservice").prop("tabIndex", -1);
                	$(".customerservice").focus();
                	return ;
        		}

        		if ($(".mypage").length > 0) {
        			$(".mypage").prop("tabIndex", -1);
                	$(".mypage").focus();
                	return ;
        		}



        	} else {
        		$("#container").prop("tabIndex", -1);
            	$("#container").focus();
        	}

        	return ;
        }) ;






        $(document).on("blur keyup", "input[type='number']", function() {

            var thisVal = $(this).val();
            var thisLength = thisVal.length ;
            var thisMaxLength = $(this).attr("maxlength");

            if (thisMaxLength != null && thisLength > thisMaxLength){
                $(this).val(thisVal.slice(0, thisMaxLength));
               }


        });

        //ie readonly일경우 backspace 뒤로가기 방지
        $(document).unbind('keydown').bind('keydown', function (event) {
        	if (event.keyCode === 8) {
        	     var d = $(event.srcElement || event.target);
        	     var disabled = d.prop("readonly") || d.prop("disabled");
        	     if (disabled) {
        	    	 event.preventDefault();
        	         return false;
        	     }
        	}
        });

        //서식지 datepicker 공통 사용
        if ($.fn.datepicker) {
	        $(".datepicker").datepicker({
	            monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	            monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	            dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	            dateFormat:'yymmdd',
	            showMonthAfterYear: true,
	            changeYear:true ,
	            changeMonth:true,
	            yearSuffix: '년',
	            yearRange: 'c-100:c+30'

	        }).blur(function(){
	            var thisVal = $(this).val();
	            if (thisVal.length < 8) {
	                $(this).val("");
	            } else {
	                var yearfield = thisVal.substring(0,4);
	                var monthfield = thisVal.substring(4,6);
	                var dayfield = thisVal.substring(6,8);
	                var dayobj = new Date(yearfield, monthfield - 1, dayfield);

	                if ((dayobj.getMonth() + 1 != monthfield)
	                     || (dayobj.getDate() != dayfield)
	                     || (dayobj.getFullYear() != yearfield)) {
	                    alert("날짜 형식이 올바르지 않습니다. YYYYMMDD");
	                    $(this).val("");
	                    $(this).focus();
	                }
	            }
	        });
        }

    })




var timmer = {

        interval : '',
        // duration : 몇분 동안 멈출것인지 ,  timerId : 타이머 들어갈 html id , countdownId : 쿨다운 영역 id , timeoverId : 타임오버 id , certifySmsNoId : sms 인증번호 입력란 id
        dailyMissionTimer : function (cfg) {

            $("#"+cfg.timeover).css("display","none");
            $("#"+cfg.countdown).css("display","block");

            var timer = cfg.duration  * 60;//받아온 파라미터를 분단위로 계산한다
            var minutes =0;
            var seconds = 0;

            this.interval = setInterval(function(){

                minutes = parseInt(timer / 60 % 60, 10);
                seconds = parseInt(timer % 60, 10);

                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                $("#"+cfg.timerId).text(minutes+"분 "+seconds+"초");

                if (--timer < 0) {
                    timer = 0;
                    $("#"+cfg.timerId).text("");
                    $("#"+cfg.countdownId).css("display","none");
                    $("#"+cfg.timeoverId).css("display","block");
                    $("#"+cfg.certifySmsNoId).val("");
                    clearInterval(this.interval);
                    return;
                }
            }, 1000);
        },
        clearTimmer : function(){
            clearInterval(this.interval);
        }

    };


    Date.prototype.format = function(f) {
        if (!this.valueOf()) return " ";
        var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
        var d = this;
        return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
            switch ($1) {
                case "yyyy": return d.getFullYear();
                case "yy": return (d.getFullYear() % 1000).zf(2);
                case "MM": return (d.getMonth() + 1).zf(2);
                case "dd": return d.getDate().zf(2);
                case "E": return weekName[d.getDay()];
                case "HH": return d.getHours().zf(2);
                case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
                case "mm": return d.getMinutes().zf(2);
                case "ss": return d.getSeconds().zf(2);
                case "a/p": return d.getHours() < 12 ? "오전" : "오후";
                default: return $1;
            }
        });
    };

    String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
    String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
    Number.prototype.zf = function(len){return this.toString().zf(len);};




    /* 비밀번호 규칙 확인 */
	var pwPatternCheck = function(obj) {

		var pwstr = obj.val();

	    var regex = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z\s])(?=.*[!@#$%^&+=]).*$/;
	    var regex1 = /^.*(?=^.{10,15}$)(?=.*\d)(?=.*[a-zA-Z\s]).*$/;
	    var regex2 = /^.*(?=^.{10,15}$)(?=.*\d)(?=.*[!@#$%^&+=]).*$/;
	    var regex3 = /^.*(?=^.{10,15}$)(?=.*[a-zA-Z\s])(?=.*[!@#$%^&+=]).*$/;
	    var regexN = /^.*(?=.*\d)(?=.*[a-zA-Z\s])(?=.*[!@#$%^&+=]).*$/;
	    var regexN1 = /^.*(?=.*\d)(?=.*[a-zA-Z\s]).*$/;
	    var regexN2 = /^.*(?=.*\d)(?=.*[!@#$%^&+=]).*$/;
	    var regexN3 = /^.*(?=.*[a-zA-Z\s])(?=.*[!@#$%^&+=]).*$/;

	    /* 패스워드 규칙 체크 */
	    if(!regex.test(pwstr)
	        && (!regexN1.test(pwstr)
	            ||!regexN2.test(pwstr)
	            || !regexN3.test(pwstr))){
	        if(!regex1.test(pwstr)){
	            if(!regex2.test(pwstr)){
	                if(!regex3.test(pwstr)){
	                    alert("영문, 숫자, 특수문자 중 2종류를 조합하여 10~16자리 조합하여 비밀번호 입력하세요.");
	                    obj.focus();
	                    return false;
	                }
	            }
	        }
	    }

	    if(!regex.test(pwstr)){
	        if(!regex1.test(pwstr)){
	            if(!regex2.test(pwstr)){
	                if(!regex3.test(pwstr)){
	                	alert("영문, 숫자, 특수문자 중 3종류를 조합하여 8~16자리 조합하여 비밀번호 입력하세요.");
	                	obj.focus();
	                    return false;
	                }
	            }
	        }
	    }

	    //동일 문자 체크
	    if (/(\w)\1\1/.test(pwstr) || /([!@#$%^&+=])\1\1/.test(pwstr) || /(\s)\1\1/.test(pwstr)) {
	    	alert("동일한 문자를 3자리 이상 반복해서 사용할 수 없습니다(예. 111, aaa)");
	    	obj.focus();
	    	return false;
	    }

	    //연속 문자 체크
	    var SamePass_1 = 0; //연속성(+) 카운드
	    var SamePass_2 = 0; //연속성(-) 카운드

	    var chr_pass_0;
	    var chr_pass_1;
	    var chr_pass_2;

	    for (var i=0; i < pwstr.length; i++) {
	        chr_pass_0 = pwstr.charAt(i);
	        chr_pass_1 = pwstr.charAt(i+1);
	        chr_pass_2 = pwstr.charAt(i+2);

	        //연속성(+) 카운드
	        if (chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == 1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == 1) {
	            SamePass_1 = SamePass_1 + 1
	        }

	        //연속성(-) 카운드
	        if (chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == -1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == -1) {
	            SamePass_2 = SamePass_2 + 1
	        }
	    }

	    if (SamePass_1 > 0 || SamePass_2 > 0 ) {
	        alert("연속된 문자열은 사용 할 수 없습니다(예. 123, 321, abc, cba)");
	        obj.focus();
	        return false;
	    }

	    //키보드 연속 입력 체크
	    var listThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm";
	    var listThreeChar_r = listThreeChar.split("").reverse().join("");
	    var arrThreeChar = listThreeChar.split("|");
	    for (var i=0; i<arrThreeChar.length; i++) {
	    	if (pwstr.toLowerCase().match(".*" + arrThreeChar[i] + ".*")) {
	    		alert("키보드 상의 3글자 이상 연속되는 문자열은 사용 할 수 없습니다( 예. 123, qwe, ewq)");
	    		obj.focus();
	    		return false;
	    	}
	    }

	    var arrThreeChar_r = listThreeChar_r.split("|");
	    for (var i=0; i<arrThreeChar_r.length; i++) {
	    	if (pwstr.toLowerCase().match(".*" + arrThreeChar_r[i] + ".*")) {
	    		alert("키보드 상의 3글자 이상 연속되는 문자열은 사용 할 수 없습니다( 예. 123, qwe, ewq)");
	    		obj.focus();
	    		return false;
	    	}
	    }

	    return true;

	};

