<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : fileDownLoadInfo.jsp
	 * @Description : 파일 다운로드 정보 입력 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.09.23     임지혜           최초 생성
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
    			width : "600px"
    	
    		},
			
			items : [
			    {type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
				
				,{type:"block", list:[ 
					 {type:"input", label:"파일명", width:500, name:"FILE_NM", value:"${requestParamMap.fileNamePc}", disabled:true }
					,{type:"input", label:"다운로드사유", rows:5, width:500, name:"DWNLD_RSN", required:true, validate: "NotEmpty", maxLength:250 }
					
					,{type:"block", list:[
					                      
						,{type:"button", value:"다운받기", offsetLeft:450, name:"btnDown" }
						,{ type : "newcolumn" }, 
						,{type:"button", value:"닫기", offsetLeft:20, name:"btnClose" }
					
					]},
					
				]}
			],
			
            
            onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnDown":
						
						var url = "/cmn/filedown/fileDown.do?"
						+ "fileNamePc=${requestParamMap.fileNamePc}"  
						+ "&filePath=${requestParamMap.filePath}"
						+ "&fileName=${requestParamMap.fileName}"
						+ "&writeFileUpDownLog=${requestParamMap.writeFileUpDownLog}"
						+ "&menuId=${requestParamMap.menuId}"
						; 
//						mvno.cmn.ajax(ROOT + url, form, function(result) {
//                            mvno.ui.closeWindowById(form, true);  
//						 });
						
						form.send(url);
						
						break;	
						
					case "btnClose":
						
						mvno.ui.closeWindowById(form);
						break;
				}
			},
			
            onValidateMore: function(data) {
				  validateMaxLength(this);
            }
				
		}    		
	};

	var PAGE = {
			
		title: "파일다운로드",
//		breadcrumb: "Home > 공통 > 파일다운로드 ",
	
	    onOpen : function() {
	        
	        mvno.ui.createForm("FORM1");
	        
	        }
	};

    </script>
