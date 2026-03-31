import { defineStore } from 'pinia'

/**
 * 서비스변경 신청서 폼 상태
 * Phase 1 서비스변경 플로우용
 */
export const useServiceChangeFormStore = defineStore('serviceChangeForm', {
  state: () => ({
    /** 가입유형 선택 (S102010101) */
    selectedOptions: [],
    /** 대리점 선택 (S102010101) */
    branchCode: '',
    /** 고객 정보 (S102020101) - join-info 조회 후 ncn, custId 세팅 */
    customerForm: {
      custType: '',
      visitType: '',
      name: '',
      birthDate: '',
      gender: '', // 남/여 (M/F)
      corporateNo: '',
      phoneAuthCompleted: false,
      phone: '',
      tel: '', // 전화번호 (지역-가운데-뒤)
      email: '',
      emailDomain: '',
      post: '',
      address: '',
      addressDtl: '',
      ncn: '', // join-info 응답 (계약정보 유효성 Y04용)
      custId: '', // join-info 응답 (M전산 고객번호)
      activationDate: '', // join-info 응답 (최초개통일, initActivationDate)
      minorAgent: {},
      agentInfo: {},
    },
    /** 상품 선택 (S102030101) */
    productForm: {
      additions: [],
      wirelessBlock: null, // 'use' | 'block' (무선데이터 이용 / 무선데이터차단 서비스 이용)
      infoLimit: null, // '0' | '3000' | '10000' | '20000' | '30000' (정보료 상한금액)
      rateChange: null,
      recommendedRatePlan: '', // RATE_CHANGE 추천요금제 soc
      ratePlanSearchResult: '', // RATE_CHANGE 요금제 조회 결과 soc
      rateChangeSchedule: null, // 'reserve' | 'immediate'
      numChange: null,
      lostRestore: null,
      insurance: null,
      phoneChange: null,
      usimChange: null,
      dataSharing: null,
      anySolo: null,
    },
    /** 동의·서명 (S102040101) */
    agreeForm: {},
    /** 신청완료 화면(S102050101) 표시용 고객명 (마스킹 전 저장) */
    lastCompletedCustomerName: '',
    /** 신청완료 화면 표시용 접수번호 (apply API 연동 시 설정) */
    lastApplicationNo: '',
  }),
  getters: {
    hasSelectedOptions: (state) => state.selectedOptions.length > 0,
    isPhoneAuthCompleted: (state) => state.customerForm.phoneAuthCompleted,
  },
  actions: {
    setSelectedOptions(opts) {
      this.selectedOptions = Array.isArray(opts) ? [...opts] : []
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
    clearLastCompletedName() {
      this.lastCompletedCustomerName = ''
    },
    setLastApplicationNo(no) {
      this.lastApplicationNo = no || ''
    },
    clearLastApplicationNo() {
      this.lastApplicationNo = ''
    },
    reset() {
      this.selectedOptions = []
      this.branchCode = ''
      this.customerForm = {
        custType: '',
        visitType: '',
        name: '',
        birthDate: '',
        gender: '',
        corporateNo: '',
        phoneAuthCompleted: false,
        phone: '',
        tel: '',
        email: '',
        emailDomain: '',
        post: '',
        address: '',
        addressDtl: '',
        ncn: '',
        custId: '',
        activationDate: '',
        minorAgent: {},
        agentInfo: {},
      }
      this.productForm = {
        additions: [],
        wirelessBlock: null,
        infoLimit: null,
        rateChange: null,
        recommendedRatePlan: '',
        ratePlanSearchResult: '',
        rateChangeSchedule: null,
        numChange: null,
        lostRestore: null,
        insurance: null,
        phoneChange: null,
        usimChange: null,
        dataSharing: null,
        anySolo: null,
      }
      this.agreeForm = {}
      // lastCompletedCustomerName, lastApplicationNo는 완료 화면에서 사용 후 clear 로 제거
    },
  },
})
