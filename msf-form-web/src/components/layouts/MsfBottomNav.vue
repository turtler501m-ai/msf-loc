<template>
  <aside class="bottom-container">
    <h2 class="ut-blind">하단 메뉴</h2>
    <ul class="bottom-list">
      <MsfBottomNavItem v-for="menu in menuStore.menus" :key="menu.id" :item="menu" />
      <li><MsfButton iconOnly="logout" class="logout-btn">로그아웃</MsfButton></li>
    </ul>
  </aside>
</template>

<script setup>
import { MsfBottomNavItem } from '@/components/layouts'
import { useMsfMenuStore } from '@/stores/msf_menu' // 메뉴

const menuStore = useMsfMenuStore()
</script>

<style lang="scss" scoped>
.bottom-container {
  width: 100%;
  height: rem(64px);
  background-color: var(--color-primary-base);
  padding-inline: rem(24px);
  // 하단바높이(64px) + 디바이스 홈바 영역(safe-area) 처리
  @include safe-area(bottom, rem(64px), height);
  @include safe-area(bottom, 0px, padding);
  @include safe-area(left, 24px, padding);
  @include safe-area(right, 24px, padding);
}

.bottom-list {
  @include flex($v: center) {
    gap: rem(16px);
  }
  width: 100%;
  max-width: var(--layout-max-width);
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
</style>
