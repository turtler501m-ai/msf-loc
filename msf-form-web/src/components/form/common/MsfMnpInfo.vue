<template>
  <div v-if="customerModel.joinType === 'MNP3'">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="번호이동 할<br/>전화번호" required>
        <MsfStack type="field">
          <MsfSelect
            title="통신사 선택"
            v-model="model.moveCompanyCd"
            :options="[
              { label: '통신사1', value: 'agency1' },
              { label: '통신사2', value: 'agency2' },
            ]"
            class="ut-w-300"
            placeholder="통신사 선택"
          />
          <MsfStack type="field">
            <MsfNumberInput
              v-model="model.moveMobileNo1"
              placeholder="앞자리"
              maxlength="3"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              v-model="model.moveMobileNo2"
              placeholder="가운데 4자리"
              maxlength="4"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              v-model="model.moveMobileNo3"
              placeholder="뒤 4자리"
              maxlength="4"
            />
          </MsfStack>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="번호이동 인증" tag="div" required>
        <MsfStack type="field">
          <MsfChip
            v-model="model.moveAuthTypeCd"
            name="inp-transferAuth"
            :data="[
              { value: 'transferAuth1', label: '휴대폰 일련번호 뒤 4자리' },
              { value: 'transferAuth2', label: '계좌번호 뒤 4자리' },
              { value: 'transferAuth3', label: '신용카드 뒤 4자리' },
            ]"
          />
        </MsfStack>
        <MsfStack type="field" v-if="model.moveAuthTypeCd">
          <MsfInput
            v-model="model.moveAuthNo"
            :placeholder="authInputPlaceholder"
            class="ut-w-300"
          />
          <MsfButton
            variant="subtle"
            @click="handlePreAuth"
            v-if="!store.authFlags?.moveAuthTypeCd"
          >번호이동 사전동의</MsfButton>
          <template v-else>
            <MsfButton variant="subtle" @click="handleCheckAgree">사전동의 결과조회</MsfButton>
            <MsfButton variant="subtle" @click="handlePayOpn">납부주장</MsfButton>
          </template>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="이번달 사용요금" tag="div" required>
        <MsfCheckbox
          v-model="model.moveThismonthPayTypeCd"
          label="다음달 요금 합산 납부 (※ 번호이동 수수료 800원)"
        />
      </MsfFormGroup>
      <MsfFormGroup label="휴대폰 할부금" tag="div" required>
        <MsfCheckboxGroup
          v-model="model.moveAllotmentSttusCd"
          :options="[
            { value: 'deviceInstallment1', label: '완납' },
            { value: 'deviceInstallment2', label: '지속(이전 통신회사에 납부)' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="미환급금<br/>요금상계(후불)" tag="div" required>
        <MsfCheckboxGroup
          v-model="model.moveRefundAgreeYn"
          :options="[
            { value: 'offsetAmt1', label: '동의' },
            { value: 'offsetAmt2', label: '미동의' },
          ]"
        />
      </MsfFormGroup>
    </MsfStack>

    <!-- 번호이동 사전동의 실패 모달 -->
    <MsfMnpAuthFailModal v-model="isFailModalOpen" />
  </div>
</template>
<script setup>
import { ref, defineModel, defineProps, computed } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import MsfMnpAuthFailModal from './popups/MsfMnpAuthFailModal.vue'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  title: { type: String, default: '번호이동 할 전화번호' },
})
const model = defineModel('modelValue', { type: Object, required: true })
const customerModel = defineModel('customerData', { type: Object, required: true })
const store = useMsfFormNewChgStore()

const isFailModalOpen = ref(false)

const authInputPlaceholder = computed(() => {
  switch (model.value?.moveAuthTypeCd) {
    case 'transferAuth1':
      return '휴대폰 일련번호 뒤 4자리'
    case 'transferAuth2':
      return '계좌번호 뒤 4자리'
    case 'transferAuth3':
      return '신용카드 뒤 4자리'
    default:
      return '번호이동 인증 정보 입력'
  }
})

const handlePreAuth = async () => {
  const payload = {
    moveCompanyCd: model.value.moveCompanyCd,
    moveMobileNo: model.value.moveMobileNo1 + model.value.moveMobileNo2 + model.value.moveMobileNo3,
    moveAuthTypeCd: model.value.moveAuthTypeCd,
    moveAuthNo: model.value.moveAuthNo,
  }

  try {
    const res = await post('/api/form/newchange/reqNpPreCheck', payload)
    if (res && res.code === '0000') {
      alert('번호이동 사전동의 요청이 완료되었습니다. 고객님의 휴대폰으로 발송된 문자의 URL을 확인해주세요.')
      if (store.authFlags) store.authFlags.moveAuthTypeCd = true
    } else {
      alert(res.message || '사전동의 요청에 실패했습니다.')
      isFailModalOpen.value = true
    }
  } catch (error) {
    console.error('PreAuth error:', error)
  }
}

const handleCheckAgree = async () => {
  const payload = {
    moveMobileNo: model.value.moveMobileNo1 + model.value.moveMobileNo2 + model.value.moveMobileNo3,
  }
  try {
    const res = await post('/api/form/newchange/reqNpAgree', payload)
    if (res && res.code === '0000') {
      alert('번호이동 사전동의 확인이 완료되었습니다.')
    } else {
      alert(res.message || '사전동의 확인에 실패했습니다.')
    }
  } catch (error) {
    console.error('Check agree error:', error)
  }
}

const handlePayOpn = async () => {
  const payload = {
    moveMobileNo: model.value.moveMobileNo1 + model.value.moveMobileNo2 + model.value.moveMobileNo3,
  }
  try {
    const res = await post('/api/form/newchange/reqPayOpn', payload)
    if (res && res.code === '0000') {
      alert('납부주장 처리가 완료되었습니다.')
    } else {
      alert(res.message || '납부주장 처리에 실패했습니다.')
    }
  } catch (error) {
    console.error('PayOpn error:', error)
  }
}

const transferAuthBtn = useAuthButton(
  () => [
    model.value?.transferAuth,
    model.value?.transferAuthNum,
    model.value?.transferBankNum,
    model.value?.transferCardNum,
  ],
  {
    get value() {
      return store.authFlags?.transferAuth || false
    },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.moveAuthTypeCd = v
      }
    },
  },
)

const validate = () => {
  if (customerModel.value.joinType === 'MNP3') {
    if (!model.value.moveCompanyCd) return false
    if (!model.value.moveMobileNo1 || !model.value.moveMobileNo2 || !model.value.moveMobileNo3)
      return false
    if (!model.value.moveAuthTypeCd || !model.value.moveAuthNo) return false
    if (!store.authFlags?.moveAuthTypeCd) return false
    if (!model.value.moveThismonthPayTypeCd) return false
    if (!model.value.moveAllotmentSttusCd) return false
    if (!model.value.moveRefundAgreeYn) return false
  }
  return true
}

defineExpose({ validate })
</script>
