<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="metaTagAttr">
        <!--종료된 이벤트, 없는 이벤트 일 경우에 noindex 삽입 -->
        <c:if test="${empty eventDto || eventDto.dDay < 0}">
            <meta name="robots" content="noindex" />
        </c:if>
        <c:set var="titleName" value="${empty eventDto.eventHeaderTitle ? '이번달 이벤트' : eventDto.eventHeaderTitle} | kt M모바일 공식 다이렉트몰" />
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
        ${empty eventDto.eventHeaderTitle ? '이번달 이벤트' : eventDto.eventHeaderTitle}
    </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.4/kakao.min.js"
                integrity="sha384-DKYJZ8NLiK8MN4/C5P2dtSmLQ4KwPaoqAfyA/DfmEc1VDxu4yyC7wy6K1Hs90nka" crossorigin="anonymous"></script>
        <script type="text/javascript"  src="/resources/js/mobile/event/eventDetailAdminView.js?version=2026-01-07"></script>
        <script type="text/javascript" src="/resources/js/mobile/event/eventJoin.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/event/wordCheck.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/sns.js?version=2025-03-24"></script>

    </t:putAttribute>


 <t:putAttribute name="contentAttr">

         <div id="content">
            <form name="mEventDetail" id="mEventDetail" action="/m/event/eventList.do" method="post">
            <input type="hidden" name="ntcartSeq" id="ntcartSeq" value="${eventDto.ntcartSeq}">
            <input type="hidden" name="ntcartSubjectRepace" id="ntcartSubjectRepace" value="${eventDto.ntcartSubjectRepace}">
            <input type="hidden" name="order" id="order" value="${order}">
            <input type="hidden" name="chkChoice" id="chkChoice"  value="${chkChoice}" />
            <input type="hidden" name="selectEvt" id="selectEvt"  value="${selectEvt}" />
            <input type="hidden" name="pageNo" id="pageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}">
            <input type="hidden" id="sbstCtg" name="sbstCtg" value="${empty eventDto.sbstCtg?'J':eventDto.sbstCtg}"/>
            <input type="hidden" id="eventBranch" name="eventBranch" value="${eventBranch}"/>
            <input type="hidden" id="listImg" name="listImg" value="${eventDto.listImg}"/>
            <input type="hidden" id="param" name="param" value=""/>
            <input type="hidden" id="selEventVal" value="${eventDto.eventJoinCd}"/>
            <input type="hidden" id="successFlag" value="N"/>
            <input type="hidden" id="eventFlag" value=""/>
            <input type="hidden" id="codeLength" value=""/>
            <input type="hidden" id="eventDetailFalg" value="Y"/>
            <input type="hidden" id="adminEventDate" value="${adminEventDate}"/>

    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">${empty eventDto.eventHeaderTitle ? '이번달 이벤트' : eventDto.eventHeaderTitle}
            <button type="button" class="header-clone__gnb"></button>
        </h2>
      </div>
<!--       <div class="c-tabs c-tabs--type2">
        <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
          <button class="c-tabs__button" data-tab-header="#tabA1-panel" id="tab1">진행중</button>
          <button class="c-tabs__button" data-tab-header="#tabA3-panel" id="tab3">당첨자 확인</button>
        </div>
        <div class="c-tabs__panel" id="tabA1-panel"> -->
          <!-- 진행중 이벤트 상세-->
          <div class="c-board c-board--type2">
            <div class="c-board__head">
              <strong class="c-board__title">${eventDto.ntcartSubject}</strong>
              <div class="c-board__sub">
                <span>${eventDto.eventStartDt} ~
                      ${eventDto.eventEndDt}
                </span>
                <span class="tit">조회</span>
                <span><fmt:formatNumber value="${eventDto.ntcartHitCnt}" pattern="#,###" /></span>
              </div>
              <a class="c-button" href="javascript:void(0);" data-dialog-trigger="#sns-share_dialog">
                <i class="c-icon c-icon--share" aria-hidden="true"></i>
                <span class="c-hidden">공유하기</span>
              </a>
            </div>

            <div class="c-board c-board--type2">
              <div class="c-board__content pd-0">
                <article class="c-editor c-expand">
                  <div class="bind-box">
                      ${eventDto.ntcartSbst2}
                  </div>
                </article>
             </div>
             <div class="c-board__nav">
                  <c:choose>
                      <c:when test="${eventDto.preSeq ne 0 && eventDto.preSeq ne null}">
                          <a class="c-button"
                              href="javascript:goView('${eventDto.preSeq}','${eventDto.sbstCtg}')"> <i
                              class="c-icon c-icon--triangle-left" aria-hidden="true"></i> <span>이전글</span>
                          </a>
                      </c:when>
                      <c:otherwise>
                          <a class="c-button is-disabled"> <i
                              class="c-icon c-icon--triangle-left" aria-hidden="true"></i> <span>이전글</span>
                          </a>
                      </c:otherwise>
                  </c:choose>
                  <c:choose>
                      <c:when test="${eventDto.nextSeq ne 0 && eventDto.nextSeq ne null}">
                          <a class="c-button"
                              href="javascript:goView('${eventDto.nextSeq}','${eventDto.sbstCtg}')"> <span>다음글</span>
                              <i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
                          </a>
                      </c:when>
                      <c:otherwise>
                          <a class="c-button is-disabled"> <span>다음글</span> <i
                              class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
                          </a>
                      </c:otherwise>
                  </c:choose>
             </div>
           </div>
          </div>
          <div class="c-button-wrap">
            <a href="javascript:;" class="c-button c-button--full c-button--white" onclick="showList('/m/event/eventListAdminView.do?sbstCtg=${eventDto.sbstCtg}&adminEventDate=${adminEventDate}');">목록보기</a>
          </div>
        </div>
<!--         <div class="c-tabs__panel" id="tabA3-panel"> </div>
      </div>
    </div> -->
    </form>
    </div>
     <div id="divEventSummary" class="event-summary-button" style="display:none;">
         <div class="event-summary-button__wrap">
             <button data-dialog-trigger="#eventSummaryModal">
                 <i class="c-icon c-icon--event-summary" aria-hidden="true"></i>혜택 요약 보기
             </button>
         </div>
     </div>
     <c:if test="${!empty bannerFloatInfo}">
         <div class="event-fab">
            <a href="${bannerFloatInfo[0].bannFloatMoUrl}" target="${bannerFloatInfo[0].bannFloatUrlType == 'N' ? '_blank' : '_self'}">
                <img src="${bannerFloatInfo[0].bannFloatMoImg}" alt="${bannerFloatInfo[0].bannFloatImgAlt}">
            </a>
          </div>
      </c:if>

    <div class="c-modal c-modal--sm" id="sns-share_dialog" role="dialog" tabindex="-1" aria-labelledby="#modalShareAlertTitle">
      <div class="c-modal__dialog" role="document">
        <div class="c-modal__content">
          <div class="c-modal__body u-ta-center">
            <h3 class="c-heading c-heading--fs22 u-fw--bold" id="modalShareAlertTitle">공유하기</h3>
            <div class="c-button-wrap c-flex u-mt--32">
              <a class="c-button" href="javascript:void(0);">
                <span class="c-hidden">카카오</span>
                <i class="c-icon c-icon--kakao" aria-hidden="true" onclick="kakaoShareNe(); return false;"></i>
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

    </t:putAttribute>


    <t:putAttribute name="popLayerAttr">
        <div class="c-modal c-modal--event-summary" id="eventSummaryModal" role="dialog" tabindex="-1" aria-describedby="eventSummaryTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="eventSummaryTitle">혜택 요약<span>08.01 ~ 08.15</span></h1>
                        <button class="c-button" data-dialog-close="">
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <ul class="event-summary__list">

                        </ul>
                    </div>
                    <div class="c-modal__footer">
                        <button class="c-button c-button--full c-button--primary" data-dialog-close="">닫기</button>
                    </div>
                </div>
            </div>
        </div>

                <!-- 대상 요금제 전체보기 팝업 -->
        <div class="c-modal" id="ktDcRateModal" role="dialog" tabindex="-1" aria-describedby="#ktDcRateModalTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="ktDtlCdNm"></h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body" id="ktDocContent">

                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>

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
</t:insertDefinition>
