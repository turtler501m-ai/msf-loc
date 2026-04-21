<script setup>
const model = defineModel({ type: Object, required: true })
</script>
<template>
  <MsfTitleArea title="요금제 정보" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="요금제" tag="div" required>
      <MsfChip
        v-model="model.productType"
        name="inp-product"
        :data="[
          { value: 'product1', label: '요금제 변경' },
          { value: 'product2', label: '현재 요금제 사용' },
        ]"
      />
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
    <MsfFormGroup label="대리점" tag="div" required>
      <MsfSelect
        title="대리점 선택"
        v-model="model.agency"
        :options="[
          { label: '대리점1', value: 'agency1' },
          { label: '대리점2', value: 'agency2' },
        ]"
        class="ut-w-300"
        placeholder="대리점 선택"
      />
    </MsfFormGroup>
  </MsfStack>
</template>
