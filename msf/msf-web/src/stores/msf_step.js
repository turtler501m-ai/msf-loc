import { defineStore } from 'pinia'

export const useMsfStepStore = defineStore('msfStep', {
  state: () => ({
    steps: {
      formSvcChg: ['FormSvcChgStep1', 'FormSvcChgStep2', 'FormSvcChgStep3'],
      formOwnChg: ['FormOwnChgStep1', 'FormOwnChgStep2', 'FormOwnChgStep3'],
      formSvcCncl: ['FormSvcCnclStep1', 'FormSvcCnclStep2', 'FormSvcCnclStep3'],
    },
    activeGroup: null,
    activeSteps: [],
  }),
  getters: {},
  actions: {},
})
