<template>
  <div class="guide-wrap">
    <div class="guide-item">
      <h3 class="ut-mb-10">1. 휴대폰 인증번호 발송 및 검증</h3>
      <MsfMobileAuthNumber
        v-model:phone1="phoneData.phone1"
        v-model:phone2="phoneData.phone2"
        v-model:phone3="phoneData.phone3"
        form-type="form-newchange-legalagent"
        @complete="onComplete"
      />
      <span>
        휴대폰 번호: {{ phoneData.phone1 }}-{{ phoneData.phone2 }}-{{ phoneData.phone3 }}, 휴대폰
        번호 인증 결과: {{ verified }}
      </span>
    </div>
    <div class="guide-item">
      <h3 class="ut-mb-10">1. 우편번호 검색</h3>
      <MsfFormGroup label="주소" tag="div" required>
        <MsfStack type="field">
          <MsfInput
            v-model="address.postcode"
            placeholder="우편번호"
            ariaLabel="우편번호 입력"
            disabled
          />
          <MsfButton variant="subtle" @click="onClickSearchAddressBtn">우편번호 찾기</MsfButton>
        </MsfStack>
        <MsfInput
          v-model="address.address1"
          id="address2"
          placeholder="주소"
          ariaLabel="주소 입력"
          class="ut-w100p"
          disabled
        />
        <MsfInput
          v-model="address.address2"
          id="address3"
          placeholder="상세주소"
          ariaLabel="상세주소 입력"
          class="ut-w100p"
        />
      </MsfFormGroup>
      <MsfAddressSearchPop v-model="showAddressSearchPop" @confirm="onConfirmAddressSearchPop" />
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'

const phoneData = ref({ phone1: '', phone2: '', phone3: '' })
const verified = ref(false)
const onComplete = (result) => {
  verified.value = result
}

const address = ref({ postcode: '', address1: '', address2: '' })
const showAddressSearchPop = ref(false)
const onClickSearchAddressBtn = () => {
  showAddressSearchPop.value = true
}
const onConfirmAddressSearchPop = (result) => {
  address.value.postcode = result.zipNo
  address.value.address1 = result.address
  address.value.address2 = result.detailAddress
}

onMounted(async () => {
  const list = await getCommonCodeList(['CLAUSE_FORM_01', 'CLAUSE_FORM_03'], true)
  console.log(list)
})
</script>

<style lang="scss" scoped>
.guide-wrap {
  padding: rem(24px);
  .guide-list {
    @include flex() {
      gap: rem(24px);
    }
    font-size: 16px;
    font-weight: bold;
  }
  .guide-item {
    margin-block: rem(42px);
  }
}
</style>
