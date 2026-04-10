<!-- 단말 사용 인증 -->
<template>
  <div class="login-wrap">
    <div class="login-inner">
      <div class="login-box">
        <div class="login-title">
          <h1 class="logo"><img src="@/assets/images/logo.svg" alt="kt m mobile 로고" /></h1>
          <strong class="title"><span class="ut-color-point">SMART</span> 신청서</strong>
        </div>
        <form>
          <div class="login-form">
            <!-- 단말사용인증 -->
            <MsfStack vertical type="formgroups">
              <MsfFormGroup label="<em class='login-label'>이름</em>" vertical>
                <MsfInput
                  variant="underline"
                  v-model="formData.userName"
                  class="ut-w100p"
                  disabled
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>전화번호</em>" vertical>
                <MsfStack type="field" class="ut-w100p">
                  <MsfInput
                    variant="underline"
                    v-model="formData.userPhone"
                    class="ut-flex-1"
                    disabled
                  />
                  <MsfButton variant="toggle">인증번호 발송</MsfButton>
                </MsfStack>
                <MsfStack type="field">
                  <MsfNumberInput
                    v-model="formData.repPhoneAuth"
                    id="inp-repPhoneAuth"
                    placeholder="인증번호 입력"
                    ariaLabel="인증번호 입력"
                  />
                  <span class="remain-time">남은시간 <em>02:33</em></span>
                </MsfStack>
              </MsfFormGroup>
              <MsfButton variant="primary" block>단말 사용 인증</MsfButton>
              <MsfButtonGroup>
                <MsfButton variant="subtle">로그인 화면으로 이동</MsfButton>
              </MsfButtonGroup>
            </MsfStack>
            <!--// 단말사용인증 -->
          </div>
        </form>
      </div>
    </div>
    <p class="copy-right">Copyright © kt M mobile. All rights reserved.</p>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

// 퍼블 샘플
const formData = reactive({
  userName: '홍길*', //이름
  userPhone: '010-11**-*111', //전화번호
  authNumber: '', //인증번호
})
</script>

<style lang="scss" scoped></style>
