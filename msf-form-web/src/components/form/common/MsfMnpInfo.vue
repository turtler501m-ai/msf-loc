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
            :disabled="customerModel.isSaved"
          />
          <MsfStack type="field">
            <MsfNumberInput
              v-model="model.moveMobileNo1"
              placeholder="앞자리"
              maxlength="3"
              :readonly="customerModel.isSaved"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              v-model="model.moveMobileNo2"
              placeholder="가운데 4자리"
              maxlength="4"
              :readonly="customerModel.isSaved"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              v-model="model.moveMobileNo3"
              placeholder="뒤 4자리"
              maxlength="4"
              :readonly="customerModel.isSaved"
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
            :disabled="customerModel.isSaved"
          />
        </MsfStack>
        <MsfStack type="field" v-if="model.moveAuthTypeCd">
          <MsfInput
            v-model="model.moveAuthNo"
            :placeholder="authInputPlaceholder"
            class="ut-w-300"
          />
          <MsfButton variant="subtle" @click="handlePreAuth">번호이동 사전동의</MsfButton>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="이번달 사용요금" tag="div" required>
        <MsfCheckbox
          v-model="model.moveThismonthPayTypeCd"
          label="다음달 요금 합산 납부 (※ 번호이동 수수료 800원)"
          :disabled="customerModel.isSaved"
        />
      </MsfFormGroup>
      <MsfFormGroup label="휴대폰 할부금" tag="div" required>
        <MsfCheckboxGroup
          v-model="model.moveAllotmentSttusCd"
          :options="[
            { value: 'deviceInstallment1', label: '완납' },
            { value: 'deviceInstallment2', label: '지속(이전 통신회사에 납부)' },
          ]"
          :disabled="customerModel.isSaved"
        />
      </MsfFormGroup>
      <MsfFormGroup label="미환급금<br/>요금상계(후불)" tag="div" required>
        <MsfCheckboxGroup
          v-model="model.moveRefundAgreeYn"
          :options="[
            { value: 'offsetAmt1', label: '동의' },
            { value: 'offsetAmt2', label: '미동의' },
          ]"
          :disabled="customerModel.isSaved"
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

const handlePreAuth = () => {
  // TODO: 사전동의 API 연동. 성공 시 authFlag 업데이트 처리 샘플
  if (store.authFlags) store.authFlags.moveAuthTypeCd = true

  // 실패 상황일 경우 모달 띄우기 (테스트용)
  // isFailModalOpen.value = true
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
