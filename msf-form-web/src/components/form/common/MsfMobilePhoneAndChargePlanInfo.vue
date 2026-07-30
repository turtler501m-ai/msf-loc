<script setup>
import { watchEffect } from 'vue'

const formData = defineModel({ type: Object, required: true })

// 상품 유형이 휴대폰('MM')이고 가입유형이 기기변경('HDN3')인 경우 개통유형 고정
watchEffect(() => {
  if (formData.value.productType === 'MM' && (formData.value.joinType === 'HDN3' || formData.value.joinType === 'HCN3')) {
    formData.value.openTypeCd = 'openingType1'
  }
})
</script>

<template>
  <!-- 휴대폰 및 요금제 정보 -->
  <MsfTitleArea title="휴대폰 및 요금제 정보" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="휴대폰" tag="div" required>
      <MsfSelect
        title="휴대폰"
        v-model="formData.deviceModel"
        :options="[
          { label: '갤럭시 A36', value: 'deviceModel1' },
          { label: '갤럭시 A366', value: 'deviceModel2' },
        ]"
        class="ut-w-300"
      />
    </MsfFormGroup>
    <MsfFormGroup label="약정기간" tag="div" required v-if="formData.productType !== 'UU'">
      <MsfChip
        v-model="formData.contractPeriod"
        name="inp-contractPeriod"
        :data="[
          { value: 'contractPeriod1', label: '무약정' },
          { value: 'contractPeriod2', label: '24개월' },
          { value: 'contractPeriod3', label: '30개월' },
          { value: 'contractPeriod4', label: '36개월' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="단말기 할부기간" tag="div" required>
      <MsfChip
        v-model="formData.installmentMonth"
        name="inp-installmentMonth"
        :data="[
          { value: 'installmentMonth1', label: '0개월(없음)' },
          { value: 'installmentMonth2', label: '24개월' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="할인유형" tag="div" required>
      <MsfChip
        v-model="formData.discountType"
        name="inp-discountType"
        :data="[
          { value: 'discountType1', label: '단말 할인' },
          { value: 'discountType2', label: '알뜰스폰서(약정지원금)' },
          { value: 'discountType3', label: '알뜰스폰서(요금할인)' },
          { value: 'discountType4', label: '심플할인' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="요금제" tag="div" required>
      <MsfSelect
        title="요금제"
        v-model="formData.planName1"
        :options="[
          { label: '추천 요금제1', value: 'planName1-1' },
          { label: '추천 요금제2', value: 'planName1-2' },
        ]"
        class="ut-w100p"
        placeholder="추천 요금제"
      />
      <MsfSelect
        title="요금제"
        v-model="formData.planName2"
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
    <MsfFormGroup label="대리점" tag="div" required>
      <MsfSelect
        title="대리점 선택"
        v-model="formData.agent"
        :options="[
          { label: '대리점1', value: 'agent1' },
          { label: '대리점2', value: 'agent2' },
        ]"
        class="ut-w-300"
        placeholder="대리점 선택"
      />
    </MsfFormGroup>
  </MsfStack>
  <!-- // 휴대폰 및 요금제 정보 -->
</template>

<style scoped lang="scss"></style>
