
	    $(document).ready(function() {
			$(document).on("change", 'input:radio[name=secedeReason]', function(){
				if($('input:radio[name=secedeReason]:checked').val() == '08'){
					$("#reasonDesc").show();
				}else{
					$("#reasonDesc").hide();
					$("#secedeReasonDesc").val('');
				}
			});
			
	    	 $(".c-button--primary").click(function(){
	    		 	if(!$('#pw').val()) {
	    			    alert("비밀번호를 입력해 주세요.");
	    			    return false;
	    			}

		    		if(!$('input:radio[name=secedeReason]:checked').val()) {
	    			    alert("탈퇴사유를 선택해 주세요.");
	    			    return false;
	    			}

		    		if($('input:radio[name=secedeReason]:checked').val()=="08" && !$('input[name=secedeReasonDesc]').val()) {
	    			    alert("탈퇴사유를 입력해 주세요.");
	    			    $('input[name=secedeReasonDesc]').focus();
	    			    return false;
	    			}

			    	if(confirm("탈퇴 하시겠습니까? \n탈퇴 후 같은 ID로는 가입이 불가능합니다.")) {
						var varData = ajaxCommon.getSerializedData({
				            userId : $("#userId").val(),
				            pw : $("#pw").val(),
				            secedeReason : $('input:radio[name=secedeReason]:checked').val(),
				            secedeReasonDesc : $("#secedeReasonDesc").val()
						});
				
						ajaxCommon.getItem({
			                type : "POST",
			                url : "/mypage/deleteUserAjax.do",
			                data : varData,
			                dataType : "json",
			                contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			                async : false,
			                beforeSend : function(){
			        		}
			        		},
			                function(data) {
			                   	if(data.returnCode == "00") {
			                   		alert("정상적으로 탈퇴가 완료되었습니다.");
			                   		location.href = "/logOutProcess.do";
			                /*   		$("#frm").attr("action", "/logOutProcess.do");
			                   		$("#frm").submit();*/
			                   	} else{
			                   		alert(data.message);
			                   	}
								return;
			                });
				   		/*	error : function() {
				 				alert("오류가 발생했습니다. 다시 시도해 주세요.");
				 				return;
				 			}*/

			           }
			
			    });

	    });