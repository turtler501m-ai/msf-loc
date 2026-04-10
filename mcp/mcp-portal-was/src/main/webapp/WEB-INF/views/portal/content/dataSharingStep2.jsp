<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">데이터쉐어링</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/validator.js?version=24.04.01"></script>
        <script type="text/javascript">
			VALIDATE_NOT_EMPTY_MSG = {};
			VALIDATE_NOT_EMPTY_MSG.radOpenType1 = "원하시는 개통 유형을 선택해 주세요.";


			history.pushState(null, null,location.href);
                window.onpopstate = function (event){
                location.href = "/mySharingInfo.do";
            }

            function btn_search(){
                ajaxCommon.createForm({
                    method:"post"
                    ,action:"/content/dataSharingStep2.do"
                });
                ajaxCommon.attachHiddenElement("menuType",$("#menuType").val());
                ajaxCommon.attachHiddenElement("ncn",$("#ncn option:selected").val());
                ajaxCommon.formSubmit();
            }

            function btnRegDtl(param){
                openPage('termsInfoPop','/termsPop.do',param);
            }



            //데이터 쉐어링결합하기
            function btn_sharingReg(){
				validator.config={};
				validator.config['radOpenType1'] = 'isNonEmpty';

				if (validator.validate()) {
					//셀프개통 가능 여부 확인
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
								method: "post"
								, action: "/content/dataSharingStep3.do"
							});
							ajaxCommon.attachHiddenElement("contractNum", $("#ncn option:selected").val());
							ajaxCommon.attachHiddenElement("onOffType", onOffType);
							ajaxCommon.attachHiddenElement("cstmrType", cstmrType);
							ajaxCommon.formSubmit();
						}
					});
				} else {
					MCP.alert(validator.getErrorMsg());
				}
			}

			$(document).ready(function (){
				// 자주 묻는 질문 및 유의사항
				ajaxCommon.getItemNoLoading({
					id:'getNotice'
					,cache:false
					,url:'/termsPop.do'
					,data: "cdGroupId1=TERMSSHA&cdGroupId2=faqImportantInfo"
					,dataType:"json"
				} ,function(jsonObj){
					var inHtml = unescapeHtml(jsonObj.docContent);
					$('.data-sharing-notice').html(inHtml);
					KTM.initialize();
				});
			})
        </script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
	<div class="ly-content" id="main-content">
		<input type="hidden" name="cstmrType" id="cstmrType" value="${cstmrType}"/>
		<input type="hidden" name="isMacTime" id="isMacTime" value="${isMacTime}"/>
		<%@ include file="/WEB-INF/views/portal/content/dataSharingInfo.jsp"%>

        <div class="c-row c-row--lg">
	        <div class="c-form-wrap data-sharing-form u-mt--96">
				<c:if test="${empty maskingSession}">
					<div class="masking-badge-wrap">
						<div class="masking-badge">
						    <a class="masking-badge__button" href="/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
						        <i class="masking-badge__icon" aria-hidden="true"></i>
						        <p>가려진(*)<br />정보보기</p>
						    </a>
						</div>
					</div>
				</c:if>
                <div class="c-form-group" role="group" aria-labelledby="formGroupA">
				    <h3 class="c-form__title--type2" id="formGroupA">내 정보</h3>
					<div class="c-box c-box--type1 c-box--round">
					  <div class="c-form-row c-col2">
					    <div class="c-form-field">
					      <div class="c-form__select u-mt--0">
					        <select class="c-select" name="ncn" id="ncn">
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
					      <label class="c-form__label" for="ncn">휴대폰 번호</label>
					    </div>
					  </div>
					  <div class="c-form-field">
					    <div class="c-form__input u-mt--0 has-value">
					      <input class="c-input" id="inpHP" type="text" readonly value="${mcpUserCntrMngDto.rateNm}">
					      <label class="c-form__label" for="inpHP">요금제</label>
					    </div>
					  </div>
					  <button class="c-button c-button-round--md c-button--primary u-width--105 u-ml--24"  onclick="btn_search();">조회</button>
					  </div>
					</div>
	          </div>
	        </div>
	        <!-- case1-1 : 결합불가인 경우-->
	        <c:choose>
	          <c:when test="${!empty moscDataSharingResDto.sharingList}">
	            <h3 class="c-heading c-heading--fs16 u-mt--48 u-mb--16 sharingYn">결합 중인 데이터쉐어링 회선</h3>
	             <div class="c-table sharingYn">
	               <table>
	                 <caption>휴대폰 번호, 요금제를 포함한 결합 중인 데이터쉐어링 회선 정보</caption>
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
	                 <tbody class="c-table__left sharingView">
	                   <c:forEach items="${moscDataSharingResDto.sharingList}" var="sharingListDto">
	                        <tr>
	                         <td class="u-ta-center">${sharingListDto.svcNo}</td>
	                         <td class="u-ta-center">${fn:substring(sharingListDto.efctStDt,0,4)}.${fn:substring(sharingListDto.efctStDt,4,6)}.${fn:substring(sharingListDto.efctStDt,6,8)}</td>
	                       </tr>
	                   </c:forEach>
	                 </tbody>
	               </table>
	             </div>
	             <div class="c-text-box c-text-box--blue c-text-box--lg u-mt--24 sharingYn">
	               <p class="c-text-box__text">이미 결합되어 있는
	                 <br>
	                 <b class="u-co-blue">회선이 있습니다.</b>
	               </p>
	               <img class="c-text-box__image" src="/resources/images/portal/content/img_possible.png" alt="" aria-hidden="true">
	             </div><!-- //-->
	          </c:when>
	          <c:when test="${'Y' eq changeYn}">
	          <div class="c-text-box c-text-box--red c-text-box--lg u-mt--24 sharingStatusYn">
	           <p class="c-text-box__text">납부방법을 신용카드/은행계좌
	             <br>
	             <b class="u-co-red"> 자동이체로 변경 후 <br>쉐어링 결합 신청이 가능합니다.</b>
	           </p>
	           <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
	          </div><!-- //-->
	          </c:when>
	          <c:when test="${'Y' eq subStatusYn}">
	             <div class="c-text-box c-text-box--red c-text-box--lg u-mt--24 sharingStatusYn">
	               <p class="c-text-box__text">사용중인 회선만
	                 <br>
	                 <b class="u-co-red">데이터쉐어링 결합 가능합니다.</b>
	               </p>
	               <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
	             </div><!-- //-->
	          </c:when>
	          <c:when test="${'Y' eq customerType}">
	             <div class="c-text-box c-text-box--red c-text-box--lg u-mt--24 sharingStatusYn">
	               <p class="c-text-box__text">개인고객만
	                 <br>
	                 <b class="u-co-red">가입이 가능합니다.</b>
	               </p>
	               <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
	             </div><!-- //-->
	          </c:when>
	           <c:when test="${'Y' ne socChkYn}">
	            <div class="c-text-box c-text-box--red c-text-box--lg u-mt--24">
	               <p class="c-text-box__text">데이터쉐어링 결합 사용이
	                 <br>
	                 <b class="u-co-red">불가능한 요금제입니다.</b>
	               </p>
	               <img class="c-text-box__image" src="/resources/images/portal/content/img_impossible.png" alt="" aria-hidden="true">
	             </div><!-- //-->
	          </c:when>

	         <c:otherwise>
	             <div class="c-text-box c-text-box--blue c-text-box--lg u-mt--24 sharingChkYn">
	               <p class="c-text-box__text">데이터쉐어링
	                 <br>
	                 <b class="u-co-blue">결합이 가능한 회선입니다.</b>
	               </p>
	               <img class="c-text-box__image" src="/resources/images/portal/content/img_possible.png" alt="" aria-hidden="true">
	             </div>
	             <div class="c-form-wrap u-mt--56">
	                <div class="c-form-group" role="group" aira-labelledby="radOpenType">
	                    <h3 class="c-form__title--type2 u-mt--0" id="radOpenType">개통 유형 선택</h3>
	                    <!-- 개통 유형에 제약에 따라서 input disabled 처리 -->
	                    <div class="c-checktype-row c-col2 u-mt--0">
							<c:choose>
								<c:when test="${'NA' eq  cstmrType and isMacTime}">
									<input class="c-radio c-radio--button" id="radOpenType1" type="radio" name="radOpenType" value="5">
									<label class="c-label u-ta-center" for="radOpenType1">셀프개통</label>
								</c:when>
								<c:otherwise>
									<input class="c-radio c-radio--button" id="radOpenType1" type="radio" name="radOpenType" value="5" disabled="disabled" >
									<label class="c-label u-ta-center" for="radOpenType1">셀프개통</label>
								</c:otherwise>
							</c:choose>
	                        <input class="c-radio c-radio--button" id="radOpenType2" type="radio" name="radOpenType" value="0">
	                        <label class="c-label u-ta-center" for="radOpenType2">상담사 개통 신청</label>
	                    </div>
	                </div>
	            </div>
	            <div class="c-box c-box--type1 c-box--bullet">
	                <ul class="c-text-list c-bullet c-bullet--dot">
	                    <li class="c-text-list__item">셀프개통 가능시간 : 08:00 ~ 21:50</li>
	                    <li class="c-text-list__item">미성년자(19세 미만 내국인) 및 외국인은 상담사 개통 신청만 가능합니다.(셀프개통 불가)</li>
	                    <li class="c-text-list__item">미성년자의 경우 법정대리인의 동의(인증)이 필요합니다.</li>
	                    <li class="c-text-list__item">상담사 개통으로 신청하실 경우 순차적으로 AI상담사 또는 개통 상담사가 전화를 드려 본인확인을 진행하며, 3회 이상 통화가 되지 않을 경우에는 신청정보가 자동으로 삭제됩니다.</li>
	                </ul>
	            </div>
	             <div class="c-button-wrap u-mt--56 sharingChkYn">
	               <a class="c-button c-button--lg c-button--primary u-width--460" href="javascript:void(0);" onclick="btn_sharingReg();">데이터쉐어링 가입하기</a>
	             </div>
	         </c:otherwise>
	         </c:choose>
	         <input type="hidden" name="menuType" id="menuType" value="${menuType}"/>
            <div class="data-sharing-notice">



            </div>
        </div>
    </div>


    </t:putAttribute>
</t:insertDefinition>
