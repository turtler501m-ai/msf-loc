<script setup>
import { ref, onMounted, onUnmounted, nextTick } from 'vue'

const emit = defineEmits(['scroll-top', 'scroll-end', 'scroll'])
const props = defineProps({
  height: { type: String, default: '100%' },
  width: { type: String, default: '100%' },
  alwaysShow: { type: Boolean, default: true }, // 항상 보일지 여부
})

const contentRef = ref(null)
const contentInnerRef = ref(null)
const thumbVRef = ref(null)
const thumbHRef = ref(null)

const isScrolling = ref(false)
const isDragging = ref(false)
const hasVScroll = ref(false)
const hasHScroll = ref(false)

let scrollTimeout = null
let resizeObserver = null
let ticking = false
let startOffset = 0
let currentAxis = null

const GAP = 2 // 스크롤바 교차점 겹침 방지 (ex: 클릭 영역 + 여유 2px)
const SIDE_GAP = 0 // 위/왼쪽 시작 지점 여백

const updateThumbs = () => {
  const el = contentRef.value
  if (!el) return
  const { scrollHeight, clientHeight, scrollTop, scrollWidth, clientWidth, scrollLeft } = el

  hasVScroll.value = scrollHeight > clientHeight + 1
  if (hasVScroll.value && thumbVRef.value) {
    // 실제 트랙 높이 = 전체 높이 - 하단GAP - 상단SIDE_GAP
    const trackHeight = clientHeight - GAP - SIDE_GAP
    const thumbHeight = Math.max((clientHeight / scrollHeight) * trackHeight, 30)
    const moveY = (scrollTop / (scrollHeight - clientHeight)) * (trackHeight - thumbHeight)
    thumbVRef.value.style.height = `${thumbHeight}px`
    thumbVRef.value.style.transform = `translateY(${moveY}px)`

    if (scrollTop <= 0) emit('scroll-top')
    else if (scrollTop + clientHeight >= scrollHeight - 1) emit('scroll-end')
  }

  hasHScroll.value = scrollWidth > clientWidth + 1
  if (hasHScroll.value && thumbHRef.value) {
    // 실제 트랙 너비 = 전체 너비 - 우측GAP - 좌측SIDE_GAP
    const trackWidth = clientWidth - GAP - SIDE_GAP
    const thumbWidth = Math.max((clientWidth / scrollWidth) * trackWidth, 30)
    const moveX = (scrollLeft / (scrollWidth - clientWidth)) * (trackWidth - thumbWidth)
    thumbHRef.value.style.width = `${thumbWidth}px`
    thumbHRef.value.style.transform = `translateX(${moveX}px)`
  }

  emit('scroll', {
    top: scrollTop,
    left: scrollLeft,
    isBottom: scrollTop + clientHeight >= scrollHeight - 1,
  })
}

const startDrag = (e, axis) => {
  // 터치 이벤트일 경우 touches[0]에서 좌표를 가져옴
  const clientY = e.touches ? e.touches[0].clientY : e.clientY
  const clientX = e.touches ? e.touches[0].clientX : e.clientX

  if (!e.touches) e.preventDefault() // 마우스일 때만 기본 동작 방지

  isDragging.value = true
  currentAxis = axis

  if (axis === 'v') {
    startOffset = clientY - thumbVRef.value.getBoundingClientRect().top
  } else {
    startOffset = clientX - thumbHRef.value.getBoundingClientRect().left
  }

  document.addEventListener('mousemove', onDragging)
  document.addEventListener('mouseup', stopDrag)
  // 터치 이벤트 추가
  document.addEventListener('touchmove', onDragging, { passive: false })
  document.addEventListener('touchend', stopDrag)

  document.body.style.userSelect = 'none'
}

const onDragging = (e) => {
  if (!isDragging.value) return
  if (e.cancelable) e.preventDefault() // 터치 시 화면 꿀렁임 방지

  const clientY = e.touches ? e.touches[0].clientY : e.clientY
  const clientX = e.touches ? e.touches[0].clientX : e.clientX

  const el = contentRef.value
  const wrapper = el.getBoundingClientRect()

  if (currentAxis === 'v') {
    const trackHeight = el.clientHeight - GAP - SIDE_GAP
    const thumbHeight = thumbVRef.value.offsetHeight
    let moveY = clientY - (wrapper.top + SIDE_GAP) - startOffset // 수정된 clientY 사용
    moveY = Math.max(0, Math.min(moveY, trackHeight - thumbHeight))
    el.scrollTop = (moveY / (trackHeight - thumbHeight)) * (el.scrollHeight - el.clientHeight)
  } else {
    const trackWidth = el.clientWidth - GAP - SIDE_GAP
    const thumbWidth = thumbHRef.value.offsetWidth
    let moveX = clientX - (wrapper.left + SIDE_GAP) - startOffset // 수정된 clientX 사용
    moveX = Math.max(0, Math.min(moveX, trackWidth - thumbWidth))
    el.scrollLeft = (moveX / (trackWidth - thumbWidth)) * (el.scrollWidth - el.clientWidth)
  }
  updateThumbs()
}

const stopDrag = () => {
  isDragging.value = false
  document.removeEventListener('mousemove', onDragging)
  document.removeEventListener('mouseup', stopDrag)
  document.removeEventListener('touchmove', onDragging) // 추가
  document.removeEventListener('touchend', stopDrag) // 추가
  document.body.style.userSelect = ''
  scrollTimeout = setTimeout(() => {
    isScrolling.value = false
  }, 300)
}

const handleWheel = (e) => {
  const el = contentRef.value
  if (el && hasHScroll.value && !hasVScroll.value && Math.abs(e.deltaY) > Math.abs(e.deltaX)) {
    el.scrollLeft += e.deltaY
    e.preventDefault()
  }
}

const handleScroll = () => {
  isScrolling.value = true
  if (scrollTimeout) clearTimeout(scrollTimeout)
  if (!ticking) {
    requestAnimationFrame(() => {
      updateThumbs()
      ticking = false
    })
    ticking = true
  }
  if (!isDragging.value) {
    scrollTimeout = setTimeout(() => {
      isScrolling.value = false
    }, 300)
  }
}

onMounted(() => {
  const el = contentRef.value
  if (!el) return
  nextTick(updateThumbs)
  el.addEventListener('scroll', handleScroll, { passive: true })
  el.addEventListener('wheel', handleWheel, { passive: false })
  if (window.ResizeObserver) {
    resizeObserver = new ResizeObserver(updateThumbs)
    resizeObserver.observe(el)
    if (contentInnerRef.value) resizeObserver.observe(contentInnerRef.value)
  }
  setTimeout(updateThumbs, 500)
})

onUnmounted(() => {
  contentRef.value?.removeEventListener('scroll', handleScroll)
  contentRef.value?.removeEventListener('wheel', handleWheel)
  resizeObserver?.disconnect()
  stopDrag() // 드래그 중 컴포넌트가 사라질 경우를 대비한 안전장치
})

defineExpose({ scrollTo: (opt) => contentRef.value?.scrollTo(opt), update: updateThumbs })
</script>

<template>
  <div class="cs-wrapper" :style="{ height: props.height, width: props.width }">
    <div ref="contentRef" class="cs-content">
      <div ref="contentInnerRef" class="cs-content-inner">
        <slot></slot>
      </div>
    </div>
    <!-- 세로 스크롤바 -->
    <div
      v-show="hasVScroll"
      class="cs-bar is-vertical"
      role="scrollbar"
      tabindex="0"
      :class="{
        'is-active': isScrolling || isDragging,
        'is-always-show': props.alwaysShow,
      }"
      @mousedown="startDrag($event, 'v')"
      @touchstart="startDrag($event, 'v')"
    >
      <div ref="thumbVRef" class="cs-thumb"></div>
    </div>
    <!-- 가로 스크롤바 -->
    <div
      v-show="hasHScroll"
      class="cs-bar is-horizontal"
      :class="{
        'is-active': isScrolling || isDragging,
        'is-always-show': props.alwaysShow,
      }"
      @mousedown="startDrag($event, 'h')"
      @touchstart="startDrag($event, 'h')"
    >
      <div ref="thumbHRef" class="cs-thumb"></div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
/* --- 커스텀스크롤바 설정값 --- */
$sc-margin: 0; // 벽(테두리)에서 스크롤바까지의 간격
$sc-radius: 1px; // 스크롤바 곡률
$sc-thickness: 4px; // 실제 보여지는 스크롤바(선)의 두께
$sc-click-area: 8px; // 마우스 클릭/드래그가 인식되는 전체 너비
$sc-track-color: rgba(139, 143, 151, 0.1); // 배경 트랙 색상
$sc-thumb-color: var(--color-gray-400); // 기본 핸들 색상
$sc-hover-color: var(--color-gray-400); // 마우스 호버 시 색상
$sc-gap: v-bind('GAP + "px"'); // 스크롤바끼리 겹치지 않게 하는 여백(위에 script에서 수정)
$sc-side-gap: v-bind('SIDE_GAP + "px"'); // 시작점(위에 script에서 수정)

.cs-wrapper {
  position: relative;
  overflow: hidden;
  overscroll-behavior: none;

  display: flex; // 내부 요소 배치를 위해 무조건 필요
  flex-direction: column;
  // 핵심: 부모가 Flex라면 1을 가져가고, 아니면 무시됨
  flex: 1;
  // height는 props로 받은 값을 넣되, 100%가 기본이면 부모 높이를 따라감
  height: v-bind('props.height');
  width: v-bind('props.width');
  min-height: 0; // 스크롤 발생을 위한 절대 법칙
  position: relative;
  overflow: hidden;

  .cs-content {
    width: 100%;
    height: 100%;
    overflow: auto;
    -webkit-overflow-scrolling: touch;
    scrollbar-width: none;
    -ms-overflow-style: none;

    flex: 1; // wrapper 안에서 남은 공간을 다 써야 스크롤이 생김
    min-height: 0; // content 자체가 부모보다 커지는 걸 막음
    overflow: auto; // 실제 스크롤 발생 지점

    &::-webkit-scrollbar {
      display: none;
      width: 0;
      height: 0;
    }
  }

  .cs-bar {
    position: absolute;
    z-index: 999;
    opacity: 0; // 기본은 숨김
    transition: opacity 0.2s;
    background: transparent;
    -webkit-tap-highlight-color: transparent;

    // always-show prop이 true일 때 항상 보이게 함
    &.is-always-show {
      opacity: 1;
    }
    &.is-active {
      opacity: 1;
    }

    &::before {
      // 트랙(배경선)
      content: '';
      position: absolute;
      background: $sc-track-color;
      border-radius: $sc-radius;
    }

    .cs-thumb {
      // 실제 핸들
      position: absolute;
      background: $sc-thumb-color;
      border-radius: $sc-radius;
      transition: background 0.2s;

      &:hover {
        background: $sc-hover-color;
      }
    }

    /* 세로 바 설정 */
    &.is-vertical {
      top: $sc-side-gap; /* 위쪽 여백 */
      right: $sc-margin; // 설정한 margin만큼 띄움
      bottom: $sc-gap;
      width: $sc-click-area;
      cursor: pointer;

      &::before,
      .cs-thumb {
        width: $sc-thickness;
        right: 0; // 클릭 영역 내에서 오른쪽 정렬
        height: 100%;
      }
      &::before {
        height: 100%;
      }
    }

    /* 가로 바 설정 */
    &.is-horizontal {
      left: $sc-side-gap; /* 왼쪽 여백 */
      bottom: $sc-margin; // 설정한 margin만큼 띄움
      right: $sc-gap;
      height: $sc-click-area;
      cursor: pointer;

      &::before,
      .cs-thumb {
        height: $sc-thickness;
        bottom: 0; // 클릭 영역 내에서 바닥 정렬
        width: 100%;
      }
      &::before {
        width: 100%;
      }
    }
  }
}

@media (hover: hover) {
  .cs-wrapper:hover > .cs-bar {
    opacity: 1;
  }
}
</style>
