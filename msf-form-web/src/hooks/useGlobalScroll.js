/**
 * [Utility] Global Scroll Controller
 * * 역할:
 * - 애플리케이션 내 주요 스크롤 영역(메인 레이아웃, 모달 등)의 인스턴스를 전역으로 관리
 * - 컴포넌트 간 깊은 계층 구조에 상관없이 어디서든 특정 영역의 스크롤을 제어(상단 이동 등)
 * * 사용법:
 * 1. 등록: 대상 컴포넌트(MsfCustomScroll 등)의 ref에 이 파일의 Ref 변수를 바인딩
 * 예: <MsfCustomScroll :ref="(el) => { mainScrollRef = el }">
 * * 2. 실행: 스크롤 제어가 필요한 곳에서 제공되는 유틸리티 함수를 호출
 * 예: import { pageScrollToTop } from '...' -> pageScrollToTop();
 */

import { ref, watch, nextTick } from 'vue'
import { useScrollLock } from '@/hooks/useScrollLock'

// 0. 전체공통 레이아웃 스크롤영역 (전체 레이아웃 에서 등록)
export const layoutScrollRef = ref(null)
// 1. MsfFormView 레이아웃 스크롤 영역 (MsfFormView.vue 에서 등록)
export const mainScrollRef = ref(null)
// 2. 모달 전용 스크롤 영역 (현재 활성화된 공통 모달에서 등록)
export const modalScrollRef = ref(null)

/**
 * 메인 페이지 스크롤을 최상단으로 이동
 * @param {string} behavior - 'smooth' (부드럽게) 또는 'auto' (즉시)
 */
// export const pageScrollToTop = (behavior = 'smooth') => {
//   mainScrollRef.value?.scrollTo({ top: 0, behavior })
// }
export const pageScrollToTop = (behavior = 'smooth') => {
  // DOM이 데이터 변경 사항을 반영해 새로 그려질 때까지 기다렸다가 실행
  nextTick().then(() => {
    requestAnimationFrame(() => {
      // 1순위: 특정 뷰(StepView 등)에 스크롤이 있으면 그걸 올린다.
      if (mainScrollRef.value?.scrollTo) {
        mainScrollRef.value.scrollTo({ top: 0, behavior })
      }
      // 2순위: 그게 없으면 기본 레이아웃 스크롤을 올린다.
      else if (layoutScrollRef.value?.scrollTo) {
        layoutScrollRef.value.scrollTo({ top: 0, behavior })
      }
      // 3순위: 등록된 스크롤 영역이 없으면 브라우저 기본 스크롤을 올린다.
      else {
        window.scrollTo({ top: 0, behavior })
      }
    })
  })
}

/**
 * 활성화된 모달 스크롤을 최상단으로 이동
 * @param {string} behavior - 'smooth' 또는 'auto'
 */
export const modalScrollToTop = (behavior = 'smooth') => {
  if (modalScrollRef.value?.scrollTo) {
    modalScrollRef.value.scrollTo({ top: 0, behavior })
  }
}

//  레이아웃 잠금 상태 (true면 잠금)
export const isLayoutLocked = ref(false)
/**
 * 레이아웃 스크롤 잠금 제어
 * @param {boolean} lock - true(잠금), false(해제)
 */
export const setLayoutLock = (lock) => {
  isLayoutLocked.value = lock
}

/**
 * [잠금 상태 동기화]
 * - 시스템의 잠금 신호(isLocked)를 레이아웃 스위치(isLayoutLocked)에 전달
 * - lock()이 어디선가 호출되면, 이 신호를 받는 모든 레이아웃용 스크롤 영역이 일괄 정지
 */
const { isLocked } = useScrollLock()
watch(isLocked, (newVal) => {
  isLayoutLocked.value = newVal
})
