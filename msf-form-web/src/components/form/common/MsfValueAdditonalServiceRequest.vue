<script setup>
import { ref, onMounted, defineModel, watch } from 'vue'
import { post } from '@/libs/api/msf.api'

const model = defineModel({ type: Object, required: true })

const freeVasOptions = ref([])
const paidVasOptions = ref([])

const activeFreeServices = ref([])
const activePaidServices = ref([])
const dailyAdditions = ref([]) // 일 단위 부가서비스 정보 저장

/**
 * 부가서비스 명칭에 사용 기간 추가 (dailyAddition 정보가 있는 경우)
 */
const formatVasLabel = (name, code, isPaid = false, price = 0) => {
  const daily = dailyAdditions.value.find((d) => (d.RATE_CD || d.rateCd) === code)
  const suffix = daily ? ` / ${daily.USE_PRD || daily.usePrd}(일)` : ''

  if (isPaid) {
    return `${name}${suffix} (${Number(price || 0).toLocaleString()}원)`
  }
  return `${name}${suffix}`
}

const fetchActiveServices = async () => {
  // 가입자 정보 (기기변경 시 기존 번호가 있을 수 있음)
  const phoneNo = `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`
  const customerLinkName = (model.value.cstmrNm || '').trim()

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

      // 일 단위 정보 누적
      if (result.dailyAddition) {
        dailyAdditions.value = [...dailyAdditions.value, ...result.dailyAddition]
      }
    }
  } catch (error) {
    console.error('[MsfValueAdditonalServiceRequest] 이용중 부가서비스 조회 실패', error)
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
    // 이용 중인 서비스 ID 목록
    const activeIds = [
      ...activeFreeServices.value.map((s) => s.rateCd),
      ...activePaidServices.value.map((s) => s.rateCd),
    ]

    // 선택된 ID들과 이용 중인 ID들을 합침 (중복 제거)
    const allFreeIds = [...new Set([...(free || []), ...activeFreeServices.value.map((s) => s.rateCd)])]
    const allPaidIds = [...new Set([...(paid || []), ...activePaidServices.value.map((s) => s.rateCd)])]

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
    // 이용중 부가서비스 먼저 조회
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

      // 일 단위 정보 업데이트
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
        label: formatVasLabel(v.rateNm, v.rateCd),
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
          label: formatVasLabel(v.rateNm, v.rateCd, true, v.baseAmt),
          value: v.rateCd,
          additionNm: v.rateNm,
          rantal: v.baseAmt,
          disabled: isActive, // 이미 가입된 서비스는 체크 해제 불가
          additionKey: v.additionKey,
        }
      })

      // 선택 상태 업데이트
      const currentFree = model.value.reqAdditionListNm || []
      const activeFreeIds = activeFreeServices.value.map((s) => s.rateCd)
      model.value.reqAdditionListNm = [...new Set([...currentFree, ...activeFreeIds])]

      const currentPaid = model.value.addtionId || []
      const activePaidIds = activePaidServices.value.map((s) => s.rateCd)
      model.value.addtionId = [...new Set([...currentPaid, ...activePaidIds])]
    }
  } catch (error) {
    console.error('[MsfValueAdditonalServiceRequest] 부가서비스 조회 실패', error)
  }
}

onMounted(() => {
  console.log('[MsfValueAdditonalServiceRequest] mounted')
  fetchVasList()
})
</script>

<template>
  <!-- 부가서비스 신청 -->
  <MsfTitleArea title="부가서비스 신청" />
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
  <!-- // 부가서비스 신청 -->
</template>

<style scoped lang="scss"></style>
