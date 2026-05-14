<template>
  <div class="page-step-panel">
    <!-- 고객 안내 사항 -->
    <MsfTitleArea title="고객 안내 사항" />
    <MsfBox>
      <ul class="agree-list">
        <li>
          <p class="agree-tit">혜택 소멸사항 동의</p>
          <MsfTextList
            :items="[
              '고객님께 제공되었던 각종 할인 등의 혜택이 모두 소멸되어 다양한 혜택을 제공받을 수 없게 됩니다.',
              '해지 시 DB 단체보험, 제주항공, 티머니 등 케이티 엠모바일의 제휴서비스가 제공되지 않습니다.',
            ]"
            type="number"
            level="2"
            margin="0"
            bottomDivider
          />
          <MsfCheckbox
            id="termination-benefit-agree"
            v-model="formData.agreeCheck1"
            label="본인은 위 혜택에 대한 설명을 듣고, 케이티 엠모바일 해지시 혜택 소멸되는 사항에 대해 동의합니다."
          />
        </li>
      </ul>
    </MsfBox>

    <!-- 신청서 확인 -->
    <MsfAppConfirm :disabled="!isAgreeChecked" @confirm="onConfirmApp" />

    <!-- [TEST] 화면 테스트용: ''이면 동의 정보 입력값 기준으로 판단 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발자용 페이지 프로세스</p>
        <select v-model="isComplete">
          <option value="">동의 정보</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watch, onMounted } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { storeToRefs } from 'pinia'
import { showAlert } from '@/libs/utils/comp.utils'

const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { formData } = storeToRefs(terminationStore)

const isComplete = ref('')
const isAppConfirmed = ref(false)

const isCheckedAgreement = (value) => value === true || value === 'Y'
const isAgreeChecked = computed(() => isCheckedAgreement(formData.value.agreeCheck1))

const isCompleteEffective = computed(() => {
  if (isComplete.value === 'true') return true
  if (isComplete.value === 'false') return false
  return isAgreeChecked.value && isAppConfirmed.value
})

const validate = () => isCompleteEffective.value

const focusField = (id) => {
  setTimeout(() => {
    document.getElementById(id)?.focus()
  }, 0)
}

const validateWithAlert = () => {
  if (!isAgreeChecked.value) {
    showAlert('혜택 소멸사항 동의가 필요합니다.', () => focusField('termination-benefit-agree'))
    return false
  }
  if (!isAppConfirmed.value) {
    showAlert('신청서 확인을 완료해 주세요.')
    return false
  }
  return true
}

const onConfirmApp = () => {
  isAppConfirmed.value = true
  console.log('[해지][동의확인] 버튼 클릭')
}

const checkRequiredFields = () => {
  emit('complete', isCompleteEffective.value)
}

watch(
  isCompleteEffective,
  () => {
    checkRequiredFields()
  },
  { immediate: true },
)

watch(isAgreeChecked, (checked) => {
  if (!checked) {
    isAppConfirmed.value = false
  }
})

watch(isComplete, () => {
  checkRequiredFields()
})

onMounted(() => {
  checkRequiredFields()
})

const save = async () => {
  console.log('[해지][동의정보저장] 요청 시작', {
    isAgreeChecked: isAgreeChecked.value,
    isAppConfirmed: isAppConfirmed.value,
    agreeCheck1: formData.value.agreeCheck1,
    agreeCheck2: formData.value.agreeCheck2,
    agreeCheck3: formData.value.agreeCheck3,
  })

  if (!validateWithAlert()) {
    console.warn('[해지][동의정보저장] 진행 중단', { reason: 'agreement incomplete' })
    return false
  }

  formData.value.agreeCheck2 = formData.value.agreeCheck1
  console.log('[해지][동의정보저장] 신청완료 호출')
  const result = await terminationStore.apiCompleteApplication()
  console.log('[해지][동의정보저장] 화면 데이터 반영 결과', { result })
  return result
}

const getCompleteErrorMessage = () => terminationStore.getCompleteErrorMessage()

defineExpose({ save, validate, validateWithAlert, getCompleteErrorMessage })
</script>

<style scoped></style>
