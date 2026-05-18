<template>
  <div class="guide-page">
    <GuideUnit
      name="ClassGuide"
      title="데이터 없음 영역"
      description="데이터 없음 영역에 설계에 맞게 텍스트 변경하여 사용합니다."
      caseTitle="nodata-wrap 클래스로 감싼 형태"
    >
      <template #cases>
        <GuideSourceBox
          title="1. 기본 (아이콘+문구) - 가이드높이 180"
          :source="selfSource"
          id="ex-typo"
        >
          <div class="nodata-wrap">데이터가 없습니다.</div>
        </GuideSourceBox>
        <GuideSourceBox
          title="2. 아이콘 없이 문구만 사용 (noIcon 클래스 추가)"
          :source="selfSource"
          id="ex-typo2"
        >
          <div class="nodata-wrap noIcon">데이터가 없습니다.</div>
        </GuideSourceBox>
        <GuideSourceBox
          title="3. 기본 (아이콘+문구) 높이 설정 해제 (autoHeight 클래스 추가)"
          :source="selfSource"
          id="ex-typo3"
        >
          <div class="nodata-wrap autoHeight">데이터가 없습니다.</div>
        </GuideSourceBox>
        <GuideSourceBox
          title="4. 기본 (아이콘+문구) 높이 직접지정 (유틸클래스 또는 인라인스타일 사용)"
          :source="selfSource"
          id="ex-typo4"
        >
          <div class="nodata-wrap ut-h-500">데이터가 없습니다.</div>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './NodataGuide.vue?raw'
</script>

<style lang="scss" scoped>
.guide-page {
  background-color: #fff;
  padding: 20px;

  .demo-list {
    display: flex;
    flex-direction: column;
    gap: 10px;

    &.ut-flex-row {
      flex-direction: row;
      align-items: flex-end;
    }
  }

  .demo-box {
    background: #4a90e2;
    color: #fff;
    font-size: 11px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 4px;
  }

  .demo-box-dark {
    background: #333;
    color: #fff;
    font-size: 11px;
    border-radius: 4px;
  }

  .margin-container {
    background: #f8f9fa;
    border: 1px dashed #ccc;
    display: inline-block;
    padding: 1px;
  }

  .demo-outline {
    border: 1px solid #4a90e2;
    background: #fff;
    color: #4a90e2;
    font-size: 11px;
    padding: 5px;
  }
}
</style>
