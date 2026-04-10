<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<script type="text/javascript" src="/resources/js/portal/jehuSugg/jehuSuggFormView.js"></script>

<div class="c-modal__content">
    <div class="c-modal__header">
        <h2 class="c-modal__title" id="find-cvs-modal__title">제휴제안</h2>
        <button class="c-button" data-dialog-close id="closePop">
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>
    <div class="c-modal__body">
        <div class="etc-guide">
            <i class="c-icon c-icon--alliance" aria-hidden="true"></i>
            <strong class="etc-guide__title"> kt M모바일에서는 개인/단체의 새로운 제안과 <br>소중한 제휴 문의를 기다립니다.</strong>
            <p class="etc-guide__text u-co-gray">서비스콘텐츠 제휴, 프로모션, 마케팅 제휴 등 kt M모바일과 제휴를 원하시는 분 께서는 세부적인 제휴제안 내용을 등록해 주시면 접수 후 검토하여 연락을 드리도록 하겠습니다.</p>
            <p class="c-bullet c-bullet--fyr u-mt--16 u-co-gray u-ta-left">검토 결과는 담당자가 입력하신 이메일로 회신을 드립니다.</p>
        </div>
        <div class="c-form-wrap u-mt--48">
            <div class="c-form-group" role="group" aira-labelledby="inpPartnershipHead">
                <h3 class="c-group--head" id="inpPartnershipHead">제휴제안 작성</h3>
                <div class="c-form-field">
                    <div class="c-form__input">
                        <input class="c-input" type="text" placeholder="제목"  id="jehuTitle" name="jehuTitle" value="" maxlength="100">
                        <label class="c-form__label" for="jehuTitle">제목</label>
                    </div>
                </div>
                <div class="c-form-field">
                    <div class="c-form__select">
                        <label class="c-form__label" for="jehuCategory">카테고리</label>
                        <select class="c-select" id="jehuCategory" name="jehuCategory">
                            <c:forEach var="jehuList" items="${jehuSuggestionCategory}">
                                <option value="${jehuList.dtlCd}">${jehuList.dtlCdNm}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="c-textarea-wrap">
                    <label class="c-hidden" for="jehuContent">문의 내용을 입력해주세요.</label>
                    <textarea class="c-textarea" placeholder="문의 내용을 입력해주세요." id="jehuContent" name="jehuContent"></textarea>
                    <div class="c-textarea-wrap__sub">
                        <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span> <span id="textAreaSbstSize">0/800자</span>
                    </div>
                </div>
                <div class="c-form-field">
                    <div class="c-form__input">
                        <input class="c-input" type="text" placeholder="회사명" id="jehuCompanyNm" name="jehuCompanyNm" value="" maxlength="30">
                        <label class="c-form__label" for="jehuCompanyNm">회사명</label>
                    </div>
                </div>
                <div class="c-form-field">
                    <div class="c-form__input">
                        <input class="c-input onlyKorEng" type="text" placeholder="제안자명" id="jehuRegNm" name="jehuRegNm" value="" maxlength="10">
                        <label class="c-form__label" for="jehuRegNm">제안자명</label>
                    </div>
                </div>
                <div class="c-form-field">
                    <div class="c-form__input c-form__input-division" aria-describedby="msg1">
                        <input class="c-input numOnly" type="tel" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" id="jehuMobileNo" name="jehuMobileNo" value="" aria-invalid="false" >
                        <label class="c-form__label" for="jehuMobileNo">휴대폰</label>
                    </div>
                    <p class="c-form__text" id="msg1" style="display:none;"></p>
                </div>
                <div class="c-form-field">
                    <div class="c-form__input">
                        <input class="c-input notKor" id="jehuEmail" type="text" placeholder="예: ktm@ktm.com" aria-invalid="false" aria-describedby="msg2" name="jehuEmail" value="" maxlength="40">
                        <label class="c-form__label" for="jehuEmail">이메일</label>
                    </div>
                    <p class="c-form__text" id="msg2" style="display:none;"></p>
                </div>

                <!-- <div class="c-form-field">
                    <div class="c-form__input">
                        <input class="c-input notKor" id="jehuEmail" type="text" placeholder="예: ktm@ktm.com" aria-invalid="false" aria-describedby="inpValidErrorB" name="jehuEmail" value="" maxlength="40">
                        <label class="c-form__label" for="jehuEmail">이메일</label>
                    </div>
                </div> -->
            </div>
        </div>
        <div class="c-form-wrap u-mt--40">
            <div class="c-agree c-agree--type2">
                <div class="c-agree__item">
                    <input class="c-checkbox" id="jehuPriAgreeYn" type="checkbox" name="jehuPriAgreeYn">
                    <label class="c-label" for="jehuPriAgreeYn">
                        개인정보 수집이용 동의<span class="u-co-gray">(필수)</span>
                    </label>
                </div>
                <div class="c-agree__inside">
                    <ul class="c-text-list c-bullet c-bullet--number">
                        <li class="c-text-list__item u-mt--0">수집 이용의 목적 <p class="c-bullet c-bullet--dot u-co-gray">제휴신청에 따른 업체확인 및 원활한 의사소통 경로 확보</p></li>
                        <li class="c-text-list__item u-mt--16">수집항목<p class="c-bullet c-bullet--dot u-co-gray">제안자명, 연락처, 이메일</p></li>
                        <li class="c-text-list__item u-mt--16">이용 및 보유기간<p class="c-bullet c-bullet--dot u-co-gray" style="font-weight: 900;">모든 검토가 완료된 후 3개월 간 이용자의 조회를 위해 보관하며, 이후 해당정보를 지체 없이 파기.</p></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="c-modal__footer">
        <div class="c-button-wrap">
            <button class="c-button c-button--lg c-button--primary c-button--w460" id="jehuSuggBtn" disabled="disabled">등록하기</button>
        </div>
    </div>
</div>