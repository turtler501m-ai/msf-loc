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
    _refreshLock: null, // 내부용 refresh 중복 방지용 대기 잠금
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
      // refresh 중이면 기존 요청 결과를 그대로 재사용
      if (this._refreshLock) return this._refreshLock

      // refreshToken은 한 번만 실행하고 결과를 공유
      this._refreshLock = (async () => {
        try {
          const response = await refreshToken()
          // 실패하면 사용자 상태 초기화
          if (response.code !== '0000') {
            this.clearUserInfo()
            return false
          }
          // 성공적으로 새 토큰 발급
          this.setUserTokenInfo(response.data)
          return true
        } catch (e) {
          // 에러 발생 시도 동일하게 로그아웃 처리
          this.clearUserInfo()
          return false
        }
      })()

      const result = await this._refreshLock // refresh 완료까지 대기
      this._refreshLock = null // 다음 refresh를 위해 잠금 해제
      return result // 실패 시 로그인 이동은 router에서 처리
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
  },
})
