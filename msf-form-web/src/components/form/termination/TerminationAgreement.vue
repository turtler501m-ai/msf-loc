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
              '고객님께서 제공하셨던 선택약인 및 각종 결합약인 혜택이 모두 소멸되어 다양한 혜택을 제공받을 수가 없습니다.',
              '해지 후 DB 완전복원, 재신청 처리, 데이터복구, 유심 변경 서비스가 제공되지 않습니다.',
            ]"
            type="number"
            level="2"
            margin="0"
            bottomDivider
          />
          <MsfCheckbox
            id="termination-benefit-agree"
            v-model="formData.agreeCheck1"
            label="본인또는 대리인은 위의 사항을 확인하였고, 케이티모바일에서 제공한 혜택 소멸에 동의합니다."
          />
        </li>
      </ul>
    </MsfBox>
    <!-- // 고객 안내 사항 -->
    <!-- 요청서 확인 -->
    <MsfAppConfirm @confirm="console.log('요청서 확인 버튼 클릭!!')" />
    <!-- // 요청서 확인 -->

    <!-- (화면테스트용 테스트영역) 추후 지워질수도 있는것-->
    <div class="ut-mt-50">
      <div>
        <p>- 개발자주석 부분- 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">동의 정보</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 테스트영역) 추후 지워질수도 있는것-->
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

// [TEST] 화면 테스트용: ''이면 동의 정보 입력값 기준으로 판단
const isComplete = ref('')

const isCompleteEffective = computed(() => {
  if (isComplete.value === 'true') return true
  if (isComplete.value === 'false') return false
  return !!formData.value.agreeCheck1
})

watch(
  isCompleteEffective,
  (newVal) => {
    emit('complete', newVal)
  },
  { immediate: true },
)

onMounted(() => {
  emit('complete', isCompleteEffective.value)
})

const validate = () => !!formData.value.agreeCheck1

const focusField = (id) => {
  setTimeout(() => {
    document.getElementById(id)?.focus()
  }, 0)
}

const validateWithAlert = () => {
  if (!formData.value.agreeCheck1) {
    showAlert('혜택 환수 안내사항 동의가 필요합니다.', () =>
      focusField('termination-benefit-agree'),
    )
    return false
  }
  return true
}

const save = async () => {
  console.log('[서비스해지][동의정보저장] 요청 시작', {
    isComplete: isComplete.value,
    agreeCheck1: formData.value.agreeCheck1,
    agreeCheck2: formData.value.agreeCheck2,
    agreeCheck3: formData.value.agreeCheck3,
  })
  if (isComplete.value === 'false') {
    console.warn('[서비스해지][동의정보저장] 진행 중단', { reason: 'agreement incomplete' })
    return false
  }
  if (!validateWithAlert()) {
    console.warn('[서비스해지][동의정보저장] 진행 중단', { reason: 'agreement incomplete' })
    return false
  }
  formData.value.agreeCheck2 = formData.value.agreeCheck1
  console.log('[서비스해지][동의정보저장] 신청완료 호출')
  const result = await terminationStore.apiCompleteApplication()
  console.log('[서비스해지][동의정보저장] 화면 데이터 반영 결과', { result })
  return result
}

const getCompleteErrorMessage = () => terminationStore.getCompleteErrorMessage()

defineExpose({ save, validate, validateWithAlert, getCompleteErrorMessage })
</script>

<style scoped></style>
