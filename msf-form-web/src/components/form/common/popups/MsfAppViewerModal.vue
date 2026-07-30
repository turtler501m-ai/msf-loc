<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신청서 열람"
    size="xlarge"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <div class="eformsign-flex-layout">
      <MsfTitleArea title="신청서" level="2" bold noline />
      <MsfEformPreview
        ref="eformImgRef"
        class="eform-frame"
        :document-id="props.documentId"
      />
    </div>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="primary" @click="onClose">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { MsfDialog } from '@/libs/ui/index.js'

const props = defineProps({
  modelValue: Boolean,
  documentId: { type: Array, default: () => [] },
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
