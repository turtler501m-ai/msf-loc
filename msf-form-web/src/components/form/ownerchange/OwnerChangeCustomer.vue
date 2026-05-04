<template>
  <div class="page-step-panel">
    <!-- 고객(양도고객) 유형 -->
    <MsfCustomerType
      v-model="formData.tr_customer"
      title="고객(양도고객) 유형"
      :name="'tr'"
      ref="trCustomerTypeRef"
    />
    <!-- // 고객(양도고객) 유형 -->
    <!-- 고객(양도고객) 신분증 확인 -->
    <MsfIdentityVerify
      v-model="formData.tr_customer"
      :title="trCustomerTitle.identityVerifyTitle"
      ref="trIdentityVerifyRef"
    />
    <!-- // 고객(양도고객) 신분증 확인 -->
    <!-- 고객(양도고객) 정보 -->
    <MsfSubscriberInfo
      v-model="formData.tr_customer"
      :preCheckFunc="trCustomerDeviceChgVerify"
      phoneLabel="명의변경 휴대폰번호"
      :title="trCustomerTitle.subscriberInfo"
      :name="'tr'"
      :isEditable="true"
      ref="trSubscriberInfoRef"
    />
    <!-- // 고객(양도고객) 정보 -->
    <!-- 고객(양도고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo
      v-model="formData.tr_customer"
      title="고객(양도고객) 법정대리인 정보"
      ref="trLegalAgentInfoRef"
    />
    <!-- // 고객(양도고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <!-- 고객(양수고객) 유형 -->
    <MsfCustomerType
      v-model="formData.te_customer"
      title="고객(양수고객) 유형"
      :name="'te'"
      ref="teCustomerTypeRef"
    />
    <!-- // 고객(양수고객) 유형 -->
    <!-- 고객(양수고객) 신분증 확인 -->
    <MsfIdentityVerify
      v-model="formData.te_customer"
      :title="teCustomerTitle.identityVerifyTitle"
      ref="teIdentityVerifyRef"
    />
    <!-- // 고객(양수고객) 신분증 확인 -->
    <!-- 고객(양수고객) 정보 -->
    <MsfSubscriberInfo
      v-model="formData.te_customer"
      :title="teCustomerTitle.subscriberInfo"
      phoneLabel="명의변경 휴대폰번호"
      :name="'te'"
      ref="teSubscriberInfoRef"
    />
    <!-- // 고객(양수고객) 정보 -->
    <!-- 고객(양수고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo
      v-model="formData.te_customer"
      title="고객(양수고객) 법정대리인 정보"
      ref="teLegalAgentInfoRef"
    />
    <!-- 고객(양수고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <!-- 고객(실사용자) 정보 -->
    <MsfRealUserInfo
      v-model="formData"
      v-if="formData.te_customer?.cstmrVisitTypeCd === 'V2'"
      ref="realUserInfoRef"
    />
    <!-- // 고객(실사용자) 정보 -->
    <!-- 대리인 위임 정보 -->
    <MsfDelegateInfo
      v-model="formData"
      v-if="formData.te_customer?.cstmrVisitTypeCd === 'V2'"
      ref="delegateInfoRef"
    />
    <!-- // 대리인 위임 정보 -->
    <!-- 구비서류 -->
    <MsfRequiredDoc v-model="formData" ref="requiredDocRef" />
    <!-- // 구비서류 -->
    <!-- 고객(양수고객) 연락처 -->
    <MsfContactInfo
      v-model="formData.te_customer"
      title="고객(양수고객) 연락처"
      ref="contactInfoRef"
    />
    <!-- // 고객(양수고객) 연락처 -->
    <!-- 요금제 정보 -->
    <MsfChargePlanInfo v-model="formData.planInfo" ref="devicePlanInfoRef" />
    <!-- // 요금제 정보 -->
    <!-- 약관 동의  -->
    <MsfTermsAgreement
      ref="termsAgreementRef"
      v-model="formData"
      :termsData="termList"
      :isSaved="formData.isSaved"
      @checked="() => console.log(validate())"
      required
    />
    <!-- // 약관 동의 -->

    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주신 부분 - 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">고객 저장</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
  </div>
</template>

<script setup>
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { useMsfFormOwnChgStore } from '@/stores/msf_ownerChange'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'
import { ref, watch } from 'vue'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const store = useMsfFormOwnChgStore()
const { formData, trCustomerTitle, teCustomerTitle } = storeToRefs(store)
const isComplete = ref(false)
const termList = ref([])

// 컴포넌트 Refs
const trCustomerTypeRef = ref(null)
const trIdentityVerifyRef = ref(null)
const trSubscriberInfoRef = ref(null)
const trLegalAgentInfoRef = ref(null)
const teCustomerTypeRef = ref(null)
const teIdentityVerifyRef = ref(null)
const teSubscriberInfoRef = ref(null)
const teLegalAgentInfoRef = ref(null)
const realUserInfoRef = ref(null)
const delegateInfoRef = ref(null)
const requiredDocRef = ref(null)
const contactInfoRef = ref(null)
const devicePlanInfoRef = ref(null)
const termsAgreementRef = ref(null)

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => isComplete.value,
  (newVal) => {
    isComplete.value = newVal
    emit('complete', newVal ? true : false)
  },
)

onMounted(async () => {
  const list = await getCommonCodeList('CLAUSE_FORM_01')
  termList.value = list
    ?.map((item) => ({
      ...item,
      label: item.title,
      value: item.code,
    }))
    .filter((item) => formData.value.customer_term.includes(item.value))
})

const trCustomerDeviceChgVerify = async (paramObj) => {
  try {
    const res = await post('/api/form/owner-change/validate', paramObj)

    if (res.data.resultCd === '00') {
      const { data } = res
      const { ncn, ctn, custId, userId } = data.response
      store.updateTrCustomer({ ncn, ctn, custId, userId })
      store.updatePlanInfo({ ctn, ncn, custId, userId })
      return true
    } else {
      showAlert(res.data.message)
      return false
    }
  } catch (e) {
    console.log(e)
    return false
  }
}

const data = async (code /* 임시저장 코드 */) => {
  // 임시저장 정보 조회
  if (code) {
    // 임시저장된 데이터 조회 후, 임시저장단계코드 리턴
    // 결과값 - null 또는 0: 임시저장 없음, 고객: 1, 상품: 2, 동의: 3
    return '1'
  }

  return '0' // 결과값 - null 또는 0: 임시저장 없음, 고객: 1, 상품: 2, 동의: 3
}

const save = async () => {
  //  데이터 임시저장
  return isComplete.value === true
}

// 현재 단계(Customer)의 모든 컴포넌트 유효성 검사
const validate = () => {
  const validations = [
    // trCustomerTypeRef.value?.validate(),
    // trIdentityVerifyRef.value?.validate(),
    // trSubscriberInfoRef.value?.validate(),
    // trLegalAgentInfoRef.value?.validate(),
    // teCustomerTypeRef.value?.validate(),
    // teIdentityVerifyRef.value?.validate(),
    // teSubscriberInfoRef.value?.validate(),
    // teLegalAgentInfoRef.value?.validate(),
    // realUserInfoRef.value?.validate(),
    // delegateInfoRef.value?.validate(),
    // requiredDocRef.value?.validate(),
    // contactInfoRef.value?.validate(),
    devicePlanInfoRef.value?.validate(),
    // termsAgreementRef.value?.validate(),
  ]
  // null(비노출 컴포넌트)은 제외하고 모든 결과가 true인지 확인
  const isReady = validations.filter((v) => v !== undefined).every((v) => v === true)
  isComplete.value = isReady
  return isReady
}

defineExpose({ data, save, validate })
</script>

<style scoped></style>
