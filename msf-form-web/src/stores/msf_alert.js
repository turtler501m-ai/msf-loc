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
    openAlert(message, onConfirm, subMessage, customId) {
      // customId가 있고 기존 알림이 있다면 덮어쓰기
      const existing = customId ? this.alerts.find((item) => item.id === customId) : null
      if (existing) {
        existing.title = message
        existing.message = subMessage
        existing.onConfirm = onConfirm
        return customId
      }
      // 덮어쓰기가 아니면 id 생성 후 push
      const id = customId || `msf-alert-${alertCounter++}` // customId 또는 고유 ID 생성
      this.alerts.push({
        id,
        title: message,
        message: subMessage,
        onConfirm: onConfirm,
      })
      return id
    },
    openConfirm(message, confirmFunc, subMessage, cancelFunc, customId, labelProps) {
      // customId가 있고 기존 알림이 있다면 덮어쓰기
      const existing = customId ? this.alerts.find((item) => item.id === customId) : null
      if (existing) {
        existing.title = message
        existing.message = subMessage
        existing.onConfirm = confirmFunc
        existing.onCancel = cancelFunc
        existing.labelProps = labelProps
        return customId
      }
      // 덮어쓰기가 아니면 id 생성 후 push
      const id = customId || `msf-alert-${alertCounter++}` // customId 또는 고유 ID 생성
      this.alerts.push({
        id,
        title: message,
        message: subMessage,
        onConfirm: confirmFunc,
        showCancel: true,
        onCancel: cancelFunc,
        labelProps,
      })
      return id
    },
  },
})
