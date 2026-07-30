import { defineStore } from 'pinia'
import { hideAlert, showConfirmWithId } from '@/libs/utils/comp.utils'
import { cancelPendingApiRequests } from '@/libs/api/msf_request'

const LONG_LOADING_TIMEOUT = 30000
const LONG_LOADING_CONFIRM_ID = 'long-loading-confirm'
const longLoadingEnabled = true //import.meta.env.MODE !== 'loc'

export const useMsfLoadingStore = defineStore('msfLoading', {
  state: () => ({
    loadingCounter: 0,
    loadings: false,
    loadingGeneration: 0,
    longLoadingTimerId: null,
    isLongLoadingConfirmOpen: false,
  }),
  getters: {},
  actions: {
    showLoading() {
      if (this.loadingCounter === 0) {
        this.loadings = true
        if (longLoadingEnabled) {
          this.startLongLoadingTimer()
        }
      }
      this.loadingCounter++
      return this.loadingGeneration
    },
    hideLoading(generation = this.loadingGeneration) {
      if (generation !== this.loadingGeneration) {
        return
      }

      this.loadingCounter--
      if (this.loadingCounter <= 0) {
        this.loadingCounter = 0
        this.loadings = false
        this.clearLongLoadingWarning()
      }
    },
    resetLoading() {
      this.loadingGeneration++
      this.loadingCounter = 0
      this.loadings = false
      this.clearLongLoadingWarning()
    },
    startLongLoadingTimer() {
      if (!longLoadingEnabled || this.longLoadingTimerId || this.isLongLoadingConfirmOpen) {
        return
      }

      this.longLoadingTimerId = window.setTimeout(() => {
        this.longLoadingTimerId = null

        if (!this.loadings || this.isLongLoadingConfirmOpen) {
          return
        }

        this.isLongLoadingConfirmOpen = true
        showConfirmWithId(
          LONG_LOADING_CONFIRM_ID,
          '처리 시간이 길어지고 있습니다.',
          () => this.continueWaiting(),
          '네트워크 연결이 불안정하거나 서버 응답이 지연되고 있습니다.\n대기를 중단해도 서버 처리는 계속 진행될 수 있습니다.',
          () => this.cancelPendingRequests(),
          { confirm: '계속 기다리기', cancel: '대기 중단하기' },
        )
      }, LONG_LOADING_TIMEOUT)
    },
    clearLongLoadingWarning() {
      if (this.longLoadingTimerId) {
        window.clearTimeout(this.longLoadingTimerId)
        this.longLoadingTimerId = null
      }

      if (this.isLongLoadingConfirmOpen) {
        hideAlert(LONG_LOADING_CONFIRM_ID)
      }
      this.isLongLoadingConfirmOpen = false
    },
    continueWaiting() {
      this.isLongLoadingConfirmOpen = false
      if (this.loadings) {
        this.startLongLoadingTimer()
      }
    },
    cancelPendingRequests() {
      this.clearLongLoadingWarning()
      cancelPendingApiRequests()
      this.resetLoading()
    },
  },
})
