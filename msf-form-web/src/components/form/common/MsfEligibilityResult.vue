<template>
  <div class="step-result" v-if="status === 'checked'">
    <p class="result-title" v-if="isEligible">
      <MsfIcon name="titleInfo" />
      <span
        >가입조건 조회 결과 개통 진행이
        <em class="ut-color-accent">가능({{ result.totalLineCnt }}회선 중 {{ result.possibleLineCnt }}회선 추가 가능)</em> 합니다.</span
      >
    </p>
    <p class="result-title" v-else>
      <MsfIcon name="titleInfo" />
      <span
        >가입조건 조회 결과 개통 진행이
        <em class="ut-color-point">불가능({{ result.totalLineCnt }}회선 중 {{ result.possibleLineCnt }}회선 추가 가능)</em> 합니다.</span
      >
    </p>
    <ul class="result-list">
      <li>
        <span class="result-txt">가입제한</span>
        <MsfFlag
          :data="result.joinLimitYn === 'N' ? '가능' : '불가능'"
          :color="result.joinLimitYn === 'N' ? 'accent' : 'gray'"
        />
      </li>
      <li>
        <span class="result-txt">가입한도</span>
        <MsfFlag
          :data="result.possibleLineCnt > 0 ? '가능' : '불가능'"
          :color="result.possibleLineCnt > 0 ? 'accent' : 'gray'"
        />
      </li>
      <li>
        <span class="result-txt">미납</span>
        <MsfFlag
          :data="result.unpaidYn === 'N' ? '가능' : '불가능'"
          :color="result.unpaidYn === 'N' ? 'accent' : 'gray'"
        />
      </li>
      <li>
        <span class="result-txt">상습해지이력</span>
        <MsfFlag
          :data="result.frequentTermYn === 'N' ? '가능' : '불가능'"
          :color="result.frequentTermYn === 'N' ? 'accent' : 'gray'"
        />
      </li>
      <li>
        <span class="result-txt">할부할인</span>
        <MsfFlag
          :data="result.installmentLimitYn === 'N' ? '가능' : '불가능'"
          :color="result.installmentLimitYn === 'N' ? 'accent' : 'gray'"
        />
      </li>
    </ul>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  status: { type: String, default: 'none' },
  result: {
    type: Object,
    default: () => ({
      totalLineCnt: 3,
      possibleLineCnt: 0,
      joinLimitYn: 'N',
      unpaidYn: 'N',
      frequentTermYn: 'N',
      installmentLimitYn: 'N',
    }),
  },
})

const isEligible = computed(() => {
  const r = props.result
  return (
    r.possibleLineCnt > 0 &&
    r.joinLimitYn === 'N' &&
    r.unpaidYn === 'N' &&
    r.frequentTermYn === 'N' &&
    r.installmentLimitYn === 'N'
  )
})
</script>

<style scoped lang="scss"></style>
