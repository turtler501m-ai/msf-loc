<script setup>
import { defineModel, defineProps, ref, watch, onMounted, defineExpose, computed } from 'vue'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils.js'
import { useAuthButton } from '@/hooks/useAuthButton'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  title: { type: String, default: '안심 보험' },
  customerData: { type: Object, default: () => ({}) },
  resetKey: { type: Number, default: 0 },
  serviceChange: { type: Boolean, default: false },
})
const model = defineModel({ type: Object, required: true })
const authFlags = defineModel('authFlags', { type: Object, required: true })

const insuranceAgreementRef = ref(null)

const categoryOptions = ref([])
const insuranceOptions = ref([])
const insurancePolicy = ref('')
const isVerified = computed(() => insuranceAuth.status.value === 'verified')

// ASIS는 보험 상품코드별 하드코딩 매핑으로 apple/android 아이콘을 표시한다.
// TOBE도 별도 OS 필드를 기대하지 않고 선택된 보험 상품코드(recCat2)를 기준으로 아이콘을 표시한다.
const IOS_INSURANCE_PRODUCT_CODES = new Set([
  'PL245L235',
  'PL245L236',
  'PL245L237',
  'PL214L317',
  'PL214L319',
  'PL214L316',
])

const ANDROID_INSURANCE_PRODUCT_CODES = new Set([
  'PL245L228',
  'PL245L229',
  'PL245L230',
  'PL245L231',
  'PL245L232',
  'PL248N660',
  'PL248N661',
  'PL248N662',
  'PL248M658',
  'PL248N659',
  'PL214L310',
  'PL214L312',
])

const BOTH_OS_INSURANCE_PRODUCT_CODES = new Set([
  // ASIS 화면은 중고/USIM 상품을 apple/android 양쪽 아이콘으로 표시한다.
  'PL245L233',
  'PL245L234',
  'PL213M175',
  'PL212O953',
])

const getInsuranceOsTypes = (insrProdCd) => {
  if (BOTH_OS_INSURANCE_PRODUCT_CODES.has(insrProdCd)) return ['ios', 'android']
  if (IOS_INSURANCE_PRODUCT_CODES.has(insrProdCd)) return ['ios']
  if (ANDROID_INSURANCE_PRODUCT_CODES.has(insrProdCd)) return ['android']
  return []
}

const selectedInsuranceOption = computed(() => {
  return insuranceOptions.value.find((option) => option.value === model.value.recCat2) || null
})

const selectedInsuranceOsTypes = computed(() => {
  return selectedInsuranceOption.value?.osTypes || []
})

const getDefaultInsuranceDeviceOs = (osTypes) => {
  if (osTypes.includes('android')) return 'android'
  return osTypes[0] || ''
}

const getInsuranceOsLabel = (osType) => (osType === 'ios' ? 'iOS' : 'Android')

const getInsuranceReqBuyType = () =>
  model.value.reqBuyType || props.customerData?.productType || 'MM'

const getInsuranceRprsPrdtId = () =>
  model.value.rprsPrdtId ||
  props.customerData?.rprsPrdtId ||
  model.value.modelId ||
  props.customerData?.modelId ||
  ''

const selectInsuranceDeviceOs = (osType) => {
  if (isVerified.value || !selectedInsuranceOsTypes.value.includes(osType)) return
  model.value.insuranceDeviceOs = osType
}

const resolveAuthFlag = () => {
  return authFlags.value?.insurance || false
}

const updateAuthFlag = (v) => {
  if (authFlags.value) {
    authFlags.value.insurance = v
  }
  model.value.insuranceConfirmCompleted = v
}

const validateInsurance = () =>
  !!model.value.recCat2 &&
  (selectedInsuranceOsTypes.value.length === 0 || !!model.value.insuranceDeviceOs) &&
  model.value.insuranceAgree

const insuranceAuth = useAuthButton(
  () => [
    model.value.recCat1,
    model.value.recCat2,
    model.value.insuranceDeviceOs,
    model.value.insuranceAgree,
  ],
  {
    get value() {
      return resolveAuthFlag()
    },
    set value(v) {
      updateAuthFlag(v)
    },
  },
  validateInsurance,
)

watch(
  () => props.resetKey,
  (val, old) => {
    if (typeof old === 'number') {
      insuranceAuth.requireReauth()
    }
  },
)

// 보험 정책 문구 설정
async function setInsurancePolicy() {
  try {
    const list = await getCommonCodeList('CLAUSE_INSUR')
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
      reqBuyTypeCd: getInsuranceReqBuyType(),
      rprsPrdtId: getInsuranceRprsPrdtId(),
      // 서비스변경은 ASIS 후보 산출 조건을 사용하되, 추천카테고리 기준 조회 흐름은 유지한다.
      serviceChangeYn: props.serviceChange ? 'Y' : 'N',
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
      reqBuyTypeCd: getInsuranceReqBuyType(), // MM(단말), UU(유심)
      rprsPrdtId: getInsuranceRprsPrdtId(), // 대표 ID (modelId) 우선 사용
      prodCtgId: ctgId,
      // 서비스변경도 선택된 추천카테고리 안에서 ASIS 후보 조건과 교집합으로 조회한다.
      serviceChangeYn: props.serviceChange ? 'Y' : 'N',
    }

    const res = await post('/api/form/product/insr/list', payload)
    const data = res.data || []
    insuranceOptions.value = data.map((item) => ({
      label: item.insrProdNm || item.rateNm || item.prodNm,
      value: item.insrProdCd || item.rateCd || item.prodId,
      osTypes: getInsuranceOsTypes(item.insrProdCd || item.rateCd || item.prodId),
    }))

    // 첫번째 상품 디폴트 선택
    if (insuranceOptions.value.length > 0) {
      const isExist = insuranceOptions.value.some((opt) => opt.value === model.value.recCat2)
      if (!model.value.recCat2 || !isExist) {
        model.value.recCat2 = insuranceOptions.value[0].value
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
      model.value.insuranceDeviceOs = ''
      model.value.insuranceAgree = false
      model.value.insuranceConfirmCompleted = false
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
      model.value.insuranceDeviceOs = ''
      insuranceOptions.value = []
    }
  },
)

watch(
  () => model.value.recCat2,
  (newVal, oldVal) => {
    if (!newVal) {
      model.value.insuranceDeviceOs = ''
      return
    }

    if (oldVal && newVal !== oldVal) {
      model.value.insuranceDeviceOs = getDefaultInsuranceDeviceOs(selectedInsuranceOsTypes.value)
    }
  },
)

watch(
  selectedInsuranceOsTypes,
  (osTypes) => {
    if (osTypes.length === 0) {
      model.value.insuranceDeviceOs = ''
      return
    }

    if (!osTypes.includes(model.value.insuranceDeviceOs)) {
      model.value.insuranceDeviceOs = getDefaultInsuranceDeviceOs(osTypes)
    }
  },
  { immediate: true },
)

onMounted(async () => {
  // 안심 보험 가입을 기본값으로 설정 (값이 없는 경우)
  if (!model.value.clauseInsuranceYn) {
    model.value.clauseInsuranceYn = 'Y'
  }

  // 초기 로드 시 가입 상태라면 데이터 조회
  if (model.value.clauseInsuranceYn === 'Y') {
    await fetchInsuranceCategories()
    await setInsurancePolicy()
    if (model.value.recCat1) {
      await fetchInsurances(model.value.recCat1)
    }
  }
})

const isVerifying = ref(false)

const handleInsuranceVerify = async () => {
  if (isVerifying.value) return
  isVerifying.value = true
  try {
    const param = {
      ncn: model.value.ncn,
      ctn: `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`,
      custId: model.value.custId,
      insrProdCd: model.value.recCat2,
      prodCtgId: model.value.recCat1,
      reqBuyType: getInsuranceReqBuyType(),
      prdcList: [
        {
          prdcCd: model.value.recCat2,
          prdcSbscTrtmCd: 'A',
          prdcTypeCd: 'R',
        },
      ],
    }
    const res = await post('/api/form/servicechange/insur/available', param)

    if (res.data.resCode === '0000') {
      insuranceAuth.verify()
      showAlert('안심보험 가입 가능여부 확인이 완료되었습니다.')
    } else {
      isVerifying.value = false
    }
  } catch (error) {
    console.error('보험 인증 실패:', error)
  } finally {
    isVerifying.value = false
  }
}

const validate = () => {
  if (model.value.clauseInsuranceYn === 'Y') {
    if (!model.value.recCat1 || !model.value.recCat2) return false
    if (!model.value.insuranceAgree) return false
  }
  return true
}

const termsAlert = () => {
  if (insuranceAuth.status.value === 'none') {
    showAlert('안심 보험 약관에 동의해주세요.')
  }
}

defineExpose({ validate })
</script>

<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="단말보험 가입" tag="div">
        <MsfChip
          v-model="model.clauseInsuranceYn"
          name="inp-isInsured"
          :data="[{ value: 'Y', label: '가입' }]"
          :readonly="isVerified"
        />
        <!-- 가입(Y) 선택 시에만 카테고리 및 상품 목록 노출 -->
        <MsfStack type="field" class="ut-w100p ut-mt-16" v-if="model.clauseInsuranceYn === 'Y'">
          <MsfSelect
            title="추천 카테고리"
            v-model="model.recCat1"
            :options="categoryOptions"
            placeholder="추천 카테고리"
            class="ut-w-300"
            :readonly="isVerified"
          />
          <MsfSelect
            title="보험 상품 선택"
            v-model="model.recCat2"
            :options="insuranceOptions"
            placeholder="보험 상품 선택"
            class="ut-flex-1"
            :readonly="isVerified"
          />
          <div class="insurance-os-badges" v-if="selectedInsuranceOsTypes.length > 0">
            <button
              v-for="osType in selectedInsuranceOsTypes"
              :key="osType"
              type="button"
              :class="['insurance-os-badge', { 'is-selected': model.insuranceDeviceOs === osType }]"
              :disabled="isVerified"
              @click="selectInsuranceDeviceOs(osType)"
            >
              {{ getInsuranceOsLabel(osType) }}
            </button>
          </div>
          <MsfStack type="field" @click="termsAlert">
            <MsfButton
              variant="toggle"
              v-if="insuranceAuth.status.value === 'none'"
              disabled
              style="pointer-events: none"
              >확인</MsfButton
            >
            <MsfButton
              variant="toggle"
              v-else-if="insuranceAuth.status.value === 'ready'"
              :disabled="isVerifying"
              @click.stop="handleInsuranceVerify"
            >
              {{ isVerifying ? '처리중...' : '확인' }}
            </MsfButton>
            <MsfButton
              variant="toggle"
              v-else-if="insuranceAuth.status.value === 'verified'"
              active
            >
              확인 완료
            </MsfButton>
          </MsfStack>
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
        type="default"
        v-model="model.insuranceAgree"
        name="본인은 안내사항을 확인하였습니다"
        required="Y"
        popTitle="안심 보험 안내사항 확인 및 동의"
        :content="insurancePolicy"
      /> -->
    </template>
  </div>
</template>

<style scoped>
.insurance-os-badges {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  flex-shrink: 0;
}

.insurance-os-badge {
  min-height: 28px;
  padding: 0 10px;
  border: 1px solid var(--color-gray-300);
  border-radius: 14px;
  background: var(--color-white);
  color: var(--color-gray-700);
  font-size: 12px;
  font-weight: 600;
  line-height: 1;
}

.insurance-os-badge.is-selected {
  border-color: var(--color-accent2-base);
  background: var(--color-bg-3);
  color: var(--color-accent2-base);
}

.insurance-os-badge:disabled {
  cursor: default;
}
</style>
