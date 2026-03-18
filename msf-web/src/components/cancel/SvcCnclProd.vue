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

    <section class="p-4 border border-gray-200 rounded-lg">
      <h4 class="font-medium mb-3">해지 대상 회선</h4>
      <p class="text-sm text-gray-500 mb-2">X01/X22 등 연동 후 회선 목록 표시</p>
      <input v-model="form.lineNote" type="text" placeholder="회선 정보 (API 연동 후)" class="w-full rounded border border-gray-300 px-3 py-2" readonly />
    </section>

    <!-- S104020101 해지 정산: 사용요금·위약금·최종 정산요금 -->
    <section class="p-4 border border-amber-100 rounded-lg bg-amber-50 border-amber-200">
      <h4 class="font-medium mb-3">잔여요금·위약금·분납금 (X22, X67)</h4>
      <p class="text-sm text-gray-600 mb-3">해지 시 납부해야 할 금액 안내</p>
      <dl class="grid gap-2 text-sm">
        <div class="flex justify-between">
          <dt class="text-gray-600">사용요금 (원)</dt>
          <dd>{{ form.remainCharge ?? '-' }}원</dd>
        </div>
        <div class="flex justify-between">
          <dt class="text-gray-600">위약금 (원)</dt>
          <dd>{{ form.penalty ?? '-' }}원</dd>
        </div>
        <div class="flex justify-between">
          <dt class="text-gray-600">최종 정산요금 (원)</dt>
          <dd>{{ (form.finalSettle ?? (num(form.remainCharge) + num(form.penalty))) || '-' }}원</dd>
        </div>
        <div class="flex justify-between">
          <dt class="text-gray-600">단말기 분납 잔액</dt>
          <dd>{{ form.installmentRemain ?? '-' }}원</dd>
        </div>
      </dl>
      <p class="text-xs text-gray-500 mt-2">실제 금액은 API(X22, X67) 연동 후 표시됩니다.</p>
    </section>

    <section class="p-4 border border-gray-200 rounded-lg">
      <h4 class="font-medium mb-3">해지 사유 (선택)</h4>
      <select v-model="form.cancelReason" class="w-full rounded border border-gray-300 px-3 py-2">
        <option value="">선택</option>
        <option value="MOVE">이동</option>
        <option value="DUPLICATE">중복 가입 해지</option>
        <option value="ETC">기타</option>
      </select>
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

const emit = defineEmits(['update:complete'])
const formStore = useCancelFormStore()

const useTypeOptions = [
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
  lineNote: '',
  useType: '',
  remainCharge: null,
  penalty: null,
  finalSettle: null,
  installmentRemain: null,
  cancelReason: '',
  memo: '',
})

const isComplete = computed(() => !!form.value.useType)

watch(form, (v) => formStore.setProductForm(v), { deep: true })
watch(isComplete, (v) => emit('update:complete', v))
onMounted(async () => {
  const saved = formStore.productForm
  if (saved && Object.keys(saved).length) form.value = { ...form.value, ...saved }
  const c = formStore.customerForm
  const ctn = c?.phone?.replace?.(/-/g, '') || ''
  if (ctn) {
    try {
      const data = await getRemainCharge({
        name: c?.name,
        ctn,
        ncn: c?.ncn,
        custId: c?.custId,
      })
      if (data && (data.remainCharge != null || data.penalty != null || data.installmentRemain != null)) {
        form.value.remainCharge = data.remainCharge ?? form.value.remainCharge
        form.value.penalty = data.penalty ?? form.value.penalty
        form.value.installmentRemain = data.installmentRemain ?? form.value.installmentRemain
        if (form.value.remainCharge != null || form.value.penalty != null) {
          form.value.finalSettle = num(form.value.remainCharge) + num(form.value.penalty) + num(form.value.installmentRemain)
        }
      }
    } catch (e) {
      console.warn('잔여 요금 조회 실패:', e)
    }
  }
  emit('update:complete', isComplete.value)
})

const validate = async () => {
  if (!form.value.useType) {
    return { valid: false, message: '사용여부를 선택해 주세요.' }
  }
  formStore.setProductForm(form.value)
  return { valid: true, message: '' }
}

defineExpose({ validate })
</script>

<style scoped></style>
