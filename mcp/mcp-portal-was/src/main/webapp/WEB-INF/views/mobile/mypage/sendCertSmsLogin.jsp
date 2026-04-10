 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/resources/js/mobile/mypage/sendCertSmsLogin.js"></script>


        <div class="c-form__group" role="group" aria-labeledby="inpCertify">
          <div class="c-input-wrap" id="inputCustNm">
            <c:choose>
                <c:when test="${maskingSession eq 'Y'}">
                  <input class="c-input" type="text" placeholder="고객명" title="고객명 입력" maxlength="60" id="custNm" value="${unMkNm}" style="ime-mode:active;">
                </c:when>
                <c:otherwise>
                  <input class="c-input" type="text" placeholder="고객명" title="고객명 입력" maxlength="60" id="custNm" value="${custNm}" style="ime-mode:active;">
                </c:otherwise>
            </c:choose>
          </div>
          <p class="c-text--form" id="custNmChk" style="display: none">고객명을 입력해 주세요.</p>

          <!-- <div class="c-input-wrap">
            <input class="c-input numOnly" type="tel" placeholder="주민등록번호" title="주민등록번호 입력" id="jumBiz" maxlength="14" pattern="[0-9]*">
          </div> -->
          <div class="c-input-wrap" id="inputPhoneNum">

            <!-- 연락처 존재 시 마스킹된 연락처로 노출 2022.10.05 -->
            <input type="hidden" id="contractNum">
            <input class="c-input numOnly" type="tel" placeholder="휴대폰 번호" title="휴대폰 번호 입력" id="phoneNum" maxlength="11">
            <input class="c-input" type="text" placeholder="휴대폰 번호" title="휴대폰 번호 입력" id="maskedPhoneNum" maxlength="11" style="display:none; margin-top: 0px;">

            <button class="c-button c-button--sm c-button--underline" onclick="certifyCallBtn();" id="certifyCallBtn">인증번호 받기</button>
            <input type="text" id="unMkNm" value="${custNm}" hidden/>
          </div>
          <p class="c-text--form" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
          <div class="c-input-wrap">
            <input class="c-input numOnly" type="number" placeholder="인증번호" title="인증번호 입력"   id="certifySms" maxlength="6">
            <button class="c-button c-button--sm c-button--underline" id="regularCertBtn" onclick="regularCertBtn();">인증번호 확인</button>
          </div><!-- case1 : 인증번호 확인 전-->
          <p class="c-text--form" id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후 -->
          <p class="c-text--form" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8"  id="timer"></span>
              <button class="c-button--underline c-button--sm u-co-sub-4 u-ml--8" id="regularCertTimeBtn" onclick="timeReload();">시간연장</button>
          </p><!-- case3 : 인증시간 초과-->
          <p class="c-text--form" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
           <input id="certify" 	  type="hidden"     value="N" />
           <input id="menuType"   type="hidden"    value="${menuType}" />
           <input type="hidden"	  id="svcCntrNo"  name="svcCntrNo" value=""/>


        </div>
