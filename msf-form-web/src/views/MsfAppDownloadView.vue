<template>
  <!-- 스마트신청서 앱 설치: 헤더,하단메뉴 없는 레이아웃입니다 -->
  <div class="app-download-wrap">
    <div class="app-download-header">
      <img src="@/assets/images/logo.svg" alt="kt m mobile 로고" class="logo" />
      <strong class="app-download-name"><span>SMART</span>신청서</strong>
    </div>
    <h4 class="app-download-tit">앱(App) 다운로드</h4>
    <div class="app-download-layout">
      <MsfBox variant="outline" align="center" class="app-download-box">
        <div class="app-icon">
          <p class="app-icon-img">
            <img src="@/assets/images/appIpad.svg" alt="ipad 아이콘" />
          </p>
          <span class="app-icon-tit">iPad</span>
        </div>
        <MsfButton variant="primary" @click="downIpad">ipad 다운로드</MsfButton>
      </MsfBox>
      <MsfBox variant="outline" align="center" class="app-download-box">
        <div class="app-icon">
          <p class="app-icon-img">
            <img src="@/assets/images/appAndroid.svg" alt="Android 아이콘" />
          </p>
          <span class="app-icon-tit">Android</span>
        </div>
        <MsfButton variant="primary" @click="downAndroid">Android 다운로드</MsfButton>
      </MsfBox>
    </div>
    <MsfBox class="infoBox">
      <MsfTextList
        type="dash"
        :items="[
          'App 설치 전 OS 는 최신 OS 버전으로 업데이트 해주세요.<br/>* 최소 권장사항 : iOS 15.0이상 / Android 12.0 이상',
          '해당되는 디바이스 버튼을 클릭하여 파일을 설치합니다.',
          '패드 등록 후 승인을 받으신 후 사용해 주세요.',
        ]"
      />
      <div class="help-desk">
        <img src="@/assets/images/helpDeskIcon.svg" alt="헬프데스크 이미지" />
        <div class="help-desk-text">
          <span class="help-txt">헬프데스크</span>
          <em class="help-number">1588-3391(6번 K-note)</em>
        </div>
      </div>
    </MsfBox>
    <p class="copyright">Copyright © kt M mobile. All rights reserved.</p>
  </div>
  <MsfDialog
    :isOpen="isModalOpen"
    title="계정 확인"
    showClose
    size="medium"
    autoHeight
    @open="handleModalOpen"
    @close="handleModalClose"
  >
    <MsfStack type="field" class="ut-w-full">
      <MsfInput
        id="inp-userId"
        v-model="userId"
        placeholder="아이디를 입력하세요."
        class="ut-flex-1"
      />
      <MsfButton @click="handleDownload">확인</MsfButton>
    </MsfStack>
    <MsfButtonGroup align="center" margin="1">
      <a :href="iosInstallUrl" download v-if="isIpad">
        <MsfButton variant="primary" prefixIcon="ios">iOS 앱 다운로드</MsfButton>
      </a>
      <a :href="androidDownloadUrl" download v-if="isAndroid">
        <MsfButton variant="primary" prefixIcon="android">Android 앱 다운로드</MsfButton>
      </a>
    </MsfButtonGroup>
    <MsfBox padding="16">
      <MsfTextList
        :items="['스마트신청서 App 사용을 위해 가입하신 아이디를 입력해 주세요.']"
        level="1"
      />
    </MsfBox>
  </MsfDialog>
  <!-- // 스마트신청서 앱 설치: 헤더,하단메뉴 없는 레이아웃입니다 -->
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'
import { isNonProduction } from '@/libs/utils/env.utils'

const androidDownloadUrl = ref('')
const iosInstallUrl = ref('')

const userId = ref('') // 아이디 입력
const userPad = ref('')
const isModalOpen = ref(false) // 계정 확인 팝업 상태
const isAndroid = ref(false)
const isIpad = ref(false)

const downIpad = () => {
  isModalOpen.value = true
  userPad.value = 'I'
}

const downAndroid = () => {
  isModalOpen.value = true
  userPad.value = 'A'
}

// 팝업 열릴 때 실행될 함수
const handleModalOpen = () => {
  // console.log('modal 열림! 이벤트 선언하세요!')
}
// 팝업 닫힐 때 실행될 함수
const handleModalClose = () => {
  isModalOpen.value = false
  userId.value = ''
  userPad.value = ''
  isAndroid.value = false
  isIpad.value = false
  // console.log('modal 닫힘! 이벤트 선언하세요!')
}

// 팝업 내 다운로드 버튼 클릭
const handleDownload = async () => {
  console.log('env no product:' + isNonProduction())
  if (!userId.value) {
    showAlert('아이디를 입력하세요.')
    return
  }
  const result = await post('/api/n/app/download', { userId: userId.value })
  if (result.code === '0000') {
    for (const item of result.data) {
      if (userPad.value === item.osCd) {
        if (userPad.value === 'A') {
          androidDownloadUrl.value = item.updateUrl
          isAndroid.value = true
        } else {
          isIpad.value = true
          // if (isNonProduction()) {
          //   iosInstallUrl.value = `itms-services://?action=download-manifest&url=` + item.updateUrl
          // } else {
          //   iosInstallUrl.value = `itms-apps://itunes.apple.com/app/id6792940961`
          // }
          iosInstallUrl.value = `itms-services://?action=download-manifest&url=` + item.updateUrl
        }
      }
    }
    // } else {
    //   showAlert('입력하신 아이디는 사용 불가합니다.\n대리점에 문의해 주세요.')
  }
}

onMounted(async () => {
  // const result = await post('/api/n/app/download')
  // if (result.code === '0000') {
  //   for (const item of result.data) {
  //     if (item.osCd === 'I') {
  //       iosManifestUrl.value = item.updateUrl
  //       // console.log(iosInstallUrl.value)
  //     } else if (item.osCd === 'A') {
  //       androidDownloadUrl.value = item.updateUrl
  //       // console.log(androidDownloadUrl.value)
  //     }
  //   }
  // } else {
  //   showAlert('앱 버전 정보를 가져오는 데 실패하였습니다.\n재실행 해주세요.')
  // }
})
</script>

<style lang="scss" scoped></style>
