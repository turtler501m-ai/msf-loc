<template>
  <div class="slider-container" ref="container" role="region" aria-roledescription="carousel">
    <div class="slider-wrapper">
      <MsfButton
        variant="ghost"
        class="nav-btn prev"
        @click="prev"
        :disabled="isAtStart"
        aria-label="이전 슬라이드"
        iconOnly="sliderLeft"
      />
      <div
        class="slider-content"
        ref="slider"
        @mousedown="onTouchStart"
        @touchstart="onTouchStart"
        @click.capture="handleCaptureClick"
      >
        <div
          class="slider-track"
          :class="{ 'is-dragging': isDragging }"
          :style="{
            transform: `translateX(-${offset + dragOffset}px)`,
            gap: `${gap}px`,
          }"
        >
          <slot></slot>
        </div>
      </div>
      <MsfButton
        variant="ghost"
        class="nav-btn next"
        @click="next"
        :disabled="isAtEnd"
        aria-label="다음 슬라이드"
        iconOnly="sliderRight"
      />
    </div>

    <div v-if="showIndicator" class="slider-indicator" role="tablist">
      <button
        v-for="i in totalPages"
        :key="i"
        class="dot"
        :class="{ active: currentPage === i - 1 }"
        role="tab"
        :aria-selected="currentPage === i - 1"
        :aria-label="`${i}번 슬라이드`"
        @click="goToPage(i - 1)"
      ></button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted, nextTick } from 'vue'

const props = defineProps({
  visibleCount: { type: Number, default: 3.2 }, // 한 화면에 보일 아이템 개수 (소수점 가능)
  slidesPerGroup: { type: Number, default: 1 }, // 한 번에 이동할 아이템 개수
  gap: { type: Number, default: 20 }, // 아이템 사이 간격 (px)
  showIndicator: { type: Boolean, default: false }, // 하단 도트 표시 여부
  breakpoints: { type: Object, default: () => ({}) }, // 반응형 설정 (화면 너비별 옵션)
})

const slider = ref(null)
const currentIndex = ref(0) // 현재 첫 번째로 보이는 아이템의 인덱스
const itemWidth = ref(0) // 계산된 아이템 한 개의 너비
const totalItems = ref(0) // 전체 아이템 개수
const isDragging = ref(false) // 현재 드래그 중인지 여부 (스타일 제어용)
const dragOffset = ref(0) // 드래그 중인 실시간 이동 거리 (px)

// touch 이벤트 옵션 통일
const touchOptions = { passive: false }

// 내부 변수
let startX = 0 // 드래그 시작 시점의 마우스/터치 X 좌표
let isMoved = false // 단순 클릭이 아닌 실제 '이동'이 발생했는지 여부

// 현재 반응형 상태에 따른 설정값
const currentVisibleCount = ref(props.visibleCount)
const currentGroup = ref(props.slidesPerGroup)

// 계산된 상태값
const totalPages = computed(() => {
  if (totalItems.value <= currentVisibleCount.value) return 1
  return Math.ceil((totalItems.value - currentVisibleCount.value) / currentGroup.value) + 1
})
const currentPage = computed(() => Math.ceil(currentIndex.value / currentGroup.value))
const offset = computed(() => currentIndex.value * (itemWidth.value + props.gap)) // 기본 이동 거리
const isAtStart = computed(() => currentIndex.value <= 0)
const isAtEnd = computed(() => currentIndex.value >= totalItems.value - currentVisibleCount.value)

// 레이아웃 및 반응형 처리
const updateLayout = () => {
  if (!slider.value) return
  const width = window.innerWidth

  // 1. Breakpoints 적용: 현재 화면 너비에 맞는 설정 찾기
  const bp = Object.keys(props.breakpoints)
    .map(Number)
    .sort((a, b) => b - a)
    .find((b) => width >= b)

  currentVisibleCount.value = bp ? props.breakpoints[bp].visibleCount : props.visibleCount
  currentGroup.value = bp ? props.breakpoints[bp].slidesPerGroup : props.slidesPerGroup

  // 2. 아이템 너비 계산 (컨테이너 너비 - 간격 제외 / 개수)
  itemWidth.value =
    (slider.value.offsetWidth - props.gap * (Math.ceil(currentVisibleCount.value) - 1)) /
    currentVisibleCount.value

  const track = slider.value.querySelector('.slider-track')
  if (!track) return
  const children = Array.from(track.children)
  totalItems.value = children.length

  // 3. 자식 요소 스타일 및 접근성 설정
  children.forEach((child) => {
    child.style.width = `${itemWidth.value}px`
    child.style.flexShrink = '0'

    // 키보드 사용자를 위한 탭 포커스 허용
    if (!child.hasAttribute('tabindex')) child.setAttribute('tabindex', '0')

    // 포커스된 상태에서 Enter 누르면 클릭으로 인식
    child.onkeydown = (e) => {
      if (e.key === 'Enter') child.click()
    }
  })
}

/**
 * 드래그 중에는 자식의 클릭 이벤트를 차단 (Capture 단계)
 */
const handleCaptureClick = (e) => {
  if (isMoved) {
    e.preventDefault()
    e.stopPropagation()
  }
}

/**
 * 드래그 시작 (Mouse / Touch)
 */
const onTouchStart = (e) => {
  isDragging.value = true
  isMoved = false // 시작 시 이동 여부 초기화
  startX = e.touches ? e.touches[0].clientX : e.clientX
  dragOffset.value = 0

  // 윈도우 전체에 리스너 등록 (영역을 벗어나도 추적 가능하도록)
  slider.value.addEventListener('mousemove', onTouchMove)
  slider.value.addEventListener('touchmove', onTouchMove, touchOptions)
  slider.value.addEventListener('mouseleave', onTouchEnd) // 드래그 이탈 대응
  window.addEventListener('mouseup', onTouchEnd)
  window.addEventListener('touchend', onTouchEnd)
  window.addEventListener('touchcancel', onTouchEnd)
}

/**
 * 드래그 중 이동
 */
const onTouchMove = (e) => {
  if (!isDragging.value) return
  const currentX = e.touches ? e.touches[0].clientX : e.clientX
  const diff = startX - currentX

  // 미세한 떨림 방지: 5px 이상 움직여야 실제 '드래그'로 인정
  if (Math.abs(diff) > 10) isMoved = true // Threshold 상향 (10px)

  if (isMoved) {
    dragOffset.value = diff
    // 가로 드래그 시 브라우저의 기본 세로 스크롤 방지
    // if (e.cancelable) e.preventDefault()
    if (isMoved && e.cancelable) {
      e.preventDefault()
    }
  }
}

/**
 * 드래그 종료
 */
const onTouchEnd = () => {
  isDragging.value = false

  if (isMoved) {
    // 1/4 이상 드래그했을 때만 다음/이전 슬라이드로 이동 판정
    if (Math.abs(dragOffset.value) > itemWidth.value / 4) {
      dragOffset.value > 0 ? next() : prev()
    }
  }

  // 트랙 위치 초기화 및 이동 여부 기록 해제 (지연 초기화로 클릭 이벤트 버블링 보장)
  dragOffset.value = 0
  setTimeout(() => {
    isMoved = false
  }, 20)

  // 등록했던 전역 리스너 해제
  slider.value.removeEventListener('mousemove', onTouchMove)
  slider.value.removeEventListener('touchmove', onTouchMove, touchOptions)
  slider.value.removeEventListener('mouseleave', onTouchEnd)
  window.removeEventListener('mouseup', onTouchEnd)
  window.removeEventListener('touchend', onTouchEnd)
  window.removeEventListener('touchcancel', onTouchEnd)
}

// 이동 함수
const next = () =>
  (currentIndex.value = Math.min(
    currentIndex.value + currentGroup.value,
    totalItems.value - currentVisibleCount.value,
  ))

const prev = () => (currentIndex.value = Math.max(currentIndex.value - currentGroup.value, 0))

const goToPage = (p) =>
  (currentIndex.value = Math.min(
    p * currentGroup.value,
    totalItems.value - currentVisibleCount.value,
  ))

/**
 * 키보드 탭으로 보이지 않는 슬라이드에 포커스가 갔을 때 자동으로 이동 처리
 */
const handleFocusIn = (e) => {
  // 마우스 클릭으로 인한 포커스라면(focus-visible이 아니라면) 리턴(0.2개 영역 클릭 시 슬라이더가 움직이지 않음)
  if (!e.target.matches(':focus-visible')) return

  const track = slider.value.querySelector('.slider-track')
  const items = Array.from(track.children)
  const focusedIndex = items.findIndex((item) => item.contains(e.target))

  if (focusedIndex !== -1) {
    // 현재 화면 범위를 벗어난 인덱스인지 확인
    const isOut =
      focusedIndex < currentIndex.value ||
      focusedIndex >= currentIndex.value + (currentVisibleCount.value - 0.5)

    if (isOut) {
      // 포커스된 위치로 슬라이드 이동
      currentIndex.value = Math.min(focusedIndex, totalItems.value - currentVisibleCount.value)
    }
  }
}

onMounted(() => {
  // DOM이 완전히 그려진 후 너비 계산
  nextTick(() => {
    updateLayout()
    window.addEventListener('resize', updateLayout)
    // 탭 포커스 감지 리스너 (Capture 모드로 자식의 포커스 감지)
    slider.value?.addEventListener('focusin', handleFocusIn, { capture: true })
  })
})

onUnmounted(() => {
  window.removeEventListener('resize', updateLayout)
})
</script>

<style lang="scss" scoped>
.slider-container {
  position: relative;
  width: 100%;
  user-select: none; // 드래그 중 텍스트 선택 방지
  overflow: hidden;
}
.slider-wrapper {
  display: flex;
  align-items: center;
  width: 100%;
}
.slider-content {
  overflow: hidden;
  flex: 1;
  cursor: grab;
  // touch-action: pan-y;
  /* WebView/모바일 터치 안정성 개선 */
  touch-action: pan-x pan-y;
  -webkit-tap-highlight-color: transparent; // 모바일 터치 하이라이트 제거

  &:active {
    cursor: grabbing;
  }

  // 키보드 접근성: focus-visible 일 때만 스타일 적용
  :deep(.slider-track > *:focus-visible) {
    outline: 2px solid var(--color-gray-900);
    outline-offset: -2px;
  }
}
.slider-track {
  display: flex;
  transition: transform 0.4s cubic-bezier(0.25, 1, 0.5, 1);
  will-change: transform;

  // 드래그 중에는 애니메이션을 꺼서 마우스 움직임에 즉각 반응하게 함
  &.is-dragging {
    transition: none !important;
  }
  /* 드래그 시 텍스트나 이미지가 선택되어 파랗게 변하는 현상 방지 */
  -webkit-user-select: none;
  /* 모바일에서 이미지 길게 눌렀을 때 저장 팝업 뜨는 것 방지 (슬라이더 방해 요소) */
  -webkit-touch-callout: none;
}
.nav-btn {
  padding: 0;
  width: rem(24px);
  height: rem(24px);
  flex-shrink: 0;
  @include flex($v: center, $h: center);
  color: var(--color-gray-400);
  &:disabled {
    background-color: transparent !important;
    border: none !important;
  }
}
.slider-indicator {
  display: flex;
  justify-content: center;
  gap: 8px;
  margin-top: 16px;

  .dot {
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background: #ccc;
    border: none;
    cursor: pointer;
    transition: all 0.3s;

    &.active {
      background: #e60012;
      width: 20px; // 현재 페이지 강조 (길쭉하게)
    }
  }
}
:deep(img) {
  -webkit-user-drag: none; // 이미지 드래그 기능이 브라우저 스와이프를 방해하지 않도록 설정
  pointer-events: none;
}
</style>
