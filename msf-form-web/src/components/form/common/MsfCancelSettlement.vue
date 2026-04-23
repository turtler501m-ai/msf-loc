<script setup>
import { ref } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'

const formData = defineModel({ type: Object, required: true })

const terminationStore = useMsfFormTerminationStore()
const isLoadingCharge = ref(false)

const onClickRemainCharge = async () => {
  isLoadingCharge.value = true
  await terminationStore.apiGetRemainCharge()
  isLoadingCharge.value = false
}
</script>
<template>
  <MsfTitleArea title="해지 정산">
    <template #right>
      <MsfButton variant="subtle" :disabled="isLoadingCharge" @click="onClickRemainCharge">
        {{ isLoadingCharge ? '조회 중..' : '잔여요금 조회' }}
      </MsfButton>
    </template>
  </MsfTitleArea>
  <MsfStack vertical type="formgroups">
      <MsfFormGroup label="사용요금" required>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.usageFee"
            placeholder="금액을 입력하세요."
            align="right"
            :clearable="false"
            class="ut-w-300"
          />
          <span class="unit-sep">원</span>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="위약금" required>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.penaltyFee"
            placeholder="금액을 입력하세요."
            align="right"
            :clearable="false"
            class="ut-w-300"
          />
          <span class="unit-sep">원</span>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="최종 정산요금" required>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.finalAmount"
            placeholder="금액을 입력하세요."
            align="right"
            :clearable="false"
            class="ut-w-300"
          />
          <span class="unit-sep">원</span>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup
        label="잔여분할상환<br/>기간/금액"
        helpText="※ 해지 시 까지 사용한 사용료, 위약금, 잔여 단말기 대금 등의 자세한 사용 요금은 다음달 청구서에서 확인 가능합니다."
      >
        <MsfStack type="field">
          <MsfInput
            v-model="formData.remainPeriod"
            placeholder="분할상환 기간을 입력하세요."
            ariaLabel="잔여분할상환 분할상환 기간을 입력하세요."
            align="right"
            :clearable="false"
            class="ut-w-300"
          />
          <span class="unit-sep">개월</span>
          <MsfInput
            v-model="formData.remainAmount"
            id="inp-remainAmount"
            placeholder="금액을 입력하세요."
            ariaLabel="잔여분할상환 금액을 입력하세요."
            align="right"
            :clearable="false"
            class="ut-w-300 ut-ml-8"
          />
          <span class="unit-sep">원</span>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
</template>
