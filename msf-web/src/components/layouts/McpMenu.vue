<template>
  <nav class="menu-list">
    <div v-for="menu in menuStore.menus" :key="menu.id" class="border-b border-teal-400">
      <router-link
        :ref="`menu-${menu.id}`"
        class="parent-menu"
        :to="menu.url"
        :class="{ active: isActiveMenu(menu, route.path) }"
      >
        {{ menu.title }}
      </router-link>
      <ul
        v-if="menu.children"
        class="sub-menu-list"
        :class="{ hidden: !isActiveMenu(menu, route.path) }"
      >
        <template v-for="subMenu in menu.children" :key="subMenu.id">
          <li
            v-if="!subMenu.hidden"
            :class="{
              [`${menu.stepSubMenu ? 'step-' : ''}sub-menu`]: true,
              active: menu.stepSubMenu
                ? isActiveStepMenu(subMenu)
                : isActiveMenu(subMenu, route.path),
            }"
          >
            <span v-if="menu.stepSubMenu">
              {{ `${subMenu.step}. ${subMenu.title}` }}
            </span>
            <router-link v-else :ref="`menu-${menu.id}-${subMenu.id}`" :to="subMenu.url">
              {{ subMenu.title }}
            </router-link>
          </li>
        </template>
      </ul>
    </div>
    <div>
      <router-link ref="menu-home" to="/" class="home-menu">
        <i class="fa-solid fa-house" />
      </router-link>
    </div>
  </nav>
</template>

<script setup>
import { onBeforeMount, onBeforeUpdate } from 'vue'
import { useRoute } from 'vue-router'
import { useMsfMenuStore } from '@/stores/msf_menu'

const route = useRoute()
const menuStore = useMsfMenuStore()

const isActiveStepMenu = (menu) => {
  return menuStore.stepSubMenuUrls.includes(menu.url)
}
const isActiveMenu = (menu, routePath) => {
  if (routePath === '/') {
    return false
  }
  if (menu.url === routePath) {
    return true
  }
  if (menu.children) {
    for (const child of menu.children) {
      if (child.url === routePath) {
        return true
      }
    }
  }
  return false
}

const runStepSubMenuUrls = (menu) => {
  if (menu?.stepSubMenu) {
    menuStore.setStepSubMenuUrls(menu.url)
  } else {
    menuStore.clearStepSubMenuUrls()
  }
}

onBeforeMount(() => {
  const menu = menuStore.getCurrentParentMenu(route.path)
  runStepSubMenuUrls(menu)
})
onBeforeUpdate(() => {
  const menu = menuStore.getCurrentParentMenu(route.path)
  if (menu.stepSubMenu && menuStore.stepSubMenuUrls[0] !== menu.url) {
    runStepSubMenuUrls(menu)
  }
})
</script>

<style scoped>
@reference "tailwindcss";

.menu-list {
  @apply space-y-2 text-center;
}

.parent-menu {
  @apply font-semibold text-teal-800 block h-16 place-content-center hover:bg-teal-700! hover:text-teal-100!;
}
.parent-menu.active {
  @apply bg-teal-600 text-teal-50;
}
.home-menu {
  @apply font-semibold text-teal-800 block h-16 place-content-center hover:bg-teal-700! hover:text-teal-100! text-2xl;
}
.sub-menu-list {
  @apply text-sm;
}
.step-sub-menu,
.sub-menu {
  @apply flex text-teal-700 block h-12 p-1 place-content-center;
}
.sub-menu {
  @apply hover:bg-teal-500! hover:text-teal-100!;
}
.step-sub-menu > span,
.sub-menu > a {
  @apply flex-1 content-center;
}
.step-sub-menu > span {
  @apply pointer-events-none;
}
.step-sub-menu.active,
.sub-menu.active {
  @apply bg-teal-400! text-teal-50!;
}
.step-sub-menu:not(:last-child),
.sub-menu:not(:last-child) {
  @apply border-b border-teal-300;
}
</style>
