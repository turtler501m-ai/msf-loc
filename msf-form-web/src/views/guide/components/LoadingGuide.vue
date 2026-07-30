<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfLoadingComp"
      title="MsfLoadingComp"
      description="로딩 디자인"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfButtonGroup align="left">
          <MsfButton @click="handleLoading(props, { spinner: false, inline: false })"
            >기본 로딩</MsfButton
          >
          <MsfButton @click="handleLoading(props, { spinner: true, inline: false })"
            >스피너 로딩</MsfButton
          >
          <MsfButton @click="handleLoading(props, { spinner: false, inline: true })"
            >컨텐츠 로딩</MsfButton
          >
        </MsfButtonGroup>
        <MsfLoadingComp v-bind="props" />
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit } from '@/views/guide/components'

// 로딩 props 객체 조작
const handleLoading = (props, config) => {
  // 1. 선택한 설정값을 props에 반영
  props.isOpen = true
  props.spinner = config.spinner
  props.inline = config.inline

  // 2. 3초 뒤에 닫기
  setTimeout(() => {
    props.isOpen = false
  }, 3000)
}

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  isOpen: {
    description: '표시 여부: v-if 사용으로 인한 초기 DOM 렌더링을 위해 기본값 true 설정',
    default: true,
  },
  spinner: {
    description: '스피너로 보여줄것 인지 여부',
    default: false,
  },
  inline: {
    description: '전체를 덮지않는 컨텐츠용 로딩',
    default: false,
  },
  height: {
    description: 'inline시 높이지정 (예: height="500px" 또는 height="500")',
    default: 'auto',
  },
}

// 2. 초기값 정의
const componentState = {
  isOpen: false,
  inline: false,
  spinner: false,
  height: undefined,
}
</script>
