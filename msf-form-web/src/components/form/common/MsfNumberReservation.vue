<template>
  <div v-if="['NAC3', 'NEW', '01'].includes(customerModel.joinType)">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="번호예약" required>
        <MsfStack type="field">
          <MsfInput
            ref="reqWantFnNoRef"
            v-model="model.reqWantFnNo"
            placeholder="010"
            maxlength="3"
            class="ut-w-100"
            disabled
          />
          <span class="unit-sep">-</span>
          <MsfInput
            ref="reqWantMnNoRef"
            v-model="model.reqWantMnNo"
            placeholder="****"
            maxlength="4"
            class="ut-w-100"
            disabled
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="reqWantRnNoRef"
            v-model="model.reqWantRnNo"
            id="inp-reserve3"
            placeholder="뒤 4자리"
            maxlength="4"
            :readonly="props.disabled || !store.preChecked || !!model.wishNo"
          />
          <MsfButton
            variant="subtle"
            @click="handleNumberSearch"
            :disabled="
              props.disabled ||
              !store.preChecked ||
              (customerModel.isSaved && store.authFlags?.reserveNo) ||
              !!model.wishNo ||
              String(model.reqWantRnNo || '').length !== 4
            "
            >번호조회</MsfButton
          >
        </MsfStack>
        <p
          class="ut-text-desc"
          v-if="!store.preChecked"
          style="color: #ff5252; font-weight: 500; margin-top: 4px"
        >
          고객 가입조건 조회 완료 후 번호예약을 진행할 수 있습니다.
        </p>
        <p class="ut-text-desc" v-else>
          <span class="ut-text-count"
            >조회 가능 횟수 <em>{{ 20 - store.wishNoSearchCount }}회</em></span
          >※ 조회 가능 횟수를 초과할 경우 신청서를 재작성 해야 합니다.
        </p>
        <MsfStack type="field">
          <MsfInput
            ref="wishNoRef"
            :model-value="formattedWishNo"
            id="inp-wishNo"
            placeholder="선택된 희망 신규번호"
            class="ut-w-300"
            disabled
          />
          <MsfButton
            variant="toggle"
            @click="handleCancelNumber"
            v-if="model.wishNo"
            :disabled="props.disabled"
            >선택취소</MsfButton
          >
        </MsfStack>

        <!-- 임시스킵기능추가 -->
        <!-- 임시 입력용 폼 및 스킵 버튼 -->
        <div style="margin-top: 8px; display: flex; gap: 8px; align-items: center">
          <MsfInput
            ref="tempWishNoRef"
            v-model="tempWishNo"
            placeholder="임시 희망번호 (예: 01012345678)"
            class="ut-w-200"
            maxlength="11"
            :readonly="props.disabled || !store.preChecked"
          />
          <MsfButton
            variant="subtle"
            @click="handleSkipNumberReservation"
            :disabled="props.disabled || !store.preChecked"
            style="border: 1px dashed red; color: red"
          >
            희망번호 스킵
          </MsfButton>
        </div>
        <!-- 임시스킵기능추가 -->
      </MsfFormGroup>
    </MsfStack>

    <!-- 신규번호 검색 모달 -->
    <MsfNewNumberSearchModal
      v-model="isModalOpen"
      :searchParams="searchParams"
      @confirm="onNumberConfirm"
    />
  </div>
</template>
<script setup>
import { ref, defineModel, defineProps, computed } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { formatTelephone } from '@/libs/utils/string.utils'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import MsfNewNumberSearchModal from './popups/MsfNewNumberSearchModal.vue'
import { post } from '@/libs/api/msf.api'
import { showAlert, showConfirmAsync } from '@/libs/utils/comp.utils'

const props = defineProps({
  title: { type: String, default: '신규가입 번호 예약' },
  disabled: Boolean,
})
const model = defineModel('modelValue', { type: Object, required: true })
const customerModel = defineModel('customerData', { type: Object, required: true })
const store = useMsfFormNewChgStore()

const reqWantFnNoRef = ref(null)
const reqWantMnNoRef = ref(null)
const reqWantRnNoRef = ref(null)
const wishNoRef = ref(null)
const tempWishNoRef = ref(null)

const isModalOpen = ref(false)

const handleNumberSearch = () => {
  if (store.wishNoSearchCount >= 20) {
    showAlert('희망번호 조회 가능 횟수(20회)를 초과하였습니다.\n신청서를 재작성해 주세요.')
    return
  }

  store.incrementWishNoSearchCount()
  isModalOpen.value = true
}

const searchParams = computed(() => ({
  reqWantFnNo: model.value.reqWantFnNo,
  reqWantMnNo: model.value.reqWantMnNo,
  reqWantRnNo: model.value.reqWantRnNo,
}))

const formattedWishNo = computed(() => {
  return formatTelephone(model.value.wishNo)
})

const onNumberConfirm = async (data) => {
  try {
    const payload = {
      requestKey: store.applicationKey,
      tlpNo: (typeof data === 'object' ? data.tlphNo : data) || '',
      encdTlphNo: data.encdTlphNo || '',
      tlphNoOwnCmpnCd: data.tlphNoOwnCmncCmpnCd || '',
    }

    const res = await post('/api/form/hopenumber/reserve', payload, { timeout: 30000 })
    if (res && res.data?.resCode === '0000') {
      model.value.wishNo = payload.tlpNo
      customerModel.value.openNo = formatTelephone(payload.tlpNo)
      reserveAuthBtn.verify()
    }
  } catch (error) {
    console.error('Reserve number error:', error)
  }
}

const handleCancelNumber = async () => {
  const isConfirmed = await showConfirmAsync('예약된 번호를 취소하시겠습니까?')
  if (!isConfirmed) return

  try {
    const payload = {
      requestKey: store.applicationKey,
    }
    const res = await post('/api/form/hopenumber/cancel', payload, { timeout: 30000 })
    if (res && res.data?.resCode === '0000') {
      model.value.wishNo = ''
      customerModel.value.openNo = ''
      reserveAuthBtn.reset()
    }
  } catch (error) {
    console.error('Cancel number error:', error)
  }
}

// 임시스킵기능추가
const tempWishNo = ref('01012345678')

const handleSkipNumberReservation = () => {
  if (!tempWishNo.value.trim()) {
    showAlert('임시 희망번호를 입력해주세요.')
    return
  }
  const cleanNo = tempWishNo.value.trim().replace(/[^0-9]/g, '')
  if (cleanNo.length < 10 || cleanNo.length > 11) {
    showAlert('올바른 희망번호 형식을 입력해주세요.')
    return
  }

  const fn = cleanNo.substring(0, 3)
  const mn = cleanNo.length === 11 ? cleanNo.substring(3, 7) : cleanNo.substring(3, 6)
  const rn = cleanNo.length === 11 ? cleanNo.substring(7) : cleanNo.substring(6)

  // 1. 로컬 모델 동기화
  model.value.reqWantFnNo = fn
  model.value.reqWantMnNo = mn
  model.value.reqWantRnNo = rn
  model.value.wishNo = cleanNo
  model.value.openNo = cleanNo
  customerModel.value.openNo = formatTelephone(cleanNo)

  // 2. 스토어 상태 강제 갱신으로 백엔드 페이로드 주입 보장
  store.customer.openNo = formatTelephone(cleanNo)
  store.product.wishNo = cleanNo
  store.product.openNo = cleanNo
  store.product.reqWantFnNo = fn
  store.product.reqWantMnNo = mn
  store.product.reqWantRnNo = rn

  if (store.authFlags) {
    store.authFlags.reserveNo = true
  }
  reserveAuthBtn.verify()
}
// 임시스킵기능추가

const reserveAuthBtn = useAuthButton(
  () => [model.value?.reqWantFnNo, model.value?.reqWantMnNo, model.value?.reqWantRnNo],
  {
    get value() {
      return store.authFlags?.reserveNo || false
    },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.reserveNo = v
      }
    },
  },
)

const validate = () => {
  if (['NAC3'].includes(customerModel.value.joinType)) {
    if (!model.value.wishNo) return false
    // 희망번호 예약(wishNo)이 정상 완료된 상태라면 인증 플래그를 자동으로 보정하여 통과시킵니다.
    if (store.authFlags && !store.authFlags.reserveNo) {
      store.authFlags.reserveNo = true
    }
  }
  return true
}

const reset = () => {
  // 입력값들 '' 처리 및 기본값 복원
  if (model.value) {
    model.value.reqWantFnNo = '010'
    model.value.reqWantMnNo = '****'
    model.value.reqWantRnNo = ''
    model.value.wishNo = ''
  }

  if (store.authFlags) {
    store.authFlags.reserveNo = false
  }
}

const checkValidation = () => {
  if (!['NAC3', 'NEW', '01'].includes(customerModel.value.joinType)) {
    return true
  }

  if (['NAC3'].includes(customerModel.value.joinType)) {
    if (!model.value.wishNo) {
      showAlert(`희망 신규번호를 조회하세요`, () => {
        reqWantRnNoRef.value?.focus()
      })
      return false
    }
  }

  return true
}

defineExpose({ validate, reset, checkValidation })
</script>
