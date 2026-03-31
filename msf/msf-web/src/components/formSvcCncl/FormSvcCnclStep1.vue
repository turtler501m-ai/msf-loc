<template>
  <div class="page-step-panel space-y-6">
    <h3 class="text-lg font-semibold">서비스 해지 - 고객 정보</h3>

    <!-- ① 고객유형 -->
    <h4 class="cncl-section-title">고객유형</h4>
    <div class="cncl-form-block">
      <div class="cncl-form-row">
        <span class="cncl-label">고객유형</span>
        <div class="cncl-input flex flex-wrap gap-2">
          <label
            v-for="t in custTypes"
            :key="t.value"
            class="cncl-radio-box"
            :class="{ 'cncl-radio-box--selected': form.custType === t.value }"
          >
            <input v-model="form.custType" type="radio" :value="t.value" name="cancelCustType" class="rounded-full shrink-0" />
            <span class="text-sm whitespace-nowrap">{{ t.label }}</span>
          </label>
        </div>
      </div>

      <!-- 방문고객: 법인/공공기관만 노출 -->
      <div v-if="showVisitType" class="cncl-form-row mt-3">
        <span class="cncl-label">방문고객</span>
        <div class="cncl-input flex gap-2">
          <label class="cncl-radio-box min-w-[90px] justify-center" :class="{ 'cncl-radio-box--selected': form.visitType === 'self' }">
            <input v-model="form.visitType" type="radio" value="self" name="cancelVisitType" class="rounded-full shrink-0" />
            <span>본인/대표</span>
          </label>
          <label class="cncl-radio-box min-w-[80px] justify-center" :class="{ 'cncl-radio-box--selected': form.visitType === 'agent' }">
            <input v-model="form.visitType" type="radio" value="agent" name="cancelVisitType" class="rounded-full shrink-0" />
            <span>대리인</span>
          </label>
        </div>
      </div>
    </div>

    <!-- ② 신분증 확인 -->
    <h4 class="cncl-section-title">신분증 확인</h4>
    <div class="cncl-form-block">
      <div class="cncl-form-row">
        <span class="cncl-label">신분증 확인</span>
        <div class="cncl-input space-y-2">
          <div class="flex items-center gap-2">
            <select v-model="form.idDocType" class="cncl-field cncl-field--select flex-1">
              <option value="">선택해 주세요</option>
              <option value="resident">주민등록증</option>
              <option value="driver">운전면허증</option>
              <option value="passport">국내여권</option>
              <option value="alien">외국인등록증</option>
              <option value="permanent">영주증</option>
              <option value="domestic">국내거소신고증</option>
              <option value="veteran">국가보훈증</option>
            </select>
            <button type="button" class="cncl-btn-outline shrink-0" @click="onIdScan">
              스캔하기
            </button>
          </div>
          <!-- 신분증 유형별 추가 필드 -->
          <template v-if="form.idDocType && form.idDocType !== ''">
            <div v-if="form.idDocType === 'driver'" class="flex items-center gap-2">
              <input v-model="form.idDocLicenseNo" type="text" placeholder="면허번호" class="cncl-field flex-1" />
              <input v-model="form.idDocLicenseArea" type="text" placeholder="면허지역" class="cncl-field w-24" />
            </div>
            <input v-model="form.idDocIssueDate" type="text" placeholder="발급일자 (YYYY.MM.DD)" class="cncl-field" />
          </template>
          <p class="cncl-hint">스캔 연동 예정</p>
        </div>
      </div>
    </div>

    <!-- ③ 가입자 정보 -->
    <h4 class="cncl-section-title">가입자 정보</h4>
    <div class="cncl-form-block">
      <div class="cncl-form-rows">
        <div class="cncl-form-row">
          <span class="cncl-label">이름 <span class="text-red-500">*</span></span>
          <div class="cncl-input">
            <input v-model="form.name" type="text" placeholder="이름 입력" class="cncl-field" :readonly="form.phoneAuthCompleted" />
          </div>
        </div>

        <template v-if="isCorporate">
          <div class="cncl-form-row">
            <span class="cncl-label">법인등록번호 <span class="text-red-500">*</span></span>
            <div class="cncl-input">
              <input v-model="form.corporateNo" type="text" placeholder="앞 6자리 - 뒤 7자리" class="cncl-field" :readonly="form.phoneAuthCompleted" />
            </div>
          </div>
          <div class="cncl-form-row">
            <span class="cncl-label">사업자등록번호</span>
            <div class="cncl-input">
              <input v-model="form.businessNo" type="text" placeholder="앞 3자리 - 가운데 2자리 - 뒤 5자리" maxlength="12" class="cncl-field" :readonly="form.phoneAuthCompleted" />
            </div>
          </div>
          <div class="cncl-form-row">
            <span class="cncl-label">대표자명 <span class="text-red-500">*</span></span>
            <div class="cncl-input">
              <input v-model="form.representName" type="text" placeholder="대표자명 입력" class="cncl-field" :readonly="form.phoneAuthCompleted" />
            </div>
          </div>
        </template>
        <template v-else>
          <div class="cncl-form-row">
            <span class="cncl-label">생년월일 <span class="text-red-500">*</span></span>
            <div class="cncl-input">
              <input v-model="form.birthDate" type="text" placeholder="8자리(YYYYMMDD)" maxlength="8" class="cncl-field" :readonly="form.phoneAuthCompleted" />
            </div>
          </div>
        </template>
      </div>

      <!-- 해지 휴대폰번호 (3분할) + 인증 -->
      <div class="cncl-form-row mt-4">
        <span class="cncl-label">해지 휴대폰번호 <span class="text-red-500">*</span></span>
        <div class="cncl-input">
          <p class="cncl-hint mb-1">해지할 휴대폰번호를 입력하고 [인증] 버튼을 클릭해 주세요.</p>
          <div class="flex items-center gap-1 flex-wrap">
            <input
              :value="phoneParts[0]"
              type="tel"
              placeholder="010"
              maxlength="3"
              class="cncl-field cncl-field--phone-part"
              :readonly="form.phoneAuthCompleted"
              @input="setPhonePart(0, $event.target.value)"
            />
            <span class="text-gray-400 shrink-0">-</span>
            <input
              :value="phoneParts[1]"
              type="tel"
              placeholder="1234"
              maxlength="4"
              class="cncl-field cncl-field--phone-part"
              :readonly="form.phoneAuthCompleted"
              @input="setPhonePart(1, $event.target.value)"
            />
            <span class="text-gray-400 shrink-0">-</span>
            <input
              :value="phoneParts[2]"
              type="tel"
              placeholder="5678"
              maxlength="4"
              class="cncl-field cncl-field--phone-part"
              :readonly="form.phoneAuthCompleted"
              @input="setPhonePart(2, $event.target.value)"
            />
            <button
              type="button"
              :disabled="!canAuth"
              class="cncl-btn-primary disabled:opacity-50 disabled:cursor-not-allowed shrink-0"
              @click="doPhoneAuth"
            >
              {{ form.phoneAuthCompleted ? '인증 완료' : '인증' }}
            </button>
          </div>
          <p v-if="form.phoneAuthCompleted" class="cncl-hint mt-1">인증 완료된 번호입니다.</p>
        </div>
      </div>

    </div>

    <!-- ④ 구비서류 (외국인인 경우만) -->
    <template v-if="isForeigner">
      <h4 class="cncl-section-title">구비서류</h4>
      <div class="cncl-form-block">
        <div class="cncl-form-row">
          <span class="cncl-label">구비서류 <span class="text-red-500">*</span></span>
          <div class="cncl-input">
            <button type="button" class="cncl-btn-outline" @click="onDocScan">
              스캔하기
            </button>
            <p class="cncl-hint mt-1">스캔 연동 예정</p>
          </div>
        </div>
      </div>
    </template>

    <!-- ⑤ 법정대리인 정보 (미성년자) -->
    <div v-if="showMinorAgent" class="cncl-form-block cncl-form-block--sub">
      <h4 class="font-medium mb-4">법정대리인 정보</h4>
      <div class="cncl-form-rows">
        <div class="cncl-form-row">
          <span class="cncl-label">이름 <span class="text-red-500">*</span></span>
          <div class="cncl-input">
            <input v-model="form.minorAgent.name" type="text" placeholder="이름 입력" class="cncl-field" :readonly="minorAuthConfirmed" />
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">생년월일 <span class="text-red-500">*</span></span>
          <div class="cncl-input">
            <input v-model="form.minorAgent.birthDate" type="text" placeholder="8자리(YYYYMMDD)" maxlength="8" class="cncl-field" :readonly="minorAuthConfirmed" />
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">신청인과의 관계 <span class="text-red-500">*</span></span>
          <div class="cncl-input">
            <select v-model="form.minorAgent.relation" class="cncl-field cncl-field--select" :disabled="minorAuthConfirmed">
              <option value="">선택</option>
              <option value="부">부</option>
              <option value="모">모</option>
              <option value="그 외">그 외</option>
            </select>
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">연락처(휴대폰) <span class="text-red-500">*</span></span>
          <div class="cncl-input">
            <div class="flex items-center gap-1 flex-wrap">
              <input
                :value="minorPhoneParts[0]"
                type="tel"
                placeholder="010"
                maxlength="3"
                class="cncl-field cncl-field--phone-part"
                :readonly="minorAuthConfirmed"
                @input="setMinorPhonePart(0, $event.target.value)"
              />
              <span class="text-gray-400 shrink-0">-</span>
              <input
                :value="minorPhoneParts[1]"
                type="tel"
                placeholder="1234"
                maxlength="4"
                class="cncl-field cncl-field--phone-part"
                :readonly="minorAuthConfirmed"
                @input="setMinorPhonePart(1, $event.target.value)"
              />
              <span class="text-gray-400 shrink-0">-</span>
              <input
                :value="minorPhoneParts[2]"
                type="tel"
                placeholder="5678"
                maxlength="4"
                class="cncl-field cncl-field--phone-part"
                :readonly="minorAuthConfirmed"
                @input="setMinorPhonePart(2, $event.target.value)"
              />
              <button
                v-if="!minorAuthConfirmed"
                type="button"
                :disabled="!canSendMinorAuth"
                class="cncl-btn-primary disabled:opacity-50 shrink-0"
                @click="sendMinorAuth"
              >
                {{ minorAuthSent ? '인증번호 재발송' : '인증번호 발송' }}
              </button>
              <span v-else class="text-sm text-teal-600 font-medium shrink-0">인증 완료</span>
            </div>

            <!-- SMS 인증번호 입력 영역 -->
            <div v-if="minorAuthSent && !minorAuthConfirmed" class="mt-2 flex items-center gap-2">
              <input
                v-model="minorAuthCode"
                type="text"
                placeholder="인증번호 입력"
                maxlength="6"
                class="cncl-field flex-1"
              />
              <span class="text-sm text-orange-600 shrink-0">남은시간 {{ minorTimerDisplay }}</span>
              <button
                type="button"
                :disabled="!minorAuthCode || minorAuthCode.length !== 6"
                class="cncl-btn-primary disabled:opacity-50 shrink-0"
                @click="confirmMinorAuth"
              >
                인증번호 확인
              </button>
            </div>
            <p class="cncl-hint mt-1">SMS 인증: M전산 모듈 연동 예정</p>
          </div>
        </div>
      </div>

      <!-- 법정대리인 안내사항 확인 및 동의 -->
      <div class="mt-4 pt-3 border-t border-gray-200">
        <label class="flex items-start gap-2 cursor-pointer">
          <input v-model="form.minorAgent.agree" type="checkbox" class="rounded mt-0.5" />
          <span class="text-sm">본인은 안내사항을 확인하였습니다. (필수)</span>
        </label>
      </div>

      <!-- 구비서류 (미성년자) -->
      <div class="cncl-form-row mt-4">
        <span class="cncl-label">구비서류</span>
        <div class="cncl-input">
          <button type="button" class="cncl-btn-outline" @click="onDocScan">스캔하기</button>
          <p class="cncl-hint mt-1">스캔 연동 예정</p>
        </div>
      </div>
    </div>

    <!-- ⑥ 대리인 위임 정보 (법인 + 방문고객 대리인) -->
    <div v-if="showAgentInfo" class="cncl-form-block cncl-form-block--sub">
      <h4 class="font-medium mb-4">대리인 위임 정보</h4>
      <div class="cncl-form-rows">
        <div class="cncl-form-row">
          <span class="cncl-label">위임받은 고객 이름 <span class="text-red-500">*</span></span>
          <div class="cncl-input">
            <input v-model="form.agentInfo.custName" type="text" placeholder="이름" class="cncl-field" />
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">생년월일 <span class="text-red-500">*</span></span>
          <div class="cncl-input">
            <input v-model="form.agentInfo.birthDate" type="text" placeholder="8자리(YYYYMMDD)" maxlength="8" class="cncl-field" />
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">성별</span>
          <div class="cncl-input flex gap-3 pt-1">
            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.agentInfo.gender" type="radio" value="M" name="agentGender" class="rounded-full" />
              <span>남</span>
            </label>
            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.agentInfo.gender" type="radio" value="F" name="agentGender" class="rounded-full" />
              <span>여</span>
            </label>
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">신청인과의 관계 <span class="text-red-500">*</span></span>
          <div class="cncl-input">
            <input v-model="form.agentInfo.relation" type="text" placeholder="관계 입력" class="cncl-field" />
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">연락처 <span class="text-red-500">*</span></span>
          <div class="cncl-input flex items-center gap-1 flex-wrap">
            <input
              :value="agentPhoneParts[0]"
              type="tel"
              placeholder="앞자리"
              maxlength="4"
              class="cncl-field cncl-field--phone-part"
              @input="setAgentPhonePart(0, $event.target.value)"
            />
            <span class="text-gray-400 shrink-0">-</span>
            <input
              :value="agentPhoneParts[1]"
              type="tel"
              placeholder="1234"
              maxlength="4"
              class="cncl-field cncl-field--phone-part"
              @input="setAgentPhonePart(1, $event.target.value)"
            />
            <span class="text-gray-400 shrink-0">-</span>
            <input
              :value="agentPhoneParts[2]"
              type="tel"
              placeholder="5678"
              maxlength="4"
              class="cncl-field cncl-field--phone-part"
              @input="setAgentPhonePart(2, $event.target.value)"
            />
          </div>
        </div>
      </div>
    </div>

    <!-- ⑦ 해지 후 연락처 -->
    <h4 class="cncl-section-title">해지 후 연락처</h4>
    <div class="cncl-form-block">
      <div class="cncl-form-rows">
        <div class="cncl-form-row">
          <span class="cncl-label">연락 전화번호 <span class="text-red-500">*</span></span>
          <div class="cncl-input flex items-center gap-1 flex-wrap">
            <input
              :value="contactPhoneParts[0]"
              type="tel"
              placeholder="010"
              maxlength="4"
              class="cncl-field cncl-field--phone-part"
              @input="setContactPhonePart(0, $event.target.value)"
            />
            <span class="text-gray-400 shrink-0">-</span>
            <input
              :value="contactPhoneParts[1]"
              type="tel"
              placeholder="1234"
              maxlength="4"
              class="cncl-field cncl-field--phone-part"
              @input="setContactPhonePart(1, $event.target.value)"
            />
            <span class="text-gray-400 shrink-0">-</span>
            <input
              :value="contactPhoneParts[2]"
              type="tel"
              placeholder="5678"
              maxlength="4"
              class="cncl-field cncl-field--phone-part"
              @input="setContactPhonePart(2, $event.target.value)"
            />
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">해지 후 연락 수단</span>
          <div class="cncl-input flex gap-2">
            <label class="cncl-radio-box" :class="{ 'cncl-radio-box--selected': form.contactMethod === 'P' }">
              <input v-model="form.contactMethod" type="radio" value="P" name="contactMethod" class="rounded-full shrink-0" />
              <span class="text-sm whitespace-nowrap">우편</span>
            </label>
            <label class="cncl-radio-box" :class="{ 'cncl-radio-box--selected': form.contactMethod === 'E' }">
              <input v-model="form.contactMethod" type="radio" value="E" name="contactMethod" class="rounded-full shrink-0" />
              <span class="text-sm whitespace-nowrap">이메일</span>
            </label>
          </div>
        </div>
        <div v-if="form.contactMethod === 'E'" class="cncl-form-row">
          <span class="cncl-label">이메일</span>
          <div class="cncl-input flex items-center gap-1">
            <input v-model="form.email" type="text" placeholder="아이디" class="cncl-field flex-1" />
            <span class="px-1 text-gray-600 shrink-0">@</span>
            <input v-model="form.emailDomain" type="text" placeholder="도메인" class="cncl-field flex-1" />
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">우편번호</span>
          <div class="cncl-input flex gap-2">
            <input v-model="form.post" type="text" placeholder="우편번호" class="cncl-field flex-1" />
            <button type="button" class="cncl-btn-outline shrink-0" @click="postcodeOpen = true">우편번호 찾기</button>
            <McpPostcodePop v-model:open="postcodeOpen" @select="onPostcodeSelect" />
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">주소</span>
          <div class="cncl-input space-y-1">
            <input v-model="form.address" type="text" placeholder="주소" class="cncl-field w-full" />
            <input v-model="form.addressDtl" type="text" placeholder="상세주소" class="cncl-field w-full" />
          </div>
        </div>
      </div>
    </div>

    <!-- ⑧ 가입유형 -->
    <h4 class="cncl-section-title">가입유형 선택</h4>
    <div class="cncl-form-block">
      <div class="cncl-form-rows">
        <div class="cncl-form-row">
          <span class="cncl-label">대리점 <span class="text-red-500">*</span></span>
          <div class="cncl-input">
            <select v-model="form.agencyCode" class="cncl-field cncl-field--select w-full">
              <option value="">대리점 선택</option>
              <option v-for="a in agencyOptions" :key="a.value" :value="a.value">{{ a.label }}</option>
            </select>
          </div>
        </div>
        <div class="cncl-form-row">
          <span class="cncl-label">개통일자</span>
          <div class="cncl-input">
            <input v-model="form.activationDate" type="text" placeholder="조회 후 자동 입력" class="cncl-field bg-gray-50" readonly />
          </div>
        </div>
      </div>
    </div>

    <!-- ⑨ 약관 동의 -->
    <h4 class="cncl-section-title">약관 동의</h4>
    <div class="cncl-form-block">
      <div class="space-y-3">
        <label class="flex items-center gap-2 cursor-pointer p-3 border border-gray-200 rounded-lg bg-gray-50">
          <input v-model="allTermsAgreed" type="checkbox" class="rounded" @change="onAllTermsChange" />
          <span class="font-medium text-sm">약관 항목에 모두 동의합니다. (필수)</span>
        </label>
        <p class="text-xs text-gray-500 pl-1">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</p>
        <label class="flex items-start gap-2 cursor-pointer pl-4">
          <input v-model="form.requiredTermsAgreed" type="checkbox" class="rounded mt-0.5" @change="onTermChange" />
          <span class="text-sm">[필수] 서비스 해지 동의 — 고객님이 신청한 서비스 해지 처리에 동의합니다.</span>
        </label>
        <label class="flex items-start gap-2 cursor-pointer pl-4">
          <input v-model="form.optionalTermsAgreed" type="checkbox" class="rounded mt-0.5" @change="onTermChange" />
          <span class="text-sm">[선택] 개인정보 제3자 제공 동의 — 해지 처리를 위한 개인정보 제공에 동의합니다.</span>
        </label>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted, onUnmounted } from 'vue'
import { useCancelFormStore } from '@/stores/cancel_form'
import { getJoinInfo, getAgencies } from '@/api/cancel'
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
  { value: 'NM', label: '미성년자(19세 미만)' },
  { value: 'FN', label: '외국인(Foreigner)' },
  { value: 'FNM', label: '외국인(Foreigner) 미성년자' },
  { value: 'JP', label: '법인' },
  { value: 'PP', label: '공공기관' },
]

const agencyOptions = ref([])

const form = ref({
  agencyCode: '',
  custType: '',
  visitType: 'self',
  name: '',
  birthDate: '',
  corporateNo: '',
  businessNo: '',
  representName: '',
  idDocType: '',
  idDocIssueDate: '',
  idDocLicenseNo: '',
  idDocLicenseArea: '',
  phoneAuthCompleted: false,
  phone: '',
  contactPhone: '',
  contactMethod: 'P',
  email: '',
  emailDomain: '',
  post: '',
  address: '',
  addressDtl: '',
  activationDate: '',
  requiredTermsAgreed: false,
  optionalTermsAgreed: false,
  minorAgent: { name: '', birthDate: '', relation: '', phone: '', agree: false },
  agentInfo: { custName: '', birthDate: '', gender: '', phone: '', relation: '' },
})

const allTermsAgreed = ref(false)

function onAllTermsChange() {
  form.value.requiredTermsAgreed = allTermsAgreed.value
  form.value.optionalTermsAgreed = allTermsAgreed.value
}
function onTermChange() {
  allTermsAgreed.value = form.value.requiredTermsAgreed && form.value.optionalTermsAgreed
}

// ── 전화번호 3분할 유틸 ──────────────────────────────
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

const contactPhoneParts = computed(() => parsePhone(form.value.contactPhone))
function setContactPhonePart(index, value) {
  const digits = (value || '').replace(/\D/g, '').slice(0, index === 0 ? 4 : 4)
  const parts = [...parsePhone(form.value.contactPhone)]
  parts[index] = digits
  form.value.contactPhone = parts.filter(Boolean).join('-')
}

const minorPhoneParts = computed(() => parsePhone(form.value.minorAgent.phone))
function setMinorPhonePart(index, value) {
  const digits = (value || '').replace(/\D/g, '').slice(0, index === 0 ? 3 : 4)
  const parts = [...parsePhone(form.value.minorAgent.phone)]
  parts[index] = digits
  form.value.minorAgent.phone = parts.filter(Boolean).join('-')
}

const agentPhoneParts = computed(() => parsePhone(form.value.agentInfo.phone))
function setAgentPhonePart(index, value) {
  const digits = (value || '').replace(/\D/g, '').slice(0, 4)
  const parts = [...parsePhone(form.value.agentInfo.phone)]
  parts[index] = digits
  form.value.agentInfo.phone = parts.filter(Boolean).join('-')
}

// ── computed ──────────────────────────────────────────
const isCorporate = computed(() => ['JP', 'PP'].includes(form.value.custType))
const isForeigner = computed(() => ['FN', 'FNM'].includes(form.value.custType))
const showVisitType = computed(() => isCorporate.value)
const showMinorAgent = computed(() => ['NM', 'FNM'].includes(form.value.custType))
const showAgentInfo = computed(() => form.value.visitType === 'agent' && isCorporate.value)

const fullPhoneDigits = computed(() => (form.value.phone || '').replace(/\D/g, ''))
const canAuth = computed(() => {
  if (!form.value.name || fullPhoneDigits.value.length !== 11) return false
  if (isCorporate.value) return !!form.value.corporateNo
  return !!form.value.birthDate && form.value.birthDate.length === 8
})

const isComplete = computed(() => {
  if (!form.value.agencyCode) return false
  if (!form.value.phoneAuthCompleted) return false
  if (!form.value.name) return false
  if (!form.value.contactPhone || form.value.contactPhone.replace(/\D/g, '').length < 9) return false
  if (!form.value.requiredTermsAgreed) return false
  if (isCorporate.value && !form.value.representName) return false
  if (showMinorAgent.value) {
    const m = form.value.minorAgent
    if (!m.name || !m.birthDate || !m.relation || !m.phone || !m.agree) return false
  }
  if (showAgentInfo.value) {
    const a = form.value.agentInfo
    if (!a.custName || !a.birthDate || !a.phone || !a.relation) return false
  }
  return true
})

// ── 법정대리인 SMS 인증 ────────────────────────────────
const minorAuthSent = ref(false)
const minorAuthConfirmed = ref(false)
const minorAuthCode = ref('')
const minorAuthTimer = ref(0)
let timerInterval = null

const minorTimerDisplay = computed(() => {
  const m = Math.floor(minorAuthTimer.value / 60).toString().padStart(2, '0')
  const s = (minorAuthTimer.value % 60).toString().padStart(2, '0')
  return `${m}:${s}`
})

const canSendMinorAuth = computed(() => {
  const ma = form.value.minorAgent
  return !!(ma.name && ma.birthDate?.length === 8 && ma.relation &&
    (ma.phone || '').replace(/\D/g, '').length === 11)
})

function startMinorTimer() {
  clearInterval(timerInterval)
  minorAuthTimer.value = 180
  timerInterval = setInterval(() => {
    if (minorAuthTimer.value > 0) {
      minorAuthTimer.value--
    } else {
      clearInterval(timerInterval)
    }
  }, 1000)
}

function sendMinorAuth() {
  if (!canSendMinorAuth.value) return
  // M전산 SMS 발송 연동 예정
  minorAuthSent.value = true
  minorAuthCode.value = ''
  startMinorTimer()
  alert('인증번호가 발송되었습니다.')
}

function confirmMinorAuth() {
  if (!minorAuthCode.value || minorAuthCode.value.length !== 6) {
    alert('인증번호는 6자리 숫자로 입력해 주세요.')
    return
  }
  if (minorAuthTimer.value === 0) {
    alert('인증번호 유효시간이 종료되었습니다.\n[인증번호 재발송] 버튼을 클릭하시면, 인증번호가 재발송 됩니다.')
    return
  }
  // 실제 SMS 인증 확인 연동 예정 — 개발 중 임시 성공 처리
  minorAuthConfirmed.value = true
  clearInterval(timerInterval)
  alert('인증이 완료되었습니다.')
}

onUnmounted(() => clearInterval(timerInterval))

// ── 신분증 / 구비서류 스캔 stub ──────────────────────
function onIdScan() {
  if (!form.value.idDocType) { alert('신분증 유형을 선택해 주세요.'); return }
  alert('신분증 스캔 연동 예정입니다.')
}
function onDocScan() {
  alert('구비서류 스캔 연동 예정입니다.')
}

// ── 휴대폰 인증 ───────────────────────────────────────
async function doPhoneAuth() {
  if (form.value.phoneAuthCompleted) return
  try {
    const data = await getJoinInfo({
      name: (form.value.name || '').trim(),
      ctn: fullPhoneDigits.value,
    })
    if (data && data.success !== false) {
      form.value.phoneAuthCompleted = true
      if (data.addr) form.value.address = data.addr
      if (data.email) {
        const parts = data.email.split('@')
        form.value.email = parts[0] || ''
        form.value.emailDomain = parts[1] || ''
      }
      if (data.initActivationDate) form.value.activationDate = data.initActivationDate
      if (data.ncn) form.value.ncn = data.ncn
      if (data.custId) form.value.custId = data.custId
      formStore.setCustomerForm(form.value)
      alert('휴대폰번호 인증이 완료되었습니다.')
    } else {
      alert(data?.message || '고객 정보와 휴대폰번호가 일치하지 않습니다.\n휴대폰번호를 다시 확인해 주세요.')
    }
  } catch (err) {
    alert(err?.message || '고객 정보와 휴대폰번호가 일치하지 않습니다.\n휴대폰번호를 다시 확인해 주세요.')
  }
}

// ── watch / lifecycle ────────────────────────────────
watch(form, (v) => {
  formStore.setCustomerForm(v)
  formStore.setAgencyCode(v.agencyCode)
  emit('complete', isComplete.value)
}, { deep: true })

watch(
  () => form.value.custType,
  (custType) => {
    if (custType === 'NA' || custType === 'FN') form.value.visitType = 'self'
    if (custType === 'NM' || custType === 'FNM') form.value.visitType = 'agent'
  },
)

onMounted(async () => {
  form.value.agencyCode = formStore.agencyCode || ''
  const saved = formStore.customerForm
  if (saved && Object.keys(saved).length) {
    form.value = {
      ...form.value,
      ...saved,
      agencyCode: form.value.agencyCode || saved.agencyCode || formStore.agencyCode || '',
      minorAgent: { birthDate: '', agree: false, ...form.value.minorAgent, ...saved.minorAgent },
      agentInfo: { ...form.value.agentInfo, ...saved.agentInfo },
    }
    allTermsAgreed.value = form.value.requiredTermsAgreed && form.value.optionalTermsAgreed
  }
  try {
    const res = await getAgencies()
    if (res && res.agencies) agencyOptions.value = res.agencies
  } catch (e) {
    console.warn('대리점 목록 조회 실패:', e)
  }
  emit('complete', isComplete.value)
})

// ── step expose ───────────────────────────────────────
const data = async () => '0'

const save = async () => {
  if (!form.value.agencyCode) { alert('대리점을 선택해 주세요.'); return false }
  if (!form.value.phoneAuthCompleted) { alert('휴대폰 번호 인증을 완료해 주세요.'); return false }
  if (!form.value.name) { alert('이름을 입력해 주세요.'); return false }
  if (isCorporate.value && !form.value.representName) { alert('대표자명을 입력해 주세요.'); return false }
  if (!form.value.contactPhone || form.value.contactPhone.replace(/\D/g, '').length < 9) {
    alert('연락 전화번호를 입력해 주세요.')
    return false
  }
  if (!form.value.requiredTermsAgreed) { alert('[필수] 약관에 동의해 주세요.'); return false }
  if (showMinorAgent.value) {
    const m = form.value.minorAgent
    if (!m.name || !m.birthDate || !m.relation || !m.phone) { alert('법정대리인 정보를 모두 입력해 주세요.'); return false }
    if (!m.agree) { alert('법정대리인 안내사항에 동의해 주세요.'); return false }
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
.cncl-section-title {
  @apply text-base font-bold text-gray-800 pb-2 mb-3 border-b border-gray-300;
}
.cncl-form-block {
  @apply mb-5;
}
.cncl-form-block--sub {
  @apply p-4 rounded-lg border border-gray-200 bg-gray-50;
}
.cncl-form-rows {
  @apply space-y-3;
}
.cncl-form-row {
  @apply flex items-start gap-4;
}
.cncl-label {
  width: 140px;
  min-width: 140px;
  @apply shrink-0 text-sm font-medium text-gray-700 pt-2;
}
.cncl-input {
  @apply flex-1 min-w-0;
}
.cncl-field {
  @apply rounded-lg border border-gray-300 px-3 py-2 text-sm focus:border-teal-500 focus:ring-1 focus:ring-teal-500;
}
.cncl-field--select {
  @apply bg-white;
}
.cncl-field--phone-part {
  width: 6.5ch;
  min-width: 3.5rem;
}
.cncl-radio-box {
  @apply flex items-center gap-1.5 cursor-pointer py-1.5 px-3 rounded-lg border border-gray-200 text-sm transition-colors hover:bg-gray-50;
}
.cncl-radio-box--selected {
  @apply border-teal-500 bg-teal-50 text-teal-700 font-medium;
}
.cncl-btn-primary {
  @apply px-4 py-2 rounded-lg bg-teal-600 text-white text-sm font-medium;
}
.cncl-btn-outline {
  @apply px-3 py-2 rounded-lg border border-teal-600 text-teal-600 text-sm font-medium hover:bg-teal-50;
}
.cncl-hint {
  @apply text-xs text-gray-500;
}
</style>
