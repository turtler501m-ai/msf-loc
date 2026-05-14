<template>
  <div class="page-step-panel">
    <!-- 고객 안내 사항 -->
    <MsfTitleArea title="고객 안내 사항" />
    <p class="ut-text-desc">다음 사항을 고객님께 설명하고 서명을 받아주세요.</p>
    <MsfBox>
      <ul class="agree-list">
        <li>
          <p class="agree-tit">※ 개통정보 녹음거부 동의</p>
          <MsfCheckbox
            v-model="formData.agreeCheck1"
            label="개통 정보에 대해 모두 확인하였고, 신청내용에 이의가 없으며 더 이상의 설명을 거부합니다."
          />
        </li>
        <li>
          <p class="agree-tit">※ 명의 대여 위험 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck2"
            label="요금 상품 및 부가 서비스를 고객의사와 상관없이 무단 가입 및 의무기간을 설정하지 않으며 공시지원금 및 요금할인(선택약정)은 개통시점 공시된 기준으로 지급되며, 이에 동의합니다. 신청자 본인은 대출업자 등 제3자에게 고객명의의 휴대폰을 개통해 주거나, 개통에 필요한 신청서류를 제공하는 경우 휴대폰 대출 사기 등에 악용되어 심각한 경제적 피해를 입을 수 있음을 안내 받았으며, 아울러 신규계약서 내용, 뒷면의 이동전화 서비스 이용약관 및 유의사항, 관련 약관의 중요내용에 대한 명시와 설명을 듣고 이에 동의하여 개인정보 처리방침, 이용약관, 위치기반 서비스/위치정보사업/통신과금 서비스/본인확인서비스 이용약관에 따라 위와 같이 신규계약을 체결합니다."
          />
        </li>
        <li>
          <p class="agree-tit">※ 통신범죄예방 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck3"
            label="타인으로부터 전화나 문자 요청을 받고 그에 따라 휴대폰 또는 USIM 개통을 하지 않았음을 확인합니다. (전기통신사업법 등 관련 법률에 따라 형사처벌을 받을 수 있습니다.)"
          />
        </li>
        <li>
          <p class="agree-tit">※ 명의도용방지 서비스(M-Safer) 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck4"
            label="명의도용방지서비스(M-Safer)는 본인명의 통신서비스 가입 현황을 실시간 조회 가능하며, 본인동의 없이 통신서비스에 가입하지 못하도록 차단서비스를 제공합니다.(이용 및 신청방법 : 홈페이지 또는 PASS앱 제공)"
          />
        </li>
        <li>
          <p class="agree-tit">※ 판매자 확인 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck5"
            label="고객 보호를 위해서 통신상품 가입 시 본인확인 및 가입의사, 추가이용에 대해서 성실히 안내 하였습니다."
          >
            <template #label-prepend><em class="accent-mark">[판매자]</em></template>
          </MsfCheckbox>
        </li>
        <li>
          <p class="agree-tit">※ 가입자 확인 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck6"
            label="본인 명의의 통신상품을 타인에게 제공하거나 매개하는 경우 법률에 따라 처벌 받을 수 있습니다."
          >
            <template #label-prepend><em class="accent-mark">[가입자]</em></template>
          </MsfCheckbox>
        </li>
      </ul>
    </MsfBox>
    <!-- // 고객 안내 사항 -->

    <!-- 신청서 확인 -->
    <MsfAppConfirm
      class="ut-mt-40"
      title="등록된 판매자 서명과<br/>다른 판매자 직원인 경우,"
      @confirm="onConfirmApp"
      @click.capture="onBeforeConfirmApp"
    />
    <!-- // 신청서 확인 -->

    <!-- 녹취 및 서명 진행 완료 표시 영역 -->
    <MsfBox class="ut-mt-20" v-if="formData.value?.recYn === 'Y'">
      <p class="ut-text-center ut-color-primary ut-font-bold">
        ✅ 신청서 확인 및 녹취/서명이 완료되었습니다.
      </p>
    </MsfBox>

    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주신 부분 - 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">동의 저장</option>
          <option :value="true">성공</option>
          <option :value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
  </div>
</template>

<script setup>
import { showAlert } from '@/libs/utils/comp.utils'
import { useMsfFormOwnChgStore } from '@/stores/msf_ownerChange'
import { storeToRefs } from 'pinia'
import { ref, watch, nextTick, onMounted } from 'vue'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const isComplete = ref(false)

const store = useMsfFormOwnChgStore()
const { formData } = storeToRefs(store)

const checkRequiredFields = () => {
  const isReady = validate()
  emit('complete', isReady)
}

const onBeforeConfirmApp = (e) => {
  // 이전 단계(Customer, Product) 유효성 검사
  // const isCustomerValid = store.validateCustomer()
  // const isProductValid = store.validateProduct()

  // if (!isCustomerValid) {
  //   showAlert('가입자 정보(Step 1) 입력이 완료되지 않았습니다.')
  //   e.stopPropagation()
  //   return
  // }
  // if (!isProductValid) {
  //   showAlert('휴대폰/요금제 정보(Step 2) 입력이 완료되지 않았습니다.')
  //   e.stopPropagation()
  //   return
  // }

  // Agreement 체크박스 확인
  const isAgreeChecked =
    formData.value.agreeCheck1 &&
    formData.value.agreeCheck2 &&
    formData.value.agreeCheck3 &&
    formData.value.agreeCheck4 &&
    formData.value.agreeCheck5 &&
    formData.value.agreeCheck6
  if (!isAgreeChecked) {
    showAlert('고객 안내 사항에 모두 동의해주세요.')
    e.stopPropagation()
    return
  }
}

const onConfirmApp = () => {
  console.log('신청서 팝업 확인 완료 - 녹취/서명 저장 처리')

  // 팝업에서 "확인"을 누르면 녹취/서명이 완료된 것으로 간주
  formData.value.recYn = 'Y'
  formData.value.recFileNm = 'sample_record_file_001.mp3'
  formData.value.recFilePathNm = '/recordings/2026/04/15/'

  checkRequiredFields()
}

const validate = () => {
  // 체크박스 3종 동의 및 녹취 완료 여부 확인
  const isAgreeChecked =
    formData.value.agreeCheck1 &&
    formData.value.agreeCheck2 &&
    formData.value.agreeCheck3 &&
    formData.value.agreeCheck4 &&
    formData.value.agreeCheck5 &&
    formData.value.agreeCheck6
  const isRecCompleted = formData.value.recYn === 'Y'

  return isAgreeChecked && isRecCompleted
}

// 동의 항목 및 녹취 상태 변경 감시
watch(
  () => [
    formData.value.agreeCheck1,
    formData.value.agreeCheck2,
    formData.value.agreeCheck3,
    formData.value.agreeCheck4,
    formData.value.agreeCheck5,
    formData.value.agreeCheck6,
    formData.value.recYn,
  ],
  () => {
    checkRequiredFields()
  },
  { deep: true },
)

onMounted(() => {
  store.validateAgreement = validate
  checkRequiredFields()
})

const save = async () => {
  //  최종 데이터 저장
  console.log('================save=================')
  console.log(store.buildCompletePayload())

  const result = await store.apiCompleteApplication()
  // console.log('[명의변경][동의정보저장] 화면 데이터 반영 결과', { result })
  return result
}

const reset = async () => {
  store.resetAgreement()
  await nextTick()
  checkRequiredFields()
}

defineExpose({ save, reset })
</script>

<style scoped></style>
