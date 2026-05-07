<script setup>
import { ref, defineModel, defineProps, onMounted } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import MsfEsimScanModal from './popups/MsfEsimScanModal.vue'

const props = defineProps({
  customerData: { type: Object, default: () => ({}) },
  authFlags: { type: Object, default: () => ({}) },
})

const formData = defineModel({ type: Object, required: true })
const isEsimScanModalOpen = ref(false)

onMounted(async () => {
  // 약관(유심구매) 공통코드 조회
  getCommonCodeList('TERMSELF').then((list) => {
    console.log('>>> 약관(유심구매) (TERMSELF):', list)
  })
})

const simAuth = useAuthButton(() => [formData.value?.reqUsimSn], {
  get value() {
    return props.authFlags?.reqUsimSn || false
  },
  set value(v) {
    if (props.authFlags) {
      props.authFlags.reqUsimSn = v
    }
  },
})

const handleSimVerify = async () => {
  const isEsim = formData.value.hasSim === 'hasSim3'
  const url = isEsim ? '/api/form/verifyEsimInfo' : '/api/form/verifyUsimInfo'

  const payload = isEsim
    ? {
        eid: formData.value.eid,
        imei1: formData.value.imei1,
        imei2: formData.value.imei2,
      }
    : {
        iccId: formData.value.reqUsimSn,
      }

  try {
    const res = await post(url, payload)
    // 비즈니스 결과(resCode) 체크
    const success = res && res.data?.resCode === '0000'

    if (success) {
      simAuth.verify()
    }
  } catch (error) {
    console.error('Verify SIM info error:', error)
  }
}

const onEsimScanConfirm = async (data) => {
  console.log('eSIM 스캔 결과:', data)
  if (data) {
    formData.value.prodNm = data.prodNm || formData.value.prodNm
    formData.value.eid = data.eid || formData.value.eid
    formData.value.imei1 = data.imei1 || formData.value.imei1
    formData.value.imei2 = data.imei2 || formData.value.imei2
    formData.value.reqUsimSn = data.iccId || formData.value.reqUsimSn

    // 1. API 검증 수행
    const payload = {
      eid: formData.value.eid,
      imei1: formData.value.imei1,
      imei2: formData.value.imei2,
    }

    try {
      const res = await post('/api/form/verifyEsimInfo', payload)
      if (res && res.data?.resCode === '0000') {
        // 2. 정상일 때만 등록 완료 처리 및 수정 불가 상태로 전환
        if (props.authFlags) {
          props.authFlags.esimImei = true
        }
      }
    } catch (error) {
      console.error('eSIM Verify error:', error)
    }
  }
}

const validate = () => {
  if (!formData.value?.hasSim) return false

  // 1. eSIM인 경우 (hasSim3)
  if (formData.value.hasSim === 'hasSim3') {
    // 모델명, EID, IMEI1 등 정보 입력 여부 및 인증 완료 여부 확인
    if (!formData.value.prodNm || !formData.value.eid || !formData.value.imei1) return false
    // eSIM은 이미지 등록(인증)이 필수
    if (!props.authFlags?.esimImei) return false
  } else {
    // 2. USIM 보유(hasSim1) 또는 USIM 구매(hasSim2)인 경우
    if (!formData.value.usimKindsCd) return false
    if (!formData.value.reqUsimSn) return false
    // 유심은 유효성 체크가 완료되어야 함
    if (!props.authFlags?.reqUsimSn) return false

    // USIM 구매 시에만 구매 방식 체크
    if (formData.value.hasSim === 'hasSim2' && !formData.value.simPurchaseMethod) return false
  }

  return true
}

defineExpose({ validate })
</script>

<template>
  <!-- SIM정보_상품(휴대폰) -->
  <MsfTitleArea title="SIM정보_상품(휴대폰)" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="SIM 보유" tag="div" required>
      <MsfChip
        v-model="formData.hasSim"
        name="inp-hasSim"
        :data="[
          { value: 'hasSim1', label: 'USIM 보유' },
          { value: 'hasSim2', label: 'USIM 구매' },
          { value: 'hasSim3', label: 'eSIM' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="USIM 선택" tag="div" required v-if="formData.hasSim !== 'hasSim3'">
      <MsfChip v-model="formData.usimKindsCd" name="inp-simType" groupCode="RCP2035" />
    </MsfFormGroup>
    <MsfFormGroup label="USIM 번호" required v-if="formData.hasSim !== 'hasSim3'">
      <MsfStack type="field">
        <MsfInput
          v-model="formData.reqUsimSn"
          placeholder="USIM 번호 19자리"
          maxlength="19"
          class="ut-w-300"
        />
        <MsfButton variant="subtle">스캔하기</MsfButton>
        <MsfButton variant="toggle" v-if="simAuth.status.value === 'none'" disabled>
          유효성 체크
        </MsfButton>
        <MsfButton
          variant="toggle"
          v-else-if="simAuth.status.value === 'ready'"
          @click="handleSimVerify"
        >
          유효성 체크
        </MsfButton>
        <MsfButton variant="toggle" v-else-if="simAuth.status.value === 'verified'" active>
          유효성 체크 완료
        </MsfButton>
      </MsfStack>
    </MsfFormGroup>
    <MsfFormGroup label="USIM 구매 방식" tag="div" required v-if="formData.hasSim === 'hasSim2'">
      <MsfChip
        v-model="formData.simPurchaseMethod"
        name="inp-simPurchaseMethod"
        :data="[
          { value: 'simPurchaseMethod1', label: '즉시납부' },
          { value: 'simPurchaseMethod2', label: '다음달 요금에 합산' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="휴대폰 정보" required v-if="formData.hasSim === 'hasSim3'">
      <MsfInput v-model="formData.prodNm" placeholder="휴대폰 모델명" class="ut-w-300" disabled />
      <MsfInput
        v-model="formData.eid"
        id="inp-phoneEID"
        placeholder="EID"
        class="ut-w-608"
        disabled
      />
      <MsfStack type="field">
        <MsfInput
          v-model="formData.imei1"
          id="inp-phoneIMEI1"
          placeholder="IMEI1"
          class="ut-w-300"
          disabled
        />
        <MsfInput
          v-model="formData.imei2"
          id="inp-phoneIMEI2"
          placeholder="IMEI2"
          class="ut-w-300"
          disabled
        />
      </MsfStack>
      <MsfButton variant="toggle" v-if="!authFlags?.esimImei" @click="isEsimScanModalOpen = true"
        >이미지 등록</MsfButton
      >
      <MsfButton variant="toggle" v-else active disabled>이미지 등록 완료</MsfButton>
    </MsfFormGroup>
  </MsfStack>

  <MsfEsimScanModal
    v-model="isEsimScanModalOpen"
    :readonly="authFlags?.esimImei"
    @confirm="onEsimScanConfirm"
  />
  <!-- // SIM정보_상품(휴대폰) -->
</template>

<style scoped lang="scss"></style>
