<script setup>
import { defineModel, defineProps, ref, watch, onMounted, defineExpose } from 'vue'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils.js'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  title: { type: String, default: '안심 보험' },
  customerData: { type: Object, default: () => ({}) },
  disabled: Boolean,
})
const model = defineModel({ type: Object, required: true })

const categoryOptions = ref([])
const insuranceOptions = ref([])
const insurancePolicy = ref('')

const recCat1Ref = ref(null)
const recCat2Ref = ref(null)
const insuranceAgreeRef = ref(null)

// 보험 정책 문구 설정
const setInsurancePolicy = async () => {
  try {
    const list = await getCommonCodeList('ClauseInsur')
    if (!list || list.length === 0) {
      insurancePolicy.value = '안심 보험 서비스 이용 약관에 동의합니다.'
      return
    }

    const isPhone = props.customerData?.productType === 'MM'
    const targetCode = isPhone ? 'INS_PH' : 'INS_USIM'
    const policy = list.find((item) => item.code === targetCode)

    // 약관 내용이 공통코드에 등록되어 있다면 사용, 아니면 기본 문구 노출
    insurancePolicy.value = policy ? policy.title : '안심 보험 서비스 이용 약관에 동의합니다.'
  } catch (e) {
    console.error('Failed to fetch insurance policy:', e)
    insurancePolicy.value = '안심 보험 서비스 이용 약관에 동의합니다.'
  }
}

// 1. 보험 카테고리 조회
const fetchInsuranceCategories = async () => {
  try {
    const res = await post('/api/form/insr/category/list', {
      prodCtgTypeCd: 'I',
      reqBuyTypeCd: props.customerData?.productType || 'MM', // MM(단말), UU(유심)
      rprsPrdtId: model.value.modelId || '',
    })
    const data = res.data || []
    categoryOptions.value = data.map((item) => ({
      label: item.prodCtgNm || item.ctgNm,
      value: item.prodCtgId || item.ctgCd,
    }))

    // 첫번째 값 디폴트 선택 (기존 값이 목록에 없거나 비어있는 경우)
    if (categoryOptions.value.length > 0) {
      const isExist = categoryOptions.value.some((opt) => opt.value === model.value.recCat1)
      if (!model.value.recCat1 || !isExist) {
        model.value.recCat1 = categoryOptions.value[0].value
      }
    }

    // 가입 상태라도 약관 동의 기본 해제
    if (model.value.clauseInsuranceYn === 'Y' && model.value.insuranceAgree === undefined) {
      model.value.insuranceAgree = false
    }
  } catch (error) {
    console.error('보험 카테고리 조회 실패:', error)
  }
}

// 2. 보험 상품 목록 조회
const fetchInsurances = async (ctgId) => {
  if (!ctgId) return

  try {
    const payload = {
      reqBuyTypeCd: props.customerData?.productType || 'MM', // MM(단말), UU(유심)
      rprsPrdtId: model.value.modelId || '', // 대표 ID (modelId) 우선 사용
      prodCtgId: ctgId,
    }

    const res = await post('/api/form/product/insr/list', payload)
    const data = res.data || []
    insuranceOptions.value = data.map((item) => ({
      label: item.insrProdNm || item.rateNm || item.prodNm,
      value: item.insrProdCd || item.rateCd || item.prodId,
    }))

    // 첫번째 상품 디폴트 선택
    if (insuranceOptions.value.length > 0) {
      const currentSavedVal = model.value.recCat2 || model.value.insrProdCd
      const hasSavedVal = currentSavedVal
        ? insuranceOptions.value.find((opt) => opt.value === currentSavedVal)
        : null
      if (hasSavedVal) {
        model.value.recCat2 = hasSavedVal.value
      } else {
        const isExist = insuranceOptions.value.some((opt) => opt.value === model.value.recCat2)
        if (!model.value.recCat2 || !isExist) {
          model.value.recCat2 = insuranceOptions.value[0].value
        }
      }
    }
  } catch (error) {
    console.error('보험 상품 조회 실패:', error)
  }
}

// 가입 여부 변경 감시
watch(
  () => model.value.clauseInsuranceYn,
  async (newVal) => {
    if (newVal === 'Y') {
      await fetchInsuranceCategories()
      await setInsurancePolicy()
    } else {
      model.value.recCat1 = ''
      model.value.recCat2 = ''
      model.value.insuranceAgree = false
      categoryOptions.value = []
      insuranceOptions.value = []
      insurancePolicy.value = ''
    }
  },
)

// 카테고리 변경 감시
watch(
  () => model.value.recCat1,
  async (newVal, oldVal) => {
    if (newVal) {
      if (oldVal && newVal !== oldVal) {
        model.value.recCat2 = ''
      }
      await fetchInsurances(newVal)
    } else {
      model.value.recCat2 = ''
      insuranceOptions.value = []
    }
  },
)

onMounted(async () => {
  // 안심 보험 가입을 기본값으로 설정 (값이 없는 경우)
  if (!model.value.clauseInsuranceYn) {
    model.value.clauseInsuranceYn = 'N'
  }

  // 초기 로드 시 가입 상태라면 데이터 조회
  if (model.value.clauseInsuranceYn === 'Y') {
    await fetchInsuranceCategories()
    await setInsurancePolicy()

    // 임시저장 복원 데이터(insrProdCd) 역추적 및 매핑 복원
    const savedInsrCd = model.value.recCat2 || model.value.insrProdCd
    if (savedInsrCd && !model.value.recCat1 && categoryOptions.value.length > 0) {
      for (const cat of categoryOptions.value) {
        try {
          const payload = {
            reqBuyTypeCd: props.customerData?.productType || 'MM',
            rprsPrdtId: model.value.modelId || '',
            prodCtgId: cat.value,
          }
          const res = await post('/api/form/product/insr/list', payload)
          const items = res.data || []
          const match = items.some(
            (item) => (item.insrProdCd || item.rateCd || item.prodId) === savedInsrCd,
          )
          if (match) {
            model.value.recCat1 = cat.value
            model.value.recCat2 = savedInsrCd
            break
          }
        } catch (e) {
          console.error('카테고리 역추적 실패:', e)
        }
      }
    }

    if (model.value.recCat1) {
      await fetchInsurances(model.value.recCat1)
    }
  }
})

const validate = () => {
  if (model.value.clauseInsuranceYn === 'Y') {
    if (!model.value.recCat1 || !model.value.recCat2) return false
    if (!model.value.insuranceAgree) return false
  }
  return true
}

const checkValidation = () => {
  if (model.value.clauseInsuranceYn !== 'Y') {
    return true
  }

  if (!model.value.recCat2) {
    showAlert(`안심 보험 가입의 보험 상품을 선택하세요`, () => {
      recCat2Ref.value?.focus()
    })
    return false
  }

  if (insurancePolicy.value && !model.value.insuranceAgree) {
    showAlert(`안심 보험 안내사항에 동의하세요`, () => {
      insuranceAgreeRef.value?.focus()
    })
    return false
  }

  return true
}

defineExpose({ validate, checkValidation })
</script>

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
          :readonly="props.disabled"
        />
        <!-- 가입(Y) 선택 시에만 카테고리 및 상품 목록 노출 -->
        <MsfStack type="field" class="ut-w100p ut-mt-16" v-if="model.clauseInsuranceYn === 'Y'">
          <MsfSelect
            ref="recCat1Ref"
            title="추천 카테고리"
            v-model="model.recCat1"
            :options="categoryOptions"
            placeholder="추천 카테고리"
            class="ut-w-300"
            :disabled="props.disabled"
          />
          <MsfSelect
            ref="recCat2Ref"
            title="보험 상품 선택"
            v-model="model.recCat2"
            :options="insuranceOptions"
            placeholder="보험 상품 선택"
            class="ut-flex-1"
            :disabled="props.disabled"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>

    <!-- 안심 보험 약관 (법정대리인 약관과 동일한 형태) -->
    <template v-if="model.clauseInsuranceYn === 'Y' && insurancePolicy">
      <MsfTitleArea title="안심 보험 안내사항 확인 및 동의" />
      <MsfAgreementGroup
        policy="CLAUSE_INSUR"
        ref="insuranceAgreementRef"
        v-model="model.insuranceAgree"
        checkboxLabel="본인은 안내사항을 확인하였습니다."
        required
        only-required
        @checked="handleChecked"
        :disabled="props.disabled"
      />
      <!-- <MsfAgreementItem
        ref="insuranceAgreeRef"
        type="default"
        v-model="model.insuranceAgree"
        name="본인은 안내사항을 확인하였습니다"
        required="Y"
        popTitle="안심 보험 안내사항 확인 및 동의"
        :content="insurancePolicy"
        :disabled="props.disabled"
      /> -->
    </template>
  </div>
</template>
