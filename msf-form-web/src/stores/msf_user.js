import { defineStore } from 'pinia'
import { post, refreshToken } from '@/libs/api/msf.api'
import { parseUserToken } from '@/libs/utils/auth.utils'

export const useMsfUserStore = defineStore('msfUser', {
  state: () => ({
    deviceUuid: null,
    token: null,
    userInfo: null,
    deviceInfo: null,
    userData: null,
  }),
  getters: {
    /**
     * 로그인 여부 체크
     * @return {boolean}
     */
    isAuthenticated() {
      return !!this.userInfo && !!this.token
    },
  },
  actions: {
    /**
     * 사용자 정보
     * @return {object|null} 사용자 정보 객체 또는 null
     */
    getUserInfo() {
      if (this.userInfo) {
        return this.userInfo
      }
      return null
    },
    /**
     * 인증 완료한 사용자 정보 저장
     *
     * @param {Object} data
     */
    setUserTokenInfo(data) {
      this.userInfo = data?.userInfo
      this.token = data?.accessToken
      this.userData = null
    },
    /**
     * 사용자 정보 조회
     */
    async loadUserInfo() {
      const response = await refreshToken()
      if (response.code !== '0000') {
        window.location.href = '/login'
      } else {
        // 성공적으로 새 토큰 발급
        this.setUserTokenInfo(response.data)
      }
    },
    /**
     * 사용자 정보 초기화 (로그아웃 등)
     */
    clearUserInfo() {
      this.userInfo = null
      this.token = null
    },
    setDeviceInfo(deviceInfo) {
      this.deviceInfo = deviceInfo
    },
    getDeviceInfo() {
      return this.deviceInfo
    },
    clearDeviceInfo() {
      this.deviceInfo = null
    },
    setUserData(userData) {
      this.userData = userData
    },
    getUserData() {
      return this.userData
    },
    clearUserData() {
      this.userData = null
    },

    // 사용자 세션 조회
    async getLoginSessionStatus() {
      return post('/api/n/auth/login/session/get', {
        loginSessionId: this.userData?.loginSessionId,
      }).then(async (data) => {
        if (data.code !== '0000' || !data.data) {
          return { type: 'fail', message: data.message || '로그인 세션 조회에 실패했습니다.' }
        }
        this.userData = data.data
        return await this.checkAuthAction()
      })
    },

    async checkAuthAction() {
      if (!this.userData?.loginSessionId) {
        return { type: 'fail', message: '로그인 세션 정보가 없습니다.' }
      }

      const requiredActionCode = this.userData?.requiredAction?.actionCode

      if (requiredActionCode === 'PASSWORD_CHANGE') {
        return {
          type: this.userData.requiredAction.actionCode,
          url: '/passwordChange',
          message: '비밀번호 변경이 필요합니다.\n비밀번호 변경 화면으로 이동합니다.',
        }
      } else if (this.userData.requiredAction.actionCode === 'VERIFY_2FA') {
        return { type: this.userData.requiredAction.actionCode, url: '/deviceAuth' }
      } else if (this.userData.requiredAction.actionCode === 'DEVICE_AUTH') {
        return { type: this.userData.requiredAction.actionCode, url: '/deviceRegist' }
      } else {
        const result = await post('/api/n/auth/login/issue', {
          loginSessionId: this.userData.loginSessionId,
        })
        if (result.code !== '0000') {
          return { type: 'fail', message: result.message }
        }
        this.token = result.data.accessToken
        this.userInfo = result.data.userInfo
        this.userData = null
        return { type: 'complete', url: '/' }
      }
    },

    async initDeviceUuid() {
      const uuid = await this.resolveDeviceUuid()
      this.setDeviceUuid(uuid)
      return uuid
    },

    async resolveDeviceUuid() {
      const allowUuidOverride = import.meta.env.VITE_MSF_ALLOW_DEVICE_UUID_OVERRIDE === 'true'
      if (allowUuidOverride) {
        let storageUuid = localStorage.getItem('MSF_DEVICE_UUID')?.trim()
        if (!storageUuid) {
          storageUuid = crypto.randomUUID()
          localStorage.setItem('MSF_DEVICE_UUID', storageUuid)
        }
        return storageUuid
      }

      const appBridge = window.MSF_APP
      if (!appBridge) return null

      const uuid =
        typeof appBridge.getDeviceUuid === 'function'
          ? await appBridge.getDeviceUuid()
          : appBridge.deviceUuid
      return typeof uuid === 'string' ? uuid.trim() : uuid
    },

    setDeviceUuid(uuid) {
      this.deviceUuid = uuid || null
    },

    getDeviceUuid() {
      return this.deviceUuid
    },

    clearDeviceUuid() {
      this.deviceUuid = null
    },
  },
})
