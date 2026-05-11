<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="부가서비스명"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <MsfTitleArea title="이용기간 설정(한국시간 기준)" level="2" color="black" noline bold />
    <MsfStack type="field" vertical>
      <MsfDateInput v-model="startDate" placeholder="시작일" class="ut-w100p" />
    </MsfStack>
    <MsfBox>
      <MsfTextList
        :items="['서비스 신청/변경이 완료되면 문자 메시지가 발송되오니 확인하여 주세요.']"
        margin="0"
        level="2"
      />
    </MsfBox>
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const startDate = ref()

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onConfirm = () => {
  emit('confirm', { startDate: startDate.value })
  onClose()
}
</script>

<style lang="scss" scoped></style>
