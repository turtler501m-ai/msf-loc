<!-- 로그인 -->
<template>
  <div class="login-wrap">
    <div class="login-inner">
      <div class="login-box">
        <div class="login-title">
          <div class="login-logo-row">
            <h1 class="logo"><img src="@/assets/images/logo.svg" alt="kt m mobile 로고" /></h1>
            <span v-if="envName" class="env-badge" :class="`env-badge--${envMode}`">{{ envName }}</span>
          </div>
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
                  :maxlength="20"
                  class="ut-w100p"
                  name="username"
                  autocomplete="username"
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>비밀번호</em>" vertical>
                <MsfInput
                  type="password"
                  variant="underline"
                  v-model="formData.password"
                  placeholder="비밀번호를 입력하세요."
                  :maxlength="20"
                  class="ut-w100p"
                  reveal
                  name="password"
                  autocomplete="current-password"
                />
                <MsfCheckbox v-model="formData.idSave" label="아이디 저장" blockPadding />
              </MsfFormGroup>
              <MsfButtonGroup gap="3">
                <MsfButton variant="primary" block @click="onClickLogin">로그인</MsfButton>
                <MsfButton
                  variant="secondary"
                  prefixIcon="faceId"
                  :disabled="bioLoginDisabled"
                  class="ut-flex-1 ut-weight-regular"
                  @click="onClickBioLogin"
                  >지문/Face ID 로그인</MsfButton
                >
                <!-- <MsfButton
                  v-if="deviceType === 'I'"
                  variant="secondary"
                  prefixIcon="faceId"
                  class="ut-flex-1 ut-weight-regular"
                  >Face ID 로그인</MsfButton
                >
                <MsfButton
                  v-if="deviceType !== 'I'"
                  variant="secondary"
                  prefixIcon="touchId"
                  class="ut-flex-1 ut-weight-regular"
                >지문 로그인</MsfButton
                > -->
              </MsfButtonGroup>
              <div class="login-state-wrap">
                <div class="use-state">
                  <p class="state-tit">사용 상태</p>
                  <p class="state-info failed" v-if="apvSttusCd != 'A'">
                    <MsfIcon name="loginCheck" size="small" />미승인
                  </p>
                  <p class="state-info" v-if="apvSttusCd == 'A'">
                    <MsfIcon name="loginCheck" size="small" />승인
                  </p>
                </div>
                <div v-if="apvSttusCd == 'A'">
                  <MsfButton variant="subtle" @click="onClickModelRemove">승인철회</MsfButton>
                </div>
              </div>
              <MsfFormGroup
                v-if="isDeviceUuidOverrideEnabled"
                class="device-uuid-test-group"
                label="<em class='login-label'>단말 고유 ID</em>"
                helpText="지정한 환경의 브라우저에서만 표시됩니다. 테스트 용도로 값을 직접 설정해서 사용 가능합니다."
                vertical
              >
                <MsfInput
                  variant="underline"
                  v-model="formData.deviceUuid"
                  placeholder="임의의 단말 고유 ID를 입력하세요."
                  class="ut-w100p"
                  @input="syncDeviceUuidOverride"
                  @blur="onDeviceUuidOverrideBlur"
                />
              </MsfFormGroup>
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
import { reactive, ref, onMounted } from 'vue'
import { post } from '@/libs/api/msf.api'
import { useRouter } from 'vue-router'
import { useMsfUserStore } from '@/stores/msf_user'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { getEnvMode, getEnvName } from '@/libs/utils/env.utils'
import {
  bioLogin,
  canOverrideDeviceUuid,
  getBioLoginStatus,
  getDeviceUuid,
  setDeviceUuidOverride,
  waitForDeviceInfo,
} from '@/libs/utils/device.utils'

const apvSttusCd = ref(null)
const router = useRouter()
const msfUserStore = useMsfUserStore()
const bioLoginDisabled = ref(true)
const isDeviceUuidOverrideEnabled = canOverrideDeviceUuid()
const envName = getEnvName()
const envMode = getEnvMode()

const formData = reactive({
  userId: '', //아이디
  password: '', //비밀번호
  authType: 'PASSWORD',
  deviceUuid: getDeviceUuid(),
})

const resetDeviceStatus = () => {
  apvSttusCd.value = null
  bioLoginDisabled.value = true
  msfUserStore.setDeviceInfo(null)
}

const syncDeviceUuidOverride = () => {
  formData.deviceUuid = isDeviceUuidOverrideEnabled
    ? setDeviceUuidOverride(formData.deviceUuid)
    : getDeviceUuid()

  if (!formData.deviceUuid) {
    resetDeviceStatus()
  }
}

const initLoad = async () => {
  await waitForDeviceInfo()
  syncDeviceUuidOverride()
  console.log('getBioLoginStatus 호출 전' + bioLoginDisabled.value)
  getBioLoginStatus()
  // delay(500)
  const isBioLoginAvailable = localStorage.getItem('isBioLoginAvailable')
  console.log('isBioLoginAvailable: ', isBioLoginAvailable)
  // delay(500)
  console.log('device uuid: ' + getDeviceUuid())
  const initData = {
    deviceUuid: getDeviceUuid(),
  }
  formData.deviceUuid = initData.deviceUuid || ''
  delay(500)
  if (initData.deviceUuid == null || initData.deviceUuid == '') {
    if (!isDeviceUuidOverrideEnabled) {
      // showAlert('단말기 정보 조회에 실패하였습니다. 앱을 재실행 해주세요.')
      showAlert('App 으로 이용이 가능합니다.')
      router.replace('/download')
    }
    return
  }
  post('/api/n/app/login/init', initData)
    .then((data) => {
      console.log('init data:', data.data.apvSttusCd)
      apvSttusCd.value = data.data.apvSttusCd
      console.log('init bio data:', data.data.bioLoginYn)
      if (data.data.apvSttusCd === 'A' && data.data.bioLoginYn === 'Y') {
        bioLoginDisabled.value = false
      }
      msfUserStore.setDeviceInfo(data.data)
    })
    .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))

  const savedUserId = localStorage.getItem('saveUserId')
  if (savedUserId) {
    formData.idSave = true
    formData.userId = savedUserId
  }
}

onMounted(() => {
  initLoad()
})

const onDeviceUuidOverrideBlur = () => {
  syncDeviceUuidOverride()
  if (formData.deviceUuid) {
    initLoad()
  }
}

const onClickBioLogin = async () => {
  if (bioLoginDisabled.value) {
    showAlert('생체인증이 지원되지 않는 단말입니다.')
    return
  }
  // 서버에서 난수 조회
  const result = await post('/api/n/auth/biometric/challenge', {
    deviceUuid: getDeviceUuid(),
  })
  console.log(result.message)
  if (result.code === '0000') {
    const randomString = result.data.nonce
    var resultdata = await bioLogin(randomString)
    // showAlert('bioLogin resultdata: ' + JSON.stringify(resultdata))

    if (resultdata) {
      const formBioData = {
        deviceUuid: getDeviceUuid(),
        bioKey: resultdata.biokey,
        encryptedNonce: resultdata.key,
      }
      if (!resultdata?.biokey || resultdata.biokey === '' || resultdata.code === '2222') {
        showAlert('생체인증 정보가 변경되었습니다.\n 다시 등록 후 이용해 주세요.')
        return
      }
      if (resultdata.code === '0000') {
        post('/api/n/auth/biometric/verify', formBioData)
          .then(async (data) => {
            if (data.code === '0000') {
              msfUserStore.setUserData(data.data)
              if (formData.idSave) {
                localStorage.setItem('saveUserId', formData.userId)
              } else {
                localStorage.removeItem('saveUserId')
              }
              localStorage.setItem('saveUserId', formData.userId)
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
            } else {
              console.log(data.message)
            }
          })
          .catch((err) => console.error('오류 발생:', err))
      } else {
        showAlert('생체인증 로그인에 실패하였습니다.\n 다시 시도해 주세요.')
      }
    } else {
      showAlert('생체인증 로그인에 실패하였습니다.\n 다시 시도해 주세요.')
    }
  } else {
    showAlert('생체인증 로그인에 실패하였습니다.\n 다시 시도해 주세요.')
  }
}

const onClickLogin = () => {
  // 로그인 API 호출
  syncDeviceUuidOverride()
  formData.deviceUuid = getDeviceUuid()
  if (!formData.deviceUuid) {
    showAlert('단말기 정보 조회에 실패하였습니다. 앱을 재실행 해주세요.')
    return
  }
  console.log('formData:', formData)
  if (!formData.userId) {
    showAlert('아이디는 필수 입력 값입니다.')
    return
  }
  if (!formData.password) {
    showAlert('비밀번호는 필수 입력 값입니다.')
    return
  }
  post('/api/n/auth/login', formData)
    .then(async (data) => {
      if (data.code === '0000') {
        msfUserStore.setUserData(data.data)
        if (formData.idSave) {
          localStorage.setItem('saveUserId', formData.userId)
        } else {
          localStorage.removeItem('saveUserId')
        }
        localStorage.setItem('saveUserId', formData.userId)
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
      } else {
        console.log(data.message)
      }
    })
    .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
}

const onClickModelRemove = () => {
  const postData = {
    deviceUuid: getDeviceUuid(),
  }
  showConfirm('단말의 사용등록을 승인 철회하시겠습니까?', () => {
    post('/api/n/app/model/remove', postData)
      .then((data) => {
        console.log('/remove.result.code:', data.code)
        if (data.code === '0000') {
          showAlert('단말기 승인 철회가 완료되었습니다.', initLoad())
        } else {
          showAlert('단말기 승인 철회가 실패하였습니다.\n 다시 시도해 주세요.')
          // showAlert(data.message)
        }
      })
      .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
  })
}
const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms))
</script>

<style lang="scss" scoped>
.device-uuid-test-group {
  padding: var(--spacing-x3) var(--spacing-x4);
  border: var(--border-width-base) solid var(--color-gray-150);
  border-radius: var(--border-radius-m);
  background-color: var(--color-gray-25);
}
</style>
