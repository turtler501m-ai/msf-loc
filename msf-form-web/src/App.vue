<template>
  <RouterView />
  <!-- 전역적으로 사용되는 포탈 영역(Dialog, Popover 등을 띄우는 영역) -->
  <div id="portal-root" class="portal"></div>
  <MsfAlertDialog
    v-for="(item, index) in alerts"
    :key="item.id"
    :id="item.id"
    :title="item.title"
    :message="item.message"
    :labelProps="item.labelProps"
    :showCancel="item.showCancel"
    :isLast="index === alerts.length - 1"
    :onConfirm="
      () => {
        // item(alert 객체) 안에 담긴 onConfirm이 함수인지 확인하고 실행
        if (typeof item.onConfirm === 'function') {
          item.onConfirm()
        }
        removeAlert(item.id) // 실행 후 해당 ID의 알럿만 닫기
      }
    "
    :onCancel="
      item.showCancel
        ? () => {
            item.onCancel?.() // 혹시 취소 시 할 일이 따로 정의되어 있다면 실행
            removeAlert(item.id) // 그리고 해당 ID의 알럿만 닫기
          }
        : undefined
    "
  />
</template>

<script setup>
import { onMounted, onUnmounted } from 'vue'
import { RouterView } from 'vue-router'
import { useMsfAlertStore } from '@/stores/msf_alert'

const { alerts, removeAlert } = useMsfAlertStore()

// 1. 마우스 우클릭(컨텍스트 메뉴) 방지
const preventContextMenu = (e) => {
  e.preventDefault() // 기본 메뉴가 뜨는 것을 막음
  return false
}

// 2. 프린트 스크린 및 단축키 방지
const preventKeyEvents = async (e) => {
  // PrintScreen 키 감지 (key 또는 keyCode 사용)
  if (e.key === 'PrintScreen' || e.keyCode === 44) {
    try {
      // 브라우저 클립보드 API를 사용해 복사된 이미지를 빈 문자열로 덮어씌움
      await navigator.clipboard.writeText('')
      alert('보안 정책상 화면 캡처가 금지되어 있습니다.')
    } catch (err) {
      console.error('클립보드 비우기 실패', err)
    }
  }

  // (추가 팁) F12(개발자 도구) 및 복사(Ctrl+C) 방지
  if (e.key === 'F12' || (e.ctrlKey && e.key === 'c')) {
    e.preventDefault()
  }

  return false
}

onMounted(() => {
  if (import.meta.env.MODE === 'prd') {
    // 컴포넌트가 화면에 마운트될 때 이벤트 등록
    window.addEventListener('contextmenu', preventContextMenu)
    window.addEventListener('keyup', preventKeyEvents)
    window.addEventListener('keydown', preventKeyEvents)
  }
})

onUnmounted(() => {
  if (import.meta.env.MODE === 'prd') {
    // 컴포넌트가 파괴될 때 이벤트 해제 (메모리 누수 방지)
    window.removeEventListener('contextmenu', preventContextMenu)
    window.removeEventListener('keyup', preventKeyEvents)
    window.removeEventListener('keydown', preventKeyEvents)
  }
})
</script>

<style scoped></style>
