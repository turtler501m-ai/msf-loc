<template>
  <div class="page-step-panel">
    <!-- SIM 정보 -->
    <MsfProductSimInfo
      v-model="formData.usimInfo"
      ref="productSimInfoRef"
      :authFlags="store.authFlags"
    />
    <!-- // SIM 정보 -->
    <!-- 납부 정보 -->
    <MsfBillingInfo
      v-model="formData.productPayment"
      ref="billingInfoRef"
      :authFlags="store.authFlags"
    />
    <!-- // 납부 정보 -->
    <!-- 메모 -->
    <MsfMemo v-model="formData" ref="memoRef" />
    <!-- // 메모 -->
  </div>
</template>

<script setup>
import { useMsfFormOwnChgStore } from '@/stores/msf_ownerChange'
import { ref, watch, nextTick, onMounted } from 'vue'
import MsfMemo from '../common/MsfMemo.vue'
import { storeToRefs } from 'pinia'
import MsfBillingInfo from '../common/MsfBillingInfo.vue'

const store = useMsfFormOwnChgStore()
const { formData } = storeToRefs(store)

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const isComplete = ref(false)

// 컴포넌트 Refs
const productSimInfoRef = ref(null)
const billingInfoRef = ref(null)
const memoRef = ref(null)

watch(
  () => [formData, store.authFlags],
  () => {
    checkRequiredFields()
  },
  { deep: true },
)

const checkRequiredFields = () => {
  const pending = []

  const check = (refObj, label) => {
    if (refObj.value && typeof refObj.value.validate === 'function') {
      if (!refObj.value.validate()) {
        pending.push(label)
        return false
      }
    }
    return true
  }

  // 각 섹션별 유효성 검사 및 누락 항목 수집
  check(productSimInfoRef, '유심 정보')
  check(billingInfoRef, '납부 정보')
  check(memoRef, '메모 정보')

  const isReady = pending.length === 0

  if (!isReady) {
    console.log('%c[미입력 항목]:', 'color: #ff4d4f; font-weight: bold;', pending.join(', '))
  } else {
    console.log('%c[모든 입력 완료!]', 'color: #52c41a; font-weight: bold;')
  }

  isComplete.value = isReady
  emit('complete', isReady)
  return isReady
}

// 현재 단계(Product)의 모든 컴포넌트 유효성 검사
const validate = () => {
  const getVal = (refObj) => {
    if (refObj.value && typeof refObj.value.validate === 'function') {
      return refObj.value.validate()
    }
    return true
  }

  const validations = [getVal(productSimInfoRef), getVal(billingInfoRef), getVal(memoRef)]
  return validations.every((v) => v === true)
}

const save = async () => {
  return validate()
}

const reset = async () => {
  store.resetProduct()
  await nextTick()
  checkRequiredFields()
}

onMounted(() => {
  checkRequiredFields()
  store.validateProduct = validate
})

defineExpose({ save, validate, reset })
</script>

<style scoped></style>
