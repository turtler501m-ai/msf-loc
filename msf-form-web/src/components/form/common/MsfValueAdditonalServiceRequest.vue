<script setup>
import { ref, onMounted, defineModel } from 'vue'
import { post } from '@/libs/api/msf.api'

const model = defineModel({ type: Object, required: true })

const freeVasOptions = ref([])
const paidVasOptions = ref([])

const fetchVasList = async () => {
  try {
    // 유료 부가서비스 조회 (RRATESVC)
    const resPaid = await post('/api/form/addition/list', { prodCtgId: 'RRATESVC' })
    const paidList = resPaid.data?.[0]?.paidAddition || []
    paidVasOptions.value = paidList.map((v) => ({
      label: `${v.rateNm} (${Number(v.baseAmt).toLocaleString()}원)`,
      value: v.rateCd,
    }))

    // 무료 부가서비스 조회 (RFREESVC)
    const resFree = await post('/api/form/addition/list', { prodCtgId: 'RFREESVC' })
    const freeList = resFree.data?.[0]?.freeAddition || []
    freeVasOptions.value = freeList.map((v) => ({
      label: v.rateNm,
      value: v.rateCd,
    }))

    // 무료 부가서비스 목록의 모든 값을 초기값으로 설정 (전체 선택 상태)
    if (!model.value.reqAdditionListNm || model.value.reqAdditionListNm.length === 0) {
      model.value.reqAdditionListNm = freeVasOptions.value.map((v) => v.value)
    }
  } catch (error) {
    console.error('부가서비스 조회 실패:', error)
  }
}

onMounted(() => {
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
