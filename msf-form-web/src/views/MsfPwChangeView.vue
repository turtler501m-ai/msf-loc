<!-- 비밀번호 변경 -->
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
              <MsfFormGroup label="<em class='login-label'>비밀번호</em>" vertical>
                <MsfInput
                  type="password"
                  variant="underline"
                  v-model="formData.pwData"
                  class="ut-w100p"
                  placeholder="현재 비밀번호 입력"
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>비밀번호 변경</em>" vertical>
                <MsfInput
                  type="password"
                  variant="underline"
                  v-model="formData.pwChangeData"
                  class="ut-w100p"
                  placeholder="변경 비밀번호 입력"
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>비밀번호 확인</em>" vertical>
                <MsfInput
                  type="password"
                  variant="underline"
                  v-model="formData.pwConfirmData"
                  class="ut-w100p"
                  placeholder="변경 비밀번호 확인"
                />
              </MsfFormGroup>
              <MsfButton variant="primary" block @click="onClickModelRegist"
                >비밀번호 변경</MsfButton
              >
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
import { reactive, onMounted } from 'vue'
// import { post } from '@/libs/api/msf.api'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'

const formData = reactive({
  pwData: '', //비밀번호
  pwChangeData: '', //비밀번호 변경
  pwConfirmData: '', //변경 비밀번호 확인
})

onMounted(async () => {})

const onClickModelRegist = () => {
  console.log('formData:', formData)
  showConfirm('비밀번호를 변경하시겠습니까?', () => {})
}
</script>

<style lang="scss" scoped></style>
