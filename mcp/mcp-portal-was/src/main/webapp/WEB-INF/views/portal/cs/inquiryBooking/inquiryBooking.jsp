<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">고객센터 > 상담예약 > 상담예약신청</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
        <script type="text/javascript" src="/resources/js/portal/cs/inquiryBooking/inquiryBooking.js"></script>
        <script>
            <%-- 달력 min(오늘), max(익월달) 날짜 설정 --%>
            var today = new Date();
            var tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate()+1);
            var endDate = new Date(today.getFullYear(), today.getMonth()+2, 0);

            KTM.datePicker('.flatpickr', {
                dateFormat: 'Y.m.d',  //날짜 형식
                minDate: tomorrow,
                maxDate: endDate
            });
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">상담 예약</h2>
            </div>
            <div class="c-tabs c-tabs--type1">
                <div class="c-tabs__list">
                    <a class="c-tabs__button is-active" id="tab1"  href="/cs/csInquiryBooking.do" aria-selected="true" title="상담 예약 신청">상담 예약 신청</a>
                    <a class="c-tabs__button" id="tab2" href="/cs/csInquiryBookingHist.do" aria-selected="false" title="상담 예약 결과보기">상담 예약 결과보기</a>
                </div>
            </div>
            <div class="c-tabs__panel u-block">
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
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-mt--0">고객님 가능하신 날짜와 시간대를 선택하여 예약해주시면, 상담원이 1899-5000번호로 전화드립니다.</li>
                            <li class="c-text-list__item u-mt--8">예약하신 시간대에 두번 전화드리며, 받지 않으실 경우 상담예약은 취소됩니다.</li>
                            <li class="c-text-list__item u-mt--8">상담원과 통화가 이뤄지지 않으면 업무처리가 되지 않습니다. 필요하실 경우 고객센터 114(무료)로 연락부탁드립니다.</li>
                            <li class="c-text-list__item u-mt--8">문의내용을 구체적으로 남겨주시면, 더 빠르게 도움드릴 수 있습니다.</li>
                            <li class="c-text-list__item u-mt--8">
                                고객님께 연락드릴 상담원은 누군가의 소중한 가족입니다.<br>
                                산업안전 보건법의 고객응대 근로자 보호조치에 따라 고객님과 통화내용은 녹음되며, 고객응대 상담원에게 폭언, 폭행 욕설 시에는 상담이 종료될 수 있습니다.
                            </li>
                            <li class="c-text-list__item u-mt--8 u-co-mint">
                                요금제 변경, 납부방법 변경 등 단순한 업무는 아래 링크를 누르시면 바로 이용 가능하십니다.<br/>
                                <div class="u-mt--8 u-co-sub-4 c-text--fs17 u-fw--bold">
                                    <a class="c-text--underline" href="/mypage/farPricePlanView.do" title="요금제 변경 페이지 바로가기">요금제 변경</a>
                                    <a class="c-text--underline u-ml--16" href="/mypage/regServiceView.do" title="부가서비스 신청 페이지 바로가기">부가서비스 신청</a>
                                    <a class="c-text--underline u-ml--16" href="/mypage/chargeView05.do" title="납부방법 변경 페이지 바로가기">납부방법 변경</a>
                                    <a class="c-text--underline u-ml--16" href="/mypage/unpaidChargeList.do" title="청구요금 납부 페이지 바로가기">청구요금 납부</a>
                                    <a class="c-text--underline u-ml--16" href="/mypage/usimSelfChg.do" title="유심 셀프변경 페이지 바로가기">유심 셀프변경</a>
                                </div>
                            </li>
                            <li class="c-text-list__item u-mt--8">간단한 문의는 자주 묻는 질문 또는 우측 하단 챗봇을 이용하시면 빠른 답변을 받아보실 수 있습니다. 많은 이용 부탁 드립니다.</li>
                            <li class="c-text-list__item u-mt--8">해외 체류 시에는 발신이 불가하므로 해외에서 상담이 필요하신 경우 1:1문의게시판 이용 또는 (+82) 2-810-6300(유료)로 연락 부탁 드립니다.</li>
                        </ul>
                    </div>

                    <div class="c-form u-mt--48 u-width--460">
                        <label class="c-label" for="svcCntrNo">문의 번호</label>
                        <select class="c-select" id="svcCntrNo">
                            <c:forEach items="${poList}" var="poList">
                                <c:choose>
                                    <c:when test="${maskingSession eq 'Y'}">
                                        <option value="${poList.contractNum}">${poList.formatUnSvcNo}</option>
                                    </c:when>
                                    <c:otherwise>
                                       <option value="${poList.contractNum}">${poList.mkMobileNo}</option>
                                    </c:otherwise>
                               </c:choose>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="c-form-wrap u-mt--48">
                        <div class="c-form-group" role="group" aria-labelledby="selCsResDttmTitle">
                            <h3 class="c-group--head" id="selCsResDttmTitle">상담 요청 일시</h3>
                            <div class="c-form-row c-col2">
                                <div class="c-form-field c-form-field--datepicker">
                                    <div class="c-form__input">
                                        <input type="text" class="c-input flatpickr flatpickr-input" id="bookingDate" name="bookingDate" placeholder="YYYY.MM.DD" readonly>
                                        <label class="c-form__label" for="bookingDate">상담 예약일</label>
                                        <span class="c-button c-button--calendar">
                                            <span class="c-hidden">날짜선택</span>
                                            <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                                        </span>
                                    </div>
                                </div>
                                <div class="c-form-field">
                                    <div class="c-form__input is-readonly">
                                        <input type="hidden" id="hiddenTimeChk" value="">
                                        <button class="c-button c-button--full c-button--white" id="timeChk" onclick="javascript:bookingTime();" style="display: none;">상담 시간 선택</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="c-form-wrap u-mt--48">
                        <div class="c-form-group" role="group" aria-labelledby="selCsResQnaCtgTitle">
                            <h3 class="c-group--head" id="selCsResQnaCtgTitle">유형 선택</h3>
                            <div class="c-form-row c-col2">
                                <div class="c-form">
                                    <label class="c-label c-hidden" for="qnaCtg">문의유형 선택</label>
                                    <select class="c-select" id="qnaCtg">
                                        <option value="">문의 유형을 선택해주세요</option>
                                        <c:if test="${inquiryCategoryList ne null and !empty inquiryCategoryList}">
                                            <c:forEach items="${inquiryCategoryList}" var="inquiryCategoryList">
                                                <option value="${inquiryCategoryList.cdVal}">${inquiryCategoryList.cdDsc}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </div>
                                <div class="c-form">
                                    <label class="c-label c-hidden" for="qnaSubCtg">세부 유형 선택</label>
                                    <select class="c-select" id="qnaSubCtg">
                                        <option value="">세부 유형을 선택해주세요</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="c-form-wrap u-mt--48">
                        <div class="c-form-group" role="group" aria-labelledby="inpInquiryTitle">
                            <h3 class="c-group--head" id="inpInquiryTitle">문의 하기</h3>
                            <div class="c-form-row c-col2 u-width--460">
                                <div class="c-form">
                                    <input class="c-input" type="text" placeholder="제목을 입력해주세요" id="qnaSubject" maxlength="50">
                                    <label class="c-form__label c-hidden" for="qnaSubject">제목 입력</label>
                                </div>
                            </div>
                            <div class="c-textarea-wrap">
                                <label class="c-form__label c-hidden" for="qnaContent">문의 내용 입력</label>
                                <textarea class="c-textarea" placeholder="문의 내용을 입력해주세요" id="qnaContent"></textarea>
                                <div class="c-textarea-wrap__sub">
                                    <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                                    <span id="textAreaSbstSize">0/1000자</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="c-button-wrap u-mt--56">
                        <button class="c-button c-button--lg c-button--primary c-button--w460" id="saveBtn" disabled>문의 등록하기</button>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
