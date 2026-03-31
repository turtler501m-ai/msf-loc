import { defineStore } from 'pinia'

/**
 * 서비스해지 신청서 폼 상태
 * 서비스해지 플로우용 (서비스변경과 동일 패턴)
 */
export const useCancelFormStore = defineStore('cancelForm', {
  state: () => ({
    /** 가입유형 선택 (S104050101) - 대리점 */
    agencyCode: '',
    /** 고객 정보 (S104010101) */
    customerForm: {
      custType: '',
      visitType: '',
      name: '',
      birthDate: '',
      corporateNo: '',
      phoneAuthCompleted: false,
      phone: '',
      email: '',
      emailDomain: '',
      post: '',
      address: '',
      addressDtl: '',
      minorAgent: { name: '', relation: '', phone: '', agree: false },
      agentInfo: {},
    },
    /** 상품 (S104020101) - 해지 정산 등 */
    productForm: {
      lineNote: '',
      useType: '', // 사용여부: KT_REUSE, SKT_USE, LGT_USE, ETC
      remainCharge: null,
      penalty: null,
      finalSettle: null, // 최종 정산요금
      installmentRemain: null,
      cancelReason: '',
      memo: '',
    },
    /** 동의·서명 (S104030101) */
    agreeForm: {},
    /** 신청완료 화면(S104040101) 표시용 고객명 (마스킹 전 저장) */
    lastCompletedCustomerName: '',
    /** 신청완료 화면 표시용 접수번호 (cancel/apply 응답) */
    lastApplicationNo: '',
    /** 신청완료 화면 B2 발송번호 자동 세팅용 (해지 후 연락처) */
    lastContactPhone: '',
  }),
  getters: {
    isPhoneAuthCompleted: (state) => state.customerForm.phoneAuthCompleted,
  },
  actions: {
    setAgencyCode(code) {
      this.agencyCode = code || ''
    },
    setCustomerForm(form) {
      this.customerForm = { ...this.customerForm, ...form }
    },
    setProductForm(form) {
      this.productForm = { ...this.productForm, ...form }
    },
    setAgreeForm(form) {
      this.agreeForm = { ...this.agreeForm, ...form }
    },
    setLastCompletedName(name) {
      this.lastCompletedCustomerName = name || ''
    },
    setLastApplicationNo(no) {
      this.lastApplicationNo = no || ''
    },
    clearLastCompletedName() {
      this.lastCompletedCustomerName = ''
    },
    clearLastApplicationNo() {
      this.lastApplicationNo = ''
    },
    setLastContactPhone(phone) {
      this.lastContactPhone = phone || ''
    },
    clearLastContactPhone() {
      this.lastContactPhone = ''
    },
    reset() {
      this.agencyCode = ''
      this.customerForm = {
        custType: '',
        visitType: '',
        name: '',
        birthDate: '',
        corporateNo: '',
        phoneAuthCompleted: false,
        phone: '',
        email: '',
        emailDomain: '',
        post: '',
        address: '',
        addressDtl: '',
        minorAgent: { name: '', relation: '', phone: '', agree: false },
        agentInfo: {},
      }
      this.productForm = {
        lineNote: '',
        useType: '',
        remainCharge: null,
        penalty: null,
        finalSettle: null,
        installmentRemain: null,
        cancelReason: '',
        memo: '',
      }
      this.agreeForm = {}
      // lastCompletedCustomerName, lastApplicationNo는 완료 화면에서 사용 후 clear로 제거
    },
  },
})
