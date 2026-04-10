<template>
  <div></div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
router.replace({ name: 'not-found' })
</script>

<style scoped></style>
