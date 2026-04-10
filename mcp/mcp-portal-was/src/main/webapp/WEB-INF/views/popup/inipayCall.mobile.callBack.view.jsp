<%@ page language = "java" contentType = "text/html;charset=UTF-8" %>
<html>
    <head>
        <title>kt M모바일 웹 어드민 시스템</title>
        <meta name="title" content="kt M모바일" />
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" >
        $(document).ready(function(){

            var rtnObj = {
            		pReqUrl:""
                    ,pStatus:""
                    ,pRmesg1:""
                    ,pTid:""
                    ,pNoti:""
            } 

            rtnObj.pReqUrl = $("#pReqUrl").val();
            rtnObj.pStatus = $("#pStatus").val();
            rtnObj.pRmesg1 = $("#pRmesg1").val();
            rtnObj.pTid = $("#pTid").val();
            rtnObj.pNoti = $("#pNoti").val();

            if (rtnObj.pTid != "") { 
                opener.setInipayResult(rtnObj) ;
            } else {
                alert("결제에 실패 하였습니다.");
            }
            self.close(); 
        })
        </script>
        
    </head>
    <body>
    
        
        
	    <input type="hidden" name="pReqUrl" id="pReqUrl"   value="${iniDto.pReqUrl}" />
	    <input type="hidden" name="pStatus" id="pStatus" value="${iniDto.pStatus}" />
	    <input type="hidden" name="pRmesg1" id="pRmesg1" value="${iniDto.pRmesg1}" />
	    <input type="hidden" name="pTid"  id="pTid" value="${iniDto.pTid}" />
	    <input type="hidden" name="pNoti"  id="pNoti" value="${iniDto.pNoti}"  />
	    <input type="hidden" name="oid"  id="oid" value="${iniDto.oid}"  />
	    <input type="hidden" name="price"  id="price" value="${iniDto.price}"  />
    </body>
</html>