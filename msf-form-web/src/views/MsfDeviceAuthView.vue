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
                :userId="formData.userId"
                :name="formData.userNm"
                :phone="formData.userPhone"
                :show-device="formData.showDevice"
                @complete="(result) => (formData.complete = result)"
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
import { reactive, watch, onMounted } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'
import { useRouter } from 'vue-router'
import { useMsfUserStore } from '@/stores/msf_user'

const router = useRouter()
const msfUserStore = useMsfUserStore()

// 퍼블 샘플
const formData = reactive({
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
    formData.userId = msfUserStore.userData.userId
    formData.userNm = msfUserStore.userData.userNm
    formData.userPhone = msfUserStore.userData.mobileNo
    formData.showDevice = msfUserStore.deviceInfo.apvSttusCd === 'C' ? true : false
  }
})

watch(
  () => formData.complete,
  (complete) => {
    if (complete) {
      showAlert('인증이 완료되었습니다.', () => {
        if (formData.showDevice === true) {
          router.push('/deviceRegist')
        } else {
          router.push('/')
        }
      })
    } else {
      showAlert('인증에 실패하였습니다. 인증번호를 다시 확인해주세요.')
    }
  },
)

const goLogin = () => {
  router.push('/login')
}
</script>

<style lang="scss" scoped></style>
