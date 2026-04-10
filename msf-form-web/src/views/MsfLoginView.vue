<!-- 로그인 -->
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
            <!-- 로그인 -->
            <MsfStack vertical type="formgroups">
              <MsfFormGroup label="<em class='login-label'>아이디</em>" vertical>
                <MsfInput
                  variant="underline"
                  v-model="formData.userId"
                  placeholder="아이디를 입력하세요."
                  class="ut-w100p"
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>비밀번호</em>" vertical>
                <MsfInput
                  type="password"
                  variant="underline"
                  v-model="formData.userPw"
                  placeholder="비밀번호를 입력하세요."
                  class="ut-w100p"
                />
                <MsfCheckbox v-model="formData.idSave" label="아이디 저장" blockPadding />
              </MsfFormGroup>
              <MsfButtonGroup gap="3">
                <MsfButton variant="primary" block>로그인</MsfButton>
                <MsfButton
                  variant="secondary"
                  prefixIcon="touchId"
                  class="ut-flex-1 ut-weight-regular"
                  >지문 로그인</MsfButton
                >
                <MsfButton
                  variant="secondary"
                  prefixIcon="faceId"
                  class="ut-flex-1 ut-weight-regular"
                  >Face ID 로그인</MsfButton
                >
              </MsfButtonGroup>
              <div class="login-state-wrap">
                <div class="use-state">
                  <p class="state-tit">사용 상태</p>
                  <p class="state-info failed"><MsfIcon name="loginCheck" size="small" />미승인</p>
                  <p class="state-info"><MsfIcon name="loginCheck" size="small" />승인</p>
                </div>
                <MsfButton variant="subtle">승인철회</MsfButton>
              </div>
            </MsfStack>
            <!--// 로그인 -->
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
  userId: '', //아이디
  userPw: '', //비밀번호
  idSave: false, //아이디 저장 여부
})
</script>

<style lang="scss" scoped></style>
