import { defineStore } from 'pinia'
import { useMsfLoadingStore } from '@/stores/msf_loading'
import { isAppWebView } from '@/libs/utils/device.utils'

// 연결 확인 주기
const CHECK_INTERVAL = 5000
// 연결 확인 요청 제한 시간
const CHECK_TIMEOUT = 4000
// 불안정 상태 전환 실패 기준 횟수
const FAIL_THRESHOLD = 1
// 복구 메시지 노출 시간
const RECOVERY_MESSAGE_DURATION = 3000

// form-web 정적 파일 기반 WebView 연결 상태 확인
const networkCheckUrl = import.meta.env.VITE_MSF_NETWORK_CHECK_URL || '/network-check.json'

// 캐시 응답 오판 방지를 위한 timestamp query 추가
const buildCheckUrl = () => {
  const path = networkCheckUrl.startsWith('/') ? networkCheckUrl : '/' + networkCheckUrl
  const url = networkCheckUrl.startsWith('http') ? networkCheckUrl : window.location.origin + path
  const separator = url.includes('?') ? '&' : '?'
  return url + separator + '_=' + Date.now()
}
const isCrossOrigin = (url) => {
  if (!url.startsWith('http')) return false
  return new URL(url).origin !== window.location.origin
}
export const useMsfNetworkStore = defineStore('msfNetwork', {
  state: () => ({
    isMonitoring: false,
    isChecking: false,
    hasPendingCheck: false,
    isUnstable: false,
    showRecovered: false,
    failCount: 0,
    lastCheckedAt: null,
    lastRecoveredAt: null,
    lastFailureReason: '',
    timerId: null,
    recoveryTimerId: null,
    monitoringScopes: [],
  }),
  actions: {
    startMonitoring(scope = 'default') {
      if (!isAppWebView()) {
        return
      }

      if (!this.monitoringScopes.includes(scope)) {
        this.monitoringScopes.push(scope)
      }

      if (this.isMonitoring) return

      this.resetStatus()
      this.isMonitoring = true
      window.addEventListener('online', this.handleOnline)
      window.addEventListener('offline', this.handleOffline)
      document.addEventListener('visibilitychange', this.handleVisibilityChange)

      this.timerId = window.setInterval(() => {
        this.checkNow()
      }, CHECK_INTERVAL)
      this.checkNow()
    },
    stopMonitoring(options = {}) {
      const scope = options.scope || 'default'
      this.monitoringScopes = this.monitoringScopes.filter((item) => item !== scope)

      if (this.isMonitoring && this.monitoringScopes.length > 0) {
        return
      }

      if (!this.isMonitoring) {
        if (options.resetStatus) {
          this.resetStatus()
        }
        return
      }

      this.isMonitoring = false
      window.removeEventListener('online', this.handleOnline)
      window.removeEventListener('offline', this.handleOffline)
      document.removeEventListener('visibilitychange', this.handleVisibilityChange)

      if (this.timerId) {
        window.clearInterval(this.timerId)
        this.timerId = null
      }
      if (this.recoveryTimerId) {
        window.clearTimeout(this.recoveryTimerId)
        this.recoveryTimerId = null
      }
      if (options.resetStatus) {
        this.resetStatus()
      }
    },
    handleOnline() {
      this.checkNow()
    },
    handleOffline() {
      this.markRequestFailed('offline')
    },
    handleVisibilityChange() {
      if (!document.hidden) {
        this.checkNow()
      }
    },
    async checkNow() {
      if (this.isChecking) {
        this.hasPendingCheck = true
        return
      }

      this.isChecking = true
      const controller = new AbortController()
      const timeoutId = window.setTimeout(() => controller.abort(), CHECK_TIMEOUT)

      try {
        const url = buildCheckUrl()
        const crossOrigin = isCrossOrigin(url)
        const response = await fetch(url, {
          method: 'GET',
          cache: 'no-store',
          mode: crossOrigin ? 'cors' : 'same-origin',
          credentials: crossOrigin ? 'include' : 'same-origin',
          signal: controller.signal,
        })

        if (!response.ok) {
          throw new Error(`network check failed: ${response.status}`)
        }

        this.markRequestSucceeded()
      } catch (error) {
        this.markRequestFailed(error?.message || 'network check failed')
      } finally {
        window.clearTimeout(timeoutId)
        this.isChecking = false
        this.lastCheckedAt = Date.now()
        if (this.hasPendingCheck) {
          this.hasPendingCheck = false
          window.setTimeout(() => this.checkNow(), 0)
        }
      }
    },
    resetStatus() {
      this.failCount = 0
      this.lastFailureReason = ''
      this.isChecking = false
      this.hasPendingCheck = false
      this.isUnstable = false
      this.showRecovered = false
    },
    markRequestSucceeded() {
      const wasUnstable = this.isUnstable

      this.failCount = 0
      this.lastFailureReason = ''
      this.isUnstable = false

      if (wasUnstable) {
        // 네트워크 단절 중 남은 전역 로딩 복구 시점 정리
        useMsfLoadingStore().resetLoading()
        this.lastRecoveredAt = Date.now()
        this.showRecovered = true
        if (this.recoveryTimerId) {
          window.clearTimeout(this.recoveryTimerId)
        }
        this.recoveryTimerId = window.setTimeout(() => {
          this.showRecovered = false
          this.recoveryTimerId = null
        }, RECOVERY_MESSAGE_DURATION)
      }
    },
    markRequestFailed(reason = '', options = {}) {
      this.failCount += 1
      this.lastFailureReason = reason
      this.lastCheckedAt = Date.now()
      this.showRecovered = false

      if (options.immediate || this.failCount >= FAIL_THRESHOLD || reason === 'offline') {
        this.isUnstable = true
      }
    },
  },
})
