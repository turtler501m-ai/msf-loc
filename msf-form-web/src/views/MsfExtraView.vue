<template>
  <div>
    <ul>
      <li
        v-for="(m, i) in currentParentMenu?.children"
        :key="i"
        :class="{ active: m.id === currentSubMenu?.id }"
      >
        <router-link :to="m.url">
          {{ m.name }}
        </router-link>
      </li>
    </ul>
    <!-- 타이틀 -->
    <MsfTitleBar>
      <template #title>
        <slot name="title">{{ currentSubMenu?.name }}</slot>
      </template>
    </MsfTitleBar>
    <component :is="currentComponent"></component>
  </div>
</template>

<script setup>
import { computed, onBeforeMount, onBeforeUpdate, ref } from 'vue'
import { useRoute } from 'vue-router'
import { useMsfMenuStore } from '@/stores/msf_menu'
import { getExtraComponent } from '@/libs/utils/comp.utils'

const route = useRoute()
const menuStore = useMsfMenuStore()

const currentParentMenu = ref(null)
const currentSubMenu = ref(null)
const currentComponent = computed(() => getExtraComponent(route.params?.pathes))

const setCurrentComponent = () => {
  currentParentMenu.value = menuStore.getParentMenu('/extra')
  currentSubMenu.value = menuStore.getSubMenu(`/extra/${route.params?.pathes.join('/')}`)
}

onBeforeMount(() => {
  setCurrentComponent()
})
onBeforeUpdate(() => {
  setCurrentComponent()
})
</script>

<style scoped></style>
