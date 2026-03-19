<template>
  <div :class="rootClasses" role="group">
    <MsfCheckbox
      v-for="option in options"
      :key="option.value"
      v-model="model"
      :value="option.value"
      :name="name"
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
  type: Array,
  default: () => [],
})

const props = defineProps({
  /** 체크박스 그룹명 */
  name: { type: String, default: () => `checkbox-group-${useId()}` },
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

const handleChange = () => {
  emit('change', model.value)
}

// 스타일 클래스
const rootClasses = computed(() => [
  'checkbox-group-root',
  {
    'is-vertical': props.vertical,
    'is-error': props.error,
  },
])
</script>

<style lang="scss" scoped>
.checkbox-group-root {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;

  &.is-vertical {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
}
</style>
