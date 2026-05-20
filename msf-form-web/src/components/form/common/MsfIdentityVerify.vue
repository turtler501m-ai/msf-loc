<template>
  <div>
    <MsfTitleArea :title="computedTitle" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup
        label="신분증"
        tag="div"
        required
        v-if="showIdentityCertType && model.serviceType !== 'TR_CUSTOMER'"
      >
        <MsfChip
          v-model="model.identityCertTypeCd"
          name="inp-idCardCertType"
          :data="identityCertTypeData"
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
            groupCode="RCP2006"
            placeholder="신분증 선택"
            title="신분증 선택"
            :selectPop="true"
            :disabled="!showIdentityCertType && model.isVerified"
            class="ut-w-300"
          />
          <MsfButton
            variant="subtle"
            :disabled="!model.identityTypeCd || (!showIdentityCertType && model.isVerified)"
            @click="isIdCardScanModalOpen = true"
            >스캔하기</MsfButton
          >
          <MsfButton
            variant="subtle"
            :disabled="!model.identityTypeCd || (!showIdentityCertType && model.isVerified)"
            @click="isIdCardScanModal2Open = true"
            >스캔하기</MsfButton
          >
        </MsfStack>
        <div v-if="model.isScanVerified" class="ut-mt-8 ut-p-12 ut-bg-gray-50 ut-radius-8">
          <p class="ut-text-primary ut-weight-bold">신분증 스캔 완료</p>
          <p v-if="model.identityTypeNm" class="ut-mt-4">종류: {{ model.identityTypeNm }}</p>
          <p v-if="model.identityIssuDate" class="ut-mt-2">
            발급일자: {{ model.identityIssuDate }}
          </p>
        </div>
        <MsfStack type="field" v-if="model.identityTypeCd">
          <!-- 모든 신분증 공통: 발급 일자 (오늘 포함 과거일자만 선택 가능하도록 max-date 설정) -->
          <MsfDateInput
            v-model="model.identityIssuDate"
            :max-date="new Date()"
            :disabled="!showIdentityCertType && model.isVerified"
          />
          <!-- 운전면허증(코드 '02' 가정)인 경우에만 노출: 면허지역, 면허번호 -->
          <template v-if="model.identityTypeCd === '02'">
            <MsfSelect
              title="면허지역"
              v-model="model.identityIssuRegion"
              :options="licenseRegionCodes"
              placeholder="면허지역"
              :disabled="!showIdentityCertType && model.isVerified"
              class="ut-w-200"
            />
            <MsfNumberInput
              v-model="model.driveLicnsNo"
              maxlength="15"
              placeholder="면허번호"
              :readonly="!showIdentityCertType && model.isVerified"
              class="ut-w-240"
            />
          </template>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>

    <!-- 신분증 목록 조회 모달 -->
    <MsfIdCardListModal
      v-model="isIdCardListModalOpen"
      :agentCd="model.agentCd"
      @confirm="onIdCardSelect"
    />
    <!-- 모바일 신분증 인증 모달 -->
    <MsfMobileIdModal v-model="isMobileIdModalOpen" @confirm="onMobileIdConfirm" />
    <!-- 안면 인증 모달 -->
    <MsfFaceAuthModal
      v-model="isFaceAuthModalOpen"
      :join-type="model.value?.productType"
      :customer-type="model.value?.cstmrTypeCd"
      :biz-number="model.value?.cstmrJuridicalBizNo1"
      :minor-agent-name="model.value?.minorAgentNm"
      :minor-agent-birth="model.value?.minorAgentBirth"
      @close="onCloseFaceAuth"
    />
    <!-- 신분증 스캔 모달 -->
    <MsfIdCardScanModal
      v-model="isIdCardScanModalOpen"
      :identityTypeCd="model.identityTypeCd"
      :identityTypeNm="selectedIdentityTypeNm"
      @confirm="onIdCardScanConfirm"
    />
    <!-- 신분증 스캔 모달 이게 ㄹㅇ -->
    <MsfIdCardScanModal2
      v-model="isIdCardScanModal2Open"
      :identityTypeCd="model.identityTypeCd"
      :identityTypeNm="selectedIdentityTypeNm"
      @confirm="onIdCardScanConfirm"
    />
  </div>
</template>
<script setup>
import { ref, defineModel, defineProps, onMounted, watch, computed } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { formatLocalDateTime } from '@/libs/utils/date.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import MsfIdCardListModal from './popups/MsfIdCardListModal.vue'
import MsfMobileIdModal from './popups/MsfMobileIdModal.vue'
import MsfFaceAuthModal from './popups/MsfFaceAuthModal.vue'
import MsfIdCardScanModal from './popups/MsfIdCardScanModal.vue'
import MsfIdCardScanModal2 from '@/components/form/common/popups/MsfIdCardScanModal2.vue'

const props = defineProps({
  title: { type: String, default: '신분증 확인' },
  authFlags: { type: Object, default: () => ({}) },
  // true: 신분증 인증유형(K-NOTE/모바일/안면/예외) 선택 영역 노출.
  // false: 해지처럼 인증유형 선택 UI 없이 신분증 스캔만 받는 화면에서 사용한다.
  //        인증유형 기본값은 각 화면 store에서 관리한다.
  showIdentityCertType: { type: Boolean, default: true },
})
const model = defineModel({ type: Object, required: true })
const emit = defineEmits(['scanConfirm'])

const licenseRegionCodes = ref([])
const fathCertIdTypeCodes = ref([])
const identityTypeCodes = ref([]) // 전체 신분증 코드 목록 추가
const rawIdentityCertTypeCodes = ref([])

const identityCertTypeData = computed(() =>
  rawIdentityCertTypeCodes.value.map((item) => ({ ...item, disabled: !!model.value?.isVerified })),
)
const isIdCardListModalOpen = ref(false)
const isMobileIdModalOpen = ref(false)
const isFaceAuthModalOpen = ref(false)
const isIdCardScanModalOpen = ref(false)
const isIdCardScanModal2Open = ref(false)

// 현재 선택된 신분증 코드(identityTypeCd)에 해당하는 명칭 반환
const selectedIdentityTypeNm = computed(() => {
  if (!model.value.identityTypeCd) return ''
  const found = identityTypeCodes.value.find((item) => item.code === model.value.identityTypeCd)
  return found ? found.title : ''
})

const isMinor = computed(() => ['NM', 'FM'].includes(model.value.cstmrTypeCd))

const computedTitle = computed(() => {
  if (props.title === '신분증 확인' && isMinor.value) {
    return '신분증 확인(법정대리인)'
  }
  return props.title
})

// 인증 방식 변경 시 인증 관련 상태만 초기화 (스캔 정보와 간섭 배제)
watch(
  () => model.value.identityCertTypeCd,
  () => {
    // 인증유형 UI가 없는 화면(현재 해지)은 신분증 스캔이 주 흐름이다.
    // 이 화면에서 identityCertTypeCd가 store 초기값/서버값으로 보정되더라도
    // 고객명, 생년월일, 법정대리인 정보를 지우면 안 되므로 초기화 로직을 타지 않는다.
    if (!props.showIdentityCertType) return

    model.value.isVerified = false

    if (isMinor.value) {
      // 미성년자인 경우: 법정대리인 정보 초기화
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
      // 일반 고객인 경우: 가입자 정보 초기화
      // 법인/공공기관인 경우 이름(상호명)은 유지한다.
      if (!['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
        model.value.cstmrNm = ''
      }
      model.value.cstmrNativeRrn1 = ''
      model.value.cstmrNativeRrn2 = ''
      model.value.cstmrForeignerRrn1 = ''
      model.value.cstmrForeignerRrn2 = ''
      if ('userBirthDate' in model.value) model.value.userBirthDate = ''
      if ('userGender' in model.value) model.value.userGender = ''
    }
  },
)

onMounted(async () => {
  const [licRegion, fathCert, fathPolicy, idTypes, certTypes] = await Promise.all([
    getCommonCodeList('driverLicenseAgency'),
    getCommonCodeList('fathCertIdType'),
    getCommonCodeList('fathCertPolicy'),
    getCommonCodeList('RCP2006'), // 일반 신분증 코드 목록 추가
    getCommonCodeList('IDENTITY_CERT_TYPE_CD'),
  ])
  licenseRegionCodes.value = (licRegion || []).map((item) => ({
    ...item,
    label: item.title,
    value: item.code,
  }))
  fathCertIdTypeCodes.value = (fathCert || []).map((item) => item.code)

  // 모든 신분증 타입을 하나의 목록으로 합쳐서 명칭 조회용으로 사용
  identityTypeCodes.value = [...(idTypes || []), ...(fathCert || [])]

  rawIdentityCertTypeCodes.value = (certTypes || []).map((item) => ({
    value: item.code,
    label: item.title,
  }))

  console.log('>>> 안면인증 관련 정책 (fathCertPolicy):', fathPolicy)
})

const handleAuthClick = () => {
  if (model.value.identityCertTypeCd === 'K') {
    if (!model.value.agentCd && !model.value.agency) {
      showAlert('대리점을 먼저 선택해 주세요.')
      return
    }
    isIdCardListModalOpen.value = true
  } else if (model.value.identityCertTypeCd === 'M') {
    isMobileIdModalOpen.value = true
  } else if (model.value.identityCertTypeCd === 'F') {
    isFaceAuthModalOpen.value = true
  }
}

const onIdCardSelect = (selected) => {
  console.log('선택된 신분증 목록 및 상세정보:', selected)
  if (selected) {
    const {
      custNm,
      realCustIdntNo,
      realIssuDate,
      knoteIdentityTypeCd,
      knoteIdentityScanCstmrNm,
      knoteIdentityEssNo,
      knoteIdentityScanDt,
      knoteScanId,
    } = selected

    // 주민번호 분리 (앞 6자리, 뒤 7자리)
    const rrn1 = realCustIdntNo?.substring(0, 6) || ''
    const rrn2 = realCustIdntNo?.substring(6) || ''

    if (isMinor.value) {
      // 미성년자인 경우 법정대리인 필드에 저장
      if (custNm) model.value.repName = custNm
      if (model.value.cstmrTypeCd === 'NM') {
        model.value.repRegistrationNo1 = rrn1
        model.value.repRegistrationNo2 = rrn2
      } else if (model.value.cstmrTypeCd === 'FM') {
        model.value.repForeignerNo1 = rrn1
        model.value.repForeignerNo2 = rrn2
      }
    } else {
      // 일반 고객인 경우 가입자 필드에 저장
      if (custNm) {
        // 법인/공공기관인 경우 대표자명에 세팅 (이름은 직접 입력)
        if (['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
          model.value.cstmrJuridicalRepNm = custNm
        } else {
          model.value.cstmrNm = custNm
        }
      }
      if (['NA', 'NM'].includes(model.value.cstmrTypeCd)) {
        model.value.cstmrNativeRrn1 = rrn1
        model.value.cstmrNativeRrn2 = rrn2
      } else if (['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
        model.value.cstmrForeignerRrn1 = rrn1
        model.value.cstmrForeignerRrn2 = rrn2
      }
    }

    // 신분증 정보 세팅
    if (realIssuDate) model.value.identityIssuDate = realIssuDate
    // knoteIdentityTypeCd: 1(주민), 5(면허) -> 01, 02 매핑
    if (knoteIdentityTypeCd) {
      model.value.identityTypeCd = knoteIdentityTypeCd === '1' ? '01' : '02'
    }

    // K-NOTE 스캔 관련 추가 정보 저장
    if (knoteIdentityScanCstmrNm) model.value.knoteIdentityScanCstmrNm = knoteIdentityScanCstmrNm
    if (knoteIdentityEssNo) model.value.knoteIdentityEssNo = knoteIdentityEssNo
    if (knoteIdentityTypeCd) model.value.knoteIdentityTypeCd = knoteIdentityTypeCd
    if (knoteIdentityScanDt) {
      model.value.knoteIdentityScanDt = formatLocalDateTime(knoteIdentityScanDt)
    }
    if (knoteScanId) model.value.knoteScanId = knoteScanId
  }

  // 인증 완료 처리 (안면인증 바로 넘어가는 로직 제거)
  model.value.isVerified = true
}

const onMobileIdConfirm = () => {
  console.log('모바일 신분증 인증 완료')
  model.value.isVerified = true
}

const onCloseFaceAuth = (result) => {
  console.log('안면 인증 완료')
  model.value.isVerified = true
}

// 실제 스캔 API와 테스트 샘플에서 내려오는 식별번호 필드명이 달라 K-NOTE 저장용으로 흡수한다.
const resolveScanRrn = (data) =>
  data.rrn ||
  data.essNo ||
  data.identityEssNo ||
  data.knoteIdentityEssNo ||
  `${data.rrn1 || ''}${data.rrn2 || ''}`

const onIdCardScanConfirm = (data) => {
  console.log('신분증 스캔 파일:', data)
  if (data) {
    // 스캔 응답에 발급일자가 포함된 경우만 갱신한다.
    // 미포함 응답 때문에 사용자가 이미 입력한 발급일자를 빈 값으로 덮어쓰지 않기 위함이다.
    if ('identityIssuDate' in data) {
      model.value.identityIssuDate = data.identityIssuDate || ''
    }
    // 스캔된 신분증 정보 저장
    if (data.identityTypeNm) {
      model.value.identityTypeNm = data.identityTypeNm
    }
    if (data.identityTypeCd) {
      model.value.identityTypeCd = data.identityTypeCd
    }

    // K-NOTE 스캔 관련 추가 정보 저장
    if (data.cstmrNm) {
      model.value.knoteIdentityScanCstmrNm = data.cstmrNm
      // 법인/공공기관인 경우 스캔된 성명을 대표자명에 매핑
      if (['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
        model.value.cstmrJuridicalRepNm = data.cstmrNm
      } else {
        // 일반 개인인 경우 가입자 성명에 매핑 (필요시)
        // model.value.cstmrNm = data.cstmrNm
      }
    }
    const scanRrn = resolveScanRrn(data)
    if (scanRrn) {
      model.value.knoteIdentityEssNo = scanRrn

      // 주민번호/외국인번호 분리 및 매핑
      const rrn1 = scanRrn.substring(0, 6)
      const rrn2 = scanRrn.substring(6)

      if (['NA', 'NM'].includes(model.value.cstmrTypeCd)) {
        model.value.cstmrNativeRrn1 = rrn1
        model.value.cstmrNativeRrn2 = rrn2
      } else if (['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
        model.value.cstmrForeignerRrn1 = rrn1
        model.value.cstmrForeignerRrn2 = rrn2
      }
    }
    if (data.identityTypeCd) model.value.knoteIdentityTypeCd = data.identityTypeCd
    if (data.scanDt) {
      model.value.knoteIdentityScanDt = formatLocalDateTime(data.scanDt)
    }
    if (data.scanId) model.value.knoteScanId = data.scanId

    // 화면별 추가 매핑이 필요한 경우 부모 컴포넌트에서 처리하도록 원본 스캔 데이터를 전달한다.
    emit('scanConfirm', data)
  }
  model.value.isScanVerified = true
}

const validate = () => {
  // 인증예외(S)인 경우 무조건 통과
  if (model.value.identityCertTypeCd === 'S') return true

  // 신분증 인증유형 선택 UI가 있는 화면만 K-NOTE/모바일/안면 본인인증 완료 여부를 검사한다.
  // 해지는 인증유형 UI를 숨기고 신분증 스캔을 받는 구조이므로 isVerified를 요구하지 않는다.
  const needsAuth = props.showIdentityCertType && !model.value.isTrCustomer
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
