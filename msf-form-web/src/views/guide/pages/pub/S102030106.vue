<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="부가서비스명"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <MsfTitleArea title="이용기간 설정(한국시간 기준)" level="2" color="black" noline bold />
    <MsfStack type="field" vertical>
      <MsfDateInput v-model="datePickerValue" placeholder="시작일" class="ut-w100p" />
    </MsfStack>
    <MsfBox>
      <MsfTextList
        :items="['서비스 신청/변경이 완료되면 문자 메시지가 발송되오니 확인하여 주세요.']"
        margin="0"
        level="2"
      />
    </MsfBox>
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

// vue-datepicker
const datePickerValue = ref()
</script>

<style lang="scss" scoped></style>
