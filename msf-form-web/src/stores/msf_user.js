import { defineStore } from 'pinia'
import { post } from '@/libs/api/msf.api'
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
      // FIXME: 임시로 자동 로그인 설정 (로직 제거 필요)
      this.loadUserInfo()
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
      if (!this.token) {
        this.loadUserInfo()
      }
      const tokenInfo = parseUserToken(this.token)
      if (tokenInfo) {
        const { id, name, organization } = tokenInfo
        this.userInfo = { id, name, organization }
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
    },
    /**
     * 사용자 정보 조회
     */
    loadUserInfo() {
      // FIXME: 실제 로그인 API 연동 시, userInfo는 API 응답에서 받아온 정보로 설정되어야 합니다.
      this.token =
        'eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJWMDAwMDAwNTgxIiwiaWQiOiJWMDAwMDAwNTgxIiwibmFtZSI6IuyCvO2MkF_rtInri7TrqqjrsJTsnbzsoJAiLCJvcmdhbml6YXRpb24iOnsiY29kZSI6IlZLSTAxODQiLCJuYW1lIjoi7IK87ISx7KCE7J6Q7YyQ66ekX03rqqjrsJTsnbwifSwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1MTg4MzEwMjJ9.TuWZNIiK2ukg9e9OCYn9_Y_mejuwNxY97VXjd5wC-rzUqUA8nt5Sd1ukQFlI8RZchrXsaejIjz6HVIlI7Qk_fQ'
      const { id, name, organization } = parseUserToken(this.token)
      this.userInfo = { id, name, organization }
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
    async checkAuthAction() {
      if (this.userData.requiredAction.actionCode === 'PASSWORD_CHANGE') {
        return {
          type: 'continue',
          url: '/passwordChange',
          message: '비밀번호 변경이 필요합니다.\n비밀번호 변경 화면으로 이동합니다.',
        }
      } else if (this.userData.requiredAction.actionCode === 'VERIFY_2FA') {
        return { type: 'continue', url: '/deviceAuth' }
      } else if (this.userData.requiredAction.actionCode === 'DEVICE_AUTH') {
        return { type: 'continue', url: '/deviceRegist' }
      } else {
        // TODO: accessToken 발급 로직 추가`
        const result = await post('/api/auth/login/issue', {
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
    setDeviceUuid(uuid) {
      this.deviceUuid = uuid
    },
    getDeviceUuid() {
      return this.deviceUuid
    },
    clearDeviceUuid() {
      this.deviceUuid = null
    },
  },
})
