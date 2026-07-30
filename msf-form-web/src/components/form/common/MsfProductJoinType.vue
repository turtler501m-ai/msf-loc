<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="상품" tag="div" required>
        <MsfChip
          ref="productTypeRef"
          v-model="model.productType"
          name="inp-product"
          :data="productTypeData"
        />
      </MsfFormGroup>
      <MsfFormGroup label="가입유형" tag="div" required>
        <MsfChip
          ref="joinTypeRef"
          v-model="model.joinType"
          name="inp-joinType"
          :data="filteredJoinTypeCodes"
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps, computed, watch, onMounted, ref } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'

const props = defineProps({
  title: { type: String, default: '가입유형 선택' },
  authFlags: { type: Object, default: () => ({}) },
  disabled: Boolean,
})
const emit = defineEmits(['change-product-type', 'change-join-type'])
const model = defineModel({ type: Object, required: true })
const newChgStore = useMsfFormNewChgStore()

const joinTypeCodes = ref([])
const rawProductTypeCodes = ref([])

const isAuthLocked = computed(
  () =>
    props.disabled || model.value?.isVerified || model.value?.isSaved || newChgStore.isDraftLoaded,
)

const productTypeData = computed(() =>
  rawProductTypeCodes.value.map((item) => ({ ...item, disabled: isAuthLocked.value })),
)

onMounted(async () => {
  const [operList, productList] = await Promise.all([
    getCommonCodeList('OPER_TYPE_CD'),
    getCommonCodeList('REQ_BUY_TYPE_CD'),
  ])
  joinTypeCodes.value = (operList || []).map((item) => ({
    ...item,
    label: item.title,
    value: item.code,
  }))
  rawProductTypeCodes.value = (productList || []).map((item) => ({
    value: item.code,
    label: item.title,
  }))
  if (model.value && !model.value.joinType) {
    model.value.joinType = 'MNP3'
  }
})

// USIM 상품 선택 시 기기변경(HDN3) 옵션 제외, 인증 완료 시 전체 비활성화
const filteredJoinTypeCodes = computed(() => {
  const base =
    model.value.productType === 'UU'
      ? joinTypeCodes.value.filter((code) => code.value !== 'HDN3')
      : joinTypeCodes.value
  return base.map((item) => ({ ...item, disabled: isAuthLocked.value }))
})

// 상품 변경 시 가입유형 기본값으로 초기화 (임시저장 로드 중에는 리셋 방지)
watch(
  () => model.value.productType,
  (newVal, oldVal) => {
    // 마운트 시 최초 세팅은 무시하고 실제로 값을 변경했을 때만 동작
    if (oldVal !== undefined && oldVal !== newVal) {
      if (newChgStore.isDraftLoading) return // 임시저장 불러오기 중에는 가입유형 리셋 방지
      emit('change-product-type', { newVal, oldVal })
      model.value.joinType = 'MNP3' // 기본값 번호이동으로 리셋
    }
  },
)

watch(
  () => model.value.joinType,
  (newVal, oldVal) => {
    if (oldVal !== undefined && oldVal !== newVal) {
      emit('change-join-type', { newVal, oldVal })
    }
  },
)

const validate = () => {
  if (!model.value.productType || !model.value.joinType) return false
  return true
}

const productTypeRef = ref(null)
const joinTypeRef = ref(null)
const checkValidation = () => {
  if (!model.value.productType) {
    showAlert('상품을 선택해주세요.', () => {
      productTypeRef.value?.focus()
    })
    return false
  }
  if (!model.value.joinType) {
    showAlert('가입유형을 선택해주세요.', () => {
      joinTypeRef.value?.focus()
    })
    return false
  }
  return true
}

defineExpose({ validate, checkValidation })
</script>
