<template>
  <div>
    <!-- 1. 고정 헤더 (Header) -->
    <header class="header-layout">
      <!-- <h1 class="text-lg font-bold">KT M mobile</h1> -->
      <img src="/logo.png" alt="KT M mobile" class="h-10" />
      <a href="/login"><i class="fa-solid fa-power-off" /></a>
    </header>
    <!-- 헤더 아래 공간 (레이아웃) -->
    <div class="main-layout">
      <!-- 2. 고정 좌측 메뉴 (Sidebar) -->
      <aside class="left-layout">
        <mcp-menu />
      </aside>
      <!-- 3. 메인 콘텐츠 (Main) - 전체 영역 차지 -->
      <main class="content-layout">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import McpMenu from '@/components/layouts/McpMenu.vue'
</script>

<style scoped>
@reference "tailwindcss";

.header-layout {
  @apply fixed top-0 left-0 w-full h-8 z-50 bg-teal-500 text-white shadow-sm flex items-center justify-between px-4;
}

.main-layout {
  @apply flex pt-8 w-full min-h-screen;
}

.left-layout {
  @apply fixed left-0 top-8 w-25 h-full bg-teal-100 p-2 overflow-y-auto;
}

.content-layout {
  @apply pl-29 pt-4 pr-4 w-full grow bg-white;
}
</style>
