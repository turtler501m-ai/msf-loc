<script setup>
import { defineModel } from 'vue'

const model = defineModel({ type: Object, required: true })
</script>
<template>
  <!-- 정보료 상한금액 설정/변경 -->
  <MsfTitleArea title="정보료 상한금액 설정/변경" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="정보료 상한금액" tag="div" required>
      <MsfChip
        v-model="model.infoFee"
        name="inp-infoFee"
        :data="[
          { value: 'infoFee1', label: '0원' },
          { value: 'infoFee2', label: '3천원' },
          { value: 'infoFee3', label: '1만원', disabled: true },
          { value: 'infoFee4', label: '2만원' },
          { value: 'infoFee5', label: '3만원' },
        ]"
      >
        <template #endSlot>
          <MsfButton variant="toggle">확인</MsfButton>
        </template>
      </MsfChip>
    </MsfFormGroup>
  </MsfStack>
  <!-- // 정보료 상한금액 설정/변경 -->
</template>
