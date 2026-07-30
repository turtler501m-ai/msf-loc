<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신청서 열람"
    @open="emit('open')"
    @close="onClose"
    size="xlarge"
  >
    <!-- 팝업 내용 -->
    <div class="eformsign-flex-layout">
      <MsfTitleArea title="신청서" level="2" bold noline />
      <!-- 신청서 이미지 퍼블 샘플 -->
      <div class="eformsign-frame">
        <div class="iframe-sample" style="width: 100%; height: 100%; border: 0; overflow-y: auto">
          <img
            src="@/assets/images/dummy/@sample_form.png"
            alt="신청서 이미지 샘플"
            style="width: 100%"
          />
        </div>
      </div>
      <!-- // 신청서 이미지 퍼블 샘플 -->
      <!-- 개발에서 사용하는 컴퍼넌트-->
      <!-- <MsfEformPreview ref="eformImgRef" class="eform-frame" :document-id="props.documentId" /> -->
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
  height: calc(100vh - 320px);
  overflow-y: auto;
  background-color: var(--color-gray-75); //임시(불필요시삭제)
}
</style>
