<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="안면인증"
    autoHeight
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfBox margin="0">
      <MsfStack vertical type="formgroups">
        <MsfFormGroup label="사진 인증" required tag="div">
          <MsfSelect
            title="사진 인증 선택"
            v-model="fathRequest.identityFormCode"
            placeholder="선택해주세요."
            class="ut-w-300"
            :options="[
              { label: 'K-NOTE 신분증 스캔', value: 'I' },
              { label: '모바일 신분증', value: 'M' },
            ]"
          />
        </MsfFormGroup>
        <MsfFormGroup label="신분증" required tag="div">
          <MsfSelect
            title="신분증 선택"
            v-model="fathRequest.identityTypeCode"
            group-code="RCP2006"
            placeholder="선택해주세요."
            class="ut-w-300"
            :disabled-items="disabledIdentityCodes"
            :hidden-items="hiddenIdentityCodes"
            @change="onChangeIdentityType"
          />
        </MsfFormGroup>
        <MsfFormGroup
          label="휴대폰번호"
          required
          helpText="※ 입력하신 휴대폰번호로 안면인증 URL이 발송됩니다."
          tag="div"
        >
          <MsfStack type="field">
            <MsfMobileInput
              v-model:number1="fathRequest.phone1"
              v-model:number2="fathRequest.phone2"
              v-model:number3="fathRequest.phone3"
              @verify="onVerifyPhoneInput"
            />
          </MsfStack>
        </MsfFormGroup>
      </MsfStack>
    </MsfBox>
    <MsfButtonGroup align="center" margin="1">
      <!--
        태블릿 브라우저로 안면인증 URL을 접속하면
        안면인증 웹에서 태블릿 브라우저를 PC로 인식하여 진행 불가능
      <MsfButton :disabled="disabledBtns" @click="onClickOpenBrowserBtn">
        안면인증 URL 열기
      </MsfButton>
      -->
      <MsfButton :disabled="disabledBtns" @click="onClickSendSmsBtn">안면인증 URL 받기</MsfButton>
      <MsfButton :disabled="disabledBtns" @click="onClickShowQrBtn">안면인증 QR 생성</MsfButton>
    </MsfButtonGroup>
    <MsfBox>
      <MsfStack v-if="isConfirmResultBtn" type="formgroups">
        <MsfInput
          v-model="kNoteConfirmData.customerName"
          maxlength="100"
          class="ut-w-150"
          clearable
          placeholder="고객명 입력"
        />
        <MsfNumberInput
          v-model="kNoteConfirmData.customerNo"
          maxlength="13"
          class="ut-w-150"
          placeholder="주민번호 입력"
        />
        <MsfInput
          v-if="fathRequest.identityTypeCode === '02'"
          v-model="kNoteConfirmData.customerDriveNo"
          placeholder="운전면허번호 입력"
        />
        <MsfNumberInput
          v-model="kNoteConfirmData.customerIssueDate"
          maxlength="8"
          placeholder="신분증발급일자 입력"
        />
      </MsfStack>
    </MsfBox>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton v-if="isConfirmResultBtn" variant="primary" @click="onClickConfirmResultBtn">
          안면인증 결과확인
        </MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
  <!-- QR코드 팝업 : 별도 파일로 빼셔도 됩니다. -->
  <MsfDialog
    size="medium"
    :isOpen="isPopinPopOpen"
    title="QR코드"
    showClose
    @close="onClickCloseQrPopup"
  >
    <p class="ut-text-title3 ut-text-center">QR Code를 조회해 주세요.</p>
    <!-- qr영역 -->
    <div class="qr-wrapper">
      <div class="qr-area">
        <!-- QR 들어감 -->
        <img :src="qrImageStream" alt="QR이미지" />
      </div>
      <p class="qr-conut">
        QR 유효시간 <em class="qr-count-txt">{{ countdownFormat }}</em>
      </p>
    </div>
    <!-- // qr영역 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClickCloseQrPopup"> 취소 </MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
  <!-- // QR코드 팝업 : 별도 파일로 빼셔도 됩니다. -->
  <MsfIdCardListModal
    v-model="isIdCardListModel"
    :agent-cd="props.agentCode"
    :identity-type-code="fathRequest.identityTypeCode"
    :oper-type-cd="props.joinType"
    @confirm="onConfirmIdCardListModal"
    @close="onCloseIdCardListModal"
  />
</template>

<script setup>
import { computed, ref, reactive, shallowRef, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useCountdown } from '@vueuse/core'
import { post } from '@/libs/api/msf.api'
import { getFormTypeCode } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
/* import { exportBrowser, isProduction } from '@/libs/utils/device.utils' */
import { generateBirthDateByRrn, formatDatetime } from '@/libs/utils/date.utils'
import { isEmpty } from '@/libs/utils/string.utils'

const route = useRoute()

const props = defineProps({
  modelValue: Boolean,
  resNo: { type: String, default: '' }, // 예약등록예약번호
  agentCode: { type: String, default: '' }, // 대리점 코드
  joinType: { type: String, default: '' }, // 가입유형
  customerType: { type: String, default: '' }, // 고객유형
  visitType: { type: String, default: '' }, // 방문유형
  bizNumber: { type: String, default: '' }, // 법인 사업자번호
  minorAgentName: { type: String, default: '' }, // 법인대리인 이름
  minorAgentBirth: { type: String, default: '' }, // 법인대리인 생년월일
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

const countdownFormat = computed(
  () =>
    `${String(Math.floor(remaining.value / 60)).padStart(2, '0')}:${String(remaining.value % 60).padStart(2, '0')}`,
)

const countTime = 50
const countdown = shallowRef(countTime)
const { remaining, start, stop, reset } = useCountdown(countdown, {
  onComplete() {
    showAlert(
      '유효시간이 종료되었습니다.\n[안면인증 QR 생성] 버튼을 클릭하시면,\n QR코드가 재생성 됩니다.',
      () => {
        isConfirmResultBtn.value = false
        isPopinPopOpen.value = false
      },
    )
  },
})

const qrImageStream = ref('') // QR코드 이미지 스트림 (base64 등)

const isPopinPopOpen = ref(false)
const phoneValid = ref(false) // 휴대폰번호 입력값 검증 여부
const isConfirmResultBtn = ref(false) // 안면인증 결과확인 버튼 노출 여부 (샘플에서는 항상 false로 유지)
const isIdCardListModel = ref(false) // 신분증 목록 모달 노출 여부
const resNo = ref(props.resNo || '') // 안면인증 트랜잭션 ID (결과확인 시 필요)

// 운영이 아닌 경우에 설정하는 데이터
// const customerName = ref('')
// const customerNo = ref('')
// const customerDriveNo = ref('')
// const customerIssueDate = ref('')

const disabledItems = reactive([
  { type: 'NA', codes: ['03', '04', '05', '06'] },
  { type: 'NM', codes: ['03', '04', '05', '06'] },
  { type: 'FN', codes: ['03', '04', '05'] },
  { type: 'FM', codes: ['03', '04', '05'] },
  { type: 'JP', codes: ['03', '04', '05', '06'] },
  { type: 'GO', codes: ['03', '04', '05', '06'] },
])
const disabledIdentityCodes = computed(() => {
  let result = []
  const item = disabledItems?.find((item) => item.type === props.customerType)
  if (item) {
    result.push(...item.codes)
  }
  return result
})
const hiddenIdentityCodes = computed(() => {
  const isForeign = ['FN', 'FM'].includes(props.customerType)
  if (isForeign) {
    return ['01', '02', '03', '04', '05']
  } else {
    return ['03', '04', '05', '06']
  }
})
const disabledBtns = computed(() => {
  return (isEmpty(resNo.value) && isEmpty(fathRequest.identityTypeCode)) || !phoneValid.value
})

const fathRequest = reactive({
  identityFormCode: 'I',
  identityTypeCode: '',
  formId: '',
  phone1: '010',
  phone2: '',
  phone3: '',
})
const kNoteConfirmData = reactive({
  customerName: '', //'[고객명]',
  customerBirth: '', //'[고객생년월일]',
  customerNo: '', //'[고객 주민번호]',
  customerDriveNo: '', //'[고객 운전면허번호]',
  customerIssueDate: '', //'[신분증 발급일자]',
})

const getSendingParams = () => {
  return {
    formType: getFormTypeCode(route.path) || '',
    path: route.path,
    identityForm: fathRequest.identityFormCode,
    identityType: fathRequest.identityTypeCode,
    formId: fathRequest.formId,
    phone: `${fathRequest.phone1}${fathRequest.phone2}${fathRequest.phone3}`,
    joinType: props.joinType,
    customerType: props.customerType,
    visitType: props.visitType,
    bizNumber: props.bizNumber,
    minorAgentName:
      ['JP', 'GO'].includes(props.customerType) && props.visitType !== 'VDP'
        ? kNoteConfirmData.customerName
        : props.minorAgentName,
    minorAgentBirthday:
      ['JP', 'GO'].includes(props.customerType) && props.visitType !== 'VDP'
        ? kNoteConfirmData.customerBirth
        : props.minorAgentBirth,
    agentCode: props.agentCode,
    resNo: resNo.value,
  }
}

const callFaceAuthResult = async (data) => {
  // FIXME: 임시로 입력하는 안면인증 고객정보
  if (
    !data &&
    (!kNoteConfirmData?.customerName ||
      !kNoteConfirmData?.customerNo ||
      !kNoteConfirmData.customerIssueDate)
  ) {
    showAlert('임시 고객명, 주민등록번호, 발급일자를 입력하세요.')
    return false
  }
  const response = await post('/api/shared/form/common/faceauth/result' + (data ? '/prev' : ''), {
    formType: getFormTypeCode(route.path) || '',
    path: route.path,
    joinType: props.joinType,
    customerType: props.customerType,
    formId: fathRequest.formId,
    agentCode: props.agentCode,
    resNo: resNo.value,
    identityForm: fathRequest.identityFormCode,
    // 운영이 아닌 경우에 설정하는 파라메터
    identityType: fathRequest.identityTypeCode,
    customerName: data?.customerName || kNoteConfirmData.customerName,
    customerBirth: data?.customerBirth || kNoteConfirmData.customerBirth,
    customerNo: data?.customerNo || kNoteConfirmData.customerNo,
    customerDriveNo: data?.customerDriveNo || kNoteConfirmData.customerDriveNo,
    customerIssueDate: data?.customerIssueDate || kNoteConfirmData.customerIssueDate,
  })
  if (response.code !== '0000') {
    // 실패 처리
    return
  }
  if (response.data.resultTypeCode === 'SKIP') {
    skipFaceAuthRequest(response.data)
    return
  }
  // response.data = {
  //   isSkip: 스킵여부(true/false),
  //   transactionId: '[안면인증키]',
  //   formId: '[서식지ID]',
  //   resNo: '[resNo]',
  //   joinType: '[가입 유형]',
  //   customerType: '[고객 유형]',
  //   completeDate: '[안면인증 완료일시]',
  //   identityType: '[신분증 유형 (01: 주민등록증, 02: 운전면허증)]',
  //   customerName: '[고객명]',
  //   customerBirth: '[고객 생년월일]',
  //   customerNo : '[고객 주민번호]',
  //   customerDriveNo: '[고객 운전면허번호]',
  //   customerIssueDate: '[신분증 발급일자]',
  //   skipPosableYn: '[안면인증 스킵 가능 여부 (Y/N)]',
  //   fathTelNo: '[안면인증 진행 휴대폰번호]',
  // }
  if (data) {
    resNo.value = response.data?.resNo
    if (response.data?.transactionId && response.data?.customerName && response.data?.customerNo) {
      isConfirmResultBtn.value = true
    } else {
      isConfirmResultBtn.value = false
    }
  } else {
    closePopup(response.data)
  }
}

const closePopup = (result) => {
  if (!result) {
    emit('update:modelValue', false)
    emit('close')
  } else if (props.modelValue) {
    const fathResult = {
      isSkip: result.isSkip ? true : false,
      fathTelNo:
        !isEmpty(fathRequest.phone2) && !isEmpty(fathRequest.phone3)
          ? `${fathRequest.phone1}${fathRequest.phone2}${fathRequest.phone3}`
          : !isEmpty(result.receivedSmsTelNo)
            ? result.receivedSmsTelNo.replaceAll(/[^0-9]/g, '')
            : '',
      ...result,
    }
    emit('update:modelValue', false)
    emit('close', fathResult)
  }
  Object.assign(fathRequest, {
    identityFormCode: 'I',
    identityTypeCode: '',
    formId: '',
    phone1: '010',
    phone2: '',
    phone3: '',
  })
  Object.assign(kNoteConfirmData, {
    customerName: '', //'[고객명]',
    customerBirth: '', //'[고객 생년월일]',
    customerNo: '', //'[고객 주민번호]',
    customerDriveNo: '', //'[고객 운전면허번호]',
    customerIssueDate: '', //'[신분증 발급일자]',
  })
  qrImageStream.value = ''
  resNo.value = ''
  isConfirmResultBtn.value = false
}

// 닫힘 이벤트
const onClose = () => {
  closePopup(false)
}

const onChangeIdentityType = async (value) => {
  // 신분증 종류 변경 시 트랜잭션 초기화 및 버튼 비활성화
  const response = await post(
    '/api/shared/form/common/faceauth/confirm',
    {
      formType: getFormTypeCode(route.path) || '',
      path: route.path,
      identityType: value,
      joinType: props.joinType,
      customerType: props.customerType,
      agentCode: props.agentCode,
      resNo: props.resNo,
    },
    { skipAlert: true },
  )
  if (response.code !== '0000') {
    showAlert('선택하신 신분증은 해당 서비스에서 지원하지 않습니다.\n다른 신분증을 선택해주세요.')
    fathRequest.identityTypeCode = ''
    disabledItems.forEach((v) => {
      v.codes.push(value)
    })
    return
  }
  resNo.value = response.data.resNo
  if (fathRequest.identityFormCode === 'I') {
    isIdCardListModel.value = true
  }
}

const onConfirmIdCardListModal = async (data) => {
  if (data) {
    fathRequest.formId = data.frmpapId
    kNoteConfirmData.customerName = data.custNm
    kNoteConfirmData.customerBirth = generateBirthDateByRrn(data.realCustIdntNo)
    kNoteConfirmData.customerNo = data.realCustIdntNo
    kNoteConfirmData.customerDriveNo = data.realCustIdntNo
    kNoteConfirmData.customerIssueDate = data.realIssuDate

    if (skipFaceAuthRequest(data)) {
      return
    }
  }
  callFaceAuthResult(data)

  isIdCardListModel.value = false
}

const onCloseIdCardListModal = () => {
  if (isIdCardListModel.value) {
    stop()
    fathRequest.formId = ''
    qrImageStream.value = ''
    isIdCardListModel.value = false
  }
}

const onVerifyPhoneInput = (result) => {
  phoneValid.value = result
}

const skipFaceAuthRequest = (data) => {
  if (
    data?.fathDecideCd === 'SKIP' ||
    data.resultTypeCode === 'SKIP' ||
    data.resultCode === 'CD05'
  ) {
    showAlert(
      !kNoteConfirmData.customerName || !kNoteConfirmData.customerNo
        ? '안면인증을 종료하고 인증예외로 진행합니다.'
        : '안면인증을 종료하고 인증완료로 진행합니다.',
      () => {
        const result = {
          isSkip: true,
          transactionId: data?.transactionId,
          formId: data?.formId || fathRequest.formId,
          resNo: data?.resNo || resNo.value,
          joinType: props.joinType,
          customerType: props.customerType,
          completeDate: formatDatetime(new Date(), '', '').replace(' ', ''),
          identityType: fathRequest.identityTypeCode,
          customerName: kNoteConfirmData.customerName,
          customerBirth: kNoteConfirmData.customerBirth,
          customerNo: kNoteConfirmData.customerNo,
          customerDriveNo: kNoteConfirmData.customerDriveNo,
          customerIssueDate: kNoteConfirmData.customerIssueDate,
          skipPosableYn: 'N',
          fathTelNo:
            !isEmpty(fathRequest.phone2) && !isEmpty(fathRequest.phone3)
              ? `${fathRequest.phone1}${fathRequest.phone2}${fathRequest.phone3}`
              : '',
        }
        closePopup(result)
      },
    )
    return true
  }

  return false
}

// 태블릿 브라우저로 안면인증 URL을 접속하면
// 안면인증 웹에서 태블릿 브라우저를 PC로 인식하여 진행 불가능
// const onClickOpenBrowserBtn = async () => {
//   const response = await post('/api/shared/form/common/faceauth/url', getSendingParams())
//   if (response.code !== '0000') {
//     return false
//   }
//   if (skipFaceAuthRequest(response)) {
//     return false
//   }
//   resNo.value = response.data.resNo
//   exportBrowser(response.data.url)
//   isConfirmResultBtn.value = true
// }

const onClickSendSmsBtn = async () => {
  const response = await post('/api/shared/form/common/faceauth/sms', getSendingParams())
  if (response.code !== '0000') {
    return false
  }
  if (skipFaceAuthRequest(response.data)) {
    return false
  }
  resNo.value = response.data.resNo
  isConfirmResultBtn.value = true
}

const onClickShowQrBtn = async () => {
  const response = await post('/api/shared/form/common/faceauth/qr', getSendingParams())
  if (response.code !== '0000') {
    return false
  }
  if (skipFaceAuthRequest(response.data)) {
    return false
  }
  resNo.value = response.data.resNo
  qrImageStream.value = `data:image/png;base64,${response.data.qr}`
  isPopinPopOpen.value = true
  isConfirmResultBtn.value = true
  countdown.value = response.data.seconds ? response.data.seconds : countTime
  reset(countdown.value)
  start()
}

const onClickCloseQrPopup = () => {
  stop()
  qrImageStream.value = ''
  isPopinPopOpen.value = false
}

const onClickConfirmResultBtn = async () => {
  callFaceAuthResult()
}

watch(
  () => props.resNo,
  (newVal) => {
    if (newVal) {
      resNo.value = newVal
    }
  },
  { immediate: true },
)
</script>

<style lang="scss" scoped>
.doc-list-wrap {
  width: 100%;
  margin-top: rem(16px);
  border-top: var(--border-width-base) solid var(--color-gray-150);
  border-bottom: var(--border-width-base) solid var(--color-gray-75);
  .doc-list {
    & > li {
      padding-block: rem(16px);
      padding-inline: rem(24px);
      @include flex($v: center, $h: space-between) {
        gap: rem(16px);
      }
      border-top: var(--border-width-base) solid var(--color-gray-75);
      & > p:first-child {
        flex: 1;
        @include flex($v: center, $w: wrap) {
          gap: rem(4px);
        }
      }
      & > :last-child {
        flex-shrink: 0;
        flex-grow: 0;
      }
    }
  }
}
</style>
