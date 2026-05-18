<template>
  <div>
    <MsfTitleArea :title="computedTitle" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput
          v-model="model.cstmrNm"
          placeholder="이름"
          class="ut-w-300"
          :readonly="cstmrNmReadOnlyCompute"
          maxlength="15"
        />
      </MsfFormGroup>

      <MsfFormGroup
        v-if="['NA', 'NM'].includes(model.cstmrTypeCd) && !model.isTrCustomer"
        label="주민등록번호"
        required
      >
        <MsfStack type="field">
          <MsfNumberInput
            ref="cstmrNativeRrn1Ref"
            v-model="model.cstmrNativeRrn1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="model.isSaved || (!isMinor && model.identityCertTypeCd !== 'S')"
            @maxlength="cstmrNativeRrn2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="cstmrNativeRrn2Ref"
            v-model="model.cstmrNativeRrn2"
            id="inp-residentNo2"
            type="password"
            placeholder="뒤 7자리"
            maxlength="7"
            :readonly="model.isSaved || (!isMinor && model.identityCertTypeCd !== 'S')"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup
        v-if="model.isTrCustomer && !['JP', 'GO'].includes(model.cstmrTypeCd)"
        label="생년월일"
        required
      >
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="model.userBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
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

      <MsfFormGroup
        v-if="['FN', 'FM'].includes(model.cstmrTypeCd) && !model.isTrCustomer"
        label="외국인등록번호"
        required
      >
        <MsfStack type="field">
          <MsfNumberInput
            ref="cstmrForeignerRrn1Ref"
            v-model="model.cstmrForeignerRrn1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="model.isSaved || (!isMinor && model.identityCertTypeCd !== 'S')"
            @maxlength="cstmrForeignerRrn2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="cstmrForeignerRrn2Ref"
            v-model="model.cstmrForeignerRrn2"
            id="inp-foreignerNo2"
            type="password"
            placeholder="뒤 7자리"
            maxlength="7"
            :readonly="model.isSaved || (!isMinor && model.identityCertTypeCd !== 'S')"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup v-if="['JP', 'GO'].includes(model.cstmrTypeCd)" label="법인등록번호" required>
        <MsfStack type="field">
          <MsfNumberInput
            ref="cstmrJuridicalRrn1Ref"
            v-model="model.cstmrJuridicalRrn1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="model.isSaved"
            @maxlength="cstmrJuridicalRrn2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="cstmrJuridicalRrn2Ref"
            v-model="model.cstmrJuridicalRrn2"
            id="inp-corpRegNo2"
            placeholder="뒤 7자리"
            maxlength="7"
            :readonly="model.isSaved"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup
        v-if="
          ['NA', 'JP', 'GO', 'FN'].includes(model.cstmrTypeCd) &&
          (!model.isTrCustomer || ['JP', 'GO'].includes(model.cstmrTypeCd))
        "
        label="사업자등록번호"
        :required="false"
        :helpText="biznohelpText"
      >
        <MsfStack type="field">
          <MsfNumberInput
            ref="bizNo1Ref"
            v-model="model.cstmrJuridicalBizNo1"
            placeholder="앞 3자리"
            maxlength="3"
            :readonly="model.isSaved"
            @maxlength="bizNo2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="bizNo2Ref"
            v-model="model.cstmrJuridicalBizNo2"
            id="inp-bizNo2"
            placeholder="가운데 2자리"
            maxlength="2"
            :readonly="model.isSaved"
            @maxlength="bizNo3Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="bizNo3Ref"
            v-model="model.cstmrJuridicalBizNo3"
            id="inp-bizNo3"
            placeholder="뒤 5자리"
            maxlength="5"
            :readonly="model.isSaved"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup v-if="['JP', 'GO'].includes(model.cstmrTypeCd)" label="대표자명" required>
        <MsfInput
          v-model="model.cstmrJuridicalRepNm"
          placeholder="대표자명 입력"
          maxlength="15"
          class="ut-w-300"
          :readonly="reqNmReadOnlyCompute"
        />
      </MsfFormGroup>

      <MsfFormGroup
        v-if="['JP', 'GO'].includes(model.cstmrTypeCd) && !model.isTrCustomer"
        label="업종/업태"
        required
      >
        <MsfStack type="field" class="ut-w100p">
          <MsfSelect
            title="업종 선택"
            v-model="model.upjnCd"
            groupCode="UPJN_CD"
            placeholder="업종 선택"
            class="ut-w-300"
            :disabled="model.isSaved"
          />
          <MsfInput
            v-model="model.bcuSbst"
            placeholder="업태 입력"
            class="ut-flex-1"
            :readonly="model.isSaved"
            maxlength="30"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup
        v-if="
          model.joinType === 'HDN3' ||
          model.formType === 'SVC' ||
          model.formType === 'TERMINATION' ||
          model.isTrCustomer
        "
        :label="phoneLabel"
        required
      >
        <MsfStack type="field">
          <MsfNumberInput
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
            @maxlength="deviceChgTel3Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="deviceChgTel3Ref"
            v-model="model.deviceChgTel3"
            id="inp-deviceChgTel3"
            placeholder="뒤 4자리"
            maxlength="4"
          />
          <MsfButton variant="toggle" v-if="deviceChgAuth.status.value === 'none'" disabled
            >인증</MsfButton
          >
          <MsfButton
            variant="toggle"
            v-else-if="deviceChgAuth.status.value === 'ready'"
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
import { computed, ref } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  title: { type: String, default: '가입자 정보' },
  phoneLabel: { type: String, default: '해지 휴대폰번호' },
  name: { type: String, default: 'base' },
  preCheckFunc: { type: Function, default: null },
  isEditable: { type: Boolean, default: false },
})

const model = defineModel({ type: Object, required: true })

const bizNo1Ref = ref(null)
const bizNo2Ref = ref(null)
const bizNo3Ref = ref(null)
const deviceChgTel1Ref = ref(null)
const deviceChgTel2Ref = ref(null)
const deviceChgTel3Ref = ref(null)

const cstmrNativeRrn1Ref = ref(null)
const cstmrNativeRrn2Ref = ref(null)
const cstmrForeignerRrn1Ref = ref(null)
const cstmrForeignerRrn2Ref = ref(null)
const cstmrJuridicalRrn1Ref = ref(null)
const cstmrJuridicalRrn2Ref = ref(null)

const store = useMsfFormNewChgStore()
const terminationStore = useMsfFormTerminationStore()
const isTerminationForm = computed(() => model.value?.formType === 'TERMINATION')
const formTypeArr = ['TERMINATION', 'OWN', 'SVC']
const biznohelpText = computed(() =>
  ['NA', 'FN'].includes(model.value.cstmrTypeCd) ? '※ 개인사업자인 경우만 입력' : '',
)

const isMinor = computed(() => ['NM', 'FM'].includes(model.value.cstmrTypeCd))
const cstmrNmReadOnlyCompute = computed(() => {
  // 1. 최우선 순위: 이미 저장된 단계라면 무조건 수정 불가 (Readonly)
  if (model.value.isSaved) return true

  // 2. 수정 가능한 조건들을 정의 (하나라도 해당하면 false 반환)
  const canEdit =
    model.value.isTrCustomer || // 양도 고객이거나
    (model.value.isTeCustomer && ['JP', 'GO'].includes(model.value.cstmrTypeCd)) || // 양수 고객 중 법인/공공이거나
    isMinor.value ||
    model.value.identityCertTypeCd === 'S' // 미성년자는 수정 가능 / 성인이면 인증 예외인 경우

  // 3. 수정 가능하면 false, 아니면 true
  return !canEdit
})
const reqNmReadOnlyCompute = computed(() => {
  // 1. 최우선 순위: 이미 저장된 단계라면 무조건 수정 불가 (Readonly)
  if (model.value.isSaved) return true
  // 2. 수정 가능한 조건들을 정의 (하나라도 해당하면 false 반환)
  const canEdit =
    // 양수 고객 중 법인/공공일 때 / 인증 예외인 경우
    model.value.isTeCustomer &&
    ['JP', 'GO'].includes(model.value.cstmrTypeCd) &&
    model.value.identityCertTypeCd === 'S'

  // 3. 수정 가능하면 false, 아니면 true
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
    (val, index) => val.length === expectedLengths[index] && /^\d+$/.test(val),
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
  if (props.preCheckFunc) {
    const ctn = `${model.value.deviceChgTel1}${model.value.deviceChgTel2}${model.value.deviceChgTel3}`
    const userNm = model.value.cstmrNm
    const isSuccess = await props.preCheckFunc({ ctn, userNm })

    if (isSuccess) {
      showAlert('휴대폰번호 인증이 완료되었습니다.')
      deviceChgAuth.status.value = 'verified'
      updateAuthFlag(true)
    }
  } else {
    handleDeviceChgVerify()
  }
}

const handleDeviceChgVerify = async () => {
  const phoneNo = `${model.value.deviceChgTel1}${model.value.deviceChgTel2}${model.value.deviceChgTel3}`
  const customerLinkName = (model.value.cstmrNm || '').trim()
  console.log('[Auth] request input', {
    formType: model.value.formType,
    cstmrNm: model.value.cstmrNm,
    deviceChgTel1: model.value.deviceChgTel1,
    deviceChgTel2: model.value.deviceChgTel2,
    deviceChgTel3: model.value.deviceChgTel3,
    phoneNo,
  })
  try {
    const res = await post('/api/form/ktmmember/auth', {
      subscriberNo: phoneNo,
      customerLinkName,
    })
    console.log('[Auth] response raw', res)
    const contractNum = res?.data?.contractNum || res?.data?.contract_num || res?.data?.ncn
    const lstComActvDate =
      res?.data?.lstComActvDate ||
      res?.data?.lst_com_actv_date ||
      res?.data?.initActivationDate ||
      ''

    if (res?.code !== '0000' || !contractNum) {
      console.warn('[Auth] failed response', {
        code: res?.code,
        message: res?.message,
        contractNum,
      })
      return
    }

    model.value.contractNum = contractNum
    model.value.lstComActvDate = lstComActvDate
    if (isTerminationForm.value) {
      terminationStore.setTerminationContract(contractNum, 'MsfSubscriberInfo')
      model.value.ncn = contractNum
      terminationStore.apiGetMyinfoView()
    }

    console.log('[Auth] mapped result', {
      contractNum: model.value.contractNum,
      ncn: model.value.ncn,
      lstComActvDate: model.value.lstComActvDate,
      formType: model.value.formType,
    })
    showAlert('휴대폰번호 인증이 완료되었습니다.')
    deviceChgAuth.status.value = 'verified'
    updateAuthFlag(true)
  } catch (error) {
    console.error('[Auth] verify failed', error)
  }
}

const validate = () => {
  if (!model.value.cstmrNm) return false

  if (['NA', 'NM'].includes(model.value.cstmrTypeCd)) {
    if (model.value.isTrCustomer && (!model.value.userBirthDate || !model.value.userGender))
      return false
    if (!model.value.isTrCustomer && (!model.value.cstmrNativeRrn1 || !model.value.cstmrNativeRrn2))
      return false
  }

  if (['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
    if (model.value.isTrCustomer && (!model.value.userBirthDate || !model.value.userGender))
      return false
    if (
      !model.value.isTrCustomer &&
      (!model.value.cstmrForeignerRrn1 || !model.value.cstmrForeignerRrn2)
    )
      return false
  }

  if (['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
    if (!model.value.cstmrJuridicalRrn1 || !model.value.cstmrJuridicalRrn2) return false
    if (!model.value.cstmrJuridicalRepNm) return false
    if (!model.value.upjnCd || !model.value.bcuSbst) return false
  }

  if (
    model.value.joinType === 'HDN3' ||
    model.value.formType === 'SVC' ||
    model.value.formType === 'TERMINATION' ||
    model.value.isTrCustomer
  ) {
    if (!model.value.deviceChgTel1 || !model.value.deviceChgTel2 || !model.value.deviceChgTel3)
      return false
    if (!resolveAuthFlag()) return false
  }

  return true
}

defineExpose({ validate })
</script>
