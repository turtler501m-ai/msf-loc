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
                <MsfInput variant="underline" v-model="formData.userNm" class="ut-w100p" disabled />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>아이피</em>" vertical>
                <MsfInput
                  variant="underline"
                  v-model="formData.localIp"
                  class="ut-w100p"
                  disabled
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>단말 고유 ID</em>" vertical>
                <MsfInput
                  variant="underline"
                  v-model="formData.deviceUuid"
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
              <MsfButton variant="primary" block @click="onClickModelRegist"
                >단말 사용 등록</MsfButton
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
import { reactive, onMounted } from 'vue'
import { post } from '@/libs/api/msf.api'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { useRouter } from 'vue-router'
import { useMsfUserStore } from '@/stores/msf_user'

const router = useRouter()
const msfUserStore = useMsfUserStore()

const loginSessionId = msfUserStore.getUserData()?.loginSessionId
const userName = msfUserStore.getUserData()?.userInfo?.userName
const localIp = msfUserStore.getUserData()?.userInfo?.clientIp
const deviceUuid = msfUserStore.getDeviceUuid()

const formData = reactive({
  loginSessionId: loginSessionId,
  userNm: userName, //이름
  localIp: localIp, //아이피
  deviceUuid: deviceUuid, //단말 고유 ID
  deptName: 'IT전략팀', //부서 // molo - 수정 필요
  osCd: 'A', // molo - 수정 필요
  version: '1.0', // molo - 수정 필요
  appOsVer: '1.1.1', // molo - 수정 필요
})

onMounted(async () => {
  if (!msfUserStore.userData) {
    showAlert('로그인이 필요합니다.\n로그인 화면으로 이동합니다.', () => {
      router.push('/login')
    })
  }
})

const onClickModelRegist = () => {
  console.log('formData:', formData)
  if (!formData.deviceUuid) {
    showAlert('단말 고유ID는 필수 입력 값입니다.')
    return
  }
  showConfirm('단말 사용을 등록하시겠습니까?', () => {
    post('/api/n/app/model/register', formData)
      .then(async (data) => {
        console.log(data.code)
        if (data.code == '0000') {
          showAlert('단말 사용 등록이 완료되었습니다.', () => {
            post('/api/n/auth/login/session/get', formData).then(async (data) => {
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
          showAlert('단말 사용 등록이 실패하였습니다.\n 다시 시도해 주세요.')
        }
      })
      .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
  })
}

const goLogin = () => {
  router.push('/login')
}
</script>

<style lang="scss" scoped></style>
