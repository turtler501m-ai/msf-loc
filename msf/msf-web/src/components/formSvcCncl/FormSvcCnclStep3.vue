<template>
  <div class="page-step-panel space-y-6">
    <h3 class="text-lg font-semibold">서비스 해지 - 동의 및 확인</h3>

    <!-- S104030101 혜택 소멸사항 동의 (필수) -->
    <section class="p-4 border border-amber-100 rounded-lg bg-amber-50">
      <h4 class="font-medium mb-3">고객 안내사항 <span class="text-red-500">*</span></h4>
      <p class="text-sm text-amber-800 whitespace-pre-line mb-3">{{ benefitNotice }}</p>
      <label class="flex items-start gap-2 cursor-pointer">
        <input v-model="benefitAgreed" type="checkbox" class="rounded mt-1" />
        <span class="text-sm">본인은 위 혜택에 대한 설명을 듣고, 케이티 엠모바일 해지 시 혜택 소멸되는 사항에 대해 동의합니다.</span>
      </label>
    </section>

    <!-- S104030101 신청서 확인 (B1) → S104030103 팝업 -->
    <section class="p-4 border border-gray-200 rounded-lg">
      <h4 class="font-medium mb-3">신청서 확인 <span class="text-red-500">*</span></h4>
      <p class="text-sm text-gray-600 mb-3">신청서 확인을 눌러서, 신청서 작성 내용을 확인하신 후 가입자(대리인) 서명을 등록해 주세요.</p>
      <div class="flex flex-col items-center gap-2">
        <button
          type="button"
          class="cncl-btn-outline"
          @click="openFormConfirm"
        >
          신청서 확인
        </button>
        <span v-if="formConfirmed" class="text-sm text-teal-600 font-medium">✓ 확인 완료</span>
      </div>
    </section>

    <!-- S104030103 신청서 확인 팝업 -->
    <div v-if="showFormConfirmDialog" class="fixed inset-0 z-50 flex items-center justify-center bg-black/40">
      <div class="bg-white rounded-lg shadow-xl w-full max-w-2xl mx-4 flex flex-col" style="max-height: 90vh">
        <div class="px-6 py-4 border-b flex items-center justify-between">
          <h3 class="text-base font-semibold">신청서 확인</h3>
        </div>
        <div class="flex-1 overflow-y-auto p-6">
          <!-- 신청서 솔루션 연동 예정 (포시에스 eFormSign iframe) -->
          <div class="border border-gray-200 rounded p-4 bg-gray-50 min-h-64 text-sm text-gray-500 text-center flex flex-col items-center justify-center gap-2">
            <p>신청서 양식 (포시에스 eFormSign 연동 예정)</p>
            <dl class="text-left text-xs mt-4 grid grid-cols-2 gap-x-6 gap-y-1 text-gray-700">
              <dt>고객명</dt><dd>{{ formStore.customerForm?.name || '-' }}</dd>
              <dt>휴대폰</dt><dd>{{ formStore.customerForm?.phone || '-' }}</dd>
              <dt>고객유형</dt><dd>{{ formStore.customerForm?.custType || '-' }}</dd>
              <dt>사용여부</dt><dd>{{ formStore.productForm?.useType || '-' }}</dd>
            </dl>
          </div>
        </div>
        <div class="px-6 py-4 border-t flex justify-end gap-3">
          <button
            type="button"
            class="px-5 py-2 rounded-lg border border-gray-300 text-sm"
            @click="onFormConfirmEdit"
          >
            수정
          </button>
          <button
            type="button"
            class="cncl-btn-primary"
            @click="onFormConfirmOk"
          >
            확인
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useCancelFormStore } from '@/stores/cancel_form'
import { submitCancelApply } from '@/api/cancel'

const emit = defineEmits(['complete'])
const formStore = useCancelFormStore()

const benefitAgreed = ref(false)
const formConfirmed = ref(false)
const showFormConfirmDialog = ref(false)

const benefitNotice = `1. 고객님께 제공되었던 평생할인 및 각종 결합할인 등이 모두 소멸되어 다양한 혜택을 제공받을 수 없게 됩니다.

2. 해지 시 DB 단체보험, 제주항공, 티머니 등 케이티 엠모바일의 제휴서비스가 제공되지 않습니다.`

function openFormConfirm() {
  showFormConfirmDialog.value = true
}

function onFormConfirmOk() {
  formConfirmed.value = true
  showFormConfirmDialog.value = false
}

function onFormConfirmEdit() {
  showFormConfirmDialog.value = false
}

const isComplete = computed(() => benefitAgreed.value && formConfirmed.value)

watch(isComplete, (v) => emit('complete', v))
onMounted(() => emit('complete', isComplete.value))

// 최종 저장 (MsfStepView가 완료 화면으로 이동)
const save = async () => {
  if (!benefitAgreed.value) { alert('혜택 소멸사항 동의에 체크해 주세요.'); return false }
  if (!formConfirmed.value) { alert('[신청서 확인] 버튼을 클릭하여 신청서를 확인해 주세요.'); return false }
  if (!confirm('신청서를 등록하시겠습니까?')) { return false }

  try {
    const c = formStore.customerForm
    const p = formStore.productForm
    const payload = {
      agencyCode: formStore.agencyCode,
      customerForm: {
        custType: c?.custType,
        name: c?.name,
        phone: c?.phone,
        phoneAuthCompleted: c?.phoneAuthCompleted,
        ncn: c?.ncn,
        custId: c?.custId,
        email: c?.email,
        emailDomain: c?.emailDomain,
        address: c?.address,
        post: c?.post,
        addressDtl: c?.addressDtl,
      },
      productForm: {
        remainCharge: p?.remainCharge ?? undefined,
        penalty: p?.penalty ?? undefined,
        installmentRemain: p?.installmentRemain ?? undefined,
        useType: p?.useType,
        memo: p?.memo,
      },
    }
    const res = await submitCancelApply(payload)
    if (res && res.success && res.applicationNo) {
      formStore.setLastApplicationNo(res.applicationNo)
      formStore.setLastCompletedName(c?.name || '')
      formStore.setLastContactPhone(c?.contactPhone || '')
      formStore.reset()
      return true
    } else {
      alert(res?.message || '신청서 등록이 실패하였습니다. 다시 시도해 주세요.')
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
@reference "tailwindcss";

.page-step-panel {
  @apply w-full p-4 border rounded-md;
}
.cncl-btn-outline {
  @apply px-4 py-2 rounded-lg border border-teal-600 text-teal-600 text-sm font-medium hover:bg-teal-50;
}
.cncl-btn-primary {
  @apply px-4 py-2 rounded-lg bg-teal-600 text-white text-sm font-medium;
}
</style>
