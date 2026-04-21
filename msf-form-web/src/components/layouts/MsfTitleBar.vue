<template>
  <div class="title-area">
    <h3 class="page-title">
      <slot name="title">{{ title || $route.meta.title }}</slot>
    </h3>
    <div class="title-side">
      <div class="action-slot">
        <slot name="actions"></slot>
      </div>
      <div class="breadcrumb-slot">
        <slot name="breadcrumb"></slot>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: String,
})
</script>

<style lang="scss" scoped>
.title-area {
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
