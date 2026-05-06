import { defineStore } from 'pinia'

const createFormData = () => ({
  searchWord: '',
  startDate: '',
  endDate: '',
  formTypeCd: '',
})

const createFormDtlData = () => ({})

const createPopupState = () => ({
  selectedScriptSeq: null,
  selectedScriptData: null,
  originalScriptData: null,
  popupTitle: '등록',
  scriptDtlOpen: false,
})

export const storeReceiptPage = defineStore('receiptpage', {
  state: () => ({
    formData: createFormData(),
    formDtlData: createFormDtlData(),
    popup: createPopupState(),
  }),
  getters: {},
  actions: {
    resetScriptPopup() {
      this.popup.selectedScriptSeq = null
      this.popup.selectedScriptData = null
      this.popup.popupTitle = '등록'
      this.popup.scriptDtlOpen = false
      this.formDtlData = createFormDtlData()
    },
    openCreatePopup() {
      this.resetScriptPopup()
      this.popup.scriptDtlOpen = true
    },
    openUpdatePopup(data, seq) {
      const { ...rest } = data ?? {}

      this.popup.selectedScriptSeq = seq ?? null
      this.popup.selectedScriptData = data ? rest : null
      this.popup.popupTitle = '수정'
      this.formDtlData = data ? { ...createFormDtlData(), ...rest } : createFormDtlData()
      this.popup.scriptDtlOpen = true
    },
    closeScriptPopup() {
      this.popup.scriptDtlOpen = false
      this.resetScriptPopup()
    },
  },
})
