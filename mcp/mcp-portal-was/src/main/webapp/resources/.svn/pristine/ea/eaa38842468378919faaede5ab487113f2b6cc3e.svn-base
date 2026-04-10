// 메인 팝업 > 오늘 하루 그만 보기
$(document).ready(function(){
  let isPopup = $("#isPopup").val() || "N";

  if(isPopup === "Y"){
      let now = new Date().getTime();
      let hidePopupTime = localStorage.getItem("hideNoticePopup");

      // 저장된 값이 없거나, 저장된 시간이 이미 지났으면 팝업 표시
      if (!hidePopupTime || now > parseInt(hidePopupTime)) {
        fn_layerPop('noticeInfo', 1040, 1800); // 팝업 열기
      }

      // 닫기 버튼
      $('.notice_cancel').click(function(){
        $(".popup_cancel").trigger("click");
      });

      // "오늘 하루 안 보기" 버튼 클릭 시
      $("#todayStop").click(function() {
        let tomorrow = new Date();
        tomorrow.setHours(24, 0, 0, 0); // 자정까지 남은 시간 설정

        localStorage.setItem("hideNoticePopup", tomorrow.getTime()); // localStorage에 저장
        $(".popup_cancel").trigger("click"); // 팝업 닫기
      });
  }

});

// 화면크기 변경될떄 팝업 닫기
/*window.addEventListener('resize', () => {
  document.querySelectorAll(".popup_cancel").forEach(el => el.click());
});
*/


//메인 배너 페이지 로드 후 첫 all-box 애니메이션
const allBox = document.querySelector(".all-box");
window.addEventListener("load", () => {
 setTimeout(() => {
     allBox.classList.add("active");
 }, 300);
});
//메인 배너
const mainSwiper = new Swiper(".main-swiper", {
 parallax: true,
 loop: true,
 autoplay: {
   delay: 8000,
   disableOnInteraction: false
 },
 pagination: {
   el: ".main-swiper .swiper-pagination",
   clickable: true,
   type: "bullets",
   renderBullet: function (index, className) {
       return '<span class="' + className + '">' + String(index + 1).padStart(2, '0') + "</span>";
   }
 },
 navigation: {
   nextEl: ".main-swiper .swiper-button-next",
   prevEl: ".main-swiper .swiper-button-prev"
 },
 keyboard: {
     enabled: true,
     onlyInViewport: true,
 },
 on: {
     slideChange: function () {
         const activeIndex = this.activeIndex;  // Swiper 인스턴스를 통해 activeIndex 가져오기
         const slides = document.querySelectorAll(".swiper-slide");
         const activeSlide = slides[activeIndex];
         const gnbMenu = document.querySelector("#gnbMenu");
         const colorType = document.querySelector('.main-banner');


         if (activeSlide.classList.contains("white")) {
             gnbMenu.classList.add("white");
             colorType.classList.add("white");
         } else {
             gnbMenu.classList.remove("white");
             colorType.classList.remove("white");
         };
     },

     // all-box 애니메이션
     slideChangeTransitionStart: function () {
         allBox.classList.remove("active");
     },
     slideChangeTransitionEnd: function () {
         const allBox = document.querySelector(".all-box");
         setTimeout(() => {
             allBox.classList.add("active");
         }, 50);
     }
 }
});
//일시정지 / 재생 기능
const playPauseBtn = document.getElementById("playPauseBtn");
const mainBanner = document.querySelector(".main-banner");

let isPlaying = true; // 초기 상태: 자동 재생
let startTime = performance.now(); // 슬라이드 시작 시간
const slideDuration = 8000; // 기본 슬라이드 지속 시간 (8초)
let remainingTime = slideDuration; // 남은 시간 (초기값 8초)
let autoplayTimeout = null; // 직접 컨트롤할 타이머

playPauseBtn.addEventListener("click", function () {
 if (isPlaying) {
     //  현재까지 경과한 시간 계산
     let elapsedTime = performance.now() - startTime;

     // 이전 남은 시간에서 추가로 차감
     remainingTime = Math.max(remainingTime - elapsedTime, 0);

     //  Swiper 자동 재생 멈추기
     mainSwiper.autoplay.stop();
     clearTimeout(autoplayTimeout); // 기존 타이머 제거

     // 🎨 UI 변경
     playPauseBtn.innerHTML = '<i class="c-icon c-icon--play" aria-hidden="true"></i>';
     playPauseBtn.classList.add("play");
     mainBanner.classList.add("paused"); // CSS 애니메이션 정지
 } else {
     clearTimeout(autoplayTimeout); // 🔄 기존 타이머 제거 (중복 방지)

     // 남은 시간 동안 기다렸다가 슬라이드 넘기기
     autoplayTimeout = setTimeout(() => {
         mainSwiper.slideNext(); // 남은 시간 후 슬라이드 전환
         mainSwiper.autoplay.start(); // 다음 슬라이드부터 자동 재생
         startTime = performance.now(); // 새로운 시작 시간 저장
         remainingTime = slideDuration; // 다시 8초로 초기화
     }, remainingTime);

     //  UI 변경
     playPauseBtn.innerHTML = '<i class="c-icon c-icon--pause" aria-hidden="true"></i>';
     playPauseBtn.classList.add("pause");
     mainBanner.classList.remove("paused"); // CSS 애니메이션 재개

     startTime = performance.now(); // 다시 시작 시간 저장
 }

 isPlaying = !isPlaying; // 상태 변경
});
