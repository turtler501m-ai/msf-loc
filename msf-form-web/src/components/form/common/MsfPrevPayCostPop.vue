<!-- 예상 납부금액 버튼 + 팝오버 -->
<template>
  <MsfPopover :is-open="isOpen" v-bind="$attrs" @update:is-open="onClose" title="예상 납부금액">
    <template #trigger>
      <MsfButton variant="accent1" @click="handlePrevPayCost">예상 납부금액</MsfButton>
    </template>
    <!-- cost-wrap -->
    <div class="cost-wrap">
      <!-- cost-list -->
      <div class="cost-list">
        <div class="cost-item">
          <dl class="cost-title">
            <dt>월 단말요금</dt>
            <dd>{{ formatCurrency(deviceMonthlyCost) }} 원</dd>
          </dl>
          <ul class="cost-infos">
            <li>
              <dl>
                <dt>단말기 출고가</dt>
                <dd>{{ formatCurrency(amtInfo.hndsetAmt) }} 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>공시지원금</dt>
                <dd class="ut-color-accent">- {{ formatCurrency(amtInfo.subsdAmt) }} 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>대리점보조금</dt>
                <dd class="ut-color-accent">- {{ formatCurrency(amtInfo.agncySubsdAmt) }} 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>할부원금</dt>
                <dd>{{ formatCurrency(amtInfo.instAmt) }} 원</dd>
              </dl>
            </li>
            <li v-if="amtInfo.instCmsn > 0">
              <dl>
                <dt>총할부수수료</dt>
                <dd>{{ formatCurrency(amtInfo.instCmsn) }} 원</dd>
              </dl>
            </li>
          </ul>
        </div>
        <div class="cost-item">
          <dl class="cost-title">
            <dt>월 통신요금</dt>
            <dd>{{ formatCurrency(planMonthlyCost) }} 원</dd>
          </dl>
          <ul class="cost-infos">
            <li>
              <dl>
                <dt>기본요금</dt>
                <dd>{{ formatCurrency(amtInfo.baseAmt) }} 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>프로모션 할인</dt>
                <dd class="ut-color-accent">
                  - {{ formatCurrency(amtInfo.dcAmt + amtInfo.addDcAmt) }} 원
                </dd>
              </dl>
            </li>
          </ul>
        </div>
        <div class="cost-item">
          <dl class="cost-title">
            <dt>기타요금</dt>
            <dd>{{ formatCurrency(amtInfo.joinFee + amtInfo.usimFee) }} 원</dd>
          </dl>
          <ul class="cost-infos">
            <li v-if="amtInfo.joinFee > 0">
              <dl>
                <dt>가입비(3개월 분납)</dt>
                <dd>{{ formatCurrency(Math.floor(amtInfo.joinFee)) }} 원</dd>
              </dl>
            </li>
            <li v-if="amtInfo.usimFee > 0">
              <dl>
                <dt>USIM/eSIM(최초 1회)</dt>
                <dd>{{ formatCurrency(amtInfo.usimFee) }} 원</dd>
              </dl>
            </li>
          </ul>
        </div>
      </div>
      <!-- // cost-list -->
      <!-- cost-total -->
      <div class="cost-total">
        <dl>
          <dt>월 납부금액(부가세 포함)</dt>
          <dd class="ut-color-point">{{ formatCurrency(totalMonthlyCost) }} 원</dd>
        </dl>
        <span class="cost-desc"
          >{{ store.customer.prdtSctnCd }} / {{ getJoinTypeName(store.customer.joinType) }} /
          {{ store.customer.prodNm }}</span
        >
      </div>
      <!-- // cost-total -->
    </div>
    <!-- // cost-wrap -->
    <!-- 안내 문구박스 영역 -->
    <MsfBox padding="16">
      <MsfTextList :items="['가입비 및 USIM비 등 기타요금은 별도 청구 됩니다.']" level="1" />
    </MsfBox>
    <MsfBox padding="16">
      <MsfTextList
        :items="[
          'eSIM비는 다운로드에 상관없이 최초 개통 시 1회 발생되며,<br/>프로파일 삭제 등으로 재 다운로드 시 추가 발생합니다.',
          '월 납부금액은 부가서비스 등의 사용에 따라 추가금액이 합산되어 청구 될 수 있습니다.',
          '월 납부금액은 부가세 포함 금액입니다.',
          '타사향(SK, LG U+ 등) 단말은 일부 서비스(MMS,영상통화, 교통카드 기능 등) 이용이 제한될 수 있습니다.',
        ]"
        level="1"
      />
    </MsfBox>
    <!-- // 안내 문구박스 영역 -->
  </MsfPopover>
</template>

<script setup>
import { computed } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  isOpen: { type: Boolean, default: false },
})

const emit = defineEmits(['update:isOpen', 'triggerClick', 'close'])
const store = useMsfFormNewChgStore()
const amtInfo = computed(
  () =>
    store.product.estimatedAmtInfo || {
      hndsetAmt: 0,
      subsdAmt: 0,
      agncySubsdAmt: 0,
      instAmt: 0,
      instCmsn: 0,
      baseAmt: 0,
      dcAmt: 0,
      addDcAmt: 0,
      joinFee: 0,
      usimFee: 0,
    },
)

// 가입유형 이름 매핑
const getJoinTypeName = (type) => {
  const map = {
    MNP3: '번호이동',
    NAC3: '신규가입',
    HDN3: '기기변경',
    HCN3: '기기변경(HCN3)',
  }
  return map[type] || '번호이동'
}

// 금액 포맷팅 (콤마 추가)
const formatCurrency = (value) => {
  if (!value) return '0'
  return value.toLocaleString('ko-KR')
}

// 월 단말 요금 (할부원금 + 할부수수료) / 할부기간 (간단 계산용)
const deviceMonthlyCost = computed(() => {
  const months = Number(store.customer.installmentMonth) || 24
  if (months === 0) return 0
  return Math.floor((amtInfo.value.instAmt + amtInfo.value.instCmsn) / months)
})

// 월 통신 요금 (기본료 - 할인)
const planMonthlyCost = computed(() => {
  return amtInfo.value.baseAmt - (amtInfo.value.dcAmt + amtInfo.value.addDcAmt)
})

// 총 월 납부금액 계산
const totalMonthlyCost = computed(() => {
  return deviceMonthlyCost.value + planMonthlyCost.value
})

//  닫기 이벤트
const onClose = (value) => {
  if (!value || props.isOpen) {
    emit('update:isOpen', false)
    emit('close')
  }
}

/**
 * 상세 가격 정보 및 예상 금액 조회
 */
const fetchPriceInfo = async () => {
  const m = store.customer
  const p = store.product

  const payload = {
    reqBuyTypeCd: m.productType || 'MM',
    operTypeCd: m.joinType || 'MNP3',
    rateCd: m.prodId,
    dataType: m.prdtSctnCd || 'LTE',
    agentCd: m.agentCd || '',
  }

  // 휴대폰 전용 필드 추가
  if (m.productType === 'MM') {
    payload.modelId = m.modelId
    payload.modelMonthly = m.installmentMonth
    payload.agrmTrm = m.contractPeriod
    payload.salePlcyCd = m.modelSalePolicyCd
    payload.sprtTp = m.discountType

    // 유심 종류 (usimKindsCd) 동적 설정
    let usimCd = '08' // 기본값
    if (p.hasSim === 'hasSim1') {
      usimCd = '06' // 유심보유
    } else if (p.hasSim === 'hasSim2') {
      usimCd = m.prdtSctnCd === '5G' ? '07' : '02' // 유심구매 (5G: 07, LTE: 02)
    } else if (p.hasSim === 'hasSim3') {
      usimCd = '09' // eSIM
    }
    payload.usimKindsCd = usimCd
  }

  try {
    // 통합 판매 가격 정보 조회 (오류 확인을 위해 silent 제거)
    const res = await post('/api/form/phone/mspsaleprice/get', payload)

    const result = {
      ...p.estimatedAmtInfo,
      hndsetAmt: 0,
      subsdAmt: 0,
      agncySubsdAmt: 0,
      instAmt: 0,
      instCmsn: 0,
      baseAmt: 0,
      dcAmt: 0,
      addDcAmt: 0,
      joinFee: 0,
      usimFee: 0,
    }

    if (res?.data) {
      const d = res.data.resData || res.data
      // API 실제 응답 키와 명세 키 혼합 지원 (실제 응답 키 우선)
      result.hndsetAmt = Number(d.hndstAmt || d.MODEL_PRICE || 0)
      result.subsdAmt = Number(d.subsdAmt || d.MODEL_SPRT || 0)
      result.agncySubsdAmt = Number(d.agncySubsdAmt || d.MODEL_DISCOUNT3 || 0)

      // 일시납일 경우 할부원금 0원 처리
      const isUpfront = String(m.installmentMonth) === '0'
      result.instAmt = isUpfront ? 0 : Number(d.instAmt || d.REAL_MDL_INSTAMT || 0)

      // 총할부수수료가 API에서 totalInstCmsn으로 내려온다면 해당 값 매핑
      result.instCmsn = Number(d.totalInstCmsn || d.instCmsn || 0)
      result.baseAmt = Number(d.baseAmt || d.SOC_BASE_CHRG_AMT || 0)
      result.dcAmt = Number(d.dcAmt || d.DC_AMT || 0)
      result.addDcAmt = Number(d.addDcAmt || d.ADD_DC_AMT || 0)

      const isJoinPaid = d.joinIsPay ? d.joinIsPay === 'Y' : true
      result.joinFee = isJoinPaid ? Number(d.joinPrice || d.JOIN_PRICE || 0) : 0

      const isSimPaid =
        d.simIsPay || d.nfcSimIsPay ? d.simIsPay === 'Y' || d.nfcSimIsPay === 'Y' : true
      result.usimFee = isSimPaid ? Number(d.simPrice || d.usimPrice || d.USIM_PRICE || 0) : 0
    }

    store.product.estimatedAmtInfo = result
    console.log('>>> 예상 납부금액 조회 완료:', store.product.estimatedAmtInfo)
    return true
  } catch (error) {
    console.error('Failed to fetch price info:', error)
    return false
  }
}

// 트리거 버튼 클릭
const handlePrevPayCost = async () => {
  const m = store.customer

  // 필수값 체크 및 안내
  if (m.productType === 'MM') {
    if (!m.deviceModel) {
      showAlert('휴대폰을 먼저 선택해 주세요.')
      return
    }
    if (!m.prodId) {
      showAlert('요금제를 먼저 선택해 주세요.')
      return
    }
    if (
      m.installmentMonth === undefined ||
      m.installmentMonth === null ||
      m.installmentMonth === ''
    ) {
      showAlert('할부기간을 선택해 주세요.')
      return
    }
  } else if (m.productType === 'UU') {
    if (!m.prodId) {
      showAlert('요금제를 먼저 선택해 주세요.')
      return
    }
  }

  const success = await fetchPriceInfo() // 데이터 먼저 조회
  if (success) {
    emit('triggerClick')
  }
}
</script>

<style lang="scss" scoped>
.cost-wrap {
  --cost-inner-space-gap: #{rem(12px)}; // 사이 간격 지정
  .cost-list {
    width: 100%;
    .cost-item {
      width: 100%;
      &:not(:first-child) {
        border-top: 1px solid var(--color-gray-75);
        padding-top: var(--cost-inner-space-gap);
        margin-top: var(--cost-inner-space-gap);
      }
      .cost-title {
        font-size: var(--font-size-14);
        font-weight: var(--font-weight-bold);
        @include flex($h: space-between) {
          gap: var(--cost-inner-space-gap);
        }
        margin-bottom: var(--cost-inner-space-gap);
        & > dd {
          flex-shrink: 0;
          flex-grow: 0;
        }
      }
      ul.cost-infos {
        width: 100%;
        @include flex($d: column) {
          gap: var(--cost-inner-space-gap);
        }
        & > li {
          & > dl {
            @include flex($h: space-between, $v: baseline) {
              gap: var(--cost-inner-space-gap);
            }
            font-size: var(--font-size-14);
            font-weight: var(--font-weight-regular);
            & > dt {
              color: var(--color-gray-600);
            }
            & > dd {
              font-weight: var(--font-weight-medium);
              flex-shrink: 0;
              flex-grow: 0;
            }
          }
        }
      }
    }
  }
  .cost-total {
    margin-top: var(--cost-inner-space-gap);
    padding-top: var(--cost-inner-space-gap);
    border-top: var(--border-width-base) solid var(--color-gray-300);
    font-size: var(--font-size-20);
    font-weight: var(--font-weight-bold);
    & > dl {
      @include flex($h: space-between) {
        gap: var(--cost-inner-space-gap);
      }
      & > dt {
        @include flex($d: column) {
          gap: rem(4px);
        }
      }
      & > dd {
        flex-shrink: 0;
        flex-grow: 0;
      }
    }
    .cost-desc {
      display: block;
      font-size: var(--font-size-14);
      font-weight: var(--font-weight-regular);
      color: var(--color-gray-600);
    }
  }
}
</style>
