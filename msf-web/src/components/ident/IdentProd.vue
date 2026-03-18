<template>
  <div class="ident-prod page-step-panel space-y-6">
    <h3 class="ident-section-title">명의변경 신청서 – 상품</h3>

    <section class="ident-card">
      <h4 class="ident-subtitle">USIM 정보</h4>
      <div class="ident-form-row">
        <span class="ident-label">SIM 보유 <span class="text-red-500">*</span></span>
        <div class="ident-input">
          <div class="flex flex-wrap gap-4">
            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.usimType" type="radio" value="current" name="identUsimType" class="rounded-full" />
              <span>현재 USIM 사용</span>
            </label>
            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.usimType" type="radio" value="purchase" name="identUsimType" class="rounded-full" />
              <span>USIM 구매</span>
            </label>
            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.usimType" type="radio" value="sim" name="identUsimType" class="rounded-full" />
              <span>SIM 보유</span>
            </label>
          </div>
        </div>
      </div>
      <template v-if="form.usimType === 'current' || form.usimType === 'sim'">
        <div class="ident-form-row">
          <span class="ident-label">USIM 번호 <span class="text-red-500">*</span></span>
          <div class="ident-input flex gap-2">
            <input v-model="form.usimNo" type="text" placeholder="19자리" maxlength="19" class="ident-field flex-1 max-w-xs" />
            <button type="button" class="ident-btn-outline">스캔하기</button>
            <button type="button" class="ident-btn-outline">USIM번호 유효성 체크</button>
          </div>
          <p class="text-xs text-gray-500 mt-1 ml-[140px]">X85 연동 예정</p>
        </div>
      </template>
      <template v-if="form.usimType === 'purchase'">
        <div class="ident-form-row">
          <span class="ident-label">USIM 선택 <span class="text-red-500">*</span></span>
          <div class="ident-input">
            <label class="flex items-center gap-2 cursor-pointer mr-4">
              <input v-model="form.usimOption" type="radio" value="normal" name="identUsimOpt" class="rounded-full" />
              <span>일반 6,600원</span>
            </label>
            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.usimOption" type="radio" value="nfc" name="identUsimOpt" class="rounded-full" />
              <span>NFC 8,800원</span>
            </label>
          </div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">USIM 구매 방식 <span class="text-red-500">*</span></span>
          <div class="ident-input flex gap-4">
            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.usimPayType" type="radio" value="immediate" name="identUsimPay" class="rounded-full" />
              <span>즉시납부</span>
            </label>
            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.usimPayType" type="radio" value="next" name="identUsimPay" class="rounded-full" />
              <span>다음달 요금에 합산</span>
            </label>
          </div>
        </div>
      </template>
    </section>

    <section class="ident-card">
      <h4 class="ident-subtitle">요금제 · 부가서비스</h4>
      <div class="ident-form-row">
        <span class="ident-label">요금제</span>
        <div class="ident-input">
          <input v-model="form.rateNote" type="text" placeholder="고객 단계에서 선택 (API 연동 후 표시)" class="ident-field w-full" readonly />
        </div>
      </div>
      <div class="ident-form-row">
        <span class="ident-label">부가서비스</span>
        <div class="ident-input text-sm text-gray-600">
          <p>카테고리에서 선택 (X20 연동)</p>
          <p v-if="form.additionItems?.length" class="mt-1">조회된 항목 수: {{ form.additionItems.length }}</p>
        </div>
      </div>
    </section>

    <section class="ident-card">
      <h4 class="ident-subtitle">납부 정보</h4>
      <div class="ident-form-row">
        <span class="ident-label">명세서 수신 유형</span>
        <div class="ident-input flex gap-4">
          <label class="flex items-center gap-2 cursor-pointer">
            <input v-model="form.statementType" type="radio" value="mobile" name="identStatement" class="rounded-full" />
            <span>모바일 명세서</span>
          </label>
          <label class="flex items-center gap-2 cursor-pointer">
            <input v-model="form.statementType" type="radio" value="email" name="identStatement" class="rounded-full" />
            <span>이메일 명세서</span>
          </label>
          <label class="flex items-center gap-2 cursor-pointer">
            <input v-model="form.statementType" type="radio" value="post" name="identStatement" class="rounded-full" />
            <span>우편 명세서</span>
          </label>
        </div>
      </div>
      <div class="ident-form-row">
        <span class="ident-label">요금 납부 방법 <span class="text-red-500">*</span></span>
        <div class="ident-input">
          <select v-model="form.payMethod" class="ident-field w-full max-w-xs">
            <option value="">선택</option>
            <option value="AUTO">자동이체</option>
            <option value="CARD">신용카드</option>
            <option value="GIRO">지로</option>
            <option value="UNIFIED">통합청구</option>
          </select>
          <p class="text-xs text-gray-500 mt-1">Y26, X91 연동 예정</p>
        </div>
      </div>
    </section>

    <section class="ident-card">
      <div class="ident-form-row">
        <span class="ident-label">메모</span>
        <div class="ident-input">
          <textarea v-model="form.memo" placeholder="메모 입력" class="ident-field w-full min-h-[80px]" />
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useIdentFormStore } from '@/stores/ident_form'
import { getCurrentAddition } from '@/api/ident'

const emit = defineEmits(['update:complete'])
const formStore = useIdentFormStore()

const form = ref({
  usimType: '',
  usimNo: '',
  usimOption: '',
  usimPayType: '',
  rateNote: '',
  payMethod: '',
  statementType: 'mobile',
  additionItems: [],
  memo: '',
})

const isComplete = computed(() => {
  if (!form.value.usimType) return false
  if (!form.value.payMethod) return false
  if ((form.value.usimType === 'current' || form.value.usimType === 'sim') && !form.value.usimNo?.trim()) return false
  if (form.value.usimType === 'purchase' && (!form.value.usimOption || !form.value.usimPayType)) return false
  return true
})

watch(form, (v) => formStore.setProductForm(v), { deep: true })
watch(isComplete, (v) => emit('update:complete', v))
onMounted(async () => {
  const saved = formStore.productForm
  if (saved && Object.keys(saved).length) form.value = { ...form.value, ...saved }
  const ctn = formStore.customerForm?.phone?.replace?.(/-/g, '') || ''
  if (ctn) {
    try {
      const data = await getCurrentAddition({ ctn })
      if (data && Array.isArray(data.items)) {
        form.value.additionItems = data.items
        formStore.setProductForm({ additionItems: data.items })
      }
    } catch (e) {
      console.warn('부가서비스 조회 실패(연동 후 적용):', e)
    }
  }
  form.value.rateNote = formStore.customerForm?.ratePlanCode ? `요금제 코드: ${formStore.customerForm.ratePlanCode}` : '고객 단계에서 선택'
  emit('update:complete', isComplete.value)
})

const validate = async () => {
  if (!form.value.usimType) return { valid: false, message: 'SIM 보유 유형(현재 사용/구매/SIM 보유)을 선택해 주세요.' }
  if (!form.value.payMethod) return { valid: false, message: '요금 납부 방법을 선택해 주세요.' }
  if ((form.value.usimType === 'current' || form.value.usimType === 'sim') && !form.value.usimNo?.trim()) {
    return { valid: false, message: 'USIM 번호를 입력해 주세요.' }
  }
  if (form.value.usimType === 'purchase') {
    if (!form.value.usimOption) return { valid: false, message: 'USIM 선택(일반/NFC)을 선택해 주세요.' }
    if (!form.value.usimPayType) return { valid: false, message: 'USIM 구매 방식을 선택해 주세요.' }
  }
  formStore.setProductForm(form.value)
  return { valid: true, message: '' }
}

defineExpose({ validate })
</script>

<style scoped>
.ident-section-title {
  font-size: 1.125rem;
  font-weight: 600;
  color: #1f2937;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #d1d5db;
}
.ident-subtitle {
  font-weight: 600;
  color: #374151;
  padding-bottom: 0.375rem;
  margin-bottom: 1rem;
  border-bottom: 1px solid #e5e7eb;
}
.ident-card {
  padding: 1rem;
  border: 1px solid #e5e7eb;
  border-radius: 0.5rem;
  background: #fff;
}
.ident-form-row {
  display: flex;
  align-items: flex-start;
  gap: 1rem;
  margin-bottom: 1rem;
}
.ident-label {
  width: 140px;
  min-width: 140px;
  padding-top: 0.5rem;
  font-size: 0.875rem;
  font-weight: 500;
  color: #374151;
}
.ident-input {
  flex: 1;
  min-width: 0;
}
.ident-field {
  border: 1px solid #d1d5db;
  border-radius: 0.375rem;
  padding: 0.5rem 0.75rem;
  font-size: 0.875rem;
}
.ident-btn-outline {
  padding: 0.5rem 0.75rem;
  border: 1px solid #0d9488;
  color: #0d9488;
  border-radius: 0.375rem;
  font-size: 0.875rem;
  white-space: nowrap;
}
</style>
