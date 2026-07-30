<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="부가서비스 추가/삭제"
    @open="onOpen"
    @close="onClose"
  >
    <template #navBar>
      <MsfSelect
        title="부가서비스 카테고리"
        v-model="selectedCategory"
        :options="planCategoryOptions"
        placeholder="카테고리를 선택하세요"
      />
    </template>

    <!-- 팝업 내용 -->
      <template v-if="showFreeSection">
      <MsfTitleArea title="무료 부가 서비스" level="2" class="ut-mt-20" />
      <MsfTable>
        <template #colgroup>
          <col style="width: 68px" />
          <col :style="{ width: codeColWidth + 'px' }" />
          <col class="service-name-col" />
          <col style="width: 160px" />
          <col style="width: 125px" />
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
            <tr v-for="opt in freeVasOptions" :key="opt.value">
              <td class="ut-text-center">
                <MsfCheckbox
                  :id="`popup-free-${opt.value}`"
                  v-model="freeService"
                  :value="opt.value"
                  :label="opt.name"
                  hideLabel
                  :disabled="isCancelDisabled(opt)"
                />
              </td>
              <td class="service-code-cell ut-text-center" :style="{ maxWidth: codeColWidth + 'px', padding: codeColWidth < 10 ? '0' : undefined }">{{ opt.value }}</td>
              <td class="service-name-cell"><label :for="`popup-free-${opt.value}`">{{ opt.name }}</label></td>
              <td class="ut-text-center amount-cell">{{ getAmountLabel(opt) }}</td>
              <td class="ut-text-center setting-cell">
                <MsfButton
                  v-if="opt.settingYn === 'Y' && getSettingModalType(opt) && getSettingModalType(opt) !== 'autoDefault'"
                  variant="subtle"
                  style="white-space: nowrap"
                  :disabled="isActiveService(opt.value)"
                  @click="onSettingClick(opt)"
                >{{ serviceSettingMap[opt.value] || isActiveService(opt.value) ? '설정완료' : '설정' }}</MsfButton>
              </td>
            </tr>
        </template>
      </MsfTable>
      </template>

      <template v-if="showPaidSection">
      <MsfTitleArea title="유료 부가 서비스" level="2" class="ut-mt-20" />
      <MsfTable>
        <template #colgroup>
          <col style="width: 68px" />
          <col :style="{ width: codeColWidth + 'px' }" />
          <col class="service-name-col" />
          <col style="width: 160px" />
          <col style="width: 125px" />
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
            <tr v-for="opt in paidVasOptions" :key="opt.value">
              <td class="ut-text-center setting-cell">
                <MsfCheckbox
                  :id="`popup-paid-${opt.value}`"
                  v-model="paidService"
                  :value="opt.value"
                  :label="opt.name"
                  hideLabel
                  :disabled="isCancelDisabled(opt)"
                />
              </td>
              <td class="service-code-cell ut-text-center" :style="{ maxWidth: codeColWidth + 'px', padding: codeColWidth < 10 ? '0' : undefined }">{{ opt.value }}</td>
              <td class="service-name-cell"><label :for="`popup-paid-${opt.value}`">{{ opt.name }}</label></td>
              <td class="ut-text-center amount-cell">{{ getAmountLabel(opt) }}</td>
              <td class="ut-text-center">
                <MsfButton
                  v-if="opt.settingYn === 'Y' && getSettingModalType(opt) && getSettingModalType(opt) !== 'autoDefault'"
                  variant="subtle"
                  style="white-space: nowrap"
                  :disabled="isActiveService(opt.value)"
                  @click="onSettingClick(opt)"
                >{{ serviceSettingMap[opt.value] || isActiveService(opt.value) ? '설정완료' : '설정' }}</MsfButton>
              </td>
            </tr>
        </template>
      </MsfTable>
      </template>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>

  <!-- 설정 팝업들 — 부가서비스 팝업 내에서 자체 완결 -->
  <MsfIllegalTmBlockModal
    :model-value="showSettingModal && settingModalType === 'illegalTm'"
    :max-count="50"
    :min-length="3"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfIllegalTmBlockModal
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
  <MsfInfoProviderBlockModal
    v-if="settingModalType === 'infoProviderBlock'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfNumberSpoofingBlockModal
    :model-value="showSettingModal && settingModalType === 'numberSpoofing'"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfRoamingStartDateModal
    v-if="settingModalType === 'roamingDate8'"
    :model-value="showSettingModal"
    variant="date8"
    :setting-data="currentSettingData"
    :service-name="currentSettingService.name"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfRoamingStartDateModal
    v-if="settingModalType === 'roamingDateRange'"
    :model-value="showSettingModal"
    variant="dateTimeRange"
    :setting-data="currentSettingData"
    :service-name="currentSettingService.name"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfRoamingShareMainModal
    v-if="settingModalType === 'roamingShareMain1'"
    :model-value="showSettingModal"
    variant="main1"
    :setting-data="currentSettingData"
    :main-phone-number="phoneNumber"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfRoamingShareMainModal
    v-if="settingModalType === 'roamingShareMain2'"
    :model-value="showSettingModal"
    variant="main2"
    :setting-data="currentSettingData"
    :main-phone-number="phoneNumber"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfRoamingShareSubModal
    v-if="settingModalType === 'roamingShareSub1'"
    :model-value="showSettingModal"
    variant="sub1"
    :service-name="currentSettingService.name"
    :setting-data="currentSettingData"
    :ncn="props.ncn"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfRoamingShareSubModal
    v-if="settingModalType === 'roamingShareSub2'"
    :model-value="showSettingModal"
    variant="sub2"
    :setting-data="currentSettingData"
    :ncn="props.ncn"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfNotifyPhoneModal
    v-if="settingModalType === 'notifyPhone'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfAlertNumbersModal
    v-if="settingModalType === 'alertNumbers'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfFreeCallNumberModal
    v-if="settingModalType === 'freeCallNumber'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
  <MsfMilitaryTimePlanModal
    v-if="settingModalType === 'militaryTimePlan'"
    :model-value="showSettingModal"
    :setting-data="currentSettingData"
    @update:model-value="closeSettingModal"
    @close="closeSettingModal"
    @confirm="applySettingData"
  />
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { post } from '@/libs/api/msf.api'
import MsfIllegalTmBlockModal from './MsfIllegalTmBlockModal.vue'
import MsfInfoProviderBlockModal from './MsfInfoProviderBlockModal.vue'
import MsfNumberSpoofingBlockModal from './MsfNumberSpoofingBlockModal.vue'
import MsfRoamingStartDateModal from './MsfRoamingStartDateModal.vue'
import MsfRoamingShareMainModal from './MsfRoamingShareMainModal.vue'
import MsfRoamingShareSubModal from './MsfRoamingShareSubModal.vue'
import MsfNotifyPhoneModal from './MsfNotifyPhoneModal.vue'
import MsfAlertNumbersModal from './MsfAlertNumbersModal.vue'
import MsfFreeCallNumberModal from './MsfFreeCallNumberModal.vue'
import MsfMilitaryTimePlanModal from './MsfMilitaryTimePlanModal.vue'

const props = defineProps({
  modelValue: Boolean,
  freeServices: { type: Array, default: () => [] },
  paidServices: { type: Array, default: () => [] },
  activeFreeIds: { type: Array, default: () => [] },
  activePaidIds: { type: Array, default: () => [] },
  phoneNumber: { type: String, default: '' }, // 로밍 대표 설정용 전화번호 (부모에서 전달)
  ncn: { type: String, default: '' },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

// ─── 서비스 선택 상태 ─────────────────────────────────────────────────────────
const selectedCategory = ref('')
const freeService = ref([])
const paidService = ref([])
const planCategoryOptions = ref([])
const freeVasOptions = ref([])
const paidVasOptions = ref([])
const ALWAYS_EXCLUDED_SERVICE_IDS = ['WIRELESSC']

const showFreeSection = computed(() => freeVasOptions.value.length > 0)
const showPaidSection = computed(() => paidVasOptions.value.length > 0)

// ─── 부가서비스코드 컬럼 리사이즈 ─────────────────────────────────────────────
const codeColWidth = ref(0)

const startCodeColResize = (e) => {
  e.preventDefault()
  const startX = e.clientX
  const startWidth = codeColWidth.value
  const onMove = (ev) => {
    codeColWidth.value = Math.max(0, startWidth + ev.clientX - startX)
  }
  const onUp = () => {
    document.removeEventListener('mousemove', onMove)
    document.removeEventListener('mouseup', onUp)
  }
  document.addEventListener('mousemove', onMove)
  document.addEventListener('mouseup', onUp)
}

// ─── 설정 팝업 상태 ───────────────────────────────────────────────────────────
const settingModalType = ref('')
const settingServiceId = ref('')
const showSettingModal = ref(false)
// rateCd → settingData (plain object으로 reactivity 보장)
const serviceSettingMap = ref({})

// ─── 설정 팝업 매핑 ───────────────────────────────────────────────────────────
const SETTING_MODAL_MAP = {
  NOSPAM4: 'illegalTm',          NOSPAM2: 'blockNumber100',
  NOSPAM3: 'infoProviderBlock',  STLPVTPHN: 'numberSpoofing',
  DATAROM01: 'roamingDate8',     DATAROM03: 'roamingDate8',
  LTEDTROM5: 'roamingDate8',     ITGSAFE3G: 'roamingDate8',
  DYDTROM05: 'roamingDateRange',
  PL2078760: 'roamingDateRange', PL2079771: 'roamingDateRange', PL2079772: 'roamingDateRange',
  PL199N109: 'roamingShareMain1', PL199N120: 'roamingShareMain1',
  PL199N122: 'roamingShareMain1', PL199N126: 'roamingShareMain1',
  PL199N129: 'roamingShareMain1', PL199N132: 'roamingShareMain1',
  PL199N117: 'roamingShareSub1',  PL199N121: 'roamingShareSub1',
  PL199N123: 'roamingShareSub1',  PL199N127: 'roamingShareSub1',
  PL199N130: 'roamingShareSub1',  PL199N133: 'roamingShareSub1',
  PL2079777: 'roamingShareMain2', PL2079778: 'roamingShareSub2',
  DATAROMSM: 'notifyPhone',      FCARVLSMS: 'alertNumbers',
  SENOINFR1: 'freeCallNumber',   PL253A854: 'militaryTimePlan',
  ITCRBS: 'autoDefault',         RNGTOUPR3: 'autoDefault',
  SKCOREPAC: 'autoDefault',      XRINGMON: 'autoDefault', XRINGWEEK: 'autoDefault',
}

const getSettingModalType = (svc = {}) => {
  const key = String(svc.rateCd || svc.value || svc.soc || svc.prodId || svc.addSvcCd || '').toUpperCase()
  return SETTING_MODAL_MAP[key] ?? null
}

// ─── 유틸리티 ─────────────────────────────────────────────────────────────────
const toNumber = (value) => Number(String(value || 0).replace(/,/g, '')) || 0

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

const getAmountUnit = (svc = {}) => {
  if (isDailyFlatRateService(svc)) return '1일'
  return svc.chargeUnit || svc.rateUnit || ''
}

const getServiceKey = (svc = {}) => {
  if (typeof svc === 'string') return svc
  return String(svc.rateCd || svc.value || svc.soc || svc.prodId || svc.addSvcCd || svc.additionId || '')
}

const getDailyAdditionPeriod = (daily = {}) =>
  daily.USE_PRD || daily.usePrd || daily.usePeriod || daily.period || ''

const getPeriodLabel = (svc = {}) => {
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
  const unit = svc.amountUnit || getAmountUnit(svc)
  return unit || ''
}

const createDailyAdditionMap = (dailyAdditions = []) =>
  new Map(
    dailyAdditions
      .map((daily) => [getServiceKey(daily), daily])
      .filter(([code]) => Boolean(code)),
  )

const mergeDailyAdditionInfo = (svc = {}, dailyAdditionMap = new Map()) => {
  const daily = dailyAdditionMap.get(getServiceKey(svc))
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
  const serviceCodes = new Set(services.map(getServiceKey).filter(Boolean))
  const dailyOnlyServices = dailyAdditions
    .filter((daily) => {
      const code = getServiceKey(daily)
      return code && !serviceCodes.has(code)
    })
    .map((daily) =>
      mergeDailyAdditionInfo(
        {
          rateCd: getServiceKey(daily),
          rateNm: daily.RATE_NM || daily.rateNm || getServiceKey(daily),
          baseAmt: daily.BASE_AMT ?? daily.baseAmt ?? 0,
          additionKey: daily.ADDITION_KEY ?? daily.additionKey,
          sortOrdr: daily.SORT_ORDR ?? daily.sortOrdr ?? 9999,
        },
        createDailyAdditionMap([daily]),
      ),
    )

  return [...services, ...dailyOnlyServices]
}

const getVatIncludedAmount = (svc = {}) => {
  const vatAmount = svc.socRateVatValue ?? svc.socRateVat ?? svc.mmBasAmtVatDesc ?? svc.baseAmtVat
  if (vatAmount != null && vatAmount !== '') return toNumber(vatAmount)
  const amount = toNumber(getServiceAmount(svc))
  return amount === 0 ? 0 : Math.round(amount * 1.1)
}

const getAmountLabel = (opt = {}) => {
  const amount = toNumber(opt.amount)
  const periodLabel = getPeriodLabel(opt)
  if (amount === 0) return '무료'
  return `${amount.toLocaleString()}원${periodLabel ? ` / ${periodLabel}` : ''}`
}
const getServiceName = (svc = {}) =>
  svc.rateNm || svc.RATE_NM || svc.socDescription || svc.SOC_DESCRIPTION || svc.prodNm || svc.PROD_NM || svc.addSvcNm || svc.ADD_SVC_NM || svc.serviceName || '-'
const getServiceAmount = (svc = {}) =>
  svc.baseAmt ?? svc.BASE_AMT ?? svc.socRateValue ?? svc.SOC_RATE_VALUE ?? svc.socRateVatValue ?? svc.SOC_RATE_VAT_VALUE ?? svc.socRateVat ?? svc.SOC_RATE_VAT ?? 0

const toOption = (svc = {}) => {
  const amount = getVatIncludedAmount(svc)
  const amountUnit = getAmountUnit(svc)
  const periodLabel = getPeriodLabel({ ...svc, amountUnit })
  const rateCd = getServiceKey(svc)
  const rateNm = getServiceName(svc)
  return {
    ...svc,
    label: amount === 0 ? rateNm : `${rateNm} (${amount.toLocaleString()}원${periodLabel ? ` / ${periodLabel}` : ''})`,
    value: rateCd,
    name: rateNm,
    amount,
    amountUnit,
    chargeUnit: amountUnit,
    settingYn: svc.settingYn || (getSettingModalType(svc) ? 'Y' : 'N'),
  }
}


// ─── 설정 팝업 핸들러 ─────────────────────────────────────────────────────────
const currentSettingService = computed(() => {
  const allOpts = [...freeVasOptions.value, ...paidVasOptions.value]
  return allOpts.find((opt) => opt.value === settingServiceId.value) || {}
})

const currentSettingData = computed(() => {
  const svc = currentSettingService.value
  const stored = serviceSettingMap.value[svc.value] || {}
  return { ...svc, ...stored, addSvcSettingCompleted: !!serviceSettingMap.value[svc.value] }
})

const onSettingClick = (opt) => {
  const modalType = getSettingModalType(opt)
  if (!modalType || modalType === 'autoDefault') return
  settingModalType.value = modalType
  settingServiceId.value = opt.value
  showSettingModal.value = true
}

const closeSettingModal = () => {
  showSettingModal.value = false
  settingModalType.value = ''
  settingServiceId.value = ''
}

const applySettingData = (settingData = {}) => {
  const rateCd = settingServiceId.value
  if (rateCd) {
    if (settingData?.isReset) {
      const rest = { ...serviceSettingMap.value }
      delete rest[rateCd]
      serviceSettingMap.value = rest
      closeSettingModal()
      return
    }
    serviceSettingMap.value = { ...serviceSettingMap.value, [rateCd]: settingData }
    // 설정완료 시 해당 서비스 자동 선택
    if (freeVasOptions.value.some(o => o.value === rateCd) && !freeService.value.includes(rateCd)) {
      freeService.value = [...freeService.value, rateCd]
    } else if (paidVasOptions.value.some(o => o.value === rateCd) && !paidService.value.includes(rateCd)) {
      paidService.value = [...paidService.value, rateCd]
    }
  }
  closeSettingModal()
}

const isActiveService = (rateCd) => {
  const activeFreeSet = new Set(props.activeFreeIds || [])
  const activePaidSet = new Set(props.activePaidIds || [])
  return activeFreeSet.has(rateCd) || activePaidSet.has(rateCd)
}

const isCancelDisabled = (opt) => {
  if (!isActiveService(opt.value)) return false
  const svc = props.freeServices?.find(s => s.rateCd === opt.value) ||
              props.paidServices?.find(s => s.rateCd === opt.value)
  if (svc && svc.usePrd) {
    const todayStr = new Date().toISOString().slice(0, 10).replace(/-/g, '') // YYYYMMDD
    return svc.usePrd === todayStr
  }
  return false
}

// ─── 팝업 열기/닫기 ───────────────────────────────────────────────────────────
const onOpen = () => {
  selectedCategory.value = ''
  freeService.value = []
  paidService.value = []
  freeVasOptions.value = []
  paidVasOptions.value = []
  serviceSettingMap.value = {}
  fetchPlanCategories()
  emit('open')
}

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// ─── 부가서비스 카테고리 목록 조회 ────────────────────────────────────────────
const fetchPlanCategories = async () => {
  try {
    const res = await post('/api/form/addition/category/list', {
      rateAdsvcDivCd: 'R',
    })

    planCategoryOptions.value = res?.data?.map((item) => ({
      label: item.ctgNm,
      value: item.ctgCd,
    }))

    if (planCategoryOptions.value?.length > 0) {
      selectedCategory.value = planCategoryOptions.value[0].value
    }
  } catch (e) {
    console.error('Failed to fetch plan categories:', e)
  }
}

// ─── 카테고리별 부가서비스 목록 조회 ──────────────────────────────────────────
const fetchAdditionList = async (ctgCd) => {
  if (!ctgCd) {
    freeVasOptions.value = []
    paidVasOptions.value = []
    return
  }
  try {
    const res = await post('/api/form/addition/list', {
      operTypeCd: '',
      prodCtgTypeCd: 'R',
      categoryMstRequest: {
        prodCtgId: [ctgCd],
      },
    })

    if (res && res.code === '0000' && res.data?.[0]) {
      const result = res.data[0]
      const dailyAdditions = result.dailyAddition || []
      const dailyAdditionMap = createDailyAdditionMap(dailyAdditions)
      const freeList = (result.freeAddition || []).map((svc) =>
        mergeDailyAdditionInfo(svc, dailyAdditionMap),
      )
      const paidList = appendDailyOnlyAdditions(
        (result.paidAddition || []).map((svc) => mergeDailyAdditionInfo(svc, dailyAdditionMap)),
        dailyAdditions,
      )

      // 부모창 메인 목록에 이미 표시된 서비스는 무료/유료 섹션이 달라져도 팝업에서 제외한다.
      const parentServiceIds = new Set(
        [...props.freeServices, ...props.paidServices, ...ALWAYS_EXCLUDED_SERVICE_IDS]
          .map(getServiceKey)
          .filter(Boolean),
      )

      freeVasOptions.value = freeList
        .map(toOption)
        .filter((opt) => opt.value && !parentServiceIds.has(opt.value))
      paidVasOptions.value = paidList
        .map(toOption)
        .filter((opt) => opt.value && !parentServiceIds.has(opt.value))
    } else {
      freeVasOptions.value = []
      paidVasOptions.value = []
    }
  } catch (e) {
    console.error('Failed to fetch addition list:', e)
    freeVasOptions.value = []
    paidVasOptions.value = []
  }
}

watch(selectedCategory, (newVal) => {
  freeService.value = []
  paidService.value = []
  fetchAdditionList(newVal)
})

// ─── 확인: 설정 데이터 포함해서 emit ─────────────────────────────────────────
const onConfirm = () => {
  const withSettingData = (opt) => {
    const stored = serviceSettingMap.value[opt.value]

    let activeSetting = null
    if (isActiveService(opt.value)) {
      const parentSvc = props.freeServices?.find(s => s.rateCd === opt.value) ||
                        props.paidServices?.find(s => s.rateCd === opt.value)
      if (parentSvc?.addSvcSettingData) {
        activeSetting = parentSvc.addSvcSettingData
      }
    }

    return {
      ...opt,
      rateCd: opt.value,
      rateNm: opt.name,
      baseAmt: getServiceAmount(opt),
      socRateVatValue: opt.amount,
      usePrd: opt.usePrd || opt.USE_PRD || '',
      usePeriodDays:
        opt.usePeriodDays || getDailyAdditionPeriod(opt.dailyAddition),
      chargeUnit: opt.amountUnit || opt.chargeUnit || '',
      ...(stored || activeSetting ? {
        addSvcSettingCompleted: true,
        addSvcSettingData: stored || activeSetting,
        ftrNewParam: (stored || activeSetting).ftrNewParam || '',
      } : {}),
    }
  }

  const freeSelected = freeVasOptions.value
    .filter((opt) => freeService.value.includes(opt.value))
    .map(withSettingData)

  const paidSelected = paidVasOptions.value
    .filter((opt) => paidService.value.includes(opt.value))
    .map(withSettingData)

  emit('confirm', {
    recommendService: '',
    freeServices: freeSelected,
    paidServices: paidSelected,
    freeCodes: freeService.value,
    paidCodes: paidService.value,
  })
  onClose()
}

onMounted(() => {
  if (props.modelValue) {
    onOpen()
  }
})
</script>

<style lang="scss" scoped>
.ut-mt-20 {
  margin-top: rem(20px);
}

.amount-cell {
  white-space: nowrap;
}

.setting-cell {
  white-space: nowrap;
}

.code-col-th {
  overflow: hidden;
  white-space: nowrap;
  padding: 0 !important;
}

.service-name-th {
  position: sticky !important;
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
      background: var(--color-gray-150);
    }
  }
}

.service-code-cell {
  overflow: hidden;
  white-space: nowrap;
}

.service-name-col {
  min-width: 0;
}

.service-name-cell {
  min-width: 0;

  label {
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}
</style>
