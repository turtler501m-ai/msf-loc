<template>
  <div>
    <MsfTitleArea :title="computedTitle" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="신분증" tag="div" required v-if="model.serviceType !== 'TR_CUSTOMER'">
        <MsfChip
          v-model="model.identityCertTypeCd"
          name="inp-idCardCertType"
          groupCode="IDENTITY_CERT_TYPE_CD"
          :disabled="model.isVerified || model.isSaved"
          :data="[]"
        >
          <template #endSlot>
            <MsfButton
              variant="subtle"
              :disabled="model.isVerified || model.identityCertTypeCd === 'S'"
              @click="handleAuthClick"
            >
              조회/인증
            </MsfButton>
          </template>
        </MsfChip>
      </MsfFormGroup>

      <MsfFormGroup label="신분증 스캔" tag="div" required>
        <MsfStack type="field">
          <MsfSelect
            v-model="model.identityTypeCd"
            :groupCode="model.identityCertTypeCd === 'F' ? 'fathCertIdType' : 'RCP2006'"
            placeholder="신분증 선택"
            title="신분증 선택"
            :selectPop="true"
            :disabled="model.isScanVerified || model.isSaved"
            class="ut-w-300"
          />
          <MsfButton
            variant="subtle"
            :disabled="model.isScanVerified || model.isSaved || !model.identityTypeCd"
            @click="isIdCardScanModalOpen = true"
            >스캔하기</MsfButton
          >
        </MsfStack>
        <div v-if="model.isScanVerified" class="ut-mt-8 ut-p-12 ut-bg-gray-50 ut-radius-8">
          <p class="ut-text-primary ut-weight-bold">신분증 스캔 완료</p>
          <p v-if="model.identityTypeNm" class="ut-mt-4">종류: {{ model.identityTypeNm }}</p>
          <p v-if="model.identityIssuDate" class="ut-mt-2">발급일자: {{ model.identityIssuDate }}</p>
        </div>
        <MsfStack type="field" v-if="model.identityTypeCd">
          <!-- 모든 신분증 공통: 발급 일자 (오늘 포함 과거일자만 선택 가능하도록 max-date 설정) -->
          <MsfDateInput
            v-model="model.identityIssuDate"
            :max-date="new Date()"
            :disabled="model.isSaved"
          />
          <!-- 운전면허증(코드 '02' 가정)인 경우에만 노출: 면허지역, 면허번호 -->
          <template v-if="model.identityTypeCd === '02'">
            <MsfSelect
              title="면허지역"
              v-model="model.identityIssuRegion"
              :options="licenseRegionCodes"
              placeholder="면허지역"
              class="ut-w-200"
              :disabled="model.isSaved"
            />
            <MsfNumberInput
              v-model="model.driveLicnsNo"
              maxlength="15"
              placeholder="면허번호"
              class="ut-w-240"
              :disabled="model.isSaved"
            />
          </template>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>

    <!-- 신분증 목록 조회 모달 -->
    <MsfIdCardListModal v-model="isIdCardListModalOpen" @confirm="onIdCardSelect" />
    <!-- 모바일 신분증 인증 모달 -->
    <MsfMobileIdModal v-model="isMobileIdModalOpen" @confirm="onMobileIdConfirm" />
    <!-- 안면 인증 모달 -->
    <MsfFaceAuthModal v-model="isFaceAuthModalOpen" @confirm="onFaceAuthConfirm" />
    <!-- 신분증 스캔 모달 -->
    <MsfIdCardScanModal
      v-model="isIdCardScanModalOpen"
      :identityTypeNm="selectedIdentityTypeNm"
      @confirm="onIdCardScanConfirm"
    />
  </div>
</template>
<script setup>
import { ref, defineModel, defineProps, onMounted, watch, computed } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import MsfIdCardListModal from './popups/MsfIdCardListModal.vue'
import MsfMobileIdModal from './popups/MsfMobileIdModal.vue'
import MsfFaceAuthModal from './popups/MsfFaceAuthModal.vue'
import MsfIdCardScanModal from './popups/MsfIdCardScanModal.vue'

const props = defineProps({
  title: { type: String, default: '신분증 확인' },
  authFlags: { type: Object, default: () => ({}) },
})
const model = defineModel({ type: Object, required: true })

const licenseRegionCodes = ref([])
const fathCertIdTypeCodes = ref([])
const identityTypeCodes = ref([]) // 전체 신분증 코드 목록 추가
const isIdCardListModalOpen = ref(false)
const isMobileIdModalOpen = ref(false)
const isFaceAuthModalOpen = ref(false)
const isIdCardScanModalOpen = ref(false)

// 현재 선택된 신분증 코드(identityTypeCd)에 해당하는 명칭 반환
const selectedIdentityTypeNm = computed(() => {
  if (!model.value.identityTypeCd) return ''
  const found = identityTypeCodes.value.find(item => item.code === model.value.identityTypeCd)
  return found ? found.title : ''
})

const isMinor = computed(() => ['NM', 'FM'].includes(model.value.cstmrTypeCd))

const computedTitle = computed(() => {
  if (props.title === '신분증 확인' && isMinor.value) {
    return '신분증 확인(법정대리인)'
  }
  return props.title
})

// 신분증 종류(주민등록증, 운전면허증 등) 변경 시 스캔 상태 초기화
watch(
  () => model.value.identityTypeCd,
  (newVal, oldVal) => {
    if (oldVal && newVal !== oldVal && !model.value.isSaved) {
      model.value.isScanVerified = false
      model.value.identityTypeNm = ''
    }
  },
)

// 인증 방식 변경 시 상태 초기화
watch(
  () => model.value.identityCertTypeCd,
  () => {
    if (!model.value.isSaved) {
      model.value.identityTypeCd = ''
      model.value.identityTypeNm = '' // 스캔된 명칭 초기화
      model.value.isVerified = false
      model.value.isScanVerified = false // 스캔 상태 초기화

      if (isMinor.value) {
        // 미성년자인 경우: 법정대리인 정보 초기화 (인증 시 비활성화되는 항목들)
        model.value.repName = ''
        model.value.repRegistrationNo1 = ''
        model.value.repRegistrationNo2 = ''
        model.value.repForeignerNo1 = ''
        model.value.repForeignerNo2 = ''
        model.value.minorAgentNm = ''
        model.value.minorAgentRelTypeCd = ''
        model.value.minorAgentTelFnNo = ''
        model.value.minorAgentTelMnNo = ''
        model.value.minorAgentTelRnNo = ''
        model.value.repRelation = ''
        model.value.repAgree = false
        if (props.authFlags) props.authFlags.repPhone = false
      } else {
        // 일반 고객인 경우: 가입자 정보 초기화 (인증 시 비활성화되는 항목들)
        model.value.cstmrNm = ''
        model.value.cstmrNativeRrn1 = ''
        model.value.cstmrNativeRrn2 = ''
        model.value.cstmrForeignerRrn1 = ''
        model.value.cstmrForeignerRrn2 = ''
        // 생년월일/성별이 있는 경우(명의변경 등) 함께 초기화
        if ('userBirthDate' in model.value) model.value.userBirthDate = ''
        if ('userGender' in model.value) model.value.userGender = ''
      }
    }
  },
)

onMounted(async () => {
  const [licRegion, fathCert, fathPolicy, idTypes] = await Promise.all([
    getCommonCodeList('LIC_REGION'),
    getCommonCodeList('fathCertIdType'),
    getCommonCodeList('fathCertPolicy'),
    getCommonCodeList('RCP2006'), // 일반 신분증 코드 목록 추가
  ])
  licenseRegionCodes.value = (licRegion || []).map((item) => ({
    ...item,
    label: item.title,
    value: item.code,
  }))
  fathCertIdTypeCodes.value = (fathCert || []).map((item) => item.code)
  
  // 모든 신분증 타입을 하나의 목록으로 합쳐서 명칭 조회용으로 사용
  identityTypeCodes.value = [...(idTypes || []), ...(fathCert || [])]
  
  console.log('>>> 안면인증 관련 정책 (fathCertPolicy):', fathPolicy)
})

const handleAuthClick = () => {
  if (model.value.identityCertTypeCd === 'K') {
    isIdCardListModalOpen.value = true
  } else if (model.value.identityCertTypeCd === 'M') {
    isMobileIdModalOpen.value = true
  } else if (model.value.identityCertTypeCd === 'F') {
    isFaceAuthModalOpen.value = true
  }
}

const onIdCardSelect = (selected) => {
  console.log('선택된 신분증 목록:', selected)
  if (selected) {
    if (isMinor.value) {
      // 미성년자인 경우 법정대리인 필드에 저장
      if (selected.cstmrNm) model.value.repName = selected.cstmrNm
      if (model.value.cstmrTypeCd === 'NM') {
        if (selected.rrn1) model.value.repRegistrationNo1 = selected.rrn1
        if (selected.rrn2) model.value.repRegistrationNo2 = selected.rrn2
      } else if (model.value.cstmrTypeCd === 'FM') {
        if (selected.rrn1) model.value.repForeignerNo1 = selected.rrn1
        if (selected.rrn2) model.value.repForeignerNo2 = selected.rrn2
      }
    } else {
      // 일반 고객인 경우 가입자 필드에 저장
      if (selected.cstmrNm) {
        model.value.cstmrNm = selected.cstmrNm
        // 법인/공공기관인 경우 대표자명에도 세팅
        if (['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
          model.value.cstmrJuridicalRepNm = selected.cstmrNm
        }
      }
      if (['NA', 'NM'].includes(model.value.cstmrTypeCd)) {
        if (selected.rrn1) model.value.cstmrNativeRrn1 = selected.rrn1
        if (selected.rrn2) model.value.cstmrNativeRrn2 = selected.rrn2
      } else if (['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
        if (selected.rrn1) model.value.cstmrForeignerRrn1 = selected.rrn1
        if (selected.rrn2) model.value.cstmrForeignerRrn2 = selected.rrn2
      }
    }

    // 신분증 유형 세팅
    if (selected.identityTypeCd) {
      model.value.identityTypeCd = selected.identityTypeCd
    }
  }

  // 선택된 신분증 유형이 안면인증 대상(fathCertIdType)에 포함되는지 확인
  const needsFaceAuth = fathCertIdTypeCodes.value.includes(model.value.identityTypeCd)

  if (needsFaceAuth) {
    console.log('K-NOTE 인증 후 안면인증 추가 진행 필요')
    isFaceAuthModalOpen.value = true
  } else {
    model.value.isVerified = true
  }
}

const onMobileIdConfirm = () => {
  console.log('모바일 신분증 인증 완료')
  model.value.isVerified = true
}

const onFaceAuthConfirm = () => {
  console.log('안면 인증 완료')
  model.value.isVerified = true
}

const onIdCardScanConfirm = (data) => {
  console.log('신분증 스캔 파일:', data)
  if (data) {
    model.value.identityIssuDate = data.identityIssuDate || ''
    // 스캔된 신분증 정보 저장
    if (data.identityTypeNm) {
      model.value.identityTypeNm = data.identityTypeNm
    }
    if (data.identityTypeCd) {
      model.value.identityTypeCd = data.identityTypeCd
    }

    // K-NOTE 스캔 관련 추가 정보 저장
    if (data.cstmrNm) model.value.knoteIdentityScanCstmrNm = data.cstmrNm
    if (data.rrn) model.value.knoteIdentityEssNo = data.rrn
    if (data.identityTypeCd) model.value.knoteIdentityTypeCd = data.identityTypeCd
    if (data.scanDt) model.value.knoteIdentityScanDt = data.scanDt
    if (data.scanId) model.value.knoteScanId = data.scanId
  }
  model.value.isScanVerified = true
}

const validate = () => {
  // 인증예외(S)인 경우 무조건 통과
  if (model.value.identityCertTypeCd === 'S') return true

  const needsAuth = !model.value.isTrCustomer
  if (needsAuth && !model.value.isVerified) return false

  // 신분증 스캔이 필수인 경우 체크 (인증예외 S가 아닌 모든 경우)
  if (!model.value.isScanVerified) return false

  // 신분증 발급일자 8자리 체크
  if (model.value.identityIssuDate) {
    const pureDate = model.value.identityIssuDate.replace(/[^0-9]/g, '')
    if (pureDate.length !== 8) return false
  } else {
    // 발급일자가 필수라고 가정 (보통 신분증 확인 시 필수임)
    return false
  }

  return true
}

defineExpose({ validate })
</script>
