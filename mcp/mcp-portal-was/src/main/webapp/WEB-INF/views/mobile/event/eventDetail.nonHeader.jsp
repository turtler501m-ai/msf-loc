<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutNoGnbFotter">

    <t:putAttribute name="metaTagAttr">
        <!--종료된 이벤트, 없는 이벤트 일 경우에 noindex 삽입 -->
        <c:if test="${empty eventDto || eventDto.dDay < 0}">
            <meta name="robots" content="noindex" />
        </c:if>
        <c:set var="titleName" value="${empty eventDto.eventHeaderTitle ? '제휴 이벤트' : eventDto.eventHeaderTitle} | kt M모바일 공식 다이렉트몰" />
        <!-- 페이스북 Open Graph Tag -->
        <meta content="1694564987490429" property="fb:app_id">
        <meta content="website" property="og:type" />
        <meta content="${titleName}" property="og:title" />
        <meta content="${eventDto.eventUrlAdr}" property="og:url" />
        <meta id="meta_og_description" content="" property="og:description" />
        <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
        <!--// 페이스북 Open Graph Tag -->
    </t:putAttribute>


    <t:putAttribute name="titleAttr">
        ${empty eventDto.eventHeaderTitle ? '제휴 이벤트' : eventDto.eventHeaderTitle}
    </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript"  src="/resources/js/mobile/event/eventDetail.js?version=2026-01-07"></script>
        <script type="text/javascript" src="/resources/js/mobile/sns.js"></script>



    </t:putAttribute>


 <t:putAttribute name="contentAttr">

        <div id="content">
            <form name="mEventDetail" id="mEventDetail" action="/m/event/eventList.do" method="post">
                <input type="hidden" name="ntcartSeq" id="ntcartSeq" value="${eventDto.ntcartSeq}">
                <input type="hidden" name="ntcartSubjectRepace" id="ntcartSubjectRepace" value="${eventDto.ntcartSubjectRepace}">
                <input type="hidden" name="order" id="order" value="${order}">
                <input type="hidden" name="chkChoice" id="chkChoice" value="${chkChoice}" />
                <input type="hidden" name="selectEvt" id="selectEvt" value="${selectEvt}" />
                <input type="hidden" name="pageNo" id="pageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}">
                <input type="hidden" id="sbstCtg" name="sbstCtg" value="${empty eventDto.sbstCtg?'J':eventDto.sbstCtg}" />
                <input type="hidden" id="eventBranch" name="eventBranch" value="${eventBranch}" />
                <input type="hidden" id="listImg" name="listImg" value="${eventDto.listImg}" />
                <input type="hidden" id="param" name="param" value="" />

                <div class="ly-content" id="main-content">
                    <div class="ly-page-sticky">
                        <h2 class="ly-page__head">${empty eventDto.eventHeaderTitle ? '제휴 이벤트' : eventDto.eventHeaderTitle}
                        <button type="button" class="header-clone__gnb"></button>
                        </h2>
                    </div>
                    <div class="c-board c-board--type2">
                        <div class="c-board__head">
                            <strong class="c-board__title">${eventDto.ntcartSubject}</strong>
                            <div class="c-board__sub">
                                <span>${eventDto.eventStartDt} ~ ${eventDto.eventEndDt}</span>
                                <span class="tit">조회</span> <span><fmt:formatNumber value="${eventDto.ntcartHitCnt}" pattern="#,###" /></span>
                            </div>
                            <a class="c-button" href="javascript:void(0);" data-dialog-trigger="#sns-share_dialog">
                                <i class="c-icon c-icon--share" aria-hidden="true"></i>
                                <span class="c-hidden">공유하기</span>
                            </a>
                        </div>
                        <div class="c-board c-board--type2 nohead">
                            <div class="c-board__content pd-0">
                                <article class="c-editor c-expand">
                                    <div class="bind-box">${eventDto.ntcartSbst2}</div>
                                </article>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <div class="c-modal c-modal--sm" id="sns-share_dialog" role="dialog" tabindex="-1" aria-labelledby="#modalShareAlertTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body u-ta-center">
                        <h3 class="c-heading c-heading--fs22 u-fw--bold" id="modalShareAlertTitle">공유하기</h3>
                        <div class="c-button-wrap c-flex u-mt--32">
                            <a class="c-button" href="javascript:void(0);">
                                <span class="c-hidden">카카오</span>
                                <i class="c-icon c-icon--kakao" aria-hidden="true" onclick="kakaoShare(); return false;"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);">
                                <span class="c-hidden">line</span>
                                <i class="c-icon c-icon--line" aria-hidden="true" onclick="lineShare(); return false;"></i>
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
    <div class="c-modal c-modal--editor" id="eventModalEditor" role="dialog" aria-describedby="modalEditorTitle" >
        <div class="c-modal__dialog c-modal__dialog--full" role="document">
            <div class="c-modal__content u-bg--transparent">
                <div class="c-modal__header sr-only">
                    <h1 class="c-modal__title" id="eventModalEditorTitle">이벤트 배너 모아보기</h1>
                </div>
                <div class="main-bs__footer u-bg--transparent c-flex--jfy-center">
                    <a class="u-block" data-dialog-close>
                        <i class="c-icon c-icon--close-ty2" aria-hidden="true"></i>
                    </a>
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
