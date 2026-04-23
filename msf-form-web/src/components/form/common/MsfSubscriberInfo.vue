<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput
          v-model="model.cstmrNm"
          placeholder="이름"
          class="ut-w-300"
          :readonly="model.isSaved || model.identityCertTypeCd !== 'S'"
        />
      </MsfFormGroup>

      <MsfFormGroup v-if="['NA', 'NM'].includes(model.cstmrTypeCd)" label="주민등록번호" required>
        <MsfStack type="field">
          <MsfNumberInput
            v-model="model.cstmrNativeRrn1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="model.isSaved || model.identityCertTypeCd !== 'S'"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.cstmrNativeRrn2"
            id="inp-residentNo2"
            placeholder="뒤 7자리"
            maxlength="7"
            :readonly="model.isSaved || model.identityCertTypeCd !== 'S'"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup v-if="['FN', 'FM'].includes(model.cstmrTypeCd)" label="외국인등록번호" required>
        <MsfStack type="field">
          <MsfNumberInput
            v-model="model.cstmrForeignerRrn1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="model.isSaved || model.identityCertTypeCd !== 'S'"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.cstmrForeignerRrn2"
            id="inp-foreignerNo2"
            placeholder="뒤 7자리"
            maxlength="7"
            :readonly="model.isSaved || model.identityCertTypeCd !== 'S'"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup v-if="['JP', 'GO'].includes(model.cstmrTypeCd)" label="법인등록번호" required>
        <MsfStack type="field">
          <MsfNumberInput
            v-model="model.cstmrJuridicalRrn1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="model.isSaved"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.cstmrJuridicalRrn2"
            id="inp-corpRegNo2"
            placeholder="뒤 7자리"
            maxlength="7"
            :readonly="model.isSaved"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup
        v-if="['NA', 'JP', 'GO'].includes(model.cstmrTypeCd)"
        label="사업자등록번호"
        :required="['JP', 'GO'].includes(model.cstmrTypeCd)"
      >
        <MsfStack type="field">
          <MsfNumberInput
            v-model="model.cstmrJuridicalBizNo1"
            placeholder="앞 3자리"
            maxlength="3"
            :readonly="model.isSaved"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.cstmrJuridicalBizNo2"
            id="inp-bizNo2"
            placeholder="가운데 2자리"
            maxlength="2"
            :readonly="model.isSaved"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
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
          class="ut-w-300"
          :readonly="model.isSaved"
        />
      </MsfFormGroup>

      <MsfFormGroup v-if="['JP', 'GO'].includes(model.cstmrTypeCd)" label="업종/업태" required>
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
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup
        v-if="model.joinType === 'HDN3' || model.formType === 'SVC' || model.formType === 'TERMINATION'"
        :label="phoneLabel"
        required
      >
        <MsfStack type="field">
          <MsfNumberInput v-model="model.deviceChgTel1" placeholder="앞자리" maxlength="3" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.deviceChgTel2"
            id="inp-deviceChgTel2"
            placeholder="가운데 4자리"
            maxlength="4"
            :readonly="model.isSaved"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.deviceChgTel3"
            id="inp-deviceChgTel3"
            placeholder="뒤 4자리"
            maxlength="4"
            :readonly="model.isSaved"
          />
          <MsfButton variant="toggle" v-if="deviceChgAuth.status.value === 'none'" disabled>인증</MsfButton>
          <MsfButton
            variant="toggle"
            v-else-if="deviceChgAuth.status.value === 'ready'"
            @click="handleDeviceChgVerify"
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
import { computed, defineExpose, defineModel, defineProps } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  title: { type: String, default: '가입자 정보' },
  phoneLabel: { type: String, default: '해지 휴대폰번호' },
})

const model = defineModel({ type: Object, required: true })
const store = useMsfFormNewChgStore()
const terminationStore = useMsfFormTerminationStore()
const isTerminationForm = computed(() => model.value?.formType === 'TERMINATION')

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
)

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
      res?.data?.lstComActvDate || res?.data?.lst_com_actv_date || res?.data?.initActivationDate || ''

    if (res?.code !== '0000') {
      console.warn('[Auth] failed response', { code: res?.code, message: res?.message, contractNum })
      showAlert(res?.message || '휴대폰번호 인증에 실패했습니다.')
      return
    }

    if (!contractNum) {
      console.warn('[Auth] failed response', { code: res?.code, message: res?.message, contractNum })
      showAlert('조회 실패: 계약번호를 확인할 수 없습니다.')
      return
    }

    model.value.contractNum = contractNum
    model.value.lstComActvDate = lstComActvDate
    if (isTerminationForm.value) {
      terminationStore.setTerminationContract(contractNum, 'MsfSubscriberInfo')
      model.value.ncn = contractNum
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
    showAlert('휴대폰번호 인증에 실패했습니다.')
  }
}

const validate = () => {
  if (!model.value.cstmrNm) return false

  if (['NA', 'NM'].includes(model.value.cstmrTypeCd)) {
    if (!model.value.cstmrNativeRrn1 || !model.value.cstmrNativeRrn2) return false
  }

  if (['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
    if (!model.value.cstmrForeignerRrn1 || !model.value.cstmrForeignerRrn2) return false
  }

  if (['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
    if (!model.value.cstmrJuridicalRrn1 || !model.value.cstmrJuridicalRrn2) return false
    if (
      !model.value.cstmrJuridicalBizNo1 ||
      !model.value.cstmrJuridicalBizNo2 ||
      !model.value.cstmrJuridicalBizNo3
    ) return false
    if (!model.value.cstmrJuridicalRepNm) return false
    if (!model.value.upjnCd || !model.value.bcuSbst) return false
  }

  if (model.value.joinType === 'HDN3' || model.value.formType === 'SVC' || model.value.formType === 'TERMINATION') {
    if (!model.value.deviceChgTel1 || !model.value.deviceChgTel2 || !model.value.deviceChgTel3) return false
    if (!resolveAuthFlag()) return false
  }

  return true
}

defineExpose({ validate })
</script>
