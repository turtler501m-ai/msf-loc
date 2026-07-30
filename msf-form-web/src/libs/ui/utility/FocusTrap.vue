<script>
// 팝업이 겹쳐도 어떤 트랩이 가장 위에 있는지 모든 인스턴스가 공유
const trapRegistry = {
  activeIds: [],
  triggerElements: new Map(),
}
</script>

<script setup>
import { ref, watch, onMounted, onUnmounted, useId, nextTick } from 'vue'

const props = defineProps({
  /** 포커스 트랩 활성화 여부 */
  isActive: { type: Boolean, default: true },
  /** 활성화 시 첫 번째 포커스 가능 요소로 자동 포커스 이동 여부 */
  autoFocus: { type: Boolean, default: true },
  /** 비활성화 시 트랩을 열기 전 포커스 요소로 복귀할지 여부 */
  restoreFocus: { type: Boolean, default: true },
  /** Tab 이동이 트랩 내부에서 순환되도록 할지 여부 */
  loop: { type: Boolean, default: true },
})

const emit = defineEmits(['exit'])

const uniqueId = useId()
const id = `focus-trap-${uniqueId}`
const containerRef = ref(null)
let focusableNodes = []
let observer = null

const SELECTORS =
  'a[href], button:not([disabled]), textarea:not([disabled]), input:not([disabled]), select:not([disabled]), [tabindex]:not([tabindex="-1"])'

const isTopMost = () => {
  const topId = trapRegistry.activeIds[trapRegistry.activeIds.length - 1]
  return topId === id
}

// Dialog open 이벤트 등에서 이미 내부 요소로 이동한 포커스를 autoFocus가 다시 뺏지 않도록 확인
const hasFocusInside = () => {
  const activeElement = document.activeElement
  return !!activeElement && containerRef.value?.contains(activeElement)
}

/** 포커스 가능한 노드 필터링 */
const updateFocusNodes = () => {
  if (!containerRef.value) return
  const elements = containerRef.value.querySelectorAll(SELECTORS)
  // sentinel 클래스를 가진 요소는 목록에서 제외해야 무한 루프가 안 생김
  focusableNodes = Array.from(elements).filter((el) => {
    const style = window.getComputedStyle(el)

    return (
      !el.classList.contains('focus-trap-sentinel') &&
      !el.hidden &&
      el.getAttribute('aria-hidden') !== 'true' &&
      !el.disabled &&
      style.display !== 'none' &&
      style.visibility !== 'hidden'
    )
  })
}

/** 포커스 처리 (무한 루프 방지 로직) */
const handleSentinelFocus = (isStart) => {
  if (!props.isActive || !isTopMost()) return
  updateFocusNodes()
  if (!props.loop) {
    emit('exit')
    return
  }
  if (focusableNodes.length > 0) {
    const target = isStart ? focusableNodes[focusableNodes.length - 1] : focusableNodes[0]
    target.focus()
  }
}

const handleKeyDown = (e) => {
  if (!props.isActive || !isTopMost() || e.key !== 'Tab') return

  updateFocusNodes()

  if (focusableNodes.length === 0) {
    if (props.loop) e.preventDefault()
    else emit('exit')
    return
  }

  const first = focusableNodes[0]
  const last = focusableNodes[focusableNodes.length - 1]

  if (e.shiftKey && document.activeElement === first) {
    e.preventDefault()
    props.loop ? last.focus() : emit('exit')
  } else if (!e.shiftKey && document.activeElement === last) {
    e.preventDefault()
    props.loop ? first.focus() : emit('exit')
  }
}

watch(
  () => props.isActive,
  async (active) => {
    if (active) {
      trapRegistry.triggerElements.set(id, document.activeElement)

      if (!trapRegistry.activeIds.includes(id)) {
        trapRegistry.activeIds.push(id)
      }

      await nextTick()
      if (containerRef.value) {
        observer?.disconnect()
        observer = new MutationObserver(updateFocusNodes)
        observer.observe(containerRef.value, {
          childList: true,
          subtree: true,
          attributes: true,
          attributeFilter: ['disabled', 'tabindex', 'hidden', 'style', 'aria-hidden'],
        })

        if (props.autoFocus) {
          setTimeout(() => {
            // 지연 중 상태가 바뀌었거나 내부 포커스가 이미 잡혔다면 기본 포커스를 건너뜀
            if (!props.isActive || !isTopMost() || hasFocusInside()) return

            updateFocusNodes()
            if (focusableNodes.length > 0) focusableNodes[0].focus()
            else containerRef.value?.focus()
          }, 150)
        }
      }
    } else {
      trapRegistry.activeIds = trapRegistry.activeIds.filter((tid) => tid !== id)
      const trigger = trapRegistry.triggerElements.get(id)
      if (props.restoreFocus) {
        setTimeout(() => {
          trigger?.focus()
        }, 200)
      }
      trapRegistry.triggerElements.delete(id)
      observer?.disconnect()
    }
  },
  { immediate: true },
)

onMounted(() => window.addEventListener('keydown', handleKeyDown))
onUnmounted(() => {
  window.removeEventListener('keydown', handleKeyDown)
  observer?.disconnect()
  trapRegistry.activeIds = trapRegistry.activeIds.filter((tid) => tid !== id)
  trapRegistry.triggerElements.delete(id)
})
</script>

<template>
  <div ref="containerRef" class="focus-trap-wrapper" tabindex="-1">
    <span
      tabindex="0"
      aria-hidden="true"
      class="focus-trap-sentinel start"
      @focus="handleSentinelFocus(true)"
    ></span>

    <slot></slot>

    <span
      tabindex="0"
      aria-hidden="true"
      class="focus-trap-sentinel end"
      @focus="handleSentinelFocus(false)"
    ></span>
  </div>
</template>

<style scoped>
.focus-trap-wrapper {
  outline: none;
  display: contents;
}
.focus-trap-sentinel {
  position: fixed;
  width: 1px;
  height: 1px;
  opacity: 0;
  overflow: hidden;
  pointer-events: none;
}
</style>
