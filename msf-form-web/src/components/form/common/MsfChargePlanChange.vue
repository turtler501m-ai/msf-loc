<script setup>
import { defineModel } from 'vue'

const model = defineModel({ type: Object, required: true })
</script>
<template>
  <!-- 요금제 변경 -->
  <MsfTitleArea title="요금제 변경" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="요금제" tag="div" required>
      <MsfSelect
        title="요금제"
        v-model="model.planName1"
        :options="[
          { label: '추천 요금제1', value: 'planName1-1' },
          { label: '추천 요금제2', value: 'planName1-2' },
        ]"
        class="ut-w100p"
        placeholder="추천 요금제"
      />
      <MsfSelect
        title="요금제"
        v-model="model.planName2"
        :options="[
          {
            label:
              '5G 단말 (2GB/200분) / 데이터 2GB(+보답프로그램 50GB(6개월)) | 음성 200분 | 문자 100건1',
            value: 'planName2-1',
          },
          {
            label:
              '5G 단말 (2GB/200분) / 데이터 2GB(+보답프로그램 50GB(6개월)) | 음성 200분 | 문자 100건2',
            value: 'planName2-2',
          },
        ]"
        class="ut-w100p"
      />
    </MsfFormGroup>
    <MsfFormGroup label="변경일시" tag="div" required>
      <MsfChip
        v-model="model.changeDate"
        name="inp-changeDate"
        :data="[
          { value: 'changeDate1', label: '예약(익월1일)' },
          { value: 'changeDate2', label: '즉시변경' },
        ]"
      >
        <template #endSlot>
          <MsfButton variant="toggle">확인</MsfButton>
        </template>
      </MsfChip>
    </MsfFormGroup>
  </MsfStack>
  <!-- // 요금제 변경 -->
</template>
