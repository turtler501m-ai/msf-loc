import { ref } from 'vue'

// 모든 인스턴스가 공유하도록 함수 외부에서 선언
const globalModalCount = ref(0)

export function useScrollLock() {
  const lock = () => {
    globalModalCount.value++
    if (globalModalCount.value === 1) {
      document.documentElement.classList.add('no-scroll')
    }
  }

  const unlock = () => {
    globalModalCount.value = Math.max(0, globalModalCount.value - 1)
    if (globalModalCount.value === 0) {
      document.documentElement.classList.remove('no-scroll')
    }
  }

  return { lock, unlock, globalModalCount }
}
