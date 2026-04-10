
var aiRecommendOpen;

/** AI 요금제 추천 팝업 오픈 */
function aiRecommendPopOpen(){

  // AILandingUrl > 각 레이아웃에 존재
  const landing = $("#AILandingUrl").val();
  if(!landing) return;

  const aiWidth = 1100;
  const aiHeight = Math.floor(window.innerHeight * 0.8);

  const screenLeft = window.screenX ?? window.screenLeft;
  const screenTop = window.screenY ?? window.screenTop;

  const outerWidth = window.outerWidth;
  const outerHeight = window.outerHeight;

  const aiLeft = screenLeft + (outerWidth - aiWidth) / 2;
  const aiTop = Math.max(screenTop + (outerHeight - aiHeight) / 2 - 50, 0);

  aiRecommendPopAction(landing, aiTop, aiLeft, aiWidth, aiHeight);
}

function aiRecommendPopAction(landing, aiTop, aiLeft, aiWidth, aiHeight){

  if(aiRecommendOpen == null || aiRecommendOpen.closed){
    aiRecommendOpen = window.open("", "aiRecommendPop", `status=no,menubar=no,scrollbars=yes,resizable=yes,top=${aiTop},left=${aiLeft},width=${aiWidth},height=${aiHeight}`);

    try{
      if(aiRecommendOpen.location.host == ""){
        aiRecommendOpen.location.href = landing;
      }
    }catch(e){
      aiRecommendOpen = window.open(landing, "aiRecommendPop", `status=no,menubar=no,scrollbars=yes,resizable=yes,top=${aiTop},left=${aiLeft},width=${aiWidth},height=${aiHeight}`);
    }
  }

  aiRecommendOpen.focus();
}