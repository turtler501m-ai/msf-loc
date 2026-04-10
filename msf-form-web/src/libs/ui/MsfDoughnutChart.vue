<template>
  <div class="mcp-doughnut-chart">
    <Doughnut :data="chartData" :options="chartOptions" class="w-full" />
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { Chart as ChartJS, registerables } from 'chart.js'
import { Doughnut } from 'vue-chartjs'
import ChartDataLabels from 'chartjs-plugin-datalabels'

ChartJS.register(...registerables, ChartDataLabels)

const props = defineProps({
  data: Array,
  nameKey: {
    type: String,
    required: true,
  },
  valueKey: {
    type: String,
    required: true,
  },
})

const chartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    datalabels: {
      color: 'white',
      labels: {
        title: {
          font: {
            size: 14,
            family: 'Pretendard',
          },
        },
      },
      formatter: function (value) {
        return value + ' 개'
      },
    },
    legend: {
      position: 'bottom',
      align: 'start',
      labels: {
        cutout: '60%',
        font: {
          size: 14,
          weight: 'bold',
          family: 'Pretendard',
        },
      },
    },
  },
})

const chartLabels = computed(() =>
  props.data && props.data.length > 0 ? props.data.map((v) => v[props.nameKey]) : [],
)
const chartValues = computed(() =>
  props.data && props.data.length > 0 ? props.data.map((v) => v[props.valueKey]) : [],
)

const chartData = ref({
  labels: chartLabels,
  datasets: [
    {
      data: chartValues,
    },
  ],
})
</script>

<style lang="scss" scoped>
.mcp-doughnut-chart {
  background: var(--color-background);
  height: rem(400px);
}
</style>
