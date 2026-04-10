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
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'

const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { product } = terminationStore

const isLoadingCharge = ref(false)

// X18 잔여요금 조회
const onClickRemainCharge = async () => {
  isLoadingCharge.value = true
  await terminationStore.apiGetRemainCharge()
  isLoadingCharge.value = false
}

// 필수 항목 입력 완료 여부
const isComplete = computed(() => {
  return (
    !!product.isActive &&
    !!product.usageFee &&
    !!product.penaltyFee &&
    !!product.finalAmount
  )
})

watch(isComplete, (val) => {
  emit('complete', val)
})

const save = async () => {
  if (!isComplete.value) return false
  return true
}

defineExpose({ save })

// 마운트 시 X18 자동 조회
onMounted(async () => {
  if (!product.remainChargeLoaded) {
    await onClickRemainCharge()
  }
})
</script>

<style scoped></style>
