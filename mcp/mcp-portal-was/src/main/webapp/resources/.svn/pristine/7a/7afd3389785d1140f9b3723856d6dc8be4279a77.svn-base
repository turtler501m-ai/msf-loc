
/** 개인화 url 본인인증 결과 확인 */
var personalAuthCallback = function(pageType, ncn){

  var authResult = {};

  var varData = ajaxCommon.getSerializedData({
     pageType: pageType
    ,ncn: ncn
    ,reqSeq: pageObj.niceResLogObj.reqSeq
    ,resSeq: pageObj.niceResLogObj.resSeq
    ,paramR3: pageObj.niceType
  });

  ajaxCommon.getItemNoLoading({
     id: 'certAuthAjax'
    ,cache: false
    ,async: false
    ,url: '/personal/certAuthAjax.do'
    ,data: varData
    ,dataType: "json"
  },function(data){
    authResult = data;
  });

  return authResult;
}