
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un"
    uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<un:useConstants var="Constants"
    className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/review/requestReviewForm.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <input type="hidden" id="makeLength" value="${fn:length(makeReviewList)}" />
        <input type="hidden" id="InfoYn" value="" />
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    사용후기
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

            <input type="hidden" id="menuType" value="reviewReg" />
            <div class="c-tabs c-tabs--type2" data-tab-active="1">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button" onclick="javascript:location.href='/m/requestReView/requestReView.do';" data-tab-header="#tabA1-panel">사용후기 보기</button>
                    <button class="c-tabs__button" onclick="javascript:location.href='/m/requestReView/requestReviewForm.do';" data-tab-header="#tabA2-panel">후기 작성하기</button>
                </div>
                <div class="c-tabs__panel" id="tabA1-panel"></div>
                <div class="c-tabs__panel" id="tabA2-panel">
                    <div class="c-box c-box--type3">
                        <strong class="c-box c-box--type3__title">고객님의 <span class="u-co-blue-1">솔직하고 리얼한</span><br>리뷰를 남겨주세요.</strong>
                        <p class="c-box c-box--type3__text">
                            고객님들의 소중한 의견을 통해 보다 나은<br>M모바일로 보답할게요!
                        </p>
                        <div class="c-box c-box--type3__image">
                            <img src="/resources/images/mobile/content/guide_info_review2.png" alt="">
                        </div>
                    </div>
                    <div class="c-form">
                        <span class="c-form__title" id="chkRecomTypeType">kt M모바일을 추천하시겠습니까?</span>
                        <div class="c-check-wrap" role="group" aria-labelledby="chkRecomType">
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button commend-check" id="chkRecomType1" type="radio" value="1" name="commendYn" checked>
                                <label class="c-label" for="chkRecomType1">추천</label>
                            </div>
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button commend-check" id="chkRecomType2" type="radio" value="0" name="commendYn">
                                <label class="c-label" for="chkRecomType2">비추천</label>
                            </div>
                        </div>
                    </div>
                    <div class="c-form">
                        <span class="c-form__title" id="selPostType">후기 작성</span>
                        <div class="c-form__group" role="group" aria-labelledby="inpG">
                            <c:if test="${nmcp:hasLoginUserSessionBean() && not empty cntrList}">
                                <c:if test="${empty maskingSession}">
                                  <div class="masking-badge-wrap">
                                      <div class="masking-badge" style="top: -12.25rem;">
                                             <a class="masking-badge__button" href="/m/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
                                            <i class="masking-badge__icon" aria-hidden="true"></i>
                                               <p>가려진(*) 정보보기</p>
                                          </a>
                                      </div>
                                  </div>
                                </c:if>
                                <div class="c-form__select has-value">
                                    <select class="c-select" name="phone">
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
                                    <label class="c-form__label">휴대폰 번호</label>
                                </div>
                                <input type="hidden" id="isLogin" value="Y" />
                            </c:if>
                            <c:if test="!${nmcp:hasLoginUserSessionBean()}">
                                <input type="hidden" id="isLogin" value="N" />
                            </c:if>
                            <div class="c-form__select has-value">
                                <select class="c-select" name="eventCd">
                                    <c:forEach var="evList" items="${eventList}">
                                        <option value="${evList.dtlCd}">${evList.dtlCdNm}</option>
                                    </c:forEach>
                                </select>
                                <label class="c-form__label">이벤트 선택</label>
                            </div>
<%--							<p class="c-bullet c-bullet--fyr u-co-gray">월 중 프로모션은 이벤트페이지를 확인해주세요.</p>--%>
                            <p class="c-bullet c-bullet--dot u-co-gray">월 중 프로모션은
                                <button class="c-text-link--bluegreen" id="eventLink">이벤트페이지</button>를 확인해주세요.
                            </p>


                            <c:if test="${!empty makeReviewList}">
                                <h4 class="c-heading c-heading--type5 u-fs-16 u-fw--medium u-mb--4">사용후기 상세 질문</h4>
                                <!-- <p class="u-fs-13 u-co-sub-4 u-mb--12">※선택 답변을 많이 응답할수록 당첨 확률이 높아집니다.</p> -->
                                <hr class="c-hr c-hr--type3">

                                <!-- 질문 리스트 시작 -->
                                <div class="review-detail">
                                 <c:forEach var="makelist" items="${makeReviewList}" varStatus="status">
                                    <!-- 질문 리스트 반복 -->
                                    <div class="c-form">
                                     <input type="hidden" class="review-cnt" value="${makeReviewList.size()}">
                                        <div class="review-detail-title">
                                            <span class="review-detail__text">${status.count}.</span><!-- 넘버링 어드민 -->
                                            <span class="review-detail__text">${makelist.questionDesc}</span><!-- 제목 어드민 -->
                                            <span class="review-detail__sub">${makelist.answerType eq 'M' ? '(복수선택 가능)' : ''}</span><!-- 선택여부 어드민 -->
                                        </div>
                                        <div class="c-check-wrap c-check-wrap--column u-co-gray" id="reviewNum${status.count}">
                                          <c:forEach var="subList" items="${makelist.subList}" varStatus="stat">
                                            <input class="c-checkbox" data-sub="${makelist.answerType eq 'S' ? 'detailQuestion' :'detailQuestions'}" data-sub2="${makelist.authFlagYn eq 'Y' ? 'consentInfo' :'notConInfo '}" data-sub3="${makelist.questionDesc}" name="detailQuestion" value="${makelist.questionId}/${subList.answerId}" id="reviewDetail_Q${status.count}_${stat.count}" type="checkbox">
                                            <label class="c-label u-ml--12" for="reviewDetail_Q${status.count}_${stat.count}">${subList.answerDesc}</label>
                                          </c:forEach>
                                        </div>
                                    </div>
                                    <!-- 질문 리스트 반복 끝 -->
                                 </c:forEach>
                                </div>
                                <!-- 질문 리스트 끝 -->
                           </c:if>
                            <div class="c-textarea-wrap u-mt--0">
                                <span class="c-form__title" id="reviewSbst">사용후기</span>
                                <textarea class="c-textarea" placeholder="${eventHolder.docContent}" name="reviewSbst"></textarea>
                                <div class="c-textarea-wrap__sub">
                                    <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span> <span><span id="txtCnt">0</span>/800</span>
                                </div>
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">사용후기는 kt M모바일 가입고객만 작성 가능합니다.</li>
                                <li class="c-text-list__item u-co-gray">욕설/비방/광고성 글이나 부적절한 내용의 경우 삭제 될 수 있습니다.</li>
                            </ul>
                        </div>
                    </div>
                    <div class="c-form">
                        <span class="c-form__title" id="inpShrType">SNS공유<span class="u-co-gray">(선택)</span></span>
                        <div class="c-form__group" role="group" aria-labelledby="inpG">
                            <input class="c-input" type="text" name="snsInfo" placeholder="사용 후기의 URL을 입력해주세요" title="URL 입력" maxlength="50">
                        </div>
                    </div>
                    <div class="c-form" id="fileContainer">
                        <span class="c-form__title" id="fileUpload">이미지 업로드<span class="u-co-gray">(선택)</span></span>
                        <div class="c-form__group" role="group" aria-labelledby="imageUploadGroup">
                            <div class="upload-image">
                                <label class="upload-image__label image-label1">
                                <input class="upload-image__hidden" key="file1" name="file" type="file" id="fileTarget1" accept=".jpg, .gif, .png">
                                    <i class="c-icon c-icon--photo" aria-hidden="true"></i>
                                    <span class="u-co-gray">사진 1 / 2</span>
                                    <button class="upload-image__delete" style="display: none;">
                                        <i class="c-icon c-icon--delete" aria-hidden="true"></i>
                                        <span class="c-hidden">삭제</span>
                                    </button>
                                    <div id="file1"></div>
                                </label>
                            </div>
                        </div>
                    </div>
                    <p class="c-bullet c-bullet--fyr u-co-gray">1장당 2MB 이내의 jpg, gif, png 형식으로 최대2장까지 등록가능</p>
                    <div class="c-button-wrap">
                        <button class="c-button c-button--lg c-button--full c-button--primary" id="certifyPopBut">후기 작성하고 선물 받기</button>
                    </div>
                </div>
            </div>
        </div>

        <button id="consentInfos" data-dialog-trigger="#modalAgree" style="display: none;"></button>
        <!-- 개인정보 수집 및 이용 동의 (선택) 팝업 -->
        <div class="c-modal" id="modalAgree" role="dialog" tabindex="-1" aria-describedby="#modalAgree-title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="modalAgree-title">개인정보 수집 및 이용 동의 (선택)</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                         ${formSelect.docContent}
                    </div>
                    <div class="c-modal__footer">
                        <button id="agreeBtn" class="c-button c-button--full c-button--primary" data-dialog-close>동의 후 글 작성하기</button>
                    </div>
                </div>
            </div>
        </div>



        <script src="../../resources/js/mobile/vendor/swiper.min.js"></script>

    </t:putAttribute>
</t:insertDefinition>