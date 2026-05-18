<script setup>
import { computed, defineModel, onMounted, ref, watch } from 'vue'
import { useMsfUserStore } from '@/stores/msf_user'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import MsfVasManageModal from './popups/MsfVasManageModal.vue'
import IllegalTmBlockModal from './popups/MsfIllegalTmBlockModal.vue'
import NumberSpoofingBlockModal from './popups/MsfNumberSpoofingBlockModal.vue'
import RoamingStartDateModal from './popups/MsfRoamingStartDateModal.vue'
import MsfInfoProviderBlockModal from './popups/MsfInfoProviderBlockModal.vue'
import MsfNotifyPhoneModal from './popups/MsfNotifyPhoneModal.vue'
import MsfFreeCallNumberModal from './popups/MsfFreeCallNumberModal.vue'
import MsfAlertNumbersModal from './popups/MsfAlertNumbersModal.vue'
import MsfMilitaryTimePlanModal from './popups/MsfMilitaryTimePlanModal.vue'
import MsfRoamingShareMainModal from './popups/MsfRoamingShareMainModal.vue'
import MsfRoamingShareSubModal from './popups/MsfRoamingShareSubModal.vue'

// ─── 로그 접두사 ──────────────────────────────────────────────────────────────
const getLogPrefix = (task) => `[변경][부가 신청변경][${task}]`


// ─── 상태 (State) ─────────────────────────────────────────────────────────────
const model = defineModel({ type: Object, required: true })

// 부가서비스 추가/삭제 팝업 표시 여부
const isVasModalOpen = ref(false)
// 설정 팝업 종류: illegalTm | blockNumber100 | infoProviderBlock | numberSpoofing | roamingDate8 | roamingDateRange | roamingShareMain1 | roamingShareMain2 | notifyPhone | alertNumbers | freeCallNumber | militaryTimePlan
const settingModalType = ref('')
// 현재 설정 중인 서비스 rateCd
const settingServiceId = ref('')
// 설정 팝업 open/close 상태
const showSettingModal = ref(false)

// X97 조회 결과 무료/유료 부가서비스 원본 (팝업 전달용)
const queriedFreeServices = ref([])
const queriedPaidServices = ref([])
// /api/form/addition/list 조회 결과 전체 부가서비스 (팝업에 전달, 이용중 항목은 팝업에서 필터링)
const allFreeVasServices = ref([])
const allPaidVasServices = ref([])
// 화면 표시용 이용중 무료/유료 서비스
const activeFreeServices = ref([])
const activePaidServices = ref([])
// 팝업에서 새로 추가한 서비스 (테이블 맨 아래 표시)
const addedServices = ref([])
// 체크박스 선택 ID 목록 (체크 해제 = 해지 대상)
const selectedServiceIds = ref([])
// 확인완료 상태 (true → 목록 잠금, 다음 버튼 활성화)
const isServiceConfirmCompleted = ref(false)
// 부가서비스 사전체크 진행중 플래그
const isPreChecking = ref(false)
const preCheckPassedServiceIds = ref([])
const onlineCancelUnavailableServiceIds = ref([])
const preCheckFailedServiceIds = ref([])
let preCheckRequestSeq = 0
let isApplyingPreCheckResult = false

// ─── 유틸리티 함수 ────────────────────────────────────────────────────────────

const toNumber = (value) => Number(String(value || 0).replace(/,/g, '')) || 0

// 여러 API 응답 필드명(rateCd·soc·prodId 등)을 단일 서비스 코드로 정규화
const getServiceKey = (svc = {}, index = 0) =>
  String(svc.rateCd || svc.soc || svc.prodId || svc.addSvcCd || `service-${index}`)

// 여러 API 응답 필드명(rateNm·socDescription 등)을 단일 서비스명으로 정규화
const getServiceName = (svc = {}) =>
  svc.rateNm || svc.socDescription || svc.prodNm || svc.addSvcNm || svc.serviceName || '-'

// 여러 API 응답 필드명(baseAmt·socRateVat 등)을 단일 금액으로 정규화
const getServiceAmount = (svc = {}) =>
  svc.baseAmt ?? svc.socRateVatValue ?? svc.socRateVat ?? svc.socRateValue ?? 0

const getTodayYmd = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const date = String(now.getDate()).padStart(2, '0')
  return `${year}${month}${date}`
}

const getServiceStartDate = (svc = {}) =>
  String(svc.strtDt || svc.startDate || svc.appStartDd || svc.effectiveDate || '').replace(/\D/g, '')

// 당일 개통 서비스 여부 (ASIS 정책: 당일 개통 서비스는 온라인 해지 불가)
const isStartedToday = (svc = {}) => getServiceStartDate(svc).slice(0, 8) === getTodayYmd()

// 해지 비활성화 조건: 당일 개통 서비스
const isCancelDisabled = (svc = {}) => isStartedToday(svc)

// 팝업으로 새로 추가한 서비스인지 여부
const isAddedService = (svc = {}) => addedServices.value.some((item) => item.rateCd === svc.rateCd)

// 이미 이용 중인(기존) 서비스인지 여부
const isExistingService = (svc = {}) =>
  [...activeFreeServices.value, ...activePaidServices.value].some((item) => item.rateCd === svc.rateCd)

// 콘솔 로그용 서비스 요약 (필수 필드만 출력)
const summarizeService = (svc = {}) => ({
  rateCd: svc.rateCd,
  rateNm: svc.rateNm,
  baseAmt: svc.baseAmt,
  settingYn: svc.settingYn,
})

// ─── 설정 팝업 관련 ───────────────────────────────────────────────────────────

// SETTING_MODAL_MAP: 상품코드 → 팝업 타입 매핑
const SETTING_MODAL_MAP = {
  // 불법TM 차단
  NOSPAM4: 'illegalTm',          // MsfIllegalTmBlockModal (maxCount=50)
  NOSPAM2: 'blockNumber100',     // MsfIllegalTmBlockModal (maxCount=100, minLength=3)
  // 정보제공차단
  NOSPAM3: 'infoProviderBlock',  // MsfInfoProviderBlockModal
  // 번호 도용 차단
  STLPVTPHN: 'numberSpoofing',  // MsfNumberSpoofingBlockModal
  // 로밍 시작일만 (8자리)
  DATAROM01: 'roamingDate8', DATAROM03: 'roamingDate8',
  LTEDTROM5: 'roamingDate8', ITGSAFE3G: 'roamingDate8',
  // 로밍 기간설정 (시작일시+종료일)
  DYDTROM05: 'roamingDateRange',
  PL2078760: 'roamingDateRange', PL2079771: 'roamingDateRange', PL2079772: 'roamingDateRange',
  // 함께쓰는 로밍 대표
  PL199N109: 'roamingShareMain1', PL199N120: 'roamingShareMain1',
  PL199N122: 'roamingShareMain1', PL199N126: 'roamingShareMain1',
  PL199N129: 'roamingShareMain1', PL199N132: 'roamingShareMain1',
  // 함께쓰는 로밍 서브
  PL199N117: 'roamingShareSub1', PL199N121: 'roamingShareSub1',
  PL199N123: 'roamingShareSub1', PL199N127: 'roamingShareSub1',
  PL199N130: 'roamingShareSub1', PL199N133: 'roamingShareSub1',
  // 하루종일 로밍 베이직 투게더 대표/서브
  PL2079777: 'roamingShareMain2', PL2079778: 'roamingShareSub2',
  // 통보/알림번호
  DATAROMSM: 'notifyPhone',     // MsfNotifyPhoneModal
  FCARVLSMS: 'alertNumbers',    // MsfAlertNumbersModal
  SENOINFR1: 'freeCallNumber',  // MsfFreeCallNumberModal
  PL253A854: 'militaryTimePlan',// MsfMilitaryTimePlanModal
  // autoDefault (팝업 없이 자동처리)
  ITCRBS: 'autoDefault', RNGTOUPR3: 'autoDefault',
  SKCOREPAC: 'autoDefault', XRINGMON: 'autoDefault', XRINGWEEK: 'autoDefault',
}

const SETTING_MODAL_NAME_MAP = {
  illegalTm: '불법TM수신차단',
  blockNumber100: '특정번호 수신차단서비스',
  infoProviderBlock: '정보제공사업자번호차단서비스',
  numberSpoofing: '번호도용 차단 서비스',
  roamingDate8: '로밍 시작일 설정',
  roamingDateRange: '로밍 기간 설정',
  roamingShareMain1: '함께쓰는 로밍 대표 설정',
  roamingShareSub1: '함께쓰는 로밍 서브 설정',
  roamingShareMain2: '하루종일 로밍 베이직 투게더(대표)',
  roamingShareSub2: '하루종일 로밍 베이직 투게더(서브)',
  notifyPhone: '데이터로밍요금알림',
  alertNumbers: '로밍 해외도착알리미',
  freeCallNumber: '망내 1회선 무료통화',
  militaryTimePlan: 'My time plan_MVNO 전용',
  autoDefault: '자동 설정',
}

const SETTING_MODAL_SCREEN_ID_MAP = {
  illegalTm: 'S102030102',
  numberSpoofing: 'S102030103',
  roamingDateRange: 'S102030104',
  roamingDate8: 'S102030106',
  roamingShareMain1: 'S102030107',
  roamingShareSub1: 'S102030108',
  roamingShareMain2: 'S102030109',
  roamingShareSub2: 'S102030110',
  blockNumber100: 'S102030111',
  infoProviderBlock: 'S102030112',
  notifyPhone: 'S102030113',
  alertNumbers: 'S102030114',
  militaryTimePlan: 'S102030115',
  freeCallNumber: 'S102030116',
}

const SETTING_MODAL_COMPONENT_MAP = {
  illegalTm: 'MsfIllegalTmBlockModal.vue',
  blockNumber100: 'MsfIllegalTmBlockModal.vue',
  infoProviderBlock: 'MsfInfoProviderBlockModal.vue',
  numberSpoofing: 'MsfNumberSpoofingBlockModal.vue',
  roamingDate8: 'MsfRoamingStartDateModal.vue',
  roamingDateRange: 'MsfRoamingStartDateModal.vue',
  roamingShareMain1: 'MsfRoamingShareMainModal.vue',
  roamingShareSub1: 'MsfRoamingShareSubModal.vue',
  roamingShareMain2: 'MsfRoamingShareMainModal.vue',
  roamingShareSub2: 'MsfRoamingShareSubModal.vue',
  notifyPhone: 'MsfNotifyPhoneModal.vue',
  alertNumbers: 'MsfAlertNumbersModal.vue',
  freeCallNumber: 'MsfFreeCallNumberModal.vue',
  militaryTimePlan: 'MsfMilitaryTimePlanModal.vue',
}

// 서비스 코드로 설정 팝업 종류 결정
const getSettingModalType = (svc = {}) => SETTING_MODAL_MAP[getServiceKey(svc).toUpperCase()] ?? null
const getSettingPopupName = (svc = {}) => SETTING_MODAL_NAME_MAP[getSettingModalType(svc)] || ''
const getSettingPopupId = (svc = {}) => SETTING_MODAL_COMPONENT_MAP[getSettingModalType(svc)] || ''
const getSettingScreenId = (svc = {}) => SETTING_MODAL_SCREEN_ID_MAP[getSettingModalType(svc)] || ''

// 설정이 필요한 서비스인지 여부 (settingYn=Y AND 알려진 모달 타입 존재)
const needsAdditionalInfo = (svc = {}) => svc.settingYn === 'Y' && !!getSettingModalType(svc)

// 추가 서비스 중 설정 미완료 → 체크박스·확인 버튼 비활성화
const isJoinDisabled = (svc = {}) =>
  isAddedService(svc) && needsAdditionalInfo(svc) && svc.addSvcSettingCompleted !== true

// 기존 이용중 서비스에서 설정 변경 가능한 타입
const EXISTING_SERVICE_SETTABLE = new Set([
  'illegalTm', 'blockNumber100', 'infoProviderBlock',
  'numberSpoofing',
  'roamingDate8', 'roamingDateRange',
  'roamingShareMain1', 'roamingShareMain2',
  'roamingShareSub1', 'roamingShareSub2',
  'notifyPhone', 'alertNumbers', 'freeCallNumber',
  'militaryTimePlan',
])

// 설정 버튼 비활성화 조건:
// - 알려진 모달 타입 없음 → 항상 비활성
// - autoDefault 타입 → 팝업 없이 자동처리하므로 비활성
// - 기존 이용중 서비스가 체크 해제되어 해지 대상이면 비활성
// - 기존 이용중 서비스 → 설정 가능한 타입만 활성
const isSettingButtonDisabled = (svc = {}) => {
  const modalType = getSettingModalType(svc)
  if (!modalType || modalType === 'autoDefault') return true
  if (isExistingService(svc)) {
    if (!selectedServiceIds.value.includes(svc.rateCd)) return true
    return !EXISTING_SERVICE_SETTABLE.has(modalType)
  }
  return false
}

const openSettingModal = (svc = {}) => {
  const modalType = getSettingModalType(svc)
  const popupName = getSettingPopupName(svc)
  const popupId = getSettingPopupId(svc)
  const screenId = getSettingScreenId(svc)

  console.log(`${getLogPrefix('부가서비스설정')} 요청 시작`, {
    service: summarizeService(toServiceRow(svc)),
    modalType,
    popupId,
    screenId,
    popupName,
    isExistingService: isExistingService(svc),
  })

  if (isSettingButtonDisabled(svc)) {
    console.warn(`${getLogPrefix('부가서비스설정')} 진행 중단`, {
      reason: 'setting disabled',
      service: summarizeService(toServiceRow(svc)),
      modalType,
      popupId,
      screenId,
      popupName,
    })
    return
  }

  settingModalType.value = modalType
  settingServiceId.value = getServiceKey(svc)
  showSettingModal.value = true

  console.log(`${getLogPrefix('부가서비스설정')} 화면 데이터 반영 결과`, {
    settingModalType: settingModalType.value,
    popupId,
    screenId,
    popupName,
    settingServiceId: settingServiceId.value,
  })
}

const closeSettingModal = () => {
  console.log(`${getLogPrefix('부가서비스설정')} 닫기`, {
    settingModalType: settingModalType.value,
    settingServiceId: settingServiceId.value,
  })
  showSettingModal.value = false
  settingModalType.value = ''
  settingServiceId.value = ''
}

// 현재 설정 팝업에서 다루는 서비스 객체
const currentSettingService = computed(() =>
  allActiveServices.value.find((svc) => svc.rateCd === settingServiceId.value) || {},
)

// 설정 팝업에 전달할 기존 설정값 (서비스 정보 + 저장된 설정 데이터 병합)
const currentSettingData = computed(() => ({
  ...currentSettingService.value,
  ...currentSettingService.value.addSvcSettingData,
}))

// autoDefault 상품 자동 처리: 팝업 없이 설정완료 처리
const handleAutoDefault = (svc = {}) => {
  const code = getServiceKey(svc).toUpperCase()
  let ftrNewParam = ''

  // autoDefault 상품별 기본값 설정
  if (code === 'ITCRBS') {
    ftrNewParam = 'ON' // 국제전화 수신차단 ON 고정
  } else if (['RNGTOUPR3', 'SKCOREPAC', 'XRINGMON', 'XRINGWEEK'].includes(code)) {
    ftrNewParam = 'GENRE=*' // 오토링/핵심팩 기본값 (장르 = 추천)
  }

  svc.addSvcSettingCompleted = true
  svc.addSvcSettingData = { ftrNewParam }

  console.log(`${getLogPrefix('부가서비스자동처리')} autoDefault 처리 완료`, {
    rateCd: code,
    ftrNewParam,
  })
}

// 설정 팝업 확인 시 설정값 저장 및 서비스 선택 처리
const applySettingData = (settingData = {}) => {
  console.log(`${getLogPrefix('부가서비스설정저장')} 요청 시작`, {
    settingServiceId: settingServiceId.value,
    settingModalType: settingModalType.value,
    settingData,
  })

  const target = allActiveServices.value.find((svc) => svc.rateCd === settingServiceId.value)

  if (target) {
    if (settingData?.isReset) {
      target.addSvcSettingCompleted = false
      target.addSvcSettingData = {}
      isServiceConfirmCompleted.value = false
      syncAdditionModel()
      closeSettingModal()
      return
    }
    target.addSvcSettingCompleted = true
    target.addSvcSettingData = settingData
    // 당일 개통 서비스가 아니면 자동 선택 처리
    if (!isCancelDisabled(target)) {
      selectedServiceIds.value = Array.from(new Set([...selectedServiceIds.value, target.rateCd]))
    }
    isServiceConfirmCompleted.value = false
    syncAdditionModel()
  } else {
    console.warn(`${getLogPrefix('부가서비스설정저장')} 진행 중단`, {
      reason: 'target service missing',
      settingServiceId: settingServiceId.value,
    })
  }

  console.log(`${getLogPrefix('부가서비스설정저장')} 화면 데이터 반영 결과`, {
    target: target ? summarizeService(target) : null,
    isExistingService: target ? isExistingService(target) : false,
    settingChangedCount: selectedSettingChangedServices.value.length,
    settingChangedServices: selectedSettingChangedServices.value.map(summarizeService),
    selectedTotalAmount: selectedTotalAmount.value,
    settingCompleted: target?.addSvcSettingCompleted === true,
  })

  closeSettingModal()
}

// ─── 서비스 행 정규화 ─────────────────────────────────────────────────────────

const getServiceAmountLabel = (svc = {}) => {
  const amount = toNumber(getServiceAmount(svc))
  const unit = svc.chargeUnit || svc.rateUnit || ''
  if (amount === 0) return '무료'
  return `${amount.toLocaleString()} 원${unit ? `/${unit}` : ''}`
}

// 서비스 객체를 화면 표시용 행 데이터로 정규화
// settingYn 미포함 시(MspAdditionDto 등) 코드/명칭으로 자동 감지하여 'Y' 부여
const toServiceRow = (svc = {}, index = 0) => {
  const baseAmt = getServiceAmount(svc)
  return {
    ...svc,
    rateCd: getServiceKey(svc, index),
    rateNm: getServiceName(svc),
    baseAmt,
    settingYn: svc.settingYn || (getSettingModalType(svc) ? 'Y' : 'N'),
  }
}

const getPersistedAdditionRecords = () =>
  Array.isArray(model.value.additionList) ? model.value.additionList : []

const getPersistedSettingRecordMap = () =>
  new Map(
    getPersistedAdditionRecords()
      .filter((svc) => svc.rateCd && (
        svc.addSvcSettingCompleted ||
        (svc.addSvcSettingData != null && Object.keys(svc.addSvcSettingData).length > 0) ||
        svc.ftrNewParam
      ))
      .map((svc) => [svc.rateCd, svc]),
  )

const mergePersistedSettingData = (svc = {}, persistedSettingMap = getPersistedSettingRecordMap()) => {
  const persisted = persistedSettingMap.get(svc.rateCd)
  if (!persisted) return svc

  const persistedSettingData =
    persisted.addSvcSettingData || (persisted.ftrNewParam ? { ftrNewParam: persisted.ftrNewParam } : {})

  return {
    ...svc,
    ftrNewParam: persisted.ftrNewParam || svc.ftrNewParam,
    addSvcSettingCompleted:
      persisted.addSvcSettingCompleted === true ||
      (persisted.addSvcSettingData != null && Object.keys(persisted.addSvcSettingData).length > 0) ||
      !!persisted.ftrNewParam,
    addSvcSettingData: {
      ...(svc.addSvcSettingData || {}),
      ...persistedSettingData,
    },
  }
}

const getRestoredAddedServices = (activeIds = new Set(), persistedSettingMap = getPersistedSettingRecordMap()) =>
  getPersistedAdditionRecords()
    .filter((svc) => (svc.action || 'ADD') === 'ADD' && svc.rateCd && !activeIds.has(svc.rateCd))
    .map((svc, index) => mergePersistedSettingData(toServiceRow(svc, index), persistedSettingMap))

// myaddsvclist 응답 list를 baseAmt 기준으로 무료/유료로 분리
const splitActiveServices = (list = []) => {
  const services = list.map(toServiceRow)
  return {
    freeAddition: services.filter((svc) => toNumber(svc.baseAmt) === 0),
    paidAddition: services.filter((svc) => toNumber(svc.baseAmt) !== 0),
  }
}

// ─── Computed ─────────────────────────────────────────────────────────────────

// 화면에 표시되는 전체 서비스 목록 (기존 무료 + 기존 유료 + 팝업 추가분)
const allActiveServices = computed(() => [
  ...activeFreeServices.value,
  ...activePaidServices.value,
  ...addedServices.value,
])

// 부가서비스 팝업에 전달할 이용중 서비스 코드 목록 (팝업 추가분 포함)
const activeFreeIds = computed(() => [
  ...activeFreeServices.value.map((svc) => svc.rateCd),
  ...addedServices.value.filter((svc) => toNumber(svc.baseAmt) === 0).map((svc) => svc.rateCd),
])
const activePaidIds = computed(() => [
  ...activePaidServices.value.map((svc) => svc.rateCd),
  ...addedServices.value.filter((svc) => toNumber(svc.baseAmt) !== 0).map((svc) => svc.rateCd),
])

// 선택된 유료 서비스 목록 (합계 금액 계산용)
const selectedPaidServices = computed(() =>
  allActiveServices.value.filter(
    (svc) =>
      selectedServiceIds.value.includes(svc.rateCd) &&
      !isCancelDisabled(svc) &&
      !isJoinDisabled(svc) &&
      toNumber(svc.baseAmt) !== 0,
  ),
)

// 선택된 유료 서비스 합계 금액
const selectedTotalAmount = computed(() =>
  selectedPaidServices.value.reduce((acc, cur) => acc + toNumber(cur.baseAmt), 0),
)

// 확인 버튼 레이블 (토글 상태에 따라 전환)
const confirmButtonLabel = computed(() => (isServiceConfirmCompleted.value ? '확인완료' : '확인'))

// selectedServiceIds에 포함된 서비스 목록
const selectedServices = computed(() =>
  allActiveServices.value.filter((svc) => selectedServiceIds.value.includes(svc.rateCd)),
)

// 팝업으로 추가했고 설정 완료된 신규 가입 대상
const selectedJoinServices = computed(() =>
  selectedServices.value.filter((svc) => isAddedService(svc) && !isJoinDisabled(svc)),
)

// 기존 이용중 서비스 중 설정 변경됨 + 선택 유지 → 선해지 후 재신청 대상
const selectedSettingChangedServices = computed(() =>
  allActiveServices.value.filter(
    (svc) =>
      isExistingService(svc) &&
      isSettingChanged(svc) &&
      !isCancelDisabled(svc) &&
      selectedServiceIds.value.includes(svc.rateCd),
  ),
)

// ─── 서비스 상태 판단 함수 ────────────────────────────────────────────────────

// 신규 추가 + 선택됨 + 설정 완료 → "추가" 뱃지
const isJoinSelected = (svc = {}) =>
  isAddedService(svc) && selectedServiceIds.value.includes(svc.rateCd) && !isJoinDisabled(svc)

// 기존 서비스 + 해지 가능 + 선택 해제됨 → "해지" 뱃지
const isCancelSelected = (svc = {}) =>
  isExistingService(svc) &&
  !isCancelDisabled(svc) &&
  !selectedServiceIds.value.includes(svc.rateCd)

const getServiceChangeLabel = (svc = {}) => {
  if (isJoinSelected(svc)) return '추가'
  if (isCancelSelected(svc)) return '해지'
  return ''
}

const isOnlineCancelUnavailable = (svc = {}) =>
  onlineCancelUnavailableServiceIds.value.includes(svc.rateCd)

const isPreCheckFailed = (svc = {}) => preCheckFailedServiceIds.value.includes(svc.rateCd)

const isPreCheckPassed = (svc = {}) => preCheckPassedServiceIds.value.includes(svc.rateCd)

const isSettingChanged = (svc = {}) =>
  svc.addSvcSettingCompleted === true &&
  svc.addSvcSettingData != null &&
  Object.keys(svc.addSvcSettingData).length > 0

// 기존 서비스는 "변경", 신규 추가 서비스는 "설정완료" 뱃지 (해지 선택 시 숨김)
const getSettingChangeLabel = (svc = {}) => {
  if (!isSettingChanged(svc)) return ''
  if (isCancelSelected(svc)) return ''
  return isExistingService(svc) ? '변경' : '설정완료'
}

const getServiceRowClass = (svc = {}) => ({
  'is-service-added': isJoinSelected(svc),
  'is-service-cancel': isCancelSelected(svc),
  'is-service-precheck-passed': isPreCheckPassed(svc),
  'is-service-cancel-unavailable': isOnlineCancelUnavailable(svc),
  'is-service-precheck-failed': isPreCheckFailed(svc),
  'is-service-setting-changed': isSettingChanged(svc),
  'is-service-disabled': isCancelDisabled(svc) || isJoinDisabled(svc),
})

const syncAdditionModel = () => {
  const toAdditionRecord = (svc = {}, action = '', overrides = {}) => ({
    ...summarizeService(svc),
    prodHstSeq: getProductSeqNo(svc),
    ftrNewParam: getFtrNewParam(svc),
    addSvcSettingCompleted: svc.addSvcSettingCompleted === true,
    addSvcSettingData: svc.addSvcSettingData || {},
    action,
    ...overrides,
  })

  const joinServices = selectedJoinServices.value.map((svc) => toAdditionRecord(svc, 'ADD'))
  const cancelServices = allActiveServices.value
    .filter(isCancelSelected)
    .map((svc) => toAdditionRecord(svc, 'CANCEL'))
  // 기존 서비스 설정 변경: 신청 payload에 flag='Y'를 세팅해 백엔드 regSvcChg에서 선해지 후 재신청 처리한다.
  const settingChangedAddList = selectedSettingChangedServices.value.map((svc) =>
    toAdditionRecord(svc, 'ADD', { flag: 'Y' }),
  )

  // additionList: 신규가입(ADD) + 설정변경(ADD, flag='Y' → 백엔드 선해지+재신청)
  // additionCancelList: 단순해지(CANCEL)만 — 설정변경은 flag='Y' 선해지로 처리
  model.value.additionList = [...joinServices, ...settingChangedAddList]
  model.value.additionCancelList = cancelServices
  model.value.additionConfirmCompleted = isServiceConfirmCompleted.value

  const summarizeAddRecord = (svc) => ({
    rateCd: svc.rateCd,
    rateNm: svc.rateNm,
    action: svc.action,
    flag: svc.flag || '',
    ftrNewParam: svc.ftrNewParam || '',
    prodHstSeq: svc.prodHstSeq || '',
  })
  console.log(`${getLogPrefix('모델동기화')} additionList 구성`, {
    joinCount: joinServices.length,
    settingChangedCount: settingChangedAddList.length,
    cancelCount: cancelServices.length,
  })
  console.table(model.value.additionList.map(summarizeAddRecord))
}

// addedServices 중 설정 미완료 서비스 존재 여부
const hasIncompleteSettingService = computed(() =>
  addedServices.value.some((svc) => needsAdditionalInfo(svc) && svc.addSvcSettingCompleted !== true),
)

// 확인 버튼 비활성화 조건:
// - 부가서비스 사전체크 진행중
// - 설정 미완료 서비스 존재 (settingYn=Y이고 설정 팝업 미완료)
const isConfirmButtonDisabled = computed(
  () => isPreChecking.value || hasIncompleteSettingService.value,
)

// 확인완료 상태이면 목록 전체 잠금 (체크박스·설정 버튼 비활성화)
const isListDisabled = computed(() => isServiceConfirmCompleted.value)

// ─── 부가서비스 사전체크 페이로드 생성 ────────────────────────────────────────

const getPhoneNo = () =>
  `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`

const getProductSeqNo = (svc = {}) =>
  String(svc.prdcSeqNo || svc.prodHstSeq || svc.prodSeqNo || svc.productSeqNo || svc.svcSeqNo || '')

// 설정 데이터를 M플랫폼 ftrNewParam 형식으로 변환
// 각 모달에서 직접 ftrNewParam을 emit하므로 폴백 로직 제거
const getFtrNewParam = (svc = {}) => {
  const settingData = svc.addSvcSettingData || {}
  if (svc.ftrNewParam) return String(svc.ftrNewParam)
  if (settingData.ftrNewParam) return String(settingData.ftrNewParam)
  return ''
}

const postAdditionPreCheck = async (payload) => {
  const baseUrl = `${import.meta.env.VITE_MSF_API_URL || ''}`.replace(/\/$/, '')
  const userStore = useMsfUserStore()
  const headers = {
    'Content-Type': 'application/json',
    Accept: 'application/json',
  }

  if (userStore.token) {
    headers.Authorization = `Bearer ${userStore.token}`
  }

  const response = await fetch(`${baseUrl}/api/form/servicechange/moscPrdcTrtmPreChk`, {
    method: 'POST',
    headers,
    credentials: 'include',
    body: JSON.stringify(payload),
  })

  const data = await response.json().catch(() => null)
  if (!response.ok) {
    return data || { code: String(response.status), message: response.statusText }
  }
  return data
}

const postMyAddSvcList = async (payload) => {
  const baseUrl = `${import.meta.env.VITE_MSF_API_URL || ''}`.replace(/\/$/, '')
  const userStore = useMsfUserStore()
  const headers = {
    'Content-Type': 'application/json',
    Accept: 'application/json',
  }

  if (userStore.token) {
    headers.Authorization = `Bearer ${userStore.token}`
  }

  const response = await fetch(`${baseUrl}/api/form/servicechange/myaddsvclist`, {
    method: 'POST',
    headers,
    credentials: 'include',
    body: JSON.stringify(payload),
  })

  const data = await response.json().catch(() => null)
  if (!response.ok) {
    return data || { code: String(response.status), message: response.statusText }
  }
  return data
}

// moscPrdcTrtmPreChk 요청 페이로드 생성
const createMoscPrdcTrtmPreChkPayload = (services = []) => ({
  ncn: model.value.ncn || model.value.contractNum || '',
  ctn: getPhoneNo(),
  custId: model.value.custId || '',
  actCode: 'SRG',
  prdcList: services.map((svc) => ({
    prdcCd: svc.rateCd,
    prdcSbscTrtmCd: svc.prdcSbscTrtmCd || 'A',
    prdcTypeCd: 'R',
    prdcSeqNo: getProductSeqNo(svc),
    ftrNewParam: getFtrNewParam(svc),
  })),
})

// ─── 사전체크 응답 실패 메시지 추출 ───────────────────────────────────────────

// 응답 객체를 깊이 우선 탐색하여 지정 필드명의 값을 반환
const findResponseField = (source, fieldNames, depth = 0) => {
  if (!source || typeof source !== 'object' || depth > 5) return ''
  for (const fieldName of fieldNames) {
    if (source[fieldName] !== undefined && source[fieldName] !== null && source[fieldName] !== '') {
      return source[fieldName]
    }
  }
  for (const value of Object.values(source)) {
    const found = findResponseField(value, fieldNames, depth + 1)
    if (found !== '') return found
  }
  return ''
}

// 사전체크 응답에서 실패 사유 메시지 추출 (성공 시 빈 문자열 반환)
const getPreCheckFailureMessage = (res) => {
  const code = String(res?.code || '')
  const formResponse = res?.data || {}
  const resCode = String(formResponse?.resCode || '')
  const preCheckData = formResponse?.resData || {}
  const rsltCd = String(preCheckData?.rsltCd || '')
  const resultCode = String(preCheckData?.resultCode || '')
  const sbscYn = String(preCheckData?.sbscYn || '').toUpperCase()

  if (code === '0000' && !res?.data) {
    return '부가서비스 가입 가능 여부를 확인할 수 없습니다.'
  }
  if (code && code !== '0000') {
    return findResponseField(res, ['message', 'resMessage', 'resltMsg']) || '부가서비스 가입이 불가합니다.'
  }
  if (resCode && resCode !== '0000') {
    return formResponse?.resMessage || findResponseField(res, ['resltMsg', 'svcMsg', 'message']) || '부가서비스 가입이 불가합니다.'
  }
  if (rsltCd && rsltCd !== '0000') {
    return preCheckData?.resltMsg || preCheckData?.svcMsg || formResponse?.resMessage || '부가서비스 가입이 불가합니다.'
  }
  if (resultCode && resultCode !== '0000') {
    return preCheckData?.resltMsg || preCheckData?.svcMsg || formResponse?.resMessage || '부가서비스 가입이 불가합니다.'
  }
  if (sbscYn && sbscYn !== 'Y') {
    return preCheckData?.resltMsg || preCheckData?.svcMsg || formResponse?.resMessage || '부가서비스 가입이 불가합니다.'
  }
  return ''
}

const escapeHtml = (value) =>
  String(value ?? '')
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;')

const getPreCheckResCode = (res) => String(res?.data?.resCode || '')

const normalizeFailedServiceIds = (value) => {
  if (Array.isArray(value)) return value.map((id) => String(id)).filter(Boolean)
  if (typeof value === 'string') {
    return value
      .split(',')
      .map((id) => id.trim())
      .filter(Boolean)
  }
  return value ? [String(value)] : []
}

const normalizeMessageList = (value) => {
  if (Array.isArray(value)) return value.map((message) => String(message)).filter(Boolean)
  if (typeof value === 'string') {
    return value
      .split(/\r?\n|,/)
      .map((message) => message.trim())
      .filter(Boolean)
  }
  return value ? [String(value)] : []
}

const getPreCheckFailedServiceIds = (res) => {
  const resData = res?.data?.resData || {}
  const ids = normalizeFailedServiceIds(resData?.prdcCdList)
  if (ids.length > 0) return ids
  return normalizeFailedServiceIds(resData?.prdcCd || resData?.soc)
}

const getTypedPreCheckFailureServiceIds = (res, allServiceIds = []) => {
  const resData = res?.data?.resData || {}
  const preCheckFailedServiceIds = normalizeFailedServiceIds(resData?.preCheckFailedPrdcCdList)
  const onlineCancelUnavailableServiceIds = normalizeFailedServiceIds(
    resData?.onlineCancelUnavailablePrdcCdList,
  )

  if (preCheckFailedServiceIds.length > 0 || onlineCancelUnavailableServiceIds.length > 0) {
    return { preCheckFailedServiceIds, onlineCancelUnavailableServiceIds }
  }

  if (getPreCheckResCode(res) === '6102') {
    return { preCheckFailedServiceIds: [], onlineCancelUnavailableServiceIds: allServiceIds }
  }
  return { preCheckFailedServiceIds: allServiceIds, onlineCancelUnavailableServiceIds: [] }
}

const getPreCheckFailureMessages = (res) => {
  const resData = res?.data?.resData || {}
  return normalizeMessageList(resData?.resltMsgList)
}

const createPreCheckFailure = (res) => {
  const message = getPreCheckFailureMessage(res)
  if (!message) return null

  const resCode = getPreCheckResCode(res)
  const serviceIds = getPreCheckFailedServiceIds(res)
  const typedServiceIds = getTypedPreCheckFailureServiceIds(res, serviceIds)
  const messages = getPreCheckFailureMessages(res)

  return {
    message,
    messages,
    resCode,
    serviceIds,
    serviceIdSet: new Set(serviceIds),
    preCheckFailedServiceIds: typedServiceIds.preCheckFailedServiceIds,
    onlineCancelUnavailableServiceIds: typedServiceIds.onlineCancelUnavailableServiceIds,
    isOnlineCancelUnavailable: resCode === '6102',
  }
}

const getServicesByFailureIds = (services = [], failure) => {
  if (!failure?.serviceIds?.length) return services
  return services.filter((svc) => failure.serviceIdSet.has(svc.rateCd))
}

const getServicesByIds = (services = [], serviceIds = []) => {
  if (serviceIds.length === 0) return []
  const serviceIdSet = new Set(serviceIds)
  return services.filter((svc) => serviceIdSet.has(svc.rateCd))
}

const buildFailureDisplayMessages = (failure, services = []) => {
  const serviceMap = new Map(services.map((svc) => [svc.rateCd, getServiceName(svc)]))
  const messages = failure?.messages || []

  return failure.serviceIds.map((serviceId, index) => {
    const serviceName = serviceMap.get(serviceId) || '부가서비스'
    const message = messages[index] || failure.message || '처리할 수 없습니다.'
    return `${serviceName}: ${message}`
  })
}

const getPreCheckFailureAlert = (failure, services = []) => {
  const displayMessages = buildFailureDisplayMessages(failure, services)
  if (displayMessages.length <= 1) {
    return { message: displayMessages[0] || failure?.message || '부가서비스 가입이 불가합니다.' }
  }
  const hasPreCheckFailed = failure.preCheckFailedServiceIds.length > 0
  const hasOnlineCancelUnavailable = failure.onlineCancelUnavailableServiceIds.length > 0
  let message = '부가서비스 사전체크 실패 항목이 있습니다.'
  if (hasPreCheckFailed && hasOnlineCancelUnavailable) {
    message = '부가서비스 처리 가능 여부를 확인해 주세요.'
  } else if (hasOnlineCancelUnavailable) {
    message = '온라인 해지가 불가한 부가서비스가 있습니다.'
  }

  return {
    message,
    subMessage: displayMessages.map((message) => `- ${escapeHtml(message)}`).join('<br>'),
  }
}

// ─── 서비스 선택 관련 ─────────────────────────────────────────────────────────

// 사전체크 실패 시 해당 서비스를 선택 목록에서 제거
const removeSelectedServices = (services = []) => {
  const removeIds = new Set(services.map((svc) => svc.rateCd))
  selectedServiceIds.value = selectedServiceIds.value.filter((id) => !removeIds.has(id))
  isServiceConfirmCompleted.value = false
  syncAdditionModel()
}

const restoreCancelServices = (services = []) => {
  const restoreIds = services.map((svc) => svc.rateCd).filter(Boolean)
  if (restoreIds.length === 0) return
  selectedServiceIds.value = Array.from(new Set([...selectedServiceIds.value, ...restoreIds]))
  isServiceConfirmCompleted.value = false
  syncAdditionModel()
}

const setPreCheckResultState = ({
  scopeServices = [],
  preCheckPassedServices = [],
  onlineCancelUnavailableServices = [],
  preCheckFailedServices = [],
} = {}) => {
  const scopeIds = scopeServices.map((svc) => svc.rateCd).filter(Boolean)
  if (scopeIds.length > 0) {
    removePreCheckResultStateByIds(scopeIds)
  }

  preCheckPassedServiceIds.value = Array.from(
    new Set([
      ...preCheckPassedServiceIds.value,
      ...preCheckPassedServices.map((svc) => svc.rateCd).filter(Boolean),
    ]),
  )
  onlineCancelUnavailableServiceIds.value = Array.from(
    new Set([
      ...onlineCancelUnavailableServiceIds.value,
      ...onlineCancelUnavailableServices.map((svc) => svc.rateCd).filter(Boolean),
    ]),
  )
  preCheckFailedServiceIds.value = Array.from(
    new Set([
      ...preCheckFailedServiceIds.value,
      ...preCheckFailedServices.map((svc) => svc.rateCd).filter(Boolean),
    ]),
  )
}

const clearPreCheckResultState = () => {
  preCheckPassedServiceIds.value = []
  onlineCancelUnavailableServiceIds.value = []
  preCheckFailedServiceIds.value = []
}

const removePreCheckResultStateByIds = (serviceIds = []) => {
  if (serviceIds.length === 0) return
  const changedServiceIdSet = new Set(serviceIds)
  preCheckPassedServiceIds.value = preCheckPassedServiceIds.value.filter(
    (id) => !changedServiceIdSet.has(id),
  )
  onlineCancelUnavailableServiceIds.value = onlineCancelUnavailableServiceIds.value.filter(
    (id) => !changedServiceIdSet.has(id),
  )
  preCheckFailedServiceIds.value = preCheckFailedServiceIds.value.filter(
    (id) => !changedServiceIdSet.has(id),
  )
}

const getChangedServiceIds = (newValue = [], oldValue = []) => {
  const oldIds = new Set(oldValue || [])
  const newIds = new Set(newValue || [])
  return Array.from(new Set([...newValue, ...oldValue])).filter(
    (id) => oldIds.has(id) !== newIds.has(id),
  )
}

const applyPreCheckFailure = (failure, { preCheckServices = [], cancelServices = [] } = {}) => {
  const fallbackFailedServices = getServicesByFailureIds(preCheckServices, failure)
  const failedCancelServices = getServicesByFailureIds(cancelServices, failure)
  const failedJoinServices = getServicesByFailureIds(selectedJoinServices.value, failure)
  const onlineCancelUnavailableServices = getServicesByIds(
    cancelServices,
    failure.onlineCancelUnavailableServiceIds,
  )
  const failedServiceIdSet = new Set(failure.serviceIds)
  const preCheckPassedServices = cancelServices.filter((svc) => !failedServiceIdSet.has(svc.rateCd))
  let preCheckFailedServices = []
  if (failure.preCheckFailedServiceIds.length > 0) {
    preCheckFailedServices = getServicesByIds(preCheckServices, failure.preCheckFailedServiceIds)
  } else if (failure.onlineCancelUnavailableServiceIds.length === 0) {
    preCheckFailedServices = fallbackFailedServices
  }

  console.warn(`${getLogPrefix('부가서비스체크')} 진행 중단`, {
    reason: 'precheck failed',
    failureMessage: failure.message,
    resCode: failure.resCode,
    failedServiceIds: failure.serviceIds,
    failedMessages: failure.messages,
    services: preCheckServices.map(summarizeService),
  })

  const failureAlert = getPreCheckFailureAlert(failure, preCheckServices)
  showAlert(failureAlert.message, undefined, failureAlert.subMessage)

  isApplyingPreCheckResult = true
  restoreCancelServices(failedCancelServices)
  removeSelectedServices(failedJoinServices)
  setPreCheckResultState({
    scopeServices: preCheckServices,
    preCheckPassedServices,
    onlineCancelUnavailableServices,
    preCheckFailedServices,
  })
  queueMicrotask(() => {
    isApplyingPreCheckResult = false
  })
}

// 이용중 서비스 조회 후 초기 선택 동기화
// 해지불가(당일 개통) 및 설정 미완료 서비스는 자동 제외
// autoDefault는 자동처리되므로 설정완료 상태 → 선택에 포함
const syncSelectedServices = () => {
  // autoDefault 상품 초기화 시 자동 처리
  allActiveServices.value
    .filter((svc) => getSettingModalType(svc) === 'autoDefault' && !svc.addSvcSettingCompleted)
    .forEach((svc) => handleAutoDefault(svc))

  const serviceIds = allActiveServices.value
    .filter((svc) => !isCancelDisabled(svc) && !isJoinDisabled(svc))
    .map((svc, index) => getServiceKey(svc, index))
  selectedServiceIds.value = serviceIds
  isServiceConfirmCompleted.value = false
  syncAdditionModel()

  console.log(`${getLogPrefix('선택초기화')} 화면 데이터 반영 결과`, {
    totalCount: allActiveServices.value.length,
    selectedCount: serviceIds.length,
    selectedServiceIds: selectedServiceIds.value,
    selectedTotalAmount: selectedTotalAmount.value,
  })
}

// ─── 이벤트 핸들러 ────────────────────────────────────────────────────────────

// 부가서비스 팝업 확인 시: 이미 이용중인 서비스 제외하고 addedServices에 추가
const onVasConfirm = ({ freeServices = [], paidServices = [] }) => {
  console.log(`${getLogPrefix('부가서비스추가')} 요청 시작`, {
    freeCount: freeServices.length,
    paidCount: paidServices.length,
    selectedServiceIds: selectedServiceIds.value,
  })

  const existingIds = new Set(allActiveServices.value.map((svc) => svc.rateCd))
  const newServices = [...freeServices, ...paidServices]
    .map(toServiceRow)
    .filter((svc) => !existingIds.has(svc.rateCd))

  console.log(`${getLogPrefix('부가서비스추가')} 신규 필터링`, {
    inputCount: freeServices.length + paidServices.length,
    newCount: newServices.length,
    skippedCount: freeServices.length + paidServices.length - newServices.length,
    newServices: newServices.map(summarizeService),
  })

  // autoDefault 상품 자동 처리
  const autoDefaultServices = newServices.filter((svc) => getSettingModalType(svc) === 'autoDefault')
  autoDefaultServices.forEach((svc) => handleAutoDefault(svc))

  newServices.forEach((svc) => addedServices.value.push(svc))
  onlineCancelUnavailableServiceIds.value = onlineCancelUnavailableServiceIds.value.filter((id) =>
    allActiveServices.value.some((svc) => svc.rateCd === id),
  )
  preCheckFailedServiceIds.value = preCheckFailedServiceIds.value.filter((id) =>
    allActiveServices.value.some((svc) => svc.rateCd === id),
  )

  // 설정 미완료 서비스는 선택 목록에 추가하지 않음 (확인 버튼 비활성화 유지)
  // autoDefault는 자동처리되므로 설정완료 상태 → 선택 목록에 추가
  const addedIds = newServices.filter((svc) => !isJoinDisabled(svc)).map((svc) => svc.rateCd)
  selectedServiceIds.value = Array.from(new Set([...selectedServiceIds.value, ...addedIds]))
  isServiceConfirmCompleted.value = false
  syncAdditionModel()

  console.log(`${getLogPrefix('부가서비스추가')} 화면 데이터 반영 결과`, {
    addedCount: newServices.length,
    autoDefaultCount: autoDefaultServices.length,
    pendingSettingCount: newServices.filter((svc) => isJoinDisabled(svc)).length,
    addedServices: newServices.map(summarizeService),
    selectedServiceIds: selectedServiceIds.value,
    selectedTotalAmount: selectedTotalAmount.value,
  })
}

// ─── 확인 버튼 핸들러 ─────────────────────────────────────────────────────────

/**
 * 부가서비스 사전체크 실행
 * - joinServices    : 신규 가입 대상 (prdcSbscTrtmCd: A)
 * - cancelServices  : 해지 대상     (prdcSbscTrtmCd: C)
 * - settingChangedServices : 설정 변경 대상 (선해지 C + 재신청 A)
 * @returns {Promise<boolean>} true = 통과, false = 실패/오류
 */
const runAdditionPreCheck = async ({ joinServices = [], cancelServices = [], settingChangedServices = [] } = {}) => {
  const preCheckServices = [
    ...joinServices.map((svc) => ({ ...svc, prdcSbscTrtmCd: 'A' })),
    ...cancelServices.map((svc) => ({ ...svc, prdcSbscTrtmCd: 'C' })),
    // 설정 변경: 선해지(C) + 재신청(A) — 6102(온라인해지불가) 사전 감지 포함
    ...settingChangedServices.map((svc) => ({ ...svc, prdcSbscTrtmCd: 'C' })),
    ...settingChangedServices.map((svc) => ({ ...svc, prdcSbscTrtmCd: 'A' })),
  ]
  const requestSeq = ++preCheckRequestSeq

  try {
    isPreChecking.value = true
    const payload = createMoscPrdcTrtmPreChkPayload(preCheckServices)
    console.log(`${getLogPrefix('부가서비스체크')} 사전체크 요청`, payload)
    const res = await postAdditionPreCheck(payload)
    console.log(`${getLogPrefix('부가서비스체크')} 사전체크 응답`, {
      code: res?.code,
      message: res?.message,
      data: res?.data,
    })

    if (requestSeq !== preCheckRequestSeq) {
      console.warn(`${getLogPrefix('부가서비스체크')} 이전 응답 무시`, { requestSeq, preCheckRequestSeq })
      return false
    }

    const failure = createPreCheckFailure(res)
    if (failure) {
      applyPreCheckFailure(failure, {
        preCheckServices,
        cancelServices: [...cancelServices, ...settingChangedServices],
      })
      return false
    }

    setPreCheckResultState({
      scopeServices: preCheckServices,
      preCheckPassedServices: preCheckServices,
    })
    return true
  } catch (error) {
    if (requestSeq !== preCheckRequestSeq) {
      console.warn(`${getLogPrefix('부가서비스체크')} 이전 예외 무시`, { requestSeq, preCheckRequestSeq })
      return false
    }
    console.error(`${getLogPrefix('부가서비스체크')} 예외 발생`, {
      message: error?.message,
      response: error?.response?.data,
    })
    showAlert(
      error?.response?.data?.message ||
        error?.response?.data?.data?.resMessage ||
        '부가서비스 가입 가능 여부 확인 중 오류가 발생했습니다.',
    )
    return false
  } finally {
    if (requestSeq === preCheckRequestSeq) {
      isPreChecking.value = false
    }
  }
}

const buildSaveConfirmSubMessage = (joinServices = [], cancelServices = [], settingChangedServices = []) => {
  const lines = []
  if (joinServices.length > 0) {
    lines.push(`추가 ${joinServices.length}건: ${joinServices.map((s) => escapeHtml(s.rateNm)).join(', ')}`)
  }
  if (cancelServices.length > 0) {
    lines.push(`해지 ${cancelServices.length}건: ${cancelServices.map((s) => escapeHtml(s.rateNm)).join(', ')}`)
  }
  if (settingChangedServices.length > 0) {
    lines.push(`설정변경 ${settingChangedServices.length}건: ${settingChangedServices.map((s) => escapeHtml(s.rateNm)).join(', ')}`)
  }
  return lines.map((l) => `- ${l}`).join('<br>')
}

// [확인] 버튼 처리 흐름:
// 1. 이미 확인완료 상태 → 토글 off (잠금 해제)
// 2. 변경사항 없음 → 확인 다이얼로그 → 사용자 확인 후 완료
// 3. 변경사항 있음 → runAdditionPreCheck → 통과 시 저장 목록 확인 다이얼로그 → 확인완료
const completeSelectedServices = async () => {
  if (isServiceConfirmCompleted.value) {
    isServiceConfirmCompleted.value = false
    console.log(`${getLogPrefix('부가서비스체크')} 확인완료 해제 (잠금 해제)`)
    return
  }

  const cancelServices = allActiveServices.value.filter(isCancelSelected)

  console.log(`${getLogPrefix('부가서비스체크')} 요청 시작`, {
    selectedServiceIds: selectedServiceIds.value,
    selectedJoinServices: selectedJoinServices.value.map(summarizeService),
    selectedCancelServices: cancelServices.map(summarizeService),
    selectedSettingChangedServices: selectedSettingChangedServices.value.map(summarizeService),
    hasIncompleteSettingService: hasIncompleteSettingService.value,
  })

  if (isConfirmButtonDisabled.value) {
    console.warn(`${getLogPrefix('부가서비스체크')} 진행 중단`, {
      reason: 'confirm disabled',
      isPreChecking: isPreChecking.value,
      hasIncompleteSettingService: hasIncompleteSettingService.value,
    })
    return
  }

  if (selectedJoinServices.value.length === 0 && cancelServices.length === 0 && selectedSettingChangedServices.value.length === 0) {
    const confirmMsg = '변경 사항이 없습니다. 계속 진행하시겠습니까?'
    console.log(`${getLogPrefix('부가서비스체크')} 확인 다이얼로그 표시`, { confirmMsg })
    showConfirm(confirmMsg, () => {
      isServiceConfirmCompleted.value = true
      syncAdditionModel()
      console.log(`${getLogPrefix('부가서비스체크')} 화면 데이터 반영 결과`, {
        reason: 'user confirmed (no changes)',
        isServiceConfirmCompleted: isServiceConfirmCompleted.value,
        selectedServiceIds: selectedServiceIds.value,
      })
    })
    return
  }

  const passed = await runAdditionPreCheck({
    joinServices: selectedJoinServices.value,
    cancelServices,
    settingChangedServices: selectedSettingChangedServices.value,
  })
  if (!passed) return

  const subMsg = buildSaveConfirmSubMessage(
    selectedJoinServices.value,
    cancelServices,
    selectedSettingChangedServices.value,
  )
  showConfirm('다음 항목을 처리합니다. 확인하시겠습니까?', () => {
    isServiceConfirmCompleted.value = true
    syncAdditionModel()
    console.log(`${getLogPrefix('부가서비스체크')} 화면 데이터 반영 결과`, {
      isServiceConfirmCompleted: isServiceConfirmCompleted.value,
      joinCount: selectedJoinServices.value.length,
      cancelCount: cancelServices.length,
      settingChangedCount: selectedSettingChangedServices.value.length,
      selectedServiceIds: selectedServiceIds.value,
      selectedTotalAmount: selectedTotalAmount.value,
    })
  }, subMsg)
}

// ─── API 호출 ─────────────────────────────────────────────────────────────────

// 전체 부가서비스 카탈로그 조회 (POST /api/form/addition/list) — 팝업에 전달할 선택 가능 목록
const fetchAllVasList = async () => {
  const baseUrl = `${import.meta.env.VITE_MSF_API_URL || ''}`.replace(/\/$/, '')
  const userStore = useMsfUserStore()
  const headers = {
    'Content-Type': 'application/json',
    Accept: 'application/json',
  }
  if (userStore.token) {
    headers.Authorization = `Bearer ${userStore.token}`
  }
  try {
    const response = await fetch(`${baseUrl}/api/form/addition/list`, {
      method: 'POST',
      headers,
      credentials: 'include',
      body: JSON.stringify({
        operTypeCd: '',
        prodCtgTypeCd: 'R',
        categoryMstRequest: { prodCtgId: ['RFREESVC', 'RRATESVC'] },
      }),
    })
    const res = await response.json().catch(() => null)
    if (res && res.code === '0000' && res.data?.[0]) {
      const result = res.data[0]
      allFreeVasServices.value = result.freeAddition || []
      allPaidVasServices.value = result.paidAddition || []
    }
  } catch (error) {
    console.error(`${getLogPrefix('전체부가서비스조회')} 예외 발생`, { message: error?.message })
  }
}

// 부가서비스 추가 버튼 클릭: 전체 카탈로그 조회 후 팝업 오픈
const openVasModal = async () => {
  if (isListDisabled.value) return
  await fetchAllVasList()
  if (isListDisabled.value) return
  isVasModalOpen.value = true
}

// M플랫폼 X97 경유 이용중 부가서비스 목록 조회 (POST /api/form/servicechange/myaddsvclist)
// ncn 또는 전화번호 미충족 시 목록 초기화 후 종료
const fetchActiveServices = async () => {
  const phoneNo = `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`
  const ncn = model.value.ncn || model.value.contractNum || ''
  const payload = {
    ncn,
    ctn: phoneNo,
    custId: model.value.custId || '',
  }

  console.log(`${getLogPrefix('이용중부가서비스조회')} 요청 준비`, payload)

  if (!ncn || phoneNo.length < 10) {
    console.warn(`${getLogPrefix('이용중부가서비스조회')} 진행 중단`, {
      reason: 'required value missing',
      hasNcn: !!ncn,
      phoneNoLength: phoneNo.length,
      hasCustId: !!payload.custId,
    })
    queriedFreeServices.value = []
    queriedPaidServices.value = []
    activeFreeServices.value = []
    activePaidServices.value = []
    addedServices.value = []
    selectedServiceIds.value = []
    clearPreCheckResultState()
    isServiceConfirmCompleted.value = false
    syncAdditionModel()
    return
  }

  try {
    console.log(`${getLogPrefix('이용중부가서비스조회')} 요청 시작`, payload)
    const res = await postMyAddSvcList(payload)
    console.log(`${getLogPrefix('이용중부가서비스조회')} 응답 수신`, {
      code: res?.code,
      message: res?.message,
      data: res?.data,
    })

    const formResponse = Array.isArray(res?.data) ? res.data[0] : res?.data
    const result = formResponse?.resData
    const persistedSettingMap = getPersistedSettingRecordMap()

    if (res && res.code === '0000' && formResponse?.resCode === '0000' && result) {
      const normalized = result?.list ? splitActiveServices(result.list) : result
      queriedFreeServices.value = (normalized?.freeAddition || [])
        .map(toServiceRow)
        .map((svc) => mergePersistedSettingData(svc, persistedSettingMap))
      queriedPaidServices.value = (normalized?.paidAddition || [])
        .map(toServiceRow)
        .map((svc) => mergePersistedSettingData(svc, persistedSettingMap))
      activeFreeServices.value = [...queriedFreeServices.value]
      activePaidServices.value = [...queriedPaidServices.value]
      const activeIds = new Set([...activeFreeServices.value, ...activePaidServices.value].map((svc) => svc.rateCd))
      addedServices.value = getRestoredAddedServices(activeIds, persistedSettingMap)
      clearPreCheckResultState()
      syncSelectedServices()
      console.log(`${getLogPrefix('이용중부가서비스조회')} 화면 데이터 반영 결과`, {
        freeCount: activeFreeServices.value.length,
        paidCount: activePaidServices.value.length,
        restoredAddedCount: addedServices.value.length,
        selectedServiceIds: selectedServiceIds.value,
        selectedTotalAmount: selectedTotalAmount.value,
        freeServices: activeFreeServices.value.map(summarizeService),
        paidServices: activePaidServices.value.map(summarizeService),
        restoredAddedServices: addedServices.value.map(summarizeService),
      })
    } else {
      console.warn(`${getLogPrefix('이용중부가서비스조회')} 진행 중단`, {
        reason: 'empty response data',
        code: res?.code,
        message: res?.message,
        resCode: formResponse?.resCode,
        resMessage: formResponse?.resMessage,
      })
      queriedFreeServices.value = []
      queriedPaidServices.value = []
      activeFreeServices.value = []
      activePaidServices.value = []
      addedServices.value = []
      selectedServiceIds.value = []
      clearPreCheckResultState()
      isServiceConfirmCompleted.value = false
      syncAdditionModel()
    }
  } catch (error) {
    console.error(`${getLogPrefix('이용중부가서비스조회')} 예외 발생`, {
      message: error?.message,
      response: error?.response?.data,
    })
    queriedFreeServices.value = []
    queriedPaidServices.value = []
    activeFreeServices.value = []
    activePaidServices.value = []
    addedServices.value = []
    selectedServiceIds.value = []
    clearPreCheckResultState()
    isServiceConfirmCompleted.value = false
    syncAdditionModel()
  }
}

// ─── Watch ────────────────────────────────────────────────────────────────────

// 가입자 인증 정보 변경 시 서비스 목록 재조회
watch(
  () => [
    model.value.deviceChgTel1,
    model.value.deviceChgTel2,
    model.value.deviceChgTel3,
    model.value.ncn,
    model.value.contractNum,
    model.value.custId,
  ],
  (newValue, oldValue) => {
    console.log(`${getLogPrefix('가입자정보변경')} 감지`, { oldValue: [...(oldValue || [])], newValue: [...(newValue || [])] })
    fetchActiveServices()
  },
)

// 선택 변경 시 비활성화 서비스 자동 제거 및 확인완료 초기화
watch(
  () => selectedServiceIds.value,
  (newValue, oldValue) => {
    const disabledServiceIds = new Set(
      allActiveServices.value
        .filter((svc) => isCancelDisabled(svc) || isJoinDisabled(svc))
        .map((svc) => svc.rateCd),
    )
    const enabledSelectedIds = newValue.filter((id) => !disabledServiceIds.has(id))

    if (enabledSelectedIds.length !== newValue.length) {
      console.warn(`${getLogPrefix('선택변경')} 비활성화 서비스 자동 제거`, {
        removedIds: newValue.filter((id) => disabledServiceIds.has(id)),
      })
      selectedServiceIds.value = enabledSelectedIds
      return
    }

    if (JSON.stringify(newValue) !== JSON.stringify(oldValue)) {
      isServiceConfirmCompleted.value = false
      if (!isApplyingPreCheckResult) {
        removePreCheckResultStateByIds(getChangedServiceIds(newValue, oldValue))
      }
    }

    console.log(`${getLogPrefix('선택변경')} 화면 데이터 반영 결과`, {
      changedServiceIds: getChangedServiceIds(newValue, oldValue).join(', '),
      selectedCount: (newValue || []).length,
      selectedTotalAmount: selectedTotalAmount.value,
    })
    syncAdditionModel()
  },
)

// 확인완료 상태 변경 시 model에 동기화 → 부모(ServiceChangeProduct)가 다음 버튼 활성화에 사용
watch(isServiceConfirmCompleted, (val) => {
  if (val) isVasModalOpen.value = false
  model.value.additionConfirmCompleted = val
  syncAdditionModel()
  console.log(`${getLogPrefix('확인완료변경')} model 동기화`, { additionConfirmCompleted: val })
})


onMounted(() => {
  console.log(`${getLogPrefix('초기화')} mounted`)
  fetchActiveServices()
})
</script>

<template>
  <!-- 부가서비스 신청/변경 -->
  <MsfTitleArea title="부가서비스 신청/변경" />
  <MsfTable>
    <template #colgroup>
      <col style="width: 68px" />
      <col />
      <col style="width: 120px" />
      <col style="width: 112px" />
    </template>
    <template #thead>
      <tr>
        <th>선택</th>
        <th>부가서비스명</th>
        <th>요금</th>
        <th>설정</th>
      </tr>
    </template>
    <template #tbody>
      <template v-if="allActiveServices.length > 0">
        <tr v-for="svc in allActiveServices" :key="svc.rateCd" :class="getServiceRowClass(svc)">
          <td class="ut-text-center">
            <MsfCheckbox
              :id="`inp-addition-${svc.rateCd}`"
              v-model="selectedServiceIds"
              :value="svc.rateCd"
              :label="svc.rateNm"
              :disabled="isListDisabled || isCancelDisabled(svc) || isJoinDisabled(svc)"
              hideLabel
            />
          </td>
          <td>
            <div class="service-name-wrap">
              <label :for="`inp-addition-${svc.rateCd}`">{{ svc.rateNm }}</label>
              <span
                v-if="getServiceChangeLabel(svc)"
                class="service-change-badge"
                :class="{
                  'is-added': isJoinSelected(svc),
                  'is-cancel': isCancelSelected(svc),
                }"
              >
                {{ getServiceChangeLabel(svc) }}
              </span>
              <span
                v-if="getSettingChangeLabel(svc)"
                class="service-change-badge is-setting-changed"
              >
                {{ getSettingChangeLabel(svc) }}
              </span>
              <span v-if="isPreCheckPassed(svc)" class="service-change-badge is-precheck-passed">
                처리가능
              </span>
              <span
                v-if="isOnlineCancelUnavailable(svc)"
                class="service-change-badge is-cancel-unavailable"
              >
                온라인해지 불가
              </span>
              <span v-if="isPreCheckFailed(svc)" class="service-change-badge is-precheck-failed">
                사전체크 실패
              </span>
            </div>
          </td>
          <td class="ut-text-center">{{ getServiceAmountLabel(svc) }}</td>
          <td class="ut-text-center">
            <MsfButton
              variant="subtle"
              v-if="svc.settingYn === 'Y' && getSettingModalType(svc) && getSettingModalType(svc) !== 'autoDefault'"
              :disabled="isListDisabled || isSettingButtonDisabled(svc)"
              @click="openSettingModal(svc)"
            >
              설정
            </MsfButton>
          </td>
        </tr>
      </template>
      <tr v-else>
        <td colspan="4">
          <div class="nodata-wrap">선택한 서비스가 없습니다.</div>
        </td>
      </tr>
    </template>
  </MsfTable>
  <!-- 합계박스 -->
  <MsfBox>
    <div class="total-box">
      <dl>
        <dt>합계(VAT 포함)</dt>
        <dd>
          <em>{{ selectedTotalAmount.toLocaleString() }}</em
          ><span class="unit">원</span>
        </dd>
      </dl>
    </div>
    <MsfButtonGroup class="total-btns">
      <MsfButton variant="subtle" :disabled="isListDisabled" @click="openVasModal">부가서비스 추가</MsfButton>
      <MsfButton
        variant="toggle"
        :active="isServiceConfirmCompleted"
        :disabled="isConfirmButtonDisabled"
        @click="completeSelectedServices"
      >
        {{ confirmButtonLabel }}
      </MsfButton>
    </MsfButtonGroup>
  </MsfBox>
  <!-- // 합계박스 -->
  <!-- // 부가서비스 신청/변경 -->

  <MsfVasManageModal
    v-model="isVasModalOpen"
    :free-services="allFreeVasServices"
    :paid-services="allPaidVasServices"
    :active-free-ids="activeFreeIds"
    :active-paid-ids="activePaidIds"
    :phone-number="`${model.deviceChgTel1 || ''}${model.deviceChgTel2 || ''}${model.deviceChgTel3 || ''}`"
    :ncn="model.ncn || model.contractNum || ''"
    @confirm="onVasConfirm"
  />
  <!-- NOSPAM4: 불법TM 수신차단 -->
  <IllegalTmBlockModal
    :model-value="showSettingModal && settingModalType === 'illegalTm'"
    :max-count="50"
    :min-length="0"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- NOSPAM2: 특정번호 수신차단 (NOSPAM4와 동일 컴포넌트, 다른 props) -->
  <IllegalTmBlockModal
    v-if="settingModalType === 'blockNumber100'"
    :model-value="showSettingModal"
    title="특정번호 수신차단서비스"
    :max-count="100"
    :min-length="3"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- NOSPAM3: 정보제공사업자번호차단 -->
  <MsfInfoProviderBlockModal
    v-if="settingModalType === 'infoProviderBlock'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- STLPVTPHN: 번호도용 차단 -->
  <NumberSpoofingBlockModal
    :model-value="showSettingModal && settingModalType === 'numberSpoofing'"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- 로밍 시작일 (8자리) -->
  <RoamingStartDateModal
    v-if="settingModalType === 'roamingDate8'"
    :model-value="showSettingModal"
    variant="date8"
    :setting-data="currentSettingData"
    :service-name="currentSettingService.rateNm"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- 로밍 기간설정 (시작일시+종료일) -->
  <RoamingStartDateModal
    v-if="settingModalType === 'roamingDateRange'"
    :model-value="showSettingModal"
    variant="dateTimeRange"
    :setting-data="currentSettingData"
    :service-name="currentSettingService.rateNm"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- 함께쓰는 로밍 대표 -->
  <MsfRoamingShareMainModal
    v-if="settingModalType === 'roamingShareMain1'"
    :model-value="showSettingModal"
    variant="main1"
    :setting-data="currentSettingData"
    :main-phone-number="getPhoneNo()"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- 하루종일 로밍 베이직 투게더 대표 -->
  <MsfRoamingShareMainModal
    v-if="settingModalType === 'roamingShareMain2'"
    :model-value="showSettingModal"
    variant="main2"
    :setting-data="currentSettingData"
    :main-phone-number="getPhoneNo()"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- 함께쓰는 로밍 서브 -->
  <MsfRoamingShareSubModal
    v-if="settingModalType === 'roamingShareSub1'"
    :model-value="showSettingModal"
    variant="sub1"
    :service-name="currentSettingService.rateNm"
    :setting-data="currentSettingData"
    :ncn="model.ncn || model.contractNum || ''"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- 하루종일 로밍 베이직 투게더 서브 -->
  <MsfRoamingShareSubModal
    v-if="settingModalType === 'roamingShareSub2'"
    :model-value="showSettingModal"
    variant="sub2"
    :setting-data="currentSettingData"
    :ncn="model.ncn || model.contractNum || ''"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- DATAROMSM: 데이터로밍요금알림 -->
  <MsfNotifyPhoneModal
    v-if="settingModalType === 'notifyPhone'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- FCARVLSMS: 로밍 해외도착알리미 -->
  <MsfAlertNumbersModal
    v-if="settingModalType === 'alertNumbers'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- SENOINFR1: 망내 1회선 무료통화 -->
  <MsfFreeCallNumberModal
    v-if="settingModalType === 'freeCallNumber'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- PL253A854: My time plan_MVNO 전용 -->
  <MsfMilitaryTimePlanModal
    v-if="settingModalType === 'militaryTimePlan'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
</template>

<style lang="scss" scoped>
.service-name-wrap {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  min-width: 0;
}

.service-change-badge {
  display: inline-flex;
  align-items: center;
  height: 20px;
  padding: 0 7px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 600;
  line-height: 1;
  white-space: nowrap;

  &.is-added {
    color: #0b5cab;
    background: #e8f3ff;
    border: 1px solid #b9dbff;
  }

  &.is-cancel {
    color: #b42318;
    background: #fff1f0;
    border: 1px solid #ffccc7;
  }

  &.is-setting-changed {
    color: #7a4a00;
    background: #fff7e6;
    border: 1px solid #ffd591;
  }

  &.is-precheck-passed {
    color: #067647;
    background: #ecfdf3;
    border: 1px solid #abefc6;
  }

  &.is-cancel-unavailable {
    color: #6941c6;
    background: #f4f3ff;
    border: 1px solid #d9d6fe;
  }

  &.is-precheck-failed {
    color: #9f1239;
    background: #fff1f2;
    border: 1px solid #fecdd3;
  }
}

:deep(tr.is-service-added td) {
  background: #f7fbff;
}

:deep(tr.is-service-cancel td) {
  background: #fffafa;
}

:deep(tr.is-service-setting-changed td) {
  background: #fffdf7;
}

:deep(tr.is-service-precheck-passed td) {
  background: #f6fef9;
}

:deep(tr.is-service-cancel-unavailable td) {
  background: #fbfaff;
}

:deep(tr.is-service-precheck-failed td) {
  background: #fff8f9;
}

:deep(tr.is-service-cancel label) {
  color: #6b7280;
}

:deep(tr.is-service-disabled label) {
  color: #9ca3af;
}
</style>
