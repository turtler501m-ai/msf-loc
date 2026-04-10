<template>
  <div :class="rootClasses" role="radiogroup">
    <MsfRadio
      v-for="option in options"
      :key="option.value"
      :model-value="props.modelValue"
      :value="option.value"
      :name="name"
      :label="option.label"
      :disabled="disabled || option.disabled"
      :error="error"
      @update:model-value="handleUpdate"
    />
  </div>
</template>

<script setup>
import { computed, useId } from 'vue'

const props = defineProps({
  /** 현재 선택된 값 */
  modelValue: {
    type: [String, Number, Boolean, null],
    default: '',
  },
  /** 라디오 그룹명 (생략 시 자동 생성) */
  name: { type: String, default: () => `radio-group-${useId()}` },
  /** 옵션 배열 */
  options: {
    type: Array,
    default: () => [],
    // 구조: [{ value: 'opt1', label: '옵션 1', disabled: false }, ...]
  },
  /** 세로 정렬 여부 */
  vertical: Boolean,
  /** 그리드 정렬 여부 */
  grid: Boolean,
  /** 에러 상태 */
  error: Boolean,
  /** 그룹 전체 비활성화 */
  disabled: Boolean,
})

// 이벤트 등록
const emit = defineEmits(['update:modelValue', 'change'])

const handleUpdate = (val) => {
  emit('update:modelValue', val)
  emit('change', val)
}

// 스타일 클래스
const rootClasses = computed(() => [
  'radio-group-root',
  {
    'is-vertical': props.vertical,
    'is-grid': props.grid,
    'is-error': props.error,
  },
])
</script>

<style lang="scss" scoped>
.radio-group-root {
  display: flex;
  flex-wrap: wrap;
  gap: rem(24px);

  &.is-vertical {
    flex-direction: column;
    align-items: flex-start;
    gap: rem(24px);
  }
  &.is-grid {
    display: grid;
    /* 1:1 비율로 두 개의 열을 만듭니다 */
    grid-template-columns: 1fr 1fr;
    gap: rem(16px);
    .radio-root {
      padding-block: rem(14px);
      padding-left: rem(16px);
    }
  }
}
</style>
