<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">데이터쉐어링</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">

        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js?version=20.07.07"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=21.09.30"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js?version=21.12.22"></script>
        <script type="text/javascript" src="/resources/js/portal/content/dataSharingStep3.js?version=26.03.03"></script>
        <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
        <script type="text/javascript">
          history.pushState(null, null,location.href);
          window.onpopstate = function (event){
            location.href = "/content/dataSharingStep1.do"
           }
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title"><h2 class="c-page--title">데이터쉐어링</h2></div>
            <form name="frm" id="frm">
                <input type="hidden" name="contractNum" id="contractNum" value="${myShareDataReqDto.ncn}"/>
                <input type="hidden" name="realContractNum" id="realContractNum" value="${myShareDataReqDto.contractNum}"/>
                <input type="hidden" name="opmdSvcNo" id="opmdSvcNo" value=""/>
                <input type="hidden" name="authYn" id="authYn" value=""/>
                <input type="hidden" name="isAuth" id="isAuth" value=""/>
                <input type="hidden" id="iccYn" name="iccYn" value=""/>
                <input type="hidden" id="selfShareYn" name="selfShareYn" value="Y">
                <input type="hidden" id="selfShareSuccesYn" name="selfShareSuccesYn" value="">
                <input type="hidden" id="menuType" name="menuType" value="shareData">
            </form>

            <input type="hidden" id="isPassAuth" name="isPassAuth">
            <input type="hidden" name="onOffType" id="onOffType" value="${onOffType}"/>
            
            <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv" > <!--안면인증 결과 -->
            <input type="hidden" id="fathTrgYn" name="fathTrgYn" > <!--안면인증 대상여부 -->


            <div class="c-row c-row--lg">

                <div class="c-form-wrap u-mt--24" style="display:none" >
                    <div class="c-form-group" role="group" aira-labelledby="cstmrTitle">
                        <div class="c-checktype-row c-col3 u-mt--0">
                            <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType1" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NA}" <c:if test="${cstmrType eq Constants.CSTMR_TYPE_NA }" > checked </c:if> >
                            <label class="c-label" for="cstmrType1">
                                <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                                <span>19세 이상 내국인</span>
                            </label>
                            <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType2" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NM}" <c:if test="${cstmrType eq Constants.CSTMR_TYPE_NM }" > checked </c:if> >
                            <label class="c-label" for="cstmrType2">
                                <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                                <span>19세 미만 미성년자</span>
                            </label>
                            <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}" <c:if test="${cstmrType eq Constants.CSTMR_TYPE_FN }" > checked </c:if> >
                            <label class="c-label" for="cstmrType3">
                                <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                                <span>외국인</span>
                            </label>
                        </div>
                    </div>
                </div>

                <h4 class="c-form__title--type2" id="inpNameCertiTitle">가입자 정보</h4>
                <div class="c-form-wrap">
                    <div class="c-form-group" role="group" aira-labelledby="inpNameCertiTitle">
                        <div class="c-form-row c-col2 u-mt--0">
                            <div class="c-form-field">
                                <div class="c-form__input ">
                                    <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" value="${myShareDataReqDto.name}"  maxlength="60" disabled="disabled" >
                                    <label class="c-form__label" for="cstmrName">이름</label>
                                </div>
                            </div>

                            <c:choose>
                                <c:when test="${ Constants.CSTMR_TYPE_FN eq cstmrType}" >
                                    <div class="c-form-field _isForeigner" >
                                    <div class="c-form__input-group">
                                        <div class="c-form__input c-form__input-division">
                                            <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn01" type="text" autocomplete="off" maxlength="6" placeholder="외국인등록번호 앞자리" title="외국인등록번호 앞자리" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrForeignerRrn02');" >
                                            <span>-</span>
                                            <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="외국인등록번호 뒷자리" title="외국인등록번호 뒷자리" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,6,13)}" >
                                            <label class="c-form__label" for="cstmrForeignerRrn01">외국인 등록번호</label>
                                        </div>
                                    </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="c-form-field _isDefault" >
                                        <div class="c-form__input-group">
                                            <div class="c-form__input c-form__input-division">
                                                <!-- 주민등록번호 입력 부분 -->
                                                <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" type="text" autocomplete="false" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                                                <span>-</span>
                                                <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,6,13)}" >
                                                <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                                            </div>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>





                        </div>
                    </div>
                </div>

                <h3 class="c-heading c-heading--fs20 u-mt--48">결합 신청 회선</h3>
                <div class="c-form-row u-mt--14">
                    <div class="c-form-field u-width--460">
                        <div class="c-form__input-group is-readonly u-mt--0">
                            <div class="c-form__input c-form__input-division has-value">
                                <input class="c-input--div3" id="inpPhoneNum1" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리" value="${fn:substring(myShareDataReqDto.ctn,0,3)}" readonly>
                                <span>-</span>
                                <input class="c-input--div3" id="inpPhoneNum2" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 중간자리" value="${fn:substring(myShareDataReqDto.ctn,4,8)}" readonly>
                                <span>-</span>
                                <input class="c-input--div3" id="inpPhoneNum3" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 뒷자리" value="${fn:substring(myShareDataReqDto.ctn,9,14)}" readonly>
                                <label class="c-form__label" for="inpPhoneNum1">가입회선 번호</label>
                            </div>
                        </div>
                    </div>
                </div>

                <c:if test="${onOffType ne '5' and Constants.CSTMR_TYPE_NM ne cstmrType }">
                    <h3 class="c-heading c-heading--fs20 u-mt--48">연락처</h3>
                    <div class="c-form-row u-mt--14">
                        <div class="c-form-field u-width--460">
                            <div class="c-form__input-group u-mt--0">
                                <div class="c-form__input c-form__input-division ">
                                    <input class="c-input--div3" id="cstmrMobileFn" value="" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리" onkeyup="nextFocus(this, 3, 'cstmrMobileMn');"  >
                                    <span>-</span>
                                    <input class="c-input--div3" id="cstmrMobileMn" value="" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 중간자리" onkeyup="nextFocus(this, 4, 'cstmrMobileRn');"  >
                                    <span>-</span>
                                    <input class="c-input--div3" id="cstmrMobileRn" value="" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 뒷자리"  >
                                    <label class="c-form__label" for="cstmrMobileFn">연락처</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-box c-box--type1 u-mt--20">
                        <p class="c-bullet c-bullet--dot u-co-gray">가입신청서를 작성 완료 시 순차적으로 AI상담사 또는 개통상담사가 전화를 드려 본인확인을 진행합니다.</p>
                        <p class="c-bullet c-bullet--dot u-co-gray">3회 이상 통화가 되지 않을 경우 신청 정보가 자동으로 삭제됩니다.</p>
                    </div>
                </c:if>

                <c:if test="${Constants.CSTMR_TYPE_NM eq cstmrType }">
                    <div class="c-form-wrap _isTeen " >
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
                                            <option value="01" >부</option>
                                            <option value="02" >모</option>
                                            <option value="10" >그외</option>
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
                </c:if>


                <!-- 본인인증 방법 선택 -->
                <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
                    <jsp:param name="controlYn" value="N"/>
                    <jsp:param name="reqAuth" value="CNAT"/>
                </jsp:include>

                <h3 class="c-heading c-heading--fs20 u-mt--64">유심정보</h3>
                <div class="c-form-row u-mt--14">
                    <div class="c-form-field u-width--460">
                        <div class="c-form__input u-mt--0 iccidChk"> <!-- has-error -->
                            <input class="c-input numOnly" id="iccId" type="text"   placeholder="'-'없이 입력" aria-invalid="false" aria-describedby="msgA1" maxlength="19" >
                            <label class="c-form__label" for="iccId">유심정보 입력</label>
                            <button class="c-button c-button--sm c-button--underline" id="iccBtn"    onclick="btn_usimChk();">유심번호 유효성 체크</button>
                        </div>
                        <p class="c-form__text" id="msgA1" style="display: none;">올바른 유심번호를 입력해 주세요.</p>
                    </div>
                </div>
                <div class="c-box c-box--type1 u-mt--20">
                    <p class="c-bullet c-bullet--dot u-co-gray">유심 카드를 미리 구매하신 고객님께서는 유심번호 19자리를 입력해 주세요</p>
                </div>



                <div class="c-form-wrap" id="selfCertSection1" >
                    <div class="c-form-group" role="group" aira-labelledby="radIdCardHead">
                        <h5 class="c-form__title--type2" id="radIdCardHead">신분증 확인</h5>
                        <div class="c-checktype-row c-col3 u-mt--0">
                            <input class="c-radio c-radio--button" id="selfCertType1" value="01" type="radio" name="selfCertType" >
                            <label class="c-label" for="selfCertType1">주민등록증</label>
                            <input class="c-radio c-radio--button" id="selfCertType2" value="02" type="radio" name="selfCertType">
                            <label class="c-label" for="selfCertType2">운전면허증</label>
                            <input class="c-radio c-radio--button" id="selfCertType3" value="03" type="radio" name="selfCertType">
                            <label class="c-label" for="selfCertType3">장애인 등록증</label>
                        </div>
                        <div class="c-checktype-row c-col3">
                            <input class="c-radio c-radio--button" id="selfCertType4" value="04" type="radio" name="selfCertType">
                            <label class="c-label" for="selfCertType4">국가 유공자증</label>
                            <input class="c-radio c-radio--button" id="selfCertType6" value="06" type="radio" name="selfCertType" <c:if test='${Constants.CSTMR_TYPE_FN ne cstmrType }'> disabled </c:if>  >
                            <label class="c-label" for="selfCertType6">외국인 등록증</label>
                            <input class="c-radio c-radio--button c-hidden" id="selfCertType5" value="05" type="radio" name="selfCertType" disabled >
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
                

                <div class="c-form-wrap u-mt--32 _chkIdCardTitle" id="selfCertSection2" style="display:none;">
                    <div class="c-form-group" role="group" aira-labelledby="chkIdCardTitle">

                        <h3 class="c-group--head c-hidden u-mb--0" id="chkIdCardTitle">신분증 확인 검증</h3>

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
                                <input type="hidden" id="cstmrForeignerNationTmp" name="cstmrForeignerNationTmp" value="${AppformReq.cstmrForeignerNation}">
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
                                    <input class="c-input" id="driverSelfIssuNum" type="text" placeholder="면허번호 입력" value="${AppformReq.selfIssuNumDesc}">
                                    <label class="c-form__label" for="driverSelfIssuNum">면허번호</label>
                                  </div>
                                </div>
                                <input  type="hidden" id="driverSelfIssuNum1">
                                <input type="hidden"  id="driverSelfIssuNum2">
                              </div>

                            <div class="c-form-field c-form-field--datepicker">
                                <div class="c-form__input has-value">
                                    <input class="c-input flatpickr" id="selfIssuExprDt" type="text" placeholder="YYYY-MM-DD" value="" maxlength="10">
                                    <label class="c-form__label" for="selfIssuExprDt">발급일자</label>
                                    <span class="c-button c-button--calendar">
                                        <span class="c-hidden">날짜선택</span>
                                        <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                                    </span>
                                </div>
                            </div>

                            <div class="c-form-wrap u-mt--20">
                                <div class="c-checktype-row">
                                    <input class="c-checkbox" id="selfInqryAgrmYnFlag" type="checkbox">
                                    <label class="c-label u-mb--20" for="selfInqryAgrmYnFlag">본인 조회에 동의합니다.</label>
                                </div>
                            </div>
                            <div class="c-box" id="selfCertSection3">
                                <p class="c-bullet c-bullet--dot u-co-gray u-fs-16">입력하신 정보는 한국정보통신진흥협회(KAIT)에서 제공하는 부정가입방지 시스템을 통해 신분증 진위여부 판단에 이용됩니다.</p>
                            </div>
                        </div>
                    </div>
                </div>



                <div class="c-accordion c-accordion--type1 acc-agree c-accordion--nested">
                    <span class="c-form__title--type2">약관동의</span>
                    <div class="c-accordion__box">
                        <div class="c-accordion__item">
                            <div class="c-accordion__head">
                                <button class="runtime-data-insert c-accordion__button" id="acc_agree_headerA1" type="button" aria-expanded="false" aria-controls="acc_agreeA1">
                                    <span class="c-hidden">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다. ※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span>
                                </button>
                                <div class="c-accordion__check">
                                    <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                                    <label class="c-label" for="btnStplAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span></label>
                                </div>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_agreeA1" aria-labelledby="acc_agree_headerA1" aria-hidden="true">
                                <div class="c-accordion__inside">
                                    <div class="c-accordion__sub-check">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}',0);">
                                            <span class="c-hidden">개인정보의 제공 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriOfferFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" >
                                        <label class="c-label" for="clausePriOfferFlag">고유식별정보 수집&middot;이용 동의<span class="u-co-gray">(필수)</span></label>
                                    </div>
                                    <div class="c-accordion__sub-check">
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}',0);" >
                                            <span class="c-hidden">개인정보 수집·이용 동의(필수) 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriCollectFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
                                        <label class="c-label" for="clausePriCollectFlag">개인정보/신용정보 수집·이용 동의<span class="u-co-gray">(필수)</span></label>
                                    </div>
                                    <div class="c-accordion__sub-check" >
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseEssCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}',0);">
                                            <span class="c-hidden">개인정보 제3자 제공 동의 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseEssCollectFlag" type="checkbox"  cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y"  >
                                        <label class="c-label" for="clauseEssCollectFlag">개인정보 제3자 제공 동의<span class="u-co-gray">(필수)</span></label>
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

                                    <c:if test="${Constants.CSTMR_TYPE_NM eq cstmrType }">
                                        <div class="c-accordion__sub-check"  >
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('nwBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_23}',0);"  >
                                                <span class="c-hidden">청소년 유해정보 네트워크차단 동의(필수) 상세 팝업 열기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                            <input class="c-checkbox c-checkbox--type2 _agree" id="nwBlckAgrmYn" type="checkbox"  code="${Constants.NOTICE_CODE_23}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y">
                                            <label class="c-label" for="nwBlckAgrmYn">청소년 유해정보 네트워크차단 동의<span class="u-co-gray">(필수)</span>
                                            </label>
                                        </div>

                                        <div class="c-accordion__sub-check"  >
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('appBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_24}',0);"  >
                                                <span class="c-hidden">청소년 유해정보차단 APP 설치 동의(필수) 상세 팝업 열기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                            <input class="c-checkbox c-checkbox--type2 _agree" id="appBlckAgrmYn" type="checkbox"  code="${Constants.NOTICE_CODE_24}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y">
                                            <label class="c-label" for="appBlckAgrmYn">청소년 유해정보차단 APP 설치 동의<span class="u-co-gray">(필수)</span>
                                            </label>
                                        </div>
                                    </c:if>

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


                <c:if test="${onOffType eq '5'}" >
                    <div class="c-form-wrap _self">
                        <h5 class="c-form__title--type2">셀프개통 안내사항</h5>
                        <div class="c-agree c-agree--type2">
                            <div class="c-agree__item">
                                <input class="c-checkbox " id="clauseSimpleOpen" type="checkbox" validityyn="Y">
                                <label class="c-label" for="clauseSimpleOpen">본인은 안내사항을 확인하였습니다.<span class="u-co-gray">(필수)</span></label>
                            </div>
                            <div class="c-agree__inside self-agree__inside">
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray u-mt--0">
                                       최근 대출업자, SNS 등을 통해 이동전화 개통 시 자금을 제공해 주겠다고 권유한 후, 개통된 휴대폰/유심을 대출 사기, 보이스 피싱 조직에 유통하는 등의 악용하는 사례가 다수 적발되고 있습니다.<br />이러한 제 3자에게 본인명의의 휴대폰/유심을 개통해주거나, 개통에 필요한 신청서류를 제공하는 행위는 전기통신사업법 제30조(타인사용의 제한) 및 97조(벌칙)의 규정에 따라 1년 이하의 징역 및 5천만원 이하의 형사처벌을 받을 수 있으니 각별히 주의하시기 바랍니다.
                                    </li>
                                    <li class="c-text-list__item u-co-gray">
                                        부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.<br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:if>



                <div class="c-button-wrap u-mt--56">
                    <a class="c-button c-button--lg c-button--gray u-width--220" href="javascript:void(0);"  onclick="sharing_cancel();">취소</a>
                    <a class="c-button c-button--lg c-button--primary u-width--220 is-disabled" id="sharing_reg" href="javascript:void(0);"  onclick="sharing_reg();">가입신청</a>
                </div><!-- [2022-01-14] 페이지 하단 유의사항 마크업 수정-->
                <h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                    <span class="u-ml--4">알려드립니다</span>
                </h3>
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                    <!-- [$2022-01-14] 페이지 하단 유의사항 마크업 수정-->
                    <li class="c-text-list__item u-co-gray">데이터 쉐어링으로 가입하는 회선은 통화가 불가능한 데이터 전용 회선으로 가입되기 때문에 회선 번호는 무작위로 부여됩니다.</li>
                </ul>
            </div>
        </div>

        <!-- 셀프 개통 Layer popup-->
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
                        <div id="divDefaultButton"  class="c-button-wrap _simpleDialogButton">
                            <button class="c-button c-button--lg c-button--primary u-width--220 _btnCheck" data-dialog-close>확인</button>
                        </div>
                        <div id="divChangeButton" class="c-button-wrap _simpleDialogButton">
                            <button id="btnOnline" class="c-button c-button--lg c-button--gray u-width--220" >해피콜 신청서 작성</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 _btnCheck" data-dialog-close>확인</button>
                            
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </t:putAttribute>
</t:insertDefinition>
