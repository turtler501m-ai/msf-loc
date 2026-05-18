<template>
  <div class="step-result" v-if="status === 'checked'">
    <p class="result-title" v-if="isEligible">
      <MsfIcon name="titleInfo" />
      <span
        >가입조건 조회 결과 개통 진행이
        <em class="ut-color-accent">{{ result.subscriptionLimitResultMessage }}</em> 합니다.</span
      >
    </p>
    <p class="result-title" v-else>
      <MsfIcon name="titleInfo" />
      <span
        >가입조건 조회 결과 개통 진행이
        <em class="ut-color-point">{{ result.subscriptionLimitResultMessage || '불가능' }}</em> 합니다.</span
      >
    </p>
    <ul class="result-list">
      <li>
        <span class="result-txt">가입제한</span>
        <MsfFlag
          :data="result.subscriptionRestrictionsYn === 'Y' ? '가능' : '불가능'"
          :color="result.subscriptionRestrictionsYn === 'Y' ? 'accent' : 'gray'"
        />
      </li>
      <li>
        <span class="result-txt">가입한도</span>
        <MsfFlag
          :data="result.subscriptionLimitYn === 'Y' ? '가능' : '불가능'"
          :color="result.subscriptionLimitYn === 'Y' ? 'accent' : 'gray'"
        />
      </li>
      <li>
        <span class="result-txt">미납</span>
        <MsfFlag
          :data="result.unPaidYn === 'Y' ? '가능' : '불가능'"
          :color="result.unPaidYn === 'Y' ? 'accent' : 'gray'"
        />
      </li>
      <li>
        <span class="result-txt">상습해지이력</span>
        <MsfFlag
          :data="result.historyOfCancellationYn === 'Y' ? '가능' : '불가능'"
          :color="result.historyOfCancellationYn === 'Y' ? 'accent' : 'gray'"
        />
      </li>
      <li>
        <span class="result-txt">할부할인</span>
        <MsfFlag
          :data="result.installmentDiscountYn === 'Y' ? '가능' : '불가능'"
          :color="result.installmentDiscountYn === 'Y' ? 'accent' : 'gray'"
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
      historyOfCancellationResultMessage: '',
      historyOfCancellationYn: 'Y',
      installmentDiscountResultMessage: '',
      installmentDiscountYn: 'Y',
      subscriptionLimitResultMessage: '',
      subscriptionLimitYn: 'Y',
      subscriptionRestrictionsResultMessage: '',
      subscriptionRestrictionsYn: 'Y',
      unPaidResultMessage: '',
      unPaidYn: 'Y',
    }),
  },
})

const isEligible = computed(() => {
  const r = props.result
  return (
    r.subscriptionRestrictionsYn === 'Y' &&
    r.subscriptionLimitYn === 'Y' &&
    r.unPaidYn === 'Y' &&
    r.historyOfCancellationYn === 'Y' &&
    r.installmentDiscountYn === 'Y'
  )
})
</script>

<style scoped lang="scss"></style>
