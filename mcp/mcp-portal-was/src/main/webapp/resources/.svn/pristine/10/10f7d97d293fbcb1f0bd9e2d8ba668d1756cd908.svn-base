
$(document).ready(function (){
  arcProgress();
}); // end of document.ready --------------------

document.addEventListener('DOMContentLoaded', function() {
  arcProgress();
});

/** 실시간 이용량 UI 세팅 */
var arcProgress = function(){
  var progressBars = document.querySelectorAll('.c-indicator-arc');
  if(progressBars.length <= 0) return;

  progressBars.forEach(function(arcProgress){
    var bar = arcProgress.querySelector('.arc');
    var val = bar.dataset.percent;
    var perc = parseInt(val, 10);

    var agent = navigator.userAgent.toLowerCase();
    if((navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || agent.indexOf('msie') != -1){
      bar.style['msTransform'] = 'rotate(' + (45 + perc * 1.8) + 'deg)';  // ie일 경우
    }else{
      bar.style['WebkitTransform'] = 'rotate(' + (45 + perc * 1.8) + 'deg)'; // ie가 아닐 경우
    }

    //알맞은 percent 값을 계산하여 해당 정보에 뿌려주세요.
    function showData() {}
  });
}

/** 조회 날짜 변경 */
var btn_search = function(){

  ajaxCommon.createForm({
      method:"post"
    ,action:"/personal/callView01.do"
  });

  ajaxCommon.attachHiddenElement("useMonth", $("#datalist option:selected").val());
  ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
  ajaxCommon.formSubmit();
}

/** 회선 조회 */
var select = function(){

  ajaxCommon.createForm({
     method:"post"
    ,action:"/personal/callView01.do"
  });

  ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
  ajaxCommon.formSubmit();
}