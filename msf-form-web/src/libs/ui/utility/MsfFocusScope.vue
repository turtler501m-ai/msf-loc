<!--
  라우팅 화면 또는 폼 영역 안에서 입력 흐름을 보조하는 Focus Scope.
  App.vue에서 RouterView를 감싸 라우팅되는 일반 화면에 공통 적용하거나,
  포탈/모달처럼 RouterView 밖에 렌더링되는 입력 영역은 별도로 감싸서 사용.

  [역할]
  - input에서 Enter 입력 시 다음 포커스 가능 요소로 이동
  - input에 maxLength가 있고 값이 모두 채워지면 다음 포커스 가능 요소로 이동
  - button, textarea 등 자체 키보드 동작이 중요한 요소는 기본 동작을 우선
  - MsfInput 내부 clear 버튼처럼 보조 UI는 다음 포커스 후보에서 제외
    단, Tab 접근과 Enter 클릭은 기존 동작을 유지한다.
  - MsfInput 개발자가 지정한 의도된 동작의 Enter키 이벤트가 있으면 제외

  [주의]
  - Alert, Loading처럼 입력 흐름이 필요 없는 전역 UI에는 적용하지 않는다.
  - portal-root로 이동하는 Dialog/Popover 내부 입력 폼은 사용한다면 별도 MsfFocusScope를 내부에 넣어준다.
-->
<template>
  <div
    class="msf-focus-scope-root"
    ref="scopeRef"
    @keydown="onKeydown"
    @input="onInput"
    @focusin="onFocusIn"
  >
    <slot />
  </div>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  // FocusScope 공통 기능 비활성화 여부
  disabled: { type: Boolean, default: false },
})

const scopeRef = ref(null)

// Enter/maxLength 자동 이동 시 다음 포커스 후보로 사용할 요소
const focusableSelector = ['input', 'select', 'textarea', 'button', '[tabindex]'].join(',')

// 포커스 시 스크롤 보정이 필요한 텍스트 입력 계열 요소
const scrollOnFocusSelector = [
  'input:not([type])',
  'input[type="text"]',
  'input[type="search"]',
  'input[type="tel"]',
  'input[type="url"]',
  'input[type="email"]',
  'input[type="password"]',
  'input[type="number"]',
  'input[type="date"]',
  'input[type="time"]',
  'select',
  'textarea',
].join(',')

// 포커스는 가능하지만 Enter/maxLength 자동 이동 대상에서는 제외할 요소.
// 예: MsfInput의 clear 버튼은 Tab 접근과 Enter 클릭은 유지하되, input 다음 후보에서는 제외한다.
const excludeSelector = ['.btn-clear', '[data-focus-scope-exclude]'].join(',')
// Enter 자동 포커스 이동만 제외할 요소
const skipEnterSelector = '[data-focus-scope-skip-enter]'

// 제외 대상이 아니고 실제 화면에서 포커스 가능한 요소인지 확인
function isFocusable(el) {
  return (
    !el.matches(excludeSelector) &&
    !el.hasAttribute('disabled') &&
    !el.hasAttribute('readonly') &&
    el.tabIndex !== -1 &&
    el.offsetParent !== null
  )
}

// 현재 요소에서 가장 가까운 실제 세로 스크롤 컨테이너를 찾는다.
// MsfCustomScroll은 내부 .cs-content가 overflow: auto이므로 이 방식으로 잡힌다.
function getScrollContainer(el) {
  let container = el.parentElement

  while (container) {
    const style = getComputedStyle(container)
    const canScrollY = /(auto|scroll|overlay)/.test(style.overflowY)

    if (canScrollY && container.scrollHeight > container.clientHeight) return container
    container = container.parentElement
  }

  return window
}

// 키패드나 주소창 변화로 줄어든 visualViewport와 컨테이너가 겹치는 실제 가시 영역
function getVisibleRect(container) {
  const viewport = window.visualViewport
  const viewportTop = viewport?.offsetTop ?? 0
  const viewportHeight = viewport?.height ?? window.innerHeight
  const viewportBottom = viewportTop + viewportHeight

  if (container === window) {
    return {
      top: viewportTop,
      height: viewportHeight,
    }
  }

  const rect = container.getBoundingClientRect()
  const top = Math.max(rect.top, viewportTop)
  const bottom = Math.min(rect.bottom, viewportBottom)

  return {
    top,
    height: Math.max(bottom - top, 0),
  }
}

// 스크롤 가능한 최대 top 값을 계산해 과도한 이동을 막는다.
function getMaxScrollTop(container) {
  if (container === window) {
    const scrollEl = document.scrollingElement || document.documentElement
    return Math.max(scrollEl.scrollHeight - window.innerHeight, 0)
  }

  return Math.max(container.scrollHeight - container.clientHeight, 0)
}

// scrollIntoView 대신 가장 가까운 스크롤 컨테이너를 직접 움직인다.
// 모바일 키패드가 올라온 뒤의 실제 가시 영역 기준으로 중앙보다 살짝 위에 배치한다.
function scrollElementIntoView(el, behavior = 'smooth') {
  // DOM 레이아웃과 visualViewport가 안정된 뒤 보정하기 위해 두 프레임 대기
  requestAnimationFrame(() => {
    requestAnimationFrame(() => {
      const container = getScrollContainer(el)
      const visibleRect = getVisibleRect(container)
      const elRect = el.getBoundingClientRect()

      if (!visibleRect.height) return

      const scrollTop = container === window ? window.scrollY : container.scrollTop
      const elTopInContainer = elRect.top - visibleRect.top + scrollTop
      const focusOffset = Math.min(48, visibleRect.height * 0.08)
      const targetScrollTop = Math.max(
        0,
        Math.min(
          elTopInContainer - visibleRect.height / 2 + elRect.height / 2 + focusOffset,
          getMaxScrollTop(container),
        ),
      )

      if (Math.abs(targetScrollTop - scrollTop) < 2) return

      if (container === window) {
        window.scrollTo({ top: targetScrollTop, behavior })
        return
      }

      container.scrollTo({ top: targetScrollTop, behavior })
    })
  })
}

// 브라우저 기본 스크롤 없이 포커스만 이동하고, 스크롤은 focusin에서 처리
function focusElement(el) {
  el.focus({ preventScroll: true })
}

// Enter 자동 포커스 이동을 건너뛸 요소인지 확인
function shouldSkipEnterMove(el) {
  if (el.matches(skipEnterSelector)) return true
  if (typeof el.onkeydown === 'function' && !el.classList.contains('input-inner')) return true
  return false
}

// 현재 요소를 기준으로 스코프 내부의 다음 포커스 가능 요소로 이동한다.
function focusNext(target) {
  const scope = scopeRef.value
  if (!scope) return false

  const focusables = Array.from(scope.querySelectorAll(focusableSelector)).filter(isFocusable)

  const currentIndex = focusables.indexOf(target)
  if (currentIndex === -1) return false

  const next = focusables[currentIndex + 1]
  if (!next) return false

  focusElement(next)
  return true
}

// Enter 입력 시 다음 포커스 가능 요소로 이동
function onKeydown(e) {
  if (props.disabled) return

  if (e.key !== 'Enter') return

  const target = e.target

  // textarea는 Enter 줄바꿈, button은 Enter 클릭이 기본 동작이므로 스코프가 개입하지 않는다.
  if (target.tagName === 'TEXTAREA') return
  if (target.tagName === 'BUTTON') return

  // 제외 대상에 직접 포커스된 경우에는 기존 키보드 동작을 그대로 둔다.
  if (target.matches(excludeSelector)) return
  if (shouldSkipEnterMove(target)) return

  if (focusNext(target)) e.preventDefault()
}

// input 값이 maxLength에 도달하면 다음 포커스 가능 요소로 이동
function onInput(e) {
  if (props.disabled) return

  const target = e.target

  // maxLength 자동 이동은 입력 완료 조건이 명확한 input에만 적용한다.
  if (target.tagName !== 'INPUT') return
  if (target.matches(excludeSelector)) return
  if (target.maxLength < 0) return
  if (target.value.length < target.maxLength) return

  focusNext(target)
}

// 직접 포커스된 입력 요소가 보이도록 이동하고, 모바일 키보드 표시 후 한 번 더 보정
function onFocusIn(e) {
  if (props.disabled) return

  const target = e.target
  // 스크롤이 필요 없는 요소는 즉시 종료
  if (!target.matches(scrollOnFocusSelector)) return
  if (target.matches(excludeSelector)) return
  if (!isFocusable(target)) return

  // 브라우저의 기본 스크롤이 끝난 상태에서 원하는 위치로 한 번만 부드럽게 보정
  // 모바일 키보드가 올라오면 크기가 변경되는 실제 화면 영역
  const viewport = window.visualViewport
  // 키보드 애니메이션 중 연속으로 발생하는 resize를 묶기 위한 타이머
  let resizeTimer
  let fallbackTimers = []
  let cleaned = false

  // 포커스 이동이 끝나면 등록한 타이머와 resize 이벤트를 정리
  const cleanup = () => {
    if (cleaned) return
    cleaned = true
    clearTimeout(resizeTimer)
    fallbackTimers.forEach(clearTimeout)
    fallbackTimers = []
    viewport?.removeEventListener('resize', onResize)
    viewport?.removeEventListener('scroll', onResize)
  }

  // 아직 같은 입력 요소에 포커스가 있을 때만 스크롤 보정
  const scrollFocusedTarget = (behavior = 'smooth') => {
    if (document.activeElement !== target) {
      cleanup()
      return
    }

    scrollElementIntoView(target, behavior)
  }

  // 키보드가 올라오거나 내려가면서 visualViewport 높이가 변경될 때 실행
  const onResize = () => {
    // resize가 계속 발생하면 이전 종료 판정은 취소
    clearTimeout(resizeTimer)
    fallbackTimers.forEach(clearTimeout)
    fallbackTimers = []
    // 마지막 resize 이후 짧게 멈추면 키보드 동작이 끝난 것으로 판단
    resizeTimer = setTimeout(() => {
      scrollFocusedTarget('smooth')
    }, 140)
  }
  // 키보드 노출로 인한 실제 화면 높이 변경 감지 시작
  viewport?.addEventListener('resize', onResize)
  // 키보드 노출 시 화면 위치만 변경되는 경우 감지
  viewport?.addEventListener('scroll', onResize)
  // 키보드 resize 전에 다른 input으로 이동하면 기존 감지 이벤트 정리
  target.addEventListener('blur', cleanup, { once: true })

  // 즉시 한 번 보정하고, WebView에서 resize 이벤트가 늦거나 누락되는 경우를 대비해 추가 보정
  scrollFocusedTarget('smooth')
  fallbackTimers = [250, 500].map((delay) =>
    setTimeout(() => {
      scrollFocusedTarget('smooth')
    }, delay),
  )
}
</script>
<style>
.msf-focus-scope-root {
  display: contents;
}
</style>
