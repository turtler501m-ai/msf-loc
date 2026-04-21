<!-- 로그인 -->
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
            <!-- 로그인 -->
            <MsfStack vertical type="formgroups">
              <MsfFormGroup label="<em class='login-label'>아이디</em>" vertical>
                <MsfInput
                  variant="underline"
                  v-model="formData.userId"
                  placeholder="아이디를 입력하세요."
                  class="ut-w100p"
                />
              </MsfFormGroup>
              <MsfFormGroup label="<em class='login-label'>비밀번호</em>" vertical>
                <MsfInput
                  type="password"
                  variant="underline"
                  v-model="formData.userPw"
                  placeholder="비밀번호를 입력하세요."
                  class="ut-w100p"
                />
                <MsfCheckbox v-model="formData.idSave" label="아이디 저장" blockPadding />
              </MsfFormGroup>
              <MsfButtonGroup gap="3">
                <MsfButton variant="primary" block @click="onClickLogin">로그인</MsfButton>
                <MsfButton
                  variant="secondary"
                  prefixIcon="touchId"
                  class="ut-flex-1 ut-weight-regular"
                  >지문 로그인</MsfButton
                >
                <MsfButton
                  variant="secondary"
                  prefixIcon="faceId"
                  class="ut-flex-1 ut-weight-regular"
                  >Face ID 로그인</MsfButton
                >
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
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'

const apvSttusCd = ref(null)

const formData = reactive({
  userId: '', //아이디
  userPw: '', //비밀번호
  idSave: false, //아이디 저장 여부
  deviceId: 'Phone-A',
  authType: 'PASSWORD',
})

onMounted(async () => {
  // 앱에서 uuid 를 구해서 사용
  const initData = {
    uuid: '7878', // molo - 수정 필요
  }
  post('/api/app/login/init', initData)
    .then((data) => {
      console.log('init data:' + data.data.apvSttusCd)
      apvSttusCd.value = data.data.apvSttusCd
    })
    .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
})

const onClickLogin = () => {
  // 로그인 API 호출
  console.log('formData:', formData)
  if (!formData.userId) {
    showAlert('아이디는 필수 입력 값입니다.')
    return
  }
  if (!formData.userPw) {
    showAlert('비밀번호는 필수 입력 값입니다.')
    return
  }
  post('/api/login/login', formData)
    .then((data) => {
      console.log(data.code)
      if (data.code == '0000') {
        console.log('로그인 성공')
      } else {
        showAlert(data.message)
      }
    })
    .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
}

const onClickModelRemove = () => {
  const postData = {
    uuid: '7878', // molo - 수정 필요
  }
  showConfirm('단말의 사용등록을 승인 철회하시겠습니까?', () => {
    post('/api/app/model/remove', postData)
      .then((data) => {
        console.log(data.code)
        if (data.code == '0000') {
          showAlert('단말기 승인 철회가 완료되었습니다.')
          window.location.reload()
        } else {
          showAlert('단말기 승인 철회가 실패하였습니다.\n 다시 시도해 주세요.')
          // showAlert(data.message)
        }
      })
      .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
  })
}
</script>

<style lang="scss" scoped></style>
