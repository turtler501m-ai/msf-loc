<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { getCommonCodeListWithDetail } from '@/libs/utils/comn.utils'

const props = defineProps({
  name: { type: String, default: 'inp-simType' },
  disabled: { type: Boolean, default: false },
  productType: { type: String, default: '' },
  prdtSctnCd: { type: String, default: '' },
})

const model = defineModel({ type: String })
const rawOptions = ref([])

const simTypeRef = ref(null)

// computed를 활용해 props에 따른 동적 disabled 상태 적용
const options = computed(() => {
  return rawOptions.value.map((opt) => {
    let isDisabled = props.disabled

    // productType이 'MM'일 때, prdtSctnCd가 'LTE'가 아니면 NFC 유심('08') 비활성화
    if (opt.value === '08' && props.productType === 'MM') {
      if (props.prdtSctnCd !== 'LTE') {
        isDisabled = true
      }
    }

    return {
      ...opt,
      disabled: isDisabled,
    }
  })
})

const validateAndSelectDefault = () => {
  if (options.value.length > 0) {
    // 유효하고 활성화(disabled가 아님)된 옵션인지 검증
    const isValid = options.value.some((opt) => opt.value === model.value && !opt.disabled)
    if (!model.value || !isValid) {
      const activeOpt = options.value.find((opt) => !opt.disabled)
      if (activeOpt) {
        model.value = activeOpt.value
      }
    }
  }
}

/**
 * usimProdInfo 공통코드 조회 및 가격 정보 포함 라벨 생성
 */
const fetchOptions = async () => {
  try {
    const list = await getCommonCodeListWithDetail('usimProdInfo')
    rawOptions.value = (list || []).map((item) => {
      return {
        label: `${item.title}`.trim(),
        value: item.code,
      }
    })

    validateAndSelectDefault()
  } catch (e) {
    console.error('Failed to fetch usimProdInfo options:', e)
  }
}

onMounted(() => {
  fetchOptions()
})

// 옵션 목록이나 동적 disabled 상태가 변경되면 기본 선택값 보정
watch(
  () => options.value,
  () => {
    validateAndSelectDefault()
  },
  { deep: true },
)

// 모델 값(초기값 및 임시저장 불러오기 등으로 모델 데이터가 비동기 업데이트될 때) 감시하여 보정
watch(
  () => model.value,
  () => {
    validateAndSelectDefault()
  },
)

defineExpose({
  focus: () => {
    simTypeRef.value?.focus()
  },
})
</script>

<template>
  <MsfChip ref="simTypeRef" v-model="model" :name="name" :data="options" />
</template>
