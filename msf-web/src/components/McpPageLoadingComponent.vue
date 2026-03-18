<template>
  <div class="page-loading-component">
    <Spinner />
    <p>화면 불러오는 중...</p>
  </div>
</template>

<script setup></script>

<style scoped>
@reference "tailwindcss";

.page-loading-component {
  @apply w-full min-h-screen fixed flex flex-col items-center justify-center z-10000 bg-black/25 backdrop-blur-xs;
}
</style>
