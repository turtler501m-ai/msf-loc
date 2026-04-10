/**
 * 
 */
  var selfTimeChk = function(){
	var now = new Date();
	var nowHH = now.getHours();
	 
	if(nowHH == 9 || (nowHH >= 19 && nowHH <=20)){
		$('#movBtn').show();
		$('#closeBtn').hide();
		$('.self-opening__text').show();
		$('.self-opening__title').text('신규 가입만 가능한 시간입니다.');
		$('#selfBtn').trigger('click');
		
	}else if(nowHH >= 10 && nowHH <= 18){
		var store = $(".c-page--title").text();
		if(store.indexOf("이마트") != -1){
			location.href = '/appForm/appFormDesignView.do?orgnId=V000019218';	
		}else if(store.indexOf("CU") != -1){
			location.href = '/appForm/appFormDesignView.do?orgnId=V000019329';
		}else if(store.indexOf("씨스페이스") != -1){
			location.href = '/appForm/appFormDesignView.do?orgnId=NZA0000240';
		}else if(store.indexOf("미니스톱") != -1){
			location.href = '/appForm/appFormDesignView.do?orgnId=V000001444';
		}else if(store.indexOf("세븐일레븐") != -1){
			location.href = '/appForm/appFormDesignView.do?orgnId=V000017488';
		}else if(store.indexOf("GS") != -1){
			location.href = '/appForm/appFormDesignView.do?orgnId=V000014270';
		}else if(store.indexOf("스토리") != -1){
			location.href = '/appForm/appFormDesignView.do?orgnId=V000019173';
		}
	}else{
		$('#movBtn').hide();
		$('#closeBtn').show();
		$('.self-opening__text').hide();
		$('.self-opening__title').text('셀프개통이 불가능한 시간입니다.');
		$('#selfBtn').trigger('click');
	}
}
 
 
 