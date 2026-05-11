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
        <MsfCheckboxGroup
          v-model="model.addtionId"
          :options="paidVasOptions"
        />
      </MsfFormGroup>
    </MsfStack>

    <!-- 부가서비스 관리 모달 -->
    <MsfVasManageModal v-model="isModalOpen" @confirm="onVasConfirm" />
  </div>
</template>
<script setup>
import { ref, onMounted, defineModel, defineProps, watch } from 'vue'
import MsfVasManageModal from './popups/MsfVasManageModal.vue'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  title: { type: String, default: '부가서비스 신청' },
})
const model = defineModel({ type: Object, required: true })

const isModalOpen = ref(false)
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
  // TODO: 모달에서 선택된 데이터를 기반으로 model 업데이트
}

const validate = () => {
  return true
}

defineExpose({ validate })
</script>
