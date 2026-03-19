import { defineStore } from 'pinia'

export const useMsfCompLoadingStore = defineStore('msfCompLoading', {
  state: () => ({
    isShowLoading: false,
  }),
  getters: {},
  actions: {
    showLoadingComponent() {
      this.isShowLoading = true
    },
    hideLoadingComponent() {
      this.isShowLoading = false
    },
  },
})
