<template>
  <div>
    <!-- 신분증 확인 -->
    <MsfTitleArea title="신분증 확인" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="신분증" tag="div" required>
        <MsfChip
          v-model="model.identityCertTypeCd"
          name="inp-idCard"
          :data="[
            { value: 'K', label: '신분증 목록 조회' },
            { value: 'M', label: '모바일 신분증' },
            { value: 'F', label: '안면 인증' },
            { value: 'S', label: '인증 예외' },
          ]"
        >
          <template #endSlot>
            <MsfButton variant="subtle">조회/인증</MsfButton>
          </template>
        </MsfChip>
      </MsfFormGroup>
      <!-- 신분증 스캔 -->
      <MsfFormGroup label="신분증 스캔" tag="div" required>
        <MsfStack type="field">
          <MsfSelect
            title="신분증 스캔"
            v-model="model.identityTypeCd"
            :options="[
              { label: '주민등록증', value: 'idCardScan1' },
              { label: '운전면허증', value: 'idCardScan2' },
              { label: '국내여권', value: 'idCardScan3' },
              { label: '외국인등록증', value: 'idCardScan4' },
              { label: '영주증', value: 'idCardScan5' },
              { label: '국내거소신고증', value: 'idCardScan6' },
              { label: '국가보훈증', value: 'idCardScan7' },
            ]"
            class="ut-w-300"
          />
          <MsfButton variant="subtle">스캔하기</MsfButton>
        </MsfStack>
        <MsfStack type="field">
          <MsfDateInput v-model="datePickerValue" />
          <MsfSelect
            title="면허지역"
            v-model="model.identityIssuRegion"
            :options="[
              { label: '면허지역1', value: 'licenseRegion1' },
              { label: '면허지역2', value: 'licenseRegion2' },
            ]"
            placeholder="면허지역"
            class="ut-w-200"
          />
          <MsfNumberInput
            v-model="licenseNumber"
            maxlength="15"
            placeholder="면허번호"
            class="ut-w-240"
          />
        </MsfStack>
      </MsfFormGroup>
      <!-- // 신분증 스캔 -->
    </MsfStack>
    <!-- // 신분증 확인 -->
  </div>
</template>

<script setup>
import { ref, defineModel } from 'vue'

const model = defineModel({ type: Object, required: true })

// 로컬 상태 변수들 (TODO: 스토어에 추가하거나 로컬에 유지할지 추후 결정)
const datePickerValue = ref()
const licenseNumber = ref()
</script>
