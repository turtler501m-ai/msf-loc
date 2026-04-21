<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="안내사항 타이틀 명"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <div>안내 상세 문구</div>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="primary">동의 후 닫기</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'

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

<style lang="scss" scoped></style>
