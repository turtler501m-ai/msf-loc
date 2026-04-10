<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/mypage/myNameChg.js?version=26-03-03"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js"></script>
        <script type="text/javascript">
        history.pushState(null, null,"/m/mypage/myNameChg.do");
        window.onpopstate = function (event){
            history.go("/m/mypage/myNameChg.do");
        }
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <style>
            /* 주소팝업 닫을때 모달이 같이 닫혀서 추가 */
            .c-icon--close2 {
                width: 1.5rem;
                height: 1.5rem;
                background: url(/resources/images/mobile/common/ico_x.svg) 50% 50% no-repeat;
            }
            /* 팝업위에 로딩이 생기지 않아 z-index 변경 */
            .c-loader {
                  position: fixed;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                z-index: 200;
            }
        </style>

        <form name="selfFrm" id="selfFrm" action="/m/mypage/myNameChg.do" method="post">
            <input type="hidden" name="ncn" value="" >
        </form>

        <input type="hidden" id="isAuth" name="isAuth">
        <input type="hidden" id="isAuth2" name="isAuth2">
        <input type="hidden" id="isSmsAuth" name="isSmsAuth"  >
        <input type="hidden" id="isAuthInsr" name="isAuthInsr"  >
        <input type="hidden" id="isPassAuth" name="isPassAuth"  >
        <input type="hidden" id="isCheckAccount" name="isCheckAccount" >
        <input type="hidden" id="isCheckCard" name="isCheckCard" >
        <input type="hidden" id="jehuProdType" name="jehuProdType" value="${jehuProdType}" ><!--제휴처 요금제 타입 -->
        <input type="hidden" id="jehuProdName" name="jehuProdName" value="${jehuProdName}" ><!--제휴처 요금제 이름 -->
        
        <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv" > <!--안면인증 결과 -->
        <input type="hidden" id="fathTrgYn" name="fathTrgYn" > <!--안면인증 대상여부 -->
        <input type="hidden" id="operType" name="operType" value="MCN">
        <input type="hidden" id="cntpntShopId" name="cntpntShopId" value="1100011741">

        <div class="ly-content" id="main-content">
          <!--include ../_includes/nav.pug-->
          <div class="ly-page-sticky">
            <h2 class="ly-page__head">명의변경<button class="header-clone__gnb"></button>
            </h2>
          </div>
          <div class="chgName-info">
            <div class="chgName-info__tit">
              <h3>
                kt M모바일에서<br/>간편하게 명의변경하세요!
              </h3>
              <p>
                <b>유심교체 없이</b> 보유중인 핸드폰을 가족명의로 변경할 수 있는 서비스입니다.
              </p>
            </div>
            <div class="chgName-info__list">
              <h3>명의변경 신청 주요 안내사항</h3>
              <ul>
                <li>현재 사용하시던 유심 그대로 명의변경 가능합니다.</li>
                <li>명의변경 신청하시면 고객센터 D+1일(영업일)이내 전화드리며, 신청정보 확인 후 가족관계 증명서를 요청하고있습니다.</li>
                <li>명의변경은 가족간에만 가능하며 법인 폐업 등에 따른 명의변경은 고객센터를 통해 접수 부탁드립니다.<br/>
                    ※ 가족의 범위 : 본인 및 배우자, 본인 및 배우자의 형제/자매(단, 형제/자매의 배우자와 자녀, 동거인은 가족범위 제외), 본인 및 배우자의 직계존비속(사위/며느리 포함)
                </li>
                <li>명의변경 후 다음달에는 양도인과 양수인 고객님께 명세서가 각각 발송됩니다.</li>
                <li>명의 주시는 분이 사용하시던 특정 부가서비스(예:010번호연결서비스 등)는 명의 받는분께서 계속 이용하실 수 없을 수 있습니다.</li>
                <li>요금고지서 기준 최근 3개월간 연속으로 사용량이 없거나, 사용기간이 90일이하, 정지, 미납 등 고객님 정보에 따라 명의변경이 제한 될 수 있습니다.</li>
                <li>명의변경 받는 분이 미성년자일 경우 본인인증 및 납부방법 등록을 위해 법정대리인의 신용카드 또는 은행계좌가 있어야 합니다.</li>
                <li>이외 혜택소멸, 약정승계 등 기타 유의사항은 아래 약관동의란을 통해 상세히 확인하시기 바랍니다.</li>
              </ul>
            </div>
            <div class="chgName-guide">
              <h3>명의변경 절차 안내</h3>
              <ul class="chgName-guide__list">
                <li class="chgName-guide__item">
                  <strong><span class="chgName-guide__step">1</span>명의 주는 고객님</strong>
                  <p>본인인증, 약관동의 등<br/>명의 양도 신청</p>
                </li>
                <li class="chgName-guide__item">
                  <strong><span class="chgName-guide__step">2</span>명의 받는 고객님</strong>
                  <p>본인인증, 신청정보 작성 등<br/>명의 양수신청</p>
                </li>
                <li class="chgName-guide__item">
                  <strong><span class="chgName-guide__step">3</span>고객센터</strong>
                  <p>두분 고객님께 전화드려 신청정보 확인 및<br/>가족관계 증명서 요청</p>
                </li>
                <li class="chgName-guide__item">
                  <strong><span class="chgName-guide__step">4</span>고객님</strong>
                  <p>양도/양수인 중 한분께서 가족관계 증명서를<br/>고객센터에 송부</p>
                </li>
                <li class="chgName-guide__item">
                  <strong><span class="chgName-guide__step">5</span>고객센터</strong>
                  <p>구비서류 확인하여 고객님께 전화드리고<br/> 명의변경 완료</p>
                </li>
              </ul>
            </div>
            <div class="chgName-guide">
              <h3>신청상태</h3>
              <div class="c-table">
                <table>
                  <caption>명의변경 회선 선택로 구성된 신청상태 정보</caption>
                  <colgroup>
                    <col>
                  </colgroup>
                  <thead>
                    <tr>
                      <th scope="col">명의변경 회선 선택</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr class="u-h--70">
                      <td class="chgName-guide__select">
                        <select class="c-select c-select--type3 u-width--140" name="selContractNum" id="selContractNum">
                        <c:forEach items="${cntrList}" var="item">
                          <c:choose>
                            <c:when test="${maskingSession eq 'Y'}">
                                  <option value="${item.contractNum }" data-cstmr_type="${item.cstmrType }" data-customer_type="${item.customerType }"
                                  data-soc="${item.soc }" data-soc_nm="${item.socNm }" data-on_off_type="${item.onOffType }"
                                  data-free_data_cnt="${item.freeDataCnt }" data-free_call_cnt="${item.freeCallCnt }"
                                  data-free_sms_cnt="${item.freeSmsCnt }" data-ncn="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.formatUnSvcNo }</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${item.contractNum }" data-cstmr_type="${item.cstmrType }" data-customer_type="${item.customerType }"
                                  data-soc="${item.soc }" data-soc_nm="${item.socNm }" data-on_off_type="${item.onOffType }"
                                  data-free_data_cnt="${item.freeDataCnt }" data-free_call_cnt="${item.freeCallCnt }"
                                  data-free_sms_cnt="${item.freeSmsCnt }" data-ncn="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.cntrMobileNo }</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                        </select>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <table class="u-bt--0">
                  <caption>양도인 신청 상태, 양수인 신청 상태로 구성된 신청상태 정보</caption>
                  <colgroup>
                    <col>
                    <col>
                  </colgroup>
                  <thead>
                    <tr>
                      <th scope="col">양도인 신청 상태</th>
                      <th scope="col">양수인 신청 상태</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr class="u-h--70">
                      <td id="tdGrReqStat">
                        <button class="c-button c-button--sm c-button--white"  data-dialog-trigger="#chgName-grantor-modal">신청하기</button>
                      </td>
                      <td id="tdAsReqStat">
                        <button class="c-button c-button--sm c-button--white" data-dialog-trigger="#chgName-assignee-modal">신청하기</button>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <ul class="c-text-list c-bullet c-bullet--fyr">
                  <li class="c-text-list__item u-co-gray">양도인, 양수인 모두 신청을 완료해야 명의변경 신청이 가능합니다.</li>
                </ul>
              </div>
              <div class="c-button-wrap u-mt--40">
                <button class="c-button c-button--lg c-button--primary c-button--full" id="btnNameChg" disabled>명의변경 신청하기</button>
              </div>
            </div>
          </div>
       </div>

       <!-- 양도인 신청하기 -->
       <div class="c-modal" id="chgName-grantor-modal" role="dialog" tabindex="-1" aria-describedby="#chgName-grantor-modal_01-title" >
         <div class="c-modal__dialog c-modal__dialog--full" role="document">
           <div class="c-modal__content">
             <div class="c-modal__header">
               <h1 class="c-modal__title" id="chgName-grantor-modal-title">양도인 신청하기</h1>
               <button class="c-button" data-dialog-close="">
                 <i class="c-icon c-icon--close2" aria-hidden="true"></i>
                 <span class="c-hidden">팝업닫기</span>
               </button>
             </div>
             <div class="c-modal__body">
               <div class="chgName-grantor">

                 <div class="c-form app-form-view-content u-mt--24">
                   <div class="c-check-wrap">
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="grCstmrType1" type="radio" name="grCstmrType" value="${Constants.CSTMR_TYPE_NA}" desc="19세 이상 내국인" disabled>
                       <label class="c-label" for="grCstmrType1">
                         <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                         <span>내국인</span>
                       </label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="grCstmrType2" type="radio" name="grCstmrType" value="${Constants.CSTMR_TYPE_NM}" desc="19세 미만 미성년자" disabled>
                       <label class="c-label" for="grCstmrType2">
                         <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                         <span class="app-form-view__lable">미성년자<br /><span class="u-fs-13">(19세 미만)</span></span>
                       </label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="grCstmrType3" type="radio" name="grCstmrType" value="${Constants.CSTMR_TYPE_FN}" desc="외국인" disabled>
                       <label class="c-label" for="grCstmrType3">
                         <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                         <span class="app-form-view__lable">외국인<br /><span class="u-fs-13">(Foreigner)</span></span>
                       </label>
                     </div>
                   </div>
                 </div>
                                  
                 <div class="c-form">
                   <span class="c-form__title" id="grInpNameCertiTitle">실명 및 본인인증</span>
                   <div class="c-form__input">
                     <input class="c-input" id="grCstmrName" type="text" placeholder="이름 입력" value="" maxlength="60">
                     <label class="c-form__label" for="grCstmrName">이름</label>
                   </div>
                 </div>                 
                 <div class="c-form-field _grIsDefault">
                   <div class="c-form__input-group">
                     <div class="c-form__input c-form__input-division" >
                       <input class="c-input--div2 onlyNum" id="grCstmrNativeRrn01" name="grCstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'grCstmrNativeRrn02');" >
                       <span>-</span>
                       <input class="c-input--div2 onlyNum" id="grCstmrNativeRrn02" name="grCstmrNativeRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" >
                       <label class="c-form__label" for="grCstmrNativeRrn01">주민등록번호</label>
                     </div>
                   </div>
                 </div>                 
                 <div class="c-form-field _grIsForeigner" style="display:none;">
                   <div class="c-form__input-group" >
                     <div class="c-form__input c-form__input-division">
                       <input class="c-input--div2 onlyNum" id="grCstmrForeignerRrn01" name="grCstmrForeignerRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="외국인 등록번호 앞자리" title="외국인 등록번호 앞자리" onkeyup="nextFocus(this, 6, 'grCstmrForeignerRrn02');" >
                       <span>-</span>
                       <input class="c-input--div2 onlyNum" id="grCstmrForeignerRrn02" name="grCstmrForeignerRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="외국인 등록번호 뒷자리" title="외국인 등록번호 뒷자리">
                       <label class="c-form__label" for="grCstmrForeignerRrn01">외국인 등록번호</label>
                     </div>
                   </div>
                 </div>

                 <div class="c-form _grIsTeen" style="display:none;">
                   <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                     <li class="c-text-list__item">영/유아(0세~4세 이하)의 경우 가입신청이 제한되므로 타 명의로 진행 바랍니다.</li>
                   </ul>
                 </div>

                 <div class="c-form _grIsForeigner" style="display:none;">
                   <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                     <li class="c-text-list__item">외국인 등록증과 동일하게 작성해주세요.</li>
                   </ul>
                 </div>

                 <div class="c-form _grIsTeen" style="display:none;">
                   <span class="c-form__title--type2" id="spMinor">법정대리인 정보</span>
                 </div>
                 <div class="c-form _grIsTeen" style="display:none;">
                   <div class="c-form__input">
                     <input class="c-input" id="grMinorAgentName" type="text" placeholder="법정대리인 성명" value="" maxlength=60 >
                     <label class="c-form__label" for="grMinorAgentName">법정대리인 성명</label>
                   </div>
                 </div>
                 <div class="c-form _grIsTeen" style="display:none;">
                   <div class="c-form__select">
                     <select id="grMinorAgentRelation" class="c-select">
                       <option value="">선택</option>
                       <option value="01">부</option>
                       <option value="02">모</option>
                       <option value="03">후견인</option>
                       <option value="04">연대보증인</option>
                     </select>
                     <label class="c-form__label" for="grMinorAgentRelation" >신청인과의 관계</label>
                   </div>
                 </div>

                 <div class="c-form-field _grIsTeen" style="display:none;">
                   <div class="c-form__input-group">
                     <div class="c-form__input c-form__input-division">
                       <input class="c-input--div2 onlyNum" id="grMinorAgentRrn01" name="grMinorAgentRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'grMinorAgentRrn02');">
                       <span>-</span>
                       <input class="c-input--div2 onlyNum" id="grMinorAgentRrn02" name="grMinorAgentRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                       <label class="c-form__label" for="grMinorAgentRrn01">법정대리인 주민등록번호</label>
                     </div>
                   </div>
                 </div>

                 <div class="c-form u-mt--8 _grIsTeen" style="display:none;">
                   <div class="c-form__input">
                     <input class="c-input onlyNum" id="grMinorAgentTel" type="tel" name="grMinorAgentTel" value="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력">
                     <label class="c-form__label" for="grMinorAgentTel">법정대리인 연락처 <em class="c-form__sublabel">(휴대폰)</em></label>
                   </div>
                 </div>

                 <div class="c-form _grIsTeen" style="display:none;">
                   <span class="c-form__title--type2">법정대리인 안내사항 확인 및 동의</span>
                   <div class="c-agree__item">
                     <input class="c-checkbox c-checkbox--type2" id="grMinorAgentAgrmYn" type="checkbox" name="grMinorAgentAgrmYn"  >
                     <label class="c-label" for="grMinorAgentAgrmYn">본인은 안내사항을 확인하였습니다.<span class="u-co-gray">(필수)</span>
                     </label>
                     <button id="grMinorAgentAgree" class="c-button c-button--xsm" onclick="fnSetEventId('grMinorAgentAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MINOR_AGENT}',0);"  >
                       <span class="c-hidden">상세보기</span>
                       <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                     </button>
                   </div>
                 </div>

                 <!-- 양도인 본인인증 방법 선택 -->
                 <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                     <jsp:param name="controlYn" value="N"/>
                     <jsp:param name="reqAuth" value="CNAT"/>
                 </jsp:include>

                 <div class="c-form">
                   <span class="c-form__title">연락가능 연락처</span>
                   <div class="c-form__input">
                     <input class="c-input onlyNum" id="cstmrSendTel" type="tel" name="cstmrSendTel" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="">
                     <label class="c-form__label" for="cstmrSendTel">양도인 미납을 위한 연락처</label>
                   </div>
                   <ul class="c-text-list c-bullet c-bullet--fyr">
                     <li class="c-text-list__item u-co-red">미납시 연락을 위한 휴대폰번호</li>
                   </ul>
                 </div>

                 <div class="c-form">
                   <span class="c-form__title" id="radC">약관동의</span>
                   <div class="c-agree u-mt--0">
                     <input class="c-checkbox" id="btnGrAllCheck" type="checkbox">
                     <label class="c-label" for="btnGrAllCheck">전체 약관에 동의합니다.</label>
                     <div class="c-agree__item">
                       <input id="notice1" code="${Constants.NOTICE_CODE_26}" cdgroupid1="FORMINFO" docver="0" type="checkbox" value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _grAgree">
                       <label class="c-label" for="notice1">혜택 소멸<span class="u-co-gray">(고지)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('notice1');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_26}');" >
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                     </div>
                     <div class="c-agree__item">
                       <input id="clauseCntrDelFlag" code="${Constants.CLAUSE_CNTR_DEL_CODE}" cdgroupid1="FORMREQUIRED" docver="0" type="checkbox" value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _grAgree">
                       <label class="c-label" for="clauseCntrDelFlag">고객정보 삭제<span class="u-co-gray">(필수)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseCntrDelFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_CNTR_DEL_CODE}');">
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                     </div>
                     <div class="c-agree__item">
                       <input id="notice2" code="${Constants.NOTICE_CODE_25}" cdgroupid1="FORMINFO" docver="0" type="checkbox" value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _grAgree">
                       <label class="c-label" for="notice2">기타 안내<span class="u-co-gray">(고지)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('notice2');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_25}');" >
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                    </div>
                   </div>
                 </div>
                 <div id="divButtonStep1" class="c-button-wrap">
                   <a class="c-button c-button--full c-button--primary" id="btnGrantor">양도인 신청하기</a>
                 </div>
               </div>
             </div>
           </div>
         </div>
       </div>

       <!-- 양수인 신청하기 -->
       <div class="c-modal" id="chgName-assignee-modal" role="dialog" tabindex="-1" aria-describedby="#chgName-assignee-modal_01-title" >
         <div class="c-modal__dialog c-modal__dialog--full" role="document">
           <div class="c-modal__content">
             <div class="c-modal__header">
               <h1 class="c-modal__title" id="chgName-assignee-modal-title">양수인 신청하기</h1>
               <button class="c-button" data-dialog-close="">
                 <i class="c-icon c-icon--close2" aria-hidden="true"></i>
                 <span class="c-hidden">팝업닫기</span>
               </button>
             </div>
             <div class="c-modal__body">
               <div class="chgName-grantor">

                 <div class="c-form app-form-view-content u-mt--24">
                   <div class="c-check-wrap">
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType1" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NA}" desc="19세 이상 내국인">
                       <label class="c-label" for="cstmrType1">
                         <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                         <span>내국인</span>
                       </label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType2" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NM}" desc="19세 미만 미성년자">
                       <label class="c-label" for="cstmrType2">
                         <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                         <span class="app-form-view__lable">미성년자<br /><span class="u-fs-13">(19세 미만)</span></span>
                       </label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}" desc="외국인">
                       <label class="c-label" for="cstmrType3">
                         <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                         <span class="app-form-view__lable">외국인<br /><span class="u-fs-13">(Foreigner)</span></span>
                       </label>
                     </div>
                   </div>
                 </div>
                 
                 <div class="c-form">
                   <span class="c-form__title" id="spOnlineAuthType">실명 및 본인인증</span>
                   <div class="c-form__input">
                     <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" value="" maxlength="60">
                     <label class="c-form__label" for="cstmrName">이름</label>
                   </div>
                 </div>
                 <div class="c-form-field _isDefault">
                   <div class="c-form__input-group">
                     <div class="c-form__input c-form__input-division" >
                       <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                       <span>-</span>
                       <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" >
                       <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                     </div>
                   </div>
                 </div>
                 
                 <div class="c-form-field _isForeigner" style="display:none;">
                   <div class="c-form__input-group" >
                     <div class="c-form__input c-form__input-division">
                       <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn01" name="cstmrForeignerRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="외국인 등록번호 앞자리" title="외국인 등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrForeignerRrn02');" >
                       <span>-</span>
                       <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn02" name="cstmrForeignerRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="외국인 등록번호 뒷자리" title="외국인 등록번호 뒷자리">
                       <label class="c-form__label" for="cstmrForeignerRrn01">외국인 등록번호</label>
                     </div>
                   </div>
                 </div>

                 <div class="c-form _isTeen" style="display:none;">
                   <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                     <li class="c-text-list__item">영/유아(0세~4세 이하)의 경우 가입신청이 제한되므로 타 명의로 진행 바랍니다.</li>
                   </ul>
                 </div>

                 <div class="c-form _isForeigner" style="display:none;">
                   <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                     <li class="c-text-list__item">외국인 등록증과 동일하게 작성해주세요.</li>
                   </ul>
                 </div>


                 <div class="c-form _isTeen" style="display:none;">
                   <span class="c-form__title--type2" id="spMinor">법정대리인 정보</span>
                 </div>
                 <div class="c-form _isTeen" style="display:none;">
                   <div class="c-form__input">
                     <input class="c-input" id="minorAgentName" type="text" placeholder="법정대리인 성명" value="" maxlength=60 >
                     <label class="c-form__label" for="minorAgentName">법정대리인 성명</label>
                   </div>
                 </div>
                 <div class="c-form _isTeen" style="display:none;">
                   <div class="c-form__select">
                     <select id="minorAgentRelation" class="c-select">
                       <option value="">선택</option>
                       <option value="01">부</option>
                       <option value="02">모</option>
                       <option value="03">후견인</option>
                       <option value="04">연대보증인</option>
                     </select>
                     <label class="c-form__label" for="minorAgentRelation" >신청인과의 관계</label>
                   </div>
                 </div>

                 <div class="c-form-field _isTeen" style="display:none;">
                   <div class="c-form__input-group">
                     <div class="c-form__input c-form__input-division">
                       <input class="c-input--div2 onlyNum" id="minorAgentRrn01" name="minorAgentRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'minorAgentRrn02');">
                       <span>-</span>
                       <input class="c-input--div2 onlyNum" id="minorAgentRrn02" name="minorAgentRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                       <label class="c-form__label" for="minorAgentRrn01">법정대리인 주민등록번호</label>
                     </div>
                   </div>
                 </div>


                 <div class="c-form u-mt--8 _isTeen" style="display:none;">
                   <div class="c-form__input">
                     <input class="c-input onlyNum" id="minorAgentTel" type="tel" name="minorAgentTel" value="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력">
                     <label class="c-form__label" for="minorAgentTel">법정대리인 연락처 <em class="c-form__sublabel">(휴대폰)</em></label>
                   </div>
                 </div>

                 <div class="c-form _isTeen" style="display:none;">
                   <span class="c-form__title--type2">법정대리인 안내사항 확인 및 동의</span>
                   <div class="c-agree__item">
                     <input class="c-checkbox c-checkbox--type2" id="minorAgentAgrmYn" type="checkbox" name="minorAgentAgrmYn"  >
                     <label class="c-label" for="minorAgentAgrmYn">본인은 안내사항을 확인하였습니다.<span class="u-co-gray">(필수)</span>
                     </label>
                     <button id="minorAgentAgree" class="c-button c-button--xsm" onclick="fnSetEventId('minorAgentAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MINOR_AGENT}',0);"  >
                       <span class="c-hidden">상세보기</span>
                       <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                     </button>
                   </div>
                 </div>

                 <!-- 양수인 본인인증 방법 선택 -->
                 <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                     <jsp:param name="controlYn" value="N"/>
                     <jsp:param name="reqAuth" value="CNAT"/>
                     <jsp:param name="spIndex" value="2"/>
                 </jsp:include>

<%--                  <div class="c-form">
                   <span class="c-form__title" id="radC">약관동의</span>
                   <div class="c-agree u-mt--0">
                     <input class="c-checkbox" id="btnAsAllCheck" type="checkbox">
                     <label class="c-label" for="btnAsAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.</label>
                     <div class="c-agree__item">
                       <input id="notice3" code="${Constants.NOTICE_CODE_27}" cdGroupId1=FORMINFO docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 hidden _agree">
                       <label class="c-label" for="notice3">명의변경 주요안내<span class="u-co-gray">(고지)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('notice3');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_27}');" >
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                     </div>
                     <div class="c-agree__item">
                       <input id="clausePriCollectFlag" code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                       <label class="c-label" for="clausePriCollectFlag">개인정보 수집 &middot; 이용 동의<span class="u-co-gray">(필수)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}');" >
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                     </div>
                     <div class="c-agree__item">
                       <input id="clausePriOfferFlag" code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                       <label class="c-label" for="clausePriOfferFlag">개인정보의 제공 동의<span class="u-co-gray">(필수)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}');">
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                     </div>

                     <div class="c-agree__item">
                       <input id="clauseEssCollectFlag" code="${Constants.CLAUSE_ESS_COLLECT_CODE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                       <label class="c-label" for="clauseEssCollectFlag">고유식별정보의 수집 &middot; 이용 동의<span class="u-co-gray">(필수)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseEssCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}');">
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                     </div>
                     <div class="c-agree__item">
                       <input id="clauseConfidenceFlag" code="${Constants.CLAUSE_CONFIDENCE_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                       <label class="c-label" for="clauseConfidenceFlag">신용정보 조회 &middot; 이용 &middot; 제공에 대한 동의서<span class="u-co-gray">(필수)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseConfidenceFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_CONFIDENCE_CODE}');"  >
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                     </div>
                     <div class="c-agree__item">
                       <input id="clausePriAdFlag" code="S${Constants.CLAUSE_PRI_AD_CODE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" class="c-checkbox c-checkbox--type2 _agree"  >
                       <label class="c-label" for="clausePriAdFlag">정보/광고 전송을 위한 개인정보 이용, 처리위탁 및   정보/광고 수신동의서<span class="u-co-gray">(선택)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriAdFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_PRI_AD_CODE}');" >
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                     </div>
                     <div class="c-agree__item _clauseJehuFlag" style="display:none;" >
                       <button class="c-button c-button--xsm" id="clauseJehuButton" >
                         <span class="c-hidden">제휴 서비스를 위한 동의(필수) 상세 팝업 열기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                       <input class="c-checkbox c-checkbox--type2 _agree" id="clauseJehuFlag" type="checkbox"  code="clauseJehuFlag" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="N" validityyn="Y" >
                       <label class="c-label" for="clauseJehuFlag">개인정보 제3자 제공 동의[${jehuProdName} 가입고객 필수 동의]<span class="u-co-gray">(필수)</span>
                       </label>
                     </div>
                     <div class="c-agree__item">
                       <input id="clauseFinanceFlag" code="S${Constants.CLAUSE_FINANCE_FLAG}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" class="c-checkbox c-checkbox--type2 _agree"  >
                       <label class="c-label" for="clauseFinanceFlag">개인(신용)정보 처리 및 보험가입 동의<span class="u-co-gray">(선택)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFinanceFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_FINANCE_FLAG}');" >
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                     </div>
                     <div class="c-agree__item">
                       <input id="notice" code="${Constants.NOTICE_CODE_16}" cdGroupId1=FORMINFO docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 hidden _agree">
                       <label class="c-label" for="notice">서비스 이용약관<span class="u-co-gray">(고지)</span>
                       </label>
                       <button class="c-button c-button--xsm" onclick="fnSetEventId('notice');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}');" >
                         <span class="c-hidden">상세보기</span>
                         <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                       </button>
                     </div>
                   </div>
                 </div> --%>
                <div class="c-accordion c-accordion--type5 acc-agree">
                  <span class="c-form__title--type2"  >약관동의</span>
                  <div class="c-accordion__box">
                    <div class="c-accordion__item">
                      <div class="c-accordion__head">
                        <div class="c-accordion__check">
                          <input class="c-checkbox" id="btnAsAllCheck" type="checkbox">
                          <label class="c-label" for="btnAsAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span></label>
                        </div>
                        <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeA1">
                          <div class="c-accordion__trigger"> </div>
                        </button>
                      </div>
                      <div class="c-accordion__panel expand" id="acc_agreeA1">
                        <div class="c-accordion__inside">

                            <div class="c-agree__item">
                               <input id="notice3" code="${Constants.NOTICE_CODE_27}" cdGroupId1=FORMINFO docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 hidden _agree">
                               <label class="c-label" for="notice3">명의변경 주요안내<span class="u-co-gray">(고지)</span>
                               </label>
                               <button class="c-button c-button--xsm" onclick="fnSetEventId('notice3');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_27}');" >
                                 <span class="c-hidden">상세보기</span>
                                 <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                               </button>
                             </div>

                            <div class="c-agree__item">
                              <input id="clausePriOfferFlag" code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                              <label class="c-label" for="clausePriOfferFlag">고유식별정보 수집&middot;이용 동의<span class="u-co-gray">(필수)</span>
                              </label>
                              <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}');">
                                <span class="c-hidden">상세보기</span>
                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                              </button>
                            </div>

                            <div class="c-agree__item">
                              <input id="clausePriCollectFlag" code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                              <label class="c-label" for="clausePriCollectFlag">개인정보/신용정보 수집·이용 동의<span class="u-co-gray">(필수)</span>
                              </label>
                              <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}');" >
                                <span class="c-hidden">상세보기</span>
                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                              </button>
                            </div>

                            <div class="c-agree__item">
                              <input id="clauseEssCollectFlag" code="${Constants.CLAUSE_ESS_COLLECT_CODE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                              <label class="c-label" for="clauseEssCollectFlag">개인정보 제3자 제공 동의<span class="u-co-gray">(필수)</span>
                              </label>
                              <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseEssCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}');">
                                <span class="c-hidden">상세보기</span>
                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                              </button>
                            </div>

                          <div class="c-agree__item">
                            <input id="clauseFathFlag01" code="clauseFathFlag01" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                            <label class="c-label" for="clauseFathFlag01">민감정보(생체인식정보)수집 및 이용동의<span class="u-co-gray">(필수)</span></label>
                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFathFlag01');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag01');">
                              <span class="c-hidden">상세보기</span>
                              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                          </div>
                          <div class="c-agree__item">
                            <input id="clauseFathFlag02" code="clauseFathFlag02" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                            <label class="c-label" for="clauseFathFlag02">민감정보(생체인식정보)조희 및 이용 / 3자 제공에 대한 동의<span class="u-co-gray">(필수)</span></label>
                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFathFlag02');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag02');">
                              <span class="c-hidden">상세보기</span>
                              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                          </div>

                            <div class="c-agree__item">
                              <input id="notice" code="F${Constants.NOTICE_CODE_16}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 hidden _agree">
                              <label class="c-label" for="notice">서비스 이용약관<span class="u-co-gray">(필수)</span>
                              </label>
                              <button class="c-button c-button--xsm" onclick="fnSetEventId('notice');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}');" >
                                <span class="c-hidden">상세보기</span>
                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                            </div>

                            <div class="c-agree__item _clauseJehuFlag" style="display:none;" >
                               <button class="c-button c-button--xsm" id="clauseJehuButton" >
                                 <span class="c-hidden">제휴 서비스를 위한 동의(필수) 상세 팝업 열기</span>
                                 <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                               </button>
                               <input class="c-checkbox c-checkbox--type2 _agree" id="clauseJehuFlag" type="checkbox"  code="clauseJehuFlag" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="N" validityyn="Y" >
                               <label class="c-label" for="clauseJehuFlag">개인정보 제3자 제공 동의[${jehuProdName} 가입고객 필수 동의]<span class="u-co-gray">(필수)</span>
                               </label>
                             </div>

                            <div class="c-accordion c-accordion--nested__sub c-agree__item">
                                <div class="c-accordion__box">
                                    <div class="c-accordion__item">
                                        <div class="c-accordion__head">
                                            <div class="c-accordion__check">
                                                <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="agreeWrap5" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
                                                <label class="c-label" for="agreeWrap5">고객 혜택 제공을 위한 정보수집 이용 동의 및 혜택 광고의 수신 위탁 동의<span class="u-co-gray">(선택)</span>
                                                </label>
                                            </div>
                                            <button class="c-accordion__button" type="button" aria-expanded="false">
                                                <div class="c-accordion__trigger"></div>
                                            </button>
                                        </div>
                                        <div class="c-accordion__panel expand" id="agree5">
                                            <div class="c-accordion__inside">
                                                <div class="c-agree__item">
                                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('personalInfoCollectAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.PERSONAL_INFO_COLLECT_AGREE}',0);">
                                                        <span class="c-hidden">고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의(선택) 상세 팝업 열기</span>
                                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                    </button>
                                                    <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="personalInfoCollectAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.PERSONAL_INFO_COLLECT_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N">
                                                    <label class="c-label" for="personalInfoCollectAgree">고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의<span class="u-co-gray">(선택)</span>
                                                    </label>
                                                </div>
                                                <div class="c-agree__item">
                                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriAdFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_PRI_AD_CODE}');" >
                                                        <span class="c-hidden">개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의(선택) 상세 팝업 열기</span>
                                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                    </button>
                                                    <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="clausePriAdFlag" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.CLAUSE_PRI_AD_CODE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="personalInfoCollectAgree">
                                                    <label class="c-label" for="clausePriAdFlag">개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="c-accordion c-accordion--nested__sub c-agree__item">
                                <div class="c-accordion__box">
                                    <div class="c-accordion__item">
                                        <div class="c-accordion__head">
                                            <div class="c-accordion__check">
                                                <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="agreeWrap6" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
                                                <label class="c-label" for="agreeWrap6">혜택 제공을 위한 제3자 제공 및 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                                </label>
                                            </div>
                                            <button class="c-accordion__button" type="button" aria-expanded="false">
                                                <div class="c-accordion__trigger"></div>
                                            </button>
                                        </div>
                                        <div class="c-accordion__panel expand" id="agree6">
                                            <div class="c-accordion__inside">
                                                <div class="c-agree__item">
                                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('othersTrnsAllAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.OTHERS_TRNS_ALL_AGREE}',0);initOthersTrnsAgrees()">
                                                        <span class="c-hidden">혜택 제공을 위한 제3자 제공 동의 상세 팝업 열기</span>
                                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                    </button>
                                                    <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="othersTrnsAllAgree" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)" code="S${Constants.OTHERS_TRNS_ALL_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N">
                                                    <label class="c-label" for="othersTrnsAllAgree">혜택 제공을 위한 제3자 제공 동의<span class="u-co-gray">(선택)</span>
                                                    </label>
                                                    <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="formOthersTrnsAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_TRNS_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5" style="display: none;">
                                                    <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="formOthersTrnsKtAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_TRNS_KT_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5" style="display: none;">
                                                </div>
                                                <div class="c-agree__item">
                                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('othersAdReceiveAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.OTHERS_AD_RECEIVE_AGREE}');" >
                                                        <span class="c-hidden">개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의(선택) 상세 팝업 열기</span>
                                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                    </button>
                                                    <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="othersAdReceiveAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_AD_RECEIVE_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5">
                                                    <label class="c-label" for="othersAdReceiveAgree">개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                 
                 <hr class="c-hr c-hr--type1 c-expand giftInfoListHr" style="">
                 <!-- 가입정보 -->
                 <div class="c-form" style="display:none;">
                   <span class="c-form__title" id="radUsimtType">가입정보</span>
                   <div class="c-check-wrap c-check-wrap--type3" role="group" aria-labelledby="join_infoTitle">
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button" id="reqInfoChgYn1" type="radio" name="reqInfoChgYn" value="N">
                       <label class="c-label" for="reqInfoChgYn1">기존과 동일</label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button" id="reqInfoChgYn2" type="radio" name="reqInfoChgYn" value="Y" checked>
                       <label class="c-label" for="reqInfoChgYn2">가입정보 변경</label>
                     </div>
                   </div>
                 </div>
                 <!-- 요금제 -->
                 <div class="c-form _divAssigneeReqInfo" style="display:none">
                   <div id="radPayTypeB">
                     <div class="c-form__title flex-type u-mt--24">사용중인 요금제</div>
                   </div>
                   <div class="c-box c-box--mint">
                     <h3 class="c-text c-text--normal" id="rateNm"><!-- 신한 모두다 맘껏 7GB+(시즌) --></h3>
                     <p class="c-text c-text--type4 c-text--regular" id="rateDesc"><!-- 데이터 7GB+1Mbps | 음성 집/이동전화 무제한 | 문자 기본제공 --></p>
                   </div>
                   <ul class="c-text-list c-bullet">
                     <li class="c-text-list__item u-co-gray">※ 요금제 변경을 희망하시는 고객님께서는 명의변경 시, 상담원분에게 요청 부탁드립니다.</li>
                   </ul>
                 </div>
                 <!-- 주소 -->
                 <div class="c-form _divAssigneeReqInfo" style="display:none">
                   <span class="c-form__title">주소</span>
                   <div class="c-form__group" role="group" aria-labeledby="inpG">
                     <div class="c-input-wrap">
                       <input id="cstmrPost" class="c-input" type="text" placeholder="우편번호" title="우편번호 입력" value="" readonly="">
                       <button class="c-button c-button--sm c-button--underline _btnAddr" addrflag="1">우편번호 찾기</button>
                     </div>
                     <input id="cstmrAddr" class="c-input" type="text" placeholder="주소 입력" title="주소 입력" value="" readonly="">
                     <input id="cstmrAddrDtl" class="c-input" type="text" placeholder="상세 주소 입력" title="상세 주소 입력" value="" readonly="">
                   </div>
                 </div>
                 <!-- 명세서 수신유형 -->
                 <div class="c-form _divAssigneeReqInfo" style="display:none">
                   <span class="c-form__title">명세서 수신 유형</span>
                   <div class="c-check-wrap">
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button c-radio--button--type2" id="cstmrBillSendCode03" value="MB" type="radio" name="cstmrBillSendCode" checked >
                       <label class="c-label" for="cstmrBillSendCode03">
                         <i class="c-icon c-icon--mobile-bill" aria-hidden="true"></i>
                         <span> 모바일<br>명세서(MMS)</span>
                       </label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button c-radio--button--type2" id="cstmrBillSendCode02" value="CB" type="radio" name="cstmrBillSendCode" >
                       <label class="c-label" for="cstmrBillSendCode02">
                         <i class="c-icon c-icon--email-bill" aria-hidden="true"></i>
                         <span> 이메일
                           <br>명세서
                         </span>
                       </label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button c-radio--button--type2" id="cstmrBillSendCode01" value="LX" type="radio" name="cstmrBillSendCode" >
                       <label class="c-label" for="cstmrBillSendCode01">
                         <i class="c-icon c-icon--post-bill" aria-hidden="true"></i>
                         <span>우편
                           <br>명세서
                         </span>
                       </label>
                     </div>
                   </div>
                   <ul class="c-text-list c-bullet c-bullet--dot">
                     <li class="c-text-list__item u-co-gray">이메일, 모바일(MMS) 명세서를 선택하시면 청구내역을 빠르고 정확하게 받아보실 수 있습니다.</li>
                   </ul>
                 </div>
                 <div class="c-form" id="divEmail" style="display:none">
                   <span class="c-form__title">이메일</span>
                   <div class="c-form__input">
                     <input class="c-input" id="cstmrMail" type="text" placeholder="예: ktm@ktm.com" title="이메일주소 입력" value="" maxlength="50">
                     <label class="c-form__label" for="cstmrMail">이메일</label>
                   </div>
                 </div>
                 <!-- 요금 납부 방법 -->
                 <div class="c-form _divAssigneeReqInfo" style="display:none">
                   <span class="c-form__title">요금 납부 방법</span>
                   <div class="c-check-wrap">
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button" id="reqPayType01" value="D" type="radio" name="reqPayType" checked>
                       <label class="c-label" for="reqPayType01">자동이체</label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button" id="reqPayType02" value="C"  type="radio" name="reqPayType" >
                       <label class="c-label" for="reqPayType02">신용카드</label>
                     </div>
                   </div>
                   <!-- case : 자동이체일 경우-->
                   <div class="c-form__group _bank" role="group" aria-labelledby="inpBankNum">
                     <div class="c-form__select has-value">
                       <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />
                       <select id="reqBank" class="c-select">
                         <option value="">은행명 선택</option>
                         <c:forEach items="${bankList }" var="itemBank" >
                           <option value="${itemBank.dtlCd }" >${itemBank.dtlCdNm }</option>
                         </c:forEach>
                       </select>
                      <label class="c-form__label">은행명</label>
                     </div>
                     <div class="c-form__input">
                       <input class="c-input onlyNum" id="reqAccountNumber" maxlength='20' type="tel" name="" pattern="[0-9]*" placeholder="'-'없이 입력" value="" >
                       <!--[2021-11-22] 인풋타입 변경-->
                       <label class="c-form__label onlyNum" for="reqAccountNumber">계좌번호</label>
                     </div>
                   </div>
                   <ul class="c-text-list c-bullet c-bullet--dot _bank">
                     <li class="c-text-list__item u-co-gray">납부방법은 본인명의의 카드 또는 계좌로만 등록 가능합니다.(미성년자의 경우 법정대리인 명의 포함)</li>
                     <li class="c-text-list__item u-co-gray">매월 21일 납부</li>
                     <li class="c-text-list__item u-co-gray">평생계좌(개인 핸드폰번호를 계좌번호로 사용하는 계좌)는 이용 불가능합니다.</li>
                   </ul>
                   <div class="c-button-wrap _bank">
                     <button id="btnCheckAccount" class="c-button c-button--full c-button--mint">계좌번호 유효성 체크</button>
                   </div>

                   <!-- case : 신용카드 일 경우-->
                   <div class="c-form__group _card" role="group" aria-labeledby="inpNum" style="display:none">
                     <div class="c-form__select">
                       <select id="reqCardCompany" class="c-select">
                         <option value="" >카드사 선택</option>
                         <option value="비씨카드" > 비씨카드</option>
                         <option value="국민카드" > 국민카드</option>
                         <option value="롯데카드" > 롯데카드</option>
                         <option value="외환카드" > 외환카드</option>
                         <option value="삼성카드" > 삼성카드</option>
                         <option value="하나카드" > 하나카드</option>
                         <option value="농협카드" > 농협카드</option>
                         <option value="현대카드" > 현대카드</option>
                         <option value="신한카드" > 신한카드</option>
                         <option value="씨티카드" > 씨티카드</option>
                         <option value="한미카드" > 한미카드</option>
                         <option value="수협카드" > 수협카드</option>
                         <option value="우리카드" > 우리카드</option>
                         <option value="제주카드" > 제주카드</option>
                         <option value="광주카드" > 광주카드</option>
                         <option value="전북카드" > 전북카드</option>
                       </select>
                       <label class="c-form__label">카드사명</label>
                     </div>
                     <div class="c-form__input">
                       <input class="c-input onlyNum" id="reqCardNo" maxlength="16" type="tel" name="" pattern="[0-9]*" placeholder="'-'없이 입력" value="" >
                       <!--[2021-11-22] 인풋타입 변경-->
                       <label class="c-form__label" for="reqCardNo">카드번호</label>
                     </div>
                     <span class="c-form__title">유효기간</span>
                     <div class="c-select-wrap">
                       <select id="reqCardMm" class="c-select">
                         <option value="" >MM</option>
                         <option value="01" >01</option>
                         <option value="02" >02</option>
                         <option value="03" >03</option>
                         <option value="04" >04</option>
                         <option value="05" >05</option>
                         <option value="06" >06</option>
                         <option value="07" >07</option>
                         <option value="08" >08</option>
                         <option value="09" >09</option>
                         <option value="10" >10</option>
                         <option value="11" >11</option>
                         <option value="12" >12</option>
                       </select>
                       <label class="c-form__label c-hidden" for="reqCardMm">월</label>
                       <fmt:formatDate value="${nmcp:getDateToCurrent(0)}" pattern="yyyy" var="nowYear" />
                       <select id="reqCardYy" class="c-select">
                         <option value="">YYYY</option>
                         <c:forEach begin="0" end="10" var="item">
                           <option value="${nowYear+item}" >${nowYear+item}</option>
                         </c:forEach>
                       </select>
                       <label class="c-form__label c-hidden" for="reqCardYy" >년</label>
                     </div>
                   </div>
                   <ul class="c-text-list c-bullet c-bullet--dot _card" style="display:none" >
                     <li class="c-text-list__item u-co-gray">납부방법은 본인명의의 카드 또는 계좌로만 등록 가능합니다.(미성년자의 경우 법정대리인 명의 포함)</li>
                      <li class="c-text-list__item u-co-gray">1회차 매월 10~11일경.</li>
                   </ul>
                   <div class="c-button-wrap _card" style="display:none">
                     <button id="btnCheckCardNo" class="c-button c-button--full c-button--mint">신용카드 유효성 체크</button>
                   </div>
                 </div>

                 <!-- 신분증 확인 -->
                 <div class="c-form _selfCert" style="display:none">
                   <span class="c-form__title">신분증 확인 수단</span>
                   <div class="c-check-wrap c-check-wrap--type2">
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button" id="selfCertType1" value="01" type="radio" name="selfCertType">
                       <label class="c-label" for="selfCertType1">주민등록증</label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button" id="selfCertType2" value="02" type="radio" name="selfCertType" >
                       <label class="c-label" for="selfCertType2">운전면허증</label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button" id="selfCertType3" value="03" type="radio" name="selfCertType" >
                       <label class="c-label" for="selfCertType3">장애인 등록증</label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button" id="selfCertType4" value="04" type="radio" name="selfCertType" >
                       <label class="c-label" for="selfCertType4">국가 유공자증</label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button" id="selfCertType6" value="06" type="radio" name="selfCertType" >
                       <label class="c-label" for="selfCertType6">외국인 등록증</label>
                     </div>
                     <div class="c-chk-wrap">
                       <input class="c-radio c-radio--button _passport" id="selfCertType5" value="05" type="radio" name="selfCertType"  disabled>
                       <label class="c-label c-hidden" for="selfCertType5">여권</label>
                     </div>
                   </div>
                 </div>

                 <div class="c-form _isFathCert" style="display:none;">
                   <button id="btnFathUrlRqt" class="c-button c-button--h48 c-button--mint c-button--full u-mt--32">안면인증 URL 받기</button>
                   <div class="c-button-wra u-mt--32">
                     <button id="btnFathTxnRetv" class="c-button c-button--h48 c-button--mint c-button--full" disabled>안면인증 결과 확인</button>
                     <button id="btnFathSkip" class="c-button c-button--h48 c-button--gray c-button--full u-mt--8" onclick="requestFathSkip();" disabled>안면인증 SKIP</button>
                   </div>
                 </div>
                 
                 <div class="c-form c-scan-wrap _chkIdCardTitle" style="display:none;">
                   <div class="c-box u-mt--32">
                     <img id="imgRadIdCard" class="center-img" src="/resources/images/mobile/content/img_jumin_card.png" alt="">
                   </div>
                   <div class="c-button-wrap c-button-wrap--right _divScan">
                     <button class="c-button _ocrRecord" data-type="social" >
                       <span class="c-text c-text--type3 u-co-sub-4">스캔하기</span>
                       <i class="c-icon c-icon--scan" aria-hidden="true"></i>
                     </button>
                   </div>
                 </div>

                 <div class="c-form__select _cstmrForeignerNation" style="display:none;">
                    <select class="c-select" id="cstmrForeignerNation" name="cstmrForeignerNation">

                    </select>
                    <label class="c-form__label" for="cstmrForeignerNation">국가</label>
                    <input type="hidden" id="cstmrForeignerNationTmp" name="cstmrForeignerNationTmp" value="">
                 </div>

                 <!-- 운전면허증 선택 시 노출-->
                 <div class="c-form _driverSelfIssuNumF" style="display:none;">
                   <div class="c-form__input">
                     <input id="driverSelfIssuNum" class="c-input" type="tel" name="driverSelfIssuNum" maxlength="15" placeholder="면허번호 입력"  value="">
                     <label class="c-form__label" for="selfIssuNum">면허번호</label>
                   </div>
                   <input  type="hidden" id="driverSelfIssuNum1">
                   <input type="hidden"  id="driverSelfIssuNum2">
                 </div>

                 <div class="c-form _selfIssuNumF" style="display:none;">
                   <div class="c-form__input">
                     <input class="c-input" id="selfIssuNum" type="tel"  pattern="[0-9]*" placeholder="보훈번호 입력" title="보훈번호"  value="" >
                     <!--[2021-11-22] 인풋타입 변경-->
                     <label class="c-form__label" for="selfIssuNum">보훈번호</label>
                   </div>
                 </div>

                 <div class="c-form c-form--datepicker _selfIssuExprDt" style="display:none;">
                   <div class="c-form__input " >
                     <input class="c-input date-input" id="selfIssuExprDt" type="text" placeholder="YYYY-MM-DD" title="발급일자"  maxlength="10" value="" >
                     <label class="c-form__label" for="selfIssuExprDt">발급일자</label>
                     <button class="c-button">
                       <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                     </button>
                   </div>
                 </div>


                 <div class="c-form _selfInqryAgrmYnFlag" style="display:none;">
                   <input class="c-checkbox" id="selfInqryAgrmYnFlag" type="checkbox" style="display:none;">
                   <label class="c-label u-mt--20" for="selfInqryAgrmYnFlag">본인 조회에 동의합니다</label>
                   <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                     <li class="c-text-list__item">입력하신 정보는 한국정보통신진흥협회(KAIT)에서 제공하는 부정가입방지 시스템을 통해 신분증 진위여부 판단에 이용됩니다.</li>
                   </ul>
                 </div>


                 <div class="c-form">
                   <span class="c-form__title">연락가능 연락처</span>
                   <div class="c-form__input">
                     <input class="c-input onlyNum" id="cstmrReceiveTel" type="tel" name="cstmrReceiveTel" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="">
                     <label class="c-form__label" for="cstmrReceiveTel">연락가능 연락처</label>
                   </div>
                   <ul class="c-text-list c-bullet c-bullet--fyr">
                     <li class="c-text-list__item u-co-red">해당 번호로 명의변경 신청내용 확인 전화를 드립니다.</li>
                   </ul>
                 </div>

                 <div class="c-form">
                   <span class="c-form__title">※명의변경 완료 이후에 다이렉트몰 내 마이페이지 또는 고객센터 상담원을 통해 가입정보 변경이 가능합니다.</span>
                 </div>
                 <div id="divButtonStep1" class="c-button-wrap">
                   <a class="c-button c-button--full c-button--primary" id="btnAssignee">양수인 신청하기</a>
                 </div>
               </div>
             </div>
           </div>
         </div>
       </div>

      <!-- Layer popup -->
      <div class="c-modal" id="simpleDialog" role="dialog" tabindex="-1" aria-describedby="#simpleTitle">

        <div class="c-modal__dialog c-modal__dialog--full" role="document">
          <div class="c-modal__content">
            <div class="c-modal__header">
              <h1 class="c-modal__title _title" >오류 TITLE</h1>
              <button class="c-button" data-dialog-close>
                <i class="c-icon c-icon--close" aria-hidden="true"></i>
                <span class="c-hidden">팝업닫기</span>
              </button>
            </div>
            <div class="c-indicator">
              <span style="width: 33.33%"></span>
            </div>

            <div class="c-modal__body">
              <h3 class="c-heading c-heading--type1"></h3>
              <p class="c-text c-text--type2 u-co-gray _detail">
              </p>

              <div id="divChangeButton" class="c-button-wrap _simpleDialogButton">
                
                
              </div>
            </div>


          </div>
        </div>


      </div>

        <!-- 아코디언 서브 스크립트 -->
        <script>
            $(document).on('click', '.c-accordion.c-accordion--nested__sub > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button', function() {
                var $btn = $(this);
                var $panel = $btn.closest('.c-accordion__item').children('.c-accordion__panel');
                var isActive = $btn.hasClass('is-active');

                $btn.toggleClass('is-active', !isActive);
                $btn.attr('aria-expanded', !isActive);

                $panel.stop()[isActive ? 'slideUp' : 'slideDown']();
                $panel.attr('aria-hidden', isActive);
            });
        </script>
    </t:putAttribute>
</t:insertDefinition>