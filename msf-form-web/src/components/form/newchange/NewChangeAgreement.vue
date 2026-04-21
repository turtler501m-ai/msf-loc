<template>
  <div class="page-step-panel">
    <MsfTitleArea title="신청서 녹취 및 서명" />
    <p class="ut-text-desc ut-mt-16">
      신청서 확인 버튼을 눌러, 고객 서명 및 안내 녹취를 진행해 주세요.
    </p>

    <div class="ut-mt-40">
      <MsfAppConfirm ref="appConfirmRef" @confirm="onConfirmApp" />
    </div>

    <!-- 녹취 및 서명 진행 완료 표시 영역 -->
    <MsfBox class="ut-mt-20" v-if="store.agreement.recYn === 'Y'">
      <p class="ut-text-center ut-color-primary ut-font-bold">
        ✅ 신청서 확인 및 녹취/서명이 완료되었습니다.
      </p>
    </MsfBox>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import MsfAppConfirm from '@/components/form/common/MsfAppConfirm.vue'

const props = defineProps({
  prevStepValidate: { type: Function, default: () => true },
})

const emit = defineEmits(['complete'])
const store = useMsfFormNewChgStore()

// 동의 단계의 임시 완료 상태
const isComplete = ref(false)

const validate = () => {
  // 녹취가 완료(Y)되었는지 확인
  return store.agreement.recYn === 'Y'
}

const checkRequiredFields = () => {
  const isReady = validate()
  isComplete.value = isReady
  emit('complete', isReady)
}

watch(
  () => store.agreement.recYn,
  () => {
    checkRequiredFields()
  },
)

onMounted(() => {
  store.validateAgreement = validate
  checkRequiredFields()
})

const onConfirmApp = () => {
  console.log('신청서 팝업 확인 완료 - 녹취/서명 저장 처리')

  // 팝업에서 "확인"을 누르면 녹취/서명이 완료된 것으로 간주 (추후 실제 녹취 모듈/서명 패드 연동)
  store.agreement.recYn = 'Y'
  store.agreement.recFileNm = 'sample_record_file_001.mp3'
  store.agreement.recFilePathNm = '/recordings/2026/04/15/'

  checkRequiredFields()
}

const save = async () => {
  // 이전 단계(Customer) 검증 (임시 주석 처리)
  /*
  if (!store.validateCustomer()) {
    console.error('Previous step (Customer) validation failed')
    return false
  }
  */

  // 이전 단계(Product) 검증 (임시 주석 처리)
  /*
  if (!store.validateProduct()) {
    console.error('Previous step (Product) validation failed')
    return false
  }
  */

  // 현재 단계(Agreement) 검증
  if (!validate()) {
    alert('신청서 확인 버튼을 눌러 서명 및 녹취를 진행해주세요.')
    return false
  }
  return await store.apiCompleteApplication()
}

defineExpose({ save, validate, reset: store.resetAll })
</script>

<style lang="scss" scoped></style>
