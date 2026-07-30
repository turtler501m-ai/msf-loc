<template>
  <div>
    <h1 class="title">단말 Bridge</h1>
    <div>devideType : {{ deviceType }}</div>
  </div>
  <br />
  <MsfStack type="field" class="ut-w100p">
    <MsfInput class="ut-w-500" placeholder="URL을 입력하세요" v-model="exportUrl" />
    <MsfButton variant="tertiary" @click="callExportBrowser"> 브라우저 정보 내보내기 </MsfButton>
  </MsfStack>
  <br />
  <MsfStack type="field" class="ut-w100p">
    <MsfButton variant="tertiary" @click="callGetDeviceInfo"> 단말정보조회(deviceInfo)</MsfButton>
    <MsfButton variant="tertiary" @click="appFinish"> 앱 종료</MsfButton>
    <MsfButton variant="tertiary" @click="getBioLoginStatus"> 생체인증 가능여부조회</MsfButton>
  </MsfStack>
  <br />
  <MsfStack type="field" class="ut-w100p">
    <MsfInput class="ut-w-500" placeholder="서버에서 생성난 난수값을 입력하세요" v-model="key" />
    <MsfButton variant="tertiary" @click="setBioLoginRegist"> 생체인증 등록 요청</MsfButton>
  </MsfStack>
  <br />
  <MsfStack type="field" class="ut-w100p">
    <MsfInput
      class="ut-w-500"
      placeholder="서버에서 생성하는 사용자 구분용 key을 입력하세요"
      v-model="biokey"
    />
    <MsfButton variant="tertiary" @click="setBioLoginRegistSave">
      생체인증 등록 완료 / 삭제</MsfButton
    >
  </MsfStack>
  <br />
  <MsfStack type="field" class="ut-w100p">
    <MsfInput class="ut-w-500" placeholder="randomKey을 입력하세요" v-model="randomKey" />
    <MsfButton variant="tertiary" @click="setBioLogin"> 생체인증 로그인 요청</MsfButton>
  </MsfStack>
  <br />
  <MsfStack type="field" class="ut-w100p">
    <MsfInput class="ut-w-500" placeholder="camkey을 입력하세요" v-model="cameraKey" />
    <MsfButton variant="tertiary" @click="getShowCamera"> 사진촬영요청</MsfButton>
  </MsfStack>
  <br />
  <MsfStack type="field" class="ut-w100p">
    <MsfInput
      class="ut-w-500"
      placeholder='appDB 저장할 데이터 [ 예: {"key": "bio", "value": "저장할 내용"} ]'
      v-model="dbSaveData"
    />
    <MsfButton variant="tertiary" @click="setAppDBSave"> appDB 저장</MsfButton>
  </MsfStack>
  <br />
  <MsfStack type="field" class="ut-w100p">
    <MsfInput
      class="ut-w-500"
      placeholder="appDB 조회 Key를 입력하세요. [ 예: bio ]"
      v-model="dbReadKey"
    />
    <MsfButton variant="tertiary" @click="getAppDBRead"> appDB 조회</MsfButton>
  </MsfStack>
  <div v-if="cameraImages.length">
    <img
      v-for="(image, index) in cameraImages"
      :key="index"
      :src="image"
      style="width: 150px; margin: 8px"
    />
  </div>
</template>

}
<script setup>
import { ref } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'
import {
  getDeviceInfo,
  exportBrowser,
  getBioLoginStatus,
  setBioLoginRegistration,
  setBioLoginRegistrationSave,
  bioLogin,
  appFinish,
  showCamera,
  setAppDB,
  getAppDB,
} from '@/libs/utils/device.utils'
// import { useMsfUserStore } from '@/stores/msf_user'

const deviceType = localStorage.getItem('deviceType')

const callGetDeviceInfo = () => {
  getDeviceInfo()
}

const exportUrl = ref('https://daum.net')
const callExportBrowser = () => {
  console.log('exportUrl.value: ', exportUrl.value)
  if (exportUrl.value === '') {
    showAlert('url 을 입력하세요')
    return
  }
  exportBrowser(exportUrl.value)
}

const key = ref('AAAAA')
const setBioLoginRegist = async (key) => {
  if (key.value === '') {
    showAlert('key 를 입력하세요')
    return
  }
  const resultdata = await setBioLoginRegistration(key.value)
  showAlert('setBioLoginRegistration resultdata: ' + JSON.stringify(resultdata))
}

const biokey = ref('')
const setBioLoginRegistSave = () => {
  if (biokey.value === '') {
    showAlert('biokey 를 입력하세요')
    return
  }
  setBioLoginRegistrationSave(biokey.value)
}

const randomKey = ref('')
const setBioLogin = async () => {
  if (randomKey.value === '') {
    showAlert('randomKey 를 입력하세요')
    return
  }
  var resultdata = await bioLogin(randomKey.value)
  showAlert('bioLogin resultdata: ' + JSON.stringify(resultdata))
}

const cameraKey = ref('')
const cameraImages = ref([])

const getShowCamera = () => {
  if (cameraKey.value === '') {
    showAlert('cameraKey 를 입력하세요')
    return
  }

  showCamera(cameraKey.value, (images) => {
    cameraImages.value = images
  })
}

const dbSaveData = ref('')
const setAppDBSave = () => {
  if (dbSaveData.value === '') {
    showAlert('저장할 데이터를 입력하세요')
    return
  }
  setAppDB(dbSaveData.value)
}

const dbReadKey = ref('bio')
const getAppDBRead = () => {
  if (dbReadKey.value === '') {
    showAlert('조회할 Key를 입력하세요')
    return
  }
  getAppDB(dbReadKey.value)
}
</script>

<style lang="scss" scoped></style>
