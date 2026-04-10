<template>
  <!-- DOM에 팝업 상태가 유지되도록 keepAlive 설정 추가 -->
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
        <MsfButton variant="secondary">수정</MsfButton>
        <MsfButton variant="primary" @click="onClose">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}
</script>

<style lang="scss" scoped>
.img-area {
  background-color: var(--color-gray-75); //임시(불필요시삭제)
}
</style>
