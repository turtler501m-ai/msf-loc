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
                :login-key="formData.loginSessionId"
                :name="formData.userNm"
                :phone="formData.userPhone"
                :show-device="formData.showDevice"
                @complete="onCompleteDeviceAuthNumber"
              />
              <MsfButtonGroup>
                <MsfButton variant="subtle" @click="goLogin">로그인 화면으로 이동</MsfButton>
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
import { reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'
import { useMsfUserStore } from '@/stores/msf_user'

const router = useRouter()
const msfUserStore = useMsfUserStore()

// 퍼블 샘플
const formData = reactive({
  loginSessionId: '',
  userId: '', //고객ID
  userNm: '', //이름
  userPhone: '', //전화번호
  complete: false,
  showDevice: false,
})

onMounted(() => {
  if (!msfUserStore.userData) {
    showAlert('로그인이 필요합니다.\n로그인 화면으로 이동합니다.', () => {
      router.push('/login')
    })
  } else {
    formData.loginSessionId = msfUserStore.userData.loginSessionId
    formData.userId = msfUserStore.userData.userInfo.userId
    formData.userNm = msfUserStore.userData.userInfo.userName
    formData.userPhone = msfUserStore.userData.userInfo.phoneNumber
    formData.showDevice = msfUserStore.deviceInfo.apvSttusCd === 'C' ? true : false
  }
})

const onCompleteDeviceAuthNumber = (result) => {
  if (result) {
    formData.complete = result
    showAlert('인증이 완료되었습니다.', async () => {
      // TODO: loginAction 호출
      post('/api/auth/login/session/get', formData).then(async (data) => {
        msfUserStore.setUserData(data.data)
        const res = await msfUserStore.checkAuthAction()
        if (res.message) {
          showAlert(res.message, () => {
            if (res.url) {
              router.push(res.url)
            }
          })
        } else {
          if (res.url) {
            router.push(res.url)
          }
        }
      })
    })
  } else {
    showAlert('인증에 실패하였습니다. 인증번호를 다시 확인해주세요.')
  }
}

const goLogin = () => {
  router.push('/login')
}
</script>

<style lang="scss" scoped></style>
