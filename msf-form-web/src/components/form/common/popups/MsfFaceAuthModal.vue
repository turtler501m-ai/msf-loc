<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="안면 인증"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <p class="ut-text-center ut-py-20">안면 인증을 진행합니다.</p>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onConfirm = () => {
  emit('confirm')
  onClose()
}
</script>
