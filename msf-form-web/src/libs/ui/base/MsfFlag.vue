<script setup>
import { computed } from 'vue'

const props = defineProps({
  data: {
    type: [Object, Array, String, Number],
    required: true,
  },
  size: {
    type: String,
    default: 'medium',
  },
  radius: {
    type: String,
    default: 'all',
  },
  color: {
    type: String,
    default: 'gray',
  },
  className: {
    type: String,
    default: '',
  },
  type: {
    type: String,
    default: 'block',
  },
})

const flags = computed(() => (Array.isArray(props.data) ? props.data : [props.data]))

// 클래스 분리
const rootClasses = computed(() => [
  'flag-root',
  props.size,
  props.radius ? `radius-${props.radius}` : '',
  props.type !== 'block' ? props.type : '',
  props.className,
])

const getItemData = (item) => {
  const isObject = typeof item === 'object' && item !== null && 'label' in item
  return {
    label: isObject ? item.label : item,
    color: isObject ? (item.color ?? props.color) : props.color,
    className: isObject ? item.className : '',
  }
}
</script>

<template>
  <div :class="rootClasses">
    <span
      v-for="(item, index) in flags"
      :key="index"
      class="flag-item"
      :class="[getItemData(item).color, getItemData(item).className]"
    >
      {{ getItemData(item).label }}
    </span>
  </div>
</template>

<style lang="scss" scoped>
.flag-root {
  --flag-min-width: #{rem(56px)};
  --flag-height: #{rem(24px)};
  --flag-font-size: var(--font-size-15);

  @include flex($display: inline-flex) {
    gap: rem(4px);
  }
  &.inline {
    display: inline-flex;
  }
  // 사이즈 정의
  &.small {
    --flag-min-width: #{rem(44px)};
    --flag-height: #{rem(18px)};
    --flag-font-size: var(--font-size-12);
  }
  &.medium {
    --flag-font-size: var(--font-size-15);
  }
  &.large {
    --flag-font-size: var(--font-size-16);
  }
  // 라운드 정의
  &.radius-all {
    .flag-item {
      border-radius: rem(4px);
    }
  }
  &.radius-tl {
    .flag-item {
      border-top-left-radius: rem(4px);
    }
  }
  .flag-item {
    @include flex($display: inline-flex, $v: center, $h: center);
    min-width: var(--flag-min-width);
    padding-inline: rem(8px);
    font-size: var(--flag-font-size);
    font-weight: var(--font-weight-regular);
    height: var(--flag-height);
    line-height: var(--flag-height);

    // 컬러 정의
    &.gray {
      color: var(--color-gray-600);
      background-color: var(--color-gray-150);
      border: var(--border-width-base) solid var(--color-gray-200);
    }
    // 민트
    &.accent {
      color: var(--color-white);
      background-color: var(--color-accent2-base);
      border: var(--border-width-base) solid var(--color-accent2-base);
    }
    // 빨강
    &.accent2 {
      color: var(--color-white);
      background-color: var(--color-accent-base);
      border: var(--border-width-base) solid var(--color-accent-base);
    }
  }
}
</style>
