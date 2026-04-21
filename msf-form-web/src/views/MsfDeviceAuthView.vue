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
              <MsfDeviceAuthNumber
                :name="formData.cstmrNm"
                :phone="formData.userPhone"
                @complete="(result) => (formData.complete = result)"
              />
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
import { reactive, watch } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'

// 퍼블 샘플
const formData = reactive({
  cstmrNm: '홍길동', //이름
  userPhone: '010-1234-5678', //전화번호
  complete: false,
})

watch(
  () => formData.complete,
  (complete) => {
    if (complete) {
      showAlert('인증이 완료되었습니다.')
    } else {
      showAlert('인증에 실패하였습니다. 인증번호를 다시 확인해주세요.')
    }
  },
)
</script>

<style lang="scss" scoped></style>
