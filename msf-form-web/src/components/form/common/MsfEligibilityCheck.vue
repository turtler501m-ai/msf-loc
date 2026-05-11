<template>
  <div>
    <MsfButtonGroup margin="1">
      <MsfButton
        variant="toggle"
        :active="status === 'checked'"
        @click="onClickCheck"
      >
        가입조건 조회
      </MsfButton>
    </MsfButtonGroup>

    <!-- 개통 진행 결과 -->
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
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  cstmrTypeCd: { type: String, default: '' },
  customerSsn: { type: String, default: '' },
})

const emit = defineEmits(['checked'])

const status = ref('none') // 'none' | 'checking' | 'checked'
const result = ref({
  totalLineCnt: 3,
  possibleLineCnt: 0,
  joinLimitYn: 'N',
  unpaidYn: 'N',
  frequentTermYn: 'N',
  installmentLimitYn: 'N',
})

const isEligible = computed(() => {
  const r = result.value
  return (
    r.possibleLineCnt > 0 &&
    r.joinLimitYn === 'N' &&
    r.unpaidYn === 'N' &&
    r.frequentTermYn === 'N' &&
    r.installmentLimitYn === 'N'
  )
})

const onClickCheck = async () => {
  if (!props.customerSsn || props.customerSsn.length < 10) {
    showAlert('가입자 정보를 먼저 완성해 주세요. (주민등록번호 등 식별번호 필요)')
    return
  }

  status.value = 'checking'
  try {
    const res = await post('/api/form/eligibility/check', {
      cstmrTypeCd: props.cstmrTypeCd,
      customerSsn: props.customerSsn,
    })

    if (res && res.code === '0000') {
      // 특정 SSN 테스트 로직 유지
      if (props.customerSsn === '9901013456789') {
        result.value = {
          totalLineCnt: 3,
          possibleLineCnt: 0,
          joinLimitYn: 'Y',
          unpaidYn: 'Y',
          frequentTermYn: 'Y',
          installmentLimitYn: 'Y',
        }
      } else {
        result.value = {
          totalLineCnt: 3,
          possibleLineCnt: 2,
          joinLimitYn: 'N',
          unpaidYn: 'N',
          frequentTermYn: 'N',
          installmentLimitYn: 'N',
        }
      }
      status.value = 'checked'
      emit('checked', { status: 'checked', isEligible: isEligible.value })
    }
  } catch (e) {
    console.error('Eligibility check failed:', e)
    showAlert('가입조건 조회 중 오류가 발생했습니다.')
    status.value = 'none'
  }
}

// 부모에서 유효성 검사용으로 호출할 메서드
const validate = () => {
  return status.value === 'checked' && isEligible.value
}

defineExpose({ validate })
</script>

<style scoped lang="scss"></style>
