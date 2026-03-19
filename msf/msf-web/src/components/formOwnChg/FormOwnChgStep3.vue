<template>
  <div class="ident-agree page-step-panel space-y-6">
    <h3 class="ident-section-title">명의변경 신청서 – 동의 및 확인</h3>

    <section class="ident-card">
      <h4 class="ident-subtitle">신청 정보 확인</h4>
      <div class="ident-form-row">
        <span class="ident-label">고객명</span>
        <span class="ident-input">{{ formStore.customerForm?.name || '-' }}</span>
      </div>
      <div class="ident-form-row">
        <span class="ident-label">휴대폰</span>
        <span class="ident-input">{{ formStore.customerForm?.phone || '-' }}</span>
      </div>
      <div class="ident-form-row">
        <span class="ident-label">이메일</span>
        <span class="ident-input">{{ emailDisplay }}</span>
      </div>
    </section>

    <!-- 고객 안내 사항 -->
    <section class="ident-card">
      <h4 class="ident-subtitle">고객 안내 사항</h4>
      <p class="text-sm text-gray-600 mb-3">아래 항목을 확인하고 모두 동의해 주세요.</p>
      <div class="space-y-2">
        <label class="ident-form-row flex items-center gap-2 cursor-pointer">
          <input v-model="guideChecks.recordRefuse" type="checkbox" class="rounded" />
          <span>개통정보 녹음 거부 시 개통이 제한될 수 있음을 안내받았습니다. <span class="text-red-500">*</span></span>
        </label>
        <label class="ident-form-row flex items-center gap-2 cursor-pointer">
          <input v-model="guideChecks.nameLending" type="checkbox" class="rounded" />
          <span>명의대여·양도는 불법이며 처벌 대상임을 안내받았습니다. <span class="text-red-500">*</span></span>
        </label>
        <label class="ident-form-row flex items-center gap-2 cursor-pointer">
          <input v-model="guideChecks.fraudPrevent" type="checkbox" class="rounded" />
          <span>통신범죄 예방 안내를 받았습니다. <span class="text-red-500">*</span></span>
        </label>
        <label class="ident-form-row flex items-center gap-2 cursor-pointer">
          <input v-model="guideChecks.mSafer" type="checkbox" class="rounded" />
          <span>M-Safer(통신사기 예방) 서비스 안내를 받았습니다. <span class="text-red-500">*</span></span>
        </label>
        <label class="ident-form-row flex items-center gap-2 cursor-pointer">
          <input v-model="guideChecks.sellerConfirm" type="checkbox" class="rounded" />
          <span>판매자 확인 사항을 확인하였습니다. <span class="text-red-500">*</span></span>
        </label>
      </div>
    </section>

    <!-- 신청서 확인 -->
    <section class="ident-card">
      <h4 class="ident-subtitle">신청서 확인</h4>
      <div class="ident-form-row">
        <span class="ident-label"></span>
        <div class="ident-input">
          <button type="button" class="ident-btn-primary" @click="openConfirmPopup = true">신청서 확인</button>
          <p class="text-xs text-gray-500 mt-2">녹취·가입정보 확인·서명은 신청서 솔루션(S103030103) 연동 후 적용됩니다.</p>
        </div>
      </div>
    </section>

    <!-- 가입자(대리인) 서명 -->
    <section class="ident-card">
      <h4 class="ident-subtitle">가입자(대리인) 서명</h4>
      <div class="ident-form-row">
        <span class="ident-label"></span>
        <div class="ident-input">
          <label class="flex items-center gap-2 cursor-pointer">
            <input v-model="signAgreed" type="checkbox" class="rounded" />
            <span>가입자(대리인) 서명을 완료하였습니다. (개발용: eFormSign 연동 후 서명 화면 적용) <span class="text-red-500">*</span></span>
          </label>
        </div>
      </div>
    </section>

    <!-- 신청정보 확인 팝업 -->
    <Teleport to="body">
      <div v-if="openConfirmPopup" class="fixed inset-0 z-50 flex items-center justify-center bg-black/50" @click.self="openConfirmPopup = false">
        <div class="ident-popup bg-white rounded-lg shadow-xl max-w-lg w-full mx-4 max-h-[90vh] overflow-auto">
          <div class="p-4 border-b border-gray-200">
            <h4 class="font-semibold text-lg">신청정보 확인</h4>
          </div>
          <div class="p-4 space-y-2 text-sm">
            <p><strong>양도고객:</strong> {{ formStore.customerForm?.name }} / {{ formStore.customerForm?.phone }}</p>
            <p><strong>양수고객:</strong> {{ formStore.customerForm?.transferee?.name }} / {{ formStore.customerForm?.transferee?.contactPhone }}</p>
            <p><strong>요금제:</strong> {{ formStore.customerForm?.ratePlanCode || '-' }}</p>
            <p><strong>납부방법:</strong> {{ payMethodLabel }}</p>
            <p class="text-gray-500 pt-2">※ 녹취·서명·신청서 미리보기는 솔루션 연동 후 제공됩니다.</p>
          </div>
          <div class="p-4 border-t border-gray-200 flex justify-end gap-2">
            <button type="button" class="ident-btn-outline" @click="openConfirmPopup = false">수정</button>
            <button type="button" class="ident-btn-primary" @click="openConfirmPopup = false">확인</button>
          </div>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useIdentFormStore } from '@/stores/ident_form'
import { submitIdentApply } from '@/api/ident'

defineProps({ isActive: Boolean })
const emit = defineEmits(['complete'])

const router = useRouter()
const formStore = useIdentFormStore()

const signAgreed = ref(false)
const openConfirmPopup = ref(false)
const guideChecks = ref({
  recordRefuse: false,
  nameLending: false,
  fraudPrevent: false,
  mSafer: false,
  sellerConfirm: false,
})

const emailDisplay = computed(() => {
  const c = formStore.customerForm
  if (!c?.email || !c?.emailDomain) return '-'
  return `${c.email}@${c.emailDomain}`
})
const payMethodLabel = computed(() => {
  const v = formStore.productForm?.payMethod
  const map = { AUTO: '자동이체', CARD: '신용카드', GIRO: '지로', UNIFIED: '통합청구' }
  return v ? (map[v] || v) : '-'
})

const isComplete = computed(() => {
  if (!signAgreed.value) return false
  const g = guideChecks.value
  return g.recordRefuse && g.nameLending && g.fraudPrevent && g.mSafer && g.sellerConfirm
})

watch(isComplete, (v) => emit('complete', v))
onMounted(() => emit('complete', isComplete.value))

const save = async () => {
  const g = guideChecks.value
  if (!g.recordRefuse || !g.nameLending || !g.fraudPrevent || !g.mSafer || !g.sellerConfirm) {
    alert('고객 안내 사항을 모두 확인하고 동의해 주세요.')
    return false
  }
  if (!signAgreed.value) {
    alert('서명을 완료해 주세요.')
    return false
  }
  try {
    const payload = {
      agencyCode: formStore.agencyCode,
      customerForm: { ...formStore.customerForm },
      productForm: { ...formStore.productForm },
    }
    const data = await submitIdentApply(payload)
    if (data && data.success) {
      formStore.setLastCompletedName(formStore.customerForm?.name || '')
      if (data.applicationNo) formStore.setLastApplicationNo(data.applicationNo)
      formStore.reset()
      await router.push({ name: 'service-complete', params: { domain: 'ident' } })
      return true
    } else {
      alert(data?.message || '신청서 등록이 실패하였습니다. 다시 시도해 주세요.')
      return false
    }
  } catch (e) {
    alert(e?.message || '신청서 등록이 실패하였습니다. 다시 시도해 주세요.')
    return false
  }
}

defineExpose({ save })
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
  margin-bottom: 0.75rem;
}
.ident-label {
  width: 140px;
  min-width: 140px;
  font-size: 0.875rem;
  font-weight: 500;
  color: #374151;
}
.ident-input {
  flex: 1;
  min-width: 0;
}
.ident-btn-primary {
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  background: #0d9488;
  color: #fff;
  font-size: 0.875rem;
}
.ident-btn-outline {
  padding: 0.5rem 0.75rem;
  border: 1px solid #0d9488;
  color: #0d9488;
  border-radius: 0.375rem;
  font-size: 0.875rem;
}
.ident-popup {
  min-width: 320px;
}
</style>
