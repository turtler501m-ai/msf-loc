import { ref, watch } from 'vue'

export function useAuthButton(dependenciesCallback, externalAuthFlag) {
  // 'none' (입력 부족) -> 'ready' (입력 완료, 발송/인증 가능) -> 'sent' (발송됨) -> 'verified' (인증완료)
  const status = ref('none')

  watch(
    dependenciesCallback,
    (newVals) => {
      // 모든 의존성 값이 유효한지 체크 (undefined, null, 빈 문자열 방지)
      const isReady = newVals.every(
        (val) => val !== undefined && val !== null && String(val).trim().length > 0,
      )

      // 의존성 값이 하나라도 바뀌면 인증 상태 강제 초기화 (재인증 요구)
      status.value = isReady ? 'ready' : 'none'
      if (externalAuthFlag) {
        externalAuthFlag.value = false
      }
    },
    { deep: true, immediate: true },
  )

  const send = () => {
    if (status.value === 'ready' || status.value === 'sent') {
      status.value = 'sent'
      // TODO: 실제 API 발송 로직 연동
    }
  }

  const verify = () => {
    if (status.value === 'sent' || status.value === 'ready') {
      status.value = 'verified'
      if (externalAuthFlag) {
        externalAuthFlag.value = true // 스토어의 인증 완료 플래그 동기화
      }
      // TODO: 실제 인증 확인 로직 연동
    }
  }

  const reset = () => {
    status.value = 'none'
    if (externalAuthFlag) {
      externalAuthFlag.value = false
    }
  }

  // 임시저장 등 외부에서 강제로 'ready' (재인증 필요) 상태로 돌릴 때 사용
  const requireReauth = () => {
    if (status.value === 'verified' || status.value === 'sent') {
      status.value = 'ready'
    }
    if (externalAuthFlag) {
      externalAuthFlag.value = false
    }
  }

  return { status, send, verify, reset, requireReauth }
}
