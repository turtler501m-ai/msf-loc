<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="신분증" tag="div" required>
        <MsfChip
          v-model="model.identityCertTypeCd"
          name="inp-idCardCertType"
          groupCode="IDENTITY_CERT_TYPE_CD"
          :disabled="model.isVerified || model.isSaved"
          :data="[]"
        >
          <template #endSlot>
            <MsfButton
              variant="subtle"
              :disabled="model.isVerified || model.identityCertTypeCd === 'S'"
              @click="handleAuthClick"
            >
              조회/인증
            </MsfButton>
          </template>
        </MsfChip>
      </MsfFormGroup>

      <MsfFormGroup label="신분증 스캔" tag="div" required v-if="model.identityCertTypeCd !== 'S'">
        <MsfStack type="field">
          <MsfSelect
            v-model="model.identityTypeCd"
            :groupCode="model.identityCertTypeCd === 'F' ? 'fathCertIdType' : 'RCP2006'"
            placeholder="신분증 선택"
            title="신분증 선택"
            :selectPop="true"
            :disabled="model.isScanVerified || model.isSaved"
            class="ut-w-300"
          />
          <MsfButton
            variant="subtle"
            :disabled="model.isScanVerified || model.isSaved || !model.identityTypeCd"
            @click="isIdCardScanModalOpen = true"
          >스캔하기</MsfButton
          >
        </MsfStack>
        <MsfStack type="field">
          <MsfDateInput
            v-model="model.identityIssuDate"
            :readonly="true"
            :disabled="model.isScanVerified || model.isSaved"
          />
          <MsfSelect
            title="면허지역"
            v-model="model.identityIssuRegion"
            :options="licenseRegionCodes"
            placeholder="면허지역"
            class="ut-w-200"
            :disabled="model.isScanVerified || model.isSaved"
          />
          <MsfNumberInput
            v-model="model.driveLicnsNo"
            maxlength="15"
            placeholder="면허번호"
            class="ut-w-240"
            :readonly="true"
            :disabled="model.isScanVerified || model.isSaved"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>

    <!-- 신분증 목록 조회 모달 -->
    <MsfIdCardListModal v-model="isIdCardListModalOpen" @confirm="onIdCardSelect" />
    <!-- 모바일 신분증 인증 모달 -->
    <MsfMobileIdModal v-model="isMobileIdModalOpen" @confirm="onMobileIdConfirm" />
    <!-- 안면 인증 모달 -->
    <MsfFaceAuthModal v-model="isFaceAuthModalOpen" @confirm="onFaceAuthConfirm" />
    <!-- 신분증 스캔 모달 -->
    <MsfIdCardScanModal v-model="isIdCardScanModalOpen" @confirm="onIdCardScanConfirm" />
  </div>
</template>
<script setup>
import { ref, defineModel, defineProps, onMounted, watch } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import MsfIdCardListModal from './popups/MsfIdCardListModal.vue'
import MsfMobileIdModal from './popups/MsfMobileIdModal.vue'
import MsfFaceAuthModal from './popups/MsfFaceAuthModal.vue'
import MsfIdCardScanModal from './popups/MsfIdCardScanModal.vue'

const props = defineProps({
  title: { type: String, default: '신분증 확인' },
  authFlags: { type: Object, default: () => ({}) },
})
const model = defineModel({ type: Object, required: true })

const licenseRegionCodes = ref([])
const fathCertIdTypeCodes = ref([])
const isIdCardListModalOpen = ref(false)
const isMobileIdModalOpen = ref(false)
const isFaceAuthModalOpen = ref(false)
const isIdCardScanModalOpen = ref(false)

// 인증 방식 변경 시 상태 초기화
watch(
  () => model.value.identityCertTypeCd,
  () => {
    if (!model.value.isSaved) {
      model.value.identityTypeCd = ''
      model.value.isVerified = false
      model.value.isScanVerified = false
    }
  },
)

onMounted(async () => {
  const [licRegion, fathCert] = await Promise.all([
    getCommonCodeList('LIC_REGION'),
    getCommonCodeList('fathCertIdType'),
  ])
  licenseRegionCodes.value = (licRegion || []).map((item) => ({
    ...item,
    label: item.title,
    value: item.code,
  }))
  fathCertIdTypeCodes.value = (fathCert || []).map((item) => item.code)
})

const handleAuthClick = () => {
  if (model.value.identityCertTypeCd === 'K') {
    isIdCardListModalOpen.value = true
  } else if (model.value.identityCertTypeCd === 'M') {
    isMobileIdModalOpen.value = true
  } else if (model.value.identityCertTypeCd === 'F') {
    isFaceAuthModalOpen.value = true
  }
}

const onIdCardSelect = (selected) => {
  console.log('선택된 신분증 목록:', selected)
  if (selected) {
    // 가입자 이름 세팅
    if (selected.cstmrNm) model.value.cstmrNm = selected.cstmrNm

    // 고객 유형에 따른 주민/외국인 등록번호 세팅
    if (['NA', 'NM'].includes(model.value.cstmrTypeCd)) {
      if (selected.rrn1) model.value.cstmrNativeRrn1 = selected.rrn1
      if (selected.rrn2) model.value.cstmrNativeRrn2 = selected.rrn2
    } else if (['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
      if (selected.rrn1) model.value.cstmrForeignerRrn1 = selected.rrn1
      if (selected.rrn2) model.value.cstmrForeignerRrn2 = selected.rrn2
    }

    // 신분증 유형 세팅
    if (selected.identityTypeCd) {
      model.value.identityTypeCd = selected.identityTypeCd
    }
  }

  // 선택된 신분증 유형이 안면인증 대상(fathCertIdType)에 포함되는지 확인
  const needsFaceAuth = fathCertIdTypeCodes.value.includes(model.value.identityTypeCd)

  if (needsFaceAuth) {
    console.log('K-NOTE 인증 후 안면인증 추가 진행 필요')
    isFaceAuthModalOpen.value = true
  } else {
    model.value.isVerified = true
  }
}

const onMobileIdConfirm = () => {
  console.log('모바일 신분증 인증 완료')
  model.value.isVerified = true
}

const onFaceAuthConfirm = () => {
  console.log('안면 인증 완료')
  model.value.isVerified = true
}

const onIdCardScanConfirm = (data) => {
  console.log('신분증 스캔 파일:', data)
  if (data) {
    model.value.identityIssuDate = data.identityIssuDate
    // 만약 API에서 다른 정보(면허지역, 면허번호 등)도 온다면 여기서 추가로 세팅할 수 있습니다.
  }
  model.value.isScanVerified = true
}

const validate = () => {
  const needsAuth = !model.value.isTrCustomer && model.value.identityCertTypeCd !== 'S'
  const needsScan = model.value.identityCertTypeCd !== 'S'

  if (needsAuth && !model.value.isVerified) return false
  if (needsScan && !model.value.isScanVerified) return false

  return true
}

defineExpose({ validate })
</script>
