<template>
  <component :is="tag" class="grid-item-root" :style="itemStyles">
    <slot></slot>
  </component>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  tag: { type: String, default: 'div' },
  col: { type: [Number, String], default: 12 }, // 데스크탑 (기본 전체)
  tab: { type: [Number, String], default: null }, // 태블릿 (미지정 시 데스크탑과 동일하게 지나감)
  mob: { type: [Number, String], default: 12 }, // 모바일 (기본 전체)
})

const itemStyles = computed(() => ({
  '--col': props.col,
  '--tab': props.tab || props.col,
  '--mob': props.mob,
}))
</script>

<style lang="scss" scoped>
.grid-item-root {
  grid-column: span var(--col) / span var(--col);
}

@media (max-width: 1024px) {
  .grid-item-root {
    grid-column: span var(--tab) / span var(--tab);
  }
}

@media (max-width: 768px) {
  .grid-item-root {
    grid-column: span var(--mob) / span var(--mob);
  }
}
</style>
