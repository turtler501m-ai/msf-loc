<template>
  <div class="page-step-panel">
    <!-- SIM 정보 -->
    <div ref="productSimInfoWrapperRef">
      <MsfProductSimInfo
        v-model="formData.usimInfo"
        ref="productSimInfoRef"
        :customerData="formData.te_customer"
        v-model:data="formData"
        :authFlags="store.authFlags"
      />
    </div>
    <!-- // SIM 정보 -->
    <!-- 납부 정보 -->
    <div ref="billingInfoWrapperRef">
      <MsfBillingInfo
        v-model="formData.productPayment"
        ref="billingInfoRef"
        :customerData="formData.te_customer"
        v-model:authFlags="store.authFlags"
      />
    </div>
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
import { showAlert } from '@/libs/utils/comp.utils'

const store = useMsfFormOwnChgStore()
const { formData } = storeToRefs(store)

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const isComplete = ref(false)

// 컴포넌트 Refs
const productSimInfoRef = ref(null)
const billingInfoRef = ref(null)
const memoRef = ref(null)
// 포커스 이동용 래퍼 Refs (자식 컴포넌트가 multi-root라 $el로 전체 영역을 잡을 수 없어 별도로 감싼다)
const productSimInfoWrapperRef = ref(null)
const billingInfoWrapperRef = ref(null)

watch(
  () => [formData, store.authFlags],
  () => {
    checkRequiredFields()
  },
  { deep: true },
)

const checkRequiredFields = () => {
  const pending = []

  // 이메일 명세서 신청 시 가입자 이메일 입력 여부 먼저 체크
  const isEmailMissing =
    formData.value.productPayment &&
    formData.value.productPayment.cstmrBillSendTypeCd === 'CB' &&
    (!formData.value.te_customer?.emailAddr1 || !formData.value.te_customer?.emailAddr2)

  if (isEmailMissing) {
    pending.push('가입자 정보의 이메일을')
  }

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
  // 이메일 명세서 신청 시 가입자 이메일 입력 여부 검증
  const isEmailMissing =
    formData.value.productPayment &&
    formData.value.productPayment.cstmrBillSendTypeCd === 'CB' &&
    (!formData.value.te_customer?.emailAddr1 || !formData.value.te_customer?.emailAddr2)
  if (isEmailMissing) return false

  const getVal = (refObj) => {
    if (refObj.value && typeof refObj.value.validate === 'function') {
      return refObj.value.validate()
    }
    return true
  }

  const validations = [getVal(productSimInfoRef), getVal(billingInfoRef), getVal(memoRef)]
  return validations.every((v) => v === true)
}

// 검증 실패 필드 그룹 -> 스크롤 대상 래퍼
const GROUP_REF_MAP = {
  usimInfo: productSimInfoWrapperRef,
  productPayment: billingInfoWrapperRef,
}

// 검증 실패 필드(group, key) -> 실제 포커스할 인풋의 CSS 셀렉터
const FIELD_FOCUS_SELECTOR = {
  usimInfo: {
    hasSim: '[name="inp-hasSim"]',
    usimKindsCd: '[name="inp-simType"]',
    reqUsimSn: '#inp-reqUsimSn',
    simPurchaseMethod: '[name="inp-simPurchaseMethod"]',
  },
  productPayment: {
    cstmrBillSendTypeCd: '[name="inp-stmtType"]',
    reqPayTypeCd: '[name="inp-payMtd"]',
    reqBankCd: '#inp-reqBankCd',
    reqAccountNo: '#inp-autoAcctNo',
    reqAccountNm: '#inp-autoPayerName',
    reqAccountRrn: '#inp-autoPayerBirth',
    reqAccountRelTypeCd: '#inp-reqAccountRelTypeCd',
    isAutoAgree: '#inp-isAutoAgree',
    reqCardCompanyCd: '#inp-reqCardCompanyCd',
    reqCardNo: '#inp-cardNo',
    reqCardMm: '#inp-reqCardMm',
    reqCardYy: '#inp-reqCardYy',
    reqCardNm: '#inp-cardPayerName',
    reqCardRrn: '#inp-cardPayerBirth',
    cardRelation: '#inp-cardRelation',
    combId: '#inp-combId',
    combAgree: '#inp-combAgree',
  },
}

// 검증 실패한 필드로 스크롤/포커스 이동 (정확한 인풋을 못 찾으면 해당 컴포넌트 영역으로 대체 이동)
const focusGroup = (group, key) => {
  const wrapperEl = GROUP_REF_MAP[group]?.value
  if (!wrapperEl) return

  nextTick(() => {
    const selector = FIELD_FOCUS_SELECTOR[group]?.[key]
    const targetEl = (selector && wrapperEl.querySelector(selector)) || wrapperEl

    targetEl.scrollIntoView({ block: 'center', behavior: 'smooth' })
    targetEl.focus?.()
  })
}

const validateWithAlert = () => {
  const isEmailMissing =
    formData.value.productPayment &&
    formData.value.productPayment.cstmrBillSendTypeCd === 'CB' &&
    (!formData.value.te_customer?.emailAddr1 || !formData.value.te_customer?.emailAddr2)

  if (isEmailMissing) {
    showAlert('가입자 정보의 이메일을 입력해 주세요.')
    return false
  }
  return store.checkFieldValidation('PRODUCT', focusGroup)
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

const checkValidation = () => {
  if (productSimInfoRef.value?.checkValidation && !productSimInfoRef.value.checkValidation()) {
    console.log('productSimInfoRef.value.checkValidation()', false)
    return false
  }
  if (billingInfoRef.value?.checkValidation && !billingInfoRef.value.checkValidation()) {
    console.log('billingInfoRef.value.checkValidation()', false)
    return false
  }
  if (memoRef.value?.checkValidation && !memoRef.value.checkValidation()) {
    console.log('memoRef.value.checkValidation()', false)
    return false
  }

  return true
}

defineExpose({ save, validate, reset, validateWithAlert, checkValidation })
</script>

<style scoped></style>
