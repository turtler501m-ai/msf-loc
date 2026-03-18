<template>
  <template v-if="hasStepMenus">
    <mcp-step-service :key="mcpStepServiceKey" />
  </template>
  <template v-else>
    <div class="mcp-service-detail">
      <component :is="getRoutingComponent(route)"></component>
    </div>
  </template>
</template>

<script setup>
import { onBeforeMount, onBeforeUpdate, ref } from 'vue'
import { useRoute } from 'vue-router'
import McpStepService from '@/components/layouts/McpStepService.vue'
import { useMsfMenuStore } from '@/stores/msf_menu'
import { getRoutingComponent } from '@/utils/mcp_components'

const route = useRoute()
const menuStore = useMsfMenuStore()
const mcpStepServiceKey = ref(0)
const hasStepMenus = ref(false)

const setHasStepMenus = () => {
  const currentMenu = menuStore.getCurrentParentMenu(route.path)
  hasStepMenus.value =
    (currentMenu.stepSubMenu && currentMenu.children && currentMenu.children.length > 0) || false
  mcpStepServiceKey.value++
}

onBeforeMount(() => {
  setHasStepMenus()
})
onBeforeUpdate(() => {
  setHasStepMenus()
})
</script>

<style scoped>
@reference "tailwindcss";

.mcp-service-detail {
  @apply min-h-[calc(100vh-4rem)] bg-white;
}
</style>
