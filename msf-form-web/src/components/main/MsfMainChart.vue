<template>
  <MsfStack class="status-stack" nowrap>
    <div class="ut-flex-1">
      <div class="main-title">
        <h3
          class="main-tit"
          tabindex="0"
          @click="moveTo('/extra/receipt/ReceiptPage')"
          @keydown.enter="moveTo('/extra/receipt/ReceiptPage')"
          @keydown.space.prevent="moveTo('/extra/receipt/ReceiptPage')"
        >
          처리상태별 건수<MsfIcon name="arrowRight" size="small" />
        </h3>
        <div class="main-tit-side">
          <span class="main-badge">90건</span>
        </div>
      </div>
      <MsfDoughnutChart :data="statusData" title="처리상태별 건수" name-key="nm" value-key="cnt" />
    </div>
    <div class="ut-flex-1">
      <div class="main-title">
        <h3
          class="main-tit"
          tabindex="0"
          @click="moveTo('/extra/receipt/ReceiptPage')"
          @keydown.enter="moveTo('/extra/receipt/ReceiptPage')"
          @keydown.space.prevent="moveTo('/extra/receipt/ReceiptPage')"
        >
          업무별 건수<MsfIcon name="arrowRight" size="small" />
        </h3>
        <div class="main-tit-side">
          <span class="main-badge">114건</span>
        </div>
      </div>
      <MsfDoughnutChart :data="serviceData" title="업무별 건수" name-key="nm" value-key="cnt" />
    </div>
  </MsfStack>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const moveTo = (path) => {
  router.push(path)
}

// 처리상태별 건수 차트
const statusData = ref([
  { nm: '접수처리완료', cnt: 60 },
  { nm: '이미지전송완료', cnt: 10 },
  { nm: '신청중', cnt: 20 },
])
// 업무별 건수 차트
const serviceData = ref([
  { nm: '신규/변경', cnt: 60 },
  { nm: '서비스변경', cnt: 30 },
  { nm: '명의변경', cnt: 14 },
  { nm: '서비스해지', cnt: 10 },
])
</script>

<style lang="scss" scoped></style>
