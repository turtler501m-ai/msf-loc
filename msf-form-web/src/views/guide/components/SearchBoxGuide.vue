<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfSearchBox"
      title="MsfSearchBox"
      description="
      내부 요소에 부여하는 클래스(.grid-1 ~ .grid-4)에 따라 너비가 변화합니다.<br/>
      클래스를 지정하지 않으면 기본설정값 grid-2의 스타일로 설정됩니다.<br/>
      @search 이벤트로 검색버튼의 이벤트를 설정합니다.
      "
      caseTitle="자식에게 class를 부여하여 레이아웃 조정 / @search로 검색버튼 이벤트 설정"
    >
      <template #cases>
        <GuideSourceBox
          title="Grid 클래스별 너비 비교"
          description="그리드 클래스를 통해 12컬럼 내에서 차지하는 영역(100%, 50%, 33.3%, 25%)을 조절"
          :source="selfSource"
          id="ex-grid-all"
        >
          <MsfSearchBox @search="handleSearch">
            <MsfInput class="grid-1" placeholder=".grid-1 (100%)" />

            <MsfInput class="grid-2" placeholder=".grid-2 (50%)" />
            <MsfInput class="grid-2" placeholder=".grid-2 (50%)" />

            <MsfInput class="grid-3" placeholder=".grid-3 (33.3%)" />
            <MsfInput class="grid-3" placeholder=".grid-3 (33.3%)" />
            <MsfInput class="grid-3" placeholder=".grid-3 (33.3%)" />

            <MsfInput class="grid-4" placeholder=".grid-4 (25%)" />
            <MsfInput class="grid-4" placeholder=".grid-4 (25%)" />
            <MsfInput class="grid-4" placeholder=".grid-4 (25%)" />
            <MsfInput class="grid-4" placeholder=".grid-4 (25%)" />
          </MsfSearchBox>
        </GuideSourceBox>

        <GuideSourceBox
          title="혼합 배치 예시"
          description="다양한 크기의 필드를 혼합하여 효율적인 검색창 레이아웃을 구성할 수 있습니다."
          :source="selfSource"
          id="ex-grid-mixed"
        >
          <MsfSearchBox @search="handleSearch">
            <MsfInput class="grid-1" placeholder="전체 너비 필드" />
            <MsfInput class="grid-3" placeholder="33.3% 필드" />
            <MsfInput class="grid-3" placeholder="33.3% 필드" />
            <MsfInput class="grid-3" placeholder="33.3% 필드" />
            <MsfInput class="grid-2" placeholder="50% 필드" />
            <MsfInput class="grid-2" placeholder="50% 필드" />
          </MsfSearchBox>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './SearchBoxGuide.vue?raw'

const handleSearch = () => {
  alert('검색버튼 이벤트 실행')
}
</script>

<style lang="scss" scoped></style>
