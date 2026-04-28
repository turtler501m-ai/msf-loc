<template>
  <div v-if="customerModel.joinType === 'NAC3'">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="번호예약" required>
        <MsfStack type="field">
          <MsfNumberInput
            v-model="model.reqWantFnNo"
            placeholder="앞자리"
            maxlength="3"
            :readonly="customerModel.isSaved || !!model.wishNo"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.reqWantMnNo"
            id="inp-reserve2"
            placeholder="가운데 4자리"
            maxlength="4"
            :readonly="customerModel.isSaved || !!model.wishNo"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.reqWantRnNo"
            id="inp-reserve3"
            placeholder="뒤 4자리"
            maxlength="4"
            :readonly="customerModel.isSaved || !!model.wishNo"
          />
          <MsfButton
            variant="subtle"
            @click="isModalOpen = true"
            :disabled="(customerModel.isSaved && store.authFlags?.reserveNo) || !!model.wishNo"
            >번호조회</MsfButton
          >
        </MsfStack>
        <p class="ut-text-desc">
          <span class="ut-text-count">조회 가능 횟수 <em>20회</em></span
          >※ 조회 가능 횟수를 초과할 경우 신청서를 재작성 해야 합니다.
        </p>
        <MsfStack type="field">
          <MsfInput
            v-model="model.wishNo"
            id="inp-wishNo"
            placeholder="선택된 희망 신규번호"
            class="ut-w-300"
            disabled
          />
          <MsfButton
            variant="toggle"
            @click="handleCancelNumber"
            v-if="model.wishNo"
            :disabled="customerModel.isSaved && store.authFlags?.reserveNo"
            >선택취소</MsfButton
          >
        </MsfStack>
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
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import MsfNewNumberSearchModal from './popups/MsfNewNumberSearchModal.vue'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  title: { type: String, default: '신규가입 번호 예약' },
})
const model = defineModel('modelValue', { type: Object, required: true })
const customerModel = defineModel('customerData', { type: Object, required: true })
const store = useMsfFormNewChgStore()

const isModalOpen = ref(false)

const searchParams = computed(() => ({
  reqWantFnNo: model.value.reqWantFnNo,
  reqWantMnNo: model.value.reqWantMnNo,
  reqWantRnNo: model.value.reqWantRnNo,
}))

const onNumberConfirm = async (number) => {
  try {
    const res = await post('/api/form/newchange/reserveNumber', {
      wishNo: number,
      ...searchParams.value,
    })
    if (res && res.code === '0000') {
      model.value.wishNo = number
      reserveAuthBtn.verify()
      alert('번호 예약이 완료되었습니다.')
    } else {
      alert(res.message || '번호 예약에 실패했습니다.')
    }
  } catch (error) {
    console.error('Reserve number error:', error)
  }
}

const handleCancelNumber = async () => {
  if (!confirm('예약된 번호를 취소하시겠습니까?')) return

  try {
    const res = await post('/api/form/newchange/cancelNumber', {
      wishNo: model.value.wishNo,
    })
    if (res && res.code === '0000') {
      model.value.wishNo = ''
      reserveAuthBtn.reset()
      alert('번호 예약이 취소되었습니다.')
    } else {
      alert(res.message || '번호 예약 취소에 실패했습니다.')
    }
  } catch (error) {
    console.error('Cancel number error:', error)
  }
}

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
  if (customerModel.value.joinType === 'NAC3') {
    if (!model.value.wishNo) return false
    if (!store.authFlags?.reserveNo) return false
  }
  return true
}

defineExpose({ validate })
</script>
