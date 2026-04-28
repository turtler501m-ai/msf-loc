<script setup>
import { ref, defineModel, defineProps } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { post } from '@/libs/api/msf.api'
import MsfEsimScanModal from './popups/MsfEsimScanModal.vue'

const props = defineProps({
  customerData: { type: Object, default: () => ({}) },
  authFlags: { type: Object, default: () => ({}) },
})

const formData = defineModel({ type: Object, required: true })
const isEsimScanModalOpen = ref(false)

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
  const payload = {
    iccId: formData.value.reqUsimSn,
  }

  try {
    const res = await post(url, payload)
    if (res && res.code === '0000') {
      simAuth.verify()
      alert('SIM 유효성 체크가 완료되었습니다.')
    } else {
      alert(res.message || 'SIM 유효성 체크에 실패했습니다.')
    }
  } catch (error) {
    console.error('Verify SIM info error:', error)
    alert('SIM 유효성 체크 중 오류가 발생했습니다.')
  }
}

const onEsimScanConfirm = (data) => {
  console.log('eSIM 스캔 결과:', data)
  if (data) {
    formData.value.prodNm = data.prodNm || formData.value.prodNm
    formData.value.eid = data.eid || formData.value.eid
    formData.value.imei1 = data.imei1 || formData.value.imei1
    formData.value.imei2 = data.imei2 || formData.value.imei2
    formData.value.reqUsimSn = data.iccId || formData.value.reqUsimSn
    if (props.authFlags) {
      props.authFlags.imei = true
    }
  }
}

const validate = () => {
  if (!formData.value?.hasSim) return false
  if (formData.value?.hasSim !== 'hasSim3' && !formData.value?.usimKindsCd) return false
  if (!formData.value?.reqUsimSn) return false
  if (!props.authFlags?.reqUsimSn) return false
  if (!formData.value?.simPurchaseMethod) return false
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
      <MsfChip
        v-model="formData.usimKindsCd"
        name="inp-simType"
        :data="[
          { value: 'simType1', label: '일반 6,600원' },
          { value: 'simType2', label: 'NFC 8,800원' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup :label="formData.hasSim === 'hasSim3' ? 'eSIM 번호' : 'USIM 번호'" required>
      <MsfStack type="field">
        <MsfInput
          v-model="formData.reqUsimSn"
          :placeholder="formData.hasSim === 'hasSim3' ? 'eSIM 번호 19자리' : 'USIM 번호 19자리'"
          maxlength="19"
          class="ut-w-300"
          :readonly="formData.hasSim === 'hasSim3'"
        />
        <MsfButton variant="subtle" @click="isEsimScanModalOpen = true">스캔하기</MsfButton>
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
      <MsfButton variant="toggle" v-if="!authFlags?.imei" @click="isEsimScanModalOpen = true">이미지 등록</MsfButton>
      <MsfButton variant="toggle" v-else active @click="authFlags.imei = false">이미지 등록 완료</MsfButton>
    </MsfFormGroup>
  </MsfStack>

  <MsfEsimScanModal v-model="isEsimScanModalOpen" @confirm="onEsimScanConfirm" />
  <!-- // SIM정보_상품(휴대폰) -->
</template>

<style scoped lang="scss"></style>
