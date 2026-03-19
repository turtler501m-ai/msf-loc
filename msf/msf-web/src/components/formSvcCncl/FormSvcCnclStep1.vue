<template>
  <div class="page-step-panel space-y-6">
    <h3 class="text-lg font-semibold">서비스 해지 - 고객 정보</h3>

    <div>
      <label class="block text-sm font-medium mb-2">고객유형</label>
      <div class="flex flex-wrap gap-3">
        <label v-for="t in custTypes" :key="t.value" class="flex items-center gap-2 cursor-pointer">
          <input v-model="form.custType" type="radio" :value="t.value" name="cancelCustType" class="rounded-full" />
          <span>{{ t.label }}</span>
        </label>
      </div>
    </div>

    <!-- 방문고객: 법인/공공기관만 선택 노출 (설계서 S104010101) -->
    <div v-if="showVisitType">
      <label class="block text-sm font-medium mb-2">방문고객</label>
      <div class="flex gap-3">
        <label class="flex items-center gap-2 cursor-pointer">
          <input v-model="form.visitType" type="radio" value="self" name="cancelVisitType" class="rounded-full" />
          <span>본인</span>
        </label>
        <label class="flex items-center gap-2 cursor-pointer">
          <input v-model="form.visitType" type="radio" value="agent" name="cancelVisitType" class="rounded-full" />
          <span>대리인</span>
        </label>
      </div>
    </div>

    <div class="grid gap-4 sm:grid-cols-2">
      <div>
        <label class="block text-sm font-medium mb-1">이름 <span class="text-red-500">*</span></label>
        <input v-model="form.name" type="text" placeholder="이름 입력" class="w-full rounded border border-gray-300 px-3 py-2" :readonly="form.phoneAuthCompleted" />
      </div>
      <template v-if="isCorporate">
        <div>
          <label class="block text-sm font-medium mb-1">법인등록번호 <span class="text-red-500">*</span></label>
          <input v-model="form.corporateNo" type="text" placeholder="법인등록번호" class="w-full rounded border border-gray-300 px-3 py-2" :readonly="form.phoneAuthCompleted" />
        </div>
      </template>
      <template v-else>
        <div>
          <label class="block text-sm font-medium mb-1">생년월일 <span class="text-red-500">*</span></label>
          <input v-model="form.birthDate" type="text" placeholder="YYYYMMDD" maxlength="8" class="w-full rounded border border-gray-300 px-3 py-2" :readonly="form.phoneAuthCompleted" />
        </div>
      </template>
    </div>

    <div>
      <label class="block text-sm font-medium mb-1">해지 휴대폰번호 <span class="text-red-500">*</span></label>
      <div class="flex gap-2">
        <input v-model="form.phone" type="tel" placeholder="010-1234-5678" maxlength="13" class="flex-1 rounded border border-gray-300 px-3 py-2" :readonly="form.phoneAuthCompleted" />
        <button
          type="button"
          :disabled="!canAuth"
          class="px-4 py-2 rounded bg-teal-600 text-white disabled:opacity-50"
          @click="doPhoneAuth"
        >
          {{ form.phoneAuthCompleted ? '인증 완료' : '인증' }}
        </button>
      </div>
    </div>

    <div class="grid gap-4 sm:grid-cols-2">
      <div>
        <label class="block text-sm font-medium mb-1">이메일</label>
        <div class="flex gap-1">
          <input v-model="form.email" type="text" placeholder="아이디" class="flex-1 rounded border border-gray-300 px-3 py-2" />
          <span class="py-2">@</span>
          <input v-model="form.emailDomain" type="text" placeholder="도메인" class="flex-1 rounded border border-gray-300 px-3 py-2" />
        </div>
      </div>
      <div>
        <label class="block text-sm font-medium mb-1">우편번호</label>
        <div class="flex gap-2">
          <input v-model="form.post" type="text" placeholder="우편번호" class="flex-1 rounded border border-gray-300 px-3 py-2" />
          <button type="button" class="px-3 py-2 rounded border border-teal-600 text-teal-600 text-sm" @click="postcodeOpen = true">우편번호 찾기</button>
        </div>
        <McpPostcodePop v-model:open="postcodeOpen" @select="onPostcodeSelect" />
      </div>
      <div class="sm:col-span-2">
        <label class="block text-sm font-medium mb-1">주소</label>
        <input v-model="form.address" type="text" placeholder="주소" class="w-full rounded border border-gray-300 px-3 py-2 mb-1" />
        <input v-model="form.addressDtl" type="text" placeholder="상세주소" class="w-full rounded border border-gray-300 px-3 py-2" />
      </div>
    </div>

    <div v-if="showAgentInfo" class="p-4 border border-gray-200 rounded-lg bg-gray-50">
      <h4 class="font-medium mb-3">대리인 위임 정보</h4>
      <div class="grid gap-4 sm:grid-cols-2">
        <div>
          <label class="block text-sm font-medium mb-1">위임받은 고객 이름</label>
          <input v-model="form.agentInfo.custName" type="text" placeholder="이름" class="w-full rounded border border-gray-300 px-3 py-2" />
        </div>
        <div>
          <label class="block text-sm font-medium mb-1">생년월일</label>
          <input v-model="form.agentInfo.birthDate" type="text" placeholder="YYYYMMDD" maxlength="8" class="w-full rounded border border-gray-300 px-3 py-2" />
        </div>
        <div>
          <label class="block text-sm font-medium mb-1">연락처</label>
          <input v-model="form.agentInfo.phone" type="tel" placeholder="휴대폰번호" class="w-full rounded border border-gray-300 px-3 py-2" />
        </div>
        <div>
          <label class="block text-sm font-medium mb-1">신청인과의 관계</label>
          <input v-model="form.agentInfo.relation" type="text" placeholder="선택" class="w-full rounded border border-gray-300 px-3 py-2" />
        </div>
      </div>
    </div>

    <div v-if="showMinorAgent" class="p-4 border border-amber-200 rounded-lg bg-amber-50">
      <h4 class="font-medium mb-3">법정대리인 정보</h4>
      <div class="grid gap-4 sm:grid-cols-2">
        <div>
          <label class="block text-sm font-medium mb-1">법정대리인 이름</label>
          <input v-model="form.minorAgent.name" type="text" class="w-full rounded border border-gray-300 px-3 py-2" />
        </div>
        <div>
          <label class="block text-sm font-medium mb-1">관계</label>
          <input v-model="form.minorAgent.relation" type="text" placeholder="예: 부, 모" class="w-full rounded border border-gray-300 px-3 py-2" />
        </div>
        <div>
          <label class="block text-sm font-medium mb-1">휴대폰</label>
          <input v-model="form.minorAgent.phone" type="tel" class="w-full rounded border border-gray-300 px-3 py-2" />
        </div>
        <div class="sm:col-span-2">
          <label class="flex items-center gap-2 cursor-pointer">
            <input v-model="form.minorAgent.agree" type="checkbox" class="rounded" />
            <span>안내사항 확인 및 동의</span>
          </label>
        </div>
      </div>
    </div>

    <!-- 대리점 선택 -->
    <section class="mt-6 pt-6 border-t border-gray-200">
      <h4 class="text-base font-semibold mb-3">대리점 선택 <span class="text-red-500">*</span></h4>
      <select v-model="form.agencyCode" class="w-full max-w-xs rounded border border-gray-300 px-3 py-2">
        <option value="">대리점 선택</option>
        <option v-for="a in agencyOptions" :key="a.value" :value="a.value">{{ a.label }}</option>
      </select>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useCancelFormStore } from '@/stores/cancel_form'
import { getJoinInfo } from '@/api/cancel'
import McpPostcodePop from '@/components/commons/McpPostcodePop.vue'

const emit = defineEmits(['complete'])
const formStore = useCancelFormStore()
const postcodeOpen = ref(false)

function onPostcodeSelect({ post, address }) {
  form.value.post = post
  form.value.address = address
}

const custTypes = [
  { value: 'NA', label: '내국인' },
  { value: 'NM', label: '미성년자' },
  { value: 'FN', label: '외국인' },
  { value: 'FNM', label: '외국인 미성년자' },
  { value: 'JP', label: '법인' },
  { value: 'PP', label: '공공기관' },
]

const agencyOptions = ref([
  { value: 'AG001', label: '대리점 A' },
  { value: 'AG002', label: '대리점 B' },
  { value: 'AG003', label: '대리점 C' },
])

const form = ref({
  agencyCode: '',
  custType: '',
  visitType: 'self',
  name: '',
  birthDate: '',
  corporateNo: '',
  phoneAuthCompleted: false,
  phone: '',
  email: '',
  emailDomain: '',
  post: '',
  address: '',
  addressDtl: '',
  minorAgent: { name: '', relation: '', phone: '', agree: false },
  agentInfo: { custName: '', birthDate: '', phone: '', relation: '' },
})

const isCorporate = computed(() => ['JP', 'PP'].includes(form.value.custType))
const showVisitType = computed(() => isCorporate.value)
const showMinorAgent = computed(() => ['NM', 'FNM'].includes(form.value.custType))
const showAgentInfo = computed(() => form.value.visitType === 'agent' && isCorporate.value)

const canAuth = computed(() => {
  if (!form.value.name || !form.value.phone) return false
  if (isCorporate.value) return !!form.value.corporateNo
  return !!form.value.birthDate && form.value.birthDate.length === 8
})

const isComplete = computed(() => {
  if (!form.value.agencyCode) return false
  if (!form.value.phoneAuthCompleted) return false
  if (!form.value.name || !form.value.email || !form.value.emailDomain) return false
  if (!form.value.post || !form.value.address) return false
  if (showMinorAgent.value) {
    const m = form.value.minorAgent
    if (!m.name || !m.relation || !m.phone || !m.agree) return false
  }
  if (showAgentInfo.value) {
    const a = form.value.agentInfo
    if (!a.custName || !a.birthDate || !a.phone || !a.relation) return false
  }
  return true
})

async function doPhoneAuth() {
  if (form.value.phoneAuthCompleted) return
  try {
    const data = await getJoinInfo({
      name: (form.value.name || '').trim(),
      ctn: (form.value.phone || '').replace(/-/g, ''),
    })
    if (data && data.success !== false) {
      form.value.phoneAuthCompleted = true
      if (data.addr) form.value.address = data.addr
      if (data.email) {
        const parts = data.email.split('@')
        form.value.email = parts[0] || ''
        form.value.emailDomain = parts[1] || ''
      }
      if (data.ncn) form.value.ncn = data.ncn
      if (data.custId) form.value.custId = data.custId
      formStore.setCustomerForm(form.value)
      alert('휴대폰번호 인증이 완료되었습니다.')
    } else {
      alert(data?.message || '고객 정보와 휴대폰번호가 일치하지 않습니다.')
    }
  } catch (err) {
    alert(err?.message || '고객 정보와 휴대폰번호가 일치하지 않습니다.')
  }
}

watch(form, (v) => {
  formStore.setCustomerForm(v)
  formStore.setAgencyCode(v.agencyCode)
  emit('complete', isComplete.value)
}, { deep: true })

onMounted(() => {
  form.value.agencyCode = formStore.agencyCode || ''
  const saved = formStore.customerForm
  if (saved && Object.keys(saved).length) {
    form.value = {
      ...form.value,
      ...saved,
      agencyCode: form.value.agencyCode || saved.agencyCode || formStore.agencyCode || '',
      minorAgent: { ...form.value.minorAgent, ...(saved.minorAgent || {}) },
      agentInfo: { ...form.value.agentInfo, ...(saved.agentInfo || {}) },
    }
  }
  emit('complete', isComplete.value)
})

// 임시저장 코드로부터 데이터 복원
const data = async (/* code */) => {
  // 임시저장 연동 예정 - 현재 고객 단계(0) 반환
  return '0'
}

// 다음 단계로 넘어가기 전 저장
const save = async () => {
  if (!form.value.agencyCode) { alert('대리점을 선택해 주세요.'); return false }
  if (!form.value.phoneAuthCompleted) { alert('휴대폰 번호 인증을 완료해 주세요.'); return false }
  if (!form.value.name) { alert('이름을 입력해 주세요.'); return false }
  if (!form.value.email || !form.value.emailDomain) { alert('이메일을 입력해 주세요.'); return false }
  if (!form.value.post || !form.value.address) { alert('우편번호와 주소를 입력해 주세요.'); return false }
  if (showMinorAgent.value) {
    const m = form.value.minorAgent
    if (!m.name || !m.relation || !m.phone || !m.agree) { alert('법정대리인 정보를 모두 입력하고 동의해 주세요.'); return false }
  }
  if (showAgentInfo.value) {
    const a = form.value.agentInfo
    if (!a.custName || !a.birthDate || !a.phone || !a.relation) { alert('대리인 위임 정보를 모두 입력해 주세요.'); return false }
  }
  formStore.setCustomerForm(form.value)
  formStore.setAgencyCode(form.value.agencyCode)
  return true
}

defineExpose({ data, save })
</script>

<style scoped>
@reference "tailwindcss";

.page-step-panel {
  @apply w-full p-4 border rounded-md;
}
</style>
