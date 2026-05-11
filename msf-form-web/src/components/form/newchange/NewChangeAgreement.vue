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
            v-model="store.agreement.agreeCheck1"
            label="개통 정보에 대해 모두 확인하였고, 신청내용에 이의가 없으며 더 이상의 설명을 거부합니다."
          />
        </li>
        <li>
          <p class="agree-tit">※ 판매자 확인 안내</p>
          <MsfCheckbox
            v-model="store.agreement.agreeCheck2"
            label="고객 보호를 위해서 통신상품 가입 시 본인확인 및 가입의사, 추가이용에 대해서 성실히 안내 하였습니다."
          >
            <template #label-prepend><em class="accent-mark">[판매자]</em></template>
          </MsfCheckbox>
        </li>
        <li>
          <p class="agree-tit">※ 가입자 확인 안내</p>
          <MsfCheckbox
            v-model="store.agreement.agreeCheck3"
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
    <MsfBox class="ut-mt-20" v-if="store.agreement.recYn === 'Y'">
      <p class="ut-text-center ut-color-primary ut-font-bold">
        ✅ 신청서 확인 및 녹취/서명이 완료되었습니다.
      </p>
    </MsfBox>
  </div>
</template>

<script setup>
import { watch, onMounted } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { showAlert } from '@/libs/utils/comp.utils'
import MsfAppConfirm from '@/components/form/common/MsfAppConfirm.vue'

const props = defineProps({
  prevStepValidate: { type: Function, default: () => true },
})

const emit = defineEmits(['complete'])
const store = useMsfFormNewChgStore()

const validate = () => {
  // 체크박스 3종 동의 및 녹취 완료 여부 확인
  const isAgreeChecked =
    store.agreement.agreeCheck1 && store.agreement.agreeCheck2 && store.agreement.agreeCheck3
  const isRecCompleted = store.agreement.recYn === 'Y'

  return isAgreeChecked && isRecCompleted
}

const getPendingItems = () => {
  const pending = []
  if (!(store.agreement.agreeCheck1 && store.agreement.agreeCheck2 && store.agreement.agreeCheck3)) {
    pending.push('고객 안내 사항 동의')
  }
  if (store.agreement.recYn !== 'Y') {
    pending.push('신청서 확인 및 녹취/서명')
  }
  return pending
}

const checkRequiredFields = () => {
  const isReady = validate()
  emit('complete', isReady)
}

// 동의 항목 및 녹취 상태 변경 감시
watch(
  () => [
    store.agreement.agreeCheck1,
    store.agreement.agreeCheck2,
    store.agreement.agreeCheck3,
    store.agreement.recYn,
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

const onBeforeConfirmApp = (e) => {
  // 이전 단계(Customer, Product) 유효성 검사
  const isCustomerValid = store.validateCustomer()
  const isProductValid = store.validateProduct()

  if (!isCustomerValid) {
    showAlert('가입자 정보(Step 1) 입력이 완료되지 않았습니다.')
    e.stopPropagation()
    return
  }
  if (!isProductValid) {
    showAlert('휴대폰/요금제 정보(Step 2) 입력이 완료되지 않았습니다.')
    e.stopPropagation()
    return
  }

  // Agreement 체크박스 확인
  const isAgreeChecked =
    store.agreement.agreeCheck1 && store.agreement.agreeCheck2 && store.agreement.agreeCheck3
  if (!isAgreeChecked) {
    showAlert('고객 안내 사항에 모두 동의해주세요.')
    e.stopPropagation()
    return
  }
}

const onConfirmApp = () => {
  console.log('신청서 팝업 확인 완료 - 녹취/서명 저장 처리')

  // 팝업에서 "확인"을 누르면 녹취/서명이 완료된 것으로 간주
  store.agreement.recYn = 'Y'
  store.agreement.recFileNm = 'sample_record_file_001.mp3'
  store.agreement.recFilePathNm = '/recordings/2026/04/15/'

  checkRequiredFields()
}

const save = async () => {
  // 현재 단계(Agreement) 검증
  if (!validate()) {
    if (
      !(store.agreement.agreeCheck1 && store.agreement.agreeCheck2 && store.agreement.agreeCheck3)
    ) {
      showAlert('고객 안내 사항에 모두 동의해주세요.')
    } else if (store.agreement.recYn !== 'Y') {
      showAlert('신청서 확인 버튼을 눌러 서명 및 녹취를 진행해주세요.')
    }
    return false
  }
  return await store.apiCompleteApplication()
}

defineExpose({ save, validate, getPendingItems, reset: store.resetAll })
</script>

<style lang="scss" scoped>
.page-step-panel {
  display: flex;
  flex-direction: column;
  height: auto;
  min-height: min-content;
  flex-shrink: 0;
}
</style>
