<template>
  <div :class="rootClasses" role="radiogroup">
    <MsfRadio
      v-for="option in optionList"
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
import { computed, onBeforeMount, ref, useId, watch } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { isEmpty } from '@/libs/utils/string.utils'

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
  groupCode: { type: String, default: '' },
})

// 이벤트 등록
const emit = defineEmits(['update:modelValue', 'change'])

const optionList = ref(props.options)

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

const getOptionsByGroupCode = (groupCode) => {
  if (props.options?.length > 0) return props.options
  if (isEmpty(groupCode)) return []
  getCommonCodeList(groupCode).then((list) => {
    console.log('list:', list)
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
