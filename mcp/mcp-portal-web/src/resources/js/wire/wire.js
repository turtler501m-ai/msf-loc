
var menuVer = new Date().getTime();

//document.write('<script type="text/javascript" src="/resources/upload/js/dataWireMenuConfig.js?version=' + menuVer + '" charset="utf-8"></script>');
document.write('<script type="text/javascript" src="/resources/js/wire/dataWireMenuConfig.js?version=' + menuVer + '" charset="utf-8"></script>');

$(function(){
    var $gnbEl = $("#topMenu");
    var $totalEl = $("#topTotalMenu");
    var $topTotalMenuList = $("#topTotalMenuList");

    var gnbMenuCode = "" ;
    var subMenuCode = "";
    if ( typeof menuCode != "undefined" && menuCode.length >= 6)  {
        subMenuCode = menuCode.substring(0, 6) ;
    }

    if ( typeof menuCode != "undefined" && menuCode.length >= 4)  {
        gnbMenuCode = menuCode.substring(0, 4) ;
    }

    var wireGnb= function(){
          $(".wireLogo").on("focus", "a", function(){
            $gnbEl.find(".ttSubDepth").hide();
          });

          $(".wireTotalMenu").on("focus", "a", function(){
            $gnbEl.find(".subDepth").hide();
          });

          $gnbEl.on("mouseover", "> li", function(){
            $(this).find(".subDepth").show();
          });

          $gnbEl.on("mouseleave", "> li", function(){
            $(this).find(".subDepth").hide();
          });

          $gnbEl.on("focus", "> li > a", function(){
            $gnbEl.find(".subDepth").hide();
            $(this).next(".subDepth").show();
          });

          /* 전체메뉴 */
          $totalEl.on("click", ".btnAll", function(e){
            e.preventDefault();

            if($(this).hasClass("on")){
              $(this).removeClass("on").text("전체메뉴 열기").next(".wireTotalList").hide();
            }else{
              $(this).addClass("on").text("전체메뉴 닫기").next(".wireTotalList").show();
            }
          });

          $totalEl.on("click", ".btnTotalX", function(e){
            e.preventDefault();
            $(this).closest(".wireTotalMenu").find(".wireTotalList").hide().prev(".btnAll").removeClass("on").text("전체메뉴 열기").focus();
          });
     };




    var intSubMenuCnt =0;
    //GNB메뉴
    var htmlMenu = [];
    //전체 메뉴
    var totalMenuHtml = [];
    for (var i = 0; i < WR_GNB_MENU_LIST.length; i++) {
        var gnbOnClass = "";
        if (WR_GNB_MENU_LIST[i].menuCode == gnbMenuCode) {
            gnbOnClass = "on";
        }

        htmlMenu.push("<li>");
        htmlMenu.push("    <a href='"+WR_GNB_MENU_LIST[i].urlAdr+"'  class='"+gnbOnClass+"' >"+WR_GNB_MENU_LIST[i].menuNm+"</a>");
        htmlMenu.push("    <div class='subDepth'>");
        htmlMenu.push("        <ul class='subDepthList'>");

        totalMenuHtml.push("<ul class='categoryDepth'>");
        totalMenuHtml.push("<li>");
        totalMenuHtml.push("	<a href='"+WR_GNB_MENU_LIST[i].urlAdr+"'>"+WR_GNB_MENU_LIST[i].menuNm+"</a>");
        totalMenuHtml.push("	<ul class='ttSubDepth'>");


        //2depth
        var tempMenuList = WR_SECOND_MENU_OJB[WR_GNB_MENU_LIST[i].menuCode] ;
        for(var j=0; j< tempMenuList.length; j++) {
            var subOnClass = "";
            intSubMenuCnt++;
            if (tempMenuList[j].menuCode == subMenuCode) {
                subOnClass = "on";
            }
            htmlMenu.push("<li><a href='"+tempMenuList[j].urlAdr+"' class='"+ subOnClass +"'>"+tempMenuList[j].menuNm+"</a></li>");

            totalMenuHtml.push("		<li><a href='"+tempMenuList[j].urlAdr+"'>"+tempMenuList[j].menuNm+"</a></li>");

        }

        htmlMenu.push("        </ul>");
        htmlMenu.push("    </div>");
        htmlMenu.push("</li>");


        totalMenuHtml.push("	</ul>");
        totalMenuHtml.push("</li>");
        totalMenuHtml.push("</ul>");

    }

     $gnbEl.html(htmlMenu.join(''));
     $topTotalMenuList.append(totalMenuHtml.join(''));

     if (intSubMenuCnt > 0) {
         wireGnb();
     }


});


