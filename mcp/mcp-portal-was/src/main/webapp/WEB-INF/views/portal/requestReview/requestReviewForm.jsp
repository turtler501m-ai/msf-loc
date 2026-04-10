<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<!-- <meta http-equiv="Content-Security-Policy" content="img-src 'self' data:; default-src 'self' https://local.portal.ktmmobile.com/requestReView/requestReviewForm.do">
 -->

<t:insertDefinition name="layoutMainDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/review/requestReviewForm.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/vendor/swiper.min.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="makeLength" value="${fn:length(makeReviewList)}" />
         <input type="hidden" id="InfoYn" value="" />
        <div id="subbody_loading" style="display: flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
            <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
        </div>
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">사용후기</h2>
            </div>
            <div class="c-tabs c-tabs--type1">
                <div class="c-tabs__list">
                    <a class="c-tabs__button" id="tab1" role="tab" aria-selected="false" href="/requestReView/requestReView.do">사용후기 보기</a>
                    <a class="c-tabs__button is-active" id="tab2" role="tab" aria-selected="true" href="/requestReView/requestReviewForm.do"><h3>후기 작성하기</h3></a>
                </div>
            </div>
            <div class="c-tabs__panel">
                <div></div>
            </div>
            <div class="c-tabs__panel u-block">
                <div>
                    <div class="ly-page--deco u-bk--blue-1">
                        <div class="ly-avail--wrap">
                            <img src="/resources/images/portal/content/img_review_center2.png" alt="고객님의 솔직하고 리얼한 리뷰를 남겨주세요. 고객님들의 소중한 의견을 통해 보다 나은 M모바일로 보답할게요! 후기 작성하고 선물받기">
                        </div>
                    </div>
                    <div class="c-row c-row--lg">
                        <p class="c-heading c-heading--fs20 u-mt--56">kt M모바일을 추천하시겠습니까?</p>
                        <hr class="c-hr c-hr--title">
                        <div class="c-form-wrap">
                            <div class="c-form-group" role="group" aira-labelledby="radioGroupHeadR">
                                <h3 class="sr-only" id="radioGroupHeadR">kt M모바일 추천 여부</h3>
                                <div class="c-checktype-row c-col2">
                                    <input class="c-radio c-radio--button commend-check" id="radioR1" type="radio" value="1" name="commendYn" checked>
                                    <label class="c-label" for="radioR1">추천</label>
                                    <input class="c-radio c-radio--button commend-check" id="radioR2" type="radio" value="0" name="commendYn">
                                    <label class="c-label" for="radioR2">비추천</label>
                                </div>
                            </div>
                        </div>
                        <div class="c-form-wrap u-mt--48">
                            <!-- case1-1: 사용중인 회선이 있는 경우-->
                            <c:choose>
                                <c:when test="${nmcp:hasLoginUserSessionBean() && not empty cntrList}">
                                    <c:if test="${empty maskingSession}">
                                            <div class="masking-badge-wrap">
                                            <div class="masking-badge">
                                                <a class="masking-badge__button" href="/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기" style="top: -12.25rem;">
                                                    <i class="masking-badge__icon" aria-hidden="true"></i>
                                                     <p>가려진(*)<br />정보보기</p>
                                                   </a>
                                            </div>
                                        </div>
                                    </c:if>
                                    <div class="c-form-group" role="group" aria-labelledby="selectGroupHeadA">
                                        <span class="c-group--head" id="selectGroupHeadA">가입정보 확인</span>
                                        <div class="c-form-row c-col2">
                                            <div class="c-form-field">
                                                <div class="c-form__select">
                                                    <select class="c-select" id="selectA" name="phone">
                                                        <c:forEach var="list" items="${cntrList}">
                                                            <c:choose>
                                                                <c:when test="${maskingSession eq 'Y'}">
                                                                      <c:choose>
                                                                        <c:when test="${fn:length(list.mkMobileNo) > 10}">
                                                                            <option value="${list.mkMobileNo}" data-maskedTelNo= "${list.mkMobileNo}" data-contractNum="${list.contractNum}">
                                                                                ${fn:substring(list.cntrMobileNo,0,3)}-${fn:substring(list.cntrMobileNo,3,7)}-${fn:substring(list.cntrMobileNo,7,11)}
                                                                            </option>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <option value="${list.mkMobileNo}" data-maskedTelNo= "${list.mkMobileNo}" data-contractNum="${list.contractNum}">
                                                                                ${fn:substring(list.cntrMobileNo,0,3)}-${fn:substring(list.cntrMobileNo,3,6)}-${fn:substring(list.cntrMobileNo,6,10)}
                                                                            </option>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:choose>
                                                                        <c:when test="${fn:length(list.mkMobileNo) > 10}">
                                                                            <option value="${list.mkMobileNo}" data-maskedTelNo= "${list.mkMobileNo}" data-contractNum="${list.contractNum}">
                                                                                ${fn:substring(list.mkMobileNo,0,3)}-${fn:substring(list.mkMobileNo,3,7)}-${fn:substring(list.mkMobileNo,7,11)}
                                                                            </option>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <option value="${list.mkMobileNo}" data-maskedTelNo= "${list.mkMobileNo}" data-contractNum="${list.contractNum}">
                                                                                ${fn:substring(list.mkMobileNo,0,3)}-${fn:substring(list.mkMobileNo,3,6)}-${fn:substring(list.mkMobileNo,6,10)}
                                                                            </option>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:otherwise>
                                                           </c:choose>
                                                      </c:forEach>
                                                    </select>
                                                    <label class="c-form__label" for="selectA">휴대폰 번호</label>
                                                </div>
                                            </div>
                                            <div class="c-form-field">
                                                <div class="c-form__select">
                                                    <select class="c-select" id="selectB" name="eventCd">
                                                        <c:forEach var="evList" items="${eventList}">
                                                            <option value="${evList.dtlCd}">${evList.dtlCdNm}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <label class="c-form__label" for="selectB">이벤트 선택</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <p class="c-bullet c-bullet--dot u-co-gray">월 중 프로모션은
                                        <button class="c-text-link--bluegreen" id="eventLink">이벤트페이지</button>를 확인해주세요.
                                    </p>
                                    <input type="hidden" id="isLogin" value="Y" />
                                </c:when>

                                <c:otherwise>
                                    <input type="hidden" id="isLogin" value="N" />
                                    <!-- case1-2: 사용중인 회선이 없는 경우-->
                                    <div class="c-form u-width--460">
                                        <label class="c-label" for="selectC">이벤트 선택</label>
                                        <select class="c-select" id="selectC" name="eventCd">
                                            <c:forEach var="evList" items="${eventList}">
                                                <option value="${evList.dtlCd}">${evList.dtlCdNm}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <p class="c-bullet c-bullet--dot u-co-gray">월 중 프로모션은
                                        <button class="c-text-link--bluegreen" id="eventLink">이벤트페이지</button>를 확인해주세요.
                                    </p>
                                </c:otherwise>
                            </c:choose>


                           <c:if test="${!empty makeReviewList}">
                                <h3 class="c-form__title--type2 u-fs-16 u-mt--48">사용후기 상세 질문</h3>
                                <hr class="c-hr c-hr--title">

                                <!-- 질문 리스트 시작 -->
                                <div class="review-detail">
                                    <c:forEach var="makelist" items="${makeReviewList}" varStatus="status">
                                        <!-- 질문 리스트 반복 -->
                                        <div class="c-form-wrap">
                                            <div class="c-form-group" role="group" aria-labelledby="reviewDetail_Q${status.count}">
                                                <div class="c-group--head" id="reviewDetail_Q${status.count}"><!-- 웹접근성 용도의 ID값이라 기능적인건 없음  / 모바일에는 빼놨음  -->
                                                    <input type="hidden" class="review-cnt" value="${makeReviewList.size()}">
                                                    <div class="review-detail-title">
                                                        <span class="review-detail__text">${status.count}.</span><!-- 넘버링 어드민 -->
                                                        <span class="review-detail__text">${makelist.questionDesc}</span><!-- 제목 어드민 -->
                                                        <span class="review-detail__sub">${makelist.answerType eq 'M' ? '(복수선택 가능)' : ''}</span><!-- 선택여부 어드민 -->
                                                    </div>
                                                </div>
                                                <div class="c-checktype-row" id="reviewNum${status.count}">
                                                  <c:forEach var="subList" items="${makelist.subList}" varStatus="stat">
                                                    <input class="c-checkbox" data-sub="${makelist.answerType eq 'S' ? 'detailQuestion' :'detailQuestions'}" data-sub2="${makelist.authFlagYn eq 'Y' ? 'consentInfo' :'notConInfo '}" data-sub3="${makelist.questionDesc}" name="detailQuestion" value="${makelist.questionId}/${subList.answerId}" id="reviewDetail_Q${status.count}_${stat.count}" type="checkbox">
                                                    <label class="c-label u-ml--12" for="reviewDetail_Q${status.count}_${stat.count}">${subList.answerDesc}</label>
                                                  </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 질문 리스트 반복 끝 -->
                                    </c:forEach>
                                </div>
                                <!-- 질문 리스트 끝 -->
                            </c:if>
                            <div class="c-form u-mt--48">
                                <label class="c-label" for="textareaA">사용후기</label>
                                <div class="c-textarea-wrap">
<%--									<textarea class="c-textarea" id="textareaA" name="reviewSbst" placeholder="※ 당첨확률 높이는 방법&#10;--%>
<%--① 가입/개통과정과 실제 사용경험을 생생하게 들려주세요! (ex. 셀프개통으로 쉽게 개통했어요, 통신요금을 월 3만원 절약했어요 등 사용상의 장점이 구체적으로 잘 드러나도록 작성)&#09;--%>
<%--② 관련 사진을 함께 업로드 해주세요! (ex. 구매한 유심 사진, 휴대폰을 개통하는 사진, 월 요금 캡쳐 사진, 가입설명서 사진 등)&#09;--%>
<%--③ SNS로 사진과 해시태그를 함께 공유해주시고, 해당 URL을 남겨주세요! (팔로워가 많거나 활성화 되어있는 SNS일수록 우수후기 당첨확률이 높아집니다.)" style="ime-mode: active;"></textarea>--%>
                                            <textarea class="c-textarea" id="textareaA" name="reviewSbst" placeholder="${eventHolder.docContent}" style="ime-mode: active;"></textarea>

                                    <div class="c-textarea-wrap__sub">
                                        <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                                        <span><span id="txtCnt">0</span>/800</span>
                                    </div>
                                </div>
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray">사용후기는 kt M모바일 가입고객만 작성 가능합니다.</li>
                                    <li class="c-text-list__item u-co-gray">욕설/비방/광고성 글이나 부적절한 내용의 경우 삭제 될 수 있습니다.</li>
                                </ul>
                            </div>
                            <div class="c-form u-mt--48">
                                <div class="c-input-wrap">
                                    <label class="c-label" for="inputA">SNS공유 URL<span class="c-form__sub">(선택)</span></label>
                                    <input class="c-input" id="inputA" type="text" name="snsInfo" placeholder="사용 후기의 URL을 입력해주세요." title="SNS공유 URL 입력" maxlength="50">
                                </div>
                            </div>
                            <div class="c-form-group u-mt--48" role="group" aria-labelledby="selectGroupHeadB">
                                <div class="c-group--head" id="selectGroupHeadB">
                                    <span>이미지 업로드<span class="c-form__sub">(선택)</span></span>
                                </div>
                                <div class="c-file-image">
                                    <input class="c-file upload-image__hidden" key="file1" id="file1" name="file" type="file" title="사진 등록" accept='.jpg, .gif, .png' onchange="setThumb(event);">
                                    <input class="c-file upload-image__hidden" key="file2" id="file2" name="file" type="file" title="사진 등록" accept='.jpg, .gif, .png' onchange="setThumb(event);">
                                    <label class="c-label file-label" for="file1"><span class="sr-only">업로드한</span> <span class="c-label__sub">사진 0/2</span></label>
                                    <div class="c-form-row c-col2 staged_img-list"></div>
                                    <p class="c-bullet c-bullet--fyr u-co-gray">1장당 2MB 이내의 jpg, gif, png 형식으로 최대2장까지 등록 가능</p>
                                </div>
                            </div>
                        </div>
                        <div class="c-button-wrap u-mt--56">
                            <a class="c-button c-button--lg c-button--primary u-width--460" href="javascript:void(0)" id="certifyPopBut">후기 작성하고 선물 받기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <button id="consentInfos" data-dialog-trigger="#modalAgree" style="display: none;"></button>

        <!-- 개인정보 수집 및 이용 동의 (선택) 팝업 -->
        <div class="c-modal c-modal--xlg" id="modalAgree" role="dialog" aria-labelledby="modalAgreeTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="modalAgreeAgreeTitle">개인정보 수집 및 이용 동의 (선택)</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                         ${formSelect.docContent}
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <button id="agreeBtn" class="c-button c-button--lg c-button--primary c-button--w460" oncl data-dialog-close>동의 후 글 작성하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>

</t:insertDefinition>