<template>
  <component :is="tag" class="grid-root" :style="{ '--gap': gap }">
    <slot></slot>
  </component>
</template>

<script setup>
defineProps({
  tag: { type: String, default: 'div' }, // section, article 등으로 변경 가능
  gap: { type: String, default: '1rem' }, // 기본값 16px
})
</script>

<style lang="scss" scoped>
.grid-root {
  display: grid;
  grid-template-columns: repeat(12, minmax(0, 1fr)); /* 12컬럼 고정 */
  gap: var(--gap);
  width: 100%;
}
</style>
