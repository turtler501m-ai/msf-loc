<template>
  <div>
    <MsfButtonGroup margin="1">
      <MsfButton
        ref="searchEligibilityBtnRef"
        variant="toggle"
        :active="status === 'checked'"
        @click="onClickCheck"
      >
        가입조건 조회
      </MsfButton>
    </MsfButtonGroup>

    <!-- 법인 대량 개통 진행 결과 -->
    <div class="step-result" v-if="status === 'checked' && isBulkMode">
      <p class="result-title" v-if="isEligible">
        <MsfIcon name="titleInfo" />
        <span
          >가입조건 조회 결과 개통 진행이
          <em class="ut-color-accent">가능(총 {{ props.bulkActivationCnt || 1 }}회선 추가 가능)</em>
          합니다.</span
        >
      </p>
      <p class="result-title" v-else>
        <MsfIcon name="titleInfo" />
        <span
          >가입조건 조회 결과 개통 진행이
          <em class="ut-color-point">불가능({{ bulkResult.possibleLineCnt || 0 }}회선 추가 가능)</em>
          합니다.</span
        >
      </p>

      <!-- 개통 불가 상세 사유 표시 -->
      <div v-if="!isEligible" class="eligibility-reason-box ut-mt-8 ut-mb-16">
        <span class="reason-label" style="font-weight: bold; margin-right: 8px"
          >개통 불가 사유:</span
        >
        <span class="reason-text ut-color-point" style="font-weight: 500">{{
          eligibilityMessage
        }}</span>
      </div>

      <ul class="result-list">
        <li>
          <span class="result-txt">가입제한</span>
          <MsfFlag :data="isEligible ? '가능' : '불가능'" :color="isEligible ? 'accent' : 'gray'" />
        </li>
      </ul>
    </div>

    <!-- 일반 개통 진행 결과 (기존) -->
    <div class="step-result" v-if="status === 'checked' && !isBulkMode">
      <p class="result-title" v-if="isEligible">
        <MsfIcon name="titleInfo" />
        <span
          >가입조건 조회 결과 개통 진행이
          <em class="ut-color-accent"
            >가능({{ result.totalLineCnt }}회선 중 {{ result.possibleLineCnt }}회선 추가 가능)</em
          >
          합니다.</span
        >
      </p>
      <p class="result-title" v-else>
        <MsfIcon name="titleInfo" />
        <span
          >가입조건 조회 결과 개통 진행이
          <em class="ut-color-point"
            >불가능({{ result.totalLineCnt }}회선 중 {{ result.possibleLineCnt }}회선 추가 가능)</em
          >
          합니다.</span
        >
      </p>

      <!-- 개통 불가 상세 사유 표시 -->
      <div v-if="!isEligible" class="eligibility-reason-box ut-mt-8 ut-mb-16">
        <span class="reason-label" style="font-weight: bold; margin-right: 8px"
          >개통 불가 사유:</span
        >
        <span class="reason-text ut-color-point" style="font-weight: 500">{{
          eligibilityMessage
        }}</span>
      </div>

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
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  cstmrTypeCd: { type: String, default: '' },
  customerSsn: { type: String, default: '' },
  beforeCheck: { type: Function, default: null },
  joinType: { type: String, default: '' },
  bizBulkActivationAvailYn: { type: String, default: 'N' },
  bulkActivationCnt: { type: [Number, String], default: 1 },
  simTypeCd: { type: String, default: '' },
  agentCd: { type: String, default: '' },
})

const emit = defineEmits(['checked'])

const searchEligibilityBtnRef = ref(null)

const status = ref('none') // 'none' | 'checking' | 'checked'
const result = ref({
  totalLineCnt: 3,
  possibleLineCnt: 0,
  joinLimitYn: 'N',
  unpaidYn: 'N',
  frequentTermYn: 'N',
  installmentLimitYn: 'N',
})

// 가입 제한 상세 원본 데이터 보관용
const rawCounts = ref({
  yearActCnt: 0,
  yearCanCnt: 0,
  delinqStatusCnt: 0,
  thisMonthActCnt: 0,
  totActCnt: 0,
})

// 법인대량 가입조건 조회 결과 데이터 보관용
const bulkResult = ref({
  isEligible: false,
  reason: '',
  maxActivationCnt: 0,
  completedCnt: 0,
  writtenCnt: 0,
})

// 법인대량 모드 판정
const isBulkMode = computed(() => {
  return (
    props.cstmrTypeCd === 'JP' &&
    props.bizBulkActivationAvailYn === 'Y' &&
    props.joinType === 'NAC3'
  )
})

const isEligible = computed(() => {
  if (isBulkMode.value) {
    return bulkResult.value.isEligible
  }
  const r = result.value
  return (
    r.possibleLineCnt > 0 &&
    r.joinLimitYn === 'N' &&
    r.unpaidYn === 'N' &&
    r.frequentTermYn === 'N' &&
    r.installmentLimitYn === 'N'
  )
})

// 개통 불가 사유 상세 텍스트 생성
const eligibilityMessage = computed(() => {
  if (isBulkMode.value) {
    return (
      bulkResult.value.reason ||
      (bulkResult.value.isEligible
        ? '가입조건 조회 결과 개통 진행이 가능합니다.'
        : '가입조건 조회 결과 개통 진행이 불가능합니다.')
    )
  }

  if (isEligible.value) {
    return '가입조건 조회 결과 개통 진행이 가능합니다.'
  }

  const reasons = []
  const isForeigner = props.cstmrTypeCd?.startsWith('F')
  const maxMonthLimit = isForeigner ? 1 : 2
  const maxTotalLimit = isForeigner ? 2 : 3

  if (result.value.unpaidYn === 'Y') {
    reasons.push('체납여부 및 미납금액이 존재합니다. (미납 1원 이상 시 개통 불가)')
  }

  if (result.value.frequentTermYn === 'Y') {
    if (rawCounts.value.yearActCnt === 3 && rawCounts.value.yearCanCnt >= 1) {
      reasons.push('1년 이내 3회선 사용 중 해지 이력이 존재하여 가입이 불가능합니다.')
    } else if (
      rawCounts.value.yearActCnt === 2 &&
      rawCounts.value.yearCanCnt >= 1 &&
      rawCounts.value.totActCnt >= 1
    ) {
      reasons.push('1년 이내 2회선 사용 중 해지 이력이 있어 가입 한도(1회선)를 초과하였습니다.')
    } else if (rawCounts.value.yearCanCnt >= 1 && rawCounts.value.totActCnt >= 1) {
      reasons.push('1년 이내 해지 후 재가입은 최대 2회선 제한에 따라 추가 가입이 불가능합니다.')
    } else {
      reasons.push('동일명의 회선의 개통취소/해지 이력 제한에 해당되어 가입이 불가능합니다.')
    }
  }

  if (rawCounts.value.thisMonthActCnt >= maxMonthLimit) {
    reasons.push(
      `당월 가입한도(${maxMonthLimit}회선)를 초과하였습니다. (현재 당월 개통: ${rawCounts.value.thisMonthActCnt}회선)`,
    )
  }
  if (rawCounts.value.totActCnt >= maxTotalLimit) {
    reasons.push(
      `전체 보유한도(${maxTotalLimit}회선)를 초과하였습니다. (현재 전체 개통: ${rawCounts.value.totActCnt}회선)`,
    )
  }

  if (result.value.possibleLineCnt === 0 && reasons.length === 0) {
    reasons.push('추가 가능한 보유 회선 한도가 남아있지 않습니다.')
  }

  return reasons.join(' / ')
})

const onClickCheck = async () => {
  if (props.beforeCheck && typeof props.beforeCheck === 'function') {
    const isOk = props.beforeCheck()
    if (!isOk) return
  }

  if (isBulkMode.value) {
    if (!props.agentCd) {
      showAlert('대리점을 선택해 주세요.')
      return
    }

    status.value = 'checking'

    try {
      const payload = {
        cstmrTypeCd: props.cstmrTypeCd || 'JP',
        operTypeCd: props.joinType || 'NAC3',
        agentCd: props.agentCd,
        cpntId: '',
        volumeMobileNoQnty: String(props.bulkActivationCnt || 1),
      }

      const res = await post('/api/form/bulkCorporateOpenInfo/check', payload, { skipAlert: true })

      if (!res || res.code !== '0000') {
        showAlert(res?.message || '법인대량 가입조건 조회 중 오류가 발생했습니다.')
        status.value = 'none'
        return
      }

      const resData = res.data || {}
      const innerData = resData.resData || {}

      // 새로운 API 스펙과 기존 스펙 모두 호환되도록 매핑 적용
      const maxActivationCnt = innerData.sbscLmtQnty !== undefined
        ? Number(innerData.sbscLmtQnty)
        : (resData.maxActivationCnt !== undefined ? Number(resData.maxActivationCnt) : 0)

      const completedCnt = innerData.completeCount !== undefined
        ? Number(innerData.completeCount)
        : (resData.completedCnt !== undefined ? Number(resData.completedCnt) : 0)

      const writtenCnt = innerData.openCount !== undefined
        ? Number(innerData.openCount)
        : (resData.writtenCnt !== undefined ? Number(resData.writtenCnt) : 0)

      const possibleLineCnt = innerData.limitCount !== undefined
        ? Number(innerData.limitCount)
        : (resData.possibleLineCnt !== undefined
            ? Number(resData.possibleLineCnt)
            : Math.max(0, maxActivationCnt - completedCnt - writtenCnt))

      // 법인 대량개통은 resCode가 0000이거나, canBulkCorporateOpenYn 플래그가 Y이면 가능으로 판단
      let eligible = resData.resCode === '0000' || innerData.canBulkCorporateOpenYn === 'Y'
      let customReason = ''

      const inputCnt = Number(props.bulkActivationCnt || 1)
      if (inputCnt > possibleLineCnt) {
        eligible = false
        customReason = '개통 가능 회선수가 초과하였습니다.'
      }

      bulkResult.value = {
        isEligible: eligible,
        reason: eligible
          ? ''
          : customReason || innerData.resMessage || resData.resMessage || '신규개통 최대 수량을 초과하였거나 가입이 불가합니다.',
        maxActivationCnt,
        completedCnt,
        writtenCnt,
        possibleLineCnt,
      }

      status.value = 'checked'
      emit('checked', { status: 'checked', isEligible: eligible })
    } catch (e) {
      console.error(e)
      status.value = 'none'
    }
    return
  }

  // 기존 일반 개통 가입조건 조회
  if (!props.cstmrTypeCd) {
    showAlert('가입자 유형을 선택해 주세요.')
    return
  }
  if (!props.customerSsn || props.customerSsn.length < 10) {
    showAlert('가입자 정보를 먼저 입력해 주세요.')
    return
  }

  status.value = 'checking'
  try {
    const payload = {
      cstmrTypeCd: props.cstmrTypeCd,
      customerSsn: props.customerSsn,
    }

    // 5개 API 병렬 호출 (skipAlert 옵션으로 공통 알림 우회 처리)
    const [resYearAct, resYearCan, resUnpaid, resThisMonthAct, resTotAct] = await Promise.all([
      post('/api/form/eligibility/actyearcnt/get', payload, { skipAlert: true }),
      post('/api/form/eligibility/cancelyearcnt/get', payload, { skipAlert: true }),
      post('/api/form/eligibility/unpaidcnt/get', payload, { skipAlert: true }),
      post('/api/form/eligibility/actthismonthcnt/get', payload, { skipAlert: true }),
      post('/api/form/eligibility/acttotalcnt/get', payload, { skipAlert: true }),
    ])

    // API 응답 데이터 범용 안전 파서
    const getCount = (res, defaultValue = 0) => {
      if (!res || res.code !== '0000') return defaultValue
      const d = res.data
      if (typeof d === 'number') return d
      if (d === null || d === undefined) return defaultValue

      const keys = Object.keys(d)
      if (keys.length > 0) {
        for (const key of keys) {
          if (typeof d[key] === 'number') return d[key]
          if (typeof d[key] === 'string' && !isNaN(d[key])) return Number(d[key])
        }
      }
      return defaultValue
    }

    const yearActCnt = getCount(resYearAct)
    const yearCanCnt = getCount(resYearCan)
    const delinqStatusCnt = getCount(resUnpaid)
    const thisMonthActCnt = getCount(resThisMonthAct)
    const totActCnt = getCount(resTotAct)

    // 원본 데이터 적재
    rawCounts.value = {
      yearActCnt,
      yearCanCnt,
      delinqStatusCnt,
      thisMonthActCnt,
      totActCnt,
    }

    const isForeigner = props.cstmrTypeCd?.startsWith('F')
    const maxMonthLimit = isForeigner ? 1 : 2
    const maxTotalLimit = isForeigner ? 2 : 3

    // 1. 가입한도 계산 (추가 가능 회선 수)
    const possibleLineCnt = Math.max(0, Math.min(maxTotalLimit - totActCnt))

    // 2. 미납 여부
    const unpaidYn = delinqStatusCnt > 0 ? 'Y' : 'N'

    // 3. 동일명의 개통취소/해지 이력
    let isTermLimited = false
    if (yearActCnt === 3 && yearCanCnt >= 1) {
      isTermLimited = true
    } else if (yearActCnt === 2 && yearCanCnt >= 1 && totActCnt >= 1) {
      isTermLimited = true
    } else if (yearCanCnt >= 1 && totActCnt >= 1) {
      isTermLimited = true
    }
    const frequentTermYn = isTermLimited ? 'Y' : 'N'

    // 4. 가입제한 여부 (보유 한도 초과 or 해지 이력 제한)
    const isLimitExceeded = thisMonthActCnt >= maxMonthLimit || totActCnt >= maxTotalLimit
    const joinLimitYn = isLimitExceeded || isTermLimited ? 'Y' : 'N'

    // 5. 할부할인 제한 (미납이 있거나 가입 제한 시)
    const installmentLimitYn = unpaidYn === 'Y' || joinLimitYn === 'Y' ? 'Y' : 'N'

    result.value = {
      totalLineCnt: maxTotalLimit,
      possibleLineCnt,
      joinLimitYn,
      unpaidYn,
      frequentTermYn,
      installmentLimitYn,
    }

    status.value = 'checked'
    emit('checked', { status: 'checked', isEligible: isEligible.value })
  } catch {
    status.value = 'none'
  }
}

// 부모에서 유효성 검사용으로 호출할 메서드
const validate = () => {
  return status.value === 'checked' && isEligible.value
}

const reset = () => {
  status.value = 'none'
  result.value = {
    totalLineCnt: 3,
    possibleLineCnt: 0,
    joinLimitYn: 'N',
    unpaidYn: 'N',
    frequentTermYn: 'N',
    installmentLimitYn: 'N',
  }
  rawCounts.value = {
    yearActCnt: 0,
    yearCanCnt: 0,
    delinqStatusCnt: 0,
    thisMonthActCnt: 0,
    totActCnt: 0,
  }
  bulkResult.value = {
    isEligible: false,
    reason: '',
    maxActivationCnt: 0,
    completedCnt: 0,
    writtenCnt: 0,
    possibleLineCnt: 0,
  }
}

const checkValidation = () => {
  if (!validate()) {
    showAlert(`가입조건 조회를 진행하세요`, () => {
      searchEligibilityBtnRef.value?.focus()
    })
    return false
  }
  return true
}

defineExpose({ validate, reset, checkValidation })
</script>

<style scoped lang="scss">
.eligibility-reason-box {
  background-color: var(--color-gray-50);
  border: 1px solid var(--color-gray-150);
  border-radius: var(--border-radius-base);
  padding: rem(12px) rem(16px);
  display: flex;
  align-items: center;
  font-size: var(--font-size-14);
}
</style>
