import { defineStore } from 'pinia'
import { ref } from 'vue'
import { api } from '@/libs/api/msf.api'

export const useMsfFormTerminationStore = defineStore('msf_form_termination', () => {
  const applicationKey = ref('TEMP_' + Math.random().toString(36).substring(7))

  // Step 1: Customer Info
  const customer = ref({
    /* 고객 유형 */
    customerType: '',     // 고객유형 (NA:내국인, MI:미성년자, FO:외국인, FM:외국인미성년자, CO:법인, PB:공공기관)
    visitCustomer: '',    // 방문고객 (self:본인/대표, agent:대리인)
    /* 신분증 확인 */
    idCardScan: '',       // 신분증 스캔 종류
    licenseRegion: '',    // 면허지역 (운전면허증 선택 시)
    licenseNumber: '',    // 면허번호
    licenseExpireDate: '',// 면허 만료일
    /* 가입자 정보 */
    userName: '',         // 이름
    userBirthDate: '',    // 생년월일 (YYYYMMDD)
    userGender: '',       // 성별
    corpRegNo1: '',       // 법인등록번호 앞
    corpRegNo2: '',       // 법인등록번호 뒤
    bizNo1: '',           // 사업자등록번호1
    bizNo2: '',           // 사업자등록번호2
    bizNo3: '',           // 사업자등록번호3
    repreName: '',        // 대표자명
    cancelPhone1: '',     // 해지 휴대폰번호 앞
    cancelPhone2: '',     // 해지 휴대폰번호 중
    cancelPhone3: '',     // 해지 휴대폰번호 뒤
    /* 법정대리인 정보 (미성년자) */
    repName: '',          // 법정대리인 이름
    repBirthDate: '',     // 법정대리인 생년월일
    repRelation: '',      // 신청인과의 관계
    repPhone1: '',        // 법정대리인 연락처 앞
    repPhone2: '',        // 법정대리인 연락처 중
    repPhone3: '',        // 법정대리인 연락처 뒤
    repPhoneAuth: '',     // 인증번호 입력
    repAgree: false,      // 법정대리인 안내사항 동의
    /* 대리인 위임정보 */
    agentName: '',        // 위임받은 고객 이름
    agentBirthDate: '',   // 생년월일
    agentGender: '',      // 성별
    agentRelation: '',    // 신청인과의 관계
    agentPhone1: '',      // 연락처 앞
    agentPhone2: '',      // 연락처 중
    agentPhone3: '',      // 연락처 뒤
    /* 해지 후 연락처 */
    afterTel1: '',        // 연락 전화번호 앞
    afterTel2: '',        // 연락 전화번호 중
    afterTel3: '',        // 연락 전화번호 뒤
    postMethod: '',       // 해지 후 연락 수단 (mail:우편, email:이메일)
    /* 가입유형 선택 */
    openingDate: '',      // 개통일자
    agencyName: '',       // 대리점
    /* 연동 키 값 */
    ncn: '',              // 계약번호 (X18 조회용)
    custId: '',           // 고객ID (X18 조회용)
  })

  // Step 2: Product (해지 정산)
  const product = ref({
    /* 해지 신청 */
    isActive: '',         // 사용여부 (kt M mobile 재사용 / kt 재사용 / skt 사용 / lgt 사용 / 기타)
    /* 해지 정산 (X18 자동 조회 후 세팅) */
    usageFee: '',         // 사용요금 (원)
    penaltyFee: '',       // 위약금 (원)
    finalAmount: '',      // 최종 정산요금 (원)
    remainPeriod: '',     // 잔여분할상환 기간 (개월)
    remainAmount: '',     // 잔여분할상환 금액 (원)
    /* X18 원본 응답 보관 */
    remainChargeLoaded: false,  // X18 조회 완료 여부
    remainChargeItems: [],      // X18 요금 항목 리스트
    /* 메모 */
    memo: '',
  })

  // Step 3: Agreements
  const agreement = ref({
    agreeCheck1: false,
    agreeCheck2: false,
    agreeCheck3: false,
  })

  // 인증 버튼 완료 여부 플래그
  const authFlags = ref({
    cancelPhone: false,   // 해지 휴대폰 인증
    repPhone: false,      // 법정대리인 휴대폰 인증
  })

  // # X18 잔여요금·위약금 실시간 조회
  const apiGetRemainCharge = async () => {
    try {
      const { data } = await api.post('/api/v1/cancel/remain-charge', {
        ncn: customer.value.ncn,
        ctn: customer.value.cancelPhone1 + customer.value.cancelPhone2 + customer.value.cancelPhone3,
        custId: customer.value.custId,
      })
      if (data?.success) {
        product.value.usageFee = data.sumAmt || ''
        product.value.remainChargeItems = data.items || []
        product.value.remainChargeLoaded = true
        // 항목별 위약금 세팅 (gubun이 '위약금'인 항목)
        const penaltyItem = data.items?.find((item) => item.gubun?.includes('위약금'))
        if (penaltyItem) {
          product.value.penaltyFee = penaltyItem.payment || ''
        }
      }
      return data
    } catch (e) {
      console.error('X18 잔여요금 조회 실패', e)
      product.value.remainChargeLoaded = false
      return null
    }
  }

  // # 초기화
  const resetStep = (step) => {
    if (step === 1) {
      applicationKey.value = ''
      // TODO: customer 데이터만 초기값으로 리셋
    } else if (step === 2) {
      product.value.usageFee = ''
      product.value.penaltyFee = ''
      product.value.finalAmount = ''
      product.value.remainPeriod = ''
      product.value.remainAmount = ''
      product.value.remainChargeLoaded = false
      product.value.remainChargeItems = []
      authFlags.value.cancelPhone = false
      authFlags.value.repPhone = false
    } else if (step === 3) {
      agreement.value.agreeCheck1 = false
      agreement.value.agreeCheck2 = false
      agreement.value.agreeCheck3 = false
    }
  }

  // # 작성완료 API
  const apiCompleteApplication = async () => {
    try {
      const payload = {
        customer: customer.value,
        product: product.value,
        agreement: agreement.value,
      }
      await api.post(`/api/msf/formTermination/${applicationKey.value}/complete`, payload)
      return true
    } catch (e) {
      console.error('Completion failed', e)
      return false
    }
  }

  return {
    applicationKey,
    customer,
    product,
    agreement,
    authFlags,
    resetStep,
    apiGetRemainCharge,
    apiCompleteApplication,
  }
})
