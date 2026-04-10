import { defineStore } from 'pinia'

export const useMsfStepStore = defineStore('msfStep', {
  state: () => ({
    steps: {
      newchange: ['NewChangeCustomer', 'NewChangeProduct', 'NewChangeAgreement'],
      servicechange: ['ServiceChangeCustomer', 'ServiceChangeProduct', 'ServiceChangeAgreement'],
      ownerchange: ['OwnerChangeCustomer', 'OwnerChangeProduct', 'OwnerChangeAgreement'],
      termination: ['TerminationCustomer', 'TerminationProduct', 'TerminationAgreement'],
    },
    activeGroup: null, // 현재 도메인 (예: 'formNewChg')
    activeSteps: [], // 현재 활성화된 컴포넌트 리스트
    activeIndex: 0, // [추가] 현재 사용자가 머물고 있는 단계 번호 (0, 1, 2...)
  }),

  actions: {
    // 초기화 액션
    initSteps(domain) {
      this.activeGroup = domain
      this.activeSteps = [this.steps[domain][0]]
      this.activeIndex = 0 // 시작 시 0으로 초기화
    },

    // 단계 변경 액션 (Next 버튼 클릭 시 호출)
    goToNextStep() {
      this.activeIndex++
    },

    // 특정 인덱스로 직접 이동 (필요 시)
    setActiveIndex(index) {
      this.activeIndex = index
    },
  },
})
