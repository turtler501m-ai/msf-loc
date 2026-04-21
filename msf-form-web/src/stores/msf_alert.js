import { defineStore } from 'pinia'

export const useMsfAlertStore = defineStore('msfAlert', {
  state: () => ({
    alerts: [],
  }),
  getters: {},
  actions: {
    removeAlert() {
      this.alerts.shift() // 가장 오래된 알럿부터 제거
    },
    openAlert(message, onConfirm, subMessage) {
      this.alerts.push({
        title: message,
        message: subMessage,
        onConfirm: onConfirm,
      })
    },
    openConfirm(message, confirmFunc, subMessage, cancelFunc) {
      this.alerts.push({
        title: message,
        message: subMessage,
        onConfirm: confirmFunc,
        showCancel: true,
        onCancel: cancelFunc,
      })
    },
  },
})
