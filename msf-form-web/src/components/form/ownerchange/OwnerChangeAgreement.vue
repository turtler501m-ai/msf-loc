<template>
  <div class="page-step-panel">
    <!-- 구비서류 -->
    <MsfRequiredDoc
      v-model="formData.te_customer"
      ref="requiredDocRef"
      v-model:authFlags="store.authFlags"
    />
    <!-- // 구비서류 -->
    <!-- 고객 안내 사항 -->
    <MsfTitleArea title="고객 안내 사항" />
    <p class="ut-text-desc">다음 사항을 고객님께 설명하고 서명을 받아주세요.</p>
    <MsfBox>
      <ul class="agree-list">
        <li>
          <p class="agree-tit">※ 개통정보 녹음거부 동의</p>
          <MsfCheckbox
            ref="agreeCheck1Ref"
            v-model="formData.agreement.agreeCheck1"
            label="개통 정보에 대해 모두 확인하였고, 신청내용에 이의가 없으며 더 이상의 설명을 거부합니다."
          />
        </li>
        <li>
          <p class="agree-tit">※ 명의 대여 위험 안내</p>
          <MsfCheckbox
            ref="agreeCheck2Ref"
            v-model="formData.agreement.agreeCheck2"
            label="요금 상품 및 부가 서비스를 고객의사와 상관없이 무단 가입 및 의무기간을 설정하지 않으며 공시지원금 및 요금할인(선택약정)은 개통시점 공시된 기준으로 지급되며, 이에 동의합니다. 신청자 본인은 대출업자 등 제3자에게 고객명의의 휴대폰을 개통해 주거나, 개통에 필요한 신청서류를 제공하는 경우 휴대폰 대출 사기 등에 악용되어 심각한 경제적 피해를 입을 수 있음을 안내 받았으며, 아울러 신규계약서 내용, 뒷면의 이동전화 서비스 이용약관 및 유의사항, 관련 약관의 중요내용에 대한 명시와 설명을 듣고 이에 동의하여 개인정보 처리방침, 이용약관, 위치기반 서비스/위치정보사업/통신과금 서비스/본인확인서비스 이용약관에 따라 위와 같이 신규계약을 체결합니다."
          />
        </li>
        <li>
          <p class="agree-tit">※ 통신범죄예방 안내</p>
          <MsfCheckbox
            ref="agreeCheck3Ref"
            v-model="formData.agreement.agreeCheck3"
            label="타인으로부터 전화나 문자 요청을 받고 그에 따라 휴대폰 또는 USIM 개통을 하지 않았음을 확인합니다. (전기통신사업법 등 관련 법률에 따라 형사처벌을 받을 수 있습니다.)"
          />
        </li>
        <li>
          <p class="agree-tit">※ 명의도용방지 서비스(M-Safer) 안내</p>
          <MsfCheckbox
            ref="agreeCheck4Ref"
            v-model="formData.agreement.agreeCheck4"
            label="명의도용방지서비스(M-Safer)는 본인명의 통신서비스 가입 현황을 실시간 조회 가능하며, 본인동의 없이 통신서비스에 가입하지 못하도록 차단서비스를 제공합니다.(이용 및 신청방법 : 홈페이지 또는 PASS앱 제공)"
          />
        </li>
        <li>
          <p class="agree-tit">※ 판매자 확인 안내</p>
          <MsfCheckbox
            ref="agreeCheck5Ref"
            v-model="formData.agreement.agreeCheck5"
            label="고객 보호를 위해서 통신상품 가입 시 본인확인 및 가입의사, 추가이용에 대해서 성실히 안내 하였습니다."
          >
            <template #label-prepend><em class="accent-mark">[판매자]</em></template>
          </MsfCheckbox>
        </li>
        <li>
          <p class="agree-tit">※ 가입자 확인 안내</p>
          <MsfCheckbox
            ref="agreeCheck6Ref"
            v-model="formData.agreement.agreeCheck6"
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
      @confirm="onConfirmApp"
      @click="onBeforeConfirmApp"
      @edit="onEditApp"
      :guideText="guideText"
      v-bind="currentSignData"
    />
    <!-- // 신청서 확인 -->

    <!-- 녹취 및 서명 진행 완료 표시 영역 -->
    <MsfBox class="ut-mt-20" v-if="formData.value?.recYn === 'Y'">
      <p class="ut-text-center ut-color-primary ut-font-bold">
        ✅ 신청서 확인 및 녹취/서명이 완료되었습니다.
      </p>
    </MsfBox>
  </div>
</template>

<script setup>
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList, getCommonCodeListWithDetail } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { formatDate } from '@/libs/utils/date.utils'
import { concatStrings, extractYYYYMMDDRrn } from '@/libs/utils/string.utils'
import { useMsfFormOwnChgStore } from '@/stores/msf_ownerChange'
import { storeToRefs } from 'pinia'
import { ref, watch, nextTick, onMounted, computed } from 'vue'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const isComplete = ref(false)

const agreeCheck1Ref = ref(null)
const agreeCheck2Ref = ref(null)
const agreeCheck3Ref = ref(null)
const agreeCheck4Ref = ref(null)
const agreeCheck5Ref = ref(null)
const agreeCheck6Ref = ref(null)
const appConfirmRef = ref(null)
const requiredDocRef = ref(null)

const store = useMsfFormOwnChgStore()
const { formData, applicationKey } = storeToRefs(store)
const guideText =
  '신청서 확인을 눌러서, 신청서 작성 내용을 확인하신 후 가입자(대리인) 서명을 등록해 주세요.'

const checkRequiredFields = () => {
  const isReady = validate()
  emit('complete', isReady)
}

const currentPlanUse = computed(() => formData.value.planInfo.planSelectType === 'CURRENT')
const othersPaymentYn = computed(() => formData.value.productPayment.othersPaymentYn === 'Y')
const isCard = computed(() => ['C'].includes(formData.value.productPayment.reqPayTypeCd))
const isTeMinor = computed(() => ['NM', 'FM'].includes(formData.value.te_customer.cstmrTypeCd))
const isTeForeigner = computed(() => ['FN', 'FM'].includes(formData.value.te_customer.cstmrTypeCd))
const isDelegate = computed(
  () =>
    ['GO', 'JP'].includes(formData.value.te_customer.cstmrTypeCd) &&
    formData.value.te_customer.cstmrVisitTypeCd === 'VDP',
)
const isGovernment = computed(() => ['GO', 'JP'].includes(formData.value.te_customer.cstmrTypeCd))

const getJuhuTerm = (code) => {
  switch (code) {
    case 'LOTTE':
      return { clausePriOfferYnLotte: 'Y' } // ㈜롯데멤버스
    case 'MI':
      return { clausePriOfferYnMi: 'Y' } // ㈜밀리의서재(밀리의서재)
    case 'WHO':
      return { clausePriOfferYnWho: 'Y' } // ㈜브이피(후후), ㈜메리츠화재
    case 'CU':
      return { clausePriOfferYnCu: 'Y' } // ㈜비즈에프리테일(CU)
    case 'STORY':
      return { clausePriOfferYnStory: 'Y' } // ㈜스토리위즈(블라이스)
    case 'WC':
      return { clausePriOfferYnWc: 'Y' } // ㈜왓차(왓차)
    case 'JINI':
      return { clausePriOfferYnJini: 'Y' } // ㈜지니뮤직(지니뮤직)
    case 'ML':
      return { clausePriOfferYnMl: 'Y' } // ㈜카카오엔터테인먼트(멜론)
    case 'ALPHA':
      return { clausePriOfferYnAlpha: 'Y' } // ㈜케이티알파
    case 'WV':
      return { clausePriOfferYnWv: 'Y' } // 콘텐츠웨이브주식회사(Wavve)
    default:
      return {} // 매칭되는 브랜드가 없을 경우
  }
}

const disPrmtAmt = ref(null)
const bankAccountCdList = ref([])
const bankCardCdList = ref([])
const countryList = ref([])
const juRelCdList = ref([])
const relCdList = ref([])

const currentSignData = computed(() => {
  const operTypeCd = 'ICN3'
  const eformDisPrmtAmt = disPrmtAmt.value ?? 0
  const tr = formData.value.tr_customer
  const te = formData.value.te_customer
  const pl = formData.value.planInfo
  const us = formData.value.usimInfo
  const pay = formData.value.productPayment
  const ju = formData.value.juridical
  const serviceTypeCd =
    us.simPurchaseMethod === 'B' ? 'PO' : us.simPurchaseMethod === 'R' ? 'PP' : null
  const rrnFirst = isTeForeigner.value ? te.cstmrForeignerRrn2?.at() : te.cstmrNativeRrn2?.at()
  const minorRrnFirst = isTeForeigner.value ? te.repForeignerNo2?.at() : te.repRegistrationNo2?.at()
  const gender = ['1', '3', '5', '7'].includes(rrnFirst) ? 'M' : 'F'
  const minorGender = ['1', '3', '5', '7'].includes(rrnFirst || minorRrnFirst) ? 'M' : 'F'
  const reqPayTypeCd = 'D' // D : 자동납부, 0 : 통합청구, O : 타인납부동의
  const reqPayType = pay.reqPayTypeCd // D : 자동이체, C : 신용카드, R : 지로
  const accountTitle = bankAccountCdList.value?.find((c) => c.code === pay.reqBankCd)?.title || ''
  const juMinorAgentRelTypeCd =
    juRelCdList.value?.find((c) => c.code === ju.minorAgentRelTypeCd)?.title || ''
  const cardTitle =
    bankCardCdList.value?.find((c) => c.detail?.etcValue1 === pay.reqCardCompanyCd)?.title || ''
  const countryTitle = countryList.value?.find((c) => c.code === te.country)?.title || ''
  const transcriptionScriptData = {
    requestKey: '',
    tgtType: 'icn3',
    reqBuyTypeCd: '', // 상품구분 (MM | UU)
    formTypeCd: '3',
    cstmrVisitTypeCd: te.cstmrVisitTypeCd || 'VMY', // 방문유형 (VMY 값만 본인방문, 그 외 코드는 모두 대리인/미성년자로 처리)
    rateYn: pl.planSelectType === 'CHANGE' ? 'Y' : 'N', // 요금제 선택 여부 (Y | N)
    cntpntCdNm: te.cntpntCdNm,
    userNm: te.userNm,
    nflCustNm: te.cstmrNm,
    mobilePriceNm: pl.planNm,
    mobileMntcntAmtFee: pl.planAmt,
  }
  const juhuTerm = getJuhuTerm(pl.jehuProdTypeCd)

  let cstmrTypeCd = 'I'

  if (['JP'].includes(te.cstmrTypeCd)) {
    cstmrTypeCd = 'B'
  } else if (['GO'].includes(te.cstmrTypeCd)) {
    cstmrTypeCd = 'E'
  } else if (te.cstmrJuridicalBizNo1) {
    cstmrTypeCd = 'O'
  }

  let othersPaymentNm = null
  let othersPaymentRelation = null
  let othersPaymentRrn = null

  const REL_TYPE_MAP = {
    '01': '부',
    '02': '모',
    '03': '후견인',
    '04': '연대보증인',
    '05': '가족',
    '06': '친척',
    '07': '친구',
    '08': '회사동료',
    10: '그외',
    11: '한정위탁',
    12: '지정위탁',
  }

  const relTypeCode = (code) => REL_TYPE_MAP[code] ?? null

  if (othersPaymentYn.value) {
    othersPaymentNm = isCard.value ? pay.reqCardNm : pay.reqAccountNm
    othersPaymentRelation = relTypeCode(pay.reqAccountRelTypeCd)
    othersPaymentRrn = isCard.value ? pay.reqCardRrn : pay.reqAccountRrn
  }

  return {
    formTypeCode: 'ownerchange',
    requestKey: applicationKey.value,
    cstmrNm: te.cstmrNm,
    phoneNo: concatStrings([te.mobileNo1, te.mobileNo2, te.mobileNo3]),
    transcriptionScriptData,
    formParameters: {
      operTypeCd,
      serviceTypeCd,
      cstmrTypeCd,
      agentCd: te.shopNm + '/' + te.shopCd,
      agentCd2: te.shopNm + '/' + te.shopCd,
      agentCd3: te.shopNm + '/' + te.shopCd,
      telnum: te.telephone,
      telnum2: te.telephone,
      telnum3: te.telephone,
      socCode: currentPlanUse.value ? pl.orgProdNm : pl.planNm,
      socCode3: currentPlanUse.value ? pl.orgProdNm : pl.planNm,
      monthFeeVat: currentPlanUse.value ? pl.orgPlanAmt : pl.planAmt,
      monthFeeVat3: currentPlanUse.value ? pl.orgPlanAmt : pl.planAmt,
      telecomMonthPay: currentPlanUse.value
        ? +pl.orgPlanAmt + +eformDisPrmtAmt
        : +pl.planAmt + +eformDisPrmtAmt,
      telecomMonthPay3: currentPlanUse.value
        ? +pl.orgPlanAmt + +eformDisPrmtAmt
        : +pl.planAmt + +eformDisPrmtAmt,
      baseMonthPay: currentPlanUse.value
        ? +pl.orgPlanAmt + +eformDisPrmtAmt
        : +pl.planAmt + +eformDisPrmtAmt, // 월기본납부액
      baseMonthPay3: currentPlanUse.value
        ? +pl.orgPlanAmt + +eformDisPrmtAmt
        : +pl.planAmt + +eformDisPrmtAmt, // 월기본납부액
      monthFeeDiscountAmt: Math.abs(eformDisPrmtAmt), // 월요금할인액
      planDiscountMnthAmt3: Math.abs(eformDisPrmtAmt), // 월요금할인(무선데이터표준계약서)
      // planDiscountAmt3: 50000, // 월약정할인
      ...juhuTerm, // 제휴 약관
      cstmrName: te.cstmrNm, // 고객명
      cstmrNativeRrn: isTeForeigner.value
        ? extractYYYYMMDDRrn(concatStrings([te.cstmrForeignerRrn1, te.cstmrForeignerRrn2]))
        : isGovernment.value
          ? concatStrings([te.cstmrJuridicalRrn1, te.cstmrJuridicalRrn2])
          : extractYYYYMMDDRrn(concatStrings([te.cstmrNativeRrn1, te.cstmrNativeRrn2])),
      gender: !isGovernment.value ? gender : null, // 고객성별
      cstmrForeignerRrn: isTeForeigner.value
        ? concatStrings([te.cstmrForeignerRrn1, te.cstmrForeignerRrn2])
        : null, // 외국인등록번호
      cstmrForeignerNation: isTeForeigner.value ? countryTitle : null, // 국가
      // cstmrForeignerPn: 'M12345678',
      cstmrReceiveTelNo: concatStrings([te.mobileNo1, te.mobileNo2, te.mobileNo3]),
      cstmrAddr: concatStrings([te.address, te.detailAddress], ' '),
      cstmrBillSendCode: pay.cstmrBillSendTypeCd, // MB : MMS, CB : 이메일, LX : 지로
      cstmrMail: concatStrings([te.emailAddr1, te.emailAddr2], '@'),
      // selfCertType: 'C',
      cstmrForeignerSdate: te.cstmrForeignerVdateStartDate?.replaceAll(/[^0-9]/g, ''), // 체류기간 시작일
      cstmrForeignerEdate: te.cstmrForeignerVdateEndDate?.replaceAll(/[^0-9]/g, ''), // 체류기간 종료일
      reqPayTypeCd: pay?.othersPaymentYn === 'Y' ? 'O' : 'D', // D : 자동납부, 0 : 통합청구, O : 타인납부동의
      reqPayType, // D : 자동이체, C : 신용카드, R : 지로
      autoPayOrgNm: isCard.value ? cardTitle : accountTitle,
      autoPayAcctCardNo: isCard.value ? pay.reqCardNo : pay.reqAccountNo,
      autoPayCardExp: isCard.value ? '20' + pay.reqCardYy + pay.reqCardMm : null,
      othersPaymentNm,
      othersPaymentRelation,
      othersPaymentRrn,
      reqUsimName: us.reqUsimNm,
      authInfo: te.knoteScanId || te.fathTransacId,
      // authInfo: 'MIS0202202607151029568795375',
      // additionNm:
      //   'M LTE 데이터 512MB,M 요금할인 9000 ,M기본료할인2000,발신번호표시,스팸차단서비스,정보제공사업자번호차단,통합사서함',
      usimKindsCd: us.simTypeCd === 'ESIM' ? '09' : 'UU', // UU : USIM ,09 : eSIM
      // reqUsimName: 'NFC-7100',
      reqUsimSn: us.reqUsimSn,
      usimPriceType: us.hasSim || us.simTypeCd === 'ESIM' ? 'N' : us.simPurchaseMethod, // N : 비구매, B : 후청구, I : 선납
      usimPrice: us.usimPrice,
      trnsNm: tr.cstmrNm,
      trnsMobileNo: concatStrings([tr.deviceChgTel1, tr.deviceChgTel2, tr.deviceChgTel3]),
      // cstmrNativeBirth: extractYYYYMMDDRrn(
      //   concatStrings([tr.cstmrNativeRrn1, tr.cstmrNativeRrn2]),
      // ),
      cstmrNativeBirth: tr.userBirthDate,
      trnsGender: tr.userGender,
      appFormReqDt: formatDate(new Date(), ''),
      minorAgentNm: isTeMinor.value ? te.minorAgentNm : null, // 법정대리인 성명
      minorAgentRelTypeCd: te.minorAgentRelTypeCd, // 법정대리인 관계
      minorAgentRrn: isTeMinor.value
        ? isTeForeigner.value
          ? extractYYYYMMDDRrn(concatStrings([te.repForeignerNo1, te.repForeignerNo2]))
          : extractYYYYMMDDRrn(concatStrings([te.repRegistrationNo1, te.repRegistrationNo2]))
        : null,
      minorAgentGender: isTeMinor.value ? minorGender : null,
      minorAgentTelNo: isTeMinor.value
        ? concatStrings([te.minorAgentTelFnNo, te.minorAgentTelMnNo, te.minorAgentTelRnNo])
        : null,
      // 위임장 관련
      minorDelegator: isDelegate.value ? te.cstmrNm : null,
      jrdclAgentNm: isDelegate.value ? ju.minorAgentNm : null,
      minorAgent: isDelegate.value ? ju.minorAgentNm : null,
      minorAgentRelTypeCd2: isDelegate.value ? juMinorAgentRelTypeCd : null,
      minorAgentRrn2: isDelegate.value ? ju.agentBirthDate : null,
      minorAgentGender2: isDelegate.value ? ju.agentGender : null,
      minorAgentTelNo2: isDelegate.value
        ? concatStrings([ju.minorAgentTelFnNo, ju.minorAgentTelMnNo, ju.minorAgentTelRnNo])
        : null,
      // 위임장 관련
      gdnFormReqDt: formatDate(new Date(), ''),
      enggReqDt: formatDate(new Date(), ''),
      enggSignYn: 'Y',
      androidReqDt: formatDate(new Date(), ''),
      iosReqDt: formatDate(new Date(), ''),
      androidCstmrNativeBirth: isTeForeigner.value
        ? extractYYYYMMDDRrn(
            concatStrings([
              formData.value.te_customer.cstmrForeignerRrn1,
              formData.value.te_customer.cstmrForeignerRrn2,
            ]),
          )
        : extractYYYYMMDDRrn(
            concatStrings([
              formData.value.te_customer.cstmrNativeRrn1,
              formData.value.te_customer.cstmrNativeRrn2,
            ]),
          ), // 신청인 생년월일
      iosCstmrNativeBirth: isTeForeigner.value
        ? extractYYYYMMDDRrn(
            concatStrings([
              formData.value.te_customer.cstmrForeignerRrn1,
              formData.value.te_customer.cstmrForeignerRrn2,
            ]),
          )
        : extractYYYYMMDDRrn(
            concatStrings([
              formData.value.te_customer.cstmrNativeRrn1,
              formData.value.te_customer.cstmrNativeRrn2,
            ]),
          ), // 신청인 생년월일,
      // telnum2: '01011112222',
      saleManagerNm: te.managerNm,
      /* 무선서비스 계약 표준 관련 */
      saleManagerNm3: te.managerNm,
      mobileNo3: concatStrings([tr.deviceChgTel1, tr.deviceChgTel2, tr.deviceChgTel3]),
      cstmrName3: te.cstmrNm,
      /* 무선서비스 계약 표준 관련 */
      // 약관관련
      appBlckAgrmYn: isTeMinor.value ? formData.value.appBlckAgrmYn : null, // 청소년유해매체차단동의여부(미성년자)
      blckAppDivCd: isTeMinor.value ? formData.value.nwBlckAgrmYn : null, // 네트워크차단동의여부 (미성년자)
      clause5GCoverageYn: formData.value?.clause5gCoverageYn ?? 'N', //
      clausePriTrustYn: formData.value.clausePriTrustYn ?? 'N', // 위탁동의 여부
      personalInfoCollectAgreeYn: formData.value.personalInfoCollectAgreeYn ?? 'N', // 고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의
      othersTrnsAllAgreeYn: formData.value.othersTrnsAllAgreeYn ?? 'N',
      othersTrnsAgreeYn: formData.value.othersTrnsAllAgreeYn ?? 'N',
      othersTrnsKtAgreeYn: formData.value.othersTrnsAllAgreeYn ?? 'N',
      othersAdReceiveAgreeYn: formData.value.othersAdReceiveAgreeYn ?? 'N',
      indvLocaPrvAgreeYn: formData.value.indvLocaPrvAgreeYn ?? 'N', // 개인위치정보제공
      collectAllAgreeYn: formData.value.othersTrnsAllAgreeYn ?? 'N', // 혜택제공 전체선택
      clauseEssCollectYn: formData.value.termsAgreed ? 'Y' : null, // 고유식별정보처리 (필수)
      clauseConfidenceYn: formData.value.termsAgreed ? 'Y' : null, // 개인정보/신용정보 수집동의 (필수)
      clausePriOfferYn: formData.value.termsAgreed ? 'Y' : null, // 개인정보 제3자 제공 동의
      clauseSensiCollectYn: formData.value.termsAgreed ? 'Y' : null, // 민감정보(생체인식정보) 수집 및 이용 동의
      clauseSensiOfferYn: formData.value.termsAgreed ? 'Y' : null, // 민감정보(생체인식정보) 조회 및 이용 / 3자 제공에 대한 동의(필수)
      clauseJehuYn: formData.value.planInfo?.jehuProdTypeCd ? 'Y' : null, // 개인정보 제3자 제공 동의
      clauseConfidenceYn2: formData.value.termsAgreed ? 'Y' : null, // 약관신용정보이용동의여부(필수)
      clausePriOfferYn2: formData.value.termsAgreed ? 'Y' : null, // 약관개인정보제공동의여부(필수)
      minorAgentAgrmYn: isTeMinor.value ? 'Y' : 'N', // 미성년자법정대리인안내사항및동의여부
      othersPaymentAgrYn: othersPaymentYn.value ? 'Y' : 'N', // 타인납부동의 여부
    },
  }
})

const onBeforeConfirmApp = async (e) => {
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

  e.preventDefault()
  e.stopPropagation()

  const flag = store.checkFieldValidation('PRODUCT')

  if (!flag) {
    return
  }

  const isTrMinor = computed(() => ['NM', 'FM'].includes(formData.value.tr_customer.cstmrTypeCd))
  const isTeMinor = computed(() => ['NM', 'FM'].includes(formData.value.te_customer.cstmrTypeCd))

  if (!store.trAuthFlags?.deviceChgTel) {
    showAlert('양도인 본인 인증을 완료해주세요.')
    return
  }

  if (isTrMinor.value && !store.trAuthFlags?.repPhone) {
    showAlert('양도인 법정대리인 휴대폰 인증을 완료해주세요.')
    return
  }

  // 인증 예외는 나중에 수정 필요
  if (
    formData.value.te_customer.identityCertTypeCd !== 'S' &&
    !(formData.value.te_customer.knoteScanId || formData.value.te_customer.fathTransacId)
  ) {
    showAlert('양수인 신분증 인증을 완료해주세요.')
    return
  }

  if (isTeMinor.value && !store.authFlags?.repPhone) {
    showAlert('양수인 법정대리인 휴대폰 인증을 완료해주세요.')
    return
  }

  if (!formData.value.usimInfo?.hasSim && !store.authFlags?.reqUsimSn) {
    showAlert('USIM 인증을 완료해주세요.')
    return
  }

  if (!(store.authFlags?.autoAcct || store.authFlags?.reqCardNo)) {
    showAlert('카드 및 계좌 인증을 완료해주세요.')
    return
  }

  // Agreement 체크박스 확인
  const isAgreeChecked =
    formData.value.agreement.agreeCheck1 &&
    formData.value.agreement.agreeCheck2 &&
    formData.value.agreement.agreeCheck3 &&
    formData.value.agreement.agreeCheck4 &&
    formData.value.agreement.agreeCheck5 &&
    formData.value.agreement.agreeCheck6
  if (!isAgreeChecked) {
    showAlert('고객 안내 사항에 모두 동의해주세요.')
    return
  }

  // 구비서류 유효성 체크
  if (requiredDocRef.value?.checkValidation && !(await requiredDocRef.value.checkValidation())) {
    return
  }

  await getDisPrmtAmt()

  appConfirmRef.value?.open()

  await nextTick()
}

const onConfirmApp = (result) => {
  console.log('신청서 팝업 확인 완료 - 녹취/서명 저장 처리')
  console.log(result)

  // 팝업에서 "확인"을 누르면 녹취/서명이 완료된 것으로 간주
  store.documentId = result?.documentIds ?? []
  formData.value.fileInfo.recYn = 'Y'
  formData.value.fileInfo.recFileNm = result?.recordFileData?.fileName
  formData.value.fileInfo.recFilePathNm = result?.recordFileData?.filePath
  formData.value.fileInfo.fileNm = result?.eformsignFileData[0]?.file?.filePath
  formData.value.fileInfo.fileMaskNm = result?.eformsignFileData[0]?.file?.fileName

  checkRequiredFields()
}

const onEditApp = () => {
  store.modifyProductStep()
}

const getCompleteData = () => {
  const isMinor = ['NM', 'FM'].includes(formData.value.te_customer.cstmrTypeCd)
  const firstName = isMinor
    ? formData.value.te_customer.repName
    : formData.value.te_customer.cstmrNm
  const firstMoblie = isMinor
    ? concatStrings(
        [
          formData.value.te_customer.minorAgentTelFnNo,
          formData.value.te_customer.minorAgentTelMnNo,
          formData.value.te_customer.minorAgentTelRnNo,
        ],
        '-',
      )
    : concatStrings(
        [
          formData.value.te_customer.deviceChgTel1,
          formData.value.te_customer.deviceChgTel2,
          formData.value.te_customer.deviceChgTel3,
        ],
        '-',
      )
  const secondName = formData.value.te_customer.cstmrNm
  const secondMobile = concatStrings(
    [
      formData.value.te_customer.mobileNo1,
      formData.value.te_customer.mobileNo2,
      formData.value.te_customer.mobileNo3,
    ],
    '-',
  )

  return {
    // 신청서 키
    requestKey: applicationKey.value,

    // 신규/변경, 서비스변경 메뉴일 경우, 가입 고객 이름
    // 명의변경 메뉴일 경우, 양수인 이름
    // 서비스해지 메뉴일 경우, 해지 고객 이름
    name: formData.value.te_customer.cstmrNm,

    mobiles: [
      // 고객이 미성년자일 경우 법정대리인 이름과 법정대리인 휴대폰번호
      // 고객이 미성년자가 아니고 신규/변경, 서비스변경 메뉴일 경우, 가입 고객 이름과 가입 휴대폰번호
      // 고객이 미성년자가 아니고 명의변경 메뉴일 경우, 양수인 이름과 양수받을 휴대폰번호
      // 고객이 미성년자가 아니고 서비스해지 메뉴일 경우, 해지 고객 이름과 해지 휴대폰번호
      { name: firstName, mobile: firstMoblie },

      // 신규/변경, 서비스변경 메뉴일 경우, 고객 이름과 가입자 연락처의 휴대폰번호
      // 명의변경 메뉴일 경우, 양수인 이름과 고객(양수고객) 연락처의 휴대폰번호
      // 서비스해지 메뉴일 경우, 해지 고객 이름과 해지 후 연락처의 연락 전화번호
      { name: secondName, mobile: secondMobile },
    ],
  }
}

const validate = () => {
  const isAgreeChecked = computed(() => {
    const ag = formData.value.agreement
    return [
      ag.agreeCheck1,
      ag.agreeCheck2,
      ag.agreeCheck3,
      ag.agreeCheck4,
      ag.agreeCheck5,
      ag.agreeCheck6,
    ].every(Boolean) // 모든 값이 true인지 확인
  })

  // 2. 녹취 완료 여부 (computed로 변경)
  const isRecCompleted = computed(() => {
    const f = formData.value.fileInfo

    return f.recYn === 'Y' && !!f.recFileNm && !!f.recFilePathNm && !!store.documentId?.[0]
  })

  // 3. 최종 검증 결과 (computed)
  const isAllValidated = computed(() => isAgreeChecked.value && isRecCompleted.value)

  // 만약 return이 필요하다면
  return isAllValidated.value
}

const getDisPrmtAmt = async () => {
  try {
    const res = await post('/api/form/owner-change/form/prmtAmt/get', {
      rateCd: formData.value.planInfo.planName2,
      agentCd: formData.value.te_customer.agentCd,
    })

    disPrmtAmt.value = res.data
  } catch (e) {
    console.error('Failed to fetch plan categories:', e)
  }
}

// 동의 항목 및 녹취 상태 변경 감시
watch(
  () => [
    formData.value.agreement.agreeCheck1,
    formData.value.agreement.agreeCheck2,
    formData.value.agreement.agreeCheck3,
    formData.value.agreement.agreeCheck4,
    formData.value.agreement.agreeCheck5,
    formData.value.agreement.agreeCheck6,
    formData.value.fileInfo.recYn,
  ],
  () => {
    checkRequiredFields()
  },
  { deep: true },
)

onMounted(async () => {
  store.validateAgreement = validate
  bankAccountCdList.value = await getCommonCodeList('BNK')
  bankCardCdList.value = await getCommonCodeListWithDetail('CRD')
  countryList.value = await getCommonCodeList('NATIONLIST')
  juRelCdList.value = await getCommonCodeList('RCP0021')
  checkRequiredFields()
})

const save = async () => {
  if (!validateWithAlert()) {
    return
  }

  //  최종 데이터 저장
  console.log('================save=================')
  console.log(store.buildCompletePayload())

  const result = await store.apiCompleteApplication()
  // console.log('[명의변경][동의정보저장] 화면 데이터 반영 결과', { result })
  return result
}

const validateWithAlert = () => store.checkFieldValidation('AGREE')

const reset = async () => {
  store.resetAgreement()
  await nextTick()
  checkRequiredFields()
}

const checkValidation = () => {
  if (!formData.value.agreement.agreeCheck1) {
    showAlert(`고객 안내 사항에 모두 동의해주세요`, () => {
      agreeCheck1Ref.value?.focus()
    })
    return false
  }
  if (!formData.value.agreement.agreeCheck2) {
    showAlert(`고객 안내 사항에 모두 동의해주세요`, () => {
      agreeCheck2Ref.value?.focus()
    })
    return false
  }
  if (!formData.value.agreement.agreeCheck3) {
    showAlert(`고객 안내 사항에 모두 동의해주세요.`, () => {
      agreeCheck3Ref.value?.focus()
    })
    return false
  }
  if (!formData.value.agreement.agreeCheck4) {
    showAlert(`고객 안내 사항에 모두 동의해주세요.`, () => {
      agreeCheck4Ref.value?.focus()
    })
    return false
  }
  if (!formData.value.agreement.agreeCheck5) {
    showAlert(`고객 안내 사항에 모두 동의해주세요.`, () => {
      agreeCheck5Ref.value?.focus()
    })
    return false
  }
  if (!formData.value.agreement.agreeCheck6) {
    showAlert(`고객 안내 사항에 모두 동의해주세요.`, () => {
      agreeCheck6Ref.value?.focus()
    })
    return false
  }
  if (
    formData.value.fileInfo.recYn !== 'Y' &&
    !!formData.value.fileInfo.recFileNm &&
    !!formData.value.fileInfo.recFilePathNm &&
    !!store.documentId?.[0]
  ) {
    showAlert(guideText, () => {
      appConfirmRef.value?.focus()
    })
    return false
  }

  return true
}

defineExpose({ save, reset, getCompleteData, validateWithAlert, checkValidation })
</script>

<style scoped></style>
