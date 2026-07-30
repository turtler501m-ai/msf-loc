/**
 * Alert 또는 validation 이후 특정 입력 항목으로 부드럽게 이동하고 focus를 주는 유틸.
 * id, name, CSS selector, data-focus-key, DOM 요소를 target으로 사용.
 */

// 실제 focus를 줄 수 있는 요소 목록
const FOCUSABLE_SELECTOR = [
  'input:not([disabled]):not([type="hidden"])',
  'select:not([disabled])',
  'textarea:not([disabled])',
  'button:not([disabled])',
  '[tabindex]:not([tabindex="-1"])',
  '.select-trigger:not([disabled])',
].join(', ')

// 전달된 target이 CSS selector 형태인지 판단
const isSelector = (target) =>
  target.startsWith('#') ||
  target.startsWith('.') ||
  target.startsWith('[') ||
  target.includes('[') ||
  target.includes(' ') ||
  target.startsWith('input') ||
  target.startsWith('select') ||
  target.startsWith('textarea') ||
  target.startsWith('button')

// 잘못된 selector가 들어와도 화면이 깨지지 않도록 조회
const safeQuery = (scope, selector) => {
  try {
    return scope.querySelector(selector)
  } catch {
    return null
  }
}

// id, name, data-focus-key, selector 순서로 target 요소 조회
const getTargetElement = (target, scope) => {
  if (!target) return null
  if (target instanceof HTMLElement) return target

  if (isSelector(target)) {
    return safeQuery(scope, target)
  }

  return (
    document.getElementById(target) ||
    safeQuery(scope, `[name="${target}"]`) ||
    safeQuery(scope, `[data-focus-key="${target}"]`) ||
    safeQuery(scope, target)
  )
}

// wrapper가 target인 경우 내부의 focus 가능 요소를 찾아 반환
const getFocusElement = (targetElement) => {
  if (!targetElement) return null

  return targetElement.matches?.(FOCUSABLE_SELECTOR)
    ? targetElement
    : targetElement.querySelector?.(FOCUSABLE_SELECTOR)
}

/**
 * 지정한 필드로 스크롤 이동 후 focus 처리
 * @param {string|HTMLElement} target - id, name, selector, data-focus-key 또는 DOM 요소
 * @param {object} options
 * @param {Document|HTMLElement} options.root - target 검색 기준 영역
 * @param {number} options.delay - alert 닫힘 이후 처리 지연 시간
 */
export const focusField = (target, options = {}) => {
  const { root = document, delay = 0 } = options

  setTimeout(() => {
    const targetElement = getTargetElement(target, root)
    const focusElement = getFocusElement(targetElement)

    if (!focusElement) return

    // 스크롤은 지정한 target 기준, focus는 내부의 실제 입력 요소 기준
    targetElement.scrollIntoView({ block: 'center', behavior: 'smooth' })

    requestAnimationFrame(() => {
      focusElement.focus({ preventScroll: true })
    })
  }, delay)
}
