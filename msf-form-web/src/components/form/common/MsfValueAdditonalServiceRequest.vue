<script setup>
import { ref, onMounted, defineModel, watch } from 'vue'
import { post } from '@/libs/api/msf.api'

const model = defineModel({ type: Object, required: true })

const freeVasOptions = ref([])
const paidVasOptions = ref([])

// 통합 저장용 배열 생성 로직
watch(
  () => [model.value.reqAdditionListNm, model.value.addtionId, freeVasOptions.value, paidVasOptions.value],
  ([free, paid, freeOpts, paidOpts]) => {
    const combined = [
      ...(free || []).map((id) => {
        const opt = freeOpts.find((o) => o.value === id)
        return {
          additionId: id,
          additionNm: opt ? opt.additionNm : '',
          rantal: opt ? Number(opt.rantal || 0) : 0,
        }
      }),
      ...(paid || []).map((id) => {
        const opt = paidOpts.find((o) => o.value === id)
        return {
          additionId: id,
          additionNm: opt ? opt.additionNm : '',
          rantal: opt ? Number(opt.rantal || 0) : 0,
        }
      }),
    ]
    model.value.additionList = combined
    console.log('[MsfValueAdditonalServiceRequest] 부가서비스 선택값 변경', {
      free,
      paid,
      combined,
    })
  },
  { deep: true, immediate: true },
)

const fetchVasList = async () => {
  try {
    const payload = {
      operTypeCd: '',
      prodCtgTypeCd: 'R',
      categoryMstRequest: {
        prodCtgId: ['RFREESVC', 'RRATESVC'],
      },
    }

    console.log('[MsfValueAdditonalServiceRequest] 가입가능 부가서비스 조회 요청', payload)
    const res = await post('/api/form/addition/list', payload)
    console.log('[MsfValueAdditonalServiceRequest] 가입가능 부가서비스 조회 응답', res)

    if (res && res.code === '0000' && res.data?.[0]) {
      const result = res.data[0]

      // 무료 부가서비스
      const freeList = result.freeAddition || []
      freeVasOptions.value = freeList.map((v) => ({
        label: v.rateNm,
        value: v.rateCd,
        additionNm: v.rateNm,
        rantal: v.baseAmt,
      }))

      // 유료 부가서비스
      const paidList = result.paidAddition || []
      paidVasOptions.value = paidList.map((v) => ({
        label: `${v.rateNm} (${Number(v.baseAmt || 0).toLocaleString()}원)`,
        value: v.rateCd,
        additionNm: v.rateNm,
        rantal: v.baseAmt,
      }))

      // 무료 부가서비스 목록의 모든 값을 선택 상태로 설정 (항상 전체 선택)
      model.value.reqAdditionListNm = freeVasOptions.value.map((v) => v.value)
      console.log('[MsfValueAdditonalServiceRequest] 가입가능 부가서비스 화면 반영', {
        freeCount: freeVasOptions.value.length,
        paidCount: paidVasOptions.value.length,
        freeOptions: freeVasOptions.value,
        paidOptions: paidVasOptions.value,
        selectedFree: model.value.reqAdditionListNm,
      })
    } else {
      console.log('[MsfValueAdditonalServiceRequest] 가입가능 부가서비스 응답 데이터 없음', res)
    }
  } catch (error) {
    console.error('[MsfValueAdditonalServiceRequest] 가입가능 부가서비스 조회 실패', error)
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
