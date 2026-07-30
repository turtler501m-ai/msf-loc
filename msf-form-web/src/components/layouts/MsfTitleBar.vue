<template>
  <div class="title-bar-root">
    <h3 class="page-title">
      <slot name="title">{{ title || $route.meta.title }}</slot>
    </h3>
    <div v-if="!!(slots.actions || slots.breadcrumb)" class="title-side">
      <div v-if="$slots.actions" class="action-slot">
        <slot name="actions"></slot>
      </div>
      <div v-if="$slots.breadcrumb" class="breadcrumb-slot">
        <slot name="breadcrumb"></slot>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useSlots } from 'vue'
const slots = useSlots()

defineProps({
  title: String,
})
</script>

<style lang="scss" scoped>
.title-bar-root {
  @include flex($v: center);
  margin-bottom: var(--spacing-x10);
  .page-title {
    flex: 1;
    margin: 0;
    font-size: var(--font-size-32);
    font-weight: var(--font-weight-bold);
    line-height: var(--line-height-fit);
    color: var(--color-foreground);
    @include ellipsis(1);
  }
}
</style>
