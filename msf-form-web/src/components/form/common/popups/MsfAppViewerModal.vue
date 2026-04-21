<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신청서 열람"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfTitleArea title="신청서" level="2" noline />
    <div class="img-area" style="height: 436px">신청서 이미지</div>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
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
  background-color: var(--color-gray-75);
}
</style>
