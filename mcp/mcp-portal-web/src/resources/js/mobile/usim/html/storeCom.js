/**
 * 
 */
 var selfTimeChk = function(){
	var now = new Date();
	var nowHH = now.getHours();
	 
	if(nowHH == 9 || (nowHH >= 19 && nowHH <=20)){
		MCP.alert('신규 가입만 가능한 시간입니다.', function (){
			var store = $(".ly-page__head").text();
			if(store.indexOf("emart") != -1){
				location.href = '/m/appForm/appFormDesignView.do?orgnId=V000019218';	
			}else if(store.indexOf("CU") != -1){
				location.href = '/m/appForm/appFormDesignView.do?orgnId=V000019329';
			}else if(store.indexOf("씨스페이스") != -1){
				location.href = '/m/appForm/appFormDesignView.do?orgnId=NZA0000240';
			}else if(store.indexOf("미니스톱") != -1){
				location.href = '/m/appForm/appFormDesignView.do?orgnId=V000001444';
			}else if(store.indexOf("세븐일레븐") != -1){
				location.href = '/m/appForm/appFormDesignView.do?orgnId=V000017488';
			}else if(store.indexOf("GS") != -1){
				location.href = '/m/appForm/appFormDesignView.do?orgnId=V000014270';
			}else if(store.indexOf("스토리") != -1){
				location.href = '/m/appForm/appFormDesignView.do?orgnId=V000019173';
			}
		});
		
	}else if(nowHH >= 10 && nowHH <= 18){
		var store = $(".ly-page__head").text();
		if(store.indexOf("emart") != -1){
			location.href = '/m/appForm/appFormDesignView.do?orgnId=V000019218';	
		}else if(store.indexOf("CU") != -1){
			location.href = '/m/appForm/appFormDesignView.do?orgnId=V000019329';
		}else if(store.indexOf("씨스페이스") != -1){
			location.href = '/m/appForm/appFormDesignView.do?orgnId=NZA0000240';
		}else if(store.indexOf("미니스톱") != -1){
			location.href = '/m/appForm/appFormDesignView.do?orgnId=V000001444';
		}else if(store.indexOf("세븐일레븐") != -1){
			location.href = '/m/appForm/appFormDesignView.do?orgnId=V000017488';
		}else if(store.indexOf("GS") != -1){
			location.href = '/m/appForm/appFormDesignView.do?orgnId=V000014270';
		}else if(store.indexOf("스토리") != -1){
			location.href = '/m/appForm/appFormDesignView.do?orgnId=V000019173';
		}
	}else{
		MCP.alert('셀프개통이 불가능한 시간입니다.');
	}
}

