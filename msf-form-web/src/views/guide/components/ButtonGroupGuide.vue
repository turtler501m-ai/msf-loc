<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfButtonGroup"
      title="MsfButtonGroup"
      description="버튼영역 레이아웃 컴퍼넌트 (기본 우측정렬)"
    >
      <template #cases>
        <GuideSourceBox :source="selfSource" id="ex1">
          <!-- 미지정시 기본 우측정렬 -->
          <MsfButtonGroup>
            <MsfButton>버튼1</MsfButton>
            <MsfButton>버튼2</MsfButton>
            <MsfButton>버튼3</MsfButton>
          </MsfButtonGroup>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex2">
          <!-- 왼쪽정렬 -->
          <MsfButtonGroup align="left">
            <MsfButton>버튼1</MsfButton>
            <MsfButton>버튼2</MsfButton>
            <MsfButton>버튼3</MsfButton>
          </MsfButtonGroup>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex3">
          <!-- 가운데정렬 -->
          <MsfButtonGroup align="center">
            <MsfButton>버튼1</MsfButton>
            <MsfButton>버튼2</MsfButton>
            <MsfButton>버튼3</MsfButton>
          </MsfButtonGroup>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './ButtonGroupGuide.vue?raw'
</script>
