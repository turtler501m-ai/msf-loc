<template>
  <div class="page-step-panel space-y-6">
    <h3 class="text-lg font-semibold">서비스 해지 - 상품·요금 정보</h3>
    <p class="text-sm text-gray-600">해지 대상 회선, 사용여부·잔여요금·위약금을 확인해 주세요.</p>

    <!-- S104020101 해지 신청: 사용여부 -->
    <section class="p-4 border border-gray-200 rounded-lg">
      <div class="cncl-form-row">
        <span class="cncl-label">사용여부 <span class="text-red-500">*</span></span>
        <div class="cncl-input flex flex-wrap gap-2">
          <label
            v-for="u in useTypeOptions"
            :key="u.value"
            class="cncl-radio-box"
            :class="{ 'cncl-radio-box--selected': form.useType === u.value }"
          >
            <input v-model="form.useType" type="radio" :value="u.value" name="cancelUseType" class="rounded-full shrink-0" />
            <span class="text-sm whitespace-nowrap">{{ u.label }}</span>
          </label>
        </div>
      </div>
    </section>

    <!-- S104020101 해지 정산 (X18 실시간요금조회 → 직접 입력 가능) -->
    <section class="p-4 border border-amber-100 rounded-lg bg-amber-50">
      <h4 class="font-medium mb-3">해지 정산</h4>
      <div class="cncl-form-rows">
        <div class="cncl-form-row">
          <label class="cncl-label">사용요금 <span class="text-red-500">*</span></label>
          <div class="cncl-input flex items-center gap-1">
            <input v-model.number="form.remainCharge" type="number" min="0" placeholder="금액을 입력하세요" class="cncl-field flex-1 text-right" />
            <span class="text-gray-600 shrink-0">원</span>
          </div>
        </div>
        <div class="cncl-form-row">
          <label class="cncl-label">위약금 <span class="text-red-500">*</span></label>
          <div class="cncl-input flex items-center gap-1">
            <input v-model.number="form.penalty" type="number" min="0" placeholder="금액을 입력하세요" class="cncl-field flex-1 text-right" />
            <span class="text-gray-600 shrink-0">원</span>
          </div>
        </div>
        <div class="cncl-form-row">
          <label class="cncl-label">최종 정산요금 <span class="text-red-500">*</span></label>
          <div class="cncl-input flex items-center gap-1">
            <input v-model.number="form.lastSumAmt" type="number" min="0" placeholder="금액을 입력하세요" class="cncl-field flex-1 text-right" />
            <span class="text-gray-600 shrink-0">원</span>
          </div>
        </div>
        <div class="cncl-form-row">
          <label class="cncl-label">잔여분할상환</label>
          <div class="cncl-input flex items-center gap-1">
            <input v-model.number="form.installmentPeriod" type="number" min="0" placeholder="기간" class="cncl-field w-20 text-right" />
            <span class="text-gray-600 shrink-0">개월</span>
            <input v-model.number="form.installmentRemain" type="number" min="0" placeholder="금액" class="cncl-field flex-1 text-right" />
            <span class="text-gray-600 shrink-0">원</span>
          </div>
        </div>
      </div>
      <p class="text-xs text-gray-500 mt-3">※ 해지 시까지 사용한 사용료, 위약금, 잔여 단말기 대금 등의 자세한 사용 요금은 다음달 청구서에서 확인 가능합니다.</p>
    </section>

    <section class="p-4 border border-gray-200 rounded-lg">
      <div class="cncl-form-row items-start">
        <span class="cncl-label pt-2">메모</span>
        <div class="cncl-input">
          <textarea v-model="form.memo" placeholder="메모 입력" rows="3" class="cncl-field w-full" />
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useCancelFormStore } from '@/stores/cancel_form'
import { getRemainCharge } from '@/api/cancel'

const emit = defineEmits(['complete'])
const formStore = useCancelFormStore()

const useTypeOptions = [
  { value: 'KT_M_MOBILE_REUSE', label: 'kt M mobile 재사용' },
  { value: 'KT_REUSE', label: 'kt 재사용' },
  { value: 'SKT_USE', label: 'skt 사용' },
  { value: 'LGT_USE', label: 'lgt 사용' },
  { value: 'ETC', label: '기타' },
]

function num(v) {
  if (v == null || v === '') return 0
  const n = Number(v)
  return Number.isNaN(n) ? 0 : n
}

const form = ref({
  useType: '',
  remainCharge: null,
  penalty: null,
  lastSumAmt: null,
  installmentPeriod: null,
  installmentRemain: null,
  memo: '',
})

watch(form, (v) => {
  formStore.setProductForm(v)
  emit('complete', !!v.useType)
}, { deep: true })

onMounted(async () => {
  const saved = formStore.productForm
  if (saved && Object.keys(saved).length) form.value = { ...form.value, ...saved }
  const c = formStore.customerForm
  const ctn = c?.phone?.replace?.(/-/g, '') || ''
  if (ctn) {
    try {
      const data = await getRemainCharge({ name: c?.name, ctn, ncn: c?.ncn, custId: c?.custId })
      if (data && (data.remainCharge != null || data.penalty != null || data.installmentRemain != null)) {
        form.value.remainCharge = data.remainCharge ?? form.value.remainCharge
        form.value.penalty = data.penalty ?? form.value.penalty
        form.value.installmentRemain = data.installmentRemain ?? form.value.installmentRemain
        // 최종 정산요금 자동 계산 세팅 (사용자가 이후 수정 가능)
        const r = num(form.value.remainCharge), p = num(form.value.penalty), i = num(form.value.installmentRemain)
        if (r + p + i > 0) form.value.lastSumAmt = r + p + i
      }
    } catch (e) {
      console.warn('잔여 요금 조회 실패:', e)
    }
  }
  emit('complete', !!form.value.useType)
})

const save = async () => {
  if (!form.value.useType) { alert('사용여부를 선택해 주세요.'); return false }
  formStore.setProductForm(form.value)
  return true
}

defineExpose({ save })
</script>

<style scoped>
@reference "tailwindcss";

.page-step-panel {
  @apply w-full p-4 border rounded-md;
}
.cncl-form-rows {
  @apply space-y-3;
}
.cncl-form-row {
  @apply flex items-center gap-4;
}
.cncl-label {
  width: 140px;
  min-width: 140px;
  @apply shrink-0 text-sm font-medium text-gray-700;
}
.cncl-input {
  @apply flex-1 min-w-0;
}
.cncl-field {
  @apply rounded-lg border border-gray-300 px-3 py-2 text-sm focus:border-teal-500 focus:ring-1 focus:ring-teal-500;
}
.cncl-radio-box {
  @apply flex items-center gap-1.5 px-3 py-2 rounded-lg border border-gray-300 cursor-pointer text-sm;
}
.cncl-radio-box--selected {
  @apply border-teal-500 bg-teal-50 text-teal-700 font-medium;
}
</style>
