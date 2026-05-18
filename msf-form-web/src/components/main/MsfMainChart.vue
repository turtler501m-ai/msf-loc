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
          <!-- <span class="main-badge">{{ statusTotalCount }}건</span> -->
        </div>
      </div>
      <MsfDoughnutChart
        :data="statusData"
        title="처리상태별 건수"
        name-key="name"
        value-key="count"
      />
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
          <!-- <span class="main-badge">{{ serviceTotalCount }}건</span> -->
        </div>
      </div>
      <MsfDoughnutChart :data="serviceData" title="업무별 건수" name-key="name" value-key="count" />
    </div>
  </MsfStack>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue'
import { useRouter } from 'vue-router'
import { post } from '@/libs/api/msf.api'

const router = useRouter()
const moveTo = (path) => {
  router.push(path)
}

// 처리상태별 건수 차트
const statusData = ref([])
const statusTotalCount = ref(0)
// 업무별 건수 차트
const serviceData = ref([])
const serviceTotalCount = ref(0)

onBeforeMount(async () => {
  const response = await post('/api/main/form/count')
  if (response.code !== '0000') {
    return
  }
  statusData.value = response.data?.statusList || []
  statusTotalCount.value = statusData.value.reduce((sum, data) => sum + data.count, 0)
  serviceData.value = response.data?.serviceList || []
  serviceTotalCount.value = serviceData.value.reduce((sum, data) => sum + data.count, 0)
})
</script>

<style lang="scss" scoped></style>
