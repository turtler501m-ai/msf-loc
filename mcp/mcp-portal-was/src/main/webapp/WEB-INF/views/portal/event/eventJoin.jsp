<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>



<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/event/eventJoin.js?version=24-01-26"></script>
        <script type="text/javascript" src="/resources/js/portal/event/wordCheck.js?version=24-01-26"></script>
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript" src="/resources/js/sns.js"></script>
    </t:putAttribute>



    <t:putAttribute name="contentAttr">
        <div class="ly-loading" id="subbody_loading">
            <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
        </div>
        <div class="ly-content" id="main-content">
          <input type="hidden" id="certify" value="N" />
          <input type="hidden" id="certifyYn" value="N" />
          <input type="hidden" id="userId" value="${userId}"/>
          <input type="hidden" id="niceName" value=""/>
          <input type="hidden" id="niceMobileNo" value=""/>
          <input type="hidden" id="niceReqSeq" value=""/>
          <input type="hidden" id="niceResSeq" value=""/>
          <input type="hidden" id="niceUseTelCode" value=""/>
          <input type="hidden" id="niceDupInfo" value=""/>
          <input type="hidden" id="eventFlag" value=""/>
          <input type="hidden" id="successFlag" value="${successFlag}"/>
          <input type="hidden" id="reqKey" value=""/>
          <input type="hidden" id="proMoNum" value="${proMoNum}"/>
          <input type="hidden" id="proMoYn" value="${proMoYn}"/>
          <c:choose>
           <c:when test="${nmcp:hasLoginUserSessionBean()}">
            <input type="hidden" id="userlogin" value="Y"/>
          </c:when>
          <c:otherwise>
            <input type="hidden" id="userlogin" value="N"/>
          </c:otherwise>
          </c:choose>

          <div class="ly-page--title">
            <h2 class="c-page--title">댓글 이벤트</h2>
          </div>
          <div class="c-row c-row--lg">
            <div class="swiper-banner" id="swiperReviewBanner">
              <div class="swiper-container">
                <div class="swiper-wrapper" id="swiperArea">

                </div>
              </div>
              <button class="swiper-banner-button--next swiper-button-next" type="button"></button>
              <button class="swiper-banner-button--prev swiper-button-prev" type="button"></button>
            </div>
            <div class="c-form-wrap u-mt--64">
              <div class="c-form-row c-col2 u-al-center">
                <div class="c-form c-form--type2 u-width--460">
                  <label class="c-label c-hidden" for="selEventCd">댓글 이벤트 검색구분</label>
                  <select class="c-select" id="selEventCd" name="selEventCd">
                    <c:forEach var="nmcpMainCdDtlDtoList" items="${nmcpMainCdDtlDtoList}">
                        <option value="${nmcpMainCdDtlDtoList.dtlCd}">${nmcpMainCdDtlDtoList.dtlCdNm}(${nmcpMainCdDtlDtoList.eventCdCtn})</option>
                    </c:forEach>
                  </select>
                  <!-- <button class="c-button c-button-round--md c-button--primary" id="selectEventReview">조회</button> -->
                </div>
                <c:if test="${nmcp:hasLoginUserSessionBean() && not empty cntrList}">
                  <div class="c-form-field u-width--300">
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
                    <div class="c-form__select u-mt--0">
                      <select class="c-select" id="selectA" name="phone">
                        <c:forEach var="list" items="${cntrList}">
                           <c:choose>
                                <c:when test="${maskingSession eq 'Y'}">
                                    <c:if test="${list.subStatus eq 'A'}">
                                      <c:choose>
                                        <c:when test="${fn:length(list.mkMobileNo) > 10}">
                                          <option value="${list.cntrMobileNo}" data-maskedTelNo= "${list.mkMobileNo}">
                                            ${fn:substring(list.cntrMobileNo,0,3)}-${fn:substring(list.cntrMobileNo,3,7)}-${fn:substring(list.cntrMobileNo,7,11)}
                                          </option>
                                        </c:when>
                                        <c:otherwise>
                                          <option value="${list.cntrMobileNo}" data-maskedTelNo= "${list.mkMobileNo}">
                                            ${fn:substring(list.cntrMobileNo,0,3)}-${fn:substring(list.cntrMobileNo,3,6)}-${fn:substring(list.cntrMobileNo,6,10)}
                                          </option>
                                        </c:otherwise>
                                      </c:choose>
                                   </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${list.subStatus eq 'A'}">
                                      <c:choose>
                                        <c:when test="${fn:length(list.mkMobileNo) > 10}">
                                          <option value="${list.cntrMobileNo}" data-maskedTelNo= "${list.mkMobileNo}">
                                            ${fn:substring(list.mkMobileNo,0,3)}-${fn:substring(list.mkMobileNo,3,7)}-${fn:substring(list.mkMobileNo,7,11)}
                                          </option>
                                        </c:when>
                                        <c:otherwise>
                                          <option value="${list.cntrMobileNo}" data-maskedTelNo= "${list.mkMobileNo}">
                                            ${fn:substring(list.mkMobileNo,0,3)}-${fn:substring(list.mkMobileNo,3,6)}-${fn:substring(list.mkMobileNo,6,10)}
                                          </option>
                                        </c:otherwise>
                                      </c:choose>
                                   </c:if>
                                </c:otherwise>
                           </c:choose>
                        </c:forEach>
                      </select>
                      <label class="c-form__label" for="selectA">휴대폰 번호</label>
                    </div>
                  </div>
                  <input type="hidden" id="isLogin" value="Y" />
                </c:if>
                <c:if test="${nmcp:hasLoginUserSessionBean() && empty cntrList}">
                  <input type="hidden" id="selectA" value="${mobileNo}" />
                  <input type="hidden" id="isLogin" value="Y" />
                </c:if>

              </div>
              <div id="formYn">
                <div class="c-form u-mt--48" >
                  <label class="c-label" for="textareaA">이벤트 참여하기</label>
                  <div class="c-textarea-wrap">
                    <textarea class="c-textarea" id="textareaA" name="reviewSbst" placeholder="${eventHolder.docContent}" style="ime-mode: active;"></textarea>
                    <div class="c-textarea-wrap__sub">
                      <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                      <span><span id="txtCnt">0</span>/800</span>
                    </div>
                  </div>
                </div>

                <div class="c-accordion c-accordion--type1 acc-agree" data-ui-accordion>
                  <span class="c-form__title--type2">약관동의</span>
                  <div class="c-accordion__box">
                    <div class="c-accordion__item">
                      <div class="c-accordion__head">
                        <button class="runtime-data-insert c-accordion__button" id="acc_agree_headerA1" type="button" aria-expanded="false" aria-controls="acc_agreeA1">
                          <span class="c-hidden">이벤트 참여를 위한 이용약관, 개인정보 수집/이용 및 선택 동의 항목에 모두 동의합니다.  ※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span>
                        </button>
                        <div class="c-accordion__check">
                          <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                          <label class="c-label" for="btnStplAllCheck">이벤트 참여를 위한 이용약관, 개인정보 수집/이용 및 선택 동의 항목에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span></label>
                        </div>
                      </div>
                      <div class="c-accordion__panel expand" id="acc_agreeA1" aria-labelledby="acc_agree_headerA1" aria-hidden="true">
                        <div class="c-accordion__inside">
                          <div class="c-accordion__sub-check">
                            <button class="c-button c-button--xsm" data-dialog-trigger="#priAgreeModal">
                              <span class="c-hidden">본인확인을 위한 개인정보 수집 및 이용 동의(필수) 상세 팝업 열기</span>
                              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                            <input class="c-checkbox c-checkbox--type2" id="priAgree" type="checkbox" name="terms" onclick="setChkbox(this)">
                            <label class="c-label" for="priAgree">본인확인을 위한 개인정보 수집 및 이용 동의<span class="u-co-gray">(필수)</span></label>
                          </div>
                          <div class="c-accordion__sub-check">
                            <button class="c-button c-button--xsm" data-dialog-trigger="#priEventAgreeModal">
                              <span class="c-hidden">이벤트 참여를 위한 개인정보 수집 및 이용 동의(필수) 상세 팝업 열기</span>
                              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                            <input class="c-checkbox c-checkbox--type2" id="priEventAgree" type="checkbox" name="terms" onclick="setChkbox(this)">
                            <label class="c-label" for="priEventAgree">이벤트 참여를 위한 개인정보 수집 및 이용 동의<span class="u-co-gray">(필수)</span></label>
                          </div>
                          <div class="c-accordion__sub-check">
                            <button class="c-button c-button--xsm" data-dialog-trigger="#priMarketingAgreeModal">
                              <span class="c-hidden">마케팅활용을 위한 개인정보 수집 및 이용 동의(선택) 상세 팝업 열기</span>
                              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                            <input class="c-checkbox c-checkbox--type2" id="priMarketingAgree" type="checkbox" name="terms" onclick="setChkbox(this)">
                            <label class="c-label" for="priMarketingAgree">마케팅활용을 위한 개인정보 수집 및 이용 동의<span class="u-co-gray">(선택)</span></label>
                          </div>
                          <div class="c-accordion__sub-check">
                            <button class="c-button c-button--xsm" data-dialog-trigger="#priAdAgreeModal">
                              <span class="c-hidden">광고성 정보 수신 동의(선택) 상세 팝업 열기</span>
                              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                            <input class="c-checkbox c-checkbox--type2" id="priAdAgree" type="checkbox" name="terms" onclick="setChkbox(this)">
                            <label class="c-label" for="priAdAgree">광고성 정보 수신 동의<span class="u-co-gray">(선택)</span></label>
                            <p class="u-fs-15 u-co-gray u-mt--m4 u-pl--26">※ 본 선택 동의는 거부 하실수 있으나, 미동의시 이벤트 응모에 참여 하실수 없습니다.</p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="c-button-wrap u-mt--48">
                  <button id="validationChk" class="c-button c-button--lg c-button--primary u-width--460" data-dialog-trigger="#userPromotionJoinModal">댓글 작성하고 선물 받기</button>
                </div>
              </div>
            </div><!-- 데이터 있는 경우-->

            <div class="comment-wrap">
                 <div id="commentCnt"></div>
              <div class="c-checktype-row">
                  <input class="c-checkbox" id="chkMyReview" type="checkbox" name="chkA">
                  <label class="c-label" for="chkMyReview">내 게시글 보기</label>
              </div>
            </div>
            <div class="c-accordion review" id="accordionReview" data-ui-accordion>
              <ul class="c-accordion__box event-join" id="reviewBoard">

              </ul>
            </div>
            <div class="board_list_table_paging" id="paging">
              <nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="requestReview"/>
            </div>
          </div>
          <div class="event-sharing-wrap">
              <div class="event-sharing">
                <button id="btnCompareView" data-dialog-trigger="#modalShareAlert">
                  <i class="c-icon c-icon--share" aria-hidden="true"></i>이벤트 공유하기
                </button>
              </div>
          </div>
        </div>


        <!-- 본인확인을 위한 개인정보 수집 및 이용 동의 팝업 -->
        <div class="c-modal c-modal--xlg" id="priAgreeModal" role="dialog" aria-labelledby="priAgreeModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="priAgreeModalTitle">본인확인을 위한 개인정보 수집 및 이용 동의 (필수)</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="one-source">
                            <p class="c-text c-text--type3">
                                <b>만 14세 이상이며 개인정보 수집 및 이용에 동의합니다.</b><br/>
                                본인은 14세 이상이고 동의 내용을 숙지하였으며, 이에 따라 본인의 개인정보를 수집 및 이용하는 것에 동의합니다.
                            </p>
                            <div class="c-table u-mt--16">
                                <table>
                                    <caption>수집 및 이용 목적, 수집항목, 보유 기간 항목이 포함된 표</caption>
                                    <colgroup>
                                        <col style="width: 340px">
                                        <col style="width: 300px">
                                        <col style="width: 300px">
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th scope="col">수집 및 이용 목적</th>
                                            <th scope="col">수집 항목</th>
                                            <th scope="col">보유 기간</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>본인 확인</td>
                                            <td>성명, 휴대폰번호, 통신사, 생년월일, CI, DI</td>
                                            <td><b>이벤트 종료일로부터 <span class="u-co-red">12개월까지</span></b></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--fyr u-fs-14 u-mt--16">
                              <li class="c-text-list__item">이 내용은 이벤트 참여를 위한 개인정보 수집 및 이용에 대한 동의입니다. 동의를 거부할 권리가 있으나, 동의하지 않을 경우 이벤트에 참여하실 수 없습니다.</li>
                              <li class="c-text-list__item">이 이벤트는 법정 대리인의 동의가 필요한 만 14세 미만 고객님은 참여하실 수 없습니다.</li>
                            </ul>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" onclick="agreeClose(1)" oncl data-dialog-close>동의 후 닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 이벤트 참여를 위한 개인정보 수집 및 이용 동의 팝업 -->
        <div class="c-modal c-modal--xlg" id="priEventAgreeModal" role="dialog" aria-labelledby="priEventAgreeModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="priEventAgreeModalTitle">이벤트 참여를 위한 개인정보 수집 및 이용 동의 (필수)</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="one-source">
                            <p class="c-text c-text--type3">
                                <b>“댓글 이벤트”와 관련하여 본인의 개인정보를 KT M모바일 주식회사가 수집 및 이용합니다. </b>
                            </p>
                            <div class="c-table u-mt--16">
                                <table>
                                    <caption>수집 및 이용 목적, 수집항목, 보유 기간 항목이 포함된 표</caption>
                                    <colgroup>
                                        <col style="width: 340px">
                                        <col style="width: 300px">
                                        <col style="width: 300px">
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th scope="col">수집 및 이용 목적</th>
                                            <th scope="col">수집 항목</th>
                                            <th scope="col">보유 기간</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>이벤트 응모, 본인 게시글 확인,<br/>참여고객 분석, 참여고객 경품 증정</td>
                                            <td>성명, 휴대폰번호, 통신사, 생년월일, CI, DI</td>
                                            <td><b>이벤트 종료일로부터 <span class="u-co-red">12개월까지</span></b></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--fyr u-fs-14 u-mt--16">
                              <li class="c-text-list__item">이 내용은 이벤트 참여를 위한 개인정보 수집 및 이용에 대한 동의입니다. 동의를 거부할 권리가 있으나, 동의하지 않을 경우 이벤트에 참여하실 수 없습니다.</li>
                              <li class="c-text-list__item">법령에 따른 개인정보의 수집 및 이용, 계약의 이행 및 편의 증진을 위한 개인정보 처리 위탁 및 개인정보 처리와 관련된 일반 사항은 서비스의 개인정보 처리 방침에 따릅니다.</li>
                            </ul>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" onclick="agreeClose(2)" oncl data-dialog-close>동의 후 닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 마케팅활용을 위한 개인정보 수집 및 이용 동의 팝업 -->
        <div class="c-modal c-modal--xlg" id="priMarketingAgreeModal" role="dialog" aria-labelledby="priMarketingAgreeModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="priMarketingAgreeModalTitle">마케팅활용을 위한 개인정보 수집 및 이용 동의 (선택)</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="one-source">
                            <p class="c-text c-text--type3">
                                <b>“댓글 이벤트”와 관련하여 본인의 개인정보를 KT M모바일 주식회사가 수집 및 이용합니다. </b><br/>
                                이벤트 참여에 필수적으로 제공되어야 하는 정보가 아니므로, 동의를 거부하시더라도 이벤트 참여는 가능합니다. 다만 마케팅/광고 활용 목적의 개인정보 수집·이용에 대하여 동의하지 않으실 경우, 이벤트 관련 제품 정보 알림, 혜택 안내 및 혜택 제공을 받으실 수 없습니다.
                            </p>
                            <div class="c-table u-mt--16">
                                <table>
                                    <caption>수집 및 이용 목적, 수집항목, 보유 기간 항목이 포함된 표</caption>
                                    <colgroup>
                                        <col style="width: 340px">
                                        <col style="width: 300px">
                                        <col style="width: 300px">
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th scope="col">수집 및 이용 목적</th>
                                            <th scope="col">수집 항목</th>
                                            <th scope="col">보유 기간</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>상품ㆍ서비스, 사은ㆍ판촉행사, 이벤트, 프로모션 등의 마케팅, 광고성 정보 전달, 인구 통계학적 특성과 이용자의 관심 및 성향의 추정을 통한 맞춤형 광고에의 활용</td>
                                            <td>성명, 휴대폰번호, 통신사, 생년월일, CI, DI</td>
                                            <td><b>이벤트 종료일로부터 <span class="u-co-red">12개월까지</span></b></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" onclick="agreeClose(3)" oncl data-dialog-close>동의 후 닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 광고성 정보 수신 동의 팝업 -->
        <div class="c-modal c-modal--md" id="priAdAgreeModal" role="dialog" aria-labelledby="priAdAgreeModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="priAdAgreeModalTitle">광고성 정보 수신 동의 (선택)</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="one-source">
                            <p class="c-text c-text--type3">
                                <b>“댓글 이벤트”와 관련하여 본인의 개인정보를 KT M모바일 주식회사가 다음과 같이 본인에게 전자적 전송 매체(SMS,LMS)를 통해, 광고 및 마케팅목적으로 전송하는 것에 동의합니다.</b>
                            </p>
                            <ul class="c-text-list c-bullet c-bullet--fyr u-fs-14 u-mt--16">
                              <li class="c-text-list__item">이 내용은 이벤트 응모를 위한 마케팅 수신에 대한 동의입니다. 동의를 거부하실 수 있으며 해당 동의 거부 시 마케팅 관련 광고성 정보를 받아보실 수 없습니다.</li>
                            </ul>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" onclick="agreeClose(4)" oncl data-dialog-close>동의 후 닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="c-modal c-modal--sm" id="modalShareAlert" role="dialog" tabindex="-1" aria-labelledby="#modalShareAlertTitle">
           <div class="c-modal__dialog" role="document">
             <div class="c-modal__content">
               <div class="c-modal__body u-ta-center">
                 <h3 class="c-heading c-heading--fs22 u-fw--bold" id="modalShareAlertTitle">공유하기</h3>
                 <div class="c-button-wrap u-mt--32">
                   <a class="c-button" href="javascript:void(0);">
                     <span class="c-hidden">카카오</span>
                     <i class="c-icon c-icon--kakao" aria-hidden="true" onclick="kakaoShare(); return false;"></i>
                   </a>
                   <a class="c-button u-ml--32" href="javascript:void(0);">
                     <span class="c-hidden">URL 복사하기</span>
                     <i class="c-icon c-icon--link" aria-hidden="true" onclick="clipNew(); return false;"></i>
                   </a>
                 </div>
                 <div class="c-button-wrap u-mt--48">
                   <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                 </div>
               </div>
             </div>
           </div>
         </div>

         <!-- 이벤트 참여 팝업 -->
        <div class="c-modal c-modal--md" id="userPromotionJoinModal" role="dialog" aria-labelledby="userPromotionJoinTitle">
            <div class="c-modal__dialog" role="document">
                  <div class="c-modal__content">
                    <div class="c-modal__header">
                          <h2 class="c-modal__title" id="userPromotionJoinTitle">이벤트 참여</h2>
                              <button class="c-button" data-dialog-close>
                                <i class="c-icon c-icon--close" aria-hidden="true"></i>
                                <span class="c-hidden">팝업닫기</span>
                              </button>
                    </div>
                    <div class="c-modal__body">
                          <div class="u-ta-center">
                            <p class="u-fs-20 u-fw--medium">본 이벤트 참여를 위해서는<br />마케팅활용을 위한 개인정보 수집 및<br />이용 동의, 광고성 정보 수신 동의가 필요합니다.</p>
                          </div>
                    </div>
                    <div class="c-modal__footer">
                          <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--gray u-width--220" data-dialog-close>취소</button>
                            <button href="javascript:void(0)" id="certifyPopBut" class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>휴대폰인증</button>
                          </div>
                    </div>
                  </div>
            </div>
        </div>

    </t:putAttribute>


</t:insertDefinition>