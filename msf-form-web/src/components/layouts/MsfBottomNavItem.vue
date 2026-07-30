<template>
  <li
    class="nav-item"
    :class="[`depth-${depth}`, { 'is-expanded': isOpen, 'is-active-root': isActiveRoot }]"
    :style="{ '--depth': depth }"
  >
    <div class="menu-row">
      <div class="menu-content" :class="`cont-depth-${depth}`">
        <a
          v-if="toUrl"
          :href="toUrl"
          class="menu-link"
          :class="{ 'router-link-exact-active': isActiveRoot }"
          @click.prevent="onClickMenu"
        >
          <MsfIcon v-if="item.iconName" :name="item.iconName" size="large" />
          <span class="menu-text">{{ item.name }}</span>
        </a>
        <span v-else class="menu-text">{{ item.name }}</span>
      </div>
    </div>
  </li>
</template>

<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
  item: Object,
  depth: { type: Number, default: 0 },
  activePath: { type: String, default: '' },
  navigateTo: { type: Function, default: null },
})

const isOpen = ref(false)

// 현재 루트 메뉴가 활성 상태인지 판단 (CSS :has 대체용으로도 사용 가능)
// 홈('/')일 때는 정확히 일치해야 하고, 아닐 때는 하위 경로를 포함하는지 확인
const isActiveRoot = computed(() => {
  if (props.item.url === '/') {
    return props.activePath === '/'
  }
  return props.item.url ? props.activePath.startsWith(props.item.url) : false
})

const toUrl = computed(() => {
  // 1. 자식이 있고, 자식 중 url이 있는 경우 해당 url 사용
  const firstChildUrl = props.item.children?.find((v) => v.url)?.url
  if (firstChildUrl) return firstChildUrl

  // 2. 자식이 없거나 자식들에게 url이 없다면 본인의 url 반환
  return props.item.url || null
})

const onClickMenu = async () => {
  if (!toUrl.value) return
  await props.navigateTo?.(toUrl.value)
}
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
  -webkit-touch-callout: none; // iOS 길게 눌렀을 때 링크 메뉴 방지
  -webkit-user-select: none; // 텍스트 선택 방지
  user-select: none;
}

/* --- 뎁스별 스타일링 --- */
.depth-0.nav-item {
  height: 100%;
}
.depth-0 > .menu-row {
  height: 100%;
  // width: rem(64px);
  width: rem(80px); // 안드로이드 줄바꿈현상 확인
  margin: 0 auto;
  position: relative;
}
// .router-link-active 대신 .router-link-exact-active 또는 스크립트에서 계산한 .is-active-root 클래스를 활용
// .depth-0.is-active-root > .menu-row {
//   &:before {
//     content: '';
//     width: 100%;
//     height: rem(2px);
//     background-color: var(--color-accent1-base);
//     position: absolute;
//     bottom: 0;
//     left: 0;
//   }
// }
.depth-0 > .menu-row .menu-text,
.depth-0 > .menu-row .menu-link {
  font-size: var(--font-size-15);
  font-weight: var(--font-weight-medium);
  color: var(--color-gray-500);
  // 웹접근성 명도대비 수정 시 - 디자인 지정 컬러 (color-gray-300, 350, 400, 450 지정가능)
  // color: var(--color-gray-300);
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
