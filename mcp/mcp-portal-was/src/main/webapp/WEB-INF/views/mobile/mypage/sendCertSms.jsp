<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/resources/js/mobile/mypage/sendCertSms.js?version=24-02-07"></script>

<c:choose>
    <c:when test="${'combine' eq popType or 'combineSelf' eq popType }">
        <div class="c-form u-mt--24">
            <div class="c-form__group" role="group">
                <div class="c-form__input" id="inputCustNm">
                    <input type="text" class="c-input" id="custNm" maxlength="60"  placeholder="고객명" title="고객명 입력">
                    <label class="c-form__label" for="custNm">고객명</label>
                </div>
                <p class="c-text--form" id="custNmChk" style="display: none">고객명을 입력해 주세요.</p>
                <div class="c-form__input" id="inputPhoneNum">
                    <input type="tel" class="c-input numOnly" id="phoneNum" maxlength="11"  placeholder="휴대폰 번호" title="휴대폰 번호 입력">
                    <label class="c-form__label" for="phoneNum">휴대폰 번호</label>
                    <button class="c-button c-button--sm c-button--underline" onclick="certifyCallBtn();" id="certifyCallBtn" >인증번호 받기</button>
                </div>
                <p class="c-text--form" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
                <div class="c-form__input">
                    <input type="number" class="c-input numOnly" id="certifySms" maxlength="6" placeholder="인증번호" title="인증번호 입력">
                    <label class="c-form__label" for="certifySms">인증번호</label>
                    <button class="c-button c-button--sm c-button--underline" id="regularCertBtn" onclick="regularCertBtn();">인증번호 확인</button>
                </div>
            </div>

            <p class="c-text--form" id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후 -->
            <p class="c-text--form" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8"  id="timer"></span>
                <button class="c-button--underline c-button--sm u-co-sub-4 u-ml--8" id="regularCertTimeBtn">시간연장</button>
            </p><!-- case3 : 인증시간 초과-->
            <p class="c-text--form" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
            <input id="certify"    type="hidden"   value="N" />
            <input id="menuType"   type="hidden"   value="${menuType}" />
            <input type="hidden"   id="svcCntrNo"  name="svcCntrNo" value=""/>
            <input type="hidden"  id="timeOverYn"  name="timeOverYn" value=""/>
            <input type="hidden"  id="timeYn"      name="timeYn" value="N"/>
            <input type="hidden" id="certifyYn"   value="N"/>
        </div>
    </c:when>

    <c:when test="${'consentGift' eq popType}">
        <div class="c-form__group" role="group" aria-labeledby="inpCertify">
            <div class="c-input-wrap">
            </div>
            <div class="c-form">
                <div class="c-form__input" id="inputCustNm" >
                    <input class="c-input"  type="text" placeholder="고객명" title="고객명 입력" maxlength="60" id="custNm" value="${custNm}" style="ime-mode:active;">
                    <label class="c-form__label" for="custNm">고객명</label>
                </div>
                <p class="c-text--form" id="custNmChk" style="display: none">고객명을 입력해 주세요.</p>
            </div>

            <div class="c-form-field _isDefault" >
                <div class="c-form__input-group" id="cstmrRun">
                    <div class="c-form__input c-form__input-division">
                        <!-- 주민등록번호 입력 부분 -->
                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" type="text" autocomplete="false" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                        <span>-</span>
                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,6,13)}" >
                        <label class="c-form__label" for="cstmrNativeRrn01" >주민등록번호</label>
                    </div>
                </div>
            </div>
            <div class="c-form__input" id="inputPhoneNum">
                <input class="c-input numOnly" type="tel" placeholder="휴대폰 번호" title="휴대폰 번호 입력" id="phoneNum" maxlength="11">
                <label class="c-form__label" for="phoneNum">휴대폰 번호</label>
                <button class="c-button c-button--sm c-button--underline" onclick="certifyCallBtn();" id="certifyCallBtn">인증번호 받기</button>
            </div>
            <p class="c-text--form" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
            <div class="c-form__input">
                <input class="c-input numOnly" type="tel" placeholder="인증번호" title="인증번호 입력" id="certifySms" maxlength="6">
                <label class="c-form__label" for="certifySms">인증번호</label>
                <button class="c-button c-button--sm c-button--underline" id="regularCertBtn" onclick="regularCertBtn();">인증번호 확인</button>
            </div> <!-- case1 : 인증번호 확인 전.-->
            <p class="c-text--form" id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후 -->
            <p class="c-text--form" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8"  id="timer"></span>
                <button class="c-button--underline c-button--sm u-co-sub-4 u-ml--8" id="regularCertTimeBtn">시간연장</button>
            </p><!-- case3 : 인증시간 초과-->
            <p class="c-text--form" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
            <input id="certify"    type="hidden"   value="N" />
            <input id="menuType"   type="hidden"   value="${menuType}" />
            <input type="hidden"   id="svcCntrNo"  name="svcCntrNo" value=""/>
            <input type="hidden"  id="timeOverYn"  name="timeOverYn" value=""/>
            <input type="hidden"  id="timeYn"      name="timeYn" value="N"/>
        </div>
    </c:when>

    <c:when test="${'frndReg' eq popType}">
        <div class="c-form__group" role="group" aria-labeledby="inpCertify">
            <div class="c-input-wrap" id="inputCustNm">
                <!-- 마스킹 처리된 고객명 추가 2022.10.05 -->
                <c:if test="${empty maskedcustNm}">
                    <input class="c-input" type="text" placeholder="고객명" title="고객명 입력" maxlength="60" id="custNm" value="${custNm}" style="ime-mode:active;">
                </c:if>
                <c:if test="${not empty maskedcustNm}">
                    <input class="c-input" type="hidden" placeholder="고객명" title="고객명 입력" maxlength="60" id="custNm" value="${custNm}" style="ime-mode:active;">
                    <input class="c-input" type="text" placeholder="고객명" title="고객명 입력" maxlength="60" id="maskedcustNm" value="${maskedcustNm}" style="ime-mode:active; margin-top:0px;">
                </c:if>
                <!-- // 마스킹 처리된 고객명 추가 2022.10.05 -->
            </div>
            <p class="c-text--form" id="custNmChk" style="display: none">고객명을 입력해 주세요.</p>

            <div class="c-input-wrap" id="inputPhoneNum">
                <input class="c-input numOnly" type="tel" placeholder="휴대폰 번호" title="휴대폰 번호 입력" id="phoneNum" maxlength="11">
                <button class="c-button c-button--sm c-button--underline" onclick="certifyCallBtn();" id="certifyCallBtn">인증번호 받기</button>
            </div>
            <p class="c-text--form" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
            <div class="c-input-wrap">
                <input class="c-input numOnly" type="tel" placeholder="인증번호" title="인증번호 입력"   id="certifySms" maxlength="6">
                <button class="c-button c-button--sm c-button--underline" id="regularCertBtn" onclick="regularCertBtn();">인증번호 확인</button>
            </div><!-- case1 : 인증번호 확인 전-->
            <p class="c-text--form" id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후 -->
            <span class="c-form__title">나의 친구초대 추천 URL</span>
            <div class="c-input-wrap">
                <input class="c-input" id="recommend" name="recommend" type="text">
                <label class="c-label c-hidden" for="recommend">나의 친구초대 추천 URL</label>
            </div>

            <p class="c-text--form" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8"  id="timer"></span>
                <button class="c-button--underline c-button--sm u-co-sub-4 u-ml--8" id="regularCertTimeBtn">시간연장</button>
            </p><!-- case3 : 인증시간 초과-->
            <p class="c-text--form" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
            <input id="certify"    type="hidden"   value="N" />
            <input id="menuType"   type="hidden"   value="${menuType}" />
            <input type="hidden"   id="svcCntrNo"  name="svcCntrNo" value=""/>
            <input type="hidden"  id="timeOverYn"  name="timeOverYn" value=""/>
            <input type="hidden"  id="timeYn"      name="timeYn" value="N"/>
        </div>
    </c:when>

    <c:otherwise>
        <div class="c-form__group" role="group" aria-labeledby="inpCertify">
            <div class="c-input-wrap" id="inputCustNm">
                <!-- 마스킹 처리된 고객명 추가 2022.10.05 -->
                <c:if test="${empty maskedcustNm}">
                    <input class="c-input" type="text" placeholder="고객명" title="고객명 입력" maxlength="60" id="custNm" value="${custNm}" style="ime-mode:active;">
                </c:if>
                <c:if test="${not empty maskedcustNm}">
                    <input class="c-input" type="hidden" placeholder="고객명" title="고객명 입력" maxlength="60" id="custNm" value="${custNm}" style="ime-mode:active;">
                    <input class="c-input" type="text" placeholder="고객명" title="고객명 입력" maxlength="60" id="maskedcustNm" value="${maskedcustNm}" style="ime-mode:active; margin-top:0px;">
                </c:if>
                <!-- // 마스킹 처리된 고객명 추가 2022.10.05 -->
            </div>
            <p class="c-text--form" id="custNmChk" style="display: none">고객명을 입력해 주세요.</p>

            <!-- <div class="c-input-wrap">
              <input class="c-input numOnly" type="tel" placeholder="주민등록번호" title="주민등록번호 입력" id="jumBiz" maxlength="14" pattern="[0-9]*">
            </div> -->
            <div class="c-input-wrap" id="inputPhoneNum">
                <input class="c-input numOnly" type="tel" placeholder="휴대폰 번호" title="휴대폰 번호 입력" id="phoneNum" maxlength="11">
                <button class="c-button c-button--sm c-button--underline" onclick="certifyCallBtn();" id="certifyCallBtn">인증번호 받기</button>
            </div>
            <p class="c-text--form" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
            <div class="c-input-wrap">
                <input class="c-input numOnly" type="tel" placeholder="인증번호" title="인증번호 입력"   id="certifySms" maxlength="6">
                <button class="c-button c-button--sm c-button--underline" id="regularCertBtn" onclick="regularCertBtn();">인증번호 확인</button>
            </div><!-- case1 : 인증번호 확인 전-->
            <p class="c-text--form" id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후 -->

            <p class="c-text--form" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8"  id="timer"></span>
                <button class="c-button--underline c-button--sm u-co-sub-4 u-ml--8" id="regularCertTimeBtn">시간연장</button>
            </p><!-- case3 : 인증시간 초과-->
            <p class="c-text--form" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
            <input id="certify" 	  type="hidden"   value="N" />
            <input id="menuType"   type="hidden"   value="${menuType}" />
            <input type="hidden"	  id="svcCntrNo"  name="svcCntrNo" value=""/>
            <input type="hidden"	 id="timeOverYn"  name="timeOverYn" value=""/>
            <input type="hidden"	 id="timeYn"      name="timeYn" value="N"/>
            <input type="hidden"   id="certifyYn"   value="N"/>
        </div>

    </c:otherwise>
</c:choose>
