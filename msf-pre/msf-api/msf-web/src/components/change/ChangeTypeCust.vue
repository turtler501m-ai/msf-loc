<template>
  <div class="page-step-panel space-y-8">
    <!-- 1. 고객 정보 -->
    <section>
      <h3 class="section-title">고객 정보</h3>

      <h4 class="form-section-title">고객유형</h4>
      <div class="form-block">
        <div class="form-row">
          <span class="form-row-label">고객유형</span>
          <div class="form-row-input flex flex-wrap gap-2">
            <label
              v-for="t in custTypes"
              :key="t.value"
              class="form-radio-box"
              :class="{ 'form-radio-box--selected': form.custType === t.value }"
            >
              <input v-model="form.custType" type="radio" :value="t.value" name="custType" class="rounded-full shrink-0" />
              <span class="text-sm whitespace-nowrap">{{ t.label }}</span>
            </label>
          </div>
        </div>
      </div>

      <div v-if="showVisitType" class="form-block">
        <div class="form-row">
          <span class="form-row-label">방문고객</span>
          <div class="form-row-input flex gap-2">
            <label class="form-radio-box min-w-[80px] justify-center" :class="{ 'form-radio-box--selected': form.visitType === 'self' }">
              <input v-model="form.visitType" type="radio" value="self" name="visitType" class="rounded-full shrink-0" />
              <span>본인</span>
            </label>
            <label class="form-radio-box min-w-[80px] justify-center" :class="{ 'form-radio-box--selected': form.visitType === 'agent' }">
              <input v-model="form.visitType" type="radio" value="agent" name="visitType" class="rounded-full shrink-0" />
              <span>대리인</span>
            </label>
          </div>
        </div>
      </div>

      <h4 class="form-section-title">가입자 정보</h4>
      <div class="form-block">
        <div class="form-rows">
          <div class="form-row">
            <span class="form-row-label">이름</span>
            <div class="form-row-input">
              <input v-model="form.name" type="text" placeholder="이름 입력" class="form-input form-input--name" />
            </div>
          </div>
          <div v-if="isCorporate" class="form-row">
            <span class="form-row-label">법인등록번호</span>
            <div class="form-row-input">
              <input v-model="form.corporateNo" type="text" placeholder="앞6자리-뒤7자리" class="form-input form-input--corp" />
            </div>
          </div>
          <div v-else class="form-row">
            <span class="form-row-label">생년월일</span>
            <div class="form-row-input flex items-center gap-4">
              <input v-model="form.birthDate" type="text" placeholder="YYYYMMDD (8자리)" maxlength="8" class="form-input form-input--birth" />
              <label class="flex items-center gap-1.5 cursor-pointer shrink-0">
                <input v-model="form.gender" type="radio" value="M" class="rounded-full border-gray-300" />
                <span class="text-sm">남</span>
              </label>
              <label class="flex items-center gap-1.5 cursor-pointer shrink-0">
                <input v-model="form.gender" type="radio" value="F" class="rounded-full border-gray-300" />
                <span class="text-sm">여</span>
              </label>
            </div>
          </div>
        </div>
      </div>

      <div class="form-block">
        <div class="form-row">
          <span class="form-row-label">변경 휴대폰번호</span>
          <div class="form-row-input flex items-center gap-2 flex-wrap">
            <input
              :value="phoneParts[0]"
              type="tel"
              placeholder="010"
              maxlength="3"
              class="form-input form-input--phone-part"
              @input="setPhonePart(0, ($event.target).value)"
            />
            <span class="text-gray-400 shrink-0">-</span>
            <input
              :value="phoneParts[1]"
              type="tel"
              placeholder="1234"
              maxlength="4"
              class="form-input form-input--phone-part"
              @input="setPhonePart(1, ($event.target).value)"
            />
            <span class="text-gray-400 shrink-0">-</span>
            <input
              :value="phoneParts[2]"
              type="tel"
              placeholder="5678"
              maxlength="4"
              class="form-input form-input--phone-part"
              @input="setPhonePart(2, ($event.target).value)"
            />
            <button
              type="button"
              :disabled="!canAuth"
              class="form-btn form-btn--primary disabled:opacity-50 disabled:cursor-not-allowed shrink-0"
              @click="doPhoneAuth"
            >
              {{ form.phoneAuthCompleted ? '인증 완료' : '인증' }}
            </button>
          </div>
        </div>
        <p v-if="!form.phoneAuthCompleted" class="form-hint form-row-hint">
          이름, 생년월일(또는 법인등록번호), 휴대폰번호 입력 후 인증해 주세요.
        </p>
      </div>

      <h4 class="form-section-title">연락처</h4>
      <div class="form-block">
        <div class="form-rows">
          <div class="form-row">
            <span class="form-row-label">휴대폰</span>
            <div class="form-row-input flex items-center gap-2 flex-wrap">
              <input
                :value="phoneParts[0]"
                type="tel"
                placeholder="010"
                maxlength="3"
                class="form-input form-input--phone-part"
                :readonly="form.phoneAuthCompleted"
                @input="setPhonePart(0, ($event.target).value)"
              />
              <span class="text-gray-400 shrink-0">-</span>
              <input
                :value="phoneParts[1]"
                type="tel"
                placeholder="1234"
                maxlength="4"
                class="form-input form-input--phone-part"
                :readonly="form.phoneAuthCompleted"
                @input="setPhonePart(1, ($event.target).value)"
              />
              <span class="text-gray-400 shrink-0">-</span>
              <input
                :value="phoneParts[2]"
                type="tel"
                placeholder="5678"
                maxlength="4"
                class="form-input form-input--phone-part"
                :readonly="form.phoneAuthCompleted"
                @input="setPhonePart(2, ($event.target).value)"
              />
              <p v-if="form.phoneAuthCompleted" class="form-hint mt-1 w-full">인증 완료된 번호입니다.</p>
            </div>
          </div>
          <div class="form-row">
            <span class="form-row-label">전화</span>
            <div class="form-row-input flex items-center gap-2 flex-wrap">
              <input
                :value="telParts[0]"
                type="tel"
                placeholder="지역"
                maxlength="4"
                class="form-input form-input--tel-part"
                @input="setTelPart(0, ($event.target).value)"
              />
              <span class="text-gray-400 shrink-0">-</span>
              <input
                :value="telParts[1]"
                type="tel"
                placeholder="1234"
                maxlength="4"
                class="form-input form-input--tel-part"
                @input="setTelPart(1, ($event.target).value)"
              />
              <span class="text-gray-400 shrink-0">-</span>
              <input
                :value="telParts[2]"
                type="tel"
                placeholder="5678"
                maxlength="4"
                class="form-input form-input--tel-part"
                @input="setTelPart(2, ($event.target).value)"
              />
            </div>
          </div>
          <div class="form-row">
            <span class="form-row-label">이메일</span>
            <div class="form-row-input flex gap-1 items-center">
              <input v-model="form.email" type="text" placeholder="아이디" class="form-input form-input--email-id" />
              <span class="text-gray-500 shrink-0">@</span>
              <input v-model="form.emailDomain" type="text" placeholder="도메인" class="form-input form-input--email-domain" />
            </div>
          </div>
          <div class="form-row">
            <span class="form-row-label">우편번호</span>
            <div class="form-row-input flex gap-2 items-center">
              <input v-model="form.post" type="text" placeholder="우편번호" class="form-input form-input--post" maxlength="5" />
              <button type="button" class="form-btn border border-gray-300 shrink-0" @click="openPostcodeSearch">우편번호 찾기</button>
            </div>
          </div>
          <div class="form-row">
            <span class="form-row-label">주소</span>
            <div class="form-row-input">
              <input v-model="form.address" type="text" placeholder="주소" class="form-input form-input--address" />
            </div>
          </div>
          <div class="form-row">
            <span class="form-row-label">상세주소</span>
            <div class="form-row-input">
              <input v-model="form.addressDtl" type="text" placeholder="상세주소" class="form-input form-input--address" />
            </div>
          </div>
        </div>
        <McpPostcodePop v-model:open="postcodeOpen" @select="onPostcodeSelect" />
      </div>

      <div v-if="showMinorAgent" class="form-block form-block--sub">
        <h4 class="form-label mb-3">현재 고객 법정대리인 정보</h4>
        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="form-field-label">이름</label>
            <input v-model="form.minorAgent.name" type="text" class="form-input form-input--name" placeholder="이름 입력" />
          </div>
          <div>
            <label class="form-field-label">신청인과의 관계</label>
            <input v-model="form.minorAgent.relation" type="text" placeholder="예: 부, 모" class="form-input form-input--relation" />
          </div>
          <div>
            <label class="form-field-label">연락처(휴대폰)</label>
            <input v-model="form.minorAgent.phone" type="tel" class="form-input form-input--phone" />
          </div>
          <div class="sm:col-span-2">
            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.minorAgent.agree" type="checkbox" class="rounded border-gray-300" />
              <span class="text-sm">현재 고객 법정대리인 안내사항 확인 및 동의(필수)</span>
            </label>
          </div>
        </div>
      </div>

      <div v-if="showAgentInfo" class="form-block form-block--sub">
        <h4 class="form-label mb-3">대리인 위임 정보</h4>
        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="form-field-label">위임받은 고객 이름*</label>
            <input v-model="form.agentInfo.custName" type="text" class="form-input form-input--name" />
          </div>
          <div>
            <label class="form-field-label">생년월일*</label>
            <input v-model="form.agentInfo.birthDate" type="text" placeholder="YYYYMMDD" class="form-input form-input--birth" maxlength="8" />
          </div>
          <div>
            <label class="form-field-label">연락처*</label>
            <input v-model="form.agentInfo.phone" type="tel" class="form-input form-input--phone" />
          </div>
          <div>
            <label class="form-field-label">신청인과의 관계*</label>
            <input v-model="form.agentInfo.relation" type="text" class="form-input form-input--relation" />
          </div>
        </div>
      </div>
    </section>

    <!-- 2. 서비스 변경 (설계서 v1.0 20260310: 카테고리 라벨·버튼 배치, 스마트서식지 DB 조회) -->
    <section class="pt-6 border-t border-gray-200">
      <h3 class="section-title">서비스 변경</h3>
      <div class="form-block">
        <div class="form-row">
          <span class="form-row-label form-row-label--bold">서비스</span>
          <div class="form-row-input space-y-4">
            <div v-if="invalidCombinationMsg" class="p-3 bg-red-50 text-red-700 rounded-lg text-sm">
              {{ invalidCombinationMsg }}
            </div>
            <template v-if="categoryList.length">
              <div
                v-for="cat in categoryList"
                :key="cat.category?.id"
                class="space-y-2"
              >
                <!-- 카테고리 라벨 버튼 (설계서 배치 그대로) -->
                <div class="flex items-center gap-2">
                  <span
                    class="inline-flex items-center px-3 py-1.5 rounded-full text-sm font-medium bg-teal-100 text-teal-800 border border-teal-200"
                  >
                    {{ cat.category?.label }}
                  </span>
                </div>
                <!-- 해당 카테고리 내 옵션 버튼(체크박스) -->
                <div class="grid grid-cols-3 gap-3 pl-0">
                  <label
                    v-for="opt in cat.options"
                    :key="opt.id"
                    class="flex items-center gap-2 p-3 rounded-lg border cursor-pointer"
                    :class="optBoxClass(opt.id)"
                  >
                    <input
                      :id="`opt-${opt.id}`"
                      v-model="selectedIds"
                      type="checkbox"
                      :value="opt.id"
                      :disabled="isOptDisabled(opt.id)"
                      class="rounded border-gray-300 shrink-0"
                    />
                    <span class="text-sm break-keep">
                      {{ opt.label }}
                      <span v-if="coreChangeCodes.includes(opt.id)" class="text-xs text-amber-600 ml-0.5 block">(동시 선택 불가)</span>
                    </span>
                  </label>
                </div>
              </div>
            </template>
            <div v-else class="grid grid-cols-3 gap-3">
              <label
                v-for="opt in selectableOptions"
                :key="opt.id"
                class="flex items-center gap-2 p-3 rounded-lg border cursor-pointer"
                :class="optBoxClass(opt.id)"
              >
                <input
                  :id="`opt-${opt.id}`"
                  v-model="selectedIds"
                  type="checkbox"
                  :value="opt.id"
                  :disabled="isOptDisabled(opt.id)"
                  class="rounded border-gray-300 shrink-0"
                />
                <span class="text-sm break-keep">
                  {{ opt.label }}
                  <span v-if="coreChangeCodes.includes(opt.id)" class="text-xs text-amber-600 ml-0.5 block">(동시 선택 불가)</span>
                </span>
              </label>
            </div>
          </div>
        </div>
        <div class="form-row mt-4">
          <span class="form-row-label"></span>
          <div class="form-row-input space-y-1.5">
            <label class="flex items-center gap-2 cursor-pointer">
              <input
                type="checkbox"
                :checked="isAllSelected"
                :indeterminate="isPartialSelected"
                class="rounded border-gray-300"
                @change="toggleSelectAll"
              />
              <span class="font-medium">전체 선택</span>
              <span v-if="isPartialSelected" class="text-sm text-gray-500">(동시 변경 불가 항목 제외)</span>
            </label>
            <p class="text-sm text-gray-600 leading-relaxed">
              ※ 동시 변경 불가능 업무 - 요금상품 변경, 번호변경, 분실복구/일시정지해제 신청, 핸드폰기변/일반기변
            </p>
          </div>
        </div>
        <div class="form-row mt-4">
          <span class="form-row-label"></span>
          <div class="form-row-input">
            <button
              type="button"
              :disabled="!serviceCheckBtnEnabled"
              class="form-btn form-btn--primary disabled:opacity-50 disabled:cursor-not-allowed"
              @click="completeServiceCheck"
            >
              {{ serviceCheckCompleted ? '서비스 완료' : '서비스 체크' }}
            </button>
          </div>
        </div>
        <div class="form-row mt-5">
          <span class="form-row-label">대리점</span>
          <div class="form-row-input">
            <select v-model="formStore.branchCode" class="form-input form-input--select max-w-xs">
              <option value="">대리점을 선택하세요</option>
            </select>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useServiceChangeFormStore } from '@/stores/service_change_form'
import { getJoinInfo, getChangeTargets } from '@/api/serviceChange'
import { CORE_CHANGE, ServiceChangeOptions } from '@/constants/serviceChange'
import McpPostcodePop from '@/components/commons/McpPostcodePop.vue'

const emit = defineEmits(['update:complete'])
const formStore = useServiceChangeFormStore()
const postcodeOpen = ref(false)
const authLoading = ref(false)
const selectedIds = ref([])
/** API 조회 결과: 카테고리별 옵션 (스마트서식지 DB) */
const categoryList = ref([])
/** 동시 변경 불가 코드 (API concurrentGroups flatten, 없으면 상수 사용) */
const coreChangeCodes = ref([...CORE_CHANGE])

const custTypes = [
  { value: 'NA', label: '내국인' },
  { value: 'NM', label: '미성년자(19세 미만)' },
  { value: 'FN', label: '외국인(Foreigner)' },
  { value: 'FNM', label: '외국인 미성년자' },
  { value: 'JP', label: '법인' },
  { value: 'PP', label: '공공기관' },
]

const defaultForm = () => ({
  custType: '',
  visitType: 'self',
  name: '',
  birthDate: '',
  gender: '',
  corporateNo: '',
  phoneAuthCompleted: false,
  phone: '',
  tel: '',
  email: '',
  emailDomain: '',
  post: '',
  address: '',
  addressDtl: '',
  ncn: '', // join-info 응답
  custId: '', // join-info 응답
  minorAgent: { name: '', relation: '', phone: '', agree: false },
  agentInfo: { custName: '', birthDate: '', phone: '', relation: '' },
})
const form = ref(defaultForm())

// 서비스 선택 (API categoryList 기준 평면 목록, 미조회 시 상수 폴백)
const selectableOptions = computed(() => {
  if (categoryList.value.length) {
    return categoryList.value.flatMap((c) => c.options || [])
  }
  return ServiceChangeOptions
})
const selectedSet = computed(() => new Set(selectedIds.value))
const hasInvalidCombination = computed(() => selectedIds.value.filter((id) => coreChangeCodes.value.includes(id)).length >= 2)
const invalidCombinationMsg = computed(() =>
  hasInvalidCombination.value ? '동시 처리 불가능한 업무가 있습니다. - 번호변경, 분실복구/일시정지해제 신청, 요금상품 변경, 핸드폰 기변/일반 기변' : '',
)
// 전체선택 대상: 동시선택 가능 서비스만 (coreChangeCodes 제외)
const nonCoreIds = computed(() =>
  selectableOptions.value.filter((o) => !coreChangeCodes.value.includes(o.id)).map((o) => o.id),
)
// 전체선택 체크: 버튼 클릭으로만 체크됨 (개별 선택 시에는 절대 체크 안 됨)
const allSelectedByButton = ref(false)
const skipResetAllSelectedByButton = ref(false)
const isAllSelected = computed(
  () =>
    nonCoreIds.value.length > 0 &&
    nonCoreIds.value.every((id) => selectedIds.value.includes(id)) &&
    allSelectedByButton.value,
)
const isPartialSelected = computed(() => {
  const n = nonCoreIds.value.filter((id) => selectedIds.value.includes(id)).length
  return n > 0 && n < nonCoreIds.value.length && !allSelectedByButton.value
})

function isOptDisabled(id) {
  return coreChangeCodes.value.includes(id) && selectedIds.value.some((x) => x !== id && coreChangeCodes.value.includes(x))
}
function optBoxClass(id) {
  const selected = selectedSet.value.has(id)
  const disabled = coreChangeCodes.value.includes(id) && selectedIds.value.some((x) => x !== id && coreChangeCodes.value.includes(x))
  return [
    selected ? 'border-teal-500 bg-teal-50' : 'border-gray-200 hover:bg-gray-50',
    disabled ? 'opacity-60' : '',
  ]
}
function toggleSelectAll() {
  if (isAllSelected.value) {
    selectedIds.value = selectedIds.value.filter((id) => !nonCoreIds.value.includes(id))
    allSelectedByButton.value = false
  } else {
    skipResetAllSelectedByButton.value = true
    selectedIds.value = [...new Set([...selectedIds.value, ...nonCoreIds.value])]
    allSelectedByButton.value = true
  }
}

// 서비스 체크 버튼
const serviceCheckCompleted = ref(false)
const serviceCheckBtnEnabled = computed(
  () => selectedIds.value.length >= 1 && !hasInvalidCombination.value && !serviceCheckCompleted.value,
)
function completeServiceCheck() {
  serviceCheckCompleted.value = true
}

const typeComplete = computed(
  () =>
    selectedIds.value.length >= 1 &&
    !hasInvalidCombination.value &&
    serviceCheckCompleted.value,
)

// 전화번호 3칸 ↔ form.phone
function parsePhone(phone) {
  const p = (phone || '').replace(/\D/g, '')
  return [p.slice(0, 3), p.slice(3, 7), p.slice(7, 11)]
}
const phoneParts = computed(() => parsePhone(form.value.phone))
function setPhonePart(index, value) {
  const digits = (value || '').replace(/\D/g, '').slice(0, index === 0 ? 3 : 4)
  const parts = [...parsePhone(form.value.phone)]
  parts[index] = digits
  form.value.phone = parts.filter(Boolean).join('-')
}

// 전화(유선) 3칸 ↔ form.tel
function parseTel(tel) {
  const s = (tel || '').replace(/\D/g, '')
  if (s.length >= 10) {
    const a = s.slice(0, 2) === '02' ? 2 : 3
    return [s.slice(0, a), s.slice(a, a + 4), s.slice(a + 4, a + 8)]
  }
  const parts = (tel || '').split('-').map((p) => (p || '').replace(/\D/g, ''))
  return [parts[0] || '', parts[1] || '', parts[2] || '']
}
const telParts = computed(() => parseTel(form.value.tel))
function setTelPart(index, value) {
  const digits = (value || '').replace(/\D/g, '').slice(0, 4)
  const parts = [...parseTel(form.value.tel)]
  parts[index] = digits
  form.value.tel = parts.filter(Boolean).join('-')
}

// 고객 정보
const isCorporate = computed(() => ['JP', 'PP'].includes(form.value.custType))
const showVisitType = computed(() => isCorporate.value)
const showMinorAgent = computed(() => ['NM', 'FNM'].includes(form.value.custType))
const showAgentInfo = computed(() => form.value.visitType === 'agent' && isCorporate.value)
const fullPhoneDigits = computed(() => (form.value.phone || '').replace(/\D/g, ''))
const canAuth = computed(() => {
  if (!form.value.name || fullPhoneDigits.value.length !== 11) return false
  return isCorporate.value ? !!form.value.corporateNo : !!form.value.birthDate && form.value.birthDate.length === 8
})
const custComplete = computed(() => {
  if (!form.value.phoneAuthCompleted || !form.value.name || !form.value.email || !form.value.emailDomain || !form.value.post || !form.value.address) return false
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
const isComplete = computed(() => typeComplete.value && custComplete.value)

watch(selectedIds, (ids) => {
  formStore.setSelectedOptions(ids)
  if (serviceCheckCompleted.value) serviceCheckCompleted.value = false
  if (skipResetAllSelectedByButton.value) {
    skipResetAllSelectedByButton.value = false
  } else {
    allSelectedByButton.value = false
  }
}, { deep: true })
watch(form, (v) => formStore.setCustomerForm(v), { deep: true })
watch(isComplete, (v) => emit('update:complete', v))
watch(
  () => form.value.custType,
  (custType) => {
    if (custType === 'NA' || custType === 'FN') form.value.visitType = 'self'
    if (custType === 'NM' || custType === 'FNM') form.value.visitType = 'agent'
  },
)

onMounted(async () => {
  try {
    const res = await getChangeTargets()
    if (res?.categoryList?.length) categoryList.value = res.categoryList
    // 동시변경 불가 코드: API 기준 반영(빈 배열이면 동시 불가 없음)
    if (Array.isArray(res?.concurrentGroups)) coreChangeCodes.value = res.concurrentGroups.flat()
  } catch (_) {
    // API 실패 시 상수 폴백 유지
  }
  if (formStore.selectedOptions?.length) selectedIds.value = [...formStore.selectedOptions]
  if (formStore.customerForm && Object.keys(formStore.customerForm).length) {
    form.value = { ...defaultForm(), ...formStore.customerForm }
  }
  if (form.value.visitType === '' && isCorporate.value) form.value.visitType = 'self'
  emit('update:complete', isComplete.value)
})

function openPostcodeSearch() {
  postcodeOpen.value = true
}
function onPostcodeSelect({ post, address }) {
  form.value.post = post
  form.value.address = address
}

async function doPhoneAuth() {
  if (form.value.phoneAuthCompleted) return
  authLoading.value = true
  try {
    const data = await getJoinInfo({
      name: (form.value.name || '').trim(),
      ctn: (form.value.phone || '').replace(/-/g, ''),
      ncn: formStore.customerForm?.ncn || '',
      custId: formStore.customerForm?.custId || '',
    })
    if (data && data.success !== false) {
      form.value.phoneAuthCompleted = true
      if (data.addr) form.value.address = data.addr
      if (data.email) {
        const [a, b] = (data.email || '').split('@')
        form.value.email = a || ''
        form.value.emailDomain = b || ''
      }
      if (data.homeTel) form.value.phone = (data.homeTel + '').replace(/-/g, '').replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3')
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
  } finally {
    authLoading.value = false
  }
}

const validate = async () => {
  if (!selectedIds.value.length) return { valid: false, message: '최소 1개 이상의 변경 항목을 선택해 주세요.' }
  if (hasInvalidCombination.value) return { valid: false, message: invalidCombinationMsg.value }
  if (!serviceCheckCompleted.value) return { valid: false, message: '서비스 체크를 완료해 주세요.' }
  formStore.setSelectedOptions(selectedIds.value)
  if (!form.value.phoneAuthCompleted) return { valid: false, message: '휴대폰 번호 인증을 완료해 주세요.' }
  if (!form.value.name) return { valid: false, message: '이름을 입력해 주세요.' }
  if (!form.value.email || !form.value.emailDomain) return { valid: false, message: '이메일을 입력해 주세요.' }
  if (!form.value.post || !form.value.address) return { valid: false, message: '우편번호와 주소를 입력해 주세요.' }
  if (showMinorAgent.value) {
    const m = form.value.minorAgent
    if (!m.name || !m.relation || !m.phone || !m.agree) return { valid: false, message: '현재 고객 법정대리인 정보를 모두 입력하고 동의해 주세요.' }
  }
  if (showAgentInfo.value) {
    const a = form.value.agentInfo
    if (!a.custName || !a.birthDate || !a.phone || !a.relation) return { valid: false, message: '대리인 위임 정보를 모두 입력해 주세요.' }
  }
  formStore.setCustomerForm(form.value)
  return { valid: true, message: '' }
}

defineExpose({ validate })
</script>

<style scoped>
@reference "tailwindcss";

.section-title {
  @apply text-lg font-semibold text-gray-800 pb-2 mb-4 border-b border-gray-300;
}
.form-section-title {
  @apply text-base font-bold text-gray-800 pb-2 mb-3 border-b border-gray-300;
}
.form-block {
  @apply mb-5;
}
.form-block--sub {
  @apply p-4 rounded-lg border border-gray-200 bg-gray-50;
}
.form-rows {
  @apply space-y-4;
}
.form-row {
  @apply flex items-start gap-4;
}
.form-row-label {
  width: 140px;
  @apply shrink-0 text-sm font-medium text-gray-700 pt-2;
}
.form-row-label--bold {
  @apply font-bold text-gray-800;
}
.form-row-input {
  @apply flex-1 min-w-0;
}
.form-label,
.form-field-label {
  @apply block text-sm font-medium text-gray-700;
}
.form-label {
  @apply mb-2;
}
.form-field-label {
  @apply mb-1.5;
}
.form-input {
  @apply rounded-lg border border-gray-300 px-3 py-2 text-sm focus:border-teal-500 focus:ring-1 focus:ring-teal-500;
}
.form-input--name { width: 20ch; min-width: 120px; }
.form-input--birth { width: 8ch; }
.form-input--corp { width: 15ch; }
.form-input--phone { width: 13ch; }
.form-input--phone-part { width: 6.5ch; min-width: 3.5rem; }
.form-input--tel-part { width: 5ch; min-width: 3rem; }
.form-input--tel { width: 14ch; }
.form-input--email-id { width: 14ch; min-width: 80px; }
.form-input--email-domain { width: 18ch; min-width: 100px; }
.form-input--post { width: 6ch; }
.form-input--relation { width: 8ch; }
.form-input--address { width: 100%; }
.form-input--select {
  @apply bg-white;
}
.form-radio-box {
  @apply flex items-center gap-2 cursor-pointer py-2 px-3 rounded-lg border border-gray-200 transition-colors hover:bg-gray-50;
}
.form-radio-box--selected {
  @apply border-teal-500 bg-teal-50;
}
.form-btn {
  @apply px-4 py-2 rounded-lg text-sm font-medium;
}
.form-btn--primary {
  @apply bg-teal-600 text-white;
}
.form-hint {
  @apply mt-1.5 text-xs text-gray-500;
}
.form-row-hint {
  @apply ml-[156px];
}
</style>
