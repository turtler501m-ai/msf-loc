<template>
  <component :is="currentContent" v-if="currentContent" />
  <div v-else>
    <h3>해당 화면({{ screenId }})을 찾을 수 없습니다.</h3>
  </div>
</template>

<script setup>
import { computed, defineAsyncComponent } from 'vue'

const props = defineProps({
  screenId: String,
})

// 폴더 안에 있는 모든 vue 파일을 탐색
const components = import.meta.glob('./pages/pub/*.vue')

const currentContent = computed(() => {
  // 파일 경로 패턴: ./pub/화면ID.vue
  const path = `./pages/pub/${props.screenId}.vue`

  if (components[path]) {
    return defineAsyncComponent(components[path])
  }
  return null
})
</script>
