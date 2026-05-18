<template>
  <div class="msf-doughnut-chart">
    <Doughnut
      :data="chartData"
      :options="chartOptions"
      :plugins="[emptyDoughnut]"
      class="ut-w-full"
    />
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
import { computed, ref, watch } from 'vue'
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

// 플러그인 정의
const emptyDoughnut = ref({
  id: 'emptyDoughnut',
  afterDraw(chart, args, options) {
    const { datasets } = chart.data
    const { color } = options // 고정값 대신 계산식을 쓰므로 color만 가져옴
    // 데이터가 0이거나 모두 0인지 확인
    const hasData = datasets.some((ds) => ds.data.some((v) => v > 0))

    if (!hasData) {
      const {
        chartArea: { top, bottom, left, right },
        ctx,
      } = chart
      const centerX = (left + right) / 2
      const centerY = (top + bottom) / 2
      const r = Math.min(right - left, bottom - top) / 2

      // 데이터 있을때 70% cutout과 동일한 디자인 맞춤
      const thickness = r * 0.3

      ctx.beginPath()
      ctx.lineWidth = thickness // 선 두께
      ctx.strokeStyle = color || '#D5D8DD' // 색상

      // 반지름 위치 보정: 두께의 절반만큼 안으로 밀어넣어야 바깥선이 일치함
      ctx.arc(centerX, centerY, r - thickness / 2, 0, 2 * Math.PI)
      ctx.stroke()
    }
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
      display: (context) => {
        // 데이터가 아예 없을 때는 라벨을 아예 그리지 않음
        const dataset = context.chart.data.datasets[0]
        return dataset.data.some((v) => v > 0)
      },
    },
    legend: {
      display: false, //커스텀 범례사용 하기위해 비활성화
    },
    emptyDoughnut: {
      color: '#D5D8DD', // 빈 데이터일 때 색상
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

watch(
  () => props.data,
  (newVal) => {
    chartData.value = {
      labels: newVal.map((v) => v[props.nameKey]) || [],
      datasets: [
        {
          data: newVal.map((v) => v[props.valueKey]) || [],
          backgroundColor: ['#00A39B', '#8DCDCB', '#CCE7E8', '#E0EEEF'], //디자이너가 지정한 컬러
        },
      ],
    }
  },
  { immediate: true, deep: true },
)

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
