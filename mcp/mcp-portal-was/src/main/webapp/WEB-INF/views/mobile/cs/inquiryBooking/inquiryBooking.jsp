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
        <script type="text/javascript" src="/resources/js/mobile/cs/inquiryBooking/inquiryBooking.js"></script>
        <script>

            var redirectUrl  = "/m/main.do";
            var resultCd = '${resultCd}';
            console.log(resultCd);

            if(resultCd == '-1') {
                MCP.alert("선불요금제는 이용할 수 없습니다", function () {
                    location.href = redirectUrl;
                });
            }

        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    상담 예약
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

            <div class="c-tabs c-tabs--type2" data-ignore="true">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button is-active">상담 예약 신청</button>
                    <button class="c-tabs__button" id="nextTab">상담 예약 결과보기</button>
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
                    <div class="csInq-info-wrap u-mt--40">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item">고객님 가능하신 날짜와 시간대를 선택하여 예약해주시면, 상담원이 1899-5000번호로 전화드립니다.</li>
                            <li class="c-text-list__item">예약하신 시간대에 두번 전화드리며, 받지 않으실 경우 상담예약은 취소됩니다.</li>
                            <li class="c-text-list__item">상담원과 통화가 이뤄지지 않으면 업무처리가 되지 않습니다. 필요하실 경우 고객센터 114(무료)로 연락부탁드립니다.</li>
                            <li class="c-text-list__item">문의내용을 구체적으로 남겨주시면, 더 빠르게 도움드릴 수 있습니다.</li>
                            <li class="c-text-list__item">
                                고객님께 연락드릴 상담원은 누군가의 소중한 가족입니다.<br>
                                산업안전 보건법의 고객응대 근로자 보호조치에 따라 고객님과 통화내용은 녹음되며, 고객응대 상담원에게 폭언, 폭행 욕설 시에는 상담이 종료될 수 있습니다.</li>
                            <li class="c-text-list__item u-co-mint">
                                요금제 변경, 납부방법 변경 등 단순한 업무는 아래 링크를 누르시면 바로 이용 가능하십니다.<br/>
                                <div class="u-mt--8 u-co-sub-4">
                                    <a class="c-button c-button--underline u-mb--8 u-mr--16 u-co-sub-4 u-fw--bold" href="/m/mypage/farPricePlanView.do" title="요금제 변경 페이지 바로가기">요금제 변경</a>
                                    <a class="c-button c-button--underline u-mb--8 u-mr--16 u-co-sub-4 u-fw--bold" href="/m/mypage/regServiceView.do" title="부가서비스 신청 페이지 바로가기">부가서비스 신청</a>
                                    <a class="c-button c-button--underline u-mb--8 u-mr--16 u-co-sub-4 u-fw--bold" href="/m/mypage/chargeView05.do" title="납부방법 변경 페이지 바로가기">납부방법 변경</a>
                                    <a class="c-button c-button--underline u-mb--8 u-mr--16 u-co-sub-4 u-fw--bold" href="/m/mypage/unpaidChargeList.do" title="청구요금 납부 페이지 바로가기">청구요금 납부</a>
                                    <a class="c-button c-button--underline u-mb--8 u-mr--16 u-co-sub-4 u-fw--bold" href="/m/mypage/usimSelfChg.do" title="유심 셀프변경 페이지 바로가기">유심 셀프변경</a>
                                </div>
                            </li>
                            <li class="c-text-list__item">간단한 문의는 자주 묻는 질문 또는 우측 하단 챗봇을 이용하시면 빠른 답변을 받아보실 수 있습니다. 많은 이용 부탁 드립니다.</li>
                            <li class="c-text-list__item">해외 체류 시에는 발신이 불가하므로 해외에서 상담이 필요하신 경우 1:1문의게시판 이용 또는 (+82) 2-810-6300(유료)로 연락 부탁 드립니다.</li>
                        </ul>
                    </div>
                    <div class="c-form">
                        <span class="c-form__title" id="selCtnTitle">문의 번호</span>
                        <div class="c-form__group" role="group" aria-labelledby="selCtnTitle">
                            <select class="c-select u-mt--0" id="svcCntrNo">
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
                    </div>

                    <div class="c-form">
                        <span class="c-form__title" id="selCsResDttmTitle">상담 요청 일시</span>
                        <div class="c-form__group" role="group" aria-labelledby="selCsResDttmTitle">
                            <div class="c-form c-form--datepicker">
                                <div class="c-form__input u-mt--0">
                                    <input class="c-input date-input" id="bookingDate" type="text" placeholder="상담 예약일(YYYY-MM-DD)" title="상담 예약일" maxlength="10" value="" pattern="yyyy-MM-dd" readonly/>
                                    <label class="c-form__label" for="bookingDate">상담 예약일</label>
                                    <button class="c-button">
                                        <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="c-input-wrap" style="display: none;">
                                <button class="c-button c-button--full c-button--white" id="timeChk" onclick="javascript:bookingTime();">상담 시간 선택</button>
                                <input type="hidden" id="hiddenTimeChk" value="">
                            </div>
                        </div>
                    </div>

                    <div class="c-form">
                        <span class="c-form__title" id="selCsResQnaCtgTitle">유형 선택</span>
                        <div class="c-form__group" role="group" aria-labelledby="selCsResQnaCtgTitle">
                            <select class="c-select" id="qnaCtg">
                                <option value="">문의 유형을 선택해주세요</option>
                                <c:if test="${inquiryCategoryList ne null and !empty inquiryCategoryList}">
                                    <c:forEach items="${inquiryCategoryList}" var="inquiryCategoryList">
                                        <option value="${inquiryCategoryList.cdVal}">${inquiryCategoryList.cdDsc}</option>
                                    </c:forEach>
                                </c:if>
                            </select>

                            <select class="c-select" id="qnaSubCtg">
                                <option value="">세부 유형을 선택해주세요</option>
                            </select>
                        </div>
                    </div>

                    <div class="c-form">
                        <span class="c-form__title" id="inpInquiryTitle">문의 하기</span>
                        <div class="c-form__group" role="group" aria-labelledby="inpInquiryTitle">
                            <input class="c-input" type="text" placeholder="제목을 입력해주세요" title="문의 제목 입력" id="qnaSubject" maxlength="50">

                            <div class="c-textarea-wrap">
                                <textarea class="c-textarea u-ta-left inner-form" placeholder="문의 내용을 입력해주세요" title="문의 내용 입력" id="qnaContent"></textarea>
                                <div class="c-textarea-wrap__sub">
                                    <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                                    <span id="textAreaSbstSize">0/1000자</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="c-button-wrap u-mt--48">
                        <button class="c-button c-button--full c-button--primary" id="saveBtn" disabled>문의 등록하기</button>
                    </div>

                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>