<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">워치 가입 신청</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/validator.js?version=23.02.06"></script>
        <script type="text/javascript" src="/resources/js/portal/appForm/eSimWatch.js?version=24.12.17"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">워치 가입 신청</h2>
            </div>

            <div id="divStep1" class="watch-join-wrap">
                <div class="user-confirm">
                    <h4 class="watch-join__title" id="eSimeWatchInfo">kt M모바일 고객인증</h4>
                    <hr class=" c-hr">
                    <div class="c-form-group" role="group" aira-labelledby="eSimeWatchInfo">
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input u-mt--0">
                                    <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" value="${cstmrName}"  maxlength="60" <c:if test="${cstmrName ne null and !empty cstmrName }">disabled="disabled"</c:if>>
                                    <label class="c-form__label" for="cstmrName">이름</label>
                                </div>
                            </div>
                            <div class="c-form-field _isDefault">
                                <div class="c-form__input-group u-mt--0">
                                      <div class="c-form__input c-form__input-division">
                                           <!-- 주민등록번호 입력 부분 -->
                                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" type="text" autocomplete="false" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                                        <span>-</span>
                                           <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="" >
                                           <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                                      </div>
                                </div>
                            </div>
                        </div>
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input-group _isDefaultMt">
                                    <div class="c-form__input c-form__input-division">
                                        <input class="c-input--div3 onlyNum" id="cstmrMobileFn" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리" onkeyup="nextFocus(this, 3, 'cstmrMobileMn');"  >
                                        <span>-</span>
                                        <input class="c-input--div3 onlyNum" id="cstmrMobileMn" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 중간자리" onkeyup="nextFocus(this, 4, 'cstmrMobileRn');"  >
                                        <span>-</span>
                                        <input class="c-input--div3 onlyNum" id="cstmrMobileRn" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 뒷자리" onkeyup="nextFocus(this, 4, 'btnAuthCstmr');" >
                                        <label class="c-form__label" for="cstmrMobileFn">휴대폰</label>
                                    </div>
                                </div>
                            </div>
                            <div class="c-form-field"></div>
                        </div>
                    </div>
                    <div class="esim-txtbox">
                        <ul class="esim-txtbox__list">
                            <li class="esim-txtbox__item">워치 개통 시 <span class="u-co-red">인증하신 휴대폰 번호로 납부금액이 통합 청구</span>됩니다.</li>
                            <li class="esim-txtbox__item">워치 셀프개통은 현재 사용중인 회선의 납부 방법이 신용카드/은행계좌 자동이체인 경우에만 가능합니다.</li>
                            <li class="esim-txtbox__item">
                               	현재 사용중인 회선의 납부 방법이 ‘지로' 일 경우 변경 신청 후 변경 적용되는 익월에 셀프개통을 진행하시거나 가입조건 선택 시 ‘상담사 개통 신청' 으로 진행 바랍니다.
                                <a class="c-text-link--bluegreen" href="/mypage/chargeView05.do" title="납부방법 변경 페이지 이동하기">납부방법 변경 바로가기 </a>
                            </li>
							<c:if test="${cstmrName ne null and !empty cstmrName }">
								<li class="esim-txtbox__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
							</c:if>
                        </ul>
                    </div>
                    <div class="btn-usercheck">
                        <button type="button"  href="javascript:void(0);"  id="btnAuthCstmr" >고객인증</button>
                    </div>
                </div>
            </div>

            <div id="divStep2" class="watch-join-wrap" style="display: none">

                <h4 class="watch-join__title">휴대폰(워치) 등록</h4>
                <hr class="c-hr">
                <div class="esim-txtbox">
                    <ul class="esim-txtbox__list" >
                    	<li class="esim-txtbox__item">워치 고유번호를 확인해 주세요. 워치 개통을 위해서는 휴대폰(워치)의 등록이 선행되어야 합니다.</li>
                    	<li class="esim-txtbox__item">
                    		‘이미지 등록’ 버튼 클릭 후 캡쳐한 휴대폰 정보 이미지를 업로드하거나 직접 휴대폰 정보를 입력 해 주세요.<br />
                    		<button class="c-text-link--bluegreen" data-dialog-trigger="#appleInfo-modal" title="애플 워치 정보 확인방법 보기">애플 워치 확인방법</button>
                    		<button class="c-text-link--bluegreen" data-dialog-trigger="#galaxyInfo-modal" title="갤럭시 워치 정보 확인방법 보기">갤럭시 워치 확인방법</button>
                    	</li>
                    </ul>
                </div>
                <div class="c-form-wrap u-mt--32">
                    <div class="c-form-group" role="group" aira-labelledby="eSimeWatchReg">
                        <div class="c-form-row c-col2">
                            <div class="c-form__input-group u-mt--0">
                                <div class="c-form__input c-form__input-division">
                                    <input class="c-input--div" id="eid" name="eid" type="text" placeholder="EID" >
                                    <label class="c-form__label" for="eid">EID</label>
                                </div>
                            </div>
                            <div class="c-form__input-group u-mt--0">
                                <div class="c-form__input c-form__input-division">
                                    <input class="c-input--div" id="imei1" name="imei1" type="text" placeholder="IMEI" >
                                    <label class="c-form__label" for="imei1">IMEI</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="esim-slot-box__err" id="errTxt" style="display:none;"></div>

                <div class="c-button-wrap u-mt--32">
                    <label for="image" class="c-button c-button-round--md c-button--mint u-width--460">이미지 등록</label>
                    <input type="file" class="c-hidden ocrImg" id="image" name="image" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp" >
                </div>

                <div class="c-button-wrap u-mt--64">
                    <a class="c-button c-button--lg c-button--gray u-width--220" href="/appForm/eSimWatchInfo.do">취소</a>
                    <button class="c-button c-button--lg c-button--primary u-width--220" onclick="javascript:;" id="nextStep">다음</button>
                </div>
            </div>
        </div>





        <!-- 애플 워치 정보 확인방법 팝업 -->
        <div class="c-modal c-modal--xlg" id="appleInfo-modal" role="dialog" aria-labelledby="appleInfo-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="appleInfo-modal__title">애플 워치 확인방법</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
						<div class="watch-info-wrap" style="padding: 0 2.5rem;">
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
												<em>1</em> 아이폰-워치
												연결 팝업 선택<br />아이폰에 팝업이 나타나지 않을 경우<br />Watch(워치) 앱 선택
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>2</em> 아이폰과
												애플워치 연결<br />연결 완료 후 이용약관 동의, 암호생성 등 진행
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>3</em> 애플워치 연동<br />노란
												네모박스에 맞춰 워치 촬영
											</span>
										</div>
									</div>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_01.png"	alt="아이폰-워치 연결 팝업 선택, 아이폰에 팝업이 나타나지 않을 경우 Watch(워치) 앱 선택">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_02.png" alt="아이폰과 애플워치 연결, 연결 완료 후 이용약관 동의, 암호생성 등 진행">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_03.png" alt="애플워치 연동, 노란 네모박스에 맞춰 워치 촬영">
										</div>
									</div>
								</li>
								<li>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>4</em>
												페어링 후 사용자 설정 진행<br />손목 선택, 이용약관 동의 등 진행
											</span>
										</div>
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
											<img src="/resources/images/common/apple_watch_guide_04.png" alt="페어링 후 사용자 설정 진행, 손목 선택, 이용약관 동의 등 진행">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_05.png" alt="셀룰러 설정, 지금 안함 선택">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_06.png" alt="페어링 완료, IMEI/EID 확인을 위해 워치 앱 실행">
										</div>
									</div>
								</li>
								<li>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>7</em>
												IMEI/EID	확인<br />일반 > 정보에서 확인
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>8</em> IMEI<br />일반 > 정보에서 단말정보 화면 캡쳐
											</span>
										</div>
										<div class="watch-guide__list__item">
											<span class="Number-label--type2">
												<em>9</em>
												EID 선택<br />EID 화면 캡쳐
											</span>
										</div>
									</div>
									<div class="watch-guide__list--group">
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_07.png" alt="IMEI/EID 확인, 일반 > 정보에서 확인">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_08.png" alt="IMEI 일반 > 정보에서 단말정보 화면 캡쳐">
										</div>
										<div class="watch-guide__list__item">
											<img src="/resources/images/common/apple_watch_guide_09.png" alt="EID 선택, EID 화면 캡쳐">
										</div>
									</div>
								</li>
							</ul>
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

        <!-- 갤럭시 워치 정보 확인방법 팝업 -->
        <div class="c-modal c-modal--xlg" id="galaxyInfo-modal" role="dialog" aria-labelledby="galaxyInfo-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="galaxyInfo-modal__title">갤럭시 워치 확인방법</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
						<div class="watch-info-wrap" style="padding: 0 2.5rem;">
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
	                                    		- ① 모단말-워치 블루투스 연결<br />앱 시작 시 자동 연결 또는 앱 상단 메뉴를<br />클릭하여 “새 기기 추가” 선택
	                                        </span>
	                                    </div>
	                                    <div class="watch-guide__list__item">
	                                    	<span class="Number-label--type2">
	                                        	<em>2</em>
	                                    		- ② 모단말-워치 블루투스 연결<br />워치에 보이는 모델명을 모단말에서 선택하여<br />블루투스 연결
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
	                                    <div class="watch-guide__list__item">
                       		 	            <img src="/resources/images/common/galaxy_watch_guide_03.png" alt="2-2 모단말-워치 블루투스 연결 워치에 보이는 모델명을 모단말에서 선택하여 블루투스 연결">
	                                    </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="watch-guide__list--group">
	                                    <div class="watch-guide__list__item">
	                                        <span class="Number-label--type2">
	                                            <em>2</em>
	                                         	- ③ 모단말-워치 블루투스 연결<br />모단말과 워치에 표시된 인증번호가 동일할<br />경우 확인 선택
	                                        </span>
	                                   </div>
	                                   <div class="watch-guide__list__item">
	                                   	   <span class="Number-label--type2">
	                                           <em>3</em>
	                                                      워치 정보 확인<br />Galaxy Wearable 어플의 워치설정 > 워치<br />정보 > 상태 정보 선택
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
	                                       <img src="/resources/images/common/galaxy_watch_guide_04.png" alt="2-3 모단말-워치 블루투스 연결, 모단말과 워치에 표시된 인증번호가 동일할 경우 확인 선택">
                                       </div>
	                                   <div class="watch-guide__list__item">
	                                       <img src="/resources/images/common/galaxy_watch_guide_05.png" alt="워치 정보 확인 Galaxy Wearable 어플의 워치설정 > 워치 정보 > 상태 정보 선택">
	                                   </div>
	                                   <div class="watch-guide__list__item">
	                                       <img src="/resources/images/common/galaxy_watch_guide_06.png" alt="상태 정보 화면 캡쳐">
	                                   </div>
                                   </div>
                               </li>
                           </ul>
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


    </t:putAttribute>
</t:insertDefinition>
