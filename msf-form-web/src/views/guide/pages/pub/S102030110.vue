<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="하루종일 로밍 베이직 투게더(서브)"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <MsfTitleArea title="이용기간 설정(한국시간 기준)" level="2" color="black" noline bold>
      <template #content>
        <MsfTextList
          :items="['신청한 시간부터 24시간 동안 적용']"
          type="dash"
          margin="0"
          color="mint"
        />
      </template>
    </MsfTitleArea>
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
    <MsfStack vertical type="formgroups" class="ut-mt-base">
      <MsfFormGroup label="<em>대표번호</em>" vertical>
        <MsfStack type="field" class="ut-w100p">
          <MsfNumberInput
            v-model="formData.phoneNumber"
            class="ut-w100p"
            placeholder="대표 휴대폰번호 입력 (필수)"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
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
// 퍼블샘플
const formData = reactive({
  timeSelect: '',
  phoneNumber: '',
})
</script>

<style lang="scss" scoped></style>
