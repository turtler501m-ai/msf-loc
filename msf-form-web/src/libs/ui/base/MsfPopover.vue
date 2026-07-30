<template>
  <div
    ref="triggerRef"
    class="popover-trigger-container"
    :class="{ 'is-active': isPopoverOpen }"
    @click.capture="togglePopover"
  >
    <slot name="trigger" :disabled="inactiveEvent"></slot>
    <Teleport :to="props.portalSelector || '#portal-root'" :disabled="!props.portalSelector">
      <FocusTrap
        v-if="isPopoverOpen"
        :is-active="true"
        :loop="false"
        :restore-focus="true"
        @exit="closePopover"
      >
        <div
          v-if="props.hasOverlay"
          class="popover-overlay-guard"
          @click.stop="handleOutsideClick"
        ></div>
        <div
          ref="popoverRef"
          class="popover-root"
          v-bind="$attrs"
          :style="{
            width: props.width,
            maxHeight: props.maxHeight,
            /* 위치 계산이 완료되기 전까지는 숨겨서 0,0 지점에 잠깐 나타나는 '깜빡임' 방지 */
            visibility: isPositioned ? 'visible' : 'hidden',
          }"
          @click.stop
        >
          <div class="popover-header">
            <div class="header-inner">
              <div v-if="props.title" class="header">
                <h2 class="title">{{ props.title }}</h2>
              </div>
              <MsfButton
                v-if="showCloseBtn"
                variant="ghost"
                iconOnly="close"
                class="popover-close-button"
                @click="closePopover"
              >
                닫기
              </MsfButton>
            </div>
          </div>
          <MsfCustomScroll class="popover-content popover-scrollbar">
            <div class="popover-inner">
              <slot></slot>
            </div>
          </MsfCustomScroll>
        </div>
      </FocusTrap>
    </Teleport>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted, onUnmounted, nextTick, useTemplateRef } from 'vue'
import FocusTrap from '@/libs/ui/utility/FocusTrap.vue'
import { useScrollLock } from '@/hooks/useScrollLock'

const { lock, unlock } = useScrollLock()

const props = defineProps({
  title: String,
  trigger: { type: String, default: 'trigger' }, // 기본 트리거 텍스트
  /**
   * @param {string | string[]} placement
   * 순서: [Side, Align]
   * - Side (첫 번째): 나타날 방향 ('top' | 'bottom' | 'left' | 'right')
   * - Align (두 번째): 해당 방향에서의 정렬 ('start' | 'center' | 'end')
   *
   * 예: ['bottom', 'end'] -> 아래쪽 렌더링, 오른쪽 끝 정렬
   * 예: ['right', 'start'] -> 오른쪽 옆 렌더링, 위쪽 끝 정렬
   */
  placement: {
    type: [String, Array],
    default: () => ['top', 'start'],
  },
  isOpen: { type: Boolean, default: false }, // 외부에서 제어 가능한 열림 상태
  inactiveEvent: { type: Boolean, default: false }, // 비활성화 여부
  gap: { type: Number, default: 8 }, // 트리거와 팝업 사이의 간격(px)
  portalSelector: { type: [String, Object], default: '#portal-root' }, // 렌더링 될 대상 요소 (null 일때 버튼뒤로 렌더링)
  width: { type: String, default: '460px' }, // 팝업 너비
  maxHeight: { type: String, default: '' }, // 팝업 최대 높이 (내부 스크롤 발생 조건)
  closeLock: { type: Boolean, default: false }, // 스크롤 시 닫기 방지 여부
  showCloseBtn: { type: Boolean, default: true }, // 우측 상단 X 버튼 표시 여부
  hasOverlay: { type: Boolean, default: true }, // 바닥 차단 레이어 여부 설정
  overlayClose: { type: Boolean, default: false }, // 오버레이 클릭 시 닫기 허용 여부
})

const emit = defineEmits(['update:isOpen', 'stateChange'])

// 내부 상태 관리
const isPopoverOpen = ref(props.isOpen)
const isPositioned = ref(false)
const popoverRef = useTemplateRef('popoverRef')
const triggerRef = useTemplateRef('triggerRef')
const isScrollLocked = ref(false) // 열리는 시점의 찰나에 스크롤 이벤트 중복 발생 방지

// 위치 설정을 배열 형태로 통일
const placements = computed(() =>
  Array.isArray(props.placement) ? props.placement : [props.placement],
)

/**
 * 팝업 위치 계산 핵심 로직
 */
const setPopoverPosition = async () => {
  /* 위치 계산이 완료되기 전까지는 숨김 */
  isPositioned.value = false

  // DOM 렌더링 및 내부 CustomScroll 높이 확보를 위해 대기
  await nextTick()
  await nextTick()

  const target = triggerRef.value
  const popover = popoverRef.value
  if (!target || !popover) return

  // [중요] 포털 사용 여부 판단
  const isPortaled = props.portalSelector !== null && props.portalSelector !== 'null'

  // 뷰포트 및 엘리먼트 정보 미리 확보
  const targetRect = target.getBoundingClientRect()
  const popoverHeight = popover.offsetHeight
  const popoverWidth = popover.offsetWidth
  const viewportHeight = window.innerHeight
  const viewportWidth = window.innerWidth

  let [side, align] = placements.value

  // ---------------------------------------------------------
  // 1. Flip 자동 감지 (공간 부족 시 반전)
  // ---------------------------------------------------------
  const space = {
    top: targetRect.top,
    bottom: viewportHeight - targetRect.bottom,
    left: targetRect.left,
    right: viewportWidth - targetRect.right,
  }

  // 현재 설정된 방향의 필요 공간
  const neededWidth = popoverWidth + props.gap
  const neededHeight = popoverHeight + props.gap

  // 현재 방향에서 공간이 부족한 경우, 가장 여유 있는 방향을 찾음
  const isVertical = side === 'top' || side === 'bottom'
  const needsFlip = isVertical
    ? side === 'top'
      ? space.top < neededHeight
      : space.bottom < neededHeight
    : side === 'left'
      ? space.left < neededWidth
      : space.right < neededWidth

  if (needsFlip) {
    if (isVertical) {
      // 세로 방향일 때: top/bottom 중 더 넓은 곳 선택
      side = space.top > space.bottom ? 'top' : 'bottom'
    } else {
      // 가로 방향일 때: left/right 중 더 넓은 곳 선택
      side = space.left > space.right ? 'left' : 'right'
    }
  }

  // ---------------------------------------------------------
  // CASE 1: 트리거 기준 (Portal 미사용)
  // ---------------------------------------------------------
  if (!isPortaled) {
    let posY = 0
    let posX = 0

    // 가로 방향 배치 (Right / Left)
    if (side === 'right' || side === 'left') {
      posX = side === 'right' ? target.offsetWidth + props.gap : -(popoverWidth + props.gap)
      // align 처리
      if (align === 'bottom') posY = target.offsetHeight - popoverHeight
      else if (align === 'center') posY = (target.offsetHeight - popoverHeight) / 2
    }
    // 세로 방향 배치 (Bottom / Top)
    else {
      posY = side === 'bottom' ? target.offsetHeight + props.gap : -(popoverHeight + props.gap)
      // align 처리
      if (align === 'end') posX = target.offsetWidth - popoverWidth
      else if (align === 'center') posX = (target.offsetWidth - popoverWidth) / 2
    }

    popover.style.top = `${posY}px`
    popover.style.left = `${posX}px`
    isPositioned.value = true
    return
  }

  // ---------------------------------------------------------
  // CASE 2: 포털(Portal) 렌더링
  // ---------------------------------------------------------
  const selector = props.portalSelector === 'null' ? '#portal-root' : props.portalSelector
  const portal = document.querySelector(selector) || document.body
  const portalRect = portal.getBoundingClientRect()

  let top = targetRect.top - portalRect.top
  let left = targetRect.left - portalRect.left

  if (side === 'right' || side === 'left') {
    left =
      side === 'right'
        ? targetRect.right - portalRect.left + props.gap
        : targetRect.left - portalRect.left - popoverWidth - props.gap

    if (align === 'bottom') top += targetRect.height - popoverHeight
    else if (align === 'center') top += (targetRect.height - popoverHeight) / 2
  } else {
    top =
      side === 'bottom'
        ? targetRect.bottom - portalRect.top + props.gap
        : targetRect.top - portalRect.top - popoverHeight - props.gap

    if (align === 'end') left += targetRect.width - popoverWidth
    else if (align === 'center') left += (targetRect.width - popoverWidth) / 2
  }

  popover.style.top = `${top}px`
  popover.style.left = `${left}px`
  isPositioned.value = true
}

// 팝업 토글 핸들러
const togglePopover = (e) => {
  if (props.inactiveEvent) return

  // 1. 이미 열려 있는 경우: 팝업을 닫고 이벤트 전파를 완전히 차단
  if (isPopoverOpen.value) {
    e.stopPropagation() // 이벤트가 부모로 올라가는 것을 막음
    closePopover()
    return
  }
  // 2. 닫혀 있는 경우: 팝업을 열고 이벤트 전파를 막지 않음
  // (부모 컴포넌트의 클릭 이벤트가 그대로 전달되어 API가 호출됨)
  isPopoverOpen.value = true
  emit('update:isOpen', true)
  // console.log('팝오버 열림')
}

// 팝업 닫기 및 포커스 복구
const closePopover = () => {
  if (!isPopoverOpen.value) return
  isPopoverOpen.value = false
  emit('update:isOpen', false)
  nextTick(() => {
    // 팝업이 닫힐 때 포커스를 다시 트리거로 돌려주어 키보드 사용자의 접근성 유지
    const focusable =
      triggerRef.value?.querySelector('button, [tabindex="0"], a') || triggerRef.value
    focusable?.focus()
  })
  // console.log('팝오버 닫힘')
}

// 외부 클릭 감지: 트리거와 팝업 내부가 아니면 닫음
const handleOutsideClick = (e) => {
  // if (popoverRef.value?.contains(e.target) || triggerRef.value?.contains(e.target)) return
  // closePopover()
  if (!props.overlayClose) return

  if (popoverRef.value?.contains(e.target) || triggerRef.value?.contains(e.target)) return
  closePopover()
}

// 스크롤 감지 핸들러
const handleScroll = (e) => {
  if (isScrollLocked.value || props.closeLock) return
  // 팝업 내부 스크롤인 경우 닫지 않음
  if (popoverRef.value?.contains(e.target)) return
  closePopover()
}

// ESC 키 감지 핸들러
const handleEscape = (e) => {
  if (e.key === 'Escape') closePopover()
}

// 부모 컴포넌트의 isOpen 프롭 변화 감시
watch(
  () => props.isOpen,
  (newVal) => (isPopoverOpen.value = newVal),
)

// 내부 열림 상태 변화 감시: 열릴 때 이벤트 리스너 등록, 닫힐 때 제거
watch(isPopoverOpen, (open) => {
  if (open) {
    setPopoverPosition()
    lock() // 열렸을 때 스크롤잠금

    isScrollLocked.value = true
    // 열리는 순간 팝업 자체의 렌더링으로 인한 스크롤 오작동 방지
    setTimeout(() => (isScrollLocked.value = false), 150)

    // capture: true를 주어 화면 어느 곳의 스크롤도 캡처함
    window.addEventListener('scroll', handleScroll, { passive: true, capture: true })
    window.addEventListener('keydown', handleEscape)
  } else {
    unlock() // 닫혔을 때 스크롤잠금 해제

    window.removeEventListener('scroll', handleScroll, { capture: true })
    window.removeEventListener('keydown', handleEscape)
  }
  emit('stateChange', open)
})

onMounted(() => {
  window.addEventListener('click', handleOutsideClick)
  window.addEventListener('resize', closePopover) // 화면 크기 변하면 좌표가 깨지므로 일단 닫음
  window.addEventListener('resize', unlock) // 화면 크기 변하면 닫고나서 커스텀스크롤 해제
})

onUnmounted(() => {
  window.removeEventListener('click', handleOutsideClick)
  window.removeEventListener('resize', closePopover)
  window.removeEventListener('scroll', handleScroll, { capture: true })
  window.removeEventListener('keydown', handleEscape)
})
</script>

<style lang="scss" scoped>
.popover-trigger-container {
  display: inline-block;
  position: relative;
  // 열렸을때 클릭을 위해서 z-index높임
  &.is-active {
    z-index: 1001;
  }
}
.popover-root {
  --popover-inner-padding: #{rem(24px)};

  @include position($t: 0, $l: 0, $i: 1000);
  @include flex($d: column) {
    gap: var(--popover-inner-padding);
  }
  background-color: var(--color-background);
  border-radius: var(--border-radius-l);
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.1);
  border: var(--border-width-base) solid var(--color-primary-base);
  margin: 0;
  padding-bottom: var(--popover-inner-padding);
  min-width: rem(120px);
  min-height: rem(540px); // 디자인가이드상 높이
  height: auto;

  // 추가_MsfCustomScrollbar 관련 스타일 추가
  overflow: hidden;
  display: flex; // 내부 MsfCustomScrollbar가 높이를 꽉 채우도록 flex 설정
  flex-direction: column;
  // 최대 높이 제한
  max-height: 62vh;
  overflow: hidden; // 부모의 기본 스크롤은 막고 커스텀만 사용

  .popover-header {
    flex-shrink: 0;
    flex-grow: 0;
    padding-inline: var(--popover-inner-padding);
    .header-inner {
      @include flex($v: center, $h: space-between) {
        gap: rem(16px);
      }
      padding-block: var(--popover-inner-padding) rem(16px);
      border-bottom: 1px solid var(--color-primary-base);
    }
    .popover-close-button {
      width: rem(24px);
      height: rem(24px);
      padding: 0;
      margin: 0;
    }
    .title {
      font-size: var(--font-size-24);
      font-weight: var(--font-weight-bold);
      line-height: var(--line-height-fit);
      color: var(--color-foreground);
      @include ellipsis(1);
    }
  }
  // 팝오버 컨텐츠
  .popover-content {
    // flex: 1;
    // overflow-y: auto;
    flex: 1 1 auto;
    overflow-y: auto;
    min-height: 0;
    .popover-inner {
      height: 100%;
      padding-inline: var(--popover-inner-padding);
    }
  }
}

/* 바닥 클릭 차단용 투명 레이어 */
.popover-overlay-guard {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 999; /* popover-root(1000)보다 바로 아래 */
  background-color: transparent;
  cursor: default;
}
</style>
