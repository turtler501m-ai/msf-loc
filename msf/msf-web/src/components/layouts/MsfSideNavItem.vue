<template>
  <li
    class="nav-item"
    :class="[`depth-${depth}`, { 'is-expanded': isOpen }]"
    :style="{ '--depth': depth }"
  >
    <div class="menu-row" @click="toggleMenu">
      <div class="menu-content" :class="`cont-depth-${depth}`">
        <router-link v-if="item.url" :to="item.url" class="menu-link">
          {{ item.name }}
        </router-link>
        <span v-else class="menu-text">{{ item.name }}</span>
      </div>
      <span v-if="hasChildren" class="menu-icon">
        <MsfIcon :name="isOpen ? 'menuMinus' : 'menuPlus'" size="small" />
      </span>
    </div>
    <transition
      name="expand"
      @enter="enter"
      @after-enter="afterEnter"
      @leave="leave"
      @after-leave="afterLeave"
    >
      <ul v-if="hasChildren && isOpen" class="sub-menu">
        <MsfSideNavItem
          v-for="child in props.item.children"
          :key="child.label"
          :item="child"
          :depth="depth + 1"
        />
      </ul>
    </transition>
  </li>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { MsfSideNavItem } from '@/components/layouts'

const props = defineProps({
  item: Object,
  depth: { type: Number, default: 0 },
})

const route = useRoute()
const isOpen = ref(false)
const hasChildren = computed(() => props.item.children?.length > 0)

// 현재 경로 기반 자동 펼침
const checkAndExpand = () => {
  if (!props.item.children) return false

  // 자식들 중에 현재 URL과 일치하는 놈이 있는지 검사 (재귀)
  const hasActiveChild = (children) => {
    return children.some((child) => {
      if (child.path === route.path) return true
      if (child.children) return hasActiveChild(child.children)
      return false
    })
  }

  if (hasActiveChild(props.item.children)) {
    isOpen.value = true
  }
}

// 초기 로드(새로고침) 시 실행
onMounted(() => {
  checkAndExpand()
})

// 페이지 이동 시에도 체크 (다른 메뉴 클릭 시 자동으로 닫히지 않게 하거나 새로 열 때 필요)
watch(
  () => route.path,
  () => {
    checkAndExpand()
  },
)

const toggleMenu = () => {
  if (hasChildren.value) isOpen.value = !isOpen.value
}

// 슬라이딩
const enter = (el) => {
  el.style.height = '0'
  el.offsetHeight // reflow trigger
  el.style.height = el.scrollHeight + 'px'
}
const afterEnter = (el) => {
  el.style.height = 'auto'
}
const leave = (el) => {
  el.style.height = el.scrollHeight + 'px'
  el.offsetHeight // reflow trigger
  el.style.height = '0'
}
const afterLeave = (el) => {
  el.style.height = ''
}
</script>

<style lang="scss" scoped>
.nav-item {
  list-style: none;
  user-select: none;
}
.menu-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
  transition: all 0.2s ease;
}
.menu-content {
  flex: 1;
  display: flex;
  align-items: center;
}
.menu-link,
.menu-text {
  text-decoration: none;
  width: 100%;
  display: block;
  color: inherit;
}

/* --- 뎁스별 스타일링 --- */
/* 1Depth */
.depth-0.nav-item {
  border-bottom: var(--border-width-base) solid var(--color-gray-100);
  padding-block: var(--spacing-x4);
  &:first-child {
    padding-top: 0;
  }
  &:last-child {
    border-bottom: none;
    padding-bottom: 0;
  }
}
.depth-0 > .menu-row {
  /* background-color: #ffffff; */
  min-height: rem(34px);
}
.depth-0 > .menu-row .menu-text,
.depth-0 > .menu-row .menu-link {
  font-size: var(--font-size-18);
  font-weight: var(--font-weight-regular);
  color: var(--color-foreground);
}
/* 2Depth */
.depth-1 > .menu-row {
  /* background-color: #f9f9f9; */
  .menu-link {
    position: relative;
    padding-left: rem(16px);
    &::before {
      content: '-';
      position: absolute;
      top: 0;
      left: 0;
      font-weight: var(--font-weight-regular);
    }
  }
}
.depth-1 > .menu-row .menu-text,
.depth-1 > .menu-row .menu-link {
  font-size: var(--font-size-16);
  font-weight: var(--font-weight-regular);
  color: var(--color-gray-600);
}
/* 3Depth 이상 */
.depth-2 > .menu-row {
  /* background-color: #f2f2f2; */
}
.depth-2 > .menu-row .menu-text,
.depth-2 > .menu-row .menu-link {
  font-size: var(--font-size-14);
  font-weight: var(--font-weight-regular);
  color: var(--color-gray-600);
}
/* Hover 상태 */
.menu-row:hover {
}

/* --- 활성화 강조 스타일 --- */
/* 1. 현재 선택된 파일(Exact Active) */
.menu-link.router-link-exact-active {
  color: var(--color-primary-base) !important;
  font-weight: var(--font-weight-bold) !important;
}
.depth-1 .menu-link.router-link-exact-active,
.depth-2 .menu-link.router-link-exact-active {
  text-decoration: underline;
}
/* 2. 현재 선택된 메뉴를 포함하고 있는 부모 카테고리 강조 */
.nav-item:has(.router-link-exact-active) > .menu-row .menu-text,
.nav-item:has(.router-link-exact-active) > .menu-row .menu-link {
  color: var(--color-primary-base);
  font-weight: var(--font-weight-bold);
}
/* menu-icon */
.menu-icon {
  color: var(--color-gray-450);
  transition: transform 0.3s ease;
}
.sub-menu {
  margin-top: var(--spacing-x4);
  overflow: hidden;
  @include flex($d: column) {
    gap: var(--spacing-x4);
  }
}
/* Transition 클래스 */
.expand-enter-active,
.expand-leave-active {
  transition:
    height 0.3s cubic-bezier(0.4, 0, 0.2, 1),
    opacity 0.3s;
}
</style>
