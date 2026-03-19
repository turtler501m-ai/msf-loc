<template>
  <aside class="lnb-container">
    <ul class="lnb-list">
      <MsfSideNavItem v-for="menu in menus.menus" :key="menu.id" :item="menu" />
    </ul>
  </aside>
</template>

<script setup>
import { MsfSideNavItem } from '@/components/layouts'
import { useMsfMenuStore } from '@/stores/msf_menu' // 메뉴샘플

const menus = useMsfMenuStore()
</script>

<style lang="scss" scoped>
.lnb-container {
  width: rem(240px);
  height: auto;
  background-color: var(--color-gray-50);
  overflow-y: auto;
  border-radius: var(--border-radius-m);
  border: var(--border-width-base) solid var(--color-gray-100);
  padding: rem(24px);
  position: sticky;
  top: rem(88px);
}
.lnb-list {
  margin: 0;
  padding: 0;
}
</style>
