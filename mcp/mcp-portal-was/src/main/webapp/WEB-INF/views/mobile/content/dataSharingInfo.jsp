<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="ly-page-sticky">
    <h2 class="ly-page__head">데이터쉐어링<button class="header-clone__gnb"></button>
    </h2>
</div>
<div class="data-sharing-banner">
    <div class="data-sharing-banner__wrap">
        <strong><span>넘치는 데이터, 쉐어링</span> 서비스로<br />자유롭게 나눠쓰세요!</strong>
        <button class="c-button" data-scroll-top=".data-sharing-form|100">가입 신청<i class="c-icon c-icon--anchor-black" aria-hidden="true"></i></button>
    </div>
    <div class="data-sharing-banner__image">
        <img src="/resources/images/mobile/content/img_data_sharing_banner.png" alt="">
    </div>
</div>
<h4 class="data-sharing-title">
    데이터쉐어링이란?
    <div class="data-sharing-title__button">
        <button id="btnDataSharingVideo" type="button" class="c-button" data-dialog-trigger="#dataSharingVideo"><i class="c-icon c-icon--play-type3" aria-hidden="true"></i>신청 방법 영상으로 보기 </button>
    </div>
</h4>
<p class="data-sharing__text">USIM을 추가 개통하여 메인 요금제의 기본제공 데이터를 다른 스마트기기와 나눠 쓸 수 있게 해 주는 서비스입니다.<br />추가 요금없이 활용이 가능합니다.(메인 회선 데이터에서 차감)</p>
<p class="data-sharing__text u-mt--8">※ 메인 회선이 5G 요금제를 사용하고 있을 경우 데이터쉐어링 가입이 불가능합니다.<br />데이터쉐어링 개통 신청을 위해서는 유심이 필요합니다. 신청 전 유심을 먼저 준비 해 주세요</p>
<div class="data-sharing__info">
    <img src="/resources/images/mobile/content/img_data_sharing_info.png" alt="">
</div>
<div class="data-sharing__text-box">
    <p><i class="c-icon c-icon--watch" aria-hidden="true"></i>셀프개통 가능시간 : 08:00 ~ 21:50</p>
    <ul class="c-text-list c-bullet c-bullet--dot">
        <li class="c-text-list__item">상담사 개통으로 신청하신 경우 신청서를 작성하시면 개통 상담사가 순차적으로 전화드려 개통을 도와드립니다.</li>
        <li class="c-text-list__item">외국인, 미성년자는 셀프개통이 불가하므로 ‘상담사 개통 신청’으로 신청 바랍니다.</li>
    </ul>
</div>
<div class="self-guide-wrap--type2 c-expand u-mt--64" id="swiperDataSharing">
    <div class="swiper-container">
        <div class="swiper-wrapper">
            <div class="swiper-slide">
                <img src="/resources/images/mobile/content/img_data_sharing_guide_01.png" alt="데이터쉐어링 가입/결합 방법 결합된 내역은 마이페이지 결합 내역 조회에서 확인이 가능합니다">
            </div>
            <div class="swiper-slide">
                <img src="/resources/images/mobile/content/img_data_sharing_guide_02.png" alt="1.가입정보확인, 로그인 또는 휴대폰 인증을 통해 메인 회선의 데이터쉐어링 가능 여부를 확인 해 보세요. 다이렉트몰 아이디로 로그인하고 간편하게 가입하세요.">
            </div>
            <div class="swiper-slide">
                <img src="/resources/images/mobile/content/img_data_sharing_guide_03.png" alt="2.결합 신청, 결합 가능 여부 확인 및 개통유형 선택 후 결합하기 버튼을 클릭 해 주세요. 쉐어링 가입을 위해 유심을 먼저 준비 해 주세요. 신청 전 USIM을 준비 해 주세요. ※ USIM 구매처는 하단 자주 묻는 질문에서 확인이 가능합니다.">
            </div>
            <div class="swiper-slide">
                <img src="/resources/images/mobile/content/img_data_sharing_guide_04.png" alt="3.셀프개통/결합, 본인인증, 유심번호를 입력 후 가입신청시 즉시 개통/결합이 진행됩니다. 사용하고 계신 회선과 같은 명의로 쉐어링 요금제가 개통/결합됩니다.">
            </div>
            <div class="swiper-slide">
                <img src="/resources/images/mobile/content/img_data_sharing_guide_05.png" alt="4 유심 장착, 사용하고자 하는 태블릿/기기에 유심을 장착 후 사용 해 주세요.">
            </div>
        </div>
        <div class="swiper-pagination"></div>
    </div>
</div>

<!-- 동영상 보기 팝업 -->
<div class="c-modal quickguide" id="dataSharingVideo" role="dialog" tabindex="-1" aria-describedby="dataSharingVideoTitle">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
        <div class="c-modal__content">
            <div class="c-modal__body">
                <div class="quickguide-box">
                    <i class="c-icon c-icon--reset" aria-hidden="true" data-dialog-close></i>
                    <iframe id="ifrmYoutobe" src="about:blank" title="알뜰폰 데이터쉐어링, 쉽고 간편하게 5분 완료!ㅣkt M모바일ㅣ서비스 퀵 가이드" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen="" style="position: relative;width: 100%;max-width: 940px;max-height: 526px;aspect-ratio: 16 / 9;margin: 0 auto;"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="/resources/js/mobile/vendor/swiper.min.js"></script>
<script>
    var selfOpeningSwiper = new Swiper('#swiperDataSharing .swiper-container', {
        pagination : {
            el : '.swiper-pagination',
            type: 'fraction',
        },
    });


    document.getElementById("btnDataSharingVideo").addEventListener("click", function () {
        document.getElementById("ifrmYoutobe").src = "https://www.youtube.com/embed/7-rB3pCL0Ow";
    });


</script>