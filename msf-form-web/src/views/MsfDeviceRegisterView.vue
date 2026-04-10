<!-- 단말 사용 등록 -->
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
            <!-- 단말사용등록 -->
            <MsfStack vertical type="formgroups">
              <MsfFormGroup label="<em class='login-label'>이름</em>" vertical>
                <MsfInput
                  variant="underline"
                  v-model="formData.userName"
                  class="ut-w100p"
                  disabled
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>아이피</em>" vertical>
                <MsfInput
                  variant="underline"
                  v-model="formData.userPhone"
                  class="ut-w100p"
                  disabled
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>단말 고유 ID</em>" vertical>
                <MsfInput
                  variant="underline"
                  v-model="formData.deviceId"
                  class="ut-w100p"
                  disabled
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>부서</em>" vertical>
                <MsfInput
                  variant="underline"
                  v-model="formData.deptName"
                  class="ut-w100p"
                  disabled
                />
              </MsfFormGroup>
              <MsfButton variant="primary" block>단말 사용 등록</MsfButton>
              <MsfButtonGroup>
                <MsfButton variant="subtle">로그인 화면으로 이동</MsfButton>
              </MsfButtonGroup>
            </MsfStack>
            <!--// 단말사용등록 -->
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
  userName: '홍길동', //이름
  userPhone: '111.111.111.111', //아이피
  deviceId: '단말 고유 ID', //단말 고유 ID
  deptName: 'IT전략팀', //부서
})
</script>

<style lang="scss" scoped></style>
