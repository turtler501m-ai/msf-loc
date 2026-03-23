<template>
  <div class="page-step-panel space-y-6">
    <h3 class="text-lg font-semibold">서비스 해지 - 상품·요금 정보</h3>
    <p class="text-sm text-gray-600">해지 대상 회선, 사용여부·잔여요금·위약금을 확인해 주세요.</p>

    <!-- S104020101 해지 신청: 사용여부 -->
    <section class="p-4 border border-gray-200 rounded-lg">
      <h4 class="font-medium mb-3">사용여부 <span class="text-red-500">*</span></h4>
      <div class="flex flex-wrap gap-3">
        <label v-for="u in useTypeOptions" :key="u.value" class="flex items-center gap-2 cursor-pointer">
          <input v-model="form.useType" type="radio" :value="u.value" name="cancelUseType" class="rounded-full" />
          <span>{{ u.label }}</span>
        </label>
      </div>
    </section>

    <!-- S104020101 해지 정산 (X18 실시간요금조회) -->
    <section class="p-4 border border-amber-100 rounded-lg bg-amber-50">
      <h4 class="font-medium mb-3">해지 정산</h4>
      <dl class="grid gap-2 text-sm">
        <div class="flex justify-between">
          <dt class="text-gray-600">사용요금 (원)</dt>
          <dd>{{ form.remainCharge != null ? form.remainCharge.toLocaleString() + '원' : '-' }}</dd>
        </div>
        <div class="flex justify-between">
          <dt class="text-gray-600">위약금 (원)</dt>
          <dd>{{ form.penalty != null ? form.penalty.toLocaleString() + '원' : '-' }}</dd>
        </div>
        <div class="flex justify-between font-medium">
          <dt class="text-gray-700">최종 정산요금 (원)</dt>
          <dd>{{ finalSettle != null ? finalSettle.toLocaleString() + '원' : '-' }}</dd>
        </div>
        <div class="flex justify-between">
          <dt class="text-gray-600">잔여분할상환기간/금액</dt>
          <dd>{{ form.installmentRemain != null ? form.installmentRemain.toLocaleString() + '원' : '-' }}</dd>
        </div>
      </dl>
      <p class="text-xs text-gray-500 mt-3">※ 해지 시까지 사용한 사용료, 위약금, 잔여 단말기 대금 등의 자세한 사용 요금은 다음달 청구서에서 확인 가능합니다.</p>
    </section>

    <section class="p-4 border border-gray-200 rounded-lg">
      <h4 class="font-medium mb-3">메모</h4>
      <textarea v-model="form.memo" placeholder="메모 입력" rows="3" class="w-full rounded border border-gray-300 px-3 py-2" />
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
  installmentRemain: null,
  memo: '',
})

const finalSettle = computed(() => {
  const r = form.value.remainCharge
  const p = form.value.penalty
  const i = form.value.installmentRemain
  if (r == null && p == null) return null
  return num(r) + num(p) + num(i)
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
</style>
