import { ref, provide, inject } from 'vue'

// 상징적인 키값
const AlertSymbol = Symbol()

export const useAlertProvider = () => {
  const alerts = ref([])

  /** 알럿 제거 */
  const removeAlert = () => {
    alerts.value.shift() // 가장 오래된 알럿부터 제거
  }

  /** 알럿 띄우기 (오버로딩 대응) */
  const showAlert = (...args) => {
    // 1. 객체 형태로 전달된 경우: showAlert({ message: '...' })
    if (typeof args[0] === 'object' && args[0] !== null) {
      alerts.value.push(args[0])
    }
    // 2. 단일 메시지만 전달된 경우: showAlert('확인해주세요')
    else if (args.length === 1) {
      // alerts.value.push({ message: args[0] })
      alerts.value.push({ title: args[0] })
    }
    // 3. 여러 인자로 전달된 경우: showAlert(title, message, onConfirm, ...)
    else {
      alerts.value.push({
        title: args[0],
        message: args[1],
        onConfirm: args[2],
        showCancel: args[3],
        labelProps: args[4],
        onCancel: args[5],
      })
    }
  }

  // 자식 컴포넌트들에게 공유할 기능들 등록
  provide(AlertSymbol, { showAlert, removeAlert })

  return { alerts, removeAlert }
}

/** 컴포넌트에서 꺼내 쓸 때 사용하는 훅 */
export const useAlert = () => {
  const context = inject(AlertSymbol)
  if (!context) {
    throw new Error('useAlert는 Provider 내부에서만 사용할 수 있습니다.')
  }
  return context
}
