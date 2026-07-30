<script setup>
import { useAuthButton } from '@/hooks/useAuthButton'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'
import { concatStrings } from '@/libs/utils/string.utils'
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { defineModel, watch } from 'vue'

const model = defineModel({ type: Object, required: true })
const store = useMsfFormSvcChgStore()

const combineSelfAuth = useAuthButton(() => [model.value.termsAgreed], {
  get value() {
    return !!model.value?.soloData
  },
  set value(v) {
    store.authFlags.combineSelfAuth = v
  },
})

watch(
  () => store.cancelAuthResetKey,
  (val, old) => {
    if (typeof old === 'number') {
      model.value.combineSoloConfirmCompleted = false
      combineSelfAuth.requireReauth()
    }
  },
)

// 아무나SOLO 결합 가능 여부 체크
const combineSelfCheck = async () => {
  try {
    const res = await post(
      '/api/form/servicechange/combine-self/check',
      {
        ncn: model.value.ncn,
        ctn: concatStrings([
          model.value.deviceChgTel1,
          model.value.deviceChgTel2,
          model.value.deviceChgTel3,
        ]),
        custId: model.value.custId,
      },
      { skipAlert: true },
    )

    if (res.code === '0000' && res.data.resCode === '0000') {
      const { resData } = res.data
      const isCombine = resData.combine
      model.value.soloData = resData.rRateNm
      model.value.combineSoloConfirmCompleted = true

      if (isCombine) {
        showAlert('결합은 가능하나 결합 혜택이 추가로 제공되지 않습니다.')
      } else {
        showAlert('아무나 SOLO 결합 가입이 가능합니다.')
      }
      combineSelfAuth.status.value = 'verified'
    } else {
      showAlert(res.data.resMessage)
    }
  } catch (error) {
    console.error('아무나SOLO 결합 가능 여부 체크:', error)
  }
}

const termsInfoUpdate = () => {
  console.log(model.value.termsAgreed)
}

const termsAlert = () => {
  if (combineSelfAuth.status.value === 'none') {
    showAlert('아무나 SOLO 결합 약관에 동의해주세요.')
  }
}
</script>
<template>
  <!-- 아무나 SOLO 결합 -->
  <MsfTitleArea title="아무나 SOLO 결합" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="데이터" required>
      <MsfStack type="field" @click="termsAlert">
        <!-- <MsfInput v-model="model.soloData" readonly /> -->
        <MsfInput v-model="model.soloData" placeholder="0 MB" class="ut-w-300" readonly />
        <MsfButton
          v-if="combineSelfAuth.status.value === 'none'"
          variant="toggle"
          disabled
          style="pointer-events: none"
          >확인</MsfButton
        >
        <MsfButton
          v-if="combineSelfAuth.status.value === 'ready'"
          variant="toggle"
          @click.stop="combineSelfCheck"
          >확인</MsfButton
        >
        <MsfButton v-else-if="combineSelfAuth.status.value === 'verified'" variant="toggle" active
          >확인 완료</MsfButton
        >
      </MsfStack>
    </MsfFormGroup>
  </MsfStack>
  <!-- // 아무나 SOLO 결합 -->
  <!-- 아무나 SOLO 결합 약관 동의 -->
  <!-- <MsfTitleArea title="아무나 SOLO 결합 약관 동의" />
  <MsfAgreementGroup
    policy="CLAUSE_SOLO"
    v-model="formData"
    @checked="termsInfoUpdate"
    ref="agreementRef"
    required
  /> -->
  <MsfTermsAgreement
    ref="termsAgreementRef"
    v-model="model"
    policy="CLAUSE_SOLO"
    title="아무나 SOLO 결합 약관 동의"
    required
    @check="termsInfoUpdate"
    :disabled="combineSelfAuth.status.value === 'verified'"
  />
  <!-- // 아무나 SOLO 결합 약관 동의 -->
</template>
