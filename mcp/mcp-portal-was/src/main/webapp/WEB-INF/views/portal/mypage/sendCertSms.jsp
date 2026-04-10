<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<script type="text/javascript" src="/resources/js/portal/mypage/sendCertSms.js"></script>



<c:choose>
    <c:when test="${'nmIncPop' eq popType}">
        <div class="c-form-wrap u-mt--48">
            <div class="c-form-group" role="group" aria-labelledby="formGroupA">
                <!-- 수정 -->  <h3 class="c-group--head" id="formGroupA">가입정보 확인</h3><!-- [2022-01-12] 고객명 마크업 수정-->

                <div class="c-form-field">
                    <div class="c-form__input custNmVal">
                        <input class="c-input"  type="text" maxlength="60" id="custNm"  value="${custNm}" style="ime-mode:active;"/>
                        <label class="c-form__label" for="custNm">고객명</label>
                    </div>
                    <p class="c-form__text" id="custNmChk"  style="display: none">고객명을 입력해 주세요.<p>
                </p>
                </div>
                <!-- [$2022-01-12] 고객명 마크업 수정-->

                <div class="c-form">
                    <div class="c-input-wrap inputPhone">
                        <input class="c-input numOnly" id="phoneNum" maxlength="11" type="text" placeholder="휴대폰 번호" aria-invalid="true" aria-describedby="msgA1">
                        <label class="c-form__label c-hidden" for="phoneNum">휴대폰 번호 입력</label>
                        <button class="c-button c-button--sm c-button--underline" type="button" onclick="certifyCallBtn();" id="certifyCallBtn">인증번호 받기</button>
                    </div>
                    <p class="c-form__text" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
                </div>
                <div class="c-form">
                    <div class="c-input-wrap">
                        <input class="c-input" id="certifySms" type="text" placeholder="인증번호" aria-describedby="msgB1" maxlength="6">
                        <label class="c-form__label c-hidden" for="certifySms">인증번호 입력</label>
                        <button class="c-button c-button--sm c-button--underline"  id="regularCertBtn" type="button"  onclick="regularCertBtn();">인증번호 확인</button>
                    </div>
                </div>
                <!--aria-describedby 와 .c-form__text의 ID를 동일하게 연결시켜주세요-->
                <!-- case1 : 인증번호 확인 전-->
                <p class="c-form__text" id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후 -->
                <p class="c-form__text" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer"></span>
                    <button class="c-button c-button--xsm c-button--underline u-ml--8 u-co-mint" id="regularCertTimeBtn">시간연장</button>
                </p><!-- case3 : 인증시간 초과-->
                <p class="c-form__text" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
                <input id="certify"      type="hidden"     value="N" />
                <input id="menuType"     type="hidden"    value="${menuType}" />
                <input type="hidden"     id="svcCntrNo"  name="svcCntrNo" value=""/>
                <input type="hidden"     id="timeOverYn"  name="timeOverYn" value=""/>
                <input type="hidden"     id="certifyYn" value="N"/>
                <input type="hidden"     id="timeYn"      name="timeYn" value="N"/>

            </div>
        </div>
    </c:when>
    <c:when test="${'combine' eq popType}">
        <div class="c-form-wrap u-mt--12">
            <div class="c-form-group" role="group" aria-labelledby="formGroupA">
                <div class="c-form-row">
                    <div class="c-form-field u-width--460">
                        <div class="c-form__input custNmVal">
                            <input class="c-input" maxlength="60" id="custNm" type="text" placeholder="이름 입력" value="${custNm}" style="ime-mode:active;">
                            <label class="c-form__label" for="custNm">이름</label>
                        </div>
                        <p class="c-form__text" id="custNmChk"  style="display: none">고객명을 입력해 주세요.<p>
                    </p>
                    </div>
                </div><!-- case1-1 : 인증번호 확인 전 -->
                <div class="c-form-row c-col2">
                    <div class="c-form-field">
                        <div class="c-form__input inputPhone">
                            <input class="c-input numOnly" id="phoneNum" maxlength="11" type="text" placeholder="휴대폰 번호 입력" aria-invalid="true" aria-describedby="phoneChk">
                            <label class="c-form__label" for="phoneNum">휴대폰 번호</label>
                            <button class="c-button c-button--sm c-button--underline" onclick="certifyCallBtn();" id="certifyCallBtn">인증번호 받기</button>
                        </div>
                        <p class="c-form__text" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
                    </div>
                    <div class="c-form-field">
                        <div class="c-form__input">
                            <input class="c-input numOnly"  type="text" placeholder="인증번호 입력" aria-describedby="countdown" id="certifySms" maxlength="6">
                            <label class="c-form__label " for="certifySms">인증번호</label>
                            <button class="c-button c-button--sm c-button--underline" id="regularCertBtn"  onclick="regularCertBtn();">인증번호 확인</button>
                        </div>
                        <p class="c-form__text" id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p>
                        <p class="c-form__text" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer"></span>
                            <button class="c-button c-button--xsm c-button--underline u-ml--8 u-co-mint" id="regularCertTimeBtn">시간연장</button>
                        </p>
                        <p class="c-form__text" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
                        <input id="certify"    type="hidden"    value="N" />
                        <input id="menuType"   type="hidden"    value="${menuType}" />
                        <input type="hidden"   id="svcCntrNo"   name="svcCntrNo" value=""/>
                        <input type="hidden"   id="timeOverYn"  name="timeOverYn" value=""/>
                        <input type="hidden"   id="certifyYn"   value="N"/>
                        <input type="hidden"   id="timeYn"      name="timeYn" value="N"/>

                    </div>
                </div>
            </div>
        </div>





    </c:when>

    <c:when test="${'frndReg' eq popType}">
        <div class="c-form-wrap u-mt--48">
            <div class="c-form-group" role="group" aria-labelledby="formGroupA">
                <h3 class="c-group--head u-mb--0" id="formGroupA">가입정보</h3>
                <div class="c-form-row">
                    <div class="c-form-field u-width--460">
                        <div class="c-form__input custNmVal">
                            <input class="c-input" maxlength="60" id="custNm" type="text" placeholder="이름 입력" value="${custNm}" style="ime-mode:active;">
                            <label class="c-form__label" for="custNm">이름</label>
                        </div>
                        <p class="c-form__text" id="custNmChk"  style="display: none">고객명을 입력해 주세요.<p>
                    </p>
                    </div>
                </div><!-- case1-1 : 인증번호 확인 전 -->
                <div class="c-form-row c-col2">
                    <div class="c-form">
                        <div class="c-input-wrap inputPhone">
                            <input class="c-input numOnly" id="phoneNum" maxlength="11" type="text" placeholder="휴대폰 번호" aria-invalid="true" aria-describedby="phoneChk">
                            <label class="c-form__label c-hidden" for="phoneNum">휴대폰 번호 입력</label>
                            <button class="c-button c-button--sm c-button--underline" onclick="certifyCallBtn();" id="certifyCallBtn">인증번호 받기</button>
                        </div>
                        <p class="c-form__text" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
                    </div>
                    <div class="c-form">
                        <div class="c-input-wrap">
                            <input class="c-input numOnly"  type="text" placeholder="인증번호" aria-describedby="countdown" id="certifySms" maxlength="6">
                            <label class="c-form__la4bel c-hidden" for="certifySms">인증번호 입력</label>
                            <button class="c-button c-button--sm c-button--underline" id="regularCertBtn"  onclick="regularCertBtn();">인증번호 확인</button>
                        </div>
                        <p class="c-form__text" id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p>
                        <p class="c-form__text" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer"></span>
                            <button class="c-button c-button--xsm c-button--underline u-ml--8 u-co-mint" id="regularCertTimeBtn">시간연장</button>
                        </p>
                        <p class="c-form__text" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
                        <input id="certify"    type="hidden"    value="N" />
                        <input id="menuType"   type="hidden"    value="${menuType}" />
                        <input type="hidden"   id="svcCntrNo"   name="svcCntrNo" value=""/>
                        <input type="hidden"   id="timeOverYn"  name="timeOverYn" value=""/>
                        <input type="hidden"   id="certifyYn"   value="N"/>
                        <input type="hidden"   id="timeYn"      name="timeYn" value="N"/>
                    </div>
                </div><!-- case1-2 : 인증번호 확인 후 -->
                <h3 class="c-group--head u-mb--0 u-mt--48">나의 친구초대 추천 URL</h3>
                <div class="c-input-wrap">
                    <input class="c-input" id="recommend" name="recommend" type="text">
                    <label class="c-label c-hidden" for="recommend">나의 친구초대 추천 URL</label>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${'frndRegNe' eq popType}">
        <div class="c-form-wrap u-mt--48">
            <div class="c-form-group" role="group" aria-labelledby="formGroupA">
                <h3 class="c-group--head u-mb--0" id="formGroupA">가입정보</h3>
                <div class="c-form-row">
                    <div class="c-form-field u-width--460">
                        <div class="c-form__input custNmVal">
                            <c:if test="${empty maskedcustNm}">
                                <input class="c-input" maxlength="60" id="custNm" type="text" placeholder="이름 입력" value="${custNm}" style="ime-mode:active;">
                            </c:if>

                            <c:if test="${not empty maskedcustNm}">
                                <input class="c-input" maxlength="60" id="custNm" type="hidden" placeholder="이름 입력" value="${custNm}" style="ime-mode:active;">
                                <input class="c-input" maxlength="60" id="maskedcustNm" type="text" placeholder="이름 입력" value="${maskedcustNm}" style="ime-mode:active; margin-top:0px;">
                            </c:if>
                            <label class="c-form__label" for="custNm">이름</label>
                        </div>
                        <p class="c-form__text" id="custNmChk"  style="display: none">고객명을 입력해 주세요.<p>
                    </p>
                    </div>
                </div><!-- case1-1 : 인증번호 확인 전 -->
                <div class="c-form-row c-col2">
                    <div class="c-form">
                        <div class="c-input-wrap inputPhone">
                            <input class="c-input numOnly" id="phoneNum" maxlength="11" type="text" placeholder="휴대폰 번호" aria-invalid="true" aria-describedby="phoneChk">
                            <label class="c-form__label c-hidden" for="phoneNum">휴대폰 번호 입력</label>
                            <button class="c-button c-button--sm c-button--underline" onclick="certifyCallBtn();" id="certifyCallBtn">인증번호 받기</button>
                        </div>
                        <p class="c-form__text" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
                    </div>
                    <div class="c-form">
                        <div class="c-input-wrap">
                            <input class="c-input numOnly"  type="text" placeholder="인증번호" aria-describedby="countdown" id="certifySms" maxlength="6">
                            <label class="c-form__la4bel c-hidden" for="certifySms">인증번호 입력</label>
                            <button class="c-button c-button--sm c-button--underline" id="regularCertBtn"  onclick="regularCertBtn();">인증번호 확인</button>
                        </div>
                        <p class="c-form__text" id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p>
                        <p class="c-form__text" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer"></span>
                            <button class="c-button c-button--xsm c-button--underline u-ml--8 u-co-mint" id="regularCertTimeBtn">시간연장</button>
                        </p>
                        <p class="c-form__text" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
                        <input id="certify"    type="hidden"    value="N" />
                        <input id="menuType"   type="hidden"    value="${menuType}" />
                        <input type="hidden"   id="svcCntrNo"   name="svcCntrNo" value=""/>
                        <input type="hidden"   id="timeOverYn"  name="timeOverYn" value=""/>
                        <input type="hidden"   id="certifyYn"   value="N"/>
                        <input type="hidden"   id="timeYn"      name="timeYn" value="N"/>
                    </div>
                </div><!-- case1-2 : 인증번호 확인 후 -->

            </div>
        </div>
    </c:when>

    <c:otherwise>
        <div class="c-form-wrap u-mt--48">
            <div class="c-form-group" role="group" aria-labelledby="formGroupA">
                <h3 class="c-group--head u-mb--0" id="formGroupA">가입정보</h3>
                <div class="c-form-row">
                    <div class="c-form-field u-width--460">
                        <div class="c-form__input custNmVal">
                            <input class="c-input" maxlength="60" id="custNm" type="text" placeholder="이름 입력" value="${custNm}" style="ime-mode:active;">
                            <label class="c-form__label" for="custNm">이름</label>
                        </div>
                        <p class="c-form__text" id="custNmChk"  style="display: none">고객명을 입력해 주세요.<p>
                    </p>
                    </div>
                </div><!-- case1-1 : 인증번호 확인 전 -->
                <div class="c-form-row c-col2">
                    <div class="c-form">
                        <div class="c-input-wrap inputPhone">
                            <input class="c-input numOnly" id="phoneNum" maxlength="11" type="text" placeholder="휴대폰 번호" aria-invalid="true" aria-describedby="phoneChk">
                            <label class="c-form__label c-hidden" for="phoneNum">휴대폰 번호 입력</label>
                            <button class="c-button c-button--sm c-button--underline" onclick="certifyCallBtn();" id="certifyCallBtn">인증번호 받기</button>
                        </div>
                        <p class="c-form__text" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
                    </div>
                    <div class="c-form">
                        <div class="c-input-wrap">
                            <input class="c-input numOnly"  type="text" placeholder="인증번호" aria-describedby="countdown" id="certifySms" maxlength="6">
                            <label class="c-form__la4bel c-hidden" for="certifySms">인증번호 입력</label>
                            <button class="c-button c-button--sm c-button--underline" id="regularCertBtn"  onclick="regularCertBtn();">인증번호 확인</button>
                        </div>
                        <p class="c-form__text" id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p>
                        <p class="c-form__text" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer"></span>
                            <button class="c-button c-button--xsm c-button--underline u-ml--8 u-co-mint" id="regularCertTimeBtn">시간연장</button>
                        </p>
                        <p class="c-form__text" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
                        <input id="certify"    type="hidden"    value="N" />
                        <input id="menuType"   type="hidden"    value="${menuType}" />
                        <input type="hidden"   id="svcCntrNo"   name="svcCntrNo" value=""/>
                        <input type="hidden"   id="timeOverYn"  name="timeOverYn" value=""/>
                        <input type="hidden"   id="certifyYn"   value="N"/>
                        <input type="hidden"   id="timeYn"      name="timeYn" value="N"/>
                    </div>
                </div><!-- case1-2 : 인증번호 확인 후 -->
            </div>
        </div>
    </c:otherwise>
</c:choose>




