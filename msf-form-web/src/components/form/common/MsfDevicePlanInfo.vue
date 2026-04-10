<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="개통 유형" tag="div" required>
        <MsfChip v-model="model.openingType" name="inp-openingType" :data="[{ value: 'openingType1', label: '휴대폰' }, { value: 'openingType2', label: 'eSIM' }]" />
      </MsfFormGroup>
      <MsfFormGroup v-if="model.product === 'MM'" label="휴대폰" tag="div" required>
        <MsfSelect title="휴대폰" v-model="model.deviceModel" :options="[{ label: '갤럭시 A36', value: 'deviceModel1' }, { label: '갤럭시 A366', value: 'deviceModel2' }]" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup v-if="model.product === 'MM'" label="용량" tag="div" required>
        <MsfSelect title="용량" v-model="model.capacity" :options="[{ label: '64GB', value: 'capacity1' }, { label: '128GB', value: 'capacity2' }]" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup v-if="model.product === 'MM'" label="색상" tag="div" required>
        <MsfSelect title="색상" v-model="model.color" :options="[{ label: '화이트', value: 'color1' }, { label: '블랙', value: 'color2' }]" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="약정기간" tag="div" required>
        <MsfChip v-model="model.contractPeriod" name="inp-contractPeriod" :data="[{ value: 'contractPeriod1', label: '무약정' }, { value: 'contractPeriod2', label: '24개월' }, { value: 'contractPeriod3', label: '30개월' }, { value: 'contractPeriod4', label: '36개월' }]" />
      </MsfFormGroup>
      <MsfFormGroup v-if="model.product === 'MM'" label="단말기 할부기간" tag="div" required>
        <MsfChip v-model="model.installmentMonth" name="inp-installmentMonth" :data="[{ value: 'installmentMonth1', label: '0개월(없음)' }, { value: 'installmentMonth2', label: '24개월' }]" />
      </MsfFormGroup>
      <MsfFormGroup label="할인유형" tag="div" required>
        <MsfChip v-model="model.discountType" name="inp-discountType" :data="[{ value: 'discountType1', label: '단말 할인' }, { value: 'discountType2', label: '알뜰스폰서(약정지원금)' }, { value: 'discountType3', label: '알뜰스폰서(요금할인)' }, { value: 'discountType4', label: '심플할인' }]" />
      </MsfFormGroup>
      <MsfFormGroup label="요금제" tag="div" required>
        <MsfSelect title="요금제" v-model="model.planName1" :options="[{ label: '추천 요금제1', value: 'planName1-1' }, { label: '추천 요금제2', value: 'planName1-2' }]" class="ut-w100p" placeholder="추천 요금제" />
        <MsfSelect title="요금제" v-model="model.planName2" :options="[{ label: '5G 단말 (2GB/200분) / 데이터 2GB(+보답프로그램 50GB(6개월)) | 음성 200분 | 문자 100건1', value: 'planName2-1' }, { label: '5G 단말 (2GB/200분) / 데이터 2GB(+보답프로그램 50GB(6개월)) | 음성 200분 | 문자 100건2', value: 'planName2-2' }]" class="ut-w100p" />
      </MsfFormGroup>
      <MsfFormGroup label="대리점" tag="div" required>
        <MsfSelect title="대리점 선택" v-model="model.agency" :options="[{ label: '대리점1', value: 'agency1' }, { label: '대리점2', value: 'agency2' }]" class="ut-w-300" placeholder="대리점 선택" />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'

const props = defineProps({
  title: { type: String, default: '휴대폰 및 요금제 정보' }
})
const model = defineModel({ type: Object, required: true })
</script>