<script setup>
import { post } from '@/libs/api/msf.api'
import { defineModel, onMounted, ref, watch } from 'vue'
import MsfRealtimeChargeInfoModal from './popups/MsfRealtimeChargeInfoModal.vue'

const model = defineModel({ type: Object, required: true })

const planCategoryOptions = ref([])
const planOptions = ref([])
const agencyOptions = ref([])
const isRealtimeChargeInfoModalOpen = ref(false)

// 요금제 카테고리 조회
const fetchPlanCategories = async () => {
  try {
    const res = await post('/api/form/rate/category/list', {
      searchProductCategoryTypeCd: 'P',
    })

    planCategoryOptions.value = res?.data?.map((item) => ({
      label: item.ctgNm,
      value: item.ctgCd,
    }))
  } catch (e) {
    console.error('Failed to fetch plan categories:', e)
  }
}

// 요금제 목록 조회 (카테고리 선택 시)
const fetchPlans = async (ctgCd) => {
  try {
    const res = await post('/api/form/rate/list', {
      orgnId: '1100033726',
      sprtTp: 'KD',
      plcySctnCd: '01',
      salePlcyCd: 'N2022011018381',
    })
    planOptions.value = res?.data?.map((item) => ({
      label: item.rateNm || item.ctgNm,
      value: item.rateCd || item.ctgCd || item.prodId,
    }))
  } catch (e) {
    console.error('Failed to fetch plans:', e)
  }
}

// 요금제 카테고리가 변경될 때 하위 요금제 목록 다시 불러오기
watch(
  () => model.value.planName1,
  (newVal) => {
    if (!model.value.isSaved) {
      model.value.planName2 = ''
    }
    fetchPlans(newVal)
  },
)

onMounted(() => {
  fetchPlanCategories()
  fetchPlans(model.value.planName1) // 초기 로드
})
</script>
<template>
  <!-- 요금제 변경 -->
  <MsfTitleArea title="요금제 변경" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="요금제" tag="div" required>
      <MsfSelect
        title="요금제"
        v-model="model.planName1"
        :options="planCategoryOptions"
        class="ut-w100p"
        placeholder="추천 요금제"
      />
      <MsfSelect title="요금제" v-model="model.planName2" :options="planOptions" class="ut-w100p" />
    </MsfFormGroup>
    <MsfFormGroup label="변경일시" tag="div" required>
      <MsfChip
        v-model="model.changeDate"
        name="inp-changeDate"
        :data="[
          { value: 'changeDate1', label: '예약(익월1일)' },
          { value: 'changeDate2', label: '즉시변경' },
        ]"
      >
        <template #endSlot>
          <MsfButton variant="toggle" @click="isRealtimeChargeInfoModalOpen = true">확인</MsfButton>
        </template>
      </MsfChip>
    </MsfFormGroup>
  </MsfStack>
  <!-- // 요금제 변경 -->
  <MsfRealtimeChargeInfoModal v-model="isRealtimeChargeInfoModalOpen" />
</template>
