<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="imgpath" value="${pageContext.request.contextPath}/resources/images"/>
<t:insertDefinition name="layoutCompanyDefault">

<t:putAttribute name="scriptHeaderAttr">
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script type="text/javascript">
var menuCode= "SM0802";

$(document).ready(function () {

    //$('input, text').placeholder();

    $(document).on("click", "#goListBtn", function(){
        $("form[name='frm']").attr("action", "/mmobile/newsDataList.do");
        $("form[name='frm']").submit();
    });

});

var goNewsDetail = function(newsDatSeq) {	//이전 다음글로 상세페이지 열때 사용하는 function
    $("input[name='pageNo']").val();
    $("#newsDatSeq").val(newsDatSeq);
    $("form[name='frm']").attr("action", "/mmobile/newsDetail.do");
    $("form[name='frm']").submit();
}

//SNS 등 sharer 처리

function unescapeXml(str){
    str = str.replaceAll("&amp;", "&");
    str = str.replaceAll("&lt;", "<");
    str = str.replaceAll("&gt;", ">");
    str = str.replaceAll("&apos;", "'");
    str = str.replaceAll("&quot;", "\"");
    str = str.replaceAll("&#039;", "'");
    return str;
}

function facebook_share(){
    var url = "https://www.ktmmobile.com/mmobile/newsDetail.do?newsDatSeq=${newsDataBasDto.newsDatSeq}&notiYn=${newsDataBasDto.notiYn}";
    var image = "https://www.ktmmobile.com${newsDto.newsDatImg}";
    var snsUrl = "https://www.facebook.com/sharer/sharer.php?u="+encodeURIComponent(url)

    var popup= window.open(snsUrl, "_snsPopupWindow", "width=500, height=500, menubar=yes, status=yes, toolbar=yes, resizable=yes");
    popup.focus();
}

function twitter_share(){
    var text = `${newsDto.newsDatSubject}`;
        text = encodeURIComponent(text);
    var url = `https://www.ktmmobile.com/mmobile/newsDetail.do?newsDatSeq=${newsDataBasDto.newsDatSeq}&notiYn=${newsDataBasDto.notiYn}`;
    var snsUrl = "https://twitter.com/intent/tweet?text="+text+"&url="+encodeURIComponent(url);
    var popup = window.open(snsUrl, "_snsPopupWindow", "width=500, height=500, menubar=yes, status=yes, toolbar=yes, resizable=yes");
    popup.focus();
}

Kakao.init('1a4c3982100206d74948a22ec2bb85d8');
function kakaostory_share(){

//	var title = '${newsDto.newsDatSubject}';
//    title = title.substring(0,100);
//    title = unescapeXml(title);
//    title = title.replace(/\&(amp;)?/ig, ""); // 카스인경우 &amp; 등록시 글자 짤림

    var url = "https://www.ktmmobile.com/mmobile/newsDetail.do?newsDatSeq=${newsDataBasDto.newsDatSeq}&notiYn=${newsDataBasDto.notiYn}";
    var snsUrl = "https://story.kakao.com/share?url="+encodeURIComponent(url);

    var popup= window.open(snsUrl, "_snsPopupWindow", "width=500, height=500, menubar=yes, status=yes, toolbar=yes, resizable=yes");
    popup.focus();
}

/*
function kakaoLink() {	//여기는 안드로이드, 아이폰 구분해서 마켓으로 보내는 부분을 참고
    var param = 'sendurl?msg=' + encodeURIComponent('보낼 메시지');
    param += '&url=' + '${pageContext.request.scheme}://${header['host']}${pageContext.request.contextPath}/mmobile/newsDataList.do?newsDatSeq=${newsDataBasDto.newsDatSeq}';
    param += '&type=link';
    param += '&apiver=2.0.1';
    param += '&appver=2.0';
    param += '&appid=dev.epiloum.net';
    param += '&appname=' + encodeURIComponent('Epiloum 개발노트');

    if(navigator.userAgent.match(/android/i))
    {
        setTimeout(function(){
            location.href = 'intent://' + param + '#Intent;package=com.kakao.talk;end';
        }, 100);
    }
    else if(navigator.userAgent.match(/(iphone)|(ipod)|(ipad)/i))
    {
        setTimeout(function(){
            location.href = 'itms-apps://itunes.apple.com/app/id362057947?mt=8';
        }, 200);
        setTimeout(function(){
            location.href = 'kakaolink://' + param;
        }, 100);
    }
}
*/

</script>

</t:putAttribute>

<t:putAttribute name="contentAttr">

    <div id="subbody_loading" style="display:flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
        <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
    </div>

<!-- 페이스북 Open Graph Tag -->
<meta content="1694564987490429" property="fb:app_id">
<meta content="website" property="og:type" />
<meta content="${newsDto.newsDatSubject}" property="og:title" />
<meta content="https://www.ktmmobile.com/mmobile/newsDetail.do?newsDatSeq=${newsDataBasDto.newsDatSeq}&notiYn=${newsDataBasDto.notiYn}" property="og:url" />
<meta content="${newsDto.newsLineSbst}" property="og:description" />
<meta content="https://www.ktmmobile.com${newsDto.newsDatImg}" property="og:image" />
<!--// 페이스북 Open Graph Tag -->
<!-- twitter 관련 메타테그 -->
<meta name="twitter:url" content="https://www.ktmmobile.com/mmobile/newsDetail.do?newsDatSeq=${newsDataBasDto.newsDatSeq}&notiYn=${newsDataBasDto.notiYn}" />  <!-- 트위터 카드를 사용하는 표시하고싶은URL -->
<meta name="twitter:title" content="${newsDto.newsDatSubject}" />  <!-- 트위터 카드에 나타날 웹 사이트의 제목 -->
<meta name="twitter:image" content="https://www.ktmmobile.com${newsDto.newsDatImg}" />
<meta name="twitter:description" content="${newsDto.newsLineSbst}" />  <!-- 트위터 카드에 나타날 요약 설명 -->
<meta name="twitter:site" content="kt M mobile" />  <!-- 트위터 카드에 사이트 배포자 트위터아이디 -->
<meta name="twitter:creator" content="kt M mobile" />  <!-- 트위터 카드에 배포자 트위터아이디 -->
<!--// twitter 관련 메타테그 -->

<form name="frm" id="frm" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${newsDataBasDto.pageNo}"/>
<input type="hidden" name="searchValue" id="searchValue" value="${searchDto.searchValue}"/>
<input type="hidden" name="newsLineSbst" id="newsLineSbst" value="${newsDto.newsLineSbst}"/>
<input type="hidden" name="newsDatSeq" id="newsDatSeq" value="${newsDataBasDto.newsDatSeq}"/>
<input type="hidden" id="searchYn" name="searchYn" value="${newsDataBasDto.searchYn == null or newsDataBasDto.searchYn == '' ? 'N' : newsDataBasDto.searchYn}"/>
    <div id="container" >
        <div class="background">
            <div class="viewport">
                <div class="blog_view">
                    <h2 class="news_title">
                        <span>보도자료</span><span class="subtitle">다양한 매체에서 소개된 엠모바일 뉴스를 만나실 수 있습니다.</span>
                    </h2>
                    <div class="blog_detail">
                        <div class="title">
                            <span id="subject">${newsDto.newsDatSubject}</span>
                            <div class="date"><fmt:formatDate value="${newsDto.cretDt}" pattern="yyyy.MM.dd"/>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;조회 ${newsDto.newsDatHitCnt}</div>
                            <div class="sns">
                                <%-- <a onclick="window.open(this.href, '', 'resizable=no,status=no,location=no,toolbar=no,menubar=no,fullscreen=no,scrollbars=no,dependent=no,width=600,height=600'); return false;" href="https://www.facebook.com/sharer/sharer.php?u=${pageContext.request.scheme}://${header['host']}${pageContext.request.contextPath}/mmobile/newsDataList.do?newsDatSeq=${newsDataBasDto.newsDatSeq}"> --%>
                                <a href="javascript:void(0)" onclick="facebook_share();" title="새창 - facebook_share">
                                    <img src="/resources/images/front/icon_facebook.png" id="facebook" alt="facebook">
                                </a>
                                <!-- </a> -->
                                <%-- <a onclick="window.open(this.href, '', 'resizable=no,status=no,location=no,toolbar=no,menubar=no,fullscreen=no,scrollbars=no,dependent=no,width=600,height=600'); return false;" href="https://twitter.com/home?statustext=${newsDto.newsDatSubject}&url=${pageContext.request.scheme}://${header['host']}${pageContext.request.contextPath}/mmobile/newsDataList.do?newsDatSeq=${newsDataBasDto.newsDatSeq}"> --%>
                                <a href="javascript:void(0)" onclick="twitter_share();" title="새창 - twitter_share">
                                    <img src="/resources/images/front/icon_twitter.png" id="twitter" alt="twitter">
                                </a>
                                <!-- </a> -->
                                <%-- <a href="https://story.kakao.com/share?url=${pageContext.request.scheme}://${header['host']}${pageContext.request.contextPath}/mmobile/newsDataList.do?newsDatSeq=${newsDataBasDto.newsDatSeq}" onclick="window.open(this.href, '', 'resizable=no,status=no,location=no,toolbar=no,menubar=no,fullscreen=no,scrollbars=no,dependent=no,width=600,height=600'); return false;"> --%>
                                <a href="javascript:void(0)" onclick="kakaostory_share();" title="새창 - kakaostory_share">
                                    <img src="/resources/images/front/icon_kakaoStory.png" alt="kakaostory">
                                </a>
                                <!-- </a> -->
                            </div>
                        </div>
                        <div class="content">
                            ${newsDto.newsDatSbst}
                            <br>
                            <c:if test="${not empty linkList}">
                            <!-- 관련 보도자료 링크 스타일 적용 // -->
                            <div class="newLink">관련 보도 자료</div>
                            <ul class="link_list">
                                <c:forEach items="${linkList}" var="n">
                                    <li><span>${n.linkNm}</span> <a href="${n.linkUrlAdr}" target="_blank" title="새창 - ${n.linkNm}">${n.linkUrlAdr}</a></li>
                                </c:forEach>
                            </ul>
                            </c:if>
                        </div>
                        <div class="board_list_table">
                            <table>
                                <colgroup>
                                    <col width="80px" />
                                    <col />
                                    <col width="100px" />
                                </colgroup>
                                    <c:if test="${newsDto.preSeq ne ''}">

                                        <tr>
                                            <td scope="row" class="text_align_center">이전 <img src="/resources/images/front/icon_board_prev.png" alt="이전"/></td>
                                            <td class="subject">
                                                <a href="javascript:void(0)" onclick="goNewsDetail('${newsDto.preSeq}')">
                                                    ${newsDto.preSubject}
                                                </a>
                                            </td>
                                            <td><fmt:formatDate value="${newsDto.preDt}" pattern="yyyy.MM.dd"/></td>
                                        </tr>

                                    </c:if>
                                    <c:if test="${newsDto.nextSeq ne ''}">

                                        <tr>
                                            <td scope="row" class="text_align_center">다음 <img src="/resources/images/front/icon_board_next.png" alt="다음"/></td>
                                            <td class="subject">
                                            <a href="javascript:void(0)" onclick="goNewsDetail('${newsDto.nextSeq}')">
                                                ${newsDto.nextSubject}
                                            </a>
                                            </td>
                                            <td><fmt:formatDate value="${newsDto.nextDt}" pattern="yyyy.MM.dd"/></td>
                                        </tr>

                                    </c:if>
                            </table>
                        </div>
                        <button id="goListBtn" type="button" class="btn_right_white float_right">목록</button>
                        <div class="clear_both"></div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</form>
<script>
$(document).ready(function () {

    setTimeout(function(){
        $('#subbody_loading').hide();
    }, 200);

});
</script>
</t:putAttribute>

</t:insertDefinition>