<template>
  <MsfTitleBar :title="currentTitle" />
  <MsfTab v-model="currentTabId" :items="menuTabItems" @change="handleTabChange">
    <template v-for="item in menuTabItems" :key="item.value" #[item.value]>
      <component :is="currentComponent"></component>
    </template>
  </MsfTab>
</template>

<script setup>
import { computed, onBeforeMount, onBeforeUpdate, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMsfMenuStore } from '@/stores/msf_menu'
import { getExtraComponent } from '@/libs/utils/comp.utils'

const route = useRoute()
const menuStore = useMsfMenuStore()

const currentParentMenu = ref(null)
const currentSubMenu = ref(null)
const currentComponent = computed(() => getExtraComponent(route.params?.pathes))
const currentTitle = computed(() => currentParentMenu.value?.name)

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

// 탭
const router = useRouter() // 페이지이동을 위하여
// 1. MsfTab용 데이터로 변환
const menuTabItems = computed(() => {
  return (
    currentParentMenu.value?.children?.map((m) => ({
      label: m.name,
      value: m.id, // 이 ID가 슬롯 이름(#[item.value])
      url: m.url,
    })) || []
  )
})
// 2. 현재 선택된 탭 (v-model) - 현재 메뉴 ID와 동기화
const currentTabId = computed({
  get: () => currentSubMenu.value?.id,
  set: () => {}, // handleTabChange에서 라우터 이동으로 처리하므로 비워둠
})

// 3. 탭 변경 시 해당 메뉴의 URL로 이동
const handleTabChange = (val) => {
  const targetMenu = menuTabItems.value.find((item) => item.value === val)
  if (targetMenu && targetMenu.url) {
    router.push(targetMenu.url)
  }
}
</script>

<style scoped></style>
