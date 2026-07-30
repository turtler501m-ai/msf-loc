<template>
  <aside class="bottom-container">
    <h2 class="ut-blind">하단 메뉴</h2>
    <div class="bottom-list-wrapper">
      <ul class="bottom-list" ref="bottomListRef">
        <MsfBottomNavItem
          v-for="menu in menuStore.menus"
          :key="menu.id"
          :item="menu"
          :active-path="activePath"
          :navigate-to="navigateTo"
        />
        <li>
          <MsfButton iconOnly="logout" class="logout-btn" @click="onClickLogout"
            >로그아웃</MsfButton
          >
        </li>
      </ul>
      <!-- 활성화된 메뉴를 따라 움직이는 언더라인 바 -->
      <div class="sliding-underline" :style="underlineStyle"></div>
    </div>
  </aside>
</template>

<script setup>
import { ref, reactive, watch, nextTick, onMounted, onBeforeUnmount } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useMsfUserStore } from '@/stores/msf_user'
import { useMsfMenuStore } from '@/stores/msf_menu' // 메뉴
import { useMsfStepStore } from '@/stores/msf_step'
import { useMsfLoadingStore } from '@/stores/msf_loading'
import { post } from '@/libs/api/msf.api'
import { showConfirm } from '@/libs/utils/comp.utils'

const router = useRouter()
const route = useRoute()
const userStore = useMsfUserStore()
const menuStore = useMsfMenuStore()
const stepStore = useMsfStepStore()
const loadingStore = useMsfLoadingStore()
const activePath = ref(route.path)

// DOM 참조 및 언더라인 스타일 상태
const bottomListRef = ref(null)
const underlineStyle = reactive({
  width: '0px',
  transform: 'translateX(0px)',
  opacity: 0,
})

// 활성화된 메뉴의 위치를 계산해 언더라인을 이동시키는 함수
const updateUnderlinePosition = async () => {
  await nextTick()

  // 1. 부모 DOM이 아직 로드되지 않았다면 리턴
  if (!bottomListRef.value) return
  // 2. 활성화된 요소 탐색
  const activeLi = bottomListRef.value.querySelector('.depth-0.is-active-root')
  const activeContent = activeLi?.querySelector('.menu-link')
  // 3. 대상 엘리먼트가 확실히 존재할 때만 위치 계산 진행
  if (!activeContent) {
    underlineStyle.opacity = 0
    return
  }
  // 4. 위치 값 구조 분해 할당으로 가독성 향상
  const { left: parentLeft } = bottomListRef.value.getBoundingClientRect()
  const { left: activeLeft, width: activeWidth } = activeContent.getBoundingClientRect()
  // 5. 상수로 분리
  const PADDING_OFFSET = 0
  const leftOffset = activeLeft - parentLeft - PADDING_OFFSET
  const finalWidth = activeWidth + PADDING_OFFSET * 2
  // 6. 상태 업데이트
  underlineStyle.width = `${finalWidth}px`
  underlineStyle.transform = `translateX(${leftOffset}px)`
  underlineStyle.opacity = 1
}

const navigateTo = async (path) => {
  if (route.path === path) {
    return
  }

  const shouldWaitForLeaveConfirm =
    route.path.startsWith('/form/') && route.path !== path && !stepStore.isWorkNotice()

  if (shouldWaitForLeaveConfirm) {
    const result = await router.push(path)
    activePath.value = result ? route.path : path
    await updateUnderlinePosition()
    return
  }

  activePath.value = path
  await updateUnderlinePosition()

  const loadingGeneration = loadingStore.showLoading()
  try {
    const result = await router.push(path)
    if (result) {
      activePath.value = route.path
      await updateUnderlinePosition()
    }
  } finally {
    await nextTick()
    loadingStore.hideLoading(loadingGeneration)
  }
}

// 라우트 변경 감시 (페이지 이동 시 언더라인 이동)
watch(
  () => route.path,
  () => {
    activePath.value = route.path
    updateUnderlinePosition()
  },
  { immediate: true },
)

// 윈도우 리사이즈 대응 (화면 크기 바뀔 때 위치 재정렬)
onMounted(() => {
  window.addEventListener('resize', updateUnderlinePosition)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', updateUnderlinePosition)
})

// 로그아웃 버튼 클릭 핸들러
const onClickLogout = () => {
  showConfirm('로그아웃 하시겠습니까?', () => {
    post('/api/auth/logout').then(() => {
      userStore.clearUserInfo()
      router.push('/login')
    })
  })
}
</script>

<style lang="scss" scoped>
.bottom-container {
  width: 100%;
  height: var(--layout-bottom-nav-height);
  background-color: var(--color-primary-base);
  padding-inline: rem(24px);
  // 하단바높이(64px) + 디바이스 홈바 영역(safe-area) 처리
  @include safe-area(bottom, rem(64px), height);
  @include safe-area(bottom, 0px, padding);
  @include safe-area(left, 24px, padding);
  @include safe-area(right, 24px, padding);
}

// 언더라인의 absolute 기준점이 될 wrapper 추가
.bottom-list-wrapper {
  position: relative;
  width: 100%;
  max-width: var(--layout-max-width);
  height: 100%;
  margin: 0 auto;
}

.bottom-list {
  @include flex($v: center) {
    gap: rem(16px);
  }
  width: 100%;
  margin: 0 auto;
  height: 100%;
  text-align: center;
}
// 로그아웃 버튼 스타일 지정
.logout-btn {
  background: var(--color-gray-750);
  border-color: var(--color-gray-750);
  border-radius: var(--border-radius-m);
  color: var(--color-gray-150);
  width: rem(44px);
  height: rem(44px);
  padding: 0;
  :deep(i) {
    --icon-size: #{rem(24px)};
  }
  &:hover {
    background: var(--color-gray-750);
    border-color: var(--color-gray-750);
  }
}

/* 좌우로 스위핑되는 언더라인 스타일 바 */
.sliding-underline {
  position: absolute;
  bottom: 0;
  left: 0;
  height: rem(2px);
  background-color: var(--color-accent1-base);
  pointer-events: none; /* 클릭 이벤트 방해 차단 */

  // transform(위치)과 width(너비)가 변할 때 부드러운 가속도 애니메이션
  transition:
    transform 0.35s cubic-bezier(0.25, 1, 0.5, 1),
    width 0.35s cubic-bezier(0.25, 1, 0.5, 1),
    opacity 0.2s ease;
}
</style>
