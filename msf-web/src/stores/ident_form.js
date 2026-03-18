import { defineStore } from 'pinia'

/**
 * 명의변경 신청서 폼 상태
 * 명의변경 플로우용 (서비스변경과 동일 패턴)
 */
export const useIdentFormStore = defineStore('identForm', {
  state: () => ({
    /** 가입유형 선택 (S103050101) - 대리점 */
    agencyCode: '',
    /** 고객 정보 (S103010101) - 양도고객(transferor) + 양수고객(transferee) */
    customerForm: {
      custType: '',
      visitType: '',
      name: '',
      birthDate: '',
      corporateNo: '',
      bizNo: '', // 사업자등록번호
      representativeName: '', // 법인/공공기관 대표자명*
      phoneAuthCompleted: false,
      phone: '',
      ncn: '',   // join-info 응답 계약번호 (MC0/MP0 연동용)
      custId: '', // join-info 응답 고객번호
      email: '',
      emailDomain: '',
      post: '',
      address: '',
      addressDtl: '',
      minorAgent: { name: '', relation: '', phone: '', agree: false },
      agentInfo: {},
      // 양수고객(명의변경 후 고객)
      transferee: {
        custType: '',
        visitType: 'self',
        name: '',
        residentNo: '',
        foreignerNo: '',
        corporateNo: '',
        bizNo: '',
        industry: '',
        representativeName: '',
        contactPhone: '',
        contactTel: '',
        email: '',
        emailDomain: '',
        post: '',
        address: '',
        addressDtl: '',
        country: '',
        visa: '',
        stayStart: '',
        stayEnd: '',
        minorAgent: { name: '', relation: '', residentNo: '', phone: '', agree: false },
        agentInfo: { custName: '', birthDate: '', phone: '', relation: '' },
        idDocType: '', // 신분증: 주민/운전/모바일/안면
        attachmentScanned: false,
      },
      ratePlanCode: '', // 고객 화면 하단 요금제*
      termsAgreed: false, // 약관 동의
    },
    /** 상품 (S103020101) */
    productForm: {
      usimType: '', // 'current' | 'purchase' | 'sim'
      usimNo: '',
      usimOption: '', // 일반/NFC
      usimPayType: '', // 즉시납부/다음달합산
      rateNote: '',
      payMethod: '',
      statementType: 'mobile', // 모바일/이메일/우편
      additionItems: [],
      memo: '',
    },
    /** 동의·서명 (S103030101) */
    agreeForm: {},
    /** 신청완료 화면(S103040101) 표시용 고객명 (마스킹 전 저장) */
    lastCompletedCustomerName: '',
    /** 신청완료 화면 표시용 접수번호 (API apply 응답 applicationNo) */
    lastApplicationNo: '',
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
    setLastApplicationNo(applicationNo) {
      this.lastApplicationNo = applicationNo || ''
    },
    clearLastCompletedName() {
      this.lastCompletedCustomerName = ''
      this.lastApplicationNo = ''
    },
    reset() {
      this.agencyCode = ''
      this.customerForm = {
        custType: '',
        visitType: '',
        name: '',
        birthDate: '',
        corporateNo: '',
        bizNo: '',
        representativeName: '',
        phoneAuthCompleted: false,
        phone: '',
        ncn: '',
        custId: '',
        email: '',
        emailDomain: '',
        post: '',
        address: '',
        addressDtl: '',
        minorAgent: { name: '', relation: '', phone: '', agree: false },
        agentInfo: {},
        transferee: {
          custType: '',
          visitType: 'self',
          name: '',
          residentNo: '',
          foreignerNo: '',
          corporateNo: '',
          bizNo: '',
          industry: '',
          representativeName: '',
          contactPhone: '',
          contactTel: '',
          email: '',
          emailDomain: '',
          post: '',
          address: '',
          addressDtl: '',
          country: '',
          visa: '',
          stayStart: '',
          stayEnd: '',
          minorAgent: { name: '', relation: '', residentNo: '', phone: '', agree: false },
          agentInfo: { custName: '', birthDate: '', phone: '', relation: '' },
          idDocType: '',
          attachmentScanned: false,
        },
        ratePlanCode: '',
        termsAgreed: false,
      }
      this.productForm = {
        usimType: '',
        usimNo: '',
        usimOption: '',
        usimPayType: '',
        rateNote: '',
        payMethod: '',
        statementType: 'mobile',
        additionItems: [],
        memo: '',
      }
      this.agreeForm = {}
      // lastCompletedCustomerName은 완료 화면에서 사용 후 clearLastCompletedName()으로 제거
    },
  },
})
