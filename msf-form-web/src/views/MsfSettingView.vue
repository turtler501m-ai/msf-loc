<!-- 설정 -->
<template>
  <MsfTitleBar title="설정" />
  <!-- 로그인 설정 -->
  <MsfTitleArea title="로그인 설정" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="지문 로그인 설정">
      <MsfSwitch v-model="formData.isFingerLogin" showInnerLabel @change="handleChange1" />
    </MsfFormGroup>
    <MsfFormGroup label="Face ID<br/>로그인 설정">
      <MsfSwitch v-model="formData.isFaceId" showInnerLabel @change="handleChange2" />
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
          >APP 버전 <span class="version"> v {{ appSettings?.version }}</span></em
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

const appSettings = ref(null)

const formData = reactive({
  isFingerLogin: false, //지문 로그인 설정
  isFaceId: false, //페이스ID 로그인 설정
})

onMounted(async () => {
  // 앱에서 uuid 를 구해서 사용
  const initData = {
    uuid: '7878', // molo - 수정 필요
  }
  post('/api/app/login/init', initData)
    .then((data) => {
      appSettings.value = data.data
      console.log('init data:' + data.data.apvSttusCd)
      console.log('bioLoginYn:' + data.data.bioLoginYn)
      // B: 지문, F: 얼굴
      if (data.data?.bioLoginYn == 'F') {
        formData.isFaceId = true
        formData.isFingerLogin = false
      } else if (data.data?.bioLoginYn == 'B') {
        formData.isFingerLogin = true
        formData.isFaceId = false
      } else {
        formData.isFaceId = false
        formData.isFingerLogin = false
      }
    })
    .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
})

const onChangeBio = () => {
  const postData = {
    // molo - 수정 필요
    uuid: '7878',
    bioLoginYn: 'N',
  }
  if (formData.isFaceId) {
    postData.bioLoginYn = 'F'
  } else if (formData.isFingerLogin) {
    postData.bioLoginYn = 'B'
  } else {
    postData.bioLoginYn = 'N'
  }
  // showAlert(postData.bioLoginYn)
  showConfirm(
    '로그인 설정을 변경하시겠습니까?',
    () => {
      post('/api/app/settingbio/modify', postData)
        .then((data) => {
          console.log(data.code)
          if (data.code == '0000') {
            showAlert('로그인 설정이 변경되었습니다.')
          } else {
            showAlert('로그인 설정 변경이 실패하였습니다.\n 다시 시도해 주세요.')
          }
          window.location.reload()
        })
        .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
    },
    '',
    () => {
      window.location.reload()
    },
  )
}

const handleChange1 = () => {
  if (formData.isFingerLogin == true) {
    formData.isFaceId = false
  }
  onChangeBio()
}

const handleChange2 = () => {
  if (formData.isFaceId == true) {
    formData.isFingerLogin = false
  }
  onChangeBio()
}

const onClickAppVersion = () => {
  const postData = {
    // molo - 수정 필요
    os: 'A',
    version: '1.1',
    uuid: '7878',
  }
  post('/api/app/intro', postData)
    .then((data) => {
      console.log(data.data.update)
      if (data.data.update == 'N') {
        showAlert('현재 설지된 App 이 최신 버전 입니다.')
      } else {
        showAlert(
          '최신 출시된 App 이 다운로드되어,\n 자동으로 설치되므로 App을 자동 종료 합니다.',
          () => {
            console.log('확인 버튼 클릭:' + data.data.updateUrl)
          },
        )
      }
    })
    .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
}
</script>

<style lang="scss" scoped></style>
