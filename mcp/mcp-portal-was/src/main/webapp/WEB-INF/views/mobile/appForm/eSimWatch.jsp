<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants"/>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/validator.js?version=23.02.06"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/eSimWatch.js?version=24.12.17"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                       워치 가입 신청
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

            <div class="watch-join-wrap">
                <div class="_Step1">
                    <h4 class="watch-join__title">kt M모바일 고객인증</h4>
                    <hr class="c-hr">
                    <div class="c-form">
                        <div class="c-form__input">
							<input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" value="${cstmrName}"  maxlength="60" <c:if test="${cstmrName ne null and !empty cstmrName }">disabled="disabled"</c:if>>
                            <label class="c-form__label" for="cstmrName">이름</label>
                        </div>
                    </div>
                    <div class="c-form-field">
                        <div class="c-form__input-group">
                            <div class="c-form__input c-form__input-division">
                                <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');">
                                <span>-</span>
                                <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="off" type="password" maxlength="7"  placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                                <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                            </div>
                        </div>
                    </div>
                    <div class="c-form">
                        <div class="c-form__input">
                            <input class="c-input onlyNum" id="cstmrMobile" type="tel" name="cstmrMobile" value="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력">
                            <label class="c-form__label" for="cstmrMobile">휴대폰</label>
                        </div>
                    </div>
                    <div class="esim-txtbox">
                        <ul class="esim-txtbox__list">
                            <li class="esim-txtbox__item">워치 개통 시 <span class="u-co-red">인증하신 휴대폰 번호로 납부금액이 통합 청구</span>됩니다.</li>
                            <li class="esim-txtbox__item">워치 셀프개통은 현재 사용중인 회선의 납부 방법이 신용카드/은행계좌 자동이체인 경우에만 가능합니다.</li>
                            <li class="esim-txtbox__item">
                               	현재 사용중인 회선의 납부 방법이 ‘지로' 일 경우 변경 신청 후 변경 적용되는 익월에 셀프개통을 진행하시거나 가입조건 선택 시 ‘상담사 개통 신청' 으로 진행 바랍니다.
                                <a class="c-text-link--bluegreen" href="/m/mypage/chargeView05.do" title="납부방법 변경 페이지 이동하기">납부방법 변경 바로가기 </a>
                            </li>
							<c:if test="${cstmrName ne null and !empty cstmrName }">
								<li class="esim-txtbox__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
							</c:if>
                        </ul>
                    </div>
                    <div class="btn-usercheck">
                        <button class="c-button" href="javascript:void(0);"  id="btnAuthCstmr" >고객인증</button>
                    </div>
                </div>

                <div class="_Step2" style="display:none;">

                    <h4 class="watch-join__title">휴대폰(워치) 등록</h4>
                    <hr class="c-hr">
                    <div class="esim-txtbox">
                        <ul class="esim-txtbox__list" id="">
                        	<li class="esim-txtbox__item">워치 고유번호를 확인해 주세요. 워치 개통을 위해서는 휴대폰(워치)의 등록이 선행되어야 합니다.</li>
                        	<li class="esim-txtbox__item">
                        		‘이미지 등록’ 버튼 클릭 후 캡쳐한 이미지를 업로드하거나 직접 휴대폰 정보를 입력 해 주세요.<br />
                        		<button class="c-text-link--bluegreen" data-dialog-trigger="#appleInfo-modal" title="애플 워치 정보 확인방법 보기">애플 워치 확인방법</button>
                        		<button class="c-text-link--bluegreen u-ml--6" data-dialog-trigger="#galaxyInfo-modal" title="갤럭시 워치 정보 확인방법 보기">갤럭시 워치 확인방법</button>
                        	</li>
                        	<li class="esim-txtbox__item">스캔(촬영)하기 사용 시 신청 중인 휴대폰에서 개통할 워치와 연결된 휴대폰의 EID, IMEI를 촬영 바랍니다.</li>
                        </ul>
                    </div>
                    <div class="c-button-wrap c-button-wrap--right _card u-mt--16" >
                        <button class="c-button _ocrEidRecord" data-type="init">
                            <span class="c-text c-text--type3 u-co-sub-4">스캔(촬영)하기</span>
                            <i class="c-icon c-icon--scan" aria-hidden="true"></i>
                        </button>
                    </div>
                    <div class="c-form u-mt--16">
						<div class="c-form__input-group ">
							<div class="c-form__input c-form__input-division">
								<input class="c-input--div" type="text" id="eid" name="eid" placeholder="EID" >
								<label class="c-form__label" for="eid">EID</label>
							</div>
						</div>
                        <div class="c-form__input-group ">
                            <div class="c-form__input c-form__input-division">
                                <input class="c-input--div" type="text" id="imei1" name="imei1" placeholder="IMEI" >
                                <label class="c-form__label" for="imei1">IMEI</label>
                            </div>
                        </div>
                    </div>
					<div class="esim-slot-box__err" id="errTxt" style="display:none;"></div>
					<div class="c-button-wrap u-mt--8">
						<label for="image" class="c-button c-button--full c-button--mint">이미지 등록</label>
						<input type="file" id="image" name="image" class="c-hidden ocrImg" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp">
					</div>

					<!-- PPT 3페이지 팝업 / 팝업 띄우는 용도라서 나중에 지워야 합니다 -->
					<!--button class="c-text-link--bluegreen u-mt--32" data-dialog-trigger="#esimInfoCheck">PPT 3페이지 팝업</button  -->

                    <div class="c-button-wrap u-mt--48">
                        <a class="c-button c-button--gray c-button--full" href="/m/appForm/eSimWatchInfo.do">취소</a>
                        <button class="c-button c-button--full c-button--primary" onclick="javascript:;" id="nextStep">다음</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 애플 워치 정보 확인방법 팝업 -->
        <div class="c-modal" id="appleInfo-modal" role="dialog" tabindex="-1" aria-describedby="appleInfo-modal__title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="appleInfo-modal__title">애플 워치 확인방법</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                    	<div class="watch-info-wrap u-mt--16">
                    		<div class="esim-slot-box">
								<ul class="esim-slot-box__list">
									<li>애플 사용자의 경우 EID/IMEI 이미지를 <span>하나씩 2번 업로드(또는 촬영)</span> 해 주시면 각 항목에 자동으로 입력됩니다.</li>
									<li>캡쳐(또는 촬영)이 어려우실 경우 화면 내 EID/IMEI 값을 직접 입력 해 주세요.</li>
								</ul>
							</div>
							<ul class="watch-guide__list--wrap">
								<li>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>1</em>
												아이폰-워치 연결 팝업 선택<br />아이폰에 팝업이 나타나지 않을 경우 Watch(워치) 앱 선택
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>2</em>
												아이폰과 애플워치 연결<br />연결 완료 후 이용약관 동의, 암호생성 등 진행
											</span>
										</div>
									</div>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_01.png">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_02.png">
										</div>
									</div>
								</li>
								<li>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>3</em>
												애플워치 연동<br />노란 네모박스에 맞춰 워치 촬영
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>4</em> 페어링 후
												사용자 설정 진행, 손목 선택, 이용약관 동의 등 진행
											</span>
										</div>
									</div>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_03.png">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_04.png">
										</div>
									</div>
								</li>
								<li>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>5</em>
												<span class="u-co-red">셀룰러 설정<br />지금 안함</span> 선택
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>6</em>
												페어링 완료<br />IMEI/EID 확인을 위해 워치 앱 실행
											</span>
										</div>
									</div>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_05.png">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_06.png">
										</div>
									</div>
								</li>
								<li>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>7</em>
												IMEI/EID 확인<br />일반 > 정보에서 확인
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>8</em>
												IMEI<br />일반 > 정보에서 단말정보 화면 캡쳐
											</span>
										</div>
									</div>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_07.png">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_08.png">
										</div>
									</div>
								</li>
								<li>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>9</em>
												EID 선택<br />EID 화면 캡쳐
											</span>
										</div>
									</div>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_09.png">
										</div>
									</div>
								</li>
							</ul>
						</div>
                    </div>
                    <div class="c-modal__footer">
				        <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
				    </div>
                </div>
            </div>
        </div>

        <!-- 갤럭시 워치 정보 확인방법 팝업 -->
        <div class="c-modal" id="galaxyInfo-modal" role="dialog" tabindex="-1" aria-describedby="galaxyInfo-modal__title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="galaxyInfo-modal__title">갤럭시 워치 확인방법</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="watch-info-wrap u-mt--16">
                        	<div class="esim-slot-box">
								<ul class="esim-slot-box__list">
									<li>캡쳐(또는 촬영)이 어려우실 경우 화면 내 EID/IMEI 값을 직접 입력 해 주세요.</li>
								</ul>
							</div>
							<ul class="watch-guide__list--wrap">
								<li>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>1</em>
												Galaxy Wearable 설치<br />모단말(안드로이드폰)에서 플레이 스토어를 접속하여 Galaxy Wearable 어플리케이션을 설치
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>2</em>
												- ① 모단말-워치 블루투스 연결<br />앱 시작 시 자동 연결 또는 앱 상단 메뉴를 클릭하여 “새 기기 추가” 선택
											</span>
										</div>
									</div>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/galaxy_watch_guide_01.png" alt="Galaxy Wearable 설치, 모단말(안드로이드폰)에서 플레이 스토어를 접속하여 Galaxy Wearable 어플리케이션을 설치">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/galaxy_watch_guide_02.png" alt="2-1 모단말-워치 블루투스 연결, 앱 시작 시 자동 연결 또는 앱 상단 메뉴를 클릭하여 “새 기기 추가” 선택">
										</div>
									</div>
								</li>
								<li>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>2</em>
												- ② 모단말-워치 블루투스 연결<br />워치에 보이는 모델명을 모단말에서 선택하여 블루투스 연결
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2"> <em>2</em>
											 - ③ 모단말-워치 블루투스 연결<br />모단말과 워치에 표시된 인증번호가 동일할 경우 확인 선택
											</span>
										</div>
									</div>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/galaxy_watch_guide_03.png" alt="2-2 모단말-워치 블루투스 연결 워치에 보이는 모델명을 모단말에서 선택하여 블루투스 연결">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/galaxy_watch_guide_04.png" alt="2-3 모단말-워치 블루투스 연결, 모단말과 워치에 표시된 인증번호가 동일할 경우 확인 선택">
										</div>
									</div>
								</li>
								<li>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>3</em>
												워치 정보 확인<br />Galaxy Wearable 어플의 워치설정 > 워치 정보 > 상태 정보 선택
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>4</em>
												상태 정보<br />화면 캡쳐
											</span>
										</div>
									</div>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/galaxy_watch_guide_05.png" alt="워치 정보 확인 Galaxy Wearable 어플의 워치설정 > 워치 정보 > 상태 정보 선택">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/galaxy_watch_guide_06.png"	 alt="상태 정보 화면 캡쳐">
										</div>
									</div>
								</li>
							</ul>
						</div>
                    </div>
                    <div class="c-modal__footer">
				        <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
				    </div>
                </div>
            </div>
        </div>

		<!-- esim 정보 체크 -->
		<div class="c-modal" id="esimInfoCheck" role="dialog" aria-describedby="esimInfoCheck_title">
			<div class="c-modal__dialog" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="esimInfoCheck_title">EID/IMEI 정보를 확인해주세요.</h1>
						<button class="c-button" data-dialog-close="">
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body u-pt--0">
						<div class="c-box c-box--type1 u-pa--16">
							<ul class="c-text-list c-bullet c-bullet--dot">
								<li class="c-text-list__item u-mt--0">입력되지 않은 항목에 대해 추가로 촬영을 원하실 경우 <span class="u-co-red">[계속 촬영하기]</span>를 통해 다시 촬영해 주세요.</li>
								<li class="c-text-list__item">직접 입력을 원하실 경우 [확인]을 눌러주세요.</li>
							</ul>
						</div>
						<div class="esim-join-wrap u-mt--0">
							<div class="esim-product-box">
			                    <div class="esim-product__item">
			                        <span class="esim-product__lable--iphone u-mb--8">EID</span>
			                        <div class="esim-product__model">
			                            <b class="u-fs-14" id="layerEid" >-</b>
			                        </div>
			                    </div>
			                    <div class="esim-product__item">
			                        <span class="esim-product__lable--iphone u-mb--8">IMEI</span>
			                        <div class="esim-product__model">
			                            <b id="layerImei" >-</b>
			                        </div>
			                    </div>
			                </div>
		                </div>
						<div class="c-button-wrap">
				            <button class="c-button c-button--full c-button--gray" data-dialog-close >확인</button>
				            <button class="c-button c-button--full c-button--primary _ocrEidRecord"  data-type="retry" >계속 촬영하기</button>
				        </div>
					</div>
				</div>
			</div>
		</div>









	</t:putAttribute>
</t:insertDefinition>