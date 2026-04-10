function pop_setCookie(name,value,expiredays) {
    var todayDate = new Date();
    todayDate.setDate(todayDate.getDate() + expiredays);
    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

function pop_getCookie(name) {
    var nameOfCookie = name + "=";
    var x = 0

    while ( x <= document.cookie.length ) {
            var y = (x+nameOfCookie.length);
            if ( document.cookie.substring( x, y ) == nameOfCookie ) {
                if ( (endOfCookie=document.cookie.indexOf( ";",y )) == -1 )
                    endOfCookie = document.cookie.length;
                return unescape( document.cookie.substring(y, endOfCookie ) );
            }
            x = document.cookie.indexOf( " ", x ) + 1;
            if ( x == 0 )
                break;
    }
    return "";
}

function pop_openPop(popupSeq, width, height, x, y, popupUrl, popupYn) {
    if (pop_getCookie(popupSeq) != "done") {
        window.open("/popupDetail.do?popupSeq="+popupSeq ,popupSeq,"width="+width+", height="+height+", top="+y+",left="+x);
    }
}

$(document).ready(function($) {


    if(typeof menuCode != 'undefined') {

    	var objMenuList = [] ;

    	if (typeof NMCP_GNB_MENU_LIST  != 'undefined' ) {
    		objMenuList = NMCP_GNB_MENU_LIST;
    	} else if (typeof WR_GNB_MENU_LIST != 'undefined' ) {
    		objMenuList = WR_GNB_MENU_LIST;
    	}

    	//console.dir(objMenuList);
    	var pathname = $(location).attr('pathname');
    	var isCall = false;
    	var orgUrl =  "";

    	if (pathname == "/main.do") {
    		// isCall = true;
    		orgUrl = "/main.do";
    	} else {
    		for (var i = 0; i < objMenuList.length; i++) {
    			var thisUrl = objMenuList[i].urlAdr ;
    			orgUrl = objMenuList[i].urlAdr ;
    			if (thisUrl.indexOf("?") > -1 ) {
    				thisUrl = thisUrl.substring(0,thisUrl.indexOf("?")) ;
    			}
    			//console.log(thisUrl +"##" +pathname);
    			if (thisUrl == pathname ) {
    				//console.log("CALL~~!" );
    				isCall = true;
    				break;
    			}
    		}
    	}

        if(menuCode != "" && isCall) {
            $.ajax({
                type : "POST",
                url : "/getComPopupListAjax.do",
                data : { menuCode : menuCode, currentUrl : orgUrl },
                dataType : "json",
                timeout : 30000 ,
                success : function(message){
                	if(message != null && message.length > 0) {
                        for(var i = 0; i < message.length; i++) {
                            var heightSize = parseInt(message[i].heightSize,10)+54;
                            pop_openPop(message[i].popupSeq, message[i].widthSize, heightSize, message[i].xcrd, message[i].ycrd, message[i].popupUrl, message[i].popupOpenStat);
                        }
                    }
                },
                error : function( obj ){
                }
            });
        }
    }

});