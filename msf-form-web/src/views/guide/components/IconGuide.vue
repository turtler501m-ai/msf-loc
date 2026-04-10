<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfIcon"
      title="MsfIcon"
      description="아이콘 컴퍼넌트. name에 아이콘 이름을 넣으시면됩니다."
      :config="componentConfig"
      :initialState="componentState"
      :stories="iconSectionStory"
      caseTitle="Icon List"
      caseDescription="아이콘 name='' 으로 사용 가능한 전체 아이콘 목록"
    >
      <template #default="{ props }">
        <MsfIcon v-bind="props" />
      </template>

      <template #cases>
        <div class="icon-grid-layout">
          <div v-for="iconName in ICON_NAMES" :key="iconName" class="icon-card-static">
            <div class="icon-visual">
              <MsfIcon :name="iconName" size="large" />
            </div>
            <div class="icon-info">
              <span class="icon-name">{{ iconName }}</span>
              <!-- <code class="icon-code">name="{{ iconName }}"</code> -->
            </div>
          </div>
        </div>
        <div class="icon-add-info">
          <p class="guide-title">i 태그로 사용가능</p>
          <GuideSourceBox :source="selfSource" id="ex1">
            <!-- 컴퍼넌트로 쓰셔도 되고 -->
            <MsfIcon name="notice" />
            <MsfIcon name="cart" />

            <!-- 컴퍼넌트말고 불가피하게 i태그로 써야할경우 (컴퍼넌트사용 권장) -->
            <i class="msf-icon notice"></i>
            <i class="msf-icon cart"></i>
          </GuideSourceBox>
        </div>
      </template>

      <!-- <template #cases><div>아이콘으로 사용가능</div></template> -->
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './IconGuide.vue?raw'
import { ICON_NAMES, ICON_SIZES } from '@/libs/ui/base/MsfIcon.vue'

const componentConfig = {
  name: {
    description: '아이콘 리소스 이름',
    options: [...ICON_NAMES],
    default: 'notice',
  },
  size: {
    description: '아이콘 크기',
    options: [...ICON_SIZES],
    default: 'medium',
  },
}

const componentState = {
  name: 'notice',
  size: 'medium',
}

// const iconSectionStory = [
//   {
//     name: 'Icon List',
//     description: 'name="" 으로 사용 가능한 전체 아이콘 목록',
//     args: {}, // 개별 아이콘 데이터 대신 리스트를 통째로 보여줄 것이므로 비워둠
//   },
// ]
</script>
<style lang="scss" scoped>
/* GuideUnit 내부 스토리 레이아웃 조정 */
:deep(.sb-stories-section) {
  .canvas-preview {
    padding: 0; // 그리드 라인을 끝까지 채우기 위해 0으로 조정
    background: #fff !important;
  }
  .canvas-footer {
    display: none;
  }
}
.icon-grid-layout {
  display: grid;
  /* 세로형일 때는 가로를 조금 더 좁게 배치 (한 줄에 더 많이 보이게) */
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  border: 1px solid #f0f0f0;
}

/* 세로형 아이콘 카드 */
.icon-card-static {
  display: flex;
  flex-direction: column; // 세로 배치
  align-items: center;
  justify-content: center;
  padding: 24px 12px;
  background: transparent;
  border-right: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
  .icon-visual {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 32px;
    height: 32px;
    margin-bottom: 12px; // 아이콘과 텍스트 사이 간격
    color: #111;
  }
  .icon-info {
    display: flex;
    flex-direction: column;
    align-items: center; // 텍스트도 중앙 정렬
    width: 100%;
    gap: 4px;
    .icon-name {
      font-size: 14px;
      color: #333;
      text-align: center;
      width: 100%;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .icon-code {
      font-family: 'Courier New', Courier, monospace;
      font-size: 10px;
      color: #aaa;
      background: transparent;
      padding: 0;
      text-align: center;
    }
  }
}
</style>
