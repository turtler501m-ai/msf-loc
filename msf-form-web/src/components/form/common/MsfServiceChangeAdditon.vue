<script setup>
import { computed, defineModel, onMounted, ref, watch } from 'vue'
import { useMsfUserStore } from '@/stores/msf_user'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import MsfVasManageModal from './popups/MsfVasManageModal.vue'
import IllegalTmBlockModal from './popups/MsfIllegalTmBlockModal.vue'
import NumberSpoofingBlockModal from './popups/MsfNumberSpoofingBlockModal.vue'
import RoamingStartDateModal from './popups/MsfRoamingStartDateModal.vue'

// ─── 로그 접두사 ──────────────────────────────────────────────────────────────
const getLogPrefix = (task) => `[서비스변경][부가서비스신청변경][${task}]`

// ─── 상태 (State) ─────────────────────────────────────────────────────────────
const model = defineModel({ type: Object, required: true })

// 부가서비스 추가/삭제 팝업 표시 여부
const isVasModalOpen = ref(false)
// 설정 팝업 종류: illegalTm | numberSpoofing | roaming
const settingModalType = ref('')
// 현재 설정 중인 서비스 rateCd
const settingServiceId = ref('')

// X97 조회 결과 무료/유료 부가서비스 원본 (팝업 전달용)
const queriedFreeServices = ref([])
const queriedPaidServices = ref([])
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

// 서비스 코드/명칭으로 설정 팝업 종류 결정
// MspAdditionDto에 settingYn 미포함 시 toServiceRow에서 이 함수로 자동 감지
const getSettingModalType = (svc = {}) => {
  const code = getServiceKey(svc).toUpperCase()
  const name = getServiceName(svc)

  if (code === 'NOSPAM4' || name.includes('불법TM')) return 'illegalTm'
  if (code === 'STLPVTPHN' || name.includes('번호도용')) return 'numberSpoofing'
  if (code === 'PL2078760' || name.includes('로밍')) return 'roaming'
  return ''
}

// 설정이 필요한 서비스인지 여부 (settingYn=Y AND 알려진 모달 타입 존재)
const needsAdditionalInfo = (svc = {}) => svc.settingYn === 'Y' && !!getSettingModalType(svc)

// 추가 서비스 중 설정 미완료 → 체크박스·확인 버튼 비활성화
const isJoinDisabled = (svc = {}) =>
  isAddedService(svc) && needsAdditionalInfo(svc) && svc.addSvcSettingCompleted !== true

// 설정 버튼 비활성화 조건:
// - 알려진 모달 타입 없음 → 항상 비활성
// - 기존 이용중 서비스 → illegalTm(불법TM)만 설정 가능, 나머지 비활성
const isSettingButtonDisabled = (svc = {}) => {
  const modalType = getSettingModalType(svc)
  if (!modalType) return true
  if (isExistingService(svc)) {
    return modalType !== 'illegalTm'
  }
  return false
}

const openSettingModal = (svc = {}) => {
  console.log(`${getLogPrefix('부가서비스설정')} 요청 시작`, {
    service: summarizeService(toServiceRow(svc)),
    modalType: getSettingModalType(svc),
    isExistingService: isExistingService(svc),
  })

  if (isSettingButtonDisabled(svc)) {
    console.warn(`${getLogPrefix('부가서비스설정')} 진행 중단`, {
      reason: 'setting disabled',
      service: summarizeService(toServiceRow(svc)),
      modalType: getSettingModalType(svc),
    })
    return
  }

  settingModalType.value = getSettingModalType(svc)
  settingServiceId.value = getServiceKey(svc)

  console.log(`${getLogPrefix('부가서비스설정')} 화면 데이터 반영 결과`, {
    settingModalType: settingModalType.value,
    settingServiceId: settingServiceId.value,
  })
}

const closeSettingModal = () => {
  console.log(`${getLogPrefix('부가서비스설정')} 닫기`, {
    settingModalType: settingModalType.value,
    settingServiceId: settingServiceId.value,
  })
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

// 설정 팝업 확인 시 설정값 저장 및 서비스 선택 처리
const applySettingData = (settingData = {}) => {
  console.log(`${getLogPrefix('부가서비스설정저장')} 요청 시작`, {
    settingServiceId: settingServiceId.value,
    settingModalType: settingModalType.value,
    settingData,
  })

  const target = allActiveServices.value.find((svc) => svc.rateCd === settingServiceId.value)

  if (target) {
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
    selectedServiceIds: selectedServiceIds.value,
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
  svc.addSvcSettingCompleted === true && !!svc.addSvcSettingData

// 기존 서비스는 "변경", 신규 추가 서비스는 "설정완료" 뱃지
const getSettingChangeLabel = (svc = {}) => {
  if (!isSettingChanged(svc)) return ''
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
  const toAdditionRecord = (svc = {}, action = '') => ({
    ...summarizeService(svc),
    prodHstSeq: getProductSeqNo(svc),
    ftrNewParam: getFtrNewParam(svc),
    addSvcSettingCompleted: svc.addSvcSettingCompleted === true,
    addSvcSettingData: svc.addSvcSettingData || {},
    action,
  })

  const joinServices = selectedJoinServices.value.map((svc) => toAdditionRecord(svc, 'ADD'))
  const cancelServices = allActiveServices.value
    .filter(isCancelSelected)
    .map((svc) => toAdditionRecord(svc, 'CANCEL'))

  model.value.additionList = [...joinServices, ...cancelServices]
  model.value.additionCancelList = cancelServices
  model.value.additionConfirmCompleted = isServiceConfirmCompleted.value
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
const getFtrNewParam = (svc = {}) => {
  const settingData = svc.addSvcSettingData || {}
  if (svc.ftrNewParam) return String(svc.ftrNewParam)
  if (settingData.ftrNewParam) return String(settingData.ftrNewParam)
  if (Object.keys(settingData).length > 0) return JSON.stringify(settingData)
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
const syncSelectedServices = () => {
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

  newServices.forEach((svc) => addedServices.value.push(svc))
  onlineCancelUnavailableServiceIds.value = onlineCancelUnavailableServiceIds.value.filter((id) =>
    allActiveServices.value.some((svc) => svc.rateCd === id),
  )
  preCheckFailedServiceIds.value = preCheckFailedServiceIds.value.filter((id) =>
    allActiveServices.value.some((svc) => svc.rateCd === id),
  )

  // 설정 미완료 서비스는 선택 목록에 추가하지 않음 (확인 버튼 비활성화 유지)
  const addedIds = newServices.filter((svc) => !isJoinDisabled(svc)).map((svc) => svc.rateCd)
  selectedServiceIds.value = Array.from(new Set([...selectedServiceIds.value, ...addedIds]))
  isServiceConfirmCompleted.value = false
  syncAdditionModel()

  console.log(`${getLogPrefix('부가서비스추가')} 화면 데이터 반영 결과`, {
    addedCount: newServices.length,
    pendingSettingCount: newServices.filter((svc) => isJoinDisabled(svc)).length,
    addedServices: newServices.map(summarizeService),
    selectedServiceIds: selectedServiceIds.value,
    selectedTotalAmount: selectedTotalAmount.value,
  })
}

// ─── 확인 버튼 핸들러 ─────────────────────────────────────────────────────────

// [확인] 버튼 처리 흐름:
// 1. 이미 확인완료 상태 → 토글 off (잠금 해제)
// 2. 가입/해지 변경사항 있음 → 사전체크
// 3. 변경사항 없음 → 확인 다이얼로그 → 사용자 확인 후 완료
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

  // 변경사항이 없을 때만 확인 다이얼로그를 표시한다.
  if (selectedJoinServices.value.length === 0 && cancelServices.length === 0) {
    const confirmMsg = '변경 사항이 없습니다. 계속 진행하시겠습니까?'

    console.log(`${getLogPrefix('부가서비스체크')} 확인 다이얼로그 표시`, {
      confirmMsg,
      cancelCount: cancelServices.length,
    })

    showConfirm(confirmMsg, () => {
      isServiceConfirmCompleted.value = true
      syncAdditionModel()
      console.log(`${getLogPrefix('부가서비스체크')} 화면 데이터 반영 결과`, {
        reason: 'user confirmed (no changes)',
        cancelCount: cancelServices.length,
        isServiceConfirmCompleted: isServiceConfirmCompleted.value,
        selectedServiceIds: selectedServiceIds.value,
      })
    })
    return
  }

  // 가입/해지 변경사항은 사전체크 성공 시 알림 없이 확인완료 상태만 반영한다.
  const preCheckServices = [
    ...selectedJoinServices.value.map((svc) => ({ ...svc, prdcSbscTrtmCd: 'A' })),
    ...cancelServices.map((svc) => ({ ...svc, prdcSbscTrtmCd: 'C' })),
  ]
  const requestSeq = ++preCheckRequestSeq

  try {
    isPreChecking.value = true
    const payload = createMoscPrdcTrtmPreChkPayload(preCheckServices)
    console.log(`${getLogPrefix('부가서비스체크')} 부가서비스 사전체크 요청`, payload)
    const res = await postAdditionPreCheck(payload)
    console.log(`${getLogPrefix('부가서비스체크')} 부가서비스 사전체크 응답`, {
      code: res?.code,
      message: res?.message,
      data: res?.data,
    })
    if (requestSeq !== preCheckRequestSeq) {
      console.warn(`${getLogPrefix('부가서비스체크')} 이전 응답 무시`, { requestSeq, preCheckRequestSeq })
      return
    }
    const failure = createPreCheckFailure(res)

    if (failure) {
      applyPreCheckFailure(failure, { preCheckServices, cancelServices })
      return
    }

    setPreCheckResultState({
      scopeServices: preCheckServices,
      preCheckPassedServices: preCheckServices,
    })
    isServiceConfirmCompleted.value = true
    syncAdditionModel()
    console.log(`${getLogPrefix('부가서비스체크')} 화면 데이터 반영 결과`, {
      isServiceConfirmCompleted: isServiceConfirmCompleted.value,
      joinCount: selectedJoinServices.value.length,
      cancelCount: cancelServices.length,
      selectedServiceIds: selectedServiceIds.value,
      selectedTotalAmount: selectedTotalAmount.value,
    })
  } catch (error) {
    if (requestSeq !== preCheckRequestSeq) {
      console.warn(`${getLogPrefix('부가서비스체크')} 이전 예외 무시`, { requestSeq, preCheckRequestSeq })
      return
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
  } finally {
    if (requestSeq === preCheckRequestSeq) {
      isPreChecking.value = false
    }
  }
}

// ─── API 호출 ─────────────────────────────────────────────────────────────────

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

    if (res && res.code === '0000' && formResponse?.resCode === '0000' && result) {
      const normalized = result?.list ? splitActiveServices(result.list) : result
      queriedFreeServices.value = (normalized?.freeAddition || []).map(toServiceRow)
      queriedPaidServices.value = (normalized?.paidAddition || []).map(toServiceRow)
      activeFreeServices.value = [...queriedFreeServices.value]
      activePaidServices.value = [...queriedPaidServices.value]
      addedServices.value = []
      clearPreCheckResultState()
      syncSelectedServices()
      console.log(`${getLogPrefix('이용중부가서비스조회')} 화면 데이터 반영 결과`, {
        freeCount: activeFreeServices.value.length,
        paidCount: activePaidServices.value.length,
        selectedServiceIds: selectedServiceIds.value,
        selectedTotalAmount: selectedTotalAmount.value,
        freeServices: activeFreeServices.value.map(summarizeService),
        paidServices: activePaidServices.value.map(summarizeService),
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
    console.log(`${getLogPrefix('가입자정보변경')} 감지`, { oldValue, newValue })
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
      oldValue,
      newValue,
      selectedTotalAmount: selectedTotalAmount.value,
    })
    syncAdditionModel()
  },
)

// 확인완료 상태 변경 시 model에 동기화 → 부모(ServiceChangeProduct)가 다음 버튼 활성화에 사용
watch(isServiceConfirmCompleted, (val) => {
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
              v-if="svc.settingYn === 'Y'"
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
      <MsfButton variant="subtle" @click="isVasModalOpen = true">부가서비스 추가</MsfButton>
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
    :free-services="queriedFreeServices"
    :paid-services="queriedPaidServices"
    :active-free-ids="activeFreeIds"
    :active-paid-ids="activePaidIds"
    @confirm="onVasConfirm"
  />
  <IllegalTmBlockModal
    :model-value="settingModalType === 'illegalTm'"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <NumberSpoofingBlockModal
    :model-value="settingModalType === 'numberSpoofing'"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <RoamingStartDateModal
    :model-value="settingModalType === 'roaming'"
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
