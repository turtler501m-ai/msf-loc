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
        <!-- SVG 아이콘 등록 사용 가이드 -->
        <div class="icon-usage-guide">
          <p class="guide-title">MsfIcon 컴포넌트 아이콘 등록 및 사용 방식</p>
          <ol class="guide-steps">
            <li>
              <strong>SVG 파일 준비</strong>
              <span>추가하려는 아이콘의 원본 SVG 파일을 준비</span>
            </li>
            <li>
              <strong>SVG 변환</strong>
              <span>
                <a
                  href="https://yoksel.github.io/url-encoder/"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  SVG URL Encoder
                </a>
                에서 SVG를 변환한 뒤 'Ready for CSS' 값을 복사
              </span>
            </li>
            <li>
              <strong>SVG 등록</strong>
              <span>
                <code>src/assets/styles/abstracts/_icons.scss</code>의 <code>$icons</code>에 이름과
                원본 크기를 추가
              </span>
            </li>
            <li>
              <strong>아이콘 이름 등록</strong>
              <span>
                <code>src/libs/ui/base/MsfIcon.vue</code>의 <code>ICON_NAMES</code>에 같은 이름을
                추가
              </span>
            </li>
            <li>
              <strong>MsfIcon 사용</strong>
              <span><code>&lt;MsfIcon name="arrowRight" size="small" /&gt;</code></span>
            </li>
            <li>
              <strong>화면 확인</strong>
              <span>가이드 또는 실제 화면에서 아이콘 이름, 크기, 색상을 확인</span>
            </li>
          </ol>
          <div class="guide-meta">
            <span><strong>크기</strong> xsmall · small · medium · large</span>
            <span><strong>색상</strong> CSS color 또는 <code>--icon-color</code></span>
          </div>
        </div>
        <!-- // SVG 아이콘 등록 사용 가이드 -->
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
  margin-top: 32px;
  border: 1px solid #f0f0f0;
}
.icon-add-info {
  margin-top: 48px;
  .guide-title {
    font-weight: 500;
    margin-top: 0;
    & + * {
      margin-top: 12px;
    }
  }
}

/* 세로형 아이콘 카드 */
.icon-usage-guide {
  padding: 24px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #f6f8fa;
  .guide-title {
    margin-top: 0;
    font-size: 16px;
    font-weight: 600;
    color: #111;
  }
  .guide-steps {
    display: grid;
    gap: 12px;
    margin: 16px 0 0;
    padding-left: 20px;
    li {
      line-height: 1.5;
      color: #555;
      strong {
        margin-right: 8px;
        color: #111;
      }
    }
  }
  .guide-meta {
    display: flex;
    flex-wrap: wrap;
    gap: 8px 20px;
    margin-top: 18px;
    padding-top: 18px;
    border-top: 1px solid #eee;
    color: #555;
    line-height: 1.5;
    strong {
      color: #111;
    }
  }
  code {
    padding: 2px 4px;
    border-radius: 3px;
    background: #f5f5f5;
    color: #e83e8c;
    font-size: 12px;
  }
  a {
    color: #2563eb;
    text-decoration: underline;
    text-underline-offset: 2px;
  }
}
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
