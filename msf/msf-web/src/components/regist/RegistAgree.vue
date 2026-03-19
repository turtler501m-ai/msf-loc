<template>
  <div class="page-step-panel">
    <h3>신규/변경 - 동의 영역</h3>
    <div>
      <div>
        <select v-model="values.val">
          <option value="">동의 validation</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'

// 상위 컴포넌트에서 참조할 해당 컴포넌트 이벤트 선언
const emit = defineEmits(['update:complete'])

const values = ref({ val: '' })

// 필수 입력 항목에 데이터 입력 완료 여부 체크 로직
const isComplete = computed(() => {
  return !!values.value.val
})

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(isComplete, (newVal) => {
  emit('update:complete', newVal)
})

// 컴포넌트가 처음 마운트되었을 때 기본 상태(false)를 상위 컴포넌트에게 알려준다.
onMounted(() => {
  emit('update:complete', isComplete.value)
})

// 상위 컴포넌트에서 실행할 함수
const validate = async () => {
  // 컴포넌트 Validation 실행 후 결과
  if (isComplete.value && values.value.val === 'true') {
    return { valid: true, message: '' }
  }
  return { valid: false, message: '동의 입력 오류 내용.' }
}

// 상위 컴포넌트에서 실행할 함수 (최종 저장 실행)
const save = async () => {
  alert('신청서 등록이 완료되었습니다.')
}

// 상위 컴포넌트가 호출할 수 있도록 함수 노출
defineExpose({ validate, save })
</script>

<style scoped></style>
