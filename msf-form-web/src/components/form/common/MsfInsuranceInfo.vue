<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="안심 보험 가입" tag="div">
        <MsfChip
          v-model="model.clauseInsuranceYn"
          name="inp-isInsured"
          :data="[
            { value: 'Y', label: '가입' },
            { value: 'N', label: '미가입' },
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
import { defineModel, defineProps, ref, watch, onMounted } from 'vue'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils.js'

const props = defineProps({
  title: { type: String, default: '안심 보험' },
})
const model = defineModel({ type: Object, required: true })

const categoryOptions = ref([])
const insuranceOptions = ref([])

// 1. 보험 카테고리 조회
const fetchInsuranceCategories = async () => {
  try {
    const res = await post('/api/form/rate/category/list', {
      prodCtgTypeCd: 'I',
    })
    const data = res.data || []
    categoryOptions.value = data.map((item) => ({
      label: item.prodCtgNm || item.ctgNm,
      value: item.prodCtgId || item.ctgCd,
    }))
  } catch (error) {
    console.error('보험 카테고리 조회 실패:', error)
  }
}

// 2. 보험 상품 목록 조회
const fetchInsurances = async (ctgId) => {
  if (!ctgId) return

  try {
    const payload = {
      intmInsrRelDTO: {
        reqBuyType: model.value.productType || 'MM', // MM(단말), UU(유심)
        rprsPrdtId: model.value.deviceModel || '', // 단말인 경우 모델 ID
      },
      prodCtgId: ctgId,
    }

    const res = await post('/api/form/product/selectInsrProdList', payload)
    const data = res.data || []
    insuranceOptions.value = data.map((item) => ({
      label: `${item.rateNm || item.prodNm} (${Number(item.baseAmt || 0).toLocaleString()}원)`,
      value: item.rateCd || item.prodId,
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

onMounted(async () => {
  // 휴대폰 안심보험 약관동의 공통코드 조회
  getCommonCodeList('ClauseInsur').then((list) => {
    console.log('>>> 휴대폰 안심보험 약관 (ClauseInsur):', list)
  })
})

const validate = () => {
  if (model.value.clauseInsuranceYn === 'isInsured1') {
    if (!model.value.recCat1 || !model.value.recCat2) return false
  }
  return true
}

defineExpose({ validate })
</script>
