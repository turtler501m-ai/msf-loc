// AOS 애니메이션
document.addEventListener("DOMContentLoaded", function () {
    AOS.init();
});
// AOS 애니메이션 초기화
window.addEventListener("scroll", () => {
  AOS.refresh();
});

// 하단 플로팅 버튼(탑이동)
window.addEventListener("scroll", function () {
  const target = document.querySelector(".float-btn-wrap");
  if (window.scrollY > 500) {
    target.classList.add("active");
  } else {
    target.classList.remove("active");
  }
});

//GNB 책갈피 스크롤
document.addEventListener("DOMContentLoaded", function () {

    // 페이지 내 해시값에 맞는 위치로 스크롤 이동
    function adjustScrollPosition() {
        const targetSection = document.querySelector(window.location.hash);

        // aos 애니 삭제
        document.querySelectorAll('.aos-init').forEach(el => {
            el.removeAttribute('data-aos'); // aos 제거
        });

        if (targetSection) {
            const isMobile = window.innerWidth <= 768;
            const offsetValue = isMobile ? 170 : 190;
            const targetPosition = targetSection.offsetTop - offsetValue;

            setTimeout(() => {
                window.scrollTo({
                    top: targetPosition,
                    behavior: "smooth"
                });

                // aos 애니 복구
                document.querySelectorAll('.aos-init').forEach(el => {
                    el.setAttribute('data-aos', 'fade-up'); // 'fade-up' 추가
                });
            },20);
        }
    }

    // 페이지 내 해시값 변경 시 스크롤 조정 (해시값이 변경될 때마다 스크롤 조정)
    window.addEventListener("hashchange", function () {

        // aos 애니 삭제
        document.querySelectorAll('.aos-init').forEach(el => {
            el.removeAttribute('data-aos'); // aos 제거
        });

        // 해시값이 변경된 경우 스크롤 조정
        setTimeout(adjustScrollPosition, 10);

        // aos 애니 복구
        document.querySelectorAll('.aos-init').forEach(el => {
            el.setAttribute('data-aos', 'fade-up'); // 'fade-up' 추가
        });
    });

    // <a> 태그 클릭 시 (절대경로 + 해시 링크만 적용), 페이지 내에서 이동은 이걸로 동작
    document.querySelectorAll("#gnbMenu a[href*='#'], #gnbMenuAll a[href*='#']").forEach(anchor => {
        anchor.addEventListener("click", function (event) {

            // aos 애니 삭제
            document.querySelectorAll('.aos-init').forEach(el => {
                el.removeAttribute('data-aos'); // aos 제거
            });

            const hrefValue = this.getAttribute("href");

            // 상대경로 해시(#id) 링크는 무시
            if (hrefValue.startsWith("#")) return;

            // 기본 동작을 막고 페이지 이동 처리
            event.preventDefault();

            setTimeout(() => {
                location.href = hrefValue;
                setTimeout(adjustScrollPosition, 10);
            }, 300);

             // aos 애니 복구
            document.querySelectorAll('.aos-init').forEach(el => {
                el.setAttribute('data-aos', 'fade-up'); // 'fade-up' 추가
            });
        });
    });

    // 페이지가 로드될 때 해시값이 존재하면 스크롤 이동 처리, 페이지이동은 이걸로 동작
    if (window.location.hash) {

        // aos 애니 삭제
        document.querySelectorAll('.aos-init').forEach(el => {
            el.removeAttribute('data-aos'); // aos 제거
        });

        setTimeout(adjustScrollPosition, 10);

        // aos 애니 복구
        document.querySelectorAll('.aos-init').forEach(el => {
            el.setAttribute('data-aos', 'fade-up'); // 'fade-up' 추가
        });
    }

});


/* 패럴렉스 효과 */
window.addEventListener("scroll", function() {
    document.querySelectorAll(".c-parallax").forEach(function(el) {
      let speed = 0.1; // 패럴렉스 속도 증가
      let yPos = -(window.scrollY * speed - 125) + "px";
      el.style.backgroundPositionY = yPos;

      if (window.innerWidth <= 768) {
          let mobileSpeed = 0.4;
          let mobileYPos = -(window.scrollY * mobileSpeed - 350) + "px";
          el.style.backgroundPositionY = mobileYPos;
      }
    });
});


/* 탭 구조*/
document.querySelectorAll('.tab-button').forEach(button => {
  button.addEventListener('click', () => {
    const selectedTab = button.getAttribute('aria-controls');

    document.querySelectorAll('.tab-button').forEach(tab => {
      tab.setAttribute('aria-selected', 'false');
    });

    document.querySelectorAll('.tab-content').forEach(content => {
      content.hidden = true;
    });

    button.setAttribute('aria-selected', 'true');
    document.getElementById(selectedTab).hidden = false;
  });
});

/* 아코디언 */
document.querySelectorAll('.accordion button').forEach(button => {
    button.addEventListener('click', function () {
        const expanded = this.getAttribute('aria-expanded') === 'true';
        this.setAttribute('aria-expanded', !expanded);
        const content = document.getElementById(this.getAttribute('aria-controls'));
        if (expanded) {
            content.style.maxHeight = '0';
            content.classList.remove('open');
        } else {
            content.style.maxHeight = content.scrollHeight + 'px';
            content.classList.add('open');
        }
    });
});

//모달 접근성 시작
document.addEventListener("DOMContentLoaded", () => {
const modal = document.getElementById("reportFormInfo");
const openButton = document.getElementById("reportBtn");
const closeButton = document.getElementById("closeModal");
const confirmButton = document.getElementById("reportFormBtn");

if (!modal) {
    return;
}
if (!openButton || !closeButton || !confirmButton) {
    return;
}

const modalContainer = modal.querySelector(".pop-container"); // 내부 컨텐츠 영역
let lastFocusedElement = null; // 모달 닫힐 때 복구할 포커스
// 모달 내 모든 포커스 가능한 요소 가져오기
const focusableElements = () => {
 return modal.querySelectorAll("button, a, input, textarea, select");
};
// 모달 열기
function openModal() {
 lastFocusedElement = document.activeElement; // 현재 포커스 저장
 modal.style.display = "block";
 modal.setAttribute("aria-hidden", "false");
 modal.focus();
 closeButton.focus(); // 닫기 버튼에 포커스 이동
}
// 모달 닫기
function closeModal() {
 modal.style.display = "none";
 modal.setAttribute("aria-hidden", "true");
 if (lastFocusedElement) lastFocusedElement.focus(); // 원래 포커스 복귀
}
// 화면크기 변경될떄 팝업 닫기
//window.addEventListener('resize', () => {
//    document.querySelectorAll(".popup_cancel").forEach(el => el.click());
//});
// 키보드 트랩 설정 (Tab 키를 모달 내부에서만 이동)
function trapTabKey(event) {
 const focusable = Array.from(focusableElements());
 const firstElement = focusable[0];
 const lastElement = focusable[focusable.length - 1];

 if (event.key === "Tab") {
   if (event.shiftKey && document.activeElement === firstElement) {
     event.preventDefault();
     lastElement.focus();
   } else if (!event.shiftKey && document.activeElement === lastElement) {
     event.preventDefault();
     firstElement.focus();
   }
 }
}
// 키보드 이벤트 리스너 (Tab 키만 제어)
document.addEventListener("keydown", (event) => {
 // 각각의 모달에 대해 Tab 키 트랩
 trapTabKey(event, modal);
});
// 이벤트 리스너 추가
openButton.addEventListener("click", openModal);
closeButton.addEventListener("click", closeModal);
confirmButton.addEventListener("click", closeModal);
document.addEventListener("keydown", handleKeyDown);
modal.addEventListener("click", handleClickOutside);
});
// 모달 종료

//PC 메뉴 탭 이동
document.addEventListener("DOMContentLoaded", function () {
    const menuItems = document.querySelectorAll(".menu-pc .menu-item");
    menuItems.forEach(item => {
        const link = item.querySelector("a");
        const submenu = item.querySelector(".menu-pc .submenu");
        if (submenu) {
            // 탭 이동 시 서브메뉴 열리도록 처리
            link.addEventListener("focus", () => {
                item.classList.add("active");
            });
            // 서브메뉴 내의 마지막 요소에서 빠져나갈 때 닫기
            const submenuLinks = submenu.querySelectorAll("a");
            submenuLinks[submenuLinks.length - 1].addEventListener("blur", () => {
                item.classList.remove("active");
            });
            // 백탭 시 메뉴에서 벗어나면 서브메뉴 닫기
            item.addEventListener("focusout", (event) => {
                 if (!item.contains(event.relatedTarget)) {
                     item.classList.remove("active");
                 }
            });
        }
    });

    // 모바일 메뉴 탭이동  막기
    function setTabIndex() {
        const menuItems = document.querySelectorAll(".menu-mobile a");
        if (window.innerWidth > 768) {  // 모바일 환경
            menuItems.forEach(item => {
                item.setAttribute("tabindex", "-1");
            });
        }
    }
    window.addEventListener("resize", setTabIndex);
    setTabIndex();
});

//PC 전체 메뉴
function toggleMenuAll() {
 const menu = document.getElementById("gnbMenuAll");
 const overlay = document.getElementById("overlayPc");
 const body = document.body;

 menu.classList.toggle("all-menu");
 overlay.style.display = menu.classList.contains("all-menu") ? "block" : "none";
 body.classList.toggle("no-scroll", menu.classList.contains("all-menu"));

//메뉴 열릴 때 닫기버튼 포커스
 const menuButtonAll = document.querySelector('#gnbMenuAll .menu-button-all');
 const menuButtonGnbAll = document.querySelector('.menu-pc .menu-button-all');
 const menuTarget = document.querySelector('#gnbMenuAll');

 if (menuTarget && menuTarget.classList.contains("all-menu")) {
     if (menuButtonAll) {
         setTimeout(() => {
             menuButtonAll.focus();
         }, 100);
     }
 }
 // 전체메뉴 닫기 시 햄버거 버튼 포커스
 if (menuButtonAll && menuButtonGnbAll) {
     menuButtonAll.addEventListener('click', () => {
         setTimeout(() => {
             menuButtonGnbAll.focus();
         }, 100);
     });
 }

 // 전체 메뉴 높이 계산
 /*event.preventDefault();*/
 /*const submenu = document.querySelector('.menu-item .submenu');
 const gnbMenu = document.querySelector('#gnbMenuAll');
 const baseHeight = gnbMenu.scrollHeight - submenu.scrollHeight;
 gnbMenu.style.height = `${baseHeight + submenu.scrollHeight}px`;*/

 // 전체 메뉴에서만 탭이동
 // 포커스 가능한 모든 요소 찾기
 const focusableElements = () => {
   return menu.querySelectorAll("button, a, input, textarea, select");
 };
 // 키보드 트랩 설정 (Tab 키를 모달 내부에서만 이동)
 function trapTabKey(event) {
   const focusable = Array.from(focusableElements());
   const firstElement = focusable[0];
   const lastElement = focusable[focusable.length - 1];

   if (event.key === "Tab") {
     if (event.shiftKey && document.activeElement === firstElement) {
       event.preventDefault();
       lastElement.focus();
     } else if (!event.shiftKey && document.activeElement === lastElement) {
       event.preventDefault();
       firstElement.focus();
     }
   }
 }
 // 키보드 이벤트 리스너 (Tab 키만 제어)
 document.addEventListener("keydown", (event) => {
   // 각각의 모달에 대해 Tab 키 트랩
   trapTabKey(event, menu);
 });
}

// 전체메뉴 > 서브메뉴 선택 시 전체메뉴 비활성화
const menuAllSub = document.querySelectorAll('#gnbMenuAll .submenu a');
menuAllSub.forEach(link => {
  link.addEventListener('click', function(event) {
     const menuAll = document.getElementById("gnbMenuAll");
     menuAll.classList.remove("all-menu");
     const overlay = document.getElementById("overlayPc");
     overlay.style.display = "none";  // 오버레이도 숨김
     document.body.classList.remove("no-scroll");
  });
});


//플로팅 버튼 > 전체메뉴
function toggleMenuFloat() {
 const menu = document.getElementById("gnbMenuAll");
 const overlay = document.getElementById("overlayPc");
 const body = document.body;
 let scrollY = window.scrollY; // 현재 스크롤 위치 저장

 menu.classList.toggle("all-menu");
 overlay.style.display = menu.classList.contains("all-menu") ? "block" : "none";
 body.classList.toggle("no-scroll", menu.classList.contains("all-menu"));

 //메뉴 열릴 때 닫기버튼 포커스
 const menuButtonAll = document.querySelector('#gnbMenuAll .menu-button-all');
 const menuButtonGnbAll = document.querySelector('.float-btn--menu');
 const menuTarget = document.querySelector('#gnbMenuAll');

 if (menuTarget && menuTarget.classList.contains("all-menu")) {
     if (menuButtonAll) {
         setTimeout(() => {
             menuButtonAll.focus();
         }, 100);
     }
 }
 // 전체메뉴 닫기 시 햄버거 버튼 포커스
 if (menuButtonAll && menuButtonGnbAll) {
     menuButtonAll.addEventListener('click', () => {
         setTimeout(() => {
             menuButtonGnbAll.focus();
             window.scrollTo(0, scrollY); // 저장한 위치로 스크롤 복원
         }, 100);
     });
 }

 // 전체 메뉴 높이 계산
 /*event.preventDefault();*/
 /*const submenu = document.querySelector('.menu-item .submenu');
 const gnbMenu = document.querySelector('#gnbMenuAll');
 const baseHeight = gnbMenu.scrollHeight - submenu.scrollHeight;
 gnbMenu.style.height = `${baseHeight + submenu.scrollHeight}px`;*/

 // 전체 메뉴에서만 탭이동
 // 포커스 가능한 모든 요소 찾기
 const focusableElements = () => {
   return menu.querySelectorAll("button, a, input, textarea, select");
 };
 // 키보드 트랩 설정 (Tab 키를 모달 내부에서만 이동)
 function trapTabKey(event) {
   const focusable = Array.from(focusableElements());
   const firstElement = focusable[0];
   const lastElement = focusable[focusable.length - 1];

   if (event.key === "Tab") {
     if (event.shiftKey && document.activeElement === firstElement) {
       event.preventDefault();
       lastElement.focus();
     } else if (!event.shiftKey && document.activeElement === lastElement) {
       event.preventDefault();
       firstElement.focus();
     }
   }
 }
 // 키보드 이벤트 리스너 (Tab 키만 제어)
 document.addEventListener("keydown", (event) => {
   // 각각의 모달에 대해 Tab 키 트랩
   trapTabKey(event, menu);
 });
}

//화면 크기가 변할 때마다 PC 전체 메뉴 비활성화
window.addEventListener('resize', () => {
 const menuAll = document.getElementById("gnbMenuAll");
 if (window.innerWidth <= 1920) {
     menuAll.classList.remove("all-menu");// 화면이 작아지면 클래스를 해제
     const overlay = document.getElementById("overlayPc");
     overlay.style.display = "none";  // 오버레이도 숨김
 }
});

//모바일 메뉴바 상단 고정
const navbar = document.querySelector('nav');
function toggleSticky() {
  if (window.innerWidth <= 768) {
      if (window.scrollY > 0) {
          navbar.classList.add('sticky');
      } else {
          navbar.classList.remove('sticky');
      }
  } else {
      // 화면 크기가 768px 초과하면 sticky 제거
      navbar.classList.remove('sticky');
  }
}
//페이지 로드 시 체크
document.addEventListener('DOMContentLoaded', toggleSticky);
//스크롤 시 체크
window.addEventListener('scroll', toggleSticky);
//화면 크기 변경 시 체크
window.addEventListener('resize', toggleSticky);

//모바일 메뉴바 활성화
function toggleMenu() {
const menu = document.getElementById("mobileMenu");
const menuBtn = document.getElementById("mobileMenuBtn");
const overlay = document.getElementById("overlayMo");
const body = document.body;
menu.classList.toggle("open");
menuBtn.classList.toggle("open");
overlay.style.display = menu.classList.contains("open") ? "block" : "none";
body.classList.toggle("no-scroll");
}
// 서브메뉴 선택 후 메뉴바 비활성화
const subMenu = document.querySelectorAll('.menu-mobile .submenu a');
subMenu.forEach(link => {
    link.addEventListener('click', function(event) {
        toggleMenu();   // 'toggleMenu' 함수를 호출하여 메뉴 토글
    });
});


//모바일 서브메뉴 활성화
function toggleSubmenu(event) {
    event.preventDefault();
    const menuItem = event.currentTarget.parentElement;
    const allMenuItems = document.querySelectorAll('.menu-mobile .menu-item');

    allMenuItems.forEach(item => {
        if (item !== menuItem) {
            item.classList.remove('open');
        }
    });
    menuItem.classList.toggle('open');
}

//화면 크기가 변할 때마다 메뉴를 체크하여 자동으로 '모바일 all-menu' 클래스를 해제
window.addEventListener('resize', () => {
const menu = document.getElementById("mobileMenu");
const overlay = document.getElementById("overlayMo");
const navbar = document.querySelector('nav');
if (window.innerWidth > 768) {
    menu.classList.remove("open");
    navbar.classList.remove('sticky');
    overlay.style.display = "none";
}
});

//중단 네비 버튼 활성화
const buttons = document.querySelectorAll('.sub-gnb .sub-gnb__item');
const sections = document.querySelectorAll('section');
const scrollOffset = 170; // 추가할 여백

// 버튼 클릭 시 스크롤
buttons.forEach(button => {
    button.addEventListener('click', () => {
        const targetId = button.getAttribute('data-target');
        const targetSection = document.getElementById(targetId);

        // AOS 애니 제거
        document.querySelectorAll('.aos-init').forEach(el => {
            const aosValue = el.getAttribute('data-aos');
            el.setAttribute('data-aos-backup', aosValue);
            el.removeAttribute('data-aos');
        });

        if (targetSection) {
            const isMobile = window.innerWidth <= 768;
            const offsetValue = isMobile ? 170 : 190;

            const targetPosition = targetSection.getBoundingClientRect().top + window.scrollY - offsetValue;

            setTimeout(() => {
                window.scrollTo({
                  top: targetPosition,
                  behavior: 'smooth'
                });

                // AOS 애니 복구
                document.querySelectorAll('.aos-init').forEach(el => {
                    const backupValue = el.getAttribute('data-aos-backup');
                    if (backupValue !== null) {
                        el.setAttribute('data-aos', backupValue);
                        el.removeAttribute('data-aos-backup');
                    }
                });
            }, 300);
        }
    });
});

// 화면 높이 기준 기본 threshold 계산
let defaultThreshold;
const windowHeight = window.innerHeight;

if (windowHeight >= 1450) defaultThreshold = 0.65;
else if (windowHeight >= 1400) defaultThreshold = 0.6;
else if (windowHeight >= 1300) defaultThreshold = 0.55;
else if (windowHeight >= 1200) defaultThreshold = 0.5;
else if (windowHeight >= 1100) defaultThreshold = 0.45;
else if (windowHeight >= 1000) defaultThreshold = 0.4;
else if (windowHeight >= 900)  defaultThreshold = 0.35;
else if (windowHeight >= 800)  defaultThreshold = 0.3;
else if (windowHeight >= 700)  defaultThreshold = 0.25;
else if (windowHeight >= 650)  defaultThreshold = 0.25;
else if (windowHeight >= 600)  defaultThreshold = 0.23;
else if (windowHeight >= 500)  defaultThreshold = 0.1;
else if (windowHeight >= 400)  defaultThreshold = 0.25;
else defaultThreshold = 0.15;

// 섹션별 threshold 설정 (길이가 긴 섹션만 낮춤)
const sectionThresholds = {};
sections.forEach(section => {
    const ratio = section.offsetHeight / windowHeight;

    if (ratio > 4) sectionThresholds[section.id] = 0.03; // 매우 긴 섹션
    else if (ratio > 3) sectionThresholds[section.id] = 0.05; // 조금 긴 섹션
    else if (ratio > 2) sectionThresholds[section.id] = 0.1;
    else sectionThresholds[section.id] = defaultThreshold; // 나머지 섹션
});
// 버튼이 화면 밖으로 이동 시 위치변경
sections.forEach(section => {
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const activeButton = document.querySelector(`.sub-gnb .sub-gnb__item[data-target="${entry.target.id}"]`);

                // 모든 버튼 비활성화 후 활성화
                buttons.forEach(btn => btn.classList.remove('active'));
                activeButton?.classList.add('active');

                // 모바일에서 버튼이 화면 밖이면 이동
                if (window.innerWidth <= 768) {
                    const rect = activeButton.getBoundingClientRect();
                    if (rect.left < 0 || rect.right > window.innerWidth) {
                        activeButton.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'center' });
                    }
                }
            }
        });
    }, {
        threshold: sectionThresholds[section.id]
    });
    observer.observe(section);
});


// 숫자 카운팅
function countUp(element, target, delay = 0) {
    setTimeout(() => {
        const start = 0;
        const duration = 1000; // 고정 실행 시간
        const increment = (target - start) / (duration / 16); // 16ms per frame
        let current = start;
        const isDecimal = target % 1 !== 0; // 소수점 포함 여부 체크

        const timer = setInterval(() => {
            current += increment;
            if (current >= target) {
                current = target;
                clearInterval(timer);
            }
            element.textContent = isDecimal ? current.toFixed(1) : Math.floor(current);
        }, 16);
    }, delay); // data-aos-delay 값만 반영
}
function startCountingWhenVisible() {
    const counters = document.querySelectorAll('.counter');
    const observer = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const counter = entry.target;
                const target = parseFloat(counter.getAttribute('data-target'));

                // 부모 요소의 data-aos-delay 값 가져오기 (없으면 기본값 0)
                const parent = counter.closest("[data-aos-delay]");
                const delay = parent ? parseInt(parent.getAttribute("data-aos-delay"), 10) || 0 : 0;

                countUp(counter, target, delay); // data-aos-delay 값 적용
                /*observer.unobserve(counter);*/ // 한 번 실행되면 더 이상 실행되지 않도록 제거
            }
        });
    }, { threshold: 0.6 }); // 60% 이상 보이면 실행

    counters.forEach(counter => observer.observe(counter));
}
document.addEventListener("DOMContentLoaded", startCountingWhenVisible);
