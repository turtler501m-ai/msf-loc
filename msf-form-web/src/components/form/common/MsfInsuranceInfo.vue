<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="안심 보험 가입" tag="div">
        <MsfChip
          v-model="model.clauseInsuranceYn"
          name="inp-isInsured"
          :data="[
            { value: 'isInsured1', label: '가입' },
            { value: 'isInsured2', label: '미가입' },
          ]"
        />
        <MsfStack type="field" class="ut-w100p" v-if="model.clauseInsuranceYn === 'isInsured1'">
          <MsfSelect
            title="추천 카테고리"
            v-model="model.recCat1"
            :options="categoryOptions"
            placeholder="추천 카테고리"
            class="ut-w-300"
          />
          <MsfSelect
            title="보험 상품 선택"
            v-model="model.recCat2"
            :options="insuranceOptions"
            placeholder="보험 상품 선택"
            class="ut-flex-1"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps, ref, watch } from 'vue'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  title: { type: String, default: '안심 보험' },
})
const model = defineModel({ type: Object, required: true })

const categoryOptions = ref([])
const insuranceOptions = ref([])

// 1. 보험 카테고리 조회 (요금제 카테고리 조회와 유사, 'R' 대신 'I' 사용)
const fetchInsuranceCategories = async () => {
  try {
    const res = await post('/api/form/rate/category/list', {
      searchProductCategoryTypeCd: 'I',
    })
    const data = res.data || []
    categoryOptions.value = data.map((item) => ({
      label: item.ctgNm,
      value: item.ctgCd,
    }))
  } catch (error) {
    console.error('보험 카테고리 조회 실패:', error)
  }
}

// 2. 보험 상품 목록 조회 (요금제 목록 조회와 유사, 'R' 대신 'I' 사용)
const fetchInsurances = async (ctgCd) => {
  try {
    const res = await post('/api/form/rate/list', {
      searchProductCategoryTypeCd: 'I',
      ctgCd: ctgCd,
      orgnId: '1100033726',
      sprtTp: 'KD',
      plcySctnCd: '01',
      salePlcyCd: 'N2022011018381',
    })
    const data = res.data || []
    insuranceOptions.value = data.map((item) => ({
      label: item.rateNm || item.ctgNm,
      value: item.rateCd || item.ctgCd || item.prodId,
    }))
  } catch (error) {
    console.error('보험 상품 조회 실패:', error)
  }
}

// 가입 여부 변경 감시
watch(
  () => model.value.clauseInsuranceYn,
  (newVal) => {
    if (newVal === 'isInsured1') {
      fetchInsuranceCategories()
    } else {
      model.value.recCat1 = ''
      model.value.recCat2 = ''
      categoryOptions.value = []
      insuranceOptions.value = []
    }
  },
)

// 카테고리 변경 감시
watch(
  () => model.value.recCat1,
  (newVal) => {
    if (newVal) {
      fetchInsurances(newVal)
    } else {
      model.value.recCat2 = ''
      insuranceOptions.value = []
    }
  },
)

const validate = () => {
  if (model.value.clauseInsuranceYn === 'isInsured1') {
    if (!model.value.recCat1 || !model.value.recCat2) return false
  }
  return true
}

defineExpose({ validate })
</script>
