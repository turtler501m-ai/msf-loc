<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" var="toDay" pattern="yyyy.MM.dd" />
<t:insertDefinition name="layoutNoGnbDefault">
    <t:putAttribute name="metaTagAttr">
        <!--종료된 이벤트, 없는 이벤트 일 경우에 noindex 삽입 -->
        <c:if test="${empty eventDto || eventDto.dDay < 0}">
            <meta name="robots" content="noindex" />
        </c:if>
        <!-- 페이스북 Open Graph Tag -->
      <meta content="1694564987490429" property="fb:app_id">
      <meta content="website" property="og:type" />
      <meta content="${titleName}" property="og:title" />
      <meta content="${eventDto.eventUrlAdr}" property="og:url" />
      <meta id="meta_og_description" content="" property="og:description" />
        <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
    </t:putAttribute>

    <t:putAttribute name="titleAttr">
        ${empty eventDto.eventHeaderTitle ? '제휴 이벤트' : eventDto.eventHeaderTitle}
    </t:putAttribute>

<t:putAttribute name="scriptHeaderAttr">
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/portal/event/eventDetail.js?version=2025-11-13"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/sns.js"></script>
  <c:set var="titleName" value="${empty eventDto.eventHeaderTitle ? '제휴 이벤트' : eventDto.eventHeaderTitle} | kt M모바일 공식 다이렉트몰" />


<!--// twitter 관련 메타테그 -->
<style>
    .ev_abs {
      position: absolute;
      top: 0;
    }
    .ev_fixed {
      position: fixed;
      bottom: 0
    }
    .ev_blind {
      display: none;
    }
    .ev_contents {
      width: 100%;
      height: auto;
    }
    .ev_floating {
      width: 1038px;
      height: 100px;
    }
    .ev_footer {
      width: 1038px;
      height: 0;
      position: relative;
    }
</style>

<script>
$(document).ready(function(){
    setTimeout(function(){
        $('#subbody_loading').hide();
    }, 200);
});
</script>

</t:putAttribute>

<t:putAttribute name="contentAttr">
<input type="hidden" id="redirectUrl" name="redirectUrl" value="${requestScope['javax.servlet.forward.request_uri']}?ntcartSeq=${param.ntcartSeq}&pageNo=${param.pageNo}&sbstCtg=${param.sbstCtg}&searchValue=${param.searchValue}">
<input type="hidden" name="pageNo" id="pageNo" value="${pageInfoBean.pageNo}"/>
<input type="hidden" name="sbstCtg" id="sbstCtg" value="${eventDto.sbstCtg}"/>
<input type="hidden" name="ntcartSeq" id="ntcartSeq" value="${eventDto.ntcartSeq}"/>
<input type="hidden" name="winnerYn" id="winnerYn" value="${eventDto.winnerYn}"/>       <!-- 이벤트의 해당 당첨자 발표가 있을경우만 값이 있음 -->
<input type="hidden" name="searchValue" id="searchValue" value="${searchDto.searchValue}"/>
<input type="hidden" name="ntcartSbst" id="ntcartSbst" value="${searchDto.searchValue}"/>
<input type="hidden" name="searchYn" id="searchYn" value="${searchDto.searchYn}"/>
<input type="hidden" name="ntcartSubjectRepace" id="ntcartSubjectRepace" value="${eventDto.ntcartSubjectRepace}"/>
<input type="hidden" id="eventBranch" name="eventBranch" value="${eventBranch}"/>
<input type="hidden" id="listImg" name="listImg" value="${eventDto.listImg}"/>
<input type="hidden" id="param" name="param" value=""/>

    <div class="ly-loading" id="subbody_loading">
        <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
    </div>

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">${empty eventDto.eventHeaderTitle ? '제휴 이벤트' : eventDto.eventHeaderTitle}</h2>
            </div>
            <div>
                <div class="c-board c-board--type2">
                    <div class="c-row c-row--lg">
                        <div class="c-board__head">
                            <strong class="c-board__title"> <span
                                class="c-board__title__sub"></span>${eventDto.ntcartSubject}
                            </strong>
                            <div class="c-board__sub">
                                <span class="c-board__sub__item"> <span class="sr-only">기간</span>${eventDto.eventStartDt}~${eventDto.eventEndDt}
                                </span> <span class="c-board__sub__item"> <span
                                    class="c-board__sub__title">조회</span>
                                <fmt:formatNumber value="${eventDto.ntcartHitCnt}"
                                        pattern="#,###" />
                                </span>
                            </div>
                            <button class="c-button c-button--xsm" type="button"
                                data-dialog-trigger="#modalShareAlert">
                                <span class="c-hidden">공유하기</span> <i
                                    class="c-icon c-icon--share" aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                    <div class="c-board__content">
                        <article class="c-editor">
                            <div class="bind-box">${eventDto.ntcartSbst}</div>
                        </article>
                    </div>
                </div>
            </div>
        </div>
        <div class="c-modal c-modal--sm" id="modalShareAlert" role="dialog" tabindex="-1" aria-labelledby="#modalShareAlertTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body u-ta-center">
                        <h3 class="c-heading c-heading--fs22 u-fw--bold" id="modalShareAlertTitle">공유하기</h3>
                        <div class="c-button-wrap c-flex u-mt--32">
                            <a class="c-button" href="javascript:void(0);">
                                <span class="c-hidden">카카오</span> <i class="c-icon c-icon--kakao" aria-hidden="true" onclick="kakaoShare(); return false;"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);">
                                <span class="c-hidden">line</span> <i class="c-icon c-icon--line" aria-hidden="true" onclick="lineShare(); return false;"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);">
                                <span class="c-hidden">URL 복사하기</span>
                                <i class="c-icon c-icon--link" aria-hidden="true" onclick="clip(); return false;"></i>
                            </a>
                        </div>
                        <div class="c-button-wrap u-mt--48">
                            <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

                    <!-- 이벤트 에디터 팝업 -->
    <div class="c-modal c-modal--editor" id="eventModalEditor" role="dialog" aria-labelledby="modalEditorTitle">
        <div class="c-modal__dialog" role="document">
            <div class="main-bs__footer">
                <button class="u-block" data-dialog-close>
                    <i class="c-icon c-icon--close-ty2" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                </button>
            </div>
            <div class="c-modal__content u-bg--transparent">
                <div class="c-modal__header c-hidden">
                    <h2 class="c-modal__title" id="eventModalEditorTitle">이벤트 배너 모아보기</h2>
                </div>
                <div class="c-modal__body" id="eventEditorDiv">
                    <!-- 에디터 영역 -->
                </div>
            </div>
        </div>
    </div>
    <!-- 이벤트 에디터 팝업 끝 -->
    </t:putAttribute>

</t:insertDefinition>