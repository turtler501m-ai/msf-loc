import { defineStore } from 'pinia'

// 중복 없는 ID 생성을 위한 전역 카운터 변수
let alertCounter = 0

export const useMsfAlertStore = defineStore('msfAlert', {
  state: () => ({
    alerts: [],
  }),
  getters: {},
  actions: {
    // 특정 ID를 찾아 삭제
    removeAlert(id) {
      const index = this.alerts.findIndex((item) => item.id === id)
      if (index !== -1) {
        this.alerts.splice(index, 1)
      }
    },
    openAlert(message, onConfirm, subMessage) {
      const id = `msf-alert-${alertCounter++}` // 고유 ID 생성
      this.alerts.push({
        id,
        title: message,
        message: subMessage,
        onConfirm: onConfirm,
      })
    },
    openConfirm(message, confirmFunc, subMessage, cancelFunc) {
      const id = `msf-alert-${alertCounter++}` // 고유 ID 생성
      this.alerts.push({
        id,
        title: message,
        message: subMessage,
        onConfirm: confirmFunc,
        showCancel: true,
        onCancel: cancelFunc,
      })
    },
  },
})
