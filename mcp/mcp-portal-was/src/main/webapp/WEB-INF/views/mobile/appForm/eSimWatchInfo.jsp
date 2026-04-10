<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefaultTitle">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/appForm/eSimInfo.js?version=2004-08-02"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    워치 개통 신청
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="watch-banner">
                <div class="watch-banner__wrap">
                    <strong>엠모바일 사용고객 한정!<br />이젠 워치도 저렴하게 사용하자!</strong>
                    <a href="/m/appForm/eSimWatch.do">
                        워치 신청서 작성
                        <i class="c-icon c-icon--anchor-black" aria-hidden="true"></i>
                    </a>
                </div>
                <div class="watch-banner__image">
                    <img src="/resources/images/mobile/esim/watch_banner.png" alt="">
                </div>
            </div>
            <div class="watch-info-wrap">
                <div class="watch-step__title">
                    <div class="watch-step__head">
                        STEP1 <span>신청 준비</span>
                    </div>
                    <div class="watch-step__sub">워치 개통 시 하기 사항을 사전에 확인/준비 해 주세요.</div>
                </div>
                <h3 class="watch-info__title">사용중인 kt M모바일 휴대폰</h3>
                <hr class="c-hr">
                <p>워치 개통을 위해서는 kt M모바일에 동일 명의로 가입 되어 있는 회선이 필요합니다.</p>
                <div class="watch-info__table">
                    <div class="c-table">
                        <table>
                            <caption>구분, 이용기준을 포함한 워치 원넘버 부가서비스 정보</caption>
                            <colgroup>
                                <col style="width: 6rem">
                                <col>
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">구분</th>
                                    <th scope="col">이용기준</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>애플 워치</td>
                                    <td class="u-ta-left">
                                        kt M모바일 회선과 애플워치 함께 이용(동일명의)<br />
                                        원넘버 부가서비스 자동 가입(무료)
                                    </td>
                                </tr>
                                <tr>
                                    <td>갤럭시 워치</td>
                                    <td class="u-ta-left">
                                        kt M모바일 회선과 갤럭시 워치 함께 이용(동일명의)<br />
                                        원넘버 부가서비스 선택(고객센터를 통해 가입 후 이용 가능/무료)
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <p>※ 원넘버 부가서비스 : 스마트 워치에서도 휴대폰과 같은 번호로 통화 및 문자메시지를 발신하고 휴대폰 번호로 오는 전화를 워치에서도 동시에 수신 가능하게 하는 서비스</p>
                        <p>※ 휴대폰 회선의 제공량을 워치에서 공유하여 사용하기를 원하실 경우 고객센터를 통해 신청 바랍니다. (스마트워치 요금제 상품소개 참조)</p>
                    </div>
                </div>
                 <h3 class="watch-info__title">휴대폰(워치) 정보 등록</h3>
                <hr class="c-hr">
                <p>
                    워치 고유번호를 확인해 주세요. 워치 개통을 위해서는 휴대폰(워치)의 등록이 선행되어야 합니다.<br />
                    신청 시 캡쳐한 휴대폰 정보 이미지를 업로드하거나 직접 휴대폰 정보를 입력 해 주세요.
                </p>
                <ul class="watch-info__list--fyr u-co-mint">
                    <li>신청 전 캡쳐 이미지를 먼저 준비 바랍니다.</li>
                </ul>
                <div class="c-accordion c-accordion--type1">
                    <ul class="c-accordion__box" id="eSIM-accordion">
                        <li class="c-accordion__item">
                            <div class="c-accordion__head" data-acc-header>
                                <button class="c-accordion__button" type="button" aria-expanded="false">애플 워치 가이드</button>
                            </div>
                            <div class="c-accordion__panel expand">
                                <div class="c-accordion__inside">
                                    <ul class="watch-guide__list--wrap">
                                        <li>
                                            <div class="watch-guide__list--group">
                                                <div class="watch-guide__list__item">
                                                    <span class="Number-label--type2">
                                                        <em>1</em>
                                                          아이폰-워치 연결 팝업 선택<br/>아이폰에 팝업이 나타나지 않을 경우 Watch(워치) 앱 선택
                                                    </span>
                                                </div>
                                                <div class="watch-guide__list__item">
                                                    <span class="Number-label--type2">
                                                        <em>2</em>
                                                          아이폰과 애플워치 연결<br/>연결 완료 후 이용약관 동의, 암호생성 등 진행
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
                                                          애플워치 연동<br/>노란 네모박스에 맞춰 워치 촬영
                                                    </span>
                                                </div>
                                                <div class="watch-guide__list__item">
                                                    <span class="Number-label--type2">
                                                        <em>4</em>
                                                           페어링 후 사용자 설정 진행<br/>손목 선택, 이용약관 동의 등 진행
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
                        </li>
                        <li class="c-accordion__item">
                            <div class="c-accordion__head" data-acc-header>
                                <button class="c-accordion__button" type="button" aria-expanded="false">갤럭시 워치 가이드</button>
                            </div>
                            <div class="c-accordion__panel expand">
                                <div class="c-accordion__inside">
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
                                                    <span class="Number-label--type2">
                                                        <em>2</em>
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
                                                    <img src="/resources/images/common/galaxy_watch_guide_06.png" alt="상태 정보 화면 캡쳐">
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="watch-step__title">
                    <div class="watch-step__head">
                        STEP2 <span>워치 셀프개통</span>
                    </div>
                    <div class="watch-step__sub">준비가 완료되시면 워치 개통을 시작해 보세요.</div>
                </div>
                <p class="esim-box__title">셀프개통 가능시간</p>
                <div class="esim-txtbox c-expand u-mt--16">
                    <p class="esim-txtbox__tit">외국인, 미성년자는 셀프개통이 불가하므로 ‘상담사 개통 신청’을 이용 바랍니다.</p>
                    <ul class="esim-txtbox__list">
                        <li class="esim-txtbox__item"><span class="u-co-black">신규가입 08:00~21:50</span> (워치는 번호이동으로 신청이 불가합니다.)</li>
                    </ul>
                </div>
                <p class="u-mt--28">휴대폰(워치) 정보 등록 실패 시 ‘상담사 개통 신청’ 으로 변경하여 진행 가능합니다.</p>
                <ul class="watch-info__list--fyr u-co-gray">
                    <li>‘상담사 개통 신청’ 으로 신청서를 작성하시면 순차적으로 상담사가 전화로 본인 확인 후 개통이 진행되며 지속 부재 시 자동으로 취소됩니다.</li>
                </ul>
                <p class="c-notice--red">
                    <span>
                        <i class="c-icon c-icon--bang--red" aria-hidden="true"></i>
                    </span>
                    <span>기기정보를 다시한번 확인해 주세요. 오등록 시 정상적으로 개통되지 않거나 eSIM 재다운로드가 필요할 수 있습니다. (eSIM 재 다운로드시 비용이 부됩니다.)</span>
                </p>
                <h5 class="watch-info__subtitle">워치 셀프개통 퀵 가이드</h5>
                <div class="embed-youtube u-mt--16">
                    <iframe src="https://www.youtube.com/embed/9Gq5AG0ezvE" title="알뜰폰 eSIM으로 쉽고 간편한 스마트워치 셀프개통!ㅣkt M모바일ㅣ서비스 퀵 가이드" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"></iframe>
                </div>
                <a id="btnWatchStart" class="c-button c-button--h48 c-button--mint c-button--full u-mt--38"  title="워치 신청서 작성 바로가기" >워치 셀프개통 시작 </a>
                <div class="watch-step__title" id="step3" >
                    <div class="watch-step__head">
                        STEP3 <span>eSIM 발급</span>
                    </div>
                    <div class="watch-step__sub">개통 완료 후 휴대폰에서 eSIM을 발급 해 주세요.</div>
                </div>
                <p class="c-notice--red u-mt--32">
                    <span>
                        <i class="c-icon c-icon--bang--red" aria-hidden="true"></i>
                    </span>
                    <span>반드시 단말기가 Wi-Fi 또는 셀룰러 망(5G/LTE)에 연결되어 있어야 합니다.</span>
                </p>
                <p class="u-mt--6">워치 셀프개통 시 eSIM 발급 비용 2,750원(VAT포함)이 청구됩니다.</p>
                <div class="c-accordion c-accordion--type1">
                    <ul class="c-accordion__box">
                        <li class="c-accordion__item">
                            <div class="c-accordion__head" data-acc-header>
                                <button class="c-accordion__button" type="button" aria-expanded="false">애플 워치 eSIM 발급</button>
                            </div>
                            <div class="c-accordion__panel expand">
                                <div class="c-accordion__inside">
                                    <ul class="watch-guide__list--wrap">
                                        <li>
                                            <div class="watch-guide__list--group">
                                                <div class="watch-guide__list__item">
                                                    <span class="Number-label--type2">
                                                        <em>1</em>
                                                          워치 앱 실행<br />워치 앱에서 셀룰러 선택
                                                    </span>
                                                </div>
                                                <div class="watch-guide__list__item">
                                                    <span class="Number-label--type2">
                                                        <em>2</em>
                                                          eSIM정보 다운로드<br />자동으로 정보가 다운로드되며 kt로고 옆 !표 확인 후 워치 사용
                                                    </span>
                                                </div>
                                            </div>
                                            <div class="watch-guide__list--group">
                                                <div class="watch-guide__list__item">
                                                    <img src="/resources/images/common/apple_watch_esim_01.png" alt="워치 앱 실행, 워치 앱에서 셀룰러 선택>">
                                                </div>
                                                <div class="watch-guide__list__item">
                                                    <img src="/resources/images/common/apple_watch_esim_02.png" alt="eSIM정보 다운로드, 자동으로 정보가 다운로드되며 kt로고 옆 !표 확인 후 워치 사용">
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                        <li class="c-accordion__item">
                            <div class="c-accordion__head" data-acc-header>
                                <button class="c-accordion__button" type="button" aria-expanded="false">갤럭시 워치 eSIM 발급</button>
                            </div>
                            <div class="c-accordion__panel expand">
                                <div class="c-accordion__inside">
                                    <ul class="watch-guide__list--wrap">
                                        <li>
                                            <div class="watch-guide__list--group">
                                                <div class="watch-guide__list__item">
                                                    <span class="Number-label--type2">
                                                        <em>1</em>
                                                          Galaxy Wearable 앱 실행<br />개통 완료 후 앱 > 워치 설정 > 모바일 요금제 선택
                                                    </span>
                                                </div>
                                                <div class="watch-guide__list__item">
                                                    <span class="Number-label--type2">
                                                        <em>2</em>
                                                          eSIM 정보 다운로드<br />자동으로 정보가 다운로드되며 완료 시 워치 사용
                                                    </span>
                                                </div>
                                            </div>
                                            <div class="watch-guide__list--group">
                                                <div class="watch-guide__list__item">
                                                    <img src="/resources/images/common/galaxy_watch_esim_01.png" alt="Galaxy Wearable 앱 실행, 개통 완료 후 앱>워치 설정>모바일 요금제 선택">
                                                </div>
                                                <div class="watch-guide__list__item">
                                                    <img src="/resources/images/common/galaxy_watch_esim_02.png" alt="eSIM 정보 다운로드, 자동으로 정보가 다운로드되며 완료 시 워치 사용">
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="c-notice-wrap">
                    <hr>
                    <h5 class="c-notice__title">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                        <span>eSIM 취급상의 유의사항</span>
                    </h5>
                    <ul class="c-notice__list">
                        <li>eSIM은 본체로부터 분리가 불가합니다.</li>
                        <li>휴대폰 분실/파손 또는 eSIM을 삭제한 경우 eSIM재발행(유상)이 필요합니다. eSIM의 재발행은 고객센터를 통해서만 가능합니다.</li>
                    </ul>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>