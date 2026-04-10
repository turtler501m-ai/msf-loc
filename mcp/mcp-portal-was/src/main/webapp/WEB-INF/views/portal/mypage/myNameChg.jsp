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

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">명의변경 </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
        <script type="text/javascript" src="/resources/js/portal/mypage/myNameChg.js?version=26-03-03"></script>
        <script type="text/javascript">
        history.pushState(null, null,"/mypage/myNameChg.do");
        window.onpopstate = function (event){
            history.go("/mypage/myNameChg.do");
        }
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <form name="selfFrm" id="selfFrm" action="/mypage/myNameChg.do" method="post">
            <input type="hidden" name="ncn" value="" >
        </form>

        <input type="hidden" id="isAuth" name="isAuth">  <!-- 양도인 본인인증 -->
        <input type="hidden" id="isAuth2" name="isAuth2"> <!-- 양수인 본인인증 -->
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
          <div class="ly-page--title">
            <h2 class="c-page--title">명의변경</h2>
          </div>

          <div class="chgName-info">
            <div class="chgName-info__tit">
              <h3>kt M모바일에서 간편하게 명의변경하세요!</h3>
              <p><b>유심교체 없이</b> 보유중인 핸드폰을 가족명의로 변경할 수 있는 서비스입니다.</p>
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
                  <p>본인인증, 약관동의 등 명의 양도 신청</p>
                </li>
                <li class="chgName-guide__item">
                  <strong><span class="chgName-guide__step">2</span>명의 받는 고객님</strong>
                  <p>본인인증, 신청정보 작성 등 명의 양수신청</p>
                </li>
                <li class="chgName-guide__item">
                  <strong><span class="chgName-guide__step">3</span>고객센터</strong>
                  <p>두분 고객님께 전화드려 신청정보 확인 및 가족관계 증명서 요청</p>
                </li>
                <li class="chgName-guide__item">
                  <strong><span class="chgName-guide__step">4</span>고객님</strong>
                  <p>양도/양수인 중 한분께서 가족관계 증명서를 고객센터에 송부</p>
                </li>
                <li class="chgName-guide__item">
                  <strong><span class="chgName-guide__step">5</span>고객센터</strong>
                  <p>구비서류 확인하여 고객님께 전화드리고 명의변경 완료</p>
                </li>
              </ul>
            </div>
            <div class="chgName-guide">
              <h3>신청상태</h3>
              <div class="c-table u-mt--16">
                <table>
                  <caption>명의변경 회선 선택, 양도인 신청 상태, 양수인 신청 상태로 신청상태 정보</caption>
                  <colgroup>
                    <col>
                    <col>
                    <col>
                  </colgroup>
                  <thead>
                    <tr>
                      <th scope="col">명의변경 회선 선택</th>
                      <th scope="col">양도인 신청 상태</th>
                      <th scope="col">양수인 신청 상태</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr class="u-h--90">
                      <td>
                        <div class="c-form--line u-mt--m8">
                          <label class="c-label c-hidden" for="selContractNum">회선 선택</label>
                          <select class="c-select" name="selContractNum" id="selContractNum">
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
                        </div>
                      </td>
                      <td id="tdGrReqStat">
                        <button class="c-button c-button--sm c-button--white" id="btnGrReq">신청하기</button>
                      </td>
                      <td id="tdAsReqStat">
                        <button class="c-button c-button--sm c-button--white" id="btnAsReq">신청하기</button>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <ul class="c-text-list c-bullet c-bullet--fyr">
                  <li class="c-text-list__item u-co-gray">양도인, 양수인 모두 신청을 완료해야 명의변경 신청이 가능합니다.</li>
                </ul>
              </div>
              <div class="c-button-wrap u-mt--40">
                <button class="c-button c-button--lg c-button--primary c-button--w460 is-disabled" id="btnNameChg" disabled>명의변경 신청하기</button>
              </div>
            </div>
          </div>

          <!-- 양도인 신청하기 -->
          <div class="chgName-grantor" id="divGrReq" style="display:none">
            <div class="chgName-grantor__tit">
              <h3>양도인 신청하기</h3>
              <hr class="c-hr c-hr--title">
            </div>

            <div class="c-form-wrap u-mt--48">
              <div class="c-form-group" role="group" aira-labelledby="grCstmrTitle">
                <h4 class="c-group--head" id="grCstmrTitle">양도인 유형</h4>
                <div class="c-checktype-row c-col3 u-mt--0">
                  <input class="c-radio c-radio--button c-radio--button--type1" id="grCstmrType1" type="radio" name="grCstmrType" value="${Constants.CSTMR_TYPE_NA}" disabled>
                  <label class="c-label" for="grCstmrType1">
                    <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                    <span class="u-ml--4">내국인</span>
                  </label>
                  <input class="c-radio c-radio--button c-radio--button--type1" id="grCstmrType2" type="radio" name="grCstmrType" value="${Constants.CSTMR_TYPE_NM}" disabled>
                  <label class="c-label" for="grCstmrType2">
                    <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                    <span class="app-form-view__lable">미성년자<br/><span class="u-fs-14">(19세 미만)</span></span>
                  </label>
                  <input class="c-radio c-radio--button c-radio--button--type1" id="grCstmrType3" type="radio" name="grCstmrType" value="${Constants.CSTMR_TYPE_FN}" disabled>
                  <label class="c-label" for="grCstmrType3">
                    <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                    <span class="app-form-view__lable">외국인<br/><span class="u-fs-14">(Foreigner)</span></span>
                  </label>
                </div>
              </div>
            </div>
            
            <div class="c-form-wrap u-mt--48">
              <div class="c-form-group" role="group" aira-labelledby="grInpNameCertiTitle">
                <h4 class="c-group--head" id="grInpNameCertiTitle">실명 및 본인인증</h4>
                <div class="c-form-row c-col2 u-mt--0">
                  <div class="c-form-field">
                    <div class="c-form__input" >
                      <input class="c-input" id="grCstmrName" name="grCstmrName" type="text" placeholder="이름 입력" value="" maxlength="60">
                      <label class="c-form__label" for="grCstmrName">이름</label>
                    </div>
                  </div>
                  <div class="c-form-field _grIsDefault">
                    <div class="c-form__input-group u-mt--0">
                      <div class="c-form__input c-form__input-division" >
                        <input class="c-input--div2 onlyNum" id="grCstmrNativeRrn01" name="grCstmrNativeRrn01" type="text" autocomplete="false" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="" onkeyup="nextFocus(this, 6, 'grCstmrNativeRrn02');" >
                        <span>-</span>
                        <input class="c-input--div2 onlyNum" id="grCstmrNativeRrn02" name="grCstmrNativeRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="" >
                        <label class="c-form__label" for="grCstmrNativeRrn01">주민등록번호</label>
                      </div>
                    </div>
                  </div>

                  <div class="c-form-field _grIsForeigner" style="display:none;">
                    <div class="c-form__input-group u-mt--0" >
                      <div class="c-form__input c-form__input-division">
                        <input class="c-input--div2 onlyNum" id="grCstmrForeignerRrn01" name="grCstmrForeignerRrn01" type="text" autocomplete="false" maxlength="6" placeholder="외국인등록번호 앞자리" title="외국인등록번호 앞자리" value="" onkeyup="nextFocus(this, 6, 'grCstmrForeignerRrn02');" >
                        <span>-</span>
                        <input class="c-input--div2 onlyNum" id="grCstmrForeignerRrn02" name="grCstmrForeignerRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="외국인등록번호 뒷자리" title="외국인등록번호 뒷자리" value="" >
                        <label class="c-form__label" for="grCstmrForeignerRrn01">외국인 등록번호</label>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="c-box c-box--type1 c-box--bullet _grIsTeen" style="display:none;">
              <ul class="c-text-list c-bullet c-bullet--dot">
                <li class="c-text-list__item">영/유아(0세~4세 이하)의 경우 가입신청이 제한되므로 타 명의로 진행 바랍니다.</li>
              </ul>
            </div>

            <div class="c-box c-box--type1 c-box--bullet _grIsForeigner" style="display:none;">
              <ul class="c-text-list c-bullet c-bullet--dot">
                <li class="c-text-list__item">외국인 등록증과 동일하게 작성해주세요.</li>
              </ul>
            </div>
            
            <div class="c-form-wrap _grIsTeen " style="display:none;">
              <div class="c-form-group" role="group" aira-labelledby="grInpDepInfoTitle">
                <h3 class="c-form__title--type2" id="grInpDepInfoTitle">법정대리인 정보</h3>
                <div class="c-form-row c-col2">
                  <div class="c-form-field">
                    <div class="c-form__input u-mt--0">
                      <input class="c-input" id="grMinorAgentName" type="text" placeholder="법정대리인 성명" aria-invalid="true" value="" maxlength=60 >
                      <label class="c-form__label" for="grMinorAgentName">법정대리인 성명</label>
                    </div>
                  </div>
                  <div class="c-form-field">
                    <div class="c-form__select u-mt--0">
                      <select id="grMinorAgentRelation" class="c-select">
                        <option value="">선택</option>
                        <option value="01">부</option>
                        <option value="02">모</option>
                        <option value="03">후견인</option>
                        <option value="04">연대보증인</option>
                      </select>
                      <label class="c-form__label" for="grMinorAgentRelation">신청인과의 관계</label>
                    </div>
                  </div>
                </div>
                <div class="c-form-row c-col2">
                  <div class="c-form-field">

                    <div class="c-form__input-group">
                      <div class="c-form__input c-form__input-division">
                        <input class="c-input--div2 onlyNum" id="grMinorAgentRrn01" autocomplete="off" type="text" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="" onkeyup="nextFocus(this, 6, 'grMinorAgentRrn02');" >
                        <span>-</span>
                        <input class="c-input--div2 onlyNum" id="grMinorAgentRrn02" autocomplete="off" type="password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="" onkeyup="nextFocus(this, 7, 'grMinorAgentTelFn');" >
                        <label class="c-form__label" for="grMinorAgentRrn01">법정대리인 주민등록번호</label>
                      </div>
                    </div>
                  </div>
                  <div class="c-form-field">
                    <div class="c-form__input-group">
                      <div class="c-form__input c-form__input-division">
                        <input class="c-input--div3 onlyNum" id="grMinorAgentTelFn" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="전화번호 첫자리" value="" onkeyup="nextFocus(this, 3, 'grMinorAgentTelMn');" >
                        <span>-</span>
                        <input class="c-input--div3 onlyNum" id="grMinorAgentTelMn" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 중간자리" value="" onkeyup="nextFocus(this, 4, 'grMinorAgentTelRn');" >
                        <span>-</span>
                        <input class="c-input--div3 onlyNum" id="grMinorAgentTelRn" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 뒷자리" value="" >
                        <label class="c-form__label" for="grMinorAgentTelFn">법정대리인 전화번호 <em class="c-form__sublabel">(휴대폰)</em></label>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <h5 class="c-form__title--type2">법정대리인 안내사항 확인 및 동의</h5>
              <div class="c-agree c-agree--type1">
                <div class="c-agree__item">
                  <button class="c-button c-button--xsm" onclick="fnSetEventId('grMinorAgentAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MINOR_AGENT}',0);" >
                    <span class="c-hidden">법정대리인 안내사항 확인 및 동의(필수) 상세 팝업 열기</span>
                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                  </button>
                  <div class="c-agree__inner">
                    <input class="c-checkbox c-checkbox--type2 " id="grMinorAgentAgrmYn" type="checkbox" >
                    <label class="c-label" for="grMinorAgentAgrmYn">본인은 안내사항을 확인하였습니다.<span class="u-co-gray">(필수)</span>
                    </label>
                  </div>
                </div>
              </div>
            </div>

            <!-- 양도인 본인인증 방법 선택 -->
            <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
                <jsp:param name="controlYn" value="N"/>
                <jsp:param name="reqAuth" value="CNATX"/>
            </jsp:include>

            <div class="c-form-wrap u-mt--48">
              <div class="c-form-group" role="group" aira-labelledby="inpSendPhoneTitle">
                <h4 class="c-group--head c-flex--jfy-start" id="inpSendPhoneTitle">양도인 미납을 위한 연락처 <span class="u-co-gray u-ml--12">※미납시 연락을 위한 휴대폰번호(명의변경 회선 사용불가)</span></h4>
              </div>
              <div class="c-form-row c-col2 u-width--460">
                <div class="c-form-field">
                  <div class="c-form__input-group">
                    <div class="c-form__input c-form__input-division">
                      <input class="c-input--div3 onlyNum" id="cstmrSendTelFn" type="text" maxlength="3" pattern="[0-9]*" placeholder="숫자입력" title="휴대폰 첫자리" onkeyup="nextFocus(this, 3, 'cstmrSendTelMn');" >
                      <span>-</span>
                      <input class="c-input--div3 onlyNum" id="cstmrSendTelMn" type="text" maxlength="4" pattern="[0-9]*" placeholder="숫자입력" title="휴대폰 중간자리" onkeyup="nextFocus(this, 4, 'cstmrSendTelRn');" >
                      <span>-</span>
                      <input class="c-input--div3 onlyNum" id="cstmrSendTelRn" type="text" maxlength="4" pattern="[0-9]*" placeholder="숫자입력" title="휴대폰 뒷자리">
                      <label class="c-form__label" for="cstmrSendTelFn">연락가능 연락처</label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            
            <div class="c-form-wrap u-mt--48">
              <h4 class="c-form__title">약관동의</h4>
              <div class="c-agree c-agree--type2">
                <div class="c-agree__item">
                  <input class="c-checkbox" id="btnGrAllCheck" type="checkbox">
                  <label class="c-label" for="btnGrAllCheck">전체 약관에 동의합니다.</label>
                </div>
                <div class="c-agree__inside">
                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('notice1');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_26}',0);" >
                      <span class="c-hidden">혜택 소멸(고지) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _grAgree" id="notice1" type="checkbox"  code="${Constants.NOTICE_CODE_26}"  cdGroupId1=FORMINFO docVer="0" value="Y" validityyn="Y" >
                    <label class="c-label" for="notice1">혜택 소멸<span class="u-co-gray">(고지)</span>
                    </label>
                  </div>
                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseCntrDelFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_CNTR_DEL_CODE}',0);" >
                      <span class="c-hidden">고객정보 삭제(필수) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _grAgree" id="clauseCntrDelFlag" type="checkbox"  code="${Constants.CLAUSE_CNTR_DEL_CODE}"  cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                    <label class="c-label" for="clauseCntrDelFlag">고객정보 삭제<span class="u-co-gray">(필수)</span>
                    </label>
                  </div>
                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('notice2');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_25}',0);" >
                      <span class="c-hidden">기타 안내(고지) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _grAgree" id="notice2" type="checkbox"  code="${Constants.NOTICE_CODE_25}"  cdGroupId1=FORMINFO docVer="0" value="Y" validityyn="Y" >
                    <label class="c-label" for="notice2">기타 안내<span class="u-co-gray">(고지)</span>
                    </label>
                  </div>
                </div>
              </div>
            </div>
            <div class="c-button-wrap u-mt--60">
              <button class="c-button c-button--lg c-button--primary c-button--w460" id="btnGrantor">양도인 신청하기</button>
            </div>
          </div>

          <!-- 양수인 신청하기 -->
          <div class="chgName-assignee" id="divAsReq" style="display:none">
            <div class="chgName-assignee__tit">
              <h3>양수인 신청하기</h3>
              <hr class="c-hr c-hr--title">
            </div>

            <div class="c-form-wrap u-mt--48">
              <div class="c-form-group" role="group" aira-labelledby="cstmrTitle">
                <h4 class="c-group--head" id="cstmrTitle">양수인 유형</h4>
                <div class="c-checktype-row c-col3 u-mt--0">
                  <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType1" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NA}">
                  <label class="c-label" for="cstmrType1">
                    <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                    <span class="u-ml--4">내국인</span>
                  </label>
                  <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType2" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NM}">
                  <label class="c-label" for="cstmrType2">
                    <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                    <span class="app-form-view__lable">미성년자<br/><span class="u-fs-14">(19세 미만)</span></span>
                  </label>
                  <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}">
                  <label class="c-label" for="cstmrType3">
                    <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                    <span class="app-form-view__lable">외국인<br/><span class="u-fs-14">(Foreigner)</span></span>
                  </label>
                </div>
              </div>
            </div>
            
            <div class="c-form-wrap u-mt--48">
              <div class="c-form-group" role="group" aira-labelledby="inpNameCertiTitle">
                <h4 class="c-group--head" id="inpNameCertiTitle">실명 및 본인인증</h4>
                <div class="c-form-row c-col2 u-mt--0">
                  <div class="c-form-field">
                    <div class="c-form__input" >
                      <input class="c-input" id="cstmrName" name="cstmrName" type="text" placeholder="이름 입력" value="" maxlength="60">
                      <label class="c-form__label" for="cstmrName">이름</label>
                    </div>
                  </div>
                  <div class="c-form-field _isDefault">
                    <div class="c-form__input-group u-mt--0">
                      <div class="c-form__input c-form__input-division" >
                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" type="text" autocomplete="false" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                        <span>-</span>
                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="" >
                        <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                      </div>
                    </div>
                  </div>

                  <div class="c-form-field _isForeigner" style="display:none;">
                    <div class="c-form__input-group u-mt--0" >
                      <div class="c-form__input c-form__input-division">
                        <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn01" name="cstmrForeignerRrn01" type="text" autocomplete="false" maxlength="6" placeholder="외국인등록번호 앞자리" title="외국인등록번호 앞자리" value="" onkeyup="nextFocus(this, 6, 'cstmrForeignerRrn02');" >
                        <span>-</span>
                        <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn02" name="cstmrForeignerRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="외국인등록번호 뒷자리" title="외국인등록번호 뒷자리" value="" >
                        <label class="c-form__label" for="cstmrForeignerRrn01">외국인 등록번호</label>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="c-box c-box--type1 c-box--bullet _isTeen" style="display:none;">
              <ul class="c-text-list c-bullet c-bullet--dot">
                <li class="c-text-list__item">영/유아(0세~4세 이하)의 경우 가입신청이 제한되므로 타 명의로 진행 바랍니다.</li>
              </ul>
            </div>

            <div class="c-box c-box--type1 c-box--bullet _isForeigner" style="display:none;">
              <ul class="c-text-list c-bullet c-bullet--dot">
                <li class="c-text-list__item">외국인 등록증과 동일하게 작성해주세요.</li>
              </ul>
            </div>

            <div class="c-form-wrap _isTeen " style="display:none;">
              <div class="c-form-group" role="group" aira-labelledby="inpDepInfoTitle">
                <h3 class="c-form__title--type2" id="inpDepInfoTitle">법정대리인 정보</h3>
                <div class="c-form-row c-col2">
                  <div class="c-form-field">
                    <div class="c-form__input u-mt--0">
                      <input class="c-input" id="minorAgentName" type="text" placeholder="법정대리인 성명" aria-invalid="true" value="" maxlength=60 >
                      <label class="c-form__label" for="minorAgentName">법정대리인 성명</label>
                    </div>
                  </div>
                  <div class="c-form-field">
                    <div class="c-form__select u-mt--0">
                      <select id="minorAgentRelation" class="c-select">
                        <option value="">선택</option>
                        <option value="01">부</option>
                        <option value="02">모</option>
                        <option value="03">후견인</option>
                        <option value="04">연대보증인</option>
                      </select>
                      <label class="c-form__label" for="minorAgentRelation">신청인과의 관계</label>
                    </div>
                  </div>
                </div>
                <div class="c-form-row c-col2">
                  <div class="c-form-field">

                    <div class="c-form__input-group">
                      <div class="c-form__input c-form__input-division">
                        <input class="c-input--div2 onlyNum" id="minorAgentRrn01" autocomplete="off" type="text" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="" onkeyup="nextFocus(this, 6, 'minorAgentRrn02');" >
                        <span>-</span>
                        <input class="c-input--div2 onlyNum" id="minorAgentRrn02" autocomplete="off" type="password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="" onkeyup="nextFocus(this, 7, 'minorAgentTelFn');" >
                        <label class="c-form__label" for="minorAgentRrn01">법정대리인 주민등록번호</label>
                      </div>
                    </div>
                  </div>
                  <div class="c-form-field">
                    <div class="c-form__input-group">
                      <div class="c-form__input c-form__input-division">
                        <input class="c-input--div3 onlyNum" id="minorAgentTelFn" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="전화번호 첫자리" value="" onkeyup="nextFocus(this, 3, 'minorAgentTelMn');" >
                        <span>-</span>
                        <input class="c-input--div3 onlyNum" id="minorAgentTelMn" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 중간자리" value="" onkeyup="nextFocus(this, 4, 'minorAgentTelRn');" >
                        <span>-</span>
                        <input class="c-input--div3 onlyNum" id="minorAgentTelRn" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 뒷자리" value="" >
                        <label class="c-form__label" for="minorAgentTelFn">법정대리인 전화번호 <em class="c-form__sublabel">(휴대폰)</em></label>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <h5 class="c-form__title--type2">법정대리인 안내사항 확인 및 동의</h5>
              <div class="c-agree c-agree--type1">
                <div class="c-agree__item">
                  <button class="c-button c-button--xsm" onclick="fnSetEventId('minorAgentAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MINOR_AGENT}',0);" >
                    <span class="c-hidden">법정대리인 안내사항 확인 및 동의(필수) 상세 팝업 열기</span>
                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                  </button>
                  <div class="c-agree__inner">
                    <input class="c-checkbox c-checkbox--type2 " id="minorAgentAgrmYn" type="checkbox" >
                    <label class="c-label" for="minorAgentAgrmYn">본인은 안내사항을 확인하였습니다.<span class="u-co-gray">(필수)</span>
                    </label>
                  </div>
                </div>
              </div>
            </div>

            <!-- 양수인 본인인증 방법 선택 -->
            <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
                <jsp:param name="controlYn" value="N"/>
                <jsp:param name="reqAuth" value="CNATX"/>
                <jsp:param name="spIndex" value="2"/>
            </jsp:include>

<%--             <div class="c-form-wrap u-mt--48">
              <h4 class="c-form__title">약관동의</h4>
              <div class="c-agree c-agree--type2">
                <div class="c-agree__item">
                  <input class="c-checkbox" id="btnAsAllCheck" type="checkbox">
                  <label class="c-label" for="btnAsAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.</label>
                </div>
                <div class="c-agree__inside">
                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('notice3');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_27}',0);" >
                      <span class="c-hidden">명의변경 주요안내(고지) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _agree" id="notice3" type="checkbox" code="${Constants.NOTICE_CODE_27}" cdGroupId1=FORMINFO docVer="0" value="Y" validityyn="Y" >
                    <label class="c-label" for="notice3">명의변경 주요안내<span class="u-co-gray">(고지)</span>
                    </label>
                  </div>

                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}',0);" >
                      <span class="c-hidden">개인정보 수집·이용 동의(필수) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriCollectFlag" type="checkbox" code="${Constants.CLAUSE_PRI_COLLECT_CODE}" cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                    <label class="c-label" for="clausePriCollectFlag">개인정보 수집 &middot; 이용 동의<span class="u-co-gray">(필수)</span>
                    </label>
                  </div>

                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}',0);">
                      <span class="c-hidden">개인정보의 제공 동의(필수) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriOfferFlag" type="checkbox" code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                    <label class="c-label" for="clausePriOfferFlag">개인정보의 제공 동의<span class="u-co-gray">(필수)</span>
                    </label>
                  </div>

                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseEssCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}',0);">
                      <span class="c-hidden">고유식별정보의 수집·이용 동의(필수) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _agree" id="clauseEssCollectFlag" type="checkbox" cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y"  >
                    <label class="c-label" for="clauseEssCollectFlag">고유식별정보의 수집 &middot; 이용 동의<span class="u-co-gray">(필수)</span>
                    </label>
                  </div>

                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseConfidenceFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_CONFIDENCE_CODE}',0);">
                      <span class="c-hidden">신용정보 조회 ·이용 ·제공에 대한 동의서(필수) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _agree" id="clauseConfidenceFlag" type="checkbox" code="${Constants.CLAUSE_CONFIDENCE_CODE}" cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                    <label class="c-label" for="clauseConfidenceFlag">신용정보 조회 &middot; 이용 &middot; 제공에 대한 동의서<span class="u-co-gray">(필수)</span>
                    </label>
                  </div>

                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriAdFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_PRI_AD_CODE}',0);">
                      <span class="c-hidden">정보/광고 전송을 위한 개인정보 이용, 처리위탁 및   정보/광고 수신동의서(선택) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriAdFlag" type="checkbox" code="S${Constants.CLAUSE_PRI_AD_CODE}" cdGroupId1=FORMSELECT docVer="0" value="Y" validityyn="N">
                    <label class="c-label" for="clausePriAdFlag">정보/광고 전송을 위한 개인정보 이용, 처리위탁 및   정보/광고 수신동의서<span class="u-co-gray">(선택)</span>
                    </label>
                  </div>

                  <div class="c-chk-wrap _clauseJehuFlag" style="display:none;" >
                    <button class="c-button c-button--xsm" id="clauseJehuButton" >
                      <span class="c-hidden">제휴 서비스를 위한 동의(필수) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _agree" id="clauseJehuFlag" type="checkbox"  code="clauseJehuFlag" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="N" validityyn="Y" >
                    <label class="c-label" for="clauseJehuFlag">개인정보 제3자 제공 동의[${jehuProdName} 가입고객 필수 동의]<span class="u-co-gray">(필수)</span>
                    </label>
                  </div>

                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFinanceFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_FINANCE_FLAG}',0);">
                      <span class="c-hidden">개인(신용)정보 처리 및 보험가입 동의(선택) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _agree" id="clauseFinanceFlag" type="checkbox" code="S${Constants.CLAUSE_FINANCE_FLAG}" cdGroupId1=FORMSELECT docVer="0" value="Y" validityyn="N" >
                    <label class="c-label" for="clauseFinanceFlag">개인(신용)정보 처리 및 보험가입 동의<span class="u-co-gray">(선택)</span>
                    </label>
                  </div>

                  <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm" onclick="fnSetEventId('notice');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}',0);" >
                      <span class="c-hidden">서비스 이용약관(고지) 상세 팝업 열기</span>
                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <input class="c-checkbox c-checkbox--type2 _agree" id="notice" type="checkbox" code="S${Constants.NOTICE_CODE_16}" cdGroupId1=FORMINFO docVer="0" value="Y" validityyn="Y" >
                    <label class="c-label" for="notice">서비스 이용약관<span class="u-co-gray">(고지)</span>
                    </label>
                  </div>

                </div>
              </div>
            </div> --%>
            <div class="c-accordion c-accordion--type1 acc-agree c-accordion--nested">
              <span class="c-form__title--type2">약관동의</span>
              <div class="c-accordion__box">
                <div class="c-accordion__item">
                  <div class="c-accordion__head">
                    <button class="runtime-data-insert c-accordion__button" id="acc_agree_headerA1" type="button" aria-expanded="false" aria-controls="acc_agreeA1">
                      <span class="c-hidden">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다. ※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span>
                    </button>
                    <div class="c-accordion__check">
                      <input class="c-checkbox" id="btnAsAllCheck" type="checkbox">
                      <label class="c-label" for="btnAsAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span></label>
                    </div>
                  </div>
                  <div class="c-accordion__panel expand" id="acc_agreeA1" aria-labelledby="acc_agree_headerA1" aria-hidden="true">
                    <div class="c-accordion__inside">
                      <div class="c-accordion__sub-check">
                        <button class="c-button c-button--xsm" onclick="fnSetEventId('notice3');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_27}',0);" >
                          <span class="c-hidden">명의변경 주요안내(고지) 상세 팝업 열기</span>
                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                        <input class="c-checkbox c-checkbox--type2 _agree" id="notice3" type="checkbox" code="${Constants.NOTICE_CODE_27}" cdGroupId1=FORMINFO docVer="0" value="Y" validityyn="Y" >
                        <label class="c-label" for="notice3">명의변경 주요안내<span class="u-co-gray">(고지)</span>
                        </label>
                      </div>

                      <div class="c-accordion__sub-check">
                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}',0);">
                          <span class="c-hidden">개인정보의 제공 동의(필수) 상세 팝업 열기</span>
                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                        <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriOfferFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" >
                        <label class="c-label" for="clausePriOfferFlag">고유식별정보 수집&middot;이용 동의<span class="u-co-gray">(필수)</span>
                        </label>
                      </div>

                      <div class="c-accordion__sub-check">
                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}',0);" >
                          <span class="c-hidden">개인정보 수집·이용 동의(필수) 상세 팝업 열기</span>
                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                        <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriCollectFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                        <label class="c-label" for="clausePriCollectFlag">개인정보/신용정보 수집·이용 동의<span class="u-co-gray">(필수)</span>
                        </label>
                      </div>

                      <div class="c-accordion__sub-check" >
                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseEssCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}',0);">
                          <span class="c-hidden">고유식별정보의 수집·이용 동의(필수) 상세 팝업 열기</span>
                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseEssCollectFlag" type="checkbox"  cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y"  >
                        <label class="c-label" for="clauseEssCollectFlag">개인정보 제3자 제공 동의<span class="u-co-gray">(필수)</span>
                        </label>
                      </div>

                      <div class="c-accordion__sub-check">
                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFathFlag01');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag01',0);" >
                          <span class="c-hidden">민감정보(생체인식정보)수집 및 이용동의(필수) 상세 팝업 열기</span>
                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseFathFlag01" type="checkbox"  code="clauseFathFlag01"  cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                        <label class="c-label" for="clauseFathFlag01">민감정보(생체인식정보)수집 및 이용동의<span class="u-co-gray">(필수)</span></label>
                      </div>
                      <div class="c-accordion__sub-check">
                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFathFlag02');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag02',0);" >
                          <span class="c-hidden">민감정보(생체인식정보)조희 및 이용 / 3자 제공에 대한 동의(필수) 상세 팝업 열기</span>
                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseFathFlag02" type="checkbox"  code="clauseFathFlag02"  cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                        <label class="c-label" for="clauseFathFlag02">민감정보(생체인식정보)조희 및 이용 / 3자 제공에 대한 동의<span class="u-co-gray">(필수)</span></label>
                      </div>

                      <div class="c-accordion__sub-check">
                        <button class="c-button c-button--xsm" onclick="fnSetEventId('notice');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}',0);" >
                          <span class="c-hidden">서비스 이용약관(필수) 상세 팝업 열기</span>
                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                        <input class="c-checkbox c-checkbox--type2 _agree" id="notice" type="checkbox"  code="F${Constants.NOTICE_CODE_16}"  cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                        <label class="c-label" for="notice">서비스 이용약관<span class="u-co-gray">(필수)</span></label>
                      </div>

                      <div class="c-accordion__sub-check _clauseJehuFlag" style="display:none;" >
                        <button class="c-button c-button--xsm" id="clauseJehuButton" >
                          <span class="c-hidden">제휴 서비스를 위한 동의(필수) 상세 팝업 열기</span>
                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseJehuFlag" type="checkbox"  code="clauseJehuFlag" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="N" validityyn="Y" >
                        <label class="c-label" for="clauseJehuFlag">개인정보 제3자 제공 동의[${jehuProdName} 가입고객 필수 동의]<span class="u-co-gray">(필수)</span>
                        </label>
                      </div>

                      <div class="c-accordion c-accordion--nested__sub c-accordion__sub-check">
                        <div class="c-accordion__box">
                          <div class="c-accordion__item">
                            <div class="c-accordion__head">
                              <button class="c-accordion__button" id="head5" type="button" aria-expanded="false" aria-controls="agree5"></button>
                              <div class="c-accordion__check">
                                <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="agreeWrap5" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
                                <label class="c-label" for="agreeWrap5">고객 혜택 제공을 위한 정보수집 이용동의 및 혜택 광고의 수신 위탁 동의<span class="u-co-gray">(선택)</span>
                                </label>
                              </div>
                            </div>
                            <div class="c-accordion__panel expand" id="agree5" aria-labelledby="head5" aria-hidden="true">
                              <div class="c-accordion__inside">
                                <div class="c-accordion__sub-check">
                                  <button class="c-button c-button--xsm" onclick="fnSetEventId('personalInfoCollectAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.PERSONAL_INFO_COLLECT_AGREE}',0);">
                                    <span class="c-hidden">고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의(선택) 상세 팝업 열기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                  </button>
                                  <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="personalInfoCollectAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.PERSONAL_INFO_COLLECT_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N">
                                  <label class="c-label" for="personalInfoCollectAgree">고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의<span class="u-co-gray">(선택)</span>
                                  </label>
                                </div>
                                <div class="c-accordion__sub-check">
                                  <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriAdFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_PRI_AD_CODE}',0);">
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

                      <div class="c-accordion c-accordion--nested__sub c-accordion__sub-check">
                        <div class="c-accordion__box">
                          <div class="c-accordion__item">
                            <div class="c-accordion__head">
                              <button class="c-accordion__button" id="head6" type="button" aria-expanded="false" aria-controls="agree6"></button>
                              <div class="c-accordion__check">
                                <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="agreeWrap6" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
                                <label class="c-label" for="agreeWrap6">혜택 제공을 위한 제 3자 제공 및 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                </label>
                              </div>
                            </div>
                            <div class="c-accordion__panel expand" id="agree6" aria-labelledby="head6" aria-hidden="true">
                              <div class="c-accordion__inside">
                                <div class="c-accordion__sub-check">
                                  <button class="c-button c-button--xsm" onclick="fnSetEventId('othersTrnsAllAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.OTHERS_TRNS_ALL_AGREE}',0);initOthersTrnsAgrees()">
                                    <span class="c-hidden">혜택 제공을 위한 제 3자 제공 및 광고 수신 동의 상세 팝업 열기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                  </button>
                                  <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="othersTrnsAllAgree" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)" code="S${Constants.OTHERS_TRNS_ALL_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N">
                                  <label class="c-label" for="othersTrnsAllAgree">혜택 제공을 위한 제3자 제공 동의<span class="u-co-gray">(선택)</span>
                                  </label>
                                  <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="formOthersTrnsAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_TRNS_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5" style="display: none;">
                                  <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="formOthersTrnsKtAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_TRNS_KT_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5" style="display: none;">
                                </div>
                                <div class="c-accordion__sub-check">
                                  <button class="c-button c-button--xsm" onclick="fnSetEventId('othersAdReceiveAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.OTHERS_AD_RECEIVE_AGREE}',0);">
                                    <span class="c-hidden">제3자 제공관련 광고 수신 동의(선택) 상세 팝업 열기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                  </button>
                                  <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="othersAdReceiveAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_AD_RECEIVE_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5">
                                  <label class="c-label" for="othersAdReceiveAgree">제3자 제공관련 광고 수신 동의<span class="u-co-gray">(선택)</span>
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
            <!-- 가입정보 -->
            <div class="c-form-wrap u-mt--48" style="display:none;">
              <div class="c-form-group" role="group" aira-labelledby="join_infoTitle">
                <h4 class="c-group--head" id="join_infoTitle">가입정보</h4>
              </div>
              <div class="c-checktype-row">
                <input class="c-radio" name="reqInfoChgYn" id="reqInfoChgYn1" value="N" type="radio">
                <label class="c-label" for="reqInfoChgYn1">기존과 동일</label>
                <input class="c-radio" name="reqInfoChgYn" id="reqInfoChgYn2" value="Y" type="radio" checked>
                <label class="c-label" for="reqInfoChgYn2">가입정보 변경</label>
              </div>
            </div>

            <!-- 요금제 -->
            <div id="divAssigneeReqInfo" style="display:none">
              <div class="c-form-wrap u-mt--48">
                <div class="c-form-group" role="group" aira-labelledby="join_rateTitle">
                  <h4 class="c-group--head" id="join_rateTitle">사용중인 요금제</h4>
                </div>
                <div class="sel-fee-wrap c-flex u-mt--16">
                  <div class="sel-fee-wrap__item u-width--460" id="radPayTypeB">
                    <dl>
                      <dt class="sel-fee-wrap__title"  id="rateNm">
                        <!-- 데이터 맘껏 15GB+/300분(PAY쿠폰 5000P) -->
                      </dt>
                      <dd class="sel-fee-wrap__text" id="rateDesc">
                        <!-- <span>데이터 1.5GB</span>
                             <span>음성 100분</span>
                             <span>문자 100건</span> -->
                      </dd>
                    </dl>
                  </div>
                </div>
                <p class="c-bullet u-co-gray">※ 요금제 변경을 희망하시는 고객님께서는 명의변경 시, 상담원분에게 요청 부탁드립니다.</p>
              </div>

              <!-- 주소 -->
              <div class="c-form-wrap u-mt--48">
                <div class="c-form-group" role="group" aira-labelledby="inputAddressHead">
                  <h5 class="c-group--head" id="inputAddressHead">주소</h5>
                  <div class="c-form-row c-col2">
                    <div class="c-form">
                      <div class="c-input-wrap u-mt--0">
                        <label class="c-form__label c-hidden" for="cstmrPost">우편번호</label>
                        <input class="c-input" id="cstmrPost" type="text" placeholder="우편번호" value="" readonly>
                        <button id="btnAddr" class="c-button c-button--sm c-button--underline _btnAddr" addrFlag="1">우편번호찾기</button>
                      </div>
                    </div>
                    <div class="c-form">
                      <div class="c-input-wrap u-mt--0">
                        <label class="c-form__label c-hidden" for="cstmrAddr">주소</label>
                        <input class="c-input" id="cstmrAddr" type="text" placeholder="주소" value="" readonly >
                      </div>
                    </div>
                  </div>
                  <div class="c-form u-mt--16">
                    <label class="c-label c-hidden" for="cstmrAddrDtl">상세주소</label>
                    <input class="c-input" id="cstmrAddrDtl" type="text" placeholder="상세주소" value="" name="cstmrAddrDtl" readonly>
                  </div>
                </div>
              </div>

              <!-- 명세서 수신유형 -->
              <div class="c-form-wrap u-mt--48">
                <div class="c-form-group" role="group" aira-labelledby="radReceiveTypeTitle">
                  <h4 class="c-group--head" id="radReceiveTypeTitle">명세서 수신유형</h4>
                  <div class="c-checktype-row c-col3">
                    <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrBillSendCode1" value="MB" type="radio" name="cstmrBillSendCode" checked>
                    <label class="c-label" for="cstmrBillSendCode1">
                      <!-- [2022-02-07] 아이콘 변경-->
                      <i class="c-icon c-icon--mobile-bill" aria-hidden="true"></i>
                      <span>모바일 명세서(MMS)</span>
                    </label>
                    <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrBillSendCode2" value="CB" type="radio" name="cstmrBillSendCode">
                    <label class="c-label" for="cstmrBillSendCode2">
                      <i class="c-icon c-icon--email-bill" aria-hidden="true"></i>
                      <span>이메일 명세서</span>
                    </label>
                    <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrBillSendCode3" value="LX" type="radio" name="cstmrBillSendCode">
                    <label class="c-label" for="cstmrBillSendCode3">
                      <i class="c-icon c-icon--post-bill" aria-hidden="true"></i>
                      <span>우편 명세서</span>
                    </label>
                  </div>
                  <p class="c-bullet c-bullet--dot u-co-gray">이메일, 모바일(MMS) 명세서를 선택 하시면 청구내역을 빠르고 정확하게 받아보실 수 있습니다.</p>
                </div>
                <div class="c-form-wrap u-mt--48" id="divEmail" style="display:none">
                  <div class="c-form-group" role="group" aira-labelledby="inputEmailHead">
                    <h5 class="c-group--head" id="inputEmailHead">이메일</h5>
                    <div class="c-form-row c-col2 u-width--460">
                      <div class="c-form-field">
                        <div class="c-form__input u-mt--0 ">
                          <input class="c-input" id="cstmrMail" type="text" placeholder="이메일 입력" aria-invalid="true" value="" maxlength="50" >
                          <label class="c-form__label" for="cstmrMail">이메일</label>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 요금 납부 방법 -->
              <div class="c-form-wrap u-mt--48">
                <div class="c-form-group" role="group" aira-labelledby="radPayMethodTitle">
                  <h5 class="c-group--head" id="radPayMethodTitle">요금 납부 방법</h5>
                  <div class="c-checktype-row c-col2">
                    <input class="c-radio c-radio--button" id="reqPayType01" value="D" type="radio" name="reqPayType" checked>
                    <label class="c-label" for="reqPayType01">자동이체</label>
                    <input class="c-radio c-radio--button" id="reqPayType02" value="C" type="radio" name="reqPayType">
                    <label class="c-label" for="reqPayType02">신용카드</label>
                  </div>
                </div>
              </div>

              <!-- case : 자동이체 선택 시-->
              <div class="c-form-wrap u-mt--48 _bank">
                <div class="c-form-group" role="group" aria-labelledby="inpAccoutInfoHead">
                  <h5 class="c-group--head" id="inpAccoutInfoHead">계좌정보</h5>
                  <div class="c-form-row c-col2">
                    <div class="c-form-field">
                      <div class="c-form__select u-mt--0" >
                        <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />
                        <select id="reqBank" class="c-select">
                          <option value="">은행명 선택</option>
                          <c:forEach items="${bankList }" var="itemBank" >
                            <option value="${itemBank.dtlCd }">${itemBank.dtlCdNm }</option>
                          </c:forEach>
                        </select>
                        <label class="c-form__label" for="reqBank">은행명</label>
                      </div>
                    </div>
                    <div class="c-form-field">
                      <div class="c-form__input u-mt--0">
                        <input class="c-input onlyNum" id="reqAccountNumber" type="number" placeholder="'-'없이 입력" value="" >
                        <label class="c-form__label" for="reqAccountNumber">계좌번호</label>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="c-box c-box--type1 u-mt--20">
                  <ul class="c-text-list c-bullet c-bullet--dot">
                    <li class="c-text-list__item u-co-gray">납부방법은 본인명의의 카드 또는 계좌로만 등록 가능합니다.(미성년자의 경우 법정대리인 명의 포함)</li>
                    <li class="c-text-list__item u-co-gray">매월 21일 납부</li>
                    <li class="c-text-list__item u-co-gray">평생계좌(개인 핸드폰번호를 계좌번호로 사용하는 계좌)는 이용 불가능합니다.</li>
                  </ul>
                </div>
                <div class="c-button-wrap u-mt--40 _bank">
                  <button id="btnCheckAccount" class="c-button c-button-round--md c-button--mint c-button--w460">계좌번호 유효성 체크</button>
                </div>
              </div>

              <!-- case : 신용카드 선택 시-->
              <div class="c-form-wrap u-mt--48 _card" style="display:none">
                <div class="c-form-group" role="group" aria-labelledby="inpcardTermHead">
                  <h5 class="c-group--head" id="inpcardTermHead">신용카드 정보</h5>
                  <div class="c-form-row c-col2">
                    <div class="c-form-field">
                      <div class="c-form__select u-mt--0">
                        <select class="c-select" id="reqCardCompany">
                          <option value="" >카드사 선택</option>
                          <option value="비씨카드"> 비씨카드</option>
                          <option value="국민카드"> 국민카드</option>
                          <option value="롯데카드"> 롯데카드</option>
                          <option value="외환카드"> 외환카드</option>
                          <option value="삼성카드"> 삼성카드</option>
                          <option value="하나카드"> 하나카드</option>
                          <option value="농협카드"> 농협카드</option>
                          <option value="현대카드"> 현대카드</option>
                          <option value="신한카드"> 신한카드</option>
                          <option value="씨티카드"> 씨티카드</option>
                          <option value="한미카드"> 한미카드</option>
                          <option value="수협카드"> 수협카드</option>
                          <option value="우리카드"> 우리카드</option>
                          <option value="제주카드"> 제주카드</option>
                          <option value="광주카드"> 광주카드</option>
                          <option value="전북카드"> 전북카드</option>
                        </select>
                        <label class="c-form__label" for="reqCardCompany">카드사명</label>
                      </div>
                    </div>
                    <div class="c-form-field">
                      <div class="c-form__input u-mt--0">
                        <input class="c-input onlyNum" id="reqCardNo" type="number" placeholder="'-'없이 입력" value="">
                        <label class="c-form__label" for="reqCardNo">카드번호</label>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="c-form-wrap u-mt--48 _card" style="display:none">
                <div class="c-form-group" role="group" aria-labelledby="inpcardInfoHead">
                  <h5 class="c-group--head" id="inpcardInfoHead">신용카드 유효기간</h5>
                  <div class="c-form-row c-col2">
                    <div class="c-form-field">
                      <div class="c-form__select u-mt--0">
                        <select class="c-select" id="reqCardMm">
                          <option value="" ></option>
                          <option value="01">1월</option>
                          <option value="02">2월</option>
                          <option value="03">3월</option>
                          <option value="04">4월</option>
                          <option value="05">5월</option>
                          <option value="06">6월</option>
                          <option value="07">7월</option>
                          <option value="08">8월</option>
                          <option value="09">9월</option>
                          <option value="10">10월</option>
                          <option value="11">11월</option>
                          <option value="12">12월</option>
                        </select>
                        <label class="c-form__label" for="reqCardMm">MM</label>
                      </div>
                    </div>
                    <div class="c-form-field">
                      <div class="c-form__select u-mt--0">
                        <fmt:formatDate value="${nmcp:getDateToCurrent(0)}" pattern="yyyy" var="nowYear" />
                        <select class="c-select" id="reqCardYy">
                          <option value=""> </option>
                          <c:forEach begin="0" end="10" var="item">
                            <option value="${nowYear+item}">${nowYear+item}년</option>
                          </c:forEach>
                        </select>
                        <label class="c-form__label" for="reqCardYy">YY</label>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="c-box c-box--type1 u-mt--20">
                  <ul class="c-text-list c-bullet c-bullet--dot">
                    <li class="c-text-list__item u-co-gray">납부방법은 본인명의의 카드 또는 계좌로만 등록 가능합니다.(미성년자의 경우 법정대리인 명의 포함)</li>
                    <li class="c-text-list__item u-co-gray">1회차 매월 10~11일경</li>
                  </ul>
                </div>
              </div>
              <div class="c-button-wrap u-mt--40 _card" style="display:none">
                <button id="btnCheckCardNo" class="c-button c-button-round--md c-button--mint c-button--w460">신용카드 유효성 체크</button>
              </div>
            </div>

            <!-- 신분증 확인 -->
            <div class="c-form-wrap u-mt--48 _selfCert" style="display:none">
              <div class="c-form-group" role="group" aira-labelledby="radIdCardHead">
                <h5 class="c-group--head" id="radIdCardHead">신분증 확인</h5>
                <div class="c-checktype-row c-col3">
                  <input class="c-radio c-radio--button" id="selfCertType1" value="01" type="radio" name="selfCertType">
                  <label class="c-label" for="selfCertType1">주민등록증</label>
                  <input class="c-radio c-radio--button" id="selfCertType2" value="02" type="radio" name="selfCertType">
                  <label class="c-label" for="selfCertType2">운전면허증</label>
                  <input class="c-radio c-radio--button" id="selfCertType3" value="03" type="radio" name="selfCertType">
                  <label class="c-label" for="selfCertType3">장애인 등록증</label>
                </div>
                <div class="c-checktype-row c-col3">
                  <input class="c-radio c-radio--button" id="selfCertType4" value="04" type="radio" name="selfCertType">
                  <label class="c-label" for="selfCertType4">국가 유공자증</label>
                  <input class="c-radio c-radio--button" id="selfCertType6" value="06" type="radio" name="selfCertType">
                  <label class="c-label" for="selfCertType6">외국인 등록증</label>
                   <input class="c-radio c-radio--button _passport" id="selfCertType5" value="05" type="radio" name="selfCertType" disabled>
                  <label class="c-label c-hidden" for="selfCertType5">여권</label>
                </div>
              </div>
            </div>

            <div class="c-form-wrap _isFathCert" style="display:none;">
              <div class="c-button-wrap u-mt--40">
                <button id="btnFathUrlRqt" class="c-button c-button--md c-button--mint c-button--w460">안면인증 URL 받기</button>
              </div>

              <div class="c-button-wrap u-mt--40">
                <button id="btnFathTxnRetv" class="c-button c-button--md c-button--mint c-button--w220" disabled>안면인증 결과 확인</button>
                <button id="btnFathSkip" class="c-button c-button--md c-button--gray c-button--w220" onclick="requestFathSkip();" disabled>안면인증 SKIP</button>
              </div>
            </div>

            <div class="c-form-wrap u-mt--32 _chkIdCardTitle" style="display:none;">
              <div class="c-form-group" role="group" aira-labelledby="chkIdCardTitle">
                <h3 class="c-group--head c-hidden" id="chkIdCardTitle">신분증 확인 검증</h3>
                <div class="u-box--w460 u-margin-auto">
                  <!--[2022-02-07] aria-hidden 속성 및 케이스별 신분증 이미지 추가-->
                  <div class="c-img-wrap" aria-hidden="true">
                    <!--주민등록증 선택 시-->
                    <img id="imgRadIdCard" src="/resources/images/portal/content/img_jumin_card.png" alt="">
                  </div>

                  <div class="c-form__select _cstmrForeignerNation" style="display:none;">
                    <select class="c-select" id="cstmrForeignerNation" name="cstmrForeignerNation">

                    </select>
                    <label class="c-form__label" for="cstmrForeignerNation">국가</label>
                    <input type="hidden" id="cstmrForeignerNationTmp" name="cstmrForeignerNationTmp" value="">
                  </div>

                  <div class="c-form-row c-col2 u-mt--0 _selfIssuNumF" style="display:none;">
                    <div class="c-form-field">
                      <div class="c-form__input">
                        <input class="c-input" id="selfIssuNum" type="text" placeholder="보훈번호 입력" value="">
                        <label class="c-form__label" for="selfIssuNum">보훈번호</label>
                      </div>
                    </div>
                  </div>

                  <!-- 운전면허증 선택 시 노출-->
                  <div class="c-form-row c-col2 _driverSelfIssuNumF" style="display:none;">
                    <div class="c-form-field">
                      <div class="c-form__input">
                        <input class="c-input" id="driverSelfIssuNum" type="text" placeholder="면허번호 입력" value="">
                        <label class="c-form__label" for="driverSelfIssuNum">면허번호</label>
                      </div>
                    </div>
                    <input  type="hidden" id="driverSelfIssuNum1">
                    <input type="hidden"  id="driverSelfIssuNum2">
                  </div>

                  <div class="c-form-field c-form-field--datepicker">
                    <div class="c-form__input has-value">
                      <input class="c-input flatpickr" id="selfIssuExprDt" type="text" placeholder="YYYY-MM-DD" value="" maxlength="10"  >
                      <label class="c-form__label" for="selfIssuExprDt">발급일자</label>
                      <span class="c-button c-button--calendar">
                        <span class="c-hidden">날짜선택</span>
                        <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                      </span>
                    </div>
                  </div>

                  <div class="c-form-wrap u-mt--20">
                    <div class="c-checktype-row">
                      <input class="c-checkbox" id="selfInqryAgrmYnFlag" type="checkbox" >
                      <label class="c-label u-mb--20" for="selfInqryAgrmYnFlag">본인 조회에 동의합니다.</label>
                    </div>
                  </div>
                  <div class="c-box">
                    <p class="c-bullet c-bullet--dot u-co-gray u-fs-16">입력하신 정보는 한국정보통신진흥협회(KAIT)에서 제공하는 부정가입방지 시스템을 통해 신분증 진위여부 판단에 이용됩니다.</p>
                  </div>

                </div>
              </div>
            </div>
            <div class="c-form-wrap u-mt--48">
              <div class="c-form-group" role="group" aira-labelledby="inpRecieverPhoneTitle">
                <h4 class="c-group--head c-flex--jfy-start" id="inpRecieverPhoneTitle">연락가능 연락처 <span class="u-co-gray u-ml--12">※해당 번호로 명의변경 신청내용 확인 전화를 드립니다.</span></h4>
              </div>
              <div class="c-form-row c-col2 u-width--460">
                <div class="c-form-field">
                  <div class="c-form__input-group">
                    <div class="c-form__input c-form__input-division">
                      <input class="c-input--div3 onlyNum" id="cstmrReceiveTelFn" type="text" maxlength="3" pattern="[0-9]*" placeholder="숫자입력" title="휴대폰 첫자리" onkeyup="nextFocus(this, 3, 'cstmrReceiveTelMn');" >
                      <span>-</span>
                      <input class="c-input--div3 onlyNum" id="cstmrReceiveTelMn" type="text" maxlength="4" pattern="[0-9]*" placeholder="숫자입력" title="휴대폰 중간자리" onkeyup="nextFocus(this, 4, 'cstmrReceiveTelRn');" >
                      <span>-</span>
                      <input class="c-input--div3 onlyNum" id="cstmrReceiveTelRn" type="text" maxlength="4" pattern="[0-9]*" placeholder="숫자입력" title="휴대폰 뒷자리">
                      <label class="c-form__label" for="cstmrReceiveTelFn">연락가능 연락처</label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="c-form-wrap u-mt--48 u-ta-center">
              <p>※명의변경 완료 이후에 다이렉트몰 내 마이페이지 또는 고객센터 상담원을 통해 가입정보 변경이 가능합니다.</p>
              <div class="c-button-wrap u-mt--16">
                <button class="c-button c-button--lg c-button--primary c-button--w460" id="btnAssignee">양수인 신청하기</button>
              </div>
            </div>
          </div>

        </div>
    <!-- main-content END -->



        <!-- Layer popup -->
        <div class="c-modal c-modal--xlg" id="simpleDialog" role="dialog" aria-labelledby="#simpleTitle">
          <div class="c-modal__dialog" role="document">
            <div class="c-modal__content">
              <div class="c-modal__header">
                <h2 class="c-modal__title _title" id="simpleTitle">오류 TITLE</h2>
                <button class="c-button" data-dialog-close>
                  <i class="c-icon c-icon--close" aria-hidden="true"></i>
                  <span class="c-hidden">팝업닫기</span>
                </button>
              </div>
              <div class="c-modal__body _detail">
    
              </div>
              <div class="c-modal__footer">    
                <div id="divChangeButton" class="c-button-wrap _simpleDialogButton">
                  
                  
                </div>
              </div>
            </div>
          </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
