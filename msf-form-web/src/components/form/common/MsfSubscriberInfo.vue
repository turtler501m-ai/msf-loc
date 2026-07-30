<template>
  <div>
    <MsfTitleArea :title="computedTitle" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput
          ref="cstmrNmRef"
          v-model="model.cstmrNm"
          id="inp-cstmrNm"
          placeholder="이름"
          class="ut-w-300"
          :readonly="isNameReadonly"
          maxlength="100"
        />
      </MsfFormGroup>

      <MsfFormGroup v-if="isRrnNotTr" label="주민등록번호" required>
        <MsfStack type="field">
          <MsfRegNoInput
            ref="cstmrNativeRrnRef"
            v-model:registNo1="model.cstmrNativeRrn1"
            v-model:registNo2="model.cstmrNativeRrn2"
            type="resident"
            :readonly="isRrnReadonly"
          />
          <!-- <MsfNumberInput
            ref="cstmrNativeRrn1Ref"
            v-model="model.cstmrNativeRrn1"
            id="inp-cstmrNativeRrn1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="isRrnReadonly"
            @maxlength="cstmrNativeRrn2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="cstmrNativeRrn2Ref"
            v-model="model.cstmrNativeRrn2"
            id="inp-cstmrNativeRrn2"
            type="password"
            placeholder="뒤 7자리"
            maxlength="7"
            :readonly="isRrnReadonly"
          /> -->
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup v-if="isTrBirth" label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            ref="userBirthDateRef"
            v-model="model.userBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
            :readonly="isReadonly"
          />
          <MsfRadioGroup
            :name="`${name}-user-gender`"
            v-model="model.userGender"
            :options="[
              { value: 'M', label: '남' },
              { value: 'F', label: '여' },
            ]"
            class="ut-ml-16"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup v-if="isFrnNotTr" label="외국인등록번호" required>
        <MsfStack type="field">
          <MsfRegNoInput
            ref="cstmrForeignerRrnRef"
            v-model:registNo1="model.cstmrForeignerRrn1"
            v-model:registNo2="model.cstmrForeignerRrn2"
            type="foreigner"
            :readonly="isRrnReadonly"
          />
          <!-- <MsfNumberInput
            ref="cstmrForeignerRrn1Ref"
            v-model="model.cstmrForeignerRrn1"
            id="inp-cstmrForeignerRrn1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="isRrnReadonly"
            @maxlength="cstmrForeignerRrn2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="cstmrForeignerRrn2Ref"
            v-model="model.cstmrForeignerRrn2"
            id="inp-cstmrForeignerRrn2"
            type="password"
            placeholder="뒤 7자리"
            maxlength="7"
            :readonly="isRrnReadonly"
          /> -->
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup v-if="isCrn" label="법인등록번호" :required="model.cstmrTypeCd === 'JP'">
        <MsfStack type="field">
          <MsfRegNoInput
            ref="cstmrJuridicalRrnRef"
            v-model:registNo1="model.cstmrJuridicalRrn1"
            v-model:registNo2="model.cstmrJuridicalRrn2"
            type="corporate"
            :readonly="isReadonly"
          />
          <!-- <MsfNumberInput
            ref="cstmrJuridicalRrn1Ref"
            v-model="model.cstmrJuridicalRrn1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="isReadonly"
            @maxlength="cstmrJuridicalRrn2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="cstmrJuridicalRrn2Ref"
            v-model="model.cstmrJuridicalRrn2"
            id="inp-corpRegNo2"
            placeholder="뒤 7자리"
            maxlength="7"
            :readonly="isReadonly"
          /> -->
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup
        v-if="isBizNo"
        label="사업자등록번호"
        :required="isCrn"
        :helpText="biznohelpText"
      >
        <MsfStack type="field">
          <MsfBizRegInput
            ref="bizNoRef"
            v-model:bizNo1="model.cstmrJuridicalBizNo1"
            v-model:bizNo2="model.cstmrJuridicalBizNo2"
            v-model:bizNo3="model.cstmrJuridicalBizNo3"
            :readonly="isBizNoReadonly"
          />
          <!-- <MsfNumberInput
            ref="bizNo1Ref"
            v-model="model.cstmrJuridicalBizNo1"
            id="inp-bizNo1"
            placeholder="앞 3자리"
            maxlength="3"
            :readonly="isBizNoReadonly"
            @maxlength="bizNo2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="bizNo2Ref"
            v-model="model.cstmrJuridicalBizNo2"
            id="inp-bizNo2"
            placeholder="가운데 2자리"
            maxlength="2"
            :readonly="isBizNoReadonly"
            @maxlength="bizNo3Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="bizNo3Ref"
            v-model="model.cstmrJuridicalBizNo3"
            id="inp-bizNo3"
            placeholder="뒤 5자리"
            maxlength="5"
            :readonly="isBizNoReadonly"
          /> -->
        </MsfStack>
      </MsfFormGroup>

      <!-- 법인, 공공기관인 경우 사업자 교부일자 추가 -->
      <MsfFormGroup v-if="isCrn" label="사업자 교부일자" required>
        <MsfDateInput
          ref="cstmrJuridicalBizNoIssuDateRef"
          v-model="model.cstmrJuridicalBizNoIssuDate"
          id="inp-bizIssuDate"
          placeholder="사업자 교부일자 선택(YYYYMMDD)"
          class="ut-w-300"
          :max-date="new Date()"
          :readonly="props.disabled"
        />
      </MsfFormGroup>

      <!-- 개인사업자인 경우 사업자등록번호 발급일자 추가 -->
      <MsfFormGroup v-if="hasPersonalBizNo" label="사업자 발급일자" required>
        <MsfDateInput
          ref="cstmrPrivateBizNoIssuDateRef"
          v-model="model.cstmrPrivateBizNoIssuDate"
          id="inp-privateBizIssuDate"
          placeholder="사업자 발급일자 선택(YYYYMMDD)"
          class="ut-w-300"
          :max-date="new Date()"
          :readonly="props.disabled"
        />
      </MsfFormGroup>

      <MsfFormGroup v-if="isCrn" label="대표자명" required>
        <MsfInput
          ref="cstmrJuridicalRepNmRef"
          v-model="model.cstmrJuridicalRepNm"
          placeholder="대표자명 입력"
          maxlength="100"
          class="ut-w-300"
          :readonly="reqNmReadOnlyCompute"
        />
      </MsfFormGroup>

      <MsfFormGroup v-if="isCrnNotTr" label="업종/업태" :required="model.cstmrTypeCd === 'JP'">
        <MsfStack type="field" class="ut-w100p">
          <MsfSelect
            ref="upperUpjnCdRef"
            title="업종 대분류 선택"
            v-model="selectedUpperUpjnCd"
            :options="upperUpjnOptions"
            placeholder="대분류 선택"
            class="ut-w-228"
            :disabled="props.disabled"
            selectPopYn
            @click="fetchUpjnCodes"
            @change="handleUpperUpjnChange"
          />
          <MsfSelect
            ref="upjnCdRef"
            title="업종 소분류 선택"
            v-model="model.upjnCd"
            :options="lowerUpjnOptions"
            placeholder="소분류 선택"
            class="ut-w-228"
            :disabled="!selectedUpperUpjnCd || props.disabled"
            selectPopYn
          />
          <MsfInput
            ref="bcuSbstRef"
            v-model="model.bcuSbst"
            placeholder="업태 입력"
            class="ut-flex-1"
            :readonly="props.disabled"
            maxlength="30"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup v-if="isHcn3SvcTrTerm" :label="phoneLabel" required>
        <MsfStack type="field">
          <MsfMobileInput
            ref="deviceChgTelRef"
            v-model:number1="model.deviceChgTel1"
            v-model:number2="model.deviceChgTel2"
            v-model:number3="model.deviceChgTel3"
            :readonly="isReadonly"
          />
          <!-- <MsfNumberInput
            ref="deviceChgTel1Ref"
            v-model="model.deviceChgTel1"
            placeholder="앞자리"
            maxlength="3"
            :readonly="true"
            @maxlength="deviceChgTel2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="deviceChgTel2Ref"
            v-model="model.deviceChgTel2"
            id="inp-deviceChgTel2"
            placeholder="가운데 4자리"
            maxlength="4"
            :readonly="isReadonly"
            @maxlength="deviceChgTel3Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="deviceChgTel3Ref"
            v-model="model.deviceChgTel3"
            id="inp-deviceChgTel3"
            placeholder="뒤 4자리"
            :readonly="isReadonly"
            maxlength="4"
          /> -->
          <MsfButton variant="toggle" v-if="deviceChgAuth.status.value === 'none'" disabled
            >인증</MsfButton
          >
          <MsfButton
            variant="toggle"
            v-else-if="deviceChgAuth.status.value === 'ready'"
            :disabled="props.disabled"
            @click="preHandleDeviceChgVerify"
          >
            인증
          </MsfButton>
          <MsfButton variant="toggle" v-else-if="deviceChgAuth.status.value === 'verified'" active>
            인증 완료
          </MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'
import { getCommonCodeListWithDetail } from '@/libs/utils/comn.utils'

const isTrCustomer = computed(() => {
  if (model.value?.formType === 'NEWCHANGE') return false
  return !!model.value?.isTrCustomer
})

const props = defineProps({
  title: { type: String, default: '가입자 정보' },
  phoneLabel: { type: String, default: '해지 휴대폰번호' },
  name: { type: String, default: 'base' },
  preCheckFunc: { type: Function, default: null },
  isEditable: { type: Boolean, default: false },
  disabled: Boolean,
})

const model = defineModel({ type: Object, required: true })

// 법인등록번호, 대표자명 표시여부
const isCrn = computed(() => {
  return ['JP', 'GO'].includes(model.value.cstmrTypeCd)
})
// 사업자 교부일자, 업종/업태 표시여부
const isCrnNotTr = computed(() => {
  return (
    ['JP', 'GO'].includes(model.value.cstmrTypeCd) &&
    !isTrCustomer.value &&
    !['HCN3', 'HDN3'].includes(model.value.joinType)
  )
})

const allUpjnCodes = ref([])
const selectedUpperUpjnCd = ref('')
const isUpjnCodesLoading = ref(false)
const isUpjnCodesLoaded = ref(false)

const upperUpjnOptions = computed(() => {
  return allUpjnCodes.value
    .filter((item) => !item.detail?.etcValue1 || item.detail.etcValue1.trim() === '')
    .map((item) => ({
      value: item.code,
      label: item.title,
      ...item,
    }))
})

const lowerUpjnOptions = computed(() => {
  if (!selectedUpperUpjnCd.value) return []
  return allUpjnCodes.value
    .filter((item) => item.detail?.etcValue1 === selectedUpperUpjnCd.value)
    .map((item) => ({
      value: item.code,
      label: item.title,
      ...item,
    }))
})

const handleUpperUpjnChange = () => {
  model.value.upjnCd = ''
}

const fetchUpjnCodes = async () => {
  if (isUpjnCodesLoaded.value || isUpjnCodesLoading.value) return

  isUpjnCodesLoading.value = true

  try {
    const list = await getCommonCodeListWithDetail('UPJN_CD')
    if (!Array.isArray(list)) return

    allUpjnCodes.value = list
    isUpjnCodesLoaded.value = true

    if (model.value?.upjnCd) {
      const current = allUpjnCodes.value.find((item) => item.code === model.value.upjnCd)
      if (current && current.detail?.etcValue1) {
        selectedUpperUpjnCd.value = current.detail.etcValue1
      }
    }
  } catch (error) {
    console.warn('업종 코드 조회에 실패했습니다.', error)
  } finally {
    isUpjnCodesLoading.value = false
  }
}

watch(
  () => model.value?.upjnCd,
  async (newVal) => {
    if (newVal) {
      if (!isUpjnCodesLoaded.value && !isUpjnCodesLoading.value) {
        await fetchUpjnCodes()
      }
      if (allUpjnCodes.value.length > 0) {
        const current = allUpjnCodes.value.find((item) => item.code === newVal)
        if (current && current.detail?.etcValue1) {
          selectedUpperUpjnCd.value = current.detail.etcValue1
        }
      }
    }
  },
  { immediate: true },
)

watch(
  () => model.value?.cstmrTypeCd,
  (newCustomerType, previousCustomerType) => {
    if (newCustomerType !== previousCustomerType) {
      selectedUpperUpjnCd.value = ''
    }
  },
)

// const bizNo1Ref = ref(null)
// const bizNo2Ref = ref(null)
// const bizNo3Ref = ref(null)
const bizNoRef = ref(null)
// const deviceChgTel1Ref = ref(null)
// const deviceChgTel2Ref = ref(null)
// const deviceChgTel3Ref = ref(null)
const deviceChgTelRef = ref(null)

const cstmrNmRef = ref(null)
// const cstmrNativeRrn1Ref = ref(null)
// const cstmrNativeRrn2Ref = ref(null)
const cstmrNativeRrnRef = ref(null)
const userBirthDateRef = ref(null)
// const cstmrForeignerRrn1Ref = ref(null)
// const cstmrForeignerRrn2Ref = ref(null)
const cstmrForeignerRrnRef = ref(null)
// const cstmrJuridicalRrn1Ref = ref(null)
// const cstmrJuridicalRrn2Ref = ref(null)
const cstmrJuridicalRrnRef = ref(null)
const cstmrJuridicalBizNoIssuDateRef = ref(null)
const cstmrPrivateBizNoIssuDateRef = ref(null)
const cstmrJuridicalRepNmRef = ref(null)
const upperUpjnCdRef = ref(null)
const upjnCdRef = ref(null)
const bcuSbstRef = ref(null)

// 주민등록번호 표시여부
const isRrnNotTr = computed(() => {
  return ['NA', 'NM'].includes(model.value.cstmrTypeCd) && !isTrCustomer.value
})
// 생년월일 표시여부
const isTrBirth = computed(() => {
  return isTrCustomer.value && !['JP', 'GO'].includes(model.value.cstmrTypeCd)
})
// 외국인등록번호 표시여부
const isFrnNotTr = computed(() => {
  return ['FN', 'FM'].includes(model.value.cstmrTypeCd) && !isTrCustomer.value
})
// 사업자등록번호 표시여부
const isBizNo = computed(() => {
  return (
    ['NA', 'JP', 'GO', 'FN'].includes(model.value.cstmrTypeCd) &&
    (!isTrCustomer.value || ['JP', 'GO'].includes(model.value.cstmrTypeCd))
  )
})
// 휴대폰번호 표시여부
const isHcn3SvcTrTerm = computed(() => {
  return (
    model.value.joinType === 'HDN3' ||
    model.value.joinType === 'HCN3' ||
    model.value.formType === 'SVC' ||
    model.value.formType === 'TERMINATION' ||
    isTrCustomer.value
  )
})

const isVerified = computed(() => deviceChgAuth.status.value === 'verified')
const isReadonly = computed(() => !model.value.isTeCustomer && (props.disabled || isVerified.value))
const isBizNoReadonly = computed(
  () => !model.value.isTeCustomer && (props.disabled || isVerified.value),
)

const store = useMsfFormNewChgStore()
const terminationStore = useMsfFormTerminationStore()
const isTerminationForm = computed(() => model.value?.formType === 'TERMINATION')
const biznohelpText = computed(() =>
  ['NA', 'FN'].includes(model.value.cstmrTypeCd) ? '※ 개인사업자인 경우만 입력' : '',
)

const isMinor = computed(() => ['NM', 'FM'].includes(model.value.cstmrTypeCd))

const hasPersonalBizNo = computed(() => {
  return (
    ['NA', 'FN'].includes(model.value.cstmrTypeCd) &&
    String(model.value.cstmrJuridicalBizNo1 || '').length === 3 &&
    String(model.value.cstmrJuridicalBizNo2 || '').length === 2 &&
    String(model.value.cstmrJuridicalBizNo3 || '').length === 5
  )
})

const isNameReadonly = computed(() => {
  if (props.disabled) return true
  // 인증 예외('S')인 경우 임시저장(isSaved) 상태이더라도 무조건 수정 가능(readonly = false)
  if (model.value.identityCertTypeCd === 'S') return false

  // 양수자 미성년자는 수정 가능
  if (isMinor.value && model.value.isTeCustomer) return false

  // 그 외에는 기존 isReadonly 및 cstmrNmReadOnlyCompute의 결합 로직 준수
  return cstmrNmReadOnlyCompute.value || isReadonly.value
})

const isRrnReadonly = computed(() => {
  if (props.disabled) return true
  // 인증 예외('S')인 경우 임시저장(isSaved) 여부와 관계없이 무조건 수정 가능(readonly = false)
  if (model.value.identityCertTypeCd === 'S') return false

  // 미성년자('NM', 'FM')인 경우: isReadonly가 false일 때 수정 가능 || 양수자 미성년자는 수정 가능
  if (isMinor.value) return model.value.isTeCustomer ? false : isReadonly.value

  // 성인이면서 인증예외가 아닌 경우: 무조건 수정 불가 (readonly = true)
  return true
})

const cstmrNmReadOnlyCompute = computed(() => {
  if (props.disabled) return true
  // 1. 최우선 순위: 이미 저장된 단계라면 무조건 수정 불가 (Readonly)
  if (model.value.isSaved) return true

  // 2. 법인/공공기관인 경우 무조건 이름(상호명/신청자명) 입력 가능
  if (['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
    return false
  }

  // 3. 수정 가능한 조건들을 정의 (하나라도 해당하면 false 반환)
  const canEdit =
    isTrCustomer.value || // 양도 고객이거나
    isMinor.value ||
    model.value.identityCertTypeCd === 'S' // 미성년자는 수정 가능 / 성인이면 인증 예외인 경우

  // 4. 수정 가능하면 false, 아니면 true
  return !canEdit
})

const reqNmReadOnlyCompute = computed(() => {
  if (props.disabled) return true
  //  인증 예외('S')인 경우 임시저장(isSaved) 여부와 관계없이 무조건 수정 가능(readonly = false)
  if (model.value.identityCertTypeCd === 'S') return false

  // 1. 최우선 순위: 이미 저장된 단계라면 무조건 수정 불가 (Readonly)
  if (model.value.isSaved) return true

  // 2. 법인/공공기관인 경우 대표자명은 수정 불가 (스캔 정보로 세팅됨, 단 인증예외 'S'는 제외)
  if (
    !isTrCustomer.value &&
    !model.value.isTeCustomer &&
    ['JP', 'GO'].includes(model.value.cstmrTypeCd) &&
    model.value.identityCertTypeCd !== 'S'
  ) {
    return true
  }

  // 3. 수정 가능한 조건들을 정의 (하나라도 해당하면 false 반환)
  const canEdit =
    // 양수 고객 중 법인/공공일 때 / 인증 예외인 경우
    isTrCustomer.value ||
    (model.value.isTeCustomer &&
      ['JP', 'GO'].includes(model.value.cstmrTypeCd) &&
      model.value.identityCertTypeCd === 'S') ||
    (['JP', 'GO'].includes(model.value.cstmrTypeCd) && model.value.identityCertTypeCd === 'S')

  // 4. 수정 가능하면 false, 아니면 true
  return !canEdit
})

const computedTitle = computed(() => {
  if (props.title === '가입자 정보' && isMinor.value) {
    return '가입자 정보(미성년자)'
  }
  return props.title
})

const resolveAuthFlag = () => {
  if (isTerminationForm.value) return terminationStore.authFlags?.cancelPhone || false
  return store.authFlags?.deviceChgTel || false
}

const updateAuthFlag = (v) => {
  if (isTerminationForm.value) {
    if (terminationStore.authFlags) terminationStore.authFlags.cancelPhone = v
    return
  }
  if (store.authFlags) store.authFlags.deviceChgTel = v
}

const validatePhoneNumber = () => {
  const expectedLengths = [3, 4, 4]
  const actualValues = [
    model.value?.deviceChgTel1,
    model.value?.deviceChgTel2,
    model.value?.deviceChgTel3,
  ]

  return actualValues.every(
    (val, index) => val?.length === expectedLengths[index] && /^\d+$/.test(val),
  )
}

const deviceChgAuth = useAuthButton(
  () => [model.value?.deviceChgTel1, model.value?.deviceChgTel2, model.value?.deviceChgTel3],
  {
    get value() {
      return resolveAuthFlag()
    },
    set value(v) {
      updateAuthFlag(v)
    },
  },
  validatePhoneNumber,
)

const preHandleDeviceChgVerify = async () => {
  if (!checkValidation()) {
    return false
  }
  const cstmrType = model.value.cstmrTypeCd
  const userNm = (model.value.cstmrNm || '').trim()

  // 명의변경 인증 사용
  if (props.preCheckFunc) {
    const ctn = `${model.value.deviceChgTel1}${model.value.deviceChgTel2}${model.value.deviceChgTel3}`
    const userNmVal = model.value.cstmrNm
    const userBirth = model.value.userBirthDate
    const cstmrJuridicalRrn1 = model.value.cstmrJuridicalRrn1
    const cstmrJuridicalRrn2 = model.value.cstmrJuridicalRrn2

    const isSuccess = await props.preCheckFunc({
      ctn,
      userNm: userNmVal,
      userBirth,
      cstmrJuridicalRrn1,
      cstmrJuridicalRrn2,
      cstmrType,
    })

    if (isSuccess) {
      showAlert('휴대폰번호 인증이 완료되었습니다.')
      deviceChgAuth.status.value = 'verified'
      updateAuthFlag(true)
    }
  } else {
    // 가입자 정보 입력 유효성 사전 검사 (불필요한 인증 호출 방지)
    if (['NA', 'NM'].includes(cstmrType)) {
      if (!userNm || !model.value.cstmrNativeRrn1 || !model.value.cstmrNativeRrn2) {
        return showAlert('가입자정보를 먼저 입력하세요', () => {
          if (!userNm) {
            cstmrNmRef.value?.focus()
          } else {
            cstmrNativeRrnRef.value?.focus()
          }
        })
      }
    } else if (['FN', 'FM'].includes(cstmrType)) {
      if (!userNm || !model.value.cstmrForeignerRrn1 || !model.value.cstmrForeignerRrn2) {
        return showAlert('가입자정보를 먼저 입력하세요', () => {
          if (!userNm) {
            cstmrNmRef.value?.focus()
          } else {
            cstmrForeignerRrnRef.value?.focus()
          }
        })
      }
    } else if (['JP', 'GO'].includes(cstmrType)) {
      if (!userNm) {
        return showAlert('가입자정보를 먼저 입력하세요', () => {
          cstmrNmRef.value?.focus()
        })
      }
    }

    handleDeviceChgVerify()
  }
}

const handleDeviceChgVerify = async () => {
  const phoneNo = `${model.value.deviceChgTel1}${model.value.deviceChgTel2}${model.value.deviceChgTel3}`
  const customerLinkName = (model.value.cstmrNm || '').trim()

  const cstmrType = model.value.cstmrTypeCd || ''
  let cstmrSsn = ''
  let cstmrJuridicalRrn = ''

  if (['NA', 'NM'].includes(cstmrType)) {
    cstmrSsn = `${model.value.cstmrNativeRrn1 || ''}${model.value.cstmrNativeRrn2 || ''}`
  } else if (['FN', 'FM'].includes(cstmrType)) {
    cstmrSsn = `${model.value.cstmrForeignerRrn1 || ''}${model.value.cstmrForeignerRrn2 || ''}`
  } else if (['JP', 'GO'].includes(cstmrType)) {
    cstmrSsn = `${model.value.cstmrJuridicalBizNo1 || ''}${model.value.cstmrJuridicalBizNo2 || ''}${model.value.cstmrJuridicalBizNo3 || ''}`
    if (cstmrType === 'JP') {
      cstmrJuridicalRrn = `${model.value.cstmrJuridicalRrn1 || ''}${model.value.cstmrJuridicalRrn2 || ''}`
    }
  }

  try {
    const res = await post('/api/form/ktmmember/newchange-auth', {
      customerMobileNo: phoneNo,
      customerLinkName,
      cstmrType,
      cstmrSsn,
      cstmrJuridicalRrn,
    })

    const data = res?.data
    const resData = data?.resData

    if (data?.resCode === '0000' && resData) {
      const contractNum = resData.contractNum || ''
      const initActivationDate = resData.initActivationDate || ''

      model.value.contractNum = contractNum
      model.value.lstComActvDate = initActivationDate
      model.value.initActivationDate = initActivationDate

      // 1. 이메일 주소 복원
      if (resData.email && resData.email.includes('@')) {
        const [emailId, emailDomain] = resData.email.split('@')
        model.value.emailAddr1 = emailId || ''
        model.value.emailAddr2 = emailDomain || ''
      }

      // 2. 가입자 주소 복원
      if (resData.addr && resData.addr !== '-') {
        model.value.address = resData.addr
      }

      // 3. 일반전화번호 복원
      if (resData.homeTel) {
        const rawTel = String(resData.homeTel).replace(/\D/g, '')
        if (rawTel.length >= 9) {
          model.value.telNo1 = rawTel.substring(0, 3)
          model.value.telNo2 = rawTel.substring(3, rawTel.length - 4)
          model.value.telNo3 = rawTel.substring(rawTel.length - 4)
        }
      }

      // 4. 납부 및 청구 관련 정보 복원 (스토어의 product 에 바인딩)
      const p = store.product || {}
      if (resData.payBizrCd) {
        p.payBizrCd = resData.payBizrCd
      }
      if (resData.billCycleDueDay) {
        p.billCycleDueDay = resData.billCycleDueDay
      }

      // 은행 계좌/납부자 정보
      if (resData.blBankAcctNo) {
        p.reqAccountNo = resData.blBankAcctNo
      }
      if (resData.bankAcctHolderName) {
        p.reqAccountNm = resData.bankAcctHolderName
        p.reqCardNm = resData.bankAcctHolderName
      }

      if (resData.prevExpirDt && resData.prevExpirDt.length >= 4) {
        // YYMM 형식 가정
        p.reqCardYy = resData.prevExpirDt.substring(0, 2)
        p.reqCardMm = resData.prevExpirDt.substring(2, 4)
      }

      // KT 합산 청구 정보
      if (resData.jointBillWithKt) {
        p.combId = resData.jointBillWithKt
      }

      if (isTerminationForm.value) {
        terminationStore.setTerminationContract(contractNum, 'MsfSubscriberInfo')
        model.value.ncn = contractNum
        terminationStore.apiGetMyinfoView()
      }

      deviceChgAuth.status.value = 'verified'
      updateAuthFlag(true)
    }
  } catch (error) {
    console.error('[Auth] verify failed', error)
    showAlert('인증 중 오류가 발생했습니다.')
  }
}

const validate = () => {
  if (!model.value.cstmrNm) return false

  const strMsg = ref('')
  if (model.value?.formType !== 'NEWCHANGE') {
    strMsg.value = isTrCustomer.value ? '양도인 ' : '양수인 '
  }

  if (['NA', 'NM'].includes(model.value.cstmrTypeCd)) {
    if (model.value.isTrCustomer && (!model.value.userBirthDate || !model.value.userGender))
      return false
    if (!model.value.isTrCustomer && (!model.value.cstmrNativeRrn1 || !model.value.cstmrNativeRrn2))
      return false
    if (!model.value.isTrCustomer && model.value.cstmrNativeRrn2) {
      const genderDigit = model.value.cstmrNativeRrn2.charAt(0)
      if (!['1', '2', '3', '4'].includes(genderDigit)) {
        showAlert(
          strMsg.value + '주민등록번호 7번째 자리가 내국인 성별(1, 2, 3, 4)에 맞지 않습니다.',
          () => {
            model.value.cstmrNativeRrn2 = ''
            cstmrNativeRrnRef.value?.focus()
          },
        )
        return false
      }
    }
  }

  if (['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
    if (isTrCustomer.value && (!model.value.userBirthDate || !model.value.userGender)) return false
    if (!isTrCustomer.value && (!model.value.cstmrForeignerRrn1 || !model.value.cstmrForeignerRrn2))
      return false
    if (!isTrCustomer.value && model.value.cstmrForeignerRrn2) {
      const genderDigit = model.value.cstmrForeignerRrn2.charAt(0)
      if (!['5', '6', '7', '8'].includes(genderDigit)) {
        showAlert(
          strMsg.value + '외국인등록번호 7번째 자리가 외국인 성별(5, 6, 7, 8)에 맞지 않습니다.',
          () => {
            model.value.cstmrForeignerRrn2 = ''
            cstmrForeignerRrnRef.value?.focus()
          },
        )
        return false
      }
    }
  }

  if (['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
    if (model.value.cstmrTypeCd === 'JP') {
      if (!model.value.cstmrJuridicalRrn1 || !model.value.cstmrJuridicalRrn2) return false
    }
    if (!model.value.cstmrJuridicalRepNm) return false
    if (isCrn.value && !model.value.cstmrJuridicalBizNoIssuDate) return false
    // 임시 필수 제외: if (!model.value.upjnCd || !model.value.bcuSbst) return false
  }

  // 개인사업자 사업자등록번호 발급일자 검증
  if (hasPersonalBizNo.value) {
    if (!isTrCustomer.value && !model.value.cstmrPrivateBizNoIssuDate) return false
  }

  if (
    model.value.joinType === 'HDN3' ||
    model.value.joinType === 'HCN3' ||
    model.value.formType === 'SVC' ||
    model.value.formType === 'TERMINATION' ||
    isTrCustomer.value
  ) {
    if (!model.value.deviceChgTel1 || !model.value.deviceChgTel2 || !model.value.deviceChgTel3)
      return false
    if (!resolveAuthFlag()) return false
  }

  return true
}

const checkBizNoValidation = () => {
  if (model.value.cstmrTypeCd !== 'JP' && model.value.cstmrTypeCd !== 'GO') {
    return true
  }

  if (isBizNo.value && !bizNoRef.value?.isValid) {
    showAlert(`${props.title} 사업자등록번호를 입력하세요`, () => {
      bizNoRef.value?.focus()
    })
    return false
  }

  return true
}

const checkValidation = () => {
  if (!model.value.cstmrNm) {
    showAlert(`${props.title} 이름을 입력하세요`, () => {
      cstmrNmRef.value?.focus()
    })
    return false
  }
  if (isRrnNotTr.value && !cstmrNativeRrnRef.value?.isValid) {
    console.log('cstmrNativeRrnRef.value?.isValid:', cstmrNativeRrnRef.value?.isValid)
    showAlert(`${props.title} 주민등록번호를 입력하세요`, () => {
      cstmrNativeRrnRef.value?.focus()
    })
    return false
  }
  if (isRrnNotTr.value && model.value.cstmrTypeCd === 'NA' && !cstmrNativeRrnRef.value?.isAdult) {
    showAlert(
      '내국인 고객유형은 만 19세 이상만 가입 가능합니다. 만 19세 미만인 경우 내국인 미성년자(19세 미만)으로 선택하세요.',
      () => {
        cstmrNativeRrnRef.value?.focus()
      },
    )
    return false
  }
  if (isRrnNotTr.value && model.value.cstmrTypeCd === 'NM' && !cstmrNativeRrnRef.value?.isMinor) {
    showAlert(
      '내국인 미성년자(19세 미만) 고객유형은 만 19세 미만만 가능합니다. 만 19세 이상인 경우 내국인으로 선택하세요',
      () => {
        cstmrNativeRrnRef.value?.focus()
      },
    )
    return false
  }
  if (isTrBirth.value && !model.value.userBirthDate) {
    showAlert(`${props.title} 생년월일을 입력하세요`, () => {
      userBirthDateRef.value?.focus()
    })
    return false
  }
  if (isTrBirth.value && !userBirthDateRef.value?.isValid) {
    showAlert(
      `${props.title} 생년월일을 ${userBirthDateRef.value?.length}자리로 입력하세요`,
      () => {
        userBirthDateRef.value?.focus()
      },
    )
    return false
  }
  if (
    isTrBirth.value &&
    ['NM', 'FM'].includes(model.value.cstmrTypeCd) &&
    !userBirthDateRef.value?.isMinor
  ) {
    showAlert(
      `만 19세 미만만 가능합니다. 만 19세 이상인 경우 ${model.value.cstmrTypeCd === 'FM' ? '외국인' : '내국인'}으로 선택하세요.`,
      () => {
        userBirthDateRef.value?.focus()
      },
    )
    return false
  }
  if (
    isTrBirth.value &&
    ['NA', 'FN'].includes(model.value.cstmrTypeCd) &&
    !userBirthDateRef.value?.isAdult
  ) {
    showAlert(
      `만 19세 이상만 가입 가능합니다. 만 19세 미만인 경우 ${model.value.cstmrTypeCd === 'FN' ? '외국인 미성년자(19세 미만)' : ' 내국인 미성년자(19세 미만)'}으로 선택하세요.`,
      () => {
        userBirthDateRef.value?.focus()
      },
    )
    return false
  }
  if (isFrnNotTr.value && !cstmrForeignerRrnRef.value?.isValid) {
    showAlert(`${props.title} 외국인등록번호를 입력하세요`, () => {
      cstmrForeignerRrnRef.value?.focus()
    })
    return false
  }
  if (
    isFrnNotTr.value &&
    model.value.cstmrTypeCd === 'FN' &&
    !cstmrForeignerRrnRef.value?.isAdult
  ) {
    showAlert(
      '외국인 고객유형은 만 19세 이상만 가입 가능합니다. 만 19세 미만인 경우 외국인 미성년자(19세 미만)으로 선택하세요.',
      () => {
        cstmrForeignerRrnRef.value?.focus()
      },
    )
    return false
  }
  if (
    isFrnNotTr.value &&
    model.value.cstmrTypeCd === 'FM' &&
    !cstmrForeignerRrnRef.value?.isMinor
  ) {
    showAlert(
      '외국인 미성년자(19세 미만) 고객유형은 만 19세 미만만 가능합니다. 만 19세 이상인 경우 외국인으로 선택하세요.',
      () => {
        cstmrForeignerRrnRef.value?.focus()
      },
    )
    return false
  }
  if (!checkBizNoValidation()) {
    return false
  }

  if (isCrn.value && model.value?.cstmrTypeCd === 'JP' && !cstmrJuridicalRrnRef.value?.isValid) {
    showAlert(`${props.title} 법인등록번호를 입력하세요`, () => {
      cstmrJuridicalRrnRef.value?.focus()
    })
    return false
  }
  if (isCrn.value && !model.value.cstmrJuridicalRepNm) {
    showAlert(`${props.title} 대표자명을 입력하세요`, () => {
      cstmrJuridicalRepNmRef.value?.focus()
    })
    return false
  }
  if (isCrn.value && !model.value.cstmrJuridicalBizNoIssuDate) {
    showAlert(`${props.title} 사업자 교부일자를 입력하세요`, () => {
      cstmrJuridicalBizNoIssuDateRef.value?.focus()
    })
    return false
  }
  if (hasPersonalBizNo.value && !model.value.cstmrPrivateBizNoIssuDate) {
    showAlert(`${props.title} 사업자 발급일자를 입력하세요`, () => {
      cstmrPrivateBizNoIssuDateRef.value?.focus()
    })
    return false
  }
  if (isCrnNotTr.value && model.value.cstmrTypeCd === 'JP') {
    if (!selectedUpperUpjnCd.value) {
      showAlert(`${props.title} 업종의 대분류를 선택하세요`, () => {
        upperUpjnCdRef.value?.focus()
      })
      return false
    }
    if (!model.value.upjnCd) {
      showAlert(`${props.title} 업종의 소분류를 선택하세요`, () => {
        upjnCdRef.value?.focus()
      })
      return false
    }
    if (!model.value.bcuSbst) {
      showAlert(`${props.title} 업태를 입력하세요`, () => {
        bcuSbstRef.value?.focus()
      })
      return false
    }
  }
  if (isHcn3SvcTrTerm.value && !deviceChgTelRef.value?.isValid) {
    showAlert(`${props.title} ${props.phoneLabel.replace('<br/>', ' ')}를 입력하세요`, () => {
      deviceChgTelRef.value?.focus()
    })
    return false
  }
  return true
}

defineExpose({ validate, checkValidation, checkBizNoValidation })
</script>
