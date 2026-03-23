<template>
  <div class="ident-type-cust page-step-panel space-y-6">
    <h3 class="ident-section-title">명의변경 – 가입유형 · 고객</h3>

    <!-- 양도고객(현재 고객) -->
    <section class="ident-card">
      <h4 class="ident-subtitle">현재 고객(양도고객)</h4>

      <div class="ident-form-row">
        <span class="ident-label">고객유형 <span class="text-red-500">*</span></span>
        <div class="ident-input">
          <div class="flex flex-wrap gap-3">
            <label v-for="t in custTypes" :key="t.value" class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.custType" type="radio" :value="t.value" name="identCustType" class="rounded-full" />
              <span>{{ t.label }}</span>
            </label>
          </div>
        </div>
      </div>

      <div v-if="showVisitType" class="ident-form-row">
        <span class="ident-label">방문고객</span>
        <div class="ident-input flex gap-3">
          <label class="flex items-center gap-2 cursor-pointer">
            <input v-model="form.visitType" type="radio" value="self" name="identVisitType" class="rounded-full" />
            <span>본인</span>
          </label>
          <label class="flex items-center gap-2 cursor-pointer">
            <input v-model="form.visitType" type="radio" value="agent" name="identVisitType" class="rounded-full" />
            <span>대리인</span>
          </label>
        </div>
      </div>

      <div class="ident-form-row">
        <span class="ident-label">이름 <span class="text-red-500">*</span></span>
        <div class="ident-input">
          <input v-model="form.name" type="text" placeholder="이름 입력" class="ident-field w-full" :readonly="form.phoneAuthCompleted" />
        </div>
      </div>
      <template v-if="isCorporate">
        <div class="ident-form-row">
          <span class="ident-label">법인등록번호 <span class="text-red-500">*</span></span>
          <div class="ident-input">
            <input v-model="form.corporateNo" type="text" placeholder="앞 6자리-뒤 7자리" class="ident-field w-full" :readonly="form.phoneAuthCompleted" />
          </div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">사업자등록번호</span>
          <div class="ident-input">
            <input v-model="form.bizNo" type="text" placeholder="앞3-가운데2-뒤5" class="ident-field w-full" />
          </div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">대표자명 <span class="text-red-500">*</span></span>
          <div class="ident-input">
            <input v-model="form.representativeName" type="text" placeholder="대표자명 입력" class="ident-field w-full" :readonly="form.phoneAuthCompleted" />
          </div>
        </div>
      </template>
      <template v-else>
        <div class="ident-form-row">
          <span class="ident-label">생년월일 <span class="text-red-500">*</span></span>
          <div class="ident-input">
            <input v-model="form.birthDate" type="text" placeholder="YYYYMMDD (8자리)" maxlength="8" class="ident-field w-full" :readonly="form.phoneAuthCompleted" />
          </div>
        </div>
      </template>

      <div class="ident-form-row">
        <span class="ident-label">명의변경 휴대폰번호 <span class="text-red-500">*</span></span>
        <div class="ident-input flex gap-2">
          <input v-model="form.phone" type="tel" placeholder="010-1234-5678" maxlength="11" class="ident-field flex-1" :readonly="form.phoneAuthCompleted" />
          <button type="button" :disabled="!canAuth || form.phoneAuthCompleted" class="ident-btn-primary" @click="doPhoneAuth">
            {{ form.phoneAuthCompleted ? '인증 완료' : '인증' }}
          </button>
        </div>
      </div>

      <div class="ident-form-row">
        <span class="ident-label">이메일</span>
        <div class="ident-input flex gap-1 items-center">
          <input v-model="form.email" type="text" placeholder="아이디" class="ident-field flex-1" />
          <span>@</span>
          <input v-model="form.emailDomain" type="text" placeholder="도메인" class="ident-field flex-1" />
        </div>
      </div>
      <div class="ident-form-row">
        <span class="ident-label">우편번호</span>
        <div class="ident-input flex gap-2">
          <input v-model="form.post" type="text" placeholder="우편번호" class="ident-field flex-1" />
          <button type="button" class="ident-btn-outline" @click="postcodeOpen = true">우편번호 찾기</button>
        </div>
        <McpPostcodePop v-model:open="postcodeOpen" @select="onPostcodeSelect" />
      </div>
      <div class="ident-form-row">
        <span class="ident-label">주소</span>
        <div class="ident-input">
          <input v-model="form.address" type="text" placeholder="주소" class="ident-field w-full mb-1" />
          <input v-model="form.addressDtl" type="text" placeholder="상세주소" class="ident-field w-full" />
        </div>
      </div>

      <div v-if="showAgentInfo" class="mt-4 p-4 bg-gray-50 rounded-lg border border-gray-200">
        <h5 class="font-medium mb-3">대리인 위임 정보</h5>
        <div class="ident-form-row">
          <span class="ident-label">위임받은 고객 이름 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.agentInfo.custName" type="text" class="ident-field w-full" /></div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">생년월일 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.agentInfo.birthDate" type="text" placeholder="YYYYMMDD" maxlength="8" class="ident-field w-full" /></div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">연락처 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.agentInfo.phone" type="tel" class="ident-field w-full" /></div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">신청인과의 관계 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.agentInfo.relation" type="text" placeholder="선택" class="ident-field w-full" /></div>
        </div>
      </div>

      <div v-if="showMinorAgent" class="mt-4 p-4 bg-amber-50 rounded-lg border border-amber-200">
        <h5 class="font-medium mb-3">법정대리인 정보</h5>
        <div class="ident-form-row">
          <span class="ident-label">법정대리인 이름 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.minorAgent.name" type="text" class="ident-field w-full" /></div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">신청인과의 관계 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.minorAgent.relation" type="text" placeholder="예: 부, 모" class="ident-field w-full" /></div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">휴대폰 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.minorAgent.phone" type="tel" class="ident-field w-full" /></div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label"></span>
          <div class="ident-input">
            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.minorAgent.agree" type="checkbox" class="rounded" />
              <span>안내사항 확인 및 동의 (필수)</span>
            </label>
          </div>
        </div>
      </div>
    </section>

    <!-- 양수고객(명의변경 후 고객) -->
    <section class="ident-card">
      <h4 class="ident-subtitle">명의변경 후 고객(양수고객)</h4>

      <div class="ident-form-row">
        <span class="ident-label">고객유형 <span class="text-red-500">*</span></span>
        <div class="ident-input">
          <div class="flex flex-wrap gap-3">
            <label v-for="t in custTypes" :key="'te-' + t.value" class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.transferee.custType" type="radio" :value="t.value" name="identTransfereeType" class="rounded-full" />
              <span>{{ t.label }}</span>
            </label>
          </div>
        </div>
      </div>

      <div class="ident-form-row">
        <span class="ident-label">신분증</span>
        <div class="ident-input">
          <select v-model="form.transferee.idDocType" class="ident-field w-full max-w-xs">
            <option value="">선택해 주세요</option>
            <option value="resident">주민등록증</option>
            <option value="driver">운전면허증</option>
            <option value="mobile">모바일 신분증</option>
            <option value="face">신원확인(안면인증)</option>
          </select>
          <p class="text-xs text-gray-500 mt-1">조회/인증 연동 예정</p>
        </div>
      </div>

      <template v-if="!isTransfereeCorporate">
        <div class="ident-form-row">
          <span class="ident-label">이름 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.transferee.name" type="text" placeholder="이름" class="ident-field w-full" /></div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">주민등록번호 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.transferee.residentNo" type="text" placeholder="앞 6자리-뒤 7자리" class="ident-field w-full max-w-xs" /></div>
        </div>
      </template>
      <template v-else>
        <div class="ident-form-row">
          <span class="ident-label">고객명(법인명) <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.transferee.name" type="text" class="ident-field w-full" /></div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">법인등록번호 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.transferee.corporateNo" type="text" placeholder="앞 6자리-뒤 7자리" class="ident-field w-full max-w-xs" /></div>
        </div>
        <div class="ident-form-row">
          <span class="ident-label">대표자명 <span class="text-red-500">*</span></span>
          <div class="ident-input"><input v-model="form.transferee.representativeName" type="text" class="ident-field w-full" /></div>
        </div>
      </template>

      <div class="ident-form-row">
        <span class="ident-label">휴대폰번호 <span class="text-red-500">*</span></span>
        <div class="ident-input"><input v-model="form.transferee.contactPhone" type="tel" placeholder="010-1234-5678" class="ident-field w-full max-w-xs" /></div>
      </div>
      <div class="ident-form-row">
        <span class="ident-label">이메일 <span class="text-red-500">*</span></span>
        <div class="ident-input flex gap-1 items-center">
          <input v-model="form.transferee.email" type="text" placeholder="아이디" class="ident-field flex-1" />
          <span>@</span>
          <input v-model="form.transferee.emailDomain" type="text" placeholder="도메인" class="ident-field flex-1" />
        </div>
      </div>
      <div class="ident-form-row">
        <span class="ident-label">주소 <span class="text-red-500">*</span></span>
        <div class="ident-input">
          <input v-model="form.transferee.post" type="text" placeholder="우편번호" class="ident-field w-24 mr-2" />
          <button type="button" class="ident-btn-outline text-sm mr-2" @click="transfereePostcodeOpen = true">우편번호 찾기</button>
          <input v-model="form.transferee.address" type="text" placeholder="주소" class="ident-field w-full mt-1" />
          <input v-model="form.transferee.addressDtl" type="text" placeholder="상세주소" class="ident-field w-full mt-1" />
        </div>
        <McpPostcodePop v-model:open="transfereePostcodeOpen" @select="onTransfereePostcodeSelect" />
      </div>

      <div class="ident-form-row">
        <span class="ident-label">구비서류 <span class="text-red-500">*</span></span>
        <div class="ident-input">
          <button type="button" class="ident-btn-outline">스캔하기</button>
          <p class="text-xs text-gray-500 mt-1">가족관계증명서 등 (K-NOTE 연동 예정)</p>
        </div>
      </div>
    </section>

    <!-- 요금제 정보 -->
    <section class="ident-card">
      <h4 class="ident-subtitle">요금제 정보</h4>
      <div class="ident-form-row">
        <span class="ident-label">요금제 <span class="text-red-500">*</span></span>
        <div class="ident-input">
          <select v-model="form.ratePlanCode" class="ident-field w-full max-w-md">
            <option value="">선택해 주세요</option>
            <option value="R1">추천 요금제 (API 연동 후 목록)</option>
          </select>
        </div>
      </div>
      <div class="ident-form-row">
        <span class="ident-label">대리점 <span class="text-red-500">*</span></span>
        <div class="ident-input">
          <select v-model="agencyCode" class="ident-field w-full max-w-md">
            <option value="">대리점 선택</option>
            <option v-for="a in agencyOptions" :key="a.value" :value="a.value">{{ a.label }}</option>
          </select>
        </div>
      </div>

      <h4 class="ident-subtitle">약관 동의</h4>
      <div class="ident-form-row">
        <span class="ident-label">약관동의선택 <span class="text-red-500">*</span></span>
        <div class="ident-input">
          <label class="flex items-center gap-2 cursor-pointer">
            <input v-model="form.termsAgreed" type="checkbox" class="rounded" />
            <span>이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다. (필수)</span>
          </label>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useIdentFormStore } from '@/stores/ident_form'
import { getJoinInfo } from '@/api/ident'
import McpPostcodePop from '@/components/commons/McpPostcodePop.vue'

defineProps({ isActive: Boolean })
const emit = defineEmits(['complete'])

const formStore = useIdentFormStore()
const postcodeOpen = ref(false)
const transfereePostcodeOpen = ref(false)
const agencyCode = ref('')
const agencyOptions = [
  { value: 'AG001', label: '대리점 A' },
  { value: 'AG002', label: '대리점 B' },
  { value: 'AG003', label: '대리점 C' },
]

function onPostcodeSelect({ post, address }) {
  form.value.post = post
  form.value.address = address
}
function onTransfereePostcodeSelect({ post, address }) {
  form.value.transferee.post = post
  form.value.transferee.address = address
}

const custTypes = [
  { value: 'NA', label: '내국인' },
  { value: 'NM', label: '미성년자' },
  { value: 'FN', label: '외국인' },
  { value: 'FNM', label: '외국인 미성년자' },
  { value: 'JP', label: '법인' },
  { value: 'PP', label: '공공기관' },
]

const defaultTransferee = () => ({
  custType: '',
  visitType: 'self',
  name: '',
  residentNo: '',
  foreignerNo: '',
  corporateNo: '',
  bizNo: '',
  industry: '',
  representativeName: '',
  contactPhone: '',
  contactTel: '',
  email: '',
  emailDomain: '',
  post: '',
  address: '',
  addressDtl: '',
  country: '',
  visa: '',
  stayStart: '',
  stayEnd: '',
  minorAgent: { name: '', relation: '', residentNo: '', phone: '', agree: false },
  agentInfo: { custName: '', birthDate: '', phone: '', relation: '' },
  idDocType: '',
  attachmentScanned: false,
})

const form = ref({
  custType: '',
  visitType: 'self',
  name: '',
  birthDate: '',
  corporateNo: '',
  bizNo: '',
  representativeName: '',
  phoneAuthCompleted: false,
  phone: '',
  email: '',
  emailDomain: '',
  post: '',
  address: '',
  addressDtl: '',
  minorAgent: { name: '', relation: '', phone: '', agree: false },
  agentInfo: { custName: '', birthDate: '', phone: '', relation: '' },
  transferee: defaultTransferee(),
  ratePlanCode: '',
  termsAgreed: false,
})

const isCorporate = computed(() => ['JP', 'PP'].includes(form.value.custType))
const isTransfereeCorporate = computed(() => ['JP', 'PP'].includes(form.value.transferee.custType))
const showVisitType = computed(() => isCorporate.value)
const showMinorAgent = computed(() => ['NM', 'FNM'].includes(form.value.custType))
const showAgentInfo = computed(() => form.value.visitType === 'agent' && isCorporate.value)

const canAuth = computed(() => {
  if (!form.value.name || !form.value.phone) return false
  if (isCorporate.value) return !!form.value.corporateNo && !!form.value.representativeName
  return !!form.value.birthDate && form.value.birthDate.length === 8
})

const isComplete = computed(() => {
  if (!form.value.phoneAuthCompleted) return false
  if (!form.value.name || !form.value.email || !form.value.emailDomain) return false
  if (!form.value.post || !form.value.address) return false
  if (isCorporate.value && !form.value.representativeName) return false
  if (showMinorAgent.value) {
    const m = form.value.minorAgent
    if (!m.name || !m.relation || !m.phone || !m.agree) return false
  }
  if (showAgentInfo.value) {
    const a = form.value.agentInfo
    if (!a.custName || !a.birthDate || !a.phone || !a.relation) return false
  }
  const te = form.value.transferee
  if (!te.custType || !te.name || !te.contactPhone || !te.email || !te.emailDomain || !te.post || !te.address) return false
  if (isTransfereeCorporate.value && (!te.representativeName || !te.corporateNo)) return false
  if (!isTransfereeCorporate.value && !te.residentNo?.trim()) return false
  if (!form.value.ratePlanCode || !form.value.termsAgreed) return false
  if (!agencyCode.value) return false
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
      if (data.homeTel) form.value.phone = (data.homeTel + '').replace(/-/g, '')
      if (data.ncn) form.value.ncn = data.ncn
      if (data.custId) form.value.custId = data.custId
      formStore.setCustomerForm(form.value)
      alert('휴대폰번호 인증이 완료되었습니다.')
    } else {
      alert(data?.message || '고객 정보와 휴대폰번호가 일치하지 않습니다. 휴대폰번호를 다시 확인해 주세요.')
    }
  } catch (err) {
    console.warn('X01 API 오류:', err)
    alert(err?.message || '고객 정보와 휴대폰번호가 일치하지 않습니다. 휴대폰번호를 다시 확인해 주세요.')
  }
}

watch(agencyCode, (v) => formStore.setAgencyCode(v))
watch(form, (v) => formStore.setCustomerForm(v), { deep: true })
watch(isComplete, (v) => emit('complete', v))
onMounted(() => {
  agencyCode.value = formStore.agencyCode || ''
  const saved = formStore.customerForm
  if (saved && Object.keys(saved).length) {
    form.value = {
      ...form.value,
      ...saved,
      minorAgent: { ...form.value.minorAgent, ...saved.minorAgent },
      agentInfo: { ...form.value.agentInfo, ...saved.agentInfo },
      transferee: { ...defaultTransferee(), ...saved.transferee },
    }
  }
  if (form.value.visitType === '' && isCorporate.value) form.value.visitType = 'self'
  emit('complete', isComplete.value)
})

const save = async () => {
  if (!form.value.phoneAuthCompleted) return { valid: false, message: '휴대폰 번호 인증을 완료해 주세요.' }
  if (!form.value.name) return { valid: false, message: '이름을 입력해 주세요.' }
  if (isCorporate.value && !form.value.representativeName) return { valid: false, message: '대표자명을 입력해 주세요.' }
  if (!form.value.email || !form.value.emailDomain) return { valid: false, message: '이메일을 입력해 주세요.' }
  if (!form.value.post || !form.value.address) return { valid: false, message: '우편번호와 주소를 입력해 주세요.' }
  if (showMinorAgent.value) {
    const m = form.value.minorAgent
    if (!m.name || !m.relation || !m.phone || !m.agree) return { valid: false, message: '법정대리인 정보를 모두 입력하고 동의해 주세요.' }
  }
  if (showAgentInfo.value) {
    const a = form.value.agentInfo
    if (!a.custName || !a.birthDate || !a.phone || !a.relation) return { valid: false, message: '대리인 위임 정보를 모두 입력해 주세요.' }
  }
  const te = form.value.transferee
  if (!te.custType) return { valid: false, message: '양수고객 고객유형을 선택해 주세요.' }
  if (!te.name) return { valid: false, message: '양수고객 이름을 입력해 주세요.' }
  if (!te.contactPhone) return { valid: false, message: '양수고객 휴대폰번호를 입력해 주세요.' }
  if (!te.email || !te.emailDomain) return { valid: false, message: '양수고객 이메일을 입력해 주세요.' }
  if (!te.post || !te.address) return { valid: false, message: '양수고객 주소를 입력해 주세요.' }
  if (!isTransfereeCorporate.value && !te.residentNo?.trim()) return { valid: false, message: '양수고객 주민등록번호를 입력해 주세요.' }
  if (isTransfereeCorporate.value && (!te.corporateNo || !te.representativeName)) return { valid: false, message: '양수고객 법인등록번호와 대표자명을 입력해 주세요.' }
  if (!form.value.ratePlanCode) return { valid: false, message: '요금제를 선택해 주세요.' }
  if (!agencyCode.value) return { valid: false, message: '대리점을 선택해 주세요.' }
  if (!form.value.termsAgreed) return { valid: false, message: '약관에 동의해 주세요.' }
  formStore.setAgencyCode(agencyCode.value)
  formStore.setCustomerForm(form.value)
  return true
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
.ident-field:read-only {
  background: #f3f4f6;
  color: #6b7280;
}
.ident-btn-primary {
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  background: #0d9488;
  color: #fff;
  font-size: 0.875rem;
  white-space: nowrap;
}
.ident-btn-primary:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.ident-btn-outline {
  padding: 0.5rem 0.75rem;
  border: 1px solid #0d9488;
  color: #0d9488;
  border-radius: 0.375rem;
  font-size: 0.875rem;
}
</style>
