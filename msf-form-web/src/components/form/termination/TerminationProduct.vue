<template>
  <div class="page-step-panel">
    <!-- 해지 신청 -->
    <MsfTitleArea title="해지 신청" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="사용여부" tag="div" required>
        <MsfChip
          v-model="product.isActive"
          name="inp-isActive"
          :data="[
            { value: 'MM', label: 'kt M mobile 재사용' },
            { value: 'KT', label: 'kt 재사용' },
            { value: 'SK', label: 'skt 사용' },
            { value: 'LG', label: 'lgt 사용' },
            { value: 'ETC', label: '기타' },
          ]"
        />
      </MsfFormGroup>
    </MsfStack>
    <!-- // 해지 신청 -->

    <!-- 해지 정산 -->
    <MsfTitleArea title="해지 정산">
      <template #right>
        <MsfButton variant="subtle" :disabled="isLoadingCharge" @click="onClickRemainCharge">
          {{ isLoadingCharge ? '조회 중...' : '잔여요금 조회' }}
        </MsfButton>
      </template>
    </MsfTitleArea>
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="사용요금" required>
        <MsfStack type="field">
          <MsfInput
            v-model="product.usageFee"
            placeholder="금액을 입력하세요."
            align="right"
            :clearable="false"
            class="ut-w-300"
          />
          <span class="unit-sep">원</span>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="위약금" required>
        <MsfStack type="field">
          <MsfInput
            v-model="product.penaltyFee"
            placeholder="금액을 입력하세요."
            align="right"
            :clearable="false"
            class="ut-w-300"
          />
          <span class="unit-sep">원</span>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="최종 정산요금" required>
        <MsfStack type="field">
          <MsfInput
            v-model="product.finalAmount"
            placeholder="금액을 입력하세요."
            align="right"
            :clearable="false"
            class="ut-w-300"
          />
          <span class="unit-sep">원</span>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup
        label="잔여분할상환<br/>기간/금액"
        helpText="※ 해지 시 까지 사용한 사용료, 위약금, 잔여 단말기 대금 등의 자세한 사용 요금은 다음달 청구서에서 확인 가능합니다."
      >
        <MsfStack type="field">
          <MsfInput
            v-model="product.remainPeriod"
            placeholder="분할상환 기간을 입력하세요."
            ariaLabel="잔여분할상환 분할상환 기간을 입력하세요."
            align="right"
            :clearable="false"
            class="ut-w-300"
          />
          <span class="unit-sep">개월</span>
          <MsfInput
            v-model="product.remainAmount"
            id="inp-remainAmount"
            placeholder="금액을 입력하세요."
            ariaLabel="잔여분할상환 금액을 입력하세요."
            align="right"
            :clearable="false"
            class="ut-w-300 ut-ml-8"
          />
          <span class="unit-sep">원</span>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 해지 정산 -->

    <!-- 메모 -->
    <MsfTitleArea title="메모" />
    <MsfFormGroup label="메모">
      <MsfTextarea v-model="product.memo" placeholder="메모 입력" />
    </MsfFormGroup>
    <!-- // 메모 -->

    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주신 부분 - 화면 프로세스</p>
        <select v-model="isCompleteOverride">
          <option value="">해지정산 저장</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'

/**
 * Step 2: 해지 정산 정보 입력 컴포넌트
 *
 * 역할:
 *  - 사용여부(isActive), 사용요금·위약금·최종정산요금 입력
 *  - '잔여요금 조회' 버튼으로 X18 API 직접 호출 가능 (수동 재조회)
 *  - X18 데이터는 Step 1 save() 시 자동 세팅됨 (여기서 onMounted 조회 불필요)
 *  - 필수 항목 완료 여부를 부모(MsfFormView)에 emit('complete') 로 전달
 *
 * 부모와의 인터페이스:
 *  - emit('complete', boolean) : 다음 버튼 활성화 여부 제어
 *  - defineExpose({ save })    : 부모가 save() 를 직접 호출하여 저장 처리
 */

// 부모(MsfFormView)로 현재 스텝 완료 여부를 전달하는 이벤트
const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { product } = terminationStore

/**
 * 잔여요금 조회 중 로딩 상태 플래그
 * true → '조회 중...' 표시 + 버튼 비활성화
 */
const isLoadingCharge = ref(false)

/**
 * X18 잔여요금 수동 재조회 (우측 상단 '잔여요금 조회' 버튼 클릭)
 * Step 1 save() 에서 자동 조회되지만, 수동 재조회가 필요한 경우 사용
 */
const onClickRemainCharge = async () => {
  console.log('[Step2] 잔여요금 수동 재조회 버튼 클릭')
  isLoadingCharge.value = true
  await terminationStore.apiGetRemainCharge()
  isLoadingCharge.value = false
  console.log('[Step2] 수동 재조회 후 product 상태', { usageFee: product.usageFee, penaltyFee: product.penaltyFee, finalAmount: product.finalAmount })
}

/**
 * 필수 항목 입력 완료 여부 (computed)
 * - isActive: 사용여부 선택
 * - usageFee: 사용요금 입력
 * - penaltyFee: 위약금 입력
 * - finalAmount: 최종 정산요금 입력
 *
 * [TEST] 화면 테스트 시 'product' 스토어에 값이 없으면 항상 false
 * 테스트를 위해서는 아래 isCompleteOverride 사용
 */
const isComplete = computed(() => {
  return (
    !!product.isActive &&
    !!product.usageFee &&
    !!product.penaltyFee &&
    !!product.finalAmount
  )
})

/**
 * [TEST] 화면 테스트용 오버라이드 — 개발/검증 완료 후 ref('') 으로 초기화 필요
 * 'true'  → 강제 성공 (computed isComplete 무시)
 * 'false' → 강제 실패
 * ''      → 미선택 (computed isComplete 로직 사용)
 */
const isCompleteOverride = ref('true')

/**
 * 실제 완료 여부: 오버라이드 값이 있으면 우선, 없으면 computed isComplete 사용
 */
const isCompleteEffective = computed(() => {
  if (isCompleteOverride.value === 'true') return true
  if (isCompleteOverride.value === 'false') return false
  return isComplete.value
})

/**
 * isCompleteEffective 변경 감지 → 부모에 완료 여부 전달
 * immediate: true — 컴포넌트 로드 시 즉시 실행하여 초기 버튼 상태 반영
 */
watch(
  isCompleteEffective,
  (val) => {
    emit('complete', val)
  },
  { immediate: true },
)

/**
 * onMounted 시점에 초기 완료 상태를 부모에 한 번 더 전달 (타이밍 보장)
 */
onMounted(() => {
  console.log('[Step2] onMounted - product 초기 상태', { usageFee: product.usageFee, penaltyFee: product.penaltyFee, finalAmount: product.finalAmount, remainChargeLoaded: product.remainChargeLoaded })
  emit('complete', isCompleteEffective.value)
})

/**
 * 다음 버튼 클릭 시 부모(MsfFormView.onClickNextBtn)에서 호출
 * @returns {Promise<boolean>} 성공 true / 실패 false
 */
const save = async () => {
  if (!isCompleteEffective.value) return false
  return true
}

defineExpose({ save })
</script>

<style scoped></style>
