import { ref, computed } from 'vue'

/**
 * 전역 스크롤 잠금 카운트
 * - 여러 곳(모달, 특정 뷰, 로딩 등)에서 동시에 잠금 요청을 보낼 수 있으므로
 * - 단순 Boolean이 아닌 카운팅 방식을 사용하여 중첩된 요청을 안전하게 처리
 */
const scrollLockCount = ref(0)

export function useScrollLock() {
  const isLocked = computed(() => scrollLockCount.value > 0)

  // 스크롤 잠금 요청 - 호출 시 카운트를 증가시키며, 최초 요청 시 브라우저 스크롤을 차단
  const lock = () => {
    // 하나 이상의 잠금 요청이 있으면 true
    scrollLockCount.value++
    if (scrollLockCount.value === 1) {
      document.documentElement.classList.add('no-scroll')
    }
  }

  // 스크롤 잠금 해제 요청 - 호출 시 카운트를 감소시키며, 모든 요청이 해제되었을 때만 스크롤을 다시 활성화
  const unlock = () => {
    scrollLockCount.value = Math.max(0, scrollLockCount.value - 1)
    if (scrollLockCount.value === 0) {
      document.documentElement.classList.remove('no-scroll')
    }
  }

  return { lock, unlock, isLocked, scrollLockCount }
}
