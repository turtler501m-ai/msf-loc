<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">워치 가입하기</t:putAttribute>


    <t:putAttribute name="scriptHeaderAttr">
        <script src="/resources/js/portal/vendor/p5.min.js"></script>
        <script src="/resources/js/portal/game/game.js"></script>

        <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=21.09.30"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=23.10.23"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?version=24.11.28"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js?version=22.08.18"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js?version=23.01.02"></script>
        <script src="${smartroScriptUrl}?version=${today}"></script>
        <script type="text/javascript" src="/resources/js/portal/appForm/eSimWatchForm.js?version=26.03.03"></script>
        <!-- 카카오 광고 분석  -->
        <script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
        <script type="text/javascript">
              kakaoPixel('5981604067138488988')
        </script>
        <!-- 카카오 광고 분석 end-->
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="mode" name="mode"/>
        <input type="hidden" id="isAuth" name="isAuth"  >
        <input type="hidden" id="isSmsAuth" name="isSmsAuth"  >
        <input type="hidden" id="isAuthInsr" name="isAuthInsr"  >
        <input type="hidden" id="isPassAuth" name="isPassAuth"  >
        <input type="hidden" id="isCheckAccount" name="isCheckAccount" >
        <input type="hidden" id="isCheckCard" name="isCheckCard" >
        <input type="hidden" id="isNpPreCheck" name="isNpPreCheck" >
        <input type="hidden" id="isCheckmember" name="isCheckmember" >
        <input type="hidden" id="isUsimNumberCheck" name="isUsimNumberCheck" >
        <input type="hidden" id="userDivision" name="userDivision" value="${userDivision}" />
        <input type="hidden" id="operType" name="operType" value="${AppformReq.operType}" />
        <input type="hidden" id="onOffTypeInit" name="onOffTypeInit" value="${AppformReq.onOffType}" />
        <input type="hidden" id="operTypeNm" name="operTypeNm" value="${AppformReq.operTypeNm}" />
        <input type="hidden" id="onOffType" name="onOffType" value="${AppformReq.onOffType}" />
        <input type="hidden" id="prdtSctnCd" name="prdtSctnCd" value="${AppformReq.prdtSctnCd}">
        <input type="hidden" id="reqBuyType" name="reqBuyType" value="${AppformReq.reqBuyType}">
        <input type="hidden" id="rprsPrdtId" name="rprsPrdtId" value="${AppformReq.rprsPrdtId}">
        <input type="hidden" id="prdtId" name="prdtId" value="${AppformReq.prdtId}">
        <input type="hidden" id="modelId" name="modelId" value="${AppformReq.prdtId}" >
        <input type="hidden" id="sesplsProdId" name=sesplsProdId value="${AppformReq.sesplsProdId}" >
        <input type="hidden" id="serviceType" name="serviceType" value="${AppformReq.serviceType}" >
        <input type="hidden" id="cntpntShopId" name="cntpntShopId" value="${AppformReq.cntpntShopId}" >
        <input type="hidden" id="socCode" name="socCode" value="${AppformReq.socCode}" >
        <input type="hidden" id="prodId" name="prodId" value="${AppformReq.prodId}" >
        <input type="hidden" id="siteReferer" name="siteReferer" value="${AppformReq.siteReferer}" >
        <input type="hidden" id="reqCnt" name="reqCnt" value="" />
        <input type="hidden" id="requestKeyTemp" name="requestKeyTemp" value="${AppformReq.requestKey}">
        <input type="hidden" id="insrProdCdTemp" name="insrProdCdTemp" value="${AppformReq.insrProdCd}">
        <input type="hidden" id="bannerCd" name="bannerCd" value="${AppformReq.bannerCd}" >
        <input type="hidden" id="modelSalePolicyCode" name="modelSalePolicyCode" value="${AppformReq.modelSalePolicyCode}" >
        <input type="hidden" id="sprtTp" name="sprtTp" value="${AppformReq.sprtTp}" >
        <input type="hidden" id="modelMonthly" name="modelMonthly" value="${AppformReq.modelMonthly}" ><!--단말할부개월수  -->
        <input type="hidden" id="reqModelName" name="reqModelName" value="${AppformReq.reqModelName}" >
        <input type="hidden" id="sntyColorCd" name="sntyColorCd" value="${AppformReq.sntyColorCd}" ><!--단말기모델아이디_색상검색용  -->
        <input type="hidden" id="sntyCapacCd" name="sntyCapacCd" value="${AppformReq.sntyCapacCd}" ><!--단품용량코드  -->
        <input type="hidden" id="enggMnthCnt" name="enggMnthCnt" value="${AppformReq.enggMnthCnt}" ><!--유심에서 약정????  -->
        <input type="hidden" name="usimKindsCd" id="usimKindsCd" value="${AppformReq.usimKindsCd}" />
        <input type="hidden" name="prntsContractNo" id="prntsContractNo" value="${AppformReq.prntsContractNo}" />
        <input type="hidden" name="uploadPhoneSrlNo" id="uploadPhoneSrlNo" value="${AppformReq.uploadPhoneSrlNo}" />
        <input type="hidden" id="requestNo" name="requestNo" >
        <input type="hidden" id="resUniqId" name="resUniqId" >
        <input type="hidden" id="joinPriceIsPay" name="joinPriceIsPay" >
        <input type="hidden" id="usimPriceIsPay" name="usimPriceIsPay" >
        <input type="hidden" id="usimPriceNfcIsPay" name="usimPriceNfcIsPay" >
        <input type="hidden" id="joinPrice" name="joinPrice" value="${AppformReq.enggMnthCnt}">
        <input type="hidden" id="usimPrice" name="usimPrice" value="">
        <input type="hidden" id="usimPriceBase" name="usimPriceBase" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_OBJ_BASE)}">
        <input type="hidden" id="usimPrice5G" name="usimPrice5G" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_OBJ_5G)}">
        <input type="hidden" id="usimPriceNfc" name="usimPriceNfc" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_USIM_NFC)}">
        <input type="hidden" id="usimPrice3G" name="usimPrice3G" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_OBJ_3G)}">
        <input type="hidden" id="bizOrgCd" name="bizOrgCd" value="" ><!--바로배송 업체코드-->

        <input type="hidden" id="evntCdPrmt" name="evntCdPrmt" value="${AppformReq.evntCdPrmt}" ><!--이벤트코드 프로모션 -->
        <input type="hidden" id="recoUseYn" name="recoUseYn" value="${AppformReq.recoUseYn}" ><!--이벤트코드에 따른 추천인ID 노출여부 -->
        
        <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv" > <!--안면인증 결과 -->
        <input type="hidden" id="fathTrgYn" name="fathTrgYn" > <!--안면인증 대상여부 -->
        

        <div class="ly-content" id="main-content">

            <div class="ly-page--title">
                <h2 class="c-page--title">워치 가입하기</h2>




                <div class="c-stepper-wrap">
                  <div class="c-hidden">휴대폰 요금제 가입하기 총 5단계 중 현재 1단계(본인확인·약관동의)</div>
                  <ul id="ulStepper" class="c-stepper">
                    <li class="c-stepper__item c-stepper__item--active">
                      <span class="c-stepper__num">1</span>
                      <span class="c-stepper__title">본인확인·약관동의</span>
                    </li>
                    <li class="c-stepper__item">
                      <span class="c-stepper__num">2</span>
                    </li>

                  </ul>
                </div>
            </div>

            <!-- STEP1 START -->
            <div id="step1" class="c-row c-row--lg _divStep">
                <h3 class="c-heading--fs22 c-heading--bold">본인확인·약관동의</h3>
                <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--48" id="cstmrTitle">고객유형</h4>
                <hr class="c-hr c-hr--title">
                <div class="c-form-wrap u-mt--32">
                  <div class="c-form-group" role="group" aira-labelledby="cstmrTitle">
                    <div class="c-checktype-row c-col3 u-mt--0">
                      <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType1" type="radio" name="cstmrType"  value="${Constants.CSTMR_TYPE_NA}" disabled >
                      <label class="c-label" for="cstmrType1">
                        <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                        <span>19세 이상 내국인</span>
                      </label>
                      <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType2" type="radio" name="cstmrType"  value="${Constants.CSTMR_TYPE_NM}"  disabled >
                      <label class="c-label" for="cstmrType2">
                        <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                        <span>19세 미만 미성년자</span>
                      </label>
                      <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}" disabled >
                      <label class="c-label" for="cstmrType3">
                        <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                        <span>외국인</span>
                      </label>
                    </div>
                  </div>
                </div>

                <!--remove autocomplete-->
                <input style="display:none" aria-hidden="true">
                <input type="password" style="display:none" aria-hidden="true">
                <!--End remove autocomplete-->


                <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--48" id="inpNameCertiTitle">실명 및 본인인증</h4>
                <hr class="c-hr c-hr--title">
                <div class="c-form-wrap u-mt--32">
                  <div class="c-form-group" role="group" aira-labelledby="inpNameCertiTitle">
                    <div class="c-form-row c-col2 u-mt--0">
                      <div class="c-form-field">
                        <div class="c-form__input ">
                          <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" value=""  maxlength="60" disabled>
                          <label class="c-form__label" for="cstmrName">이름</label>
                        </div>
                      </div>

                      <div class="c-form-field _isForeigner" style="display:none;">
                          <div class="c-form__input">
                            <input class="c-input " id="cstmrForeignerRrn01" type="text" autocomplete="off" maxlength="6" placeholder="외국인등록번호 " title="외국인등록번호 " value="" disabled >
                            <label class="c-form__label" for="cstmrForeignerRrn01">외국인 등록번호</label>
                          </div>
                      </div>

                      <div class="c-form-field _isDefault" >
                          <div class="c-form__input ">
                           <!-- 주민등록번호 입력 부분 -->
                            <input class="c-input" id="cstmrNativeRrn01" type="text" autocomplete="false" maxlength="6" placeholder="주민등록번호 " title="주민등록번호 " value=""  disabled >
                            <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                          </div>
                      </div>

                    </div>
                  </div>


                  <div class="c-box c-box--type1 u-mt--20 _isTeen">
                    <ul class="c-text-list c-bullet c-bullet--dot">
                      <li class="c-text-list__item u-co-gray">영/유아(0세~4세 이하)의 경우 가입신청이 제한되므로 타 명의로 진행 바랍니다.</li>
                    </ul>
                  </div>





                </div>

                <div class="c-form-wrap u-mt--32 _isTeen " style="display:none;">
                  <div class="c-form-group" role="group" aira-labelledby="inpDepInfoTitle">
                    <h3 class="c-group--head" id="inpDepInfoTitle">법정대리인 정보</h3>
                    <div class="c-form-row c-col2">
                      <div class="c-form-field">
                        <div class="c-form__input">
                          <input class="c-input" id="minorAgentName" type="text" placeholder="법정대리인 성명" aria-invalid="true" value="${AppformReq.minorAgentName}" maxlength=60 >
                          <label class="c-form__label" for="minorAgentName">법정대리인 성명</label>
                        </div>
                      </div>
                      <div class="c-form-field">
                        <div class="c-form__select">
                          <select id="minorAgentRelation" class="c-select">
                            <option value="">선택</option>
                            <option value="01" <c:if test="${AppformReq.minorAgentRelation eq '01' }" > selected </c:if> >부</option>
                            <option value="02" <c:if test="${AppformReq.minorAgentRelation eq '02' }" > selected </c:if> >모</option>
                            <option value="10" <c:if test="${AppformReq.minorAgentRelation eq '10' }" > selected </c:if> >그외</option>
                          </select>
                          <label class="c-form__label" for="minorAgentRelation">신청인과의 관계</label>
                        </div>
                      </div>
                    </div>
                    <div class="c-form-row c-col2">
                      <div class="c-form-field">
                        <div class="c-form__input-group">
                          <div class="c-form__input c-form__input-division">
                            <input class="c-input--div2 onlyNum" id="minorAgentRrn01" autocomplete="off" type="text" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="${fn:substring(AppformReq.minorAgentRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'minorAgentRrn02');" >
                            <span>-</span>
                            <input class="c-input--div2 onlyNum" id="minorAgentRrn02" autocomplete="off" type="password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="${fn:substring(AppformReq.minorAgentRrnDesc,6,13)}" onkeyup="nextFocus(this, 7, 'minorAgentTelFn');" >
                            <label class="c-form__label" for="minorAgentRrn01">법정대리인 주민등록번호</label>
                          </div>
                        </div>
                      </div>
                      <div class="c-form-field">
                        <div class="c-form__input-group">
                          <div class="c-form__input c-form__input-division">
                            <input class="c-input--div3 onlyNum" id="minorAgentTelFn" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="전화번호 첫자리" value="${AppformReq.minorAgentTelFn}" onkeyup="nextFocus(this, 3, 'minorAgentTelMn');" >
                            <span>-</span>
                            <input class="c-input--div3 onlyNum" id="minorAgentTelMn" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 중간자리" value="${AppformReq.minorAgentTelMn}" onkeyup="nextFocus(this, 4, 'minorAgentTelRn');" >
                            <span>-</span>
                            <input class="c-input--div3 onlyNum" id="minorAgentTelRn" type="text" maxlength="4" placeholder="숫자입력" title="전화번호 뒷자리" value="${AppformReq.minorAgentTelRn}" >
                            <label class="c-form__label" for="minorAgentTelFn">법정대리인 전화번호</label>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <h5 class="c-form__title u-mt--48">법정대리인 안내사항 확인 및 동의</h5>
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

                <!-- 본인인증 방법 선택 -->
                <c:if test="${AppformReq.onOffType eq '5'}">
                    <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
                        <jsp:param name="controlYn" value="Y"/>
                    </jsp:include>
                </c:if>
                <c:if test="${AppformReq.onOffType ne '5'}">
                    <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
                        <jsp:param name="controlYn" value="N"/>
                        <jsp:param name="reqAuth" value="CNKATX"/>
                    </jsp:include>
                </c:if>

                <div class="c-accordion c-accordion--type1 acc-agree c-accordion--nested">
                  <span class="c-form__title--type2">약관동의</span>
                  <div class="c-accordion__box">
                    <div class="c-accordion__item">
                      <div class="c-accordion__head">
                        <button class="runtime-data-insert c-accordion__button" id="acc_agree_headerA1" type="button" aria-expanded="false" aria-controls="acc_agreeA1">
                          <span class="c-hidden">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.</span>
                        </button>
                        <div class="c-accordion__check">
                          <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                          <label class="c-label" for="btnStplAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.</label>
                        </div>
                      </div>
                      <div class="c-accordion__panel expand" id="acc_agreeA1" aria-labelledby="acc_agree_headerA1" aria-hidden="true">
                        <div class="c-accordion__inside">

                           <c:if test="${ AppformReq.operType eq Constants.OPER_TYPE_MOVE_NUM }" >
	                            <div class="c-accordion__sub-check">
	                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseMoveCode');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MOVE_CODE}',0);">
	                                    <span class="c-hidden">번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법 동의(필수) 상세 팝업 열기</span>
	                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	                                </button>
	                                <input class="c-checkbox c-checkbox--type2 _agree" id="clauseMoveCode" type="checkbox"  code="${Constants.CLAUSE_MOVE_CODE}" cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
	                                <label class="c-label" for="clauseMoveCode">번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법 동의<span class="u-co-gray">(필수)</span>
	                                </label>
	                            </div>
	                        </c:if>

	                        <div class="c-accordion__sub-check">
	                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}',0);">
	                                <span class="c-hidden">고유식별정보 수집&middot;이용 동의(필수) 상세 팝업 열기</span>
	                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	                            </button>
	                            <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriOfferFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" >
	                            <label class="c-label" for="clausePriOfferFlag">고유식별정보 수집&middot;이용 동의<span class="u-co-gray">(필수)</span>
	                            </label>
	                        </div>

	                        <div class="c-accordion__sub-check">
	                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}',0);" >
	                                <span class="c-hidden">개인정보/신용정보 수집·이용 동의(필수) 상세 팝업 열기</span>
	                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	                            </button>
	                            <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriCollectFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED docVer="0" value="Y" validityyn="Y" >
	                            <label class="c-label" for="clausePriCollectFlag">개인정보/신용정보 수집·이용 동의<span class="u-co-gray">(필수)</span>
	                            </label>
	                        </div>

	                        <div class="c-accordion__sub-check">
	                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseEssCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}',0);">
	                                <span class="c-hidden">개인정보 제3자 제공 동의(필수) 상세 팝업 열기</span>
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

	                        <div class="c-accordion__sub-check _isTeen" style="display:none;" >
	                            <button class="c-button c-button--xsm" onclick="fnSetEventId('nwBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_23}',0);"  >
	                                <span class="c-hidden">청소년 유해정보 네트워크차단 동의(필수) 상세 팝업 열기</span>
	                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	                            </button>
	                            <input class="c-checkbox c-checkbox--type2 _agree" id="nwBlckAgrmYn" type="checkbox"  code="${Constants.NOTICE_CODE_23}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y">
	                            <label class="c-label" for="nwBlckAgrmYn">청소년 유해정보 네트워크차단 동의<span class="u-co-gray">(필수)</span>
	                            </label>
	                        </div>

	                        <div class="c-accordion__sub-check _isTeen" style="display:none;" >
	                            <button class="c-button c-button--xsm" onclick="fnSetEventId('appBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_24}',0);"  >
	                                <span class="c-hidden">청소년 유해정보차단 APP 설치 동의(필수) 상세 팝업 열기</span>
	                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	                            </button>
	                            <input class="c-checkbox c-checkbox--type2 _agree" id="appBlckAgrmYn" type="checkbox"  code="${Constants.NOTICE_CODE_24}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y">
	                            <label class="c-label" for="appBlckAgrmYn">청소년 유해정보차단 APP 설치 동의<span class="u-co-gray">(필수)</span>
	                            </label>
	                        </div>
	                        <c:if test="${AppformReq.prdtSctnCd eq PhoneConstant.FIVE_G_FOR_MSP}">
	                            <div class="c-accordion__sub-check">
	                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clause5gCoverage');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_5G_COVERAGE}',0);"  >
	                                    <span class="c-hidden">5g 커버리지 확인 및 가입 동의(필수) 상세 팝업 열기</span>
	                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	                                </button>
	                                <input class="c-checkbox c-checkbox--type2 _agree" id="clause5gCoverage" type="checkbox"  code="${Constants.CLAUSE_5G_COVERAGE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" >
	                                <label class="c-label" for="clause5gCoverage">5g 커버리지 확인 및 가입 동의<span class="u-co-gray">(필수)</span>
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

<%--                      <div class="c-chk-wrap">--%>
<%--                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriTrustFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_TRUST_CODE}',0);" >--%>
<%--                          <span class="c-hidden">개인정보의 처리 업무 위탁 동의(필수) 상세 팝업 열기</span>--%>
<%--                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>--%>
<%--                        </button>--%>
<%--                        <input class="c-checkbox c-checkbox--type2 _agree" id="clausePriTrustFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_TRUST_CODE}" cdGroupId1=FORMREQUIRED  docVer="0"  type="checkbox"  value="Y" validityyn="Y" >--%>
<%--                        <label class="c-label" for="clausePriTrustFlag">개인정보의 처리 업무 위탁 동의<span class="u-co-gray">(필수)</span>--%>
<%--                        </label>--%>
<%--                      </div>--%>

<%--                      <div class="c-chk-wrap">--%>
<%--                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseConfidenceFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_CONFIDENCE_CODE}',0);">--%>
<%--                          <span class="c-hidden">신용정보 조회 ·이용 ·제공에 대한 동의서(필수) 상세 팝업 열기</span>--%>
<%--                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>--%>
<%--                        </button>--%>
<%--                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseConfidenceFlag" type="checkbox"  code="${Constants.CLAUSE_CONFIDENCE_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" >--%>
<%--                        <label class="c-label" for="clauseConfidenceFlag">신용정보 조회 &middot; 이용 &middot; 제공에 대한 동의서<span class="u-co-gray">(필수)</span>--%>
<%--                        </label>--%>
<%--                      </div>--%>

<%--                      <div class="c-chk-wrap">--%>
<%--                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFinanceFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_FINANCE_FLAG}',0);">--%>
<%--                          <span class="c-hidden">개인(신용)정보 처리 및 보험가입 동의(선택) 상세 팝업 열기</span>--%>
<%--                          <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>--%>
<%--                        </button>--%>
<%--                        <input class="c-checkbox c-checkbox--type2 _agree" id="clauseFinanceFlag" type="checkbox"  code="S${Constants.CLAUSE_FINANCE_FLAG}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" >--%>
<%--                        <label class="c-label" for="clauseFinanceFlag">개인(신용)정보 처리 및 보험가입 동의<span class="u-co-gray">(선택)</span>--%>
<%--                        </label>--%>
<%--                      </div>--%>

	                        <div class="c-accordion__sub-check">
	                            <button class="c-button c-button--xsm" onclick="fnSetEventId('notice');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}',0);" >
	                                <span class="c-hidden">서비스 이용약관(고지) 상세 팝업 열기</span>
	                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
	                            </button>
	                            <p class="u-ml--32 u-fs-15 u-co-black">서비스 이용약관<span class="u-co-gray u-ml--4">(고지)</span>
	                            </p>
	                        </div>


                        </div>
                      </div>
                    </div>
                  </div>
                </div>
















                <c:if test="${AppformReq.onOffType eq '5'}" >
                    <div class="c-form-wrap u-mt--48 _self">
                        <h5 class="c-form__title">셀프개통 안내사항</h5>
                        <div class="c-agree c-agree--type1">
                            <div class="c-agree__item">
                                <button class="c-button c-button--xsm" id="btnClauseSimple" >
                                    <span class="c-hidden">셀프개통 안내사항(필수) 상세 팝업 열기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                </button>
                                <div class="c-agree__inner">
                                    <input class="c-checkbox c-checkbox--type2 " id="clauseSimpleOpen" type="checkbox" validityyn="Y" >
                                    <label class="c-label" for="clauseSimpleOpen"> 본인은 안내사항을 확인하였습니다<span class="u-co-gray">(필수)</span>
                                    </label>
                                </div>

                            </div>
                        </div>
                    </div>
                </c:if>


              <div class="c-form-wrap" id="selfCertSection1" >
                <div class="c-form-group" role="group" aira-labelledby="radIdCardHead">
                  <h5 class="c-form__title--type2" id="radIdCardHead">신분증 확인</h5>
                  <div class="c-checktype-row c-col3 u-mt--0">
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
              

            </div>


            <!-- STEP2 START -->
            <div id="step2" class="c-row c-row--lg _divStep" style="display:none" >
                <h3 class="c-heading--fs22 c-heading--bold">가입정보 확인</h3>
                <!-- eSIM -->
                <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--48" id="eSimePhoneTitle">가입신청 정보</h4>
                <hr class="c-hr c-hr--title">
                <div class="c-box c-box--type1  ">
                    <ul class="c-text-list c-bullet c-bullet--dot" >
                        <li class="c-text-list__item u-co-gray">개통 진행 시 변경이 불가하므로 다시 한번 확인 후 진행하시기 바랍니다. </li>
                        <li class="c-text-list__item u-co-gray">납부금액은 하기 모회선 번호로 <span  class="u-co-point-4 u-fs-15">통합 청구</span>됩니다. </li>
                        <li class="c-text-list__item u-co-gray">
                            애플 워치는 원넘버 서비스가 자동 가입되며 <span  class="u-co-point-4 u-fs-15">갤럭시 워치는 원넘버 서비스 이용을 원하실 경우 개통 후 고객센터(114)를 통해 신청 바랍니다.(무료)</span> <br/>
                            ※ 원넘버 부가서비스 : 스마트 워치에서도 휴대폰과 같은 번호로 통화 및 문자메시지를 발신하고 휴대폰
                            번호로 오는 전화를 워치에서도 동시에 수신 가능하게 하는 서비스
                        </li>
                    </ul>
                </div>

                <div class="c-form-wrap u-mt--20">
                    <div class="c-form-group" role="group" aira-labelledby="eSimePhoneTitle">
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input u-mt--0">
                                    <input class="c-input" id="eid" maxlength='60' type="text"  aria-invalid="true" value="${AppformReq.eid}" readonly>
                                    <label class="c-form__label" for="eid">EID</label>
                                </div>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__input u-mt--0">
                                    <input class="c-input" id="imei2" maxlength='60' type="text"  aria-invalid="true" value="${AppformReq.imei1}" readonly >
                                    <label class="c-form__label" for="imei2">IMEI</label>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>



                <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--48" >워치 개통 희망 번호</h4>
                <c:if test="${AppformReq.onOffType eq '5' }">
                    <div class="c-form-wrap reservation u-mt--32 _self">
                        <div class="c-form-group" role="group" aira-labelledby="inpReservNumHead">
                            <div class="c-flex c-flex--jfy-between">
                                <h5 class="c-group--head u-mb--0" id="inpReservNumHead">번호예약</h5>
                                <span id="searchCnt" class="u-co-point-4 u-fs-15">조회 가능 횟수<b>20회</b>
                      </span>
                            </div>

                            <!-- 번호 조회 전-->
                            <div class="c-box c-box--type1">
                                <!-- [2022-01-19] 클래스 추가 -->
                                <span class="reservation__text u-co-gray-7 _searchAfter" style="display:none">예약번호</span>
                                <span class="reservation__text u-co-black _searchAfter" id="reqWantNumberText" style="display:none">-</span>
                                <span class="reservation__text _searchBefor">010 - **** -</span>
                                <input class="c-input onlyNum _searchBefor" id="reqWantNumberN" type="number" name="" maxlength="4" pattern="[0-9]" placeholder="" value="${AppformReq.reqWantNumber}">
                                <button id="btnSearchNumber" action="RSV" class="c-button c-button--xsm c-button--mint c-button-round--xsm">번호조회</button>
                            </div>
                            <p class="c-bullet c-bullet--dot u-co-gray u-mt--16 _searchAfter" style="display:none">조회 가능 횟수를 초과할 경우 신청서를 재작성 해야 합니다.</p>


                        </div>
                    </div>
                </c:if>
                <div class="c-form-wrap reservation u-mt--32 _noSelf">
                    <div class="c-form-group" role="group" aira-labelledby="inpReservNumHead">
                        <div class="c-flex c-flex--jfy-between">
                            <h5 class="c-group--head u-mb--0" id="inpReservNumHead">가입희망번호 뒷 4자리</h5>

                        </div>
                        <div class="c-box c-box--type1">
                            1순위 <span class="reservation__text">010 - **** -</span>
                            <input class="c-input onlyNum" id="reqWantNumber" type="number"  maxlength="4" pattern="[0-9]" placeholder="" value="${AppformReq.reqWantNumber}">
                        </div>
                        <div class="c-box c-box--type1">
                            2순위 <span class="reservation__text">010 - **** -</span>
                            <input class="c-input onlyNum" id="reqWantNumber2" type="number"  maxlength="4" pattern="[0-9]" placeholder="" value="${AppformReq.reqWantNumber2}">
                        </div>
                        <div class="c-box c-box--type1">
                            3순위 <span class="reservation__text">010 - **** -</span>
                            <input class="c-input onlyNum" id="reqWantNumber3" type="number"  maxlength="4" pattern="[0-9]" placeholder="" value="${AppformReq.reqWantNumber3}">
                        </div>

                    </div>
                </div>



                <!-- 사은품 리스트-->
                <section id="divGift" class="gift-section" style="display:none;"></section>


                <div class="c-row c-row--lg">
                    <div class="c-form-wrap u-mt--48" id="recoIdArea">
                        <div class="c-form-group" role="group" aria-labelledby="inpAddInfoHead">
                            <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--48" id="inpAddInfoHead">
                                친구초대 추천인ID<span class="u-co-gray u-fs-14 u-ml--4">(선택)</span>
                            </h4>
                            <hr class="c-hr c-hr--title">

                            <div class="c-form-row c-col2">
                                <div class="c-form">
                                    <c:set var="recommendFlagList" value="${nmcp:getCodeList(Constants.RECOMMEND_FLAG_GRUP_CODE)}" />
                                    <label class="c-label c-hidden" for="recommendFlagCd">추가정보 선택</label>
                                    <select class="c-select" id="recommendFlagCd" >
                                        <c:forEach items="${recommendFlagList}" var="recommendFlag" >
                                            <option value="${recommendFlag.dtlCd}" <c:if test="${AppformReq.recommendFlagCd eq recommendFlag.dtlCd }" > selected </c:if> >${recommendFlag.dtlCdNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="c-form">
                                    <div class="c-input-wrap u-mt--0">
                                        <input class="c-input" id="recommendInfo" type="text" placeholder="직접입력" value="${AppformReq.recommendInfo}" maxlength="20"  >
                                        <label class="c-form__label c-hidden" for="recommendInfo">직접입력</label>
                                        <input type="hidden" name="commendId" id="commendId"  value=""  title="추천 아이디"  >
                                    </div>
                                </div>
                            </div>


                        </div>
                    </div>

                </div>


            </div>


            <div id="divBtnGroup1"  class="c-button-wrap u-mt--56">
                <button id="btnNext" class="c-button c-button--lg c-button--primary c-button--w460 is-disabled _btnNext">다음</button>
            </div>

            <div id="divBtnGroup2" class="c-button-wrap u-mt--56" style="display:none">
                <!-- 비활성화 시 .is-disabled 클래스 추가-->
                <a id="btnBefore" class="c-button c-button--lg c-button--gray u-width--220 " href="javascript:void(0)">이전</a>
                <a id="btnNext2" class="c-button c-button--lg c-button--primary u-width--220 is-disabled _btnNext" href="javascript:void(0)">다음</a>
            </div>



        </div>

    </t:putAttribute>
    <t:putAttribute name="popLayerAttr">

        <div class="c-bs-compare" id="bottom_sheet">
            <button class="c-bs-compare__openner" id="bs_acc_btn" type="button">
                <span class="sr-only">비교함 상세 열기|비교함 상세 닫기</span>
            </button>
            <div class="c-bs-compare__container">
                <div class="c-bs-compare__inner">
                    <div class="c-bs-compare__preview" aria-hidden="false">
                        <div class="u-h--100 c-flex c-flex--jfy-between">
                            <div class="c-flex u-ta-left">
                                <img id="phoneImg" class="usim-img" src="about:blank" alt="" onerror="this.src='/resources/images/mobile/content/img_noimage.png'" style="display:none">
                                <div class="inner-box">
                                    <p class="c-text c-text--fs18 u-fw--medium _bottomTitle"  ><!-- 갤럭시 A32 / 64GB / 블랙 --></p>
                                    <p class="c-text c-text--fs16 u-co-gray" id="bottomTitle2"><!-- 모두다 맘껏 15GB+ --></p>
                                </div>
                            </div>
                            <div class="fee-compare-wrap">
                                <div class="fee-compare-wrap__item">
                                    <div class="u-co-red">
                                        <span class="c-text c-text--fs14">월 납부금액</span>
                                        <span class="c-text c-text--fs32 c-text--bold u-ml--12" id="paySumMnthTxt2">0</span>
                                        <span class="c-text c-text--fs18 c-text--bold">원</span>
                                    </div>
                                    <p class="c-text c-text--fs13 u-co-gray">가입비 및 유심비 등 기타요금은 별도 청구됩니다</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-bs-compare__detail" id="acc_content_1" aria-hidden="true">
                        <div class="top-sticky-banner">
                            <div class="swiper-banner" id="swiperBanner">
                                <div class="swiper-container">
                                    <div class="swiper-wrapper">
                                        <div class="swiper-slide">
                                            <img src="/resources/images/portal/content/img_sticky_banner.png" alt="">
                                        </div>
                                        <div class="swiper-slide">
                                            <img src="/resources/images/portal/content/img_sticky_banner.png" alt="">
                                        </div>
                                        <div class="swiper-slide">
                                            <img src="/resources/images/portal/content/img_sticky_banner.png" alt="">
                                        </div>
                                    </div>
                                </div>
                                <button class="swiper-button-next" type="button"></button>
                                <button class="swiper-button-prev" type="button"></button>
                            </div>
                        </div>
                        <div class="c-row c-col2-row c-scrolly-auto">
                            <div class="c-col2-left">
                                <div class="c-addition-wrap">
                                    <!-- [2022-01-20] 마크업 수정-->
                                    <strong class="c-addition c-addition--type1">가입정보</strong>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>가입유형</dt>
                                        <dd class="u-ta-right" id="ddOperTypeNm"><!-- 번호이동 --></dd>
                                    </dl>

                                    <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                                    <dl class="c-addition c-addition--type1">
                                        <dt>
                                            월 통신요금
                                        </dt>
                                        <dd class="u-ta-right">
                                            <b id="payMnthChargeAmtTxt">-</b>원
                                        </dd>
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>기본요금</dt>
                                        <dd class="u-ta-right" id="baseAmt">- 원</dd>
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>
                                            <a href="#n" role="button" data-tpbox="#deli_tp1" aria-describedby="#deli-tp1__title">기본할인<i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                            </a>
                                            <div class="c-tooltip-box is-active" id="deli_tp1" role="tooltip" style="left: 60px">
                                                <div class="c-tooltip-box__title c-hidden" id="deli-tp1__title">기본할인 상세설명</div>
                                                <div class="c-tooltip-box__title">기본할인</div>
                                                <!--[2022-02-23] 툴팁박스 내용 수정-->
                                                <div class="c-tooltip-box__content">약정 가입시 기본적으로 제공되는 요금제 할인금액입니다.</div>
                                            </div>
                                        </dt>
                                        <dd class="u-ta-right">
                                            <b>
                                                <em id="dcAmtTxt">-원</em>
                                            </b>
                                        </dd>
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>
                                            <a href="#n" role="button" data-tpbox="#deli_tp2" aria-describedby="#deli-tp2__title">요금할인<i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                            </a>
                                            <div class="c-tooltip-box is-active" id="deli_tp2" role="tooltip" style="left: 60px">
                                                <div class="c-tooltip-box__title c-hidden" id="deli-tp2__title">요금할인 상세설명</div>
                                                <div class="c-tooltip-box__title">요금할인</div>
                                                <div class="c-tooltip-box__content">약정- 할인 선택 시 제공되는 요금할인 혜택입니다.</div>
                                            </div>
                                        </dt>
                                        <dd class="u-ta-right">
                                            <b>
                                                <em id="addDcAmt">-원</em>
                                            </b>
                                        </dd>
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>프로모션 할인</dt>
                                        <dd class="u-ta-right">
                                            <b>
                                                <em id="promotionDcAmtTxt">- 원</em>
                                            </b>
                                        </dd>
                                    </dl>
                                    <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                                    <dl class="c-addition c-addition--type1">
                                        <dt>기타요금</dt>
                                        <!-- <dd class="u-ta-right">
                                          <b>7,400</b>원
                                        </dd> -->
                                    </dl>
                                    <dl class="c-addition c-addition--type2">
                                        <dt>가입비(3개월 분납)</dt>
                                        <dd class="u-ta-right" id="joinPriceTxt">
                                            <!-- <span class="c-text u-td-line-through">7,200</span>(무료) -->
                                        </dd>
                                    </dl>

                                    <dl class="c-addition c-addition--type2">
                                        <dt>eSIM(1회)</dt>
                                        <dd class="u-ta-right" id="usimPriceTxt"><!-- 6,600 원 --></dd>
                                    </dl>




                                </div>
                            </div>
                            <div class="c-col2-right">
                                <div class="c-box c-box--type1">
                                    <div class="c-addition-wrap">
                                        <dl class="c-addition c-addition--type1 c-addition--sum">
                                            <dt>
                                                <b>월 납부금액</b>
                                                <span class="c-text c-text--fs14 u-co-gray">(부가세 포함)</span>
                                            </dt>
                                            <dd class="u-ml--auto u-ta-right">
                                                <b id="paySumMnthTxt">-</b>원
                                            </dd>
                                        </dl><!-- [2022-01-20] 마크업 수정-->
                                        <p class="c-text c-text--fs15 u-co-gray" id="pRateNm">-</p>
                                        <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">





                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                            <c:if test="${AppformReq.onOffType ne '5'}">
                                                <li class="c-text-list__item u-co-gray">
                                                    eSIM비는 다운로드에 상관없이 최초 개통 시 1회 발생되며, 프로파일 삭제 등으로 재 다운로드 시 추가 발생합니다.
                                                </li>
                                            </c:if>
                                            <li class="c-text-list__item u-co-gray">월 납부금액은 부가서비스 등의 사용에 따라 추가금액이 합산되어 청구될 수 있습니다.</li>
                                            <li class="c-text-list__item u-co-gray">월 납부금액은 부가세 포함 금액입니다.</li>
                                            <li class="c-text-list__item u-co-gray">타사향(SK, LG U+ 등) 단말은 일부 서비스(MMS,영상통화, 교통카드 기능 등) 이용이 제한될 수 있습니다.</li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="c-button-wrap u-mt--48">
                                    <button id="btnNext3" class="c-button c-button--full c-button--primary _btnNext">다음</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>








        <!--사전동의 안내-->
        <div class="c-modal c-modal--xlg" id="before-agree-modal" role="dialog" aria-labelledby="#before-agree-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="before-agree-modal__title">번호이동 사전동의 안내</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <h3 class="c-heading c-heading--fs20">번호이동을 위해서는 현재 사용 중인 통신사의 인증 절차가 필요합니다.</h3>
                        <p class="c-text c-text--fs17 u-co-gray u-mt--12">사전 동의 요청 시 문자가 발송되며, 문자가 오지 않을 경우 통신사 ARS로 전화하셔서 동의를 진행해 주시기 바랍니다.</p>
                        <hr class="c-hr c-hr--basic u-mt--48">
                        <h4 class="c-heading c-heading--fs16 u-mt--48">문자인증방법<span class="c-text c-text--fs14 u-co-gray u-ml--8">사전동의 요청 클릭 후 발송되는 문자를 통해 동의 진행</span>
                        </h4>
                        <ul class="c-step-list c-step-list--type2">
                            <li class="c-step-list__item">
                                <span class="c-step-list__label">STEP 1</span>
                                <i class="c-step-list__image" aria-hidden="true">
                                    <img src="/resources/images/portal/content/img_certi_sms1.png" alt="">
                                </i>
                                <p class="c-step-list__text"> 번호이동 인증 유형 입력 후
                                    <br>‘다음’ 버튼 클릭
                                </p>
                            </li>
                            <li class="c-step-list__item">
                                <span class="c-step-list__label">STEP 2</span>
                                <i class="c-step-list__image" aria-hidden="true">
                                    <img src="/resources/images/portal/content/img_certi_sms2.png" alt="">
                                </i>
                                <p class="c-step-list__text"> 사전동의 문자 내 URL을 접속하여
                                    <br>번호이동 동의 진행
                                </p>
                            </li>
                            <li class="c-step-list__item">
                                <span class="c-step-list__label">STEP 3</span>
                                <i class="c-step-list__image" aria-hidden="true">
                                    <img src="/resources/images/portal/content/img_certi_sms3.png" alt="">
                                </i>
                                <p class="c-step-list__text">‘사전체크 확인’ 버튼을 클릭하여
                                    <br>다음 단계로 이동
                                </p>
                            </li>
                        </ul>
                        <h4 class="c-heading c-heading--fs16 u-mt--48">ARS인증방법<span class="c-text c-text--fs14 u-co-gray u-ml--8">사전동의 클릭 후 안내되는 ARS로 전화하여 동의 진행</span>
                        </h4>
                        <ul class="c-step-list c-step-list--type2">
                            <li class="c-step-list__item">
                                <span class="c-step-list__label">STEP 1</span>
                                <i class="c-step-list__image" aria-hidden="true">
                                    <img src="/resources/images/portal/content/img_certi_ars1.png" alt="">
                                </i>
                                <p class="c-step-list__text"> 번호이동 인증 유형 입력 후
                                    <br>‘다음’ 버튼 클릭
                                </p>
                            </li>
                            <li class="c-step-list__item">
                                <span class="c-step-list__label">STEP 2</span>
                                <i class="c-step-list__image" aria-hidden="true">
                                    <img src="/resources/images/portal/content/img_certi_ars2.png" alt="">
                                </i>
                                <p class="c-step-list__text"> 팝업 내 안내되는 전 통신사 ARS
                                    <br>번호로 전화 동의 진행
                                </p>
                            </li>
                            <li class="c-step-list__item">
                                <span class="c-step-list__label">STEP 3</span>
                                <i class="c-step-list__image" aria-hidden="true">
                                    <img src="/resources/images/portal/content/img_certi_ars3.png" alt="">
                                </i>
                                <p class="c-step-list__text">‘사전체크 확인’ 버튼을 클릭하여
                                    <br>다음 단계로 이동
                                </p>
                            </li>
                        </ul>
                        <div class="c-table u-mt--64">
                            <table>
                                <caption>통신사, 사전동의 ARS번호로 구성된 통신사 ARS 정보</caption>
                                <colgroup>
                                    <col style="width: 50%">
                                    <col style="width: 50%">
                                </colgroup>
                                <thead>
                                <tr>
                                    <th scope="col">통신사</th>
                                    <th scope="col">사전동의 ARS 번호</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>KT / KT 알뜰폰(MVNO)</td>
                                    <td>1588-2935</td>
                                </tr>
                                <tr>
                                    <td>SKT</td>
                                    <td>1566-1509</td>
                                </tr>
                                <tr>
                                    <td>SKT 알뜰폰(MVNO)</td>
                                    <td>1599-0133</td>
                                </tr>
                                <tr>
                                    <td>LGU+ / LGU+ 알뜰폰(MVNO)</td>
                                    <td>1544-3553</td>
                                </tr>
                                <tr>
                                    <td>CJ헬로모바일</td>
                                    <td>070-7336-7766</td>
                                </tr>
                                <tr>
                                    <td>세종텔레콤(스노우맨)</td>
                                    <td>1688-9345</td>
                                </tr>
                                <tr>
                                    <td>한국케이블텔레콤_KCT</td>
                                    <td>1877-9120</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>



        <!-- 번호예약 Layer  layerSearchNum -->
        <div class="c-modal c-modal--md" id="searchNumDialog" role="dialog" tabindex="-1" aria-labelledby="#searchNumTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="searchNumTitle">예약가능 번호 리스트</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="c-form-wrap">
                            <div class="c-form-group" role="group" aira-labelledby="radNumGroupHead">
                                <h3 class="c-group--head u-fs-20 u-fw--regular" id="radNumGroupHead">전화번호 선택</h3>
                                <!--[2022-01-19] 마크업 수정-->
                                <div class="c-checktype-row c-col2 u-mt--32 _list">
                                    <!-- <input class="c-radio" id="radSelNum1" type="radio" name="radSelNum">
                                    <label class="c-label u-mb--20" for="radSelNum1">010-1111-1234</label> -->

                                </div>
                            </div>
                        </div>
                        <div class="c-button-wrap u-mt--32">
                            <button class="c-button c-button--lg c-button--gray u-width--220" data-dialog-close>취소</button>
                            <button id="btnConfirmNum" class="c-button c-button--lg c-button--primary u-width--220" >확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 셀프 개통 Layer-->
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
                        <div id="divReqPreButton" class="c-button-wrap _simpleDialogButton">
                            <button id="reqPreButton" class="c-button c-button--lg c-button--primary u-width--220">개통사전체크확인</button>
                        </div>

                        <div id="divChangeButton" class="c-button-wrap _simpleDialogButton">
                            <button id="btnOnline" class="c-button c-button--lg c-button--gray u-width--220" >해피콜 신청서 작성</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 _btnCheck" data-dialog-close>확인</button>
                            
                            
                        </div>

                        <div id="divChangeUsimButton" class="c-button-wrap _simpleDialogButton">
                            <button onclick="location.href='/m/appForm/reqSelfDlvry.do'" class="c-button c-button--lg c-button--gray u-width--220" >유심구매</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 _btnCheck" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 셀프 개통 Layer-->
        <div class="c-modal c-modal--xlg" id="simpleDialogNP1" role="dialog" aria-labelledby="#simpleDialogNP1Title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <h3 class="c-heading c-heading--fs24 u-ta-center">번호이동 사전 동의 안내</h3>
                        <ul class="c-text-list c-bullet c-bullet--dot u-mt--48">
                            <li class="c-text-list__item">현재 사용하고 계시는 통신사의 상황에 따라, 사전동의 SMS 수신 및 인증이 원활하지 않을 수 있으며, 이 경우 ARS로 사전동의 진행을 부탁드립니다.</li>
                            <li class="c-text-list__item">번호정보 내 현재 이용 중이신 통신사를 잘못 선택하셨을 경우에는 인증이 불가합니다.</li>
                            <li class="c-text-list__item"> ARS의 경우 현재 사용 중이신 통신사에 따라
                                <span class="u-co-red-1">10분 이상 소요</span>될 수 있습니다.
                            </li>
                        </ul>
                        <div class="before-agree-wrap u-mt--64">
                            <div class="before-agree-wrap__item">
                                <h4 class="before-agree-wrap__title">문자를 받았어요!</h4>
                                <div class="before-agree-wrap__text"> 사전동의 문자 수령 시, 수신된 문자의 URL을 통하여
                                    <br>인증을 완료해주세요.
                                </div>
                                <div class="c-button-wrap">
                                    <button id="bntNp1Confirm" class="c-button c-button--lg c-button--primary u-width--220">확인</button>
                                </div>
                            </div>
                            <div class="before-agree-wrap__item">
                                <h4 class="before-agree-wrap__title">문자를 못 받았어요!</h4>
                                <div class="before-agree-wrap__text"> 현재 사용하고 계시는 통신사 ARS로 전화하여 안내에 따라
                                    <br>번호 이동 동의 후 ‘사전체크 확인’ 버튼을 눌러 다음 단계로
                                    <br>이동해주세요.
                                </div>
                                <div class="c-button-wrap u-mt--48">
                                    <button id="bntArsConfirm" class="c-button c-button--lg c-button--primary u-width--220">ARS 번호확인</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--번호이동 사전동의 ARS Layer-->
        <div class="c-modal c-modal--xlg" id="simpleDialogNp2" role="dialog" aria-labelledby="#modal_game_title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body" tabindex="0">
                        <div class="game-wrap">
                            <div class="game-pre">
                                <p class="c-text c-text--fs24 u-fw--medium u-ta-center">번호이동 사전동의 ARS 후
                                    <br>아래 ‘사전체크 확인’ 버튼이 활성화 될 때까지
                                    <br>잠시만 기다려주세요.
                                </p>
                                <div class="c-form u-mt--48">
                                    <label class="c-label" for="arsGuide">
                                        <a href="#n1" role="button" data-tpbox="#ars_tp1" aria-describedby="#ars_tp1__title">ARS 번호 안내<i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                        </a>
                                        <div class="c-tooltip-box is-active" id="ars_tp1" role="tooltip" style="left: 100px">
                                            <div class="c-tooltip-box__title c-hidden" id="ars_tp1__title">ARS 번호 안내 상세설명</div>
                                            <div class="c-tooltip-box__content">
                                                <ul class="c-text-list c-bullet c-bullet--dot">
                                                    <li class="c-text-list__item">사전동의 문자를 통해서 인증하신 경우 ARS 인증은 불필요합니다. ‘개통 사전체크 확인’ 버튼 활성화 시 선택해주세요.</li>
                                                    <li class="c-text-list__item">사전동의 문자를 못 받은 경우, 하단 ARS 번호를 통해 번호이동 서비스 이용 가능 대상 여부를 확인해주세요. ARS 진행 후 ‘개통 사전체크 확인’ 버튼이 활성화됩니다.</li>
                                                </ul>
                                            </div>
                                        </div>
                                        <!--[2022-02-18] UI 변경 적용-->
                                        <button class="c-button c-button--underline" data-dialog-trigger="#modalArsNum">전 통신사 ARS 번호 보기</button>
                                    </label>
                                    <!--[2022-02-18] UI 변경 적용-->
                                    <input class="c-input" id="arsGuide" type="text" placeholder="" value="KT | 1588-2935">
                                    <div class="c-button-wrap u-mt--48">
                                        <button class="c-button c-button--full c-button--primary is-disabled" id="reqPreCheck" disabled>사전체크 확인</button>
                                    </div>
                                </div>
                            </div>
                            <div class="game-container">
                                <button id="start_game" type="button">
                                    <img src="/resources/images/portal/content/img_temp_game.png" alt="">
                                </button>
                                <div class="game-area">
                                    <div class="game-over-cover">
                                        <div class="game-over-txt">
                                            <img src="/resources/images/portal/game/gameover_text.png" alt="">
                                            <button class="reset_button" id="game_reset_btn" type="button"></button>
                                        </div>
                                    </div>
                                    <div class="game-score-current" id="game_score_current">0</div>
                                    <div class="game-score-high" id="game_score_high">최고기록 0점</div>
                                    <div id="game_canvas"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!--[2022-02-18] ARS번호 안내 팝업 추가 -->
        <div class="c-modal c-modal--md" id="modalArsNum" role="dialog" tabindex="-1" aria-labelledby="#modalNumInquiryTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="modalNumInquiryTitle">전 통신사 ARS 번호 안내</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="c-table">
                            <table>
                                <!-- [2022-01-14] 테이블 caption정보 수정-->
                                <caption>통신사, 사전동의 ARS번호로 구성된 통신사 ARS 정보</caption>
                                <colgroup>
                                    <col style="width: 50%">
                                    <col style="width: 50%">
                                </colgroup>
                                <thead>
                                <tr>
                                    <th scope="col">통신사</th>
                                    <th scope="col">사전동의 ARS 번호</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>KT / KT 알뜰폰(MVNO)</td>
                                    <td>1588-2935</td>
                                </tr>
                                <tr>
                                    <td>SKT</td>
                                    <td>1566-1509</td>
                                </tr>
                                <tr>
                                    <td>SKT 알뜰폰(MVNO)</td>
                                    <td>1599-0133</td>
                                </tr>
                                <tr>
                                    <td>LGU+ / LGU+ 알뜰폰(MVNO)</td>
                                    <td>1544-3553</td>
                                </tr>
                                <tr>
                                    <td>CJ헬로모바일</td>
                                    <td>070-7336-7766</td>
                                </tr>
                                <tr>
                                    <td>세종텔레콤(스노우맨)</td>
                                    <td>1688-9345</td>
                                </tr>
                                <tr>
                                    <td>한국케이블텔레콤_KCT</td>
                                    <td>1877-9120</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 이탈방지 Layer-->
        <button class="c-button c-button c-button--white" style="display:none" data-dialog-trigger="#preventLeaveModal" id="chkLayerPopBtn">페이지 이탈방지 popup 호출</button>
        <div class="c-modal c-modal--md" id="preventLeaveModal" role="dialog" aria-labelledby="#preventLeave__title">
            <div class="c-modal__dialog" role="document">
                <c:set var="O101List" value="${nmcp:getBannerList('O101')}" />
                <c:if test="${fn:length(O101List) gt 0}">
                    <div class="outbox-banner swiper-container" id="outbox-banner">
                        <div class="swiper-wrapper">
                            <c:forEach var="item" items="${O101List}">
                                <c:choose>
                                    <c:when test="${item.linkTarget eq 'Y' and item.linkUrlAdr ne ''}">
                                        <div class="swiper-slide" href="javascript:void(0);" onclick="javascript:insertBannAccess('${item.bannSeq}','${item.bannCtg}');location.href='${item.linkUrlAdr}';"><img src="${item.bannImg}" alt="${item.bannNm}"></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="swiper-slide"><img src="${item.bannImg}" alt="${item.bannNm}"></div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                        <div class="swiper-controls-wrap">
                            <div class="swiper-controls">
                                <button class="swiper-button-pause" type="button" aria-prssed="false">
                                    <span class="c-hidden">자동재생 정지</span>
                                </button>
                                <div class="swiper-pagination" aria-hidden="true"></div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="c-modal__content prevent-leave">
                    <h2 class="c-hidden" id="preventLeave__title">페이지 이탈방지</h2>
                    <div class="c-modal__body c-modal__body--full">
                        <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                        <h3 class="c-heading c-heading--fs24 u-mt--32">
                            앗 ! <br>신청서 작성이 아직 남아있어요. <br>화면에서 나가시겠습니까?
                        </h3>
                        <!--[2022-01-12] 문구 띄어쓰기 변경-->
                        <p class="u-mt--16">
                            화면 이탈 시 입력 내용은 '신청조회' 메뉴에서 확인 후 <br>이어 할 수 있습니다. <br>(입력 정보는 7일간 보관됩니다.)
                        </p>
                    </div>
                    <div class="c-modal__footer u-flex-center">
                        <div class="u-box--w460 c-button-wrap">
                            <button class="c-button c-button--full c-button--gray" onclick="leaveUrlGo();">나가기</button>
                            <button class="c-button c-button--full c-button--primary"  data-dialog-close >계속하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>





    </t:putAttribute>
</t:insertDefinition>
