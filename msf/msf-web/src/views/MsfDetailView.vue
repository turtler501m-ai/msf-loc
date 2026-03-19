<template>
  <template v-if="hasStepMenus">
    <MsfStepParentComp :key="msfStepServiceKey" />
  </template>
  <template v-else>
    <div class="msf-detail-view">
      <component :is="getRoutingComponent(route)"></component>
    </div>
  </template>
</template>

<script setup>
import { onBeforeMount, onBeforeUpdate, ref } from 'vue'
import { useRoute } from 'vue-router'
import { useMsfMenuStore } from '@/stores/msf_menu'
import { getRoutingComponent } from '@/libs/utils/comp.utils'

const route = useRoute()
const menuStore = useMsfMenuStore()
const msfStepServiceKey = ref(0)
const hasStepMenus = ref(false)

const setHasStepMenus = () => {
  const currentMenu = menuStore.getCurrentParentMenu(route.path)
  hasStepMenus.value =
    (currentMenu.stepSubMenu && currentMenu.children && currentMenu.children.length > 0) || false
  msfStepServiceKey.value++
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

.msf-detail-view {
  @apply min-h-[calc(100vh-4rem)] bg-white;
}
</style>
