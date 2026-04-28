<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="My time plan_MVNO 전용"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <MsfTitleArea title="사용 기간 설정" level="2" color="black" noline bold />
    <MsfStack type="field" vertical>
      <MsfDateRange
        v-model:from="rangeDatePickerValue.start"
        v-model:to="rangeDatePickerValue.end"
        class="ut-w100p"
      />
      <MsfSelect
        title="시작 시간 선택"
        v-model="formData.timeSelect"
        :options="[
          { label: '시작 시간 선택1', value: 'time1' },
          { label: '시작 시간 선택2', value: 'time2' },
        ]"
        placeholder="시작 시간 선택"
        class="ut-w100p"
      />
    </MsfStack>
    <MsfBox>
      <MsfTextList
        :items="[
          '입대일자, 전역일자, 시작시간 순으로 선택해 주세요.',
          '입대일자와 전역일자는 21개월만 가능합니다.',
        ]"
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

// vue-datepicker
const rangeDatePickerValue = ref({ start: '', end: '' })
// 퍼블 샘플
const formData = reactive({
  timeSelect: '',
})
</script>

<style lang="scss" scoped></style>
