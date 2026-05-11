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
                  v-model="formData.password"
                  class="ut-w100p"
                  placeholder="현재 비밀번호 입력"
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>비밀번호 변경</em>" vertical>
                <MsfInput
                  type="password"
                  variant="underline"
                  v-model="formData.newPassword"
                  class="ut-w100p"
                  placeholder="변경 비밀번호 입력"
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>비밀번호 확인</em>" vertical>
                <MsfInput
                  type="password"
                  variant="underline"
                  v-model="formData.cfmPassword"
                  class="ut-w100p"
                  placeholder="변경 비밀번호 확인"
                />
              </MsfFormGroup>
              <MsfButton variant="primary" block @click="onClickModelRegist"
                >비밀번호 변경</MsfButton
              >
              <MsfButtonGroup>
                <MsfButton variant="subtle" @click="goLogin">로그인 화면으로 이동</MsfButton>
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
import { ref, reactive, onMounted } from 'vue'
import { post } from '@/libs/api/msf.api'
import { useRouter } from 'vue-router'
import { useMsfUserStore } from '@/stores/msf_user'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { isEmpty } from '@/libs/utils/string.utils'

const oldPwdError = ref(false)
const newPwdError = ref(false)
const cfmPasswordError = ref(false)

const router = useRouter()
const msfUserStore = useMsfUserStore()

const formData = reactive({
  loginSessionId: msfUserStore.userData.loginSessionId,
  password: '', //비밀번호
  newPassword: '', //비밀번호 변경
  cfmPassword: '', //변경 비밀번호 확인
})

onMounted(async () => {
  if (!msfUserStore.userData) {
    showAlert('로그인이 필요합니다.\n로그인 화면으로 이동합니다.', () => {
      router.push('/login')
    })
  } else {
    // formData.userNm = msfUserStore.userData.userNm
    // formData.localIp = msfUserStore.userData.ip
  }
})

const changePwd = async () => {
  const result = await post('/api/n/auth/passwd/modify', formData)
  if (result.code >= '400' || !result.data) {
    showAlert('비밀번호 변경이 실패하였습니다. 다시 시도해 주세요.')
    return false
  }

  if (result.data) {
    showAlert('비밀번호 변경이 완료되었습니다.', () => {
      goLogin()
    })
  }
}

const onClickModelRegist = () => {
  console.log('formData:', formData)
  // 1. 필수 입력 체크
  oldPwdError.value = isEmpty(formData.password)
  newPwdError.value = isEmpty(formData.newPassword)
  cfmPasswordError.value = isEmpty(formData.cfmPassword)
  if (oldPwdError.value) {
    showAlert('현재 비밀번호는 필수 입력 값입니다.')
    return false
  }
  if (newPwdError.value) {
    showAlert('변경 비밀번호는 필수 입력 값입니다.')
    return false
  }
  if (cfmPasswordError.value) {
    showAlert('변경 비밀번호 확인은 필수 입력 값입니다.')
    return false
  }
  // 현재 비밀번호, 변경 비밀번호 불일치 여부 체크
  if (formData.password === formData.newPassword) {
    newPwdError.value = true
    showAlert('현재 비밀번호와 변경 비밀번호가 동일합니다.')
    return false
  }

  // 4. 변경 비밀번호, 변경 비밀번호 확인 일치 여부 체크
  if (formData.newPassword !== formData.cfmPassword) {
    cfmPasswordError.value = true
    showAlert('변경 비밀번호와 확인 비밀번호는\n 같아야 합니다.')
    return false
  }

  showConfirm('비밀번호를 변경하시겠습니까?', changePwd)
}

const goLogin = () => {
  router.push('/login')
}
</script>

<style lang="scss" scoped></style>
