<template>
  <div class="loading-overlay">
    <div class="image-wrapper">
      <img :src="loadingSequence[currentIndex].src" alt="Loading..." class="loading-image" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useScrollLock } from '@/hooks/useScrollLock'

// 로딩 디자인 이미지 임포트
import img1 from '@/assets/images/loading_1.svg'
import img2 from '@/assets/images/loading_2.svg'
import img3 from '@/assets/images/loading_3.svg'
import img4 from '@/assets/images/loading_4.svg'
import img5 from '@/assets/images/loading_5.svg'

// 이미지와 각 이미지별 유지 시간(ms) 설정 - 피그마(dev모드) 설정값
const loadingSequence = [
  { src: img1, duration: 200 },
  { src: img2, duration: 200 },
  { src: img2, duration: 200 },
  { src: img3, duration: 200 },
  { src: img3, duration: 200 },
  { src: img4, duration: 200 },
  { src: img4, duration: 200 },
  { src: img5, duration: 1000 }, // 마지막 이미지만 유지시간 다르게 설정(피그마설정값)
]

const currentIndex = ref(0)
let timer = null

const { lock, unlock } = useScrollLock()

const updateVh = () => {
  const vh = window.innerHeight * 0.01
  document.documentElement.style.setProperty('--layout-vh', `${vh}px`)
}

// 가변 타이머 애니메이션 함수
const startAnimation = () => {
  const currentItem = loadingSequence[currentIndex.value]

  timer = setTimeout(() => {
    // 다음 인덱스로 이동 (마지막이면 처음으로)
    currentIndex.value = (currentIndex.value + 1) % loadingSequence.length

    // 재귀 호출을 통해 다음 이미지의 duration 적용
    startAnimation()
  }, currentItem.duration)
}

onMounted(() => {
  lock()
  updateVh()
  window.addEventListener('resize', updateVh)

  // 애니메이션 시작
  startAnimation()
})

onUnmounted(() => {
  unlock()
  window.removeEventListener('resize', updateVh)

  // setTimeout 해제
  if (timer) clearTimeout(timer)
})
</script>

<style lang="scss" scoped>
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: calc(var(--layout-vh, 1vh) * 100);
  background-color: rgba(0, 0, 0, 0.6);
  @include flex($h: center, $v: center);
  z-index: 9999;
}
.image-wrapper {
  position: relative;
  width: rem(100px);
  height: rem(100px);
  @include flex($h: center, $v: center);
}
.loading-image {
  width: 100%;
  height: auto;
  object-fit: contain;
}
</style>
