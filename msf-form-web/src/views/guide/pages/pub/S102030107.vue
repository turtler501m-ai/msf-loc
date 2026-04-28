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
      <MsfDateInput v-model="datePickerValue" placeholder="시작일" class="ut-w100p" />
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
          <MsfInput v-model="formData.phoneNumber" class="ut-w100p" disabled />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup
        label="<em class='ut-w100p ut-nowrap'>서브번호 (데이터를 함께 이용할 추가 휴대폰 번호 등록)</em>"
        tag="div"
        vertical
      >
        <MsfStack type="field" class="ut-w100p">
          <MsfNumberInput
            v-model="formData.subPhoneNumber1"
            placeholder="서브 휴대폰번호 입력 (선택)"
            class="ut-w100p"
          />
          <MsfNumberInput
            v-model="formData.subPhoneNumber2"
            placeholder="서브 휴대폰번호 입력 (선택)"
            class="ut-w100p"
          />
          <MsfNumberInput
            v-model="formData.subPhoneNumber3"
            placeholder="서브 휴대폰번호 입력 (선택)"
            class="ut-w100p"
          />
          <MsfNumberInput
            v-model="formData.subPhoneNumber4"
            placeholder="서브 휴대폰번호 입력 (선택)"
            class="ut-w100p"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <MsfBox>
      <MsfTextList
        :items="[
          '추가 휴대폰 번호는 kt M모바일 사용 휴대폰 번호에 한해 최대 4개까지 등록 가능합니다.',
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
const datePickerValue = ref()
// 퍼블샘플
const formData = reactive({
  timeSelect: '',
  phoneNumber: '010-1234-5678',
  subPhoneNumber1: '',
  subPhoneNumber2: '',
  subPhoneNumber3: '',
  subPhoneNumber4: '',
})
</script>

<style lang="scss" scoped></style>
