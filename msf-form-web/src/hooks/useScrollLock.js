import { ref, computed } from 'vue'

/**
 * 전역 스크롤 잠금 카운트
 * - 여러 곳(모달, 알럿, 로딩 등)에서 동시에 잠금 요청을 보낼 수 있으므로
 * - 단순 Boolean이 아닌 카운팅 방식을 사용하여 중첩된 요청을 안전하게 처리
 */
const scrollLockCount = ref(0)
let scrollTop = 0

export function useScrollLock() {
  const isLocked = computed(() => scrollLockCount.value > 0)

  /**
   * 현재 useScrollLock 인스턴스가 스크롤 잠금을 요청했는지 여부
   * - 같은 컴포넌트에서 lock이 중복 호출되는 것 방지
   * - unlock이 중복 호출되어 다른 컴포넌트의 잠금을 해제하는 것 방지
   */
  const lockedByThis = ref(false)

  const lock = () => {
    if (lockedByThis.value) return

    lockedByThis.value = true
    scrollLockCount.value += 1

    if (scrollLockCount.value === 1) {
      scrollTop = window.scrollY || document.documentElement.scrollTop || 0
      document.documentElement.classList.add('no-scroll')

      // 팝업이 열려 있는 동안 body 자체를 fixed로 설정
      document.body.style.position = 'fixed'
      document.body.style.top = `-${scrollTop}px`
      document.body.style.left = '0'
      document.body.style.width = '100%'
    }
  }

  const unlock = () => {
    if (!lockedByThis.value) return

    lockedByThis.value = false
    scrollLockCount.value = Math.max(0, scrollLockCount.value - 1)

    if (scrollLockCount.value === 0) {
      document.documentElement.classList.remove('no-scroll')
      document.body.style.position = ''
      document.body.style.top = ''
      document.body.style.left = ''
      document.body.style.width = ''

      // body fixed 해제 후 잠그기 전 스크롤 위치로 복구
      window.scrollTo(0, scrollTop)
      scrollTop = 0
    }
  }

  return { lock, unlock, isLocked, scrollLockCount }
}
