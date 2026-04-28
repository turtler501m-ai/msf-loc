<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="데이터로밍요금알림(태블릿PC용)"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <MsfFormGroup label="<em>통보번호</em>" vertical>
      <MsfNumberInput
        v-model="formData.phoneNumber"
        placeholder="통보 휴대폰번호 입력 (필수)"
        class="ut-w100p"
      />
    </MsfFormGroup>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary">취소</MsfButton>
        <MsfButton variant="primary">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref, reactive } from 'vue'

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

// 퍼블샘플
const formData = reactive({
  phoneNumber: '',
})
</script>

<style lang="scss" scoped></style>
