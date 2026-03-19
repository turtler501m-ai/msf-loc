<template>
  <div :class="rootClasses">
    <slot></slot>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  /** 세로 방향 여부 */
  vertical: Boolean,
  /** 상단마진: 0, 1, 2, 3 */
  margin: {
    type: [String, Number],
    default: '0',
    validator: (v) => ['0', '1', '2', '3'].includes(v),
  },
  /** 버튼 정렬 변경 */
  alignItem: {
    type: String,
    default: 'left',
    validator: (v) => ['left', 'center', 'right'].includes(v),
  },
})

// 루트 클래스
const rootClasses = computed(() => [
  'button-area-root',
  `mt${props.margin}`,
  `align-${props.alignItem}`,
  {
    'is-vertical': props.vertical,
  },
])
</script>

<style lang="scss" scoped>
.button-area-root {
  display: flex;
  align-items: center;
  gap: var(--spacing-x1);
  margin-top: var(--layout-gutter-y);
  &.align-left {
    justify-content: flex-start;
  }
  &.align-center {
    justify-content: center;
  }
  &.align-right {
    justify-content: flex-end;
  }
  &:global(.ut-align-vertical) {
    align-items: flex-start;
  }
  :where(p) {
    margin-top: 0;
  }
  &.is-vertical {
    flex-direction: column;
    & > * {
      width: 100%;
    }
  }
  // buttonArea 다음에 위치한 테이블의 마진상단값 조절
  & + :deep(.base-table-root) {
    margin-top: var(--spacing-x2);
  }
}

// margin props
.mt0 {
  margin-top: 0;
}
.mt1 {
  margin-top: var(--spacing-x10);
}
.mt2 {
  margin-top: var(--spacing-x14);
}
.mt3 {
  margin-top: var(--spacing-x6);
}
</style>
