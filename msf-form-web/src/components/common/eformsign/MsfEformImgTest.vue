<!-- MsfEformImgTest.vue -->
<template>
  <iframe
    ref="iframeRef"
    class="eformsign-frame is-img"
    src="/eformsign/embedding/MsfMulti.html"
    @load="openEformsign"
  />
</template>

<script setup>
import { onBeforeUnmount, ref } from 'vue'
import { postRaw } from '@/libs/api/msf.api.js'
import { getEformsignTemplateIds } from '@/libs/utils/eformsignTemplate.utils.js'

const EFORM_EMBEDDING_ORIGIN = window.location.origin

/**
 * 테스트할 props를 여기에 직접 설정
 *
 * formTypeCode:
 * - newchange
 * - servicechange
 * - ownerchange
 * - termination
 */
const TEST_PROPS = {
  formTypeCode: 'newchange',
  // 서비스 변경에서 신규/변경 신청서를 같이 표시할 경우 true ['newchange', 'servicechange', 'ownerchange', 'termination']
  useNewChangeTemplate: false,
  // 서비스 변경에서 보험 신청서를 표시할 경우 ios 또는 android
  deviceOs: '',
}

/**
 * 업무별 테스트 데이터
 *
 * 확인하려는 업무의 배열 안에 jsondata를 넣으면 됨.
 */
const TEST_FORM_PARAMETERS = {
  newchange: [
    {
      name: 'jsondata',
      value: JSON.stringify({
        requestKey: 2588,
        appFormReqDt: '20260625',
        cstmrName: '테스트',
        cstmrNativeRrn: '19740119',
        cstmrReceiveTelNo: '01033334444',
        cstmrAddr: '서울특별시 강남구 테헤란로 428 (대치동, 테헤란로 대우 아이빌) 1',
        cstmrTypeCd: 'I',
        gender: 'F',
        operTypeCd: 'MNP3',
        moveCompanyCd: 'SKT',
        moveMobileNo: '01012345678',
        reqModelName: 'SM-A175NK',
        reqPhoneSn: '0014777',
        reqUsimSn: '8982300517001844431',
        prodNm: 'LTE 데이터 실속 1GB/100분',
        socCodeNm: 'LTE 데이터 실속 1GB/100분',
        telecomMonthPay: '21000',
        discountProg: 'KD',
        enggMnthCnt: '24',
        modelPrice: '165000',
        modelSprt: '144000',
        deviceDiscountAmt: '144000',
        autoPayOrgNm: '국민은행',
        autoPayAcctCardNo: '043211036205',
        personalInfoCollectAgreeYn: 'Y',
        collectAllAgreeYn: 'N',
        // 신규/변경 데이터를 여기에 입력(상위 내용 예시)
      }),
    },
  ],

  servicechange: [
    {
      name: 'jsondata',
      value: JSON.stringify({
        // 서비스 변경 데이터를 여기에 입력
      }),
    },
  ],

  ownerchange: [
    {
      name: 'jsondata',
      value: JSON.stringify({
        // 명의변경 데이터를 여기에 입력
      }),
    },
  ],

  termination: [
    {
      name: 'jsondata',
      value: JSON.stringify({
        // 해지 데이터를 여기에 입력
      }),
    },
  ],

  // 서비스 변경 + iOS 보험 신청서 데이터
  ios: [
    {
      name: 'jsondata',
      value: JSON.stringify({
        // iOS 보험 데이터를 여기에 입력
      }),
    },
  ],

  // 서비스 변경 + Android 보험 신청서 데이터
  android: [
    {
      name: 'jsondata',
      value: JSON.stringify({
        // Android 보험 데이터를 여기에 입력
      }),
    },
  ],
}

const emit = defineEmits(['viewer-ready', 'load-fail'])

const iframeRef = ref(null)
const initRequested = ref(false)
const tokenData = ref(null)

const getEformToken = async () => {
  const res = await postRaw('/api/form/common/eform-api-token/get')
  return res.data?.data
}

const getValidToken = async () => {
  if (tokenData.value) {
    return tokenData.value
  }

  const token = await getEformToken()

  if (!token?.accessToken) {
    throw new Error('eformsign 토큰 정보가 없습니다.')
  }

  tokenData.value = token
  return token
}

/**
 * TEST_FORM_PARAMETERS에 입력한 데이터를 그대로 전달한다.
 *
 * MsfMulti.html에서 formTypeCode에 맞는 데이터를 선택하여 사용한다.
 */
const getTestFormParameters = () => {
  return {
    newchange: TEST_FORM_PARAMETERS.newchange || [],
    servicechange: TEST_FORM_PARAMETERS.servicechange || [],
    ownerchange: TEST_FORM_PARAMETERS.ownerchange || [],
    termination: TEST_FORM_PARAMETERS.termination || [],
    ios: TEST_FORM_PARAMETERS.ios || [],
    android: TEST_FORM_PARAMETERS.android || [],
  }
}

const validateTestConfig = () => {
  const validFormTypeCodes = ['newchange', 'servicechange', 'ownerchange', 'termination']

  if (!validFormTypeCodes.includes(TEST_PROPS.formTypeCode)) {
    throw new Error(`지원하지 않는 formTypeCode입니다: ${TEST_PROPS.formTypeCode}`)
  }

  const currentFormParameters = TEST_FORM_PARAMETERS[TEST_PROPS.formTypeCode]

  if (!Array.isArray(currentFormParameters) || currentFormParameters.length === 0) {
    throw new Error(`${TEST_PROPS.formTypeCode} 테스트 데이터가 없습니다.`)
  }
}

const openEformsign = async () => {
  if (initRequested.value) return

  const iframe = iframeRef.value

  if (!iframe?.contentWindow) {
    return
  }

  initRequested.value = true

  try {
    validateTestConfig()

    const token = await getValidToken()

    iframe.contentWindow.postMessage(
      {
        type: 'EFORMSIGN_INIT',
        payload: {
          ...token,
          envUrl: import.meta.env.VITE_EFORM_BASE_URL,
          companyId: token.companyId,
          userId: token.memberId,
          userName: token.userName,
          accessToken: token.accessToken,
          refreshToken: token.refreshToken,

          templateIds: getEformsignTemplateIds(),

          requestKey: TEST_PROPS.requestKey,
          formTypeCode: TEST_PROPS.formTypeCode,
          cstmrNm: TEST_PROPS.cstmrNm,
          phoneNo: TEST_PROPS.phoneNo,

          formParameters: getTestFormParameters(),

          useNewChangeTemplate: TEST_PROPS.useNewChangeTemplate,
          deviceOs: TEST_PROPS.deviceOs,

          fileData: [],
          documentTitleSuffix: '',
        },
      },
      EFORM_EMBEDDING_ORIGIN,
    )
  } catch (error) {
    initRequested.value = false

    console.error('[MsfEformImgTest] 초기화 실패:', error)

    emit('load-fail', {
      message:
        error?.response?.data?.message ||
        error?.response?.data?.result?.message ||
        error?.message ||
        'eformsign 초기화에 실패했습니다.',
      error,
    })
  }
}

const onMessage = (event) => {
  if (event.origin !== EFORM_EMBEDDING_ORIGIN) {
    return
  }

  const iframeWindow = iframeRef.value?.contentWindow

  if (iframeWindow && event.source !== iframeWindow) {
    return
  }

  const message = event.data

  if (!message?.type) {
    return
  }

  if (message.type === 'EFORMSIGN_HTML_READY') {
    openEformsign()
    return
  }

  if (message.type === 'EFORMSIGN_VIEWER_READY') {
    emit('viewer-ready')
  }
}

window.addEventListener('message', onMessage)

onBeforeUnmount(() => {
  window.removeEventListener('message', onMessage)
})
</script>

<style scoped>
.eformsign-frame {
  display: block;
  width: 100%;
  min-height: 700px;
  border: 0;
}
</style>
