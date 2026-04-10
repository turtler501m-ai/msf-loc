<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">아무나 가족 결합+</t:putAttribute>


    <t:putAttribute name="contentAttr">



        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">아무나 가족 결합+</h2>
            </div>

            <div class="combine-complete" >
                <c:choose>
                    <c:when test="${reqCombine.combTgtTypeCd eq '01'}">
                        <!-- 결합 완료(가족결합 제외) 노출 영역 -->
                        <div class="combine-complete__wrap"  >
                            <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                            <p class="combine-complete__txt">
                                <span class="u-co-mint">결합이 완료 되었습니다.</span><br/>kt M모바일 회선에 무료결합데이터(${reqCombine.mRateAdsvcNm})이 제공되었습니다.
                            </p>
                            <p class="combine-complete__subtxt">
                                ※ 결합 완료 시 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.<br/>
                                요금제 변경 및 해지 시 고객센터를 통해 신청바랍니다.
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <!-- 결합(가족결합) 신청완료 노출 영역 -->
                        <div >
                            <div class="combine-complete__wrap">
                                <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                                <p class="combine-complete__txt">
                                    <span class="u-co-mint">결합 신청이 완료 되었습니다.</span><br/>가족관계검증을 위해 관련 서류를 보내주시기 바랍니다.<br/>서류검증 후 결합처리가 완료됩니다.
                                </p>
                                <p class="combine-complete__subtxt">
                                    ※ 결합 신청 후 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.
                                </p>
                            </div>
                            <div class="combine-checklist u-mt--48">
                                <p class="combine-checklist__tit u-co-red">
                                    <i class="c-icon c-icon c-icon--bang--red" aria-hidden="true"></i>
                                    <span>주의사항</span>
                                </p>
                                <ul class="combine-checklist__item">
                                    <li>해당 페이지에서 결합 신청완료 한 후, 아래 서류를 신청 후 3일 이내에 이메일 (ktm.vit@kt.com) 이나 팩스(070-4275-2483)로 전달 주셔야 합니다.</li>
                                    <li>결합 신청일 기준 5일이내로 서류가 도착하지 않는 경우 해당 결합 신청이 취소되며, 취소 후에 결합을 원하실 경우  재신청 바랍니다.</li>
                                </ul>
                                <p class="combine-checklist__subtit u-co-red">
                                    <span>※가족명의 결합 요청 시</span>
                                </p>
                                <ul class="combine-checklist__item no-bullet">
                                    <li><b>① 주민등록등본, 건강보험증, 가족관계증명서, 혼인관계증명서, 입양관계증명서, 친양자입양관계증명서 중 택 1</b></li>
                                    <li><b>② 결합신청인, 결합대상인의 신분증(주민등록증, 여권, 운전면허증, 공무원증 중 택 1)</b></li>
                                    <li>
                                        ※ 주민번호 뒷 번호가 보이지 않게 마스킹 처리 바랍니다. 미처리 시 해당 자료는 폐기됩니다.<br />
                                        ※ 미성년자의 경우 법정대리인 신분증으로 제출 바랍니다.
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>




                <div class="combine-complete__list">
                    <h3>신청 내역</h3>
                    <hr>
                    <h4>내 정보</h4>
                    <div class="c-table">
                        <table>
                            <caption>전화번호, 무료결합데이터, 요금제 정보를 포함한 결합내역 정보</caption>
                            <colgroup>
                                <col style="width: 10rem">
                                <col>
                                <col style="width: 10rem">
                                <col>
                            </colgroup>
                            <tbody>
                            <tr>
                                <th scope="row">전화번호</th>
                                <td class="_ctn" >${reqCombine.mCtnMaker}</td>
                                <th scope="row">무료결합데이터</th>
                                <td class="_serviceNm">${reqCombine.mRateAdsvcNm}</td>
                            </tr>
                            <tr>
                                <th scope="row">요금제</th>
                                <td colspan="3" class="_rate">${reqCombine.mRateNm}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <h4>결합 회선 정보</h4>
                    <div class="c-table">
                        <table>
                            <caption>전화번호, 무료결합데이터, 요금제 정보를 포함한 결합내역 정보</caption>
                            <colgroup>
                                <col style="width: 10rem">
                                <col>
                            </colgroup>
                            <tbody>
                            <tr>
                                <th scope="row">KT</th>
                                <td id="tdKtInfo">${reqCombine.combSvcNoMaker}</td>
                            </tr>
                            </tbody>
                        </table>
                        <p class="combine-complete__listsubtxt">※ 해당 KT 상품의 상세 내용 확인은 KT 고객센터로 문의 해주시기 바랍니다.</p>
                    </div>
                </div>

                <div class="combine-complete__confirm">
                    <button type="button" onclick="location.href='/content/combineKt.do' ">확인</button>
                </div>

            </div>

        </div>




    </t:putAttribute>




    <t:putAttribute name="popLayerAttr">



        <!-- 결합내역 조회 팝업 -->
        <div class="c-modal c-modal--lg850" id="combinHistory" role="dialog" aria-labelledby="combinHistoryTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <div class="c-modal__title" id="combinHistoryTitle">결합내역 조회</div>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="combin-history__tit">
                            <h3>결합 중인 회선</h3>
                            <button id="btnPrint" type="button" style="display: none">결합상품이용확인서</button>
                        </div>
                        <div class="combin-history__myinfo c-table">
                            <table>
                                <caption>조회회선, 무료결합데이터를 포함한 결합 중인 회선 정보</caption>
                                <colgroup>
                                    <col style="width: 8.3125rem">
                                    <col>
                                    <col style="width: 8.3125rem">
                                    <col>
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th scope="row">조회 회선</th>
                                    <td>
                                        <p class="_ctn" ><!--010-12**-*123--></p>
                                        <p  class="rate-info _rate"><!--데이터 맘껏 15GB+/300분(기프티쇼 5000P)--></p>
                                    </td>
                                    <th scope="row">무료결합데이터</th>
                                    <td class="progress-info _serviceNm" >-</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div id="divCombinHistory" class="combin-history__list c-table" style="display: none">
                            <table>
                                <caption>kt M모바일, KT 인터넷, KT 모바일을 포함한 결합 중인 회선 정보</caption>
                                <colgroup>
                                    <col style="width: 8.3125rem">
                                    <col>
                                    <col style="width: 8.3125rem">
                                    <col>
                                </colgroup>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                        <div id="divCombinHistory2" class="combin-history__progress" style="display: none">
                            <h3 class="">결합 진행 현황<span class="c-form__sub">심사가 완료되면 상단의 결합중인 회선에 결합 내역이 노출됩니다.</span></h3>
                            <div class="combin-history__proginfo c-table">
                                <table>
                                    <caption>결합 대상 회선, 진행현황을 포함한 결합 진행 현황 정보</caption>
                                    <colgroup>
                                        <col style="width: 8.3125rem">
                                        <col>
                                        <col style="width: 8.3125rem">
                                        <col>
                                    </colgroup>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div id="divCombinHistoryNodata" class="combin-nodata " style="display: none">
                            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                            <p>현재 결합 중인 회선이 없습니다.</p>
                        </div>
                        <div class="c-notice-wrap">
                            <h5 class="c-notice__title">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                <span>알려드립니다</span>
                            </h5>
                            <ul class="c-notice__list">
                                <li>결합된 회선의 요금제 변경 및 해지 시 kt M모바일 고객은 kt M모바일 고객센터(114, 1899-5000), KT 고객은 KT 고객센터를 통해 가능합니다.</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- 정보제공 동의 팝업 -->
        <div class="c-modal c-modal--xlg" id="combinInfoAgree" role="dialog" aria-labelledby="combinInfoAgreeTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="combinInfoAgreeTitle">정보제공동의</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="combin-Info-Agree">
                            <h3>정보제공 동의란?</h3>
                            <p>KT 상품을 가입한 고객이 KT 고객센터를 통해 자신의 정보가 kt M모바일에 제공되도록 <span class="u-co-mint">정보제공 동의</span>를 하는 절차입니다.</p>
                            <div class="combin-Info-Agree__wrap">
                                <ul class="process-wrap">
                                    <li class="process-list">
                                        <span class="process-list__step">1</span>
                                        <strong>KT 고객센터 전화하기</strong>
                                        <p>KT 모바일 사용자 : 114번</p>
                                        <p>KT 인터넷 가입자 : 100번</p>
                                    </li>
                                    <li class="process-list">
                                        <span class="process-list__step">2</span>
                                        <strong>상담원 연결</strong>
                                    </li>
                                    <li class="process-list">
                                        <span class="process-list__step">3</span>
                                        <strong class="u-fs-14">kt M모바일과 결합 위해<br/>「MVNO정보제공동의」 요청</strong>
                                    </li>
                                    <li class="process-list">
                                        <span class="process-list__step">4</span>
                                        <strong>본인인증</strong>
                                    </li>
                                    <li class="process-list">
                                        <span class="process-list__step">5</span>
                                        <strong>완료</strong>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 서비스 ID 팝업 -->
        <div class="c-modal c-modal--md" id="serviceInfo" role="dialog" aria-labelledby="serviceInfoTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <div class="c-modal__title" id="serviceInfoTitle">인터넷 서비스 ID</div>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <p class="c-text c-text--type3">
                            인터넷 서비스 ID란  KT 유선 서비스에 가입된 고객에게 부여되며 마이케이티APP 및 KT 고객센터에서 문의 후 입력해 주시기 바랍니다.
                        </p>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap u-mt--0">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>



        <!--결합상품이용계약서 인쇄 영역-->
        <div class="c-modal c-modal--combine-print" id="combinePrint" role="dialog" aria-labelledby="combinePrintTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="combinePrintTitle">결합 상품 이용 확인서</h2>
                        <button class="c-button no-print" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body combine-print">
                        <div class="certi-box">
                            <div class="c-button-wrap c-button-wrap--right print-btn">
                                <button class="c-button c-button--sm c-button--white c-button-round--sm">
                                    <i class="c-icon c-icon--print" aria-hidden="true"></i>
                                    <span>인쇄</span>
                                </button>
                            </div>
                            <div class="c-table u-mt--16">
                                <table>
                                    <caption>고객명, 휴대폰번호/휴대폰모델명을 포함한 고객정보</caption>
                                    <colgroup>
                                        <col style="width: 8.5rem">
                                        <col>
                                        <col style="width: 8.5rem">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th scope="row">고객명</th>
                                        <td class="u-ta-left _userNm"><!-- 홍길동 --></td>
                                        <th scope="row">연락처</th>
                                        <td class="u-ta-left _ctn"><!-- 010-222-3333 --></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">요금제명</th>
                                        <td class="u-ta-left _rate" ><!-- 데이터 맘껏 안심 1GB+/100분--></td>
                                        <th scope="row">무료결합데이터</th>
                                        <td class="u-ta-left _serviceNm" >-</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <h3>계약내역</h3>
                            <div class="combine-info-list c-table">
                                <table>
                                    <caption>구분, 상품명, 안내사항, 요금제, 유료부가서비스, 기본제공 부가서비스, 납부방법, 요금명세서를 포함한 모바일 가입정보</caption>
                                    <colgroup>
                                        <col>
                                        <col >
                                        <col style="width: 7.625rem">
                                        <col>
                                        <col style="width: 6.625rem">
                                    </colgroup>
                                    <thead>
                                    	<tr>
		                                    <th scope="col">통신사</th>
		                                    <th scope="col">상품유형</th>
		                                    <th scope="col">ID/전화번호</th>
		                                    <th scope="col">약정기간</th>
		                                    <th scope="col">만료예정일</th>
                                    	</tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                                <ul class="combine-notice__bullet u-mt--30">
                                    <li>이용 확인서 계약 내용이 본인이 신청한 내용과 불일치 시 고객센터(114)통해 연락 주시기 바랍니다.</li>
                                    <li>개인정보보호를 위해 고객의 정보는 일부 마스킹처리 됩니다.</li>
                                </ul>
                            </div>
                        </div>
                        <div class="combine-checklist u-mt--48">
                            <div class="combine-notice u-mt--0">
                                <h5 class="combine-notice__tit">
                                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                    <span>안내사항</span>
                                </h5>

                                <ul class="combine-notice__list-txt">
                                    <li>본 결합 상품 이용 확인서는 현재 고객님의 결합 현황을 토대로 작성된 것으로 최초 결합 신청 당시의 정보와 상이할 수 있습니다.</li>
                                </ul>
                                <ul class="combine-notice__list">
                                    <li>
                                        <p class="combine-notice__list-tit">[결합 혜택]</p>
                                        <ul class="combine-notice__bullet">
                                            <li>kt M모바일 : 사용하고 있는 요금제에 따라 무료 결합 데이터가 제공되며 제공량은 요금제별로 상이합니다.</li>
                                            <li>KT : 결합 상품의 유형에 따라(홈결합, 우리가족 무선결합) 결합 할인이 상이합니다.</li>
                                        </ul>
                                    </li>
                                    <li>
                                        <p class="combine-notice__list-tit">[KT결합할인 안내]</p>
                                        <ul class="combine-notice__bullet">
                                            <li>고객이 2개 이상의 상품 또는 서비스를 함께 가입하여 이용하는 경우, 결합판매 요금할인을 받을 수 있으며,<br />그 종류로는 ① 유선간 결합 요금할인과 ② 유무선간 결합상품이 있습니다.</li>
                                            <li>타 결합할인, 요금(제휴)할인프로그램, 복지감면 요금할인과 중복 가입제한 될 수 있습니다. 가입 가능한 할인프로그램은 개별서비스 이용약관에 따릅니다.</li>
                                            <li>세부적인 결합할인 대상, 금액 및 이용조건은 이용약관을 따릅니다. 참조바랍니다.</li>
                                        </ul>
                                    </li>
                                    <li>
                                        <p class="combine-notice__list-tit">[KT할인반환금 기준 및 산정 방식]</p>
                                        <p class="combine-notice__list-titsub">○ 대상</p>
                                        <ul class="combine-notice__bullet">
                                            <li>이용자는 이용자의 책임있는 사유로 결합서비스 전부 또는 일부를 해지, 결합상품별로 할인반환금 부과 사유 발생 시 결합서비스 이용기간 동안 제공받은 결합할인 및 혜택을 반환하여야 합니다.</li>
                                            <li>설치 장소 변경 장소가 서비스 미제공 지역, 최저 속도보장제도 기준 미달, 서비스 불안정(유선서비스), 이용계약서 미서명(또는 녹취하지 않은 경우), 이용자 사망의 경우는 할인반환금을 청구하지 않습니다.</li>
                                        </ul>
                                    </li>
                                    <li>
                                        <p class="combine-notice__list-titsub">○ 할인반환금 산정 방법</p>
                                        <ul class="combine-notice__bullet">
                                            <li>할인반환금 산정 방법은 결합상품별, 가입 시기별로 다를 수 있으므로 이용약관을 참조바랍니다.</li>
                                            <li>할인형 방식으로 제공되는 결합할인에 대한 할인반환금은 결합된 개별 서비스의 접수일 기준에 따라 아래 산정식으로 부과됩니다.<br />(인터넷 올레/베이직/에센스/슬림플러스,OTV슬림/베이직/라이트/에센스/엔터/키즈/무비/세이브25/무비플러스/슬림VR/라이트VR/에센스VR/넷플릭스HD/넷플릭스UHD/프라임슈퍼/프라임홈스쿨, OTS슬림/베이직/라이트/에센스/엔터/키즈 등)</li>
                                        </ul>
                                    </li>
                                    <li>
                                        <p class="combine-notice__list-titsub">(2016.4.1. 이후 신규접수고객)</p>
                                        <ul class="combine-notice__bullet">
                                            <li>산정식 = Σ[약정기간별 총할인금액 X (1–사용기간별 위약금 할인율)]<br />※약정기간별 총할인금액 : 약정 사용기간 별로 제공받은 약정 할인액의 합산금액<br />※사용기간 별 위약금 할인율 : 약정 사용기간 별로 제공받은 약정할인액의 합산 금액에 대해 할인율을 적용하여 위약금 부과</li>
                                            <li>정지기간은 사용기간에 미포함 됩니다.</li>
                                            <li>산정 방식 예시 2년약정으로 인터넷+IPTV+인터넷 전화 결합 후 월 1,100원 결합할인 받은 뒤 13개월 차 해지 시 인터넷 결합할인 위약금 (1~6개월 약정할인금액 X (1-0%)) + (7~12개월 약정할인금액 X (1-60%)) + (13개월 약정할인금액 X (1-95%)) = 9,295원</li>
                                        </ul>
                                    </li>
                                    <li>
                                        <p class="combine-notice__list-tit">[정지]</p>
                                        <p class="combine-notice__list-titsub">○ 이용정지</p>
                                        <ul class="combine-notice__bullet">
                                            <li>회사는 결합서비스 이용자가 약관을 위반하는 경우와 요금을 미납했을 경우에 개별약관에서 정하는 바에 따라<br />결합서비스 일부 또는 전부에 대하여 일정기간 동안 서비스의 이용을 정지 또는 해지 할 수 있습니다.</li>
                                            <li>회사는 결합서비스의 일부 또는 전부에 대하여 이용정지 기간 동안에는 결합할인을 제공하지 않습니다. 다만, 유선상품은 이용정지 기간 동안 요금이 부과되는 경우에 요금이 부과되는 부분에 대하여 결합할인을 제공합니다.</li>
                                        </ul>
                                    </li>
                                    <li>
                                        <p class="combine-notice__list-titsub">○ 일시정지</p>
                                        <ul class="combine-notice__bullet">
                                            <li>이용자는 개별약관에서 정하는 바에 따라 결합서비스의 일부 또는 전부에 대하여 일정기간 동안 서비스의 일시정지를 회사에 신청할 수 있습니다.</li>
                                            <li>회사는 결합서비스 일부 또는 전부에 대하여 일시정지 기간 동안은 결합할인을 제공하지 않습니다.</li>
                                        </ul>
                                    </li>
                                    <li>
                                        <p class="combine-notice__list-tit">[해지]</p>
                                        <ul class="combine-notice__bullet">
                                            <li>이용자는 결합서비스 전부 또는 일부를 해지 할 경우 kt M고객센터(114)를 통해 회사에 신청해야 합니다.</li>
                                            <li>해지 시 kt M모바일 회선에 대한 위약금은 없으며, KT고객의 위약금은 KT고객센터를 통해 확인 바랍니다.</li>
                                            <li>결합 회선 일부가 변경 또는 해지되어 계약자의 잔여서비스로 결합을 구성하지 못하는 경우 직권으로 결합서비스를 해지 할 수 있으며, 위약금이 발생할 수 있습니다.자세한 내용은 KT Shop의 KT결합서비스 이용약관(제 3장 계약의 변경, 해지) 및 KT고객센터에서 확인 가능 합니다.</li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="certi-box--bottom u-ta-center">
                            <p><fmt:formatDate value="${nmcp:getDateToCurrent(0)}"  pattern="yyyy년  MM월  dd일" /><span>신청인 <em class="_userNm"><!-- 김엠모 --></em></span></p>
                            <b>주식회사 케이티엠모바일</b>
                        </div>
                    </div>
                </div>
            </div>
        </div>








    </t:putAttribute>






</t:insertDefinition>

