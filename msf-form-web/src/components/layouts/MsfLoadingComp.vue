<template>
  <div
    v-if="shouldRender"
    class="loading-overlay"
    :class="{
      'is-inline': props.inline,
      'is-spinner': props.spinner,
      'is-hidden': !isActualOpen,
      'is-active': isActualOpen,
    }"
    :style="inlineStyle"
    @transitionend="onTransitionEnd"
  >
    <div class="image-wrapper" :class="{ 'inline-spinner': isSpinnerMode }">
      <!-- 스피너 모드 -->
      <img v-if="isSpinnerMode" :src="loadingSpinnerImage" alt="Loading..." class="spin-img" />
      <!-- 브랜드 심볼 모드 -->
      <span v-else class="loading-image" role="img" aria-label="Loading...">
        <img
          :src="loadingSpriteImage"
          alt="Loading..."
          class="loading-sprite"
          :style="spriteStyle"
          draggable="false"
        />
      </span>
    </div>
  </div>
</template>

<script setup>
import { ref, onUnmounted, watch, nextTick, computed } from 'vue'
import { useScrollLock } from '@/hooks/useScrollLock'

// 브랜드 심볼 스프라이트 이미지: 별도 요청을 막기 위해 강제 인라인
import loadingSpriteImage from '@/assets/images/loading_sprite.png?inline'
// 스피너 이미지: 4KB 미만 자동 인라인 대상이지만, 항상 인라인되도록 명시
import loadingSpinnerImage from '@/assets/images/loading_spinner.svg?inline'

// 스프라이트 이미지의 프레임 크기
// CSS의 --loading-img-size 값과 동일하게 유지
const FRAME_SIZE = 60

// opacity transition이 발생하지 않는 경우를 위한 DOM 제거 대기 시간
const CLOSE_FALLBACK_DELAY = 250

const props = defineProps({
  /** 표시 여부: v-if 사용으로 인한 초기 DOM 렌더링을 위해 기본값 true 설정.
     (isOpen 로 로딩 띄울 시 로딩 애니메이션 전체 시간보다 빠르게 종료되어도 마지막 프레임 유지 후 제거) **/
  isOpen: { type: Boolean, default: true },
  /** 스피너로 표시여부 **/
  spinner: { type: Boolean, default: false },
  /** 로딩 표시 모드: true는 컨텐츠 영역 내 인라인 배치, false는 화면 전체 오버레이 **/
  inline: { type: Boolean, default: false },
  /** 인라인 모드(props.inline)에서 높이 제어 */
  height: { type: [String, Number], default: 'auto' },
})

// 스피너 노출모드
const isSpinnerMode = computed(() => props.inline || props.spinner)

// 이미지와 각 이미지별 유지 시간(ms) 설정 - 피그마(dev모드) 설정값
// 동일한 프레임이 연속되는 구간은 유지 시간을 합쳐서 설정
const loadingSequence = [
  { frame: 0, duration: 200 },
  { frame: 1, duration: 400 },
  { frame: 2, duration: 400 },
  { frame: 3, duration: 400 },
  { frame: 4, duration: 1000 }, // 마지막 이미지만 유지시간 다르게 설정(피그마설정값)
]

// 애니메이션 상태 관리 변수들
const currentIndex = ref(0) // 현재 재생 중인 이미지 인덱스
const isActualOpen = ref(false) // CSS 페이드인/아웃 제어 상태
const shouldRender = ref(false) // DOM 생성/제거를 위한 렌더링 제어

let animationTimer = null // 애니메이션 타이머 참조
let closeTimer = null // transition 미발생 대응 타이머
let startTime = 0 // 로딩 시작 시간 기록
let openRequestId = 0 // 비동기 open/close 요청 구분값

// 현재 스프라이트 프레임 위치
const spriteStyle = computed(() => {
  const frame = loadingSequence[currentIndex.value].frame

  return {
    transform: `translate3d(-${frame * FRAME_SIZE}px, 0, 0)`,
  }
})

// inline일 경우 높이지정
const inlineStyle = computed(() => {
  if (!props.inline) return {}

  const h = props.height
  // 숫자인지 확인(예: 300)하거나, 단위가 없는 문자열(예: '300')인 경우 px 추가
  const formattedHeight = /^\d+$/.test(String(h)) ? `${h}px` : h

  return { height: formattedHeight }
})

// 스크롤 잠금 훅 및 시퀀스 전체 재생 시간 계산
const { lock, unlock } = useScrollLock()
const totalDuration = loadingSequence.reduce((sum, item) => sum + item.duration, 0)

const clearAnimationTimer = () => {
  if (!animationTimer) return

  clearTimeout(animationTimer)
  animationTimer = null
}

const clearCloseTimer = () => {
  if (!closeTimer) return

  clearTimeout(closeTimer)
  closeTimer = null
}

// 가변 타이머 애니메이션 함수
const startAnimation = () => {
  clearAnimationTimer()

  const currentItem = loadingSequence[currentIndex.value]

  animationTimer = setTimeout(() => {
    // 다음 인덱스로 이동 (마지막이면 처음으로)
    currentIndex.value = (currentIndex.value + 1) % loadingSequence.length

    // 재귀 호출을 통해 다음 이미지의 duration 적용
    startAnimation()
  }, currentItem.duration)
}

// 로딩 표시 시작 및 스크롤 잠금 처리
const handleOpen = async () => {
  const requestId = ++openRequestId

  // 시작 전 이전 타이머 정리
  clearAnimationTimer()
  clearCloseTimer()

  shouldRender.value = true
  currentIndex.value = 0
  startTime = 0

  await nextTick() // DOM 생성 후 애니메이션 시작 보장

  // nextTick 대기 중 close 요청이 발생한 경우 open 처리 중단
  if (requestId !== openRequestId || !props.isOpen) {
    return
  }

  isActualOpen.value = true

  // 인라인 모드에서는 스크롤 잠금을 적용하지 않음
  if (!props.inline) {
    lock()
  }

  // 스피너는 CSS 애니메이션을 사용하므로 별도 타이머를 시작하지 않음
  if (isSpinnerMode.value) {
    return
  }

  startTime = Date.now()
  startAnimation()
}

// 로딩 종료 처리 및 페이드 아웃 후 DOM 정리
const handleClose = async () => {
  // 진행 중인 handleOpen 무효화
  const requestId = ++openRequestId

  clearAnimationTimer()
  clearCloseTimer()

  // 렌더링된 로딩이 없으면 종료 처리 생략
  if (!shouldRender.value) {
    startTime = 0

    if (!props.inline) {
      unlock()
    }

    return
  }

  const hasAnimationStarted = startTime > 0
  const elapsed = hasAnimationStarted ? Date.now() - startTime : totalDuration

  // 로딩 애니메이션 전체 시간보다 빠르게 종료되면 마지막 프레임 표시
  if (!isSpinnerMode.value && hasAnimationStarted && elapsed < totalDuration) {
    currentIndex.value = loadingSequence.length - 1

    await nextTick()

    // 마지막 프레임 반영 중 다시 open된 경우 기존 close 처리 중단
    if (requestId !== openRequestId || props.isOpen) {
      return
    }
  }

  // 인라인 모드에서는 스크롤 잠금을 적용하지 않음
  if (!props.inline) {
    unlock()
  }

  isActualOpen.value = false
  startTime = 0

  // 인라인 모드는 opacity transition을 사용하지 않으므로 즉시 DOM 제거
  if (props.inline) {
    shouldRender.value = false
    return
  }

  // transitionend가 발생하지 않는 경우 대응
  closeTimer = setTimeout(() => {
    shouldRender.value = false
    closeTimer = null
  }, CLOSE_FALLBACK_DELAY)
}

// 트랜지션 종료 시 DOM 제거를 위한 이벤트 핸들러
const onTransitionEnd = (event) => {
  // 이벤트 발생 대상이 overlay 자신이고 opacity transition인 경우에만 실행
  if (event.target !== event.currentTarget || event.propertyName !== 'opacity' || props.isOpen) {
    return
  }

  clearCloseTimer()
  shouldRender.value = false
}

// props 상태 변경 감지
watch(
  () => props.isOpen,
  (newVal) => {
    newVal ? handleOpen() : handleClose()
  },
  { immediate: true },
)

onUnmounted(() => {
  // 진행 중인 비동기 open/close 처리 무효화
  openRequestId += 1

  clearAnimationTimer()
  clearCloseTimer()

  // 인라인 모드에서는 스크롤 잠금을 적용하지 않음
  if (!props.inline) {
    unlock()
  }
})
</script>

<style lang="scss" scoped>
.loading-overlay {
  --loading-img-size: 60px;
  --loading-img-sprite-count: 5;
  --loading-img-sprite-width: calc(var(--loading-img-size) * var(--loading-img-sprite-count));

  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: var(--msf-app-height, 100vh);
  // background-color: rgba(0, 0, 0, 0.4);
  background-color: transparent;
  @include flex($h: center, $v: center);
  z-index: 9999999;

  // 페이드 효과 지정
  opacity: 1;
  transition: opacity 0.2s ease;
  &.is-hidden {
    opacity: 0;
    pointer-events: none;
  }

  // inline 인라인 형태
  &.is-inline {
    position: relative;
    width: 100%;
    height: auto;
    opacity: 1 !important;
    background: none;
  }
  // spinner 형태
  &.is-spinner {
    background: rgba(255, 255, 255, 0.6);
    .inline-spinner {
      &::before {
        display: none;
      }
    }
  }
}
.image-wrapper {
  position: relative;
  width: var(--loading-img-size);
  height: var(--loading-img-size);
  @include flex($h: center, $v: center);
}

.loading-overlay:not(.is-inline) .image-wrapper {
  width: 80px;
  height: 80px;
  isolation: isolate;

  &::before {
    position: absolute;
    inset: -12px;
    z-index: 0;
    background-color: rgba(255, 255, 255, 0.84);
    border: 1px solid rgba(255, 51, 76, 0.8);
    border-radius: 50%;
    content: '';
    animation: none;
  }
}

.loading-overlay.is-active:not(.is-inline) .image-wrapper::before {
  animation: LoadingPulse 1.2s ease-out infinite;
}
.loading-image {
  position: relative;
  z-index: 1;
  display: block;
  width: var(--loading-img-size);
  height: var(--loading-img-size);
  overflow: hidden;
  filter: drop-shadow(0 3px 8px rgba(0, 0, 0, 0.25));
}
.spin-img {
  position: relative;
  z-index: 1;
  pointer-events: none;
  user-select: none;
  -webkit-user-drag: none;
}
.loading-sprite {
  position: absolute;
  top: 0;
  left: 0;
  display: block;
  width: var(--loading-img-sprite-width);
  height: var(--loading-img-size);
  max-width: none;
  will-change: transform;
  pointer-events: none;
  user-select: none;
  -webkit-user-drag: none;
}

@keyframes LoadingPulse {
  0% {
    opacity: 0.9;
    transform: scale(0.9);
  }
  100% {
    opacity: 0;
    transform: scale(1.16);
  }
}

@media (prefers-reduced-motion: reduce) {
  .loading-overlay.is-active:not(.is-inline) .image-wrapper::before {
    animation: none;
  }
}
</style>
