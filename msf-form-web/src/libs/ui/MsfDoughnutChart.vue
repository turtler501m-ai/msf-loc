<template>
  <div class="msf-doughnut-chart">
    <Doughnut :data="chartData" :options="chartOptions" class="ut-w-full" />
    <!-- 중앙 건수 -->
    <div class="chart-center-text">
      <strong class="total-value">{{ totalCount }}</strong>
      <span class="total-label">건수</span>
    </div>
    <!-- // 중앙 건수 -->
    <!-- 범례꾸미기 -->
    <div class="custom-legend-container">
      <ul class="custom-legend-list">
        <li v-for="(label, i) in chartLabels" :key="i" class="legend-item">
          <span
            class="swatch"
            :style="{ backgroundColor: chartData.datasets[0].backgroundColor[i] }"
          ></span>
          <span class="label-text">{{ label }}</span>
          <span class="label-value">{{ chartValues[i] }}건</span>
        </li>
      </ul>
    </div>
    <!-- // 범례꾸미기 -->
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
  cutout: '70%',
  radius: '100%',
  responsive: true,
  maintainAspectRatio: false,
  layout: {
    padding: {
      top: 16,
      bottom: 16,
      left: 0,
      right: 0,
    },
  },
  plugins: {
    // 툴팁 비활성화
    tooltip: {
      enabled: false, //마우스 호버시 툴팁이 나타나지 않음
    },
    datalabels: {
      color: '#55585D',
      clip: false,
      clamp: true,
      labels: {
        title: {
          font: {
            size: 14,
            family: 'Pretendard',
          },
        },
      },
      anchor: 'end',
      align: 'end',
      offset: -2,
      font: {
        size: 14,
        family: 'Pretendard',
        weight: 'normal',
      },
      listeners: {
        enter: function (context) {
          context.hovered = true
          return true
        },
        leave: function (context) {
          context.hovered = false
          return true
        },
      },
      display: true,
    },
    legend: {
      display: false, //커스텀 범례사용 하기위해 비활성화
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
      backgroundColor: ['#00A39B', '#8DCDCB', '#CCE7E8', '#E0EEEF'], //디자이너가 지정한 컬러
    },
  ],
})

// 총합계 계산
const totalCount = computed(() => {
  return chartValues.value.reduce((acc, cur) => acc + Number(cur), 0).toLocaleString()
})
</script>

<style lang="scss" scoped>
.msf-doughnut-chart {
  position: relative;
  width: rem(230px);
  height: rem(208px); //176 + 16 + 16(디자이너가 설정한 여백크기까지 더함(dataLabel 짤림))
  // 중앙 총 건수 표현
  .chart-center-text {
    position: absolute;
    top: 50%; // 도넛 위치에 따라 미세하게 조정 (범례가 아래에 있으므로 40~45% 추천)
    left: 50%;
    transform: translate(-50%, -50%);
    @include flex($d: column, $v: center, $h: center) {
      gap: rem(2px);
    }
    pointer-events: none; // 마우스 이벤트 차단
    // 총 건수
    .total-value {
      font-size: var(--font-size-32);
      font-weight: var(--font-weight-bold);
      line-height: var(--line-height-fit);
      color: var(--color-gray-900);
      letter-spacing: var(--letter-spacing-base);
    }
    // '건수' 텍스트
    .total-label {
      font-size: var(--font-size-14);
      line-height: var(--line-height-fit);
      color: var(--color-gray-600);
      margin-bottom: rem(2px);
      letter-spacing: var(--letter-spacing-base);
    }
  }

  // 커스텀 범례
  .custom-legend-container {
    width: 100%;
    @include flex($h: center);
    // margin-top: rem(16px);
  }
  // 범례 리스트 (ul)
  .custom-legend-list {
    @include flex($h: flex-start, $w: wrap) {
      gap: rem(8px) rem(16px); // 아이템 간 간격
    }
    line-height: var(--line-height-fit);
    // 범례 아이템 (li)
    .legend-item {
      flex-basis: calc(50% - 8px);
      @include flex($v: center);
      font-family: var(--font-family-base);
      // 컬러 스와치 스타일 (정사각형)
      .swatch {
        width: rem(4px); // 크기 조절
        height: rem(4px);
        margin-right: rem(4px); // 텍스트와의 간격
        flex-shrink: 0; // 크기 고정
      }
      // 범례의 텍스트 스타일
      .label-text {
        font-size: rem(12px);
        font-weight: var(--font-weight-medium);
        color: var(--color-gray-600);
        margin-right: rem(4px); // 값과의 간격
      }
      .label-value {
        font-size: rem(12px);
        font-weight: var(--font-weight-medium);
        color: var(--color-gray-600);
      }
    }
  }
}
</style>
