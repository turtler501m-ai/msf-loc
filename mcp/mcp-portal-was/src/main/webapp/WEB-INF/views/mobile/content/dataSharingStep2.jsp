<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/validator.js?version=24.04.01"></script>
        <script type="text/javascript">
            VALIDATE_NOT_EMPTY_MSG = {};
            VALIDATE_NOT_EMPTY_MSG.radOpenType1 = "원하시는 개통 유형을 선택해 주세요.";

            history.pushState(null, null,location.href);
            window.onpopstate = function (event){
                location.href = "/m/content/dataSharingStep1.do";
            }

            $(document).ready(function (){
                $("#ncn").on("change",function(){
                    ajaxCommon.createForm({
                        method:"post"
                        ,action:"/m/content/dataSharingStep2.do"
                    });

                    ajaxCommon.attachHiddenElement("menuType",$("#menuType").val());
                    ajaxCommon.attachHiddenElement("ncn",$("#ncn option:selected").val());
                    ajaxCommon.formSubmit();
                });

                // 자주 묻는 질문 및 유의사항
                ajaxCommon.getItemNoLoading({
                    id:'getNotice'
                    ,cache:false
                    ,url:'/termsPop.do'
                    ,data: "cdGroupId1=TERMSSHA&cdGroupId2=faqImportantInfoM"
                    ,dataType:"json"
                } ,function(jsonObj){
                    var inHtml = unescapeHtml(jsonObj.docContent);
                    $('.data-sharing-notice').html(inHtml);
                    KTM.initialize();
                });
            });



            function btnRegDtl(param){
                openPage('termsInfoPop','/termsPop.do',param);
            }

            //데이터 쉐어링결합하기
            function btn_sharingReg(){

                validator.config={};
                validator.config['radOpenType1'] = 'isNonEmpty';

                if (validator.validate()) {

                    ajaxCommon.getItemNoLoading({
                        id:'isSimpleApply'
                        ,cache:false
                        ,url:'/appForm/isSimpleApplyAjax.do'
                        ,data: ""
                        ,dataType:"json"
                        ,async:false
                    },function(jsonObj){
                        var simpleApplyObj = jsonObj ;

                        if (!simpleApplyObj.IsMacTime ) {
                            MCP.alert("<b>셀프개통이 불가능한 시간입니다.</b><br>이용가능시간 : 08:00~21:50");
                        } else {
                            var onOffType = $('input[name=radOpenType]:checked').val();						//구분코드 7 모바일(셀프개통) 3 모바일
                            var cstmrType = $('#cstmrType').val();						//구분코드 7 모바일(셀프개통) 3 모바일

                            ajaxCommon.createForm({
                                method:"post"
                                ,action:"/m/content/dataSharingStep3.do"
                            });
                            ajaxCommon.attachHiddenElement("opmdSvcNo",	 $("#opmdSvcNo").val());
                            ajaxCommon.attachHiddenElement("contractNum",$("#ncn option:selected").val());
                            ajaxCommon.attachHiddenElement("onOffType", onOffType);
                            ajaxCommon.attachHiddenElement("cstmrType", cstmrType);
                            ajaxCommon.formSubmit();
                        }
                    });


                } else {
                    MCP.alert(validator.getErrorMsg());
                }


            }
        </script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <input type="hidden" name="cstmrType" id="cstmrType" value="${cstmrType}"/>
            <input type="hidden" name="isMacTime" id="isMacTime" value="${isMacTime}"/>
            <%@ include file="/WEB-INF/views/mobile/content/dataSharingInfo.jsp"%>

            <c:if test="${empty maskingSession}">
              <div class="masking-badge-wrap">
                  <div class="masking-badge">
                      <a class="masking-badge__button" href="/m/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
                        <i class="masking-badge__icon" aria-hidden="true"></i>
                        <p>가려진(*) 정보보기</p>
                      </a>
                  </div>
              </div>
            </c:if>
            <div class="c-form data-sharing-form u-mt--48">
                <div class="c-form__title--type2 flex-type u-mb--12">내 정보</div>
                <div class="c-form__group" role="group" aria-labeledby="inpHP">
                    <div class="c-form__select has-value">
                        <select class="c-select" name="ncn" id="ncn"   title="서비스번호" >
                            <c:choose>
                                <c:when test="${'02' eq  searchVO.userDivisionYn}">
                                    <option value="${searchVO.ncn}">${searchVO.ctn}</option>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${cntrList}" var="item">
                                         <c:choose>
                                             <c:when test="${maskingSession eq 'Y'}">
                                                 <option value="${item.svcCntrNo }" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.formatUnSvcNo }</option>
                                             </c:when>
                                             <c:otherwise>
                                                 <option value="${item.svcCntrNo }" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.cntrMobileNo }</option>
                                             </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </select>
                        <label class="c-form__label">휴대폰 번호</label>
                    </div>
                    <div class="c-form__input has-value">
                        <input class="c-input" id="inpHP" type="text" placeholder="사용중인 요금제"  value="${mcpUserCntrMngDto.rateNm}" readonly>
                        <label class="c-form__label" for="inpHP">요금제</label>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${!empty moscDataSharingResDto.sharingList}">
                    <h3 class="c-heading c-heading--type2 u-mb--12">결합 중인 데이터쉐어링 회선</h3>
                    <div class="c-table">
                        <table>
                            <caption>결합 중인 데이터쉐어링 회선</caption>
                            <colgroup>
                                <col style="width: 50%">
                                <col style="width: 50%">
                            </colgroup>
                            <thead>
                            <tr>
                                <th scope="row">휴대폰 번호</th>
                                <th scope="row">결합일</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${moscDataSharingResDto.sharingList}" var="sharingListDto">
                                <tr>
                                    <td class="u-ta-center">${sharingListDto.svcNo}</td>
                                    <td class="u-ta-center">${fn:substring(sharingListDto.efctStDt,0,4)}.${fn:substring(sharingListDto.efctStDt,4,6)}.${fn:substring(sharingListDto.efctStDt,6,8)}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="c-text-box c-text-box--red u-mt--32">
                        <p class="c-text c-text--type5 u-co-black">이미 결합되어 있는</p>
                        <b class="c-text c-text--type2">회선이 있습니다.</b>
                    </div><!-- //-->
                </c:when>
                <c:when test="${'Y' eq subStatusYn}">
                    <div class="c-text-box c-text-box--red u-mt--8">
                        <p class="c-text c-text--type5 u-co-black">사용중인 회선만</p>
                        <b class="c-text c-text--type2">데이터쉐어링 결합 가능합니다.</b>
                    </div>
                </c:when>
                <c:when test="${'Y' eq customerType}">
                    <div class="c-text-box c-text-box--red u-mt--8">
                        <p class="c-text c-text--type5 u-co-black">개인고객만</p>
                        <b class="c-text c-text--type2">가입이 가능합니다.</b>
                    </div>
                </c:when>

                <c:when test="${'Y' eq changeYn}">
                    <div class="c-text-box c-text-box--red u-mt--8">
                        <p class="c-text c-text--type5 u-co-black">납부방법을 신용카드/은행계좌</p>
                        <b class="c-text c-text--type2">자동이체로 변경 후 <br>쉐어링 결합 신청이 가능합니다.</b>
                    </div>
                </c:when>
                <c:when test="${'Y' ne socChkYn}">
                    <div class="c-text-box c-text-box--red deco-combine u-mt--8">
                        <p class="c-text c-text--type5 u-co-black">데이터쉐어링 결합 사용이</p>
                        <b class="c-text c-text--type2">불가능한 요금제입니다.</b>
                        <img src="/resources/images/mobile/content/img_combine.svg" alt="">
                    </div><!-- //-->
                </c:when>
                <c:otherwise>
                    <div class="c-text-box c-text-box--blue u-mt--8 sharingYn">
                        <p class="c-text c-text--type5 u-co-black">데이터쉐어링 결합이</p>
                        <b class="c-text c-text--type2">가능한 회선입니다.</b>
                    </div>
                    <div class="c-form">
                        <span class="c-form__title--type2" id="radOpenType">개통 유형 선택</span>
                        <div class="c-check-wrap" role="group" aria-labelledby="radOpenType">


                            <c:choose>
                                <c:when test="${'NA' eq  cstmrType and isMacTime}">
                                    <div class="c-chk-wrap">
                                        <input class="c-radio c-radio--button" id="radOpenType1" type="radio" name="radOpenType" value="7">
                                        <label class="c-label" for="radOpenType1">셀프개통</label>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="c-chk-wrap">
                                        <input class="c-radio c-radio--button" id="radOpenType1" type="radio" name="radOpenType" value="7" disabled="disabled" >
                                        <label class="c-label" for="radOpenType1">셀프개통</label>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button" id="radOpenType2" type="radio" name="radOpenType" value="3">
                                <label class="c-label" for="radOpenType2">상담사 개통 신청</label>
                            </div>
                        </div>
                    </div>
                    <div class="c-box c-box--type6">
                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                            <li class="c-text-list__item">셀프개통 가능시간 : 08:00 ~ 21:50</li>
                            <li class="c-text-list__item">미성년자(19세 미만 내국인) 및 외국인은 상담사 개통 신청만 가능합니다.(셀프개통 불가)</li>
                            <li class="c-text-list__item">미성년자의 경우 법정대리인의 동의(인증)이 필요합니다.</li>
                            <li class="c-text-list__item">상담사 개통으로 신청하실 경우 순차적으로 AI상담사 또는 개통 상담사가 전화를 드려 본인확인을 진행하며, 3회 이상 통화가 되지 않을 경우에는 신청정보가 자동으로 삭제됩니다.</li>
                        </ul>
                    </div>
                    <div class="c-button-wrap sharingYn">
                        <button class="c-button c-button--full c-button--primary" onclick="btn_sharingReg();">데이터쉐어링 가입하기</button>
                    </div>
                </c:otherwise>
            </c:choose>
            <input type="hidden" name="menuType" id="menuType" value="${menuType}"/>
            <div class="data-sharing-notice">

            </div>
        </div>


    </t:putAttribute>
</t:insertDefinition>