<template>
  <div class="page-step-panel">
    <h3>신규/변경 - 고객 영역</h3>
    <div>
      <div>
        <select v-model="values.val">
          <option value="">고객 validation</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'

const emit = defineEmits(['update:complete'])

const values = ref({ val: '' })

// 상위 컴포넌트에서 참조할 해당 컴포넌트 이벤트 선언
const isComplete = computed(() => {
  return !!values.value.val
})

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(isComplete, (newVal) => {
  console.log('isComplete changed:', newVal)
  emit('update:complete', newVal)
})

// 컴포넌트가 처음 마운트되었을 때 기본 상태(false)를 상위 컴포넌트에게 알려준다.
onMounted(() => {
  emit('update:complete', isComplete.value)
})

// 상위 컴포넌트에서 실행할 함수
const validate = async () => {
  /*
    컴포넌트의 자체 validation 체크를 실행한 결과를
    return object에 valid값을 설정한 후, 리턴한다.
    *** validation 실패일 경우,
        return object의 valid = false, message = [실패 메시지] 를 설정 후, 리턴한다.
  */
  // 컴포넌트 Validation 실행 후 결과
  if (isComplete.value && values.value.val === 'true') {
    return { valid: true, message: '' }
  }
  return { valid: false, message: '고객 입력 오류 내용.' }
}

// 상위 컴포넌트가 호출할 수 있도록 함수 노출
defineExpose({ validate })
</script>

<style scoped></style>
