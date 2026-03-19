<template>
  <div class="container-root">
    <MsfTitleBar>
      <template #title>
        <slot name="title">{{ currentMenuLabel }}</slot>
      </template>
      <template #breadcrumb>
        <MsfBreadcrumb />
      </template>
    </MsfTitleBar>
    <div class="page-content">
      <slot></slot>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { MsfTitleBar, MsfBreadcrumb } from '@/components/layouts'
import { menuData } from '@/components/layouts/menu' // 메뉴샘플

const route = useRoute()

// 현재 경로에 맞는 메뉴명을 menuData에서 찾아 한 줄로 반환
const currentMenuLabel = computed(() => {
  const find = (data, path) => {
    for (const n of data) {
      if (n.path === path) return n.label
      if (n.children) {
        const res = find(n.children, path)
        if (res) return res
      }
    }
    return route.meta?.title || ''
  }
  return find(menuData, route.path)
})
</script>

<style lang="scss" scoped>
.container-root {
  margin: 0 auto;
  width: 100%;
  box-sizing: border-box;
  padding-top: rem(88px);
  // 컨텐츠 내부
  .page-content {
    padding-block: rem(24px) rem(48px);
  }
}
</style>
