<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">1:1 상담문의 </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
         <script type="text/javascript" src="/resources/js/portal/cs/inquiry/inquiryInt.js?version=25.12.03"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">1:1 상담문의</h2>
            </div>
            <div class="c-tabs c-tabs--type1">
                <div class="c-tabs__list">
                    <a class="c-tabs__button is-active" id="tab1" href="/cs/csInquiryInt.do" aria-selected="true" title="1:1 문의 작성 선택됨">1:1 문의 작성</a>
                    <a class="c-tabs__button" id="tab2" href="/cs/csInquiryIntHist.do" aria-selected="false">내 문의 확인하기</a>
                </div>
            </div>
            <div class="c-tabs__panel u-block">
            <c:choose>
                <c:when test="${userSession eq null and authSmsDto eq null}">
                    <div class="c-row c-row--lg">
                        <div class="csInq-info-wrap">
                            <p class="csInq-info--txt u-mt--0">
                                <span class="u-co-mint">kt M모바일 서비스에 대해 궁금한 사항을 문의</span>해 주시면 답변 드리도록 하겠습니다. 주말 및 휴일에는 답변 처리가 다소 지연될 수 있습니다.
                            </p>
                            <p class="csInq-info--bullet">
                                       간단한 문의는 우측 하단 챗봇을 이용하시면 빠른 답변을 받아보실 수 있습니다. 많은 이용 부탁 드립니다.
                            </p>
                            <p class="csInq-info--rink u-mt--8">
                                <a class="c-button-csInq" href="/m/mypage/farPricePlanView.do" title="요금제 변경(예약) 페이지 바로가기">요금제 변경(예약)</a>
                                <a class="c-button-csInq" href="/m/mypage/regServiceView.do" title="부가서비스 신청(변경) 페이지 바로가기">부가서비스 신청(변경)</a>
                                <a class="c-button-csInq" href="/m/mypage/chargeView05.do" title="납부방법 변경 페이지 바로가기">납부방법 변경</a>
                                <a class="c-button-csInq" href="/m/mypage/unpaidChargeList.do" title="요금즉시납부 페이지 바로가기">요금즉시납부</a>
                                <a class="c-button-csInq" href="/m/mypage/usimSelfChg.do" title="유심 셀프변경 페이지 바로가기">유심 셀프변경</a>
                                <span class="u-ml--4">등은</span>
                                <a class="c-button-csInq" href="/m/mypage/myinfoView.do" title="마이페이지 바로가기">마이페이지</a>
                                <span>에서  바로 처리하실 수 있습니다.</span>
                            </p>
                            <p class="csInq-info--txt u-mt--10"><span class="u-co-mint">로그인 및 정회원 인증</span>을 완료하신 후 문의해주시면, 고객님의 정보를 확인하여 보다 정확하게 안내드릴 수 있습니다.</p>
                            <p class="csInq-info--bullet">
                                   회원정보 변경은 홈페이지 상담이 불가하오니, 고객센터(가입휴대폰에서 114[무료], 타사휴대폰 또는 유선전화 1899-5000[유료]) 로 문의 부탁 드립니다.
                            </p>
                            <p class="csInq-info--txt u-mb--0">산업안전보건법에 따라, 1:1문의 중 <span class="u-co-mint">욕설, 폭언, 성희롱, 위협적인 언행 등이 확인될 경우, 상담이 제한되거나 중단될 수 있습니다.</span></p>
                        </div>

                        <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>
                        <div class="c-form-wrap u-mt--48">
                            <span class="c-form__title">약관동의</span>
                            <div class="c-agree">
                                <div class="c-agree__item">
                                    <button class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMSINF&cdGroupId2=TERMSINF03');">
                                        <span class="c-hidden">개인정보 수집이용 동의 상세팝업 보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                    <div class="c-agree__inner">
                                        <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">
                                        <label class="c-label" for="chkAgree">개인정보 수집이용 동의</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="c-button-wrap u-mt--56">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" id="applyBtn" disabled="disabled">확인</button>
                        </div>

                        <div class="c-button-wrap u-mt--40">
                            <a class="c-button c-button--underline u-mr--12 u-co-black" href="/loginForm.do">kt M모바일계정 로그인</a>
                            <a class="c-button c-button--underline u-ml--12 u-co-black" href="/join/fstPage.do">회원가입</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="c-row c-row--lg">
                        <c:if test="${empty maskingSession}">
                                <div class="masking-badge-wrap">
                                <div class="masking-badge">
                                    <a class="masking-badge__button" href="/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
                                        <i class="masking-badge__icon" aria-hidden="true"></i>
                                         <p>가려진(*)<br />정보보기</p>
                                       </a>
                                </div>
                            </div>
                        </c:if>
                        <div class="csInq-info-wrap">
                            <p class="csInq-info--txt u-mt--0">
                                <span class="u-co-mint">kt M모바일 서비스에 대해 궁금한 사항을 문의</span>해 주시면 답변 드리도록 하겠습니다. 주말 및 휴일에는 답변 처리가 다소 지연될 수 있습니다.
                            </p>
                            <p class="csInq-info--bullet">
                                       간단한 문의는 우측 하단 챗봇을 이용하시면 빠른 답변을 받아보실 수 있습니다. 많은 이용 부탁 드립니다.
                            </p>
                            <p class="csInq-info--rink u-mt--8">
                                <a class="c-button-csInq" href="/m/mypage/farPricePlanView.do" title="요금제 변경(예약) 페이지 바로가기">요금제 변경(예약)</a>
                                <a class="c-button-csInq" href="/m/mypage/regServiceView.do" title="부가서비스 신청(변경) 페이지 바로가기">부가서비스 신청(변경)</a>
                                <a class="c-button-csInq" href="/m/mypage/chargeView05.do" title="납부방법 변경 페이지 바로가기">납부방법 변경</a>
                                <a class="c-button-csInq" href="/m/mypage/unpaidChargeList.do" title="요금즉시납부 페이지 바로가기">요금즉시납부</a>
                                <a class="c-button-csInq" href="/m/mypage/usimSelfChg.do" title="유심 셀프변경 페이지 바로가기">유심 셀프변경</a>
                                <span class="u-ml--4">등은</span>
                                <a class="c-button-csInq" href="/m/mypage/myinfoView.do" title="마이페이지 바로가기">마이페이지</a>
                                <span>에서  바로 처리하실 수 있습니다.</span>
                            </p>
                            <p class="csInq-info--txt u-mt--10"><span class="u-co-mint">로그인 및 정회원 인증</span>을 완료하신 후 문의해주시면, 고객님의 정보를 확인하여 보다 정확하게 안내드릴 수 있습니다.</p>
                            <p class="csInq-info--bullet">
                                   회원정보 변경은 홈페이지 상담이 불가하오니, 고객센터(가입휴대폰에서 114[무료], 타사휴대폰 또는 유선전화 1899-5000[유료]) 로 문의 부탁 드립니다.
                            </p>
                            <p class="csInq-info--txt u-mb--0">산업안전보건법에 따라, 1:1문의 중 <span class="u-co-mint">욕설, 폭언, 성희롱, 위협적인 언행 등이 확인될 경우, 상담이 제한되거나 중단될 수 있습니다.</span></p>
                        </div>
                        <div class="c-form-wrap u-mt--48">
                            <div class="c-form-group" role="group" aria-labelledby="selInquiryHead">
                                <h3 class="c-group--head" id="selInquiryHead">유형선택</h3>
                                <div class="c-form-row c-col2">
                                    <div class="c-form">
                                        <label class="c-label c-hidden" for="qnaCtg">문의유형 선택</label>
                                        <select class="c-select" id="qnaCtg">
                                            <option value="">문의 유형을 선택해주세요</option>
                                            <c:if test="${inquiryCategoryList ne null and !empty inquiryCategoryList}">
                                                <c:forEach items="${inquiryCategoryList}" var="inquiryCategoryList">
                                                    <option value="${inquiryCategoryList.dtlCd}">${inquiryCategoryList.dtlCdNm}</option>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </div>
                                    <div class="c-form">
                                        <label class="c-label c-hidden" for="qnaSubCtgCd">세부 유형 선택</label>
                                        <select class="c-select" id="qnaSubCtgCd">
                                            <option value="">세부 유형을 선택해주세요</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="c-box c-box--type1 u-mt--20" id="guidance" style="display:none;">

                        </div>
                        <div class="c-form-wrap u-mt--48">
                            <div class="c-form-group" role="group" aria-labelledby="inpInquiryHead">
                                <h3 class="c-group--head" id="inpInquiryHead">문의하기</h3>
                                <div class="c-form-row c-col2 u-width--460">
                                    <div class="c-form">
                                        <input class="c-input" type="text" placeholder="제목을 입력해주세요." id="qnaSubject" maxlength="50">
                                        <label class="c-form__label c-hidden" for="qnaSubject">제목 입력</label>
                                    </div>
                                </div>
                                <div class="c-textarea-wrap">
                                    <label class="c-form__label c-hidden" for="qnaContent">문의 내용 입력</label>
                                    <textarea class="c-textarea" placeholder="문의 내용을 입력해주세요." id="qnaContent"></textarea>
                                    <div class="c-textarea-wrap__sub">
                                        <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                                        <span id="textAreaSbstSize">0/1000</span>
                                    </div>
                                </div>
                                <div class="c-form-row c-col2 u-width--460 u-mt--16">
                                    <div class="c-form">
                                        <label class="c-label c-hidden" for="cntrMobileNo">휴대폰 번호 선택</label>
                                        <c:choose>
                                            <c:when test="${userDivision eq '01' or userDivision eq '02'}">
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
                                            <c:otherwise>
                                                <input class="c-input numOnly2" type="text" id="cntrMobileNo" name="cntrMobileNo" cntrMobileNo="" placeholder="휴대폰 번호를 입력해 주세요" title="휴대폰 번호입력" maxlength="11" value="${cntrList[0].mkMobileNo}">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="c-form-wrap u-mt--48">
                            <div class="c-form-group" role="group"
                                aria-labelledby="#chkNoticeHead">
                                <h3 class="c-group--head" id="chkNoticeHead">알림 설정</h3>
                                <div class="c-checktype-row">
                                    <input class="c-checkbox" id="qnaSmsSendYn" type="checkbox" name="qnaSmsSendYn" value="Y">
                                    <label class="c-label" for="qnaSmsSendYn">답변 완료 시 문의번호로 SMS 알림 받기</label>
                                </div>
                            </div>
                        </div>
                        <div class="c-button-wrap u-mt--56">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" id="saveBtn" disabled>문의 등록하기</button>
                        </div>
                    </div>
                    <input type="hidden" id="userDivision" name="userDivision" value="${userDivision}">
                </c:otherwise>
            </c:choose>
            </div>
        </div>

        <!-- 해지 팝업 -->
        <div class="c-modal c-modal--md" id="cancelPopup" role="dialog" tabindex="-1" aria-modal="true">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="complete u-ta-center">
                            <i class="c-icon c-icon--notice" aria-hidden="true"></i>
                            <h3 class="u-fs-24 u-mt--16">
                                <b>해지 상담신청은 현재 페이지에서 불가능 합니다.</b>
                            </h3>
                        </div>
                        <p class="c-bullet c-bullet--fyr u-fs-16 u-mt--32">확인버튼 클릭 시 해지상담 신청 페이지로 이동합니다.</p>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <a class="c-button c-button--full c-button--primary" href="/mypage/cancelConsult.do">확인</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="c-modal c-modal--sm" id="modalMypageInfo"  role="dialog" tabindex="-1" aria-modal="true"   aria-labelledby="#modalMypageInfo__title" >
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content"   style="width: 39rem;"  >
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
                        <div class="c-button-wrap">
                            <a class="c-button c-button--full c-button--gray" data-dialog-close >아니요</a>
                            <a class="c-button c-button--full c-button--primary" id="btnMypageLink">예</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
