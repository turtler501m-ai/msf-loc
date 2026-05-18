<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="부가서비스 추가/삭제"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfStack vertical type="formgroups">
      <MsfSelect
        title="추천 부가서비스"
        v-model="service"
        :options="recommendOptions"
        placeholder="추천 부가서비스"
      />

      <MsfTitleArea title="무료 부가 서비스" level="2" class="ut-mt-20" />
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
          <template v-if="freeVasOptions.length > 0">
            <tr v-for="opt in freeVasOptions" :key="opt.value">
              <td class="ut-text-center">
                <MsfCheckbox
                  :id="`popup-free-${opt.value}`"
                  v-model="freeService"
                  :value="opt.value"
                  :label="opt.name"
                  hideLabel
                />
              </td>
              <td><label :for="`popup-free-${opt.value}`">{{ opt.name }}</label></td>
              <td class="ut-text-center">{{ getAmountLabel(opt) }}</td>
              <td class="ut-text-center">
                <MsfButton
                  v-if="opt.settingYn === 'Y' && getSettingModalType(opt) && getSettingModalType(opt) !== 'autoDefault'"
                  variant="subtle"
                  style="white-space: nowrap"

                  @click="onSettingClick(opt)"
                >{{ serviceSettingMap[opt.value] ? '설정완료' : '설정' }}</MsfButton>
              </td>
            </tr>
          </template>
          <tr v-else>
            <td colspan="4"><div class="nodata-wrap">추가 가능한 무료 부가서비스가 없습니다.</div></td>
          </tr>
        </template>
      </MsfTable>

      <MsfTitleArea title="유료 부가 서비스" level="2" class="ut-mt-20" />
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
          <template v-if="paidVasOptions.length > 0">
            <tr v-for="opt in paidVasOptions" :key="opt.value">
              <td class="ut-text-center">
                <MsfCheckbox
                  :id="`popup-paid-${opt.value}`"
                  v-model="paidService"
                  :value="opt.value"
                  :label="opt.name"
                  hideLabel
                />
              </td>
              <td><label :for="`popup-paid-${opt.value}`">{{ opt.name }}</label></td>
              <td class="ut-text-center">{{ getAmountLabel(opt) }}</td>
              <td class="ut-text-center">
                <MsfButton
                  v-if="opt.settingYn === 'Y' && getSettingModalType(opt) && getSettingModalType(opt) !== 'autoDefault'"
                  variant="subtle"
                  style="white-space: nowrap"

                  @click="onSettingClick(opt)"
                >{{ serviceSettingMap[opt.value] ? '설정완료' : '설정' }}</MsfButton>
              </td>
            </tr>
          </template>
          <tr v-else>
            <td colspan="4"><div class="nodata-wrap">추가 가능한 유료 부가서비스가 없습니다.</div></td>
          </tr>
        </template>
      </MsfTable>
    </MsfStack>

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
    :min-length="0"
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
import { ref, computed, onMounted } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
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
const service = ref('')
const freeService = ref([])
const paidService = ref([])
const recommendOptions = ref([])
const freeVasOptions = ref([])
const paidVasOptions = ref([])

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

const getAmountLabel = (opt = {}) => {
  const amount = opt.amount ?? 0
  if (amount === 0) return '무료'
  return `${Number(amount).toLocaleString()} 원`
}

const getServiceKey = (svc = {}) => String(svc.rateCd || svc.value || svc.soc || svc.prodId || svc.addSvcCd || '')
const getServiceName = (svc = {}) =>
  svc.rateNm || svc.socDescription || svc.prodNm || svc.addSvcNm || svc.serviceName || '-'
const getServiceAmount = (svc = {}) =>
  svc.baseAmt ?? svc.socRateVatValue ?? svc.socRateVat ?? svc.socRateValue ?? 0

const toOption = (svc = {}) => {
  const amount = toNumber(getServiceAmount(svc))
  const rateCd = getServiceKey(svc)
  const rateNm = getServiceName(svc)
  return {
    ...svc,
    label: amount === 0 ? rateNm : `${rateNm} (${amount.toLocaleString()}원)`,
    value: rateCd,
    name: rateNm,
    amount,
    settingYn: svc.settingYn || (getSettingModalType(svc) ? 'Y' : 'N'),
  }
}

// ─── 옵션 세팅 (이용중 항목 제외) ────────────────────────────────────────────
const setOptionsFromServices = () => {
  const activeFreeSet = new Set(props.activeFreeIds)
  const activePaidSet = new Set(props.activePaidIds)
  freeVasOptions.value = props.freeServices
    .map(toOption)
    .filter((opt) => opt.value && !activeFreeSet.has(opt.value))
  paidVasOptions.value = props.paidServices
    .map(toOption)
    .filter((opt) => opt.value && !activePaidSet.has(opt.value))
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

// ─── 팝업 열기/닫기 ───────────────────────────────────────────────────────────
const onOpen = () => {
  service.value = ''
  freeService.value = []
  paidService.value = []
  serviceSettingMap.value = {}
  fetchRecommendCodes()
  setOptionsFromServices()
  emit('open')
}

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// ─── 추천 부가서비스 공통코드 조회 ────────────────────────────────────────────
const fetchRecommendCodes = async () => {
  try {
    const list = await getCommonCodeList('RATE_ADSVC_DIV_CD')
    recommendOptions.value = list.map(item => ({ label: item.title, value: item.code }))
  } catch (error) {
    console.error('추천 부가서비스 코드 조회 실패:', error)
  }
}

// ─── 확인: 설정 데이터 포함해서 emit ─────────────────────────────────────────
const onConfirm = () => {
  const withSettingData = (opt) => {
    const stored = serviceSettingMap.value[opt.value]
    return {
      ...opt,
      rateCd: opt.value,
      rateNm: opt.name,
      baseAmt: opt.amount,
      ...(stored ? {
        addSvcSettingCompleted: true,
        addSvcSettingData: stored,
        ftrNewParam: stored.ftrNewParam || '',
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
    recommendService: service.value,
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
</style>
