<script setup>
import { computed, defineModel, nextTick, onMounted, ref, watch } from 'vue'
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

// ─── 부가서비스 설정 정책 ─────────────────────────────────────────────────────
// 1. SETTING_MODAL_MAP 등록되고 settingYn=Y인 서비스만 설정 대상이다.
//    단, 매핑된 서비스는 원본 settingYn이 없더라도 화면 정규화 과정에서 Y로 보정한다.
// 2. 신규 추가 서비스는 설정 버튼을 노출하며, 필수 설정 완료 전까지 선택 및 확인을 막는다.
// 3. 기존 이용중 서비스는 onlineCanYn=Y인 설정 가능 유형만 설정 버튼을 노출한다.
// 4. EXISTING_SETTING_CANCEL_EXCEPTIONS 등록된 SOC는 onlineCanYn과 무관하게 기존 설정 변경을 허용한다.
// 5. EXISTING_SETTING_HIDDEN_SERVICES 등록된 SOC는 기존 이용중일 때 설정 버튼을 숨기고 해지만 허용한다.
// 6. autoDefault 유형은 팝업 없이 기본 설정값을 자동 반영하며, 기존 이용중 서비스는 변경 대상으로 처리하지 않는다.
// 7. 기존 설정 변경은 additionList에 action=ADD, flag=Y로 저장하여 백엔드에서 선해지 후 재가입 처리한다.

// ─── 로그 접두사 ──────────────────────────────────────────────────────────────
const getLogPrefix = (task) => `[변경][부가 신청변경][${task}]`


// ─── 상태 (State) ─────────────────────────────────────────────────────────────
const model = defineModel({ type: Object, required: true })
const emit = defineEmits(['ready'])

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
const selfCareUnavailableServiceIds = ref([])
const preCheckUncheckedServiceIds = ref([])
const pendingPreCheckPassedServices = ref([])
let preCheckRequestSeq = 0
let isApplyingPreCheckResult = false

// ─── 유틸리티 함수 ────────────────────────────────────────────────────────────

const toNumber = (value) => Number(String(value || 0).replace(/,/g, '')) || 0

// 여러 API 응답 필드명(rateCd·soc·prodId 등)을 단일 서비스 코드로 정규화
const getServiceKey = (svc = {}, index = 0) => {
  const serviceKey =
    svc.rateCd || svc.value || svc.soc || svc.prodId || svc.addSvcCd || svc.additionId
  return serviceKey ? String(serviceKey) : index == null ? '' : `service-${index}`
}

const getSettingYn = (svc = {}) => {
  const value = svc.settingYn ?? svc.SETTING_YN ?? svc.setYn ?? svc.SET_YN
  return ['Y', '1', 'TRUE'].includes(String(value || '').trim().toUpperCase()) ? 'Y' : 'N'
}

// 여러 API 응답 필드명(rateNm·socDescription 등)을 단일 서비스명으로 정규화
const getServiceName = (svc = {}) =>
  svc.rateNm || svc.RATE_NM || svc.socDescription || svc.SOC_DESCRIPTION || svc.prodNm || svc.PROD_NM || svc.addSvcNm || svc.ADD_SVC_NM || svc.serviceName || '-'

// 여러 API 응답 필드명(baseAmt·socRateVat 등)을 단일 금액으로 정규화
const getServiceAmount = (svc = {}) =>
  svc.baseAmt ?? svc.BASE_AMT ?? svc.socRateValue ?? svc.SOC_RATE_VALUE ?? svc.socRateVatValue ?? svc.SOC_RATE_VAT_VALUE ?? svc.socRateVat ?? svc.SOC_RATE_VAT ?? 0

const getVatIncludedAmount = (svc = {}) => {
  const vatAmount = svc.socRateVatValue ?? svc.SOC_RATE_VAT_VALUE ?? svc.socRateVat ?? svc.SOC_RATE_VAT ?? svc.mmBasAmtVatDesc ?? svc.MM_BAS_AMT_VAT_DESC ?? svc.baseAmtVat ?? svc.BASE_AMT_VAT
  if (vatAmount != null && vatAmount !== '') return toNumber(vatAmount)
  const amount = toNumber(getServiceAmount(svc))
  return amount === 0 ? 0 : Math.round(amount * 1.1)
}

const isDailyFlatRateService = (svc = {}) => {
  const values = [
    svc.chargeUnit,
    svc.rateUnit,
    svc.chargePeriod,
    svc.ratePeriod,
    svc.periodUnit,
    svc.periodUnitNm,
    svc.feeType,
    svc.chargeType,
    svc.chargeTypeCd,
    svc.rateType,
    svc.rateTypeCd,
    svc.socChargeType,
    svc.socChargeTypeCd,
    svc.addSvcChargeTypeCd,
    svc.addSvcRateTypeCd,
    svc.dailyRateYn,
    svc.dayRateYn,
    svc.dayChargeYn,
    svc.dailyChargeYn,
    svc.oneDayYn,
    svc.oneDayChargeYn,
    svc.flatRatePeriod,
    svc.flatRatePeriodUnit,
    svc.flatRatePeriodUnitNm,
    svc.periodType,
    svc.periodTypeCd,
  ].map((value) => String(value || '').trim())

  return values.some((value) => {
    const upperValue = value.toUpperCase()
    return (
      ['일정액', '일', '1일', 'D', 'DAY', 'DAILY', 'Y'].includes(upperValue) ||
      value.includes('일정액') ||
      value.includes('일요금') ||
      value.includes('1일')
    )
  })
}

const getServiceAmountUnit = (svc = {}) => {
  if (isDailyFlatRateService(svc)) return '1일'
  return svc.chargeUnit || svc.rateUnit || ''
}

const getDailyAdditionPeriod = (daily = {}) =>
  daily.USE_PRD || daily.usePrd || daily.usePeriod || daily.period || ''

const getServicePeriodLabel = (svc = {}) => {
  const dailyPeriod =
    svc.usePrd ||
    svc.USE_PRD ||
    svc.usePeriodDays ||
    svc.dailyUsePrd ||
    getDailyAdditionPeriod(svc.dailyAddition)
  if (dailyPeriod) {
    const period = String(dailyPeriod).trim()
    if (period.includes('일') || period.includes('월')) return period
    return /^\d+$/.test(period) ? `${period}일` : period
  }
  const unit = getServiceAmountUnit(svc)
  return unit || ''
}

const createDailyAdditionMap = (dailyAdditions = []) =>
  new Map(
    dailyAdditions
      .map((daily) => [getServiceKey(daily, null), daily])
      .filter(([code]) => Boolean(code)),
  )

const mergeDailyAdditionInfo = (svc = {}, dailyAdditionMap = new Map()) => {
  const daily = dailyAdditionMap.get(getServiceKey(svc, null))
  if (!daily) return svc

  return {
    ...svc,
    dailyRateYn: 'Y',
    usePeriodDays: getDailyAdditionPeriod(daily),
    dailyAddition: daily,
    chargeUnit: '1일',
  }
}

const appendDailyOnlyAdditions = (services = [], dailyAdditions = []) => {
  const serviceCodes = new Set(services.map((svc) => getServiceKey(svc, null)).filter(Boolean))
  const dailyOnlyServices = dailyAdditions
    .filter((daily) => {
      const code = getServiceKey(daily, null)
      return code && !serviceCodes.has(code)
    })
    .map((daily) =>
      mergeDailyAdditionInfo(
        {
          rateCd: getServiceKey(daily, null),
          rateNm: daily.RATE_NM || daily.rateNm || getServiceKey(daily, null),
          baseAmt: daily.BASE_AMT ?? daily.baseAmt ?? 0,
          additionKey: daily.ADDITION_KEY ?? daily.additionKey,
          sortOrdr: daily.SORT_ORDR ?? daily.sortOrdr ?? 9999,
        },
        createDailyAdditionMap([daily]),
      ),
    )

  return [...services, ...dailyOnlyServices]
}

const createEmptyAdditionCatalog = () => ({
  freeAddition: [],
  paidAddition: [],
  dailyAddition: [],
})

// const getTodayYmd = () => {
//   const now = new Date()
//   const year = now.getFullYear()
//   const month = String(now.getMonth() + 1).padStart(2, '0')
//   const date = String(now.getDate()).padStart(2, '0')
//   return `${year}${month}${date}`
// }

// const getServiceStartDate = (svc = {}) =>
//   String(svc.strtDt || svc.startDate || svc.appStartDd || svc.effectiveDate || '').replace(/\D/g, '')

// 당일 개통 서비스 여부 (ASIS 정책: 당일 개통 서비스는 온라인 해지 불가)
// const isStartedToday = (svc = {}) => getServiceStartDate(svc).slice(0, 8) === getTodayYmd()

// 해지 비활성화 조건: 당일 개통 서비스
const isCancelDisabled = (/* svc = {} */) => {
  // 확인용 임시 처리: 당일 등록건 비활성화 조건 해제
  // return isStartedToday(svc)
  return false
}

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
  chargeUnit: svc.chargeUnit || '',
  rateUnit: svc.rateUnit || '',
  dailyRateYn: svc.dailyRateYn || '',
  usePrd: svc.usePrd || '',
  settingYn: svc.settingYn,
})

const mapSelectedServiceLog = () => [
  ...selectedJoinServices.value,
  ...allActiveServices.value.filter(isCancelSelected),
  ...selectedSettingChangedServices.value,
].map((svc) => ({
  부가서비스코드: getServiceKey(svc),
  코드명: getServiceName(svc),
  금액: String(getServiceAmount(svc)),
  금액표시: getServiceAmountLabel(svc),
  일요금판단: isDailyFlatRateService(svc),
  요금단위: getServiceAmountUnit(svc),
  chargeUnit: svc.chargeUnit || '',
  rateUnit: svc.rateUnit || '',
  periodUnit: svc.periodUnit || '',
  periodType: svc.periodType || '',
  dailyRateYn: svc.dailyRateYn || '',
  usePrd: svc.usePrd || '',
}))

// const mapSettingServiceLog = (serviceIds = []) =>
//   serviceIds
//     .map((id) => allActiveServices.value.find((svc) => svc.rateCd === id))
//     .filter((svc) => svc && (svc.settingYn === 'Y' || !!getSettingModalType(svc)))
//     .map((svc) => ({
//       부가서비스코드: getServiceKey(svc),
//       코드명: getServiceName(svc),
//       설정여부: svc.settingYn || '',
//       설정타입: getSettingModalType(svc) || '',
//       설정완료: svc.addSvcSettingCompleted === true,
//       선택상태: selectedServiceIds.value.includes(svc.rateCd) ? '선택' : '해제',
//       기존이용중: isExistingService(svc),
//       당일개통해지불가: isCancelDisabled(svc),
//       추가설정미완료: isJoinDisabled(svc),
//       설정버튼비활성: isSettingButtonDisabled(svc),
//     }))

const mapAllServiceLog = () =>
  allActiveServices.value.map((svc) => ({
    부가서비스코드: getServiceKey(svc),
    코드명: getServiceName(svc),
    금액표시: getServiceAmountLabel(svc),
    //요금단위: getServiceAmountUnit(svc),
    //설정여부: svc.settingYn || '',
    설정타입: getSettingModalType(svc) || '',
    //설정완료: svc.addSvcSettingCompleted === true,
    //선택상태: selectedServiceIds.value.includes(svc.rateCd) ? '선택' : '해제',
    //기존이용중: isExistingService(svc),
    //당일개통해지불가: isCancelDisabled(svc),
    //추가설정미완료: isJoinDisabled(svc),
    //설정버튼비활성: isSettingButtonDisabled(svc),
  }))

// ─── 설정 팝업 관련 ───────────────────────────────────────────────────────────

// 상품코드 → 팝업 타입 매핑
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

// 사용자가 설정 팝업에서 값을 입력해야 하는 서비스인지 여부.
// autoDefault는 팝업 입력이 없으므로 설정 완료 여부로 확인 버튼을 막지 않는다.
const needsAdditionalInfo = (svc = {}) => {
  const modalType = getSettingModalType(svc)
  return getSettingYn(svc) === 'Y' && !!modalType && modalType !== 'autoDefault'
}

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

// MSP_RATE_MST의 온라인 해지 가능 여부와 무관하게 부가정보 변경을 허용하는 예외 SOC
const EXISTING_SETTING_CANCEL_EXCEPTIONS = new Set(['NOSPAM3', 'NOSPAM4'])

// 기존 이용중 서비스는 해지만 허용하고 부가정보 변경은 허용하지 않는 SOC
// 20260723 부가정보 변경 대상 제한 상품 추가
const EXISTING_SETTING_HIDDEN_SERVICES = new Set([
  'DYDTROM05', 'PL2078760', 'PL2079771', 'PL2079772',
  'DATAROM01', 'DATAROM03', 'DATAROMSM', 'DTRMCLIGT',
  'DTRMUTALK', 'FCARVLSMS', 'ITGSAFE3G', 'LTEDTROM5',
  'NOSPAM2',   'PL199N109', 'PL199N117', 'PL199N120',
  'PL199N121', 'PL199N122', 'PL199N123', 'PL199N126',
  'PL199N127', 'PL199N129', 'PL199N130', 'PL199N132',
  'PL199N133', 'PL2079777', 'PL2079778', 'PL253A854',
  'SENOINFR1', 'STLPVTPHN', 'USADTROMC'
])

const isSettingButtonVisible = (svc = {}) => {
  const modalType = getSettingModalType(svc)
  if (getSettingYn(svc) !== 'Y' || !modalType || modalType === 'autoDefault') return false
  if (!isExistingService(svc)) return true

  const serviceKey = getServiceKey(svc).toUpperCase()
  if (EXISTING_SETTING_HIDDEN_SERVICES.has(serviceKey)) return false

  const onlineCanYn = String(svc.onlineCanYn ?? svc.ONLINE_CAN_YN ?? '').trim().toUpperCase()
  return (
    EXISTING_SERVICE_SETTABLE.has(modalType) &&
    (onlineCanYn === 'Y' || EXISTING_SETTING_CANCEL_EXCEPTIONS.has(serviceKey))
  )
}

// 설정 버튼 비활성화 조건:
// - 설정 버튼 노출 대상이 아니면 비활성
// - 기존 이용중 서비스가 체크 해제되어 해지 대상이면 비활성
const isSettingButtonDisabled = (svc = {}) => {
  if (!isSettingButtonVisible(svc)) return true
  if (isExistingService(svc)) {
    if (!selectedServiceIds.value.includes(svc.rateCd)) return true
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
  // Vue post-flush 단계를 완전히 벗어난 후 팝업 열기 — nextTick은 flush 내에서 resolve되어 부족함
  requestAnimationFrame(() => {
    showSettingModal.value = true
  })

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
  showChangeCancel:
    isExistingService(currentSettingService.value) &&
    currentSettingService.value.addSvcSettingCompleted === true,
}))

const initialSettingData = computed(() => {
  const { addSvcSettingData, addSvcSettingCompleted, ...initialData } = currentSettingService.value
  return initialData
})

// autoDefault 상품 자동 처리: 팝업 없이 설정완료 처리
const normalizeDigits = (value) => String(value || '').replace(/\D/g, '')

const parseKeyValueParam = (param = '') =>
  String(param || '')
    .split('|')
    .map((item) => item.split('='))
    .reduce((result, [key, value]) => {
      if (key) result[String(key).trim()] = String(value || '').trim()
      return result
    }, {})

const getSortedValues = (params = {}, prefix = '') =>
  Object.keys(params)
    .filter((key) => new RegExp(`^${prefix}\\d+$`).test(key))
    .sort((leftKey, rightKey) => Number(leftKey.replace(/\D/g, '')) - Number(rightKey.replace(/\D/g, '')))
    .map((key) => ({ index: key.replace(/\D/g, ''), value: String(params[key] || '').trim() }))
    .filter(({ value }) => value)

const toBlockNumbersFromRows = (rows = []) =>
  rows.flatMap(({ number1, number2, number3 }) => {
    const values = []
    if (normalizeDigits(number1)) values.push(normalizeDigits(number1))
    if (normalizeDigits(number2)) values.push(`*${normalizeDigits(number2)}*`)
    if (normalizeDigits(number3)) values.push(`*${normalizeDigits(number3)}`)
    return values
  })

const getIllegalTmBlockNumbers = (settingData = {}) => {
  if (settingData.blockNumbers?.length) return settingData.blockNumbers.map(String).filter(Boolean)
  if (settingData.phoneNumbers?.length) return toBlockNumbersFromRows(settingData.phoneNumbers)

  const directBlckNo = getSortedValues(settingData.blckNoParams || settingData, 'BLCK_NO').map(({ value }) => value)
  if (directBlckNo.length) return directBlckNo

  const paramSbst = settingData.paramSbst || settingData.paramSbstCtt
  const savedBlckNo = getSortedValues(parseKeyValueParam(paramSbst), 'BLCK_NO').map(({ value }) => value)
  if (savedBlckNo.length) return savedBlckNo

  return String(settingData.ftrNewParam || '')
    .split(':')
    .map((value) => value.trim())
    .filter(Boolean)
}

const getInfoProviderBlockPairs = (settingData = {}) => {
  if (settingData.blockRows?.length) {
    return settingData.blockRows.flatMap((row) =>
      toBlockNumbersFromRows([row]).map((number) => `${number}:${row.blockType || '3'}`),
    )
  }

  const paramSbst = settingData.paramSbst || settingData.paramSbstCtt
  if (paramSbst) {
    const params = parseKeyValueParam(paramSbst)
    return getSortedValues(params, 'BLCK_NO')
      .map(({ index, value }) => `${value}:${params[`BLCK_TYPE${index}`] || '3'}`)
  }

  const parts = String(settingData.ftrNewParam || '')
    .split(':')
    .map((value) => value.trim())
    .filter(Boolean)

  return parts
    .filter((_, index) => index % 2 === 0)
    .map((number, index) => `${number}:${parts[index * 2 + 1] || '3'}`)
}

const getRoamingDateRangeKey = (settingData = {}) => {
  const params = parseKeyValueParam(settingData.paramSbst || settingData.paramSbstCtt)
  const start = params.STRT_DT || settingData.strtDt || settingData.startDt || settingData.startDateTime || ''
  const end = params.END_DT || settingData.endDt || settingData.endDateTime || ''

  if (start || end) {
    const startDigits = normalizeDigits(start)
    const endDigits = normalizeDigits(end)
    return `${startDigits.slice(0, 10)}:${endDigits.slice(0, 8)}`
  }

  return String(settingData.ftrNewParam || '').trim()
}

const getRoamingShareSettingKey = (settingData = {}, modalType = '') => {
  const params = parseKeyValueParam(settingData.paramSbst || settingData.paramSbstCtt)
  const ftrParts = String(settingData.ftrNewParam || '').split(':')
  const isMain2 = modalType === 'roamingShareMain2'
  const ftrHasEndDate = isMain2 && normalizeDigits(ftrParts[1]).length === 8

  const explicitStart = `${normalizeDigits(settingData.startDate)}${normalizeDigits(settingData.startHour)}`
  const start = normalizeDigits(
    params.STRT_DT ||
      settingData.strtDt ||
      settingData.startDt ||
      settingData.startDateTime ||
      explicitStart ||
      ftrParts[0],
  )
  const end = normalizeDigits(
    params.END_DT ||
      settingData.endDt ||
      settingData.endDateTime ||
      settingData.endDate ||
      (ftrHasEndDate ? ftrParts[1] : ''),
  )

  const savedSubNumbers = Array.isArray(settingData.subNumbers)
    ? settingData.subNumbers
    : Array.isArray(settingData.shareSubCtnList)
      ? settingData.shareSubCtnList
      : []
  const numberStartIndex = ftrHasEndDate ? 2 : 1
  const subNumbers = (savedSubNumbers.length > 0 ? savedSubNumbers : ftrParts.slice(numberStartIndex))
    .map(normalizeDigits)
    .filter(Boolean)

  return JSON.stringify({
    start: start.slice(0, isMain2 ? 10 : 8),
    end: isMain2 ? end.slice(0, 8) : '',
    subNumbers,
  })
}

const isSameSettingAsExisting = (svc = {}, settingData = {}) => {
  const modalType = getSettingModalType(svc)

  if (['illegalTm', 'blockNumber100'].includes(modalType)) {
    return JSON.stringify(getIllegalTmBlockNumbers(svc)) === JSON.stringify(getIllegalTmBlockNumbers(settingData))
  }

  if (modalType === 'infoProviderBlock') {
    return JSON.stringify(getInfoProviderBlockPairs(svc)) === JSON.stringify(getInfoProviderBlockPairs(settingData))
  }

  if (modalType === 'roamingDateRange') {
    return getRoamingDateRangeKey(svc) === getRoamingDateRangeKey(settingData)
  }

  if (['roamingShareMain1', 'roamingShareMain2'].includes(modalType)) {
    return getRoamingShareSettingKey(svc, modalType) === getRoamingShareSettingKey(settingData, modalType)
  }

  const originalFtrNewParam = String(svc.ftrNewParam || '').trim()
  const newFtrNewParam = String(settingData.ftrNewParam || '').trim()
  return !!originalFtrNewParam && originalFtrNewParam === newFtrNewParam
}

const handleAutoDefault = (svc = {}) => {
  const code = getServiceKey(svc).toUpperCase()
  let ftrNewParam = ''

  // autoDefault 상품별 기본값 설정
  if (code === 'ITCRBS') {
    ftrNewParam = 'ON' // 국제전화 수신차단 ON 고정
  } else if (code === 'RNGTOUPR3') {
    ftrNewParam = '' // 오토링 상품파람은 빈 값으로 요청
  } else if (['SKCOREPAC', 'XRINGMON', 'XRINGWEEK'].includes(code)) {
    ftrNewParam = '' // 핵심팩/링투유 상품파람도 빈 값으로 요청
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
    if (isExistingService(target) && isSameSettingAsExisting(target, settingData)) {
      target.addSvcSettingCompleted = false
      target.addSvcSettingData = {}
    } else {
      target.addSvcSettingCompleted = true
      target.addSvcSettingData = settingData
    }
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
  const amount = getVatIncludedAmount(svc)
  if (amount === 0) return '무료'
  const periodLabel = getServicePeriodLabel(svc)
  return `${amount.toLocaleString()}원${periodLabel ? ` / ${periodLabel}` : ''}`
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
    usePrd: svc.usePrd || svc.USE_PRD || '',
    usePeriodDays:
      svc.usePeriodDays || getDailyAdditionPeriod(svc.dailyAddition),
    chargeUnit: getServiceAmountUnit(svc),
    settingYn: (getSettingYn(svc) === 'Y' || !!getSettingModalType(svc)) ? 'Y' : 'N',
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
      ...svc.addSvcSettingData,
      ...persistedSettingData,
    },
  }
}

const getRestoredAddedServices = (activeIds = new Set(), persistedSettingMap = getPersistedSettingRecordMap()) =>
  getPersistedAdditionRecords()
    .filter((svc) => (svc.action || 'ADD') === 'ADD' && svc.rateCd && !activeIds.has(svc.rateCd))
    .map((svc, index) => mergePersistedSettingData(toServiceRow(svc, index), persistedSettingMap))

const mergeCatalogServiceInfo = (svc = {}) => {
  const serviceKey = getServiceKey(svc)
  const catalogSvc = [...allFreeVasServices.value, ...allPaidVasServices.value]
    .find((item) => getServiceKey(item) === serviceKey)
  if (!catalogSvc) return svc

  return {
    ...catalogSvc,
    ...svc,
    usePrd: svc.usePrd || svc.USE_PRD || '',
    usePeriodDays:
      svc.usePeriodDays ||
      catalogSvc.usePeriodDays ||
      getDailyAdditionPeriod(svc.dailyAddition || catalogSvc.dailyAddition),
    dailyAddition: svc.dailyAddition || catalogSvc.dailyAddition,
    chargeUnit: svc.chargeUnit || catalogSvc.chargeUnit,
  }
}

// myaddsvclist 응답 list를 baseAmt 기준으로 무료/유료로 분리
const splitActiveServices = (list = []) => {
  // AS-IS regServiceView.js와 동일하게 요금할인(음수 금액) 부가서비스는 화면에서 제외
  const services = list.filter((svc) => toNumber(svc.socRateVat) >= 0).map(toServiceRow)
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
  ...addedServices.value.filter((svc) => getVatIncludedAmount(svc) === 0).map((svc) => svc.rateCd),
])
const activePaidIds = computed(() => [
  ...activePaidServices.value.map((svc) => svc.rateCd),
  ...addedServices.value.filter((svc) => getVatIncludedAmount(svc) !== 0).map((svc) => svc.rateCd),
])

const modalExcludedFreeServices = computed(() => [
  ...activeFreeServices.value,
  ...addedServices.value.filter((svc) => getVatIncludedAmount(svc) === 0),
])

const modalExcludedPaidServices = computed(() => [
  ...activePaidServices.value,
  ...addedServices.value.filter((svc) => getVatIncludedAmount(svc) !== 0),
])

// 선택된 유료 서비스 목록 (합계 금액 계산용)
const selectedPaidServices = computed(() =>
  allActiveServices.value.filter(
    (svc) =>
      selectedServiceIds.value.includes(svc.rateCd) &&
      !isCancelDisabled(svc) &&
      !isJoinDisabled(svc) &&
      getVatIncludedAmount(svc) !== 0,
  ),
)

// 선택된 유료 서비스 합계 금액
const selectedTotalAmount = computed(() =>
  selectedPaidServices.value.reduce((acc, cur) => acc + getVatIncludedAmount(cur), 0),
)

// 확인 버튼 레이블 (토글 상태에 따라 전환)
const confirmButtonLabel = computed(() => {
  if (isPreChecking.value) return '진행중...'
  return isServiceConfirmCompleted.value ? '확인완료' : '확인'
})

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

const isOnlineCancelAvailable = (svc = {}) =>
  isExistingService(svc) &&
  String(svc.onlineCanYn ?? svc.ONLINE_CAN_YN ?? '').trim().toUpperCase() === 'Y'

const isOnlineCancelUnavailable = (svc = {}) =>
  onlineCancelUnavailableServiceIds.value.includes(svc.rateCd)

const isPreCheckFailed = (svc = {}) => preCheckFailedServiceIds.value.includes(svc.rateCd)

const isSelfCareUnavailable = (svc = {}) =>
  selfCareUnavailableServiceIds.value.includes(svc.rateCd)

const isPreCheckPassed = (svc = {}) => preCheckPassedServiceIds.value.includes(svc.rateCd)

const isPreCheckUnchecked = (svc = {}) => preCheckUncheckedServiceIds.value.includes(svc.rateCd)

const isPreCheckPassedForTreatment = (svc = {}, treatmentCode = '') =>
  preCheckPassedServiceIds.value.includes(`${svc.rateCd}:${treatmentCode}`)

const isSettingChanged = (svc = {}) =>
  svc.addSvcSettingCompleted === true &&
  svc.addSvcSettingData != null &&
  Object.keys(svc.addSvcSettingData).length > 0

const getServiceFlags = (svc = {}) => {
  const flags = []
  if (isJoinSelected(svc)) flags.push({ label: '추가', color: 'create' })
  if (isOnlineCancelAvailable(svc) && !isCancelSelected(svc) && !isSettingChanged(svc)) {
    flags.push({ label: '해지가능', color: 'cancelable' })
  }
  if (isCancelSelected(svc)) flags.push({ label: '해지', color: 'close' })
  if (isSettingChanged(svc) && isExistingService(svc) && !isCancelSelected(svc)) {
    flags.push({ label: '변경', color: 'change' })
  }
  if (isSettingChanged(svc) && !isExistingService(svc) && !isCancelSelected(svc)) {
    flags.push({ label: '설정완료', color: 'done' })
  }
  if (isPreCheckPassed(svc) && !isSelfCareUnavailable(svc) && !isPreCheckFailed(svc)) {
    flags.push({ label: '처리가능', color: 'ready' })
  }
  if (isSelfCareUnavailable(svc)) flags.push({ label: '판매점 해지 불가', color: 'selfcare' })
  if (isOnlineCancelUnavailable(svc)) flags.push({ label: '판매점 온라인해지 불가', color: 'locked' })
  if (isPreCheckFailed(svc) || isSelfCareUnavailable(svc)) flags.push({ label: '사전체크 실패', color: 'fail' })
  if (isPreCheckUnchecked(svc)) flags.push({ label: '체크미처리', color: 'locked' })
  return flags
}

const syncAdditionModel = () => {
  const toAdditionRecord = (svc = {}, action = '', overrides = {}) => ({
    ...summarizeService(svc),
    prodHstSeq: getProductSeqNo(svc),
    ftrNewParam: getFtrNewParam(svc),
    addSvcSettingCompleted: svc.addSvcSettingCompleted === true,
    addSvcSettingData: svc.addSvcSettingData || {},
    selfCareUnavailable: isSelfCareUnavailable(svc),
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
  // selfCareUnavailable=true 건은 화면 사전체크 실패 대상이다.
  model.value.additionList = [...joinServices, ...settingChangedAddList]
  model.value.additionCancelList = cancelServices
  model.value.additionConfirmCompleted = isServiceConfirmCompleted.value

  const summarizeAddRecord = (svc) => ({
    rateCd: svc.rateCd,
    rateNm: svc.rateNm,
    action: svc.action,
    flag: svc.flag || '',
    selfCareUnavailable: svc.selfCareUnavailable === true,
    ftrNewParam: svc.ftrNewParam || '',
    prodHstSeq: svc.prodHstSeq || '',
  })
  console.log(`${getLogPrefix('모델동기화')} additionList 구성`, {
    joinCount: joinServices.length,
    settingChangedCount: settingChangedAddList.length,
    cancelCount: cancelServices.length,
    selfCareUnavailableCount: [...joinServices, ...settingChangedAddList, ...cancelServices]
      .filter((svc) => svc.selfCareUnavailable === true).length,
  })
  console.table(model.value.additionList.map(summarizeAddRecord))
}

// addedServices 중 설정 미완료 서비스 존재 여부
const hasIncompleteSettingService = computed(() =>
  addedServices.value.some((svc) => needsAdditionalInfo(svc) && svc.addSvcSettingCompleted !== true),
)

const getCurrentProcessServices = (cancelServices = []) => [
  ...selectedJoinServices.value,
  ...cancelServices,
  ...selectedSettingChangedServices.value,
]

// 사전체크 결과 배열은 화면 표시용으로 유지된다.
// 따라서 확인완료 차단은 전체 결과가 아니라 "현재 처리 대상"에 남은 실패/미처리만 봐야 한다.
const getPreCheckBlockingState = (targetServices = []) => {
  const serviceIds = new Set(targetServices.map((svc) => svc.rateCd).filter(Boolean))

  // 처리 대상이 없으면 과거 실패 상태가 남아 있어도 다음 진행을 막지 않는다.
  if (serviceIds.size === 0) {
    return {
      hasBlockingResult: false,
      preCheckFailedServiceIds: [],
      onlineCancelUnavailableServiceIds: [],
      selfCareUnavailableServiceIds: [],
      preCheckUncheckedServiceIds: [],
    }
  }

  const blockingFailedIds = preCheckFailedServiceIds.value.filter((id) => serviceIds.has(id))
  const blockingOnlineCancelIds = onlineCancelUnavailableServiceIds.value.filter((id) => serviceIds.has(id))
  const blockingSelfCareUnavailableIds = selfCareUnavailableServiceIds.value.filter((id) => serviceIds.has(id))
  const blockingUncheckedIds = preCheckUncheckedServiceIds.value.filter((id) => serviceIds.has(id))

  return {
    hasBlockingResult:
      blockingFailedIds.length > 0 ||
      blockingOnlineCancelIds.length > 0 ||
      blockingSelfCareUnavailableIds.length > 0 ||
      blockingUncheckedIds.length > 0,
    preCheckFailedServiceIds: blockingFailedIds,
    onlineCancelUnavailableServiceIds: blockingOnlineCancelIds,
    selfCareUnavailableServiceIds: blockingSelfCareUnavailableIds,
    preCheckUncheckedServiceIds: blockingUncheckedIds,
  }
}

const logPreCheckBlockingState = (blockingState) => {
  console.warn(`${getLogPrefix('부가서비스체크')} 확인완료 중단`, {
    reason: 'blocking precheck result exists',
    preCheckFailedServiceIds: blockingState.preCheckFailedServiceIds,
    onlineCancelUnavailableServiceIds: blockingState.onlineCancelUnavailableServiceIds,
    selfCareUnavailableServiceIds: blockingState.selfCareUnavailableServiceIds,
    preCheckUncheckedServiceIds: blockingState.preCheckUncheckedServiceIds,
  })
}

const completeServiceConfirm = (logData = {}) => {
  isServiceConfirmCompleted.value = true
  syncAdditionModel()
  console.log(`${getLogPrefix('부가서비스체크')} 화면 데이터 반영 결과`, {
    isServiceConfirmCompleted: isServiceConfirmCompleted.value,
    selectedServiceIds: selectedServiceIds.value,
    selectedTotalAmount: selectedTotalAmount.value,
    ...logData,
  })
}

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
  if (
    ['RNGTOUPR3', 'SKCOREPAC', 'XRINGMON', 'XRINGWEEK'].includes(
      getServiceKey(svc).toUpperCase(),
    )
  ) {
    return ''
  }
  const settingData = svc.addSvcSettingData || {}
  if (Object.prototype.hasOwnProperty.call(settingData, 'ftrNewParam')) {
    return String(settingData.ftrNewParam || '')
  }
  if (svc.ftrNewParam) return String(svc.ftrNewParam)
  return ''
}

const postAdditionPreCheck = async (payload) => {
  const baseUrl = `${import.meta.env.VITE_MSF_BASE_URL || ''}`.replace(/\/$/, '')
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
  const baseUrl = `${import.meta.env.VITE_MSF_BASE_URL || ''}`.replace(/\/$/, '')
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

const normalizePreCheckResultList = (value) => {
  if (!Array.isArray(value)) return []
  return value
    .map((item) => ({
      prdcCd: String(item?.prdcCd || ''),
      successYn: String(item?.successYn || '').toUpperCase(),
      message: String(item?.message || ''),
    }))
    .filter((item) => item.prdcCd)
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

const uniqueValues = (values = []) => Array.from(new Set(values.filter(Boolean)))

const isSelfCareUnavailableMessage = (message = '') => {
  const text = String(message || '')
  return (text.includes('판매점') || text.includes('셀프케어')) && (text.includes('불가능') || text.includes('불가'))
}

const isSelfCareUnavailablePreCheckItem = (item = {}) =>
  item.successYn !== 'Y' && isSelfCareUnavailableMessage(item.message)

const createPreCheckFailure = (res) => {
  const message = getPreCheckFailureMessage(res)
  if (!message) return null

  const resData = res?.data?.resData || {}
  const preCheckResultList = normalizePreCheckResultList(resData?.preCheckResultList)
  const selfCareUnavailableServiceIds = uniqueValues(
    preCheckResultList.filter(isSelfCareUnavailablePreCheckItem).map((item) => item.prdcCd),
  )
  const resultFailedServiceIds = uniqueValues(
    preCheckResultList
      .filter((item) => item.successYn !== 'Y')
      .map((item) => item.prdcCd),
  )
  const resCode = getPreCheckResCode(res)
  const serviceIds = resultFailedServiceIds.length > 0
    ? resultFailedServiceIds
    : uniqueValues(getPreCheckFailedServiceIds(res))
  const typedServiceIds = getTypedPreCheckFailureServiceIds(res, serviceIds)
  const resultMessages = preCheckResultList
    .filter((item) => item.successYn !== 'Y')
    .map((item) => item.message)
    .filter(Boolean)
  const messages = resultMessages.length > 0 ? resultMessages : getPreCheckFailureMessages(res)

  return {
    message,
    messages,
    resCode,
    serviceIds,
    serviceIdSet: new Set(serviceIds),
    preCheckResultList,
    checkedServiceIds: uniqueValues(preCheckResultList.map((item) => item.prdcCd)),
    passedServiceIds: uniqueValues(
      preCheckResultList.filter((item) => item.successYn === 'Y').map((item) => item.prdcCd),
    ),
    selfCareUnavailableServiceIds,
    preCheckFailedServiceIds: resultFailedServiceIds.length > 0
      ? resultFailedServiceIds
      : uniqueValues([
        ...typedServiceIds.preCheckFailedServiceIds,
        ...selfCareUnavailableServiceIds,
      ]),
    onlineCancelUnavailableServiceIds: uniqueValues(typedServiceIds.onlineCancelUnavailableServiceIds),
    isOnlineCancelUnavailable: resCode === '6102',
  }
}

const getServicesByFailureIds = (services = [], failure) => {
  if (!failure?.serviceIds?.length) return []
  return services.filter((svc) => failure.serviceIdSet.has(svc.rateCd))
}

const getServicesByIds = (services = [], serviceIds = []) => {
  if (serviceIds.length === 0) return []
  const serviceIdSet = new Set(serviceIds)
  return services.filter((svc) => serviceIdSet.has(svc.rateCd))
}

const getServicesIdentifiedByFailureMessage = (services = [], failure) => {
  const messages = [failure?.message, ...(failure?.messages || [])]
    .map((message) => String(message || ''))
    .filter(Boolean)
  if (messages.length === 0) return []
  return services.filter((svc) => {
    const serviceId = String(svc.rateCd || '')
    const serviceName = getServiceName(svc)
    return messages.some((message) =>
      messageAlreadyIdentifiesService(message, serviceId, serviceName),
    )
  })
}

const messageAlreadyIdentifiesService = (message = '', serviceId = '', serviceName = '') => {
  const text = String(message || '')
  return (
    (!!serviceId && (text.includes(`[${serviceId}]`) || text.includes(serviceId))) ||
    (!!serviceName && text.includes(serviceName))
  )
}

const buildFailureDisplayMessages = (failure, services = []) => {
  const serviceMap = new Map(services.map((svc) => [svc.rateCd, getServiceName(svc)]))
  const messages = failure?.messages || []
  const fallbackMessage = isSelfCareUnavailableMessage(failure?.message)
    ? ''
    : failure?.message

  return failure.serviceIds.map((serviceId, index) => {
    const serviceName = serviceMap.get(serviceId) || '부가서비스'
    const message = messages[index] || fallbackMessage || '처리할 수 없습니다.'
    if (serviceName && serviceName !== '부가서비스' && message.includes(`[${serviceId}]`)) {
      return message.replace(`[${serviceId}]`, `${serviceName}(${serviceId})`)
    }
    if (serviceName && serviceName !== '부가서비스' && !message.includes(serviceName)) {
      return `${serviceName}(${serviceId}): ${message}`
    }
    return message
  })
}

const getPreCheckFailureCounts = (failure, services = []) => {
  const totalCount = services.length
  const failedIds = uniqueValues([
    ...(failure?.preCheckFailedServiceIds || []),
    ...(failure?.onlineCancelUnavailableServiceIds || []),
    ...(failure?.serviceIds || []),
  ])
  const checkedIds = uniqueValues([
    ...(failure?.checkedServiceIds || []),
    ...(failure?.passedServiceIds || []),
    ...failedIds,
  ])
  const checkedCount = Math.min(totalCount, checkedIds.length || (failure ? totalCount : 0))
  const failedCount = Math.min(
    checkedCount,
    failedIds.length || (failure ? checkedCount : 0),
  )
  const successCount = Math.max(0, checkedCount - failedCount)
  const uncheckedCount = Math.max(0, totalCount - checkedCount)
  return { totalCount, checkedCount, successCount, failedCount, uncheckedCount, checkedIds }
}

const getPreCheckFailureAlert = (failure, services = []) => {
  const displayMessages = uniqueValues([
    ...buildFailureDisplayMessages(failure, services),
  ])
  const counts = getPreCheckFailureCounts(failure, services)
  const hasPreCheckFailed = failure.preCheckFailedServiceIds.length > 0
  const hasOnlineCancelUnavailable = failure.onlineCancelUnavailableServiceIds.length > 0
  let message = counts.failedCount > 0
    ? '부가서비스 체크에 실패했습니다.'
    : '부가서비스 체크가 완료되었습니다.'
  if (counts.failedCount > 0 && hasPreCheckFailed && hasOnlineCancelUnavailable) {
    message = '부가서비스 처리 가능 여부를 확인해 주세요.'
  } else if (counts.failedCount > 0 && hasOnlineCancelUnavailable) {
    message = '판매점 온라인해지가 불가한 부가서비스가 있습니다.'
  }

  const uncheckedMessage = counts.uncheckedCount > 0 ? `, 미처리 ${counts.uncheckedCount}건` : ''
  const countMessage = `총 ${counts.totalCount}건 중 체크 ${counts.checkedCount}건(성공 ${counts.successCount}건, 실패 ${counts.failedCount}건)${uncheckedMessage}`
  if (displayMessages.length <= 1) {
    const detailMessage = displayMessages[0] || failure?.message || '부가서비스 가입이 불가합니다.'
    return {
      message,
      subMessage: `${escapeHtml(countMessage)}<br>${escapeHtml(detailMessage)}`,
    }
  }

  return {
    message,
    subMessage: `${escapeHtml(countMessage)}<br>${displayMessages.map((message) => `- ${escapeHtml(message)}`).join('<br>')}`,
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
  selfCareUnavailableServices = [],
  onlineCancelUnavailableServices = [],
  preCheckFailedServices = [],
  preCheckUncheckedServices = [],
} = {}) => {
  // scopeServices는 "이번 체크 요청에 포함된 서비스"다.
  // 같은 서비스의 이전 통과/실패/미처리 상태를 먼저 지우고, 이번 응답 기준으로 다시 세팅한다.
  const scopeIds = scopeServices.map((svc) => svc.rateCd).filter(Boolean)
  if (scopeIds.length > 0) {
    removePreCheckResultStateByIds(scopeIds)
  }

  preCheckPassedServiceIds.value = Array.from(
    new Set([
      ...preCheckPassedServiceIds.value,
      ...preCheckPassedServices.flatMap((svc) => {
        const serviceId = svc.rateCd
        const treatmentCode = svc.prdcSbscTrtmCd
        if (!serviceId) return []
        return treatmentCode ? [serviceId, `${serviceId}:${treatmentCode}`] : [serviceId]
      }),
    ]),
  )
  onlineCancelUnavailableServiceIds.value = Array.from(
    new Set([
      ...onlineCancelUnavailableServiceIds.value,
      ...onlineCancelUnavailableServices.map((svc) => svc.rateCd).filter(Boolean),
    ]),
  )
  selfCareUnavailableServiceIds.value = Array.from(
    new Set([
      ...selfCareUnavailableServiceIds.value,
      ...selfCareUnavailableServices.map((svc) => svc.rateCd).filter(Boolean),
    ]),
  )
  preCheckFailedServiceIds.value = Array.from(
    new Set([
      ...preCheckFailedServiceIds.value,
      ...preCheckFailedServices.map((svc) => svc.rateCd).filter(Boolean),
    ]),
  )
  preCheckUncheckedServiceIds.value = Array.from(
    new Set([
      ...preCheckUncheckedServiceIds.value,
      ...preCheckUncheckedServices.map((svc) => svc.rateCd).filter(Boolean),
    ]),
  )
}

const clearPreCheckResultState = () => {
  preCheckPassedServiceIds.value = []
  onlineCancelUnavailableServiceIds.value = []
  selfCareUnavailableServiceIds.value = []
  preCheckFailedServiceIds.value = []
  preCheckUncheckedServiceIds.value = []
}

const removePreCheckResultStateByIds = (serviceIds = []) => {
  if (serviceIds.length === 0) return
  const changedServiceIdSet = new Set(serviceIds)

  // preCheckPassedServiceIds에는 "상품코드"와 "상품코드:처리구분(A/C)"가 같이 저장될 수 있다.
  // 같은 상품을 다시 체크할 때 두 형식 모두 제거해야 이전 결과가 남지 않는다.
  preCheckPassedServiceIds.value = preCheckPassedServiceIds.value.filter(
    (id) => !changedServiceIdSet.has(id.split(':')[0]),
  )
  onlineCancelUnavailableServiceIds.value = onlineCancelUnavailableServiceIds.value.filter(
    (id) => !changedServiceIdSet.has(id),
  )
  selfCareUnavailableServiceIds.value = selfCareUnavailableServiceIds.value.filter(
    (id) => !changedServiceIdSet.has(id),
  )
  preCheckFailedServiceIds.value = preCheckFailedServiceIds.value.filter(
    (id) => !changedServiceIdSet.has(id),
  )
  preCheckUncheckedServiceIds.value = preCheckUncheckedServiceIds.value.filter(
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
  // 응답에 실패 상품코드가 명시되지 않는 케이스가 있어 메시지의 상품코드/상품명으로 한 번 더 보정한다.
  const selfCareUnavailableIds = uniqueValues(failure.selfCareUnavailableServiceIds || [])
  const inferredFailedServiceIds = getServicesIdentifiedByFailureMessage(preCheckServices, failure)
    .map((svc) => svc.rateCd)
    .filter(Boolean)
  const effectiveFailureServiceIds = failure.serviceIds.length > 0
    ? failure.serviceIds
    : (inferredFailedServiceIds.length > 0 ? inferredFailedServiceIds : preCheckServices.map((svc) => svc.rateCd).filter(Boolean))
  const effectiveFailure = {
    ...failure,
    serviceIds: effectiveFailureServiceIds,
    serviceIdSet: new Set(effectiveFailureServiceIds),
    selfCareUnavailableServiceIds: selfCareUnavailableIds,
    preCheckFailedServiceIds: failure.preCheckFailedServiceIds.length > 0
      ? failure.preCheckFailedServiceIds
      : (failure.onlineCancelUnavailableServiceIds.length > 0 ? [] : effectiveFailureServiceIds),
  }
  const failedServiceIdSet = new Set([
    ...effectiveFailure.serviceIds,
    ...effectiveFailure.preCheckFailedServiceIds,
    ...effectiveFailure.onlineCancelUnavailableServiceIds,
  ])
  // 단건 병합 사전체크는 요청 상품을 끝까지 처리한다.
  // 체크/통과 목록이 없는 구형 응답도 요청 전체를 체크 완료로 보고 실패 외 상품을 통과 처리한다.
  if (effectiveFailure.checkedServiceIds.length === 0) {
    effectiveFailure.checkedServiceIds = preCheckServices.map((svc) => svc.rateCd).filter(Boolean)
  }
  if (effectiveFailure.passedServiceIds.length === 0) {
    effectiveFailure.passedServiceIds = effectiveFailure.checkedServiceIds.filter(
      (serviceId) => !failedServiceIdSet.has(serviceId),
    )
  }
  const fallbackFailedServices = getServicesByFailureIds(preCheckServices, effectiveFailure)
  const failedCancelServices = getServicesByFailureIds(cancelServices, effectiveFailure)
  const failedJoinServices = getServicesByFailureIds(selectedJoinServices.value, effectiveFailure)
  const onlineCancelUnavailableServices = getServicesByIds(
    cancelServices,
    effectiveFailure.onlineCancelUnavailableServiceIds,
  )
  const selfCareUnavailableServices = getServicesByIds(
    preCheckServices,
    effectiveFailure.selfCareUnavailableServiceIds,
  )
  let preCheckFailedServices = []
  if (effectiveFailure.preCheckFailedServiceIds.length > 0) {
    preCheckFailedServices = getServicesByIds(preCheckServices, effectiveFailure.preCheckFailedServiceIds)
  } else if (effectiveFailure.onlineCancelUnavailableServiceIds.length === 0) {
    preCheckFailedServices = fallbackFailedServices
  }
  const checkedServiceIdSet = new Set([
    ...effectiveFailure.checkedServiceIds,
    ...effectiveFailure.passedServiceIds,
    ...effectiveFailure.serviceIds,
    ...effectiveFailure.preCheckFailedServiceIds,
    ...effectiveFailure.onlineCancelUnavailableServiceIds,
    ...effectiveFailure.selfCareUnavailableServiceIds,
  ])
  const preCheckPassedServices = getServicesByIds(
    preCheckServices,
    effectiveFailure.passedServiceIds,
  )
  const preCheckUncheckedServices = preCheckServices.filter(
    (svc) => !checkedServiceIdSet.has(svc.rateCd),
  )

  console.warn(`${getLogPrefix('부가서비스체크')} 진행 중단`, {
    reason: 'precheck failed',
    failureMessage: effectiveFailure.message,
    resCode: effectiveFailure.resCode,
    failedServiceIds: effectiveFailure.serviceIds,
    failedMessages: effectiveFailure.messages,
    services: preCheckServices.map(summarizeService),
  })

  const failureAlert = getPreCheckFailureAlert(effectiveFailure, preCheckServices)

  isApplyingPreCheckResult = true

  // 실패한 해지 건은 원래 선택 상태로 되돌리고, 실패한 신규가입 건은 선택에서 제거한다.
  // 실패 상태 자체는 화면 표시용으로 남기되, 이후 확인완료 차단은 현재 처리 대상만 기준으로 판단한다.
  restoreCancelServices(failedCancelServices)
  removeSelectedServices(failedJoinServices)
  setPreCheckResultState({
    scopeServices: preCheckServices,
    preCheckPassedServices,
    selfCareUnavailableServices,
    onlineCancelUnavailableServices,
    preCheckFailedServices,
    preCheckUncheckedServices,
  })

  // 단건 병합 결과에서 실패한 신규가입 상품은 선택 해제되므로 현재 처리 대상에서 제외된다.
  // 나머지 통과 상품이 있고 현재 처리 대상에 실패/미처리가 없으면 동일 체크 결과로 확인 절차를 계속한다.
  const currentCancelServices = allActiveServices.value.filter(isCancelSelected)
  const blockingState = getPreCheckBlockingState(getCurrentProcessServices(currentCancelServices))
  const canContinue = (
    preCheckPassedServices.length > 0
  ) && !blockingState.hasBlockingResult

  queueMicrotask(() => {
    isApplyingPreCheckResult = false
  })

  return {
    canContinue,
    preCheckPassedServices,
    selfCareUnavailableServices,
    blockingState,
    failureAlert,
  }
}

// 이용중 서비스 조회 후 초기 선택 동기화
// 해지불가(당일 개통) 및 설정 미완료 서비스는 자동 제외
// autoDefault 기본값은 신규 추가 상품에만 적용한다.
// 기존 가입 상품까지 초기화하면 실제 설정 변경 없이 "변경" 상태로 판정된다.
const syncSelectedServices = () => {
  // 기존 가입 autoDefault 상품은 사용자 설정 변경 대상이 아니다.
  // 이전 조회/상태에서 남은 자동 설정값도 제거해 "변경" 배지가 붙지 않게 한다.
  allActiveServices.value
    .filter((svc) => isExistingService(svc) && getSettingModalType(svc) === 'autoDefault')
    .forEach((svc) => {
      svc.addSvcSettingCompleted = false
      svc.addSvcSettingData = {}
    })

  // 팝업에서 신규 추가한 autoDefault 상품만 자동 처리
  allActiveServices.value
    .filter(
      (svc) =>
        isAddedService(svc) &&
        getSettingModalType(svc) === 'autoDefault' &&
        !svc.addSvcSettingCompleted,
    )
    .forEach((svc) => handleAutoDefault(svc))

  const serviceIds = allActiveServices.value
    .filter((svc) => !isCancelDisabled(svc) && !isJoinDisabled(svc))
    .map((svc, index) => getServiceKey(svc, index))
  selectedServiceIds.value = serviceIds
  isServiceConfirmCompleted.value = false
  syncAdditionModel()

  const initiallyUnselectedServices = allActiveServices.value.filter((svc) => !serviceIds.includes(svc.rateCd))
  console.log(`${getLogPrefix('선택초기화')} 화면 데이터 반영 결과`, {
    totalCount: allActiveServices.value.length,
    selectedCount: serviceIds.length,
    selectedServiceIds: selectedServiceIds.value,
    selectedTotalAmount: selectedTotalAmount.value,
    initiallyUnselectedServices: initiallyUnselectedServices.map((svc) => ({
      부가서비스코드: getServiceKey(svc),
      코드명: getServiceName(svc),
      당일개통해지불가: isCancelDisabled(svc),
      추가설정미완료: isJoinDisabled(svc),
    })),
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
    .map(mergeCatalogServiceInfo)
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
  preCheckPassedServiceIds.value = preCheckPassedServiceIds.value.filter((id) =>
    allActiveServices.value.some((svc) => svc.rateCd === id.split(':')[0]),
  )
  onlineCancelUnavailableServiceIds.value = onlineCancelUnavailableServiceIds.value.filter((id) =>
    allActiveServices.value.some((svc) => svc.rateCd === id),
  )
  selfCareUnavailableServiceIds.value = selfCareUnavailableServiceIds.value.filter((id) =>
    allActiveServices.value.some((svc) => svc.rateCd === id),
  )
  preCheckFailedServiceIds.value = preCheckFailedServiceIds.value.filter((id) =>
    allActiveServices.value.some((svc) => svc.rateCd === id),
  )
  preCheckUncheckedServiceIds.value = preCheckUncheckedServiceIds.value.filter((id) =>
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
 * - settingChangedServices : 설정 변경 대상
 *   기존 가입 상품의 설정 변경이므로 Y24 가입 가능 체크 대상에서 제외한다.
 *   실제 선해지+재신청 처리는 확인완료 후 저장 payload(flag='Y')를 받은 백엔드에서 처리한다.
 * @returns {Promise<boolean>} true = 통과, false = 실패/오류
 */
const runAdditionPreCheck = async ({ joinServices = [], cancelServices = [], settingChangedServices = [] } = {}) => {
  pendingPreCheckPassedServices.value = []
  const settingChangedServiceIds = settingChangedServices.map((svc) => svc.rateCd).filter(Boolean)

  // 설정변경은 기존 가입 상품이므로 Y24 신규가입 가능 체크에 보내면 1021이 발생할 수 있다.
  // 이전 시도에서 남은 실패/미처리 상태만 제거하고, 실제 처리는 저장 단계의 flag='Y' 흐름에 맡긴다.
  removePreCheckResultStateByIds(settingChangedServiceIds)

  const requestedPreCheckServices = [
    ...joinServices.map((svc) => ({ ...svc, prdcSbscTrtmCd: 'A' })),
    ...cancelServices.map((svc) => ({ ...svc, prdcSbscTrtmCd: 'C' })),
    ...settingChangedServices.map((svc) => ({ ...svc, prdcSbscTrtmCd: 'C' })),
  ]
  const preCheckServices = requestedPreCheckServices.filter(
    (svc) =>
      !isPreCheckPassedForTreatment(svc, svc.prdcSbscTrtmCd) &&
      !isPreCheckPassed(svc) &&
      !isSelfCareUnavailable(svc),
  )
  const requestSeq = ++preCheckRequestSeq

  try {
    isPreChecking.value = true
    if (preCheckServices.length === 0) {
      console.log(`${getLogPrefix('부가서비스체크')} 사전체크 생략`, {
        reason: 'all requested services already passed',
        requestedServices: requestedPreCheckServices.map(summarizeService),
        settingChangedServices: settingChangedServices.map(summarizeService),
      })
      return true
    }
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
      pendingPreCheckPassedServices.value = []
      const failureResult = applyPreCheckFailure(failure, {
        preCheckServices,
        cancelServices: [...cancelServices, ...settingChangedServices],
      })
      if (!failureResult.canContinue) {
        showAlert(
          failureResult.failureAlert.message,
          undefined,
          failureResult.failureAlert.subMessage,
        )
        return false
      }

      console.log(`${getLogPrefix('부가서비스체크')} 부분 실패 제외 후 확인 절차 계속`, {
        passedServiceIds: failureResult.preCheckPassedServices.map((svc) => svc.rateCd),
      })

      // API 사전체크는 완료되었으므로 결과 팝업을 기다리는 동안 로딩 상태를 유지하지 않는다.
      if (requestSeq === preCheckRequestSeq) {
        isPreChecking.value = false
      }
      await new Promise((resolve) => {
        showAlert(
          failureResult.failureAlert.message,
          resolve,
          failureResult.failureAlert.subMessage,
        )
      })
      return 'PARTIAL_SUCCESS'
    }

    pendingPreCheckPassedServices.value = preCheckServices
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
// 1. 이미 확인완료 상태이면 잠금 해제
// 2. 설정 미완료/체크 진행 중이면 중단
// 3. 변경사항이 없으면 사용자 확인 후 완료
// 4. 변경사항이 있으면 사전체크 실행
// 5. 사전체크 통과 건을 상태에 반영한 뒤, 현재 처리 대상에 남은 실패/미처리가 없을 때만 완료
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
      const blockingState = getPreCheckBlockingState(getCurrentProcessServices(cancelServices))
      if (blockingState.hasBlockingResult) {
        logPreCheckBlockingState(blockingState)
        return
      }
      completeServiceConfirm({
        reason: 'user confirmed (no changes)',
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

  const completeAfterPreCheck = () => {
    // runAdditionPreCheck 성공 직후의 통과 건을 확정 상태로 옮긴다.
    // 이 처리가 먼저 되어야 직전 미처리 상태가 남아 확인완료를 막지 않는다.
    if (pendingPreCheckPassedServices.value.length > 0) {
      setPreCheckResultState({
        scopeServices: pendingPreCheckPassedServices.value,
        preCheckPassedServices: pendingPreCheckPassedServices.value,
      })
      pendingPreCheckPassedServices.value = []
    }

    // 부분 실패 처리에서 실패 상품의 선택 상태가 변경될 수 있으므로
    // 체크 전 배열이 아닌 현재 화면 상태로 처리 대상을 다시 계산한다.
    const currentCancelServices = allActiveServices.value.filter(isCancelSelected)
    const blockingState = getPreCheckBlockingState(getCurrentProcessServices(currentCancelServices))
    if (blockingState.hasBlockingResult) {
      logPreCheckBlockingState(blockingState)
      return
    }

    completeServiceConfirm({
      joinCount: selectedJoinServices.value.length,
      cancelCount: currentCancelServices.length,
      settingChangedCount: selectedSettingChangedServices.value.length,
    })
  }

  // 부분 실패는 결과 안내 팝업에서 이미 사용자가 확인했으므로 확인 팝업을 연속 표시하지 않는다.
  if (passed === 'PARTIAL_SUCCESS') {
    completeAfterPreCheck()
    return
  }

  showConfirm('다음 항목을 처리합니다. 확인하시겠습니까?', completeAfterPreCheck, subMsg)
}

// ─── API 호출 ─────────────────────────────────────────────────────────────────

const fetchFormApi = async (path, body) => {
  const baseUrl = `${import.meta.env.VITE_MSF_BASE_URL || ''}`.replace(/\/$/, '')
  const userStore = useMsfUserStore()
  const headers = {
    'Content-Type': 'application/json',
    Accept: 'application/json',
  }
  if (userStore.token) {
    headers.Authorization = `Bearer ${userStore.token}`
  }

  const response = await fetch(`${baseUrl}${path}`, {
    method: 'POST',
    headers,
    credentials: 'include',
    body: JSON.stringify(body),
  })
  return response.json().catch(() => null)
}

const getUniqueServices = (services = []) =>
  [
    ...new Map(
      services
        .map((service) => [getServiceKey(service, null), service])
        .filter(([serviceCode]) => Boolean(serviceCode)),
    ).values(),
  ]

const fetchAdditionCategoryIds = async () => {
  const res = await fetchFormApi('/api/form/addition/category/list', {
    rateAdsvcDivCd: 'R',
  })
  return [...new Set((res?.data || []).map((category) => category.ctgCd).filter(Boolean))]
}

const fetchAdditionCatalog = async () => {
  let categoryIds = []
  try {
    categoryIds = await fetchAdditionCategoryIds()
  } catch (error) {
    console.warn(`${getLogPrefix('부가서비스카테고리조회')} 기본 카테고리 사용`, {
      message: error?.message,
    })
  }

  if (categoryIds.length === 0) {
    categoryIds = ['RFREESVC', 'RRATESVC']
  }

  const res = await fetchFormApi('/api/form/addition/list', {
    operTypeCd: '',
    prodCtgTypeCd: 'R',
    categoryMstRequest: { prodCtgId: categoryIds },
  })
  if (res && res.code === '0000' && res.data?.[0]) {
    const result = res.data[0]
    return {
      freeAddition: getUniqueServices(result.freeAddition),
      paidAddition: getUniqueServices(result.paidAddition),
      dailyAddition: getUniqueServices(result.dailyAddition),
    }
  }
  return createEmptyAdditionCatalog()
}

// 전체 부가서비스 카탈로그 조회 (POST /api/form/addition/list) — 팝업에 전달할 선택 가능 목록
const fetchAllVasList = async () => {
  try {
    const result = await fetchAdditionCatalog()
    const dailyAdditionMap = createDailyAdditionMap(result.dailyAddition || [])
    allFreeVasServices.value = (result.freeAddition || []).map((svc) =>
      mergeDailyAdditionInfo(svc, dailyAdditionMap),
    )
    allPaidVasServices.value = appendDailyOnlyAdditions(
      (result.paidAddition || []).map((svc) => mergeDailyAdditionInfo(svc, dailyAdditionMap)),
      result.dailyAddition || [],
    )
  } catch (error) {
    console.error(`${getLogPrefix('전체부가서비스조회')} 예외 발생`, { message: error?.message })
    allFreeVasServices.value = []
    allPaidVasServices.value = []
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
const completeInitialLoading = async () => {
  emit('ready')
  if (model.value.additionInitialLoading !== true) return
  await nextTick()
  model.value.additionInitialLoading = false
}

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
    await completeInitialLoading()
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
      const additionCatalog = await fetchAdditionCatalog().catch((error) => {
        console.warn(`${getLogPrefix('일요금제조회')} 진행 중단`, { message: error?.message })
        return createEmptyAdditionCatalog()
      })
      const dailyAdditionMap = createDailyAdditionMap([
        ...(additionCatalog.dailyAddition || []),
        ...(result.dailyAddition || []),
      ])
      queriedFreeServices.value = (normalized?.freeAddition || [])
        .map((svc) => mergeDailyAdditionInfo(svc, dailyAdditionMap))
        .map(toServiceRow)
        .map((svc) => mergePersistedSettingData(svc, persistedSettingMap))
      queriedPaidServices.value = (normalized?.paidAddition || [])
        .map((svc) => mergeDailyAdditionInfo(svc, dailyAdditionMap))
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
        dailyAdditionCount: dailyAdditionMap.size,
        freeServices: activeFreeServices.value.map(summarizeService),
        paidServices: activePaidServices.value.map(summarizeService),
        restoredAddedServices: addedServices.value.map(summarizeService),
      })
      console.log(`${getLogPrefix('초기전체내역')} 부가서비스 전체 내역`)
      console.table(mapAllServiceLog())
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
  } finally {
    await completeInitialLoading()
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
  () => [...selectedServiceIds.value],
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

    const changedServiceIds = getChangedServiceIds(newValue, oldValue)
    const selectedServiceLog = mapSelectedServiceLog()
    // const settingServiceLog = mapSettingServiceLog(newValue || [])
    console.log(`${getLogPrefix('선택변경')} 화면 데이터 반영 결과`, {
      changedServiceIds: changedServiceIds.join(', '),
      selectedCount: (newValue || []).length,
      selectedTotalAmount: selectedTotalAmount.value,
    })
    if (selectedServiceLog.length > 0) {
      console.table(selectedServiceLog)
    }
    //if (settingServiceLog.length > 0) {
    //  console.log(`${getLogPrefix('부가정보설정선택')} 설정 대상 부가서비스`)
    //  console.table(settingServiceLog)
    //}
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

// 외부(서비스선택 초기화)에서 additionConfirmCompleted=false로 리셋될 때 로컬 상태 동기화
watch(
  () => model.value.additionConfirmCompleted,
  (val) => {
    if (!val && isServiceConfirmCompleted.value) {
      clearPreCheckResultState()
      isServiceConfirmCompleted.value = false
    }
  },
)


onMounted(() => {
  console.log(`${getLogPrefix('초기화')} mounted`)
  fetchActiveServices()
})

// ─── 부가서비스코드 컬럼 리사이즈 ─────────────────────────────────────────────
const codeColWidth = ref(0)

const startCodeColResize = (e) => {
  e.preventDefault()
  const startX = e.clientX
  const startWidth = codeColWidth.value
  const onMove = (ev) => { codeColWidth.value = Math.max(0, startWidth + ev.clientX - startX) }
  const onUp = () => { document.removeEventListener('mousemove', onMove); document.removeEventListener('mouseup', onUp) }
  document.addEventListener('mousemove', onMove)
  document.addEventListener('mouseup', onUp)
}
</script>

<template>
  <MsfLoadingComp v-if="isPreChecking" />
  <!-- 부가서비스 신청/변경 -->
  <MsfTitleArea title="부가서비스 신청/변경" />
  <MsfTable>
    <template #colgroup>
      <col style="width: 68px" />
      <col :style="{ width: codeColWidth + 'px' }" />
      <col class="service-name-col" />
      <col style="width: 160px" />
      <col style="width: 112px" />
    </template>
    <template #thead>
      <tr>
        <th>선택</th>
        <th class="code-col-th" :style="{ width: codeColWidth + 'px' }">
          <span v-if="codeColWidth >= 30">부가서비스코드</span>
        </th>
        <th class="service-name-th">
          <span class="code-col-resize-handle" @mousedown.prevent="startCodeColResize" title="드래그하여 부가서비스코드 컬럼 표시"></span>
          부가서비스명
        </th>
        <th>요금</th>
        <th>설정</th>
      </tr>
    </template>
    <template #tbody>
      <template v-if="allActiveServices.length > 0">
        <tr v-for="svc in allActiveServices" :key="svc.rateCd">
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
          <td class="service-code-cell ut-text-center" :style="{ maxWidth: codeColWidth + 'px', padding: codeColWidth < 10 ? '0' : undefined }">{{ svc.rateCd }}</td>
          <td class="service-name-cell">
            <div class="service-name-stack">
              <label :for="`inp-addition-${svc.rateCd}`">{{ svc.rateNm }}</label>
              <MsfFlag
                v-if="getServiceFlags(svc).length"
                :data="getServiceFlags(svc)"
                size="small"
                type="inline"
                class="service-name-flags"
              />
            </div>
          </td>
          <td class="ut-text-center amount-cell">{{ getServiceAmountLabel(svc) }}</td>
          <td class="ut-text-center setting-cell">
            <MsfButton
              variant="subtle"
              v-if="isSettingButtonVisible(svc)"
              :disabled="isListDisabled || isSettingButtonDisabled(svc)"
              @click="openSettingModal(svc)"
            >
              설정
            </MsfButton>
          </td>
        </tr>
      </template>
      <tr v-else>
        <td colspan="5">
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
    :free-services="modalExcludedFreeServices"
    :paid-services="modalExcludedPaidServices"
    :active-free-ids="activeFreeIds"
    :active-paid-ids="activePaidIds"
    :phone-number="`${model.deviceChgTel1 || ''}${model.deviceChgTel2 || ''}${model.deviceChgTel3 || ''}`"
    :ncn="model.ncn || model.contractNum || ''"
    @confirm="onVasConfirm"
  />
  <!-- NOSPAM4: 불법TM 수신차단 -->
  <IllegalTmBlockModal
    v-if="settingModalType === 'illegalTm'"
    :model-value="showSettingModal"
    :max-count="50"
    :min-length="3"
    :setting-data="currentSettingData"
    :initial-setting-data="initialSettingData"
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
    :initial-setting-data="initialSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- NOSPAM3: 정보제공사업자번호차단 -->
  <MsfInfoProviderBlockModal
    v-if="settingModalType === 'infoProviderBlock'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    :initial-setting-data="initialSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- STLPVTPHN: 번호도용 차단 -->
  <NumberSpoofingBlockModal
    v-if="settingModalType === 'numberSpoofing'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    :initial-setting-data="initialSettingData"
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
    :initial-setting-data="initialSettingData"
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
    :initial-setting-data="initialSettingData"
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
    :initial-setting-data="initialSettingData"
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
    :initial-setting-data="initialSettingData"
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
    :initial-setting-data="initialSettingData"
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
    :initial-setting-data="initialSettingData"
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
    :initial-setting-data="initialSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- FCARVLSMS: 로밍 해외도착알리미 -->
  <MsfAlertNumbersModal
    v-if="settingModalType === 'alertNumbers'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    :initial-setting-data="initialSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- SENOINFR1: 망내 1회선 무료통화 -->
  <MsfFreeCallNumberModal
    v-if="settingModalType === 'freeCallNumber'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    :initial-setting-data="initialSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <!-- PL253A854: My time plan_MVNO 전용 -->
  <MsfMilitaryTimePlanModal
    v-if="settingModalType === 'militaryTimePlan'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    :initial-setting-data="initialSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
</template>

<style lang="scss" scoped>
.service-name-col {
  min-width: 0;
}

.service-name-cell,
.service-name-stack {
  min-width: 0;
}

.service-name-stack {
  display: inline-flex;
  align-items: center;
  max-width: 100%;
  gap: 4px;
  vertical-align: middle;
}

.service-name-cell label {
  display: block;
  flex: 1 1 auto;
  max-width: 100%;
  min-width: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.service-name-flags {
  flex: 0 0 auto;
}

.amount-cell,
.setting-cell {
  white-space: nowrap;
}

.code-col-th {
  overflow: hidden;
  white-space: nowrap;
  padding: 0 !important; // MsfTable :deep(th) padding 8px 16px 오버라이드
}

.service-name-th {
  position: sticky !important; // MsfTable :deep(th) sticky 유지
  z-index: 2;

  .code-col-resize-handle {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    width: 6px;
    cursor: col-resize;
    z-index: 1;

    &:hover {
      background: var(--color-gray-150); // 디자인 토큰 사용
    }
  }
}

.service-code-cell {
  overflow: hidden;
  white-space: nowrap;
  // text-align은 ut-text-center 공통 유틸 클래스로 처리
}
</style>
