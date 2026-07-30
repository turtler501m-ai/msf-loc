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
          ref="identityCertTypeCdRef"
          v-model="model.identityCertTypeCd"
          name="inp-idCardCertType"
          :data="identityCertTypeData"
          :readonly="props.disabled"
        >
          <template #endSlot>
            <MsfButton
              ref="authClickBtnRef"
              variant="subtle"
              :disabled="isVerifyLocked"
              @click="handleAuthClick"
            >
              조회/인증
            </MsfButton>
          </template>
        </MsfChip>
      </MsfFormGroup>

      <MsfFormGroup
        label="신분증 정보"
        tag="div"
        required
        v-if="!['JP', 'GO'].includes(model.cstmrTypeCd)"
      >
        <MsfStack type="field">
          <MsfSelect
            ref="identityTypeCdRef"
            v-model="model.identityTypeCd"
            :options="filteredIdentityTypeCodes"
            placeholder="신분증 선택"
            title="신분증 선택"
            :selectPop="true"
            :disabled="isInputLocked"
            class="ut-w-300"
          />
          <!-- TEST_SCAN_SAMPLE_START: temporary test popup trigger. Remove after real OCR rollout. -->
          <!--          <MsfButton-->
          <!--            variant="subtle"-->
          <!--            :disabled="!model.identityTypeCd || (!showIdentityCertType && model.isVerified)"-->
          <!--            @click="isIdCardScanModalOpen = true"-->
          <!--            >스캔하기</MsfButton-->
          <!--          >-->
          <!-- TEST_SCAN_SAMPLE_END -->
          <MsfButton
            v-if="showsIdCardScan"
            variant="subtle"
            :disabled="!model.identityTypeCd || isInputLocked"
            @click="isIdCardScanModalOpen = true"
            :return-file="props.returnFile"
            >스캔하기</MsfButton
          >
        </MsfStack>
        <template v-if="!model.isTrCustomer">
          <div
            v-if="showsIdCardScan && model.isScanVerified"
            class="ut-mt-8 ut-p-12 ut-bg-gray-50 ut-radius-8"
          >
            <p class="ut-text-primary ut-weight-bold">신분증 스캔 완료</p>
            <p v-if="model.identityTypeNm" class="ut-mt-4">종류: {{ model.identityTypeNm }}</p>
            <p v-if="showIdentityDetailFields && model.identityIssuDate" class="ut-mt-2">
              발급일자: {{ model.identityIssuDate }}
            </p>
          </div>
          <MsfStack type="field" v-if="showIdentityDetailFields && model.identityTypeCd">
            <!-- 모든 신분증 공통: 발급 일자 (오늘 포함 과거일자만 선택 가능하도록 max-date 설정) -->
            <MsfDateInput
              ref="identityIssuDateRef"
              v-model="model.identityIssuDate"
              :max-date="new Date()"
              :disabled="isInputLocked"
            />
            <!-- 운전면허증(코드 '02' 가정)인 경우에만 노출: 면허지역, 면허번호 -->
            <template v-if="model.identityTypeCd === '02'">
              <MsfSelect
                ref="identityIssuRegionRef"
                title="면허지역"
                v-model="model.identityIssuRegion"
                :options="licenseRegionCodes"
                placeholder="면허지역"
                :disabled="isInputLocked"
                class="ut-w-200"
              />
              <MsfNumberInput
                ref="driveLicnsNoRef"
                v-model="model.driveLicnsNo"
                maxlength="12"
                placeholder="면허번호"
                :readonly="isInputLocked"
                class="ut-w-240"
              />
            </template>
            <!-- 국가유공자증(코드 '04')인 경우에만 노출: 유공자번호 (저장은 driveLicnsNo 필드에 함) -->
            <template v-if="model.identityTypeCd === '04'">
              <MsfInput
                ref="driveLicnsNo04Ref"
                v-model="model.driveLicnsNo"
                maxlength="30"
                placeholder="유공자번호"
                :readonly="isInputLocked"
                class="ut-w-300"
              />
            </template>
          </MsfStack>
        </template>
      </MsfFormGroup>
    </MsfStack>

    <!-- 신분증 목록 조회 모달 -->
    <MsfIdCardListModal
      v-model="isIdCardListModalOpen"
      :agentCd="model.agentCd"
      :oper-type-cd="resolvedJoinType"
      @confirm="onIdCardSelect"
    />
    <!-- 모바일 신분증 인증 모달 -->
    <MsfMobileIdModal v-model="isMobileIdModalOpen" @confirm="onMobileIdConfirm" />
    <!-- 안면 인증 모달 -->
    <MsfFaceAuthModal
      v-model="isFaceAuthModalOpen"
      :res-no="model.resNo"
      :agent-code="model.agentCd"
      :join-type="resolvedJoinType"
      :customer-type="model.cstmrTypeCd"
      :visit-type="model.cstmrVisitTypeCd"
      :biz-number="cstmrJuridicalBizNo"
      :minor-agent-name="model.minorAgentNm || juridicalModel?.minorAgentNm"
      :minor-agent-birth="model.agentBirthDate || juridicalModel?.agentBirthDate"
      @close="onCloseFaceAuth"
    />
    <!-- 신분증 스캔 모달 -->
    <!-- TEST_SCAN_SAMPLE_START: temporary test popup. Remove after real OCR rollout. -->
    <MsfIdCardScanModal
      v-model="isIdCardScanModalOpen"
      :identityTypeCd="model.identityTypeCd"
      :identityTypeNm="selectedIdentityTypeNm"
      :return-file="props.returnFile"
      :fileCategory="fileCategory"
      @confirm="onIdCardScanConfirm"
    />
    <!-- TEST_SCAN_SAMPLE_END -->
    <!-- 신분증 스캔 모달 이게 ㄹㅇ -->
    <!--    <MsfIdCardScanModal2-->
    <!--      v-model="isIdCardScanModal2Open"-->
    <!--      :identityTypeCd="model.identityTypeCd"-->
    <!--      :identityTypeNm="selectedIdentityTypeNm"-->
    <!--      @confirm="onIdCardScanConfirm"-->
    <!--    />-->
  </div>
</template>
<script setup>
import { ref, defineModel, defineProps, onMounted, watch, computed } from 'vue'
import { useRoute } from 'vue-router'
import { getCommonCodeList, getFormTypeCode } from '@/libs/utils/comn.utils'
import { formatLocalDateTime, checkNewJoinTime, checkMnpJoinTime } from '@/libs/utils/date.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { post } from '@/libs/api/msf.api'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'

const props = defineProps({
  title: { type: String, default: '신분증 확인' },
  authFlags: { type: Object, default: () => ({}) },
  // true: 신분증 인증유형(K-NOTE/모바일/안면/예외) 선택 영역 노출.
  // false: 해지처럼 인증유형 선택 UI 없이 신분증 스캔만 받는 화면에서 사용한다.
  //        인증유형 기본값은 각 화면 store에서 관리한다.
  showIdentityCertType: { type: Boolean, default: true },
  showIdentityDetailFields: { type: Boolean, default: true },
  returnFile: { type: Boolean, default: false },
  fileTypeCd: { type: String, default: '' },
  joinType: { type: String, default: 'NAC3' },
  restoreDefaultIdentityTypeOnCustomerTypeChange: { type: Boolean, default: false },
  useScanVerifiedForInputLock: { type: Boolean, default: false },
  disabled: Boolean,
  checkBeforeFaceAuth: { type: Function, default: null },
})
const model = defineModel({ type: Object, required: true })
const juridicalModel = defineModel('juridical', { type: Object, default: () => {} })
const emit = defineEmits(['scanConfirm'])
const newChgStore = useMsfFormNewChgStore()

const isVerifyLocked = computed(() => props.disabled || model.value?.isVerified)
const isInputLocked = computed(
  () =>
    props.disabled ||
    (!props.showIdentityCertType &&
      (props.useScanVerifiedForInputLock ? model.value?.isScanVerified : model.value?.isVerified)),
)

const route = useRoute()

const authClickBtnRef = ref(null)
const identityIssuDateRef = ref(null)
const identityTypeCdRef = ref(null)
const identityIssuRegionRef = ref(null)
const driveLicnsNoRef = ref(null)
const driveLicnsNo04Ref = ref(null)

const licenseRegionCodes = ref([])
const fathCertIdTypeCodes = ref([])
const identityTypeCodes = ref([]) // 전체 신분증 코드 목록 추가
const rawIdentityTypes = ref([]) // 순수 신분증 코드 목록 추가
const rawIdentityCertTypeCodes = ref([])

const identityCertTypeData = computed(() =>
  rawIdentityCertTypeCodes.value.map((item) => ({ ...item, disabled: !!model.value?.isVerified })),
)
const isIdCardListModalOpen = ref(false)
const isMobileIdModalOpen = ref(false)
const isFaceAuthModalOpen = ref(false)
// TEST_SCAN_SAMPLE_START: temporary test popup state. Remove after real OCR rollout.
const isIdCardScanModalOpen = ref(false)

// 현재 선택된 신분증 코드(identityTypeCd)에 해당하는 명칭 반환
const selectedIdentityTypeNm = computed(() => {
  if (!model.value.identityTypeCd) return ''
  const found = identityTypeCodes.value.find((item) => item.code === model.value.identityTypeCd)
  return found ? found.title : ''
})

const filteredIdentityTypeCodes = computed(() => {
  const customerType = model.value.cstmrTypeCd
  // RCP2006 신분증 코드 상수 정의 (01: 주민증, 02: 운전면허증, 03: 장애인등록증, 04: 국가유공자증, 06: 외국인등록증)
  const DOMESTIC_IDS = ['01', '02', '03', '04']
  const FOREIGN_IDS = ['06']

  const isDomestic = ['NA', 'NM', 'JP', 'GO'].includes(customerType)
  const isForeign = ['FN', 'FM'].includes(customerType)

  return rawIdentityTypes.value
    .filter((item) => {
      if (isDomestic) {
        return DOMESTIC_IDS.includes(item.code)
      }
      if (isForeign) {
        return FOREIGN_IDS.includes(item.code)
      }
      return true
    })
    .map((item) => ({
      label: item.title,
      value: item.code,
    }))
})

const isMinor = computed(() => ['NM', 'FM'].includes(model.value.cstmrTypeCd))
const showsIdCardScan = computed(
  () => !['JP', 'GO'].includes(model.value.cstmrTypeCd) || !props.showIdentityDetailFields,
)

const getDefaultIdentityTypeCd = (customerType) => {
  if (['FN', 'FM'].includes(customerType)) return '06'
  if (['NA', 'NM', 'JP', 'GO'].includes(customerType)) return '01'
  return ''
}

const computedTitle = computed(() => {
  if (props.title === '신분증 확인' && isMinor.value) {
    return '신분증 확인(법정대리인)'
  }
  return props.title
})
const cstmrJuridicalBizNo = computed(
  () =>
    `${model.value.cstmrJuridicalBizNo1 || ''}${model.value.cstmrJuridicalBizNo2 || ''}${model.value.cstmrJuridicalBizNo3 || ''}`,
)

const getFormTypeCd = () => {
  return getFormTypeCode(route.path)
}

const resolvedJoinType = computed(() => {
  return model.value.joinType || props.joinType
})

const fileCategory = computed(() => {
  const typeCd = getFormTypeCd()
  if (typeCd === '2') return 'servicechange'
  if (typeCd === '3') return 'ownerchange'
  if (typeCd === '4') return 'termination'
  return 'newchange'
})

// 인증 방식 변경 시 인증 관련 상태만 초기화 (스캔 정보와 간섭 배제)
watch(
  () => model.value.identityCertTypeCd,
  () => {
    // 임시저장 데이터 로딩 중에는 인증 유형 변경에 의한 리셋 처리를 수행하지 않음 (대리인 정보 보존)
    if (newChgStore.isDraftLoading) return

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
      // 안면인증 스킵 종료가 아닌 경우
      model.value.minorAgentNm = ''
      model.value.minorAgentRelTypeCd = ''
      model.value.minorAgentTelFnNo = ''
      model.value.minorAgentTelMnNo = ''
      model.value.minorAgentTelRnNo = ''
      model.value.repRelation = ''
      model.value.repAgree = false
    } else {
      // 일반 고객인 경우: 가입자 정보 초기화
      // 법인/공공기관인 경우 이름(상호명)은 유지한다.
      if (!['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
        model.value.cstmrNm = ''
      }
      // 안면인증 스킵 종료가 아닌 경우
      model.value.cstmrNativeRrn1 = ''
      model.value.cstmrNativeRrn2 = ''
      model.value.cstmrForeignerRrn1 = ''
      model.value.cstmrForeignerRrn2 = ''
      if ('userBirthDate' in model.value) model.value.userBirthDate = ''
      if ('userGender' in model.value) model.value.userGender = ''
    }
  },
)

// 고객 유형 변경 시 선택된 신분증 종류 리셋
watch(
  () => model.value.cstmrTypeCd,
  () => {
    model.value.identityTypeCd = props.restoreDefaultIdentityTypeOnCustomerTypeChange
      ? getDefaultIdentityTypeCd(model.value.cstmrTypeCd)
      : ''
    model.value.identityTypeNm = ''
    model.value.identityIssuDate = ''
    model.value.driveLicnsNo = ''
    model.value.identityIssuRegion = ''
    model.value.isScanVerified = false
  },
)

onMounted(async () => {
  const res = await getCommonCodeList([
    'driverLicenseAgency',
    'fathCertIdType',
    'fathCertPolicy',
    'RCP2006',
    'IDENTITY_CERT_TYPE_CD',
  ])

  const licRegion = res?.driverLicenseAgency || []
  const fathCert = res?.fathCertIdType || []
  const fathPolicy = res?.fathCertPolicy || []
  const idTypes = res?.RCP2006 || []
  const certTypes = res?.IDENTITY_CERT_TYPE_CD || []

  licenseRegionCodes.value = (licRegion || []).map((item) => ({
    ...item,
    label: item.title,
    value: item.code,
  }))
  fathCertIdTypeCodes.value = (fathCert || []).map((item) => item.code)

  // 모든 신분증 타입을 하나의 목록으로 합쳐서 명칭 조회용으로 사용
  identityTypeCodes.value = [...(idTypes || []), ...(fathCert || [])]
  rawIdentityTypes.value = idTypes || []

  rawIdentityCertTypeCodes.value = (certTypes || []).map((item) => ({
    value: item.code,
    label: item.title,
  }))

  console.log('>>> 안면인증 관련 정책 (fathCertPolicy):')
  console.table(fathPolicy)
})

const getIgnoreSendingParams = () => {
  let phone = ''
  if (model.value.fathMobileFnNo && model.value.fathMobileMnNo && model.value.fathMobileRnNo) {
    phone = `${model.value.fathMobileFnNo}${model.value.fathMobileMnNo}${model.value.fathMobileRnNo}`
  } else if (model.value.fathTelNo) {
    phone = model.value.fathTelNo
  } else if (model.value.mobileNo1 && model.value.mobileNo2 && model.value.mobileNo3) {
    phone = `${model.value.mobileNo1}${model.value.mobileNo2}${model.value.mobileNo3}`
  } else if (
    model.value.minorAgentTelFnNo &&
    model.value.minorAgentTelMnNo &&
    model.value.minorAgentTelRnNo
  ) {
    phone = `${model.value.minorAgentTelFnNo}${model.value.minorAgentTelMnNo}${model.value.minorAgentTelRnNo}`
  }

  return {
    formType: getFormTypeCode(route.path) || model.value.formType || 'NEWCHANGE',
    path: route.path,
    identityForm: 'M',
    identityType: model.value.identityTypeCd || '01',
    formId: model.value.knoteScanId || '',
    phone: phone,
    joinType: resolvedJoinType.value,
    customerType: model.value.cstmrTypeCd || '',
    visitType: model.value.cstmrVisitTypeCd || '',
    bizNumber: cstmrJuridicalBizNo.value,
    minorAgentName: model.value.minorAgentNm || '',
    minorAgentBirthday: model.value.minorAgentBirth || model.value.agentBirthDate || '',
    agentCode: model.value.agentCd || '',
    resNo: model.value.resNo || '',
  }
}

const handleAuthClick = async () => {
  // 개통 가능 시간 검증 추가
  if (resolvedJoinType.value === 'NAC3' && !checkNewJoinTime()) {
    showAlert('신규개통 가능한 시간은 08:00~21:50 입니다.')
    //return 통과를 위해 제거
  }
  if (resolvedJoinType.value === 'MNP3' && !checkMnpJoinTime()) {
    showAlert('번호이동 가능한 시간은 10:00~19:50 (일요일, 신청/설/추석 당일 제외) 입니다.')
    //return 통과를 위해 제거
  }

  const certType = model.value.identityCertTypeCd

  if (certType === 'K') {
    if (!model.value.agentCd && !model.value.agent) {
      showAlert('대리점을 먼저 선택해 주세요.')
      return
    }
    isIdCardListModalOpen.value = true
  } else if (certType === 'M') {
    isMobileIdModalOpen.value = true
  } else if (certType === 'F') {
    if (props.checkBeforeFaceAuth) {
      if (!props.checkBeforeFaceAuth()) {
        return false
      }
    }

    if (['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
      if (
        !model.value.cstmrJuridicalBizNo1 ||
        !model.value.cstmrJuridicalBizNo2 ||
        !model.value.cstmrJuridicalBizNo3
      ) {
        showAlert('사업자등록번호를 먼저 입력해 주세요.')
        return
      }
      if (model.value.cstmrVisitTypeCd === 'VDP') {
        if (
          (!model.value.minorAgentNm || !model.value.agentBirthDate) &&
          (!juridicalModel.value?.minorAgentNm || !juridicalModel.value?.agentBirthDate)
        ) {
          showAlert('대리인의 성명과 생년월일을 먼저 입력해 주세요.')
          return
        }
      }
    }
    isFaceAuthModalOpen.value = true
  } else if (certType === 'S') {
    try {
      const response = await post(
        '/api/shared/form/common/faceauth/ignore',
        getIgnoreSendingParams(),
      )
      if (response.data.resultCode !== '0000') {
        showAlert('인증 오류가 발생했습니다.')
        return
      }
      const transactionId = response.data?.transactionId || ''
      model.value.fathTransacId = transactionId
      model.value.fathTrgIdentityCertTypeCd = model.value.identityTypeCd || '01'
      model.value.authInfo = response.data?.resNo || ''
      model.value.isVerified = true
      showAlert('인증 예외 처리(스킵)가 완료되었습니다.')
    } catch (error) {
      console.error('[Ignore Auth] error', error)
      showAlert('인증 예외 처리 중 오류가 발생했습니다.')
    }
  }
}

const onIdCardSelect = (selected) => {
  // console.log('선택된 신분증 목록 및 상세정보:', selected)
  if (selected) {
    const { realIssuDate, knoteIdentityScanCstmrNm, knoteIdentityEssNo, knoteIdentityScanDt } =
      selected

    const knoteIdentityTypeCd = selected.knoteIdentityTypeCd || selected.custIdntNoIndCd
    const knoteScanId = selected.frmpapId || selected.id

    // 신분증 정보 세팅
    if (realIssuDate) model.value.identityIssuDate = realIssuDate
    if (knoteIdentityTypeCd) {
      if (knoteIdentityTypeCd === '1') {
        model.value.identityTypeCd = '01'
      } else if (knoteIdentityTypeCd === '5') {
        model.value.identityTypeCd = '02'
      } else if (knoteIdentityTypeCd === '4') {
        model.value.identityTypeCd = '06'
      } else {
        model.value.identityTypeCd = knoteIdentityTypeCd
      }
    }

    // K-NOTE 스캔 관련 추가 정보 저장
    if (knoteIdentityScanCstmrNm) model.value.knoteIdentityScanCstmrNm = knoteIdentityScanCstmrNm
    if (knoteIdentityEssNo) model.value.knoteIdentityEssNo = knoteIdentityEssNo
    if (knoteIdentityTypeCd) model.value.knoteIdentityTypeCd = knoteIdentityTypeCd
    if (knoteIdentityScanDt) {
      model.value.knoteIdentityScanDt = formatLocalDateTime(knoteIdentityScanDt)
    }
    if (knoteScanId) model.value.knoteScanId = knoteScanId

    // 가입자 정보, 연락처 영역 1차 세팅 (인증 정보 기준)
    setIdentityData(selected, true)
  }

  // 인증 완료 처리 (안면인증 바로 넘어가는 로직 제거)
  model.value.isVerified = true
}

const onMobileIdConfirm = async () => {
  // console.log('모바일 신분증 인증 완료')
  try {
    const response = await post('/api/shared/form/common/faceauth/ignore', getIgnoreSendingParams())
    if (response.code !== '0000') {
      showAlert(response.message || '모바일 신분증 인증 예외 처리에 실패했습니다.')
      return
    }
    const transactionId = response.data?.transactionId || ''
    model.value.fathTransacId = transactionId
    model.value.fathTrgIdentityCertTypeCd = model.value.identityTypeCd || '01'
    model.value.authInfo = response.data?.resNo || ''
    model.value.isVerified = true
    showAlert('모바일 신분증 인증이 완료되었습니다.')
  } catch (error) {
    console.error('[Mobile ID Ignore Auth] error', error)
    showAlert('모바일 신분증 인증 처리 중 오류가 발생했습니다.')
  }
}

const onCloseFaceAuth = (result) => {
  if (result) {
    // result = {
    //   isSkip: 스킵여부(true/false),
    //   transactionId: '[안면인증키]',
    //   formId: '[서식지ID]',
    //   resNo: '[resNo]',
    //   joinType: '[가입 유형]',
    //   customerType: '[고객 유형]',
    //   completeDate: '[안면인증 완료일시]',
    //   identityType: '[신분증 유형 (01: 주민등록증, 02: 운전면허증)]',
    //   customerName: '[고객명]',
    //   customerNo : '[고객 주민번호]',
    //   customerDriveNo: '[고객 운전면허번호]',
    //   customerIssueDate: '[신분증 발급일자]',
    //   skipPosableYn: '[안면인증 스킵 가능 여부 (Y/N)]',
    //   fathTelNo: '[안면인증 진행 휴대폰번호]',
    // }
    // console.log('안면 인증 완료')

    // 안면인증 정보 세팅
    model.value.knoteScanId = result.formId
    model.value.fathTransacId = result.transactionId
    model.value.fathTelNo = result.fathTelNo
    if (result.resNo) {
      model.value.resNo = result.resNo
    }
    if (result.completeDate) {
      model.value.fathCmpltNtfyDate = result.completeDate
    }

    // 신분증 유형 및 상세 정보 세팅
    const isIdCard = result.identityType === '01'
    const isDriverCard = result.identityType === '02'
    const isForeignerCard = result.identityType === '06'

    model.value.identityTypeCd = isIdCard ? '01' : isDriverCard ? '02' : isForeignerCard ? '06' : ''
    model.value.identityTypeNm = isIdCard
      ? '주민등록증'
      : isDriverCard
        ? '운전면허증'
        : isForeignerCard
          ? '외국인등록증'
          : ''

    if (result.customerIssueDate) {
      model.value.identityIssuDate = result.customerIssueDate
    }

    // 운전면허증 정보 및 면허지역 세팅
    if (isDriverCard) {
      if (result.customerDriveNo) {
        model.value.driveLicnsNo = result.customerDriveNo
        model.value.identityIssuRegion = result.customerDriveNo.substring(0, 2)
      }
    } else {
      model.value.driveLicnsNo = ''
      model.value.identityIssuRegion = ''
    }

    // K-NOTE 관련 상태 정보 저장
    const finalEssNo =
      result.customerNo ||
      result.knoteIdentityEssNo ||
      result.custIdntNo ||
      result.realCustIdntNo ||
      result.essNo ||
      result.identityEssNo ||
      resolveScanRrn(result)

    if (result.customerName || result.custNm) {
      model.value.knoteIdentityScanCstmrNm = result.customerName || result.custNm
    }
    if (finalEssNo) {
      model.value.knoteIdentityEssNo = finalEssNo
    }
    model.value.knoteIdentityTypeCd = isIdCard
      ? '1'
      : isDriverCard
        ? '5'
        : isForeignerCard
          ? '4'
          : ''
    if (result.completeDate) {
      model.value.knoteIdentityScanDt = formatLocalDateTime(result.completeDate)
    }

    // 가입자 정보, 연락처 영역 세팅 (안면인증 결과 직접 세팅)
    setIdentityData(result, true)

    if (result.isSkip && (!result.customerName || !result.customerNo)) {
      model.value.identityCertTypeCd = 'S'
    }

    model.value.isVerified = true
  }
}

// 실제 스캔 API와 테스트 샘플에서 내려오는 식별번호 필드명이 달라 K-NOTE 저장용으로 흡수한다.
const resolveScanRrn = (data) =>
  data.rrn ||
  data.essNo ||
  data.identityEssNo ||
  data.knoteIdentityEssNo ||
  data.cstmrNativeRrn ||
  data.cstmrForeignerRrn ||
  `${data.rrn1 || ''}${data.rrn2 || ''}`

const splitScanRrn = (value) => {
  const digits = (value || '').replace(/[^0-9]/g, '')
  if (digits.length < 6) return { rrn1: '', rrn2: '', birthDate: '' }

  if (digits.length >= 15) {
    const yyyyMMdd = digits.substring(0, 8)
    return {
      rrn1: yyyyMMdd.substring(2),
      rrn2: digits.substring(8, 15),
      birthDate: yyyyMMdd,
    }
  }

  if (digits.length === 8) {
    return {
      rrn1: digits.substring(2),
      rrn2: '',
      birthDate: digits,
    }
  }

  const rrn1 = digits.substring(0, 6)
  const rrn2 = digits.length > 6 ? digits.substring(6, 13) : ''
  const genderDigit = rrn2.charAt(0)
  let birthDate = rrn1

  if (genderDigit) {
    if (['1', '2', '5', '6'].includes(genderDigit)) birthDate = `19${rrn1}`
    else if (['3', '4', '7', '8'].includes(genderDigit)) birthDate = `20${rrn1}`
  }

  return { rrn1, rrn2, birthDate }
}

// 스캔 및 인증 데이터 매핑 헬퍼 함수
const setIdentityData = (source, isAuth = false) => {
  const cstmrType = model.value.cstmrTypeCd

  // 이름 및 번호 파싱 (KNOTE 인증 & 안면인증 모두 지원)
  const name =
    source.custNm || source.cstmrNm || source.knoteIdentityScanCstmrNm || source.customerName || ''
  const rrnRaw = isAuth
    ? source.realCustIdntNo || source.knoteIdentityEssNo || source.customerNo
    : resolveScanRrn(source)
  const { rrn1, rrn2, birthDate } = splitScanRrn(rrnRaw)

  // 주소 파싱
  const zip = source.zipNo || source.zipcode || source.cstmrZipcd || ''
  const addr = source.address || source.addr || source.cstmrAdr || ''
  const detailAddr = source.detailAddress || source.detailAddr || source.cstmrAdrDtl || ''

  // 1. 주소 세팅 (공통)
  if (zip) model.value.zipNo = zip
  if (addr) model.value.address = addr
  if (detailAddr) model.value.detailAddress = detailAddr

  // 2. 고객유형별 세팅
  if (cstmrType === 'NA') {
    // 내국인: 가입자 정보 - 이름, 주민등록번호
    if (name) model.value.cstmrNm = name
    if (rrn1) model.value.cstmrNativeRrn1 = rrn1
    if (rrn2) model.value.cstmrNativeRrn2 = rrn2
    if (birthDate && 'userBirthDate' in model.value) model.value.userBirthDate = birthDate
  } else if (cstmrType === 'NM') {
    // 미성년자(19세 미만): 법정대리인 정보 - 이름, 주민등록번호
    if (name) {
      model.value.repName = name
      model.value.minorAgentNm = name
    }
    if (rrn1) {
      model.value.repRegistrationNo1 = rrn1
      model.value.minorAgentRrn = rrnRaw
    }
    if (rrn2) {
      model.value.repRegistrationNo2 = rrn2
    }
  } else if (cstmrType === 'FN') {
    // 외국인(Foreigner): 가입자 정보 - 이름, 외국인등록번호
    if (name) model.value.cstmrNm = name
    if (rrn1) model.value.cstmrForeignerRrn1 = rrn1
    if (rrn2) model.value.cstmrForeignerRrn2 = rrn2
    if (birthDate && 'userBirthDate' in model.value) model.value.userBirthDate = birthDate

    // 외국인 가입자 연락처 - 국가, 체류기간, 비자
    const countryVal = source.country || source.cstmrNation || source.nation || ''
    const stayPeriodVal =
      source.stayPeriod || source.cstmrForeignerVdateEndDate || source.expiryDate || ''
    const visaVal = source.visaType || source.cstmrForeignerVisaNo || source.visaCode || ''

    if (countryVal) {
      model.value.country = countryVal
      model.value.cstmrForeignerCountryCd = countryVal
    }
    if (stayPeriodVal) {
      model.value.stayPeriod = stayPeriodVal
      model.value.cstmrForeignerVdateEndDate = stayPeriodVal
    }
    if (visaVal) {
      model.value.visaType = visaVal
      model.value.cstmrForeignerVisaNo = visaVal
    }
  } else if (cstmrType === 'FM') {
    // 외국인 미성년자: 법정대리인 정보 - 이름, 주민/외국인등록번호
    if (name) {
      model.value.repName = name
      model.value.minorAgentNm = name
    }
    if (rrn1) {
      model.value.repRegistrationNo1 = rrn1
      model.value.repForeignerNo1 = rrn1
      model.value.minorAgentRrn = rrnRaw
    }
    if (rrn2) {
      model.value.repRegistrationNo2 = rrn2
      model.value.repForeignerNo2 = rrn2
    }

    // 외국인 가입자 연락처 - 국가, 체류기간, 비자
    const countryVal = source.country || source.cstmrNation || source.nation || ''
    const stayPeriodVal =
      source.stayPeriod || source.cstmrForeignerVdateEndDate || source.expiryDate || ''
    const visaVal = source.visaType || source.cstmrForeignerVisaNo || source.visaCode || ''

    if (countryVal) {
      model.value.country = countryVal
      model.value.cstmrForeignerCountryCd = countryVal
    }
    if (stayPeriodVal) {
      model.value.stayPeriod = stayPeriodVal
      model.value.cstmrForeignerVdateEndDate = stayPeriodVal
    }
    if (visaVal) {
      model.value.visaType = visaVal
      model.value.cstmrForeignerVisaNo = visaVal
    }
  } else if (['JP', 'GO'].includes(cstmrType)) {
    // 법인 / 공공기관: 가입자 정보 - 대표자명
    if (name) model.value.cstmrJuridicalRepNm = name
    // 대리인 방문(VDP)인 경우 대리인 인증 정보 연동
    if (model.value.cstmrVisitTypeCd === 'VDP') {
      if (name) {
        model.value.jrdclAgentNm = name
      }
      if (rrnRaw) {
        model.value.jrdclAgentRrn = rrnRaw
      }
    }
  }

  // 3. 법정대리인 본인인증 신분증 유형 세팅 (RCP2006 공통코드 기반)
  if (['NM', 'FM'].includes(cstmrType)) {
    const rawCertType =
      model.value.identityTypeCd ||
      source.identityTypeCd ||
      source.identityType ||
      source.knoteIdentityTypeCd ||
      source.custIdntNoIndCd ||
      source.fathTrgIdentityCertTypeCd ||
      source.identityCertTypeCd ||
      '01'

    // RCP2006 공통코드 (01: 주민등록증, 02: 운전면허증 등) 기반으로 세팅
    let certType = '01' // 기본값 01
    if (rawCertType === '1' || rawCertType === 'I' || rawCertType === '01') {
      certType = '01'
    } else if (rawCertType === '5' || rawCertType === 'D' || rawCertType === '02') {
      certType = '02'
    } else if (rawCertType) {
      if (rawCertType.length === 1 && !isNaN(rawCertType)) {
        certType = rawCertType.padStart(2, '0')
      } else {
        certType = rawCertType
      }
    }
    model.value.minorAgentSelfCertTypeCd = certType
  }
}

const onIdCardScanConfirm = (data) => {
  // console.log('[MsfIdentityVerify][scanConfirm] raw data:', data)
  if (data) {
    console.log('[MsfIdentityVerify][scanConfirm] input summary:')
    console.table([
      {
        scanSource: data.scanSource,
        isRealOcr: data.isRealOcr,
        identityTypeCd: data.identityTypeCd,
        identityTypeNm: data.identityTypeNm,
        cstmrNm: data.cstmrNm,
        cstmrNativeRrn: data.cstmrNativeRrn,
        cstmrForeignerRrn: data.cstmrForeignerRrn,
        scanDt: data.scanDt,
        scanId: data.scanId,
        maskImageFile: data.maskImageFile,
      },
    ])
    // 스캔 응답에 발급일자가 포함된 경우만 갱신한다.
    // 미포함 응답 때문에 사용자가 이미 입력한 발급일자를 빈 값으로 덮어쓰지 않기 위함이다.
    if (data.identityIssuDate) {
      model.value.identityIssuDate = data.identityIssuDate
    }
    // 스캔된 신분증 정보 저장
    if (data.identityTypeNm) {
      model.value.identityTypeNm = data.identityTypeNm
    }
    if (data.identityTypeCd) {
      model.value.identityTypeCd = data.identityTypeCd
    }
    if (data.driveLicnsNo) {
      model.value.driveLicnsNo = data.driveLicnsNo
    }
    if (data.identityIssuRegion) {
      model.value.identityIssuRegion = data.identityIssuRegion
    }
    if (data.maskImageFile) {
      model.value.maskImageFile = data.maskImageFile
    }
    model.value.zipNo = data.zipNo
    model.value.address = data.address
    model.value.detailAddress = data.detailAddress

    // K-NOTE 스캔 관련 추가 정보 저장
    if (data.cstmrNm) {
      model.value.knoteIdentityScanCstmrNm = data.cstmrNm
    }
    const scanRrn = resolveScanRrn(data)
    if (scanRrn) {
      model.value.knoteIdentityEssNo = scanRrn
    }
    const scanTypeCd = data.knoteIdentityTypeCd || data.identityTypeCd
    const scanDt = data.knoteIdentityScanDt || data.scanDt
    const scanId = data.knoteScanId || data.scanId

    if (scanTypeCd) model.value.knoteIdentityTypeCd = scanTypeCd
    if (scanDt) {
      model.value.knoteIdentityScanDt = formatLocalDateTime(scanDt)
    }
    if (scanId) model.value.knoteScanId = scanId

    // 가입자 정보, 연락처 영역 2차 세팅 (인증 정보가 없을 때만 스캔 정보 세팅)
    if (!model.value.isVerified) {
      setIdentityData(data, false)
    }

    console.log('[MsfIdentityVerify][scanConfirm] mapped model:')
    console.table([
      {
        identityTypeCd: model.value.identityTypeCd,
        identityTypeNm: model.value.identityTypeNm,
        identityIssuDate: model.value.identityIssuDate,
        identityIssuRegion: model.value.identityIssuRegion,
        driveLicnsNo: model.value.driveLicnsNo,
        userBirthDate: model.value.userBirthDate,
        cstmrNativeRrn1: model.value.cstmrNativeRrn1,
        cstmrNativeRrn2: model.value.cstmrNativeRrn2,
        cstmrForeignerRrn1: model.value.cstmrForeignerRrn1,
        cstmrForeignerRrn2: model.value.cstmrForeignerRrn2,
        scanName: model.value.knoteIdentityScanCstmrNm,
        scanRrn: model.value.knoteIdentityEssNo,
        scanTypeCd: model.value.knoteIdentityTypeCd,
        scanDt: model.value.knoteIdentityScanDt,
        scanId: model.value.knoteScanId,
      },
    ])

    // 구비서류 목록(msfRequestDocList)에 신분증 파일 메타데이터 추가
    if (data.fileInfo || (props.returnFile && data.maskImageFile)) {
      const fileInfo = data.fileInfo || {}
      const filePath = fileInfo.filePath || ''

      // 전달받은 props.fileTypeCd가 있으면 우선 적용하고, 없으면 기존 mapping을 적용한다.
      const targetFileTypeCd =
        props.fileTypeCd ||
        data.fileTypeCd ||
        (['01', '02'].includes(model.value.identityTypeCd)
          ? '21'
          : model.value.identityTypeCd || '21')

      const newDoc = {
        fileTypeCd: targetFileTypeCd,
        filePathNm: filePath || data.maskImageFile || '',
        fileNm:
          fileInfo.fileName ||
          fileInfo.fileNm ||
          (filePath ? filePath.split('/').pop() : '') ||
          `${model.value.identityTypeNm || '신분증'}_마스킹.png`,
        filePageNo: 1,
        previewUrl: data.maskImageFile || '',
        maskImageFile: data.maskImageFile || '',
      }

      // 기존 목록에 동일한 fileTypeCd가 이미 존재하면 교체하고, 없으면 추가한다.
      const list = [...(model.value.msfRequestDocList || [])]
      const targetIdx = list.findIndex((item) => item.fileTypeCd === newDoc.fileTypeCd)
      if (targetIdx >= 0) {
        list[targetIdx] = newDoc
      } else {
        list.push(newDoc)
      }
      model.value.msfRequestDocList = list
    }

    // 화면별 추가 매핑이 필요한 경우 부모 컴포넌트에서 처리하도록 원본 스캔 데이터를 전달한다.
    emit('scanConfirm', data)
  }
  model.value.isScanVerified = true
}

const isTrCustomer = computed(() => {
  if (model.value?.formType === 'NEWCHANGE') return false
  return !!model.value?.isTrCustomer
})

const validate = (showAlertOnError = false) => {
  // 신분증 인증유형 선택 UI가 있는 화면이면서 인증예외(S)가 아닐 때만 K-NOTE/모바일/안면 본인인증 완료 여부를 검사한다.
  const needsAuth =
    props.showIdentityCertType && !isTrCustomer.value && model.value.identityCertTypeCd !== 'S'
  if (needsAuth && !model.value.isVerified) {
    if (showAlertOnError)
      showAlert('본인인증을 완료해 주세요.', () => {
        authClickBtnRef.value?.focus()
      })
    return false
  }

  // 법인/공공기관인 경우 신분증 정보(유형 선택, 발급일자 등) 상세 검증 및 입력을 모두 제외한다.
  if (['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
    return true
  }

  // 1. 신분증 선택 필수 체크
  if (!model.value.identityTypeCd) {
    if (showAlertOnError) {
      showAlert('고객을 확인할 신분증 종류를 선택해 주세요.', () => {
        identityTypeCdRef?.value?.focus()
      })
    }
    return false
  }

  const typeCd = model.value.identityTypeCd

  // 양도고객 신분증 일자 정보 제거
  if (model.value.isTrCustomer) return true

  if (!props.showIdentityDetailFields) return true

  // 2. 공통 발급일자 8자리 체크 (주민등록증 '01', 운전면허증 '02', 장애인등록증 '03', 국가유공자증 '04', 외국인등록증 '06')
  if (['01', '02', '03', '04', '06'].includes(typeCd)) {
    if (model.value.identityIssuDate) {
      const pureDate = model.value.identityIssuDate.replace(/[^0-9]/g, '')
      if (pureDate.length !== 8) {
        if (showAlertOnError)
          showAlert('신분증 발급일자를 8자리(YYYYMMDD)로 정확히 입력해 주세요.', () => {
            identityIssuDateRef.value?.focus()
          })
        return false
      }
    } else {
      if (showAlertOnError) {
        const label = typeCd === '02' ? '면허발급일자' : '신분증 발급일자'
        showAlert(`${label}를 입력해 주세요.`, () => {
          identityIssuDateRef.value?.focus()
        })
      }
      return false
    }
  }

  // 3. 운전면허증('02')인 경우 발급지역 및 면허번호 필수 체크
  if (typeCd === '02') {
    if (!model.value.identityIssuRegion) {
      if (showAlertOnError)
        showAlert('운전면허증 발급지역을 선택해 주세요.', () => {
          identityIssuRegionRef.value?.focus()
        })
      return false
    }
    if (!model.value.driveLicnsNo) {
      if (showAlertOnError)
        showAlert('운전면허번호를 입력해 주세요.', () => {
          driveLicnsNoRef.value?.focus()
        })
      return false
    }
  }

  // 4. 국가유공자증('04')인 경우 유공자번호 필수 체크
  if (typeCd === '04') {
    if (!model.value.driveLicnsNo) {
      if (showAlertOnError)
        showAlert('국가유공자 번호를 입력해 주세요.', () => {
          driveLicnsNo04Ref.value?.focus()
        })
      return false
    }
  }

  return true
}

const identityCertTypeCdRef = ref(null)
const checkValidation = () => {
  if (['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
    return true
  }
  if (
    props.showIdentityCertType &&
    !isTrCustomer.value &&
    model.value.identityCertTypeCd !== 'S' &&
    !model.value.isVerified
  ) {
    console.log('본인인증을 미완료')
    showAlert('본인인증을 완료해 주세요.', () => {
      authClickBtnRef.value?.focus()
    })
    return false
  }

  // 양수자 법인/공공기관이면 신분증 정보 제거
  if (
    ['JP', 'GO'].includes(model.value.cstmrTypeCd) &&
    model.value.isTeCustomer &&
    model.value.identityCertTypeCd
  ) {
    return true
  }

  if (!model.value.identityTypeCd) {
    console.log('신분증 종류 미선택')
    showAlert('고객을 확인할 신분증 종류를 선택해 주세요.', () => {
      identityTypeCdRef.value?.focus()
    })
    return false
  }

  // 양도고객 신분증 일자 정보 제거
  if (model.value.isTrCustomer) return true

  // 2. 공통 발급일자 8자리 체크 (주민등록증 '01', 운전면허증 '02', 장애인등록증 '03', 국가유공자증 '04', 외국인등록증 '06')
  if (['01', '02', '03', '04', '06'].includes(model.value.identityTypeCd)) {
    if (model.value.identityIssuDate) {
      const pureDate = model.value.identityIssuDate.replace(/[^0-9]/g, '')
      if (pureDate.length !== 8) {
        console.log('발급일자 미입력')
        showAlert('신분증 발급일자를 8자리(YYYYMMDD)로 정확히 입력해 주세요.', () => {
          identityIssuDateRef.value?.focus()
        })
        return false
      }
    } else {
      console.log('발급일자 미입력')
      const label = model.value.identityTypeCd === '02' ? '면허발급일자' : '신분증 발급일자'
      showAlert(`${label}를 입력해 주세요.`, () => {
        identityIssuDateRef.value?.focus()
      })
      return false
    }
  }

  // 3. 운전면허증('02')인 경우 발급지역 및 면허번호 필수 체크
  if (model.value.identityTypeCd === '02') {
    if (!model.value.identityIssuRegion) {
      console.log('운전면허증 발급지역을 미선택')
      showAlert('운전면허증 발급지역을 선택해 주세요.', () => {
        identityIssuRegionRef.value?.focus()
      })
      return false
    }
    if (!model.value.driveLicnsNo) {
      console.log('운전면허번호 미입력')
      showAlert('운전면허번호를 입력해 주세요.', () => {
        driveLicnsNoRef.value?.focus()
      })
      return false
    }
  }

  // 4. 국가유공자증('04')인 경우 유공자번호 필수 체크
  if (model.value.identityTypeCd === '04') {
    if (!model.value.driveLicnsNo) {
      console.log('국가유공자 번호 미입력')
      showAlert('국가유공자 번호를 입력해 주세요.', () => {
        driveLicnsNo04Ref.value?.focus()
      })
      return false
    }
  }
  return true
}

defineExpose({ validate, checkValidation })
</script>
