<script setup>
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'
import { getCommonCodeListWithDetail } from '@/libs/utils/comn.utils'
import { computed, defineModel, nextTick, onMounted, ref, watch } from 'vue'

const model = defineModel({ type: Object, required: true })

const agencyOptions = ref([])
const lastValidServiceSelect = ref([])
const serviceChipKey = ref(0)
const SERVICE_TARGET_GROUP_CODE = 'SVC_TGT_CD'
const NUMBER_CHANGE_CODE = 'O11'
const DATA_SHARING_CODE = 'R15'

const isServiceSelectCompleted = computed(() => model.value.serviceSelectCompleteYn === 'Y')
const isServiceCheckCompleted = computed(
  () => model.value.serviceCheckYn === 'Y' || isServiceSelectCompleted.value,
)
const isServiceCheckButtonDisabled = computed(
  () =>
    isServiceSelectCompleted.value ||
    (!isServiceCheckCompleted.value && !(model.value.serviceSelect || []).length),
)
const serviceCheckButtonLabel = computed(() => {
  if (isServiceSelectCompleted.value) return '서비스 선택완료'
  if (isServiceCheckCompleted.value) return '서비스 체크완료'
  return '서비스 체크'
})

const applyAgencyMeta = (agencyValue) => {
  const selected = agencyOptions.value.find((v) => v.value === agencyValue)
  if (!selected) return

  model.value.managerCd = selected.ktOrgId || selected.value
  model.value.managerNm = selected.label
  model.value.agentCd = selected.orgnId || selected.value
  model.value.agentNm = selected.label
  model.value.cpntId = selected.ktOrgId || selected.orgnId || selected.value
  model.value.cpntNm = selected.label
  model.value.cntpntShopCd = selected.orgnId || selected.value
  model.value.cntpntShopNm = selected.label
}

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
      return '번호변경은 평일 오전10시~오후8시만 선택 가능합니다.'
    }
  }

  if (code === DATA_SHARING_CODE && !isInTimeRange(now, 8 * 60, 21 * 60 + 50)) {
    return '데이터쉐어링은 오전8시~오후9시50분만 선택 가능합니다.'
  }

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

const getConcurrentServiceValues = () =>
  (model.value.serviceList || [])
    .filter((item) => !item.notConcurrentChange)
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
  const selectedItems = serviceList.filter((item) => selected.includes(item.value))
  const hasNotConcurrentSelected = selectedItems.some((item) => item.notConcurrentChange)
  const hasAnySelected = selected.length > 0

  model.value.serviceList = serviceList.map((item) => {
    if (selected.includes(item.value)) return { ...item, disabled: false }
    const disabled =
      item.businessTimeDisabled ||
      hasNotConcurrentSelected ||
      (hasAnySelected && item.notConcurrentChange)
    return { ...item, disabled }
  })
}

const getSelectedServiceValidationMessage = (selectedValues = []) => {
  const selected = Array.isArray(selectedValues) ? selectedValues : []
  const serviceList = model.value.serviceList || []
  const selectedItems = serviceList.filter((item) => selected.includes(item.value))
  const timeRestrictedItem = selectedItems.find((item) => item.disabledReason)

  if (timeRestrictedItem) {
    return timeRestrictedItem.disabledReason
  }

  if (selectedItems.some((item) => item.notConcurrentChange) && selectedItems.length > 1) {
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

    model.value.serviceSelect = (model.value.serviceSelect || []).filter((value) =>
      availableValues.includes(value),
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

// 대리점 목록 조회
const fetchAgencies = async () => {
  try {
    const res = await post('/api/form/agent/list', { shopOrgnId: 'V000001083' })
    const data = res.data || res
    const list = Array.isArray(data) ? data : (data && typeof data === 'object' ? [data] : [])

    agencyOptions.value = list.map((item) => ({
      label: item.orgnNm || item.cntpntNm || '대리점명 없음',
      value: item.ktOrgId || item.shopOrgnId || item.orgnId || '',
      ktOrgId: item.ktOrgId || '',
      orgnId: item.orgnId || item.shopOrgnId || '',
    }))

    // 결과가 1개뿐이거나, 현재 선택된 값이 없으면 첫 번째 항목 자동 선택
    if (agencyOptions.value.length > 0) {
      if (!model.value.agency || agencyOptions.value.length === 1) {
        const first = agencyOptions.value[0]
        model.value.agency = first.value
        applyAgencyMeta(first.value)
      } else {
        applyAgencyMeta(model.value.agency)
      }
    }
  } catch (error) {
    console.error('Failed to fetch agencies:', error)
    showAlert('대리점 목록을 불러오지 못했습니다. 다시 시도해 주세요.')
  }
}

watch(
  () => model.value?.agency,
  (agencyValue) => {
    if (!agencyValue) return
    applyAgencyMeta(agencyValue)
  },
)

async function update(value) {
  if (isServiceCheckCompleted.value) return

  const previousSelected = [...lastValidServiceSelect.value]
  const selected = Array.isArray(value) ? value : []
  const validationMessage = getSelectedServiceValidationMessage(selected)

  console.log('[변경][서비스선택] 선택 변경', {
    selected,
    validationMessage,
    before: summarizeServiceList(),
  })

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
  model.value.serviceCheckYn = 'N'
  model.value.serviceChecked = false
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
  if (isServiceCheckCompleted.value) return

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
  model.value.serviceCheckYn = 'N'
  model.value.serviceChecked = false
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

const checkServiceSelection = async () => {
  const selected = Array.isArray(model.value.serviceSelect) ? model.value.serviceSelect : []

  if (isServiceCheckCompleted.value && !isServiceSelectCompleted.value) {
    console.log('[변경][서비스체크] 체크 완료 해제', {
      selected,
      allCheck: model.value.allCheck,
      serviceCheckYn: model.value.serviceCheckYn,
    })
    model.value.serviceCheckYn = 'N'
    model.value.serviceChecked = false
    model.value.serviceSelectCompleteYn = 'N'
    model.value.serviceSelectCompleted = false
    serviceChipKey.value += 1
    await nextTick()
    return
  }

  console.log('[변경][서비스체크] 요청 시작', {
    selected,
    allCheck: model.value.allCheck,
    serviceCheckYn: model.value.serviceCheckYn,
  })

  if (selected.length === 0) {
    console.warn('[변경][서비스체크] 진행 중단', {
      reason: 'service not selected',
    })
    showAlert('서비스를 선택해 주세요.')
    return
  }

  const validationMessage = getSelectedServiceValidationMessage(selected)
  if (validationMessage) {
    console.warn('[변경][서비스체크] 진행 중단', {
      reason: 'validation failed',
      selected,
      message: validationMessage,
    })
    showAlert(validationMessage)
    return
  }

  model.value.serviceCheckYn = 'Y'
  model.value.serviceChecked = true
  serviceChipKey.value += 1
  await nextTick()

  console.log('[변경][서비스체크] 화면 데이터 반영 결과', {
    selected: model.value.serviceSelect,
    allCheck: model.value.allCheck,
    serviceCheckYn: model.value.serviceCheckYn,
  })
}

onMounted(() => {
  fetchServiceTargetCodes()
  fetchAgencies() // 대리점 조회
})
</script>
<template>
  <!-- 서비스 변경 선택 -->
  <MsfTitleArea title="서비스 변경 선택" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="서비스 선택" tag="div" required>
      <MsfStack vertical>
        <MsfCheckbox
          v-model="model.allCheck"
          label="전체 선택"
          :returndata="{ true: 'Y', false: 'N' }"
          :disabled="isServiceCheckCompleted"
          @change="updateAllCheck"
        />
        <MsfChip
          :key="serviceChipKey"
          v-model="model.serviceSelect"
          name="inp-serviceSelect"
          :data="model.serviceList"
          :readonly="isServiceCheckCompleted"
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
          <li>번호변경 : 평일 오전10시~오후8시만 가능 (해당 시간대 이외 및 주말/공휴일 선택 불가)</li>
          <li>데이터쉐어링 : 오전 08시~오후9:50까지 가능 (해당 시간대 이외 선택 불가)</li>
        </MsfTextList>
      </MsfStack>
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
    </MsfFormGroup>
    <MsfFormGroup label="대리점" tag="div" required>
      <MsfSelect
        id="inp-agency"
        title="대리점 선택"
        v-model="model.agency"
        :options="agencyOptions"
        class="ut-w-300"
        placeholder="대리점 선택"
      />
    </MsfFormGroup>
  </MsfStack>
  <!-- // 서비스 변경 선택 -->
</template>
