<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : fileDownCause.jsp
	 * @Description : 파일 다운로드 사유 입력 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.11.04 고은정           최초 생성
	 * @
	 */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

    <!-- 화면 배치 -->
	<div id="FORM1" class="section-box" ></div>
	
    <!-- Script -->
    <script type="text/javascript">
    
    var DHX = {
    	
		FORM1 : {
    		css : {
    			width : "430px"
    	
    		},
			
			items : [
			    {type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
				,{type:"block", list:[ 
					,{type:"input", label:"다운로드사유", rows:5, width:300, name:"DWNLD_RSN", required:true, validate: "NotEmpty", maxLength:250 }
				]}
			],
			buttons : {
                center : {
                	btnApply : { label : "확인" },
                    btnClose : { label : "닫기" }
                }
            },
            
            onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnApply":
						if(!form.getItemValue("DWNLD_RSN")){
		                      mvno.ui.alert("다운로드 사유를 입력하세요.");
		                      break;
                        }
						
				        if(PAGE.param.data != null && PAGE.param.data.url != ""){
				            				        	
				            mvno.cmn.download(PAGE.param.data.url + "&DWNLD_RSN="+PAGE.FORM1.getItemValue("DWNLD_RSN"));
				        }
						break;	
						
					case "btnClose":
						
						mvno.ui.closeWindowById(form);
						break;
				}
			}
			
		}    		
	};

	var PAGE = {
			
		title: "파일다운로드",
	
	    onOpen : function() {
	        
	        mvno.ui.createForm("FORM1");
	        
        }
	};
	
    </script>
