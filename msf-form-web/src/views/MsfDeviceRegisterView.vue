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
                <MsfInput variant="underline" v-model="formData.uuid" class="ut-w100p" disabled />
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
                <MsfButton variant="subtle">로그인 화면으로 이동</MsfButton>
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

const formData = reactive({
  userNm: '홍길동', //이름
  localIp: '111.111.111.111', //아이피
  uuid: '7878', //단말 고유 ID  // molo - 수정 필요
  deptName: 'IT전략팀', //부서
  osCd: 'A',
  version: '1.0',
  appOsVer: '1.1.1',
})

onMounted(async () => {})

const onClickModelRegist = () => {
  console.log('formData:', formData)
  if (!formData.uuid) {
    showAlert('단말 고유ID는 필수 입력 값입니다.')
    return
  }
  showConfirm('단말 사용을 등록하시겠습니까?', () => {
    post('/api/app/model/register', formData)
      .then((data) => {
        console.log(data.code)
        if (data.code == '0000') {
          showAlert('단말 사용 등록이 완료되었습니다.')
          window.location.reload()
        } else {
          showAlert('단말 사용 등록이 실패하였습니다.\n 다시 시도해 주세요.')
          // showAlert(data.message)
        }
      })
      .catch((err) => console.error('데이터를 가져오는 중 오류 발생:', err))
  })
}
</script>

<style lang="scss" scoped></style>
