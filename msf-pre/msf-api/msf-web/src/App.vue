<template>
  <div>
    <mcp-page-loading-component v-if="loadingBlocked" />
    <router-view />
  </div>
</template>

<script setup>
import { onMounted, onUpdated, ref, watch } from 'vue'
import { RouterView } from 'vue-router'
import { useMsfCompLoadingStore } from '@/stores/msf_comp_loading'
import McpPageLoadingComponent from '@/components/McpPageLoadingComponent.vue'

const msfCompLoadingStore = useMsfCompLoadingStore()
const loadingBlocked = ref(true)

watch(
  () => msfCompLoadingStore.isShowLoading,
  (newShow) => {
    loadingBlocked.value = newShow
  },
  { immediate: true },
)

onMounted(() => {
  msfCompLoadingStore.hideLoadingComponent()
})
onUpdated(() => {
  setTimeout(async () => {
    msfCompLoadingStore.hideLoadingComponent()
  }, 1000)
})
</script>

<style scoped></style>
