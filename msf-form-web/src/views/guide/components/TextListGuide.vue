<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfTextList"
      title="MsfTextList"
      description="콘텐츠 하단 안내문구, 유의사항 처럼 여러 문장을 목록 형태로 정리할 때 사용하는 텍스트 리스트 컴포넌트입니다."
      :config="componentConfig"
      :initialState="componentState"
      caseTitle="활용 예시"
    >
      <template #default="{ props }">
        <div style="border: 1px dashed #dcdcdc; padding: 8px">콘텐츠</div>
        <MsfTextList v-bind="props" />
      </template>

      <template #cases>
        <GuideSourceBox
          :source="selfSource"
          id="ex1"
          title="1. items + slot 사용"
          description="배열 목록에 템플릿을 넣을 수 있다."
        >
          <MsfTextList :items="['텍스트 목록1', '텍스트 목록2']" type="star">
            <template #default="{ item }">
              <span style="color: blue">[중요]</span> {{ item }}
            </template>
          </MsfTextList>
        </GuideSourceBox>
        <GuideSourceBox
          :source="selfSource"
          id="ex2"
          title="2. li 직접 작성"
          description="필요한 컨텐츠 구성 시 li로 직접 작성한다."
        >
          <MsfTextList type="dash">
            <li>직접 작성한 항목 1</li>
            <li>직접 작성한 항목 2</li>
          </MsfTextList>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './TextListGuide.vue?raw'

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  items: {
    description: '목록에 표시할 텍스트 배열(HTML 문자열도 사용가능)',
    default: '[]',
  },
  type: {
    description: '목록 앞에 표시할 기호 타입',
    options: ['dot', 'dash', 'star', 'none', 'number'],
    default: 'star',
  },
  level: {
    description: '텍스트 크기 레벨 (14px(1), 16px(2)',
    options: ['1', '2'],
    default: '2',
  },
  margin: {
    description: '상단 여백 (0(0), 1(16px), 2(24px), 3(32px))',
    options: ['0', '1', '2', '3'],
    default: '1',
  },
  bottomDivider: {
    description: '하단 구분라인 여부',
    default: false,
  },
  color: {
    description: '색상 지정',
    options: ['default', 'mint'],
    default: 'default',
  },
}

// 2. 초기값 정의
const componentState = {
  items: [
    '목록 항목은 안내 문구나 유의사항을 표시할 때 사용합니다.',
    'type 속성으로 dot, dash, star, number 형태를 선택할 수 있습니다.',
    'items 배열을 사용하거나 slot으로 li 항목을 직접 작성할 수 있습니다.',
  ],
  type: 'star',
  level: '2',
  margin: '1',
  bottomDivider: false,
  color: 'default',
}
</script>
