<template>
  <!-- 간편신청서 -->
  <div class="msf-easy-form">
    <!-- 헤더 -->
    <div class="msf-easy-form-header">
      <h1 class="logo"><img src="@/assets/images/logo.svg" alt="kt mobile" /></h1>
    </div>
    <!-- // 헤더 -->
    <!-- 컨텐츠 -->
    <div class="msf-easy-form-content">
      <MsfTitleBar title="간편신청서" />
      <MsfTitleArea title="개인정보" />
      <MsfStack vertical type="formgroups">
        <MsfFormGroup label="매장명" vertical>
          <MsfInput v-model="formData.storeName" placeholder="매장명" class="ut-w100p" />
        </MsfFormGroup>
        <MsfFormGroup label="무선상품" tag="div" vertical>
          <MsfChip
            v-model="formData.product"
            name="inp-product"
            :data="[
              { value: 'product1', label: '기기변경' },
              { value: 'product2', label: '신규가입' },
              { value: 'product3', label: '번호이동' },
            ]"
          />
        </MsfFormGroup>
        <MsfFormGroup label="이름" vertical>
          <MsfInput v-model="formData.name" placeholder="이름 입력" class="ut-w100p" />
        </MsfFormGroup>
        <MsfFormGroup label="생년월일" vertical>
          <MsfBirthdayInput
            v-model="formData.birthday"
            length="8"
            placeholder="생년월일 8자리를 입력해 주세요."
            class="ut-w100p"
          />
        </MsfFormGroup>
        <MsfFormGroup label="연락처" vertical>
          <MsfInput v-model="formData.phone" placeholder="010-9170-9875" class="ut-w100p" />
        </MsfFormGroup>
        <MsfFormGroup label="본인인증" tag="div" vertical>
          <MsfButton variant="subtle">통합인증</MsfButton>
          <MsfTextList
            margin="0"
            :items="[
              '본인인증 창이 뜨지 않을 경우 브라우저 설정 내 팝업 차단 설정 여부를 확인해주세요.',
            ]"
          />
        </MsfFormGroup>
      </MsfStack>
      <MsfTitleArea title="메모" />
      <MsfTextarea
        v-model="formData.memo"
        placeholder="(무선)상담하고 싶은 단말 및 요금제를 입력해 주세요."
      />
      <MsfTitleArea title="개인정보 수집동의" />
      약관동의 컴퍼넌트(MsfAgreementGroup) 공통
      <button @click="openPolicy('policy01')">약관팝업(S106060103)</button>
      <MsfButtonGroup align="center" class="ut-mt-m">
        <MsfButton variant="primary" block>작성 완료</MsfButton>
      </MsfButtonGroup>
    </div>
    <!-- // 컨텐츠 -->
    <!-- 안내 -->
    <div class="msf-easy-info">
      <strong class="easy-info-tit">간편 신청서 접수가<br />완료되었습니다.</strong>
    </div>
    <div class="msf-easy-info is-error">
      <strong class="easy-info-tit">간편 신청서 접수가<br />실패하였습니다.</strong>
    </div>
    <div class="msf-easy-info is-error">
      <strong class="easy-info-tit">간편 신청서 정보가<br />없습니다.</strong>
    </div>
    <div class="msf-easy-info is-error">
      <strong class="easy-info-tit">
        간편 신청서 작성 유효 기간이<br />
        경과하였습니다.
      </strong>
      <span class="easy-info-desc">재요청 해주세요.</span>
    </div>
    <!-- // 안내 -->
  </div>
  <!-- // 간편신청서 -->

  <!-- 간편신청서 에서 공통 팝업사용시에 props 설정후 MsfDialog - maximize 사용 -->
  <PolicySample v-model="policyPopOpen" :policy="currentPolicy" maximize />
</template>

<script setup>
import { reactive, ref } from 'vue'

// 퍼블 샘플
const formData = reactive({
  storeName: 'SPT8050', //매장명
  product: '', //무선상품
  name: '', //이름
  birthday: '', //생년월일
  phone: '010-9170-9875', //연락처
  memo: '', //메모
})

// 약관 샘플 팝업 관련
import PolicySample from '@/views/guide/pages/pub/PolicySample.vue'
const policyPopOpen = ref(false)
const currentPolicy = ref('')
// 약관 샘플 팝업 열기
const openPolicy = (id) => {
  currentPolicy.value = id // 'policy01' 등을 저장
  policyPopOpen.value = true
}
</script>

<style lang="scss" scoped></style>
