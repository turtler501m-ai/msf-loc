<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="로밍 해외도착알리미"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <MsfFormGroup label="<em>알림번호</em>" vertical tag="div">
      <MsfStack type="field" class="ut-w100p">
        <MsfNumberInput
          v-model="formData.numberValue1"
          placeholder="알림 휴대폰번호 입력 (필수)"
          class="ut-w100p"
        />
        <MsfNumberInput
          v-model="formData.numberValue2"
          placeholder="알림 휴대폰번호 입력 (선택)"
          class="ut-w100p"
        />
        <MsfNumberInput
          v-model="formData.numberValue3"
          placeholder="알림 휴대폰번호 입력 (선택)"
          class="ut-w100p"
        />
        <MsfNumberInput
          v-model="formData.numberValue4"
          placeholder="알림 휴대폰번호 입력 (선택)"
          class="ut-w100p"
        />
      </MsfStack>
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
import { reactive } from 'vue'

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
  numberValue1: '',
  numberValue2: '',
  numberValue3: '',
  numberValue4: '',
})
</script>

<style lang="scss" scoped></style>
