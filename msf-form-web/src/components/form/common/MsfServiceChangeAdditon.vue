<script setup>
import { computed, defineModel, onMounted, ref, watch } from 'vue'
import { post } from '@/libs/api/msf.api'

const model = defineModel({ type: Object, required: true })

const activeFreeServices = ref([])
const activePaidServices = ref([])
const selectedServiceIds = ref([])

const toNumber = (value) => Number(String(value || 0).replace(/,/g, '')) || 0

const getServiceKey = (svc = {}, index = 0) =>
  String(svc.rateCd || svc.soc || svc.prodId || svc.addSvcCd || `service-${index}`)

const getServiceName = (svc = {}) =>
  svc.rateNm || svc.socDescription || svc.prodNm || svc.addSvcNm || svc.serviceName || '-'

const getServiceAmount = (svc = {}) =>
  svc.baseAmt ?? svc.socRateVatValue ?? svc.socRateVat ?? svc.socRateValue ?? 0

const getServiceAmountLabel = (svc = {}) => {
  const amount = toNumber(getServiceAmount(svc))
  const unit = svc.chargeUnit || svc.rateUnit || ''

  if (amount === 0) return '무료'
  return `${amount.toLocaleString()} 원${unit ? `/${unit}` : ''}`
}

const toServiceRow = (svc = {}, index = 0) => {
  const baseAmt = getServiceAmount(svc)

  return {
    ...svc,
    rateCd: getServiceKey(svc, index),
    rateNm: getServiceName(svc),
    baseAmt,
    settingYn: svc.settingYn || 'N',
  }
}

const splitActiveServices = (list = []) => {
  const services = list.map(toServiceRow)

  return {
    freeAddition: services.filter((svc) => toNumber(svc.baseAmt) === 0),
    paidAddition: services.filter((svc) => toNumber(svc.baseAmt) !== 0),
  }
}

const allActiveServices = computed(() => [...activeFreeServices.value, ...activePaidServices.value])

const selectedPaidServices = computed(() =>
  allActiveServices.value.filter(
    (svc) => selectedServiceIds.value.includes(svc.rateCd) && toNumber(svc.baseAmt) !== 0,
  ),
)

const selectedTotalAmount = computed(() =>
  selectedPaidServices.value.reduce((acc, cur) => acc + toNumber(cur.baseAmt), 0),
)

const summarizeService = (svc = {}) => ({
  rateCd: svc.rateCd,
  rateNm: svc.rateNm,
  baseAmt: svc.baseAmt,
  settingYn: svc.settingYn,
})

const getLogPrefix = (task) => `[서비스변경][부가서비스신청변경][${task}]`

const syncSelectedServices = () => {
  const serviceIds = allActiveServices.value.map((svc, index) => getServiceKey(svc, index))
  selectedServiceIds.value = serviceIds
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
    activeFreeServices.value = []
    activePaidServices.value = []
    selectedServiceIds.value = []
    return
  }

  try {
    console.log(`${getLogPrefix('이용중부가서비스조회')} 요청 시작`, payload)
    const res = await post('/api/form/servicechange/myaddsvclist', payload)
    console.log(`${getLogPrefix('이용중부가서비스조회')} 응답 수신`, {
      code: res?.code,
      message: res?.message,
      data: res?.data,
    })

    const formResponse = Array.isArray(res?.data) ? res.data[0] : res?.data
    const result = formResponse?.resData

    if (res && res.code === '0000' && formResponse?.resCode === '0000' && result) {
      const normalized = result?.list ? splitActiveServices(result.list) : result
      activeFreeServices.value = (normalized?.freeAddition || []).map(toServiceRow)
      activePaidServices.value = (normalized?.paidAddition || []).map(toServiceRow)
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
      activeFreeServices.value = []
      activePaidServices.value = []
      selectedServiceIds.value = []
    }
  } catch (error) {
    console.error(`${getLogPrefix('이용중부가서비스조회')} 예외 발생`, {
      message: error?.message,
      response: error?.response?.data,
    })
    activeFreeServices.value = []
    activePaidServices.value = []
    selectedServiceIds.value = []
  }
}

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
    console.log(`${getLogPrefix('가입자정보변경')} 감지`, {
      oldValue,
      newValue,
    })
    fetchActiveServices()
  },
)

watch(
  () => selectedServiceIds.value,
  (newValue, oldValue) => {
    console.log(`${getLogPrefix('선택변경')} 화면 데이터 반영 결과`, {
      oldValue,
      newValue,
      selectedTotalAmount: selectedTotalAmount.value,
    })
  },
)

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
        <tr v-for="svc in allActiveServices" :key="svc.rateCd">
          <td class="ut-text-center">
            <MsfCheckbox
              :id="`inp-addition-${svc.rateCd}`"
              v-model="selectedServiceIds"
              :value="svc.rateCd"
              :label="svc.rateNm"
              hideLabel
            />
          </td>
          <td>
            <label :for="`inp-addition-${svc.rateCd}`">{{ svc.rateNm }}</label>
          </td>
          <td class="ut-text-center">{{ getServiceAmountLabel(svc) }}</td>
          <td class="ut-text-center">
            <MsfButton variant="subtle" v-if="svc.settingYn === 'Y'">설정</MsfButton>
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
      <MsfButton variant="subtle">부가서비스 추가</MsfButton>
      <MsfButton variant="toggle" :disabled="selectedServiceIds.length === 0">확인</MsfButton>
      <MsfButton variant="toggle" active>확인 완료</MsfButton>
    </MsfButtonGroup>
  </MsfBox>
  <!-- // 합계박스 -->
  <!-- // 부가서비스 신청/변경 -->
</template>
