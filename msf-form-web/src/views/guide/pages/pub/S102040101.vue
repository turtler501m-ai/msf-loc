<template>
  <MsfTitleBar title="서비스변경 신청서 > 동의 퍼블확인용" />
  <div class="page-step-panel">
    <!-- 신청서 확인 -->
    <MsfAppConfirm @confirm="console.log('신청서 확인 버튼 클릭!!')" />
    <!-- // 신청서 확인 -->
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const isComplete = ref('')

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => isComplete.value,
  (newVal) => {
    isComplete.value = newVal
    emit('complete', newVal ? true : false)
  },
)

const save = async () => {
  //  최종 데이터 저장
  return isComplete.value === 'true'
}

defineExpose({ save })
</script>

<style scoped></style>
