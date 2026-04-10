<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />


<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">

<!-- 	    <script src="/resources/js/mobile/vendor/p5.min.js"></script> -->
<!-- 	    <script src="/resources/js/mobile/game/game.js"></script> -->
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/mypage/usimSelfChg.js?version=25-10-31"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js?version=24.01.11"></script>
        <script type="text/javascript">
        history.pushState(null, null,"/m/mypage/usimSelfChg.do");
        window.onpopstate = function (event){
            history.go("/m/mypage/usimSelfChg.do");
        }
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="isAuth" name="isAuth"  >
        <input type="hidden" id="isUsimNumberCheck" name="isUsimNumberCheck">
        <input type="hidden" id="isPassAuth" name="isPassAuth"  >
        <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">
        <div class="ly-content" id="main-content">

            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    유심 셀프변경
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <h3 class="c-heading c-heading--type7 u-mt--32">조회회선</h3>
            <div class="c-form u-width--42p">
                <select class="c-select c-select--type3" name="selSvcCntrNo" id="selSvcCntrNo">
                    <c:forEach items="${cntrList}" var="item">
                       <c:choose>
                          <c:when test="${maskingSession eq 'Y'}">
                              <option value="${item.svcCntrNo }"${item.svcCntrNo eq searchVO.ncn?'selected':''}
                              data-age="${item.age }" data-cstmr_type="${item.cstmrType }"
                              data-cntpnt_cd="${item.openAgntCd }">${item.formatUnSvcNo }</option>
                          </c:when>
                          <c:otherwise>
                              <option value="${item.svcCntrNo }"${item.svcCntrNo eq searchVO.ncn?'selected':''}
                              data-age="${item.age }" data-cstmr_type="${item.cstmrType }"
                              data-cntpnt_cd="${item.openAgntCd }">${item.cntrMobileNo }</option>
                          </c:otherwise>
                       </c:choose>
                    </c:forEach>
                </select>
            </div>
            <hr class="c-hr c-hr--type3">

            <ul class="c-text-list c-bullet c-bullet--dot">
              <li class="c-text-list__item u-co-gray">핸드폰 변경으로 유심 크기가 달라지거나 단말 분실로 유심 변경이 필요할 경우 다른 유심으로 변경할 수 있는 서비스입니다.</li>
              <li class="c-text-list__item u-co-gray">유심 셀프변경 시 음성, 문자 등 휴대폰 사용이 중단됩니다. 신속히 새로운 유심을 단말기에 장착하여 1~3회 전원을 껐다 켜면 사용 가능합니다.</li>
            </ul>

            <h4 class="c-heading u-fw-normal u-fs-20 u-mt--38 u-mb--12">유심(USIM) 교체 전 확인 필수 안내</h4>
            <ul class="c-text-list c-bullet c-bullet--dot">
              <li class="c-text-list__item u-co-gray">
                <span class="u-fw--bold u-co-black">SIM에 <span class="u-co-red">연락처</span>가 저장되어 있는 경우 단말기에 연락처를 복사하거나 백업 앱을 통해 저장하신 후 USIM 교체를 진행해 주세요.</span>
                <ul class="c-text-list c-bullet c-bullet--hyphen">
                  <li class="c-text-list__item u-co-gray u-mt--2">삼성(갤럭시) : 연락처 앱 열기 → 메뉴/설정 → 연락처 내보내기 → 내장 저장공간 저장</li>
                  <li class="c-text-list__item u-co-gray u-mt--2">아이폰 : 설정 → 하단 “앱” → “연락처” 선택 → 연락처 가져오기</li>
                </ul>
                <p class="c-bullet c-bullet--fyr u-mt--2">단말기에 따라 경로가 다를 수 있으며, 위 경로로 확인되지 않을 경우 제조사에 문의해 주시기 바랍니다.</p>
              </li>
              <li class="c-text-list__item u-co-gray u-mt--12">
                <span class="u-fw--bold u-co-black"><span class="u-co-red">선불 모바일 교통카드</span>의 잔액은 USIM에 저장되므로 USIM 변경 전 아래 내용을 확인해 주세요.</span><br>
                자세한 사항은 티머니/이즐 고객센터로 문의해 주시기 바랍니다.
                <ul class="c-text-list c-bullet c-bullet--hyphen">
                  <li class="c-text-list__item u-co-gray u-mt--2">티머니 : USIM 변경 전에 잔액을 환불하고, 변경 완료 후 재가입</li>
                  <li class="c-text-list__item u-co-gray u-mt--2">이즐 : USIM 교체 전 앱에서 잔액 환급을 신청하거나 잔액 보관 신청을 한 뒤 변경 후 잔액을 이전</li>
                </ul>
              </li>
              <li class="c-text-list__item u-co-gray u-mt--12">
                현재 사용 중인 단말이 아닌 다른 단말에서 이용하실 예정인 경우, ‘유심보호서비스(부가서비스)’를 먼저 해지해 주세요.
              </li>
            </ul>

            <h3 class="c-heading c-heading--type1 u-mt--50">신규 유심 정보 등록</h3>
            <div class="c-form c-scan-wrap _offLineUsim">
                <div class="c-box c-box--type1 c-box--dotted u-mt--36">
                  <img class="center-img" src="/resources/images/mobile/content/img_usim_card.png" alt="유심 카드 샘플 이미지">
                </div>
                <div class="c-button-wrap c-button-wrap--right" id="divUsimScan">
                  <button class="c-button _ocrRecord" data-type="usim" >
                    <span class="c-text c-text--type3 u-co-sub-4">스캔하기</span>
                    <i class="c-icon c-icon--scan" aria-hidden="true"></i>
                  </button>
                </div>
            </div>

            <div class="c-form">
              <div class="c-form__input _offLineUsim">
                <input class="c-input onlyNum" id="reqUsimSn" type="tel" maxlength="19" pattern="[0-9]*" placeholder="직접입력('-'없이 입력)" title="유심번호 입력" value="${AppformReq.reqUsimSn}">
                <label class="c-form__label" for="reqUsimSn">유심번호 19자리</label>
              </div>
              <div class="c-button-wrap _offLineUsim">
                <button id="btnUsimNumberCheck" class="c-button c-button--full c-button--mint">유심번호 유효성 체크</button>
              </div>
            </div>


            <div class="c-form">
              <span class="c-form__title" id="radC">유심변경 본인인증 동의</span>
              <div class="c-agree u-mt--0">
                <input class="c-checkbox" id="clauseSimpleOpen" type="checkbox" >
                <label class="c-label" for="clauseSimpleOpen">본인인증 절차 동의</label>
              </div>
              <ul class="c-text-list c-bullet c-bullet--dot" id="ulOnOffLineUsimDesc">
                <li class="c-text-list__item u-co-gray">명의자 본인 확인을 위해서 본인인증이 필요합니다. 안전한 서비스 이용 및 고객님의 개인정보 보호를 위해 본인인증을 받으신 고객분만 이용이 가능합니다. 유심변경 이용을 위해서 본인확인 절차를 진행해주세요.</li>
              </ul>
            </div>

            <!-- 본인인증 방법 선택 -->
            <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                <jsp:param name="controlYn" value="N"/>
                <jsp:param name="reqAuth" value="CNAT"/>
            </jsp:include>

            <div class="c-button-wrap u-mb--20">
                <button class="c-button c-button--full c-button--primary" id="btnUsimChg" style="display: none;">유심변경</button>
            </div>

            <b class="c-text c-text--type3 u-mb--16">
                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
            </b>
            <ul class="c-text-list c-bullet c-bullet--dot">
                <li class="c-text-list__item u-co-gray"><span style="font-color:red">유심 셀프변경 시 음성, 문자 등 휴대폰 사용이 중단됩니다. 신속히 새로운 유심을 단말기에 장착하여 1~3회 전원을 껐다 켜면 사용 가능합니다.</span></li>
                <li class="c-text-list__item u-co-gray">홈페이지 또는 편의점 등에서 kt M모바일 유심을 구매하신 고객님께서 이용가능하며, 유심변경 취소는 불가능합니다.</li>
                <li class="c-text-list__item u-co-gray">유심변경 시 기존 유심에 저장된 개인정보(연락처, 사진 등)는 자동으로 전송되지 않습니다. 미리 휴대폰으로 저장하셔야 합니다.</li>
            </ul>
        </div>

        <!-- PASS 인증 2단계 -->
        <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />
        <input type="hidden" id="requestNo" name="requestNo" >
        <input type="hidden" id="resUniqId" name="resUniqId" >

      <!--  계좌점유  Layer-->
      <div class="c-modal" id="bankAutDialog" role="dialog" tabindex="-1" aria-describedby="#bankAutTitle">

        <div class="c-modal__dialog c-modal__dialog--full" role="document">
          <div class="c-modal__content">
            <div class="c-modal__header">
              <h1 class="c-modal__title" id="bankAutTitle">계좌인증</h1>
              <button class="c-button" data-dialog-close>
                <i class="c-icon c-icon--close" aria-hidden="true"></i>
                <span class="c-hidden">팝업닫기</span>
              </button>
            </div>
            <div class="c-indicator">
              <span style="width: 33.33%"></span>
            </div>

            <div id="divBankAut1" class="c-modal__body">
              <h3 class="c-heading c-heading--type1">계좌정보 입력</h3>
              <p class="c-text c-text--type2 u-co-gray"> 본인인증에 필요한 정보를 입력해 주세요.
                PASS 인증을 받은 실명과 계좌 명의자가 동일해야
                인증이 가능합니다.</p>
              <div class="c-form u-mt--0">
                <span class="c-form__title">은행/증권사</span>
                <div class="c-form__group" role="group" aria-labelledby="inpBankNum">
                  <div class="c-form__select">
                    <select id="reqBankAut" class="c-select">
                      <option value="">선택</option>
                      <c:forEach items="${bankList}" var="itemBank" >
                        <option value="${itemBank.dtlCd }">${itemBank.dtlCdNm }</option>
                      </c:forEach>
                    </select>
                    <label class="c-form__label">은행명</label>
                  </div>
                  <div class="c-form__input">
                    <input class="c-input onlyNum" id="reqAccountAut" type="tel" name="" pattern="[0-9]*" placeholder="'-'없이 입력">
                    <label class="c-form__label" for="reqAccountAut">계좌번호</label>
                  </div>
                  <div class="c-form__input has-value">
                    <input class="c-input" id="cstmrNameTemp" type="text" placeholder=""  readonly >
                    <label class="c-form__label" for="cstmrNameTemp">이름</label>
                  </div>
                </div>
              </div><!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
              <div class="c-button-wrap">
                <!--[2021-12-02] 유틸클래스명 변경 -->
                <button class="c-button c-button--gray c-button--lg u-width--120" data-dialog-close>취소</button>
                <button id="btnAutAccount" class="c-button c-button--full c-button--primary">인증요청</button>
              </div>
            </div>


            <div id="divBankAut2" style="display:none;" class="c-modal__body">
              <h3 class="c-heading c-heading--type1">인증번호 입력</h3>
              <p class="c-text c-text--type2 u-co-gray"> 입력하신 계좌로 1원을 보내드렸습니다.
                <br>계좌 거래내역에서 1원의 입금자로 표시된
                <br>숫자 6자리를 입력해 주시기 바랍니다.
              </p>
              <div class="c-form u-mt--32">
                <div class="c-box u-mb--24">
                  <img class="center-img" src="/resources/images/mobile/content/img_account_certification.png" alt="">
                </div>
                <div class="c-form__input">
                  <input class="c-input onlyNum" id="textOpt"  maxlength="6" type="tel" name="" pattern="[0-9]*" placeholder="입금자 명에 표시된 숫자 6자리 입력">
                  <label class="c-form__label" for="textOpt">인증번호</label>
                </div>
              </div><!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
              <div class="c-button-wrap">
                <!--[2021-12-02] 유틸클래스명 변경 -->
                <button class="c-button c-button--gray c-button--lg u-width--120" data-dialog-close>취소</button>
                <button id="btnCheckAccountOtpConfirm"  class="c-button c-button--full c-button--primary">인증하기</button>
              </div>
            </div>

            <div id="divBankAut3" style="display:none;" class="c-modal__body">
              <h3 class="c-heading c-heading--type1">인증완료</h3>
              <p class="c-text c-text--type2 u-co-gray"> 계좌 인증이 완료되었습니다.
                <br>확인 버튼을 누른 뒤 신청서를
                <br>계속 작성해주시기 바랍니다.
              </p><!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
              <div class="c-button-wrap">
                <button class="c-button c-button--full c-button--primary" data-dialog-close >확인</button>
              </div>
            </div>


          </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>