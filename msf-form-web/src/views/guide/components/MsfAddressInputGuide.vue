<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfAddressInput"
      title="MsfAddressInput"
      description="MsfAddressInput 설명"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfAddressInput
          v-bind="props"
          v-model:address1="zipCode"
          v-model:address2="baseAddr"
          v-model:address3="detailAddr"
          @search="showAddressSearchPop = true"
        />
        <p class="ut-mt-10">
          우편번호: {{ zipCode }} <br />주소: {{ baseAddr }} <br />상세주소:
          {{ detailAddr }}
        </p>
      </template>
    </GuideUnit>
    <!-- 주소 검색 모달 -->
    <MsfAddressSearchPop
      v-model="showAddressSearchPop"
      :detail-address-required="detailAddressRequired"
      :address1="baseAddr"
      :address2="detailAddr"
      @confirm="onConfirmAddressSearchPop"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const zipCode = ref('12356') //우편번호
const baseAddr = ref('서울특별시 관악구') //주소
const detailAddr = ref('') //상세주소
const showAddressSearchPop = ref(false)
const detailAddressRequired = ref(true)

/**
 * 1. 컨트롤러 설정 (config)
 */
const componentConfig = {
  id: undefined,
  label: '휴대폰번호',
  disabled: false,
  readonly: false,
}

/**
 * 2. 가이드 초기 상태 (initialState)
 */
const componentState = {
  id: 'input-id',
  label: '휴대폰번호',
  disabled: false,
  readonly: false,
}

const onConfirmAddressSearchPop = (result) => {
  zipCode.value = result.zipNo
  baseAddr.value = result.address
  detailAddr.value = result.detailAddress
}
</script>
