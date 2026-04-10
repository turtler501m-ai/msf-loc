<!-- Dialog 파일로 생성할 경우 샘플입니다 -->
<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="Dialog 파일 타이틀"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 상단 고정 -->
    <template #navBar>
      <MsfButtonGroup>
        <MsfButton>버튼1</MsfButton>
        <MsfButton variant="primary">버튼2</MsfButton>
      </MsfButtonGroup>
    </template>
    <!-- 팝업 내용 -->
    <MsfButton @click="onConfirm">이것은 확인</MsfButton>
    <p v-for="n in 100" :key="n">자유로운 내용</p>
    <!-- 하단 고정 -->
    <template #footer>하단 고정영역</template>
  </MsfDialog>
</template>

<script setup>
const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'confirm', 'open', 'close'])

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 확인 버튼 클릭 시 실행 샘플
const onConfirm = () => {
  emit('confirm')
  onClose()
}
</script>

<style scoped>
/* 스타일이 있다면... */
</style>
