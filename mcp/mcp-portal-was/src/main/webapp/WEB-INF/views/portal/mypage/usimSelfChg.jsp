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

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">유심 셀프변경 신청</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
<!--     <script src="/resources/js/portal/vendor/p5.min.js"></script> -->
<!--     <script src="/resources/js/portal/game/game.js"></script> -->
    <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
    <script type="text/javascript" src="/resources/js/portal/mypage/usimSelfChg.js?version=25-10-31"></script>
    <script type="text/javascript">
    /* history.pushState(null, null,"/mypage/usimSelfChg.do");
    window.onpopstate = function (event){
        history.go("/mypage/usimSelfChg.do");
    } */
    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <form name="selfFrm" id="selfFrm" action="/mypage/usimSelfChg.do" method="post">
        <input type="hidden" name="ncn" value="" >
    </form>
    <input type="hidden" id="isAuth" name="isAuth">
    <input type="hidden" id="isUsimNumberCheck" name="isUsimNumberCheck">
    <input type="hidden" id="isPassAuth" name="isPassAuth"  >
    <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">유심 셀프변경</h2>
      </div>
      <div class="c-row c-row--lg">
        <h3 class="c-heading c-heading--fs20 c-heading--regular">유심변경할 회선을 선택해 주세요.</h3>
        <div class="c-select-search">
          <div class="c-form--line">
            <label class="c-label c-hidden" for="selSvcCntrNo">회선 선택</label>
            <select class="c-select" name="selSvcCntrNo" id="selSvcCntrNo">
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
          <button type="button" class="c-button c-button-round--xsm c-button--primary u-ml--10" href="javascript:void(0);" onclick="searchSvcCntrNo();">조회</button>
        </div>
        <hr class="c-hr c-hr--title">
        <ul class="c-text-list c-bullet c-bullet--dot">
          <li class="c-text-list__item u-co-gray">핸드폰 변경으로 유심 크기가 달라지거나 단말 분실로 유심 변경이 필요할 경우 다른 유심으로 변경할 수 있는 서비스입니다.</li>
          <li class="c-text-list__item u-co-gray">유심 셀프변경 시 음성, 문자 등 휴대폰 사용이 중단됩니다. 신속히 새로운 유심을 단말기에 장착하여 1~3회 전원을 껐다 켜면 사용 가능합니다.</li>
        </ul>

        <h4 class="c-heading c-heading--fs20 u-mt--52 u-mb--12">유심(USIM) 교체 전 확인 필수 안내</h4>
        <ul class="c-text-list c-bullet c-bullet--dot">
          <li class="c-text-list__item u-co-gray">
            <span class="u-fw--bold u-co-black">SIM에 <span class="u-co-red">연락처</span>가 저장되어 있는 경우 단말기에 연락처를 복사하거나 백업 앱을 통해 저장하신 후 USIM 교체를 진행해 주세요.</span><br>
            <ul class="c-text-list c-bullet c-bullet--hyphen u-mb--0">
              <li class="c-text-list__item u-co-gray u-mt--2">삼성(갤럭시) : 연락처 앱 열기 → 메뉴/설정 → 연락처 내보내기 → 내장 저장공간 저장</li>
              <li class="c-text-list__item u-co-gray u-mt--2">아이폰 : 설정 → 하단 “앱” → “연락처” 선택 → 연락처 가져오기</li>
            </ul>
            <p class="c-bullet c-bullet--fyr u-mt--2">단말기에 따라 경로가 다를 수 있으며, 위 경로로 확인되지 않을 경우 제조사에 문의해 주시기 바랍니다.</p>
          </li>
          <li class="c-text-list__item u-co-gray u-mt--12">
            <span class="u-fw--bold u-co-black"><span class="u-co-red">선불 모바일 교통카드</span>의 잔액은 USIM에 저장되므로 USIM 변경 전 아래 내용을 확인해 주세요.</span><br>
            자세한 사항은 티머니/이즐 고객센터로 문의해 주시기 바랍니다.
            <ul class="c-text-list c-bullet c-bullet--hyphen u-mb--0">
              <li class="c-text-list__item u-co-gray u-mt--2">티머니 : USIM 변경 전에 잔액을 환불하고, 변경 완료 후 재가입</li>
              <li class="c-text-list__item u-co-gray u-mt--2">이즐 : USIM 교체 전 앱에서 잔액 환급을 신청하거나 잔액 보관 신청을 한 뒤 변경 후 잔액을 이전</li>
            </ul>
          </li>
          <li class="c-text-list__item u-co-gray u-mt--12">
            현재 사용 중인 단말이 아닌 다른 단말에서 이용하실 예정인 경우, ‘유심보호서비스(부가서비스)’를 먼저 해지해 주세요.
          </li>
        </ul>

        <h5 class="c-form__title u-mt--64">신규 유심 정보 등록</h5>
        <div class="c-form-wrap u-mt--32 _offLineUsim">
          <div class="c-form-group" role="group" aira-labelledby="chkValUsimTitle1">
            <h3 class="c-group--head c-hidden" id="chkValUsimTitle1">유심번호 유효성 체크</h3>
            <div class="u-box--w460 u-margin-auto">
              <div class="c-form">
                <div class="c-img-wrap">
                  <img src="/resources/images/portal/content/img_usim_example.png" alt="유심카드 예시(유심번호는 4자리+4자리+4자리+4자리+3자리 숫자)">
                </div>

                <!-- 에러 발생 시 .has-error(aria-invalid="true") validation 통과 시 .has-safe(aria-invalid="false") 클래스를 사용-->
                <div class="c-input-wrap ">
                  <label class="c-form__label c-hidden" for="reqUsimSn">유심번호 입력</label>
                  <input class="c-input onlyNum" id="reqUsimSn" type="number" maxlength="19" placeholder="직접입력('-'없이 입력)" aria-invalid="false" aria-describedby="msgA1" value="${AppformReq.reqUsimSn}" >
                </div>
              </div>
              <div class="c-button-wrap u-mt--40">
                <button id="btnUsimNumberCheck" class="c-button c-button-round--md c-button--mint c-button--w460">유심번호 유효성 체크</button>
              </div>
            </div>
          </div>
        </div>


        <div class="c-form-wrap u-mt--32">
          <div class="c-form-group" role="group" aira-labelledby="inpNameCertiTitle">
            <div class="c-form-wrap u-mt--48 _self">
              <h5 class="c-form__title">유심변경 본인인증 동의</h5>
              <div class="c-agree u-mt--16">
                <div class="c-agree__item">
                  <div class="c-agree__inner">
                    <input class="c-checkbox" id="clauseSimpleOpen" type="checkbox" validityyn="Y">
                    <label class="c-label" for="clauseSimpleOpen">본인인증 절차 동의</label>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="c-box c-box--type1 u-mt--20">
            <ul class="c-text-list c-bullet c-bullet--dot">
              <li class="c-text-list__item u-co-gray">명의자 본인 확인을 위해서 본인인증이 필요합니다. 안전한 서비스 이용 및 고객님의 개인정보 보호를 위해
                                                      본인인증을 받으신 고객분만 이용이 가능합니다. 유심변경 이용을 위해서 본인확인 절차를 진행해주세요.</li>
            </ul>
          </div>
        </div>

        <!-- 본인인증 방법 선택 -->
        <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
            <jsp:param name="controlYn" value="N"/>
            <jsp:param name="reqAuth" value="CNATX"/>
        </jsp:include>

        <div class="c-button-wrap u-mt--56">
          <button class="c-button c-button--lg c-button--primary c-button--w460" id="btnUsimChg" style="display: none;">유심변경</button>
        </div>

        <h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </h3>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <li class="c-text-list__item u-co-gray"><span style="color: #ff0000;">유심 셀프변경 시 음성, 문자 등 휴대폰 사용이 중단됩니다. 신속히 새로운 유심을 단말기에 장착하여 1~3회 전원을 껐다 켜면 사용 가능합니다.</span></li>
          <li class="c-text-list__item u-co-gray">홈페이지 또는 편의점 등에서 kt M모바일 유심을 구매하신 고객님께서 이용가능하며, 유심변경 취소는 불가능합니다.</li>
          <li class="c-text-list__item u-co-gray">유심변경 시 기존 유심에 저장된 개인정보(연락처, 사진 등)는 자동으로 전송되지 않습니다. 미리 휴대폰으로 저장하셔야 합니다.</li>
        </ul>
      </div>

    </div>


      <!-- PASS 인증 2단계 -->
      <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />
      <input type="hidden" id="requestNo" name="requestNo" >
      <input type="hidden" id="resUniqId" name="resUniqId" >

       <div class="c-modal c-modal--xlg" id="bankAutDialog" role="dialog" aria-labelledby="#bankAutDialog__title">
            <div class="c-modal__dialog" role="document">
              <div class="c-modal__content">
                <div class="c-modal__header">
                  <h2 class="c-modal__title" id="#bankAutDialog__title">계좌인증</h2>
                  <div class="c-stepper-wrap">
                    <div class="c-hidden">유심구매 총 2단계 중 현재 2단계(계좌정보 입력)</div>
                    <ul class="c-stepper">
                      <li class="c-stepper__item c-stepper__item--active">
                        <span class="c-stepper__num">1</span>
                        <span class="c-stepper__title">계좌정보 입력</span>
                      </li>
                      <li class="c-stepper__item">
                        <span class="c-stepper__num">2</span>
                      </li>
                    </ul>
                  </div>
                  <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
                </div>
                <div class="c-modal__body">
                  <h3 class="c-heading c-heading--fs20" id="AccountInfoHead">계좌정보 입력</h3>
                  <p class="c-text c-text--fs17 u-co-gray u-mt--12">본인인증에 필요한 정보를 입력해 주세요.
                    <br>PASS 인증을 받은 실명과 계좌 명의자가 동일해야 인증이 가능합니다.
                  </p>
                  <div class="c-form-wrap u-mt--48 u-mb--56">
                    <div class="c-form-group" role="group" aria-labelledby="AccountInfoHead">
                      <div class="c-form-row c-col2">
                        <div class="c-form-field">
                          <div class="c-form__select u-mt--0">
                            <select id="reqBankAut" class="c-select">
                                <option value="">선택</option>
                                <c:forEach items="${bankList}" var="itemBank" >
                                  <option value="${itemBank.dtlCd }">${itemBank.dtlCdNm }</option>
                                </c:forEach>
                              </select>
                            <label class="c-form__label" for="reqBankAut">은행/증권사</label>
                          </div>
                        </div>
                        <div class="c-form-field">
                          <div class="c-form__input u-mt--0">
                            <input class="c-input onlyNum" id="reqAccountAut" type="number" placeholder="'-'없이 입력">
                            <label class="c-form__label" for="reqAccountAut">계좌번호</label>
                          </div>
                        </div>
                      </div>
                      <div class="c-form-row c-col2 u-width--460">
                        <div class="c-form-field">
                          <div class="c-form__input">
                            <input class="c-input" id="cstmrNameTemp" type="text" placeholder="이름 입력" value="" readonly >
                            <label class="c-form__label" for="cstmrNameTemp">이름</label>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="c-modal__footer u-flex-center">
                  <div class="u-box--w460 c-button-wrap">
                    <button class="c-button c-button--full c-button--gray"  data-dialog-close>취소</button>
                    <button class="c-button c-button--full c-button--primary" id="btnAutAccount">인증요청</button>
                  </div>
                </div>
              </div>
            </div>
        </div>

         <div class="c-modal c-modal--xlg" id="bankAutDialog2" role="dialog" aria-labelledby="#bankAutDialog2__title">
            <div class="c-modal__dialog" role="document">
              <div class="c-modal__content">
                <div class="c-modal__header">
                  <h2 class="c-modal__title" id="bankAutDialog2__title">계좌인증</h2>
                  <div class="c-stepper-wrap">
                    <div class="c-hidden">총 2단계 중 현재 2단계(계좌정보 입력)</div>
                    <ul class="c-stepper">
                      <li class="c-stepper__item">
                        <span class="c-stepper__num">1</span>
                      </li>
                      <li class="c-stepper__item c-stepper__item--active">
                        <span class="c-stepper__num">2</span>
                        <span class="c-stepper__title">인증번호 입력</span>
                      </li>
                    </ul>
                  </div>
                  <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
                </div>
                <div class="c-modal__body">
                  <h3 class="c-heading c-heading--fs20">인증번호 입력</h3>
                  <p class="c-text c-text--fs17 u-co-gray u-mt--12">입력하신 계좌로 1원을 보내드렸습니다.
                    <br>계좌 거래내역에서 1원의 입금자로 표시된 숫자 6자리를 입력해 주시기 바랍니다.
                  </p>
                  <div class="c-form-wrap u-mt--48">
                    <div class="c-form-group" role="group" aira-labelledby="chkValUsimTitle">
                      <h3 class="c-group--head c-hidden" id="chkValUsimTitle">유심번호 유효성 체크</h3>
                      <div class="u-box--w460 u-margin-auto">
                        <div class="c-form">
                          <!--[2022-02-07] aria-hidden 속성 추가 및 이미지 변경-->
                          <div class="c-img-wrap" aria-hidden="true">
                            <img src="/resources/images/portal/content/img_account_certification.png" alt="">
                          </div><!-- 인증 성공 시 .has-safe, 인증 실패 시 .has-error 추가-->
                          <div class="c-form-field">
                            <div class="c-form__input">
                              <input class="c-input onlyNum" id="textOpt" type="text" placeholder="숫자 6자리" maxlength="6">
                              <label class="c-form__label" for="textOpt">인증번호</label>
                            </div><!-- 에러 설명 필요할때 -->
                            <!-- p.c-bullet.c-bullet--dot.u-co-gray 설명                 -->
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="c-modal__footer u-flex-center">
                  <div class="u-box--w460 c-button-wrap">
                    <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
                    <button class="c-button c-button--full c-button--primary" id="btnCheckAccountOtpConfirm">인증요청</button>
                  </div>
                </div>
              </div>
            </div>
        </div>

        <div class="c-modal c-modal--xlg" id="bankAutDialog3" role="dialog" aria-labelledby="#bankAutDialog3__title">
            <div class="c-modal__dialog" role="document">
              <div class="c-modal__content">
                <div class="c-modal__header">
                  <h2 class="c-modal__title" id="bankAutDialog3__title">계좌인증</h2>
                  <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
                </div>
                <div class="c-modal__body">
                  <!-- [2022-01-20] 간격조정 유틸 클래스 추가-->
                  <div class="complete u-mt--80 u-mb--64 u-ta-center">
                    <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
                    <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                      <b>인증 완료</b>
                    </h3>
                    <p class="c-text c-text--fs20 u-co-gray u-mt--16"> 계좌 인증이 완료되었습니다.
                      <br>확인 버튼을 누른 뒤 신청서를 계속 작성해주시기 바랍니다.
                    </p>
                  </div><!-- [2022-01-20] .u-flex-center 클래스 삭제-->
                </div>
                <div class="c-modal__footer">
                  <div class="c-button-wrap">
                    <button class="c-button c-button--lg c-button--primary c-button--w460" data-dialog-close>확인</button>
                  </div>
                </div>
              </div>
            </div>
        </div>
</t:putAttribute>
</t:insertDefinition>
