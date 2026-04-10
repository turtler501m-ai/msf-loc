<template>
  <div v-if="customerModel.joinType === 'NAC3'">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="번호예약" required>
        <MsfStack type="field">
          <MsfInput v-model="model.reserve1" placeholder="앞 3자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="model.reserve2" id="inp-reserve2" placeholder="가운데 4자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="model.reserve3" id="inp-reserve3" placeholder="뒤 4자리" />
          <MsfButton variant="subtle">번호조회</MsfButton>
        </MsfStack>
        <p class="ut-text-desc"><span class="ut-text-count">조회 가능 횟수 <em>20회</em></span>※ 조회 가능 횟수를 초과할 경우 신청서를 재작성 해야 합니다.</p>
        <MsfStack type="field">
          <MsfInput v-model="model.wishNo" id="inp-wishNo" placeholder="선택된 희망 신규번호" class="ut-w-300" disabled />
          <MsfButton variant="toggle">선택취소</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'

const props = defineProps({
  title: { type: String, default: '신규가입 번호 예약' }
})
const model = defineModel('modelValue', { type: Object, required: true })
const customerModel = defineModel('customerData', { type: Object, required: true })
const store = useMsfFormNewChgStore()

const reserveAuthBtn = useAuthButton(
  () => [model.value?.reserve1, model.value?.reserve2, model.value?.reserve3],
  {
    get value() { return store.authFlags?.reserveNo || false },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.reserveNo = v
      }
    }
  }
)
</script>
