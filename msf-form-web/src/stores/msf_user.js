import { defineStore } from 'pinia'
import { parseUserToken } from '@/libs/utils/auth.utils'

export const useMsfUserStore = defineStore('msfUser', {
  state: () => ({
    token: null,
    userInfo: null,
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
      const tokenInfo = parseUserToken(this.token)
      if (tokenInfo) {
        const { id, name, organization } = tokenInfo
        this.userInfo = { id, name, organization }
        return this.userInfo
      }
      return null
    },
    /**
     * 사용자 정보 조회
     */
    loadUserInfo() {
      // FIXME: 실제 로그인 API 연동 시, userInfo는 API 응답에서 받아온 정보로 설정되어야 합니다.
      this.token =
        'eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4MjMxMjAwNCIsImlkIjoiODIzMTIwMDQiLCJuYW1lIjoi7ZmN7ISx66-8Iiwib3JnYW5pemF0aW9uIjp7ImNvZGUiOiJTUFQ4MDUwIiwibmFtZSI6IuyKpOuniO2KuOyEnOyLneyngCDtlITroZzsoJ3tirjtjIAifSwiaWF0IjoxNzc1NjI5MTE3fQ.oGDN5LylCy98yzO6jdoDzGGFWDdKrtwsbAypfSM81CiWsllVn6dvCUEuTB-JSFZloQpe24orWm94VtRdzuFzPw'
      const { id, name, organization } = parseUserToken(this.token)
      this.userInfo = { id, name, organization }
      console.log('store.userInfo:', this.userInfo)
    },
    /**
     * 사용자 정보 초기화 (로그아웃 등)
     */
    clearUserInfo() {
      this.userInfo = null
      this.token = null
    },
  },
})
