<template>
  <RouterView />
  <MsfAlertDialog
    v-for="(item, index) in alerts"
    :key="index"
    :title="item.title"
    :message="item.message"
    :labelProps="item.labelProps"
    :showCancel="item.showCancel"
    :onConfirm="
      () => {
        // item(alert 객체) 안에 담긴 onConfirm이 함수인지 확인하고 실행
        if (typeof item.onConfirm === 'function') {
          item.onConfirm()
        }
        removeAlert() // 실행 후 닫기
      }
    "
    :onCancel="
      item.showCancel
        ? () => {
            item.onCancel?.() // 혹시 취소 시 할 일이 따로 정의되어 있다면 실행
            removeAlert() // 그리고 닫기
          }
        : undefined
    "
  />
</template>

<script setup>
import { RouterView } from 'vue-router'
import { useAlertProvider } from '@/hooks/useAlert'
// showAlert 기능을 하위 컴포넌트들에 전파(provide)
const { alerts, removeAlert } = useAlertProvider()
</script>

<style scoped></style>
