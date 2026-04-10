<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/validator.js?version=20.07.07"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=21.09.30"></script>
        <script type="text/javascript" src="/resources/js/mobile/content/reqSharingViewOcr.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js?version=21.12.22"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/content/dataSharingStep3.js?version=26.03.03"></script>
        <script type="text/javascript">
            history.pushState(null, null,location.href);
            window.onpopstate = function (event){
                location.href = "/m/content/dataSharingStep1.do";
            }
        </script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">데이터쉐어링<button class="header-clone__gnb"></button>
                </h2>
            </div>
            <form name="frm" id="frm">
                <input type="hidden" name="contractNum" id="contractNum" value="${myShareDataReqDto.ncn}"/>
                <input type="hidden" name="realContractNum" id="realContractNum" value="${myShareDataReqDto.contractNum}"/>
                <input type="hidden" name="opmdSvcNo" id="opmdSvcNo" value=""/>
                <input type="hidden" name="authYn"      id="authYn" value=""/>
                <input type="hidden" name="isAuth"      id="isAuth" value=""/>
                <input type="hidden" id="birthday" name="birthday" value="${myShareDataReqDto.birthday}"  >
                <input type="hidden" id="iccYn" name="iccYn" value=""/>
                <input type="hidden" id="selfShareYn" name="selfShareYn" value="Y">
                <input type="hidden" id="selfShareSuccesYn" name="selfShareSuccesYn" value="">
            </form>

            <!-- PASS 인증 추가 -->
            <input type="hidden" id="isPassAuth" name="isPassAuth">
            <input type="hidden" name="onOffType" id="onOffType" value="${onOffType}"/>
            
            <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv" > <!--안면인증 결과 -->
            <input type="hidden" id="fathTrgYn" name="fathTrgYn" > <!--안면인증 대상여부 -->

            <div class="c-form u-mt--24" style="display:none" >
                <div class="c-check-wrap">
                    <div class="c-chk-wrap">
                        <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType1" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NA}" desc="19세 이상 내국인" <c:if test="${cstmrType eq Constants.CSTMR_TYPE_NA }" > checked </c:if> >
                        <label class="c-label" for="cstmrType1">
                            <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                            <span>19세 이상 내국인</span>
                        </label>
                    </div>
                    <div class="c-chk-wrap">
                        <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType2" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NM}" desc="19세 미만 미성년자" <c:if test="${cstmrType eq Constants.CSTMR_TYPE_NM }" > checked </c:if>  >
                        <label class="c-label" for="cstmrType2">
                            <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                            <span>19세 미만 미성년자</span>
                        </label>
                    </div>
                    <div class="c-chk-wrap">
                        <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}" desc="외국인" <c:if test="${cstmrType eq Constants.CSTMR_TYPE_FN }" > checked </c:if>>
                        <label class="c-label" for="cstmrType3">
                            <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                            <span>외국인</span>
                        </label>
                    </div>
                </div>
            </div>


            <h4 id="inpNameCertiTitle" class="c-form__title--type2">가입자 정보</h4>
            <div class="c-form">
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
                                <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn01" name="cstmrForeignerRrn01" autocomplete="off" type="tel" maxlength="6" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,0,6)}" placeholder="외국인 등록번호 앞자리" title="외국인 등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrForeignerRrn02');" >
                                <span>-</span>
                                <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn02" name="cstmrForeignerRrn02" autocomplete="off" type="password" maxlength="7" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,6,13)}" placeholder="외국인 등록번호 뒷자리" title="외국인 등록번호 뒷자리">
                                <label class="c-form__label" for="cstmrForeignerRrn01">외국인 등록번호</label>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="c-form-field _isDefault" >
                        <div class="c-form__input-group">
                            <div class="c-form__input c-form__input-division">
                                <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,0,6)}" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');">
                                <span>-</span>
                                <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="off" type="password" maxlength="7" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,6,13)}" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                                <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

            <h3 class="c-form__title--type2">결합 신청 회선</h3>
            <div class="c-form">
                <div class="c-form__input">
                    <input class="c-input" id="inpTel" type="text" name="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="${myShareDataReqDto.ctn}" readonly>
                    <label class="c-form__label" for="inpTel">가입회선 번호</label>
                </div>
            </div>

            <c:if test="${onOffType ne '7' and Constants.CSTMR_TYPE_NM ne cstmrType }">
                <h3 class="c-form__title--type2">연락처</h3>
                <div class="c-form">
                    <div class="c-form__input">
                        <input class="c-input" id="cstmrMobile" type="text" name="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="" >
                        <label class="c-form__label" for="inpTel">연락처</label>
                    </div>
                </div>
                <div class="c-form">
                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                        <li class="c-text-list__item">가입신청서를 작성 완료 시 순차적으로 AI상담사 또는 개통상담사가 전화를 드려 본인확인을 진행합니다.</li>
                        <li class="c-text-list__item">3회 이상 통화가 되지 않을 경우 신청 정보가 자동으로 삭제됩니다.</li>
                    </ul>
                </div>
            </c:if>

            <c:if test="${Constants.CSTMR_TYPE_NM eq cstmrType }">

                <div class="c-form _isTeen" >
                    <span class="c-form__title--type2" id="spMinor">법정대리인 정보</span>
                </div>
                <div class="c-form _isTeen" >
                    <div class="c-form__input">
                        <input class="c-input" id="minorAgentName" type="text" placeholder="법정대리인 성명" value="" maxlength=60 >
                        <label class="c-form__label" for="minorAgentName">법정대리인 성명</label>
                    </div>
                </div>
                <div class="c-form _isTeen" >
                    <div class="c-form__select">
                        <select id="minorAgentRelation" class="c-select">
                            <option value="">선택</option>
                            <option value="01">부</option>
                            <option value="02">모</option>
                            <option value="10">그외</option>
                        </select>
                        <label class="c-form__label" for="minorAgentRelation" >신청인과의 관계</label>
                    </div>
                </div>


                <div class="c-form-field _isTeen" >
                    <div class="c-form__input-group">
                        <div class="c-form__input c-form__input-division">
                            <input class="c-input--div2 onlyNum" id="minorAgentRrn01" name="minorAgentRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'minorAgentRrn02');">
                            <span>-</span>
                            <input class="c-input--div2 onlyNum" id="minorAgentRrn02" name="minorAgentRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                            <label class="c-form__label" for="minorAgentRrn01">법정대리인 주민등록번호</label>
                        </div>
                    </div>
                </div>

                <div class="c-form u-mt--8 _isTeen" >
                    <div class="c-form__input">
                        <input class="c-input onlyNum" id="minorAgentTel" type="tel" name="minorAgentTel" value="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력">
                        <label class="c-form__label" for="minorAgentTel">법정대리인 연락처 <em class="c-form__sublabel">(휴대폰)</em></label>
                    </div>
                </div>

                <div class="c-form _isTeen"  >
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
            </c:if>


            <!--  본인인증 방법 선택 -->
            <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                <jsp:param name="controlYn" value="N"/>
                <jsp:param name="reqAuth" value="CNAT"/>
            </jsp:include>


            <h3 class="c-form__title--type2">유심정보</h3>
            <div class="c-box">
                <img class="center-img" src="/resources/images/mobile/content/img_usim_card.png" alt="">
            </div>
            <div class="c-form c-scan-wrap">
                <div class="c-button-wrap c-button-wrap--right">
                    <button class="c-button _ocrRecord" data-type="usim">
                        <!--[2021-11-30] 마크업 순서 변경(디자인 변경 적용)-->
                        <!--[2021-12-02] 텍스트 컬러 적용 class 변경-->
                        <span class="c-text c-text--type3 u-co-sub-4" >스캔하기</span>
                        <i class="c-icon c-icon--scan" aria-hidden="true"></i>
                        <!--[$2021-12-02] 텍스트 컬러 적용 class 변경-->
                    </button>
                </div>
            </div>
            <div class="c-form">
                <input class="c-input numOnly" type="tel" id="iccId" maxlength="19" pattern="[0-9]*" placeholder="직접입력('-'없이 입력)" title="유심번호 입력">
            </div>
            <p class="c-bullet c-bullet--fyr u-co-gray">유심카드를 미리 구매하신 고객님께서는 유심번호 19자리를 입력해 주세요.</p>
            <div class="c-button-wrap">
                <button class="c-button c-button--full c-button--mint" id="iccBtn"   onclick="btn_usimChk();">유심번호 유효성 체크</button><!-- 유효성 체크 성공 시-->
            </div>




            <div class="c-form" id="selfCertSection1" >
                <span id="radIdCardHead" class="c-form__title--type2">신분증 확인</span>
                <div class="c-check-wrap c-check-wrap--type2" >
                    <div class="c-chk-wrap">
                        <input class="c-radio c-radio--button" id="selfCertType1" value="01" type="radio" name="selfCertType" >
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
                        <input class="c-radio c-radio--button" id="selfCertType6" value="06" type="radio" name="selfCertType" <c:if test='${Constants.CSTMR_TYPE_FN ne cstmrType }'> disabled </c:if> >
                        <label class="c-label" for="selfCertType6">외국인 등록증</label>
                    </div>
                    <div class="c-chk-wrap">
                        <input class="c-radio c-radio--button c-hidden" id="selfCertType5" value="05" type="radio" name="selfCertType" disabled>
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
            
            
            <div class="_chkIdCardTitle" style="display:none;">
                <div class="c-form c-scan-wrap" id="selfCertSection2" >
                    <div class="c-box u-mt--32">
                        <img id="imgRadIdCard" class="center-img" src="/resources/images/mobile/content/img_jumin_card.png" alt="">
                    </div>
                    <div class="c-button-wrap c-button-wrap--right _divScan" style="display:none;">
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
                                     <input id="driverSelfIssuNum" class="c-input" type="tel" name="driverSelfIssuNum" maxlength="15" placeholder="면허번호 입력"  value="${AppformReq.selfIssuNumDesc}">
                                     <label class="c-form__label" for="selfIssuNum">면허번호</label>
                            </div>
                             <input  type="hidden" id="driverSelfIssuNum1">
                             <input type="hidden"  id="driverSelfIssuNum2">
                        </div>
    
                 <div class="c-form _selfIssuNumF"  style="display:none;">
                    <div class="c-form__input">
                        <input class="c-input" id="selfIssuNum" type="tel"  pattern="[0-9]*" placeholder="보훈번호 입력" title="보훈번호"  value="" >
                        <!--[2021-11-22] 인풋타입 변경-->
                        <label class="c-form__label" for="selfIssuNum">보훈번호</label>
                    </div>
                </div>
    
                <div class="c-form c-form--datepicker" id="selfCertSection3" >
                    <div class="c-form__input " >
                        <input class="c-input date-input" id="selfIssuExprDt" type="text" placeholder="YYYY-MM-DD" title="발급일자"  maxlength="10"   value="" >
                        <label class="c-form__label" for="selfIssuExprDt">발급일자</label>
                        <button class="c-button">
                            <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                        </button>
                    </div>
                </div>
    
                <div id="selfCertSection4" >
                    <input class="c-checkbox" id="selfInqryAgrmYnFlag" type="checkbox" >
                    <label class="c-label u-mt--20" for="selfInqryAgrmYnFlag">본인 조회에 동의합니다</label>
                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                        <li class="c-text-list__item">입력하신 정보는 한국정보통신진흥협회(KAIT)에서 제공하는 부정가입방지 시스템을 통해 신분증 진위여부 판단에 이용됩니다.</li>
                    </ul>
                </div>
            </div>




                <div class="c-accordion c-accordion--type5 acc-agree">
    
                    <span class="c-form__title--type2"  >약관동의</span>
                    <div class="c-accordion__box">
                        <div class="c-accordion__item">
                            <div class="c-accordion__head">
                                <div class="c-accordion__check">
                                    <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                                    <label class="c-label" for="btnStplAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span></label>
                                </div>
                                <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeA1">
                                    <div class="c-accordion__trigger"> </div>
                                </button>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_agreeA1">
                                <div class="c-accordion__inside">
                                    <div class="c-agree__item">
                                        <input id="clausePriOfferFlag" code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                        <label class="c-label" for="clausePriOfferFlag">고유식별정보 수집&middot;이용 동의<span class="u-co-gray">(필수)</span></label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}');">
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                    <div class="c-agree__item">
                                        <input id="clausePriCollectFlag" code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                                        <label class="c-label" for="clausePriCollectFlag">개인정보/신용정보 수집·이용 동의<span class="u-co-gray">(필수)</span></label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}');" >
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                    <div class="c-agree__item">
                                        <input id="clauseEssCollectFlag" code="${Constants.CLAUSE_ESS_COLLECT_CODE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                                        <label class="c-label" for="clauseEssCollectFlag">개인정보 제3자 제공 동의<span class="u-co-gray">(필수)</span></label>
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
                                        <label class="c-label" for="notice">서비스 이용약관<span class="u-co-gray">(필수)</span></label>
                                        <button class="c-button c-button--xsm" onclick="fnSetEventId('notice');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}');" >
                                            <span class="c-hidden">상세보기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                    </div>
    
                                    <c:if test="${Constants.CSTMR_TYPE_NM eq cstmrType }">
                                        <div class="c-agree__item" >
                                            <input id="nwBlckAgrmYn" code="${Constants.NOTICE_CODE_23}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                            <label class="c-label" for="nwBlckAgrmYn">청소년 유해정보 네트워크차단 동의<span class="u-co-gray">(필수)</span>
                                            </label>
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('nwBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_23}');"  >
                                                <span class="c-hidden">상세보기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </div>
    
                                        <div class="c-agree__item" >
                                            <input id="appBlckAgrmYn" code="${Constants.NOTICE_CODE_24}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                                            <label class="c-label" for="appBlckAgrmYn">청소년 유해정보차단 APP 설치 동의<span class="u-co-gray">(필수)</span>
                                            </label>
                                            <button class="c-button c-button--xsm" onclick="fnSetEventId('appBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_24}');"  >
                                                <span class="c-hidden">상세보기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                    </c:if>
    
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
            </div>
            <c:if test="${onOffType eq '7'}" >
                <div class="c-form _self">
                    <span class="c-form__title--type2" >셀프개통 안내사항</span>
                    <div class="c-agree__item">
                        <input class="c-checkbox" id="clauseSimpleOpen" type="checkbox" name="clauseSimpleOpen" validityyn="Y" >
                        <label class="c-label" for="clauseSimpleOpen">본인은 안내사항을 확인하였습니다. <span class="u-co-gray">(필수)</span></label>
                    </div>
                    <div class="c-agree__inside">
                        <div class="self-agree__inside">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">
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



            <div class="c-button-wrap">
                <a class="c-button c-button--full c-button--gray"    href="javascript:;" onclick="sharing_cancel();">취소</a><!-- 활성화 시 .is-disabled 삭제-->
                <a class="c-button c-button--full c-button--primary is-disabled" href="javascript:;" id="sharing_reg" onclick="sharing_reg();">가입신청</a>
            </div>
            <hr class="c-hr c-hr--type2">
            <b class="c-text c-text--type3">
                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
            </b>
            <p class="c-bullet c-bullet--dot u-co-gray">데이터 쉐어링으로 가입하는 회선은 통화가 불가능한 데이터 전용 회선으로 가입되기 때문에 회선 번호는 무작위로 부여됩니다.</p>
        </div>

        <!-- 셀프 개통 Layer popup-->
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
                        <p class="c-text c-text--type2 u-co-gray _detail"></p>
                        <div id="divDefaultButton" class="c-button-wrap _simpleDialogButton">
                            <button class="c-button c-button--full c-button--primary _btnCheck" data-dialog-close >확인</button>
                        </div>
                        <div id="divChangeButton" class="c-button-wrap _simpleDialogButton">
                            <button class="c-button c-button--gray c-button--lg u-width--120 _btnCheck" data-dialog-close>확인</button>
                            
                            
                            <button id="btnOnline" class="c-button c-button--full c-button--primary">해피콜 신청서 작성</button>
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