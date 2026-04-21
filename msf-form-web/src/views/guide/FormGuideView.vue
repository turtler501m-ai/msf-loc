<template>
  <div class="guide-wrap">
    <strong class="guide-wrap-title"
      >&lt;MsfStack type="field"&gt; &lt;/MsfStack&gt;로 감싸서 컴퍼넌트 생성하시면 됩니다.</strong
    >
    <!-- MsfMobileInput : 휴대폰번호 -->
    <div class="guide-item">
      <p class="guide-item-title">
        MsfMobileInput : 휴대폰번호, 명의변경 휴대폰번호, 해지 휴대폰번호, 연락처(휴대폰)
      </p>
      <MsfStack type="field">
        <MsfMobileInput
          v-model:number1="userPhone.p1"
          v-model:number2="userPhone.p2"
          v-model:number3="userPhone.p3"
        />
      </MsfStack>
      <p class="guide-item-result-txt">
        MsfMobileInput : {{ userPhone.p1 }}-{{ userPhone.p2 }}-{{ userPhone.p3 }}<br />
      </p>
    </div>
    <!-- // MsfMobileInput : 휴대폰번호 -->
    <!-- MsfTelInput : 전화번호 -->
    <div class="guide-item">
      <p class="guide-item-title">MsfTelInput : 전화번호</p>
      <MsfStack type="field">
        <MsfTelInput
          v-model:telNo1="formData.telNo1"
          v-model:telNo2="formData.telNo2"
          v-model:telNo3="formData.telNo3"
        />
      </MsfStack>
      <p class="guide-item-result-txt">
        MsfTelInput : {{ formData.telNo1 }}-{{ formData.telNo2 }}-{{ formData.telNo3 }}
      </p>
    </div>
    <!-- // MsfTelInput : 전화번호 -->
    <!-- MsfRegNoInput : 주민등록번호, 외국인등록번호, 법인등록번호 -->
    <div class="guide-item">
      <p class="guide-item-title">
        MsfRegNoInput : 주민등록번호, 외국인등록번호, 법인등록번호 (type으로 각 ariaLabel 지정됨)
      </p>
      <p class="guide-item-desc">1. 주민등록번호 (type="resident")</p>
      <MsfStack type="field">
        <MsfRegNoInput
          type="resident"
          v-model:registNo1="formData.registNo1"
          v-model:registNo2="formData.registNo2"
        />
      </MsfStack>
      <p class="guide-item-desc">2. 외국인등록번호 (type="foreigner")</p>
      <MsfStack type="field">
        <MsfRegNoInput
          type="foreigner"
          v-model:registNo1="formData.registNo1"
          v-model:registNo2="formData.registNo2"
        />
      </MsfStack>
      <p class="guide-item-desc">3. 법인등록번호 (type="corporate")</p>
      <MsfStack type="field">
        <MsfRegNoInput
          type="corporate"
          v-model:registNo1="formData.registNo1"
          v-model:registNo2="formData.registNo2"
        />
      </MsfStack>
      <p class="guide-item-result-txt">
        MsfRegNoInput : {{ formData.registNo1 }}-{{ formData.registNo2 }}
      </p>
    </div>
    <!-- // MsfRegNoInput : 주민등록번호, 외국인등록번호, 법인등록번호 -->
    <!-- MsfBizRegInput : 사업자등록번호 -->
    <div class="guide-item">
      <p class="guide-item-title">MsfBizRegInput : 사업자등록번호</p>
      <MsfStack type="field">
        <MsfBizRegInput
          v-model:bizNo1="formData.bizNo1"
          v-model:bizNo2="formData.bizNo2"
          v-model:bizNo3="formData.bizNo3"
        />
      </MsfStack>
      <p class="guide-item-result-txt">
        MsfBizRegInput : {{ formData.bizNo1 }}-{{ formData.bizNo2 }}-{{ formData.bizNo3 }}
      </p>
    </div>
    <!-- // MsfBizRegInput : 사업자등록번호 -->
    <!-- MsfEmailInput : 이메일 -->
    <div class="guide-item">
      <p class="guide-item-title">
        MsfEmailInput : 이메일주소 (2026-04-15__필수입력 표시 설계 삭제)
      </p>
      <MsfStack type="field">
        <MsfEmailInput
          v-model:emailId="formData.emailId"
          v-model:emailDomain="formData.emailDomain"
        />
      </MsfStack>
      <p class="guide-item-result-txt">
        MsfEmailInput : {{ formData.emailId }}@{{ formData.emailDomain }}
      </p>
    </div>
    <!-- // MsfEmailInput : 이메일 -->
    <!-- MsfAddressInput : 주소 -->
    <div class="guide-item">
      <p class="guide-item-title">MsfAddressInput : 주소</p>
      <MsfAddressInput
        v-model:address1="formData.zipCode"
        v-model:address2="formData.baseAddr"
        v-model:address3="formData.detailAddr"
        @search="console.log('우편번호 찾기 클릭!')"
      />
      <p class="guide-item-result-txt">
        MsfAddressInput : <br />우편번호: {{ formData.zipCode }}<br />주소: {{ formData.baseAddr
        }}<br />상세주소: {{ formData.detailAddr }}
      </p>
    </div>
    <!-- // MsfAddressInput : 주소 -->
  </div>
</template>

<script setup>
import { reactive } from 'vue'

// 휴대폰번호
const userPhone = reactive({
  p1: '010',
  p2: '',
  p3: '',
  code: '',
})
const formData = reactive({
  // MsfTelInput
  telNo1: '', //전화번호1
  telNo2: '', //전화번호2
  telNo3: '', //전화번호3
  // MsfBizRegInput
  bizNo1: '', //사업자등록번호1
  bizNo2: '', //사업자등록번호2
  bizNo3: '', //사업자등록번호3
  // MsfEmailInput
  emailId: '', //이메일 아이디
  emailDomain: '', //이메일 도메인
  // MsfAddressInput
  zipCode: '123-456', //우편번호
  baseAddr: '서울특별시 관악구', //주소
  detailAddr: '', //상세주소
  // MsfRegNoInput
  rrn1: '',
  rrn2: '',
  authCode: '',
})
</script>

<style lang="scss" scoped>
.guide-wrap {
  padding: rem(32px);
  .guide-wrap-title {
    font-size: rem(20px);
    font-weight: var(--font-weight-bold);
  }
  .guide-item {
    border-bottom: 1px solid var(--color-gray-150);
    margin-block: rem(32px);
    padding-bottom: rem(32px);
    .guide-item-title {
      font-size: rem(18px);
      font-weight: var(--font-weight-medium);
      margin-bottom: rem(8px);
      position: relative;
      padding-left: 0.8em;
      &::before {
        content: '-';
        position: absolute;
        top: 0;
        left: 0;
      }
    }
    .guide-item-desc {
      font-size: rem(14px);
      color: var(--color-gray-500);
      font-weight: var(--font-weight-medium);
      margin-block: rem(16px) rem(8px);
      position: relative;
      padding-left: 0.9em;
      &::before {
        content: '※';
        position: absolute;
        top: 0;
        left: 0;
      }
    }
  }
  .guide-item-result-txt {
    margin-block: rem(10px);
    font-size: rem(14px);
  }
}
</style>
