<template>
  <div class="page-step-panel">
    <!-- 구비서류 -->
    <MsfRequiredDoc
      ref="requiredDocRef"
      v-model="store.customer"
      v-model:authFlags="store.authFlags"
      :refresh-key="docRefreshKey"
      :disabled="store.agreement.recYn === 'Y'"
      class="ut-mb-20"
    />

    <!-- 고객 안내 사항 -->
    <MsfTitleArea title="고객 안내 사항" />
    <p class="ut-text-desc">다음 사항을 고객님께 설명하고 서명을 받아주세요.</p>
    <MsfBox>
      <ul class="agree-list">
        <li>
          <p class="agree-tit">※ 개통정보 녹음거부 동의</p>
          <MsfCheckbox
            ref="agreeCheck1Ref"
            v-model="store.agreement.agreeCheck1"
            label="개통 정보에 대해 모두 확인하였고, 신청내용에 이의가 없으며 더 이상의 설명을 거부합니다."
          />
        </li>
        <li>
          <p class="agree-tit">※ 판매자 확인 안내</p>
          <MsfCheckbox
            ref="agreeCheck2Ref"
            v-model="store.agreement.agreeCheck2"
            label="고객 보호를 위해서 통신상품 가입 시 본인확인 및 가입의사, 추가이용에 대해서 성실히 안내 하였습니다."
          >
            <template #label-prepend><em class="accent-mark">[판매자]</em></template>
          </MsfCheckbox>
        </li>
        <li>
          <p class="agree-tit">※ 가입자 확인 안내</p>
          <MsfCheckbox
            ref="agreeCheck3Ref"
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
      ref="appConfirmRef"
      class="ut-mt-40"
      formTypeCode="newchange"
      :request-key="store.applicationKey || ''"
      :transcription-script-data="computedTranscriptionScriptData"
      :cstmr-nm="store.customer.cstmrNm || ''"
      :phone-no="store.customer.openNo || ''"
      @confirm="onConfirmApp"
      @click="onBeforeConfirmApp"
      @edit="onEditApp"
    />
    <!-- // 신청서 확인 -->

    <!-- 녹취 및 서명 진행 완료 표시 영역 -->
    <MsfBox class="ut-mt-20" v-if="store.agreement.recYn === 'Y'">
      <p class="ut-text-center ut-color-primary ut-font-bold">
        ✅ 신청서 확인 및 녹취/서명이 완료되었습니다.
      </p>
    </MsfBox>
    <MsfMnpAuthFailModal v-model="isMnpFailModalOpen" />
  </div>
</template>

<script setup>
import { onMounted, watch, ref, computed } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { showAlert, showConfirmAsync } from '@/libs/utils/comp.utils'
import MsfAppConfirm from '@/components/form/common/MsfAppConfirm.vue'
import MsfMnpAuthFailModal from '@/components/form/common/popups/MsfMnpAuthFailModal.vue'

const props = defineProps({
  prevStepValidate: { type: Function, default: () => true },
})

const emit = defineEmits(['complete'])
const store = useMsfFormNewChgStore()
const appConfirmRef = ref(null)
const isMnpFailModalOpen = ref(false)

const agreeCheck1Ref = ref(null)
const agreeCheck2Ref = ref(null)
const agreeCheck3Ref = ref(null)

const requiredDocRef = ref(null)
const docRefreshKey = ref(0)

watch(
  () => store.isDraftLoading,
  (loading) => {
    if (!loading) {
      docRefreshKey.value++
    }
  }
)

const computedTranscriptionScriptData = computed(() => {
  const c = store.customer
  const p = store.product

  // 1. 가입유형 소문자 변환 (mnp3, nac3, hdn3 등)
  const joinType = (c.joinType || '').toLowerCase()
  const tgtType = ['mnp3', 'nac3', 'hdn3', 'icn3'].includes(joinType) ? joinType : ''

  // 2. 약정/할부 여부 (약정기간이나 할부기간이 존재하면 Y)
  const enggMnth = parseInt(p.enggMnthCnt || 0, 10)
  const modelMnth = parseInt(p.modelMonthly || 0, 10)
  const enggYn = enggMnth > 0 || modelMnth > 0 ? 'Y' : 'N'

  // 3. 단말보험 가입 여부 (보험코드나 보험약관동의가 존재하면 Y)
  const insrYn = p.insrCd || p.insrProdCd || p.clauseInsuranceYn === 'Y' ? 'Y' : 'N'

  // 4. 부가서비스 가입 여부 (부가서비스 리스트가 존재하면 Y)
  const addYn = p.additionList && p.additionList.length > 0 ? 'Y' : 'N'

  return {
    requestKey: store.applicationKey || '',
    formTypeCd: '1',
    tgtType,
    reqBuyTypeCd: c.productType || '',
    cstmrVisitTypeCd: c.cstmrVisitTypeCd || 'VMY',
    enggYn,
    sprtTypeCd: c.discountType || p.discountType || '',
    rmndYn: 'N', // 기변 승계 등 특수 처리가 없을 시 기본값 N
    rateYn: p.prodId || p.socCode ? 'Y' : 'N',
    insrYn,
    addYn,
  }
})

const validate = () => {
  // 체크박스 3종 동의 및 녹취 완료 여부 확인
  const isAgreeChecked =
    store.agreement.agreeCheck1 && store.agreement.agreeCheck2 && store.agreement.agreeCheck3
  const isRecCompleted = store.agreement.recYn === 'Y'

  return isAgreeChecked && isRecCompleted
}

const getPendingItems = () => {
  const pending = []
  if (
    !(store.agreement.agreeCheck1 && store.agreement.agreeCheck2 && store.agreement.agreeCheck3)
  ) {
    pending.push('고객 안내 사항 동의를')
  }
  if (store.agreement.recYn !== 'Y') {
    pending.push('신청서 확인 및 녹취/서명을')
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

const onBeforeConfirmApp = async (e) => {
  // 모달 동기적 오픈을 먼저 취소
  e.preventDefault()
  e.stopPropagation()

  // 이전 단계(Customer, Product) 유효성 검사
  const isCustomerValid = store.validateCustomer(true)
  const isProductValid = store.validateProduct()

  if (!isCustomerValid) {
    showAlert('고객정보 입력이 완료되지 않았습니다.')
    return
  }
  if (!isProductValid) {
    showAlert('상품정보 입력이 완료되지 않았습니다.')
    return
  }

  // Agreement 체크박스 확인
  const isAgreeChecked =
    store.agreement.agreeCheck1 && store.agreement.agreeCheck2 && store.agreement.agreeCheck3
  if (!isAgreeChecked) {
    showAlert('고객 안내 사항에 모두 동의해주세요.')
    return
  }

  // 구비서류 유효성 체크
  if (requiredDocRef.value?.checkValidation && !(await requiredDocRef.value.checkValidation())) {
    return
  }

  // 동의가 완료되었으므로 모달 열리기 전에 임시저장 API 호출
  try {
    const success = await store.apiSaveDraft('3')
    if (success) {
      // 임시저장 성공 시 수동으로 모달 열기
      appConfirmRef.value?.open()
    } else {
      showAlert('신청서 저장 중 오류가 발생했습니다. 다시 시도해 주세요.')
    }
  } catch (error) {
    console.error('apiSaveDraft error:', error)
    showAlert('서버 통신 중 오류가 발생했습니다.')
  }
}

const onConfirmApp = (result) => {
  store.agreement.recYn = 'Y'

  // 원본 객체 통째로 스토어에 적재 (save/complete 시 통째로 전송하기 위함)
  store.rawEformsignFileData = result?.eformsignFileData || []
  store.rawRecordFileData = result?.recordFileData || null

  // eformsignFileData 적재 및 스토어 동기화 (한글 파일명 대신 물리 경로 filePathName을 fileNm에 탑재)
  const eformFile = result?.eformsignFileData?.[0]
  if (eformFile) {
    const fullPathName = eformFile.file?.filePathName || eformFile.file?.filePath || ''
    store.documentId = eformFile.documentId || ''
    store.originalfileName = fullPathName

    store.agreement.scanId = eformFile.documentId || ''
    store.agreement.recFileNm = fullPathName
    store.agreement.recFilePathNm = fullPathName
  } else {
    const fallbackId = result?.documentIds?.[0] || ''
    store.documentId = fallbackId
    store.agreement.scanId = fallbackId
  }

  // recordFileData -> msfRequestRecList 매핑
  if (result?.recordFileData) {
    const rec = result.recordFileData
    store.agreement.recFileNm = rec.fileName || ''
    store.agreement.recFilePathNm = rec.filePathName || rec.filePath || ''
    store.agreement.msfRequestRecList = [
      {
        formTypeCd: '1', // 신규/변경 신청서의 폼 타입 코드
        recFilePathNm: rec.filePathName || rec.filePath || '',
        recFileNm: rec.fileName || '',
      },
    ]
  }

  checkRequiredFields()
}

const onEditApp = () => {
  // 수정 진입 시 상품 영역의 데이터를 초기화하지 않고, 오직 재인증할 수 있도록 인증 플래그만 해제
  if (store.authFlags) {
    store.authFlags.autoAcct = false
    store.authFlags.reqCardNo = false
    store.authFlags.combId = false
    store.authFlags.reqUsimSn = false
    store.authFlags.imei = false
    store.authFlags.esimImei = false
  }
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

  // 번호이동(MNP3)인 경우 작성완료 전 최종 사전동의 결과조회 자동 실행 및 사전체크 임시저장
  if (!store.preChecked && !store.authFlags.moveAuthTypeCd && store.customer.joinType === 'MNP3') {
    const result = await store.apiCheckMnpAgreeResult()
    if (result !== true) {
      // 실패 시 작성완료에서만 특별히 우회 컨펌 노출
      const failMsg =
        typeof result === 'object' ? result.message : '번호이동 사전동의 결과조회에 실패했습니다.'
      const proceed = await showConfirmAsync(
        `${failMsg}\n\n성공으로 진행하시겠습니까?`,
        '번호이동 사전동의 결과조회 성공 처리',
      )
      if (proceed) {
        if (store.authFlags) {
          store.authFlags.moveAuthTypeCd = true
        }
        store.preChecked = true
      } else {
        isMnpFailModalOpen.value = true // 번호이동 사전동의 실패 모달 노출
        return false
      }
    }

    const saveResult = await store.apiSaveDraft(2, { requestPreCheck: 'Y' })
    if (!saveResult) {
      console.error('Final precheck save draft failed')
      return false
    }
  }

  return await store.apiCompleteApplication()
}

const validateWithAlert = () => {
  const pending = getPendingItems()
  if (pending.length > 0) {
    if (pending.includes('고객 안내 사항 동의')) {
      showAlert('고객 안내 사항에 모두 동의해주세요.')
    } else if (pending.includes('신청서 확인 및 녹취/서명')) {
      showAlert('신청서 확인 버튼을 눌러 서명 및 녹취를 진행해주세요.')
    } else {
      showAlert(`${pending[0]} 입력해 주세요.`)
    }
    return false
  }
  return true
}

const resetStep = () => {
  // 초기화 버튼 클릭 시 동의 항목 3개 및 녹취 정보 명시적 초기화
  store.agreement.agreeCheck1 = false
  store.agreement.agreeCheck2 = false
  store.agreement.agreeCheck3 = false
  store.agreement.recYn = 'N'
  store.agreement.recFileNm = ''
  store.agreement.recFilePathNm = ''
  store.agreement.msfRequestRecList = []
  store.agreement.scanId = ''
  checkRequiredFields()
}

const getCompleteData = () => {
  const c = store.customer
  const p = store.product
  const isMinor = ['NM', 'FM'].includes(c.cstmrTypeCd)

  // 1. 가입 휴대폰번호 판별 (스토어의 computed openNo 활용)
  const openNo = store.openNo || ''

  // 2. 연락처 휴대폰번호
  const contactMobile = [c.mobileNo1, c.mobileNo2, c.mobileNo3].filter(Boolean).join('-')

  return {
    // 신청서 키
    requestKey: store.applicationKey,

    // 가입 고객 이름
    name: c.cstmrNm,

    mobiles: [
      // 1번 모바일: 미성년자 시 법정대리인 정보, 성인 시 가입 고객 및 가입 번호
      {
        name: isMinor ? c.repName || c.minorAgentNm : c.cstmrNm,
        mobile: isMinor
          ? [c.minorAgentTelFnNo, c.minorAgentTelMnNo, c.minorAgentTelRnNo]
              .filter(Boolean)
              .join('-')
          : openNo,
      },
      // 2번 모바일: 가입 고객 및 연락처 휴대폰번호
      {
        name: c.cstmrNm,
        mobile: contactMobile,
      },
    ],
  }
}

const checkValidation = () => {
  if (!store.agreement.agreeCheck1) {
    showAlert(`고객 안내 사항에 모두 동의해주세요`, () => {
      agreeCheck1Ref.value?.focus()
    })
    return false
  }
  if (!store.agreement.agreeCheck2) {
    showAlert(`고객 안내 사항에 모두 동의해주세요`, () => {
      agreeCheck2Ref.value?.focus()
    })
    return false
  }
  if (!store.agreement.agreeCheck3) {
    showAlert(`고객 안내 사항에 모두 동의해주세요.`, () => {
      agreeCheck3Ref.value?.focus()
    })
    return false
  }
  if (store.agreement.recYn !== 'Y') {
    showAlert(`신청서 확인 버튼을 눌러 서명 및 녹취를 진행해주세요.`, () => {
      appConfirmRef.value?.focus()
    })
    return false
  }

  return true
}

defineExpose({
  save,
  validate,
  getPendingItems,
  validateWithAlert,
  resetStep,
  getCompleteData,
  reset: () => store.resetStep(3),
  checkValidation,
})
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
