<template>
  <div>
    <MsfTitleArea :title="title">
      <template #extra>
        <MsfButton variant="subtle" size="small" @click="isModalOpen = true"
          >부가서비스 추가/삭제</MsfButton
        >
      </template>
    </MsfTitleArea>
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="무료부가서비스" tag="div">
        <MsfChip
          v-model="model.reqAdditionListNm"
          name="inp-freeVas"
          :data="freeVasOptions"
          multiple
          readonly
        />
      </MsfFormGroup>
      <MsfFormGroup label="유료부가서비스" tag="div">
        <MsfCheckboxGroup v-model="model.addtionId" :options="paidVasOptions" />
      </MsfFormGroup>
    </MsfStack>

    <!-- 부가서비스 관리 모달 -->
    <MsfVasManageModal
      v-model="isModalOpen"
      :free-services="currentFreeServices"
      :paid-services="currentPaidServices"
      :active-free-ids="model.reqAdditionListNm"
      :active-paid-ids="model.addtionId"
      @confirm="onVasConfirm"
    />
  </div>
</template>
<script setup>
import { ref, onMounted, defineModel, defineProps, watch, computed } from 'vue'
import MsfVasManageModal from './popups/MsfVasManageModal.vue'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  title: { type: String, default: '부가서비스 신청' },
  customerData: { type: Object, default: () => ({}) },
})
const model = defineModel({ type: Object, required: true })

const isModalOpen = ref(false)
const freeVasOptions = ref([])
const paidVasOptions = ref([])

const activeFreeServices = ref([])
const activePaidServices = ref([])
const dailyAdditions = ref([])

const formatServiceLabel = (rateNm, rateCd, defaultAmt) => {
  const daily = dailyAdditions.value.find(
    (d) =>
      String(d.RATE_CD || d.rateCd || d.soc || d.prodId || d.addSvcCd || '') === String(rateCd),
  )

  if (daily) {
    const amt = Number(
      daily.baseAmt || daily.rantal || daily.BASE_AMT || daily.amt || defaultAmt || 0,
    )
    const rawPrd = daily.USE_PRD || daily.usePrd || daily.usePeriod || daily.period || ''
    let prdText = '월'
    if (rawPrd) {
      const s = String(rawPrd)
      if (s.includes('일') || s.includes('월')) {
        prdText = s
      } else if (/^\d+$/.test(s)) {
        prdText = `${s}일`
      } else {
        prdText = s
      }
    }
    return `${rateNm} (${amt.toLocaleString()}원/${prdText})`
  }

  if (defaultAmt !== undefined && Number(defaultAmt) > 0) {
    return `${rateNm} (${Number(defaultAmt).toLocaleString()}원)`
  }
  return rateNm
}

const currentFreeServices = computed(() => {
  return (model.value.additionList || [])
    .filter((s) => {
      const isFreeOpt = freeVasOptions.value.some((o) => o.value === s.additionId)
      return isFreeOpt || Number(s.rantal || 0) === 0
    })
    .map((s) => ({
      rateCd: s.additionId,
      rateNm: s.additionNm,
      baseAmt: s.rantal,
      additionKey: s.additionKey,
    }))
})

const currentPaidServices = computed(() => {
  return (model.value.additionList || [])
    .filter((s) => {
      const isPaidOpt = paidVasOptions.value.some((o) => o.value === s.additionId)
      return isPaidOpt && Number(s.rantal || 0) > 0
    })
    .map((s) => ({
      rateCd: s.additionId,
      rateNm: s.additionNm,
      baseAmt: s.rantal,
      additionKey: s.additionKey,
    }))
})

const fetchActiveServices = async () => {
  const customer = props.customerData || {}
  const isDeviceChange = customer.joinType === 'HDN3' || customer.joinType === 'HCN3'
  if (!isDeviceChange) return

  const phoneNo = `${customer.deviceChgTel1 || ''}${customer.deviceChgTel2 || ''}${customer.deviceChgTel3 || ''}`
  const customerLinkName = (customer.cstmrNm || '').trim()

  if (phoneNo.length < 10 || !customerLinkName) return

  const payload = {
    subscriberNo: phoneNo,
    customerLinkName: customerLinkName,
  }

  try {
    const res = await post('/api/form/activeaddition/list', payload)
    if (res && res.code === '0000' && res.data?.[0]) {
      const result = res.data[0]
      activeFreeServices.value = result.freeAddition || []
      activePaidServices.value = result.paidAddition || []

      if (result.dailyAddition) {
        dailyAdditions.value = [...dailyAdditions.value, ...result.dailyAddition]
      }
    }
  } catch (error) {
    console.error('이용중 부가서비스 조회 실패:', error)
  }
}

// 통합 저장용 배열 생성 로직
watch(
  () => [
    model.value.reqAdditionListNm,
    model.value.addtionId,
    freeVasOptions.value,
    paidVasOptions.value,
  ],
  ([free, paid, freeOpts, paidOpts]) => {
    const allFreeIds = [
      ...new Set([...(free || []), ...activeFreeServices.value.map((s) => s.rateCd)]),
    ]
    const allPaidIds = [
      ...new Set([...(paid || []), ...activePaidServices.value.map((s) => s.rateCd)]),
    ]

    const combined = [
      ...allFreeIds.map((id) => {
        const opt = freeOpts.find((o) => o.value === id)
        const active = activeFreeServices.value.find((s) => s.rateCd === id)
        return {
          additionId: id,
          additionNm: opt ? opt.additionNm : active ? active.rateNm : '',
          rantal: opt ? Number(opt.rantal || 0) : active ? Number(active.baseAmt || 0) : 0,
          additionKey: opt ? opt.additionKey : active ? active.additionKey : '',
        }
      }),
      ...allPaidIds.map((id) => {
        const opt = paidOpts.find((o) => o.value === id)
        const active = activePaidServices.value.find((s) => s.rateCd === id)
        return {
          additionId: id,
          additionNm: opt ? opt.additionNm : active ? active.rateNm : '',
          rantal: opt ? Number(opt.rantal || 0) : active ? Number(active.baseAmt || 0) : 0,
          additionKey: opt ? opt.additionKey : active ? active.additionKey : '',
        }
      }),
    ]
    model.value.additionList = combined
  },
  { deep: true, immediate: true },
)

const fetchVasList = async () => {
  try {
    await fetchActiveServices()

    const payload = {
      operTypeCd: '',
      prodCtgTypeCd: 'R',
      categoryMstRequest: {
        prodCtgId: ['RFREESVC', 'RRATESVC'],
      },
    }

    const res = await post('/api/form/addition/list', payload)
    if (res && res.code === '0000' && res.data?.[0]) {
      const result = res.data[0]

      if (result.dailyAddition) {
        dailyAdditions.value = [...dailyAdditions.value, ...result.dailyAddition]
      }

      // 무료 부가서비스 병합
      const freeList = result.freeAddition || []
      const mergedFree = [...freeList]
      activeFreeServices.value.forEach((active) => {
        if (!mergedFree.some((f) => f.rateCd === active.rateCd)) {
          mergedFree.push(active)
        }
      })

      freeVasOptions.value = mergedFree.map((v) => ({
        label: formatServiceLabel(v.rateNm, v.rateCd, v.baseAmt),
        value: v.rateCd,
        additionNm: v.rateNm,
        rantal: v.baseAmt,
        additionKey: v.additionKey,
      }))

      // 유료 부가서비스 병합
      const paidList = result.paidAddition || []
      const mergedPaid = [...paidList]
      activePaidServices.value.forEach((active) => {
        if (!mergedPaid.some((p) => p.rateCd === active.rateCd)) {
          mergedPaid.push(active)
        }
      })

      paidVasOptions.value = mergedPaid.map((v) => {
        const isActive = activePaidServices.value.some((s) => s.rateCd === v.rateCd)
        return {
          label: formatServiceLabel(v.rateNm, v.rateCd, v.baseAmt),
          value: v.rateCd,
          additionNm: v.rateNm,
          rantal: v.baseAmt,
          disabled: isActive,
          additionKey: v.additionKey,
        }
      })

      // 선택 상태 업데이트
      const activeFreeIds = activeFreeServices.value.map((s) => s.rateCd)
      model.value.reqAdditionListNm = [
        ...new Set([...freeVasOptions.value.map((v) => v.value), ...activeFreeIds]),
      ]

      const currentPaid = model.value.addtionId || []
      const activePaidIds = activePaidServices.value.map((s) => s.rateCd)
      const finalPaidIds = [...new Set([...currentPaid, ...activePaidIds])]

      // 유료 부가서비스 목록(mergedPaid)에 'CHCALRCC1'이 존재하는 경우 추천식 기본 체크
      const isExistCHCALRCC1 = mergedPaid.some(
        (v) => String(v.rateCd || v.value || '') === 'CHCALRCC1',
      )
      if (isExistCHCALRCC1) {
        if (!finalPaidIds.includes('CHCALRCC1')) {
          finalPaidIds.push('CHCALRCC1')
        }
      }
      model.value.addtionId = finalPaidIds
    }
  } catch (error) {
    console.error('부가서비스 조회 실패:', error)
  }
}

onMounted(() => {
  fetchVasList()
})

const onVasConfirm = (data) => {
  console.log('선택된 부가서비스:', data)
  if (!data) return

  // 1. 부모창 옵션 목록 확장 (레이블 매핑용)
  if (data.freeServices && data.freeServices.length > 0) {
    data.freeServices.forEach((svc) => {
      if (!freeVasOptions.value.some((o) => o.value === svc.rateCd)) {
        freeVasOptions.value.push({
          label: formatServiceLabel(svc.rateNm, svc.rateCd, svc.baseAmt),
          value: svc.rateCd,
          additionNm: svc.rateNm,
          rantal: svc.baseAmt,
          additionKey: svc.additionKey,
        })
      }
    })
  }
  if (data.paidServices && data.paidServices.length > 0) {
    data.paidServices.forEach((svc) => {
      if (!paidVasOptions.value.some((o) => o.value === svc.rateCd)) {
        paidVasOptions.value.push({
          label: formatServiceLabel(svc.rateNm, svc.rateCd, svc.baseAmt),
          value: svc.rateCd,
          additionNm: svc.rateNm,
          rantal: svc.baseAmt,
          additionKey: svc.additionKey,
        })
      }
    })
  }

  // 2. 모델 선택 ID 목록에 병합
  if (data.freeCodes) {
    model.value.reqAdditionListNm = [
      ...new Set([...(model.value.reqAdditionListNm || []), ...data.freeCodes]),
    ]
  }
  if (data.paidCodes) {
    model.value.addtionId = [...new Set([...(model.value.addtionId || []), ...data.paidCodes])]
  }
}

const validate = () => {
  return true
}

const checkValidation = () => {
  return true
}

defineExpose({ validate, checkValidation })
</script>
