<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="안심 보험 가입" tag="div">
        <MsfChip
          v-model="model.clauseInsuranceYn"
          name="inp-isInsured"
          :data="[
            { value: 'isInsured1', label: '가입' },
            { value: 'isInsured2', label: '미가입' },
          ]"
        />
        <MsfStack type="field" class="ut-w100p">
          <MsfSelect
            title="추천 카테고리"
            v-model="model.recCat1"
            :options="[
              { label: '추천 카테고리1', value: 'recCat1-1' },
              { label: '추천 카테고리2', value: 'recCat1-2' },
            ]"
            placeholder="추천 카테고리"
            class="ut-w-300"
          />
          <MsfSelect
            title="추천 카테고리"
            v-model="model.recCat2"
            :options="[
              { label: '안드로이드 플래티넘 / 월 기본료 4,900원1', value: 'recCat2-1' },
              { label: '안드로이드 플래티넘 / 월 기본료 4,900원2', value: 'recCat2-2' },
            ]"
            class="ut-flex-1"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'

const props = defineProps({
  title: { type: String, default: '안심 보험' },
})
const model = defineModel({ type: Object, required: true })

const validate = () => {
  if (model.value.clauseInsuranceYn === 'isInsured1') {
    if (!model.value.recCat1 || !model.value.recCat2) return false
  }
  return true
}

defineExpose({ validate })
</script>
