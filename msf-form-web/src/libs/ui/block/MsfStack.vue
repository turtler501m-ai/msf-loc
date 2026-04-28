<script setup>
import { computed } from 'vue'

const props = defineProps({
  /** 타입 */
  type: {
    type: String,
    default: 'default',
    validator: (v) => ['default', 'field', 'formgroups'].includes(v),
  },
  /** 수직 정렬 여부  */
  vertical: {
    type: Boolean,
    default: false,
  },
  /** 간격 설정 */
  gap: {
    type: [String, Number],
    default: 1,
  },
  /** 자동으로 요소 줄바꿈처리 안함 여부  */
  nowrap: {
    type: Boolean,
    default: false,
  },
})

const rootClasses = computed(() => [
  'stack-root',
  props.type !== 'default' ? props.type : '',
  { 'is-vertical': props.vertical, 'is-nowrap': props.nowrap },
  `gap-${props.gap}`,
])
</script>

<template>
  <div :class="rootClasses">
    <slot></slot>
  </div>
</template>

<style lang="scss" scoped>
.stack-root {
  box-sizing: border-box;
  @include flex($v: center, $w: wrap);
  &.is-vertical {
    flex-direction: column;
    align-items: flex-start;
  }
  &.is-nowrap {
    flex-wrap: nowrap;
  }
  // 타입
  &.field {
    gap: rem(8px);
    &:not(.column) {
      align-items: center;
      :deep([class^='select-root']) {
        flex: 0 0 auto;
        width: var(--formgroup-inner-inline-width);
      }
      :deep([class^='btn-root']) {
        flex: 0 0 auto;
        width: auto;
        white-space: nowrap;
        height: rem(52px);
      }
    }
  }
  // formgroup 여러개 묶기
  &.formgroups {
    gap: rem(24px) !important;
    :deep([class^='input-root']) {
      flex: 0 0 auto;
      width: var(--formgroup-inner-inline-width);
    }
    :deep([class^='select-root']) {
      flex: 0 0 auto;
      width: var(--formgroup-inner-inline-width);
    }
  }
  // 정렬
  &.between {
    justify-content: space-between;
  }
  &.end {
    justify-content: flex-end;
  }

  // Gap
  &.gap-0 {
    gap: 0;
  }
  &.gap-1 {
    gap: rem(8px);
  }
  &.gap-2 {
    gap: rem(12px);
  }
  &.gap-3 {
    gap: rem(16px);
  }
  &.gap-4 {
    gap: rem(24px);
  }

  // 내부 최초 너비 고정값 지정
  :deep([class^='input-root']) {
    flex: 0 0 auto;
    width: var(--formgroup-inner-inline-width);
  }
  :deep([class^='select-root']) {
    flex: 0 0 auto;
    width: rem(300px);
  }
  // 폼그룹 내부에 체크박스,라디오 의 기본높이 레이블과 맞춰서 지정
  :deep([class^='checkbox-group-root']),
  :deep([class^='radio-group-root']) {
    row-gap: 0;
    column-gap: rem(24px);
  }
  :deep([class^='checkbox-root']),
  :deep([class^='radio-root']) {
    min-height: var(--form-group-min-height);
    /* 전체 높이에서 글자 높이를 뺀 절반 */
    padding-top: calc((var(--form-group-min-height) - 1em) / 2);
  }
}
</style>
