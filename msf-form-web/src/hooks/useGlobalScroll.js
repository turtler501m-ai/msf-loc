/**
 * [Utility] Global Scroll Controller
 * * 역할:
 * - 애플리케이션 내 주요 스크롤 영역(메인 레이아웃, 모달 등)의 인스턴스를 전역으로 관리
 * - 컴포넌트 간 깊은 계층 구조에 상관없이 어디서든 특정 영역의 스크롤을 제어(상단 이동 등)
 * * 사용법:
 * 1. 등록: 대상 컴포넌트(CustomScroll 등)의 ref에 이 파일의 Ref 변수를 바인딩
 * 예: <CustomScroll :ref="(el) => { mainScrollRef = el }">
 * * 2. 실행: 스크롤 제어가 필요한 곳에서 제공되는 유틸리티 함수를 호출
 * 예: import { pageScrollToTop } from '...' -> pageScrollToTop();
 */

import { ref } from 'vue'

// 1. 메인 레이아웃 스크롤 영역 (MainLayout.vue 등에서 등록)
export const mainScrollRef = ref(null)

// 2. 모달 전용 스크롤 영역 (현재 활성화된 공통 모달에서 등록)
export const modalScrollRef = ref(null)

/**
 * 메인 페이지 스크롤을 최상단으로 이동
 * @param {string} behavior - 'smooth' (부드럽게) 또는 'auto' (즉시)
 */
export const pageScrollToTop = (behavior = 'smooth') => {
  mainScrollRef.value?.scrollTo({ top: 0, behavior })
}

/**
 * 활성화된 모달 스크롤을 최상단으로 이동
 * @param {string} behavior - 'smooth' 또는 'auto'
 */
export const modalScrollToTop = (behavior = 'smooth') => {
  modalScrollRef.value?.scrollTo({ top: 0, behavior })
}
