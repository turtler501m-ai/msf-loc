import { defineStore } from 'pinia'

export const useMsfAppStore = defineStore('msfApp', {
  state: () => ({
    deviceType: 'PC',
    appName: 'MSF Admin Web',
    appVersion: '1.0.0',
    showFirst: true, // 첫 방문 시 로그인 페이지 이동
  }),
  getters: {},
  actions: {
    setShowFirst(value) {
      this.showFirst = value
    },
  },
})
