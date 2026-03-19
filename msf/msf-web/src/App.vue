<script setup>
import { RouterView } from 'vue-router'
import { useAlertProvider } from '@/hooks/useAlert'

const { alerts, removeAlert } = useAlertProvider()
</script>

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
        if (typeof item.onConfirm === 'function') {
          item.onConfirm()
        }
        removeAlert()
      }
    "
    :onCancel="
      item.showCancel
        ? () => {
            item.onCancel?.()
            removeAlert()
          }
        : undefined
    "
  />
</template>
