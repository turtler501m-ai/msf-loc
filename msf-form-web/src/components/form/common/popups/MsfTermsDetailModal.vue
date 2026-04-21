<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    :title="title"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <div v-html="content"></div>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onConfirm">동의 후 닫기</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
const props = defineProps({
  modelValue: Boolean,
  title: { type: String, default: '안내사항' },
  content: { type: String, default: '안내 상세 문구' },
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
