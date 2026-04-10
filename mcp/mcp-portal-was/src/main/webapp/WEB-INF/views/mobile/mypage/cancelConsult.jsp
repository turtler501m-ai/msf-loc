<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/popup/diAuth.js"></script>
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/mypage/cancelConsult.js?version=25.09.30"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <input type="hidden" id="isAuth" name="isAuth" value="">
        <input type="hidden" id="underAge" value="${underAge}">

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">해지 상담 신청<button class="header-clone__gnb"></button></h2>
            </div>
            <div class="c-tabs c-tabs--type2">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list>
                    <button class="c-tabs__button" data-tab-header="#tab1-panel">해지예약 접수</button>
                    <button class="c-tabs__button" data-tab-header="#tab2-panel">접수이력 확인하기</button>
                </div>
                <div class="c-tabs__panel" id="tab1-panel">
                    <!-- 해지 상담 신청 시작 -->
                    <div class="cancel-reason-content">
                        <div class="cancel-reason-info">
                            <h3 class="c-form__title--type2 u-mt--26" data-tpbox="#cancelReasonTooltip">해지 신청 안내 <i class="c-icon c-icon--tooltip" aria-hidden="true"></i></h3>
                            <div class="c-tooltip-box" id="cancelReasonTooltip">
                                <h3 class="c-tooltip-box__title c-hidden">해지 신청 안내 설명</h3>
                                <div class="c-tooltip-box__content">
                                    <div class="c-tooltip-box__title">해지 신청 안내</div>
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item">대리점을 통해 개통하신 고객님은 실 사용일수 181일 이내인 경우, 대리점을 통해 추가 확인이 필요할 수 있습니다.</li>
                                        <li class="c-text-list__item">미납요금이 있을 경우 납부 후 해지가 가능합니다.</li>
                                        <li class="c-text-list__item">함께쓰기 결합을 이용중이신 경우 해지 시 일할계산되어 초과요금 청구될 수 있습니다.</li>
                                        <li class="c-text-list__item">일거양득/데이터쉐어링/워치 요금제를 통해 회선을 함께 이용중이신 경우 모회선 해지 시 자회선 이용이 불가하며 일거양득의 경우 모회선 해지일부터 자회선의 요금이 부과됩니다.(자회선 해지가 필요할 경우 추가 해지 접수 필요)</li>
                                    </ul>
                                </div>
                                <button class="c-tooltip-box__close" data-tpbox-close="">
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>
                            <p class="c-form__title-sub">해지 접수 시 고객님께 안내 전화를 드리며, 본인 여부 및 할인 반환금(위약금) 등 최종 확인 후 처리됩니다. <span>(해지 신청 접수 는 본인 명의의 회선만 가능합니다.)</span></p>
                            <ul class="cancel-reason-step">
                                <li class="cancel-reason-step__item">
                                    <em>1</em>
                                    <p>필수 확인사항 동의, 본인인증</p>
                                </li>
                                <li class="cancel-reason-step__item">
                                    <em>2</em>
                                    <p>해지 신청 접수</p>
                                </li>
                                <li class="cancel-reason-step__item">
                                    <em>3</em>
                                    <p class="u-co-sub-4">상담사 통화</p>
                                </li>
                                <li class="cancel-reason-step__item">
                                    <em>4</em>
                                    <p>해지완료</p>
                                </li>
                            </ul>
                            <p class="c-bullet c-bullet--fyr u-fs-14 u-mt--28">본인인증이 불가하거나, 미성년자 또는 명의자 외 대리인이 해지를 원하실 경우 고객센터로 문의 부탁드립니다.</p>
                            <h3 class="c-form__title--type2">해지 전 필수 확인 사항</h3>
                            <div class="c-accordion c-accordion--type5 acc-agree">
                                <div class="c-accordion__box">
                                    <div class="c-accordion__item">
                                        <div class="c-accordion__head">
                                            <div class="c-accordion__check">
                                                <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                                                <label class="c-label" for="btnStplAllCheck">본인은 해지 시 혜택 소멸 및 하기 안내사항 에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 전체동의는 해당 개별 항목의 상세 내용을 모두 읽어야 가능합니다.</span></label>
                                            </div>
                                            <button class="c-accordion__button u-ta-right is-active" type="button" aria-expanded="false" data-acc-header="#acc_agreeA1">
                                                <div class="c-accordion__trigger"> </div>
                                            </button>
                                        </div>
                                        <div class="c-accordion__panel expand open" id="acc_agreeA1">
                                            <div class="c-accordion__inside">
                                                <div class="c-agree__item">
                                                    <input class="c-checkbox c-checkbox--type2 _agree" id="benefitAgreeFlag" type="checkbox" value="Y">
                                                    <label class="c-label" for="benefitAgreeFlag">혜택 소멸 <span class="u-co-gray">(필수)</span>
                                                    </label>
                                                    <button class="c-button c-button--xsm" data-dialog-trigger="#modalTerms1">
                                                        <span class="c-hidden">상세보기</span>
                                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                                <div class="c-agree__item">
                                                    <input class="c-checkbox c-checkbox--type2 _agree" id="clauseCntrDelFlag" type="checkbox" value="Y">
                                                    <label class="c-label" for="clauseCntrDelFlag">해지 고객 정보 삭제 <span class="u-co-gray">(필수)</span>
                                                    </label>
                                                    <button class="c-button c-button--xsm" data-dialog-trigger="#modalTerms2">
                                                        <span class="c-hidden">상세보기</span>
                                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                                <div class="c-agree__item">
                                                    <input class="c-checkbox c-checkbox--type2 _agree" id="etcAgreeFlag" type="checkbox" value="Y">
                                                    <label class="c-label" for="etcAgreeFlag">기타 안내 <span class="u-co-gray">(필수)</span>
                                                    </label>
                                                    <button class="c-button c-button--xsm" data-dialog-trigger="#modalTerms3">
                                                        <span class="c-hidden">상세보기</span>
                                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="c-accordion c-accordion--type5 acc-agree u-mt--0">
                                <div class="c-accordion__box">
                                    <div class="c-accordion__item u-bd--0">
                                        <div class="c-accordion__head">
                                            <div class="c-accordion__check">
                                                <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">
                                                <label class="c-label" for="chkAgree">
                                                                                      온라인 해지 접수는 <span class="u-fs-16 u-co-red u-inline">당일 해지가 불가능하며, 처리가 지연될 수 있습니다.</span><br/>
                                                                                      빠른 해지를 원하시는 경우 KT엠모바일 고객센터 114번으로 연락 주시기 바랍니다.
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr class="c-hr c-hr--type1 c-expand">
                            <h3 class="c-form__title--type2">본인인증 방법 선택</h3>
                            <input class="c-checkbox" type="checkbox" id="niceChkAgree" name="niceChkAgree">
                            <label class="c-label u-fs-16 u-mt--8" for="niceChkAgree">본인인증 절차 동의</label>
                            <ul class="c-text-list c-bullet c-bullet--dot u-fs-14 u-mt--16">
                                <li class="c-text-list__item u-co-gray-9">안전한 서비스 이용 및 고객님의 개인정보 보호를 위해 본인 인증을 받으신 고객분만 이용이 가능합니다. 서비스 이용을 위해서 본인확인 절차를 진행해 주세요.</li>
                            </ul>
                            <div class="c-button-wrap u-mt--28">
                                <button class="c-button--center _btnNiceAuthBut" data-online-auth-type="M"><i class="c-icon c-icon--phone" aria-hidden="true"></i>휴대폰<br />인증하기</button>
                                <button class="c-button--center _btnNiceAuthBut" data-online-auth-type="C"><i class="c-icon c-icon--card" aria-hidden="true"></i>신용카드<br />인증하기</button>
                            </div>
                        </div>
                        <!-- 본인인증 완료 후 나오는 영역 -->
                        <div class="cancel-reason-form" style="display: none;">
                            <h3 class="c-form__title--type2">해지 신청 정보 입력</h3>
                            <div class="c-form">
                                <div class="c-form__input ">
                                    <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" value="${searchVO.userName}"  maxlength="60" readonly>
                                    <label class="c-form__label" for="cstmrName">이름</label>
                                </div>
                            </div>
                            <div class="c-form-field _isDefault">
                                <div class="c-form__input-group is-readonly">
                                    <div class="c-form__input c-form__input-division">
                                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="text" maxlength="6" value="${birthday}" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" readonly>
                                        <span>-</span>
                                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="off" type="text" maxlength="7" value="${gender}●●●●●●" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" readonly>
                                        <label class="c-form__label" for="cstmrNativeRrn01">생년월일</label>
                                    </div>
                                </div>
                            </div>
                            <div class="c-form">
                                <div class="c-form__input-group has-value">
                                    <div class="c-form__input c-form__input-division">
                                        <input class="c-input--div3 onlyNum" name="receiveMobileNo1" id="receiveMobileNo1" type="tel" maxlength="3" placeholder="숫자입력" onkeyup="nextFocus(this, 3, 'receiveMobileNo2');" autocomplete="off"> <span>-</span>
                                        <input class="c-input--div3 onlyNum" name="receiveMobileNo2" id="receiveMobileNo2" type="tel" maxlength="4" placeholder="숫자입력" onkeyup="nextFocus(this, 4, 'receiveMobileNo3');" autocomplete="off"> <span>-</span>
                                        <input class="c-input--div3 onlyNum" name="receiveMobileNo3" id="receiveMobileNo3" type="tel" maxlength="4" placeholder="숫자입력" autocomplete="off">
                                        <label class="c-form__label" for="receiveMobileNo1">연락가능 연락처</label>
                                    </div>
                                </div>
                                <p class="c-bullet c-bullet--fyr u-fs-14 u-co-red">명의자 분과 통화 가능한 국내 전화번호를 남겨주세요.<br/>상담사 전화(1899-5000번)을 받지 못하신 경우 접수가 취소될 수 있습니다.</p>
                            </div>
                            <div class="c-form">
                                <div class="c-form__select has-value">
                                    <select class="c-select" id="cancelMobileNo" name="cancelMobileNo">
                                        <c:forEach items="${cntrList}" var="item">
                                            <c:choose>
                                                <c:when test="${maskingSession eq 'Y'}">
                                                    <option value="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.formatUnSvcNo}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.cntrMobileNo}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <option value="manual">직접입력</option>
                                    </select>
                                    <label class="c-form__label" for="cancelMobileNo">해지 신청 번호 선택</label>
                                </div>
                            </div>
                            <!-- 직접 입력 선택 시 나오는 영역 -->
                            <div class="c-form" id="cancelMobileNoInputArea" style="display:none;">
                                <div class="c-form__input-group has-value">
                                    <div class="c-form__input c-form__input-division has-value">
                                        <input class="c-input--div3 onlyNum" name="cancelMobileNo1" id="cancelMobileNo1" type="tel" maxlength="3" placeholder="숫자입력" onkeyup="nextFocus(this, 3, 'cancelMobileNo2');" autocomplete="off"> <span>-</span>
                                        <input class="c-input--div3 onlyNum" name="cancelMobileNo2" id="cancelMobileNo2" type="tel" maxlength="4" placeholder="숫자입력" onkeyup="nextFocus(this, 4, 'cancelMobileNo3');" autocomplete="off"> <span>-</span>
                                        <input class="c-input--div3 onlyNum" name="cancelMobileNo3" id="cancelMobileNo3" type="tel" maxlength="4" placeholder="숫자입력" autocomplete="off">
                                        <label class="c-form__label" for="cancelMobileNo1">해지 신청 번호</label>
                                    </div>
                                </div>
                            </div>
                            <!-- 해지 하시는 이유 시작 -->
                            <div class="cancel-reason-list-wrap">
                                <hr class="c-hr c-hr--type1 c-expand">
                                <h3 class="cancel-reason-list__title"><i class="c-icon c-icon--cancel-reason" aria-hidden="true"></i>해지하시는 이유를 알려주세요!</h3>
                                <div class="cancel-reason-list">
                                    <ul class="cancel-reason__list">
                                        <li class="cancel-reason__item">
                                            <h4 class="cancel-reason__item-title" id="cancelReasonList1">kt M모바일을 해지하시는 이유는 무엇인가요?</h4>
                                            <div class="c-form-wrap">
                                                <div class="c-form-group" role="group" aira-labelledby="cancelReasonList1">
                                                    <div class="c-check-wrap c-check-wrap--column">
                                                        <input class="c-radio" id="survey1_01" type="radio" name="survey1Cd" value="01">
                                                        <label class="c-label" for="survey1_01">요금 부담이 커서</label>
                                                        <div class="cancel-reason-message" style="display:none;">
                                                            <p class="cancel-reason-message__title"><span>잠깐!</span> 이렇게 해보시면 어때요?</p>
                                                            <p class="cancel-reason-message__desc">현재 사용하고 계시는 요금제보다 저렴한 요금제를 찾아보시는 게 어떨까요?</p>
                                                            <div class="c-button-wrap c-button-wrap--column u-mt--17">
                                                                <a class="c-button c-button--h40 c-button--mint" href="/m/rate/rateList.do" title="요금제 소개 바로가기">요금제 소개 바로가기</a>
                                                                <a class="c-button c-button--h40 c-button--mint" href="/m/rate/rateComp.do" title="요금제 비교 바로가기">요금제 비교 바로가기</a>
                                                            </div>
                                                        </div>
                                                        <input class="c-radio" id="survey1_02" type="radio" name="survey1Cd" value="02">
                                                        <label class="c-label" for="survey1_02">데이터/통화 혜택이 부족해서</label>
                                                        <div class="cancel-reason-message" style="display:none;">
                                                            <p class="cancel-reason-message__title"><span>잠깐!</span> 이렇게 해보시면 어때요?</p>
                                                            <p class="cancel-reason-message__desc">kt M모바일에서는 결합 등의 서비스를 통해 추가 혜택을 제공하고 있어요! 혜택을 확인해보세요.</p>
                                                            <div class="c-button-wrap c-button-wrap--column u-mt--15">
                                                                <a class="c-button c-button--h40 c-button--mint" href="/m/content/combineSelf.do" title="아무나 SOLO 결합 바로가기">아무나 SOLO 결합 바로가기</a>
                                                            </div>
                                                        </div>
                                                        <input class="c-radio" id="survey1_03" type="radio" name="survey1Cd" value="03">
                                                        <label class="c-label" for="survey1_03">서비스 품질 문제(통화끊김, 속도 등)</label>
                                                        <div class="cancel-reason-message" style="display:none;">
                                                            <p class="cancel-reason-message__title" id="cancelReasonListMessage1"><span>(필수)</span> 개선이 필요한 부분에 대해 알려주세요.</p>
                                                            <div class="c-form-wrap u-mt--12">
                                                                <div class="c-form-group" role="group" aira-labelledby="cancelReasonListMessage1">
                                                                    <div class="c-checktype-column">
                                                                        <input class="c-radio" id="survey2_01" type="radio" name="survey2Cd" value="01">
                                                                        <label class="c-label" for="survey2_01">통화 품질이 불만족스러웠다</label>
                                                                        <input class="c-radio" id="survey2_02" type="radio" name="survey2Cd" value="02">
                                                                        <label class="c-label" for="survey2_02">고객센터 응대가 미흡했다</label>
                                                                        <input class="c-radio" id="survey2_03" type="radio" name="survey2Cd" value="03">
                                                                        <label class="c-label u-mb--0" for="survey2_03">멤버십/부가 혜택이 부족했다</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <input class="c-radio" id="survey1_04" type="radio" name="survey1Cd" value="04">
                                                        <label class="c-label" for="survey1_04">단순 사용 종료</label>
                                                        <input class="c-radio" id="survey1_05" type="radio" name="survey1Cd" value="05">
                                                        <label class="c-label" for="survey1_05">기타(직접입력) <span class="u-co-gray">(선택)</span></label>
                                                        <div class="c-form u-fs-0" style="display:none;">
                                                            <label class="c-hidden" for="survey1Text">기타(직접입력)</label>
                                                            <textarea class="c-textarea u-mb--8" id="survey1Text" placeholder="자유롭게 적어주세요.(100자 이내)"></textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="cancel-reason__item">
                                            <h4 class="cancel-reason__item-title" id="cancelReasonList2">kt M모바일 서비스에 만족하셨나요?</h4>
                                            <div class="c-form-wrap">
                                                <div class="c-form-group" role="group" aira-labelledby="cancelReasonList2">
                                                    <div class="c-check-wrap c-check-wrap--column">
                                                        <input class="c-radio" id="surveyScore_5" type="radio" name="surveyScore" value="5">
                                                        <label class="c-label" for="surveyScore_5">매우 만족</label>
                                                        <input class="c-radio" id="surveyScore_4" type="radio" name="surveyScore" value="4">
                                                        <label class="c-label" for="surveyScore_4">만족</label>
                                                        <input class="c-radio" id="surveyScore_3" type="radio" name="surveyScore" value="3">
                                                        <label class="c-label" for="surveyScore_3">보통</label>
                                                        <input class="c-radio" id="surveyScore_2" type="radio" name="surveyScore" value="2">
                                                        <label class="c-label" for="surveyScore_2">불만족</label>
                                                        <input class="c-radio" id="surveyScore_1" type="radio" name="surveyScore" value="1">
                                                        <label class="c-label" for="surveyScore_1">매우 불만족</label>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="cancel-reason__item">
                                            <h4 class="cancel-reason__item-title" id="cancelReasonList3">마지막으로 저희 통신사에 바라는 점이나 개선사항이 있다면 자유롭게 적어주세요.<span>(선택)</span></h4>
                                            <div class="c-form u-fs-0">
                                                <label class="c-hidden" for="surveySuggestion">통신사에 바라는 점이나 개선사항</label>
                                                <textarea class="c-textarea" id="surveySuggestion" placeholder="자유롭게 적어주세요.(100자 이내)"></textarea>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <!-- 해지 하시는 이유 끝 -->
                            <div class="c-button-wrap u-mt--48 u-mb--32">
                                <button class="c-button c-button--full c-button--gray">취소</button>
                                <button class="c-button c-button--full c-button--primary is-disabled" id="btnCancelConsult">해지신청</button>
                            </div>
                        </div>
                    </div>
                    <!-- 해지 상담 신청 끝 -->
                </div>
                <div class="c-tabs__panel" id="tab2-panel">
                    <h4 class="u-fs-18 u-fw--bold u-mb--6">
                        접수 상태가 <b class="u-co-sub-4">'처리 중'</b>이면, 상담원이 해지 접수를 받아 처리하고 있는 중입니다.
                    </h4>

                    <div class="c-table">
                        <table class="u-mt--16" id="dataArea">
                            <caption>해지신청 접수이력</caption>
                            <colgroup>
                                <col style="width: 19.5%">
                                <col style="width: 30.5%">
                                <col>
                                <col style="width: 23%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">고객명</th>
                                    <th scope="col">해지 신청번호</th>
                                    <th scope="col">해지 신청일</th>
                                    <th scope="col">접수상태</th>
                                </tr>
                            </thead>
                            <tbody id="liDataArea">
                                <c:choose>
                                    <c:when test="${!empty cancelConsultList}">
                                        <c:forEach var="cancelConsultItem" varStatus="status" items="${cancelConsultList}">
                                            <tr>
                                                <td class="u-ta-center">${cancelConsultItem.cstmrName}</td>
                                                <td class="u-ta-center">${cancelConsultItem.cancelMobileNo}</td>
                                                <td class="u-ta-center">
                                                    <fmt:formatDate value="${cancelConsultItem.regstDttm}" pattern="yyyy-MM-dd"/>
                                                </td>
                                                <td class="u-ta-center">
                                                    <c:choose>
                                                        <c:when test="${cancelConsultItem.procCd == 'RC'}">접수완료</c:when>
                                                        <c:when test="${cancelConsultItem.procCd == 'RQ'}">처리중</c:when>
                                                        <c:when test="${cancelConsultItem.procCd == 'CP'}">처리완료</c:when>
                                                        <c:when test="${cancelConsultItem.procCd == 'BK'}">접수취소</c:when>
                                                        <c:otherwise>접수완료</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="4">
                                                <div class="c-nodata">
                                                    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                                    <p class="c-noadat__text">접수이력이 없습니다.</p>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- 혜택 소멸 (필수) -->
        <div class="c-modal" id="modalTerms1" role="dialog" tabindex="-1" aria-describedby="#modalTerms1Title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="#modalTerms1Title">혜택 소멸 (필수)</h1>
                        <button class="c-button" data-dialog-close="">
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body" data-scroll-required>
                        <ul class="c-text-list c-bullet c-bullet--number">
                            <li class="c-text-list__item">kt M모바일의 기본료 할인혜택 및 음성/문자/데이터 무료 추가 제공혜택이 소멸 됩니다.</li>
                            <li class="c-text-list__item">해지 시 kt M모바일 제휴서비스가 제공되지 않습니다.</li>
                        </ul>
                    </div>
                    <div class="c-modal__footer">
                        <button class="c-button c-button--full c-button--primary" data-dialog-close data-agree-btn>동의 후 닫기</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 해지 고객 정보 삭제 (필수) -->
        <div class="c-modal" id="modalTerms2" role="dialog" tabindex="-1" aria-describedby="#modalTerms2Title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="#modalTerms2Title">해지 고객 정보 삭제 (필수)</h1>
                        <button class="c-button" data-dialog-close="">
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body" data-scroll-required>
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item">
                                      케이티엠모바일은 서비스 계약 유지, 이용요금 정산, 요금 관련 분쟁 발생 시 입증을 위하여 보유기간 및 이용기간을 해지 후 6개월 이내로 합니다. 단, 아래와 같이 그 기간 도래되거나 조건이 성취되는 때까지 필요한 범위내에서 가입정보를 보관할 수 있습니다.
                                <ul class="c-text-list c-bullet c-bullet--hyphen">
                                    <li class="c-text-list__item">국세기본법 제 85조 3규정에 의하여 보관하는 성명, 고객식별번호(주민등록번호, 여권번호 등), 전화번호, 청구시 주소, 요금납부 내역(청구액, 수납액, 수납일시, 요금납부 방법) 등의 경우 5년</li>
                                    <li class="c-text-list__item">요금 관련 분쟁이 발생한 경우 보유기간 내에 해당 분쟁이 해결되지 않은 경우</li>
                                    <li class="c-text-list__item">해지고객이 이용요금을 납부하지 않은 경우</li>
                                    <li class="c-text-list__item">불법스팸 전송으로 계약 해지된 고객의 재가입을 제한하기 위하여 필요한 성명, 주민번호, 전화번호, 해지 시유의 경우 12개월 규정에 의해 보관 필요 시 그 법령에 따름 </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    <div class="c-modal__footer">
                        <button class="c-button c-button--full c-button--primary" data-dialog-close data-agree-btn>동의 후 닫기</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 기타 안내 (필수) -->
        <div class="c-modal" id="modalTerms3" role="dialog" tabindex="-1" aria-describedby="#modalTerms3Title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="#modalTerms3Title">기타 안내 (필수)</h1>
                        <button class="c-button" data-dialog-close="">
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body" data-scroll-required>
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item">해지취소(복구)의 경우 D+2일 이내 가능합니다. 단, 번호이동 해지 취소(복구)의 경우 번호이동 철회 D+3일이내 가능</li>
                            <li class="c-text-list__item">1일부터 해지 전까지 사용하신 요금은 다음달에 청구되며, 기본료 및 기본제공량 초과 사용 시 추가 요금 청구됩니다.</li>
                            <li class="c-text-list__item">약정기간 내 해지 시 발생된 위약금은 해지 후 다음달에 청구됩니다.</li>
                            <li class="c-text-list__item">다음달에 최종요금을 정산하여 수신자부담 등 현재 시점 확인이 불가능한 요금 등으로 인해 차액이 발생하면 추가 청구되거나 자동 납부됩니다.</li>
                            <li class="c-text-list__item">납부하신 금액이 사용금액을 초과한 경우 환불계좌로 돌려드립니다.</li>
                            <li class="c-text-list__item">통화내역은 해지 후 6개월 경과시 고객님에게는 제공되지 않습니다. 해지 후 단말기 할부 유지할 경우 청구 관련 주소 등 정보 변경 시에는 반드시 변경처리하셔야 체납과 같은 불이익이 발생하지 않습니다.</li>
                        </ul>
                        <p class="c-bullet c-bullet--fyr u-fs-14 u-mt--16">고객정보 보관 6개월 기준</p>
                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                            <li class="c-text-list__item">고객님의 요청으로 전산 처리된 날로부터 6개월</li>
                            <li class="c-text-list__item">해지 후 미납 요금이 있는 경우 미납 요금이 전액 납부 완료된 날로부터 6개월</li>
                            <li class="c-text-list__item">직권해지 고객님의 체납 요금이 전액 납부 완료된 날로부터 6개월</li>
                        </ul>
                    </div>
                    <div class="c-modal__footer">
                        <button class="c-button c-button--full c-button--primary" data-dialog-close data-agree-btn>동의 후 닫기</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 페이지 진입 시 팝업 -->
        <div class="c-modal" id="startModal" role="dialog" tabindex="-1" aria-describedby="startModalTitle">
            <div class="c-modal__dialog  c-modal__dialog--alert" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="startModalTitle"> </h1>
                        <button class="c-button" data-dialog-close="">
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body u-pt--0 u-ta-center">
                        <h3 class="c-heading u-fs-22">
                            <b>해지 전 잠시 <span class="u-co-sub-4">일시정지</span><br/>해두시는건 어떠세요?</b>
                        </h3>
                        <p class="c-text c-text--fs18 u-mt--24">
                                 당분간 휴대폰 사용이 어려우시다면,<br/><b class="u-co-sub-4">일시정지</b>를 신청하세요!
                        </p>
                        <p class="c-text c-text--fs18 u-mt--8">
                                 고객센터 통화 없이 온라인에서<br/>즉시 신청이 가능합니다.
                        </p>
                        <a class="c-button c-button--h48 u-round--30 c-button--full c-button--mint u-width--170 u-mt--20 u-mr--auto u-ml--auto" href="/m/mypage/suspendView01.do">일시정지 신청하기</a>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <button class="c-button c-button--full c-button--primary" data-dialog-close>해지 예약 계속</button>
                            <a class="c-button c-button--full c-button--gray" href="/m/main.do">해지 예약 취소</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 미성년자 신청불가 팝업 -->
        <div class="c-modal" id="modalMinorNotAllowed" role="dialog" tabindex="-1" aria-describedby="applyNotAllowedTitle">
            <div class="c-modal__dialog  c-modal__dialog--alert" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="complete u-pt--0">
                            <i class="c-icon c-icon--notice" aria-hidden="true"></i>
                            <h3 class="u-fs-20 u-mt--16">
                                <b>미성년자의 경우</b><br/>온라인 해지 상담 신청이 불가합니다.
                            </h3>
                            <p class="c-text c-text--fs16 u-mt--20">제출 서류 등 안내를 위해<br/>고객센터(114)로 연락부탁드립니다.</p>
                        </div>
                        <p class="c-bullet c-bullet--fyr u-ta-left u-fs-14 u-mt--20">확인버튼 클릭 시 가입정보 페이지로 이동합니다.</p>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <a class="c-button c-button--full c-button--primary" href="/m/mypage/myinfoView.do">확인</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 신청완료 팝업 -->
        <div class="c-modal" id="modalCancelRequestComplete" role="dialog" tabindex="-1" aria-describedby="applyNotAllowedTitle">
            <div class="c-modal__dialog  c-modal__dialog--alert" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="complete u-pt--0">
                            <i class="c-icon c-icon--notice" aria-hidden="true"></i>
                            <h3 class="u-fs-20 u-mt--16">
                                <b>해지상담신청</b>이 완료되었습니다.
                            </h3>
                            <p class="c-text c-text--fs16 u-mt--20">해지 절차 및 동의사항 안내를 위해 D+1일(영업일)이내 연락 드리며, 1899-5000번으로 발신 예정이니 수신 부탁드립니다.</p>
                        </div>
                        <ul class="c-text-list c-bullet c-bullet--fyr u-fs-14 u-ta-left u-mt--20">
                            <li class="c-text-list__item">2회 부재 시 해지 접수가 취소됩니다.</li>
                            <li class="c-text-list__item">부재로 인한 접수 취소시, 신청일포함 7일 이내 고객센터 문의, 이후에는 재접수 부탁드립니다.</li>
                        </ul>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <a class="c-button c-button--full c-button--primary" href="/m/main.do">확인</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 이미 접수 완료 팝업 -->
        <div class="c-modal" id="modalAlreadyRequested" role="dialog" tabindex="-1" aria-describedby="applyNotAllowedTitle">
            <div class="c-modal__dialog  c-modal__dialog--alert" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="complete u-pt--0">
                            <i class="c-icon c-icon--notice" aria-hidden="true"></i>
                            <h3 class="u-fs-20 u-mt--16">
                                <b>이미 접수중인 내용이 있습니다.</b>
                            </h3>
                            <p class="c-text c-text--fs16 u-mt--20">신청 내역 확인 후<br/>상담사가 연락드리도록 하겠습니다.</p>
                        </div>
                        <p class="c-bullet c-bullet--fyr u-ta-left u-fs-14 u-mt--20">상담전화를 받지 못하신 경우 접수가 취소될 수 있습니다.</p>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <a class="c-button c-button--full c-button--primary" href="/m/mypage/cancelConsult.do">확인</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>



    </t:putAttribute>
</t:insertDefinition>