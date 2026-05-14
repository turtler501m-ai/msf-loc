<template>
  <div v-if="['MNP3', '02'].includes(customerModel.joinType)">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="번호이동 할<br/>전화번호" required>
        <MsfStack type="field">
          <MsfSelect
            title="통신사 선택"
            v-model="model.moveCompanyCd"
            groupCode="NSC"
            withDetail
            class="ut-w-300"
            placeholder="통신사 선택"
          />
          <MsfStack type="field">
            <MsfNumberInput
              ref="moveMobileNo1Ref"
              v-model="model.moveMobileNo1"
              placeholder="앞자리"
              maxlength="3"
              @maxlength="moveMobileNo2Ref?.focus()"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              ref="moveMobileNo2Ref"
              v-model="model.moveMobileNo2"
              placeholder="가운데 4자리"
              maxlength="4"
              @maxlength="moveMobileNo3Ref?.focus()"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              ref="moveMobileNo3Ref"
              v-model="model.moveMobileNo3"
              placeholder="뒤 4자리"
              maxlength="4"
            />
          </MsfStack>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="번호이동 인증" tag="div" required>
        <MsfStack type="field">
          <MsfButton
            variant="subtle"
            @click="handlePreAuth"
            v-if="!isRequested && !store.authFlags?.moveAuthTypeCd"
            :disabled="!isPhoneReady || !model.moveCompanyCd"
          >
            번호이동 사전동의
          </MsfButton>
          <template v-else-if="store.authFlags?.moveAuthTypeCd">
            <MsfButton variant="subtle" disabled>사전동의 완료</MsfButton>
          </template>
          <template v-else-if="isRequested">
            <MsfButton variant="subtle" @click="handleCheckAgree">사전동의 결과조회</MsfButton>
            <MsfButton variant="subtle" @click="handlePayOpn">납부주장</MsfButton>
          </template>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="이번달 사용요금" tag="div" required>
        <MsfCheckbox
          v-model="model.moveThismonthPayTypeCd"
          label="다음달 요금 합산 납부 (※ 번호이동 수수료 800원)"
        />
      </MsfFormGroup>
      <MsfFormGroup label="휴대폰 할부금" tag="div" required>
        <MsfCheckboxGroup
          v-model="model.moveAllotmentSttusCd"
          :options="[
            { value: '01', label: '완납' },
            { value: '02', label: '지속(이전 통신회사에 납부)' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="미환급금<br/>요금상계(후불)" tag="div" required>
        <MsfCheckboxGroup v-model="model.moveRefundAgreeYn" groupCode="MRA" />
      </MsfFormGroup>
    </MsfStack>

    <!-- 번호이동 사전동의 실패 모달 -->
    <MsfMnpAuthFailModal v-model="isFailModalOpen" @pay-opn="handlePayOpn" />
  </div>
</template>
<script setup>
import { ref, defineModel, defineProps, computed, onMounted, watch } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import MsfMnpAuthFailModal from './popups/MsfMnpAuthFailModal.vue'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'

defineProps({
  title: { type: String, default: '번호이동 할 전화번호' },
})
const model = defineModel('modelValue', { type: Object, required: true })
const customerModel = defineModel('customerData', { type: Object, required: true })
const store = useMsfFormNewChgStore()

const moveMobileNo1Ref = ref(null)
const moveMobileNo2Ref = ref(null)
const moveMobileNo3Ref = ref(null)

const isFailModalOpen = ref(false)
const isRequested = ref(false)

const isPhoneReady = computed(() => {
  return (
    String(model.value.moveMobileNo1 || '').length === 3 &&
    String(model.value.moveMobileNo2 || '').length === 4 &&
    String(model.value.moveMobileNo3 || '').length === 4
  )
})

const handlePreAuth = async () => {
  if (!isPhoneReady.value) return

  const c = customerModel.value
  const payload = {
    requestKey: store.applicationKey,
    slsCmpnCd: c.cntpntShopCd || '',
    npTlphNo: model.value.moveMobileNo1 + model.value.moveMobileNo2 + model.value.moveMobileNo3,
    bchngNpCommCmpnCd: model.value.moveCompanyCd,
    custTypeCd: ['JA', 'JP'].includes(c.cstmrTypeCd) ? 'C' : 'I',
    custIdntNoIndCd: '01',
    custIdntNo:
      c.cstmrNativeRrn1 + c.cstmrNativeRrn2 || c.cstmrForeignerRrn1 + c.cstmrForeignerRrn2,
    custNm: c.cstmrNm,
    crprNo: c.cstmrJuridicalBizNo1 + c.cstmrJuridicalBizNo2 + c.cstmrJuridicalBizNo3 || '',
    indvBizrYn: ['PA', 'PP'].includes(c.cstmrTypeCd) ? 'Y' : 'N',
  }

  try {
    // 번호이동 사전동의 실패 시 전용 팝업을 띄워야 하므로 silent: true 처리
    const res = await post('/api/form/newchange/reqNpPreCheck', payload, { silent: true })
    if (res && res.code === '0000' && res.data?.resCode === '0000') {
      // 요청 성공 시 '결과조회' 버튼들이 나오도록 상태 변경
      isRequested.value = true
    } else {
      isFailModalOpen.value = true
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
    npTlphNo: model.value.moveMobileNo1 + model.value.moveMobileNo2 + model.value.moveMobileNo3,
    bchngNpCommCmpnCd: model.value.moveCompanyCd,
    custTypeCd: ['JA', 'JP'].includes(c.cstmrTypeCd) ? 'C' : 'I',
    custIdntNoIndCd: '01',
    custIdntNo:
      c.cstmrNativeRrn1 + c.cstmrNativeRrn2 || c.cstmrForeignerRrn1 + c.cstmrForeignerRrn2,
    custNm: c.cstmrNm,
    crprNo: c.cstmrJuridicalBizNo1 + c.cstmrJuridicalBizNo2 + c.cstmrJuridicalBizNo3 || '',
    indvBizrYn: ['PA', 'PP'].includes(c.cstmrTypeCd) ? 'Y' : 'N',
  }

  try {
    const res = await post('/api/form/newchange/reqNpAgree', payload, { silent: true })
    // resCode가 '0000'이면 번호이동 사전동의 최종 완료 처리
    if (res && res.code === '0000' && res.data?.resCode === '0000') {
      if (store.authFlags) store.authFlags.moveAuthTypeCd = true
    } else {
      // 결과조회에서도 실패(아직 동의 안함 등) 시 실패 팝업 노출
      isFailModalOpen.value = true
    }
  } catch (error) {
    console.error('Check agree error:', error)
    isFailModalOpen.value = true
  }
}

/**
 * 납부주장 (Payment Claim) 처리
 * 이전 통신사 미납 등으로 인해 사전동의가 안될 때,
 * 고객이 이미 납부했음을 주장하여 번호이동을 강제로 진행할 수 있게 요청하는 절차
 */
const handlePayOpn = async () => {
  const payload = {
    requestKey: store.applicationKey,
  }
  try {
    const res = await post('/api/form/newchange/reqPayOpn', payload)
    if (res && res.code === '0000') {
      isFailModalOpen.value = false
    }
  } catch (error) {
    console.error('PayOpn error:', error)
  }
}

// 데이터 변경 시 인증 상태 초기화
watch(
  () => [
    model.value.moveCompanyCd,
    model.value.moveMobileNo1,
    model.value.moveMobileNo2,
    model.value.moveMobileNo3,
  ],
  () => {
    // 값이 하나라도 바뀌면 사전동의 요청 상태와 완료 상태 모두 초기화
    isRequested.value = false
    if (store.authFlags) {
      store.authFlags.moveAuthTypeCd = false
    }
  },
  { deep: true },
)

onMounted(async () => {
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

    // 번호이동 인증(사전동의) 필수
    if (!store.authFlags?.moveAuthTypeCd) return false

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

defineExpose({ validate })
</script>
