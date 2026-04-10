<template>
  <main>
    {{ msfApiUrl }}

    <!-- 컨텐츠 내용 넣기 -->
    <MsfButtonGroup align="center">
      <router-link to="/guide" target="_blank">
        <MsfButton role="link">퍼블리싱 가이드 바로가기</MsfButton>
      </router-link>
      <router-link to="/publishing" target="_blank">
        <MsfButton role="link">퍼블리싱 목록 바로가기</MsfButton>
      </router-link>
      <router-link to="/test" target="_blank">
        <MsfButton role="link">컴퍼넌트 테스트 바로가기</MsfButton>
      </router-link>
    </MsfButtonGroup>
  </main>
</template>

<script setup>
import { ref } from 'vue'

const msfApiUrl = ref(import.meta.env.VITE_MSF_API_URL)
</script>

<style scoped></style>
