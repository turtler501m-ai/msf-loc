<template>
  <div class="page-step-panel space-y-6">
    <h3 class="text-lg font-semibold">서비스 해지 - 동의 및 확인</h3>

    <section class="p-4 border border-gray-200 rounded-lg bg-gray-50">
      <h4 class="font-medium mb-3">신청 정보 확인</h4>
      <dl class="grid gap-2 text-sm sm:grid-cols-2">
        <dt class="text-gray-600">고객명</dt>
        <dd>{{ formStore.customerForm?.name || '-' }}</dd>
        <dt class="text-gray-600">휴대폰</dt>
        <dd>{{ formStore.customerForm?.phone || '-' }}</dd>
        <dt class="text-gray-600">이메일</dt>
        <dd>{{ emailDisplay }}</dd>
      </dl>
    </section>

    <!-- S104030101 혜택 소멸사항 동의 (필수) -->
    <section class="p-4 border border-amber-100 rounded-lg bg-amber-50">
      <h4 class="font-medium mb-3">혜택 소멸사항 동의 <span class="text-red-500">*</span></h4>
      <p class="text-sm text-amber-800 mb-3">
        (1) 평생할인·각종 결합할인 등 소멸, 혜택 제공받을 수 없음. (2) 해지 시 DB 단체보험, 제주항공, 티머니 등 케이티 엠모바일 제휴서비스 미제공.
      </p>
      <label class="flex items-start gap-2 cursor-pointer">
        <input v-model="benefitAgreed" type="checkbox" class="rounded mt-1" />
        <span class="text-sm">본인은 위 혜택에 대한 설명을 듣고, 케이티 엠모바일 해지 시 혜택 소멸되는 사항에 대해 동의합니다.</span>
      </label>
    </section>

    <section class="p-4 border border-gray-200 rounded-lg bg-gray-50">
      <p class="text-sm text-gray-700">해지 신청 후에는 복구가 어렵습니다. 잔여요금·위약금 납부 후 해지가 완료됩니다.</p>
    </section>

    <!-- 서명 (UI 설계서 S104030101: eFormSign 연동 예정) -->
    <section class="p-4 border border-gray-200 rounded-lg">
      <h4 class="font-medium mb-3">가입자(대리인) 서명</h4>
      <p class="text-xs text-gray-400 mb-2">(개발용: 포시에스 eFormSign 연동 후 서명 화면 적용)</p>
      <label class="flex items-center gap-2 cursor-pointer">
        <input v-model="signAgreed" type="checkbox" class="rounded" />
        <span>서명 완료 확인 (개발용)</span>
      </label>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useCancelFormStore } from '@/stores/cancel_form'
import { submitCancelApply } from '@/api/cancel'

const emit = defineEmits(['complete'])
const router = useRouter()
const formStore = useCancelFormStore()

const signAgreed = ref(false)
const benefitAgreed = ref(false)

const emailDisplay = computed(() => {
  const c = formStore.customerForm
  if (!c?.email || !c?.emailDomain) return '-'
  return `${c.email}@${c.emailDomain}`
})

const isComplete = computed(() => signAgreed.value && benefitAgreed.value)

watch(isComplete, (v) => emit('complete', v))
onMounted(() => emit('complete', isComplete.value))

// 최종 저장 및 신청서 등록
const save = async () => {
  if (!benefitAgreed.value) { alert('혜택 소멸사항 동의에 체크해 주세요.'); return false }
  if (!signAgreed.value) { alert('서명을 완료해 주세요.'); return false }

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
        cancelReason: p?.cancelReason,
      },
    }
    const res = await submitCancelApply(payload)
    if (res && res.success && res.applicationNo) {
      formStore.setLastApplicationNo(res.applicationNo)
      formStore.setLastCompletedName(c?.name || '')
      formStore.reset()
      await router.push({ name: 'service-complete', params: { domain: 'cancel' } })
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
</style>
