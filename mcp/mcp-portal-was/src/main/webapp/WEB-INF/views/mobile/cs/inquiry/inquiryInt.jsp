<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/cs/inquiry/inquiryInt.js?version=25.12.03"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    1:1 상담문의
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

            <div class="c-tabs c-tabs--type2" data-ignore="true">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button is-active">1:1 문의작성</button>
                    <button class="c-tabs__button" id="nextTab">내 문의 확인하기</button>
                </div>

                <div class="c-tabs__panel">
                    <c:if test="${empty maskingSession}">
                      <div class="masking-badge-wrap">
                          <div class="masking-badge" style="top: -2rem;">
                                 <a class="masking-badge__button" href="/m/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
                                <i class="masking-badge__icon" aria-hidden="true"></i>
                                   <p>가려진(*) 정보보기</p>
                              </a>
                          </div>
                      </div>
                    </c:if>
                    <c:choose>
                        <c:when test="${userSession eq null and authSmsDto eq null}">
                            <div class="csInq-info-wrap u-mt--40 u-mb--40">
                                <p class="csInq-info--txt u-mt--0">
                                    <span class="u-co-sub-4">kt M모바일 서비스에 대해 궁금한 사항을 문의</span>해 주시면 답변 드리도록 하겠습니다. 주말 및 휴일에는 답변 처리가 다소 지연될 수 있습니다.
                                </p>
                                <p class="csInq-info--bullet u-co-sub-4 u-mt--10">
                                            간단한 문의는 우측 하단 챗봇을 이용하시면 빠른 답변을 받아보실 수 있습니다. 많은 이용 부탁 드립니다.
                                </p>
                                <p class="csInq-info--rink u-mt--10">
                                    <a class="c-button-csInq" href="/m/mypage/farPricePlanView.do" title="요금제 변경(예약) 페이지 바로가기">요금제 변경(예약)</a>
                                    <a class="c-button-csInq" href="/m/mypage/regServiceView.do" title="부가서비스 신청(변경) 페이지 바로가기">부가서비스 신청(변경)</a>
                                    <a class="c-button-csInq" href="/m/mypage/chargeView05.do" title="납부방법 변경 페이지 바로가기">납부방법 변경</a>
                                    <a class="c-button-csInq" href="/m/mypage/unpaidChargeList.do" title="요금즉시납부 페이지 바로가기">요금즉시납부</a>
                                    <a class="c-button-csInq" href="/m/mypage/usimSelfChg.do" title="유심 셀프변경 페이지 바로가기">유심 셀프변경</a>
                                    <span class="u-mr--4">등은</span>
                                    <a class="c-button-csInq" href="/m/mypage/myinfoView.do" title="마이페이지 바로가기">마이페이지</a>
                                    <span>에서  바로 처리하실 수 있습니다.</span>
                                </p>
                                <p class="csInq-info--txt"><span class="u-co-sub-4">로그인 및 정회원 인증</span>을 완료하신 후 문의해주시면, 고객님의 정보를 확인하여 보다 정확하게 안내드릴 수 있습니다.</p>
                                <p class="csInq-info--bullet u-co-sub-4 u-mt--10">
                                       회원정보 변경은 홈페이지 상담이 불가하오니, 고객센터(가입휴대폰에서 114[무료], 타사휴대폰 또는 유선전화 1899-5000[유료]) 로 문의 부탁 드립니다.
                                </p>
                                <p class="csInq-info--txt">산업안전보건법에 따라, 1:1문의 중 <span class="u-co-sub-4">욕설, 폭언, 성희롱, 위협적인 언행 등이 확인될 경우, 상담이 제한되거나 중단될 수 있습니다.</span></p>
                            </div>
                            <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
                            <input type="hidden" name="certifyYn" id="certifyYn" value="">
                            <div class="c-agree">
                                <div class="c-agree__item">
                                    <input class="c-checkbox" id="chkA1" type="checkbox" name="chkA1">
                                    <label class="c-label" for="chkA1">개인정보 수집이용 동의</label>
                                    <button class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMSINF&cdGroupId2=TERMSINF03');">
                                        <span class="c-hidden">상세보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="c-button-wrap">
                                <a class="c-button c-button--xsm" href="/m/loginForm.do">kt M모바일계정 로그인</a>
                                <a class="c-button c-button--xsm" href="/m/join/fstPage.do"> <b>회원가입</b></a>
                            </div>
                            <div class="c-button-wrap">
                                <button class="c-button c-button--full c-button--primary" id="applyBtn" disabled="disabled">확인</button>
                            </div>
                        </c:when>

                        <c:otherwise>
                            <div class="csInq-info-wrap u-mt--40">
                                <p class="csInq-info--txt u-mt--0">
                                    <span class="u-co-sub-4">kt M모바일 서비스에 대해 궁금한 사항을 문의</span>해 주시면 답변 드리도록 하겠습니다. 주말 및 휴일에는 답변 처리가 다소 지연될 수 있습니다.
                                </p>
                                <p class="csInq-info--bullet u-co-sub-4 u-mt--10">
                                            간단한 문의는 우측 하단 챗봇을 이용하시면 빠른 답변을 받아보실 수 있습니다. 많은 이용 부탁 드립니다.
                                </p>
                                <p class="csInq-info--rink u-mt--10">
                                    <a class="c-button-csInq" href="/m/mypage/farPricePlanView.do" title="요금제 변경(예약) 페이지 바로가기">요금제 변경(예약)</a>
                                    <a class="c-button-csInq" href="/m/mypage/regServiceView.do" title="부가서비스 신청(변경) 페이지 바로가기">부가서비스 신청(변경)</a>
                                    <a class="c-button-csInq" href="/m/mypage/chargeView05.do" title="납부방법 변경 페이지 바로가기">납부방법 변경</a>
                                    <a class="c-button-csInq" href="/m/mypage/unpaidChargeList.do" title="요금즉시납부 페이지 바로가기">요금즉시납부</a>
                                    <a class="c-button-csInq" href="/m/mypage/usimSelfChg.do" title="유심 셀프변경 페이지 바로가기">유심 셀프변경</a>
                                    <span class="u-mr--4">등은</span>
                                    <a class="c-button-csInq" href="/m/mypage/myinfoView.do" title="마이페이지 바로가기">마이페이지</a>
                                    <span>에서  바로 처리하실 수 있습니다.</span>
                                </p>
                                <p class="csInq-info--txt"><span class="u-co-sub-4">로그인 및 정회원 인증</span>을 완료하신 후 문의해주시면, 고객님의 정보를 확인하여 보다 정확하게 안내드릴 수 있습니다.</p>
                                <p class="csInq-info--bullet u-co-sub-4 u-mt--10">
                                       회원정보 변경은 홈페이지 상담이 불가하오니, 고객센터(가입휴대폰에서 114[무료], 타사휴대폰 또는 유선전화 1899-5000[유료]) 로 문의 부탁 드립니다.
                                </p>
                                <p class="csInq-info--txt">산업안전보건법에 따라, 1:1문의 중 <span class="u-co-sub-4">욕설, 폭언, 성희롱, 위협적인 언행 등이 확인될 경우, 상담이 제한되거나 중단될 수 있습니다.</span></p>
                            </div>
                            <div class="c-form">
                                <span class="c-form__title" id="inpG">문의 유형</span>
                                <div class="c-form__group" role="group" aria-labelledby="inpG">
                                    <select class="c-select" id="qnaCtg">
                                        <option value="">문의 유형을 선택해주세요</option>
                                        <c:if test="${inquiryCategoryList ne null and !empty inquiryCategoryList}">
                                            <c:forEach items="${inquiryCategoryList}" var="inquiryCategoryList">
                                                <option value="${inquiryCategoryList.dtlCd}">${inquiryCategoryList.dtlCdNm}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>

                                    <select class="c-select" id="qnaSubCtgCd">
                                        <option value="">세부 유형을 선택해주세요</option>
                                    </select>
                                </div>
                            </div>
                            <div class="c-accordion c-accordion--type2" id="guidanceDiv" style="display:none;">
                                <div class="c-accordion__box" id="accordion1">
                                    <div class="c-accordion__item">
                                        <div class="c-accordion__head" data-acc-header>
                                            <button class="c-accordion__button" type="button" aria-expanded="false">
                                                <i class="c-icon c-icon--bang" aria-hidden="true"></i>
                                                <span>문의유형 안내</span>
                                            </button>
                                        </div>
                                        <div class="c-accordion__panel expand">
                                            <div class="c-accordion__inside">
                                                <article class="editor-wrap" id="guidance">
                                                </article>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="c-form">
                                <span class="c-form__title" id="inpG">문의 작성</span>
                                <div class="c-form__group" role="group" aria-labelledby="inpG">
                                  <input class="c-input" type="text" placeholder="제목을 입력해주세요" title="제목 입력" id="qnaSubject" maxlength="50">
                                  <div class="c-textarea-wrap">
                                    <textarea class="c-textarea u-ta-left inner-form" placeholder="문의 내용을 입력해주세요." id="qnaContent"></textarea>
                                        <div class="c-textarea-wrap__sub">
                                            <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                                            <span id="textAreaSbstSize">0/1000</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="c-form">
                                <span class="c-form__title" id="inpG">휴대폰 번호</span>
                                <div class="c-form__group" role="group" aria-labelledby="inpG">

                                <c:choose>
                                    <c:when test="${userDivision eq '01' or userDivision eq '02'}"><!-- 정회원 또는 인증받은사람 -->
                                        <select class="c-select" id="cntrMobileNo">
                                            <c:forEach items="${cntrList}" var="cntrList">
                                                <c:choose>
                                                    <c:when test="${maskingSession eq 'Y'}">
                                                        <option value="${cntrList.formatUnSvcNo}" svc_cntr_no="${cntrList.svcCntrNo}">${cntrList.formatUnSvcNo}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${cntrList.mkMobileNo}" svc_cntr_no="${cntrList.svcCntrNo}">${cntrList.mkMobileNo}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </c:when>
                                    <c:otherwise><!-- 비회원 -->
                                        <input class="c-input numOnly2" type="text" id="cntrMobileNo" name="cntrMobileNo" cntrMobileNo="" placeholder="휴대폰 번호를 입력해 주세요" title="휴대폰 번호입력" value="${cntrList[0].mkMobileNo}">
                                    </c:otherwise>
                                </c:choose>
                                </div>
                            </div>
                            <div class="c-agree">
                                <div class="c-agree__item">
                                    <input class="c-checkbox" id="qnaSmsSendYn" type="checkbox" name="qnaSmsSendYn" value="Y">
                                    <label class="c-label" for="qnaSmsSendYn">답변 완료 시 문의번호로 SMS 알림 받기</label>
                                </div>
                            </div>
                            <div class="c-button-wrap u-mt--48">
                                <button class="c-button c-button--full c-button--primary" id="saveBtn" disabled="disabled">문의 등록하기</button>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <input type="hidden" id="userDivision" name="userDivision" value="${userDivision}">
                </div>


            </div>
        </div>

        <!-- 해지 팝업 -->
        <div class="c-modal" id="cancelPopup" role="dialog" tabindex="-1" aria-describedby="applyNotAllowedTitle">
            <div class="c-modal__dialog  c-modal__dialog--alert" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="complete u-pt--0">
                            <i class="c-icon c-icon--notice" aria-hidden="true"></i>
                            <h3 class="u-fs-20 u-mt--16">
                                <b>해지 상담신청은 현재 페이지에서 불가능 합니다.</b>
                            </h3>
                        </div>
                        <p class="c-bullet c-bullet--fyr u-ta-left u-fs-14 u-mt--20">확인버튼 클릭 시 해지상담 신청 페이지로 이동합니다.</p>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <a class="c-button c-button--full c-button--primary" href="/m/mypage/cancelConsult.do">확인</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="c-modal" id="modalMypageInfo" role="dialog" tabindex="-1" aria-describedby="#modalMypageInfo__title" >
            <div class="c-modal__dialog  c-modal__dialog--alert" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="modalMypageInfo__title">마이페이지 이동 안내</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        선택하신 항목은 바로 처리하실 수 있습니다.<br/>해당 페이지로 이동하시겠습니까?
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <a class="c-button c-button--full c-button--gray" data-dialog-close >아니요</a>
                            <a class="c-button c-button--full c-button--primary" id="btnMypageLink">예</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>