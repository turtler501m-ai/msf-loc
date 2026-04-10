<template>
  <li
    class="nav-item"
    :class="[`depth-${depth}`, { 'is-expanded': isOpen, 'is-active-root': isActiveRoot }]"
    :style="{ '--depth': depth }"
  >
    <div class="menu-row">
      <div class="menu-content" :class="`cont-depth-${depth}`">
        <router-link
          v-if="toUrl"
          :to="toUrl"
          class="menu-link"
          :class="{ 'router-link-exact-active': isActiveRoot }"
          :exact="item.url === '/'"
        >
          <MsfIcon v-if="item.iconName" :name="item.iconName" size="large" />
          <span class="menu-text">{{ item.name }}</span>
        </router-link>
        <span v-else class="menu-text">{{ item.name }}</span>
      </div>
    </div>
  </li>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'

const props = defineProps({
  item: Object,
  depth: { type: Number, default: 0 },
})

const route = useRoute()
const isOpen = ref(false)

// 현재 루트 메뉴가 활성 상태인지 판단 (CSS :has 대체용으로도 사용 가능)
// 홈('/')일 때는 정확히 일치해야 하고, 아닐 때는 하위 경로를 포함하는지 확인
const isActiveRoot = computed(() => {
  if (props.item.url === '/') {
    return route.path === '/'
  }
  return props.item.url ? route.path.startsWith(props.item.url) : false
})

const toUrl = computed(() => {
  // 1. 자식이 있고, 자식 중 url이 있는 경우 해당 url 사용
  const firstChildUrl = props.item.children?.find((v) => v.url)?.url
  if (firstChildUrl) return firstChildUrl

  // 2. 자식이 없거나 자식들에게 url이 없다면 본인의 url 반환
  return props.item.url || null
})
</script>

<style lang="scss" scoped>
.nav-item {
  flex: 1;
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
}
.menu-link {
  @include flex($d: column) {
    gap: rem(4px);
  }
}

/* --- 뎁스별 스타일링 --- */
.depth-0.nav-item {
  height: 100%;
}
.depth-0 > .menu-row {
  height: 100%;
  width: rem(64px);
  margin: 0 auto;
  position: relative;
}
// .router-link-active 대신 .router-link-exact-active 또는 스크립트에서 계산한 .is-active-root 클래스를 활용
.depth-0.is-active-root > .menu-row {
  &:before {
    content: '';
    width: 100%;
    height: rem(2px);
    background-color: var(--color-accent1-base);
    position: absolute;
    bottom: 0;
    left: 0;
  }
}
.depth-0 > .menu-row .menu-text,
.depth-0 > .menu-row .menu-link {
  font-size: var(--font-size-15);
  font-weight: var(--font-weight-medium);
  color: var(--color-gray-500);
}

/* 2Depth */
.depth-1 > .menu-row {
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
/* 활성화 강조 스타일 */
/* 현재 선택된 파일 (완전 일치) */
.menu-link.router-link-exact-active {
  color: var(--color-white) !important;
  .menu-text {
    color: var(--color-white) !important;
  }
}
/* 부모 카테고리 강조 */
.nav-item.is-active-root > .menu-row .menu-text,
.nav-item.is-active-root > .menu-row .menu-link {
  color: var(--color-primary-base);
  font-weight: var(--font-weight-medium);
}
.sub-menu {
  margin-top: var(--spacing-x4);
  overflow: hidden;
  @include flex($d: column) {
    gap: var(--spacing-x4);
  }
}
.expand-enter-active,
.expand-leave-active {
  transition:
    height 0.3s cubic-bezier(0.4, 0, 0.2, 1),
    opacity 0.3s;
}
</style>
