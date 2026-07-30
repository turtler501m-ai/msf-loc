<template>
  <div ref="checkboxGroupRef" :class="rootClasses" role="group">
    <MsfCheckbox
      v-for="option in optionList"
      :key="option.value"
      :model-value="props.modelValue"
      :value="option.value"
      :name="name"
      :label="option.label"
      :disabled="disabled || option.disabled"
      :error="error"
      @update:model-value="handleUpdate"
      @change="(e) => handleChange(e, option.value)"
    />
  </div>
</template>

<script setup>
import { computed, onBeforeMount, ref, useId, watch } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { isEmpty } from '@/libs/utils/string.utils'

const props = defineProps({
  /** 현재 선택된 값 (배열로 관리됨) */
  modelValue: {
    type: Array,
    default: () => [],
  },
  /** 체크박스 그룹명 */
  name: { type: String, default: () => `checkbox-radio-group-${useId()}` },
  /** 옵션 배열 */
  options: {
    type: Array,
    default: () => [],
  },
  /** 세로 정렬 여부 */
  vertical: Boolean,
  /** 그리드 정렬 여부 */
  grid: Boolean,
  /** 에러 상태 */
  error: Boolean,
  /** 그룹 전체 비활성화 */
  disabled: Boolean,
  groupCode: { type: String, default: '' },
})

// 이벤트 등록
const emit = defineEmits(['update:modelValue', 'change'])

const checkboxGroupRef = ref(null)

const optionList = ref(props.options)

/**
 * 단일 선택만 가능하도록 제어하는 핸들러
 */
const handleUpdate = (val) => {
  let newValue = val
  if (val.length === 0) {
    // 최소 1개는 선택 상태를 유지하도록 해제 방지
    newValue = props.modelValue
  } else if (val.length > 1) {
    // 선택된 개수가 1개를 초과하면 가장 마지막에 선택된 항목만 남김
    newValue = val.slice(-1)
  }
  emit('update:modelValue', newValue)
  emit('change', newValue)
}

const handleChange = (e, val) => {
  if (Array.isArray(props.modelValue) && props.modelValue.includes(val) && !e.target.checked) {
    e.target.checked = true
    e.preventDefault()
  }
}

// 스타일 클래스
const rootClasses = computed(() => [
  'checkbox-radio-group-root',
  {
    'is-vertical': props.vertical,
    'is-grid': props.grid,
    'is-error': props.error,
  },
])

const getOptionsByGroupCode = (groupCode) => {
  if (props.options?.length > 0) return props.options
  if (isEmpty(groupCode)) return []
  getCommonCodeList(groupCode).then((list) => {
    optionList.value = list.map((item) => ({ value: item.code, label: item.title }))
  })
}

watch(
  () => props.options,
  (newOptions) => {
    if (newOptions?.length > 0) {
      optionList.value = newOptions
    } else {
      getOptionsByGroupCode(props.groupCode)
    }
  },
  { immediate: true, deep: true },
)
watch(
  () => props.groupCode,
  (newGroupCode) => {
    if (isEmpty(newGroupCode)) return
    getOptionsByGroupCode(newGroupCode)
  },
  { immediate: true },
)

onBeforeMount(() => {
  getOptionsByGroupCode(props.groupCode)
})

defineExpose({
  focus: () => {
    checkboxGroupRef.value?.querySelector('input[type=checkbox]')?.focus()
  },
})
</script>

<style lang="scss" scoped>
.checkbox-radio-group-root {
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
    grid-template-columns: 1fr 1fr;
    gap: rem(16px);
    .checkbox-root {
      padding-block: rem(14px);
      padding-left: rem(16px);
    }
  }
}
</style>
