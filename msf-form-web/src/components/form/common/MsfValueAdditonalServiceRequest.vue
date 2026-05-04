<script setup>
import { ref, onMounted, defineModel, watch } from 'vue'
import { post } from '@/libs/api/msf.api'

const model = defineModel({ type: Object, required: true })

const freeVasOptions = ref([])
const paidVasOptions = ref([])

// 통합 저장용 배열 생성 로직
watch(
  () => [model.value.reqAdditionListNm, model.value.addtionId],
  ([free, paid]) => {
    const combined = [
      ...(free || []).map((id) => ({ prodId: id })),
      ...(paid || []).map((id) => ({ prodId: id })),
    ]
    model.value.reqAdditionList = combined
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

    const res = await post('/api/form/addition/list', payload)
    if (res && res.code === '0000' && res.data?.[0]) {
      const result = res.data[0]
      
      // 무료 부가서비스
      const freeList = result.freeAddition || []
      freeVasOptions.value = freeList.map((v) => ({
        label: v.rateNm,
        value: v.rateCd,
      }))

      // 유료 부가서비스
      const paidList = result.paidAddition || []
      paidVasOptions.value = paidList.map((v) => ({
        label: `${v.rateNm} (${Number(v.baseAmt || 0).toLocaleString()}원)`,
        value: v.rateCd,
      }))

      // 무료 부가서비스 목록의 모든 값을 선택 상태로 설정 (항상 전체 선택)
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
