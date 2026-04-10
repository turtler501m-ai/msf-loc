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
<t:insertDefinition name="layoutGnbDefault">
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
        ${empty eventDto.eventHeaderTitle ? '이번달 이벤트' : eventDto.eventHeaderTitle}
    </t:putAttribute>

<t:putAttribute name="scriptHeaderAttr">
 <script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.4/kakao.min.js"
            integrity="sha384-DKYJZ8NLiK8MN4/C5P2dtSmLQ4KwPaoqAfyA/DfmEc1VDxu4yyC7wy6K1Hs90nka" crossorigin="anonymous"></script>
<script type="text/javascript" src="/resources/js/portal/event/eventJoin.js"></script>
<script type="text/javascript" src="/resources/js/portal/event/wordCheck.js"></script>
<script type="text/javascript" src="/resources/js/sns.js?version=2025-03-24"></script>
<script type="text/javascript" src="/resources/js/portal/event/eventDetailAdminView.js?version=2025-11-13"></script>

    <c:set var="titleName" value="${empty eventDto.eventHeaderTitle ? '이번달 이벤트' : eventDto.eventHeaderTitle} | kt M모바일 공식 다이렉트몰" />

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
<input type="hidden" id="selEventVal" value="${eventDto.eventJoinCd}"/>
<input type="hidden" id="successFlag" value="N"/>
<input type="hidden" id="eventFlag" value=""/>
<input type="hidden" id="codeLength" value=""/>
<input type="hidden" id="eventDetailFalg" value="Y"/>
<input type="hidden" id="adminEventDate" value="${adminEventDate}"/>
    <div class="ly-loading" id="subbody_loading">
        <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
    </div>

     <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">${empty eventDto.eventHeaderTitle ? '이번달 이벤트' : eventDto.eventHeaderTitle}</h2>
      </div>
<!--       <div class="c-tabs c-tabs--type1" data-ui-tab>
        <div class="c-tabs__list" role="tablist">
          <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabB1panel" aria-selected="false" tabindex="-1">진행중 이벤트</button>
          <button class="c-tabs__button" id="tab3" role="tab" aria-controls="tabB3panel" aria-selected="false" tabindex="-1">당첨자 확인</button>
        </div>
      </div>
      <div class="c-tabs__panel" id="tabB1panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1"> -->
        <div>
          <div class="c-board c-board--type2">
            <div class="c-row c-row--lg">
              <div class="c-board__head">
                <strong class="c-board__title">
                  <span class="c-board__title__sub"></span>${eventDto.ntcartSubject}
                </strong>
                <div class="c-board__sub">
                  <span class="c-board__sub__item">
                    <span class="sr-only">기간</span>${eventDto.eventStartDt}~${eventDto.eventEndDt}
                  </span>
                  <span class="c-board__sub__item">
                    <span class="c-board__sub__title">조회</span><fmt:formatNumber value="${eventDto.ntcartHitCnt}" pattern="#,###" />
                  </span>
                </div>
                <button class="c-button c-button--xsm" type="button" data-dialog-trigger="#modalShareAlert">
                  <span class="c-hidden">공유하기</span>
                  <i class="c-icon c-icon--share" aria-hidden="true"></i>
                </button>
              </div>
            </div>
            <div class="c-board__content">
              <article class="c-editor">
                <div class="bind-box">
                  ${eventDto.ntcartSbst}
                </div>
              </article>
            </div>
            <div class="c-row c-row--lg">
              <div class="c-board__nav">
                  <c:choose>
                      <c:when test="${eventDto.preSeq ne 0 && eventDto.preSeq ne null}">
                          <a class="c-button" href="javascript:goView('${eventDto.preSeq}','${eventDto.sbstCtg}')">
                            <i class="c-icon c-icon--triangle-left" aria-hidden="true"></i>
                            <span>이전</span>
                          </a>
                      </c:when>
                      <c:otherwise>
                          <a class="c-button is-disabled" href="javascript:void(0);">
                            <i class="c-icon c-icon--triangle-left" aria-hidden="true"></i>
                            <span>이전</span>
                          </a>
                      </c:otherwise>
                  </c:choose>
                  <c:choose>
                      <c:when test="${eventDto.nextSeq ne 0 && eventDto.nextSeq ne null}">
                          <a class="c-button" href="javascript:goView('${eventDto.nextSeq}','${eventDto.sbstCtg}')">
                            <span>다음</span>
                            <i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
                          </a>
                      </c:when>
                      <c:otherwise>
                          <a class="c-button is-disabled" href="javascript:void(0);">
                            <span>다음</span>
                            <i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
                          </a>
                      </c:otherwise>
                  </c:choose>
              </div>
            </div>
            </div>
            <div class="c-button-wrap u-mt--56">
              <a href="javascript:;" class="c-button c-button--lg c-button--white c-button--w460" onclick="showList('/event/eventListAdminView.do?sbstCtg=${eventDto.sbstCtg}&adminEventDate=${adminEventDate}');">목록보기</a>
            </div>
          </div>
        <!-- </div> -->


         <div id="divEventSummary" class="event-summary-button" style="display:none;">
             <div class="event-summary-button__wrap">
                 <button data-dialog-trigger="#eventSummaryModal">
                     <i class="c-icon c-icon--event-summary" aria-hidden="true"></i>혜택 요약 보기
                 </button>
             </div>
         </div>

        <c:if test="${!empty bannerFloatInfo}">
          <div class="event-fab">
               <a href="${bannerFloatInfo[0].bannFloatPcUrl}" target="${bannerFloatInfo[0].bannFloatUrlType == 'N' ? '_blank' : '_self'}">
                   <img src="${bannerFloatInfo[0].bannFloatPcImg}" alt="${bannerFloatInfo[0].bannFloatImgAlt}">
               </a>
           </div>
        </c:if>
      </div>
      <!-- <div class="c-tabs__panel" id="tabB3panel" role="tabpanel" aria-labelledby="tab3" tabindex="-1"></div> -->


  <div class="c-modal c-modal--sm" id="modalShareAlert" role="dialog" tabindex="-1" aria-labelledby="#modalShareAlertTitle">
   <div class="c-modal__dialog" role="document">
     <div class="c-modal__content">
       <div class="c-modal__body u-ta-center">
         <h3 class="c-heading c-heading--fs22 u-fw--bold" id="modalShareAlertTitle">공유하기</h3>
         <div class="c-button-wrap c-flex u-mt--32">
           <a id="btnShareKaka"  class="c-button" href="javascript:void(0);">
             <span class="c-hidden">카카오 공유하기-새 창 열림</span>
             <i class="c-icon c-icon--kakao" aria-hidden="true" onclick="kakaoShareNe(); return false;"></i>
           </a>
           <a class="c-button" href="javascript:void(0);">
             <span class="c-hidden">line 공유하기-새 창 열림</span>
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

    <!-- 대상 요금제 전체보기 팝업 -->
        <div class="c-modal c-modal--lg" id="ktDcRateModal" role="dialog" aria-labelledby="ktDcRateModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="ktDtlCdNm"></h2>
                            <button class="c-button" data-dialog-close>
                                <i class="c-icon c-icon--close" aria-hidden="true"></i>
                                <span class="c-hidden">팝업닫기</span>
                            </button>
                    </div>
                    <div class="c-modal__body u-pt--0" id="ktDocContent">

                    </div>
                </div>
           </div>
        </div>


</t:putAttribute>



    <t:putAttribute name="popLayerAttr">
        <div class="c-modal c-modal--md c-modal--event-summary" id="eventSummaryModal" role="dialog" aria-labelledby="eventSummaryTitle" aria-modal="true" style="z-index: 105;">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="eventSummaryTitle">혜택 요약<span>08.01 ~ 08.15</span></h2>
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
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--primary c-button--w340" data-dialog-close="">닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>




</t:insertDefinition>