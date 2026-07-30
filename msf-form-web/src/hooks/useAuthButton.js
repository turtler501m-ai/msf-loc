import { ref, watch } from 'vue'

export function useAuthButton(dependenciesCallback, externalAuthFlag, validator) {
  // 'none' (입력 부족) -> 'ready' (입력 완료, 발송/인증 가능) -> 'sent' (발송됨) -> 'verified' (인증완료)
  const status = ref('none')

  watch(
    dependenciesCallback,
    (newVals) => {
      let isReady = false
      if (validator) {
        // 커스텀 검증 함수가 제공된 경우 사용
        isReady = !!validator(newVals)
      } else {
        // 모든 의존성 값이 유효한지 체크 (undefined, null, 빈 문자열 방지)
        isReady = newVals.every(
          (val) => val !== undefined && val !== null && val && String(val).trim().length > 0,
        )
      }

      // 의존성 값이 하나라도 바뀌면 인증 상태 강제 초기화 (재인증 요구)
      // 단, 이미 'verified'인 상태에서 값이 바뀐 경우에만 'ready'로 돌림
      if (status.value === 'verified' && !isReady) {
        status.value = 'none'
      } else if (status.value === 'verified' && isReady) {
        // 이미 인증된 상태에서 값이 바뀌면(예: 번호 수정) 재인증 필요
        // (단, validator가 여전히 true라면 'ready'로, 아니면 'none'으로)
        // 실제로는 dependenciesCallback이 바뀌면 무조건 재인증이 필요할 수 있음
        status.value = 'ready'
      } else {
        status.value = isReady ? 'ready' : 'none'
      }

      if (externalAuthFlag && status.value !== 'verified') {
        externalAuthFlag.value = false
      }
    },
    { deep: true, immediate: true },
  )

  if (externalAuthFlag) {
    watch(
      () => externalAuthFlag.value,
      (newVal) => {
        if (newVal === false) {
          if (status.value === 'verified' || status.value === 'sent') {
            status.value = 'ready'
          }
        } else if (newVal === true) {
          if (status.value !== 'verified') {
            status.value = 'verified'
          }
        }
      }
    )
  }

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
