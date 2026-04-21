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
            :groupCode="model.identityCertTypeCd === 'F' ? 'fathCertIdType' : 'IDENTITY_TYPE_CD'"
            placeholder="신분증 선택"
            title="신분증 선택"
            :selectPop="true"
            :disabled="model.isVerified || model.isSaved"
            class="ut-w-300"
          />
          <MsfButton
            variant="subtle"
            :disabled="model.isVerified || !model.identityTypeCd"
            @click="isIdCardScanModalOpen = true"
          >스캔하기</MsfButton
          >
        </MsfStack>
        <MsfStack type="field">
          <MsfDateInput
            v-model="model.identityIssuDate"
            :readonly="true"
            :disabled="model.isSaved"
          />
          <MsfSelect
            title="면허지역"
            v-model="model.identityIssuRegion"
            :options="licenseRegionCodes"
            placeholder="면허지역"
            class="ut-w-200"
            disabled
          />
          <MsfNumberInput
            v-model="model.driveLicnsNo"
            maxlength="15"
            placeholder="면허번호"
            class="ut-w-240"
            :readonly="true"
            :disabled="model.isSaved"
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
const isIdCardListModalOpen = ref(false)
const isMobileIdModalOpen = ref(false)
const isFaceAuthModalOpen = ref(false)
const isIdCardScanModalOpen = ref(false)

// 인증 방식 변경 시 선택된 신분증 유형 초기화
watch(
  () => model.value.identityCertTypeCd,
  () => {
    if (!model.value.isSaved) {
      model.value.identityTypeCd = ''
    }
  },
)

onMounted(async () => {
  const list = await getCommonCodeList('LIC_REGION')
  licenseRegionCodes.value = (list || []).map((item) => ({
    ...item,
    label: item.codeName || item.dtlCdNm || item.label || item.DTL_CD_NM,
    value: item.code || item.dtlCd || item.value || item.DTL_CD,
  }))
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
  model.value.isVerified = true
}

const onMobileIdConfirm = () => {
  console.log('모바일 신분증 인증 완료')
  model.value.isVerified = true
}

const onFaceAuthConfirm = () => {
  console.log('안면 인증 완료')
  model.value.isVerified = true
}

const onIdCardScanConfirm = (file) => {
  console.log('신분증 스캔 파일:', file)
  model.value.isVerified = true
}

const validate = () => {
  return true
}

defineExpose({ validate })
</script>
