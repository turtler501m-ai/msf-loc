<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfFormGroup"
      title="MsfFormGroup"
      description="레이블, 필수 표시 등을 표시하는 레이아웃으로 내부 슬롯에 고유 ID를 전달합니다."
      :config="componentConfig"
      :initialState="componentState"
      caseTitle="활용 예시"
      caseDescription='기본 tag(label)는 첫 번째 입력 컨트롤 id와 연결됩니다. 특정 입력 컨트롤과 연결하지 않고 항목 제목으로만 표시할 때는 tag="div"를 사용합니다.'
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
          <!-- 단일 입력 컨트롤 -->
          <MsfFormGroup label="이메일 주소" required>
            <MsfInput v-model="inputValue" placeholder="example@email.com" />
          </MsfFormGroup>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex2">
          <!-- 특정 입력 컨트롤과 label을 연결하지 않는 항목 -->
          <MsfFormGroup label="제목" tag="div" required>
            <MsfButton variant="subtle">버튼</MsfButton>
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
  tag: {
    description:
      'label: 첫 번째 입력 컨트롤 id와 연결 / div: 특정 입력 컨트롤과 연결하지 않고 항목 제목으로만 표시할 때 사용',
    options: ['label', 'div'],
    default: 'label',
  },
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
