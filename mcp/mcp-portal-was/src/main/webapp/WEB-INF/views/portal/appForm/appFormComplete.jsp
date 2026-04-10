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

    <t:putAttribute name="titleAttr">
        <c:choose>
            <c:when test="${AppformReq.usimKindsCd eq '09'}">
                eSIM 개통신청
            </c:when>
            <c:when test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_PHONE}">
                    휴대폰 요금제 가입하기
            </c:when>
            <c:when test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM && 'Y' eq AppformReq.sesplsYn}">
                    자급제 요금제 가입하기
            </c:when>
            <c:when test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM }">
                    휴대폰 요금제 가입하기
            </c:when>
            <c:otherwise>
             kt M모바일 공식 다이렉트몰
            </c:otherwise>
          </c:choose>
    </t:putAttribute>

    <t:putAttribute name="googleTagScript">
        <!-- Event snippet for 가입신청완료_PC conversion page -->
        <script>
            gtag('event', 'conversion', {'send_to': 'AW-862149999/xGdrCKrkxLYZEO-6jZsD'});
        </script>
    </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/appForm/appFormComplete.js?version=25.05.23"></script>
        <!-- 카카오 광고 분석  -->
        <script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
        <script type="text/javascript">
              kakaoPixel('5981604067138488988').pageView();
              kakaoPixel('5981604067138488988').completeRegistration();
        </script>
        <!-- 카카오 광고 분석 end-->
    </t:putAttribute>

    <t:putAttribute name="bottomScript">
        <!-- naver DA script  통계  전환페이지 설정 -->
        <script type="text/javascript">
            if(window.wcs){
                // (5) 결제완료 전환 이벤트 전송
                var _conv = {}; // 이벤트 정보를 담을 object 생성
                _conv.type = "purchase";  // 전환이벤트의 종류 설정
                _conv.value = "10";  // 결제에 여러개 상품을 구매했다면 여기에 금액 합계
                wcs.trans(_conv); // 이벤트 정보를 담은 object를 서버에 전송
            }
        </script>
        <!-- naver DA script  통계  전환페이지 end -->
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="requestKey" name="requestKey" value="${AppformReq.requestKey}" >
        <input type="hidden" id="modelSalePolicyCode" name="modelSalePolicyCode" value="${AppformReq.modelSalePolicyCode}" >
        <input type="hidden" id="enggMnthCnt" name="enggMnthCnt" value="${AppformReq.enggMnthCnt}" ><!--유심에서 약정????  -->
        <input type="hidden" id="rprsPrdtId" name="rprsPrdtId" value="${AppformReq.rprsPrdtId}">
        <input type="hidden" id="cntpntShopId" name="cntpntShopId" value="${AppformReq.cntpntShopId}" >
        <input type="hidden" id="operType" name="operType" value="${AppformReq.operType}" />
        <input type="hidden" id="socCode" name="socCode" value="${AppformReq.socCode}" >
        <input type="hidden" id="onOffType" name="onOffType" value="${AppformReq.onOffType}" />
        <input type="hidden" id="prdtSctnCd" name="prdtSctnCd" value="" />
        <input type="hidden" id="operTypeNm" name="operTypeNm" value="${AppformReq.operTypeNm}" />
        <input type="hidden" id="cstmrName" name="cstmrName" value="${AppformReq.cstmrName}" />
        <input type="hidden" id="joinPriceIsPay" name="joinPriceIsPay" >
        <input type="hidden" id="usimPriceIsPay" name="usimPriceIsPay" >
        <input type="hidden" id="joinPrice" name="joinPrice" >
        <input type="hidden" id="usimPrice" name="usimPrice" >


        <div class="ly-content" id="main-content">
            <c:choose>



                <c:when test="${AppformReq.usimKindsCd eq '09' && AppformReq.onOffType eq '5'}">
                    <div class="ly-page--title">
                        <h2 class="c-page--title">셀프개통</h2>
                    </div>
                    <div class="c-row c-row--xlg c-row--divider-top"></div>
                    <div class="c-row">
                        <div class="complete u-mt--80 u-ta-center">
                            <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--26">
                                <b>셀프개통이 완료되었습니다.</b>
                            </h3>
                            <span class="c-sticker--read">필독!</span>

                            <c:choose>
                                <c:when test="${empty AppformReq.prntsContractNo || AppformReq.prntsContractNo eq ''}">
                                    <!-- eSIM -->
                                    <p class="c-text u-co-gray-9 u-fs-20 u-mt--11">
                                        * 정상 사용을 위해서는 eSIM을 반드시 발급(프로파일 다운로드) 받으셔야 합니다.<br />
                                        <span class="c-text--redline">“eSIM을 사용할 휴대폰으로 아래 QR코드를 촬영”</span> 해 주세요.
                                    </p>
                                    <p class="c-text u-co-gray u-mt--10">또는 휴대폰 설정 메뉴에서도 발급 가능하며, 발급방법은 ‘eSIM 발급방법’을 참고 해 주십시오.</p>
                                    <img class="esim-qrcode" src="/resources/images/common/eSIM_QR_cat.png" alt="eSIM 이용을 위한 QR코드 이미지">
                                    <div class="c-button-wrap u-mt--58">
                                        <a class="c-button c-button--lg c-button--gray u-width--220" href="/main.do">메인으로</a>
                                        <a class="c-button c-button--lg c-button--primary u-width--220" href="/appForm/eSimInfom.do#step3">eSIM 발급방법</a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <!-- eSIM  watch-->
                                    <p class="c-text u-co-gray-9 u-fs-20 u-mt--11">
                                        * 애플 워치는 별도 등록 없이 사용 가능하며<br />
                                        <span class="c-text--redline">“갤럭시 워치는 워치 설정에서 eSIM 발급 후” </span> 사용 가능합니다.
                                    </p>
                                    <p class="c-text u-co-gray u-mt--10">갤럭시 워치는 ‘eSIM 발급방법’을 참고 해 주십시오.</p>
                                    <div class="c-button-wrap u-mt--58">
                                        <a class="c-button c-button--lg c-button--gray u-width--220" href="/main.do">메인으로</a>
                                        <a class="c-button c-button--lg c-button--primary u-width--220" href="/appForm/eSimWatchInfo.do#step3">eSIM 발급방법</a>
                                    </div>
                                </c:otherwise>
                            </c:choose>




                        </div>
                    </div>

                 </c:when>
                 <c:when test="${AppformReq.usimKindsCd eq '09' }">
                    <div class="ly-page--title">
                        <c:choose>
                            <c:when test="${empty AppformReq.prntsContractNo || AppformReq.prntsContractNo eq ''}">
                                <h2 class="c-page--title">eSIM 가입하기</h2>
                            </c:when>
                            <c:otherwise><h2 class="c-page--title">워치 가입하기</h2></c:otherwise>
                        </c:choose>
                    </div>
                    <div class="c-row c-row--xlg c-row--divider-top"></div>
                    <div class="c-row c-row--md">
                        <div class="complete u-mt--80 u-ta-center">
                            <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
                            <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                            <b>가입신청</b>이 정상적으로
                            <br>
                            <b>완료</b>되었습니다
                            </h3>
                            <p class="c-text c-text--fs20 u-co-gray u-mt--16">상담사가 신청서를 확인하는 중입니다.내용 확인 후 연락드리겠습니다!</p>
                            <div class="c-button-wrap u-mt--56">
                                <a class="c-button c-button--lg c-button--gray u-width--220" href="/main.do">메인으로</a>
                                <a class="c-button c-button--lg c-button--primary u-width--220" href="/event/eventBoardList.do">이벤트 보러가기</a>
                            </div>
                        </div>
                    </div>
                 </c:when>
                <c:when test="${AppformReq.onOffType eq '5'}">
                    <div class="ly-page--title">
                        <h2 class="c-page--title">셀프개통</h2>
                    </div>
                    <div class="c-row c-row--xlg c-row--divider-top"></div>
                    <div class="c-row c-row--md">
                        <div class="complete u-mt--80 u-ta-center">
                            <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
                            <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                            <b>셀프개통</b>이 정상적으로
                            <br>
                            <b>완료</b>되었습니다
                            </h3>
                            <p class="c-text c-text--fs20 u-co-gray u-mt--16">kt M모바일과 함께해 주셔서 감사합니다.</p>
                            <div class="c-button-wrap u-mt--56">
                                <a class="c-button c-button--lg c-button--gray u-width--220" href="/main.do">메인으로</a>
                                <a class="c-button c-button--lg c-button--primary u-width--220" href="/mmobile/ktmMobileUsimGuid.do">유심 장착방법</a>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${AppformReq.onOffType ne '5' && AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM && 'Y' eq AppformReq.sesplsYn}">
                    <div class="ly-page--title">
                        <h2 class="c-page--title">자급제 요금제 가입하기</h2>
                    </div>
                    <div class="c-row c-row--xlg c-row--divider-top"></div>
                    <div class="c-row c-row--md">
                        <div class="complete u-mt--80 u-ta-center">
                            <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
                            <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                            <b>주문</b>이 정상적으로
                            <br>
                            <b>완료</b>되었습니다
                            </h3>
                            <p class="c-text c-text--fs20 u-co-gray u-mt--16">kt M모바일과 함께해 주셔서 감사합니다.</p>
                            <div class="c-button-wrap u-mt--56">
                                <a class="c-button c-button--lg c-button--gray u-width--220" href="/main.do">메인으로</a>
                                <a class="c-button c-button--lg c-button--primary u-width--220" href="/event/eventBoardList.do">이벤트 보러가기</a>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${AppformReq.onOffType ne '5' && AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM}">
                    <div class="ly-page--title">
                        <h2 class="c-page--title">가입신청(상담사 연결)</h2>
                    </div>
                    <div class="c-row c-row--xlg c-row--divider-top"></div>
                    <div class="c-row c-row--md">
                        <div class="complete u-mt--80 u-ta-center">
                            <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
                            <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                            <b>가입신청</b>이 정상적으로
                            <br>
                            <b>완료</b>되었습니다
                            </h3>
                            <p class="c-text c-text--fs20 u-co-gray u-mt--16">상담사가 신청서를 확인하는 중입니다.내용 확인 후 연락드리겠습니다!</p>
                            <div class="c-button-wrap u-mt--56">
                                <a class="c-button c-button--lg c-button--gray u-width--220" href="/main.do">메인으로</a>
                                <a class="c-button c-button--lg c-button--primary u-width--220" href="/event/eventBoardList.do">이벤트 보러가기</a>
                            </div>
                        </div>
                    </div>
                 </c:when>
                 <c:otherwise>
                    <div class="ly-page--title">
                        <h2 class="c-page--title">휴대폰 요금제 가입하기</h2>
                    </div>
                    <div class="c-row c-row--xlg c-row--divider-top"></div>
                    <div class="c-row c-row--md">
                        <div class="complete u-mt--80 u-ta-center">
                            <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
                            <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                            <b>주문</b>이 정상적으로
                            <br>
                            <b>완료</b>되었습니다
                            </h3>
                            <p class="c-text c-text--fs20 u-co-gray u-mt--16">상담사가 신청서를 확인하는 중입니다.내용 확인 후 연락드리겠습니다!</p>
                            <div class="c-button-wrap u-mt--56">
                                <a class="c-button c-button--lg c-button--gray u-width--220" href="/main.do">메인으로</a>
                                <a class="c-button c-button--lg c-button--primary u-width--220" href="/myShareDataCntrInfo.do">함께쓰기</a>
                            </div>
                        </div>
                    </div>

                 </c:otherwise>
            </c:choose>


      </div>






    </t:putAttribute>

</t:insertDefinition>
