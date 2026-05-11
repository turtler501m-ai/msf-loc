<template>
  <div class="page-step-panel">
    <!-- 신청서 확인 -->
    <MsfAppConfirm @confirm="onConfirmApp" @edit="onEditApp" />
    <!-- // 신청서 확인 -->

    <!-- (화면테스트용 테스트영역) 추후 지워질수도 있는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주세요 부분 - 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">동의 대기</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 테스트영역) 추후 지워질수도 있는것 -->
  </div>
</template>

<script setup>
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { storeToRefs } from 'pinia'
import { ref, watch } from 'vue'

const emit = defineEmits(['complete'])

const store = useMsfFormSvcChgStore()
const { formData } = storeToRefs(store)

const isComplete = ref(formData.value.additionConfirmCompleted ? 'true' : '')

const setComplete = (value) => {
  const completed = value === true
  isComplete.value = completed ? 'true' : ''
  formData.value.additionConfirmCompleted = completed
  emit('complete', completed)
}

watch(
  () => isComplete.value,
  (newVal) => {
    const completed = newVal === true || newVal === 'true'
    formData.value.additionConfirmCompleted = completed
    emit('complete', completed)
  },
  { immediate: true },
)

const onConfirmApp = () => {
  console.log('[서비스변경][신청서확인] 완료')
  setComplete(true)
}

const onEditApp = () => {
  console.log('[서비스변경][신청서확인] 수정 요청')
  setComplete(false)
}

const save = async () => {
  console.log('[서비스변경][신청서확인] 작성완료 처리 시작', {
    additionConfirmCompleted: formData.value.additionConfirmCompleted,
    additionList: formData.value.additionList,
    additionCancelList: formData.value.additionCancelList,
  })

  if (formData.value.additionConfirmCompleted !== true) {
    console.warn('[서비스변경][신청서확인] 작성완료 처리 중단', {
      reason: 'addition confirm incomplete',
    })
    return false
  }

  const result = await store.apiCompleteAdditionApplication()
  console.log('[서비스변경][신청서확인] 작성완료 처리 결과', { result })
  return result
}

const getCompleteErrorMessage = () => store.getCompleteErrorMessage()

defineExpose({ save, getCompleteErrorMessage })
</script>

<style scoped></style>
