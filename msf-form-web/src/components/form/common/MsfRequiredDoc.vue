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
  authFlags: { type: Object, default: () => ({}) },
})
const model = defineModel({ type: Object, required: true })

const isModalOpen = ref(false)

const hasRequiredDocs = computed(() => {
  if (!model.value) return false

  const { cstmrTypeCd, cstmrVisitTypeCd, minorAgentNm, repRelation } = model.value

  // 외국인 및 외국인 미성년자
  if (['FN', 'FM'].includes(cstmrTypeCd)) return true
  // 미성년자
  if (['NM', 'FM'].includes(cstmrTypeCd)) return true

  // 사업자등록번호 입력
  const hasBizNo =
    (model.value.cstmrJuridicalBizNo1 &&
      model.value.cstmrJuridicalBizNo2 &&
      model.value.cstmrJuridicalBizNo3) ||
    (model.value.tr_bizNo1 && model.value.tr_bizNo2 && model.value.tr_bizNo3) ||
    (model.value.te_bizNo1 && model.value.te_bizNo2 && model.value.te_bizNo3)

  // 개인사업자/법인/관공서 또는 사업자등록번호 입력
  if (['PP', 'JP', 'GO'].includes(cstmrTypeCd) || hasBizNo) return true

  // 대리인 방문 시
  if (cstmrVisitTypeCd === 'V2') return true

  // 미성년자(법정대리인) 케이스 - 가입자 정보 등에 법정대리인 정보가 있을 때
  if (!['NM', 'FM'].includes(cstmrTypeCd) && (minorAgentNm || repRelation)) return true

  return false
})

const onConfirm = ({ completedDocs, isAllUploaded }) => {
  // 스토어의 인증 플래그 업데이트
  if (props.authFlags) {
    props.authFlags.requiredDocs = isAllUploaded
  }

  // 업로드된 파일 정보를 모델에 저장 (필요 시)
  model.value.uploadedDocs = completedDocs

  console.log('구비서류 확인 완료:', isAllUploaded ? '모두 완료' : '일부 누락')
}

// 외부(부모)에서 호출할 수 있는 유효성 검사 함수
const validate = () => {
  return true
}

defineExpose({ validate })
</script>
