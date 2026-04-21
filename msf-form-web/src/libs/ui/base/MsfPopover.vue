<template>
  <div ref="triggerRef" class="popover-trigger-container" @click="togglePopover">
    <slot name="trigger" :disabled="inactiveEvent"></slot>
  </div>

  <Teleport :to="props.portalSelector || 'body'">
    <FocusTrap
      v-if="isPopoverOpen"
      :is-active="true"
      :loop="false"
      :restore-focus="true"
      @exit="closePopover"
    >
      <div
        :class="['popover-wrapper', { 'has-overlay': props.hasOverlay }]"
        @click="handleOverlayClick"
      >
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
          <!-- <div class="popover-content">
          <MsfCustomScroll class="popover-scrollbar">
            <div class="popover-inner">
              <slot></slot>
            </div>
          </MsfCustomScroll>
        </div> -->
          <MsfCustomScroll class="popover-content popover-scrollbar">
            <div class="popover-inner">
              <slot></slot>
            </div>
          </MsfCustomScroll>
        </div>
      </div>
    </FocusTrap>
  </Teleport>
</template>

<script setup>
import { ref, computed, watch, onMounted, onUnmounted, nextTick, useTemplateRef } from 'vue'
import FocusTrap from '@/libs/ui/utility/FocusTrap.vue'

const props = defineProps({
  title: String,
  trigger: { type: String, default: 'trigger' }, // 기본 트리거 텍스트
  placement: { type: [String, Array], default: ['top', 'start'] }, // 나타날 위치 (top, bottom 등)
  isOpen: { type: Boolean, default: false }, // 외부에서 제어 가능한 열림 상태
  inactiveEvent: { type: Boolean, default: false }, // 비활성화 여부
  gap: { type: Number, default: 8 }, // 트리거와 팝업 사이의 간격(px)
  closeLock: { type: Boolean, default: false }, // 스크롤 시 닫기 방지 여부
  portalSelector: { type: [String, Object], default: null }, // 렌더링될 대상 요소
  width: { type: String, default: '460px' }, // 팝업 너비
  maxHeight: { type: String, default: '' }, // 팝업 최대 높이 (내부 스크롤 발생 조건)
  showCloseBtn: { type: Boolean, default: true }, // 우측 상단 X 버튼 표시 여부
  hasOverlay: { type: Boolean, default: true }, // 배경 차단 레이어 여부 설정
  overlayClose: { type: Boolean, default: false }, // 오버레이 클릭 시 닫기 허용 여부
})

const emit = defineEmits(['update:isOpen', 'stateChange'])

// 내부 상태 관리
const isPopoverOpen = ref(props.isOpen)
const isPositioned = ref(false)
const popoverRef = useTemplateRef('popoverRef')
const triggerRef = useTemplateRef('triggerRef')
const portalContainer = ref(null) // 스크롤이 있는 부모 컨테이너 자동 감지용
const isScrollLocked = ref(false) // 열리는 시점의 찰나에 스크롤 이벤트 중복 발생 방지

// 위치 설정을 배열 형태로 통일
const placements = computed(() =>
  Array.isArray(props.placement) ? props.placement : [props.placement],
)

// 팝업 위치 계산
const setPopoverPosition = async () => {
  isPositioned.value = false
  await nextTick()
  await nextTick() // Teleport 이동 및 DOM 렌더링을 완전히 기다리기 위해 이중 nextTick 사용

  const target = triggerRef.value
  const popover = popoverRef.value
  if (!target || !popover) return

  // 1. 기준 요소(트리거) 및 뷰포트 정보 획득
  const targetRect = target.getBoundingClientRect()
  const portal = props.portalSelector || portalContainer.value || document.body
  const isBody = portal === document.body || portal === document.documentElement

  // 2. Portal의 좌표값 (특정 요소 안에 렌더링될 경우 상대 좌표 계산용)
  const portalRect = isBody ? { top: 0, left: 0 } : portal.getBoundingClientRect()

  const popoverWidth = popover.offsetWidth
  const popoverHeight = popover.offsetHeight
  const viewportHeight = window.innerHeight
  const viewportWidth = window.innerWidth

  // 3. 기본 위치 설정 (Portal 기준 상대 좌표 계산)
  let posX = isBody ? targetRect.left + window.scrollX : targetRect.left - portalRect.left
  let posY = isBody ? targetRect.top + window.scrollY : targetRect.top - portalRect.top

  let [side, align] = placements.value

  // 4. Flip (화면 공간 부족 시 상하 반전 자동 감지)
  if (side === 'bottom' && targetRect.bottom + popoverHeight + props.gap > viewportHeight) {
    if (targetRect.top > popoverHeight) side = 'top' // 위쪽 공간이 충분하면 위로 보냄
  } else if (side === 'top' && targetRect.top - popoverHeight - props.gap < 0) {
    if (viewportHeight - targetRect.bottom > popoverHeight) side = 'bottom' // 아래 공간이 충분하면 아래로 보냄
  }

  // 5. 선택된 방향(side)에 따른 최종 Y축 좌표 확정
  if (side === 'top') {
    posY -= popoverHeight + props.gap
  } else {
    posY += targetRect.height + props.gap
  }

  // 6. 정렬(Align) 수식 적용 (start, end, center)
  if (align === 'start') {
    // 트리거 왼쪽 끝에 정렬
  } else if (align === 'end') {
    posX += targetRect.width - popoverWidth
  } else {
    // 중앙 정렬
    posX += targetRect.width / 2 - popoverWidth / 2
  }

  // 7. 화면 가로 이탈 방지 (가로 방향으로 화면 밖으로 나가는 것 방어)
  posX = Math.max(
    isBody ? window.scrollX : 0,
    Math.min(posX, (isBody ? viewportWidth + window.scrollX : portal.clientWidth) - popoverWidth),
  )

  // 8. 최종 스타일 적용 및 노출
  popover.style.top = `${posY}px`
  popover.style.left = `${posX}px`
  isPositioned.value = true
}

// 팝업 토글 핸들러
const togglePopover = () => {
  if (props.inactiveEvent) return
  isPopoverOpen.value = !isPopoverOpen.value
  emit('update:isOpen', isPopoverOpen.value)
  // console.log('팝오버 열림')
  document.querySelector('.step-content-wrap').classList.add('no-scroll') //일단 열림시 스크롤 막음
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
  document.querySelector('.step-content-wrap').classList.remove('no-scroll')
}

// 외부 클릭 감지: 트리거와 팝업 내부가 아니면 닫음
const handleOutsideClick = (e) => {
  // if (popoverRef.value?.contains(e.target) || triggerRef.value?.contains(e.target)) return
  // closePopover()
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
    isScrollLocked.value = true
    // 열리는 순간 팝업 자체의 렌더링으로 인한 스크롤 오작동 방지
    setTimeout(() => (isScrollLocked.value = false), 150)

    // capture: true를 주어 화면 어느 곳의 스크롤도 캡처함
    window.addEventListener('scroll', handleScroll, { passive: true, capture: true })
    window.addEventListener('keydown', handleEscape)
  } else {
    window.removeEventListener('scroll', handleScroll, { capture: true })
    window.removeEventListener('keydown', handleEscape)
  }
  emit('stateChange', open)
})

onMounted(() => {
  window.addEventListener('click', handleOutsideClick)
  window.addEventListener('resize', closePopover) // 화면 크기 변하면 좌표가 깨지므로 일단 닫음
})

onUnmounted(() => {
  window.removeEventListener('click', handleOutsideClick)
  window.removeEventListener('resize', closePopover)
  window.removeEventListener('scroll', handleScroll, { capture: true })
  window.removeEventListener('keydown', handleEscape)
})

// 오버레이 클릭 핸들러
const handleOverlayClick = () => {
  // 오버레이 클릭 시 닫기가 허용된 경우에만 닫음 (닫기 버튼 전용이면 무시)
  if (props.overlayClose) {
    closePopover()
  }
}
</script>

<style lang="scss" scoped>
.popover-trigger-container {
  display: inline-block;
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
  max-height: 70vh;
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

/* 배경 차단 및 오버레이 스타일 */
.popover-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 1000;
  pointer-events: auto;

  // hasOverlay가 true일 때만 가림막
  &.has-overlay {
    background-color: transparent;
  }
}
</style>
