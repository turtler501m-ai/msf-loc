<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.ktis.msp.base.login.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
   String l_reqUri    = StringUtils.defaultString(request.getParameter("reqUri"),"");
%>
<style type="text/css">
   body{width:auto !important; max-width:none !important; background: url(/images/bg_login_top.jpg) repeat-x top !important; text-align:center !important;}
   body h4{margin-top:50px;}
</style>
<h4><img src="/images/etc/img_login_txt.png" alt="Login / kt is mobile executive system" /></h4>
<div class="login_box">
   <form name="form1" id="form1"  method="post" action="<c:url value='/cmn/login/loginProc.do' />" >
      <fieldset class="form_area">
         <legend class="blind">로그인</legend>
         <div class="login">
            <p align="left">
               <label for="usrId" class="blind">아이디</label> <input type="text" id="usrId" name="usrId" style="ime-mode:disabled;" class="ip_type" />
               <span><label for="pass" class="blind">비밀번호</label> <input type="password" id="pass" name="pass" class="ip_type" /></span>

               <input type="text" id="otp" name="otp" style="ime-mode:disabled; display:none; width:175px" class="ip_type" maxlength="6" tabindex="-1"/>
               <span id="wTimer" style="display:none; color: #D5D5D5; font-family: 돋움; font-weight:bold;">(180초)</span>
            </p>

            <div id="pLogin"><a href="#none" id="login"><img src="/images/etc/btn_login.png" style="margin: 5px 0 0 10px;" /></a></div>
            <div id="aLogin" style="display:none;"><img src="/images/etc/btn_login_disable.png" style="margin: 5px 0 0 10px;" /></div>

            <a id="btnSms" href="javascript:fnLogin()" tabindex="-1"><img id="btnOtp" src="/images/etc/btn_confirm.png" alt="OTP인증" style="display:none; margin: 2px 0 0 10px;"/></a>
         </div>
      </fieldset>

      <input type="hidden" name="reqUri" id="ar_req_uri" value="<%=l_reqUri%>" tabindex="-1"/>
   </form>
</div>
<div id ="LOADING" style="z-index:999999;position:fixed;top:30%;left:45%;display:none;"><img src=../../images/loading.gif ></div>
<div class="info_txt">
   <!-- 20150210 JMH 추가 -->
   <table cellpadding="0" class="tb_base" summary="고객정보의 개인용도 사용 절대 엄금을 담고 있습니다.">
      <caption><span>고객정보의 개인용도 사용 절대 엄금</span></caption>
      <colgroup>
         <col width="30%" />
         <col width="70%" />
      </colgroup>
      <tbody>
         <tr>
            <th scope="row"><img src="/images/etc/img_infotxt3.gif" alt="" /></th>
            <td>
               <p class="bull00"><span class="pc_r">고객정보의 개인용도 사용 절대 엄금</span></p>
               <ol class="mt10">
                  <li>1. 고객정보를 조회하여 제 3자(판매점, 친구, 친지 등) 에게 유출 제공하는 경우</li>
                  <li>2. 고객정보를 조회하여 업무 이외에 사용시</li>
               </ol>
               <ul class="mt10">
                  <li>위반 시 5년 이하의 징역 또는 5천만원 이하의 벌금 부과(제공받은 자도 처벌)<br />(관련법규 : 정보통신망 이용촉진 및 정보보호 등에 관한 법률)</li>
                  <li>N-Step/N-RDS/M 전산을 이용해 고객정보를 조회할 경우 누가, 어떤 컴퓨터를 통해 언제, 어디서 조회했는지<br />모든 이력(단순조회 포함) 이 전산으로 자동 관리되므로 고객정보를 불법으로 사용하였을 경우 당사자를 즉시<br />적발할 수 있습니다.</li>
               </ul>
            </td>
         </tr>
      </tbody>
   </table>
   <p class="info_agree"><input type="checkbox" id="agree" name="agree" class="va_m" /><label for="agree"> 위 내용에 동의합니다.</label></p>
   <!-- //20150210 JMH 추가 -->
   <table cellpadding="0" class="tb_base" summary="선불폰과 후불폰의 고객센터정보와 대리점 전용센터등의 정보를 담고 있습니다.">
      <caption><span>고객센터 안내</span></caption>
      <colgroup>
         <col width="30%" />
         <col width="70%" />
      </colgroup>
      <tbody>
         <tr>
            <th scope="row"><img src="/images/etc/img_infotxt1.gif" alt="" /></th>
            <td><p class="bull00"><span class="pc_r">선불폰</span> 고객센터 <span style="letter-spacing: -0.5px; font-size: 12px;"> &nbsp;☞  09~12시 / 13~18시, 주말 및 공휴일 제외</span></p> 
               <ul>
                  <li><strong>가상계좌조회/잔액조회 :</strong> ☎ 080-0120-114</li>
                  <li><strong>선불카드 충전 :</strong> ☎ 080-0130-114</li>
                  <li><strong>기타 문의사항 :</strong> ☎ 1899-5000</li>
                  <!-- <li><strong>앱 :</strong> 플레이스토어 > ktism 검색 > 앱 다운로드 설치 (충전, 조회등 가능)</li> -->
               </ul>
               <br />
               <span class="bull00"><span class="pc_r">후불폰</span> 고객센터 <span style="letter-spacing: -0.5px; font-size: 12px;"> &nbsp;☞ 09~12시 / 13~18시, 주말 및 공휴일 제외</span></span>
               <ul>
                  <li>☎ 1899-5000</li>
	               <li><strong><a onclick= "javascript:fnPwdReset();" style="cursor:pointer; color:blue;"  target="_blank"> 패스워드 초기화 </a></strong></li>
               </ul>
            </td>
         </tr>
         <tr>
            <th scope="row"><img src="/images/etc/img_infotxt2.gif" alt="" /></th>
            <td><p class="bull00"><span class="pc_r">대리점</span> 전용센터 <span style="letter-spacing: -0.5px; font-size: 12px;"> &nbsp;☞  09~12시 / 13~18시, 주말 및 공휴일 제외</span></p>
               <ul>
               	  <!-- <li><strong><a onclick= "javascript:callMemAdd();" style="cursor:pointer; color:blue;"  target="_blank"> 대리점계정신청 </a></strong></li> -->
                  <li><strong>헬프데스크 :</strong> ☎ 02-2624-8412(후불전용)</li>
                  <li style="background:none"><strong style="visibility:hidden;">헬프데스크 :</strong> ☎ 02-2624-8413(후불전용)</li>
               </ul>
            </td>
         </tr>
         <tr>
            <th scope="row">홈페이지 URL</th>
            <td class="pc_r">
               <strong>홈페이지 URL :</strong><a href="https://www.ktmmobile.com" target="_blank"> https://www.ktmmobile.com</a>
               <br />
               <strong>모바일 홈페이지 URL :</strong><a href="https://www.ktmmobile.com/m/main.do" target="_blank"> https://www.ktmmobile.com/m/main.do</a>
            </td>
         </tr>
      </tbody>
   </table>
</div>

<script type="text/javascript">
    var oCount = 0;
    var oNumber = '';
    var oTimer;
    var passChange = false;
    var isOtp = "<%=request.getAttribute("otpYn")%>" == "Y" ? true : false ;
    var otpUse = "<%=request.getAttribute("otpUse")%>" == "Y" ? true : false ;
    var isMac = "<%=request.getAttribute("macYn")%>" == "Y" ? true : false ;
    var macUse = "<%=request.getAttribute("macUse")%>" == "Y" ? true : false ;
    var scanUrl = "<%=request.getAttribute("scanUrl")%>";
    var downUrl = "<%=request.getAttribute("downUrl")%>";

    $(function() {
        // 항상 로그인화면은 Top Window 에서 실행되도록!
        if ( top.location.href != self.location.href) {
            top.location.href = encodeURI(self.location.href);
            return;
        }

        // 한글입력방지를 위해서는 keypress 가 아니라 keydown 에서 막아야!!
        // 크롬등에서 229 (IE 는 ime-mode:disabled; 로 막음) (완벽하지 않고 보조수단으로 활용)
        $("#usrId").on("keydown", function(e) {
            var ch = e.keyCode ? e.keyCode : e.which;
            if (ch == 229) return false;

            return true;
        });

        // 실제 영문,숫자 입력체크는 keypress 에서
        $("#usrId").on("keypress", function(e) {
            var ch = e.keyCode ? e.keyCode : e.which;
            if (ch >= 48 && ch <= 57) return true;    // 숫자 (0:48, ... 9:57)
            if (ch >= 65 && ch <= 90) return true;    // 대문자 (A:65, ... Z:90)
            if (ch >= 97 && ch <= 122) return true;    // 소문자 (A:97, ... Z:122)
            if (ch == 13) {
            	if (isMac && macUse) {
            		fnMac();	
            	} else {
            		if (isOtp && otpUse) {
                        fnOtp();
                    } else {
                        fnLogin();
                    }
            	}
            	
                return false;  // #none 안나오도록
            }

            return false;
        });

        $("#pass").on("keypress", function(e) {
            var ch = e.keyCode ? e.keyCode : e.which;
            if (ch == 13) {      	
            	if (isMac && macUse) {
            		fnMac();	
            	} else {
            		if (isOtp && otpUse) {
                        fnOtp();
                    } else {
                        fnLogin();
                    }
            	}
            	
                return false;  // #none 안나오도록
            }
        });

        $("#otp").on("keypress", function(e) {
            var ch = e.keyCode ? e.keyCode : e.which;
            if (ch >= 48 && ch <= 57) return true;    // 숫자 (0:48, ... 9:57)
            if ($('#otp').css('display') != "none" && ch == 13) {
                fnLogin();

                return true;
            }

            return false;
        });

        //------------------------------------------------------
        // 로그인 처리
        //------------------------------------------------------

        $("#login").on("click", function() {
        	if (isMac && macUse) {
        		fnMac();	
        	} else {
        		if (isOtp && otpUse) {
                    fnOtp();
                } else {
                    fnLogin();
                }
        	}
        	
            return false;  // #none 안나오도록
        });
    });

    //------------------------------------------------------------
    // 실제 로그인 처리 - 향후 Ajax 방식으로 변경 검토!
    //------------------------------------------------------------
    function fnLogin() {
        ///* eunjung AJAX 방식으로 변경
        var f = document.form1;

        if( f.usrId.value == "" ) {
            mvno.ui.alert("아이디를 입력해주세요.");
            f.usrId.focus();

            return ;
        }

        if( f.pass.value == "" ) {
            mvno.ui.alert("비밀번호를 입력해주세요.");
            f.pass.focus();

            return ;
        }
        
        if( isOtp && otpUse && f.otp.value == "" ) {
            mvno.ui.alert("OTP 인증번호를 입력해주세요.");
            f.otp.focus();

            return ;
        }

        if(!$("#agree").is(":checked")) {
            mvno.ui.alert("고객정보 개인용도 사용금지에 동의해주세요.");
            $("#agree").focus();

            return ;
        }

        f.usrId.value = f.usrId.value.toUpperCase();    // 실제 데이터는 대문자화해서 보내야!!

        mvno.cmn.ajax2(ROOT + "/cmn/login/loginProc.json", {usrId:f.usrId.value, pass:f.pass.value, otp:f.otp.value}, function(resultData) {
            if(mvno.cmn.isEmpty(resultData) || ! resultData.result || ! resultData.result.code) {
                alert("로그인에 실패하였습니다.\n다시 시도해주세요.");
                top.location.href = ROOT + "/cmn/login/loginForm.do";
            } else {
                if(resultData.result.code == "OK") {
                    if(resultData.result.passChange == "Y") {
                        top.location.href = ROOT + "/main.do?passChange=Y";
                    } else {
                        top.location.href = ROOT + "/main.do";
                    }
                } else {
                	if(resultData.result.type == "OTP") {
                		mvno.ui.alert(resultData.result.msg);
                        f.otp.value = "";

                		return ;
                	} else if (resultData.result.type == "OTPINIT") {
                		alert(resultData.result.msg);
                        top.location.href = ROOT + "/cmn/login/loginForm.do";
                	} else if (resultData.result.type == "OLDPASS" || resultData.result.type == "FIRST") {
                		alert(resultData.result.msg);

                		clearInterval(oTimer);

                		$('#pass').val("");
                		$('#otp').val("");

                        otpEventOn(false);

                		mvno.ui.popupWindow(ROOT + "/cmn/login/passChgPopup.do?usrId="+$('#usrId').val(), "사용자정보수정", '350', '280');
                	} else if(resultData.result.type == "STATUSREQ"){
                      	mvno.ui.confirm("정지된 사용자입니다. 관리자에게 정지해제 요청을 하시겠습니까?", function(){
                      		mvno.cmn.ajax2(ROOT + "/cmn/login/cpntStatusReq.json", {usrId:f.usrId.value, pass:f.pass.value, otp:f.otp.value}, function(resultData){
                      			if(resultData.result.code == "OK"){
                      				mvno.ui.alert("정지해제 요청이 완료되었습니다.");
                      			}else{
                      				alert(resultData.result.msg);
                      			}
                      		});
                    	});
                	} else if(resultData.result.type == "ING") {
                        mvno.ui.confirm(resultData.result.msg, function(){
                           mvno.cmn.ajax2(ROOT + "/cmn/login/disconnectCurrentLogin.json", {usrId:f.usrId.value}, function(rsltData) {
                              mvno.ui.alert(rsltData.result.msg);
                           });
                        });
                    } else {
                		alert(resultData.result.msg);
                        top.location.href = ROOT + "/cmn/login/loginForm.do";
                	}
                    
                }
            }
        }, { resultTypeCheck: false });

        
    }

    function fnOtp() {
        var f = document.form1;

        if( f.usrId.value == "" ) {
            mvno.ui.alert("아이디를 입력해주세요.");
            f.usrId.focus();

            return ;
        }

        if( f.pass.value == "" ) {
            mvno.ui.alert("비밀번호를 입력해주세요.");
            f.pass.focus();

            return ;
        }

        f.usrId.value = f.usrId.value.toUpperCase();    // 실제 데이터는 대문자화해서 보내야!!

        mvno.cmn.ajax(ROOT + "/cmn/login/otpProc.json", {usrId:f.usrId.value, pass:f.pass.value}, function(resultData) {
            if(mvno.cmn.isEmpty(resultData) || ! resultData.result || ! resultData.result.code) {
                mvno.ui.alert("로그인에 실패하였습니다.<br>다시 시도해주세요.");

                return;
            } else {
            	if  (resultData.result.code == "STATUSREQ") {
            		mvno.ui.confirm("정지된 사용자입니다. 관리자에게 정지해제 요청을 하시겠습니까?", function(){
                  		mvno.cmn.ajax2(ROOT + "/cmn/login/cpntStatusReq.json", {usrId:f.usrId.value, pass:f.pass.value, otp:f.otp.value}, function(resultData){
                  			if(resultData.result.code == "OK"){
                  				mvno.ui.alert("정지해제 요청이 완료되었습니다.");
                  			}else{
                  				alert(resultData.result.msg);
                  			}
                  		});
                	});
            	}else if (resultData.result.code == "1TYMOK" || resultData.result.code == "OTPPASS") {
                	mvno.cmn.ajax2(ROOT + "/cmn/login/loginProc.json", {usrId:f.usrId.value, pass:f.pass.value, otp:f.otp.value}, function(resultData) {
                        if(mvno.cmn.isEmpty(resultData) || ! resultData.result || ! resultData.result.code) {
                            alert("로그인에 실패하였습니다.\n다시 시도해주세요.");
                            top.location.href = ROOT + "/cmn/login/loginForm.do";
                        } else {
                            if(resultData.result.code == "OK") {
                                if(resultData.result.passChange == "Y") {
                                    top.location.href = ROOT + "/main.do?passChange=Y";
                                } else {
                                    top.location.href = ROOT + "/main.do";
                                }
                            } else {
                            	if (resultData.result.type == "OLDPASS" || resultData.result.type == "FIRST") {
                            		alert(resultData.result.msg);
	
                            		$('#pass').val("");
                            	                                    
                            		mvno.ui.popupWindow(ROOT + "/cmn/login/passChgPopup.do?usrId="+$('#usrId').val(), "사용자정보수정", '350', '280');
                            	} else if(resultData.result.type == "ING") {
                                   mvno.ui.confirm(resultData.result.msg, function(){
                                      mvno.cmn.ajax2(ROOT + "/cmn/login/disconnectCurrentLogin.json", {usrId:f.usrId.value}, function(rsltData) {
                                         mvno.ui.alert(rsltData.result.msg);
                                      });
                                   });
                                } else {
                            		alert(resultData.result.msg);
                                    top.location.href = ROOT + "/cmn/login/loginForm.do";
                            	}                                
                            }
                        }
                    }, { resultTypeCheck: false });
                } else if(resultData.result.code == "OK") {
                	startSmsTimer();
                } else {
                	f.usrId.value = "";
                    f.pass.value = "";
                	mvno.ui.alert(resultData.result.msg);
                	
                    return;
                }
            }
        }, { resultTypeCheck: false });
    }
    
    function fnMac() {
    	var f = document.form1;
    	
    	if( f.usrId.value == "" ) {
            mvno.ui.alert("아이디를 입력해주세요.");
            f.usrId.focus();

            return ;
        }

        if( f.pass.value == "" ) {
            mvno.ui.alert("비밀번호를 입력해주세요.");
            f.pass.focus();

            return ;
        }

        f.usrId.value = f.usrId.value.toUpperCase();    // 실제 데이터는 대문자화해서 보내야!!

        mvno.cmn.ajax(ROOT + "/cmn/login/macProc.json", {usrId:f.usrId.value, pass:f.pass.value}, function(resultData) {
            if(mvno.cmn.isEmpty(resultData) || ! resultData.result || ! resultData.result.code) {
                mvno.ui.alert("로그인에 실패하였습니다.<br>다시 시도해주세요.");

                return;
            } else {
                if(resultData.result.code == "OK") {
                	if(resultData.result.targetYn == "Y") {
                		callViewer(resultData.result);	
                	} else {
                		if (isOtp && otpUse) {
                            fnOtp();
                        } else {
                            fnLogin();
                        }
                	}
                	
                } else {
                	mvno.ui.alert(resultData.result.msg);
                    return;
                }
            }
        }, { resultTypeCheck: false });
    	
    }

    function startSmsTimer() {
        mvno.ui.alert("OTP 인증 번호가 발송 되었습니다. 3분 내로 인증번호를 입력해 주세요.");

        otpEventOn(true);

        clearInterval(oTimer);

        var waitMax = 180;

        oTimer = setInterval(function() {
            if(waitMax < 0) {
                clearInterval(oTimer);
                oNumber = '';

                document.form1.usrId.value = "";
                document.form1.pass.value = "";
                document.form1.otp.value = "";

                otpEventOn(false);

                mvno.ui.alert("인증번호 입력 시간이 초과하였습니다.<br>처음부터 다시 진행해 주시기 바랍니다.");
            }

            $('#wTimer').html('('+waitMax+'초)');
            waitMax --;
        }, 1000);
    }

    function otpEventOn(type) {
        if (type) {
            $('#pLogin').hide();
            $('#aLogin').show();
            $('#usrId').attr('disabled', true);
            $('#pass').attr('disabled', true);
            $('#otp').css('display','');
            $('#btnOtp').css('display','');
            $('#wTimer').css('display','');
        } else {
            $('#pLogin').show();
            $('#aLogin').hide();
            $('#usrId').attr('disabled', false);
            $('#pass').attr('disabled', false);
            $('#otp').css('display','none');
            $('#btnOtp').css('display','none');
            $('#wTimer').css('display','none');
        }
    }
    
    function callMemAdd(){
    	mvno.ui.popupWindow(ROOT + "/cmn/login/userApprPopup.do", "대리점계정신청", '810', '350');
    }
    
    function callViewer(macInfo) {
    	$("#LOADING").show();
    	$('#pLogin').hide();
        $('#aLogin').show();
    	
        var timeOutTimer = window.setTimeout(function (){
        	$("#LOADING").hide();
        	$('#pLogin').show();
            $('#aLogin').hide();
            
        	mvno.ui.confirm("이미지 프로그램이 설치되지 않았거나 실행중이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
      			location.replace(downUrl);         // 설치페이지로 이동
      		});
        }, macInfo.delayTime);

    	$.ajax({
    		type:"GET",  
	      	url:scanUrl+"?AGENT_VERSION="+macInfo.agentVersion+"&SERVER_URL="+macInfo.serverUrl+"&VIEW_TYPE="+macInfo.viewType+"&RGST_PRSN_ID="+macInfo.rgstPrsnId,
	      	crossDomain: true,
	      	dataType:'jsonp',
	      	success:function(args){
	      		$("#LOADING").hide();
	        	$('#pLogin').show();
	            $('#aLogin').hide();
	      		
	      		window.clearTimeout(timeOutTimer);
	      		
	      		if(args.RESULT == "SUCCESS") {
	      			if (isOtp && otpUse) {
                        fnOtp();
                    } else {
                        fnLogin();
                    }	
	      		} else if(args.RESULT == "ERROR_TYPE2") {
	      			mvno.ui.confirm("최신 이미지 프로그램이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
    	      			location.replace(downUrl);         // 설치페이지로 이동
    	      		});
	      		} else if(args.RESULT == "ERROR_TYPE3") {
	      			mvno.ui.alert("등록된 MAC정보와 일치하지 않습니다.");
	      		} else {
	      			alert(args.RESULT + " : " + args.RESULT_MSG);
	      			mvno.ui.alert("["+args.RESULT+"] MAC인증실패 관리자에게 문의하세요.");
	      		}
	      	}
    	});
    	
    }

    function fnPwdReset(){
    	mvno.ui.popupWindow(ROOT + "/cmn/login/usrPwdResetPopup.do", "패스워드 초기화", '350', '230');
	}
</script>
