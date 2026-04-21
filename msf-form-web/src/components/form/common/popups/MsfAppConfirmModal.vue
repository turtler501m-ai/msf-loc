<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신청서 확인"
    @open="emit('open')"
    @close="onClose"
    keepAlive
  >
    <!-- 팝업 내용 -->
    <MsfCollapse defaultOpen>
      <template #header>안내 녹취</template>
      <MsfButtonGroup>
        <MsfButton variant="toggle">녹취</MsfButton>
        <MsfButton variant="toggle">녹취중</MsfButton>
        <MsfButton variant="toggle" active>녹취완료</MsfButton>
        <MsfButton variant="subtle">재생</MsfButton>
      </MsfButtonGroup>
      <MsfTextarea placeholder="안내 녹취 스크립트" class="ut-mt-8" />
    </MsfCollapse>
    <MsfTitleArea title="신청서" level="2" noline />
    <div class="img-area" style="height: 468px">신청서 이미지</div>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onEdit">수정</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm', 'edit'])

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

const onEdit = () => {
  emit('edit')
  onClose()
}
</script>

<style lang="scss" scoped>
.img-area {
  background-color: var(--color-gray-75);
}
</style>
