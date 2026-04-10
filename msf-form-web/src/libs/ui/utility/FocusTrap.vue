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
  isActive: { type: Boolean, default: true },
  autoFocus: { type: Boolean, default: true },
  restoreFocus: { type: Boolean, default: true },
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

/** 포커스 가능한 노드 필터링 */
const updateFocusNodes = () => {
  if (!containerRef.value) return
  const elements = containerRef.value.querySelectorAll(SELECTORS)
  // sentinel' 클래스를 가진 요소는 목록에서 제외해야 무한 루프가 안 생김
  focusableNodes = Array.from(elements).filter((el) => {
    return (
      !el.classList.contains('focus-trap-sentinel') &&
      window.getComputedStyle(el).display !== 'none' &&
      window.getComputedStyle(el).visibility !== 'hidden'
    )
  })
}

/** 포커스 처리 (무한 루프 방지 로직) */
const handleSentinelFocus = (isStart) => {
  if (!isTopMost()) return
  updateFocusNodes()

  if (focusableNodes.length > 0) {
    const target = isStart ? focusableNodes[focusableNodes.length - 1] : focusableNodes[0]
    target.focus()
  }
}

const handleKeyDown = (e) => {
  if (!props.isActive || !isTopMost() || e.key !== 'Tab') return

  updateFocusNodes()
  if (focusableNodes.length === 0) return e.preventDefault()

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
      trapRegistry.activeIds.push(id)

      await nextTick()
      if (containerRef.value) {
        observer = new MutationObserver(updateFocusNodes)
        observer.observe(containerRef.value, { childList: true, subtree: true })

        if (props.autoFocus) {
          setTimeout(() => {
            updateFocusNodes()
            if (focusableNodes.length > 0) focusableNodes[0].focus()
            else containerRef.value?.focus()
          }, 150)
        }
      }
    } else {
      trapRegistry.activeIds = trapRegistry.activeIds.filter((tid) => tid !== id)
      if (props.restoreFocus) {
        const trigger = trapRegistry.triggerElements.get(id)
        setTimeout(() => {
          trigger?.focus()
          trapRegistry.triggerElements.delete(id)
        }, 200)
      }
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
