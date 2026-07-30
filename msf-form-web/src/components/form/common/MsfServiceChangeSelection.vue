<script setup>
import { showAlert, showConfirmAsync } from '@/libs/utils/comp.utils'
import { getCommonCodeListWithDetail } from '@/libs/utils/comn.utils'
import { computed, defineModel, nextTick, onMounted, ref, watch } from 'vue'

const model = defineModel({ type: Object, required: true })

const lastValidServiceSelect = ref([])
const serviceChipKey = ref(0)
const SERVICE_TARGET_GROUP_CODE = 'SVC_TGT_CD'
const PLAN_CHANGE_CODE = 'P11'
const NUMBER_CHANGE_CODE = 'O11'
const UNPAUSE_CODE = 'O12'
const USIM_CHANGE_CODE = 'O13'
//const DATA_SHARING_CODE = 'R15'
const EXCLUSIVE_SERVICE_CODES = [PLAN_CHANGE_CODE, NUMBER_CHANGE_CODE, UNPAUSE_CODE]
const USIM_CHANGE_RESTRICTED_CUSTOMER_TYPES = ['NM', 'FM']
const SUSPENDED_ONLY_MESSAGE =
  '일시정지 상태인 휴대폰번호는 분실복구/일시정지해제 신청만 가능합니다.'
const USIM_UNPAID_RESTRICTED_MESSAGE = '미납이 있는 고객은 USIM 변경을 신청할 수 없습니다.'
const USIM_MINOR_RESTRICTED_MESSAGE = '미성년자는 USIM 변경을 신청할 수 없습니다.'

const isServiceSelectCompleted = computed(() => model.value.serviceSelectCompleteYn === 'Y')
const isServiceSelectionLocked = computed(() => model.value.serviceSelectionLocked === true)
const isServiceCheckCompleted = computed(
  () => model.value.serviceCheckYn === 'Y' || isServiceSelectCompleted.value,
)

// [기존 기능] 서비스 체크 완료 후 다음 단계 진입 전까지 서비스 선택 칩 전체를 readonly 처리했다.
// const isChipReadonly = computed(
//   () => model.value.serviceCheckYn === 'Y' && !isServiceSelectCompleted.value,
// )
// [추가/변경 기능] 서비스별 추가/초기화를 위해 서비스 선택 칩은 항상 클릭 가능하게 둔다.
// const isChipReadonly = computed(
//   () => false,
// )
// [추가/변경 기능] 서비스상품 영역 다음 버튼 완료 후에는 서비스변경 선택 영역을 잠근다.
const isChipReadonly = computed(() => isServiceSelectionLocked.value)

// 각 서비스 코드의 확인완료 여부를 담은 필드명 매핑
const CONFIRM_FIELD_MAP = {
  P11: 'planChangeConfirmCompleted',
  O11: 'numberChgConfirmCompleted',
  O12: 'unpauseConfirmCompleted',
  R11: 'additionConfirmCompleted',
  R12: 'wirelessBlockConfirmCompleted',
  R14: 'insuranceConfirmCompleted',
  O13: 'reqUsimConfirmCompleted',
  R15: 'dataSharingConfirmCompleted',
  R16: 'combineSoloConfirmCompleted',
}

// step1 진입 후 확인완료된 서비스 코드 집합
const confirmedServiceCodes = computed(() => {
  const selected = Array.isArray(model.value.serviceSelect) ? model.value.serviceSelect : []
  return new Set(
    selected.filter((code) => {
      const field = CONFIRM_FIELD_MAP[code]
      return field ? model.value[field] === true : false
    }),
  )
})

// 확인완료/진행중 여부에 따라 className을 추가한 서비스 목록
const enhancedServiceList = computed(() => {
  const list = model.value.serviceList || []
  const selected = Array.isArray(model.value.serviceSelect) ? model.value.serviceSelect : []
  return list.map((item) => {
    if (confirmedServiceCodes.value.has(item.value)) return { ...item, className: 'chip-confirmed' }
    if (selected.includes(item.value)) return { ...item, className: 'chip-inprogress' }
    return { ...item, className: '' }
  })
})

const getServiceLabel = (code) => {
  const item = (model.value.serviceList || []).find((s) => s.value === code)
  return item?.label || code
}

const resetServiceData = (serviceCode) => {
  //console.log('[변경][서비스선택][서비스별초기화] 시작', {
  //  serviceCode,
  //  serviceName: getServiceLabel(serviceCode),
  //  beforeSelected: model.value.serviceSelect,
  //})

  switch (serviceCode) {
    case 'R11':
      model.value.additionList = []
      model.value.additionCancelList = []
      model.value.additionConfirmCompleted = false
      break
    case 'R12':
      model.value.blockService = null
      model.value.wirelessBlockConfirmCompleted = false
      break
    case 'R15':
      Object.assign(model.value, {
        shareUseState: '',
        sharePhoneNum: '',
        shareUsimNum: '',
        dataSharingSubscribed: false,
        dataSharingTargetNo: '',
        dataSharingAuthCompleted: false,
        dataSharingUsimCheckCompleted: false,
        dataSharingAvailableChecked: false,
        dataSharingAgreementCompleted: false,
        dataSharingConfirmCompleted: false,
        dataSharingMessage: '',
        dataSharingPlanName: '',
      })
      break
    case 'P11':
      Object.assign(model.value, {
        planName1: '',
        planName2: '',
        changeDate: '',
        planChangeConfirmCompleted: false,
      })
      break
    case 'O11':
      model.value.numberChgConfirmCompleted = false
      Object.assign(model.value, {
        reqWantFnNo: '',
        reqWantMnNo: '',
        reqWantRnNo: '',
        wishNo: '',
        wishNoc: '',
        wishMarket: '',
      })
      break
    case 'O12':
      model.value.unLockPw = ''
      model.value.unpauseConfirmCompleted = false
      break
    case 'R14':
      Object.assign(model.value, {
        clauseInsuranceYn: '',
        recCat1: '',
        recCat2: '',
        insuranceDeviceOs: '',
        insuranceAgree: false,
        insuranceConfirmCompleted: false,
      })
      break
    case 'O13':
      Object.assign(model.value, {
        hasSim: true,
        simTypeCd: '',
        usimKindsCd: '',
        reqUsimSn: '',
        eid: '',
        imei1: '',
        imei2: '',
        simPurchaseMethod: '',
        reqUsimConfirmCompleted: false,
      })
      break
    case 'R16':
      model.value.soloData = ''
      model.value.combineSoloConfirmCompleted = false
      break
  }
  // 서비스 선택 해제 → step1 해당 영역 사라짐 (v-if 조건 false)
  const newSelect = (
    Array.isArray(model.value.serviceSelect) ? model.value.serviceSelect : []
  ).filter((code) => code !== serviceCode)
  model.value.serviceSelect = newSelect
  setServiceListDisabled(newSelect)
  syncAllCheck(newSelect)
  applyServiceCheckState(newSelect)
  lastValidServiceSelect.value = [...newSelect]
  serviceChipKey.value++

  //console.log('[변경][서비스선택][서비스별초기화] 완료', {
  //  serviceCode,
  //  selected: model.value.serviceSelect,
  //  allCheck: model.value.allCheck,
  //  serviceCheckYn: model.value.serviceCheckYn,
  //  serviceChecked: model.value.serviceChecked,
  //})
}

const applyServiceCheckState = (selectedValues = model.value.serviceSelect) => {
  // [추가/변경 기능] 서비스 칩을 클릭할 때마다 기존 서비스 체크 버튼의 완료 상태를 즉시 반영한다.
  const selected = Array.isArray(selectedValues) ? selectedValues : []
  const hasSelectedService = selected.length > 0

  model.value.serviceCheckYn = hasSelectedService ? 'Y' : 'N'
  model.value.serviceChecked = hasSelectedService

  // [추가/변경 기능] 다음 버튼 클릭 후에는 선택 서비스가 모두 해제되어도 전체선택 비활성 상태를 유지한다.
  if (!hasSelectedService && !isServiceSelectCompleted.value) {
    model.value.serviceSelectCompleteYn = 'N'
    model.value.serviceSelectCompleted = false
  }

  console.log('[변경][서비스선택][서비스체크상태반영]', {
    selected,
    hasSelectedService,
    serviceCheckYn: model.value.serviceCheckYn,
    serviceChecked: model.value.serviceChecked,
    serviceSelectCompleteYn: model.value.serviceSelectCompleteYn,
    serviceSelectCompleted: model.value.serviceSelectCompleted,
  })
}
const isSuspendedLine = computed(() => String(model.value.subStatus || '').toUpperCase() === 'S')
// [기존 기능] 수동 서비스 체크 버튼용 disabled/label computed.
// [추가/변경 기능] 서비스 체크 버튼을 미표출하므로 실행 코드는 주석 처리한다.
// const isServiceCheckButtonDisabled = computed(
//   () =>
//     isServiceSelectCompleted.value ||
//     (!isServiceCheckCompleted.value && !(model.value.serviceSelect || []).length),
// )
// const serviceCheckButtonLabel = computed(() => {
//   if (isServiceSelectCompleted.value) return '서비스 선택완료'
//   if (isServiceCheckCompleted.value) return '서비스 체크완료'
//   return '서비스 체크'
// })

const normalizeYn = (value) => String(value || '').toUpperCase()
const getMinutesOfDay = (date) => date.getHours() * 60 + date.getMinutes()
const isInTimeRange = (date, startMinutes, endMinutes) => {
  const minutes = getMinutesOfDay(date)

  return minutes >= startMinutes && minutes <= endMinutes
}
const isWeekend = (date) => [0, 6].includes(date.getDay())

const getBusinessTimeRestriction = (code, now = new Date()) => {
  if (code === NUMBER_CHANGE_CODE) {
    if (isWeekend(now)) {
      return '번호변경은 평일에만 선택 가능합니다.'
    }
    if (!isInTimeRange(now, 10 * 60, 20 * 60)) {
      return '번호변경은 평일 오전10시~오후8시까지 선택 가능합니다.'
    }
  }

  // if (code === DATA_SHARING_CODE && !isInTimeRange(now, 8 * 60, 21 * 60 + 50)) {
  //   return '데이터쉐어링 : 오전 08시~오후9:50까지 가능 (해당 시간대 이외 선택 불가)'
  // }

  return ''
}

const mapServiceTargetCode = (item) => {
  const concurrentChangeYn = normalizeYn(item.detail?.etcValue1)
  const separateReportImageYn = normalizeYn(item.detail?.etcValue2)
  const disabledReason = getBusinessTimeRestriction(item.code)

  return {
    value: item.code,
    label: item.title,
    svcTgtCd: item.code,
    concurrentChangeYn,
    separateReportImageYn,
    disabledReason,
    businessTimeDisabled: !!disabledReason,
    notConcurrentChange: concurrentChangeYn === 'N',
  }
}

const summarizeServiceList = (serviceList = model.value.serviceList) =>
  serviceList.map((item) => ({
    value: item.value,
    label: item.label,
    svcTgtCd: item.svcTgtCd,
    concurrentChangeYn: item.concurrentChangeYn,
    separateReportImageYn: item.separateReportImageYn,
    disabledReason: item.disabledReason || '',
    disabled: item.disabled || false,
  }))

const isExclusiveService = (value) => EXCLUSIVE_SERVICE_CODES.includes(value)
const getPaySources = () => [model.value, model.value.payData || {}, model.value.billData || {}]
const getPaySourceValue = (source, keys) => {
  for (const key of keys) {
    const value = source?.[key]
    if (value !== undefined && value !== null && String(value) !== '') return value
  }
  return ''
}
const toNumber = (value) => {
  if (typeof value === 'number') return Number.isFinite(value) ? value : 0
  const normalized = String(value || '').replace(/[^0-9.-]/g, '')
  if (!normalized) return 0
  const numberValue = Number(normalized)
  return Number.isFinite(numberValue) ? numberValue : 0
}
const isAffirmativeYn = (value) =>
  ['Y', 'YES', 'TRUE', '1'].includes(String(value || '').trim().toUpperCase())
const hasUnpaidCharge = () => {
  const delinqStatusKeys = ['colDelinqStatus', 'COL_DELINQ_STATUS']
  const ynKeys = ['unpaidYn', 'unpayYn', 'arrearYn', 'delinqYn', 'delinquentYn', 'overdueYn']
  const countKeys = ['delinqStatusCnt', 'unpaidCnt', 'unpayCnt', 'arrearCnt', 'overdueCnt']
  const amountKeys = [
    'unpaidAmt',
    'unpayAmt',
    'arrearAmt',
    'delinqAmt',
    'overdueAmt',
    'pastDueAmt',
    'unpaidAmount',
    'unpayAmount',
    'totalUnpaidAmt',
    'billUnpaidAmt',
  ]

  return getPaySources().some(
    (source) =>
      String(getPaySourceValue(source, delinqStatusKeys) || '').toUpperCase() === 'D' ||
      isAffirmativeYn(getPaySourceValue(source, ynKeys)) ||
      toNumber(getPaySourceValue(source, countKeys)) > 0 ||
      toNumber(getPaySourceValue(source, amountKeys)) > 0,
  )
}
const isUsimRestrictedMinor = () =>
  USIM_CHANGE_RESTRICTED_CUSTOMER_TYPES.includes(String(model.value.cstmrTypeCd || '').toUpperCase())
const getUsimChangeRestrictionMessage = () => {
  if (hasUnpaidCharge()) return USIM_UNPAID_RESTRICTED_MESSAGE
  if (isUsimRestrictedMinor()) return USIM_MINOR_RESTRICTED_MESSAGE
  return ''
}
const isSelectableServiceValue = (value) =>
  (!isSuspendedLine.value || value === UNPAUSE_CODE) &&
  !(value === USIM_CHANGE_CODE && getUsimChangeRestrictionMessage())

const getConcurrentServiceValues = () =>
  (model.value.serviceList || [])
    .filter((item) => !item.notConcurrentChange)
    .filter((item) => isSelectableServiceValue(item.value))
    .map((item) => item.value)

const isAllServiceSelected = (selectedValues = model.value.serviceSelect) => {
  const selected = Array.isArray(selectedValues) ? selectedValues : []
  const allValues = getConcurrentServiceValues()

  return allValues.length > 0 && allValues.every((value) => selected.includes(value))
}

const syncAllCheck = (selectedValues = model.value.serviceSelect) => {
  model.value.allCheck = isAllServiceSelected(selectedValues) ? 'Y' : 'N'
}

const setServiceListDisabled = (selectedValues = model.value.serviceSelect) => {
  const selected = Array.isArray(selectedValues) ? selectedValues : []
  const serviceList = model.value.serviceList || []
  const hasExclusiveSelected = selected.some(isExclusiveService)
  const usimChangeRestrictionMessage = getUsimChangeRestrictionMessage()

  model.value.serviceList = serviceList.map((item) => {
    const disabledReason = item.businessTimeDisabled ? item.disabledReason : ''
    if (isSuspendedLine.value && item.value !== UNPAUSE_CODE) return { ...item, disabled: true }
    if (item.value === USIM_CHANGE_CODE && usimChangeRestrictionMessage) {
      return { ...item, disabled: true, disabledReason: usimChangeRestrictionMessage }
    }
    if (selected.includes(item.value)) return { ...item, disabled: false, disabledReason }
    const disabled =
      item.businessTimeDisabled || (hasExclusiveSelected && isExclusiveService(item.value))
    return { ...item, disabled, disabledReason }
  })
}

const normalizeSuspendedServiceSelection = () => {
  if (!isSuspendedLine.value) return

  const selected = Array.isArray(model.value.serviceSelect) ? model.value.serviceSelect : []
  const normalizedSelected = selected.filter((value) => value === UNPAUSE_CODE)

  if (normalizedSelected.length !== selected.length) {
    model.value.serviceSelect = normalizedSelected
    model.value.serviceCheckYn = 'N'
    model.value.serviceChecked = false
    model.value.serviceSelectCompleteYn = 'N'
    model.value.serviceSelectCompleted = false
    serviceChipKey.value += 1
  }

  setServiceListDisabled(normalizedSelected)
  syncAllCheck(normalizedSelected)
  lastValidServiceSelect.value = [...normalizedSelected]
}

const normalizeUsimServiceSelection = () => {
  const usimChangeRestrictionMessage = getUsimChangeRestrictionMessage()
  const selected = Array.isArray(model.value.serviceSelect) ? model.value.serviceSelect : []

  if (!usimChangeRestrictionMessage || !selected.includes(USIM_CHANGE_CODE)) {
    setServiceListDisabled(selected)
    syncAllCheck(selected)
    return
  }

  resetServiceData(USIM_CHANGE_CODE)
}

const getSelectedServiceValidationMessage = (selectedValues = []) => {
  const selected = Array.isArray(selectedValues) ? selectedValues : []
  const serviceList = model.value.serviceList || []
  const selectedItems = serviceList.filter((item) => selected.includes(item.value))
  const timeRestrictedItem = selectedItems.find((item) => item.disabledReason)
  const usimChangeRestrictionMessage = selected.includes(USIM_CHANGE_CODE)
    ? getUsimChangeRestrictionMessage()
    : ''

  if (isSuspendedLine.value && selected.some((value) => value !== UNPAUSE_CODE)) {
    return SUSPENDED_ONLY_MESSAGE
  }

  if (usimChangeRestrictionMessage) {
    return usimChangeRestrictionMessage
  }

  if (timeRestrictedItem) {
    return timeRestrictedItem.disabledReason
  }

  if (selected.filter(isExclusiveService).length > 1) {
    return '요금제 변경, 번호변경, 분실복구/일시정지해제 신청은 동시 변경할 수 없습니다.'
  }

  return ''
}

const fetchServiceTargetCodes = async () => {
  console.log('[변경][서비스선택][공통코드조회] 요청 시작', {
    groupCode: SERVICE_TARGET_GROUP_CODE,
  })

  try {
    const list = await getCommonCodeListWithDetail(SERVICE_TARGET_GROUP_CODE)
    console.log('[변경][서비스선택][공통코드조회] 응답 수신', {
      groupCode: SERVICE_TARGET_GROUP_CODE,
      count: Array.isArray(list) ? list.length : 0,
      list,
    })

    if (!Array.isArray(list)) {
      console.warn('[변경][서비스선택][공통코드조회] 진행 중단', {
        reason: 'invalid response',
        groupCode: SERVICE_TARGET_GROUP_CODE,
      })
      model.value.serviceList = []
      model.value.serviceSelect = []
      return
    }

    const serviceList = list.map(mapServiceTargetCode)
    const availableValues = serviceList.map((item) => item.value)

    model.value.serviceSelect = (model.value.serviceSelect || []).filter(
      (value) => availableValues.includes(value) && isSelectableServiceValue(value),
    )
    model.value.serviceList = serviceList
    setServiceListDisabled(model.value.serviceSelect)
    syncAllCheck(model.value.serviceSelect)
    lastValidServiceSelect.value = [...model.value.serviceSelect]

    console.log('[변경][서비스선택][공통코드조회] 화면 데이터 반영 결과', {
      selected: model.value.serviceSelect,
      allCheck: model.value.allCheck,
      serviceList: summarizeServiceList(),
    })
  } catch (error) {
    console.error('[변경][서비스선택][공통코드조회] 예외 발생', {
      message: error?.message,
      response: error?.response?.data,
    })
    model.value.serviceList = []
    model.value.serviceSelect = []
  }
}

watch(
  () => model.value?.subStatus,
  () => {
    normalizeSuspendedServiceSelection()
  },
)

watch(
  () => [
    model.value?.cstmrTypeCd,
    JSON.stringify(model.value?.payData || {}),
    JSON.stringify(model.value?.billData || {}),
  ],
  () => {
    normalizeUsimServiceSelection()
  },
)

async function update(value) {
  if (isServiceSelectionLocked.value) return

  // [기존 기능] 다음 단계 진입 후(serviceSelectCompleteYn=Y)에만 서비스별 초기화/재추가를 허용했다.
  // if (isServiceSelectCompleted.value) {
  // [추가/변경 기능] 서비스 체크 완료 이후에도 서비스별 추가/초기화를 허용한다.
  if (isServiceCheckCompleted.value) {
    const selected = Array.isArray(value) ? value : []
    const previous = [...lastValidServiceSelect.value]
    const removedCode = previous.find((s) => !selected.includes(s))
    const addedCode = selected.find((s) => !previous.includes(s))

    //console.log('[변경][서비스선택][체크완료후선택변경]', {
    //  selected,
    //  previous,
    //  addedCode,
    //  addedName: addedCode ? getServiceLabel(addedCode) : '',
    //  removedCode,
    //  removedName: removedCode ? getServiceLabel(removedCode) : '',
    //  serviceCheckYn: model.value.serviceCheckYn,
    //  serviceSelectCompleteYn: model.value.serviceSelectCompleteYn,
    //})

    if (removedCode) {
      // [추가/변경 기능] 서비스별 초기화: 확인완료 서비스는 확인 후 초기화, 진행중 서비스는 바로 초기화한다.
      model.value.serviceSelect = previous
      serviceChipKey.value++
      const confirmField = CONFIRM_FIELD_MAP[removedCode]
      const isConfirmed = confirmField ? model.value[confirmField] === true : false
      if (isConfirmed) {
        const serviceName = getServiceLabel(removedCode)
        await nextTick()
        const confirmed = await showConfirmAsync(`[${serviceName}] 영역을 초기화하시겠습니까?`)
        console.log('[변경][서비스선택][서비스해제확인]', {
          removedCode,
          removedName: serviceName,
          confirmed,
        })
        if (confirmed) {
          resetServiceData(removedCode)
        }
      } else {
        resetServiceData(removedCode)
      }
    } else if (addedCode) {
      // [추가/변경 기능] 서비스별 추가: 선택 가능 시간/동시 변경 제한만 검증하고 즉시 추가한다.
      const validationMessage = getSelectedServiceValidationMessage(selected)
      if (validationMessage) {
        model.value.serviceSelect = previous
        serviceChipKey.value++
        console.warn('[변경][서비스선택][서비스추가중단]', {
          reason: 'validation failed',
          addedCode,
          addedName: getServiceLabel(addedCode),
          selected,
          restored: previous,
          message: validationMessage,
        })
        await nextTick()
        showAlert(validationMessage)
        return
      }
      model.value.serviceSelect = selected
      setServiceListDisabled(selected)
      syncAllCheck(selected)
      applyServiceCheckState(selected)
      lastValidServiceSelect.value = [...selected]
      console.log('[변경][서비스선택][서비스추가완료]', {
        addedCode,
        addedName: getServiceLabel(addedCode),
        selected: model.value.serviceSelect,
        allCheck: model.value.allCheck,
        serviceList: summarizeServiceList(),
      })
    } else {
      model.value.serviceSelect = previous
      serviceChipKey.value++
      console.log('[변경][서비스선택][변경없음]', {
        restored: previous,
      })
    }
    return
  }

  // [기존 기능] 서비스 체크 완료 후에는 선택 변경을 차단했다.
  // if (isServiceCheckCompleted.value) return

  const previousSelected = [...lastValidServiceSelect.value]
  const selected = Array.isArray(value) ? value : []
  const validationMessage = getSelectedServiceValidationMessage(selected)

  //console.log('[변경][서비스선택] 선택 변경', {
  //  selected,
  //  validationMessage,
  //  before: summarizeServiceList(),
  //})

  if (validationMessage) {
    model.value.serviceSelect = previousSelected
    syncAllCheck(previousSelected)
    serviceChipKey.value += 1
    console.warn('[변경][서비스선택] 진행 중단', {
      reason: 'validation failed',
      selected,
      restored: previousSelected,
      message: validationMessage,
    })
    await nextTick()
    showAlert(validationMessage)
    return
  }

  model.value.serviceSelect = selected
  applyServiceCheckState(selected)
  // [기존 기능] 서비스 체크 버튼을 눌러야 serviceCheckYn/serviceChecked가 완료 처리됐다.
  // model.value.serviceCheckYn = 'N'
  // model.value.serviceChecked = false
  model.value.serviceSelectCompleteYn = 'N'
  model.value.serviceSelectCompleted = false
  setServiceListDisabled(selected)
  syncAllCheck(selected)
  lastValidServiceSelect.value = [...selected]
  console.log('[변경][서비스선택] 화면 데이터 반영 결과', {
    selected: model.value.serviceSelect,
    allCheck: model.value.allCheck,
    serviceList: summarizeServiceList(),
  })
}

async function updateAllCheck(event) {
  if (isServiceSelectionLocked.value) return

  // [기존 기능] 서비스 체크 완료 후 전체선택 변경을 차단했다.
  // if (isServiceCheckCompleted.value) return
  // [추가/변경 기능] 다음 버튼 클릭 후에는 전체선택 변경을 차단한다.
  if (isServiceSelectCompleted.value) return

  const checked = event?.target?.checked === true
  const selected = checked ? getConcurrentServiceValues() : []
  const validationMessage = getSelectedServiceValidationMessage(selected)

  console.log('[변경][서비스선택][전체선택] 선택 변경', {
    checked,
    excluded: checked
      ? (model.value.serviceList || [])
          .filter((item) => item.notConcurrentChange || item.businessTimeDisabled)
          .map((item) => ({
            value: item.value,
            notConcurrentChange: item.notConcurrentChange,
            disabledReason: item.disabledReason || '',
          }))
      : [],
    selected,
    validationMessage,
    before: summarizeServiceList(),
  })

  if (validationMessage) {
    model.value.allCheck = 'N'
    model.value.serviceSelect = [...lastValidServiceSelect.value]
    syncAllCheck(lastValidServiceSelect.value)
    serviceChipKey.value += 1
    console.warn('[변경][서비스선택][전체선택] 진행 중단', {
      reason: 'validation failed',
      selected,
      restored: lastValidServiceSelect.value,
      message: validationMessage,
    })
    await nextTick()
    showAlert(validationMessage)
    return
  }

  model.value.allCheck = checked ? 'Y' : 'N'
  model.value.serviceSelect = selected
  applyServiceCheckState(selected)
  // [기존 기능] 전체선택 변경 후에는 서비스 체크 버튼을 다시 누르도록 체크 상태를 해제했다.
  // model.value.serviceCheckYn = 'N'
  // model.value.serviceChecked = false
  model.value.serviceSelectCompleteYn = 'N'
  model.value.serviceSelectCompleted = false
  setServiceListDisabled(selected)
  lastValidServiceSelect.value = [...selected]

  console.log('[변경][서비스선택][전체선택] 화면 데이터 반영 결과', {
    selected: model.value.serviceSelect,
    allCheck: model.value.allCheck,
    serviceList: summarizeServiceList(),
  })
}

// [기존 기능] 수동 서비스 체크 버튼에서 사용하던 검증 함수.
// [추가/변경 기능] 현재는 서비스 칩 클릭 시 update()에서 동일 검증 후 applyServiceCheckState()를 호출한다.
// const checkServiceSelection = async () => {
//   const selected = Array.isArray(model.value.serviceSelect) ? model.value.serviceSelect : []
//
//   if (isServiceCheckCompleted.value && !isServiceSelectCompleted.value) {
//     console.log('[변경][서비스체크] 체크 완료 해제', {
//       selected,
//       allCheck: model.value.allCheck,
//       serviceCheckYn: model.value.serviceCheckYn,
//     })
//     model.value.serviceCheckYn = 'N'
//     model.value.serviceChecked = false
//     model.value.serviceSelectCompleteYn = 'N'
//     model.value.serviceSelectCompleted = false
//     serviceChipKey.value += 1
//     await nextTick()
//     return
//   }
//
//   console.log('[변경][서비스체크] 요청 시작', {
//     selected,
//     allCheck: model.value.allCheck,
//     serviceCheckYn: model.value.serviceCheckYn,
//   })
//
//   if (selected.length === 0) {
//     console.warn('[변경][서비스체크] 진행 중단', {
//       reason: 'service not selected',
//     })
//     showAlert('서비스를 선택해 주세요.')
//     return
//   }
//
//   const validationMessage = getSelectedServiceValidationMessage(selected)
//   if (validationMessage) {
//     console.warn('[변경][서비스체크] 진행 중단', {
//       reason: 'validation failed',
//       selected,
//       message: validationMessage,
//     })
//     showAlert(validationMessage)
//     return
//   }
//
//   model.value.serviceCheckYn = 'Y'
//   model.value.serviceChecked = true
//   serviceChipKey.value += 1
//   await nextTick()
//
//   console.log('[변경][서비스체크] 화면 데이터 반영 결과', {
//     selected: model.value.serviceSelect,
//     allCheck: model.value.allCheck,
//     serviceCheckYn: model.value.serviceCheckYn,
//   })
// }

onMounted(() => {
  fetchServiceTargetCodes()
})
</script>
<template>
  <!-- [추가/변경 기능] 다음 버튼 이후 영역 데이터 로딩 오버레이는 ServiceChangeCustomer/Product의 전역 loadingStore에서 관리한다. -->
  <!-- 서비스 변경 선택 -->
  <MsfTitleArea title="서비스 변경 선택" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="서비스 선택" tag="div" required>
      <MsfStack vertical>
        <!-- [기존 기능] 서비스 체크 완료 후 전체선택 재클릭 차단: :disabled="isServiceCheckCompleted || isSuspendedLine" -->
        <!-- [추가/변경 기능] 다음 버튼 클릭 후(serviceSelectCompleteYn=Y)에만 전체선택을 비활성화 -->
        <MsfCheckbox
          v-model="model.allCheck"
          label="전체 선택"
          :returndata="{ true: 'Y', false: 'N' }"
          :disabled="isServiceSelectionLocked || isServiceSelectCompleted || isSuspendedLine"
          @change="updateAllCheck"
        />
        <!-- [기존 기능] 서비스 체크 완료 후 칩 전체를 readonly 처리: :readonly="isChipReadonly" -->
        <!-- [추가/변경 기능] isChipReadonly를 false로 고정해 서비스별 추가/초기화를 허용 -->
        <!-- [추가/변경 기능] 서비스상품 영역 다음 버튼 완료 후에는 칩을 readonly 처리 -->
        <MsfChip
          :key="serviceChipKey"
          v-model="model.serviceSelect"
          name="inp-serviceSelect"
          :data="enhancedServiceList"
          :readonly="isChipReadonly"
          multiple
          @change="update"
        />
        <MsfTextList margin="0">
          <li>
            동시 변경 불가능 업무
            <MsfTextList type="dash">
              <li>요금제 변경, 번호변경, 분실복구/일시정지해제 신청</li>
            </MsfTextList>
          </li>
          <li>
            번호변경 : 평일 오전10시~오후8시까지 가능 (해당 시간대 이외 및 주말/공휴일 선택 불가)
          </li>
          <!-- <li>데이터쉐어링 : 오전 08시~오후9:50까지 가능 (해당 시간대 이외 선택 불가)</li> -->
        </MsfTextList>
      </MsfStack>
      <!--
        [기존 기능] 서비스 선택 후 별도 버튼으로 선택 검증/체크완료 처리.
        [추가/변경 기능] 각 서비스 칩 클릭 시마다 체크 기능을 즉시 수행하므로 버튼은 표출하지 않는다.
        <MsfButtonGroup borderTop align="left">
          <MsfButton
            variant="toggle"
            :active="isServiceCheckCompleted"
            :disabled="isServiceCheckButtonDisabled"
            @click="checkServiceSelection"
          >
            {{ serviceCheckButtonLabel }}
          </MsfButton>
        </MsfButtonGroup>
      -->
    </MsfFormGroup>
  </MsfStack>
  <!-- // 서비스 변경 선택 -->
</template>

<style scoped>
/* 진행중인 서비스 칩: 주황색으로 표시 */
:deep(.chip-inprogress .input:checked + .label) {
  --service-chip-progress-color: #f5a623;
  --service-chip-progress-bg-color: rgba(245, 166, 35, 0.06);
  --chip-border-color: var(--service-chip-progress-color);
  --chip-text-color: var(--service-chip-progress-color);
  box-shadow: inset 0 0 0 1px var(--service-chip-progress-color);
  background-color: var(--service-chip-progress-bg-color);
}

:deep(.chip-inprogress .input:disabled + .label) {
  cursor: not-allowed;
}

/* 확인완료된 서비스 칩: 초록색으로 표시 */
:deep(.chip-confirmed .input:checked + .label) {
  --chip-border-color: var(--color-accent2-base);
  --chip-text-color: var(--color-accent2-base);
  box-shadow: inset 0 0 0 1px var(--color-accent2-base);
  background-color: var(--color-bg-3);
}
</style>
