<template>
  <div v-if="hasRequiredDocs">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="구비서류" tag="div" required>
        <MsfButton variant="subtle" @click="isModalOpen = true">스캔하기</MsfButton>
      </MsfFormGroup>
    </MsfStack>

    <!-- 구비서류 모달 -->
    <MsfRequiredDocModal v-model="isModalOpen" :form-data="model" @confirm="onConfirm" />
  </div>
</template>

<script setup>
import { ref, defineModel, defineProps, computed, defineExpose } from 'vue'
import MsfRequiredDocModal from './popups/MsfRequiredDocModal.vue'

const props = defineProps({
  title: { type: String, default: '구비서류' },
  authFlags: { type: Object, default: () => null },
})
const model = defineModel({ type: Object, required: true })
const isAllUploadedModel = defineModel('isAllUploaded', { type: Boolean, default: false })

const emit = defineEmits(['change'])
const isModalOpen = ref(false)

const checkBizNoLength = (b1, b2, b3) => {
  return (
    String(b1 || '').length === 3 && String(b2 || '').length === 2 && String(b3 || '').length === 5
  )
}

// 사업자번호가 3-2-5 자리 모두 입력되었는지 여부
const hasFullBizNo = computed(() => {
  if (!model.value) return false
  return (
    checkBizNoLength(
      model.value.cstmrJuridicalBizNo1,
      model.value.cstmrJuridicalBizNo2,
      model.value.cstmrJuridicalBizNo3,
    ) ||
    checkBizNoLength(model.value.tr_bizNo1, model.value.tr_bizNo2, model.value.tr_bizNo3) ||
    checkBizNoLength(model.value.te_bizNo1, model.value.te_bizNo2, model.value.te_bizNo3) ||
    (model.value.tr_customer &&
      checkBizNoLength(
        model.value.tr_customer.cstmrJuridicalBizNo1,
        model.value.tr_customer.cstmrJuridicalBizNo2,
        model.value.tr_customer.cstmrJuridicalBizNo3,
      )) ||
    (model.value.te_customer &&
      checkBizNoLength(
        model.value.te_customer.cstmrJuridicalBizNo1,
        model.value.te_customer.cstmrJuridicalBizNo2,
        model.value.te_customer.cstmrJuridicalBizNo3,
      ))
  )
})

// 현재 조건에 따라 필요한 구비서류 명칭 목록 반환
const getRequiredDocNames = () => {
  if (!model.value) return []
  const { cstmrTypeCd, cstmrVisitTypeCd, minorAgentNm, repRelation } = model.value
  const list = []

  // 1. 외국인
  if (['FN', 'FM'].includes(cstmrTypeCd)) list.push('외국인등록증/거소신고증')
  // 2. 미성년자
  if (['NM', 'FM'].includes(cstmrTypeCd)) list.push('가족관계증명서')

  // 3. 사업자
  // 내국인('NA')인 경우 사업자등록번호 3-2-5 자리가 모두 입력되야만 구비서류 항목(사업자등록증) 추가
  if (cstmrTypeCd === 'NA') {
    if (hasFullBizNo.value) list.push('사업자등록증')
  } else if (['PP', 'JP', 'GO'].includes(cstmrTypeCd) || hasFullBizNo.value) {
    // 그 외 사업자 유형은 기존 로직 유지 (항목이 있거나 코드가 맞으면 추가)
    list.push('사업자등록증')
  }

  // 4. 법인
  if (['JP', 'GO'].includes(cstmrTypeCd)) list.push('법인인감증명서')

  // 5. 대리인 방문
  if (
    cstmrVisitTypeCd === 'V2' ||
    model.value.tr_customer?.cstmrVisitTypeCd === 'V2' ||
    model.value.te_customer?.cstmrVisitTypeCd === 'V2'
  ) {
    list.push('대리인 신분증')
    if (['JP', 'GO'].includes(cstmrTypeCd)) {
      list.push('위임장', '대리인 재직증명서')
    }
  }

  // 6. 법정대리인 케이스
  if (!['NM', 'FM'].includes(cstmrTypeCd) && (minorAgentNm || repRelation)) {
    if (!list.includes('가족관계증명서')) list.push('가족관계증명서')
  }

  // 명의변경 양수고객은 내국인인 경우에도 구비서류 필요
  if (model.value.isTeCustomer && ['NA', 'FN'].includes(cstmrTypeCd)) {
    list.push('가족관계증명서')
  }

  return [...new Set(list)] // 중복 제거
}

const hasRequiredDocs = computed(() => {
  return getRequiredDocNames().length > 0
})

const onConfirm = ({ completedDocs, isAllUploaded }) => {
  // 스토어의 인증 플래그 업데이트
  if (props.authFlags) {
    props.authFlags.requiredDocs = isAllUploaded
  }

  isAllUploadedModel.value = isAllUploaded
  emit('change', isAllUploaded)

  // 업로드된 파일 정보를 모델에 저장 (필요 시)
  model.value.uploadedDocs = completedDocs

  console.log('구비서류 확인 완료:', isAllUploaded ? '모두 완료' : '일부 누락')
}

// 외부(부모)에서 호출할 수 있는 유효성 검사 함수
const validate = () => {
  if (hasRequiredDocs.value) {
    return props.authFlags
      ? props.authFlags.requiredDocs === true
      : isAllUploadedModel.value === true
  }
  return true
}

defineExpose({ validate, getRequiredDocNames })
</script>
