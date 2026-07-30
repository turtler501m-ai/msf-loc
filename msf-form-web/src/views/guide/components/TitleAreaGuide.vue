<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfTitleArea"
      title="MsfTitleArea"
      description="타이틀 영역 컴퍼넌트입니다."
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfTitleArea v-bind="props" />
        <br />
        <MsfTitleArea v-bind="props">
          <template #actions>&lt;template #actions&gt;타이틀 우측 내용&lt;/template&gt;</template>
          <template #content> &lt;template #content&gt;타이틀 하단 내용&lt;/template&gt; </template>
        </MsfTitleArea>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit } from '@/views/guide/components'

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  level: {
    description: '타이틀 레벨 (기본: 1(20px) / 2(16px) / 3(18px))',
    options: [1, 2, 3],
    default: '1',
  },
  title: {
    description: '타이틀 텍스트',
    default: undefined,
  },
  desc: {
    description: '타이틀 보조 텍스트',
    default: undefined,
  },
  noline: {
    description: '타이틀 하단 라인 숨김 여부',
    default: false,
  },
  bold: {
    description: '타이틀 볼드 처리',
    default: false,
  },
  color: {
    description: '타이틀 컬러 (색상 유틸클래스로 연결) ut-color-point 사용할 경우 point로 지정)',
    default: undefined,
  },
  margin: {
    description: '상단 여백 (0, 1(40px), 2(32px), 3(24px), 4(16px/12px))',
    options: [0, 1, 2, 3, 4],
    default: undefined,
  },
}

// 2. 초기값 정의
const componentState = {
  level: '1',
  title: '타이틀 텍스트',
  desc: undefined,
  noline: false,
  bold: false,
  color: undefined,
  margin: undefined,
}
</script>
