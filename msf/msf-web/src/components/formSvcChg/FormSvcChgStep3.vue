<template>
  <div class="page-step-panel space-y-6">
    <h3 class="text-lg font-semibold">서비스 변경 - 동의 및 확인</h3>

    <!-- 신청정보 요약 -->
    <section class="p-4 border border-gray-200 rounded-lg bg-gray-50">
      <div class="flex items-center justify-between mb-3">
        <h4 class="font-medium">신청 정보</h4>
        <button
          type="button"
          class="text-sm px-3 py-1.5 rounded border border-teal-600 text-teal-600 hover:bg-teal-50"
          @click="confirmModalOpen = true"
        >
          신청서 확인
        </button>
      </div>
      <dl class="grid gap-2 text-sm sm:grid-cols-2">
        <dt class="text-gray-600">변경 항목</dt>
        <dd>{{ selectedLabels }}</dd>
        <dt class="text-gray-600">고객명</dt>
        <dd>{{ formStore.customerForm?.name || '-' }}</dd>
        <dt class="text-gray-600">휴대폰</dt>
        <dd>{{ formStore.customerForm?.phone || '-' }}</dd>
        <dt class="text-gray-600">이메일</dt>
        <dd>{{ emailDisplay }}</dd>
      </dl>
    </section>

    <!-- 서명 -->
    <section class="p-4 border border-gray-200 rounded-lg">
      <h4 class="font-medium mb-3">가입자(대리인) 서명</h4>
      <p class="text-sm text-gray-500 mb-3">
        가입자(대리인) 서명을 눌러서, 서명을 등록해 주세요.
      </p>
      <template v-if="!signAgreed">
        <button
          type="button"
          class="px-4 py-2 rounded-lg border-2 border-dashed border-gray-300 text-gray-600 hover:border-teal-500 hover:text-teal-600"
          @click="signAgreed = true"
        >
          가입자(대리인) 서명
        </button>
        <p class="text-xs text-gray-400 mt-2">(개발용: eFormSign 연동 후 서명 팝업 적용)</p>
      </template>
      <template v-else>
        <div class="p-3 bg-teal-50 rounded-lg border border-teal-200">
          <p class="text-sm font-medium text-teal-800">서명 등록 완료</p>
          <p class="text-sm text-gray-700 mt-1">성명: {{ formStore.customerForm?.name || '-' }}</p>
          <button type="button" class="text-xs text-gray-500 underline mt-1" @click="signAgreed = false">
            다시 서명하기
          </button>
        </div>
      </template>
    </section>

    <!-- 신청정보 확인 팝업 -->
    <Teleport to="body">
      <div
        v-if="confirmModalOpen"
        class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/40"
        @click.self="confirmModalOpen = false"
      >
        <div class="bg-white rounded-xl shadow-lg max-w-md w-full p-5 max-h-[90vh] overflow-y-auto">
          <h4 class="font-semibold mb-4">신청정보 확인</h4>
          <dl class="grid gap-2 text-sm mb-6">
            <dt class="text-gray-600">변경 항목</dt>
            <dd>{{ selectedLabels }}</dd>
            <dt class="text-gray-600">고객명</dt>
            <dd>{{ formStore.customerForm?.name || '-' }}</dd>
            <dt class="text-gray-600">휴대폰</dt>
            <dd>{{ formStore.customerForm?.phone || '-' }}</dd>
            <dt class="text-gray-600">이메일</dt>
            <dd>{{ emailDisplay }}</dd>
            <dt class="text-gray-600">주소</dt>
            <dd>{{ addressDisplay }}</dd>
          </dl>
          <div class="flex gap-2 justify-end">
            <button type="button" class="px-4 py-2 rounded border border-gray-300" @click="confirmModalOpen = false">수정</button>
            <button type="button" class="px-4 py-2 rounded bg-teal-600 text-white" @click="confirmModalOpen = false">확인</button>
          </div>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useServiceChangeFormStore } from '@/stores/service_change_form'
import { ServiceChangeOptions } from '@/constants/serviceChange'

defineProps({ isActive: Boolean })
const emit = defineEmits(['complete'])

const router = useRouter()
const formStore = useServiceChangeFormStore()

const signAgreed = ref(false)
const confirmModalOpen = ref(false)

const selectedLabels = computed(() => {
  const ids = formStore.selectedOptions || []
  return ids.map((id) => ServiceChangeOptions.find((o) => o.id === id)?.label || id).join(', ') || '-'
})
const emailDisplay = computed(() => {
  const c = formStore.customerForm
  if (!c?.email || !c?.emailDomain) return '-'
  return `${c.email}@${c.emailDomain}`
})
const addressDisplay = computed(() => {
  const c = formStore.customerForm
  if (!c?.post && !c?.address) return '-'
  return [c?.post, c?.address, c?.addressDtl].filter(Boolean).join(' ') || '-'
})

const isComplete = computed(() => signAgreed.value)

watch(isComplete, (v) => emit('complete', v))
onMounted(() => emit('complete', isComplete.value))

const save = async () => {
  if (!signAgreed.value) {
    alert('서명을 완료해 주세요.')
    return false
  }
  try {
    // TODO: 신청서 등록 API, 리포트 이미징, M플랫폼 전송
    console.log('서비스변경 신청서 저장:', {
      selectedOptions: formStore.selectedOptions,
      customerForm: formStore.customerForm,
      productForm: formStore.productForm,
    })
    formStore.setLastCompletedName(formStore.customerForm?.name || '')
    formStore.reset()
    await router.push({ name: 'service-complete', params: { domain: 'change' } })
    return true
  } catch (e) {
    alert('신청서 등록이 실패하였습니다. 다시 시도해 주세요.')
    return false
  }
}

defineExpose({ save })
</script>
