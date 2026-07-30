<!-- 설정 -->
<template>
  <MsfTitleBar title="설정" />
  <!-- 로그인 설정 -->
  <MsfTitleArea title="로그인 설정" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="지문/Face ID<br/>로그인 설정">
      <MsfSwitch
        v-model="formData.isBioLogin"
        :disabled="bioLoginDisabled"
        showInnerLabel
        @change="onChangeBio"
      />
    </MsfFormGroup>
  </MsfStack>
  <!-- // 로그인 설정 -->
  <!-- App 정보 -->
  <MsfTitleArea title="App 정보" />
  <MsfBox variant="outline">
    <div class="app-version">
      <img src="@/assets/images/appIcon.svg" alt="kt m mobile 앱 아이콘" class="app-img" />
      <div class="app-version-msg">
        <em class="app-version-txt"
          >APP 버전 <span class="version"> {{ appSettings?.version }}</span></em
        >
        <span class="app-version-desc">최신 버전 업데이트를 해주시기 바랍니다.</span>
      </div>
      <MsfButtonGroup align="center" margin="2">
        <MsfButton variant="subtle" @click="onClickAppVersion">업데이트</MsfButton>
      </MsfButtonGroup>
    </div>
  </MsfBox>
  <!-- // App 정보 -->
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { post } from '@/libs/api/msf.api'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
// import { useMsfUserStore } from '@/stores/msf_user'
import {
  getBioLoginStatus,
  setBioLoginRegistration,
  setBioLoginRegistrationSave,
  generateRandomString,
  generateHash,
  bioLogin,
  getDeviceUuid,
  appUpdate,
} from '@/libs/utils/device.utils'
import { isNonProduction } from '@/libs/utils/env.utils'

const appSettings = ref(null)
const bioLoginDisabled = ref(true)
// const msfUserStore = useMsfUserStore()

const formData = reactive({
  isBioLogin: false, //지문 로그인 설정
})

onMounted(async () => {
  console.log('getBioLoginStatus 호출 전' + bioLoginDisabled.value)
  await getBioLoginStatus()
  console.log('isBioLoginAvailable: ', localStorage.getItem('isBioLoginAvailable'))

  if (localStorage.getItem('isBioLoginAvailable') === 'Y') {
    bioLoginDisabled.value = false
    const initData = {
      deviceUuid: getDeviceUuid(),
    }
    if (initData.deviceUuid == null || initData.deviceUuid == '') {
      showAlert('deviceUuid is null or empty')
      return
    }
    post('/api/app/login/init', initData)
      .then((data) => {
        if (data.code == '0000') {
          appSettings.value = 'V ' + data.data
          console.log('init data:' + data.data.apvSttusCd)
          console.log('bioLoginYn:' + data.data.bioLoginYn)
          if (data.data?.bioLoginYn == 'Y') {
            formData.isBioLogin = true
          } else {
            formData.isBioLogin = false
          }
        } else {
          showAlert(data.message)
        }
      })
      .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
  } else {
    formData.isBioLogin = false
  }
})

const onChangeBio = () => {
  const postData = {
    deviceUuid: getDeviceUuid(),
    bioLoginYn: 'N',
    bioLoginToken: '',
    osCd: localStorage.getItem('deviceType'),
  }

  showConfirm(
    '로그인 설정을 변경하시겠습니까?',
    async () => {
      console.log('formData.isBioLogin: ', formData.isBioLogin)
      if (formData.isBioLogin == true) {
        // 사용자가 생체인증을 켜는 경우
        const randomString = (await generateRandomString(10)) ?? 'ktmAppAAmo'
        // alert('생체인증 등록을 위한 randomString: ' + randomString)

        const resultkey = await setBioLoginRegistration(randomString)

        if (resultkey !== randomString) {
          showAlert('생체인증 등록에 실패하였습니다.\n 다시 시도해 주세요.')
          setFailBioLogin()
          return
        }

        postData.bioLoginYn = 'Y'
        postData.bioLoginToken = await generateHash(postData.deviceUuid)
        console.log('생체인증 토큰: ', postData.bioLoginToken)

        post('/api/app/settingbio/modify', postData)
          .then((data) => {
            console.log(data.code)
            if (data.code == '0000') {
              // app data 변경
              setBioLoginRegistrationSave(postData.bioLoginToken)
              showAlert('로그인 설정이 변경되었습니다.')
            } else {
              showAlert('로그인 설정 변경이 실패하였습니다.\n 다시 시도해 주세요.')
              setFailBioLogin()
            }
          })
          .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
      } else {
        // 서버에서 난수 조회
        const result = await post('/api/auth/biometric/challenge', {
          deviceUuid: getDeviceUuid(),
        })
        console.log(result.message)
        if (result.code === '0000') {
          const randomString = result.data.nonce
          var resultdata = await bioLogin(randomString)
          // showAlert('bioLogin resultdata: ' + JSON.stringify(resultdata))

          postData.bioLoginYn = 'N'
          postData.bioLoginToken = ''

          if (resultdata) {
            const formBioData = {
              deviceUuid: getDeviceUuid(),
              bioKey: resultdata.biokey,
              encryptedNonce: resultdata.key,
              osCd: localStorage.getItem('deviceType'),
              bioLoginYn: 'N',
              bioLoginToken: '',
            }
            if (!resultdata?.biokey || resultdata.biokey === '' || resultdata.code === '2222') {
              showAlert('생체인증 정보가 변경되었습니다.\n생체정보를 초기화합니다.', () => {
                postData.bioLoginYn = 'N'
                postData.bioLoginToken = ''
                post('/api/app/settingbio/modify', postData)
                  .then((data) => {
                    console.log(data.code)
                    if (data.code == '0000') {
                      showAlert('초기화 되었습니다.')
                    } else {
                      showAlert('변경이 실패하였습니다.\n 다시 시도해 주세요.')
                      setFailBioLogin()
                    }
                  })
                  .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
              })
              return
            }
            if (resultdata.code === '0000') {
              post('/api/auth/biometric/disable/verify', formBioData)
                .then(async (data) => {
                  if (data.code == '0000') {
                    // app data 변경
                    setBioLoginRegistrationSave(postData.bioLoginToken)
                    showAlert('로그인 설정이 변경되었습니다.')
                  } else {
                    showAlert('로그인 설정 변경이 실패하였습니다.\n 다시 시도해 주세요.')
                    setFailBioLogin()
                  }
                })
                .catch((err) => console.error('오류 발생:', err))
            } else {
              showAlert('생체인증에 실패하였습니다.\n 다시 시도해 주세요.')
              setFailBioLogin()
            }
          }
        }
      }
    },
    '',
    () => {
      setFailBioLogin()
    },
  )
}

const setFailBioLogin = () => {
  if (formData.isBioLogin == true) {
    formData.isBioLogin = false
  } else {
    formData.isBioLogin = true
  }
}

const downloadUrl = ref('')

const onClickAppVersion = () => {
  const postData = {
    os: localStorage.getItem('deviceType'), // 운영체제 정보
    appOsVer: localStorage.getItem('appOsVersion'), // 앱 운영체제 버전 정보 (예시)
    version: localStorage.getItem('appVersion'), // 앱 버전 정보
    uuid: getDeviceUuid(),
  }
  if (postData.os === 'P') {
    showAlert('PC 버전은 최신 버전입니다.')
    return
  }
  post('/api/app/intro', postData)
    .then((result) => {
      console.log(result.code)
      console.log('env no product:' + isNonProduction())
      if (result.code == '0000') {
        if (result.data.update == 'Y') {
          if (localStorage.getItem('deviceType') === 'I') {
            // if (isNonProduction()) {
            //   downloadUrl.value =
            //     `itms-services://?action=download-manifest&url=` + result.data.updateUrl
            // } else {
            //   downloadUrl.value = `itms-apps://itunes.apple.com/app/id6792940961`
            // }
            downloadUrl.value =
              `itms-services://?action=download-manifest&url=` + result.data.updateUrl
          }
          if (localStorage.getItem('deviceType') === 'A') {
            downloadUrl.value = result.data.updateUrl
          }
          console.log(downloadUrl.value)
          if (result.data.mustUpCd === 'Y') {
            showAlert(
              '현재 설치된 App이 최신 버전이 아닙니다.\n최신 버전으로 업데이트 해주세요.',
              () => {
                appUpdate(downloadUrl.value)
              },
            )
          } else {
            showConfirm(
              '최신 출시된 App이 있습니다.\n최신 버전으로 업데이트 하시겠습니까?',
              () => {
                appUpdate(downloadUrl.value)
              },
              '',
              () => {},
            )
          }
        } else {
          showAlert('현재 설치된 App 이 최신 버전 입니다.')
        }
      } else {
        showAlert(result.message)
      }
    })
    .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
}
</script>

<style lang="scss" scoped></style>
