import { onMounted, onUnmounted, nextTick } from 'vue'

/**
 * 실제 가용 뷰포트 높이를 계산하여
 * CSS 변수(--msf-app-height)로 주입
 *
 * - 앱 레이아웃이 키보드/주소창 변화에 맞춰 사용할 높이값
 */
export function useAppHeight() {
  /**
   * 브라우저의 실제 가용 높이를 계산하여 CSS 변수에 할당
   * - window.visualViewport: 모바일 키보드나 주소창 변화를 반영
   * - window.innerHeight: 구형 브라우저 대응을 위한 대체값
   */
  const setAppHeight = () => {
    // 키보드 노출 등 실제 가용 화면 변화가 반영되는 높이
    const visualHeight = window.visualViewport ? window.visualViewport.height : window.innerHeight

    // CSS :root에 --msf-app-height 값 설정
    // [사용 예시]: height: var(--msf-app-height, 100vh);
    document.documentElement.style.setProperty('--msf-app-height', `${visualHeight}px`)

    // console.log('Current App visualViewport:', visualHeight)
  }

  /**
   * 리사이즈 이벤트가 빈번하게 발생하는 것을 방지하는 디바운스 유틸리티
   * @param {Function} fn - 실행할 함수
   * @param {number} ms - 지연 시간(ms)
   */
  const debounce = (fn, ms) => {
    let timer
    return () => {
      clearTimeout(timer)
      timer = setTimeout(fn, ms)
    }
  }
  // 리사이즈 시 성능을 위해 100ms(0.1) 디바운스 적용
  const debouncedSetAppHeight = debounce(setAppHeight, 100)

  onMounted(() => {
    // 초기 실행
    nextTick(() => setAppHeight())

    // 윈도우 리사이즈 및 visualViewport 변화 감지 이벤트 등록
    window.addEventListener('resize', debouncedSetAppHeight)
    if (window.visualViewport) {
      window.visualViewport.addEventListener('resize', debouncedSetAppHeight)
    }
  })

  onUnmounted(() => {
    // 컴포넌트 언마운트 시 메모리 누수 방지를 위한 이벤트 해제
    window.removeEventListener('resize', debouncedSetAppHeight)
    if (window.visualViewport) {
      window.visualViewport.removeEventListener('resize', debouncedSetAppHeight)
    }
  })
}
