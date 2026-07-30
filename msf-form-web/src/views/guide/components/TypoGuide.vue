<template>
  <div class="guide-page">
    <GuideUnit
      name="TypoGuide"
      title="Typography"
      description="프로젝트 기본 타이포그래피 가이드"
      caseTitle="Font"
      caseDescription="기본 폰트는 Pretendard를 사용하며, 본문 기본값은 16px / letter-spacing -5% / font-weight regular / color #191A1B 기준입니다."
    >
      <template #cases>
        <GuideSourceBox
          title="1. font-family - Pretendard (프리텐다드)"
          :description="`기본 폰트 토큰은 Pretendard(src/assets/fonts/PretendardVariable.woff2)를 사용합니다.\nVariable font라서 regular, medium, semibold, bold 같은 굵기를 별도 폰트 파일 여러 개가 아니라 하나의 woff2 파일에서 처리합니다.`"
          :source="selfSource"
          id="ex-1"
          hideCode
        >
          <div class="demo-list">
            <div class="typo-sample">
              <p class="ut-text-heading1">
                <span class="sp-head">Heading1</span>32px / bold / #191A1B
              </p>
              <p class="ut-text-heading2">
                <span class="sp-head">Heading2</span>24px / bold / #191A1B
              </p>
              <p class="ut-text-heading3">
                <span class="sp-head">Heading3</span>22px / bold / #191A1B
              </p>
              <p class="ut-text-title1"><span class="sp-head">Title1</span>20px / bold / #191A1B</p>
              <p class="ut-text-title2"><span class="sp-head">Title2</span>18px / bold / #191A1B</p>
              <p class="ut-text-body1">
                <span class="sp-head">Body1</span>16px / regular / #191A1B
              </p>
              <p class="ut-text-body2">
                <span class="sp-head">Body2</span>15px / regular / #191A1B
              </p>
              <p class="ut-text-body3">
                <span class="sp-head">Body3</span>14px / regular / #191A1B
              </p>
              <p class="ut-text-caption">
                <span class="sp-head">Caption</span>12px / regular / #191A1B
              </p>
            </div>
          </div>
        </GuideSourceBox>
        <GuideSourceBox title="2. font-weight" :source="selfSource" id="ex-2" hideCode>
          <div class="demo-list">
            <div class="typo-sample">
              <p class="ut-text-title1 ut-weight-bold"><span class="sp-head">Bold</span>700</p>
              <p class="ut-text-title1 ut-weight-semibold">
                <span class="sp-head">Semibold</span>600
              </p>
              <p class="ut-text-title1 ut-weight-medium"><span class="sp-head">Medium</span>500</p>
              <p class="ut-text-title1 ut-weight-regular">
                <span class="sp-head">Regular</span>400
              </p>
            </div>
          </div>
        </GuideSourceBox>
        <GuideSourceBox
          title="3. line-height"
          description="별도 유틸 클래스가 없을 때는 CSS에서 line-height 토큰을 직접 사용합니다."
          :source="selfSource"
          id="ex-3"
          hideCode
        >
          <div class="demo-list">
            <div class="typo-sample line-height-sample">
              <div v-for="item in lineHeightTokens" :key="item.name" class="line-height-item">
                <span class="sp-head">{{ item.label }}</span>
                <p :style="{ lineHeight: `var(${item.name})` }">
                  <code>var({{ item.name }})</code>
                  <span class="line-height-value">{{ lineHeightValues[item.name] || '-' }}</span>
                  <span class="line-height-preview">행간 예시<br />줄 간격 비교</span>
                </p>
              </div>
            </div>
          </div>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue'
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './TypoGuide.vue?raw'

const lineHeightTokens = [
  { label: 'Base', name: '--line-height-base' },
  { label: 'Reading', name: '--line-height-reading' },
  { label: 'Heading', name: '--line-height-heading' },
  { label: 'Fit', name: '--line-height-fit' },
  { label: '16', name: '--line-height-16' },
  { label: '18', name: '--line-height-18' },
  { label: '20', name: '--line-height-20' },
  { label: '22', name: '--line-height-22' },
  { label: '24', name: '--line-height-24' },
  { label: '26', name: '--line-height-26' },
  { label: '28', name: '--line-height-28' },
  { label: '30', name: '--line-height-30' },
  { label: '34', name: '--line-height-34' },
  { label: '38', name: '--line-height-38' },
]

const lineHeightValues = ref({})

onMounted(async () => {
  await nextTick()

  const rootStyle = getComputedStyle(document.documentElement)
  lineHeightValues.value = lineHeightTokens.reduce((values, item) => {
    values[item.name] = rootStyle.getPropertyValue(item.name).trim() || '-'
    return values
  }, {})
})
</script>

<style lang="scss" scoped>
.guide-page {
  .demo-list {
    display: flex;
    flex-direction: column;
    gap: 10px;

    .typo-sample {
      flex-direction: row;
      display: flex;
      flex-direction: column;
      gap: 12px;
      .sp-head {
        display: inline-block;
        width: 200px;
      }
    }
    .line-height-sample {
      gap: 0;
      width: 100%;
      max-width: 100%;
      .line-height-item {
        display: grid;
        grid-template-columns: 140px minmax(0, 1fr);
        gap: 16px;
        align-items: center;
        padding: 12px 0;
        &:first-child {
          padding-top: 0;
        }
        &:last-child {
          padding-bottom: 0;
        }

        & + .line-height-item {
          border-top: 1px solid var(--color-gray-75);
        }

        > .sp-head {
          width: auto;
          color: var(--color-foreground);
          font-size: var(--font-size-13);
          font-weight: var(--font-weight-bold);
        }
      }

      p {
        display: grid;
        grid-template-columns: 220px 64px minmax(180px, 1fr);
        gap: 12px;
        align-items: center;
        min-width: 0;
        margin: 0;
        color: var(--color-foreground);
        font-size: var(--font-size-14);
      }

      code {
        color: var(--color-foreground);
        font-family: var(--font-family-mono);
      }

      .line-height-value {
        color: var(--color-gray-600);
        font-weight: var(--font-weight-semibold);
      }

      .line-height-preview {
        padding: 8px 10px;
        border-radius: 4px;
        background: var(--color-bg-gray);
        color: var(--color-gray-700);
      }
    }
  }
  .code-area {
    display: none;
  }
}
</style>
