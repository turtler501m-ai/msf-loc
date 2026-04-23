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
import { ref, defineModel, defineProps, onMounted } from 'vue'
import MsfVasManageModal from './popups/MsfVasManageModal.vue'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  title: { type: String, default: '부가서비스 신청' },
})
const model = defineModel({ type: Object, required: true })

const isModalOpen = ref(false)
const freeVasOptions = ref([])
const paidVasOptions = ref([])

const fetchVasList = async () => {
  try {
    const res = await post('/api/shared/form/common/terms/list', { groupCode: 'SVCCHG_RATE_CD' })
    const data = res.data || []

    // 무료 부가서비스 (RFREESVC)
    const freeObj = data.find((item) => item.additionCtgCd === 'RFREESVC')
    if (freeObj && freeObj.freeAddition) {
      freeVasOptions.value = freeObj.freeAddition.map((v) => ({
        label: v.rateNm,
        value: v.rateCd,
      }))
      // 기본적으로 모든 무료 부가서비스를 선택 상태로 설정 (필요 시)
      if (!model.value.reqAdditionListNm || model.value.reqAdditionListNm.length === 0) {
        model.value.reqAdditionListNm = freeVasOptions.value.map((v) => v.value)
      }
    }

    // 유료 부가서비스 (RRATESVC)
    const paidObj = data.find((item) => item.additionCtgCd === 'RRATESVC')
    if (paidObj && paidObj.paidAddition) {
      paidVasOptions.value = paidObj.paidAddition.map((v) => ({
        label: `${v.rateNm} ${v.baseAmt} 원`,
        value: v.rateCd,
      }))
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
