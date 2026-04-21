<template>
  <MsfTitleBar title="서비스해지 신청서 > 상품 퍼블확인용" />
  <div class="page-step-panel">
    <!-- 해지 신청 -->
    <MsfTitleArea title="해지 신청" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="사용여부" tag="div" required>
        <MsfChip
          v-model="formData.isActive"
          name="inp-isActive"
          :data="[
            { value: 'isActive1', label: 'kt M mobile 재사용' },
            { value: 'isActive2', label: 'kt 재사용' },
            { value: 'isActive3', label: 'skt 사용' },
            { value: 'isActive4', label: 'lgt 사용' },
            { value: 'isActive5', label: '기타' },
          ]"
        />
      </MsfFormGroup>
    </MsfStack>
    <!-- // 해지 신청 -->
    <!-- 해지 정산 -->
    <MsfTitleArea title="해지 정산" />
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
    <!-- // 해지 정산 -->
    <!-- 메모 -->
    <MsfTitleArea title="메모" />
    <MsfFormGroup label="메모">
      <MsfTextarea v-model="formData.memo" placeholder="메모 입력" />
    </MsfFormGroup>
    <!-- // 메모 -->
  </div>
</template>

<script setup>
import { ref, watch, reactive } from 'vue'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const isComplete = ref('')

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => isComplete.value,
  (newVal) => {
    isComplete.value = newVal
    emit('complete', newVal ? true : false)
  },
)

const save = async () => {
  //  데이터 임시저장
  return isComplete.value === 'true'
}

defineExpose({ save })

//퍼블 샘플
const formData = reactive({
  isActive: '', //사용여부
  usageFee: '', //사용요금
  penaltyFee: '', //위약금
  finalAmount: '', //최종정산요금
  remainPeriod: '', //잔여분할상환 기간
  remainAmount: '', //잔여분할상환 금액
  memo: '', //메모
})
</script>

<style scoped></style>
