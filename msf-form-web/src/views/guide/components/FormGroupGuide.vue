<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfFormGroup"
      title="MsfFormGroup"
      description="레이블, 필수 표시 등을 표시하는 레이아웃으로 내부 슬롯에 고유 ID를 전달합니다."
      :config="componentConfig"
      :initialState="componentState"
      caseTitle="활용 예시"
      caseDescription="FormGroup 활용 예시입니다."
    >
      <template #default="{ props: unitProps }">
        <MsfFormGroup v-bind="unitProps">
          <MsfInput
            v-model="inputValue"
            :error="!!unitProps.error"
            placeholder="내용을 입력해주세요"
          />
        </MsfFormGroup>
      </template>

      <template #cases>
        <GuideSourceBox :source="selfSource" id="ex1">
          <!-- 이메일주소 -->
          <MsfFormGroup label="이메일 주소" required>
            <MsfInput v-model="inputValue" placeholder="example@email.com" />
          </MsfFormGroup>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex2">
          <!-- 이메일주소 -->
          <MsfFormGroup label="이메일 주소" required>
            <MsfInput v-model="inputValue" placeholder="example@email.com" />
          </MsfFormGroup>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './FormGroupGuide.vue?raw'

const inputValue = ref('')

const componentConfig = {
  label: '레이블',
  tag: ['label', 'div'],
  required: false,
  helpText: '도움말 문구',
  error: '에러 메시지',
  id: '아이디',
}

const componentState = {
  label: '레이블',
  tag: undefined,
  required: true,
  helpText: undefined,
  error: undefined,
  id: '',
  slotText: `<Input v-model="inputValue" placeholder="내용을 입력해주세요" />`,
}
</script>

<style lang="scss" scoped></style>
