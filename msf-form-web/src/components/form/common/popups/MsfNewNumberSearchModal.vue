<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신규번호 검색"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <p class="ut-text-desc">번호를 선택해 주세요.</p>
    <MsfRadioGroup
      name="number-select"
      v-model="numberSelect"
      :options="[
        { value: 'select1', label: '010-1234-5671' },
        { value: 'select2', label: '010-1234-5672' },
        { value: 'select3', label: '010-1234-5673' },
        { value: 'select4', label: '010-1234-5674' },
        { value: 'select5', label: '010-1234-5675' },
        { value: 'select6', label: '010-1234-5676' },
        { value: 'select7', label: '010-1234-5677' },
        { value: 'select8', label: '010-1234-5678' },
        { value: 'select9', label: '010-1234-5679' },
        { value: 'select10', label: '010-1234-5670' },
      ]"
      grid
    />
    <!-- 하단 고정 -->
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

// 신규번호 선택항목
const numberSelect = ref('select1')

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onConfirm = () => {
  emit('confirm', numberSelect.value)
  onClose()
}
</script>

<style lang="scss" scoped>
.ut-text-desc {
  padding-bottom: rem(12px);
  margin-bottom: rem(12px);
  border-bottom: var(--border-width-base) solid var(--color-gray-150);
}
</style>
