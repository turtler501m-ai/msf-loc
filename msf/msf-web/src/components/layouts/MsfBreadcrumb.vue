<template>
  <nav class="breadcrumbs">
    <ol>
      <li class="home-mark">
        <MsfIcon name="home" size="small" />
        <!-- <router-link to="/"><MsfIcon name="home" size="small" /></router-link> -->
      </li>
      <li v-for="(crumb, index) in breadcrumbs" :key="index">
        <span v-if="index > 0" class="divider"> <MsfIcon name="arrowRight" /></span>
        <router-link v-if="!crumb.active" :to="crumb.path">
          {{ crumb.label }}
        </router-link>
        <strong v-else>{{ crumb.label }}</strong>
      </li>
    </ol>
  </nav>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { menuData } from '@/components/layouts/menu' // 메뉴샘플

const route = useRoute()

// 현재 경로에 맞는 메뉴 계층(부모->자식)을 menuData에서 찾아 배열로 반환
const breadcrumbs = computed(() => {
  const findPath = (nodes, path, acc = []) => {
    for (const node of nodes) {
      if (node.path === path) return [...acc, node]
      if (node.children) {
        const res = findPath(node.children, path, [...acc, node])
        if (res) return res
      }
    }
  }
  return findPath(menuData, route.path) || []
})
</script>

<style lang="scss" scoped>
.breadcrumbs ol {
  @include flex($v: center);
  font-size: var(--font-size-14);
  color: var(--color-foreground);
  & > li:last-child {
    a {
      font-weight: var(--font-weight-bold);
    }
  }
  .divider {
    margin: 0 rem(4px);
    color: var(--color-gray-600);
  }
  a {
    text-decoration: none;
    color: inherit;
  }
  a:hover {
    text-decoration: underline;
  }
  strong {
    color: #333;
    font-weight: 700;
  }
  .home-mark {
    color: var(--color-gray-300);
    margin-right: rem(4px);
    line-height: 1;
  }
}
</style>
