<template>
  <div class="page-step-panel">
    <!-- 고객 유형 -->
    <MsfCustomerType v-model="formData" />
    <!-- // 고객 유형 -->
    <!-- 가입자 정보 -->
    <MsfSubscriberChgInfo v-model="formData" phoneLabel="변경 휴대폰번호" />
    <!-- // 가입자 정보 -->
    <!-- 법정대리인 정보 / 법정대리인 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo v-model="formData" />
    <!-- // 법정대리인 정보 / 법정대리인 안내사항 확인 및 동의 -->
    <!-- 대리인 위임 정보 -->
    <MsfDelegateInfo v-model="formData" v-if="formData.cstmrVisitTypeCd === 'V2'" />
    <!-- // 대리인 위임 정보 -->
    <!-- 가입자 연락처 -->
    <MsfContactInfo v-model="formData" />
    <!-- // 가입자 연락처 -->
    <!-- 서비스 변경 선택 -->
    <MsfServiceChangeSelection v-model="formData" />
    <!-- // 서비스 변경 선택 -->

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
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { showAlert } from '@/libs/utils/comp.utils'
import { storeToRefs } from 'pinia'
import { computed, ref, watch } from 'vue'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const store = useMsfFormSvcChgStore()
const { formData } = storeToRefs(store)

const isComplete = ref('')
const isServiceSelectionChecked = computed(() => formData.value.serviceCheckYn === 'Y')

const focusField = (target) => {
  setTimeout(() => {
    let element = null
    if (target.startsWith('#') || target.startsWith('input') || target.startsWith('button')) {
      element = document.querySelector(target)
    } else {
      element = document.getElementById(target)
    }

    if (element && !['INPUT', 'BUTTON', 'SELECT', 'TEXTAREA'].includes(element.tagName)) {
      element = element.querySelector('input, button, select, textarea') || element
    }

    element?.scrollIntoView({ block: 'center', behavior: 'smooth' })
    element?.focus()
  }, 0)
}

const getMissingPhoneTarget = (prefix) => {
  const f = formData.value
  if (!f[`${prefix}1`]) return `inp-${prefix}1`
  if (!f[`${prefix}2`]) return `inp-${prefix}2`
  return `inp-${prefix}3`
}

const hasLegalAgentRegistrationNo = (f) =>
  !!((f.repRegistrationNo1 || f.repForeignerNo1) && (f.repRegistrationNo2 || f.repForeignerNo2))

const getMissingAgentPhoneTarget = (f) => {
  if (!f.minorAgentTelFnNo) return 'inp-agentPhone1'
  if (!f.minorAgentTelMnNo) return 'inp-agentPhone2'
  return 'inp-agentPhone3'
}

const validateWithAlert = () => {
  const f = formData.value

  if (!f.cstmrTypeCd) {
    showAlert('고객 유형을 선택해 주세요.', () => focusField('input[name="base-inp-customerType"]'))
    return false
  }
  if (['JP', 'GO'].includes(f.cstmrTypeCd) && !f.cstmrVisitTypeCd) {
    showAlert('방문 유형을 선택해 주세요.', () => focusField('input[name="base-inp-visitType"]'))
    return false
  }
  if (!f.cstmrNm) {
    showAlert('이름을 입력해 주세요.', () => focusField('inp-cstmrNm'))
    return false
  }
  if (!f.userBirthDate) {
    showAlert('생년월일을 입력해 주세요.', () => focusField('inp-userBirthDate'))
    return false
  }
  if (!f.userGender) {
    showAlert('성별을 선택해 주세요.', () => focusField('input[name="base-user-gender"]'))
    return false
  }
  if (!f.deviceChgTel1 || !f.deviceChgTel2 || !f.deviceChgTel3) {
    showAlert('변경 휴대폰번호를 입력해 주세요.', () => focusField(getMissingPhoneTarget('deviceChgTel')))
    return false
  }
  if (!store.authFlags?.deviceChgTel) {
    showAlert('변경 휴대폰번호 인증을 완료해 주세요.', () => focusField('btn-deviceChgTelAuth'))
    return false
  }
  if (['NM', 'FM'].includes(f.cstmrTypeCd)) {
    if (!f.repName) {
      showAlert('법정대리인 이름을 입력해 주세요.', () => focusField('inp-repName'))
      return false
    }
    if (!hasLegalAgentRegistrationNo(f)) {
      showAlert('법정대리인 주민등록번호/외국인등록번호를 입력해 주세요.', () => focusField('inp-combinedNo1'))
      return false
    }
    if (!f.minorAgentRelTypeCd) {
      showAlert('신청인과의 관계를 선택해 주세요.', () => focusField('inp-repRelation'))
      return false
    }
    if (!f.minorAgentTelFnNo || !f.minorAgentTelMnNo || !f.minorAgentTelRnNo) {
      showAlert('법정대리인 연락처를 입력해 주세요.', () => focusField('inp-repPhone'))
      return false
    }
    if (!f.repAgree) {
      showAlert('법정대리인 안내사항 확인 및 동의가 필요합니다.')
      return false
    }
  }
  if (f.cstmrVisitTypeCd === 'V2') {
    if (!f.minorAgentNm) {
      showAlert('위임받은 고객 이름을 입력해 주세요.', () => focusField('inp-minorAgentNm'))
      return false
    }
    if (!f.agentBirthDate) {
      showAlert('위임받은 고객 생년월일을 입력해 주세요.', () => focusField('inp-agentBirthDate'))
      return false
    }
    if (!f.minorAgentRelTypeCd) {
      showAlert('신청인과의 관계를 선택해 주세요.', () => focusField('inp-minorAgentRelTypeCd'))
      return false
    }
    if (!f.minorAgentTelFnNo || !f.minorAgentTelMnNo || !f.minorAgentTelRnNo) {
      showAlert('위임받은 고객 연락처를 입력해 주세요.', () => focusField(getMissingAgentPhoneTarget(f)))
      return false
    }
  }
  if (!f.mobileNo1 || !f.mobileNo2 || !f.mobileNo3) {
    showAlert('휴대폰번호를 입력해 주세요.', () => focusField(getMissingPhoneTarget('mobileNo')))
    return false
  }
  if (!f.emailAddr1 || !f.emailAddr2) {
    showAlert('이메일주소를 입력해 주세요.', () => focusField('inp-emailAddr'))
    return false
  }
  if (!f.zipNo || !f.address) {
    showAlert('주소를 입력해 주세요.', () => focusField('inp-detailAddress'))
    return false
  }
  if (!f.detailAddress) {
    showAlert('상세주소를 입력해 주세요.', () => focusField('inp-detailAddress'))
    return false
  }
  if (!(f.serviceSelect || []).length) {
    showAlert('서비스를 선택해 주세요.', () => focusField('input[name="inp-serviceSelect"]'))
    return false
  }
  if (f.serviceCheckYn !== 'Y') {
    showAlert('서비스 체크를 완료해 주세요.', () => focusField('input[name="inp-serviceSelect"]'))
    return false
  }
  if (!f.agency) {
    showAlert('대리점을 선택해 주세요.', () => focusField('inp-agency'))
    return false
  }

  return true
}

// const { codeList: rawTermsList } = useCommonCode('CLAUSE_MINOR_AGENT')

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => isComplete.value,
  (newVal) => {
    isComplete.value = newVal
    emit('complete', newVal === 'true' || isServiceSelectionChecked.value)
  },
)

watch(
  () => formData.value.serviceCheckYn,
  (newVal) => {
    emit('complete', newVal === 'Y' || isComplete.value === 'true')
  },
  { immediate: true },
)

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
  if (!validateWithAlert()) {
    console.warn('[서비스변경][고객정보저장] 진행 중단', { reason: 'validate failed' })
    return false
  }

  formData.value.serviceSelectCompleteYn = 'Y'
  formData.value.serviceSelectCompleted = true
  emit('complete', true)

  console.log('[서비스변경][서비스선택] 선택 완료', {
    selected: formData.value.serviceSelect,
    serviceCheckYn: formData.value.serviceCheckYn,
    serviceSelectCompleteYn: formData.value.serviceSelectCompleteYn,
  })

  return true
}

defineExpose({ data, save })

</script>

<style scoped></style>
