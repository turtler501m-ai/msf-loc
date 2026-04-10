<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/appForm/eSimInfo.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    eSIM 개통 신청
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="upper-banner upper-banner--esim-opening c-expand">
                <div class="upper-banner__content">
                    <strong class="upper-banner__title">하나의 폰 두개의 SIM<br>eSIM으로 합리적인 통신생활!</strong>
                    <a class="c-button c-button--sm upper-banner__anchor u-mr--8" href="/m/appForm/eSimView.do"> eSIM 신청서 작성
                        <i class="c-icon c-icon--anchor-black" aria-hidden="true"></i>
                    </a>
                    <button class="c-button c-button--sm upper-banner__anchor" data-scroll-top="#device_reg|120">가이드 보기
                        <i class="c-icon c-icon--anchor-black" aria-hidden="true"></i>
                    </button>
                </div>
                <div class="upper-banner__image">
                    <img src="/resources/images/mobile/esim/bg_esim_open.png" alt="">
                </div>
            </div>
            <div class="esim-join-wrap">
                <h3 class="esim-join__title">eSIM</h3>
                <hr class="c-hr">
                <p>eSIM은 스마트폰 본체에 내장된 SIM으로, 개통 후 프로파일을 다운로드함으로써 유심카드를 삽입하지 않아도 이용할 수 있는 서비스입니다.</p>
                <ul class="esim-join__list--fyr u-co-mint ">
                    <li>eSIM 개통을 원하시는 고객은 휴대폰 정보 확인을 위한 이미지 등록이 필요합니다. 이미지를 먼저 준비해 주세요.</li>
                    <li>eSIM은 휴대폰 당 1회선만 개통 가능하며, 두 번째 IMEI (IMEI2)로만 개통 가능합니다.</li>
                </ul>
                <div class="esim-Adv-box">
                    <div class="esim-Adv__item">
                        <i class="" aria-hidden="true">
                            <img src="/resources/images/mobile/esim/eSIM_Adv_01.png" alt="">
                        </i>
                        <div class="esim-Adv__panel">
                            <strong>유심카드 불필요</strong>
                            <p class="u-co-gray u-mb--2">eSIM은 스마트폰에 내장된 SIM으로 개통 하기 때문에 별도의 유심이 불필요합니다.</p>
                            <p class="u-co-mint">※ 단, 프로파일 다운로드 시 발행 비용(2,750원)이 부과됩니다.</p>
                        </div>
                    </div>
                    <div class="esim-Adv__item">
                        <i class="" aria-hidden="true">
                            <img src="/resources/images/mobile/esim/eSIM_Adv_02.png" alt="">
                        </i>
                        <div class="esim-Adv__panel">
                            <strong>배송 불필요</strong>
                            <p class="u-co-gray u-mb--2">eSIM은 스마트폰에 내장되어 있기 때문에 배송이 불필요합니다.</p>
                        </div>
                    </div>
                    <div class="esim-Adv__item">
                        <i class="" aria-hidden="true">
                            <img src="/resources/images/mobile/esim/eSIM_Adv_03.png" alt="">
                        </i>
                        <div class="esim-Adv__panel">
                            <strong>개통완료 시 바로 사용 가능</strong>
                            <p class="u-co-gray u-mb--2">eSIM 개통 완료 시 프로파일을 다운받으면 바로 사용이 가능합니다.</p>
                            <p class="u-co-mint">※ eSIM은 셀프개통이 불가하며 상담사 개통 신청만 가능합니다.</p>
                        </div>
                    </div>
                </div>

                <c:if test="${bannerList ne null and !empty bannerList}">
                    <div class="swiper-container swiper-event-banner friend-banner c-expand u-mt--48" id="swipereSIMBanner">
                        <div class="swiper-wrapper">
                            <c:forEach var="bannerList" items="${bannerList}">
                                <div class="swiper-slide" addr="${bannerList.mobileLinkUrl}" style="background-color:${bannerList.bgColor}">
                                    <c:if test="${bannerList.mobileLinkUrl ne null and bannerList.mobileLinkUrl ne ''}">
                                        <button class="swiper-event-banner__image" onclick="javascript:insertBannAccess('${bannerList.bannSeq}','${bannerList.bannCtg}');">
                                    </c:if>
                                        <img src="${bannerList.mobileBannImgNm}" alt="${bannerList.imgDesc}">
                                    </button>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>

                <span id="device_reg" class="c-hidden"></span>
                <div class="esim-step__title">
                    <div class="esim-step__head">
                        STEP1 <span>신청 준비</span>
                    </div>
                    <div class="esim-step__sub">eSIM 개통 시 하기 사항을 사전에 확인/준비 해 주세요.</div>
                </div>
                <h3 class="esim-join__title">eSIM 지원 기종 여부 확인</h3>
                <hr class="c-hr">
                <p>하기 리스트는 eSIM 사용이 가능한 대표 기종이며, 기종 별 eSIM 지원 여부 상세는 제조사 홈페이지를 통해 확인 바랍니다.</p>
                <p class="c-notice--red">
					<span>
						<i class="c-icon c-icon--bang--red" aria-hidden="true"></i>
					</span>
					<span>통신사에서 구매하신 휴대폰은 5G요금제로만 사용 가능하며 LTE요금제를 선택하여 개통하실 경우 사용이 불가합니다.</span>
				</p>
                <div class="esim-product-box">
                    <div class="esim-product__item">
                        <span class="esim-product__lable--iphone">iPhone</span>
                        <div class="esim-product__model">
                            <ul class="esim-product__list">
                                <li>iPhone XS, iPhone XS Max, iPhone XR 및 이후 모델</li>
                            </ul>
                        </div>
                    </div>
                    <div class="esim-product__item">
                        <span class="esim-product__lable--android">Android</span>
                        <div class="esim-product__model">
                            <ul class="esim-product__list">
                                <li>갤럭시 Z Flip4, 갤럭시 Z Fold4 및 해외 eSIM 지원 기종</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <h3 class="esim-join__title">IMEI/EID 이미지</h3>
                <hr class="c-hr">
                <p>eSIM 개통을 위해서는 EID등록이 선행되어야 하며, IMEI 및 32자리 EID 캡쳐화면 이미지 업로드가 필요합니다.</p>
                <ul class="esim-join__list--fyr u-co-mint">
                    <li>신청 전 캡쳐 이미지를 먼저 준비 바랍니다.</li>
                </ul>
                <h5 class="esim-join__subtitle">이미지 준비 가이드</h5>
                <div class="esim-guide-box">
                    <div class="esim-guide__list">
                        <span class="Number-label">1</span> 통화창에 *#06# 입력
                    </div>
                    <img src="/resources/images/mobile/esim/eSIM_guide_01.png" alt="1.통화창에 *#06#입력 가이드 이미지">
                </div>
                <div class="esim-guide-space">
                    <i class="c-icon c-icon--triangle_bluegreen-bottom" aria-hidden="true"></i>
                </div>
                <div class="esim-guide-box u-mt--0">
                    <div class="esim-guide__list">
                        <span class="Number-label">2</span> 화면 캡쳐
                    </div>
                    <img src="/resources/images/mobile/esim/eSIM_guide_02.png" alt="2.화면 캡쳐 정보의 가이드 이미지">
                </div>
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--20">
                    <li class="c-text-list__item u-co-gray fs-14">신청서 내 이미지 업로드 시 자동으로 기기 정보가 입력 됩니다.</li>
                    <li class="c-text-list__item u-co-gray fs-14">캡쳐 이미지 외 다른 이미지 업로드 시 기기 정보가 오 등록 될 수 있으므로 반드시 가이드를 준수 바랍니다.</li>
                </ul>
                <div class="esim-step__title">
                    <div class="esim-step__head">
                        STEP2 <span>eSIM 개통 신청</span>
                    </div>
                    <div class="esim-step__sub">준비가 완료되시면 eSIM 개통을 시작해 보세요.</div>
                </div>
                <p class="u-mt--32">가입신청서를 작성하시면 순차적으로 상담사가 전화로 본인확인 후 개통이 진행되며 지속 부재 시 자동으로 취소됩니다.</p>
                <p class="c-notice--red">
					<span>
						<i class="c-icon c-icon--bang--red" aria-hidden="true"></i>
					</span>
					<span>기기정보를 다시한번 확인해 주세요. 오등록 시 정상적으로 개통되지 않거나 eSIM 재다운로드가 필요할 수 있습니다. (eSIM 재 다운로드시 비용이 부됩니다.)</span>
				</p>
                <button class="c-button c-button--h48 c-button--mint c-button--full u-mt--38" onclick="javascript:location.href='/m/appForm/eSimView.do'">eSIM 신청서 작성</button>
                <div class="esim-step__title">
                    <div class="esim-step__head">
                        STEP3 <span>프로파일 다운로드</span>
                    </div>
                    <div class="esim-step__sub">휴대폰에 프로파일을 다운로드 하세요.</div>
                </div>
                <p class="u-mt--32">eSIM 개통 후 이용을 위해서는 QR코드를 스캔이 필요합니다.</p>
                <ul class="esim-join__list--fyr ">
                    <li>QR코드는 전 고객 공통이기 때문에 화면상의 QR코드를 스캔하셔도 되며, 신청과정에서 작성하신 연락처/이메일로도 QR코드가 발송됩니다.</li>
                </ul>
                <div class="esim-qr-box">
                    <p class="c-notice--red">
						<span>
							<i class="c-icon c-icon--bang--red" aria-hidden="true"></i>
						</span>
						<span>eSIM 프로파일을 다운로드 후 eSIM을 삭제하지 말아주세요. 재 다운로드 시 비용이 부과됩니다.</span>
					</p>
                    <img src="/resources/images/mobile/esim/eSIM_QR.png" alt="eSIM 개통 후 이용을 위한 QR코드 이미지">
                </div>
                <div class="esim-step__title">
                    <div class="esim-step__head">
                        STEP4 <span>재부팅</span>
                    </div>
                    <div class="esim-step__sub">eSIM프로파일을 다운로드하면 휴대폰을 재부팅 하세요.</div>
                </div>
                <h5 class="esim-join__subtitle">eSIM 정상 인식 확인 방법</h5>
                <img class="u-mt--18" src="/resources/images/mobile/esim/eSIM_check.png" alt="eSIM 정상 인식 확인 방법 이미지">
                <div class="c-notice-wrap">
                    <hr>
                    <h5 class="c-notice__title">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                        <span>eSIM 취급상의 유의사항</span>
                    </h5>
                    <ul class="c-notice__list">
                        <li>eSIM은 본체로부터 분리가 불가합니다.</li>
                        <li>휴대폰 분실/파손 또는 eSIM을 삭제한 경우 eSIM재발행(유상) 이 필요합니다. eSIM의 재발행은 고객센터를 통해서만 가능합니다.</li>
                    </ul>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>