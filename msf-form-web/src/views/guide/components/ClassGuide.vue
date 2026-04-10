<template>
  <div class="guide-page">
    <GuideUnit name="ClassGuide" title="유틸 클래스" caseTitle="앞에 (ut-) prefix 클래스명">
      <template #cases>
        <GuideSourceBox
          title="Typography & Color"
          description="색상 변수 및 폰트 두께, 정렬"
          :source="selfSource"
          id="ex-typo"
        >
          <div class="demo-list">
            <div class="ut-d-flex ut-gap-10 ut-flex-wrap">
              <span class="ut-color-primary ut-weight-bold">Primary</span>
              <span class="ut-color-point ut-weight-bold">Point</span>
              <span class="ut-color-alert ut-weight-bold">Alert</span>
              <span class="ut-color-gray">Gray</span>
              <span class="ut-color-gray1">Gray1</span>
              <span class="ut-color-gray2">Gray2</span>
              <span class="ut-color-gray3">Gray3</span>
              <span class="ut-color-gray4">Gray4</span>
              <span class="ut-color-black" style="background: #eee">Black</span>
              <span class="ut-color-white" style="background: #000">White</span>
            </div>
            <div>
              <p class="ut-weight-regular">Regular Weight (400)</p>
              <p class="ut-weight-medium">Medium Weight (500)</p>
              <p class="ut-weight-bold">Bold Weight (700)</p>
              <p class="ut-text-center ut-p-10 ut-mt-10" style="background: #f8f9fa">
                Text Center Align
              </p>
            </div>
            <div class="ut-mt-10">
              <p class="link-txt">링크 텍스트 꾸밈 필요하다면</p>
              <p class="ut-ellipsis ut-w-200" style="border-bottom: 1px solid #ddd">
                이 텍스트는 너비를 넘어가면 말줄임표(...)가 생깁니다.
              </p>
            </div>
          </div>
        </GuideSourceBox>

        <GuideSourceBox
          title="Width & Height"
          description="1px 단위 고정 수치"
          :source="selfSource"
          id="ex-size"
        >
          <div class="demo-list ut-flex-row">
            <div class="ut-w-50 ut-h-50 demo-box">50</div>
            <div class="ut-w-80 ut-h-40 demo-box">80x40</div>
            <div class="ut-w-100 ut-h-30 demo-box">100x30</div>
          </div>
        </GuideSourceBox>

        <GuideSourceBox
          title="Width (넓이) - px"
          description="ut-w-1부터 ut-w-800까지 1px 단위로 지정"
          :source="selfSource"
          id="ex-width"
        >
          <div class="demo-list">
            <div class="ut-w-50 demo-box">50</div>
            <div class="ut-w-100 demo-box">100</div>
            <div class="ut-w-150 demo-box">150</div>
            <div class="ut-w-200 demo-box">200</div>
          </div>
        </GuideSourceBox>

        <GuideSourceBox
          title="Width (넓이) - 퍼센트"
          description="ut-w1p부터 ut-w100p까지 % 단위로 지정"
          :source="selfSource"
          id="ex-width-percent"
        >
          <div class="demo-list">
            <div class="ut-w20p demo-box">20</div>
            <div class="ut-w40p demo-box">40</div>
            <div class="ut-w60p demo-box">60</div>
            <div class="ut-w100p demo-box">100</div>
          </div>
        </GuideSourceBox>

        <GuideSourceBox
          title="Height (높이)"
          description="ut-h-1부터 ut-h-800까지 1px 단위로 지정"
          :source="selfSource"
          id="ex-height"
        >
          <div class="demo-list ut-flex-row">
            <div class="ut-h-20 ut-w-60 demo-box">20</div>
            <div class="ut-h-40 ut-w-60 demo-box">40</div>
            <div class="ut-h-60 ut-w-60 demo-box">60</div>
            <div class="ut-h-80 ut-w-60 demo-box">80</div>
          </div>
        </GuideSourceBox>

        <GuideSourceBox
          title="Flex & Alignment"
          description="요소 정렬 및 Flex 레이아웃"
          :source="selfSource"
          id="ex-align"
        >
          <div class="demo-list">
            <div class="ut-d-flex ut-jc-between ut-ai-center ut-p-10" style="background: #f8f9fa">
              <span class="ut-color-gray2">JC-BETWEEN</span>
              <div class="ut-d-flex ut-gap-5">
                <div class="ut-w-30 ut-h-30 demo-box">1</div>
                <div class="ut-w-30 ut-h-30 demo-box">2</div>
              </div>
            </div>
            <div class="ut-d-flex ut-gap-10">
              <div class="ut-w-grow demo-box-dark ut-h-40">W-GROW (100%)</div>
              <div class="ut-w-auto demo-box-dark ut-h-40 ut-p-10">W-AUTO</div>
            </div>
          </div>
        </GuideSourceBox>

        <GuideSourceBox
          title="Margin & Padding"
          description="외부/내부 여백 클래스"
          :source="selfSource"
          id="ex-spacing"
        >
          <div class="ut-d-flex ut-gap-20">
            <div class="margin-container">
              <div class="ut-mt-20 ut-ml-20 demo-outline">MT-20 / ML-20</div>
            </div>
            <div class="demo-box-dark ut-p-20">P-20 Padding</div>
          </div>
        </GuideSourceBox>

        <GuideSourceBox
          title="Margin (외부 여백)"
          description="0px~100px 범위를 지원하며 mt(상), mb(하), ml(좌), mr(우) 조합이 가능"
          :source="selfSource"
          id="ex-margin"
        >
          <div class="margin-container">
            <div class="ut-mt-10 ut-ml-10 demo-outline">MT-10 / ML-10</div>
            <div class="ut-mt-20 ut-ml-30 demo-outline">MT-20 / ML-30</div>
            <div class="ut-mt-30 ut-ml-50 demo-outline">MT-30 / ML-50</div>
          </div>
        </GuideSourceBox>

        <GuideSourceBox
          title="Padding (내부 여백)"
          description="0px~100px 범위를 지원"
          :source="selfSource"
          id="ex-padding"
        >
          <div class="demo-list ut-flex-row">
            <div class="ut-p-10 demo-box-dark">P-10</div>
            <div class="ut-p-20 demo-box-dark">P-20</div>
            <div class="ut-p-30 demo-box-dark">P-30</div>
          </div>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './ClassGuide.vue?raw'
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
