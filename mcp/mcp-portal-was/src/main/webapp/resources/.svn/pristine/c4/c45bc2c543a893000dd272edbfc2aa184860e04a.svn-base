$(document).ready(function (){

 	arcProgress();

 	$(".link").click(function(){
		KTM.LoadingSpinner.show();
		var url = $(this).attr("url");
		ajaxCommon.createForm({
		    method : "post"
		    ,action : url
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});
});

function arcProgress() {
  var progressBars = document.querySelectorAll('.c-indicator-arc');
  if (!!progressBars) {
    progressBars.forEach(function(arcProgress) {
      var bar = arcProgress.querySelector('.arc');
      var val = bar.dataset.percent;
      var perc = parseInt(val, 10);
      var bar_text = arcProgress.querySelector('.indicator-text');
      //ie 11 경우 체크
      var agent = navigator.userAgent.toLowerCase();
      if ((navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || agent.indexOf('msie') != -1) {
        // ie일 경우
        bar.style['msTransform'] = 'rotate(' + (45 + perc * 1.8) + 'deg)';
      } else {
        // ie가 아닐 경우
        bar.style['WebkitTransform'] = 'rotate(' + (45 + perc * 1.8) + 'deg)';
      }

      function showData() {
        //알맞은 percent 값을 계산하여 해당 정보에 뿌려주세요.
      }
    });
  }
}

document.addEventListener('DOMContentLoaded', function() {
  arcProgress();
});

function select(){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/mypage/callView01.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}


function btn_search(){
	ajaxCommon.createForm({
		method:"post"
		,action:"/mypage/callView01.do"
	});

	ajaxCommon.attachHiddenElement("useMonth", $("#datalist option:selected").val());
	ajaxCommon.attachHiddenElement("ncn", $("#ncn option:selected").val());
	ajaxCommon.formSubmit();
}