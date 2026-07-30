<template>
  <div v-if="['MNP3', '02'].includes(customerModel.joinType)">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="번호이동 할<br/>전화번호" required>
        <MsfStack type="field">
          <MsfSelect
            ref="moveCompanyCdRef"
            title="전 통신사"
            v-model="model.moveCompanyCd"
            groupCode="NSC"
            withDetail
            class="ut-w-300"
            placeholder="전 통신사"
            :disabled="props.disabled || !!store.authFlags?.moveAuthTypeCd"
          />
          <MsfStack type="field">
            <MsfMobileInput
              ref="moveMobileNoRef"
              v-model:number1="model.moveMobileNo1"
              v-model:number2="model.moveMobileNo2"
              v-model:number3="model.moveMobileNo3"
              :disabled="props.disabled || !!store.authFlags?.moveAuthTypeCd"
            />
            <!-- <MsfNumberInput
              ref="moveMobileNo1Ref"
              v-model="model.moveMobileNo1"
              placeholder="앞자리"
              maxlength="3"
              @maxlength="moveMobileNo2Ref?.focus()"
              :disabled="true"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              ref="moveMobileNo2Ref"
              v-model="model.moveMobileNo2"
              placeholder="가운데 4자리"
              maxlength="4"
              @maxlength="moveMobileNo3Ref?.focus()"
              :disabled="props.disabled || !!store.authFlags?.moveAuthTypeCd"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              ref="moveMobileNo3Ref"
              v-model="model.moveMobileNo3"
              placeholder="뒤 4자리"
              maxlength="4"
              :disabled="props.disabled || !!store.authFlags?.moveAuthTypeCd"
            /> -->
          </MsfStack>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="번호이동 인증" tag="div" required>
        <MsfStack type="field">
          <template v-if="!store.authFlags?.moveAuthTypeCd">
            <MsfButton
              key="btn-mnp-request"
              ref="moveAuthTypeCdBtnRef"
              variant="subtle"
              @click="handlePreAuth"
              v-if="!isRequested"
              :disabled="props.disabled || !isPhoneReady || !model.moveCompanyCd"
            >
              번호이동 사전동의 요청
            </MsfButton>
            <MsfButton
              key="btn-mnp-query"
              variant="subtle"
              @click="handleCheckAgree"
              v-else
              :disabled="props.disabled"
            >
              결과 조회
            </MsfButton>
          </template>
          <template v-else>
            <MsfButton variant="subtle" disabled>사전동의 완료</MsfButton>
          </template>

          <!-- 임시스킵기능추가 -->
          <MsfButton
            variant="subtle"
            @click="handleSkipMnpAuth"
            :disabled="props.disabled"
            style="margin-left: 8px; border: 1px dashed red; color: red"
          >
            사전동의 스킵
          </MsfButton>
          <!-- 임시스킵기능추가 -->
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="전 통신사 이번달 사용요금" tag="div" required>
        <MsfRadioGroup
          variant="type2"
          ref="moveThismonthPayTypeCdRef"
          name="moveThismonthPayTypeCd"
          v-model="model.moveThismonthPayTypeCd"
          :options="[{ value: true, label: '다음달 요금 합산 납부 (※ 번호이동 수수료 800원)' }]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="전 통신사 휴대폰 할부금" tag="div" required>
        <MsfRadioGroup
          variant="type2"
          ref="moveAllotmentSttusCdRef"
          name="moveAllotmentSttusCd"
          v-model="moveAllotmentSttusCdComputed"
          :options="moveAllotmentSttusOptions"
          :disabled="props.disabled || store.preChecked"
        />
      </MsfFormGroup>
      <MsfFormGroup label="전 통신사 미환급금<br/>요금상계(후불)" tag="div" required>
        <MsfRadioGroup
          variant="type2"
          ref="moveRefundAgreeYnRef"
          name="moveRefundAgreeYn"
          v-model="moveRefundAgreeYnComputed"
          :options="moveRefundAgreeOptions"
          :disabled="props.disabled || store.preChecked"
        />
      </MsfFormGroup>
    </MsfStack>

    <!-- 번호이동 사전동의 실패 모달 -->
    <MsfMnpAuthFailModal v-model="isFailModalOpen" />
  </div>
</template>
<script setup>
import { ref, defineModel, defineProps, computed, onMounted, watch } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { showAlert, showConfirmAsync } from '@/libs/utils/comp.utils'
import MsfRadioGroup from '@/libs/ui/base/MsfRadioGroup.vue'

const props = defineProps({
  title: { type: String, default: '번호이동 할 전화번호' },
  disabled: Boolean,
})
const model = defineModel('modelValue', { type: Object, required: true })
const customerModel = defineModel('customerData', { type: Object, required: true })
const store = useMsfFormNewChgStore()

const moveCompanyCdRef = ref(null)
// const moveMobileNo1Ref = ref(null)
// const moveMobileNo2Ref = ref(null)
// const moveMobileNo3Ref = ref(null)
const moveMobileNoRef = ref(null)
const moveAuthTypeCdBtnRef = ref(null)
const moveThismonthPayTypeCdRef = ref(null)
const moveAllotmentSttusCdRef = ref(null)
const moveRefundAgreeYnRef = ref(null)

const isFailModalOpen = ref(false)
const isRequested = ref(false)

const moveAllotmentSttusOptions = ref([])
const moveRefundAgreeOptions = ref([])

const moveAllotmentSttusCdComputed = computed({
  get: () => {
    const val = model.value.moveAllotmentSttusCd
    return Array.isArray(val) ? val[0] || '' : val || ''
  },
  set: (newVal) => {
    model.value.moveAllotmentSttusCd = Array.isArray(model.value.moveAllotmentSttusCd)
      ? [newVal]
      : newVal
  },
})

const moveRefundAgreeYnComputed = computed({
  get: () => {
    const val = model.value.moveRefundAgreeYn
    return Array.isArray(val) ? val[0] || '' : val || ''
  },
  set: (newVal) => {
    model.value.moveRefundAgreeYn = Array.isArray(model.value.moveRefundAgreeYn) ? [newVal] : newVal
  },
})

const isPhoneReady = computed(() => {
  return (
    String(model.value.moveMobileNo1 || '').length === 3 &&
    String(model.value.moveMobileNo2 || '').length === 4 &&
    String(model.value.moveMobileNo3 || '').length === 4
  )
})

const handlePreAuth = async () => {
  if (typeof store.validateCustomerWithAlert === 'function') {
    if (!store.validateCustomerWithAlert(true)) {
      return
    }
  }

  if (!isPhoneReady.value) return

  const c = customerModel.value
  const payload = {
    requestKey: store.applicationKey,
    slsCmpnCd: c.cntpntShopCd || '',
    agentCd: c.agentCd || '',
    npTlphNo: model.value.moveMobileNo1 + model.value.moveMobileNo2 + model.value.moveMobileNo3,
    bchngNpCommCmpnCd: model.value.moveCompanyCd,
    cstmrTypeCd: c.cstmrTypeCd,
    custIdntNoIndCd: ['JP', 'GO'].includes(c.cstmrTypeCd)
      ? '02'
      : ['FN', 'FM'].includes(c.cstmrTypeCd)
        ? '05'
        : '01',
    custIdntNo:
      c.cstmrNativeRrn1 + c.cstmrNativeRrn2 ||
      c.cstmrForeignerRrn1 + c.cstmrForeignerRrn2 ||
      c.cstmrJuridicalBizNo1 + c.cstmrJuridicalBizNo2 + c.cstmrJuridicalBizNo3,
    custNm: c.cstmrNm,
    crprNo: c.cstmrJuridicalRrn1 + c.cstmrJuridicalRrn2 || '',
    indvBizrYn:
      ['NA', 'NM', 'FN', 'FM'].includes(c.cstmrTypeCd) &&
      ((c.cstmrJuridicalBizNo1 && c.cstmrJuridicalBizNo2 && c.cstmrJuridicalBizNo3) ||
        c.cstmrJuridicalBizNo ||
        c.cstmrPrivateBizNo)
        ? 'Y'
        : 'N',
  }

  try {
    // 번호이동 사전동의 실패 시 전용 팝업을 띄워야 하므로 skipAlert: true 처리
    const res = await post('/api/form/portnumber/precheck/request', payload, {
      skipAlert: true,
      timeout: 30000,
    })
    if (res) {
      isRequested.value = true
    }
  } catch (error) {
    console.error('PreAuth error:', error)
    isFailModalOpen.value = true
  }
}

const handleCheckAgree = async () => {
  const c = customerModel.value
  const payload = {
    requestKey: store.applicationKey,
    slsCmpnCd: c.cntpntShopCd || '',
    agentCd: c.agentCd || '',
    npTlphNo: model.value.moveMobileNo1 + model.value.moveMobileNo2 + model.value.moveMobileNo3,
    bchngNpCommCmpnCd: model.value.moveCompanyCd,
    cstmrTypeCd: c.cstmrTypeCd,
    custIdntNoIndCd: ['JP', 'GO'].includes(c.cstmrTypeCd)
      ? '02'
      : ['FN', 'FM'].includes(c.cstmrTypeCd)
        ? '05'
        : '01',
    custIdntNo:
      c.cstmrNativeRrn1 + c.cstmrNativeRrn2 ||
      c.cstmrForeignerRrn1 + c.cstmrForeignerRrn2 ||
      c.cstmrJuridicalBizNo1 + c.cstmrJuridicalBizNo2 + c.cstmrJuridicalBizNo3,
    custNm: c.cstmrNm,
    crprNo: c.cstmrJuridicalRrn1 + c.cstmrJuridicalRrn2 || '',
    indvBizrYn:
      ['NA', 'NM', 'FN', 'FM'].includes(c.cstmrTypeCd) &&
      ((c.cstmrJuridicalBizNo1 && c.cstmrJuridicalBizNo2 && c.cstmrJuridicalBizNo3) ||
        c.cstmrJuridicalBizNo ||
        c.cstmrPrivateBizNo)
        ? 'Y'
        : 'N',
  }

  try {
    const res = await post('/api/form/portnumber/precheck/result', payload, {
      timeout: 30000,
      skipAlert: true,
    })
    // resCode가 '0000'이면 번호이동 사전동의 최종 완료 처리
    if (res && res.code === '0000' && res.data?.resCode === '0000') {
      if (store.authFlags) {
        store.authFlags.moveAuthTypeCd = true
      }
      store.preChecked = true
      // 사전동의 완료 성공 시 임시저장 API를 한 번 더 호출하여 상태 동기화
      await store.apiSaveDraft(2, { requestPreCheck: 'Y' })
    } else {
      // 결과조회 실패 시 우회 성공 컨펌 팝업 노출
      const failMsg =
        res?.data?.resMessage ||
        res?.message ||
        '번호이동 사전동의 결과조회에 실패했습니다.'
      const proceed = await showConfirmAsync(
        `${failMsg}\n\n성공으로 진행하시겠습니까?`,
        '번호이동 사전동의 결과조회 성공 처리',
      )
      if (proceed) {
        if (store.authFlags) {
          store.authFlags.moveAuthTypeCd = true
        }
        store.preChecked = true
        await store.apiSaveDraft(2, { requestPreCheck: 'Y' })
      } else {
        isFailModalOpen.value = true // 결과조회 실패 모달 노출
      }
    }
  } catch (error) {
    console.error('Check agree error:', error)
    const proceed = await showConfirmAsync(
      `서버 통신 중 오류가 발생했습니다.\n\n성공으로 진행하시겠습니까?`,
      '번호이동 사전동의 결과조회 성공 처리',
    )
    if (proceed) {
      if (store.authFlags) {
        store.authFlags.moveAuthTypeCd = true
      }
      store.preChecked = true
      await store.apiSaveDraft(2, { requestPreCheck: 'Y' })
    } else {
      isFailModalOpen.value = true
    }
  }
}

// 임시스킵기능추가
const handleSkipMnpAuth = async () => {
  if (typeof store.validateCustomerWithAlert === 'function') {
    // 임시 스킵 시에는 가입조건조회 완료 여부 검증을 건너뛰기 위해 true 전달
    if (!store.validateCustomerWithAlert(true)) {
      return
    }
  }

  if (!model.value.moveCompanyCd) {
    model.value.moveCompanyCd = 'KTT'
  }
  if (!model.value.moveMobileNo1) model.value.moveMobileNo1 = '010'
  if (!model.value.moveMobileNo2) model.value.moveMobileNo2 = '1234'
  if (!model.value.moveMobileNo3) model.value.moveMobileNo3 = '5678'
  const cleanMnpNo = [
    model.value.moveMobileNo1,
    model.value.moveMobileNo2,
    model.value.moveMobileNo3,
  ]
    .filter(Boolean)
    .join('-')
  model.value.openNo = cleanMnpNo.replace(/-/g, '')
  customerModel.value.openNo = cleanMnpNo
  if (!model.value.moveThismonthPayTypeCd) model.value.moveThismonthPayTypeCd = true
  if (!model.value.moveAllotmentSttusCd) model.value.moveAllotmentSttusCd = '1'
  if (!model.value.moveRefundAgreeYn) model.value.moveRefundAgreeYn = 'Y'

  if (store.authFlags) {
    store.authFlags.moveAuthTypeCd = true
  }
  isRequested.value = true
}
// 임시스킵기능추가

// 데이터 변경 시 인증 상태 초기화
watch(
  () => [
    model.value.moveCompanyCd,
    model.value.moveMobileNo1,
    model.value.moveMobileNo2,
    model.value.moveMobileNo3,
  ],
  (newVal, oldVal) => {
    if (store.authFlags?.moveAuthTypeCd) return
    if (!oldVal || oldVal.every((v) => v === undefined)) return
    if (newVal.join('') === oldVal.join('')) return

    // 값이 하나라도 바뀌면 사전동의 요청 상태와 완료 상태 모두 초기화
    isRequested.value = false
    if (store.authFlags) {
      store.authFlags.moveAuthTypeCd = false
    }
  },
  { deep: true },
)

watch(
  () => model.value.moveThismonthPayTypeCd,
  (newVal) => {
    if (newVal !== true) {
      model.value.moveThismonthPayTypeCd = true
    }
  },
  { immediate: true },
)

onMounted(async () => {
  if (store.authFlags?.moveAuthTypeCd) {
    store.preChecked = true
  }

  // MPPY (전 통신사 휴대폰 할부금) 공통코드 조회
  const mppyCodes = await getCommonCodeList('MPPY')
  if (mppyCodes && mppyCodes.length > 0) {
    moveAllotmentSttusOptions.value = mppyCodes.map((item) => ({
      label: item.title,
      value: item.code,
    }))
    const currentVal = Array.isArray(model.value.moveAllotmentSttusCd)
      ? model.value.moveAllotmentSttusCd[0]
      : model.value.moveAllotmentSttusCd
    if (!currentVal && moveAllotmentSttusOptions.value.length > 0) {
      model.value.moveAllotmentSttusCd = Array.isArray(model.value.moveAllotmentSttusCd)
        ? [moveAllotmentSttusOptions.value[0].value]
        : moveAllotmentSttusOptions.value[0].value
    }
  }

  // MRA (전 통신사 미환급금 요금상계) 공통코드 조회
  const mraCodes = await getCommonCodeList('MRA')
  if (mraCodes && mraCodes.length > 0) {
    moveRefundAgreeOptions.value = mraCodes.map((item) => ({
      label: item.title,
      value: item.code,
    }))
    const currentVal = Array.isArray(model.value.moveRefundAgreeYn)
      ? model.value.moveRefundAgreeYn[0]
      : model.value.moveRefundAgreeYn
    if (!currentVal && moveRefundAgreeOptions.value.length > 0) {
      model.value.moveRefundAgreeYn = Array.isArray(model.value.moveRefundAgreeYn)
        ? [moveRefundAgreeOptions.value[0].value]
        : moveRefundAgreeOptions.value[0].value
    }
  }

  // 사전인증 예외 통신사 목록 조회
  getCommonCodeList('NpNscException').then((list) => {
    console.log('>>> 사전인증 예외 통신사 (NpNscException):', list)
  })
})

const validate = () => {
  if (['MNP3', '02'].includes(customerModel.value.joinType)) {
    if (!model.value.moveCompanyCd) return false
    if (!model.value.moveMobileNo1 || !model.value.moveMobileNo2 || !model.value.moveMobileNo3)
      return false

    // 번호이동 인증(사전동의) 필수 검사를 작성완료 시점에 백그라운드로 미루므로 여기서는 주석 처리
    // if (!store.authFlags?.moveAuthTypeCd) return false

    // 이번달 사용요금 동의 필수
    if (!model.value.moveThismonthPayTypeCd) return false

    // 휴대폰 할부금 선택 필수 (*)
    if (
      !model.value.moveAllotmentSttusCd ||
      (Array.isArray(model.value.moveAllotmentSttusCd) &&
        model.value.moveAllotmentSttusCd.length === 0)
    )
      return false

    // 미환급금 요금상계 선택 필수 (*)
    if (
      !model.value.moveRefundAgreeYn ||
      (Array.isArray(model.value.moveRefundAgreeYn) && model.value.moveRefundAgreeYn.length === 0)
    )
      return false
  }
  return true
}

const reset = () => {
  isRequested.value = false

  // 입력값들 '' 처리 및 기본값 복원
  if (model.value) {
    model.value.moveCompanyCd = ''
    model.value.moveMobileNo1 = '010'
    model.value.moveMobileNo2 = ''
    model.value.moveMobileNo3 = ''
    model.value.moveThismonthPayTypeCd = true
    model.value.moveAllotmentSttusCd = []
    model.value.moveRefundAgreeYn = []
  }

  if (store.authFlags) {
    store.authFlags.moveAuthTypeCd = false
  }
}

const checkValidation = () => {
  if (!['MNP3', '02'].includes(customerModel.value.joinType)) {
    return true
  }
  if (!model.value.moveCompanyCd) {
    showAlert(`${props.title} 통신사를 선택하세요`, () => {
      moveCompanyCdRef.value?.focus()
    })
    return false
  }
  if (!moveMobileNoRef.value?.isValid) {
    showAlert(`${props.title}를 입력하세요`, () => {
      moveMobileNoRef.value?.focus()
    })
    return false
  }

  if (!model.value.moveThismonthPayTypeCd) {
    showAlert(`이번달 사용요금을 선택하세요`, () => {
      moveThismonthPayTypeCdRef.value?.focus()
    })
    return false
  }
  if (
    !model.value.moveAllotmentSttusCd ||
    (Array.isArray(model.value.moveAllotmentSttusCd) &&
      model.value.moveAllotmentSttusCd.length === 0)
  ) {
    showAlert(`휴대폰 할부금을 선택하세요`, () => {
      moveAllotmentSttusCdRef.value?.focus()
    })
    return false
  }
  if (
    !model.value.moveRefundAgreeYn ||
    (Array.isArray(model.value.moveRefundAgreeYn) && model.value.moveRefundAgreeYn.length === 0)
  ) {
    showAlert(`미환급금 요금상계를 선택하세요`, () => {
      moveRefundAgreeYnRef.value?.focus()
    })
    return false
  }

  return true
}

defineExpose({ validate, reset, checkValidation })
</script>
