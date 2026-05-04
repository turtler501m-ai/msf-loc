<template>
  <main class="main-layout">
    <!-- 차트 영역-->
    <section class="main-box box-status">
      <MsfMainChart />
    </section>
    <!-- // 차트 영역-->
    <!-- Q&A -->
    <section class="main-box box-qna">
      <MsfMainQnaList />
    </section>
    <!-- // Q&A -->
    <!-- 공지사항 -->
    <section class="main-box box-notice">
      <MsfMainNoticeList />
    </section>
    <!-- // 공지사항 -->
  </main>
  <!-- 팝업목록: 개발시 새로운 파일명으로 생성하여 사용 -->
</template>

<script setup>
import { onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMsfAppStore } from '@/stores/msf_app'

const route = useRoute()
const msfAppStore = useMsfAppStore()
// 타이틀 클릭 라우터 이동
const router = useRouter()

onMounted(() => {
  if (!route.meta.skipAuth && msfAppStore.showFirst) {
    msfAppStore.setShowFirst(false)
    router.push('/login')
  }
})
</script>

<style lang="scss" scoped></style>
