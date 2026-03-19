<template>
  <div :class="rootClasses" role="radiogroup">
    <MsfRadio
      v-for="option in options"
      :key="option.value"
      v-model="model"
      :name="name"
      :value="option.value"
      :label="option.label"
      :disabled="disabled || option.disabled"
      :error="error"
      @change="handleChange"
    />
  </div>
</template>

<script setup>
import { computed, useId } from 'vue'

const model = defineModel({
  type: [String, Number, Boolean, null],
  default: '',
})

const props = defineProps({
  /** 현재 선택된 값 (v-model) */
  modelValue: { type: [String, Number, Boolean, null], default: '' },
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
  /** 에러 상태 */
  error: Boolean,
  /** 그룹 전체 비활성화 */
  disabled: Boolean,
})

// 이벤트 등록
const emit = defineEmits(['change'])

const handleChange = (val) => {
  emit('change', val)
}

// 스타일 클래스
const rootClasses = computed(() => [
  'radio-group-root',
  {
    'is-vertical': props.vertical,
    'is-error': props.error,
  },
])
</script>

<style lang="scss" scoped>
.radio-group-root {
  display: flex;
  flex-wrap: wrap;
  gap: rem(16px);

  &.is-vertical {
    flex-direction: column;
    align-items: flex-start;
    gap: rem(16px);
  }
}
</style>
