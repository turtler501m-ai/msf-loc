<template>
  <div class="p-8 text-center text-gray-500">
    <p class="text-lg font-semibold mb-2">화면을 불러올 수 없습니다.</p>
    <p class="text-sm">{{ error?.message }}</p>
  </div>
</template>

<script setup>
defineProps({ error: Object })
</script>
