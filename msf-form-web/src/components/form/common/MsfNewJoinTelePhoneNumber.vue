<template>
  <!-- 번호 변경 -->
  <MsfTitleArea title="번호 변경" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="번호예약" required>
      <MsfStack type="field">
        <MsfNumberInput v-model="model.reqWantFnNo" placeholder="010" maxlength="3" disabled />
        <span class="unit-sep">-</span>
        <MsfNumberInput
          v-model="model.reqWantMnNo"
          placeholder="****"
          maxlength="4"
          class="ut-w-100"
          disabled
        />
        <span class="unit-sep">-</span>
        <MsfNumberInput
          v-model="model.reqWantRnNo"
          id="inp-reserve3"
          placeholder="뒤 4자리"
          maxlength="4"
          :readonly="!!model.wishNo"
        />
        <MsfButton
          variant="subtle"
          @click="handleNumberSearch"
          :disabled="
            store.authFlags?.numberChgConfirmCompleted ||
            !!model.wishNo ||
            String(model.reqWantRnNo || '').length !== 4
          "
          >번호조회</MsfButton
        >
      </MsfStack>
      <p class="ut-text-desc">
        <span class="ut-text-count"
          >조회 가능 횟수 <em>{{ 20 - store.wishNoSearchCount }}회</em></span
        >※ 조회 가능 횟수를 초과할 경우 신청서를 재작성 해야 합니다.
      </p>
      <MsfStack type="field">
        <MsfInput
          :model-value="formattedWishNo"
          id="inp-wishNo"
          placeholder="선택된 희망 신규번호"
          class="ut-w-300"
          disabled
        />
        <MsfButton variant="toggle" @click="handleCancelNumber" v-if="model.wishNo"
          >선택취소</MsfButton
        >
      </MsfStack>
    </MsfFormGroup>
  </MsfStack>
  <!-- // 신규가입 번호 예약 -->

  <!-- 신규번호 검색 모달 -->
  <MsfNewNumberSearchModal
    v-model="isModalOpen"
    :searchParams="searchParams"
    @confirm="onNumberConfirm"
  />
</template>
<script setup>
import { ref, defineModel, defineProps, onMounted, computed, watch } from 'vue'
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { useAuthButton } from '@/hooks/useAuthButton'
import { formatTelephone } from '@/libs/utils/string.utils'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'

import MsfNewNumberSearchModal from './popups/MsfNewNumberSearchModal.vue'

const emit = defineEmits(['ready'])

// ─── 로그 접두사 ──────────────────────────────────────────────────────────────
const getLogPrefix = (task) => `[변경][분실복구/일시정지해제][${task}]`

// ─── 상태 (State) ─────────────────────────────────────────────────────────────
const store = useMsfFormSvcChgStore()

const model = defineModel('modelValue', { type: Object, required: true })
const props = defineProps({
  title: { type: String, default: '번호 변경' },
})
const isModalOpen = ref(false)

const searchParams = computed(() => ({
  pageMode: 'CHG',
  reqWantFnNo: model.value.reqWantFnNo,
  reqWantMnNo: model.value.reqWantMnNo,
  reqWantRnNo: model.value.reqWantRnNo,
  ncn: model.value.ncn || model.value.contractNum || '',
  ctn: `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`,
  custId: model.value.custId || '',
}))

const formattedWishNo = computed(() => {
  return formatTelephone(model.value.wishNo)
})

// ─── 버튼 핸들러 ──────────────────────────────────────────────────────────

const handleNumberSearch = () => {
  if (store.wishNoSearchCount >= 20) {
    showAlert('희망번호 조회 가능 횟수(20회)를 초과하였습니다.\n신청서를 재작성해 주세요.')
    return
  }
  store.incrementWishNoSearchCount()
  isModalOpen.value = true
}

// Modal callback
const onNumberConfirm = async (data) => {
  try {
    const payload = {
      tlpNo: (typeof data === 'object' ? data.ctn : data) || '',
      tlpNoc: (typeof data === 'object' ? data.sctn : data) || '',
      tlpMarket: (typeof data === 'object' ? data.marketGubun : data) || '',
    }

    store.authFlags.numberChg = false
    model.value.numberChgConfirmCompleted = false

    model.value.wishNo = payload.tlpNo
    model.value.wishNoc = payload.tlpNoc
    model.value.wishMarket = payload.tlpMarket
    if (model.value.wishNo !== '') {
      store.authFlags.numberChg = true
      model.value.numberChgConfirmCompleted = true
    }
    reserveAuthBtn.verify()
  } catch (error) {
    console.error('Reserve number error:', error)
  }
}

// 선택 취소
const handleCancelNumber = async () => {
  //if (!confirm('예약된 번호를 취소하시겠습니까?')) return
  showConfirm(
    '예약된 번호를 취소하시겠습니까?',
    () => {
      model.value.wishNo = ''
      model.value.wishNoc = ''
      model.value.wishMarket = ''
      store.authFlags.numberChg = false
      model.value.numberChgConfirmCompleted = false

      reserveAuthBtn.reset()
    },
    '',
    () => {}
  )
}

const reserveAuthBtn = useAuthButton(
  () => [model.value?.reqWantFnNo, model.value?.reqWantMnNo, model.value?.reqWantRnNo],
  {
    get value() {
      return store.authFlags?.numberChg || false
    },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.numberChg = v
      }
    },
  },
)

watch(
  () => store.cancelAuthResetKey,
  (val, old) => {
    if (typeof old === 'number') {
      reserveAuthBtn.reset()
    }
  },
)

const validate = () => {
  if (!model.value.wishNo) return false
  if (!model.value.wishNoc) return false
  if (!model.value.wishMarket) return false
  //if (!store.authFlags?.numberChg) return false
  return true
}

defineExpose({ validate })

// ─── 라이프사이클 & 이벤트 ─────────────────────────────────────────────────

onMounted(() => {
  //console.log(`${getLogPrefix('초기화')} mounted`)

  store.authFlags.numberChg = false
  model.value.numberChgConfirmCompleted = false

  model.value.wishNo = ''
  model.value.wishNoc = ''
  model.value.wishMarket = ''
  emit('ready')
})
</script>
