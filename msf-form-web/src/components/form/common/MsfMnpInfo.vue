<template>
  <div v-if="customerModel.joinType === 'MNP3'">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="번호이동 할<br/>전화번호" required>
        <MsfStack type="field">
          <MsfSelect title="통신사 선택" v-model="model.carrier" :options="[{ label: '통신사1', value: 'agency1' }, { label: '통신사2', value: 'agency2' }]" class="ut-w-300" placeholder="통신사 선택" />
          <MsfInput v-model="model.transferPhone" placeholder="휴대폰 번호 ‘-’ 없이 입력" class="ut-w-300" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="번호이동 인증" tag="div" required>
        <MsfStack type="field">
          <MsfChip v-model="model.transferAuth" name="inp-transferAuth" :data="[{ value: 'transferAuth1', label: '휴대폰 일련번호' }, { value: 'transferAuth2', label: '요금납부 계좌번호' }, { value: 'transferAuth3', label: '요금납부 신용카드' }, { value: 'transferAuth4', label: '지로' }]" />
        </MsfStack>
        <MsfStack type="field">
          <MsfInput v-model="model.transferAuthNum" placeholder="휴대폰 일련번호 뒤 4자리" class="ut-w-300" />
          <MsfButton variant="subtle">번호이동 사전동의</MsfButton>
        </MsfStack>
        <MsfInput v-model="model.transferBankNum" placeholder="요금납부 계좌번호 뒤 4자리" class="ut-w-300" />
        <MsfInput v-model="model.transferCardNum" placeholder="요금납부 신용카드 뒤 4자리" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="이번달 사용요금" tag="div" required>
        <MsfCheckbox v-model="model.currentMonthFee" label="다음달 요금 합산 납부 (※ 번호이동 수수료 800원)" />
      </MsfFormGroup>
      <MsfFormGroup label="휴대폰 할부금" tag="div" required>
        <MsfCheckboxGroup v-model="model.deviceInstallment" :options="[{ value: 'deviceInstallment1', label: '완납' }, { value: 'deviceInstallment2', label: '지속(이전 통신회사에 납부)' }]" />
      </MsfFormGroup>
      <MsfFormGroup label="미환급금<br/>요금상계(후불)" tag="div" required>
        <MsfCheckboxGroup v-model="model.offsetAmt" :options="[{ value: 'offsetAmt1', label: '동의' }, { value: 'offsetAmt2', label: '미동의' }]" />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'

const props = defineProps({
  title: { type: String, default: '번호이동 할 전화번호' }
})
const model = defineModel('modelValue', { type: Object, required: true })
const customerModel = defineModel('customerData', { type: Object, required: true })
const store = useMsfFormNewChgStore()

const transferAuthBtn = useAuthButton(
  () => [model.value?.transferAuth, model.value?.transferAuthNum, model.value?.transferBankNum, model.value?.transferCardNum],
  {
    get value() { return store.authFlags?.transferAuth || false },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.transferAuth = v
      }
    }
  }
)
</script>
