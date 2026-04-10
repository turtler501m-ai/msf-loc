<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefaultTitle">

    <t:putAttribute name="titleAttr">USIM 없다면</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/appForm/withoutUsim.js?version=26-02-19"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="tab" value="${tab}">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">USIM 없다면</h2>
            </div>
            <div class="c-tabs c-tabs--type1 c-tabs--usim no-sticky" id="withoutUsim-tab" data-ui-tab>
			  <div class="c-tabs__list" role="tablist">
			    <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabpanel1" aria-selected="true" tabindex="0" data-name="esim"><p>eSIM</p><span>QR코드 개통</span></button>
			    <button class="c-tabs__button" id="tab2" role="tab" aria-controls="tabpanel2" aria-selected="false" tabindex="0" data-name="usim"><p>USIM</p><span>실물 유심 개통</span></button>
			  </div>
			</div>
			<div class="c-tabs__panel" id="tabpanel1" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
				<div class="esim-now-banner">
					<div class="esim-now-banner-wrap">
						<img class="esim-now-banner-effect" src="/resources/images/portal/esim/esim_now_effect1.png" alt="">
						<img class="esim-now-banner-effect" src="/resources/images/portal/esim/esim_now_effect2.png" alt="">
						<img class="esim-now-banner-effect" src="/resources/images/portal/esim/esim_now_effect3.png" alt="">
						<img class="esim-now-banner-effect" src="/resources/images/portal/esim/esim_now_effect4.png" alt="">
						<img class="esim-now-banner-effect" src="/resources/images/portal/esim/esim_now_effect5.png" alt="">
						<div class="c-button-wrap">
		                  <button class="c-button c-button--lg c-button--primary" id="step3">eSIM 셀프개통 하기</button>
		                </div>
		                <img src="/resources/images/portal/esim/esim_now_banner.png" alt="바로 eSIM 바로 개통, 쉽고 싸고 빠르게!">
					</div>
				</div>
				<div class="esim-now-content">
					<div class="esim-now-wrap w1100">
						<img src="/resources/images/portal/esim/esim_now_content1.png" alt="바로 eSIM 바로 개통, eSIM은 스마트폰 본체에 내장된 SIM으로 휴대폰에 장착할 필요 없이 SIM을 다운로드해 사용하는 방식입니다.">
					</div>
					<div class="esim-now-wide">
						<div class="esim-now-wrap w1100">
							<img src="/resources/images/portal/esim/esim_now_content2.png" alt="클릭 한 번으로 착! 쉽고! eSIM은 SIM을 꽂을 필요 없이 QR코드 다운으로 설치 및 이용이 쉽습니다.">
						</div>
					</div>
					<div class="esim-now-wide esim-now--blue">
						<div class="esim-now-wrap w1100">
							<img src="/resources/images/portal/esim/esim_now_content3.png" alt="유심보다 저렴하게! 싸고! eSIM은 USIM 보다 약 69% 저렴해 합리적으로 이용 가능합니다. 69% SAVE, eSIM 2,750원, USIM 6,600원">
						</div>
					</div>
					<div class="esim-now-wide">
						<div class="esim-now-wrap w1100">
							<img src="/resources/images/portal/esim/esim_now_content4.png" alt="5분이면 개통 끝! 빠르게! eSIM은 카드 구매, 배송 기다릴 필요 없이 휴대폰만 있으면 빠르게 개통 가능합니다.">
						</div>
					</div>
					<div class="esim-now-wrap w1100">
						<img src="/resources/images/portal/esim/esim_now_content5.png" alt="">
					</div>
					<div class="esim-now-wrap">
						<img src="/resources/images/portal/esim/esim_now_fast.png" alt="얼마나 빠를까? eSIM vs USIM">
					</div>
					<div class="esim-now-wrap esim-fast" data-aos="fade-up" data-aos-duration="1500" data-aos-delay="0">
						<img class="esim-fast__item" src="/resources/images/portal/esim/esim_now_fast_step1.png" alt="eSIM USIM">
						<img class="esim-fast__item" src="/resources/images/portal/esim/esim_now_fast_step2.png" alt="eSIM : 1분, *#06# 입력 후 기기정보 캡쳐, USIM : 1분, 유심카드 선택">
						<img class="esim-fast__item" src="/resources/images/portal/esim/esim_now_fast_step3.png" alt="eSIM : 2분, 요금제 선택 후 셀프개통 시작!, USIM : 2분 주문 완료">
						<img class="esim-fast__item" src="/resources/images/portal/esim/esim_now_fast_step4.png" alt="eSIM : 2분, QR코드 스캔 후 eSIM 활성화하면 끝!, USIM : 2분, 배송 준비중">
						<img class="esim-fast__item" src="/resources/images/portal/esim/esim_now_fast_step5.png" alt="eSIM : 셀프개통  5분 완성, USIM : 1시간, 배송 출발, 2일 뒤, 드디어 유심 도착! 셀프개통 이제 시작">
					</div>
					<div class="esim-now-wrap">
						<img src="/resources/images/portal/esim/esim_now_join.png" alt="5분 완성! eSIM 가입방법">
					</div>
					<div class="esim-now-wide">
						<div class="esim-now-wrap">
							<img src="/resources/images/portal/esim/esim_now_join_guide.png" alt="1. 개통 전 기기 정보를 준비합니다. 통화창에 *#06# 입력 후 고유식별 번호 EID와 IMEI가 포함된 화면을 캡처해 주세요. 2. 원하는 요금제 선택 후 가입 신청서 내 휴대폰 정보를 등록합니다. 이미지 등록 버튼 클릭 후 캡처한 이미지를 업로드해 주세요. 3. 화면의 QR 코드 스캔 후 eSIM 활성화하면 끝! 정상적인 사용을 위해서는 휴대폰에 eSIM을 반드시 발급받으셔야 합니다.">
						</div>
					</div>
					<div class="esim-now-wide">
						<div class="esim-now-wrap">
							<div class="c-button-wrap u-pb--80">
			                  <button class="c-button c-button--lg c-button--w460 c-button--h68 u-fs-22 c-button--primary" data-dialog-trigger="#esimPhone">eSIM 지원 휴대폰 확인하기</button>
			                  <button class="c-button c-button--lg c-button--w460 c-button--h68 u-fs-22 c-button--primary" data-dialog-trigger="#eidNumber">고유번호(EID/IMEI) 확인방법</button>
			                </div>
						</div>
					</div>
					<div class="esim-now-wrap">
						<img src="/resources/images/portal/esim/esim_now_issuance.png" alt="eSIM 발급 가이드 반드시 단말기가 Wi-Fi 또는 셀룰러 망 (5G/LTE)에 연결되어 있어야 합니다. eSIM으로 개통 시, 개통 신청한 단말에 eSIM 발급(상세정보 추가 입력)이 필요합니다.">
					</div>
					<div class="esim-now-wrap">
						<div class="esim-join-wrap">
							<div class="c-accordion c-accordion--single">
			                    <ul class="c-accordion__box">
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button" id="acc_header_01" type="button" aria-expanded="false" aria-controls="acc_content_01">
			                                	<strong class="c-accordion__title">삼성 갤럭시 단말</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_content_01" aria-labelledby="acc_header_01">
			                                <div class="c-accordion__inside">
			                                    <ul class="esim-process__list android u-mt--0">
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>1</em>메인화면
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungA-01.png" alt="삼성 갤럭시 단말 eSIM 발급방법 : 1.메인화면">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>2</em>설정 > 연결
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungA-02.png" alt="삼성 갤럭시 단말 eSIM 발급방법 : 2.설정 > 연결">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>3</em>SIM 카드 관리자
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungA-03.png" alt="삼성 갤럭시 단말 eSIM 발급방법 : 3.SIM 카드 관리자">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>4</em>모바일 요금제 추가
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungA-04.png" alt="삼성 갤럭시 단말 eSIM 발급방법 : 4.모바일 요금제 추가">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>5</em>자동으로 발급됨
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungA-05.png" alt="삼성 갤럭시 단말 eSIM 발급방법 : 5.자동으로 발급됨">
			                                        </li>
			                                    </ul>
			                                    <div class="esim-process__head u-mt--35">
			                                        <p class="sticker">자동으로 발급실패시엔</p>
			                                    </div>
			                                    <ul class="esim-process__list android">
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>1</em>통신사 QR코드 스캔
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungB-01.png" alt="삼성 갤럭시 단말 eSIM 자동으로 발급실패시 : 1.통신사 QR코드 스캔">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>2</em>QR코드 스캔
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungB-02.png" alt="삼성 갤럭시 단말 eSIM 자동으로 발급실패시 : 2.QR코드 스캔">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>3</em>발급 시작
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungB-03.png" alt="삼성 갤럭시 단말 eSIM 자동으로 발급실패시 : 3.발급 시작">
			                                        </li>
			                                    </ul>
			                                    <div class="esim-process__head u-mt--35">
			                                        <p class="sticker">QR코드가 없을 경우에는</p>
			                                        <div class="esim-process__code">
			                                            <p>활성화 코드 : <span>LPA:1$kt.prod.ondemandconnectivity.com$</span></p>
			                                        </div>
			                                    </div>
			                                    <ul class="esim-process__list android">
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>1</em>통신사 QR코드 스캔
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungC-01.png" alt="삼성 갤럭시 단말 eSIM QR코드가 없을 경우 : 1.통신사 QR코드 스캔">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>2</em>개통 코드 입력 진입
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungC-02.png" alt="삼성 갤럭시 단말 eSIM QR코드가 없을 경우 : 2.개통코드입력 진입">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>3</em>활성화 코드 직접 입력
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungC-03.png" alt="삼성 갤럭시 단말 eSIM QR코드가 없을 경우 : 3.활성화 코드 직접 입력">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>4</em>발급 시작
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungC-04.png" alt="삼성 갤럭시 단말 eSIM QR코드가 없을 경우 : 4.발급 시작">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>5</em>SIM카드 관리자 화면에서 eSIM이 추가됨
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungC-05.png" alt="삼성 갤럭시 단말 eSIM QR코드가 없을 경우 : 5.SIM카드 관리자 화면에서 eSIM이 추가됨">
			                                        </li>
			                                        <li class="c-flex">
			                                            <div class="esim-process__textbox">
			                                                <strong>1~2분 후 또는 재부팅</strong>
			                                                <p>※ 실제로 번호가 뜨기까지 약 1~2분정도 소요됨<br/> 그래도 안보이면<br/>단말 재부팅 권장</p>
			                                            </div>
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>6</em>USIM + eSIM<br/>듀얼심 개통상태
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungC-06.png" alt="삼성 갤럭시 단말 eSIM QR코드가 없을 경우 : 6.USIM + eSIM 듀얼심 개통상태">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>7</em>eSIM 자급 개통 상태<br/>(USIM 없음)
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_samsungC-07.png" alt="삼성 갤럭시 단말 eSIM QR코드가 없을 경우 : 7.eSIM 자급 개통 상태(USIM 없음)">
			                                        </li>
			                                    </ul>
			                                </div>
			                            </div>
			                        </li>
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button" id="acc_header_02" type="button" aria-expanded="false" aria-controls="acc_content_02">
			                                	<strong class="c-accordion__title">애플 iPhone 단말</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_content_02" aria-labelledby="acc_header_02">
			                                <div class="c-accordion__inside">
			                                    <ul class="esim-process__list ios u-mt--0">
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>1</em>메인화면
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleA-01.png" alt="애플 iPhone 단말 eSIM 발급방법 : 1.메인화면">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>2</em>설정 > 연결
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleA-02.png" alt="애플 iPhone 단말 eSIM 발급방법 : 2.설정 > 연결">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>3</em>셀룰러 요금제 추가
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleA-03.png" alt="애플 iPhone 단말 eSIM 발급방법 : 3.셀룰러 요금제 추가">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>4</em>카메라 활성화
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleA-04.png" alt="애플 iPhone 단말 eSIM 발급방법 : 4.카메라 활성화">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>5</em>회선 구분을 위한 레이블<br/>(회선의 이름) 설정
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleB-01.png" alt="애플 iPhone 단말 eSIM 발급방법 : 5.회선 구분을 위한 레이블(회선의 이름) 설정">
			                                        </li>
			                                        <li>
			                                            <div class="esim-process__num no-title">
			                                            </div>
			                                            <img src="/resources/images/common/img_eSIM_appleB-02.png" alt="애플 iPhone 단말 eSIM 발급방법 : 5.회선 구분을 위한 레이블(회선의 이름) 설정">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>6</em>기본으로 사용할 음성/메시지/<br/>데이터 회선 선택
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleB-03.png" alt="애플 iPhone 단말 eSIM 발급방법 : 6.기본으로 사용할 음성/메시지/데이터 회선 선택">
			                                        </li>
			                                        <li>
			                                            <div class="esim-process__num no-title">
			                                            </div>
			                                            <img src="/resources/images/common/img_eSIM_appleB-04.png" alt="애플 iPhone 단말 eSIM 발급방법 : 6.기본으로 사용할 음성/메시지/데이터 회선 선택">
			                                        </li>
			                                    </ul>
			                                    <div class="esim-process__head u-mt--35">
			                                        <p class="sticker">QR코드가 없을 경우에는</p>
			                                        <div class="esim-process__code">
			                                            <p>활성화 코드 : <span>kt.prod.ondemandconnectivity.com</span></p>
			                                        </div>
			                                    </div>
			                                    <ul class="esim-process__list ios">
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>1</em>세부사항 직접입력
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleC-01.png" alt="애플 iPhone 단말 eSIM QR코드가 없을 경우 : 1.세부사항 직접입력">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>2</em>활성화 코드 직접입력
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleC-02.png" alt="애플 iPhone 단말 eSIM QR코드가 없을 경우 : 2.활성화 코드 직접입력">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>3</em>발급 후 안테나 상태
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleC-03.png" alt="애플 iPhone 단말 eSIM QR코드가 없을 경우 : 3.발급 후 안테나 상태">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>4</em>설정 > 일반 > 정보 화면
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleC-04.png" alt="애플 iPhone 단말 eSIM QR코드가 없을 경우 : 4.설정 > 일반 > 정보 화면">
			                                        </li>
			                                        <li>
			                                            <span class="Number-label--type2">
			                                                <em>5</em>제어센터 화면
			                                               </span>
			                                            <img src="/resources/images/common/img_eSIM_appleC-05.png" alt="애플 iPhone 단말 eSIM QR코드가 없을 경우 : 5.제어센터 화면">
			                                        </li>
			                                    </ul>
			                                </div>
			                            </div>
			                        </li>
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button is-active" id="acc_header_03" type="button" aria-expanded="true" aria-controls="acc_content_03">
			                                	<strong class="c-accordion__title">핸드폰에서 QR 코드 스캔</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_content_03" aria-labelledby="acc_header_03">
			                                <div class="c-accordion__inside">
			                                    <ul class="c-text-list c-bullet c-bullet--dot">
			                                        <li class="c-text-list__item u-co-gray u-co-gray-9 u-mt--0">eSIM QR코드를 인식하여 발급받는 방식</li>
			                                        <li class="c-text-list__item u-co-gray u-co-gray-9"><b>공통 QR</b> : 모든 QR이 동일한 모양. eSIM 개통된 단말기에 한해 프로파일 발급 가능</li>
			                                    </ul>
			                                    <div class="esim-qr-img">
			                                        <img src="/resources/images/portal/esim/esim_now_issuance_qr.png" alt="eSIM 발급 시 스캔하는 QR코드 예시">
			                                    </div>
			                                </div>
			                            </div>
			                        </li>
			                    </ul>
			                </div>
		                </div>
					</div>
					<div class="esim-now-wrap">
						<img src="/resources/images/portal/esim/esim_now_notice.png" alt="eSIM 취급상의 유의사항 eSIM은 본체로부터 분리가 불가합니다. 휴대폰 분실/파손 또는 eSIM을 삭제한 경우 eSIM재발행(유상)이 필요합니다. eSIM의 재발행은 고객센터를 통해서만 가능합니다.">
					</div>
					<div class="esim-now-wide">
						<div class="esim-now-wrap">
							<img src="/resources/images/portal/esim/esim_now_time.png" alt="eSIM 개통 가능 시간 셀프개통(번호이동): 10:00 ~ 19:50 (일요일, 신정·설·추석 당일 제외) 셀프개통(신규): 08:00 ~ 21:50 상담사 개통: 09:00 이후 순차 진행 ※ 외국인·미성년자는 상담사 개통만 가능">
						</div>
					</div>
					<div class="esim-now-wrap">
						<img src="/resources/images/portal/esim/esim_now_review.png" alt="98% 고객이 만족하는 eSIM">
						<div class="swiper esim-now-review-swiper">
						  <div class="swiper-wrapper">
						    <div class="swiper-slide"><img src="/resources/images/portal/esim/esim_now_review_list1.png" alt="생각보다 쉬워서 놀랐어요. 비 오는 날이었는데 밖에 나갈 필요도 없고 집에서 다 해결됐어요, 5G 모두다 맘껏 70GB+(밀리의 서재), 박**"></div>
						    <div class="swiper-slide"><img src="/resources/images/portal/esim/esim_now_review_list2.png" alt="급하게 개통해야 했는데 배송 기다릴 필요 없이 바로 쓸 수 있어서 좋았어요. 전체 과정이 생각보다 간편했습니다, 모두다 맘껏 100GB+(CU 20%할인), 윤**"></div>
						    <div class="swiper-slide"><img src="/resources/images/portal/esim/esim_now_review_list3.png" alt="유심비용보다 저렴한데다 빠르고 쉬워서 다음에도 eSIM 쓸 것 같아요. 매장 방문 없이 집에서 가능해서 더 편했습니다. 모두다 맘껏 100GB+(밀리의 서재 FREE) 김**"></div>
						    <div class="swiper-slide"><img src="/resources/images/portal/esim/esim_now_review_list4.png" alt="설명대로 따라하니까 금방 개통됐어요. 복잡한 과정 없이 써서 좋았습니다. 5G 모두다 맘껏 14GB+ 백**"></div>
						    <div class="swiper-slide"><img src="/resources/images/portal/esim/esim_now_review_list5.png" alt="처음 써보는 eSIM 이라 걱정했는데 어렵지 않았어요. 빠르고 간편해서 만족합니다. 5G 모두다 맘껏 안심 8GB+ 송**"></div>
						  </div>
						</div>
					</div>
					<div class="esim-now-wide">
						<div class="esim-now-wrap">
							<img src="/resources/images/portal/esim/esim_now_qna.png" alt="자주 묻는 질문">
						</div>
						<div class="esim-now-wrap u-pb--120">
							<div class="c-accordion c-accordion--single qna">
			                    <ul class="c-accordion__box">
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button is-active" id="acc_qna_header_01" type="button" aria-expanded="true" aria-controls="acc_qna_01">
			                                	<strong class="c-accordion__title">
				                                	<div class="esim-now-qna-wrap">
					                                	<span class="esim-now-question">Q</span>
					                                	<span>eSIM 개통은 모든 요금제로 신청이 가능한가요?</span>
				                                	</div>
			                                	</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_qna_01" aria-labelledby="acc_qna_header_01">
			                                <div class="c-accordion__inside">
			                                    <div class="esim-now-qna-wrap">
				                                    <span class="esim-now-answer">A</span>
				                                    <span>일반 요금제는 모두 신청 가능하며, 데이터 쉐어링, 워치 등의 특수 전용 요금제는 이용이 어렵습니다.</span>
			                                    </div>
			                                </div>
			                            </div>
			                        </li>
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button" id="acc_qna_header_02" type="button" aria-expanded="true" aria-controls="acc_qna_02">
			                                	<strong class="c-accordion__title">
				                                	<div class="esim-now-qna-wrap">
					                                	<span class="esim-now-question">Q</span>
					                                	<span>[삼성 단말] eSIM을 이용중인데 교통카드나 모바일 사원증이 안됩니다.</span>
				                                	</div>
			                                	</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_qna_02" aria-labelledby="acc_qna_header_02">
			                                <div class="c-accordion__inside">
			                                    <div class="esim-now-qna-wrap">
				                                    <span class="esim-now-answer">A</span>
				                                    <span>eSIM,USIM을 동시에 사용하는 경우 설정>연결>SIM 카드 관리자에서 주 통화SIM을 USIM으로 설정 후 사용 가능합니다. eSIM 단독으로 사용하는 스마트폰에서는 현재 서비스 지원이 불가할 수 있으며, 사용 가능 여부는 교통카드 또는 모바일사원증 서비스 제공 업체를 통해 확인할 수 있습니다.</span>
			                                    </div>
			                                </div>
			                            </div>
			                        </li>
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button" id="acc_qna_header_03" type="button" aria-expanded="true" aria-controls="acc_qna_03">
			                                	<strong class="c-accordion__title">
				                                	<div class="esim-now-qna-wrap">
					                                	<span class="esim-now-question">Q</span>
					                                	<span>설정에서 eSIM을 삭제하면 개통이 해지 되나요?</span>
				                                	</div>
			                                	</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_qna_03" aria-labelledby="acc_qna_header_03">
			                                <div class="c-accordion__inside">
			                                    <div class="esim-now-qna-wrap">
				                                    <span class="esim-now-answer">A</span>
				                                    <span>아닙니다. 설정에서 eSIM 삭제하는 것은 단순 해당 핸드폰에서의 eSIM 정보 삭제를 의미하고, 회선은 유지됩니다. 해지를 위해서는 전산 상 해지 처리가 이루어져야 합니다.</span>
			                                    </div>
			                                </div>
			                            </div>
			                        </li>
			                    </ul>
			                </div>
						</div>
					</div>
				</div>
			</div>
			<div class="c-tabs__panel" id="tabpanel2" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
				<div class="usim-now-banner">
					<div class="usim-now-banner-wrap">
						<img class="usim-now-banner-effect" src="/resources/images/portal/usim/without_usim_effect.png" alt="">
						<div class="c-button-wrap">
		                  <button class="c-button c-button--lg c-button--primary" href="javascript:void(0);" onclick="selfTimeChk();">USIM 셀프개통 하기</button>
		                  <button class="c-button c-button c-button--white" data-dialog-trigger="#join-info-modal" id="selfBtn" style="display:none;">셀프개통 가능 시간 안내 popup 호출</button>
		                </div>
		                <img src="/resources/images/portal/usim/without_usim_banner.png" alt="USIM이 없다면? 당일 배송, 당일 개통">
					</div>
				</div>
				<div class="usim-now-content">
					<div class="usim-now-wrap w1100">
						<img src="/resources/images/portal/usim/without_usim_content1.png" alt="유심 이렇게 구매할 수 있어요!">
						<a class="usim-now-link" href="/appForm/reqSelfDlvry.do" title="바로배송 유심 신청 페이지 이동">
							<img src="/resources/images/portal/usim/without_usim_content2.png" alt="바로배송 유심, 2시간 내 바로 도착! 유심 받고 즉시 개통하세요.">
						</a>
						<img src="/resources/images/portal/usim/without_usim_content3.png" alt="">
						<a class="usim-now-link" href="/direct/storeInfo.do" title="편의점/마트 신청 페이지 이동">
							<img src="/resources/images/portal/usim/without_usim_content4.png" alt="편의점 · 마트, 집 근처에서 바로 구매! 기다림 없이 개통하세요.">
						</a>
						<img src="/resources/images/portal/usim/without_usim_content5.png" alt="">
						<a class="usim-now-link" href="/direct/openMarketInfo.do" title="오픈마켓 신청 페이지 이동">
							<img src="/resources/images/portal/usim/without_usim_content6.png" alt="온라인 마켓, 온라인으로 간편 주문! 도착 즉시 바로 개통하세요.">
						</a>
						<img src="/resources/images/portal/usim/without_usim_content7.png" alt="">
					</div>
					<div class="usim-now-wide usim-now--blue">
						<div class="usim-now-wrap w1100">
							<img src="/resources/images/portal/usim/without_usim_esim.png" alt="잠깐! eSIM은 어떠세요? eSIM은 USIM보다 더 저렴하고, 배송 기다릴 필요 없어요! 쉽고! SIM 꽂을 필요 없이 QR코드 다운로드만 하면 끝! 싸고! 기존 USIM보다 약 69% 더 저렴하게, 부담 없이! 빠르게! 구매·배송 기다릴 필요 없이 휴대폰만 있으면 즉시 개통!">
						</div>
					</div>
					<div class="usim-now-wide usim-now--blue">
						<div class="esim-now-wrap w1100">
							<div class="c-button-wrap u-pb--88">
			                    <button class="c-button c-button--lg c-button--w460 c-button--h68 u-fs-22 c-button--primary" id="step4">eSIM 셀프개통 하기</button>
			                </div>
						</div>
					</div>
					<div class="usim-now-wrap w1100">
						<img src="/resources/images/portal/usim/without_usim_type.png" alt="USIM vs eSIM 어떤 방식이 나에게 맞을까? 두 가지 방식을 자세히 비교해 보고, 내 휴대폰 사용 패턴에 맞는 개통 방법을 선택하세요!">
						<div class="sr-only">
	                        <table>
	                            <caption>
	                                KT인터넷 3년 약정 기준 결합할인 적용 금액 표
	                            </caption>
	                            <thead>
	                                <tr>
	                                    <th scope="colgroup">USIM 유심</th>
	                                    <th scope="colgroup">vs</th>
	                                    <th scope="colgroup">eSIM 이심</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                                <tr>
	                                    <td>카드 형태의 실물 SIM</td>
	                                    <td>형태</td>
	                                    <td>휴대폰에 내장된 디지털 SIM</td>
	                                </tr>
	                                <tr>
	                                    <td>가입신청서 작성 전 유심 구매 (배송 또는 방문 구입) 후 휴대폰에 삽입</td>
	                                    <td>개통 방식</td>
	                                    <td>가입신청서 작성 후 QR코드 스캔·앱 설치 등 비대면 개통</td>
	                                </tr>
	                                <tr>
	                                    <td>분실 · 파손 가능</td>
	                                    <td>교체/분실</td>
	                                    <td>분실 걱정 없음</td>
	                                </tr>
	                                <tr>
	                                    <td>6,600원 / NFC 8,800원</td>
	                                    <td>가격</td>
	                                    <td>2,750원</td>
	                                </tr>
	                                <tr>
	                                    <td>대부분의 단말에서 사용 가능 </td>
	                                    <td>지원 단말</td>
	                                    <td>지원 단말에서만 사용 가능, 애플 iPhone XS · Max · XR 및 이후 모델, 삼성 갤럭시 Z Flip4 · Fold4 이후 모델, 해외 eSIM 지원 기종</td>
	                                </tr>
	                                <tr>
	                                    <td>일부 단말만 가능 </td>
	                                    <td>듀얼심 사용 </td>
	                                    <td>eSIM + 물리 유심 동시 사용 가능</td>
	                                </tr>
	                                <tr>
	                                    <td>플라스틱 유심 카드</td>
	                                    <td>ESG</td>
	                                    <td>물리 심 없음</td>
	                                </tr>
	                            </tbody>
	                        </table>
	                    </div>
					</div>
					<div class="usim-now-wide usim-now--gray">
						<div class="usim-now-wrap w1100">
							<img src="/resources/images/portal/usim/without_usim_qna.png" alt="자주 묻는 질문 TOP 5">
						</div>
					</div>
					<div class="usim-now-wide usim-now--gray">
						<div class="usim-now-wrap u-pb--120">
							<div class="c-accordion c-accordion--single qna">
			                    <ul class="c-accordion__box">
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button" id="acc_usim_qna_header_01" type="button" aria-expanded="false" aria-controls="acc_usim_qna_01">
			                                	<strong class="c-accordion__title">
				                                	<div class="esim-now-qna-wrap">
					                                	<span class="esim-now-question">Q</span>
					                                	<span>USIM 카드 없이 개통 할 수 있나요?</span>
				                                	</div>
			                                	</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_usim_qna_01" aria-labelledby="acc_usim_qna_header_01">
			                                <div class="c-accordion__inside">
			                                    <div class="esim-now-qna-wrap">
				                                    <span class="esim-now-answer">A</span>
				                                    <span>USIM 및 eSIM 으로 개통이 가능하며, eSIM 가능 단말의 경우 유심카드 없이 eSIM으로 개통 가능합니다.</span>
			                                    </div>
			                                </div>
			                            </div>
			                        </li>
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button" id="acc_usim_qna_header_02" type="button" aria-expanded="false" aria-controls="acc_usim_qna_02">
			                                	<strong class="c-accordion__title">
				                                	<div class="esim-now-qna-wrap">
					                                	<span class="esim-now-question">Q</span>
					                                	<span>5G 요금제를 사용하려면 5G 전용 USIM 이어야 하나요?</span>
				                                	</div>
			                                	</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_usim_qna_02" aria-labelledby="acc_usim_qna_header_02">
			                                <div class="c-accordion__inside">
			                                    <div class="esim-now-qna-wrap">
				                                    <span class="esim-now-answer">A</span>
				                                    <span>아닙니다. LTE 유심과 5G유심 구분이 없으며, 요금제 사용이나 속도에 대한 차이 역시 없으니, LTE 유심을 사용하셔도 됩니다.<br />(단, 5G <-> 3G는 불가)</span>
			                                    </div>
			                                </div>
			                            </div>
			                        </li>
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button" id="acc_usim_qna_header_03" type="button" aria-expanded="false" aria-controls="acc_usim_qna_03">
			                                	<strong class="c-accordion__title">
				                                	<div class="esim-now-qna-wrap">
					                                	<span class="esim-now-question">Q</span>
					                                	<span>USIM 구입 시 NFC USIM과 일반 USIM이 있는데 차이가 있나요?</span>
				                                	</div>
			                                	</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_usim_qna_03" aria-labelledby="acc_usim_qna_header_03">
			                                <div class="c-accordion__inside">
			                                    <div class="esim-now-qna-wrap">
				                                    <span class="esim-now-answer">A</span>
				                                    <span>NFC 란 약 10CM 이내 거리에서 무선의 데이터를 주고 받을 수 있는 기술입니다. NFC 기능이 있는 단말기에서 교통카드 기능 이용을 원하시는 경우 NFC 유심으로 구매하셔야만, 이용 가능합니다.</span>
			                                    </div>
			                                </div>
			                            </div>
			                        </li>
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button" id="acc_usim_qna_header_04" type="button" aria-expanded="false" aria-controls="acc_usim_qna_04">
			                                	<strong class="c-accordion__title">
				                                	<div class="esim-now-qna-wrap">
					                                	<span class="esim-now-question">Q</span>
					                                	<span>USIM 구매 후 불량인 경우 교체가 가능한가요?</span>
				                                	</div>
			                                	</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_usim_qna_04" aria-labelledby="acc_usim_qna_header_04">
			                                <div class="c-accordion__inside">
			                                    <div class="esim-now-qna-wrap">
				                                    <span class="esim-now-answer">A</span>
				                                    <span>네 유심이 불량일 경우 개통하신 대리점에서 동일한 유심으로 무상교체 가능합니다. 다이렉트몰 또는 알뜰폰 허브사이트를 통하여 신청 및 개통하신 고객은 고객센터에 문의 부탁드립니다.</span>
			                                    </div>
			                                </div>
			                            </div>
			                        </li>
			                        <li class="c-accordion__item">
			                            <div class="c-accordion__head">
			                                <button class="c-accordion__button" id="acc_usim_qna_header_05" type="button" aria-expanded="false" aria-controls="acc_usim_qna_05">
			                                	<strong class="c-accordion__title">
				                                	<div class="esim-now-qna-wrap">
					                                	<span class="esim-now-question">Q</span>
					                                	<span>바로배송(일반 택배)으로 USIM 을 신청을 하려고 하는데, 제 휴대폰 USIM 사이즈를 모르겠어요.</span>
				                                	</div>
			                                	</strong>
			                                </button>
			                            </div>
			                            <div class="c-accordion__panel expand" id="acc_usim_qna_05" aria-labelledby="acc_usim_qna_header_05">
			                                <div class="c-accordion__inside">
			                                    <div class="esim-now-qna-wrap">
				                                    <span class="esim-now-answer">A</span>
				                                    <span>바로배송(일반 택배)을 통해 주문하신 경우 3 in1 USIM으로 배송되어 나노/마이크로/일반 사이즈 모두 이용 가능합니다.</span>
			                                    </div>
			                                </div>
			                            </div>
			                        </li>
			                    </ul>
			                </div>
						</div>
					</div>
				</div>
			</div>
        </div>

        <!-- eSIM 지원 휴대폰 확인하기 팝업 -->
		<div class="c-modal c-modal--xlg" id="esimPhone" role="dialog" aria-labelledby="esimPhone__title">
		    <div class="c-modal__dialog" role="document">
		      <div class="c-modal__content">
		        <div class="c-modal__header">
		          <h2 class="c-modal__title" id="esimPhone__title">eSIM 지원 휴대폰 확인하기</h2>
		          <button class="c-button" data-dialog-close>
		            <i class="c-icon c-icon--close" aria-hidden="true"></i>
		            <span class="c-hidden">팝업닫기</span>
		          </button>
		        </div>
		        <div class="c-modal__body c-modal__body--full u-pb--0">
		        	<img src="/resources/images/portal/esim/esim_now_modal1.png" alt="eSIM 지원 휴대폰 기종별 상세 지원 여부는 제조사 홈페이지를 확인해주세요. Androidㅣ삼성 갤럭시 S 시리즈 : S25 FE · S25 Edge · S25 · S25+ · S25 Ultra, S24 · S24+ · S24 Ultra, S23 · S23+ · S23 Ultra · S23 FE, 갤럭시 Z 시리즈 : Z Fold7 · Z Flip7 · Z Flip7 FE, Z Fold6 · Z Flip6, Z Fold5 · Z Flip5, Z Fold4 · Z Flip4, 갤럭시 A 시리즈 : A35 · A36 · A54 · A55 · A56, 태블릿 : Galaxy Tab S9 · S9+ · S9 Ultra, iOSㅣ애플, 아이폰 (XS · XR 이후 전부 지원) iPhone 17 · 17 Pro · 17 Pro Max · 17 Air, iPhone 16 · 16 Pro · 16 Pro Max · 16 Plus · 16e iPhone 15 · 14 · 13 · 12 · 11 시리즈 , iPhone SE2 · SE3, 아이패드 iPad Pro 11·12.9 (3~6세대), iPad Air (3~5세대), iPad mini (5·6세대), iPad (7~10세대), 아이패드 (eSIM 단독 탑재 모델) iPad Pro 11 · 13 (M4), iPad Air 11 · 13 (M2)">
		        </div>
		      </div>
		    </div>
		</div>
		<!-- 고유번호(EID/IMEI) 확인방법 팝업 -->
		<div class="c-modal c-modal--xlg" id="eidNumber" role="dialog" aria-labelledby="eidNumber__title">
		    <div class="c-modal__dialog" role="document">
		      <div class="c-modal__content">
		        <div class="c-modal__header">
		          <h2 class="c-modal__title" id="eidNumber__title">고유번호(EID/IMEI) 확인방법</h2>
		          <button class="c-button" data-dialog-close>
		            <i class="c-icon c-icon--close" aria-hidden="true"></i>
		            <span class="c-hidden">팝업닫기</span>
		          </button>
		        </div>
		        <div class="c-modal__body c-modal__body--full u-pb--0">
		        	<img src="/resources/images/portal/esim/esim_now_modal2.png" alt="고유번호(EID/IMEI) 확인방법, 신청서 내 이미지 업로드 시 자동으로 기기 정보가 입력됩니다. 캡처 이미지 외 다른 이미지 업로드 시 기기 정보가 오 등록 될 수 있으므로 반드시 가이드를 준수 바랍니다.">
		        </div>
		      </div>
		    </div>
		</div>

        <div class="c-modal c-modal--sm" id="join-info-modal" role="dialog" tabindex="-1" aria-labelledby="#join-info-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="self-opening">
                            <strong class="self-opening__title">신규 가입만 가능한 시간입니다.</strong>
                            <p class="self-opening__text u-mb--0">
                                   번호 이동 셀프 개통은 <br> <a class="u-td-underline u-fw--medium" href="/appForm/appCounselorInfo.do">온라인 가입신청</a>을 이용해 주세요.
                            </p>
                            <span class="c-text-label c-text-label--type2 c-text-label--red u-mt--20 rectangle">셀프개통 가능시간</span>
                            <div class="c-table">
                                <table>
                                    <caption>번호이동, 신규가입을 포함한 셀프개통 가능 시간 정보</caption>
                                    <colgroup>
                                        <col style="width: 50%">
                                        <col style="width: 50%">
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th scope="col">번호이동</th>
                                            <th scope="col">신규가입</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <strong class="self-opening__highlight">10:00 ~ 19:50</strong>
                                                <p class="c-bullet c-bullet--fyr u-co-gray">일요일, 신정/설/추석 당일 제외</p>
                                            </td>
                                            <td>
                                                <strong class="self-opening__highlight">08:00 ~ 21:50</strong>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="c-button-wrap u-mt--48">
                            <a class="c-button c-button--full c-button--primary" href="/appForm/appFormDesignView.do" id="movBtn" style="display:none;">확인</a>
                            <a class="c-button c-button--full c-button--primary" data-dialog-close id="closeBtn">확인</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>

