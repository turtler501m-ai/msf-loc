 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<script type="text/javascript"
            src="/resources/js/mobile/mypage/multiPhoneLinePop.js?ver=${jsver}"></script>

<div class="c-modal" id="pw_confirm-dialog" role="dialog" tabindex="-1" aria-describedby="#pw_confirm-title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <!-- 준회원 접근시 : 타이틀 '정회원 인증'-->
                        <!-- 정회원 접근시 : 타이틀 '다회선 추가'-->
                        <h1 class="c-modal__title" id="pw_confirm-title">${userVO.userDivision == '01' ? '다회선 추가' : '정회원 인증' }</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <p class="c-text c-text--type2 u-co-gray u-mt--32">kt M모바일에
                            가입된 휴대폰번호로 ${userVO.userDivision == '01' ? '다회선 추가 인증을 하시면' : '정회원 인증을 받으시면' } 해당 번호에 대해서 편의서비스 이용이 가능합니다.</p>
                        <div class="c-form u-mt--40">
                            <span class="c-form__title sr-only" id="user-info-txt">회원정보 입력</span>
                            <div class="c-form__group" role="group" aria-labelledby="inpCertify">
                            <input type="hidden" id="certify" value="N" />
                            <input type="hidden" id="menuType" value="multiPhone" />
                            <input type="hidden" id="certifyYn" value="N" />
                            <input type="hidden" id="contNum" value="" />
                            <input type="hidden" id="menuLoc" value="multiPhone"/>
                            <input type="hidden" id="timeYn" value="N"/>
                            <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSmsLogin.jsp"%>


                        </div>
                        <h3 class="c-heading c-heading--type2 u-mb--0">개인정보 수집·이용 동의</h3>
                        <div class="c-agree u-mt--16">
                            <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">
                            <label class="c-label" for="chkAgree">개인정보를 수집하는 것에 동의합니다.</label>
                        </div>
                        <ul class="c-text-list u-pl--36">
                            <li class="c-text-list__item">
                                <p>수집하는 개인정보 항목</p>
                                <span class="c-bullet c-bullet--hyphen u-co-gray">이름, 휴대폰번호</span>
                            </li>
                            <li class="c-text-list__item">
                                <p>수입, 이용목적</p>
                                <span class="c-bullet c-bullet--hyphen u-co-gray">${userVO.userDivision == '01' ? '다회선 추가를' : '정회원 등록을' } 위한 회원 정보 확인</span>
                            </li>
                            <li class="c-text-list__item">
                                <p>수집하는 개인정보의 보유기간</p>
                                <span class="c-bullet c-bullet--hyphen u-co-gray">회원탈퇴 시 파기</span>
                            </li>
                        </ul>

                        <div class="c-button-wrap u-mt--48">
                            <button class="c-button c-button--full c-button--gray" id="modalCancel">취소</button>
                            <button class="c-button c-button--full c-button--primary" id="addMultiBut">확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>