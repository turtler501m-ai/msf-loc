<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfButton"
      title="MsfButton"
      description="기본 버튼 컴포넌트. 테마, 크기 등을 설정할 수 있습니다."
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfButton v-bind="props">
          {{ props.slotText }}
        </MsfButton>
      </template>

      <!-- <template #story="{ args }">
        <MsfButton v-bind="args">
          {{ args.slotText }}
        </MsfButton>
      </template> -->
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit } from '@/views/guide/components'
import { ICON_NAMES } from '@/libs/ui/base/MsfIcon.vue'
import { BTN_VARIANTS } from '@/libs/ui/base/MsfButton.vue'

/**
 * 1. 컨트롤러 설정 (config)
 */
const componentConfig = {
  variant: {
    description: '버튼 스타일',
    options: [...BTN_VARIANTS],
    default: 'tertiary',
  },
  // size: {
  //   description: '버튼 크기',
  //   options: [...BTN_SIZE],
  //   default: 'medium',
  // },
  prefixIcon: {
    description: '왼쪽 아이콘 이름',
    options: [...ICON_NAMES], // 아이콘 목록 연결
    default: undefined,
  },
  suffixIcon: {
    description: '오른쪽 아이콘 이름',
    options: [...ICON_NAMES],
    default: undefined,
  },
  iconOnly: {
    description: '아이콘만 사용 (텍스트 숨김)',
    options: [...ICON_NAMES],
    default: undefined,
  },
  slotText: {
    description: '버튼 텍스트 내용',
    default: '확인',
  },
  disabled: {
    description: '비활성화 여부',
    default: false,
  },
  block: {
    description: '가로 너비 100% 채움',
    default: false,
  },
}

/**
 * 2. 가이드 초기 상태 (initialState)
 */
const componentState = {
  variant: undefined,
  // size: undefined,
  prefixIcon: undefined,
  suffixIcon: undefined,
  iconOnly: undefined,
  slotText: '확인',
  disabled: false,
  block: false,
}

/**
 * 3. 스토리북 스타일 케이스 데이터 args로 표현할경우 (stories)
 */
// const componentStories = [
//   {
//     name: '타이틀',
//     description: '설명',
//     args: { variant: undefined, size: undefined, slotText: ' 버튼' },
//   },
// ]
</script>
