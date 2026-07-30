<script setup>
import { ref, onMounted, watch } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'

const props = defineProps({
  name: { type: String, default: 'inp-simPurchaseMethod' },
  disabled: { type: Boolean, default: false },
})

const model = defineModel({ type: String })
const options = ref([])

const simPurchaseMethodRef = ref(null)

const validateAndSelectDefault = () => {
  if (options.value.length > 0) {
    const isValid = options.value.some((opt) => opt.value === model.value)
    if (!model.value || !isValid) {
      model.value = options.value[0].value
    }
  }
}

/**
 * USIM_PYMN_MTHD_CD 공통코드 조회 및 초기값 설정
 */
const fetchOptions = async () => {
  try {
    const list = await getCommonCodeList('USIM_PYMN_MTHD_CD', true)
    options.value = (list || [])
      .filter((item) => item.detail?.etcValue1 === 'Y')
      .map((item) => ({
        label: item.title,
        value: item.code,
        disabled: props.disabled,
      }))

    validateAndSelectDefault()
  } catch (e) {
    console.error('Failed to fetch USIM_PYMN_MTHD_CD options:', e)
  }
}

onMounted(() => {
  fetchOptions()
})

// 모델 값(초기값 및 임시저장 불러오기 등으로 모델 데이터가 비동기 업데이트될 때) 감시하여 보정
watch(
  () => model.value,
  () => {
    validateAndSelectDefault()
  },
)

// 부모의 disabled 상태가 변경되면 옵션의 disabled 상태도 동기화
watch(
  () => props.disabled,
  (newVal) => {
    options.value = options.value.map((opt) => ({ ...opt, disabled: newVal }))
  },
)

defineExpose({
  focus: () => {
    simPurchaseMethodRef.value?.focus()
  },
})
</script>

<template>
  <MsfChip ref="simPurchaseMethodRef" v-model="model" :name="name" :data="options" />
</template>
